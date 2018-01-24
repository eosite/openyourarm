/**
 * charts
 */
var imageupload_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		imageupload_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	setPreimage : function(component,val){
		var prop = "backgroundImage";
		imageupload_designer.setDataRule(component,prop,val);
		if(val==''){
			component.attr("preImage",'');
		}else{
			component.attr("preImage","url("+contextPath+val+")");
		}
		
	},
	setBorder : function(component, val) {
		var $component = $(component);
		
		var prop = "hasBorder";
		imageupload_designer.setDataRule(component,prop,val);
		if(val=="no"){
			component.attr("borderWidth","");
		}else{
			component.attr("borderWidth",'0px');
		}
	},
	setRadiu:function(component,val){
		var prop="gfd";
		imageupload_designer.setDataRule(component,prop,val);
	    val=val+'% !important';
		component.css("cssText",'border-radius:'+val);
		component.attr('border-radius:',val);
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = imageupload_designer.getDataRule(component,prop)
		
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
	setWidth : function(component,val,unit){
		var prop = "width";
		if (val == '') {
			$(component[0]).css('width','');
			component.find("button").css("width", "");
		} else {
			val=val+unit;
			$(component[0]).css('width',val);
		}
	},
	setHeight : function(component,val,unit){
		var prop = "height";
		imageupload_designer.setDataRule(component,prop,val);
		if (val == '') {
			$(component[0]).css('height','');
			component.find("button").css("height", "");
		} else {
			val=val+unit;
			$(component[0]).css('height',val);
		}
	},
}
