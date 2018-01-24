/**
 * login_verify
 */
var login_verify_designer = {

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
		login_verify_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	setInfo : function(component, val) {
		var prop = "info";
		login_verify_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.find("input").attr("placeholder","");
		} else {
			component.find("input").attr("placeholder",val);
		}
	},
	setWidth : function(component, val) {
		var prop = "width";
		login_verify_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.find("input").css("width","");
		} else {
			component.find("input").css("width",val);
		}
	},
	setTextAlign : function(component,val){
		var prop = "textAlign";
		login_verify_designer.setDataRule(component,prop,val);
		component.find("img").closest("div").css("text-align", val);
	},
	setTPaddingTop : function(component, val) {
		var prop = "tPaddingTop";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setTPadding(component, "top", val);
	},
	setTPaddingBottom : function(component, val) {
		var prop = "tPaddingBottom";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setTPadding(component, "bottom", val);
	},
	setTPaddingLeft : function(component, val) {
		var prop = "tPaddingLeft";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setTPadding(component, "left", val);
	},
	setTPaddingRight : function(component, val) {
		var prop = "tPaddingRight";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setTPadding(component, "right", val);
	},
	setTPadding : function(component, direct, val) {
		var obj = component.find("input").closest("div");
		if (val == '') {
			obj.css("padding-" + direct, "");
		} else {
			obj.css("padding-" + direct, val + "px");
		}
	},
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		login_verify_designer.setDataRule(component,prop,val);
		login_verify_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = component.find("img").closest("div");
		if (val == '') {
			obj.css("padding-" + direct, "");
		} else {
			obj.css("padding-" + direct, val + "px");
		}
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = login_verify_designer.getDataRule(component,prop)
		
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
