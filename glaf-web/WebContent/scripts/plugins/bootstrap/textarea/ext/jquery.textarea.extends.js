!function($){
	
	//默认参数
	var defaults = {
		visible:true,
		enabled:true,
		defaultVal:""
	};
	
	
	$.fn.textAreaBtExt = function(opts){
		if ( methods[opts] ) {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
//        	methods.init.apply( this, arguments );
//        	alert(methods.getValue.apply(this));
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.textAreaBtExt' );
        }
	}
	var methods = {
		init:function(options){	//初始化
			var opts = $.extend(true,{},defaults, options);
			methods.setPlaceholder.call(this,opts.placeholder);
			methods.setValue.call(this,opts.defaultVal);
			methods.display.call(this,opts.visible);
			methods.disabled.call(this,opts.enabled);
			$this.data('textareabt',{"element":this,"methods":methods,"defaults":defaults,"options":opts});
		},
		getValue : function(){
			var jq = methods.getOject.call(this);
			return jq.val();
		},
		//添加默认值
		setValue:function(val){
			var jq = methods.getOject.call(this);
			val != undefined ? jq.val(pubsub.htmlUnescape(val)) : null;
		},
		//添加默认值
		setPlaceholder:function(val){
			var jq = methods.getOject.call(this);
			if(val != undefined && val != ""){
				jq[0].placeholder = val;
			} 
		},
		//是否显示
		display:function(bl){
			var $this = $(this);
			if (!bl){    
				$this.css("display", "none");
			}else{
				$this.css("display", "");
			}
		},
		//是否启用    
		disabled:function(bl){
			var jq = methods.getOject.call(this);
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
			if ($this.find("textarea").length>0) {
				obj = $this.find("textarea");
			} else {
				obj = $this;
			}
			return obj;
		}
			
	}
	
}(jQuery);