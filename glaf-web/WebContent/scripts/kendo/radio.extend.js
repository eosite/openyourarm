/**
 * 单选按钮
 */
var radioFunc = {
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).val();
	},
	isChecked: function(rule, args) {
		return pubsub.getJQObj(rule, true).is(":checked");
	},
	getSelect: function(rule, args) {
		var t = pubsub.getJQObj(rule, true);
		var s = t.parents("body").find("input[name=" + t.attr("name") + "]:checked");
		return s.val();
	},
	selectNode: function(rule, params, args) {
		var t = pubsub.getJQObj(rule);
		var s = t.parents("body").find("input[name=" + t.attr("name") + "]");
		s.prop("checked", false);
		$.each(s, function(i, v) {
			var $ele = $(v);
			for (var p in params) {
				if ($ele.val() == params[p]) {
					$ele.prop("checked", true);
				}
			}
		});
	}
};
pubsub.sub("radio", radioFunc);

var checkboxFunc = $.extend(true, {}, radioFunc, {
	selectNode: function(rule, params, args) {
		var t = pubsub.getJQObj(rule);
		var s = t.parents("body").find("input[name=" + t.attr("name") + "]");
		s.prop("checked", false);
		$.each(s, function(i, v) {
			var $ele = $(v);
			for (var p in params) {
				var param = params[p];
				if (param) {
					var ps = param.toString().split(",");
					$.each(ps, function(j, k) {
						if ($ele.val() == k) {
							$ele.prop("checked", true);
						}
					})
				}
			}
		});
	}
});

pubsub.sub("checkbox", checkboxFunc);