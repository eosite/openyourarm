(function($, window, document, undefined) {
	var plugin = "indexedlist", optionKey = plugin + ".options",
	
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
				this.data("indexedlist", new Plugin(this, params));
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
				    $opt = that.option,
				    $target = that.target;
				$target.find(".mui-indexed-list-bar").empty();
				$target.find(".mui-table-view").empty();
                that.query(null,$target);
				
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
			_loadDefinedC : function($target,obj){
				var definedColArray = eval("(" + obj + ")"),
				    element = [],
				    el = "";
				$.each(definedColArray,function(i,colArray){
					el = "<a>" + colArray.indexColumn + "</a>";
					element.push(el);
				});
				$target.find(".mui-indexed-list-bar").append(element);
			},
			_ajaxSuccess : function(datas,$target){
				this._loadDefinedC($target,this.option.definedColumn);
				this._renderObj(datas,$target);
				this._muiInitS($target);
				
			},
			_buildTemplateContent : function(data){
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
			
			_renderObj : function(datas,$target){
				var that = this,
				    dexColumn = eval("("+ this.option.indexColumn +")"),
				    object = eval("(" + this.option.definedColumn + ")"),
				    arr = [],
				    reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
				
				$.each(object,function(i,obj){
					arr.push("<li data-group=\""+ obj.indexColumn +"\" class=\"mui-table-view-divider mui-indexed-list-group\">"+ obj.indexColumn +"</li>");
					$.each(datas.data,function(j,ret){
						if(reg.test(obj.indexColumn)){
							obj.indexColumn == ret[dexColumn[0].columnName].split("")[0] ? arr.push("<li  class=\"mui-table-view-cell mui-indexed-list-item\">"+ ret[dexColumn[0].columnName] +"</li>") : undefined;
						}
						else{	
							if(reg.test(ret[dexColumn[0].columnName])){		
								ConvertPinyin(ret[dexColumn[0].columnName]).split("")[0].toLowerCase() == obj.indexColumn.toLowerCase() ? 
										arr.push("<li  class=\"mui-table-view-cell mui-indexed-list-item\">"+ that._buildTemplateContent(ret)+"</li>") : undefined;
							}
							else{
								obj.indexColumn.toLowerCase() == ret[dexColumn[0].columnName].split("")[0].toLowerCase() ? arr.push("<li  class=\"mui-table-view-cell mui-indexed-list-item\">"+  that._buildTemplateContent(ret) +"</li>") : undefined;
							}
						}
					});
				});
				$target.find(".mui-table-view").append(arr);
				
			},
			_muiInitS : function($target){
				mui.init();
				mui.ready(function() {
					var header = document.querySelector('header.mui-bar');
					var list = $target.find('#list')[0];
					//calc hieght
					list.style.height = document.body.offsetHeight + 'px';
					//create
					window.indexedList = new mui.IndexedList(list);
				});
			},
			search: function(keyword) {
				var self = this;
				keyword = (keyword || '').toLowerCase();
				var selectorBuffer = [];
				var groupIndex = -1;
				var itemCount = 0;
				var liArray = self.el.liArray;
				var itemTotal = liArray.length;
				self.hiddenGroups = [];
				var checkGroup = function(currentIndex, last) {
					if (itemCount >= currentIndex - groupIndex - (last ? 0 : 1)) {
						selectorBuffer.push(classSelector('indexed-list-inner li') + ':nth-child(' + (groupIndex + 1) + ')');
						self.hiddenGroups.push(liArray[groupIndex]);
					};
					groupIndex = currentIndex;
					itemCount = 0;
				}
				liArray.forEach(function(item) {
					var currentIndex = liArray.indexOf(item);
					if (item.classList.contains($.className('indexed-list-group'))) {
						checkGroup(currentIndex, false);
					} else {
						var text = (item.innerText || '').toLowerCase();
						var value = (item.getAttribute('data-value') || '').toLowerCase();
						var tags = (item.getAttribute('data-tags') || '').toLowerCase();
						if (keyword && text.indexOf(keyword) < 0 &&
							value.indexOf(keyword) < 0 &&
							tags.indexOf(keyword) < 0) {
							selectorBuffer.push(classSelector('indexed-list-inner li') + ':nth-child(' + (currentIndex + 1) + ')');
							itemCount++;
						}
						if (currentIndex >= itemTotal - 1) {
							checkGroup(currentIndex, true);
						}
					}
				});
				if (selectorBuffer.length >= itemTotal) {
					self.el.inner.classList.add('empty');
				} else if (selectorBuffer.length > 0) {
					self.el.inner.classList.remove('empty');
					self.el.styleForSearch.innerText = selectorBuffer.join(', ') + "{display:none;}";
				} else {
					self.el.inner.classList.remove('empty');
					self.el.styleForSearch.innerText = "";
				}
			},
			
	};
	//格式化日期工具
	
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

