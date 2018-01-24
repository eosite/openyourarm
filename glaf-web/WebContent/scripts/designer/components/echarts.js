/**
 * echarts
 */
var echarts_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		echarts_designer.setDataRule(component,prop,val);
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
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = echarts_designer.getBodyObject(component);
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
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		echarts_designer.setDataRule(component,prop,val);
		echarts_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
			//debugger;
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
		$(this).siblings().removeClass("active");
		echarts_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	/**
	*设置浮动
	*/
	 setFloatStyle:function(component,val){
		var prop = "float";
		echarts_designer.setDataRule(component,prop,val);
		component.css("float",val);
	},
	/**
	 * 设置宽度
	 */
	setWidth:function (component,val,unit){ 
		var prop = "width";
		echarts_designer.setDataRule(component,prop,val,unit);
		echarts_designer.setSize(component,"width",val,unit);
	},
	/**
	 * 设置高度
	 */
	setHeight:function (component,val,unit){ 
		alert("请在定义平台设置高度！");
		var prop = "height";
//		carousel_designer.setDataRule(component,prop,val,unit);
//		carousel_designer.setSize(component,"height",val,unit);
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
		
		var lastVal = echarts_designer.getDataRule(component,prop)
		
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
	setZindex:function (component,val){
		var prop = "zindex";
		echarts_designer.setDataRule(component,prop,val);
		component.css("z-index",val);
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
    	setStaticData(null,'echarts');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// echarts_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			echarts_designer.setting = $.extend(false,echarts_designer.setting,datas.dataGrid);
			
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
				setting : echarts_designer.setting,
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
			echarts_designer.setting.columns = echarts_designer.setting.columns.slice(0,echarts_designer.setting.columns.length-1);
			echarts_designer.setting.toolbar = "";
			echarts_designer.setting.type = component.attr("data-role");
			component.echarts(echarts_designer.setting);
			//获取页面id
			//获取控件id

		}
	},

}
var echarts_line_designer = Object.create(echarts_designer);
var echarts_cross_designer = Object.create(echarts_designer);
var echarts_rose_designer = Object.create(echarts_designer);
var echarts_heatmap_designer = Object.create(echarts_designer);
var echarts_panel_designer = Object.create(echarts_designer);
var echarts_mappie_designer = Object.create(echarts_designer);
