/**
 * Tabs（选项卡）扩展
 */
(function($) {
	var plugin = 'tabs';

	function resize(target, options) {
		var outHeight = $(target).attr("data-outHeight");
		if (outHeight && !options.asDemo) {
			$(target).height(outHeight).css("margin", "0px");
			var targetHeight = $(target).height();
			var tabHeight = $(target).find('>ul.nav-tabs').height();
			var content = $(target).find('>div.tab-content');
			if (content.length) {
				var $content = $(content.get(0));
				$content.height(targetHeight - tabHeight - 8);
				$content.find(">div.tab-pane").height("100%");
			}
		}
		//TODO  增加内部栅格自适应,可能会导致一些性能问题 试用
		window.autoComputeHeight && autoComputeHeight();
		
		return $(target);
	}
	/**
	 * 创建Tabs对象
	 * @param  {[type]} target 新的tabs对象
	 */
	function createTabs(target) {
		var $target = $(target);

		//不能清除
		//clear(target);

		var tabsObj = $.data(target, plugin);

		//if($nt.length==0){
		//	return;
		//}

		var targetId = $target.attr('id');

		var dataWidth = $target.attr('data-width') || 'auto';
		var dataHeight = $target.attr('data-height') || 'auto';

		var minWidth = $target.attr('min-width');
		var minHeight = $target.attr('min-height');

		var maxWidth = $target.attr('max-width');
		var maxHeight = $target.attr('max-height');

		//dataWidth = dataWidth==0? 'auto': dataWidth;
		//dataHeight = dataHeight==0? 'auto': dataHeight;
		tabsObj.options.width = dataWidth;
		tabsObj.options.height = dataHeight;


		if (minHeight) {
			tabsObj.options.minHeight = minHeight;
		}
		tabsObj.options.minWidth = minWidth;
		//tabsObj.options.minHeight = minHeight;

		tabsObj.options.maxWidth = maxWidth;
		tabsObj.options.maxHeight = maxHeight;



		//把当前静态页面添加到options里面


		var existsTabs;
		//如果是左右布局
		var $row = $(target).find('>div.row').eq(0);
		var $tabs = $row.length == 0 ? $target.find('>ul.nav') : $row.find('>div>ul.nav'); //选卡标题
		var $content = $row.length == 0 ? $target.find(">div.tab-content"):$row.find(">div>div.tab-content"); //选卡内容

		if ($tabs.length) { //如果存在选卡
			var datas = [];
			var max_idseed = $tabs.length ;
			$.each($tabs.children(), function(i, el) {
				var $el = $(el),
					$a = $el.find('[data-toggle=tab]'),
					$c = $target.find($a.attr('href')),
					idstr = $el.attr("id"),
					idseed = idstr.substring(idstr.lastIndexOf("_")+1);
				if(idseed - max_idseed>0){
					max_idseed = idseed-0 ;
				}
				var data = {
					title: $a.text(),
					titleStyle: $a.attr("style"),
					content: $c.html()
				};
				//手动注册事件 主要解决左右模式下的问题
				$a.bind('click', {
					target: target,
					which: $a.text()
				}, function(event) {
					selectTab(event.data.target, event.data.which);
				});
				var closeable = eval("("+$(target).attr("data-rule")+")") != undefined ? eval("("+$(target).attr("data-rule")+")").closeable : undefined;
				if (closeable == "true") {
					var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
					$closeSpan[0].innerHTML = 'x';
					$closeSpan.attr({
						'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
					}).bind('click', {
						target: target,
						which: $a.text()
					}, function(event) {
						hideTab(event.data.target, event.data.which);
					});
					$closeSpan.appendTo($el);	
				}
				else {
					var $closeSpan = $el.find('.ui-icon-close');
					if ($closeSpan) {
						$closeSpan.remove();
					}
				}
				datas.push(data);
				tabsObj.jqtabs.push($el);
				//$el.data(plugin,data);
				$.data($el,plugin,data);
				tabsObj.tabs.push(data);
				$($el).mouseover(function(){
	                onMouseDownFlag = false;
	                $($el).on('mouseover', function(e){
                		$($el).find("span.ui-icon-close").css("display","block");
                	});
                    onMouseDownFlag = true;
	
	            });
				$($el).mouseout(function(){
	                if(onMouseDownFlag){
	                	$($el).on('mouseout', function(e){
	                		$($el).find("span.ui-icon-close").css("display","none");
	                	});     
	                    // OnMouseUp Code in here
	                }
	            });  
			});
			existsTabs = datas;
			$content.find(">div").css({
				"min-height": tabsObj.options.minHeight + "px"
			});
			

			//$.fn[plugin].defaults.idSeed = $tabs.length+1 ;
			//计算idSeed 算法修改 成 获取存在的最大值
			$.fn[plugin].defaults.idSeed = max_idseed+1 ;
		} else { // 如果不存在
			$tabs = $('<ul>').addClass('nav');
			$target.append($tabs);

			//if($content.length){
			$content = $("<div>").addClass('tab-content');
			$target.append($content);
			//}
		}

		var tabid = target.id + '_' + ($.fn[plugin].defaults.idSeed++);

		//选卡样式
		var tabStyle = $target.attr('data-tabStyle') || tabsObj.options.tabStyle;
		if (tabStyle === 'pills') {
			$tabs.addClass('nav-pills');
		} else {
			$tabs.addClass('nav-tabs');
			$tabs.removeClass('nav-pills');
		}

		//设置tab的位置
		var tabPosition = $target.attr('data-tabPosition') || tabsObj.options.tabPosition.toLowerCase();
		if (tabPosition === 'left' || tabPosition === 'right') {
			$tabs.addClass('tabs-' + tabPosition);
		}

		if (tabStyle === 'line') {
			$target.addClass('tabbable-line');
		} else if (tabStyle === 'custom') {
			$target.addClass('tabbable-custom');
		}
		
		if (tabStyle !== 'line') {
			$target.removeClass('tabbable-line');
			$.each($target.find('.tabbable-line'), function(index, val) {
				var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
				if (_targetId === targetId) {
					$(this).removeClass('tabbable-line');
				}
			});
		}
		if (tabStyle !== 'custom') {
			$target.removeClass('tabbable-custom');
			$.each($target.find('.tabbable-custom'), function(index, val) {
				var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
				if (_targetId === targetId) {
					$(this).removeClass('tabbable-custom');
				}
			});
		}

		if (tabsObj.options.fit) {
			$tabs.addClass('nav-justified');
			$tabs.addClass('nav-justified');
		}


		//tab反方向显示
		var reversed = $target.attr('data-reversed') || (tabsObj.options.reversed + "");
		if (eval(reversed)) {
			$tabs.addClass('tabs-reversed');
		} else {
			$tabs.removeClass('tabs-reversed');
		}

		var width = tabsObj.options.width;
		var height = tabsObj.options.height;
		//宽度
		if (minWidth) {
			$target.css('min-width', minWidth);
		} else if (maxWidth) {
			$target.css('max-width', maxWidth);
		} else {
			$target.width(width);
		}

		//高度
		var panels = $target.find('>div.tab-content>.tab-pane');
		$.each(panels, function(index, val) {
			var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
			if (_targetId === targetId) {
				if (minHeight) {
					$(this).css('min-height', minHeight);
				} else if (maxHeight) {
					$(this).css('max-height', maxHeight);
				} else {
					$(this).height(height);
				}
			}

		});

		if ($tabs.hasClass('tabs-' + tabPosition)) {
			if (minHeight) {
				$tabs.css('min-height', minHeight);
			} else if (maxHeight) {
				$tabs.css('max-height', maxHeight);
			} else {
				$tabs.height(height);
			}
		}

		//边框
		var border = $(target).attr('data-border') || tabsObj.options.border;
		if (eval(border)) {
			$content.css({
				'border': '1px solid #ddd'
			});
		} else {
			$content.css({
				'border': '0px'
			});
		}
		
		if (tabPosition === 'bottom') {
			$(target).find('>div.row').remove();
			//$row
			//tab在下面
			if (tabStyle !== 'line' && tabStyle !== 'custom')
				$content.css({
					'border-bottom': '0px'
				});
			else
				$content.css({
					'border-bottom': '1px solid #ddd'
				});
			$tabs.css({
				'margin-top': '0px'
			});
			$target.addClass('tabs-below');
			$tabs.removeClass('tabs-left tabs-right');
			$tabs.height('auto');
			$target.append($content).append($tabs);
			//$target.append($content).append($tabs);
			//$(target).find('div.row').remove();
			/*$.each($target.find('div.row'), function(index, val) {
				var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
				if (_targetId === targetId) {
					$(this).remove();
				}
			});*/
		} else if (tabPosition === 'top') {
			$(target).find('>div.row').remove();
			//tab在上
			if (tabStyle !== 'line' && tabStyle !== 'custom')
				$content.css({
					'border-top': '0px'
				});
			else
				$content.css({
					'border-top': '1px solid #ddd'
				});
			$tabs.css({
				'margin-bottom': '0px'
			});
			$(target).removeClass('tabs-below');
			$tabs.removeClass('tabs-left tabs-right');
			$tabs.height('auto');
			$(target).append($tabs).append($content);
			//$(target).append($tabs).append($content);
			//$(target).find('div.row').remove();
			/*$.each($(target).find('div.row'), function(index, val) {
				var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
				if (_targetId === targetId) {
					$(this).remove();
				}
			});*/
		} else if (tabPosition === 'left') {
			$(target).find('ul.nav').eq(0).removeClass('tabs-right');
			//tab在左边
			var cols = tabsObj.options.cols;
			var col0 = $target.attr('data-col0') || cols[0];
			var col1 = $target.attr('data-col1') || cols[1];

			if (tabStyle !== 'line' && tabStyle !== 'custom')
				$content.css({
					'border-left': '0px'
				});
			else
				$content.css({
					'border-left': '1px solid #ddd'
				});
			$tabs.css({
				'margin-right': '0px'
			});
			//获取是否已经存在左右布局
			var $row = $target.find('>div.row').eq(0);
			if ($row.length === 0) {
				$row = $('<div>').addClass('row').appendTo(target);
			}
			//
			var $col = $row.find('>div.tabnav');
			if ($col.length === 0) {
				$col = $('<div>').addClass('tabnav').addClass('col-md-' + col0).addClass('col-sm-' + col0).addClass('col-xs-' + col0);
				$col.appendTo($row);
			}
			if (tabStyle === 'line' || tabStyle === 'custom') {
				$col.addClass('tabbable-' + tabStyle);
			}
			$col.css('padding-right', '0px');
			$tabs.appendTo($col);

			var $contentCol = $row.find('>div.contentCol');
			if ($contentCol.length === 0) {
				$contentCol = $('<div>').addClass('contentCol').addClass('col-md-' + col1).addClass('col-sm-' + col1).addClass('col-xs-' + col1);
			}
			$contentCol.appendTo($row);
			$contentCol.css('padding-left', '0px');
			$content.appendTo($contentCol);
		} else if (tabPosition === 'right') {
			$tabs.removeClass('tabs-left');
			//tab在右边
			var cols = tabsObj.options.cols;
			var col0 = $(target).attr('data-col1') || cols[0];
			var col1 = $(target).attr('data-col0') || cols[1];

			if (tabStyle !== 'line' && tabStyle !== 'custom')
				$content.css({
					'border-right': '0px'
				});
			else
				$content.css({
					'border-right': '1px solid #ddd'
				});
			$tabs.css({
				'margin-left': '0px'
			});

			var $row = $(target).find('>div.row').eq(0);
			if ($row.length === 0) {
				$row = $('<div>').addClass('row').appendTo(target);
			}

			var $contentCol = $row.find('>div.contentCol');
			if ($contentCol.length === 0) {
				$contentCol = $('<div>').addClass('contentCol').addClass('col-md-' + col0).addClass('col-sm-' + col0).addClass('col-xs-' + col0);
			}
			$contentCol.appendTo($row);
			$contentCol.css('padding-right', '0px');
			$content.appendTo($contentCol);

			var $col = $row.find('>div.tabnav');
			if ($col.length === 0) {
				$col = $('<div>').addClass('tabnav').addClass('col-md-' + col1).addClass('col-sm-' + col1).addClass('col-xs-' + col1);
			}
			$col.appendTo($row);
			if (tabStyle === 'line' || tabStyle === 'custom') {
				$col.addClass('tabbable-' + tabStyle);
			}
			$col.css('padding-left', '0px');
			$tabs.appendTo($col);
		}
		else if (tabPosition === 'center') {
			$(target).find('>div.row').remove();
			//$row
			//tab在下面
			if (tabStyle !== 'line' && tabStyle !== 'custom')
				$content.css({
					'border-bottom': '0px'
				});
			else
				$content.css({
					'border-top': '1px solid #ddd',
					
					
				});
			$tabs.css({
				'margin-top': '0px'
			});
			$target.css("margin-left","50%");
			$tabs.removeClass('tabs-left tabs-right');
			$tabs.height('auto');
			$target.append($content).append($tabs);
			//$target.append($content).append($tabs);
			//$(target).find('div.row').remove();
			/*$.each($target.find('div.row'), function(index, val) {
				var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
				if (_targetId === targetId) {
					$(this).remove();
				}
			});*/
		}
		else {
			//不设置，默认在上边
			//$(target).append($tabs).append($content);
			if (tabStyle !== 'line' && tabStyle !== 'custom')
				$content.css({
					'border-top': '0px'
				});
			else
				$content.css({
					'border-top': '1px solid #ddd'
				});
			$tabs.css({
				'margin-bottom': '0px'
			});
		}
		//设置tab位置结束

		//如果有设置tab，则添加tab
		if (tabsObj.options.tabs) {
			var tab = tabsObj.options.tabs;
			for (var i = 0; i < tab.length; i++) {
				var tabOptions = tab[i];
				//addTab(target, tabOptions);
				addTab(target, $.extend(true,tabsObj.options,tabOptions));
			}
		}

		//合并存在的tabs 和 动态 选卡
		if (existsTabs && $.isArray(existsTabs) && existsTabs.length) {
			tabsObj.options.existsTabs = $.merge(existsTabs, tabsObj.options.tabs||[]);
		}else{
			tabsObj.options.existsTabs = $.extend(true, [], tabsObj.options.tabs||[]);
		}
		tabsObj.options.tabs = null ;

		

		//如果超出宽度显示下拉为true时
		//则调用bootstrap-tabdrop.js插件重置tab
		if ($.data(target, plugin).options.tabdrop) {
			$(target).addClass('tabbable tabbable-tabdrop');
			var tabdrop = $tabs.tabdrop({
				text: '<i class="fa fa-ellipsis-v"></i><i class="fa fa-angle-down"></i>'
			}).data('tabdrop');
			$.data(target, 'tabdrop', tabdrop);
		}

		tabsObj.options.onLoad.call();
		resize(target, tabsObj.options);
		$(target).resize(function(){
			setTimeout(function(){
		  		resize(target, tabsObj.options);
			},199);
		});
		$(window).resize(function(){
			resize(target, tabsObj.options);
		});
	}

	/**
	 * 创建Tab选项卡
	 * @param  {[type]} target  tabs对象
	 * @param  {[type]} options 需要新创建tab的options属性
	 */
	function createTab(target, options,url) {
		var $tabs = $(target).find('ul.nav').eq(0);
		if ($tabs.length == 0) {
			$tabs = $('<ul>').addClass('nav nav-tabs').appendTo($(target));
		}
		var max_tabid = target.id + '_' + ($.fn[plugin].defaults.idSeed++);

		//内容
		//设置高度与外层窗口高度一致
		var _target = $.data(target, plugin).target;
		var width = options.width || $.data(_target, plugin).options.width;
		var height = options.height || $.data(_target, plugin).options.height;
		if ($.isNumeric(width)) {
			width = width + 'px';
		}
		if ($.isNumeric(height)) {
			height = height + 'px';
		}

		//选项卡
		options.id = options.id || 'tab_li_' + max_tabid;
		options.tabId = max_tabid;
		var $tab = $('<li>').attr({
			'id': options.id,
			'style': 'position:relative;width:' + (options.width || 'auto') + ';' //加入position:relative，主要用于定位关闭图标的位置
		});
		$tab.appendTo($tabs);

		//选项卡标题
		var $tab_a = $('<a>', {
			href: '#' + max_tabid
		}).attr('data-toggle', 'tab').html($('<span style="margin-right:8px">').html(options.title));
		$tab_a.appendTo($tab);
		$tab_a.bind('click', {
			target: _target,
			which: options.title
		}, function(event) {
			selectTab(event.data.target, event.data.which);
		});
		var closeable = eval("("+$(target).attr("data-rule")+")") != undefined ? eval("("+$(target).attr("data-rule")+")").closeable : undefined;
		//可关闭
		if (closeable == "true" || $(target).attr("closeable")) {
			var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
			if($(target).attr("closeable")){
				$closeSpan[0].innerHTML = "x";

				$tab.mouseover(function(){
	                $tab.find("span.ui-icon-close").css("display","block");
	
	            });
				$tab.mouseout(function(){
					$tab.find("span.ui-icon-close").css("display","none");
	            });  
			}else{
				$closeSpan.attr({
					'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px'
				})
			}
			$closeSpan.bind('click', {
				target: target,
				which: options.title
			}, function(event) {
				closeTab(event.data.target, event.data.which);
			});
			$closeSpan.appendTo($tab);
		} else {
			var $closeSpan = $tab.find('.ui-icon-close');
			if ($closeSpan) {
				$closeSpan.remove();
			}
		}
		

		var $content = $('<div>', {
			id: max_tabid,
			//width:width,
			//height:height,
			class: 'tab-pane containerComp fade ui-sortable'
		});

		var minWidth = options.minWidth;
		var minHeight = options.minHeight;

		var maxWidth = options.maxWidth;
		var maxHeight = options.maxHeight;

		if (minWidth) {
			$content.css('min-width', minWidth);
		} else if (maxWidth) {
			$content.css('max-width', maxWidth);
		} else {
			$content.width(width);
		}

		if (minHeight) {
			$content.css('min-height', minHeight);
		} else if (maxHeight) {
			$content.css('max-height', maxHeight);
		} else {
			$content.height(height);
		}

		if (options.href) {
			$content.append('<iframe src="' + options.href + '" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" ></iframe>');
			var $target = $(target);
			var tabsMap = $target.data("tabsMap");
			tabsMap[options.href] = options.id;
		} else if (options.content) {
			$content.append(options.content);
		}
		var $contents = $(target).find('.tab-content').eq(0);
		if ($contents.length == 0) {
			$contents = $("<div>").addClass('tab-content').appendTo($(target));
		}
		$contents.append($content);

		if (options.selected) {
			$content.addClass('active in');
			$($tab).addClass('active');
		}
		$.data(_target, plugin).jqtabs.push($tab);
		if (options.addOptsToTabs || options.children[0]) {
			options.tabIndex = getTabIndex(_target, $tab);
			$.data(_target, plugin).tabs.push(options);
		}
		$.data($tab, plugin, options);

		var tabdrop = $.data(_target, 'tabdrop');
		if (tabdrop) {
			tabdrop.layout();
		}

		$.data(target, plugin).options.onAdd.call(target, options.title, getTabIndex(_target, $tab));
	}

	/**
	 * 添加tab
	 * @param {[type]} target  tabs对象
	 * @param {[type]} options 需要增加的tab的options属性
	 */
	function addTab(target, options) {
		options = $.extend({
			id: null,
			width: 'auto',
			title: '',
			content: '',
			href: null,
			icon: null,
			closeable: false,
			selected: true,
			isDropdownMenu: false,
			addOptsToTabs: true,
			children: []
		}, options || {});

		if(options.href){
			var $target = $(target);
			var tabsMap = $target.data("tabsMap");
			if(!tabsMap){
				tabsMap = {};
				$target.data("tabsMap",tabsMap);
			}
			if(tabsMap[options.href]){
				$("#"+tabsMap[options.href]+">a").trigger("click");
				return;
			}
			var maxtabsnum = $target.attr("maxtabsnum");
			if(maxtabsnum && Object.keys(tabsMap).length == maxtabsnum){
				//为最大时，先删除后添加
				var $lasttab = $target.find(".nav-tabs li:last");
				var lasttabid = $lasttab.attr("id");
				$.each(tabsMap,function(i,item){
					if(item == lasttabid){
						delete tabsMap[i];
						return false;
					}
				})
				$($lasttab.attr("href")).remove();
				$lasttab.remove();
			}
		}

		if (!options.isDropdownMenu) {
			options.children = [];
		}

		if (options.isDropdownMenu) {
			var tabsOptions = $.data(target, plugin).options;

			var $tabs = $(target).find('ul.nav').eq(0);
			var max_tabid = target.id + '_' + ($.fn[plugin].defaults.idSeed++);

			//选项卡
			var tabid = options.id || 'tab_li_children_' + max_tabid;
			var $tab = $('<li>').attr({
				'id': tabid,
				'style': 'position:relative;width:' + (options.width || 'auto') + ';' //加入position:relative，主要用于定位关闭图标的位置
			}).addClass('dropdown');
			$tab.appendTo($tabs);

			var $dropdown_a = $('<a>').attr({
				'href': 'javascript:;',
				'data-toggle': 'dropdown',
				'tabindex': '-1'
			}).addClass('dropdown-toggle').html(options.title).appendTo($tab);
			var closeable = eval("("+$(target).attr("data-rule")+")") != undefined ? eval("("+$(target).attr("data-rule")+")").closeable : undefined;
			
			//可关闭
			if (closeable == "true") {
				var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
				$closeSpan.attr({
					'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px'
				}).bind('click', {
					target: target,
					which: options.title
				}, function(event) {
					closeTab(event.data.target, event.data.which);
				});
				$closeSpan.appendTo($tab);
			}
				else {
					var $closeSpan = $tab.find('.ui-icon-close');
					if ($closeSpan) {
						$closeSpan.remove();
					}
			}
			
			var $dropdown_pic = $('<i>').addClass('fa').appendTo($dropdown_a);
			var dropdown_ul = $('<ul>').attr('role', 'menu').addClass('dropdown-menu').appendTo($tab);
			var tabPosition = tabsOptions.tabPosition.toLowerCase();
			if (tabPosition === 'bottom') {
				$($tab).addClass('dropup');
				$dropdown_pic.addClass('fa-angle-up');
			} else {
				$dropdown_pic.addClass('fa-angle-down');
			}

			$.data(target, plugin).jqtabs.push($tab);

			options.tabIndex = getTabIndex(target, $tab);
			$.data(target, plugin).tabs.push(options);

			$.data($tab, plugin, {
				options: $.extend(true, {}, $.fn[plugin].defaults, $.data($tab, plugin)),
				target: target,
				tabs: [],
				isDropdownMenu: true
			});

			$.each(options.children, function(index, val) {
				createTab($tab, val);
			});
			return;
		}

		//是否选择
		if (options.selected) {
			var targetId = $(target).attr('id');
			$.each($(target).find('li'), function(index, val) {
				var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
				if (_targetId === targetId) {
					$(this).removeClass('active');
				}
			});
			var panels = $(target).find('.tab-content>.tab-pane');
			$.each(panels, function(index, val) {
				var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
				if (_targetId === targetId) {
					$(this).removeClass('active').removeClass('in');
				}
			});
		}

		$.data($(target), plugin, {
			options: $.extend(true, {}, $.fn[plugin].defaults),
			target: target
		});
		createTab(target, options);
	}


	/**
	 * 更新tab
	 * @param  {[type]} target  tabs对象
	 * @param  {[type]} params  更新属性
	 * @return {[type]}         [description]
	 */
	function updateTab(target, params) {
		var $tab = params.tab;

		var options = $.extend($.data($tab, plugin), params.options || {});


		$tab.find('a:eq(0)').bind('click', {
			target: target,
			which: options.title
		}, function(event) {
			selectTab(event.data.target, event.data.which);
		}).children('span').html(options.title);
		var closeable = eval("("+$(target).attr("data-rule")+")") != undefined ? eval("("+$(target).attr("data-rule")+")").closeable : undefined;
		
		if (closeable == "true") {
			var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
			$closeSpan.attr({
				'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px'
			}).bind('click', {
				target: target,
				which: options.title
			}, function(event) {
				closeTab(event.data.target, event.data.which);
			});
			$closeSpan.appendTo($tab);
		} else {
			var $closeSpan = $tab.find('.ui-icon-close');
			if ($closeSpan) {
				$closeSpan.remove();
			}
		}

		//内容
		var $content = $($tab.find('a:eq(0)').attr('href'));
		if (options.href) {
			if ($content.find('p:eq(0)')) {
				$content.find('p:eq(0)').remove();
			}
			//设置高度与外层窗口高度一致
			var width = $.data(target, plugin).options.width;
			var height = $.data(target, plugin).options.height;
			if ($.isNumeric(width)) {
				width = width + 'px';
			}
			if ($.isNumeric(height)) {
				height = height + 'px';
			}

			var $frame = $content.find('iframe:eq(0)');
			if ($frame && $frame.length > 0) {
				$frame.attr('src', options.href);
			} else {
				$frame = $('<iframe src="' + options.href + '" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" ></iframe>');
				if (options.onload && $.isFunction(options.onload)) {
					$frame.bind("load", function() {
						options.onload.call(this, options);
					});
				}
				$content.append($frame);
			}
		} else if (options.content) {
			var $frame = $content.find('iframe:eq(0)');
			if ($frame && $frame.length > 0) {
				$frame.remove();
			}
			var $p = $content.find('p:eq(0)');
			if ($p && $p.length > 0) {
				$p.html(options.content);
			} else {
				$content.append('<p style="margin-top:0px">' + options.content + '</p>');
			}
		}

		if (options.id) {
			$tab.attr('id', options.id);
		}

		$.data($tab, plugin, options);
		$.data(target, plugin).options.onUpdate.call(target, options.title, getTabIndex(target, $tab));


		if (options.selected) {
			selectTab(target, options.title);
		}
	}

	/**
	 * 关闭Tab
	 * @param  {[type]} target tabs对象
	 * @param  {[type]} which  tab标签，可以为tabIndex，也可以为title
	 * @return {[type]}        [description]
	 */
	function closeTab(target, which) {

		if (!exists(target, which)) return;

		var data = $.data(target, plugin);
		var tabs = data.jqtabs;
		var options = data.options;

		//取得当前选中的tab标题
		var currentSelectTitle = $.data(getSelectedTab(target), plugin).title;

		var index = getTabIndex(target, getTab(target, which));

		var $tab = getTab(target, which, true);
		var title = $.data($tab, plugin).title;
		var contentId = $tab.find('a:first').attr('href');

		var $target = $(target);
		var tabsMap = $target.data("tabsMap");
		if(tabsMap){
			var tabid = $tab.attr("id");
			$.each(tabsMap,function(i,item){
				if(item == tabid){
					delete tabsMap[i];
					return false;
				}
			})
		}

		//判断需要关闭的tab是否为当前选中的tab
		var isCurrentSelect = false;
		if (currentSelectTitle == title) {
			isCurrentSelect = true;
		}

		$(contentId).remove();
		$tab.remove();
		$.removeData($tab);
		options.onClose.call(target, title, index);

		//如果关闭的tab是当前选中的tab
		//则删除完之后选择最后一个tab
		if (isCurrentSelect) {
			var tab = tabs[tabs.length - 1];
			if (tab) {
				selectTab(target, $.data(tab, plugin).title);
			} else {
				selectTab(target, 0);
			}
		}

		var tabdrop = $.data(target, 'tabdrop');
		if (tabdrop) {
			tabdrop.layout();
		}
	}

	/**
	 * 隐藏 tab
	 * @param  {[type]} target tabs对象
	 * @param  {[type]} which  tab标签，可以为tabIndex，也可以为title
	 * @return {[type]}        [description]
	 */
	function hideTab(target, which) {
		if (!exists(target, which)) return;

		var data = $.data(target, plugin);
		var tabs = data.jqtabs;
		var options = data.options;

		//取得当前选中的tab标题
		var currentSelectTitle = $.data(getSelectedTab(target), plugin).title;

		var index = getTabIndex(target, getTab(target, which));

		var $tab = getTab(target, which, false);
		var title = $.data($tab, plugin).title;
		var contentId = $tab.find('a:first').attr('href');

		//判断需要关闭的tab是否为当前选中的tab
		var isCurrentSelect = false;
		if (currentSelectTitle == title) {
			isCurrentSelect = true;
		}

		$(contentId).hide();
		$tab.hide();
		//$.removeData($tab);
		options.onClose.call(target, title, index);

		//如果关闭的tab是当前选中的tab
		//则删除完之后选择最后一个tab
		if (isCurrentSelect) {
			var tab = tabs[tabs.length - 1];
			if (tab) {
				selectTab(target, $.data(tab, plugin).title);
			} else {
				selectTab(target, 0);
			}
		}

		var tabdrop = $.data(target, 'tabdrop');
		if (tabdrop) {
			tabdrop.layout();
		}
	}

	/**
	 * 显示 tab
	 * @param  {[type]} target tabs对象
	 * @param  {[type]} which  tab标签，可以为tabIndex，也可以为title
	 * @return {[type]}        [description]
	 */
	function showTab(target, which) {

		if (!exists(target, which)) return;

		var data = $.data(target, plugin);
		var tabs = data.jqtabs;
		var options = data.options;

		//取得当前选中的tab标题
		var currentSelectTitle = $.data(getSelectedTab(target), plugin).title;

		var index = getTabIndex(target, getTab(target, which));

		var $tab = getTab(target, which, false);
		var title = $.data($tab, plugin).title;
		var contentId = $tab.find('a:first').attr('href');

		//判断需要关闭的tab是否为当前选中的tab
		var isCurrentSelect = false;
		if (currentSelectTitle == title) {
			isCurrentSelect = true;
		}

		$(contentId).show().css("display", "");
		$tab.show();
		//$.removeData($tab);
		options.onClose.call(target, title, index);

		//如果关闭的tab是当前选中的tab
		//则删除完之后选择最后一个tab
		if (isCurrentSelect) {
			var tab = tabs[tabs.length - 1];
			if (tab) {
				selectTab(target, $.data(tab, plugin).title);
			} else {
				selectTab(target, 0);
			}
		}

		var tabdrop = $.data(target, 'tabdrop');
		if (tabdrop) {
			tabdrop.layout();
		}
	}
	/**
	 * 获取选中的tab
	 * @param  {[type]} target tabs对象
	 * @return {[type]}        当前选中的tab
	 */
	function getSelectedTab(target) {
		var tabs = $.data(target, plugin).jqtabs;
		for (var i = 0; i < tabs.length; i++) {
			var $tab = tabs[i];
			if ($tab.hasClass('active')) {
				return $tab;
			}
		}
		return null;
	}

	/**
	 * 选择tab
	 * @param  {[type]} target tabs对象
	 * @param  {[type]} which  tab标签，可以为tabIndex，也可以为title
	 * @return {[type]}        选中的tab
	 */
	function selectTab(target, which) {
		if (which != undefined) {
			var $elem = getTab(target, which);
			if ($elem) {
				$elem.find('a:first').tab('show');
				$.data(target, plugin).options.onSelect.call(target, $.data($elem, plugin).title, getTabIndex(target, $elem));
				//TODO  增加内部栅格自适应,可能会导致一些性能问题 试用
				setTimeout(function(){
					window.autoComputeHeight && autoComputeHeight();
					if($(target).find("div[data-role=echarts]").data("echarts")!=null && $(target).find("div[data-role=echarts]").data("echarts") != undefined){
						var targetEcharts = $(target).find("div[data-role=echarts]");
						$.each(targetEcharts,function(i,echart){
//							$(echart).data("echarts")._init();
							$(echart).data("echarts").resize({
								height : '100%',
								width:'100%'
							});
						});
					}
					
				},200)
				return $elem;
			}
		}
		return null;
	}

	/**
	 * 获取tabIndex
	 * @param  {[type]} target tabs对象
	 * @param  {[type]} tab    tab对象
	 * @return {[type]}        tabIndex
	 */
	function getTabIndex(target, tab) {
		var tabs = $.data(target, plugin).jqtabs;
		for (var i = 0; i < tabs.length; i++) {
			if (tabs[i][0] == $(tab)[0]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 取得tab
	 * @param  {[type]} target   tabs对象
	 * @param  {[type]} which    tab标签，可以为tabIndex，也可以为title
	 * @param  {[type]} removeit 获取后是否删除
	 * @return {[type]}          返回匹配的tab
	 */
	function getTab(target, which, removeit) {
		var tabs = $.data(target, plugin).jqtabs;
		if (typeof which == 'number') {
			if (which < 0 || which >= tabs.length) {
				return null;
			} else {
				var tab = tabs[which];
				if (removeit) {
					tabs.splice(which, 1);
					$.data(target, plugin).tabs.splice(which, 1);
				}
				return tab;
			}
		}
		for (var i = 0; i < tabs.length; i++) {
			var tab = tabs[i];
			var taboption = $.data(tab, plugin);
			if (taboption.title == which) {
				if (removeit) {
					tabs.splice(i, 1);
					$.data(target, plugin).tabs.splice(i, 1);
				}
				return tab;
			}
		}
		return null;
	}

	/**
	 * set selected tab panel size
	 */
	function setSelectedSize(container) {
		var opts = $.data(container, plugin).options;
		var tab = getSelectedTab(container);
		if (tab) {
			var panels = $(container).children('div.tabs-panels');
			var width = opts.width == 'auto' ? 'auto' : panels.width();
			var height = opts.height == 'auto' ? 'auto' : panels.height();
			tab.panel('resize', {
				width: width,
				height: height
			});
		}
	}

	/**
	 * 判断tab是否存在
	 * @param  {[type]} target tabs对象
	 * @param  {[type]} which  tab标签，可以为tabIndex，也可以为title
	 * @return {[type]}        [description]
	 */
	function exists(target, which) {
		return getTab(target, which) != null;
	}

	/**
	 * 绑定事件
	 * @param  {[type]} target [description]
	 * @return {[type]}        [description]
	 */
	function bindEvents(target) {
		var state = $.data(target, plugin)
		var opts = state.options;

		$(target).find('>ul.nav-tabs>li>a').bind('click', {
			target: target
		}, function(event) {
			/*var splits = $(this).attr("href").split("_"),
				index = parseInt(splits[splits.length - 1]) - 1;*/
			selectTab(event.data.target, /*index*/$(this).parent().index());
		});
	}

	/**
	 * 初始化时选中tab
	 * @param  {[type]} target tabs对象
	 * @return {[type]}        [description]
	 */
	function doFirstSelect(target) {
		var state = $.data(target, plugin);
		var tabs = state.jqtabs;
		for (var i = 0; i < tabs.length; i++) {
			var opts = $.data(tabs[i], plugin);
			if (opts.selected) {
				selectTab(target, i);
				return;
			}
		}
		var design = $(target).attr("design"),
		    tabSelected = eval("("+design+")") != undefined ? eval("("+design+")").tabSelected : undefined;
		if(tabSelected != undefined && tabSelected != ''){
			state.options.selected = parseInt(tabSelected);
		}
		//if()
		selectTab(target, state.options.selected);
	}

	function loadData(target, tabs) {
		updateTabs(target, tabs);
		createTabs(target);
	}

	function getTabContent(tab) {
		return $('#' + tab.tabId).html();
	}

	function setContents(target, tabs) {
		var datas = []
		$.each(tabs, function(index, tab) {
			datas.push($.extend(true, tab, {
				content: getTabContent(tab)
			}));
		});
		return datas;
	}

	function updateTabs(target, tabs) {
		var opts = getOptions(target);
		opts.tabs = tabs;
	}

	function clear(target) {
		$.data(target, plugin).tabs = [];
		$(target).empty();
	}

	function destroy(target) {
		clear(target);
	}

	function getOptions(target) {
		return $(target).data(plugin).options;
	}

	//初始化
	$.fn[plugin] = function(options, param) {

		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			$.each($(o).children(), function(index, val) {
				//val.remove();
			});

			var state = $.data(o, plugin);
			if (state) {
				//state.jqtabs = [];
				//state.tabs = [];
				$.extend(true, state.options, options);
			} else {
				$.data(o, plugin, {
					options: $.extend(true, {}, $.fn[plugin].defaults, options),
					target: o,
					jqtabs: [],
					tabs: [],
					datas: param
				});
			}
			createTabs(o);
			doFirstSelect(this);

			bindEvents(this);
			
		});
	};

	//外部调用方法
	$.fn[plugin].methods = {
		options: function(jq) {
			var cc = jq[0];
			var opts = $.data(cc, plugin).options;
			var s = getSelectedTab(cc);
			opts.selected = s ? getTabIndex(cc, s) : -1;
			return opts;
		},
		tabs: function(jq) {
			return $.data(jq[0], plugin).tabs;
		},
		add: function(jq, options) {
			return jq.each(function() {
				addTab(this, options);
			});
		},
		load: function(jq, tabs) {
			return jq.each(function() {
				loadData(this, tabs);
			});
		},
		destroy: function(jq) {
			return jq.each(function() {
				destroy(this);
			});
		},
		close: function(jq, which) {
			return closeTab(jq[0], which);
		},
		getTab: function(jq, which) {
			return getTab(jq[0], which);
		},
		getTabIndex: function(jq, tab) {
			return getTabIndex(jq[0], tab);
		},
		getTabContent: function(jq, tab) {
			return getTabContent(tab);
		},
		setContents: function(jq, tabs) {
			return setContents(jq[0], tabs);
		},
		getSelected: function(jq) {
			return getSelectedTab(jq[0]);
		},
		select: function(jq, which) {
			return selectTab(jq[0], which);
		},
		exists: function(jq, which) {
			return exists(jq[0], which);
		},
		update: function(jq, params) {
			return updateTab(jq[0], params);
		},
		getCols: function(jq) {
			return;
		},
		resize: function(jq, options) {
			return resize(jq);
		},
		hide: function(jq, which) {
			return hideTab(jq[0], which);
		},
		show: function(jq, which) {
			return showTab(jq[0], which);
		}
	};

	$.fn[plugin].defaults = {
		asDemo: false,
		width: 'auto',
		height: 'auto',
		idSeed: 0,
		selected: 0, //初始化时默认选中的tab
		fit: false, //是否拉伸宽度
		tabdrop: false, //超出宽度时是否展现下拉
		border: false, //边框
		reversed: false, //反向
		tabPosition: 'top', //tab位置:top、bottom、left、right
		tabStyle: 'tabs', //tab样式:tabs、pills、line、custom
		tabs: [],
		cols: [2, 10], //tabPosition为left或right时，左右的间隔，总和为12，例如[8,4]
		onLoad: function() {},
		onSelect: function(title, index) {},
		onClose: function(title, index) {},
		onAdd: function(title, index) {},
		onUpdate: function(title, index) {}
	};

	$(document).ready(function() {
		//$("div.tabbable").tabs();
	});

})(jQuery);