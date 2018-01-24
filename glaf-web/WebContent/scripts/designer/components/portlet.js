var portlet_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			portlet_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}
	},
	/**
	*设置色调
	*/
	setColor: function (component,val){
		//if(val!=''){
			var prop = "color"; 
			portlet_designer.setDataRule(component,prop,val);
			component.css({'border': '1px solid '+val, 'border-top': 0});
			component.find('>.portlet-title').css({'background-color': val});
		//}
	},
	/**
	*设置标题文本
	*/
	setTitleText: function (component,val){
		// if(val!=''){
		var prop = "titleText"; 
		portlet_designer.setDataRule(component,prop,val);
		component.find('>.portlet-title .caption span.frame-variable').text(val);
		// }
	},
	/**
	*设置标题内容颜色
	*/
	setTitleColor: function (component,val){
		//if(val!=''){
			var prop = "titleColor"; 
			portlet_designer.setDataRule(component,prop,val);
			component.find('>.portlet-title .caption').css({'color': val});
			component.find('>.portlet-title .caption>i').css({'color': val});
			component.find('>.portlet-title .tools').css({'color': val});
			component.find('>.portlet-title .actions>a').css({'color': val, 'border-color': val});
			component.find('>.portlet-title .actions>a>i').css({'color': val});
		//}
	},
	/**
	*设置轨道可见
	*/
	setRailVisible: function (component,val){
		var prop = "railVisible"; 
		portlet_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.find('.scroller').attr('data-rail-visible', 0);
			component.find('.slimScrollDiv .slimScrollRail').css('display', 'none');
		}else{
			component.find('.scroller').attr('data-rail-visible', 1);
			component.find('.slimScrollDiv .slimScrollRail').css('display', 'block');
		}
	},
	/**
	*设置轨道颜色
	*/
	setRailColor: function (component,val){
		//if(val!=''){
			var prop = "railColor"; 
			portlet_designer.setDataRule(component,prop,val);
			component.find('.scroller').attr('data-rail-color', val);
			component.find('.slimScrollDiv .slimScrollRail').css('background-color', val);
		//}
	},
	/**
	*设置滚条颜色
	*/
	setBarColor: function (component,val){
		//if(val!=''){
			var prop = "handleColor"; 
			portlet_designer.setDataRule(component,prop,val);
			component.find('.scroller').attr('data-handle-color', val);
			component.find('.slimScrollDiv .slimScrollBar').css('background-color', val);
		//}
	},
	/**
	*设置折叠按键
	*/
	setCollapseBtn: function (component,val){
		var prop = "collapse"; 
		portlet_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.find('>.portlet-title .tools .collapse').css('display', 'none');
		}else{
			component.find('>.portlet-title .tools .collapse').css('display', 'inline-block');
		}
	},
	/**
	*设置全屏按键
	*/
	setFullscreenBtn: function (component,val){
		var prop = "fullscreen"; 
		portlet_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.find('>.portlet-title .tools .fullscreen').css('display', 'none');
		}else{
			component.find('>.portlet-title .tools .fullscreen').css('display', 'inline-block');
		}
	},
	/**
	 * 设置刷新按钮
	 */
	setRefreshscreenBtn: function (component,val){
		var prop = "refreshscreen"; 
		portlet_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.find('>.portlet-title .tools .reload').css('display', 'none');
		}else{
			component.find('>.portlet-title .tools .reload').css('display', 'inline-block');
		}
	},
	/**
	*设置刷新按键
	*/
	setReloadBtn: function (component,val){
		var prop = "refresh"; 
		portlet_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.find('>.portlet-title .tools .reload').css('display', 'none');
		}else{
			component.find('>.portlet-title .tools .reload').css('display', 'inline-block');
		}
	},
	/**
	*设置关闭按键
	*/
	setRemoveBtn: function (component,val){
		if(val=='hide'){
			component.find('>.portlet-title .tools .remove').remove();
		}else{
			var tools = component.find('>.portlet-title .tools');
			var tg = tools.find('.remove');
			if(!tg.length){
				tools.append('<a href="javascript:;" class="remove"> </a>');
			}
		}
	},

	/**
	*设置风格
	*/
	setStyle: function (component,val){
		var prop = "main"; 
		portlet_designer.setDataRule(component,prop,val);
		if(val=='box'){
			component.addClass('box');
			component.addClass('red');
			component.removeClass('light');
		}else if(val=='simple'){
			component.addClass('light');
			component.removeClass('box');
			component.removeClass('red');
		}else if(val=='solid'){
			component.addClass('light');
			component.addClass('bordered');
			component.removeClass('box');
			component.removeClass('red');
		}
	},
	/**
	*设置背景反转
	*/
	setBgInverse: function (component,val){
		var prop = "bgInverse"; 
		portlet_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.removeClass('bg-inverse');
		}else{
			component.addClass('bg-inverse');
		}
	},
	/**
	 * 	设置宽
	 */
	setWidth: function(component,val, unit){
		var prop = "width"; 
		portlet_designer.setDataRule(component,prop,val,unit);
		portlet_designer.setSize(component,"width",val,unit);
	},
	/**
	 * 	设置高
	 */
	setHeight: function(component,val, unit){
		var prop = "height";
		portlet_designer.setDataRule(component,prop,val,unit); 
		portlet_designer.setSize(component,"height",val,unit);
	},
	setSize:function(component,type,val,unit){
		      var scroller = component.find('.portlet-body .scroller'),
			  slimScrollDiv = component.find('.portlet-body .slimScrollDiv');
		if(val==''){
		  scroller.css(type,'');	
		  slimScrollDiv.css(type,'');
		  component.css(type,'');
		}else{
			if(unit==undefined){
				unit="px";
			}
			//if(type=="width"){
				component.css(type,val+unit);
				if(type=="height"){
					scroller.css(type,val+unit);
				    slimScrollDiv.css(type,val+unit);
				}
			//}else{
				//component.css(type,val+unit);
				//scroller.css(type,val+unit);
				//slimScrollDiv.css(type,val+unit);
			//}
			
		}
	},
	setDataRule : function(component,prop,val,unit){
        var lastVal = portlet_designer.getDataRule(component,prop)
        if(component.attr("data-rule")){
            var data_rule = JSON.parse(component.attr("data-rule"));
            data_rule[prop]=val;
        }else{
            var data_rule={};
            data_rule[prop]=val;
        }
		if(unit!=undefined){
			data_rule[prop+"_unit"]=unit;
		}
        component.attr("data-rule",JSON.stringify(data_rule));
        return lastVal;
    },
    getDataRule : function(component,prop){
        var value ="";
        if(component.attr("data-rule")){
            value = JSON.parse(component.attr("data-rule"))[prop];
        }
        return value;
    },

    setIconClass : function(component,val){
		var $component = $(component);
		
		var prop = "iconClass";
		var iconClass = portlet_designer.setDataRule(component,prop,val);

		var $i = $component.find(">.portlet-title .caption i:first");
		$i.removeClass();
		$i.addClass(val);
	},
	//自定义工具栏
	addDefinedTools : function(component,val){
		var $component = $(component);
		var prop = "definedTools";
		portlet_designer.setDataRule(component,prop,val);
		if(val=='creat'){
			var $portlet_button = $component.find(">.portlet-title>.tools>.portler_button");
			//已经存在，不用管
			if($portlet_button[0]){

			}else{
				//添加
				var $portlet_button_html = $('<div class="containerComp btn-group portler_button">'
					+	'<form role="form" class="form-inline myform">'
					+	'<div class="form-group myformgroup">'
					+		      '<button type="button" class="btn btn-default" data-role="mtbutton" scope="bootstrap" crtltype="kendo"><span class="id frame-variable" frame-variable="fv1">默认按钮</span></button>'
					+	   '</div>'
					+	   '<div class="form-group myformgroup">'
					+		      '<button type="button" class="btn btn-default" data-role="mtbutton" scope="bootstrap" crtltype="kendo"><span class="id frame-variable" frame-variable="fv1">默认按钮</span></button>'
					+	  '</div>'
					+      '<div class="form-group myformgroup">'
					+		    '<button type="button" class="btn btn-default" data-role="mtbutton" scope="bootstrap" crtltype="kendo"><span class="id frame-variable" frame-variable="fv1">默认按钮</span></button>'
					+	  '</div>'
					+    '</form>'
					+'</div>')
				$component.find(">.portlet-title>.tools").prepend($portlet_button_html);

				var toolbar = $($("#formgrouptoolbarTemplate").html());
                $portlet_button_html.find(".myformgroup").prepend(toolbar);

                var elementType = "form";	//datarole
                var dtStr = new Date().getTime();	
                var id = elementType + "_" + dtStr;	//生成唯一ID
                $portlet_button_html.attr("id",id);
                $portlet_button_html.attr("cname", id + '_控件');	//自动生成中文名称
                $portlet_button_html.addClass(id);	//增加CLASS
                $portlet_button_html.addClass("nlayout_elem");

             	//子控件重新生成ID
                var childComps= $portlet_button_html.find("[data-role]");
             	var dtStr;
                var nId;
                var oldId;

                $.each(childComps, function (i, item) {
			        oldId = $(item).attr("id");
			        dtStr = new Date().getTime() + i;
			        nId = $(item).attr("data-role") + "_" + dtStr;
			        $(item).attr("cname", nId + '_控件');
			        $(item).attr("id", nId);
			    });
                $portlet_button_html.attr("data-role", elementType);
                $portlet_button_html.attr("scope", "bootstrap");
                $portlet_button_html.attr("crtltype", "kendo");

                $portlet_button_html.attr("pdata-role", "container");
                initContainer();
			}
		}else if(val=='delete'){
			var $portlet_button = $component.find(">.portlet-title>.tools>.portler_button");
			$portlet_button.remove();
		}
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		portlet_designer.setDataRule(component,prop,val);
		portlet_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		portlet_designer.setDataRule(component,prop,val);
		portlet_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		portlet_designer.setDataRule(component,prop,val);
		portlet_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		portlet_designer.setDataRule(component,prop,val);
		portlet_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = $(component);
		if (val == '') {
			obj.css("margin-" + direct, "");
		} else {
			obj.css("margin-" + direct, val + "px");
		}
	},
	/**
	 *设置边框颜色
	 */
	setBorderColor: function(component, val) {
		var prop = "borderColor";
		// if (val != '') {
		// 	// component.css("border-color", val + " !important");
		// }else{
		// 	// component.css("border-color", 'none');
		// }
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	/**
	 *设置边框样式
	 */
	setBorderStyle: function(component, val) {
		if (val != '') {
			var prop = "borderStyle";
			portlet_designer.setDataRule(component, prop, val);
			portlet_designer.updateStyleByKey(component,prop,val);
			// component.css("border-style", val);
		}

	},
	setBorderWidth_Top: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthTop";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	setBorderWidth_Bottom: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthBottom";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	setBorderWidth_Left: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthLeft";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	setBorderWidth_Right: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthRight";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	/**
	 * 设置边框宽度
	 */
	setBorderWidth: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidth";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},

	/**
	 *设置边框颜色
	 */
	setBorderColor_title: function(component, val) {
		var prop = "borderColor_title";
		// if (val != '') {
		// 	// component.css("border-color", val + " !important");
		// }else{
		// 	// component.css("border-color", 'none');
		// }
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	/**
	 *设置边框样式
	 */
	setBorderStyle_title: function(component, val) {
		if (val != '') {
			var prop = "borderStyle_title";
			portlet_designer.setDataRule(component, prop, val);
			portlet_designer.updateStyleByKey(component,prop,val);
			// component.css("border-style", val);
		}

	},
	setBorderWidth_Top_title: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthTop_title";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	setBorderWidth_Bottom_title: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthBottom_title";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	setBorderWidth_Left_title: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthLeft_title";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},
	setBorderWidth_Right_title: function(component,val){
		if (val != '') {
			val = val + "px";
		}
		var prop = "borderWidthRight_title";
		portlet_designer.setDataRule(component, prop, val);
		portlet_designer.updateStyleByKey(component,prop,val);
	},

	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = portlet_designer.getStyler(component);
		styler[key] = val;
		portlet_designer.updateStyler(component,styler);
		portlet_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = portlet_designer.getStylerId(component);
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
		var sid = portlet_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var boxShadow = styler['boxShow_h'] || '0px';
		boxShadow += " " + (styler['boxShow_v'] || '0px');
		boxShadow += " " + (styler['boxShow_blur'] || '0px');
		boxShadow += " " + (styler['boxShow_size'] || '0px');
		boxShadow += " " + (styler['boxShow_color'] || '');

		var _default_title = _id + " .portlet-title{";
		if(styler['borderColor_title']){
			_default_title += 'border-color:'+styler['borderColor_title']+" !important;";
		}
		if(styler['borderStyle_title']){
			_default_title += 'border-style:'+styler['borderStyle_title']+" !important;";	
		}
		if(styler['borderWidthTop_title']){
			_default_title += 'border-top-width:'+styler['borderWidthTop_title']+" !important;";		
		}
		if(styler['borderWidthBottom_title']){
			_default_title += 'border-bottom-width:'+styler['borderWidthBottom_title']+" !important;";		
		}
		if(styler['borderWidthLeft_title']){
			_default_title += 'border-left-width:'+styler['borderWidthLeft_title']+" !important;";		
		}
		if(styler['borderWidthRight_title']){
			_default_title += 'border-right-width:'+styler['borderWidthRight_title']+" !important;";		
		}
		_default_title += "}"

		var _default_content = _id + "{";
		if(styler['borderColor']){
			_default_content += 'border-color:'+styler['borderColor']+" !important;";
		}
		if(styler['borderStyle']){
			_default_content += 'border-style:'+styler['borderStyle']+" !important;";	
		}
		if(styler['borderWidthTop']){
			_default_content += 'border-top-width:'+styler['borderWidthTop']+" !important;";		
		}
		if(styler['borderWidthBottom']){
			_default_content += 'border-bottom-width:'+styler['borderWidthBottom']+" !important;";		
		}
		if(styler['borderWidthLeft']){
			_default_content += 'border-left-width:'+styler['borderWidthLeft']+" !important;";		
		}
		if(styler['borderWidthRight']){
			_default_content += 'border-right-width:'+styler['borderWidthRight']+" !important;";		
		}
		// if(styler['borderWidth']){
		// 	_default_content += 'border-width:'+styler['borderWidth']+" !important;";		
		// }

		_default_content += 'box-shadow:'+ boxShadow+" ;";	

		_default_content += "}"
		
		_default += _default_content + _default_title;

		var stylebox = portlet_designer.getStyleBox(component);
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
	 * 阴影
	 */
	setBoxShow_h : function(component, val){
		var prop = "boxShow_h";
		portlet_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		portlet_designer.updateStyleByKey(component,'boxShow_h',val);
	},
	setBoxShow_v : function(component, val){
		var prop = "boxShow_v";
		portlet_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		portlet_designer.updateStyleByKey(component,'boxShow_v',val);
	},
	setBoxShow_blur : function(component, val){
		var prop = "boxShow_blur";
		portlet_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		portlet_designer.updateStyleByKey(component,'boxShow_blur',val);
	},
	setBoxShow_size : function(component, val){
		var prop = "boxShow_size";
		portlet_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		portlet_designer.updateStyleByKey(component,'boxShow_size',val);
	},
	setBoxShow_color : function(component, val){
		var prop = "boxShow_color";
		portlet_designer.setDataRule(component,prop,val);
		portlet_designer.updateStyleByKey(component,'boxShow_color',val);
	},
};