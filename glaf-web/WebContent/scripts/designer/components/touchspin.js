/**
 * textareabt
 */

var touchspin_designer = {
	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		var prop = "name";
		touchspin_designer.setDataRule(component,prop,val);
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
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = component.find("input");
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
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var $component = $(component);
		if (val == '') {
			$component.css("margin-" + direct, "");
		} else {
			$component.css("margin-" + direct, val + "px");
		}
	},
	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		var $component = $(component);
		$component.css("position", "relative");
		if (val == '') {
			$component.css(direct, '');
		} else {
			$component.css(direct, val + "px");
		}
	},

	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var $component = $(component);
		var prop = "position";
		touchspin_designer.setDataRule(component,prop,val);
		$component.css("position", val);

	},
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		var prop = "width";
		touchspin_designer.setDataRule(component,prop,val);
//		var obj = component.find("input");
//		obj.removeClass("input-medium input-lg input-sm input-xs");
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + "px");
		}

	},

	/**
	 * 设置高度
	 */
//	setHeight : function(component, val) {
//		var prop = "height";
//		touchspin_designer.setDataRule(component,prop,val);
//		var div = component.find("div.input-group");
//		var input = component.find("input");
//		input.removeClass("input-lg input-sm input-xs");
//		div.removeClass("input-group-lg input-group-sm input-group-xs");
//		switch (val) {
//			case 'input-group-lg' :
//				div.addClass("input-group-lg");
//				input.addClass('input-lg');	
//				break;
//			case 'input-group-sm' :
//				div.addClass("input-group-sm");
//				input.addClass('input-sm');
//				break;
//			case 'input-group-xs' :
//				div.addClass("input-group-xs");
//				input.addClass('input-xs');
//		}
//	}
	setHeight : function(component, val) {
		var prop = "height";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		var btn_down = component.find("button.bootstrap-touchspin-down");
		var btn_up = component.find("button.bootstrap-touchspin-up");
		if(component.find("span.input-group-btn-vertical").length){
			if (val == '') {
				btn_down.css("height", '');
				input.css("height", '');
				btn_up.css("height", '');
			} else {
				btn_down.css("height", (parseFloat(val)+1)/2 + "px");
				input.css("height", val + "px");
				btn_up.css("height", (parseFloat(val)+1)/2 + "px");
			}
		}else{
			if (val == '') {
				btn_down.css("height", '');
				input.css("height", '');
				btn_up.css("height", '');
			} else {
				btn_down.css("height", val + "px");
				input.css("height", val + "px");
				btn_up.css("height", val + "px");
			}
		}
		
	},
	
	setSize:function(component, val){

		var prop = "size";
		touchspin_designer.setDataRule(component,prop,val);
		component.removeClass(" hmtd-xs");
		component.addClass(val);
	
	},
		/**
		*设置背景色
		*/
	setBackgroundColorLeft:function (component,val){
		var prop = "backgroundColorLeft";
		touchspin_designer.setDataRule(component,prop,val);
		var btn_down = component.find("button.bootstrap-touchspin-down");
		if(val==''){
		  btn_down.css("background-color",'');
		}else{
		  btn_down.css("background-color",val);
		}
	},
	setBackgroundColorRight:function (component,val){
		var prop = "backgroundColorRight";
		touchspin_designer.setDataRule(component,prop,val);
		var btn_up = component.find("button.bootstrap-touchspin-up");
		if(val==''){
		  btn_up.css("background-color",'');
		}else{
		  btn_up.css("background-color",val);
		}
	},
	setBtnType:function(component,val){
		var prop = "btnType";
		touchspin_designer.setDataRule(component,prop,val);
		var btn_down = component.find("button.bootstrap-touchspin-down");
		var btn_up = component.find("button.bootstrap-touchspin-up");
		if(component.find("span.input-group-btn-vertical").length){
			if(val=="<>"){
				btn_down.find("i").attr("class","glyphicon glyphicon-chevron-down");
				btn_up.find("i").attr("class","glyphicon glyphicon-chevron-up");			
			}else{
				btn_down.find("i").attr("class","glyphicon glyphicon-minus");
				btn_up.find("i").attr("class","glyphicon glyphicon-plus");
			}
		}else{
			if(val=="<>"){
				btn_down.html("<");
				btn_up.html(">");
			}else{
				btn_down.html("-");
				btn_up.html("+");
			}
		}
	},
	/**
	 * 设置边框宽度
	 */
	setBorderTopWidth : function(component, val) {
		var prop = "borderTopWidth";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth";
		touchspin_designer.setDataRule(component,prop,val);
		touchspin_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var input = component.find("input");
		var btn_down = component.find("button.bootstrap-touchspin-down");
		var btn_up = component.find("button.bootstrap-touchspin-up");
   		var span_prefix = component.find("span.bootstrap-touchspin-prefix");  
   		var span_post = component.find("span.bootstrap-touchspin-postfix"); 
   		
   		if(direct=="top"||direct=="bottom"){
   			if (val == '') {
				btn_down.css("border-" + direct + "-width", '');
				span_prefix.css("border-" + direct + "-width", '');
				input.css("border-" + direct + "-width", '');				
				span_post.css("border-" + direct + "-width", '');
				btn_up.css("border-" + direct + "-width", '');
			} else {
				btn_down.css("border-" + direct + "-width", val + "px");
				span_prefix.css("border-" + direct + "-width", val + "px");
				input.css("border-" + direct + "-width", val + "px");				
				span_post.css("border-" + direct + "-width", val + "px");
				btn_up.css("border-" + direct + "-width", val + "px");
			}
   		}else if(direct=="left"){
   			if (val == '') {
				btn_down.css("border-" + direct + "-width", '');
			} else {
				btn_down.css("border-" + direct + "-width", val + "px");
			}
   		}else if(direct=="right"){
   			if (val == '') {
				btn_up.css("border-" + direct + "-width", '');
			} else {
				btn_up.css("border-" + direct + "-width", val + "px");
			}
   		}
		
	},

	/**
	 * 设置边框颜色
	 */
	setBorderColor : function(component, val) {
		var prop = "borderColor";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		var btn_down = component.find("button.bootstrap-touchspin-down");
		var btn_up = component.find("button.bootstrap-touchspin-up");
   		var span_prefix = component.find("span.bootstrap-touchspin-prefix");  
   		var span_post = component.find("span.bootstrap-touchspin-postfix"); 
   		
   		if (val == '') {
			btn_down.css("border-color", '');
			span_prefix.css("border-color", '');
			input.css("border-color", '');
			span_post.css("border-color", '');
			btn_up.css("border-color", '');
		} else {
			btn_down.css("border-color",val);
			span_prefix.css("border-color",val);
			input.css("border-color",val);
			span_post.css("border-color",val);
			btn_up.css("border-color", val);
		}
   		
	},

	/**
	 * 设置边框样式
	 */
	setBorderStyle : function(component, val) {
		var prop = "borderStyle";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		var btn_down = component.find("button.bootstrap-touchspin-down");
		var btn_up = component.find("button.bootstrap-touchspin-up");
   		var span_prefix = component.find("span.bootstrap-touchspin-prefix");  
   		var span_post = component.find("span.bootstrap-touchspin-postfix"); 
   		if (val == '') {
			btn_down.css("border-style", '');
			span_prefix.css("border-style", '');
			input.css("border-style", '');
			span_post.css("border-style", '');
			btn_up.css("border-style", '');
		} else {
			btn_down.css("border-style",val);
			span_prefix.css("border-style",val);
			input.css("border-style",val);
			span_post.css("border-style",val);
			btn_up.css("border-style", val);
		}
	},
	/**
	 * 设置圆边
	 */
	setCircleStyle : function(component, val) {
		var prop = "circleStyle";
		touchspin_designer.setDataRule(component,prop,val);
		var btn_down = component.find("button.bootstrap-touchspin-down");
		var btn_up = component.find("button.bootstrap-touchspin-up");
		if(component.find("span.input-group-btn-vertical").length){
		
		}else{
			if (val == 'display') {
				btn_down.addClass("btn-circle-left");
				btn_up.addClass("btn-circle-right");
			} else {
				btn_down.removeClass("btn-circle-left");
				btn_up.removeClass("btn-circle-right");
			}
		}
		
	},

	/**
	 * 设置字体
	 */
	setFontFamily : function(component, val) {
		var prop = "fontFamily";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		if (val == '') {
			input.css("font-family", '');
		} else {
			input.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		if (val == '') {
			input.css("color", '');
		} else {
			input.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		if (val == 'italic') {
			if ($(this).hasClass('active')) {
				input.css("font-style", 'italic');
			} else {
				input.css("font-style", '');
			}
		} else if (val == 'bold') {
			if ($(this).hasClass('active')) {
				input.css("font-weight", 'bold');
			} else {
				input.css("font-weight", '');
			}
		}
	},
	/**
	 * 设置字体间距
	 */
	setLetterSpacing : function(component, val) {
		var prop = "letterSpacing";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		if (val == '') {
			input.css("letter-spacing", '');
		} else {
			input.css("letter-spacing", val + "px");
		}
	},
	/**
	 * 设置字体大小
	 */
	setFontSize : function(component, val) {
		var prop = "fontSize";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		if (val == '') {
			input.css("font-size", '');
		} else {
			input.css("font-size", val + "px");
		}
	},
	/**
	 * 设置文本缩进
	 */
	setTextIndent : function(component, val) {
		var prop = "textIndent";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		if (val == '') {
			input.css("text-indent", '');
		} else {
			input.css("text-indent", val + "px");
		}
	},
	/**
	 * 文本水平对齐
	 */
	setTextAlign : function(component, val) {
		var prop = "textAlign";
		touchspin_designer.setDataRule(component,prop,val);
		var input = component.find("input");
		if (val == '') {
			input.css("text-align", '');
		} else {
			input.css("text-align", val);
		}
	},
	/**
	 * 文本垂直对齐
	 */
	// function setVerticalAlign(component,val){
	// var obj=getBodyObject(component);
	// obj.css("vertical-align",val);
	// }

	
	setDataRule : function(component,prop,val){
		
		var lastVal = touchspin_designer.getDataRule(component,prop)
		
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
