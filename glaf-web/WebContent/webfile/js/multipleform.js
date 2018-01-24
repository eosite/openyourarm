/**
 * 流程表单绑定
 */
var $tabstrip = $(".tabstrip"),$vertical = $("#vertical"), contextPath = mtxx.contextPath;;

$vertical.css({
	height : $(window).height()
}).kendoSplitter({
	orientation: "vertical",
	panes: [
		{ collapsible: false , resizable : false, scrollable: false ,size : '32px'},
		{ collapsible: true, resizable : true, scrollable: false},
		{ collapsible: true, resizable : true, scrollable: false, size : '0%'}
	],
	layoutChange : onResize,
});

$("#center-pane").kendoSplitter({
	orientation: "horizontal",
	panes: [
		{ collapsible: true, resizable : true, scrollable: true,size : '40%'},
		{ collapsible: true, resizable : true, scrollable: true},
		{ collapsible: true, resizable : true, scrollable: false,size : '10%'}
	]
});

function onResize(e){
	$vertical.css({
		height : $(window).height()
	});
	$tabstrip.css({
		height : $tabstrip.closest('div[role=group]').height()
	});
}

$tabstrip.each(function(i,item){
	var $this = $(this), a_target = 'span.dbl-close',eventType = 'click.' + a_target, $tab;
	mtxx[$this.attr('id')] = $this.kendoTabStrip({
		animation:  {
			open: {
				effects: "fadeIn"
			}
		},
		select : function(e){
			var dd = $(this.wrapper);
			$('#' + e.contentElement.id).height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
			window.refreshPages();
			var $grid = window.getGridByIndex($(e.item).index());
			window.setVariablesData($grid.data("variables"));
		}
	}).off(eventType, a_target).on(eventType, a_target, function(event) {
		$tab = $this.data('kendoTabStrip');
		$tab.remove($(this).closest("li"));
		$tab.select("li:last");
	});
});

function getSelectedVariables(){
	var $grid = window.getSelectedGrid();
	return $grid.data("variables");
}


var buttons = {
		select : false,
		selectProcess : function(e) {
			var url = mtxx.contextPath + "/mx/activiti/process/processDefinitionsSelect?"
					+ $.param({
						targetId : '',
						fn : 'selectProcessFunc'
					});
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : false,
				title : "选择流程",
				closeBtn : [ 0, true ],
				shade : [ 0, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '1100px', '630px' ],
				iframe : {
					src : url
				}
			});
		},
		saveSet : function(e) { //保存流程配置
			
			if(!window.pd){
				alert("请选择流程!");
				return false;
			}
			
			window.refreshPages();
			
			function initTarget(vs, taskId, collection, tasks){
				if(vs && vs.length){
					var nulls = [];
					$.each(vs, function(i, v){	
						if(!v.t){
							nulls.push({
								index : i,
								v : v
							});
						}
						if(v.target){
							collection.push({
								eleId : v.t,
								taskId : taskId
							});
						}
						if(v.targetTask){//指定节点
							tasks.push({
								eleId : v.t,
								taskId : taskId
							});
						}
					});
					if(nulls.length){
						$.each(nulls, function(i, v){
							//vs.splice(v.v, 1); 
							vs.splice(v.index, 1);
						});
					}
				}
			}
			
			if (pageProperties) {
				var array = new Array(), task, targets = {}, targetTasks = {};				
				$.each(pageProperties,function(pageId,v){
					if(v){
						!targets[pageId] && (targets[pageId] = []);
						!targetTasks[pageId] && (targetTasks[pageId] = []);
						$.each(v, function(taskId, vs){
							task = getTaskByTaskId(taskId);
							if(task){
								initTarget(vs, taskId, targets[pageId], targetTasks[pageId]);
								if(vs.length){
									if(taskWdataSet[pageId]){
										vs[0].taskWdataSet = taskWdataSet[pageId][taskId];
										vs[0].wdataSetId = taskWdataSet[pageId].wdataSetId;
									}
								}
								array.push({
									pageId : pageId,
									actTaskId : taskId,
									actTaskName : task.properties ? task.properties.name : '',
									bytes : vs
								});
							}							
						});
					}					
				});
				current = current || {};
				
				var plans = new Array(), c = {}, t = $.extend(true, {}, current);
				
				var pages = window.getPagesMsg();
				
				$.each(pages, function(i, v){
					t.variables = v.variables;
					t.name = current.name;
					t.pageName = v.name;
					t.targets = targets[v.id];
					t.targetTasks = targetTasks[v.id];
					t.listno = window.getPageIndex(v.id);
					t.href = v.href;
					t.terminal = v.terminal;
					plans.push({
						processDefId : current.processDefinitionId,
						pageId : v.id,
						pageName : v.name,
						bytes : JSON.stringify([t])
					});
				});
				
				var url = mtxx.contextPath + "/mx/form/formWorkflowPlan/batchSaveFormWorkflowPlan";
				
				var _callback_ = function(ret){
					window.parentDef.MSGID = ret.defId;
					parentDef.processName = current.name;
					parentDef.datas = array;
					pageFunc(parentDef);
					if(confirm("关闭窗口?")){
						window.close();
					}
				};
				
				if(array.length > 0){
					$.ajax({
						type : 'POST',
						dataType : 'JSON',
					//	async : false,
						data : {
							defId : window.parentDef.MSGID,
							jsonStr : JSON.stringify({
								rules : array,
								plans : plans
							})
						},
						url : url,
						success : _callback_
					});
				} else {
					//alert("没有设置任何节点!");
					if(window.confirm("未设置任何节点!确定保存空模版吗?")){
						$.ajax({
							type : 'POST',
							dataType : 'JSON',
							data : {
								defId : window.parentDef.MSGID,
								jsonStr : JSON.stringify({
									rules : array,
									plans : [{
										processDefId : current.processDefinitionId,
										bytes : JSON.stringify([t])
									}]
								})
							},
							url : url,
							success : _callback_
						});
					}
				}
				
			}
		},
		selectPage : function(){
			var url = mtxx.contextPath + '/mx/form/defined/ex/selectPage?' + $.param({
				targetId : null,
				fn : 'addPageGrid',
			});
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : false,
				title : "选择页面",
				closeBtn : [ 0, true ],
				shade : [ 0, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '1100px', '630px' ],
				iframe : {
					src : url
				}
			});
		},
		"paraType" : function(e){
			var target = e.target;
			!target.id && (target.id = "id_" + new Date().getTime());
			
			var $tg = $(this).closest('div[data-role="treelist"]');
			if(!$tg.attr("taskId")){
				alert('请选择流程节点!');
				return false;
			}
			
			var link = contextPath + '/mx/form/defined/param/outInParam?'
					+ $.param({
						eleId : e.target.id,
						flow : true,
						fn : "initInOutParameter"
					});
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : true,
				title : "输入&输出关系定义",
				closeBtn : [ 0, true ],
				shade : [ 0, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '980px', '500px' ],
				iframe : {
					src : link
				}
			});
		}
	};

function closeLayer() {
	layer.close(layer.getFrameIndex());
}
window.taskWdataSet = {};

function initInOutParameter(data){
	
	var $tg = $(this).closest('div[data-role="treelist"]');
	var pageId = $tg.attr("id");
	var taskId = $tg.attr("taskId");
	
	!!taskId && !taskWdataSet[pageId] //
		&& (taskWdataSet[pageId] = {});
	
	if(data){
		taskWdataSet[pageId][taskId] = data;
		if($tg.data(pageId)){
			var wdataSet = $tg.data(pageId).wdataSet;
			if(wdataSet){
				taskWdataSet[pageId].wdataSetId = wdataSet.wdataSetId;
			}
		}
		closeLayer();
	} else {
		var datas, menus = {
	    	'in' : [],
			out : [{
				text : '更新集参数',
				level : 0,
				items : []
			}]
		};
		if($tg.get(0)){
			if(datas = $tg.data(pageId)){
				datas.pageId = pageId,
				datas.title = $tg.attr("title");
				window.getInOutParameter(datas, menus);
			}
		}
		var datas = null;
		try {
			if(taskWdataSet[pageId] && taskWdataSet[pageId][taskId]){
				datas = taskWdataSet[pageId][taskId][0].datas;
			}
		} catch(e){
			console.log(e);
		}
		
		return {
			datas: datas || {},
			menus: menus
		}
	}
}

function getInOutParameter(data, menus){
	!!data.wdataSet && (typeof data.wdataSet == 'string') //
	&& (data.wdataSet = JSON.parse(data.wdataSet));
	var wd = data.wdataSet;
	(function(inObj){
		if(!data.rows)
			return;
		var obj = {
			id : data.pageId,
			text : data.title,
			items : []
		};
		inObj.push(obj);
		
		function addIn(i, v){
			if(v.t && v.dataRole){
				if(!dataRoleFilter(v.dataRole)){
					obj.items.push({
						id : v.t,
						text : v.name,
						items : window.getControlParams//
						(v.dataRole, v.t, data.pageId)
					});
				}
			}
		}
		
		$.each(data.rows, addIn);
	})(menus['in']);
	
	(function(outObj){
		if(!wd)
			return;
		var obj = {
				items : [],
				text : data.wdataSetName || ''
		};
		outObj.push(obj);
		function addOut(i, v){
			obj.items.push({
				text : v.text,
				id : v.dataColumnName,
				param : v.dataColumnName,
				title : v.text
			});
		}
		$.each(wd.columns, addOut);
	})(menus.out[0].items);
}



function refreshPages(){
	window.showSelectProperties({id : '0000'},{});
}

/**
 * 页面还原
 */
function initPageMsg(){
	if(mtxx.targetId){
		var json = pageFunc();
		if(json && json.MSGID){
			$.ajax({
				url : mtxx.contextPath + '/mx/form/formWorkflowPlan/getFormWorkflowPlanByDefId',
				data : {
					defId : json.MSGID
				},
				async : false,
				type : 'POST',
				dataType : 'JSON',
				success : function(ret){
					if(ret && ret.plans && ret.plans[0]){
						var plans = ret.plans, bytes = null, name, pageEle, tabs = [];
						$.each(plans, function(i, plan){
							try{
								bytes = JSON.parse(plan.bytes || JSON.stringify([{}]))[0];
							}catch(e){
								console.log(e);
							}
							if(i == 0 && bytes && bytes.id){
								selectProcessFunc(bytes);
							}
							pageEle = {
								id : plan.pageId,
								name : (bytes ? bytes.pageName : null),
								variables : (bytes ? bytes.variables : null),
								listno : (bytes ? bytes.listno : 0)*1,
								terminal : bytes.terminal,
								href : bytes.href
							};
							tabs.push(pageEle);
						});
						if(tabs.length){
							tabs.sort(function(a, b){
								return (a.listno - b.listno);
							});
							
							$.each(tabs, function(i, pageEle){
								window.addPageGrid(pageEle);
							});
							
							/**
							 * 
							 */
							HrefPage.init(tabs);
						}
						if(ret.rules && ret.rules.length){
							var p, t = null, pageId;
							$.each(ret.rules, function(i, rule){
								pageId = rule.pageId;
								!(p = pageProperties[pageId]) && (p = pageProperties[pageId] = {});
								try{
									t = p[rule.actTaskId] = bytes = JSON.parse(rule.bytes || JSON.stringify([{}]));
									if(t && t.length){
										if(t[0].taskWdataSet){
											!taskWdataSet[pageId] && (taskWdataSet[pageId] = {});
											taskWdataSet[pageId][rule.actTaskId] = t[0].taskWdataSet;
											taskWdataSet[pageId]["wdataSetId"] = t[0].wdataSetId;
										}
									}
								}catch(e){
									console.log(e);
								}
							});
						}
					} else {
						 buttons.selectProcess(null);
					}
				}
			});
		} else { //弹出流程选择页面
			 buttons.selectProcess(null);
		}
	}
}
$queue.add(initPageMsg);

function initVariablesGrid(){
	$("#variables").kendoGrid({ // 流程变量
		toolbar : '<div>流程参数</div>',
		editable : true,
		selectable: "multiple, row",
		columns : [{
			title : '流程定义参数',
			field : 'varname',
			template : function(dataItem){
				return $("<input/>", {
					class : 'k-textbox',
					name : 'fieldId',
					value : dataItem.varname,
					readonly : true
				}).css({
					width : "100%"
				}).outter();
			}
		},
		{
			title : '表单元素',
			field : 'fieldId',
			template : function(dataItem){
				return $("<input/>", {
					class : 'k-textbox',
					name : 'fieldId',
					value : dataItem.fieldId,
					ondblclick : "openExpress(this)",
					onchange : 'textboxOnchange(this)',
					't-uid' : dataItem.uid
				}).css({
					width : "100%"
				}).outter();
			}
		}],
		dataSource : []
	});
}
$queue.add(initVariablesGrid);

function initButtons(){
	
	
	function click(e){
		e.preventDefault();
		var t = $(this).attr("t");
		if(t && buttons[t]){
			return buttons[t].call(this, e);
		//	$(this).kendoButton({
		//		click : buttons[t]
		//	});
		}
		return false;
	}
	
	$(document).on("click.button.t", "button.k-button[t]", click);
	
	/*$(".k-button").each(function(i, v) {
		var t = $(this).attr("t");
		if(t){
			$(this).kendoButton({
				click : buttons[t]
			});
		}
	});*/
	
	$(document).on("change.check.all", ".check-all", function(e){
		var id = this.id, type = id.substring(6), checked = this.checked;
		$("input[name="+ type +"]").each(function(){
			this.checked = checked;
			clickCheckbox(this);
		});
	});
	
	$("#tabstrip-1").on("click", "li span.k-i-close", function(e){
		if(confirm("你确定删除该页面吗?")){
			var $li = $(e.target).closest("li");
			var $grid = window.getGridByIndex($li.index());
			if($grid){
				var id = $grid.attr("id");
				!!pageProperties[id] && (delete pageProperties[id]);	
			}
			var $nearby = $li.next().get(0) || $li.prev().get(0);
			$("#tabstrip-1").data("kendoTabStrip").remove($li).select($nearby);
		}
	});
}
$queue.add(initButtons);

function pageFunc(data){
	window.parent = window.opener || window.parent;
	var target = parent.document.getElementById(mtxx.targetId);
	if(data){
		 if(parent[mtxx.fn]){
			var datas = {
				data : data,
				rule : $(target).attr("dbrule")
			};
			return parent[mtxx.fn].call(target, datas);
		 }
	} else {
		if(parent[mtxx.fn]){
			var data = parent[mtxx.fn].call(target, datas);
			return window.parentDef = data;
		}
	}
}

function selectProcessFunc(data){
	current = data;
	if (data) {
		
		function goTo(){
			var url = mtxx.contextPath + "/workflow/diagram-viewer/view.html?processDefinitionId="+data.processDefinitionId + "&openType=defined";			
			$("#processimage").attr({
				src : url,
				onload:"iframeLoaded(this);"
			});
			closeLayer();
		}
		
		if(data.key){
			/**
			 * 获取最新版本
			 */
			$.post(mtxx.contextPath + "/mx/activiti/process/processDefinitionJson", {
				key : data.key,
				lastVersion : true
			}, function(ret){
				if(ret && ret.records){
					current = data = ret.records[0];
					goTo();
					console.log(current);
				}
			}, "JSON");
		} else {
			goTo();
		}
		
		
	}
}

/**
 * add tab
 */
function addPageGrid(data){
	if(data){
		window.addPageTab(data);
	} else {
		var pages = [];
		$.each(pageProperties, function(k, v){
			pages.push({
				id : k
			});
		});
		return pages;
	}
}

var $tabstrip_1 = $("#tabstrip-1");
function addPageTab(data){		
	
	var id = data.id, text = data.name;	
	
	var $tabstrip1 = $tabstrip_1.data('kendoTabStrip'),
	selector = "li:last", pageIndex = window.getPageIndex(id);
	if(pageIndex){
		selector = "li:eq(" + pageIndex + ")";
	} else {
		
		$tabstrip1.append({
			id : 'tab_' + id,
			encoded : false,
			text : "<a class='"+ id +"'>" + text +"</a> <span role='presentation' class='k-icon k-i-close'>Close</span>" ,
			content : $('<div>', {
				id : id,
				style : 'width:100%;height:99%'
			}).attr({
				title : text
			}).outter()
		});
	//	window.setTimeout(function(){
			var variables = [], map = {}, keys = ["t","varcode"];
			if(data && data.variables && data.variables.length){
				$.each(data.variables, function(i, v){
					//v.t && (map[v.t] = v);
					$.each(keys, function($i, k){
						v[k] && (map[v[k]] = v);//覆盖前面的，保留最新版本
					});
				});
				$.each(map, function(i, v){
					variables.push(v);
				});
			}
			window.initTreeGrid(id).data("variables", variables);
	//	}, 0);
	}
	$tabstrip1.select(selector);
	
}

/**
 * 初始化treelist
 */
function initTreeGrid(id){
	return jQuery("#" + id).kendoTreeList({
		toolbar : $("#toolbar-01").html(),
		editable : true,
		dataSource : new kendo.data.TreeListDataSource({	
			transport : {
				read : {
					type : "POST",
					dataType : "json",
					data : {pageId : id},
					url : mtxx.contextPath + "/mx/form/defined/getPageElementsById"
				},
				parameterMap : function(options, operation) {
					return options;
				}
			},
			schema : {
				data : 'rows',
				model : {
					fields : {
						id : {
							type : "number",
							nullable : false
						},
						parentId : {
							field : "pid",
							type : "number",
							nullable : true
						}
					},
					expanded : true
				},
				parse : function(ret){
					var $jq = jQuery("#" + id);
					$jq.data(id, ret);
					return ret;
				}
			}
		}),
		columns : [ {
			"field" : "name",
			"title" : "名称",
			"width" : "150px"
		}, {
			"field" : "read",
			"title" : "可读",
			"width" : "150px",
			headerTemplate: '<input type="checkbox" id="check-read" class="check-all" /><label for="check-read">可读</label>',
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'read');
			}
		}, {
			"field" : "write",
			"title" : "可写",
			"width" : "150px",
			headerTemplate: '<input type="checkbox" id="check-write" class="check-all" /><label for="check-write">可写</label>',
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'write');
			}
		}, {
			"field" : "show",
			"title" : "显示",
			"width" : "120px",
			headerTemplate: function(item){
				return '<input type="checkbox" id="check-show" class="check-all" /><label for="check-show">显示</label>';
			},
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'show');
			}
		}, {
			"field" : "target",
			"title" : "接收对象",
			"width" : "120px",
			headerTemplate: function(item){
				return '<input type="checkbox" id="check-target" class="check-all" /><label for="check-target">接收对象</label>';
			},
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'target');
			}
		}, {
			"field" : "targetTask",
			"title" : "指定节点",
			"width" : "120px",
			headerTemplate: function(item){
				return '<input type="checkbox" id="check-targetTask" class="check-all" /><label for="check-targetTask">指定节点</label>';
			},
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'targetTask');
			}
		
		}, {
			"field" : "defaultVal",
			"title" : "默认值",
			"width" : "150px",
			template : function(dataItem) {
				if (dataItem.parentId) {
					return $("<input/>", {
						class : 'k-textbox',
						name : 'defaultVal',
						value : dataItem.defaultVal,
						ondblclick : "openExpress(this)",
						onchange : 'textboxOnchange(this)',
						't-uid' : dataItem.uid
					}).css({
						width : "100%"
					}).outter();
				}
			}
		} ],
		selectable : true
	});
}

/**
 * 获取页面所在tab 序号
 */
function getPageIndex(id){
	var $a = $tabstrip_1.find("a." + id);
	if($a.get(0)){
		return $a.closest("li").index() + "";
	}
	return null;
}

/**
 * 获取各页面信息
 */
function getPagesMsg(){
	var $tabstrip1 = $tabstrip_1.data('kendoTabStrip');
	var items = $tabstrip1.items(), pages = [];
	if(items && items.length){
		$.each(items, function(i, v){
			var msg = window.getPageMsg(i);
			pages.push(msg);
		});
	}
	return pages;
}

/**
 * 获取指定页面信息
 * @param index
 * @returns
 */
function getPageMsg(index){
	var $this = window.getGridByIndex(index);	
	
	var terminal = $this.find("select[name=terminal]").val(), //
		_href = $this.find("select[name=k-href]").val();
	
	var page = {
		id : $this.attr("id"),
		name : $this.attr("title"),
		variables : $this.data("variables"),
		terminal : terminal,
		href : _href
	};
	return page;
}

function getContent(index){
	return $tabstrip_1.data('kendoTabStrip').contentElement(index);
}

function getSelectedContent(){
	var ts = $tabstrip_1.data('kendoTabStrip');
	var $li = ts.select();
	if($li.get(0)){
		return window.getContent($li.index());
	}
	return null;
}

function getGridByIndex(index){
	return window.getSelectedGrid(window.getContent(index));
}

function getSelectedGrid(content){
	return $(content || window.getSelectedContent()).find("div[data-role=treelist]");
}

function setVariablesData(variables, isInit){
	var $var = $("#variables");
	if($var.get(0) && $var.data("kendoGrid")){
		var tmp = {};
		
		if(isInit){
			var data = $("div[data-role=treelist]:eq(0)").data("variables");
			if(data && data.length){
				variables = data;
			}
		}
		
		$.each(window.globalVariables, function(i, v){
			tmp[v.varcode] = v;
		});
		
		if(variables && variables.length){
			$.each(variables, function(i, v){
				tmp[v.varcode] && $.extend(true, tmp[v.varcode] = {}, v);
			});
		}
		
		var variables_ = [];
		$.each(tmp, function(i, v){
			variables_.push(v);
		});
		
		$var.data("kendoGrid").dataSource.data(variables_);
	}
}

var $ztree, globalVariables = null;//流程定义全局变量
function iframeLoaded(iframe){
	var docs = $(iframe).contents(),p = {
		id : 'lqbz',
		name : $(".processed-name",docs).val() || '流程'
	};
	globalVariables = new Array();
	window.setTimeout(function(){
		var pdg = iframe.contentWindow.ProcessDiagramGenerator;
		window.processDiagram = pdg.processDiagrams[current.processDefinitionId];
		window.pd = processDiagram.processDefinitionDiagramLayout.processDefinition;
		var ds = getProcessed(pdg);
		if(ds){	
			p = {
					id : pd.id,
					name : pd.name
			};
			var datas = new Array(p);
			$.each(ds,function(i,o){
				datas.push({
					id : o.activityId,
					name : o.properties.name || '',
					parentId : p.id
				});
			});
			
			var defTmp = current.processDefinitionId.split(":")[0];
			window.ajaxGetElementSet(defTmp,function(str){//获取流程定义里面的全局变量
				if(str){
					var p = JSON.parse(str);
					if(p.propertyPackages && p.propertyPackages){
						$.each(p.propertyPackages,function(i,v){
							if(v.id == "varprop"){
								var variableProp = new Object();
								if(current.variables && current.variables[0]){
									$.each(current.variables,function(i,vv){
										variableProp[vv.varcode] = vv;
									});
								}
								var tmpVariables = [];
								$.each(v.properties,function(index, obj){
									globalVariables.push($.extend({}, obj.value));
									if(obj.value){
										if(obj.value.varcode in variableProp){
											$.extend(obj.value, variableProp[obj.value.varcode]);
										}
									}
									tmpVariables.push(obj.value);
								});
								window.setVariablesData(tmpVariables, true);
							}
						});
					}
				}
			});			
//			window.processDiagram = pdg.processDiagrams[current.processDefinitionId];
//			window.pd = processDiagram.processDefinitionDiagramLayout.processDefinition;
//			p = {
//				id : pd.id,
//				name : pd.name
//			};
//			var datas = new Array(p);
//			$.each(ds,function(i,o){
//				datas.push({
//					id : o.activityId,
//					name : o.properties.name || '',
//					parentId : p.id
//				});
//			});
		}
	}, 2000); //延迟一秒，确保iframe 加载完
}

function getTaskByTaskId(taskId){
	function getTask(activities){
		return activities[taskId];
	}	
	if(!window.activities && window.processDiagram){
		if(processDiagram.processDefinitionDiagramLayout){
			window.activities = processDiagram.processDefinitionDiagramLayout.activities;
		}
	}
	return getTask(window.activities || {});
}

var currentId = null;
var current = null;
function ajaxGetElementSet(elementId,fn){
	$.ajax({
		url : mtxx.contextPath + "/service/workflow/flow/elemsetview",
		data : {
			"processDefinitionId" : current.processDefinitionId,
			"resourceId" : elementId
		},
		type : 'post',
		async : true,
		success:function(ret){
			if(ret && fn)
				fn(ret);
		}
	});
}

var activities = {};//每个节点
function getProcessed(processDiagramGenerator){
	var processDiagrams = processDiagramGenerator.processDiagrams;
	if(processDiagrams && processDiagrams[current.processDefinitionId]){
		var processDiagram = processDiagrams[current.processDefinitionId];
		if(processDiagram){
			var nodes = processDiagram.processDefinitionDiagramLayout.activities;
			if(nodes){
				$.each(nodes,function(i,o){
					o.children = new Array();
					activities[o.activityId] = o;
				});
			}			
			var sequenceFlows = processDiagram.processDefinitionDiagramLayout.sequenceFlows;
			if(sequenceFlows && sequenceFlows[0]){
				$.each(sequenceFlows,function(i,v){
					var activiti = activities[v.sourceActivityId];
					if(activiti){
						activiti.children.push(v);
					}
				});
			}
			return nodes;
		}
	}
	return null;
}

/**
 ** 点击流程节点，显示属性配置
 */
var currentId = null, pageProperties = {};
var currentContextObject;
function showSelectProperties(o, properties) {
	o = o || {};
	var id = o.id, $grid = window.getSelectedGrid().data('kendoTreeList');
	if(!$grid){
		return false;
	}
	var data = $grid.dataSource.data(), props;
	if (currentId && (currentId !== id)) {
		props = [];
		$.each(data, function(k, v) {
			if (!v.hasChildren) {
				var obj = {};
				$.each([ "t", "rid", "write", "show", "read", "defaultVal",//
				         "datas", "target", "container", "targetTask"],
					function(i, field) {
					obj[field] = v[field];
				});
				props.push(obj);
			}
		});
		properties[currentId] = props;
	}
	currentId = id;
	currentContextObject = o;
	var p = properties[currentId], datas = [];
	if (p) {
		var tmp = {}, e, ele, rst;
		$.each(p, function(i, v) {
			tmp[v.t] = v;
		});
		$.each(data, function(i, v) {
			v.uid = null;
			v.datas = null;
			ele = {};
			e = null;
			rst = (!v.hasChildren && !!(e = tmp[v.t]));
			
			datas.push($.extend(ele, v));
			if(e == null){
				$.each(["target", "targetTask"], function($i, k){
					ele[k] = false; 
				});
			}
			
			rst && ($.extend(ele, e));
		});
	} else {
		$.each(data, function(k, v) {
			v.uid = null;
			v.datas = null;
			if (!v.hasChildren) {
				$.extend(v, {
					write : true,
					show : true,
					read : true,
					defaultVal : '',
					targetTask : false, //接收人
					target : false //接收人
				});				
			}
			datas.push(v);
		});
		properties[currentId] = datas;
	}
	$grid.dataSource.data(datas);
}

/**
 * 点击流程图节点回调函数
 * @param canvas
 * @param element
 * @param contextObject
 * @returns
 */
function openPropertiesView(canvas, element, contextObject){
	var $content = $(window.getSelectedContent());
	if($content.get(0)){
		if(contextObject && contextObject.properties){
			var $span = $content.find("div.k-toolbar span.k-span");
			$span.text("[" + (contextObject.properties.name || '') + "]");
		}
		var $grid = window.getSelectedGrid($content);
		if($grid.get(0)){
			var item = null, key = "tmpKey";
			!(item = $grid.data(key)) && ($grid.data(key, item = {id : $grid.attr("id")}));
			window.populateProperties(item, contextObject);
			contextObject && $grid.attr("taskId", contextObject.id);
		}
	}
}

/**
 * 属性权限赋值
 * @param item
 * @param contextObject
 * @returns
 */
function populateProperties(item, contextObject){
	if(item){
		var pageId = item.id;
		if(!item.properties){
			!pageProperties[pageId] && (pageProperties[pageId] = {});
			item.properties = pageProperties[pageId];
		}
		window.showSelectProperties(contextObject, item.properties);
	}
}

$.log = function(msg){
	return console.log(msg || '');
};
function closeLayer(){
	layer.close(layer.getFrameIndex());
}

function checkboxTemplate(dataItem, key) {
	if (dataItem.parentId) {
		var opt = {
			type : 'checkbox',
			onclick : "clickCheckbox(this)",
			name : key,
			't-uid' : dataItem.uid
		}, t;
		if ((t = dataItem[key]) == true || t == 'true'){
			opt.checked = true;
		}
		return $("<input>", opt).outter();
	}
	return null;
}

function clickCheckbox(o) {
	set(o, o.checked);
}

function textboxOnchange(o) {
	return set(o, o.value);
}

function getGrid(jq){
	return jq.closest("[data-role=grid]").data("kendoGrid") || jq.closest("[data-role=treelist]").data("kendoTreeList");
}

function set(o,value){
	var $this = $(o), name = $this.attr("name");
	var grid = getGrid($this) || $("#variables").data("kendoGrid");
	if(grid){
		var dataItem = grid.dataSource.getByUid($this.attr("t-uid"));
		if(dataItem){
			dataItem.set(name, value);
		}
	}
	return dataItem;
}

var expTarget = null;
function openExpress(o) {
	expTarget = o;
	var expURL = mtxx.contextPath + "/mx/expression/defined/view?" + $.param({
		retFn : "setRowField",
		getPageAttrFn : "getPageAttrFn",
		initExpFn : "getinitExp",
		category : 'form',
		notValidate : true
	});
	window.open(expURL);
}

function getinitExp(){
	if(expTarget){
	//	var $this = $(expTarget);
		var uid = $(expTarget).attr("t-uid");
		var grid = getGrid($(expTarget));
		var dataItem = grid.dataSource.getByUid(uid);
		if(dataItem && dataItem.datas){
			return dataItem.datas;
		}
	}
	return null;
}

function getPageAttrFn(){
	
	var $grid = window.getSelectedGrid();
	
	var id = $grid.attr("id");
	var datas = [{
		id : id,
		pId : null,
		name : $grid.attr("title")
	}], parents = {};
	
	$.each($grid.data('kendoTreeList').dataSource.data(),function(i,v){
		if(!v.pname && !v.hasChildren){
			return true;
		}
		var obj = {
			id : v.id,
			pId : v.parentId,
			name : v.name	
		};
		if(!v.parentId){
			parents[v.id] = v;
			if(obj.pId == undefined)
				obj.pId = id;
		} else {
			var p = parents[v.parentId];
			if(p){
				obj.dType = 'string';
				obj.code = "~M{" + v.t + "}";
				obj.value = "~M{" + p.name + "_" + v.name + "}";
			}
		}
		
		if(!v.hasChildren && !v.isParent){
			obj.dType = 'string';
			obj.code = "~M{" + v.t + "}";
			obj.value = "~M{" + v.name + "}";
		}
		datas.push(obj);
	});
	parents = null;
	return datas;
}

function setRowField(data){
	if(data){
		expTarget.value = data.expVal;
		var dataItem = textboxOnchange(expTarget);
		if(dataItem){
			dataItem.set("datas",data);
			var $grid =  window.getSelectedGrid(), key = "variables";
			if($grid){
				var item = $grid.data(key);
				!item && ($grid.data(key, item = []));
				window.pushByCover(item, dataItem, "varcode");
			}
		}
	}
}

function pushByCover(c, o, f){
	if(c && o && f){
		var ret = true;
		$.each(c, function(i, v){
			var t0 = v[f], t1 = o[f];
			if(t0 && t1 && (t0 === t1)){
				c[i] = o;
				return (ret = false);
			}
		});
		if(ret){
			c.push(o);
		}
	}
}

function contains(collection, field){
	var ret = false;
	if(collection && field){
		var o , len = collection.length, i = 0;
		for(; i < len; i ++){
			o = collection[0];
			if(o[field]){
				ret = true;
				break;
			}
		}
	}
	return ret;
}

/**
 * 从属页面下拉
 */
var HrefPage = (function(){
	var func = function(){};
	var o = "<option value='{0}'>{1}</option>";
	
	func.init = function(data){
		if(!data || !data.length){
			return;
		}
		
		var grid = null,//
			SB = new StringBuffer("<option value=''>---选择---</option>");
		$.each(data, function(i, v){
			SB.appendFormat(o, v.id, v.name);
			
			grid = $("#" + v.id);
			grid.find("select[name=terminal]").data("value", v.terminal).val(v.terminal);
			v.href && grid.find("select[name=k-href]").data("value", v.href).val(v.href);
		});
		
		SB = SB.toString();
		
		$("select[name=k-href]").each(function(){
			var _this = $(this).empty().append(SB);
			var value = _this.val() || _this.data("value");
			!!value && _this.val(value);
		});
	};
	
	return func;
})();

$.fn.outer = $.fn.outter = function(){
	return this[0] ? this[0].outerHTML : '';
};
