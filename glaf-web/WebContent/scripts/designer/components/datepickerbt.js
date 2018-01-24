/**
 * datepickerbt
 */

var datepickerbt_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var prop = "name";
		datepickerbt_designer.setDataRule(component,prop,val);
		var $component = $(component);
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
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			if (val == '') {
				obj.css("padding-" + direct, "");
			} else {
				obj.css("padding-" + direct, val + "px");
			}
		} else {
			var input = obj.find("input");
			if (val == '') {
				input.css("padding-" + direct, "");
			} else {
				input.css("padding-" + direct, val + "px");
			}
		}
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = datepickerbt_designer.getBodyObject(component);
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
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		
		var obj = datepickerbt_designer.getBodyObject(component);
		
		if (obj.attr("type") == "text") {
			component.css("position","relative");
			if (val == '') {
				component.css(direct, '');
			} else {
				component.css(direct, val + "px");
			}
		} else {
			if (val == '') {
				obj.css(direct, '');
			} else {
				obj.css(direct, val + "px");
			}
		}
	},

	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var prop = "positon";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			component.css("position", val);
		} else {
			obj.css("position", val);
		}
	},

	/**
	 * 设置边框宽度
	 */
	setBorderTopWidth : function(component, val) {
		var prop = "borderTopWidth";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth";
		datepickerbt_designer.setDataRule(component,prop,val);
		datepickerbt_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			if (val == '') {
				obj.css("border-" + direct + "-width", '');
			} else {
				obj.css("border-" + direct + "-width", val + "px");
			}
		} else {
			var input = obj.find("input");
			var btn = obj.find("button");
			if (val == '') {
				input.css("border-" + direct + "-width", '');
				btn.css("border-" + direct + "-width", '');
			} else {
				if (direct == "left") {
					input.css("border-" + direct + "-width", val + "px");
				} else if (direct == "right") {
					btn.css("border-" + direct + "-width", val + "px");
				} else {
					input.css("border-" + direct + "-width", val + "px");
					btn.css("border-" + direct + "-width", val + "px");
				}
			}
		}

	},

	/**
	 * 设置边框颜色
	 */
	setBorderColor : function(component, val) {
		var prop = "borderColor";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			if (val == '') {
				obj.css("border-color", '');
			} else {
				obj.css("border-color", val);
			}
		} else {
			var input = obj.find("input");
			var btn = obj.find("button");
			if (val == '') {
				input.css("border-color", '');
				btn.css("border-color", '');
			} else {
				input.css("border-color", val);
				btn.css("border-color", val);
			}
		}

	},

	/**
	 * 设置边框样式
	 */
	setBorderStyle : function(component, val) {
		var prop = "borderStyle";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);

		if (obj.attr("type") == "text") {
			if (val == '') {
				obj.css("border-style", '');
			} else {
				obj.css("border-style", val);
			}
		} else {
			var input = obj.find("input");
			var btn = obj.find("button");
			if (val == '') {
				input.css("border-style", '');
				btn.css("border-style", '');
			} else {
				input.css("border-style", val);
				btn.css("border-style", val);
			}
		}

	},
	/**
	 * 设置圆边
	 */
	setCircleStyle : function(component, val) {
		var prop = "circleStyle";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			if (val == 'display') {
				obj.addClass('input-circle');
			} else {
				obj.removeClass('input-circle');
			}
		} else {
			var btn = obj.find("button");
			var input = obj.find("input");
			if (val == 'display') {
				input.addClass('input-circle-left');
				btn.addClass('btn-circle-right');
			} else {
				input.removeClass('input-circle-left');
				btn.removeClass('btn-circle-right');
			}

		}
	},

	/**
	 * 设置宽度
	 */
	setWidth : function(component, val,unit) {
		 
		var prop = "width";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			obj.removeClass("input-medium input-lg input-sm input-xs");
		} else {
			obj.removeClass("input-medium input-group-lg input-group-sm input-group-xs");
		}

		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + unit);
		}
	},

	/**
	 * 设置高度
	 */
	setSize : function(component, val) {
		var prop = "size";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			obj.removeClass("input-lg input-sm input-xs hmtd-xs");

			switch (val) {
				case 'input-group-lg' :
					obj.addClass('input-lg');
					break;
				case 'input-group-sm' :
					obj.addClass('input-sm');
					break;
				case 'input-group-xs' :
					obj.addClass('input-xs');
					break;
				case 'hmtd-xs':
					obj.addClass('hmtd-xs');
			}

		} else {
			obj.removeClass("input-group-lg input-group-sm input-group-xs hmtd-xs");
			obj.addClass(val);
		}

	},
	/**
	 * 文本水平对齐
	 */
	setTextAlign : function(component, val) {
		var prop = "textAlign";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			obj.css("text-align", val);
		} else {
			var input = obj.find("input");
			input.css("text-align", val);
		}
	},
	/**
	 * 文本垂直对齐
	 */
	setVerticalAlign : function(component, val) {
		var prop = "verticalAlign";
		datepickerbt_designer.setDataRule(component,prop,val);
		var obj = datepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			obj.css("vertical-align", val);
		} else {
			var input = obj.find("input");
			input.css("vertical-align", val);
		}
	},
	getBodyObject : function(component) {
		var mainBody;
		if (component.hasClass("datepickerbt")) {
			mainBody = component;
		} else {
			mainBody = component.find(".datepickerbt");
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = datepickerbt_designer.getDataRule(component,prop)
		
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
	 * 设置禁用颜色
	 */
	setDisabledColor: function(component, val,unit){
		var prop = "disabledColor"; 
		datepickerbt_designer.setDataRule(component,prop,val,unit);
		datepickerbt_designer.updateStyleByKey(component,'disabledColor',val);
	},
	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = datepickerbt_designer.getStyler(component);
		styler[key] = val;
		datepickerbt_designer.updateStyler(component,styler);
		datepickerbt_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = datepickerbt_designer.getStylerId(component);
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
		var sid = datepickerbt_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _disabled_input = _id + ' input[readonly]{';
		if(styler['disabledColor']){
			_disabled_input += 'background-color:' + styler['disabledColor'] + ";";
		}
		_disabled_input += "}"
		
		_default += _disabled_input;

		var stylebox = datepickerbt_designer.getStyleBox(component);
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
