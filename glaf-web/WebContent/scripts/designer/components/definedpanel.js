/**
 * gridList
 */
var definedpanel_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		definedpanel_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	getBodyObject : function(component) {
		var mainBody;
		if (component.find("input").length) {
			mainBody = $(component.find("input"));
		} else {
			mainBody = $(component);
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = definedpanel_designer.getDataRule(component,prop)
		
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
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = definedpanel_designer.getStyler(component);
		styler[key] = val;
		definedpanel_designer.updateStyler(component,styler);
		definedpanel_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = definedpanel_designer.getStylerId(component);
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
		var sid = definedpanel_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _content_selected = _id + " .definedPanleContent.selected{"
		if(styler['contentBackgroundSelected']){
			_content_selected += "background-color:"+styler['contentBackgroundSelected']+";";
		}
		if(styler['contentBorderColorSelected']){
			_content_selected += "border-color:"+styler['contentBorderColorSelected']+";";	
		}
		if(styler['contentBorderWidthTopSelected']){
			_content_selected += "border-top-width:"+styler['contentBorderWidthTopSelected']+";";	
		}
		if(styler['contentBorderWidthBottomSelected']){
			_content_selected += "border-bottom-width:"+styler['contentBorderWidthBottomSelected']+";";	
		}
		if(styler['contentBorderWidthLeftSelected']){
			_content_selected += "border-left-width:"+styler['contentBorderWidthLeftSelected']+";";	
		}
		if(styler['contentBorderWidthRightSelected']){
			_content_selected += "border-right-width:"+styler['contentBorderWidthRightSelected']+";";	
		}
		
		_content_selected += "}"

		var _content = _id + " .definedPanleContent{"
		if(styler['contentMarginTop']){
			_content += "margin-top:"+styler['contentMarginTop']+";";
		}
		if(styler['contentMarginBottom']){
			_content += "margin-bottom:"+styler['contentMarginBottom']+";";
		}
		if(styler['contentMarginLeft']){
			_content += "margin-left:"+styler['contentMarginLeft']+";";
		}
		if(styler['contentMarginRight']){
			_content += "margin-right:"+styler['contentMarginRight']+";";
		}
		_content += "}"
		

		_default += _content_selected + _content;

		var stylebox = definedpanel_designer.getStyleBox(component);
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
	/**
	 * 设置内容布局方式，水平或垂直
	 * @param  {[type]} component [description]
	 * @return {[type]}           [description]
	 */
	setContentLayout : function(component,val){
		var prop = 'contentLayout';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val == 'vertical'){
			//垂直
			component.removeClass("inlineContent");
		}else{
			//水平
			component.addClass("inlineContent");
		}
	},
	setContentBackgroundSelected : function(component,val){
		var prop = 'contentBackgroundSelected';
		definedpanel_designer.setDataRule(component,prop,val);

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentBorderColorSelected : function(component,val){
		var prop = 'contentBorderColorSelected';
		definedpanel_designer.setDataRule(component,prop,val);

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentMarginTop : function(component,val){
		var prop = 'contentMarginTop';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentMarginBottom : function(component,val){
		var prop = 'contentMarginBottom';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentMarginLeft : function(component,val){
		var prop = 'contentMarginLeft';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentMarginRight : function(component,val){
		var prop = 'contentMarginRight';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentBorderWidthTopSelected : function(component,val){
		var prop = 'contentBorderWidthTopSelected';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentBorderWidthBottomSelected : function(component,val){
		var prop = 'contentBorderWidthBottomSelected';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentBorderWidthLeftSelected : function(component,val){
		var prop = 'contentBorderWidthLeftSelected';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
	setContentBorderWidthRightSelected : function(component,val){
		var prop = 'contentBorderWidthRightSelected';
		definedpanel_designer.setDataRule(component,prop,val);
		if(val){
			val = val + 'px';
		}

		definedpanel_designer.updateStyleByKey(component,prop,val);
	},
}	
