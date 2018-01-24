var phonePanel_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function(component, val) {
		if (val !== '') {
			var prop = "name";
			phonePanel_designer.setDataRule(component, prop, val);
			component.attr("cname", val);
		}
	},
	/**
	 * 设置按钮文本
	 */
	setText: function(component, val) {
		if ($.trim(val) !== '') {
			var prop = "text";
			phonePanel_designer.setDataRule(component, prop, val);
			component.find('>span').html(val);
		}
	},
	setTitleName : function(component,val){
		var prop = "text";
		phonePanel_designer.setDataRule(component, prop, val);
		component.find('.weui-panel__hd')[0].innerHTML = val;
	},

	/**
	 * 设置高度
	 */
	setSizeVal : function(component, val) {
		var prop = "sizeVal";
		mtbutton_proto.setDataRule(component,prop,val);
			component.removeClass("btn-lg btn-sm hmtd-xs");
			component.addClass(val);
	},
	getStyler: function(component) {
		var sid = mtbutton_proto.getStylerId(component);
		var styler = component.find('#' + sid);
		if (!styler.length) {
			component.append("<input id=\"" + sid + "\" type=\"hidden\" value=\"\" />");
			return new Object();
		} else {
			return $.parseJSON(styler.val());
		}
	},
	setWidth:function(component,val,unit){ 
		var prop = "width";
		mtbutton_proto.setDataRule(component,prop,val);
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + unit);
		}

	},
	
	 setHeight:function(component,val,unit){
		var prop = "height";
		mtbutton_proto.setDataRule(component,prop,val); 
		if (val == '') {
			component.css("height", '');
		} else {
			component.css("height", val + unit);
		}

	},
	setRadius : function(component,val,unit){
		var prop = "border-radius";
		mtbutton_proto.setDataRule(component,prop,val); 
		if (val == '') {
			component[0].setAttribute("style","")
		} else {
			component[0].setAttribute("style","border-radius:"+ val + unit + " !important");
		}

	},
	getStyleBox: function(component) {
		var sid = mtbutton_proto.getStyleBoxId(component);
		var box = component.find('#' + sid);
		if (!box.length) {
			component.append("<style id=\"" + sid + "\"></style>");
		}
		return component.find('#' + sid);
	},
	getStylerId: function(component) {
		var id = component.attr('id');
		return id + '_styler_hidden';
	},
	getStyleBoxId: function(component) {
		var id = component.attr('id');
		return id + '_styler_init';
	},
	updateStyler: function(component, styler) {
		var sid = mtbutton_proto.getStylerId(component);
		$('#' + sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function(component, styler) {
		var id = component.attr('id');
		var _id = '#' + id;
		//default
		var _default = _id + "{";
		if (styler['bgcolor']) {
			_default += "background-color:" + styler['bgcolor'] + ";";
		}
		if (styler['bocolor']) {
			_default += "border-color:" + styler['bocolor'] + ";";
		}
		if (styler['focolor']) {
			_default += "color:" + styler['focolor'] + ";";
		}
		_default += "}";
		//hover
		var _hover = _id + ":hover{";
		if (styler['bgcolor_h']) {
			_hover += "background-color:" + styler['bgcolor_h'] + ";";
		}
		if (styler['bocolor_h']) {
			_hover += "border-color:" + styler['bocolor_h'] + ";";
		}
		if (styler['focolor_h']) {
			_hover += "color:" + styler['focolor_h'] + ";";
		}
		_hover += "}";

		var stylebox = mtbutton_proto.getStyleBox(component);
		stylebox.html(_default + _hover);
	},
	updateStyleByKey: function(component, key, val) {
		var styler = mtbutton_proto.getStyler(component);
		styler[key] = val;
		mtbutton_proto.updateStyler(component, styler);
		mtbutton_proto.updateStyleBox(component, styler);
	},
	setTextAlign : function(component,val){
		var prop = "textAlign";
		phonePanel_designer.setDataRule(component, prop, val);
		component.css("text-align",val);
	},
	/**
	 *设置背景色
	 */
	setBackgroundColor: function(component, val) {
		var prop = "backgroundColor";
		phonePanel_designer.setDataRule(component, prop, val);
		mtbutton_proto.updateStyleByKey(component, 'bgcolor', val);
	},
	/**
	 *设置边框颜色
	 */
	setBorderColor: function(component, val) {
		if (val != '') {
			var prop = "borderColor";
			phonePanel_designer.setDataRule(component, prop, val);
			mtbutton_proto.updateStyleByKey(component, 'bocolor', val);
		}
	},
	/**
	 *设置边框样式
	 */
	setBorderStyle: function(component, val) {
		if (val != '') {
			var prop = "borderStyle";
			phonePanel_designer.setDataRule(component, prop, val);
			component.css("border-style", val);
		}
	},
	/**
	 *设置字体
	 */
	setFontFamily: function(component, val) {
		if (val != '') {
			var tg = component.find('span');
			tg.css("font-family", val);
		}
	},
	setFontColor: function(component, val) {
		if (val != '') {
			var prop = "color";
			phonePanel_designer.setDataRule(component, prop, val);
			mtbutton_proto.updateStyleByKey(component, 'focolor', val);
		}
	},
	setFontStyle: function(component, val) {
		var prop = "fontStyle";
		phonePanel_designer.setDataRule(component, prop, val);
		var tg = component.find('span');
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
	/**
	 *设置字体大小
	 */
	setFontSize: function(component, val) {
		if (val != '') {
			var prop = "fontSize";
			phonePanel_designer.setDataRule(component, prop, val);
			var tg = component.find('span');
			tg.css("font-size", val + "px");
		}
	},
	setLineHeight: function(component, val) {
		if (val != '') {
			var prop = "lineHeight";
			phonePanel_designer.setDataRule(component, prop, val);
			component.css("line-height", val);
		} else {
			component.css("line-height", "");
		}
	},
	/**
	 *鼠标悬停效果
	 */
	setBackgroundColor_hover: function(component, val) {
		
			var prop = "backgroundColor_h";
			phonePanel_designer.setDataRule(component, prop, val);
			mtbutton_proto.updateStyleByKey(component, 'bgcolor_h', val);
		
	},
	setBorderColor_hover: function(component, val) {
		
			var prop = "borderColor_h";
			phonePanel_designer.setDataRule(component, prop, val);
			mtbutton_proto.updateStyleByKey(component, 'bocolor_h', val);
		
	},
	setFontColor_hover: function(component, val) {
	
			var prop = "fontColor";
			phonePanel_designer.setDataRule(component, prop, val);
			mtbutton_proto.updateStyleByKey(component, 'focolor_h', val);
	
	},
	setTip: function(component, val){
		var com=$(component);
		if(val!=''){
			com.attr('title',val);
		}
	},
	/**
	 * 圆边
	 */
	setCircleStyle: function(component, val) {
		var prop = "circleStyle";
		phonePanel_designer.setDataRule(component, prop, val);
		if (val == 'display') {
			component.addClass('btn-circle');
		} else {
			component.removeClass('btn-circle');
		}
	},
	/**
	 * 圆边
	 */
	setClearSpan: function(component, val) {
		var prop = "clearSpan";
		phonePanel_designer.setDataRule(component, prop, val);
		if (val == 'yes') {
			component.find("span").remove();
			
		} else {
			if(component.find("span")[0] == undefined){
				component.append("<span class='frame-variable "+component[0].getAttribute("id")+"' contenteditable='true' frame-variable='fv1'>默认按钮</span>");
			}
		}
	},
	/**
	 * 设置模态窗口
	 */
	setModal: function(component, val) {
		var prop = "modal";
		phonePanel_designer.setDataRule(component, prop, val);
		if (val == 'display') {
			component.attr('bt-dialog', true);
		} else {
			component.attr('bt-dialog', false);
		}
	},
	/**
	 * 设置窗口打开模式
	 */
	setMode: function(component, val) {
		if (val != '') {
			var prop = "mode";
			phonePanel_designer.setDataRule(component, prop, val);
			component.attr('btdg-mode', val);
		}
	},
	/**
	 * 设置窗口风格
	 */
	setType: function(component, val) {
		if (val != '') {
			var prop = "type";
			phonePanel_designer.setDataRule(component, prop, val);
			component.attr('btdg-type', val);
		}
	},
	/**
	 * 设置标题
	 */
	setTitle: function(component, val) {
		if (val != '') {
			var prop = "title";
			phonePanel_designer.setDataRule(component, prop, val);
			component.attr('btdg-title', val);
		}
	},
	/**
	 * 设置提示信息
	 */
	setMessage: function(component, val) {
		if (val != '') {
			component.attr('btdg-message', val);
		}
	},
	/**
	 * 设置窗口大小
	 */
	setSize: function(component, val) {
		if (val != 'display') {
			component.attr('btdg-size', val);
		}
	},
	/**
	 * 设置可拖动
	 */
	setDraggable: function(component, val) {
		if (val != '') {
			component.attr('btdg-draggable', val);
		}
	},
	/**
	 * 预览
	 */
	preview: function(component, val) {
		var state = component.attr('bt-dialog');
		if (eval(state)) {
			var dgobj = {
				type: BootstrapDialog[component.attr('btdg-type')],
				size: BootstrapDialog[component.attr('btdg-size')],
				title: component.attr('btdg-title'),
				message: component.attr('btdg-message'),
				draggable: eval(component.attr('btdg-draggable'))
			}
			if (component.attr('btdg-mode') === 'show') {
				BootstrapDialog.show(dgobj);
			} else if (component.attr('btdg-mode') === 'confirm') {
				BootstrapDialog.confirm(dgobj);
			} else {
				BootstrapDialog.alert(dgobj);
			}
		} else {
			alert('请先设置窗口为‘是’！');
		}
	},
	setDataRule: function(component, prop, val) {
		var lastVal = phonePanel_designer.getDataRule(component, prop)

		if (component.attr("data-rule")) {
			var data_rule = JSON.parse(component.attr("data-rule"));
			data_rule[prop] = val;
		} else {
			var data_rule = {};
			data_rule[prop] = val;
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
	/**
	 *设置内间距
	 */
	setPaddingTop: function(component, val) {
		var prop = "paddingTop";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setPadding(component, "top", val);
	},
	setPaddingBottom: function(component, val) {
		var prop = "paddingBottom";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft: function(component, val) {
		var prop = "paddingLeft";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setPadding(component, "left", val);
	},
	setPaddingRight: function(component, val) {
		var prop = "paddingRight";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setPadding(component, "right", val);
	},
	setPadding: function(component, direct, val) {
		if (val == '') {
			component.css("padding-" + direct, "");
		} else {
			component.css("padding-" + direct, val + "px");
		}
	},
	/**
	 *设置外间距
	 */
	setMarginTop: function(component, val) {
		var prop = "marginTop";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setMargin(component, "top", val);
	},
	setMarginBottom: function(component, val) {
		var prop = "marginBottom";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft: function(component, val) {
		var prop = "marginLeft";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setMargin(component, "left", val);
	},
	setMarginRight: function(component, val) {
		var prop = "marginRight";
		phonePanel_designer.setDataRule(component, prop, val);
		phonePanel_designer.setMargin(component, "right", val);
	},
	setMargin: function(component, direct, val) {
		if (val == '') {
			component.css("margin-" + direct, "");
		} else {
			component.css("margin-" + direct, val + "px");
		}
	},
	setIconClass : function(component,val){
		var $component = $(component);
		
		var prop = "iconClass";
		var iconClass = phonePanel_designer.setDataRule(component,prop,val);
			
		if($component.find("i").length>0){
			$component.find("i").removeClass(iconClass);
			$component.find("i").addClass(val);				
		}else{
			$icon = $("<i></i>");
			$icon.addClass(val);			
			$component.prepend($icon);
		}
	},
	setRightIconClass : function(component,val){
		var $component = $(component);
		
		var prop = "iconClass";
		var iconClass = phonePanel_designer.setDataRule(component,prop,val);
			
		if($component.find("i.pull-right").length>0){
			$component.find("i.pull-right").removeClass(iconClass);
			$component.find("i.pull-right").addClass(val);				
		}else{
			$icon = $("<i class='pull-right'></i>");
			$icon.addClass(val);			
			$component.prepend($icon);
		}
	},
	initIconClass: function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#icon").addClass(value);
			$temp.find("#iconName").html(value);
		}
		return $temp[0];
	},
	initIconClass2: function(template,value){
		var $temp = $(template);
		if(value){
			$temp.find("#icon2").addClass(value);
			$temp.find("#iconName2").html(value);
		}
		return $temp[0];
	},
	setIconColor:function(component,val){
		var prop = "iconColor";
		var iconColor = phonePanel_designer.setDataRule(component,prop,val);
		if(component.find("i").length>0){
			component.find("i").removeClass(iconColor);
			component.find("i").addClass(val);	
		}
	},
	setIconSize:function(component,val){
		var prop = "iconSize"; 
		phonePanel_designer.setDataRule(component,prop,val);
		if(component.find("i").length>0){
			if (val == '') {
				component.find("i").css("font-size", '');
			} else {
				component.find("i").css("font-size", val + "px");
			}
		}
		
	},
	setIconAlign:function(component,val){
		var prop = "iconAlign"; 
		phonePanel_designer.setDataRule(component,prop,val);
		if(component.find("i").length>0){
			var $i = component.find("i");
			if(val=="left"){
				$i.remove();
				component.prepend($i);
			}else{
				$i.remove();
				component.append($i);				
			}
		}
	},
	setIconRoll:function(component,val){
		var prop = "iconRoll"; 
		phonePanel_designer.setDataRule(component,prop,val);
		if(component.find("i").length>0){
			if(val=="no"){
				component.find("i").removeClass("fa-spin");
			}else{
				component.find("i").addClass("fa-spin");
			}
		}
	},
	 setBorderRadius:function(component,val){
		var prop = "borderRadius";
		phonePanel_designer.setDataRule(component,prop,val);
	    component.removeClass("radius-level-1 radius-level-2 radius-level-3 radius-level-4 radius-level-5");
		if(val == 'zero'){
			
		}else if(val=='one'){
		    component.addClass("radius-level-1");
		}else if(val=='two'){ 
		    component.addClass("radius-level-2");
		}else if(val=='three'){
			component.addClass("radius-level-3");
		}else if(val=='four'){ 
			component.addClass("radius-level-4");
		}else if(val=='five'){ 
			component.addClass("radius-level-5");
		}else if(val=='five'){ 
			component.addClass("radius-level-5");
		}
	}
};

var phoneListView_proto = Object.create(phonePanel_designer);