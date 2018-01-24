var portlet_tabs_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}
	},
	clear: function(component){
		component.find('.portlet-title .nav-tabs').empty();
        component.find('.portlet-body .tab-content').empty();
	},
	getTabs: function(component){
		var datas = [];
		$.each(component.find('.portlet-title .nav-tabs').children(), function(index, li){
			var $li = $(li).children()[0];
			var $a = $($li);
			datas.push({
				title: $a.text(),
				content: component.find($a.attr('href')).html()
			});
		});
		return datas;
	},
	/**
     * 设置项
     */
    setItems: function (component,val){
        val = val*1;
        if(isNaN(val) || val===0){
        	return;
        }

        var prop = "items"; 
        portlet_tabs_designer.setDataRule(component,prop,val);

        var datas = portlet_tabs_designer.getTabs(component);
        portlet_tabs_designer.clear(component);

        for (var i = 0; i < val; i++) {
            var index = i;
            var num = index+1;
            var title = 'Tab '+num;
            var content = 'Tab '+num +' Content!';
            var active = '';
            if(datas[index]){
                title = datas[index].title;
                content = datas[index].content;
            }
            if(index==val-1){
            	active = 'active';
            }
            portlet_tabs_designer.addTab(component, title, content, active);
        }

    },
	/**
	*添加选项卡
	*/
	addTab: function (component,title,content, active){
		var index = portlet_tabs_designer.getTabIndex(component);
		component.find('.portlet-title .nav-tabs')
			.append('<li class="'+active+'"><a href="#portlet_tab_'+index+'" data-toggle="tab">'+title+'</a></li>');
		component.find('.portlet-body .tab-content')
			.append('<div class="containerComp tab-pane '+active+'" id="portlet_tab_'+index+'">'+content+'</div>');
	},
	/**
	*添加选项卡
	*/
	deleteTab: function (component,val){
		if(val!=''){
			component.find('.portlet-body>.tab-content>.tab-pane.active').remove();
			component.find('.portlet-title>ul.nav-tabs>li.active').remove();
		}
	},
	/**
	*获取新索引
	*/
	getTabIndex: function (component){
		var tabs = component.find('.portlet-body .tab-content .tab-pane');
		var id = $(tabs.last()).attr('id');
		var maxIndex = 0;
		if(id!==undefined){
			maxIndex = id.substring(id.lastIndexOf('_')+1)*1;
		}
		return maxIndex+1;
	},
	/**
	*获取当前最大索引
	*/
	getTabMaxIndex: function (component){
		return portlet_tabs_proto.getTabIndex(component)-1;
	},
	/**
	*设置色调
	*/
	setColor: function (component,val){
		//if(val!=''){
			var prop = "color"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			component.css({'border': '1px solid '+val, 'border-top': 0});
			component.find('.portlet-title').css({'background-color': val});
		//}
	},
	/**
	*设置标题内容颜色
	*/
	setTitleColor: function (component,val){
		//if(val!=''){
			var prop = "titleColor"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			component.find('.portlet-title .caption').css({'color': val});
			component.find('.portlet-title .caption>i').css({'color': val});
			component.find('.portlet-title .tools').css({'color': val});
			component.find('.portlet-title .actions>a').css({'color': val, 'border-color': val});
			component.find('.portlet-title .actions>a>i').css({'color': val});
		//}
	},
	/**
	*设置轨道可见
	*/
	setRailVisible: function (component,val){
		var prop = "railVisible"; 
		portlet_tabs_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.find('.scroller').attr('data-rail-visible', 0);
			component.find('.slimScrollDiv .slimScrollRail').css('display', 'none');
		}else{
			component.find('.scroller').attr('data-rail-visible', 1);
			component.find('.slimScrollDiv .slimScrollRail').css('display', 'block');
		}
	},
	/**
	*设置轨道颜色
	*/
	setRailColor: function (component,val){
		//if(val!=''){
			var prop = "railColor"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			component.find('.scroller').attr('data-rail-color', val);
			component.find('.slimScrollDiv .slimScrollRail').css('background-color', val);
		//}
	},
	/**
	*设置滚条颜色
	*/
	setBarColor: function (component,val){
		//if(val!=''){
			var prop = "handleColor"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			component.find('.scroller').attr('data-handle-color', val);
			component.find('.slimScrollDiv .slimScrollBar').css('background-color', val);
		//}
	},
	/**
	*设置风格
	*/
	setStyle: function (component,val){
		var prop = "main"; 
		portlet_tabs_designer.setDataRule(component,prop,val);
		if(val=='box'){
			component.addClass('box');
			component.addClass('red');
			component.removeClass('light');
		}else if(val=='simple'){
			component.addClass('light');
			component.removeClass('box');
			component.removeClass('red');
		}
	},
	/**
	*设置背景反转
	*/
	setBgInverse: function (component,val){
		var prop = "bgInverse"; 
		portlet_tabs_designer.setDataRule(component,prop,val);
		if(val=='hide'){
			component.removeClass('bg-inverse');
		}else{
			component.addClass('bg-inverse');
		}
	},
	getStyler: function (component){
		var sid = portlet_tabs_proto.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return $.parseJSON(styler.val());
		}
	},
	getStyleBox: function (component){
		var sid = portlet_tabs_proto.getStyleBoxId(component);
		var box = component.find('#'+sid);
		if(!box.length){
			component.append("<style id=\""+sid+"\"></style>");
		}
		return component.find('#'+sid);
	},
	getStylerId: function (component){
		var id = component.attr('id');
		return id+'_styler_hidden';
	},
	getStyleBoxId: function (component){
		var id = component.attr('id');
		return id+'_styler_init';
	},
	updateStyler: function (component,styler){
		var sid = portlet_tabs_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		//default
		var _default = _id+" .portlet-title .nav-tabs>li>a{";
		if(styler['bgcolor']){
			_default += "background-color:"+styler['bgcolor']+";";
		}
		if(styler['bocolor']){
			_default += "border-color:"+styler['bocolor']+";";
		}
		if(styler['focolor']){
			_default += "color:"+styler['focolor']+";";
		}
		_default += "}";
		//hover
		var _hover = _id+" .portlet-title .nav-tabs>li>a:hover{";
		var _active = _id+" .portlet-title .nav-tabs .active>a{";
		var _style = "";
		if(styler['bgcolor_h']){
			_style += "background-color:"+styler['bgcolor_h']+";";
		}
		if(styler['bocolor_h']){
			_style += "border-color:"+styler['bocolor_h']+";";
		}
		if(styler['focolor_h']){
			_style += "color:"+styler['focolor_h']+";";
		}
		_style += "}";
		_hover += _style;
		_active += _style;

		var stylebox = portlet_tabs_proto.getStyleBox(component);
		stylebox.html(_active+_default+_hover);
	},
	updateStyleByKey: function (component,key,val){
		var styler = portlet_tabs_proto.getStyler(component);
		styler[key] = val;
		portlet_tabs_proto.updateStyler(component,styler);
		portlet_tabs_proto.updateStyleBox(component,styler);
	},
	/**
	*设置背景色
	*/
	setBackgroundColor: function (component,val){
		//if(val!=''){
			var prop = "backgroundColor"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			portlet_tabs_proto.updateStyleByKey(component,'bgcolor',val);
		//}
	},
	/**
	*设置文本颜色
	*/
	setFontColor: function (component,val){
		//if(val!=''){
			var prop = "fontColor"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			portlet_tabs_proto.updateStyleByKey(component,'focolor',val);
		//}
	},
	/**
	*鼠标悬停效果
	*/
	setBackgroundColor_hover: function (component,val){
		if(val!=''){
			var prop = "backgroundColor_h"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			portlet_tabs_proto.updateStyleByKey(component,'bgcolor_h',val);
		}
	},
	setFontColor_hover: function (component,val){
		//if(val!=''){
			var prop = "fontColor_h"; 
			portlet_tabs_designer.setDataRule(component,prop,val);
			portlet_tabs_proto.updateStyleByKey(component,'focolor_h',val);
		//}
	},
	setDataRule : function(component,prop,val){
        var lastVal = portlet_tabs_designer.getDataRule(component,prop)
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
        var value ="";
        if(component.attr("data-rule")){
            value = JSON.parse(component.attr("data-rule"))[prop];
        }
        return value;
    },
    /**
     *设置尺寸
     */
    setWidth: function(component, val, unit) {
        var prop = "width";

		portlet_tabs_designer.setDataRule(component,prop,val,unit);

		portlet_tabs_designer.setSize(component,"width",val,unit);
		
        /*portlet_tabs_designer.refleshTabs(component);
        $(component).tabs({
            width: val
        });*/

    },
	
    setHeight: function(component, val, unit) {
        var prop = "height";

		portlet_tabs_designer.setDataRule(component,prop,val,unit); 

		portlet_tabs_designer.setSize(component,"height",val,unit);
//        $(component).tabs({
//            height: val
//        });

    },
    setSize:function(component,type,val,unit){
//		var scroller = component.find('.portlet-body .scroller').height(val),
			slimScrollDiv = component.find('.portlet-body .tab-pane');
		if(val==''){
//		  scroller.css(type,'');	
		  slimScrollDiv.css(type,'');
		  component.css(type,'');
		}else{
			if(unit==undefined){
				unit="px";
			}else{
				if(type=="width"){
					component.css(type,val+unit);
				}else{
//					scroller.css(type,val+unit);
					slimScrollDiv.css(type,val+unit);
				}
			}
		}
	}
};
var portlet_tabs_proto = Object.create(portlet_tabs_designer);