/**
 * 系统拖拽组件 
 * 实现接口 var mtr =  new mtResizable($jQObj,{
 * 		start : function(){},
 * 		move : function(){},
 * 		end : function(){}
 * })
 */
var mtResizable = (function($) {
	var pluginName = "mt_resizable",
		resizeGobal = {
			seq: 1,
			currentSeq: null,
			events: {},
		},
		$body,
		resizable,
		MDOWN = "mousedown",
		MUP = "mouseup",
		MMOVE = "mousemove";
	$(function() {
		// 文档就绪
		//注册全局鼠标移动事件
		$body = $(document.body);
		$body.on(MMOVE + ".mt_resizable", function(e) {
			//设置光标
			if (resizeGobal.currentSeq != null) {
				resizable = resizeGobal.events[resizeGobal.currentSeq];
				e.location = resizable.location;
				resizable.hint && resizable.hint.css({left:e.originalEvent.pageX});
				resizable.defaultOptions.move.call(resizable, e);
			}
		}).on(MUP + ".mt_resizable", function(e) {
			$body.css("cursor", 'default');
			resizable = resizable || resizeGobal.events[resizeGobal.currentSeq];
			resizeGobal.currentSeq = null;
			if (resizable) {
				resizable.hint && resizable.hint.remove();
				resizable.defaultOptions.end.call(resizable, e);
			}
			resizable = null;
		});
	});

	var Resizable = function($ele, options) {
		var rthat = this;
		this.location = null;
		this.seq = "mt_resizable_" + (resizeGobal.seq++);
		this.targets = $ele;
		this.defaultOptions = {
			//提示基准线
			hint: function(e){
					return "" ;
			},
			//鼠标按下事件
			start: function(e) {

			},
			//鼠标移动过程中触发
			move: function(e) {

			},
			//鼠标放开后
			end: function() {

			}
		};
		$.extend(true,
			this.defaultOptions, options);
		this.init = function($ele) {
			$ele.on(MDOWN + "." + rthat.seq, function(e) {
				if (e.originalEvent.offsetX > this.offsetWidth - 8) {
					rthat.location = {
						oldX: e.originalEvent.pageX,
						oldWidth: this.offsetWidth
					};
					e.location = rthat.location;
					rthat.hint = rthat.defaultOptions.hint.call(this,e);
					if(rthat.hint){
						rthat.hint.css({
							border : "1px solid #cccccc",
							position : "absolute",
							top : rthat.hint.css("top") || $(this).offset().top-10,
							left:e.originalEvent.pageX,
							"z-index" : 99999,
						});
						$(document.body).append(rthat.hint);
					}
					rthat.defaultOptions.start.call(this, e);
					resizeGobal.currentSeq = rthat.seq;
					$body.css("cursor", this.style.cursor);
					rthat.target = $(this);
				}
			}).on(MMOVE + "." + rthat.seq, function(e) {
				//设置光标
				if (e.originalEvent.offsetX > this.offsetWidth - 8) {
					this.style.cursor = 'col-resize';
				} else {
					this.style.cursor = 'default';
				}
			});
			//注册移动function到全局
			resizeGobal.events[rthat.seq] = this;
		};
		this.init($ele);
		this.destory = function(){
			this.targets.off(MDOWN + "." + this.seq);
			this.targets.off(MMOVE + "." + this.seq);
			delete resizeGobal.events[this.seq];
		}
	}

	return Resizable;
})(jQuery);