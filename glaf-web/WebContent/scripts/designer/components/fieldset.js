/**
 * fieldset
 */
var fieldset_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
    	var $component = $(component);
    	if (val !== '') {
            var prop = "name";
            fieldset_designer.setDataRule(component, prop, val);
            $component.attr("cname", val);
        }
    },


    /**
     * 设置data-rule
     */
	setDataRule: function(component, prop, val,unit) {
//	    var lastVal = fieldset_designer.getDataRule(component, prop)
	    if (component.attr("data-rule")) {
	        var data_rule = JSON.parse(component.attr("data-rule"));
	        data_rule[prop] = val;
	    } else {
	        var data_rule = {};
	        data_rule[prop] = val;
	    }
	    if(unit!=undefined){
	        data_rule[prop+"_unit"]=unit;
	    }
	    component.attr("data-rule", JSON.stringify(data_rule));
	},
	getDataRule: function(component, prop) {
	    var value = "";
	    if (component.attr("data-rule")) {
	        value = JSON.parse(component.attr("data-rule"))[prop];
	    }
	    return value;
	},
    
	setFieldname: function(component, val) {
    	var prop = "fieldname";
    	fieldset_designer.setDataRule(component, prop, val);
        
    	component.find(">legend").text(val);
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
    	fieldset_designer.setDataRule(component,prop,val);
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
    
    setIconClass : function(component,val){
    		var $component = $(component);
    		var prop = "iconClass";
    		var iconClass = fieldset_designer.setDataRule(component,prop,val);
    		var $i = $component.find(".portlet-title .caption i:first");
    		$i.removeClass();
    		$i.addClass(val);
    },
    
    /**
    *设置内间距
    */
     setPaddingTop:function(component,val){
    	var prop = "paddingTop";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPadding(component,"top",val);
    },
     setPaddingBottom:function(component,val){
    	var prop = "paddingBottom";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPadding(component,"bottom",val);
    },
     setPaddingLeft:function(component,val){
    	var prop = "paddingLeft";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPadding(component,"left",val);
    },
     setPaddingRight:function(component,val){
    	var prop = "paddingRight";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPadding(component,"right",val);
    },
     setPadding:function(component,direct,val){
    	
    	var portletBody=fieldset_designer.getBodyObject(component);
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
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setMargin(component,"top",val);
    },
     setMarginBottom:function(component,val){
    	var prop = "marginBottom";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setMargin(component,"bottom",val);
    },
     setMarginLeft:function(component,val){
    	var prop = "marginLeft";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setMargin(component,"left",val);
    },
     setMarginRight:function(component,val){
    	var prop = "marginRight";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setMargin(component,"right",val);
    },
     setMargin:function(component,direct,val){
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet");
    	if(val==''){
    		component.css("margin-"+direct,"");
    	}else{
    		component.css("margin-"+direct,val+"px");
    	}
    },
    /**
    *内容水平对齐
    */
     setTextAlign:function(component,val){
    	
    	var prop = "textAlign";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portletBody=content.find("."+compId+".portlet-body");
    	var portletBody=fieldset_designer.getBodyObject(component);
//    	portletBody.css("text-align",val);
    	portletBody.css("display","flex");
    	portletBody.css("justify-content",val);
    },
     setVerticalAlign:function(component,val){
    	var prop = "verticalAlign";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portletBody=content.find("."+compId+".portlet-body");
    	var portletBody=fieldset_designer.getBodyObject(component);
//    	portletBody.css("vertical-align",val);
    	portletBody.css("display","flex");
    	portletBody.css("align-items",val);
    	
    },
    /**
    *横向溢出
    */
     setOverflowX:function(component,val){
    	var prop = "overflowX";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portletBody=content.find("."+compId+".portlet-body");
    	var portletBody=fieldset_designer.getBodyObject(component);
    	portletBody.css("overflow-x",val);
    },
    /**
    *纵向溢出
    */
     setOverflowY:function(component,val){
    	var prop = "overflowY";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portletBody=content.find("."+compId+".portlet-body");
    	var portletBody=fieldset_designer.getBodyObject(component);
    	portletBody.css("overflow-y",val);
    },
    /**
    *设置位置
    */
     setPositionTop:function(component,val){
    	var prop = "top";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPositon(component,'top',val);
    },
     setPositionBottom:function(component,val){
    	var prop = "bottom";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPositon(component,'bottom',val);
    },
     setPositionLeft:function(component,val){
    	var prop = "left";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPositon(component,'left',val);
    },
     setPositionRight:function(component,val){
    	var prop = "right";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setPositon(component,'right',val);
    },
     setPositon:function(component,direct,val){
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portlet=content.find("."+compId+".portlet");
    	
    	if(val==''){
    		component.css(direct,'');
    	}else{
    		component.css(direct,val+"px");
    	}
    },
    /**
    *设置浮动
    */
     setFloatStyle:function(component,val){
    	var prop = "float";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portlet=content.find("."+compId+".portlet");
    	component.css("float",val);
    },
    /**
    *设置层级
    */
     setZindex:function(component,val){
    	var prop = "zindex";
    	fieldset_designer.setDataRule(component,prop,val);
    	component.css("z-index",val);
    },
    /**
    *设置定位方式
    */
     setPositonStyle:function(component,val){
    	var prop = "position";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portlet=content.find("."+compId+".portlet");
    	component.css("position",val);
    },
    /**
    *设置尺寸
    */
     setWidth:function(component,val,unit){ 
    	var prop = "width";
    	fieldset_designer.setDataRule(component,prop,val,unit);
    	fieldset_designer.setSize(component,"width",val,unit);
    },
     setHeight:function(component,val,unit){
    	var prop = "height";
    	fieldset_designer.setDataRule(component,prop,val,unit); 
    	fieldset_designer.setSize(component,"height",val,unit);
    },
     setMinWidth:function(component,val,unit){
    	var prop = "minWidth";
    	fieldset_designer.setDataRule(component,prop,val,unit); 
    	fieldset_designer.setSize(component,"min-width",val,unit);
    },
     setMinHeight:function(component,val,unit){
    	var prop = "minHeight";  
    	fieldset_designer.setDataRule(component,prop,val,unit); 
    	fieldset_designer.setSize(component,"min-height",val,unit);
    },
     setMaxWidth:function(component,val,unit){
    	var prop = "maxWidth";
    	fieldset_designer.setDataRule(component,prop,val,unit); 
    	fieldset_designer.setSize(component,"max-width",val,unit);
    },
     setMaxHeight:function(component,val,unit){
    	var prop = "maxHeight";
    	fieldset_designer.setDataRule(component,prop,val,unit); 
    	fieldset_designer.setSize(component,"max-height",val,unit);
    },
     setSize:function(component,type,val,unit){
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portlet=content.find("."+compId+".portlet");
    	
    	if(val==''){
    		component.css(type,'');
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
    				component.css('height',val+unit);
    			}
    		    else
    			{
    		    	component.css('height','');	
    			}
    			component.addClass('autoHt');
    			component.children(".view").addClass('autoHt');
    			//portlet.css('height','');
    		}else if(type=='height'){
    			//设计容器调整高度
    			component.removeClass('autoHt');
    			component.children(".view").removeClass('autoHt');
    			component.css('height','');
    			component.css(type,val+unit);
    		}
    	    else{
    	    	component.css(type,val+unit);
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
    	fieldset_designer.setDataRule(component,prop,val); 
    	if(val==''){
    		component.css("background-color",'');
    		component.css("background-color",'');
    	}else{
    		component.css("background-color",val);
    		component.css("background-color",val);
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
    	fieldset_designer.setDataRule(component,prop,val); 
    	if(val==''){
    		component.css("background-image",'');
    	}else{
    		component.css("background-image","url("+contextPath+val+")");
    	}
    },
    

     setStretch:function(component,val){
    	var prop = "stretch";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portlet=content.find("."+compId+".portlet-body");
    	var portlet=fieldset_designer.getBodyObject(component);
    	if(val == 'full'){
    		portlet.css("background-size","100% 100%");
    	}else{
    		portlet.css("background-size",val);	
    	}
    },
     setRepeat:function(component,val){ 
    	var prop = "repeat";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var portlet=content.find("."+compId+".portlet-body");
    	var portlet=fieldset_designer.getBodyObject(component);
    	portlet.css("background-repeat",val);
    },
    /**
    *设置边框
    */
     setBorderTopWidth:function(component,val){
    	var prop = "borderWidthTop";
    	fieldset_designer.setDataRule(component,prop,val); 
    	fieldset_designer.setBorderWidth(component,'top',val);
    },
     setBorderBottomWidth:function(component,val){
    	var prop = "borderWidthBottom";
    	fieldset_designer.setDataRule(component,prop,val); 
    	fieldset_designer.setBorderWidth(component,'bottom',val);
    },
     setBorderLeftWidth:function(component,val){
    	var prop = "borderWidthLeft";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setBorderWidth(component,'left',val);
    },
     setBorderRightWidth:function(component,val){
    	var prop = "borderWidthRight";
    	fieldset_designer.setDataRule(component,prop,val);
    	fieldset_designer.setBorderWidth(component,'right',val);
    },
     setBorderWidth:function(component,direct,val){
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet");
    	if(val==''){
    		component.css("border-"+direct+"-width",'');
    	}else{
    		component.css("border-"+direct+"-width",val+"px");
    	}
    },
    /**
    *设置边框颜色
    */
     setBorderColor:function(component,val){
    	var prop = "borderColor";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet");
    	if(val==''){
    		component.css("border-color",'transparent');
    	}else{
    		component.css("border-color",val);
    	}
    },

     setBorderRadius:function(component,val){
    	var prop = "borderRadius";
    	fieldset_designer.setDataRule(component,prop,val);
    	component.removeClass("radius-level-1 radius-level-2 radius-level-3 radius-level-4 radius-level-5");
    	if(val=='one'){
    		component.addClass("radius-level-1");
    	}else if(val=='two'){ 
    		component.addClass("radius-level-2");
    	}else if(val=='three'){
    		component.addClass("radius-level-3");
    	}else if(val=='four'){ 
    		component.addClass("radius-level-4");
    	}else if(val=='five'){ 
    		component.addClass("radius-level-5");
    	}
    },
    /**
    *设置边框样式
    */
     setBorderStyle:function(component,val){
    	var prop = "borderStyle";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet");
    	if(val==''){
    		component.css("border-style",'');
    	}else{
    		component.css("border-style",val);
    	}
    },
    /**
    *设置字体
    */
     setFontFamily:function(component,val){
    	var prop = "fontFamily";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet-body");
    	var obj = component.find("legend");
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
    	fieldset_designer.setDataRule(component,prop,val);
    	var obj = component.find("legend");
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
    	fieldset_designer.setDataRule(component,prop,val);
    	var obj = component.find("legend");
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
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet-body");
    	var obj = component.find("legend");
    	if(val==''){
    		obj.css("letter-spacing",'');
    	}else{
    		obj.css("letter-spacing",val+"px");
    	}
    },
     setLineHeight:function(component,val){
    	var prop = "lineHeight";
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet-body");
    	var obj = component.find("legend");
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
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet-body");
    	var obj = component.find("legend");
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
    	fieldset_designer.setDataRule(component,prop,val);
    	//var compId=component.attr("id");
    	//var content=component.find(".view:first");
    	//var obj=content.find("."+compId+".portlet-body");
    	if(val==''){
    		component.css("text-indent",'');
    	}else{
    		component.css("text-indent",val+"px");
    	}
    },
    	
    	setDataRule : function(component,prop,val,unit){
    		
    		var lastVal = fieldset_designer.getDataRule(component,prop)
    		
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
    	*设置适应尺寸
    	*/
    	setFitSize : function(component,val){
    		var prop = "fitsize";
    		var vals=val.join(",");
    		fieldset_designer.setDataRule(component,prop,vals);
    		component.removeClass("row_sm");
    		component.removeClass("row_xs");
    		$.each(val,function(i,item){
    			component.addClass("row_"+item);
    		})
    	},


    	setBoxShow_h : function(component, val){
    		var prop = "boxShow_h";
    		fieldset_designer.setDataRule(component,prop,val);
    		if (val == '') {
    			val = '0px';
    		} else {
    			val = val + "px";
    		}
    		fieldset_designer.setBoxShow(component);
    	},
    	setBoxShow_v : function(component, val){
    		var prop = "boxShow_v";
    		fieldset_designer.setDataRule(component,prop,val);
    		if (val == '') {
    			val = '0px';
    		} else {
    			val = val + "px";
    		}
    		fieldset_designer.setBoxShow(component);
    	},
    	setBoxShow_blur : function(component, val){
    		var prop = "boxShow_blur";
    		fieldset_designer.setDataRule(component,prop,val);
    		if (val == '') {
    			val = '0px';
    		} else {
    			val = val + "px";
    		}
    		fieldset_designer.setBoxShow(component);
    	},
    	setBoxShow_size : function(component, val){
    		var prop = "boxShow_size";
    		fieldset_designer.setDataRule(component,prop,val);
    		if (val == '') {
    			val = '0px';
    		} else {
    			val = val + "px";
    		}
    		fieldset_designer.setBoxShow(component);
    	},
    	setBoxShow_color : function(component, val){
    		var prop = "boxShow_color";
    		fieldset_designer.setDataRule(component,prop,val);
    		fieldset_designer.setBoxShow(component);
    	},
    	
    
    
};