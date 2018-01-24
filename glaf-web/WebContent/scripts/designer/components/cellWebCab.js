
var cellWebCab_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            cellWebCab_designer.setDataRule(component, prop, val);
            component.attr("cname", val);
        }
    },
    getMainObject: function(component) {

        return component;

    },

    /**
     *设置尺寸
     */
    setWidth: function(component, val, unit) {
        var prop = "width";
        cellWebCab_designer.setDataRule(component, prop, val, unit);
        component.css("width",val+unit);

    },

    setHeight: function(component, val, unit) {
    	var prop = "height";
        cellWebCab_designer.setDataRule(component, prop, val, unit);
        component.css("height",val+unit);

    },

    setMinWidth: function(component, val, unit) {

        var prop = "min-width";
        cellWebCab_designer.setDataRule(component, prop, val, unit);
        component.css("min-width",val+unit);
    },

    setMinHeight: function(component, val, unit) {

        var prop = "min-height";
        cellWebCab_designer.setDataRule(component, prop, val, unit);
        component.css("min-height",val+unit);
    },

    setMaxWidth: function(component, val, unit) {

    	var prop = "max-width";
        cellWebCab_designer.setDataRule(component, prop, val, unit);
        component.css("max-width",val+unit);

    },

    setMaxHeight: function(component, val, unit) {

    	var prop = "max-height";
        cellWebCab_designer.setDataRule(component, prop, val, unit);
        component.css("max-height",val+unit);
    },
    setSize: function(component, type, val, unit) {
     
        if (val == '') {
            portlet.css(type, '');
        } else {
            if (unit == undefined) {
                unit = "px";
            } else {
                component.css(type, val + unit);
            }
        }
    },
    getSize: function(component, type, val, unit) {
        var portlet = cellWebCab_designer.getMainObject(component);
        if (val == '') {
            return '';
        } else {
            if (unit == undefined) {
                unit = "px";
            } else {
                return val + unit;
            }
        }
    },

    setBorder: function(component, val) {
        var prop = "border";
        cellWebCab_designer.setDataRule(component, prop, val);
        $(component).attr('data-border', val);

        cellWebCab_designer.refleshTabs(component);
        $(component).tabs({
            border: eval(val)
        });
    },
   
   
    updateStaticDatas: function(component, datas) {
        component.attr('static-datas', JSON.stringify(datas));
    },

    setDataRule: function(component, prop, val,unit) {
        var lastVal = cellWebCab_designer.getDataRule(component, prop)
        if (component.attr("data-rule")) {
            var data_rule = JSON.parse(component.attr("data-rule"));
            data_rule[prop] = val;
        } else {
            var data_rule = {};
            data_rule[prop] = val;
        }
        if(unit!=undefined){
            data_rule[prop+"_unit"]=unit;
        }
        component.attr("data-rule", JSON.stringify(data_rule));
        return lastVal;
    },
    getDataRule: function(component, prop) {
        var value = "";
        if (component.attr("data-rule")) {
            value = JSON.parse(component.attr("data-rule"))[prop];
        }
        return value;
    },
   
   
    /**
     *设置内间距
     */
    setPaddingTop: function(component, val) {
        var prop = "paddingTop";
        cellWebCab_designer.setDataRule(component, prop, val);
        cellWebCab_designer.setPadding(component, "top", val);
    },
    setPaddingBottom: function(component, val) {
        var prop = "paddingBottom";
        cellWebCab_designer.setDataRule(component, prop, val);
        cellWebCab_designer.setPadding(component, "bottom", val);
    },
    setPaddingLeft: function(component, val) {
        var prop = "paddingLeft";
        cellWebCab_designer.setDataRule(component, prop, val);
        cellWebCab_designer.setPadding(component, "left", val);
    },
    setPaddingRight: function(component, val) {
        var prop = "paddingRight";
        cellWebCab_designer.setDataRule(component, prop, val);
        cellWebCab_designer.setPadding(component, "right", val);
    },
    setPadding: function(component, direct, val) {
        var portletBody = component.find(">ul>li>a");
        if (val == '') {
            portletBody.css("padding-" + direct, "");
        } else {
            portletBody.css("padding-" + direct, val + "px");
        }
    },
    /**
     *设置外尺寸
     */
    setOutWidth: function(component, val, unit) {
        
        var prop = "outWidth";

        cellWebCab_designer.setDataRule(component, prop, val, unit);

        //cellWebCab_designer.setSize(component,"width",val,unit);
        val = cellWebCab_designer.getSize(component, prop, val, unit);
        $(component).attr("data-outWidth",val);
     
    },

    setOutHeight: function(component, val, unit) {
        var prop = "outHeight";
        cellWebCab_designer.setDataRule(component, prop, val, unit);
        val = cellWebCab_designer.getSize(component, prop, val, unit);
        $(component).attr("data-outHeight",val);
   
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
    	setStaticData(null,'ztree');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// cellWebCab_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			cellWebCab_designer.setting = $.extend(false,cellWebCab_designer.setting,datas.dataGrid);
			
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
				setting : cellWebCab_designer.setting,
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
			cellWebCab_designer.setting.columns = cellWebCab_designer.setting.columns.slice(0,cellWebCab_designer.setting.columns.length-1);
			cellWebCab_designer.setting.toolbar = "";
			component.nestable(cellWebCab_designer.setting);
			//获取页面id
			//获取控件id

		}
	},

};