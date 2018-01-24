/**
 * bootstrap tabs
 */
var tabbar_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        if (val !== '') {
            var prop = "name";
            tabbar_designer.setDataRule(component, prop, val);
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

    /**
     *设置尺寸
     */
    setWidth: function(component, val, unit) {
        var prop = "width";
        tabbar_designer.setDataRule(component, prop, val, unit);
        component.css("width".val + unit);
        //tabbar_designer.setSize(component,"width",val,unit);
        

    },

    setHeight: function(component, val, unit) {
        

        var prop = "height";

        tabbar_designer.setDataRule(component, prop, val, unit);
        component.css("height".val + unit);

    },
    

    setMinWidth: function(component, val, unit) {

        var prop = "minWidth";

        tabbar_designer.setDataRule(component, prop, val, unit);
        component.css("min-width".val + unit);

    },

    setMinHeight: function(component, val, unit) {

        var prop = "minHeight";

        tabbar_designer.setDataRule(component, prop, val, unit);
        component.css("min-height".val + unit);

    },

    setMaxWidth: function(component, val, unit) {

        var prop = "maxWidth";

        tabbar_designer.setDataRule(component, prop, val, unit);
        component.css("max-width".val + unit);

    },

    setMaxHeight: function(component, val, unit) {

        var prop = "maxHeight";

        tabbar_designer.setDataRule(component, prop, val, unit);
        component.css("max-height".val + unit);

    },
   

    setBorder: function(component, val) {
        var prop = "border";
        tabbar_designer.setDataRule(component, prop, val);
        $(component).attr('data-border', val);

        tabbar_designer.refleshTabs(component);
        $(component).tabs({
            border: eval(val)
        });
    },
    setClosEnable: function(component, val) {
        var prop = "closeable";
        tabbar_designer.setDataRule(component, prop, val);
        $(component).tabs({
        	closeable : val
        });
    },
    /**
    * 设置选卡反向
    */
    setReversed: function(component, val) {
        var prop = "reversed";
        tabbar_designer.setDataRule(component, prop, val);
        $(component).attr('data-reversed', val);

        tabbar_designer.refleshTabs(component);
        $(component).tabs({
            reversed: eval(val)
        });
    },
    /**
    * 设置选卡位置
    */
    setTabPosition: function(component, val) {
        var prop = "tabPosition";
        tabbar_designer.setDataRule(component, prop, val);
        
        if(val == "top"){
        	if(component.find(".weui-tabbar__item")[0] != undefined){
        		component.find(".weui-tabbar").addClass("weui-navbar");
        		component.find(".weui-tabbar").removeClass("weui-tabbar");
        		component.find(".weui-tabbar__item").addClass("weui-navbar__item");
        		component.find(".weui-tabbar__item").removeClass("weui-tabbar__item");   	
        		component.find(".weui-tab__bd-item").css("margin-top","25px;");
        	} 	
        }
        if(val == "bottom"){
        	if(component.find(".weui-navbar")[0] != undefined){
        		component.find(".weui-navbar").addClass("weui-tabbar");
        		component.find(".weui-navbar").removeClass("weui-navbar");
        		component.find(".weui-navbar__item").addClass("weui-tabbar__item");
        		component.find(".weui-navbar__item").removeClass("weui-navbar__item");
        		component.find(".weui-tab__bd-item").css("margin-top","3px;");
        		
        	}
        }
    },
    /**
    * 设置选卡样式
    */
    setTabStyle: function(component, val) {
        var prop = "tabStyle";
        tabbar_designer.setDataRule(component, prop, val);
        $(component).attr('data-tabStyle', val);

        tabbar_designer.refleshTabs(component);
        $(component).tabs({
            tabStyle: val
        });
    },

    updateTab: function(component, val) {
        var prop = "updateTab";
        tabbar_designer.setDataRule(component, prop, val);
        component.find(".weui-bar__item--on .weui-tabbar__label ")[0].innerHTML = val;

    },

    deleteTab: function(component, val) {
    	var prop = "deleteTab";
        tabbar_designer.setDataRule(component, prop, val);
        component.find(".weui-bar__item--on").remove();
        component.find(".weui-tab__bd-item--active").remove();
        
    },

    /**
     * 设置项
     */
    setItems: function(component, val) {
        var prop = "setItems";
        /*<a href="#tab2" class="weui-tabbar__item">
        <div class="weui-tabbar__icon">
          <img src="/glaf/scripts/jquery-weui-master/demos/images/icon_nav_msg.png" alt="">
        </div>
        <p class="weui-tabbar__label">2</p>
      </a>*/
       var length = component.find(".weui-tabbar__item").length;
       tabbar_designer.setDataRule(component, prop, val);
       for(var i = 0;i < parseInt(val) ; i++){
    	   length = length + i + 1;
    	   var _title = "<a href=\"#tab"+ length +"\" class=\"weui-tabbar__item\"><div class=\"weui-tabbar__icon\">" +
      		"<img src=\"/glaf/scripts/jquery-weui-master/demos/images/icon_nav_msg.png\" alt=\"\">" +
      		"</div><p class=\"weui-tabbar__label\">"+length+"</p></a>";
      		component.find(".weui-tabbar").append(_title);
      		var _content = "<div id=\"tab"+length+"\" class=\"weui-tab__bd-item containerComp" +
      				" ui-sortable\" style=\"min-height: 100px;\"></div>";
      		component.find(".weui-tab__bd").append(_content);
      		
       }


    },

    updateStaticDatas: function(component, datas) {
        component.attr('static-datas', JSON.stringify(datas));
    },

    setDataRule: function(component, prop, val,unit) {
        var lastVal = tabbar_designer.getDataRule(component, prop)
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
        var prop = "FontColor";
        tabbar_designer.setDataRule(component, prop, val);
        component.find(".weui-tabbar__item .weui-tabbar__label").css("color",val);
        component.attr("data-color",val);
    },
    setFontStyle: function(component, val) {
        var prop = "FontStyle";
        tabbar_designer.setDataRule(component, prop, val);
        var tg = component.find(".weui-tabbar__item .weui-tabbar__label");
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
        var prop = "FontSize";
        tabbar_designer.setDataRule(component, prop, val);
        component.find(".weui-tabbar__item .weui-tabbar__label").css("font-size", val + "px");
    },
    setWeightSize : function(component, val){
    	var prop = "WeightSize";
        tabbar_designer.setDataRule(component, prop, val);
        component.find(".weui-tabbar__item .weui-tabbar__label").css("font-weight", val);
    },
    /**
     *设置内间距
     */
    setPaddingTop: function(component, val) {
        var prop = "paddingTop";
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setPadding(component, "top", val);
    },
    setPaddingBottom: function(component, val) {
        var prop = "paddingBottom";
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setPadding(component, "bottom", val);
    },
    setPaddingLeft: function(component, val) {
        var prop = "paddingLeft";
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setPadding(component, "left", val);
    },
    setPaddingRight: function(component, val) {
        var prop = "paddingRight";
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setPadding(component, "right", val);
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
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setContentPadding(component, "top", val);
    },
    setContentPaddingBottom: function(component, val) {
        var prop = "contentpaddingBottom";
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setContentPadding(component, "bottom", val);
    },
    setContentPaddingLeft: function(component, val) {
        var prop = "contentpaddingLeft";
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setContentPadding(component, "left", val);
    },
    setContentPaddingRight: function(component, val) {
        var prop = "contentpaddingRight";
        tabbar_designer.setDataRule(component, prop, val);
        tabbar_designer.setContentPadding(component, "right", val);
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

        tabbar_designer.setDataRule(component, prop, val, unit);

        //tabbar_designer.setSize(component,"width",val,unit);
        val = tabbar_designer.getSize(component, prop, val, unit);

        $(component).css("width",val);
        // $(component).attr("data-outWidth",val);
        //$(component).width(val);

        //tabbar_designer.refleshTabs(component);
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

        tabbar_designer.setDataRule(component, prop, val, unit);

        //tabbar_designer.setSize(component,"height",val,unit);
        val = tabbar_designer.getSize(component, prop, val, unit);

        $(component).attr("data-outHeight",val);
        //$(component).height(val);
        
        //tabbar_designer.refleshTabs(component);
        /*$(component).tabs({
            height: val
        });*/

    },

    setTitleWidth : function(component, val, unit){
        var prop = "TitleWidth ";
        tabbar_designer.setDataRule(component,prop,val);
        if (val == '') {
        } else {
            val = val + "px";
        }
        tabbar_designer.updateStyleByKey(component,'titleWidth',val);
    },

    /**
     * 获取styler并且赋值
     */
    updateStyleByKey: function (component,key,val){
        var styler = tabbar_designer.getStyler(component);
        styler[key] = val;
        tabbar_designer.updateStyler(component,styler);
        tabbar_designer.updateStylerBox(component,styler);
    },
    /**
     * 获取styler数组
     */
    getStyler: function (component){
        var sid = tabbar_designer.getStylerId(component);
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
        var sid = tabbar_designer.getStylerId(component);
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

        var stylebox = tabbar_designer.getStyleBox(component);
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
    setBackGroundColor : function(component,val){
        var prop = "backgroundcolor";
    	tabbar_designer.setDataRule(component,prop,val);
    	component.find(".weui-tabbar").css("background-color",val); 
    },
    /**
     * 标题栏边框颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBorderColor_ul: function(component,val){
        var prop = "border_color_ul"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'border_color_ul',val);
    },

    /**
     * 标题栏背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_ul: function(component,val){
        var prop = "background_color_ul"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'background_color_ul',val);
    },
    /**
     * 设置鼠标悬停背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_hover: function(component,val){
        var prop = "backgroundColor_h"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'backgroundColor_h',val);
    },

    /**
     * 设置鼠标悬停文本颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setFontColor_hover: function(component,val){
        var prop = "fontColor_h"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'font-color_h',val);
    },

    /**
     * 激活的选项卡背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_active: function(component,val){
        var prop = "backgroundColor_active"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'background-color_active',val);
    },

    
    /**
     * 激活的选项卡文本颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setFontColor_active: function(component,val){
        var prop = "fontColor_active"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'font-color_active',val);
    },

    /**
     * 未激活的选项卡背景颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setBackgroundColor_unactive: function(component,val){
        var prop = "backgroundColor_unactive"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'background-color_unactive',val);
    },

    /**
     * 未激活的选项卡文本颜色
     * @param {[type]} component [description]
     * @param {[type]} val       [description]
     */
    setFontColor_unactive: function(component,val){
        var prop = "fontColor_unactive"; 
        tabbar_designer.setDataRule(component,prop,val);
        tabbar_designer.updateStyleByKey(component,'font-color_unactive',val);
    },

    setZindex:function (component,val,unit){ 
        var prop = "Zindex";
        tabbar_designer.setDataRule(component,prop,val,unit);
        component.css("z-index",val);
        
        // tabbar_designer.updateStyleByKey(component,'zindex',val);
    },
    setTabSelected : function(component,val,unit){
    	var prop = "TabSelected";
        tabbar_designer.setDataRule(component,prop,val);
    	$(component).tabs({
            tabSelected : val
        });
    },
    setIconClass : function(component,val){
    	var prop = "IconClass";
    	tabbar_designer.setDataRule(component,prop,val);
    	if(component.find(".weui-bar__item--on .weui-tabbar__icon")[0] != undefined){
    		component.find(".weui-bar__item--on .weui-tabbar__icon")[0].innerHTML = "<i class=\""+val+"\" style=\"margin-top:5px\"></i>";
    	}
    	else{
    		component.find(".weui-bar__item--on").prepend("<div class=\"weui-tabbar__icon\" ><i class=\""+val+"\" style=\"margin-top:5px\"></i></div>");
    		component.find(".weui-bar__item--on .weui-tabbar__label ").css("padding-top","0px");
    	}
    },
    setIconColor : function(component,val){
    	var prop = "IconColor";
    	tabbar_designer.setDataRule(component,prop,val);
    	if(component.find(".weui-tabbar__item .weui-tabbar__icon").length>0){
			component.find(".weui-tabbar__item .weui-tabbar__icon i").css("color",val);	
			
		}
    },
    deleteIcon : function(component,val){
    	var prop = "deleteIcon";
    	tabbar_designer.setDataRule(component,prop,val);
    	if(component.find(".weui-bar__item--on .weui-tabbar__icon")[0] != undefined){
    		component.find(".weui-bar__item--on .weui-tabbar__icon").remove();
    		component.find(".weui-bar__item--on .weui-tabbar__label ").css("padding-top","10px");
    	}
    },
    setSelectColor : function(component,val){
    	var prop = "SelectColor";
    	tabbar_designer.setDataRule(component,prop,val);
    	component.attr("select-color",val);
    	component.find(".weui-bar__item--on .weui-tabbar__label").css("color",val);
    	component.find(".weui-bar__item--on .weui-tabbar__icon i").css("color",val);
    	
    },
    setIconSize:function(component,val){
		var prop = "IconSize"; 
		tabbar_designer.setDataRule(component,prop,val);
		if(component.find(".weui-tabbar__item .weui-tabbar__icon i").length>0){
			if (val == '') {
				component.find(".weui-tabbar__item .weui-tabbar__icon i").css("font-size", '');
                component.find(".weui-tabbar__item .weui-tabbar__icon").css("height", '');
			} else {
				component.find(".weui-tabbar__item .weui-tabbar__icon i").css("font-size", val + "px");
                component.find(".weui-tabbar__item .weui-tabbar__icon").css("height", (parseInt(val)+5)+'px');
			}
		}
		
	},
	setTabbarHeight : function(component,val,unit){
		var prop = "TabbarHeight"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__item").css("height",val + unit);
	},
	setTabbarMarginTop : function(component,val,unit){
		var prop = "TabbarMarginTop"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__item").css("margin-top",val + unit);
	},
	setTabbarMarginBottom : function(component,val,unit){
		var prop = "TabbarMarginBottom"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__item").css("margin-bottom",val + unit);
	},
	setTabbarMarginLeft : function(component,val,unit){
		var prop = "TabbarMarginLeft"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__item").css("margin-left",val + unit);
	},
	setTabbarMarginRight : function(component,val,unit){
		var prop = "TabbarMarginRight"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__item").css("margin-right",val + unit);
	},
	setTabbarIconMarginTop : function(component,val,unit){
		var prop = "TabbarIconMarginTop"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__icon").css("margin-top",val + unit);
	},
	setTabbarIconMarginBottom : function(component,val,unit){
		var prop = "TabbarIconMarginBottom"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__icon").css("margin-bottom",val + unit);
	},
	setTabbarIconMarginLeft : function(component,val,unit){
		var prop = "TabbarIconMarginLeft"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__icon").css("margin-left",val + unit);
	},
	setTabbarIconMarginRight : function(component,val,unit){
		var prop = "TabbarIconMarginRight"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__icon").css("margin-right",val + unit);
	},
	setTabbarlableHeight : function(component,val,unit){
		var prop = "TabbarlableHeight"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__label").css("height",val + unit);
	},
	setTabbarIconHeight : function(component,val,unit){
		var prop = "TabbarIconHeight"; 
		tabbar_designer.setDataRule(component,prop,val);
		component.find(".weui-tabbar__icon").css("height",val + unit);
	}
};