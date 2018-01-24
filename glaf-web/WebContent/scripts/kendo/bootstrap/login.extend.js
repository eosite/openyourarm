/**
 * 登录事件
 */
var loginUserNameFunc = {

	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.find("input").val();
	}
	
};
pubsub.sub("loginusername", loginUserNameFunc);


var loginPasswordFunc = {

	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.find("input").val();
	}
	
};
pubsub.sub("loginpassword", loginPasswordFunc);



var loginVerificationFunc = {

	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.find("input").val();
	}
};
pubsub.sub("login_verify", loginVerificationFunc);


var loginAlertFunc = {

	setValue: function(rule, args) { //取值
		var $span = pubsub.getJQObj(rule).find("span"), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$span.html(v);
	}
};
pubsub.sub("loginalert", loginAlertFunc);


var loginFormFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.attr("alert");
	}
};
pubsub.sub("loginform", loginFormFunc);