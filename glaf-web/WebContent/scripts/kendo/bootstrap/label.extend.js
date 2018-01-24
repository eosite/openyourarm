/**
 * label事件定义器
 */
var labelFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		var $span = $id.find("span").length>0?$id.find("span"):$id;
		return $span.html();
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.attr("setValue", "setValue");
		var $span = $id.find("span.frame-variable").length>0?$id.find("span.frame-variable"):$id;

		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$span.html(pubsub.htmlUnescape4Html(v));
		} else {
			$span.html(pubsub.htmlUnescape4Html(args));
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.show();
	}
};
pubsub.sub("label", labelFunc);

