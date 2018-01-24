/**
 * metrolist
 */
var metrolist_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		metrolist_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	/**
	 * 	设置宽
	 */
	setWidth: function(component,val, unit){
		var prop = "width"; 
		metrolist_designer.setDataRule(component,prop,val);
		metrolist_designer.setSize(component,"width",val,unit);
	},
	/**
	 * 	设置高
	 */
	setHeight: function(component,val, unit){
		var prop = "height";
		metrolist_designer.setDataRule(component,prop,val,unit);
		component.find('.mt-list-container.list-simple').addClass("scroller");
		metrolist_designer.setSize(component,"height",val,unit);
	},
	setSize:function(component,type,val,unit){
		var scroller = component.find('.scroller'),
			slimScrollDiv = component.find('.slimScrollDiv');
		if(val==''){
		  scroller.css(type,'');	
		  slimScrollDiv.css(type,'');
		  component.css(type,'');
		  if(type=="height"){
		      App.destroySlimScroll(scroller);
		      scroller.removeClass("scroller");
		  }
		}else{
			if(unit==undefined){
				unit="px";
			}else{
				if(type=="width"){
					component.css(type,val+unit);
				}else{
					scroller.css(type,val+unit);
					slimScrollDiv.css(type,val+unit);
					App.initSlimScroll(component.find('.scroller'));
				}
			}
		}
	},
	
	setHeaderStyle :function(component,val){
		var prop = "headerStyle"; 
		metrolist_designer.setDataRule(component,prop,val);
		component.find(".mt-list-head").removeClass("bg-blue-chambray bg-green-sharp bg-green-seagreen bg-grey-gallery bg-yellow-crusta bg-dark bg-blue bg-red");
		component.find(".mt-list-head").addClass(val);
	},
	setFontColor :function(component,val){
		var prop = "fontColor"; 
		metrolist_designer.setDataRule(component,prop,val);
		if(val!=''){
			metrolist_designer.updateStyleByKey(component,'fontColor',val);
		}else{
			metrolist_designer.updateStyleByKey(component,'fontColor',"");
		}
	},
		updateStyleByKey: function (component,key,val){
		var styler = metrolist_designer.getStyler(component);
		styler[key] = val;
		var sid = component.attr('id')+'_styler_hidden';
		$('#'+sid).val(JSON.stringify(styler));
		
		var id = component.attr('id');
		var _id = '#'+id;
		/**
		 * default
		 */
		var _default = _id+" .mt-list-container>ul>li{";
		if(styler['fontColor']){
			_default += "color:"+styler['fontColor']+";";
		}
		_default += "}";
		//奇数行
//		var _default1 = _id+" tbody>tr:nth-of-type(odd){";
//		if(styler['bgcolor1']){
//			_default1 += "background-color:"+styler['bgcolor1']+";";
//		}
//		if(styler['bocolor1']){
//			_default1 += "border-color:"+styler['bocolor1']+";";
//		}
//		_default1 += "}";
		var stylebox = metrolist_designer.getStyleBox(component);
		stylebox.html(_default);
	},
	getStyler: function (component){
		var sid = component.attr('id')+'_styler_hidden';
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return $.parseJSON(styler.val());
		}
	},
	getStyleBox: function (component){
		var sid = component.attr('id')+'_styler_init';
		var box = component.find('#'+sid);
		if(!box.length){
			component.append("<style id=\""+sid+"\"></style>");
		}
		return component.find('#'+sid);
	},

	setDataRule : function(component,prop,val){
		
		var lastVal = metrolist_designer.getDataRule(component,prop)
		
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
	}
	,

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
    	setStaticData(null,'metrolist');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// metrolist_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			metrolist_designer.setting = $.extend(false,metrolist_designer.setting,datas.dataGrid);
			
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
				setting : metrolist_designer.setting,
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
			metrolist_designer.setting.columns = metrolist_designer.setting.columns.slice(0,metrolist_designer.setting.columns.length-1);
			metrolist_designer.setting.toolbar = "";
			component.metrolist(metrolist_designer.setting,metrolist_designer.setting.columns);
			//获取页面id
			//获取控件id

		}
	},

}