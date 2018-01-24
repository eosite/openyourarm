/**
 * Accordions（手风琴）扩展
 */
(function($) {
	var plugin = 'accordions';

	/**
	 * 初始化加载手风琴项
	 * @param  {[type]} target accordions对象
	 * @return {[type]}        [description]
	 */
	function initAccordions(target) {
		var opts = $.data(target, plugin).options;
		var accordions_id = target.id || 'accordions_' + ($.fn[plugin].defaults.idSeed++);

		$(target).addClass('accordion panel-group');

		$.each(opts.tabs, function(index, val) {
			if (opts.selected == index) {
				val.selected = true;
			}
			createAccordion(target, val);
		});

	}

	/**
	 * 创建一个项
	 * @param  {[type]} target  accordions对象
	 * @param  {[type]} options 手风琴项的属性
	 * @return {[type]}         [description]
	 */
	function createAccordion(target, options) {
		var opts = $.data(target, plugin).options;

		var accordion_id = options.id || 'accordion_' + ($.fn[plugin].defaults.idSeed++);
		var content_id = 'collapse_content_' + ($.fn[plugin].defaults.idSeed++);

		var $panel_body = $('<div>').addClass('panel-body');
		//高度
		var height = options.height;
		if ($.isNumeric(height)) {
			height = height + 'px';
		}
		$panel_body.height(height);

		//内容
		if (options.content) {
			$panel_body.html(options.content);
		} else if (options.href) {
			$panel_body.append($('<iframe src="' + options.href + '" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" ></iframe>'));
		}

		//内容外部div
		var $panel_content = $('<div>').addClass('panel-collapse').attr('id', content_id).append($panel_body);

		//选中
		if (options.selected) {
			$(target).find('.accordion-toggle').addClass('collapsed');
			$(target).find('.panel-collapse').removeClass('in').addClass('collapse');
			$panel_content.addClass('in');
		} else {
			$panel_content.addClass('collapse');
		}

		//标题
		var $panel_a = $('<a>').addClass('accordion-toggle');
		//右边有图标
		if (opts.icon) {
			$panel_a.addClass('accordion-toggle-styled');
			if (options.selected) {
				$panel_a.removeClass('collapsed');
			} else {
				$panel_a.addClass('collapsed');
			}
		}

		//标题链接
		$panel_a.attr({
			'data-toggle': 'collapse',
			'data-parent': '#' + target.id,
			'href': '#' + content_id
		}).html(options.title);
		var $panel = $('<div>').addClass('panel panel-default').attr('id', accordion_id);
		var $panel_header = $('<div>').addClass('panel-heading').append($('<h4>').addClass('panel-title').append($panel_a));

		$panel.append($panel_header).append($panel_content).appendTo(target);

		$.data(target, plugin).accordions.push(options);
		$.data(target, plugin).jqAccordions.push($panel);
		$.data($panel, plugin, options);

		$.data(target, plugin).options.onAdd.call(target, options.title, getAccordionIndex(target, $panel));
	}

	/**
	 * 添加一个项
	 * @param {[type]} target  accordions对象
	 * @param {[type]} options 属性
	 */
	function addAccordion(target, options) {
		options = $.extend({
			id: null,
			title: '',
			content: '',
			href: '',
			selected: false,
			height: 'auto'
		}, options || {});

		//创建
		createAccordion(target, options);
	}

	/**
	 * 删除一个项
	 * @param  {[type]} target accordions对象
	 * @param  {[type]} which  title或index
	 * @return {[type]}        [description]
	 */
	function removeAccordions(target, which) {

		if (!exists(target, which)) return;

		var jqaccordions = $.data(target, plugin).jqAccordions;
		var currentSelectTitle = $.data(getSelectedAccordion(target), plugin).title;
		var index = getAccordionIndex(target, getAccordion(target, which));
		var $jqAccordion = getAccordion(target, which, true);

		var options = $.data($jqAccordion, plugin);
		var title = options.title;


		$.removeData($jqAccordion);
		$jqAccordion.remove();

		//判断是否关闭的是否为当前选中的项
		//如果是，关闭后选择最后一个
		var isCurrentSelect = false;
		if (currentSelectTitle == title) {
			isCurrentSelect = true;
		}
		if (isCurrentSelect) {
			var $jqAccordion = jqaccordions[jqaccordions.length - 1];
			if ($jqAccordion) {
				selectAccordion(target, $.data($jqAccordion, plugin).title);
			} else {
				selectAccordion(target, 0);
			}
		}

		$.data(target, plugin).options.onRemove.call(target, title, index);
	}

	/**
	 * 更新一个项
	 * @param  {[type]} target  accordions对象
	 * @param  {[type]} params  配置属性:{accordion:...,options:{...}}
	 * @return {[type]}         [description]
	 */
	function updateAccordion(target, params) {
		var $accordion = params.accordion;

		var options = $.extend($.data($accordion, plugin), params.options || {});
		// id: null,
		// 	title: '',
		// 	selected: false,

		var panel_body = $accordion.find('div.panel-body')[0];
		//高度
		var height = options.height;
		if ($.isNumeric(height)) {
			height = height + 'px';
		}
		$(panel_body).height(height);

		//内容
		if (options.content) {
			var $frame = $(panel_body).find('iframe:eq(0)');
			if ($frame && $frame.length > 0) {
				$frame.remove();
			}

			$(panel_body).html(options.content);
		} else if (options.href) {
			$(panel_body).html('');
			$(panel_body).append($('<iframe src="' + options.href + '" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" ></iframe>'));
		}

		var $panel_a = $accordion.find('a.accordion-toggle');
		$panel_a.html(options.title);
	}

	/**
	 * 判断指定项是否存在
	 * @param  {[type]} target accordions对象
	 * @param  {[type]} which  title或索引
	 * @return {[type]}        [description]
	 */
	function exists(target, which) {
		return getAccordion(target, which) != null;
	}

	/**
	 * 获取一个项
	 * @param  {[type]} target accordions对象
	 * @param  {[type]} which  title或index
	 * @param  {[type]} removeit 是否移除
	 * @return {[type]}        [description]
	 */
	function getAccordion(target, which, removeit) {
		var jqAccordions = $.data(target, plugin).jqAccordions;

		var accordions = $.data(target, plugin).accordions;
		if (typeof which == 'number') {
			if (which < 0 || which >= jqAccordions.length) {
				return null;
			} else {
				var jqAccordion = jqAccordions[which];
				if (removeit) {
					jqAccordions.splice(which, 1);
					accordions.splice(which, 1);
				}
				return jqAccordion;
			}
		}

		for (var i = 0; i < accordions.length; i++) {
			var accordion = accordions[i];
			var jqaccordion = jqAccordions[i];
			if (accordion.title == which) {
				if (removeit) {
					accordions.splice(i, 1);
					jqAccordions.splice(i, 1);
				}
				return jqaccordion;
			}
		}
		return null;
	}

	/**
	 * 获取手风琴的索引
	 * @param  {[type]} target    accordions对象
	 * @param  {[type]} accordion [description]
	 * @return {[type]}           [description]
	 */
	function getAccordionIndex(target, accordion) {
		var jqAccordions = $.data(target, plugin).jqAccordions;

		for (var i = 0; i < jqAccordions.length; i++) {
			if (jqAccordions[i][0] == $(accordion)[0]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 获取当前选中的项
	 * @param  {[type]} target accordions对象
	 * @return {[type]}        [description]
	 */
	function getSelectedAccordion(target) {
		var jqAccordions = $.data(target, plugin).jqAccordions;

		var rst;
		for (var i = 0; i < jqAccordions.length; i++) {
			var $accordion = jqAccordions[i];
			$.each($accordion.children(), function(index, val) {
				if ($(val).hasClass('in')) {
					rst = $accordion;
				}
			});
			if (rst)
				return rst;
		}
	}

	/**
	 * 选中指定项
	 * @param  {[type]} target accordions对象
	 * @param  {[type]} which  标题或索引
	 * @return {[type]}        [description]
	 */
	function selectAccordion(target, which) {
		if (which != undefined) {
			var $elem = getAccordion(target, which);
			if ($elem) {
				$elem.find('a.collapsed').trigger('click');
				$.data(target, plugin).options.onSelect.call(target, $.data($elem, plugin).title, getAccordionIndex(target, $elem));
				return $elem;
			}
		}
		return null;
	}

	/**
	 * 设置属性
	 * @param {[type]} target 设置手风琴属性
	 */
	function setProperties(target) {
		var opts = $.data(target, plugin).options;

		if (opts.scrollable) {
			$(target).addClass('scrollable');
		} else {
			$(target).removeClass('scrollable');
		}

		//高度
		var height = opts.height;
		if ($.isNumeric(height)) {
			height = height + 'px';
		}
		$(target).height(height);

		//宽度
		var width = opts.width;
		if ($.isNumeric(width)) {
			width = width + 'px';
		}
		$(target).width(width);

		//右边是否有图标
		var jqAccordions = $.data(target, plugin).jqAccordions;
		$.each(jqAccordions, function(index, accordion) {
			if (opts.icon) {
				accordion.find('a.accordion-toggle').addClass('accordion-toggle-styled');
			} else {
				accordion.find('a.accordion-toggle').removeClass('accordion-toggle-styled');
			}
		});
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
					target: o,
					accordions: [],
					jqAccordions: [],
					datas: param
				});
				initAccordions(o);
			}
			setProperties(this);
		});
	};

	//外部调用方法
	$.fn[plugin].methods = {
		options: function(jq) {
			var cc = jq[0];
			var opts = $.data(cc, plugin).options;
			var s = getSelectedAccordion(cc);
			opts.selected = s ? getAccordionIndex(cc, s) : -1;
			return opts;
		},
		accordions: function(jq) {
			return $.data(jq[0], plugin).accordions;
		},
		add: function(jq, options) {
			return jq.each(function() {
				addAccordion(this, options);
			});
		},
		remove: function(jq, which) {
			return removeAccordions(jq[0], which);
		},
		update: function(jq, options) {
			return updateAccordion(jq[0], options);
		},
		getSelected: function(jq) {
			return getSelectedAccordion(jq[0]);
		},
		getAccordion: function(jq, which) {
			return getAccordion(jq[0], which);
		},
		getAccordionIndex: function(jq, accordion) {
			return getAccordionIndex(jq[0], accordion);
		},
		select: function(jq, which) {
			return selectAccordion(jq[0], which);
		}
	};

	$.fn[plugin].defaults = {
		width: 'auto',
		multiple: true,
		selected: 0,
		idSeed: 0,
		scrollable: false,
		icon: false,
		tabs: [],
		onSelect: function(title, index) {},
		onAdd: function(title, index) {},
		onRemove: function(title, index) {}
	};

})(jQuery);