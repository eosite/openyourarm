! function($) {
	var plugin = 'foldlistext';

	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options');
		var columns = $(target).data("columns");
		if (columns && columns.length > 0) {
			state.options.columns = eval('(' + columns + ')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		datas : null,
		url:"",
		visible:true,	//显示
		events: {
			onClickRow: function(index, row) {},
			onDblClickRow: function(index, row) {},
			switchChange: function(index, row) {},
			onSelected : function(index,row){}
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
		targetClass : 'foldlist',
		rowClass : 'foldListRow',
		rowTextClass : 'foldListText',
		headCellClass : 'headCell',
		headTextClass : 'headText',
		rowIndexName : 'row-index',
		_allData : [],
		resize : function(){
			var that =this;

			if(that.scrollload){
				//滚动加载时，判断高度是否小于最大高度，小于时，自动加载下一页
				if(that.$content.height() < that.$contentParent.height() && !that.$contentParent.is(":hidden")){
					//高度小于最大高度时，自动加载下一页
					var pageMax = Math.ceil(that.total / that.options.pagination.pageSize);
					if(pageMax > that.options.pagination.page){
						that.options.pagination.page++;
						that.ajaxquery(that.options.read._query_params_ || {});
					}
				}
			}
			// that.$content.height(that.$target.height() -  (that.$tool?that.$tool.outerHeight(true):0));
		},
		_showLoadding : function(){
			var that = this;
			that.$loading.show();
		},
		_hideLoadding : function(){
			var that = this;
			that.$loading.hide();
		},
		_init : function(){

			var that = this;
			var options = that.options;
			var $target = $(that.target);
			that.$target = $target;

			$(this.target).resize(function() {
				that.resize.call(that);
			});

			//是否滚动加载
			that.scrollload = that.options.scrollload;

			if(that.options.styleRule && typeof that.options.styleRule == 'string'){
				that.styleRule = JSON.parse(that.options.styleRule);
			}else{
				that.styleRule = that.options.styleRule;
			}

			//设置加载面板
			var $loading = $("<div class='foldlistLoading'></div>");
			$loading.append('<div class="shade"></div>');
			var $loading_img = $('<div class="foldlist-loadding"></div>');
			$loading.append($loading_img);
			$loading_img.append('<div class="foldlist-file-upload-spinner"><div class="foldlist-file-upload-spinner-inner"></div></div>');
			$loading_img.append('<div class="foldlist-file-hover-content-upload-message">Loading…</div>');
			that.$target.append($loading);
			that.$loading = $loading;

			//设置宽高
			if(that.options.width){
				$target.css("width",that.options.width);
			}
			if(that.options.height){
				$target.css("height",that.options.height);
			}
			$target.addClass(that.targetClass);

			var $tool = $("<div></div>");
			that.$tool = $tool;
			
			that.$target.append($tool);

			var $contentParent = $("<div></div>");
			$contentParent.css({width:"100%",height:"100%",overflow:"auto"});
			$contentParent.attr("id",that.target.id+"_contentParent");
			that.$contentParent = $contentParent;
			
			var $content= $("<div></div>");
			that.$content = $content;
			$contentParent.append($content);
			
			that.$target.append($contentParent);

			that.resize();

			if(that.scrollload){
				$contentParent.css({"height":that.options.height || "100%","overflow-y":"scroll"});	
				that._addScrollEvent();
			}
			

			//记录原始请求参数
			that.options.readSub = $.extend(true,{},that.options.read);

			if(options.data){
				that._ajaxSuccess(options.data);
			}else{
				that.ajaxquery();
			}
			that._buildEvent();
		},
		_addScrollEvent : function(){
			var that = this;
			that.$contentParent.scroll(function(){
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
					that.ajaxquery(that.options.read._query_params_ || {});
			    }
			})
		},
		_buildEvent : function(){
			var that = this;

			

			that.$target.on("click","."+that.rowTextClass,function(event){
				//点击，展开节点
				var $li = $(this).closest("."+that.rowClass);
				$li.toggleClass("actived");
			})
			
			that.$target.on("click","."+that.rowClass,function(event){
				//点击行事件
				var $div = $(this);
				var $li = $(this);
				var rowIndex = $li.attr(that.rowIndexName);
				var item = that.getDataByRowIndex(parseInt(rowIndex)-1);
				//触发选中事件
				//选中
				$("."+that.rowClass+".selected").removeClass("selected");
				$li.addClass("selected");
				if($.isFunction(that.options.events.onSelected)){
					that.options.events.onSelected();
				}
				
			})

			//按钮变化事件
			that.$target.on("toggle",".mui-switch",function(event){
				var $li = $(this).closest("."+that.rowClass);
				$("."+that.rowClass+".selected").removeClass("selected");
				if($li[0]){
					$li.addClass("selected");
					var data = that.getDataByRowIndex(parseInt($li.attr(that.rowIndexName))-1);
					
					if(event.detail.isActive){
						data.switchValue = 1;
						// console.log("你启动了开关");
					}else{
						data.switchValue = 0;
						// console.log("你关闭了开关");  
					}
				}
				if($.isFunction(that.options.events.switchChange)){
					that.options.events.switchChange();
				}
			})
		},
		getSelectedData : function(rowIndex){
			var that = this;
			var $selectRow = that.$target.find("."+that.rowClass+".selected");
			return that.getDataByRowIndex(parseInt($selectRow.attr(that.rowIndexName))-1);
		},
		getAllData : function(rowIndex){
			var that = this;
			return that._allData;
		},
		getDataByRowIndex : function(rowIndex){
			var that = this;
			return that._allData[rowIndex];
		},
		ajaxquery : function(data,parentData,callback){
			var that = this;
			var ar = that.options.read;
			var parentData = parentData;
			if(parentData){
				if(!data || !data.id){
					return;
				}
				var ar = that.options.readSub;
			}else{
				
			}
			that._showLoadding();
			ar.success = function(ret) {
				that._ajaxSuccess(ret, parentData);
				if($.isFunction(callback)){
					callback(ret);
				}
				that._hideLoadding();
			};
			//保存查询参数
			ar._query_params_ = data;
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params),data?data.id:null);
		},
		query:function(data){
			var that = this;
			//重新查询的，清空页数
			that.options.pagination.page = 1;
			that._allData.length = 0;
			that.$content.empty();
			that.ajaxquery(data);
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
		_initParams: function(ar, params,id) {
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
					total: that.total,
					totalPage: that.pageMax,
					showPageList:false,
					events: {
						onSelectPage: function(pageNumber, pageSize) {
							if(that.options.pagination.serverPaging){
								//服务器分页
								that.options.pagination.page = pageNumber;
								that.ajaxquery(that.options.read._query_params_ || {});
							}else{
								// that.options.pagination.page = pageNumber;
								// that._ajaxSuccess(null,that.options.pagination.page);
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
		},
		/**
		 * [_ajaxSuccess description]
		 * @param  {[type]} ret        [description]
		 * @param  {[type]} $parentDom [父节点信息]
		 * @return {[type]}            [description]
		 */
		_ajaxSuccess:function(ret,parentData){
			var that = this;
			that.total = ret.total;
			if(!that.scrollload){
				that.renderPagination();//初始化页面	
			}
			if(!that.scrollload){
				that._allData.length = 0;
				that.$content.empty();
			}
			that._loadData(ret.data);
			that.isloadding = false;

			if(that.scrollload){
				//滚动加载时，判断高度是否小于最大高度，小于时，自动加载下一页
				if(that.$content.height() < that.$contentParent.height() && !that.$contentParent.is(":hidden")){
					//高度小于最大高度时，自动加载下一页
					var pageMax = Math.ceil(that.total / that.options.pagination.pageSize);
					if(pageMax > that.options.pagination.page){
						that.options.pagination.page++;
						that.ajaxquery(that.options.read._query_params_ || {});
					}
				}
			}
		},
		//加载数据
		_loadData : function(datas){
			var that = this;
			var $content = that.$content;
			
			$.each(datas,function(i,item){
				that._allData.push(item);
				$content.append(that._buildRow(item,that._allData.length));
			})

			var $activeSwitch = that.$target.find(".mui-switch.mui-active");
			var width = $activeSwitch.width() / 2;
			$activeSwitch.find(".mui-switch-handle").css({
				"-webkit-transform": 'translate('+width + 'px,0)',
				"transform": 'translate('+width+'px,0)'
			})
				

			mui("#"+that.target.id + ' .mui-switch')['switch']()
		},
		_buildHeader : function(item){
			var that = this;
			
			var that = this;
			var $head = $("<div class='mui-card-header'>");
			//增加文本内容
			var $div = $('<div style="width:100%"></div>');
			$div.addClass(that.rowTextClass);
			$div.text(item[that.options.keys.nameKey]);
			$head.append($div);

			//开关按钮设置
			$head.append(that._buildSwitch(item));
			return $head;
		},
		_buildContent : function(item){
			var that = this;
			var $content = $("<div class='mui-card-content'>");
			//增加文本内容
			var $div = $('<div></div>');
			$div.append(that._buildTemplateContent(item));
			$content.append($div);

			return $content;
		},
		//生成行信息
		_buildRow : function(item,rowIndex){
			var that = this;

			var $card = $('<div class="mui-card">');
			var $head = $("<div class='mui-card-header'>");
			

			$card.append(that._buildHeader(item));
			$card.append(that._buildContent(item));

			//增加class
			$card.addClass(that.rowClass);
			$card.attr(that.rowIndexName,rowIndex);

			return $card;
		},
		//生成开关按钮
		_buildSwitch : function(item){
			var that = this;
			var dataItem = item;
			var $switch = $('<div class="mui-switch"><div class="mui-switch-handle"></div></div>');

			dataItem.switchValue = 0;
			if(that.styleRule.check){
				$.each(that.styleRule.check,function(k,ktem){
					if(eval(ktem.expression.replace(/\\\"/g,"\""))){
						if(ktem.type == 'check'){
							//启用
							$switch.addClass('mui-active');
							dataItem.switchValue = 1;
						}else if(ktem.type == 'disabled'){
							//禁用
							$switch.addClass('disabled');
						}
					}
				})	
			}
			return $switch;
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
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);