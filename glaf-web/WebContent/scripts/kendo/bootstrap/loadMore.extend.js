
var loadMoreFunc = {
		
		thidden : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("loadMore").thidden();
		},
		tshow : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("loadMore").tshow();
		}
};
pubsub.sub("loadMore", loadMoreFunc);