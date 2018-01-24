/**
 * type[{ value : 文本框值 text : 单元格文本 row : 一行 }] 输入输出关系配置 start (以下代码，有待完善...)
 */

window.fzmt = {
	getControlParams: function(type, id, pageId) {
		if (!(type in mt.control))
			type = 'default';
		var f = mt.control[type];
		if ($.isFunction(f)) {
			return f(id, pageId);
		} else {
			return f;
		}
	}
};
/**
 * 获取数据集后台参数
 * 
 * @param datasetIds
 * @param fn
 */
function getDataSetParams(datasetIds, fn) {
	$.ajax({
		url: contextPath + "/rs/dataset/getDataSetParams",
		type: 'POST',
		dataType: 'JSON',
		async: false,
		data: {
			datasetIds: datasetIds
		},
		success: function(ret) {
			if (fn) {
				fn(ret);
			}
		}
	});
}

function getDataSourceSet(id, pageId) {
	var datas = null;
	$.ajax({
		url: contextPath + '/mx/form/defined/getDataSourceSet',
		data: {
			pageId: pageId || mtxx.saveData.pageId,
			name: id
		},
		dataType: 'JSON',
		type: 'post',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}

function getCellParams(item) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/getCellParams',
		data: {
			pageId: JSON.parse(item.pObj).pageId,
			cellId: item.cellId,
			eleId: JSON.parse(item.pObj).eleId || item.eleId
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}
/**
 * 获取GRID内联动功能
 * 
 * @param {[type]}
 *            item [description]
 * @return {[type]} [description]
 */
function getOtypeParams(item) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/getOtypeParams',
		data: {
			pageId: JSON.parse(item.pObj).pageId,
			role: item.otype,
			eleId: item.oEleId
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}

function getParametersByPageId(pageId) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/getParametersByPageId',
		data: {
			pageId: pageId
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}

function getStuffByPageId(pageId) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/getStuffByPageId',
		data: {
			pageId: pageId
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}

// 输入输出参数
function initInOutParameter(data) {
	if (data) {
		$(this).val(JSON.stringify(data));
		$('#id_' + $(this).attr('idfield')).val(data[0].name);
		closeLayer();
	} else {
		var saveData = mtxx.saveData,
			tfMsg = mtxx.tfMsg;
		var params = getParametersByPageId(saveData.pageId),
			inParams = {}; // 输出控件的输入参数
		if (params) {
			if (saveData.pageId == saveData.name) {
				$.each(params, function(i, v) {
					if (v.PARAMTYPE_ == 'inParamDefined') {
						if (v.NAME_ != v.PAGEID_) {
							v.text = v.TITLE_ || v.NAME_;
							v.items = [];
							if (v.VALUE_) {
								/*
								 * $.each(JSON.parse(v.VALUE_), function(i, p) {
								 * p.text = p.name; p.id = v.NAME_; p.title =
								 * v.NAME_ + "-选中行-" + p.name; v.items.push(p);
								 * });
								 */
								initObjParams(v);
							}
							inParams[v.NAME_] = v;
						}
					}
				});
			} else {
				$.each(params, function(i, v) {
					if (v.PARAMTYPE_ == 'inParamDefined') {
						v.text = v.TITLE_ || v.NAME_;
						v.items = [];
						if (v.VALUE_) {
							/*
							 * $.each(JSON.parse(v.VALUE_), function(i, p) {
							 * p.text = p.name; p.id = v.NAME_; p.title =
							 * v.NAME_ + "-选中行-" + p.name; v.items.push(p); });
							 */
							initObjParams(v);
						}
						inParams[v.NAME_] = v;
					}
				});
			}
		}
		var $in = $("#" + tfMsg.linkageControlInId),
			$out = $("#" + tfMsg.linkageControlId),
			get = function(drop, isIn) {
				var jq = $("#id_" + drop.attr('itemid') + "_hidden");
				var str = jq.val();
				if (str) {
					try {
						var arr = JSON.parse(str);
						if (arr) {
							var retArr = new Array();
							var obj0 = {
									text: '输入参数',
									items: [],
									level: 0
								},
								obj;
							$.each(arr, function(i, item) {
								var role = item.id.replace(/[^a-zA-Z]*/g, ''); // 只保留字母
								var items = [];
								if (isIn) {
									items = fzmt.getControlParams(role, item.id, item.pageId);
									obj = {
										text: item.text,
										id: item.id,
										items: items
									};
									retArr.push(obj);
								} else {
									var p = inParams[item.id];
									if (p)
										obj0.items.push(p);
								}
							});

							if (!isIn) {
								retArr.push(obj0);
							}
							return retArr;
						}
					} catch (e) {
						$.log(e);
					}
				}
			};
		var menus;
		var linkPageObj = {
			text: '联动页面',
			items: []
		};
		if (saveData.pageId == saveData.name) { // 本页面页面参数
			var obj = {
				text: '输入参数',
				items: [],
				level: 0
			};
			$.each(inParams, function(i, item) {
				var items = [];
				var p = inParams[i];
				if (p)
					items.push(p);

				obj.items.push(p);
			});
			var datas = [{
				text: '数据源',
				items: []
			}, {
				text: '页面参数',
				items: []
			}];

			// 获取数据源列
			var str = $('#' + tfMsg.dataSourceSetId).val();
			if (str) {
				str = JSON.parse(str);
			}
			debugger;
			var ds = str;
			if (ds && ds[0]) {
				var dataSource = ds[0];
				var columns = dataSource.columns;
				if (columns) {
					$.each(columns, function(i, c) {
						c.text = c.title;
						c.type = 'getRow';
						c.id = saveData.pageId;
						datas[0].items.push(c);
					});
				}
				/*
				 * datas.push({ text : '更新集', items : [ { text :
				 * dataSource.title, type : 'updateSource', id :
				 * dataSource.datasetId, dataSetId : dataSource.datasetId } ]
				 * });
				 */
			}
			// 获取页面参数
			$.each(params, function(i, v) {
				if (v.PARAMTYPE_ == 'inParamDefined' && v.NAME_ == saveData.pageId) {
					v.text = '页面参数';
					v.items = [];
					if (v.VALUE_) {
						/*
						 * $.each(JSON.parse(v.VALUE_), function(i, p) { p.text =
						 * p.name; p.id = v.PAGEID_; p.inparam = p.param; p.type =
						 * 'getValue'; v.items.push(p); });
						 */
						// initObjParams(v);
						var value_ = JSON.parse(v.VALUE_);
						if (value_ && value_[0] && value_[0]["type"]) {
							$.each(value_[0]["source"], function(i, p) {
								p.text = p.name;
								p.id = v.PAGEID_;
								p.inparam = p.param;
								p.type = 'getValue_' + p.param;
								v.items.push(p);
							});

							/*
							 * if (value_[0]["objSource"]) {
							 * $.each(value_[0]["objSource"], function(i, p) {
							 * var objv = { text: p.name, items: [] };
							 * $.each(JSON.parse(p.child), function(ii, pp) {
							 * pp.text = pp.name; pp.id = v.PAGEID_; pp.dataType =
							 * "obj"; pp.dataId = p.param; pp.inparam =
							 * pp.param; pp.type = 'getValue';
							 * objv.items.push(pp); }); v.items.push(objv); }); }
							 * 
							 * if (value_[0]["arySource"]) {
							 * $.each(value_[0]["arySource"], function(i, p) {
							 * var objv = { text: p.name, items: [] };
							 * $.each(JSON.parse(JSON.parse(p.child)[0]["child"]),
							 * function(ii, pp) { pp.text = pp.name; pp.id =
							 * v.PAGEID_; pp.dataType = "ary"; pp.dataId =
							 * p.param; pp.inparam = pp.param; pp.type =
							 * 'getValue'; objv.items.push(pp); });
							 * v.items.push(objv); }); }
							 */
						} else {
							$.each(value_, function(i, p) {
								p.text = p.name;
								p.id = v.PAGEID_;
								p.inparam = p.param;
								p.type = 'getValue' + p.param;
								v.items.push(p);
							});
						}
					}
					/*
					 * v.items.push({ columnName : true , text : "默认值true", id :
					 * v.PAGEID_, param : "isTrue" , value : "true" , type :
					 * '__defaultValue__' });
					 */
					datas[1] = v;
				}
			});

			menus = {
				'in': datas,
				out: [obj]
			};

		} else {
			menus = {
				'in': get($in, true),
				out: get($out, false) || [{
					text: '输入参数',
					items: [],
					level: 0
				}]
			};
			var isSavePageLink = false;
			var id = '';
			if (tfMsg.pageElementId) {
				if ($("#" + tfMsg.pageElementId).val()) {
					id = JSON.parse($("#" + tfMsg.pageElementId).val())[0].id;
				}
			}
			// getLinkPageParams(saveData.pageId);//

			if (tfMsg.pageElementId && !isSavePageLink) { // 获取关联页面的接收参数
				var p = $("#" + tfMsg.pageElementId);
				getInParamDefined(p.val());
			}
		}

		menus['out'][0].items.push(linkPageObj);

		(function(collection) { // 获取报表参数
			if (mtxx.tfMsg.selectReportTemplateId) {
				var $selectReportTemplate = $("#" + mtxx.tfMsg.selectReportTemplateId);
				if ($selectReportTemplate[0]) {
					var $selectReportTemplateParams = JSON.parse($selectReportTemplate.val() || "[]");
					if ($selectReportTemplateParams[0] && ($selectReportTemplateParams = $selectReportTemplateParams[0].params)) {
						var linkReportObj = {
							text: '报表接收参数',
							items: []
						};
						collection.push(linkReportObj);
						$.each($selectReportTemplateParams, function(i, v) {
							linkReportObj.items.push({
								id: saveData.name,
								name: v.ParameterRemark,
								param: v.ParameterName,
								text: v.ParameterRemark
							});
						});
					}
				}
			}
		})(menus['out'][0].items);

		(function(collection) { // 报表返回参数
			// templateId、reportId、pageCount
			var obj = {
				text: '报表输出参数',
				items: [{
					'text': '模版id',
					id: 'templateId',
					name: '模版id',
					param: 'templateId',
					type: 'setValue'
				}, {
					'text': '报表id',
					id: 'reportId',
					name: '报表id',
					param: 'reportId',
					type: 'setValue'
				}, {
					'text': '页码',
					id: 'pageCount',
					name: '页码',
					param: 'pageCount',
					type: 'setValue'
				}, {
					'text': '数据库',
					id: 'content',
					name: '数据库',
					param: 'content',
					type: 'setValue'
				}, {
					'text': '密钥',
					id: 'key',
					name: '密钥',
					param: 'key',
					type: 'setValue'
				}]
			};
			collection.push(obj);
		})(menus['in'] || []);

		(function(collection) { // 工作流数据集参数
			var $dataSourceSetByFlowId = $("#" + mtxx.tfMsg.dataSourceSetByFlowId);
			if ($dataSourceSetByFlowId[0]) {
				var $dataSourceSetByFlow = JSON.parse($dataSourceSetByFlowId.val() || "[]");
				if ($dataSourceSetByFlow && $dataSourceSetByFlow[0]) {
					$dataSourceSetByFlow = $dataSourceSetByFlow[0].selectDatasource;
					if ($dataSourceSetByFlow && $dataSourceSetByFlow[0]) {
						var ids = [],
							flowParams = {
								text: '工作流参数',
								items: []
							};
						$.each($dataSourceSetByFlow, function(i, v) {
							ids.push(v.datasetId);
						});
						collection.push(flowParams);
						getDataSetParams(ids.join(','), function(ret) {
							if (ret && ret[0]) {
								$.each(ret, function(i, v) {
									$.extend(v, {
										text: v.name,
										id: v.param
									});
									flowParams.items.push(v);
								});
							}
						});
					}
				}
			}
		})(menus['out'][0].items);

		(function(collection) { // 报表数据集参数(新报表)

			/*
			 * var $dataSourceSetByFlowId = $("#" +
			 * mtxx.tfMsg.dataSourceSetByFlowId); if ($dataSourceSetByFlowId[0]) {
			 * var $dataSourceSetByFlow =
			 * JSON.parse($dataSourceSetByFlowId.val() || "[]"); if
			 * ($dataSourceSetByFlow && $dataSourceSetByFlow[0]) {
			 * $dataSourceSetByFlow = $dataSourceSetByFlow[0].selectDatasource;
			 * if ($dataSourceSetByFlow && $dataSourceSetByFlow[0]) { var ids =
			 * [], flowParams = { text: '工作流参数', items: [] };
			 * $.each($dataSourceSetByFlow, function(i, v) {
			 * ids.push(v.datasetId); }); collection.push(flowParams);
			 * getDataSetParams(ids.join(','), function(ret) { if (ret &&
			 * ret[0]) { $.each(ret, function(i, v) { $.extend(v, { text:
			 * v.name, id: v.param }); flowParams.items.push(v); }); } }); } } }
			 */

			var ajaxGet = function(params, fn) {
				$.ajax({
					type: 'post',
					data: params,
					async: false,
					dataType: 'json',
					url: contextPath + "/mx/report/reportTmpMapping/getParamsByMappingId",
					success: function(ret) {
						fn && (fn(ret));
					}
				});
			};

			var $pageElementId = $("#" + mtxx.tfMsg.pageElementId),
				str;
			if ($pageElementId.get(0) && (str = $pageElementId.val())) {
				try {
					var json = JSON.parse(str)[0];
					if (json.pageType && json.pageType == 'report-page') {
						var id = json.id,
							reportParams = {
								text: '报表数据集参数',
								items: []
							};
						ajaxGet({
							mappingId: id
						}, function(ret) {
							if (ret && ret.data && ret.data.length) {
								$.each(ret.data, function(i, dataSet) {
									if (dataSet.items) {
										$.each(dataSet.items, function() {
											this['text'] = this["name"];
											this['id'] = this["param"];
											this["sortType"] = dataSet.id;
										});
									}
									reportParams.items.push(dataSet);
								});
							}
						});
						collection.push(reportParams);
					}
				} catch (e) {
					console.log(e);
				}
			}

		})(menus['out'][0].items);

		getLinkPageParams(saveData.pageId); //

		function getLinkPageParams(pageId) { // 获取当前页面所关联的所有页面参数
			var data = getStuffByPageId(pageId);
			if (data) {
				$.each(data, function(i, v) {
					if (id == v.PAGEID_) {
						isSavePageLink = true;
					}
					if (v.VALUE_) {
						var d = JSON.parse(v.VALUE_);
						if (d && d[0]) {
							for (var x = 0; x < d.length; x++) {
								var r = d[x];
								if (r.node != undefined && r.node != null && r.node.isFix) { // 固定页面接收参数
									params = r.node.params || [];
									var pageUrl = r.node.id,
										pageName = r.node.name,
										param, v = {
											text: pageName,
											items: []
										};
									if (params.length > 0) {
										for (var i = 0; i < params.length; i++) {
											param = params[i];
											v.items.push({
												id: pageUrl,
												name: param.name,
												param: param.code,
												text: param.name
											});
										}
										linkPageObj.items.push(v);
									}
								}
							}
						}
						getInParamDefined(v.VALUE_);
					}
				});
			}
		}

		function getInParamDefined(str) {
			if (str) {
				var d = JSON.parse(str);
				if (d && d[0]) {
					for (var x = 0; x < d.length; x++) {
						var r = d[x];
						if (isSavePageLink)
							continue;
						params = getParametersByPageId(r.id), inParams = {};
						$.each(params, function(i, v) {
							if (v.PARAMTYPE_ == 'inParamDefined' && v.NAME_ == r.id) {
								if (id == v.PAGEID_) {
									isSavePageLink = true;
								}
								v.text = v.TITLE_ || v.NAME_;
								v.items = [];
								if (v.VALUE_) {
									initObjParams(v);
									/*$.each(JSON.parse(v.VALUE_), function(i, p) {
										p.text = p.name;
										p.id = v.NAME_;
										v.items.push(p);
									});*/
								}
								linkPageObj.items.push(v);
							}
						});
					}
				}
			}
		}

		var data = $(this).val();
		if (data) {
			var v = JSON.parse(data)[0].datas;
			data = v;
		}
		if (!menus) {
			alert("参数不够!");
			closeLayer();
			return false;
		}
		return {
			datas: data || {},
			menus: menus
		};
	}
}

/**
 * 输入输出关系配置 end
 */

function getEventsParametersByPageId(pageId, inPages) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/getEventsParametersByPageId',
		data: {
			pageId: pageId,
			inPages: inPages.join(",")
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}
// 根据属性id获取数据
function getParametersByAttrId(pageId) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/getParametersByAttrId',
		data: {
			pageId: pageId
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}
// 获取后端表达式定义的输入输出参数
function getParametersByExprCalc(id, isCallback) {
	return baseGetParameters(id, isCallback, "getParametersByExprCalc");
}
// 获取更新集中定义的输入输出参数
function getParametersByCrud(id, isCallback) {
	return baseGetParameters(id, isCallback, "getParametersByCrud");
}
// 获取sqlite规则中定义的输入输出参数
function getParametersBySqliteRule(id, isCallback) {
	return baseGetParameters(id, isCallback, "getParametersBySqliteRule");
}
// 获取批量任务定义的输入参数
function getParametersByTaskTable(id, isCallback) {
	return baseGetParameters(id, isCallback, "getParametersByTaskTable");
}
// 获取数据列
function getParametersBySearch(id, isCallback) {
	var datas = null;
	// 获取输入参数
	if (isCallback) {
		$.ajax({
			url: contextPath + '/rs/dataset/getSelectJson',
			data: {
				datasetId: id
			},
			type: 'post',
			dataType: 'json',
			async: false,
			success: function(data) {
				datas = data;
			}
		});
	} else {
		getDataSetParams(id, function(ret) {
			datas = ret;
		})
	}
	return datas;
}

function baseGetParameters(id, isCallback, url) {
	var datas = null;
	// 获取输入参数
	$.ajax({
		url: contextPath + '/mx/form/defined/' + url,
		data: {
			id: id,
			isCallback: isCallback
		},
		type: 'post',
		dataType: 'json',
		async: false,
		success: function(data) {
			datas = data;
		}
	});
	return datas;
}

var gobalParamDefined = {
		newWindow: function(item) {
			var pp = [{
				id: item.eleId,
				name: '最大化',
				param: 'isMax',
				text: '最大化'
			}, {
				id: item.eleId,
				name: '宽度',
				param: 'width',
				text: '宽度'
			}, {
				id: item.eleId,
				name: '高度',
				param: 'height',
				text: '高度'
			}, {
				id: item.eleId,
				name: '子窗口',
				param: 'singlePage',
				text: '子窗口'
			}, {
				id: item.eleId,
				name: '模态',
				param: 'model',
				text: '模态'
			}, {
				id: item.eleId,
				name: '跳转',
				param: 'render',
				text: '跳转'
			}, {
				id: item.eleId,
				name: '窗口名称',
				param: 'title',
				text: '窗口名称'
			}, {
				id: item.eleId,
				name: '拖拽移动(默认true)',
				param: 'draggable',
				text: '拖拽移动(默认true)'
			}, {
				id: item.eleId,
				name: '选项卡打开(主页)',
				param: 'mtOpenTab',
				text: '选项卡打开(主页)'
			}, {
				id: item.eleId,
				name: '选项卡名称',
				param: 'TABNAME',
				text: '选项卡名称'
			}, {
				id: item.eleId,
				name: '标题栏内边距',
				param: 'headerPadding',
				text: '标题栏内边距'
			}, {
				id: item.eleId,
				name: '内容内边距',
				param: 'contentPadding',
				text: '内容内边距'
			}, {
				id: item.eleId,
				name: '背景模糊',
				param: 'backdrop',
				text: '背景模糊'
			}, {
				id: item.eleId,
				name: '顶层弹窗',
				param: 'isParent',
				text: '顶层弹窗'
			}, {
				id: item.eleId,
				name: '点击背景关闭',
				param: 'closeByBackdrop',
				text: '点击背景关闭'
			}, {
				id: item.eleId,
				name: '不加载为下级事件',
				param: 'ignoreWindow',
				text: '不加载为下级事件'
			}, {
				id: item.eleId,
				name: '标题栏背景色',
				param: 'headerBackgroundColor',
				text: '标题栏背景色'
			}, {
				id: item.eleId,
				name: '标题字体大小',
				param: 'headerFontSize',
				text: '标题字体大小'
			}, {
				id: item.eleId,
				name: '标题字体颜色',
				param: 'headerFontColor',
				text: '标题字体颜色'
			},{
				id: item.eleId,
				name: '透明度(0-1)',
				param: 'opacity',
				text: '透明度(0-1)'
			},{
				id: item.eleId,
				name: '动态参数',
				param: 'parameters',
				text: '动态参数'
			},{
				id: item.eleId,
				name: '关闭按钮颜色',
				param: 'closeBtnColor',
				text: '关闭按钮颜色'
			},{
				id: item.eleId,
				name: '关闭按钮大小',
				param: 'closeBtnSize',
				text: '关闭按钮大小'
			},
			{
				id: item.eleId,
				name: '隐藏标题栏',
				param: 'hideHeader',
				text: '隐藏标题栏'
			},
			{
				id: item.eleId,
				name: '圆角率',
				param: 'borderRadius',
				text: '圆角率'
			},];
			if (item.params && item.params.length) {
				$.each(item.params, function(i, v) {
					pp.push({
						id: item.eleId,
						name: v.name,
						param: v.code,
						text: v.name
					});
				})
			}
			return pp;
		},
		/**
		 * 下发事件
		 */
		sendDown: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
					id: item.eleId,
					name: '源标段',
					param: 'sourceTenders',
					text: '源标段'
				}, {
					id: item.eleId,
					name: '目标标段',
					param: 'targetTenders',
					text: '目标标段'
				}
				/*
				 * , { id: item.eleId, name: '下发数据', param: 'sendData', text: '下发数据' }
				 */
			];
			var datasAry = getParametersByAttrId(item.pageId),
				newDatas = [];
			$.each(datasAry, function(index, el) {
				newDatas.push({
					id: item.eleId,
					name: el.name,
					param: el.param,
					text: el.name
				});
			});
			$.merge(reDatas, newDatas);
			return reDatas;
		},
		/**
		 * 整表下发
		 */
		sendAllDown: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				id: item.eleId,
				name: '源标段',
				param: 'sourceTenders',
				text: '源标段'
			}, {
				id: item.eleId,
				name: '目标标段',
				param: 'targetTenders',
				text: '目标标段'
			}, {
				id: item.eleId,
				name: '下发表名',
				param: 'sendData',
				text: '下发表名'
			}];
			return reDatas;
		},
		/**
		 * 警示操作
		 */
		mtAlert: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			return [{
				id: item.eleId,
				name: '输入参数',
				param: 'params',
				text: '输入参数'
			}];
		},
		/**
		 * 逐级汇总
		 */
		collect: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var datasAry = baseGetParameters(_item.outExt.value, isCallback, "getParametersByTreeAggregate"),
				reDatas = [];
			$.each(datasAry, function(index, el) {
				reDatas.push({
					id: item.eleId,
					name: el.columnName,
					param: el.columnLabel,
					text: el.columnName
				});
			});
			reDatas.push({
				id: item.eleId,
				name: '标段',
				param: 'databaseId',
				text: '标段'
			});
			reDatas.push({
				id: item.eleId,
				name: "禁用提示",
				param: "isAlt",
				text: "禁用提示"
			});
			return reDatas;
		},
		/**
		 * 计算表达式
		 */
		exprCalc: function(item, _item, isCallback) {
			var datasAry = getParametersByExprCalc(_item.outExt.value, isCallback),
				reDatas = [];
			$.each(datasAry, function(index, el) {
				if (isCallback) {
					reDatas.push({
						id: item.eleId,
						name: el.name,
						columnName: el.param,
						text: el.name
					});
				} else {
					reDatas.push({
						id: item.eleId,
						name: el.name,
						param: el.param,
						text: el.name
					});
				}
			});
			return reDatas;
		},

		/**
		 * 微服务
		 */
		mt_service: function(item, _item, isCallback) {
			return MtService(item, _item, isCallback);
		},

		/*
		 * CRUD事件
		 */
		mtcrud: function(item, _item, isCallback) {
			var datasAry = getParametersByCrud(_item.outExt.value, isCallback),
				reDatas = [],
				delDatas = [{
					id: item.eleId,
					name: "id",
					param: "id",
					text: "id",
					otype: "del"
				}];
			if (isCallback) {
				$.each(datasAry, function(index, el) {
					reDatas.push({
						id: item.eleId,
						name: el.tableNameCN + "-->" + el.columnName,
						columnName: el.dname,
						text: el.tableNameCN + "-->" + el.columnName
					});
				});
			} else {
				// 已选择列数据
				$.each(datasAry["selectedColumns"], function(index, el) {
					reDatas.push({
						id: item.eleId,
						name: el.tableNameCN + "-->" + el.columnName,
						param: el.dname,
						text: el.tableNameCN + "-->" + el.columnName,
						otype: "cu"
					});
				});
				// where 条件参数
				$.each(datasAry["whereParams"], function(index, el) {
					reDatas.push({
						id: item.eleId,
						name: el.paramName,
						param: el.param,
						text: el.paramName,
						otype: "cu"
					});
					delDatas.push({
						id: item.eleId,
						name: el.paramName,
						param: el.param,
						text: el.paramName,
						otype: "del"
					});
				});
				reDatas = [{
					text: "新增更新",
					items: reDatas
				}, {
					text: "删除操作",
					items: delDatas
				}, {
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "批量操作",
						param: "isBatch",
						text: "批量操作"
					}, {
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}, {
						id: item.eleId,
						name: "禁用删除提示",
						param: "isDelAlt",
						text: "禁用删除提示"
					}, {
						id: item.eleId,
						name: "标段",
						param: "databaseId",
						text: "标段"
					}]
				}];
			}
			return reDatas;
		},
		/*
		 * 转换服务
		 */
		convertService: function(item, _item, isCallback) {
			var reDatas = [];
			if (isCallback) {
				reDatas.push({
					id: item.eleId,
					name: "主键",
					columnName: "id",
					text: "主键"
				}, {
					id: item.eleId,
					name: "表单id",
					columnName: "parent",
					text: "表单id"
				}, {
					id: item.eleId,
					name: "文件名称",
					columnName: "fileName",
					text: "文件名称"
				}, {
					id: item.eleId,
					name: "文件大小",
					columnName: "fileSize",
					text: "文件大小"
				});
			} else {
				reDatas = [{
					id: item.eleId,
					name: "传入值",
					param: "cellValue",
					text: "传入值"
				}, {
					id: item.eleId,
					name: "文件名",
					param: "fileName",
					text: "文件名"
				}, {
					id: item.eleId,
					name: "清晰度DPI",
					param: "dpi",
					text: "清晰度DPI(默认160)"
				}, {
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}]
				}];
			}
			return reDatas;
		},
		cell2pdf: function(item, _item, isCallback) {
			var reDatas = [];
			if (isCallback) {
				reDatas.push({
					id: item.eleId,
					name: "主键",
					columnName: "id",
					text: "主键"
				}, {
					id: item.eleId,
					name: "表单id",
					columnName: "parent",
					text: "表单id"
				}, {
					id: item.eleId,
					name: "文件名称",
					columnName: "fileName",
					text: "文件名称"
				}, {
					id: item.eleId,
					name: "文件大小",
					columnName: "fileSize",
					text: "文件大小"
				});
			} else {
				reDatas = [{
					id: item.eleId,
					name: "传入值",
					param: "cellValue",
					text: "传入值"
				}, {
					id: item.eleId,
					name: "文件名",
					param: "fileName",
					text: "文件名"
				}, {
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}]
				}];
			}
			return reDatas;
		},
		pdfmerge: function(item, _item, isCallback) {
			var reDatas = [];
			if (isCallback) {
				reDatas.push({
					id: item.eleId,
					name: "主键",
					columnName: "id",
					text: "主键"
				}, {
					id: item.eleId,
					name: "表单id",
					columnName: "parent",
					text: "表单id"
				}, {
					id: item.eleId,
					name: "文件名称",
					columnName: "fileName",
					text: "文件名称"
				}, {
					id: item.eleId,
					name: "文件大小",
					columnName: "fileSize",
					text: "文件大小"
				});
			} else {
				reDatas = [{
					id: item.eleId,
					name: "传入值",
					param: "ids",
					text: "传入值"
				}, {
					id: item.eleId,
					name: "文件名",
					param: "fileName",
					text: "文件名"
				}, {
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}]
				}];
			}
			return reDatas;
		},
		/**
		 * cell 打印服务
		 */
		mtCellPrint: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				id: item.eleId,
				name: '打印',
				param: 'printValue',
				text: '打印'
			}];
			return reDatas;
		},
		/**
		 * cell 导出excel
		 */
		mtCellExport: function(item, _item, isCallback) {
			var datasAry = getParametersByExprCalc(_item.outExt.value, isCallback),
				reDatas = [];
			$.each(datasAry, function(index, el) {
				if (isCallback) {
					reDatas.push({
						id: item.eleId,
						name: el.name,
						columnName: el.param,
						text: el.name
					});
				} else {
					reDatas.push({
						id: item.eleId,
						name: el.name,
						param: el.param,
						text: el.name
					});
				}
			});
			if (!_item.outExt.value) {
				reDatas.push({
					id: item.eleId,
					name: '导出xls',
					param: 'exprotValue',
					text: '导出xls'
				});
			}
			reDatas.push({
				id: item.eleId,
				name: '导出名称',
				param: 'exprotName',
				text: '导出名称'
			});
			return reDatas;

			/*
			 * if(isCallback){ return [] ; } var reDatas = [{ id: item.eleId, name:
			 * '导出xls', param: 'exprotValue', text: '导出xls' }]; return reDatas;
			 */
		},
		convertItem: function(item, _item) {
			var p = {};
			p.text = p.TITLE_ = _item.name;
			p.NAME_ = item.eleId;
			p.PAGEID_ = item.pageId;
			p.items = [];
			return p;
		},

		/**
		 * 导出sqllive文件
		 * 
		 * @param {[type]}
		 *            item [description]
		 * @param {[type]}
		 *            _item [description]
		 * @return {[type]} [description]
		 */
		downSqlLite: function(item, _item, isCallback) {
			var reDatas = [];

			var datasAry = getParametersBySqliteRule(_item.outExt.value, isCallback),
				reDatas = [];
			if (isCallback) {} else {
				$.each(datasAry, function(index, el) {
					reDatas.push({
						id: item.eleId,
						name: el.name,
						param: el.param,
						text: el.name,
						otype: "cu"
					});
				})
				reDatas = [{
					text: "输入形参",
					items: reDatas
				}, {
					text: "系统属性",
					items: []
				}];
			}
			return reDatas;
		},
		/*
		 * 查询服务
		 */
		mtsearch: function(item, _item, isCallback) {
			var datasAry = getParametersBySearch(_item.outExt.value, isCallback),
				reDatas = [];
			if (isCallback) {
				$.each(datasAry, function(index, el) {
					reDatas.push({
						id: item.eleId,
						name: el.title,
						columnName: el.columnLabel,
						text: el.title
					});
				});
			} else {
				$.each(datasAry, function(index, el) {
					reDatas.push({
						id: item.eleId,
						name: el.name,
						param: el.param,
						text: el.name
					});
				});
				reDatas = [{
					text: "查询参数",
					items: reDatas
				}, {
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}, {
						id: item.eleId,
						name: "标段",
						param: "databaseId",
						text: "标段"
					}]
				}];
			}
			return reDatas;
		},
		/*
		 * 短信服务
		 */
		nodeMessage: function(item, _item, isCallback) {
			var reDatas = [];
			reDatas = [{
				text: "发送服务",
				items: [{
					id: item.eleId,
					name: "手机号码",
					param: "x",
					text: "手机号码"
				}, {
					id: item.eleId,
					name: "短信内容",
					param: "y",
					text: "短信内容"
				}, {
					id: item.eleId,
					name: "标题",
					param: "subject",
					text: "标题"
				}, {
					id: item.eleId,
					name: "定时发送(时间数据)",
					param: "sendLaterTime",
					text: "定时发送(时间数据)"
				}]
			}]
			return reDatas;
		},
		/**
		 * bim
		 */
		bim: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			return [{
				text: "BIM节点",
				items: [{
					id: item.eleId,
					name: "节点名称",
					param: "name",
					text: "节点名称"
				}, {
					id: item.eleId,
					name: "节点ID",
					param: "id",
					text: "节点ID"
				}]
			}];
		},
		/**
		 * 手机查看WPFSOATIF
		 */
		callWpfSoaTif: function(item, _item, isCallback) {
			if (isCallback) {
				return [{
					id: item.eleId,
					name: 'tif图片路径',
					columnName: 'tifurl',
					text: 'tif图片路径'
				}];
			}
			var reDatas = [{
				id: item.eleId,
				name: '服务地址*',
				param: 'uri',
				text: '服务地址*'
			},{
				id: item.eleId,
				name: '外网地址*',
				param: 'wuri',
				text: '外网地址*'
			}, {
				id: item.eleId,
				name: 'did*',
				param: 'did',
				text: 'did*'
			}, {
				id: item.eleId,
				name: '标段ID*',
				param: 'databaseId',
				text: '标段ID*'
			}, {
				id: item.eleId,
				name: 'sysId*',
				param: 'sysId',
				text: 'sysId*'
			}, {
				id: item.eleId,
				name: '文件ID*',
				param: 'fileId',
				text: '文件ID*'
			}, {
				id: item.eleId,
				name: '文件类型',
				param: 'fileType',
				text: '文件类型'
			}, {
				id: item.eleId,
				name: '查询表名',
				param: 'queryTable',
				text: '查询表名'
			}, {
				id: item.eleId,
				name: '查询字段',
				param: 'queryField',
				text: '查询字段'
			}, {
				id: item.eleId,
				name: '文件ID字段',
				param: 'IdField',
				text: '文件ID字段'
			}, {
				id: item.eleId,
				name: '文件内容字段',
				param: 'FileField',
				text: '文件内容字段'
			}];
			return reDatas;
		},
		/**
		 * 手机查看TIF
		 */
		callTif: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				id: item.eleId,
				name: 'TIF文件ID',
				param: 'tifId',
				text: 'TIF文件ID'
			}, {
				id: item.eleId,
				name: '表名字段',
				param: 'tnfield',
				text: '表名字段'
			}, {
				id: item.eleId,
				name: '内容字段',
				param: 'ctfield',
				text: '内容字段'
			}, {
				id: item.eleId,
				name: '主键字段(默认id)',
				param: 'idfield',
				text: '主键字段(默认id)'
			}, {
				id: item.eleId,
				name: '标段',
				param: 'databaseId',
				text: '标段'
			}];
			return reDatas;
		},
		/**
		 * 手机登录
		 */
		callLogin: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				id: item.eleId,
				name: '登录帐号',
				param: 'x',
				text: '登录帐号'
			}, {
				id: item.eleId,
				name: '登录密码',
				param: 'y',
				text: '登录密码'
			}, {
				id: item.eleId,
				name: '域名',
				param: 'z',
				text: '域名'
			}, {
				id: item.eleId,
				name: '记住帐号',
				param: 'r',
				text: '记住帐号'
			}, {
				id: item.eleId,
				name: '自动登录',
				param: 'a',
				text: '自动登录'
			}, {
				id: item.eleId,
				name: '应用名(默认/glaf)',
				param: 'k',
				text: '应用名(默认/glaf)'
			},{
				id: item.eleId,
				name: '记住账号',
				param: 'rember',
				text: '记住账号'
			}];
			return reDatas;
		},
		/**
		 * 拨打电话
		 * @param  {[type]}  item       [description]
		 * @param  {[type]}  _item      [description]
		 * @param  {Boolean} isCallback [description]
		 * @return {[type]}             [description]
		 */
		callTelPhone: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				id: item.eleId,
				name: '号码',
				param: 'telPhone',
				text: '号码'
			}];
			return reDatas;
		},
		/**
		 * 扫描二维码
		 * @param  {[type]}  item       [description]
		 * @param  {[type]}  _item      [description]
		 * @param  {Boolean} isCallback [description]
		 * @return {[type]}             [description]
		 */
		callScan: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				id: item.eleId,
				name: '是否回调',
				param: 'hasCallback',
				text: '是否回调'
			}];
			return reDatas;
		},
		/**
		 * 手机号码
		 * @param  {[type]}  item       [description]
		 * @param  {[type]}  _item      [description]
		 * @param  {Boolean} isCallback [description]
		 * @return {[type]}             [description]
		 */
		callTelNo: function(item, _item, isCallback) {
			if (isCallback) {
				return [{
					text: "回调参数",
					items: [{
						id: item.eleId,
						name: "手机号码",
						param: "telNo",
						text: "手机号码"
					}]
				}]
			}
			var reDatas = [/*{
				id: item.eleId,
				name: '是否回调',
				param: 'hasCallback',
				text: '是否回调'
			}*/];
			return reDatas;
		},
		/**
		 * 经纬度
		 * @param  {[type]}  item       [description]
		 * @param  {[type]}  _item      [description]
		 * @param  {Boolean} isCallback [description]
		 * @return {[type]}             [description]
		 */
		callGps: function(item, _item, isCallback) {
			if (isCallback) {
				return [{
					text: "回调参数",
					items: [{
						id: item.eleId,
						name: "经纬度",
						param: "lal",
						text: "经纬度"
					}]
				}]
			}
			var reDatas = [{
				id: item.eleId,
				name: '是否回调',
				param: 'hasCallback',
				text: '是否回调'
			}];
			return reDatas;
		},
		/**
		 * 调用萤石云视频监控
		 */
		callYsVideo: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				id: item.eleId,
				name: '摄像头编号(通道)',
				param: 'cameraNo',
				text: '摄像头编号(通道)'
			}, {
				id: item.eleId,
				name: '序列号',
				param: 'deviceSerial',
				text: '序列号'
			}, {
				id: item.eleId,
				name: '开发者ID',
				param: 'developerId',
				text: '开发者ID'
			}];
			return reDatas;
		},
		/**
		 * 批量任务调度
		 */
		taskTable: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var pDatas = [{
					id: item.eleId,
					name: "频率",
					param: "frequency",
					text: "频率"
				}, {
					id: item.eleId,
					name: "开始时间",
					param: "startTime",
					text: "开始时间"
				}, {
					id: item.eleId,
					name: "结束时间",
					param: "endTime",
					text: "结束时间"
				}],
				params = getParametersByTaskTable(_item.outExt.value, isCallback);
			$.each(params, function(index, el) {
				pDatas.push({
					id: item.eleId,
					name: el.columnLabel,
					param: el.columnName,
					text: el.columnLabel
				});
			});
			var reDatas = [{
				text: "参数",
				items: pDatas
			}, {
				text: "系统属性",
				items: [{
					id: item.eleId,
					name: "禁用提示",
					param: "isAlt",
					text: "禁用提示"
				}]
			}];
			return reDatas;
		},
		/**
		 * 批量任务调度
		 */
		tableCombination: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				text: "系统属性",
				items: [{
					id: item.eleId,
					name: "禁用提示",
					param: "isAlt",
					text: "禁用提示"
				}]
			}];
			return reDatas;
		},
		/**
		 * 表列转换调度
		 */
		tableTransform: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			var reDatas = [{
				text: "系统属性",
				items: [{
					id: item.eleId,
					name: "禁用提示",
					param: "isAlt",
					text: "禁用提示"
				}, {
					id: item.eleId,
					name: "标段",
					param: "databaseId",
					text: "标段"
				}]
			}];
			return reDatas;
		},
		/**
		 * 设置定时循环任务
		 */
		setCyclicTask: function(item, _item, isCallback) {
			var reDatas = [];
			if (isCallback) {
				reDatas = [{
					text: "回调参数",
					items: [{
						id: item.eleId,
						name: "定时任务标识",
						param: "cyclicTask",
						text: "定时任务标识"
					}]
				}]
			} else {
				reDatas = [{
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "间隔时间",
						param: "delayTime",
						text: "间隔时间"
					}]
				}]
			}
			return reDatas;
		},

		/**
		 * 清除定时循环任务
		 */
		clearCyclicTask: function(item, _item, isCallback) {
			var reDatas = [];
			reDatas = [{
				text: "系统属性",
				items: [{
					id: item.eleId,
					name: "定时任务标识",
					param: "cyclicTask",
					text: "定时任务标识"
				}]
			}]
			return reDatas;
		},
		/**
		 * 验证
		 */
		mtVali: function(item, _item, isCallback) {
			var reDatas = [];
			if (isCallback) {
				reDatas = [{
					id: item.eleId,
					name: "验证结果",
					param: "valiResult",
					text: "验证结果"
				}];
			} else {
				reDatas = [{
					id: item.eleId,
					name: "层级差",
					param: "isVali",
					text: "层级差"
				}];
			}
			return reDatas;
		},
		/**
		 * 确认警示操作
		 */
		mtConfirm: function(item, _item, isCallback) {
			if (isCallback) {
				return [{
					id: item.eleId,
					name: "点击按钮",
					param: "btstate",
					text: "点击按钮"
				}];
			}
			return [{
				id: item.eleId,
				name: "标题名称",
				param: "title",
				text: "标题名称"
			}, {
				id: item.eleId,
				name: "标题颜色(1-6)",
				param: "titleType",
				text: "标题颜色(1-6)"
			}, {
				id: item.eleId,
				name: "内容文本",
				param: "message",
				text: "内容文本"
			}, {
				id: item.eleId,
				name: "确认文本",
				param: "btnOKLabel",
				text: "确认文本"
			}, {
				id: item.eleId,
				name: "确认颜色(1-6)",
				param: "btnOKClass",
				text: "确认颜色(1-6)"
			}, {
				id: item.eleId,
				name: "取消文本",
				param: "btnCancelLabel",
				text: "取消文本"
			}, {
				id: item.eleId,
				name: "取消颜色(1-6)",
				param: "btnCancelClass",
				text: "取消颜色(1-6)"
			}];
		},
		mtSubmit0: function(item, _item, isCallback) {
			
			var datas = [{
				id: item.eleId,
				name: "实例ID",
				param: "processId",
				text: "实例ID"
			}];
			
			if(isCallback){
				datas.push({
					id: item.eleId,
					name: "流程状态",
					param: "STATUS",
					text: "流程状态"
				});
			} else {
				datas.push({
					id: item.eleId,
					name: "任务ID",
					param: "taskId",
					text: "任务ID"
				});
				datas.push({
					id: item.eleId,
					name: "是否批量",
					param: "multiple",
					text: "是否批量"
				});
				datas.push({
					id: item.eleId,
					name: "动态指定人参数",
					param: "multiActorId",
					text: "动态指定人参数"
				});
			}
			return datas;
		},
		mtAssign0 : function(item, _item, isCallback){
			var datas = [{
				id: item.eleId,
				name: "实例ID",
				param: "processId",
				text: "实例ID"
			}];
			return datas;
		},
		mtBack0: function(item, _item, isCallback) {
			var datas = [{
				id: item.eleId,
				name: "实例ID",
				param: "processId",
				text: "实例ID"
			}, {
				id: item.eleId,
				name: "是否批量",
				param: "multiple",
				text: "是否批量"
			}, {
				id: item.eleId,
				name: "不弹窗",
				param: "notOpen",
				text: "不弹窗"
			}];
			return datas;
		},
		reject0: function(item, _item, isCallback) {
			var datas = [{
				id: item.eleId,
				name: "实例ID",
				param: "processId",
				text: "实例ID"
			}, {
				id: item.eleId,
				name: "任务ID",
				param: "taskId",
				text: "任务ID"
			}];
			return datas;
		},
		active0: function(item, _item, isCallback) {
			var datas = [{
				id: item.eleId,
				name: "实例ID",
				param: "processId",
				text: "实例ID"
			}];
			return datas;
		},
		cancel0: function(item, _item, isCallback) {
			var datas = [{
				id: item.eleId,
				name: "实例ID",
				param: "processId",
				text: "实例ID"
			}];
			return datas;
		},
		stop0: function(item, _item, isCallback) {
			var datas = [{
				id: item.eleId,
				name: "实例ID",
				param: "processId",
				text: "实例ID"
			}];
			return datas;
		},
		newSystemServer:function(item, _item, isCallback) {
			var value = _item.outExt.otherParam;
			var params = value.param,retDatas = [];
			if (isCallback) {
				params = value.callbackParam || [];
			}
			$.each(params, function(i, param) {
				retDatas.push({
					id: item.eleId,
					name: param.title,
					param: param.code,
					text: param.title,
					columnName: param.code,
				})
			});

			if (!isCallback){
				retDatas = [{
					text: "参数信息",
					items: retDatas
				},{
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}]
				}]
			}

			return retDatas;
		},
		systemServer: function(item, _item, isCallback) {
			if (isCallback) {
				if(_item.outExt.value && _item.outExt.value == 'addRole'){
					return [{
						id: item.eleId,
						text : "角色ID",
						name : "id",
						param : "id",
						columnName: "id",
					}];	
				}else if(_item.outExt.value && _item.outExt.value == 'addDepartment'){
					return [{
						id: item.eleId,
						text : "部门ID",
						name : "id",
						param : "id",
						columnName: "id",
					},{
						id: item.eleId,
						text : "部门编号",
						name : "nodeId",
						param : "nodeId",
						columnName: "id",
					}];	
				}
				return [];
			}

			var value = _item.outExt.otherParam;
			var params = value.param;
			var retDatas = [],itemset = [];
			$.each(params, function(i, param) {
				retDatas.push({
					id: item.eleId,
					name: param.title,
					param: param.code,
					text: param.title,
				})
			});
			
			retDatas = [{
				text: "参数信息",
				items: retDatas
			},
			{
				text: "节点信息",
				items : [{
					text : "复制节点",
					items : [{
						
						id: item.eleId,
						text : "indexId",
						name : "indexId",
						param : "indexId"
					},{
						
						id: item.eleId,
						text : "treeId",
						name : "treeId",
						param : "treeId"
					},{
						
						id: item.eleId,
						text : "parentId",
						name : "parentId",
						param : "parentId"
					}]
				},{
					text : "目标节点",
					items : [{
						
						id: item.eleId,
						text : "indexID",
						name : "t_indexId",
						param : "t_indexId"
					},{
						
						id: item.eleId,
						text : "treeId",
						name : "t_treeId",
						param : "t_treeId"
					},{
						
						id: item.eleId,
						text : "parentId",
						name : "t_parentId",
						param : "t_parentId"
					}]
				}]	
			},
			
		    {
				text: "系统属性",
				items: [{
					id: item.eleId,
					name: "禁用提示",
					param: "isAlt",
					text: "禁用提示"
				}]
			}];

			return retDatas;
		},
		mtSave: function(item,_item,isCallback){
			retDatas = [{
				text: "系统属性",
				items: [{
					id: item.eleId,
					name: "禁用提示",
					param: "isAlt",
					text: "禁用提示"
				}]
			}];
			return retDatas;
		},
		confirmMessage: function(item,_item,isCallback){
			retDatas = [{
				text: "提示框样式",
				items: [{
					
					text: "警告消息框",
					items:[{
						id: item.eleId,
						name: "alert",
						param: "alert",
						text: "显示"
					},{
						id: item.eleId,
						name: "标题",
						param: "isAlertTitle",
						text: "标题"
					},{
						id: item.eleId,
						name: "内容",
						param: "isAlertContent",
						text: "内容"
					}]
				},{
					text: "确认消息框",
					items : [{
						id: item.eleId,
						name: "confirm",
						param: "confirm",
						text: "显示"
					},{
						id: item.eleId,
						name: "标题",
						param: "isConfirmTitle",
						text: "标题"
					},{
						id: item.eleId,
						name: "内容",
						param: "isConfirmContent",
						text: "内容"
					}]
				},{
					text: "自动消失提示框",
					items : [{
						id: item.eleId,
						name: "toast",
						param: "toast",
						text: "显示"
					},{
						id: item.eleId,
						name: "消息语言",
						param: "isToast",
						text: "消息语言"
					},{
						id: item.eleId,
						name: "持续时间",
						param: "duration",
						text: "持续时间"
					}]
				}]
			}];
			return retDatas;
		},
		/**
		 * wpf移动通知
		 */
		wpfMobileNotification: function(item, _item, isCallback) {
			if (isCallback) {
				return [];
			}
			return [{
				id: item.eleId,
				name: "用户名",
				param: "user",
				text: "用户名"
			}, {
				id: item.eleId,
				name: "标题",
				param: "title",
				text: "标题"
			}, {
				id: item.eleId,
				name: "内容",
				param: "alert",
				text: "内容"
			}, {
				id: item.eleId,
				name: "页面ID",
				param: "pageId",
				text: "页面ID"
			},  {
				id: item.eleId,
				name: "页面路径",
				param: "pagePath",
				text: "页面路径"
			}, {
				id: item.eleId,
				name: "定时(yyyy-MM-dd HH:mm:ss)",
				param: "time",
				text: "定时(yyyy-MM-dd HH:mm:ss)"
			}, {
				id: item.eleId,
				name: "开始时间(yyyy-MM-dd HH:mm:ss)",
				param: "start",
				text: "开始时间(yyyy-MM-dd HH:mm:ss)"
			}, {
				id: item.eleId,
				name: "结束时间(yyyy-MM-dd HH:mm:ss)",
				param: "end",
				text: "结束时间(yyyy-MM-dd HH:mm:ss)"
			}, {
				id: item.eleId,
				name: "频率",
				param: "frequency",
				text: "频率"
			}, {
				id: item.eleId,
				name: "0:all,1:android,2:ios",
				param: "pushType",
				text: "0:all,1:android,2:ios"
			}];
		},
		mtPagePrint : function(item, _item, isCallback) {
			return [{
				id: item.eleId,
				name: "测试参数",
				param: "test",
				text: "测试参数"
			}]
		},
		/**
		 * 清除定时循环任务
		 */
		openWindowFunc: function(item, _item, isCallback) {
			var reDatas = [];
			reDatas = [{
				text: "基础参数",
				items: [{
					id: item.eleId,
					name: "连接地址",
					param: "url",
					text: "连接地址"
				},{
					id: item.eleId,
					name: '最大化',
					param: 'isMax',
					text: '最大化'
				}, {
					id: item.eleId,
					name: '宽度',
					param: 'width',
					text: '宽度'
				}, {
					id: item.eleId,
					name: '高度',
					param: 'height',
					text: '高度'
				}, {
					id: item.eleId,
					name: '子窗口',
					param: 'singlePage',
					text: '子窗口'
				}, {
					id: item.eleId,
					name: '模态',
					param: 'model',
					text: '模态'
				},{
					id: item.eleId,
					name: "跳转",
					param: "isturn",
					text: "跳转"
				},{
					id: item.eleId,
					name: '窗口名称',
					param: 'title',
					text: '窗口名称'
				}, {
					id: item.eleId,
					name: '拖拽移动(默认true)',
					param: 'draggable',
					text: '拖拽移动(默认true)'
				}, {
					id: item.eleId,
					name: '标题栏内边距',
					param: 'headerPadding',
					text: '标题栏内边距'
				}, {
					id: item.eleId,
					name: '背景模糊',
					param: 'backdrop',
					text: '背景模糊'
				}, {
					id: item.eleId,
					name: '点击背景关闭',
					param: 'closeByBackdrop',
					text: '点击背景关闭'
				}, {
					id: item.eleId,
					name: '标题栏背景色',
					param: 'headerBackgroundColor',
					text: '标题栏背景色'
				}, {
					id: item.eleId,
					name: '标题字体大小',
					param: 'headerFontSize',
					text: '标题字体大小'
				},{
					id: item.eleId,
					name: '透明度(0-1)',
					param: 'opacity',
					text: '透明度(0-1)'
				}]
			}]
			return reDatas;
		},
		/**
		 * 获取客户端ID地址
		 */
		getClientIp: function(item, _item, isCallback) {
			if(isCallback){
				return [{
					id: item.eleId,
					name: "客服端IP",
					param: "clientIp",
					columnName: "clientIp",
					text: "客服端IP"
				}];
			}else{
				return [{
					id: item.eleId,
					name: "测试",
					param: "test",
					columnName: "test",
					text: "测试"
				}];
			}
		},
		/**
		 * 附件下载
		 */
		downloadFile: function(item, _item, isCallback) {
			if(isCallback){
				return [];
			}else{
				return [{
					id: item.eleId,
					name: "附件ID",
					param: "attachmentId",
					columnName: "attachmentId",
					text: "附件ID"
				}];
			}
		},
		callteim:function(item, _item, isCallback) {
			var params = _item.outExt.inParams,retDatas = [];
			if (isCallback) {
				params = _item.outExt.callbackParams || [];
			}
			$.each(params, function(i, param) {
				retDatas.push({
					id: item.eleId,
					name: param.name,
					param: param.param,
					text: param.text,
					columnName: param.param,
				})
			});

			if (!isCallback){
				retDatas = [{
					text: "参数信息",
					items: retDatas
				},{
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}]
				}]
			}

			return retDatas;
		},
		parseIdentity:function(item, _item, isCallback) {
			var params = _item.outExt.inParams,retDatas = [];
			if (isCallback) {
				params = _item.outExt.callbackParams || [];
			}
			$.each(params, function(i, param) {
				retDatas.push({
					id: item.eleId,
					name: param.name,
					param: param.param,
					text: param.text,
					columnName: param.param,
				})
			});

			if (!isCallback){
				retDatas.push({
					id: item.eleId,
					name: "附件ID",
					param: "attachmentId",
					text: "附件ID"
				})
				retDatas = [{
					text: "参数信息",
					items: retDatas
				},{
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}]
				}]
			}

			return retDatas;
		},
	}, 
	
	/**
	 * 控件输出参数默认值
	 * （所有内置参数都可以放这里）
	 */
	ControlOutParameters = {
		officebt : {
			saveAs : function(item, _item){
				return [{
					text: "OFFICE另存属性",
					items: [{
						id: item.eleId,
						name: "文件名称",
						param: "fileName",
						text: "文件名称"
					}]
				}];
			}
		}
}



	// 输入输出参数
function initInOutParameterByEvents(data, inParam, outParam, outExt) {
	// debugger;
	if (data) {
		$(this).val(JSON.stringify(data));
		$('#id_' + $(this).attr('idfield')).val(data[0].name);
		closeLayer();
	} else {
		var saveData = mtxx.saveData,
			tfMsg = mtxx.tfMsg,
			inParamObj = inParam ? JSON.parse(inParam) : [],
			outParamObj = outParam ? JSON.parse(outParam) : [],
			inPages = [];
		$.each(inParamObj, function(i, v) {
			if (saveData.pageId != v.pageId && $.inArray(v.pageId, inPages) == -1) {
				inPages.push(v.pageId);
			}
		});
		$.each(outParamObj, function(i, val) {
			if (!val.isPage) {
				var v = JSON.parse(val.pObj);
				if (saveData.pageId != v.pageId && $.inArray(v.pageId, inPages) == -1) {
					inPages.push(v.pageId);
				}
			} else { // 选择页面情况
				inPages.push(val.id);
			}
		});
		var params = getEventsParametersByPageId(saveData.pageId, inPages), // 获取所有的输入控件的参数
			// ，包括关联页面
			inParams = {}, // 输出控件的输入参数
			cellParams, otypeParams = {};
		if (params) {
			/*
			 * // 当前选中是页面的时候 if(saveData.pageId == saveData.name当前选中的控件ID){
			 * $.each(params,function(i,v){ //输入形参定义 if(v.PARAMTYPE_ ==
			 * 'inParamDefined'){ if(v.NAME_ != v.PAGEID_){ // 控件级别的 v.text =
			 * v.TITLE_ || v.NAME_; v.items = []; if(v.VALUE_){
			 * $.each(JSON.parse(v.VALUE_),function(i,p){ p.text = p.name; p.id =
			 * v.NAME_; p.title = v.NAME_ + "-选中行-" + p.name; v.items.push(p);
			 * }); } inParams[v.PAGEID_][v.NAME_] = v ; //inParams[v.NAME_] = v; } }
			 * }); }else{ // 当前选中是控件 }
			 */
			$.each(params, function(i, v) {
				if (v.PARAMTYPE_ == 'inParamDefined') {
					v.text = v.TITLE_ || v.NAME_;
					v.items = [];
					if (v.VALUE_) {
						initObjParams(v);
					}
					var ppp = inParams[v.PAGEID_] || (inParams[v.PAGEID_] = {});
					ppp[v.NAME_] = v;
					// inParams[v.NAME_] = v;
				}
			});
		}
		var $in = inParam, // $("#" + tfMsg.linkageControlInId),//联动控件的输入控件
			$out = outParam /* $("#" + tfMsg.linkageControlId) */ , // 联动控件的输出控件
			get = function(drop, isIn, isOut) {
				var retArr = new Array();
				if (isIn) {
					var items = fzmt.getControlParams("page", saveData.pageId, saveData.pageId) || [],
						pageInItems;
					if (items.length) {
						items[0].items.lev = 1;
						items[0].items.pageId = saveData.pageId;
					}
					inParams[saveData.pageId] && (pageInItems = inParams[saveData.pageId][saveData.pageId])
					if (pageInItems) {
						pageInItems.text = "页面接收参数";
						var piis = pageInItems.items;

						function initPageItems(piis) {
							piis.lev = 1;
							piis.pageId = saveData.pageId;
							for (var i = 0, len = piis.length; i < len; i++) {
								var pii = piis[i];
								if (pii.items) {
									initPageItems(pii.items);
								} else {
									pii.type = "getRow_" + pii.param;
									pii.columnName = pii.columnLabel = pii.param;
									// pii.code =
									// "~F{"+saveData.pageId+"."+saveData.pageId+"."+""+"}"
									// ;
									pii.pageId = saveData.pageId;
									pii.datasetId = null;
								}
							}
						}
						initPageItems(piis);
						items.push(pageInItems);
					}
					items.lev = 1;
					items.pageId = saveData.pageId;
					retArr.push({
						text: "页面",
						id: saveData.pageId,
						items: items
					});
				}
				if (drop) {
					try {
						var arr = JSON.parse(drop);
						if (arr) {

							var obj0 = {
									text: '输入参数',
									items: [],
									level: 0
								},
								obj;
							$.each(arr, function(i, _item) {
								if (outExt) {
									_item.outExt = outExt;
								}
								var item = _item;
								if (isOut) {
									item = _item.pObj ? JSON.parse(_item.pObj) : _item;
								}
								var role = item.oEditor ? item.oEditor : item.eleId.length > 31 ? "page" : item.eleId.replace(/[^a-zA-Z]*/g, ''); // 只保留字母
								if (isIn) {
									var itemPObj = item.pObj ? JSON.parse(item.pObj) : {},
										itype = itemPObj.eleId;
									// 获取联动控件的参数
									items = fzmt.getControlParams(role,
										/*
										 * item.oEditor ?
										 * item.oEditor :
										 */
										item.eleId, item.pageId);
									for (var k = 0; k < items.length; k++) {
										if (items && items[k].items) {
											if (item.otype == "cell") {
												items[k].items.otype = itype;
											} else if (item.otype) {
												items[k].items.otype = itype;
												items[k].items.oEditor = item.oEditor;
											}
											items[k].items.lev = item.klevel || item.level;
											items[k].items.pageId = item.pageId;
										}
									}
									if (item.otype == "cell") {
										items.otype = itype;
									} else if (item.otype) {
										items.otype = itype;
										items.oEditor = item.oEditor;
									}
									items.lev = item.klevel || item.level; // 层级
									items.pageId = item.pageId;
									obj = {
										text: item.name,
										id: item.eleId,
										items: items,
									};
									retArr.push(obj);
								} else {
									if (item.otype == "cell") { // spreadjs
										// 获取输入输出参数方法
										if (item.otype == "cell") { // 获取cell参数定义
											if (!cellParams) {
												cellParams = getCellParams(item);
												// console.log(cellParams);
											}
										}
										var objItem = inParams[item.pageId][item.eleId],
											p = cellParams[item.eleId],
											itemPObj = JSON.parse(item.pObj);
										if (p) {
											p.items.lev = item.klevel || item.level;
											p.items.pageId = itemPObj.pageId;
											p.items.eventType = _item.eName;
											p.items.otype = itemPObj.eleId;
											//obj0.items.push(p);
											if(!objItem){
												objItem = p;
											}else{
												objItem.items.push(p);
											} 
										}
										objItem.items.lev = item.klevel || item.level;
										objItem.items.pageId = itemPObj.pageId;
										objItem.items.eventType = _item.eName;
										objItem.items.otype = itemPObj.eleId;
										obj0.items.push(objItem);
									} else if (item.otype) {// grid 内联字段
										if (!otypeParams[item.oEleId]) {
											otypeParams[item.oEleId] = getOtypeParams(item);
										}
										var p = otypeParams[item.oEleId][item.eleId];
										if (p) {
											itemPObj = JSON.parse(item.pObj);
											p.items.lev = item.klevel || item.level;
											p.items.pageId = itemPObj.pageId;
											p.items.eventType = _item.eName;
											p.items.otype = item.oEleId;
											p.items.oEditor = item.oEditor;
											obj0.items.push(p);
										}
									} else {
										//当前页面参数
										var currentPageParams = inParams[item.pageId],
											p = currentPageParams && (item.eleId in currentPageParams) && (_item.eName == "newWindow" || !(_item.eName in gobalParamDefined)) ? currentPageParams[item.eleId] : gobalParamDefined["convertItem"](item, _item);
										if (p) {
											p.items.lev = item.klevel || item.level;
											p.items.pageId = item.pageId;
											p.items.eventType = _item.eName;
											if (item.pageId === item.eleId) {
												if (item.srcUrl) { // 如果是打开页面
													p.items.srcUrl = item.srcUrl;
												}
												var gobalParamCallback = gobalParamDefined[_item.eName];
												$.merge(p.items, gobalParamCallback ? gobalParamCallback(item, _item) : []);
											} else if(ControlOutParameters[item.drole]){
												var ctrl = ControlOutParameters[item.drole];
												$.merge(p.items, ctrl[_item.eName] ? ctrl[_item.eName](item, _item) : []);
											}
											/**
											 * 合并页面参数到输出事件
											 */
											if(mergePageParam.indexOf(_item.eName)>=0){
												$.merge(p.items, currentPageParams && currentPageParams[item.pageId] ? currentPageParams[item.pageId].items : []);
											}
											obj0.items.push(p);
										}
									}

								}
							});

							if (!isIn) {
								retArr.push(obj0);
							}

						}
					} catch (e) {
						$.log(e);
					}
				}
				return retArr;
			};
		var menus;
		/*
		 * var linkPageObj = { text : '联动页面' , items : [] };
		 */
		menus = {
			'in': get($in, true),
			out: get($out, false, true) || [{
				text: '输入参数',
				items: [],
				level: 0
			}]
		};
		var isSavePageLink = false;
		var id = '';
		if (tfMsg.pageElementId) {
			if ($("#" + tfMsg.pageElementId).val()) {
				id = JSON.parse($("#" + tfMsg.pageElementId).val())[0].id;
			}
		}
		// getLinkPageParams(saveData.pageId);//

		if (tfMsg.pageElementId && !isSavePageLink) { // 获取关联页面的接收参数
			var p = $("#" + tfMsg.pageElementId);
			getInParamDefined(p.val());
		}

		// menus['out'][0].items.push(linkPageObj);

		var data;
		if (!$.isWindow(this)) {
			data = $(this).val();
		}
		if (data) {
			var v = JSON.parse(data)[0].datas;
			data = v;
		}
		if (!menus) {
			alert("参数不够!");
			closeLayer();
			return false;
		}
		return {
			datas: data || {},
			menus: menus
		};
	}
}
// 回调参数设置
function initCallbackByEvents(data, inParam, outParam, outExt) {
	if (data) {
		$(this).val(JSON.stringify(data));
		$('#id_' + $(this).attr('idfield')).val(data[0].name);
		closeLayer();
	} else {
		var saveData = mtxx.saveData,
			tfMsg = mtxx.tfMsg,
			inPages = [saveData.pageId];
		var params = getEventsParametersByPageId(saveData.pageId, inPages), // 获取当前页面的参数
			inParams = {}, // 输出控件的输入参数
			cellParams;
		if (params) {
			$.each(params, function(i, v) {
				if (v.PARAMTYPE_ == 'inParamDefined') {
					v.text = v.TITLE_ || v.NAME_;
					v.items = [];

					if (v.VALUE_) {
						/*
						 * $.each(JSON.parse(v.VALUE_), function(i, p) { p.text =
						 * p.name; p.id = v.NAME_; p.title = v.NAME_ + "-选中行-" +
						 * p.name; v.items.push(p); });
						 */
						initObjParams(v);
					}
					var ppp = inParams[v.PAGEID_] || (inParams[v.PAGEID_] = {});
					ppp[v.NAME_] = v;
				}
			});
		}

		var inArr = new Array();
		var items = [],
			pageInItems;
		if (items.length) {
			items[0].items.lev = 1;
			items[0].items.pageId = saveData.pageId;
		}
		inParams[saveData.pageId] && (pageInItems = inParams[saveData.pageId][saveData.pageId])
		if (pageInItems) {
			pageInItems.text = "页面接收参数";
			var piis = pageInItems.items;
			piis.lev = 1;
			piis.pageId = saveData.pageId;
			for (var i = 0, len = piis.length; i < len; i++) {
				var pii = piis[i];
				pii.type = "getRow";
				pii.columnName = pii.columnLabel = pii.param;
				// pii.code =
				// "~F{"+saveData.pageId+"."+saveData.pageId+"."+""+"}" ;
				pii.pageId = saveData.pageId;
				pii.datasetId = null;
			}
			items.push(pageInItems);
		}
		items.lev = 1;
		items.pageId = saveData.pageId;
		inArr.push({
			text: "页面",
			id: saveData.pageId,
			items: items
		});

		var outArr = new Array();
		var obj0 = {
			text: '回调参数',
			items: [],
			lev: 1,
			pageId: saveData.pageId
		};
		var eName, inCallback;
		if (outParam) {
			var outParamObj = JSON.parse(outParam);
			eName = outParamObj[0]['eName'];
			inCallback = gobalParamDefined[eName];
		}
		// currentPageParams = inParams[saveData.pageId],
		if (obj0 && (outExt || inCallback)) {
			if (!outExt) {
				outExt = {
					type: eName
				};
			}
			obj0.items.lev = 1;
			obj0.items.pageId = saveData.pageId;
			obj0.items.eventType = outExt.type;

			var gobalParamCallback = gobalParamDefined[outExt.type];
			$.merge(obj0.items, gobalParamCallback ? gobalParamCallback({}, {
				outExt: outExt
			}, true) : []);

			outArr.push(obj0);
		}

		var menus = {
			'in': outArr,
			'out': inArr
		};
		var data, ddata = [];
		if (!$.isWindow(this)) {
			data = $(this).val();
		}
		if (data) {
			var dataAry = JSON.parse(data);
			if (dataAry.length) {
				ddata = dataAry[0].datas;
			}
		}
		return {
			datas: ddata,
			menus: menus
		};

	}
}

/**
 * 输出控件扩展选项 urlStr 默认输出路径为 from/defined/outExp url 为全路径
 */
var outExpDefined = {
	"exprCalc": function() {
		return {
			title: "表达式计算选择",
			urlStr: "exprCalc",
		}
	},
	"mtCellExport": function() {
		return {
			title: "导出模板选择",
			url: "/mx/form/defined/outExp/exprCalc",
			params: {
				"isCell": true
			}
		}
	},
	"collect": function() {
		return {
			title: "逐级汇总选择",
			url: "/mx/form/defined/ex/collect",
		}
	},
	"mtcrud": function() {
		return {
			title: "CRUD选择",
			url: "/mx/dep/base/depBaseWdataSet",
		}
	},
	"downSqlLite": function() {
		return {
			title: "数据集选择",
			url: "/mx/form/defined/ex/selectSqlliteRule"
		}
	},
	"mtsearch": function() {
		return {
			title: "数据集选择",
			url: "/mx/dataset"
		}
	},
	/**
	 * 组合表
	 */
	"tableCombination": function() {
		return {
			title: "组合表选择",
			url: "/mx/sys/tableCombination"
		}
	},
	/**
	 * 批量任务表
	 */
	"taskTable": function() {
		return {
			title: "批量任务表选择",
			url: "/mx/sys/taskTable"
		}
	},
	/**
	 * 表列转换
	 */
	"tableTransform": function() {
		return {
			title: "表列转换选择",
			url: "/mx/sys/tableTransform"
		}
	},
	"systemServer": function() {
		return {
			title: "系统内置服务",
			url: "/mx/form/defined/ex/systemServer"
		}
	},
	"newSystemServer": function() {
		return {
			title: "系统内置服务",
			url: "/mx/form/formSystemServer/view"
		}
	},
	/**
	 * 服务
	 */
	"mt_service": function() {
		return {
			title: "微服务",
			url: "/mx/form/defined/ex/dataSourceService"
		}
	},
	"tableConvert" : function(){
		return {
			title: "表列转换",
			url: "/mx/form/defined/ex/tableConvert"
		}
	},
	"callteim" : function(){
		return {
			title: "外部服务",
			url: "/mx/form/defined/ex/teimService"
		}
	},
	"parseIdentity" : function(){
		return {
			title: "外部服务",
			url: "/mx/form/defined/ex/teimService"
		}
	},
};

/**
 * 服务输入输出参数处理
 */
var MtService = (function() {

	var service = {
		/**
		 * 数据集查询服务
		 */
		// dataset : {
		cb: function(item, _item, data) {
			var c = data.content,
				reDatas = [];
			return c.outPutParams && $.each(c.outPutParams, function(i, el) {
				reDatas.push({
					id: item.eleId,
					name: el.outPutParams_title,
					columnName: el.outPutParams_code,
					text: el.outPutParams_title
				});
			}), reDatas;
		},
		data: function(item, _item, data) {
				var c = data.content,
					reDatas = [];
				return c.inputParams && $.each(c.inputParams, function(i, el) {
					reDatas.push({
						id: item.eleId,
						name: el.inputParams_title,
						param: el.inputParams_code,
						text: el.inputParams_title
					});
				}), [{
					text: "服务参数",
					items: reDatas
				}, {
					text: "系统属性",
					items: [{
						id: item.eleId,
						name: "禁用提示",
						param: "isAlt",
						text: "禁用提示"
					}, {
						id: item.eleId,
						name: "标段",
						param: "databaseId",
						text: "标段"
					}]
				}];
			}
			// }
	};

	return function(item, _item, isCallback) {
		if (!_item) {
			return [];
		}
		var data = _item.outExt;
		var compCode = data.compCode;
		if (!compCode) {
			return [];
		}
		return service /* [compCode] */ [isCallback ? //
			"cb" : "data"](item, _item, data);
	};
})();

/*
 * 事件定义器中回调函数显示
 */
var callbackDefined = ["parseIdentity","callteim","callWpfSoaTif","officeSetValue", "mt_service", "mtReject0", "mtStop0", "mtCancel0", "mtActive0", "mtSubmit0", "mtBack0", "mtAssign0","exprCalc", "mtSave", "collect", "mtcrud", "convertService","cell2pdf","pdfmerge",
	"sendDown", "sendAllDown", "_init_", "mtsearch", "nodeMessage", "setCyclicTask","endEdit","linkage", "mtVali","confirmMessage","tableTransform", "mtConfirm","systemServer","newSystemServer","getClientIp","callTelNo","callGps","callScan"
];

/*
 * 把页面参数拼接到控件参数里面
 * 
 */
var mergePageParam = ["mtSubmit0"];