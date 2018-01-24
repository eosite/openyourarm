
var medialist_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            medialist_designer.setDataRule(component, prop, val);
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
        if ($.trim(val) == '') {
            return;
        }
        val = val * 1;
        if (isNaN(val) || val < 0) {
            return;
        }

        var prop = "width";

        medialist_designer.setDataRule(component, prop, val, unit);

        //medialist_designer.setSize(component,"width",val,unit);
        val = medialist_designer.getSize(component, "width", val, unit);

        $(component).attr('data-minWidth', val);

        /*medialist_designer.refleshTabs(component);*/
        $(component).tabs({
            minWidth: val
        });

    },

    setHeight: function(component, val, unit) {
        if ($.trim(val) == '') {
            return;
        }
        val = val * 1;
        if (isNaN(val) || val < 0) {
            return;
        }

        var prop = "height";

        medialist_designer.setDataRule(component, prop, val, unit);

        //medialist_designer.setSize(component,"height",val,unit);
        val = medialist_designer.getSize(component, "height", val, unit);

        $(component).attr('data-minHeight', val);
        
        /*medialist_designer.refleshTabs(component);*/
        $(component).tabs({
            minHeight: val
        });

    },

    setMinWidth: function(component, val, unit) {

        var prop = "minWidth";

        medialist_designer.setDataRule(component, prop, val, unit);

        medialist_designer.setSize(component, "min-width", val, unit);

    },

    setMinHeight: function(component, val, unit) {

        var prop = "minHeight";

        medialist_designer.setDataRule(component, prop, val, unit);

        medialist_designer.setSize(component, "min-height", val, unit);

    },

    setMaxWidth: function(component, val, unit) {

        var prop = "maxWidth";

        medialist_designer.setDataRule(component, prop, val, unit);

        medialist_designer.setSize(component, "max-width", val, unit);

    },

    setMaxHeight: function(component, val, unit) {

        var prop = "maxHeight";

        medialist_designer.setDataRule(component, prop, val, unit);

        medialist_designer.setSize(component, "max-height", val, unit);

    },
    setSize: function(component, type, val, unit) {
        var portlet = medialist_designer.getMainObject(component);
        if (val == '') {
            portlet.css(type, '');
        } else {
            if (unit == undefined) {
                unit = "px";
            } else {
                portlet.css(type, val + unit);
            }
        }
    },
    getSize: function(component, type, val, unit) {
        var portlet = medialist_designer.getMainObject(component);
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
        medialist_designer.setDataRule(component, prop, val);
        $(component).attr('data-border', val);

        medialist_designer.refleshTabs(component);
        $(component).tabs({
            border: eval(val)
        });
    },
   
   
    updateStaticDatas: function(component, datas) {
        component.attr('static-datas', JSON.stringify(datas));
    },

    setDataRule: function(component, prop, val,unit) {
        var lastVal = medialist_designer.getDataRule(component, prop)
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
        medialist_designer.setDataRule(component, prop, val);
        medialist_designer.setPadding(component, "top", val);
    },
    setPaddingBottom: function(component, val) {
        var prop = "paddingBottom";
        medialist_designer.setDataRule(component, prop, val);
        medialist_designer.setPadding(component, "bottom", val);
    },
    setPaddingLeft: function(component, val) {
        var prop = "paddingLeft";
        medialist_designer.setDataRule(component, prop, val);
        medialist_designer.setPadding(component, "left", val);
    },
    setPaddingRight: function(component, val) {
        var prop = "paddingRight";
        medialist_designer.setDataRule(component, prop, val);
        medialist_designer.setPadding(component, "right", val);
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

        medialist_designer.setDataRule(component, prop, val, unit);

        //medialist_designer.setSize(component,"width",val,unit);
        val = medialist_designer.getSize(component, prop, val, unit);
        $(component).attr("data-outWidth",val);
     
    },

    setOutHeight: function(component, val, unit) {
        var prop = "outHeight";
        medialist_designer.setDataRule(component, prop, val, unit);
        val = medialist_designer.getSize(component, prop, val, unit);
        $(component).attr("data-outHeight",val);
   
    },
   
	setTableStyle : function(component,val,unit){
		var prop = "tablestyle";
		medialist_designer.setDataRule(component, prop, val, unit);
		if(val == "default"){
			component.find(".mui-media-object").attr("class","mui-media-object mui-pull-left");
			component.find(".mui-table-view-cell a").attr("class","");
		} else if(val == "right"){
			component.find(".mui-media-object").attr("class","mui-media-object mui-pull-right");
			component.find(".mui-table-view-cell a").attr("class","");
		} else if(val == "right_th"){
			component.find(".mui-media-object").attr("class","mui-media-object mui-pull-left");
			component.find(".mui-table-view-cell a").attr("class","mui-navigate-right");
		} 
		
	}

};