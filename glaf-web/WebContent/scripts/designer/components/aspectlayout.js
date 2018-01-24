
var aspectlayout_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            aspectlayout_designer.setDataRule(component, prop, val);
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

        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += unit;
        }
        var realComponent = aspectlayout_designer.getRealComponent(component);
        realComponent.width(val);
        if(realComponent.aspectlayout){
            realComponent.aspectlayout("resize");
        }
    },

    setHeight: function(component, val, unit) {

        var prop = "height";

        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += unit;
        }
        var realComponent = aspectlayout_designer.getRealComponent(component);
        realComponent.attr("realHeight",val);
        // component.height(val);
        // if(component.aspectlayout){
        //     component.aspectlayout("resize");
        // }
    },

   
    setDataRule: function(component, prop, val,unit) {
        var lastVal = aspectlayout_designer.getDataRule(component, prop)
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
    getRealComponent : function(component){
        return component.find(">.view>.aspectlayout");
    },
    getNorthComponent : function(realComponent){
        return realComponent.find(">.aspectlayout-north");
    },
    getSouthComponent : function(realComponent){
        return realComponent.find(">.aspectlayout-south");
    },
    getWestComponent : function(realComponent){
        return realComponent.find(">.aspectlayout-middle>.aspectlayout-west");
    },
    getEastComponent : function(realComponent){
        return realComponent.find(">.aspectlayout-middle>.aspectlayout-east");
    },
   
    setMarginTop: function(component, val, unit) {
        var prop = "northHeight";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += 'px';
        }
        aspectlayout_designer.setMargin(component,"top",val);
    },
    setMarginBottom: function(component, val, unit) {
        var prop = "marginBottom";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += 'px';
        }
        aspectlayout_designer.setMargin(component,"bottom",val);
    },
    setMarginLeft: function(component, val, unit) {
        var prop = "marginLeft";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += 'px';
        }
        aspectlayout_designer.setMargin(component,"left",val);
    },
    setMarginRight: function(component, val, unit) {
        var prop = "marginRight";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += 'px';
        }
        aspectlayout_designer.setMargin(component,"right",val);
    },
    setMargin : function(component,prop,val){
        var realComponent = aspectlayout_designer.getRealComponent(component);
        realComponent.css("margin-"+prop,val);
    },

    setNorthHeight: function(component, val, unit) {
        var prop = "northHeight";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += unit;
        }
        var realComponent = aspectlayout_designer.getRealComponent(component);
        var $north = aspectlayout_designer.getNorthComponent(realComponent);
        $north.css("height",val);
    },
    setSouthHeight: function(component, val, unit) {
        var prop = "southHeight";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += unit;
        }
        var realComponent = aspectlayout_designer.getRealComponent(component);
        var $south = aspectlayout_designer.getSouthComponent(realComponent);
        $south.css("height",val);
    },
    setWestWidth: function(component, val, unit) {

        var prop = "westWidth";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += unit;
        }
        var realComponent = aspectlayout_designer.getRealComponent(component);
        var $west = aspectlayout_designer.getWestComponent(realComponent);
        $west.css("width",val);
        if(realComponent.aspectlayout){
            realComponent.aspectlayout("resizeCenter");
        }
    },
    setEastWidth: function(component, val, unit) {
        var prop = "eastWidth";
        aspectlayout_designer.setDataRule(component, prop, val, unit);
        if(val){
            val += unit;
        }
        var realComponent = aspectlayout_designer.getRealComponent(component);
        var $east = aspectlayout_designer.getEastComponent(realComponent);
        $east.css("width",val);
        if(realComponent.aspectlayout){
            realComponent.aspectlayout("resizeCenter");
        }
    },
    setContent : function(component,val,unit){
        var prop = "content";
        aspectlayout_designer.setDataRule(component,prop,val);
        var realComponent = aspectlayout_designer.getRealComponent(component);
        if(val=='north'){
            if(!realComponent.find(">.aspectlayout-north")[0]){
                realComponent.prepend('<div class="aspectlayout-north"><div class="aspectlayout-content containerComp"></div></div>')
            }else{
                realComponent.find(">.aspectlayout-north").remove(); 
            }
        }else if(val=='south'){
            if(!realComponent.find(">.aspectlayout-south")[0]){
                realComponent.append('<div class="aspectlayout-south"><div class="aspectlayout-content containerComp"></div></div>')
            }else{
                realComponent.find(">.aspectlayout-south").remove(); 
            }
        }else if(val=='west'){
            if(!realComponent.find(">.aspectlayout-middle>.aspectlayout-west")[0]){
                realComponent.find(">.aspectlayout-middle").prepend('<div class="aspectlayout-west"><div class="aspectlayout-content containerComp"></div></div>')
            }else{
                realComponent.find(">.aspectlayout-middle>.aspectlayout-west").remove(); 
            }
            if(realComponent.aspectlayout){
                realComponent.aspectlayout("resizeCenter");
            }
        }else if(val=='east'){
            if(!realComponent.find(">.aspectlayout-middle>.aspectlayout-east")[0]){
                realComponent.find(">.aspectlayout-middle").append('<div class="aspectlayout-east"><div class="aspectlayout-content containerComp"></div></div>')
            }else{
                realComponent.find(">.aspectlayout-middle>.aspectlayout-east").remove(); 
            }
            if(realComponent.aspectlayout){
                realComponent.aspectlayout("resizeCenter");
            }
        }
    }
};