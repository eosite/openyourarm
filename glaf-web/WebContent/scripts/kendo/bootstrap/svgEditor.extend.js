/**
 * 链接
 */
var svgEditorFunc = {
	setValue:function(rule, params){
		var $jqObj = pubsub.getJQObj(rule);
		for(var key in params){
			$jqObj.svgEditor("setValue",params[key]);
		}
	},
	getValue:function(rule, args){
		var $jqObj = pubsub.getJQObj(rule,true);
		return $jqObj.svgEditor("getValue");
	}
};
pubsub.sub("svgeditor", svgEditorFunc);