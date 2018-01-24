/**
 * 阿里直播播放器
 */
var alivideoFunc = {
	linkage: function(rule, params, args) {
		var $this = pubsub.getJQObj(rule);
		for (var key in params) {
			var param = params[key];
			if (param) {
				$this.alivideo(key == "roomId" ? "playByRoomId" : "play", param);
			}
		}
	}
};
pubsub.sub("alivideo", alivideoFunc);