var customFunc = {
	init: function(rule, args) {
		$("#" + rule.inid).data("retSource", args[0]);
		customFunc.setValue($("#" + rule.inid), "");
	},
	setValue: function(jq, args) {
		var rets = jq.data("retSource"),
			length = rets.length,
			ret, key, val;
		if (length) {
			for (var i = 0; i < length; i++) {
				ret = rets[i];
				key = customFunc._convert(ret.key, args, 'key');
				if (eval(key) || key == "true") {
					jq.html(customFunc._convert(ret.val, args, 'val'));
					var onLoadSucessFunc =jq.data("onLoadSucess");
					if($.isFunction(onLoadSucessFunc)){
						onLoadSucessFunc();
					}
					return;
				}
			}
		}
	},
	_convert: function(k, v, type) {
		var length = v.length,
			o, par, val, reger, tv = (type == 'key' ? "'" : "");
		if (length) {
			for (var i = 0; i < length; i++) {
				o = v[i];
				par = o.param;
				val = o.value;
				reger = new RegExp("##" + par + "##", "gm");
				k = k.replace(reger, tv + val + tv);
			}
		}
		return k.replace(/\\"/gm, "'").replace(/##col\w*##/gm, type == 'key' ? "''" : "");
	},
	_paramsToArgs: function(params) {
		var args = [],
			arg;
		for (var p in params) {
			arg = {};
			arg.param = p;
			arg.value = params[p];
			args.push(arg);
		}
		return args;
	},
	linkage: function(rule, params) {
		customFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) { // 联动控件
		var jq = pubsub.getJQObj(id),
			rets = jq.data("retSource"),
			length = rets.length,
			ret, key, val, args = customFunc._paramsToArgs(params);
		if (length) {
			for (var i = 0; i < length; i++) {
				ret = rets[i];
				key = customFunc._convert(ret.key, args, 'key');
				if (eval(key) || key == "true") {
					if(params.esc){
						jq.html($(customFunc._convert(ret.val, args, 'val')).text());	
					}else{
						jq.html(customFunc._convert(ret.val, args, 'val'));
					}
					var onLoadSucessFunc = jq.data("onLoadSucess");
					if($.isFunction(onLoadSucessFunc)){
						onLoadSucessFunc();
					}
					return;
				}
			}
		}
	}
};
pubsub.sub("custom", customFunc);