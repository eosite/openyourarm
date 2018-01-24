(function($, window, document, undefined) {
	var plugin = "islider", optionKey = plugin + ".options",
	
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
				this.data("islider", new Plugin(this, params));
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
			
	};
	
	
	$.fn[plugin].methods = {
			init : function(e){
				var that = this,
				    opt = that.option,
				    target = that.target;
				if(opt.width.indexOf("%") != -1 || opt.width.indexOf("px") != -1){
					target.css("width",opt.width);
				} else{
					target.css("width",opt.width + "%");
				}
				if(opt.height.indexOf("%") != -1 || opt.height.indexOf("px") != -1){
					target.css("height",opt.height);
				} else{
					target.css("height",opt.height + "%");
				}

                that.query(null,target);
				
			},
			
			query:function(data,$target){
				var that = this;
				var ar = that.option.read;
				
				ar.success = function(ret) {
					that._ajaxSuccess(ret, $target);
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
					// ar.success = function(ret) {
					// 	that._ajaxSuccess(ret);
					// };
					ar.__success__ = true;
					ar.__data__ = $.extend(ar.__data__ || {}, ar.data); // 原始参数保存
					ar.d_data__ = $.extend(ar.d_data__ || {}, ar.data); // 动态参数保存
				}
				that._initParams(ar, params,id);
				$.ajax(ar);
			},
			_initParams: function(ar, params,id) {
				// var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);
				// ar.data = this._getParameterMap(data);
				
				var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);
//				if (this.options.pagination.paging) {
//					$.extend(data, {
//						page: this.options.pagination.page,
//						pageSize: this.options.pagination.pageSize
//					});
//				}
				
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
			
			_ajaxSuccess : function(datas,$target){
				var that = $target.data("islider");
				that.SliderMUInit(datas.data,$target);
				that.SliderMUIScript($target);
				
			},
			SliderMUInit : function(data,target){
				var that = target.data("islider"),
				    opt = that.option,
				    title = JSON.parse(opt.sliderTitle)[0].columnName,
				    image = JSON.parse(opt.sliderImage)[0].columnName,
				    muiSliderGroup = target.find(".mui-slider-group"),
				    arr = [];
				muiSliderGroup.empty();
				$.each(data,function(i,item){
					if(i == 0){
						arr.push("<div class=\"mui-slider-item mui-slider-item-duplicate\">");
					} else{
						arr.push("<div class=\"mui-slider-item\">");
					}
					
					arr.push("<a href=\"#\">");
					if(item[image].indexOf("/") != -1){
						arr.push("<img src=\""+ contextPath + item[image] +"\">");
					} else{
						var thumbPath = contextPath +"/mx/form/imageUpload?method=downloadById&id=" + item[image];
						arr.push("<img src=\""+thumbPath+"\">");
					}
					
					arr.push("<p class=\"mui-slider-title\">"+ item[title] +"</p>");
					arr.push("</a>");
					arr.push("</div>");
					
				});
				$.each(data,function(i,item){
					if(i < 2){
						if(i == 0){
							arr.push("<div class=\"mui-slider-item\">");
						} else{
							arr.push("<div class=\"mui-slider-item mui-slider-item-duplicate\">");
						}
						arr.push("<a href=\"#\">");
						if(item[image].indexOf("/") != -1){
							arr.push("<img src=\""+ contextPath + item[image] +"\">");
						} else{
							var thumbPath = contextPath +"/mx/form/imageUpload?method=downloadById&id=" + item[image];
							arr.push("<img src=\""+thumbPath+"\">");
						}
						
						arr.push("<p class=\"mui-slider-title\">"+ item[title] +"</p>");
						arr.push("</a>");
						arr.push("</div>");
					}
					
				});
				muiSliderGroup.append(arr.join(""));
			},
			SliderMUIScript : function(target){
				var slider = mui("#" + target.attr("id"));
				slider.slider({
					interval: 5000
				});
					
			}
			
	};
	//格式化日期工具
	
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

