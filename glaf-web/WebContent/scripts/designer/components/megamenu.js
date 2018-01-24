/**
 * megamenu
 */

var megamenu_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var prop = "name";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},

	setWidth : function(component,val,unit){
		var prop = "width";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.css("width","");
		} else {
			$component.css("width",val+unit);
		}
	},

	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var $component = $(component);
		if (val == '') {
			$component.css("margin-" + direct, "");
		} else {
			$component.css("margin-" + direct, val + "px");
		}
	},

	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		var $component = $(component);
		if (val == '') {
			$component.css(direct, '');
		} else {
			$component.css(direct, val + "px");
		}
	},
	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var prop = "position";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		$component.css("position", val);

	},
	/**
	 * 设置背景色
	 */
	setBackgroundColor : function(component, val) {
		var prop = "backgroundColor";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.css("background-color", '');
		} else {
			$component.css("background-color", val);
		}
	},

	/**
	 * 设置透明度   影响悬浮子菜单的层级
	 */
//	setTransparency : function(component, val) {
//		var prop = "transparency";
//		megamenu_designer.setDataRule(component,prop,val);
//		var $component = $(component);
//		if (val == '') {
//			$component.css("filter", "");
//			$component.css("-moz-opacity", "");
//			$component.css("-khtml-opacity", "");
//			$component.css("opacity", "");
//		} else {
//			$component.css("filter", "alpha(opacity=" + val + ")");
//			$component.css("-moz-opacity", val / 100);
//			$component.css("-khtml-opacity", val / 100);
//			$component.css("opacity", val / 100);
//		}
//	},

	/**
	 * 设置主菜单悬浮背景色
	 */
	setMainHoverColor : function(component, val) {
		var prop = "mhoverColor";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("fhovercolor", "#3f4f62");
		} else {
			$component.attr("fhovercolor", val);
		}
		var obj;
		if ($component.hasClass("page-header-mt")) {
			obj = $component.find("div.hor-menu ul.navbar-nav>li");
		} else if ($component.hasClass("page-sidebar-mt")) {
			obj = $component.find("ul.page-sidebar-menu-mt>li");
		}

		obj.hover(function() {
					var el = $(this).find(">a");
					if (val == '') {
						el.css("background-color", "");
						$(this).find("ul").css("background-color", "");
					} else {
						el.css("background-color", val);
						$(this).find("ul").css("background-color", val);
					}
				}, function() {
					var el = $(this).find(">a");
					el.css("background-color", "");
					$(this).find("ul").css("background-color", "");
				});

	},

	/**
	 * 设置主菜单悬浮字体色
	 */
	setMainHoverColor_Font : function(component, val) {
		var prop = "mhoverColorFont";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("fhovercolorfont", "#b4bcc8");
		} else {
			$component.attr("fhovercolorfont", val);
		}
		var obj;
		if ($component.hasClass("page-header-mt")) {
			obj = $component.find("div.hor-menu ul.navbar-nav>li");
		} else if ($component.hasClass("page-sidebar-mt")) {
			obj = $component.find("ul.page-sidebar-menu-mt>li");
		}

		obj.hover(function() {
					var el = $(this).find(">a");
					if (val == '') {
						el.find(".title,i").css("color", "");
					} else {
						el.find(".title,i").css("color", val);
					}
				}, function() {
					var el = $(this).find(">a");
					el.find(".title,i").css("color", "");
				});

	},

	setHoverColor_Font : function(component, val) {
		var prop = "hoverColorFont";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("shovercolorfont", "#b4bcc8");
		} else {
			$component.attr("shovercolorfont", val);
		}
		var obj;
		if ($component.hasClass("page-header-mt")) {
			obj = $component
					.find("div.hor-menu ul.navbar-nav>li ul.dropdown-menu li");
		} else if ($component.hasClass("page-sidebar-mt")) {
			obj = $component.find("ul.page-sidebar-menu-mt ul.sub-menu > li");
		}
		obj.hover(function() {
					var el = $(this).find(">a");
					if (val == '') {
						el.find(".title,i").css("color", "");
					} else {
						el.find(".title,i").css("color", val);
					}
				}, function() {
					var el = $(this).find(">a");
					el.find(".title,i").css("color", "");
				});
	},

	/**
	 * 设置子菜单悬浮背景色
	 */
	setHoverColor : function(component, val) {
		var prop = "hoverColor";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("shovercolor", "#47596e");
		} else {
			$component.attr("shovercolor", val);
		}
		var obj;
		if ($component.hasClass("page-header-mt")) {
			obj = $component
					.find("div.hor-menu ul.navbar-nav>li ul.dropdown-menu li");
		} else if ($component.hasClass("page-sidebar-mt")) {
			obj = $component.find("ul.page-sidebar-menu-mt ul.sub-menu > li");
		}
		obj.hover(function() {
					var el = $(this).find(">a");
					if (val == '') {
						el.css("background-color", "");
					} else {
						el.css("background-color", val);
					}
				}, function() {
					var el = $(this).find(">a");
					el.css("background-color", "");
				});
	},
	/**
	 * 设置选中菜单背景色
	 */
	setSelectColor : function(component, val) {
		var prop = "selectColor";
		megamenu_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("selectcolor", "#36c6d3");
		} else {
			$component.attr("selectcolor", val);
		}
	},
	/**
	 * 设置字体种类
	 */
	setFontType : function(component, val) {
		var prop = "fontType";
		megamenu_designer.setDataRule(component,prop,val);

	},
	/**
	 * 设置字体
	 */
	setFontFamily : function(component, val) {
		var $component = $(component);
		var prop = "fontFamily";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			if (val == '') {
				$component.find("h3").css("font-family", '');
			} else {
				$component.find("h3").css("font-family", val);
			}
		} else {
			if (val == '') {
				$component.find("a").css("font-family", '');
			} else {
				$component.find("a").css("font-family", val);
			}
		}

	},
	setFontColor : function(component, val) {
		var $component = $(component);
		var prop = "fontColor";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			if (val == '') {
				$component.find("h3").css("color", '');
			} else {
				$component.find("h3").css("color", val);
			}
		} else {
			if (val == '') {
				$component.find("a").css("color", '');
			} else {
				$component.find("a").css("color", val);
			}
		}
	},
	setFontColor_Icon : function(component, val) {
		var $component = $(component);
		var prop = "fontColorIcon";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			if (val == '') {
				$component.find("h3").css("color", '');
			} else {
				$component.find("h3").css("color", val);
			}
		} else {
			if (val == '') {
				$component.find("a i").css("color", '');
			} else {
				$component.find("a i").css("color", val);
			}
		}
	},
	setFontStyle : function(component, val) {

		var $component = $(component);
		var prop = "fontStyle";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			if (val == 'italic') {
				if ($(this).hasClass('active')) {
					$component.find("h3").css("font-style", 'italic');
				} else {
					$component.find("h3").css("font-style", '');
				}
			} else if (val == 'bold') {
				if ($(this).hasClass('active')) {
					$component.find("h3").css("font-weight", 'bold');
				} else {
					$component.find("h3").css("font-weight", '');
				}
			}
		} else {
			if (val == 'italic') {
				if ($(this).hasClass('active')) {
					$component.find("a").css("font-style", 'italic');
				} else {
					$component.find("a").css("font-style", '');
				}
			} else if (val == 'bold') {
				if ($(this).hasClass('active')) {
					$component.find("a").css("font-weight", 'bold');
				} else {
					$component.find("a").css("font-weight", '');
				}
			}
		}
	},
	/**
	 * 设置字体间距
	 */
	setLetterSpacing : function(component, val) {
		var $component = $(component);
		var prop = "letterSpacing";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			if (val == '') {
				$component.find("h3").css("letter-spacing", '');
			} else {
				$component.find("h3").css("letter-spacing", val + "px");
			}
		} else {
			if (val == '') {
				$component.find("a").css("letter-spacing", '');
			} else {
				$component.find("a").css("letter-spacing", val + "px");
			}
		}

	},
	/**
	 * 设置字体大小
	 */
	setFontSize : function(component, val) {

		var $component = $(component);
		var prop = "fontSize";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			if (val == '') {
				$component.find("h3").css("font-size", '');
			} else {
				$component.find("h3").css("font-size", val + "px");
			}
		} else {
			if (val == '') {
				component.removeAttr("fontSize");
				$component.find("a>span").css("font-size", '');
			} else {
				component.attr("fontSize",val + "px");
				$component.find("a>span").css("font-size", val + "px");
			}
		}

	},
	//大图标字体大小
	setBigFontSize : function(component, val) {
		var $component = $(component);
		var prop = "bigFontSize";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");

		var $font = "";
		if($component.hasClass("upbig")){
			$font = $component.find('.page-sidebar-menu-mt>.nav-item:first>a>span,.page-sidebar-menu-mt> .nav-item:first>a>span');
		}
		if($component.hasClass("downBig")){
			var $downBig = $component.find('.page-sidebar-menu-mt> .nav-item:last>a>span,.page-sidebar-menu-mt> .nav-item:last>a>span');
			if($font && $font.length>0){
				$font.push($downBig);
			}else{
				$font = $downBig;
			}
		}
		if(!$font || $font.length == 0){
			return;
		}

		if (key == "title") {
			if (val == '') {
				$component.find("h3").css("font-size", '');
			} else {
				$component.find("h3").css("font-size", val + "px");
			}
		} else {
			if (val == '') {
				component.removeAttr("bigFontSize");
				$font.css("font-size", '');
			} else {
				component.attr("bigFontSize",val + "px");
				$font.css("font-size", val + "px");
			}
		}
	},
	setIconSize : function(component, val) {
		var $component = $(component);
		var prop = "iconSize";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");

		var $icon = $component.find('.page-sidebar-menu-mt> .nav-item>a>.megamenu_icon,'+
				'.page-sidebar-menu-mt> .nav-item>a>.megamenu_icon i');
		if (val == '') {
			component.removeAttr("iconSize");
			$icon.css("height", "");
			$icon.css("width", "");
			$icon.css("font-size", "");
			$icon.css("line-height", "");
		} else {
			component.attr("iconSize",val + "px");
			$icon.css("height", val + "px");
			$icon.css("width", val + "px");
			$icon.css("font-size", val + "px");
			$icon.css("line-height", val + "px");
		}
	},
	setBigIconSize : function(component, val) {
		var $component = $(component);
		var prop = "bigIconSize";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");

		var $icon = "";
		if($component.hasClass("upbig")){
			$icon = $component.find('.page-sidebar-menu-mt> .nav-item:first>a>.megamenu_icon,.page-sidebar-menu-mt> .nav-item:first>a>.megamenu_icon i');
		}
		if($component.hasClass("downBig")){
			var $downBig = $component.find('.page-sidebar-menu-mt> .nav-item:last>a>.megamenu_icon,.page-sidebar-menu-mt> .nav-item:last>a>.megamenu_icon i');
			if($icon && $icon.length>0){
				$.merge($icon,$downBig)
			}else{
				$icon = $downBig;
			}
		}
		if(!$icon || $icon.length == 0){
			return;
		}
		
		if (val == '') {
			component.removeAttr("bigIconSize");
			$icon.css("height", "");
			$icon.css("width", "");
			$icon.css("font-size", "");
			$icon.css("line-height", "");
		} else {
			component.attr("bigIconSize",val + "px");
			$icon.css("height", val + "px");
			$icon.css("width", val + "px");
			$icon.css("font-size", val + "px");
			$icon.css("line-height", val + "px");
		}
	},
	/**
	 * 设置文本缩进
	 */
	setTextIndent : function(component, val) {

		var $component = $(component);
		var prop = "textIndent";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			if (val == '') {
				$component.find("h3").css("text-indent", '');
			} else {
				$component.find("h3").css("text-indent", val + "px");
			}
		} else {
			if (val == '') {
				$component.find("a>span").css("text-indent", '');
			} else {
				$component.find("a>span").css("text-indent", val + "px");
			}
		}

	},

	/**
	 * 文本水平对齐
	 */
	setTextAlign : function(component, val) {

		var $component = $(component);
		var prop = "textAlign";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			$component.find("h3").css("text-align", val);
		} else {
			$component.find("a").css("text-align", val);
			$component.removeClass("left_icon");
			$component.removeClass("right_icon");
			if(val == 'right'){
				$component.addClass("right_icon");
			}else if(val == 'left'){
				$component.addClass("left_icon");
			}
		}

	},
	/**
	 * 文本垂直对齐
	 */
	setVerticalAlign : function(component, val) {
		var $component = $(component);
		var prop = "verticalAlign";
		megamenu_designer.setDataRule(component,prop,val);
		var key = megamenu_designer.getDataRule(component,"fontType");
		if (key == "title") {
			$component.find("h3").css("vertical-align", val);
		} else {
			$component.find("a").css("vertical-align", val);
		}
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = megamenu_designer.getDataRule(component,prop)
		
		if(component.attr("data-rule")){
			var data_rule = JSON.parse(component.attr("data-rule"));
			data_rule[prop]=val;
		}else{
			var data_rule={};
			data_rule[prop]=val;
		}
		component.attr("data-rule",JSON.stringify(data_rule));
		
		return lastVal;
		
	},
	getDataRule : function(component,prop){
		
		//debugger;
		var value ="";
		if(component.attr("data-rule")){
			value = JSON.parse(component.attr("data-rule"))[prop];
		}
		
		return value;
	},

	//分割线设置
	setLine : function(component,val){
		var prop = "line";
		if(val && val == 'yes'){
			component.find(".page-header-inner").addClass("lineStyle");
		}else{
			component.find(".page-header-inner").removeClass("lineStyle");
		}
		megamenu_designer.setDataRule(component,prop,val);
	},
	setLineColor : function(component,val){
		var prop = "lineColor";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.updateStyleByKey(component,"lineColor",val);
	},
	setLineWidth : function(component,val){
		var prop = "lineWidth";
		if(val){
			val = val+"px";
		}
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.updateStyleByKey(component,"lineWidth",val);
	},
	setLineHeight : function(component,val){
		var prop = "lineHeight";
		if(val){
			val = val+"px";
		}
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.updateStyleByKey(component,"lineHeight",val);
	},
	setLineTop : function(component,val){
		var prop = "lineTop";
		if(val){
			val = val+"px";
		}
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.updateStyleByKey(component,"lineTop",val);
	},

	setSelectColor_f : function(component,val){
		var prop = "selectColor_f";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.updateStyleByKey(component,"selectColor_f",val);
	},
	setSelectColor_b : function(component,val){
		var prop = "selectColor_b";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.updateStyleByKey(component,"selectColor_b",val);
	},


	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		megamenu_designer.setDataRule(component,prop,val);
		megamenu_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		megamenu_designer.updateStyleByKey(component,"padding-" + direct,val + "px");
	},

	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = megamenu_designer.getStyler(component);
		styler[key] = val;
		megamenu_designer.updateStyler(component,styler);
		megamenu_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = megamenu_designer.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return $.parseJSON(styler.val());
		}
	},
	getStylerId: function(component){
		return component.attr('id') +'_styler_hidden';
	},
	updateStyler: function (component,styler){
		var sid = megamenu_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";
		if(styler["padding-top"] || styler["padding-left"] || styler["padding-right"] || styler["padding-bottom"]){
			component.css("min-height","0px");
			component.css("height","auto");
		}else{
			component.css("min-height","");
			component.css("height","");
		}
		var _default_title = _id + ".navbar .hor-menu .navbar-nav > li > a{";
		if(styler["padding-top"]){
			_default_title += 'padding-top:'+styler["padding-top"]+";";
		}
		if(styler["padding-left"]){
			_default_title += 'padding-left:'+styler["padding-left"]+";";	
		}
		if(styler["padding-right"]){
			_default_title += 'padding-right:'+styler["padding-right"]+";";
		}
		if(styler["padding-bottom"]){
			_default_title += 'padding-bottom:'+styler["padding-bottom"]+";";
		}
		_default_title += "}";

		var _default_line = _id + " .lineStyle .lineDiv{";
		if(styler["lineTop"]){
			_default_line += 'margin-top:'+styler["lineTop"]+";";
		}
		if(styler["lineHeight"]){
			_default_line += 'height:'+styler["lineHeight"]+";";
		}
		if(styler["lineWidth"]){
			_default_line += 'width:'+styler["lineWidth"]+";";
		}
		if(styler["lineColor"]){
			_default_line += 'background-color:'+styler["lineColor"]+";";
		}
		_default_line += "}";

		var _default_select_b = _id + ' .active-mt-success.not-toggle{';
		if(styler["selectColor_b"]){
			_default_select_b += 'background-color:'+styler["selectColor_b"]+" !important;";
		}
		_default_select_b += "}";
		var _default_select_f = _id + ' .active-mt-success span,'+_id+' .active-mt-success{';
		if(styler["selectColor_f"]){
			_default_select_f += 'color:'+styler["selectColor_f"]+" !important;";
		}
		_default_select_f += "}";

		_default += _default_title +_default_line+_default_select_f+_default_select_b;

		var stylebox = megamenu_designer.getStyleBox(component);
		stylebox.html(_default);
	},
	/**
	 * 获取<style id> 对象
	 */
	getStyleBox: function (component){
		var sid = component.attr('id') + '_styler_init';
		var box = component.find('#'+sid);
		if(!box.length){
			component.append("<style id=\""+sid+"\"></style>");
		}
		return component.find('#'+sid);
	},

}
