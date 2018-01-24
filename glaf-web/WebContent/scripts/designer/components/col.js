

var col_designer = {
	
getMainObject:function(component){
	var compId=component.attr("id");
	var content=component.find(".view:first");
	var mainObj;
	if(content.find("."+compId+".portlet").length){
		mainObj=content.find("."+compId+".portlet");
	}else{
		mainObj=content.find("."+compId+".row");
	}
	return mainObj;
},
 getBodyObject:function(component){
	var compId=component.attr("id");
	var content=component.find(".view:first");
	var mainBody;
	if(content.find("."+compId+".portlet-body").length){
		mainBody=content.find("."+compId+".portlet-body");
	}else{
		mainBody=content.find("."+compId+".row");
	}
	return mainBody;
},
 setTitleDisplay:function(component,val){
	var prop = "titleDisplay";
	col_designer.setDataRule(component,prop,val);
	var autoHtFlag=false;
	//获取当前是否自适应
    if(component.hasClass("autoHt")){
		autoHtFlag=true;
	}	
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
		   if(autoHtFlag){
			   portletDiv.addClass("autoHt");
		   }
		   var  portletTitleDiv=$("<div class=\""+compId+" id portlet-title\"></div>");
		   var  captionDiv=$("<div class=\""+compId+"  caption\"></div>");
		   captionDiv.append("<i class=\""+compId+" id fa fa-gift \"> </i>");
		   captionDiv.append("<span class=\"frame-variable\" frame-variable=\"title\">标题</span>");
		   var  toolsDiv=$("<div class=\"tools\"></div>");
		   toolsDiv.append("<a href=\"javascript:;\" class=\"expand\"> </a>");
           toolsDiv.append("<a href=\"\" class=\"fullscreen\"> </a>");
           toolsDiv.append("<a href=\"javascript:;\" class=\"reload\"> </a>");
		   portletTitleDiv.append(captionDiv);
		   portletTitleDiv.append(toolsDiv);
		   portletDiv.append(portletTitleDiv);
		   var  portletBodyDiv=$("<div class=\""+compId+" id portlet-body frame-variable\" frame-variable=\"body\"></div>");
		   //if(autoHtFlag){
			  // portletBodyDiv.css("height","95.5%");
		   //}
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
 setTitleName:function(component,val){
	var prop = "titleName";
	col_designer.setDataRule(component,prop,val);
	var compId=component.attr("id");
	var content=component.find(".view:first");
	content.find("."+compId+".caption span").html(" "+val);
},
setIconClass : function(component,val){
		var $component = $(component);
		var prop = "iconClass";
		var iconClass = col_designer.setDataRule(component,prop,val);
		var $i = $component.find(".portlet-title .caption i:first");
		$i.removeClass();
		$i.addClass(val);
},
/**
*设置标题内容颜色
*/
setTitleFrontColor: function (component,val){
		var prop = "titleFrontColor"; 
		col_designer.setDataRule(component,prop,val);
		component.find('.portlet-title .caption').css({'color': val});
		component.find('.portlet-title .caption>i').css({'color': val});
		component.find('.portlet-title .tools').css({'color': val});
		component.find('.portlet-title .actions>a').css({'color': val, 'border-color': val});
		component.find('.portlet-title .actions>a>i').css({'color': val});
},

/**标题
 *设置边框颜色
 */
setBorderColor_title: function(component, val) {
	var prop = "borderColor_title";
	
	col_designer.setDataRule(component, prop, val);
	var $i = component.find(".portlet-title");
	$i.css("border-color",val);
},
/**
 *设置标题边框样式
 */
setBorderStyle_title: function(component, val) {
	var prop = "borderStyle_title";
	col_designer.setDataRule(component, prop, val);
	var $i = component.find(".portlet-title");
	$i.css("border-style",val);

},
/**
 * 设置标题边框大小
 */
setBorderWidth_Top_title: function(component,val){
	if (val != '') {
		val = val + "px";
	}
	var prop = "borderWidthTop_title";
	col_designer.setDataRule(component, prop, val);
	var $i = component.find(".portlet-title");
	$i.css("border-top-width",val);
},
/**
 * 设置标题边框大小
 */
setBorderWidth_Bottom_title: function(component,val){
	if (val != '') {
		val = val + "px";
	}
	var prop = "borderWidthBottom_title";
	col_designer.setDataRule(component, prop, val);
	var $i = component.find(".portlet-title");
	$i.css("border-bottom-width",val);
},
/**
 * 设置标题边框大小
 */
setBorderWidth_Left_title: function(component,val){
	if (val != '') {
		val = val + "px";
	}
	var prop = "borderWidthLeft_title";
	col_designer.setDataRule(component, prop, val);
	var $i = component.find(".portlet-title");
	$i.css("border-left-width",val);
},
/**
 * 设置标题边框大小
 */
setBorderWidth_Right_title: function(component,val){
	if (val != '') {
		val = val + "px";
	}
	var prop = "borderWidthRight_title";
	col_designer.setDataRule(component, prop, val);
	var $i = component.find(".portlet-title");
	$i.css("border-right-width",val);
},


/**
*设置内间距
*/
 setPaddingTop:function(component,val){
	var prop = "paddingTop";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPadding(component,"top",val);
},
 setPaddingBottom:function(component,val){
	var prop = "paddingBottom";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPadding(component,"bottom",val);
},
 setPaddingLeft:function(component,val){
	var prop = "paddingLeft";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPadding(component,"left",val);
},
 setPaddingRight:function(component,val){
	var prop = "paddingRight";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPadding(component,"right",val);
},
 setPadding:function(component,direct,val){
	
	var portletBody=col_designer.getBodyObject(component);
	if(val==''){
		portletBody.css("padding-"+direct,"");
	}else{
		portletBody.css("padding-"+direct,val+"px");
	}
},
/**
*设置外间距
*/
 setMarginTop:function(component,val){
	var prop = "marginTop";
	col_designer.setDataRule(component,prop,val);
	col_designer.setMargin(component,"top",val);
},
 setMarginBottom:function(component,val){
	var prop = "marginBottom";
	col_designer.setDataRule(component,prop,val);
	col_designer.setMargin(component,"bottom",val);
},
 setMarginLeft:function(component,val){
	var prop = "marginLeft";
	col_designer.setDataRule(component,prop,val);
	col_designer.setMargin(component,"left",val);
},
 setMarginRight:function(component,val){
	var prop = "marginRight";
	col_designer.setDataRule(component,prop,val);
	col_designer.setMargin(component,"right",val);
},
 setMargin:function(component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=col_designer.getMainObject(component);
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
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=col_designer.getBodyObject(component);
//	portletBody.css("text-align",val);
	if(val){
		portletBody.css("display","flex");	
	}else{
		portletBody.css("display","");	
	}
	portletBody.css("justify-content",val);
},
 setVerticalAlign:function(component,val){
	var prop = "verticalAlign";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=col_designer.getBodyObject(component);
//	portletBody.css("vertical-align",val);
	portletBody.css("display","flex");
	portletBody.css("align-items",val);
	
},
/**
*横向溢出
*/
 setOverflowX:function(component,val){
	var prop = "overflowX";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=col_designer.getBodyObject(component);
	portletBody.css("overflow-x",val);
},
/**
*纵向溢出
*/
 setOverflowY:function(component,val){
	var prop = "overflowY";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=col_designer.getBodyObject(component);
	portletBody.css("overflow-y",val);
},
/**
*设置位置
*/
 setPositionTop:function(component,val){
	var prop = "top";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPositon(component,'top',val);
},
 setPositionBottom:function(component,val){
	var prop = "bottom";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPositon(component,'bottom',val);
},
 setPositionLeft:function(component,val){
	var prop = "left";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPositon(component,'left',val);
},
 setPositionRight:function(component,val){
	var prop = "right";
	col_designer.setDataRule(component,prop,val);
	col_designer.setPositon(component,'right',val);
},
 setPositon:function(component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet");
	
	var portlet=col_designer.getMainObject(component);
	if(val==''){
	  portlet.css(direct,'');
	}else{
	  portlet.css(direct,val+"px");
	}
},
/**
*设置浮动
*/
 setFloatStyle:function(component,val){
	var prop = "float";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet");
	var portlet=col_designer.getMainObject(component);
	portlet.css("float",val);
},
/**
*设置层级
*/
 setZindex:function(component,val){
	var prop = "zindex";
	col_designer.setDataRule(component,prop,val);
	var portlet=col_designer.getMainObject(component);
	portlet.css("z-index",val);
},
/**
*设置定位方式
*/
 setPositonStyle:function(component,val){
	var prop = "position";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet");
	var portlet=col_designer.getMainObject(component);
	portlet.css("position",val);
},
setSizeAuto:function(component,val,unit){
	var portlet=col_designer.getMainObject(component);
	if(val == 'width'){
		portlet.css("width","auto");
	}else if(val == 'height'){
		portlet.css("height","auto");
	}
},
/**
*设置尺寸
*/
 setWidth:function(component,val,unit){ 
	var prop = "width";
	col_designer.setDataRule(component,prop,val,unit);
	col_designer.setSize(component,"width",val,unit);
},
 setHeight:function(component,val,unit){
	var prop = "height";
	col_designer.setDataRule(component,prop,val,unit); 
	col_designer.setSize(component,"height",val,unit);
},
 setMinWidth:function(component,val,unit){
	var prop = "minWidth";
	col_designer.setDataRule(component,prop,val,unit); 
	col_designer.setSize(component,"min-width",val,unit);
},
 setMinHeight:function(component,val,unit){
	var prop = "minHeight";  
	col_designer.setDataRule(component,prop,val,unit); 
	col_designer.setSize(component,"min-height",val,unit);
},
 setMaxWidth:function(component,val,unit){
	var prop = "maxWidth";
	col_designer.setDataRule(component,prop,val,unit); 
	col_designer.setSize(component,"max-width",val,unit);
},
 setMaxHeight:function(component,val,unit){
	var prop = "maxHeight";
	col_designer.setDataRule(component,prop,val,unit); 
	col_designer.setSize(component,"max-height",val,unit);
},
 setSize:function(component,type,val,unit){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet");
	
	var portlet=col_designer.getMainObject(component);
	if(val==''){
	  portlet.css(type,'');
	  if(type=='height')
	  component.css('height','');	
	}else{
		if(unit==undefined)
		{
			unit="px";
		}
	    if(unit=='%'&&type=='height'){
			//设计容器调整高度
			if(val!='')
			{
				portlet.css('height',val+unit);
			}
		    else
			{
				portlet.css('height','');	
			}
			component.addClass('autoHt');
			component.children(".view").addClass('autoHt');
			//portlet.css('height','');
		}else if(type=='height'){
			//设计容器调整高度
			component.removeClass('autoHt');
			component.children(".view").removeClass('autoHt');
			component.css('height','');
			portlet.css(type,val+unit);
		}
	    else{
			portlet.css(type,val+unit);
		}
	}
	
},
/**
*设置背景色
*/
 setBackgroundColor:function(component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var prop = "backgroundColor";
	col_designer.setDataRule(component,prop,val); 
	var portletBody=col_designer.getBodyObject(component);
	var portlet=col_designer.getMainObject(component);
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
	col_designer.setDataRule(component,prop,val); 
	var portletBody=col_designer.getBodyObject(component);
	if(val==''){
	  portletBody.css("background-image",'');
	}else{
	  portletBody.css("background-image","url("+contextPath+val+")");
	}
},
/**
*设置透明度
*/
 setTransparency:function(component,val){
	var prop = "transparency";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portletBody=content.find("."+compId+".portlet-body");
	var portletBody=col_designer.getBodyObject(component);
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
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet-body");
	var portlet=col_designer.getBodyObject(component);
	if(val == 'full'){
		portlet.css("background-size","100% 100%");
	}else{
		portlet.css("background-size",val);	
	}
},
 setRepeat:function(component,val){ 
	var prop = "repeat";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var portlet=content.find("."+compId+".portlet-body");
	var portlet=col_designer.getBodyObject(component);
	portlet.css("background-repeat",val);
},
/**
*设置边框
*/
 setBorderTopWidth:function(component,val){
	var prop = "borderWidthTop";
	col_designer.setDataRule(component,prop,val); 
	col_designer.setBorderWidth(component,'top',val);
},
 setBorderBottomWidth:function(component,val){
	var prop = "borderWidthBottom";
	col_designer.setDataRule(component,prop,val); 
	col_designer.setBorderWidth(component,'bottom',val);
},
 setBorderLeftWidth:function(component,val){
	var prop = "borderWidthLeft";
	col_designer.setDataRule(component,prop,val);
	col_designer.setBorderWidth(component,'left',val);
},
 setBorderRightWidth:function(component,val){
	var prop = "borderWidthRight";
	col_designer.setDataRule(component,prop,val);
	col_designer.setBorderWidth(component,'right',val);
},
 setBorderWidth:function(component,direct,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=col_designer.getMainObject(component);
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
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=col_designer.getMainObject(component);
	if(val==''){
	   obj.css("border-color",'transparent');
	}else{
	   obj.css("border-color",val);
	}
},

 setBorderRadius:function(component,val){
	var prop = "borderRadius";
	col_designer.setDataRule(component,prop,val);
	var obj=col_designer.getMainObject(component);
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
*设置边框样式
*/
 setBorderStyle:function(component,val){
	var prop = "borderStyle";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet");
	var obj=col_designer.getMainObject(component);
	if(val==''){
	   obj.css("border-style",'');
	}else{
	   obj.css("border-style",val);
	}
},
/**
*设置字体
*/
 setFontFamily:function(component,val){
	var prop = "fontFamily";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=col_designer.getBodyObject(component);
	if(val==''){
	   obj.css("font-family",'');
	}else{
	   obj.css("font-family",val);
	}
},
 setFontColor:function(component,val){ 
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var prop = "fontColor";
	col_designer.setDataRule(component,prop,val);
	var obj=col_designer.getBodyObject(component);
	if(val==''){
	   obj.css("font-color",'');
	}else{
	   obj.css("font-color",val);
	}
},
 setFontStyle:function(component,val){
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var prop = "fontStyle";
	col_designer.setDataRule(component,prop,val);
	var obj=col_designer.getBodyObject(component);
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
*设置字体间距
*/
 setLetterSpacing:function(component,val){
	var prop = "letterSpacing";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=col_designer.getBodyObject(component);
	if(val==''){
	   obj.css("letter-spacing",'');
	}else{
	   obj.css("letter-spacing",val+"px");
	}
},
 setLineHeight:function(component,val){
	var prop = "lineHeight";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=col_designer.getBodyObject(component);
	if(val==''){
	   obj.css("line-height",'');
	}else{
	   obj.css("line-height",val+"px");
	}
},
/**
*设置字体大小
*/
 setFontSize:function(component,val){
	var prop = "fontSize";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=col_designer.getBodyObject(component);
	if(val==''){
	   obj.css("font-size",'');
	}else{
	   obj.css("font-size",val+"px");
	}
},
	
	
	/**
*设置缩进
*/
 setTextIndent:function(component,val){
	var prop = "textIndent";
	col_designer.setDataRule(component,prop,val);
	//var compId=component.attr("id");
	//var content=component.find(".view:first");
	//var obj=content.find("."+compId+".portlet-body");
	var obj=col_designer.getBodyObject(component);
	if(val==''){
	   obj.css("text-indent",'');
	}else{
	   obj.css("text-indent",val+"px");
	}
},
	
	setDataRule : function(component,prop,val,unit){
		
		var lastVal = col_designer.getDataRule(component,prop)
		
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
	},
	/*
	* 设置标题颜色
	*/
	setTitleColor : function(component,val){
		var portletTitle = component.find('.'+component.attr("id")+'.portlet-title');
		col_designer.setDataRule(component,"titleColor",val);
		if(portletTitle && portletTitle.length){
			portletTitle.css({
				"background-color": val
			});
		}
	},
	/*
	* 工具盒
	*/
	setTitleTools : function(component,val){
		var tools = component.find('.'+component.attr("id")+'.portlet-title>.tools');
		if(tools && tools.length){
			var tool = tools.find("."+val);
			if(val=="collapse" && tool.length==0){
				tool = tools.find(".expand");
			}
			if(tool.css("display")!="none"){
				tool.css("display","none");
			}else{
				tool.css("display","");
			}
		}
	},
	/*
	*设置适应尺寸
	*/
	setFitSize : function(component,val){
		var prop = "fitsize";
		var vals=val.join(",");
		col_designer.setDataRule(component,prop,vals);
		var obj=col_designer.getMainObject(component);
		obj.removeClass("row_sm");
		obj.removeClass("row_xs");
		$.each(val,function(i,item){
			obj.addClass("row_"+item);
		})
	},
	/*
	* 设置控件名称
	*/
	setWidgetName : function(component,val){
		component.attr("cname",val);
		col_designer.setDataRule(component,"widgetName",val);
	},


	/**
	 * 阴影
	 */
	setBoxShow : function(component){
		var boxShadow = (col_designer.getDataRule(component,'boxShow_h') || '0') + 'px';
		boxShadow += " " + ((col_designer.getDataRule(component,'boxShow_v') || '0') + 'px');
		boxShadow += " " + ((col_designer.getDataRule(component,'boxShow_blur') || '0') + 'px');
		boxShadow += " " + ((col_designer.getDataRule(component,'boxShow_size') || '0') + 'px');
		boxShadow += " " + (col_designer.getDataRule(component,'boxShow_color') || '');

		var obj=col_designer.getMainObject(component);
		obj.css("box-shadow",boxShadow);
	},
	setBoxShow_h : function(component, val){
		var prop = "boxShow_h";
		col_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		col_designer.setBoxShow(component);
	},
	setBoxShow_v : function(component, val){
		var prop = "boxShow_v";
		col_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		col_designer.setBoxShow(component);
	},
	setBoxShow_blur : function(component, val){
		var prop = "boxShow_blur";
		col_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		col_designer.setBoxShow(component);
	},
	setBoxShow_size : function(component, val){
		var prop = "boxShow_size";
		col_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		col_designer.setBoxShow(component);
	},
	setBoxShow_color : function(component, val){
		var prop = "boxShow_color";
		col_designer.setDataRule(component,prop,val);
		col_designer.setBoxShow(component);
	},
	
}
	
	
