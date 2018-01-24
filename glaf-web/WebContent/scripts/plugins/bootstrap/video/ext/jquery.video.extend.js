! function($) {
	var plugin = 'videoExt';

	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options');
		var columns = $(target).data("columns");
		if(columns&&columns.length>0){
			state.options.columns = eval('('+columns+')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		
	};

	$.fn[plugin].methods = {
		_init: function() {
			var that = this;
			var options = that.options;
			that._createVideo();
			that._bindEvent();
		},
		_bindEvent: function(){
			var that = this;
			//当容器的宽高变化后自动变化
			var $element = $(that.target);
			$element.resize(function(){
				if(that.player){
					that.player.width($element.width());
					that.player.height($element.height());
				}
			})
		},
		_createVideo: function(){
			var that = this;
			var options = that.options;
			var $element = $(that.target);
			//append video;
			var video_id = $element.attr("id") + '_video';
			var $video = $('<video id="'+video_id+'" class="video-js vjs-default-skin" controls></video>');
			$video.attr("data-setup",'{ "html5" : { "nativeTextTracks" : false } }');
			$element.append($video);
			
			var sourceSrc = contextPath + that.options.videoSrc;
			var posterSrc = contextPath + that.options.preImgSrc;

			if(options.mode && options.mode == 'SOA'){
				sourceSrc = options.soa + that.options.videoSrc;
				posterSrc = options.soa + that.options.preImgSrc;
			}

			that.prevSourceSrc = sourceSrc;
			videojs(video_id,{
				// src:sourceSrc,
				poster: posterSrc,
				sourceOrder: true,
				techOrder: [ 'flash','html5'],
				width:$element.width(),
				height:$element.height(),
				
			},function(){
				that.player = this;
				that.player.src(that.prevSourceSrc);
				that.player.load();
			});
			$element.find(".vjs-fullscreen-control").before('<button class="vjs-control rotate" id="danmu_send_opt" ><i class="fa fa-undo" aria-hidden="true"></i></button>');
			// posterSrc = contextPath + that.options.preImgSrc;
			// sourceSrc = contextPath + that.options.videoSrc;
			// player.poster(posterSrc);
			// sourceObj = {
			// 		src: '',
			// 		type: '',
			// }
			$element.find(".rotate").on('click',function(e){
		    var video = $element.find("video");
		    var transform = video[0].style.transform;
			if(transform != undefined && transform != ''){
				
				var deg = transform.substring(transform.indexOf("rotate(") + 'rotate('.length,transform.indexOf("deg"));
				video[0].style.transform = 'rotate('+( 90 + parseInt(deg))+'deg)';
			}
			else{
				video[0].style.transform = 'rotate(90deg)'
			}
			});
		},
		//设置播放源
		setVideoSource: function(sourceObj){
			var that = this;
			var options = that.options;
			if(sourceObj){
				var sourceObjSrc = "";
				if(typeof sourceObj == 'object'){
					sourceObjSrc = sourceObj.src;
				}else{
					sourceObjSrc = sourceObj;
				}

				if(options.mode && options.mode == 'SOA'){
					value = options.soa;
				}else{
					value = contextPath;
				}

				if(sourceObjSrc.indexOf("/") != 0){
					value += "/";
				}
				value += sourceObjSrc;
				
				// sourceObj.src = value;
				if(!that.player){
					that.prevSourceSrc = value;
				}else{
					that.player.src(value);
					that.player.load();	
				}
			}
		},
		//设置预览图片
		setVideoPreImg: function(posterObj){
			var that = this;
			var options = that.options;
			if(posterObj){
				var value = "";
				if(options.mode && options.mode == 'SOA'){
					value = options.soa;
				}else{
					value = contextPath;
				}

				if(posterObj.src.indexOf("/") != 0){
					value += "/";
				}
				value += posterObj.src;

				posterObj.src = value;
				that.player.poster(posterObj);
				that.player.load();
			}
		},
		getOject: function() {
			return $(this.target).find("video");
		},
	}
	
	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});

}(jQuery);