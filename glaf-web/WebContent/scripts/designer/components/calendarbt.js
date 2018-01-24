/**
 * calendarbt
 */
// $('body').append('<script
// src="'+contextPath+'/scripts/plugins/bootstrap/calendar/js/moment.min.js"
// type="text/javascript"></script>');
// $('body').append('<script
// src="'+contextPath+'/scripts/plugins/bootstrap/calendar/js/fullcalendar.min.js"
// type="text/javascript"></script>');
// $('body').append('<script
// src="'+contextPath+'/scripts/plugins/bootstrap/calendar/js/lang-zh-cn.js"
// type="text/javascript"></script>');
// $('body').append('<script
// src="'+contextPath+'/scripts/plugins/bootstrap/calendar/js/jquery-ui.min.js"
// type="text/javascript"></script>');
// $('body').append('<script
// src="'+contextPath+'/scripts/plugins/bootstrap/calendar/ext/jquery.calendar.extends.js"
// type="text/javascript"></script>');


var calendarbt_designer = {

	/**
	 * 设置控件名称
	 */
	setName : function(component, val) {
		var $component = $(component);
		var prop = "name";
		calendarbt_designer.setDataRule(component,prop,val);
		if (val == '') {
			$component.attr("cname", "");
		} else {
			$component.attr("cname", val);
		}
	},

	/**
	 * 初始化渲染
	 */
	setBodyStyle : function(component, val) {
		if (val = "render") {
			if (component.find(".portlet").length) {
				return;
			}
			var portletDiv = $("<div class=\" portlet light portlet-fit bordered calendar\"></div>");
			var portletBodyDiv = $("<div class=\"portlet-body \"></div>");
			portletBodyDiv.append(component.find('.has-toolbar'));
			portletDiv.append(portletBodyDiv);
			component.html(portletDiv);
			var h = {
				left : 'title',
				center : '',
				right : 'prev,next,today,month,agendaWeek,agendaDay'
			};
			component.find('.has-toolbar').fullCalendar({
						header : h,
						defaultView : 'month',
						slotMinutes : 15,
						editable : true,
						droppable : true
					});
		}
	},

	/**
	 * 设置标题是否显示
	 */
	setTitleDisplay : function(component, val) {
		var prop = "titleDisplay";
		calendarbt_designer.setDataRule(component,prop,val);
		
		var compId = component.attr("id");
		var content = component.find(".portlet");
		if (val == 'display') {
			if (content.find(".portlet-title").length) {
				content.find(".portlet-title").show();
				content.find(".caption").show();
				return;
			}
			var portletTitleDiv = $("<div class=\"" + compId
					+ " portlet-title\"></div>");
			var captionDiv = $("<div class=\"" + compId + "  caption\"></div>");
			captionDiv
					.append("<i class=\" icon-layers font-green\"></i><span class=\"caption-subject font-green sbold uppercase "
							+ compId
							+ " frame-variable\" frame-variable=\"title\">Calendar</span>");
			portletTitleDiv.append(captionDiv);
			content.prepend(portletTitleDiv);
		} else if (val == 'hide') {
			if (content.find(".portlet-title").length) {
				content.find(".caption").hide();
				return;
			}
		} else if (val == 'none') {
			if (content.find(".portlet-title").length) {
				content.find(".portlet-title").hide();
				return;
			}
		}
	},
	/**
	 * 设置标题名称
	 */
	setTitleName : function(component, val) {
		var prop = "titleName";
		calendarbt_designer.setDataRule(component,prop,val);
		var compId = component.attr("id");
		var content = component.find(".portlet");
		content.find(".caption span").html(" " + val);
	},

	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
		// var obj=component.find(".portlet");
		if (val == '') {
			component.css("margin-" + direct, "");
		} else {
			component.css("margin-" + direct, val + "px");
		}
	},

	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.setPositon(component, 'right', val);
	},
	setPositon : function(component, direct, val) {
		component.css("position", "relative");
		if (val == '') {
			component.css(direct, '');
		} else {
			component.css(direct, val + "px");
		}
	},

	/**
	 * 设置定位方式
	 */
	setPositonStyle : function(component, val) {
		var prop = "position";
		calendarbt_designer.setDataRule(component,prop,val);
		component.css("position", val);
	},

	/**
	 * 设置宽度
	 */
	setWidth : function(component, val) {
		var prop = "width";
		calendarbt_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.css("width", '');
		} else {
			component.css("width", val + "px");
		}

	},

	/**
	 * 设置高度
	 */
	setHeight : function(component, val) {

		alert("最好自适应");
		// var obj = component.find("div.fc-row.fc-week.fc-widget-content");
		// if(val==''){
		// obj.css("height",'');
		// }else{
		// obj.css("height",val+"px");
		// }
	},
	setDataRule : function(component,prop,val){
		
		var lastVal = calendarbt_designer.getDataRule(component,prop)
		
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
		
		//debugger;
		var value ="";
		if(component.attr("data-rule")){
			value = JSON.parse(component.attr("data-rule"))[prop];
		}
		
		return value;
	},

	setBorderColor : function(component,val){
		var prop = "borderColor";
		calendarbt_designer.setDataRule(component,prop,val);
		calendarbt_designer.updateStyleByKey(component,prop,val);
	},

	/**
	 * 获取styler并且赋值
	 */
	updateStyleByKey: function (component,key,val){
		var styler = calendarbt_designer.getStyler(component);
		styler[key] = val;
		calendarbt_designer.updateStyler(component,styler);
		calendarbt_designer.updateStylerBox(component,styler);
	},
	/**
	 * 获取styler数组
	 */
	getStyler: function (component){
		var sid = calendarbt_designer.getStylerId(component);
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
		var sid = calendarbt_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	/**
	 * 设置样式
	 */
	updateStylerBox: function(component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		var _default = "";

		var _border = _id + " .fc-unthemed .fc-divider,"+_id+" .fc-unthemed .fc-popover, "+_id+".fc-unthemed .fc-row, "+_id+".fc-unthemed tbody,"+_id+" .fc-unthemed td, "+_id+" .fc-unthemed th,"+_id+" .fc-unthemed thead{";
		
		if(styler['borderColor']){
			_border += 'border-color:'+styler['borderColor']+";";
		}
		_border += "}"


		
		_default += _border;

		var stylebox = calendarbt_designer.getStyleBox(component);
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
    	setStaticData(null,'grid');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// calendarbt_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			calendarbt_designer.setting = $.extend(false,calendarbt_designer.setting,datas.dataGrid);
			
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
				setting : calendarbt_designer.setting,
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
			calendarbt_designer.setting.columns = calendarbt_designer.setting.columns.slice(0,calendarbt_designer.setting.columns.length-1);
			calendarbt_designer.setting.toolbar = "two";
			calendarbt_designer.setting.statics = true;
			component.calendarExt(calendarbt_designer.setting);
			//获取页面id
			//获取控件id

		}
	},

	
}
