
var div_designer={
	
getMainObject:function (component){
	return component;
},
getBodyObject:function (component){
	return component;
},
setTitleDisplay:function (component,val){
	var prop = "titleDisplay";
		div_designer.setDataRule(component,prop,val);
		
	var compId=component.attr("id");
	var content=component.find(".view:first");
	    if(val=='display'){
		   if(content.find("."+compId+".portlet").length){
			   content.find("."+compId+".caption").show();
			   return;
		   }
		  // var  row=$("<div class=\"row clearfix\"></div>");
		   //var  column=$("<div class=\"col-md-12 column ui-sortable\"></div>");
		   var  portletDiv=$("<div class=\""+compId+"  portlet box blue-hoki\"></div>");
		   var  portletTitleDiv=$("<div class=\""+compId+" id portlet-title\"></div>");
		   var  captionDiv=$("<div class=\""+compId+"  caption\"></div>");
		   captionDiv.append("<i class=\""+compId+" id fa fa-gift frame-variable\" frame-variable=\"title\"> �� ��</i>");
		   var  toolsDiv=$("<div class=\"tools\"></div>");
		   toolsDiv.append("<a href=\"javascript:;\" class=\"expand\"> </a>");
           toolsDiv.append("<a href=\"\" class=\"fullscreen\"> </a>");
           toolsDiv.append("<a href=\"javascript:;\" class=\"reload\"> </a>");
		   portletTitleDiv.append(captionDiv);
		   portletTitleDiv.append(toolsDiv);
		   portletDiv.append(portletTitleDiv);
		   var  portletBodyDiv=$("<div class=\""+compId+" id portlet-body frame-variable\" frame-variable=\"body\"></div>");
		   portletBodyDiv.append(content.html());
		   portletDiv.append(portletBodyDiv);
		   content.html(portletDiv);
		   //column.append(portletDiv);
		   //row.append(column);
		   //content.html(row);
	    }
		else if(val=='hide'){
			 if(content.find("."+compId+".portlet").length){
			   content.find("."+compId+".caption").hide();
			   return;
		   }
		}else if(val=='none'){
			content.html(component.find("."+compId+".portlet-body").html());
		}
},
setTitleName:function (component,val){
	var prop = "titleName";
	div_designer.setDataRule(component,prop,val);
	var compId=component.attr("id");
	var content=component.find(".view:first");
	content.find("."+compId+".caption i").html(" "+val);
},
/**
*�����ڼ��
*/
setPaddingTop:function (component,val){
	var prop = "paddingTop";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPadding(component,"top",val);
},
setPaddingBottom:function (component,val){
	var prop = "paddingBottom";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPadding(component,"bottom",val);
},
setPaddingLeft:function (component,val){
	var prop = "paddingLeft";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPadding(component,"left",val);
},
setPaddingRight:function (component,val){
	var prop = "paddingRight";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPadding(component,"right",val);
},
setPadding:function (component,direct,val){
	
	var portletBody=div_designer.getBodyObject(component);
	if(val==''){
		portletBody.css("padding-"+direct,"");
	}else{
		portletBody.css("padding-"+direct,val+"px");
	}
},
/**
*��������
*/
setMarginTop:function (component,val){
	var prop = "marginTop";
	div_designer.setDataRule(component,prop,val);
	div_designer.setMargin(component,"top",val);
},
setMarginBottom:function (component,val){
	var prop = "marginBottom";
	div_designer.setDataRule(component,prop,val);
	div_designer.setMargin(component,"bottom",val);
},
setMarginLeft:function (component,val){
	var prop = "marginLeft";
	div_designer.setDataRule(component,prop,val);
	div_designer.setMargin(component,"left",val);
},
setMarginRight:function (component,val){
	var prop = "marginRight";
	div_designer.setDataRule(component,prop,val);
	div_designer.setMargin(component,"right",val);
},
setMargin:function (component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=div_designer.getMainObject(component);
	if(val==''){
			obj.css("margin-"+direct,"");
	}else{
	        obj.css("margin-"+direct,val+"px");
	}
},
/**
*����ˮƽ����
*/
setTextAlign:function (component,val){
	
	var prop = "textAlign";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=div_designer.getBodyObject(component);
//	portletBody.css("text-align",val);
	// portletBody.css("display","flex");
	if(val){
		portletBody.css("display","flex");	
	}else{
		portletBody.css("display","");	
	}
	portletBody.css("justify-content",val);
},
setVerticalAlign:function (component,val){
	var prop = "verticalAlign";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=div_designer.getBodyObject(component);
//	portletBody.css("vertical-align",val);
	portletBody.css("display","flex");
	portletBody.css("align-items",val);
},
/**
*�������
*/
setOverflowX:function (component,val){
	var prop = "overflowX";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=div_designer.getBodyObject(component);
	portletBody.css("overflow-x",val);
},
/**
*�������
*/
setOverflowY:function (component,val){
	var prop = "overflowY";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=div_designer.getBodyObject(component);
	portletBody.css("overflow-y",val);
},
/**
*����λ��
*/
setPositionTop:function (component,val){
	var prop = "top";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPositon(component,'top',val);
},
setPositionBottom:function (component,val){
	var prop = "bottom";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPositon(component,'bottom',val);
},
setPositionLeft:function (component,val){
	var prop = "left";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPositon(component,'left',val);
},
setPositionRight:function (component,val){
	var prop = "right";
	div_designer.setDataRule(component,prop,val);
	div_designer.setPositon(component,'right',val);
},
setPositon:function (component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet");
	
	var portlet=div_designer.getMainObject(component);
	if(val==''){
	  portlet.css(direct,'');
	}else{
	  portlet.css(direct,val+"px");
	}
},
/**
*���ö�λ��ʽ
*/
setPositonStyle:function (component,val){
	var prop = "position";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet");
	var portlet=div_designer.getMainObject(component);
	portlet.css("position",val);
},
/**
*设置层级
*/
 setZindex:function(component,val){
	var prop = "zindex";
	div_designer.setDataRule(component,prop,val);
	var portlet=div_designer.getMainObject(component);
	portlet.css("z-index",val);
},
setSizeAuto:function(component,val,unit){
	var portlet=div_designer.getMainObject(component);
	if(val == 'width'){
		portlet.css("width","auto");
	}else if(val == 'height'){
		portlet.css("height","auto");
	}
},
/**
*���óߴ�
*/
setWidth:function (component,val,unit){ 
	var prop = "width";
	div_designer.setDataRule(component,prop,val,unit);
	div_designer.setSize(component,"width",val,unit);
},
setHeight:function (component,val,unit){
	var prop = "height";
	div_designer.setDataRule(component,prop,val,unit); 
	div_designer.setSize(component,"height",val,unit);
},
setMinWidth:function (component,val,unit){
	var prop = "minWidth";
	div_designer.setDataRule(component,prop,val,unit); 
	div_designer.setSize(component,"min-width",val,unit);
},
setMinHeight:function (component,val,unit){
	var prop = "minHeight";  
	div_designer.setDataRule(component,prop,val,unit); 
	div_designer.setSize(component,"min-height",val,unit);
},
setMaxWidth:function (component,val,unit){
	var prop = "maxWidth";
	div_designer.setDataRule(component,prop,val,unit); 
	div_designer.setSize(component,"max-width",val,unit);
},
setMaxHeight:function (component,val,unit){
	var prop = "maxHeight";
	div_designer.setDataRule(component,prop,val,unit); 
	div_designer.setSize(component,"max-height",val,unit);
},
setSize:function (component,type,val,unit){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet");
	
	var portlet=div_designer.getMainObject(component);
	if(val==''){
	  portlet.css(type,'');
	}else{
		if(unit==undefined)
		{
			unit="px";
		}
	  portlet.css(type,val+unit);
	}
},
/**
*���ñ���ɫ
*/
setBackgroundColor:function (component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var prop = "backgroundColor";
	div_designer.setDataRule(component,prop,val); 
	var portletBody=div_designer.getBodyObject(component);
	if(val==''){
	  portletBody.css("background-color",'');
	}else{
	  portletBody.css("background-color",val);
	}
},
/**
*���ñ���ɫ
*/
setBackgroundImage:function (component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var prop = "backgroundImage";
	div_designer.setDataRule(component,prop,val); 
	var portletBody=div_designer.getBodyObject(component);
	if(val==''){
	  portletBody.css("background-image",'');
	}else{
	   portletBody.css("background-image","url("+contextPath+val+")");
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
*����͸����
*/
setTransparency:function (component,val){
	var prop = "transparency";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=div_designer.getBodyObject(component);
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
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet-body");
	var portlet=div_designer.getBodyObject(component);
	if(val == 'full'){
		portlet.css("background-size","100% 100%");
	}else{
		portlet.css("background-size",val);	
	}
},
setRepeat:function (component,val){ 
	var prop = "repeat";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet-body");
	var portlet=div_designer.getBodyObject(component);
	portlet.css("background-repeat",val);
},
/**
*���ñ߿�
*/
setBorderTopWidth:function (component,val){
	var prop = "borderWidthTop";
	div_designer.setDataRule(component,prop,val); 
	div_designer.setBorderWidth(component,'top',val);
},
setBorderBottomWidth:function (component,val){
	var prop = "borderWidthBottom";
	div_designer.setDataRule(component,prop,val); 
	div_designer.setBorderWidth(component,'bottom',val);
},
setBorderLeftWidth:function (component,val){
	var prop = "borderWidthLeft";
	div_designer.setDataRule(component,prop,val);
	div_designer.setBorderWidth(component,'left',val);
},
setBorderRightWidth:function (component,val){
	var prop = "borderWidthRight";
	div_designer.setDataRule(component,prop,val);
	div_designer.setBorderWidth(component,'right',val);
},
setBorderWidth:function (component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=div_designer.getMainObject(component);
	if(val==''){
	   obj.css("border-"+direct+"-width",'');
	}else{
	   obj.css("border-"+direct+"-width",val+"px");
	}
},
/**
*���ñ߿���ɫ
*/
setBorderColor:function (component,val){
	var prop = "borderColor";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=div_designer.getMainObject(component);
	if(val==''){
	   obj.css("border-color",'');
	}else{
	   obj.css("border-color",val);
	}
},

setBorderRadius:function(component,val){
	var prop = "borderRadius";
	div_designer.setDataRule(component,prop,val);
	var obj = div_designer.getMainObject(component);
	    obj.removeClass("radius-level-1 radius-level-2 radius-level-3 radius-level-4 radius-level-5");
	if(val=='one'){
	    obj.addClass("radius-level-1");
	}else if(val=='two'){ 
	    obj.addClass("radius-level-2");
	}else if(val=='three'){
		obj.addClass("radius-level-3");
	}else if(val=='four'){ 
		obj.addClass("radius-level-4");
	}else if(val=='five'){ 
		obj.addClass("radius-level-5");
	}
},
/**
*���ñ߿���ʽ
*/
setBorderStyle:function (component,val){
	var prop = "borderStyle";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=div_designer.getMainObject(component);
	if(val==''){
	   obj.css("border-style",'');
	}else{
	   obj.css("border-style",val);
	}
},
/**
*��������
*/
setFontFamily:function (component,val){
	var prop = "fontFamily";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.css("font-family",'');
	}else{
	   obj.css("font-family",val);
	}
},
setFontColor:function (component,val){ 
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var prop = "fontColor";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.css("font-color",'');
	}else{
	   obj.css("font-color",val);
	}
},
setFontStyle:function (component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var prop = "fontStyle";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val=='italic'){
	   if($(this).hasClass('active')){
	    obj.css("font-style",'italic');
	   }else{
		obj.css("font-style",'');   
	   }
	}else if(val=='bold'){
		 if($(this).hasClass('active')){
	      obj.css("font-weight",'bold');
		 }else{
		   obj.css("font-weight",'');   
	    }
	}
},
/**
*����������
*/
setLetterSpacing:function (component,val){
	var prop = "letterSpacing";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.css("letter-spacing",'');
	}else{
	   obj.css("letter-spacing",val+"px");
	}
} ,
setLineHeight:function (component,val){
	var prop = "lineHeight";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.css("line-height",'');
	}else{
	   obj.css("line-height",val+"px");
	}
},
/**
*���������С
*/
setFontSize:function (component,val){
	var prop = "fontSize";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.css("font-size",'');
	}else{
	   obj.css("font-size",val+"px");
	}
},
setSlimAble: function(component,val){
	var prop = "slimAble";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.removeAttr("needSlimScroll");
	}else{
	   obj.attr("needSlimScroll","needSlimScroll");
	}
},
setHandleWidth: function(component,val){
	var prop = "handleWidth";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.removeAttr("data-handle-width");
	}else{
	   obj.attr("data-handle-width",val+"px");
	}
},
setHandleColor: function(component,val){
	var prop = "handleColor";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.removeAttr("data-handle-color");
	}else{
	   obj.attr("data-handle-color",val);
	}
	
},
setRailColor: function(component,val){
	var prop = "railColor";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.removeAttr("data-rail-color");
	}else{
	   obj.attr("data-rail-color",val);
	}
},
setSlimOpacity: function(component,val){
	var prop = "slimOpcity";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.removeAttr("data-opacity");
	}else{
	   obj.attr("data-opacity",val);
	}
},
setAlwaysVisible: function(component,val){
	var prop = "alwaysVisible";
	div_designer.setDataRule(component,prop,val);
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.removeAttr("data-always-visible");
	}else{
	   obj.attr("data-always-visible","1");
	}
},
/**
*��������
*/
setTextIndent:function (component,val){
	var prop = "textIndent";
	div_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=div_designer.getBodyObject(component);
	if(val==''){
	   obj.css("text-indent",'');
	}else{
	   obj.css("text-indent",val+"px");
	}
},
setDataRule : function(component,prop,val,unit){
		
		var lastVal = div_designer.getDataRule(component,prop)
		
		if(component.attr("data-rule")){
			var data_rule = JSON.parse(component.attr("data-rule"));
			data_rule[prop]=val;
		}else{
			var data_rule={};
			data_rule[prop]=val;
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