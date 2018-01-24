/**
 * Bootstrap Button扩展
 */
(function($){
	var plugin = 'mtbutton';

	var _init = function(target){
		
	}

	function getOptions(target){
		return $(target).data(plugin).options;
	}

	function close(target){
		$(target).alert('close');
	}

	function open(target){
		$(target).alert();
	}

	//初始化
	$.fn[plugin] = function(options, param){
	    if (typeof options == 'string'){
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i,o){
			var state = $.data(o, plugin);
			if (state) {
				state.tabs = [];
				$.extend(true,state.options,options);
			} else {
				$.data(o, plugin, {
					options : $.extend(true,{},$.fn[plugin].defaults, options),
					target : o,
					columns : [],
					datas : param,
				});
			}
			_init(o);
		});
	};
	
	//外部调用方法
	$.fn[plugin].methods = {
		close: function(jq){
			close(jq[0]);
		},
		open: function(jq){
			open(jq[0]);
		}
	};
	
	$.fn[plugin].defaults = {
		width: 'auto',
		height: 'auto'
	};
})(jQuery);