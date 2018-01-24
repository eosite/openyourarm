/**
 * Bootstrap Messenger扩展
 */
(function($){
	var plugin = 'btmessenger';
	var msg = null;

	_init = function(target){
		var opts = getOptions(target);
		var $target = $(target);
		$target.hide();
		var x = $target.attr('x-position');
		var y = $target.attr('y-position');
		var theme = $target.attr('data-theme');
		
		opts._options = {
			extraClasses: 'messenger-fixed messenger-on-'+y+' messenger-on-'+x,
			theme: theme
	    }
	    Messenger.options = opts._options;
	    msg = Messenger().post();
	    msg.hide();
	}

	function getOptions(target){
		return $(target).data(plugin).options;
	}

	function close(target){
		msg.hide();
	}

	function open(target, content){
		content = $(target).attr('data-msg') || content;
		msg.update(content);
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
		open: function(jq, content){
			open(jq[0], content);
		}
	};
	
	$.fn[plugin].defaults = {
		width: 'auto',
		height: 'auto',
		_options: null,
		msger: null
	};
})(jQuery);