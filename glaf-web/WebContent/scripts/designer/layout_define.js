//验证当前浏览器内核环境
var Sys = (function (ua) {
	var s = {};
	s.IE = ua.match(/msie ([\d.]+)/) ? true : false;
	s.Firefox = ua.match(/firefox\/([\d.]+)/) ? true : false;
	s.Chrome = ua.match(/chrome\/([\d.]+)/) ? true : false;
	s.IE6 = (s.IE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6)) ? true : false;
	s.IE7 = (s.IE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 7)) ? true : false;
	s.IE8 = (s.IE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 8)) ? true : false;
	return s;
})(navigator.userAgent.toLowerCase());
//闭包限定命名空间
(function ($) {
	//默认参数
	var defaluts = {
		container: ".designer"
	};
	var methods = {
		init: function (options) {
			//支持多选择器多对象
			return this.each(function () {
				//支持链式调用
				var $this = $(this);
				var opts = $.extend({}, defaluts, options); //覆盖插件默认参数
				if (!isValid(opts)) {
					return;
				}
				$this.data("options", opts);
				$this.layout("bindEvent");
			});
		},
		refresh_:function(){
			var opts = this.data("options");
			var $container = $(opts.container);
			//获取layoutCell
			var cells = this.find(".laycell");
			var layoutcell,
			$cell;
			var $this = this;
			$.each(cells, function (i, cell) {
				$cell = $(cell);
				//检测是否存在此布局格
				layoutcell = $container.find(".lay_" + $cell.attr("layno"));
				if (layoutcell.length == 1) {
					$cell.addClass("active");
				}else{
					$cell.removeClass("active");
				}
			});
		},
		bindEvent: function () {
			var opts = this.data("options");
			var $container = $(opts.container);
			//获取layoutCell
			var cells = this.find(".laycell");
			var layoutcell,
			$cell;
			var $this = this;
			$.each(cells, function (i, cell) {
				$cell = $(cell);
				//检测是否存在此布局格
				layoutcell = $container.find(".lay_" + $cell.attr("layno"));
				if (layoutcell.length == 1) {
					$cell.addClass("active");
				}
				$this.layout("bindCellClickEvent", $cell);
			});
		},
		bindCellClickEvent: function ($cell) {
			var opts = this.data("options");
			var $container = $(opts.container);
			var layno,
			active;
			var $this = this;
			$cell.click(function () {
				layno = $(this).attr("layno");
				active = false;
				if ($container.find(".lay_" + layno).length > 0) {
					active = true;
				}
				if (active) {
					//取消
					$this.layout("removeLayoutCell", layno);
					$(this).removeClass("active");
				} else {
					//增加
					$this.layout("addLayoutCell", layno);
					$(this).addClass("active");
				}
			});
		},
		addLayoutCell: function (layno) {
			//验证布局容器是否存在
			var opts = this.data("options");
			var $container = $(opts.container);
			var layoutContainer = $container.find(".layoutContainer").find(".column:first");
			if (layoutContainer.length == 0) {
				layoutContainer = this.layout("createLayoutContainer", $container).find(".column");
			}
			if (layno == "1" || layno == "3") {
				if (layno == "1") {
					this.layout("createLayoutContainer", layoutContainer, layno, "prepend");
				} else {
					this.layout("createLayoutContainer", layoutContainer, layno);
				}
			} else {
				//检测中间内容区首层栅格分列数
				var cols = 0;
				var colArr = [0, 0, 0];
				var insertPos = -1;
				var insertDirect;
				var insertLayno;
				var firstColLen = layoutContainer.find(".lay" + "_2_1").length;
				var divColsNum;
				var insertL = layno.split("_").length;
				if (firstColLen > 0 || layno == "2_1") {
					cols++;
					if (firstColLen > 0) {
						colArr[0] = 1;
					} else {
						insertPos = 0;
					}
				}
				var middleColLen = layoutContainer.find("[class*='lay" + "_2_2']").length;
				if (middleColLen > 0 || layno.indexOf("2_2") > -1) {
					cols++;
					if (middleColLen > 0) {
						colArr[1] = 1;
					} else {
						insertPos = 1;
					}
				}
				var lastColLen = layoutContainer.find(".lay" + "_2_3").length;
				if (lastColLen > 0 || layno == "2_3") {
					cols++;
					if (lastColLen > 0) {
						colArr[2] = 1;
					} else {
						insertPos = 2;
					}
				}
				if (insertPos >= 0) {
					//创建一个行栅格列数为1
					if (cols == 1) {
						var topLayout = this.layout("createLayoutContainer", layoutContainer, "2", "after", "1");
						if (insertL == 2) {
							topLayout.find(".column:first").addClass("lay" + "_" + layno);
						} else {
							var layoutContainer = topLayout.find(".column:first");
							layoutContainer.addClass("lay_2_2");
							if (insertL == 4) {
								layoutContainer = this.layout("createLayoutContainer", layoutContainer, "2_2_2");
								layoutContainer.find(".column:first").addClass("lay_" + layno);
							} else {
								this.layout("createLayoutContainer", layoutContainer, layno);
							}
						}
						return;
					}
					//前面插入列
					if (insertPos == 0) {
						insertDirect = "prepend";
						if (cols == 2) {
							divColsNum = "3,9";
						} else if (cols == 3) {
							divColsNum = "3,6,3";
						}
					}
					//中间插入列
					else if (insertPos == 1) {
						if (cols == 2 && colArr[0] == 1) {
							divColsNum = "3,9";
						} else if (cols == 2 && colArr[2] == 1) {
							insertDirect = "before";
							insertLayno = "2_3";
							divColsNum = "9,3";
						} else if (cols == 3) {
							insertDirect = "before";
							insertLayno = "2_3";
							divColsNum = "3,6,3";
						}
					}
					//尾部插入列
					else if (insertPos == 2) {
						if (cols == 2) {
							divColsNum = "9,3";
						} else if (cols == 3) {
							divColsNum = "3,6,3";
						}
					}
					layoutContainer = layoutContainer.find(".lay" + "_2").find(".row:first");
					var targetLayno;
					if (insertL > 2) {
						targetLayno = layno;
						layno = "2_2";
					}
					this.layout("createCol", layoutContainer, layno, divColsNum, insertDirect, insertLayno);
					layno = targetLayno;
				}

				if (insertL > 2) {
					var colContainer = layoutContainer.find(".lay_2_2");
					var insertLayno;
					var insertDirect;
					targetLayno = layno;
					if (layno == "2_2_1" || layno == "2_2_3") {
						if (layno == "2_2_1") {
							insertDirect = "prepend";
						}
						this.layout("createLayoutContainer", colContainer, layno, insertDirect, insertLayno);

					} else if (layno.indexOf("2_2_2") > -1) {
						insertDirect = null;
						insertLayno = null;
						var currColcontainer = layoutContainer.find(".lay_2_2_2");
						if (currColcontainer.length == 0) {
							insertDirect = "after";
							insertLayno = "2_2_1";
							if (layoutContainer.find(".lay_2_2_1").length == 0) {
								insertDirect = "prepend";
								insertLayno = undefined;
							}
							currColcontainer = this.layout("createLayoutContainer", colContainer, "2_2_2", insertDirect, insertLayno);
							colContainer.find(".column:first").addClass("lay_" + layno);
						} else {
							if (targetLayno == "2_2_2_1") {
								insertDirect = "prepend";
							}
							var divColsNum = "6,6";
							currColcontainer = currColcontainer.find(".row:first");
							this.layout("createCol", currColcontainer, targetLayno, divColsNum, insertDirect);
						}
					}
				}
			}
		},
		removeLayoutCell: function (layno) {
			//验证布局容器是否存在
			var opts = this.data("options");
			var $container = $(opts.container);
			var layoutContainer = $container.find(".layoutContainer");
			if (layoutContainer.length == 1) {
				this.layout("removeLayoutContainer", layoutContainer, layno);
			}
		},
		removeLayoutContainer: function ($container, layno) {
			var layoutDom = $container.find(".lay_" + layno);
			var rowFlag = true;
			if (layoutDom.hasClass("column")) {
				rowFlag = false;
			}
			if (!rowFlag) {
				//记录当前栅格数
				var colnumCss = layoutDom.attr("class").match((/col\-(md|mmd|24md)\-\d+/));
				var currClass,
				currCells;
				var preColumnClass;
				if (colnumCss != null) {
					currClass = colnumCss[0];
					currCells = currClass.match((/\d+$/))[0];
					preColumnClass = getGridSysByColumnClass(currClass);
				}
				//获取兄弟节点，前一个节点存在，取前一个节点，不存在则取后一个节点
				var slibcol;
				if (layoutDom.prev().length > 0) {
					slibcol = layoutDom.prev();
				} else if (layoutDom.next().length > 0) {
					slibcol = layoutDom.next();
				}
				if (slibcol != undefined) {
					if(slibcol.attr("class").match((/col\-(md|mmd|24md)\-\d+/))!=null){
					var slibcolClass = slibcol.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
					var slibCurrCells = slibcolClass.match((/\d+$/))[0];
					var slibNewCells = parseInt(currCells) + parseInt(slibCurrCells);
					var slibNewClass = preColumnClass + slibNewCells;
					slibcol.removeClass(slibcolClass);
					slibcol.addClass(slibNewClass);
					}
				}
			}
			layoutDom.addClass("removeContainer");
			this.layout("removeEmptyContanier", $container, layoutDom);
		},
		removeEmptyContanier: function ($container, contanier) {
			var contanierDom = contanier.closest("[class*='lay_']:not(.removeContainer)");
			var layno;
			while (contanierDom.length > 0 && contanierDom.find("[class*='lay_']:not(.removeContainer)").length == 0) {
				contanierDom.addClass("removeContainer");
				if(contanierDom.attr("class").match(/lay(_\d){1,}/)!=null){
					layno = contanierDom.attr("class").match(/lay(_\d){1,}/)[0];
					layno = layno.replace("lay_","");
					this.find("[layno='" + layno + "']").removeClass("active");
				}
				var rowFlag = true;
				if (contanierDom.hasClass("column")) {
					rowFlag = false;
				}
				if (!rowFlag) {
					//记录当前栅格数
					var colnumCss = contanierDom.attr("class").match((/col\-(md|mmd|24md)\-\d+/));
					var currClass,
					currCells;
					var preColumnClass;
					if (colnumCss != null) {
						currClass = colnumCss[0];
						currCells = currClass.match((/\d+$/))[0];
						preColumnClass = getGridSysByColumnClass(currClass);
					}
					//获取兄弟节点，前一个节点存在，取前一个节点，不存在则取后一个节点
					var slibcol;
					if (contanierDom.prev().length > 0) {
						slibcol = contanierDom.prev();
					} else if (contanierDom.next().length > 0) {
						slibcol = contanierDom.next();
					}
					if (slibcol != undefined) {
						var slibcolClass = slibcol.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
						var slibCurrCells = slibcolClass.match((/\d+$/))[0];
						var slibNewCells = parseInt(currCells) + parseInt(slibCurrCells);
						var slibNewClass = preColumnClass + slibNewCells;
						slibcol.removeClass(slibcolClass);
						slibcol.addClass(slibNewClass);
					}
				}
				contanierDom = contanierDom.closest("[class*='lay_']:not(.removeContainer)");
			}
			$container.find(".removeContainer").remove();
		},
		createCol: function ($container, layno, divColsNum, insertPos, insertLayno) {
			var coldom = $("<div class=\"column ui-sortable\"></div>");
			coldom.append($("#colconfigTemplate").html());
			coldom.addClass("lay" + "_" + layno);
			if (insertPos == undefined) {
				$container.append(coldom);
			} else {
				var insertLaynoColDom = $container.find(".lay_" + insertLayno);
				if (insertPos == "prepend") {
					$container.prepend(coldom);
				} else if (insertLaynoColDom.length > 0) {
					if (insertPos == "before") {
						insertLaynoColDom.before(coldom);
					} else if (insertPos == "after") {
						insertLaynoColDom.after(coldom);
					}
				} else {
					$container.append(coldom);
				}
			}
			var cols = $container.children(".column");
			var currClass,
			newClass,
			preColumnClass;
			var divColsNumArr = divColsNum.split(",");
			//获取列栅格样式
			if ($container.children("[class*=col-md]").length > 0) {
				preColumnClass = "col-md-";
			} else if ($container.children("[class*=col-mmd]").length > 0) {
				preColumnClass = "col-mmd-";
			} else if ($container.children("[class*=col-24md]").length > 0) {
				preColumnClass = "col-24md-";
			}
			$.each(cols, function (i, item) {
				if ($(item).attr("class").match((/col\-(md|mmd|24md)\-\d+/)) != null) {
					currClass = $(item).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
					$(item).removeClass(currClass);
				}
				newClass = preColumnClass + divColsNumArr[i];
				$(item).addClass(newClass);
			});
		},
		createLayoutContainer: function ($container, layno, insertPos, insertLayno) {
			var elementType = "col-md-12";
			var targetElem = $($('#' + elementType).html());
			//获取工具按钮组（编辑、删除、拖动）
			gridlayout = $($("#gridlayout").html());
			gridlayout.find(".view").append(targetElem);
			targetElem = gridlayout;
			var dtStr = new Date().getTime();
			targetElem.addClass("layout_elem");
			targetElem.attr("id", elementType + "_" + dtStr);
			targetElem.find(".view>.row:first").addClass(elementType + "_" + dtStr);
			targetElem.find(".preview>input").attr("value", $(this).find('span').text());
			targetElem.attr("data-role", elementType);
			targetElem.attr("scope", "kendo,bootstrap");
			targetElem.attr("crtltype", "kendo");
			//替换id样式表
			targetElem.find('.id').addClass(targetElem.attr("id"));
			targetElem.find('.id').removeClass("id");
			//列添加工具栏
			targetElem.find(".column").prepend($("#colconfigTemplate").html());
			if (layno == undefined) {
				targetElem.addClass("layoutContainer");
			} else {
				targetElem.addClass("lay" + "_" + layno);
			}
			//增加自适用高度样式
			if ($("#autoHeightBt").hasClass("green-jungle")) {
				addAutoHeightClass(targetElem);
			}
			if (insertPos == undefined) {
				$container.append(targetElem);
			} else if (insertPos == "prepend") {
				$container.prepend(targetElem);
			} else if (insertPos == "before" && insertLayno != undefined) {
				if ($container.find(".lay" + "_" + insertLayno).length > 0) {
					$container.find(".lay" + "_" + insertLayno).before(targetElem);
				} else {
					$container.append(targetElem);
				}
			} else if (insertPos == "after" && insertLayno != undefined) {
				if ($container.find(".lay" + "_" + insertLayno).length > 0) {
					$container.find(".lay" + "_" + insertLayno).after(targetElem);
				} else {
					$container.append(targetElem);
				}
			}
			initContainer();
			changeElemStyle(targetElem);
			if (stopsave > 0)
				stopsave--;
			startdrag = 0;
			clashCompute();
			return targetElem;
		}
	};
	$.fn.layout = function (method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.layout');
			return this;
		}
	};
	//私有方法，检测参数是否合法
	function isValid(options) {
		return !options || (options && typeof options === "object") ? true : false;
	}
})(window.jQuery);

$(function () {
	$("#freelayoutModal").layout();
});