/**
 * 全局配信息
 */
window.CONFIG = {
	contextPath : "/glaf"
}, $callback = jQuery.Callbacks("hmtd.java.spread");

/**
 * 获取地址栏参数
 * 
 * @param name
 * @returns
 */
var getUrlVar = (function() {
	var vars = {}, pattern = /[?&]+([^=&]+)=([^&]*)/gi;
	var parts = window.location.href.replace//
	(pattern, function(m, key, value) {
		vars[key] = value;
	});
	return function(name) {
		return vars[name];
	};
})();

var require = (function() {
	var config = {
		baseUrl : CONFIG.contextPath
	}, $container = $("html");

	var func = new Function();

	func.config = function(conf) {
		$.extend(true, config, conf);
	};

	function createScript(i, src) {
		$("<script>", {
			src : src,
			type : "text/javascript"
		}).appendTo($container);
	}

	func.addRelative = function(ss, cb) {
		if (!ss)
			return;
		if (!(ss instanceof Array)) {
			ss == [ ss ];
		}
		$.each(ss, createScript);
		cb && cb();
	};

	func.add = function(ss, cb) {
		if (!ss)
			return;
		if (!(ss instanceof Array)) {
			ss == [ ss ];
		}
		$.each(ss, function(i, src) {
			ss[i] = config.baseUrl + src;
		});
		func.addRelative(ss, cb);
	};
	return func;

})(jQuery);

var GcSpreadStyle = (function($) {
	var methods = {
		init : function() {
		},
		initTools : function() {
		},
		setTitle : function(title) {
			$("title").text(GcSpread.Sheets.designer//
			.cn_res.title = title || this.title || "");
		},
		resize : function() {

		}
	};

	var styles = new Object()//
	, func = new Function();

	func.init = function(type) {
		var method = func.get(type);
		if (method) {
			$.each([ "init", 'initTools', 'setTitle',//
			'resize' ], function(i, m) {
				method[m] && method[m]();
			});
		}
	};

	func.get = function(type) {
		return styles[type];
	};

	func.add = function(type, title, init, objs) {
		styles[type] = $.extend(true, {}, methods, {
			title : title,
			init : init
		}, objs);
	};
	return func;
})(window.jQuery);

/**
 * 定时器
 */
var IntervalProcess = (function() {
	var func = new Function();

	func.clear = function(interval) {
		interval && window.clearInterval(interval);
	};

	/**
	 * filter 过滤函数 fn 成功回调 timeOut 执行频率 times 执行次数
	 */
	func.wait = function(filter, fn, timeOut, times) {
		times = times || 10;
		var time = 0, isOk = false, //
		interval = window.setInterval(function() {
			fn && filter && //
			(isOk = filter()) && fn();
			(time > times || isOk) && func.clear(interval);
			console.log(++time);
		}, timeOut || 500);
	};
	return func;
})();

/**
 * 外部侵入js,添加外部自定义按钮\菜单以及方法处理
 */
(function(GcSpread) {
	window.$GcSpread = GcSpread;
	var styles = {
		report : [ "/webfile/js/jquery.ztree.extends.js",
				"/scripts/spreadjs/report/report.js" ]
	}, style = window.getUrlVar("style");

	require.add(styles[style || ""] || [], function() {
	});
})(window.GcSpread);

$callback.add(function() {
	GcSpreadStyle.init(window.getUrlVar("style"));
});

/**
 * 页面还原
 * 
 * @param spread
 */
var showTemplate = function(spread, id) {
	var URL = CONFIG.contextPath + //
	"/mx/dep/report/depReportTemplate/getDepReportTemplate";
	$.ajax({
		url : URL,
		data : {
			id : id
		},
		type : 'POST',
		dataType : 'JSON',
		success : function(ret) {
			if (ret && ret.tmpJson) {
				spread.fromJSON(JSON.parse(ret.tmpJson));
			}
		}
	});
}

/**
 * 初始化spread dom,以及页面
 */
$callback.add(function() {
	IntervalProcess.wait(function() {
		return !!($ss = $("#ss")).get(0);
	}, function() {
		var templateId = window.getUrlVar("templateId");
		templateId && showTemplate($ss.data("spread"), templateId);
	});
});

/**
 * 
 * @returns
 */
function ContentHeight() {
	return $(".container>.content").height();
}

$(document).ready(function() {
	$callback.fire();
});