/**
 * 
 */
var loginmessagevalidFunc = {
	getValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule,true);
		return $id.loginmessagevalid("getValue");
	},
	setTelephone: function(rule,args){
		var value;
		$.each(args,function(i,item){
			value = item;
		})
		var $this = pubsub.getJQObj(rule) ;
		return $this.loginmessagevalid("setTelephone",value);
	}
};
pubsub.sub("loginmsgvalid", loginmessagevalidFunc);