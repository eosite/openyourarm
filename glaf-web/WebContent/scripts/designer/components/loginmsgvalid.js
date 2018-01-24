
var loginmsgvalid_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            loginmsgvalid_designer.setDataRule(component, prop, val);
            component.attr("cname", val);
        }
    },
    getMainObject: function(component) {

        return component;

    },
    setDataRule: function(component, prop, val,unit) {
        var lastVal = loginmsgvalid_designer.getDataRule(component, prop)
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
    getInput: function(component){
        return component.find("input");
    },
    getValidBtn: function(component){
        return component.find('.getValidCodeBtn');
    },
    getRightPanel : function(component){
        return component.find('.rightcontent');
    },
    setInfo : function(component,val){
        var prop = "info";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        var $input = loginmsgvalid_designer.getInput(component);
        $input.attr("placeholder",val);
    },

    setCMarginTop : function(component,val){
        var prop = "cMarginTop";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("margin-top",val);
    },
    setCMarginBottom : function(component,val){
        var prop = "cMarginBottom";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("margin-bottom",val);
    },
    setCMarginLeft : function(component,val){
        var prop = "cMarginLeft";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("margin-left",val);
    },
    setCMarginRight : function(component,val){
        var prop = "cMarginRight";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("margin-right",val);
    },

    setCPaddingTop : function(component,val){
        var prop = "cPaddingTop";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("padding-top",val);
    },
    setCPaddingBottom : function(component,val){
        var prop = "cPaddingBottom";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("padding-bottom",val);
    },
    setCPaddingLeft : function(component,val){
        var prop = "cPaddingLeft";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("padding-left",val);
    },
    setCPaddingRight : function(component,val){
        var prop = "cPaddingRight";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("padding-right",val);
    },

    setCBorderTop : function(component,val){
        var prop = "cBorderTop";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("border-top-width",val);
    },
    setCBorderBottom : function(component,val){
        var prop = "cBorderBottom";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("border-bottom-width",val);
    },
    setCBorderLeft : function(component,val){
        var prop = "cBorderLeft";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("border-left-width",val);
    },
    setCBorderRight : function(component,val){
        var prop = "cBorderRight";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("border-right-width",val);
    },
    setContentBorderColor : function(component,val){
        var prop = "contentBorderColor";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("border-color",val);
    },
    setBorderStyle : function(component,val){
        var prop = "borderStyle";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("border-style",val);
    },
    setCfontSize : function(component,val){
        var prop = "cfontSize";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        if(val){
            val += "px";
        }
        $btn.css("font-size",val);
    },
    setContentFontColor : function(component,val){
        var prop = "contentFontColor";
        loginmsgvalid_designer.setDataRule(component,prop,val);
        var $btn = loginmsgvalid_designer.getValidBtn(component);
        $btn.css("color",val);
    }, 
};