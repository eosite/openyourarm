/**
 * image
 */
var imageview_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		imageview_designer.setDataRule(component,prop,val);
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
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = imageview_designer.getBodyObject(component);
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
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		imageview_designer.setDataRule(component,prop,val);
		imageview_designer.setPositon(component, 'right', val);
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
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
	
		var prop = "position";
		imageview_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	/**
	 * 设置宽度
	 */
	setWidth:function (component,val,unit){ 
		var prop = "width";
		imageview_designer.setDataRule(component,prop,val,unit);
		imageview_designer.setSize(component,"width",val,unit);
	},
	/**
	 * 设置高度
	 */
	setHeight:function (component,val,unit){ 
		var prop = "height";
		imageview_designer.setDataRule(component,prop,val,unit);
		imageview_designer.setSize(component,"height",val,unit);
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
	
	setImage:function (component,val){
		
		var prop = "image";
		imageview_designer.setDataRule(component,prop,val); 
		component.attr("src",contextPath+val);
	
	},
	initImage:function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#backgroundImage1").val(value);
		}
		return $temp[0];
	},
	setImageRadius:function(component,val){
	
		var prop = "imageRadius"; 
		imageview_designer.setDataRule(component,prop,val);
		if(val!=""){
			imageview_designer.setStyle(component[0],"border-radius:"+val+"px!important");
		}else{
			imageview_designer.setStyle(component[0],"border-radius:0px!important");
		}
	},
	setBorderColor:function(component,val){
		var prop = "borderColor"; 
		imageview_designer.setDataRule(component,prop,val);
		component.css("border-color",val);
	},
	setBorderStyle:function(component,val){
		var prop = "borderStyle"; 
		imageview_designer.setDataRule(component,prop,val);
		component.css("border-style",val);
	},
	setBorderWidthTop:function(component,val){
		var prop = "borderWidthTop"; 
		imageview_designer.setDataRule(component,prop,val);
		if(val){
			val = val +"px";
		}
		component.css("border-top-width",val);
	},
	setBorderWidthBottom:function(component,val){
		var prop = "borderWidthBottom"; 
		imageview_designer.setDataRule(component,prop,val);
		if(val){
			val = val +"px";
		}
		component.css("border-bottom-width",val);
	},
	setBorderWidthLeft:function(component,val){
		var prop = "borderWidthLeft"; 
		imageview_designer.setDataRule(component,prop,val);
		if(val){
			val = val +"px";
		}
		component.css("border-left-width",val);
	},
	setBorderWidthRight:function(component,val){
		var prop = "borderWidthRight"; 
		imageview_designer.setDataRule(component,prop,val);
		if(val){
			val = val +"px";
		}
		component.css("border-right-width",val);
	},
	setStyle:function(el, strCss){
	    function endsWith(str, suffix) {
	        var l = str.length - suffix.length;
	        return l >= 0 && str.indexOf(suffix, l) == l;
	    }
	    var sty = el.style,
	        cssText = sty.cssText;
	    if(!endsWith(cssText, ';')&&cssText!=""){
	        cssText += ';';
	    }
   	 	sty.cssText = cssText + strCss;
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
		
		var lastVal = imageview_designer.getDataRule(component,prop)
		
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

