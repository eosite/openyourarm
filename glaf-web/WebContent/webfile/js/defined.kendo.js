/**
 * 定义平台js   defined.kendo.js 
 * klaus.wang 
 */

/**
 * 布局 start
 */

var $tabstrip = $(".tabstrip"),
	$vertical = $("#vertical"),
	contextPath = mtxx.contextPath;;

$vertical.css({
	height: $(window).height()
}).kendoSplitter({
	orientation: "vertical",
	panes: [{
		collapsible: false,
		resizable: false,
		scrollable: false,
		size: '32px'
	}, {
		collapsible: true,
		resizable: true,
		scrollable: false
	}, {
		collapsible: true,
		resizable: true,
		scrollable: false,
		size: '4%'
	}],
	layoutChange: onResize,
});

$("#center-pane").kendoSplitter({
	orientation: "horizontal",
	panes: [{
		collapsible: true,
		resizable: true,
		scrollable: false,
		size: '252px'
	}, {
		collapsible: true,
		resizable: true,
		scrollable: true
	}, {
		collapsible: true,
		resizable: true,
		scrollable: false,
		size: '340px'
	}]
});

var $tableTreeTabStrip = $("#tabletree").kendoTabStrip({
	select: function(e) {
		var dd = $(this.wrapper);
		$('#' + e.contentElement.id).height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
	}
}).data("kendoTabStrip");

$tableTreeTabStrip.select("li:first");

function onResize(e) {

	$vertical.css({
		height: $(window).height()
	});

	$tabstrip.css({
		height: $tabstrip.closest('div[role=group]').height()
	});

}

$tabstrip.each(function(i, item) {
	var $this = $(this);
	mtxx[$this.attr('id')] = $this.kendoTabStrip({
		animation: {
			open: {
				effects: "fadeIn"
			}
		},
		select: function(e) {
			var dd = $(this.wrapper);
			$('#' + e.contentElement.id).height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
		}
	});
});

/**
 * 保存设置
 */
$('#button-1').kendoButton({
	imageUrl: mtxx.contextPath + "/images/database_save.png",
	click: function() {
		if (mtxx.saveData) {
			var field = null,
				val, collection = new Object(),
				ok = false;
			var json = mtxx.resultData.data.value || new Object();
			$(".prop-table").each(function(i, v) {

				var id = $(this).attr('id');

				if (!(id in collection)) { // 按数据类型分类
					collection[id] = new Object();
				}

				$(this).find("input[idField]").each(function(index, item) {

					field = jQuery(this).attr('idfield');
					if (field) {
						ok = true;
						val = jQuery(this).val(); // ||
						// json[field];
						json[field] = val;
						collection[id][field] = val;
					}
				});
			});

			if (ok) {
				//	if (!saveData.formRuleId) {
				$('#button-1').attr("disabled", true);
				//	}
				var saveData = mtxx.saveData;
				saveData.id = saveData.formRuleId;
				saveData.value = JSON.stringify(json);
				saveData.collection = JSON.stringify(collection);

				mtxx.jQueryFrame.data('save', {
					id: saveData.name,
					isSave: true
				});


				//				jQuery.post(mtxx.url + '/saveOrUpdateFormRule', saveData, function(data) {
				//					if (data) {
				//						$('#button-1').removeAttr("disabled");
				//						if(data.statusCode == -999){
				//							alert(data.message);
				//							return;
				//						}
				//						saveData.formRuleId = data.id;
				//						if (!window.pageRoleInit) {
				//							alert('操作成功!');
				//						}
				//						window.pageRoleInit = false;
				//					}
				//				}, 'json');


				var callback_ = function(data) {
					if (data) {
						$('#button-1').removeAttr("disabled");
						if (data.statusCode == -999) {
							alert(data.message);
							return;
						}
						saveData.formRuleId = data.id;
						if (!window.pageRoleInit) {
							alert('操作成功!');
						}
						window.pageRoleInit = false;
					}
				};

				//jQuery.post(mtxx.url + '/saveOrUpdateFormRule', saveData, callback_, 'json');

				$.ajax({
					type: 'POST',
					data: JSON.stringify(saveData),
					contentType: 'application/json',
					dataType: 'JSON',
					url: mtxx.url + '/saveOrUpdateFormRule'
				}).done(callback_).error(function(msg) {
					alert("服务器出错!");
				});
			}
		} else {
			alert('请配置属性!');
		}

	}
});

/**
 * 事件流程
 */
$('#button-22').kendoButton({
	imageUrl: mtxx.contextPath + "/images/eye.png",
	click: function() {
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		if (nodes && nodes.length) {
			var node = nodes[0];
			if (node.formType == 0) {
				window.open(mtxx.contextPath + '/mx/form/defined/workflow/workflow2?pageId=' + node.id);
			} else {
				alert("请选择模板,不能选择分类");
			}
		} else {
			alert('请选择左侧树分类节点');
		}

	}
});

/**
 * 页面设计
 */
$('#button-11').kendoButton({
	imageUrl: mtxx.contextPath + "/images/eye.png",
	click: function() {
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		if (nodes && nodes.length) {
			var node = nodes[0];
			if (node.formType == 0) {
				var uiType = node.uiType ? node.uiType : 'kendo';
				// window.open(mtxx.contextPath + '/mx/page/designer/metro?id=' + node.id + '&uiType=' + uiType);
				window.open(mtxx.contextPath + '/mx/page/designer/metro_new?id=' + node.id + '&uiType=' + uiType);
			} else {
				alert("请选择模板,不能选择分类");
			}
		} else {
			alert('请选择左侧树分类节点');
		}

	}
});

// $('#button-113').kendoButton({
// 	imageUrl: mtxx.contextPath + "/images/eye.png",
// 	click: function() {
// 		if (mtxx.jQueryFrame) {
// 			if (mtxx.saveData) {
// 				var pageId = mtxx.saveData.pageId;
// 				if (pageId && componentName && dataRole) {
// 					var url = mtxx.contextPath + '/mx/form/defined/ex/bulkImportComponent?pageId=' + pageId;
// 					$.layer({
// 						type: 2,
// 						maxmin: true,
// 						shadeClose: true,
// 						title: "保存设置",
// 						closeBtn: [0, true],
// 						shade: [0.6, '#000'],
// 						border: [10, 0.3, '#000'],
// 						offset: ['', ''],
// 						fadeIn: 100,
// 						area: ['99%', '99%'],
// 						iframe: {
// 							src: url
// 						}
// 					});
// 				}
// 			}
// 		} else {
// 			alert('请选择!');
// 		}
// 	}
// });

/**
 * 静态发布页面
 * @param  {[type]}
 * @return {[type]}
 */
$('#button-113').kendoButton({
	imageUrl: mtxx.contextPath + "/images/eye.png",
	click: function() {
		var url = mtxx.contextPath + '/mx/form/defined/ex/staticPublish';
		$.layer({
			type: 2,
			maxmin: true,
			shadeClose: false,
			title: "静态发布设置",
			closeBtn: [0, true],
			shade: [0, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['', ''],
			fadeIn: 100,
			area: ['600px', '99%'],
			iframe: {
				src: url
			}
		});
	}
});


$('#button-112').kendoButton({
	imageUrl: mtxx.contextPath + "/images/eye.png",
	click: function() {
		if (mtxx.jQueryFrame) {
			if (mtxx.saveData) {
				var pageId = mtxx.saveData.pageId;
				var componentName = mtxx.saveData.name;
				var dataRole = mtxx.saveData.dataRole;
				var ruleId = mtxx.saveData.formRuleId;
				if (pageId && componentName && dataRole) {
					var url = mtxx.contextPath + '/mx/form/defined/ex/importComponent?pageId=' + pageId + '&dataRole=' + dataRole + '&componentName=' + componentName + '&ruleId=' + ruleId;
					$.layer({
						type: 2,
						maxmin: true,
						shadeClose: true,
						title: "保存设置",
						closeBtn: [0, true],
						shade: [0.6, '#000'],
						border: [10, 0.3, '#000'],
						offset: ['', ''],
						fadeIn: 100,
						area: ['99%', '99%'],
						iframe: {
							src: url
						}
					});
				}
			}
		} else {
			alert('请选择!');
		}
	}
});

$('#button-111').kendoButton({
	imageUrl: mtxx.contextPath + "/images/eye.png",
	click: function() {
		if (mtxx.jQueryFrame) {
			var id = mtxx.jQueryFrame.attr('id');
			if (id) {
				var $button = this.element;
				if ($button.attr("disabled")) {
					return;
				}
				$button.attr("disabled", "disabled");
				$.ajax({
					url: mtxx.url + '/initPageComponent',
					type: 'post',
					data: {
						id: id
					},
					dataType: 'json',
					async: true,
					success: function(msg) {
						if (msg && msg.statusCode == '500') {
							alert(msg.message);
						} else {
							alert('操作成功!');
						}
					},
					complete: function() {
						$button.removeAttr("disabled");
					},
					error: function(e, errorText, errorThrown) {
						alert("异常错误,请稍后再试.");
					}
				})
			}
		} else {
			alert('请选择!');
		}

	}
});

/**
 * 预览
 */
$('#button-2').kendoButton({
	imageUrl: mtxx.contextPath + "/images/eye.png",
	click: function() {
		if (mtxx.jQueryFrame) {
			var id = mtxx.jQueryFrame.attr('id');
			if (id) {
				var url = mtxx.contextPath + "/mx/form/page/viewPage?" + $.param({
					isPublish: true,
					id: id
				});
				window.open(url);
			}
		} else {
			alert('请选择!');
		}

	}
});

/**
 * 增加分类
 */
$('#button-3').kendoButton({
	imageUrl: mtxx.contextPath + "/images/documents_add.png",
	click: function() {
		createSort();
	}
});
/**
 * 是否显示禁用项
 */
$('#showLockedBox').on('change', function(e) {
	var isChecked = e.target.checked;
	mtxx.zTree.setting.async.otherParam = {
		locked: isChecked,
		pageCategory: $("#pageCategory").data("kendoDropDownList").value()
	};
	mtxx.zTree.reAsyncChildNodes(null, 'refresh');
});
/**
 * 搜索
 */
$("#searchTextBox").on("keydown", function(e) {
	if (e.keyCode == 13) {
		$("#button-4").trigger('click');
	}
})
$('#button-4').kendoButton({
	imageUrl: mtxx.contextPath + "/images/search.png",
	click: function() {
		var searchTextBox = $("#searchTextBox"),
			val = searchTextBox.val();
		var f = mtxx.zTree.expandAll(false);

		function updateHighlight(nodes, flag) {
			$.each(nodes, function(i, node) {
				node.highlight = flag || false;
				mtxx.zTree.updateNode(node);
			});
		}
		if (!f) {
			setTimeout(function() {
				var ns = searchTextBox.data('nodes');
				if (ns && ns[0]) {
					updateHighlight(ns, false);
				}
				if (val) {
					var nodes = mtxx.zTree.getNodesByParamFuzzy("name", val, null);
					if (nodes && nodes[0]) {
						$.each(nodes, function(i, node) {
							if (!node.isParent) {
								node = node.getParentNode();
							}
							mtxx.zTree.expandNode(node, true, true, false);
						});
						updateHighlight(nodes, true);
						searchTextBox.data('nodes', nodes);
					}
				}
			}, 500);
		}
	}
});
/** * 布局 end */

/**
 * 右键菜单 start
 */
var lis = ["<li onclick='createSort(mtxx.selectedNode);' >增加分类</li>", "<li onclick='showSort(mtxx.selectedNode);' >分类排序</li>",
	"<li onclick='mtxx.zTree.setting.callback.onCreate.call();' >增加模板</li>", "<li onclick='mtxx.zTree.editName(mtxx.selectedNode);' >修改模板名称</li>",
	"<li onclick='mtxx.zTree.setting.callback.onUpdate.call();' >更新模板</li>", "<li onclick='mtxx.zTree.setting.callback.onEexport.call();' >导出模板</li>",
	"<li onclick='mtxx.zTree.setting.callback.onCopy.call();' >复制模板</li>", "<li onclick='mtxx.zTree.setting.callback.onRemove.call();' >删除</li>",
	"<li onclick='ztreeFuncs.onEnabled(mtxx.selectedNode);' >启用禁用</li>"
];
$("<ul>", {
	id: 'context-menu'
}).append(lis.join('')).appendTo(document.body).kendoContextMenu({
	target: "#container",
	alignToAnchor: true
});
/**
 * 右键菜单 end
 */

/**
 * 页面初始方法
 */
$(function() {
	mtxx.pageCategory = $.cookie('pageCategory');
	initPageTree();

	var dictSort = mtxx.dictSort = {};
	mtxx.getDictByCode('property_sort', function(data) {
		if (!dictSort.data) {
			dictSort.data = new Object();
		}
		$.each(data, function(i, v) {
			dictSort.data[v.code] = v;
		});
	});

	// ui类型 url : mtxx.url + '/getDictByCode',
	$("#uiTypeSelect").kendoDropDownList({
		dataSource: new kendo.data.DataSource({
			transport: {
				read: {
					url: mtxx.url + '/getDictByCode',
					data: {
						code: 'ui_type'
					},
					type: 'post',
					dataType: 'json',
				}
			}
		}),
		dataTextField: "name",
		dataValueField: "code",
		animation: false,
		enable: true,
		change: function(e) {
			var saveData = mtxx.saveData,
				uiType = this.value();
			$.ajax({
				url: mtxx.contextPath + "/mx/form/defined/updateUiType",
				data: {
					id: saveData.pageId,
					uiType: uiType,
				},
				type: 'post',
				dataType: 'json',
				async: false,
				success: function(data) {

				},
				error: function(data) {
					alert('切换失败，请稍后重试！');
				}
			});
		}
	});

	$("#pageCategory").kendoDropDownList({
		dataSource: new kendo.data.DataSource({
			transport: {
				read: {
					url: mtxx.contextPath + '/rs/form/formPageCategory/all',
					data: {},
					type: 'post',
					dataType: 'json',
				}
			}
		}),
		dataTextField: "name",
		dataValueField: "id",
		animation: false,
		change: function(e) {
			var value = this.value();
			$.cookie('pageCategory', value, {
				expires: 30
			});
			mtxx.zTree.setting.async.otherParam = {
				locked: $("#showLockedBox").prop("checked"),
				pageCategory: value
			};
			mtxx.zTree.reAsyncChildNodes(null, 'refresh');
		}
	});

	if (mtxx.pageCategory) {
		$("#pageCategory").data("kendoDropDownList").value(mtxx.pageCategory);
	}

	mtxx.widgetTreeNodes = [];
	mtxx.widgetTreeSetting = {
		callback: {
			onClick: function(event, treeId, treeNode) {
				mtxx.jQueryFrame.contents().find("#" + treeNode.eleId).trigger('click');
			}
		}
	};

	mtxx.componentDataSetTreeNodes = [];
	mtxx.componentDataSetTreeSetting = {
		callback: {
			onClick: function(event, treeId, treeNode) {
				mtxx.jQueryFrame.contents().find("#" + treeNode.eleId).trigger('click');
			}
		}
	};
	reloadWidgetTree();
	reloadComponentDataSetTree();
	mtxx.selectPageId && tabstripAppend(mtxx.selectPageName, mtxx.selectPageId);
});

function reloadWidgetTree() {
	$.fn.zTree.destroy("widgetTree");
	mtxx.$widgetTree = $.fn.zTree.init($("#widgetTree"), mtxx.widgetTreeSetting, mtxx.widgetTreeNodes);
}

function reloadComponentDataSetTree() {
	$.fn.zTree.destroy("componentDataSetTree");
	mtxx.$componentDataSetTree = $.fn.zTree.init($("#componentDataSetTree"), mtxx.componentDataSetTreeSetting, mtxx.componentDataSetTreeNodes);
}

function ajaxDataFilter(treeId, parentNode, responseData) {
	if (responseData) {
		for (var i = 0; i < responseData.length; i++) {
			var data = responseData[i];
			if (data.locked == 1) {
				data.icon = contextPath + "/images/lock.png";
			} else {
				if (data.type == 1) {
					if (data.formType == 1) {
						data.icon = contextPath + "/images/folder_page_blue.png";
					} else {
						data.icon = contextPath + "/images/page_gear.png";
					}
				} else {
					if (data.formType == 1) {
						data.icon = contextPath + "/images/folder_page_white.png";
					} else {
						data.icon = contextPath + "/images/page_white.png";
					}
				}
			}
		}
	}
	return responseData;
};
/**
 * 加载左边树菜单
 */
function initPageTree() {
	var otherParam = {};
	if (mtxx.pageCategory) {
		otherParam.pageCategory = mtxx.pageCategory;
	}
	mtxx.zTree = $("#tree").ztree({
		async: {
			url: mtxx.url + '/getFormPageTree',
			autoParam: ["parentId=pId", "id"],
			otherParam: otherParam,
			dataFilter: ajaxDataFilter
		},
		callback: {
			beforeClick: ztreeFuncs.ztreeBeforeClick,
			onRightClick: ztreeFuncs.zTreeOnRightClick,
			onCreate: ztreeFuncs.zTreeOnCreate,
			onRename: ztreeFuncs.zTreeOnRename,
			onRemove: ztreeFuncs.zTreeOnRemove,
			onUpdate: ztreeFuncs.zTreeOnUpdate,
			onEexport: ztreeFuncs.zTreeOnExport,
			onCopy: ztreeFuncs.zTreeOnCopy,
			onDrop: ztreeFuncs.zTreeOnDrop,
			beforeDrop: ztreeFuncs.zTreeBeforeDrop,
			onAsyncSuccess: function() {

				if (mtxx.selectedNode) {

					var nodes = mtxx.zTree.getNodesByParamFuzzy("id", mtxx.selectedNode.id, null);

					mtxx.zTree.expandNode(nodes[0], true, true, false);

				}
			},
			onCollapse: function(event, treeId, treeNode) {

			}
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
			}
		},
		edit: {
			enable: true,
			showRenameBtn: false,
			showRemoveBtn: false,
			editNameSelectAll: true,
			drag: {
				isCopy: false,
				isMove: true
			}
		}
	}).ztree('getZtree');
}

function showUiType(uiType) {
	$("#uiTypeSelect").data('kendoDropDownList').value(uiType ? uiType : 'kendo');
}
/**
 * ztree funcs
 */
var ztreeFuncs = {
	ztreeBeforeClick: function(treeId, treeNode, clickFlag) {
		mtxx.selectedNode = treeNode;
		if (!treeNode.isParent) {
			tabstripAppend(treeNode.name, treeNode.id);
			showUiType(treeNode.uiType);
		}
	},
	zTreeOnRightClick: function(e, treeId, treeNode) { // 右键事件
		mtxx.selectedNode = treeNode;
		$("#" + treeId).ztree('getZtree').selectNode(treeNode);
		$("#context-menu").data('kendoContextMenu').open(e.pageX, e.pageY);
	},
	zTreeOnCreate: function() { // 增加节点
		if (mtxx.selectedNode.formType == 0) {
			alert('模板下不能增加模板，请在分类下增加模板');
		} else {
			openUploadPage({
				id: mtxx.selectedNode ? mtxx.selectedNode.id : '',
				pageCategory: $("#pageCategory").data("kendoDropDownList").value(),
				sortType: mtxx.selectedNode ? mtxx.selectedNode.type : '',
			});
		}
	},
	zTreeOnRename: function(event, treeId, treeNode, isCancel) { // 名称编辑完以后
		if (!treeNode.name) {
			alert('请输入节点名称!');
			zTree.reAsyncChildNodes(null, "refresh");
			return;
		} else {
			jQuery.post(mtxx.contextPath + '/mx/form/page/saveFormPage', {
				id: treeNode.id,
				name: treeNode.name
			}, function(data) {});
		}
	},
	zTreeOnRemove: function() { // 删除节点
		var msg = "你确定删除该节点吗?";
		if (mtxx.selectedNode.isParent) {
			msg = '你确定删除该节点以及级联节点吗?';
		}
		if (!confirm(msg)) {
			return false;
		} else {
			var ids = new Array();
			ztreeFuncs.getNodeIds(mtxx.selectedNode, ids);
			jQuery.post(mtxx.contextPath + '/mx/form/page/delete', {
				ids: ids.join(',')
			}, function() {
				mtxx.zTree.removeNode(mtxx.selectedNode);
			});
		}

	},
	zTreeOnUpdate: function() { // 覆盖节点
		if (mtxx.selectedNode.formType == 1) {
			alert('分类不能操作');
		} else {
			openUploadPage({
				id: mtxx.selectedNode ? mtxx.selectedNode.id : '',
				type: 'update',
				pageCategory: $("#pageCategory").data("kendoDropDownList").value(),
				sortType: mtxx.selectedNode ? mtxx.selectedNode.sortType : '',
			});
		}
	},
	zTreeOnExport: function() {
		if (mtxx.selectedNode.formType != 1) {
			window.location.href = mtxx.contextPath + "/mx/form/defined/download?pageId=" + mtxx.selectedNode.id + "&r=" + new Date().getTime();
		} else {
			alert('分类不能操作');
		}
	},
	zTreeOnCopy: function() {
		if (mtxx.selectedNode.formType != 1) {
			jQuery.post(mtxx.contextPath + '/mx/form/page/copy', {
				id: mtxx.selectedNode.id
			}, function(data) {
				if (data.status == 200) {
					alert('复制完成');
					mtxx.zTree.reAsyncChildNodes(null, "refresh");
				} else {
					alert(data.message);
				}
			}, 'json');
		} else {
			alert('分类不能操作');
		}
	},
	zTreeBeforeDrop: function(treeId, treeNodes, targetNode, moveType, isCopy) {
		if (moveType == 'inner') {
			// 拖拽的节点成为目标节点的子节点
			// 判断是否为分类，如果是分类，则允许，否则，不允许
			if (targetNode.formType === '0') {
				alert("分类和页面只能移动到分类中，不能移动到页面使页面成为分类");
				return false;
			}
		}
	},
	zTreeOnDrop: function(event, treeId, treeNodes, targetNode, moveType) {

		var parentid = ''; // 目标节点的父节点，放在与目标节点同一层级
		var ids = []; // 所有拖拽的节点id

		var updateflag = true;
		if (moveType == 'inner') {
			// 拖拽的节点成为目标节点的子节点
			// 判断是否为分类，如果是分类，则允许，否则，不允许
			if (targetNode.formType === '0') {
				updateflag = false;
			}
		}

		for (var i = 0; i < treeNodes.length; i++) {
			if (parentid === '') {
				parentid = treeNodes[i].parentId;
			}
			ids.push(treeNodes[i].id);
		}

		if (updateflag) {
			jQuery.post(mtxx.contextPath + '/mx/form/page/move', {
				ids: ids.join(';'),
				parentid: parentid
			}, function(data) {
				if (data.status == 200) {

				} else {
					alert(data.message);
				}
			}, 'json');
		} else {
			alert("分类和页面只能移动到分类中，不能移动到页面使页面成为分类");
		}
	},
	getNodeIds: function(node, collection) {
		collection.push(node.id);
		if (node.isParent) {
			jQuery.each(node.children, function(index, item) {
				ztreeFuncs.getNodeIds(item, collection);
			});
		}
	},
	onEnabled: function(selectNode) {
		// mtxx.zTree.reAsyncChildNodes(selectNode,'refresh');
		var locked = 0;
		if (selectNode.locked == 0) {
			locked = 1;
		}
		var ids = new Array();
		ztreeFuncs.getNodeIds(mtxx.selectedNode, ids);
		jQuery.post(mtxx.contextPath + '/mx/form/page/enabledDisable', {
			ids: ids.join(','),
			locked: locked
		}, function() {
			// mtxx.zTree.hideNodes(mtxx.selectedNode);
			mtxx.zTree.reAsyncChildNodes(null, 'refresh');
		});
	}
};

/**
 * 新建分类
 * 
 * @param selectedNode
 */
function createSort(selectedNode) {
	if (selectedNode && selectedNode.formType != 1) {
		alert('模板下不能增加分类');
		return;
	}
	var $sortDialog = $("#sortDialog");

	if (!$sortDialog[0]) {
		var SB = new StringBuffer();
		SB.append("<div style='padding:10px;'>");
		SB.append("	分 类 名   : <input id='sortId' type='text' >");
		SB.append("</div>");
		$('<div>', {
			id: 'sortDialog'
		}).css({
			width: 300,
			height: 150
		}).append(SB.toString('')).dialog({
			title: '增加分类',
			buttons: [{
				text: '确定',
				imageUrl: mtxx.contextPath + '/images/okay.png',
				click: function(e) {
					var sortName = jQuery('#sortId').val();
					if (!sortName) {
						alert('请输入分类');
						return;
					} else {
						jQuery.post(mtxx.contextPath + '/mx/form/page/saveFormPage', {
							name: sortName,
							formHtml: sortName,
							parentId: mtxx.selectedNode ? mtxx.selectedNode.id : '',
							pageCategory: $("#pageCategory").data("kendoDropDownList").value(),
							// 用于区别分类的字段
							formType: 1,
							//用于模板文类 1 表示模板分类
							sortType: (selectedNode && selectedNode.type) || null
						}, function(rst) {
							if (rst) {
								alert('操作成功!');
								$(e).dialog('close');
								mtxx.zTree.reAsyncChildNodes(null, "refresh");
							}
						});
					}
				}
			}, {
				text: '取消',
				imageUrl: mtxx.contextPath + '/images/cancel.png',
				click: function(e) {
					$(e).dialog('close');
				}
			}]
		});
	} else {
		$sortDialog.dialog('open');
	}
}

/**
 * 分类排序
 * 
 * @param node
 */
function showSort(node) {
	var link = mtxx.contextPath + "/mx/form/page/showSort?parentId=" + node.id;
	jQuery.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "分类排序",
		closeBtn: [0, true],
		shade: [0.8, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['20px', ''],
		fadeIn: 100,
		area: ['620px', (jQuery(window).height() - 50) + 'px'],
		iframe: {
			src: link
		}
	});
};

/**
 * 上传模版页面
 */
function openUploadPage(data) {
	var link = mtxx.url + "/uploadFiles?" + jQuery.param(data);
	jQuery.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "增加节点",
		closeBtn: [0, true],
		shade: [0.8, '#000'],
		border: [10, 0.3, '#000'],
		fadeIn: 100,
		close: function() {
			mtxx.zTree.reAsyncChildNodes(null, "refresh");
			// expandAll();
		},
		area: ['420px', (jQuery(window).height() - 350) + 'px'],
		iframe: {
			src: link
		}
	});
}

/**
 * 定义页面
 * 
 * @param text
 * @param id
 */
function tabstripAppend(text, id) {

	var $tabstrip1 = mtxx["tabstrip-1"].data('kendoTabStrip'),
		selector = "li:last",
		$frame = jQuery('<iframe>', {
			frameborder: 0,
			scrolling: 'auto',
			id: id,
			style: 'width:100%;height:99%'
		}).attr({
			src: mtxx.url + '/getFormPageHtmlById?id=' + id,
			onload: "frameLoaded(this);",
			title: text
				/*
				 * function(){ setTimeout(function(){
				 * 
				 * populateJs(mtxx.jQueryFrame = $('#' + id));
				 * 
				 * mtxx.jQueryFrame.data('text',text);
				 * 
				 * var $body = mtxx.jQueryFrame.contents().find("body");
				 * $tabstrip1.wrapper.find(selector).bind('click',function(){ $body.click();
				 * }).attr({ title : '点击配置页面属性' }); $body.click();
				 * //mtxx["tabstrip-2"].data('kendoTabStrip').remove("li");
				 * 
				 * },800); // 延迟加载，保证iframe 完全加载后注入js }
				 */
		});

	$tabstrip1.remove(selector).append({
		text: text,
		content: $frame.outter()
	}).select(selector);

}

function frameLoaded(o) {

	var $tabstrip1 = mtxx["tabstrip-1"].data('kendoTabStrip'),
		selector = "li:last";

	populateJs(mtxx.jQueryFrame = $("#" + o.id));

	function clickBody() {
		mtxx.jQueryFrame.contents().find("body").click();
	}

	function iterNodes($el, parentNodes, isOpen) {
		var eleNode = {
				name: $el.attr("cname") || $el.attr("data-role"),
				eleId: $el.attr("id"),
				open: isOpen,
				children: []
			},
			canInsert = !!($el.attr("data-role") && $el.attr("cname"));
		if (canInsert) {
			parentNodes.push(eleNode);
		}
		$el.children().each(function(i, ele) {
			iterNodes($(ele), canInsert ? eleNode.children : parentNodes);
		});
	}

	function iterComponentSetNodes($el, parentNodes, isOpen, datas, isflag) {
		var eleNode = {
				name: $el.attr("cname") || $el.attr("data-role"),
				eleId: $el.attr("id"),
				open: isOpen,
				children: []
			},
			canInsert = !!($el.attr("data-role") && $el.attr("cname"));
		var newEleNode = undefined;
		if (datas) {
			$.each(datas, function(i, data) {
				if (eleNode.eleId == data.name) {
					/*iterComponentSetNodes($(ele),children);*/
					if (data.datasource != undefined) {
						newEleNode = {
							name: data.datasource.name,
							eleId: data.name,
							open: isOpen,
							children: []
						}
						eleNode.children.push(newEleNode);
					}

				}
			});
		}

		if (canInsert) {
			parentNodes.push(eleNode);
		}
		$el.children().each(function(i, ele) {
			iterComponentSetNodes($(ele), parentNodes, isOpen, datas, true);
		});
	}

	mtxx.widgetTreeNodes.length = 0;
	mtxx.componentDataSetTreeNodes.length = 0;
	//查找所有页面元素 组装树结构
	mtxx.jQueryFrame.contents().find("body").children().each(function(index, el) {
		iterNodes($(el), mtxx.widgetTreeNodes, true);
	});
	$.ajax({
		url: contextPath + "/mx/form/formRuleProperty/formRuleMessage",
		type: 'POST',
		dataType: 'json',
		data: {
			pageId: $("iframe").attr("id")
		},
		async: false,
	}).done(function(ret) {
		mtxx.datas = []
		$.each(ret, function(i, data) {
			var datasource = eval("(" + data.datasource + ")");
			var conf = {
				name: data.name
			};
			$.each(datasource, function(i, source) {
				if (source.indexOf("selectDatasource") != -1) {
					conf['datasource'] = eval("(" + source + ")")[0].selectDatasource[0];
				}
				if (source.indexOf("datasource") != -1) {
					conf['datasource'] = eval("(" + source + ")")[0].datasource[0];
				}
			});
			mtxx.datas.push(conf);
		});
		mtxx.jQueryFrame.contents().find("body").children().each(function(index, el) {
			iterComponentSetNodes($(el), mtxx.componentDataSetTreeNodes, true, mtxx.datas, true);
		});
	});

	reloadWidgetTree();
	reloadComponentDataSetTree();
	$tabstrip1.wrapper.find(selector).bind('click', function() {
		clickBody();
	}).attr({
		title: '点击配置页面属性'
	});

	clickBody();


	if (mtxx.saveData && !mtxx.saveData.formRuleId) {
		window.pageRoleInit = true;
		$('#button-1').click();
	}

}

/**
 * 注册定义页面事件
 * 
 * @param jQueryFrame
 */
function populateJs(jQueryFrame) {
	var doc = jQueryFrame.contents(),
		divs_ = new Array();
	var crtltypes = doc.find('[crtltype]'),
		pageId = jQueryFrame.attr("id");
	if (crtltypes && crtltypes.length) {
		var $this, $body = doc.find("body");
		$body.bind('click', function(index, item) {
			mtxx.saveData = {
				name: pageId,
				pageId: pageId,
				dataRole: 'page'
			};
			showComponentProperties({
				id: pageId,
				pageId: pageId,
				dataRole: 'page'
			}, $(this));

			popuCss(pageId);

			return false;
		});

		$.each(crtltypes, function(index, item) {
			$this = $(item).unbind('click').unbind('dblclick');
			$.each(['click', 'dblclick'], function(index, val) {
				$this.bind(val, function(e) {
					$this = jQuery(this);
					var id = $this.attr('id');
					var dataRole = $this.attr('data-role');

					var dataRole2 = dataRole;
					if (dataRole) {
						dataRole = dataRole.toLowerCase();
					}
					mtxx.saveData = {
						name: id,
						pageId: pageId,
						dataRole: dataRole,
					};
					showComponentProperties({
						id: id,
						pageId: pageId,
						dataRole: dataRole2
					}, $this);

					popuCss(id);

					return false;
				});
			});
		});

		function popuCss(id) {
			// 清楚其他div_颜色
			jQuery.each(divs_, function() {
				$(this).css({
					'border-color': ''
				});
			});

			jQuery.each(crtltypes, function() {
				if ($(this).attr('id') === id) {
					$this.css({
						border: '5px #FFB90F solid'
					});
				} else {
					$(this).css({
						border: ''
					});
				}
			});
		}

	}
	doc.find("[data-hover='hover']").each(function(i, v) {
		$this = $(this);
		var id = $this.attr('id');
		var offset = $this.offset();
		var left = offset.left + $this.width() - 30;
		var div_ = $('<div>', {
			id: id + '_div',
			'data-role': 'splitter',
			'crtltype': 'splitter'
		});
		div_.css({
			'font-size': '12px',
			color: 'green',
			'border': '2px',
			'border-style': 'solid',
			'border-color': 'green',
			position: 'absolute',
			float: 'left',
			left: left,
			top: offset.top + 3,
			background: '#fff',
			filter: 'alpha(opacity=70)',
			opacity: '0.7'
		}).text('设置').bind('click', function(e) {
			var that = $(this);
			var dd = that.attr('id');
			var dataRole = that.attr('data-role');
			if (dataRole) {
				dataRole = dataRole.toLowerCase();
			}
			mtxx.saveData = {
				name: dd,
				pageId: pageId,
				dataRole: dataRole
			};
			showComponentProperties({
				id: dd,
				pageId: pageId,
				dataRole: dataRole
			}, that);
			div_.css({
				border: '2px #FFB90F solid'
			});

			// 清楚其他div_颜色
			jQuery.each(divs_, function() {
				if ($(this).attr('id') != div_.attr('id')) {
					$(this).css({
						'border-color': ''
					});
				}
			});
			// 清楚其他元素的颜色
			jQuery.each(crtltypes, function() {
				$(this).css({
					border: ''
				});
			});
			return false;
		}).appendTo(doc.find('body'));

		divs_.push(div_);

	});
}

var dictDatas = new Object();
/**
 * 获取、显示配置属性 规则说明: 每个属性配置都有一个显示域与隐藏域。 1、显示域 id 为 (id_ + form属性id); eg : var id =
 * 'id_' + itemid; 2、隐藏域 id 为 显示域 id + '_hidden'; eg : var hiddenId = 'id_' +
 * itemid + '_hidden' 注: 每个dom 都有一个属性(attr) 'itemid'
 * 
 * @param options
 * @param jq
 * @returns {showComponentProperties}
 */
function showComponentProperties(options, jq) {
	this.options = options;
	this.jq = jq;
	window.K = this;
	this.init = function() {
		mtxx.tfMsg = {};
		mtxx.resultData = {};
		jQuery.ajax({
			url: mtxx.url + '/getFormCompoentProperties',
			type: 'post',
			data: options,
			async: false,
			dataType: 'json',
			success: function(data) {
				if (data) {
					mtxx.resultData.data = data;
					mtxx.saveData.formRuleId = data.formRuleId;
					var obj = new Object();
					mtxx.rows = new Object();
					if (data.rows.length > 0) {
						data.rows.sort(function(a, b) {
							return b.listNo - a.listNo; // 根据容器属性排序(倒序)
						});
					}
					mtxx.hideRows = new Object();

					function populateObj(item, collection) { // 按类型分组
						if (item.category == 'Action' && item.isSort == 0) {
							return;
						}
						if (!(item.type in collection)) {
							collection[item.type] = new Array();
						}
						collection[item.type].push(item);
					}

					jQuery.each(data.rows, function(index, item) {

						var pValue = item.pValue,
							parentId = item.parentId;
						if (pValue) {
							jQuery.each(pValue.split(','), function(i, v) {
								if (!(v in mtxx.hideRows)) {
									mtxx.hideRows[v] = new Object();
								}
								populateObj(item, mtxx.hideRows[v]);
							});
						}
						if (!parentId) {
							populateObj(item, obj);
						} else {
							if (!mtxx.resultData[parentId]) {
								mtxx.resultData[parentId] = new Array();
							}
							mtxx.resultData[parentId].push(item);
						}
					});
					var tabs = new Array(),
						name, table, dict;
					for (var i in obj) {
						if (i in mtxx.dictSort.data) {
							dict = mtxx.dictSort.data[i];
							name = dict.name;
							table = new StringBuffer();
							table.appendFormat("<table class='prop-table' id={0} width='100%' border=0 cellpadding=5 cellspacing=0 align='left'>", i);
							K.createPropertyDocument(obj[i], function(tr) {
								table.append(tr.join(''));
							});
							table.append("</table>");
							tabs.push({
								index: dict.index,
								text: name,
								content: table.toString()
							});
						}
					}

					if (tabs.length > 0) {
						tabs.sort(function(a, b) {
							return a.index - b.index;
						});
					}

					$tabstrip2Append(tabs);
					K.initEvent($('.prop-table'));
					K.showHideRows(mtxx.tfMsg.value_);
				}
			}
		});
	};

	this.hideRows = {};

	this.showHideRows = function(value_) {
		if (value_) {
			var obj = {};
			for (var i in value_) {
				obj[i] = value_[i];
				var hide = value_[i],
					roleindex = hide.id,
					value = hide.value;
				var $tr = $("table tr[roleindex=" + roleindex + "]");
				if ($tr[0]) {
					var hideRow = mtxx.hideRows[value],
						pad = $tr.attr('pad');
					if (hideRow) {
						$.each(hideRow, function(key, val) {

							if (val && val[0] && val[0].parentId === roleindex) {

								K.createPropertyDocument(val, function(tr) {
									$tr.after(tr.join(''));
								}, pad, $tr.attr('class'));
							}

						});
					}
					K.initEvent($('.cls_' + roleindex));
				}
			}
			var t = null;
			value_ = mtxx.tfMsg.value_;
			if (value_) {
				for (var key in value_) {
					if (!(key in obj)) {
						if (t == null)
							t = new Object();
						t[key] = value_[key];
					}
				}
				mtxx.tfMsg.value_ = null;
				K.showHideRows(t);
			}
		}
	};

	this.createPropertyDocument = function(data, fn, pad, cls) {
		var tr = new Array(),
			df = new K.domFn({
				tr: tr,
				span: "<span style='color:#fff;'  >·</span>",
				pad: pad || 1,
				cls: cls || ''
			});
		$.each(data, function(index, item) {
			df.dTypeFn.init(item);
		});
		if (fn) {
			fn(tr);
		}

	};

	this.domFn = function(options) {
		this.options = options;
		var Fn = this;
		this.dTypeFn = {
			init: function(item) {
				item.value ? item.value = item.value : item.value = '';
				item.parentId ? item.parentId = item.parentId : item.parentId = -1;
				mtxx.saveData.componentId = item.componentId;
				var id = 'id_' + item.id,
					isSort = item.isSort == '0',
					editor = item.editor,
					pad = Fn.options.pad,
					desc = item.desc || '';
				Fn.options.tr.push("<tr title='" + desc + "' roleIndex='" + item.id + "' class='" + Fn.options.cls + " cls_" + item.parentId + "' isSort='" + isSort + "' pad=" + pad + " ><td class='tdCls' >");

				function padding(n, collection) {
					for (var i = 0; i < n; i++) {
						collection.push(Fn.options.span);
					}
				}
				if (isSort) { // 分类
					var $img = $("<img>", {
						id: 'img_' + item.id,
						src: mtxx.contextPath + '/webfile/icons/open.png',
						onclick: 'openChildren(this)',
						name: item.id,
						itemid: item.id
					});
					padding(pad - 1, Fn.options.tr);
					Fn.options.tr.push($img.outter());
				} else {
					padding(pad, Fn.options.tr);
				}
				Fn.options.tr.push(item.title);
				Fn.options.tr.push('</td><td class="tdCls" style="width:60%;">');
				if (editor) {
					if (!isSort) {
						addDefaultValue(item);
						var fn = editor.type,
							rst;
						if (Fn.dTypeFn[fn]) {
							rst = Fn.dTypeFn[fn](item, id, Fn.options.tr);
						} else {
							rst = Fn.dTypeFn["def"](item, id, Fn.options.tr);
						}

						if (rst) {
							Fn.options.tr.push($('<input>', {
								id: id + '_hidden',
								idField: item.id,
								name: item.id,
								type: 'hidden',
								itemid: item.id,
								value: item.hiddenValue || item.value
							}).outter());
						}
					}
				} else {
					Fn.options.tr.push(item.value);
				}
				Fn.options.tr.push('</td></tr>');
			},
			def: function(item, id, tr) {
				var value_ = item.value_,
					idField = item.id,
					json = new Object(),
					jdom, cls = '';
				var selectLength = 'selectLength';
				if (!value_) {
					idField = item.id;
					json.value = item.value;
				} else {
					// id = 'name_' + item.id;
					function endswidth(str, syn) {
						if (!str || !syn) {
							return false;
						}
						if (str.substring(str.length - syn.length) == syn)
							return true;
						else
							return false;
					}
					if (item.value) {
						if (!dictDatas[selectLength]) {
							mtxx.getDictByCode(selectLength, function(data) {
								dictDatas[selectLength] = data;
							});
						}
						$.each(dictDatas[selectLength], function(i, v) {
							var syn = v.code;
							if (endswidth(item.value, syn)) {
								json.value = item.value.substring(0, item.value.length - syn.length);
								json.code = syn;
							}
						});
					}
					cls = 'changeCls';
				}
				var jdomOption = {
					id: id,
					itemid: item.id,
					idField: idField,
					name: item.id,
					type: 'text',
					'class': 'textCls k-textbox ' + cls,
					value: json.value
				};
				if (item.dataType == 'int' || item.dataType == 'double') {
					jdomOption['class'] = 'easyui-numberbox ' + cls;
					jdomOption.dataType = item.dataType;
				}
				if (item.name == 'id') {
					jdomOption.readonly = 'readonly=true';
					jdomOption.value = item.value ? item.value : K.options.id;
				} else if (item.name == 'html') {
					jdomOption.value = item.value || K.jq.attr("cname") || K.jq.attr('title') || K.jq.attr('name') || mtxx.jQueryFrame.attr('title');
				}
				jdom = jQuery('<input>', jdomOption).outter();
				tr.push(jdom);
				if (value_ && value_ == selectLength) {
					/*
					 * tr.push($("<input>",{ idField : item.id, id : 'hidden_' +
					 * item.id, type : 'hidden', name : item.id, value :
					 * item.value }).outter());
					 */
					tr.push("<select itemid='" + item.id + "' name='" + item.id + "'  class='definedcombo' style='width:70px;margin-left:5px;'  value_='" + item.value_ + "' >");
					if (dictDatas[selectLength]) {
						jQuery.each(dictDatas[selectLength], function(i, o) {
							if (json.code == o.code) {
								selected = 'selected';
							} else
								selected = '';
							tr.push("<option value='" + o.code + "' " + selected + "   >");
							tr.push(o.name);
							tr.push("</option>");
						});
					}
					tr.push("</select>");

					return true;
				}
				if (item.name && item.name == 'jsgisUrl') {
					var objId = item.name + "Id"; //
					mtxx.tfMsg[objId] = id;
				}
			},
			text: function(item, id, tr) {
				tr.push(jQuery('<input>', {
					id: id,
					itemid: item.id,
					idField: item.id,
					name: item.id,
					class: 'textCls',
					value: item.value
				}).outter());
			},
			// textbox : function(item,id,tr){
			// tr.push(jQuery('<input>',{id:id,itemid:item.id,idField:item.id,name:item.id,class:'textCls',value:item.value}).outter());
			// },
			dialog: function(item, id, tr) {
				var value = item.value,
					hiddenId = id + '_hidden',
					hiddenValue = '',
					json = value,
					editor = item.editor;
				try {
					if (!(value instanceof Array) || !(value instanceof Object))
						json = JSON.parse(value);
				} catch (e) {}
				if (json instanceof Array) {
					hiddenValue = JSON.stringify(json);
					value = [];
					jQuery.each(json, function(i, v) {
						value.push(v.name);
					});
					value = value.join(',');
				} else if (json instanceof Object) {
					hiddenValue = JSON.stringify(json);
					value = [];
					jQuery.each(json.datas, function(i, v) {
						value.push(v.name);
					});
					value = value.join(',');
				}
				if (editor.data) {
					if (editor.data == 'selectTable') {
						mtxx.tfMsg.tableObjElementId == null ? mtxx.tfMsg.tableObjElementId = hiddenId : "";
					} else if (editor.data == 'selectField') {
						mtxx.tfMsg.fieldObjElementId == null ? mtxx.tfMsg.fieldObjElementId = hiddenId : '';
						mtxx.tfMsg.filedNameElementId == null ? mtxx.tfMsg.filedNameElementId = id : '';
					} else if (editor.data == 'selectTreeField') {
						mtxx.tfMsg.selectTreeFieldId == null ? mtxx.tfMsg.selectTreeFieldId = hiddenId : '';
					} else if (editor.data == 'selectPage') {
						mtxx.tfMsg.pageElementId == null ? mtxx.tfMsg.pageElementId = hiddenId : '';
					} else if (editor.data == 'dataSourceSet') {
						mtxx.tfMsg.dataSourceSetId == null ? mtxx.tfMsg.dataSourceSetId = hiddenId : '';
					} else {
						var objId = editor.data + "Id"; //
						mtxx.tfMsg[objId] = mtxx.tfMsg[objId] || hiddenId;
					}
				}
				var jdom;
				if (editor.data && editor.data == 'selectIcon') { // 图标

					var opt = {
						id: id,
						readonly: true,
						name: item.id,
						itemid: item.id,
						alt: '无图',
						style: 'margin-right:40px;margin-top:5px;margin-left:45px;'
					};
					var src = value;
					hiddenValue = src;
					if (src) {
						opt.src = mtxx.contextPath + src;
					}
					jdom = jQuery('<img>', opt);
					tr.push(jdom.outter()); // 显示值
				} else {
					jdom = jQuery('<input>', {
						id: id,
						class: 'easyui-validatebox k-textbox textCls',
						readonly: true,
						name: item.id,
						itemid: item.id,
						type: 'text',
						value: value,
						ondblclick: 'showDetailDialog(this)'
					});
					tr.push(jdom.outter()); // 显示值
				}

				item.hiddenValue = hiddenValue;
				// jdom =
				// jQuery('<input>',{id:hiddenId,idField:item.id,name:item.id,type:'hidden',value:hiddenValue});
				// tr.push(jdom.outter());//隐藏值(保存)
				jdom = jQuery('<a>', {
					'class': 'linkbutton',
					value: '..',
					dialogType: editor.data,
					nameElementId: id,
					objElementId: hiddenId,
					name: item.id,
					itemid: item.id,
					onclick: 'selectObj(this)'
				}).html('.');
				tr.push(jdom.outter());

				return true;
			},
			checkbox: function(item, id, tr) {
				item.value == 'true' ? item.value = true : item.value = false;
				tr.push($('<input>', {
					id: id,
					name: item.id,
					itemid: item.id,
					value_: item.value_,
					type: 'checkbox',
					style: 'margin-left: 40%;'
				}).outter());

				if (!mtxx.tfMsg.value_) {
					mtxx.tfMsg.value_ = new Object();
				}
				mtxx.tfMsg.value_[item.id] = {
					id: item.id,
					value: item.value
				};

				// tr.push(jQuery('<input>',{id:id +
				// '_hidden',idField:item.id,name:item.id,value:item.value,type:'hidden'}).outter());
				return true;
			},
			select: function(item, id, tr) {

				// tr.push(jQuery('<input>',{id:id,idField:item.id,value:item.value,type:'hidden'}).outter());
				if (!mtxx.tfMsg.value_) {
					mtxx.tfMsg.value_ = new Object();
				}
				mtxx.tfMsg.value_[item.id] = {
					id: item.id,
					value: item.value
				};
				/*
				 * mtxx.tfMsg.value_.push({ id : item.id, value : item.value });
				 */
				tr.push("<select itemid='" + item.id + "' hiddenId='" + id + "' class='combobox selectCls'  name='" + item.id + "' value_='" + item.value_ + "' ><option value='' >---请选择---</option>");
				jQuery.each(item.editor.options.data, function(i, o) {
					if (item.value == o.value) {
						selected = 'selected';
					} else
						selected = '';
					tr.push("<option value='" + o.value + "' " + selected + "   >");
					tr.push(o.text);
					tr.push("</option>");
				});
				tr.push("</select>");
				return true;
			},
			multiselect: function(item, id, tr) {
				var editor = item.editor,
					opt = {
						id: id,
						hiddenId: id,
						name: item.id,
						itemid: item.id,
						class: 'combotree selectCls',
						'data-options': 'width:120,multiple:true,data:[]'
					};
				if (editor.options.data) {
					if (item.name == 'linkageControlIn') {
						mtxx.tfMsg.linkageControlInId = id;
					} else {
						mtxx.tfMsg[item.name + "Id"] = id;
					}
					// tr.push(jQuery('<input>',{id:id,idField:item.id,type:'hidden',value:item.value}).outter());
					if (editor.options.data instanceof Array) {
						var data = JSON.stringify(editor.options.data);
						opt["data-options"] = "multiple:true,data:" + data;
					} else {
						opt.multiChange = editor.data;
					}
					tr.push($("<select>", opt).outter());
					return true;
				}
			},
			colorpicker: function(item, id, tr) {
				tr.push(jQuery('<input>', {
					id: id,
					itemid: item.id,
					idField: item.id,
					name: item.id,
					class: 'colorCls',
					value: item.value
				}).outter());
			}
		};
	};

	this.initEvent = function(jDom) {
		// color 颜色控件
		jDom.find('.colorCls').each(function(i, v) {
			var $this = $(this);
			$this.kendoColorPicker({});
		});
		// 数字文本框
		jDom.find('.easyui-numberbox').each(function() {
			var $this = $(this);
			var dataType = $this.attr('dataType'),
				precision = 0,
				$width;
			if (dataType == 'double')
				precision = 8;
			if ($this.nextAll('select').length > 0) {
				$width = '45%';
			} else {
				$width = '90%';
			}
			$this.css({
				width: $width
			}).kendoNumericTextBox({
				decimals: precision,
				format: "#.########"
			});
		});

		// 文本框
		jDom.find('.k-textbox').each(function() {
			var $this = $(this);
			var $width;
			if ($this.nextAll('.linkbutton').length > 0) {
				$width = "78%";
			} else {
				$width = "90%";
			}
			$this.css({
				width: $width
			});
		});

		// checkbox
		jDom.find('input[type=checkbox]').each(function() {
			var $this = $(this),
				hidden = $('#' + $this.attr('id') + '_hidden');
			$this.attr('checked', hidden.val() == 'true').bind('click', function() {
				var checked = $(this).is(':checked');
				hidden.val(checked);

				appendChildren.call($(this), checked);
			});
		});

		// 弹出框 小按钮
		jDom.find('.linkbutton').kendoButton({

		});

		// combo 自定义下拉
		jDom.find('.definedcombo').each(function(i, v) {
			var $this = $(this);
			$this.kendoDropDownList({
				change: function(e) {
					var data = e.sender.dataItem();
					var itemid = "id_" + $this.attr('itemid');
					$('#' + itemid + '_hidden').val($('#' + itemid).val() + data.value);
				}
			});
		});

		// combobox
		try {
			jDom.find('.combobox').each(function() {
				var $this = $(this);
				$this.kendoDropDownList({
					change: function(e) {
						appendChildren.call($this, e.sender.dataItem().value);
					}
				});
			});
		} catch (e) {
			$.log(e);
		}

		// changeCls
		jDom.find('.changeCls').each(function() {
			var $this = $(this),
				$select = $this.closest('td').find("select.definedcombo"),
				itemid = $this.attr('itemid'),
				syn;
			$this.on('change', function() {
				if ($select[0]) {
					syn = $select.data('kendoDropDownList').value();
					$('#id_' + itemid + "_hidden").val(this.value + syn);
				}
			});
			/*
			 * $this.next('span').find('input:visible').on('change',function(){
			 * alert('changeCls'); var combo =
			 * $this.parent().find('.definedcombo'); var syn =
			 * combo.data("kendoDropDownList").value(); var itemid =
			 * $this.attr('itemid'); $('#id_' + itemid +
			 * "_hidden").val(this.value + syn); });
			 */
		});

		// combotree
		jDom.find('.combotree').each(function(index, item) {
			var $this = $(this).css({
					width: '155'
				}),
				hiddenId = "id_" + $this.attr('name') + "_hidden";
			var hiddenObj = jQuery('#' + hiddenId);
			var multiChange = $this.attr('multiChange');
			var datas = new Array(),
				values = new Array();;
			if (multiChange == 'domId') { // 联动控件(找到dom 里面控件)
				var doc = mtxx.jQueryFrame.contents();
				var crtltypes = doc.find('[crtltype]');
				if (crtltypes && crtltypes.length) {
					jQuery.each(crtltypes, function(index, item) {
						var id = jQuery(this).attr('id');
						var text = jQuery(this).attr('cname') || jQuery(this).attr('title') || jQuery(this).attr('name') || id;
						datas.push({
							id: id,
							text: text
						});
					});
				}
			} else if (multiChange == 'fields') {
				/**
				 * var fieldsData = getFieldsData(); if(fieldsData){
				 * $this.combotree({ data : fieldsData, onShowPanel :
				 * function(){ //每次点击下拉时，都要重新获取数据字段信息 if($this.combotree()) var
				 * jsons = getHiddenJson(hiddenId); var values = new Array();
				 * fieldsData = getFieldsData(); if(fieldsData && jsons){
				 * if(jQuery.isArray(fieldsData)){
				 * jQuery.each(fieldsData,function(i,v){
				 * if(jQuery.inArray(v.id,jsons) > -1){ values.push(v.id); } }); } }
				 * var tree = $this.combotree('tree'); // 得到树对象 var nodes =
				 * tree.tree('getChecked'); // 得到选择的节点 if(nodes.length == 0){
				 * jQuery('#' + hiddenId).val(''); } $this.combotree({
				 * data:fieldsData });
				 * 
				 * if(jsons){ $this.combotree('setValues',values); } } }); }
				 */
			} else if ('parameterIn' == multiChange) {
				var pageId = mtxx.jQueryFrame.attr('id'); // 当前页面
				$.getJSON(mtxx.url + '/getFormRule', {
					name: pageId,
					pageId: pageId
				}, function(data) {
					if (data && data[0]) {

						datas = $.parseJSON(data[0].pageParameters);

						var val = hiddenObj.val();
						if (val) {
							$.each($.parseJSON(val), function(i, v) {
								values.push(v.id);
							});
						}
					}
				});
			} else if (!multiChange) {
				var str = $this.attr('data-options');
				if (str) {
					try {
						datas = eval("({" + str + "})").data;
					} catch (e) {

					}
				}
			}

			function getHiddenJson(id) {
				var val = jQuery('#' + id).val();
				if (val) {
					try {
						var jsons = eval(val),
							values = new Array();
						jQuery.each(jsons, function(i, n) {
							values.push(n.name);
						});
						return values;
					} catch (ex) {
						return val;
					}
				}
				return null;
			}
			if (!values.length) {
				values = getHiddenJson(hiddenId) || [];
			}
			$this.kendoMultiSelect({
				dataTextField: "text",
				dataValueField: "id",
				dataSource: datas,
				change: function(e) {
					var $kendo = e.sender,
						dataItems = $kendo.dataItems();
					if (dataItems && dataItems.length > 0) {
						jQuery.each(dataItems, function(i, n) {
							n.name = n.id;
							n.parent = null;
							n._events = null;
						});
					}
					hiddenObj.val(JSON.stringify(dataItems));
				},
				autoClose: false,
				tagMode: "single",
				value: values
			});
		});

		/**
		 * tr tooltip
		 */
		function initTr(jq) {
			jq.each(function(i, tr) {
				var $this = $(this);
				if ($this.attr('isSort') == 'true') {
					$this.css({
						'font-size': 13,
						'font-family': 'Microsoft YaHei',
						'font-weight': 'bold'
					});
				}
				var title = $this.attr('title');
				if (title) {
					$this.kendoTooltip({
						autoHide: true,
						position: "left",
						content: title
					});
				}
			});
		}
		if (jDom.is('tr')) {
			initTr(jDom);
		} else {
			initTr(jDom.find('tr'));
		}

	};
	this.init();
}

/**
 * 添加控件默认值
 */
function addDefaultValue(item) {
	if (!item)
		return;

	var id = "id_" + item.id;

	var hiddenId = id + "_hidden";

	var dataRole_ = mtxx.saveData.dataRole;
	if (item && dataRole_ && dataRole_.indexOf("office") > -1) {
		//增加office 默认形参[可改为异步从字典里面动态获取]
		if (item && item.value_) {
			console.log(item);
			if (item.value_ == "inParamDefined") {
				var values = [],
					params = {
						DocTitle: '标题',
						DocBody: '正文',
						DocFooter: '结尾',
						DocId: '文档ID',
						InsertMsg: '插入信息',
						ShowRevisions: '修订显示',
						Protect: '修订加密',
						PWD: '修订密码'
					};
				$.each(params, function(k, v) {
					values.push({
						name: v + "--参数",
						param: k
					});
				});
				var isMutil = false;
				if (item.value) {
					try {
						var jsons = JSON.parse(item.value);
						$.each(jsons, function(i, v) {
							if (v.type) {
								isMutil = true;
								$.each(v.source, function(j, vs) {
									!(vs.param in params) && (values.push(vs));
								});
							} else {
								!(v.param in params) && (values.push(v));
							}
						});
						isMutil && (jsons[0]["source"] = values);
					} catch (e) {
						console.log(e);
					}
				}
				item.value = JSON.stringify(isMutil ? jsons : values);
			}
		}
	} else {

	}
}

function getChildren(parentId) {
	return mtxx.resultData[parentId];
}

function appendChildren(type) {

	var $this = $(this);

	var hiddenId = "id_" + $this.attr('itemid') + "_hidden",
		hiddenObj = jQuery('#' + hiddenId),
		$tr = hiddenObj.closest('tr');
	var children = '.cls_' + $tr.attr('roleIndex'),
		pad = $tr.attr('pad'),
		$children = $(children);
	if ($children && $children[0]) {
		$children.remove();
	}
	hiddenObj.val(type);
	var value_ = $this.attr('value_'),
		cre = function() {
			if (type) {
				var obj = mtxx.hideRows[type];
				if (obj) {
					$.each(obj, function(key, data) {
						K.createPropertyDocument(data, function(tr) {
							$tr.after(tr.join(''));
						}, pad, $tr.attr('class'));
					});
					K.initEvent($(children));
					// 隐藏的内容
					K.showHideRows(mtxx.tfMsg.value_);
				}
			}
		};

	/**
	 * switch(value_){ case 'buttonType': case 'jumpType': case 'chartsType':
	 * cre(); break; default: break; }
	 */
	if (value_)
		cre();
}

/**
 * 点击显示分类
 * 
 * @param img
 */
function openChildren(img) {
	if (img) {
		var $img = $(img),
			$tr = $img.parent().parent();
		var parentId = $tr.attr('roleIndex'),
			data = getChildren(parentId);
		if (data) {
			var children = '.cls_' + parentId;
			var opertator = new operateChildren($(children), parentId);
			if (!$tr.data('open')) {
				$img.attr({
					src: mtxx.contextPath + '/webfile/icons/close.png'
				});
				if (!$tr.data('isShow')) {
					var pad = $tr.attr('pad') * 1;
					K.createPropertyDocument(data, function(tr) {
						$tr.after(tr.join(''));
					}, (pad + 1), $tr.attr('class'));
					K.initEvent($(children));
					// 隐藏的内容
					K.showHideRows(mtxx.tfMsg.value_);
					$tr.data('isShow', true);
				} else {
					opertator.show();
				}
			} else {
				$img.attr({
					src: mtxx.contextPath + '/webfile/icons/open.png'
				});
				opertator.hide();
			}
			$tr.data('open', !$tr.data('open'));
		}
	}
}

/**
 * 显示隐藏分类
 */
function operateChildren(jq, parentId) {
	this.jq = jq, this.parentId = parentId;
	this.hide = function() {
		$('.cls_' + parentId).each(function() {
			$(this).hide();
		});
	};

	this.show = function() {
		var cls = null;
		$('.cls_' + parentId).each(function() {
			if ($(this).attr('isSort') == 'true' && !$(this).data('open')) {
				cls = 'cls_' + $(this).attr('roleIndex');
			}
			if (!$(this).hasClass(cls)) {
				$(this).show();
			}
		});
	};
}

function $tabstrip2Append(tabs) {
	var $tabstrip2 = mtxx["tabstrip-2"].data('kendoTabStrip');
	$tabstrip2.remove("li");
	$tabstrip2.append(tabs).select(0);
}

/**
 * 获取字典信息
 */
mtxx.getDictByCode = function(code, fn) {
	$.ajax({
		url: mtxx.url + '/getDictByCode',
		data: {
			code: code
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			if (fn)
				fn(data);
		}
	});
};

(function($) {

	window.MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;

	$.fn.watch = function(o, fn) {
		var _watch_g_ = '_watch_g_';
		var cfg = {
			trackValues: false,
			callback: $.noop,
			attrs: null,
			type: _watch_g_
				// 监听所有
		};

		if (typeof o === "function") {
			cfg.callback = o;
		} else {
			cfg.type = o || cfg.type;
			if (cfg.type !== _watch_g_) {
				cfg.attrs = cfg.type.split(' ');
			}
			cfg.callback = fn;
		}

		if (MutationObserver) {
			var mOptions = {
					subtree: false,
					attributes: true,
					attributeOldValue: cfg.trackValues
				},
				oldKey = 'attr-old-value';
			var observer = new MutationObserver(function(mutations) {
					mutations.forEach(function(e) {
						if (cfg.attrs) {
							if (cfg.contains(e.attributeName)) {
								_call(e);
							}
						} else {
							_call(e);
						}
					});
				}),
				_call = function(e) {
					var $this = $(e.target);
					$.data(e.target, oldKey) && (e.oldValue = $.data(e.target, oldKey)[e.attributeName]);
					e.newValue = $this.attr(e.attributeName);
					cfg.callback.call(e.target, e);
				};

			return this.each(function() {
				var $this = $(this),
					el = this;
				var attributes = {};
				if (cfg.attrs) {
					$.each(cfg.attrs, function(i, v) {
						attributes[v] = $this.attr(v);
					});
				} else {
					for (var attr, i = 0, attrs = el.attributes, l = attrs.length; i < l; i++) {
						attr = attrs.item(i);
						attributes[attr.nodeName] = attr.value;
					}
				}
				$.data(this, oldKey, attributes);

				observer.observe(this, mOptions);
			});
		}
		return this;
	};

})(jQuery);

function selectObj(obj) {

	var $this = jQuery(obj),
		dialogType = $this.attr('dialogType');
	var d = dialogType;
	if (!dialogType)
		return false;
	if (!(dialogType in selectObjFuncs)) {
		dialogType = 'default';
	}
	mtxx._targetId_ = $this.attr('objElementId');

	var $target = $("#" + mtxx._targetId_);

	selectObjFuncs[dialogType].call($this, {
		nameElementId: $this.attr('nameElementId'),
		objElementId: mtxx._targetId_,
		objJson: $target.val(),
		dialogType: d
	});

	if (d.indexOf('dataSourceSet') > -1 && mtxx.tfMsg.inParamDefinedId) {
		watch($target);
	} else if (d.indexOf('selectPage') > -1) {
		watchSelectPage($target);
	}

}
/**
 * 监听选择cell表页面，获取输入输出参数
 * 
 * @param $target
 */
function watchSelectPage($target) {
	if (!$target.data("watch")) {
		$target.watch(function(e) {
			var val = e.target.value,
				json;
			if (val) {
				json = JSON.parse(val);
				if (json && (json = json[0])) {
					if (json.url && json.url.indexOf('/mx/bi/chart/viewChart') > -1) {
						getCellInParams(json.id, function(ret) {
							var inParamDefined = $("#" + mtxx.tfMsg.inParamDefinedId);
							var params = inParamDefined.val(), //
								tmp = [],
								names = [];
							/*if (params) {
								params = JSON.parse(params);
								$.each(params, function(i, v) {
									tmp[v.param] = v;
									names.push(v.name);
								})
							}*/

							if (params) {
								params = JSON.parse(params);
							}
							params = params || [];
							var datas = JSON.parse(JSON.parse(ret.ruleJson)['page']['A000-1-1-003'])[0]['datas'],
								data;
							for (var key in datas) {
								data = datas[key][0]['val'];
							}

							params = mergeObjParams(params, data);
							if (params[0] && params[0]["type"]) {
								tmp = params[0]["source"];
							} else {
								tmp = params;
							}
							$.each(tmp, function(i, v) {
									names.push(v.name);
								})
								/*$.each(data, function(i, v) {
									if (!tmp[v.param]) {
										params.push(v);
										names.push(v.name);
									}
								});*/
							inParamDefined.val(JSON.stringify(params));
							$("#id_" + inParamDefined.attr('itemid')).val(names.join(', '));
						});
					}
				}
			}
		}).data("watch", true);
	}
}

/**
 * 获取表头文本
 * @param  {[type]} nodes [description]
 * @return {[type]}       [description]
 */
function getAllHeaderNames(nodes) {
	var names = [];
	for (var i = 0, len = nodes.length; i < len; i++) {
		if (nodes[i].children && nodes[i].children.length > 0) {
			var tnames = getAllHeaderNames(nodes[i].children);
			for (var j = 0; j < tnames.length; j++) {
				names.push(tnames[j]);
			}
		} else {
			names.push(nodes[i].name);
		}
	}
	return names;
}
/**
 * 监听数据集变化，获取参数
 * 
 * @param $target
 */
function watch($target) {
	if (!$target.data("watch")) {
		$target.watch(function(e) {
			var val = e.target.value,
				json;
			if (val) {
				json = JSON.parse(val);
				if (json && (json = json[0])) {
					var datasetIds = [];
					$.each(json.datasource, function(i, v) {
						datasetIds.push(v.datasetId);
					});
					getDataSetParams(datasetIds.join(','), function(ret) {
						if (ret && ret.length) {
							var inParamDefined = $("#" + mtxx.tfMsg.inParamDefinedId);
							var params = inParamDefined.val(), //
								tmp = [],
								names = [];
							/*if (params) {
								params = JSON.parse(params);
								$.each(params, function(i, v) {
									tmp[v.param] = v;
									names.push(v.name);
								})
							}*/
							if (params) {
								params = JSON.parse(params);
							}
							params = params || [];
							params = mergeObjParams(params, ret);
							/*$.each(ret, function(i, v) {
								if (!tmp[v.param]) {
									params.push(v);
									names.push(v.name);
								}
							});*/
							if (params[0] && params[0]["type"]) {
								tmp = params[0]["source"];
							} else {
								tmp = params;
							}
							$.each(tmp, function(i, v) {
								names.push(v.name);
							})
							inParamDefined.val(JSON.stringify(params));
							$("#id_" + inParamDefined.attr('itemid')).val(names.join(', '));
						}
					});

					//若是grid自动生成对应的表头
					var columnTemplateId = mtxx.tfMsg.columnTemplateId;
					if (columnTemplateId) {
						var $columnTemplateHtml = $("#" + columnTemplateId);
						var $columnTemplateHeaderHtml = $("#" + columnTemplateId.substring(0, columnTemplateId.lastIndexOf("_")))
						if ($columnTemplateHtml[0] && ($columnTemplateHtml.val() == "" || $columnTemplateHtml.val() == "[]")) {
							var columnData = json.columns;
							var columnHeaderList = [];
							var treeCount = 0;
							for (var i = 0, len = columnData.length; i < len; i++) {
								var item = columnData[i];
								if (item.title == 'id' || item.title == 'topid' || item.title == 'index_id' || item.title == 'parent_id' || item.title == 'treeid') {
									continue;
								}
								var obj = {};
								obj.id = ++treeCount;
								obj.name = item.title,
									obj.isHidden = "";
								obj.columnName = item.columnName;
								obj.columnNameCN = item.title,
									obj.columnNameTitle = item.title,
									obj.columnId = item.id, //数据集列主键
									obj.value = item.columnName;
								columnHeaderList.push(obj);
							}
							$columnTemplateHtml.val(JSON.stringify(columnHeaderList));

							var headers = getAllHeaderNames(columnHeaderList);
							$columnTemplateHeaderHtml.val(headers);
						}
					}
				}
			}
		}).data("watch", true);
	}
}

/**
 *  move to defined.kendo.eventParam.js
 * 获取数据集后台参数
 * @param datasetIds
 * @param fn
 */
/*function getDataSetParams(datasetIds, fn) {
	$.ajax({
		url: contextPath + "/rs/dataset/getDataSetParams",
		type: 'POST',
		dataType: 'JSON',
		async: false,
		data: {
			datasetIds: datasetIds
		},
		success: function(ret) {
			if (fn) {
				fn(ret);
			}
		}
	});
}*/
/**
 * 获取cell表定义的页面形参
 * @param datasetIds
 * @param fn
 */
function getCellInParams(cellId, fn) {
	$.ajax({
		url: contextPath + "/mx/dep/report/depReportTemplate/getDepReportTemplate",
		type: 'POST',
		dataType: 'JSON',
		async: false,
		data: {
			id: cellId
		},
		success: function(ret) {
			if (fn) {
				fn(ret);
			}
		}
	});
}

function saveSourceSetFunc(data) {
	var id = "id_" + $(this).attr('itemid');
	if (data && data.length) {
		var $id = $("#" + id),
			$hidden = $("#" + id + "_hidden");
		if (data[0].table) {
			var names = [];

			$.each(data, function(i, v) {
				names.push(v.table.name);
			});

			$id.val(names.join(','));

			var jsonString = JSON.stringify(data);

			$hidden.val(jsonString);
		} else {
			$id.val('');
			$hidden.val('');
		}

		var sd = mtxx.saveData;
		if (sd && sd.name == sd.pageId) {
			$.getJSON(mtxx.url + '/getFormRule', {
				name: sd.pageId,
				pageId: sd.pageId
			}, function(data) {
				if (data && (data = data[0])) {
					if (data.paraType) {
						//	$("#id_311_hidden").val(data.paraType);
						$("#" + mtxx.tfMsg.paraTypeId).val(data.paraType);
						$("#" + mtxx.tfMsg.dataSourceSetId).val(data.dataSourceSet);
						if (!sd.id) {
							sd.id = data.ruleId;
						}
						//	$("#id_296_hidden").val(data.paraType);
						$('#button-1').click();
					}
				}
			});
		} else {
			$('#button-1').click();
		}
	} else {
		var $linkageControlIn = $("#" + mtxx.tfMsg.linkageControlInId + "_hidden");
		return {
			linkageControl: $linkageControlIn.val(),
			tableMsg: $("#" + id + "_hidden").val(),
			pageId: mtxx.saveData.pageId,
			rid: mtxx.saveData.formRuleId
		};
	}
}


function selectReportTemplateFunc(data) {
	var $this = $(this);
	var $name = $('#id_' + $this.attr('itemid'));
	$name.val(data.name);
	$this.val(JSON.stringify([data]));

}

function selectProFunc(datas) {
	var $this = $(this);
	var $name = $('#id_' + $this.attr('itemid'));
	if (datas) {
		var changeType = $this.data("changeType") || {},
			names = [];
		$.each(datas, function(i, v) {
			names.push(v[changeType.processName]);
			v.defId = v[changeType.MSGID];
		});
		$name.val(names.join(","));
		$this.val(JSON.stringify([{
			data: {
				changeType: changeType,
				datas: datas
			},
			name: $name.val()
		}]));
	} else {

	}
	closeLayer();
}

/**
 * html 样式定义 callBack 函数
 * 
 * @param data
 */
function htmlDefinedFunc(data) {
	var target = document.getElementById(mtxx._targetId_) || this;
	var $name = $('#id_' + $(target).attr('itemid')),
		$this = $('#id_' + $(target).attr('itemid') + "_hidden");
	if (data) {
		$name.val('html 样式定义');
		$this.val(JSON.stringify([{
			htmldata: data,
			name: $name.val()
		}]));
	} else {
		var o = $this.val();
		if (o) {
			o = JSON.parse(o);
			return o[0].htmldata;
		}
		return '';
	}
}

/**
 * 获取数据列
 */
function getDataSourceField() {
	var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceServiceId),
		val = $dataSourceSet.val();
	if (!val) {
		$dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId),
			val = $dataSourceSet.val();
	}
	if (val) {
		var datas = JSON.parse(val);
		if (datas && datas[0]) {
			var columns = datas[0].columns;
			$.each(columns, function(i, v) {
				v.name = v.title;
			});
			return columns;
		}
	}
}
// html编辑器 根据角色获取参数
var paramFnDatas = {
		//{total}-{point.name}-{y}-{point.percentage:.1f}-{percentage:.1f}
		"default": [],
		"charts": [{
			id: 1,
			pId: 0,
			name: "百分比",
			t: '',
			dType: 'string',
			code: "{point.percentage:.1f}",
			value: "{百分比}",
			isParent: false
		}, {
			id: 2,
			pId: 0,
			name: "系列名",
			t: '',
			dType: 'string',
			code: "{point.name}",
			value: "{系列名}",
			isParent: false
		}, {
			id: 3,
			pId: 0,
			name: "值",
			t: '',
			dType: 'string',
			code: "{y}",
			value: "{值}",
			isParent: false
		}, {
			id: 4,
			pId: 0,
			name: "总数量",
			t: '',
			dType: 'string',
			code: "{total}",
			value: "{总数量}",
			isParent: false
		}, ]
	}
	// html编辑器获取参数方法
function getParamFn() {
	var role = mtxx.saveData.dataRole;
	if (role in paramFnDatas) {
		return paramFnDatas[role];
	}
	return [];
}

function getVarFn() {
	return [];
}

function flowDefinedFunc(data) {
	var $name = $('#id_' + $(this).attr('itemid')),
		$this = $('#id_' + $(this).attr('itemid') + "_hidden");
	if (data) {
		$name.val(data.name);
		$this.val(JSON.stringify([data]));
		closeLayer();
		$('#button-1').click();
	} else {

	}
}

function selectDatasetByVideos(resultElementId, tablenameElementId, elementId, pageId) {
	var flag = false;
	if (elementId.indexOf("ztree") != (-1)) {
		flag = true;
	}
	var link = contextPath + '/mx/form/defined/table/video_datasource?resultsElementId=' + resultElementId + '&tablenameElementId=' + tablenameElementId + '&fieldnameElementId=&flag=' + flag + "&pageId=" + pageId;
	// &fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['', ''],
		fadeIn: 100,
		area: ['980px', '700px'],
		iframe: {
			src: link
		}
	});
}

function selectDatasetByCalendar(resultElementId, tablenameElementId, elementId, pageId) {
	var flag = false;
	if (elementId.indexOf("ztree") != (-1)) {
		flag = true;
	}
	var link = contextPath + '/mx/form/defined/table/calendar_datasource?resultsElementId=' + resultElementId + '&tablenameElementId=' + tablenameElementId + '&fieldnameElementId=&flag=' + flag + "&pageId=" + pageId;
	// &fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['', ''],
		fadeIn: 100,
		area: ['980px', '700px'],
		iframe: {
			src: link
		}
	});
}

function selectDatasetByCommon(dictTreeCode, resultElementId, tablenameElementId, elementId, pageId) {
	var flag = false;
	if (elementId.indexOf("ztree") != (-1)) {
		flag = true;
	}
	var link = contextPath + '/mx/form/defined/table/common_datasource?pageId=' + pageId;

	link = link + '&' + $.param({
		dictTreeCode: dictTreeCode,
		resultsElementId: resultElementId,
		tablenameElementId: tablenameElementId,
		flag: flag
	});

	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['', ''],
		fadeIn: 100,
		area: ['980px', '700px'],
		iframe: {
			src: link
		}
	});
}

function selectDatasetByUpdate(dictTreeCode, resultElementId, tablenameElementId, elementId, pageId) {
	var flag = false;
	if (elementId.indexOf("ztree") != (-1)) {
		flag = true;
	}
	var link = contextPath + '/mx/form/defined/table/update_datasource?pageId=' + pageId;

	link = link + '&' + $.param({
		dictTreeCode: dictTreeCode,
		resultsElementId: resultElementId,
		tablenameElementId: tablenameElementId,
		flag: flag
	});

	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择更新集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['', ''],
		fadeIn: 100,
		area: ['980px', '700px'],
		iframe: {
			src: link
		}
	});
}

// 选择图片 (回调函数)
function selectIconFunc(src) {

	var $this = $('#id_' + $(this).attr('itemid'));
	if (src) {
		$this.attr({
			src: contextPath + src
		});
	} else {
		$this.removeAttr("src");
	}

}

// 选择关联页面(回调函数)
function selectPageBack(data) {

	var obj = {
		name: '',
		value: ''
	};

	if (data) {
		obj = {
			name: data.name,
			// value :
			// JSON.stringify([{id:data.id,name:data.name,url:data.url}])
			value: JSON.stringify([data])
		};
	}
	var $this = $(this);
	if ($this[0]) {
		var idfield = $this.attr('idfield');
		$('#id_' + idfield).val(obj.name);
		$this.val(obj.value);
	}
	closeLayer();
}

function closeLayer() {
	layer.close(layer.getFrameIndex());
}

function maxLayer() {
	layer.full(layer.getFrameIndex());
}

/**
 * 查看详细信息
 * 
 * @param o
 */
function showDetailDialog(o) {
	if (!mtxx.showDetailDialog) {
		mtxx.showDetailDialog = $("<div>").dialog({
			title: '查看详细信息',
			width: 400,
			height: 150,
			buttons: [{
				text: '确定',
				click: function(e) {
					$(e).dialog('close');
				}
			}]
		});
	} else {
		mtxx.showDetailDialog.dialog('open');
	}
	mtxx.showDetailDialog.text(o.value);
}



function getTableMsg(data) {
	var $this = $(this);
	if (!data) {
		var ret = null;
		if ($this.val()) {
			ret = JSON.parse($this.val())[0];
		} else {
			ret = {};
		}

		var str = $("#" + mtxx.tfMsg.columnTemplateId).val();
		if (str) {
			var linkageControl = [];
			var json = JSON.parse(str);
			$.each(json, function() {
				linkageControl.push({
					text: this.name,
					id: this.id
				});
			});
			ret.linkageControl = JSON.stringify(linkageControl);
		}
		return ret;
	} else {
		if (data.wdataSet) {
			var $name = $('#id_' + $this.attr('itemid'));

			$name.val(data.wdataSet.dataSetName);
		}
		$this.val(JSON.stringify([data]));
		//$this.val(JSON.stringify(data));
	}
}