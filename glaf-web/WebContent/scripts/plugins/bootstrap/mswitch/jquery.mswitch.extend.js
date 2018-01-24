! function($) {
	var plugin = 'mswitchext';

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
			switchChange: function(index, row) {}
		}
	};

	$.fn[plugin].methods = {
		_init : function(){
			var that = this;
			var options = that.options;
			var $target = $(that.target);
			that.$target = $target;
			$target.find(".mui-switch").remove();

			var $switch = $('<div class="mui-switch"><div class="mui-switch-handle"></div></div>');
			if($target.attr("selected")){
				$switch.addClass('mui-active');
				$target.data("switchValue",that.$target.attr("realValue") || 1);
			}else{
				$target.data("switchValue",that.$target.attr("fakeValue") || 0);
			}
			
			$target.append($switch);
			var $activeSwitch = that.$target.find(".mui-switch.mui-active");
			if($activeSwitch[0]){
				var width = $activeSwitch.width() / 2;
				$activeSwitch.find(".mui-switch-handle").css({
					"-webkit-transform": 'translate('+width + 'px,0)',
					"transform": 'translate('+width+'px,0)'
				})
			}
			
			mui("#"+that.target.id +" .mui-switch")['switch']();
			that.mswitch = mui("#"+that.target.id +" .mui-switch").switch();
			$target.on("toggle",".mui-switch",function(event){
		        if (event.detail.isActive) {
	               that.$target.data("switchValue",that.$target.attr("realValue") || 1);
				} else {
	               that.$target.data("switchValue",that.$target.attr("fakeValue") || 0);
				}
				if($.isFunction(that.options.events.switchChange)){
					that.options.events.switchChange();
				}
				
			});
		},
		setValue : function(val){
			var that = this;
			var value = that.$target.data("switchValue");
			if(value != val){
				that.mswitch.toggle();
			}
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);