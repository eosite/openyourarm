/**
 * 组织机构图表
 */
var diagramFunc = {
	init : function(rule, args) {
		var options = args[0];
		$("#" + rule.inid).kendoDiagram(options);
	},
	getValue : function(rule, args) {
		return args[1].indexId;
	},
	getNode : function(rule, args) {
		return args[1][rule.columnName];
	}
};
pubsub.sub("diagram", diagramFunc);