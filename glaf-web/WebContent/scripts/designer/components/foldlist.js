var foldlist_designer = {
	getMainObject:function (component){

		return component;

	},


	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			foldlist_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}else{
			component.attr("cname", "");
		}
	},

	settitlePaddingTop : function(component,val){
		var prop = "titlePaddingTop"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'titlePaddingTop',val);
	},
	settitlePaddingBottom : function(component,val){
		var prop = "titlePaddingBottom"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'titlePaddingBottom',val);
	},
	settitlePaddingLeft : function(component,val){
		var prop = "titlePaddingLeft"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'titlePaddingLeft',val);
	},
	settitlePaddingRight : function(component,val){
		var prop = "titlePaddingRight"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'titlePaddingRight',val);
	},

	setselectedColor : function(component,val){
		var prop = "selectedColor"; 
		foldlist_designer.setDataRule(component,prop,val);
	
		foldlist_designer.updateStyleByKey(component,'selectedColor',val);
	},
	
	setSizeStyle : function(component,val){
		var prop = "sizeStyle"; 
		if(val){
			component.addClass("hmtd-xs");	
		}else{
			component.removeClass("hmtd-xs");	
		}
	},

	setswitchWidth : function(component,val){
		var prop = "switchWidth"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchWidth',val);
	},
	setswitchHeight : function(component,val){
		var prop = "switchHeight"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchHeight',val);
	},

	setswitchHandleWidth : function(component,val){
		var prop = "switchHandleWidth"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchHandleWidth',val);
	},
	setswitchHandleHeight : function(component,val){
		var prop = "switchHandleHeight"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchHandleHeight',val);
	},

	setswitchHandleTop : function(component,val){
		var prop = "switchHandleTop"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchHandleTop',val);
	},
	setswitchHandleLeft : function(component,val){
		var prop = "switchHandleLeft"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchHandleLeft',val);
	},

	setswitchFontSize : function(component,val){
		var prop = "switchFontSize"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchFontSize',val);
	},
	setswitchFontTop : function(component,val){
		var prop = "switchFontTop"; 
		foldlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		foldlist_designer.updateStyleByKey(component,'switchFontTop',val);
	},

	getStyler: function (component){
		var sid = foldlist_designer.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return styler.val()?$.parseJSON(styler.val()) : new Object();
		}
	},
	getStyleBox: function (component){
		var sid = foldlist_designer.getStyleBoxId(component);
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
		var sid = foldlist_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		 
		var _default_selectedRow = _id+" .foldListRow.selected .mui-card-header{";
		if(styler['selectedColor']){
			_default_selectedRow += "background-color:"+styler['selectedColor']+";";
		}
		//表格进度条样式
		_default_selectedRow += "}";

		var _default_title = _id+" .mui-card-header{";
		if(styler['titlePaddingTop']){
			_default_title += "padding-top:"+styler['titlePaddingTop']+";";
		}
		if(styler['titlePaddingBottom']){
			_default_title += "padding-bottom:"+styler['titlePaddingBottom']+";";
		}
		if(styler['titlePaddingLeft']){
			_default_title += "padding-left:"+styler['titlePaddingLeft']+";";
		}
		if(styler['titlePaddingRight']){
			_default_title += "padding-right:"+styler['titlePaddingRight']+";";
		}
		//表格进度条样式
		_default_title += "}";


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

		var stylebox = foldlist_designer.getStyleBox(component);
		stylebox.html(_default_selectedRow + _default_title + _default_switch +_default_handel_switch + _default_switch_font);
	},
	updateStyleByKey: function (component,key,val){
		var styler = foldlist_designer.getStyler(component);
		styler[key] = val;
		foldlist_designer.updateStyler(component,styler);
		foldlist_designer.updateStyleBox(component,styler);
	},
	setDataRule : function(component,prop,val,unit){
        var lastVal = foldlist_designer.getDataRule(component,prop)

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
// var dhxgantt_proto = Object.create(foldlist_designer);