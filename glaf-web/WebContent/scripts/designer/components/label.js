/**
 * label
 */
var label_designer = {

	/**
	 * 设置控件名称
	 */
	setLabelName : function(component, val) {
		var prop = "name";
		label_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.attr("cname", "");
		} else {
			component.attr("cname", val+"_标签");
		}
	},

	/**
	 * 设置文本内容
	 */
	setContent : function(component, val) {
		var prop = "content";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		if (val == '') {
			obj.html("");
		} else {
			obj.html(val);
		}
	},

	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		label_designer.setDataRule(component,prop,val);
		label_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		label_designer.setDataRule(component,prop,val);
		label_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		label_designer.setDataRule(component,prop,val);
		label_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		label_designer.setDataRule(component,prop,val);
		label_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		if (val == '') {
			component.css("margin-" + direct, "");
		} else {
			component.css("margin-" + direct, val + "px");
		}
	},
	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		label_designer.setDataRule(component,prop,val);
		label_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		label_designer.setDataRule(component,prop,val);
		label_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		label_designer.setDataRule(component,prop,val);
		label_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		label_designer.setDataRule(component,prop,val);
		label_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
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
		label_designer.setDataRule(component,prop,val);
		component.css("position", val);
	},
	/**
	 * 水平对齐
	 */
	setObjTextAlign : function(component, val) {
		var prop = "objTextAlign";
		label_designer.setDataRule(component,prop,val);
	    component.css("text-align", val);
	},
	/**
	 * 垂直对齐
	 */
	setObjVerticalAlign : function(component, val) {
		var prop = "objVerticalAlign";
		label_designer.setDataRule(component,prop,val);
		component.css("vertical-align", val);
	},
	
	/**
	 * 常规尺寸
	 */
	setSize: function (component, val){
		var prop = "size"; 
		label_designer.setDataRule(component,prop,val);
		if ($(this).hasClass('active')) {
			component.removeClass("hmtd-xs");
			component.addClass(val);
		} else {
			component.removeClass("hmtd-xs");
		}

	},
	/**
	 * 设置背景色
	 */
	setBackgroundColor : function(component, val) {
		var prop = "backgroundColor";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		if (val == '') {
			obj.css("background-color", '');
		} else {
			obj.css("background-color", val);
		}
	},
	/**
	 * 设置主题
	 * 
	 * @param {}
	 *            component
	 * @param {}
	 *            val
	 */
	setTheme : function(component, val) {
		var prop = "theme";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		obj.removeClass("label-default label-primary label-success label-info label-warning label-danger");
		obj.css("background-color", "");
		if (val == "no-bgc") {
			obj.removeClass("label");
		} else {
			obj.addClass("label");
			obj.addClass(val);
		}
	},
	/**
	 * 设置透明度
	 */
	setTransparency : function(component, val) {
		var prop = "transparency";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		if (val == '') {
			obj.css("filter", "");
			obj.css("-moz-opacity", "");
			obj.css("-khtml-opacity", "");
			obj.css("opacity", "");
		} else {
			obj.css("filter", "alpha(opacity=" + val + ")");
			obj.css("-moz-opacity", val / 100);
			obj.css("-khtml-opacity", val / 100);
			obj.css("opacity", val / 100);
		}
	},
	/**
	 * 设置字体
	 */
	setFontFamily : function(component, val) {
		var prop = "fontFamily";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-family", '');
		} else {
			obj.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		if (val == '') {
			obj.css("color", '');
		} else {
			obj.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		debugger
		var prop = "fontStyle";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		if (val == 'italic') {
			if ($(this).hasClass('active')) {
				var utal = obj.css("font-style")
				if(utal != 'italic'){
					obj.css("font-style", 'italic');
				}else if(utal == 'italic'){
					obj.css("font-style", '');
				}
			} 
		} else if (val == 'bold') {
			if ($(this).hasClass('active')) {
				var bol = obj.css("font-weight")
				if(bol != 'bold'){
					obj.css("font-weight", 'bold');
				}else if(bol == 'bold'){
					obj.css("font-weight", '');
				}
			} 
		}
		else if (val == 'underline') {
			if ($(this).hasClass('active')) {
				var under  = obj.css("text-decoration");
				if(under[0]== 'n'){
					obj.css("text-decoration", 'underline');
				}else if(under[0]== 'u'){
					obj.css("text-decoration", '');
				}
			}
		}
	},
	/**
	 * 设置字体间距
	 */
	setLetterSpacing : function(component, val) {
		var prop = "letterSpacing";
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
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
		label_designer.setDataRule(component,prop,val);
		var obj = label_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-size", '');
		} else {
			obj.css("font-size", val + "px");
		}
	},

	getBodyObject : function(component) {
		var mainBody;
		if (component.find("span").length) {
			mainBody = component.find("span");
		} else if (component.find("label").length) {
			mainBody = component.find("label");
		} else{
			mainBody = component;
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = label_designer.getDataRule(component,prop)
		
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

	selectIconPage:function(o){
		var $this = $(o);
		BootstrapDialog.show({
			title : '选择图标',
			size : BootstrapDialog.SIZE_WIDE,
			message : "<iframe id='font_icon'  width='100%' height='600' src='"+contextPath+"/scripts/plugins/bootstrap/textbox/font_icons.html?fn=iconCallBack&targetId=iconClass' frameborder='no' scrolling='yes'></iframe>",
			css : {
				width : $(window).width() * 0.6
			},
			modal : false //遮蔽层
		});
	
	},
	setIconContent : function(component,val){
		var $component = $(component);
		var xsClass = "hmtd-xs",
			hasXs = $component.hasClass(xsClass);

		var prop = "iconText";
		label_designer.setDataRule(component,prop,val);

		$component.find(">span").css("display","inline-block")

		if($component.find(".label-abon")[0]){
			$component.find(".label-abon").text(val);
		}else{
			var $div = $("<span class = \"label-abon\" style='vertical-align: middle;'></span>");
			if(hasXs){
				$div.addClass(xsClass);
			}
			$div.text(val);
			$component.append($div);
			$icon = $("<i></i>");
			$icon.addClass("");			
			$div.prepend($icon);	
		}	
	},
	setIconClass : function(component,val){
		var $component = $(component);
		var xsClass = "hmtd-xs",
			hasXs = $component.hasClass(xsClass);
		var prop = "iconClass";
		var iconClass = label_designer.setDataRule(component,prop,val);

		$component.find(">span").css("display","inline-block")

		if($component.find(".label-abon")[0]){
			$component.closest("div").find("i").removeClass(iconClass);
			$component.closest("div").find("i").addClass(val);				
		}else{
			var $div = $("<span class = \"label-abon\"></span>");
			if(hasXs){
				$div.addClass(xsClass);
			}
			$component.append($div);
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
		var iconColor = label_designer.setDataRule(component,prop,val);
		var $labelAbon = component.find(".label-abon");

		$labelAbon.removeClass(iconColor);
		$labelAbon.addClass(val);	
		$labelAbon.find("i").removeClass(iconColor);
		$labelAbon.find("i").addClass(val);	
	},
	setIconSize:function(component,val){
		var prop = "iconSize"; 
		label_designer.setDataRule(component,prop,val);
		var $labelAbon = component.find(".label-abon");
		if (val == '') {
			$labelAbon.css("font-size","");
			$labelAbon.find("i").css("font-size", '');
		} else {
			$labelAbon.css("font-size",val + "px");
			$labelAbon.find("i").css("font-size", val + "px");
		}

	},
	setIconAlign:function(component,val){
		var prop = "iconAlign"; 
		label_designer.setDataRule(component,prop,val);
		var $labelAbon = component.find(".label-abon");
		if(val == "left"){
			component.prepend($labelAbon);
		}else{
			component.append($labelAbon);
		}
	},

	
}
