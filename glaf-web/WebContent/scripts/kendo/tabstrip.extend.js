/**
 * 选卡
 */
var tabstripFunc = {
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide().closest(".k-tabstrip-wrapper").hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show().closest(".k-tabstrip-wrapper").show();
	},
	activateTab:function(rule,params,args){
		for(var key in params){
			pubsub.getJQObj(rule).data("kendoTabStrip").select(params[key]);	
		}
	}
};
pubsub.sub("tabstrip", tabstripFunc);
var glafTabstrip = {
	initHeight: function(e) {
		var dd = $(this.wrapper),
			ts = dd.data("kendoTabStrip"),ismodel = dd.attr("model");

		options = this.options, tabPosition = options.tabPosition, flag = (tabPosition == 'left' || tabPosition == 'right') ? true : false;
		if (options.innerWidth) {
			$(e.contentElement).width(options.innerWidth);
		}
		if (options.innerHeight) {
			$(e.contentElement).height(options.innerHeight);
		} else {
			$(e.contentElement).height(dd.innerHeight() - (flag ? 0 : dd.children(".k-tabstrip-items").outerHeight()) - 16);
		}
		if (kendo.support.browser.msie && flag /*&& !ismodel*/) { // 用于适配IE浏览器问题
			//if ($("html").hasClass("dj_contentbox")) {
			//	dd.children(".k-tabstrip-items").css("font-size", "74%");
			//}
			var ow = $(e.contentElement).hasClass('k-state-active') ? 0 : $(e.contentElement).outerWidth();
			var iow = dd.children(".k-tabstrip-items").outerWidth(); // 适配隐藏tab导致的
			if (dd.is(':hidden')) { // 如果是隐藏
				iow = 70;
			}
			dd.width(ow + iow + 3);
		}
		var iframe = $(e.contentElement).find('iframe');
		setTimeout(function() { // 延迟嵌套问题
			if (!iframe.attr('src')) {
				iframe.attr('src', iframe.attr('prosrc'));
				iframe.load(function() {
					$(this).data("onReadyState", true);
					if (ts._events.activate) {
						var acts = ts._events.activate,
							actsLength = acts.length;
						for (var i = 0; i < actsLength; i++) {
							acts[i](e);
						}
					}
				});
			}
		}, 350);
	}
};