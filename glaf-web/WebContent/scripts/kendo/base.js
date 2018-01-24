var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
var jqCb = $.Callbacks(),
	mapCb = $.Callbacks();
//eg: pubsub.pub("linkageControl",datas);
/**
 *从页面级参数中获取
 */
function getParam(id, param) {
	if (pageParameters) {
		var paramObj = pageParameters,
			detail = paramObj['detail'],
			data, obj, v;
		if (detail && (data = detail['data'])) {
			obj = data[id];
			if (obj && obj.length) {
				for (var i = 0, length = obj.length; i < length; i++) {
					v = obj[i];
					if (v.param == param) {
						//return detail[v.inparam];
						return v.inparam ? detail[v.inparam] : v.value;
					}
				}
			}
		}
	}
	return null;
}

function getParams(cid) {
	if (pageParameters) {
		var paramObj = pageParameters,
			detail = paramObj['detail'],
			data, obj, v, retVal = {};
		if (detail && (data = detail['data'])) {
			if (cid) {
				obj = data[cid];
				if (obj && obj.length) {
					for (var i = 0, length = obj.length; i < length; i++) {
						v = obj[i];
						//retVal[v.param] = detail[v.inparam];
						retVal[v.param] = v.inparam ? detail[v.inparam] : v.value;
					}
				}
			} else {
				for (var id in data) {
					obj = data[id];
					if (obj && obj.length) {
						for (var i = 0, length = obj.length; i < length; i++) {
							v = obj[i];
							// 增加 如果是数据集中转发的参数 则从数据集转发中获取
							if (v.type == "__defaultValue__") {
								retVal[v.param] = v.columnName;
							} else {
								retVal[v.param] = v.inparam ? detail[v.inparam] : v.value;
							}

						}
					}
				}
			}
			return retVal;
		}
	}
	return null;
}
var pubsub = (function() {
	var q = {
			elongateStockIndex: null
		},
		roles = {},
		subUid = -1;
	/**
	 *  输出参数为对象或者数组时转换
	 * @param  {[object]} rule [规则]
	 * @param  {[object]} obj  [输出参数的对象]
	 * @param  {[object]} o    [数据对象]
	 * @param  {[object]} o2    [数据对象2]
	 * @return {[void]}      [无返回值]
	 */
	q.outParamsObj = function(rule, obj, o, o2) {
		var isObj = obj && $.isPlainObject(obj),
			isAry = obj && $.isArray(obj);
		o || (o = {});
		o2 || (o2 = {});
		isObj && (obj[rule.param] = o[rule.columnName] || o2[rule.columnName] || "");
		if (isAry) {
			var i = this;
			obj[i] || (obj[i] = {});
			obj[i][rule.param] = o[rule.columnName] || o2[rule.columnName] || "";
		}
	};
	q.inParamsObj = function(rule, o, o2) {
		var obj;
		o || (o = {});
		o2 || (o2 = {});
		if (rule.idataId) {
			obj = o[rule.idataId] || o2[rule.idataId];
		}
		return obj;
	};
	/**
	 * 转义
	 * @param  {[type]} str [description]
	 * @return {[type]}     [description]
	 */
	var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"','mdash':'-','#39':'\''};
	function escape2Html(str) {
 		return str.replace(/&(lt|gt|nbsp|amp|quot|mdash|#39);/ig,function(all,t){return arrEntities[t];});
	};
	/**
	 * 转义还原
	 * @param  {[type]} sHtml [description]
	 * @return {[type]}       [description]
	 */
	function html2Escape(sHtml) {
 		return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
	};
	q.htmlEscape = function(str){
		return html2Escape(str);
	};
	/**
	 * 转义 适用于 $().val() 的
	 * @param  {[type]} str [description]
	 * @return {[type]}     [description]
	 */
	q.htmlUnescape = function(str){
		return escape2Html($("<div></div>").append(str).html());
	};
	/**
	 * 转义 适用于 $().html() 的
	 * @param  {[type]} str [description]
	 * @return {[type]}     [description]
	 */
	q.htmlUnescape4Html = function(str){
		return $("<div></div>").append(str).html().replace(/&(amp|nbsp|mdash|#39);/ig,function(all,t){return arrEntities[t];});
	};
	q.execChilds = function(rule) {
		var childs = rule.childs;
		childs && pubsub._execOps(childs, rule.trigger, null, null);
	};
	q.getThat = function(rs, isIn) {
		var that = window,
			tlevel;
		if (typeof rs == "object") {
			var rules = [];
			if ($.isArray(rs)) {
				rules = rs;
				tlevel = rs.tlevel;
			} else {
				rules.push(rs);
			}
			var rule = rules[0],
				lev = isIn ? rule.inlev : rule.outlev,
				pageId = isIn ? rule.inpage : rule.outpage;
			if (lev) {
				// 输入or输出控件层数 - 触发事件控件层数
				var t = lev - (tlevel || rule.tlevel || pageLevel);
				if (t > 0) { // 往下找
					that = q.getNextLevel(that, t, pageId, isIn);
					if (that)
						return that;
					else
						return null;
				} else if (t < 0) { // 往上找
					that = q.getPrevLevel(that, -t);
					return that;
				} else { // 处在同级
					var pageParams = pageParameters,
						thisPageId = pageParams.id;
					if (pageId == thisPageId) { // 如果在同一页面内
						return that;
					} else { // 处在同级 但是在不同页面

					}
				}
			} else {
				return that;
			}
		} else {
			return that;
		}
	};
	q.getPrevLevel = function(that, t) {
		for (var i = 0; i < t; i++) {
			that = that.opener || that.parent;
		}
		return that;
	};
	q.getNextLevel = function(that, t, pageId, container) {
		var iframes, iframe, ct = (typeof container === "boolean") ? "body" : container;
		if (t == 1) {
			iframe = that.$('iframe[src*="' + pageId + '"]', ct);
		} else {
			iframes = that.$('iframe', ct);
			var iframeContents;
			iframeContents = iframes.contents();
			while (t > 2) {
				t--;
				iframeContents = iframeContents.contents();
			}
			iframe = iframeContents.find('iframe[src*="' + pageId + '"]');
		}
		if (iframe && iframe[0]) {
			that = iframe[0].contentWindow;
			return that;
		}
		// return null ;
	};
	q.getJQObj = function(rs, isIn) {
		var $ret = null;
		if (typeof rs == "object") {
			var rules = [];
			if ($.isArray(rs)) {
				rules = rs;
			} else {
				rules.push(rs);
			}
			var rule = rules[0],
				id = isIn ? (rule.iid || rule.inid) : (rule.oid || rule.outid);
			var that = q.getThat(rs, isIn);
			if (that) {
				$ret = that.$("#" + id);
			}
			//如果是grid内联事件
			if ((isIn && rule.ie) || (!isIn && rule.oe)) {
				$ret = $ret.find("[name=" + rule[isIn ? "inid" : "outid"] + "]");
			}
		} else {
			$ret = $("#" + rs);
		}
		//计算变长区偏移量
		if ($ret && $ret.attr("columnname") && q.elongateStockIndex) {
			$ret = $("[columnname=" + $ret.attr("columnname") + "]").eq(q.elongateStockIndex);
		}

		return $ret;
	};
	q.getRole = function(id) {
		// 去除数字
		return id.replace(/\d|\_|\-/g, "");
	};
	q.paramsToStr = function(params, exclude) {
		if (exclude) {
			var excludeParams = {};
			for (var p in params) {
				if ($.inArray(p, exclude) == -1) {
					excludeParams[p] = params[p];
				}
			}
			params = excludeParams;
		}
		return "&" + (window.BASE64?"__mt__="+encodeURIComponent(BASE64.encoder(JSON.stringify(params))) : $.param(params));
	};
	q.expConvert = function(exp, params, toEscaping) {
			if (params) {
				var escapingStr = "\"",
					reger, val, str;
				for (var p in params) {
					reger = new RegExp(p, "gm");
					val = params[p];
					str = (toEscaping && ($.isNumeric(val) || $.isArray(val) || $.isPlainObject(val)) ? "" : escapingStr);
					if ($.isArray(val) || $.isPlainObject(val)) {
						val = JSON.stringify(val);
					}else if(typeof val == 'string'){
						//文本域中带有换行符会导致使用eval()时异常。需要进行转义
						val = val.replace(/(\r\n)|(\n)/g,'\\n');
					}
					exp = exp.replace(reger, /*typeof val == "boolean" ? val : */(str + val + str));
				}
			}
			return exp.replace(/\\"/gm, "'");
		},
		q.variableLength = function(out, cellExp, outFuncs, cindex, args, tflag) {
			var exp = cellExp.exp.replace(/\{area\}/ig, cindex);
			var obj = eval(exp);
			if (obj == "NaN")
				obj = "";
			var outissplits = out.outid.split("_");
			tflag ? outissplits[2] = cindex : outissplits[1] = cindex;
			out.outid = outissplits.join("_");
			outFuncs[out.eventType](out, obj, args);
		},
		/**
		 * 根据规则获取参数
		 */
		q.getParameters = function(rules) {
			// TODO
			var args = Array.prototype.slice.call(arguments, 1); // 这个东东获取不明确...
			if (rules) {
				var id, ruleArray, params = new Object(),
					that = this;
				for (id in rules) {
					ruleArray = rules[id];
					$.each(ruleArray, function(i, r) {
						// 根据输入控件id获取其所属角色role
						var inRole = (r.inid.length < 31 && r.inid.indexOf("/") == -1) ? that.getRole(r.inid) : 'page';
						if (!roles[inRole]) {
							return false;
						}
						// 根据控件角色获取控件 事件方法
						var inFuncs = roles[inRole][0]['funcs'];
						if (typeof inFuncs[r.type] == 'function') {
							var p = inFuncs[r.type](r, args);
							if (p) {
								params[r.param] = p;
							}
						}
					});
				}
				return params;
			}
			return null;
		};
	q._getInParams = function(expds, tlevel) {
		var eobj = {};
		if (expds && expds.length) {
			for (var kk = 0; kk < expds.length; kk++) {
				var ed = expds[kk];
				ed.tlevel = tlevel;
				// 根据输入控件id获取其所属角色role
				//var inRole = (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
				var inRole = ed.ie ? this.getRole(ed.ie) : (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
				if (!roles[inRole]) { // 找不到方法跳过
					continue;
				}
				// 根据控件角色获取控件 事件方法
				var inFuncs = roles[inRole][0]['funcs'];
				if (typeof inFuncs[ed.type] == 'function') {
					try {
						var p = inFuncs[ed.type](ed, []);
						//if (p) {
						eobj[ed.param] = p;
						//}
					} catch (e) {
						console.log(e);
					}
				}
			}
		}
		return eobj;
	}
	q._execOps = function(ops, trigger, rules, args) {
		var tlevel = trigger.level,
			outp, pars, par, expdata, exp, expds, expd, cellExp, childs,
			dbObj;
		for (var ii = 0, ol = ops.length, op; ii < ol; ii++) {
			dbObj = {
				"trigger": trigger
			};
			pubsub.dbAry.push(dbObj);
			op = ops[ii];
			outp = op['out']; //输出控件
			pars = op['param']; //输入输出关系
			// 增加了表达式验证执行
			expdata = op['expdata']; //表达式
			exp = expdata.exp; // 验证表达式
			expds = expdata.expdata; // 数据属性
			cellExp = op['cellExp']; //cell表达式
			if (expds) {
				var eobj = {};
				for (var kk = 0; kk < expds.length; kk++) {
					var ed = expds[kk];
					ed.tlevel = tlevel;
					// 根据输入控件id获取其所属角色role
					var inRole = ed.ie ? this.getRole(ed.ie) : (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
					if (!roles[inRole]) { // 找不到方法跳过
						continue;
					}
					// 根据控件角色获取控件 事件方法
					var inFuncs = roles[inRole][0]['funcs'],
						edType = ed.type;
					if (inRole == "page") {
						edType.indexOf("getRow") == 0 && (edType = "getRow");
						edType.indexOf("getValue") == 0 && (edType = "getValue");
					}
					if (typeof inFuncs[edType] == 'function') {
						try {
							ed.trigger = trigger;
							var p = inFuncs[edType](ed, args);
							//if (p) {
							eobj[ed.param] = p;
							//}
						} catch (e) {
							console.log(e);
						}
					} else {

					}
				}
				// 替换表达式 数据
				exp = q.expConvert(exp, eobj);
			}
			// 执行表达式
			dbObj["exp"] = exp;
			if (exp != "") {
				var exps = eval(exp.replace(/\n/g,"\\n"));
				if (!exps) {
					continue;
				}
			}

			// 处理没有输入输出参数的其他事件方法
			for (var jj = 0; jj < outp.length; jj++) {
				var out = outp[jj];
				out.tlevel = tlevel;
				if (!pars || !pars[out.outid]) {
					var outRole = (out.outid.length < 31 && out.outid.indexOf("/") == -1) ? this.getRole(out.oid || out.outid) : 'page';
					var outFuncs = roles[outRole][0]['funcs'];
					if (typeof outFuncs[out.eventType] == 'function') {
						var obj = {};
						if (cellExp) { // 执行  cell 表达式 
							var cell = op['cell'];
							if (cell) { //变长区
								var tflag = (cell.d == "h");
								var target = mtxx.e.target,
									tid = target.id,
									idIndexs = tid.split("_"); //点击事件
								var index = tflag ? idIndexs[2] : idIndexs[1];
								var cindex = tflag ? cell.c : cell.r;
								if (cindex <= index) { //当前点击是属于变长区内变量
									this.variableLength(out, cellExp, outFuncs, index, args, tflag);
								} else { //当前点击控件不是变长区控件
									//获取out同变长区数量
									var areas = $("[name=" + $('#' + out.outid).attr('name') + "]"),
										areaLength = areas.length,
										area, areaId, areasplits;
									for (var m = 0; m < areaLength; m++) {
										area = areas[m];
										areaId = area.id, areasplits = areaId.split("_");
										cindex = tflag ? areasplits[2] : areasplits[1];
										this.variableLength(out, cellExp, outFuncs, cindex, args, tflag);
										cindex++;
									}
								}
							} else { //非变长cell公式计算
								obj = eval(cellExp.exp);
								outFuncs[out.eventType](out, obj, args);
							}
						} else {
							//args && (obj = args);
							try {
								out.childs = op['childs'];
								out.tlevel = tlevel;
								out.trigger = trigger;
								outFuncs[out.eventType](out, obj, args);
							} catch (e) {
								console.log(e);
							}
						}
					}
				}
			}
			var isCallback = false;
			//获取输入参数对象
			dbObj["in"] = [];
			dbObj["param"] = [];
			dbObj["out"] = [];
			// 执行参数传递工作
			for (var id in pars) {
				par = pars[id];
				var obj = {},
					aryFlag = [],
					expObj = {},
					insExpEval;
				for (var i = 0; i < par.length; i++) {
					var r = par[i],
						isExpFunc = false;
					r.tlevel = tlevel;
					if (r.insExp) { //表达式计算参数传递
						var ins = r.ins,
							isExpFunc = true;
						if (ins) {
							var insLength = ins.length,
								rc;
							for (var m = 0; m < insLength; m++) {
								rc = ins[m];
								rc.tlevel = tlevel;
								// 根据输入控件id获取其所属角色role
								var inRole = rc.ie ? this.getRole(rc.ie) : (rc.inid.length < 31 && rc.inid.indexOf("/") == -1) ? this.getRole(rc.iid || rc.inid) : 'page';
								if (!roles[inRole]) {
									continue;
								}
								// 根据控件角色获取控件 事件方法
								var inFuncs = roles[inRole][0]['funcs'],
									rcType = rc.type;
								if (inRole == "page") {
									rcType.indexOf("getRow") == 0 && (rcType = "getRow");
									rcType.indexOf("getValue") == 0 && (rcType = "getValue");
								}
								if (typeof inFuncs[rcType] == 'function') {
									try {
										rc.trigger = trigger;
										var p = inFuncs[rcType](rc, args, obj[rc.dataId]);
										//if (p) {
										expObj[rc.param] = p;
										//}
										dbObj["in"].push({
											id: rc.iid || rc.inid,
											method: rc.type,
											param: p,
											columnName: rc.columnName
										});
									} catch (e) {
										console.error(e);
									}
								} else {
									dbObj["in"].push({
										id: rc.iid || rc.inid,
										method: rc.type,
										type: "none",
										param: p,
										columnName: rc.columnName
									});
								}
							}
						}
					} else {
						// 根据输入控件id获取其所属角色role					
						var inRole = r.ie ? this.getRole(r.ie) : (r.inid.length < 31 && r.inid.indexOf("/") == -1) ? this.getRole(r.iid || r.inid) : 'page';

						if (!roles[inRole]) {
							continue;
						}
						// 根据控件角色获取控件 事件方法
						var inFuncs = roles[inRole][0]['funcs'],
							rType = r.type;
						if (inRole == "page") {
							rType.indexOf("getRow") == 0 && (rType = "getRow");
							rType.indexOf("getValue") == 0 && (rType = "getValue");
						}
						if (typeof inFuncs[rType] == 'function') {
							try {
								if (r.dataId) {
									(r.dataId.indexOf("obj") != -1) && (obj[r.dataId] || (obj[r.dataId] = {}));
									(r.dataId.indexOf("ary") != -1) && (obj[r.dataId] || (obj[r.dataId] = []));
								}
								r.trigger = trigger;
								var p = inFuncs[rType](r, args, obj[r.dataId]);
								//r.dataId && (obj[r.dataId] || (obj[r.dataId] = {})) && (obj[r.dataId][r.param] = p);
								//r.dataId && (r.dataId.indexOf("ary") != -1) && ($.inArray(r.dataId, aryFlag) == -1) && aryFlag.push(r.dataId);

								dbObj["in"].push({
									id: r.iid || r.inid,
									method: r.type,
									param: p,
									columnName: r.columnName
								});
								if (p || p == 0) {
									obj[r.param] = p;
								}
							} catch (e) {
								console.error(e);
							}
						} else {
							dbObj["in"].push({
								id: r.iid || r.inid,
								method: r.type,
								param: p,
								type: "none",
								columnName: r.columnName
							});
						}
					}
					if (isExpFunc) { //表达式执行运算 
						dbObj["in"].push({
							exp: r.insExp,
							type: "exp",
							param: "",
							columnName: r.insExp
						});
						try {
							insExpEval = q.expConvert(r.insExp, expObj, true);
							if (insExpEval) {
								obj[r.param] = eval(insExpEval);
							}
						} catch (e) {
							mtDebugger.pushQueue("error", "输入表达式：" + r.insExp + ",执行异常");
						}
					}

				}

				dbObj["param"].push(obj);

				// 输出控件方法
				var outd = par[0],
					outType = outd.eventType,
					outRole = outd.oe ? this.getRole(outd.oe) : (id.length < 31 && id.indexOf("/") == -1) ? this.getRole(outd.oid || id) : 'page'
				isCallback = outd.callback;

				if (!roles[outRole]) {
					continue;
				}

				dbObj["out"].push({
					id: id || outd.oid,
					method: outType
				});

				par.childs = op['childs'];
				par.trigger = trigger;

				var outFuncs = roles[outRole][0]['funcs'];
				if (typeof outFuncs[outType] == 'function') {
					try {
						par.tlevel = tlevel;
						outFuncs[outType](par, obj, args);
					} catch (e) {
						console.log(e);
						mtDebugger.pushQueue("error", "outLinkTmp", {
							id: id,
							outType: outType,
							message: e.message
						});
					}
				} else {
					mtDebugger.pushQueue("error", "outLinkTmp", {
						id: id,
						outType: outType,
						message: "没有执行方法"
					});
				}

			}
			/*if(!isCallback){
				childs = op['childs'];
				if (childs && childs.length) {
					q._execOps(childs, tlevel, rules, args);
				}
			}*/
		}
	};
	// 执行事件 type 方法类型，rule 规则
	// {"grid":[{"inid":"textbox","outid":"grid","type":"getValue","param":"col1438410245523"},
	//          {"inid":"grid","outid":"grid","type":"getRow","param":"col1438410245523"}]
	//}
	q.pub = function(type, rules) {
		var args = Array.prototype.slice.call(arguments, 2);
		if (typeof type == 'string') { // 事件类型为字符串
			// 根据规则分组输入 输出控件
			for (var id in rules) {
				// 获取规则
				var ruleArray = rules[id];
				// 封装参数
				var obj = {};
				for (var i = 0; i < ruleArray.length; i++) {
					var r = ruleArray[i];
					// 根据输入控件id获取其所属角色role
					var inRole = (r.inid.length < 31 && r.inid.indexOf("/") == -1) ? this.getRole(r.inid) : 'page';
					if (!roles[inRole]) {
						continue;
					}
					// 根据控件角色获取控件 事件方法
					var inFuncs = roles[inRole][0]['funcs'];
					if (typeof inFuncs[r.type] == 'function') {
						try {
							var p = inFuncs[r.type](r, args);
							if (p) {
								obj[r.param] = p;
							}
						} catch (e) {
							console.log(e);
						}
					}
				}
				// 输出控件方法
				var outRole = (id.length < 31 && id.indexOf("/") == -1) ? this.getRole(id) : 'page';
				if (!roles[outRole]) {
					continue;
				}
				var outFuncs = roles[outRole][0]['funcs'];

				if (typeof outFuncs[type] == 'function') {
					// args.rules = ruleArray ;
					try {
						outFuncs[type](id, obj, args);
					} catch (e) {
						console.log(e);
					}
				}
			}
		} else if (typeof type == 'object') { // 事件传递的是对象
			// type.out.   inps = type['in'], //输入控件
			var ops = type['ops'], //输出控件
				trigger = type['trigger'][0], //触发控件
				tlevel = trigger.level, //触发控件层级
				childs = type["childs"]; //子事件
			pubsub.dbAry = [];
			q._execOps(ops, trigger, rules, args);
			//循环子节点事件
			if (childs && childs.length) {
				$.each(childs, function(i, child) {
					q.pub(child, rules);
				})
			}
			mtDebugger.__etable(pubsub.dbAry);
		}
		return this;
	};
	// 注册事件 role 角色类型 funcs 角色相关事件
	q.sub = function(role, funcs) {
		var fn = function() {
			for (var p in funcs) {
				this[p] = funcs[p];
			}
		};
		fn.prototype = new baseFunc();
		roles[role] = roles[role] ? roles[role] : [];
		var token = (++subUid).toString();
		roles[role].push({
			token: token,
			funcs: new fn()
		});
		return token;
	};
	return q;
})();

var bindSpreadEvents = function(spread) {
	var $t = $(spread._host),
		er = $t.data("eventsRule"),
		ev;
	er && (ev = er[$t.attr("id")]);
	ev && ev.call();
	var sheet = spread.getActiveSheet();
	sheet.bind(GcSpread.Sheets.Events.ValueChanged, function(e, info) {
		var cell = info.sheet.getCell(info.row, info.col),
			cellType = cell.cellType();
		if (cellType instanceof GcSpread.Sheets.ComboBoxCellType || cellType instanceof GcSpread.Sheets.RadioButtonCellType || cellType instanceof GcSpread.Sheets.CheckboxButtonCellType) {
			var $target = $(info.sheet.parent._host),
				eventsRule = $target.data("eventsRule");
			if (eventsRule) {
				//var event = eventsRule[info.row + "-" + info.col];
				var event = eventsRule[dynamicAreaFunc.calculate(info.sheet, info.row + "-" + info.col, true)];
				if (event) {
					event.call();
				}
			}
		}
	});
	spread.bind(GcSpread.Sheets.Events.ButtonClicked, function(e, info) {
		var cell = info.sheet.getCell(info.row, info.col),
			cellType = cell.cellType(),
			watermark = cell.watermark();
		if (watermark) {
			var obj = JSON.parse(watermark);
			if (obj) {
				if (obj.type == "del") { //删除按钮
					dynamicAreaFunc.delFunc.call(cell, obj.area);
				} else if (obj.type == "add") { //新增按钮
					dynamicAreaFunc.addFunc.call(cell, obj.area);
				}
			}
		}
		if (cellType instanceof GcSpread.Sheets.ButtonCellType) {
			var $target = $(info.sheet.parent._host),
				eventsRule = $target.data("eventsRule");
			if (eventsRule) {
				//var event = eventsRule[info.row + "-" + info.col];
				var event = eventsRule[dynamicAreaFunc.calculate(info.sheet, info.row + "-" + info.col, true)];
				if (event) {
					event.call();
				}
			}
		}
	});
}

/**
 *	spread js 绑定验证器
 */
var bindSpreadValidate = function(eleId) {
	if (!CustomerCondition) {
		function CustomerCondition(compareType, expected, formula, trigger) {
			var self = this;
			self.ignoreBlank = false;
			self.conditionType = "CustomerCondition";
			self.compareType = compareType;
			self.expected = expected;
			self.formula = formula;
			self.trigger = trigger;
		};
		CustomerCondition.prototype = new GcSpread.Sheets.CellValueCondition();
		CustomerCondition.prototype.evaluate = function(evaluator, baseRow, baseColumn, actualValue) {
			//debugger;
			var trigger = this.trigger,
				mtValidate = trigger.vali,
				iid = trigger.iid,
				k = $('#' + iid);
			// 执行表达式
			var execObj = pubsub._getInParams(
					mtValidate.execExpData,
					trigger.inlev),
				exec = pubsub.expConvert(mtValidate.execExp || "", execObj);
			if (exec != "") {
				try {
					var execBol = eval(exec);
					if (!execBol)
						return true;
				} catch (e) {
					return false;
				}
			}
			// 验证表达式
			var eObj = pubsub._getInParams(mtValidate.expData, trigger.inlev),
				exp = pubsub.expConvert(mtValidate.exp || "", eObj);
			if (exp != "") {
				k.data("currentCell", trigger);
				try {
					var expBol = eval(exp);
					if (expBol)
						return true;
					else
						return false;
				} catch (e) {
					return false;
				}
			}
			return true;
		};
	}
	var $this = $("#" + eleId),
		spread = $this.data("spread"),
		sheet = spread.getActiveSheet(),
		spreadValidate = $this.data("spreadValidate"),
		inids, trigger, vali, cCondition, validator1;
	spread.highlightInvalidData(true);
	if (spreadValidate) {
		for (var pro in spreadValidate) {
			trigger = spreadValidate[pro];
			vali = trigger.vali;
			inids = pro.split("-");
			cCondition = new CustomerCondition(1, 4, null, trigger);
			validator1 = new GcSpread.Sheets.DefaultDataValidator(cCondition);
			//validator1.inputTitle = vali.tip;
			validator1.inputMessage = vali.tip;
			validator1.ignoreBlank = false;
			validator1.type = GcSpread.Sheets.CriteriaType.Custom;
			sheet.setDataValidator(inids[0], inids[1], validator1);
		}
	}
};

/**
 * 日期格式化
 * @param  
 * @returns 
 */
Date.prototype.format = function(format) {
	var args = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"H+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3), //quarter 
		"S": this.getMilliseconds()
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
			.substr(4 - RegExp.$1.length));
	for (var i in args) {
		var n = args[i];
		if (new RegExp("(" + i + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
	}
	return format;
};



function imageUploadInitFunc() {
	// 添加事件
	$('body')
		.delegate(
			'.image_upload_div',
			'change',
			function() {
				var $this = $(this),
					input = $this
					.find(".image_upload_input_file")[0],
					maxfilesize = $this
					.attr("maxfilesize");
				var mypic = input.files[0];
				/* 判断文件大小 */
				if (!mypic) return;
				if (mypic.size > maxfilesize) {
					alert("上传的文件不能超过" + (maxfilesize / (1024 * 1024)) + "M");
					return;
				}
				var params = {
					randomparent: $this.attr("randomparent"),
					type: "image"
				}
				if($this.attr("attachmentId")){
					params.attachmentId = $this.attr("attachmentId");
				}
				var onUploadSuccessFunc = $this.data("onUploadSuccess");
				$.ajaxFileUpload({
					url: $this.attr("saveurl"),
					secureuri: false,
					fileElementId: input.id,
					data: params,
					dataType: 'json',
					success: function(data, status) {
						if (data && data.statusCode == 200) {
							alert("上传成功！");
							$this.attr("attachmentId",
								data.attachmentId);
							var $image = $this.find(".imageupload_class");
							// $image[0].onload = function() {
							//        EXIF.getData($image[0], function() {
							//            $(this).data("tags",EXIF.getAllTags(this));
							//        });
							//    };
							$image.attr(
									"src",
									window.URL.createObjectURL(mypic))
								.show();

							var urlTest = '/glaf/mx/form/imageUpload?method=downloadById&_' + (new Date().getTime()) + '&id=' + data.attachmentId;

							var imageTest = new Image();
							imageTest.onload = function() {
								EXIF.getData(this, function() {
									$image.data("tags", EXIF.getAllTags(this));
								});
							};
							imageTest.src = urlTest;

							var outputName = $this.attr("outputName");
							if (outputName) {
								imageuploadFunc.setOutPutName(
									outputName, data.names);
							}
							if(onUploadSuccessFunc && $.isFunction(onUploadSuccessFunc)){
								onUploadSuccessFunc();
							}
						} else {
							alert('上传失败！');
						}
					},
					error: function(data, status, e) {
						alert('上传失败！Error=' + e);
						console.log('出错 ' + status + '; data=' + JSON.stringify(data) + '; e=' + e);
					}
				});
			});
}