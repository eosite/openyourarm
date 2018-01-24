/**
 * login_page
 */
var login_page_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var prop = "name";
		login_page_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.attr("cname", "");
		} else {
			component.attr("cname", val);
		}
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = login_page_designer.getDataRule(component,prop)
		
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
