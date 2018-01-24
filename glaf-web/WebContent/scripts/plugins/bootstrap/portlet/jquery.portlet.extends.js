/**
 
 * Metronic portlet扩展
 
 */

(function($) {

	var plugin = 'portlet';



	function _init(target) {

		var $target = $(target);

		var id = $target.attr('id');

		var opts = getOptions($target);

		var $pbody = $target.find('.portlet-body');

		var targetHeight = $target.height();
		var titleHeight = $target.find('.portlet-title:first').height();

		$target.find('.portlet-title .tools .reload').attr('data-url', opts.url);

		// $('.portlet-title .tools .reload').click();

		var $slimScrollDiv = $pbody.children('.slimScrollDiv');
		if ($slimScrollDiv[0]) {
			var $scroller = $slimScrollDiv.children('.scroller');

			//destroy
			var height = $scroller.height();
			App.destroySlimScroll($scroller[0]);

			//restore height
			$scroller.height( /*height*/ targetHeight - titleHeight -30);
			App.initSlimScroll($scroller[0]);
		}

	}



	function _initEvent(target) {



	}



	function getOptions($target) {

		return $target.data(plugin).options;

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

				$.extend(true, state.options, options);

			} else {

				$.data(o, plugin, {

					options: $.extend(true, {}, $.fn[plugin].defaults, options),

					target: o

				});

			}

			_init(o);

			_initEvent(o);

		});

	};



	//外部调用方法

	$.fn[plugin].methods = {

		destroy: function(jq) {



		}

	};



	$.fn[plugin].defaults = {

		url: ''

	};

})(jQuery);