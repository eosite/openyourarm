/**
 * textboxbt
 */
var videoplay_designer = {
	setDataRule : function(component,prop,val){
		
		var lastVal = videoplay_designer.getDataRule(component,prop)
		
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
	
	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		videoplay_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	
	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = videoplay_designer.getBodyObject(component);
		
		if (val == '') {
			obj.css("padding-" + direct, "");
		} else {
			obj.css("padding-" + direct, val + "px");
		}
		
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = videoplay_designer.getBodyObject(component);
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
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		videoplay_designer.setDataRule(component,prop,val);
		videoplay_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		
		var obj = videoplay_designer.getBodyObject(component);
		
		obj.css("position","relative");
		if (val == '') {
			obj.css(direct, '');
		} else {
			obj.css(direct, val + "px");
		}
		
	},

	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var prop = "positon";
		videoplay_designer.setDataRule(component,prop,val);
		var obj = videoplay_designer.getBodyObject(component);
		component.css("position", val);
		
	},
	/**
	*����ˮƽ����
	*/
	setTextAlign:function (component,val){
		
		var prop = "textAlign";
		videoplay_designer.setDataRule(component,prop,val);
		var obj=videoplay_designer.getBodyObject(component);
		component.css("display","block");
		component.css("text-decoration","blink");
		if(val =="flex-start"){
			obj.css("text-align","left");
			
			component.css("text-align","left");
		}else if(val=="center"){
			obj.css("text-align","center");
			component.find("i").length&&component.find("i").css("margin","0 auto");
			
			component.find("img").length&&component.find("img").css("display","inline");
			component.css("text-align","center");
			
		}else if(val=="flex-end"){
			obj.css("text-align","right");
			component.find("i").length&&component.find("i").css("margin","0 0 0 auto");
			
			component.find("img").length&&component.find("img").css("display","inline");
			component.css("text-align","right");
		}
//		obj.css("display","flex");
//		obj.css("justify-content",val);
	},
	
	/**
	*���óߴ�
	*/
	setWidth:function (component,val,unit){ 
		var prop = "width";
		videoplay_designer.setDataRule(component,prop,val,unit);
		
		videoplay_designer.setSize(component,"width",val,unit);
	},
	setHeight:function (component,val,unit){ 
		var prop = "height";
		videoplay_designer.setDataRule(component,prop,val,unit);
		if(component.find("img").length){
			videoplay_designer.setSize(component.find("img"),"height",val,unit);
		}else{
			videoplay_designer.setSize(component,"height",val,unit);
		}
	},
	setSize:function (obj,type,val,unit){
		if(val==''){
		  obj.css(type,'');
		}else{
			if(unit==undefined){
				unit="px";
			}
		  	obj.css(type,val+unit);
		}
	},
	
	
	
	
}