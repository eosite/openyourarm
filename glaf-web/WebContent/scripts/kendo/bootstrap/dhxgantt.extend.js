/**
 * 
 */
var dhxganttFunc = {
	getSelectedData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).dhxgantt("getSelectedData"),
			val = [];
		return dataItems[rule.columnName];
	},
	getAllData : function(rule, args){
		var dataItems = pubsub.getJQObj(rule, true).dhxgantt("getAllData"),
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
	refreshRow : function(rule,params){
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
		$id.dhxgantt("refreshRowByParam", key,value);
	},
	reExpandParent : function(rule,params){
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
		$id.dhxgantt("reExpandParentByParam", key,value);
	},
	linkage: function(rule, params) {
		pubsub.getJQObj(rule).dhxgantt("query", params);
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
		$id.dhxgantt("expandNodeByParam", key,value);
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	},
	exportToPDF : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.dhxgantt("exportToPDF");
	},
	exportToPNG : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.dhxgantt("exportToPNG");
	},
	upDurationUnit : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.dhxgantt("upDurationUnit");
	},
	downDurationUnit : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.dhxgantt("downDurationUnit");
	},
};
pubsub.sub("dhxgantt", dhxganttFunc);