! function($) {

	//默认参数
	var defaults = {
		url: "/glaf/mx/form/calendar/data", //远程获取数据地址
		date: [],
		ajax: {
			url: '',
			type: 'post',
			dataType: 'json',
			data: {},
			contentType: "application/json",
			async: true,
			success: function(ret) {},
			error: function() {
				//console.log("Ajax请求超时!");
			}
		},

		toolbar: "four",
		defaultView: "month",
		visible: true
	};


	var brandColors = {
		'blue': '#89C4F4',
		'red': '#F3565D',
		'green': '#1bbc9b',
		'purple': '#9b59b6',
		'grey': '#95a5a6',
		'yellow': '#F8CB00'
	};


	$.fn.calendarExt = function(opts) {
		if (methods[opts]) {
			return methods[opts].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof opts === 'object' || !opts) {
			opts.statics ? methods.staticCalendar.apply(this,arguments) : null;
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + opts + ' does not exist on jQuery.calendarExt');
		}
	};
   

	var methods = {
		init: function(options) { //初始化
			
			var opts = $.extend(true, {}, defaults, options);
			var $this = $(this);
			$this.data('calendarbt', {
				"element": this,
				"methods": methods,
				"defaults": defaults,
				"options": opts
			});
			//$this.data("opts",opts);
			
			methods.createELe.call(this, opts);
			methods.initEvents($this, opts);
			methods.display.call(this, opts.visible);
		},
		//静态初始化构建日历组建
		staticCalendar : function(options){
			var content = "<div class='portlet light portlet-fit bordered calendar'><div class='portlet-title'><div class='caption'>"
					+ "<i class=' icon-layers font-green'></i> <span class='caption-subject font-green sbold uppercase frame-variable calendarbt_1498527960183' frame-variable='title'>Calendar</span>"
					+ "</div></div><div class='portlet-body frame-variable calendarbt_1498527960183' frame-variable='body'>"
					+ "<div class='has-toolbar fc fc-ltr fc-unthemed'><div class='fc-toolbar'><div class='fc-left'></div><div class='fc-right'>"
					+ "<div class='fc-button-group'><button type='button' class='fc-prev-button fc-button fc-state-default fc-corner-left'>"
					+ "<span class='fc-icon fc-icon-left-single-arrow'></span></button><button type='button' class='fc-next-button fc-button fc-state-default'>"
					+ "<span class='fc-icon fc-icon-right-single-arrow'></span></button>"
					+ "<button type='button' class='fc-today-button fc-button fc-state-default fc-state-disabled' disabled='disabled'>今天</button>"
					+ "<button type='button' class='fc-month-button fc-button fc-state-default fc-state-active'>月</button>"
					+ "<button type='button' class='fc-agendaWeek-button fc-button fc-state-default'>周</button>"
					+ "<button type='button' class='fc-agendaDay-button fc-button fc-state-default fc-corner-right'>日</button>"
					+ "</div></div><div class='fc-center'></div><div class='fc-clear'></div></div>"
					+ "<div class='fc-view-container' style=''><div class='fc-view fc-month-view fc-basic-view'></div></div></div></div></div>";
			var $this = $(this);
			$this.append(content);
		},
		createELe: function(options) { //初始化日历组件
			var $this = $(this);
			//		var width = $(id).css("width");
			//		var height = $(id).find("div.fc-row.fc-week.fc-widget-content").css("height");
			if (options.title) {
				$this.find(".portlet .caption span").html(options.title);
			}
			var toolbar,
				isbasic = options.basicModel;
			if (options.toolbar == "one") {
				toolbar = {
					right: 'title, prev, next',
					center: '',
					left: isbasic ? 'basicDay, basicWeek, month, today' : 'agendaDay, agendaWeek, month, today'
				};
			} else if (options.toolbar == "two") {
				toolbar = {
					right: 'title',
					center: '',
					left: isbasic ? 'basicDay, basicWeek, month, today, prev,next' : 'agendaDay, agendaWeek, month, today, prev,next'
				};
			} else if (options.toolbar == "three") {
				toolbar = {
					left: 'title, prev, next',
					center: '',
					right: isbasic ? 'today,month,basicWeek,basicDay' : 'today,month,agendaWeek,agendaDay'
				};
			} else if (options.toolbar == "four") {
				toolbar = {
					left: 'title',
					center: '',
					right: isbasic ? 'prev,next,today,month,basicWeek,basicDay' : 'prev,next,today,month,agendaWeek,agendaDay'
				};
			}

			options.toolbar = toolbar;
			$this.find(".has-toolbar").fullCalendar('destroy');
			$this.find(".has-toolbar").html("");

			if (isbasic) {
				options.defaultView == "agendaWeek" && (options.defaultView = "basicWeek");
				options.defaultView == "agendaDay" && (options.defaultView = "basicDay");
			}

			options.rid = $this.attr('rid');
			methods.render(options, $this);
			if (!methods.isEmptyObject(options.dataSourceSet)) {
				methods.refresh.call(this);
			}
		},
		render: function(opts, $this) { //初始化渲染
			var eventsData = [];
			$this.find("div.has-toolbar").fullCalendar({
				header: opts.toolbar,
				defaultView: opts.defaultView,
				slotMinutes: 15,
				selectable: true,
				editable: false,
				droppable: false,
				events: eventsData,
				eventClick: function(calEvent, jsEvent, view) {
					var nowTime = new Date().getTime();
					var clickTime = $(this).attr("ctime");
					if (clickTime != 'undefined' && (nowTime - clickTime < 500)) {
						//alert('操作过于频繁，稍后再试');
						return false;
					} else {
						$(this).attr("ctime", nowTime);
						methods.clickEvent(calEvent, opts);
						//选中一个日期，利用取消选中回调事件刷新数据库
						//$this.find("div.has-toolbar").fullCalendar('select',new Date("1980/01/01 8:00:00"), new Date("1980/01/02 8:00:00"),true);
					}
				},
				dayClick: function(date, allDay, jsEvent, view) {
					var nowTime = new Date().getTime();
					var clickTime = $(this).attr("ctime");
					if (clickTime != 'undefined' && (nowTime - clickTime < 500)) {
						//alert('操作过于频繁，稍后再试');
						return false;
					} else {
						$(this).attr("ctime", nowTime);
						methods.dayClick(date, allDay, opts);
					}
				}
			});
			//$(id).find("div.fc-row.fc-week.fc-widget-content").css("height",height);
		},
		clickEvent: function(event, opts) { //修改事项窗口
			var source = opts.dataSourceSet,
				style = opts.dataLinkPage;
			if (style) {
				for (var k = 0; k < style.length; k++) {
					var ee = style[k],
						rules = ee.rules,
						key = methods._convert(ee.exp, [event], 'key');
					if (eval(key)) {
						if (ee.urlId) {
							var trul = getUrlToSplitter() + contextPath + "/mx/form/page/viewPage?id=" + ee.urlId + "&" + methods._getParamName(rules, event, 'c');
							BootstrapDialog.show({
								title: ee.windowName,
								modal: false,
								message: "<iframe width='100%' height='" + ee.windowHeight + "' src='" + trul + "' frameborder='no' scrolling='no'></iframe>",
								css: {
									width: ee.windowWidth
								}
							});
						}
					}
				}
			}
		},
		dayClick: function(date, allDay, opts) { //新增事项窗口
			var style = opts.dataLinkPage;
			if (style) {
				for (var k = 0; k < style.length; k++) {
					var ee = style[k],
						rules = ee.rules,
						key = methods._convert(ee.exp, [], 'key');
					if (eval(key)) {
						if (ee.urlId) {
							var current = {};
							current.date = methods.dateFormat(date, "yyyy-MM-dd HH:mm:dd");
							var $el = $(allDay.target);
							if ($el.hasClass("fc-day")) {
								current.allDay = 1;
							} else {
								current.allDay = 0;
							}
							var trul = getUrlToSplitter() + contextPath + "/mx/form/page/viewPage?id=" + ee.urlId + "&" + methods._getParamName(rules, current, 'c');
							BootstrapDialog.show({
								title: ee.windowName,
								modal: false,
								message: "<iframe width='100%' height='" + ee.windowHeight + "' src='" + trul + "' frameborder='no' scrolling='no'></iframe>",
								css: {
									width: ee.windowWidth
								}
							});
						}
					}
				}
			}
		},
		getBrandColor: function(color) { //获取背景色
			if (brandColors[color]) {
				return brandColors[color];
			} else {
				return '';
			}
		},
		_convert: function(exp, event, type) { //转换表达式
			var length = event.length,
				o, par, val, reger, tv = (type == 'key' ? "'" : "");
			if (length) {
				for (var i = 0; i < length; i++) {
					o = event[i];
					for (var p in o) {
						if (o[p] && p.split("_0_").length > 1) {
							var kt = p.split("_0_")[1];
							reger = new RegExp("##" + kt + "##", "gm");
							exp = exp.replace(reger, tv + o[p] + tv);
						}
					}
				}
			}
			return exp.replace(/\\"/gm, "'").replace(/##\w*##/gm, type == 'key' ? "''" : "");
		},
		_getParamName: function(rules, param, type) { //获取传递的参数名称
			if (type) {
				if (rules) {
					var r = JSON.parse(rules),
						rule, ruleObj, par, retVal = "";
					for (var p in r) {
						rule = r[p];
						if (rule) {
							for (var i = 0; i < rule.length; i++) {
								ruleObj = rule[i];
								par = param[ruleObj['columnName']];
								if (par) {
									retVal += ruleObj['param'] + "=" + par + "&";
								}
								//								else{
								//									if(ruleObj['type']=="getStart"){
								//										retVal +=ruleObj['param'] + "="+param.date+"&";										
								//									}else if(ruleObj['type']=="getAllDay"){	
								//										retVal +=ruleObj['param'] + "="+param.allDay+"&";
								//									}
								//								}
							}
						}
					}
					return retVal;
				}
				return "";
			} else {
				for (var i = 0; i < rules.length; i++) {
					var rule = rules[i];
					if (param.urlId == rule.outid) {
						return rule.param;
					}
				}
				return "";
			}
		},
		refresh: function() { //刷新
			$this = $(this);
			var opts = $this.data("calendarbt").options;
			methods._doAjax($this, opts);
		},
		query: function(param) {
			$this = $(this);
			var opts = $this.data("calendarbt").options;
			methods._doAjax($this, opts, param);
		},
		_doAjax: function($this, opts, param) { //发送ajax请求
			var view = $this.find("div.has-toolbar").fullCalendar('getView');
			var start = view.intervalStart != null ? view.intervalStart._d : new Date();
			//var day = start.getDate();
			var month = start.getMonth() + 1;
			var year = start.getFullYear();
			var dataParams = {};

			var ar = opts.ajax,
				ajaxData = typeof opts.ajax.data === "string" ? JSON.parse(opts.ajax.data) : null,
				ajaxDataParam = ajaxData && ajaxData.params ? JSON.parse(ajaxData.params) : null;
			if (ajaxDataParam) {
				$.extend(true, dataParams, ajaxDataParam);
			}

			dataParams.year = year + "";
			dataParams.month = month + "";

			if (param) {
				$.extend(true, dataParams, param);
			}

			dataParams.params = JSON.stringify(dataParams);
			dataParams.rid = opts.rid;



			ar.data = JSON.stringify(dataParams);
			ar.url = opts.url;
			ar.success = function(ret) {
				if (ret) {
					$this.find("div.has-toolbar").fullCalendar('removeEvents'); //清空上次查询到的日历行程
					var eventsData = methods.handleData(ret, opts);
					$.each(eventsData, function(i, o) {
						$this.find("div.has-toolbar").fullCalendar('renderEvent', o, true);
					});
				}
			}
			$.ajax(ar);
			//			var time = start.getTime();
			//			var isclicked = false ;
			//			$.each(opts.date,function(i,o){
			//				var date = opts.date;
			//				if(date[i]==time){
			//					isclicked = true;
			//					return;
			//				}
			//			});
			//			if(!isclicked){
			//				$.ajax(ar);
			//				opts.date.push(time);					
			//			}	
		},
		handleData: function(ret, opts) { //处理ajax接收的数据
			var eventsData = [];
			$.each(ret, function(i, o) {
				var start = new Date(Date.parse(o.start.replace(/-/g, "/")));
				var stop = new Date(Date.parse(o.end.replace(/-/g, "/")));
				eventsData[i] = o;
				eventsData[i].start = start;
				eventsData[i].end = stop;
				eventsData[i].backgroundColor = methods.getBrandColor(o.backgroundColor);
				if (parseInt(o.allDay)) {
					eventsData[i].allDay = true;
				} else {
					eventsData[i].allDay = false;
				}

				var style = opts.dataLinkPage;
				if (style) {
					for (var k = 0; k < style.length; k++) {
						var ee = style[k],
							key = methods._convert(ee.exp, [o], 'key');
						if (eval(key)) {
							if (ee.bgcColor != "") {
								eventsData[i].backgroundColor = ee.bgcColor;
							}
						}
					}
				}
			});
			return eventsData;
		},
		isEmptyObject: function(obj) { //空对象判断工具
			for (var name in obj) {
				return false;
			}
			return true;
		},
		dateFormat: function(time, format) { //格式化日期工具
			var date = new Date(time);
			var tf = function(i) {
				return (i < 10 ? '0' : '') + i
			};
			return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
				switch (a) {
					case 'yyyy':
						return tf(date.getFullYear());
						break;
					case 'MM':
						return tf(date.getMonth() + 1);
						break;
					case 'mm':
						return tf(date.getMinutes());
						break;
					case 'dd':
						return tf(date.getDate());
						break;
					case 'HH':
						return tf(date.getHours());
						break;
					case 'ss':
						return tf(date.getSeconds());
						break;
				}
			});
		},
		initEvents: function($this, opts) { //注册事件
			var btnFunc = {
				prev: function(e) {
					methods._doAjax($this, opts);
				},
				next: function(e) {
					methods._doAjax($this, opts);
				},
				today: function(e) {
					methods._doAjax($this, opts);
				}
			};
			$.each(btnFunc, function(type, fn) {
				$(document.body).on("click.calender.prev" + type, "button.fc-" + type + "-button", fn);
			});
			//		   $this.find("div.has-toolbar").data("fullCalendar").options.unselect  = function (view, jsEvent){
			//				methods.refresh($this);
			//		   };
		},
		display: function(bl) { //是否显示
			var $this = $(this);
			if (!bl) {
				$this.css("display", "none");
			} else {
				$this.css("display", "");
			}
		}
	};



}(jQuery);