/**
 * 
 */
var levellistFunc = {
	getHeaderData : function(rule,args){
		//获取表头数据
		var dataItems = pubsub.getJQObj(rule, true).levellistext("getHeaderData"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
				}
			});
			return val.join(",");
		}
	},
	getSelectedData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).levellistext("getSelectedData"),
			val = [];
		return dataItems[rule.columnName];
	},
	getAllData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).levellistext("getAllData"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
				}
			});
			return val.join(",");
		}
	},
	linkage: function(rule, params) {
		pubsub.getJQObj(rule).levellistext("query", params);
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	}
};
pubsub.sub("levellist", levellistFunc);