/**
 * login_actions
 */
var login_actions_designer = {

	/**
	 * 移除区域
	 */
	setRemove : function(component, val) {
		if (val == 'yes') {
			component.remove();
		}
	},
	setName : function(component,val){
		var prop = "name";
		login_actions_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		if (val == '') {
			component.css("margin-" + direct, "");
		} else {
			component.css("margin-" + direct, val + "px");
		}
	},
	setTextAlign : function(component,val){
		var prop = "textAlign";
		login_actions_designer.setDataRule(component,prop,val);
		component.css("text-align", val);
	},
	setWidth : function(component,val){
		var prop = "width";
		login_actions_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.find("button").css("width", "");
		} else {
			component.find("button").css("width",val);
		}
	},
	setHeight : function(component,val){
		var prop = "height";
		login_actions_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.find("button").css("height", "");
		} else {
			component.find("button").css("height",val);
		}
	},
	setAutoLine : function(component,val){
		var prop = "autoLine";
		login_actions_designer.setDataRule(component,prop,val);
		if (val == 'yes') {
			component.find("button").addClass("btn-block");
		} else {
			component.find("button").removeClass("btn-block");
		}
	},
	setBtnColor : function(component,val){
		var prop = "btnColor";
		var color = login_actions_designer.setDataRule(component,prop,val);
		
		if (color == '') {
			component.find("button").removeClass("green red blue");
			component.find("button").addClass(val);
		} else {
			component.find("button").removeClass(color);
			component.find("button").addClass(val);
		}
		
	},
	/**
	 * 设置透明度
	 */
	setTransparency : function(component, val) {
		var prop = "transparency";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = component.find("button");
		if (val == '') {
			obj.css("filter", "");
			obj.css("-moz-opacity", "");
			obj.css("-khtml-opacity", "");
			obj.css("opacity", "");
		} else {
			obj.css("filter", "alpha(opacity=" + val + ")");
			obj.css("-moz-opacity", val / 100);
			obj.css("-khtml-opacity", val / 100);
			obj.css("opacity", val / 100);
		}
	},
	
	/**
	 * 设置边框宽度
	 */
	setBorderTopWidth : function(component, val) {
		var prop = "borderTopWidth";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth";
		login_actions_designer.setDataRule(component,prop,val);
		login_actions_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var obj = component.find("button");
		
		if (val == '') {
			obj.css("border-" + direct + "-width", '');
		} else {
			obj.css("border-" + direct + "-width", val + "px");
		}

	},

	/**
	 * 设置边框颜色
	 */
	setBorderColor : function(component, val) {
		var prop = "borderColor";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = component.find("button");
	
		if (val == '') {
			obj.css("border-color", '');
		} else {
			obj.css("border-color", val);
		}
		
	},

	/**
	 * 设置边框样式
	 */
	setBorderStyle : function(component, val) {
		var prop = "borderStyle";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = component.find("button");
		if (val == '') {
			obj.css("border-style", '');
		} else {
			obj.css("border-style", val);
		}
		

	},
	/**
	 * 设置圆边
	 */
	setCircleStyle : function(component, val) {
		var prop = "circleStyle";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = component.find("button");
		if (val == 'display') {
			obj.addClass('input-circle');
		} else {
			obj.removeClass('input-circle');
		}
	},

	setBtnContent : function(component, val) {
		var prop = "btnContent";
		login_actions_designer.setDataRule(component,prop,val);
		
		if (val == '') {
			component.find("button").html("");
		} else {
			component.find("button").html(val);
		}	
		
	},
	setFontFamily : function(component, val) {
		var prop = "fontFamily";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = login_actions_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-family", '');
		} else {
			obj.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = login_actions_designer.getBodyObject(component);
		if (val == '') {
			obj.css("color", '');
		} else {
			obj.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = login_actions_designer.getBodyObject(component);
		if (val == 'italic') {
			if ($(this).hasClass('active')) {
				obj.css("font-style", 'italic');
			} else {
				obj.css("font-style", '');
			}
		} else if (val == 'bold') {
			if ($(this).hasClass('active')) {
				obj.css("font-weight", 'bold');
			} else {
				obj.css("font-weight", '');
			}
		}
	},
	/**
	 * 设置字体间距
	 */
	setLetterSpacing : function(component, val) {
		var prop = "letterSpacing";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = login_actions_designer.getBodyObject(component);
		if (val == '') {
			obj.css("letter-spacing", '');
		} else {
			obj.css("letter-spacing", val + "px");
		}
	},
	/**
	 * 设置字体大小
	 */
	setFontSize : function(component, val) {
		var prop = "fontSize";
		login_actions_designer.setDataRule(component,prop,val);
		var obj = login_actions_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-size", '');
		} else {
			obj.css("font-size", val + "px");
		}
	},

	getBodyObject : function(component){
		return component.find("button");
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = login_actions_designer.getDataRule(component,prop)
		
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
	}
	
	
}
