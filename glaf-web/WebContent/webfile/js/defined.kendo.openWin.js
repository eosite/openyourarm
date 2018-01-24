/**
 * 通用属性返回方法 parent.CommonCallBack(data, targetId, fn);
 */
var CommonCallBack = (function() {
	return function(data, targetId, fn) {
		if (!data) {
			return false;
		}
		var target = targetId ? document.getElementById(//
		targetId) : null;
		if (fn && window[fn]) {
			window[fn].call(target, data);
		} else if (target) {
			target = $(target);
			var $name = $('#id_' + target.attr('itemid')), $this = $('#id_'
					+ target.attr('itemid') + "_hidden");
			$name.val(data.name);
			$this.val(JSON.stringify([ data ]));
		}
		closeLayer();
	};
})();

// 属性弹出页面配置
var selectObjFuncs = {

	dataSourceService : function(e) {

		var resultElementId = e.objElementId, tablenameElementId //
		= e.nameElementId, elementId = mtxx.saveData.name, pageId = mtxx.saveData.pageId
		var flag = 0;
		if (elementId.indexOf("ztree") != (-1)) {
			flag = 1; // ztree
		} else if (elementId.length > 30) {
			flag = 2; // 页面
		}
		var link = contextPath
				+ '/mx/form/defined/table/main3?resultsElementId='
				+ resultElementId //
				+ '&tablenameElementId=' + tablenameElementId
				+ '&fieldnameElementId=&flag=' + flag + "&pageId=" + pageId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择表/字段",
			closeBtn : [ 0, true ],
			shade : [ 0, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});

		return false;

		var url = contextPath + "/mx/form/defined/ex/dataSourceService?"
				+ $.param({
					targetId : e.objElementId
				});
		var index = $.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "服务选择",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : url
			}
		});
	},

	/**
	 * 保存设置
	 * 
	 * @param e
	 */
	'saveSourceSet' : function(e) {
		// createTable
		var url = contextPath + "/mx/form/defined/ex/sort?";
		var sd = mtxx.saveData;
		url = url + $.param({
			fn : 'saveSourceSetFunc', // 回调函数
			targetId : e.objElementId,
			isPage : sd.name == sd.pageId
		// 隐藏域的ID,
		});

		window.open(url);
		return false;

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "保存设置",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '1100px', '550px' ],
			iframe : {
				src : url
			}
		});
	},

	'selectField' : function(e) {
		var tableObjElementId = mtxx.tfMsg.tableObjElementId;
		var tableJson = '';
		if (tableObjElementId) {
			tableJson = jQuery('#' + tableObjElementId).val();
		}
		selectField('', '', e.nameElementId, e.objElementId, tableJson,
				e.objJson);
	},
	'selectFoldListField' : function(e) { // 树数据字段 弹出窗口
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.dataSourceSetId,
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '数据样式定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area: ['600px', '600px'],
			iframe : {
				src : link
			}
		});
	},
	'selectFoldListField' : function(e) { // 树数据字段 弹出窗口
		var link = mtxx.contextPath + '/mx/form/defined/ex/selectFoldListField?uploadtype=foldList'
				+ "&" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.dataSourceSetId,
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '数据样式定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area: ['600px', '600px'],
			iframe : {
				src : link
			}
		});
	},
	'selectRangeCalendarField' : function(e){
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceServiceId),
			val = $dataSourceSet.val();
		var sourceIdStr = mtxx.tfMsg.dataSourceServiceId;
		if(!val){
			sourceIdStr = mtxx.tfMsg.dataSourceSetId;
		}
		
		var link = mtxx.contextPath + '/mx/form/defined/ex/selectFoldListField?uploadtype=cangeCalendar'
				+ "&" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : sourceIdStr,	//数据集id
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '数据样式定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area: ['600px', '600px'],
			iframe : {
				src : link
			}
		});
	},
	'selectTreeField' : function(e) { // 树数据字段 弹出窗口
		var tableObjElementId = mtxx.tfMsg.dataSourceSetId
				|| mtxx.tfMsg.dataSourceSetByDiagramId
				|| mtxx.tfMsg.dataSourceSetGanttId;
		var tableJson = '';
		if (tableObjElementId) {
			tableJson = jQuery('#' + tableObjElementId).val();
		}
		selectTreeField('', '', e.nameElementId, e.objElementId, tableJson,
				e.objJson, '', mtxx.saveData.name);
	},

	'selectGanttField' : function(e) { // 树数据字段 弹出窗口
		var tableObjElementId = mtxx.tfMsg.dataSourceSetId;
		var tableJson = '';
		if (tableObjElementId) {
			tableJson = jQuery('#' + tableObjElementId).val();
		}
		selectTreeField('', '', e.nameElementId, e.objElementId, tableJson,
				e.objJson, '', mtxx.saveData.name, 'select_gantt_field');
	},
	'selectMediaListField' : function(e) { // 树数据字段 弹出窗口
		var tableObjElementId = mtxx.tfMsg.dataSourceSetId
		|| mtxx.tfMsg.dataSourceSetByDiagramId
		|| mtxx.tfMsg.dataSourceSetGanttId;
		var tableJson = '';
		if (tableObjElementId) {
			tableJson = jQuery('#' + tableObjElementId).val();
		}
		selectMediaListField('', '', e.nameElementId, e.objElementId, tableJson,
				e.objJson, '', mtxx.saveData.name, 'select_gantt_field');
	},

	'selectTable' : function(e) {
		selectTable('', '', e.nameElementId, e.objElementId, e.objJson);
	},

	'dataSourceSet' : function(e) {
		selectTableAndFields(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
		// selectTableAndFields(objElementId,nameElementId,"");
		// selectTableAndFields(objElementId,"tableName","fieldName");
	},

	'dataSourceSetByCharts' : function(e) {
		selectDatasetByCharts(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByPhone' : function(e) {
		selectDatasetByPhone(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByRoundWater' : function(e) {s
		selectDatasetByRoundWater(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByJPlayer' : function(e) {
		selectDatasetByJPlayer(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByMap' : function(e) {
		selectDatasetByMap(e.objElementId, e.nameElementId, mtxx.saveData.name,
				mtxx.saveData.pageId);
	},

	'dataSourceSetByVideos' : function(e) {
		selectDatasetByVideos(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByIndexedList' : function(e) {
		selectDatasetByIndexedList(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByCellSet' : function(e) {
		selectDatasetByCellSet(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByCalendar' : function(e) {
		selectDatasetByCalendar(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByDiagram' : function(e) {
		selectDatasetByCommon('diagramFieldCode', e.objElementId,
				e.nameElementId, mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByDiagramBt' : function(e) {
		selectDatasetByCommon('diagramBtFieldCode', e.objElementId,
				e.nameElementId, mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetByFlow' : function(e) {
		selectDatasetByCommon('multipleform', e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetGantt' : function(e) {
		selectDatasetByCommon('ganttFields', e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	// 伸缩缝支座图
	'dataSourceSetSeam' : function(e) {
		selectDatasetByCommon('seamFields', e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},
	'dataSourceSetUpdate' : function(e) {
		// selectDatasetByUpdate('ganttFields', e.objElementId, e.nameElementId,
		// mtxx.saveData.name, mtxx.saveData.pageId);
		//		
		var url = mtxx.contextPath
				+ "/mx/dep/base/depBaseWdataSet?view=/dep/base/depBaseWdataSet/sqlcrud&fn=getTableMsg&targetId="
				+ e.objElementId;
		window.open(url);
	},

	'dataSourceSetByJSGIS' : function(e) {
		selectDatasetByJSGIS(e.objElementId, e.nameElementId,
				mtxx.saveData.name, mtxx.saveData.pageId);
	},

	'dataSourceSetByGisUpload' : function(e) {
		var link = '/glaf/mx/form/defined/table/gisupload_datasource?resultsElementId='
				+ e.objElementId
				+ '&tablenameElementId='
				+ e.nameElementId
				+ '&pageId=' + mtxx.saveData.pageId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择数据集",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '700px' ],
			iframe : {
				src : link
			}
		});
	},

	'dataSourceSetByBIMUpload' : function(e) {
		var link = '/glaf/mx/form/defined/table/gisupload_datasource?uploadtype=bim&resultsElementId='
				+ e.objElementId
				+ '&tablenameElementId='
				+ e.nameElementId
				+ '&pageId=' + mtxx.saveData.pageId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择数据集",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '700px' ],
			iframe : {
				src : link
			}
		});
	},
	'dataSourceSetByBmapHeat' : function(e) {
		var link = '/glaf/mx/form/defined/table/gisupload_datasource?uploadtype=bmapheat&resultsElementId='
				+ e.objElementId
				+ '&tablenameElementId='
				+ e.nameElementId
				+ '&pageId='
				+ mtxx.saveData.pageId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择数据集",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '700px' ],
			iframe : {
				src : link
			}
		});
	},
	'dataSourceSetByBmapmarker' : function(e) {
		var link = '/glaf/mx/form/defined/table/gisupload_datasource?uploadtype=bmapmarker&resultsElementId='
				+ e.objElementId
				+ '&tablenameElementId='
				+ e.nameElementId
				+ '&pageId='
				+ mtxx.saveData.pageId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择数据集",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '700px' ],
			iframe : {
				src : link
			}
		});
	},
	'dataSourceSetByBmapClusterer' : function(e) {
		var link = '/glaf/mx/form/defined/table/gisupload_datasource?uploadtype=bmapclusterer&resultsElementId='
				+ e.objElementId
				+ '&tablenameElementId='
				+ e.nameElementId
				+ '&pageId='
				+ mtxx.saveData.pageId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择数据集",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '700px' ],
			iframe : {
				src : link
			}
		});
	},
	'dataSourceSetByEXCELUpload' : function(e) {
		var link = '/glaf/mx/form/defined/table/gisupload_datasource?uploadtype=excel&resultsElementId='
				+ e.objElementId
				+ '&tablenameElementId='
				+ e.nameElementId
				+ '&pageId='
				+ mtxx.saveData.pageId
				+ '&columnTemplateId='
				+ mtxx.tfMsg.addExcelModelId
				+ '&excelRuleId='
				+ mtxx.tfMsg.addExcelIdRule;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择数据集",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '700px' ],
			iframe : {
				src : link
			}
		});
	},

	'addExcelModel' : function(e) {
		var link = "/glaf/mx/form/defined/table/excel_table_hander?&elementId="
				+ e.nameElementId + "&objElementId=" + e.objElementId
				+ "&datasId=" + mtxx.tfMsg.dataSourceSetId + "&inParamId="
				+ mtxx.tfMsg.inParamDefinedId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "增加Excel模板",
			closeBtn : [ 0, true ],
			shade : [ 0, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '600px' ],
			iframe : {
				src : link
			}
		});
	},

	'addExcelIdRule' : function(e) {
		var link = "/glaf/mx/form/defined/table/add_excel_id_rule?&elementId="
				+ e.nameElementId + "&objElementId=" + e.objElementId
				+ "&datasId=" + mtxx.tfMsg.dataSourceSetId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "创建id解析规则",
			closeBtn : [ 0, true ],
			shade : [ 0, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '600px' ],
			iframe : {
				src : link
			}
		});
	},

	'columnTemplate' : function(e) {
		// alert(tfMsg.fieldObjElementId);
		
		/**
		 * 数据服务
		 */
		var dataSourceServiceId = mtxx.tfMsg.dataSourceServiceId, $o;
		if(dataSourceServiceId && ($o = $("#" + dataSourceServiceId)).get(0)){
			if($o.val()){
				return modifyTableHander(e.nameElementId, e.objElementId,dataSourceServiceId);
			}
		}
		modifyTableHander(e.nameElementId, e.objElementId,
				mtxx.tfMsg.dataSourceSetId);
		// modifyTableHander("tableHander","headerObjElementId",null,"fieldObjElementId");
	},
	'menuTemplate' : function(e) {
		// 参数1：返回的表头中文文本框ID
		// 参数2：返回的表头JSON字符串对象文本框ID
		// 参数3：选表后返回的结果文本框ID
		var elementId = e.nameElementId, objElementId = e.objElementId, datasId = mtxx.tfMsg.dataSourceSetId;

		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?1=1";
		link += "&elementId=" + elementId + "&objElementId=" + objElementId
				+ "&datasId=" + datasId;
		// link += "&tableHanderJsonArray="+tableHanderJsonArray;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑数据",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '1000px', '600px' ],
			iframe : {
				src : link
			}
		});
	},
	'stepTemplate' : function(e) {
		// 参数1：返回的表头中文文本框ID
		// 参数2：返回的表头JSON字符串对象文本框ID
		// 参数3：选表后返回的结果文本框ID
		var elementId = e.nameElementId, objElementId = e.objElementId, datasId = mtxx.tfMsg.dataSourceSetId;
		paramElementId = mtxx.tfMsg.inParamDefinedId;

		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?1=1";
		link += "&elementId=" + elementId + "&objElementId=" + objElementId
				+ "&paramElementId=" + paramElementId;
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑数据",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '1000px', '600px' ],
			iframe : {
				src : link
			}
		});
	},
	selectBgImage : function(e) {
		var url = contextPath + "/mx/form/defined/ex/" + e.dialogType + "?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId
				// 隐藏域的ID
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择背景图",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '1100px', '680px' ],
			iframe : {
				src : url
			}
		});
	},
	'updateField' : function(e) {
		var fieldJson = '';
		if (mtxx.tfMsg.fieldObjElementId) {
			fieldJson = jQuery('#' + mtxx.tfMsg.fieldObjElementId).val();
			var nameVal = jQuery('#' + mtxx.tfMsg.filedNameElementId).val();
			jQuery('#' + e.nameElementId).val(nameVal);
		}
		modifyField('', mtxx.tfMsg.fieldObjElementId, fieldJson);
	},

	'combineFields' : function(e) {
		selectObjFuncs.defaultOrderParameters.call(this, e);
	},
	'countFields' : function(e) {
		selectObjFuncs.defaultOrderParameters.call(this, e);
	},
	'pageParameters' : function(e) {
		selectObjFuncs.defaultOrderParameters.call(this, e);
	},
	'defaultOrderParameters' : function(e) {
		alert('defaultOrderParameters 未完成');
		// operateFields(obj,dialogType);
	},

	inParamDefined : function(e) {
		var url = contextPath
				+ "/mx/form/defined/param/paramDefined?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					datasourceElementId : mtxx.tfMsg.dataSourceSetId
							|| mtxx.tfMsg.dataSourceSetByDiagramBtId
							|| mtxx.tfMsg.dataSourceSetByChartsId, // 数据集的隐藏域ID
					saveId : mtxx.saveData.formRuleId
				});

		/**
		 * 获取服务参数
		 */
		if (mtxx.tfMsg.dataSourceServiceId) {
			var $id = $("#" + mtxx.tfMsg.dataSourceServiceId), val;
			if ($id.get(0) && (val = $id.val())) {
				var json = JSON.parse(val)[0], inputParams, params = {}, index = 0;
				if (json.datasource && json.datasource.length) {
					inputParams = json.datasource[0].params;
				} else {
					inputParams = json.content.inputParams;
				}
				$.each(inputParams, function(i, v) {
					params[v.inputParams_code] = {
						name : v.inputParams_title,
						param : v.inputParams_code
					};
					index++;
				});
				if (index > 0) {
					var targetId = e.objElementId;
					var $target = $("#" + targetId);
					if ($target.get(0)) {
						if (val = $target.val()) {
							json = JSON.parse(val)[0];					
						} 						
						(!json || !json.source) && (json = {
								"type" : "mutil",
								source : []
							});						
						var names = [];
						if (json.source && json.source.length) {
							$.each(json.source, function(i, v) {
								if (params[v.param]) {
									delete params[v.param];
									index--;
								}
								names.push(v.name);
							});
						}
						if (index > 0) {
							$.each(params, function(k, v) {
								json.source.push(v);
								names.push(v.name);
							});
						}
						json.name = names.join(",");
						window.CommonCallBack(json, targetId, "");
					}
				}
			}
		}

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "输入形参定义",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			// area: ['680px', '500px'],
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : url
			}
		});
	},

	inParameters : function(e) {
		var url = contextPath
				+ "/mx/form/defined/param/inparam?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					dataSourceSetId : mtxx.tfMsg.dataSourceSetId
							|| mtxx.tfMsg.dataSourceSetByChartsId
							|| mtxx.tfMsg.dataSourceSetByVideosId
							|| mtxx.tfMsg.dataSourceSetByJSGISId
							|| mtxx.tfMsg.dataSourceSetByCalendarId,
					inParamDefinedId : mtxx.tfMsg.inParamDefinedId
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "输入表达式定义",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '500px' ],
			iframe : {
				src : url
			}
		});
	},

	selectPage : function(e) { // 选择关联页面
		// return obj {name : '',id : '',url : '',node : obj}
		var url = contextPath + "/mx/form/defined/ex/selectPage?" + $.param({
			fn : 'selectPageBack',
			targetId : e.objElementId,
		});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "页面选择",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '500px' ],
			iframe : {
				src : url
			}
		});
	},

	selectReportTemplate : function(e) {
		var url = contextPath + "/mx/form/defined/ex/selectReportTemplate?"
				+ $.param({
					id : e.objElementId,
					name : e.nameElementId,
					fn : 'selectReportTemplateFunc'
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "报表模板选择",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '500px' ],
			iframe : {
				src : url
			}
		});
	},

	tableRelationDefined : function(e) {
		var url = contextPath + '/mx/form/defined/ex/tableRelationDefined';
		window.open(url);
	},

	selectTabstrip : function(e) {
		var link = contextPath + '/mx/form/defined/ex/tabstrip_datasource?'
				+ $.param({
					id : e.objElementId,
					name : e.nameElementId
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选项卡配置",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '600px', '500px' ],
			iframe : {
				src : link
			}
		});
	},

	paraType : function(e) {
		var link = contextPath + '/mx/form/defined/param/outInParam?'
				+ $.param({
					pageId : mtxx.jQueryFrame.attr('id'),
					eleId : e.objElementId,
					eleType : mtxx.saveData.name,
					fn : "initInOutParameter"
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "输入&输出关系定义",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '500px' ],
			iframe : {
				src : link
			}
		});
	},

	datatextfield : function(e) {
		selectObjFuncs.datavaluefield.call(this, e);
	},
	datavaluefield : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	vectorXField : function(e) {
		selectObjFuncs.vectorXValField.call(this, e);
	},
	vectorXValField : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	vectorYField : function(e) {
		selectObjFuncs.vectorYValField.call(this, e);
	},
	vectorYValField : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	sliderImage : function(e) {
		selectObjFuncs.sliderImageField.call(this, e);
	},
	sliderImageField : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	sliderTitle : function(e) {
		selectObjFuncs.sliderTitleField.call(this, e);
	},
	sliderTitleField : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	vectorStartField : function(e) {
		selectObjFuncs.vectorStartFunc.call(this, e);
	},
	vectorStartFunc : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	vectorEndField : function(e) {
		selectObjFuncs.vectorEndFunc.call(this, e);
	},
	vectorEndFunc : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	selectVectorField : function(e){
		selectObjFuncs.selectVectorFunc.call(this, e);
	},
	selectVectorFunc : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	takesPartField : function(e) {
		selectObjFuncs.takesPartFunc.call(this, e);
	},
	takesPartFunc : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId), val = $dataSourceSet
				.val();
		if (!val) {
			alert('请先配置数据源!');
			return false;
		} else {
			var data = JSON.parse(val);
			if (data[0]) {
				var columns = data[0].columns;
				if(!columns.length){
					alert('请先选择列!');
					return false;
				}

				var obj = $("#" + e.objElementId), json = [ {} ];

				if (obj.val()) {
					json = JSON.parse(obj.val());
				}else{
					var dobj = columns[0];
					dobj.name = dobj.title;
					json = [dobj];
					$("#" + e.nameElementId).val(dobj.name);
					obj.val(JSON.stringify(json));
				}

				$("#selectFieldComb").kendoDropDownList({
					dataSource : columns,
					dataTextField : "title",
					dataValueField : "columnName",
					change : function(s) {
						var data = s.sender.dataItem();
						data.name = data.title;
						$("#" + e.nameElementId).val(data.name);
						obj.val(JSON.stringify([ data ]));
					},
					value : json[0].columnName
				});

				$("#selectField").show().dialog({
					title : '请选择字段',
					buttons : [ {
						text : '确定',
						click : function(e) {
							$(e).dialog('close');
						}
					}, {
						text : '取消',
						click : function(e) {
							$(e).dialog('close');
						}
					} ]
				});

			}
		}
	},
	selectIcon : function(e) {
		var link = contextPath + "/mx/image/choose?" + $.param({
			fn : 'selectIconFunc',
			elementId : e.objElementId
		});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择图标",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '1100px', '580px' ],
			iframe : {
				src : link
			}
		});
	},
	selectImage : function(e) {
		var link = contextPath + "/mx/form/defined/ex/" + e.dialogType + "?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId
				// 隐藏域的ID
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择图片",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '1100px', '580px' ],
			iframe : {
				src : link
			}
		});
	},
	yAxisPlotBands : function(e) {
		var url = contextPath + "/mx/form/defined/charts/yAxisPlotBands?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "Y轴区域带",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '680px', '500px' ],
			iframe : {
				src : url
			}
		});
	},
	axisSet : function(e) {
		var url = contextPath + "/mx/form/defined/charts/axisSet?" + $.param({
			nameElementId : e.nameElementId, // 展示的ID
			objelementId : e.objElementId, // 隐藏域的ID
		});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "轴设置",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '500px' ],
			iframe : {
				src : url
			}
		});
	},
	screenZoom : function(e) {
		var url = contextPath + "/mx/form/defined/ex/screenZoom?" + $.param({
			nameElementId : e.nameElementId, // 展示的ID
			objelementId : e.objElementId, // 隐藏域的ID
		});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "屏幕缩放模式",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '980px', '500px' ],
			iframe : {
				src : url
			}
		});
	},
	'customDefined' : function(e) {
		var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceServiceId),
			val = $dataSourceSet.val();
		var sourceIdStr = mtxx.tfMsg.dataSourceServiceId;
		if(!val){
			sourceIdStr = mtxx.tfMsg.dataSourceSetId;
		}
		var link = mtxx.contextPath
				+ '/mx/form/defined/ex/'
				+ e.dialogType
				+ "?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.inParamDefinedId,
					datasourceElementId : sourceIdStr
							|| mtxx.tfMsg.dataSourceSetByDiagramBtId
							|| mtxx.tfMsg.dataSourceSetByChartsId // 数据集的隐藏域ID
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	},
	'customDefinedByCharts' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/customDefined' + "?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.inParamDefinedId,
					datasourceElementId : '', // 数据集的隐藏域ID
					getParamFn : 'getParamFn',
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	},
	'customDefinedByMenu' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/customDefined' + "?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.inParamDefinedId,
					datasourceElementId : '', // 数据集的隐藏域ID
					getParamFn : 'getParamFn',
					sysMenuData : JSON.stringify([{
						text: '菜单ID',
						type: 'getMenu',
						columnName: 'id'
					}, {
						text: '菜单父ID',
						type: 'getMenu',
						columnName: 'parentId'
					}, {
						text: '菜单名称',
						type: 'getMenu',
						columnName: 'name'
					}, {
						text: '菜单图标',
						type: 'getMenu',
						columnName: 'icon'
					}, {
						text: '菜单链接',
						type: 'getMenu',
						columnName: 'url'
					}])
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	},
	'customDefinedBy' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/colorDefineDat' + "?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.inParamDefinedId,
					datasourceElementId : '', // 数据集的隐藏域ID
					getParamFn : 'getParamFn',
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	},
	'stepDefined' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.dataSourceSetId,
					fixDataSourceId : mtxx.tfMsg.stepTemplateId
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	},
	'jsgis_dataStyle' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.dataSourceSetByJSGISId,
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '数据样式定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'calendar_dataStyle' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.dataSourceSetByCalendarId,
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '数据样式定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'selectMenuField' : function(e) { // 菜单数据字段 弹出窗口

		var tableObjElementId = mtxx.tfMsg.dataSourceSetId
				|| mtxx.tfMsg.dataSourceSetByDiagramId;
		var tableJson = '';
		if (tableObjElementId) {
			tableJson = jQuery('#' + tableObjElementId).val();
		}
		var databaseCode = '', fieldIdElementId = '', fieldNameElementId = e.nameElementId, fieldObjElementId = e.objElementId, fieldJson = e.objJson, endFunction = '', elementId = mtxx.saveData.name;
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?";

		link += "databaseCode=" + databaseCode;

		var flag = false;
		if (elementId.indexOf("treelist") != (-1)) {
			flag = true;
			link += "&flag=" + flag;
		}

		if (tableJson == "") {
			layer.alert("请选择数据集", 3);
			return;
		}
		// link += "&tableJson="+encodeURIComponent($.base64.encode(tableJson));
		link += "&fieldIdElementId=" + fieldIdElementId
				+ "&fieldNameElementId=" + fieldNameElementId
				+ "&fieldObjElementId=" + fieldObjElementId;

		tableJsonArray = tableJson;
		if (fieldJson != "") {
			// link +=
			// "&fieldJson="+encodeURIComponent($.base64.encode(fieldJson));
			fieldJsonArray = fieldJson;
		}

		// layer彻底关闭后的回调函数
		if (endFunction == undefined) {
			endFunction = function(index) {
				fieldJsonArray = [];
			}
		}

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "选择字段",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '600px', '600px' ],
			iframe : {
				src : link
			},
			end : endFunction
		});
	},
	flowDefined : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					targetId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
					fn : e.dialogType + 'Func'
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : "工作流程定义",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'eventsDefined' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "2?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '事件定义器',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},

	HTMLDefined : function(e) { // HTML 样式定义
		var link = mtxx.contextPath + "/mx/html/editor/view/?" + $.param({
			getParamFn : 'getParamFn',
			getVarFn : "getVarFn",
			initHTMLFn : "htmlDefinedFunc",
			getFieldFn : 'getDataSourceField',
			retFn : 'htmlDefinedFunc',
			targetId : e.objElementId, // 隐藏域的ID
		});

		window.open(link);

	},
	FTPField : function(e) {
		var url = contextPath + "/mx/form/defined/ex/FTPField?" + $.param({
			nameElementId : e.nameElementId, // 展示的ID
			objelementId : e.objElementId, // 隐藏域的ID
			dataSourceSetId : mtxx.tfMsg.dataSourceSetId, // 数据集ID
		});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "字段设置",
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '480px', '300px' ],
			iframe : {
				src : url
			}
		});
	},
	'jsgisParam' : function(e) {
		var code = '';
		if (mtxx.tfMsg.pageElementId) {
			var str = $("#" + mtxx.tfMsg.pageElementId).val();
			if (str) {
				var datas = JSON.parse(str);
				if (datas && datas.length) {
					code = datas[0].node.code || '';
				}
			}
		}
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'), // 页面ID
					code : code
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	},

	selectPro : function(e) {
		var dataSourceSetByFlowId = mtxx.tfMsg.dataSourceSetByFlowId, warning = function(
				msg) {
			alert(msg || '请选择工作流信息!');
			return false;
		};
		if (dataSourceSetByFlowId) {
			var $obj = $("#" + dataSourceSetByFlowId);
			var val = $obj.val();
			if (val) {
				var data = JSON.parse(val);
				if (data && data.length) {
					var selectColumns = data[0].selectColumns, changeType = {};
					if (selectColumns && selectColumns.length) {
						$.each(selectColumns, function(i, v) {
							if (v.ctype) {
								changeType[v.ctype] = v.columnLabel;
							}
						});
						$("#" + e.objElementId).data("changeType", changeType);
					} else {
						return warning("请选择列!");
					}
					var selectDatasource = data[0].selectDatasource;
					if (selectDatasource && selectDatasource[0]) {
						var datasetId = selectDatasource[0].datasetId;
						e.link = mtxx.contextPath + '/mx/dataset/preview?'
								+ $.param({
									id : datasetId,
									fn : 'selectProFunc',
									view : '/form/defined/ex/selectPro',
									targetId : e.objElementId
								// 隐藏域的ID
								});
						selectObjFuncs["default"](e);
					}
				}
			} else {
				return warning();
			}
		} else {
			return warning();
		}
	},
	'validate' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '验证定义器',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'jsgisDefaultView' : function(e) { // gis地图默认显示位置
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
					gisUrlId : mtxx.tfMsg.jsgisUrlId
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '默认显示选择',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'multiMapPosition' : function(e) { // gis地图默认显示位置
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
					gisUrlId : mtxx.tfMsg.jsgisUrlId
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '多地图选择按钮位置定位',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'permissionDefined' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '权限定义器',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	"cellPageSet" : function(e) {
		var tfMsg = mtxx.tfMsg;
		if (tfMsg.pageElementId) {
			var $p = $("#" + tfMsg.pageElementId);
			if ($p.val()) {
				var json = JSON.parse($p.val());
				if (json = json[0]) {
					if (json.id) {
						var url = mtxx.contextPath
								+ "/mx/form/defined/spreadjs/list?" + $.param({
									id : json.id,
									pageId : mtxx.jQueryFrame.attr('id')
								});
						window.open(url);
					}
				}
			} else {
				alert("请选择cell页面!");
			}
		}
	},
	// 正态分布
	'curveType' : function(e) {
		var link = e.link
				|| (mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
						+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'), // 页面ID
					dataSourceSetId : mtxx.tfMsg.dataSourceSetByChartsId
				// 数据集id
				}));

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '曲线类型',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '910px', '650px' ],
			iframe : {
				src : link
			}
		});
	},
	// 正态分布
	'common_tablesource' : function(e) {
		var link = e.link
				|| (mtxx.contextPath + '/mx/form/defined/table/' + e.dialogType
						+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'), // 页面ID
					dataRole : mtxx.saveData.dataRole, // dataRole
				}));

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '物理表设置',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '910px', '650px' ],
			iframe : {
				src : link
			}
		});
	},
	'levellist_dataStyle' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.dataSourceSetId,
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '数据样式定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'ztree_dataStyle' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.dataSourceSetId,
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '数据样式定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'grid_combined_field' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.columnTemplateId, // 表头隐藏域
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '合并列选择',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '600px', '600px' ],
			iframe : {
				src : link
			}
		});
	},
	'cellReportTools' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '报表工具条定义',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ $(document).width() - 10, $(document).height() - 10 ],
			iframe : {
				src : link
			}
		});
	},
	'setmapdata' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.columnTemplateId, // 表头隐藏域
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : 'excel解析规则',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '650px', '600px' ],
			iframe : {
				src : link
			}
		});
	},
	'excelUploadRule' : function(e) {
		var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
				+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.columnTemplateId, // 表头隐藏域
					pageId : mtxx.jQueryFrame.attr('id'),
					eleType : mtxx.saveData.name,
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : 'excel解析规则',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '650px', '600px' ],
			iframe : {
				src : link
			}
		});
	},
	'default' : function(e) {
		var link = e.link
				|| (mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
						+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'), // 页面ID
				}));

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	},
	'childAndPage' : function(e) {
		var link = e.link
				|| (mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType
						+ "?" + $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					pageId : mtxx.jQueryFrame.attr('id'), // 页面ID
				}));

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : '选中联动',
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '250px', '250px' ],
			iframe : {
				src : link
			}
		});
	},
	'colorDefine' : function(e) {
		var link = mtxx.contextPath
				+ '/mx/form/defined/ex/'
				+ e.dialogType
				+ "?"
				+ $.param({
					nameElementId : e.nameElementId, // 展示的ID
					objelementId : e.objElementId, // 隐藏域的ID
					paramElementId : mtxx.tfMsg.inParamDefinedId,
					datasourceElementId : mtxx.tfMsg.dataSourceSetId
							|| mtxx.tfMsg.dataSourceSetByDiagramBtId
							|| mtxx.tfMsg.dataSourceSetByChartsId // 数据集的隐藏域ID
				});
		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : false,
			title : e.dialogType,
			closeBtn : [ 0, true ],
			shade : [ 0.6, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '800px', '500px' ],
			iframe : {
				src : link
			}
		});
	}
};