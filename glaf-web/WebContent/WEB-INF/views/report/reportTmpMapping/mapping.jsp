<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑模板数据映射信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style scoped>
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}

ul li {
	list-style-type: none;
}

.list {
	margin: 2px auto;
	width: 80%;
}

.list li.columnhead {
	font-size: 14px;
	font-weight: bold;
}

.list a {
	text-decoration: none;
}

.k-textbox {
	width: 18.8em;
}

.main-section {
	width: 800px;
	padding: 0;
}

label {
	display: inline-block;
	width: 100px;
	text-align: right;
	padding-right: 10px;
}

.required {
	font-weight: bold;
}

.accept, .status {
	padding-left: 90px;
}

.confirm {
	text-align: right;
}

.valid {
	color: green;
}

.invalid {
	color: red;
}

span.k-tooltip {
	margin-left: 6px;
}
</style>
<script type="text/javascript">
	var $id = "${param.templateId}";

	var dropdownlistDatasource = [];
	function getDropdownlistDatasource() {
		var str = $("#input-01").val();
		if (str) {
			var json = JSON.parse(str);
			json = json[0];
			dropdownlistDatasource = json.selectColumns;
			if ($currentDataSet && json.selectDatasource) {
				$currentDataSet["selectDatasource"] = json.selectDatasource[0];
			}
		}
		return dropdownlistDatasource;
	}

	var $currentDataSet = null, dataSources = {};

	$.ajax({
		url : '${contextPath}/mx/report/reportTmpMapping/getTempMappingById',
		data : {
			id : "${param.tmpMappingId}"
		},
		type : 'post',
		async : false,
		dataType : 'json',
		success : function(ret) {
			if (ret && ret.data) {
				var mappings = ret.data.reportTmpDataSetMappings;
				if (mappings && mappings.length) {
					$.each(mappings, function(i, v) {
						if (v.dataSetCode) {
							dataSources[v.dataSetCode] = v;
						}
					});
				}
			}
		}
	});

	$(function() {
		var $grid = $("#grid").kendoGrid({
			editable : true,
			selectable : 'row',
			columns : [ {
				field : 'title',
				title : '报表参数'
			}, {
				field : 'code',
				title : '代码'
			}, {
				field : 'fname',
				title : '数据集字段',
				editor : function(container, options) {
					var s = editors.stuff(container);
					var row = s.grid.dataSource.data()[s.index];
					editors.dropdownlist(container, options, {
						dataTextField : 'title',
						dataValueField : 'columnName',
						change : function(e) {
							var item = e.sender.dataItem();
							row["FieldType"] = item.FieldType;
							row["columnName"] = e.sender.value();
							row[options.field] = e.sender.text();
						},
						dataSource : window.getDropdownlistDatasource()
					});
				}
			} ]
		});

		var ds = new kendo.data.DataSource({
			transport : {
				read : {
					url : "${contextPath}/mx/reportTemplate/getTemplateById",
					dataType : "json",
					type : 'post',
					data : {
						tmpMappingId : '${param.tmpMappingId}',
						id : "${param.templateId}"
					}
				}
			},
			schema : {
				data : function(response) {
					if (response && response.data) {
						var rds = response.data.reportTmpDataSets;
						if (rds && rds.length) {
							$.each(rds, function(i, v) {
								var code = v.code;
								var d = dataSources[code];
								if (d) {
									v.selectDatasource = {
										datasetId : d.mappingDataSetId,
										name : d.mappingDataSetName
									};
									var tmp = null;
									if (tmp = d.reportTmpColMappings) {
										var map = {};
										$.each(tmp, function() {
											if (this.colCode) {
												map[this.colCode] = this;
											}
										});
									}
									if (tmp = v.reportTmpCols) {
										$.each(tmp, function($i, $v) {
											var col = map[$v.code];
											if (col) {
												$.extend(true, $v, {
													fname : col.colMappingTitle,
													FieldType : col.colMappingDtype,
													columnName : col.colMappingCode
												});
											}
										});
									}
								}
							});
						}
						return rds;
					} else
						return [];
				}
			}
		});

		$("#grid-01")
				.kendoGrid(
						{
							toolbar : '<div>报表数据集</div>',
							reorderable : true,
							dataSource : ds,
							columns : [ {
								field : 'name',
								title : '名称'
							}, {
								field : 'code',
								title : '代码'
							} ],
							selectable : "row",
							change : function(e) {
								var selectedRows = this.select();
								if (selectedRows && selectedRows[0]) {
									var item = this.dataItem(selectedRows[0]);
									if ($currentDataSet = item) {
										$("#legend-01").text(item.name);
										if (item.reportTmpCols) {
											$grid.data("kendoGrid")//
											.dataSource
													.data(item.reportTmpCols);
										}
										var s = $currentDataSet["selectDatasource"] || {};
										var dataSet = {
											datasetId : s.datasetId,
											name : s.name,
											title : s.name,
											id : "NO1"
										};
										if(dataSet.datasetId){
											window.getColumns(dataSet, function(rows){
												var datas = [{
													selectDatasource : [dataSet],
													selectColumns : rows,
													relation : []
												}];
												$("#input-01").val(JSON.stringify(datas));
											});
										} else {
											$("#input-01").val("");
										}
										
										$("#input-02").val(s.name);
									}
								}
							}
						});

		$("button.k-button").on("click.k-button", function(e) {
			var t = $(this).attr("t");
			t && (t = btnFunc[t]) && (t.call(this, e));
			return false;
		});
		$(window).on("resize", onResize);
	});

	/**
	 *	按钮方法
	 */
	var btnFunc = {
		select : function(e) {
			
			if(!$currentDataSet){
				alert("请选择报表数据集!");
				return false;
			}
			
			var params = {
				resultsElementId : 'input-01',
				tablenameElementId : 'input-02',
				flag : false,
				retFn : 'java1234'
			}, link = '${contextPath}/mx/form/defined/table/common_datasource?'
					+ $.param(params);
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : true,
				title : "选择数据集",
				closeBtn : [ 0, true ],
				shade : [ 0, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '980px', '710px' ],
				iframe : {
					src : link
				}
			});
		},
		'save-1' : function(e) {

		},
		save : function(e) {
			var $grid01 = $("#grid-01").data("kendoGrid");
			var datas = $grid01.dataSource.data();
			if (datas && datas.length) {

				var dataSets = [];
				var reportTmpMapping = {
					id : '${param.tmpMappingId}',
					reportTmpDataSetMappings : dataSets
				};
				for (var i = 0; i < datas.length; i++) {
					var data = datas[i];
					var selectDatasource = data.selectDatasource;
					if (!selectDatasource) {
						selectDatasource = {};
					}
					var reportTmpColMappings = [];
					if (data.reportTmpCols && data.reportTmpCols.length) {
						$.each(data.reportTmpCols, function(i, col) {
							var json = {
								dataSetMappingId : null,
								colCode : col.code,
								colName : col.name,
								colTitle : col.title,
								colDtype : col.dtype,
								colMappingCode : col.columnName,
								colMappingName : col.columnName,
								colMappingTitle : col.fname,
								colMappingDtype : col.FieldType
							};
							reportTmpColMappings.push(json);
						});
					}
					var dataSet = {
						tmpMappingId : '${param.tmpMappingId}',
						templateId : data.templateId,
						dataSetCode : data.code,
						dataSetName : data.title,
						mappingDataSetId : selectDatasource.datasetId,
						mappingDataSetName : selectDatasource.name,
						reportTmpColMappings : reportTmpColMappings
					};
					dataSets.push(dataSet);
				}

				var url = "${contextPath}/mx/report/reportTmpDataSetMapping/saveReportTmpDataSetMappingStuff";
				$.ajax({
					url : url,
					type : 'post',
					data : {
						reportTmpMapping : JSON.stringify(reportTmpMapping)
					},
					dataType : 'json',
					success : function(ret) {
						if (ret && ret.message) {
							alert(ret.message);
						}
					}
				});
			}
		}
	};
	
	function getColumns(dataSet, fn){
		$.ajax({
			url : '${contextPath}/rs/dataset/getSelectJson',
		//	contentType : "application/json",
			dataType : "json",
			type : 'POST',
			data : {
				datasetId : dataSet.datasetId
			},
			success : function(ret){
				
				var data = {};
				
				data.id = "NO1";
				data.datasetId = dataSet.datasetId ;
				data.title = dataSet.title ;
				data.name = dataSet.name ;
			//	data.createTime = dataItem.createTime ;
				data.databaseId = 0 ;
				data.chartType = "" ;
				//data.xAxisName = "" ;
				//data.yAxisName = "" ;
				//selectDatasource.push(data);
				//data.columns = transformRows(columns,data.id) ;
				//selectColumns.push();
				var rows = transformRows(ret, data);
				
				!!fn && (fn(rows));
			}
		});
	}
	
	
	
	//转变字段数据
	function transformRows(rowsData,data) {
		var rows = [];
		var size = rowsData.length ;
		for(var i=0;i<size;i++){
			var rowData = rowsData[i];
			var row = {};
			row.seq = data.id ;
			row.id = rowData.id;
			row.sid = row.seq + rowData.id;
			row.datasetId = rowData.datasetId;
			row.datasetName = data.name;
			row.databaseId = data.databaseId;
			row.tableName = rowData.tableName;
			row.columnName = rowData.columnLabel;
			row.columnLabel = rowData.columnLabel;
			row.title = rowData.title;
			//{"value":"~F{默认.项目文件收集分类.顺序号}","code":"~F{default.cell_useradd7028.cell_useradd7028_user3}"}
			var exp = JSON.parse(rowData.expression);
			row.FieldLength = exp.FieldLength;
			row.FieldType = exp.FieldType;
			row.tableNameCn = exp.tableNameCN;
			row.value = exp.value;
			row.code = exp.code;
			row.ctype = "" ; //系列or名称or值  类型
			rows.push(row);
		}
		return rows;
	}
	
	function java1234(data){
		if(!data) return false;
		$("#input-02").val(data.datasetNames.join(','));
		var source = $("#grid").data("kendoGrid").dataSource;
		if(source){
			var columns = data.results[0].selectColumns;
			var tmpMap = {};
			if(columns){
				$.each(columns, function(i, v){
					tmpMap[v.title] = v;	
				});
			}
			var datas = source.data();
			if(datas){
				$.each(datas, function(i, row){
					var t = tmpMap[row.title];
					if(!row.columnName && t){
						row["FieldType"] = t.FieldType;
						row["columnName"] = t.columnName;
						row["fname"] = t.title;
					}
				});
				$("#grid").data("kendoGrid").refresh();
			}
		}
		$("#input-01").val(JSON.stringify(data.results));
	}
	
</script>
</head>
<body style="margin-top: 0px;">
	<div id="container">
		<div id="vertical" style=''>
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td
							style="width: 500px; text-align: left; vertical-align: middle;"><img
							src="${contextPath}/images/setting_tools.png"
							style="vertical-align: middle;" /> <span
							style="font-family: Lucida Calligraphy; font-size: 20px; font-weight: bolder;">DataSource
								Mapping</span></td>

						<td style="text-align: right;">
							<button class='k-button' t='save'>保存配置</button>
						</td>
					</tr>
				</table>
			</div>

			<div id="center-pane" style="border: 0px;">
				<div style="border: 0px;">
					<div id="grid-01"></div>
				</div>
				<div style="border: 0px;">
					<div id="tabstrip-1" class='tabstrip' style="border: 0px;">
						<fieldset style="border:#DBDBDB dashed 1px;">
							<legend id="legend-01" style="font-size:12px;font-weight:800; ">映射数据集</legend>
							<input type="hidden" id="input-01" /> <input class='k-textbox'
								readonly="readonly" id="input-02">
							<button class='k-button' t='select' title="选择数据集">.</button>
							<!-- <button class='k-button' t='save-1'>保存</button> -->
						</fieldset>

						<fieldset style="border:#DBDBDB dashed 1px;">
							<legend style="font-size:12px;font-weight:800; ">映射列信息</legend>
							<div id="grid"></div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath }/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript">
	var $tabstrip = $(".tabstrip"), $vertical = $("#vertical");
	$vertical.css({
		height : $(window).height()
	}).kendoSplitter({
		orientation : "vertical",
		panes : [ {
			collapsible : false,
			resizable : false,
			scrollable : false,
			size : '32px'
		}, {
			collapsible : false,
			resizable : true,
			scrollable : false
		} ],
		layoutChange : onResize,
	});

	$("#center-pane").kendoSplitter({
		orientation : "horizontal",
		panes : [ {
			collapsible : false,
			resizable : true,
			scrollable : true,
			size : '20%'
		}, {
			collapsible : false,
			resizable : true,
			scrollable : true
		} ]
	});

	function onResize(e) {

		$vertical.css({
			height : $(window).height()
		});

		$tabstrip.css({
			height : $tabstrip.closest('div[role=group]').height()
		});

	}

	$tabstrip.each(function(i, item) {
		var $this = $(this);
		$this.kendoTabStrip({
			animation : {
				open : {
					effects : "fadeIn"
				}
			},
			select : function(e) {
				var dd = $(this.wrapper);
				$('#' + e.contentElement.id).height(
						dd.innerHeight()
								- dd.children(".k-tabstrip-items")
										.outerHeight() - 16);
			}
		});
	});

	var editors = {
		stuff : function(container) {
			var $tr = container.closest('tr');
			return {
				tr : $tr,
				index : $tr.index(),
				grid : $tr.closest('[data-role=grid]').data('kendoGrid')
			};
		},
		textbox : function(container, options) {
			var s = editors.stuff(container), $input = $("<input/>", {
				name : options.field,
				class : 'k-textbox'
			}).appendTo(container).change(
					function(e) {
						s.grid.dataSource.data()[s.index][options.field] = $(
								this).val();
					});
			return $input;
		},
		dropdownlist : function(container, options, dropdownlist) {
			var $input = $("<input/>", {
				name : options.field
			}).appendTo(container).kendoDropDownList(dropdownlist);

			return $input;
		},
		checkbox : function(container, options) {
			var $input = $("<input/>", {
				name : options.field,
				type : 'checkbox'
			}).appendTo(container);
			return $input;
		}
	};
</script>
</html>