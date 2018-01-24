/**
 * 网格
 */
var videoplayFunc = {
	setVideoSource: function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		var sourceObj = {
			src: v, 
			type: "video/" + v.substring(v.lastIndexOf(".")+1)
		}
		$id.videoExt("setVideoSource",sourceObj);
	},
	setVideoPreImg: function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		var posterObj = {
			src: v, 
			type: "video/" + v.substring(v.lastIndexOf(".")+1)
		}
		// var posterSrc = v;

		$id.videoExt("setVideoPreImg",posterObj);
	}
};

pubsub.sub("videoplay", videoplayFunc);