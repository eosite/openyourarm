/**
 * 
 */
var videoFunc = {
	getRow : function(rule, args) {
		return null;
	},
	linkage : function(rule, params) {
		videoFunc.linkageControl(rule, params);
	},
	linkageControl : function(rule, params) {
		var $this = pubsub.getJQObj(rule)  ;
		var drole = $this.attr('drole');
		var model = $this.attr('model');
		if(drole && "ys"==drole){ //萤石云模式
			$this.ysVideo("link",{params:params});
		} else if(model && "traffic"==model){
			var sparams = {
					params : JSON.stringify(params) || null
				};
				sparams.rid = $this.attr("rid");
				$.ajax({
					url : contextPath + '/mx/form/data/getVideoSetByTraffic',
					data : sparams,
					type : "POST" ,
					dataType : "JSON",
					async : false,
					success : function(data) {
						if (data && data[0]) {
							loginNetVideo(data[0]);
						}
					}
				});
		}else{
			var sparams = {
				params : JSON.stringify(params) || null
			};
			sparams.rid = $this.attr("rid");
			$.ajax({
				url : contextPath + '/mx/form/data/getVideoSet',
				data : sparams,
				type : "POST" ,
				dataType : "JSON",
				async : false,
				success : function(data) {
					if (data && data[0]) {
						loginNetVideo(data[0]);
					}
				}
			});
		}
		
		
	}
};
pubsub.sub("video", videoFunc);