var getDictByCode = function(code, fn) {
	var k;
	$.ajax({
		url: mtxx.contextPath + "/mx/form/defined/getDictByCode",
		data: {
			code: code
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			if (fn)
				fn(data);
			k = data;
		}
	});
	return k;
};

/**
 *保存选中的控件
 */
function saveSelectNodes() {
	var treeObj = $.fn.zTree.getZTreeObj("tree"),
		nodes = treeObj.getCheckedNodes(),
		retDatas = {};
	if (nodes.length == 0) {
		retDatas.names = "";
		retDatas.nodes = "";
	} else {
		var names = "",
			retNodes = [];
		for (var i = 0; i < nodes.length; i++) {
			var node = nodes[i];
			names += node.name + ",";
			var d = $.extend(true, {}, node);
			d.children = "";
			filterNode(d);
			retNodes.push(d);
		}
		retDatas.names = names;
		retDatas.nodes = retNodes;
		retDatas.isGroup = isGroup;
	}

	parent[retFn].call(this, retDatas);
	window.close();
}

function zTreeOnClick(event, treeId, treeNode) {
	//console.log(treeNode);
}

function checkPageElement(treeNode) {
	if (treeNode.isEle) {
		var pId = isGroup ? treeNode.getParentNode().pId : treeNode.pId,
			$frame = $('iframe[treeId=' + pId + ']'),
			$doc = $frame.contents(),
			$target = $doc.find("#" + treeNode.eleId);
		if (treeNode.checked) {
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

function zTreeOnCheck(event, treeId, treeNode) {
	checkPageElement(treeNode);
}

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
	if (isZoom) {
		$html.css("transform", "scale(0.8,0.8)");
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
}
/**
 *选中元素
 */
function selectNode() {
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
		if (!selectNode.hasClass('isSelected')) {
			$.each(nodes, function(i, v) {
				if(isGroup){
					if(v.drole == role){
						$.each(v.children, function(j, cv) {
							if (selectNodeId == cv.eleId) {
								zTreeObj.checkNode(cv, true, false);
							}
						});
					}
				}else{
					if (selectNodeId == v.eleId) {
						zTreeObj.checkNode(nodes[i], true, false);
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
				if(isGroup){
					if(v.drole == role){
						$.each(v.children, function(j, cv) {
							if (selectNodeId == cv.eleId) {
								zTreeObj.checkNode(cv, false, false);
							}
						});
					}
				}else{
					if (selectNodeId == v.eleId) {
						zTreeObj.checkNode(nodes[i], false, false);
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

var kendoRole = {
	ztree: '',
	grid: '',
	button: '',
	splitter: ''
};

function selectedFunc(jq) {
	var selected = "selected-cls";
	var isSelected = jq.hasClass("isSelected");
	this.addISelectedStyle = function() {
		jq.removeClass(selected).addClass("isSelected").css({
			'background-color': 'yellow',
			//"boder-color" : "red"
		});
	};
	this.addStyle = function() {
		if (!jq.hasClass(selected)) {
			jq.addClass(selected).css("border-color", "red");
			//jq.addClass(selected).css("background-color","yellow");
		}
	};
	this.removeStyle = function() {
		if (!isSelected) {
			jq.css({
				"background-color": "",
				"border-color": ""
			}).removeClass(selected);
		} else {
			this.addISelectedStyle();
		}
	};
	this.removeAll = function() {
		jq.attr({
			class: ''
		}).css({
			"background-color": "",
			"border-color": ""
		});
	};
}
/**
 * 选中事件
 */
var divEvents = {
	document: null,
	isInit: false,
	init: function(e) {
		var that = this;
		that.document.data("select-item", that.document.find("[crtltype]"));
		that.isInit = true;
	},
	bindMouseDown: function(e) {
		var that = this,
			doc = that.document;
		if (!that.isInit) {
			that.init(e);
		}
		var position = {
				x: (e.clientX + 270),
				y: (e.clientY + 85)
			},
			$div = divEvents.$div = $("<div>", {
				'class': "ui-selectable-helper"
			}).css({
				position: 'absolute',
				"top": position.y + "px",
				"left": position.x + "px"
			}).appendTo('body');
		$div.data("position", position);

		doc.bind("mousemove", function(e) {
			divEvents.bindMouseMove(e);
		}).bind("mouseup", function(e) {
			divEvents.bindMouseUp(e);
		}).bind("selectstart", function() {
			//return false; 
		}).data("start", {
			x: (e.clientX),
			y: (e.clientY)
		});
	},
	unbind: function() {
		var that = this,
			doc = that.document;

		$('.ui-selectable-helper').remove();
		doc.unbind("mousemove").unbind("mouseup");
	},
	bindMouseUp: function(e) {
		var that = this,
			doc = that.document;
		this.unbind();
		if (doc.find('.selected-cls').length > 0) {
			doc.data("context-menu").open((e.clientX + 270), (e.clientY + 85));
		}
		//return false;
	},
	bindMouseMove: function(e) {
		var that = this,
			doc = that.document;
		doc.data("context-menu").close();
		var $div = divEvents.$div;
		if ($div[0]) {
			var pos = $div.data('position') || {};
			$div.css({
				"width": (e.clientX * 1 + 270 - pos.x * 1) + "px",
				"height": (e.clientY * 1 + 85 - pos.y * 1) + "px"
			});
			divEvents.findCover(e);
		}
		//return false;
	},
	findCover: function(e) { //查找包含元素
		var that = this,
			$this = that.document,
			selectItems = $this.data("select-item");;
		if (!$this.data("init-select")) {

			if (selectItems && selectItems.length) {
				selectItems.each(function(i, v) {
					var b = $(this),
						c = b.offset();
					var opt = {
						element: this,
						$element: b,
						left: c.left,
						top: c.top,
						right: c.left + b.outerWidth(),
						bottom: c.top + b.outerHeight()
					};
					$.data(this, "selectable-item", opt);
				});
			}
			$this.data("init-select", true);
		}
		var start = $this.data("start") || {
			x: 0,
			y: 0
		};
		var docScrollTop = $this.scrollTop(),
			docScrollLeft = $this.scrollLeft();
		var c = start.x,
			d = start.y + (docScrollTop > 0 ? docScrollTop : 0),
			X = e.pageX - docScrollLeft,
			Y = e.pageY + (docScrollTop > 0 ? docScrollTop - 120 : 0);
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
		if (selectItems && selectItems.length) {
			selectItems.each(function() {
				var i = $.data(this, "selectable-item");
				if (!i)
					return;
				var j = !(i.left > X || i.right < c || i.top > Y || i.bottom < d),
					$this = i.$element;
				var selectedFn = new selectedFunc($this);
				if (j) {
					if (($this.attr('data-role') in kendoRole)) {
						return;
					}
					if ($this.hasClass("isSelected")) {

						//return;
					}
					selectedFn.addStyle();
				} else {
					selectedFn.removeStyle();
				}
			});
		}

	},
	onDrag: function(e) {

	}
};

function FrameLoadedFunc(o) {
	this.o = $(o);
}
FrameLoadedFunc.prototype = {
	constructor: FrameLoadedFunc,
	exec: function() {
		return this["viewSort"]();
	},
	sort: function() {
		var that = this;
	},
	viewSort: function() {
		var that = this;
		var doc = that.o.contents();
		/**
		 * 右键菜单 start
		 */
		var lis = [
			"<li onclick='selectNode();' >选中</li>",
			"<li onclick='removeNode();' >移除</li>"
		];

		//写入拖拽选择框  
		if (!$("#context-menu")[0]) {


			var indexArray;
			var $context = $("<ul>", {
				id: 'context-menu'
			}).append(lis.join('')).appendTo(doc.find('body')).kendoContextMenu({
				target: doc.find("body"),
				alignToAnchor: true,
				open: function(e) {
					$context.remove("#addsort_id li");
					var items = new Array();
					indexArray = new Array();
					$(".view-tr-cls").each(function(i) {
						var $this = $(this);
						items.push({
							text: $this.find('.k-span').text()
						});
						indexArray.push({
							trId: $this.attr('id'),
							text: $this.find('.k-span').text(),
							level: -1,
							click: function(e) {
								addSelected($this, true);
							}
						});
					});
					if (items.length > 0) {
						$context.append(items, "#addsort_id");
					}
				},
				select: function(e) {
					var $item = $(e.item),
						$parent = $item.closest("ul").closest("li");
					if ($parent[0]) {
						if ($parent.attr("id") == 'addsort_id') {
							var index = $item.index();
							var options = indexArray[index];
							if (options && options.click) {
								options.click(e);
							}
						}
					}
				}
			}).data("kendoContextMenu");
			/**
			 * 右键菜单 end
			 */
			//绑定鼠标按下事件
			divEvents.document = doc;
			doc.on('mousedown', function(e) {
				divEvents.bindMouseDown(e);
			}).data('context-menu', $context);
		}



		if (sourceData) { //还原页面
			var ary = sourceData.nodes,
				treeObj = $.fn.zTree.getZTreeObj("tree"),
				ns = treeObj.transformToArray(treeObj.getNodes()),
				n, i, j;
			if (ary) {
				for (j = 0; j < ns.length; j++) {
					n = ns[j];
					for (i = 0; i < ary.length; i++) {
						if (n.id == ary[i].id) {
							treeObj.checkNode(n, true, false, false);
							checkPageElement(n);
						}
					}
				}
			}

		}

	}
};
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
 * jQuery treegrid extend
 */
var TreeGridExtend = function() {
	var obj = {};
	//行模板 treegrid-3 treegrid-parent-1 treegrid-collapsed
	//<button class='k-button addChild'>+ 下级</button>
	var lineTemp = ["<tr class='{0} {1}'>",
		"<td><input  type='text' class='k-textbox tg-name'/></td>",
		"<td><input  type='text' class='k-textbox tg-button tg-input'/></td>",
		"<td><input  type='text' class='k-textbox tg-button tg-execExp'/></td>",
		"<td><input  type='text' class='k-textbox tg-button tg-trigger'/></td>",
		"<td><input  type='text' class='tg-type'/></td>",
		"<td><input  type='text' class='k-textbox tg-button tg-exp'/></td>",
		"<td><input  type='text' class='k-textbox tg-tip'/></td>",
		"<td><input  type='text' class='tg-tipType'/></td>",
		"<td><button class='k-button addBrother'>+ 同级</button>{2}<button class='k-button tg-up'>↑</button><button class='k-button tg-down'>↓</button></td>",
		"</tr>"
	];
	obj.targetName = ".tree";
	obj.lineTemp = lineTemp;
	obj.nodeClassPrefix = "treegrid-";
	obj.parentNodeClassPrefix = obj.nodeClassPrefix + "parent-";
	obj._delStr = "<button class='k-button del_row'>删除</button>";
	obj.formatLine = function(arg0, arg1, isRemDel) {
		arg0 = this.nodeClassPrefix + arg0, arg1 = arg1 == null ? "" : this.parentNodeClassPrefix + arg1;
		return kendo.format(this.lineTemp.join(), arg0, arg1, isRemDel ? "" : this._delStr);
	};
	obj.isClick = true;
	obj.bindData = function($tr, nodeObj) {
		if (nodeObj) {
			$tr.find(".tg-name").val(nodeObj.name);

			$tr.find(".tg-input").val(nodeObj.input);
			$tr.find(".tg-input").data("idata", nodeObj.inputData);

			$tr.find(".tg-execExp").val(nodeObj.execExp);
			$tr.find(".tg-execExp").data("expdata", nodeObj.execExpData);

			$tr.find(".tg-trigger").val(nodeObj.trigger);
			$tr.find(".tg-trigger").data("idata", nodeObj.triggerData);

			//$tr.find(".tg-type").val(nodeObj.type);
			$tr.find("div .tg-type").data("kendoMultiSelect").value(nodeObj.type.split(","));

			$tr.find(".tg-exp").val(nodeObj.exp);
			$tr.find(".tg-exp").data("expdata", nodeObj.expData);

			$tr.find(".tg-tip").val(nodeObj.tip);

			$tr.find("span .tg-tipType").data("kendoDropDownList").value(nodeObj.tipType);
			//$tr.find(".tg-tipType").val();
		}
	};
	obj.clearSelect = function() {
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
	};
	obj.showSelect = function(nodes) {
		//清除选中 
		if (nodes && nodes.length) {
			var $frameTab = $("#frameTab"),
				$tabstrip = $frameTab.data("kendoTabStrip"),
				tabItems = $tabstrip.items();
			$.each(nodes, function(i, v) {
				var $frame = $frameTab.find("iframe[treeid=" + v.pId + "]"),
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
	};
	obj.showTrigger = function(nodes) {
		//清除选中 
		if (nodes && nodes.length) {
			var $frameTab = $("#frameTab"),
				$tabstrip = $frameTab.data("kendoTabStrip"),
				tabItems = $tabstrip.items();
			$.each(nodes, function(i, v) {
				var $frame = $frameTab.find("iframe[treeid=" + v.pId + "]"),
					index = $frame.parents("div[role=tabpanel]").index(),
					tabli = tabItems[index - 1];
				$tabli = $(tabli).css({
					'background-color': 'yellow'
				});
				var $ele = $frame.contents().find("#" + v.eleId);
				if ($ele) {
					$ele.addClass("isSelected").css({
						'border-left': '6px solid red'
					});
				}
			});
		}
	};
	obj.click = function(e, $tr) {
		obj.clearSelect();

		var $input = $tr.find(".tg-input"),
			inputData = $input.data("idata"),
			nodes = [];
		if (inputData) {
			var idatas = JSON.parse(inputData);
			nodes = idatas.nodes;
		}
		obj.showSelect(nodes);


		var $trigger = $tr.find(".tg-trigger"),
			triggerData = $trigger.data("idata");
		nodes = [];
		if (triggerData) {
			var idatas = JSON.parse(triggerData);
			nodes = idatas.nodes;
		}
		obj.showTrigger(nodes);

	};
	obj.clickFun = function(e) {
		var $tr = $(this);
		$tr.css("background", "#207092");
		$tr.siblings().css("background", "");
		obj.click(e, $tr);
	};
	obj.dbclick = function() {

	};
	obj.bindRowEvent = function($tr) {
		if (obj.isClick) {
			$tr.on("click", obj.clickFun);
		}
	};
	//增加同级
	obj.addBrother = function(target, nodeDatas) {
		var $k;
		if (target instanceof jQuery) {
			$k = target;
			$(obj.targetName).append($k);
		} else {
			var $tr = $(this).parents("tr"),
				cls = $tr.prop("class"),
				maxNo = $(obj.targetName).data("maxNo"),
				//m = cls.match(new RegExp(obj.nodeClassPrefix + "(\\d+)")),
				n = cls.match(new RegExp(obj.parentNodeClassPrefix + "(\\d+)")), //匹配父节点class
				arg0 = maxNo + 1,
				arg1 = (n && n.length >= 2 ? parseInt(n[1]) : null);
			$k = $(obj.formatLine(arg0, arg1));
			$tr.before($k);
			$(obj.targetName).data('maxNo', maxNo + 1);
		}
		//注册行事件
		obj.bindRowEvent($k);
		//渲染
		$(obj.targetName).treegrid();
		obj._initControl($k, nodeDatas);
	};
	//增加下级
	obj.addChild = function(target, nodeDatas) {
		var $tr;
		if (target instanceof jQuery) {
			$tr = target;
		} else {
			$tr = $(this).parents("tr");
		}
		var cls = $tr.prop("class"),
			maxNo = $(obj.targetName).data("maxNo"),
			m = cls.match(new RegExp(obj.nodeClassPrefix + "(\\d+)")),
			arg0 = maxNo + 1,
			arg1 = (m && m.length >= 2 ? parseInt(m[1]) : null),
			$k = $(obj.formatLine(arg0, arg1));
		if (target instanceof jQuery) {
			$(obj.targetName).append($k);
		} else {
			$tr.after($k);
		}
		//注册行事件
		obj.bindRowEvent($k);
		//渲染
		$(obj.targetName).data('maxNo', arg0).treegrid();
		obj._initControl($k, nodeDatas);
		return $k;
	};
	//删除行
	obj.delRow = function() {
		//迭代删除
		function delIterator($tr) {
			$tr.treegrid('getChildNodes').each(function(i, n) {
				var $this = $(n);
				if (!$this.treegrid("isLeaf")) { //不是叶子的话遍历删除
					delIterator($this);
				}
				$this.remove();
			});
		}
		var $this = $(this),
			$tr = $this.parents("tr");
		delIterator($tr);
		$tr.remove();
	};
	obj.outerAddRow = function() {
		var maxNo = $(obj.targetName).data("maxNo"),
			$tr = $(obj.formatLine(++maxNo, null));
		obj.addBrother($tr);
		$(obj.targetName).data("maxNo", maxNo);
	};
	obj._initControl = function($tr, nodeDatas) {
		$tr.find(".tg-type").kendoMultiSelect({
			dataSource: obj.validateTypeDataSource,
			dataTextField: "name",
			dataValueField: "code"
		});
		$tr.find(".tg-tipType").kendoDropDownList({
			dataSource: obj.validateTipTypeDataSource,
			dataTextField: "name",
			dataValueField: "code"
		});
		obj.bindData($tr, nodeDatas);
	};
	//注册事件
	obj._initEvent = function() {
		$(obj.targetName).delegate(".addBrother", "click", this.addBrother).delegate(".addChild", "click", this.addChild).delegate(".del_row", "click", this.delRow)
			.delegate('.tg-button', 'click', opWin).delegate(".tg-up", "click", this.upFun).delegate(".tg-down", "click", this.downFun);
	};
	obj.init = function(datas) {
		obj.validateTypeDataSource = getDictByCode("validate_type");
		obj.validateTipTypeDataSource = getDictByCode("validate_tip_type");
		var $targetObj = $(obj.targetName).data("maxNo", 1);
		if (datas && datas != "") {
			//恢复数据 
			function iteratorChildsBuild($ptr, nodes) {
				var length = nodes.length,
					node,
					$tr,
					i = 0;
				for (; i < length; i++) {
					node = nodes[i];
					$tr = obj.addChild($ptr, node.node);
					childs = node.childs;
					if (childs && childs.length) {
						iteratorChildsBuild($tr, childs);
					}
				}
			}
			var retdatasJson = JSON.parse(datas);
			if (retdatasJson && retdatasJson.length) {
				var nodes = retdatasJson[0]['nodes'],
					i = 0,
					length = nodes.length,
					node,
					childs,
					maxNo = $targetObj.data("maxNo");
				for (; i < length; i++) {
					node = nodes[i];
					var $tr = $(obj.formatLine(maxNo++, null));
					obj.addBrother($tr, node);
					$targetObj.data("maxNo", maxNo);
					childs = node.childs;
					if (childs && childs.length) {
						iteratorChildsBuild($tr, childs);
					}
				}
			}
		} else {
			var $tr = $(obj.formatLine("1", null));
			$targetObj.append($tr).end().treegrid();
			obj._initControl($tr);
			obj.bindRowEvent($tr);
		}
		obj._initEvent();
	};
	obj._getLastNode = function($tr) {
		var lastNode = $tr;
		$childNodes = $tr.treegrid("getChildNodes");
		if ($childNodes && $childNodes.length) {
			lastNode = obj._getLastNode($($childNodes[$childNodes.length - 1]));
		}
		return lastNode;
	}
	obj.upFun = function() {
		var $this = $(this),
			$nodes,
			$tree = $(obj.targetName),
			$tr = $this.parents("tr"),
			lastIndex = obj._getLastNode($tr).index(),
			depth = $tr.treegrid("getDepth"),
			index = $tr.index(),
			$nodes = depth ? $tr.treegrid("getParentNode").treegrid("getChildNodes") : $tree.treegrid("getRootNodes"),
			indexInArray = $nodes.index($tr);
		if (index && indexInArray) {
			var prevNode = $nodes[indexInArray - 1],
				$prevNode = $(prevNode),
				prevIdex = $prevNode.index(),
				$moveNodes = $tree.find("tr:gt(" + index + "):lt(" + (lastIndex - index + 1) + ")");
			$moveNodes.insertBefore($prevNode);
		}
	};
	obj.downFun = function() {
		var $this = $(this),
			$nodes,
			$tree = $(obj.targetName),
			allLength = $tree.find("tbody tr").length,
			$tr = $this.parents("tr"),
			lastIndex = obj._getLastNode($tr).index(),
			depth = $tr.treegrid("getDepth"),
			index = $tr.index(),
			$nodes = depth ? $tr.treegrid("getParentNode").treegrid("getChildNodes") : $tree.treegrid("getRootNodes"),
			indexInArray = $nodes.index($tr);
		if (index < allLength - 1 && indexInArray < $nodes.length - 1) {
			var nextNode = $nodes[indexInArray + 1],
				$nextNode = $(nextNode),
				nextIndex = $nextNode.index(),
				$moveNodes = $tree.find("tr:gt(" + index + "):lt(" + (nextIndex - index) + ")"),
				$nextLastNode = obj._getLastNode($nextNode);
			$moveNodes.insertAfter($nextLastNode);
		}
	};
	return obj;
};

function getInputData() {
	var select = mtxx.select,
		data = select.data("idata");
	return data ? JSON.parse(data) : [];
}

function retInputData(data) {
	var $select = mtxx.select;
	$select.val(data.names).data("idata", JSON.stringify(data));
	var $tr = $select.parents("tr");
	$tr.trigger("click");
}
//初始化参数
function initExpressionFn() {
	var select = mtxx.select,
		data = select.data("expdata");
	return data ? JSON.parse(data) : [];
}
//返回表达式执行函数
function retExpression(data) {
	var select = mtxx.select;
	select.val(data.expVal).data("expdata", JSON.stringify(data));
}
//获取参数
function getExpression() {
	var select = mtxx.select,
		$tr = select.parents("tr"),
		$input = $tr.find("input.tg-input"),
		idata = $input.data("idata"),
		inDatas = idata ? JSON.parse(idata).nodes : [],
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
			subexpress.iEditor = items.oEditor;
			if (param.code) {
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
			}
		}
		expressionData.push(subexpress);
	}
}
var opWin = function() {
	var url = mtxx.contextPath,
		$this = $(this);
	mtxx.select = $this;
	if ($this.hasClass("tg-input") || $this.hasClass("tg-trigger")) { //执行验证匹配项（前提）
		url += "/mx/form/defined/ex/verificationSetting?getFn=getInputData&retFn=retInputData&pageId=" + pageId;
	} else if ($this.hasClass("tg-execExp") || $this.hasClass("tg-exp")) { //执行验证表达式
		url += "/mx/expression/defined/view?category=front&retFn=retExpression&getFn=getExpression&initExpFn=initExpressionFn&notValidate=true"
	}
	window.open(url);
};


function saveValidate() {
	var rootNodes = $(".tree").treegrid("getRootNodes"),
		retdatas = [],
		retObj = {},
		rets = [];

	function nodeToObj($node) {
		var nodeObj = {};
		nodeObj.name = $node.find(".tg-name").val();

		nodeObj.input = $node.find(".tg-input").val();
		nodeObj.inputData = $node.find(".tg-input").data("idata");

		nodeObj.execExp = $node.find(".tg-execExp").val();
		nodeObj.execExpData = $node.find(".tg-execExp").data("expdata");

		nodeObj.trigger = $node.find(".tg-trigger").val();
		nodeObj.triggerData = $node.find(".tg-trigger").data("idata");

		nodeObj.type = $node.find("div .tg-type").data("kendoMultiSelect").value().join(",");

		nodeObj.exp = $node.find(".tg-exp").val();
		nodeObj.expData = $node.find(".tg-exp").data("expdata");

		nodeObj.tip = $node.find(".tg-tip").val();

		nodeObj.tipType = $node.find("span .tg-tipType").data("kendoDropDownList").value();
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
	//console.log(rets);
	if (rets && rets.length) {
		name = "验证器已定义";
		retObj.name = name;
		retObj.nodes = rets;
		retdatas.push(retObj);
	}
	parent.$("#" + nameElementId).val(name);
	parent.$("#" + objelementId).val((retdatas && retdatas.length) ? JSON.stringify(retdatas) : "");

	parent.layer.close(parent.layer.getFrameIndex());
}