/**
 * custom
 */
var custom_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		custom_designer.setDataRule(component,prop,val);
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
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		
		if (val == '') {
			component.css("margin-" + direct, "");
		} else {
			component.css("margin-" + direct, val + "px");
		}
	},
	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		custom_designer.setDataRule(component,prop,val);
		custom_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
			//debugger;
			component.css("position", "relative");
			if (val == '') {
				component.css(direct, '');
			} else {
				component.css(direct, val + "px");
			}
		
	},
	/**
	*设置浮动
	*/
	 setFloatStyle:function(component,val){
		var prop = "float";
		custom_designer.setDataRule(component,prop,val);
		component.css("float",val);
	},
	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
	
		var prop = "position";
		custom_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	
	setDataRule : function(component,prop,val){
		
		var lastVal = custom_designer.getDataRule(component,prop)
		
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
	/*
	* 设置控件堆叠顺序
	*/
	setZindex : function(component,val){
		var prop = "zindex";
		col_designer.setDataRule(component,prop,val);
		component.css("z-index",val);
	},
}