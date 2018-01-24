! function($) {
	var plugin = 'definedPanelExt';

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
		if(columns&&columns.length>0){
			state.options.columns = eval('('+columns+')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults={
		datas : "",
		titleHtmlTemplate : '<div class="caption" style="font-size: 25px;border-bottom: 1px solid grey;margin-bottom: 6px;"><i class="fa fa-gift"></i><span class="frame-variable portlet_1501485388650" frame-variable="fv1">标题 </span></div>',
		contentHtmlTemplate : '<img src="/glaf/mx/form/imageUpload?method=download2&amp;databaseId=&amp;mode=ATTA&amp;id=2a393f60d5e34094b920780a8e68cdba&amp;rp=476" width="100" height="100" border="0" vspace="0" title="" alt="" style="width: 100px; height: 100px;">',
		_itemIndex : "item-index",	//为定位在数据中的位置的属性名称
		pagination : {
            page: 1,
            pageSize:5,
            paging: true,
        },
        events:{
        	onClickEvent : function(){

        	}
        }
	}
	$.fn[plugin].defaults.ajax = {
		read: {
			url: '',
			type: 'POST',
			dataType: 'JSON',
			data: {},
			contentType: "application/json",
			async: true
		},
		parameterMap: function(options) {
			return options;
		},
	}

	$.fn[plugin].methods = {
		_init: function() {
			var that = this;
			that._initPanel();
			that._load();
			that._initEvents();
		},
		_initEvents: function(){
			var that = this;
			var $target = that.$target;
			var options = that.options;

			//点击事件
			$target.on("click",".definedPanleContent",function(events){
				$(".definedPanleContent.selected").removeClass("selected");
				$(this).addClass("selected");
				if($.isFunction(options.events.onClickEvent)){
					options.events.onClickEvent();
				}
			})
			

		
		},
		/**
		 * 查询
		 * @param  {[type]} data [请求参数]
		 * @return {[type]}      [null]
		 */
		query:function(data){
			var that = this,
			ar = that.options.ajax.read;

			that.loadding = true;	//设置加载状态
			ar.success = function(ret) {
				that._ajaxSuccess(ret, $(that.target));
				that.loadding = false;	//设置加载状态
			};
			//保存查询参数
			ar._query_params_ = data;
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params));
		},
		/**
		 * 将请求参数放入指定的请求体中
		 * @param  {[type]} params [请求参数]
		 * @return {[type]}        [description]
		 */
		getParams: function(params) {
			var that = this,
				data = that.options.ajax.read.d_data__ = (that.options.ajax.read.__data__ ? $.extend({}, that.options.ajax.read.__data__) : undefined); //取动态参数
			var ret = $.extend({}, data || this.options.ajax.read.data);

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
		/**
		 * 使用ajax发送请求
		 * @param  {[type]} ar     [description]
		 * @param  {[type]} params [description]
		 * @return {[type]}        [description]
		 */
		_request: function(ar, params) {
			var that = this;
			if (!ar.__success__) { //第一次ajax 调用
				ar.success = function(ret) {
					that._ajaxSuccess(ret, ar.__target);
				};
				ar.__success__ = true;
				//第一次查询将查询信息保存起来
				ar.__data__ = $.extend(ar.__data__ || {}, ar.data); // 原始参数保存
				ar.d_data__ = $.extend(ar.d_data__ || {}, ar.data); // 动态参数保存
			}
			that._initParams(ar, params);
			$.ajax(ar);
		},
		/**
		 * [将查询参数信息放入保存中]
		 * @param  {[type]} ar     [description]
		 * @param  {[type]} params [description]
		 * @return {[type]}        [description]
		 */
		_initParams: function(ar, params) {
			var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);
			if (this.options.pagination.paging) {
				$.extend(data, {
					page: this.options.pagination.page,
					pageSize: this.options.pagination.pageSize
				});
			}
			//将查询参数信息放入保存中,ajax.save
			if(params && this.options.ajax.save){
				this.options.ajax.save.data.params = JSON.parse(params.params || "{}");
			}
			$.extend(data, {
				sort: this.__sortArray
			});
			ar.data = this._getParameterMap(data);
		},

		_getParameterMap: function(data) {
			return this.options.ajax.parameterMap(data);
		},
		/**
		 * 声明常量
		 * @return {[type]} [description]
		 */
		_initPanel : function(){
			var that = this;
			that.$target = $(that.target);
			var options = that.options;
			that.$definedPanelList = that.$target.find(".definedPanelList");
			if(!that.$definedPanelList[0]){
				var $definedPanelList = $('<div class="definedPanelList"></div>');
				that.$definedPanelList = $definedPanelList;
				that.$target.append($definedPanelList);
			}
			
			var titleDefinedHtmlArray = options.titleHtmlDefined;
			if(options.titleHtmlDefined && typeof options.titleHtmlDefined == 'string'){
				titleDefinedHtmlArray = JSON.parse(options.titleHtmlDefined);
			}
			that.titleDefinedHtmlArray = titleDefinedHtmlArray;

			var contentDefinedHtmlArray = options.contentHtmlDefined;
			if(options.contentHtmlDefined && typeof options.contentHtmlDefined == 'string'){
				contentDefinedHtmlArray = JSON.parse(options.contentHtmlDefined);
			}
			that.contentDefinedHtmlArray = contentDefinedHtmlArray;


			that.sortNumName = "definedPanelNum";	//排序号的属性名
			that.__itemArray = null;	//所有数据
		},
		/**
		 * 数据初始化
		 * @return {[type]} [description]
		 */
		_load : function(){
			var that = this;
			var options = that.options;
			var fixDataSource = options.fixDataSourc
			if(fixDataSource && fixDataSource.length){
				//固定菜单数据源。
				debugger;
			}else if(options.datas){
				//若有静态数据,则以静态数据为主
				that._buildByData(options.datas);
			}else{
				//已查询为主
				that.query();
			}
		},
		/**
		 * 
		 * @param  {[type]} data    [为数据]
		 * @param  {[type]} $target [为目标节点，即在某节点下面添加，null为根节点]
		 * @return {[type]}         [description]
		 */
		_buildByData : function(datas,$parent){
			var that = this;
			var $target = that.$target;

			var $definedPanelList = that.$definedPanelList;

			var sortdatas = that._sortData(datas);
			if(sortdatas && sortdatas.length > 0){
				datas  = sortdatas;
			}
			that.__itemArray = datas;	//赋值所有数据
			$.each(datas,function(i,data){
				var $definedPanelItem = $('<div class="definedPanelItem"></div>');
				$definedPanelList.append($definedPanelItem);
				//生成标题
				var $definedPanelTitle = $('<div class="definedPanelTitle"></div>');
				var $itemTitle = that._buildItemTitle(data);
				$definedPanelTitle.append($itemTitle);
				$definedPanelItem.append($definedPanelTitle);
				//排序序号
				$definedPanelItem.attr(that.sortNumName,i);
				
				//生成内容列表
				var $definedPanelContentList = $('<div class="definedPanelContentList"></div>');
				$definedPanelItem.append($definedPanelContentList);
				$.each(data.children,function(k,item){
					var $definedPanleContent = $('<div class="definedPanleContent"></div>');
					$definedPanelContentList.append($definedPanleContent);
					var $itemContent = that._buildItemContent(item);
					$definedPanleContent.append($itemContent);
					//排序序号
					$definedPanleContent.attr(that.sortNumName,i+","+k);
				})
			})
		},
		_sortData : function(datas){
			var that = this,
				keys = that.options.keys || {},
				children = keys.childrenKey || that._children || "children";
			if(that.options.sysmenu && that.options.sysmenu == 'true'){
				keys = {
					idKey : 'id',
					parentKey : 'parentId'
				}
			}
			return $.transformToTreeFormat(datas, keys.idKey, keys.parentKey, children)
		},
		_ajaxSuccess:function(ret){
			var that = this;
			var options = that.options;
			var datas = ret[options.ajax.schema.data || "data"];
			//若有静态数据,则以静态数据为主
			that._buildByData(datas);
		},
		
		/**
		 * 创建对象标题面板
		 * @param  {[type]} data [description]
		 * @return {[type]}      [description]
		 */
		_buildItemTitle : function(data){
			var that = this;
			var options = that.options;

			var htmlStr = that._buildTemplateContent(that.titleDefinedHtmlArray,options.titleHtmlTemplate,data);

			return $(htmlStr);
		},
		/**
		 * 创建对象内容面板
		 * @param  {[type]} data [description]
		 * @return {[type]}      [description]
		 */
		_buildItemContent : function(data){
			var that = this;
			var options = that.options;

			var htmlStr = that._buildTemplateContent(that.contentDefinedHtmlArray,options.contentHtmlTemplate,data);
			
			return $(htmlStr);
		},
		// _buildTemplateContent : function(htmlTemplate,data){
		// 	var that = this;
		// 	var reg1 = /#:[^#]+#/g;

		// 	var columns = htmlTemplate.match(reg1) || [];
		// 	$.each(columns,function(i,item){
		// 		var dataItemName = item.substring(2,item.length-1);
		// 		if(!data[dataItemName]){
		// 			dataItemName = dataItemName.toLowerCase();
		// 		}

		// 		htmlTemplate = htmlTemplate.replace(item,data[dataItemName] || "");	
				
		// 	})
		// 	return htmlTemplate;
		// },
		_buildTemplateContent : function(customdefinedArray,htmlTemplate,data){
			var that = this;
			var templateStr = htmlTemplate;

			if(customdefinedArray && customdefinedArray.length > 0){
				customdefined = customdefinedArray[0];
				if(customdefined.value){
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
		getAllData : function(){
			var that = this;
			return that.__itemArray;
		},
		/**
		 * 根据排序号获取对象数据
		 * @param  {[type]} sortNum [description]
		 * @return {[type]}         [description]
		 */
		getNodeDataBySortNum : function(sortNum){
			var that = this;
			if(sortNum){
				var itemArray = that.getAllData();
				sortNum = sortNum.split(",")
				if(sortNum.length == 1){
					return itemArray[sortNum[0]];
				}else if(sortNum.length == 2){
					return itemArray[sortNum[0]].children[sortNum[1]];
				}
			}
			return "";
		},
		getSelectRowData : function(data){
			var that = this;
			var indexId = that.$target.find(".definedPanleContent.selected").attr(that.sortNumName);
			return that.getNodeDataBySortNum(indexId);
		}, 
		refresh : function(data){
			var that = this;
			var $element = $(that.target);
			//清空原本的数据
			$element.find(".definedCardExt_list").empty();
			//清空原本的数组
			that.__itemArray = [];
			//初始化页面为第一页
			that.options.pagination.page;
			//重新查询
			that.query();
		},
		linkage : function(data){
			var that = this;
			if(that.loadding){
				return;
			}
			var $element = $(that.target);
			//清空原本的数据
			$element.find(".definedCardExt_list").empty();
			//清空原本的数组
			that.__itemArray = [];
			//初始化页面为第一页
			that.options.pagination.page = 1;
			//重新查询
			that.query(data);
		}
	}
	
	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});

}(jQuery);