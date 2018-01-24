!function($){
	
	//默认参数
	var defaults = {
	};
    
    $.fn.loginPageExt = function(opts){
	  	if ( methods[opts] ) {
            return methods[opts].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof opts === 'object' || ! opts ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  opts + ' does not exist on jQuery.loginPageExt' );
        }    
	};
  

	
	var methods = {
		init:function(options){	//初始化
			var opts = $.extend(true,{},defaults, options);
			var $this = $(this);
			$this.data("opts",opts);
			
			methods.initHtml.call($this,opts);
		
		},
		initHtml:function(opts){
			var $this = $(this);
			$this.find("form.login-form").attr("action","javascript:;");
	
			if($this.hasClass("template1")){
				$this.removeClass("template1 login");
				$("body").addClass("template1 login");
			    handleOthers($this,opts);
			}else if($this.hasClass("template2")){
				$this.removeClass("template2 login");
				$("body").addClass("template2 login");
				handleOthers($this,opts);
			}else if($this.hasClass("template3")){
				$this.removeClass("template3 login");
				$("body").addClass("template3 login");
				handleOthers($this,opts);
			}else if($this.hasClass("template4")){
				$this.removeClass("template4 login");
				$("body").addClass("template4 login");
				handleOthers($this,opts);
			}else if($this.hasClass("template5")){
				$this.removeClass("template5");
				$("body").addClass("template5");
				handleOthers($this,opts);
			}else if($this.hasClass("template6")){
				$this.removeClass("template6");
				$("body").addClass("template6");
				handleOthers($this,opts);
			}
		},
		isEmptyObject: function(obj) { //空对象判断工具
			for ( var name in obj ) { 
				return false; 
			} 
			return true; 
		}
	};
		
	var handleOthers = function($this,opts){
		
		if($this.hasClass("user-login-5")){
			//背景图切换
			var images= [];
			if(opts.bgImage&&opts.bgImage.length==0){
			}else if(opts.bgImage&&opts.bgImage.length==1){
				$('.login-bg').backstretch([
			        contextPath+opts.bgImage[0].name
			        ], {
			          fade: opts.fade*1000,
			          duration:opts.duration*1000
			    });
			}else if(opts.bgImage){
				$.each(opts.bgImage,function(i,o){
					images[i] = contextPath+o.name;
				});
				$('.login-bg').backstretch(images,{
		        	fade: opts.fade*1000,
		        	duration: opts.duration*1000
			    });
			}		
		}else{
			//背景图切换
			var images= [];
			if(opts.bgImage&&opts.bgImage.length==0){
			}else if(opts.bgImage&&opts.bgImage.length==1){
				$.backstretch([
			        contextPath+opts.bgImage[0].name
			        ], {
			          fade: opts.fade*1000,
			          duration: opts.duration*1000
			    });
			}else if(opts.bgImage){
				$.each(opts.bgImage,function(i,o){
					images[i] = contextPath+o.name;
				});
				$.backstretch(images,{
		        	fade: opts.fade*1000,
		        	duration: opts.duration*1000
			    });
			}		
		}	
	};
	
	var handleUniform = function() {
        if (!$().uniform) {
            return;
        }
        var test = $("input[type=checkbox]:not(.toggle, .md-check, .md-radiobtn, .make-switch, .icheck), input[type=radio]:not(.toggle, .md-check, .md-radiobtn, .star, .make-switch, .icheck)");
        if (test.size() > 0) {
            test.each(function() {
                $(this).show();
                $(this).uniform();
            });
        }
    };
    

}(jQuery);


 

