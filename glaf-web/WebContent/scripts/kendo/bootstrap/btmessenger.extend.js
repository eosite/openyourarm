/**
 * Bootstrap Messenger
 */
var btmessengerFunc = {
	open : function(rule, args) {
		pubsub.getJQObj(rule).btmessenger("open", args.content);
	},
	close : function(rule, args){
		pubsub.getJQObj(rule).btmessenger("close");
	}
};
pubsub.sub("btmessenger", btmessengerFunc);