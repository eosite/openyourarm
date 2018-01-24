var levellist_designer = {
	getMainObject:function (component){

		return component;

	},


	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			levellist_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}else{
			component.attr("cname", "");
		}
	},

	setcontentPaddingTop : function(component,val){
		var prop = "contentPaddingTop"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'contentPaddingTop',val);
	},
	setcontentPaddingBottom : function(component,val){
		var prop = "contentPaddingBottom"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'contentPaddingBottom',val);
	},
	setcontentPaddingLeft : function(component,val){
		var prop = "contentPaddingLeft"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'contentPaddingLeft',val);
	},
	setcontentPaddingRight : function(component,val){
		var prop = "contentPaddingRight"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'contentPaddingRight',val);
	},

	setheaderPaddingTop : function(component,val){
		var prop = "headerPaddingTop"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'headerPaddingTop',val);
	},
	setheaderPaddingBottom : function(component,val){
		var prop = "headerPaddingBottom"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'headerPaddingBottom',val);
	},
	setheaderPaddingLeft : function(component,val){
		var prop = "headerPaddingLeft"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'headerPaddingLeft',val);
	},
	setheaderPaddingRight : function(component,val){
		var prop = "headerPaddingRight"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'headerPaddingRight',val);
	},

	setheaderFontSize : function(component,val){
		var prop = "headerFontSize"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'headerFontSize',val);
	},

	setselectedColor : function(component,val){
		var prop = "selectedColor"; 
		levellist_designer.setDataRule(component,prop,val);
	
		levellist_designer.updateStyleByKey(component,'selectedColor',val);
	},

	setswitchWidth : function(component,val){
		var prop = "switchWidth"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchWidth',val);
	},
	setswitchHeight : function(component,val){
		var prop = "switchHeight"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchHeight',val);
	},

	setswitchHandleWidth : function(component,val){
		var prop = "switchHandleWidth"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchHandleWidth',val);
	},
	setswitchHandleHeight : function(component,val){
		var prop = "switchHandleHeight"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchHandleHeight',val);
	},

	setswitchHandleTop : function(component,val){
		var prop = "switchHandleTop"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchHandleTop',val);
	},
	setswitchHandleLeft : function(component,val){
		var prop = "switchHandleLeft"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchHandleLeft',val);
	},

	setswitchFontSize : function(component,val){
		var prop = "switchFontSize"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchFontSize',val);
	},
	setswitchFontTop : function(component,val){
		var prop = "switchFontTop"; 
		levellist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		levellist_designer.updateStyleByKey(component,'switchFontTop',val);
	},

	
	
	setSizeStyle : function(component,val){
		var prop = "sizeStyle"; 
		if(val){
			component.addClass("hmtd-xs");	
		}else{
			component.removeClass("hmtd-xs");	
		}
	},

	getStyler: function (component){
		var sid = levellist_designer.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return styler.val()?$.parseJSON(styler.val()) : new Object();
		}
	},
	getStyleBox: function (component){
		var sid = levellist_designer.getStyleBoxId(component);
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
		var sid = levellist_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		 
		var _default_selectedRow = _id+" .levelListRow.selected{";
		if(styler['selectedColor']){
			_default_selectedRow += "background-color:"+styler['selectedColor']+";";
		}
		//表格进度条样式
		_default_selectedRow += "}";

		var _default_title = _id+" .levelListRow{";
		if(styler['contentPaddingTop']){
			_default_title += "padding-top:"+styler['contentPaddingTop']+";";
		}
		if(styler['contentPaddingBottom']){
			_default_title += "padding-bottom:"+styler['contentPaddingBottom']+";";
		}
		if(styler['contentPaddingLeft']){
			_default_title += "padding-left:"+styler['contentPaddingLeft']+";";
		}
		if(styler['contentPaddingRight']){
			_default_title += "padding-right:"+styler['contentPaddingRight']+";";
		}
		//表格进度条样式
		_default_title += "}";

		var _default_header = _id+" .mui-card-header{";
		if(styler['headerPaddingTop']){
			_default_header += "padding-top:"+styler['headerPaddingTop']+";";
		}
		if(styler['headerPaddingBottom']){
			_default_header += "padding-bottom:"+styler['headerPaddingBottom']+";";
		}
		if(styler['headerPaddingLeft']){
			_default_header += "padding-left:"+styler['headerPaddingLeft']+";";
		}
		if(styler['headerPaddingRight']){
			_default_header += "padding-right:"+styler['headerPaddingRight']+";";
		}
		//表格进度条样式
		_default_header += "}";

		var _default_header_text = _id+" .mui-card-header .headCell{";
		if(styler['headerFontSize']){
			_default_header_text += "font-size:"+styler['headerFontSize']+";";
		}
		//表格进度条样式
		_default_header_text += "}";

		var _default_switch = _id+" .mui-switch{";
		if(styler['switchWidth']){
			_default_switch += "width:"+styler['switchWidth']+";";
		}
		if(styler['switchHeight']){
			_default_switch += "height:"+styler['switchHeight']+";";
		}
		//表格进度条样式
		_default_switch += "}";

	
		var _default_handel_switch = _id+" .mui-switch .mui-switch-handle{";
		if(styler['switchHandleWidth']){
			_default_handel_switch += "width:"+styler['switchHandleWidth']+";";
		}
		if(styler['switchHandleHeight']){
			_default_handel_switch += "height:"+styler['switchHandleHeight']+";";
		}
		if(styler['switchHandleTop']){
			_default_handel_switch += "top:"+styler['switchHandleTop']+";";
		}
		if(styler['switchHandleLeft']){
			_default_handel_switch += "left:"+styler['switchHandleLeft']+";";
		}
		//表格进度条样式
		_default_handel_switch += "}";		

		var _default_switch_font = _id+" .mui-switch:before{";
		if(styler['switchFontSize']){
			_default_switch_font += "font-size:"+styler['switchFontSize']+";";
		}
		if(styler['switchFontTop']){
			_default_switch_font += "top:"+styler['switchFontTop']+";";
		}
		//表格进度条样式
		_default_switch_font += "}";

		var stylebox = levellist_designer.getStyleBox(component);
		stylebox.html(_default_selectedRow + _default_title + _default_header + _default_header_text + _default_switch + _default_handel_switch + _default_switch_font);
	},
	updateStyleByKey: function (component,key,val){
		var styler = levellist_designer.getStyler(component);
		styler[key] = val;
		levellist_designer.updateStyler(component,styler);
		levellist_designer.updateStyleBox(component,styler);
	},
	setDataRule : function(component,prop,val,unit){
        var lastVal = levellist_designer.getDataRule(component,prop)

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

};
// var dhxgantt_proto = Object.create(levellist_designer);