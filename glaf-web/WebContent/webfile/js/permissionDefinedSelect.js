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
			if (tab.isPage) {
				text = tab.name;
				$frame = jQuery('<iframe>', {
					frameborder: 0,
					scrolling: 'auto',
					id: tab.id,
					treeId: tab.id,
					style: 'width:100%;height:99%;',
					title: text
				}).attr({
					type: 'sort',
					src: mtxx.url + '/getFormPageHtmlById?id=' + tab.id,
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

//检查页面元素 初始化
function checkPageElement(evnetTreeNode) {
	if (evnetTreeNode.isEvn) {
		var treeNode = JSON.parse(evnetTreeNode.pObj)
		if (treeNode.isEle) {
			var pId = treeNode.pId,
				$frame = $('iframe[treeId=' + pId + ']'),
				$doc = $frame.contents(),
				$target = $doc.find("#" + treeNode.id),
				eleConf = null;
			if (evnetTreeNode.checked) {
				$target.addClass('isSelected').css({
					"background-color": "yellow",
					"border-color": ""
				});
				mtxx.conf && (eleConf = mtxx.conf[treeNode.id]);
				for (var p in eleConf) {
					addHandle(eleConf[p] || null, evnetTreeNode);
				}
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
		zTreeObj = mtxx.zTreeObj || (mtxx.zTreeObj = $.fn.zTree.getZTreeObj("tree")),
		nodes = zTreeObj.getNodesByParam("pId", treeNodeId), //页面下的所有元素集合
		selectNodes = $frame.contents().find('.selected-cls'),
		name, source = "";

	//isSelected
	$.each(selectNodes, function(index, val) {
		var selectNode = $(val),
			selectNodeId = selectNode.attr("id");
		//if (!selectNode.hasClass('isSelected')) {
		$.each(nodes, function(i, v) {
			if (selectNodeId == v.id) {
				if (v.isEle) {
					$.each(v.children, function(j, k) {
						if (k.eName == eventName) {
							mtxx.selectZtreeNode = k;
							if (k.callback) {
								if (selectNodes.length > 1) {
									alert("特殊情况请选择单个控件");
									return;
								}
								mtxx.select = selectNode;
								mtxx.conf[role] && mtxx.conf[role][eventName] && (source = mtxx.conf[role][eventName]);
								var url = mtxx.contextPath + "/mx/form/defined/ex/" + k.callback + "?val=" + source;
								openLayer(k.name, url);
							} else {
								zTreeObj.checkNode(k, true, false);
								//zTreeObj.updateNode(k);
							}
						}
					})
				}
			}
		})
		selectNode.addClass('isSelected').css({
			"background-color": "yellow",
			"border-color": ""
		});

		/*} else {
			selectNode.css({
				"background-color": "yellow",
				"border-color": ""
			});
		}*/
	});
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
			selectNodeId = selectNode.attr("id");
		if (selectNode.hasClass('isSelected')) {
			$.each(nodes, function(i, v) {
				if (selectNodeId == v.id) {
					if (v.isEle) {
						$.each(v.children, function(j, k) {
							zTreeObj.checkNode(k, false, false);
						})
					}
				}
			})
			selectNode.removeClass('isSelected').css({
				"background-color": "",
				"border-color": ""
			});
			//移除标签
			$("iframe").contents().find("#" + selectNode.attr("id") + "handle").remove();
			delete mtxx.conf[selectNode.attr("id")];
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
				pObj = JSON.parse(node.pObj);
			names += pObj.name + "-" + node.name + ",";
			var d = $.extend(true, {}, node);
			d.children = "";
			retNodes.push(d);
		}
		retDatas.names = names;
		retDatas.nodes = retNodes;
		retDatas.conf = mtxx.conf;
		parent[retFn].call(this, retDatas);
		window.close();
	}
}

function openLayer(name, url, width, height) {
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: name,
		closeBtn: [0, true],
		shade: [0.5, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['', ''],
		fadeIn: 100,
		area: [width || 500, height || 300],
		iframe: {
			src: url
		}
	});
}


function callbackFn(val) {
	var k = mtxx.selectZtreeNode,
		role = k.pId,
		eventName = k.eName;
	//name = prompt("请输入想隐藏卡片，如1,3", source);
	if (val == null)
		return;
	mtxx.conf[k.pId] || (mtxx.conf[k.pId] = {});
	mtxx.conf[k.pId][eventName] = val;
	if (val) {
		var $handle = $("iframe").contents().find("#" + mtxx.select.attr("id") + "handle");
		handleAppend(val,$handle,mtxx.select,eventName,k.name,k.pId);
		/*if ($handle && $handle.length) {
			$handle.html(val);
		} else {
			mtxx.select.after('<div id="' + k.pId + 'handle" style="position:absolute;top: 16px;left: 80px;border:1px solid red;"><div class="' + eventName + '">' + k.name + '-' + val + '</div></div>');
		}*/
	}
	mtxx.zTreeObj.checkNode(k, true, false);
}

function addHandle(val, node) {
	var k = node || mtxx.selectZtreeNode,
		role = k.role,
		eventName = k.eName;
	if (val) {
		var $select = $("iframe").contents().find("#" + k.pId);
		var $handle = $("iframe").contents().find("#" + k.pId + "handle");
		handleAppend(val,$handle,$select,eventName,k.name,k.pId);
		/*if ($handle && $handle.length) {
			var ev = $handle.find("." + eventName);
			if (ev && ev.length) {
				ev.html(val);
			} else {
				$handle.append('<div class="' + eventName + '">' + k.name + '-' + val + '</div>');
			}
		} else {
			$select.after('<div id="' + k.pId + 'handle" style="position:absolute;top: 16px;left: 80px;border:1px solid red;"><div class="' + eventName + '">' + k.name + '-' + val + '</div></div>');
		}*/
	}
}

function handleAppend(val,$handle,$select,eventName,kname,pId) {
	if ($handle && $handle.length) {
		var ev = $handle.find("." + eventName);
		if (ev && ev.length) {
			ev.html(val);
		} else {
			$handle.append('<div class="' + eventName + '">' + kname + '-' + val + '</div>');
		}
	} else {
		$select.after('<div id="' + pId + 'handle" style="position:absolute;top: 16px;left: 80px;border:1px solid red;"><div class="' + eventName + '">' + kname + '-' + val + '</div></div>');
	}
}