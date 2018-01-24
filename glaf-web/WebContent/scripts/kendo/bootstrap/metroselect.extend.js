/**
 * Metronic select2
 */
var metroselectFunc = {
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
		var $target = pubsub.getJQObj(rule);
		var $select = pubsub.getJQObj(rule).find("select");
		$target.attr("enabled","false");
		$select.attr("disabled", "disabled");
		$select.prop("disabled", "disabled");
	},
	tenabled: function(rule, args) { // 启用
		var $target = pubsub.getJQObj(rule);
		var $select = $target.find("select");
		$target.attr("enabled","true");
		$select.removeAttr("disabled");
		$select.prop("disabled", false);
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
pubsub.sub("metroselect", metroselectFunc);



/**
 * metroselectMFunc
 */
var metroselectMFunc = {
	reload: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect_m("reload", args);
	},
	//多选下拉框值携带有","符号会导致页面CRUD操作异常，因此将","换成"，"
	getValue: function(rule, args,obj) {
		// return pubsub.getJQObj(rule, true).metroselect_m("getValue");
		var value = pubsub.getJQObj(rule, true).metroselect_m("getValue");
		pubsub.outParamsObj.call(0,rule, obj, {value:value});
		return value;
	},
	getData: function(rule, args,obj) {
		var data = pubsub.getJQObj(rule, true).find('select').select2('data');
		var _rowIndex = 'row-index',
			val = [],
			item;
		for (var j = 0; j < data.length; j++) {
			item = data[j]["data"][rule.columnName] || "";
			val.push(item);
			pubsub.outParamsObj.call(j,rule, obj, item);
		}
		return val.join(",");
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param] || "";
		}
		if(v){
			v = v.split(",");	
		}
		$id.metroselect_m('select', v);
	},
	//多选下拉框值携带有","符号会导致页面CRUD操作异常，因此将","换成"，"
	getText: function(rule, args,obj) {
		var value = pubsub.getJQObj(rule, true).metroselect_m("getText");
		pubsub.outParamsObj.call(0,rule, obj, {text:value});
		return value;
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
		pubsub.getJQObj(rule).metroselect_m("clear");
	},



	disable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect_m("disable", args.index);
		}
	},
	enable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect_m("enable", args.index);
		}
	},
	change: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect_m("change", args);
	},
	select: function(rule, args) {
		if (args.value) {
			pubsub.getJQObj(rule).metroselect_m("select", args.value);
		}
	},
	unselect: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect_m("uncheck", args.index);
		}
	},
	selectIndex: function(rule, args) {
		if (args.index != null) {
			pubsub.getJQObj(rule).metroselect_m("selectIndex", args.index);
		}
	}

};
pubsub.sub("metroselectm", metroselectMFunc);


/**
 * Metronic select2
 */
var selectFunc = {
	selectIndex: function(rule,args){
		var $that = pubsub.getJQObj(rule);

	},
	reload: function(rule, args) {
		var $that = pubsub.getJQObj(rule),
			options = $that.data("select2").options.options,
			params = $.extend({}, options.editor.params, {
				params: JSON.stringify(args)
			});
		$.ajax({
			url: contextPath + options.editor.url.replace("contextPath", ""),
			type: 'POST',
			dataType: 'JSON',
			contentType: "application/x-www-form-urlencoded",
			data: params,
			success: function(data) {
				var cons = [];
				if (data && data.length) {
					$.each(data, function(i, v) {
						v["id"] = v[options.editor.params.value];
						v["text"] = v[options.editor.params.text];
						cons.push(v);
					});
				}
				var oldData = $that.select2("data") || [];
				$that.empty();
				$that.select2($.extend({},options,{
					data: cons
				}))
				if(oldData[0] || !options.editor.defaultIndex){
					$that.val(oldData[0]?oldData[0].value:"").trigger("change");
				}else if(cons[options.editor.defaultIndex-1]){
					$that.val(cons[options.editor.defaultIndex-1].id).trigger("change");
				}
			},
			async: true
		});
		return $that;
	},
	getValue: function(rule, args) {
		var data = pubsub.getJQObj(rule, true).val();
		if(typeof data == 'object'){
			var value = "";
			$.each(data,function(i,item){
				if(item){
					value && (value += ",");
					value += item;	
				}
			})
			return value;
		}else{
			return data;
		}
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
		$id.val(v).trigger("change");
	},
	getText: function(rule, args) {
		return pubsub.getJQObj(rule, true).metroselect("getText");
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
		pubsub.getJQObj(rule).val("").trigger("change");
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
pubsub.sub("select", selectFunc);
pubsub.sub("select2", selectFunc);