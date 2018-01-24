(function($) {
	//异步获取数据
	function ajaxRequest(opts, $this) {
		$.ajax({
			url: opts.url,
			type: 'post',
			async: true,
			data: JSON.stringify(opts.params),
			contentType: "application/json",
			success: function(ret) {
				if (ret == $this.data("config") && $this.data("isPlay")) {
					return;
				}
				$this.data("config", ret);
				if (opts.autoPlay) {
					playVideo($this, $('#' + $this.attr("id") + '_object'));
					window.onunload = function(){
						stopVideo($this, $('#' + $this.attr("id") + '_object'));
					};
					window.onbeforeunload = function(){
						stopVideo($this, $('#' + $this.attr("id") + '_object'));
					};
				}
			}
		});
	};
	//初始化视频布局
	function initLayout(id) {
		var $this = $("#" + id),
			thisWidth = $this.width(),
			thisHeight = $this.height();
		thisHeight == 0 && $this.css({
			height: 400
		});
		thisHeight = thisHeight == 0 ? 400 : thisHeight;
		$this.find('.mt-separate').css({
			left: thisWidth - 230,
			height: thisHeight - 6
		}).end().find(".mt-left").css({
			width: thisWidth - 233,
			height: thisHeight - 3
		}).end().find(".mt-right").css({
			height: thisHeight - 3,
			left: thisWidth - 212,
		});
		/*$("#" + id + "_vertical").css({
			//height : $(window).height() - 3
		});.kendoSplitter({
			orientation: "horizontal",
			panes: [{
				collapsible: false,
				resizable: false,
				scrollable: false
			}, {
				collapsible: true,
				resizable: false,
				scrollable: false,
				size: '198px'
			}],
			layoutChange: function(e) {
				$(this.wrapper).css({
					//height : $(window).height() - 3
				})
			}
		})*/
	};
	//注册其他按钮事件
	function regButtonEvent(id) {
		var video = $('#' + id),
			videoObject = $('#' + id + '_object');
		$('#' + id + ' .k-start').on('click', function() {
			playVideo(video, videoObject);
		});
		$('#' + id + ' .k-stop').on('click', function() {
			stopVideo(video, videoObject);
		});
		$('#' + id + ' .k-btn').on('mousedown', function() {
			var $this = $(this);
			fnStart(videoObject, $this.attr('t'));
		}).on('mouseup', function() {
			var $this = $(this);
			fnStop(videoObject, $this.attr('t'));
		});
	};
	var cmdFn = function(videoObject, orientation, action) {
		var cmd = "{ \"enCommand\": \"" + orientation + "\", \"enAction\": \"" + action + "\"}";
		videoObject[0].Yun(cmd);
	};
	var fnStart = function(videoObject, orientation) {
		cmdFn(videoObject, orientation, "START");
	};

	var fnStop = function(videoObject, orientation) {
		cmdFn(videoObject, orientation, "STOP");
	};
	//播放视频方法
	function playVideo(video, videoObject) {
		var config = video.data('config');
		if (config == "") {
			if (video.data("count") > 1)
				alert('当前视频无法播放');
			return;
		}
		try{
			stopVideo(video, videoObject);
		} catch (e) {

		}
		var bol = videoObject[0].StartNew(window, config, "CallBackFun");
		//var bol = videoObject[0].Start(config);
		video.data("isPlay", bol);
	};
	//停止正在播放的视频
	function stopVideo(video, videoObject) {
		if (video.data("isPlay")) {
			videoObject[0].Stop();
		}
	};

	//方法集合
	var methods = {
		init: function(options) {
			var opts = $.extend(true, {}, $.fn[plugin].defaults, options);
			return this.each(function(i, o) {
				var $this = $(this),
					id = $this.attr('id');
				opts.params.rid = $this.attr('rid');
				regButtonEvent(id);
				initLayout(id);
				$this.data("count", 1);
				$this.data("opts", opts);
				ajaxRequest(opts, $this);
			});
		},
		start: function(options) {
			return this.each(function(i, o) {
				var $this = $(this),
					id = $this.attr('id');
				$this.data("count", $this.data("count") + 1);
				playVideo($this, $('#' + id + '_object'));
			});
		},
		stop: function(options) {
			return this.each(function(i, o) {
				var $this = $(this),
					id = $this.attr('id');
				stopVideo($this, $('#' + id + '_object'));
			});
		},
		link: function(options) {
			//opts.params.params  联动需要配置的参数
			return this.each(function(i, o) {
				var $this = $(this),
					id = $this.attr('id'),
					opts = $.extend(true, {}, $this.data("opts"), {});
				opts.params.params = JSON.stringify(options.params);
				ajaxRequest(opts, $this);
				$this.data("count", $this.data("count") + 1);
				$this.data("opts", opts);
				stopVideo($this, $('#' + id + '_object'));
			});
		}
	};
	var plugin = "ysVideo";
	$.fn[plugin] = function(opts) {
			if (methods[opts]) {
				return methods[opts].apply(this, Array.prototype.slice.call(arguments, 1));
			} else if (typeof opts === 'object' || !opts) {
				return methods.init.apply(this, arguments);
			} else {
				$.error('Method ' + opts + ' does not exist on jQuery.ysVideo');
			}
		}
		//默认参数
	$.fn[plugin].defaults = {
		url: null, //远程获取数据地址
		autoPlay: false,
		params: {} //参数
	};

})(jQuery);