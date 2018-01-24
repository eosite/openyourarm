/**
 * 
 */
var reviewAreaFunc = {
	getClickRowData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).reviewArea("getClickRowData"),
			val = [];
		return dataItems[rule.columnName];
	},
	getAllData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).reviewArea("getAllData"),
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
		pubsub.getJQObj(rule).reviewArea("query", params);
	},
	expandNode : function(rule,params){
		var $id = pubsub.getJQObj(rule);
		var values=params[rule[0].param];
		
		var key = params.key;
		key = key || 'id';

		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'key'){
				return true;
			}
			value = params[rule[i].param] || "";
		})
		if(!value){
			return;
		}
		$id.reviewArea("expandNodeByParam", key,value);
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	}
};
pubsub.sub("reviewArea", reviewAreaFunc);