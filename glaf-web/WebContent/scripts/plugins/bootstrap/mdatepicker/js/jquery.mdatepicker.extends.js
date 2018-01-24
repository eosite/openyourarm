(function($, window, document, undefined) {
	var plugin = "mdatepicker", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this.init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("mdatepicker", new Plugin(this, params));
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
			changeByinput : function(val){
				
			}
		}	
	};
	//默认参数
	var defaults = {
		dateTimeStr:"",
		/*format:"yyyy-mm-dd hh:ii:ss",*/
		defaultsystemdate:"null",
		position:"right top",
		visible:true,
		enabled:true,
		readable:true,
		time:""
	};

	
	
	
	$.fn[plugin].methods = {
			init : function(e){
				var that = this,
				    $options = that.option,
				    $target = that.target;
				var $opts = $.extend(true,{},defaults, $options);
				
				that.defaultsystemdate.call(this,$opts);
				that.initElement.call(this,$opts);
				that.display.call(this,$opts);
//				that.disabled.call(this,$opts);			
				that.readable.call(this,$opts);
				if($opts.width.indexOf("px") != -1 || $opts.width.indexOf("%") != -1){
					$target.css("width",$opts.width);
				};
				if($opts.height.indexOf("px") != -1 || $opts.height.indexOf("%") != -1){
					$target.css("height",$opts.height);
				};
//				$target.find("input").attr("readonly","true");
			},
			getValue : function(val){
				return
//				var opts = $(this).data("datetimepickerbt").options;
//				return opts.dateTimeStr;
			},
			setValue : function(val){ 	//添加默认值
				if($(this.target).data("mdatepicker.options")&&val!=""){

					val = hmtdUtils.parseDate(val);
					if(val){
						var time,
						    options = $(this.target).data("mdatepicker.options").options;
						options.dateTimeStr = val;
						this.target.val(this.dateFormat(val,options.format));
					}else{
						/*$(this.target).find("#time-inline").datetimePicker(null);*/
					}
				}
			},
			format : function(target){
				var opt = target.data("mdatepicker.options").options;
				
				if(opt.format.toLocaleLowerCase().indexOf("hh") == -1){
					
					var dataOpt = target.attr("data-options");
					if(dataOpt == undefined){
						dataOpt = {};
					} else{
						dataOpt = JSON.parse(dataOpt);
					}
					dataOpt['type'] = "date";
					target.attr("data-options",JSON.stringify(dataOpt));
				}
				
			},
			initElement : function(options){
		
				var that = this,
				    target = that.target,
				    opt = that.option;
				
				var dataOpt = target.attr("data-options");
				if(dataOpt == undefined){
					dataOpt = {};
				} else{
					dataOpt = JSON.parse(dataOpt);
				}
				
				dataOpt['beginYear'] = opt.minDate;
				dataOpt['endYear'] = opt.maxDate;
				target.attr("data-options",JSON.stringify(dataOpt));
				
				mui.init();
				that.format(target);
				target.on('tap', function(e) {
//					var _self = this;
					
					if(opt.picker) {
						that.tshow();
					} else {
						that._dtPicker(target,opt);
						that.tshow();
					}
					
				})
				
			},
			_dtPicker : function(target,opt){
				var optionsJson = target.attr('data-options') || '{}';
				var options = JSON.parse(optionsJson);
				var id = target.attr('id');
				opt.picker = new mui.DtPicker(options);
				
			},
			formatDate : function(format){ 	
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
				var date = new Date();
				date = date != null && date != undefined ? date.format(format) : '';
				return date
			},
			defaultsystemdate : function($opt){ 	//默认系统日期
				var that = this,
				    target = that;
				if($opt.defaultsystemdate=="null"){
				}else {
                    var ar = {};
					var date = new Date();
					date = that.formatDate($opt.format);
					date != undefined && date != '' ? (ar['value'] = date) : undefined;
					target.attr("data-options",JSON.stringify(ar));
					
											
				}	
			},
			display : function($opt){//是否显示
				
				if (!$opt.visible){    
					$(this.target).css("display", "none");
				}else{
					$(this.target).css("display", "");
				}
			},
			tdisabled : function(){
				this.target.datetimePicker("disable");
				this.target.attr("readonly","true");
				
			},
			disabled :function($opt){//是否启用
				var that = this,
			    opt = that.option;
				opt.picker.dispose();
				opt.picker = null;
				
			},
			thidden : function(){
				var that = this,
			    opt = that.option;
				opt.picker.dispose();
				opt.picker = null;
			},
			tshow : function(){
//				$(this.target).css("display", "block");
				var that= this,
				    target = that.target,
				    opt = that.option;
				opt.picker.show(function (rs) {
//					result.innerText = '选择结果: ' + rs.text;
					target.val(rs.text);
					opt.events.changeByinput(rs.text);
					opt.picker.dispose();
					opt.picker = null;
				});
			},
			readable:function($opt){//是否只读	
				
				if(!$opt.readable){
					this.target.removeAttr("readonly");	
				}else{
					this.target.attr("readonly","true");
				}
			},
			dateFormat : function(time, format){
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
	//格式化日期工具
	
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

