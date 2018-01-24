(function($) {
	//产生内容元素
	/**
	 * schema 可以为function 自定义模板内容返回值
	 * 		  也可以为 对象 定义了固定模式  {img:field,title:field}
	 */
	var jhtml = "";
	var buildByFn = function(i, data, schmea) {
		return schema.call(this, i, data);
	};
	var buildBody = function(datas, fn, $this, opts, databaseId) {
		var retStr = "<div class='bd'><ul class='picList'>",
			data, length = datas.length;
		for (var i = 0; i < length; i++) {
			data = datas[i];
			data.databaseId = databaseId||"";
			retStr += "<li>";
			retStr += fn(data);
			retStr += "</li>";
		}
		retStr += "</ul></div><a class='prev' href='javascript:void(0)'></a><a class='next' href='javascript:void(0)'></a>";
		jhtml = retStr;
		if ($this) {
			f($this, opts, jhtml);
		}
		return jhtml;
	};
	//异步获取数据
	function ajaxRequest($this, opts, fn) {
		$.ajax({
			url: opts.url,
			type: 'post',
			dataType: 'json',
			async: true,
			data: JSON.stringify(opts.params),
			contentType: "application/json",
			success: function(datas) {
				jhtml = buildBody(datas.data, fn, null, null, $this.data("databaseId"));
				f($this, opts, jhtml);
				opts.callback.call($this);
				var params = $this.data("params");
				if(params){
					yscrollFunc.linkageControl($this.attr("id"),params);
					$this.data("params",null);
				}
			}
		});
	}
	var f = function($this, opts, jhtml) {
			$this.html(jhtml);
			$this.data("opts", opts);
			$('#yscroll').find('.yscrollclass').width(($this.width() - (25 * opts.vis)) / opts.vis).height($this.height - 23);
			$this.addClass("yscroll").slide({
				mainCell: ".bd ul",
				autoPage: true,
				effect: "left",
				autoPlay: opts.autoPlay,
				vis: opts.vis,
				scroll: opts.scroll,
				trigger: "click",
				interTime:opts.speed || 2000
			});
		}
		//方法集合
	var methods = {
		init: function(options) {
			var opts = $.extend(true, {}, $.fn[plugin].defaults, options);
			return this.each(function(i, o) {
				//模板产生
				var tp = opts.template,
					$this = $(this);
				opts.datas ? buildBody(opts.datas, tp, $this, opts) : ajaxRequest($this, opts, tp);
				//$(this).html(jhtml);
				//$(this).data("opts",opts);
				//$('#yscroll').find('.yscrollclass').width(($(this).width()-(25*opts.vis))/opts.vis).height($(this).height-23);
				//$(this).addClass("yscroll").slide({mainCell:".bd ul",autoPage:true,effect:"left",autoPlay:opts.autoPlay,vis:opts.vis,scroll:opts.scroll,trigger:"click"});
			});
		},
		next: function(options) {
			var opts = $.extend(true, {}, $.fn[plugin].defaults, options);
		},
		prev: function(options) {
			var opts = $.extend(true, {}, $.fn[plugin].defaults, options);
		}
	};
	var plugin = "yScroll";
	$.fn[plugin] = function(opts) {
			if (methods[opts]) {
				return methods[opts].apply(this, Array.prototype.slice.call(arguments, 1));
			} else if (typeof opts === 'object' || !opts) {
				return methods.init.apply(this, arguments);
			} else {
				$.error('Method ' + opts + ' does not exist on jQuery.yScroll');
			}
		}
		//默认参数
	$.fn[plugin].defaults = {
		effect: "left",
		scroll: 2,
		autoPage: true, //自动分页
		autoPlay: true, //
		vis: 2, //显示数量
		speed: 2000, //图片切屏时间
		url: null, //远程获取数据地址
		template: null,
		datas: null, //静态数据
		callback: function() {} //回调函数
	};

})(jQuery);