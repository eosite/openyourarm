/**
 * datetimepickerbt
 */

var datetimepickerbt_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var prop = "name";
		datetimepickerbt_designer.setDataRule(component,prop,val);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = datetimepickerbt_designer.getBodyObject(component);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = datetimepickerbt_designer.getBodyObject(component);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setPositon(component, 'right', val);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		component.css("position", val);

	},

	/**
	 * 设置边框宽度
	 */
	setBorderTopWidth : function(component, val) {
		var prop = "borderTopWidth";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		datetimepickerbt_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var obj = datetimepickerbt_designer.getBodyObject(component);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		var obj = datetimepickerbt_designer.getBodyObject(component);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		var obj = datetimepickerbt_designer.getBodyObject(component);

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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		var obj = datetimepickerbt_designer.getBodyObject(component);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		var obj = datetimepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			obj.removeClass("input-medium input-lg input-sm input-xs");
		} else {
			obj
					.removeClass("input-medium input-group-lg input-group-sm input-group-xs");
		}

		if (val == '') {
			obj.css("width", '');
		} else {
			obj.css("width", val + unit);
		}
	},
	/**
	 * 设置高度
	 */
	setSize : function(component, val) {
		var prop = "size";
		datetimepickerbt_designer.setDataRule(component,prop,val);
		var obj = datetimepickerbt_designer.getBodyObject(component);
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
					obj.addClass("hmtd-xs");
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		var obj = datetimepickerbt_designer.getBodyObject(component);
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
		datetimepickerbt_designer.setDataRule(component,prop,val);
		var obj = datetimepickerbt_designer.getBodyObject(component);
		if (obj.attr("type") == "text") {
			obj.css("vertical-align", val);
		} else {
			var input = obj.find("input");
			input.css("vertical-align", val);
		}
	},
	getBodyObject : function(component) {
		var mainBody;
		if (component.find(".form_datetime").length) {
			mainBody = component.find(".datepicker");
		} else {
			mainBody = component;
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = datetimepickerbt_designer.getDataRule(component,prop)
		
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
