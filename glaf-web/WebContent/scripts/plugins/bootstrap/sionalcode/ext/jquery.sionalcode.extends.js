!function($) {
	var plugin = 'sionalcodeExt';

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
		codeContent : '',
		codeHeight : '256',
		codeMatch : 'H',

	};
	var sional;
	$.fn[plugin].methods = {
		_init : function() {
			var that = this;
			console.log(that);
			var options = that.options;
			// var $this = that.getOject.call(that);
			// $("#"+options.id).qrcode({text: "http://www.baidu.com"});
			if (options.codeContent != '') {
				var len = options.codeMatch;
				$('#' + options.id).qrcode({
					text : options.codeContent,
					width : options.codeHeight,
					height : options.codeHeight,
					correctLevel : QRErrorCorrectLevel.len
				});
			}

		},

		copyContent : function(args) {
			// 获取配置信息
			var options = this.options;
			if (args.correctLevel == '') {
				args.correctLevel = options.codeMatch;
			}
			if (args.height == '') {
				args.height = options.codeHeight;
			}
			if (args.height == '') {
				args.width = options.codeHeight;
			}
			sional = args.sionalFile;
			if (args.text != '') {
				var len = args.correctLevel;
				$(args.id).qrcode({
					text : args.text,
					width : args.height,
					height : args.height,
					correctLevel : QRErrorCorrectLevel[len]
				});

			}
		},
		downloadSional : function(args) {
			var canvas = $(args.id).find("canvas").get(0);

			var type = 'png';
			var imgData = canvas.toDataURL(type);
			// 下载后的问题名
			var filename = sional + '.' + type;

			var browser = CheckBrowser();

			if (browser[0] === 'IE') {
				window.navigator.msSaveBlob(new dataURLtoBlob(imgData),
						filename);
				return;
			}

			var _fixType = function(type) {
				type = type.toLowerCase().replace(/jpg/i, 'jpeg');
				var r = type.match(/png|jpeg|bmp|gif/)[0];
				return 'image/' + r;
			};

			// 加工image data，替换mime type
			imgData = imgData.replace(_fixType(type), 'image/octet-stream');

			var saveFile = function(data, filename) {
				
				var urlObject = window.URL || window.webkitURL || window;
				var export_blob = new Blob([ data ]);
				var save_link = document.createElementNS(
						"http://www.w3.org/1999/xhtml", "a")
				save_link.href = urlObject.createObjectURL(export_blob);
				// var save_link = document.createElement('a');
				save_link.target = "_blank";
				save_link.href = data;
				save_link.download = filename;
				var event = document.createEvent('MouseEvents');
				event.initMouseEvent('click', true, false, window, 0, 0, 0,
				 0, 0, false, false, false, false, 0, null);
				 save_link.dispatchEvent(event);
				 
			};
			
			// download
			saveFile(imgData, filename);

		}

	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});

	function CheckBrowser() {
		var ua = navigator.userAgent, browserVersion;
		ua = ua.toLocaleLowerCase();
		if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
			browserType = "IE"; // 哈哈，现在可以检测ie11.0了！
			browserVersion = ua.match(/msie ([\d.]+)/) != null ? ua
					.match(/msie ([\d.]+)/)[1] : ua.match(/rv:([\d.]+)/)[1];
		} else if (ua.match(/firefox/) != null) {
			browserType = "firefox";
		} else if (ua.match(/opera/) != null) {
			browserType = "opera";
		} else if (ua.match(/chrome/) != null) {
			browserType = "chrome";
		} else if (ua.match(/safari/) != null) {
			browserType = "Safari";
		}
		var arr = new Array(browserType, browserVersion);
		return arr;
	}

}(jQuery);