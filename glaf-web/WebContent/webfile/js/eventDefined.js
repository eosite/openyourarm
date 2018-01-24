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
		if (!childNode.isEle) {
			//childNode.chkDisabled = true;
		}
		if (i == 0) {
			childNode.open = true;
		}
	}
	return childNodes;
}
/**
 * ztree点击事件
 * @return {[type]} [description]
 */
function zTreeOnClick(event, treeId, treeNode) {
	selectNodeEvent(treeNode);
}

function selCheckClick() {
	var selectNodes = mtxx.zTreeObj.getSelectedNodes();
	if (selectNodes && selectNodes.length) {
		selectNodeEvent(selectNodes[0]);
	}
}

function clrCheckClick() {
	var selectNodes = mtxx.zTreeObj.getSelectedNodes();
	if (selectNodes && selectNodes.length) {
		mtxx.zTreeObj.cancelSelectedNode(selectNodes[0]);
		var tg = $("#treegrid"),
			tg2 = $("#treegrid2");
		tg.find("tbody tr").removeClass('hidCls');
		tg2.find("tbody tr").removeClass('hidCls');
		var vtr = tg.find("tbody tr:visible:first");
		if (vtr && vtr.length) {
			vtr.trigger('click');
		}
	}

}

function selectNodeEvent(treeNode) {
	var rets = getSaveEventsDatas();
	//如果是元素
	var eleId = null,
		pageId = null,
		drole = null;
	if (treeNode.isEle || treeNode.isPage) {
		eleId = treeNode.eleId;
		pageId = treeNode.pageId;
	} else if (treeNode.isGroup) {
		drole = treeNode.drole;
	} else {
		return;
	}
	var checkedstr = "";
	$("[name=selCheck]:checked").each(function(index, el) {
		checkedstr += $(el).val();
	});
	/**
	 * 	主事件 ：[子事件]
	 *  {0:[1,2],1:[0,1]}
	 */
	var ary = {};
	$.each(rets, function(i, ret) {
		if (checkedstr.indexOf("1") != -1) {
			$.each(ret.inWidget, function(j, inWidget) {
				if ((inWidget.eleId == eleId && inWidget.pageId == pageId) || (drole && inWidget.eleId.indexOf(drole) != -1)) {
					ary[i] || (ary[i] = []);
				}
			});
		}
		if (checkedstr.indexOf("2") != -1) {
			$.each(ret.widget, function(j, widget) {
				var pObj = JSON.parse(widget.pObj);
				if ((pObj.eleId == eleId && (widget.pageId == pageId || pObj.pageId == pageId)) || (drole && pObj.eleId.indexOf(drole) != -1)) {
					ary[i] || (ary[i] = []);
				}
			});
		}
		if (checkedstr.indexOf("3") != -1) {
			$.each(ret.widgetEvent, function(j, widgetEvent) {
				$.each(widgetEvent.outWidget, function(k, outWidget) {
					var pObj = JSON.parse(outWidget.pObj);
					if ((pObj.eleId == eleId && (outWidget.pageId == pageId || pObj.pageId == pageId)) || (drole && pObj.eleId.indexOf(drole) != -1)) {
						ary[i] || (ary[i] = []);
						//ary[i].push(j);
					}
				});
			});
		}
	});
	var tg = $("#treegrid"),
		tg2 = $("#treegrid2");
	tg.find("tbody tr").addClass('hidCls');
	tg2.find("tbody tr").removeClass('hidCls');
	if (!$.isEmptyObject(ary)) {
		for (var p in ary) {
			tg.find("tbody tr:eq(" + p + ")").removeClass('hidCls');
		}
		var vtr = tg.find("tbody tr:visible:first");
		if (vtr && vtr.length) {
			vtr.trigger('click');
			/*var sary = ary[vtr.index()];
			if (sary && sary.length) {

			}*/
		}
	} else {
		tg2.find("tbody tr").addClass('hidCls');
	}
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
	/*mtxx.actFrame = $div.find("iframe[type=sort]");
	if (divEvents.document) {
		divEvents.document.off("mousedown");
		var doc = mtxx.actFrame.contents();
		divEvents.document = doc;
		doc.on('mousedown', function(e) {
			divEvents.bindMouseDown(e);
		}).data("context-menu", $("#context-menu").data("kendoContextMenu"));
	}*/
};
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
};
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
	if (isZoom) {
		$html.css("transform", "scale(0.95,0.95)");
	} else {
		//注册选择控件功能
		new FrameLoadedFunc(frame).exec();
	}
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
};

/**
 * 输入扩展回调方法
 * @param  {[type]} data [description]
 * @return {[type]}      [description]
 */
function retOutExtData(data) {
	var $select = mtxx.select,
		idata = $select.data("idata");
	data.type = idata.type;
	$select.val(data.name).data("idata", data);
}

function getOutExtData(){
	var $select = mtxx.select,
		idata = $select.data("idata");
	return idata;
}

var opWin = function(e) {
	var area = [($(document).width() - 200) + "px", ($(document).height() - 200) + "px"];

	var url = mtxx.contextPath,
		$this = $(this);
	mtxx.select = $this;
	if ($this.hasClass("tg-quick")) {
		e.stopPropagation();
		var $this = $(this),
			$input = $this.prev("input"),
			isTrigger = false,
			showEvent = false;
		mtxx.ioSelect = $input;
		if ($input.hasClass("tg-trigger")) {
			isTrigger = true;
			showEvent = true;
		}
		var link = mtxx.contextPath + "/mx/form/defined/ex/quickSelect?getFn=getInputData&retFn=retInputData&isTrigger=" + isTrigger + "&showEvent=" + showEvent + "&pageId=" + pageId;
		$.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "快速选择",
			closeBtn: [0, true],
			shade: [0.3, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['', ''],
			fadeIn: 100,
			area: ['480px', ($(window).height() - 100) + 'px' /*'500px'*/ ],
			iframe: {
				src: link
			}
		});
		return;
	}
	if ($this.hasClass("tg-out") || $this.hasClass("tg-outExt")) {
		mtxx.selectTG2Row = $this.closest("tr");
	}
	if ($this.hasClass("tg-input")) { //输入控件
		mtxx.ioSelect = $this;
		url += "/mx/form/defined/ex/verificationSetting?getFn=getInputData&retFn=retInputData&pageId=" + pageId;
	} else if ($this.hasClass("tg-trigger") || $this.hasClass("tg-out")) { //触发控件事件定义器
		mtxx.ioSelect = $this;
		url += "/mx/form/defined/ex/eventDefinedSelect?getFn=getInputData&retFn=retInputData&pageId=" + pageId + "&isTrigger=" + $this.hasClass("tg-trigger");
	} else if ($this.hasClass("tg-cell") || $this.hasClass("tg-exp")) { //表达式
		url += "/mx/expression/defined/view?category=front&retFn=retExpression&getFn=getExpression&initExpFn=initExpressionFn&notValidate=true"
	} else if ($this.hasClass("tg-outExt")) { //输出控件扩展属性
		var idata = $this.data("idata"),
			rp = parent.outExpDefined[idata.type](pageId),
			params = rp.params ? ("&" + $.param(rp.params)) : "";
		var link = mtxx.contextPath + "/mx/form/defined/outExp/" + rp.urlStr + "?retFn=retOutExtData&pageId=" + pageId + params;
		if (rp.url) {
			link = mtxx.contextPath + rp.url + "?retFn=retOutExtData&getFn=getOutExtData&pageId=" + pageId + params;
		}
		$.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: rp.title,
			closeBtn: [0, true],
			shade: [0.3, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['', ''],
			fadeIn: 100,
			area: area || ['980px', '500px'],
			iframe: {
				src: link
			}
		});
		return;
	} else if ($this.hasClass("tg-outin") || $this.hasClass("tg-callback")) { //输入输出函数定义
		var link = mtxx.contextPath + '/mx/form/defined/param/events_outInParam?' + $.param({
			pageId: pageId,
			eleType: eleType,
			eleId: 'hidParam',
			fn: $this.hasClass("tg-outin") ? "initInOutParameterByEvents" : "initCallbackByEvents",
			retFn: $this.hasClass("tg-outin") ? "retParamFn" : "retCallbackFn"
		});
		var inoutparam = $this.data("idata");
		$('#hidParam').val((typeof inoutparam == "string" ? inoutparam : JSON.stringify(inoutparam)) || "");
		link += "&isQuick=true";
		//弹窗更改为iframe
		mtxx.fn = $this.hasClass("tg-outin") ? parent.initInOutParameterByEvents : parent.initCallbackByEvents;
		mtxx.retFn = $this.hasClass("tg-outin") ? retParamFn : retCallbackFn;
		initOutInParams();
		//$("#paramFrame").attr("src", link);
		mtxx.paramUrl = link;
		/*$.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "输入&输出关系定义",
			closeBtn: [0, true],
			shade: [0.3, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['', ''],
			fadeIn: 100,
			area: area || ['980px', '500px'],
			iframe: {
				src: link
			}
		});*/
		return;
	} else if ($this.hasClass("tg-view")) { //查看按钮
		getParamCallbackRets();
		$(".tg-view").removeClass("mt-view-select");
		$this.addClass("mt-view-select");
		$this.prev("input").trigger("click");
		mtxx.selectTG2Row = $this.closest("tr");
		return;
	}
	window.open(url);
};

function getInputData() {
	var select = mtxx.ioSelect,
		data = select.data("idata"),
		isGroup = select.data("isGroup"),
		dd = {
			isGroup: isGroup,
			nodes: (typeof data == "string" ? (data ? JSON.parse(data) : []) : data)
		};
	return dd;
}
//选择页面格式转换为选择控件格式
function pageObj2NodeObj(data) {
	var node = data.node;
	node.eName = "newWindow";
	node.isPage = true;
	node.eleId = node.id;
	node.pageId = node.id;
	node.srcUrl = data.url;
	node.pObj = JSON.stringify(node);
	return {
		names: data.name,
		nodes: [node]
	};
}

/**
 * 重新选择了输入控件输出控件以后刷新输入输出参数定义
 * @return {[type]} [description]
 */
function refreshParamDefined() {
	var $select = mtxx.ioSelect,
		$paramTr = $("#treegrid2").find("button.mt-view-select").closest("tr");
	if ($select.hasClass("tg-input") || ($select.hasClass("tg-out") && $paramTr[0] == $select.closest("tr")[0])) {
		//$("#paramFrame").attr("src", mtxx.paramUrl);
		initOutInParams();
	}
}

var outExpAry = parent.outExpDefined,
	callbackAry = parent.callbackDefined,
	openWindows = [];

function retInputData(data) {
	if (data.name) {
		data = pageObj2NodeObj(data);
	}
	var $select = mtxx.ioSelect,
		nodes, isShow = false,
		isShowCallback = false,
		$tr = $select.closest("tr"),
		$outExt = $tr.find(".tg-outExt"),
		$callback = $tr.find(".tg-callback"),
		$callbackview = $tr.find(".tg-callback-view");

	$select.val(data.names).data("idata", data.nodes);
	if (data.isGroup) {
		$select.data("isGroup", data.isGroup);
	}
	var $tr = $select.parents("tr");
	$tr.trigger("click");

	//用来刷新输入输出参数

	refreshParamDefined();

	//显示输出控件属性扩展
	if (data && (nodes = data.nodes) && (data.nodes.length == 1)) {
		$.each(nodes, function(i, node) {
			if (node.eName in outExpAry) {
				isShow = node.eName;
			}
			if ($.inArray(node.eName, callbackAry) != -1) {
				isShowCallback = node.eName;
			}
		})
		isShow && $outExt.show() && $outExt.data("idata", {
			type: isShow
		});
		isShowCallback && $callbackview.show() && $tr.find(".addChild").show();
	}
}

var treeGridExtend = new TreeGridExtend();
//<button class='k-button addChild'>+ 下级</button>
treeGridExtend.push({
	targetName: "#treegrid",
	lineTemp: ["<tr class='{0} {1}'>",
		"<td style='display: inline-flex;width: 100%;'><div style='display: inline-flex;width: 99%;'><input  type='text' class='k-textbox mt-style tg-name' style='width:99%'/></div></td>",
		"<td><div style='display: inline-flex;width: 100%;'><input  type='text' class='k-textbox mt-style tg-button tg-input' style='width:100%'/><button class='k-button tg-button tg-quick' title='输入控件'>Q</button></div></td>",
		"<td><div style='display: inline-flex;width: 100%;'><input  type='text' class='k-textbox mt-style tg-button tg-trigger' style='width:100%'/><button class='k-button tg-button tg-quick' title='触发控件'>Q</button></div></td>",
		"<td class='hidCls'><button type='text' class='k-button tg-outeditor'>输出控件事件编辑器</button></td>",
		"<td><button class='k-button addBrother'>同级</button>{2}<button class='k-button tg-up'>↑</button><button class='k-button tg-down'>↓</button><button class='k-button tg-copy' title='复制'>c</button></td>",
		"</tr>"
	],
	initControlEvent: function($tr) {
		$tr.delegate('.tg-button', 'click', opWin);
		$tr.delegate('.tg-outeditor', 'click', showEventsWin);
	},
	click: function(e, $tr) {
		var bool = true;
		if (mtxx.selectTG1Row && mtxx.selectTG1Row[0] == $tr[0]) {
			bool = false;
		}
		mtxx.selectTG1Row = $tr;
		treeGridExtend.clearSelect();

		//点击行触发选中
		bool && showEventsWin.call($tr.find(".tg-outeditor"));

		//输入控件
		var $input = $tr.find(".tg-input"),
			inputData = $input.data("idata"),
			nodes = [];
		if (inputData) {
			nodes = typeof inputData == "string" ? JSON.parse(inputData) : inputData;
		}
		treeGridExtend.showSelect(nodes);

		//触发控件
		var $trigger = $tr.find(".tg-trigger"),
			triggerData = $trigger.data("idata");
		nodes = [];
		if (triggerData) {
			nodes = typeof triggerData == "string" ? JSON.parse(triggerData) : triggerData;
		}
		treeGridExtend.showTrigger(nodes);

		//输出控件
		var $outeditor = $tr.find(".tg-outeditor"),
			outeditorData = $outeditor.data("idata");
		nodes = [];
		if (outeditorData) {
			var editors = typeof outeditorData == "string" ? JSON.parse(outeditorData) : outeditorData;

			function getNodes(editors) {
				$.each(editors, function(i, editor) {
					var outWidget = editor.outWidget;
					if (outWidget) {
						$.merge(nodes, typeof outWidget == "string" ? JSON.parse(outWidget) : outWidget);
					}
					var childs = editor.childs;
					if (childs && childs.length) {
						getNodes(childs)
					}
				});
			}
			getNodes(editors);
			//nodes = JSON.parse(triggerData);
		}
		treeGridExtend.showTrigger(nodes, "bottom");
	},
	clearSelect: function() {
		var $frameTab = $("#frameTab"),
			iframes = $frameTab.find("iframe"),
			$tabstrip = $frameTab.data("kendoTabStrip"),
			tabItems = $tabstrip.items();
		$.each(tabItems, function(i, v) {
			var $item = $(v);
			$item.css({
				'background-color': ''
			});
		})
		$.each(iframes, function(i, v) {
			var $frame = $(v);
			$frame.contents().find('.isSelected').css({
				'border': ''
			}).removeClass("isSelected");
		});
	},
	showSelect: function(nodes) {
		//显示选中 
		if (nodes && nodes.length) {
			var $frameTab = $("#frameTab"),
				$tabstrip = $frameTab.data("kendoTabStrip"),
				tabItems = $tabstrip.items();
			$.each(nodes, function(i, v) {
				var $frame = $frameTab.find("iframe[treeid=" + (v.ppId || v.pId) + "]"),
					index = $frame.parents("div[role=tabpanel]").index(),
					tabli = tabItems[index - 1];
				$tabli = $(tabli).css({
					'background-color': 'yellow'
				});
				var $ele = $frame.contents().find("#" + v.eleId);
				if ($ele) {
					$ele.addClass("isSelected").css({
						'border-top': '6px solid green'
					});
				}
			});
		}
	},
	showTrigger: function(nodes, direction) {
		//显示触发控件 
		if (nodes && nodes.length) {
			var $frameTab = $("#frameTab"),
				$tabstrip = $frameTab.data("kendoTabStrip"),
				tabItems = $tabstrip.items();
			$.each(nodes, function(i, v) {
				if (v.pObj) {
					var pObj = JSON.parse(v.pObj),
						$frame = $frameTab.find("iframe[treeid=" + (pObj.ppId || pObj.pId) + "]"),
						index = $frame.parents("div[role=tabpanel]").index(),
						tabli = tabItems[index - 1];
					$tabli = $(tabli).css({
						'background-color': 'yellow'
					});
					var $ele = $frame.contents().find("#" + pObj.eleId);
					if ($ele) {
						if (direction == "bottom") {
							$ele.addClass("isSelected").css({
								'border-bottom': '6px solid blue'
							});
						} else {
							$ele.addClass("isSelected").css({
								'border-left': '6px solid red'
							});
						}
					}
				}
			});
		}
	},
	bindData: function($tr, nodeObj) {
		if (nodeObj) {
			$tr.find(".tg-name").val(nodeObj.cname);

			$tr.find(".tg-input").val(nodeObj.inWidgetName);
			$tr.find(".tg-input").data("idata", nodeObj.inWidget);
			$tr.find(".tg-input").data("isGroup", nodeObj.inGroup);

			$tr.find(".tg-trigger").val(nodeObj.widgetName);
			$tr.find(".tg-trigger").data("idata", nodeObj.widget);
			$tr.find(".tg-trigger").data("isGroup", nodeObj.wGroup);

			$tr.find(".tg-outeditor").data("idata", nodeObj.widgetEvent);
		}
	},
	init: function(datas, noResetMaxNo) {
		var $targetObj = $(treeGridExtend.targetName);
		!noResetMaxNo && $targetObj.data("maxNo", 1);
		if (datas && datas != "") {
			//恢复数据 
			function iteratorChildsBuild($ptr, nodes) {
				var length = nodes.length,
					node,
					$tr,
					i = 0;
				for (; i < length; i++) {
					node = nodes[i];
					$tr = treeGridExtend.addChild($ptr, node);
					childs = node.childs;
					if (childs && childs.length) {
						iteratorChildsBuild($tr, childs);
					}
				}
			}
			var retdatasJson = JSON.parse(datas);
			if (retdatasJson && retdatasJson.length) {
				window.isFilter = retdatasJson[0]['isFilter'];
				var nodes = retdatasJson[0]['values'],
					i = 0,
					length = nodes.length,
					node,
					childs,
					maxNo = $targetObj.data("maxNo");
				for (; i < length; i++) {
					node = nodes[i];
					var $tr = $(treeGridExtend.formatLine(maxNo++, null));
					treeGridExtend.addBrother($tr, node);
					$targetObj.data("maxNo", maxNo);
					childs = node.childs;
					if (childs && childs.length) {
						iteratorChildsBuild($tr, childs);
					}
				}
			}
		} else {
			window.isFilter = true;
			var $tr = $(treeGridExtend.formatLine("1", null));
			$targetObj.append($tr).treegrid();
			treeGridExtend._initControl($tr);
			treeGridExtend.bindRowEvent($tr);
		}
		treeGridExtend._initEvent();
		$targetObj.data("isEvent", true);
	},
	getData: function($node) {
		var nodeObj = {};
		//事件定义名称
		nodeObj.cname = $node.find(".tg-name").val();

		//输入控件
		var $in = $node.find(".tg-input"),
			inWidget = $in.data("idata"),
			inGroup = $in.data("isGroup");
		nodeObj.inGroup = inGroup;
		nodeObj.inWidgetName = $in.val();
		if (inWidget) {
			nodeObj.inWidget = inWidget && typeof inWidget == "string" ? JSON.parse(inWidget) : inWidget;
		}

		//触发控件
		var $widget = $node.find(".tg-trigger"),
			widget = $widget.data("idata"),
			wGroup = $widget.data("isGroup");
		nodeObj.widgetName = $widget.val();
		nodeObj.wGroup = wGroup;
		if (widget) {
			nodeObj.widget = widget && typeof widget == "string" ? JSON.parse(widget) : widget;
		}

		//输出控件定义
		var widgetEvent = $node.find(".tg-outeditor").data("idata");
		if (widgetEvent) {
			nodeObj.widgetEvent = widgetEvent && typeof widgetEvent == "string" ? JSON.parse(widgetEvent) : widgetEvent;
		}
		return nodeObj;
	},
	delRowEnd: function($tree) {
		var $firstTr = $tree.find("tbody tr:first");
		if ($firstTr.length) {
			$firstTr.trigger('click');
			triggerFirstParam();
		} else {
			treeGridExtend2.clearNodes();
			//$("#paramFrame").attr("src", "");
			mtxx.retFn = null;
			paramTable.empty();
		}
	},
	beforeClick: function(e, $tr) {
		/*getParamCallbackRets();
		mtxx.TG2Init && saveOutInEditor();*/
		//如果点击是当前行则不处理右侧
		if (mtxx.selectTG1Row && mtxx.selectTG1Row[0] == $tr[0]) {
			return;
		}
		autoSave();
		//$("#paramFrame").attr("src", "");
		mtxx.retFn = null;
		paramTable.empty();
	},
	endClick: function(e, $tr) {
		triggerFirstParam();
	}
});

/**
 * 触发选中第一行的事件
 * @return {[type]} [description]
 */
function triggerFirstParam() {
	var $tg2 = $("#treegrid2"),
		$tr = $tg2.find("tbody tr:first");
	if ($tg2.find("button.mt-view-select").length > 0) {
		return;
	}
	if ($tr.length) {
		$tr.find(".tg-outin-view").trigger("click");
	} else {
		treeGridExtend2.outerAddRow();
		triggerFirstParam();
	}
}

function autoSave() {
	getParamCallbackRets();
	mtxx.TG2Init && saveOutInEditor();
}

//显示事件输入定义器
function showEventsWin(e) {
	var $this = $(this);
	mtxx.buttonSelect = $this;
	treeGridExtend2.clearNodes();
	var datas = $this.data("idata");
	var dd = [{
		nodes: (typeof datas == "string" ? (datas ? JSON.parse(datas) : []) : datas)
	}];
	treeGridExtend2.init(datas ? JSON.stringify(dd) : "");
	mtxx.TG2Init = true;
	//var dialog = $('#eventsWin').data("kendoWindow");
	//dialog.center();
	//dialog.open();
}

function iteratorEventNodes(nodes, rets) {
	var i = 0,
		node,
		name = "",
		$node,
		childs,
		nodeObj,
		childsRets,
		length = nodes.length;
	for (i; i < length; i++) {
		node = nodes[i];
		$node = $(node);
		nodeObj = treeGridExtend2.getData($node) /*nodeToObj($node)*/ ;
		if (!$node.treegrid('isLeaf')) {
			childs = $node.treegrid('getChildNodes');
			childsRets = [];
			iteratorEventNodes(childs, childsRets);
			if (childsRets.length)
				nodeObj.childs = childsRets;
		}
		rets.push(nodeObj);
	}
};
//保存事件定义编辑器
function saveOutInEditor() {

	var rootNodes = $("#treegrid2").treegrid("getRootNodes"),
		retdatas = [],
		retObj = {},
		rets = [];

	iteratorEventNodes(rootNodes, rets);
	var $tr = $("#treegrid").find(".mt-select");
	$tr.find(".tg-outeditor").data("idata", JSON.stringify(rets));
	var kw = $('#eventsWin').data("kendoWindow");
	kw && kw.close();
}
//"<td><input  type='text' class='k-textbox mt-style tg-out' style='width:80%'/><div style='visibility:hidden;display:none;height:20px;width:80%'><ul class='out-menu conmenu' style='width:100%;'><li class='tg-li li-out'>输出控件</li><li class='tg-li li-page'>弹出页面</li></ul><div></td>",
var treeGridExtend2 = new TreeGridExtend();
treeGridExtend2.push({
	targetName: "#treegrid2",
	lineTemp: ["<tr class='{0} {1}'>",
		"<td style='display: inline-flex;width: 100%;'><div style='display: inline-flex;width: 100%;'><input  type='text' class='k-textbox mt-style tg-out' style='width:100%'/><button class='k-button tg-li li-out' title='输出控件'>Q</button><button class='k-button tg-li li-page' title='弹出页面'>P</button></div></td>",
		"<td class='outExt'><input  type='text' class='k-textbox mt-style tg-button tg-outExt' style='width:100%'/></td>",
		"<td><input  type='text' class='k-textbox mt-style tg-button tg-exp' style='width:100%'/></td>",
		"<td><input  type='text' class='k-textbox hidCls mt-style tg-button tg-outin' style='width:90%'/><button class='k-button tg-button tg-outin-view tg-view'>编辑</button></td>",
		"<td class='hbcell'><input  type='text' class='k-textbox mt-style tg-button tg-cell' style='width:90%'/></td>",
		"<td class='outExt'><input  type='text' class='k-textbox hidCls mt-style tg-button tg-callback' style='width:90%'/><button class='hidCls k-button tg-button tg-callback-view tg-view'>编辑</button></td>",
		"<td><button class='k-button addBrother'>同级</button><button class='k-button addChild'>下级</button>{2}<button class='k-button tg-up' title='上移'>↑</button><button class='k-button tg-down' title='下移'>↓</button><button class='k-button tg-power' title='转成上级'>-↑</button><button class='k-button tg-lower' title='转成下级'>-↓</button><button class='k-button tg-copy' title='复制'>c</button></td>",
		"</tr>"
	],
	initControlEvent: function($tr) {
		$tr.delegate('input.tg-out', 'click', function() {
			//$(this).hide().next().css('visibility', 'inherit').css('display', 'inline-block');
			mtxx.ioSelect = $(this);
			mtxx.selectTG2Row = $(this).closest("tr");
			var url = mtxx.contextPath + "/mx/form/defined/ex/eventDefinedSelect?getFn=getInputData&retFn=retInputData&pageId=" + pageId;
			window.open(url);
		});
		//$tr.find(".out-menu").kendoMenu();
		$tr.find(".tg-li").on('click', function() {
			var $this = $(this),
				$input = $this.closest("tr").find("input.tg-out");
			//$input.show().next().css('visibility', 'hidden').css('display', 'none');
			//mtxx.select = $input;
			if ($this.hasClass("li-out")) {
				mtxx.ioSelect = $input;
				mtxx.selectTG2Row = $this.closest("tr");
				/*var url = mtxx.contextPath + "/mx/form/defined/ex/eventDefinedSelect?getFn=getInputData&retFn=retInputData&pageId=" + pageId;
				window.open(url);*/
				//快速按钮
				var link = mtxx.contextPath + "/mx/form/defined/ex/quickSelect?getFn=getInputData&retFn=retInputData&showEvent=true&pageId=" + pageId;
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "快速选择",
					closeBtn: [0, true],
					shade: [0.3, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['', ''],
					fadeIn: 100,
					area: ['480px', ($(window).height() - 100) + 'px' /*'500px'*/ ],
					iframe: {
						src: link
					}
				});
			} else if ($this.hasClass("li-page")) {
				mtxx.ioSelect = $input;
				mtxx.selectTG2Row = $this.closest("tr");
				var link = mtxx.contextPath + "/mx/form/defined/ex/selectPage?retFn=retInputData";
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "弹出页面",
					closeBtn: [0, true],
					shade: [0.3, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['', ''],
					fadeIn: 100,
					area: ['980px', '500px'],
					iframe: {
						src: link
					}
				});
			}
		})
		$tr.delegate('.tg-button', 'click', opWin);
		$tr.delegate('.tg-outeditor', 'click', showEventsWin);
	},
	click: function(e, $tr) {},
	clearNodes: function() {
		$(treeGridExtend2.targetName).find("tbody").empty();
	},
	clearSelect: function() {},
	showSelect: function(nodes) {},
	showTrigger: function(nodes) {},
	selectStyle: function(nodes) {},
	bindData: function($tr, nodeObj) {
		if (nodeObj) {
			$tr.find(".tg-out").val(nodeObj.outWidgetName);
			$tr.find(".tg-out").data("idata", nodeObj.outWidget);
			$tr.find(".tg-out").data("isGroup", nodeObj.outGroup);
			var isExt = false,
				isCallBack = false;
			if (nodeObj.outWidget && nodeObj.outWidget.length == 1) {
				var eName = nodeObj.outWidget[0]["eName"];
				if (eName in outExpAry) {
					isExt = true;
				}
				if ($.inArray(eName, callbackAry) != -1) {
					isCallBack = true;
				}
			}

			if (nodeObj.outExtName || isExt) {
				$tr.find(".tg-outExt").show().val(nodeObj.outExtName);
				$tr.find(".tg-outExt").data("idata", nodeObj.outExt);
				$tr.find(".addChild").show();
			}

			if (nodeObj.callbackName || isCallBack) {
				$tr.find(".tg-callback-view").show();
				$tr.find(".tg-callback") /*.show()*/ .val(nodeObj.callbackName);
				if (nodeObj.callbackName) {
					$tr.find(".tg-callback-view").css("color", "red");
				}
				$tr.find(".tg-callback").data("idata", nodeObj.callback);
				$tr.find(".addChild").show();
			}

			$tr.find(".tg-exp").val(nodeObj.exp);
			$tr.find(".tg-exp").data("expdata", nodeObj.expdata);

			$tr.find(".tg-outin").val(nodeObj.paramName);
			if (nodeObj.paramName) {
				$tr.find(".tg-outin-view").css("color", "red");
			}
			$tr.find(".tg-outin").data("idata", nodeObj.param);

			$tr.find(".tg-cell").val(nodeObj.cellExp);
			$tr.find(".tg-cell").data("expdata", nodeObj.cellExpdata);
		}
	},
	getData: function($node) {
		var nodeObj = {};

		//输出控件
		var $out = $node.find(".tg-out"),
			outWidget = $out.data("idata"),
			outGroup = $out.data("isGroup");
		nodeObj.outGroup = outGroup;
		nodeObj.outWidgetName = $out.val();
		nodeObj.outWidget = outWidget ? outWidget : "";

		//输出扩展事件
		var $outExt = $node.find(".tg-outExt"),
			outExt = $outExt.data("idata");
		nodeObj.outExtName = $outExt.val();
		nodeObj.outExt = outExt ? outExt : "";

		//表达式定义
		var $exp = $node.find(".tg-exp"),
			expdata = $exp.data("expdata");
		nodeObj.exp = $exp.val();
		nodeObj.expdata = expdata ? expdata : "";

		//输入输出参数关系定义
		var $param = $node.find(".tg-outin"),
			param = $param.data("idata");
		nodeObj.paramName = $param.val();
		nodeObj.param = param ? param : "";

		//回调参数定义
		var $callback = $node.find(".tg-callback"),
			callback = $callback.data("idata");
		nodeObj.callbackName = $callback.val();
		nodeObj.callback = callback ? callback : [];


		//eName  pObj-->eleId
		var outWidgets = nodeObj.outWidget ? (typeof nodeObj.outWidget == "string" ? JSON.parse(nodeObj.outWidget) : nodeObj.outWidget) : null; //输出控件
		var paramDatas = nodeObj.param ? (typeof nodeObj.param == "string" ? JSON.parse(nodeObj.param)[0].datas : nodeObj.param[0].datas) : null; //输入输出参数
		if (outWidgets && paramDatas) {
			for (var j = 0; j < outWidgets.length; j++) {
				var outWidget = outWidgets[j];
				var eName = outWidget.eName;
				var eleId = JSON.parse(outWidget.pObj).eleId;
				var datas = paramDatas[eleId];
				if (datas && datas.length) {
					for (var k = 0; k < datas.length; k++) {
						datas[k].eventType = eName;
					}
				}
			}
			var rdatas = [{
				"name": "输入输出关系",
				"datas": paramDatas
			}];
			nodeObj.param = rdatas /*JSON.stringify(rdatas)*/ ;
		}

		//输入输出参数关系定义
		var $cell = $node.find(".tg-cell"),
			cellExpdata = $cell.data("expdata");
		nodeObj.cellExp = $cell.val();
		nodeObj.cellExpdata = cellExpdata ? cellExpdata : null;

		if (!$node.treegrid('isLeaf')) {
			childs = $node.treegrid('getChildNodes');
			childsRets = [];
			iteratorEventNodes(childs, childsRets);
			if (childsRets.length)
				nodeObj.childs = childsRets;
		}

		return nodeObj;
	},
	copyFun: function(e) {
		var $this = $(this),
			$tr = $this.parents("tr");
		treeGridExtend2.init(JSON.stringify([{
			nodes: [treeGridExtend2.getData($tr)]
		}]), true);
	},
	delRowEnd: function($tree) {
		var $selBtn = $tree.find("tbody button.mt-view-select");
		if ($selBtn.length) {} else {
			//$("#paramFrame").attr("src", "");
			mtxx.retFn = null;
			paramTable.empty();
		}
	},
});

//初始化参数
function initExpressionFn() {
	var select = mtxx.select,
		data = select.data("expdata");
	return data ? (typeof data == "string" ? JSON.parse(data) : data) : [];
}
//返回表达式执行函数
function retExpression(data) {
	var select = mtxx.select;
	select.val(data.expVal).data("expdata", data);
}
//获取参数
function getExpression() {
	var $tr = $("#treegrid").find(".mt-select"),
		$input = $tr.find("input.tg-input"),
		idata = $input.data("idata"),
		inDatas = idata ? (typeof idata == "string" ? JSON.parse(idata) : idata) : [],
		expressionData = [],
		inOutParams = parent.initInOutParameterByEvents(null, JSON.stringify(inDatas)),
		inparams = inOutParams.menus['in'],
		inparam, i;
	if (inparams && inparams.length > 0) {
		for (i = 0; i < inparams.length; i++) {
			inparam = inparams[i];
			var express = {};
			express.id = inparam.items.pageId + inparam.id;
			express.name = inparam.text;
			getExpParams(inparam, express.id, expressionData);
			expressionData.push(express);
		}
	}
	return expressionData;
}
//获取表达式参数
function getExpParams(inParam, pid, expressionData) {
	var items = inParam.items;
	for (var j = 0; j < items.length; j++) {
		var param = items[j];
		var subexpress = {};
		subexpress.id = param.id ? (items.pageId + param.id) : (pid + "-" + j);
		if (subexpress.id == pid) {
			subexpress.id = subexpress.id + j;
		}
		subexpress.name = param.text;
		subexpress.pId = pid;
		subexpress.t = "";
		if (param.items) {
			getExpParams(param, subexpress.id, expressionData)
		} else {
			subexpress.pageId = items.pageId;
			subexpress.eleId = param.id;
			subexpress.fnType = param.type;
			subexpress.lev = items.lev;
			subexpress.otype = items.otype;
			subexpress.oEditor = items.oEditor;
			if (param.code) {
				subexpress.datasetId = param.datasetId;
				subexpress.columnName = param.columnName;
				subexpress.dType = param.FieldType;
				subexpress.code = param.code;
				subexpress.value = param.value;
				subexpress.isFn = false;
			} else {
				subexpress.dType = "string";
				subexpress.code = "~F{" + items.pageId + "." + param.id + "." + param.type + "}";
				subexpress.value = "~F{" + inParam.text + "." + param.text + "}";
				subexpress.isFn = true;

				param.columnName && (subexpress.columnName = param.columnName);
			}
		}
		expressionData.push(subexpress);
	}
}

/**
 * 获取iframe里面的输入输出参数定义结果
 * @return {[type]} [description]
 */
function getParamCallbackRets() {
	//新的方式
	var paramName = "输入输出关系",
		paramAry = [];
	if(mtxx.retFn){
		var rets = getRets();
		paramAry.push({
			name: paramName,
			datas: rets
		});
		var triggerButton;
		var selRow = $("#treegrid2").find("button.mt-view-select").closest("tr") || mtxx.selectTG2Row;
		if (selRow) {
			if (mtxx.retFn.name == "retParamFn") {
				triggerButton = selRow.find('.tg-outin')
			} else if (mtxx.retFn.name == "retCallbackFn" ) {
				triggerButton = selRow.find('.tg-callback');
			}
			if (!$.isEmptyObject(rets)) {
				triggerButton && triggerButton.val(paramName) && triggerButton.data("idata", paramAry).next("button").css("color", "red");
			} else {
				triggerButton && triggerButton.val("") && triggerButton.removeData("idata", []).next("button").css("color", "");
			}
		}
	}
	return;
	//以前的实现方式
	var $frame = $("#paramFrame"),
		frameSrc = $frame.attr("src"),
		paramName = "",
		paramAry = [];
	if (frameSrc) {
		if ($frame[0].contentWindow && $frame[0].contentWindow.getRets) {
			var rets = $frame[0].contentWindow.getRets();
			paramName = "输入输出关系";
			paramAry.push({
				name: paramName,
				datas: rets
			});
			var triggerButton;
			var selRow = $("#treegrid2").find("button.mt-view-select").closest("tr") || mtxx.selectTG2Row;
			if (selRow) {
				if (frameSrc.indexOf("retParamFn") != -1) {
					triggerButton = selRow.find('.tg-outin')
				} else if (frameSrc.indexOf("retCallbackFn") != -1) {
					triggerButton = selRow.find('.tg-callback');
				}
				if (!$.isEmptyObject(rets)) {
					triggerButton && triggerButton.val(paramName) && triggerButton.data("idata", paramAry).next("button").css("color", "red");
				} else {
					triggerButton && triggerButton.val("") && triggerButton.removeData("idata", []).next("button").css("color", "");
				}
			}
		}
	}
}

//获取输入参数
function getParamFn() {
	var $tr = $("#treegrid").find(".mt-select"),
		$input = $tr.find(".tg-input"),
		inWidget = $input.data("idata"),
		$tr2 = mtxx.selectTG2Row || $("#treegrid2").find(".mt-select"),
		$out = $tr2.find(".tg-out"),
		outWidget = $out.data("idata"),
		$outExt = $tr2.find(".tg-outExt"),
		outExt = $outExt.data("idata");
	var data = {};
	if (inWidget) {
		data.inWidget = typeof inWidget == "string" ? inWidget : JSON.stringify(inWidget);
	}
	if (outWidget) {
		data.outWidget = typeof outWidget == "string" ? outWidget : JSON.stringify(outWidget);
	}
	if (outExt) {
		data.outExt = outExt;
	}
	return data;
}
//输入输出参数返回设置
function retParamFn(data) {
	if (data) {
		var $tr = mtxx.selectTG2Row || $("#treegrid2").find(".mt-select"),
			$param = $tr.find(".tg-outin");
		$param.val(data[0].name);
		$param.data("idata", data);
	}
}

//回调函数输入输出返回设置
function retCallbackFn(data) {
	if (data) {
		var $tr = mtxx.selectTG2Row || $("#treegrid2").find(".mt-select"),
			$param = $tr.find(".tg-callback");
		$param.val(data[0].name);
		$param.data("idata", data);
	}
}

/**
 * 获取整个数据
 * @return {[type]} [description]
 */
function getSaveEventsDatas() {
	autoSave();
	var rootNodes = $("#treegrid").treegrid("getRootNodes"),
		retdatas = [],
		retObj = {},
		rets = [];

	function filterWidget(widget) {
		if (widget && widget.length) {
			$.each(widget, function(i, v) {
				v = filterNode(v);
			})
		}
	}

	function filterVarVal(varVal) {
		if (varVal && varVal.length) {
			$.each(varVal, function(i, v) {
				filterNode(v.value);
			})
		}
	}

	function filterParamVal(params) {
		if (params && params.length) {
			$.each(params, function(i, v) {
				Object.defineProperty(v, "items", {
					enumerable: false
				});
			})
		}
	}

	function nodeToObj($node) {
		var nodeObj = treeGridExtend.getData($node) || {};

		if (!isFilter) {
			filterWidget(nodeObj.inWidget);
			filterWidget(nodeObj.widget);

			function filterIter(widgetEvent) {
				$.each(widgetEvent, function(i, v) {
					filterWidget(v.outWidget);
					if (v.expdata) {
						filterVarVal(v.expdata.varVal);
					}
					if (v.param && v.param.length) {
						var datas = v.param[0]["datas"];
						for (var p in datas) {
							filterParamVal(datas[p]);
						}
					}
					if (v.childs && v.childs.length) {
						filterIter(v.childs);
					}
				})
			}
			filterIter(nodeObj.widgetEvent);
		}

		return nodeObj;
	};

	function iteratorNodes(nodes, rets) {
		var i = 0,
			node,
			name = "",
			$node,
			childs,
			nodeObj,
			childsRets,
			length = nodes.length;
		for (i; i < length; i++) {
			node = nodes[i];
			$node = $(node);
			nodeObj = nodeToObj($node);
			if (!$node.treegrid('isLeaf')) {
				childs = $node.treegrid('getChildNodes');
				childsRets = [];
				iteratorNodes(childs, childsRets);
				if (childsRets.length)
					nodeObj.childs = childsRets;
			}
			rets.push(nodeObj);
		}
	};
	iteratorNodes(rootNodes, rets);

	return rets;
}

//保存
function saveEvents() {

	var rets = getSaveEventsDatas();

	var retAry = [],
		retObj = {},
		names = "";
	if (rets.length) {
		names = "事件定义器";
		retObj.values = rets;
		retObj.name = names;
		retObj.isFilter = true;
	}
	retAry.push(retObj);
	window.parent.$('#' + nameElementId).val(names);
	window.parent.$('#' + objelementId).val(JSON.stringify(retAry));
	parent.layer.close(parent.layer.getFrameIndex());
}