/**
 * login_username
 */
var login_username_designer = {

	/**
	 * 移除区域
	 */
	setRemove : function(component, val) {
		if (val == 'yes') {
			component.remove();
		}
	},
	setName : function(component,val){
		var prop = "name";
		login_username_designer.setDataRule(component,prop,val);
		var $component = $(component);
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
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		if (val == '') {
			component.css("margin-" + direct, "");
		} else {
			component.css("margin-" + direct, val + "px");
		}
	},
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		
		var prop = "width";
		login_username_designer.setDataRule(component,prop,val);
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
		var obj = login_username_designer.getBodyObject(component);
		var prop = "height";
		login_username_designer.setDataRule(component,prop,val);
		obj.removeClass("input-medium input-lg input-sm input-xs");
		if (val == '') {
			obj.css("height", '');
		} else {
			obj.css("height", val + "px");
		}
	},
	/**
	 * 设置边框宽度
	 */
	setBorderTopWidth : function(component, val) {
		var prop = "borderTopWidth";
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";    
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";  
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth"; 
		login_username_designer.setDataRule(component,prop,val);
		login_username_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var obj = login_username_designer.getBodyObject(component);
	
		if (val == '') {
			obj.css("border-" + direct + "-width", '');
		} else {
			obj.css("border-" + direct + "-width", val + "px");
		}
		

	},

	/**
	 * 设置边框颜色
	 */
	setBorderColor : function(component, val) {
		var prop = "borderColor"; 
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
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
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
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
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
		if (val == 'display') {
			obj.addClass('input-circle');
		} else {
			obj.removeClass('input-circle');
		}
	},
	
	setInfo : function(component, val) {
		var prop = "info";
		login_username_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.find("input").attr("placeholder","");
		} else {
			component.find("input").attr("placeholder",val);
		}
	},
	selectIcon:function(o){
		var $this = $(o);
		BootstrapDialog.show({
			title : '选择图标',
			size : BootstrapDialog.SIZE_WIDE,
			message : "<iframe id='font_icon'  width='100%' height='600' src='../../../scripts/plugins/bootstrap/textbox/font_icons.html' frameborder='no' scrolling='yes'></iframe>",
			buttons : [
			     {
			    	label: '确定',
		            cssClass: 'btn-success',
		            action: function(d){
		            	//debugger;
		            	var $doc = $("#font_icon").contents();
		            	var iconClass = $doc.find("#tab_1").attr("iconclass");
		            	if(iconClass){
		            		$this.closest("div.input-group").find("i").attr("class","");
		            		$this.closest("div.input-group").find("i").addClass(iconClass);
		            		$this.closest("div.input-group").children().eq(1).html(iconClass);
		            		//隐藏域存值
		            		$this.closest("div.input-group").find("#icon-class").val(iconClass);
		            		$this.closest("div.input-group").find("#icon-class").click();
		            	}	
		            }
			     }, 
			     {
			    	label: '取消',
		            cssClass: 'btn-warning',
		            action: function(d){
		            	d.close();
		            }
			     }
			],
			css : {
				width : $(window).width() * 0.6
			},
			modal : false //遮蔽层
		});
	
	},
	setIconClass : function(component,val){
		var $component = $(component);
		
		var prop = "iconClass";
		var iconClass = login_username_designer.setDataRule(component,prop,val);
		
		if($component.hasClass("form-group")){
			if($component.find("div").hasClass("input-icon")){
				$component.find("i").removeClass(iconClass);
				$component.find("i").addClass(val);				
			}else{
				var $div = $("<div class = \"input-icon\"></div>");
				$div.html($component.html());
				$icon = $("<i></i>");
				$icon.addClass(val);
				//debugger;
				//$icon.css("bottom","");//排除下划线文本框被设位置后图标上浮bug				
				$div.prepend($icon);
				$component.html($div);
			}	
		}else{
			if($component.hasClass("input-icon")){
				$component.find("i").removeClass(iconClass);
				$component.find("i").addClass(val);	
			}else{
				$component.addClass("input-icon");
				$icon = $("<i></i>");
				$icon.addClass(val);
				$component.prepend($icon);
			}
		}	
	},
	initIconClass: function(template,value){
		var template = $("<div class=\"input-group\"><i></i><span></span><span class=\"input-group-btn\"><button class=\"btn blue input-sm\" type=\"button\" onclick=\"javascript:login_username_designer.selectIcon(this);\" >...</button></span></div>");
		template.find("i").addClass(value);
		template.children().eq(1).html(value);
		return template;
	},
	setIconColor:function(component,val){
		var $component = $(component);
		
		var prop = "iconColor";
		var iconColor = login_username_designer.setDataRule(component,prop,val);
		$component.find("i").removeClass(iconColor);
		$component.find("i").addClass(val);	
		
	},
	setIconSize:function(component,val){
		var prop = "iconSize"; 
		login_username_designer.setDataRule(component,prop,val);
		var $component = $(component);
			if (val == '') {
				$component.find("i").css("font-size", '');
			} else {
				$component.find("i").css("font-size", val + "px");
			}
	},
	setIconAlign:function(component,val){
		var $component = $(component);
		var prop = "iconAlign"; 
		login_username_designer.setDataRule(component,prop,val);
		if($component.hasClass("form-group")){
			if(val=="left"){
				$component.find("div.input-icon").removeClass("right");
			}else{
				$component.find("div.input-icon").addClass("right");				
			}
		}else{
			if(val=="left"){
				$component.removeClass("right");
			}else{
				$component.addClass("right");				
			}
		}
	},
	setIconRoll:function(component,val){
		var $component = $(component);
		var prop = "iconRoll"; 
		login_username_designer.setDataRule(component,prop,val);
			if(val=="no"){
				$component.find("i").removeClass("fa-spin");
			}else{
				$component.find("i").addClass("fa-spin");
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
		
		var lastVal = login_username_designer.getDataRule(component,prop)
		
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
	 * 设置字体
	 */
	setFontFamily : function(component, val) {
		var prop = "fontFamily"; 
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-family", '');
		} else {
			obj.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor"; 
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
		if (val == '') {
			obj.css("color", '');
		} else {
			obj.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle"; 
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
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
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
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
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
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
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
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
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);

		obj.css("text-align", val);

	},
	/**
	 * 文本垂直对齐
	 */
	setVerticalAlign : function(component, val) {
		var prop = "verticalAlign"; 
		login_username_designer.setDataRule(component,prop,val);
		var obj = login_username_designer.getBodyObject(component);
		obj.css("vertical-align", val);
	},
	
	
}
