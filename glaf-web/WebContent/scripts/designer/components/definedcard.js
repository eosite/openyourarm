/**
 * definedcard
 */
var definedcard_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	
	setDataRule : function(component,prop,val){
		
		var lastVal = definedcard_designer.getDataRule(component,prop)
		
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
	 * 设置控件的尺寸
	 */
	setWidth:function (component,val,unit){ 
		var prop = "width";
		definedcard_designer.setDataRule(component,prop,val,unit);
		
		definedcard_designer.setSize(component,"width",val,unit);
	},
	setHeight:function (component,val,unit){ 
		var prop = "height";
		definedcard_designer.setDataRule(component,prop,val,unit);
		if(component.find("img").length){
			definedcard_designer.setSize(component.find("img"),"height",val,unit);
		}else{
			definedcard_designer.setSize(component,"height",val,unit);
		}
	},
	setSize:function (obj,type,val,unit){
		if(val==''){
		  obj.css(type,'');
		}else{
			if(unit==undefined){
				unit="px";
			}
		  	obj.css(type,val+unit);
		}
	},

	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPadding(component, "right", val);
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
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setMargin(component, "right", val);
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
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		
		var obj = component;
		
		obj.css("position","relative");
		if (val == '') {
			obj.css(direct, '');
		} else {
			obj.css(direct, val + "px");
		}
		
	},

	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var prop = "positon";
		definedcard_designer.setDataRule(component,prop,val);
		var obj = component;
		component.css("position", val);
		
	},
	/**
	*内容对齐方式
	*/
	setTextAlign:function (component,val){
		
		var prop = "textAlign";
		definedcard_designer.setDataRule(component,prop,val);
		
		definedcard_designer.updateStyleByKey(component,prop,val)
	},

	/**
	 * 整体内容内边距
	 */
	setPaddingTop_allCon : function(component, val) {
		var prop = "paddingTop_allCon";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'paddingTop_allCon',val);
	},
	setPaddingBottom_allCon : function(component, val) {
		var prop = "paddingBottom_allCon";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'paddingBottom_allCon',val);
	},
	setPaddingLeft_allCon : function(component, val) {
		var prop = "paddingLeft_allCon";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'paddingLeft_allCon',val);
	},
	setPaddingRight_allCon : function(component, val) {
		var prop = "paddingRight_allCon";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'paddingRight_allCon',val);
	},


	/**
	 * 内容内边距
	 */
	setCPaddingTop : function(component, val) {
		var prop = "cpaddingTop";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'cpaddingTop',val);
	},
	setCPaddingBottom : function(component, val) {
		var prop = "cpaddingBottom";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'cpaddingBottom',val);
	},
	setCPaddingLeft : function(component, val) {
		var prop = "cpaddingLeft";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'cpaddingLeft',val);
	},
	setCPaddingRight : function(component, val) {
		var prop = "cpaddingRight";
		definedcard_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'cpaddingRight',val);
	},

	setTitleColor: function(component,val){

		var prop = "borderBottomColor";
		definedcard_designer.setDataRule(component,prop,val);
		component.find(".definedCardExt_header").css("background-color",val);
		// definedcard_designer.updateStyleByKey(component,'borderBottomColor',val);
	},

	/**
	 * 设置内间距
	 */
	setTitlePaddingTop : function(component, val) {
		var prop = "titlePaddingTop";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setTitlePadding(component, "top", val);
	},
	setTitlePaddingBottom : function(component, val) {
		var prop = "titlePaddingBottom";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setTitlePadding(component, "bottom", val);
	},
	setTitlePaddingLeft : function(component, val) {
		var prop = "titlePaddingLeft";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setTitlePadding(component, "left", val);
	},
	setTitlePaddingRight : function(component, val) {
		var prop = "titlePaddingRight";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.setTitlePadding(component, "right", val);
	},
	setTitlePadding : function(component, direct, val) {
		var obj = component.find(".definedCardExt_header");
		
		if (val == '') {
			obj.css("padding-" + direct, "");
		} else {
			obj.css("padding-" + direct, val + "px");
		}
	},

	/**
	 * 内容间隔border颜色
	 */
	setBorderBottomColor: function(component,val) {
		var prop = "borderBottomColor";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.updateStyleByKey(component,'borderBottomColor',val);
	},

	/**
	 * 内容间隔边框大小
	 */
	setBorderBottomSize: function(component,val) {
		var prop = "borderBottomSize";
		definedcard_designer.setDataRule(component,prop,val);
		if(val){
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'borderBottomSize',val);
	},

	
	setBorderColor: function(component,val) {
		var prop = "borderColor";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.updateStyleByKey(component,'borderColor',val);
	},

	//begin-------边框大小设置
	setBorderSizeTop:function(component,val) {
		var prop = "borderSizeTop";
		definedcard_designer.setDataRule(component,prop,val);
		if(val){
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'borderSizeTop',val);
	},
	setBorderSizeBottom:function(component,val) {
		var prop = "borderSizeBottom";
		definedcard_designer.setDataRule(component,prop,val);
		if(val){
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'borderSizeBottom',val);
	},
	setBorderSizeLeft:function(component,val) {
		var prop = "borderSizeLeft";
		definedcard_designer.setDataRule(component,prop,val);
		if(val){
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'borderSizeLeft',val);
	},
	setBorderSizeRight:function(component,val) {
		var prop = "borderSizeRight";
		definedcard_designer.setDataRule(component,prop,val);
		if(val){
			val = val + "px";
		}
		definedcard_designer.updateStyleByKey(component,'borderSizeRight',val);
	},
	//end----------边框大小设置

	setSeclectedColor: function(component,val) {
		var prop = "selectedColor";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.updateStyleByKey(component,'selectedColor',val);
	},

	setHoverColor: function(component,val) {
		var prop = "hoverColor";
		definedcard_designer.setDataRule(component,prop,val);
		definedcard_designer.updateStyleByKey(component,'hoverColor',val);
	},
	

	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = definedcard_designer.getStyler(component);
		styler[key] = val;
		definedcard_designer.updateStyler(component,styler);
		definedcard_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = definedcard_designer.getStylerId(component);
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
		var sid = definedcard_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";



		var _default_item = _id + " .definedCardExt_content .definedCardExt_list li{";
		if(styler['textAlign']){
			_default_item += 'text-align:'+styler['textAlign']+";";
		}
		// if(styler['cpaddingLeft']){
		// 	_default_item += 'padding-left:'+styler['cpaddingLeft']+";";	
		// }
		// if(styler['cpaddingRight']){
		// 	_default_item += 'padding-right:'+styler['cpaddingRight']+";";	
		// }
		// if(styler['cpaddingTop']){
		// 	_default_item += 'padding-top:'+styler['cpaddingTop']+";";	
		// }
		// if(styler['cpaddingBottom']){
		// 	_default_item += 'padding-bottom:'+styler['cpaddingBottom']+";";	
		// }
		if(styler['borderBottomColor']){
			_default_item += 'border-color:'+styler['borderBottomColor']+";";
		}
		if(styler['borderBottomSize']){
			_default_item += 'border-width:'+styler['borderBottomSize']+";";
		}
		_default_item += "}"

		var _default_content = _id + " .definedCardExt_content .definedCardExt_list li .list_item_conent{";
		if(styler['textAlign']){
			_default_content += 'text-align:'+styler['textAlign']+";";
		}
		if(styler['cpaddingLeft']){
			_default_content += 'padding-left:'+styler['cpaddingLeft']+";";	
		}
		if(styler['cpaddingRight']){
			_default_content += 'padding-right:'+styler['cpaddingRight']+";";	
		}
		if(styler['cpaddingTop']){
			_default_content += 'padding-top:'+styler['cpaddingTop']+";";	
		}
		if(styler['cpaddingBottom']){
			_default_content += 'padding-bottom:'+styler['cpaddingBottom']+";";	
		}
		if(styler['borderBottomColor']){
			_default_content += 'border-color:'+styler['borderBottomColor']+";";
		}
		if(styler['borderBottomSize']){
			_default_content += 'border-width:'+styler['borderBottomSize']+";";
		}
		_default_content += "}"

		var _default_list = _id + " .definedCardExt_content .definedCardExt_list {";
		if(styler['borderColor']){
			_default_list += 'border-color:'+styler['borderColor']+";";
		}
		//begin 设置边框大小
		if(styler['borderSizeTop']){
			_default_list += 'border-top-width:'+styler['borderSizeTop']+";";
		}
		if(styler['borderSizeBottom']){
			_default_list += 'border-bottom-width:'+styler['borderSizeBottom']+";";
		}
		if(styler['borderSizeLeft']){
			_default_list += 'border-left-width:'+styler['borderSizeLeft']+";";
		}
		if(styler['borderSizeRight']){
			_default_list += 'border-right-width:'+styler['borderSizeRight']+";";
		}
		//end 设置边框大小
		if(styler['paddingTop_allCon']){
			_default_list += 'padding-top:'+styler['paddingTop_allCon']+";";
		}
		if(styler['paddingBottom_allCon']){
			_default_list += 'padding-bottom:'+styler['paddingBottom_allCon']+";";
		}
		if(styler['paddingLeft_allCon']){
			_default_list += 'padding-left:'+styler['paddingLeft_allCon']+";";
		}
		if(styler['paddingRight_allCon']){
			_default_list += 'padding-right:'+styler['paddingRight_allCon']+";";
		}
		_default_list += "}"


		var _selecetd_list = _id + " .definedCardExt_content .definedCardExt_list .selected .list_item_conent{"
		if(styler['selectedColor']){
			_selecetd_list += 'border-color:'+styler['selectedColor']+" !important;";
		}
		_selecetd_list += "}";

		var _hover_list = _id + " .definedCardExt_content .definedCardExt_list .list_item_conent:hover{"
		if(styler['hoverColor']){
			_hover_list += 'border-color:'+styler['hoverColor']+" !important;";
		}
		_hover_list += "}";
		
		_default += _default_content + _default_list + _default_item + _selecetd_list + _hover_list;

		var stylebox = definedcard_designer.getStyleBox(component);
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
	setting :{
		datas:[],
		// tableCls: 'table myTableStyle',
	    resizable: false,
	    scrollable: false,
	    clickUpdate: false,
	    occupy: false,
	    sortable: {},
	    selectable: '',
	    showInLine: true,
	    sortDesc: false,
	    combineAble: false,
	    toolbar: "[{'name':'create','text':'新增'}]",
	    columns:[],
    },
    showStaticDataPanle : function(){
    	setStaticData(null,'grid');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// definedcard_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			definedcard_designer.setting = $.extend(false,definedcard_designer.setting,datas.dataGrid);
			
			var datasourceSet_columns = [];
			var dataSourceSet = [{
				"databaseId": "",
				"datasetId": "",
				"name": "staticSource",
				"title": "静态数据数据集",
				'columns': datas.fieldGrid.datas.slice(0,datas.fieldGrid.datas.length),
			}]

			var ruleData = {
				rule : {
					dataSourceSet : dataSourceSet,
				},
				setting : definedcard_designer.setting,
				staticDataRule : datas
			}
			var pageId = id;
			var param = {
				pageId : pageId,
				componentId : component.attr("id"),
				ruleData : JSON.stringify(ruleData),
			}

			$.ajax({
				url: contextPath + '/mx/form/staticFile/save',
				type: "post",
				data: param,
				async: true,
				success: function(msg) {
					if(msg){
						
					}
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
			definedcard_designer.setting.fieldGrid = datas.fieldGrid;
			definedcard_designer.setting.columns = definedcard_designer.setting.columns.slice(0,definedcard_designer.setting.columns.length-1);
			definedcard_designer.setting.toolbar = "";
			component.definedCardExt(definedcard_designer.setting);
			//获取页面id
			//获取控件id

		}
	},
}
