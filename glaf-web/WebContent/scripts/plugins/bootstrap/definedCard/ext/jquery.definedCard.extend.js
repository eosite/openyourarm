! function($) {
	var plugin = 'definedCardExt';

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
			state.options.columns = eval("("+columns+")");
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults={
		_itemIndex : "item-index",	//为定位在数据中的位置的属性名称
		pagination : {
            page: 1,
            pageSize:5,
            paging: true,
        },
        events:{
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
		_isMobile: function(){
			var userAgentInfo = navigator.userAgent;  
			var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
			var flag = false;  
			for (var v = 0; v < Agents.length; v++) {  
				if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = true; break; }  
			}  
			return flag;  
		},
		_init: function() {
			var that = this;
			that._createList();
			that._initEvents();
		},
		_createList: function(){
			var that = this;
			var $element = $(that.target);
			var opts = that.options;

			$element.addClass("definedCardExt");
			that._itemIndex = that.options._itemIndex;	//为itemIndex的属性名称
			that.__itemArray = [];	//用来保存所有数据
			that.itemstyle = "list_item";	//li标签的class

			//这个是表达式转换模板  去除此编译模板
			//var currentTemplate = $.extend(true, {}, template);
			//currentTemplate.config ("openTag", "#:");
			//currentTemplate.config ("closeTag", "#");
			//render = currentTemplate.compile(that.options.template);
			//that.templateRender = render;	//这个是表达式转换模板

			// var $definedCardExt_header = $('<div class="definedCardExt_header"> ');
			// $definedCardExt_header.html(opts.mainTitle);
			// $element.append($definedCardExt_header);

			// var $definedCardExt_header = $element.find('.definedCardExt_header');
			// $definedCardExt_header.html(opts.mainTitle);
			
			// var $definedCardExt_content = $('<div class="definedCardExt_content"></div>');
			// var $definedCardExt_list = $('<div class="definedCardExt_list"></div>');

			// $definedCardExt_content.append($definedCardExt_list);
			// $element.append($definedCardExt_content);


			var $definedCardExt_content = $element.find(".definedCardExt_content");
			if(opts.height){
				$definedCardExt_content.height(opts.height);
				$definedCardExt_content.css("overflow-y","auto");
			}
			$element.find(".definedCardExt_list").empty();

			//标题
			var $definedCardExt_header = $element.find('.definedCardExt_header');
			$definedCardExt_header.html(opts.mainTitle);
			if(!opts.isTitle){
				$definedCardExt_header.hide();
			}

			

			if(opts.columns || opts.columns != undefined){
				var content = "<div class='definedCardExt'>"
						+ "<div class='definedCardExt_header'>"
						+ "<p><span style='color: rgb(255, 255, 255);'></span>		</p>"
						+ "</div><div class='definedCardExt_content' style='height: 400px; '>"
						+ "<div class='definedCardExt_list'></div></div></div>";
				$(that.target).append(content);
				opts.data = opts.datas;
				that._ajaxSuccess(opts, $(that.target));
			}
			else{
				that.query();
			}
			
		},
		_initSlimScroll:function(){
			var that = this;
			var opts = that.options;
			var $target = $(that.target);

			debugger;
			if(that._isMobile()){
				$(".definedCardExt_content").scroll(function(){
					if(that.isloadding){
						//数据加载中
						return false;
					}

					var divHeight = $(this).height();
				    var nScrollHeight = $(this)[0].scrollHeight;
				    var nScrollTop = $(this)[0].scrollTop;

				    if(nScrollTop + divHeight >= nScrollHeight) {
				    	that.isloadding = true;
						$target.find(".definedCardExt_list li.more a").trigger('click');
				    }
				})
			}else{
				//滚动条设置
				var $definedCardExt_list = $target.find(".definedCardExt_list")
				if(opts.height){
					try{
						$definedCardExt_list.slimScroll({"destroy":"destroy"});
					}catch(e){

					}
					// $definedCardExt_list.height(opts.height);
					$definedCardExt_list.slimScroll({
						alwaysVisible: true, //是否 始终显示组件
						disableFadeOut: false,//是否 鼠标经过可滚动区域时显示组件，离开时隐藏组件
						railVisible: true, //是否 显示轨道
						railColor: (opts.slimScroll?opts.slimScroll.railColor:"") || '#333', //轨道颜色
						railOpacity: .2, //轨道透明度
						height: opts.height, //可滚动区域高度
						color: (opts.slimScroll?opts.slimScroll.slimScrollColor:"") || "#207bd6",	//滚动条颜色
						wheelStep: (opts.slimScroll?opts.slimScroll.slimScrollWheelStep:"") || 10,	//滚轮滚动量
						size: (opts.slimScroll?opts.slimScroll.slimScrollWheelStep:"") || '5px',	//滚动条宽度
					}).bind('slimscroll', function(e, pos){
						if(pos == 'bottom' && !that.endFlag){
							var scrollTop = $definedCardExt_list.scrollTop();
							console.log(scrollTop);
							$target.find(".definedCardExt_list li.more a").trigger('click');

							that.onloadSuccess_ = function(){
								$definedCardExt_list.slimscroll({"scrollTo":scrollTop});		
							}
						}
					 //    $definedCardExt_list.slimScroll({"destroy":"destroy"});
					 //    $definedCardExt_list.slimScroll({
						// })
					});
					// $definedCardExt_list.css("overflow","scroll");
				}
			}
		},
		_initEvents: function(){
			var that = this;
			var $target = $(that.target);
			var opts = that.options;
			that.endFlag = false;

			$target.on("click.more",".definedCardExt_list li.more a",function(e) {  //加载更多
				e.preventDefault();
				var total = opts.pagination.total || 0;
				if(opts.pagination.pageSize*opts.pagination.page<total){
					opts.pagination.page = parseInt(opts.pagination.page) + 1 ;
				}else{
					if(opts.showTip && opts.showTip == "true"){
						alert("友情提示：没有更多了。");	
					}
					$target.find(".definedCardExt_list li.more a").text("友情提示：没有更多了。");
					// $target.unbind("click");
					that.endFlag = true;
					return false;
				}
				var $li = $target.find(".definedCardExt_list ul li:last"); //加载中效果
				if($li[0]){
					App.blockUI({
		                target: $li,
		                animate: true,
		                overlayColor: 'none'
		            });
				}
	            that.query(that.options.ajax.read._query_params_ || {});
				return false;
			});

			$target.on("click",".definedCardExt_list li:not(.more)",function(e) {  //行选中
				$target.find(".definedCardExt_list li:not(.more)").removeClass("selected");
				$(this).addClass("selected");
				if($.isFunction(opts.events.onClickRow)){
					opts.events.onClickRow($(this).attr(that._itemIndex));
				}
			});

			that._initSlimScroll();

			//动态事件的触发方法

			// $target.find(".definedCardExt_list").slimScroll().bind('slimscroll', function(e, pos){
			//     if(pos=='bottom' && !that.scrollWait){
			//     	that.scrollWait = true;
			// 		$target.find(".definedCardExt_list li.more a").trigger('click');
			// 		$(this).slimscroll({});
			//     }
			// });

			

			// $target.find(".definedCardExt_content").scroll(function(e){
			// 	var scrollTop = this.scrollTop;	//滚动条可以滚动的长度,[0, (offsetHeight - clientHeight)],scrollTop表示滚动条（一个点）当前的位置在750px里占了多少，不是图中标出的a
			// 	var offsetHeight = this.offsetHeight;	//包括可见部分及以滚动条下面的不可见部分。
			// 	var clientHeight = this.clientHeight;	//这个高度则是不包括滚动条没显示出来的下面部分的内容。而只是单纯的DIV的高度
			// 	var scrollHeight = this.scrollHeight;
			// 	var elementHeight = $(this).height();

			// 	if(scrollTop + elementHeight >= scrollHeight && !that.scrollWait){
			// 		that.scrollWait = true;
			// 		$target.find(".definedCardExt_list li.more a").trigger('click');
			// 	}

			// 	if(opts.scrollTopAble){
			// 		if(scrollTop > 100){
			// 			$target.find(".scroll-top").fadeIn(500);
			// 		}else{
			// 			$target.find(".scroll-top").fadeOut(500);
			// 		}
			// 	}
			// })
		},
		getOject: function() {
			return $(this.target).find("video");
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
					that.loadding = false;	//设置加载状态
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

		_ajaxSuccess:function(ret){
			var that = this;
			var $target = $(that.target);
			var $list = $target.find(".definedCardExt_list");

			that.options.pagination.total = ret.total;

			if(!that.options.pagination.pagingServer){
				that.options.pagination.pageSize = ret.total;
			}

			if($target.find("ul li:last")[0]){
				$target.find("ul li:last").unblock({     //解除加载中效果
		            onUnblock: function() {
		                $target.find("ul li:last").css('position', '');
		                $target.find("ul li:last").css('zoom', '');
		            }
		        });
			}

			var dataArray = ret.data;
			that.__itemArray = that.__itemArray || [];

			var $ul = $list.find("ul");
			if($ul.length){   //加载第二页以后
				var $limore = $ul.find("li.more");
				$.each(dataArray,function(i,data){
					data[that._itemIndex] = that.__itemArray.length;
					that.__itemArray.push(data);
					var $li = that._buildItem(data,$list);
					if($limore[0]){
						$limore.before($li);
					}else{
						$ul.append($li);
					}
				})
			}else{
				$ul = $("<ul></ul>");
				$list.append($ul);
				$.each(dataArray,function(i,data){
					data[that._itemIndex] = that.__itemArray.length;
					that.__itemArray.push(data);

					var $li = that._buildItem(data,$list);
					$ul.append($li);
				})
				var $li = $('<li class="text-center more"><a href="javascript:;">加载更多</a></li>');
				$li.addClass($list.find("li:last").attr("class"));
				$li.find("a").attr("total",ret.total);
				$ul.append($li);
			}
			that.scrollWait = false;

			//修改图片信息，将没有表达式的图片修改为取rp
			$.each($target.find("img"),function(i,o){
				var $img = $(o);
				var $img_src = $img.attr("src");
				if($img_src.indexOf("/glaf/mx/form/imageUpload") > -1){
					var rp = $img_src.substring($img_src.indexOf("rp=")+3);
					var matchAry = rp.match(/.jpg|.png|.gif|.bmp/);
					if(matchAry && matchAry.length > 0){
						$img.attr("src",rp).show();
					}
				}
			})

			if($.isFunction(that.onloadSuccess_)){
				that.onloadSuccess_();
			}
			if(!that.options.showMoreBtn || that.options.showMoreBtn == 'false'){
				$target.find(".definedCardExt_list li.more").hide();
			}
			if($target.find(".definedCardExt_list ul").height() < $target.find(".definedCardExt_list").height()){
				//高度小于最大高度时，自动加载下一页
				$target.find(".definedCardExt_list li.more a").trigger("click");
			}

			that.isloadding = false;
		},

		_buildItem : function(data){
			var that = this;
			var itemstyle = that.itemstyle;
			var columns = that.options.columns
			var $li = $("<li></li>");
			$li.addClass(itemstyle);
			$li.attr("item-index",data[that._itemIndex]);

			var templateStr = "";

			var $content = $("<div></div>");	//内容div
			$content.addClass("list_item_conent");

			// $li.append(that.templateRender(data));
            if(that.options.template != null || that.options.template != undefined){
            	var templateStr = that._buildTemplateContent(data);
    			// $li.append(templateStr);
    			$content.append(templateStr);
            }
            else{
            	
            	$.each(columns,function(i,column){
            		var temp = null
            		$.each(that.options.fieldGrid.datas,function(i,fieldGrid){
            			if(column.field == fieldGrid.columnName)
                		 if(fieldGrid.type == 'image'){
                			 templateStr = templateStr + "<p style='text-align:center'><img src='"+contextPath + data[column.field]+"' width='250' height='250' /></p>";
                			 temp = "y";
                		 }
                	});
            		if(temp == null){
            			templateStr = templateStr + "<p style='text-align:center'>"+data[column.field]+"</p>";
            		}
            	});
            	// $li.append(templateStr);
            	$content.append(templateStr);
            }
            $li.append($content);
			return $li;
		},
		_buildTemplateContent : function(data){
			var that = this;
			var templateStr = that.options.template;

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
		getAllData : function(){
			var that = this;
			return that.__itemArray;
		},
		getSelectRow : function(data){
			var that = this;
			var itemArray = that.getAllData();
			var indexId = $(that.target).find(".definedCardExt_list>ul>li.selected").attr(that._itemIndex);
			return itemArray[indexId];
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
			that.endFlag = false;
			var $element = $(that.target);
			//清空原本的数据
			$element.find(".definedCardExt_list").empty();
			//注销滚动条
			//清空原本的数组
			that.__itemArray = [];
			//初始化页面为第一页
			that.options.pagination.page = 1;
			//重新查询
			that.query(data);

			that._initSlimScroll();
		}
	}
	
	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});

}(jQuery);