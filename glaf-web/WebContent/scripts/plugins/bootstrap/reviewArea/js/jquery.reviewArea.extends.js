(function($, window, document, undefined) {
	var plugin = "reviewArea", optionKey = plugin + ".options",
	
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
				this.data("reviewArea", new Plugin(this, params));
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
		event : {
			reply : function(e){
				
			}
		},
		pagination: {
			littlePage : true,	//分页是否为最小
			buttonCount:3,	//按钮数量
			paging: true,	//是否显示分页,显示分页取消滑动效果
			page: 1,	//当前页数
			pageSize: 10,	//每页个数
			// pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
			serverPaging: false,	//服务器分页
			previousNext: "true", //是否使能分页翻页按钮
			numeric: "true", //是否使能数字页码按钮
			input: "false", //是否使能输入框翻页
			first: '首页', //首页按钮提示文本
			last: '末页', //末页按钮提示文本
			previous: '上一页', //上一页显示文本
			next: '下一页', //下一页显示文本
			refresh: 'false',
			showPageList:false,
			pagePanelState: 'onehide',	//分页栏占位隐藏
		}
	};

	$.fn[plugin].methods = {
		rowIndex : 'row-index',
		_alldata : [],	//全部数据
		_init : function(e){
			var that = this,
			    $target = $(that.target);
			that.options = that.option;

			//自动展开
			that.options.expandauto == 'true' ? (that.expandauto = true) : (that.expandauto = false);
			//滚动加载
			that.options.scrollload == 'true' ? (that.scrollload = true) : (that.scrollload = false);

			var $contentPanel = $("<div class='contentPanel'>");
			$target.append($contentPanel);
			that.$contentPanel = $contentPanel;
			//一层内容
			var $commentUl = $("<ul class='commentUl'>");	//子内容
			that.$contentPanel.append($commentUl);	
			that.$commentUl = $commentUl;

			
			if(that.scrollload){
				$contentPanel.css({"height":that.options.height || "100%","overflow-y":"scroll"});	
				that._addScrollEvent();
			}
			
			that.query();
			that._bindEvent();

			// that.__resultSet($target,$opt);
			// that.__loadData(that);
		},
		_addScrollEvent : function(){
			var that = this;
			that.$contentPanel.scroll(function(){
				if(that.isloadding){
					//数据加载中
					return false;
				}

				var divHeight = $(this).height();
			    var nScrollHeight = $(this)[0].scrollHeight;
			    var nScrollTop = $(this)[0].scrollTop;

			    if(nScrollTop + divHeight >= nScrollHeight) {
			    	that.isloadding = true;
			    	that.options.pagination.page++;
					that.query();
			    }
			})
		},
		_bindEvent : function(){
			var that = this;
			var $target = $(that.target);

			$target.on("click",".commentDiv>.content",function(event){
				var $clickObj = $(event.target);
				
				if($clickObj.hasClass("fadeoutBtn")){
					$clickObj.hide();
				}else {
					var $k = $clickObj.closest(".fadeoutBtn");
					if($k[0]){
						$k.hide();
					}
				}

				$(".commentDiv>.content.selected").removeClass("selected");
				$(this).addClass("selected");
			})
		},
		getAllData : function(){
			var that = this;
			return that._alldata || [];
			// return $.extend(true,{},that._alldata);
		},
		getClickRowData : function(){
			var that = this;
			var $selectedRow = $(".commentDiv>.content.selected");
			var index = $selectedRow.attr(that.rowIndex);

			return that._alldata[index-1];
		},
		query:function(data,$parentDom,isAuto){
			var that = this;
			var ar = that.option.read;
			var $parentDom = $parentDom;
			if($parentDom){
				if(!data || !data.id){
					return;
				}
				$parentDom.attr("expanded","true");
				var ar = that.option.readSub;
			}else{
			}
			ar.success = function(ret) {
				that._ajaxSuccess(ret, $parentDom,isAuto);
			};
			//保存查询参数
			ar._query_params_ = data;
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params),data?data.id:null);
		},
		getParams: function(params) {
			// return params || {};
			var that = this,
				data = that.options.read.d_data__ = (that.options.read.__data__ ? $.extend({}, that.options.read.__data__) : undefined); //取动态参数
			var ret = $.extend({}, data || this.options.read.data);

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
			if (this.options.pagination.paging) {
				$.extend(data, {
					page: this.options.pagination.page,
					pageSize: this.options.pagination.pageSize
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
		_expandNode : function(item,$subCommentUl,isAuto){
			var that = this;
			var item = item;
			if(!item || !$subCommentUl || !$subCommentUl[0] || $subCommentUl.attr("expanded") == 'true'){
				return;
			}

			//查询其子节点
			var params = {};
			params["parent_id"] = item[that.options.keys.idKey];
			params[that.options.keys.parentKey] = item[that.options.keys.idKey];
			that.query({
				id : item[that.options.keys.idKey],
				parentId : item[that.options.keys.idKey],
				params : JSON.stringify(params)
			},$subCommentUl,isAuto)
		},
		expandNodeByParam : function(key,value){
			var that = this;
			var key = key || "id";
			if(!value){
				return;
			}
			var rows = that.getAllData();
			var pas = value.split(",");
			if (rows && rows.length) {
				$.each(rows, function(i, row) {
					//遍历值进行对比
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							//找到对应的对象
							var $subCommentUl = $('.commentDiv['+that.rowIndex+'="'+(i+1)+'"]>.subCommentUl');
							if($subCommentUl && $subCommentUl[0]){
								that._expandNode(row,$subCommentUl);
							}
						}
					}
				});
			}
		},
		/**
		 * [_ajaxSuccess description]
		 * @param  {[type]} ret        [description]
		 * @param  {[type]} $parentDom [父节点信息]
		 * @return {[type]}            [description]
		 */
		_ajaxSuccess:function(ret, $parentDom,isAuto){
			var that = this;
			that.datas = ret.data;
			that.total = ret.total;

			var $parentDom = $parentDom;
			if(!$parentDom){
				if(!that.scrollload){
					//使用分页栏时，清空数据
					that._alldata.length = 0;
					that.$commentUl.empty();	//一层内容清空
				}

				$parentDom = that.$commentUl;

				if(!that.scrollload){
					that.renderPagination();//初始化页面	
				}
				that.isloadding = false;
			}
			var isAuto = isAuto;
			$.each(ret.data,function(i,item){
				var $li = $("<li class='commentDiv'>");
				var $content = $("<div class='content'>");	//内容
				var $subCommentUl = $("<ul class='subCommentUl'>");	//子内容

				//加载内容
				$content.append(that._buildTemplateContent(item));

				$li.append($content);
				$li.append($subCommentUl);
				$parentDom.append($li);
				//添加记录
				that._alldata.push(item);
				$li.attr(that.rowIndex,that._alldata.length);
				$content.attr(that.rowIndex,that._alldata.length);

				if($parentDom.hasClass("subCommentUl")){
					return true;
				}
				if(that.expandauto){
					that._expandNode(item,$subCommentUl,true);	
				}
			})

			if(that.scrollload){
				//滚动加载时，判断高度是否小于最大高度，小于时，自动加载下一页
				if(that.$commentUl.height() < that.$contentPanel.height()){
					//高度小于最大高度时，自动加载下一页
					var pageMax = Math.ceil(that.total / that.options.pagination.pageSize);
					if(pageMax > that.options.pagination.page){
						that.options.pagination.page++;
						that.query();
					}
				}
			}
			
		},
		_buildTemplateContent : function(data){
			var that = this;
			var templateStr = that.options.template;

			var customdefinedArray = that.options.customdefined;
			if(customdefinedArray && customdefinedArray.length > 0){
				customdefined = customdefinedArray[0];
				if(customdefined.value && customdefined.value.length > 0){
					$.each(customdefined.value,function(i,item){
						var expdata = JSON.parse(item.expdata);
						var expression = expdata.expActVal;
						// var reg1 = /~F{[\w|\.]+}/g;

						var reg1 = /#:[^#]+#/g;

						var varVal = expdata.varVal;
						$.each(varVal,function(k,column){
							expression = expression.replace(column.value.code,"#:"+column.value.columnName+"#");
							
						})
						//column.value.code

						var columns = expression.match(reg1);
						$.each(columns,function(k,column){
							var dataItemName = column.substring(2,column.length-1);
							// dataItemName = dataItemName.split("_0_")[1];

							if(!data[dataItemName]){
								dataItemName = dataItemName.toLowerCase();
							}
							expression = expression.replace(column,data[dataItemName] || '');
						})

						if(eval(expression)){
							templateStr = item.htmlExpression;
							templateStr = that._renderContentbyTemplate(templateStr,data);
							return false;
						}
					})
				}else{
					if(templateStr != null)
						templateStr = that._renderContentbyTemplate(templateStr,data);	
				}
			}else{
				if(templateStr != null)
					templateStr = that._renderContentbyTemplate(templateStr,data);
			}
			
			templateStr = pubsub.htmlUnescape4Html(templateStr);
			return templateStr;
		},
		_renderContentbyTemplate : function(templateStr,data){
			var that = this;
			var reg1 = /#:[^#]+#/g;
			// var columns = reg1.exec(templateStr);
			var columns = templateStr.match(reg1);
			if(columns){
				$.each(columns,function(i,item){
					var dataItemName = item.substring(2,item.length-1);
					if(!data[dataItemName]){
						dataItemName = dataItemName.toLowerCase();
					}
					templateStr = templateStr.replace(item,data[dataItemName] || "");	
				})
			}
			else if(data['content'] != null && data['content'] != undefined){
				
				templateStr = templateStr.replace('Locations',data['content']);
				//templateStr = data['content'];
			}
			return templateStr;
		},
		__loadData : function($obj){
			$($obj.option.read);
		},
		__resultSet : function($target,$opt){
			var that = this;
			$opt.read.success = function(ret){
				var agoComment = [];
				var phonelistDataSource = eval("("+$opt.phoneDataSource+")");
				$.each(ret[0].data,function(i,data){
					
					var id = phonelistDataSource[0].id != undefined ? phonelistDataSource[0].id[0].en : undefined;
					var title = phonelistDataSource[0].title != undefined ? phonelistDataSource[0].title.en : undefined;
					var datetime = phonelistDataSource[0].datetime != undefined ? phonelistDataSource[0].datetime[0].en : undefined;
					var parentid = phonelistDataSource[0].parentid != undefined ? phonelistDataSource[0].parentid[0].en : undefined;
					var value = phonelistDataSource[0].value != undefined ? phonelistDataSource[0].value[0].en : undefined;
					var imgUrl = phonelistDataSource[0].imgUrl != undefined ? phonelistDataSource[0].imgUrl[0].en : undefined;
					var datasetId = phonelistDataSource[0].datasetId != undefined ? phonelistDataSource[0].datasetId[0].en : undefined;
					var config = {
							  "componentID" : $target.attr("id"), 
							  "id" : data[id],
							  "userName" : data[title],
							  "time" : data[datetime],
							  "sortID" : data[parentid],
							  "content" : data[value],
							  "flag" : $opt.imageFlag
				    }
					if(datasetId != undefined){
						config['datasetId'] = data[datasetId];
					}
					else{
						if(imgUrl != undefined){
							config['imgUrl'] = data[imgUrl];
						}
					}
					agoComment.push(config);
				});
				$target.find("#articleComment").css("width",$opt.width);
				$target.find("#articleComment").css("height",$opt.height);
				$target.find("#articleComment").zyComment({	
					"agoComment":agoComment,
					
				});
				$target.find(".reply").on("click",function(e){
					$opt.event.reply(e);
				});
			}
		},

		renderPagination: function() {
			var that = this;
			var $target = $(this.target);
			var $id = $target.attr("paging");
			!$id && ($target.attr("paging", ($id = 'pageId-' + new Date().getTime())));
			var $page = $("#" + $id),
				pageFn = $page.data("pagination");

			if(that.total <= 0){
				$page.hide();
				return;
			}
			if (!$page[0]) {

				var page = this.options.pagination;
				var $page = $("<div>", {
					id: $id,
					class: 'page hmtd-xs',
					'style': 'text-align:right;'
				})
				$page.pagination($.extend(true, {}, page, {
					total: this.total,
					totalPage: this.pageMax,
					showPageList:false,
					events: {
						onSelectPage: function(pageNumber, pageSize) {
							if(that.options.pagination.serverPaging){
								//服务器分页
								that.options.pagination.page = pageNumber;
								that.query(that.options.read._query_params_ || {});
							}else{
								that.options.pagination.page = pageNumber;
								that._ajaxSuccess(null,that.options.pagination.page);
							}
						},
					}
				}));

				$(this.target).append($page);
			}else{
				pageFn.refresh({
					total: this.total,
					totalPage: this.pageMax,
					pageNumber: this.options.pagination.page
				});
			}
			$page.css("background-color","transparent");
			if(that.pageMax == 1 && that.options.pagination.pagePanelState == 'onehide'){
				$page.css("opacity",'0');
			}
		}
	
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

