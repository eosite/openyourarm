/**
 * textboxbt
 */
var switch_designer = {
	setDataRule : function(component,prop,val){
		
		var lastVal = switch_designer.getDataRule(component,prop)
		
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
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		switch_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	getBodyObject:function (component){
		return component.find("input.switch_input");
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setMargin(component, "right", val);
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
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.setPositon(component, 'right', val);
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
		switch_designer.setDataRule(component,prop,val);
		component.css("position", val);
	},
	/**
	 * 设置尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setSize : function(component,val){
		var prop = "size";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);

		obj.bootstrapSwitch("onText","ON");
		obj.bootstrapSwitch("size",val);
		obj.attr("data-size",val);

		obj.bootstrapSwitch("onText",obj.attr("data-on-text"));
	},
	
	/**
	 * 设置动画效果
	 */
	setAnimate : function(component,val){
		var prop = "animate";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		val = eval(val);
		obj.bootstrapSwitch("animate",val);
		obj.attr("data-animate",val);

	},

	/**
	 * 模态
	 */
	setIndeterminate: function(component,val){
		var prop = "indeterminate";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		val = eval(val);
		obj.bootstrapSwitch("indeterminate",val);
		obj.attr("data-indeterminate",val);

	},
	/**
	 * 颠倒开关顺序
	 */
	setInverse: function(component,val){
		var prop = "inverse";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		val = eval(val);
		obj.bootstrapSwitch("inverse",val);
		obj.attr("data-inverse",val);

	},
	/**
	 * 左侧开关颜色
	 */
	setOnColor: function(component,val){
		var prop = "onColor";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.updateStyleByKey(component,prop,val);
	},
	/**
	 * 右侧开关颜色
	 */
	setOffColor: function(component,val){
		var prop = "offColor";
		switch_designer.setDataRule(component,prop,val);
		switch_designer.updateStyleByKey(component,prop,val);
	},

	/**
	 * 左侧开关文本
	 */
	setOnText: function(component,val){
		var prop = "onText";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		obj.bootstrapSwitch("onText",val);
		obj.attr("data-on-text",val);
	},

	/**
	 * 左侧开关图标
	 */
	setOnIcon: function(component,val){
		var prop = "onIcon";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		val = "<i class='"+val+"'></i>";
		obj.bootstrapSwitch("onText",val);
		obj.attr("data-on-text",val);
	},
	
	/**
	 * 右侧开关文本
	 */
	setOffText: function(component,val){
		var prop = "offText";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		obj.bootstrapSwitch("offText",val);
		obj.attr("data-off-text",val);
	},

	/**
	 * 右侧开关图标
	 */
	setOffIcon: function(component,val){
		var prop = "offIcon";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		val = "<i class='"+val+"'></i>";
		obj.bootstrapSwitch("offText",val);
		obj.attr("data-off-text",val);
	},
	/**
	 * 中间显示文本
	 */
	setLabelText: function(component,val){
		var prop = "labelText";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		obj.bootstrapSwitch("labelText",val);
		obj.attr("data-label-text",val);
	},
	/**
	 * 两侧宽度
	 */
	setHandleWidth: function(component,val){
		var prop = "handleWidth";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		obj.bootstrapSwitch("handleWidth",val);
		obj.attr("data-handle-width",val);
	},
	/**
	 * 中间宽度
	 */
	setLabelWidth: function(component,val){
		var prop = "labelWidth";
		switch_designer.setDataRule(component,prop,val);
		var obj=switch_designer.getBodyObject(component);
		obj.bootstrapSwitch("labelWidth",val);
		obj.attr("data-label-width",val);
	},
	

	

	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = switch_designer.getStyler(component);
		styler[key] = val;
		switch_designer.updateStyler(component,styler);
		switch_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = switch_designer.getStylerId(component);
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
		var sid = switch_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _left_button = _id + " .bootstrap-switch-handle-on{";
		
		if(styler['onColor']){
			_left_button += 'background:'+styler['onColor']+";";
		}
		_left_button += "}"

		var _right_button = _id + " .bootstrap-switch-handle-off{";
		if(styler['offColor']){
			_right_button += 'background:'+styler['offColor']+";";
		}
		_right_button += "}"
		

		
		_default += _left_button + _right_button;

		var stylebox = switch_designer.getStyleBox(component);
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
