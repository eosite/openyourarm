/**
 *  kendo 扩展方法
 */
(function($) {
	//选卡隐藏事件
	$.fn.kendoTabstripHidden = function() {
		if (this.is(":hidden")) {
			this.closest(".k-tabstrip-wrapper").hide();
		}
	};
	//grid 单击事件
	$.fn.kendoGridClick = function(id, linkType, rules, params) {
		$("#" + id).data("isdblclick", false);
		setTimeout(function() {
			if ($("#" + id).data("isdblclick")) {
				return;
			}
			pubsub.pub(linkType, rules, params);
		}, 500)
	};
	//grid 双击事件
	$.fn.kendoGridDblClickByType = function(id, linkType, rules, params) {
		$("#" + id + " tbody").delegate("tr", "dblclick", function(e) {
			$("#" + id).data("isdblclick", true);
			pubsub.pub(linkType, rules, params);
		});
	}
	//双击事件 by 事件定义器
	$.fn.kendoGridDblClick = function(id, ex) {
		$("#" + id + " tbody").delegate("tr", "dblclick", function(e) {
			$("#" + id).data("isdblclick", true);
			pubsub.pub(ex, "", this);
		});
	}
})(jQuery);