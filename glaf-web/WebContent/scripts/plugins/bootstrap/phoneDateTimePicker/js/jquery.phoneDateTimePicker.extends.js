(function($, window, document, undefined) {
	var plugin = "phoneDateTimePicker", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this.init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("phoneDateTimePicker", new Plugin(this, params));
            } 
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};

	$.fn[plugin].defaults = {
			
	};
	//默认参数
	var defaults = {
		dateTimeStr:"",
		/*format:"yyyy-mm-dd hh:ii:ss",*/
		defaultsystemdate:"null",
		position:"right top",
		visible:true,
		enabled:true,
		readable:true,
		time:""
	};

	
	
	
	$.fn[plugin].methods = {
			init : function(e){
				var that = this,
				    $options = that.option,
				    $target = that.target;
				var $opts = $.extend(true,{},defaults, $options);
				that.initElement.call(this,$opts);
				that.defaultsystemdate.call(this,$opts);
				that.display.call(this,$opts);
				that.disabled.call(this,$opts);			
				that.readable.call(this,$opts);
			},
			getValue : function(){
				var jq = this.getOject.call(this);
				if(jq.val()!=""){
					var date = new Date(jq.val());
					if (date == "Invalid Date") {
						var a = jq.val().split(" ");
						var b = a[0].split("-"); 
						var c = a[1].split(":"); 
						date = new Date(b[0], b[1]-1, b[2], c[0], c[1]);
					}
					return date;
				}else{
					return "";
				}
//				var opts = $(this).data("datetimepickerbt").options;
//				return opts.dateTimeStr;
			},
			setValue : function(val){ 	//添加默认值
//				var jq = methods.getOject.call(this);
				if($(this.target).data("phoneDateTimePicker.options")&&val!=""){
//					var opts = $(this).data("datetimepickerbt").options;
//					var format = opts.format.replace(/mm/g,"MM");
//					format = format.replace(/hh/g,"HH");
//					format = format.replace(/ii/g,"mm");
//					var dateStr = dateFormat(val,format);
					val = hmtdUtils.parseDate(val);
					if(val){
						var time;
						$(this.target).data("phoneDateTimePicker.options").options.dateTimeStr = val;
						if(pageParameters){
							time = pageParameters.sys_date||options.time;
						}
						$(this.target).find("input[id=time]").datetimePicker(val);
					}else{
						/*$(this.target).find("#time-inline").datetimePicker(null);*/
					}
				}
			},
			initElement : function(options){
				var date = new Date();
				var config = {};
				if(options.format.indexOf("-") != -1){
					config.title = "自定义格式";
					config.yearSplit = "-";
					config.monthSplit = "-";
					config.times = function () {
			          return [  // 自定义的时间
				            {
				              values: (function () {
				                var hours = [];
				                for (var i=0; i<24; i++) hours.push(i > 9 ? i : '0'+i);
				                return hours;
				              })()
				            },
				            {
				              divider: true,  // 这是一个分隔符
				              content: ':'
				            },
				            {
				              values: (function () {
				                var minutes = [];
				                for (var i=0; i<59; i++) minutes.push(i > 9 ? i : '0'+i);
				                return minutes;
				              })()
				            },
				            {
				              divider: true,  // 这是一个分隔符
				              content: ':'
				            }
				          ];
					}
									
				} 
				else{
					config.title = "自定义格式";
					config.yearSplit = "年";
					config.monthSplit = "月";
					config.dateSplit = "日";
					config.times = function () {
			          return [  // 自定义的时间
				            {
				              values: (function () {
				                var hours = [];
				                for (var i=0; i<24; i++) hours.push(i > 9 ? i : '0'+i);
				                return hours;
				              })()
				            },
				            {
				              divider: true,  // 这是一个分隔符
				              content: '时'
				            },
				            {
				              values: (function () {
				                var minutes = [];
				                for (var i=0; i<59; i++) minutes.push(i > 9 ? i : '0'+i);
				                return minutes;
				              })()
				            },
				            {
				              divider: true,  // 这是一个分隔符
				              content: '分'
				            }
				          ];
					}
				}
				this.target.find("input[id=time]").datetimePicker(config);
			},
			formatDate : function(format){ 	
				Date.prototype.Format = function (fmt) {
				    var o = {
				        "M+": this.getMonth() + 1, //月份 
				        "d+": this.getDate(), //日 
				        "h+": this.getHours(), //小时 
				        "m+": this.getMinutes(), //分 
				        "s+": this.getSeconds(), //秒 
				        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
				        "S": this.getMilliseconds() //毫秒 
				    };
				    if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
				    for (var k in o){
				    if (new RegExp("(" + k + ")").test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
				}
				    }
				    return fmt;
				}
				var date = new Date();
				date = date != null && date != undefined ? date.format(format) : '';
				return date
			},
			defaultsystemdate : function($opt){ 	//默认系统日期
				var that = this;
				if($opt.defaultsystemdate=="null"){
				}else {
//					var format = options.format.replace(/mm/g,"MM");
//					format = format.replace(/hh/g,"HH");
//					format = format.replace(/ii/g,"mm");
//					var date = dateFormat(options.time,format);
					var date = new Date();
					date = that.formatDate($opt.format);
					this.target.find("input[id=time]").val(date);
				}	
			},
			display : function($opt){//是否显示
				
				if (!$opt.visible){    
					$(this.target).css("display", "none");
				}else{
					$(this.target).css("display", "");
				}
			},
			disabled :function($opt){//是否启用
				
				
			},
			readable:function($opt){//是否只读	
				if(!$opt.readable){
					$(this.target).find("input[id=time]").removeAttr("readonly");	
				}else{
					$(this.target).find("input[id=time]").attr("readonly","true");
				}
			},
			
	};
	//格式化日期工具
	var dateFormat = function(time, format){
	    var t = new Date(time);
	    var tf = function(i){return (i < 10 ? '0' : '') + i};
	    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
	        switch(a){
	            case 'yyyy':
	                return tf(t.getFullYear());
	                break;
	            case 'MM':
	                return tf(t.getMonth() + 1);
	                break;
	            case 'mm':
	                return tf(t.getMinutes());
	                break;
	            case 'dd':
	                return tf(t.getDate());
	                break;
	            case 'HH':
	                return tf(t.getHours());
	                break;
	            case 'ss':
	                return tf(t.getSeconds());
	                break;
	        }
	    })   
	}
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

