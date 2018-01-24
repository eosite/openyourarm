/**
 * bootstrap tabs
 */
var bootstrap_tabs_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            bootstrap_tabs_designer.setDataRule(component, prop, val);
            component.attr("cname", val);
        }
    },
    getMainObject: function(component) {

        return component;

    },
    refleshTabs: function(component) {
        var datas = component.tabs('tabs');
        datas = component.tabs('setContents', datas);
        //component.tabs('destroy');
        //component.tabs('load', datas);
    },

    setMaxtabsnum : function(component,val){
        var prop = "maxtabsnum";

        bootstrap_tabs_designer.setDataRule(component, prop, val);

        if(val){
            $(component).attr('maxtabsnum', val);
        }else{
            $(component).removeAttr('maxtabsnum');
        }
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

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        //bootstrap_tabs_designer.setSize(component,"width",val,unit);
        val = bootstrap_tabs_designer.getSize(component, "width", val, unit);

        $(component).attr('data-minWidth', val);

        /*bootstrap_tabs_designer.refleshTabs(component);*/
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

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        //bootstrap_tabs_designer.setSize(component,"height",val,unit);
        val = bootstrap_tabs_designer.getSize(component, "height", val, unit);

        $(component).attr('data-minHeight', val);
        
        /*bootstrap_tabs_designer.refleshTabs(component);*/
        $(component).tabs({
            minHeight: val
        });

    },
    

    setMinWidth: function(component, val, unit) {

        var prop = "minWidth";

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        bootstrap_tabs_designer.setSize(component, "min-width", val, unit);

    },

    setMinHeight: function(component, val, unit) {

        var prop = "minHeight";

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        bootstrap_tabs_designer.setSize(component, "min-height", val, unit);

    },

    setMaxWidth: function(component, val, unit) {

        var prop = "maxWidth";

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        bootstrap_tabs_designer.setSize(component, "max-width", val, unit);

    },

    setMaxHeight: function(component, val, unit) {

        var prop = "maxHeight";

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        bootstrap_tabs_designer.setSize(component, "max-height", val, unit);

    },
    setSize: function(component, type, val, unit) {
        var portlet = bootstrap_tabs_designer.getMainObject(component);
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
        var portlet = bootstrap_tabs_designer.getMainObject(component);
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
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        $(component).attr('data-border', val);

        bootstrap_tabs_designer.refleshTabs(component);
        $(component).tabs({
            border: eval(val)
        });
    },
    setClosEnable: function(component, val) {
        var prop = "closeable";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        if(val == 'true'){
            component.attr("closeable","closeable");    
        }else{
            component.removeAttr("closeable"); 
        }
        $(component).tabs({
        	closeable : val
        });
    },
    /**
    * 设置选卡反向
    */
    setReversed: function(component, val) {
        var prop = "reversed";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        $(component).attr('data-reversed', val);

        bootstrap_tabs_designer.refleshTabs(component);
        $(component).tabs({
            reversed: eval(val)
        });
    },
    /**
    * 设置选卡位置
    */
    setTabPosition: function(component, val) {
        var prop = "tabPosition";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        $(component).attr('data-tabPosition', val);

        bootstrap_tabs_designer.refleshTabs(component);
        $(component).tabs({
            tabPosition: val
        });
    },
    /**
    * 设置选卡样式
    */
    setTabStyle: function(component, val) {
        var prop = "tabStyle";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        
        bootstrap_tabs_designer.refleshTabs(component);
        if(val === 'customborder'){
            val = 'custom';
            component.addClass('tabbable-customborder');
        }else{
            component.removeClass('tabbable-customborder');
        }
        $(component).attr('data-tabStyle', val);
        $(component).tabs({
            tabStyle: val
        });
    },

    updateTab: function(component, val) {

        var targetId = component.attr('id');
        $.each($(component).find('ul.nav.nav-tabs>li.active>a'), function(index) {
            var _targetId = $(this).closest('[data-role=bootstrap_tabs]').attr('id');
            if (_targetId === targetId) {
                $(this).html(val);

                var $dropdown = $(this).find('i.fa.fa-angle-down');
                if ($dropdown.length > 0) {
                    $(this).html(val + $dropdown.prop('outerHTML'));
                }
            }
        });

    },

    deleteTab: function(component, val) {
        $(component).find('ul.nav.nav-tabs>li.active').eq(0).remove();
        $(component).find('div.tab-pane.active').eq(0).remove();
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
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        var datas = component.tabs('tabs'),
            tabsLength = datas.length,
            diff = val - tabsLength;
        if (diff > 0) {
            for (var i = 0; i < diff; i++) {
                component.tabs("add", {
                    title: 'New Tab ' + (tabsLength + i + 1),
                    content: "",
                    minHeight: 200
                });
            }
        } else if (diff < 0) {
            for (var i = 0; i < -diff; i++) {
                component.tabs("close", tabsLength - i - 1);
            }
        }

        /*var datas = component.tabs('tabs');
        datas = component.tabs('setContents', datas);
        component.tabs('destroy');
        var tabs = [];
        for (var i = 0; i < val; i++) {
            var index = i;
            var num = index+1;
            var title = 'New Tab '+num;
            var content = '';
            var height = 200;
            if(datas[index]){
                title = datas[index].title;
                content = datas[index].content;
                height = datas[index].height;
            }
            tabs.push({
                title: title,
                content: content,
                height: height
            });
        }
        bootstrap_tabs_designer.updateStaticDatas(component, tabs);
        component.tabs('load', tabs);*/

    },

    updateStaticDatas: function(component, datas) {
        component.attr('static-datas', JSON.stringify(datas));
    },

    setDataRule: function(component, prop, val,unit) {
        var lastVal = bootstrap_tabs_designer.getDataRule(component, prop)
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
    setFontColor: function(component, val) {
        var prop = "fontColor";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        component.find("ul.nav-tabs>li>a").css("color", val);
    },
    setFontStyle: function(component, val) {
        var prop = "fontStyle";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        var tg = component.find('ul.nav-tabs>li>a');
        var tgcss = $(tg).attr('style') || '';
        if (val == 'italic') {
            if (tgcss.indexOf('font-style: italic') == -1) {
                tg.css("font-style", 'italic');
            } else {
                tg.css("font-style", '');
            }
        } else if (val == 'bold') {
            if (tgcss.indexOf('font-weight: bold') == -1) {
                tg.css("font-weight", 'bold');
            } else {
                tg.css("font-weight", '');
            }
        }
    },
    setFontSize: function(component, val) {
        var prop = "fontSize";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        component.find("ul.nav-tabs>li>a").css("font-size", val + "px");
    },
    setWeightSize : function(component, val){
    	var prop = "fontWeight";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        component.find("ul.nav-tabs>li>a").css("font-weight", val);
    },
    /**
     *设置内间距
     */
    setPaddingTop: function(component, val) {
        var prop = "paddingTop";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setPadding(component, "top", val);
    },
    setPaddingBottom: function(component, val) {
        var prop = "paddingBottom";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setPadding(component, "bottom", val);
    },
    setPaddingLeft: function(component, val) {
        var prop = "paddingLeft";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setPadding(component, "left", val);
    },
    setPaddingRight: function(component, val) {
        var prop = "paddingRight";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setPadding(component, "right", val);
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
     *设置内间距
     */
    setContentPaddingTop: function(component, val) {
        var prop = "contentpaddingTop";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setContentPadding(component, "top", val);
    },
    setContentPaddingBottom: function(component, val) {
        var prop = "contentpaddingBottom";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setContentPadding(component, "bottom", val);
    },
    setContentPaddingLeft: function(component, val) {
        var prop = "contentpaddingLeft";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setContentPadding(component, "left", val);
    },
    setContentPaddingRight: function(component, val) {
        var prop = "contentpaddingRight";
        bootstrap_tabs_designer.setDataRule(component, prop, val);
        bootstrap_tabs_designer.setContentPadding(component, "right", val);
    },
    setContentPadding: function(component, direct, val) {
        var portletBody = component.find(".tab-content");
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
        /*if ($.trim(val) == '') {
            return;
        }
        val = val * 1;
        if (isNaN(val) || val < 0) {
            return;
        }*/

        var prop = "outWidth";

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        //bootstrap_tabs_designer.setSize(component,"width",val,unit);
        val = bootstrap_tabs_designer.getSize(component, prop, val, unit);

        $(component).css("width",val);
        // $(component).attr("data-outWidth",val);
        //$(component).width(val);

        //bootstrap_tabs_designer.refleshTabs(component);
        /*$(component).tabs({
            width: val
        });*/

    },

    setOutHeight: function(component, val, unit) {
        /*if ($.trim(val) == '') {
            return;
        }
        val = val * 1;
        if (isNaN(val) || val < 0) {
            return;
        }*/

        var prop = "outHeight";

        bootstrap_tabs_designer.setDataRule(component, prop, val, unit);

        //bootstrap_tabs_designer.setSize(component,"height",val,unit);
        val = bootstrap_tabs_designer.getSize(component, prop, val, unit);

        $(component).attr("data-outHeight",val);
        //$(component).height(val);
        
        //bootstrap_tabs_designer.refleshTabs(component);
        /*$(component).tabs({
            height: val
        });*/

    },

    setTitleWidth : function(component, val, unit){
        var prop = "titleWidth";
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        if (val == '') {
        } else {
            val = val + "px";
        }
        bootstrap_tabs_designer.updateStyleByKey(component,'titleWidth',val);
    },

    /**
     * 获取styler并且赋值
     */
    updateStyleByKey: function (component,key,val){
        var styler = bootstrap_tabs_designer.getStyler(component);
        styler[key] = val;
        bootstrap_tabs_designer.updateStyler(component,styler);
        bootstrap_tabs_designer.updateStylerBox(component,styler);
    },
    /**
     * 获取styler数组
     */
    getStyler: function (component){
        var sid = bootstrap_tabs_designer.getStylerId(component);
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
        var sid = bootstrap_tabs_designer.getStylerId(component);
        $('#'+sid).val(JSON.stringify(styler));
    },
    /**
     * 设置样式
     */
    updateStylerBox: function(component,styler){
        var id = component.attr('id');
        var _id = '#'+id;
        var _default = "";

        var _default_div = _id + '{';
        if(styler['zindex']){
            _default_div += 'z-Index:'+styler['zindex']+";";
        }
         _default_div += "}";

        var _default_left_content = _id + '[data-tabposition="left"]>.row>.tabnav,'+_id + '[data-tabposition="right"]>.row>.tabnav{';
        if(styler['titleWidth']){
            _default_left_content += 'width:'+styler['titleWidth']+";";
        }
        _default_left_content += "}"

        var _default_ul = _id+" >.nav-tabs{";
         if(styler['background_color_ul']){
            _default_ul += "background-color:"+styler['background_color_ul']+";";
        }
         if(styler['border_color_ul']){
            _default_ul += "border-color:"+styler['border_color_ul']+";";
        }

        _default_ul += "}";





       

        //hover
        var _hover = _id+" >.nav-tabs>li>a:hover,"+_id+">.row>.tabnav>.nav-tabs li>a:hover{";
        var _style = "";
        if(styler['backgroundColor_h']){
            _style += "background-color:"+styler['backgroundColor_h']+";";
        }
        if(styler['font-color_h']){
            _style += "color:"+styler['font-color_h']+";";
        }
        _style += "}";
        _hover += _style;


        

        //unactive
        var _unactive = _id+" >.nav-tabs li:not(.active)>a,"+_id+">.row>.tabnav>.nav-tabs li:not(.active)>a{";
        if(styler['background-color_unactive']){
            _unactive += "background-color:"+styler['background-color_unactive']+";";
        }
        if(styler['font-color_unactive']){
            _unactive += "color:"+styler['font-color_unactive']+";";
        }

        _unactive += "}";

        //active
        var _active = _id+" >.nav-tabs .active>a,"+_id+">.row>.tabnav>.nav-tabs .active>a{";
        if(styler['background-color_active']){
            _active += "background-color:"+styler['background-color_active']+";";
        }

        if(styler['font-color_active']){
            _active += "color:"+styler['font-color_active']+";";
        }

        _active += "}";

        
        _default += _default_left_content + _hover + _unactive + _active + _default_ul + _default_div;

        var stylebox = bootstrap_tabs_designer.getStyleBox(component);
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
    },

    /**
     * 标题栏边框颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBorderColor_ul: function(component,val){
        var prop = "border_color_ul"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'border_color_ul',val);
    },

    /**
     * 标题栏背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_ul: function(component,val){
        var prop = "background_color_ul"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'background_color_ul',val);
    },
    /**
     * 设置鼠标悬停背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_hover: function(component,val){
        var prop = "backgroundColor_h"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'backgroundColor_h',val);
    },

    /**
     * 设置鼠标悬停文本颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setFontColor_hover: function(component,val){
        var prop = "fontColor_h"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'font-color_h',val);
    },

    /**
     * 激活的选项卡背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_active: function(component,val){
        var prop = "backgroundColor_active"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'background-color_active',val);
    },

    
    /**
     * 激活的选项卡文本颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setFontColor_active: function(component,val){
        var prop = "fontColor_active"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'font-color_active',val);
    },

    /**
     * 未激活的选项卡背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_unactive: function(component,val){
        var prop = "backgroundColor_unactive"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'background-color_unactive',val);
    },

    /**
     * 未激活的选项卡文本颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setFontColor_unactive: function(component,val){
        var prop = "fontColor_unactive"; 
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        bootstrap_tabs_designer.updateStyleByKey(component,'font-color_unactive',val);
    },

    setZindex:function (component,val,unit){ 
        var prop = "zindex";
        bootstrap_tabs_designer.setDataRule(component,prop,val,unit);
        component.css("z-index",val);
        
        // bootstrap_tabs_designer.updateStyleByKey(component,'zindex',val);
    },
    setTabSelected : function(component,val,unit){
    	var prop = "tabSelected";
        bootstrap_tabs_designer.setDataRule(component,prop,val);
        var design = component.attr("design") != undefined ? eval("("+component.attr("design")+")") : {};
        design['tabSelected'] = val;
        component.attr("design",JSON.stringify(design));
    }
};