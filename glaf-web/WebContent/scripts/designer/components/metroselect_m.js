var metroselect_m_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		
			var prop = "name"; 
			metroselect_m_designer.setDataRule(component,prop,val);
		
			if (val == '') {
				component.attr("cname", "");
			} else {
				component.attr("cname", val);
			}
		
	},
	/**
	 * 设置宽
	 */
	setWidthSize: function (component, val,unit){
		var prop = "widthSize"; 
		metroselect_m_designer.setDataRule(component,prop,val,unit);
		
		if (val == '') {
			metroselect_m_designer.updateStyleByKey(component,'spanWidth','auto');
		} else {
			if(unit==undefined){
				unit="px";
			}else{
				metroselect_m_designer.updateStyleByKey(component,'spanWidth',val+unit);
			}
		}
	},

	/**
	 * 设置宽
	 */
	setMaxWidth: function (component, val){
		
			var prop = "maxWidth"; 
			metroselect_m_designer.setDataRule(component,prop,val);

			if (val == '') {
				component.css('width', '');
			} else {
				component.css('width', val+'px');
			}
			
		
	},
	/**
	 * 设置高
	 */
	setMaxHeight: function (component, val){
		
			var prop = "maxHeight"; 
			metroselect_m_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.css('height', '');
			} else {
				component.css('height', val+'px');
			}
		
	},
	/**
	 * 选择提示
	 */
	setPlaceholder: function (component, val){
		var prop = "placeholder"; 
		metroselect_m_designer.setDataRule(component,prop,val);
		component.find('select').attr('data-placeholder', val);
		metroselect_m_designer.updateConf(component, prop, val);
	},
	/**
	 * 输入检索
	 */
	setTags: function (component, val){
		if(val=='hide'){
			component.find('select').attr('data-tags', false);
		}else{
			component.find('select').attr('data-tags', true);
		}
	},
	/**
	 * 清除按键
	 */
	setAllowClear: function (component, val){
		var prop = "allowClear"; 
		metroselect_m_designer.setDataRule(component,prop,val);
		var val_ = 'false';
		if(val=='hide'){
			component.find('select').attr('data-allow-clear', false);
		}else{
			component.find('select').attr('data-allow-clear', true);
			val_ = 'true';
		}
		metroselect_m_designer.updateConf(component, prop, val_);
	},
	/**
	 * 装饰
	 */
	setWidgetState: function (component, val){
		var prop = "showWidget"; 
		metroselect_m_designer.setDataRule(component,prop,val);
		if(eval(val)){
			component.find('.input-group-btn').show();
		}else{
			component.find('.input-group-btn').hide();
		}
		metroselect_m_designer.updateConf(component, prop, component.find('.input-group-btn').css('display'));
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
		metroselect_m_designer.setDataRule(component,prop,val);
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
		metroselect_m_designer.setDataRule(component,prop,val);
		var temp = component.find('select');
		if(val==='R'){
			temp.attr('dir', 'rtl');
		}else{
			temp.attr('dir', 'ltl');
		}
		temp.select2('destroy');
		temp.select2();
	},
	/**
	 * 常规尺寸
	 */
	setSize: function (component, val){
		var prop = "size"; 
		metroselect_m_designer.setDataRule(component,prop,val);

		if ($(this).hasClass('active')) {
			component.addClass('hmtd-xs');
		}else{
			component.removeClass("hmtd-xs");
		}
	
		
	},
	/**
	 * 边框风格
	 */
	setBorderStyle: function (component, val){
		var prop = "borderStyle"; 
		metroselect_m_designer.setDataRule(component,prop,val);
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
	setDataRule : function(component,prop,val){
        var lastVal = metroselect_m_designer.getDataRule(component,prop)
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
		var styler = metroselect_m_designer.getStyler(component);
		styler[key] = val;
		metroselect_m_designer.updateStyler(component,styler);
		metroselect_m_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = metroselect_m_designer.getStylerId(component);
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
		var sid = metroselect_m_designer.getStylerId(component);
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
		
		
		_default += _default_span_container

		var stylebox = metroselect_m_designer.getStyleBox(component);
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
	}
};