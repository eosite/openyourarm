/**
 * login_register
 */
var login_register_designer = {

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
		login_register_designer.setDataRule(component,prop,val);
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
		login_register_designer.setDataRule(component,prop,val);
		login_register_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		login_register_designer.setDataRule(component,prop,val);
		login_register_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		login_register_designer.setDataRule(component,prop,val);
		login_register_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		login_register_designer.setDataRule(component,prop,val);
		login_register_designer.setMargin(component, "right", val);
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
		login_register_designer.setDataRule(component,prop,val);
		component.css("text-align", val);
	},
	setContent : function(component, val) {
		var prop = "content";
		login_register_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.find("a").html("");
		} else {
			component.find("a").html(val);
		}
	},setFontFamily : function(component, val) {
		var prop = "fontFamily";
		login_register_designer.setDataRule(component,prop,val);
		var obj = login_register_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-family", '');
		} else {
			obj.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor";
		login_register_designer.setDataRule(component,prop,val);
		var obj = login_register_designer.getBodyObject(component);
		if (val == '') {
			obj.css("color", '');
		} else {
			obj.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle";
		login_register_designer.setDataRule(component,prop,val);
		var obj = login_register_designer.getBodyObject(component);
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
		login_register_designer.setDataRule(component,prop,val);
		var obj = login_register_designer.getBodyObject(component);
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
		login_register_designer.setDataRule(component,prop,val);
		var obj = login_register_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-size", '');
		} else {
			obj.css("font-size", val + "px");
		}
	},
	getBodyObject : function(component){
		return component.find("a");
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = login_register_designer.getDataRule(component,prop)
		
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
