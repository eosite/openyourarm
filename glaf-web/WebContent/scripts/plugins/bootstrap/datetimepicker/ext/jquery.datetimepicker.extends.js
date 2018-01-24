!function($){
	
	//默认参数
	var defaults = {
		dateTimeStr:"",
		format:"yyyy-mm-dd hh:ii:ss",
		defaultsystemdate:"null",
		position:"right top",
		visible:true,
		enabled:true,
		readable:true,
		time:""
	};
	
	
	$.fn.datetimepicker.dates['zh-CN'] = {
		days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
		daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
		daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
		months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		meridiem: ["am", "pm"],
		suffix: ["st", "nd", "rd", "th"],
		today: "今天"
	};
	
	
	$.fn.datetimepickerExt = function(opts){	
		if ( methods[opts] && typeof opts === 'string') {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
//        	methods.init.apply( this, arguments );
//        	alert(methods.getValue.apply(this));
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.datetimepickerExt' );
        }
	}
	
	var methods = {
		init : function(options){
			var opts = $.extend(true,{},defaults, options);
			$(this).data('datetimepickerbt',{"element":this,"methods":methods,"defaults":defaults,"options":opts});
			methods.initElement.call(this,opts);
			methods.defaultsystemdate.call(this,opts);
			methods.display.call(this,opts.visible);
			methods.disabled.call(this,opts.enabled);			
			methods.readable.call(this,opts.readable);
			$(this).datetimepicker().on('changeDate', function(ev){
			   	opts.dateTimeStr = new Date(ev.date.valueOf()-8*60*60*1000)||"";
			});
		},
		getValue : function(){
			var jq = methods.getOject.call(this);
			if(jq.val()!=""){
				var date = new Date(jq.val());
				if(jq.val().length == 13){
					date = new Date(jq.val() + ":00");
				}
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
//			var opts = $(this).data("datetimepickerbt").options;
//			return opts.dateTimeStr;
		},
		setValue : function(val){ 	//添加默认值
//			var jq = methods.getOject.call(this);
			if($(this).data("datetimepickerbt")&&val!=""){
//				var opts = $(this).data("datetimepickerbt").options;
//				var format = opts.format.replace(/mm/g,"MM");
//				format = format.replace(/hh/g,"HH");
//				format = format.replace(/ii/g,"mm");
//				var dateStr = dateFormat(val,format);
				val = hmtdUtils.parseDate(val);
				if(val){
					$(this).data("datetimepickerbt").options.dateTimeStr = val;
					$(this).datetimepicker("setDate",val);
				}else{
					$(this).datetimepicker('setUTCDate',null);
				}
			}
		},
		initElement : function(options){
			var $this = $(this);
			if(options.clearBtn){
				var $clearBtn = $('<button class="btn default date-reset" type="button"><i class="fa fa-times"></i></button>');
				$this.find("button.date-set").removeClass("form-control");
				$this.find("span.input-group-btn").prepend($clearBtn);
			}
			switch(options.position)
			{
			case "left top":
			  options.position = "bottom-right";
			  break;
			case "left bottom":
			  options.position = "top-right";
			  break;
			case "right bottom":
			  options.position = "top-left";
			  break;
			default:
			  options.position = "bottom-left";
			}
		    var param = {
					autoclose: true,
			        format: options.format,
			        pickerPosition: options.position,
					language:'zh-CN',
					todayBtn: true,			
		    }
		    if(options.format.indexOf("ii") != -1){
		    	param.minView = 0;
		    } else if(options.format.indexOf("hh") != -1){
		    	param.minView = 1;
		    } else if(options.format.indexOf("dd") != -1){
		    	param.minView = 2;
		    } else if(options.format.indexOf("mm") != -1){
		    	param.minView = 3;
		    } else if(options.format.indexOf("yyyy") != -1){
		    	param.minView = 4;
		    }
			$this.datetimepicker(param);
		},
		defaultsystemdate : function(options){ 	//默认系统日期
			if(options.defaultsystemdate=="null"){
			}else {
//				var format = options.format.replace(/mm/g,"MM");
//				format = format.replace(/hh/g,"HH");
//				format = format.replace(/ii/g,"mm");
//				var date = dateFormat(options.time,format);
				var time ; 
				if(pageParameters){
					time = pageParameters.sys_date||options.time;
				}
				options.dateTimeStr = new Date(time);
				methods.setValue.call(this,options.time);
			}	
		},
		display : function(visible){//是否显示
			var $this = $(this);
			if (!visible){    
				$this.css("display", "none");
			}else{
				$this.css("display", "");
			}
		},
		disabled :function(enabled){//是否启用
			var jq = methods.getOject.call(this);
			if(!enabled){
				jq.attr("disabled","disabled").end().find("button").attr("disabled","disabled");		
			}else{
				jq.removeAttr("disabled").end().find("button").removeAttr("disabled");
			}
		},
		readable:function(readable){//是否只读
			var jq = methods.getOject.call(this);			
			if(!readable){
				jq.removeAttr("readonly");	
			}else{
				jq.attr("readonly","true");
			}
		},
		getOject : function(){
			$this = $(this);
			var obj;
			if ($this.find("input").length>0) {
				obj = $this.find("input");
			} else {
				obj = $this;
			}
			return obj;
		}
	}	

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

	
	
}(jQuery);