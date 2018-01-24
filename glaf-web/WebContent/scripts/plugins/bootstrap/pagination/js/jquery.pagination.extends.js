(function($) {
	var plugin = 'pagination', Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options'),

		p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		
		pageNumber : 1,
		listSize : 5,
		total : 0,
		pageSize : 10,//一页显示记录数
		totalPage : 0,
		pageSizes:[5,10,15,20,25,50,100,200,500],//分页条数选择
		buttonCount:10,//页码按钮显示数量
		previousNext:"true",//是否使能分页翻页按钮
		numeric:"true", //是否使能数字页码按钮
		input:"false", //是否使能输入框翻页
		first:'首页', //首页按钮提示文本
		last :'末页',//末页按钮提示文本
		previous:'上一页',//上一页显示文本
		next:'下一页',//下一页显示文本
		refresh:'false',
		
		events : {
			onSelectPage : function(pageNumber, pageSize) {
				//console.log(this);
				//alert('onSelectPage');
			},
			onBeforeRefresh : function(pageNumber, pageSize) {
			},
			onRefresh : function(pageNumber, pageSize) {
				//alert('onRefresh:'+pageNumber+','+pageSize);
			},
			onChangePageSize : function(pageNumber,pageSize) {
			}
		},
		pageClass : 'pagination'
	};

	$.fn[plugin].methods = {
		_init : function() {
			var $target = $(this.target), opts = this.options;
			!$target.is("ul") && $target.append("<ul>");
			var $ul = this.ul();
			!$ul.hasClass(opts.pageClass) && $ul.addClass(opts.pageClass);
			this._initEvents();
			this._appendPageSize(opts.total);
			this._appends(opts.total);
		},
		_activeCls : 'active',
		_namespace : '.bt.pagination',
		_initEvents : function() {
			var that = this, $ul = this.ul(), opts = this.options;
			$.each(opts.events, function(k, fn) {
				$ul.bind(k, function(e) {
					return fn.apply(that, Array.prototype.slice.call(arguments,
							1));
				});
			});
			var active = that._activeCls;
			$ul.on('click' + this._namespace, 'li>a', function(e) {//翻页按钮事件
				var $li = $(this).closest("li");
				if (!$li.hasClass(active)) {
					opts.pageNumber = parseInt($li.attr(that._rowIndex) || 1);
					$li.addClass(active).siblings().removeClass(active);
					return that._trigger("onSelectPage", opts.pageNumber,
							opts.pageSize);
				}
			});
			$ul.on('click' + this._namespace, 'li>span', function(e) {//页码点击事件
				var $li = $(this).closest("li");
				if (!$li.hasClass(active)) {
					opts.pageNumber = parseInt($li.find("span").attr("value")|| 1);
					$li.addClass(active).siblings().removeClass(active);

					if($(this).closest("ul").find('input').length>0){
						$(this).closest("ul").find('input').attr('value',opts.pageNumber);
					}
					return that._trigger("onSelectPage", opts.pageNumber,
							opts.pageSize);
				}
			});
			$ul.on('change'+ this._namespace, 'li>input', function(e) {//输入框翻页
				var $ul = $(this).closest("ul");
				if ($(this).val()!=''&&parseInt($(this).val()|| 1)<=opts.totalPage){
					that.options.pageNumber = $(this).val();
					that._trigger("onSelectPage",$(this).val(),opts.pageSize);
				}else{
					$(this).val(opts.pageNumber);
				}
			});
			$ul.on('click'+ this._namespace, 'a.btn-icon-only', function(e) {//刷新按钮
					that._trigger("onRefresh",$(this).attr('value'),opts.pageSize);
			});
		},
		_active : function(pageNumber) {
			var active = that._activeCls;
			var pn = (pageNumber - 1) || 0, $li = this.ul().find("li").eq(pn);
			$li.addClass(active).siblings().removeClass(active);
		},
		_trigger : function(k) {
			var params = Array.prototype.slice.call(arguments, 1);
			return this.ul().trigger(k, params);
		},
		_rowIndex : 'row-index',
		_numericIndex : 'numeric-index',
		__pagelist : {
			start : 1,
			end : 0,
			listNo : 1
		},
		_template : "<li {1}='{0}' class='{2}'><a href='javascript:void(0)'>{3}</a></li>",
		_append : function(SB, n) {
			SB.appendFormat(this._template, n, this._rowIndex,
					this.options.pageNumber == n ? this._activeCls : "", n);
		},
		__getListNo : function() {
			var opts = this.options, pl = this.__pagelist, ln;
			if (opts.pageNumber == 1) {
				pl.listNo = 1;
			} else if (opts.pageNumber >= opts.totalPage) {
				pl.listNo = Math.ceil((opts.totalPage + 1)/(opts.listSize - 1));
			} else if (pl.start == opts.pageNumber) {
				pl.listNo--;
			} else if (pl.end == opts.pageNumber) {
				pl.listNo++;
			}
			ln = ((pl.listNo || 1) - 1) * (opts.listSize - 1) + 1;
			if (opts.pageNumber > opts.totalPage) {
				ln--;
			}
			return ln;
		},
		_getListNo : function() {
			var opts = this.options, pl = this.__pagelist, ln;
			pl.listNo = Math.ceil(opts.pageNumber/opts.buttonCount);
			pl.start = (pl.listNo-1)*opts.buttonCount+1;
			if(pl.listNo*opts.buttonCount<=opts.totalPage){
				pl.end = pl.listNo*opts.buttonCount;
			}else{
				pl.end = opts.totalPage;
			}
			return pl;
		},
		_appendPageSize:function(total){
			function sortNumber(a,b){
				return a - b
			};
			var that = this;
			if(that.options.showPageList == false){
				return;
			}
			$target = $(this.target);
			var $info = $('<div class="page-size"><label>每页<select class="form-control input-sm input-xsmall input-inline"></select>条</label></div>');
			var pageSize = this.options.pageSize;
			if(this.options.pageSizes.indexOf(pageSize)==-1){
				this.options.pageSizes.push(pageSize);
				this.options.pageSizes.sort(sortNumber);
			}
			$.each(this.options.pageSizes,function(i,o){
				$info.find('select').append('<option value="'+o+'">'+o+'</option>');
			});
			// $info.find('select').append('<option style="display: none;" value="'+pageSize+'">'+pageSize+'</option>');
			$info.find('select').val(pageSize);
			$info.on('change','select',function(e){
				that.options.pageSize=$(this).val();
				that.options.pageNumber=1;
				that._appends(that.options.total);
				that._trigger("onChangePageSize",that.options.pageNumber,$(this).val());
			});
			$target.append($info);
			$target.append('<div class="total">,共'+total+'条</div>');
		},
		_appends : function(total) {
			var that = this;

			var SB = new StringBuffer();
			that._template = "<li {3}='{0}' ><a href='javascript:void(0)' page='{0}' title='{2}'>{1}</a></li>";
			var page = that.options.pageNumber;
			var pageSize = that.options.pageSize;
			var totalPage = that.options.totalPage = Math.ceil(total / pageSize);
			var previousNext = that.options.previousNext;
			
			var pl = this._getListNo();

			if(previousNext=='true'){

				SB.appendFormat(that._template, 1, "<<", this.options.first, that._rowIndex);

				SB.appendFormat(that._template, page-1>0?page-1:1, "<",this.options.previous, that._rowIndex);
				
				if(that.options.numeric=='true'){
					if(pl.listNo!=1){
						SB.appendFormat("<li {2}='{0}'><span title='第{0}页 共{1}页' value={0}>{3}</span></li>", 
						pl.start-1,totalPage,that._numericIndex,'...');
					}
					for (var i = pl.start; i<=pl.end; i++) {
						SB.appendFormat("<li {2}='{0}'><span title='第{0}页 共{1}页' value={0}>{0}</span></li>", 
							i,totalPage,that._numericIndex);
					};
					if(pl.end<totalPage){
						SB.appendFormat("<li {2}='{0}'><span title='第{0}页 共{1}页' value={0}>{3}</span></li>", 
						pl.end+1,totalPage,that._numericIndex,'...');
					}
				}
				
				SB.appendFormat(that._template,page+1<=totalPage?page+1:totalPage, "&gt", this.options.next, that._rowIndex);

				SB.appendFormat(that._template, totalPage, "&gt&gt", this.options.last,
						that._rowIndex);
						
			}else{
				if(that.options.numeric=='true'){
					if(pl.listNo!=1){
						SB.appendFormat("<li {2}='{0}'><span title='第{0}页 共{1}页' value={0}>{3}</span></li>", 
						pl.start-1,totalPage,that._numericIndex,'...');
					}
					for (var i = pl.start; i<=pl.end; i++) {
						
						SB.appendFormat("<li {2}='{0}'><span title='第{0}页 共{1}页' value={0}>{0}</span></li>", 
							i,totalPage,that._numericIndex);
						
					};
					if(pl.end<totalPage){
						SB.appendFormat("<li {2}='{0}'><span title='第{0}页 共{1}页' value={0}>{3}</span></li>", 
						pl.end+1,totalPage,that._numericIndex,'...');
					}
				}		
			}
			if(that.options.input!='false'){
				SB.appendFormat('<li>页<input class="form-control input-inline" style="width: 50px;" value={1}>共{0}页</li>',
					totalPage,page);
			}
			if(that.options.refresh!='false'){
				SB.appendFormat('<a href="javascript:;" class="btn btn-icon-only btn-refresh" value={0}><i class="fa fa-refresh">刷新</i></a>',page);
			}
			//SB.appendFormat("<li><span title='共 {0} 条记录'>共 {0} 条</span></li>",total);

			that.ul().empty().append(SB.toString(''));
			var $li = that.ul().find("li["+that._numericIndex+"="+page+"]");
			$li&&$li.addClass(that._activeCls);
			that.ul().siblings(".total").html(',共'+total+'条');
		},
		_load : function() {
			this._appends(this.options.total);
		},
		ul : function() {
			var $target = $(this.target);
			return $target.is("ul") ? $target : $target.find("ul");
		},
		refresh : function(options) {
			if (options) {
				this._load($.extend(this.options, options));
				return false;
				if (options.total != opts.total) {
					$.extend(opts, options);
					this._load();
				} else if (options.pageNumber
						&& options.pageNumber != opts.pageNumber) {
					this._active(options.pageNumber);
					opts.pageNumber = options.pageNumber;
				}
			}
		}
	};

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});

})(jQuery);
