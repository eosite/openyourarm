
$.fn.outter = function(){
	return $("<div>").append($(this).clone(true)).html();
};

/**
 * 定义平台控件分组
 */

/**
 * 布局 start
 */
var $tabstrip = $(".tabstrip"),$vertical = $("#vertical");
$vertical.css({
	height : $(window).height()
}).kendoSplitter({
	orientation: "vertical",
	panes: [
		{ collapsible: false , resizable : false, scrollable: false ,size : '32px'},
		{ collapsible: true, resizable : true, scrollable: true}
	],
	layoutChange : onResize
});

function onResize(e){
	
	$vertical.css({
		height : $(window).height()
	});
	
	$tabstrip.css({
		height : $tabstrip.closest('div[role=group]').height()*0.98
	});
	
}

$tabstrip.each(function(i,item){
	var $this = $(this);
	mtxx[$this.attr('id')] = $this.kendoTabStrip({
		animation:  {
			open: {
				effects: "fadeIn"
			}
		},
		select : function(e){
			var dd = $(this.wrapper),$contentElement = $('#' + e.contentElement.id);
			var index = $(e.item).index();
			$contentElement.height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
			var rowIndex = null, tableMsg;
			
			function eachDatas(collection, obj){
				if(collection && collection.length){
					$.each(collection, function(i, v){
						obj[v.id] = i;
					});
				}
			}
			if(index == 0){
				
				
				/**
				 * 同步视图中的卡片到列表当中
				 */
				var $availableColTb = $("#availableColTb").data("kendoGrid");
				$availableColTb.clearSelection();
				var datas = $availableColTb.dataSource.data(), dataObj = {};
				var func = new typeFuncs("sortedColTb");
				eachDatas(datas, dataObj);
				
				$(".view-tr-cls").each(function(){
					rowIndex = $(this).attr("row-index");
					var o = $(".append-sort-" + rowIndex),//
					tableMsg = $(this).data("tableMsg");
					
					function each(fn){
						if(tableMsg.linkageControl && //
								tableMsg.linkageControl.length)
							$.each(tableMsg.linkageControl, fn);
					}
					
					if(!o.get(0)){
						each(function(i, v){
							if(v.id in dataObj){
								$availableColTb.select//
								("tbody tr:eq(" + dataObj[v.id] + ")");
							}
						});
						mtxx.sort.append(rowIndex);
					} else { //交换元素
						var $grid = o.find("[data-role=grid]").data("kendoGrid");
						$grid.clearSelection();
						var datas1 = $grid.dataSource.data(), dataObj1 = {};
						
						var jian = [], plus = [];
						
						eachDatas(datas1, dataObj1);
						
						each(function(i, v){
							if(!(v.id in dataObj1)){//增加控件到卡片
								$availableColTb.select//
								("tbody tr:eq(" + dataObj[v.id] + ")");
								plus.push(v);
							} else {
								jian.push(v);
							}
						});
						if(plus.length){
							func.right($grid);
						}
						if(jian.length){
							datas1 = $grid.dataSource.data(), dataObj1 = {}, jian = [];
							eachDatas(tableMsg.linkageControl, dataObj1);
							
							$.each(datas1, function(i, v){
								if(!(v.id in dataObj1)){//增加控件到卡片
									$grid.select//
									("tbody tr:eq(" + i + ")");
									jian.push(v);
								}
							});
							if(jian.length)
								func.left($grid);
						}
						
					}
				});
			} else if(index >= 1){
				if($this.data("tr")){
					viewSelected($this.data("tr"));
				}
				var last = $this.data("last");
				
				if(last != undefined && last == 0){					
					$(".sort-tr-cls").each(function(){
						var rowIndex = $(this).attr("row-index");
						var o = $(".view-sort-" + rowIndex),//
						tableMsg = o.data("tableMsg");
						if(!o.get(0)){  //不存在时新增
							var $grid = $(this).find("[data-role=grid]").data("kendoGrid");
						//	$grid.clearSelection();
							var datas1 = $grid.dataSource.data(), linkageControl = [];
							var v = {};
							$.each(datas1, function(){
								linkageControl.push($.extend({}, this));
							});
							var $tr = mtxx.Viewsort.append(rowIndex);
							$tr.data("tableMsg", {
								linkageControl : linkageControl
							});
							
							
							$tr.find(".k-isbatch")[0].checked = !!v.batch;
							
							var drop = $tr.data("k-dropdownlist");
							
							drop.value(v.topTableName);
							
							tabstripAppend($tr);
							
							graySelected($tr);
							
						} else { //存在时检查
							var $grid = $(this).find("[data-role=grid]").data("kendoGrid");
							var datas = $grid.dataSource.data(), linkageControl = [], dataObj = {};
							eachDatas(datas, dataObj);
							if(tableMsg){
								var viewDatas = tableMsg.linkageControl, viewObj = {};
								eachDatas(viewDatas, viewObj);
								var removes = [], plus = [];
								$.each(viewDatas, function(i, v){
									if(!(v.id in dataObj)){//删除
										removes.push(v);
									}
								});
								
								viewDatas.remove(removes, function(a, b){
									return a.id == b.id;
								});
								
								$.each(datas, function(i, v){
									if(!(v.id in viewObj)){//增加
										viewDatas.push(v);
									}
								});
							}
							
						}
					});
				}
			} else if(index > 1){
				$this.data("tr", $("#" + $(e.item).data('tr')));
				refleshFrame($contentElement.find("iframe"));
			}
			
			$this.data("last", index);
		},
		show : function(e){
			var $ce = $(e.contentElement);
			var $f = $ce.find("iframe.tab-sort");
			if($f.get(0)){
				$f.attr({
					src : $f.attr("src_"),
					onload:"FrameLoaded(this);"
				})
			}
		}
	});
});

Array.prototype.remove = function(eles, fn){
	if(!this.length || !eles)
		return;
	if(!(eles instanceof Array)){
		eles = [eles];
	}
	var i, o, index, v, tmp = [];
	for(i = 0; i < this.length; i ++){
		o = this[i];
		for(index = 0; index < eles.length; index ++){
			v = eles[index];
			if(fn){
				if(fn(o, v)){
					o = undefined;
					break;
				}
				
			} else if(o == v){
				o = undefined;
				break;
			}
		}
		tmp.push(o);
	}
	this.splice(0, this.length);
	for(i = 0; i < tmp.length; i ++){
		o = tmp[i];
		if(o !== undefined){
			this.push(o);
		}
	}
};

function refleshFrame(frame){
	setTimeout(function(){
		frame.attr({
			src : frame.attr("src")
		});
	}, 100);
}

/**
 * 布局 end
 */


/**
 * 页面初始化
 */
$(function() {
	
	var $p = mtxx.parent,data = [];
	if(!mtxx.fn || !mtxx.targetId){
		alert('页面参数不对!');
		return false;
	} 
	mtxx.frame = $("#iframe-1");
	mtxx.sort = new appendSort($("#sort-table"));
	mtxx.Viewsort = new viewAppend($("#view-table-1"));
	var availableColTbDatas = new Array();
	mtxx["availableColTb"] = $("#availableColTb").kendoGrid({
		toolbar : kendo.template("<button class='k-button' id='colTb-toolbar-create'>增加分组</button>"),
		dataSource : availableColTbDatas,
		columns : [ {
			field : 'text',
			title : '名称'
		} ],
		selectable:"multiple, row",
		height:'80%'
	}).data("kendoGrid");
	
	var func = $p[mtxx.fn],parentMsg = null,
	target = $p.document.getElementById(mtxx.targetId);
	if(func){
		parentMsg = func.call(target);
	} else if (target){
		parentMsg = target.value;
	}
	if(parentMsg){
		var linkageControl = parentMsg.linkageControl,
		tableMsg = parentMsg.tableMsg;
		mtxx.pageId = parentMsg.pageId;
		mtxx.rid = parentMsg.rid;
		mtxx.linkageControlObj = {};
		if(linkageControl){
			linkageControl = JSON.parse(linkageControl);
			$.each(linkageControl,function(i,v){
				mtxx.linkageControlObj[v.id] = v;
			});
			data = linkageControl;
		}
		if(tableMsg){
			tableMsg = JSON.parse(tableMsg);
			if(tableMsg && tableMsg[0]){
				mtxx.tmpdata = {};
				tableMsg.sort(function(a,b){
					return b.index - a.index;//排序
				});
				
				mtxx.tableMsgTmp = tableMsg;
				
				
				
				/*$.each(tableMsg,function(i,v){
					var $tr = mtxx.sort.append();
					$.each(v.columns,function(index,item){
						if(mtxx.linkageControlObj[item.id]){
							item.text = mtxx.linkageControlObj[item.id].text;
						}
						mtxx.tmpdata[item.id] = item;
					});
					
					setGridValues($tr.find("[data-role=grid]"),v);
					
					$tr.find('.sortedColTb').data("dataSetId",v.dataSetId);
				});	*/							
			}
		}
	}			
	
	
	if(mtxx.tmpdata){
		$.each(data,function(i,v){
			if(!mtxx.tmpdata[v.id])
				availableColTbDatas.push(v);
		});
	} else {
		availableColTbDatas = data || [];
	}
	
	setAvailableColTbDataSource(availableColTbDatas);
	
	$("#colTb-toolbar-create").kendoButton({
		click : function(e){
			mtxx.sort.append(++index);
		}
	});
	
	$("#sort_submit_btn").kendoButton({
		click : function(e){
			
			var func = mtxx.parent[mtxx.fn],target = mtxx.parent.document.getElementById(mtxx.targetId);
			//var $sorts = $(".sortedColTb"),$this,tableMsg;
			var $sorts = $(".view-tr-cls"),$this,tableMsg;
			if($sorts.length > 0){
				
				var values = new Array(),rst = true, tempCollection = new Object();
				$sorts.each(function(i,v){
					$this = $(this);
					tableMsg = $this.data(key);
					if(!tableMsg || !tableMsg[key]){
						alert("请关联第(" + (i + 1) + ")组数据!");
						rst = false;
					} else {
						var tableMsgArr = JSON.parse(tableMsg[key]);
						var obj = tableMsgArr[0];
						if(obj){
							var columns = new Array();
							$.each(obj.columns,function(i,v){
								columns.push(v);
							});
							
							
							obj.table.isMaster = $this.find('.k-chk').is(":checked");
							
							 if(tableMsg.topTableName){
								 var master = tempCollection[tableMsg.topTableName];
								 if(!master){
									 master = tempCollection[tableMsg.topTableName] = {
											 children : new Array(),
											 isMaster : true
									 };
								 }
								 master.children.push(obj.table.tableName);
							 }
							
							values.push({
								index : i,
								dataSetId : $this.data("dataSetId"),
								name : obj.table.name,
								table : obj.table,
								columns : columns,
								batchRules : tableMsg.batchRules,
								fields : tableMsg.fields,
								types : tableMsg.types,
								linkageControl : tableMsg.linkageControl,
								batch : tableMsg.batch,
								topTableName : tableMsg.topTableName,
								wdataSet : obj.wdataSet
							});
						}
					}
				});
				
				
				var tmpTableName = null, dataSetId_ = null, tmpTables = {};
				
				$.each(values,function(i, tmp){
					var tableName = tmp.table.tableName;
					if(tableName in tempCollection){
						$.extend(tmp.table, tempCollection[tableName]);
						tmpTableName = tableName;
						dataSetId_ = tmp.dataSetId;
					}
					if(values.length == 1){
						tmpTableName = tableName;
						dataSetId_ = tmp.dataSetId;
					}
					tmpTables["[" + tableName + "]"] = tmp;
				});
				
					$.ajax({
						url : mtxx.contextPath + '/mx/dataset/json',
						type : "post",
						data : {
							titleLike : tmpTableName
						},
						dataType : "json",
						success : function(ret){
							var cover = false, reg = new RegExp('\\[(.+?)\\]',"g");;
							if(ret && ret.rows.length > 0){
								if(!dataSetId_ && confirm("当前设置已存在数据集，是否覆盖?")){
									cover = true;
									$.each(ret.rows, function(i, v){
										var name = v.name;
										if(name){
											try {
												name = name.match(reg);
												if(name && (name = name[0])){
													tmpTables[name] && //
													(tmpTables[name].dataSetId = v.id);
												}
											} catch(e){
												console.log(e);
											}
										}
									});
								} else if(dataSetId_){
									cover = true;
								}
							}
							if(!cover){
								$.each(values, function(){
									this.dataSetId = null;
								});
							}
							
							values = createUpdateSource(values, cover);
							if(rst && mtxx.parent){
								returnValues(values);
							}
						}
					});
				
				} else {
					if(confirm('未添加分组信息,确认将清空数据?')){						
						var values = [{}];
						if(func){
							func.call(target,values);
						} else if (target){
							target.value = JSON.stringify(values);
						}
						window.close();
					}
				}
		}
	});
	
	mtxx["tabstrip-1"].data('kendoTabStrip').select("li:last");
	
	mtxx.frame.attr({
		src : mtxx.contextPath + "/mx/form/defined/getFormPageHtmlById?id=" + mtxx.pageId,
		type : 'viewSort',
		onload:"FrameLoaded(this);"
	});
	
});

/**
 * 返回结果
 * @param values
 * @returns
 */
function returnValues(values){
	var func = mtxx.parent[mtxx.fn],//
	target = mtxx.parent.document.getElementById(mtxx.targetId);
	if(func){
		func.call(target,values);
	} else if (target){
		target.value = JSON.stringify(values);
	}
	window.close();
}

/**
 * 定义页面 
 * 
 * @param text
 * @param id
 */
var index = 0, tableMgrUrl = "/mx/form/defined/ex/tableMgr?";
if(mtxx.isPage == 'true'){
	tableMgrUrl = "/mx/dep/base/depBaseWdataSet?view=/dep/base/depBaseWdataSet/sqlcrud&";
}
function tabstripAppend(tr){
	text = '分组-' + (++ index);
	var $tr = tr || mtxx.Viewsort.append(index);
	$tr.find(".k-span").text(text);
	
	//$tr.addClass("view-sort-" + index).attr("row-index", index);
	
	id = "frame_" + $tr.attr("id");
	var $tabstrip1 = mtxx["tabstrip-1"].data('kendoTabStrip'),selector = "li:last";
	var $frame = jQuery('<iframe>', {
		frameborder : 0,
		scrolling : 'auto',
		id : id,
		class : 'tab-sort',
		style : 'width:100%;height:100%'
	}).attr({
		src_ : mtxx.contextPath + tableMgrUrl + $.param({
			fn : 'viewSourceSetFunc',
			targetId : $tr.attr("id"),
			isBind : true
		}),
		type : 'sort'
		//onload:"FrameLoaded(this);"
	});
	
	$tabstrip1.append({
		text : text,
		content : $frame.outter()
	});
	//var sel = $tabstrip1.select(selector);
	var tableMsg = $tr.data(key) || {};
	var li = mtxx["tabstrip-1"].find(selector);
	li.data("tr",$tr.attr("id"));
	tableMsg.li = li.index();
	tableMsg.frameId = id;
	
	return $tr;
}

function setAvailableColTbDataSource(datas){
	mtxx["availableColTb"].setDataSource(new kendo.data.DataSource({
		data : datas
	}));
}

function FrameLoaded(o){
	var fl = new FrameLoadedFunc(o);
	
	fl.exec();

	if(o.id == "iframe-1")
		window.setAvailableColTbDataSource(fl.linkageControl);
}

function FrameLoadedFunc(o){
	
	this.linkageControl = [];
	this.o = $(o);
	
	(function(that){
		that.o.contents().find("[data-role]")//
		.each(function(){
			var $this = $(this);
			if(dataRoleFilter($this.attr("data-role"))){
				return true;
			}
			that.linkageControl.push({
				id : $this.attr("id"),
				text : $this.attr("cname") ||//
				$this.attr("title") || $this.attr("name")
			});
		});
		
	})(this);
	
}

FrameLoadedFunc.prototype = {
	constructor : FrameLoadedFunc,
	
	exec : function(){
		var type = this.o.attr("type");
		if(type in this){
			return this[type]();
		}
	},
	sort : function(){
		var that = this;
	},
	viewSort : function(){
		var that = this;
		var doc = that.o.contents();
		/**
		 * 右键菜单 start
		 */
		var lis = [
			"<li onclick='tabstripAppend();' >添加分组</li>",
			"<li onclick='removeSort();' >从分组中移除</li>",
			"<li id='addsort_id' >添加到</li>"
		];
		
		var indexArray;
		var $context = $("<ul>",{
			id : 'context-menu'
		}).append(lis.join('')).appendTo(doc.find('body')).kendoContextMenu({
		    target: doc.find("body") ,
		    alignToAnchor: true,
		    open : function(e){
		    	$context.remove("#addsort_id li");
		    	var items = new Array();
		    	indexArray = new Array();
		    	$(".view-tr-cls").each(function(i){
		    		var $this = $(this);
		    		items.push({
		    			text : $this.find('.k-span').text()
		    		});
		    		indexArray.push({
		    			trId : $this.attr('id'),
		    			text : $this.find('.k-span').text(),
		    			level : -1,
		    			click : function(e){
		    				addSelected($this,true);
		    			}
		    		});
		    	});
		    	if(items.length > 0){
		    		$context.append(items,"#addsort_id");
		    	}
		    },
		    select : function(e){
		    	var $item = $(e.item),$parent = $item.closest("ul").closest("li");
		    	if($parent[0]){
		    		if($parent.attr("id") == 'addsort_id'){
		    			var index = $item.index();
		    			var options = indexArray[index];
		    			if(options && options.click){
		    				options.click(e);
		    			}
		    		}
		    	}
		    }
		}).data("kendoContextMenu");
		/**
		 * 右键菜单 end
		 */
		
		divEvents.document = doc;
		
		doc.on('mousedown',function(e){
			divEvents.bindMouseDown(e);
		}).data('context-menu',$context);
		
		
		if(mtxx.tableMsgTmp){//还原页面
			$.each(mtxx.tableMsgTmp,function(i,v){
				var d = getRequiredData(v);
				var $tr = mtxx.Viewsort.append(++index);
				$tr.data(key,{
					
					tableMsg : JSON.stringify([/*{
						name : v.name,
						table : v.table,
						columns : v.columns
					}*/d]),
					fields : v.fields,
					types : v.types,
					batch : v.batch,
					batchRules : v.batchRules,
					linkageControl : v.linkageControl || v.columns,
					topTableName : v.topTableName
				}).data("dataSetId",v.dataSetId);
				
				$tr.find(".k-isbatch")[0].checked = !!v.batch;
				
				var drop = $tr.data("k-dropdownlist");
				
				drop.value(v.topTableName);
				
				tabstripAppend($tr);
				
				graySelected($tr);
			});	
		}
		
	}
};

function removeSort(addChild){
	//console.log(addChild);
	mtxx.frame.contents().find('.selected-cls').each(function(){
		var $this = $(this),$tr = $this.data('parent'),id = $this.attr("id"),
		isSelected = $this.hasClass("isSelected");
		if($tr && isSelected){
			var tableMsg = $tr.data(key);
			for(var i = 0; i < tableMsg.linkageControl.length; i ++){
				if(id == tableMsg.linkageControl[i].id){
					tableMsg.linkageControl.splice(i,1);
					break;
				}
			}
			if(!addChild)
				new selectedFunc($this).removeAll();
		}
	});
}


function setViewValues(jq,data){
	var d = {
		name : data.table.name,
		table : data.table,
		columns : data.columns,
		wdataSet : data.wdataSet
	};
	
	
	d = getRequiredData(data);
	
	var tableMsg = jq.data(key);
	
	tableMsg[key] = JSON.stringify([d]);
	
	if(tableMsg.batch){
		
		tableMsg.topTableName = jq.data("k-dropdownlist").value();
		
		if(data.columns && data.columns.length){
			var cId,tableName = data.table.tableName;
			var obj = {
				fields : {},
				inobj : {}
			};
			$.each(data.columns,function(i,v){
				obj.fields[v.id] = v;
			});
			
			var fields = tableMsg.fields,typeField;
			if(fields && fields.length){
				$.each(fields,function(i,v){
					if(v.id in obj.fields){
						obj.inobj["index" + v.index] = obj.fields[v.id];
						if(v.isType){
							typeField = obj.fields[v.id].fieldName;
						}
					}
				});
			}
			var types = tableMsg.types;
			if(types && types.length){
				$.each(types,function(i,v){
					var fieldIds = v.fieldIds;
					if(fieldIds){
						$.each(fieldIds,function(index,val){
							var key = "index" + val.index,f = obj.inobj[key];
							if(f){
								val.columnName = f.fieldName;
								val.name = f.text;
								
							}
							if(!cId)
								cId = val.id;
						});
					}
				});
			}
			
			var Msg = {
					cId : cId,
					tableName : tableName,
					topTableName : tableMsg.topTableName,
					typeField : typeField,
					tableMsg : types
			};
			
			tableMsg.batchRules = Msg;
		}
		
	}
}

function viewSourceSetFunc(data){
	
	var $tr = $(this);
	if(data){
		
		if(!data.columns && data.tableMsg){
			var wdataSet = data.wdataSet;
			var arr = JSON.parse(data.tableMsg);
			data = arr[0];
			data.wdataSet = wdataSet;
		}
		
		setViewValues($tr,data);
		
		mtxx["tabstrip-1"].data('kendoTabStrip').select("li:eq(1)");
	} else {
		var json = $tr.data(key) || {};
		var linkageControl = json.batch ? json.fields : ( json.linkageControl ||  new Array());
		var ctrls = [];
		if(linkageControl && linkageControl.length){
			$.each(linkageControl, function(i, v){
				if(!v.text || !v.id){
					//linkageControl.splice(i, 1);
				} else {
					ctrls.push(v);
				}
			});
		}
		
		var tableMsg = json[key];
		try {
			tableMsg = JSON.parse(tableMsg)[0];
		} catch(e){
			console.log(e);
			tableMsg = {};
		}
		tableMsg.dataSetId = $tr.data("dataSetId");
		var ret = {
				wdataSet : tableMsg.wdataSet,
				tableMsg : JSON.stringify([tableMsg]),
				linkageControl:JSON.stringify(ctrls)
			};
		return ret;
	}
}

function viewAppend(jq){
	var viewTemplate = [
	    "<tr>",
			"<td>",
				"<table class='bordered' style='width:200px;height:120px;'>",
					"<tr>",
						"<th>",
							"<div style=''>",
								"<span class='k-span' style=''></span>",
								"<button class='k-view' style='margin:5px;'></button>",
								"<button class='k-set' style='margin:5px;'></button>",
								"<button class='k-delete' style='margin:5px;'></button>",
							"</div>",
							"<div style=''>",
								"<span>主&nbsp; &nbsp;表</span>",
								"<input class='k-dropdownlist' style='width:100px;margin:5px;'></input>",
							"</div>",
							"<div style=''>",
								"<span>变长区  </span>",
								"<input type='checkbox' class='k-isbatch' ></input>",
							"</div>",
						"</th>",
					"</tr>",
				"</table>",
			"</td>",
		"</tr>"
	],K = this,viewTable = jq,type = viewTable.attr('name');
	
	this.append = function(i){
		var $tr = $(viewTemplate.join('')), con = viewTable.find("tr:eq(0)");
		if(i){
			$tr.addClass("view-sort-" + i).attr("row-index", i);
		}
		if(con[0]){
			con.before($tr);
		} else {
			viewTable.append($tr);
		}
		return this.initEvents($tr);
	};
	
	this.initEvents = function($tr){
		
		var frameDoc = mtxx.frame.contents(),$tabstrip = mtxx["tabstrip-1"].data('kendoTabStrip');
		
		$tr.attr({
			id : "id_" + new Date().getTime()
		}).data(key,{
			tableMsg : '',
			linkageControl:new Array()
		}).addClass("view-tr-cls");
		
		$tr.find(".k-delete").kendoButton({
			imageUrl : mtxx.contextPath + "/images/cancel.png",
			click : function(e){
				if(confirm("确定删除吗?")){
					var tableMsg = $tr.data(key);
					$.each(tableMsg.linkageControl,function(i,v){
						frameDoc.find("#" + v.id).attr({
							'class' : ''
						}).css({
							'background-color' : ''
						});
					});
					$tabstrip.remove("li:eq(" + tableMsg.li + ")");
					$tr.remove();
				}
			}
		});
		
		$tr.find(".k-set").kendoButton({
			imageUrl : mtxx.contextPath + "/images/setting_tools.png",
			click : function(e){
				refreshSelected($tr.data(key));
				viewSelected($tr);
			}
		});
		
		addSelected($tr);
		
		$tr.find(".k-view").kendoButton({
			imageUrl : mtxx.contextPath + "/images/eye.png",
			click : function(e){
				viewSelected($tr);
			}
		});
		
		var kendoDropDownList = setDropDownList($tr);
		
		$tr.data("k-dropdownlist",kendoDropDownList);
		
		$tr.find('.k-isbatch').on('click',function(e){
			var checked = this.checked;
			if(checked){
				var master = kendoDropDownList.value();
				if(!master){
					alert('请选主表!');
					return false;
				}
				var tableMsg =  $tr.data(key);
				if(tableMsg && tableMsg.linkageControl){
					var linkageControls = tableMsg.linkageControl,tr,table,trLen = 0,sort = new Object(),
					tmpTime = new Date().getTime(),t,findTdIndex = function($tr,$td){
						var tds = $tr.find('td'),td,$td0 = $td[0];
						for(var i = 0; i < tds.length; i ++){
							td = tds[i];
							if(td === $td0){
								return i;
							}
						}
					};
					//同一行分组
					$.each(linkageControls,function(i,v){
						tr = $("#" + v.id,frameDoc).closest("tr");
						if(!table){
							table = tr.closest("table");
						}
						t = tr.attr("id");
						if(!t){
							t = "tr" + (tmpTime + i);
							tr.attr("id",t);
						}
						if(!sort[t]){
							trLen ++;
							sort[t] = new Array();				
						}
						sort[t].push({
							id : v.id,
							index : findTdIndex(tr,$("#" + v.id,frameDoc).closest("td"))
						});
					});
					
					var allTrLen = table.find("tr").length, limit = (allTrLen - trLen)-1;
					var fieldTr = table.find("tr").eq((allTrLen - trLen)-1),fields = new Array();//表头行
				//	debugger;
					var hasText = false;
					var addFields = function(collection){
						$.each(collection, function(i, td){
							var t = new Date().getTime() + i, $this = $(this).attr("t", t);
							var text = $.trim($this.attr("title") || $this.text());
							var f = {
								id : t,
								index : i
							};
							if(!text){
								f.isType = true;
								text = "系统分类";
								if(hasText){
									return;
								}
								hasText = true;
							}
							f.text = text;
							fields.push(f);
						});
					};
					
					addFields(fieldTr.find('td'));
					
					var $tb = table, collection = [];
					$tb.find("tr").each(function(i, tr){
						if(i > limit){
							return false;
						}
						var $tr = $(this);
						$tr.find("td").each(function(index, td){
							window.findLastChildren(td, collection, limit);
						});
					});
					if(collection.length){
						fields = [];
						addFields(collection);
					}
					
					
					var types = new Array(),items;
					for(t in sort){
						tr = $("#" + t,frameDoc);
						var allTdLen = tr.find("td").length;
						var os = sort[t];
						var x = allTdLen - os.length;
						if(x > 0){
							var tdType = tr.find("td").eq(--x),xy = tr.index() + '-' + x; //分类							
							var obj = {
								xy : xy,
								tdIndex : x,
								trIndex : tr.index(),
								fieldIds : os,
								name : tdType.text(),
								cid : os[0] ? os[0].id : '',
								items : new Array()
							};
							types.push(obj);			
							items = obj.items;				
						} else { //没有分类
							var obj = {
								xy : 'sys-' + x,
								tdIndex : -1,
								trIndex : tr.index(),
								fieldIds : os,
								name : '系统',
								cid : os[0] ? os[0].id : '',
								items : new Array()
							};
							types.push(obj);
							items = obj.items;			
						}
						if(items){
							items.push({
								trIndex : tr.index()
							});
						}
					}
					if(tableMsg && tableMsg.li){
						tableMsg.types = types;
						tableMsg.fields = fields;
						tableMsg.batch = true;
						refreshSelected(tableMsg);
					}
				}
				
			}
		});
		return $tr;
	};
}

/**
 * 获取最底层td
 * @param td
 * @param collection
 */
function findLastChildren(td, collection, limit){
	var $td = $(td), colspan, rowspan;
	var o = {
		colspan : (colspan = $td.attr("colspan")) ? colspan*1 : 0,
		rowspan : (rowspan = $td.attr("rowspan")) ? rowspan*1 : 0
	};
	$.data(td, "data-o", o);
	if(o.colspan > 0){//可能有下一行
		var $nextTr = $td.closest("tr").next();index = $nextTr.index(),count=0;
		if(limit <= index){
			$nextTr.find("td").each(function(i,v){
				if($.inArray(v,collection)==-1 && o.colspan >= count){
					count ++ ;
					window.findLastChildren(this, collection);	
				}
			});
		} 
       /* var index = $td.closest("tr").index(); 
		if(index && limit <= index){
			$td.closest("table").find("tr").eq(index + 1)//
			.find("td").each(function(){
				window.findLastChildren(this, collection);
			});
		}*/
	} else {
		if($.inArray(td,collection)==-1){
			collection.push(td);
		}
		/*collection.push(td);*/
	}
}


function getDropDownListSource(jq){
	var table = jq.closest("table");
	var datas = new Array({name : '',tableName : ''});
	var trs = table.find('.view-tr-cls');
	trs.each(function(i,tr){
		var tableMsg = $(this).data(key);
		if(tableMsg && tableMsg[key]){
			var tm = JSON.parse(tableMsg[key]);
			if(tm[0]){
				var t = tm[0];
				if(t && t.table){
					var table = t.table;
					datas.push(table);
				}
			}
		}
	});
	return datas;
}

function setDropDownList(jq){
	
	var table = jq.closest("table");
	
	var dataSource = getDropDownListSource(jq);
	
	var kendoDropDownList = jq.find(".k-dropdownlist").kendoDropDownList({
		dataSource : dataSource,
		dataTextField: "name",
	    dataValueField: "tableName",
		open : function(e){
			var s = getDropDownListSource(jq);
			kendoDropDownList.setDataSource(s);
		},
		change : function(e){
			//console.log(e);
			var tableMsg = jq.data(key);
			tableMsg && (tableMsg.topTableName = e.sender.value());
			console.log(tableMsg);
		}
	}).data("kendoDropDownList");
	
	return kendoDropDownList;
}

function addSelected($tr,addChild){
	
	removeSort(addChild);
	
	var tableMsg = $tr.data(key);
	var collection = tableMsg.linkageControl;
	mtxx.frame.contents().find('.selected-cls').each(function(){
		var $this = $(this);
		collection.push({
			id : $this.attr("id"),
			text : $this.attr("cname") || $this.attr("title") || $this.attr("name")
		});
		$this.addClass("isSelected").css({
			'background-color' : 'yellow'
		}).data("parent",$tr);
	});
}

function viewSelected($tr){
	var tableMsg = $tr.data(key);
	if(tableMsg){
		var frameDoc = mtxx.frame.contents();
		frameDoc.find('.selected-cls').each(function(){
			new selectedFunc($(this)).removeStyle();
		});
		$.each(tableMsg.linkageControl,function(i,v){
			new selectedFunc(frameDoc.find('#' + v.id)).addStyle();
		});
	}
}

function graySelected($tr){
	var tableMsg = $tr.data(key);
	if(tableMsg){
		var frameDoc = mtxx.frame.contents(),$this;
		$.each(tableMsg.linkageControl,function(i,v){
			$this = frameDoc.find('#' + v.id);
			new selectedFunc($this).addISelectedStyle();
			$this.data('parent',$tr);
		});
	}
}

function refreshSelected(tableMsg){
	if(tableMsg){
		var selector = "li:eq(" + tableMsg.li + ")";
		mtxx["tabstrip-1"].data('kendoTabStrip').select(selector);
		if(tableMsg.frameId){
			refleshFrame($("#" + tableMsg.frameId));
		}
	}
}

var toolbarTemplate = [
	"<div style='font-family:Lucida Calligraphy;font-size: 18px;font-weight: bolder;'>",			
		"<div class='k-div' style='float:left;' ></div>",
		"<div style='float:right'>",
			"<input type='checkbox' class='k-chk' title='勾选主表' />",
		"</div>",
	"</div>"
];

function appendSort(jq){
	
	var sortTemplate = [
   		"<tr>",
   			"<td>",
   				"<table>",
   					"<tr>",
   						"<td style='text-align:right;'>",
   							"<button class='k-right' style='width:70px'></button>",
   						"</td>",
   					"</tr>",
   					"<tr>",
   						"<td style='text-align:right;'>",
   							"<button class='k-left' style='width:70px'></button>",
   						"</td>",
   					"</tr>",
   					"<tr>",
   						"<td style='text-align:right;'>",
   							"<button class='k-delete' style='width:70px'></button>",
   						"</td>",
   					"</tr>",
//   					"<tr>",
//   						"<td style='text-align:right;'>",
//   							"<button class='k-set' title='表关联设置' style='width:70px'></button>",
//   						"</td>",
//   					"</tr>",
//   					"<tr>",
//						"<td style='text-align:right;'>",
//							"<button class='k-updateSet' title='更新设置' style='width:70px'></button>",
//						"</td>",
//					"</tr>",
   				"</table>",
   			"</td>",
   			"<td>",
   				"<div class='sortedColTb'></div>",
   			"</td>",
   		"</tr>"              
    ].join(''), K = this, type = jq.attr('name');
	
	this.sortTable = jq;
	this.append = function(i){
		var $tr = $(sortTemplate).addClass("sort-tr-cls"), con = K.sortTable.find("tr:eq(0)");
		if(i){
			$tr.addClass("append-sort-" + i).attr("row-index", i);
		}
		if(con[0]){
			con.before($tr);
		} else {
			K.sortTable.append($tr);
		}
		K.initEvents($tr).level(true);
		return $tr;
	};
	
	this.level = function(add){
		var h = 670;
		if(K.sortTable.find('tr').length > 3){
			h = K.sortTable.height();
		}
	};
	
	this.initEvents = function($tr){
		var gridId = "grid-" + new Date().getTime();
		var func = new typeFuncs(type);
		var $grid = $tr.find(".sortedColTb").attr({
			id : gridId
		}).kendoGrid({
			toolbar : toolbarTemplate.join(''),
			columns : [ {
				field : 'text',
				title : '名称'
			} ],
			selectable:"multiple, row",
			height:300,
			width : 300
		}).data("kendoGrid");
		$tr.find(".k-right").kendoButton({
			imageUrl : mtxx.contextPath + "/images/arrow_right_16.png",
			click : function(e){
				func.right($grid);
			}
		});
		$tr.find(".k-left").kendoButton({
			imageUrl : mtxx.contextPath + "/images/arrow_left_16.png",
			click : function(e){
				func.left($grid);
			}
		});
		$tr.find(".k-delete").kendoButton({
			imageUrl : mtxx.contextPath + "/images/cancel.png",
			click : function(e){
				func['delete']($tr);
			}
		});
		$tr.find(".k-set").kendoButton({
			imageUrl : mtxx.contextPath + "/images/setting_tools.png",
			click : function(e){
				var $grid = $tr.find(".sortedColTb").data("kendoGrid");
				if($grid.dataSource.data().length == 0){
					alert('请添加分组信息');
				} else {
					mtxx.grid = $grid;
					var url = mtxx.contextPath + tableMgrUrl;
					url = url + $.param({
						fn : 'saveSourceSetFunc' , //回调函数
						targetId : gridId,
						isBind : true //页面绑定标识,
					}) ;
					mtxx.tableMgrWin = window.open(url);
				}
			}
		});
		$tr.find(".k-updateSet").kendoButton({
			imageUrl : mtxx.contextPath + "/images/role.png",
			click : function(e){
				
			}
		});
		$tr.find('.k-chk').on('click',function(){
			var $this = this;
			if(this.checked == true){
				$('.k-chk').each(function(){
					if(!($this === this)){
						this.checked = false;
					}
				});
			}
		});
		func.select($grid);
		return this;
	};
}

window.typeFuncs = function(type){
	var funcs = {
		sortedColTb : {
			select : function($grid){
				if(mtxx["availableColTb"].select().length > 0){
					this.exangeRows(mtxx["availableColTb"],$grid);
				}
			},
			exangeRows : function(fromGrid,toGrid){
				var rows = fromGrid.select();
				if(!rows || rows.length == 0){
					alert("请选择!");
					return false;
				} else {
					var fromDataSource = fromGrid.dataSource,
					toDataSource = toGrid.dataSource,dataItem,tmp = new Array();
					for(var i = 0; i < rows.length; i ++){
						dataItem = fromGrid.dataItem(rows[i]);
						if(dataItem){
							toDataSource.add(dataItem);
							tmp.push(dataItem);
						}
					}
					if(tmp.length > 0){
						for(var i = 0; i < tmp.length; i ++){
							dataItem = tmp[i];
							if(dataItem){
								fromDataSource.remove(dataItem);
							}
						}
					}
					tmp = null;
				}
			},
			'right' : function($grid){
				this.exangeRows(mtxx["availableColTb"],$grid);
			},
			left : function($grid){
				this.exangeRows($grid,mtxx["availableColTb"]);
			},
			'delete' : function($tr){
				if(!confirm('确定删除吗?'))
					return false;
				var $grid = $tr.find(".sortedColTb").data("kendoGrid");
				var data = $grid.dataSource.data();
				if(data && data.length > 0){
					for(var i = 0; i < data.length; i ++){
						mtxx["availableColTb"].dataSource.add(data[i]);
					}
				}
				$tr.remove();
				K.level(true);
			}
		},
		viewedColTb : {
			select : function($grid){
				this.right($grid);
			},
			exangeRows : function(from,to){
				if(from instanceof jQuery){
					from.contents().find(".selected-cls").each(function(){
						var $this = $(this);
						if(dataRoleFilter($this.attr('data-role'))){
							return;
						}
						if(!$this.hasClass("isSelected")){
							to.dataSource.add({
								id : $(this).attr("id"),
								text : $(this).attr("cname") || $(this).attr("title") || $(this).attr("name")
							});
						}
					});
				} else {
					var rows = from.select();
					if(rows.length == 0){
						alert("请选择!");
						return false;
					} else {
						var dataItem,tmp = new Array(),fromDataSource = from.dataSource;
						for(var i = 0; i < rows.length; i ++){
							dataItem = from.dataItem(rows[i]);
							if(dataItem){
								tmp.push(dataItem);
							}
						}
						if(tmp.length > 0){
							for(var i = 0; i < tmp.length; i ++){
								dataItem = tmp[i];
								if(dataItem){
									fromDataSource.remove(dataItem);
								}
							}
						}
						tmp = null;
					}
				}
			},
			'right' : function($grid){
				this.exangeRows($("#iframe-1"),$grid);
			},
			left : function($grid){
				this.exangeRows($grid, $("#iframe-1"));
			},
			'delete' : function($tr){
				if(!confirm('确定删除吗?'))
					return false;
				$tr.remove();
			}
		}
	};
	this.select = function($grid){
		funcs[type].select($grid);
	};
	this.exangeRows = function(from,to){
		funcs[type].exangeRows(from,to);
	};
	this.right = function($grid){
		funcs[type].right($grid);
	};
	this.left = function($grid){
		funcs[type].left($grid);
	};
	this['delete'] = function($tr){
		funcs[type]["delete"]($tr);
	};

}


var kendoRole = {
	ztree : '',
	grid : '',
	button : '',
	splitter : '',
	label : '',
	mtbutton : '',
	form : '',
	div : '',
	tabstrap : '',
	contextmenu : '',
	 tabstrip : '',
	 selectable : '',
	 popup : '',
	 staticlist : '',
	 bootstrap_tabs : ''
};

function dataRoleFilter(dataRole){
	if(dataRole && (dataRole in kendoRole) || dataRole.indexOf("col-") > -1){
		return true;
	}
	return false;
}

var fields = ['id','text','name'],key = "tableMsg";
function setGridValues(jq,data){
	var kendoGrid = jq.data("kendoGrid"),chk = jq.find(".k-chk")[0];
	jq.find(".k-div").text(data.table.name);
	if(chk){
		if(data.table.isMaster || chk.checked)
			chk.checked = true;
	}
//	var d = {
//		name : data.table.name,
//		table : data.table,
//		columns : data.columns
//	};
	var d = getRequiredData(data);
	jq.data(key,JSON.stringify([d]));
	kendoGrid.setDataSource(new kendo.data.DataSource({
		data : data.columns
	}));
}

function getRequiredData(data){
	return {
		name : data.table.name,
		table : data.table,
		columns : data.columns,
		wdataSet : data.wdataSet
	};
}

function saveSourceSetFunc(data){
	var $grid = $(this),kendoGrid = $grid.data("kendoGrid");
	if(data){
		setGridValues($grid,data);
		if(mtxx.tableMgrWin){
			mtxx.tableMgrWin.close();
		}
	} else {
		var tableMsg = $grid.data(key) || '';
		var linkageControl = new Array();
		if(kendoGrid){
			var ds = kendoGrid.dataSource.data(),d,o;
			for(var i = 0; i < ds.length; i ++){
				d = ds[i],o = {};
				$.each(fields,function(i,v){
					o[v] = d[v];
				});
				linkageControl.push(o);
			}
		} else {
			
		}
		var ret = {
				tableMsg : tableMsg,
				linkageControl:JSON.stringify(linkageControl)
			};
		return ret;
	}
}

/**
 * 选中事件
 */
var divEvents = {
		document : null,
		isInit : false,
		init : function(e){
			var that = this;
			//that.document.data("select-item",that.document.find("[crtltype],[data-role]"));
			that.document.data("select-item",that.document.find("[data-role]"));
			that.isInit = true;
		},
		bindMouseDown : function(e){
			var that = this,doc = that.document;
			if(!that.isInit){
				that.init(e);
			}
			var position = {
					x : (e.clientX + 20),
					y : (e.clientY + 85)
			},$div = divEvents.$div = $("<div>",{
				'class' : "ui-selectable-helper"
			}).css({
				position:'absolute',
				"top": position.y + "px",
				"left": position.x + "px"
			}).appendTo('body');
			$div.data("position",position);
			
			doc.bind("mousemove", function(e){
				divEvents.bindMouseMove(e);
			}).bind("mouseup", function(e){
				divEvents.bindMouseUp(e);
			}).bind("selectstart",function(){
				//return false; 
			}).data("start",{
				x : (e.clientX),
				y : (e.clientY)
			});
		},
		unbind : function(){
			var that = this, doc = that.document;
			
			$('.ui-selectable-helper').remove();
			doc.unbind("mousemove").unbind("mouseup");
		},
		bindMouseUp: function(e) {
			var that = this, doc = that.document;
			this.unbind();
			if(doc.find('.selected-cls').length > 0){
				doc.data("context-menu").open((e.clientX + 20),(e.clientY + 85));
			}
			//return false;
		},
		bindMouseMove: function(e) {
			var that = this, doc = that.document;
			doc.data("context-menu").close();
			var $div = divEvents.$div;
			if($div[0]){
				var pos = $div.data('position') || {};
				$div.css({
					"width": (e.clientX*1 + 20 - pos.x*1) + "px",
					"height": (e.clientY*1 + 85 - pos.y*1) + "px"
				});
				divEvents.findCover(e);
			}
			//return false;
		},
		findCover : function(e){
			var that = this, $this = that.document;
			if(!$this.data("init-select")){
				$this.data("select-item").each(function(i,v){
					var b = $(this), c = b.offset();
					var opt = {
							element : this,
							$element : b,
							left : c.left,
							top : c.top,
							right : c.left + b.outerWidth(),
							bottom : c.top + b.outerHeight()
					};
					$.data(this, "selectable-item", opt);
				});
				$this.data("init-select",true);
			} 
			var start = $this.data("start") || {x:0,y:0};
			var docScrollTop = $this.scrollTop(),
			docScrollLeft = $this.scrollLeft();
			var c = start.x, d = start.y + (docScrollTop > 0 ? docScrollTop : 0), X = e.pageX - docScrollLeft , Y = e.pageY + (docScrollTop > 0 ? docScrollTop - 120 : 0);
			if (c > X) {
				var i = X;
				X = c;
				c = i;
			}
			if (d > Y) {
				var i = Y;
				Y = d;
				d = i;
			}
			$this.data("select-item").each(function() {
				var i = $.data(this, "selectable-item");
				if (!i)
					return;
				var j = !(i.left > X || i.right < c
							|| i.top > Y || i.bottom < d),
			    $this = i.$element;
				var selectedFn = new selectedFunc($this);
				if(j){
					//if(($this.attr('data-role') in kendoRole)){
					//	return;
				//	}
					if(dataRoleFilter($this.attr('data-role'))){
						return;
					}
					if($this.hasClass("isSelected")){
						
						//return;
					}
					selectedFn.addStyle();
				} else {
					selectedFn.removeStyle();
				}
			});
		},
		onDrag : function(e){
			
		}
};

function selectedFunc(jq){
	var selected = "selected-cls";
	var isSelected = jq.hasClass("isSelected");
	this.addISelectedStyle = function(){
		jq.removeClass(selected).addClass("isSelected").css({
			'background-color' : '#D9D9D9'
		});
	};
	this.addStyle = function(){
		if(!jq.hasClass(selected)){
			jq.addClass(selected).css("background-color","yellow");
		}
	};
	this.removeStyle = function(){
		if(!isSelected){
			jq.css("background-color","").removeClass(selected);
		} else {
			this.addISelectedStyle();
		}
	};
	this.removeAll = function(){
		jq.attr({
			class : ''
		}).css({
			"background-color" : ""
		});
	};
}

/**
 * 生成更新集、表单查询集
 * @param jq
 */
function createUpdateSource(tableMsgArr, cover){
	var jqxhr = $.ajax({
	    url: mtxx.contextPath + '/mx/form/defined/createUpdateSource',
	    type: "post",
	    data: {
	    	pageId : mtxx.pageId,
	    	tableMsgArr : JSON.stringify(tableMsgArr),
	    	cover : cover
	    }, 	    
	    dataType: "json",
	    success : function(data){
	    	tableMsgArr = data;
	    },
	    async : false
	});
	return tableMsgArr;
}