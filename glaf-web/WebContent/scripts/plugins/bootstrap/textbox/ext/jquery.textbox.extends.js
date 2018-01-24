!function($){
	
	var defaults = {
		visible:true,
		enabled:true,
		defaultVal:"",
		message:""
	};	
	
	$.fn.textboxBtExt = function(opts){	
		if ( methods[opts] && typeof opts === 'string') {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
//        	methods.init.apply( this, arguments );
//        	alert(methods.getValue.apply(this));
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.textboxBtExt' );
        }
	}
	
	var methods = {
		init : function(options){
			 var id =$(this).attr("id");
			 // mui('#'+id).input(); 
			 // $("#"+id).focus();
			 // $("#"+id).addClass('mui-active');
			if($(this).attr("disabledColor")){
				var style = $("<style></style>");
				style.text("input#"+$(this).attr("id") + "[disabled]{ background-color:"+$(this).attr("disabledColor")+"};");
				$(this).append(style);
			}
			if($(this).attr("reaonlyColor")){
				var style = $("<style></style>");
				style.text("input#"+$(this).attr("id") + "[readonly]{ background-color:"+$(this).attr("reaonlyColor")+"};");
				$(this).append(style);
			}

			var opts = $.extend(true,{},defaults, options);
			methods.setValue.call(this,opts.defaultVal);
			methods.setMessage.call(this,opts.message);
			methods.display.call(this,opts.visible);
			methods.disabled.call(this,opts.enabled);
			methods.readable.call(this,opts.readable);
	        
			$(this).data('textboxbt',{"element":this,"methods":methods,"defaults":defaults,"options":opts});
		},
		getValue : function(){
			var jq = methods.getOject.call(this);
			return jq.val();
		},
		keyup : function(){
			
		},
		
		setValue : function(val){ 	//添加默认值
			var jq = methods.getOject.call(this);
			jq.attr("setValue", "setValue");
			jq.val(pubsub.htmlUnescape(val));
		},
		setMessage : function(message){ //添加提示信息
			var jq = methods.getOject.call(this);
			if(message){
				$this.attr("placeholder",message);
			}else{
				$this.attr("placeholder","");			
			}
		},
		display : function(bl){    //是否显示
			var jq = methods.getOject.call(this);
			if(jq.closest("div").hasClass("input-icon")){
				if (!bl){    
					jq.closest("div").css("display", "none");
				}else{
					jq.closest("div").css("display", "");
				}
			}else{
				if (!bl){    
					jq.css("display", "none");
				}else{
					jq.css("display", "");
				}
			}
		},
		disabled : function(bl){    //是否启用   
			var jq = methods.getOject.call(this);
			if(jq.attr("sdisabled")){
				return;
			}
			if(!bl){
				jq.attr("disabled","disabled");		
			}else{
				jq.removeAttr("disabled");
			}
		},
		readable:function(bl){  //是否只读
			var jq = methods.getOject.call(this);
			if(!bl){
				jq.removeAttr("readonly");
			}else{
				jq.attr("readonly",true);
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

	
	
}(jQuery);