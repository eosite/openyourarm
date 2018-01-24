/**
 * bootstrap tabs
 */
var rangecalendar_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            rangecalendar_designer.setDataRule(component, prop, val);
            component.attr("cname", val);
        }
    },
    getMainObject: function(component) {
        return component;
    },

    /**
    *设置位置
    */
     setPositionTop:function(component,val){
        var prop = "top";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPositon(component,'top',val);
    },
     setPositionBottom:function(component,val){
        var prop = "bottom";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPositon(component,'bottom',val);
    },
     setPositionLeft:function(component,val){
        var prop = "left";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPositon(component,'left',val);
    },
     setPositionRight:function(component,val){
        var prop = "right";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPositon(component,'right',val);
    },
     setPositon:function(component,direct,val){
        //var compId=component.attr("id");
        //var content=component.find(".view:first");
        //var portlet=content.find("."+compId+".portlet");
        
        var portlet=rangecalendar_designer.getMainObject(component);
        if(val==''){
          portlet.css(direct,'');
        }else{
          portlet.css(direct,val+"px");
        }
    },
    /**
    *设置定位方式
    */
     setPositonStyle:function(component,val){
        var prop = "position";
        rangecalendar_designer.setDataRule(component,prop,val);
        //var compId=component.attr("id");
        //var content=component.find(".view:first");
        //var portlet=content.find("."+compId+".portlet");
        var portlet=rangecalendar_designer.getMainObject(component);
        portlet.css("position",val);
    },

    /**
    *设置内间距
    */
    setPaddingTop:function(component,val){
        var prop = "paddingTop";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPadding(component,"top",val);
    },
     setPaddingBottom:function(component,val){
        var prop = "paddingBottom";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPadding(component,"bottom",val);
    },
     setPaddingLeft:function(component,val){
        var prop = "paddingLeft";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPadding(component,"left",val);
    },
     setPaddingRight:function(component,val){
        var prop = "paddingRight";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setPadding(component,"right",val);
    },
     setPadding:function(component,direct,val){
        
        var portletBody=rangecalendar_designer.getMainObject(component);
        if(val==''){
            portletBody.css("padding-"+direct,"");
        }else{
            portletBody.css("padding-"+direct,val+"px");
        }
    },
    
    /**
    *设置外间距
    */
     setMarginTop:function(component,val){
        var prop = "marginTop";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setMargin(component,"top",val);
    },
     setMarginBottom:function(component,val){
        var prop = "marginBottom";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setMargin(component,"bottom",val);
    },
     setMarginLeft:function(component,val){
        var prop = "marginLeft";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setMargin(component,"left",val);
    },
     setMarginRight:function(component,val){
        var prop = "marginRight";
        rangecalendar_designer.setDataRule(component,prop,val);
        rangecalendar_designer.setMargin(component,"right",val);
    },
     setMargin:function(component,direct,val){
        //var compId=component.attr("id");
        //var content=component.find(".view:first");
        //var obj=content.find("."+compId+".portlet");
        var obj=rangecalendar_designer.getMainObject(component);
        if(val==''){
                obj.css("margin-"+direct,"");
        }else{
                obj.css("margin-"+direct,val+"px");
        }
    },

    setFerialWidth : function(component,val){
        var prop = "ferialWidth";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'ferialWidth',val);
    },

    setFerialHeight : function(component,val){
        var prop = "ferialHeight";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'ferialHeight',val);
    },

    setFerialSize : function(component,val){
        var prop = "ferialSize";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'ferialSize',val);
    },

    setNumberWidth : function(component,val){
        var prop = "numberWidth";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'numberWidth',val);
    },

    setNumberHeight : function(component,val){
        var prop = "numberHeight";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'numberHeight',val);
    },

    setNumberSize : function(component,val){
        var prop = "numberSize";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'numberSize',val);
    },

    setCalendarHeight : function(component,val){
        var prop = "calendarHeight";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'calendarHeight',val);
    },

    /**
    *设置内间距
    */
    setPaddingCalendarTop:function(component,val){
        var prop = "paddingCalendarTop";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'paddingCalendarTop',val);
    },
    setPaddingCalendarBottom:function(component,val){
        var prop = "paddingCalendarBottom";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'paddingCalendarBottom',val);
    },
    setPaddingCalendarLeft:function(component,val){
        var prop = "paddingCalendarLeft";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'paddingCalendarLeft',val);
    },
    setPaddingCalendarRight:function(component,val){
        var prop = "paddingCalendarRight";
        rangecalendar_designer.setDataRule(component,prop,val);
        if(val){
            val += "px";
        }

        rangecalendar_designer.updateStyleByKey(component,'paddingCalendarRight',val);
    },

    setDataRule: function(component, prop, val,unit) {
        var lastVal = rangecalendar_designer.getDataRule(component, prop)
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
     * 获取styler并且赋值
     */
    updateStyleByKey: function (component,key,val){
        var styler = rangecalendar_designer.getStyler(component);
        styler[key] = val;
        rangecalendar_designer.updateStyler(component,styler);
        rangecalendar_designer.updateStylerBox(component,styler);
    },
    /**
     * 获取styler数组
     */
    getStyler: function (component){
        var sid = rangecalendar_designer.getStylerId(component);
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
        var sid = rangecalendar_designer.getStylerId(component);
        $('#'+sid).val(JSON.stringify(styler));
    },
    /**
     * 设置样式
     */
    updateStylerBox: function(component,styler){
        var id = component.attr('id');
        var _id = '#'+id;
        var _default = "";

        var _default_title = _id + ".range-calendar .calendar .cal-cell .cell-content .day{";
        if (styler["ferialWidth"]) {
            _default_title += " width:"+styler["ferialWidth"] + ";";
        }
        if (styler["ferialHeight"]) {
            _default_title += " height:"+styler["ferialHeight"] + ";";
        }
        if (styler["ferialSize"]) {
            _default_title += " font-size:"+styler["ferialSize"] + ";";
        }
        _default_title += "}";

        var _default_number = _id + ".range-calendar .calendar .cal-cell .cell-content .day-number{";
        if (styler["numberWidth"]) {
            _default_number += " width:"+styler["numberWidth"] + ";";
        }
        if (styler["numberHeight"]) {
            _default_number += " height:"+styler["numberHeight"] + ";";
        }
        if (styler["numberSize"]) {
            _default_number += " font-size:"+styler["numberSize"] + ";";
        }
        _default_number += "}";

        var _default_calendar = _id + ".range-calendar .calendar{";
        if (styler["calendarHeight"]) {
            _default_calendar += " height:"+styler["calendarHeight"] + ";";
        }
        if (styler["paddingCalendarTop"]) {
            _default_calendar += " padding-top:"+styler["paddingCalendarTop"] + ";";
        }
        if (styler["paddingCalendarBottom"]) {
            _default_calendar += " padding-bottom:"+styler["paddingCalendarBottom"] + ";";
        }
        if (styler["paddingCalendarLeft"]) {
            _default_calendar += " padding-left:"+styler["paddingCalendarLeft"] + ";";
        }
        if (styler["paddingCalendarRight"]) {
            _default_calendar += " padding-right:"+styler["paddingCalendarRight"] + ";";
        }
        _default_calendar += "}";

        

       
        _default += _default_title + _default_number + _default_calendar;

        var stylebox = rangecalendar_designer.getStyleBox(component);
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
    }
};