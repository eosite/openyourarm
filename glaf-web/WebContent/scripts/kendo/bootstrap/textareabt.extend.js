/**
 * textareabt事件定义器
 */
var textAreaBtFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.textAreaBtExt("getValue");
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.textAreaBtExt("setValue",pubsub.htmlUnescape(v));
		} else {
			$id.textAreaBtExt("setValue",pubsub.htmlUnescape(args));
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("display",true);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.textAreaBtExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("disabled",false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("disabled",true);
	},
	appendSetValue: function(rule, args) {//追加赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		var jq = $id.textAreaBtExt("getOject"),
			bval = jq.val() ? jq.val() + "," : "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.textAreaBtExt("setValue",pubsub.htmlUnescape(bval + v));
		} else {
			$id.textAreaBtExt("setValue",pubsub.htmlUnescape(bval + args));
		}
	}
	
};
pubsub.sub("textareabt", textAreaBtFunc);