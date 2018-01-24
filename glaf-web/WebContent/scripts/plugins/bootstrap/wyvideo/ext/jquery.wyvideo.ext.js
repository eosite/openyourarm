 /**
  * 网易直播客户端
  *  	$("id").wyvideo(options); // 初始化
  *  	$("id").wyvideo('play',url); //播放
  */
 (function($) {
 	var pluginName = "wyvideo";


 	$.fn[pluginName] = function(command, options) {
 		if (!this.length) {
 			return this;
 		}
 		if (typeof command === "object") {
 			options = command;
 			var opts = $.extend({}, $.fn[pluginName].defaults, options);
 			this.each(function() {
 				var $this = $(this);
 				if (!$this.data(pluginName)) {
 					$this.data(pluginName, opts);
 					methods.__init__.call($this, opts);
 				}
 			});
 		} else if (typeof command === "string" && methods[command]) {
 			this.each(function() {
 				var $this = $(this);
 				methods[command].call($this, options);
 			});
 		} else {
 			$.error('Method ' + command + ' does not exist on jQuery.' + pluginName + "!");
 		}

 		return this;
 	};

 	function playNow(myPlayer,url) {
        var lowUrl = url.toLowerCase(),
            urlType = lowUrl.substring(0, 4),
            type;
        if (url === '') {
            alert('播放地址不能为空');
            return;
        }
        switch (urlType) {
            case 'http':
                if (lowUrl.indexOf('mp4') !== -1) {
                    type = 'video/mp4';
                } else if (lowUrl.indexOf('flv') !== -1) {
                    type = 'video/x-flv';
                } else if (lowUrl.indexOf('m3u8') !== -1) {
                    type = 'application/x-mpegURL';
                }
                break;
            case 'rtmp':
                type = 'rtmp/flv';
                break;
            default:
            	alert('播放地址不正确');
            	return;
        }
        myPlayer.setDataSource({ type: type, src: url });
        myPlayer.play();
    }

 	var methods = $.fn[pluginName].methods = {
 		play:function(params){
			var $this = $(this),
 				opts = $this.data(pluginName),
 				myPlayer = $this.data("neplayer");
 			playNow(myPlayer,params);
 		},
 		__init__: function(opts) {
 			var $this = this,
 				id = $this.attr("id");
 			var myPlayer = neplayer(id, $.extend({},opts), function() {
            	console.log('播放器初始化完成');
        	});
        	$("#"+id).data("neplayer",myPlayer);
 		}
 	};

 	// default options
 	var defaults = $.fn[pluginName].defaults = {
 	};

 })(jQuery);