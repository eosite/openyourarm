(function($, window, document, undefined) {
	var plugin = "searchbar", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this._init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("searchbar", new Plugin(this, params));
            } 
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};

	$.fn[plugin].defaults = {
			
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    $opt = that.option,
			    $target = that.target;
			
//			document.head.innerHTML = document.head.innerHTML + "<script type='text/javascript' src='" + contextPath +"/scripts/jquery-weui-master/src/lib/jquery-weui.js'></script>";
		},
		setValue : function(val){
			this.target.find(".weui-search-bar__input").val(val);
			this.target.find(".weui-search-bar__label").css("display","none");
		},
		appendSetValue : function(val){
			this.target.find(".weui-search-bar__input").append(val);
			this.target.find(".weui-search-bar__label").css("display","none");
		},
		tdisabled : function(){
			this.target.find(".weui-search-bar__input").attr("readonly","readonly");
		},
		tenabled : function(){
			this.target.find(".weui-search-bar__input")[0].removeAttribute("readonly");
		},
		thidden : function(){
			this.target.css("display","none");
		},
		tshow : function(){
			this.target.css("display","display")
		}
		
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

