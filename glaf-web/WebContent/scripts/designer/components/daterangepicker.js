/**
 * daterangepicker
 */

var daterangepicker_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var prop = "name";
		daterangepicker_designer.setDataRule(component,prop,val);
		var $component = $(component);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	setKeyWord : function(component, val){
		var prop = "keyWord";
		daterangepicker_designer.setDataRule(component,prop,val);
		component.find(".input-group-addon").text(val);
		component.attr("keyWord",val);
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setMargin(component, "right", val);
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
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		
		component.css("position","relative");
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
		var prop = "positon";
		daterangepicker_designer.setDataRule(component,prop,val);
	
		component.css("position", val);
	
	},
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		var prop = "width";
		daterangepicker_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + "px");
		}
	},

	/**
	 * 设置高度
	 */
	setSize : function(component, val) {
		var prop = "size";
		daterangepicker_designer.setDataRule(component,prop,val);
		component.removeClass("input-group-lg input-group-sm input-group-xs hmtd-xs");
		component.addClass(val);
	},
	/**
	 * 设置边框宽度
	 */
	setBorderTopWidth : function(component, val) {
		var prop = "borderTopWidth";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setBorderWidth(component, 'top', val);
	},
	setBorderBottomWidth : function(component, val) {
		var prop = "borderBottomWidth";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setBorderWidth(component, 'bottom', val);
	},
	setBorderLeftWidth : function(component, val) {
		var prop = "borderLeftWidth";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setBorderWidth(component, 'left', val);
	},
	setBorderRightWidth : function(component, val) {
		var prop = "borderRightWidth";
		daterangepicker_designer.setDataRule(component,prop,val);
		daterangepicker_designer.setBorderWidth(component, 'right', val);
	},
	setBorderWidth : function(component, direct, val) {
		var firstInput = component.children().eq(0);
		var span = component.children().eq(1);
		var lastInput = component.children().eq(2);
		if (val == '') {
			firstInput.css("border-" + direct + "-width", '');
			span.css("border-" + direct + "-width", '');
			lastInput.css("border-" + direct + "-width", '');
		} else {
			if (direct == "left") {
				firstInput.css("border-" + direct + "-width", val + "px");
			} else if (direct == "right") {
				lastInput.css("border-" + direct + "-width", val + "px");
			} else {
				firstInput.css("border-" + direct + "-width", val + "px");
				span.css("border-" + direct + "-width", val + "px");
				lastInput.css("border-" + direct + "-width", val + "px");
			}
		}

	},
	/**
	 * 设置边框颜色
	 */
	setBorderColor : function(component, val) {
		var prop = "borderColor";
		daterangepicker_designer.setDataRule(component,prop,val);
		
		var firstInput = component.children().eq(0);
		var span = component.children().eq(1);
		var lastInput = component.children().eq(2);
		if (val == '') {
			firstInput.css("border-color", '');
			span.css("border-color", '');
			lastInput.css("border-color", '');
		} else {
			firstInput.css("border-color", val);
			span.css("border-color", val);
			lastInput.css("border-color", val);
		}
	},

	/**
	 * 设置边框样式
	 */
	setBorderStyle : function(component, val) {
		var prop = "borderStyle";
		daterangepicker_designer.setDataRule(component,prop,val);

		var firstInput = component.children().eq(0);
		var span = component.children().eq(1);
		var lastInput = component.children().eq(2);
		if (val == '') {
			firstInput.css("border-style", '');
			span.css("border-style", '');
			lastInput.css("border-style", '');
		} else {
			firstInput.css("border-style", val);
			span.css("border-style", val);
			lastInput.css("border-style", val);
		}

	},
	/**
	 * 设置圆边
	 */
	setCircleStyle : function(component, val) {
		var prop = "circleStyle";
		daterangepicker_designer.setDataRule(component,prop,val);
		var firstInput = component.children().eq(0);
		var lastInput = component.children().eq(2);

		if (val == 'display') {
			firstInput.addClass('input-circle-left');
			lastInput.addClass('btn-circle-right');
		} else {
			firstInput.removeClass('input-circle-left');
			lastInput.removeClass('btn-circle-right');
		}
	},

	
	/**
	 * 文本水平对齐
	 */
	setTextAlign : function(component, val) {
		var prop = "textAlign";
		daterangepicker_designer.setDataRule(component,prop,val);
		var firstInput = component.children().eq(0);
		var lastInput = component.children().eq(2);
		firstInput.css("text-align", val);
		lastInput.css("text-align", val);
		
	},
	/**
	 * 文本垂直对齐
	 */
	setVerticalAlign : function(component, val) {
		var prop = "verticalAlign";
		daterangepicker_designer.setDataRule(component,prop,val);
		var firstInput = component.children().eq(0);
		var lastInput = component.children().eq(2);
		firstInput.css("vertical-align", val);
		lastInput.css("vertical-align", val);
	
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = daterangepicker_designer.getDataRule(component,prop)
		
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
