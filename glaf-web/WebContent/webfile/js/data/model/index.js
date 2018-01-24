/**
 * 
 */
window.CONFIG = {
	contextPath : "/glaf"
}, $callback = jQuery.Callbacks("hmtd.java.data");

$(function() {
	$callback.fire();
});

/**
 * resize
 */
var WinReszier = (function() {
	var registered = [];
	var timer;
	var resize = function(ev) {
		timer && window.clearTimeout(timer);
		timer = setTimeout(notify, 100);
	};
	var notify = function() {
		for (var i = 0, cnt = registered.length; i < cnt; i++) {
			registered[i] && registered[i].apply();
		}
	};
	
	$(window).bind('resize', resize);
	
	return {
		register : function(fn) {
			registered.push(fn);
		},
		unregister : function(fn) {
			for (var i = 0, cnt = registered.length; i < cnt; i++) {
				if (registered[i] == fn) {
					delete registered[i];
					break;
				}
			}
		}
	}
}());

/**
 * 
 * 
 * @param name
 * @returns
 */
var getUrlVar = (function() {
	var vars = {}, pattern = /[?&]+([^=&]+)=([^&]*)/gi;
	var parts = window.location.href.replace//
	(pattern, function(m, key, value) {
		vars[key] = value;
	});
	return function(name) {
		return vars[name];
	};
})();

function GetHeight() {
	return $(window).height() - 198;
};

var GetGridHeight = function() {
	return GetHeight() - 100;
}, GetGridWidth = function() {
	return $(window).width() * 0.78;
}

var ResizeHeight = (function() {
	var resize = function() {
		$(".tab-pane").css({
			height : GetHeight()
		});

		$("#ztree-01").css({
			height : GetHeight() + 23
		});

		$(".mt-well").css({
			height : GetHeight() + 84,
			overflow : 'scroll'
		});

		ResizeBtn();

		return resize;
	};
	return resize;
})();

function ResizeBtn() {
	var $left = $(".split-left"), //
	$btn = $("#split-btn");
	var left = 10, text = ">>";
	if ($left.is(":visible")) {
		left = $left.width() + $left.position().left + 15
		text = "<<";
	}
	$btn.animate({
		left : left,
		top : $left.position().top + ($left.height() / 2) - 50
	}, function() {
		$btn.find("button").text(text);
	}).show();

	DBDesigner.get() && DBDesigner.reset();
}

/**
 * 异步获取设计区数据
 * 
 * @param oid
 * @param fn
 * @returns
 */
function GetDesigner(oid, fn) {
	if (!oid) {
		fn && fn(null);
	} else {
		$.ajax({
			url : contextPath + '/data/model/getDataModelById',
			data : {
				id : oid
			},
			dataType : 'JSON',
			success : fn,
			error : function() {
				console.log(arguments);
			}
		});
	}
}
function _treeNodeAdd(e,index){
	var treeObj = $("#ztree-01").ztree("getZtree");
	var name = $("#type-name").val();
	var code = $("#type-code").val();
	var  Node = {
			name : 'New Node',
			isNew : true,
			catalog : true,
			model : false,
			code : ""
		};
	var nodes = treeObj.getSelectedNodes(), pNode, //
	nNode = $.extend(true, {}, Node, {
		name : name,
	    code : code,
		model : e.model
	});
	if (nodes && nodes.length == 1) {
		pNode = nodes[0];
		if (pNode.model) {
			alert("不能在物理模型下建分类!");
			return false;
		}
		$.extend(nNode, {
			topId : pNode.id,
			treeId : pNode.treeId
		});
	}
	if (nNode.model) {
		nNode = $.extend(true, {}, Node, {
			name : name,
		    code : nodes[0].code,
			model : e.model
		});
		nNode.icon = window.CONFIG.contextPath//
				+ "/images/database_gear.png";
	} else {
		// nNode.iconSkin = "folder";
	}
	treeObj.addNodes(pNode, nNode);
};
function _treeNodeEdit(e,index){
	var treeObj = $("#ztree-01").ztree("getZtree"),
	    name = $("#type-name").val(),
	    code = $("#type-code").val(),
	    nodes = treeObj.getSelectedNodes(),//
	    nNode = $.extend(true, {}, nodes[0], {
		name : name,
	    code : code,
		model : nodes[0].model,
		id : nodes[0].id
	});
	var that = this;
	treeObj.setting.callback.onRename(e,nodes.id,nNode,undefined);
};
function _jsonFunc(e){
	 var treeObj = $("#ztree-01").ztree("getZtree"), 
	     nodes = treeObj.getSelectedNodes(),
	     childNodes = treeObj.transformToArray(nodes), idAmouse = new Array(),
	     that = this;
	if(!nodes.model){
		for (i = 0; i < childNodes.length; i++) {
			idAmouse[i] = childNodes[i].id;
		}
		$.ajax({
			url : contextPath + '/data/model/getDataModelByIdAmouse',
			type : 'POST',
			data : {
				idAmouse : idAmouse.join(",")
			},
			success : function(ret) {
				ret = eval("("+ret+")");
			    this.istable = ret.istable ? true : false;
				ButtonFunc["model-edit"].call(this, e);
			}
		})
	}
	else 
		ButtonFunc["model-edit"].call(this, e);
	
}
/**
 * 页面初始化
 * 
 * @param data
 * @returns
 */
function InitPage(data) {
	if (data && data.designer) {
		DBDesigner.reloadData(data.designer);
	} else {
		DBDesigner.clear();
	}
}

var DataTmpl = {
	oid : window.oid
};

$callback.add(function() {
	var $left = $(".split-left"), $right = $(".split-right"), //
	$btn = $("#split-btn");
	$btn.on("click.button", "button", function() {
		$left.toggle();
		$right.toggleClass("col-sm-12").toggleClass("col-sm-10");
		ResizeBtn();
	});
});

$callback
		.add(function() {
			/**
			 * 页面还原
			 */
			if (DataTmpl.oid) {
				window.IntervalProcess.wait(function() {
					return DBDesigner.get();// 必须让iframe 加载完以后执行
				}, function() {
					// window.GetDesigner(DataTmpl.oid, InitPage);
				});
			}
		})
		.add(function() {
			/**
			 * 按钮注册事件
			 */
			$("button[t]").click(function(e) {
				e.preventDefault();
				var t = $(this).attr("t");
				ButtonFunc[t] && (ButtonFunc[t].call(this, e));
			});
		})
		.add(function() {
			/**
			 * 动态调整
			 */
			WinReszier.register($.proxy(ResizeHeight(), this));
		})
		.add(function() {
			/**
			 * 切换tab事件
			 */

		})
		.add(
				function() {
					/**
					 * TODO 表树
					 */

					var treeAddUrl = contextPath + "/data/model/tree", //
					nodeAddUrl = contextPath + "/data/model";
					var defCT = "application/x-www-form-urlencoded; charset=utf-8";
					function Ajax(url, data, fn, contentType) {
						$.ajax({
							url : url,
							data : data,
							success : fn,
							dataType : 'json',
							type : 'POST',
							contentType : contentType || defCT
						});
					}

					function AddNode(url, data, fn) {
						Ajax(url, JSON.stringify(data), fn, "application/json");
					}

					function DeleteNode(url, id, fn) {
						Ajax(url, {
							id : id
						}, fn, "");
					}

					var newCount = 1;
					var addStr = "<span class='button {2}' id='addBtn_{0}'" //
							+ " title='{1}' onfocus='this.blur();'></span>";
					function addHoverDom(treeId, treeNode) {

						var sObj = $("#" + treeNode.tId + "_span"), //
						ztree = $("#" + treeId).ztree("getZtree");
						if (treeNode.editNameFlag || //
						$("#addBtn_" + treeNode.tId).length > 0
								|| treeNode.model) {
							return;
						}
						if (treeNode.tables) {
							sObj.after(addStr.format(treeNode.tId, "刷新",
									"refresh"));
							$("#addBtn_" + treeNode.tId).bind(
									"click",
									function(e) {
										ztree.reAsyncChildNodes(treeNode,
												"refresh");
									});
							return;
						} else if (treeNode.table) {
							return;
						} else if (treeNode.views) {
							return;
						}
						sObj
								.after(addStr.format(treeNode.tId, "添加物理模型",
										"add"));
						$("#addBtn_" + treeNode.tId).bind("click", function(e) {
							e.model = true;
							ButtonFunc["model-add"].call(this, e);
						});
						$("#"+treeNode.tId+"_edit").bind("click", function(e) {
							_jsonFunc(e);
						});
					}

					function removeHoverDom(treeId, treeNode) {
						$("#addBtn_" + treeNode.tId).unbind().remove();
					}
					
					var setting = {
						view : {
							awesome : true,
							addHoverDom : addHoverDom,
							removeHoverDom : removeHoverDom,
						},
						edit : {
							enable : true,
							editNameSelectAll : true,
							showRemoveBtn : function(treeId, treeNode) {
								return treeNode.model !== undefined;
							},
							showRenameBtn : function(treeId, treeNode) {
								return treeNode.model !== undefined;
							}
						},
						data : {
							simpleData : {
								enable : true,
								idKey : "id",
								pIdKey : "parentId"
									
							}
						},
						async : {
							enable : true,
							contentType : "application/json",
							url : function(treeId, treeNode) {

								var method = (treeNode && treeNode.tables) ? //
								"getDataModelTablesByTreeId?treeId="
										+ treeNode.getParentNode().id
										: "getDataModels";

								return nodeAddUrl + "/" + method;
							},
							autoParam : [ "id", "name" ],
							dataFilter : function(treeId, parentNode,
									responseData) {
								if (responseData && responseData.length) {
									$
											.each(
													responseData,
													function(i, v) {
														if (v.model)
															v.icon = window.CONFIG.contextPath//
																	+ "/images/database_gear.png"
													});
								}
								return responseData;
							}
						},
						callback : {
							
						
							onSave : function(event, treeId, treeNode, fn) {
								var url = treeNode.model ? nodeAddUrl + //
								"/saveOrUpdateDetails" : treeAddUrl
										+ "/saveOrUpdate";
								AddNode(url, treeNode, function(ret) {
									treeNode.id = ret.id;
									fn && fn();
								});
							},
							beforeAsync : function(treeId, treeNode) {
								if (treeNode) {
									return !treeNode.views;
								}
								return true;
							},
							onAsyncSuccess : function(event, treeId, treeNode,
									msg) {
								var treeObj = $("#" + treeId).ztree("getZtree");
								var nodes = treeObj.getNodes();
								if (nodes && nodes.length) {
									var expand = setting.callback.onExpand;
									function eachNode(i, v) {
										!v.model
												&& treeObj.expandNode(v, true,
														false, false)//
												&& expand(event, treeId, v);
									}
									$.each(nodes, eachNode);
								}
							},
							onExpand : function(event, treeId, treeNode) {
								if (!treeNode || (treeNode.model !== false))
									return false;
								window.setTimeout(function() {
									var treeObj = $("#" + treeId).ztree(
											"getZtree"), //
									tableId = treeNode.id + "-tables", //
									node = treeObj.getNodeByParam("id",
											tableId, treeNode);
									!node && treeObj.addNodes(treeNode, [ {
										name : "Tables",
										tables : true,
										iconSkin : "tables",
										id : tableId,
										isParent : true
									}, {
										name : "Views",
										views : true,
										iconSkin : "views",
										id : treeNode.id + "-views",
										isParent : true
									} ]);
								}, 800);
							},
							onNodeCreated : function(event, treeId, treeNode) {
								if (treeNode.isNew) {
									setting.callback.onSave.apply(this,
											arguments);
								}
							},
							onRename : function(event, treeId, treeNode,
									isCancel) {
								if (treeNode.id) {
									setting.callback.onSave.apply(this,
											arguments);
								}
							},
							beforeRemove : function(treeId, treeNode) {
								return confirm("确定删除?");
							},
							onRemove : function(event, treeId, treeNode) {
								
								if(!treeNode.id){
									return;
								}
								
								var url = treeNode.model ? nodeAddUrl
											: treeAddUrl;
								
								if(true){
									alert("暂时不提供删除功能!")
									return;
								}
								
								DeleteNode(url + "/delete", treeNode.id,
											function(ret) {
												alert(ret.message);
											});
							},
							beforeClick : function(treeId, treeNode, clickFlag) {
								//传递参数
								if(!treeNode.model){
									DBDesigner.setData(treeNode);									
								}
								else{
									var node = treeNode.getParentNode();
									DBDesigner.setData(node);									
								}
								if (treeNode && treeNode.model) {
									GetDesigner(treeNode.id, function(ret) {
										if (ret && ret.json) {
											try {
												DBDesigner.reloadData(JSON.parse(ret.json));
											} catch (e) {
												console.log(e);
											}
										} else {
											DBDesigner.clear();
										}
									});
								}
							},
							beforeDrop : function(treeId, treeNodes,//
							targetNode, moveType) {
								// debugger;
								if (treeNodes && treeNodes[0]//
										&& targetNode) {
									if (treeNodes[0].model === undefined) {

										console.log(arguments);

										return false;
									} else {
										treeNodes[0].parentId = targetNode.id;
										setting.callback.onSave//
										.call(this, null, treeId, treeNodes[0]);
									}
								}
							},
							beforeDrag : function(treeId, treeNodes) {
								if (treeNodes && treeNodes[0]) {
									return treeNodes[0].table
											|| treeNodes[0].type == 'table';
								}
							},
							onDrop : function(e, treeId, treeNodes, targetNode,
									moveType) {
								// console.log(arguments);

								var node = treeNodes[0];

								node.type = "table";

								DBDesigner.AddNode(e, node)

								return true;
							}
						}
					};

					$("#ztree-01").css({
						'overflow' : 'scroll'
					}).ztree(setting, []);

				}).add(function() {
			/**
			 * 列
			 */

		});

/**
 * 设计区
 */
DBDesigner = (function() {
	var func = new Function(), frame;

	func.set = function(that) {
		frame = that;
	};

	func.get = function() {
		return frame;
	};

	func.GetToolKitFunc = function() {
		return func.GetDesignerWin().ToolKitFunc;
	};
	func.GetToolkit = function() {
		return func.GetToolKitFunc().getToolkit();
	};

	func.clear = function() {
		func.GetToolkit().clear();
	};

	/**
	 * 获取设计区内容
	 * 
	 * @returns
	 */
	func.GetDesignJSON = function() {
		return func.GetToolKitFunc().exportData();
	};

	/**
	 * 还原设计区
	 * 
	 * @returns
	 */
	func.InitDesigner = function(data) {
		return func.GetToolKitFunc().loadData(data);
	};

	/**
	 * 清空并加载
	 */
	func.reloadData = function(data) {
		return func.GetToolKitFunc().reloadData(data);
	}
	func.setData = function(data) {
		return func.GetToolKitFunc().setParamter(data);
	}
	/**
	 * 获取设计区window
	 * 
	 * @returns
	 */
	func.GetDesignerWin = function() {
		return frame.contentWindow;
	};

	/**
	 * 获取已选列
	 */
	func.GetSelectedColumns = function(json) {
		json = json || func.GetDesignJSON(), columns = [];
		if (json && json.nodes) {
			var eachColumns = function(i, c) {
				if (c.checked) {
					columns.push(c);
				}
			}, eachNodes = function(i, node) {
				if (node.columns) {
					$.each(node.columns, eachColumns);
				}
			};
			$.each(json.nodes, eachNodes);
		}
		return columns;
	};

	/**
	 * 重置
	 */
	func.reset = function() {
		window.setTimeout(function() {
			func.GetToolKitFunc().reset();
		}, 100);
	};

	/**
	 * 寻找顶层分类id
	 */
	function GetParentId(node) {
		if (node.getParentNode()) {
			return GetParentId(//
			node.getParentNode());
		}
		return node.id;
	}

	var columnsUrl = contextPath + "/data/model/getDataModelColumns";
	func.GetColumns = function(node, fn) {
		var id = GetParentId(node);
		$.getJSON(columnsUrl, {
			treeId : id,
			tableName : node.tableName
		}, fn);
	};

	func.AddNode = function(e, data) {

		func
				.GetColumns(
						data,
						function(ret) {

							!data.id && (data.id = data.tableName);
							var win = func.GetDesignerWin();
							// var os =
							// win.jsPlumb.getOffset($("i[jtk-node-type='table']",
							// win.document).get(0));

							// console.log(os);
							// var pz = win.newMiniview.getPanZoom();

							// var eventLocation = pz.mapLocation(os.left,
							// os.top);
							// console.log(eventLocation);
							data.noWin = true;
							var toolkit = func.GetToolKitFunc().getToolkit(), //
							position = {};
							var nodes = toolkit.getNodes();
							if (nodes && nodes.length) {
								var node = nodes[0], $iframe = $("iframe")
										.offset();
								var $t0 = $("div[data-jtk-node-id=" + node.id
										+ "]", win.document);
								if ($t0.get(0)) {
									var offset = $t0.offset(), nl = node.data.left || 0, nt = node.data.top || 0;
									var rl = offset.left - nl, rt = offset.top
											- nt;
									var x = e.clientX, y = e.clientY;
									position = {
										left : x - rl - $iframe.left,
										top : y - rt - $iframe.top
									};
								}
							}
							toolkit.getNodeFactory()(data.type, data,
									function(n) {
										toolkit.addNode(n, {
											position : position
										});
									}, e);
						});
	};

	return func;
})();

/**
 * 按钮事件
 */
ButtonFunc = (function() {
	var func = new Function();

	func.save = function(e, auto) {
		var designJson = DBDesigner.GetDesignJSON();

		var json = {
			designer : designJson
		};

		$.ajax({
			url : contextPath + '/data/saveDesigner',
			type : 'POST',
			data : {
				oid : DataTmpl.oid,
				json : JSON.stringify(json)
			},
			success : function(ret) {
				!auto && alert('操作成功!');
			}
		});
	};

	var treeId = "ztree-01";

	func["model-save"] = function(e, auto) {
		var designJson = DBDesigner.GetDesignJSON(),
		    treeObj = $("#" + treeId).ztree("getZtree"),
		    nodes = treeObj.getSelectedNodes(), pNode,
		    that = this;
		if (nodes && nodes.length == 1) {
			pNode = nodes[0];
			if (pNode.model !== true) {
				alert("非模型视图,不能保存!");
				return;
			}
			if(that.refresh){
				$.each(that.tablesData,function(index,table){
					$.each(designJson.nodes,function(i,node){
						if(node.id.toLowerCase() == table.tableName){
							node.code = table.code;
						}
					});
				});				
			}
			pNode.json = JSON.stringify(designJson);
			try {
				pNode.treeId = pNode.getParentNode().treeId;
			} finally {

			}
			treeObj.setting.callback.onSave(e, treeId, pNode, function(ret) {
				!auto && alert('操作成功!');
			});
		}
	};

	var index = 0, Node = {
		name : 'New Node',
		isNew : true,
		catalog : true,
		model : false
	};

	func["model-add"] = function(e) {
		var that = this,
		    $tableDetails = null
		    title = null;
		if(e.model){
			$tableDetails ='<div class="form-group has-success"><label class="control-label">模型名</label><div class="input-icon right"><input type="text" class="form-control" id="type-name" name="name"></div></div>';
			title = "新建模型";
		}
		else{
			$tableDetails ='<div class="form-group has-success"><label class="control-label">分类名</label><div class="input-icon right"><input type="text" class="form-control" id="type-name" name="name"></div><label class="control-label">code</label><div class="input-icon right"><input type="text" class="form-control" id="type-code" name="code"></div></div>';	
			title = "新建分类";
		}
		var bootstrapDialogOpts = {
				css : {
					width : '750px'
				},
				cssClass : 'goes-dialog',
				buttons : [ {
					label : '确定',
					cssClass : 'btn-primary btn-sm',
					action : function(dialog){
						var that = this;
						_treeNodeAdd(e,index);
						dialog.close();
					}
				}, {
					label : '关闭',
					cssClass : 'btn-default btn-sm',
					action : function(dialog) {
						dialog.close();
					}
				} ]
			};
		BootstrapDialog.show($.extend(true, bootstrapDialogOpts, {
			title : title,
			message : $tableDetails
		}));
	};
	func["model-edit"] = function(e) {
		
		var treeObj = $("#ztree-01").ztree("getZtree"),
		    nodes = treeObj.getSelectedNodes(),
		    that = this,
		    $tableDetails = null,
		    title = null,
		    //判断该分类有无表数据
		    istable = that.istable;
		switch (nodes[0].model) {
		case true:
			$tableDetails = "<div class='form-group has-success'><label class='control-label'>模型名</label>"
					+ "<div class='input-icon right'><input type='text' class='form-control' id='type-name' name='name' value='"
					+ nodes[0].name + "'></div></div>";
			title = "编辑模型";
			break;
		case false:
			if (istable) {
				$tableDetails = "<div class='form-group has-success'><label class='control-label'>分类名</label>"
						+ "<div class='input-icon right'><input type='text' class='form-control' id='type-name' name='name' value='"
						+ nodes[0].name
						+ "'>"
						+ "</div><label class='control-label'>code</label><div class='input-icon right'>"
						+ "<input type='text' class='form-control' id='type-code' name='code' readonly='readonly'  value='"
						+ nodes[0].code + "'></div></div>";
				title = "编辑分类";
			} else {
				$tableDetails = "<div class='form-group has-success'><label class='control-label'>分类名</label>"
						+ "<div class='input-icon right'><input type='text' class='form-control' id='type-name' name='name' value='"
						+ nodes[0].name
						+ "'>"
						+ "</div><label class='control-label'>code</label><div class='input-icon right'>"
						+ "<input type='text' class='form-control' id='type-code'  name='code' value='"
						+ nodes[0].code + "'></div></div>";
				title = "编辑分类";
			}
			break;
		}
		var bootstrapDialogOpts = {
				css : {
					width : '750px'
				},
				cssClass : 'goes-dialog',
				buttons : [ {
					label : '确定',
					cssClass : 'btn-primary btn-sm',
					action : function(dialog){
						var that = this;
						_treeNodeEdit(e,index);
						dialog.close();
					}
				}, {
					label : '关闭',
					cssClass : 'btn-default btn-sm',
					action : function(dialog) {
						dialog.close();
					}
				} ]
			};
		BootstrapDialog.show($.extend(true, bootstrapDialogOpts, {
			title : title,
			message : $tableDetails
		}));
	};
	func.preview = function(e) {
		alert("preview");
	};

	func.validate = function(e) {
		alert("validate");
	};

	func.buildMetadatas = function(e) {
		var treeObj = $("#" + treeId).ztree("getZtree");
		var nodes = treeObj.getSelectedNodes(), node;
		if (nodes && nodes.length == 1) {
			node = nodes[0];
			if (node.model !== true) {
				alert("非模型视图,不能操作!");
				return;
			} else {
				func.model2db({
					id : node.id,
					code :node.code
				});
			}
		}
	};

	func.model2db = function(obj) {
		if (!confirm("刷新表结构?")) {
			return false;
		}
		var url = contextPath + "/data/model/model2db";
		$.post(url, {
			id : obj.id,
			code : obj.code
		}, function(ret) {
			if(ret != null){
				this.refresh = true;
				this.tablesData = eval(ret);
				ButtonFunc["model-save"].call(this, null,false);
			}
			
			/*ret && ret.message && alert(ret.message);*/
		}, "JSON");
	};

	return func;
})();