/**
 * bim 三维图 $("id").bim(options); // 初始化 $("id").bim('setValue',options); //赋值等
 */

function BimOnload(o) {
	var $o = $(o);
	var $div = $o.closest("div[data-role=bim]");
	var opts = $div.data("bim");
	
	var _documentId = "_documentId";
	if (opts && $div.data(_documentId)) {
		opts.documentId = opts.urn + $div.data(_documentId);
	}
	if (opts/* && opts.documentId*/) {
		o.contentWindow.$(".mt-bim").bim(opts);
	}
	$div.data("loaded", true);
}

(function($) {
	var pluginName = "bim", _documentId = "_documentId";

	$.fn[pluginName] = function(command, options) {
		var self = this;
		if (!self.length) {
			return self;
		}
		if (typeof command === "object") {
			options = command;
			var opts = $.extend(true, {}, $.fn[pluginName].defaults, options);
			return self.each(function() {
				methods.__init__.call(this, opts);
			}, opts);
		} else if (typeof command === "string" && methods[command]) {
			return methods[command].apply(self, Array.prototype.slice.call(
					arguments, 1));
		} else {
			$.error('Method ' + command + ' does not exist on jQuery.bim');
		}
		return self;
	};

	
	var GetBimTarget = function(jq){
		var $iframe = jq.find("iframe");
		if($iframe.get(0) && $iframe.get(0).contentWindow){
			return $iframe.get(0).contentWindow.$(".mt-bim");
		}
		return null;
	};
	
	
	var methods = $.fn[pluginName].methods = {
		setValue : function(value) {
			var $this = $(this), opts = $this.data(pluginName);
			if (opts) {
				opts.documentId = opts.urn + value;
				opts[_documentId] =  value;
			}
			$this.data(_documentId, value);
			if($this.data("loaded")){
				var $bim = GetBimTarget(this);
	 			return $bim.bim("setValue", value) ;
			}
		},
		__init__ : function(opts) {
			var $this = $(this);
			$this.data(pluginName, opts);
			if ($this.data(_documentId)) {
				opts.documentId = opts.urn + $this.data(_documentId);
			}
			ResizeFrame($this.find("iframe").get(0));
		},
 		select2View : function(id){
 			var $bim = GetBimTarget(this);
 			if(id && typeof id == "string"){
 				id = parseInt(id);
 			}
 			$(this).data("client_method", true);
 			return $bim.bim("select2View", id) ;
 		},
 		getObjectTree : function(callback){
 			var $bim = GetBimTarget(this);
 			return $bim.bim("getObjectTree", callback);
 		},
 		GetNodeById:function(id){
 			var $bim = GetBimTarget(this);
 			if(id && typeof id == "string"){
 				id = parseInt(id);
 			}
 			return $bim.bim("GetNodeById", null, id);
 		},
 		GetSelection : function(){
 			var $bim = GetBimTarget(this);
 			return $bim.bim("GetSelection");
 		}
	};

	// default options
	var defaults = $.fn[pluginName].defaults = {
		urn : 'urn:',
		env : 'AutodeskProduction',
		tokenUrl : null,
		accessToken : '',
		documentId : '',
		docOptions : {
			'type' : 'geometry'
		},
		onDocumentLoadSuccessNoViewables : function() {

		},
		onDocumentLoadFailure : function(viewerErrorCode) {

		},
		onLoadModelSuccess : function(model) {

		},
		onLoadModelError : function(viewerErrorCode) {

		},
		/**
		 * 客户端调用点击事件
		 */
		onSelect : function(e) {
			console.log(e);
		}
	};
	
	function ResizeFrame(frame){
		var $this = $(frame), h = $this.attr("height");
		if($this.data("resize_") || !h || (h == "auto") || (h == "100%")){
			var $container = $this.closest("div.autoHt");
			if(!$container.get(0)){
				$container = $this.closest("div.row");
			}
			if($container.get(0)){
				$this.attr({
					height : $container.height() - 10
				});
				$this.data("resize_", true);
			}
		}
	}
	
	var Resize = (function(){
		var resizes = {
			resizeFrame : function(){
				window.setTimeout(function(){
					$("div[data-role=bim] iframe")//
					.each(function(){
						ResizeFrame(this);
					});
				}, 10);
			}
		};
		return function(){
			$.each(resizes, function(i, fn){
				fn && fn();
			});
		};
	})();
	
	$(window).resize(Resize);

})(jQuery);