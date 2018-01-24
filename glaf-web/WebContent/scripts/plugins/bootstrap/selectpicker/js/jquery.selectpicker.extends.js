(function($, window, document, undefined) {
	var plugin = "selectpicker", optionKey = plugin + ".options",
	
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
				this.data("selectpicker", new Plugin(this, params));
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
			change : function(item){
				
			},
			click : function(item){
				
			}
		}	
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    target = that.target,
			    opt = that.option;
			opt.visible ? target.css("display","block") : target.css("display","none");
            that.query();
            $("body").on('tap',"#"+target.attr("id")+"_tab",function(e){
            	var pick = $(e.currentTarget).closest(".mui-picker"),
			    text = "";
				var length = parseInt(pick.attr("grade"));
				var pickItems = opt.picker.getSelectedItems(),items = [];
				for(var i = 0 ; i <= length ; i++){
					if(i == 0){
						text = text + pickItems[i].text;
					}
					else{
						pickItems[i] != undefined && pickItems[i]['value'] != undefined ? (text = text + "," + pickItems[i].value) : undefined;
					}
					items.push(pickItems[i]);
				}
				opt.events.click(items);
				target.val(text);
				
				that.hidden();
            });
		},
		query:function(data,$target){
			var that = this;
			var ar = that.option.read;
			
			ar.success = function(ret) {
				that._ajaxSuccess(ret);
			};
			//保存查询参数
			ar._query_params_ = data;
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params),data?data.id:null);
		},
		getParams: function(params) {
			// return params || {};
			var that = this,
				data = that.option.read.d_data__ = (that.option.read.__data__ ? $.extend({}, that.option.read.__data__) : undefined); //取动态参数
			var ret = $.extend({}, data || this.option.read.data);

			if (params && data) { //如果不是第一次查询

				if (!data.params) {
					data.params = {};
				} else {
					data.params = JSON.parse(data.params);
				}

				for (var key in params) {
					if (!(key in data)) {
						data.params[key] = params[key]; //保存要保留参数					
					} else {
						ret[key] = params[key]; //装载变化参数
					}
				}
				
				data.params = JSON.stringify(data.params);

				ret.params = data.params; //更新要保留参数
			}
			return ret;
		},
		_request: function(ar, params,id) {
			var that = this;
			if (!ar.__success__) { //第一次ajax 调用

				ar.__success__ = true;
				ar.__data__ = $.extend(ar.__data__ || {}, ar.data); // 原始参数保存
				ar.d_data__ = $.extend(ar.d_data__ || {}, ar.data); // 动态参数保存
			}
			that._initParams(ar, params,id);
			$.ajax(ar);
		},
		_initParams: function(ar, params,id) {
     		var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);

			
			$.extend(data, {
				sort: this.__sortArray
			});
			if(id){
				data.params = "";
				data.id = id;
			}
			ar.data = this._getParameterMap(data);
		},
		_getParameterMap: function(data) {
			return JSON.stringify(data || {});
		},
		_ajaxSuccess : function(ret){
			var that = this,
			    target = that.target,
			    opt = that.option,
			    arr = [];
			arr = that._selectData(ret.data,opt);
			that._selectpicker(arr);
            
		},
		
		_selectData : function(datas,opt){
			var arr = [],
			    
			    that = this;
			if(opt.EnumValue != undefined && opt.EnumValue != ""){
				$.each(datas,function(i,data){
					var coll = {};
					coll['value'] = data.name;
					coll['text'] = data.name;			
					arr.push(coll);		    
				});
			}
			else{
				$.each(datas,function(i,data){
					var coll = {};
					if(data[opt.pidKey] == undefined){
						coll['value'] = data[opt.dataValueField];
						coll['text'] = data[opt.dataTextField];
						coll['children'] = that.recursion(datas,data,opt);				
						arr.push(coll);
					}
					else{
						if(parseInt(data[opt.pidKey]) == parseInt(opt.defaultParentId)){
					    	coll['value'] = data[opt.dataValueField];
							coll['text'] = data[opt.dataTextField];
							coll['children'] = that.recursion(datas,data,opt);
							
							arr.push(coll);
					    }
					}
				    
				});
				
				if(arr.length == 0){
					
					$.each(datas,function(i,data){
						var coll = {};
						coll['value'] = data[opt.dataValueField];
						coll['text'] = data[opt.dataTextField];
						coll['children'] = that.recursion(datas,data,opt);				
						arr.push(coll);		    
					});
				}
			}
			
			
			
			return arr;
		},
		_selectpicker : function(data){
			var that = this,
			    target = that.target,
			    opt = that.option,
			    _getParam = function(obj, param) {
					return obj[param] || '';
				};
				opt.data = data;
				if(opt.enabled){
					that.enable();
					target.on('click',function(e){
		
						that.show();
						
					});
					
				}
				
		},
        setValue : function(data){
        	
            var that = this,
                opt = that.option,
                target = that.target,
                pk = opt.picker,
                text = "";
        	for(var i = 0 ; i < data.length ; i++){
            	pk.pickers[i].setSelectedValue(data[i]);
            	if(i == 0){
					text = text + data;
				}
				
				else{
					text = text + "," + data;
				}
            	target.val(text);
        	}
        	
		},
		hidden : function(){
			var that = this,
		    target = that.target,
		    opt = that.option;
			opt.picker.hide();
		},
		show : function(target,opt){
			var that = this,
			    target = that.target,
			    opt = that.option;
			opt.picker.show(function(items){
				
				var text = "";
				$.each(items,function(i,it){
					if(i == 0){
						it.text != undefined ? (text = text + it.text ) : undefined;
					}
					else{
						it.text != undefined ? (text = text + "," + it.text ) : undefined;
					}
				});
				target.val(text);
				opt.events.change(items);
		    },false);
			var _muipicker = $(".mui-poppicker-body").find(".mui-picker"),count = 0;
			$.each(_muipicker,function(i,item){
				
				$(item).attr("grade",count);
				count++;
			});
			
			var picker_event = $(".mui-pciker-rule.mui-pciker-rule-ft");
			picker_event.attr("id",target.attr("id") + "_tab");
//			$.each(picker_event,function(i,rule){
//				$(rule).on("tap",function(e){
//				
//				});
//			});
		},
		enable : function(){
			var that = this,
			    target = that.target,
			    opt = that.option,
				Picker = new mui.PopPicker({
					layer: opt.grade
				});
			Picker.setData(opt.data);
			opt.picker = Picker;
			

		},
		disable : function(){
			var that = this,
		    target = that.target,
		    opt = that.option;
			
			opt.picker.dispose();
		},
		clear : function(){
			var that = this,
			    opt = that.option;
			opt.picker.setData();
		},
		reload : function(args,target){
			var that = this;
			that.query(JSON.stringify(args));  
		},
		recursion : function(dats,ret,opt){
			var arr = [],
			    that = this;	 
			$.each(dats,function(i,data){
				var coll = {};
				if(data[opt.pidKey] == ret[opt.indexKey]){
					coll['value'] = data[opt.dataValueField];
					coll['text'] = data[opt.dataTextField];
					coll['children'] = that.recursion(dats,data,opt);
					
					arr.push(coll);
				}
			});
			return arr;
			
		}
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document );

