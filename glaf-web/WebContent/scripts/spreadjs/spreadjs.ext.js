/**
 * 
 */

/**
 * star
 */
(function(g) {

	var spreadNS = g.Sheets,
		plugin = function() {
			this.size = 10;
			this.typeName = "100";
			spreadNS.CustomCellType.apply(this, arguments);
		};

	plugin.prototype = new spreadNS.CustomCellType();

	plugin.prototype.paint = function(ctx, value, x, y, w, h, style, options) {
		if (!ctx) {
			return;
		}

		ctx.save();
		ctx.rect(x, y, w, h);
		ctx.clip();
		ctx.beginPath();

		if (value) {
			ctx.fillStyle = "orange";
		} else {
			ctx.fillStyle = "gray";
		}

		var size = this.size;
		var dx = x + w / 2;
		var dy = y + h / 2;
		ctx.beginPath();
		var dig = Math.PI / 5 * 4;
		for (var i = 0; i < 5; i++) {
			ctx.lineTo(dx + Math.sin(i * dig) * size, dy + Math.cos(i * dig) * size);
		}
		ctx.closePath();
		ctx.fill();

		ctx.restore();
	};
	plugin.prototype.getHitInfo = function(x, y, cellStyle, cellRect, context) {
		var xm = cellRect.x + cellRect.width / 2,
			ym = cellRect.y + cellRect.height / 2,
			size = 10;
		var info = {
			x: x,
			y: y,
			row: context.row,
			col: context.col,
			cellRect: cellRect,
			sheetArea: context.sheetArea
		};
		if (xm - size <= x && x <= xm + size && ym - size <= y && y <= ym + size) {
			info.isReservedLocation = true;
		}
		return info;
	};
	plugin.prototype.processMouseUp = function(hitInfo) {
		var sheet = hitInfo.sheet;
		if (sheet && hitInfo.isReservedLocation) {
			var row = hitInfo.row,
				col = hitInfo.col,
				sheetArea = hitInfo.sheetArea;
			var newValue = !sheet.getValue(row, col, sheetArea);
			var cellEditInfo = {
				row: row,
				col: col,
				newValue: newValue
			};
			var undoAction = new spreadNS.UndoRedo.CellEditUndoAction(sheet,
				cellEditInfo);
			sheet.doCommand(undoAction);
			return true;
		}
		return false;
	};
	spreadNS.FivePointedStarCellType = plugin;
})(window.$GcSpread || window.GcSpread);

function isEmptyObject(obj) {
	for (var n in obj) {
		return false
	}
	return true;
}
/**
 * radio
 */
(function(g) {
	var GcSpread = g,
		spreadNS = GcSpread.Sheets,
		plugin = function() {
			this.typeName = "101";
			this._items = ['A', 'C'];
			this._direction = "h"; // horizontal 横向  vertical  纵向    默认横向
			//	spreadNS.CustomCellType.apply(this, arguments);
		};

	var Override = {
		items: function(items) {
			if (arguments.length === 0) {
				return this._items;
			}
			this._items = items;
			return this;
		},
		direction: function(direction) {
			if (arguments.length === 0) {
				return this._direction;
			}
			this._direction = direction;
			return this;
		},
		_getRadioHTML: function(value) {
			var items = this._items;
			var innerHtml = "";
			var radioButtonPattern = '<div class="sp-radio" style="margin: 10px 4px 10px 2px;position: relative;{4}"><label style="min-height: 20px;padding-left: 20px;margin-bottom: 0;"><input type="radio" name="GCInnerRadios" id="optionsRadios{0}" value="{1}" {2} style="position: absolute;margin: 4px 0 0 -20px;"/>{3}</label></div>';
			if (items) {
				var count = items.length;
				if (count > 0) {
					for (var i = 0; i < count; i++) {
						item = items[i];
						var radioText, radioValue;
						if (typeof item === "object") {
							radioText = item.text;
							radioValue = item.value;
						} else {
							radioText = item;
							radioValue = item;
						}

						var isChecked = radioValue == value ? 'checked="checked"' : '';
						innerHtml += radioButtonPattern.replace(/\{0\}/g,
								i.toString()).replace("{1}", radioValue)
							.replace("{2}", isChecked).replace("{3}",
								radioText).replace("{4}",
								this._direction == "h" ? "display:inline-block;" : "");
					}
				}
			}
			return innerHtml;
		},
		paint: function(ctx, value, x, y, w, h, style, context) {
			if (!ctx) {
				return;
			}
			var self = this,
				items = self._items;
			var DOMURL = window.URL || window.webkitURL || window;
			var cell = context.sheet.getCell(context.row, context.col);
			var img = cell.tag();
			if (img && !isEmptyObject(img)) {
				ctx.save();
				ctx.rect(x, y, w, h);
				ctx.clip();
				ctx.drawImage(img, x + 2, y + 2)
				DOMURL.revokeObjectURL(url);
				ctx.restore();
				cell.tag(null);
				return;
			}
			var svgPattern = '<svg xmlns="http://www.w3.org/2000/svg" width="{0}" height="{1}">' + '<foreignObject width="100%" height="100%"><div xmlns="http://www.w3.org/1999/xhtml" style="font:{2};margin:0px 2px; padding-top:1px;">{3}</div></foreignObject></svg>';
			var innerHtml = self._getRadioHTML(value);
			var data = svgPattern.replace(/\{0\}/g, w.toString()).replace(
					/\{1\}/g, h.toString()).replace(/\{2\}/g, style.font)
				.replace(/\{3\}/g, innerHtml);
			img = new Image();
			var svg = new Blob([data], {
				type: 'image/svg+xml;charset=utf-8'
			});
			var url = DOMURL.createObjectURL(svg);
			cell.tag(img);
			img.onload = function() {
				context.sheet.repaint(new GcSpread.Sheets.Rect(x, y, w, h));
			}
			img.src = url;


			return;
		},
		createEditorElement: function(context) {
			var div = document.createElement("div");
			var $div = $(div);
			$div.attr("gcUIElement", "gcEditingInput");
			$div.css("background-color", "white");
			$div.css("position", "absolute");
			$div.css("overflow", "hidden");
			$div.css("margin", "2px");
			var html = this._getRadioHTML("");
			$div.html(html);
			return div;
		},
		setEditorValue: function(editorContext, value, context) {
			if (editorContext) {
				var selected = $("input[type='radio'][value='" + value + "']",
					$(editorContext));
				if (selected.length > 0) {
					selected.prop("checked", true);
				}
			}
			//$(editorContext).val(value);
		},
		getEditorValue: function(editorContext, value, context) {
			var selectedVal = "";
			var selected = $("input[type='radio']:checked", $(editorContext));
			if (selected.length > 0) {
				return selected.val();
			}
			return null;
		},
		updateEditor: function(editorContext, cellStyle, cellRect, context) {
			if (editorContext) {
				var height = (this._items && this._items.length * 30 > cellRect.height) ? this._items.length * 30 : cellRect.height;
				$(editorContext).width(cellRect.width - 5);
				//$(editorContext).height(height - 3);
				$(editorContext).css('font', cellStyle.font);
				$(editorContext).css('padding-left', '3px');
			}
		},
		getHitInfo: function(x, y, cellStyle, cellRect, context) {
			var size = 30;
			var info = {
				x: x,
				y: y,
				row: context.row,
				col: context.col,
				cellRect: cellRect,
				sheetArea: context.sheetArea,
				isReservedLocation: false,
				reservedLocation: -1
			};
			var self = this,
				items = self._items,
				item;
			if (items && items.length) {
				for (var i = 0; i < items.length; i++) {
					if (y - cellRect.y < size * (i + 1)) {
						info.isReservedLocation = true;
						item = items[i];
						info.reservedLocation = typeof item === "object" ? item.value : item;
						//break
					}
				}
			}
			return info;
		},
		processMouseUp: function(hitInfo) {
			var sheet = hitInfo.sheet;
			sheet.setActiveCell(hitInfo.row, hitInfo.col);
			sheet.startEdit();
			sheet.triggerCellClick({
				sheet: sheet,
				sheetName: sheet._name,
				sheetArea: hitInfo.sheetArea,
				row: hitInfo.row,
				col: hitInfo.col
			});
			if (sheet && hitInfo.isReservedLocation && hitInfo.reservedLocation >= 0) {
				var row = hitInfo.row,
					col = hitInfo.col,
					sheetArea = hitInfo.sheetArea;
				var cellEditInfo = {
					row: row,
					col: col,
					newValue: hitInfo.reservedLocation
				};
				var undoAction = new GcSpread.Sheets.UndoRedo.CellEditUndoAction(
					sheet, cellEditInfo);
				sheet.doCommand(undoAction);
				return true;
			}
			return false;
		}
	};

	plugin.prototype = $.extend(true, new spreadNS.CustomCellType(), Override);

	spreadNS.RadioButtonCellType = plugin;

})(window.$GcSpread || window.GcSpread);

/**
 * checkbox
 */
(function(g) {
	var GcSpread = g,
		spreadNS = GcSpread.Sheets,
		plugin = function() {
			this.typeName = "102";
			this._items = ['A', 'C'];
			this._direction = "h"; // horizontal 横向  vertical  纵向    默认横向
			//	spreadNS.CustomCellType.apply(this, arguments);
		};

	var Override = {
		items: function(items) {
			if (arguments.length === 0) {
				return this._items;
			}
			this._items = items;
			return this;
		},
		direction: function(direction) {
			if (arguments.length === 0) {
				return this._direction;
			}
			this._direction = direction;
			return this;
		},
		_getCheckBoxHTML: function(value) {
			var items = this._items;
			var innerHtml = "";
			var checkboxButtonPattern = '<div class="sp-checkbox" style="margin: 10px 4px 10px 2px;position: relative;{4}"><label style="min-height: 20px;padding-left: 20px;margin-bottom: 0;"><input type="checkbox" name="GCInnerCheckboxs" id="optionsCheckboxs{0}" value="{1}" {2} style="position: absolute;margin: 4px 0 0 -20px;"/>{3}</label></div>';
			if (items) {
				var values = [];
				if (value && typeof(value + "") === "string") {
					values = (value + "").split(",");
				}
				var count = items.length;
				if (count > 0) {
					for (var i = 0; i < count; i++) {
						item = items[i];
						var checkboxText, checkboxValue;
						if (typeof item === "object") {
							checkboxText = item.text;
							checkboxValue = item.value;
						} else {
							checkboxText = item;
							checkboxValue = item;
						}

						var isChecked = $.inArray(checkboxValue, values) > -1 /*value == checkboxValue */ ? 'checked="checked"' : '';
						innerHtml += checkboxButtonPattern.replace(/\{0\}/g,
								i.toString()).replace("{1}", checkboxValue)
							.replace("{2}", isChecked).replace("{3}",
								checkboxText).replace("{4}",
								this._direction == "h" ? "display:inline-block;" : "");
					}
				}
			}
			return innerHtml;
		},
		paint: function(ctx, value, x, y, w, h, style, context) {
			if (!ctx) {
				return;
			}
			var self = this,
				items = self._items;
			var DOMURL = window.URL || window.webkitURL || window;
			var cell = context.sheet.getCell(context.row, context.col);
			var img = cell.tag();
			if (img && !isEmptyObject(img)) {
				ctx.save();
				ctx.rect(x, y, w, h);
				ctx.clip();
				ctx.drawImage(img, x + 2, y + 2)
				DOMURL.revokeObjectURL(url);
				ctx.restore();
				cell.tag(null);
				return;
			}
			var svgPattern = '<svg xmlns="http://www.w3.org/2000/svg" width="{0}" height="{1}">' + '<foreignObject width="100%" height="100%"><div xmlns="http://www.w3.org/1999/xhtml" style="font:{2};margin:0px 2px; padding-top:1px;">{3}</div></foreignObject></svg>';
			var innerHtml = self._getCheckBoxHTML(value);
			var data = svgPattern.replace(/\{0\}/g, w.toString()).replace(
					/\{1\}/g, h.toString()).replace(/\{2\}/g, style.font)
				.replace(/\{3\}/g, innerHtml);
			img = new Image();
			var svg = new Blob([data], {
				type: 'image/svg+xml;charset=utf-8'
			});
			var url = DOMURL.createObjectURL(svg);
			cell.tag(img);
			img.onload = function() {
				context.sheet.repaint(new GcSpread.Sheets.Rect(x, y, w, h));
			}
			img.src = url;

			//spreadNS.CustomCellType.prototype.paint.apply(this, [ctx, value, x, y, w, h, style, context]);

			return;
		},
		createEditorElement: function(context) {
			var div = document.createElement("div");
			var $div = $(div);
			$div.attr("gcUIElement", "gcEditingInput");
			$div.css("background-color", "white");
			$div.css("position", "absolute");
			$div.css("overflow", "hidden");
			$div.css("margin", "2px");
			var html = this._getCheckBoxHTML("");
			$div.html(html);
			return div;
		},
		setEditorValue: function(editorContext, value, context) {
			if (editorContext && typeof value == "string") {
				var values = value.split(","),
					v;
				for (var i = values.length - 1; i >= 0; i--) {
					v = values[i];
					var selected = $("input[type='checkbox'][value='" + v + "']", $(editorContext));
					if (selected.length > 0) {
						selected.prop("checked", true);
					}
				}
			}
			//$(editorContext).val(value);
		},
		getEditorValue: function(editorContext, value, context) {
			var selectedVal = "",
				retVal = [];
			var selected = $("input[type='checkbox']:checked", $(editorContext));
			if (selected.length > 0) {
				$.each(selected, function(index, val) {
					retVal.push($(val).val());
				});
				return /*selected.val()*/ retVal.join(",");
			}
			return "";
		},
		updateEditor: function(editorContext, cellStyle, cellRect, context) {
			if (editorContext) {
				var height = (this._items && this._items.length * 30 > cellRect.height) ? this._items.length * 30 : cellRect.height;
				$(editorContext).width(cellRect.width - 5);
				$(editorContext).height(cellRect.height - 3);
				$(editorContext).css('font', cellStyle.font);
				$(editorContext).css('padding-left', '3px');
			}
		},
		getHitInfo: function(x, y, cellStyle, cellRect, context) {
			var size = 30;
			var info = {
				x: x,
				y: y,
				row: context.row,
				col: context.col,
				cellRect: cellRect,
				sheetArea: context.sheetArea,
				isReservedLocation: false,
				reservedLocation: -1
			};
			var reservedLocations = [];
			var self = this,
				items = self._items,
				item;
			if (items && items.length) {
				for (var i = 0; i < items.length; i++) {
					if (y - cellRect.y < size * (i + 1)) {
						info.isReservedLocation = true;
						item = items[i];
						reservedLocations.push(typeof item === "object" ? item.value : item);
						//info.reservedLocation = i;
						//break
					}
				}
				info.reservedLocation = reservedLocations.join(",");
			}
			return info;
		},
		processMouseUp: function(hitInfo) {
			var sheet = hitInfo.sheet;
			sheet.setActiveCell(hitInfo.row, hitInfo.col);
			sheet.startEdit();
			sheet.triggerCellClick({
				sheet: sheet,
				sheetName: sheet._name,
				sheetArea: hitInfo.sheetArea,
				row: hitInfo.row,
				col: hitInfo.col
			});
			if (sheet && hitInfo.isReservedLocation && hitInfo.reservedLocation >= 0) {
				var row = hitInfo.row,
					col = hitInfo.col,
					sheetArea = hitInfo.sheetArea;
				var cellEditInfo = {
					row: row,
					col: col,
					newValue: hitInfo.reservedLocation
				};
				var undoAction = new GcSpread.Sheets.UndoRedo.CellEditUndoAction(
					sheet, cellEditInfo);
				sheet.doCommand(undoAction);
				return true;
			}
			return false;
		}
	};

	plugin.prototype = $.extend(true, new spreadNS.CustomCellType(), Override);

	spreadNS.CheckboxButtonCellType = plugin;

})(window.$GcSpread || window.GcSpread);

/**
 * imageType 
 */
(function(g) {
	var GcSpread = g,
		spreadNS = GcSpread.Sheets,
		plugin = function() {
			this.typeName = "103";
			this._url = (parent.contextPath || contextPath) + "/images/filesave.png";
			this._click = function() {};
		};

	var Override = {
		url: function(url) {
			if (arguments.length === 0) {
				return this._url;
			}
			this._url = url;
			return this;
		},
		click: function(fn) {
			if (arguments.length === 0) {
				return this._click;
			}
			this._click = fn;
			return this;
		},
		_getCellHtml: function() {
			var items = this._items;
			var innerHtml = "";
			var cellPattern = '<image src="{0}" />';
			if (this._url) {
				innerHtml = cellPattern.replace(/\{0\}/g, this._url);
			}
			return innerHtml;
		},
		paint: function(ctx, value, x, y, w, h, style, context) {
			if (!ctx) {
				return;
			}
			var self = this;
			var DOMURL = window.URL || window.webkitURL || window;
			var cell = context.sheet.getCell(context.row, context.col);
			var img = cell.tag();
			if (img) {
				ctx.save();
				ctx.rect(x, y, w, h);
				ctx.clip();
				ctx.drawImage(img, x + 2, y + 2);
				ctx.restore();
				cell.tag(null);
				return;
			}
			img = new Image();
			cell.tag(img);
			img.onload = function() {
				context.sheet.repaint(new GcSpread.Sheets.Rect(x, y, w, h));
			}
			img.src = this._url;
			return;
		},
		getHitInfo: function(x, y, cellStyle, cellRect, context) {
			var size = 30;
			var info = {
				x: x,
				y: y,
				row: context.row,
				col: context.col,
				cellRect: cellRect,
				sheetArea: context.sheetArea,
				isReservedLocation: false,
				reservedLocation: -1
			};
			return info;
		},
		processMouseUp: function(hitInfo) {
			var sheet = hitInfo.sheet;
			sheet.triggerCellClick({
				sheet: sheet,
				sheetName: sheet._name,
				sheetArea: hitInfo.sheetArea,
				row: hitInfo.row,
				col: hitInfo.col
			});
			this._click.call();
			if (sheet && hitInfo.isReservedLocation && hitInfo.reservedLocation >= 0) {
				var row = hitInfo.row,
					col = hitInfo.col,
					sheetArea = hitInfo.sheetArea;
				var cellEditInfo = {
					row: row,
					col: col,
					newValue: hitInfo.reservedLocation
				};
				var undoAction = new GcSpread.Sheets.UndoRedo.CellEditUndoAction(
					sheet, cellEditInfo);
				sheet.doCommand(undoAction);
				return true;
			}
			return false;
		}
	};

	plugin.prototype = $.extend(true, new spreadNS.CustomCellType(), Override);

	spreadNS.ImageCellType = plugin;

})(window.$GcSpread || window.GcSpread);

/**
 * datebox
 */
(function(g) {
	var GcSpread = g,
		spreadNS = GcSpread.Sheets,
		plugin = function() {
			this.typeName = "104";
			this._value = "";
			//	spreadNS.CustomCellType.apply(this, arguments);
		};

	var Override = {
		_getHTML: function(value) {
			//var value = this._value;
			var innerHtml = '<input id="sp-databox" placeholder="请输入日期" class="laydate-icon" onclick="laydate();" value="{0}">';
			innerHtml = innerHtml.replace(/\{0\}/g, value);
			return innerHtml;
		},
		paint: function(ctx, value, x, y, w, h, style, context) {
			if (value) {
				var cell = context.sheet.getCell(context.row, context.col);
				try {
					spreadNS.CustomCellType.prototype.paint.apply(this, [ctx, new Date(value).format(cell.formatter() || "yyyy-MM-dd"), x, y, w, h, style, context]);
				} catch (e) {}
			}
		},
		createEditorElement: function(context) {
			var div = document.createElement("div");
			var $div = $(div);
			$div.attr("gcUIElement", "gcEditingInput");
			$div.css("background-color", "white");
			$div.css("position", "absolute");
			$div.css("overflow", "hidden");
			$div.css("margin", "2px");
			var html = this._getHTML("");
			$div.html(html);

			return div;
		},
		setEditorValue: function(editorContext, value, context) {
			if (editorContext && editorContext.children.length === 1) {
				var input = editorContext.children[0];
				if (value) {
					var cell = context.sheet.getCell(context.row, context.col);
					try {
						$(input).val(new Date(value).format( /*cell.formatter() || */ "yyyy-MM-dd"));
					} catch (e) {

					}
				}
			}
		},
		getEditorValue: function(editorContext, value, context) {
			if (editorContext && editorContext.children.length === 1) {
				var input = editorContext.children[0];
				return $(input).val();
			}
		},
		updateEditor: function(editorContext, cellStyle, cellRect, context) {
			if (editorContext) {
				//$(editorContext).width(245);
				// $(editorContext).height(250);
			}
		},
		activateEditor: function(editorContext, cellStyle, cellRect, context) {
			var $editor = $(editorContext);
			spreadNS.CustomCellType.prototype.activateEditor.apply(this, [editorContext, cellStyle, cellRect, context]);
			setTimeout(function() {
				$editor.find("input.laydate-icon").trigger("click");
				$("#laydate_box").attr("gcUIElement", "gcEditingInput");
			}, 50);
			return $editor;
		},
		deactivateEditor: function(editorContext, context) {
			spreadNS.CustomCellType.prototype.deactivateEditor.apply(this, [editorContext, context]);
			//super.deactivateEditor(editorContext, context);
		},
		getHitInfo: function(x, y, cellStyle, cellRect, context) {
			var info = {
				x: x,
				y: y,
				row: context.row,
				col: context.col,
				cellRect: cellRect,
				sheetArea: context.sheetArea,
				isReservedLocation: false,
				reservedLocation: -1
			};
			return info;
		},
		processMouseUp: function(hitInfo) {
			var sheet = hitInfo.sheet;
			sheet.setActiveCell(hitInfo.row, hitInfo.col);
			sheet.startEdit();
			sheet.triggerCellClick({
				sheet: sheet,
				sheetName: sheet._name,
				sheetArea: hitInfo.sheetArea,
				row: hitInfo.row,
				col: hitInfo.col
			});
			spreadNS.CustomCellType.prototype.processMouseUp(hitInfo);
		}
	};

	plugin.prototype = $.extend(true, new spreadNS.CustomCellType(), Override);

	spreadNS.DateboxCellType = plugin;

})(window.$GcSpread || window.GcSpread);


/**
 * select2 多选下拉框
 */
(function(g) {
	var GcSpread = g,
		spreadNS = GcSpread.Sheets,
		plugin = function() {
			this.typeName = "105";
			this._items = ["A", "B", "C"];
			//	spreadNS.CustomCellType.apply(this, arguments);
		};

	var Override = {
		items: function(items) {
			if (arguments.length === 0) {
				return this._items;
			}
			this._items = items;
			return this;
		},
		_getHTML: function(value) {
			//var value = this._value;
			var items = this._items;
			var innerHtml = '<select class="sp-select2 form-control select2" style="display:none" multiple>';
			var pattern = '<option value="{0}" {1}>{2}</option>';
			if (items) {
				var values = [];
				if (value && typeof(value + "") === "string") {
					values = (value + "").split(",");
				}
				var count = items.length;
				if (count > 0) {
					for (var i = 0; i < count; i++) {
						item = items[i];
						var dtext, dvalue;
						if (typeof item === "object") {
							dtext = item.text;
							dvalue = item.value;
						} else {
							dtext = item;
							dvalue = item;
						}
						var isChecked = $.inArray(dvalue, values) > -1 ? 'checked="checked"' : '';
						innerHtml += pattern.replace(/\{0\}/g, dvalue).replace("{1}", isChecked).replace("{2}", dtext);
					}
				}
			}
			innerHtml += '</select>';
			return innerHtml;
		},
		paint: function(ctx, value, x, y, w, h, style, context) {
			spreadNS.CustomCellType.prototype.paint.apply(this, [ctx, value, x, y, w, h, style, context]);
		},
		createEditorElement: function(context) {
			var div = document.createElement("div");
			var $div = $(div);
			$div.attr("gcUIElement", "gcEditingInput");
			$div.css("background-color", "white");
			$div.css("position", "absolute");
			$div.css("overflow", "hidden");
			$div.css("margin", "2px");
			var html = this._getHTML("");
			$div.html(html);
			return div;
		},
		setEditorValue: function(editorContext, value, context) {
			if (editorContext) {
				if (value) {
					var values = value.split(","),
						v;
					for (var i = values.length - 1; i >= 0; i--) {
						v = values[i];
						var selected = $("option[value='" + v + "']", $(editorContext));
						if (selected.length > 0) {
							selected.prop("selected", true);
						}
					}
				}
			}
		},
		getEditorValue: function(editorContext, value, context) {
			if (editorContext) {
				var select2 = $(editorContext).find(".sp-select2").data("select2"),
					val = select2 ? select2.val() : "";
				return val ? val.join(",") : "";
			}
		},
		updateEditor: function(editorContext, cellStyle, cellRect, context) {
			if (editorContext) {
				//$(editorContext).width(245);
				// $(editorContext).height(250);
			}
		},
		activateEditor: function(editorContext, cellStyle, cellRect, context) {
			var $editor = $(editorContext);
			spreadNS.CustomCellType.prototype.activateEditor.apply(this, [editorContext, cellStyle, cellRect, context]);
			setTimeout(function() {
				var $select2 = $editor.find(".sp-select2").width(cellRect.width).height(cellRect.height).show();
				if ($select2.select2) {
					$select2.select2();
				}
			}, 10);
			return $editor;
		},
		deactivateEditor: function(editorContext, context) {
			if (editorContext) {
				var $select2 = $(editorContext).find(".sp-select2");
				if ($select2.select2) {
					$select2.select2("close");
				}
			}
			spreadNS.CustomCellType.prototype.deactivateEditor.apply(this, [editorContext, context]);
			//super.deactivateEditor(editorContext, context);
		},
		getHitInfo: function(x, y, cellStyle, cellRect, context) {
			var info = {
				x: x,
				y: y,
				row: context.row,
				col: context.col,
				cellRect: cellRect,
				sheetArea: context.sheetArea,
				isReservedLocation: false,
				reservedLocation: -1
			};
			return info;
		},
		processMouseUp: function(hitInfo) {
			var sheet = hitInfo.sheet;
			sheet.setActiveCell(hitInfo.row, hitInfo.col);
			sheet.startEdit();
			sheet.triggerCellClick({
				sheet: sheet,
				sheetName: sheet._name,
				sheetArea: hitInfo.sheetArea,
				row: hitInfo.row,
				col: hitInfo.col
			});
			spreadNS.CustomCellType.prototype.processMouseUp(hitInfo);
		}
	};

	plugin.prototype = $.extend(true, new spreadNS.CustomCellType(), Override);

	spreadNS.Select2CellType = plugin;

})(window.$GcSpread || window.GcSpread);

/**
 * imageUpload
 */
(function(g) {
	var GcSpread = g,
		spreadNS = GcSpread.Sheets,
		plugin = function() {
			this.typeName = "106";
			this._click = function() {};
			this._contextPath = (parent.contextPath || contextPath);
			this._saveUrl = this._contextPath + '/mx/form/imageUpload?method=upload&to=to_db';
			this._removeUrl = this._contextPath + '/mx/form/imageUpload?method=removeByUUID&from=to_db';
			this._downloadUrl = this._contextPath + '/mx/form/imageUpload?method=download&from=to_db';
			this._maxFileSize = '5242880';

		};

	var Override = {
		click: function(fn) {
			if (arguments.length === 0) {
				return this._click;
			}
			this._click = fn;
			return this;
		},
		_getCellHtml: function(value) {
			var innerHtml = "";
			var patterns = [
				'<div data-role="imageupload" class="image_upload_div" saveurl="{0}" outputname="textbox" removeurl="{1}" downloadurl="{2}" attachmentid="{3}" randomparent="{4}" maxfilesize="{5}">',
				'<div class="imageupload_tools" style="width:100%;height:100%;">',
				'<a href="#" class="image_upload_btn_select" >',
				'<img class="imageupload_del" title="删除图片" alt="删除" src="/glaf/images/del.png" onclick="return imageuploadFunc.delImg(this)">',
				'<input class="image_upload_input_file" type="file" name="file" id="img_upload_input_imageupload">',
				'选择图片</a></div>',
				'<img class="imageupload_class" height="100%" style="display:none;" src="{2}+ &randomparent={4}">',
				'</div>'
			];
			var cellPattern = patterns.join("");
			innerHtml = cellPattern.replace(/\{0\}/g, this._saveUrl).replace(/\{1\}/g, this._removeUrl).replace(/\{2\}/g, this._downloadUrl)
				.replace(/\{3\}/g, "").replace(/\{4\}/g, "").replace(/\{5\}/g, this._maxFileSize);
			return innerHtml;
		},
		paint: function(ctx, value, x, y, w, h, style, context) {
			if (!ctx) {
				return;
			}
			//value = "478,bf1c92f3-7e9f-483c-894a-25bc2fd4b10a" ;
			if (!value) {
				return;
			}
			var self = this;
			var DOMURL = window.URL || window.webkitURL || window;
			var cell = context.sheet.getCell(context.row, context.col);
			var img = cell.tag();
			try {
				if (img) {
					ctx.save();
					ctx.rect(x, y, w, h);
					ctx.clip();
					ctx.drawImage(img, x + 2, y + 2);
					ctx.restore();
					cell.tag(null);
					return;
				}
			} catch (e) {
				console.log(e);
				return;
			}

			img = new Image();
			cell.tag(img);
			img.onload = function() {
				context.sheet.repaint(new GcSpread.Sheets.Rect(x, y, w, h));
			}
			var vls = value.split(",");
			img.src = this._downloadUrl + "&randomparent=" + (vls.length > 1 ? vls[1] : vls[0]) + "&_" + new Date().getTime();
			return;
		},
		createEditorElement: function(context) {
			var div = document.createElement("div");
			var $div = $(div);
			$div.attr("gcUIElement", "gcEditingInput");
			$div.css("background-color", "white");
			$div.css("position", "absolute");
			$div.css("overflow", "hidden");
			$div.css("margin", "2px");
			var html = this._getCellHtml("");
			$div.html(html);
			return div;
		},
		setEditorValue: function(editorContext, value, context) {
			if (editorContext) {
				//value = "478,bf1c92f3-7e9f-483c-894a-25bc2fd4b10a" ;
				var $editor = $(editorContext),
					$div = $editor.find(".image_upload_div"),
					$img = $editor.find(".imageupload_class");
				if (value) {
					var vls = value.split(",");
					$div.attr("attachmentid", vls.length == 1 ? "-1" : vls[0]).attr("randomparent", vls.length > 1 ? vls[1] : vls[0]);
					$img.attr("src", this._downloadUrl + "&randomparent=" + (vls.length > 1 ? vls[1] : vls[0]) + "&_" + new Date().getTime()).show();
				} else {
					if (window.UUID) {
						$div.attr("randomparent", new UUID().createUUID());
					}
				}
			}
		},
		getEditorValue: function(editorContext, value, context) {
			if (editorContext) {
				var $editor = $(editorContext),
					$div = $editor.find(".image_upload_div"),
					attachmentid = $div.attr("attachmentid"),
					randomparent = $div.attr("randomparent");
				if (attachmentid) {
					if (attachmentid == "-1")
						return randomparent;
					else
						return attachmentid + "," + randomparent;
				}
			}
			return "";
		},
		updateEditor: function(editorContext, cellStyle, cellRect, context) {
			if (editorContext) {
				//$(editorContext).width(245);
				// $(editorContext).height(250);
			}
		},
		activateEditor: function(editorContext, cellStyle, cellRect, context) {
			var $editor = $(editorContext);
			spreadNS.CustomCellType.prototype.activateEditor.apply(this, [editorContext, cellStyle, cellRect, context]);
			var $div = $editor.find(".image_upload_div").width(cellRect.width).height(cellRect.height - 3);
			$editor.find(".imageupload_tools").width(cellRect.width).height(cellRect.height - 3);
			$editor.find(".image_upload_btn_select").css("line-height", (cellRect.height - 3) + "px");
			return $editor;
		},
		getHitInfo: function(x, y, cellStyle, cellRect, context) {
			var info = {
				x: x,
				y: y,
				row: context.row,
				col: context.col,
				cellRect: cellRect,
				sheetArea: context.sheetArea,
				isReservedLocation: false,
				reservedLocation: -1
			};
			return info;
		},
		processMouseUp: function(hitInfo) {
			var sheet = hitInfo.sheet;
			//sheet.setActiveCell(hitInfo.row, hitInfo.col);
			//sheet.startEdit();
			sheet.triggerCellClick({
				sheet: sheet,
				sheetName: sheet._name,
				sheetArea: hitInfo.sheetArea,
				row: hitInfo.row,
				col: hitInfo.col
			});
			this._click.call();
			return spreadNS.CustomCellType.prototype.processMouseUp(hitInfo);
		}
	};

	plugin.prototype = $.extend(true, new spreadNS.CustomCellType(), Override);

	spreadNS.ImageUploadCellType = plugin;

})(window.$GcSpread || window.GcSpread);

/**
 * fileUpload
 */
(function(g) {
	var GcSpread = g,
		spreadNS = GcSpread.Sheets,
		plugin = function() {
			this.typeName = "107";
			this._click = function() {};
			this._contextPath = (parent.contextPath || contextPath);
			this._saveUrl2 = this._contextPath + '/mx/form/attachment?method=upload&to=to_db&randomParent=';
			this._removeUrl2 = this._contextPath + '/mx/form/attachment?method=remove&from=to_db';
			this._downloadUrl2 = this._contextPath + '/mx/form/attachment?method=download&from=to_db';
			this._getUrl2 = this._contextPath + "/mx/form/attachment?method=getFilesByRandomParent&randomParent=";
			this._maxFileSize = '5242880';
			this._acName = "";
			this.loading = false;
		};

	var Override = {
		click: function(fn) {
			if (arguments.length === 0) {
				return this._click;
			}
			this._click = fn;
			return this;
		},
		setMaxFileSize: function(size) {
			if (arguments.length === 0) {
				return this._maxFileSize;
			}
			this.loading = false;
			this._maxFileSize = size;
			return this;
		},
		_getCellHtml: function(value) {
			var patterns = [
				'<div class="mt-sp-jqfileupload" data-role="jqfileupload2">',
				'<span class="btn btn-success fileinput-button"><i class="glyphicon glyphicon-plus"></i><span>选择文件</span>',
				'<input class="mt-sp-fileupload" type="file" name="file" multiple></input></span>',
				'<div class="progress">',
				'<div class="progress-bar progress-bar-success"></div>',
				'</div>',
				'<div class="mt-sp-files"></div>',
				'</div>'
			];
			return patterns.join("");
		},
		paint: function(ctx, value, x, y, w, h, style, context) {
			if (!ctx) {
				return;
			}
			if (!value) {
				return;
			}
			var self = this;
			var DOMURL = window.URL || window.webkitURL || window;
			var cell = context.sheet.getCell(context.row, context.col);
			var img = cell.tag();
			if (img && !isEmptyObject(img)) {
				ctx.save();
				ctx.rect(x, y, w, h);
				ctx.clip();
				ctx.drawImage(img, x + 2, y + 2)
				DOMURL.revokeObjectURL(url);
				ctx.restore();
				cell.tag(null);
				return;
			}
			var svgPattern = '<svg xmlns="http://www.w3.org/2000/svg" width="{0}" height="{1}">' + '<foreignObject width="100%" height="100%"><div xmlns="http://www.w3.org/1999/xhtml" style="font:{2};margin:0px 2px; padding-top:1px;">{3}</div></foreignObject></svg>';
			if (!this._acName) {
				if (this.loading) {
					return;
				}
				this.loading = true;
				$.ajax({
					url: self._getUrl2 + value,
					success: function(data) {
						if (data) {
							$.each(JSON.parse(data), function(i, v) {
								self._acName += "<a href='#'>" + v.fileName + "</a><br/>";
							});
						}
						var innerHtml = self._acName || "" //self._getCellHtml(value);
						var data = svgPattern.replace(/\{0\}/g, w.toString()).replace(
								/\{1\}/g, h.toString()).replace(/\{2\}/g, style.font)
							.replace(/\{3\}/g, innerHtml);
						img = new Image();
						var svg = new Blob([data], {
							type: 'image/svg+xml;charset=utf-8'
						});
						var url = DOMURL.createObjectURL(svg);
						cell.tag(img);
						img.onload = function() {
							context.sheet.repaint(new GcSpread.Sheets.Rect(x, y, w, h));
						}
						img.src = url;
						return;
					},
					error: function(d) {
						self.loading = false;
					}
				});
			} else {
				var innerHtml = this._acName || "" //self._getCellHtml(value);
				var data = svgPattern.replace(/\{0\}/g, w.toString()).replace(
						/\{1\}/g, h.toString()).replace(/\{2\}/g, style.font)
					.replace(/\{3\}/g, innerHtml);
				img = new Image();
				var svg = new Blob([data], {
					type: 'image/svg+xml;charset=utf-8'
				});
				var url = DOMURL.createObjectURL(svg);
				cell.tag(img);
				img.onload = function() {
					context.sheet.repaint(new GcSpread.Sheets.Rect(x, y, w, h));
				}
				img.src = url;
				return;
			}
		},
		createEditorElement: function(context) {
			var div = document.createElement("div");
			var $div = $(div);
			$div.attr("gcUIElement", "gcEditingInput");
			$div.css("background-color", "white");
			$div.css("position", "absolute");
			$div.css("overflow", "hidden");
			$div.css("margin", "2px");
			var html = this._getCellHtml("");
			$div.html(html);
			return div;
		},
		setEditorValue: function(editorContext, value, context) {
			if (editorContext) {
				var $editor = $(editorContext),
					$fileUpload = $editor.find(".mt-sp-jqfileupload"),
					randomParent = value || new UUID().createUUID();
				$fileUpload.spFileUpload({
					url: this._saveUrl2,
					getUrl: this._getUrl2,
					dowmloadUrl: this._downloadUrl2,
					removeUrl: this._removeUrl2,
					randomParent: randomParent,
					maxFileSize: this._maxFileSize
				});
				//$fileUpload.spFileUpload("setValue", randomParent);
				if (value) {
					$fileUpload.spFileUpload("setValue", value);
				}
			}
		},
		getEditorValue: function(editorContext, value, context) {
			if (editorContext) {
				var $editor = $(editorContext),
					$fileUpload = $editor.find(".mt-sp-jqfileupload"),
					self = this;
				self._acName = "";
				$fileUpload.find(".mt-sp-files span").each(function(index, el) {
					if (!el.children.length) {
						self._acName += el.innerHTML + "<br/>";
					}
				});
				//this._acName = $fileUpload.data("saveId");
				return $fileUpload.data("saveId");
			}
			return "";
		},
		updateEditor: function(editorContext, cellStyle, cellRect, context) {
			if (editorContext) {
				//$(editorContext).width(245);
				//$(editorContext).height(250);
			}
		},
		activateEditor: function(editorContext, cellStyle, cellRect, context) {
			var $editor = $(editorContext);
			spreadNS.CustomCellType.prototype.activateEditor.apply(this, [editorContext, cellStyle, cellRect, context]);
			/*var $div = $editor.find(".image_upload_div").width(cellRect.width).height(cellRect.height-3);
				$editor.find(".imageupload_tools").width(cellRect.width).height(cellRect.height-3);
				$editor.find(".image_upload_btn_select").css("line-height",(cellRect.height-3)+"px");*/
			return $editor;
		},
		getHitInfo: function(x, y, cellStyle, cellRect, context) {
			var info = {
				x: x,
				y: y,
				row: context.row,
				col: context.col,
				cellRect: cellRect,
				sheetArea: context.sheetArea,
				isReservedLocation: false,
				reservedLocation: 9999
			};
			return info;
		},
		processMouseUp: function(hitInfo) {
			var sheet = hitInfo.sheet;
			//sheet.setActiveCell(hitInfo.row, hitInfo.col);
			//sheet.startEdit();
			sheet.triggerCellClick({
				sheet: sheet,
				sheetName: sheet._name,
				sheetArea: hitInfo.sheetArea,
				row: hitInfo.row,
				col: hitInfo.col
			});
			this._click.call();
			return spreadNS.CustomCellType.prototype.processMouseUp(hitInfo);
		}
	};

	plugin.prototype = $.extend(true, new spreadNS.CustomCellType(), Override);

	spreadNS.FileUploadCellType = plugin;

})(window.$GcSpread || window.GcSpread);

(function(g) {

	/**
	 * 范序列化指定函数
	 */
	var spreadNS = g.Sheets,
		oldFun = spreadNS.getTypeFromString;

	var mapping = {
		"100": spreadNS.FivePointedStarCellType,
		"101": spreadNS.RadioButtonCellType,
		"102": spreadNS.CheckboxButtonCellType,
		"103": spreadNS.ImageCellType,
		"104": spreadNS.DateboxCellType,
		"105": spreadNS.Select2CellType,
		"106": spreadNS.ImageUploadCellType,
		"107": spreadNS.FileUploadCellType,
	};

	spreadNS.getTypeFromString = function(typeString) {
		if (typeString && (typeString in mapping)) {
			return mapping[typeString];
		} else {
			return oldFun.apply(this, arguments);
		}
	};

})((window.$GcSpread || window.GcSpread));