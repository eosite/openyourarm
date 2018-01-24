/**
 * [Debugger调式封装]
 *  mtDebugger.log('foo');   // 白底黑字
 *  mtDebugger.info('bar');  // 白底紫字
 *  mtDebugger.debug('oh');  // 白底黄字
 *  mtDebugger.warn('foo');  // 黄底黄字
 *  mtDebugger.error('bar'); // 红底红字
 *  mtDebugger.table([{a:1,b:2},{a:3,b:4}]); // 表格模式
 * 
 *  #mtDebugger 对象
 *  	isDebug： 是否为debugger模式
 *  	vc：封装的调试器主面板对象
 * @param  {[type]} global [window对象]
 * @return {[obj]}        [debugger日志调式器]
 */
(function(global, _$) {
	var vc = global.vConsole,
		c = global.console,
		tool,
		$,
		store = {
			reqList: {},

		},
		isDebug = global && vc,
		suffix = "mtvconsole",
		idSuffix = "mtdebug",
		seq = 1,
		funs = {
			isDebug: isDebug,
			vc: vc,
			queue: [],
			store: store
		},
		_open = global.XMLHttpRequest.prototype.open,
		_send = global.XMLHttpRequest.prototype.send;

	function str2json(param, flag) {
		if (param) {
			if (typeof param == "string") {
				try {
					param = JSON.parse(param);
				} catch (e) {

				}
			}
			if (typeof param == "object") {
				return param;
			}
			if (flag) {
				return param;
			}
		}
		return null;
	}
	if (isDebug) {
		(tool = vc.tool) && ($ = vc.$);
		/**
		 * 劫持ajax
		 * @return {[type]} [description]
		 */
		window.XMLHttpRequest.prototype.send = function() {
			var XMLReq = this,
				args = [].slice.call(arguments),
				param = args[0],
				id = XMLReq._requestID,
				item = store.reqList[id] || (store.reqList[id] = {});
			item["param"] = str2json(param);
			return _send.apply(XMLReq, args);
		}
		window.XMLHttpRequest.prototype.open = function() {
			var XMLReq = this,
				args = [].slice.call(arguments),
				url = args[1],
				id = idSuffix + (++seq);
			XMLReq._requestID = id;
			var _onreadystatechange = XMLReq.onreadystatechange || function() {};
			XMLReq.onload = XMLReq.onreadystatechange = function() {
				var item = store.reqList[id] || (store.reqList[id] = {});
				// update status
				item.url = url;
				item.readyState = XMLReq.readyState;
				if (XMLReq.readyState == 4 && item.param && item.param["rid"]) {
					item.response = XMLReq.response;
					if (XMLReq.status == 200) {
						if (typeof item.response == "string") {
							console.info($.render(formatTmp["ajaxStrTmp"].join(""), {
								rid: item.param["rid"],
								dvar: window.debuggerVar_
							}, true));
							console.info(str2json(item.response, true));
						}
					} else {
						console.error(XMLReq.response);
					}
				}
				return _onreadystatechange.apply(XMLReq, arguments);
			};
			return _open.apply(XMLReq, args);
		};
	}

	/**
	 * 获取数组中的对象，不重复的key
	 * exp: [{a:1,b:2},{b:3,c:4}] -> ["a","b","c"]
	 * @param  {[type]} arys [description]
	 * @return {[type]}      [description]
	 */
	function getKeys(arys) {
		var keys = [],
			obj,
			nKeys;
		for (var i = 0; i < arys.length; i++) {
			obj = arys[i];
			nKeys = vc.tool.getObjAllKeys(obj);
			nKeys.forEach(function(el, index) {
				keys.indexOf(el) == -1 && keys.push(el);
			});
		}
		return keys;
	};

	/**
	 * 构建表格样式
	 * @param  {[type]} schema [description]
	 * @param  {[type]} datas  [description]
	 * @return {[type]}        [description]
	 */
	function buildTable(schema, datas) {
		var html = "<table width='100%' border-spacing='0'>";
		html += "<tr style='background-color:#ADADAD;'>";
		html += "<th width='30px;'>行号</th>";
		schema.forEach(function(s) {
			html += "<th>" + s + "</th>";
		})
		html += "</tr>";
		datas.forEach(function(data, i) {
			html += "<tr>";
			html += "<td style='border:1px solid rgb(221, 238, 245);'>" + (i + 1) + "</td>";
			schema.forEach(function(s) {
				html += "<td style='border:1px solid rgb(221, 238, 245);'>" + (data[s] || "") + "</td>";
			})
			html += "</tr>";
		})
		return html += "</table>";
	}

	/**
	 * [用法和chrome console.table一致]
	 * @param  {[type]} arys [description]
	 * @param  {[type]} arys [description]
	 * @return {[type]}      [description]
	 */
	funs.table = function(arys, showNames) {
		if (!isDebug)
			return;
		if (vc.tool.isArray(arys)) {
			console.table && console.table(arys);
			var keys = getKeys(arys);
			c.vhtml(buildTable(keys, arys));
		} else {
			c.log(arys, showNames);
		}
	};

	/**
	 * 模板对象
	 * @type {Object}
	 */
	var formatTmp = { in : [ /*"输入控件:{{dvar[id] || id}}", "获取方法:{{dvar[method] || method}}", */ "获取途径:{{dvar[columnName] || columnName}}", "获取值:{{param}}"],
			out: ["输出控件:{{dvar[id] || id}}", "执行方法:{{dvar[method] || method}}"],
			trigger: ["触发控件:{{dvar[eleId] || eleId}}", "触发事件:{{dvar[eventType] || eventType}}"],
			tplFold: ['<div class="vc-fold">' +
				'<i class="vc-code-key">{{ dvar[key] || key}}</i>: <i class="vc-code-{{valueType}}">{{value}}</i>' +
				'</div>'
			],
			tplFoldCode: ['<span>' +
				'<i class="vc-code-key{{if (keyType)}} vc-code-{{keyType}}-key{{/if}}">{{dvar[key] || key}}</i>: <i class="vc-code-{{valueType}}">{{value}}</i>' +
				'</span>'
			],
			ajaxStrTmp: ["控件:{{dvar[rid] || rid}},返回结果如下:"],
			outLinkTmp: ["输出控件：{{dvar[id] || id}},执行< {{dvar[outType] || outType}} >,{{dvar[message] || message}}"]
		},
		separator = "\n";

	/**
	 * 渲染HTML模板
	 * @param  {[type]} type [description]
	 * @param  {[type]} obj  [description]
	 * @return {[type]}      [description]
	 */
	function formatHtml(type, obj) {
		var html = "";
		if (obj[type]) {
			if (vc.tool.isArray(obj[type])) {
				obj[type].forEach(function(cobj) {
					cobj['dvar'] = window.debuggerVar_;
					html += vc.$.render(formatTmp[type].join("<br>"), cobj, true) + "\n" + separator;
				});
			} else {
				var cobj = obj[type];
				cobj['dvar'] = window.debuggerVar_;
				html += vc.$.render(formatTmp[type].join("<br>"), cobj, true) + "\n" + separator;
			}
		}
		return html;
	};

	/**
	 * 可扩展对象，数组
	 * @param  {[type]} obj   [description]
	 * @param  {[type]} outer [description]
	 * @return {[type]}       [description]
	 */
	function getFoldedLine(obj, outer) {
		var tplFold = formatTmp.tplFold.join(""),
			tplFoldCode = formatTmp.tplFoldCode.join(""),
			id = suffix + (++seq);
		if (!outer) {
			var json = tool.JSONStringify(obj);
			var preview = json.substr(0, 26);
			outer = tool.getObjName(obj);
			if (json.length > 26) {
				preview += '...';
			}
			outer += ' ' + preview;
		}
		var $line = $.render('<div class="vc-fold mt-vc-fold" id="' + id + '"><i class="vc-fold-outer">{{outer}}</i><div class="vc-fold-inner"></div></div>', {
			outer: outer
		});
		_$(document).on('click', '#' + id + ">.vc-fold-outer", function(e) {
			e.preventDefault();
			e.stopPropagation();
			var $that = _$(this);
			$fold = $that.closest('.mt-vc-fold');
			if ($that.hasClass('vc-toggle')) {
				$that.removeClass('vc-toggle');
				$fold.find('.vc-fold-inner').removeClass('vc-toggle');
				$fold.find('.vc-fold-outer').removeClass('vc-toggle');
			} else {
				$that.addClass('vc-toggle');
				$fold.find('.vc-fold-inner').addClass('vc-toggle');
				$fold.find('.vc-fold-outer').addClass('vc-toggle');
			}
			var $content = $fold.find('.vc-fold-inner')[0];
			if ($content.children.length == 0 && !!obj) {
				// render object's keys
				var keys = tool.getObjAllKeys(obj);
				for (var i = 0; i < keys.length; i++) {
					if (keys[i] == "remove" || keys[i] == "removeObj")
						continue;
					var val = obj[keys[i]],
						valueType = 'undefined',
						keyType = '',
						$obj;
					// handle value
					if (tool.isString(val)) {
						valueType = 'string';
						val = '"' + val + '"';
					} else if (tool.isNumber(val)) {
						valueType = 'number';
					} else if (tool.isBoolean(val)) {
						valueType = 'boolean';
					} else if (tool.isNull(val)) {
						valueType = 'null';
						val = 'null';
					} else if (tool.isUndefined(val)) {
						valueType = 'undefined';
						val = 'undefined';
					} else if (tool.isFunction(val)) {
						valueType = 'function';
						val = 'function()';
					} else if (tool.isSymbol(val)) {
						valueType = 'symbol';
					}
					// render
					var $sub;
					if (tool.isArray(val)) {
						var name = tool.getObjName(val) + '[' + val.length + ']';
						$sub = getFoldedLine(val, $.render(tplFoldCode, {
							key: keys[i],
							keyType: keyType,
							value: name,
							valueType: 'array',
							dvar: window.debuggerVar_
						}, true));
					} else if (tool.isObject(val)) {
						var name = tool.getObjName(val);
						$sub = getFoldedLine(val, $.render(tplFoldCode, {
							key: tool.htmlEncode(keys[i]),
							keyType: keyType,
							value: name,
							valueType: 'object',
							dvar: window.debuggerVar_
						}, true));
					} else {
						if (obj.hasOwnProperty && !obj.hasOwnProperty(keys[i])) {
							keyType = 'private';
						}
						var renderData = {
							lineType: 'kv',
							key: tool.htmlEncode(keys[i]),
							keyType: keyType,
							value: tool.htmlEncode(val),
							valueType: valueType,
							dvar: window.debuggerVar_
						};
						$sub = $.render(tplFold, renderData);
					}
					$fold.find('>.vc-fold-inner').append($sub);
				}
			}
			return false;
		});
		return $line.outerHTML;
	}

	function paramLine(params) {
		var html = "";
		_$.each(params, function(index, val) {
			html += getFoldedLine(val || {});
		});
		return html;
	}

	/**
	 * 事件定义器输入输出关系列表
	 * @param  {[type]} arys [description]
	 * @return {[type]}      [description]
	 */
	funs.__etable = function(arys) {
		if (!isDebug)
			return;
		if (vc.tool.isArray(arys)) {
			var nArys = [],
				nObj;
			arys.forEach(function(obj) {
				nObj = {};
				nObj["输入控件"] = formatHtml("in", obj);
				nObj["输出控件"] = formatHtml("out", obj);
				nObj["触发控件"] = formatHtml("trigger", obj);
				nObj["参数"] = paramLine(obj["param"] || []) /*getFoldedLine(obj["param"] || [])*/ ;
				nObj["表达式"] = obj["exp"];
				nArys.push(nObj);
			});
			funs.table(nArys);

			var queAry = [],
				queObj = {};
			funs.queue.forEach(function(que) {
				if (que.tmplId && formatTmp[que.tmplId]) {
					que.param["dvar"] = window.debuggerVar_ || {};

					if (que.param && typeof que.param === 'object' && !isNaN(que.param.length)) {
						//如果有参数是
						queObj = {};
						_$.each(que.param, function(i, item) {
							if (typeof item.value == 'object')
								queObj[item.name] = getFoldedLine(item.value);
							else {
								var tmp = formatTmp[item.tmplId];
								queObj[item.name] = $.render(_$.isArray(tmp) ? tmp.join("") : tmp, item, true);
							}
						});
						queAry.push(queObj);
					} else {
						if (typeof que.param.value == 'object')
							queObj[que.param.name] = getFoldedLine(que.param.value);
						else {
							var tmp = formatTmp[que.tmplId];
							queObj[que.param.name] = $.render(_$.isArray(tmp) ? tmp.join("") : tmp, que.param, true);
						}
					}

					// funs[que.type]($.render(formatTmp[que.tmplId],que.param,true));
				} else {
					funs[que.type](que.param);
				}
			});

			if (queAry.length > 0) {
				funs.table(queAry);
			}
			funs.queue.length = 0;
		}
	};

	/**
	 * 简单的日志打印
	 * @param  {[type]} a [description]
	 * @return {[type]}   [description]
	 */
	funs.log = function(a) {
		if (!isDebug)
			return;
		c.log(a);
	};
	funs.info = function(a) {
		if (!isDebug)
			return;
		c.info(a);
	}
	funs.debug = function(a) {
		if (!isDebug)
			return;
		c.debug(a);
	}
	funs.warn = function(a) {
		if (!isDebug)
			return;
		c.warn(a);
	}
	funs.error = function(a) {
		if (!isDebug)
			return;
		c.error(a);
	}

	/**
	 * 事件定义器执行完打印队列
	 * exp: mtDebugger.pushQueue("log","我是打印") ;
	 * 		mtDebugger.pushQueue("log","tmpId",{a:1}) ;
	 * @param  {[type]} type  [description]
	 * @param  {[type]} param [description]
	 * @return {[type]}       [description]
	 */
	funs.pushQueue = function(type, tmplId, param) {
		var args = [].slice.call(arguments);
		if (args.length == 3) {
			funs.queue.push({
				type: type,
				tmplId: tmplId,
				param: param
			})
		} else if (args.length == 2) {
			param = tmplId;
			funs.queue.push({
				type: type,
				param: param
			})
		}
	};

	/**
	 * 添加模板
	 * @param {[type]} tmplId  [description]
	 * @param {[type]} tmplStr [description]
	 */
	funs.addTmpl = function(tmplId, tmplStr) {
		if (typeof tmplStr === "string") {
			formatTmp[tmplId] = tmplStr;
		}
	};

	global.mtDebugger = funs;
})(window, jQuery);

var operatorTmpl = "{{value}}"; //操作名称
var requestParamsTmpl = "{{value}}"; //请求参数
var responseResultTmpl = "{{value}}"; //返回结果
var responseStatusTmpl = "{{if (value)}}<div style='color:blue'>成功</div>{{else}}<div style='color:red'>失败</div>{{/if}}"; //结果状态
mtDebugger.addTmpl('operatorTmpl', operatorTmpl);
mtDebugger.addTmpl('requestParamsTmpl', requestParamsTmpl);
mtDebugger.addTmpl('responseResultTmpl', responseResultTmpl);
mtDebugger.addTmpl('responseStatusTmpl', responseStatusTmpl);