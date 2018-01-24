/**
 * touchspin事件定义器
 */
var touchSpinFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.touchSpinExt("getValue");
	},
	getSumValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		var $thats = $id.closest("table").find("[columnname=" + $id.attr("columnname") + "]");
		var retVal = 0;
		$.each($thats, function(index, el) {
			retVal = mtMath.add(retVal, parseFloat($(this).touchSpinExt("getValue") || 0));
		});
		return retVal;
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param] != null ? args[r.param] : "";
			}
			$id.touchSpinExt("setValue", v);
		} else {
			$id.touchSpinExt("setValue", args);
		}
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.textboxBtExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.touchSpinExt("disabled", false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.touchSpinExt("disabled", true);
	}

};
pubsub.sub("touchspin", touchSpinFunc);