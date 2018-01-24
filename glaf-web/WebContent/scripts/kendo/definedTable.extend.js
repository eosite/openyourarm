/**
 * 自定义表格
 */
var definedTableFunc = {
	getPage : function(rule,args){
		return pubsub.getJQObj(rule,true).definedTable("getPage");
	},
	getRow : function(rule, args) {
		// var target = mtxx.e.target;
		// if (target) {
		// 	var dataItem = pubsub.getJQObj(rule,true).definedTable("dataItem", target)
		// 	if (dataItem) {
		// 		return dataItem[rule.columnName];
		// 	}
		// }
		
		var dataItem = pubsub.getJQObj(rule,true).definedTable("getSelectedRowsData");
		if(dataItem && dataItem[0]){
			return dataItem[0][rule.columnName];
		}

		/*
		 * var widget = kendo.widgetInstance(targer); var rows =
		 * widget.select(); var value = []; for (var i = 0; i < rows.length;
		 * i++) { var data = widget.dataItem(rows[i]);
		 * value.push(data[rule.columnName]); } return value.join(",");
		 */
		return null;
	},
	getValue : function(rule, args) {
		return pubsub.getJQObj(rule,true).closest("a").text();
	},
	linkage : function(rule,args){
		definedTableFunc.linkageControl(rule,args);
	},
	linkageControl : function(id, params) {
		pubsub.getJQObj(id).definedTable("query", $.isArray(params)?{}:params);
	},
	getAll : function(rule, args) {
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("definedTable").rows, dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName]);
				}
				return retAry.join(",");
			}
		}
		return null;
	},
	getAllRows : function(rule,args){
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("definedTable").allRows, dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName]);
				}
				return retAry.join(",");
			}
		}
		return null;
	},
	getFirstRow : function(rule, args) {
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("definedTable").rows, dataItem;
			if (dataItems && dataItems[0]) {
				return dataItems[0][rule.columnName];
			}
		}
		return null;
	}
};
pubsub.sub("definedTable", definedTableFunc);