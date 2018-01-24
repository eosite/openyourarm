(function($, window, document, undefined) {
	var plugin = "ratylimaster", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.el = $(o);
		this.w = $(document);
		this.option = this.el.data(optionKey).options;
		this._init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("ratylimaster", new Plugin(this, params));
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
			    full = '★',
			    empty = '☆',
			    list = that.el;
			if(that.option.state == 'agree'){
				full = "<i class='fa fa-thumbs-up'></i>";
				empty = "<i class='fa fa-thumbs-o-up'></i>";
			}
			else if(that.option.state == 'radius'){
				full = "X";
				empty = "O";
			}
			else if(that.option.state == 'people'){
				full = "<i class='fa fa-male' aria-hidden='true'></i>";
				empty = "<i class='fa fa-male' aria-hidden='true'></i>";
			}
			var rule = list.find(".ratymode").attr("rule");
			if(rule != undefined){
				rule = eval("(" + rule + ")");
				if(rule.icon != undefined){
					full = "<i class='"+ rule.icon+"'></i>";
					empty = "<i class='"+ rule.icon+"'></i>";
				} 
				if(rule.iconsize != undefined){
					list.find(".ratymode").css("font-size",rule.iconsize);
				}
			}
			list.find(".ratymode").ratyli({
				rate : 0,
				ratemax : that.option.scount,
				full:full,
				empty:empty
			});
			var emptyColor = $("#"+list.attr("id")+" .ratymode").attr("empty-color");
			var fullColor = $("#"+list.attr("id")+" .ratymode").attr("full-color");
			$("#"+list.attr("id")+" .ratymode.rated .rate-full").css("color","#fffff");
			emptyColor != undefined ? ($("#"+list.attr("id")+" span.rate-empty").css("color",emptyColor)) : undefined;
			fullColor != undefined ? ($("#"+list.attr("id")+" span.rate-full").css("color",fullColor)) : undefined;
			
		},
		
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);




















