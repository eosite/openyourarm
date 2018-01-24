! function($) {
	var plugin = 'levellistext';

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
			onSelectedEndLevel : function(index,row){}
		}
	};

	$.fn[plugin].methods = {
		rowClass : 'levelListRow',
		rowTextClass : 'levelListText',
		headCellClass : 'headCell',
		headTextClass : 'headText',
		rowIndexName : 'row-index',
		_allData : [],
		resize : function(){
			var that =this;
			that._resizeHeader();
			that.$content.height(that.$target.height() -  (that.$tool?that.$tool.outerHeight(true):0));
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

			if(that.options.styleRule && typeof that.options.styleRule == 'string'){
				that.styleRule = JSON.parse(that.options.styleRule);
			}else{
				that.styleRule = that.options.styleRule;
			}

			//设置加载面板
			var $loading = $("<div class='levellistLoading'></div>");
			$loading.append('<div class="shade"></div>');
			var $loading_img = $('<div class="levellist-loadding"></div>');
			$loading.append($loading_img);
			$loading_img.append('<div class="levellist-file-upload-spinner"><div class="levellist-file-upload-spinner-inner"></div></div>');
			$loading_img.append('<div class="levellist-file-hover-content-upload-message">Loading…</div>');
			that.$target.append($loading);
			that.$loading = $loading;

			//设置宽高
			if(that.options.width){
				$target.css("width",that.options.width);
			}
			if(that.options.height){
				$target.css("height",that.options.height);
			}
			$target.addClass("levellist");
			$target.addClass("mui-card");

			var $tool = $("<div></div>");
			that.$tool = $tool;
			$tool.addClass("mui-card-header");
			that.$target.append($tool);

			var $content = $("<div></div>");
			$content.css({width:"100%",height:"100%",overflow:"auto"});
			that.$content = $content;
			$content.addClass("mui-card-content");
			
			that.$target.append($content);

			that.resize();

			//记录原始请求参数
			that.options.readSub = $.extend(true,{},that.options.read);

			if(options.data){
				that._ajaxSuccess(options.data);
			}else{
				that.query();
			}
			that._buildEvent();
		},
		_buildEvent : function(){
			var that = this;

			$(this.target).resize(function() {
				that.resize.call(that);
			});
			$(this.target).find(".mui-card-header").resize(function() {
				that.resize.call(that);
			});
			
			that.$target.on("click","."+that.rowTextClass,function(event){
				//点击行事件
				var $div = $(this);
				var rowIndex = $div.attr(that.rowIndexName);
				var item = that.getDataByRowIndex(parseInt(rowIndex)-1);

				var $li = $(this).closest("."+that.rowClass);
				if(item._isEndLeval){
					//作为最后一级，可以触发最后一级选中事件
					//选中
					$("."+that.rowClass+".selected").removeClass("selected");
					$li.addClass("selected");
					if($.isFunction(that.options.events.onSelectedEndLevel)){
						that.options.events.onSelectedEndLevel();
					}
				}else{
					that.query({
						id : item[that.options.keys.indexKey],
						parentId : item[that.options.keys.parentkey],
					},item,function(ret){
						if(!ret.data || ret.data.length == 0){
							//作为最后一级时，进行选中和设置为最后一级
							item._isEndLeval = true;
							//选中
							$("."+that.rowClass+".selected").removeClass("selected");
							$li.addClass("selected");

							if($.isFunction(that.options.events.onSelectedEndLevel)){
								that.options.events.onSelectedEndLevel();
							}
						}
					})
				}
				
			})

			that.$target.on("click","."+that.headCellClass,function(event){
				//切换面板
				var $div = $(this);
				var item = $div.data("data");
				if(item){
					that.query({
						id : item[that.options.keys.indexKey],
						parentId : item[that.options.keys.parentkey],
					},item,function(data){
						//删除对应的选卡
						$div.nextAll().remove();
						that._resizeHeader();
						// $div.remove();
					})
				}else{
					if($div.hasClass("moreHeader")){
						var $headCell = that.$tool.find(".headCell:hidden");
						if($headCell[0]){
							$($headCell[$headCell.length-1]).trigger("click");
						}
						
					}else{
						that.query(null,null,function(data){
							//清空所有数据
							$div.nextAll().remove();
						});	
					}
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
		query:function(data,parentData,callback){
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
		/**
		 * [_ajaxSuccess description]
		 * @param  {[type]} ret        [description]
		 * @param  {[type]} $parentDom [父节点信息]
		 * @return {[type]}            [description]
		 */
		_ajaxSuccess:function(ret,parentData){
			var that = this;
			if(ret.data && ret.data.length > 0){
				that._allData.length = 0;
				//初始化节点数据
				that._buildHeader(parentData);
				that._loadData(ret.data);	
			}
		},
		//加载数据
		_loadData : function(datas){
			var that = this;
			var $content = that.$content;
			$content.empty();
			var $ul = $('<ul class="mui-table-view"></ul>');
			$content.append($ul);
			$.each(datas,function(i,item){
				that._allData.push(item);
				$ul.append(that._buildRow(item,that._allData.length));
			})

			var $activeSwitch = that.$target.find(".mui-switch.mui-active");
			var width = $activeSwitch.width() / 2;
			$activeSwitch.find(".mui-switch-handle").css({
				"-webkit-transform": 'translate('+width + 'px,0)',
				"transform": 'translate('+width+'px,0)'
			})
							

			mui("#"+that.target.id + ' .mui-switch')['switch']()
		},
		_resizeHeader : function(){
			var that = this;
			//判断标题栏的宽度，是否内容超过标题栏宽度，超过是，隐藏前面几个。并显示...
			
			var $headCell = that.$tool.find(".headCell");
			var widthSum = 0;
			// if(that.$moreHeader)
			// 	that.$moreHeader.hide();
			$.each($headCell,function(i,item){
				if(!$(item).is(":hidden"))
					widthSum += $(item).outerWidth(true);
			})
			if(widthSum > that.$tool.width()){
				//保留最后面几个
				var flag = true;
				for(var i =  0 ; i < $headCell.length; i++){
					var $item = $($headCell[i]);
					if($item.hasClass("moreHeader")){
						continue;
					}
					if(!$item.is(":hidden")){
						if(widthSum > that.$tool.width() - $($headCell[0]).outerWidth()){
							widthSum -=  $item.outerWidth(true);	
							$item.hide();
						}else{
							flag = false;
						}
					}else if(widthSum + $item.outerWidth(true) <= that.$tool.width() - $($headCell[0]).outerWidth()){
						$item.show();
					}
				}
				if(that.$moreHeader)
					that.$moreHeader.show();
			}else{
				//从后面先前显示
				var flag = true;
				for(var i =  $headCell.length ; i > 0 ; i--){
					var $item = $($headCell[i]);
					if($item.is(":hidden")){
						if(!flag){
							break;
						}
						if(widthSum + $item.outerWidth(true) <= that.$tool.width()){
							widthSum +=  $item.outerWidth(true);	
							$item.show();
						}else{
							flag = false;
						}
					}
				}
				if(flag){
					that.$moreHeader.hide();
				}
			}
		},
		_buildHeader : function(item){
			var that = this;
			var $tool = that.$tool;
			var $div = $("<div></div>");
			//增加class
			$div.addClass(that.headCellClass);
			var $span = $("<span ></span>");
			//增加class
			$span.addClass(that.headTextClass);
			$div.append("&nbsp;>&nbsp;");
			$div.append($span);
			if(item){
				$div.attr(that.rowIndexName,item[that.options.keys.indexKey]);
				$span.text(item[that.options.keys.nameKey]);
				$div.data("data",item);
			}else{
				$tool.empty();
				$div.addClass("active");
				$span.text('全部');

				var $moreDiv = $("<div></div>");
				$moreDiv.addClass(that.headCellClass);
				$moreDiv.addClass("moreHeader");
				$moreDiv.text('....');
				that.$moreHeader = $moreDiv;
				$moreDiv.hide();
				$tool.append($moreDiv);
			}
			$tool.append($div);
			that._resizeHeader();
		},
		//生成行信息
		_buildRow : function(item,rowIndex){
			var that = this;
			var $li = $('<li class="mui-table-view-cell"></li>');
			//增加class
			$li.addClass(that.rowClass);
			$li.attr(that.rowIndexName,rowIndex);

			//增加文本内容
			var $div = $('<div></div>');
			$div.addClass(that.rowTextClass);
			$div.attr(that.rowIndexName,rowIndex);
			$div.text(item[that.options.keys.nameKey]);
			$li.append($div);

			//开关按钮设置
			$li.append(that._buildSwitch(item));

			return $li;
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
		//获取表头数据
		getHeaderData : function(){
			var that = this;
			var $div = that.$target.find("."+that.headCellClass);

			var datas = [];
			$.each($div,function(i,item){
				var data = $(item).data("data");
				if(data){
					datas.push(data);
				}
			})
			return datas;
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);