! function($) {
	var plugin = 'aspectlayout';

	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options');
		var columns = $(target).data("columns");
		if (columns && columns.length > 0) {
			state.options.columns = eval('(' + columns + ')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		datas : null,
		url:"",
		visible:true,	//显示
		events: {
		}
	};

	$.fn[plugin].methods = {
		_init : function(){
			var that = this;
			var options = that.options;
			var $target = $(that.target);
			that.$target = $target;
			if(!$(".designer")[0]){
				$(".aspectlayout-designer").removeClass("aspectlayout-designer");
				// that.$target.removeClass("aspectlayout-designer");
			}

			if(that.$target.attr("realWidth")){
				that.$target.css("width",that.$target.attr("realWidth"));
			}
			if(that.$target.attr("realHeight")){
				that.$target.css("height",that.$target.attr("realHeight"));
			}

			that._buildEvent();
			that.resize();
		},
		//计算左中右，中间的宽度
		resizeCenter : function($component){
			var that = this;
			if(!$component){
				$component = that.$target;
			}
			var $middle = $component.find(">.aspectlayout-middle");
			if(!$middle[0]){
				return;
			}
			//计算中间的宽度
			var $west = $middle.find(">.aspectlayout-west");
			var $east = $middle.find(">.aspectlayout-east");
			var $center = $middle.find(">.aspectlayout-center");
			var centerWidth = $middle.innerWidth();
			if($west[0]){
				centerWidth -= $west.outerWidth(true);
			}
			if($east[0]){
				centerWidth -= $east.outerWidth(true);
			}
			$center.outerWidth(centerWidth-1);
		},
		resizeHeight : function($component){
			var that = this;
			if(!$component){
				$component = that.$target;
			}
			if(!$component.is(":hidden") ){
				if($component.hasClass("aspectlayout-designer")){
					var $middle = $component.find(">.aspectlayout-middle");
					if($middle[0]){
						that.resizeCenter($component);
					}
					return;
				}
				var $north = $component.find(">.aspectlayout-north");
				var $south = $component.find(">.aspectlayout-south");
				var $middle = $component.find(">.aspectlayout-middle");
				//计算中间的高度
				var middleHeight = $component.innerHeight();
				if($north[0]){
					middleHeight -= $north.outerHeight(true);
				}
				if($south[0]){
					middleHeight -= $south.outerHeight(true);
				}
				if($middle[0]){
					$middle.outerHeight(middleHeight);
					that.resizeCenter($component);
				}
			}
		},
		resize : function(){
			var that = this;
			if(!that.$target.is(":hidden") ){
				if(that.$target.hasClass("aspectlayout-designer")){
					var $middle = that.$target.find(">.aspectlayout-middle");
					if($middle[0]){
						that.resizeCenter();
					}
					return;
				}
				var $north = that.$target.find(">.aspectlayout-north");
				var $south = that.$target.find(">.aspectlayout-south");
				var $middle = that.$target.find(">.aspectlayout-middle");
				//计算中间的高度
				var middleHeight = that.$target.innerHeight();
				if($north[0]){
					middleHeight -= $north.outerHeight(true);
				}
				if($south[0]){
					middleHeight -= $south.outerHeight(true);
				}
				if($middle[0]){
					$middle.outerHeight(middleHeight);
					that.resizeCenter();
				}
			}
		},
		_buildEvent : function(){
			var that = this;
			that.count = that.count || 0;

			if(that.$target.hasClass("aspectlayout-designer")){
				$('.designer').bind("designerWidthChange",function(){
					if(!that.count){
						that.count++;
						setTimeout(function(){
							that.resize.call(that);
							that.count = 0;
						},100)
					}
				})
				$(that.target).hover(function(){
					if(!that.count){
						that.count++;
						setTimeout(function(){
							that.resize.call(that);
							that.count = 0;
						},100)
					}
				})
			}else{
				if(!$("body").attr("aspectlayoutResize")){
					$("body").attr("aspectlayoutResize",true);
					$("body").resize(function(){
						if(!that.count){
							that.count++;
							setTimeout(function(){
								$.each($(".aspectlayout"),function(i,item){
									var $item = $(item);
									that.resizeHeight($item);
								});
								that.count = 0;
							},100)
						}	
					})
				}
				// $(that.target).resize(function() {
				// 	if(!that.count){
				// 		that.count++;
				// 		setTimeout(function(){
				// 			that.resize.call(that);
				// 			that.count = 0;
				// 		},100)
				// 	}
				// });
			}

			// $(that.target)[0].addEventListener("resize",function() {
			// 	if(!that.count){
			// 		that.count++;
			// 		setTimeout(function(){
			// 			that.resize.call(that);
			// 			that.count = 0;
			// 		},100)
			// 	}
			// })
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);