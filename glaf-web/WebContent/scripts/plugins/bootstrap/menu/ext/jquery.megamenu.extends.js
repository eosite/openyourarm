! function($) {

	//默认参数
	var defaults = {
		url: contextPath + "/mx/form/menuData/data", //远程获取数据地址
		ajax: {
			url: '',
			type: 'post',
			dataType: 'json',
			data: {},
			contentType: "application/json",
			async: true,
			success: function(ret) {},
			error: function() {
				//console.log("Ajax请求超时!");
			}
		},
		parameterMap: function(params) {
			return JSON.stringify(params);
		}
	};

	var selectColors = {
		'#36c6d3': 'active-mt-success',
		'#337ab7': 'active-mt-primary',
		'#ed6b75': 'active-mt-error',
		'#ed6b75': 'active-mt-error',
		'#F1C40F': 'active-mt-warning',
		'#659be0': 'active-mt-info',
		'#bac3d0': 'active-mt-default'
	};

	$.fn.megaMenuExt = function(opts) {
		if (methods[opts]) {
			return methods[opts].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof opts === 'object' || !opts) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + opts + ' does not exist on jQuery.megaMenuExt');
		}
	};


	var methods = {
		init: function(options) { //初始化
			var opts = $.extend(true, {}, defaults, options);
			var $this = $(this);
			opts.textStyle = $this.find("ul>li>a").eq(0).attr("style");
			$this.data("opts", opts);
			methods.initElement.call(this, opts);
			methods.display.call(this, opts.visible);
			methods.initEvent($this);
			methods._bindGobalEvent($this);
		},
		initEvent: function($this){
			if($this.hasClass("horizontal-menu")){
				var $preBtn = $this.find(".megamenu-preBtn");
				var $nextBtn = $this.find(".megamenu-nextBtn");
				$preBtn.hide();
				$nextBtn.hide();
				//整体宽度，即每次移动的宽度
				var $megamenu_inner = $this.find(".page-header-inner");
				//滚动体的宽度
				var $megamenu_hor_menu = $this.find(".hor-menu");

				var movewidth = $megamenu_inner.width();
				var maxwidth = $megamenu_hor_menu.find(">.nav").width();
				//滚动体的宽度小于整体宽度，则无需下移
				if(maxwidth <= movewidth){
					$nextBtn.hide();
				}else{
					$nextBtn.show();
				}
				
				var lockflag = false;
				$preBtn.click(function(){
					if(lockflag){
						return;
					}
					var movewidth = $megamenu_inner.width();
					//下一次滚动体的位置 = 滚动体位置+整体宽度
					lockflag = true;
					var nowleft = $megamenu_hor_menu.position().left + movewidth;
					if(nowleft > 0){
						$preBtn.hide();
						$megamenu_hor_menu.stop().animate({left: 0},300);
					}else{
						$megamenu_hor_menu.stop().animate({left: nowleft},300);
					}
					$nextBtn.show();
					setTimeout(function(){
						lockflag = false;
					},300);
				})
				$nextBtn.click(function(){
					if(lockflag){
						return;
					}
					var movewidth = $megamenu_inner.width();
					var maxwidth = $megamenu_hor_menu.find(">.nav").width() - movewidth;
					//下一次滚动体的位置 = 滚动体位置-整体宽度
					lockflag = true;
					var nowleft = $megamenu_hor_menu.position().left - movewidth;
					if(nowleft < -maxwidth){
						$nextBtn.hide();
						$megamenu_hor_menu.stop().animate({left: -maxwidth},300);
					}else{
						$megamenu_hor_menu.stop().animate({left: nowleft},300);
					}
					$preBtn.show();
					setTimeout(function(){
						lockflag = false;
					},300);
				})
			}
		},
		initElement: function(options) {
			//处理操作类型
			var $this = $(this);
			$this.removeAttr("contenteditable");

			if ($this.find("div.hor-menu").length > 0) {
				$this.find("div.hor-menu").html("");
			} else {
				$this.html("");
			}

			if (options.fixDataSource && options.fixDataSource.length) {
				methods.createElement($this, options, options.fixDataSource);
				return;
			}
			if (!methods.isEmptyObject(options.columns)) {
				methods._doAjax.call(this, options,options.param);
			}

		},
		query : function(params){
			var $this = $(this);
			$this.find("div.hor-menu").empty();
			var options = $this.data("opts");
			methods._doAjax.call(this, options,params);
		},
		_doAjax: function(opts,params) { //发送ajax请求

			var $this = $(this);
			var dataParams = {};
			if(params){
				dataParams.params = JSON.stringify(params);
			}
			dataParams.rid = opts.ruleId;
			var ar = opts.ajax;
			ar.data = dataParams;
			
			ar.url = opts.url;
			ar.success = function(ret) {

				var sNodes = ret.data,
					key = opts.columns.indexKey;
				var parentKey = opts.columns.pIdKey,
					childKey = "children";
				var r = [];
				var tmpMap = [];
				for (i = 0, l = sNodes.length; i < l; i++) {
					tmpMap[sNodes[i][key]] = sNodes[i];
				}
				for (i = 0, l = sNodes.length; i < l; i++) {
					if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] != sNodes[i][parentKey]) {
						if (!tmpMap[sNodes[i][parentKey]][childKey])
							tmpMap[sNodes[i][parentKey]][childKey] = [];
						tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
					} else {
						r.push(sNodes[i]);
					}
				}
				methods.createElement($this, opts, r);

				if($this.hasClass("horizontal-menu")){
					var $nextBtn = $this.find(".megamenu-nextBtn");
					//滚动体的宽度
					var $megamenu_hor_menu = $this.find(".hor-menu");
					//整体宽度，即每次移动的宽度
					var $megamenu_inner = $this.find(".page-header-inner");
					var movewidth = $megamenu_inner.width();
					var maxwidth = $megamenu_hor_menu.find(">.nav").width();
					//滚动体的宽度小于整体宽度，则无需下移
					if(maxwidth <= movewidth){
						$nextBtn.hide();
					}else{
						$nextBtn.show();
					}
				}
				//-----默认选中第一行
				if($this.attr("tSelectFirst")){
					$this.find("a.nav-link:not(.nav-toggle):first").trigger("click");
				}
			}
			ar.data = opts.parameterMap(ar.data);
			$.ajax(ar);
		},
		createElement: function($this, opts, r) {
			if($this.hasClass("horizontal-menu-tab")){
				$ul = $("<ul class=\"nav navbar-nav\">");
				$.each(r, function(i, o) {
					var icon = o.icon != undefined ? o.icon : o[opts.columns.icon];
					var name = o.name != undefined ? o.name : o[opts.columns.nameKey];
					if (o.name != undefined) {
						o[opts.columns.icon] = o.icon;
						o[opts.columns.nameKey] = o.name;
						o[opts.columns.urlKey] = o.url;
					}
					if (i > 0) {
						$ul.append('<div class="lineDiv"></div>');
					}
					var li = $('<li class="classic-menu-dropdown"></li>');
					var a = $('<a href="#" data-hover="megamenu-dropdown" data-close-others="true"><i class="' + icon + '"></i>' + name + '</a>');
					li.append(a);
					li.data("mObj", o);
					$ul.append(li);
					$this.find("div.hor-menu").append($ul);
				});
			}else if($this.hasClass("vertical-menu")){
				$ul = $("<ul class=\"page-sidebar-menu-mt page-sidebar-menu-light page-header-fixed page-sidebar-menu-hover-submenu-mt \" data-keep-expanded=\"false\" data-auto-scroll=\"true\" data-slide-speed=\"200\">");
				$.each(r, function(i, o) {
					var icon = o.icon != undefined ? o.icon : o[opts.columns.icon];
					var name = o.name != undefined ? o.name : o[opts.columns.nameKey];
					if (o.name != undefined) {
						o[opts.columns.icon] = o.icon;
						o[opts.columns.nameKey] = o.name;
						o[opts.columns.urlKey] = o.url;
					}
					if (o.children && o.children.length) {
						var li = $('<li class="nav-item nav-toggle">');
						//兼容图片
						var $iconDiv = $('<div class="megamenu_icon"></div>');
						if (icon) {
							var $icon;
							if (icon.indexOf(".") > 0) {
								$icon = $('<img src="' + contextPath + icon + '">');
							} else {
								$icon = $('<i class="' + icon + '"></i>');
							}
							$iconDiv.append($icon);
						}else{
							$icon = $('<img src="' + contextPath + '/scripts/home/default/images/leftico01.png">');
							$iconDiv.append($icon);
						}
						var $text = $('<span class="title">' + name + '</span>');
						var a = $('<a href="javascript:;" class="nav-link nav-toggle"></a>');
						a.prepend($text);
						a.prepend($iconDiv);
						li.append(a);
						li.data("mObj", o);
						var ul = methods.createMenu2(o.children, opts,true);
						li.append(ul);
						$ul.append(li);
					} else {
						var li = $('<li class="nav-item"></li>');
						//兼容图片
						var $iconDiv = $('<div class="megamenu_icon"></div>');
						if (icon) {
							var $icon;
							if (icon.indexOf(".") > 0) {
								$icon = $('<img src="' + contextPath + icon + '">');
							} else {
								$icon = $('<i class="' + icon + '"></i>');
							}
							$iconDiv.append($icon);
						}else{
							$icon = $('<img src="' + contextPath + '/scripts/home/default/images/leftico01.png">');
							$iconDiv.append($icon);
						}

						var $text = $('<span class="title">' + name + '</span>');
						// var a=$('<a href="#" class="nav-link"> <i class="'+icon+'"></i><span class="title">'+name+'</span></a>');
						var a = $('<a href="#" class="nav-link not-toggle"></a>');
						a.prepend($text);
						a.prepend($iconDiv);
						li.append(a);
						li.data("mObj", o);
						$ul.append(li);
					}
				});
				$this.find("div.hor-menu").append($ul);
			}else if($this.hasClass("horizontal-menu")){
				$ul = $("<ul class=\"nav navbar-nav\">");
				$.each(r, function(i, o) {
					var icon = o.icon != undefined ? o.icon : o[opts.columns.icon];
					var name = o.name != undefined ? o.name : o[opts.columns.nameKey];
					if (o.name != undefined) {
						o[opts.columns.icon] = o.icon;
						o[opts.columns.nameKey] = o.name;
						o[opts.columns.urlKey] = o.url;
					}

					var li = $('<li class="nav-item">');
					//兼容图片
					var $iconDiv = $('<div class="megamenu_icon"></div>');
					if (icon) {
						var $icon;
						if (icon.indexOf(".") > 0) {
							$icon = $('<img src="' + contextPath + icon + '">');
						} else {
							$icon = $('<i class="' + icon + '"></i>');
						}
						$iconDiv.append($icon);
					}
					var $text = $('<span class="title">' + name + '</span>');
					var a = $('<a href="javascript:;" class="nav-link"><span class="arrow"></span></a>');
					a.prepend($text);
					a.prepend($iconDiv);
					li.append(a);
					li.data("mObj", o);
					$ul.append(li);
				});
				$this.find("div.hor-menu").append($ul);
			}else if ($this.hasClass("page-header-mt")) {
				$ul = $("<ul class=\"nav navbar-nav\">");
				$.each(r, function(i, o) {
					var icon = o.icon != undefined ? o.icon : o[opts.columns.icon];
					var name = o.name != undefined ? o.name : o[opts.columns.nameKey];
					if (o.name != undefined) {
						o[opts.columns.icon] = o.icon;
						o[opts.columns.nameKey] = o.name;
						o[opts.columns.urlKey] = o.url;
					}
					if (i > 0) {
						$ul.append('<div class="lineDiv"></div>');
					}
					if (o.children && o.children.length) {
						var li = $('<li class="classic-menu-dropdown"></li>');
						var a = $('<a href="javascript:;" class="nav-toggle" data-hover="megamenu-dropdown" data-close-others="true"><i class="' + icon + '"></i>' + name + '</a>');
						li.append(a);
						li.data("mObj", o);
						var ul = methods.createMenu(o.children, opts);
						li.append(ul);
						$ul.append(li);
						$this.find("div.hor-menu").append($ul);
					} else {
						var li = $('<li class="classic-menu-dropdown"></li>');
						var a = $('<a href="#" data-hover="megamenu-dropdown" data-close-others="true"><i class="' + icon + '"></i>' + name + '</a>');
						li.append(a);
						li.data("mObj", o);
						$ul.append(li);
						$this.find("div.hor-menu").append($ul);
					}
				});
			} else {
				$ul = $("<ul class=\"page-sidebar-menu-mt page-sidebar-menu-light page-header-fixed page-sidebar-menu-hover-submenu-mt \" data-keep-expanded=\"false\" data-auto-scroll=\"true\" data-slide-speed=\"200\" style=\"padding-top: 20px\">");
				$.each(r, function(i, o) {
					var icon = o.icon != undefined ? o.icon : o[opts.columns.icon];
					var name = o.name != undefined ? o.name : o[opts.columns.nameKey];
					if (o.name != undefined) {
						o[opts.columns.icon] = o.icon;
						o[opts.columns.nameKey] = o.name;
						o[opts.columns.urlKey] = o.url;
					}
					if (o.children && o.children.length) {
						var li = $('<li class="nav-item">');
						//兼容图片
						var $iconDiv = $('<div class="megamenu_icon"></div>');
						if (icon) {
							var $icon;
							if (icon.indexOf(".") > 0) {
								$icon = $('<img src="' + contextPath + icon + '">');
							} else {
								$icon = $('<i class="' + icon + '"></i>');
							}
							$iconDiv.append($icon);
						}
						var $text = $('<span class="title">' + name + '</span>');
						var a = $('<a href="javascript:;" class="nav-link nav-toggle"><span class="arrow"></span></a>');
						a.prepend($text);
						a.prepend($iconDiv);
						li.append(a);
						li.data("mObj", o);
						var ul = methods.createMenu2(o.children, opts);
						li.append(ul);
						$ul.append(li);
						$this.append($ul);
					} else {
						var li = $('<li class="nav-item"></li>');
						//兼容图片
						var $iconDiv = $('<div class="megamenu_icon"></div>');
						if (icon) {
							var $icon;
							if (icon.indexOf(".") > 0) {
								$icon = $('<img src="' + contextPath + icon + '">');
							} else {
								$icon = $('<i class="' + icon + '"></i>');
							}
							$iconDiv.append($icon);
						}

						var $text = $('<span class="title">' + name + '</span>');
						// var a=$('<a href="#" class="nav-link"> <i class="'+icon+'"></i><span class="title">'+name+'</span></a>');
						var a = $('<a href="#" class="nav-link"></a>');
						a.prepend($text);
						a.prepend($iconDiv);
						li.append(a);
						li.data("mObj", o);
						$ul.append(li);
						$this.append($ul);
					}
				});
			}

			if (opts.handleType == "hover") {
				methods._renderHoverEvent($this);

			}
			if ($this.hasClass("page-sidebar-mt") && (opts.handleType == "click")) {
				$this.find("ul.page-sidebar-menu-mt").removeClass("page-sidebar-menu-hover-submenu-mt");
				methods._renderClickEvent($this);
			}
			methods.addStyle($this, opts);
			//				methods.renderSelectColor($this);
		},
		addStyle: function($this, opts) {
			$this.find("ul li a").attr("style", opts.textStyle);
		},
		createMenu: function(o, opts) {
			var ul = $('<ul class="dropdown-menu">');
			$.each(o, function(j, p) {
				var icon = p.icon != undefined ? p.icon : p[opts.columns.icon];
				var name = p.name != undefined ? p.name : p[opts.columns.nameKey];
				if (p.name != undefined) {
					p[opts.columns.icon] = p.icon;
					p[opts.columns.nameKey] = p.name;
					p[opts.columns.urlKey] = p.url;
				}
				if (p.children && p.children.length) {
					var sli = $('<li class="dropdown-submenu"><a href="javascript:;" class="nav-toggle"><i class="' + icon + '"></i>' + name + '</a></li>');
					sli.data("mObj", p);
					ul.append(sli);
					var sul = methods.createMenu(p.children, opts);
					sli.append(sul);
				} else {
					var sli = $('<li><a href="#"><i class="' + icon + '"></i>' + name + '</a></li>');
					sli.data("mObj", p);
					ul.append(sli);
				}
			});
			return ul;
		},
		createMenu2: function(o, opts,isvertical) {
			var ul = $('<ul class="sub-menu">');
			$.each(o, function(j, p) {
				var icon = p.icon != undefined ? p.icon : p[opts.columns.icon];
				var name = p.name != undefined ? p.name : p[opts.columns.nameKey];
				if (p.name != undefined) {
					p[opts.columns.icon] = p.icon;
					p[opts.columns.nameKey] = p.name;
					p[opts.columns.urlKey] = p.url;
				}
				if(isvertical){
					var li = $('<li class="nav-item"></li>');
					var a = $('<a href="#" class="nav-link not-toggle"></a>');

					a.append('<div class="selectDiv"></div>');
					a.append('<div class="splitDiv"></div>');
					a.append('<div class="indentImg"><i class="fa fa-caret-right"></i></div>');

					var $text = $('<span class="title">' + name + '</span>');
					a.append($text);

					li.append(a);
					li.data("mObj", p);
					ul.append(li);
				}else if (p.children && p.children.length) {
					var li = $('<li class="nav-item">');
					var a = $('<a href="javascript:;" class="nav-link nav-toggle"> <i class="' + icon + '"></i><span class="title">' + name + '</span><span class="arrow"></span></a>');
					li.append(a);
					li.data("mObj", p);
					ul.append(li);
					var sul = methods.createMenu2(p.children, opts);
					li.append(sul);
				} else {
					var li = $('<li class="nav-item"></li>');
					var a = $('<a href="#" class="nav-link not-toggle"></a>');

					a.append('<div class="selectDiv"></div>');
					a.append('<div class="splitDiv"></div>');

					var $text = $('<span class="title">' + name + '</span>');
					a.append($text);

					li.append(a);
					li.data("mObj", p);
					ul.append(li);
				}
			});
			return ul;
		},
		renderSelectColor: function($this) {

			var selectcolor = $this.attr("selectcolor");
			var selectColor = methods.getSelectColor(selectcolor);
			$this.find("ul.page-sidebar-menu-mt,ul.nav.navbar-nav").on('click', 'ul li a:not(".nav-toggle")', function() {
				if ($this.data("select")) {
					$this.data("select").removeClass(selectColor);
					$this.data("select").parents("li").find(">a").removeClass(selectColor);
				}
				$(this).addClass(selectColor);
				$(this).parents("li").find(">a").addClass(selectColor);
				$this.data("select", $(this));
			});
		},
		//只注册一次事件
		_bindGobalEvent : function($this){
			var fhovercolor = $this.attr("fhovercolor"); //悬浮背景颜色
			var shovercolor = $this.attr("shovercolor"); //悬浮子菜单颜色

			var bgcColor = $this.css("background-color");
			$this.on('click', 'ul.page-sidebar-menu-mt li > a.nav-toggle, li > a > span.nav-toggle', function(e) {
				var that = $(this).closest('.nav-item').children('.nav-link');

				if (!$('.page-sidebar-menu-mt').attr("data-initialized") && $('body').hasClass('page-sidebar-closed') && that.parent('li').parent('.page-sidebar-menu-mt').size() === 1) {
					return;
				}

				var hasSubMenu = that.next().hasClass('sub-menu');

				if (that.parents('.page-sidebar-menu-hover-submenu').size() === 1) { // exit of hover sidebar menu
					return;
				}

				if (hasSubMenu === false) {
					if ($('.page-sidebar-mt').hasClass("in")) { // close the menu on mobile view while laoding a page 
						$('.page-header .responsive-toggler').click();
					}
					return;
				}

				if (that.next().hasClass('sub-menu always-open')) {
					return;
				}

				$(this).closest('.nav-item').find("ul").css("background-color", fhovercolor);


				var parent = that.parent().parent();
				var the = that;
				var menu = $('.page-sidebar-menu-mt');
				var sub = that.next();

				var autoScroll = menu.data("auto-scroll");
				var slideSpeed = parseInt(menu.data("slide-speed"));
				var keepExpand = menu.data("keep-expanded");

				if (!keepExpand) {
					parent.children('li.open').children('a').children('.arrow').removeClass('open');
					parent.children('li.open').children('.sub-menu:not(.always-open)').slideUp(slideSpeed);
					parent.children('li.open').removeClass('open');

				}

				var slideOffeset = -200;

				if (sub.is(":visible")) {
					$('.arrow', the).removeClass("open");
					$('.arrow', the).removeClass("open");
					the.parent().removeClass("open");
					sub.slideUp(slideSpeed, function() {
						if (autoScroll === true && $('body').hasClass('page-sidebar-closed') === false) {
							if ($('body').hasClass('page-sidebar-fixed')) {
								menu.slimScroll({
									'scrollTo': (the.position()).top
								});
							} else {
								//                            App.scrollTo(the, slideOffeset);
							}
						}
						//                    handleSidebarAndContentHeight();
					});
				} else if (hasSubMenu) {

					$('.arrow', the).addClass("open");
					the.parent().addClass("open");
					sub.slideDown(slideSpeed, function() {
						if (autoScroll === true && $('body').hasClass('page-sidebar-closed') === false) {
							if ($('body').hasClass('page-sidebar-fixed')) {
								menu.slimScroll({
									'scrollTo': (the.position()).top
								});
							} else {
								//                            App.scrollTo(the, slideOffeset);
							}
						}
						//                    handleSidebarAndContentHeight();
					});
				}
				e.preventDefault();
			});
		},
		_renderClickEvent: function($this) {

			var fHover, sHover;
			fHover = $this.find("ul.page-sidebar-menu-mt>li");
			sHover = $this.find("ul.page-sidebar-menu-mt ul.sub-menu > li");
			var fhovercolor = $this.attr("fhovercolor"); //悬浮背景颜色
			var shovercolor = $this.attr("shovercolor"); //悬浮子菜单颜色

			var bgcColor = $this.css("background-color");

			fHover.on("mouseover", function() { //设置主菜单背景色
				var el = $(this).find(">a");
				if (fhovercolor) {
					el.css("background-color", bgcColor);
				}
			});
			sHover.on("mouseover", function() { //设置子菜单背景色
				var el = $(this).find(">a");
				if (fhovercolor) {
					el.css("background-color", fhovercolor);
				}
			});
			sHover.on("mouseover", function() { //设置子菜单悬浮背景色
				var el = $(this).find(">a");
				if (fhovercolor) {
					el.css("background-color", shovercolor);
				}
			});
			sHover.on("mouseleave", function() {
				var el = $(this).find(">a");
				el.css("background-color", fhovercolor);
			});
		},
		_renderHoverEvent: function($this) { //处理悬浮色彩

			var fHover, sHover;
			var fhovercolor = $this.attr("fhovercolor");
			var shovercolor = $this.attr("shovercolor");

			var fontSize = $this.attr("fontSize"); //字体大小
			var bigFontSize = $this.attr("bigFontSize"); //大图标字体大小
			var iconSize = $this.attr("iconSize"); //图标大小
			var bigIconSize = $this.attr("bigIconSize"); //大图标图标大小
			var fhovercolor_font = $this.attr("fhovercolorfont"); //悬浮背景字体颜色
			var shovercolor_font = $this.attr("shovercolorfont"); //悬浮子菜单字体颜色
			if ($this.hasClass("page-header-mt")) {
				fHover = $this.find("div.hor-menu ul.navbar-nav>li");
				sHover = $this.find("div.hor-menu ul.navbar-nav>li ul.dropdown-menu li");
			} else if ($this.hasClass("page-sidebar-mt")) {
				fHover = $this.find("ul.page-sidebar-menu-mt>li");
				sHover = $this.find("ul.page-sidebar-menu-mt ul.sub-menu > li");
			}

			var $bigIcon = "";
			if ($this.hasClass("upbig")) {
				$bigIcon = $this.find('.page-sidebar-menu-mt> .nav-item:first>a>.megamenu_icon,.page-sidebar-menu-mt> .nav-item:first>a>.megamenu_icon i');
			}
			if ($this.hasClass("downBig")) {
				var $downBig = $this.find('.page-sidebar-menu-mt> .nav-item:last>a>.megamenu_icon,.page-sidebar-menu-mt> .nav-item:last>a>.megamenu_icon i');
				if ($bigIcon && $bigIcon.length > 0) {
					$.merge($bigIcon, $downBig)
				} else {
					$bigIcon = $downBig;
				}
			}
			var $bigFont = "";
			if ($this.hasClass("upbig")) {
				$bigFont = $this.find('.page-sidebar-menu-mt>.nav-item:first>a>span,.page-sidebar-menu-mt> .nav-item:first>a>span');
			}
			if ($this.hasClass("downBig")) {
				var $downBig = $this.find('.page-sidebar-menu-mt> .nav-item:last>a>span,.page-sidebar-menu-mt> .nav-item:last>a>span');
				if ($bigFont && $bigFont.length > 0) {
					$bigFont.push($downBig);
				} else {
					$bigFont = $downBig;
				}
			}

			// var $bigIcon = $this.find('.page-sidebar-menu-mt.upbig> .nav-item:first>a>.megamenu_icon,'+
			// 	'.page-sidebar-menu-mt.upbig> .nav-item:first>a>.megamenu_icon i,'+
			// 	'.page-sidebar-menu-mt.downBig> .nav-item:last>a>.megamenu_icon,'+
			// 	'.page-sidebar-menu-mt.downBig> .nav-item:last>a>.megamenu_icon i');
			var $icon = $this.find('.page-sidebar-menu-mt> .nav-item>a>.megamenu_icon,' +
				'.page-sidebar-menu-mt> .nav-item>a>.megamenu_icon i');
			// var $bigFont = $this.find('.page-sidebar-menu-mt.upbig> .nav-item:first>a>span,'+
			// 	'.page-sidebar-menu-mt.upbig> .nav-item:first>a>span,'+
			// 	'.page-sidebar-menu-mt.downBig> .nav-item:last>a>span,'+
			// 	'.page-sidebar-menu-mt.downBig> .nav-item:last>a>span');
			$this.find("a>span").css("font-size", fontSize);
			$icon.css({
				"height": iconSize,
				"width": iconSize,
				"font-size": iconSize,
				"line-height": iconSize
			});
			if ($bigIcon && $bigIcon.length > 0) {
				$bigIcon.css({
					"height": bigIconSize,
					"width": bigIconSize,
					"font-size": bigIconSize,
					"line-height": bigIconSize
				});
			}
			if ($bigFont && $bigFont.length > 0) {
				$bigFont.css("font-size", bigFontSize);
			}

			fHover.on("mouseover", function(e) { //设置主菜单悬浮背景色
				var el = $(this).find(">a");
				if (fhovercolor) {
					el.css("background-color", fhovercolor);
					$(this).find("ul").css("background-color", fhovercolor);
				}
				if (fhovercolor_font) {
					el.find(".title,i").css("color", fhovercolor_font);
				}
			});
			fHover.on("mouseleave", function(e) {
				var el = $(this).find(">a");
				el.css("background-color", "");
				$(this).find("ul").css("background-color", "");
				el.find(".title,i").css("color", "");
			});
			sHover.on("mouseover", function(e) { //设置子菜单悬浮背景色
				var el = $(this).find(">a");
				if (shovercolor) {
					el.css("background-color", shovercolor);
				}
				if (shovercolor_font) {
					el.find(".title,i").css("color", shovercolor_font);
				}
			});
			sHover.on("mouseleave", function(e) {
				var el = $(this).find(">a");
				el.css("background-color", "");
				el.find(".title,i").css("color", "");
			});

		},
		display: function(bl) { //是否显示
			var $this = $(this);
			if (!bl) { //隐藏
				if ($this.hasClass("page-header-mt")) {
					$this.css("display", "none");
				} else if ($this.hasClass("page-sidebar-mt")) {
					$this.find("ul.page-sidebar-menu-mt").css("display", "none");
				}
			} else { //显示
				if ($this.hasClass("page-header-mt")) {
					$this.css("display", "");
				} else if ($this.hasClass("page-sidebar-mt")) {
					$this.find("ul.page-sidebar-menu-mt").css("display", "");
				}
			}
		},
		isEmptyObject: function(obj) { //空对象判断工具
			for (var name in obj) {
				return false;
			}
			return true;
		},
		getSelectColor: function(color) { //获取背景色
			if (selectColors[color]) {
				return selectColors[color];
			} else {
				return 'active-mt-success';
			}
		},
		tSelectFirst: function(obj){
			$(this).attr("tSelectFirst",true);
		}
	};



}(jQuery);