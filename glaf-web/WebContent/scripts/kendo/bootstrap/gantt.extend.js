/**
 * Gantt
 */
var ganttFunc = {
	getRow : function(rule, args) {
		var index = args[0];
		var task = args[1] || {};
		return task["row"][rule.columnName] || '';
		//return pubsub.getJQObj(rule, true).iframegantt("getRow", args);
	},
	save : function(rule, args){
		return pubsub.getJQObj(rule).iframegantt("save", args);
	},
	load : function(rule, args){
		return pubsub.getJQObj(rule).iframegantt("load", $.isArray(args)?{}:args);
	}
};
pubsub.sub("gantt", ganttFunc);