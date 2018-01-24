(function($, window, document, undefined) {
	var plugin = "medialist", optionKey = plugin + ".options",
	
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
				this.data("medialist", new Plugin(this, params));
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
			tap : function(item){
				
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
				$target.find("ul.mui-table-view").empty();
				that.query(null,$target);
				$target.css("width",$options.width);
				$target.css("height",$options.width);
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
				if (this.option.pagination.paging) {
					$.extend(data, {
						page: this.option.pagination.page,
						pageSize: this.option.pagination.pageSize
					});
				}
				
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
				var that = $target.data("medialist");
				var opt = that.option;
				opt.pagination.total = datas.total;
//				opt.pagination.count = datas.data.length;
				opt.data = datas.data;
				var dataView = $target.find("ul.mui-table-view");
				var pageView = $target.find("ul.mui-pagination");
				dataView[0] != undefined ? dataView.empty() : null;
				pageView[0] != undefined ? pageView.empty() : null;
				
				var el = that._buildMediaListHtml(datas.data,$target);
				
				that._buildPagination($target);
				that._buildPageEvent($target);
				
				el.find("li").css("width","100%");
				el.find("li").css("height","25%");
			},
			_buildMediaListHtml : function(data,target){
				var that = target.data("medialist");
			    var arr = that._buildModuleHtml(data,target);
			    target.find("ul.mui-table-view").append(arr.join(""));
			    that._buildTapListenr(target);
			    return target;
			},
			_buildPagination : function(target){
				var opt = target.data("medialist").option,
				    total = opt.pagination.total,
				    count = opt.pagination.pageSize,
				    page = opt.pagination.page,
				    pageable = eval("("+opt.pagination.pageable+")"),
				    buttonCount = pageable.buttonCount,
				    dataButtonCount = Math.ceil(total/buttonCount),
				    ableCount,
				    arr = [];
				opt.pagination.dataButtonCount = dataButtonCount;
				if(dataButtonCount > buttonCount){
					ableCount = buttonCount;
				} 
				else{
					ableCount = dataButtonCount;
				}
				opt.pagination.ableCount = ableCount;
				arr.push("<div class=\"mui-content-padded\">");
				arr.push("<ul class=\"mui-pagination\" style=\"float:right\">");
			    arr.push("<li class=\"mui-previous mui-disabled\">");
				arr.push("<a href=\"#\"><<</a></li>");
			    arr.push("<li class=\"mui-previous mui-disabled\">");
				arr.push("<a href=\"#\"><</a></li>");
				var current = page * ableCount - ableCount + 1; 
				var group = opt.pagination.group;
				if(page > group * ableCount){
					
					group = group + 1;
					opt.pagination.group = group;
				}
				else if(page < group * ableCount - ableCount + 1){
//						opt.pagination.group = opt.pagination.group - 1;
					    
						group = group - 1;
						opt.pagination.group = group;
					
				} 
					
				
				
				var end = group * ableCount;
				var start = group * ableCount - ableCount + 1;
				for(var i = start ; i <= end ; i++){
					var str = "<li ><a href=\"#\">"+ i +"</a></li>";
					if(i == page){
						str = "<li class=\"mui-active\"><a href=\"#\">"+ i +"</a></li>";
					}
					
					
					arr.push(str);
					
				}		
				arr.push("<li class=\"mui-next\"><a href=\"#\">></a></li>");
				arr.push("<li class=\"mui-next\"><a href=\"#\">>></a></li>");
		        arr.push("</ul></div>");
				target.append(arr.join(""));
			},
			_buildPageEvent : function(target){
				var that = target.data("medialist"),
				    opt = that.option;
				    li = target.find(".mui-pagination li");
				li.on("tap",function(e){
					var etar = e.currentTarget;
					var atar = $(etar).find("a");
					var str = atar[0].innerText;
					if(str == "<"){
						if(opt.pagination.page != 1){
							opt.pagination.page = opt.pagination.page - 1;
							that.query(null,target);
						} 
					
					} else if(str == ">"){
						if(opt.pagination.page != opt.pagination.ableCount){
							opt.pagination.page = opt.pagination.page + 1;
							that.query(null,target);
						} 
					}
					else if(str == "<<"){
							opt.pagination.page = 1;
							opt.pagination.group = 1;
							that.query(null,target);
						 
					}
					else if(str == ">>"){
						    var ableCount = opt.pagination.ableCount;
						    var buttonCount = opt.pagination.dataButtonCount;
							opt.pagination.page = opt.pagination.total;
							opt.pagination.group = buttonCount / ableCount;
							that.query(null,target);
						
					}
					else{
						if(opt.pagination.page != parseInt(str)){
							opt.pagination.page = parseInt(str);
							that.query(null,target);
						}
						
					}
					
				});
			},
			_buildTemplateHtml : function(data){
				var that = this;
				var templateStr = that.option.template;

				var reg1 = /#:[^#]+#/g;
				// var columns = reg1.exec(templateStr);
				var columns = templateStr.match(reg1);
				$.each(columns,function(i,item){
					var dataItemName = item.substring(2,item.length-1);
					if(!data[dataItemName]){
						dataItemName = dataItemName.toLowerCase();
					}

					templateStr = templateStr.replace(item,data[dataItemName] || "");	
					
				})
				return templateStr;
			},
			_buildBottomTemplateHtml : function(data){
				var that = this;
				var templateStr = that.option.bottomTemplate;

				var reg1 = /#:[^#]+#/g;
				// var columns = reg1.exec(templateStr);
				var columns = templateStr.match(reg1);
				$.each(columns,function(i,item){
					var dataItemName = item.substring(2,item.length-1);
					if(!data[dataItemName]){
						dataItemName = dataItemName.toLowerCase();
					}

					templateStr = templateStr.replace(item,data[dataItemName] || "");	
					
				})
				return templateStr;
			},
			_buildModuleHtml : function(data,target){
				var that = target.data("medialist"),
				    opt = that.option,
				    ar = [],
				    title,
				    imgUrl,
				    content,
				    param = {},
				    moduleSourceField = JSON.parse(opt.mediaSource),
				    field = 0;
				$.each(moduleSourceField,function(i,field){
					if(field.columnType == "imgUrl"){
						imgUrl = field.ColumnName;
					} else if(field.columnType == "title"){
						title = field.ColumnName;
					} else if(field.columnType == "content"){
						content = field.ColumnName;
					}
				});
				$.each(data,function(i,item){
					
					if(opt.mediaModule == "top"){
						ar.push("<li class=\"mui-table-view-cell mui-media\" field=\""+field+"\">");
				    	ar.push("<a href=\"javascript:;\">");
				    	
				    	var template = that._buildTemplateHtml(item);
				    
				    	
				    	template = template.replace("<p>","");
				    	template = template.replace("</p>","");
				    	ar.push(template);
												
						ar.push("<div class=\"mui-media-body\">");
				    	ar.push(item[title]);
						ar.push("<p class='mui-ellipsis'>"+ item[content] +"</p>");	
						ar.push("</div></a></li>");	
					} else if(opt.mediaModule == "bot"){
						ar.push("<li class=\"mui-table-view-cell mui-media\" field=\""+field+"\">");
				    	ar.push("<a href=\"javascript:;\">");
				    												
						ar.push("<div class=\"mui-media-body\">");
				    	ar.push(item[title]);
						ar.push("<p class='mui-ellipsis'>"+ item[content] +"</p>");	
						
				    	var template = that._buildTemplateHtml(item);
				    	    	
				    	template = template.replace("<p>","");
				    	template = template.replace("</p>","");
				    	ar.push(template);
						
						ar.push("</div></a></li>");	
					} else if(opt.mediaModule == "left"){
						ar.push("<li class=\"mui-table-view-cell mui-media\" field=\""+field+"\">");
				    	ar.push("<a href=\"javascript:;\">");
				    	
				    	var template = that._buildTemplateHtml(item);
				    
				    	
				    	template = template.replace("<p>","");
				    	template = template.replace("</p>","");
				    	template = $(template).addClass("mui-media-object mui-pull-left")[0].outerHTML;
						ar.push(template);
												
						ar.push("<div class=\"mui-media-body\">");
				    	ar.push(item[title]);
						ar.push("<p class='mui-ellipsis'>"+ item[content] +"</p>");	
						ar.push("</div></a></li>");	
					} else if(opt.mediaModule == "right"){
						ar.push("<li class=\"mui-table-view-cell mui-media\" field=\""+field+"\">");
				    	ar.push("<a href=\"javascript:;\">");
				    	
				    	var template = that._buildTemplateHtml(item);
				    
				    	
				    	template = template.replace("<p>","");
				    	template = template.replace("</p>","");
				    	template = $(template).addClass("mui-media-object mui-pull-right")[0].outerHTML;
						ar.push(template);
												
						ar.push("<div class=\"mui-media-body\">");
				    	ar.push(item[title]);
						ar.push("<p class='mui-ellipsis'>"+ item[content] +"</p>");	
						ar.push("</div></a></li>");	
					} else if(opt.mediaModule == "back"){
						ar.push("<li class=\"mui-table-view-cell mui-media\" field=\""+field+"\">");
				    	
				    	
						
						var url = "";
						if(item[imgUrl].indexOf("/") != -1){
							url = ""+ contextPath + item[imgUrl] +""; 
						} else{
							var thumbPath = contextPath +"/mx/form/imageUpload?method=downloadById&id=" + item[imgUrl];			
							url = thumbPath; 
						}
						var template = that._buildTemplateHtml(item);
						ar.push("<div style=\"width:100%;height:80%;background-image:url("+url+");background-size:100% 100%;-moz-background-size:100% 100%;opacity:0.8\">");		    	
				    	ar.push(template);
				    	ar.push("</div>");
				    	var bottemplate = that._buildBottomTemplateHtml(item);
				    	ar.push("<div class=\"mui-media-body\" style=\"height:20%;\">");
				    	ar.push(bottemplate);
						ar.push("</div>");
						
						ar.push("</li>");
					}
					field = field + 1;
				});
				return ar;
				
			},
			_buildTapListenr : function(target){
			      var that = target.data("medialist"),
			          opt = that.option;
			      target.find("li.mui-table-view-cell").on('tap',function(e){
			    	  var data = opt.data,
			    	      etar = e.currentTarget,
			    	      field = $(etar).attr("field"),
			    	      item = data[field];
			    	  opt.events.tap(item);
			      })
			}
			
	};
	//格式化日期工具
	
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

