/**
 * Bootstrap Alert
 */
var btalertFunc = {
	open : function(rule, args) {
		pubsub.getJQObj(rule).btalert("open", args);
	},
	close : function(rule, args){
		pubsub.getJQObj(rule).btalert("close", args);
	}
};
pubsub.sub("btalert", btalertFunc);