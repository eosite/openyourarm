/**
 * Metronic select2
 */
var customSelectFunc = {
	reload: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect("reload", args);
	},
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).metroselect("getValue");
	},
	getData: function(rule, args) {
		var data = pubsub.getJQObj(rule, true).find('select').select2('data')[0].data;
		if (data) {
			return data[rule.columnName] || "";
		} else {
			return "";
		}
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param] || "";
		}
		$id.metroselect('select', v);
	},
	getText: function(rule, args) {
		// var msg = pubsub.getJQObj(rule, true).select2('data')[0].text != '' ? pubsub.getJQObj(rule, true).select2('data')[0].text : null;
		var msg = pubsub.getJQObj(rule, true).metroselect("getText");
		return msg;
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show();
	},
	tdisabled: function(rule, args) { // 禁用
		pubsub.getJQObj(rule).find("select").attr("disabled", "disabled");
	},
	tenabled: function(rule, args) { // 启用
		pubsub.getJQObj(rule).find("select").removeAttr("disabled");
	},
	clear: function(rule, args) {
		pubsub.getJQObj(rule).metroselect("clear");
	},


	disable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("disable", args.index);
		}
	},
	enable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("enable", args.index);
		}
	},
	change: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect("change", args);
	},
	select: function(rule, args) {
		if (args.value) {
			pubsub.getJQObj(rule).metroselect("select", args.value);
		}
	},
	unselect: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("uncheck", args.index);
		}
	}

};
pubsub.sub("metroselect", customSelectFunc);



