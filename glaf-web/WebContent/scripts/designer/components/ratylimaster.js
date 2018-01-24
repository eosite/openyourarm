/**
 * textboxbt
 */
var ratylimaster_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		ratylimaster_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},

	
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		
		var prop = "width";
		ratylimaster_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + "px");
		}

	},
	/**
	 * 设置高度
	 */
	setHeight : function(component, val) {
		var obj = ratylimaster_designer.getBodyObject(component);
		var prop = "height";
		ratylimaster_designer.setDataRule(component,prop,val);
		obj.removeClass("input-medium input-lg input-sm input-xs");
		if (val == '') {
			obj.css("height", '');
		} else {
			obj.css("height", val + "px");
		}
	},
	

	
	
	getBodyObject : function(component) {
		var mainBody;
		if (component.find("input").length) {
			mainBody = $(component.find("input"));
		} else {
			mainBody = $(component);
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = ratylimaster_designer.getDataRule(component,prop)
		
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
	setColor : function(component,val,unit){
		var prop = "color";
		ratylimaster_designer.setDataRule(component,prop,val);
		component.find("span.rate-empty").css("color","#" + val);
		component.find(".ratymode ").attr("empty-color", val);
	},
	setActiveColor : function(component,val,unit){
		var prop = "color";
		ratylimaster_designer.setDataRule(component,prop,val);
		component.find("span.rate-full").css("color","#" + val);
		component.find(".ratymode ").attr("full-color", val);
	},
	setIconClass : function(component,val,unit){
		var prop = "iconclass";
		ratylimaster_designer.setDataRule(component,prop,val);
		var rate = component.find("span.rate");
		$.each(rate,function(i,r){
			r.innerHTML = "<i class='"+val+"'></i>";
		});
		ratylimaster_designer.setRateRule(component,"icon",val);
	},
	setRateRule : function(component,state,val){
		var rule = component.find(".ratymode").attr("rule");
		if(rule != undefined){
			rule = eval("(" + rule + ")");
			rule[state] = val;
			component.find(".ratymode").attr("rule",JSON.stringify(rule));
		}
		else{
			rule = {};
			rule[state] = val;
			component.find(".ratymode").attr("rule",JSON.stringify(rule));
		}
	},
	setIconSize : function(component,val,unit){
		var prop = "iconSize";
		ratylimaster_designer.setDataRule(component,prop,val);
		component.find("span.rate i").css("font-size",val + "px");
		ratylimaster_designer.setRateRule(component,"iconsize",val+"px");
	},
	initIconClass2: function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#icon2").addClass(value);
			$temp.find("#iconName2").html(value);
		}
		return $temp[0];
	},initIconClass: function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#icon").addClass(value);
			$temp.find("#iconName").html(value);
		}
		return $temp[0];
	}
}

