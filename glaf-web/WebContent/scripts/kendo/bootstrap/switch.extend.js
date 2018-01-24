/**
 * 网格
 */
var switchFunc = {
	getValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		return $id.find("input[type='checkbox']").bootstrapSwitch("state");
	},
	setValue : function(rule,args){
		var $id = pubsub.getJQObj(rule), r, v;
		return $id.find("input[type='checkbox']").bootstrapSwitch("state");
	},
	tdisabled: function(rule,args){
		var $id = pubsub.getJQObj(rule), r, v;
		$id.find("input[type='checkbox']").bootstrapSwitch("disabled",true);
	},
	tenabled: function(rule,args){
		var $id = pubsub.getJQObj(rule), r, v;
		$id.find("input[type='checkbox']").bootstrapSwitch("disabled",false);
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

pubsub.sub("switch", switchFunc);