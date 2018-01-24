(function($) {
	var plugin = "rangeSlider", 
		optionKey = plugin + ".options",
		
	Build = function(o) {
		this.$target = $(o);
		this.options = this.$target.data(optionKey).options;
		this._init();
	};

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
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
					options : $.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};
	
	//默认的拖动进度条配置
	$.fn[plugin].defaults = {
			  hide_min_max: true,
	          keyboard: true,
	          min: 0,
	          max: 5000,
	          from: 1000,
	          to: 4000,
	          type: 'single',
	          step: 100,
	          prefix: "$",
	          postfix:"%",
	          grid: false,
	          onChan:function(){}
	};
	
	$.fn[plugin].methods = {
		//初始化方法的时候要调用值	
		_init : function(){
			//初始化的时候改变样式
			 //$("#range").css('display','inline');
			 var that=this;
			 $('#process').hide();
			 $("#range").ionRangeSlider({
		            hide_min_max: this.options.hide_min_max,
		            keyboard:this.options.keyboard,
		            min: this.options.min,
		            max: this.options.max,
		            from: this.options.from,
		            to: this.options.to,
		            type: this.options.type,
		            step: this.options.step,
		            prefix: this.options.prefix,
					postfix:this.options.postfix,
		            grid: this.options.grid,
		            onChange: function (data) {
		            	that.options.onChan();
		            }
		     });
			 if(this.options.skin == "red"){
				 $("#styleSrc").attr("href",contextPath + "/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css");
			 } else if(this.options.skin == "green"){
				 $("#styleSrc").attr("href",contextPath + "/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinModern.css");
			 } else if(this.options.skin == "blue"){
				 $("#styleSrc").attr("href",contextPath + "/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinHTML5.css");
			 } else if(this.options.skin == "grey"){
				 $("#styleSrc").attr("href",contextPath + "/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinNice.css");			 
			 } else if(this.options.skin == "brown"){
				 $("#styleSrc").attr("href",contextPath + "/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinSimple.css");
			 }
			 
			 
	    }
	};
	
	$.extend(true,Build.prototype, 
			{contructor : Build}, 
			$.fn[plugin].methods
	);
})(jQuery);
