/**
 * textareabt
 */

var textareabt_designer = {
	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		var prop = "name";
		textareabt_designer.setDataRule(component,prop,val);
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
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = textareabt_designer.getBodyObject(component);

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
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setMargin(component, "right", val);
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
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setPositon(component, 'right', val);
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
		textareabt_designer.setDataRule(component,prop,val);
		$component.css("position", val);

	},
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		var prop = "width";
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
		obj.removeClass("input-medium input-lg input-sm input-xs");
		if (val == '') {
			obj.css("width", '');
		} else {
			obj.css("width", val + "px");
		}

	},

	/**
	 * 设置高度
	 */
	setHeight : function(component, val) {
		var prop = "height";
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth";
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var obj = textareabt_designer.getBodyObject(component);

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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
		if (val == '') {
			obj.css("font-family", '');
		} else {
			obj.css("font-family", val);
		}
	},
	setFontColor : function(component, val) {
		var prop = "fontColor";
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
		if (val == '') {
			obj.css("color", '');
		} else {
			obj.css("color", val);
		}
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle";
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);
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
		textareabt_designer.setDataRule(component,prop,val);
		var obj = textareabt_designer.getBodyObject(component);

		obj.css("text-align", val);

	},
	/**
	 * 文本垂直对齐
	 */
	// function setVerticalAlign(component,val){
	// var obj=getBodyObject(component);
	// obj.css("vertical-align",val);
	// }

	getBodyObject : function(component) {
		var mainBody;
		if (component.find("textarea").length) {
			mainBody = $(component.find("textarea"));
		} else {
			mainBody = $(component);
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = textareabt_designer.getDataRule(component,prop)
		
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
		textareabt_designer.setDataRule(component,prop,val);
		textareabt_designer.updateStyleByKey(component,prop,val);
		// if (val == '') {
		// 	component.removeAttr("disabledColor");
		// } else {
		// 	component.attr("disabledColor",val);
		// }
	},
	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = textareabt_designer.getStyler(component);
		styler[key] = val;
		textareabt_designer.updateStyler(component,styler);
		textareabt_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = textareabt_designer.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return $.parseJSON(styler.val());
		}
	},
	getStylerId: function(component){
		return component.attr('id') +'_styler_hidden';
	},
	updateStyler: function (component,styler){
		var sid = textareabt_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _default_textarea_disabled = _id + " textarea[disabled]{";
		if(styler['disabledColor']){
			_default_textarea_disabled += 'background-color:'+styler['disabledColor']+";";
		}
		_default_textarea_disabled += "}"
		
		_default += _default_textarea_disabled;

		var stylebox = textareabt_designer.getStyleBox(component);
		stylebox.html(_default);
	},
	/**
	 * 获取<style id> 对象
	 */
	getStyleBox: function (component){
		var sid = component.attr('id') + '_styler_init';
		var box = component.find('#'+sid);
		if(!box.length){
			component.append("<style id=\""+sid+"\"></style>");
		}
		return component.find('#'+sid);
	},
}
