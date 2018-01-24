!function($){

	//默认参数
	var defaults = {
		enabled:true,
		visible:true,
		showStatus:true,
		active:false,
		max:"100",
		min:"0",
		value:"50",
		type:"percent"
	};
	
	
	$.fn.progressBarExt = function(opts){	
		if ( methods[opts] ) {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.progressBarExt' );
        }
	}
	
	var methods = {
		init : function(options){
			var $this = $(this);
			var opts = $.extend(true,{},defaults, options);
			$this.data('progressbarbt',{"element":this,"methods":methods,"defaults":defaults,"options":opts});
			methods.display.call(this,opts.visible);
			methods.disabled.call(this,opts.enabled);
			methods.setMax.call(this,opts.max);
			methods.setMin.call(this,opts.min);
			methods.setFix.call(this,opts.value);
			methods.showStatus.call(this,opts.type,opts.showStatus);
			methods.showStripe.call(this,opts.active);
		},
		getValue:function(){
			var $this = $(this);
			var now = $this.find(".progress-bar").css("width");
			now = parseFloat(now.substring(0,now.length-2));
			var total = $this.css("width");
			total = parseFloat(total.substring(0,total.length-2));
			var percent =  parseInt(now/total*100);
			return percent.toString()+"%";
		},
		setValue:function(args){
			var $this = $(this);
			var $span = $this.find(".progress-bar span");	//固定值文本
			var $remain_span = $this.find(".progress_remain span");	//剩余值文本
			var $bar = $this.find(".progress-bar");	//固定值进度条
			var $remain_bar = $this.find(".progress_remain");	//剩余值进度条
			var max = parseFloat($bar.attr("aria-valuemax"));	//最大值
			var min = parseFloat($bar.attr("aria-valuemin"));	//最小值
			var now = parseFloat($bar.attr("aria-valuenow"));	//固定值
			var remain = parseFloat($bar.attr("aria-valueremain"));	//剩余值
			var value_uint = "";	//固定值后缀
			var remain_uint = "";	//剩余值后缀

			if(args.length){
				value_uint = args.value_uint || "";	//固定值后缀
				remain_uint = args.remain_uint || "";	//剩余值后缀
				//获取固定值
				var param_value = args.value;
				//获取剩余值
				var param_remain = args.remain;
				if(param_remain && param_value){
					remain = parseFloat(param_remain);
					now = parseFloat(param_value);
				}else if(param_remain){
					remain = parseFloat(param_remain);
				}else if(param_value){
					now = parseFloat(param_value);
				}
				max = remain + now;	//计算最大值
			}else{
				now = args;	//设置当前值
				remain = max - remain;	//计算剩余值
			}

			$bar.attr("aria-valuemax",max);	//设置最大值
			$bar.attr("aria-valuenow",now);	//设置固定值
			$bar.attr("aria-valueremain",remain);	//设置剩余值

			var value = parseInt((now-min)/(max-min)*100);
			var val=value.toString()+"%";
			$bar.css("width",val);
			$span.html(now + value_uint);

			var remain_val = 100-value;
			remain_val = (remain_val<0?"0":remain_val.toString()) + "%";
			$remain_bar.css("width",remain_val);
			// remain = max - now;
			$remain_span.html(remain + remain_uint);
		},
		display : function(visible){//是否显示
			var $this = $(this);
			if (!visible){    
				$this.css("display", "none");
			}else{
				$this.css("display", "");
			}
		},
		disabled :function(bl){//是否启用
			var $this = $(this);
			if(!bl){
				$this.css("cursor","not-allowed");		
			}else{
				$this.css("cursor","");
			}
		},
		setMax : function(max){//设定最大值
			var $this = $(this);
			$this.find(".progress-bar").attr("aria-valuemax",max);
		},
		setMin:function(min){//设定最小值
			var $this = $(this);
			$this.find(".progress-bar").attr("aria-valuemin",min);
		},
		setFix :function(value){//设定固定值
			var $this = $(this);
			$this.find(".progress-bar").attr("aria-valuenow",value);
		},
		showStatus:function(type,status){ 	//显示数字状态
			var $this=$(this);
			var span = $this.find(".progress-bar span");
			var remain_span = $this.find(".progress_remain span");
			var bar = $this.find(".progress-bar");
			var remain_bar = $this.find(".progress_remain");
			var max = parseFloat(bar.attr("aria-valuemax"));
			var min = parseFloat(bar.attr("aria-valuemin"));
			var now = parseFloat(bar.attr("aria-valuenow"));
			var remain = parseFloat(bar.attr("aria-valueremain"));	//剩余值
			var value = parseInt((now-min)/(max-min)*100);
			var val=value.toString()+"%";
			bar.css("width",val);
			//剩余进度条赋值
			var remain_val = 100-value;
			remain_val = (remain_val<0?"0":remain_val.toString()) + "%";
			remain_bar.css("width",remain_val);
			remain = max - now;

			if(status){
				if(type=="percent"){
					span.removeClass("sr-only").html(val);
					remain_span.removeClass("sr-only").html(remain_val);
				}else if(type=="value"){
					span.removeClass("sr-only").html(now.toString());
					remain_span.removeClass("sr-only").html(remain.toString());
				}
			}else{
				span.addClass("sr-only");
			}
		},
		showStripe:function(active){ //激活条纹
			var $this=$(this);
			if(active){
				$this.addClass("progress-striped");
				// if($this.hasClass("progress-striped")){
				// 	$this.addClass("active");
				// }
			}else{
				$this.removeClass("progress-striped");
				// if($this.hasClass("progress-striped active")){
				// 	$this.removeClass("active");
				// }
			}
		}
	}
	

	
	
}(jQuery);