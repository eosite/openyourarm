/**
 * 链接
 */
var wyvideoFunc = {
	linkage:function(rule, params,args){
		var $this = pubsub.getJQObj(rule);
		for(var key in params){
			var param =  params[key];
			if(param){
				$this.wyvideo("play",param);
			}
		}
	}
};
pubsub.sub("wyvideo", wyvideoFunc);