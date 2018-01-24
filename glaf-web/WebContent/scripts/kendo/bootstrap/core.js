var mtxx = {},
	baseFunc = function() {
		return {
			thidden: function(rule, args) { // 隐藏
				pubsub.getJQObj(rule).hide();
			},
			tshow: function(rule, args) { // 显示
				var $id = pubsub.getJQObj(rule); 
				if($id.attr("data-role") && $id.attr("data-role") == "a"){
					$id.css("display","block");
				}else{
					$id.show();
				}
				
			},
			tdisabled: function(rule, args) { // 禁用
				pubsub.getJQObj(rule).attr("disabled", "disabled");
			},
			tenabled: function(rule, args) { // 启用
				pubsub.getJQObj(rule).removeAttr("disabled");
			},
			_init_: function(rule, param, args) { //控件初始化事件
				var $id = pubsub.getJQObj(rule);
				eval("(" + __gobalInit__[$id.attr("id")] + ")")($.isArray(param) ? {} : param);
				pubsub.execChilds(rule);
			},
			setIndex : function(rule,args){
				var $id = pubsub.getJQObj(rule);
				var v = "";
				for (var i = 0; i < rule.length; i++) {
					r = rule[i];
					v = args[r.param]||"";
				}
				$id.css("z-Index",v);
			},
		}
	};

// 页面初始化赋值
function pageInit() {
	// 控件缺省初始化
	$("[data-role]").each(function(i, o) {
		var dataRole = $(o).attr("data-role");
		if (!$(o).data("textboxbt") && dataRole == "textboxbt") {
			$(o).textboxBtExt();
		} else if (!$(o).data("touchspin") && dataRole == "touchspin") {
			$(o).touchSpinExt();
		} else if (!$(o).data("textareabt") && dataRole == "textareabt") {
			$(o).textAreaBtExt();
		} else if (!$(o).data("progressbarbt") && dataRole == "progressbarbt") {
			$(o).progressBarExt();
		} else if (!$(o).data("datetimepickerbt") && dataRole == "datetimepickerbt") {
			$(o).datetimepickerExt();
		} else if (!$(o).data("datepickerbt") && $(o).closest("div.input-daterange").length == 0 && dataRole == "datepickerbt") {
			$(o).datepickerExt();
		} else if (!$(o).data("daterangepicker") && dataRole == "daterangepicker") {
			$(o).daterangepickerExt();
		} else if (!$(o).data("calendarbt") && dataRole == "calendarbt") {
			$(o).calendarExt();
		}
	});

	if (pageParameters) {
		// initPageDetail(p);
		var params = pageParameters;
		if (params) {
			mtxx.params = params;
			var ret = params['detail'];
			initPageDetailSuccess(ret);
		}
	} else {
		initPageDetailSuccess();
	}

	// 给记忆控件注册点击事件
	$('body').delegate('input[memory=true]', 'click', function() {
		window.memoryObj = $(this);
	});

}

function initPageDetail(p) {
	var params = eval('(' + p + ')');
	if (params) {
		mtxx.params = params;
		var $body = $(document.body);
		var url = contextPath + '/mx/form/data/initPageDetail',
			jdom;
		$.ajax({
			url: url,
			data: params,
			dataType: 'json',
			type: 'post',
			success: initPageDetailSuccess
		});
	}
}

function initPageDetailSuccess(ret) {
	if ((window.$ret = ret) && ret.data) {
		// var ff = new FlowFilter(ret.flowDefined || {});
		ret.jdoms = [];
		$.each(ret.data, function(id, v) {
			jdom = $("#" + id);
			if (jdom[0]) {
				// if (ff.filter(jdom, id)) {
				var val;
				if (v[0].value || v[0].value == 0) {
					val = v[0].value;
				} else {
					val = jdom.val();
				}
				jdom.mtbootstrap('setValue', val, v);
				if (!id.startsWith("ztree")) {
					jdom.data("params", v[0]);
				}
				// }
				ret.jdoms.push({
					jdom: jdom,
					id: id
				});
			}
		});

		if (ret.handleColumns) {
			var $body = document.body,
				saveKey = "saveMsg";
			!$.data($body, saveKey) && ($.data($body, saveKey, []));
			var btn = new MtButton({
				handlecolumns: JSON.stringify(ret.handleColumns)
			});
			$.data(document.body, saveKey).push(btn);
		}

		linkAge(ret); // 联动页面控件、
		initPageFlow(ret); // 页面工作流
	}

	initProgressBarFunc();
	// initKendoUploadFunc();
	initImageUploadFunc();
	initImageUploadFunc2();
	initMutilFileViewsFunc();
	initPageLevel();
	jqCb.fire();
}

/**
 * 页面工作流
 * 
 * @param o
 */
function initPageFlow(o) {
	o.__idValue = o.__idValue || o.idValue;
	if (o.jdoms.length) {
		$.ajax({
			url: contextPath + '/mx/form/data/initPageFlow',
			type: 'post',
			dataType: 'JSON',
			data: {
				idValue: o.__idValue,
				pageId: mtxx.params.id,
				processId : mtxx.params.processId
			},
			success: function(ret) {
				if (ret.flowDefined) {
					$.data(document.body, "flowFilter", new FlowFilter(ret.flowDefined).exec(o.jdoms));
				}
			}
		});
	}
}

function FlowFilter(flow) {

	this.flow = flow;

	this.elements = flow.elements;

	this.unValisaves = [];

	this.exec = function(doms) {
		var that = this;
		$.each(doms, function(i, v) {
			that.filter(v.jdom, v.id);
		});
		if (that.elements) {
			$.each(that.elements, function(id, element) {
				if (!element.ready) {
					var jq = $("#" + id);
					jq[0] && (that.filter(jq, id));
				}
			});

			if (that.unValisaves.length) { // 去掉当前节点不可写的验证
				var valisaveKey = "valisave";
				$.each(that.unValisaves, function(i, $jq) {
					$jq.removeAttr(valisaveKey);
					$("[" + valisaveKey + "=true]", $jq) //
						.removeAttr(valisaveKey);
				});
			}
		}
		return that;
	};

	this.target = function(id) {
		addVariables(id, this.flow._taskId);
	};

	this.filter = function(jq, id) {
		if (!this.elements)
			return true;
		var f = this.elements[id];
		if (f) {
			f.ready = true;
			try {
				var value = jq.mtbootstrap("getValue");
				if(f.defaultVal && !value){
					jq.mtbootstrap("setValue", f.defaultVal);
				}
				// jq.mtbootstrap("setValue", value || f.defaultVal || '');
			} catch (e) {
				console.log(e);
			}

			// if (f.target) { // 接收人 (已经放到全局参数里面)
			// that.target(id);
			// }
			if (!f.show) { // 不显示
				jq.hide();
				return false;
			} else {
				// jq.show();
			}
			if (!f.write) {
				try {
					if(jq.attr("id") && jq.closest("tr").attr("tid")){
						jq.closest("tbody").find("[tempid='"+jq.attr("id")+"']").mtbootstrap("disable", true);	
					}
					jq.mtbootstrap("disable", true);	
				} catch (e) {
					if (jq.is("div")) {
						jq = jq.find("." + id);
					}
					jq.attr({
						readonly: true
					});
				}
				this.unValisaves.push(jq);
			} else { // 流程里面可写的保存才做验证

			}
			if (!f.read) {}
		}
		return true;
	};

	(function(that) {
		var f = that.flow;
		mtxx.params.flow = f;
		if (f.targets && f.targets.length) { // 接收人放入工作流全局变量
			$.each(f.targets, function(i, v) {
				if(f._taskId && f._taskId != v.taskId)//当前节点的  taskId_user  参数不传
					addVariables(v.eleId, v.taskId);
			});
		}

		/**
		 * 指定节点
		 */
		if (f.targetTasks && f.targetTasks.length) {
			mtxx.params.flow.targetTasksVars = [];
			$.each(f.targetTasks, function(i, v) {//必须是当前节点下
				if(f._taskId && f._taskId == v.taskId)
					addVar(v.eleId, v.taskId, mtxx.params.flow.targetTasksVars);
			});
		}

	})(this);
}

/**
 * 获取流程定义参数
 */
function processGetData() {
	var id = $("[fieldname=id]:hidden").val(),
		flow = mtxx.params.flow;
	var pageRuleId = $(".pageruleid").val();
	var params = {
		id: id,
		pageRuleId: pageRuleId,
		targetTask : null
	};
	
	var populate = function(collection, params){
		if(collection && collection.length){
			function _each(i, v){
				if (v.datas) {
					var expActVal = v.datas.expActVal;
					var o = new RegExp("~M\{([^\\}]*)\}", "g").exec(expActVal);
					if (o && o[1]) {
						try {
							var value_ = $("#" + o[1]).mtbootstrap("getValue");
							params[v.varname] = value_;
							params[v.varcode] = value_;
						} catch (e) {
							console.log(e);
						}
					}
				}
			}
			$.each(collection, _each);
		}
	};
	
	// targetTasks
	
//	if (flow.variables) {
//		$.each(flow.variables, function(i, v) {
//			if (v.datas) {
//				var expActVal = v.datas.expActVal;
//				var o = new RegExp("~M\{([^\\}]*)\}", "g").exec(expActVal);
//				if (o && o[1]) {
//					try {
//						params[v.varname] = $("#" + o[1]).mtbootstrap("getValue");
//						params[v.varcode] = $("#" + o[1]).mtbootstrap("getValue");
//					} catch (e) {
//						console.log(e);
//					}
//				}
//			}
//		});
//	}
	
	populate(flow.variables, params);
	
	var tt = {};
	
	populate(flow.targetTasksVars, tt);

	function GetTargetTask(t){
		var targetTask = null;
		for(var k in t){
			if(targetTask = t[k]){
				break;
			}
		}
		return targetTask;
	}
	params.targetTask = GetTargetTask(tt);
	
	/*if(params.targetTask){//指定节点的指派人还得加进去
		$.each(["user", "role"], function(i, v){
			if(params[flow._taskId + "_" + v]){
				params[params.targetTask + "_" + v] //
				= params[flow._taskId + "_" + v];
				delete params[flow._taskId + "_" + v];
			}
		});
		if(params[flow._taskId]){
			delete params[flow._taskId];
		}
	}*/
	
	return params;
}

/**
 * 获取流程动态更新节点的参数
 * 
 * @returns
 */
function getFlowDataSetParams() {
	var ff = $.data(document.body, "flowFilter"),
		flow;
	var params = window.getIdValues();
	if (ff && (flow = ff.flow)) {
		if (flow.taskWdataSet) {
			return $.extend(params, //
				pubsub.getParameters(flow.taskWdataSet[0].datas));
		}
	}
	return null;
}

/**
 * 获取页面隐藏主键值
 * 
 * @returns
 */
function getIdValues() {
	var params = {};

	function values(i, v) {
		params[$(v).attr("fieldname")] = $(v).val();
	}
	$("input.id-field:hidden[fieldname]").each(values);
	return params;
}

/**
 * 角色类型 部门、角色、用户
 * 
 * @param jq
 * @returns
 */
function getSelectType(jq) {
	var surl = jq.attr("surl");
	if (surl) {
		var pars = surl.split("=");
		return pars[pars.length - 1];
	}
	return jq.attr("select-type") || "user";
}

function addVariables(id, taskId) {
	// var jq = $("#" + id);
	// var selectType = getSelectType(jq);
	!mtxx.params.flow && (mtxx.params.flow = {});
	!mtxx.params.flow.variables && (mtxx.params.flow.variables = []);
// mtxx.params.flow.variables.push({
// datas: {
// expActVal: "~M{" + id + "}"
// },
// varcode: taskId,
// varname: taskId + "_" + selectType
// });
	addVar(id, taskId, mtxx.params.flow.variables);
};

function addVar(id, taskId, collection){
	var jq = $("#" + id);
	var selectType = getSelectType(jq);
// !mtxx.params.flow && (mtxx.params.flow = {});
// !mtxx.params.flow.variables && (mtxx.params.flow.variables = []);
	collection.push({
		datas: {
			expActVal: "~M{" + id + "}"
		},
		varcode: taskId,
		varname: taskId + "_" + selectType
	});
}


/**
 * 判断对象是否为空
 */
function objectISNotEmpty(o) {
	if (o) {
		for (var key in o) {
			return true;
		}
	}
	return false;
}

/**
 * 页面初始化 联动
 * 
 * @param ret
 */
function linkAge(ret) {
	if (ret) {
		if (ret.controlRule) { // 页面联动
			pubsub.pub("linkageControl", ret.controlRule);
			pubsub.pub("linkagePage", ret.controlRule);
		}
		if (ret.tables) { // 页面参数隐藏域
			$.each(ret.tables, function(key, list) {
				if (list.length) {
					for (var i = 0; i < list.length; i++) {
						var v = list[i];
						$("<input>", {
							class: (key || "") + " id-field", // 主键
							type: 'hidden',
							fieldName: v.fieldName,
							value: v.value
						}).appendTo("body");
					}
				}
			});
		}

		if (ret.pageRuleId) {
			$("<input>", {
				class: 'pageruleid',
				type: 'hidden',
				value: ret.pageRuleId
			}).appendTo("body");
		}
		if (ret.batchRules && ret.batchRules[0]) { // 变长区数据获取
			if (ret.batchRules[0].cell) {
				// new CellBatchFunc(ret.batchRules[0]);
			} else {
				new BatchFunc(ret.batchRules);
			}
		}
	}
}

function odblClick_(o) {
	var $this = $(o),
		$t = $this.closest(".nlayout_elem");
	var surl, url = contextPath + (surl = ($(o).attr("surl") || "/mx/form/defined/jsgis")) + ((surl.indexOf("?") > -1) ? "&" : "?") + $.param({
		targetId: $t.attr("data-role") == "form" ? $this.attr("id") : $t.attr("id"),
		fn: "odblClickCallFunc_"
	});

	window.open(url);
}

function getControlInput($this) {
	return $($this.find("input." + $this.attr("id")).get(0) || $this);
}

function odblClickCallFunc_(datas) {
	// var $that = $(this),$this =
	// $($that.find("input."+$that.attr("id")).get(0) || $that);
	var $that = $(this),
		$this = window.getControlInput($that);
	if (datas) {
		var data = datas.data;
		var rules = datas.rule || $this.attr("dbrule");
		var rs = eval(rules);
		for (var i = 0; i < rs.length; i++) {
			var r = rs[i];
			if (r.name == "FID") {
				$('#' + r.output).val(data.layerId + "-" + data.FID);
			} else {
				var $output = $('#' + r.output);

				// $output.find("input."+$output.attr("id")).val(data[r.name]);
				window.getControlInput($output).val(data[r.name]);
			}
		}
	} else {
		if ($this[0]) {
			var rules = $this.attr("dbrule");
			if (rules) {
				var rs = eval(rules),
					data = {};
				for (var i = 0; i < rs.length; i++) {
					var r = rs[i];
					data[r.name] = $("#" + r.output).val();
				}
				return data;
			}
		}
	}
}

function initImageUploadFunc2() {
	$('body').delegate('.clickView', 'click', zoomViewImg);
	$('body').delegate('.dblclickView', 'dblclick', zoomViewImg);
	imageUploadInitFunc();
}
// 图片放大显示
function zoomViewImg(e) {
	var $that = $(this),
		width = $(window).width(),
		height = $(window).height(),
		ileft = (width - 800) / 2,
		itop = (height - 800) / 2;
	if ($that.hasClass("showWin")) {
		var path = contextPath + "/mx/form/defined/viewImg?srcl=";
		window.open(path + $that.attr('src').replace(/&/ig, "@"), '查看图片', "width=" + screen.width + ",height=" + screen.height + ",modal=true,status=no,scrollbars=no,resizable=no");
	} else {
		var $body = $('body');
		$body.append('<div class="hock_wrap" style="display: block; width: 100%;height: 100%; opacity: 0.5;position: absolute;background:#000;top:0;left:0;z-index:9999"></div>');
		$body.append('<div class="img_view_wrap hock_wrap" style="width: 100%;height:100%;vertical-align:middle;align-items: center;line-height:100%;text-align:center;position: absolute;top:0;left:0;z-index:99999"><img src="' + $that.attr('src') + '" style="vertical-align:middle"></div>');
		var $wrap = $('.img_view_wrap');

		function initImgSize(e) {
			width = e ? $(window).width() : width;
			height = e ? $(window).height() : height;
			var $img = $(".img_view_wrap > img");
			$wrap.css({
				'line-height': height + 'px'
			});
			if ((height / width) < ($img.height() / $img.width())) {
				if ($img.height() >= height || e) {
					$img.width('').height(height - 5);
				}

			} else {
				if ($img.width() >= width || e) {
					$img.width(width - 5).height('');
				}
			}
		}

		$(window).resize(initImgSize);
		initImgSize();

		$(".hock_wrap").on("click", function(e) {
			$(".hock_wrap").remove();
		});
	}
};


function initMutilFileViewsFunc() {
	var options = {
		'childList': true,
		'arrtibutes': true,
		'subtree': true,
		'characterData': true
	};
	var observer = new MutationObserver(
		function() {
			// 多文件查看功能
			$(".mt-fileView")
				.each(
					function() {
						var $this = $(this);
						if ($this.attr("model") == "multi") {
							var htmlStr = $this.html(),
								tvalStr = $this.attr("tval"),
								splitStr = $this.attr("split"),
								html, tval, i = 0,
								key = $this.attr("key"),
								url = $this.attr("url"),
								model = $this.attr("model"),
								fid = $this.attr("fid"),
								dbid = $this.attr("dbid"),
								htmls = htmlStr.split(","),
								tvals = tvalStr.split(","),
								length = htmls.length,
								targetStr = "";
							for (; i < length; i++) {
								if (htmls[i]) {
									targetStr += "<a href='#' fid='" + fid + "' kid='" + tvals[i] + "' dbid='" + dbid + "' model='" + model + "' key='" + key + "' url='" + url + "' onclick='viewFiles(this)'>" + htmls[i] + "</a>" + splitStr;
								}
							}
							if (targetStr) {
								targetStr = targetStr.substr(0,
									targetStr.length - 1);
								$this.after(targetStr);
								$this.remove();
							}
						}
					});
			// observer.disconnect();
		});
	observer.observe(document.body, options);
	document.body.appendChild(document.createElement("div"));
}

// //////变长区

function initPageBatch(data, fn) {
	var url = contextPath + '/mx/form/data/initPageBatch';
	$.ajax({
		url: url,
		type: 'post',
		dataType: 'json',
		data: data,
		async: true,
		success: fn
	});
}

function BatchFunc(rules) {
	this.rules = rules;
	console.log(rules);
	this.deleteCollection = [];
	this.init();
}
BatchFunc.prototype = {
	constructor: BatchFunc,
	init: function() {
		var that = this,
			rules = that.rules;
		that.initTools();
		for (var i = 0; i < rules.length; i++) {
			var rule = rules[i],
				tableMsg = rule.tableMsg,
				tableName = rule.tableName,
				dataSetId = rule.dataSetId,
				typeField = rule.typeField,
				topTableName = rule.topTableName;
			var topIdField = $("." + topTableName + "[fieldName=id]");
			if (!topIdField[0]) {
				topIdField = $("." + topTableName + "[fieldName=topid]");
			}
			if (tableMsg) {
				var typeSort = {
						stuff: new Object()
					},
					typeCode;

				/**
				 * 根据datasetId 从数据集获取
				 */
				var dataSource = [];
				window.batch = that;
				if (dataSetId && topIdField.val()) {
					rule.topId = topIdField.val();
					var url = contextPath + '/mx/form/data/initPageBatch';
					$
						.ajax({
							url: url,
							type: 'post',
							dataType: 'json',
							data: {
								dataSetId: dataSetId,
								topId: topIdField.val()
							},
							async: false,
							success: function(data) {
								var rows = data.rows;
								if (rows && rows.length) {
									$
										.each(
											rows,
											function(i, v) {
												var obj = new Object();
												for (var key in v) {
													obj[key
														.replace(
															tableName + '_0_',
															'')] = v[key];
												}
												typeCode = obj[typeField] || "sys-0";
												if (!typeSort[typeCode]) {
													typeSort[typeCode] = new Array();
												}
												typeSort[typeCode]
													.push(obj);
											});
								}
							}
						});
				}
				var clickType = "click.elements",
					randomId = new Date()
					.getTime(),
					idKey = "id";
				for (var k = 0; k < tableMsg.length; k++) {
					var msg = tableMsg[k];
					var table = $("#" + msg.cid).closest("table"),
						trIndex = msg.trIndex,
						tdIndex = msg.tdIndex;
					table
						.unbind(clickType)
						.on(
							clickType,
							'td [columnname]',
							function(e) {
								var $target = $(e.target);
								var $tr = $target.closest("tr");
								var $templateTr = table.find("tr[tid='" + $tr.attr("class") + "']"); // 当前选择行的母板
								$tr
									.find("[columnname]")
									.each(
										function(i, v) {
											var $v = $(v);
											!$v.attr(idKey) && ($v
												.attr(
													idKey,
													randomId++));
											$templateTr
												.find(
													"[columnname='" + $v
													.attr("columnname") + "']")
												.attr({
													toId: $v
														.attr(idKey)
												});
										});
							});
					var initData = typeSort[msg.xy];
					// if (initData) {
					// 	initData.sort(function(a, b) {
					// 		return (a.listno - b.listno);
					// 	});
					// }
					if (msg.items) {
						var trs = table.find("tr"),
							$this = trs.eq(trIndex)
							.data("fieldIds", msg.fieldIds).data('type', {
								typeField: typeField,
								typeCode: msg.xy,
								typeName: msg.name
							}).data("rule", rule),
							fn = new Batch($this,
								tdIndex),
							tId = $this.attr("tId");
						typeSort.stuff[msg.xy] = {
							fn: fn,
							length: msg.items.length
						};
						$.each(msg.items, function(i, v) {
							var tr = trs.eq(v.trIndex).addClass(tId);
							if (tr && tr[0]) {
								fn.initTrEvents(tr);
								if (!($this[0] === tr[0])) {
									tr.find("td").each(function(index, td) {
										var last = $(this).children().last();
										if (last && last[0]) {
											last.attr({
												columnName: fn.fields[index]
											});
										}
									});
								}
								fn.rowspan(+1);
								if (initData && initData.length >= i) {
									fn.set(initData[i], tr);
								}
							}
						});
					}
				}
				if (typeSort.stuff) {
					var obj;
					for (var key in typeSort.stuff) {
						obj = typeSort.stuff[key];
						if (obj) {
							var kds = typeSort[key];
							if (kds && (kds.length > obj.length)) {
								var datas = kds.slice(obj.length, kds.length);
								if (datas) {
									obj.fn.add(datas);
								}
							}
						}
					}
				}
			}
		}
	},
	getValue: function(tables) {
		var that = this,
			rules = that.rules;

		var tmps, index; // 先删除，在push
		function remove() {
			tables.splice(index, tmps.length);
		}

		function iterator(key) {
			tmps = [], index = -1;
			$.each(tables, function(i, v) {
				if (v.table && v.table.tableName == key) {
					tmps.push(v);
					if (index == -1) {
						index = i;
					}
				}
			});
			if (tmps.length) {
				remove();
			}
		}
		var batchId = new Date().getTime();
		for (var i = 0; i < rules.length; i++) {
			var rule = rules[i],
				cId = rule.cId;
			if (tables.length > 0) {
				iterator(rule.tableName);
			}
			var table = $("#" + cId).closest("table"),
				tbId = table
				.attr("tbId");
			table
				.find("tr[tbid='" + tbId + "']")
				.each(
					function() {
						var batchRoot = $(this),
							typeObj = batchRoot
							.data("type") || {};
						var tid = batchRoot.attr("tId"),
							o;
						table
							.find('.' + tid)
							.each(
								function(i, v) {
									var typeField = typeObj.typeField,
										collection = new Array();

									var row = $(this).data(
										"row");
									tables
										.push({
											idValue: row ? row['id'] : '',
											table: {
												tableName: rule.tableName
											},
											columns: collection
										});
									if (typeObj.typeField) { // type
										collection
											.push({
												fieldName: typeObj.typeField,
												value: typeObj.typeCode
											});
									}
									collection.push({ // listno
										fieldName: 'listno',
										value: i
									});
									collection.push({ // topId
										fieldName: 'topId',
										value: rule.topId
									});
									
									batchId ++;
									
									collection.push({ // topId
										fieldName: 'batchId',
										value: batchId
									});
									
									$(this).addClass("_" + batchId, batchId);
									
									$(this)
										.find(
											"[columnName]")
										.each(
											function() {
												var cn = $(this),
													fieldName = cn
													.attr('columnName');
												if (fieldName) {
													collection
														.push({
															fieldName: fieldName,
															value: cn.mtbootstrap("getValue")
														});
												}
											});
								});
					});
		}

		var dels = that.deleteCollection;
		if (dels.length > 0) {
			for (var i = 0; i < dels.length; i++) {
				tables.push(dels[i]);
			}
		}

	},
	initTools: function() {
		var $tools = $(".k-batch-tools");
		if (!$tools[0]) {
			$tools = $("<div>", {
				'class': 'k-batch-tools'
			}).css({
				position: 'absolute',
				'z-index': '99'
			}).appendTo(document.body).hide();


			$("<button>", {
				'class': 'k-button k-batch-tools-add'
			}).text("↑").on('click', function(e) {
				e.preventDefault();
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.insert(null, o.target,"top");
				}
			}).appendTo($tools);
			$("<button>", {
				'class': 'k-button k-batch-tools-add'
			}).text("↓").on('click', function(e) {
				e.preventDefault();
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.insert(null, o.target,"bot");
				}
			}).appendTo($tools);
			$("<button>", {
				'class': 'k-button k-batch-tools-remove'
			}).text("-").on('click', function(e) {
				e.preventDefault();
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.remove(o.target);
				}
			}).appendTo($tools);
			$("<button>", {
				'class': ''
			}).text("X").on('click', function(e) {
				e.preventDefault();
				var o = $tools.hide();
			}).appendTo($tools);
		}

		return $tools;
	}
};

/**
 * 控件角色（data-role）与初始化jq 插件初始化方法映射
 */
var $copyRole = (function() {
	var mapping = {
		touchspin: 'touchSpinExt',
		"datepickerbt" : "datepickerExt"
	}, enableds = {
		touchspin : function(){
			return !!!$(this).find("input").attr("disabled");
		},
		textboxbt : function(){
			return !!!$(this).attr("disabled");
		}
	};
	var func = function() {};
	func.add = function(role, initMethod) {
		mapping[role] = initMethod;
	};
	func.get = function(role) {
		return mapping[role];
	};
	
	func.getOptions = function(opts){
		
		var options = $.extend(true, {}, opts.options, {
			id : this.id
		});
		
		var enabled = func.enabled($("#" + opts.options.id));
		
		if(enabled !== undefined){
			
			options.enabled = enabled;
		}
		
		return options;
	};
	
	func.enabled = function($jq){
		var r = $jq.data("role"), fn = enableds[r];
		if(fn){
			return fn.call($jq.get(0));
		}
	};
	
	return func;
})();

function Batch(tr, tdIndex) {
	this.tr = tr;
	this.rows = 0;
	this.template = null;
	this.tdIndex = tdIndex;
	this.fieldIds = tr.data("fieldIds");
	this.rule = tr.data("rule");
	this.fields = new Array();
	this.init();
}
Batch.prototype = {
	constructor: Batch,
	init: function() {
		var that = this;
		if (that.fieldIds) {
			$.each(that.fieldIds, function(index, v) {
				var o = that.tr.find("#" + v.id);
				if (o && o[0]) {
					o.attr({
						columnName: v.columnName
					});
				}
			});
			that.tr.find("[columnName]").each(function() {
				that.fields.push($(this).attr("columnName"));
			});
		}
		var tmpTr = that.tr.clone(true);
		for (var i = 0; i <= that.tdIndex; i++) {
			tmpTr.find("td").eq(i).remove();
		}

		that.template = tmpTr.html();
		var table = that.tr.closest("table");
		var tbId = table.attr("tbId") || (new Date().getTime());
		table.attr("tbId", tbId);
		that.tr.attr({
			'data-role': 'k-batch',
			tId: "tr" + (new Date().getTime() + that.tr.index()),
			tbId: tbId
		});
		return that;
	},
	set: function(data, target) {
		if (data) {
			for (var key in data) {
				var dom = target.data("table", {
					tableName: this.rule.tableName
				}).data("row", data).find("[columnName='" + key + "']");
				if (dom && dom[0]) {
					dom.mtbootstrap("setValue", data[key]);
				}
			}
		}
		return this;
	},
	add: function(datas, target) {
		var that = this;
		target = target || $("." + that.tr.attr("tid") + ":last");
		if (datas) {
			for (var i = 0; i < datas.length; i++) {
				target = appendTr(datas[i]);
			}
		} else {
			appendTr();
		}

		function appendTr(data) {
			var $tr = target,
				$current = target;
			if (that.rows == 0) {

			} else {
				$tr = $("<tr>", {
					'class': that.tr.attr('tId')
				});
				target.after($tr);
			}
			$tr.append(that.template);
			var tmpId = new Date().getTime(),
				optionsKey = "optionsKey";
			data = data || {};
			$tr
				.find("[id]")
				.each(
					function() {
						var $tmp = $("#" + this.id),
							columnname = $tmp
							.attr("columnname");
						var opts = $tmp.data(optionsKey),
							datas;

						/*
						 * $(this).show().attr({ tempId : this.id, id : this.id +
						 * (tmpId++) });
						 */
						var id = this.id + (tmpId++);
						$(this).show().attr({
								tempId: this.id,
								id: id
							}).removeClass(this.id).find("." + this.id) //
							.removeClass(this.id).addClass(id);

						if ($tmp.attr("tmpval") == "true") {
							var tmpValue = $current ? $current.find("[columnname='" + columnname + "']").mtbootstrap("getValue") : null ;
							if(!data[columnname]){
								//若值不存在是，才取模板值。
								data[columnname] = tmpValue || ($tmp).mtbootstrap("getValue");	
							}
						}
						/*
						 * if (!opts) { if (datas = $tmp.data()) { for ( var k
						 * in datas) { if (k.toLowerCase() == ("kendo" + $(
						 * this).attr('data-role'))) { opts = { key : k, options :
						 * $.extend(true, {}, datas[k].options) };
						 * $tmp.data(optionsKey, opts); break; } } } } if (opts) {
						 * $(this)[opts.key](opts.options); }
						 */
						if (!opts) {
							if (datas = $tmp.data()) {
								if (datas.role && $tmp.data(datas.role)) {
									opts = {
										key: datas.role,
										options: $.extend(true, {},
											$tmp.data(datas.role).options)
									};
									$tmp.data(optionsKey, opts);
								}
							}
						}
						if (opts) {

							var key = $copyRole.get(opts.key) || opts.key;
							if($.fn[key]){
								var options = $copyRole.getOptions//
									.call(this, opts);
								$(this)[key](options);
							} else {
								var enabled = $copyRole.enabled($tmp);
								if(!enabled){
									$(this).attr("disabled", "disabled");
								}
							}
						}
					});
			that.initTrEvents($tr).rowspan(1).set(data, $tr);
			return $tr;
		}
		return that;
	},
	insert: function(datas,target,state) {
		var that = this;
		target = target || $("." + that.tr.attr("tid") + ":last");
		if (datas) {
			for (var i = 0; i < datas.length; i++) {
				target = appendTr(datas[i]);
			}
		} else {
			appendTr();
		}

		function appendTr(data) {
			var $tr = target,
				$current = target;
			if (that.rows == 0) {

			} else {
				$tr = $("<tr>", {
					'class': that.tr.attr('tId')
				});
				if(state == "top"){
					if(target[0].previousElementSibling.getAttribute("class")!=null){
						target.before($tr);						
					}
				}
				else if(state == "bot"){
					target.after($tr);
				}			
			}
			$tr.append(that.template);
			var tmpId = new Date().getTime(),
				optionsKey = "optionsKey";
			data = data || {};
			$tr
				.find("[id]")
				.each(
					function() {
						var $tmp = $("#" + this.id),
							columnname = $tmp
							.attr("columnname");
						var opts = $tmp.data(optionsKey),
							datas;

						/*
						 * $(this).show().attr({ tempId : this.id, id : this.id +
						 * (tmpId++) });
						 */
						var id = this.id + (tmpId++);
						$(this).show().attr({
								tempId: this.id,
								id: id
							}).removeClass(this.id).find("." + this.id) //
							.removeClass(this.id).addClass(id);

						if ($tmp.attr("tmpval") == "true") {
							var tmpValue = $current ? $current.find("[columnname='" + columnname + "']").mtbootstrap("getValue") : null ;
							data[columnname] = tmpValue || ($tmp).mtbootstrap("getValue");
						}
						/*
						 * if (!opts) { if (datas = $tmp.data()) { for ( var k
						 * in datas) { if (k.toLowerCase() == ("kendo" + $(
						 * this).attr('data-role'))) { opts = { key : k, options :
						 * $.extend(true, {}, datas[k].options) };
						 * $tmp.data(optionsKey, opts); break; } } } } if (opts) {
						 * $(this)[opts.key](opts.options); }
						 */
						if (!opts) {
							if (datas = $tmp.data()) {
								if (datas.role && $tmp.data(datas.role)) {
									opts = {
										key: datas.role,
										options: $.extend(true, {},
											$tmp.data(datas.role).options)
									};
									$tmp.data(optionsKey, opts);
								}
							}
						}
						if (opts) {

							var key = $copyRole.get(opts.key) || opts.key;
							if($.fn[key]){
								var options = $copyRole.getOptions//
									.call(this, opts);
								$(this)[key](options);
							} else {
								var enabled = $copyRole.enabled($tmp);
								if(!enabled){
									$(this).attr("disabled", "disabled");
								}
							}
						}
					});
			that.initTrEvents($tr).rowspan(1).set(data, $tr);
			return $tr;
		}
		return that;
	},
	remove: function(target) {
		if (confirm("确定删除?")) {
			if (this.tr[0] === target[0]) {
				alert('首行不能删除!');
			} else {
				var row = target.data("row"),
					table = target.data("table");
				if (row && table && window.batch) {
					var tableName = table.tableName;
					var del = {
						'delete': true,
						tableName: tableName,
						id: row["id"]
					};
					window.batch.deleteCollection.push(del);
				}
				target.remove();
				this.rowspan(-1);
				$(".k-batch-tools").hide();
			}
		}
		return this;
	},
	rowspan: function(n) {
		this.rows = this.rows + n;
		if (this.tdIndex > -1) {
			this.tr.find("td").eq(this.tdIndex).attr("rowspan", this.rows);
		}
		return this;
	},
	initTrEvents: function($this) {
		var that = this;
		var lastTd = $this.find('td:last'),
			$tools = $(".k-batch-tools");
		var $table = $this.closest("table");
		$this.off("mouseover").on(
			'mouseover',
			function(e) {
				var p = lastTd.offset();
				$tools.css({
					left: p.left + lastTd.css('width').replace(/px/gi,
						'') * 1 - 50,
					top: p.top
				}).show();
				return true;
			}).off("mouseout").on('mouseout', function(e) {
			var $target = $(e.target);
			if (!$target.is("td")) {
				$target = $target.closest("td");
			}
			if ($target.get(0) == lastTd[0]) {
				$tools.data("o", {
					fn: that,
					target: $this
				});
			} else {
				// if($table.get(0) != $target.closest("table").get(0))
				$tools.hide();
			}
			return true;
		});

		return that;
	}
};

function initProgressBarFunc() {
	var options = {
		'childList': true,
		'arrtibutes': true,
		'subtree': true,
		'characterData': true
	};
	var observer = new MutationObserver(
		function() {
			$(".mt-progressbar").each(
				function() {
					var $this = $(this);
					var data = JSON.parse($this.attr("data").replace(
						/\'/gi, '\"'));
					var val = $this.attr("tval");
					var $div = $('<div class="progress"><div class="progress-bar progress-bar-primary progress-bar-striped"><span class="sr-only">40%</span></div></div>');
					
					if($this.closest(".grid")[0]){
						//如果是grid和treelist内部，则去掉外间距
						$div.css("margin-bottom","0px");
						$div.find(".progress-bar").css("color","black");
					}
					$this.after($div[0]);
					try {
						var opts = {
							type: data.model,
							showStatus: data.showState,
							value: parseFloat(val)
						};
						if (data.min) {
							opts.min = parseFloat(data.min);
						}
						if (data.max) {
							opts.max = parseFloat(data.max);
						}
						$div.attr("style", $this.attr("style"));
						$div.progressBarExt(opts)
							/*
							 * $div.kendoProgressBar(opts);
							 * $div.find(".k-state-selected").css({
							 * "background-color" : "#ef1f0c", "border-color" :
							 * "#ef1f0c" });
							 */
					} catch (e) {
						console.log(e);
					}
					$this.remove();
				});
			// 多文件查看功能
			$(".mt-fileView")
				.each(
					function() {
						var $this = $(this);
						if ($this.attr("model") == "multi") {
							var htmlStr = $this.html(),
								tvalStr = $this.attr("tval"),
								splitStr = $this.attr("split") || " ",
								html, tval, i = 0,
								key = $this.attr("key"),
								url = $this.attr("url"),
								model = $this.attr("model"),
								dbid = $this.attr("dbid"),
								fid = $this.attr("fid"),
								htmls = htmlStr.split(","),
								tvals = tvalStr.split(","),
								length = htmls.length,
								targetStr = "";
							for (; i < length; i++) {
								if (htmls[i]) {
									targetStr += "<a href='#' fid='" + fid + "' kid='" + tvals[i] + "' dbid='"+dbid+"' model='" + model + "' key='" + key + "' url='" + url + "' onclick='viewFiles(this)'>" + htmls[i] + "</a>" + splitStr;
								}
							}
							if (targetStr) {
								targetStr = targetStr.substr(0,
									targetStr.length - 1);
								$this.after(targetStr);
								$this.remove();
							}
						}
					});
			// observer.disconnect();
		});
	observer.observe(document.body, options);
	document.body.appendChild(document.createElement("div"));
}