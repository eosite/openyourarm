var gridFunc = {
	getText: function(rule) { // 获取单元格的值
		// var targer = jQuery('#' + rule.inid);
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.select();
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].innerText){
					value.push(rows[i].innerText);
				}
			}
		}
		return value.join(",");
	},
	getRow: function(rule) { // 获取选中行的字段
		// var targer = jQuery('#' + rule.inid);
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.select();
			for (var i = 0; i < rows.length; i++) {
				var data = widget.dataItem(rows[i]);
				if(data[rule.columnName]){
					value.push(data[rule.columnName]);
				}
			}
		}
		return value.join(",");
	},
	getAll: function(rule, args) {
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.dataItems();
			for (var i = 0; i < rows.length; i++) {
				var data = rows[i];
				value.push(data[rule.columnName]);
			}
		}
		return value.join(",");
	},
	linkage: function(rule, params) {
		gridFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) {
		// var targer = jQuery('#' + id);
		var targer = pubsub.getJQObj(id);
		params["databaseId"] && targer.attr("dbid",params["databaseId"]);
		var widget = kendo.widgetInstance(targer);
		if (widget) {
			var data = widget.dataSource.transport.options.read.data;
			data.params = JSON.stringify(params);
			// 传入创建父ID
			var create = widget.dataSource.transport.options.create;
			if (create) {
				var createData = create.data;
				createData.params = JSON.stringify(params);
			}
			var update = widget.dataSource.transport.options.update;
			if (update) {
				var updateData = update.data;
				updateData.params = JSON.stringify(params);
			}
			widget.dataSource.read();
		}
	},
	gsaveChange: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			// var kg = $id.data('kendoGrid') || $id.data('kendoTreeList');
			var kg = kendo.widgetInstance($id);
			kg.saveChanges();
		}
	},
	grefresh: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			// var kg = $id.data('kendoGrid') || $id.data('kendoTreeList');
			var kg = kendo.widgetInstance($id);
			kg.dataSource.read();
		}
	},
	tSelectFirst: function(rule, args) { //选中第一行
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			setTimeout(function() {
				var kg = kendo.widgetInstance($id);
				var isGrid = $id.data("role") == "grid";
				kg.select(isGrid ? ( /*"#" + $id.attr("id") + */ " tbody tr:eq(0)") : $id.find("tbody tr:eq(0)"));
				var ink = new MutationObserver(function(record) {
					kg.select(isGrid ? ( /*"#"+$id.attr("id")+*/ " tbody tr:eq(0)") : $id.find("tbody tr:eq(0)"));
					ink.disconnect();
				});
				ink.observe($id[0], {
					'childList': true,
					'arrtibutes': true,
					'subtree': true,
					'characterData': true
				});
			}, 100);
		}
	},
	cancelSelect: function(rule, args) { //取消选中
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			// var kg = $id.data('kendoGrid') || $id.data('kendoTreeList');
			var kg = kendo.widgetInstance($id);
			var isGrid = $id.data("role") == "grid";
			if (isGrid) {
				kg.cancelChanges();
			} else {
				kg.clearSelection();
			}
		}
	},
	selectRow: function(rule, params,args) { //恢复选中事件
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var widget = kendo.widgetInstance($id);
			if (widget) {
				var isGrid = $id.data("role") == "grid";
				var rows = widget.dataItems();
				if (rows && rows.length) {
					$.each(rows,function(i,row){
						for(var param in params){
							var ps = params[param], pas = ps.split(",");
							for(var j=0;j<pas.length;j++){
								if(row.id == pas[j]){
									widget.select(isGrid ? "tbody tr:eq("+i+")" : $id.find("tbody tr:eq"+i+")"));
								}
							}
						}
					});
				}
			}
		}
	},
	addNode: function(rule, args){
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var kg = kendo.widgetInstance($id);
			var isGrid = $id.data("role") == "grid";
			if (isGrid) {
				//kg.cancelChanges();
			} else {
				var selectRow = kg.select();
				if(selectRow.length==1){
					kg.addRow(selectRow);
				}else if(selectRow.length==0){
					kg.addRow();
				}
			}
		}
	}
};
//注册 grid
pubsub.sub("grid", gridFunc);
pubsub.sub("treelist", gridFunc);
// ********************************** kendo grid 相关
// ***************************************************************
var glafGrid = {
	// 打开链接
	openLink: function(t, path) {
		var ele = $(t);
		window.open(path + '/mx/form/page/viewPage?id=' + ele.attr('link'));
	},
	treelistExpand: function(e) {
		if (e.data.isExpand) {
			var params = e.sender.dataSource.transport.options.read.data.params;
			if (params && !$.isEmptyObject(JSON.parse(params)) && $(e.sender).data('expandTime')) {
				var rows = e.sender.tbody.find("tr");
				if (rows && rows.length < 10) {
					for (var j = 0; j < rows.length; j++) {
						e.sender.expand(rows[j]);
					}
				}
				$(e.sender).data('expandTime',($(e.sender).data('expandTime')||0)+1);
			}else{
				$(e.sender).data('expandTime',1);
			}
		}
	},
	// k:kendo的Grid对象 linkType:联动类型 ids:联动的对象ID集合
	clickFunction: function(k, linkType, ids) {
		// 获取选中行
		var selectedRows = k.select();
		var dataItem = k.dataItem(selectedRows[0]);
		// console.log(k);
		var prid = k.dataSource.transport.options.read.data.rid;
		// console.log(dataItem);
		for (var i = 0; i < ids.length; i++) {
			// 获取目标组件
			var targer = jQuery('#' + ids[i].name);
			if (linkType == 'linkageControl') {
				var widget = kendo.widgetInstance(targer);
				var woname = widget.options.name;
				if (woname == "Grid" || woname == "TreeList") {
					var data = widget.dataSource.transport.options.read.data;
					data.parentId = (k.options.name == "Grid" ? dataItem.id : dataItem.startId);
					data.prid = prid;
					widget.dataSource.read();
					// 传入创建父ID
					var createData = widget.dataSource.transport.options.create.data;
					createData.parentId = (k.options.name == "Grid" ? dataItem.id : dataItem.startId);
					createData.prid = prid;
				}
			}
		}
	},
	// Grid checkbox选择行
	selectRow: function(that, id, ids) {
		var checked = that.checked,
			row = $(that).closest("tr"),
			grid = $("#" + id).data("kendoGrid"),
			dataItem = grid.dataItem(row);
		ids[dataItem.id] = checked;
		if (checked) {
			row.addClass("k-state-selected");
		} else {
			row.removeClass("k-state-selected");
		}
	},
	// Grid checkbox全选框
	selectAllRow: function(that, id, ids) {
		var dataItem, row, checked = that.checked,
			grid = $("#" + id).data("kendoGrid"),
			dataItems = grid.dataSource.data();
		var checkboxs = $("." + id + "_checkbox");
		$.each(checkboxs, function(i, n) {
			n.checked = checked;
			row = $(n).closest("tr");
			dataItem = grid.dataItem(row)
			ids[dataItem.id] = checked;
			if (checked) {
				row.addClass("k-state-selected");
			} else {
				row.removeClass("k-state-selected");
			}
		});
	},
	// 翻页单选
	onDataBound: function(e, id, ids) {
		var checkbox = $("." + id + "_checkbox_All");
		checkbox[0].checked = false;
		var view = e.sender.dataSource.view();
		var count = 0;
		for (var i = 0; i < view.length; i++) {
			if (ids[view[i].id]) {
				e.sender.tbody.find("tr[data-uid='" + view[i].uid + "']").addClass("k-state-selected").find("." + id + "_checkbox").attr("checked", "checked");
				count++;
			}
		}
		if (view.length == count) {
			checkbox[0].checked = true;
		}
	},
	// 启用1、禁用0 validityFlag
	toolbar_enable: function(type, e) {
		if (!confirm("确定要" + (type == 1 ? "启用" : "禁用") + "吗？")) {
			return;
		}
		var grid = $("#" + e.getAttribute("gid")).data("kendoGrid");
		var rows = grid.select();
		for (var i = 0; i < rows.length; i++) {
			var data = grid.dataItem(rows[i]);
			if (data.hasOwnProperty('validityFlag')) {
				data.set("validityFlag", type);
			} else {
				alert('当前无法使用启用、禁用功能!');
				return;
			}
		}
		grid.dataSource.sync();
	},
	// 全局提示
	showTooltip: function(id) {
		$("#" + id).kendoTooltip({
			width: 200,
			filter: "td:not([data-container-for]):not(:contains('编辑删除')):not(:contains('更新取消'))",
			position: "right",
			content: function(e) {
				var td = e.target.closest("td");
				var content = td[0].innerText;
				return content;
			}
		}).data("kendoTooltip");
	},
	// 显示数据源的值
	getGridDataSourceValue: function(array, value, t, v) {
		var length = arguments.length;
		for (var i = 0; i < array.length; i++) {
			var obj = array[i];
			if (length < 4) {
				if (obj.value == value) {
					return obj.text;
				}
			} else {
				if (obj[v] == value) {
					return obj[t];
				}
			}
		}
		return "";
	},
	searchGrid: function() {
		var array = this.wrapper.find("input[cn]");
		// grid
		var targer = jQuery('#' + this.wrapper.attr('grid'));
		var widget = kendo.widgetInstance(targer);
		var data = widget.dataSource.transport.options.read.data;
		// "filter":{"logic":"and","filters":[{"field":"cell_useradd7028_user3","operator":"eq","value":22}]
		var filter = {};
		var fiobj = {
			"logic": "and"
		};
		var farray = [];
		for (var i = 0; i < array.length; i++) {
			var ele = jQuery(array[i]);
			var obj = new Object();
			obj['field'] = ele.attr('cn');
			if (ele.attr('ft') == 'checkbox') {
				obj['value'] = ele.val() == 'true' ? 1 : 0;
			} else {
				obj['value'] = ele.val();
			}
			obj['operator'] = "eq";
			if (ele.val() != '') {
				farray.push(obj);
			}
		}
		fiobj.filters = farray;
		data.filter = fiobj;
		widget.dataSource.read();

	},
	searchClear: function() {
		var array = this.wrapper.find("input[cn]");
		for (var i = 0; i < array.length; i++) {
			kendo.widgetInstance(jQuery(array[i])).value('');
		}
	},
	hiddenSearchToolbar: function(e) {
		if ($(e).html() == '隐藏') {
			$(e).html('显示');
			$('#' + e.getAttribute("gid") + '_toolbar').hide('fast', 'swing');
		} else {
			$(e).html('隐藏');
			$('#' + e.getAttribute("gid") + '_toolbar').show('fast');
		}
	}
};