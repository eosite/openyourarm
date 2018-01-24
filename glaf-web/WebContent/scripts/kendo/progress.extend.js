/**
 * 进度条
 */
var progressbarFunc = {
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		if (v) {
			kendo.widgetInstance($id).value(v);
		}
	},
};
pubsub.sub("progressbar", progressbarFunc);