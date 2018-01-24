/**
 * 
 */
//初始化布局
window.MSPointerEvent = null;
window.PointerEvent = null;
var mainHeight = $(window).height();
var mainWidth = $(window).width();
$('#vertical').height(mainHeight);
$("#vertical").kendoSplitter({
	orientation : "vertical",
	panes : [ {
		collapsed : false,
		resizable : false,
		scrollable : false,
		collapsible : true,
		size : "40px"
	}, {
		collapsed : false,
		scrollable : false
	} ]
});
var splitter = $("#horizontal").kendoSplitter({
	panes : [ {
		collapsed : false,
		collapsible : true,
		collapsedSize : "0px",
		max : "300px",
		resizable : false,
		size : "200px",
		scrollable : true
	}, {
		scrollable : false
	} ]
});
//新建选项卡
var tabStrip = $("#tabstrip").kendoTabStrip({
	activate : onActivate,
	select : onSelect
}).data("kendoTabStrip");
var selectTab;
//默认设置第一个选项卡选中
tabStrip.select(0);
//选项卡选中方法
function onSelect(e) {
	selectTab = e.contentElement.id;
}
//选项卡选中渲染完成后执行方法
function onActivate(e) {
	if (selectTab == "tabstrip-1") {
		bindPageClickEvent($("#" + selectTab + " iframe"), 0);
	}
	//高度自适应
	setTimeout("resizeTab()", 200);
}
//选项卡内容高度自适应
function resizeTab() {
	$("#" + selectTab).height($("#horizontal").height() - 32);
	$("#" + selectTab).css("padding", "0px");
}
$(window).resize(function() {
	mainHeight = $(window).height();
	$("#vertical").height(mainHeight);
	$("#content").height(mainHeight - 40);
});
//新增模型
function add() {
	if (confirm("确认新建流程？")) {
		//获取当前选中的节点
		var compId;
		var event;
		var parentNode;
		var modelName;
		var modelKey;
		if (selectedNode) {
			compId = selectedNode.pId;
			event = selectedNode.event;
			//获取父节点
			parentNode = treeObj.getNodeByParam("id", selectedNode.pId, null);
			modelName = selectedNode.compName + selectedNode.name + "事件执行流程";
			modelKey = pageId + "_" + compId + "_" + event;
		}
		$.ajax({
			url : contextPath+"/rs/page/workflow/createPageWorkFlow",
			type : "post",
			async : false,
			dataType : "json",
			data : {
				pageId : pageId,
				modelName : modelName,
				modelKey : modelKey,
				modelDesc : "",
				compId : compId,
				event : event
			},
			success : function(data) {
				if (data) {
					if (data.result) {
						if (data.result == 1) {
							//跳转到编辑页面
							var url = "/workflow/modeler.html?modelId="
									+ data.modelId+"&definedType=pageflow";
							openPage(url, "流程设计");
							bindWorkFlowTreeData(0);
						} else {
							alert("未找到流程定义对应的页面或控件！");
						}
					}
				}
			},
			error : function() {
				alert("新建流程失败！");
			}

		});
	}
}
//跳转到页面
splitter = splitter.data("kendoSplitter");
function openPage(url, name) {
	//验证url是否使用相对路径，如果使用相对路径则补全
	if (url.indexOf("http") < 0) {
		url = webPath + url;
	}
	$('#process').attr("src", url);
	tabStrip.select(1);
	$("#tab-name2 .k-link").text(name);
	//splitter.ajaxRequest("#designArea", url);
}
//树初始化设置
var setting = {
	data : {
		key : {
			title : "t"
		},
		simpleData : {
			enable : true
		}
	},
	callback : {
		//声明单击回调事件
		beforeClick : beforeClick
	}
};
//单击回调事件
var selectedNode;
//树节点点击事件
function beforeClick(treeId, treeNode) {
	selectedNode = treeNode;
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	controlToolBarStatus(treeNode);
	//如果是父节点单击则展开
	if (!treeNode.nodeType
			|| (treeNode.isParent && treeNode.nodeType != 'component' && treeNode.nodeType != 'event')) {
		zTree.expandNode(treeNode);
		return false;
	} else if (treeNode.nodeType == 'component') {
		//var urlAddr ="/mx/form/defined/getFormPageHtmlById?id=${param.pageId}";
		//openPage(urlAddr);
		tabStrip.select(0);
		$('#process').attr("src", null);
		bindPageClickEvent($("#" + selectTab + " iframe"), 0);
	} else if (treeNode.nodeType == 'event') {
		//bindPageClickEvent($("#"+selectTab+" iframe"),0);
		//验证当前事件节点是否有定义流程
		if (!treeNode.procModelId || treeNode.procModelId == '') {
			var urlAddr = "/mx/page/workflowDetail/view?compName="
					+ encodeURIComponent(encodeURIComponent((treeNode.compName
							+ "[" + treeNode.pId + "]"))) + "&event="
					+ encodeURIComponent(encodeURIComponent(treeNode.name));
			openPage(urlAddr, "流程查看");
		} else {
			var urlAddr = "/mx/page/workflowDetail/view?procModelId="
					+ treeNode.procModelId
					+ "&compName="
					+ encodeURIComponent(encodeURIComponent((treeNode.compName
							+ "[" + treeNode.pId + "]"))) + "&event="
					+ encodeURIComponent(encodeURIComponent(treeNode.name));
			if (treeNode.procDeployStatus && treeNode.procDeployStatus == "1"
					&& treeNode.procDeployId) {
				urlAddr = urlAddr
						+ "&procDeployId="
						+ encodeURIComponent(encodeURIComponent(treeNode.procDeployId));
			}
			openPage(urlAddr, "流程查看");
		}
	}
}
//树控件数据绑定
function bindWorkFlowTreeData(triggerFlag) {
	var selectedNodeId;
	if (treeObj) {
		if (selectedNode) {
			selectedNodeId = selectedNode.id;
		}
	}
	$.ajax({
		url : contextPath+"/rs/page/workflow/getPageWorkFlows",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			pageId : pageId
		},
		success : function(data) {
			if (data) {
				var nodes = eval(data);
				$.fn.zTree.init($("#workflowTree"), setting, nodes);
				treeObj = $.fn.zTree.getZTreeObj("workflowTree");
				triggerNodeClick(selectedNodeId, triggerFlag);
			}
		},
		error : function() {

		}

	});

}
//新建模型方法
function createModel() {
	if (pageId == '') {
		alert("页面未指定");
		return;
	}
	//获取当前选中的节点
	if (selectedNode && selectedNode.nodeType == 'event') {
		if (selectedNode.procDefkey && selectedNode.procDefkey != "") {
			alert("该流程已经存在");
			return;
		} else {
			//弹出新建流程窗口
			$("#addDialog").data("kendoWindow").open();
			$("#addDialog").data("kendoWindow").center();
		}
	} else {
		alert("请选择事件节点");
		return;
	}
}
//修改模型方法
function editModel() {
	if (pageId == '') {
		alert("页面未指定");
		return;
	}
	//收起左侧页面构件树
	var splitter = $("#horizontal").data("kendoSplitter");
	splitter.collapse(".k-pane:first");
	//获取当前选中的节点
	//var treeObj = $.fn.zTree.getZTreeObj("workflowTree");
	if (selectedNode && selectedNode.procModelId) {
		var urlAddr = "/workflow/modeler.html?modelId="
				+ selectedNode.procModelId+"&definedType=pageflow";
		openPage(urlAddr, "流程设计");
	} else if (selectedNode.procDefId) {
		//异步转换流程为可编辑流程
		$.ajax({
			url : contextPath+"/rs/page/workflow/convertPageWorkFlow",
			type : "post",
			async : false,
			dataType : "json",
			data : {
				pageId : pageId,
				processDefId : selectedNode.processDefId
			},
			success : function(data) {
				if (data) {
					if (data.result) {
						if (data.result == 1 && data.modelId) {
							var urlAddr = "/workflow/modeler.html?modelId="
									+ data.modelId+"&definedType=pageflow";
							openPage(urlAddr, "流程设计");
							bindWorkFlowTreeData();
						} else if (data.result == 0) {
							alert("转换为可编辑流程失败！原因：文件中未包含有效的BPMN定义或BPMN DI信息");
						} else if (data.result == -1) {
							alert("转换为可编辑流程异常！");
						}
					}
				}
			},
			error : function() {
				alert("转换为可编辑流程异常！");
			}

		});
	}
}
//弹出删除模型窗口
function openDeleteModelDialog() {
	if (pageId == '') {
		alert("页面未指定");
		return;
	}
	//获取当前选中的节点
	//var treeObj = $.fn.zTree.getZTreeObj("workflowTree");
	if (selectedNode && selectedNode.procModelId) {
		var okTemplate = {
			text : "&nbsp&nbsp确定&nbsp&nbsp",
			callback : function(kendoConfirm) {
				kendoConfirm.close();
				deleteModel();
			}
		};
		kendo.confirm("确认删除流程\"" + selectedNode.name + "\"？", {
			title : "<font size=\"2\">系统提醒</font>",
			width : "250px",
			height : "150px"
		}, okTemplate);
	}
}
//删除模型方法
function deleteModel() {

	$.ajax({
		url : contextPath+"/rs/page/workflow/deletePageWorkFlow",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			pageId : pageId,
			modelId : selectedNode.procModelId,
			eventProcId : selectedNode.eventProcId
		},
		success : function(data) {
			if (data) {
				if (data.result) {
					if (data.result == 1) {
						alert("删除成功！");
						bindWorkFlowTreeData();
					} else {
						alert("删除失败！");
					}
				}
			}
		},
		error : function() {
			alert("删除异常！");
		}

	});
}
//弹出发布模型窗口
function openDeployModelDialog() {
	if (pageId == '') {
		alert("页面未指定");
		return;
	}
	//获取当前选中的节点
	//var treeObj = $.fn.zTree.getZTreeObj("workflowTree");
	if (selectedNode && selectedNode.procModelId) {
		var okTemplate = {
			text : "&nbsp&nbsp确定&nbsp&nbsp",
			callback : function(kendoConfirm) {
				kendoConfirm.close();
				deployModel();
			}
		};
		kendo.confirm("确认发布流程\"" + selectedNode.name + "\"？", {
			title : "<font size=\"2\">系统提醒</font>",
			width : "250px",
			height : "150px"
		}, okTemplate);
	}

}
//部署模型方法
function deployModel() {
	$.ajax({
		url : contextPath+"/rs/page/workflow/deployPageWorkFlow",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			pageId : pageId,
			modelId : selectedNode.procModelId,
			eventProcId : selectedNode.eventProcId
		},
		success : function(data) {
			if (data) {
				if (data.result) {
					if (data.result == 1) {
						alert("部署成功！");
						bindWorkFlowTreeData();
					} else {
						alert("部署失败！");
					}
				}
			}
		},
		error : function() {
			alert("部署异常！");
		}

	});
}
//导出模型方法
function exportModel() {
	//获取当前选中的节点
	//var treeObj = $.fn.zTree.getZTreeObj("workflowTree");
	if (selectedNode && selectedNode.procModelId) {
		window.location = contextPath+"/rs/page/workflow/exportPageWorkFlow?pageId="+pageId+"&modelId="
				+ selectedNode.procModelId;

	}
}
$("#importDialog")
		.kendoWindow(
				{
					width : 400,
					height : 200,
					title : "<img src='"+contextPath+"/images/document_small_upload.png' style='vertical-align: middle;'/><span style='margin-left:5px;'>导入模型</span>",
					visible : false,
					modal : true
				});

function onSuccess(e) {
	bindWorkFlowTreeData();
}
//打开流程导入对话框
function openImportDialog() {
	//弹出新建流程窗口
	$("#importDialog").data("kendoWindow").open();
	$("#importDialog").data("kendoWindow").center();
	var saveUrl = contextPath+"/mx/page/workflow/importPageWorkFlow?pageId="+pageId;
	var compId;
	var event;
	var eventProcId;
	if (selectedNode) {
		compId = selectedNode.pId;
		event = selectedNode.event;
		eventProcId = selectedNode.eventProcId;
		if (compId) {
			saveUrl = saveUrl + "&compId=" + compId;
		}
		if (event) {
			saveUrl = saveUrl + "&event=" + event;
		}
		if (eventProcId) {
			saveUrl = saveUrl + "&eventProcId=" + eventProcId;
		}
	}
	var upload = $("#uploadfile").data("kendoUpload");
	if (upload) {
		upload.setOptions({
			saveUrl : saveUrl,
			files : null
		});
	} else {
		$("#uploadfile").kendoUpload({
			async : {
				saveUrl : saveUrl,
				removeUrl : "remove",
				autoUpload : false
			},
			localization : {
				statusFailed : "导入模型失败",
				statusUploaded : "导入模型成功"
			},
			multiple : false,
			success : onSuccess

		});
	}
}
//var treeObj = $.fn.zTree.getZTreeObj("workflowTree");
var treeObj;
bindWorkFlowTreeData();
$(document).ready(function() {
	//工具栏渲染
	$("#toolbar").kendoToolBar({
		items : [ {
			type : "button",
			id : "create",
			text : "新  建",
			enable : false,
			imageUrl : contextPath + "/images/page_add.png",
			click : add
		}, {
			type : "button",
			id : "edit",
			text : "编  辑",
			enable : false,
			imageUrl : contextPath + "/images/page_edit.png",
			click : editModel
		}, {
			type : "button",
			id : "deploy",
			text : "发布",
			enable : false,
			imageUrl : contextPath + "/images/msn_messenger.png",
			click : openDeployModelDialog
		}, {
			type : "separator"
		}, {
			type : "button",
			id : "import",
			text : "导  入",
			enable : false,
			imageUrl : contextPath + "/images/document_small_upload.png",
			click : openImportDialog
		}, {
			type : "button",
			id : "export",
			text : "导  出",
			enable : false,
			imageUrl : contextPath + "/images/download.png",
			click : exportModel
		}, {
			type : "separator"
		}, {
			type : "button",
			id : "delete",
			text : "删  除",
			enable : false,
			imageUrl : contextPath + "/images/page_delete.png",
			click : openDeleteModelDialog
		}]
	});

	//默认选中节点
	//selectedNode= treeObj.getNodeByParam("event", "pageInit", null);
	//if(selectedNode)
	//{
	// triggerNodeClick(selectedNode.id);
	//}
});

//页面视图
$("#pageViewBt").kendoButton({
	imageUrl : contextPath+"/images/layout-design.png"
});
var button = $("#pageViewBt").data("kendoButton");
button.bind("click", function(e) {
	$("#pageViewDialog").data("kendoWindow").open();
	$("#pageViewDialog").data("kendoWindow").center();
	bindPageClickEvent($("#pageViewDialog iframe"), 0);
});
//页面视图
$("#pageViewDialog")
		.kendoWindow(
				{
					width : "85%",
					height : "90%",
					actions : [ "Refresh", "Minimize", "Maximize", "Close" ],
					title : "页面视图",
					visible : false,
					modal : true,
					content : webPath+"mx/form/defined/getFormPageHtmlById?id="+pageId,
					iframe : true
				});

//给iframe页面控件绑定方法
var currComponent;
function bindPageClickEvent(frame, triggerFlag) {
	//获取当前Tree选中的控件
	//var treeObj = $.fn.zTree.getZTreeObj("workflowTree");
	var selectedComp;
	var currSelectedComp;
	if (selectedNode && selectedNode.nodeType
			&& selectedNode.nodeType == 'component') {
		selectedComp = selectedNode.id;
		currSelectedComp = selectedNode.id;
	} else if (selectedNode && selectedNode.nodeType
			&& selectedNode.nodeType == 'event') {
		selectedComp = selectedNode.pId;
		currSelectedComp = selectedNode.id;
	}
	var doc = frame.contents();
	var crtltypes = doc.find('[crtltype]');
	if (crtltypes && crtltypes.length) {
		var pageId = frame.attr("id");
		doc.find("body").bind('click', function(index, item) {
			return false;
		});
		$.each(crtltypes, function(index, item) {
			$(item).unbind('click');
			$(item).bind(
					'click',
					function(e) {
						if (currComponent) {
							currComponent.css("border", "");
						}
						$(this).css("border", "5px solid red");
						currComponent = $(this);
						//设置tree选中
						if ($(this).attr("id") != currSelectedComp
								&& ((!selectedNode || !selectedNode.pId) || $(
										this).attr("id") != selectedNode.pId))
							triggerNodeClick($(this).attr("id"), triggerFlag);
						//触发前一个页面click
						return false;
					});
			//初始化选中控件
			if ($(item).attr("id") == selectedComp) {
				$(item).click();
			}
		});
	}
}
//弹出构件规则设置窗口
function openRuleSetDialog(url, title, width, height, modal) {
	$("#ruleSetDialog").kendoWindow({
		width : width,
		height : height,
		actions : [ "Refresh", "Minimize", "Close" ],
		title : title,
		visible : false,
		modal : modal,
		content : webPath + url,
		iframe : true,
		animation : false,
		resizable : false
	});
	$("#ruleSetDialog").data("kendoWindow").center();
	$("#ruleSetDialog").data("kendoWindow").open();
}
//选中树节点并触发点击事件
function triggerNodeClick(id, triggerFlag) {
	var node = treeObj.getNodeByParam("id", id, null);
	controlToolBarStatus(node);
	if (node && node != selectedNode) {
		treeObj.selectNode(node);
		selectedNode = node;
		if (triggerFlag == undefined || triggerFlag != 0) {
			$("#" + node.tId + "_a").trigger("click");
		}

	}
}
//控制菜单按钮状态
function controlToolBarStatus(node) {
	var toolbar = $("#toolbar").data("kendoToolBar");
	if (toolbar) {
		toolbar.enable("#create", false);
		toolbar.enable("#edit", false);
		toolbar.enable("#deploy", false);
		toolbar.enable("#import", false);
		toolbar.enable("#export", false);
		toolbar.enable("#copy", false);
		toolbar.enable("#delete", false);
		if (node && node.nodeType && node.nodeType == 'event') {
			if (!node.procModelId || node.procModelId == '') {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#deploy", false);
				toolbar.enable("#import");
				toolbar.enable("#export", false);
				toolbar.enable("#copy", false);
				toolbar.enable("#delete", false);
			} else {
				toolbar.enable("#create", false);
				toolbar.enable("#edit");
				if (!node.procDeployStatus || node.procDeployStatus != '1') {
					toolbar.enable("#deploy");
				}
				toolbar.enable("#import");
				toolbar.enable("#export");
				toolbar.enable("#copy");
				toolbar.enable("#delete");
			}
		}
	}
}
//更新流程为未发布状态
function updateDeployStatus() {
	if (selectedNode.procDeployStatus && selectedNode.procDeployStatus == '1') {
		$.ajax({
			url : contextPath+"/rs/page/workflow/updateDeployStatus",
			type : "post",
			async : false,
			dataType : "json",
			data : {
				eventProcId : selectedNode.eventProcId
			},
			success : function(data) {
				if (data) {
					if (data.result) {
						if (data.result == 1) {
							bindWorkFlowTreeData(0);
							var toolbar = $("#toolbar").data("kendoToolBar");
							toolbar.enable("#deploy", true);

						} else if (data.result == 0) {
							console.log("更新eventProcId:"
									+ selectedNode.eventProcId + "发布状态为未发布失败");
						} else if (data.result == -1) {
							console.log("更新eventProcId:"
									+ selectedNode.eventProcId + "发布状态为未发布异常");
						}
					}
				}
			},
			error : function() {

			}

		});
	}
}