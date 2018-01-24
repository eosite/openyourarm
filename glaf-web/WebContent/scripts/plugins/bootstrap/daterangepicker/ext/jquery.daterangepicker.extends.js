!function($){
	
	
	//默认参数
	var defaults = {
		format:"yyyy-mm-dd",
		keyWord:"-",
		position:"left top",
		visible:true,
		enabled:true,
		readable:true
	};
	
	
	$.fn.datepicker.dates['zh-CN'] = {
	    days: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
	    daysShort: ["日", "一", "二", "三", "四", "五", "六", "七"],
	    daysMin: ["日", "一", "二", "三", "四", "五", "六", "七"],
	    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	    monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	    today: "今天",
	    clear: "清除"
	};
	
	$.fn.daterangepickerExt = function(opts){	
		if ( methods[opts] ) {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.daterangepickerExt' );
        }
	}
	
	var methods = {
		init : function(options){
			var opts = $.extend(true,{},defaults, options);
			methods.initElement.call(this,opts);
			methods.changeKeyWord.call(this,opts.keyWord);
			methods.defaultsystemdate.call(this,opts);
			methods.display.call(this,opts.visible);
			methods.disabled.call(this,opts.enabled);
			methods.readable.call(this,opts.readable);
			var $this = $(this);
			$this.data('daterangepicker',{"element":this,"methods":methods,"defaults":defaults,"options":opts});
		},
		initElement: function(options){//初始化渲染
			var $this = $(this);
			$this.datepicker({
				autoclose: true,
				clearBtn:true,
				format:options.format,
				language: 'zh-CN',
				todayBtn: 'linked',
				orientation: options.position
			});
		},
		changeKeyWord:function(str){
			var $this = $(this);
			if(str!=""){
				$this.find("span").html(str);	
			}
			if($this.attr("keyWord")){
				$this.find("span").html($this.attr("keyWord"));		
			}
		},
		display:function(bl){	//是否显示
			var $this = $(this);
			if (!bl){    
				$this.css("display", "none");
			}else{
				$this.css("display", "");
			}
		},
		defaultsystemdate : function(options){ 	//默认系统日期
			var that = this,
			    element = that.find(".form-control");
			if(!options.startDefaultDate){
			}else {
				var time ; 
				if(pageParameters){
					time = pageParameters.sys_date||options.time;
				}
				var format = options.format.replace(/mm/g,"MM");
				format = format.replace(/hh/g,"HH");
				format = format.replace(/ii/g,"mm");
				var date = dateFormat(time,format);
				$(element[0]).datepicker('setDate',date);
			}	
			if(!options.endDefaultDate){
			}else {
				
				var time ; 
				if(pageParameters){
					time = pageParameters.sys_date||options.time;
				}
				var format = options.format.replace(/mm/g,"MM");
				format = format.replace(/hh/g,"HH");
				format = format.replace(/ii/g,"mm");
				var date = dateFormat(time,format);
				$(element[1]).datepicker('setDate',date);
			}	
		},
		//日期转字符串（yyyy-MM-dd）
		formatDate : function(elments,obj){ 	
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
			var date = null;
			$.each(obj,function(i,v){
			   date = v;
			});
			
			date = date != null && date != undefined ? date.format("yyyy-MM-dd") : '';
			return date
		},
		disabled:function(bl){	//是否启用    
			var $this = $(this);
			if(!bl){
				$this.find("input").attr("disabled","disabled");		
			}else{
				$this.find("input").removeAttr("disabled");
			}
		},
		
		readable:function(bl){	//是否只读
			var $this = $(this);
				$this.find("input").removeAttr("readonly");		
			if(!bl){
			}else{
				$this.find("input").attr("readonly","true");
			}
		},   
		setValue : function(val){ 	//添加默认值
//			var jq = methods.getOject.call(this);
			if($(this).data("daterangepicker")&&val!=""){
//				var opts = $(this).data("datetimepickerbt").options;
//				var format = opts.format.replace(/mm/g,"MM");
//				format = format.replace(/hh/g,"HH");
//				format = format.replace(/ii/g,"mm");
//				var dateStr = dateFormat(val,format);
				val = hmtdUtils.parseDate(val);
				if(val){
					$(this).data("daterangepicker").options.dateTimeStr = val;
					$(this).datetimepicker("setDate",val);
				}else{
					$(this).datetimepicker('setUTCDate',null);
				}
			}
		},
		dateFormat :function(time, format){   //格式化日期工具
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
	};
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