var datetimepickerFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		return kendo.widgetInstance($id).value();
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		kendo.widgetInstance($id).value(v);
	},
	thidden : function(rule, args) {// 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').hide();
	},
	tshow : function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').show();
	},
	tdisabled : function(rule, args) {// 禁用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(false);
	},
	tenabled : function(rule, args) {// 启用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(true);
	},
};
pubsub.sub("datetimepicker", datetimepickerFunc);

var datepickerFunc = $.extend({}, datetimepickerFunc, {

});
pubsub.sub("datepicker", datepickerFunc);