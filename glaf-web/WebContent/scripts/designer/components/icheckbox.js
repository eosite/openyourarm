var icheckbox_designer = {
	destroy: function (component){
		component.find('input').iCheck('destroy');
	},
	restore: function (component){
		component.find('input').iCheck({checkboxClass: component.attr('data-style')});
	},
	refresh: function (component, style){
		component.attr('data-style', style);
		icheckbox_proto.destroy(component);
		icheckbox_proto.restore(component);
	},
	getStaticData: function(component){
		return JSON.parse(component.attr('static-datas'));
	},
	addStaticData: function (component, obj){
		var datas = icheckbox_designer.getStaticData(component);
		datas.push(obj);
		icheckbox_designer.updateStaticDatas(component, datas);
	},
	updateStaticDatas: function (component, datas){
		component.attr('static-datas', JSON.stringify(datas));
	},
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			icheckbox_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}
	},
	/**
	 * 设置文本内容
	 */
	setContent : function(component, val) {
		var prop = "content";
		icheckbox_designer.setDataRule(component,prop,val);
		var icheckboxClass = icheckbox_designer.getFlagStyle(component);
		var obj = component.find("label div.checked");
		
		if (val != ''&&obj) {
			$.each(obj,function(i,o){
				var input = $(o).find("input");
				var label = $(o).closest("label");
				input.attr("text",val);
				if(label.find(".contentSpan")[0]){
					var $contentSpan =label.find(".contentSpan").text(val);
					label.html(input.outer());
					label.append($contentSpan);
				}else{
					label.html(input.outer()+val);
				}
				$("#"+input.attr("id")).iCheck({checkboxClass: icheckboxClass});
			});
		}

	},
	/**
	 * 设置文本值
	 */
	setContentVal : function(component, val) {
		var prop = "contentVal";
		icheckbox_designer.setDataRule(component,prop,val);
		var icheckboxClass = icheckbox_designer.getFlagStyle(component);
		var obj = component.find("label div.checked");
		if (val != ''&&obj) {
			var input =obj.find("input");
			input.val(val);
			$("#"+input.attr("id")).iCheck({checkboxClass: icheckboxClass});
		}
	},
	/**
	 * 当前风格
	 */
	getFlagStyle: function (component){
		var tginput = component.find('input');
		var dt = 'icheckbox_minimal-grey';
		if(tginput.length>0){
			dt = $(tginput[0]).attr('data-checkbox') || dt;
		}
		return dt;
	},
	/**
	 * 设置项
	 */
	setItems: function (component,val){
		val = val*1;
        if(isNaN(val) || val===0){
        	return;
        }
        
		var prop = "items"; 
		icheckbox_designer.setDataRule(component,prop,val);

		var id = component.attr('id');
		var tg = component.find('.icheck-list');
		if(!tg.length){
			tg = component.find('.icheck-inline');
		}
		var dt = icheckbox_designer.getFlagStyle(component);
		tg.empty();
		var datas = icheckbox_designer.getStaticData(component);
		var list = [];
		for (var i = 0; i < val; i++) {
			var index = i;
			var text = 'New '+(index+1);
			if(datas[index]){
				text = datas[index].text;
			}
			list.push({text: text});
			tg.append('<label><input type="checkbox" id="'+id+'_'+index+'" name="'+id+'_in" class="icheck id frame-variable" frame-variable="fvn" data-checkbox="'+dt+'"> <span class="contentSpan">'+text+'</span> </label>');
		}
		icheckbox_designer.updateStaticDatas(component, list);
		icheckbox_designer.refresh(component, dt);
	},
	/**
	 * 风格
	 */
	setFlagStyle: function (component,val){
		var prop = "flagStyle"; 
		icheckbox_designer.setDataRule(component,prop,val);
		var dt = icheckbox_proto.getFlagStyle(component);
		var tginput = component.find('input');
		var data = dt.substring(0, dt.indexOf('_'))+'_'+val;
		if(dt.indexOf('-')!=-1){
			data += dt.substring(dt.indexOf('-'));
		}
		tginput.attr('data-checkbox', data);
		icheckbox_proto.refresh(component,data);
	},
	/**
	 * 独特风格
	 */
	setParticular: function (component,val){
		var prop = "particular"; 
		icheckbox_designer.setDataRule(component,prop,val);
		var dt = icheckbox_proto.getFlagStyle(component);
		var tginput = component.find('input');
		var data = dt.substring(0, dt.indexOf('_'))+'_'+val;
		tginput.attr('data-checkbox', data);
		icheckbox_proto.refresh(component,data);
	},
	/**
	 * 皮肤
	 */
	setColor: function (component,val){
		var prop = "color"; 
		icheckbox_designer.setDataRule(component,prop,val);
		var dt = icheckbox_proto.getFlagStyle(component);
		if(dt.indexOf('polaris')!=-1 || dt.indexOf('futurico')!=-1){
			return;
		}
		var data = dt.substring(0, dt.indexOf('-'));
		if(dt.indexOf('-')==-1){
			data = dt.substring(0);
		}
		if(val!=='black'){
			data += '-'+val;
		}
		var tginput = component.find('input');
		tginput.attr('data-checkbox', data);
		icheckbox_proto.refresh(component,data);
	},
	/**
	 * 水平放置
	 */
	setHorizontalStyle: function (component,val){
		var prop = "horizontalStyle"; 
		icheckbox_designer.setDataRule(component,prop,val);
		if(val=='display'){
			var tg = component.find('.icheck-list');
			tg.addClass('icheck-inline');
			tg.removeClass('icheck-list');
		}else{
			var tg = component.find('.icheck-inline');
			tg.addClass('icheck-list');
			tg.removeClass('icheck-inline');
		}
	},
	saveStaticDatas: function(component,val){
		var items = component.find('.icheck-list').children();
		var datas = [];
		$.each(items, function(i, o) {
			datas.push({text: $(o).text()});
		});
		icheckbox_designer.updateStaticDatas(component, datas);
	},

	/**
	 * 设置内容居中
	 */
	setObjHorizontalAlign:function(component,val){
		var obj = component.find("div.input-group");
		if(val=="left"){
			obj.css("margin","");
		}else if(val=="center"){
			obj.css("margin","0 auto");
		}else if(val=="right"){
			obj.css("margin","0 0 0 auto");
		}else {
			obj.css("margin","");
		}
	},
	getBodyObject:function(component){
		var obj = component.find("label");
		return obj;
	},
	/**
	 * 设置字体
	 */
	setFontFamily : function(component, val) {
		var prop = "fontFamily";
		icheckbox_designer.setDataRule(component,prop,val);
		var obj = component.find("div.icheck-list");

		if (val == '') {
			obj.css("font-family", '');
		} else {
			obj.css("font-family", val);
		}

	},
	setFontColor : function(component, val) {
		var prop = "fontColor";
		icheckbox_designer.setDataRule(component,prop,val);
		var obj = component.find("div.icheck-list");
		icheckbox_designer.updateStyleByKey(component,'fontColor',val);
	},
	setFontStyle : function(component, val) {
		var prop = "fontStyle",that = this;
		icheckbox_designer.setDataRule(component,prop,val);
		var obj = component.find("label");
		$.each(obj,function(i,o){
			if (val == 'italic') {
				if ($(that).hasClass('active')) {
					$(o).css("font-style", 'italic');
				} else {
					$(o).css("font-style", '');
				}
			} else if (val == 'bold') {
				if ($(that).hasClass('active')) {
					$(o).css("font-weight", 'bold');
				} else {
					$(o).css("font-weight", '');
				}
			}
		});
	},
	/**
	 * 设置字体间距
	 */
	setLetterSpacing : function(component, val) {
		var prop = "letterSpacing";
		icheckbox_designer.setDataRule(component,prop,val);
		var obj = component.find("div.icheck-list");
		if (val == '') {
			obj.css("letter-spacing", '');
		} else {
			obj.css("letter-spacing", val + "px");
		}

	},
	/**
	 * 设置字体大小
	 */
	setFontSize : function(component, val) {
		var prop = "fontSize";
		icheckbox_designer.setDataRule(component,prop,val);
		var obj = component.find("div.icheck-list");
		if (val == '') {
			obj.css("font-size", '');
		} else {
			obj.css("font-size", val + "px");
		}

	},
	/**
	 * 设置文本缩进
	 */
	setTextIndent : function(component, val) {
		var prop = "textIndent";
		icheckbox_designer.setDataRule(component,prop,val);
		var obj = component.find("div.icheck-list");
		if (val == '') {
			obj.css("text-indent", '');
		} else {
			obj.css("text-indent", val + "px");
		}

	},

	setCheckButton : function(component,val){
		var prop = "checkButton";
		icheckbox_designer.setDataRule(component,prop,val);
		if (val == 'yes') {
			val = 'none';
		}else{
			val = '';
		}
		icheckbox_designer.updateStyleByKey(component,'checkButton',val);
	},
	setSelectedSpanColor : function(component,val){
		var prop = "selectedSpanColor";
		icheckbox_designer.setDataRule(component,prop,val);
		icheckbox_designer.updateStyleByKey(component,'selectedSpanColor',val);
	},
	setSelectedFontColor : function(component,val){
		var prop = "selectedFontColor";
		icheckbox_designer.setDataRule(component,prop,val);
		icheckbox_designer.updateStyleByKey(component,'selectedFontColor',val);
	},
	setCPaddingTop : function(component,val){
		var prop = "cpaddingTop";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cpaddingTop',val);
	},
	setCPaddingBottom : function(component,val){
		var prop = "cpaddingBottom";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cpaddingBottom',val);
	},
	setCPaddingLeft : function(component,val){
		var prop = "cpaddingLeft";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cpaddingLeft',val);
	},
	setCPaddingRight : function(component,val){
		var prop = "cpaddingRight";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cpaddingRight',val);
	},

	setCBorderTop : function(component,val){
		var prop = "cBorderTop";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cBorderTop',val);
	},
	setCBorderBottom : function(component,val){
		var prop = "cBorderBottom";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cBorderBottom',val);
	},
	setCBorderLeft : function(component,val){
		var prop = "cBorderLeft";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cBorderLeft',val);
	},
	setCBorderRight : function(component,val){
		var prop = "cBorderRight";
		icheckbox_designer.setDataRule(component,prop,val);
		if(val){
			val += "px";
		}
		icheckbox_designer.updateStyleByKey(component,'cBorderRight',val);
	},

	setContentBorderColor : function(component,val){
		var prop = "contentBorderColor";
		icheckbox_designer.setDataRule(component,prop,val);
		icheckbox_designer.updateStyleByKey(component,'contentBorderColor',val);
	},

	setSelectedContentBackColor : function(component,val){
		var prop = "contentBackColor";
		icheckbox_designer.setDataRule(component,prop,val);
		icheckbox_designer.updateStyleByKey(component,'contentBackColor',val);
	},

	setBorderStyle : function(component,val){
		var prop = "borderStyle";
		icheckbox_designer.setDataRule(component,prop,val);
		icheckbox_designer.updateStyleByKey(component,'borderStyle',val);
	},

	setDataRule : function(component,prop,val){
        var lastVal = icheckbox_designer.getDataRule(component,prop)
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
		var styler = icheckbox_designer.getStyler(component);
		styler[key] = val;
		icheckbox_designer.updateStyler(component,styler);
		icheckbox_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = icheckbox_designer.getStylerId(component);
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
		var sid = icheckbox_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _default_label_div = _id + " label>div{";
		if(styler['checkButton'] != null){
			_default_label_div += 	"display:" + styler['checkButton'] + " ;";
		}
		_default_label_div += "}";

		var _default_contentSpan = _id + " label .contentSpan{";
		if(styler['cpaddingTop'] != null){
			_default_contentSpan += 	"padding-top:" + styler['cpaddingTop'] + " ;";
		}
		if(styler['cpaddingBottom'] != null){
			_default_contentSpan += 	"padding-bottom:" + styler['cpaddingBottom'] + " ;";
		}
		if(styler['cpaddingLeft'] != null){
			_default_contentSpan += 	"padding-left:" + styler['cpaddingLeft'] + " ;";
		}
		if(styler['cpaddingRight'] != null){
			_default_contentSpan += 	"padding-right:" + styler['cpaddingRight'] + " ;";
		}

		if(styler['cBorderTop'] != null){
			_default_contentSpan += 	"border-top-width:" + styler['cBorderTop'] + " ;";
		}
		if(styler['cBorderBottom'] != null){
			_default_contentSpan += 	"border-bottom-width:" + styler['cBorderBottom'] + " ;";
		}
		if(styler['cBorderLeft'] != null){
			_default_contentSpan += 	"border-left-width:" + styler['cBorderLeft'] + " ;";
		}
		if(styler['cBorderRight'] != null){
			_default_contentSpan += 	"border-right-width:" + styler['cBorderRight'] + " ;";
		}
		if(styler['contentBorderColor'] != null){
			_default_contentSpan += 	"border-color:" + styler['contentBorderColor'] + " ;";
		}
		if(styler['borderStyle'] != null){
			_default_contentSpan += 	"border-style:" + styler['borderStyle'] + " ;";
		}
		if(styler['fontColor'] != null){
			_default_contentSpan += 	"color:" + styler['fontColor'] + " ;";
		}
		if(styler['contentBackColor'] != null){
			_default_contentSpan += 	"background-color:" + styler['contentBackColor'] + " ;";
		}
		_default_contentSpan += "}";

		var _default_contentSpan_s = _id + " label.selected .contentSpan{";
		if(styler['selectedSpanColor'] != null){
			_default_contentSpan_s += 	"background-color:" + styler['selectedSpanColor'] + " ;";
		}
		if(styler['selectedFontColor'] != null){
			_default_contentSpan_s += 	"color:" + styler['selectedFontColor'] + " ;";
		}
		_default_contentSpan_s += "}";

		_default += _default_label_div + _default_contentSpan + _default_contentSpan_s;

		var stylebox = icheckbox_designer.getStyleBox(component);
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
var icheckbox_proto = Object.create(icheckbox_designer);