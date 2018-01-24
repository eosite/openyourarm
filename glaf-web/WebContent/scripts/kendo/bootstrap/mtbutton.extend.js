// 按钮事件 klaus.wang 2015-05-14
function configMtbutton(options){
	if(options&&!options.isVisible){
		$("#"+options.id).css("display", "none");
	}
	if(options&&!options.isEnabled){
		$("#"+options.id).attr("disabled","disabled");	
	}
	if(options&&options.sureDialog){
		if (!$().confirmation) {
            return;
        }
        $("#"+options.id).off(".mtbutton");
        $("#"+options.id).confirmation({ 
        	container: 'body', 
        	placement: options.dialogPosition,
			title : '是否确定?',
        	btnOkClass: 'btn btn-sm btn-success', 
        	btnOkLabel: '确定',
        	btnCancelClass: 'btn btn-sm btn-danger',
        	btnCancelLabel : '取消',
        	onConfirm : function(event, element){
        		kendoButtonFunc(options, 'mtbutton');
        	}
        });	
	}
	
};

function kendoButtonFunc(viewModel) {
	if (!viewModel.buttonType) {
		return false;
	}
	var functions = viewModel.functions;
	if (!functions) {
		functions = new MtButton(viewModel);
	}

	if ((viewModel.buttonType in functions)) {
		functions[viewModel.buttonType]();
	} else {
		alert(viewModel.buttonType + ' 方法未找到');
	}
};
function MtButton(vm) {

	this.buttonKey = "MtButton";

	this.$target = $("#" + vm.id);

	this.viewModel = vm;

	this.init();

	(function(that){
		if(that.$target.get(0))
			$.data(that.$target.get(0), that.buttonKey, that);
	})(this);

};
MtButton.prototype.dialogInst = function(dialogRel) {
	this.dialogRel = dialogRel;
};
MtButton.prototype.init = function() {
	this.viewModel.data = new Object();
	if (this.viewModel.handlecolumns)
		this.viewModel.data.tableMsg = eval('(' + this.viewModel.handlecolumns + ')');
	if (this.viewModel.ruleData) {
		this.viewModel.data.rule = eval('(' + this.viewModel.ruleData + ')');
	}
	this.viewModel.data.pageParameters = pageParameters;
	this.viewModel.functions = this;
};

/**
 * 表单验证
 * @returns
 */
function getFormValiate(){
	var $valiSave = $("[valiSave=true]");
	if(!$valiSave.get(0)){
		return true; //不验证
	} else { 
		return $valiSave.valid(); 
	}
};

/**
 * 保存操作
 */
MtButton.prototype.save = function($alert, args) {
	args = args || {};
	var K = this,
		data = new Array(),
		tables = new Object();
	//验证
	/*if (K.viewModel.saveValidate) {
		var valis = $("[valisave=true]"),
			vali, length = valis.length,
			i = 0,
			bol, rebol = true;
		if (valis && valis.length) {
			var first = null;
			for (; i < length; i++) {
				vali = valis[i];

				bol = $(vali).valid();
				if (!bol){
					rebol = false;
				}
				if(!first && !rebol){
					first = $(vali);
				}
			}
			if(first){
				$(first).focus();
			}
			if (!rebol)
				return false;//unsubmit
		}
	}*/
	
	if(!window.getFormValiate()){
		return false;
	}
	
	var vmd = K.viewModel.data;
	if (!vmd.tableMsg) {
		alert('未保存设置!');
		return false;
	}
	
	var tableMap = null;
	jQuery.each(K.viewModel.data.tableMsg, function(i, tableMsg) {
		var container = {dataSetId : tableMsg.dataSetId,
				idValue : tableMsg.idValue,
				table : tableMsg.table,
				columns : new Array(),
				batch : tableMsg.batch,
				wdataSet : tableMsg.wdataSet};
		
		var tmpMap = {};
		if (tableMsg.columns) {
			$.each(tableMsg.columns, function(index, v) {

				var $this = jQuery('#' + v.id),
					params = $this.data('params') || {};

				if (params.idValue && !container.idValue) {
					container.idValue = params.idValue;
				}

				try {
					v.value = $this.mtbootstrap('getValue', v.fieldName);
				} catch(e){
					v.value = $this.val() || "";
				}
				container.columns.push(v);
				
				tmpMap[v.fieldName] = v;
			});
		}

		
		/**
		 *  获取页面获取主键
		 */
		var tables = $("." + container.table.tableName);
		if (tables.length) {
			tables.each(function(i, v) {
				var $this = $(v), fieldName = $this.attr('fieldName');
				
				/**
				 * 判断该主键字段 值有没有
				 */
				if(fieldName && (fieldName in tmpMap) //
						){
					tmpMap[fieldName].value = tmpMap[fieldName].value;
				} else if(fieldName){
					container.columns.push({
						fieldName: fieldName,
						value: $this.val()
					});
				}
				
			});
		}

		if (container.columns.length > 0) {
			if (tableMsg.batch || tableMsg.cell) {
				if (window.batch) {
					if (container.wdataSet && container.wdataSet.id) {
						//window.batch.getValue(container.columns = []);
						//data.push(container);
						if(!tableMap){
							var cols = [];
							tableMap = {};
							window.batch.getValue(cols);
							if(cols.length > 0){
								$.each(cols, function(i, v){
									var t = v.table;
									if(t){
										!tableMap[t.tableName] && (tableMap[t.tableName] = []);
										tableMap[t.tableName].push(v);
									}
								});
							}
						}
						container.columns = tableMap[container.table.tableName] || [];
						data.push(container);
					} else {
						window.batch.getValue(data);
					}
				}
			} else {
				data.push(container);
			}
		}
	});

	if (data.length > 0) {
		var s = true;
		if (window.saveFileToUrl) {
			s = saveFileToUrl(true);
		}
		
		if (s) {
			
			var msg = data[0], relation = [];
			if (msg && msg.wdataSet  && msg.wdataSet.id) {// 更新集保存方式
				var saveMsg = [];

				function to(m, collection, dels) {
					var obj = {
						id : m.idValue
					};
					$.each(m.columns, function(i, v) {
						if(v.delete && v.id){
							dels.push(v);
						} else if (m.batch) {
							to(v, collection);
						} else {
							obj[v.fieldName] = v.value;
						}
					});
					$.extend(obj, args); //外部传入的参数优先级高，覆盖表单数据
					if (!m.batch)
						collection.push(obj);
				}
				var map = {}, ch, chMap = {};
				
				$.each(data, function(i, m){
					if((ch = m.table.children) && ch.length){
						$.each(ch, function(ii, mm){
							chMap[mm] = m.table.id;//将子节点的父节点id存放起来
						});
					}
				});
				
				$.each(data, function(i, m) {
					var t = {
						id : m.wdataSet.id,
						table : m.table,
						datas : [],
						dels : []
					};
					m.table.children = [];
					
					if(chMap[m.table.tableName] && !m.table.topId){
						m.table.topId = chMap[m.table.tableName];
					}
					
					!map[m.table.id] && (map[m.table.id] = m.table) &&//
					(relation.push($.extend(true, {}, m.table)));
					//relation.push(m.table);
					to(m, t.datas, t.dels);

					saveMsg.push(t);
				});
				
				if(relation.length > 1){
					//relation = $.transformToTreeFormat(relation, "id", "topId", "children");
					relation = $.transformToTreeFormat(relation, "tableName", "topTableName", "children");
				} else {
					relation = null;
				}

				$.ajax({
					url : contextPath + '/mx/form/data/saveFormData',
					type : 'POST',
					data : {
						relation : relation ? JSON.stringify(relation) : relation,
						tableMsg : JSON.stringify(saveMsg)
					},
					error:function(){
						window.savecount++;
					},
					dataType : 'JSON',
					success : successFunc
				/*
				 * function(ret) { if (ret && ret.message) { alert(ret.message);
				 * $.each(saves, function(i, msg) { if (wdataSetId && msg.id !==
				 * wdataSetId) { return false; } window.dynamicCellDataSet(msg);
				 * }); } }
				 */
				});

				return false;
			}
			
			/**
			 * 返回主键保存
			 */
			function eachTableMsg(i, v, data) {
				var tmsg = data[v.table.tableName];
				if(!tmsg && relation && relation.length){
					var table = relation[0];
					if(v.table.id == table.id && data[table.id]){
						tmsg = {
							idValue : data[table.id]
						};
					}
				}
				if (tmsg) {
					v.idValue = tmsg.idValue || tmsg;
					!$("input." + v.table.tableName)[0] && ($("<input>", {
						'class' : v.table.tableName,
						type : 'hidden',
						fieldname : 'id',
						value : v.idValue
					}).appendTo("body"));

					var $idVal = $("." + mtxx.params.id);
					if (!$idVal.get(0)) {
						$idVal = $("<input>", {
							'class' : mtxx.params.id,
							type : 'hidden',
						}).appendTo("body");
					}

					$idVal.attr({
						idValue : v.idValue
					});

				}
				if (v.cell && window.batch) {
					window.batch.refresh();
				}
				
				/**
				 * 变长区id赋值
				 */
				if(v.batch && data.batchIds){
					var $tr, row;
					$.each(data.batchIds, function(cls, id){
						$tr = $("._" + cls);
						if($tr.get(0) && !(row = $tr.data("row"))){
							 $tr.data("row", {id : id});
						}
					});
				}
			}

			/**
			 * 保存成功回调
			 */
			function successFunc(data) {
				window.savecount++;
				if (data && data.message && !data.rst) {
					alert(data.message);
					mtDebugger.pushQueue("error", "页面保存失败！错误信息为:");
					mtDebugger.pushQueue("error", data.message);
				} else {
					mtDebugger.pushQueue("info", "页面保存成功！其保存结果为:");
					mtDebugger.pushQueue("info", data);
					if(!$alert && !args.isAlt)
						alert('操作成功!');
					$.each(vmd.tableMsg, function(i, v) {
						eachTableMsg(i, v, data);
					});
					if($alert && $.isFunction($alert)){
						$alert.call(window, data);
					}
				}
			}
			
			jQuery.ajax({
				url: contextPath + '/mx/form/data/saveOrUpdateDetail',
				type: 'post',
				data: {
					tableMsg: JSON.stringify(data)
				},
				async: true,
				dataType: 'json',
				error:function(){
					window.savecount++;
				},
				success: function(data) {
					window.savecount++;
					if (data && data.message) {
						alert(data.message);
					} else {
//						alert('操作成功!');
						
						successFunc(data);
						
						var odt = getOpenDialogToggle(window);
						if(odt){
							var bootstrapData = K.bootstrapData(parent.$(odt.viewModel.change));
							if(bootstrapData && bootstrapData.bootstrap){
								bootstrapData.bootstrap.load();
							}
						}
						if (K.viewModel.data.rule.closeWindow == 'true') {
							K.closeWindow();
						}
					}
				}
			});
		}
	} else {
		//alert('请输入!');
	}
};
/**
 * 关闭操作
 */
MtButton.prototype.closeWindow = function() {
	var that = this;
	var pageParameters = that.viewModel.data.pageParameters;
	if (pageParameters) {
		var rst = MtButtonClose(window, that);
		if (!rst) {
			window.close();
		}
	} else {
		window.close();
	}
};
/**
 * 查询操作
 */
MtButton.prototype.search = function() {
	var jq = jQuery(this.viewModel.change);

	var paraType = this.viewModel.data.rule.paraType,
		idRelation = {};
	if (paraType && paraType[0]) {
		var datas = paraType[0].datas;
		if (datas) {
			pubsub.pub("linkageControl", datas);
		}
	}

};
/**
 * 删除操作
 */
MtButton.prototype['delete'] = function() {
	var K = this,
		deleteFunctions = K.deleteFuncs;
	jQuery.each(K.viewModel.data.tableMsg, function(i, v) {
		var $this = jQuery('#' + v.id),
			dataRole = K.bootstrapData($this).dataRole;
		if (deleteFunctions[dataRole]) {
			deleteFunctions[dataRole].call(K, $this);
		}
	});
};
// 删除方法
MtButton.prototype.deleteFuncs = {
	gridbt: function(jq) {
		var K = this,
			bootstrapData = K.bootstrapData(jq);
		var grid = bootstrapData.bootstrap;
		var rows = grid.getSelectedRows();
		if (rows && rows.length > 0) {
			if (K.viewModel.multiPlay == 'false') {
				if (rows.length > 1) {
					alert('请选择一条记录!');
					return false;
				}
			}
			var models = rows;
			grid.ajaxDestroy(models, function(){
				grid.load();
			});
//			confirm2('你确定删除吗?', function(result) {
//				if(result){
//				}
//			});
		} else if (rows.length == 0) {
			alert('请选择!');
		}
	}
};
/**
 * 流程提交
 */
MtButton.prototype.submit = function() {
	var that = this,
		data = that.viewModel.data;

	var process = {
		isDefined: function() {
			return true;
		},
		isUndefined: function() {
			alert('流程未定义!');
		}
	};
	if (data && data.rule) {
		var id = $("[fieldname=id]:hidden").val(),
			flow = mtxx.params.flow;
		var pageRuleId = $(".pageruleid").val(),
			params = {
				multiple: data.rule["multiple-flow"],
				rid: that.viewModel.prid,
				id: id,
				pageRuleId: pageRuleId,
				pageId: data.rule.pageId
			};
		if (objectISNotEmpty(flow)) {
			if (id) {
				var url = contextPath + "/mx/form/defined/ex/flowFeedback?" + $.param({
					fn: 'processGetData',
					pageId: data.rule.pageId
				});
				var win = jQuery.mt.window({
					id: pageRuleId,
					title: '流程审批',
					width: '750px',
					height: '350px',
					refresh: true,
					modal: true,
					content: {
						url: url,
						iframe: true
					},
					appendTo: 'body'
				});
				win.kendo.center();
			} else {
				alert("该流程未启动");
			}
		} else if (objectISNotEmpty(flow) || params.multiple) {
			var paraType = data.rule.paraType;
			if (paraType) {
				var flowParams = getParaType(paraType);
				if (flowParams) {
					if (!flowParams.isReady) {
						alert('流程参数不能为空!');
						return false;
					}
					params.flowParams = JSON.stringify(flowParams);
				}
			}

			var processData = null;
			if (!flow) {
				$.ajax({
					url: contextPath + "/mx/form/workflow/defined/getMultipleVariables",
					async: false,
					data: params,
					type: "POST",
					dataType: "JSON",
					success: function(bytes) {
						if (bytes && bytes[0]) {
							$.each(bytes, function(i, b) {
								var by = JSON.parse(b);
								if ((by = by[0]) && by.targets) {
									$.each(by.targets, function(i, v) {
										addVariables(v.eleId, v.taskId);
									});
								}
							});
							processData = processGetData() || {};
							$.extend(params, processData)
						}
						mtxx.params.flow = null;
					}
				})
			}

			if (confirm("你确定提交流程吗?")) {
				if (processData && !processData.id) {
					alert("请先保存!");
					return false;
				} else {
					$.post(contextPath + "/mx/form/workflow/defined/processSubmit", params, function(ret) {
						alert("流程提交成功! 流程实例ID为：" + ret.processInstanceId);
					}, "JSON");
				}
			}
		} else {
			process.isUndefined();
		}
	} else {
		process.isUndefined();
	}
};
/**
 * 新建窗口
 */
MtButton.prototype.newWindow = function(type) {
	var K = this,
		id = 'mtWindow_' + K.viewModel.id,
		rule = K.viewModel.data.rule,
		maximize = K.viewModel.maximize;
	var jumpType = rule.jumpType; // 页面跳转类型
	var jumpFuncs = {
		childPage: { // 子窗口(弹出)
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {
					var width = rule.width || 650;
					var height = rule.height || 450;
					if(maximize){
						width = $(document).width()-24;
						height = $(document).height()-81;
					}
					var dialog = BootstrapDialog.show({
						draggable:true,
				        message: function(dialog) {
				            var pageToLoad = dialog.getData('pageToLoad');
				            var frame = "<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='"+height+"' src='"+pageToLoad+"' frameborder='no' scrolling='yes'></iframe>";
				            return frame;
				        },
				        data: {
				            'pageToLoad': url + '?' + jQuery.param(data)
				        },
				        css : {
							width : width+'px',
							height: height+'px'
						}
				    });
				    if(maximize){
					    dialog.$modal.css({
					    	display: 'table',
					    	padding: '0px 10px'
					    });
					    dialog.$modalDialog.css({
					    	margin: '0px 10px'
					    });
				    }
				    K.dialogInst(dialog);
				});
			},
			close: function(win) {
				//jQuery('#' + id).data('kendoWindow').close();
			}
		},
		singlePage: { // 跳转页面
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {
					window.open(url + '?' + jQuery.param(data));
				});
			},
			close: function(win) {
				win.close();
			}
		}
	};
	if (!type) {
		if (jumpFuncs[jumpType]) {
			jumpFuncs[jumpType].open();
		}
	}
	return jumpFuncs[jumpType];
};
// 窗口方法
MtButton.prototype.newWinFuncs = {
	init: function(K, fn) {
		var paraType = K.viewModel.data.rule.paraType,
			idRelation = {};
		var url = contextPath + '/mx/form/page/viewPage';
		var has = false,
			params = null,
			commonParam = {
				newWin: K.viewModel.buttonType,
				id: K.viewModel.pageId,
				prid: K.viewModel.prid,
				btnId: K.viewModel.id
			};
		if (paraType && paraType[0]) {
			var datas = paraType[0].datas;
			if (datas) {
				for (var k in datas) {
					if (k.indexOf("/mx/") > -1) {
						url = contextPath + k;
						break;
					}
				}
				params = pubsub.getParameters(datas);
			}
		}

		/**
		 * 有参数时执行
		 */
		if (params != null) {
			has = true;
			if (fn) {
				fn(url, $.extend(params, commonParam));
			}
		}

		/**
		 * 无参数时...
		 */
		if (!has) {
			if (fn) {
				fn.call({}, url, commonParam);
			}
		}
	},
	grid: function(K, jq) {
		var bootstrapData = K.bootstrapData(jq),
			grid = bootstrapData.bootstrap,
			rows = grid.select();
		if (rows && rows.length > 0) {
			if (K.viewModel.multiPlay == 'false') {
				if (rows.length > 1) {
					alert('请选择一条记录!');
					return false;
				}
			}
			var pageId = K.viewModel.pageId;
			if (pageId) {
				var ids = new Array();

				var relation = jq.data("idRelation"),
					ret = {};

				jQuery.each(rows, function(i, v) {
					if (relation) {
						$.each(relation, function(col, item) {
							item.items.push(grid.dataItem(v)[item.columnName]);
						});
					} else
						ids.push(grid.dataItem(v).id);
				});

				if (relation) {
					$.each(relation, function(k, item) {
						ret[k] = item.items.join('_0_');
					});
				}
				ret.newWin = K.viewModel.buttonType;
				ret.id = K.viewModel.pageId;
				ret.prid = K.viewModel.prid;
				ret.btnId = K.viewModel.id;

				return ret;
			}
		} else {
			alert('请选择!');
			return false;
		}
	}
};
var roleList = {
	gridbt: 'grid'
};
function getBootstrapData(jq) {
	if (!jq || !jq[0]) {
		return null;
	}
	var dataRole = jq.attr('data-role');
	return {
		dataRole: dataRole || 'text',
		bootstrap: jq.data(roleList[dataRole])
	};
}
MtButton.prototype.bootstrapData = function(jq) {
	return getBootstrapData(jq);
};
function getOpenDialogToggle(win) {
	if (pageParameters) {
		var params = pageParameters;
		var parent = win.opener || win.parent;
		if (params && params.btnId) {
			var $btn = parent.$('#' + params.btnId);
			var btn = $btn.data('MtButton');
			return btn;
		}
	}
	return null;
}
function MtButtonClose(win, btnTarget) {
	if (pageParameters) {
		var params = pageParameters;
		var parent = win.opener || win.parent;
		if (params && params.btnId) {
			var $btn = parent.$('#' + params.btnId);
			var btn = $btn.data(btnTarget.buttonKey);
			if(params.newWin && btn){
				btn.dialogRel.close();
			}
		} else {
			return MtButtonClose(parent);
		}
	}
	return false;
}
/**
 * Metronic Button
 */
var mtbuttonFunc = {
		binding: function(rule, args,options){
			debugger;
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd).clone();
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			htmled.attr("id","");
			if(tile){
				$('#'+ids).tooltip({title:tile,placement:place,html:true,template:htmled});
			}else {
				htmled.find('.tooltip-inner').remove();
				$('#'+ids).tooltip({title:"z",placement:place,html:true,template:htmled});
			}
			
		},
		setValue : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$.each(args,function(i,arg){
				$id.find("span.frame-variable")[0].innerHTML = arg;
			});
			
		},
		getValue : function(rule,args){
			var $id = pubsub.getJQObj(rule,true);
			return $id.find("span.frame-variable").text();
		},
		//添加按钮
		copyMtButon : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			var content = $id.parent().parent();
			var div = $id.parent().parent().parent();
			
			/*
			$id.closest("div").append($id[0].outerHTML);*/
			$.each(args,function(i,arg){
				if(arg.toString().indexOf(",") != -1){
					var arr = arg.toString().split(",");
					for(var z=0;z<arr.length;z++){
						content.before(content[0].outerHTML);
						$(div.find("div.row")[z]).find("button").css("display","block");
						$(div.find("div.row")[z]).find("span")[0].innerHTML = arr[z];
					}
				}
				else{
					content.before(content[0].outerHTML);
					$(div.find("div")[0]).find("button").css("display","block");
					div.find("div").find("span")[0].innerHTML = arg;
				}
				
			})
		},
		bindpopover: function(rule, args,options){
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd);
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			var cont =options[0].content;
			if(!cont){
				content = args.content;
			}
			htmled.attr("id","");
			!tile && htmled.find(".popover-title").remove();
			!cont && htmled.find(".popover-content").remove();
			$('#'+ids).popover({title:tile?tile:"x",content:cont?cont:"x",placement:place,html:true,template:htmled});
			
		},
	
};
pubsub.sub("mtbutton", mtbuttonFunc);