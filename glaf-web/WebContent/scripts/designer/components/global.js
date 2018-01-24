

var global_designer = {
	
getMainObject:function(component){
	return component;
},
 getBodyObject:function(component){
	return component;
},
/**
*设置内间距
*/
 setRowPaddingTop:function(component,val){
	var prop = "rowPaddingTop";
	global_designer.setDataRule(component,prop,val);
},
 setRowPaddingBottom:function(component,val){
	var prop = "rowPaddingBottom";
	global_designer.setDataRule(component,prop,val);
},
 setRowPaddingLeft:function(component,val){
	var prop = "rowPaddingLeft";
	global_designer.setDataRule(component,prop,val);
},
 setRowPaddingRight:function(component,val){
	var prop = "rowPaddingRight";
	global_designer.setDataRule(component,prop,val);
},
 setRowPadding:function(component,direct,val){
	
	var portletBody=global_designer.getBodyObject(component);
	if(val==''){
		portletBody.css("padding-"+direct,"");
	}else{
		portletBody.css("padding-"+direct,val+"px");
	}
},
/**
*设置外间距
*/
 setRowMarginTop:function(component,val){
	var prop = "rowMarginTop";
	global_designer.setDataRule(component,prop,val);
},
 setRowMarginBottom:function(component,val){
	var prop = "rowMarginBottom";
	global_designer.setDataRule(component,prop,val);
},
 setRowMarginLeft:function(component,val){
	var prop = "rowMarginLeft";
	global_designer.setDataRule(component,prop,val);
},
 setRowMarginRight:function(component,val){
	var prop = "rowMarginRight";
	global_designer.setDataRule(component,prop,val);
},
 setRowMargin:function(component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=global_designer.getMainObject(component);
	if(val==''){
			obj.css("margin-"+direct,"");
	}else{
	        obj.css("margin-"+direct,val+"px");
	}
},
/**
*设置列内间距
*/
 setColPaddingTop:function(component,val){
	var prop = "colPaddingTop";
	global_designer.setDataRule(component,prop,val);
},
 setColPaddingBottom:function(component,val){
	var prop = "colPaddingBottom";
	global_designer.setDataRule(component,prop,val);
},
 setColPaddingLeft:function(component,val){
	var prop = "colPaddingLeft";
	global_designer.setDataRule(component,prop,val);
},
 setColPaddingRight:function(component,val){
	var prop = "colPaddingRight";
	global_designer.setDataRule(component,prop,val);
},
 setColPadding:function(component,direct,val){
	
	var portletBody=global_designer.getBodyObject(component);
	if(val==''){
		portletBody.css("padding-"+direct,"");
	}else{
		portletBody.css("padding-"+direct,val+"px");
	}
},
/**
*设置外间距
*/
 setColMarginTop:function(component,val){
	var prop = "colMarginTop";
	global_designer.setDataRule(component,prop,val);
},
 setColMarginBottom:function(component,val){
	var prop = "colMarginBottom";
	global_designer.setDataRule(component,prop,val);
},
 setColMarginLeft:function(component,val){
	var prop = "colMarginLeft";
	global_designer.setDataRule(component,prop,val);
},
 setColMarginRight:function(component,val){
	var prop = "colMarginRight";
	global_designer.setDataRule(component,prop,val);
},
 setColMargin:function(component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=global_designer.getMainObject(component);
	if(val==''){
			obj.css("margin-"+direct,"");
	}else{
	        obj.css("margin-"+direct,val+"px");
	}
},
/**
*内容水平对齐
*/
 setTextAlign:function(component,val){
	
	var prop = "textAlign";
	global_designer.setDataRule(component,prop,val);
},
 setVerticalAlign:function(component,val){
	var prop = "verticalAlign";
	global_designer.setDataRule(component,prop,val);
},
/**
*设置背景色
*/
 setBackgroundColor:function(component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var prop = "backgroundColor";
	global_designer.setDataRule(component,prop,val); 
	var portletBody=global_designer.getBodyObject(component);
	var portlet=global_designer.getMainObject(component);
	if(val==''){
	  portletBody.css("background-color",'');
	  portlet.css("background-color",'');
	}else{
	  portletBody.css("background-color",val);
	  portlet.css("background-color",val);
	}
},

initBackgroundImage:function(template,value){
	var $temp = $(template);
	if(value){
		$temp.find("#backgroundImage1").val(value);
	}
	return $temp[0];
},
/**
*设置背景色
*/
 setBackgroundImage:function(component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var prop = "backgroundImage";
	global_designer.setDataRule(component,prop,val); 
	var portletBody=global_designer.getBodyObject(component);
	if(val==''){
	  portletBody.css("background-image",'url("")');
	}else{
	  portletBody.css("background-image","url("+contextPath+val+")");
	}
},
/**
*设置透明度
*/
 setTransparency:function(component,val){
	var prop = "transparency";
	global_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=global_designer.getBodyObject(component);
	if(val==''){
	  portletBody.css("filter","");
	  portletBody.css("-moz-opacity","");
	  portletBody.css("-khtml-opacity","");
	  portletBody.css("opacity","");
	}else{
	  portletBody.css("filter","alpha(opacity="+val+")");
	  portletBody.css("-moz-opacity",val/100);
	  portletBody.css("-khtml-opacity",val/100);
	  portletBody.css("opacity",val/100);
	}
},

 setStretch:function(component,val){
	var prop = "stretch";
	global_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet-body");
	var portlet=global_designer.getBodyObject(component);
	portlet.css("background-size",val);
},
 setRepeat:function(component,val){ 
	var prop = "repeat";
	global_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet-body");
	var portlet=global_designer.getBodyObject(component);
	portlet.css("background-repeat",val);
},
/**
*设置边框
*/
 setBorderTopWidth:function(component,val){
	var prop = "borderWidthTop";
	global_designer.setDataRule(component,prop,val); 
},
 setBorderBottomWidth:function(component,val){
	var prop = "borderWidthBottom";
	global_designer.setDataRule(component,prop,val); 
},
 setBorderLeftWidth:function(component,val){
	var prop = "borderWidthLeft";
	global_designer.setDataRule(component,prop,val);
},
 setBorderRightWidth:function(component,val){
	var prop = "borderWidthRight";
	global_designer.setDataRule(component,prop,val);
},
 setBorderWidth:function(component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=global_designer.getMainObject(component);
	if(val==''){
	   obj.css("border-"+direct+"-width",'');
	}else{
	   obj.css("border-"+direct+"-width",val+"px");
	}
},
/**
*设置边框颜色
*/
 setBorderColor:function(component,val){
	var prop = "borderColor";
	global_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
},

 setBorderRadius:function(component,val){
	var prop = "borderRadius";
	global_designer.setDataRule(component,prop,val);
},
/**
*设置边框样式
*/
 setBorderStyle:function(component,val){
	var prop = "borderStyle";
	global_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
},
/**
*设置字体
*/
 setFontFamily:function(component,val){
	var prop = "fontFamily";
	global_designer.setDataRule(component,prop,val);
},
 setFontColor:function(component,val){ 
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var prop = "fontColor";
	global_designer.setDataRule(component,prop,val);
},
 setFontStyle:function(component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var prop = "fontStyle";
	global_designer.setDataRule(component,prop,val);
},
/**
*设置字体间距
*/
 setLetterSpacing:function(component,val){
	var prop = "letterSpacing";
	global_designer.setDataRule(component,prop,val);
},
 setLineHeight:function(component,val){
	var prop = "lineHeight";
	global_designer.setDataRule(component,prop,val);
},
/**
*设置字体大小
*/
 setFontSize:function(component,val){
	var prop = "fontSize";
	global_designer.setDataRule(component,prop,val);
},
	
	
	/**
*设置缩进
*/
 setTextIndent:function(component,val){
	var prop = "textIndent";
	global_designer.setDataRule(component,prop,val);
},
	
	setDataRule : function(component,prop,val,unit){
		
		var lastVal = global_designer.getDataRule(component,prop)
		
		if(component.attr("data-rule")){
			var data_rule = JSON.parse(component.attr("data-rule"));
			data_rule[prop]=val;
		}else{
			var data_rule={};
			data_rule[prop]=val;
		}
		data_rule["@"+prop]=val;
		//背景色url特殊处理
		if(prop=="backgroundImage"){
			data_rule["@"+prop]=val.substring(val.indexOf("?"),val.length);
		}
		if(unit!=undefined){
			data_rule[prop+"_unit"]=unit;
		}
		component.attr("data-rule",JSON.stringify(data_rule));
		
		return lastVal;
		
	},
	getDataRule :  function(component,prop){
		//debugger;
		var value ="";
		if(component.attr("data-rule")){
			value = JSON.parse(component.attr("data-rule"))[prop];
		}	
		return value;
	}
}
	
	
