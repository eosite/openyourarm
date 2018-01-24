/**
 * 
 */
var mswitchFunc = {
	getValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule,true), r, v;
		return $id.data("switchValue");
	},
	tdisabled: function(rule,args){
		var $id = pubsub.getJQObj(rule), r, v;
		$id.find(".mui-switch").addClass("mui-disabled");
	},
	tenabled: function(rule,args){
		var $id = pubsub.getJQObj(rule), r, v;
		$id.find(".mui-switch").removeClass("mui-disabled");
	},
	thidden: function(rule,args){
		var $id = pubsub.getJQObj(rule), r, v;
		$id.hide();
	},
	tshow: function(rule,args){
		var $id = pubsub.getJQObj(rule), r, v;
		$id.hide();
	}
};
pubsub.sub("mswitch", mswitchFunc);