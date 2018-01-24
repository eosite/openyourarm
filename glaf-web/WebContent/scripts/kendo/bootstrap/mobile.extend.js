/**
 * mobile 手机注册事件
 */

(function() {

	function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge)
			}, false);
		}
	}

	//注册回调函数，第一次连接时调用 初始化函数
	connectWebViewJavascriptBridge(function(bridge) {
		if(!bridge.init){
			return;
		}
		//初始化
		bridge.init(function(message, responseCallback) {
			var data = {
				'Javascript Responds': 'Wee!'
			};
			responseCallback(data);
		});

		//接管window.open
		var oldOpen = window.open,
			oldClose = window.close;

		window.open = function() {
			var args = [].slice.call(arguments);
			window.WebViewJavascriptBridge.callHandler('openWindow', {
				"url": args[0]
			}, function(responseData) {});
			//oldOpen.apply(this, args);
		}

		window.close = function() {
			var args = [].slice.call(arguments);
			window.WebViewJavascriptBridge.callHandler('closeWindow', {}, function(responseData) {});
			//oldClose.apply(this, args);
		}

		//接收安卓发来的消息   并返回给安卓通知
		
		//扫码回调
		bridge.registerHandler("scanCallback", function(data, responseCallback) {
		    var callback = $("body").data("scanCallback");
		    callback && callback.call(null,data);
		});
	})
})();