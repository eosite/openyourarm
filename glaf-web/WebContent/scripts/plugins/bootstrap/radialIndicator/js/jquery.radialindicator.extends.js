(function($, window, document, undefined) {
	var plugin = "radialindicator", optionKey = plugin + ".options",
	
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
				this.data("radialindicator", new Plugin(this, params));
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
			    opts = that.option,
			    list = that.target;
			list.find('#indicatorContainer').empty();
			$.ajax(opts.read).done(function(ret){
				var dataSource = opts.dataSource != undefined && opts.dataSource != null ? eval("("+opts.dataSource+")") : undefined;
				    xAxisName = dataSource != undefined ? dataSource[0].xAxisName : null,
				    yAxisName = dataSource != undefined ? dataSource[0].yAxisName : null;	
				$.each(ret[0].data,function(i,data){
					list.find('#indicatorContainer').radialIndicator({
				        barColor: '#87CEEB',
				        barWidth: 10,
				        initValue: parseInt(data[xAxisName.en]) / parseInt(data[yAxisName[0].en]) * 100,
				        radius :opts.width,
				        roundCorner : true,
				        percentage: true
				    });
				});
				
			});
			
		},
		
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);




















