/**
 * 只对更新集进行修改
 */

var $queue = $.Callbacks("zygs.fzmt.sqlcrud"), currentTime = new Date()
		.getTime();
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

	}
}).data("kendoTabStrip").select(0);

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
		"input", "nullAble", "variable", "output" ];
function a2b(fs, a, b) {
	if (fs && a && b) {
		$.each(fs, function(i, f) {
			b[f] = a[f];
		});
	}
}
$queue.add(function() { // 按钮初始化
	var btns = {
		sure : function(e) {
			var node = window._treeNode;
			if (node && window.wdataSet) {
				var datas = $("#grid-2").grid("getData");
				var wdataSetId = window.wdataSet.id;
				var table = {
					dataSetCode : 'wds-code-' + wdataSetId,
					tableName : node.tableNameCN,
					dataTableName : node.dataTableName || node.tableName
				};

				$.extend(window.wdataSet, table);
				var $bd = $("#select-biaoduan").data("kendoDropDownList")
						.dataItem()
						|| {};
				var $type = $("#select-type").data("kendoDropDownList")
						.dataItem()
						|| {};

				window.a2b([ "id", "tableId", "name" ], node, table);
				var ruleJson = {
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
					whereClaus : []
				}, column;
				var idColumn = null;
				$.each(datas, function(i, v) {
					column = {
						wdataSetId : wdataSetId,
						columnName : v.fname,
						dataColumnName : v.dname,
						defaultVal : v.defVal,
						isKey : v.isKey
					};
					window.a2b(fields, v, column);

					ruleJson.columns.push(column);
				
					if (column.dname == "id") {
						$.extend((idColumn = {}), column);
					}
					if(v.isKey){
						ruleJson.whereClaus.push($.extend({}, column));
					}
				});
				if(!ruleJson.whereClaus.length){
					ruleJson.whereClaus.push(idColumn);
				}
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
							alert("操作成功!");
							window.showMessage(JSON.parse(ret.ruleJson));
						}
					}
				});
			}
		}
	};
	$(".k-button").on("click.k-button", function(e) {
		var t = $(this).attr("t");
		t && (t = btns[t]) && (t.call(this, e));
	});
});

function getWhereClaus() {

}

function clickZtreeFunc(treeId, treeNode, clickFlag) {
	var key = "clickNode", $tree = $("#" + treeId);
	if (!treeNode.isParent && $tree.data(key) !== treeNode) {
		window.getColumns(window._treeNode = treeNode, function(columns){
			$("#grid-2").grid("load", columns);
		});
	}
	$tree.data(key, treeNode);
}


$queue.add(function() {
	initZTree(window.getTableType(), window.getSystemName());
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
			s.grid.dataSource.data()[s.index][options.field] = $(this).val();
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

function checkboxTemplate(dataItem, key) {
	var opt = {
		t : dataItem.uid,
		type : 'checkbox',
		name : key,
		onclick : "clickCheckbox(this);"
	};
	if (dataItem[key] == true || dataItem[key] == 'true')
		opt.checked = true;
	return $("<input />", opt).outer();
}

function clickCheckbox(o) {
	var $this = $(o);
	var s = editors.stuff($this);
	var data = s.grid.dataSource.getByUid($this.attr("t"));
	data[$this.attr("name")] = o.checked;
}

function ztreeBeforeClick(treeId, treeNode, clickFlag) {
	var key = "clickNode", $tree = $("#" + treeId);
	if (!treeNode.isParent && $tree.data(key) !== treeNode) {
		window.getColumns(window._treeNode = treeNode, function(columns) {
			$("#grid-2").grid("load", columns);
		});
	}
	$tree.data(key, treeNode);
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
		notEempty : true
	}, url = contextPath + "/mx/expression/defined/view?" + $.param(params);
	window.open(url);
}
function getColTbExp() {
	var $o = $($.data(document.body, "expressionObj"));
	var row = $("#grid-2").grid("getRow", $o);
	if (row && row.dataItem) {
		return JSON.parse(row.dataItem);
	}
}
function setColTbField(data) {
	if (data) {
		var $o = $($.data(document.body, "expressionObj"));
		var row = $("#grid-2").grid("getRow", $o);
		if (row) {
			row[$o.attr("name")] = data.expVal;
			$o.val(data.expVal);
			row.dataItem = JSON.stringify(data);
		}
	}
}
function getRowTree() {
	var datas = $("#grid-2").grid("getData");
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
			tableName : window._treeNode.tableName || window._treeNode.dataTableName
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
		$.each(datas, function(i, v) {
			p.children.push({
				code : "~F{" + v.param + "." + v.param + "." + v.param + "}",
				value : "~F{" + v.paramName + "}",
				name : v.paramName
			});
			collection.push({
				pId : table.tableName,
				id : v.id,
				name : v.fname,
				dType : v.dtype,
				t : '',
				code : "~F{" + bd.code + "." + table.tableName + "." + v.dname
						+ "}",
				value : "~F{" + bd.text + "." + table.name + "." + v.fname
						+ "}",
				isParent : false
			});
		});
		return collection;
	}
	return null;
}

/**
 * 保存列表
 */

function initGrid1() {
	var toolbar = "<button class='k-button' id='k-grid-createNew'>增加</button>", //
	btn = "<button class='k-button' onclick='delParam(this)'	 >删除</button>";
	$("#grid-1").kendoGrid({
		editable : true,
		selectable : true,
		toolbar : toolbar,
		columns : [ {
			field : 'fname',
			title : '列名',
			width : 200,
			editor : function(container, options) {
				editors.textbox(container, options).attr({
					readonly : true
				});
			}
		}, {
			field : 'unNull',
			title : '不可空',
			width : 70,
			template : function(dataItem) {
				return checkboxTemplate(dataItem, "unNull");
			}
		}, {
			field : 'defVal',
			title : '默认值',
			width : 200,
			editor : function(container, options) {
				editors.textbox(container, options).attr({
					t : options.model.uid,
					readonly : true
				}).on('dblclick', function(e) {
					openExpress(this);
				});
			}
		}, {
			field : 'paramName',
			title : '参数',
			template : function(dataItem) {
				currentTime++;
				dataItem.param = "col" + currentTime;
				dataItem.paramName = dataItem.fname + " | 参数-" + currentTime;
				return dataItem.paramName;
			},
			editor : function(container, options) {
				editors.textbox(container, options).attr({
					readonly : true
				});
			}
		}, {
			title : "操作",
			width : "120px",
			template : function() {
				return btn;
			}
		} ]
	});
}

// $queue.add(initGrid1);

/**
 * 修改列表
 */
$queue.add(function() {
	var options = {
			clickUpdate : true,
			columns : [
				{
					title : '列名',
					field : 'fname',
					style : "width : 20%;",
					editor : true
				},
				{
					title : '默认值',
					field : 'defVal',
					style : "width : 20%;",
					editor : function(container, options, row) {
						editors.textbox(container, options).attr({
							class : "form-control",
							style : "width:90%; height:90%;",
							readonly : true
						}).val(row[options.field] || '').on(
								'dblclick.form-control', function(e) {
									openExpress(this);
								});
					}
				}, {
					field : 'paramName',
					title : '参数',
					template : function(row, opts) {
						if (row[opts.field])
							return row[opts.field];
						currentTime++;
						row.param = "col" + currentTime;
						row.paramName = row.tableNameCN + " " + row.fname + " | 参数-" + currentTime;
						return row.paramName;
					}
				}, {
					title : 'I',
					field : 'input',
					style : "width: 30px",
					template : function(row, opts) {
						return checkboxTemplate(row, opts);
					}
				}, {
					title : 'O',
					field : 'output',
					style : "width: 30px",
					template : function(row, opts) {
						return checkboxTemplate(row, opts);
					}
				}, {
					title : 'V',
					field : 'variable',
					style : "width: 30px",
					template : function(row, opts) {
						return checkboxTemplate(row, opts);
					}
				}, {
					title : 'NullAble',
					field : 'nullAble',
					style : "width: 30px",
					template : function(row, opts) {
						return checkboxTemplate(row, opts);
					}
				}, {
					title : '主键',
					field : 'isKey',
					style : "width: 30px",
					template : function(row, opts) {
						return checkboxTemplate(row, opts);
					}
				}, {
					title : '操作',
					style : "width : 20%;",
					template : function(row, opts) {
					}
				} ],
		events : {
			onClickCell : function(index, field, value) {

			}
		}
	};
	$("#grid-2").grid(options);
});

/**
 * 
 * @param row
 * @param opts
 * @returns
 */
function checkboxTemplate(row, opts) {
	var field = opts.field, checked = row[field] == true
			|| row[field] == 'true';
	if (field == 'input') {
		if (row[field] === undefined)
			checked = true;
	} else if (field == "variable") {

	} else if (field == "nullAble") {
		if (row.dname != "id")
			checked = true;
	} else if (field == "output") {
		if (row[field] === undefined)
			checked = true;
	}
	row[field] = checked || false;
	return "<div style='text-align:center;'><input class='mt-checkbox' type='checkbox' {0} name='{1}'  /></div>"
			.format(checked ? "checked=true" : "", field);
}

$queue.add(function() {
	$("#grid-2 table").on("click.checkbox.mt-checkbox", "input.mt-checkbox",
			function() {
				var name = $(this).attr("name");
				var row = $("#grid-2").grid("getRow", this);
				row[name] = this.checked;
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
			systemName : window.getSystemName()
		},
		success : function(data) {
			if (fn)
				fn(data);
		}
	});
}

function getPrimaryKeys(tableObj, fn){
	$.ajax({
		url : contextPath + '/mx/dep/base/depBaseWdataSet/getPrimaryKeys',
		type : "POST",
		dataType : 'JSON',
		data : {
			tableName : tableObj.tableName,
			systemName : window.getSystemName()
		},
		success : function(ret){
			fn && fn(ret.keys);
		}
	});
}

function isKey(keys, dname){
	var ret = false;
	if(keys && keys.length){
		for(var i = 0, len = keys.length; i < len; i ++){
			var key = keys[i];
			if(key && (key.toLowerCase() == dname.toLowerCase())){
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
	var collection = [], json = {}, dname;
	window.getPrimaryKeys(tableObj, function(keys){
		var datas = [ window.getKendo($("#select-type")).dataItem().code || 99, 99 ];
		$.each(datas, function(i, type) {
			tableObj.type = type;
			window.getColumnsByTableId(tableObj, function(data) {
				if (data && data.rows) {
					$.each(data.rows, function(index, row) {
						dname = row.dname;
						dname != "id" && (row.nullAble = true);
						
						!row.tableNameCN && (row.tableNameCN = window._treeNode.tableNameCN);
						
						row.isKey = window.isKey(keys, dname);
						
						!json[dname]
						&& ((json[dname] = row) && (collection
								.push(row)));
					});
				}
				if(i === (datas.length - 1)){
					fn && fn(collection);
				}
			});
		});
	});
	return collection;
}

function getSystemName() {
	var k = null, SystemName = !!(k = window.getKendo($("#select-biaoduan"))) ? k
			.value()
			: null;
	return SystemName || "default";
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
 * 页面初始化还原数据
 */
$queue.add(function() {
	if (window.wdataSet && wdataSet.id) {
		$.ajax({
			url : contextPath + '/mx/dep/base/depBaseWdataSet/getById',
			data : {
				id : wdataSet.id
			},
			type : "POST",
			dataType : 'JSON',
			success : function(ret) {
				!!ret && ($.extend(window.wdataSet, ret));
				if (window.wdataSet.ruleJson) {
					window.initPage();
				}
			}
		});
	}
});

/**
 * 页面初始化方法
 */
function initPage() {
	var ruleJson = JSON.parse(wdataSet.ruleJson);
	$("#grid-2").grid("load", ruleJson.columns || []);

	window._treeNode = (ruleJson.table) || { //
		name : wdataSet.tableName,
		tableName : wdataSet.dataTableName,
		dataTableName : wdataSet.dataTableName,
		tableNameCN : wdataSet.tableName
	};

	var $bd = $("#select-biaoduan").data("kendoDropDownList");

	var $type = $("#select-type").data("kendoDropDownList");

	$bd.value((ruleJson.bd || {
		code : 'default'
	}).code);

	$type.value((ruleJson.type || {

	}).code);

	initZTree($type.value(), $bd.value());

	setting.callback.selectNodeById(window._treeNode.id);

	window.showMessage(ruleJson);
}

function showMessage(ret) {
	$("#message").empty().html(
			"<span>{0}</span><hr><span>{1}</span>".format(ret.insertSql,
					ret.updateSql));
}
