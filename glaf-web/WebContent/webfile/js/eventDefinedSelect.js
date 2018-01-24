/**
 * 树结构样式定义
 * @param treeId
 * @param parentNode
 * @param childNodes
 * @returns
 */
function treeDataFilter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for (var i = 0, l = childNodes.length, childNode; i < l; i++) {
		childNode = childNodes[i];
		if (childNode.icon) {
			childNode.icon = mtxx.contextPath + childNode.icon;
		}
		if (!childNode.isEvn) {
			//childNode.chkDisabled = true;
		}
		if (i == 0) {
			childNode.open = true;
		}
	}
	return childNodes;
}
/**
 * 初始化选卡内容
 * @param tabs
 */
function initTabsContent(datas, isZoom) {
	try {
		var tabs = JSON.parse(datas),
			tab, length = tabs.length,
			text, $frame;
		for (var i = 0; i < length; i++) {
			tab = tabs[i];
			if (tab.eleId == tab.pageId) {
				text = tab.name;
				$frame = jQuery('<iframe>', {
					frameborder: 0,
					scrolling: 'auto',
					id: tab.eleId,
					treeId: tab.id,
					style: 'width:100%;height:99%;',
					title: text
				}).attr({
					type: 'sort',
					src: mtxx.url + '/getFormPageHtmlById?id=' + tab.eleId,
					onload: isZoom ? "frameLoadedZoom(this);" : "frameLoaded(this);",
				}).data("param", tab);
				mtxx.tabStrip.append({
					text: text,
					content: $frame[0].outerHTML
				});
			}
		}
		mtxx.tabStrip.select(0);
	} catch (e) {
		console.log(e);
	}
}
/**
 * 
 * @param frame
 */
function frameLoadedZoom(frame) {
	frameLoaded(frame, true);
}

function frameLoaded(frame) {
	var isZoom = arguments.length > 1 ? arguments[1] : false;
	var $frame = $(frame),
		$html = $frame.contents().find("html"),
		$body = $html.find("body");

	//注册选择控件功能
	new FrameLoadedFunc(frame).exec();

	//body 注册禁用右键
	$body.attr({
		"oncontextmenu": "self.event.returnvalue=false",
		"onkeypress": "self.event.returnvalue=false",
		"onkeydown": "self.event.returnvalue=false",
		"onkeyup": "self.event.returnvalue=false",
		"ondragstart": "self.event.returnValue=false",
		"onselectstart": "self.event.returnvalue=false",
	});
	//$html.css("margin-left",- $frame.width()*0.1).css("margin-top",- $frame.width()*0.1) ;
}

function zTreeOnClick(event, treeId, treeNode) {
	//console.log(treeNode);
}

function checkPageElement(evnetTreeNode) {
	if (evnetTreeNode.isEvn) {
		var treeNode = JSON.parse(evnetTreeNode.pObj)
		if (treeNode.eleId != treeNode.pageId) {
			var pId = isGroup ? evnetTreeNode.ppId : treeNode.pId,
				$frame = $('iframe[treeId=' + pId + ']'),
				$doc = $frame.contents(),
				$target = $doc.find("#" + treeNode.eleId);
			if (evnetTreeNode.checked) {
				$target.addClass('isSelected').css({
					"background-color": "yellow",
					"border-color": ""
				});
			} else {
				$target.removeClass('isSelected').css({
					"background-color": "",
					"border-color": ""
				});
			}
		}


	}
}

function zTreeOnCheck(event, treeId, treeNode) {
	checkPageElement(treeNode);
}


/**
 * 初始化选卡高度
 * @param e
 */
function initTabHeight(e) {
	var dd = $(this.wrapper),
		ts = dd.data("kendoTabStrip"),
		$div = $(e.contentElement);
	$div.height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight());
	mtxx.actFrame = $div.find("iframe[type=sort]");
	if (divEvents.document) {
		divEvents.document.off("mousedown");
		var doc = mtxx.actFrame.contents();
		divEvents.document = doc;
		doc.on('mousedown', function(e) {
			divEvents.bindMouseDown(e);
		}).data("context-menu", $("#context-menu").data("kendoContextMenu"));
	}
}

/**
 *选中元素
 */
function selectNode(role, eventName) {
	var $frame = mtxx.actFrame,
		treeNodeId = $frame.attr("treeId"),
		zTreeObj = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTreeObj.getNodesByParam("pId", treeNodeId), //页面下的所有元素集合
		selectNodes = $frame.contents().find('.selected-cls');
	//isSelected
	$.each(selectNodes, function(index, val) {
		var selectNode = $(val),
			selectNodeId = selectNode.attr("id");
		if (!selectNode.hasClass('isSelected')) {
			$.each(nodes, function(i, v) {
				if (selectNodeId == v.eleId) {
					if (v.isEle || v.isPage) {
						$.each(v.children, function(j, k) {
							if (k.eName == eventName) {
								zTreeObj.checkNode(k, true, false);
							}
						})
					}
				}
			})
			selectNode.addClass('isSelected').css({
				"background-color": "yellow",
				"border-color": ""
			});
		} else {
			selectNode.css({
				"background-color": "yellow",
				"border-color": ""
			});
		}
	});
}
/**
 *选中元素
 */
function selectNodeById(selectNodeId, eventName) {
	var $frame = mtxx.actFrame,
		treeNodeId = $frame.attr("treeId"),
		zTreeObj = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTreeObj.getNodesByParam("pId", treeNodeId), //页面下的所有元素集合
		selectNode = $frame.contents().find('#' + selectNodeId),
		role = selectNode.attr("data-role");
	if (!selectNode.hasClass('isSelected')) {
		$.each(nodes, function(i, v) {
			if (isGroup) {
				if (v.drole == role) {
					$.each(v.children, function(ii, cv) {
						if (selectNodeId == cv.eleId) {
							if (cv.isEle || cv.isPage) {
								$.each(cv.children, function(j, k) {
									if (k.eName == eventName) {
										zTreeObj.checkNode(k, true, false);
									}
								})
							}
						}
					});
				}
			} else {
				if (selectNodeId == v.eleId) {
					if (v.isEle || v.isPage) {
						$.each(v.children, function(j, k) {
							if (k.eName == eventName) {
								zTreeObj.checkNode(k, true, false);
							}
						})
					}
				}
			}
		})
		selectNode.addClass('isSelected').css({
			"background-color": "yellow",
			"border-color": ""
		});
	} else {
		selectNode.css({
			"background-color": "yellow",
			"border-color": ""
		});
	}
}
/**
 *移除元素
 */
function removeNodeById(selectNodeId) {
	var $frame = mtxx.actFrame,
		treeNodeId = $frame.attr("treeId"),
		zTreeObj = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTreeObj.getNodesByParam("pId", treeNodeId), //页面下的所有元素集合
		selectNode = $frame.contents().find('#' + selectNodeId),
		role = selectNode.attr("data-role");
	if (selectNode.hasClass('isSelected')) {
		$.each(nodes, function(i, v) {
			if (isGroup) {
				if (v.drole == role) {
					$.each(v.children, function(ii, cv) {
						if (selectNodeId == cv.eleId) {
							if (cv.isEle || cv.isPage) {
								$.each(cv.children, function(j, k) {
									zTreeObj.checkNode(k, false, false);
								})
							}
						}
					});
				}
			} else {
				if (selectNodeId == v.eleId) {
					if (v.isEle || v.isPage) {
						$.each(v.children, function(j, k) {
							zTreeObj.checkNode(k, false, false);
						})
					}
				}
			}
		})
		selectNode.removeClass('isSelected').css({
			"background-color": "",
			"border-color": ""
		});
	} else {
		selectNode.css({
			"background-color": "",
			"border-color": ""
		});
	}
}
/**
 *移除元素
 */
function removeNode() {
	var $frame = mtxx.actFrame,
		treeNodeId = $frame.attr("treeId"),
		zTreeObj = $.fn.zTree.getZTreeObj("tree"),
		nodes = zTreeObj.getNodesByParam("pId", treeNodeId), //页面下的所有元素集合
		selectNodes = $frame.contents().find('.selected-cls');
	//isSelected
	$.each(selectNodes, function(index, val) {
		var selectNode = $(val),
			selectNodeId = selectNode.attr("id"),
			role = selectNode.attr("data-role");
		if (selectNode.hasClass('isSelected')) {
			$.each(nodes, function(i, v) {
				if (isGroup) {
					if (v.drole == role) {
						$.each(v.children, function(ci, cv) {
							if (selectNodeId == cv.eleId) {
								if (cv.isEle || cv.isPage) {
									$.each(cv.children, function(j, k) {
										zTreeObj.checkNode(k, false, false);
									})
								}
							}
						});
					}
				} else {
					if (selectNodeId == v.eleId) {
						if (v.isEle || v.isPage) {
							$.each(v.children, function(j, k) {
								zTreeObj.checkNode(k, false, false);
							})
						}
					}
				}
			})
			selectNode.removeClass('isSelected').css({
				"background-color": "",
				"border-color": ""
			});
		} else {
			selectNode.css({
				"background-color": "",
				"border-color": ""
			});
		}
	});
}
/**
 *保存选中的控件
 */
function saveSelectNodes() {
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	var nodes = treeObj.getCheckedNodes();
	if (nodes.length == 0) {

	} else {
		var names = "",
			retNodes = [],
			retDatas = {};
		for (var i = 0; i < nodes.length; i++) {
			var node = nodes[i],
				pObj = node.pObj ? JSON.parse(node.pObj) : null;
			names += (pObj ? (pObj.name + "-") : "") + node.name + ",";
			var d = $.extend(true, {}, node);
			d.children = "";
			filterNode(d);
			retNodes.push(d);
		}
		retDatas.names = names;
		retDatas.nodes = retNodes;
		retDatas.isGroup = isGroup;
		parent[retFn].call(this, retDatas);
		window.close();
		parent.layer && parent.layer.close(parent.layer.getFrameIndex());
	}
}