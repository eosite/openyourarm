/**
 * 下拉框
 */
var comboboxFunc = $.extend({}, {}, {
	getText : function(rule, args) {
		var d = pubsub.getJQObj(rule, true);
		var k = kendo.widgetInstance(d)
		return k.text();
	},
	getValue : function(rule, args) {
		var d = pubsub.getJQObj(rule, true);
		var k = kendo.widgetInstance(d)
		return k.value();
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
	linkage : function(id, params) {
		var targer = pubsub.getJQObj(id);
		var widget = kendo.widgetInstance(targer);
		var data = widget.dataSource.transport.options.read.data;
		data.params = JSON.stringify(params);
		widget.dataSource.read();
	},
	linkageControl : function(id, params) {
		comboboxFunc.linkage(id,params);
	}

});
pubsub.sub("combobox", comboboxFunc);

var dropdownlistFunc = $.extend({}, comboboxFunc, {});
pubsub.sub("dropdownlist", dropdownlistFunc);


pubsub.sub("autocomplete", comboboxFunc);