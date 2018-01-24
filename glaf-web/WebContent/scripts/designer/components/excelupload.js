/**
 * excelupload
 */
var excelupload_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		excelupload_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		var prop = "width";
		excelupload_designer.setDataRule(component,prop,val);
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
		excelupload_designer.setDataRule(component,prop,val);
		component.removeClass("hmtd-xs");
		component.addClass(val);

	},
	/**
	 * 设置excel上传大小样式
	 */
	setExcelUploadSize : function(component, val) {
		var prop = "uploadSize";
		excelupload_designer.setDataRule(component,prop,val);
		component.find(".btn").removeClass("btn-xs");
		component.find(".btn").addClass(val);
		$(".btn-group-xs>.btn,.btn-xs").css("padding","4px 4px 4px 5px");
		
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = excelupload_designer.getDataRule(component,prop)
		
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
	setButtonColor : function(component,val){
		var prop = "buttonColor"; 
		excelupload_designer.setDataRule(component,prop,val);
	
		excelupload_designer.updateStyleByKey(component,'buttonColor',val);
	},
	setButtonBorderColor : function(component,val){
		var prop = "buttonBorderColor"; 
		excelupload_designer.setDataRule(component,prop,val);
	
		excelupload_designer.updateStyleByKey(component,'buttonBorderColor',val);
	},
	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = excelupload_designer.getStyler(component);
		styler[key] = val;
		excelupload_designer.updateStyler(component,styler);
		excelupload_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = excelupload_designer.getStylerId(component);
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
		var sid = excelupload_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _default_span_container = _id + " .btn-success{";
		if(styler['buttonColor']){
			_default_span_container += 'background-color:'+styler['buttonColor']+" ;";
		}
		if(styler['buttonBorderColor']){
			_default_span_container += 'border-color:'+styler['buttonBorderColor']+" ;";	
		}
		_default_span_container += "};"

		_default += _default_span_container;

		var stylebox = excelupload_designer.getStyleBox(component);
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
