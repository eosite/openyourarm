/**
 * bootstrap tabs
 */
var muitab_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            muitab_designer.setDataRule(component, prop, val);
            component.attr("cname", val);
        }
    },
    getMainObject: function(component) {

        return component;

    },
    setProgress : function(component,val,unit){
        var prop = "progress"; 
        muitab_designer.setDataRule(component,prop,val);

        if(val == "1"){
            component.find(".sliderProgressBar").show();
        }else{
            component.find(".sliderProgressBar").hide();
        }
    },
    setProgressColor : function(component,val){
        var prop = "progress"; 
        muitab_designer.setDataRule(component,prop,val);

        component.find(".sliderProgressBar").css("background-color",val);
    },
    setContentBackColor : function(component,val){
        var prop = "contentBackColor"; 
        muitab_designer.setDataRule(component,prop,val);

        muitab_designer.updateStyleByKey(component,'contentBackColor',val);
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
        if(val){
            val += unit;
        }
        component.attr("realWidth",val);
        component.css("width",val);
        muitab_designer.setDataRule(component, prop, val, unit);
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
        if(val){
            val += unit;
        }
        component.attr("realHeight",val);
        component.css("height",val);
        muitab_designer.setDataRule(component, prop, val, unit);
    },
    
    updateTab: function(component, val) {

        $(component).find(".mui-control-item.mui-active").html(val);

    },

    deleteTab: function(component, val) {
        var $active = $(component).find(".mui-control-item.mui-active");
        if($active[0]){
            $(component).muitab('remove',$active.attr("row-index"))
        }
    },

    /**
     * 设置项
     */
    setItems: function(component, val) {
        val = val * 1;
        if (isNaN(val) || val === 0) {
            return;
        }

        var prop = "items";
        muitab_designer.setDataRule(component, prop, val);
        var tabsLength = component.muitab('getMaxLength'),
            maxLength = component.muitab('getMaxNumber');
            diff = val - tabsLength;
        if (diff > 0) {
            for (var i = 0; i < diff; i++) {
                component.muitab("addTabContent", {
                    title: 'New Tab ' + (maxLength + i + 1),
                    content: "",
                    minHeight: 200
                });
            }
        } else if (diff < 0) {
            for (var i = 0; i < -diff; i++) {
                component.muitab("removeLast");
            }
        }
    },

    setProgressDelay : function(component, val){
        var prop = "progressDelay";
        muitab_designer.setDataRule(component, prop, val);
        if(val){
            val += "ms";
        }
        component.find(".sliderProgressBar").css("transition-duration",val);
    },


    setDataRule: function(component, prop, val,unit) {
        var lastVal = muitab_designer.getDataRule(component, prop)
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

    setFontSize : function(component, val){
        var prop = "fontSize";
        muitab_designer.setDataRule(component, prop, val);
        if(val){
            val += "px";
        }
        muitab_designer.updateStyleByKey(component,'fontSize',val);
    },

    setTitleHeight : function(component, val){
        var prop = "titleHeight";
        muitab_designer.setDataRule(component, prop, val);
        if(val){
            val += "px";
        }
        muitab_designer.updateStyleByKey(component,'titleHeight',val);
    },
    setTitleColor : function(component, val){
        var prop = "setTitleColor";
        muitab_designer.setDataRule(component, prop, val);
        muitab_designer.updateStyleByKey(component,'setTitleColor',val);
    },
    setTitleBackColor : function(component, val){
        var prop = "titleBackColor";
        muitab_designer.setDataRule(component, prop, val);
        muitab_designer.updateStyleByKey(component,'titleBackColor',val);
    },
    setTitleColor_s : function(component, val){
        var prop = "titleColor_s";
        muitab_designer.setDataRule(component, prop, val);
        muitab_designer.updateStyleByKey(component,'titleColor_s',val);
    },
    setTitleBackColor_s : function(component, val){
        var prop = "titleBackColor_s";
        muitab_designer.setDataRule(component, prop, val);
        muitab_designer.updateStyleByKey(component,'titleBackColor_s',val);
    },

    /**
     * 获取styler并且赋值
     */
    updateStyleByKey: function (component,key,val){
        var styler = muitab_designer.getStyler(component);
        styler[key] = val;
        muitab_designer.updateStyler(component,styler);
        muitab_designer.updateStylerBox(component,styler);
    },
    /**
     * 获取styler数组
     */
    getStyler: function (component){
        var sid = muitab_designer.getStylerId(component);
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
        var sid = muitab_designer.getStylerId(component);
        $('#'+sid).val(JSON.stringify(styler));
    },
    /**
     * 设置样式
     */
    updateStylerBox: function(component,styler){
        var id = component.attr('id');
        var _id = '#'+id;
        var _default = "";

        var _default_title = _id + ' .sliderSegmentedTitle .mui-control-item{';
        if(styler['fontSize']){
            _default_title += 'font-size:'+styler['fontSize']+";";
        }
        if(styler['titleHeight']){
            _default_title += 'line-height:'+styler['titleHeight']+";";
        }
        if(styler['setTitleColor']){
            _default_title += 'color:'+styler['setTitleColor']+";";
        }
        if(styler['titleBackColor']){
            _default_title += 'background-color:'+styler['titleBackColor']+";";
        }
        _default_title += "}";

        var _default_title_s = _id + ' .sliderSegmentedTitle .mui-control-item.mui-active{';
        if(styler['titleColor_s']){
            _default_title_s += 'color:'+styler['titleColor_s']+";";
        }
        if(styler['titleBackColor_s']){
            _default_title_s += 'background-color:'+styler['titleBackColor_s']+";";
        }
        _default_title_s += "}";

        var _default_content = _id + ' .slider .mui-slider-item{';
        if(styler['contentBackColor']){
            _default_content += 'background-color:'+styler['contentBackColor']+";";
        }
        _default_content += "}";

        _default += _default_title + _default_title_s + _default_content;

        var stylebox = muitab_designer.getStyleBox(component);
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