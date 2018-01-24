// JavaScript Document
(function($){
	var plugin = "Pagefullpage", 
	clear,
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
			navigation: false,
			navigationPosition: '',
			slidesNavigation: true,
			slidesNavPosition: '',
			verticalCentered:false,
			slidesColor:'#323423',
			controlArrowColor:'#adf',
			scrollingSpeed: 1000,
			loopHorizontal: false,
			continuousVertical: true,
			continuousHorizontal: true,
			controlArrows: true,
			verticalCentered: false,
			keyboardScrolling:true,
			//sectionsColor:['#f2f2f2', '#4BBFC3', '#7BAABE', 'whitesmoke', '#000'],
            scrollOverflow:true,
			afl: function(){
				
			},
			asl: function(){
				
			}
	};
	
	$.fn[plugin].methods = {
		//启动垂直动画
	    vticstart : function(){
	    	    that=this;
	    		clear=setTimeout(function(){
	    			$.fn.fullpage.moveSectionDown();
	    			that.vticstart();
	         	}, that.options.Vspeed);
	    },
	    //停止垂直动画
	    vticstop  : function(){
	    	 clearTimeout(clear);
	    },
	    horcstart : function(){
    	    that=this;
    		clear=setTimeout(function(){
    			$.fn.fullpage.moveSlideRight();
    			that.horcstart();
         	}, that.options.Hspeed);
        },
	    //停止垂直动画
        horcstop  : function(){
	    	 clearTimeout(clear);
	    },
		//初始化方法的时候要调用值	
		_init : function(){
			//初始化的时候给html和body增加class
			$("html").addClass("fullpage");
			$("body").addClass("fullpage");
			//初始化的时候改变样式
			 var that=this;
			 var id=this.$target.attr('id');
			 var realid='#'+id;
			 $(realid).fullpage({
					//Navigation
					navigation: this.options.navigation,
					navigationPosition: this.options.navigationPosition,
					slidesNavigation: this.options.slidesNavigation,
					slidesNavPosition: this.options.slidesNavPosition,
					verticalCentered:this.options.verticalCentered,
					slidesColor:this.options.slidesColor,
					controlArrowColor:this.options.controlArrowColor,
					scrollingSpeed:this.options.scrollingSpeed,
					loopHorizontal:this.options.loopHorizontal,
					continuousVertical:this.options.continuousVertical,
					continuousHorizontal:this.options.continuousHorizontal,
					controlArrows: this.options.controlArrows,
					verticalCentered: this.options.verticalCentered,
					keyboardScrolling:this.options.keyboardScrolling,
					autoScrolling:this.options.autoScrolling,
					sectionsColor:this.options.sectionsColor,
					afterLoad: function(anchorLink, index){
						var loadedSection = $(this);
						if(index == 3){
							that.options.afl();
						}
					},
					afterSlideLoad: function(anchorLink, index, slideAnchor, slideIndex){
						var loadedSlide = $(this);
						if(index == 3 && slideIndex == 2){
							that.options.asl();
						}
					}
			 });
			 //设置竖直方向的动画
			 if(this.options.continuousVertical==true){
				 if(this.options.Vstart==true){
					 this.vticstart(); 
				 }
			 }
			 if(this.options.continuousVertical==false){
				 if(this.options.loopHorizontal==true){
					 if(this.options.Hstart==true){
						 this.horcstart(); 
					 }
				 }
			 }
	    }
	};
	
	$.extend(true,Build.prototype, 
			{contructor : Build}, 
			$.fn[plugin].methods
	);
})(jQuery);