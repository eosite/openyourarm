/**
 * textboxbt
 */
var searchbar_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		searchbar_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	/**
	 * 设置提示名称
	 */
	setPlaceholder : function(component, val) {
		var $component = $(component);
		
		var prop = "placeholder";
		searchbar_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("placeholder", "");
		} else {
			$component.attr("placeholder", val);
		}
	},
	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = searchbar_designer.getBodyObject(component);

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
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = searchbar_designer.getBodyObject(component);
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
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setPositon(component, 'right', val);
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
		searchbar_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		
		var prop = "width";
		searchbar_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + "px");
		}

	},
	/**
	 * 设置高度
	 */
	setHeight : function(component, val) {
		var prop = "height";
		searchbar_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("height", '');
		} else {
			component.css("height", val + "px");
		}
	},
	setSize:function(component, val){

		var prop = "size";
		searchbar_designer.setDataRule(component,prop,val);
		component.removeClass("input-lg input-sm input-xs hmtd-xs");
		var $parent = component.parent();
		if($parent.length){
			$parent.removeClass("input-lg input-sm input-xs hmtd-xs");
		}
		if(val === "hmtd-xs" && $parent.hasClass("input-icon")){
			$parent.addClass(val);
		}else{
			component.addClass(val);
		}
	},
	/**
	 * 设置边框宽度
	 */
	setBorderTopWidth : function(component, val) {
		var prop = "borderTopWidth";
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";    
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";  
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth"; 
		searchbar_designer.setDataRule(component,prop,val);
		searchbar_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var obj = searchbar_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			if (val == '') {
				obj.css("border-" + direct + "-width", '');
			} else {
				obj.css("border-" + direct + "-width", val + "px");
			}
		}

	},

	/**
	 * 设置边框颜色
	 */
	setBorderColor : function(component, val) {
		var prop = "borderColor"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		component.removeClass("has-success");
		if (val == '') {
			obj.css("border-color", '');
		} else {
			obj.css("border-color", val);
		}
	},

	/**
	 * 设置边框样式
	 */
	setBorderStyle : function(component, val) {
		var prop = "borderStyle"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == '') {
			obj.css("border-style", '');
		} else {
			obj.css("border-style", val);
		}  

	},
	/**
	 * 设置圆边
	 */
	setCircleStyle : function(component, val) {
		var prop = "circleStyle"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == 'display') {
			obj.addClass('input-circle');
		} else {
			obj.removeClass('input-circle');
		}
	},

	
	/**
	 * 设置字体
	 */
	setFontFamily : function(component, val) {
		var prop = "fontFamily"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-family", '');
		} else {
			obj.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == '') {
			obj.css("color", '');
		} else {
			obj.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == 'italic') {
			if ($(this).hasClass('active')) {
				obj.css("font-style", 'italic');
			} else {
				obj.css("font-style", '');
			}
		} else if (val == 'bold') {
			if ($(this).hasClass('active')) {
				obj.css("font-weight", 'bold');
			} else {
				obj.css("font-weight", '');
			}
		}
	},
	/**
	 * 设置字体间距
	 */
	setLetterSpacing : function(component, val) {
		var prop = "letterSpacing"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == '') {
			obj.css("letter-spacing", '');
		} else {
			obj.css("letter-spacing", val + "px");
		}
	},
	/**
	 * 设置字体大小
	 */
	setFontSize : function(component, val) {
		var prop = "fontSize"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-size", '');
		} else {
			obj.css("font-size", val + "px");
		}
	},
	/**
	 * 设置文本缩进
	 */
	setTextIndent : function(component, val) {
		var prop = "textIndent"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		if (val == '') {
			obj.css("text-indent", '');
		} else {
			obj.css("text-indent", val + "px");
		}
	},
	/**
	 * 文本水平对齐
	 */
	setTextAlign : function(component, val) {
		var prop = "textAlign"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);

		obj.css("text-align", val);

	},
	/**
	 * 文本垂直对齐
	 */
	setVerticalAlign : function(component, val) {
		var prop = "verticalAlign"; 
		searchbar_designer.setDataRule(component,prop,val);
		var obj = searchbar_designer.getBodyObject(component);
		obj.css("vertical-align", val);
	},
	selectIconPage:function(o){
		var $this = $(o);
		BootstrapDialog.show({
			title : '选择图标',
			size : BootstrapDialog.SIZE_WIDE,
			message : "<iframe id='font_icon'  width='100%' height='600' src='"+contextPath+"/scripts/plugins/bootstrap/textbox/font_icons.html?fn=iconCallBack&targetId=iconClass' frameborder='no' scrolling='yes'></iframe>",
//			buttons : [
//			     {
//			    	label: '确定',
//		            cssClass: 'btn-success',
//		            action: function(d){
//		            	//debugger;
//		            	var $doc = $("#font_icon").contents();
//		            	var iconClass = $doc.find("#tab_1").attr("iconclass");
//		            	if(iconClass){
//		            		$this.closest("div.input-group").find("i").attr("class","");
//		            		$this.closest("div.input-group").find("i").addClass(iconClass);
//		            		$this.closest("div.input-group").children().eq(1).html(iconClass);
//		            		//隐藏域存值
//		            		$this.closest("div.input-group").find("#icon-class").val(iconClass);
//		            		$this.closest("div.input-group").find("#icon-class").click();
//		            	}	
//		            }
//			     }, 
//			     {
//			    	label: '取消',
//		            cssClass: 'btn-warning',
//		            action: function(d){
//		            	d.close();
//		            }
//			     }
//			],
			css : {
				width : $(window).width() * 0.6
			},
			modal : false //遮蔽层
		});
	
	},
	setIconClass : function(component,val){
		var $component = $(component);
		var xsClass = "hmtd-xs",
			hasXs = $component.hasClass(xsClass);
		var prop = "iconClass";
		var iconClass = searchbar_designer.setDataRule(component,prop,val);
			
		if($component.closest("div").hasClass("input-icon")){
			$component.closest("div").find("i").removeClass(iconClass);
			$component.closest("div").find("i").addClass(val);				
		}else{
			var $div = $("<div class = \"input-icon\"></div>");
			if(hasXs){
				$div.addClass(xsClass);
			}
			$component.closest("div.view").append($div);
			$component.removeClass("nlayout_elem");
			$div.addClass("nlayout_elem");
			$div.prepend($component);
			$icon = $("<i></i>");
			$icon.addClass(val);			
			$div.prepend($icon);	
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
		var iconColor = searchbar_designer.setDataRule(component,prop,val);
		if(component.closest("div").hasClass("input-icon")){
			component.closest("div").find("i").removeClass(iconColor);
			component.closest("div").find("i").addClass(val);	
		}
	},
	setIconSize:function(component,val){
		var prop = "iconSize"; 
		searchbar_designer.setDataRule(component,prop,val);
		if(component.closest("div").hasClass("input-icon")){
			if (val == '') {
				component.closest("div").find("i").css("font-size", '');
			} else {
				component.closest("div").find("i").css("font-size", val + "px");
			}
		}
		
	},
	setIconAlign:function(component,val){
		var prop = "iconAlign"; 
		searchbar_designer.setDataRule(component,prop,val);
		if(component.closest("div").hasClass("input-icon")){
			if(val=="left"){
				component.closest("div").removeClass("right");
			}else{
				component.closest("div").addClass("right");				
			}
		}
	},
	setIconRoll:function(component,val){
		var prop = "iconRoll"; 
		searchbar_designer.setDataRule(component,prop,val);
		if(component.closest("div").hasClass("input-icon")){
			if(val=="no"){
				component.closest("div").find("i").removeClass("fa-spin");
			}else{
				component.closest("div").find("i").addClass("fa-spin");
			}
		}
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
		
		var lastVal = searchbar_designer.getDataRule(component,prop)
		
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
	setDisableColor : function(component,val){
		var prop = "disabledColor"; 
		searchbar_designer.setDataRule(component,prop,val);
		
		if (val == '') {
			component.removeAttr("disabledColor");
		} else {
			component.attr("disabledColor",val);
		}
	},
	setRadiu:function(component,val){
	     val=val+'% !important';
		component.css("cssText",'border-radius:'+val);
	}
}

