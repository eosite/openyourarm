 /**
  * 阿里云直播客户端
  *  	$("id").alivideo(options); // 初始化
  *  	$("id").alivideo('play',url); //播放
  * view https://player.alicdn.com/prismplayer/docs/properties.html
  */
 (function($) {
     var pluginName = "alivideo",
         pluginObjName = "aliplayer";


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

     var methods = $.fn[pluginName].methods = {
        playByRoomId:function(params){
            var $this = $(this),
                 opts = $this.data(pluginName),
                 source = opts.domainName+"/"+opts.appName+"/"+params+".flv";
            methods.play.call($this,source);
        },
        play: function(params) {
             var $this = $(this),
                 opts = $this.data(pluginName);
             if (params) {
                 methods.__init__.call($this, $.extend(opts, {
                     source: params
                 }));
             }
             $this[0].play && $this[0].play();
         },
        __init__: function(opts) {
             var $this = this,
                 id = $this.attr("id");
             //if (opts.source) {
                 var player = new prismplayer($.extend({
                     id: id
                 }, opts));
                 $("#" + id).data(pluginObjName, player);
            //}
         }
     };

     // default options
     var defaults = $.fn[pluginName].defaults = {
         autoplay: true, //自动播放
         source: ""/*"http://pushlive.chinaiss.com/isdp/test.flv"*/, //播放源
         cover: "", //播放器封面地址
         isLive: true, //播放内容是否为直播，直播时会禁止用户拖动进度条
         controlBarVisibility:"always",
         useH5Prism:false,
         useFlashPrism:false,
         playsinline:false,
         domainName:"",
         appName:"",
     };

 })(jQuery);