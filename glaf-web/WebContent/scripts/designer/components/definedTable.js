/**
 * definedTable
 */
var definedTable_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		definedTable_designer.setDataRule(component,prop,val);
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
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setMargin(component, "right", val);
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
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.setPositon(component, 'right', val);
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
		definedTable_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	
	setDataRule : function(component,prop,val){
		
		var lastVal = definedTable_designer.getDataRule(component,prop)
		
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
	setBackgroundColor : function(component,val){
		var prop = "backgroundColor";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.updateStyleByKey(component,'backgroundColor',val);
	},
	setBackgroundColorSelected : function(component,val){
		var prop = "backgroundColorSelected";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.updateStyleByKey(component,'backgroundColorSelected',val);
	},
	setBorderColorSelected : function(component,val){
		var prop = "borderColorSelected";
		definedTable_designer.setDataRule(component,prop,val);
		definedTable_designer.updateStyleByKey(component,'borderColorSelected',val);
	},

	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = definedTable_designer.getStyler(component);
		styler[key] = val;
		definedTable_designer.updateStyler(component,styler);
		definedTable_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = definedTable_designer.getStylerId(component);
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
		var sid = definedTable_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _default_selected = _id + " tr[role='k-row'].selected>td{";
		if(styler['borderColorSelected']){
			_default_selected += 'border:1px solid '+styler['borderColorSelected']+";";	
		}
		if(styler['backgroundColorSelected']){
			_default_selected += 'background-color: '+styler['backgroundColorSelected']+";";	
		}

		_default_selected += "}"

		var _default_table = _id + " .k-definedtable{";
		if(styler['backgroundColor']){
			_default_table += 'background-color: '+styler['backgroundColor']+";";	
		}
		_default_table += "}"

		_default += _default_selected+_default_table;

		var stylebox = definedTable_designer.getStyleBox(component);
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