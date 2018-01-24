/**
 * diagrambtbt
 */
var diagrambt_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		
		var prop = "name";
		diagrambt_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},

	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
		var obj = diagrambt_designer.getBodyObject(component);

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
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		var obj = diagrambt_designer.getBodyObject(component);
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
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		diagrambt_designer.setDataRule(component,prop,val);
		diagrambt_designer.setPositon(component, 'right', val);
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
		diagrambt_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	
	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		
		var prop = "width";
		diagrambt_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + "px");
		}

	},
	/**
	 * 设置高度
	 */
	setHeight : function(component, val) {
		var obj = diagrambt_designer.getBodyObject(component);
		var prop = "height";
		diagrambt_designer.setDataRule(component,prop,val);
		obj.removeClass("input-medium input-lg input-sm input-xs");
		if (val == '') {
			obj.css("height", '');
		} else {
			obj.css("height", val + "px");
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
		
		var lastVal = diagrambt_designer.getDataRule(component,prop)
		
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
    	setStaticData(null,'ztree');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// diagrambt_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			diagrambt_designer.setting = $.extend(false,diagrambt_designer.setting,datas.dataGrid);
			
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
				setting : diagrambt_designer.setting,
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
			
			diagrambt_designer.setting.columns = diagrambt_designer.setting.columns.slice(0,diagrambt_designer.setting.columns.length-1);
			diagrambt_designer.setting.toolbar = "";
			diagrambt_designer.setting.pan = true;
			diagrambt_designer.setting.data = diagrambt_designer.setting.datas;
			component.diagrambt(diagrambt_designer.setting);
			//获取页面id
			//获取控件id

		}
	},
}
