!function($){
	
	//默认参数
	var defaults = {
		defaultVal:"",
		maxValue:1000000000,
		minValue:-1000000000,
		decimals:0,
		step:1,
		prefix:"",
		postfix:"",
		visible:true,
		enabled:true
	};	
    
    $.fn.touchSpinExt = function(opts){
	  	if ( methods[opts] ) {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.touchSpinExt' );
        }    
	};
    
	
	var methods = {
		init:function(options){	//初始化
			var opts = $.extend(true,{},defaults, options);
			var $this = $(this);
			$this.removeAttr("contenteditable");
			
			$this.find("input").TouchSpin({
				initval:opts.defaultVal,
            	min: opts.minValue,
	            max: opts.maxValue,
	            step: opts.step,
	            decimals: opts.decimals,
	            prefix: opts.prefix,
            	postfix: opts.postfix
       		});
			methods.display.call(this,opts.visible);
			methods.disabled.call(this,opts.enabled);
			methods.display.call(this,opts.visible);
			$this.data('touchspin',{"element":this,"methods":methods,"defaults":defaults,"options":opts});
		},
		getValue:function(){
			var jq = methods.getOject.call(this);
			return jq.val();
		},
		setValue:function(val){
			var opts = $(this).data('touchspin').options,format='{0:#.';
			var jq = methods.getOject.call(this);
			if(val != undefined && (typeof val != 'string' || val != '') && val != null){
			if(parseFloat(opts.maxValue || opts.max)>=parseFloat(val)&&parseFloat(val)>=parseFloat(opts.minValue || opts.min)){
				for(var i=0;i<opts.decimals;i++){
					format+="#";
				}
				format +="}";
				val = hmtdUtils.format(format,parseFloat(val));
				
				
				jq.attr("setValue", "setValue");
				jq.val(val);
			}else{
				jq.val("");
				//alert("注意：超出最大或最小值范围！");
			}
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
		},
		readable:function(bl){  //是否只读
			var jq = methods.getOject.call(this);
			if(!bl){
				jq.removeAttr("readonly");
			}else{
				jq.attr("readonly",true);
			}
		},
		display:function(bl){   	//是否显示
			var $this = $(this);
			if (!bl){ //隐藏
				$this.css("display", "none");
			}else{ //显示
				$this.css("display","");
			}
		},
		//是否启用    
		disabled:function(bl){
			var $this = $(this);
			var input = $this.find("input");
			var btn_down = $this.find("button.bootstrap-touchspin-down");
			var btn_up = $this.find("button.bootstrap-touchspin-up");
//	   		var span_prefix = $this.find("span.bootstrap-touchspin-prefix");  
//	   		var span_post = $this.find("span.bootstrap-touchspin-postfix");
			if(!bl){
				input.attr("disabled","disabled");		
				btn_down.attr("disabled","disabled");		
				btn_up.attr("disabled","disabled");			
			}else{
				input.removeAttr("disabled");
				btn_down.removeAttr("disabled");
				btn_up.removeAttr("disabled");
			}
		},
		isEmptyObject: function(obj) { //空对象判断工具
			for ( var name in obj ) { 
				return false; 
			} 
			return true; 
		}
	};
		
	

}(jQuery);