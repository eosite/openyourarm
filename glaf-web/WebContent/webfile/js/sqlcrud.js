window.$queue = window.$queue || $.Callbacks("zygs.fzmt.sqlcrud");

var getSequence = (function() {
	var currentTime = new Date().getTime();
	return function() {
		return currentTime++;
	};
})();

window.getDictByCode = function(code, fn) {
	$.ajax({
		url : contextPath + '/mx/form/defined/getDictByCode',
		data : {
			code : code
		},
		type : 'post',
		dataType : 'json',
		async : false,
		success : function(data) {
			if (fn)
				fn(data);
		}
	});
};

/**
 * 字段类型
 */
window.getDictByCode('dType', function(data) {
	data = data || new Array(), window.dTypeSourceObj = new Object();
	var mapping = {
		i4 : [ 'integer', 'long' ],
		i8 : [ 'double' ],
		datetime : [ 'date' ]
	};

	var sb = new StringBuffer("<option value=''></option>");

	$.each(data, function(i, v) {
		window.dTypeSourceObj[v.code] = v;
		sb.appendFormat("<option value='{code}' >{name}</option>", v);
	});
	window.dTypeSelect = sb.toString();
	$.each(mapping, function(code, types) {
		if (types && types.length) {
			var o = window.dTypeSourceObj[code];
			if (o) {
				$.each(types, function(i, t) {
					window.dTypeSourceObj[t] = $.extend(true, {}, o);
				});
			}
		}
	});

});

/**
 * 获取参数
 * 
 * @param row
 * @returns
 */
function getParameterName(row) {
	
	!row.param //
		&& (row.param = "col" + window.getSequence());
	
	
	row.paramName = (row.tableNameCN || (window._treeNode ? //
			window._treeNode.tableNameCN : "" )) + " " + row.fname + " | 参数";
	
	return row.paramName;
}


var conExpressTmp = '{"expVal":"~F{默认.{tableNameCN}.{text}}!=null","expActVal":"~F{default.{tableName}.{dname}}!=null","varVal":[{"key":"~F{默认.{tableNameCN}.{text}}","value":{"code":"~F{default.{tableName}.{dname}}","value":"~F{默认.{tableNameCN}.{text}}"}}]}';

function getConExpress(opts, row){

	row.tableName = row.tableName || wdataSet.dataTableName;
	
	var str = conExpressTmp.format(row);
	
	var dataItem = JSON.parse(str);
	
	row[opts.field + "Item"] = str;
	
	return row[opts.field] = dataItem.expVal;
}

/**
 * dom 加载完成
 */
$(function() {
	$queue.fire();
});

/* 布局 start */
$("#vertical").kendoSplitter({
	orientation : "vertical",
	panes : [ {
		collapsed : false,
		resizable : false,
		scrollable : false,
		size : "40px"
	}, {
		collapsed : false,
		scrollable : false
	}, {
		collapsed : false,
		resizable : false,
		scrollable : false,
		size : "50px"
	} ]
});

$("#horizontal").kendoSplitter({
	panes : [ {
		collapsed : false,
		collapsible : true,
		collapsedSize : "0px",
		max : "300px",
		resizable : true,
		size : "300px",
		scrollable : true
	}, {
		size : "300px",
		scrollable : false
	} ]
}).data("kendoSplitter");

// Tab初始化
$("#tabstrip").kendoTabStrip({
	tabPosition : "bottom",
	scrollable : false,
	animation : {
		open : {
			effects : "fadeIn"
		}
	},
	select : function(e) {
		var $li = $(e.item);
		var index = $li.index();
		if (index <= 1) {
			Exchange(index);
		} else {
			var $body = $(document.body);
			if (!$body.data("selected")) {
				$body.data("selected", !!Exchange(1));
			}
		}
	},
	activate : function(e) {
		var $li = $(e.item);
		var index = $li.index();
		var $g = $("#grid-" + (index + 2)).data("grid");
		$g && ($g.resize());
	}
}).data("kendoTabStrip").select(0);

function Exchange(index) {
	var datas = window.getUnSelectRows(index == 0 ? "grid-3" : "grid-2");
	if (datas && datas.length) {
		var rows = {}, mtGrid = $("#grid-" + (index + 2));
		var data = mtGrid.grid("getData");
		if (data && data.length) {
			$.each(data, function(i, v) {
				rows[v.dname] = v;
			});
		}
		$.each(datas, function(i, v) {
			if (rows[v.dname]) {
				$.extend(true, v, rows[v.dname]);
			}
		});

		mtGrid.grid("load", datas);
	}
}

function resizeTabs() {
	$("[id^=tabstrip-]").css({
		height : $(document).height() * 0.7
	});
}

$queue.add(resizeTabs);

$queue.add(function() {
	$(window).resize(function() {
		resizeTabs();
	});
});
/* 布局 end */

var fields = [ "FieldId", "dataItem", "defVal", 'dtype', "fname", "dname",
		"param", 'paramName', "strlen", "tableNameCN", "tablename", 'tableid',
		"input", "nullAble", "variable", "update", "id", "text", "prepared","conExp", "conExpItem" ];
function a2b(fs, a, b) {
	if (fs && a && b) {
		$.each(fs, function(i, f) {
			b[f] = a[f];
		});
	}
}

function getNode() {
	var node, //
	nodes = $zTree.getSelectedNodes();
	if (nodes && nodes[0]) {
		node = nodes[0];
	} else {
		node = window._treeNode;
	}
	return node;
}

$queue
		.add(function() { // 按钮初始化
			var btns = {
				sure : function(e) {
					var node = window.getNode();

					if (!node) {
						alert("请选择表格或分类!");
						return false;
					}

					if (!node.tableName) {
						createDataTable(node);

						return false;
					}

					if (node && window.wdataSet) {
						createWdataSet(node);
					}
				},
				add : function(e) {
					var gridKendo = $("#grid-2").data('grid');
					gridKendo.insertStaticRow([ {
						dtype : 'string',
						strlen : 200
					} ]);
				},
				test : function(e) {
					var url = contextPath
							+ "/mx/dep/base/depBaseWdataSet/edit?view=/dep/base/depBaseWdataSet/test&id="
							+ wdataSet.id;

					window.open(url);
				}
			};
			$(".k-button").on("click.k-button", function(e) {
				var t = $(this).attr("t");
				t && (t = btns[t]) && (t.call(this, e));
			});
		});

/**
 * 创建表格
 * 
 * @param node
 * @returns
 */
function createDataTable(node) {
	// 先建表
	if (!$("#create_table_name").val()) {
		alert("表名不能为空!");
		return false;
	}
	var datas = $("#grid-2").grid("getData"), //
	isOk = true;
	if (datas && datas.length) {
		var i, v;
		for (i = 0; i < datas.length; i++) {
			v = datas[i];
			if (!v.dtype) {
				alert("第{0}行没有配置哦!".format(i + 1));
				isOk = false;
				break;
			}
			v.dType = v.dtype;
		}
		if (isOk) {
			var params = {
				name : $("#create_table_name").val(),
				systemName : getSystemName(),
				indexId : node.indexId,// $("#create_table_sort").data("kendoDropDownList").value(),
				tableid : node.tableId,
				columns : JSON.stringify(datas),
				R : node.R
			};
			var myUrl = contextPath + "/rs/isdp/table/saveTableInfo";
			if (params.R) {
				myUrl = contextPath + "/mx/isdp/rdataTable/saveTableInfo";
			}
			$.ajax({
				type : "POST",
				url : myUrl,
				dataType : 'json',
				async : false,
				data : params,
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null && data.message != null) {
						alert(data.message);
					} else {
						window.createTable__ = true;
						setting.callback.refreshAndSelectById(data.table.id);
					}
				}
			});
		}
	}
	if (!isOk) {
		return false;
	}
}

/**
 * 创建更新集
 * 
 * @param node
 * @returns
 */
function createWdataSet(node) {
	var datas = $("#grid-2").grid("getData");
	var wdataSetId = window.wdataSet.id;
	var table = {
		dataSetCode : 'wds-code-' + wdataSetId,
		tableName : node.tableNameCN,
		dataTableName : node.dataTableName || node.tableName,
		dataSetDesc : window.wdataSet.dataSetDesc || (node.name + " 系统自动生成"),
		dataSetName : window.wdataSet.dataSetName || (node.name + " 系统自动生成")
	};

	$.extend(window.wdataSet, table);
	var $bd = $("#select-biaoduan").data("kendoDropDownList").dataItem() || {};
	var $type = $("#select-type").data("kendoDropDownList").dataItem() || {};

	window.a2b([ "id", "tableId", "name" ], node, table);

	var dataSource = {
		databaseId : $bd.id,
		systemName : $bd.id ? $bd.code : ""
	};

	$.extend(table, dataSource);

	var ruleJson = {
		wdataSetId : wdataSetId,
		bd : {
			code : $bd.code,
			text : $bd.text
		},
		type : {
			code : $type.code,
			text : $type.name
		},
		columns : [],
		table : table,
		node : $.extend(node, dataSource),
		whereClaus : [],
		whereParams : window.whereClausParams()
	// 自定义参数
	}, column;

	$(".tableCon, .table-tree").each(function(i, v) {
		var json = whereClaus.toJson($("#" + v.id));
		ruleJson[v.id] = json;
	});

	window.pushColumns(ruleJson, datas, "", true);

	var $grid3 = $("#grid-3");
	datas = $grid3.grid("getData");
	if (!datas || !datas.length) {
		var rows = window.getUnSelectRows();
		if (rows && rows.length) {
			$grid3.grid("load", rows);
		}
		datas = $grid3.grid("getData");
	}

	window.pushColumns(ruleJson, datas, [ "id" ]);

	window.wdataSet.ruleJson = JSON.stringify(ruleJson);

	var saveUrl = contextPath
			+ '/mx/dep/base/depBaseWdataSet/saveDepBaseWdataSet';
	$.ajax({
		url : saveUrl,
		data : window.wdataSet,
		type : "POST",
		dataType : 'JSON',
		success : function(ret) {
			if (ret) {
				window.showMessage(JSON.parse(ret.ruleJson));
				if (isBind) {
					if (confirm("返回保存吗?")) {
						window.initTableMsg(ret);
					}
				} else {
					alert("操作成功!");
				}
			}
		}
	});
}

/**
 * 
 * @param ruleJson
 * @param datas
 * @returns
 */
function pushColumns(ruleJson, datas, uns, selected) {
	var idColumn, column;
	$.each(datas, function(i, v) {
		column = {
			wdataSetId : ruleJson.wdataSetId,
			columnName : v.fname,
			dataColumnName : v.dname,
			defaultVal : v.defVal,
			isKey : v.isKey,
			selected : !!selected
		};
		if (uns && uns.length) {
			try {
				$.each(uns, function(i, f) {
					delete v[f];
				});
			} catch (e) {
				console.log(e);
			}
		}
		window.a2b(fields, v, column);
		ruleJson.columns.push(column);
		if (column.dname == "id") {
			$.extend((idColumn = {}), column);
		}
		if (v.isKey) {
			ruleJson.whereClaus.push($.extend({}, column));
		}
	});
	if (!ruleJson.whereClaus.length && idColumn) {
		ruleJson.whereClaus.push(idColumn);
	}
}

/**
 * 获取条件
 * 
 * @returns
 */
function getWhereClaus() {

}

$queue.add(function() {
	// if (!wdataSet.id)
	window.setTimeout(function() {
		initZTree(window.getTableType(), window.getSystemName());
	}, 100);
});

// //
var editors = {
	stuff : function(container) {
		var $tr = container.closest('tr');
		return {
			tr : $tr,
			index : $tr.index(),
			grid : $tr.closest('[data-role=grid]').data('kendoGrid')
		};
	},
	textbox : function(container, options) {
		var s = editors.stuff(container), $input = $("<input/>", {
			name : options.field,
			class : 'k-textbox'
		}).appendTo(container).change(function(e) {
			// console.log(options);
			// s.grid.dataSource.data()[s.index][options.field] = $(this).val();
		});
		return $input;
	},
	dropdownlist : function(container, options, dropdownlist) {

		var $input = $("<input/>", {
			name : options.field
		}).appendTo(container).kendoDropDownList(dropdownlist);

		return $input;

	},
	checkbox : function(container, options) {
		var $input = $("<input/>", {
			name : options.field,
			type : 'checkbox'
		}).appendTo(container);
		return $input;
	}
};

/*
 * function checkboxTemplate(dataItem, key) { var opt = { t : dataItem.uid, type :
 * 'checkbox', name : key, onclick : "clickCheckbox(this);" }; if (dataItem[key] ==
 * true || dataItem[key] == 'true') opt.checked = true; return $("<input />",
 * opt).outer(); }
 */

function clickCheckbox(o) {
	var $this = $(o);
	var s = editors.stuff($this);
	var data = s.grid.dataSource.getByUid($this.attr("t"));
	data[$this.attr("name")] = o.checked;
}

/**
 * 表达式弹窗
 */
function openExpress(o) {
	$.data(document.body, "expressionObj", o);
	var params = {
		retFn : "setColTbField",
		getFn : "getRowTree",
		initExpFn : "getColTbExp",
		category : "db",
		notEempty : true,
	}, url = contextPath + "/mx/expression/defined/view?" + $.param(params);
	window.open(url);
}

function getColTbExp() {
	var $o = $($.data(document.body, "expressionObj"));
	var $grid = $o.closest(".grid");

	if ($grid.get(0)) {
		var row = $grid.grid("getRow", $o);
		if (!row) {
			return;
		}
		var dataItem = row.dataItem;
		if ($o.data("self")) {
			dataItem = row[$o.attr("name") + "Item"];
		}
		if (!dataItem) {
			return;
		}

		return JSON.parse(dataItem);
	} else {
		var $input = $o;
		var item = $input.data("item") || $input.closest("tr").data("item");
		if (!item) {
			var stuff = MtColumns.editors.stuff($input);
			if (stuff.grid) {
				var dataItem = stuff.grid.dataItem($input.closest("tr"));
				if (dataItem) {
					item = dataItem.expItem;
				}
			} else {

			}
		}
		$input.data("item", item);

		return item.dataStr ? JSON.parse(item.dataStr) : item;
	}
}

function setColTbField(data) {
	if (data) {
		var $o = $($.data(document.body, "expressionObj"));
		var $grid = $o.closest(".grid");

		$o.val(data.expVal).data("item", {
			expression : true,
			value : data.expActVal,
			dataStr : JSON.stringify(data)
		}).focus().attr({
			title : data.expVal
		});

		if ($grid.get(0)) {
			var row = $grid.grid("getRow", $o);

			if (!row) {
				return;
			}

			row[$o.attr("name")] = data.expVal;

			var dataItem = JSON.stringify(data);

			if ($o.data("self")) {
				row[$o.attr("name") + "Item"] = dataItem;
			} else {
				row.dataItem = dataItem;
			}

		} else {// kendo
			try {
				var $input = $o, editors = MtColumns.editors;
				var stuff = editors.stuff($input);
				if (stuff.grid) {
					stuff.tr.data("item", data);
					var dataItem = stuff.grid.dataItem($input.closest("tr"));
					dataItem[$input.attr("name")] = data.expVal;
					dataItem.expItem = data;
					kendo.bind($input, dataItem);
				} else {

				}
			} catch (e) {
				console.log(e);
			}
		}

	}
}

// 表达是页面回调函数
function setRowField(data) {

	if (data) {
		var $menu = $("#conditionTree").data("menu");

		var textbox = $menu.prev('.textbox-menu');

		data = $.extend({}, data);

		if (textbox && textbox[0]) {
			textbox.show().val(data.expVal).data("item", {
				expression : true,
				value : data.expActVal,
				// data : data,
				dataStr : JSON.stringify(data)
			});
			$menu.hide();
		}
	}

}

/**
 * 获取自定参数
 * 
 * @returns
 */
function whereClausParams(datas) {
	if (window.getParamsData && window.initParams) {
		if (datas) {
			window.initParams(datas);
		} else {
			return window.getParamsData();
		}
	}
	return null;
}

function getRowTree() {
	var datas = $("#grid-2").grid("getData");

	var data3 = $("#grid-3").grid("getData");

	if (data3 && data3.length) {
		datas.addAll(data3);
	}

	var paramData = window.whereClausParams();
	if (paramData && paramData.length) {
		paramData.length && ($.each(paramData, function(i, v) {
			v.paramName = v.name;
		})) && datas.addAll(paramData);
	}

	if (datas && datas.length) {
		var ds = new Object(), collection = new Array(), d = new Array();
		var p = {
			id : 'param-01',
			name : '输入形参',
			pId : '',
			children : []
		};
		collection.push(p);
		var bd = $("#select-biaoduan").data("kendoDropDownList").dataItem()
				|| {};
		var table = {
			name : window._treeNode.name,
			tableName : window._treeNode.tableName
					|| window._treeNode.dataTableName
		};
		collection.push({
			id : '123',
			name : '字段',
			pId : ''
		}, {
			id : bd.code,
			name : bd.text,
			pId : '123',
			open : false,
			isParent : true
		}, {
			id : table.tableName,
			name : table.name,
			pId : bd.code,
			isParent : true
		});

		/**
		 * 内部参数
		 */
		$.each(datas, function(i, v) {

			if (v.param && !v.dname) {
				p.children.push({
					code : "~F{" + v.param + "." + v.param + "." + v.param
							+ "}",
					value : "~F{" + v.paramName + "}",
					name : v.paramName
				});
			} else {
				collection.push({
					pId : table.tableName,
					id : v.id,
					name : v.fname,
					dType : v.dtype,
					t : '',
					code : "~F{" + bd.code + "." + table.tableName + "."
							+ v.dname + "}",
					value : "~F{" + bd.text + "." + table.name + "." + v.fname
							+ "}",
					isParent : false
				});
			}

		});
		return collection;
	}
	return null;
}

var commonOptions = [
		{
			title : '字段类型',
			field : 'dtype',
			style : 'width : 15%;',
			template : function(row, opts) {

				var o = row[opts.field];
				if (o && (o = window.dTypeSourceObj[o])) {
					return o.name;
				} else {
					row.dtype = "string";
					o = row[opts.field];
					o = window.dTypeSourceObj[o]
					return o.name;
				}
			},
			editor : function(container, options, row) {
				var $select = $("<select>", {
					field : options.field,
					class : 'form-control',
					style : "width:90%; height:90%;"
				}).appendTo(container);
				var val = row[options.field] || row["text"];

				function change(e) {
					var $this = $(this);
					var $selected = $this.find(":selected");
					var value = $selected.attr("value");
					if (value && value.toLowerCase() == 'string') {

						if (!row.strlen || row.strlen == '0') {
							row.strlen = 200
						}

						row[options.field] = value;
						$this.closest(".grid").grid("refreshRow",//
						$this, $.extend({}, row));
					}
				}

				$select.append(window.dTypeSelect).val(val).on("change.dtype",//
				change);
			}
		},
		{
			title : '字段长度',
			field : 'strlen',
			style : "width : 15%;",
			template : function(row, opts) {
				var o = row[opts.field];
				if (row[opts.field]) {
					return row[opts.field];
				} else {
					row['strlen'] = 200;
					return 200;
				}
			},
			editor : function(container, options, row) {
				var $input = $('<input>', {
					field : options.field,
					type : 'number',
					class : 'form-control',
					style : "width:90%; height:90%;"
				}).val(row[options.field] || 0).appendTo(container);
			}
		},
		{
			title : '默认值',
			field : 'defVal',
			style : "width : 15%;",
			editor : function(container, options, row) {
				editors.textbox(container, options).attr({
					class : "form-control",
					style : "width:90%; height:90%;",
					readonly : true
				}).val(row[options.field] || '').on('dblclick.form-control',
						function(e) {
							window.openExpress(this);
						});
			}
		},
		{
			title : '条件表达式',
			field : 'conExp',
			style : "width : 15%;",
			editor : function(container, options, row) {
				editors.textbox(container, options).attr({
					class : "form-control",
					style : "width:90%; height:90%;",
					readonly : true,
					'data-self' : true
				}).val(row[options.field] || '').on('dblclick.form-control',
						function(e) {
							window.openExpress(this);
						});
			}/*,
			template : function(row, opts) {
				if (row[opts.field])
					return row[opts.field];
				return window.getConExpress(opts, row);
			}*/
		}, {
			field : 'paramName',
			title : '参数',
			style : "width : 10%;",
			template : function(row, opts) {
				console.log(row);
				if (row[opts.field])
					return window.getParameterName(row)/*row[opts.field]*/;
				if (!row.fname)
					return "";
				return window.getParameterName(row);
			}
		}, {
			title : '插入',
			field : 'input',
			headerTemplate : function(opts) {
				return window.headerTemplateChk(opts);
			},
			style : "width: 50px",
			template : function(row, opts) {
				return checkboxTemplate(row, opts);
			}
		}, {
			title : '更新',
			headerTemplate : function(opts) {
				return window.headerTemplateChk(opts);
			},
			field : 'update',
			style : "width: 50px",
			template : function(row, opts) {
				return checkboxTemplate(row, opts);
			}
		}, /*
			 * { title : '变量', headerTemplate : function(opts) { return
			 * window.headerTemplateChk(opts); }, field : 'variable', style :
			 * "width: 50px", template : function(row, opts) { return
			 * checkboxTemplate(row, opts); } },
			 */{
			title : '预编译',
			headerTemplate : function(opts) {
				return window.headerTemplateChk(opts);
			},
			field : 'prepared',
			style : "width: 60px",
			template : function(row, opts) {
				return checkboxTemplate(row, opts);
			}
		}, {
			title : '可空',
			field : 'nullAble',
			headerTemplate : function(opts) {
				return window.headerTemplateChk(opts);
			},
			style : "width: 50px",
			template : function(row, opts) {
				return checkboxTemplate(row, opts);
			}
		}, {
			title : '主键',
			field : 'isKey',
			style : "width: 50px",
			template : function(row, opts) {
				return checkboxTemplate(row, opts);
			}
		}, {
			title : '操作',
			field : 'del',
			style : "width: 10%",
			template : function(row, opts) {
				return "<button class='btn btn-info btn-delete'>删除</button>";
			}
		} ];

/**
 * 
 * @param name
 * @returns
 */
function headerTemplateChk(opts) {
	return "<input type='checkbox' class='mt-checkbox-all' t='{0}' ></input><span>{1}</span>"//
	.format(opts.field, opts.title);
}

Array.prototype.addAll = function(eles) {
	var that = this;
	if (eles && eles.length) {
		$.each(eles, function(i, v) {
			that.push($.extend(true, {}, v));
		});
	}
	return that;
};

window.gridOptions = {
	clickUpdate : true,
	columns : [ {
		title : '控件名称',
		field : 'text',
		style : 'width : 15%;',
		template : function(row, opts) {
			var o = row[opts.field];
			if (row[opts.field]) {
				return row[opts.field];
			}
		},
		editor : function(container, options, row) {
			var $input = $('<input>', {
				field : options.field,
				type : 'string',
				class : 'form-control',
				style : "width:90%; height:90%;"
			}).val(row[options.field] || 0).appendTo(container);
		}
	}, {
		title : '绑定字段',
		field : 'fname',
		style : "width : 20%;",
		template : function(row, opts) {
			return row[opts.field];
		},
		editor : function(container, options, row) {
			console.log(row);
			var $select = $("<select>", {
				field : options.field,
				class : 'form-control',
				style : "width:90%; height:90%;"
			}).appendTo(container);
			var $options = window.getSelectOptions();

			var val = row[options.field] || row["text"];
			$select.append($options).val(val).on("change.fname", function(e) {
				var $this = $(this);
				var $selected = $this.find(":selected");
				var value = $selected.attr("value");
				var obj = $.extend(true, {}, window.columnsObject[value]);

				if (obj) {

					window.a2b([ 'text', 'row-index', 'id' ], row, obj);

					$this.closest(".grid").grid("refreshRow", $this, obj);
				}
			});
		}
	} ].addAll(commonOptions)
};

/**
 * 获取联动控件信息
 * 
 * @returns
 */
function getLinkAgeControl() {
	var msg = window.initTableMsg(), controls = [];
	if (msg && msg.linkageControl) {
		controls = JSON.parse(msg.linkageControl);
	}
	return controls;
}

/**
 * 未跟页面元素匹配的字段
 * 
 * @returns
 */
function getUnSelectRows(gridId) {
	var g = $("#" + (gridId || "grid-2")).data("grid"), data, arr = [];
	if (g && (data = g.getData())) {
		var tmp = {};
		$.each(data, function(i, v) {
			tmp[v.fname] = v;
		});
		$.each(window.columnsObject, function(i, v) {
			if (v.fname && !(v.fname in tmp)) {
				arr.push(v);
			}
		});
	}
	return arr
}

/**
 * 组装下拉框
 * 
 * @returns
 */
function getSelectOptions() {
	var sb = new StringBuffer("<option value=''></option>");
	var index = 0;
	$.each(window.columnsObject, function(i, v) {
		v.index = index++;
		sb.appendFormat(
				"<option value='{text}' index='{index}' >{text}</option>", v);
	});
	return sb.toString();
}

/**
 * checkbox 模版
 * 
 * @param row
 * @param opts
 * @returns
 */
function checkboxTemplate(row, opts) {
	var field = opts.field, checked = row[field] == true
			|| row[field] == 'true';
	if (field == 'input') {
		if (row[field] === undefined || row[field] === ''){
			checked = true;
		}
	} else if (field == "variable") {

	} else if (field == "nullAble") {
		if (row.dname != "id")
			checked = true;
	} else if (field == "update" || field == "prepared") {
		if (row[field] === undefined || row[field] === ''){
			checked = true;
		}
	}
	row[field] = checked || false;
	return "<div style='text-align:center;'><input class='mt-checkbox' type='checkbox' {0} name='{1}'  /></div>"
			.format(checked ? "checked=true" : "", field);
}

/**
 * checkbox 处理 作用域:本身
 * 
 * @returns
 */
function checkboxFunc(checked) {
	var name = $(this).attr("name");
	var row = $(this).closest(".grid").grid("getRow", this);
	this.checked = row[name] = checked;
}

$queue.add(function() {
	$("body").on("click.grid-table-checkbox.mt-checkbox",
			".grid table input.mt-checkbox", function() {
				// var name = $(this).attr("name");
				// var row = $(this).closest(".grid").grid("getRow", this);
				// row[name] = this.checked;
				window.checkboxFunc.call(this, this.checked);
			}).on("click.grid-table.btn-delete",
			".grid table button.btn-delete", function(e) {
				e.preventDefault();
				if (confirm("确定删除吗?")) {
					var $grid = $(this).closest(".grid");
					var grid = $grid.data("grid");
					if (grid) {
						grid.removeRow(this);
						var id = $grid.attr("id");
						id && (id = id.replace("grid-", ""));
						Exchange(!(parseInt(id) - 2) ? 1 : 0);
					}
				}
				return false;
			}).on("click.grid-table-checkbox.mt-checkbox-all",//
	"input.mt-checkbox-all", function(e) {
		var $this = $(this), t = $this.attr("t"), //
		checked = this.checked;
		$this.closest(".grid").find(".mt-checkbox[name='{0}']"//
		.format(t)).each(function() {
			window.checkboxFunc.call(this, checked);
		});
	});
});

/**
 * 根据tableId 获取table及列信息
 */
function getColumnsByTableId(tableObj, fn) {
	$.ajax({
		url : contextPath + "/rs/isdp/cellDataField/selectFieldByTableId",
		type : "POST",
		dataType : "JSON",
		data : {
			tableId : tableObj.tableId,
			type : tableObj.type,
			tableName : tableObj.tableName,
			systemName : window.getSystemName(),
			R : tableObj.R
		},
		success : function(data) {
			if (fn)
				fn(data);
		}
	});
}

function getPrimaryKeys(tableObj, fn) {
	$.ajax({
		url : contextPath + '/mx/dep/base/depBaseWdataSet/getPrimaryKeys',
		type : "POST",
		dataType : 'JSON',
		data : {
			tableName : tableObj.tableName,
			systemName : window.getSystemName()
		},
		success : function(ret) {
			fn && fn(ret.keys);
		}
	});
}

function isKey(keys, dname) {
	var ret = false;
	if (keys && keys.length) {
		for (var i = 0, len = keys.length; i < len; i++) {
			var key = keys[i];
			if (key && (key.toLowerCase() == dname.toLowerCase())) {
				ret = true;
				break;
			}
		}
	}
	return ret;
}

/**
 * 根据tableId 获取table及列信息 return []
 */
function getColumns(tableObj, fn) {
	var collection = [], json = {}, dname, keys;
	var types = [ window.getKendo($("#select-type")).dataItem().code || 99, 99 ];

	function _a(index, row) {// 返回列过滤
		dname = row.dname;

		dname != "id" && (row.nullAble = true);

		!row.tableNameCN && (row.tableNameCN = window._treeNode.tableNameCN);

		row.isKey = window.isKey(keys, dname);
		
		(row.isKey || dname == 'ctime' || (dname.indexOf("create") == 0)) && (row.update = false);
		
		(dname.indexOf("update") == 0) && (row.input = false);
		
		!json[dname] && ((json[dname] = row) && (collection.push(row)));
	}

	window.getPrimaryKeys(tableObj, function(ks) {
		keys = ks;
		var datas = types;
		$.each(datas, function(i, type) {
			tableObj.type = type;
			window.getColumnsByTableId(tableObj, function(data) {
				if (data && data.rows) {
					$.each(data.rows, _a);
				}
				if (i === (datas.length - 1)) {
					fn && fn(collection);
				}
			});
		});
	});
	return (window.columnsCollection = collection);
}

function getKendo(jq) {
	var data = jq.data();
	if (data) {
		for ( var key in data) {
			if (key.toLowerCase() == ("kendo" + jq.data("role"))) {
				return jq.data(key);
			}
		}
	}
	return null;
}

/**
 * 修改列表
 */
$queue.add(function() {

	var options3 = {
		clickUpdate : true,
		columns : [ {
			title : '字段',
			field : 'fname',
			style : "width : 15%;",
			template : function(row, opts) {
				var o = row[opts.field];
				if (row[opts.field]) {
					return row[opts.field];
				}
			},
			editor : function(container, options, row) {
				var $input = $('<input>', {
					field : options.field,
					type : 'string',
					class : 'form-control',
					style : "width:90%; height:90%;"
				}).val(row[options.field] || 0).appendTo(container);
			}
		} ].addAll(commonOptions),
		events : {
			onClickCell : function(index, field, value) {

			}
		}
	}

	var options2 = $.extend(true, {
	// datas : window.getLinkAgeControl()
	}, window.gridOptions);

	if (!isBind) { // 客户端只做查看
		$("#grid-2").grid(options3);
	} else {
		$("#grid-2").grid(options2);
	}

	$("#grid-3").grid(options3);
});

/**
 * 页面初始化还原数据
 */
$queue.add(function() {
	if (window.wdataSet && wdataSet.id) {
		window.initPageFunc(wdataSet.id);
	} else {
		// $("#grid-2").grid("load", window.getLinkAgeControl());
		var tableMsg = window.initTableMsg();
		if (tableMsg && tableMsg.wdataSet) {
			window.initPageFunc(tableMsg.wdataSet.id);
		} else {
			$("#grid-2").grid("load", window.getLinkAgeControl());
		}
	}
});

function initPageFunc(wdataSetId) {

	var url = contextPath + '/mx/dep/base/depBaseWdataSet/getById';
	$.ajax({
		url : url,
		data : {
			id : wdataSetId
		},
		type : "POST",
		dataType : 'JSON',
		success : function(ret) {
			!!ret && (window.wdataSet = //
			$.extend((window.wdataSet || {}), ret));
			if (window.wdataSet.ruleJson) {
				window.initPage(wdataSet);
			}
		}
	});
}

/**
 * 页面初始化方法
 */
function initPage(wdataSet) {

	var ruleJson = JSON.parse(wdataSet.ruleJson);

	// var collection = window.getPopulateColumns(ruleJson.columns || []);

	// $("#grid-2").grid("load", collection);

	window._treeNode = $.extend(true, {}, (ruleJson.table) || { //
		name : wdataSet.tableName,
		tableName : wdataSet.dataTableName,
		dataTableName : wdataSet.dataTableName,
		tableNameCN : wdataSet.tableName
	}, ruleJson.node || {});

	var columnTmp = {}, load = function() {
		$("#grid-2").grid("load", window.getPopulateColumns(//
		ruleJson.columns || []));
	};
	try {
		/**
		 * 查询出系统建的字段
		 */
		$.each(ruleJson.columns || [], function(i, v) {
			columnTmp[v.dname] = v;
		});

		window.getColumnsByTableId(window._treeNode, function(data) {
			if (data && data.rows) {
				$.each(data.rows, function(i, v) {
					!columnTmp[v.dname] && (ruleJson.columns.push(v));
				});
			}
			load(columnTmp = null);
		});
	} catch (e) {
		load();
	}

	var $bd = $("#select-biaoduan").data("kendoDropDownList");

	var $type = $("#select-type").data("kendoDropDownList");

	$bd.value((ruleJson.bd || {
		code : 'default'
	}).code);

	$type.value((ruleJson.type || {}).code);

	initZTree($type.value(), $bd.value());

	setting.callback.refreshAndSelectById(window._treeNode.id, true);

	window.showMessage(ruleJson);

	/**
	 * 条件树
	 */
	if (window.whereClaus) {
		$(".tableCon, .table-tree").each(function(i, v) {
			if (ruleJson[v.id]) {
				whereClaus.fromJson($("#" + v.id), ruleJson[v.id]);
			}
		});
	}

	/**
	 * whereParams
	 */
	if (ruleJson.whereParams) {
		window.whereClausParams(ruleJson.whereParams);
	}
}

/**
 * 
 * @param columns
 * @returns
 */
function getPopulateColumns(columns, new_) {
	window.columnsObject = {};
	var collection = [], obj;
	var dnameObject = {}, expValue = window.DefaultExpValue || {
		id : {
			"expVal" : "$USER_ID(~CONST)",
			"expActVal" : "$USER_ID(~CONST)",
			"varVal" : [ {
				"key" : "$USER_ID(~CONST)",
				"value" : {
					"code" : "$USER_ID(~CONST)",
					"t" : "用户自增ID",
					"name" : "USERID 用户自增ID",
					"value" : "$USER_ID(~CONST)"
				}
			} ]
		},
		ctime : {
			"expVal" : "$SYSDATE()",
			"expActVal" : "$SYSDATE()",
			"varVal" : [ {
				"key" : "$SYSDATE()",
				"value" : {
					"code" : "$SYSDATE()",
					"isParent" : false,
					"t" : "系统时间",
					"name" : "系统时间",
					"value" : "$SYSDATE()"
				}
			} ]
		}
	};
	$.each(columns, function(i, c) {
		c.text = c.fname;
		c["row-index"] = i;
		window.columnsObject[c.fname] = c;
		dnameObject[c.dname] = c;
		
		if(new_ && !c.defVal && (c.dname in expValue)){
			var data = expValue[c.dname];
			
			c.defVal = data.expVal;
			c.dataItem = JSON.stringify(data);
		}
		
//		if (new_ && c.dname == 'id' && !c.defVal) {
//			var data = {
//				"expVal" : "$USER_ID(~CONST)",
//				"expActVal" : "$USER_ID(~CONST)",
//				"varVal" : [ {
//					"key" : "$USER_ID(~CONST)",
//					"value" : {
//						"code" : "$USER_ID(~CONST)",
//						"t" : "用户自增ID",
//						"name" : "USERID 用户自增ID",
//						"value" : "$USER_ID(~CONST)"
//					}
//				} ]
//			};
//			c.defVal = data.expVal;
//			c.dataItem = JSON.stringify(data);
//		}
//		if (new_ && c.dname == 'ctime' && !c.defVal) {
//			var data = {
//				"expVal" : "$SYSDATE()",
//				"expActVal" : "$SYSDATE()",
//				"varVal" : [ {
//					"key" : "$SYSDATE()",
//					"value" : {
//						"code" : "$SYSDATE()",
//						"isParent" : false,
//						"t" : "系统时间",
//						"name" : "系统时间",
//						"value" : "$SYSDATE()"
//					}
//				} ]
//			};
//			c.defVal = data.expVal;
//			c.dataItem = JSON.stringify(data);
//		}
	});

	/**
	 * 非页面绑定
	 */
	if (!isBind) {

		collection = getCustomFields(columns, false);

		return collection;
	}

	var controls = window.getLinkAgeControl();

	var columnsObj = {};
	try {
		/**
		 * 页面还原信息
		 */
		var msg = window.initTableMsg();
		if (msg.tableMsg) {
			var tableMsg = JSON.parse(msg.tableMsg)[0];
			if (tableMsg.columns) {
				$.each(tableMsg.columns, function(i, c) {
					columnsObj[c.id] = c;
					if (c.dname == 'id') {

					}
				});
			}
		}
	} catch (e) {
		console.log(e);
	}

	if (controls && controls.length) {
		$.each(controls, function(t, v) {
			obj = null;
			var column = columnsObj[v.id];
			if (column) {
				obj = dnameObject[column.fieldName];
			}
			obj = obj || window.columnsObject[v.text] || {};

			var o = $.extend({}, obj, v);

			collection.push(o);
		});
	}
	return collection;
}

var sysFields = (function() {
	var fields = [], map = {};

	var func = function() {
	};

	func.add = function(field, self) {
		!map[field] && (map[field] = field) && (!self && fields.push(field));
	};

	func.contains = function(field) {
		return (field in map);
	};

	func.remove = function(field) {

	};
	$.each(
			[ "id", "ctime", "topid", "isdel", "unitid", "sys_id", "nlevel",
					"index_id", "parent_id", "listno", "old_id", "tzflag",
					"repflag", "tofileflag", "type_id", "type_baseid",
					"lockrec", "cell_locate", "intauto", "email_id",
					"email_type", "email_fromsysid", "spection_id", "parentid",
					"treeid", "createupdate", "updatedate" ], function(i, v) {
				func.add(v);
			});

	return func;
})();

/**
 * 获取用户自定义的字段
 * 
 * @param columns
 * @returns
 */
function getCustomFields(columns, sys) {
	var fields = [], sysField = false;
	if (columns && columns.length) {
		$.each(columns, function(i, v) {
			if(!v.dname){
				return;
			}
			sysField = sysFields.contains//
			(v.dname.toLowerCase());// 系统字段
			if ((sys && sysField) || (!sys && !sysField) || v.selected) {
				if (v.selected)
					fields.push(v);
			}
		});
	}
	return fields;
}

$.extend(true, window.setting, {
	beforeClick : ztreeBeforeClick
});

function ztreeBeforeClick(treeId, treeNode, clickFlag) {
	var key = "clickNode", $tree = $("#" + treeId);
	if (!treeNode.isParent && $tree.data(key) !== treeNode
			&& !window.__TreeNodeIdInit) {
		window.getColumns(window._treeNode = treeNode, function(columns) {
			var collection;
			collection = window.getPopulateColumns(columns, true);
			$("#grid-2").grid("load", collection);
		});
	}
	window.__TreeNodeIdInit = false;
	$("#create_table_name").val(treeNode.tableNameCN || '');
	$("#table_name").text(treeNode.tableName || '');
	$tree.data(key, treeNode);
	if (window.createTable__) {
		window.setTimeout(function() {
			$("button[t=sure]").click();
			window.createTable__ = false;
		}, 100);
	}
}

function showMessage(ret) {
	$("#message").empty().html(
			"<span>{0}</span><hr><span>{1}</span>".format(ret.insertSql,
					ret.updateSql));
}
