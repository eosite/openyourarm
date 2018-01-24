/**
 * Metronic Button
 */
var metrolistFunc = {
	reload : function(rule, args) {
		return pubsub.getJQObj(rule).metrolist("reload", args);
	},
	removeItem : function(rule, args){
		return pubsub.getJQObj(rule).metrolist("removeItem", args);
	},
	getDrapRow : function(rule,args){
		var $inid = pubsub.getJQObj(rule,true);
		var column = $inid.metrolist("getDrapRow");
		return column[rule.columnName];
	}
};
pubsub.sub("metrolist", metrolistFunc);