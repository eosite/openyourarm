/**
 * step
 */
var step_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		step_designer.setDataRule(component,prop,val);
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
		step_designer.setDataRule(component,prop,val);
		step_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		step_designer.setDataRule(component,prop,val);
		step_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		step_designer.setDataRule(component,prop,val);
		step_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		step_designer.setDataRule(component,prop,val);
		step_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
//		var obj = step_designer.getBodyObject(component);
		if (val == '') {
			component.css("padding-" + direct, "");
		} else {
			component.css("padding-" + direct, val + "px");
		}
	},


	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		step_designer.setDataRule(component,prop,val);
		step_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		step_designer.setDataRule(component,prop,val);
		step_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		step_designer.setDataRule(component,prop,val);
		step_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		step_designer.setDataRule(component,prop,val);
		step_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
//		var obj = step_designer.getBodyObject(component);
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
		step_designer.setDataRule(component,prop,val);
		step_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		step_designer.setDataRule(component,prop,val);
		step_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		step_designer.setDataRule(component,prop,val);
		step_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		step_designer.setDataRule(component,prop,val);
		step_designer.setPositon(component, 'right', val);
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
		step_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		
		var prop = "width";
		step_designer.setDataRule(component,prop,val);
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
//		var obj = step_designer.getBodyObject(component);
		var prop = "height";
		step_designer.setDataRule(component,prop,val);
//		component.removeClass("input-medium input-lg input-sm input-xs");
		if (val == '') {
			component.css("height", '');
		} else {
			component.css("height", val + "px");
		}
	},
	setShape :function(component, val){
		var prop = "shape";
		step_designer.setDataRule(component,prop,val);
		
		var style = step_designer.getStyleBox(component);
		step_designer.updateStyleBox(component);
	},
	updateStyleBox: function (component){
		var id = component.attr('id');
		var _id = '#'+id;
		var style="";
	    var stylebox = step_designer.getStyleBox(component);
	    var shape = step_designer.getDataRule(component,"shape");
	    if(shape&&shape=="round"){
	    	style +=_id+'.mt-element-step .step-line .mt-step-number{border-radius: 50%!important;}';
	    }else if(shape&&shape=="square"){
	    	style +=_id+'.mt-element-step .step-line .mt-step-number{border-radius: 0%!important;}';
	    }
	    stylebox.html(style);
	},
	getStyleBoxId: function (component){
		var id = component.attr('id');
		return id+'_styler_init';
	},
	getStyleBox: function (component){
		var sid = step_designer.getStyleBoxId(component);
		var box = component.find('#'+sid);
		if(!box.length){
			component.append("<style id=\""+sid+"\"></style>");
		}
		return component.find('#'+sid);
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = step_designer.getDataRule(component,prop)
		
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
	setDoneColor : function(component,val){
		var prop = "doneColor";
		step_designer.setDataRule(component,prop,val);
		step_designer.updateStyleByKey(component,'doneColor',val);
	},
	setDoingColor : function(component,val){
		var prop = "doingColor";
		step_designer.setDataRule(component,prop,val);
		step_designer.updateStyleByKey(component,'doingColor',val);
	},

	setTitleSize : function(component,val){
		var prop = "titleSize";
		step_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		step_designer.updateStyleByKey(component,'titleSize',val);
	},
	setIndexSize : function(component,val){
		var prop = "indexSize";
		step_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		step_designer.updateStyleByKey(component,'indexSize',val);
	},
	setContentSize : function(component,val){
		var prop = "contentSize";
		step_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		step_designer.updateStyleByKey(component,'contentSize',val);
	},

	setNumberMaxWidht : function(component, val) {
		var prop = "numberMaxWidht";
		step_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		step_designer.updateStyleByKey(component,'numberMaxWidht',val);
	},

	setNumberMaxHeight : function(component, val) {
		var prop = "numberMaxHeight";
		step_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		step_designer.updateStyleByKey(component,'numberMaxHeight',val);
	},

	setNumberTextHeight : function(component, val) {
		var prop = "numberTextHeight";
		step_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		step_designer.updateStyleByKey(component,'numberTextHeight',val);
	},

	setNumberLineHeight : function(component, val) {
		var prop = "numberLineHeight";
		step_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		step_designer.updateStyleByKey(component,'numberLineHeight',val);
	},

	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = step_designer.getStyler(component);
		styler[key] = val;
		step_designer.updateStyler(component,styler);
		step_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = step_designer.getStylerId(component);
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
		var sid = step_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";
		var _default_done_after = _id+" .step-line .done .mt-step-title:after, "+_id+" .step-line .done .mt-step-title:before{";
		var _default_done = _id + "  .step-line .done .mt-step-content,"+_id+"  .step-line .done .mt-step-title,"+_id+
			" .step-line .done .mt-step-number{";
		if(styler['doneColor']){
			_default_done += 'color:'+styler['doneColor']+" !important;";
			_default_done += 'border-color:'+styler['doneColor']+" !important;";
			_default_done_after +=' background-color:'+styler['doneColor']+" !important;";
		}
		_default_done += "}";
		_default_done_after +="}";

		var _default_active = _id + "  .step-line .active .mt-step-content,"+_id+"  .step-line .active .mt-step-title,"+_id+
			" .step-line .active .mt-step-number{";
		var _default_active_after = _id+" .step-line .active .mt-step-title:after, "+_id+" .step-line .active .mt-step-title:before{";
		if(styler['doingColor']){
			_default_active += 'color:'+styler['doingColor']+" !important;";
			_default_active += 'border-color:'+styler['doingColor']+" !important;";
			_default_active_after +=' background-color:'+styler['doingColor']+" !important;";
		}
		_default_active += "}";
		_default_active_after +="}";

		var _default_title = _id + " .step-line .mt-step-title{";
		var _default_content = _id + " .step-line .mt-step-content{";
		var _default_index = _id + " .step-line .mt-step-number{";
		if(styler['titleSize']){
			_default_title += 'font-size:'+styler['titleSize']+" !important;";
		}
		if(styler['indexSize']){
			_default_index += 'font-size:'+styler['indexSize']+" !important;";
		}
		if(styler['contentSize']){
			_default_content += 'font-size:'+styler['contentSize']+" !important;";
		}
		_default_title += "}";
		_default_content +="}";
		_default_index +="}";

		var _default_number = _id + " .mt-step-number{";
		if(styler['numberMaxHeight']){
			_default_number += 'height:'+styler['numberMaxHeight']+" ;";
		}
		if(styler['numberTextHeight']){
			_default_number += 'line-height:'+styler['numberTextHeight']+" ;";
		}
		if(styler['numberMaxWidht']){
			_default_number += 'width:'+styler['numberMaxWidht']+" ;";
		}
		_default_number +="}";

		var _default_line = _id + " .step-line .mt-step-title:after,"+_id +" .step-line .mt-step-title:before{";
		if(styler['numberLineHeight']){
			_default_line += 'top:'+styler['numberLineHeight']+" !important;";
		}
		_default_line +="}";
		

		_default += _default_done + _default_done_after + _default_active + _default_active_after + _default_number+ _default_line;
		_default += _default_title + _default_content + _default_index;

		var stylebox = step_designer.getStyleBox(component);
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
