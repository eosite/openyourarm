var svgeditor_designer = {
	getMainObject: function(component) {

		return component;

	},
	/**
	 * 设置控件名称
	 */
	setName: function(component, val) {
		if (val !== '') {
			var prop = "name";
			svgeditor_designer.setDataRule(component, prop, val);
			component.attr("cname", val);
		} else {
			component.attr("cname", "");
		}
	},

	/**
	 * 设置位置
	 */
	setPositionTop: function(component, val) {
		var prop = "top";
		svgeditor_designer.setDataRule(component, prop, val);
		svgeditor_designer.setPositon(component, 'top', val);
	},
	setPositionBottom: function(component, val) {
		var prop = "bottom";
		svgeditor_designer.setDataRule(component, prop, val);
		svgeditor_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft: function(component, val) {
		var prop = "left";
		svgeditor_designer.setDataRule(component, prop, val);
		svgeditor_designer.setPositon(component, 'left', val);
	},
	setPositionRight: function(component, val) {
		var prop = "right";
		svgeditor_designer.setDataRule(component, prop, val);
		svgeditor_designer.setPositon(component, 'right', val);
	},
	setPositon: function(component, direct, val) {

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
	setPositonStyle: function(component, val) {

		var prop = "position";
		svgeditor_designer.setDataRule(component, prop, val);

		component.css("position", val);

	},
	/**
	 *设置尺寸
	 */
	setWidth: function(component, val, unit) {

		var prop = "width";

		svgeditor_designer.setDataRule(component, prop, val, unit);

		svgeditor_designer.setSize(component, "width", val, unit);

	},

	setHeight: function(component, val, unit) {

		var prop = "height";

		svgeditor_designer.setDataRule(component, prop, val, unit);

		svgeditor_designer.setSize(component, "height", val, unit);

	},
	setSize: function(component, type, val, unit) {
		var portlet = svgeditor_designer.getMainObject(component);
		if (val == '') {
			portlet.css(type, '');
		} else {
			if (unit == undefined) {
				unit = "px";
			} else {
				portlet.css(type, val + unit);
			}
		}
	},

	setDataRule: function(component, prop, val, unit) {
		var lastVal = svgeditor_designer.getDataRule(component, prop)

		if (component.attr("data-rule")) {

			var data_rule = JSON.parse(component.attr("data-rule"));

			data_rule[prop] = val;

		} else {

			var data_rule = {};

			data_rule[prop] = val;

		}

		if (unit != undefined) {

			data_rule[prop + "_unit"] = unit;

		}

		component.attr("data-rule", JSON.stringify(data_rule));

		return lastVal;
	},
	getDataRule: function(component, prop) {
		var value = "";
		if (component.attr("data-rule")) {
			value = JSON.parse(component.attr("data-rule"))[prop];
		}
		return value;
	},
};
var svgeditor_proto = Object.create(svgeditor_designer);