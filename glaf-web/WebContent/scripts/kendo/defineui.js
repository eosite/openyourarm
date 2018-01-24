var roleList = {
	grid: 'kendoGrid',
	combobox: 'kendoComboBox'
};

function getKendoData(jq) {
	if (!jq || !jq[0]) {
		return null;
	}
	var dataRole = jq.attr('data-role');
	return {
		dataRole: dataRole || 'text',
		kendo: jq.data(roleList[dataRole])
	};
}

function kendoReload(jq, params) {
	if (!jq || !jq[0]) {
		return false;
	} else {
		jq.mtkendo('reload', params);
	}
}

function getKendoValue(jq, key) {
	if (!jq || !jq[0]) {
		return null;
	} else {
		return jq.mtkendo('getValue', key);
	}
}

function setKendoValue(jq, val) {
	if (!jq || !jq[0]) {
		return false;
	} else {
		jq.mtkendo('setValue', val);
	}
}

// 按钮事件 klaus.wang 2015-05-14
function kendoButtonFunc(viewModel) {
	if (!viewModel.buttonType) {
		return false;
	}
	var functions = viewModel.functions;
	if (!functions) {
		functions = new KendoButton(viewModel);
	}

	if ((viewModel.buttonType in functions)) {
		functions[viewModel.buttonType]();
	} else {
		alert(viewModel.buttonType + ' 方法未找到');
	}
}

function KendoButton(vm) {

	this.viewModel = vm;

	this.init();
}

KendoButton.prototype.genReport = function() {
	var rule = this.viewModel.data.rule;
	var selectReportTemplate = rule.selectReportTemplate;
	var opts = selectReportTemplate[0];
	var paraType = rule.paraType;
	var $id = this.viewModel.id;

	var reportParamsArr = [];
	if (paraType && paraType.length > 0) {
		reportParamsArr = paraType[0].datas[$id];
	}

	var reportParams = {};
	$.each(reportParamsArr, function(index, val) {
		reportParams[val.param] = $("#" + val.inid).val();
	});
	$.ajax({
		url: contextPath + '/mx/form/defined/getDatabaseInfo?databaseCode=' + opts.dbCode,
		type: 'post',
		dataType: 'JSON',
		success: function(datakk) {
			$.ajax({
				url: opts.generateReportUrl,
				type: 'post',
				dataType: 'jsonp',
				data: {
					data: JSON.stringify({
						ReportTemplateId: opts.id,
						ReportParameters: reportParams,
						key: datakk.key,
						content: datakk.content
					})
				},
				// data: '{"ReportTemplateId":"' + opts.id +
				// '","ReportParameters":' + JSON.stringify(reportParams) +
				// ',"key":"'+datakk.key+'",content":"'+datakk.content+'"}',
				success: function(data) {
					if (data.ResultCode == 1) {
						if (data.ResultCode) {
							var resultData = data.ResultData;

							var p = {};
							p.templateId = opts.id;
							p.reportId = resultData.ReportId;
							p.pageCount = resultData.PageCount;
							p.content = datakk.content;
							p.key = datakk.key;
							$.each(paraType[0].datas, function(index, val) {
								if (val[0].type == 'setValue') {
									$("#" + val[0].outid).val(p[val[0].inid]);
								}
							});
						}
					}
					alert(data.ResultMessage);
				}
			});
		}
	});
};

KendoButton.prototype.init = function() {
	this.viewModel.data = new Object();
	if (this.viewModel.handlecolumns)
		this.viewModel.data.tableMsg = eval('(' + this.viewModel.handlecolumns + ')');
	if (this.viewModel.ruleData) {
		this.viewModel.data.rule = eval('(' + this.viewModel.ruleData + ')');
	}
	this.viewModel.data.pageParameters = pageParameters;
	this.viewModel.functions = this;
};

/**
 * 修改cell表
 */
KendoButton.prototype.edit_cell = function() {
	var K = this,
		editFunctions = K.editFuncs;
	jQuery.each(K.viewModel.data, function(id, v) {
		var $this = jQuery('#' + id),
			dataRole = K.kendoData($this).dataRole;
		if (editFunctions[dataRole]) {
			editFunctions[dataRole].call(K, $this);
		}
	});
};
KendoButton.prototype.editFuncs = {
	grid: function(jq) {
		var K = this,
			kendoData = K.kendoData(jq),
			grid = kendoData.kendo,
			rows = grid
			.select();
		if (rows && rows.length == 1) {
			var edit = $(rows[0]);
			if (edit && edit[0]) {
				grid.editRow(edit);
			}
		} else {
			alert('请选择一条记录!');
		}
	}
};

/**
 * 查询操作
 */
KendoButton.prototype.search = function() {
	var jq = jQuery(this.viewModel.change);

	var paraType = this.viewModel.data.rule.paraType,
		idRelation = {};
	if (paraType && paraType[0]) {
		var datas = paraType[0].datas;
		if (datas) {
			pubsub.pub("linkageControl", datas);
		}
	}

	/*
	 * if (jq[0]) jq.mtkendo('reload',{ paramType : 'query-button', prid :
	 * this.viewModel.prid, params : kendo.stringify(this.getSearchData()) });
	 */
};
// 查询方法
KendoButton.prototype.getSearchData = function() {
	if (this.viewModel.data) {
		jQuery.each(this.viewModel.data, function(id, json) {
			json.value = jQuery('#' + id).mtkendo('getValue', json.ColumnName);
		});
	}
	return this.viewModel.data;
};

/**
 * 删除操作
 */
KendoButton.prototype['delete'] = function() {
	var K = this,
		deleteFunctions = K.deleteFuncs;
	jQuery.each(K.viewModel.data.tableMsg, function(i, v) {
		var $this = jQuery('#' + v.id),
			dataRole = K.kendoData($this).dataRole;
		if (deleteFunctions[dataRole]) {
			deleteFunctions[dataRole].call(K, $this);
		}
	});
};
// 删除方法
KendoButton.prototype.deleteFuncs = {
	grid: function(jq) {
		var K = this,
			kendoData = K.kendoData(jq);
		var grid = kendoData.kendo;
		var rows = grid.select();
		if (rows && rows.length > 0) {
			if (K.viewModel.multiPlay == 'false') {
				if (rows.length > 1) {
					alert('请选择一条记录!');
					return false;
				}
			}
			if (confirm('你确定删除吗?')) {
				var models = new Array();
				jQuery.each(rows, function(i, item) {
					models.push(grid.dataItem(item));
				});

				var dataSource = grid.dataSource;
				$.each(models, function(i, dataItem) {
					dataSource.remove(dataItem);
					dataSource.sync();
				});
			}
		} else if (rows.length == 0) {
			alert('请选择!');
		}
	}
};

/**
 * 流程提交
 */
KendoButton.prototype.submit = function() {
	var that = this,
		data = that.viewModel.data;

	var process = {
		isDefined: function() {
			return true;
		},
		isUndefined: function() {
			alert('流程未定义!');
		}
	};
	if (data && data.rule) {
		var id = $("[fieldname=id]:hidden").val(),
			flow = mtxx.params.flow;
		var pageRuleId = $(".pageruleid").val(),
			params = {
				multiple: data.rule["multiple-flow"],
				rid: that.viewModel.prid,
				id: id,
				pageRuleId: pageRuleId,
				pageId: data.rule.pageId
			};
		if (objectISNotEmpty(flow)) {
			if (id) {
				var url = contextPath + "/mx/form/defined/ex/flowFeedback?" + $.param({
					fn: 'processGetData',
					pageId: data.rule.pageId
				});
				var win = jQuery.mt.window({
					id: pageRuleId,
					title: '流程审批',
					width: '750px',
					height: '350px',
					refresh: true,
					modal: true,
					content: {
						url: url,
						iframe: true
					},
					appendTo: 'body'
				});
				win.kendo.center();
			} else {
				alert("该流程未启动");
			}
		} else if (objectISNotEmpty(flow) || params.multiple) {
			var paraType = data.rule.paraType;
			if (paraType) {
				var flowParams = getParaType(paraType);
				if (flowParams) {
					if (!flowParams.isReady) {
						alert('流程参数不能为空!');
						return false;
					}
					params.flowParams = JSON.stringify(flowParams);
				}
			}

			var processData = null;
			if (!flow) {
				$.ajax({
					url: contextPath + "/mx/form/workflow/defined/getMultipleVariables",
					async: false,
					data: params,
					type: "POST",
					dataType: "JSON",
					success: function(bytes) {
						if (bytes && bytes[0]) {
							$.each(bytes, function(i, b) {
								var by = JSON.parse(b);
								if ((by = by[0]) && by.targets) {
									$.each(by.targets, function(i, v) {
										console.log(v);
										addVariables(v.eleId, v.taskId);
									});
								}
							});
							processData = processGetData() || {};
							$.extend(params, processData);
						}
						mtxx.params.flow = null;
					}
				});
			}

			if (confirm("你确定提交流程吗?")) {
				if (processData && !processData.id) {
					alert("请先保存!");
					return false;
				} else {
					$.post(contextPath + "/mx/form/workflow/defined/processSubmit",
						params,
						function(ret) {
							alert("流程提交成功! 流程实例ID为：" + ret.processInstanceId);
						}, "JSON");
				}
			}
		} else {
			process.isUndefined();
		}
	} else {
		process.isUndefined();
	}
};

function getParaType(paraType) {
	if (paraType && paraType[0]) {
		var datas = paraType[0].datas,
			params = {
				isReady: true
			},
			data, val;
		for (var key in datas) {
			if ((data = datas[key]) && data[0]) {
				$.each(data, function(i, v) {
					val = $("#" + v.inid).mtkendo('getValue', v.columnName);
					if (val) {
						params[v.param] = val;
					} else {
						params.isReady = false;
					}
				});
			}
		}
		return params;
	}
	return null;
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

function processCloseWin(id) {
	var win = $("#" + id);
	if (win[0]) {
		win.data("kendoWindow") ? win.data("kendoWindow").close() : null;
	}
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
		pageRuleId: pageRuleId
	};
	if (flow.variables) {
		$.each(flow.variables, function(i, v) {
			if (v.datas) {
				var expActVal = v.datas.expActVal;
				var o = new RegExp("~M\{([^\\}]*)\}", "g").exec(expActVal);
				if (o && o[1]) {
					params[v.varname] = $("#" + o[1]).val();
					params[v.varcode] = $("#" + o[1]).val();
				}
			}
		});
	}
	return params;
}

/**
 * 保存操作
 */
KendoButton.prototype.save = function($alert) {
	var K = this,
		data = new Array(),
		tables = new Object();
	// 验证
	if (K.viewModel.saveValidate) {
		var valis = $("[valisave=true]"),
			vali, $vali, length = valis.length,
			i = 0,
			bol, rebol = true;
		if (valis && valis.length) {
			for (; i < length; i++) {
				vali = valis[i];
				$vali = $(vali);
				if ($vali.data("role") === "cell") {
					var spreadValidate = $vali.data("spreadValidate"),
						spread = $vali
						.data("spread"),
						sheet = spread.getActiveSheet(),
						inids;
					for (var pro in spreadValidate) {
						inids = pro.split("-");
						bol = sheet.getDataValidator(inids[0], inids[1])
							.isValid();
						if (!bol)
							rebol = false;
					}
				} else {
					bol = $(vali).data("kendoValidator").validate();
					if (!bol)
						rebol = false;
				}
			}
			if (!rebol)
				return false;
		}
		// var bol = $("[valisave=true]").data("kendoValidator").validate();
		// if (!bol)
		// return false;
	}
	/**
	 * jQuery.each(K.viewModel.data, function(id, v) { var $this = jQuery('#' +
	 * id), params = $this.data('params') || v;
	 * 
	 * params.value = $this.mtkendo('getValue', params.fieldName);
	 * 
	 * if(params.value){ data.push(params); }
	 * 
	 * 
	 * });
	 */
	var vmd = K.viewModel.data;
	if (!vmd.tableMsg) {
		alert('未保存设置!');
		return false;
	}
	jQuery.each(vmd.tableMsg, function(i, tableMsg) {
		var container = {
			dataSetId: tableMsg.dataSetId,
			idValue: tableMsg.idValue,
			table: tableMsg.table,
			columns: new Array(),
			batch: tableMsg.batch,
			wdataSet: tableMsg.wdataSet
		};
		if (tableMsg.columns) {
			$.each(tableMsg.columns, function(index, v) {

				var $this = jQuery('#' + v.id),
					params = $this.data('params') || {};

				if (params.idValue && !container.idValue) {
					container.idValue = params.idValue;
				}

				v.value = $this.mtkendo('getValue', v.fieldName);
				// console.log(v.value);

				// if(v.value){
				container.columns.push(v);
				// }
			});
		}

		var tables = $("." + container.table.tableName);
		if (tables.length) {
			tables.each(function(i, v) {
				var $this = $(v);
				container.columns.push({
					fieldName: $this.attr('fieldName'),
					value: $this.val()
				});
			});
		}

		if (container.columns.length > 0) {
			if (tableMsg.batch || tableMsg.cell) {
				if (window.batch) {
					if (container.wdataSet && container.wdataSet.id) {
						window.batch.getValue(container.columns = []);
						data.push(container);
					} else {
						window.batch.getValue(data);
					}
				}
			} else {
				data.push(container);
			}
		}
	});

	if (data.length > 0) {

		var s = true;

		if (window.saveFileToUrl) {
			s = saveFileToUrl(true);
		}

		if (s) {

			var msg = data[0];
			if (msg && msg.wdataSet && msg.wdataSet.id) { // 更新集保存方式
				var saveMsg = [];

				function to(m, collection) {
					var obj = {
						id: m.idValue
					};
					$.each(m.columns, function(i, v) {
						if (m.batch) {
							to(v, collection);
						} else {
							obj[v.fieldName] = v.value;
						}
					});
					if (!m.batch)
						collection.push(obj);
				}

				$.each(data, function(i, m) {
					var t = {
						id: m.wdataSet.id,
						table: m.table,
						datas: []
					};

					to(m, t.datas);

					saveMsg.push(t);
				});

				$.ajax({
					url: contextPath + '/mx/form/data/saveFormData',
					type: 'POST',
					data: {
						// relation: relation ? JSON.stringify(relation) :
						// relation,
						tableMsg: JSON.stringify(saveMsg)
					},
					dataType: 'JSON',
					success: successFunc
						/*
						 * function(ret) { if (ret && ret.message) { alert(ret.message);
						 * $.each(saves, function(i, msg) { if (wdataSetId && msg.id !==
						 * wdataSetId) { return false; } window.dynamicCellDataSet(msg);
						 * }); } }
						 */
				});

				return false;
			}

			/**
			 * 返回主键保存
			 */
			function eachTableMsg(i, v, data) {
				var tmsg = data[v.table.tableName];
				if (tmsg) {
					v.idValue = tmsg.idValue || tmsg;
					!$("input." + v.table.tableName)[0] && ($("<input>", {
						'class': v.table.tableName,
						type: 'hidden',
						fieldname: 'id',
						value: v.idValue
					}).appendTo("body"));

					var $idVal = $("." + mtxx.params.id);
					if (!$idVal.get(0)) {
						$idVal = $("<input>", {
							'class': mtxx.params.id,
							type: 'hidden',
						}).appendTo("body");
					}

					$idVal.attr({
						idValue: v.idValue
					});

				}
				if (v.cell && window.batch) {
					window.batch.refresh();
				}
			}

			/**
			 * 保存成功回调
			 */
			function successFunc(data) {
				if (data && data.message && !data.rst) {
					alert(data.message);
				} else {
					if (!$alert)
						alert('操作成功!');
					$.each(vmd.tableMsg, function(i, v) {
						eachTableMsg(i, v, data);
					});
					if ($alert && $.isFunction($alert)) {
						$alert.call(window, data);
					}
				}
			}

			var saveUrl = contextPath + '/mx/form/data/saveOrUpdateDetail';
			$.ajax({
				url: saveUrl,
				type: 'post',
				data: {
					tableMsg: JSON.stringify(data)
				},
				async: true,
				dataType: 'json',
				success: function(data) {
					if (data && data.message) {
						alert(data.message);
					} else {
						if (data) {
							successFunc(data);
						}
						$(K.viewModel.change).mtkendo('reload', {});

						if (vmd.rule && vmd.rule.closeWindow == 'true') {
							K.closeWindow();
						}
					}
				}
			});
		}
	} else {
		// alert('请输入!');
	}
};

/**
 * 新建窗口
 */
KendoButton.prototype.newWindow = function(type) {
	var K = this,
		id = 'mtWindow_' + K.viewModel.id,
		rule = K.viewModel.data.rule,
		maximize = K.viewModel.maximize;
	var jumpType = rule.jumpType; // 页面跳转类型
	var getUrl = function(url, data) {
		var queryString = jQuery.param(data);
		return (url.indexOf("?") > -1) ? (url + "&" + queryString) : (url + "?" + queryString)
	};
	var jumpFuncs = {
		childPage: { // 子窗口(弹出)
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {

					var win = jQuery.mt.window({
						id: id,
						title: rule.title || '查看',
						width: rule.width || '650px',
						height: rule.height || '450px',
						refresh: true,
						modal: rule.modal,
						content: {
							// url: url + '?' + jQuery.param(data),
							url: getUrl(url, data),
							iframe: true
						},
						appendTo: 'body'
					});
					win.kendo.center();
					if (maximize)
						win.kendo.maximize();
				});
			},
			close: function(win) {
				jQuery('#' + id).data('kendoWindow').close();
			}
		},
		singlePage: { // 跳转页面
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {
					// window.open(url + '?' + jQuery.param(data));
					window.open(getUrl(url, data));
				});
			},
			close: function(win) {
				win.close();
			}
		}
	};
	if (!type) {
		if (jumpFuncs[jumpType]) {
			jumpFuncs[jumpType].open();
		}
	}
	return jumpFuncs[jumpType];
};

/**
 * 获取分组参数 (如果有相同的参数名，应该放到分组里面传JSON过去)
 * 
 * @param datas
 * @returns
 */
function getInOutParameters(datas) {
	if (!datas) {
		return {};
	}
	var sortType = {},
		sort = false, //
		param = pubsub.getParameters(datas);
	for (var k in datas) {
		var params = datas[k];
		if (params && params.length) {
			$.each(params, function(i, v) {
				if (v.sortType) {
					!sortType[v.sortType] && (sortType[v.sortType] = {});
					!sortType[v.sortType][k] && (sortType[v.sortType][k] = []);
					sortType[v.sortType][k].push(v);
					sort = true;
				}
			});
		}
	}
	if (sort) {
		var sortParams = {};
		$.each(sortType, function(k, v) {
			sortParams[k] = pubsub.getParameters(v);
		});
		param = $.extend((param || {}), {
			params: JSON.stringify(sortParams)
		});
	}
	return param;
}

// 窗口方法
KendoButton.prototype.newWinFuncs = {
	init: function(K, fn) {
		var paraType = K.viewModel.data.rule.paraType,
			idRelation = {};
		var url = contextPath + '/mx/form/page/viewPage';
		/*
		 * if (paraType && paraType[0]) { var datas = paraType[0].datas; if
		 * (datas) { $.each(datas, function(k, v) { if (!idRelation[v[0].inid]) {
		 * idRelation[v[0].inid] = {}; } v[0].items = [];
		 * idRelation[v[0].inid][v[0].param] = v[0]; }); } } var id,v,has =
		 * false; var commonParam = { newWin : K.viewModel.buttonType, id :
		 * K.viewModel.pageId, prid : K.viewModel.prid, btnId : K.viewModel.id };
		 * for(id in idRelation){ has = true; var $this = jQuery('#' + id),
		 * dataRole = K.kendoData($this).dataRole; if ($this[0]) {
		 * $this.data("idRelation", idRelation[id]); } if
		 * (K.newWinFuncs[dataRole]) { var data = K.newWinFuncs[dataRole](K,
		 * $this); if (data && fn) { fn.call($this, url,
		 * $.extend(data,commonParam)); } } else if(fn){ fn.call($this, url,
		 * commonParam); break; } }
		 * 
		 * if(!has){ if(fn){ fn.call({}, url,commonParam); } }
		 */
		var has = false,
			params = null,
			commonParam = {
				newWin: K.viewModel.buttonType,
				id: K.viewModel.pageId,
				prid: K.viewModel.prid,
				btnId: K.viewModel.id
			};
		if (paraType && paraType[0]) {
			var datas = paraType[0].datas;
			if (datas) {
				for (var k in datas) {
					if (k.indexOf("/mx/") > -1) {
						url = contextPath + k;
						break;
					}
				}
				// params = pubsub.getParameters(datas);
				params = window.getInOutParameters(datas);
			}
		}

		var linkPageJson = K.viewModel.data.rule.linkPageJson;
		if (linkPageJson) {
			var pageType = linkPageJson.pageType;
			var emu = {
				'report-page': '',
				'relate-page': ''
			};
			if (pageType && (pageType in emu)) {
				if (linkPageJson.url) {
					url = contextPath + linkPageJson.url;
				}
				if (pageType == 'relate-page' && commonParam) {
					$.extend(commonParam, {
						fn: 'odblClickCallFunc_',
						targetId: K.viewModel.data.rule.id
					});
				}
			}
		}

		/**
		 * 有参数时执行
		 */
		if (params != null) {
			has = true;
			if (fn) {
				fn(url, $.extend(params, commonParam));
			}
		}

		/**
		 * 无参数时...
		 */
		if (!has) {
			if (fn) {
				fn.call({}, url, commonParam);
			}
		}

		/*
		 * jQuery.each(K.viewModel.data, function(id, v) { var $this =
		 * jQuery('#' + id), dataRole = K.kendoData($this).dataRole; if
		 * ($this[0]) { $this.data("idRelation", idRelation[id]); } if
		 * (K.newWinFuncs[dataRole]) { var data = K.newWinFuncs[dataRole](K,
		 * $this); if (data && fn) { fn.call($this, url, data); } } else if(fn){
		 * fn.call($this, url, { id : K.viewModel.pageId }); return; } });
		 */
	},
	grid: function(K, jq) {
		var kendoData = K.kendoData(jq),
			grid = kendoData.kendo,
			rows = grid
			.select();
		if (rows && rows.length > 0) {
			if (K.viewModel.multiPlay == 'false') {
				if (rows.length > 1) {
					alert('请选择一条记录!');
					return false;
				}
			}
			var pageId = K.viewModel.pageId;
			if (pageId) {
				var ids = new Array();

				var relation = jq.data("idRelation"),
					ret = {};

				jQuery.each(rows, function(i, v) {
					if (relation) {
						$.each(relation, function(col, item) {
							item.items.push(grid.dataItem(v)[item.columnName]);
						});
					} else
						ids.push(grid.dataItem(v).id);
				});

				if (relation) {
					$.each(relation, function(k, item) {
						ret[k] = item.items.join('_0_');
					});
				}
				ret.newWin = K.viewModel.buttonType;
				ret.id = K.viewModel.pageId;
				ret.prid = K.viewModel.prid;
				ret.btnId = K.viewModel.id;

				return ret;
				/*
				 * return { newWin : K.viewModel.buttonType, id : pageId,
				 * parentId : '[' + ids.join(',') + ']' , prid :
				 * K.viewModel.prid, btnId : K.viewModel.id };
				 */
			}
		} else {
			alert('请选择!');
			return false;
		}
	}
};

/**
 * 关闭操作
 */
KendoButton.prototype.closeWindow = function() {
	if (confirm('你确定关闭窗口吗?')) {
		var pageParameters = this.viewModel.data.pageParameters;
		if (pageParameters) {
			var rst = KendoButtonClose(window);
			if (!rst) {
				window.close();
			}
			/*
			 * var btnId = pageParameters.btnId, vm; if (btnId) { var parent =
			 * opener || window.parent; vm = parent.$('#' +
			 * btnId).mtkendo('getViewModel');
			 * parent.$(vm.change).mtkendo('reload', {}); if
			 * (pageParameters.newWin) {
			 * vm.functions[pageParameters.newWin](true).close(window); } } else {
			 * window.close(); }
			 */
		} else {
			window.close();
		}
	}
};

var findWin = 0;

function KendoButtonClose(win) {
	if (pageParameters) {
		var params = pageParameters,
			vm;
		var parent = win.opener || win.parent;
		if (params && params.btnId) {
			var btn = parent.$('#' + params.btnId);
			vm = btn.mtkendo('getViewModel');
			parent.$(vm.change).mtkendo('reload', {});
			if (params.newWin) {
				vm.functions[params.newWin](true).close(win);
				return true;
			}
		} else {
			if (findWin++ == 3) {
				return false;
			}
			return KendoButtonClose(parent);
		}
	}
	return false;
}

KendoButton.prototype.kendoData = function(jq) {
	return jq.mtkendo('getKendoData');
};

var mtxx = {

};

function getCompensate() {

}
// 初始化页面层次
function initPageLevel() {
	var parent,
		parentPageLevel;
	try {
		parent = window.parent;
		parentPageLevel = parent.pageLevel;
	} catch (e) {

	}
	/*
	 * if (parentPageLevel && !isWeighting) { pageLevel = parentPageLevel + 1; }
	 * else { pageLevel = 1; }
	 */
	if (parentPageLevel) {
		pageLevel = parentPageLevel + 1;
	} else {
		pageLevel = 1;
	}
	if (pageLevel != 1) {
		if (isWeighting) { // 合并事件
			// pageed =
			// JSON.stringify($.merge(JSON.parse(pageed),JSON.parse(parent.pageed)))
			var pageedJson = JSON.parse(pageed);
			pageed = parent.pageed || {};
			pageed[pageLevel] = pageedJson;
		} else {
			pageed = parent.pageed;
		}
	} else {
		if (pageed) {
			var pageedJson = JSON.parse(pageed);
			pageed = {};
			pageed[pageLevel] = pageedJson;
		}
	}
	if (pageed) {
		for (var plevel in pageed) {
			var pageEvents = pageed[plevel],
				len = pageEvents.length,
				pageEvent, triggers, trigger, thref = window.location.href,
				stockMoObj = {};
			for (var i = 0; i < len; i++) {
				pageEvent = pageEvents[i];
				triggers = pageEvent.trigger;
				for (var j = 0; j < triggers.length; j++) {
					trigger = triggers[j];
					if ((trigger.level == pageLevel || (trigger.level == pageLevel - plevel + 1)) && thref.indexOf(trigger.pageId) != -1) { // 控件事件的层级
						// TODO 注册事件
						var $this = $('#' + trigger.eleId),
							r = trigger.eleId.length > 25 ? "page" : pubsub.getRole(trigger.eleId),
							nb = (r == "button" ? "default" : r),
							t = $this.attr('data-role') || nb;

						if (trigger.otype) {
							$this = $('#' + trigger.oid);
							t = trigger.otype;
						}
						//变长区事件特殊注册
						var columnnameKey = "columnname",
							columnname = $this.attr(columnnameKey);
						if (columnname) {
							var $table = $this.closest('table'),
								eventType = trigger.eventType,
								index = $this.closest("tr").index();
							if ("listenVal" == eventType) {
								var moObj = stockMoObj[$table.attr("tbid")] || (stockMoObj[$table.attr("tbid")] = {
										target: $table,
										stock: []
									}),
									stock = moObj.stock;
								stock.push({
									columnname: columnname,
									pageEvent: pageEvent,
									index: index
								});
								eventType = "change";
							}
							$table.on(eventType + "." + columnname, "[" + columnnameKey + "=" + columnname + "]", {
								index: index,
								pageEvent: pageEvent
							}, function(e) {
								pubsub.elongateStockIndex = $(this).closest("tr").index() - e.data.index;
								pubsub.pub(e.data.pageEvent, "");
								pubsub.elongateStockIndex = null;
							});
						} else {
							var fn = pubsubobjects[t] || pubsubobjects["default"];
							fn.call($this, {
								pageEvent: pageEvent,
								trigger: trigger,
								eventType: trigger.eventType
							});
						}
					}
				}
			}
			for (var tbid in stockMoObj) {
				var stock = stockMoObj[tbid];
				stock.target.data("moStock", stock);
				var ink = new MutationObserver(function(record) {
					$.each(record, function(index, el) {
						var columnname = el.target.getAttribute ? (el.target.getAttribute("columnname") || $(el.target).closest('[data-role]').attr("columnname")) : null;
						var tstock = $(el.target).closest('table').data("moStock")["stock"];
						if ((columnname && el.attributeName.indexOf("setvalue") != -1)) {
							$.each(tstock, function(i, v) {
								if (v.columnname == columnname) {
									pubsub.elongateStockIndex = $(el.target).closest("tr").index() - v.index;
									pubsub.pub(v.pageEvent, "");
									pubsub.elongateStockIndex = null;
								}
							});
						} else if (el.removedNodes.length) {
							$.each(tstock, function(i, v) {
								pubsub.elongateStockIndex = $(el.previousSibling).closest("tr").index() - v.index;
								pubsub.pub(v.pageEvent, "");
								pubsub.elongateStockIndex = null;
							});
						}
					});
				});
				ink.observe(stock.target[0], {
					'attributes': true,
					'subtree': true,
					'childList': true,
					'attributesFilter': ['setvalue']
				});
			}
		}
	}
	initPageValidate(validateObj);
}
initPageValidate.prototype.customRule = function(k) {
	var mtValidate = k.data("mtValidate"),
		trigger = k.data("mtValiObj");
	// 执行表达式
	var execObj = pubsub._getInParams(mtValidate.execExpData, trigger.inlev),
		exec = pubsub
		.expConvert(mtValidate.execExp || "", execObj);
	if (exec != "") {
		var execBol = eval(exec);
		if (!execBol)
			return true;
	}
	// 验证表达式
	var eObj = pubsub._getInParams(mtValidate.expData, trigger.inlev),
		exp = pubsub
		.expConvert(mtValidate.exp || "", eObj);
	if (exp != "") {
		var expBol = eval(exp);
		if (expBol)
			return true;
		else
			return false;
	}
	return true;
};
// 初始化页面验证方法
function initPageValidate(ruleObj) {
	function bindValidateFunc($this, vali) {
		var types = vali.type.split(","),
			type;
		$.each(types, function(i, v) {
			switch (v) {
				case "validate_type_foucs": // 聚焦
					/*
					 * $this.on('blur', function(event) {
					 * $(this).data("kendoValidator").validate(); });
					 */
					ruleObj.validate_type_foucs($this);
					break;
				case "validate_type_save":
					/* $this.attr("valiSave", "true"); */
					ruleObj.validate_type_save($this);
					break;
				default:
					break;
			}
		})
	}
	var parent = window.parent;
	if (pageLevel != 1)
		mtValidate = parent.mtValidate;
	if (mtValidate) {
		var pageValidate = JSON.parse(mtValidate),
			len = pageValidate.length,
			vali, triggers, trigger, tlen, $target;
		for (var i = 0; i < len; i++) {
			vali = pageValidate[i];
			triggers = vali.trigger;
			tlen = triggers.length;
			for (var j = 0; j < tlen; j++) {
				trigger = triggers[j];
				$target = pubsub.getJQObj(trigger, true);
				if (trigger.itype == "cell") { // cell表达式验证
					var $jq = pubsub.getJQObj(trigger, true),
						spread = $jq
						.data("spread"),
						sheet = spread.getActiveSheet(),
						inid = trigger["inid"],
						inids = inid
						.split("-"),
						spreadValidate = $jq
						.data("spreadValidate") || {};
					trigger.vali = vali;
					spreadValidate[inid] = trigger;
					$jq.data("spreadValidate", spreadValidate);
					if (vali.type && vali.type.indexOf("validate_type_save") > 0) {
						$jq.attr("valiSave", "true");
					}
					// 绑定
					/*
					 * if(j==0){
					 * spread.bind(GcSpread.Sheets.Events.EditChange,function(sender,
					 * args){ console.log(sender); console.log(args); var $host =
					 * $(args.sheet.parent.getHost()); }); }
					 */
				} else if ($target) {
					$target.data("mtValidate", vali).data("mtValiObj", trigger)
						.attr("mtValidate", "true").attr("mtTitle",
							$target.attr("title"));
					bindValidateFunc($target, vali);
				}
			}
		}
		ruleObj.bindEvent();
	}
};
var pubsubobjects = {
	'default': function(e) {
		if ("changeByName" == e.eventType) {
			$('input[name=' + $(this).attr('name') + ']').on('change',
				function(event) {
					// 事件方法
					mtxx.e = event;
					pubsub.pub(e.pageEvent, "");
				});
		} else if ("listenVal" == e.eventType) {
			var ink = new MutationObserver(function(record) {
				pubsub.pub(e.pageEvent, "");
			});
			ink.observe($(this)[0], {
				'attributes': true,
				'characterData': true
			});
			$(this).on("change", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else {
			$(this).on(e.eventType, function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	ztree: function(e) { // ztree 事件
		var ztreeObj = $.fn.zTree.getZTreeObj(this.attr('id'));
		if (e.eventType == "clickEvent") {
			ztreeObj.setting.callback.onClick = function(event, treeId,
				treeNode) {
				pubsub.pub(e.pageEvent, "", treeId, treeNode);
			}
		}
	},
	grid: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id),
			g = $this
			.data('kendoGrid');
		if (e.eventType == "gchange") {
			g.bind('change', function(ex) {
				$this.kendoGridClick(id, e.pageEvent, "", ex);
				// pubsub.pub(e.pageEvent, "", ex);
			})
		} else if (e.eventType == "dbclick") {
			$this.kendoGridDblClick(id, e.pageEvent);
		} else if (e.eventType == "loadEnd") {
			g.bind("dataBound", function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}
	},
	treelist: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id),
			g = $this
			.data('kendoTreeList');
		if (e.eventType == "gchange") {
			g.bind('change', function(ex) {
				$this.kendoGridClick(id, e.pageEvent, "", ex);
				// pubsub.pub(e.pageEvent, "", ex);
			})
		} else if (e.eventType == "dbclick") {
			$this.kendoGridDblClick(id, e.pageEvent);
		}
	},
	dropdownlist: function(e) {
		var g = $("#" + this.attr("id")).data('kendoDropDownList');
		if (e.eventType == "change") {
			g.bind('change', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		} else if (e.eventType == "loadEnd") {
			g.bind('dataBound', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}
	},
	tabstrip: function(e) {
		var tabStrip = this.data("kendoTabStrip");
		if (e.eventType == "activated") { // 激活事件
			tabStrip
				.bind(
					"activate",
					function(ex) {
						var $tabStrip = $(ex.sender.element),
							$content = $(ex.contentElement);
						if ($tabStrip.attr("model") == "noframe") {
							var eles = $content.find("#" + e.pageEvent['in'][0].eleId);
							if (!eles.length) {
								return;
							}
							pubsub.pub(e.pageEvent, "", "#" + ex.contentElement.id);
						} else {
							var iframe = $content.find('iframe');
							if (!iframe.attr('src') || iframe
								.attr('src')
								.indexOf(
									e.pageEvent['in'][0].pageId) == -1) {
								return;
							}
							var win = iframe[0].contentWindow;
							// 初次执行
							var options = {
								'childList': true,
								'arrtibutes': true,
								'subtree': true,
								'characterData': true
							};
							var k = new MutationObserver(function() {
								pubsub.pub(e.pageEvent, "", "#" + ex.contentElement.id);
								k.disconnect();
								win.t = true;
							});
							var body = win.document.body;
							if (body) {
								k.observe(body, options);
							}
							if (win.t) {
								pubsub.pub(e.pageEvent, "", "#" + ex.contentElement.id);
							} else {
								if (body && iframe.data("onReadyState")) {
									// setTimeout(function(){
									body.appendChild(document
										.createElement("div"));
									// },350);
								}
							}
							/*
							 * if(iframe.data("onReadyState")){
							 * setTimeout(function(){ //延时执行
							 * pubsub.pub(e.pageEvent,"","#"+ex.contentElement.id);
							 * },450); }
							 */
						}
					});
		}
	},
	button: function(e) {
		var events = $(this).attr("t-events");
		if (events == 'true') { // 动态事件

			/*
			 * var id = $(this).attr("id"); var $a = $("a[btn-id=" + id + "]");
			 * var definedTable = $a.closest("[data-role=definedTable]");
			 * 
			 * if (definedTable[0]) {
			 * 
			 * var $event = definedTable.data("event") || {};
			 * 
			 * definedTable.data("event", $event);
			 * 
			 * $event[id] = { id : id, e : e };
			 * 
			 * definedTable.off(e.eventType, "a").on(e.eventType, "a",
			 * function(event) { var $this = $(this), btnId =
			 * $this.attr("btn-id"), eve = $event[btnId]; if (eve && eve.id) {
			 * mtxx.e = event; pubsub.pub(eve.e.pageEvent, ""); } }); } else {
			 * $a.each(function(i, a) { pubsubobjects["default"].call($(a), e);
			 * }); }
			 */
			var targetEle = /*"[data-role=definedTable] [btn-id]"*/ "[data-role] [btn-id][t-events=true]",
				key = "event";
			$(this).data(key, e);
			$(document.body).off(e.eventType, targetEle).on(
				e.eventType,
				targetEle,
				function(event) {
					event.preventDefault();
					var btnId = $(this).attr("btn-id"),
						$btn = $("#" + btnId);
					if ($btn[0]) {
						var eve = $btn.data(key);
						if (eve) {
							mtxx.e = event;
							pubsub.pub(eve.pageEvent, "");
						}
					}
				});

		} else {
			pubsubobjects["default"].call(this, e);
		}
	},
	page: function(e) {
		if (e.eventType == "pageInit") {
			jqCb.add(function() {
				pubsub.pub(e.pageEvent);
			});
		}
	},
	jsgis: function(e) {
		var jsgisEvent = function(e, eventName) {
			var $this = $("#" + e.trigger.eleId),
				ary = $this.data(eventName) || [];
			ary
				.push(function(kk) {
					var bol = false,
						isCurrentPoint = false; // 当前点是否是当前弹窗位置的点
					$
						.each(
							e.pageEvent.ops,
							function(i, v) {
								for (var p in v.param) {
									var dps = v.param[p];
									$
										.each(
											dps,
											function(i, v) {
												if (v.param == "point" && v.columnName == kk.graphic.symbol.column)
													bol = true;
												if (v.param != "point" && v.columnName == kk.graphic.symbol.column)
													isCurrentPoint = true;
											});
								}
							})
					if (bol)
						pubsub.pub(e.pageEvent, "", kk, isCurrentPoint);
				}) && $this.data(eventName, ary);
		}
		if (e.eventType == "jsgisInit") {
			mapCb.add(function() {
				pubsub.pub(e.pageEvent);
			});
		} else if (e.eventType == "clickEvent") { // 单击事件
			jsgisEvent(e, "clickEventList");
		} else if (e.eventType == "dbclick") { // 双击事件
			jsgisEvent(e, "dbclickList");
		} else if (e.eventType == "mouseover") { // 鼠标移入
			jsgisEvent(e, "mouseoverList");
		} else if (e.eventType == "mouseup") { // 鼠标移出
			jsgisEvent(e, "mouseupList");
		}
	},
	definedTable: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "loadEndExecOne") {
			jq.definedTable('loadEndExecOne', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		} else if (e.eventType == "loadEnd") {
			jq.definedTable('loadEnd', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}
	},
	treelistbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "gchange") {
			$this.treelist({
				events: {
					onClickRow: function(index, row) {
						pubsub.pub(e.pageEvent, "", index, row);
					}
				}
			});
			// pubsub.pub(e.pageEvent, "", ex);

		}
	},
	gridbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "gchange") {
			$this.grid({
				events: {
					onClickRow: function(index, row) {
						pubsub.pub(e.pageEvent, "", index, row);
					}
				}
			});
			// pubsub.pub(e.pageEvent, "", ex);

		}
	},
	cell: function(e) {
		$this = this;
		var eventsRule = $this.data("eventsRule") || {};
		eventsRule[e.trigger.eleId] = function(ex) {
			// console.log(ex);
			pubsub.pub(e.pageEvent, "", ex);
		}
		$this.data("eventsRule", eventsRule);
	},
	charts: function(e) {
		var $this = this;
		if (e.eventType == "clickEvent") {
			$this.data("clickEvent", function(ex) {
				pubsub.pub(e.pageEvent, "", ex, this);
			});
		}
	},
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
					var $div = $("<div></div>");
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
						$div.kendoProgressBar(opts);
						$div.find(".k-state-selected").css({
							"background-color": "#ef1f0c",
							"border-color": "#ef1f0c"
						});
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

function initKendoUploadFunc() {
	$("[data-role=upload]").each(function() {
		initKendoUpload(this, this.id, $(this).attr("randomparent"));
	});
}
//

//

// 页面初始化执行方法
function pageInit() {
	if (pageParameters) {
		var params = pageParameters;
		if (params) {
			mtxx.params = params;
			var ret = params['detail'];
			initPageDetailSuccess(ret);
		}
		// initPageDetail(p);
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
			async: false,
			beforeSend: function() {
				// $body.mask();
			},
			success: initPageDetailSuccess
		}).always(function() {
			// $body.mask("hide");
		});
	}
}

/**
 * 页面初始化赋值
 * 
 * @param ret
 * @returns
 */
function initPageDetailSuccess(ret) {
	if ((window.$ret = ret)) {
		if (ret.data) {
			ret.jdoms = [];
			$.each(ret.data, function(id, v) {
				jdom = $("#" + id);
				if (jdom[0]) {
					jdom.mtkendo('setValue', v[0].value || jdom.val(), v);
					if (!id.startsWith("ztree")) {
						jdom.data("params", v[0]);
					}
					ret.jdoms.push({
						jdom: jdom,
						id: id
					});
				}
			});
			linkAge(ret); // 联动页面控件、
			initPageFlow(ret); // 页面工作流
		}
		if (ret.handleColumns) {
			var $body = document.body,
				saveKey = "saveMsg";
			!$.data($body, saveKey) && ($.data($body, saveKey, []));
			var btn = new KendoButton({
				handlecolumns: JSON.stringify(ret.handleColumns)
			});
			$.data(document.body, saveKey).push(btn);
		}
	}
	initProgressBarFunc();
	initKendoUploadFunc();
	initImageUploadFunc();
	//

	initPageLevel();
	jqCb.fire();
}

/**
 * 页面工作流
 * 
 * @param o
 */
function initPageFlow(o) {
	if (o.jdoms.length) {
		$.ajax({
			url: contextPath + '/mx/form/data/initPageFlow',
			type: 'post',
			dataType: 'JSON',
			data: {
				idValue: o.__idValue,
				pageId: mtxx.params.id
			},
			success: function(ret) {
				if (ret.flowDefined) {
					$.data(document.body, "flowFilter", new FlowFilter(ret.flowDefined).exec(o.jdoms));
				}
			}
		});
	}
}

/**
 * 工作流权限过滤
 * 
 * @param flow
 * @returns
 */
function FlowFilter(flow) {

	this.flow = flow;

	this.elements = flow.elements;

	this.exec = function(doms) {
		var that = this;
		$.each(doms, function(i, v) {
			that.filter(v.jdom, v.id);
		});
		$.each(that.elements, function(id, element) {
			if (!element.ready) {
				var jq = $("#" + id);
				jq[0] && (that.filter(jq, id));
				element.$jq = jq;
			}
		});
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

			var value = jq.mtkendo("getValue");

			jq.mtkendo("setValue", value || f.defaultVal || '');
			// if (f.target) { // 接收人 (已经放到全局参数里面)
			// that.target(id);
			// }
			if (!f.show) { // 不显示
				jq.toggle();
				return false;
			}
			if (!f.write) {
				jq.attr({
					readonly: true
				});
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
				addVariables(v.eleId, v.taskId);
			});
		}
	})(this);
}

/**
 * 获取选择用户类型[角色，部门，人]
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

/**
 * 工作流参数
 * 
 * @param id
 * @param taskId
 * @returns
 */
function addVariables(id, taskId) {
	var jq = $("#" + id);
	var selectType = getSelectType(jq);
	!mtxx.params.flow && (mtxx.params.flow = {});
	!mtxx.params.flow.variables && (mtxx.params.flow.variables = []);
	mtxx.params.flow.variables.push({
		datas: {
			expActVal: "~M{" + id + "}"
		},
		varcode: taskId,
		varname: taskId + "_" + selectType
	});
};

/**
 * 流程实例函数 撤销、终止...
 * @author klaus.wang
 */
var FlowProcess = {
	STOP: 1,
	CANCEL: 2,
	ACTIVE: 3,
	REJECT: 4
};


FlowProcess.callWindow = function(args, sfn, efn) {
	$.extend(FlowProcess, {
		args: args,
		sfn: sfn,
		efn: efn
	});
	var url = contextPath + //
		"/mx/form/defined/ex/flowProcessFeedback?fn=FlowProcessFunc&&taskId=" + (args.taskId || '') + "&" + $.param({
			processId: args.processId,
			type: args.type
		});

	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: false,
		title: "流程操作",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		//	offset : [ '', '' ],
		fadeIn: 100,
		area: [650, 400],
		iframe: {
			src: url
		}
	});
};

function FlowProcessFunc(args) {
	args = $.extend(true, (FlowProcess.args || {}), args || {});
	var url = contextPath + //
		"/mx/form/workflow/defined/flowProcess";
	$.ajax({
		url: url,
		data: args,
		type: 'post',
		dataType: 'json',
		success: FlowProcess.sfn || function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
				FlowProcessFunc.close();
			}
		},
		error: FlowProcess.efn || function(ret) {
			alert("服务器处理出错!");
		}
	});
}

FlowProcessFunc.close = function() {
	closeLayer();
}

/**
 * 流程终止
 */
FlowProcess.stop = function(args) {
	if (!confirm("你确定终止流程吗?")) {
		return false;
	}
	FlowProcess.callWindow({
		type: FlowProcess.STOP,
		processId: args.processId
	});
}

/**
 * 流程挂起
 */
FlowProcess.cancel = function(args) {
	if (!confirm("你确定挂起流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type: FlowProcess.CANCEL,
		processId: args.processId
	});
}

/**
 * 流程激活
 */
FlowProcess.active = function(args) {
	if (!confirm("你确定激活流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type: FlowProcess.ACTIVE,
		processId: args.processId
	});
}

/**
 * 流程撤回
 */
FlowProcess.reject = function(args) {
	if (!confirm("你确定撤回流程吗?")) {
		return false;
	}
	FlowProcess.callWindow({
		type: FlowProcess.REJECT,
		processId: args.processId,
		taskId: args.taskId
	});
}

///////流程实例函数 end/////////////////

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
							'class': key,
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
				'class': 'pageruleid ' + mtxx.params.id,
				type: 'hidden',
				value: ret.pageRuleId,
				idValue: ret.__idValue
			}).appendTo("body");
		}
		if (ret.batchRules && ret.batchRules[0]) { // 变长区数据获取
			if (ret.batchRules[0].cell) {
				new CellBatchFunc(ret.batchRules[0]);
			} else {
				new BatchFunc(ret.batchRules);
			}
		}
	}
}

function CellBatchFunc(rule) {
	this.rule = rule;
	this.hashField = {};
	this.topId = null;
	this.index_id = null;
	this.init();
}

CellBatchFunc.prototype = {
	constructor: CellBatchFunc,
	refresh: function() {
		this.init();
	},
	init: function() {
		var that = this;
		var rule = this.rule,
			dataSetId = rule.dataSetId;
		var topTableName = rule.topTableName,
			tableName = rule.table.tableName;
		var topIdField = $("." + topTableName + "[fieldName=id]");
		if (!topIdField[0]) {
			topIdField = $("." + topTableName + "[fieldName=topid]");
		}
		window.batch = that;
		if (dataSetId && topIdField.val() && rule.columns.length) {
			that.topId = rule.topId = topIdField.val();

			$.each(rule.columns, function(i, v) {
				that.hashField[v.fieldName] = v;
			});

			initPageBatch({
					cell: true,
					dataSetId: dataSetId,
					topId: topIdField.val(),
					topTableName: topTableName
				},
				function(data) {
					var rows = data.rows;
					if (rows && rows.length > 0) {
						var tmp = that.hashField,
							key, field, id, input, fieldInput;
						$.each(rows,
							function(i, v) {
								if (!that.index_id) {
									that.index_id = v[tableName + "_0_index_id"];
								}
								if (!v[tableName + "_0_id"]) {
									return false;
								}
								for (key in tmp) {
									field = tableName + "_0_" + key;
									fieldInput = $("[name=" + tmp[key].id + "]");
									if (fieldInput && fieldInput[0]) {
										fieldInput.attr({
											t: key
										});
										input = fieldInput.eq(i);
										if (input && input[0]) {
											input.val(v[field]).data(
												"row", v);
										}
									}
								}
							});
					}
				});
		}
	},
	getValue: function(tables) {

		var that = this,
			rules = that.rule,
			fieldName, key, tmp = that.hashField,
			length, input;

		if (tmp) {
			for (key in tmp) {
				length = $("[t=" + key + "]").length;
				if (length)
					break;
			}
			var i, fieldInput, input, obj, collection, table, isok, index_id = that.index_id;

			if (!index_id) {
				initPageBatch({
					dataSetId: dataSetId,
					topId: topIdField.val()
				}, function(data) {

				});
			}

			for (i = 0; i < length; i++) {
				collection = new Array();
				isok = false;
				table = {
					idValue: null,
					table: {
						tableName: rules.table.tableName
					},
					columns: collection
				};

				for (key in tmp) {
					obj = new Object();
					fieldInput = $("[t=" + key + "]");
					if (fieldInput && fieldInput[0]) {
						input = fieldInput.eq(i);
						if (input && input[0]) {
							var row = input.data("row");
							if (row && !table.idValue) {
								table.idValue = row[rules.table.tableName + "_0_id"];
							}
							obj.fieldName = key;
							obj.value = input.val();
							if (obj.value) {
								isok = true;
							}
						}
						collection.push(obj);
					}
				}
				collection.push({ // listno
					fieldName: 'listno',
					value: i
				});
				collection.push({ // topId
					fieldName: 'topId',
					value: rules.topId
				});
				collection.push({ // topId
					fieldName: 'index_id',
					value: index_id
				});
				if (isok)
					tables.push(table);
			}
		}
	}
};

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
					if (initData) {
						initData.sort(function(a, b) {
							return (a.listno - b.listno);
						});
					}
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
		// tables = new Array();
		for (var i = 0; i < rules.length; i++) {
			var rule = rules[i],
				cId = rule.cId;
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
									$(this)
										.find(
											"[columnName]")
										.each(
											function() {
												var cn = $(this),
													fieldName = cn
													.attr('columnName');
												if (fieldName) {
													// fieldName
													// =
													// fieldName.replace(rule.tableName
													// +
													// '_0_','');
													collection
														.push({
															fieldName: fieldName,
															// value:
															// cn
															// .val()
															value: cn
																.mtkendo("getValue")
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
			}).text("增加").on('click', function(e) {
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.add(null, o.target);
				}
			}).appendTo($tools);
			$("<button>", {
				'class': 'k-button k-batch-tools-remove'
			}).text("删除").on('click', function(e) {
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.remove(o.target);
				}
			}).appendTo($tools);
		}

		return $tools;
	}
};

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
					dom.mtkendo("setValue", data[key]);
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
							datas, tmpValue = $current ? $current
							.find(
								"[columnname='" + columnname + "']").mtkendo(
								"getValue") : null;

						$(this).show().attr({
							tempId: this.id,
							id: this.id + (tmpId++)
						});

						if (!!($tmp.attr("data-role") == "imageupload")) {
							// $(this).attr("randomparent", new
							// UUID().createUUID());
							// initImageUpload(this, this.id,
							// $(this).attr("randomparent"));

							initImageUpload(this, this.id, $(this)
								.attr("randomparent"));
						}

						if ($tmp.attr("tmpval") == "true") {
							data[columnname] = tmpValue || ($tmp).mtkendo("getValue");
						}
						if (!opts) {
							if (datas = $tmp.data()) {
								for (var k in datas) {
									if (k.toLowerCase() == ("kendo" + $(
											this).attr('data-role'))) {
										opts = {
											key: k,
											options: $.extend(true, {},
												datas[k].options)
										};
										$tmp.data(optionsKey, opts);
										break;
									}
								}
							}
						}
						if (opts) {
							$(this)[opts.key](opts.options);
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
		$this.on(
			'mouseover',
			function(e) {
				var p = lastTd.offset();
				$tools.css({
					left: p.left + lastTd.css('width').replace(/px/gi,
						'') * 1 - 50,
					top: p.top
				}).show();
			}).on('mouseout', function(e) {
			if (e.target == lastTd[0]) {
				$tools.data("o", {
					fn: that,
					target: $this
				});
			} else {
				$tools.hide();
			}
		});
		return that;
	}
};


function openDialog(e) {
	e.preventDefault();
	var $target = $(e.target).closest("a");
	var dialogHeight = $target.attr("dialogHeight");
	var dialogWidth = $target.attr("dialogWidth");
	var isModel = $target.attr("isModel");
	var url = $target.attr("ahref");
	var title = $target.attr("title") || $target.text();
	var iTop = (window.screen.availHeight - 30 - dialogHeight)
		// 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - dialogWidth)
		// 获得窗口的水平位置;
		// var iTop = e.screenY; // 获得窗口的垂直位置;
		// var iLeft = e.screenX; // 获得窗口的水平位置;
	if (isModel == "true") {
		if (url.startsWith(contextPath)) {
			url = url.replace(contextPath, "");
		}
		var link = {
			url: url,
			name: title,
			id: 'xx'
		};
		var rules = {
			link: JSON.stringify(link),
			model: true,
			title: title,
			jumpType: 'childPage',
			width: dialogWidth,
			height: dialogHeight
		};
		pageFunc.newWindow("", "", [rules]);
	} else {
		var option = "height=" + dialogHeight + ",width=" + dialogWidth + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
		window.open(url, title, option);
	}
}

function layerOpen($target) {
	closeLayer();
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: false,
		title: title,
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['', ''],
		fadeIn: 100,
		area: [dialogWidth, dialogHeight],
		iframe: {
			src: url
		}
	});
}

function closeLayer() {
	try {
		layer.close(layer.getFrameIndex());
	} catch (e) {

	}
}

function onResize(o, persent) {

	persent = persent || 1;

	o.height = document.body.scrollHeight * persent;

}

/**
 * 文件上传控件初始化前查询文件列表
 * 
 * @param elementObj
 * @param elementId
 * @param randomUUID
 */
function initKendoUpload(elementObj, elementId, randomUUID) {
	var id = elementObj.id;
	if (!randomUUID) { //
		randomUUID = new UUID().createUUID();
		$(elementObj).attr("randomparent", randomUUID);
		loadKendoUpload(elementObj.id, randomUUID);
	} else {
		if (elementObj) {
			$(elementObj).attr("randomparent", randomUUID);
			var files = [];
			$.post(contextPath + '/mx/form/attachment?method=getFilesByRandomParent', {
				randomParent: randomUUID
			}, function(data) {
				for (var i = 0; i < data.length; i++) {
					var obj = {};
					var fileName = data[i].fileName;
					obj.name = fileName;
					obj.size = data[i].fileSize;
					obj.id = data[i].id;
					obj.extension = fileName.substring(fileName
						.lastIndexOf("."), fileName.length);
					files.push(obj);
				}
				loadKendoUpload(elementObj.id, randomUUID, files);
			}, 'json');
		} else {
			id = elementId
			document.getElementById(elementId).setAttribute("randomparent",
				randomUUID);
			loadKendoUpload(elementId, randomUUID);
		}
	}

	$("body").on(
		"dblclick",
		".k-upload .k-dropzone",
		function() {
			$(this).parents("div .k-upload").find('ul.k-upload-files')
				.toggle('slow');
		});
}

/**
 * 加载文件上传控件
 * 
 * @param id
 * @param randomparent
 * @param files
 */
function loadKendoUpload(id, randomparent, files) {
	if (!files) {
		files = [];
	}

	var options = $("#" + id).data("options");
	var localization = $("#" + id).data("localization");

	var saveUrl = options.saveUrl + "&randomParent=" + randomparent + "&rid=" + options.rid,
		removeUrl = options.removeUrl + "&randomParent=" + randomparent,
		downloadUrl = options.downloadUrl,
		outputNames = options.outputName,
		outputIds = options.outputId;
	$("#" + id)
		.kendoUpload({
			multiple: options.multiple,
			showFileList: options.showFileList,
			files: files,
			async: {
				autoUpload: options.autoUpload,
				saveUrl: saveUrl,
				removeUrl: removeUrl,
				batch: options.batch
			},
			localization: localization,
			template: function(e) {
				var files = e.files;
				var html = "<span class='k-progress' style='width: 100%;'></span>";
				$.each(files, function(i, v) {
					html += "<span class='k-filename' title='" + this.name + "'>" + "<a href='" + downloadUrl + "&id=" + this.id + "' auid='" + this.uid + i + "'>" + this.name + "</a>" + "</span>";
				});
				html += "<strong class='k-upload-status'>" + "<button type='button' class='k-button k-button-bare k-upload-action'>" + "<span class='k-icon k-i-close k-delete' title='移除'></span>" + "</button>" + "</strong>";
				return html;
			},
			upload: function(e) {
				var files = e.files;
				if (options.fileextensionvalue) {
					var stuffixs = options.fileextensionvalue
						.trim().split(';');

					var flag = false;
					$
						.each(
							files,
							function() {
								var fileextension = this.extension
									.toLowerCase();
								$
									.each(
										stuffixs,
										function() {
											if (this
												.toLowerCase() == '' || this
												.toLowerCase() == fileextension) {
												flag = true;
												return;
											}
										});

								if (!flag) {
									alert("请上传后缀名为（" + options.fileextensionvalue + "）的文件");
									e.preventDefault();
									return;
								}

								if (this.size > options.maxfilesize) {
									alert("上传的文件不能超过" + (options.maxfilesize / (1024 * 1024)) + "M");
									e.preventDefault();
									return;
								}
							});
				}
			},
			remove: function(e) {
				var files = e.files;
				var filename = [];
				var idparam = "";
				$.each(files, function() {
					filename.push(this.name);
					idparam = idparam + "&id=" + this.id;
				});
				e.sender.options.async.removeUrl = removeUrl + idparam;

				if (!confirm("确定要删除文件[" + filename + "]吗？")) {
					e.preventDefault();
				}
			},
			success: function(e) {
				var files = e.files,
					names = "",
					ids = "",
					datas = e.response;
				if (e.operation == "upload") {
					for (var i = 0; i < files.length; i++) {
						names += files[i].name + ",";
						ids += datas[i].id + ",";
						files[i].id = datas[i].id;
						$("a[auid=" + files[i].uid + i + "]").attr(
							'href',
							downloadUrl + '&id=' + files[i].id);
					}
					if (outputNames && names) {
						var opns = outputNames.split(",");
						for (var k = 0; k < opns.length; k++) {
							outputName = opns[k];
							var $op = $("#" + outputName);
							var opval = $op.val();
							$op.val((opval ? (opval + ",") : "") + names.substr(0,
								names.length - 1));
						}
					}
					if (outputIds && ids) {
						var opids = outputIds.split(",");
						for (var k = 0; k < opids.length; k++) {
							var outputId = opids[k],
								$op = $("#" + outputId),
								opval = $op.val();
							$op
								.val((opval ? (opval + ",") : "") + ids.substr(0,
									ids.length - 1));
						}
					}
				} else if (e.operation == "remove") {
					for (var i = 0; i < files.length; i++) {
						names += files[i].name + ",";
						ids += files[i].id + ",";
					}
					if (outputNames && names) {
						var opns = outputNames.split(",");
						for (var k = 0; k < opns.length; k++) {
							outputName = opns[k];
							var $op = $("#" + outputName);
							var str = ($op.val() + ",").replace(
								names, "");
							$op.val(str.substr(0, str.length - 1));
						}
					}
					if (outputIds && ids) {
						var opns = outputIds.split(",");
						for (var k = 0; k < opns.length; k++) {
							var outputId = opns[k],
								$op = $("#" + outputId),
								str = ($op.val() + ",")
								.replace(ids, "");
							$op.val(str.substr(0, str.length - 1));
						}
					}
				}
			}
		});
}

function odblClick_(o) {
	var surl, url = contextPath + (surl = ($(o).attr("surl") || "/mx/form/defined/jsgis")) + ((surl.indexOf("?") > -1) ? "&" : "?") + $.param({
		targetId: o.id,
		fn: "odblClickCallFunc_"
	});

	window.open(url);
}

function odblClickCallFunc_(datas) {
	var $this = $(this);
	if (datas) {
		var data = datas.data;
		var rules = datas.rule || $this.attr("dbrule");
		var rs = eval(rules);
		for (var i = 0; i < rs.length; i++) {
			var r = rs[i];
			if (r.name == "FID") {
				if ($.isArray(data)) {
					var outAry = [],
						da;
					for (var j = 0; j < data.length; j++) {
						da = data[j];
						outAry.push(da.layerId + "-" + da.FID);
					}
					$('#' + r.output).val(outAry.join(","));
				} else {
					$('#' + r.output).val(data.layerId + "-" + data.FID);
				}
			} else {
				if ($.isArray(data)) {
					var outAry = [],
						da;
					for (var j = 0; j < data.length; j++) {
						da = data[j];
						outAry.push(da[r.name]);
					}
					$('#' + r.output).val(outAry.join(","));
				} else {
					$('#' + r.output).val(data[r.name]);
				}
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
					data[r.name] = $("#" + r.output).mtkendo("getValue");
				}
				return data;
			}
		}
	}
}

// --end--