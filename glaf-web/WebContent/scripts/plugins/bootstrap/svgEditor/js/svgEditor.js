(function($) {
	var plugin = 'svgEditor';

	var methods = {
		init: function($target, read,opts) {
			var $iframe = $('<iframe>');
			var frameId = methods.getFrameId($target);
			var params = {};
			if (read && read.data) {
				params = JSON.parse(read.data);
			}
			$iframe.attr({
				'id': frameId,
				'name': frameId,
				'width': '100%',
				'height': '100%',
				'frameborder': 'no',
				'scrolling': 'no',
				'src': contextPath + "/mx/form/svgEditor/view?frameId=" + frameId + "&rid=" + (params.rid || '') + "&dataRole=" + $target.attr('data-role') +"&showSysMenu=" + (opts?(opts.showSysMenu||"false"):"false")
			});
			$target.append($iframe);
		},
		getFrameId: function($target) {
			return $target.attr('id') + '_iframe';
		},
		_init : function(target, params) {
			clear(target);
			var opts = getOptions(target);
			opts.frameId = $(target).attr('id') + '_iframe'
			if (opts.asDemo) {
				$(target).css("padding","5px");
			}else{
				$(target).css("padding","0px");	
			}
			methods.init($(target));
		}
	}

	function getOptions(target) {
		return $(target).data(plugin) ? $(target).data(plugin).options : {};
	}

	function clear(target) {
		$(target).empty();
	}

	//初始化
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, plugin);
			if (state) {
				state.tabs = [];
				$.extend(true, state.options, options);
			} else {
				$.data(o, plugin, {
					options: $.extend(true, {}, $.fn[plugin].defaults, options),
					target: o,
					columns: [],
					datas: param,
				});
			}
			methods._init(o);

		});
	};

	//外部调用方法
	$.fn[plugin].methods = {
		getIframe:function($target){
			return $target.find("iframe");
		},
		getIframeWindow : function($target){
			var $iframe = this.getIframe($target);
			return $iframe[0] && $iframe[0].contentWindow || null;
		},
		setValue: function(jq,param) {
			var iframeWindow = this.getIframeWindow(jq);
			if(iframeWindow.svgCanvas){
				iframeWindow.svgCanvas.setSvgString(param);
			}else{
				var options = getOptions(jq);
				options.defaultValue = param ;
			}
			return jq ;
		},
		getValue: function(jq){
			var iframeWindow = this.getIframeWindow(jq);
			return iframeWindow.svgCanvas.getSvgString();
		}
	};

	$.fn[plugin].defaults = {
		asDemo: false
	};
})(jQuery);
