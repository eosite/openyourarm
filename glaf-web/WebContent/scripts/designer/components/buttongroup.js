var buttongroup_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			buttongroup_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}
	},
	getButtons: function(component){
		var btns = [];
		$.each(component.children('button'), function(index, btn){
			var boxId = buttongroup_designer.getStyleBoxId(component);
			var box = component.find('#'+boxId);
			if(box[0]){
				box.remove();
			}
			btns.push({text: $(btn).find(">span").text()});
		});
		return btns;
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
        buttongroup_designer.setDataRule(component,prop,val);

        var stylerId = buttongroup_designer.getStylerId(component);
		var boxId = buttongroup_designer.getStyleBoxId(component);
		var styler = component.find('#'+stylerId);
		var box = component.find('#'+boxId);
        var datas = buttongroup_designer.getButtons(component);
        var temp = component.find('button:last').prop('outerHTML');

        var $buttons = component.find("button");
        var addNum = 0;	//操作的数量
        var addFlag = true; //是否为新增操作
        var buttonsLength = $buttons.length;
        if($buttons.length > val){
        	//删除操作
        	addNum = $buttons.length - val;
        	addFlag = false;
        }else if($buttons.length < val){
        	//新增操作
        	addNum = val - $buttons.length;
        	addFlag = true;
        }
        for(var i = 0 ; i < addNum ; i++){
        	if(addFlag){
        		//新增
        		var dtStr = new Date().getTime();
        		var $temp = $(temp);
        		$temp.attr("id","mtbutton_"+dtStr);
	            $temp.find("span").text('按钮');
	            component.append($temp);
        	}else{
        		//删除
        		$buttons[buttonsLength-i-1].remove();
        	}
        }
        // component.empty();
        // for (var i = 0; i < val; i++) {
        //     var index = i;
        //     var num = index+1;
        //     var text = '按钮 '+num;
        //     if(datas[index]){
        //         text = datas[index].text;
        //     }
        //     var $temp = $(temp);
        //     $temp.attr("id",$temp.attr("id")+i);
        //     $temp.find("span").text(text);
        //     component.append($temp);
        // }

        buttongroup_designer.restoreStyler(styler,box,component);

    },
	add: function (component, val){
		if((val = $.trim(val))!==''){
			var id = component.attr('id');
			var temp = component.find('button:last');
			var $newbtn = $(temp.prop('outerHTML'));
			$newbtn.html(val);
			$newbtn.insertAfter('#'+id+' button:last');
		}
	},
	modify: function (component, val){
		var prop = "content"; 
        buttongroup_designer.setDataRule(component,prop,val);
		
		if((val = $.trim(val))===''){
			return;
		}
		
		var stylerId = buttongroup_proto.getStylerId(component);
		
		var boxId = buttongroup_proto.getStyleBoxId(component);
		var styler = component.find('#'+stylerId);
		var box = component.find('#'+boxId);

		var btns = val.split(';');
		$.each(btns, function(i, v) {
			var btnval = $.trim(btns[i]);
			if(btnval===''){
				return;
			}
			var btn = component.find('button:nth-child('+(i+1)+')');
			var prevBtn = component.find('button:nth-child('+i+')');
			if(btn.length>0){
				btn.html(btnval);
			}else{
				var newBtn = $(prevBtn.prop('outerHTML'));
				newBtn.html(btnval);
				component.append(newBtn);
			}
		});

		buttongroup_proto.restoreStyler(styler,box,component);
	},
	delete: function (component, val){
		if((val = $.trim(val))!==''){
			$.each(component.find('button'), function(i, v) {
				if($(this).html()===val){
					$(this).remove();
				}
			});
		}
	},
	restoreStyler: function(styler,box,component){
		if(styler.length>0 && box.length>0){
			component.find('button:first').append(styler.prop('outerHTML'));
			component.find('button:first').append(box.prop('outerHTML'));
		}
	},
	getStyler: function (component){
		var sid = buttongroup_proto.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.find('button:first').append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return $.parseJSON(styler.val());
		}
	},
	getStyleBox: function (component){
		var sid = buttongroup_proto.getStyleBoxId(component);
		var box = component.find('#'+sid);
		if(!box.length){
			component.find('button:first').append("<style id=\""+sid+"\"></style>");
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
		var sid = buttongroup_proto.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		//default
		var _default = _id+" button{";
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
		var _hover = _id+" button:hover{";
		if(styler['bgcolor_h']){
			_hover += "background-color:"+styler['bgcolor_h']+";";
		}
		if(styler['bocolor_h']){
			_hover += "border-color:"+styler['bocolor_h']+";";
		}
		if(styler['focolor_h']){
			_hover += "color:"+styler['focolor_h']+";";
		}
		_hover += "}";

		var stylebox = buttongroup_proto.getStyleBox(component);
		stylebox.html(_default+_hover);
	},
	updateStyleByKey: function (component,key,val){
		var styler = buttongroup_proto.getStyler(component);
		styler[key] = val;
		buttongroup_proto.updateStyler(component,styler);
		buttongroup_proto.updateStyleBox(component,styler);
	},
	/**
	*设置背景色
	*/
	setBackgroundColor: function (component,val){
		if(val!=''){
			var prop = "backgroundColor"; 
			buttongroup_designer.setDataRule(component,prop,val);
			buttongroup_proto.updateStyleByKey(component,'bgcolor',val);
		}
	},
	/**
	*设置边框颜色
	*/
	setBorderColor: function (component,val){
		if(val!=''){
			var prop = "borderColor"; 
			buttongroup_designer.setDataRule(component,prop,val);
			buttongroup_proto.updateStyleByKey(component,'bocolor',val);
		}
	},
	/**
	*设置边框样式
	*/
	setBorderStyle: function (component,val){
		if(val!=''){
			var prop = "borderStyle"; 
			buttongroup_designer.setDataRule(component,prop,val);
			component.children('button').css("border-style",val);
		}
	},
	/**
	*设置字体
	*/
	setFontFamily: function (component,val){
		if(val!=''){
			var tg = component.find('span');
			tg.css("font-family",val);
		}
	},
	setFontColor: function (component,val){
		if(val!=''){
			var prop = "color"; 
			buttongroup_designer.setDataRule(component,prop,val);
			buttongroup_proto.updateStyleByKey(component,'focolor',val);
		}
	},
	setFontStyle: function (component,val){
		var prop = "fontStyle"; 
		buttongroup_designer.setDataRule(component,prop,val);
		var tg = component.find('span');
		var tgcss = $(tg).attr('style') || '';
		if(val=='italic'){
			if(tgcss.indexOf('font-style: italic')==-1){
				tg.css("font-style",'italic');
			}else{
				tg.css("font-style",'');
			}
		}else if(val=='bold'){
			if(tgcss.indexOf('font-weight: bold')==-1){
				tg.css("font-weight",'bold');
			}else{
				tg.css("font-weight",'');
			}
		}
	},
	/**
	*设置字体大小
	*/
	setFontSize: function (component,val){
		if(val!=''){
			var prop = "fontSize"; 
			buttongroup_designer.setDataRule(component,prop,val);
			var tg = component.find('span');
			tg.css("font-size",val+"px");
		}
	},
	/**
	*鼠标悬停效果
	*/
	setBackgroundColor_hover: function (component,val){
		if(val!=''){
			var prop = "backgroundColor_h"; 
			buttongroup_designer.setDataRule(component,prop,val);
			buttongroup_proto.updateStyleByKey(component,'bgcolor_h',val);
		}
	},
	setBorderColor_hover: function (component,val){
		if(val!=''){
			var prop = "borderColor_h"; 
			buttongroup_designer.setDataRule(component,prop,val);
			buttongroup_proto.updateStyleByKey(component,'bocolor_h',val);
		}
	},
	setFontColor_hover: function (component,val){
		if(val!=''){
			var prop = "fontColor_h"; 
			buttongroup_designer.setDataRule(component,prop,val);
			buttongroup_proto.updateStyleByKey(component,'focolor_h',val);
		}
	},
	/**
	 * 圆边
	 */
	setCircleStyle: function (component,val){
		var prop = "circleStyle"; 
		buttongroup_designer.setDataRule(component,prop,val);
		if(val=='display'){
			if(component.hasClass('btn-group-vertical')){
				component.addClass('btn-group-vertical-circle');
			}else{
				component.addClass('btn-group-circle');
			}
		}else{
			if(component.hasClass('btn-group-vertical')){
				component.removeClass('btn-group-vertical-circle');
			}else{
				component.removeClass('btn-group-circle');
			}
		}
	},
	/**
	 * 垂直
	 */
	setVerticalStyle: function (component,val){
		var prop = "verticalStyle"; 
		buttongroup_designer.setDataRule(component,prop,val);
		if(val=='display'){
			component.addClass('btn-group-vertical');
			component.removeClass('btn-group');
			if(component.hasClass('btn-group-circle')){
				component.removeClass('btn-group-circle');
				component.addClass('btn-group-vertical-circle');
			}
		}else{
			component.addClass('btn-group');
			component.removeClass('btn-group-vertical');
			if(component.hasClass('btn-group-vertical-circle')){
				component.removeClass('btn-group-vertical-circle');
				component.addClass('btn-group-circle');
			}
		}
	},
	resize: function(component, val){
		var prop = "resize"; 
		var lastVal = buttongroup_designer.getDataRule(component,prop);

		if(lastVal!=='default'){
			component.removeClass('btn-group-'+lastVal);
		}

		if(val!=='default'){
			component.addClass('btn-group-'+val);
		}

		buttongroup_designer.setDataRule(component,prop,val);
	},
	setDataRule : function(component,prop,val){
        var lastVal = buttongroup_designer.getDataRule(component,prop)
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
    }
};
var buttongroup_proto = Object.create(buttongroup_designer);