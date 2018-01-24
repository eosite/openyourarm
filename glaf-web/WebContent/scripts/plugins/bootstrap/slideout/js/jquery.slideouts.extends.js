(function($, window, document, undefined) {
	var plugin = "slideouts", optionKey = plugin + ".options",
	
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
				this.data("slideouts", new Plugin(this, params));
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
			Canvas : {
				
			}
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    target = that.target,
			    opt = that.option;
			$("body").prepend(target);
			target.removeAttr("style");
			target.removeAttr("cname");
			target.removeAttr("crtltype");
			target.removeAttr("data-role");
			target.removeAttr("data-offcanvas");
			mui.init();
//			that.__OffCanvasSide(target);
			that.__OffCanvaScroll();
			that.__OffCanvaIOSClose();	
			opt.Canvas.offCanvasWrapper = mui('#offCanvasWrapper');
		},
        __OffCanvasSide : function(target){
        	var that = this;
        	 //侧滑容器父节点
			var offCanvasWrapper = mui('#offCanvasWrapper');
			 //主界面容器
			var offCanvasInner = offCanvasWrapper[0].querySelector('body');
			 //菜单容器
			var offCanvasSide = target.find("#offCanvasSide");
			
			 //移动效果是否为整体移动
			var moveTogether = false;
			 //侧滑容器的class列表，增加.mui-slide-in即可实现菜单移动、主界面不动的效果；
			var classList = offCanvasWrapper[0].classList;
			 //变换侧滑动画移动效果；
			mui('.mui-input-group').on('change', 'input', function() {
				if (this.checked) {
					offCanvasSide.classList.remove('mui-transitioning');
					offCanvasSide.setAttribute('style', '');
					classList.remove('mui-slide-in');
					classList.remove('mui-scalable');
					switch (this.value) {
						case 'main-move':
							if (moveTogether) {
								//仅主内容滑动时，侧滑菜单在off-canvas-wrap内，和主界面并列
//								offCanvasWrapper[0].insertBefore(offCanvasSide, offCanvasWrapper[0].firstElementChild);
								that.__OffCanvaInsertBefore(offCanvasWrapper,offCanvasSide,offCanvasWrapper[0].firstElementChild);
							}
							break;
						case 'main-move-scalable':
							if (moveTogether) {
								//仅主内容滑动时，侧滑菜单在off-canvas-wrap内，和主界面并列
//								offCanvasWrapper[0].insertBefore(offCanvasSide, offCanvasWrapper[0].firstElementChild);
								that.__OffCanvaInsertBefore(offCanvasWrapper,offCanvasSide,offCanvasWrapper[0].firstElementChild);
							}
							classList.add('mui-scalable');
							break;
						case 'menu-move':
							classList.add('mui-slide-in');
							break;
						case 'all-move':
							moveTogether = true;
							//整体滑动时，侧滑菜单在inner-wrap内
//							offCanvasInner.insertBefore(offCanvasSide, offCanvasInner.firstElementChild);
							that.__OffCanvaInsertBefore(offCanvasWrapper,offCanvasSide,offCanvasInner.firstElementChild);
							break;
					}
//					offCanvasWrapper.offCanvas().refresh();
					that.__OffCanvaRefresh(offCanvasWrapper);
				}
			});
        },
        
        __OffCanvaInsertBefore : function(offCanvasWrapper,offCanvasSide,firstElementChild){
        	offCanvasWrapper[0].insertBefore(offCanvasSide, firstElementChild);
        },
	    __OffCanvaRefresh : function(offCanvasWrapper){
	    	offCanvasWrapper.offCanvas().refresh();
	    },
	    __OffCanvaScroll : function(){
	    	 //主界面和侧滑菜单界面均支持区域滚动；
			mui('#offCanvasSideScroll').scroll();
			
	    },
	    __OffCanvaIOSClose : function(){
	    	 //实现ios平台原生侧滑关闭页面；
			if (mui.os.plus && mui.os.ios) {
				mui.plusReady(function() { //5+ iOS暂时无法屏蔽popGesture时传递touch事件，故该demo直接屏蔽popGesture功能
					plus.webview.currentWebview().setStyle({
						'popGesture': 'none'
					});
				});
			}
	    },
        _hidden : function(){
        	var that = this,
        	    opt = that.option,
        	    target = that.target;
        	mui("#"+target.attr("id")).offCanvas("close")
        },
        _show : function(){
        	var that = this,
    	    opt = that.option,
    	    target = that.target;
        	mui("#"+target.attr("id")).offCanvas("show")
        }
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

