/**
 * bimupload
 */
var bimupload_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		bimupload_designer.setDataRule(component,prop,val);
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
		bimupload_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + "px");
		}
	},

	/**
	 * 设置高度
	 */
	setSize : function(component, val) {
		var prop = "size";
		bimupload_designer.setDataRule(component,prop,val);
		component.removeClass("hmtd-xs");
		component.addClass(val);

	},
	setDataRule : function(component,prop,val){
		
		var lastVal = bimupload_designer.getDataRule(component,prop)
		
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
