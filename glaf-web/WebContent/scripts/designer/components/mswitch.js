var mswitch_designer = {
	getMainObject:function (component){

		return component;

	},

	setDefaullV : function(component,val){
		var prop = "defaultv"; 
		mswitch_designer.setDataRule(component,prop,val);

		if(val == "1"){
			component.attr("selected","selected");	
		}else{
			component.removeAttr("selected");	
		}
	},

	setRealValue : function(component,val){
		var prop = "realValue"; 
		mswitch_designer.setDataRule(component,prop,val);

		component.attr("realValue",val);
	},

	setFakeValue : function(component,val){
		var prop = "fakeValue"; 
		mswitch_designer.setDataRule(component,prop,val);
		
		component.attr("fakeValue",val);
	},

	setmarginTop : function(component,val){
		var prop = "marginTop"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'marginTop',val);
	},
	setmarginBottom : function(component,val){
		var prop = "marginBottom"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'marginBottom',val);
	},
	setmarginLeft : function(component,val){
		var prop = "marginLeft"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'marginLeft',val);
	},
	setmarginRight : function(component,val){
		var prop = "marginRight"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'marginRight',val);
	},
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			mswitch_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}else{
			component.attr("cname", "");
		}
	},

	setmarginTop : function(component,val){
		var prop = "marginTop"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		component.css("margin-top",val);
	},
	setmarginBottom : function(component,val){
		var prop = "marginBottom"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		component.css("margin-bottom",val);
		mswitch_designer.updateStyleByKey(component,'marginBottom',val);
	},
	setmarginLeft : function(component,val){
		var prop = "marginLeft"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
		component.css("margin-left",val);
		mswitch_designer.updateStyleByKey(component,'marginLeft',val);
	},
	setmarginRight : function(component,val){
		var prop = "marginRight"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}	
		component.css("margin-right",val);
		mswitch_designer.updateStyleByKey(component,'marginRight',val);
	},

	setswitchWidth : function(component,val){
		var prop = "switchWidth"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchWidth',val);
	},
	setswitchHeight : function(component,val){
		var prop = "switchHeight"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchHeight',val);
	},

	setswitchHandleWidth : function(component,val){
		var prop = "switchHandleWidth"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchHandleWidth',val);
	},
	setswitchHandleHeight : function(component,val){
		var prop = "switchHandleHeight"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchHandleHeight',val);
	},

	setswitchHandleTop : function(component,val){
		var prop = "switchHandleTop"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchHandleTop',val);
	},
	setswitchHandleLeft : function(component,val){
		var prop = "switchHandleLeft"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchHandleLeft',val);
	},

	setswitchFontSize : function(component,val){
		var prop = "switchFontSize"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchFontSize',val);
	},
	setswitchFontTop : function(component,val){
		var prop = "switchFontTop"; 
		mswitch_designer.setDataRule(component,prop,val);
		if(val){
			val = val+"px";
		}
	
		mswitch_designer.updateStyleByKey(component,'switchFontTop',val);
	},

	
	
	setSizeStyle : function(component,val){
		var prop = "sizeStyle"; 
		if(val){
			component.addClass("hmtd-xs");	
		}else{
			component.removeClass("hmtd-xs");	
		}
	},

	setStyle : function(component,val){
		var prop = "style";
		if(val){
			component.addClass("circle");	
		}else{
			component.removeClass("circle");	
		}
	},

	getStyler: function (component){
		var sid = mswitch_designer.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return styler.val()?$.parseJSON(styler.val()) : new Object();
		}
	},
	getStyleBox: function (component){
		var sid = mswitch_designer.getStyleBoxId(component);
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
		var sid = mswitch_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		 

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

		var stylebox = mswitch_designer.getStyleBox(component);
		stylebox.html(_default_switch + _default_handel_switch + _default_switch_font);
	},
	updateStyleByKey: function (component,key,val){
		var styler = mswitch_designer.getStyler(component);
		styler[key] = val;
		mswitch_designer.updateStyler(component,styler);
		mswitch_designer.updateStyleBox(component,styler);
	},
	setDataRule : function(component,prop,val,unit){
        var lastVal = mswitch_designer.getDataRule(component,prop)

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
// var dhxgantt_proto = Object.create(mswitch_designer);