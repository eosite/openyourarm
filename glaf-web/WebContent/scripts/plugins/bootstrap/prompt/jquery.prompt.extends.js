! function($) {
	var plugin = 'promptExt';


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
		if(columns&&columns.length>0){
			state.options.columns = eval('('+columns+')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
			promptmessage:'right',
			titile:'',
		
		
	};
	$.fn[plugin].methods = {
		_init: function() {
			var that = this;
			var options = that.options;
			var place =options.promptmessage;
			var $this = that.getOject.call(that);
			$(this.target).css("left","-1000px");
			$(this.target).find(".containerComp").css("minHeight","0px");
		},
	
	
		getOject: function() {
			return $(this.target).find(".containerComp");
		},
	}
	
	
	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});
	

}(jQuery);