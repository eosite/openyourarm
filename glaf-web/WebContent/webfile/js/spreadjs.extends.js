/**
 * 
 */
window.readyFuncs.push(function() {
	$("button").on('click.btn', function() {
		var id = $(this).attr("id"),
			func = id + "Func";
		if (window[func]) {
			window[func].call(this);
		} else {
			show("方法 " + func + " 未定义!");
		}
	});
});

readyFuncs.push(function() {
	var target = "button.mt-relate",
		key = target;
	$("body").on("mouseover." + target, target, function() {
		var $this = $(this);
		var $input = $this.closest("div.form-group").find("input:hidden");
		if ($input.val()) {
			var json = JSON.parse($input.val());
			if (json = json["col-2"]) {
				if (json = json["data"]) {
					if (json["linkageControl"]) {
						var jar = JSON.parse(json["linkageControl"]);
						window.setCellLight(jar, true);
						$this.data(key, jar);
					}
				}
			}
		}
	}).on("mouseout." + target, target, function() {
		window.setCellLight($(this).data(key), false);
	});
});

function setCellLight(cells, light) {
	if (cells && cells.length) {
		$.each(cells, function(i, c) {
			var cell = window.getCellById(c.id);
			if (light) {
				cell.backColor("lightblue");
			} else {
				cell.clearStyleProperty("backColor");
			}
		});
	}
}

function getCellById(id) {
	var r = id.split("-");
	return window.getActiveCell({
		row: r[0],
		col: r[1]
	});
}


/**
 * 保存操作
 */
function saveBtFunc(rst, fn) {
	var spread = $$("#ss").data("spread");
	var json = spread.toJSON(false);
	populateValue();
	populatePageValue();
	$.ajax({
		url: contextPath + "/mx/dep/report/depReportTemplate/saveOrUpdate",
		data: JSON.stringify({
			id: window.templateId,
			tmpJson: JSON.stringify(json),
			ruleJson: JSON.stringify(ruleJson),
			name: $("[data-field=templateName]").val()
		}),
		contentType: 'application/json;charset=UTF-8',
		type: 'POST',
		dataType: 'JSON',
		success: function(ret) {
			if (ret) {
				window.templateId = ret.id;
				!rst && alert("操作成功!");
				!!fn && fn();
			}
		}
	});
}

/**
 * 预览 (暂时弹出窗口)
 * 
 * @param o
 */
function previewBtFunc(o) {
	// var url = contextPath +
	// "/mx/form/page/viewPage?id=8f65a0393de84135bc54afa9d24dac25";
	// openWindow(url);
}

var currentTime = new Date().getTime();

function getSingleId() {
	return "id_" + (currentTime++);
}

function openWindow(o) {

	selectPageId = "28f81651f200404ea901f47ca29eb460";
	var $o = $(o),
		$input = $o.closest("div").find(".form-control"),
		$label = $o.parents("div.form-group").find("label.control-label"),
		titleLabel = $label.length ? $label.html() : "";
	var url = $input.attr("pageurl");
	if (!url) {
		alert("页面未定义!");
	} else {
		url = contextPath + url;
		selectPageId = $input.attr("pageid");
	}

	!$o.attr("id") && $o.attr("id", window.getSingleId());
	$("body").data("target", $o);

	var id = window.getSingleId();
	var SB = new StringBuffer( /* "<div class='row'><div class='col-lg-9'>" */ );
	/*SB
		.appendFormat(
			"<iframe id='{id}' onload='{onload}' width='100%' height='610' src='{url}' frameborder='no' scrolling='no'></iframe>", {
				id: id,
				url: url,
				onload: "$frameWinOnload(this)"
			});*/
	// SB.append("</div><div class='col-lg-3'>");
	var link = contextPath + '/mx/form/defined/param/outInParam?' + $.param({
		pageId: selectPageId,
		fn: "initInOutParameter",
		dynamicDataBase: true
	});
	SB.appendFormat('<div role="definedTabs"><ul class="nav nav-tabs" role="tablist">' +
		'<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">1.引用选择</a></li>' +
		'<li role="presentation"><a href="#params" aria-controls="params" role="tab" data-toggle="tab">2.引用参数定义</a></li></ul>' +
		'<div class="tab-content">' +
		'<div role="tabpanel" class="tab-pane active" id="home"><iframe id="{id}" onload="{onload}" width="100%" height="610" ' +
		'src="{url}" frameborder="no" scrolling="yes"></iframe></div>' +
		'<div role="tabpanel" class="tab-pane" id="params"><iframe id="inOutParams" width="100%" height="610" bsrc="{link}" ' +
		'frameborder="no" scrolling="yes"></iframe></div></div>', {
			id: id,
			url: url,
			onload: "$frameWinOnload(this)",
			link: link
		});
	window.appendTemplate(SB);
	// SB.append("</div></div>");

	window.openWindowDialog = window.show({
		title: '选择' + titleLabel,
		size: BootstrapDialog.SIZE_WIDE,
		message: SB.toString(),
		modal: false,
		draggable: true,
		buttons: [
			/*{
						label: 'OK!',
						cssClass: 'btn-success',
						action: function(d) {
							test();
						}
					},*/
			{
				label: 'Close!',
				cssClass: 'btn-warning',
				action: function(d) {
					d.close();
				}
			}
		],
		css: {
			width: $(window).width() * 0.6
		}
	});

}

function appendTemplate(sb) {
	// console.info(sb.toString());
}

$.extend(true, window, {
	selectPageId: null
});

function $frameWinOnload(f) {
	var $window = window.$openWindow = f.contentWindow;
	var $$ = $window.$,
		pageId = selectPageId;
	var control = window.mt.control || {};
	window.menus = {
		"in": [],
		out: [{
			text: '输出属性',
			items: [],
			level: 0
		}]
	};
	window.dataSources = {};
	//获取页面级别参数
	var gobalParamsStr = $("[name=A000-1-1-003]").data("jsonSource") /*.val()*/ ;
	if (gobalParamsStr && gobalParamsStr != "") {
		var gobalPrams = JSON.parse(gobalParamsStr);
		if (gobalPrams[0]['datas']['72916ca7b6a74e089818e69c08c3781f']) {
			var p = gobalPrams[0]['datas']['72916ca7b6a74e089818e69c08c3781f'][0]['val'];
			var items = []
			$.each(p, function(i, v) {
				items.push({
					"text": v.name,
					"id": v.param
				});
			});
		}
		var obj = {
			text: "页面参数",
			items: items
		};
		window.menus["in"].push(obj);
	}

	$$($window.document).ready(function() {
		$$("[data-role][id]").each(function() {

			var $this = $(this),
				id = this.id, //
				dataRole = $this.attr("data-role");
			if (control[dataRole]) {
				var items = fzmt.getControlParams(dataRole, id, pageId);
				if ($this.data("params")) {
					items = eval("(" + $this.data("params") + ")");
				}
				var obj = {
					text: $this.attr("cname") || $this.attr("title") || id,
					id: id,
					items: items
				};
				window.menus["in"].push(obj);
			}
		});
	});

	(function(collection) {
		var $o = $("body").data("target");

		if ($o && ($o = $o.closest(".form-group"))) {
			var items = [];
			var $input = $o.find("input[data-field][t]");
			if ($input.get(0) && $input.attr("t")) {
				items = $input.getOut() || [];
			}

			if ($o = $o.find("input:hidden")) {
				var $items = $o.getOut();
				if ($items)
					$.each($items, function(i, item) {
						if (!items.contains(item)) {
							items.push(item);
						}
					});
			}
			collection.push({
				text: '属性',
				items: items
			});
		}

		//		if ($o && ($o = $o.closest(".form-group").find("input:hidden"))) {
		//			collection.push({
		//				text : '属性',
		//				items : $o.getOut()
		//			});
		//		}
	})(window.menus["out"][0].items);

	//执行完毕刷新右侧选卡 inOutParams 输入输出参数
	var $inOutParams = $(f).parents("[role=definedTabs]").find("#inOutParams");
	$inOutParams.attr("src", $inOutParams.attr("bsrc"));
}

Array.prototype.contains = function(item) {
	var tmp = false;
	$.each(this, function(index, val) {
		if (val && (val.id == item.id)) {
			tmp = true;
			return;
		}
	});
	return tmp;
};

function test() {
	var link = contextPath + '/mx/form/defined/param/outInParam?' + $.param({
		pageId: selectPageId,
		fn: "initInOutParameter"
	});

	var msg = "<iframe width='100%' height='500' src='{url}' frameborder='no' scrolling='no'></iframe>"
		.format({
			url: link
		});

	window.testDialog = window.show({
		title: '输入&输出关系定义',
		size: BootstrapDialog.SIZE_WIDE,
		message: msg,
		modal: false,
		draggable: true,
		/*
		 * buttons : [ { label : 'OK!', cssClass : 'btn-success', action :
		 * function(d) { } }, { label : 'Close!', cssClass : 'btn-warning',
		 * action : function(d) { d.close(); } } ],
		 */
		css: {
			width: $(window).width() * 0.5
		}
	});
}

window.fzmt = {
	getControlParams: function(type, id, pageId) {
		if (!(type in mt.control))
			type = 'default';
		var f = mt.control[type];
		if ($.isFunction(f)) {
			return f(id, pageId);
		} else {
			return f;
		}
	}
};

/**
 * 获取数据集
 * @param id
 * @param pageId
 * @returns
 */
function getDataSourceSet(id, pageId) {
	var datas = null;
	$.ajax({
		url: contextPath + '/mx/form/defined/getDataSourceSet',
		data: {
			pageId: pageId || selectPageId,
			name: id
		},
		dataType: 'JSON',
		type: 'post',
		async: false,
		success: function(data) {
			datas = data;
			if (data && data[0]) {
				window.dataSources[id] = $.extend(true, {}, data[0]);
			}
		}
	});
	return datas;
}

/**
 * 获取页面级输入参数
 * @param pageId
 * @returns
 */
function getParametersByPageId(pageId) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/getParametersByPageId',
		data: {
			pageId: pageId
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}
/**
 * 
 * @param data
 * @returns {data}
 */
function initInOutParameter(data) {
	if (data && (data = data[0])) {
		var $$ = window.$openWindow.$,
			i, n, val, $dom;
		var fields = ["databaseId", "datasetId"],
			split = "_0_";

		var json = {};
		$.each(data.datas, function(k, array) {
			i = 0, collection = [];
			for (; i < array.length; i++) {
				n = array[i];
				$dom = $$("#" + n.inid);
				if ($dom && $dom.get(0)) {
					val = $dom.mtkendo(n.type, n.columnName);
					if (!$dom.data("params")) {
						if (window.dataSources[n.inid]) {
							var ds = window.dataSources[n.inid];
							window.a2b(fields, ds, n);
							if (ds && n.columnName && val) {
								var table = n.columnName.split(split)[0];
								n.idValue = val[table + split + "id"] || val[table + split + "ID"];
							}
						}
						if (val && (typeof val == 'object')) {
							n.value = val[n.columnName];
						} else {
							n.value = val;
						}
					} else {
						n.val = val;
					}
				}
			}
			var jsonStr = JSON.stringify([data]);
			// $("input[data-item='" + k + "']").val(jsonStr);
			json[k] = {
				name: n.value,
				data: jsonStr
			};
			var eid = data.eleId ? data.eleId : k,
				nameValue = "";
			$("#" + eid).data("jsonSource", jsonStr).setValue(data.name);
		});
		var o = $("body").data("target").closest(".form-group").find(
			"input[data-field]");
		try {
			if (o.get(0)) {
				var retValue = JSON.parse(o.data("jsonSource") || o.val());
				if (retValue instanceof Array) {

				} else if (retValue instanceof Object) {
					for (var key in retValue) {
						if (!json[key]) {
							json[key] = retValue[key];
						}
					}
				}
				updateFormDataRow(o, json, o.attr('data-code'));
			}
		} catch (e) {
			console.log(e);
		}

		window.testDialog && window.testDialog.close();

		window.openWindowDialog.close();
	} else {
		var datas = $("body").data("target").closest(".form-group").find(
				"input[data-field]").data("jsonSource") /*getValue()*/ ,
			isDynamic, dynamicId, dynamicName;
		if (datas) {
			var obj = JSON.parse(datas),
				v = null;
			if (obj instanceof Array) {
				v = obj[0].datas;
				isDynamic = obj[0].isDynamic;
				dynamicId = obj[0].dynamicId;
				dynamicName = obj[0].dynamicName;
			} else if (obj instanceof Object) {
				v = obj;
			}

			datas = v;
		}
		data = {
			datas: datas,
			isDynamic: isDynamic,
			dynamicId: dynamicId,
			dynamicName: dynamicName,
			menus: window.menus
		};
	}
	return data;
}

function a2b(fs, a, b) {
	if (fs && a && b) {
		$.each(fs, function(i, f) {
			b[f] = a[f];
		});
	}
}

/**
 * 页面还原
 * 
 * @param spread
 */
function showTemplate(spread) {
	if (window.templateId) {
		$.ajax({
			url: contextPath + "/mx/dep/report/depReportTemplate/getDepReportTemplate",
			data: {
				id: window.templateId
			},
			type: 'POST',
			dataType: 'JSON',
			success: function(ret) {
				if (ret && ret.tmpJson) {
					spread.fromJSON(JSON.parse(ret.tmpJson));
					ruleJson = ret.ruleJson ? JSON.parse(ret.ruleJson) : {};
					window.populatePageValue(ruleJson);
				}
			}
		});
	} else {

	}
}

window.mtxx = {
	mapping_code: 'cell_ui_mapping',
	url: contextPath + '/mx/form/defined/getDictByCode',
	getDictByCode: function(code, fn) {
		$.ajax({
			url: mtxx.url,
			data: {
				code: code
			},
			dataType: 'JSON',
			success: fn || function() {}
		});
	}
};

SpreadJs.prototype = {
	constructor: SpreadJs,
	init: function() {
		this.initEvents(); // 初始化加载事件
		this._mapping();
	},
	_mapping: function() { // 初始化UI mapping
		var types = window[mtxx.mapping_code];
		if (!types) {
			var types = window[mtxx.mapping_code] = {};
			mtxx.getDictByCode(mtxx.mapping_code, function(ret) {
				if (ret != null) {
					var i = 0,
						len = ret.length,
						v;
					for (; i < len; i++) {
						v = ret[i];
						types[v.code] = v;
					}
				}
			});
		}
	},
	initEvents: function() {
		var that = this,
			GcSpread = that.GcSpread, //
			spread = that.spread;
		showTemplate(spread);
		var Events = GcSpread.Sheets.Events;
		$.each([Events.CellClick, Events.ButtonClicked], function(i,
			clickType) {
			that.spread.bind(clickType, function(sender, args) {
				if (args.sheetArea === GcSpread.Sheets.SheetArea.colHeader) {
					console.log("The column header was clicked.");
				}

				if (args.sheetArea === GcSpread.Sheets.SheetArea.rowHeader) {
					console.log("The row header was clicked.");
				}

				if (args.sheetArea === GcSpread.Sheets.SheetArea.corner) {
					console.log("The corner header was clicked.");
				}

				populateValue(); // 保存全局变量

				var $args = GetArgs(args);

				setValue($args);

				that.openTab();
			});
		});
	},
	openTab: function() {
		if (!$("body").hasClass("page-quick-sidebar-open")) {
			$(".dropdown-toggle").click();
			window.tempGetDataSetParams();
		}
	},
	getActiveCell: getActiveCell,
	switchCellType: function(args) {
		// var that = this, cellType = args.sheet.getCellType();
		// console.log(cellType);
	}
};

var dataField = "data-field";
/**
 * 特殊属性映射
 */
var FIELDMAPPING = {
	No: 'A001-1-003',
	NAME: 'A001-1-002', // 名称
	ctrltype: "A001-1-001", // 控件类型
	position: 'A001-1-004' // 位置信息,
};

/**
 * 获取属性配置
 */
function populateValue() {
	var $No = $("[name=" + FIELDMAPPING.No + "]");
	var $ctrltype = window.getCtrlType();
	var No = $No.val(); // 单元个编号为key
	var ctrltype = $ctrltype.val();
	if (ctrltype && No) {
		var json = ruleJson[No];
		!json && (json = ruleJson[No] = {});
		$ctrltype.closest("div.form-body").find("[" + dataField + "]").each(
			function(i, v) {
				var $this = $(this),
					name = $this.attr("name");
				name && (json[name] = ($this.data("jsonSource") || $this.getValue()));
			});
	}
}


function getPageJson(No) {
	var json = {};

	json[FIELDMAPPING.ctrltype] = No;

	return json;
};

/**
 * 页面属性配置
 */
function populatePageValue(data) {
	var No = 'page';
	var json = data ? data[No] : ruleJson[No];
	!json && (json = ruleJson[No] = window.getPageJson(No));

	if (data) {
		iteratorSetValue(json);
	} else {
		window.getCtrlType().closest("div.tab-pane").siblings().find(
			"[" + dataField + "]").each(function() {

			var $this = $(this),
				name = $this.attr("name");
			name && (json[name] = ($this.data("jsonSource") || $this.getValue()) /*$this.getValue()*/ );
		});
	}

}

function iteratorSetValue(json) {
	for (var k in json) {
		var ruleVal = json[k] || '',
			val = '';
		// $("[" + dataField + "='" + k + "']").setValue(val);
		try {
			if (ruleVal != '') {
				var ruleJson = $.parseJSON(ruleVal);
				if ("name" in ruleJson[0]) {
					val = ruleJson[0].name;
				} else {
					$("[name='" + k + "']").removeData("jsonSource").setValue(ruleVal);
					continue;
				}
			}
			$("[name='" + k + "']").data("jsonSource", ruleVal).setValue(val);
		} catch (e) {
			$("[name='" + k + "']").removeData("jsonSource").setValue(ruleVal);
		}

		// $("input[data-item='" + k + "']").setValue(val);
	}
}

function getCtrlType() {
	return $("[name='" + FIELDMAPPING.ctrltype + "']");
}

function setValue(args) {
	var key = args.row + "-" + args.col,
		json;
	if (ruleJson && (json = ruleJson[key])) {
		var id = window.getCtrlType().attr( /*"id"*/ "name"); // 获取控件类型对应的id
		var type = json[id] || "textbox";
		window.setCellValue(type, function() {
			iteratorSetValue(json);
		});
	} else { // 清空
		var $ctrlType = window.getCtrlType();
		$ctrlType.closest("div.form-body").find("[" + dataField + "]").each(function(i, v) {
			var $this = $(this), // field = $this.attr(dataField),
				name = $this.attr("name"),
				val = null;
			switch (name) {
				case FIELDMAPPING.No:
					val = key;
					break;
				case FIELDMAPPING.position:
					val = JSON.stringify(args);
					break;
				default:
					break;
			}
			$this.removeData("jsonSource").setValue(val);
		});
	}
}

var sheetArgs = { // cell固定属性
		col: '',
		row: '',
		sheet: {
			name: '',
			selections: '',
			activeRow: '',
			activeCol: '',
		},
		sheetArea: '',
		sheetName: ''
	},
	ruleJson = {};

if (window.auto == "true") {
	var timer = window.setInterval(function() {
		window.saveBtFunc(true, function() {
			var dialog = window.show({
				title: '温馨提示',
				message: "系统自动保存成功!",
				autodestroy: true,
				type: 'type-success'
			});
			window.setTimeout(function() {
				dialog.close();
			}, 2000);
		});
	}, 60 * 1000); // 60秒定时保存
}

function GetArgs(args) {
	var json = JSON.parse(JSON.stringify(args));
	var v, ret = {},
		iterator = function(tmp, o, collection) {
			for (var k in tmp) {
				v = tmp[k];
				if (typeof v == 'object') {
					iterator(v, o[k], collection[k] = {});
				} else {
					collection[k] = o[k];
				}
			}
		};
	iterator(sheetArgs, json, ret);
	json = null;
	return ret;
}

function getActiveSheet() {
	var spread = $$("#ss").data("spread");
	return spread ? spread.getActiveSheet() : null;
}

function getSelections() {
	return getActiveSheet().getSelections();
}

// 获取当前选中单元格
function getActiveCell(args) {
	var c = getActiveSheet().getCell(args.row, args.col, args.sheetArea);
	return c;
}

/**
 * 获取单元格类型 var buttonType = getCellTypeByCode('button');
 */
function getCellTypeByCode(code) {
	var sheets = $window.GcSpread.Sheets;
	var mapping = window[mtxx.mapping_code];
	if (mapping[code]) {
		return new sheets[mapping[code].desc]();
	}
	return null;
}

/**
 * 获取单元格类型 var cellType = getCellTypeByValue('2');
 */
function getCellTypeByValue(value) {
	var mapping = window[mtxx.mapping_code],
		code = null;
	if (mapping) {
		$.each(mapping, function(i, v) {
			if (v.value == value) {
				code = v.code;
				return false;
			}
		});
		return !!code ? getCellTypeByCode(code) : null;
	}
	return null;
}

/**
 * 单元格id 生成规则
 */
function getCellId(args) {
	var mapping = window[mtxx.mapping_code],
		key = 'value';
	var id = "-" + args.row + "-" + args.col,
		def = 'textbox';
	if (mapping) {
		var typeName = args.sheet.getCellType().typeName,
			v = null;
		for (var k in mapping) {
			v = mapping[k];
			if (v[key] == typeName)
				break;
		}!!v && (def = v.code);
	}
	return def + id;
}

/**
 * 改变单元格类型
 * 
 * @param o
 */
function changeType(o) {
	var selections = getSelections();
	if (o.value) {
		var cellType = getCellTypeByCode(o.value);
		if (selections && selections[0]) {
			if (cellType.text) {
				cellType.text($("[name=" + FIELDMAPPING.NAME + "]").val());
			}
			$.each(selections, function(i, v) {
				var cell = getActiveCell(v);
				cell.cellType(cellType);
			});

		}
	} else {
		$.each(selections, function(i, v) {
			var cell = getActiveCell(v);
			cell.cellType(new $window.GcSpread.Sheets.BaseCellType());
			//清空定义后的规则
			delete ruleJson[cell.row + "-" + cell.col];
			setValue(cell);
		});
	}
}

var baseCtrl = {
		getOut: function() {
			return [{
				text: this.attr("title"),
				id: this.attr("data-item")
			}];
		},
		click: function(e) {

		},
		getValue: function() {
			return this.val();
		},
		setValue: function(value) {
			return this.val(value);
		}
	},
	CtrlFuncs = { // 所有特殊注册事件, getter、setter 在此扩展
		checkbox: $.extend(true, {}, baseCtrl, {
			click: function() {
				return this.val(this.get(0).checked);
			},
			setValue: function(val) {
				var dom = this.get(0);
				dom.checked = (val == "true" || val == true) ? true : false;
				return this.val(dom.checked);
			}
		}),
		openWinSelect: {
			setValue: function(val) {
				if (val) {
					//console.log(val);
				}
				return this.val(val);
			},
			getValue: function() {
				return this.val();
			}
		},
		dynamicArea: {
			setValue: function(val) {
				if (val) {
					var $this = this,
						rstValue = JSON.parse(val);

					var dataItem = $this.attr('data-item');
					$.each($('.' + dataItem), function(index, input) {
						//$(input).closest('.form-group').remove();
						$(input).closest('li.mt-list-item').remove();
					});

					$.each(rstValue, function(index, val) {
						addDataFormRow($this, val, $this
							.attr('data-code'));
					});
				}
			},
			getValue: function() {
				var $this = this,
					rstValue = [];

				var dataItem = $this.attr('data-item');
				$('.' + dataItem).each(function(index, input) {
					var v = $(input).val();
					if (v) {
						rstValue.push(JSON.parse(v));
					}
				});
				return JSON.stringify(rstValue);
			},
			getOut: function() {
				var $this = this;
				var type = $this.attr('data-code');
				var templData = staticHtmlTemplate[type];

				var items = [];
				for (var key in templData) {
					var colTempl = templData[key];
					if (colTempl.isData) {
						items.push({
							text: colTempl.text || key,
							id: key
						});
					}
				}
				return items;
			},
			setOutValue: function(json) {
				// json:{'col-1':'','col-2':''...}
				var $this = this;
				var retValue = {};
				for (var key in json) {
					retValue[key] = {
						name: json[key]
					};
				}

				var type = $this.attr('data-code');
				updateFormDataRow($this, retValue, type);
			}
		},
		base: $.extend(true, {

		}, baseCtrl)
	};

$.fn.extend({
	getCtrl: function(type) {
		var t = this.attr("t");
		var ctrl = $.extend(true, {}, CtrlFuncs.base, CtrlFuncs[t]);
		return !!ctrl ? ctrl[type] : null;
	},
	getValue: function() {
		var fn = this.getCtrl("getValue");
		return !!fn ? fn.call(this) : null;
	},
	setValue: function(val) {
		var fn = this.getCtrl("setValue");
		!!fn && fn.call(this, val);
		return this;
	},
	getOut: function() {
		var fn = this.getCtrl("getOut");
		return !!fn ? fn.call(this) : null;
	}
});

/**
 * 选择更新集回调函数
 * 
 * @param data
 * @returns
 */
function viewSourceSetFunc(data) {
	var $this = $(this),
		$openWin, col2 = "col-2";
	var $hidden = window.getHidden(this),
		str = $hidden.val();
	var json = JSON.parse(str);
	if (data) { // 返回
		var linkageControl = [];
		$.each(data.columns, function() {
			linkageControl.push({
				id: this.id,
				text: this.text
			});
		});
		json[col2] = {
			name: data.wdataSet.dataSetName,
			data: {
				linkageControl: JSON.stringify(linkageControl),
				wdataSet: data.wdataSet,
				tableMsg: JSON.stringify([{
					name: data.table.name,
					table: data.table,
					dataSetId: data.dataSetId,
					columns: data.columns
				}])
			},
			other: {
				results: data.results
			}
		};
		window.updateFormDataRow(this, json, "data_save_area");
		($openWin = $this.data("$openWin")) && $openWin.close();
	} else { // 获取数据
		return json[col2].data || window.getSelectCellData();
	}
	return null;
}

function getHidden(o) {
	return $(o).closest(".form-group").find("input:hidden");
}

function getSelectCellData() {
	var linkageControl = [];
	$.each(window.getSelectionCells(), function(i, c) {
		var id = c.row + "-" + c.col;
		if (id in ruleJson) {
			linkageControl.push({
				id: id,
				text: ruleJson[id][FIELDMAPPING.NAME]
			});
		}
	});
	return {
		linkageControl: JSON.stringify(linkageControl)
	};
}

/**
 * 获取选择的所有单元格
 */
function getSelectionCells() {
	var selections = window.getSelections();
	var ri, ci, row, col, cells = [];
	$.each(selections, function(si, s) {
		row = s.row;
		for (ri = 0; ri < s.rowCount; ri++) {
			col = s.col;
			for (ci = 0; ci < s.colCount; ci++) {
				var cell = window.getActiveCell({
					row: row,
					col: col
				});
				cells.push(cell);
				col++;
			}
			row++;
		}
	});
	return cells;
}

/**
 * 获取更新集后台参数
 * @param wdataSetIds
 * @param fn
 */
function getWDataSetParams(wdataSetIds, fn) {
	$.ajax({
		url: contextPath + "/rs/dep/base/depBaseWdataSet/getWDataSetParams",
		type: 'POST',
		dataType: 'JSON',
		async: true,
		data: {
			datasetIds: wdataSetIds
		},
		success: function(ret) {
			if (fn) {
				fn(ret);
			}
		}
	});
}

/**
 * 获取数据集后台参数
 * @param datasetIds
 * @param fn
 */
function getDataSetParams(datasetIds, fn) {
	$.ajax({
		url: contextPath + "/rs/dataset/getDataSetParams",
		type: 'POST',
		dataType: 'JSON',
		async: true,
		data: {
			datasetIds: datasetIds
		},
		success: function(ret) {
			if (fn) {
				fn(ret);
			}
		}
	});
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
function getCellPositionStr(rowNum, colNum) {
	var u = '',
		pos = '',
		v,
		row = rowNum + 1,
		col = colNum + 1;

	var sheet = getActiveSheet();
	switch (sheet.referenceStyle()) {
		case 0:
			while (col > 0)
				v = col % 26, v == 0 ? (pos = "Z" + pos, col--) : pos = String.fromCharCode("A".charCodeAt(0) + v - 1) + pos, col = parseInt((col / 26).toString());
			pos += row.toString();
			break;
		case 1:
			pos = "R" + row.toString() + "C" + col.toString();
			break;
		default:
			break
	}

	return pos;
}

/**
 * 获取区域单元格位置字符串，如E10
 * 
 * @param {[type]}
 *            selections [description]
 * @return {[type]} [description]
 */
function getRangeCellPositionStr(selections) {
	var sobj = selections[selections.length - 1];
	var start = {
			row: sobj.row,
			col: sobj.col
		},
		end = {
			row: sobj.row + sobj.rowCount - 1,
			col: sobj.col + sobj.colCount - 1
		};

	if (sobj.rowCount > 1 || sobj.colCount > 1) {
		return getCellPositionStr(start.row, start.col) + ':' + getCellPositionStr(end.row, end.col);
	} else {
		return getCellPositionStr(start.row, start.col);
	}

}

/** ********************************** */
/** rocky add 2016-04-09 --start-- ** */
/** ********************************** */

/**
 * 属性初始化
 */
readyFuncs.push(function() {
	$.post(contextPath + '/rs/form/spreadjs/readAttribute', {
		systemType: systemType
	}, function(data) {
		var tabs = $('#componentAttr').tabs({
			width: 'auto',
			height: 'auto',
			reversed: false,
			border: true,
			fit: false,
			tabdrop: false,
			tabPosition: 'right',
			tabStyle: 'tabs',
			cols: [10, 2]
		});
		var gehangse = 0;
		// 循环分类
		$.each(data, function(index, d) {

			var form = $('<form class="form-horizontal" role="form">');
			var formbody2 = $('<div class="form-body">').appendTo(form);
			var outWrap = $('<div class="mt-element-list"></div>').appendTo(formbody2);
			var formbody = $('<div class="mt-list-container list-simple ext-1 group"></div>').appendTo(outWrap);
			//debugger;
			// 循环属性分类
			$.each(d.propertyPackages, function(index, propertyPackage) {
				gehangse = index % 3;
				//debugger;
				// 添加分类行
				var formgroup = addCategoryFormRow(propertyPackage, d);
				//formgroup.appendTo(formbody);
				var formgroupWrap = $('<a class="list-toggle-container" data-toggle="collapse" href="#list' + propertyPackage.id + '" aria-expanded="' + (propertyPackage.expandFlag == "1" ? 'true' : 'false') + '"></a>');
				var divWrap = $('<div class="list-toggle ' + (gehangse == 0 ? '' : ('done' + gehangse)) + ' uppercase"></div>');
				formgroup.appendTo(divWrap);
				divWrap.appendTo(formgroupWrap);
				formgroupWrap.appendTo(formbody);

				var proWrap = $('<div class="panel-collapse collapse ' + (propertyPackage.expandFlag == "1" ? 'in' : '') + '" id="list' + propertyPackage.id + '" aria-expanded="' + (propertyPackage.expandFlag == "1" ? 'true' : 'false') + '" style=""></div>');
				var ulWrap = $('<ul></ul>');
				ulWrap.appendTo(proWrap);
				proWrap.appendTo(formbody);

				// 循环属性
				$.each(propertyPackage.attributes, function(index, _attr) {
					var formgroupAttr = addAttrFormRow(_attr, d);
					//formgroupAttr.appendTo(formbody);
					var liWrap = $('<li class="mt-list-item ' + (gehangse == 0 ? '' : ('done' + gehangse)) + '"></li>');
					formgroupAttr.appendTo(liWrap);
					liWrap.appendTo(ulWrap);

				});
			});
			// form为整个tab的内容。拼接form后插入tab
			$('#componentAttr').tabs('add', {
				id: d.id,
				content: form,
				title: d.name,
				closeable: false,
				selected: false,
				isDropdownMenu: false
			});

		});
		$('#componentAttr').tabs('select', 0); // 选中第一个

		resizePropertiesMenu();

		showHide("textbox");

	}, 'json');
});

/**
 * 获取参数
 * @returns
 */
function tempGetDataSetParams() {
	var dataSetCls = ".602",
		dataSetClsByExpression = ".2205", //表达式模式下
		dataSetClsByExcell = ".2409",
		paraType = "72916ca7b6a74e089818e69c08c3781f";
	var $602 = $(dataSetCls).length ? $(dataSetCls) : ($(dataSetClsByExpression).length ? $(dataSetClsByExpression) : $(dataSetClsByExcell));
	if ($602.get(0) && !$602.data("isInit")) {
		var ids = [],
			idTmp = {};
		$602.each(function(i, v) {
			if (v.value) {
				try {
					var json = JSON.parse(v.value);
					if (json["col-2"]) {
						var data = json["col-2"].data;
						if (data) {
							var tableMsg = JSON.parse(data.tableMsg || "[]");
							if (tableMsg && (tableMsg = tableMsg[0])) {
								var dataSetId = tableMsg.dataSetId;
								dataSetId && !(idTmp[dataSetId]) && (idTmp[dataSetId] = dataSetId) && (ids.push(dataSetId));
							}
						}
					}
				} catch (e) {
					console.log(e);
				}
			}
		});

		if (ids.length) {
			window.getDataSetParams(ids.join(","), function(ret) {
				if (ret && ret.length) {
					var $paraType = $("#" + paraType),
						key = "jsonSource";
					if ($paraType.get(0)) {
						var json, datas = {};
						var params = datas[paraType] =
							[{
								"inid": "grid",
								"inname": "grid-->获取-->所有行",
								"columnName": "",
								"outname": "属性-->输入形参定义",
								"outid": paraType,
								"type": "getAll",
								"val": ret
							}]
						if (json = $paraType.data(key)) {
							json = JSON.parse(json);
							if (json) {
								var datas = json[0].datas;
								var o = datas[paraType];
								if (o) {
									var val = o[0].val,
										vals = ret;
									if (val && val.length) {
										var paramsObj = {};
										vals = $.extend([], val);
										$.each(val, function(i, v) {
											paramsObj[v.param] = v;
										});
										$.each(ret, function(i, v) {
											!paramsObj[v.param] && (vals.push(v));
										});
									}
									o[0].val = vals;
								}
							}
						} else {
							json = [{
								name: '输入输出关系',
								isDynamic: 'true',
								dynamicName: '',
								eleId: paraType,
								datas: datas
							}];
						}

						$paraType.data(key, JSON.stringify(json))
					}
				}
			});
		}

		$602.data("isInit", true);
	}
}

// 右边属性绑定事件
readyFuncs.push(function() {
	window.setTimeout(function() {
		$(".tab-content").on("click.properties", ".form-control", function(e) {
			var $this = $(this),
				t = $this.attr("t"),
				ctrl;
			!!(ctrl = CtrlFuncs[t]) && !!ctrl.click && ctrl.click.call($this, e);
		});
	}, 1000);
});

function resizePropertiesMenu() {
	$(".tab-content").css({
		"overflow": 'auto',
		height: $(document).height()
	});
}
resizeFuncs.push(resizePropertiesMenu);

/**
 * 创建表单组
 * 
 * @param {[type]}
 *            customClass 自定义class样式
 * @return {[type]} [description]
 */
function createFormGroup(customClass, category) {
	var formgroup = $('<div>').attr({
		'data-item': category ? category.id : '',
		'data-field': category ? category.id : '',
		'data-code': category ? category.code : '',
		'name': category ? category.code : '',
		categoryid: category ? category.id : '', // 用于区分是否为页面属性还是控件属性
		class: 'form-group ' + customClass,
		style: 'height:20px'
	});
	return formgroup;
}

/**
 * 添加分组行
 * 
 * @param {[type]}
 *            category [description]
 */
function addCategoryFormRow(attrCategory, category) {
	var formgroup = createFormGroup('common-group', attrCategory);
	if (category && category.id) {
		// 覆盖categoryid为分组，而不是属性分组
		// 用于区分是否为页面属性还是控件属性
		formgroup.attr('categoryid', category.id);
	}
	$('<label class="col-md-5">  <i class="fa fa-plus-square-o"></i> ' + attrCategory.title + '</label>').appendTo(
		formgroup);

	if (attrCategory.toolBarTemplate) {
		formgroup.attr('t', 'dynamicArea');
		$('<div>').attr({
			class: 'col-md-7',
			style: 'text-align:left'
		}).html(attrCategory.toolBarTemplate).appendTo(formgroup);
	}

	return formgroup;
}

window.componentJSON = {}; // 组件HTML模板JSON
/**
 * 添加属性行
 * 
 * @param {[type]}
 *            attr [description]
 * @param category
 *            分类树的分类
 */
function addAttrFormRow(attr, category) {
	var extJson = JSON.parse(attr.extJson);
	var components = attr.components;

	var commonRule = extJson.commonRule ? "common-rule" : "";
	var formgroup = createFormGroup(commonRule, category);

	var customAttr = extJson.customAttr || {};
	if (customAttr.isHidden) {
		formgroup.attr('hidden', true);
	}

	// 添加支持的组件样式
	// 用于在切换某种组件的时候，只显示支持该组件的属性
	$.each(components, function(index, val) {
		formgroup.addClass(val.type + '-cls');
		if (!window.componentJSON[val.type]) {
			window.componentJSON[val.type] = val.htmlTemplate;
		}
	});

	$('<label class="col-md-5 control-label">' + attr.name + '</label>')
		.appendTo(formgroup);

	// 每个组件加入隐藏框保存数据
	var $valueCol = $('<div class="col-md-7" style="text-align:left;">')
		.appendTo(formgroup).append($("<input>", {
			"data-item": attr.id,
			"title": attr.name,
			type: 'hidden'
		}));

	var html = window.componentJSON[attr.type];
	var $_dom = $('<div>' + html + '</div>').appendTo($valueCol);

	var $btn = $_dom.find(".input-sm");
	var $dom = $_dom.find(".input-value");

	if (attr.values) {
		var comboboxValues = attr.values;
		for (var n in comboboxValues) {
			$('<option>').attr('value', comboboxValues[n].code).text(
				comboboxValues[n].text).appendTo($dom);
		}
	}
	if ($btn.get(0)) {
		$btn.attr({
			"data-field": attr.id
		});
	}
	if ($dom.get(0)) {
		var objAttrs = $.extend({}, extJson.customAttr || {}, {
			t: attr.type,
			id: attr.id,
			name: attr.ruleCode,
			"data-field": attr.id,
			class: 'form-control input-sm',
			readonly: attr.readOnly == "0" ? false : true,
			value: attr.defaultVal,
			pageurl: extJson.pageUrl,
			pageid: extJson.pageId
		});
		$dom.attr(objAttrs);
	}

	return formgroup;
}

/**
 * 静态的html模板配置 值 ：{key:value} key=col-1等，要与数据data中的key一致
 * 
 * @type { dynamic_area:变长区 { 'col-1':{ colWidth:宽度，一行宽度总和为12
 *       isData:判断是否数据，还是按钮等,true为数据，可用于弹窗返回时赋值 type:类型，如select、button等
 *       htmlTempl:html模板，如<select>，生成时通过$(htmlTempl)生成 classStyle:class样式表,
 *       optionValues:为select时的option值 [ {text:'',value:''} ]
 *       text:为button等类型时显示的文本, hidden:是否为隐藏 clickFunc:单击回调函数 } },
 *       dynamic_data：变长区数据, tb_lineNum:表格栏数, args_data：引入表数据, sign:签名/盖章,
 *       chart:工程图表, data_valid:数据判断, atta_field:附表字段 }
 */
var staticHtmlTemplate = {
	'dynamic_area': {
		'col-1': {
			colWidth: 3,
			isData: true,
			text: '方向',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm input-zpadding',
			optionValues: [{
				text: '左右',
				value: 'bc_left_right'
			}, {
				text: '上下',
				value: 'bc-top-bottom'
			}]
		},
		'col-2': {
			colWidth: 3,
			text: '位置',
			classStyle: 'input-zpadding',
			isData: true
		},
		'col-3': {
			colWidth: 4,
			isData: true,
			text: '动态',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm input-zpadding',
			optionValues: [{
				text: '--请选择--',
				value: ''
			}, {
				text: '动态多行',
				value: 'dynamic'
			}, {
				text: '固定多行',
				value: 'fixed'
			}]
		},
		'col-4': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-sm',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'dynamic_data': {
		'col-1': {
			colWidth: 3,
			isData: true
		},
		'col-2': {
			colWidth: 3,
			isData: true
		},
		'col-3': {
			colWidth: 4,
			isData: true
		},
		'col-4': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'data_save_area': {
		'col-1': {
			colWidth: 3,
			text: '位置',
			isData: true
		},
		'col-2': {
			colWidth: 3,
			text: '引用信息',
			isData: true
		},
		'col-3': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '添加',
			clickFunc: function(o) {
				var cd = window.getSelectCellData(), //
					linkageControl = JSON.parse(cd.linkageControl);
				if (linkageControl.length) {
					var $hidden = window.getHidden(o);
					var data = JSON.parse($hidden.val() || JSON.stringify({})),
						col2;
					if ((col2 = data["col-2"])) {
						var tmp = {},
							lcs = [];
						if (col2.data && col2.data.linkageControl) {
							$.each(JSON.parse(col2.data.linkageControl),
								function(i, v) {
									tmp[v.id] = v;
									lcs.push(v);
								});
						} else {
							col2.data = {};
						}
						$.each(linkageControl, function(i, cell) {
							!tmp[cell.id] && (lcs.push(cell));
						});
						col2.data.linkageControl = JSON.stringify(lcs);
						tmp = null;
					}
					$hidden.val(JSON.stringify(data));
				} else {

				}
			}
		},
		'col-4': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs mt-relate',
			text: '关联',
			clickFunc: function(o) {
				!o.id && ($(o).attr("id", window.getSingleId()));
				var url = contextPath + "/mx/form/defined/ex/tableMgr?" + $.param({
					targetId: o.id,
					fn: 'viewSourceSetFunc',
					isBind: true,
					type: "update"
				});
				$.data(o, "$openWin", window.open(url));
			}
		},
		'col-5': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '删除',
			clickFunc: removeDataFormRow
		},
		'col-6': {
			text: '查看',
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			clickFunc: function(o) {
				var $input = $(o).closest("div[data-field]").find("input:hidden");
				if ($input.val()) { //查看数据集
					try {
						var json = JSON.parse($input.val()),
							dataSetId;
						if ((json = json["col-2"]) && (json = json.data) && //
							(json = json.tableMsg)) {
							json = JSON.parse(json);
							json && (json = json[0]) && (dataSetId = json.dataSetId);
							dataSetId && (window.open(contextPath + //
								"/mx/dataset/sqlbuilder?id=" + dataSetId));
						}
					} catch (e) {
						console.log(e);
						alert('未找到数据集!');
					}
				}
			}
		},
		'col-7': {
			text: '关联数据集',
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			clickFunc: function(o) {
				!o.id && ($(o).attr("id", window.getSingleId()));

				var data = viewSourceSetFunc.call(o);

				if (data && data.linkageControl) {
					try {
						data = JSON.parse(data.linkageControl);
						$.each(data, function(i, v) {
							v.value = v.id;
						});
						cellFieldsFunc.update(data, o);
						selectDatasetByCommon('multipleform', o.objElementId, o.nameElementId,
							"", o.pageId, "cellFieldsFunc");
					} catch (e) {
						console.log(e);
					}
				}
			}
		},
		'col-8': {
			colWidth: 6,
			isData: true,
			text: '强制分页',
			type: 'checkbox',
			htmlTempl: '<label><input type="checkbox" title="用来设置数据集强制分页">强制分页</label>',
			classStyle: '',
			clickFunc: function(o) {
				var $checkbox = $(o);
				if (o.nodeName != "INPUT") {
					$checkbox = $(o).find("[type=checkbox]");
				}
				$checkbox.attr("checked") ? $checkbox.removeAttr('checked') : $checkbox.attr("checked", "true");
				var $hiddenInput = $(o).closest("[categoryid]").find("input:hidden:first"),
					value = $hiddenInput.val(),
					jsonVal = JSON.parse(value);
				jsonVal["col-8"] = $checkbox.attr("checked");
				$hiddenInput.val(JSON.stringify(jsonVal));
			}
		},
		'col-9': {
			colWidth: 2,
			isData: true,
			text: '分栏',
			type: 'checkbox',
			htmlTempl: '<label><input type="checkbox" title="分栏">分栏</label>',
			classStyle: '',
			clickFunc: function(o) {
				var $checkbox = $(o);
				if (o.nodeName != "INPUT") {
					$checkbox = $(o).find("[type=checkbox]");
				}
				$checkbox.attr("checked") ? $checkbox.removeAttr('checked') : $checkbox.attr("checked", "true");
				var $hiddenInput = $(o).closest("[categoryid]").find("input:hidden:first"),
					value = $hiddenInput.val(),
					jsonVal = JSON.parse(value);
				jsonVal["col-9"] = $checkbox.attr("checked");
				$hiddenInput.val(JSON.stringify(jsonVal));
			}
		}
	},
	'tb_lineNum': {
		'col-1': {
			colWidth: 3,
			text: '位置',
			isData: true
		},
		'col-2': {
			colWidth: 4,
			text: '说明',
			isData: true
		},
		'col-3': {
			colWidth: 3,
			text: '引用信息',
			isData: true
		},
		'col-4': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'args_data': {
		'col-1': {
			text: '位置',
			colWidth: 3,
			isData: true
		},
		'col-2': {
			text: '说明',
			colWidth: 3,
			isData: true
		},
		'col-3': {
			text: '引用数据',
			colWidth: 4,
			isData: true
		},
		'col-4': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'sign': {
		'col-1': {
			text: '位置',
			colWidth: 2,
			isData: true
		},
		'col-2': {
			text: '角色',
			colWidth: 3,
			isData: true
		},
		'col-3': {
			text: '说明',
			colWidth: 3,
			isData: true
		},
		'col-4': {
			text: '自动签名',
			colWidth: 2,
			isData: true
		},
		'col-5': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'chart': {},
	'data_valid': {
		'col-1': {
			colWidth: 4,
			text: '判断条件',
			isData: true
		},
		'col-2': {
			colWidth: 6,
			text: '提示信息',
			isData: true
		},
		'col-3': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'atta_field': {},
	'params-defined': {
		'col-1': {
			colWidth: 3,
			text: '位置',
			isData: true
		},
		'col-2': {
			colWidth: 0,
			text: '引用信息',
			isData: true
		},
		'col-3': {
			colWidth: 3,
			colStyle: {
				'margin-left': 0,
				'margin-right': 0,
				'padding-left': 0,
				'padding-right': 0,
			},
			isData: true,
			text: '',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm',
			optionValues: systemType == "E000" ? [{
				text: '输入',
				value: 'input'
			}] : [{
				text: '输入',
				value: 'input'
			}, {
				text: '输出',
				value: 'output'
			}],
			changeFunc: function(o) {
				var $hidden = window.getHidden(o);
				var data = JSON.parse($hidden.val() || JSON.stringify({})),
					col2, col3,
					thisVal = {
						name: $(o).val()
					};
				data["col-3"] = thisVal;
				if ((col2 = data["col-2"])) {
					if (col2.data) {
						col2.data.type = thisVal;
					} else {
						col2.data = {};
					}
					$hidden.val(JSON.stringify(data));
				}

			}
		},
		'col-4': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '添加',
			clickFunc: function(o) {
				var cd = window.getSelectCellData(), //
					linkageControl = JSON.parse(cd.linkageControl);
				if (linkageControl.length) {
					var $hidden = window.getHidden(o);
					var data = JSON.parse($hidden.val() || JSON.stringify({})),
						col2;
					if ((col2 = data["col-2"])) {
						var tmp = {},
							lcs = [];
						if (col2.data && col2.data.linkageControl) {
							$.each(JSON.parse(col2.data.linkageControl),
								function(i, v) {
									tmp[v.id] = v;
									lcs.push(v);
								});
						} else {
							col2.data = {};
						}
						$.each(linkageControl, function(i, cell) {
							!tmp[cell.id] && (lcs.push(cell));
						});
						col2.data.linkageControl = JSON.stringify(lcs);
						tmp = null;
					}
					$hidden.val(JSON.stringify(data));
				} else {

				}
			}
		},
		'col-5': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs mt-relate',
			text: '查看',
			clickFunc: function() {}
		},
		'col-6': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-xs',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'data_pageset': {
		'col-1': {
			colWidth: 5,
			isData: true,
			text: '方向',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm',
			optionValues: [{
				text: '页码',
				value: 'pageNo'
			}, {
				text: '页数',
				value: 'pageTotal'
			}, {
				text: '总数',
				value: 'total'
			}]
		},
		'col-2': {
			colWidth: 5,
			text: '位置',
			isData: true
		},
		'col-3': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-sm',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'data_merge': {
		'col-1': {
			colWidth: 5,
			isData: true,
			text: '模式',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm',
			optionValues: [{
				text: '左右合并',
				value: 'left_right'
			}, {
				text: '上下合并',
				value: 'top_buttom'
			}, {
				text: '全部合并',
				value: 'total'
			}]
		},
		'col-2': {
			colWidth: 5,
			text: '位置',
			isData: true
		},
		'col-3': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-sm',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'data_autosize': {
		'col-1': {
			colWidth: 5,
			isData: true,
			text: '模式',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm',
			optionValues: [{
				text: '自动行高',
				value: 'autoRow'
			}, {
				text: '自动列宽',
				value: 'autoColumn'
			}]
		},
		'col-2': {
			colWidth: 5,
			text: '位置',
			isData: true
		},
		'col-3': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-sm',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
	'data_print': {
		'col-1': {
			colWidth: 5,
			isData: true,
			text: '模式',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm',
			optionValues: [{
				text: '打印区域',
				value: 'print_area'
			}, {
				text: '重复打印',
				value: 'print_repeat'
			}]
		},
		'col-2': {
			colWidth: 5,
			text: '位置',
			isData: true
		},
		'col-3': {
			colWidth: 2,
			type: 'button',
			htmlTempl: '<button>',
			classStyle: 'btn blue btn-sm',
			text: '删除',
			clickFunc: removeDataFormRow
		}
	},
};

/**
 * 关联数据集
 */
var cellFieldsFunc = (function() {
	var fields_ = null,
		fieldsObj = {},
		_target = null;
	var func = function(data) {
		if (!data) {
			return fields_;
		} else {
			(function(d) {
				console.log(d);

				var results = d.results[0];

				var result = {
					columns: [],
					dataSetId: results.selectDatasource[0].datasetId,
					table: {},
					wdataSet: {
						dataSetName: results.name
					},
					results: d.results
				};
				$.each(results.selectColumns, //
					function(i, v) {
						if (v.ctype) { //选择已经关联的字段
							result.columns.push({
								dType: v.FieldType,
								fieldId: null,
								fieldName: v.columnLabel,
								id: v.ctype,
								strlen: v.FieldLength,
								text: fieldsObj[v.ctype].text
							});
						}
					});

				viewSourceSetFunc.call(_target, result);

			})(data)
			this.close && this.close();
		}
	};

	func.update = function(fields, target) {
		try {
			fields_ = fields;
			_target = target;
			fieldsObj = {};
			$.each(fields, function(i, v) {
				fieldsObj[v.id] = v;
			});

			var $this = $(target),
				col2 = "col-2";
			var $hidden = window.getHidden(target),
				str = $hidden.val();

			if (str) {
				var id = target.id + "_";
				target.objElementId = id;
				var json = JSON.parse(str);
				var data = json[col2];
				!($("#" + id).get(0)) && ($("<input>", {
					type: 'hidden',
					id: id
				}).appendTo("body"));

				data.other && $("#" + id).val(JSON.stringify(data.other.results));
			}

		} catch (e) {
			fields_ = [];
			fieldsObj = {};
		}
	};

	return func;
})();

function selectDatasetByCommon(dictTreeCode, resultElementId, tablenameElementId, elementId, pageId, fn) {
	var flag = false;
	if (elementId.indexOf("ztree") != (-1)) {
		flag = true;
	}
	var link = contextPath + '/mx/form/defined/table/common_datasource?pageId=' + pageId;

	link = link + '&' + $.param({
		dictTreeCode: dictTreeCode,
		resultsElementId: resultElementId,
		/*tablenameElementId: tablenameElementId,
		flag: flag,*/
		retFn: fn,
		fn: fn
	});


	window.open(link);

	return;

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

/**
 * 添加多条数据行
 * 
 * @param {[type]}
 *            target 按钮父级.form-group节点
 * @param {[type]}
 *            datas 数据
 */
function addDataFormRows(target, datas, type) {
	$.each(datas, function(index, val) {
		addDataFormRow(target, val, type);
	});
}

/**
 * 添加一条数据行
 * 
 * @param {[type]}
 *            target [description]
 * @param {[type]}
 *            data [description]
 */
function addDataFormRow(target, data, type) {
	var targetItem = target.attr("data-item"),
		$targetUl = $("#list" + targetItem).removeClass('in').find("ul"),
		$toggleDiv = target.parents("div.list-toggle"),
		done = $toggleDiv.hasClass("done1") ? 1 : $toggleDiv.hasClass("done2") ? 2 : 0,
		$li = $("<li class='mt-list-item'></li>").appendTo($targetUl);
	if (done == 1) {
		$li.addClass("done1");
	} else if (done == 2) {
		$li.addClass("done2");
	}

	var formgroup = createFormGroup('common-group');
	formgroup.attr('categoryid', target.attr('categoryid'));

	$("<input>").attr({
		type: 'hidden',
		value: JSON.stringify(data),
		'class': target.attr('data-item')
	}).appendTo(formgroup);

	var template = staticHtmlTemplate[type];
	var count = 0;
	for (var key in template) {
		var col_data = template[key];
		var dataValue = data[key] ? data[key].name : data[key];

		count += col_data['colWidth'];
		var $col = $(
			'<div class="col-md-' + col_data['colWidth'] + '" style="text-align:center;">').css(col_data['colStyle'] || {}).appendTo(formgroup);
		$col.attr({
			colnumber: key,
			id: getSingleId()
		});

		if (col_data['hidden']) {
			$col.hide();
		}

		if (col_data['htmlTempl']) {
			var $tg = $(col_data['htmlTempl']).appendTo($col);
			$tg.attr('type', key); // 将key放入button属性中，方便在绑定事情时取得

			if (col_data['classStyle']) {
				$tg.addClass(col_data['classStyle']);
			}

			if (col_data['clickFunc']) {
				$tg.on('click', function(event) {
					var attrType = $(event.target).attr('type');
					if (attrType == "checkbox") {
						attrType = $(event.target).parent().attr('type');
					} else {
						event.preventDefault();
					}
					template[attrType]['clickFunc'].call(event, event.target);
				});
			}

			// 以下为单独处理内容
			if (col_data['type'] === 'select') {
				$.each(col_data['optionValues'], function(index, val) {
					$tg.append('<option value="' + val.value + '" ' + (dataValue == val.value ? 'selected' : '') + ' >' + val.text + '</option>');
				});
				if (col_data['changeFunc']) {
					$tg.on('change', function(event) {
						event.preventDefault();
						template[$(event.target).attr('type')]['changeFunc'].call(event, event.target);
					});
				} else {
					$tg.on('change', function(event) {
						// 选择事件
						var $target = $(event.target);
						var value = $target.children('option:selected').val();
						var inputs = $target.closest('.form-group').find("input:hidden");
						var json = JSON.parse(inputs.val());
						json[$target.attr('type')] = {
							name: value
						};
						inputs.val(JSON.stringify(json));
					});
				}

			} else if (col_data['type'] === 'button') {
				$tg.text(col_data['text']);
			} else if (col_data['type'] === 'textbox') {
				$tg.val(dataValue);
			} else if (col_data['type'] === 'checkbox') {
				data[key] && $tg.find("input[type=checkbox]").attr("checked", "checked");
			}
		} else {
			$col.css({
				'white-space': 'nowrap',
				'text-overflow': 'ellipsis',
				'overflow': 'hidden'
			}).text(dataValue).attr('title', dataValue);
		}

	}

	if (count > 12) {
		formgroup.height("40px");
	}

	//formgroup.insertAfter(target);
	formgroup.appendTo($li);

	return formgroup;
}

/**
 * 更新一行数据
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            data [description]
 * @return {[type]} [description]
 */
function updateFormDataRow(o, data, type) {
	var target = $(o).closest('.form-group');
	var inputs = target.find("input:hidden");
	$(inputs).val(JSON.stringify(data));

	var template = staticHtmlTemplate[type];
	if (!template) {
		return false;
	}
	for (var key in data) {
		var col_data = template[key];
		var $col = target.find('div[colnumber="' + key + '"]');
		var dataValue = data[key] ? data[key].name : data[key];

		if (col_data['htmlTempl']) {
			// 以下为单独处理内容
			var $tg;
			if (col_data['type'] === 'select') {
				$tg = $col.find('select');
				$.each($tg.find('option'), function(index, opt) {
					if ($(opt).val() == dataValue) {
						$(opt).attr('selected', 'selected');
					}
				});
			} else if (col_data['type'] === 'button') {
				// 按钮不更新
				// $tg = $col.find('button');
				// $tg.text(col_data['text']);
			} else if (col_data['type'] === 'textbox') {
				$tg = $col.find('input');
				$tg.val(dataValue);
			}
		} else {
			$col.html(dataValue);
		}
	}
}

/**
 * 移除一行
 * 
 * @param {[type]}
 *            o 删除按钮对象
 * @return {[type]} [description]
 */
function removeDataFormRow(o) {
	//var target = $(o).closest('.form-group');
	var target = $(o).closest('li.mt-list-item');
	target.remove();
}

/**
 * 打开保存区域配置窗口页面
 * 
 * @param {[type]}
 *            o 保存按钮对象
 * @param {[type]}
 *            type data_save_area
 * @return {[type]} [description]
 */
function openSaveAreaDialog(o, type) {
	var target = $(o).closest('.form-group'); // 行对象
	!target.attr("id") && target.attr("id", window.getSingleId());
	var SB = new StringBuffer();
	var url = contextPath + "/mx/form/defined/ex/tableRelation?fn=getSaveAreaDialogData&targetId=" + target.attr("id");
	SB
		.appendFormat(
			"<iframe id='{id}' onload='{onload}' width='100%' height='610' src='{url}' frameborder='no' scrolling='no'></iframe>", {
				id: window.getSingleId(),
				url: url,
				onload: ""
			});
	window.openWindowDialog = window.show({
		title: '配置主从表关系',
		size: BootstrapDialog.SIZE_WIDE,
		message: SB.toString(),
		modal: false,
		draggable: true,
		buttons: [{
			label: 'OK!',
			cssClass: 'btn-success',
			action: function(d) {
				// test();
				var cw = d.$modalBody.find("iframe")[0].contentWindow;
				cw.$parent[cw.fnName].call(cw.$parent.document.getElementById(cw.targetId), cw.getSaveData());
				d.close();
			}
		}, {
			label: 'Close!',
			cssClass: 'btn-warning',
			action: function(d) {
				d.close();
			}
		}],
		css: {
			width: $(window).width() * 0.6
		}
	});
}

function getSaveAreaDialogData(data) {
	var col2 = "col-2";
	if (data) {
		var val = $(this).getValue();
		if (val) {
			var jsons = JSON.parse(val);
			if (jsons && jsons.length) {
				var retJsons = [];
				$.each(jsons, function(i, json) {
					if (typeof json == "string") {
						json = JSON.parse(json);
					}
					var col, msg, tabRelation;
					if (col = json[col2]) {
						if (col.data && (msg = col.data.tableMsg)) {
							msg = JSON.parse(msg)[0];
							tabRelation = data[msg.table.tableName];
							if (tabRelation) {
								msg.table.topId = tabRelation.tableId;
								msg.table.isSubTable = "1";
							}
							col.data.tableMsg = JSON.stringify([msg]);
						}
					}
					retJsons.push(json);
				});
				$(this).setValue(JSON.stringify(retJsons));
			}
		}

	} else {
		var val = $(this).getValue();
		if (val) {
			var jsons = JSON.parse(val);
			if (jsons && jsons.length) {
				var collection = [];
				$.each(jsons, function(i, json) {
					if (typeof json == "string") {
						json = JSON.parse(json);
					}
					var col, msg;
					if (col = json[col2]) {
						if (col.data && (msg = col.data.tableMsg)) {
							msg = JSON.parse(msg)[0];
							collection.push(msg.table);
						}
					}
				});
				return collection;
			}
		}
	}
}

/** ******************************** */
/** rocky add 2016-04-09 --end-- ** */
/** ******************************** */

/** ******************************** */
/** ** 以下为分组中按钮调用方法 *** */
/** ******************************** */

/**
 * 变长区：添加按钮
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
var selectDefaultVal = {
	"data_pageset": "pageNo",
	"data_merge": "left_right",
	"data_autosize": "autoRow",
	"data_print": "print_area"
};

function addDynamicArea(o, type) {
	var target = $(o).closest('.form-group');
	var datas = [{
		'col-1': {
			name: selectDefaultVal[type] || 'bc-left-right'
		},
		'col-2': {
			name: getRangeCellPositionStr(getSelections()),
			datas: JSON.stringify(getSelections())
		},
		'col-3': {
			colWidth: 5,
			isData: true,
			text: '变长',
			type: 'select',
			htmlTempl: '<select>',
			classStyle: 'form-control input-sm',
			optionValues: [{
				text: '动态',
				value: 'dynamic'
			}]
		}
	}];
	addDataFormRows(target, datas, type);
}
/**
 * 变长区：多栏数据添加按钮
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addDynamicAreaData(o, type) {
	var target = $(o).closest('.form-group');
	var datas = [{
		'col-1': {
			name: getRangeCellPositionStr(getSelections())
		},
		'col-2': {
			name: '引数'
		},
		'col-3': {
			name: '实测值'
		}
	}];
	addDataFormRows(target, datas, type);
}

/**
 * 数据更新集保存区域：添加按钮方法
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addDataSaveArea(o, type) {
	var cellDatas = getSelectCellData();

	var target = $(o).closest('.form-group');

	var datas = [{
		'col-1': {
			name: getRangeCellPositionStr(getSelections())
		},
		'col-2': {
			name: '',
			data: cellDatas
		}
	}];
	addDataFormRows(target, datas, type);
}

/**
 * 表格栏数规范值统计 ：添加按钮方法
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addNormalValue(o, type) {
	var target = $(o).closest('.form-group');
	var datas = [{
		'col-1': {
			name: getRangeCellPositionStr(getSelections())
		},
		'col-2': {
			name: '规范值统计'
		},
		'col-3': {
			name: '搭接宽度 实测值（检测点数）'
		}
	}];
	addDataFormRows(target, datas, type);
}
/**
 * 签名：添加按钮
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addSinger(o, type) {
	var target = $(o).closest('.form-group');

	var hiddenInput = target.next('div[hidden="hidden"]').find('.form-control');

	var data = {
		'col-1': {
			name: getRangeCellPositionStr(getSelections())
		},
		'col-2': {
			name: ''
		},
		'col-3': {
			name: ''
		},
		'col-4': {
			name: ''
		}
	};
	var formgroup = addDataFormRow(target, data, type);
	formgroup.attr({
		t: target.attr('t'),
		'data-code': target.attr('data-code')
	});
	var $input = formgroup.find('input:hidden');

	var tmpId = getSingleId();
	$input.attr({
		id: tmpId,
		'data-field': tmpId,
		'data-code': target.attr('data-code'),
		'data-item': target.attr('data-item'),
		class: 'form-control ' + target.attr('data-item'),
		t: 'dynamicArea',
		pageurl: hiddenInput.attr('pageurl'),
		pageid: hiddenInput.attr('pageid')
	});
	openWindow(formgroup);

}
/**
 * 数据引用：定义公式
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 * @return {[type]} [description]
 */
function definedFormula(o, type) {

}

/**
 * 数据引用：引入表数据按钮
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addImportDataFormRows(o, type) {
	var target = $(o).closest('.form-group');

	var hiddenInput = target.siblings('div[hidden="hidden"]').find('.form-control');

	var data = {
		'col-1': {
			name: getRangeCellPositionStr(getSelections())
		},
		'col-2': {
			name: '引数'
		},
		'col-3': {
			name: ''
		}
	};

	var formgroup = addDataFormRow(target, data, type);
	formgroup.attr({
		t: target.attr('t'),
		'data-code': target.attr('data-code')
	});
	var $input = formgroup.find('input:hidden');

	var tmpId = getSingleId();
	$input.attr({
		id: tmpId,
		'data-field': tmpId,
		'data-code': target.attr('data-code'),
		'data-item': target.attr('data-item'),
		class: 'form-control ' + target.attr('data-item'),
		t: 'dynamicArea',
		pageurl: hiddenInput.attr('pageurl'),
		pageid: hiddenInput.attr('pageid')
	});
	openWindow(formgroup);
}

/**
 * 工程图表：添加按钮
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addProjectChart(o, type) {

}

/**
 * 数据判断：添加按钮
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addDataValidate(o, type) {
	var target = $(o).closest('.form-group');
	var datas = {
		'col-1': {
			name: 'A4>1'
		},
		'col-2': {
			name: '太大了'
		}
	};

	addDataFormRow(target, datas, type);
}

/**
 * 附表字段：添加添加按钮
 * 
 * @param {[type]}
 *            o [description]
 * @param {[type]}
 *            type [description]
 */
function addAttaField(o, type) {

}
/** ******************************** */
/** ****分组按钮调用方法结束******** */
/** ******************************** */


/**
 * 选择图片回调函数
 * 
 */
function selectIconFunc(url) {
	if (url) {
		var selections = getSelections();
		$.each(selections, function(i, v) {
			var cell = getActiveCell(v),
				cellType = cell.cellType();
			cellType.url(contextPath + url);
		});
		window.openWindowDialog.close();
	}
}



/*====================计算公式函数====================*/
/**
 * 定义输入输出
 */
function definedParams(o, type) {
	var cellDatas = getSelectCellData();

	var target = $(o).closest('.form-group');

	var datas = [{
		'col-1': {
			name: getRangeCellPositionStr(getSelections())
		},
		'col-2': {
			name: '',
			data: cellDatas,
			type: 'input'
		},
		'col-3': {
			name: 'input'
		}
	}];
	addDataFormRows(target, datas, type);
}