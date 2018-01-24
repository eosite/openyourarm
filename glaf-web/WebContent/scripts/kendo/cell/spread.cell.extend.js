(function($) {
	var plugin = "cell",
		optionKey = plugin + ".options";


	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			return p ? p[options].apply(p, Array.prototype.slice.call(
				arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options: $
						.extend(true, {}, $.fn[plugin].defaults, options),
					datas: param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};

	var __innerMethod__ = {
		"font-icon": function(cell, event) {
			if (event.type == "change") {
				var val = this.val() || "Calibri",
					font = cell.font(),
					fonts = font.split(" "),
					fontsLen = fonts.length;
				fonts[fontsLen - 1] = val;
				cell.font(fonts.join(" "));
			}
		},
		"fontsize-icon": function(cell, event) {
			if (event.type == "change") {
				var val = this.val() || 11,
					valPx = val / 0.75 + "px",
					font = cell.font(),
					fonts = font.split(" "),
					fontsLen = fonts.length;
				fonts[fontsLen - 2] = valPx;
				cell.font(fonts.join(" "));
			}
		},
		"textbold-icon": function(cell) {
			var font = cell.font(),
				fonts = font.split(" "),
				fontsLen = fonts.length,
				bold = "bold";
			switch (fontsLen) {
				case 4:
					fonts.remove(1);
					break;
				case 3:
					font.indexOf(bold) == -1 ? fonts.splice(1, 0, bold) : fonts.remove(0);
					break;
				case 2:
					(font.indexOf(bold) == -1) && fonts.splice(0, 0, bold);
					break;
				default:

			}
			cell.font(fonts.join(" "));
		},
		"textitalic-icon": function(cell) {
			var font = cell.font(),
				fonts = font.split(" "),
				fontsLen = fonts.length,
				bold = "italic";
			switch (fontsLen) {
				case 4:
					fonts.remove(0);
					break;
				case 3:
					font.indexOf(bold) == -1 ? fonts.splice(0, 0, bold) : fonts.remove(0);
					break;
				case 2:
					(font.indexOf(bold) == -1) && fonts.splice(0, 0, bold);
					break;
				default:

			}
			cell.font(fonts.join(" "));
		},
		"textunderline-icon": function(cell) {
			cell.textDecoration() == GcSpread.Sheets.TextDecorationType.Underline ? cell.textDecoration(GcSpread.Sheets.TextDecorationType.None) : cell.textDecoration(GcSpread.Sheets.TextDecorationType.Underline);
		},
		"justify-icon": function(cell) {
			cell.wordWrap() ? cell.wordWrap(false) : cell.wordWrap(true);
		},
		"horizontalleft-icon": function(cell) {
			cell.hAlign(0);
		},
		"horizontalcenter-icon": function(cell) {
			cell.hAlign(2);
		},
		"horizontalright-icon": function(cell) {
			cell.hAlign(1);
		},
		"verticaltop-icon": function(cell) {
			cell.vAlign(0);
		},
		"verticalcenter-icon": function(cell) {
			cell.vAlign(1);
		},
		"verticalbuttom-icon": function(cell) {
			cell.vAlign(2);
		},
		"setColor": function(sheet, tooltype, value) {
			var selections = sheet.getSelections(),
				$this = this,
				rgbcolor = value || $this.attr("rgbcolor");
			$.each(selections, function(index, area) {
				row = area.row;
				col = area.col;
				rowLen = area.rowCount;
				colLen = area.colCount;
				for (var i = 0; i < rowLen; i++) {
					for (var j = 0; j < colLen; j++) {
						cell = sheet.getCell(row + i, col + j);
						__innerMethod__[tooltype] && __innerMethod__[tooltype].call($this, cell, rgbcolor);
					}
				}
			});
		},
		"fillcolor-icon": function(cell, rgbcolor) {
			cell.backColor(rgbcolor);
		},
		"textcolor-icon": function(cell, rgbcolor) {
			cell.foreColor(rgbcolor);
		},
	}

	$.fn[plugin].methods = {

		_initEvents: function() {
			var that = this,
				$target = this.$target;
			$.each(that.options.events, function(event, fn) {
				$target.bind(event, function(e) {
					return fn.apply(that, Array.prototype.slice.call(arguments,
						1));
				});
			});
		},
		_trigger: function(event, params) {
			return this.$target.trigger(event, params);
		},
		_setPrintInfo: function() {
			var that = this,
				sheet = that.spread.getActiveSheet(),
				// 设置打印属性
				printInfo = sheet.printInfo();
			printInfo.showGridLine(false);
			printInfo.showRowHeader(false);
			printInfo.showColumnHeader(false);
			printInfo.showBorder(false);
			printInfo.footerCenter("&P/&N");
			printInfo.margin({
				"top": 0,
				"bottom": 25,
				"left": 0,
				"right": 0,
				"header": 0,
				"footer": 8
			});

			printInfo.bestFitColumns(false);

			printInfo.centering(GcSpread.Sheets.PrintCentering.Both);

			// printInfo.orientation(GcSpread.Sheets.PrintPageOrientation.Landscape);
			// 设置A4
			var a4 = new GcSpread.Sheets.PaperSize(GcSpread.Sheets.PaperKind.A4);
			a4._width += 22;
			printInfo.paperSize(a4);
			// 填充打印
			// printInfo.fitPagesTall(1);
			// printInfo.fitPagesWide(1);
			sheet.printInfo(printInfo);
		},
		init: function(cellId) {
			var that = this;
			$.ajax({
				url: that.url,
				data: {
					id: cellId
				},
				type: 'POST',
				dataType: 'JSON',
				success: function(ret) {
					if (ret && ret.tmpJson) {
						that.spread.fromJSON(JSON.parse(ret.tmpJson));
						if (that.options.reportModel) {

							var sheet = that.spread.getActiveSheet(),
								$combo = $("#" + that.$target.attr("id") + "_combo");
							// 隐藏选卡
							that.spread.tabStripVisible(false);
							// 隐藏网格线
							sheet.setGridlineOptions({
								showVerticalGridline: false,
								showHorizontalGridline: false
							});
							// 隐藏
							sheet.setColumnHeaderVisible(false);
							sheet.setRowHeaderVisible(false);

							function setZoom(sheet, value) {
								sheet.zoom(value / 100);
								sheet.isPaintSuspended(false);
								sheet.repaint();
							}
							var $rangeSlider = $combo.find('.spreadjs_rangeSlider'),
								$zoomval = $combo.find(".spreadjs_zoomval");
							$rangeSlider.ionRangeSlider({
								grid: false,
								min: 0,
								max: 400,
								postfix: "%",
								hide_min_max: true
							}).show();
							$rangeSlider.on("change", function() {
								var $this = $(this),
									value = $this.prop("value");
								$zoomval.text(value + "%");
								setZoom(sheet, value);
							});
							$combo.find(".spreadjs_zoomlink").click(function() {
								var zoomVal = $(this).attr("val");
								$zoomval.text(zoomVal + "%");
								$rangeSlider.val(zoomVal);
								$rangeSlider.data("ionRangeSlider").update({
									from: zoomVal
								});
								setZoom(sheet, zoomVal);
							});

							var spreadjs_cell_color = $combo.find('.spreadjs_cell_color');
							spreadjs_cell_color.on('click', '.spread_color_chunk', function(event) {
								var $this = $(this),
									tooltype = spreadjs_cell_color.attr("tooltype"),
									spread = that.spread,
									sheet = spread.getActiveSheet();
								__innerMethod__["setColor"].call($this, sheet, tooltype);
								spreadjs_cell_color.hide();
								event.preventDefault();
							});
							spreadjs_cell_color.find('input').minicolors({
								format: "rgb",
								hide: function(value, opacity) {
									var $this = $(this),
										spread = that.spread,
										tooltype = spreadjs_cell_color.attr("tooltype"),
										sheet = spread.getActiveSheet();
									__innerMethod__["setColor"].call($this, sheet, tooltype, $this.minicolors("rgbString"));
									spreadjs_cell_color.hide();
								}
							});
							// 注册点击
							$.each(['click', 'change'], function(index, val) {
								$("#" + that.$target.attr("id") + "_combo").on(val, '.spread_tools', function(event) {
									var $this = $(this),
										tooltype = $this.attr("tooltype"),
										spread = that.spread,
										sheet = spread.getActiveSheet(),
										selections = sheet.getSelections(),
										row, col, rowLen, colLen;
									switch (tooltype) {
										case "print-icon":
											spread.print();
											break;
										case "fillcolor-icon":
											var offset = $this.offset();
											spreadjs_cell_color.find('input').minicolors("value", "");
											spreadjs_cell_color.attr("tooltype", "fillcolor-icon").css({
												top: 0 /*
														 * offset.top +
														 * $this.width() + 10
														 */ ,
												left: offset.left
											}).show();
											break;
										case "textcolor-icon":
											var offset = $this.offset();
											spreadjs_cell_color.find('input').minicolors("value", "");
											spreadjs_cell_color.attr("tooltype", "textcolor-icon").css({
												top: 0 /*
														 * offset.top +
														 * $this.width() + 10
														 */ ,
												left: offset.left
											}).show();
											break;
										case "editundo-icon":
											GcSpread.Sheets.SpreadActions.undo.apply(sheet);
											break;
										case "editredo-icon":
											GcSpread.Sheets.SpreadActions.redo.apply(sheet);
											break;
										case "save-icon":
											var pageId = pageParameters.id;
											pageBtFunc.mtSave({
												"outid": pageId,
												"outpage": pageId,
												"outlev": window.pageLevel,
												"tlevel": window.pageLevel
											});
											break;
										case "first-icon":
											var $jq = that.$target;
											$jq.data("__page__", 1);
											pageCell.call($jq.data("spread"));
											break;
										case "prev-icon":
											var $jq = that.$target,
												__page__ = $jq.data("__page__") || 1;
											$jq.data("__page__", __page__ > 1 ? __page__ - 1 : 1);
											pageCell.call($jq.data("spread"));
											break;
										case "next-icon":
											var $jq = that.$target,
												__page__ = $jq.data("__page__") || 1;
											$jq.data("__page__", __page__ + 1);
											pageCell.call($jq.data("spread"));
											break;
										case "last-icon":
											var $jq = that.$target,
												__page__ = $jq.data("__total__") || 1;
											$jq.data("__page__", __page__);
											pageCell.call($jq.data("spread"));
											break;
										default:
											$.each(selections, function(index, area) {
												// {"row":2,"rowCount":3,"col":1,"colCount":3}
												row = area.row;
												col = area.col;
												rowLen = area.rowCount;
												colLen = area.colCount;
												for (var i = 0; i < rowLen; i++) {
													for (var j = 0; j < colLen; j++) {
														cell = sheet.getCell(row + i, col + j);
														__innerMethod__[tooltype] && __innerMethod__[tooltype].call($this, cell, event);
													}
												}
											});

									}
									event.preventDefault();
								});
							});
						}
						that._setPrintInfo.call(that);
						that._trigger("onShow", [that.spread]);
					}
				}
			});
		},
		getCell: function(id) {
			return $SC({
				id: id
			}, this.spread);
		}
	};

	$.fn[plugin].defaults = {
		events: {
			onShow: function(spread) {
				console.log(spread);
			}
		}
	};

	function Build(o) {

		this.$target = $(o);
		this.options = $.data(o, optionKey).options;

		this.spread = null;
		this.url = contextPath + "/mx/dep/report/depReportTemplate/getDepReportTemplate";

		(function(that) {
			that.spread = new GcSpread.Sheets.Spread(that.$target.get(0), {
				sheetCount: 3
			});



			that._initEvents();

			that.init(that.options.cellId);

			var events = {
				button: {
					click: GcSpread.Sheets.Events.ButtonClicked
				}
			};

			$.each(events, function(t, v) {
				for (var k in v) {
					that.spread.bind(v[k], function(e, args) {
						return $SC({
							id: args.row + "-" + args.col
						}, that.spread).trigger(k, e, args);
					});
				}
			});

		})(this);

	}

	Build.prototype = $.extend(true, {
		constructor: Build
	}, $.fn[plugin].methods);

})(jQuery);


/**
 * 
 */
window.getDictoryByCode = function(code, fn) {
	var url = contextPath + "/mx/form/defined/getDictByCode";
	$.ajax({
		url: url,
		type: 'post',
		data: {
			code: code
		},
		dataType: 'json',
		success: fn
	});
};
/**
 * 获取引用数据
 */
window.getQuoteDatas = function(data, fn) {
	var url = contextPath + "/mx/form/data/getQuoteDatas",
		vdata = {},
		params = {};
	// 处理quote参数
	if (data.dynamicId) { // 动态数据集
		vdata.dataSetId = data.dynamicId;
		if (data.params && data.params.length) {
			$.each(data.params, function(i, param) {
				if (param.value) {
					params[param.outid] = param.value;
				} else {
					var inval = getParam(data.id, param.inid);
					if (inval) {
						params[param.outid] = inval;
					}
				}
			})
			vdata.params = JSON.stringify(params);
		}
	}
	$.ajax({
		url: url,
		type: 'post',
		data: vdata,
		success: fn
	});
};

(function($) {

	function SpreadCell(rule, s) {
		this.id = null;
		this.rule = rule;
		this.spread = s;
		this.sheet = null;
		this.cell = null;
		this.cellType = null;

		(function(that) {
			that.sheet = that.spread.getActiveSheet();
			var id = that.id = that.rule.id;
			if (id && (id = id.split('-'))) {
				var c = that.cell = that.sheet.getCell(id[0], id[1]);
				that.cellType = that.sheet.getCellType(c.row, c.col,
					GcSpread.Sheets.SheetArea.viewport);
			}
		})(this);

	}

	SpreadCell.prototype.bind = function(type, fn) {
		var that = this,
			t = type + that.id;
		$(that.spread._host).bind(t, function(e) {
			return fn.apply(that.cell, //
				Array.prototype.slice.call(arguments, 1));
		});
		return that;
	};

	SpreadCell.prototype.unbind = function(type, fn) {
		var that = this,
			t = type + that.id;
		$(that.spread._host).unbind(t);
		return that;
	};

	SpreadCell.prototype.trigger = function(type, params) {
		$(this.spread._host).trigger(type + this.id, params);
		return this;
	};

	SpreadCell.prototype.getDictoryByCode = window.getDictoryByCode;
	SpreadCell.prototype.getQuoteDatas = window.getQuoteDatas;

	var SpreadCellsKey = "SpreadCells";

	window.$SC = function(o, c) {
		if (o && o.id) {
			if (o instanceof SpreadCell) {
				return o;
			} else {
				var _host = c._host,
					scs;
				!(scs = $.data(_host, SpreadCellsKey)) && $.data(_host, SpreadCellsKey, scs = {});
				!(scs[o.id]) && (scs[o.id] = new SpreadCell(o, c));
				return (scs[o.id]);
			}
		} else {
			return null;
		}
	};

})(jQuery);

function dataPrintSetting(rule) {
	var $spread = this,
		data_print = "data_print",
		printRule,
		printInfo = $spread.getActiveSheet().printInfo(),
		isUseMax = true;
	if (rule && (printRule = rule[data_print])) {
		$.each(printRule, function(i, v) {
			if (v.type == "print_area") {
				isUseMax = false;
				printInfo.rowStart(v.area.row);
				printInfo.rowEnd(v.area.row + v.area.rowCount - 1);
				printInfo.columnStart(v.area.col);
				if (v.area.colCount != 1) {
					printInfo.columnEnd(v.area.col + v.area.colCount - 1);
				}
			} else if (v.type == "print_repeat") {
				printInfo.repeatRowStart(v.area.row);
				printInfo.repeatRowEnd(v.area.row + v.area.rowCount - 1);
				/*
				 * printInfo.repeatColumnStart(v.area.col);
				 * printInfo.repeatColumnEnd(v.area.col+v.area.colCount-1);
				 */
			}
		})
	}
	// 设置为最大格打印 否则为打印内容
	isUseMax && printInfo.useMax(false);
	var printSet = "printSet",
		printSetRule;
	if (rule && (printSetRule = rule[printSet])) {
		var paper = new GcSpread.Sheets.PaperSize(GcSpread.Sheets.PaperKind[printSetRule["printSize"]]);
		paper._width += 22;
		printSetRule["printSize"] && printInfo.paperSize(paper);
		printInfo.zoomFactor(0.9);
	}
	// 设置打印对齐方式
	rule["printAlign"] !== undefined && printInfo.centering(parseInt(rule["printAlign"]));
}

function pageCell(rule) {
	var $spread = this,
		$target = $($spread._host),
		objId = $target.attr("id");
	$target.data("_rule") ? (rule = $target.data("_rule")) : $target.data("_rule", rule);
	var data_save_area = 'data_save_area',
		saveKey = "saveMsg";
	if (rule && rule[data_save_area]) {
		var $body = $("body"),
			objsaves = $body.data(data_save_area);
		!objsaves && ($body.data(data_save_area, objsaves = {}));
		// 更改为根据ID来存储
		var saves = objsaves[objId] || (objsaves[objId] = {});
		$.each(rule[data_save_area], function(i, msg) {
			msg.$spread = $spread;
			msg.__rule__ = rule;
			window.dynamicCellFunc(msg);
			if (msg.children) {
				$.each(msg.children, function(j, childMsg) {
					childMsg.$spread = $spread;
					childMsg.__rule__ = rule;
					window.dynamicCellFunc(childMsg);
					saves[childMsg.id] = childMsg;
				})
			}
			if (msg.id) { // 关联的数据集不能用来保存
				saves[msg.id] = msg;
			}
			window.dynamicCellDataSet(msg);
		});
		!$.data(document.body, saveKey) && ($.data(document.body, saveKey, []));
		$body.data(saveKey).length || $body.data(saveKey).push({
			save: cellPageSave
		});
	}
	// 打印设置
	dataPrintSetting.call(this, rule);
}

function a2b(fs, a, b) {
	if (fs && a && b) {
		$.each(fs, function(i, f) {
			b[f] = a[f];
		});
	}
}

/**
 * 根据单元格撑大区域
 * 
 * @param area
 * @param cell
 */
function getArea(area, cell) {
	if (!area.row && !area.col) {
		a2b(['row', 'col', 'colCount', 'rowCount'], cell, area);
	}
	var row = cell.row * 1,
		col = cell.col * 1,
		max = 100000,
		min = 0;
	area.minRow = Math.min(area.minRow || max, row);
	area.maxRow = Math.max(area.maxRow || min, row);
	area.minCol = Math.min(area.minCol || max, col);
	area.maxCol = Math.max(area.maxCol || min, col);
	area.colCount = (area.maxCol - area.minCol) || 1;
	area.RowCount = (area.maxRow - area.minRow) || 1;
}

/**
 * 变长区数据准备 1、复制规则 2、复制样式
 * 
 * @param spread
 * @param msg
 */
function dynamicCellFunc(msg) {
	var datas = [
			[]
		],
		$spread = msg.$spread,
		copys = [],
		$target = $($spread._host),
		dynamicAreaExt = $target.data("dynamicAreaExt") || {};
	var populate = function(count, t) {
		msg.columnModel = {}, area = msg.area;
		var split = "_0_",
			sc, cell, sheet;
		var tableName = msg.table.tableName,
			rule;
		$.each(msg.columns, function($i, column) {

			if (!column.fieldName) {
				return;
			}

			if (column.fieldName.indexOf(split) > -1) {
				column.field = column.fieldName;
				msg.custom = true; // 用户手动匹配数据集
				// 如果是手动选择的数据集
				msg.table._tableName = column.fieldName.split(split)[0];
			} else {
				column.field = (tableName ? (tableName + split) : "") + column.fieldName;
			}

			msg.columnModel[column.field] = column;
			sc = $SC($.extend(true, {}, column), $spread), cell = sc.cell;
			datas[0].push($.extend({}, column, sc));

			if (!msg.updateDataKey) {
				msg.updateDataKey = [];
				window.pushUpdateKey(msg, tableName || msg.table._tableName);
			}
			rule = sc.rule || {};

			if (!area) {
				if (!msg.area) {
					msg.area = {
						form: true,
						fenLan : !!msg.fenLan
					};
				}
				
				if(msg.area.fenLan){ // 分栏
					window.initAreaDatas(datas, column, msg);
				}
				
				cell.rowCount = cell.rowCount || 1;
				cell.colCount = cell.colCount || 1;
				window.getArea(msg.area, cell);
			}

			if (!count)
				return true;
			// 当不是多行变长时
			sheet = sc.sheet;	
			var row = parseInt(cell.row), //
				col = parseInt(cell.col),
				id;
			if(!msg.dynamicElongate){
				var i5 = window.getNextI(sc, t);
				// var i = window.getNextI(1, sc, t) || 1;
				for (var i = 1; i < count; i++) {

					if (i > 1) {
						if (i5 >= count) {
							break;
						}
						try {
							i5 += window.getNextI(sc, t);
						} catch (e) {
							i5 = 1;
						}
					} else {
						// i5 = 1;
					}

					id = row + '-' + (col + i5);
					// !!t && (id = (row + i) + '-' + col);
					if (t) { // 纵向变长
						id = (row + i5) + '-' + col;
					}
					rule.id = id;
					sc = $SC($.extend(true, {}, rule), $spread);

					var c = $.extend({}, column, sc);
					c.id = sc.cell.row + "-" + sc.cell.col;

					!datas[i] && (datas[i] = []);
					var data = datas[i];
					!data[$i] && (data[$i] = c);
					cell = sc.cell;
					if ($i == 0) {
						window.pushUpdateKey(msg, tableName);
						if (t) { // 纵向变长
							copys.push([row, col, row + i5, col, 1, count, t]);
						} else {
							copys.push([row, col, row, col + i5, count, 1]);
						}
					}
				}
			}else{
				// 动态变长行数
				/*
				 * var dynamicCount = t ? area.rowCount : area.colCount, i5 =
				 * dynamicCount, dyCount = 1; for (var i = 1; i <= dyCount; i++) {
				 * id = row + '-' + (col + i5); // !!t && (id = (row + i) + '-' +
				 * col); if (t) { //纵向变长 id = (row + i5) + '-' + col; } rule.id =
				 * id; sc = $SC($.extend(true, {}, rule), $spread);
				 * 
				 * var c = $.extend({}, column, sc); c.id = sc.cell.row + "-" +
				 * sc.cell.col;
				 * 
				 * !datas[i] && (datas[i] = []); var data = datas[i]; !data[$i] &&
				 * (data[$i] = c); cell = sc.cell; if ($i == 0) {
				 * window.pushUpdateKey(msg, tableName); } }
				 */
			}

		});
		/**
		 * 复制样式
		 */
		// window.execCopys(copys, msg.$spread);
		// debugger;
		if ((count == 1 || msg.dynamicElongate=="dynamic") && msg.type) {
			if (t) {
				// 上下变长 动态给后面2格增加事件 并且count为1的情况
				msg.isDynamic = "tb";
				area.dyType = "tb";
				dynamicAreaFunc.initEvent($spread, area);
			} else {
				// 左右变长
				msg.isDynamic = "lr";
				area.dyType = "lr";
				dynamicAreaFunc.initEvent($spread, area);
			}
		}
	};

	msg.whereClaus && (typeof msg.whereClaus === "string") && (msg.whereClaus = JSON.parse(msg.whereClaus));
	// debugger;
	if (msg.area && msg.type) {
		var tb = "bc-top-bottom",
			isTb = msg.type == tb,
			area = msg.area = (typeof msg.area === "string" ? JSON.parse(msg.area) : msg.area);
		msg.dynamicElongate && (msg.area.dynamicElongate = msg.dynamicElongate);
		!dynamicAreaExt[isTb ? area.row : area.col] && (dynamicAreaExt[isTb ? area.row : area.col] = {"scope":isTb ? area.rowCount : area.colCount});
		$target.data("dynamicAreaExt",dynamicAreaExt);
		if (isTb) { // 上下变长
			populate(area.rowCount, true);
		} else { // 左右变长
			populate(area.colCount);
		}
	} else {
		populate(null); // 表单形式
	}
	msg.datas = datas;
}

/**
 * 初始化分栏数据列
 * 
 * @param datas
 * @param c
 * @param dynamic_data
 * @returns
 */
function initAreaDatas(datas, column, msg){
	
	var dynamic_data = msg.__rule__.dynamic_data;
	
	if(!dynamic_data || !dynamic_data.length){
		return;
	}
	
	var c = column.id.split("-");
	
	c = {
		row : c[0]*1,
		col : c[1]*1
	};
	
	var area = null, data;
	for(var i = 0; i < dynamic_data.length; i ++){
		area = dynamic_data[i]["col-1"]["name"];
		if(cellInAreaStr(c, area)){
			break;
		}
		area = null;
	}
	
	if(!area){
		return;
	}
	
	var args = area.split(":");
	var min = args[0], max = args[1];
	
	var row = max.substring(1)*1 - 1,
	col = (max.substring(0, max.length - 1)).charCodeAt(0) - "A".charCodeAt(0);
	
	var area_ = {
		rowCount : row - c.row,
		colCount : col - c.col,
	};
	
	var index = 1;
	for(var rc = 0; rc <= area_.rowCount; rc ++){
		for(var cc = 0; cc <= area_.colCount; cc ++){
			var id = (c.row + rc) + "-" + (c.col + cc);
			column.id = id;
			var sc = $SC($.extend(true, {}, column)//
					, msg.$spread), cell = sc.cell;
			if(rc == 0 && cc == 0){
				continue;
			}
			!datas[index] && (datas[index] = []);
			datas[index].push($.extend({}, column, sc));
			
			window.pushUpdateKey(msg, msg.table.tableName || msg.table._tableName);
			
			index++;
		}
	}
}

/**
 * 判断格子是否在区域内
 * 
 * @param c
 * @param area
 * @returns
 */
function cellInAreaStr(c, area){
	// 6-7
	// G8:M12
	if(!area){
		return false;
	}
	var args = area.split(":");
	var min = args[0], max = args[1];
	
	var row = min.substring(1)*1 - 1,
	col = (min.substring(0, min.length - 1)).charCodeAt(0) - "A".charCodeAt(0) - 1;
	
	if(c.col < col || c.row < row){
		return false;
	}
	
	row = max.substring(1)*1 - 1,
	col = (max.substring(0, max.length - 1)).charCodeAt(0) - "A".charCodeAt(0) - 1;
	
	if(c.col > col || c.row > row){
		return false;
	}
	
	return true;
	
}

/**
 * 获取单元格步长（对象）
 * 
 * @returns
 */
function getCellStep(c) {
	var s = c.sheet;
	s.setSelection(c.row * 1, c.col * 1, 1, 1); // 先选中该单元格
	var selections = s.getSelections(); // 获取选中的单元格
	if (selections && selections.length) {
		return selections[0];
	}
}

/**
 * 获取下一个单元格下标(row或者col)
 * 
 * @returns
 */
function getNextI(sc, t) {
	var step = window.getCellStep(sc.cell),
		count = 1;
	if (!step)
		return count;
	if (t) { // 纵向变长
		count = step.rowCount;
	} else {
		count = step.colCount;
	}
	return count;
}

function execCopys(copys, s) {
	var sheet = s.getActiveSheet();
	$.each(copys, function(i, v) {
		if (v[6]) {
			v[5] = copys.length;
		} else {
			v[4] = copys.length;
		}
		sheet.copyTo(v[0], v[1], v[2], v[3], v[4], v[5], GcSpread.Sheets.CopyToOption.Style);
	});
}

/**
 * 修改字段key
 * 
 * @param msg
 * @param tableName
 */
function pushUpdateKey(msg, tableName) {
	var updates = null;
	if (msg.whereClaus) {
		updates = [];
		$.each(msg.whereClaus, function(i, w) {
			updates.push({
				dname: w.dname,
				field: (w.tablename || tableName) + "_0_" + w.dname
			});
		});
	} else {
		updates = [{
			dname: "id",
			field: tableName + "_0_id"
		}];
	}
	msg.updateDataKey.push(updates);
}

/**
 * 
 * @param msg
 * @param row
 * @param i
 */
function pushUpdateKeyData(msg, row, i) {
	var k = msg.updateDataKey[i];
	if (k && k.length) {
		$.each(k, function($i, v) {
			v.value = row[v.field];
		});
	}
}

/**
 * 变长区数据还原
 * 
 * @param spread
 * @param msg
 */
function dynamicCellDataSet(msg, parentTableName, parentRow) {
	if (msg.columns && msg.dataSetId) {
		var url = contextPath + '/mx/form/data/initPageBatch',
			isPage = (msg.type && !msg.isDynamic) || !!msg.forcePage,
			page = 1,
			pageSize = 5;
		// 是固定变长区才能使用分页功能
		var $target = $(msg.$spread._host),
			_rule = $target.data("_rule"),
			pageSet = _rule["data_pageset"],
			$spread = msg.$spread,
			activeSheet = $spread.getActiveSheet();
		if (isPage) {
			page = $target.data("__page__") || 1;
			pageSize = msg.type == "bc-top-bottom" ? msg.area.rowCount : msg.area.colCount;
			!!msg.forcePage && (pageSize = 1);
		}
		if (!activeSheet.isPaintSuspended()) {
			activeSheet.isPaintSuspended(true);
		}
		var params = $.extend(true, {}, window.getParams(), {
			dataSetId: msg.dataSetId,
			custom: msg.custom,
			isPage: isPage,
			page: page,
			pageSize: pageSize
		}, $target.data("__params__") || {});
		if (parentTableName && parentRow) {
			params.topId = parentRow[parentTableName + "_0_id"];
		}
		var datesetcount = "__datasetcount__";
		$target.data(datesetcount) == undefined && $target.data(datesetcount, 0);
		$target.data(datesetcount, $target.data(datesetcount) + 1);
		try {
			jQuery.post(url, params, function(ret) {
				$target.data(datesetcount, $target.data(datesetcount) - 1);
				if (isPage && pageSet) {
					var maxTotal = 1;
					$.each(pageSet, function(i, v) {
						if (v.type == "pageNo") {
							activeSheet.setValue(v.area.row, v.area.col, ret["page"]);
						} else if (v.type == "pageTotal") {
							var _total = Math.ceil(ret["total"] / pageSize) /*
																			 * parseInt(ret["total"] /
																			 * pageSize) +
																			 * 1
																			 */ ;
							activeSheet.setValue(v.area.row, v.area.col, _total);
							(_total - maxTotal > 0) && (maxTotal = _total);
						} else if (v.type == "total") {
							activeSheet.setValue(v.area.row, v.area.col, ret["total"]);
						}
					});
					$target.data("__total__", maxTotal);
				}
				// 填充数据
				window.fullCellData(msg, ret);
				if (msg.children && msg.children.length) {
					$.each(ret.rows, function(i, row) {
						$.each(msg.children, function(j, childMsg) {
							dynamicCellDataSet(childMsg, msg.table.tableName, row);
						});
					});
				}
				// 所有ajax 执行完毕执行
				if ($target.data(datesetcount) == 0) {
					// 执行自动行高
					var dynamicArea = $target.data("dynamicArea"),
						dynamicAreaType = $target.data("dynamicAreaType"),
						dynamicAreaExt = $target.data("dynamicAreaExt");
					var autoFit = _rule["autoFit"];
					if (autoFit && autoFit.length) {
						$.each(autoFit, function(index, fit) {
							var offsetCount = 0;
							if (fit.type == "autoRow") {
								if (dynamicAreaType && dynamicAreaType[fit.area.row] && (dynamicAreaType[fit.area.row] == "tb")) {
									offsetCount = dynamicArea[fit.area.row] * dynamicAreaExt[fit.area.row]["scope"];
								}
								for (var i = 0; i < fit.area.rowCount + offsetCount; i++) {
									activeSheet.autoFitRow(fit.area.row + i);
								}
							} else if (fit.type == "autoColumn") {
								if (dynamicAreaType && dynamicAreaType[fit.area.col] && (dynamicAreaType[fit.area.col] == "lr")) {
									offsetCount = dynamicArea[fit.area.col] * dynamicAreaExt[fit.area.row]["scope"];
								}
								for (var i = 0; i < fit.area.colCount + offsetCount; i++) {
									activeSheet.autoFitColumn(fit.area.col + i);
								}
							}
						});
					}
					// 执行合并
					var data_merge = _rule["data_merge"];
					if (data_merge && data_merge.length) {
						var merges = [];

						function canMerge(cell1, cell2) {
							return cell1 && cell2 && cell1.value() != "无" && (cell1.value() == cell2.value());
						}
						$.each(data_merge, function(index, merge) {
							var offsetCount = 0;
							if (merge.type == "top_buttom") {
								if (dynamicAreaType && dynamicAreaType[merge.area.row] && (dynamicAreaType[merge.area.row] == "tb")) {
									offsetCount = dynamicArea[merge.area.row] * dynamicAreaExt[merge.area.row]["scope"];
								}
								var startRow = merge.area.row,
									endRow = merge.area.row + merge.area.rowCount + offsetCount,
									startCol = merge.area.col,
									endCol = merge.area.col + merge.area.colCount,
									cell1,
									cell2;
								for (var j = startCol; j < endCol; j++) {
									var firstNum = null,
										lastNum = null,
										isMerge = false;
									for (var i = startRow; i < endRow; i++) {
										cell1 = activeSheet.getCell(i, j);
										cell2 = activeSheet.getCell(i + 1, j);
										if (canMerge(cell1, cell2)) {
											if (firstNum == null) {
												firstNum = i;
												lastNum = i + 1;
												isMerge = true;
											} else {
												lastNum += 1;
											}
										} else {
											if (firstNum != null && isMerge) {
												merges.push([firstNum, j, lastNum - firstNum + 1, 1]);
											}
											firstNum = null;
											lastNum = null;
											isMerge = false;
										}
									}
								}
							} else if (merge.type == "left_right") {
								if (dynamicAreaType[merge.area.col] && (dynamicAreaType[merge.area.col] == "lr")) {
									offsetCount = dynamicArea[merge.area.col] * dynamicAreaExt[merge.area.row]["scope"];
								}
								var startRow = merge.area.row,
									endRow = merge.area.row + merge.area.rowCount,
									startCol = merge.area.col,
									endCol = merge.area.col + merge.area.colCount + offsetCount,
									cell1,
									cell2;
								for (var i = startRow; i < endRow; i++) {
									var firstNum = null,
										lastNum = null,
										isMerge = false;
									for (var j = startCol; j < endCol; j++) {
										cell1 = activeSheet.getCell(i, j);
										cell2 = activeSheet.getCell(i, j + 1);
										if (canMerge(cell1, cell2)) {
											if (firstNum == null) {
												firstNum = j;
												lastNum = j + 1;
												isMerge = true;
											} else {
												lastNum += 1;
											}
										} else {
											if (firstNum != null && isMerge) {
												merges.push([i, firstNum, 1, lastNum - firstNum + 1]);
											}
											firstNum = null;
											lastNum = null;
											isMerge = false;
										}
									}
								}
							} else {
								var offsetTbCount = 0;
								if (dynamicAreaType[merge.area.row] && (dynamicAreaType[merge.area.row] == "tb")) {
									offsetTbCount = dynamicArea[merge.area.row] * dynamicAreaExt[merge.area.row]["scope"];
								}
								if (dynamicAreaType[merge.area.col] && (dynamicAreaType[merge.area.col] == "lr")) {
									offsetCount = dynamicArea[merge.area.col] * dynamicAreaExt[merge.area.row]["scope"];
								}
								// 优先上下合并
								var startRow = merge.area.row,
									endRow = merge.area.row + merge.area.rowCount + offsetTbCount,
									startCol = merge.area.col,
									endCol = merge.area.col + merge.area.colCount + offsetCount,
									cell1,
									cell2,
									rowCount = 1,
									colCount = 1;
								var firstRow = null,
									firstCol = null,
									mergeCell = null,
									crossCol = 1;
								for (var j = startCol; j < endCol; j += colCount) {
									if (firstRow != null) {
										crossCol++;
									}
									for (var i = startRow; i < endRow; i++) {
										cell1 = activeSheet.getCell(i, j);
										cell2 = activeSheet.getCell(i, j + 1);
										if (canMerge(cell1, cell2)) {
											mergeCell = cell1;
											rowCount++;
											if (firstRow == null) {
												firstRow = i;
												firstCol = j;
											}
										} else {
											if (mergeCell != null) {
												// 判断左右是否合并
												colCount = 1;
												h1: for (var jj = j + 1; jj < endCol; jj++) {
													h2: for (var ii = startRow; ii < startRow + rowCount; ii++) {
														var cell3 = activeSheet.getCell(ii, jj);
														if (!canMerge(mergeCell, cell3)) {
															break h1;
														}
													}
													colCount++;
												}
												merges.push([firstRow, firstCol, crossCol > 1 ? rowCount / (crossCol) : rowCount, colCount + crossCol - 1]);
												crossCol = 1;
											}

											firstRow = null;
											firstCol = null;
											rowCount = 1;
											mergeCell = null;
										}
									}
								}
							}
						});
						// activeSheet.addSpan(sel.row, sel.col, sel.rowCount,
						// sel.colCount);
						if (merges.length) {
							$.each(merges, function(i, m) {
								var span1 = activeSheet.getSpan(m[0], m[1]);
								if (span1 != null) {
									m[2] += span1.rowCount - 1;
									m[3] += span1.colCount - 1;
								}
								var span2 = activeSheet.getSpan(m[0] + m[2] - 1, m[1] + m[3] - 1);
								if (span1 == null && span2 != null) {
									m[2] += span2.rowCount - 1;
									m[3] += span2.colCount - 1;
								}
								activeSheet.addSpan.apply(activeSheet, m);
							})
						}
					}
					activeSheet.isPaintSuspended(false);
					activeSheet.repaint();
				}

				var printRule = null;
				// 如果存在打印设置，重新计算上动态列的信息
				if(_rule && (printRule = _rule["data_print"])){
					// 执行自动行高
					var dynamicArea = $target.data("dynamicArea"),
						dynamicAreaType = $target.data("dynamicAreaType"),
						dynamicAreaExt = $target.data("dynamicAreaExt");
					var printInfo = activeSheet.printInfo();
					$.each(printRule, function(i, v) {
						if (v.type == "print_area") {
							var offsetCount = 0;
							for(var i = 0 ; i < v.area.rowCount ; i ++ ){
								var cellRow = v.area.row + i;
								if (dynamicAreaType && dynamicAreaType[cellRow] && (dynamicAreaType[cellRow] == "tb")) {
									offsetCount += dynamicArea[cellRow] * dynamicAreaExt[cellRow]["scope"];
								}
							}
							printInfo.rowStart(v.area.row);
							printInfo.rowEnd(v.area.row + v.area.rowCount + offsetCount - 1);
						}
					});
				}
				

			}, 'JSON').error(function(e) {
				$target.data(datesetcount, $target.data(datesetcount) - 1);
				console.log(e);
				activeSheet.isPaintSuspended(false);
				activeSheet.repaint();
			});
		} catch (e) {
			console.log(e);
			activeSheet.isPaintSuspended(false);
			activeSheet.repaint();
		}
	}
}

/**
 * 但表单形式填充
 * 
 * @param msg
 * @param data
 * @returns
 */
function fullCellDataForm(datas) {
	if (datas && datas.length) {
		$.each(datas, function(i, data) {
			$.each(data, function(i0, c) {
				c.cell.value(c.__value);
			});
		});
	}
	return true;
}

/**
 * cell 表格数据填充
 * 
 * @param msg
 * @param data
 * @returns {Boolean}
 */
function fullCellData(msg, data) {
	debugger;
	var area = msg.area;

	// 横向变长
	var size = data.rows.length,
		len = msg.datas.length;
	var arr = [],
		sameRow = {},
		isTb,
		sheet = msg.$spread.getActiveSheet();

	if (msg.isDynamic) { // 动态变长处理
		isTb = !(msg.type == "bc-left-right");
		var $target = $(msg.$spread._host),
			dynamicArea = $target.data("dynamicArea"),
			dynamicAreaExt = $target.data("dynamicAreaExt"),
			// 变长的条数
			dynamicCount = dynamicArea && (isTb ? dynamicArea[msg.area.row] : dynamicArea[msg.area.col]) || 0,
			extObj;
		
		 
		// 如果有要清空原来的数据
		if (dynamicCount > 0) {
			// 获取重新计算后的区域范围
			var calculateArea = dynamicAreaFunc.calculateArea($target, msg.area);

			for (var i = dynamicCount; i >= 1; i--) {
				dynamicAreaFunc.delFunc.call({
					sheet: sheet,
					col: isTb ? calculateArea.col : calculateArea.col + (calculateArea.dynamicElongate?i*calculateArea.colCount:i),
					row: isTb ? calculateArea.row + (calculateArea.dynamicElongate?i*calculateArea.rowCount:i) : calculateArea.row
				}, msg.area);
			}
			if (isTb) {
				delete dynamicArea[msg.area.row];
			} else {
				delete dynamicArea[msg.area.col];
			}
		}
		for (var i = 0; i < (size || 1); i++) {
			if (i == 0) {
				// 给第一行初始化事件
				dynamicAreaFunc.initEvent(msg.$spread, msg.area);
			}
			if (i >= len) {
				if (!msg.updateDataKey[i]) {
					// 增加行
					dynamicAreaFunc.addFunc.call($SC({
						id: dynamicAreaFunc.nextRow((msg.area.row + (isTb ?0:(msg.area.rowCount - 2))) + "-" + (msg.area.col + (isTb ?(msg.area.colCount-2):0)), i - 1, isTb, msg.area, $(msg.$spread._host))
					}, msg.$spread).cell, msg.area);
					// 给key主键扩展
					msg.updateDataKey[i] = $.extend(true, [], msg.updateDataKey[0]);
				}
			}
			var row = data.rows[i];
			$.each(msg.datas[0], function($i, k) {
				var c = /* i > 0 ? */ $SC($.extend(true, k.rule, {
					id: dynamicAreaFunc.nextRow(k.id, i, isTb, msg.area, $(msg.$spread._host))
				}), msg.$spread) /* : k */ ;
				a2b(['field', 'fieldName'], k, c);
				!sameRow[c.cell.row] && (sameRow[c.cell.row] = {}); // 同一行的数据放同一数组里(行专列)
				k.cell = c.cell;
				k.__value = c.__value = row[c.field];
				sameRow[c.cell.row][c.cell.col] = c.__value;
			});
			window.pushUpdateKeyData(msg, row, i);
		}
	} else {
		// 因为要用分页 所以遍历是根据固定变长长度来遍历
		for (var i = 0; i < len; i++) {
			/*
			 * if (i >= size) // 不得超出变长区范围 break;
			 */
			var row = i < size ? data.rows[i] : {};
			$.each(msg.datas[i], function($i, k) {
				if (k) {
					var c = k;
					if (area.form) {
						c = $SC($.extend(true, k.rule, {
							id: dynamicAreaFunc.calculate(msg.$spread.getActiveSheet(), k.id),
						}), msg.$spread);
						a2b(['field', 'fieldName'], k, c);
					}
					k.cell = c.cell;
					k.__value = c.__value = row[c.field] == undefined ? "" : row[c.field];
					!sameRow[c.cell.row] && (sameRow[c.cell.row] = {}); // 同一行的数据放同一数组里(行专列)
					sameRow[c.cell.row][c.cell.col] = c.__value;
				}
			});
			window.pushUpdateKeyData(msg, row, i);
		}
	}



	if (area.form) {
		return fullCellDataForm(msg.datas);
	}

	function getMaxCol(row) {
		var max = 0;
		for (var p in row) {
			if (p - max > 0) {
				max = p;
			}
		}
		return max-0;
	}

	var col = area.col * 1,
		colCount = area.colCount + col,
		maxCol;
	// 如果是动态多行变长不能使用array填充模式
	if(msg.area.dynamicElongate){
		var dysc,row;
		for (var dyRow in sameRow) {
			row = sameRow[dyRow];
			for(var dyCol in row){
				dysc = $SC({id: dyRow+"-"+dyCol},msg.$spread);
				dysc.cell.value(row[dyCol]||'');
			}
		}
	}else{
		for (var k in sameRow) {
			var row = sameRow[k],
				data = [],
				val;
			if (row) {
				if (!maxCol) {
					maxCol = getMaxCol(row);
				}
				for (var i = col; i <= (maxCol || colCount); i++) {
					val = row[i];
					if (!val) {
						// var sc = $SC({
						// id: k + "-" + i
						// }, msg.$spread);
						// val = sc.cell.value();
					}
					data.push(val);
				}
				/*
				 * for (var p in row) { val = row[p]; data.push(val); }
				 */
				arr.push(data);
				window.fullEmptyRow(k * 1 /* + 1 */, sameRow, arr, colCount);
			}
		}
		sameRow = null;
		if (arr.length) {
			msg.$spread.getActiveSheet().setArray(dynamicAreaFunc.calculateArea($(msg.$spread._host), msg.area).row * 1, col, arr);
		}
	}
	return true;
}

/**
 * 每个单元格是合并的，需要填充空数据 补齐空行
 * 
 * @returns
 */
function fullEmptyRow(i, sameRow, arr, colCount) {
	if (!sameRow[i + ""]) {
		var data = [];
		for (var i = 0; i <= colCount; i++) {
			data[i] = null;
		}
		arr.push(data);
	}
}


/**
 * 保存
 */
function cellPageSave(wdataSetId) {

	var fn = null;
	if (wdataSetId && $.isFunction(wdataSetId)) {
		fn = wdataSetId;
	}

	var $body = $("body"),
		objsaves = $body.data('data_save_area'),
		saves;
	for (var id in objsaves) {
		saves = objsaves[id];
		if (saves && !isEmptyObject(saves)) {
			var saveMsg = [],
				update; // 保存数据项
			var populate = function(msg) {
				var wdataSet = {
						id: msg.id,
						datas: [],
						table: msg.table,
						dels: msg.dels
					},
					validate = true;
				if (msg.datas && msg.datas.length) {
					var i, ii, data, c;
					if (msg.isDynamic) { // 动态变长获取
						var $target = $(msg.$spread._host),
							dynamicArea = $target.data("dynamicArea"),
							isTb = msg.area.dyType == "tb",
							dynamicCount = (dynamicArea ? (dynamicArea[isTb ? msg.area.row : msg.area.col] || 0) : 0) + 1;
						h1: for (i = 0; i < dynamicCount; i++) {
							data = msg.datas[0];
							var json = {},
								isOk = false,
								value;
							if (update = msg.updateDataKey[i] || (function() {
									var k = $.extend(true, [], msg.updateDataKey[0]);
									k[0].value = null;
									msg.updateDataKey[i] = k;
									return k;
								})()) {
								$.each(update, function() {
									json[this.dname] = this.value;
									this.value && (isOk = true);
								});
							}

							if (data) {
								h2: for (ii = 0; ii < data.length; ii++) {
									c = i > 0 ? $SC($.extend(true, data[ii].rule, {
										id: dynamicAreaFunc.nextRow(data[ii].id, i, isTb, msg.area, $target)
									}), msg.$spread) : data[ii];
									a2b(['field', 'fieldName'], data[ii], c);
									if (validate = window.cellValidate(c)) {
										(value = c.cell.value()) && (isOk = true);
										json[c.fieldName] = value;
									} else {
										saveMsg = [];
										break h1;
									}
								}
								if (isOk) {
									wdataSet.datas.push(json);
								}
							}
						}
					} else {
						h1: for (i = 0; i < msg.datas.length; i++) {
							data = msg.datas[i];

							var json = {},
								isOk = false,
								value;
							if (update = msg.updateDataKey[i]) {
								$.each(update, function() {
									json[this.dname] = this.value;
									this.value && (isOk = true);
								});
							}

							if (data) {
								h2: for (ii = 0; ii < data.length; ii++) {
									// c = data[ii];
									c = $SC($.extend(true, data[ii].rule, {
										id: dynamicAreaFunc.calculate(data[ii].sheet, data[ii].id)
									}), msg.$spread);
									a2b(['field', 'fieldName'], data[ii], c);

									if (validate = window.cellValidate(c)) {
										(value = c.cell.value()) && (isOk = true);
										json[c.fieldName] = value;
									} else {
										saveMsg = [];
										break h1;
									}
								}
								if (isOk) {
									wdataSet.datas.push(json);
								}
							}
						}
					}
				}
				if (validate && wdataSet.datas.length) {
					saveMsg.push(wdataSet);
				}
			};
			// $.transformToTreeFormat = function(a, idStr, pidStr, childrenStr)
			var relation = null,
				map = {};
			if (wdataSetId && !fn) { // 单独保存
				populate(saves[wdataSetId]);
			} else { // 集体保存
				relation = [];
				$.each(saves, function(id, msg) {
					populate(msg);
					if (msg.table && !map[msg.table.id]) {
						msg.table && (relation.push($.extend(true, {}, msg.table)));
						map[msg.table.id] = msg.table;
					}
				});
				if (relation.length > 1) {
					relation = $.transformToTreeFormat(relation, "id", "topId", "children");
				} else {
					relation = null;
				}
			}
			if (saveMsg.length) {
				$.ajax({
					url: contextPath + '/mx/form/data/saveFormData',
					type: 'POST',
					data: {
						relation: relation ? JSON.stringify(relation) : relation,
						tableMsg: JSON.stringify(saveMsg)
					},
					dataType: 'JSON',
					success: function(ret) {
						if (ret && ret.message) {
							!fn && alert(ret.message);
							$.each(saves, function(i, msg) {
								if (wdataSetId && msg.id !== wdataSetId) {
									return false;
								}
								window.dynamicCellDataSet(msg);
							});
							fn && fn(ret);
						}
					}
				});
			} else {

			}
		} else {
			alert("未设置保存操作!");
		}
	}
}

/**
 * 单元格验证 需要集成
 * 
 * @returns {Boolean}
 */
function cellValidate(c) { // TODO

	var rule = c.rule;
	if (rule) {
		/*
		 * A001-3-001: "" A001-3-002: "" A001-3-003: "" A001-3-004: "false"
		 * A001-3-005: "" A001-3-006: "false" A001-3-007: "false" A001-3-008:
		 * "false"
		 */

		initCellProperties(c); // 默认值
		var tmp, cell = c.cell,
			val = cell.value(),
			pos = //
			window.getCellPositionStr(cell.row, cell.col, c.sheet);
		if (tmp = rule["A001-3-004"]) { // 验证不为空
			if (tmp === "true") {
				if (!cell.value()) {
					alert(pos + " 不能为空!");
					return false;
				}
			}
		}

		if ((tmp = rule["A001-3-001"]) && val) { // 验证数据类型
			var fn = validateMethods[tmp];
			if (fn) {
				if (!fn(val)) {
					alert(pos + " 输入格式错误!");
					return false;
				}
			}
		}



	}

	return true;
}

var validateMethods = {
	// 邮件
	email: function(value) {
		return /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value);
	},

	// URL
	url: function(value) {
		return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(value);
	},

	// 日期
	date: function(value) {
		return !/Invalid|NaN/.test(new Date(value).toString());
	},

	datetime: function(value) {
		return validateMethods.date(value);
	},

	// ISO日期
	dateISO: function(value) {
		return /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/.test(value);
	},

	// 数值
	number: function(value) {
		return /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
	},
	// 数字
	digits: function(value) {
		return /^\d+$/.test(value);
	},
	// 信用卡号
	creditcard: function(value) {
		if (/[^0-9 \-]+/.test(value)) {
			return false;
		}
		var nCheck = 0,
			nDigit = 0,
			bEven = false,
			n, cDigit;

		value = value.replace(/\D/g, "");
		if (value.length < 13 || value.length > 19) {
			return false;
		}

		for (n = value.length - 1; n >= 0; n--) {
			cDigit = value.charAt(n);
			nDigit = parseInt(cDigit, 10);
			if (bEven) {
				if ((nDigit *= 2) > 9) {
					nDigit -= 9;
				}
			}
			nCheck += nDigit;
			bEven = !bEven;
		}
		return (nCheck % 10) === 0;
	}
};

/**
 * 初始化表格属性(默认值...)
 * 
 * @param c
 */
function initCellProperties(sc) {
	var rule = sc.rule;
	if (rule) {
		var tmp;
		if (tmp = rule["A001-3-005"]) { // 默认值
			if (!sc.cell.value())
				sc.cell.value(tmp);
		}
	}
}

function cellInArea(cell, area) {
	if (cell && area) {
		if (area.row <= cell.row && area.col <= cell.col && (area.row + area.rowCount - 1 >= cell.row) && (area.col + area.colCount - 1 >= cell.col)) {
			return true;
		}
	}
	return false;
}


/**
 * 获取单元格位置字符串，如E10
 * 
 * @param {[type]}
 *            row [description]
 * @param {[type]}
 *            col [description]
 * @return {[type]} [description]
 */
function getCellPositionStr(rowNum, colNum, sheet) {
	var pos = '',
		v,
		row = rowNum * 1 + 1,
		col = colNum * 1 + 1;
	switch (sheet.referenceStyle()) {
		case 0:
			while (col > 0)
				v = col % 26, v == 0 ? (pos = "Z" + pos, col--) : pos = String.fromCharCode("A".charCodeAt(0) + v - 1) + pos, //
				col = parseInt((col / 26).toString());
			pos += row.toString();
			break;
		case 1:
			pos = "R" + row.toString() + "C" + col.toString();
			break;
		default:
			break;
	}

	return pos;
}

$.transformToTreeFormat = function(a, idStr, pidStr, childrenStr) {
	var r = [],
		hash = {},
		id = idStr,
		pid = pidStr,
		children = childrenStr,
		i = 0,
		j = 0,
		len = a.length;
	for (; i < len; i++) {
		hash[a[i][id]] = a[i];
	}
	for (; j < len; j++) {
		var aVal = a[j],
			hashVP = hash[aVal[pid]];
		if (hashVP) {
			!hashVP[children] && (hashVP[children] = []);
			hashVP[children].push(aVal);
		} else {
			r.push(aVal);
		}
	}
	return r;
};
/**
 * 动态变长区域方法
 * 
 */
var dynamicAreaFunc = {
	initEvent: function($spread, sarea) {
		var lastId, // 删除按钮
			secondLastId,// 添加按钮
			area = dynamicAreaFunc.calculateArea($($spread._host), sarea);
		if (area.dyType == "tb") { // 上下动态变长
			// 删除
			lastId = (area.row /* + area.rowCount - 1 */) + "-" + (area.col + area.colCount - 1);
			// 添加
			secondLastId = (area.row /* + area.rowCount - 1 */) + "-" + (area.col + area.colCount - 2);
		} else { // 左右动态变长
			lastId = (area.row + area.rowCount - 1) + "-" + (area.col /*
																		 * +
																		 * area.colCount -
																		 * 1
																		 */);
			secondLastId = (area.row + area.rowCount - 2) + "-" + (area.col /*
																			 * +
																			 * area.colCount -
																			 * 1
																			 */);
		}
		var lastCell = $SC({
				id: lastId
			}, $spread),
			secondLastCell = $SC({
				id: secondLastId
			}, $spread);
		lastCell.cell.watermark(JSON.stringify({
			"area": sarea,
			"type": "del"
		}));
		secondLastCell.cell.watermark(JSON.stringify({
			"area": sarea,
			"type": "add"
		}));
	},
	// 添加行功能
	addFunc: function(sarea) {
		var sheet = this.sheet,
			$target = $(sheet.parent._host),
			area = dynamicAreaFunc.calculateArea($(sheet._getHost()), sarea),
			isTb = area.dyType == "tb", // 动态上下变长
			col = parseInt(this.col),
			row = parseInt(this.row),
			rowCount = sarea.dynamicElongate ? sarea.rowCount : 1,
			colCount = sarea.dynamicElongate ? sarea.colCount : 1,
			toCol = isTb ? col : col + colCount/* 1 */,
			toRow = isTb ? row + rowCount/* 1 */ : row;
		if (isTb) {
			sheet.addRows(toRow, /* 1 */rowCount);
			sheet.setRowHeight(toRow, sheet.getRowHeight(row, GcSpread.Sheets.SheetArea.viewport), GcSpread.Sheets.SheetArea.viewport);
		} else {
			sheet.addColumns(toCol, /* 1 */colCount);
			sheet.setColumnWidth(toCol, sheet.getColumnWidth(col, GcSpread.Sheets.SheetArea.viewport), GcSpread.Sheets.SheetArea.viewport);
		}
		sheet.copyTo(isTb ? row : 0, isTb ? 0 : col, isTb ? toRow : 0, isTb ? 0 : toCol, isTb ? rowCount/* 1 */ : sheet.getRowCount() - 2, isTb ? sheet.getColumnCount() - 2 : colCount /* 1 */, GcSpread.Sheets.CopyToOption.All);
		// 如果是多行的 只清理输入值
		if(sarea.dynamicElongate){
			dynamicAreaFunc.clearDynamicElongate.call($target,sarea,isTb?(toRow-sarea.row)/rowCount:(toCol-sarea.col)/colCount);
		}else{
			sheet.clear(isTb ? toRow : 0, isTb ? 0 : toCol, isTb ? 1 : sheet.getRowCount() - 2, isTb ? sheet.getColumnCount() - 2 : colCount/* 1 */, GcSpread.Sheets.SheetArea.viewport, GcSpread.Sheets.StorageType.Data);
		}
		// 还原公式
		sheet.copyTo(isTb ? row : 0, isTb ? 0 : col, isTb ? toRow : 0, isTb ? 0 : toCol, isTb ? rowCount/* 1 */ : sheet.getRowCount() - 2, isTb ? sheet.getColumnCount() - 2 : colCount /* 1 */, GcSpread.Sheets.CopyToOption.Formula);
		// sheet.copyTo(isTb?row:area.row, isTb?area.col:col,
		// isTb?toRow:area.row, isTb?area.col:toCol, area.rowCount,
		// area.colCount,
		// GcSpread.Sheets.CopyToOption.All/*GcSpread.Sheets.CopyToOption.Style*/);
		// 清空数据
		// sheet.clear(isTb?toRow:area.row,isTb?area.col:toCol,area.rowCount,
		// area.colCount,GcSpread.Sheets.SheetArea.viewport,GcSpread.Sheets.StorageType.Data);
		// 添加水印 用来做事件保存
		sheet.getCell(toRow, toCol).watermark(JSON.stringify({
			"area": sarea,
			"type": "add"
		}));
		sheet.getCell(isTb ? toRow : toRow + 1, isTb ? toCol + 1 : toCol).watermark(JSON.stringify({
			"area": sarea,
			"type": "del"
		}));
		// 计算增加后的动态区域
		var dynamicArea = $target.data("dynamicArea") || {},
			dynamicAreaType = $target.data("dynamicAreaType") || {},
			dynamicAreaExt = $target.data("dynamicAreaExt") || {} ,
			dyScope = dynamicAreaExt[isTb ? area.row : area.col]["scope"],
			areaObj = dynamicArea[isTb ? area.row : area.col] || 0;
		// 动态区域的类型
		dynamicAreaType[isTb ? area.row : area.col] = area.dyType;
		$target.data("dynamicAreaType", dynamicAreaType);
		dynamicArea[isTb ? area.row : area.col] = ++areaObj;
		$target.data("dynamicArea", dynamicArea);


		dynamicAreaFunc.handleDataKey.call($target, area, isTb ? (row - area.row)/(area.dynamicElongate?dyScope:1) : (col - area.col)/(area.dynamicElongate?dyScope:1), "add");
	},
	// 删除行功能
	delFunc: function(area) {
		var sheet = this.sheet,
			isTb = area.dyType == "tb", // 动态上下变长
			col = parseInt(this.col),
			row = parseInt(this.row),
			$target = $(sheet.parent._host),
			rowCount = area.dynamicElongate ? area.rowCount : 1,
			colCount = area.dynamicElongate ? area.colCount : 1,
			dynamicArea = $target.data("dynamicArea") || {},
			dynamicAreaExt = $target.data("dynamicAreaExt") || {} ,
			dyScope = dynamicAreaExt[isTb ? area.row : area.col]["scope"],
			areaObj = dynamicArea[isTb ? area.row : area.col] || 0;
		if (isTb) {
			sheet.deleteRows(row, rowCount/* 1 */);
		} else {
			sheet.deleteColumns(col, colCount/* 1 */);
		}
		dynamicArea[isTb ? area.row : area.col] = --areaObj;
		$target.data("dynamicArea", dynamicArea);
		dynamicAreaFunc.handleDataKey.call($target, area, isTb ? (row - area.row)/(area.dynamicElongate?dyScope:1) : (col - area.col)/(area.dynamicElongate?dyScope:1), "del");
		// 同时删除 msg.updateDataKey 中的记录
	},
	// 计算多个变长后的变长区
	calculateArea: function($target, sarea) {
		var dynamicArea = $target.data("dynamicArea"),
			dynamicAreaType = $target.data("dynamicAreaType"),
			dynamicAreaExt = $target.data("dynamicAreaExt"),
			area = $.extend({}, sarea),
			isTb = area.dyType == "tb",
			dArea = {};
		if (dynamicAreaType && dynamicArea) {
			for (var k in dynamicAreaType) {
				if (dynamicAreaType[k] == "tb" && isTb) { // 上下变长
					if (k < area.row) {
						dArea[k] = dynamicArea[k];
					}
				} else if (dynamicAreaType[k] != "tb" && !isTb) {
					if (k < area.col) {
						dArea[k] = dynamicArea[k];
					}
				}
			}
			if (isTb) {
				for (var k in dArea) {
					area.row += dArea[k] * (dynamicAreaExt[k]["scope"] || 1);
				}
			} else {
				for (var k in dArea) {
					area.col += dArea[k] * (dynamicAreaExt[k]["scope"] || 1);
				}
			}
			return area;
		}
		return area;
	},
	// 动态增长后补偿算法
	calculate: function(sheet, info, isEvent) {
		var $target = $(sheet.parent._host),
			dynamicArea = $target.data("dynamicArea"),
			dynamicAreaType = $target.data("dynamicAreaType"),
			// 动态变长扩展属性 {1:{scope:2}}
			dynamicAreaExt = $target.data("dynamicAreaExt"),
			infos = info.split("-"),
			row = infos[0],
			col = infos[1],
			newCol = parseInt(col),
			newRow = parseInt(row),
			isTb = true;
		if (dynamicArea) {
			var dext = null;
			for (var key in dynamicArea) {
				dext = dynamicAreaExt[key];
				if (dynamicAreaType[key] == "tb") {
					if (row - key > 0) {
						if (isEvent) {
							newRow -= dynamicArea[key] * (dext["scope"]||1);
						} else {
							newRow += dynamicArea[key] * (dext["scope"]||1);
						}
					}
				} else {
					if (col - key > 0) {
						isTb = false;
						if (isEvent) {
							newCol -= dynamicArea[key] * (dext["scope"]||1);
						} else {
							newCol += dynamicArea[key] * (dext["scope"]||1);
						}
					}
				}
			}
			return isTb ? (newRow + "-" + col) : (row + "-" + newCol);
		} else {
			return info;
		}
	},
	// 用来判断是否在动态变长区域内
	isInArea: function(dynamicAreaType, dynamicArea, row, col) {
		var fixArea = {},
			keyTbAry = [], // 上下动态变长
			keyLfAry = []; // 左右动态变长
		for (var key in dynamicArea) {
			if (dynamicAreaType[key] == "tb") {
				keyTbAry.push(key);
			} else {
				keyLfAry.push(key);
			}
		}
		// 排序
		keyTbAry = keyTbAry.sort(function(i, j) {
			return i - j > 0
		});
		keyLfAry = keyLfAry.sort(function(i, j) {
			return i - j > 0
		});
		// 计算动态变长后的结果
		$.each(keyTbAry, function(i, v) {
			fixArea[v] = {
				start: v,
				end: dynamicArea[v]
			}
		});
	},
	/**
	 * 获取下一个坐标
	 * 
	 * @param {[type]}
	 *            info 坐标 7-5
	 * @param {[type]}
	 *            i 第几条记录
	 * @param {Boolean}
	 *            isTb true为上下变长 false 为左右变长
	 * @param {[type]}
	 *            sarea 原选择区域 source area
	 * @param {[type]}
	 *            $target dom节点
	 * @return {[type]} [description]
	 */
	nextRow: function(info, i, isTb, sarea, $target) {
		var area = dynamicAreaFunc.calculateArea($target, sarea),
			ccount = isTb ? area.row - sarea.row : area.col - sarea.col,
			dynamicAreaExt = $target.data('dynamicAreaExt'),
			scope = dynamicAreaExt[isTb ? sarea.row : sarea.col]["scope"],
			infos = info.split("-");
		if (isTb) {
			return (parseInt(infos[0]) + (i*scope) + ccount) + "-" + infos[1];
		} else {
			return infos[0] + "-" + (parseInt(infos[1]) + (i*scope) + ccount);
		}

	},
	/**
	 * 如果是多行动态 那么只清除当前复制行内的输入框的内容
	 */
	clearDynamicElongate: function(area,index){
		var $target = this,
			rule = $target.data("_rule"),
			data_save_area = "data_save_area",
			pMsg = $("body").data(data_save_area)[$target.attr('id')],
			msg = isEmptyObject(pMsg) ? rule[data_save_area] : pMsg,
			isTb = area.dyType == "tb", // 动态上下变长
			obj, areaObj,sc;
		for (var key in msg) {
			obj = msg[key];
			if (obj.isDynamic && obj.dynamicElongate) {
				areaObj = obj["area"];
				if(areaObj.col == area.col && areaObj.row == area.row) {
					$.each(obj.columns,function(i,col){
						sc = $SC({id: dynamicAreaFunc.nextRow(col.id, index, isTb, obj.area,$target)},obj.$spread);
						sc.cell.value("");
					});
				}
			}
		}
	},
	/*
	 * 更新主键
	 */
	handleDataKey: function(area, index, type) {
		var $target = this,
			rule = $target.data("_rule"),
			data_save_area = "data_save_area",
			pMsg = $("body").data(data_save_area)[$target.attr('id')],
			msg = isEmptyObject(pMsg) ? rule[data_save_area] : pMsg,
			isTb = area.dyType == "tb", // 动态上下变长
			obj, areaObj, updateDataKey;
		for (var key in msg) {
			obj = msg[key];
			if (obj.isDynamic) {
				areaObj = obj["area"];
				if (areaObj.col == area.col && areaObj.row == area.row) {
					updateDataKey = obj["updateDataKey"];
					if (type == "add") {
						updateDataKey.splice(index + 1, 0, (function() {
							var k = $.extend(true, [], updateDataKey[0]);
							k[0].value = null;
							return k;
						})());
					} else {
						var delObj = updateDataKey[index];
						if (delObj[0] && delObj[0].value) {
							obj.dels || (obj.dels = []);
							var oo = {};
							oo[delObj[0]["dname"]] = delObj[0]["value"];
							obj.dels.push(oo);
						}
						updateDataKey.splice(index, 1);
					}
				}
			}
		}
	}
};

(function() {
	if (mtBrowser.isIE()) {
		var Spreadjs_oldFunction = GcSpread.Sheets.TextCellType.prototype.setEditorValue;

		GcSpread.Sheets.TextCellType.prototype.setEditorValue = function(editorContext, value, context) {
			var self = this,
				args = arguments;
			try {
				Spreadjs_oldFunction.call(self, args);
			} catch (e) {
				// do nothing
			}
		}
	}
})();