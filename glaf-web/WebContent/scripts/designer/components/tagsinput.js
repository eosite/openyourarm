/**
 * tagsinput
 */
var tagsinput_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		tagsinput_designer.setDataRule(component,prop,val);
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
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = component;
		if (val == '') {
			obj.css("margin-" + direct, "");
		} else {
			obj.css("margin-" + direct, val + "px");
		}
	},
	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		tagsinput_designer.setDataRule(component,prop,val);
		tagsinput_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		component.css("position", "relative");
		if (val == '') {
			component.css(direct, '');
		} else {
			component.css(direct, val + "px");
		}
		
	},

	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var prop = "position";
		tagsinput_designer.setDataRule(component,prop,val);
		
		component.css("position", val);	
	},
	
	/**
	 * 设置宽度
	 */
	setWidth : function(component,val,unit) {	
		var prop = "width";
		tagsinput_designer.setDataRule(component,prop,val,unit); 
		tagsinput_designer.setSize(component,"width",val,unit);
	},
	setSize:function(component,type,val,unit){
		var obj=tagsinput_designer.getBodyObject(component);
		if(val==''){
		  obj.css(type,'');	
		}else{
			if(unit==undefined){
				unit="px";
			}else{
				obj.css(type,val+unit);
			}
		}
	},
	getBodyObject : function(component) {
		var mainBody;
		if (component.find("div.bootstrap-tagsinput").length) {
			mainBody = component.find("div.bootstrap-tagsinput");
		} else {
			mainBody = $(component);
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = tagsinput_designer.getDataRule(component,prop)
		
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

