!function($){
	
	//默认参数
	var defaults = {
		format:"yyyy-MM-dd",
		defaultsystemdate:"null",
		position:"right top",
		visible:true,
		enabled:true,
		readable:true,
		time:""
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


	$.fn.datepickerExt = function(opts){
		if ( methods[opts] && typeof opts === 'string') {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
//        	methods.init.apply( this, arguments );
//        	alert(methods.getValue.apply(this));
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.datepickerExt' );
        }
	}

	var methods = {
		init : function(options){
			var opts = $.extend(true,{},defaults, options);
			$(this).data('datepickerbt',{"element":this,"methods":methods,"defaults":defaults,"options":opts});
			methods.initElement.call(this,opts);
			methods.defaultsystemdate.call(this,opts);
			methods.display.call(this,opts.visible);
			methods.disabled.call(this,opts.enabled);
			methods.readable.call(this,opts.readable);

			if(opts.inputable){
				var $input = $(this).find("input");
				$input.removeAttr("readonly");
				$input.blur();

				$input.bind('keydown', function (e) {
					var key = e.which;
					if (key == 13) {
						$(this).blur();
					}
				})
			}
			
		},
		getValue : function(){
			var val,$this = $(this);
			if($this.data("datepickerbt")){
				if($this.find(".datepickerbt").length>0){
					val = $this.find(".datepickerbt").datepicker("getDate")||"";
				}else{
					val = $this.datepicker("getDate")||"";
				}
			}else if($this.data("datepicker")){
				var datepicker = $this.data("datepicker");
				if(datepicker.getDate){
					val = datepicker.getDate();
				}
			}
			return val;
		},
		setValue : function(val){ 	//添加默认值
			var $this = $(this);
			var jq = methods.getOject.call(this);
			if($(this).data("datepickerbt")){
//				var opts = $(this).data("datepickerbt").options;
//				var dateStr = dateFormat(val,opts.format);
				val = hmtdUtils.parseDate(val) != null ? hmtdUtils.parseDate(val) : val;
				if($this.find(".datepickerbt").length>0){
					$this.find(".datepickerbt").datepicker("setDate",val);
				}else{
					$this.datepicker("setDate",val);
				}
			}else if($this.data("datepicker")){
				var datepicker = $this.data("datepicker");
				val = hmtdUtils.parseDate(val) != null ? hmtdUtils.parseDate(val) : val;
				if(datepicker.setDate){
					datepicker.setDate(val);
				}
			}
		},
		initElement : function(options){		
			var $this = $(this);
			var fmt = options.format.replace(/MM/g,"mm");
			var datepicker_config = {
				autoclose: true,
				clearBtn:true,
				format:fmt,
				language: 'zh-CN',
				todayBtn: 'linked',
				orientation: options.position,
			}
			if(options.format == "yyyy-MM" || options.format == "yyyy/MM" || options.format == "yyyy年MM月" || options.format == "yyyyMM"  ){
				datepicker_config['startView'] = 1;
				datepicker_config['maxViewMode'] = 2;
				datepicker_config['minViewMode'] = 1;
			}
			/*else if(options.format == "yyyy-MM-dd" || options.format == "yyyy年MM月dd日" || options.format == "yyyyMMdd" || options.format == "yyyy-MM-dd HH:mm:ss" 
				|| options.format == "yyyy年MM月dd日 HH时mm分ss秒 " || options.format == "yyyy/MM/dd"){
				datepicker_config['startView'] = 0;
				datepicker_config['maxViewMode'] = 2;
			}*/
			else if(options.format == "yyyy" || options.format == "yyyy年 "){
				datepicker_config['startView'] = 2;
				datepicker_config['maxViewMode'] = 2;
				datepicker_config['minViewMode'] = 2;
			}
			if($this.hasClass("datepickerbt")){
				$this.datepicker(datepicker_config);
			}else{
				$this.find(".datepickerbt").datepicker(datepicker_config);
			}
		},
		defaultsystemdate : function(options){ 	//默认系统日期
			if(options.defaultsystemdate=="null"){
			}else {
				var time;
				if(pageParameters){
					time=pageParameters.sys_date||options.time;
				}
				var date = dateFormat(time,options.format);	
				methods.setValue.call(this,date);
//				methods.getOject.call(this).val(date);
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