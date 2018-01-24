(function($, window, document, undefined) {
	var plugin = "tabbar", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this._init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("tabbar", new Plugin(this, params));
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
			events : {
				tabClick : function(){
					
				}
			}
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    opt = that.option,
			    target = that.target,
			    tabbar_item = target.find(".weui-tabbar__item"),
	    	    tab__bd_item = target.find(".weui-tab__bd-item");
			//that.__resultSet($target,$opt);
			target.css("margin","0px");
			//that._loadData(that);
			target.css("width",target.parent().width() + "px");

			if(!opt.visible){
				tabbar_item.css("display","none");
				target.find(".weui-bar__item--on").removeClass("weui-bar__item--on");
				target.find(".weui-tab__bd-item--active").removeClass("weui-tab__bd-item--active");
			}
			
		},
       visible : function(args){
    	   var that = this,
    	       target = that.target,
    	       tabbar_item = target.find(".weui-tabbar__item");
    	   args != undefined ? (
    	      $.each(args,function(i,item){
    	    	  if(item.split != undefined){
    	    		  var arr = item.split(",");
        	    	  for(var k = 0 ; k < arr.length ; k++){
        	    		  var tabbar = tabbar_item[parseInt(arr[k]) - 1];
        	    		  $(tabbar).css("display","block");
        	    	  } 
    	    	  } else{
    	    		  var tabbar = tabbar_item[parseInt(item) - 1];
    	    		  $(tabbar).css("display","block");
    	    	  }
    	    	  
    	    	  
    	      })
    	   ) : undefined
       },
       hidden : function(args){
	    	 var that = this, 
		    	 target = that.target, 
		    	 tabbar_item = target.find(".weui-tabbar__item");
			args != undefined ? ($.each(args, function(i, item) {
				if (item.split != undefined) {
					var arr = item.split(",");
					for (var k = 0; k < arr.length; k++) {
						var tabbar = tabbar_item[parseInt(arr[k]) - 1];
						$(tabbar).css("display", "none");
					}
				} else {
					var tabbar = tabbar_item[parseInt(item) - 1];
					$(tabbar).css("display", "none");
				}

			})) : undefined
	      
       },
       select : function(args){
    	   var that = this, 
	    	 target = that.target, 
	    	 tabbar_item = target.find(".weui-tabbar__item"),
	    	 tab__bd_item = target.find(".weui-tab__bd-item"),
	    	 component = $(target).closest(".weui-tab"),
		     dcolor = component.attr("data-color"),
		     scolor = component.attr("select-color");
		    	
    	   target.find(".weui-tabbar__label").css("color",dcolor);
    	   target.find(".weui-tabbar__icon i").css("color",dcolor);
    	  var weui_bar = target.find(".weui-bar__item--on");
    	  var weui_tab_bd =  target.find(".weui-tab__bd-item--active");
    	  weui_bar.removeClass("weui-bar__item--on");
    	  weui_tab_bd.removeClass("weui-tab__bd-item--active");
    	    args != undefined ? ($.each(args, function(i, item) {
    	       
    		   var tabbar = tabbar_item[parseInt(item) - 1];
				var tab__bd = tab__bd_item[parseInt(item) - 1];
				$(tabbar).addClass("weui-bar__item--on");
				$(tab__bd).addClass("weui-tab__bd-item--active");
				$(tabbar).find(".weui-tabbar__label").css("color",scolor);
    			$(tabbar).find(".weui-tabbar__icon i").css("color",scolor);

			})) : undefined;
		   
    	  
       },
     
       linkageNumber : function(args,target){
		   var number = 0,count = 0;
		   $.each(args,function(i,arg){
			   if(i == "no"){
				   number = parseInt(arg);
			   } else{
				   count = parseInt(arg);
			   }	   
		   });
		  var ar = target.find(".weui-tabbar a[href=tab"+number+"]");
		  var badge= ar.find(".weui-badge");
		   if(count != 0){
			   if(badge[0] != undefined){
					  badge[0].innerHTML = count;
				  }
				  else{
					  var ax = "<span class=\"weui-badge\" style=\"position: absolute;top: -.4em;right: 0.5em;\">8</span>";
					  ar.append(ax);
				  }
			  
		   }   
	   }
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

