/**
 * jqfileupload2
 */
var jqfileupload2_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		jqfileupload2_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	
	setBtnName : function(component,val){
		var $component = $(component);

		var prop = "btnName";
		jqfileupload2_designer.setDataRule(component,prop,val);
		$component.find(".fileinput-button span").text(val);
	},
	//start------------内容
	//颜色
	setFontColor : function(component,val){
		var $component = $(component);
		var prop = "fontColor";
		jqfileupload2_designer.setDataRule(component,prop,val);
		$component.find(".fileinput-button span").css("color",val);
	},
	//样式
	setFontStyle: function(component, val) {
		var $component = $(component);
		var prop = "fontStyle";
		jqfileupload2_designer.setDataRule(component, prop, val);
		var tg = $component.find(".fileinput-button span");
		var tgcss = $(tg).attr('style') || '';
		if (val == 'italic') {
			if (tgcss.indexOf('font-style: italic') == -1) {
				tg.css("font-style", 'italic');
			} else {
				tg.css("font-style", '');
			}
		} else if (val == 'bold') {
			if (tgcss.indexOf('font-weight: bold') == -1) {
				tg.css("font-weight", 'bold');
			} else {
				tg.css("font-weight", '');
			}
		}
	},
	//字体大小
	setFontSize: function(component, val) {
		var $component = $(component);
		if (val != '') {
			var prop = "fontSize";
			jqfileupload2_designer.setDataRule(component, prop, val);
			var tg = $component.find(".fileinput-button span");
			tg.css("font-size", val + "px");
		}
	},
	//图标颜色
	setBtnIconColor : function(component,val){
		var $component = $(component);

		var prop = "btnIconColor";
		jqfileupload2_designer.setDataRule(component,prop,val);
		$component.find(".fileinput-button i").css("color",val);
	},
	//图标大小
	setBtnIconSize: function(component, val) {
		var $component = $(component);
		if (val != '') {
			var prop = "btnIconSize";
			jqfileupload2_designer.setDataRule(component, prop, val);
			var tg = $component.find(".fileinput-button i");
			tg.css("font-size", val + "px");
		}
	},
	setBtnPaddingTop: function(component, val) {
		var prop = "btnpaddingTop";
		jqfileupload2_designer.setDataRule(component, prop, val);
		jqfileupload2_designer.setBtnPadding(component, "top", val);
	},
	setBtnPaddingBottom: function(component, val) {
		var prop = "btnpaddingBottom";
		jqfileupload2_designer.setDataRule(component, prop, val);
		jqfileupload2_designer.setBtnPadding(component, "bottom", val);
	},
	setBtnPaddingLeft: function(component, val) {
		var prop = "btnpaddingLeft";
		jqfileupload2_designer.setDataRule(component, prop, val);
		jqfileupload2_designer.setBtnPadding(component, "left", val);
	},
	setBtnPaddingRight: function(component, val) {
		var prop = "btnpaddingRight";
		jqfileupload2_designer.setDataRule(component, prop, val);
		jqfileupload2_designer.setBtnPadding(component, "right", val);
	},
	setBtnPadding: function(component, direct, val) {
		var $component = $(component);
		if (val == '') {
			$component.find(".fileinput-button").css("padding-" + direct, "");
		} else {
			$component.find(".fileinput-button").css("padding-" + direct, val + "px");
		}
	},
	//end------------内容
	
	setProgressStatus : function(component,val){
		var $component = $(component);

		var prop = "progressStatus";
		jqfileupload2_designer.setDataRule(component,prop,val);
		if(val && val == 'no'){
			$component.find(".progress").hide();
		}else{
			$component.find(".progress").show();
		}
		
	},

	setBtnIcon : function(component,val){
		var $component = $(component);

		var prop = "btnIcon";
		jqfileupload2_designer.setDataRule(component,prop,val);
		$component.find(".fileinput-button i").removeClass().addClass(val);
	},

	setBtnColor : function(component,val){
		var $component = $(component);

		var prop = "btnIcon";
		jqfileupload2_designer.setDataRule(component,prop,val);
		$component.find(".fileinput-button").css("background-color",val);
	},
	setBtnBorderColor : function(component,val){
		var $component = $(component);

		var prop = "btnBorderColor";
		jqfileupload2_designer.setDataRule(component,prop,val);
		$component.find(".fileinput-button").css("border-color",val);
	},

	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		var prop = "width";
		jqfileupload2_designer.setDataRule(component,prop,val);
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
		var prop = "height";
		jqfileupload2_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("height", '');
		} else {
			component.css("height", val + "px");
		}
	},

	/**
	 * 设置高度
	 */
	setSize : function(component, val) {
		var prop = "size";
		jqfileupload2_designer.setDataRule(component,prop,val);
		component.removeClass("hmtd-xs");
		component.addClass(val);

	},

	/**
	 * 横向或纵向排序
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setSortType : function(component, val) {
		var prop = "sortType";
		jqfileupload2_designer.setDataRule(component,prop,val);
		component.attr("sortType",val);
	},

	setDisabledBackColor : function(component,val){
		var prop = "disabledBackColor";
		jqfileupload2_designer.setDataRule(component,prop,val);

		jqfileupload2_designer.updateStyleByKey(component,'disabledBackColor',val);
	},
	setDisabledColor : function(component,val){
		var prop = "disabledColor";
		jqfileupload2_designer.setDataRule(component,prop,val);

		jqfileupload2_designer.updateStyleByKey(component,'disabledColor',val);
	},


	setDataRule : function(component,prop,val){
		
		var lastVal = jqfileupload2_designer.getDataRule(component,prop)
		
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
	getStyler: function (component){
		var sid = jqfileupload2_designer.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return styler.val()?$.parseJSON(styler.val()) : new Object();
		}
	},
	getStyleBox: function (component){
		var sid = jqfileupload2_designer.getStyleBoxId(component);
		var box = component.find('#'+sid);
		if(!box.length){
			component.append("<style id=\""+sid+"\"></style>");
		}
		return component.find('#'+sid);
	},
	getStylerId: function (component){
		var id = component.attr('id');
		return id+'_styler_hidden';
	},
	getStyleBoxId: function (component){
		var id = component.attr('id');
		return id+'_styler_init';
	},
	updateStyler: function (component,styler){
		var sid = jqfileupload2_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;

		var _default_disabled_btn = _id+"[disabled] .fileinput-button{";
		if(styler['disabledColor']){
			_default_disabled_btn += "color:"+styler['disabledColor']+" !important;";
		}
		if(styler['disabledBackColor']){
			_default_disabled_btn += "background-color:"+styler['disabledBackColor']+" !important;";
		}
		_default_disabled_btn += "}";

		var stylebox = jqfileupload2_designer.getStyleBox(component);
		stylebox.html(_default_disabled_btn);
	},
	updateStyleByKey: function (component,key,val){
		var styler = jqfileupload2_designer.getStyler(component);
		styler[key] = val;
		jqfileupload2_designer.updateStyler(component,styler);
		jqfileupload2_designer.updateStyleBox(component,styler);
	}
}
