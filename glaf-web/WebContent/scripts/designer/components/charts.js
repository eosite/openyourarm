/**
 * charts
 */
var charts_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		charts_designer.setDataRule(component,prop,val);
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
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = charts_designer.getBodyObject(component);
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
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		charts_designer.setDataRule(component,prop,val);
		charts_designer.setPositon(component, 'right', val);
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
		charts_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	/**
	*设置浮动
	*/
	 setFloatStyle:function(component,val){
		var prop = "float";
		charts_designer.setDataRule(component,prop,val);
		component.css("float",val);
	},
	/**
	 * 设置宽度
	 */
	setWidth:function (component,val,unit){ 
		var prop = "width";
		charts_designer.setDataRule(component,prop,val,unit);
		charts_designer.setSize(component,"width",val,unit);
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
		
		var lastVal = charts_designer.getDataRule(component,prop)
		
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
		charts_designer.setDataRule(component,prop,val);
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
    	setStaticData(null,'charts');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// charts_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			charts_designer.setting = $.extend(false,charts_designer.setting,datas.dataGrid);
			
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
				setting : charts_designer.setting,
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
			charts_designer.setting.columns = charts_designer.setting.columns.slice(0,charts_designer.setting.columns.length-1);
			charts_designer.setting.fieldGrid = datas.fieldGrid;
			charts_designer.setting.toolbar = "";
			charts_designer.setting.type = component.attr("data-role");
			component.charts(charts_designer.setting);
			//获取页面id
			//获取控件id

		}
	},

}


var charts_pile_designer = Object.create(charts_designer);
var charts_treemap_designer = Object.create(charts_designer);
var charts_area_designer = Object.create(charts_designer);
var charts_scatter_designer = Object.create(charts_designer);
var charts_bar_designer = Object.create(charts_designer);
var charts_dial_designer = Object.create(charts_designer);
var charts_pie_designer = Object.create(charts_designer);
var charts_line_designer = Object.create(charts_designer);
var charts_histo_designer = Object.create(charts_designer);