(function($, window, document, undefined) {
	var plugin = "modelbim", optionKey = plugin + ".options",
	
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
				this.data("modelbim", new Plugin(this, params));
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
		event : {
			BimClick : function(){},
		}	
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    $tar = that.target,
			    $opt = that.option;
			$tar.empty();
			$tar.css("width",$opt.width);
			$tar.css("height",$opt.height);
		    that.initCarsViewModel(that);
	       
		},
		initCarsViewModel : function($that){
			var $tar = $that.target,
			    $opt = $that.option,
			    token;
			window.option = $that.option;
			 window.onload = function () {
		            var pro=new CarsView($tar.attr("id"),$opt.renderType,$opt.model,$that._callback);
		            pro.then(function(value) {
		                tk=value;
		                tk.setCeshiIds([12400
		                    ,6930                    
		                ]);

		            }, function(value) {
		                // failure
		            });

		     }
		},
		
		_thereRunBims : function(){
            //tk.isolateAndCenterWithGuid(['789af0bc-52fb-4fb3-9f06-65f70125128b']);
        },
        _callback : function(arr){
        	if(arr.length != 0){
        		this.option.event.BimClick(arr);
        	}
        	
        }
		
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

