var customSelect_designer = {
	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = component;
		
		if (val == '') {
			obj.css("padding-" + direct, "");
		} else {
			obj.css("padding-" + direct, val + "px");
		}
		
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		customSelect_designer.setDataRule(component,prop,val);
		customSelect_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = component;
		if (val == '') {
			obj.css("margin-" + direct, "");
		} else {
			obj.css("margin-" + direct, val + "px");
		}
	},

	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
	
			var prop = "name"; 
			customSelect_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.attr("cname", "");
			} else {
				component.attr("cname", val);
			}
		
	},
	/**
	 * 设置宽
	 */
	setMaxWidth: function (component, val){
	
			var prop = "maxWidth"; 
			customSelect_designer.setDataRule(component,prop,val);
			
			if (val == '') {
				component.css('width', '');
			} else {
				component.css('width', val+'px');
			}
//			component.find('.select2-container').css('width', val+'px');
		
	},
	/**
	 * 设置高
	 */
	setMaxHeight: function (component, val){
		
			var prop = "maxHeight"; 
			customSelect_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.css('height', '');
			} else {
				component.css('height', val+'px');
			}
//			component.find('.select2-container').css('height', val+'px');
		
	},
	/**
	 * 设置禁用颜色
	 */
	setDisabledColor: function(component, val,unit){
		var prop = "disabledColor"; 
		customSelect_designer.setDataRule(component,prop,val,unit);
		customSelect_designer.updateStyleByKey(component,'disabledColor',val);
	},
	/**
	 * 设置宽
	 */
	setWidthSize: function (component, val,unit){
		var prop = "widthSize"; 
		customSelect_designer.setDataRule(component,prop,val,unit);
		
		if (val == '') {
			customSelect_designer.updateStyleByKey(component,'spanWidth','auto');
		} else {
			if(unit==undefined){
				unit="px";
			}else{
				customSelect_designer.updateStyleByKey(component,'spanWidth',val+unit);
			}
		}
	},
	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = customSelect_designer.getStyler(component);
		styler[key] = val;
		customSelect_designer.updateStyler(component,styler);
		customSelect_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = customSelect_designer.getStylerId(component);
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
		var sid = customSelect_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _default_span_container = _id + " span.select2-container{";
		if(styler['spanWidth']){
			_default_span_container += 'width:'+styler['spanWidth']+" !important;";
		}
		_default_span_container += "}"
		
		var _default_container_selection__rendered = _id + ' .select2-container--bootstrap .select2-selection--single .select2-selection__rendered{';
		if(styler['dir']){
			_default_container_selection__rendered += 'text-align:center !important;';
		}
		_default_container_selection__rendered += "}"


		var _default_select2_selection = _id +' .select2-container--bootstrap .select2-selection{';
		if(styler['borderWidthTop']){
			_default_select2_selection += 'border-top-width:' + styler['borderWidthTop'] + ";";
		}
		if(styler['borderWidthBottom']){
			_default_select2_selection += 'border-bottom-width:' + styler['borderWidthBottom'] + ";";
		}
		if(styler['borderWidthLeft']){
			_default_select2_selection += 'border-left-width:' + styler['borderWidthLeft'] + ";";
		}
		if(styler['borderWidthRight']){
			_default_select2_selection += 'border-right-width:' + styler['borderWidthRight'] + ";";
		}
		if(styler['borderColor']){
			_default_select2_selection += 'border-color:' + styler['borderColor'] + ";";
		}

		_default_select2_selection += "}"

		var _default_clearBtn = _id + ' .select2-container--bootstrap .selection .select2-selection__clear{';
		if(styler['clearSize']){
			_default_clearBtn += 'font-size:' + styler['clearSize'] + ";";
		}
		if(styler['clearColor']){
			_default_clearBtn += 'color:' + styler['clearColor'] + ";";
		}
		_default_clearBtn += "}"

		var _disabled_select2_selection = _id + ' .select2-container--bootstrap.select2-container--disabled .select2-selection{';
		if(styler['disabledColor']){
			_disabled_select2_selection += 'background-color:' + styler['disabledColor'] + ";";
		}
		_disabled_select2_selection += "}"

		_default += _default_span_container + _default_container_selection__rendered + _default_select2_selection + _disabled_select2_selection + _default_clearBtn;

		var stylebox = customSelect_designer.getStyleBox(component);
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
	 * 选择提示
	 */
	setPlaceholder: function (component, val){
		var prop = "placeholder"; 
		customSelect_designer.setDataRule(component,prop,val);
		component.find('select').attr('data-placeholder', val);
		customSelect_designer.updateConf(component, prop, val);
	},
	clearPlaceholder: function(component,val){
		var prop = "clearPlaceholder"; 
		customSelect_designer.setDataRule(component,prop,val);
		var val_ = 'false';
		if(val=='hide'){
			component.find('select').attr('data-placeholder',component.attr('data-placeholder'));
			val_ = 'true';
		}else{
			component.find('select').removeAttr('data-placeholder');
		}
		customSelect_designer.updateConf(component, prop, val_);
	},
	/**
	 * 清楚按钮大小设置
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setClearSize: function (component, val){
		var prop = "clearSize"; 
		customSelect_designer.setDataRule(component,prop,val);
		if(val!=''){
			val = val + 'px';
		}
		customSelect_designer.updateStyleByKey(component,'clearSize',val);
	},
	setClearColor: function (component, val){
		var prop = "clearColor"; 
		customSelect_designer.setDataRule(component,prop,val);
		
		customSelect_designer.updateStyleByKey(component,'clearColor',val);
	},
	/**
	 * 输入检索
	 */
	setTags: function (component, val){
		if(val=='hide'){
			component.children('select').attr('data-tags', false);
		}else{
			component.children('select').attr('data-tags', true);
		}
	},
	/**
	 * 清除按键
	 */
	setAllowClear: function (component, val){
		var prop = "allowClear"; 
		customSelect_designer.setDataRule(component,prop,val);
		var val_ = 'false';
		if(val=='hide'){
			component.find('select').attr('data-allow-clear', false);
		}else{
			component.find('select').attr('data-allow-clear', true);
			val_ = 'true';
		}
		customSelect_designer.updateConf(component, prop, val_);
	},
	/**
	 * 装饰
	 */
	setWidgetState: function (component, val){
		var prop = "showWidget"; 
		customSelect_designer.setDataRule(component,prop,val);
		if(eval(val)){
			component.find('.input-group-btn').show();
		}else{
			component.find('.input-group-btn').hide();
		}
		customSelect_designer.updateConf(component, prop, component.find('.input-group-btn').css('display'));
	},
	/**
	 * 装饰
	 */
	setWidget: function (component, val){

	},
	/**
	 * 装饰位置
	 */
	setWidgetPos: function (component, val){
		var prop = "position"; 
		customSelect_designer.setDataRule(component,prop,val);
		var temp = component.find('span.input-group-btn');
		if(val==='R'){
			component.find('span.input-group-btn').remove();
			component.find('div.input-group').append(temp.prop('outerHTML'));
		}else{
			temp.insertBefore(component.find('select'));
		}
	},
	/**
	 * 列表对齐
	 */
	setDir: function (component, val){
		var prop = "dir"; 
		customSelect_designer.setDataRule(component,prop,val);
		var temp = component.find('select');
		var ctl = false;
		if(val==='R'){
			temp.attr('dir', 'rtl');
		}else if(val === 'L'){
			temp.attr('dir', 'ltl');
		}else{
			ctl = true;
		}
		customSelect_designer.updateStyleByKey(component,'dir',ctl);
		temp.select2('destroy');
		temp.select2();
	},
	/**
	 * 常规尺寸
	 */
	setSize: function (component, val){
		var prop = "size"; 
		customSelect_designer.setDataRule(component,prop,val);
	
		component.removeClass("hmtd-xs");
		component.addClass('hmtd-xs');
	
	},
	/**
	 * 边框风格
	 */
	setBorderStyle: function (component, val){
		var prop = "borderStyle"; 
		customSelect_designer.setDataRule(component,prop,val);
		var temp = component.find('select');
		component.removeClass('has-success has-warning has-error');
		component.addClass('has-'+val);
		temp.select2('destroy');
		temp.select2();
	},
	updateConf: function(component, key, val){
		var designConfig = component.attr('designConfig');
		var conf = {};
		if(designConfig){
			conf = $.parseJSON(designConfig);
		}
		conf[key] = val;
		component.attr('designConfig', JSON.stringify(conf));
	},
	setDataRule : function(component,prop,val,unit){
        var lastVal = customSelect_designer.getDataRule(component,prop)
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

    /**
	*设置边框
	*/
	 setBorderTopWidth:function(component,val){
		var prop = "borderWidthTop";
		customSelect_designer.setDataRule(component,prop,val); 
		if(val!=''){
			val = val + 'px';
		}
		customSelect_designer.updateStyleByKey(component,'borderWidthTop',val);
	},
	 setBorderBottomWidth:function(component,val){
		var prop = "borderWidthBottom";
		customSelect_designer.setDataRule(component,prop,val); 
		if(val!=''){
			val = val + 'px';
		}
		customSelect_designer.updateStyleByKey(component,'borderWidthBottom',val);
	},
	 setBorderLeftWidth:function(component,val){
		var prop = "borderWidthLeft";
		customSelect_designer.setDataRule(component,prop,val);
		if(val!=''){
			val = val + 'px';
		}
		customSelect_designer.updateStyleByKey(component,'borderWidthLeft',val);
	},
	 setBorderRightWidth:function(component,val){
		var prop = "borderWidthRight";
		customSelect_designer.setDataRule(component,prop,val);
		if(val!=''){
			val = val + 'px';
		}
		customSelect_designer.updateStyleByKey(component,'borderWidthRight',val);
	},
	/**
	*设置边框颜色
	*/
	 setBorderColor:function(component,val){
		var prop = "borderColor";
		customSelect_designer.setDataRule(component,prop,val);
		//var compId=component.attr("id");
		//var content=component.find(".view:first");
		//var obj=content.find("."+compId+".portlet");
		customSelect_designer.updateStyleByKey(component,'borderColor',val);
	},

	setStaticData : function(component,dataStr){
        var prop = 'staticData';
        if(dataStr){
            var datas = JSON.parse(dataStr);
            customSelect_designer.setDataRule(component,prop,dataStr);

            $.each(datas.dataGrid.datas,function(i,item){
            	item.id = item.value;
            	item.text = item.value;
            })
			try{
				component.find('select.select2').select2('destroy');	
			}catch(e){
				component.find('.select2.select2-container').empty();
			}
            component.find('select.select2').empty();
            component.find('select.select2').select2({width:"auto",data:datas.dataGrid.datas});
        }
    },
    setMultiSelect : function(component,val){
    	var prop = "multiSelect";
		customSelect_designer.setDataRule(component,prop,val);
		if(val == "true"){
			component.find("select[name=select-choice-a]").attr("multiple","multiple");
		}
		else{
			$(component.find("select[name=select-choice-a]"))[0].removeAttribute("multiple");
		}
		
    }
};