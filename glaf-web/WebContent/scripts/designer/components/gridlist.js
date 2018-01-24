/**
 * gridList
 */
var gridlist_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	getBodyObject : function(component) {
		var mainBody;
		if (component.find("input").length) {
			mainBody = $(component.find("input"));
		} else {
			mainBody = $(component);
		}
		return mainBody;
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = gridlist_designer.getDataRule(component,prop)
		
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
		gridlist_designer.setDataRule(component,prop,val,unit);
		
		gridlist_designer.setSize(component,"width",val,unit);
	},
	setHeight:function (component,val,unit){ 
		var prop = "height";
		gridlist_designer.setDataRule(component,prop,val,unit);
		if(component.find("img").length){
			gridlist_designer.setSize(component.find("img"),"height",val,unit);
		}else{
			gridlist_designer.setSize(component,"height",val,unit);
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

	setZindex:function (component,val,unit){ 
		var prop = "zindex";
		gridlist_designer.setDataRule(component,prop,val,unit);
		// component.css("z-index",val);
		gridlist_designer.updateStyleByKey(component,'zindex',val);
	},

	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = gridlist_designer.getBodyObject(component);
		
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
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = gridlist_designer.getBodyObject(component);
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
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		
		var obj = gridlist_designer.getBodyObject(component);
		
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
		gridlist_designer.setDataRule(component,prop,val);
		var obj = gridlist_designer.getBodyObject(component);
		component.css("position", val);
		
	},
	/**
	*内容对齐方式
	*/
	setTextAlign:function (component,val){
		
		var prop = "textAlign";
		gridlist_designer.setDataRule(component,prop,val);
		
		gridlist_designer.updateStyleByKey(component,prop,val)
	},

	/**
	 * 分页按钮对齐方式
	 */
	setPageBtAlign:function(component,val){
		debugger
		var prop = "pageBtAlign";
		gridlist_designer.setDataRule(component,prop,val);
		
		gridlist_designer.updateStyleByKey(component,prop,val);
	},

	/**
	 * 内容内边距
	 */
	setCPaddingTop : function(component, val) {
		var prop = "cpaddingTop";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'cpaddingTop',val);
	},
	setCPaddingBottom : function(component, val) {
		var prop = "cpaddingBottom";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'cpaddingBottom',val);
	},
	setCPaddingLeft : function(component, val) {
		var prop = "cpaddingLeft";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'cpaddingLeft',val);
	},
	setCPaddingRight : function(component, val) {
		var prop = "cpaddingRight";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'cpaddingRight',val);
	},

	/**
	 * 阴影
	 */
	setBoxShow_h : function(component, val){
		var prop = "boxShow_h";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'boxShow_h',val);
	},
	setBoxShow_v : function(component, val){
		var prop = "boxShow_v";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'boxShow_v',val);
	},
	setBoxShow_blur : function(component, val){
		var prop = "boxShow_blur";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'boxShow_blur',val);
	},
	setBoxShow_size : function(component, val){
		var prop = "boxShow_size";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'boxShow_size',val);
	},
	setBoxShow_color : function(component, val){
		var prop = "boxShow_color";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.updateStyleByKey(component,'boxShow_color',val);
	},

	setBorderRadius : function(component, val){
		var prop = "borderRadius";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'borderRadius',val);
	}, 


	/**
	 * 分页外间距
	 */
	setPageBtMarginTop : function(component, val) {
		var prop = "pageBtMarginTop";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'pageBtMarginTop',val);
	},
	setPageBtMarginBottom : function(component, val) {
		var prop = "pageBtMarginBottom";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'pageBtMarginBottom',val);
	},
	setPageBtMarginLeft : function(component, val) {
		var prop = "pageBtMarginLeft";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'pageBtMarginLeft',val);
	},
	setPageBtMarginRight : function(component, val) {
		var prop = "pageBtMarginRight";
		gridlist_designer.setDataRule(component,prop,val);
		if (val == '') {
			val = '0px';
		} else {
			val = val + "px";
		}
		gridlist_designer.updateStyleByKey(component,'pageBtMarginRight',val);
	},

	/**
	 * 悬停格背景颜色
	 */
	setBackgroundColor_hover : function(component, val) {
		var prop = "background_color_h";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.updateStyleByKey(component,'background_color_h',val);
	},

	/**
	 * 背景颜色
	 */
	setBackgroundColor : function(component, val) {
		var prop = "background_color";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.updateStyleByKey(component,'background_color',val);
	},

	setBackgroundColor_selected : function(component, val) {
		var prop = "background_color_s";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.updateStyleByKey(component,'background_color_s',val);
	},

	setBackgroundColor_d : function(component, val) {
		var prop = "background_color_d";
		gridlist_designer.setDataRule(component,prop,val);
		gridlist_designer.updateStyleByKey(component,'background_color_d',val);
	},

	setBackgroundColor_do : function(component, val) {
		var prop = "background_color_do";
		gridlist_designer.setDataRule(component,prop,val);
		if(val){
			val = val/100;
		}
		gridlist_designer.updateStyleByKey(component,'background_color_do',val);
	},


	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = gridlist_designer.getStyler(component);
		styler[key] = val;
		gridlist_designer.updateStyler(component,styler);
		gridlist_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = gridlist_designer.getStylerId(component);
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
		var sid = gridlist_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _main_ = _id + "{";
		if(styler['zindex']){
			_main_ += 'z-Index:'+styler['zindex']+";";
		}
		_main_ += "}"

		var _default_content_li = _id + " .gridList_content .gridList_box .gridList_ul> li{";
		if(styler['cpaddingLeft']){
			_default_content_li += 'padding-left:'+styler['cpaddingLeft']+";";	
		}
		if(styler['cpaddingRight']){
			_default_content_li += 'padding-right:'+styler['cpaddingRight']+";";	
		}
		if(styler['cpaddingTop']){
			_default_content_li += 'padding-top:'+styler['cpaddingTop']+";";	
		}
		if(styler['cpaddingBottom']){
			_default_content_li += 'padding-bottom:'+styler['cpaddingBottom']+";";	
		}
		_default_content_li += "}"

		var _default_content = _id + " .gridList_content .gridList_box .demo_content{";
		if(styler['textAlign']){
			_default_content += 'text-align:'+styler['textAlign']+";";
		}
		if(styler['background_color']){
			_default_content += 'background-color:'+styler['background_color']+" ;";	
		}

		if(styler['borderRadius']){
			_default_content += 'border-radius:'+styler['borderRadius']+" !important;";	
		}
		var boxShadow = styler['boxShow_h'] || '0px';
		boxShadow += " " + (styler['boxShow_v'] || '0px');
		boxShadow += " " + (styler['boxShow_blur'] || '0px');
		boxShadow += " " + (styler['boxShow_size'] || '0px');
		boxShadow += " " + (styler['boxShow_color'] || '');

		_default_content += 'box-shadow:'+ boxShadow+" ;";	
		// if(styler['boxShow_h']){
			//_default_content += 'box-shadow:'+styler['boxShow_h']+" "+ styler['boxShow_v']+" "+ styler['boxShow_blur'] + " "+ styler['boxShow_size'] + " "+ styler['boxShow_color'] +" ;";	
		// }
		if(styler['background_color']){
			_default_content += 'background-color:'+styler['background_color']+" ;";	
		}
		if(styler['background_color']){
			_default_content += 'background-color:'+styler['background_color']+" ;";	
		}
		if(styler['background_color']){
			_default_content += 'background-color:'+styler['background_color']+" ;";	
		}
		if(styler['background_color']){
			_default_content += 'background-color:'+styler['background_color']+" ;";	
		}
		_default_content += "}"



		var _default_pageBt = _id + " .pagination {";
		if(styler['pageBtAlign']){
			_default_pageBt += 'float:'+styler['pageBtAlign']+";";
		}
		if(styler['pageBtMarginLeft']){
			_default_pageBt += 'margin-left:'+styler['pageBtMarginLeft']+";";	
		}
		if(styler['pageBtMarginRight']){
			_default_pageBt += 'margin-right:'+styler['pageBtMarginRight']+";";	
		}
		if(styler['pageBtMarginTop']){
			_default_pageBt += 'margin-top:'+styler['pageBtMarginTop']+";";	
		}
		if(styler['pageBtMarginBottom']){
			_default_pageBt += 'margin-bottom:'+styler['pageBtMarginBottom']+";";	
		}
		_default_pageBt += "}";

		var _default_cell_h = _id + " .gridList_box .gridList_ul>li>.demo_content:hover{";
		if(styler['background_color_h']){
			_default_cell_h += 'background-color:'+styler['background_color_h']+" ;";	
		}
		_default_cell_h += "}";

		var _default_cell_s = _id + " .gridList_box .gridList_ul>li>.demo_content.selected{";
		if(styler['background_color_s']){
			_default_cell_s += 'background-color:'+styler['background_color_s']+" ;";	
		}
		_default_cell_s += "}";

		var _default_cell_d = _id + " .gridList_box .gridList_ul>li>.demo_content.disabledCell{";
		if(styler['background_color_d']){
			_default_cell_d += 'background-color:'+styler['background_color_d']+" !important;";	
		}
		if(styler['background_color_do']){
			_default_cell_d += 'opacity:'+styler['background_color_do']+" !important;";	
		}
		_default_cell_d += "}";


		_default += _main_ + _default_content_li + _default_content + _default_pageBt + _default_cell_h + _default_cell_s + _default_cell_d;

		var stylebox = gridlist_designer.getStyleBox(component);
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
    	setStaticData(null,'gridlist');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// gridlist_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			gridlist_designer.setting = $.extend(false,gridlist_designer.setting,datas.dataGrid);
			
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
				setting : gridlist_designer.setting,
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
			gridlist_designer.setting.columns = gridlist_designer.setting.columns.slice(0,gridlist_designer.setting.columns.length-1);
			gridlist_designer.setting.toolbar = "";
			component.gridList(gridlist_designer.setting);
			//获取页面id
			//获取控件id

		}
	},
}


/**
 * gridList
 */
var gridList_designer = gridlist_designer