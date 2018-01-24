/**
 * https://www.jqueryscript.net/demo/jQuery-Plugin-For-Date-Range-Selector-Range-Calendar/
 * @param  {[type]} $ [description]
 * @return {[type]}   [description]
 */
! function($) {
	var plugin = 'rangecalendarext';

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
		rangeCalendar : {
			lang:"zh-cn",	//语言,实际上使用的是moment的语言格式
			theme : "default-theme",	//主题格式,默认主题
			startDate : new Date("2017-10-01"),	//开始时间,如果不设置，默认是moment()，则需要前面设置语言种类.
			endDate : new Date("2017-12-12"),
			start : "+0",	//开始的格式
			startRangeWidth : 1,	//开始的选中范围。
			minRangeWidth : 1,	//可设置的最小日期范围
			maxRangeWidth : 1,	//可设置的最大日期范围
			hideMonths : true,	//隐藏月份
			autoHideMonths : true,	//自动隐藏月份月份
			visible : true,	//可见
			changeRangeCallback : function( el, cont, dateProp ) {
				//日期改变事件
				return false; 
			}
		},
		events: {
			onClickCell : function(event){

			}
		}
	};

	$.fn[plugin].methods = {
		convertToDataStr : function(dateStr){
			var prestr = dateStr.substring(0,6);
			var afterstr = dateStr.substring(6);
			if(afterstr.length < 2){
				afterstr = "0"+afterstr;
			}
			return prestr+afterstr;
		},
		resize : function(){
			var that = this;
			var options = that.options;
			var $target = that.$target;

			if(!that.$target.is(":hidden") ){
				//重新生成格子的大小
				var nowCellWidth = $target.width() / 7;

				if(Math.abs(options.rangeCalendar.cellWidth - nowCellWidth) >0.01){
					that.$target.hide();
					that.$target.find(".cal-cell").outerWidth(nowCellWidth);
					options.rangeCalendar.cellWidth = nowCellWidth;
					
					var calendarObj = that.calendarObj;
					var $selected = $target.find('.cal-cell.selected');
					var currentMonthDate;
					if($selected[0]){
						currentMonthDate = that.convertToDataStr($selected.attr("date-id"));
					}else{
						currentMonthDate = options.rangeCalendar.defaultDate || new Date();
					}
					if(currentMonthDate){
						that._changeToPosition(moment(currentMonthDate));
					}
					setTimeout(function(){
						that.$target.show();	
					},1);
				}
			}
		},
		_changeToPosition : function(date,callback){
			var that = this;
			var options = that.options;
			var $target = that.$target;

			//重新计算下每个格子的宽度，保证一个页面能够放7个格子。
			// $target.find(".cal-cell").outerWidth($target.width() / 7);
			var defaultDate = date.clone();
			var vWeekOfDay=moment(defaultDate).format("E");//算出这周的周几
			if(vWeekOfDay != 7){
				defaultDate=defaultDate.add('days',-vWeekOfDay);
			}
    		

			var default_Day = defaultDate.format('YYYYMMD');
			var $month = $target.find(".cal-cell[date-id='"+default_Day+"']");
			var $calendar = $target.find(".wrapper .calendar");
			if($month[0]){
				var left = $month.position().left;
				if(left == 0 ){
					var prevNum = $month.prevAll().length;
					if(prevNum > 0){
						left = prevNum * $month.outerWidth(true);
					}
				}
				//拥有默认日期
				// $month.position().left
				$calendar.stop().animate({left: -left},1,'easeOutCirc');	

				var $selected = $target.find(".cal-cell[date-id='"+moment(options.rangeCalendar.defaultDate || new Date()).format('YYYYMMD')+"']");
				if(callback){
					callback($selected);
				}
				that.onChangeMonthFun($selected.attr("date-id"));
			}else{
				var $orignDay = $target.find(".cal-cell[date-id='"+moment(date).format("YYYYMMD")+"']");
				if($orignDay[0]){
					vWeekOfDay= parseInt(moment(date).format("E")) ;//算出这周的周几
					var left = $orignDay.position().left;
					left += $target.width() / 7 * vWeekOfDay;
					$calendar.stop().animate({left: left},1,'easeOutCirc');		
				}else{
					//默认选中中间的一个
					var $cell = $calendar.find(".cell");
					var middleNum = Math.ceil($cell.length/2);
					if(middleNum>0){
						$month = $($cell[middleNum]);
						var defaultDateStr = $month.attr("date-id");
						defaultDate = moment(defaultDateStr);
						vWeekOfDay=moment(defaultDate).format("E");//算出这周的周几
						if(vWeekOfDay != 0){
							defaultDate=defaultDate.add('days',-vWeekOfDay);
							default_Day = defaultDate.format('YYYYMMD');
							$month = $target.find(".cal-cell[date-id='"+default_Day+"']");
						}
						
						var left = $month.position().left;
						if(left == 0 ){
							var prevNum = $month.prevAll().length;
							if(prevNum > 0){
								left = prevNum * $month.outerWidth(true);
							}
						}
						$calendar.stop().animate({left: -left},1,'easeOutCirc');	
						if(callback){
							callback($month);
						}
						that.onChangeMonthFun($month.attr("date-id"));
					}
				}
			}
		},
		slideOperator : function($component,left){
			var that = this;
			that.calendarObj.calendarObj.data("isSlidering",true);
			$component.stop().animate({left: left},300);
			setTimeout(function(){
				that.calendarObj.calendarObj.data("isSlidering",false);
			},300);
		},
		_initrangecalendar : function(){
			var that = this;
			var options = that.options;
			var $target = that.$target;
			var calendarObj = that.calendarObj;
			//修改原有的日历控件。
			options.rangeCalendar.changeRangeCallback = function(el,cont,dateProp){
				
			}

			options.rangeCalendar.onSuccessFunc = function(obj){
				that.currentMonthDate = options.rangeCalendar.defaultDate || new Date();
				that._changeToPosition(moment(that.currentMonthDate),function($selected){
					$selected.trigger("click");
				});

				$target.find(".cal-cell[date-id='"+moment(new Date()).format('YYYYMMD')+"']").addClass("nowDate")
			}
			
			calendarObj = that.$target.rangeCalendar(options.rangeCalendar);
			that.calendarObj = calendarObj;

			calendarObj._placeElement = function (el, position,currentMonthId) {
				//修改用于拖拽时，滑动下一页。
				var $el = $target;

				if(that.calendarObj.calendarObj.data("isSlidering")){
					return false;
				}

				var calendarViewWidth = $el.outerWidth();
				var cellWidth = $(el).find(".cell").first().outerWidth();
				var objChildrens = $(el).children().length;
				//objWidth 为最大的滚动量
				var objWidth = (objChildrens*cellWidth);

				//该功能是用于表头的月份栏的滚动的
				if(calendarViewWidth > objWidth ){
					//日历的总宽度，小于总的父元素中。
				    left = (calendarViewWidth-objWidth)/2;
				    that.slideOperator($(el),left);
				    // $(el).stop().animate({left: left},200,'easeOutCirc');
				    return;
				}
				
				//position() 方法返回匹配元素相对于父元素的位置（偏移）
				//查询出所移动的地方，移动与父元素的位置.
				var elPos =  $(el).position();
				left = (  !position ? parseInt(elPos.left) :  -position.left);

				var startleft = $(el).data("startleft");
				$(el).data("startleft",null);
				if(startleft == null){
					//则是切换到指定的位置信息.
					if(currentMonthId){
						that.currentMonthDate = that.convertToDataStr($(calendarObj.calendarObj.find('.cell[month-id="'+currentMonthId+'"]').eq(0)).attr("date-id"));
						that._changeToPosition(moment(that.currentMonthDate));
					}
				}else {
					//如果有开始位置时，则是滚动切换
					var xmove = elPos.left - startleft;
					var direction = xmove >= 0 ? 'left' : 'right';
					if(Math.abs(xmove) < $el.width()/7){
						that.slideOperator($(el),startleft);
						// $(el).stop().animate({left: startleft},200,'easeOutCirc');	
						return;
					}

					var isstart = $(el).data("isstart");
					var isend = $(el).data("isend");
					if(direction == 'left'){
						if(startleft == 0 || isstart){
							//滑动到顶端，不处理
							left =startleft; 	
						}else{
							//向右滑动，每次滑动，滑动整个控件宽度
							left = startleft + $el.width();	
						}
						$(el).data("isend",false);
					}else if(direction == 'right'){
						if(isend){
							left =startleft; 	
						}else{
							//向左滑动，每次滑动，滑动整个控件宽度
							left = startleft - $el.width();
						}
						$(el).data("isstart",false);
					}else{
						//不是左右滑动，不处理
						return;
					}
					
					if (calendarViewWidth < objWidth && left >= 0){
						//这里的left > 0 是向右移动，所以left >= 0 是相对于父元素，移动与父元素的左边了，即超过左边了
						$(el).data("isstart",true);
						$(el).data("isend",false);
					}else if(left < calendarViewWidth-objWidth){
						//向右移动，移动到顶端了。
						$(el).data("isstart",false);
						$(el).data("isend",true);
					}else{
						//其他默认
						$(el).data("isstart",false);
						$(el).data("isend",false);
					}

					that.slideOperator($(el),left);
					// $(el).stop().animate({left: left},200,'easeOutCirc');	

					var obj = calendarObj;
					if($(el) != obj.monthsObj){
						setTimeout(function(){
							var rangeCalendarWidth = $el.outerWidth();
				  			var calendarOffset = obj.calendarObj.position();

							var monthMaxId = parseInt(obj.monthsObj.find(".cell").last().attr("month-id"));
							var monthMinId = parseInt(obj.monthsObj.find(".cell").first().attr("month-id"));

							var currentMonthId = parseInt(obj.monthsObj.find(".cell.selected").attr("month-id"));
							var nextMonthId = parseInt(obj.monthsObj.find(".cell.selected").next().attr("month-id"));
							var prevMonthId = parseInt(obj.monthsObj.find(".cell.selected").prev().attr("month-id"));
							if(nextMonthId && currentMonthId && nextMonthId <= monthMaxId && direction == "right") {
							
								var nextMonthsCell = obj.monthsObj.find('.cell[month-id="'+nextMonthId+'"]');
								var nextMonthCalendarCell = obj.calendarObj.find('.cell[month-id="'+nextMonthId+'"]').first();
								var nextMonthCalendarCellPos = nextMonthCalendarCell.position();
								
								
								var nextMonthLeftCenter = (rangeCalendarWidth/2 -(nextMonthCalendarCellPos.left )) ;
								
								if( nextMonthLeftCenter >= calendarOffset.left && calendarOffset.left != 0){
				      				
			      					obj.monthsObj.find(".cell").removeClass("selected");
			      					$(nextMonthsCell).addClass("selected");
			      					obj._placeElement(obj.monthsObj,nextMonthsCell.position());

			      					//日期月份改变事件
			      					that.onChangeMonthFun(nextMonthId);
			      				}
			      				
			      			}
			      			else if(prevMonthId && currentMonthId && prevMonthId >= monthMinId && direction == "left") {
							
								var prevMonthCell = obj.monthsObj.find('.cell[month-id="'+prevMonthId+'"]');
								var prevMonthCalendarCell = obj.calendarObj.find('.cell[month-id="'+prevMonthId+'"]').last();
								var prevMonthCalendarCellPos = prevMonthCalendarCell.position();
								var prevMonthLeftCenter = (rangeCalendarWidth/2 -(prevMonthCalendarCellPos.left )) ;
														
			      				if(prevMonthLeftCenter <= calendarOffset.left+prevMonthCalendarCell.outerWidth() ) {
				      				
				      				obj.monthsObj.find(".cell").removeClass("selected");
			      					$(prevMonthCell).addClass("selected");
			      					
			      					obj._placeElement(obj.monthsObj,prevMonthCell.position());

			      					//日期月份改变事件
			      					that.onChangeMonthFun(prevMonthId);
			      				}
			      			}
						},310);
					}
				}
				
			}
		},
		onChangeMonthFun : function(monthId){
			var that = this;
			that.ajaxquery({monthId:monthId});
		},
		_init : function(){
			var that = this;
			var options = that.options;
			var $target = $(that.target);
			that.$target = $target;

			//每个格子的宽度
			options.rangeCalendar.cellWidth = $target.width() / 7;
			options.rangeCalendar.defaultDate = new Date();

			options.rangeCalendar.startDate = options.rangeCalendar.startDate?moment(options.rangeCalendar.startDate).toDate() : moment(new Date()).add('days',-90).toDate();
			options.rangeCalendar.endDate = options.rangeCalendar.endDate?moment(options.rangeCalendar.endDate).toDate() : moment(new Date()).add('days',+90).toDate();

			that._buildEvent();
			that._initrangecalendar();

			that.resize();

			that.count = that.count || 0;
			$(that.target).resize(function() {
				if(!that.count){
					that.count++;
					setTimeout(function(){
						that.resize.call(that);
						that.count = 0;
					},100)
				}
			});
			
			//然后再查询数据集，获取出已有的数据，并设置class。
			// that.ajaxquery();
		},
		_buildEvent : function(){
			var that = this;

			that.$target.on("click",".cal-cell",function(event){
				if($.isFunction(that.options.events.onClickCell)){
					that.options.events.onClickCell();
				}
			})

			that.target.addEventListener("dragstart",function(event){
				event.preventDefault();
	          	event.stopPropagation();	
			})
			that.target.addEventListener("drag",function(event){
				event.preventDefault();
	          	event.stopPropagation();	
			})
			that.target.addEventListener("dragend",function(event){
				event.preventDefault();
	          	event.stopPropagation();	
			})
			that.target.addEventListener("slide",function(event){
				event.preventDefault();
	          	event.stopPropagation();	
			})
			that.target.addEventListener(["touchstart","touchmove","touchend"],function(event){
				event.preventDefault();
	          	event.stopPropagation();	
			})
			that.target.addEventListener(["mousedown",'mousemove','mouseup'],function(event){
				event.preventDefault();
	          	event.stopPropagation();	
			})
			// that.$target.touchstart = function(event){
			// 	event.preventDefault();
	  //         	event.stopPropagation();	
			// }

			// that.$target.mousedown = function(event){
			// 	event.preventDefault();
	  //         	event.stopPropagation();	
			// }
		},
		ajaxquery : function(data,callback){
			var that = this;
			var ar = that.options.read;
			ar.success = function(ret) {
				that._ajaxSuccess(ret);
				if($.isFunction(callback)){
					callback(ret);
				}
			};
			//保存查询参数
			ar._query_params_ = data;
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params),data?data.id:null);
		},
		query:function(data){
			var that = this;
			//重新查询的，清空页数
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
			}else{
				ret.params = JSON.stringify($.extend(true,JSON.parse(ret.params),params));
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
		/**
		 * [_ajaxSuccess description]
		 * @param  {[type]} ret        [description]
		 * @param  {[type]} $parentDom [父节点信息]
		 * @return {[type]}            [description]
		 */
		_ajaxSuccess:function(ret){
			var that = this;
			that.total = ret.total;
			that._loadData(ret.data);
			that.isloadding = false;
		},
		//加载数据
		_loadData : function(datas){
			var that = this;
			var $target = that.$target;
			var datekeyname = that.options.keys.datekeyname;
			$.each(datas,function(i,item){
				if(item[datekeyname]){
					$target.find(".cal-cell[date-id='"+moment(item[datekeyname]).format("YYYYMMD")+"']").addClass("hasData");
				}
			})
		},
		getValue : function(datas){
			var that = this;
			var dateId = that.$target.find(".cal-cell.selected").attr("date-id");
			if(dateId){
				return moment(that.convertToDataStr(dateId)).toDate();
			}
			return "";
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);