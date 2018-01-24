/**
 * 阿里直播播放器
 */
var scanFunc = {
	getValue: function(rule, args) {
		var $this = pubsub.getJQObj(rule,true);
		return $this.scan("getValue");
	},
	getParent: function(rule, args) {
		var $this = pubsub.getJQObj(rule,true);
		return $this.scan("getParent");
	},
	loadFile: function(rule, params,args){
		var $this = pubsub.getJQObj(rule);
		for(var key in params){
			if(params[key]){
				$this.scan("loadFile",params[key]);
				return;
			}
		}
	}
};
pubsub.sub("scan", scanFunc);