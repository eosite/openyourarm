/**
 * 页面初始方法
 */
$(function() {

	var setting = {
		async: {
			enable: true,
			url: contextPath + '/mx/form/defined/getFormPageTree',
			autoParam: ["parentId=pId", "id"],
			otherParam: {
				type: 1
			},
			dataFilter: ajaxDataFilter
		},
		callback: { 
			beforeClick: function(treeId, node, type) {
				//$("#iframe").attr("src", contextPath + '/mx/form/defined/getFormPageHtmlById?id=' + node.id)
			},
			onAsyncSuccess: function(event, treeId, treeNode, msg) {
				initdraggable();
			},
			onExpand: function(){
				initdraggable();
			},
			beforeDrag: function(treeId, treeNodes) {
				return treeNodes[0].formType != 1;
			},
			beforeDrop: function(treeId, treeNodes, targetNode, moveType) {
				return false;
			},
			onDrop: function(event, treeId, treeNodes, targetNode, moveType) {
				var treeNode = treeNodes[0],
					drole = treeNode.drole,
					$target = $(event.target),
					$viewport = $target.find(">.viewport");
				if ($target.attr("data-element-id") == "Process_1") {}
			}
		},
		/*edit: {
			drag: {
				isCopy: true
			},
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},*/
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
			}
		}
	}

	window.ztreeObj = $.fn.zTree.init($("#ztree"), setting);

	$("#select").on("click", function() {
		var nodes = ztreeObj.getSelectedNodes();
		if (nodes.length && nodes[0]["formType"] != 1) {
			var nodeId = nodes[0].id;
			var $shine_red = parent.$(".shine_red");
			$.ajax({
				url: contextPath + '/mx/form/defined/getFormDesignHtmlById?id=' + nodeId,
				type: "post",
				async: false,
				contentType: "application/json",
				success: function(rdata) {
					debugger;
					if (rdata) {
						$shine_red.after($(rdata).attr("data-templateId", nodeId));
						$shine_red.remove();
						$shine_red = null;
					}
				},
				error: function(e) {
					console.log(e);
				}
			});
		} else {
			BootstrapDialog.alert("请选择一个模板页面");
		}
	})

});

function ajaxDataFilter(treeId, parentNode, responseData) {
	if (responseData) {
		for (var i = 0; i < responseData.length; i++) {
			var data = responseData[i];
			if (data.formType == 1) {
				data.icon = contextPath + "/images/folder_page_blue.png";
			} else {
				data.icon = contextPath + "/images/page_gear.png";
			}
		}
	}
	return responseData;
};