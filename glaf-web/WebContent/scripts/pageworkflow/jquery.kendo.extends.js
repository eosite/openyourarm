/**
 * mt
 */
(function($, kendo) {

	$.mt = {
		roleList : {
			grid : 'kendoGrid',
			window : 'kendoWindow',
			//multiselect : 'kendoMultiSelect',
			combobox : 'kendoComboBox'
		},
		get : function(url,data,fn){
			$.ajax({
				url : url,
				contentType:"application/json",
				type : 'post',
				async : false,
				data : data,
				dataType : 'json',
				success : function(data){
					if(fn)fn.call(this,data);
				}
			});
		}
	};
	
	$.mt.window = function(options) {
		if (!options || !options.id) {
			throw new Error('window 参数不对!');
		}
		var jq = $('#' + options.id), kendoWindow = 'kendoWindow';
		if (!jq[0]) {
			var defaults = {
				title : 'new Window',
				animation : false,
				modal : true,
				height : '400',
				width : '600',
				content : {},
				appendTo : 'body',
				actions : [ "Maximize", "Close" ]
			};
			// $.extend(defaults,options);
			$.each(defaults, function(k, v) {
				defaults[k] = options[k] == undefined ? v : options[k];
			});
			jq = $('<div>', {
				id : options.id
			}).kendoWindow(defaults);
		} else if (options.refresh) {
			jq.data(kendoWindow).refresh({
				url : options.content.url
			});
		}
		kendoWindow = jq.data(kendoWindow);
		kendoWindow.open();
		return {
			jq : jq,
			kendo : kendoWindow
		};
	};

	$.mt.init = function(opt, fn) {
		if (typeof opt.options == 'string') {
			var method = $.fn[opt.plugin].methods[opt.options];
			if (method) {
				return method(this, opt.param);
			} else {
				return this;
			}
		}
		opt.options = opt.options || {};
		return this.each(function() {
			var state = $.data(this, 'kendoData');
			if (state) {
				$.extend(state.options, opt.options);
			} else {
				opt.options.target = this;
				$.data(this, 'kendoData', {
					options : opt.options,
					target : this
				});
			}
			if (fn)
				fn(this);
		});
	};

})(jQuery, kendo);

/**
 * mtkendo get、set、reload。。。 klaus.wang 2015-05-21
 */
(function($, kendo) {

	var plugin = 'mtkendo';

	function kendoBind(ele) {
		var state = $.data(ele, 'kendoData');
		var opts = state.options;
		var vm = kendo.observable(opts);
		kendo.bind($(ele), vm);
		state.viewModel = vm;
	}

	$.fn[plugin] = function(options, param) {
		return $.mt.init.call(this, {
			plugin : plugin,
			options : options,
			param : param
		}, kendoBind);
	};

	$.fn[plugin].methods = {
		init : function(jq,method,params,fn){
			var kendoData = jq[plugin]('getKendoData'),dataRole = kendoData.dataRole,kendo = kendoData.kendo,roleFunction = $.fn[plugin].roleFunctions[dataRole];
			if(roleFunction){
				if(roleFunction[method])
					return roleFunction[method].call(kendo,jq,params);
			}else if(fn){
				return fn(kendo);
			}
		},
		reload : function(jq, params) {
			
			jq.each(function(index, item) {
				
				var kendo =  $.fn[plugin].methods.init(jq,'reload',params,null);
				
				$.extend(kendo.dataSource.transport.options.read.data,params);
				
				kendo.dataSource.read();
				
			});
		},
		rebind : function(jq){
			var kendoData = jq[plugin]('getKendoData'),K = kendoData.kendo;
			
			if(K.destroy){
				//K.destroy();
				var newJq = jq.clone();
				jq.parent().after(newJq).remove();
				newJq.mtkendo(kendoData.viewModel);
			}
			
		},
		getValue : function(jq, key) {
			
			return $.fn[plugin].methods.init(jq,'getValue',key,function(kendo){
				return jq.val();
			});
			
		},
		setValue : function(jq, val) {
			
			$.fn[plugin].methods.init(jq,'setValue',val,function(kendo){
				return jq.val(val);
			});
			
		},
		options : function(jq) {
			return jq.data('kendoData').options;
		},
		getKendoData : function(jq) {
			var data = jq.data('kendoData') || {},d = jq.data();
			var dataRole = jq.attr('data-role');
			if (!$.mt.roleList[dataRole]) {
				for ( var key in d) {
					if(key.toLowerCase().indexOf(dataRole.toLowerCase()) > 0){
						$.mt.roleList[dataRole] = key;
						break;
					}
				}
			}
			return $.extend(data, {
				dataRole : dataRole || 'text',
				kendo : jq.data($.mt.roleList[dataRole])
			});
		},
		getViewModel : function(jq) {
			return jq.data('kendoData').viewModel;
		}
	};

	$.fn[plugin].roleFunctions = {
		grid : {
			reload : function(jq,params) {
				return this;
			}
		},
		combobox : {
			reload : function(jq,params) {
				this.value(null);// 先把下拉框清空
				return this;
			},
			getValue : function(jq,key) {
				var dataItem = this.dataItem();
				if (dataItem)
					return dataItem[key];
				return null;
			},
			setValue : function(jq,val) {

			}
		}
	};

})(jQuery, kendo);


(function($){
	
	var plugin = 'dialog',key = 'k-dialog';
	
	function createdialog(target){
		var $this = $(target),state = $this.data(plugin),make = function(){
			state.options.height = ($this.height() || state.options.height)*1 + 20;
			state.options.width = ($this.width() || state.options.width)*1 + 20;
			$this.wrap(function(){
				return "<div class='k-dialog'></div>";
			}).css({
				width : state.options.width - 20,
				height : state.options.height - 70,
				overflow:  'auto'
			});
			var $dialog = $this.closest('.k-dialog');
			if(state.options.buttons){
				var $div = $("<div class='k-tools'>").css({
					'text-align' : 'right',
					margin : '5px'
				}).appendTo($dialog),$button;
				
				$.each(state.options.buttons,function(i,v){
					$button = $('<button>',{
						style : 'margin:5px'
					}).text(v.text).kendoButton({
						imageUrl : v.imageUrl,
						click : function(e){
							return v.click ? v.click.call(e,$this) : '';
						}
					});
					
					$div.append($button);
				});
			}
			var d = $dialog.kendoWindow(state.options).data('kendoWindow');
			$this.data(key,d);
			return d;
		};
		
		if(!$this.data(key)){
			make();
		}
		
		$this[plugin]('open');
		$this[plugin]('center');
		
	}
	
	$.fn[plugin] = function(options,param){
		
		if (typeof options == 'string'){
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i,o){
			var state = $.data(o, plugin);
			if (state) {
				$.extend(true,state.options,options);
			} else {
				$.data(o, plugin, {
					options : $.extend(true,{},$.fn[plugin].defaults, options),
					target : null
				});
			}
			createdialog(o);
		});
		
	};
	
	$.fn[plugin].methods = {
		open : function(jq){
			jq[plugin](plugin).open();
		},
		center : function(jq){
			jq[plugin](plugin).center();
		},
		close : function(jq){
			jq[plugin](plugin).close();
		},
		options : function(jq){
			return $.data(jq[0], plugin).options;
		},
		dialog : function(jq){
			return jq.data(key);
		}
	};
	
	$.fn[plugin].defaults = {
		title : 'new Window',
		animation : false,
		modal : true,
		height : '400',
		width : '600',
		content : {},
		appendTo : 'body',
		actions : [ "Close" ]
   };
	
})(jQuery);





/***
 *  扩展方法...................................................
 */
(function($){
	$.fn.extend({
		outter : function(){
			return $('<div>').append(jQuery(this).clone()).html();
		}
	});
	
	$.extend({
		log : function(msg){
			console.log(msg);
		}
	});
	
})(jQuery);

function StringBuffer(str){
	  
	  this.collection = new Array();
	  
	  this.append = function(str){
		  this.collection[this.collection.length] = str;
		  return this;
	  };
	  
	  this.toString = function(split){
		  return this.collection.join(split ? split : '');
	  };
	  
	  this.appendFormat = function(){
		  if(arguments.length == 0) 
			  return this;
		  var str = arguments[0];
		  for(var i = 1; i < arguments.length; i++)  
			  str = str.replace(new RegExp("\\{" + (i - 1) + "\\}","g"), arguments[i]);  
		  return this.append(str);
	  };
	  
	  if(str){
		  this.append(str);
	  }
}

