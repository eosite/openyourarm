/**
 * Õ¤¸ñÏµÍ³
 */
var carouselFunc = {
	linkage: function(rule,params,args){
		var $this = pubsub.getJQObj(rule);
		$this.carouselbt("loadData",$.isArray(params)?{}:params) ;
	},
	refresh: function(rule,params,args){
		var $this = pubsub.getJQObj(rule);
		$this.carouselbt("refresh") ;
	}
};
pubsub.sub("carousel", carouselFunc);