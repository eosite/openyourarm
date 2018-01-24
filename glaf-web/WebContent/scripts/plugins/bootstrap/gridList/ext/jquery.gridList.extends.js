//模板列表插件
!(function ($) {

	var plugin = 'gridList';

	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init_();
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
		datas : null,
		url:"",
		ablecheckbox: false,	//是否有选择框
		selectable: 'single',	//行单选，multiple行多选
		disabledCls: "disabledCell",	//禁用单元格的CLASS
		distance: 1,	//间隔
		cols:3,	//每行列数
		rows:3,	//每页行数
		distanceColor:'#ccc',	//间隔颜色
		hoverDistanceColor:'#f00',	//悬浮间隔颜色
		slideType: 'leftRight',	//滑动类型,upDown(上下),leftRight(左右)
		touchSensitivity : 0.2,	//触控敏感度，用于判断是否换到下一页
		isbank: true,	//是否显示空白
		width:333,
		height:333,
		ajax:null,
		adapt: false,	//自适应
		delayTime: 700,	//延迟时间
		isOpenImg: true,
		visible:true,	//显示
		template : '<a href="javascript:;" class="icon-btn"><i class="fa fa-map-marker"></i><div> Locations </div></a>',
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
		},
		callback:{
			OnContentClick: function(data){},	//内容点击事件,data为内容的信息
			OnContentDblClick: function(data){},	//内容的双击事件,data为内容信息
			OnLoadSuccess: function(data){},	//内容加载完事件
		}
	};

	$.fn[plugin].defaults.ajax = {
		read: {
			url: '',
			type: 'POST',
			dataType: 'JSON',
			data: {},
			contentType: "application/json",
			success: function(r) {
				return {
					total: r.total,
					rows: r.rows
				};
			},
			async: true
		},
		parameterMap: function(options) {
			return options;
		},
		schema: {
			total: 'total',
			data: 'data',
			errors: 'error',
			model: {
				id: "",
				fields: {}
			}
		}
	}
   
    var methods = $.fn[plugin].methods={
		//设置单元格禁用
		disableCell : function(cellid){
			var that = this;
			var gridlistBox = this.getGridlistBox();
			var datas = this.getData();
			var $gridlistLi =  gridlistBox.find('>.gridList_ul>li>.demo_content[' + that._cellIndex_ + "=" + datas[cellid][that._cellIndex_] + ']');
			$gridlistLi.removeClass("selected");
			$gridlistLi.parent().find(".gridlist_checkbox_div input").iCheck("uncheck");
			$gridlistLi.addClass(this.options.disabledCls);
		},
		blukDisableCell : function(key,cellid){
			var that = this;
			var gridlistBox = this.getGridlistBox();
			var datas = this.getData();

			var key = key || 'id';
			var lowKey = key.toLowerCase();
			var cellidArray = cellid.split(",");

			$.each(cellidArray,function(i,item){
				for (var j = 0; j < datas.length; j++) {
					if (datas[j][key] && datas[j][key].indexOf(item) > 0) {
						var $gridlistLi =  gridlistBox.find('>.gridList_ul>li>.demo_content[' + that._cellIndex_ + "=" + datas[j][that._cellIndex_] + ']');
						$gridlistLi.removeClass("selected");
						$gridlistLi.parent().find(".gridlist_checkbox_div input").iCheck("uncheck");
						$gridlistLi.addClass(that.options.disabledCls);
					}else if (datas[j][lowKey] && datas[j][lowKey].indexOf(item) >= 0) {
						var $gridlistLi =  gridlistBox.find('>.gridList_ul>li>.demo_content[' + that._cellIndex_ + "=" + datas[j][that._cellIndex_] + ']');
						$gridlistLi.removeClass("selected");
						$gridlistLi.parent().find(".gridlist_checkbox_div input").iCheck("uncheck");
						$gridlistLi.addClass(that.options.disabledCls);
					}
				}
			})
		},
		blukCancelDisableCell : function(key,cellid){
			var that = this;
			var gridlistBox = this.getGridlistBox();
			var datas = this.getData();

			var key = key || 'id';
			var lowKey = key.toLowerCase();
			var cellidArray = cellid.split(",");

			$.each(cellidArray,function(i,item){
				for (var j = 0; j < datas.length; j++) {
					if (datas[j][key] && datas[j][key].indexOf(item) > 0) {
						gridlistBox.find('>.gridList_ul>li>.demo_content[' + that._cellIndex_ + "=" + datas[j][that._cellIndex_] + ']').removeClass(this.options.disabledCls);
					}else if (datas[j][lowKey] && datas[j][lowKey].indexOf(item) >= 0) {
						gridlistBox.find('>.gridList_ul>li>.demo_content[' + that._cellIndex_ + "=" + datas[j][that._cellIndex_] + ']').removeClass(this.options.disabledCls);
					}
				}
			})
		},
		cancelAllDisableCell : function(){
			var that = this;
			var gridlistBox = this.getGridlistBox();
			gridlistBox.find('>.gridList_ul>li>.demo_content.'+this.options.disabledCls).removeClass(this.options.disabledCls);
		},
		cancelDisableCell : function(cellid){
			var that = this;
			var gridlistBox = this.getGridlistBox();
			var datas = this.getData();
			if (cellid) {
				for (var j = 0; j < datas.length; j++) {
					if (datas[j]['id'] == cellid) {
						gridlistBox.find('>.gridList_ul>li>.demo_content[' + that._cellIndex_ + "=" + datas[j][that._cellIndex_] + ']').removeClass(this.options.disabledCls);
					}
				}
			}
		},
		getGridlistBox: function(){
			var that = this;
			return $(that.target).find(".gridList_box");
		},
		getData: function(){
			var that = this;
			return that.datas;
		},
		_init_ : function(){
			var that = this;
			// that.datas = that.options.datas;
			// that._initGridList_();
			// that._loadGridList_();
			// that._bindEvent_();
			that.changeTime = 700;	//切换时间
			//若是自适应
			if(that.options.adapt){
				$(that.target).width('100%');
				that.width = $(that.target).width();	//控件宽度
			}else{
				$(that.target).width(that.options.width);
				that.width = $(that.target).width();	//控件宽度
			}
			if(that.options.visible){
				
			}else{
				$(that.target).hide();
			}
			// $(that.target).height(that.options.height);
			
			that.cellWidth = ($(that.target).width()-that.options.distance) / that.options.cols;	//每格宽度
			that.cellHeight = ($(that.target).height()-that.options.distance) / that.options.rows;	//每格高度
			that.widthScale = 1/that.options.cols * 100;	//行精度，即拖动多大时换页
			that.heightScale = 1/that.options.rows * 100;	//列精度
			that.pageCount = that.options.cols * that.options.rows;	//每页格子总数
			that.options.pagination.pageSize = that.pageCount;
			that._cellIndex_ = "cell-index";	//顺序位名称

			if(that.options.pagination.paging){
				that.changeTime = 0;
				this.initPageEvent();	
			}
			// that.query();
			if(that.options.datas){
				var ret = {
					total : that.options.datas.length,
					data : that.options.datas
				}
				that._ajaxSuccess(ret);
			}else{
				that.query();
			}
		},
		_initGridList_: function(){
			var that = this;
			that.pageMax = Math.ceil(that.total / that.pageCount);	//总页数
			//滚动时才滑动
			if(!that.options.pagination.paging){
				$(that.target).css("overflow","hidden");	//超过隐藏
			}
			that.nextDistant = 0;	//下一页距离

			//基础标签。
			var that = this;
 
			if(that.options.pagination.littlePage){
				$(that.target).addClass("mt_little_page");
			}	
			var $content = $(that.target).find(">.gridList_content");
			if($content[0]){
				$content.empty();
			}else{
				$(that.target).empty();
				$content = $('<div class="gridList_content"></div>');
				$(that.target).append($content);	
			}
			
			var $box = $('<div class="gridList_box"></div>');	//内容box
			
			//百分比
			// if(that.options.slideType == "leftRight"){
			// 	$box.width('100'*that.pageMax + "%");
			// }else{
				$box.width("100%");
			// }
			// $box.height('100%');
			
			$box.height(that.options.height);

			var currentDistance = 0;	//当前位置
			$box.css("transition","all "+that.changeTime+"ms ease");
			$box.css("transform",'translate3d(0px, '+currentDistance+'px, 0px)');

			//滚动时才滑动
			if(!that.options.pagination.paging){
				$content.css("overflow","hidden");	//超过隐藏
			}

			//使用分页取消，滚动操作
			// if(that.options.pagination.paging){
			// 	$content.height($(that.target).height() - 44);
			// }else{
			// 	$content.height('100%');
			// }
			if(that.options.adapt)
				that.options.height = $(that.target).height() - 51;
			$content.height(that.options.height);

			$content.append($box);
			// $(that.target).empty();
			// $content.append($('<div class="gridList_mask"><a href="#" class="btn red btn-outline thumbnailbt" role="button">放大</a></div>'));

			
			// var $style = $(that.target).find("style");
			// if($style[0]){
			// 	$style.siblings().remove();
			// }else{
			// 	$(that.target).append($content);	
			// }


			that.height = $content.height();	//内容控件高度

			that.$box = $box;
		},
		getDrapRow : function(){
			var that = this;
			return that.dragItem;
		},
		getDropRow : function(){
			var that = this;
			return that.dropItem;
		},
		getOverRow : function(){
			var that = this;
			return that.overItem;
		},

		getCellData: function(o) {
			//	var index = this.getRowIndex(o), row = this._getDataArray()[index];
			var that = this;
			var datas = this.getData();
			var index = $(o).attr(that._cellIndex_);
			return datas[index];
		},

		getSelectionCells : function(){
			var that = this;
			return $(that.target).find(".gridList_box .gridList_ul> li> .demo_content.selected");
		},

		getSelectedDatas : function(){
			var that = this;
			var selectedRows = new Array(),
				that = this;
			$.each(that.getSelectionCells() || [], function() {
				selectedRows.push(that.getCellData(this));
			});
			return selectedRows;
		},

		//获取点击的数据
		getClickValue : function(){
			var that = this;

			return that.clickData;
		},
		//获取点击的数据
		getDblClickValue : function(){
			var that = this;

			return that.dblClickData;
		},

		getData : function(){
			var that = this;
			return that.datas;
		},

		//快速切换指定页
		// _gotoPage : function(pageNum){
		// 	var that = this;
		// 	var $box = that.$box;
		// 	var pageCount = that.pageCount;	//每页格子数
		// 	var datas = that.datas;
		// 	$box.empty();
		// 	that.appendPage(datas.slice((pageNum-1)*pageCount,pageCount*pageNum),pageNum);
		// },

		refresh : function(){
			var that = this;
			that.options.pagination.page = 1;
			that.query(that.options.ajax.read._query_params_ || {});
		},
		
		//加载表格
		_loadGridList_ : function(pageNo){
			var that = this;
			var $box = that.$box;

			var $ul;	//ul
			var n = 0;
			var nextDistant = 0;

			var k = 0;	//当前序号
			var datas = that.datas;	//数据
			var pageCount = that.pageCount;	//每页格子数

			if(that.options.pagination.paging && !that.options.pagination.serverPaging){
				//分页，服务端分页。
				pageNo = pageNo || 1;	//页数
				$box.empty();
				that.appendPage(datas.slice((pageNo-1)*pageCount,pageCount*pageNo),pageNo);//增加一页;
			}else{
				//分页，或非分页
				pageNo = pageNo || 0;
				for(k = 0; k < datas.length; k = k+pageCount){
					pageNo ++;
					that.appendPage(datas.slice(k,k+pageCount),pageNo);//增加一页;
				}
			}

			$(that.target).find(".gridlist_checkbox_div>input").iCheck({
	            checkboxClass: 'icheckbox_square-red',
	            disabledClass: 'icheckbox_square-red',
	            cursor:'default',
	        });

			that.width = $(that.target).find(".gridList_ul:first").width();

			var $target = $(that.target);
			if(that.options.draggable){
				//拖拽事件
				var height = $target.find(".demo_content").height() + 'px';
				var width = $target.find(".demo_content").width() + 'px';
				var $demo_content = $target.find("demo_content");
				$target.find(".demo_content").draggable(
			        {
			            connectToSortable: ".designer",
			            cursor: "move",
			            // cursorAt: { top: height, left: 3 },
			            helper: "clone",
			            // width: width,
			            // height: height,
			            zIndex:9999,
						start: function (e, t) {
							var $helper = t.helper;
							var $dragItem = $(e.target);
							$helper.width($dragItem.width());
							$helper.height($dragItem.height());

							var cellIndex =  parseInt($dragItem.attr("cell-index"));
							// if(that.options.pagination.paging && !that.options.pagination.serverPaging){
							// 	cellIndex = cellIndex  + (that.options.pagination.page - 1) * that.pageCount;
							// }
			               	that.dragItem = that.datas[cellIndex];
			               	// that.clickData = that.dragItem;
			               	//拖拽时触发事件
			               	if($.isFunction(that.options.callback.onDrapStart)){
								that.options.callback.onDrapStart();
							}
			               	// that.dropItem = null;
			            },
			        }
		        );
			}
			if(that.options.droppable){
				$target.find(".demo_content").droppable({
					drop: function( event, ui ) {
						// alert("drap");
						var $dropItem = $(event.target);
						if($dropItem.hasClass(that.options.disabledCls)){
							return false;
						}

						var cellIndex = parseInt($dropItem.attr("cell-index"));
						// if(that.options.pagination.paging && !that.options.pagination.serverPaging){
						// 	cellIndex = cellIndex  + (that.options.pagination.page - 1) * that.pageCount;
						// }
						// if(that.dropItem == null){
	               		that.dropItem = that.datas[cellIndex];
						if($.isFunction(that.options.callback.onDroppEnd)){
							that.options.callback.onDroppEnd();
						}
						// }
					},
					over: function(event,ui){
						var $overItem = $(event.target);
						var cellIndex = parseInt($overItem.attr("cell-index"));
	               		that.overItem = that.datas[cellIndex];

						if($.isFunction(that.options.callback.onDroppOver)){
							that.options.callback.onDroppOver();
						}
					}
				});
			}

			
			if(that.options.pagination.paging && that.pageMax == 1 && that.options.pagination.pagePanelState == 'onenone'){

			}else{
				that.renderPagination();	
			}
			
			// if(that.options.pagination.paging && that.pageMax == 1){
			// 	that.renderPagination();
			// }else{
			// 	if(!$target.find(".page")[0]){
			// 		$target.append("<div class='page' style='height:51px;width:10px;background-color: transparent;'>");	
			// 	}
			// }
		},
		/**
		 * 增加一页
		 * @param  {[type]} datas  页面数据
		 * @param  {[type]} pageNo 该页页数
		 * @return {[type]}        [description]
		 */
		appendPage : function(datas,pageNo){
			var that = this;
			var $box = that.$box;

			$ul = $("<ul class='gridList_ul'>");
			$ul.css("position",'absolute');
			//百分比
			// if(that.options.slideType == "leftRight"){
			// 	$ul.width((100/that.pageMax) +"%");
			// }else{
				$ul.width("100%");
			// }
			$ul.height('100%');

			if(!that.options.pagination.paging){
				$ul.css("float",'left');

				// 调整边距
				if(that.options.slideType === 'upDown'){
					//百分比
					$ul.css("top",that.nextDistant + "%");
					that.nextDistant += 100;
				}else if(that.options.slideType === 'leftRight'){
					//百分比
					$ul.css("left",(100/that.pageMax)*that.nextDistant/100  + "%");
					that.nextDistant += 100;
				}
			}

			
			$box.append($ul);
			var k = (pageNo-1) * that.pageCount;	//前一页的最有一页的编码。

			$.each(datas,function(i,item){
				that.appendCell($ul,item,k+i);
				if(that.options.ajax.schema.model.id){
					item.id = item[that.options.ajax.schema.model.id];
				}
			})

			//若是填充空白
			if(that.options.isbank){
				for(var i = datas.length; i < that.pageCount;i++){
					that.appendCell($ul,null,k+i,true);
				}
			}
		},

		query:function(data){
			var that = this,
			ar = that.options.ajax.read;
			ar.success = function(ret) {

				that._ajaxSuccess(ret, $(that.target));
			};
			//保存查询参数
			ar._query_params_ = data;
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params));
		},
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
		_request: function(ar, params) {
			var that = this;
			if (!ar.__success__) { //第一次ajax 调用
				ar.success = function(ret) {
					that._ajaxSuccess(ret, ar.__target);
				};
				ar.__success__ = true;
				ar.__data__ = $.extend(ar.__data__ || {}, ar.data); // 原始参数保存
				ar.d_data__ = $.extend(ar.d_data__ || {}, ar.data); // 动态参数保存
			}
			that._initParams(ar, params);
			$.ajax(ar);
		},
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
		_ajaxSuccess:function(ret){
			var that = this;
			that.datas = ret.data;
			that.total = ret.total;

			var render;
			//这个是表达式转换模板
			// var currentTemplate = $.extend(true, {}, template);
			// currentTemplate.config ("openTag", "#:");
			// currentTemplate.config ("closeTag", "#");
			// render = currentTemplate.compile(that.options.template);
			// that.templateRender = render;	//这个是表达式转换模板

			that._initGridList_();
			
			that._loadGridList_();
			// that.appendPage(ret.data,1);//增加一页;
			that._bindEvent_();
			if($.isFunction(that.options.callback.OnLoadSuccess)){
				that.options.callback.OnLoadSuccess(ret);
			}
		},
		initPageEvent : function(){
			var that = this;
			if(that.options.pagination.serverPaging){
				//服务器分页
				$(that.target).on("click", ".pageBt", function(e) {
					// $(that.element).find(".btn-toolbar .btn-group .pageBt").removeClass("active");
					// $(that).addClass("active");
					that.options.pagination.page = parseInt($(this).html());
					that.query(that.options.ajax.read._query_params_ || {});
				});
				$(that.target).on("click", ".prevBt", function(e) {
					if (that.options.pagination.page - 1 > 0) {
						that.options.pagination.page = that.options.pagination.page - 1;
						that.query(that.options.ajax.read._query_params_ || {});
					}
				});
				$(that.target).on("click", ".nextBt", function(e) {
					if (that.options.pagination.page + 1 <= that.pageMax) {
						that.options.pagination.page = that.options.pagination.page + 1;
						that.query(that.options.ajax.read._query_params_ || {});
					}
				});
			}else{
				//客户端分页
				$(that.target).on("click", ".pageBt", function(e) {
					$(that.target).find(".btn-toolbar .btn-group .pageBt").removeClass("active");
					$(this).addClass("active");
					that.options.pagination.page = parseInt($(this).html());
					that._loadGridList_(that.options.pagination.page);
					// that.scrolling('render');
				});
				$(that.target).on("click", ".prevBt", function(e) {
					if(that.options.pagination.page-1>0){
						$(that.target).find(".btn-toolbar .btn-group .pageBt").removeClass("active");
						$(that.target).find("#pageBt"+(that.options.pagination.page-1)).addClass("active");
						that.options.pagination.page = that.options.pagination.page - 1;
						that._loadGridList_(that.options.pagination.page);
						// that.scrolling('up');
					}
				});
				$(that.target).on("click", ".nextBt", function(e) {
					if(that.options.pagination.page+1<=that.pageMax){

						$(that.target).find(".btn-toolbar .btn-group .pageBt").removeClass("active");
						$(that.target).find("#pageBt"+(that.options.pagination.page+1)).addClass("active");
						that.options.pagination.page = that.options.pagination.page + 1;
						that._loadGridList_(that.options.pagination.page);
						// that.scrolling('down');
					}
				});
			}
		},
		renderPagination: function() {
			var that = this;
			var $target = $(this.target);
			if (this.pageMax > 0) {
				// var paginateBtToolBar = $("<div class=\"btn-toolbar margin-bottom-10 pageBt-toolbar\">");
				// var paginateBtGroup = $(" <div class=\"btn-group\">");
				// var prevBt = $("<button type=\"button\" class=\"btn btn-default prevBt\"><i class=\"fa fa-angle-left\"></i></button>");
				// // if(this.options.pageNo==1){
				// // 	prevBt.addClass("disabled");
				// // }
				// paginateBtGroup.append(prevBt);

				// var totalPages = this.pageMax;
				// for (var i = 0; i < totalPages; i++) {
				// 	var pageBt = $("<button type=\"button\" class=\"btn btn-default pageBt\"></button>");
				// 	if (this.options.pagination.page == (i + 1)) {
				// 		pageBt.addClass("active");
				// 	}
				// 	pageBt.attr("id",'pageBt'+(i+1));
				// 	pageBt.html(i + 1);
				// 	paginateBtGroup.append(pageBt);
				// }
				// var nextBt = $("<button type=\"button\" class=\"btn btn-default nextBt\"><i class=\"fa fa fa-angle-right\"></i></button>");
				// // if(this.options.pageNo==totalPages){
				// // 	nextBt.addClass("disabled");
				// // }
				// paginateBtGroup.append(nextBt);
				// paginateBtToolBar.append(paginateBtGroup);

				var $id = $target.attr("paging");
				!$id && ($target.attr("paging", ($id = 'pageId-' + new Date().getTime())));
				var $page = $("#" + $id),
					pageFn = $page.data("pagination");
				if (!$page[0]) {

					var page = this.options.pagination;
					var $page = $("<div>", {
						id: $id,
						class: 'page',
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
									that.query(that.options.ajax.read._query_params_ || {});
								}else{
									that.options.pagination.page = pageNumber;
									that._loadGridList_(that.options.pagination.page);
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
		},
		//添加元素
		appendCell : function($ul,data,cellIndex,isbank){
			var that = this;
			var $li = $('<li>');
			var $div = $('<div class="demo_content">');
			// $div.css("background-color","#fff");

			//百分比
			$li.css("width",this.widthScale+"%");
			$li.css("height",this.heightScale+"%");
			$div.width("100%");
			$div.height("100%");


			$div.css("border-width",that.options.distance);	//设置间隔
			$div.css("border-color",that.options.distanceColor);	//设置间隔颜色
			$div.css("border-style","solid");	//设置border样式
			// $div.attr("cell-index",cellIndex);
			$div.attr(that._cellIndex_,cellIndex);
			if(data){
				data[that._cellIndex_] = cellIndex;	
			}
			

			$li.data("data",data);
			if(isbank == null || !isbank){
				
				$div.append(that._buildTemplateContent(data));
				// $div.append(that.templateRender(data));	//添加内容
				$div.click(function(event){
					that.isdblclick = false;	//非双击事件
					var $this = $(this);
					setTimeout(function() {
						if(that.isdblclick){
							return;
						}
						that.clickData = data;
						if($this.hasClass(that.options.disabledCls)){
							return;
						}

						if(!that.options.selectable || that.options.selectable == 'single'){
							$(that.target).find(".gridList_box .gridList_ul> li> .demo_content.selected").removeClass("selected");
							$(that.target).find(".gridList_box .gridList_ul> li> .gridlist_checkbox_div input:checked").iCheck("uncheck");
							$div.addClass('selected');
							$this.parent().find(".gridlist_checkbox_div input").iCheck("check");
						}else if(that.options.selectable == 'multiple'){
							if($this.hasClass('selected')){
								$this.removeClass('selected');
								$this.parent().find(".gridlist_checkbox_div input").iCheck("uncheck");
							}else{
								$this.addClass('selected');
								$this.parent().find(".gridlist_checkbox_div input").iCheck("check");
							}
						}

						if($.isFunction(that.options.callback.OnContentClick)){
							that.options.callback.OnContentClick(data);
						}
					}, 100)
				});
				$div.dblclick(function(event){
					that.isdblclick = true;	//双击事件
					that.dblClickData = data;
					if($.isFunction(that.options.callback.OnContentDblClick)){
						that.options.callback.OnContentDblClick(data);
					}
				})
			}

			if(that.options.ablecheckbox){
				var $gridlist_checkbox_div = $("<div class='gridlist_checkbox_div'><input type='checkbox' disabled></div>");
				// $gridlist_checkbox_div.css("position","absolute");
				$gridlist_checkbox_div.css("float","right");
				// $gridlist_checkbox_div.css("margin-top","-"+$div.outerHeight()+"px");
				$li.append($gridlist_checkbox_div);
			}

			$li.append($div);
			$ul.append($li);
			// $div.outerWidth($li.width());
			// $div.outerHeight($li.height());

			
			

			// $div.append("<div class='gridlist_checkbox_div'><input type='checkbox'></div>");
		},
		_buildTemplateContent : function(data){
			var that = this;
			var templateStr = that.options.template;

			var customdefinedArray = that.options.customdefined;
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

			// that.options.template.match(/#:[^#]+#/g)

			// var reg1 = /#:[^#]+#/g;
			// // var columns = reg1.exec(templateStr);
			// var columns = templateStr.match(reg1);
			// $.each(columns,function(i,item){
			// 	var dataItemName = item.substring(2,item.length-1);
			// 	if(!data[dataItemName]){
			// 		dataItemName = dataItemName.toLowerCase();
			// 	}
			// 	templateStr = templateStr.replace(item,data[dataItemName]);
			// })
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
		//添加事件
		_bindEvent_ : function(){
			var that = this;
			$(that.target).find(".gridList_box .gridList_ul> li> .demo_content").hover(function(){
				$(this).css("border-color",that.options.hoverDistanceColor);
			},function(){
				$(this).css("border-color",that.options.distanceColor);
			});

			//使用分页取消，滚动操作
			if(!that.options.pagination.paging){
				that.isTouchDevice = navigator.userAgent.match(/(iPhone|iPod|iPad|Android|playbook|silk|BlackBerry|BB10|Windows Phone|Tizen|Bada|webOS|IEMobile|Opera Mini)/);
				that.isTouch = (('ontouchstart' in window) || (navigator.msMaxTouchPoints > 0) || (navigator.maxTouchPoints));
				if(that.isTouchDevice){
			    	//触控
			    	that._setTouchScrolling(true);
				}
				//当鼠标移到指定的元素上
				$(that.target).on('mouseenter', function () {
					that.isTouchDevice = navigator.userAgent.match(/(iPhone|iPod|iPad|Android|playbook|silk|BlackBerry|BB10|Windows Phone|Tizen|Bada|webOS|IEMobile|Opera Mini)/);
				    if(that.isTouchDevice){
					}else{
						//设置鼠标滚动事件
				    	that._setMouseWheelScrolling(true);	
					}
				});
				$(that.target).on('mouseleave', function(){
				    //设置鼠标滚动事件
				    that._setMouseWheelScrolling(false);
				    that._setTouchScrolling(false);
				});
			}

			//添加点击可变大图片事件
			if(that.options.isOpenImg){
				// $(that.target).find('img').mouseover(function(e){
				// 	that.showMask($(this));
				// })

				//绑定事件
				$(that.target).find('.thumbnailbt').click(function(e) {
					var $imgObj = $(that.target).find('.gridList_mask').data("imgObj");
					that.openOn($imgObj);
					e.stopPropagation();
				});
			}

			window.onresize = function(){
			    that.width =  $(that.target).find(".gridList_ul:first").width();
			    that.height = $(that.target).find(".gridList_content").height();

			    if(that.options.slideType === 'upDown'){
		        	//向下滚动
	        		var currentDistance = (that.options.pagination.page - 1) * (-that.height);
					$(that.target).find(".gridList_box").css("transform",'translate3d(0px, '+currentDistance+'px, 0px)');
				}else if(that.options.slideType === 'leftRight'){
					// 向左滚动
	        		var currentDistance = (that.options.pagination.page - 1) * (-that.width);
					$(that.target).find(".gridList_box").css("transform",'translate3d('+currentDistance+'px, 0px, 0px)');
				}
			}

		},
		//添加鼠标滚轮事件
		_setMouseWheelScrolling : function(value){
			var that = this;
			
			if(value){
				//鼠标滚轮事件
                that.addMouseWheelHandler();
            }else{
                that.removeMouseWheelHandler();
            }
		},
		//添加鼠标滚轮事件
		_setTouchScrolling : function(value){
			var that = this;
			
			if(value){
                that.addTouchHandler();
            }else{
                that.removeMouseWheelHandler();
                that.removeTouchHandler();
            }
		},

		/**
        * Gets the pageX and pageY properties depending on the browser.
        * https://github.com/alvarotrigo/fullPage.js/issues/194#issuecomment-34069854
        */
        getEventsPage : function(e){
            var that = this;
            var events = [];

            events.y = (typeof e.pageY !== 'undefined' && (e.pageY || e.pageX) ? e.pageY : e.touches[0].pageY);
            events.x = (typeof e.pageX !== 'undefined' && (e.pageY || e.pageX) ? e.pageX : e.touches[0].pageX);

            //in touch devices with scrollBar:true, e.pageY is detected, but we have to deal with touch events. #1008
            if(that.isTouch && that.isReallyTouch(e)){
                events.y = e.touches[0].pageY;
                events.x = e.touches[0].pageX;
            }

            return events;
        },

		/**
		 * 添加鼠标滚轮和触控板的滚动事件
		 * 当该事件发生时，会滚动到下一页
		 */
		addMouseWheelHandler : function(){
            var that = this;
            var prefix = '';
            var _addEventListener;

            if (window.addEventListener){
                _addEventListener = "addEventListener";
            }else{
                _addEventListener = "attachEvent";
                prefix = 'on';
            }

            //检测适合的滚动事件
             // detect available wheel event
            var support = 'onwheel' in document.createElement('div') ? 'wheel' : // Modern browsers support "wheel"
                      document.onmousewheel !== undefined ? 'mousewheel' : // Webkit and IE support at least "mousewheel"
                      'DOMMouseScroll'; // let's assume that remaining browsers are older Firefox


            if(support == 'DOMMouseScroll'){
                $(that.target)[0][ _addEventListener ](prefix + 'MozMousePixelScroll', that.MouseWheelHandler2, false);
            }

            //handle MozMousePixelScroll in older Firefox
            else{
                $(that.target)[0][ _addEventListener ](prefix + support, that.MouseWheelHandler2, false);
            }
        },

        /**
        * Removes the auto scrolling action fired by the mouse wheel and trackpad.
        * After this function is called, the mousewheel and trackpad movements won't scroll through sections.
        */
        removeMouseWheelHandler : function(){
            var that = this;
            if ($(that.target)[0].addEventListener) {
                $(that.target)[0].removeEventListener('mousewheel', that.MouseWheelHandler, false); //IE9, Chrome, Safari, Oper
                $(that.target)[0].removeEventListener('wheel', that.MouseWheelHandler, false); //Firefox
                $(that.target)[0].removeEventListener('MozMousePixelScroll', that.MouseWheelHandler, false); //old Firefox
            } else {
                $(that.target)[0].detachEvent('onmousewheel', that.MouseWheelHandler); //IE 6/7/8
            }
        },

        MouseWheelHandler2 : function(e){
        	$(this).gridList("MouseWheelHandler",e);
        },

        /**
         * 检测滚动事件
         * @param {[type]} e [description]
         */
        MouseWheelHandler : function(e) {
        	var that = this;
        	that.scrollings = that.scrollings || [];
        	
        	var scrollings = that.scrollings;
        	

            var curTime = new Date().getTime();
            that.prevTime = that.prevTime || curTime;
            var prevTime = that.prevTime;

            e = e || window.event;
            var value = e.wheelDelta || -e.deltaY || -e.detail;
            var delta = Math.max(-1, Math.min(1, value));


            var horizontalDetection = typeof e.wheelDeltaX !== 'undefined' || typeof e.deltaX !== 'undefined';
            var isScrollingVertically = (Math.abs(e.wheelDeltaX) < Math.abs(e.wheelDelta)) || (Math.abs(e.deltaX ) < Math.abs(e.deltaY) || !horizontalDetection);

            //Limiting the array to 150 (lets not waste memory!)
            if(scrollings.length > 149){
                scrollings.shift();
            }

            //keeping record of the previous scrollings
            scrollings.push(Math.abs(value));

			//time difference between the last scroll and the current one
            var timeDiff = curTime-prevTime;
            prevTime = curTime;   //上一次滚动时间         

            //haven't they scrolled in a while?
            //(enough to be consider a different scrolling action to scroll another section)
            if(timeDiff > 200){	//2次滚动间隔为200ms
                //emptying the array, we dont care about old scrollings for our averages
                scrollings = [];
            }

            //keeping record of the previous scrollings
            scrollings.push(Math.abs(value));

            var averageEnd = that.getAverage(scrollings, 10);
            var averageMiddle = that.getAverage(scrollings, 70);
            var isAccelerating = averageEnd >= averageMiddle;

            //to avoid double swipes...
            if(isAccelerating && isScrollingVertically){
                //scrolling down?
                if (delta < 0) {
                    that.scrolling('down');

                //scrolling up?
                }else {
                    that.scrolling('up');
                }
            }
        },

        addTouchHandler : function(){
			var that = this;
			var isTouch = (('ontouchstart' in window) || (navigator.msMaxTouchPoints > 0) || (navigator.maxTouchPoints));

            $(that.target)[0].addEventListener('touchstart', that.touchStartHandler2,false);  
            $(that.target)[0].addEventListener('touchmove', that.touchMoveHandler2,false);
		},
		/**
		 * scrolling Event End
		 * @return {[type]} [description]
		 */
		touchEndHandler2 : function(){
			$(this).gridList("touchEndHandler",event);
		},

		touchEndHandler : function(){
			var that = this;
         	// additional: if one of the normalScrollElements isn't within options.normalScrollElementTouchThreshold hops up the DOM chain
            if ( that.isReallyTouch(event) ) {
                //preventing the easing on iOS devices
                var touchEndY = that.touchEndY;
                var touchEndX = that.touchEndX;
                $(that.target).find(".gridList_box").css("transition-delay",'700ms');

                if(that.options.slideType === 'upDown'){
            		//is the movement greater than the minimum resistance to scroll?
	                if (Math.abs(that.touchStartY - touchEndY) > ($(that.target).height() * that.options.touchSensitivity)) {
	                    if (that.touchStartY > touchEndY) {
	                        that.scrolling('down', null);
	                    } else if (touchEndY > that.touchStartY) {
	                        that.scrolling('up', null);
	                    }
	                }
                }else if(that.options.slideType === 'leftRight'){

                	// //is the movement greater than the minimum resistance to scroll?
	                if (Math.abs(that.touchStartX - touchEndX) > ($(that.target).width() * that.options.touchSensitivity)) {
	                    if (that.touchStartX > touchEndX) {
	                        that.scrolling('down', null);
	                    } else if (touchEndX > that.touchStartX) {
	                        that.scrolling('up', null);
	                    }
	                }
                }
            }
		},

		/**
        * Removes the auto scrolling for touch devices.
        */
        removeTouchHandler : function(){
        	var that = this;
            $(that.target)[0].removeEventListener('touchstart', that.touchStartHandler2,false);  
        	$(that.target)[0].removeEventListener('touchmove', that.touchStartHandler2,false);
        },
		touchStartHandler2 : function(event){
            $(this).gridList("touchStartHandler",event);
        },
        /**
        * Handler for the touch start event.
        */
        touchStartHandler : function(event){
            // var e = event.originalEvent();
            e = event;
            var that = this;

            //stopping the auto scroll to adjust to a section
            $(that.target).stop();

            if(that.isReallyTouch(event)){
                var touchEvents = that.getEventsPage(e);
                that.touchStartY = touchEvents.y;
                that.touchStartX = touchEvents.x;
            }
        },
        touchMoveHandler2 : function(evnet){
        	$(this).gridList("touchMoveHandler",event);
        },
        /* Detecting touch events

        * As we are changing the top property of the page on scrolling, we can not use the traditional way to detect it.
        * This way, the touchstart and the touch moves shows an small difference between them which is the
        * used one to determine the direction.
        */
        touchMoveHandler : function(event){
        	var that = this;

         	// additional: if one of the normalScrollElements isn't within options.normalScrollElementTouchThreshold hops up the DOM chain
            if ( that.isReallyTouch(event) ) {

                //preventing the easing on iOS devices
                event.preventDefault();
                var touchEvents = event.touches[0];

                that.touchEndY = touchEvents.pageY;
                that.touchEndX = touchEvents.pageX;
                var touchEndY = touchEvents.pageY;
                var touchEndX = touchEvents.pageX;
                $(that.target).find(".gridList_box").css("transition-delay",'30ms');

                if(that.options.slideType === 'upDown'){
            		//is the movement greater than the minimum resistance to scroll?
	                if (Math.abs(that.touchStartY - touchEndY) > ($(that.target).height() * that.options.touchSensitivity)) {
	                    //向下滚动
	                    if (that.touchStartY > touchEndY) {
	                        that.scrolling('down', null);
	                    } else if (touchEndY > that.touchStartY) {
	                        that.scrolling('up', null);
	                    }
	                }
                }else if(that.options.slideType === 'leftRight'){
                	//is the movement greater than the minimum resistance to scroll?
	                if (Math.abs(that.touchStartX - touchEndX) > ($(that.target).width() * that.options.touchSensitivity)) {
	                    if (that.touchStartX > touchEndX) {
	                        that.scrolling('down', null);
	                    } else if (touchEndX > that.touchStartX) {
	                        that.scrolling('up', null);
	                    }
	                }
                }

                
            }
        },
        /**
        * As IE >= 10 fires both touch and mouse events when using a mouse in a touchscreen
        * this way we make sure that is really a touch event what IE is detecting.
        */
        isReallyTouch : function(e){
            //if is not IE   ||  IE is detecting `touch` or `pen`
            return typeof e.pointerType === 'undefined' || e.pointerType != 'mouse';
        },
        /**
        * Determines the way of scrolling up or down:
        * by 'automatically' scrolling a section or by using the default and normal scrolling.
        */
        scrolling : function(type){
            var that = this;
            if(type === 'render'){
            	//若是直接跳转到某页
            	if(that.options.slideType === 'upDown'){
	        		//向下滚动
	        		var currentDistance = (that.options.pagination.page - 1) * (-that.height);
					$(that.target).find(".gridList_box").css("transform",'translate3d(0px, '+currentDistance+'px, 0px)');
				}else if(that.options.slideType === 'leftRight'){
					//向右滚动
					var currentDistance = (that.options.pagination.page - 1) * (-that.width);
					$(that.target).find(".gridList_box").css("transform",'translate3d('+currentDistance+'px, 0px, 0px)');
				}
            }else{
	            var scrollSection = (type === 'down') ? that.moveSectionDown : that.moveSectionUp;
	            scrollSection.call(that);
	        }
        },

        moveSectionDown : function(){
        	var that = this;
			if(that.options.pagination.page >= this.pageMax){
        	}else{
        		that.options.pagination.page ++;	
        	}
        	if(that.options.slideType === 'upDown'){
        		//向下滚动
        		var currentDistance = (that.options.pagination.page - 1) * (-that.height);
				$(that.target).find(".gridList_box").css("transform",'translate3d(0px, '+currentDistance+'px, 0px)');
			}else if(that.options.slideType === 'leftRight'){
				//向右滚动
				var currentDistance = (that.options.pagination.page - 1) * (-that.width);
				$(that.target).find(".gridList_box").css("transform",'translate3d('+currentDistance+'px, 0px, 0px)');
			}
        },

        moveSectionUp : function(){
        	var that = this;
        	if(that.options.pagination.page <= 1){
        	}else{
        		that.options.pagination.page --;        		
        	}
        	if(that.options.slideType === 'upDown'){
	        	//向下滚动
        		var currentDistance = (that.options.pagination.page - 1) * (-that.height);
				$(that.target).find(".gridList_box").css("transform",'translate3d(0px, '+currentDistance+'px, 0px)');
			}else if(that.options.slideType === 'leftRight'){
				// 向左滚动
        		var currentDistance = (that.options.pagination.page - 1) * (-that.width);
				$(that.target).find(".gridList_box").css("transform",'translate3d('+currentDistance+'px, 0px, 0px)');
			}
        },

		/**
		* Gets the average of the last `number` elements of the given array.
		*/
		getAverage : function(elements, number){
		    var sum = 0;

		    //taking `number` elements from the end to make the average, if there are not enought, 1
		    var lastElements = elements.slice(Math.max(elements.length - number, 1));

		    for(var i = 0; i < lastElements.length; i++){
		        sum = sum + lastElements[i];
		    }

		    return Math.ceil(sum/number);
		},

		// showMask : function($img) {
		// 	//获取当前图片的位置和高宽度信息
		// 	if ($img && $img != window) {
		// 		var imgObj = $img;
		// 		//设置遮罩层大小位置
		// 		var mask = $(this.target).find(".gridList_mask");
		// 		mask.height(imgObj.height());
		// 		mask.width(imgObj.width());
		// 		mask.css("position", "absolute");
		// 		mask.css("line-height", imgObj.height() + "px");
		// 		mask.css("top", imgObj.offset().top);
		// 		mask.css("left", imgObj.offset().left);
		// 		mask.data("imgObj",imgObj);
		// 	}
		// },
		// //打开模板大图弹窗
		// openOn : function(imgObj) {
		// 	if (imgObj && imgObj != window) {
		// 		$.fancybox.open({
		// 			href: imgObj.attr("src"),
		// 			type: 'image',
		// 			tpl: {
		// 				image: '<img class="fancybox-image" src="{href}" alt="" /></div>',
		// 			},
		// 			closeClick: true,
		// 			openEffect: 'none',
		// 			helpers: {
		// 				title: {
		// 					type: 'inside'
		// 				}
		// 			}
		// 		});
		// 	}
		// }
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});
})(jQuery);


// .btn-toolbar .active {
// 			    z-index: 2;
// 			    color: #fff;
// 			    background-color: #337ab7;
// 			    border-color: #337ab7;
// 			    cursor: default;
// 			}
