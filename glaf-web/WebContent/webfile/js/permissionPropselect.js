var kendoRole = {
	//ztree: '',
	//grid: '',
	//button: '',
	splitter: ''
};
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
		var selectNodes = doc.find('.selected-cls'),
			len = selectNodes.length,
			selectNode,
			$selectNode,
			roles = [],
			events = [];
		if (len) {
			var $frame = mtxx.actFrame,
				treeNodeId = $frame.attr("treeId"),
				zTreeObj = $.fn.zTree.getZTreeObj("tree"),
				nodes = zTreeObj.getNodesByParam("pId", treeNodeId),
				node,
				r;

			var $menu = doc.data("context-menu");
			$menu.remove(".kmeau");
			//$kmeau = $menu.element.find('#kmeau');
			//isSelected
			for (var i = 0; i < len; i++) {
				selectNode = selectNodes[i];
				$selectNode = $(selectNode);
				var r = $selectNode.attr("data-role");
				if ($.inArray(r, roles) == -1) {
					roles.push(r);
				}

			}
			//$kmeau.empty();
			if (roles.length == 1) {
				var items = [],
					item,
					selectNodeId = selectNodes[0].id;
				role = roles[0];
				$.each(nodes, function(index, val) {
					if (selectNodeId == (val.eleId || val.id)) {
						$.each(val.children, function(i, v) {
							if (v.isEvn) {
								item = {
									encoded: false
								};
								item.text = "<div class=\"li-span\" onclick=\"selectNode('" + role + "','" + v.eName + "')\">" + v.name + "</div>";
								items.push(item);
							}
						})
					}
				});
				//return false;
				$menu.insertBefore([{
					cssClass: "kmeau",
					text: "选中",
					items: items
				}], "li:first-child");
				//$kmeau.append('<li>kkk</li>bbbb<li>');
			}
			$menu.open((e.clientX + 270), (e.clientY + 85));
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
			//"<li>选中<ul id='kmeau'></ul></li>",
			"<li onclick='removeNode();' class='remove' >移除</li>"
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


		var sourceData = parent[getFn].call(this);
		if (sourceData) { //还原页面
			var ary = sourceData.nodes,
				treeObj = $.fn.zTree.getZTreeObj("tree"),
				ns = treeObj.transformToArray(treeObj.getNodes()),
				n, i, j;
			if (sourceData.conf) {
				mtxx.conf = sourceData.conf;
			}
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