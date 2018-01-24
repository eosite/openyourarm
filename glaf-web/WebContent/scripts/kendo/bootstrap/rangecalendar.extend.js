/**
 * 
 */
var rangecalendarFunc = {
	getValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule,true);
		return $id.rangecalendarext("getValue");
	},
	linkage: function(rule, params) {
		pubsub.getJQObj(rule).rangecalendarext("query", params);
	}
};
pubsub.sub("rangecalendar", rangecalendarFunc);