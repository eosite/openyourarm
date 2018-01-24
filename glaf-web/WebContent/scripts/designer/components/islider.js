/**
 * carousel
 */
var islider_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		islider_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},
	
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		// var obj=component.find(".portlet");
		if (val == '') {
			component.css("margin-" + direct, "");
		} else {
			component.css("margin-" + direct, val + "px");
		}
	},

	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		islider_designer.setDataRule(component,prop,val);
		islider_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		component.css("position", "relative");
		if (val == '') {
			component.css(direct, '');
		} else {
			component.css(direct, val + "px");
		}
	},

	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var prop = "position";
		islider_designer.setDataRule(component,prop,val);
		component.css("position", val);
	},
	/**
	 * 设置宽度
	 */
	setWidth:function (component,val,unit){ 
		var prop = "width";
		islider_designer.setDataRule(component,prop,val,unit);
		
		islider_designer.setSize(component,"width",val,unit);
	},
	/**
	 * 设置高度
	 */
	setHeight:function (component,val,unit){ 
		alert("请在定义平台设置高度！");
		var prop = "height";
//		islider_designer.setDataRule(component,prop,val,unit);
//		islider_designer.setSize(component,"height",val,unit);
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
	setDataRule : function(component,prop,val){
		
		var lastVal = islider_designer.getDataRule(component,prop)
		
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
    	setStaticData(null,'carousel');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// islider_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			islider_designer.setting = $.extend(false,islider_designer.setting,datas.dataGrid);
			
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
				setting : islider_designer.setting,
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
			});
			islider_designer.setting.fieldGrid = datas.fieldGrid;	
			islider_designer.setting.columns = islider_designer.setting.columns.slice(0,islider_designer.setting.columns.length-1);
			islider_designer.setting.toolbar = "";
			component.carouselbt(islider_designer.setting);
			//获取页面id
			//获取控件id

		}
	},
}
