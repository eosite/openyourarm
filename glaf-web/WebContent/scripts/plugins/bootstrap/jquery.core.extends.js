/**
 * author klaus.wang
 * 
 * @param $
 */
(function($) {

	$.log = function(msg) {
		return console.log(msg || '');
	};

	$.fnInit = function(plugin, fn) {
		$.fn[plugin] = function(options, param) {
			var pluginKey = plugin + '.options';
			if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
				var p = this.data(plugin);
				return p ? p[options].apply(p, Array.prototype.slice.call(
						arguments, 1)) : $.fn[plugin].methods[options](this,
						param);
			}
			options = options || {};
			return this.each(function(i, o) {
				var state = $.data(o, pluginKey);
				if (state) {
					$.extend(true, state.options, options);
				} else {
					$.data(o, pluginKey, {
						options : $.extend(true, {}, $.fn[plugin].defaults,
								$.parser.parseOptions(this), options),
						datas : param
					});
				}
				if (fn)
					fn(o);
			});
		};
	};

	$.Init = function(plugin, fn, arguments) {
		var options = arguments[0], param = arguments[1];
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {}, optionKey = plugin + ".options";
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $.extend(true, {}, $.fn[plugin].defaults,
							$.parser.parseOptions(this), options),
					datas : param
				});
			}
			$.data(o, plugin, new fn(o));
		});
	};

	$.parser = {
		auto : true,
		onComplete : function(context) {
		},
		plugins : [],
		parse : function(context) {
			for (var i = 0; i < $.parser.plugins.length; i++) {
				var name = $.parser.plugins[i];
				var r = $('.mt-' + name, context);
				if (r.length) {
					if (r[name]) {
						r[name]();
					}
				}
			}
		},

		parseValue : function(property, value, parent, delta) {
			delta = delta || 0;
			var v = $.trim(String(value || ''));
			var endchar = v.substr(v.length - 1, 1);
			if (endchar == '%') {
				v = parseInt(v.substr(0, v.length - 1));
				if (property.toLowerCase().indexOf('width') >= 0) {
					v = Math.floor((parent.width() - delta) * v / 100.0);
				} else {
					v = Math.floor((parent.height() - delta) * v / 100.0);
				}
			} else {
				v = parseInt(v) || undefined;
			}
			return v;
		},

		parseData : function(target, key) {
			var t = $(target);
			var options = {};
			var s = $.trim(t.attr(key));
			if (s) {
				if (s.substring(0, 1) != '{') {
					s = '{' + s + '}';
				}
				options = (new Function('return ' + s))();
			}
			return options;
		},

		parseOptions : function(target, properties) {
			var t = $(target);
			var options = {};

			var s = $.trim(t.attr('data-options'));
			if (s) {
				if (s.substring(0, 1) != '{') {
					s = '{' + s + '}';
				}
				options = (new Function('return ' + s))();
			}
			$.map([ 'width', 'height', 'left', 'top', 'minWidth', 'maxWidth',
					'minHeight', 'maxHeight' ], function(p) {
				var pv = $.trim(target.style[p] || '');
				if (pv) {
					if (pv.indexOf('%') == -1) {
						pv = parseInt(pv) || undefined;
					}
					options[p] = pv;
				}
			});

			if (properties) {
				var opts = {};
				for (var i = 0; i < properties.length; i++) {
					var pp = properties[i];
					if (typeof pp == 'string') {
						opts[pp] = t.attr(pp);
					} else {
						for ( var name in pp) {
							var type = pp[name];
							if (type == 'boolean') {
								opts[name] = t.attr(name) ? (t.attr(name) == 'true')
										: undefined;
							} else if (type == 'number') {
								opts[name] = t.attr(name) == '0' ? 0
										: parseFloat(t.attr(name)) || undefined;
							}
						}
					}
				}
				$.extend(options, opts);
			}
			return options;
		}

	};

	$.transformToTreeFormat = function(a, idStr, pidStr, childrenStr) {
		var r = [], hash = {}, id = idStr, pid = pidStr, children = childrenStr, i = 0, j = 0, len = a.length;
		for (; i < len; i++) {
			hash[a[i][id]] = a[i];
		}
		for (; j < len; j++) {
			var aVal = a[j], hashVP = hash[aVal[pid]];
			if (hashVP) {
				!hashVP[children] && (hashVP[children] = []);
				hashVP[children].push(aVal);
			} else {
				r.push(aVal);
			}
		}
		return r;
	};

	$.fn.outer = function() {
		return this[0] ? this[0].outerHTML : '';
	};

	window.callbacks = $.Callbacks("fzmt.zygs"), window.resizeCallbacks //
	= $.Callbacks("fzmt.zygs.$Resize");
	window.mt = window.mt || {

	};

	window.callbacks.add($.parser.parse);

	$(document).ready(function() {
		window.callbacks.fire();
	});

	var $Resize = (function() {
		
		var func = new Function();
		
		var data_resize = "data-resize";
		
		func.resize = function(o){
			var options = $.parser.parseData(o, data_resize)//
				, opts = {};
			for ( var key in options) {
				if(!options[key])
					continue;
				try {
					opts[key] = eval(options[key]); 
				} catch (e) {
					opts[key] = options[key];
				}
			}
			$(o).css(opts);
		};
		
		func.init = function(selector){
			$(selector || "["+ data_resize +"]").each(function(i, v) {
				func.resize(v);
			});
		};
		
		return func;
	})();

	window.callbacks.add(function() {

		/**
		 * 初始化调整
		 */
		var selector = "[data-resize]";
		$Resize.init(selector);
		
		window.resizeCallbacks.add(function(){
			$Resize.init(selector);
		});

		$(window).resize(window.resizeCallbacks.fire);

	});

})(jQuery);

String.prototype.trim = function() {
	return this ? this.replace(/(^\s*)|(\s*$)/g, "") : this;
};

/**
 * eg : var str = mt.format("我叫{name},今年{age}岁",{name : '小新', age : 5})
 */
mt.format = function(str, o) {
	if (o && str) {
		var k, v;
		return str.replace(/\{\w*\}/g, function(w) {
			k = w.substring(1, w.length - 1).trim(), v = o[k];
			return v === 0 ? 0 : (v ? v : '');
		});
	}
	return str;
};

/**
 * alert("我叫{name},今年{age}岁,现在读{0}".format("幼儿园", {name : '小新', age : 5}));
 * 
 * @returns {String}
 */
String.prototype.format = function() {
	var str = this, arg;
	if (str) {
		for (var i = 0; i < arguments.length; i++) {
			arg = arguments[i];
			if (typeof arg === 'object') {
				str = mt.format(str, arg);
			} else {
				str = str.replace(new RegExp("\\{" + (i) + "\\}", "g"), arg);
			}
		}
	}
	return str;
};

function StringBuffer(str) {
	this.collection = new Array();
	this.appendFormat.apply(this, arguments);
}

StringBuffer.prototype = {
	constructor : StringBuffer,
	size : function() {
		return this.collection.length;
	},
	append : function(str) {
		this.collection.push(str);
		return this;
	},
	appendFormat : function() {
		if (arguments.length == 0)
			return this;
		var str = arguments[0];
		if (arguments.length > 1)
			str = str.format.apply(str, Array.prototype.slice
					.call(arguments, 1));
		return this.append(str);
	},
	toString : function(split) {
		return this.collection.join(split ? split : '');
	},
	clear : function() {
		this.collection = [];
	}
};

(function(window) {

	var $ajax = {
		url : '',
		type : 'POST',
		dataType : 'JSON',
		data : {},
		contentType : "application/json",
		success : function(r) {
		},
		async : true
	};

	var defaults = {
		read : $.extend(true, {}, $ajax),
		save : $.extend(true, {}, $ajax),
		destroy : $.extend(true, {}, $ajax),
		parameterMap : function(options) {
			return options;
		},
		schema : {
			total : 'total',
			data : 'data',
			errors : 'error'
		},
		onRequest : function(data) {

		}
	};

	function DataSource(options) {

		this.datas = options.data;

		this.options = $.extend(true, {}, defaults, options || {});

		this.crud = {
			deletes : [],
			saves : [],
			collection : [],
			collectionObj : {}
		};

		this.uidKey = "__uuid__";

		this.indexKey = "row-index";

		this.init();

	}

	DataSource.prototype = {
		constructor : DataSource,
		init : function() {
			this.datas && (this.addAll(this.datas));
		},
		_ajaxData : function(models) {
			return {
				models : models
			};
		},
		_getParameterMap : function(data) { // 调用方option
			return this.options.parameterMap(data);
		},
		_request : function(opts, sfn, efn) {
			var that = this;
			var ajax = that.options;

			sfn = ajax.__fn || sfn || (sfn = opts.__success);
			efn = efn || (efn = opts.__error);
			ajax.__fn = null;

			$.extend(opts, {
				success : function(ret) {
					that.transdata(ret, sfn);
				},
				error : function(ret) {
					!!efn && (efn.call(that, ret));
				}
			});
			$.ajax(opts);
		},
		transdata : function(ret, fn) {
			var datas = null, total = 0, a = this.options;
			if (ret) {
				if (ret instanceof Array) {
					datas = ret;
					total = datas.length;
				} else if (ret instanceof Object) {
					datas = ret[a.schema.data];
					total = ret[a.schema.total];
				}
			}
			!!fn && (fn.call(this, ret, datas, total));
		},
		query : function(params, sfn, efn) {
			var opts = this.options.read;
			!opts.__success && (opts.__success = (sfn || opts.success))
					&& (opts.__error = (efn || opts.error));
			opts.data = this._getParameterMap(params);

			this._request(opts, sfn, efn);
		},
		_async : function(opts, type) {
			var collection = this.crud[type];
			if (collection && collection.length) {
				!opts.data__ && (opts.data__ = $.extend({}, opts.data));
				$.extend((opts.data = {}), this._ajaxData(collection),
						opts.data__);

				this.options.onRequest(opts.data);
				opts.data = this._getParameterMap(opts.data); // 调用方option

				this._request(opts);
				this.crud[type] = [];
			}
			return this;
		},
		async : function(fn) {
			var ajax = this.options;
			ajax.__fn = fn;
			this._async(ajax.save, "saves");
			this._async(ajax.destroy, "deletes");
		},
		save : function(eles, fn) {
			this.options.save.__success = fn;

			return this._addAll(eles, this.crud.saves);
		},
		destroy : function(eles, fn) {

			this.options.destroy.__success = fn;

			return this._addAll(eles, this.crud.deletes);
		},
		_addAll : function(eles_, collection) {
			var eles = (eles_ instanceof Array) ? eles_ : [ eles_ ];
			var i, len = eles.length;
			if (eles && (len = eles.length)) {
				for (i = 0; i < len; i++) {
					collection.push(eles[i]);
				}
			}
			return this;
		},
		add : function(ele, fn) {
			if (!ele)
				return this;
			var uid = this.getEleUid(ele);
			var collectionObj = this.crud.collectionObj;
			if (!uid || (uid in collectionObj)) {
				ele[this.uidKey] = uid = window.getUid();
			}
			this.crud.collectionObj[uid] = ele;
			ele[this.indexKey] = this.crud.collection.push(ele) - 1;
			!!fn && fn(ele);
			return this;
		},
		addAll : function(eles, fn) {
			var i, len = eles.length;
			if (eles && (len = eles.length)) {
				for (i = 0; i < len; i++) {
					this.add(eles[i], fn);
				}
			}
			return this;
		},
		data : function() {
			return this.crud.collection;
		},
		remove : function(ele) {
			if (!ele)
				return this;
			var uid = this.getEleUid(ele);
			var collectionObj = this.crud.collectionObj;
			if (uid && (uid in collectionObj)) {
				this.crud.deletes.push(ele);
				this.crud.collection.remove(ele);
				delete (collectionObj[uid]);
			}
			return this;
		},
		getByUid : function(uid) {
			return this.crud.collectionObj[uid];
		},
		getEleIndex : function(ele) {
			return ele ? ele[this.indexKey] : null;
		},
		getEleUid : function(ele) {
			return ele ? ele[this.uidKey] : null;
		}
	};

	mt.DataSource = DataSource;
})(window);

/**
 * 定时器
 * 
 * IntervalProcess.wait(function(){ return !!window.$; }, function(){ exec... },
 * 1000, 60);
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
			try {
				fn && filter && //
				(isOk = filter()) && fn();
			} catch (e) {
				console.log(e);
				isOk = true;
			}
			if (time > times || isOk) {
				func.clear(interval);
			} else
				console.log(++time);
		}, timeOut || 500);
	};
	return func;
})();