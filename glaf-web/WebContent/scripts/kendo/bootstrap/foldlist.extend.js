/**
 * 
 */
var foldlistFunc = {
	getSelectedData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).foldlistext("getSelectedData"),
			val = [];
		return dataItems[rule.columnName];
	},
	getAllData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).foldlistext("getAllData"),
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
		pubsub.getJQObj(rule).foldlistext("query", params);
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	}
};
pubsub.sub("foldlist", foldlistFunc);