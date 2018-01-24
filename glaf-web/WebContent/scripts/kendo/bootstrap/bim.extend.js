/**
 * 链接
 */
var bimFunc = {
	setValue:function(rule, params){
		var $jqObj = pubsub.getJQObj(rule);
		for(var key in params){
			$jqObj.bim("setValue",params[key]);
		}
	},
	select2View : function(rule, params){
		var $jqObj = pubsub.getJQObj(rule);
		for(var key in params){
			$jqObj.bim("select2View", params[key]);
		}
	},
	getData : function(rule, params){
		var $jqObj = pubsub.getJQObj(rule, true);
		if($jqObj && $jqObj.get(0)){
			params = $jqObj.bim("GetSelection");
		}
		if(params[0]){
			return params[0][rule.columnName];
		}
		return null;
	}
};
pubsub.sub("bim", bimFunc);