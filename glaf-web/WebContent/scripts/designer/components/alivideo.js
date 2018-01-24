/**
 * textboxbt
 */
var alivideo_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		alivideo_designer.setDataRule(component,prop,val);
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
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = alivideo_designer.getBodyObject(component);
		
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
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = alivideo_designer.getBodyObject(component);
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
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		alivideo_designer.setDataRule(component,prop,val);
		alivideo_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		
		var obj = alivideo_designer.getBodyObject(component);
		
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
		alivideo_designer.setDataRule(component,prop,val);
		var obj = alivideo_designer.getBodyObject(component);
		component.css("position", val);
		
	},
	/**
	*����ˮƽ����
	*/
	setTextAlign:function (component,val){
		
		var prop = "textAlign";
		alivideo_designer.setDataRule(component,prop,val);
		var obj=alivideo_designer.getBodyObject(component);
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
		alivideo_designer.setDataRule(component,prop,val,unit);
		
		alivideo_designer.setSize(component,"width",val,unit);
	},
	setHeight:function (component,val,unit){ 
		var prop = "height";
		alivideo_designer.setDataRule(component,prop,val,unit);
		if(component.find("img").length){
			alivideo_designer.setSize(component.find("img"),"height",val,unit);
		}else{
			alivideo_designer.setSize(component,"height",val,unit);
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
	
	
	
	
	/**
	*���ñ���ɫ
	*/
	setBackgroundColor:function (component,val){
		var prop = "backgroundColor";
		alivideo_designer.setDataRule(component,prop,val); 
		var obj=alivideo_designer.getBodyObject(component);
		if(val==''){
		  obj.css("background-color",'');
		}else{
		  obj.css("background-color",val);
		}
	},
	/**
	*����͸����
	*/
	setTransparency:function (component,val){
		var prop = "transparency";
		alivideo_designer.setDataRule(component,prop,val);
		var obj=alivideo_designer.getBodyObject(component);
		if(val==''){
		  obj.css("filter","");
		  obj.css("-moz-opacity","");
		  obj.css("-khtml-opacity","");
		  obj.css("opacity","");
		}else{
		  obj.css("filter","alpha(opacity="+val+")");
		  obj.css("-moz-opacity",val/100);
		  obj.css("-khtml-opacity",val/100);
		  obj.css("opacity",val/100);
		}
	},
	/**
	*���ñ���ɫ
	*/
	setBackgroundImage:function (component,val){
		var prop = "backgroundImage";
		alivideo_designer.setDataRule(component,prop,val); 
		var obj=alivideo_designer.getBodyObject(component);
		if(val==''){
		   component.css("background-image",'');
		}else{
		   component.css("background-image","url("+contextPath+val+")");
		}
	},
	initBackgroundImage:function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#backgroundImage1").val(value);
		}
		return $temp[0];
	},
	setStretch:function(component,val){
		var prop = "stretch";
		alivideo_designer.setDataRule(component,prop,val);
		var obj=alivideo_designer.getBodyObject(component);
		component.css("background-size",val);
	},
	setRepeat:function (component,val){ 
		var prop = "repeat";
		alivideo_designer.setDataRule(component,prop,val);
		var obj=alivideo_designer.getBodyObject(component);
		component.css("background-repeat",val);
	},

	

	setContent : function(component, val){
		var prop = "content"; 
		alivideo_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.find("p").html("");
		} else {
			component.find("p").html(val);
		}
	},
	
	/**
	 * 设置字体
	 */
	setFontFamily : function(component, val) {
		var prop = "fontFamily"; 
		alivideo_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("font-family", '');
		} else {
			component.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor"; 
		alivideo_designer.setDataRule(component,prop,val);

		if (val == '') {
			component.css("color", '');
		} else {
			component.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle"; 
		alivideo_designer.setDataRule(component,prop,val);
		if (val == 'italic') {
			if ($(this).hasClass('active')) {
				component.css("font-style", 'italic');
			} else {
				component.css("font-style", '');
			}
		} else if (val == 'bold') {
			if ($(this).hasClass('active')) {
				component.css("font-weight", 'bold');
			} else {
				component.css("font-weight", '');
			}
		}
	},
	/**
	 * 设置字体间距
	 */
	setLetterSpacing : function(component, val) {
		var prop = "letterSpacing"; 
		alivideo_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("letter-spacing", '');
		} else {
			component.css("letter-spacing", val + "px");
		}
	},
	/**
	 * 设置字体大小
	 */
	setFontSize : function(component, val) {
		var prop = "fontSize"; 
		alivideo_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("font-size", '');
		} else {
			component.css("font-size", val + "px");
		}
	},
	getBodyObject:function (component){
		return component.find("p");
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = alivideo_designer.getDataRule(component,prop)
		
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
	setIconClass : function(component,val){
		var $component = $(component);
		
		var prop = "iconClass";
		var iconClass = alivideo_designer.setDataRule(component,prop,val);
			
		$component.find("p").css("display","inline");
		if($component.find("i").length>0){
			$component.find("i").removeClass(iconClass);
			$component.find("i").addClass(val);				
		}else{
			$icon = $("<i></i>");
			$icon.addClass(val);			
			$component.prepend($icon);
		}
	},
	initIconClass: function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#icon").addClass(value);
			$temp.find("#iconName").html(value);
		}
		return $temp[0];
	},
	setIconColor:function(component,val){
		var prop = "iconColor";
		var iconColor = alivideo_designer.setDataRule(component,prop,val);
		if(component.find("i").length>0){
			component.find("i").removeClass(iconColor);
			component.find("i").addClass(val);	
		}
	},
	setIconSize:function(component,val){
		var prop = "iconSize"; 
		alivideo_designer.setDataRule(component,prop,val);
		if(component.find("i").length>0){
			if (val == '') {
				component.find("i").css("font-size", '');
			} else {
				component.find("i").css("font-size", val + "px");
			}
		}
		
	},
	setIconAlign:function(component,val){
		var prop = "iconAlign"; 
		alivideo_designer.setDataRule(component,prop,val);
		
		if(component.find("i").length>0){
			var $i = component.find("i");
			if(val=="left"){
				component.find("p").css("display","inline");
				$i.remove();
				component.prepend($i);
			}else if(val=="right"){
				component.find("p").css("display","inline");
				$i.remove();
				component.append($i);
			}else if(val=="top"){
				component.find("p").css("display","");
				$i.remove();
				component.prepend($i);	
				$i.css("padding-top",parseInt($i.css("font-size").replace("px",""))/3);
				$i.css("padding-bottom",parseInt($i.css("font-size").replace("px",""))/3);
			}else if(val=="bottom"){
				component.find("p").css("display","");
				$i.remove();
				component.append($i);		
				$i.css("padding-top",parseInt($i.css("font-size").replace("px",""))/3);
				$i.css("padding-bottom",parseInt($i.css("font-size").replace("px",""))/3);
			}
		}
	},
	setIconRoll:function(component,val){
		var prop = "iconRoll"; 
		alivideo_designer.setDataRule(component,prop,val);
		if(component.find("i").length>0){
			if(val=="no"){
				component.find("i").removeClass("fa-spin");
			}else{
				component.find("i").addClass("fa-spin");
			}
		}
	},
	/**
	*���ñ���ɫ
	*/
	setImage:function (component,val){
		
		var prop = "image";
		alivideo_designer.setDataRule(component,prop,val); 
		if(component.find("img").length){
			component.find("img").attr("src",contextPath+val);
		}else{
			var $img =$('<img class="img-responsive"/>');
			if(val==''){
			   $img.attr("src",'');
			}else{
			   $img.attr("src",contextPath+val);
			}
			component.prepend($img);
		}
		
	},
	initImage:function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#backgroundImage1").val(value);
		}
		return $temp[0];
	},
	setImageAlign:function(component,val){
		var prop = "imageAlign"; 
		alivideo_designer.setDataRule(component,prop,val);
		
		component.find("p").css("position","");
		component.find("p").css("bottom","");
		if(component.find("img").length>0){
			var $img = component.find("img");
			if(val=="top"){
				component.find("p").css("display","");
				$img.remove();
				component.prepend($img);	
			}else if(val=="bottom"){
				component.find("p").css("display","");
				$img.remove();
				component.append($img);
			}else if(val=="inner"){
				component.find("p").css("position","absolute");
				component.find("p").css("bottom","0px");
				component.find("p").css("width","100%");
			}
		}
	},
	setImageRadius:function(component,val){
	
		var prop = "imageRadius"; 
		alivideo_designer.setDataRule(component,prop,val);
		if(component.find("img").length>0){
			alivideo_designer.setStyle(component.find("img")[0],"border-radius:"+val+"px!important");
		}else{
			alivideo_designer.setStyle(component.find("img")[0],"border-radius:0px!important");
		}
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
	}

}
