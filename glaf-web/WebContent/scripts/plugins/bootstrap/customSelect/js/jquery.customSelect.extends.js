(function($, window, document, undefined) {
	var plugin = "customSelect", optionKey = plugin + ".options",
	
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
				this.data("customSelect", new Plugin(this, params));
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
	    events: {
	    	onLoadSuccess: function(data) {},
				
	   },
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    $opt = that.option,
			    $target = that.target;
			that.__resultSet($target,$opt);
			that._loadData(that);
			
		},
		_loadData : function($obj){
			$.ajax($obj.option.ajax.read);
		},
		__resultSet : function($target,$opt){
			var that = this;
			$opt.ajax.read.success = function(ret){
					that.__renderTemplate($target,$opt,ret);
				    $opt.events.onLoadSuccess(ret);
			}
		},
		
		
		__renderTemplate : function($target,$opt,ret){
			var that = this;
			var listboxid = $target.find("a[role=button]").attr("id"); 
			listboxid = listboxid != undefined ? listboxid.replace("-button","-listbox") : undefined;
			$target.find("select[name=select-choice-a]").attr("id",$target.attr("id"));
			$target.find("select[name=select-choice-a]").empty();
			$target.find("a[role=button] span")[0].innerHTML = "请选择";
			$("#"+listboxid).find(".ui-selectmenu-list").empty();
			$("#"+listboxid).find(".ui-header").remove();
			$.each(ret,function(i,data){
				if(i == 0){
					var $element = "<option>菜单</option>"
					$target.find("select[name=select-choice-a]").append($element);
					var _$firstlistboxEle = "<li data-option-index=\"0\"   data-icon=\"true\"  " +
					"role=\"option\" aria-selected=\"true\"><a href=\"#\" tabindex=\"-1\" class=\"ui-btn\">" +
					"菜单</a></li>";
					 $("#"+listboxid).find(".ui-selectmenu-list")[0].innerHTML = $("#"+listboxid).find(".ui-selectmenu-list")[0].innerHTML + _$firstlistboxEle;
						
				}	
				var $element = "<option value="+ data[$opt.DataValueField] +">"+ data[$opt.DataTextField] +"</option>"
				$target.find("select[name=select-choice-a]").append($element);
				var $listboxEle = "<li data-option-index=\""+(i+1)+"\"   data-icon=\"true\"  " +
				"role=\"option\" aria-selected=\"true\"><a href=\"#\" tabindex=\"-1\" class=\"ui-btn\">" +
				""+ data[$opt.DataTextField] +"</a></li>";
				 $("#"+listboxid).find(".ui-selectmenu-list")[0].innerHTML = $("#"+listboxid).find(".ui-selectmenu-list")[0].innerHTML + $listboxEle;
			
			});
			
			
		}
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

