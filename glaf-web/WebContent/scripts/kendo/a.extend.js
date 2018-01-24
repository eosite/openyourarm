/**
 * 链接
 */
var aFunc = {
	getValue : function(rule, args) {
		return pubsub.getJQObj(rule, true).html();
	},
	getActVal : function(rule, args) {
		return pubsub.getJQObj(rule, true).attr("actVal");
	},
	setValue:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.find("p").html(v);
		} else {
			$id.find("p").html(args);
		}
	}
};
pubsub.sub("a", aFunc);