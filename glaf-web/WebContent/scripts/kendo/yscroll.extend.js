/**
 * 图片轮播
 */
var yscrollFunc = {
	linkage : function(rule, params) {
		yscrollFunc.linkageControl(rule, params);
	},
	linkageControl : function(id, params) {
		var target = pubsub.getJQObj(id);
		var opts = target.data("opts");
		if(opts && opts.params){
			if(params.databaseId){
				target.data("databaseId",params.databaseId);
			}
			opts.params.params = JSON.stringify(params) ;
			target.yScroll("init",opts);
		}else{
			target.data("params",params);
		}
	},
}
pubsub.sub("yscroll", yscrollFunc);