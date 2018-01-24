<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("theme", "default");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/defined.kendo.paramConvert.js"></script>
<style>
.wdt {
	height: 30px;
}
.wdtw{
	width : 100px;
}
div.k-window-content {
    padding: 0;
}
#toolbar {
	border-width: 0 0 1px;
}
#widgets {
    margin: 0;
    padding: 10px;
    border-width: 0 0 1px 0;
}
.k-k-title{
	width: 1px;
	min-width:1px !important;
}
.k-k-div{
	height: 26px;
}
.hasMany{
	color: red;
}
</style>
<script type="text/x-kendo-template" id="template">
	<div id="batch">
		列宽：<input id="batch_columnWidth" data-role="numerictextbox" data-format="\\#"/>
		对齐方式：<input id="batch_alignment" data-role="dropdownlist" data-text-field="text" data-value-field="value" data-source="alignmentSource" />
		<button data-role="button" data-click="">批量填写</button>
	</div>
</script>
<script type="text/javascript">
	var fieldTypeControl = [], $height = $(window).height();
	function GetHeight(){
		return ($height / 1.12);
	}
	$.ajax({
		url : '${pageContext.request.contextPath }/mx/form/defined/getDictByCode',
		data : {
			code : 'FieldTypeControl'
		},
		type : 'post',
		dataType : 'json',
		async : false,
		success : function(data) {
			$.each(data, function(i, d) {
				var e = {};
				e.code = d.code;
				e.value = d.value
				fieldTypeControl.push(e);
			});
		}
	});

	//转换字段类型
	function getfieldTypeValue(fieldType) {
		var v = "";
		$.each(fieldTypeControl, function(i, d) {
			if (d.code == fieldType) {
				v = d.value;
			}
		})
		return v;
	}
	function init(){
		if('${param.resultsElementId}' != ''){
			var results = parent.document.getElementById('${param.resultsElementId}').value;
			if(results){
				var datas = JSON.parse(results);
				if(datas.length){
					$.merge(selectDatasource, datas[0].datasource||datas);
					$.merge(selectColumns, datas[0].columns);
					
					var datasource = datas[0].datasource;
					if(datasource && datasource[0]){
						$.merge(columnsGridDataSource, datasource[0].columnsGridDataSource || []);
					}
					
				}
			}
		}
	}
	//已选择的数据集
	var selectDatasource = [];
	//已选择的字段
	var selectColumns = [];
	//图片链接
	var linkImgDataSource = [];
	//自定义控件数据源
	var customSource = [] ;
	
	var columnsGridDataSource = [];
	
	init();
	//刷新 已选择数据集
	function rushSelectDatasourceGrid() {
		//刷新选择的数据集
		var grid = $("#selectDatasourceGrid").data("kendoGrid");
		grid.dataSource.read();
		//刷新下拉列表
		var dropdownlist = $("#datasourceDropdownlist").data("kendoDropDownList");
		dropdownlist.dataSource.read();
		//刷新字段grid
		var columnsGrid = $("#columnsGrid").data("kendoGrid");
		columnsGrid.dataSource.read();
	//	columnsGrid.dataSource.transport.options.read.data.datasetId = (selectDatasource.length > 0 ? (selectDatasource[0].dataSetId||selectDatasource[0].datasetId) : '');
	//	columnsGrid.dataSource.read();
	}
	//刷新 选择字段
	function rushSelectColumns() {
		//刷新已选择字段列表
		var selectColumnsGrid = $("#selectColumnsGrid").data("kendoGrid");
		selectColumnsGrid.dataSource.read();
		//刷新编辑字段
		var fieldColumnsGrid = $("#fieldColumnsGrid").data("kendoGrid");
		fieldColumnsGrid.dataSource.read();
	}

	//刷新 选择字段
	function rushFieldColumns() {
		//刷新编辑字段
		var fieldColumnsGrid = $("#fieldColumnsGrid").data("kendoGrid");
		fieldColumnsGrid.dataSource.read();
	}


	//选卡项 切换
	function onSelect(e) {
		if (selectDatasource.length == 0) {
			layer.alert('请选择数据集！', 3);
			e.preventDefault();
		}
	}
	//转变字段数据
	function transformRow(rowData) {
		var row = {};
		row.id = rowData.id;
		row.datasetId = rowData.datasetId;
		row.tableName = rowData.tableName;
		row.columnName = rowData.columnLabel;
		row.columnLabel = rowData.columnLabel;
		row.title = rowData.title;
		//{"value":"~F{默认.项目文件收集分类.顺序号}","code":"~F{default.cell_useradd7028.cell_useradd7028_user3}"}
		var exp = {};
		try{
			exp = JSON.parse(rowData.expression);
		}catch(e){
			exp = JSON.parse(rowData.exp);
		}
		row.FieldLength = exp.FieldLength;
		row.FieldType = exp.FieldType;
		row.tableNameCn = exp.tableNameCN;
		row.value = exp.value;
		row.code = exp.code;
		//selectColumns.push(row);
		if('${param.flag}' != '1'){ //如果不是树才加载其他值 
			//其他值
			row.isShowList = true;
			row.isFilterable = false;
			row.isSortable = false;
			row.isMenu = false;
			row.columnWidth = "200px";
			row.alignment = 'left';
			row.editor = 'maskedtextbox';
			row.formatValue = '';
			row.isRequired = false;
			row.defaultVlue = '';
			row.dataValidation = '';
			row.hidLinkImg = '';
			row.isValidate = false;
			row.isEditor = false;
			row.validateMsg = '';
			row.numberMinValue = '';
			row.numberMaxValue = '';
			row.widgetSourceType = '';
			row.widgetSource = '';
			row.dateMinValue = '';
			row.dateMaxValue = '';
			row.dynamic = false;
			row.latitude = false;
		}
		return row;
	}

	function buildSource(type,urlType) {
		if(urlType){
			urlType = "getDictByCodeOld" ;
		}else{
			urlType = "getDictByCode" ;
		}
		return new kendo.data.DataSource({//编辑器数据源
			type : "json",
			transport : {
				read : {
					//contentType : "application/json",
					url : "${pageContext.request.contextPath}/mx/form/defined/"+urlType+"?code=" + type,
					type : "POST",
					dataType : "JSON"
				},
				parameterMap : function(options, operation) {
					if (operation == "read") {
						return options;
					}
				}
			}
		});
	}
	//编辑字段数据源
	var alignmentSource = [ {
		text : '左对齐',
		value : 'left'
	}, {
		text : '居中',
		value : 'center'
	}, {
		text : '右对齐',
		value : 'right'
	} ]; //对齐数据源
	//控件数据源
	var editorSource = buildSource('widgetType');
	editorSource.fetch();//加载数据源
	var dataValidationSource = buildSource('dataValidation');
	dataValidationSource.fetch();//加载数据源
	var databaseSource = new kendo.data.DataSource({//编辑器数据源
		type : "json",
		transport : {
			read : {
				contentType : "application/json",
				url : "${pageContext.request.contextPath}/rs/isdp/global/databaseJson",
				type : "POST",
				dataType : "JSON"
			},
			parameterMap : function(options, operation) {
				if (operation == "read") {
					return JSON.stringify(options);
				}
			}
		}
	});
	databaseSource.fetch().then(function() {
		rushSelectColumns();
	});//加载数据源
	//显示格式数据源组
	var numericFormatSource = buildSource('numericFormat');
	var datetimeFormatSource = buildSource('datetimeFormat');
	var dateFormatSource = buildSource('dateFormat');
	var timeFormatSource = buildSource('timeFormat');
	var widgetSourceTypeSource = buildSource('widgetSourceType');
	var widgetSourceSource = buildSource('',true);
	
	var datasetSource = [];

	var dataIndex;
	//编辑图片链接
	function showLikeImgWin(e) {
		var fieldColumnsGrid = $('#fieldColumnsGrid').data("kendoGrid");
		var $tr = $(e).parents('tr');
		dataIndex = $tr.index();
		var dataItem = fieldColumnsGrid.dataItem($tr);
		var hidLinkImg = dataItem.hidLinkImg ? JSON.parse(dataItem.hidLinkImg) : [];
		//清空linkImgDataSource 
		linkImgDataSource.length = 0;
		//重新赋值
		$.merge(linkImgDataSource, hidLinkImg);
		var linkImgGrid = $("#linkImgGrid").data("kendoGrid");
		linkImgGrid.dataSource.read();
		$('#linkImgWin').data("kendoWindow").open();
	}
	//控件数据源
	function showWidgetWin(e) {
		var fieldColumnsGrid = $('#fieldColumnsGrid').data("kendoGrid");
		var $tr = $(e).parents('tr');
		dataIndex = $tr.index();
		var dataItem = fieldColumnsGrid.dataItem($tr);
		var data = $.extend(true,{},dataItem);
		if(data){
			data.val10 = false ;
			data.val20 = false ;
			data.val30 = false ;
			if(data.widgetSourceType == "10"){
				data.val10 = true ;
			}else if(data.widgetSourceType == "20"){
				data.widgetSource = JSON.parse(dataItem.widgetSource) ;
				rushDatasetSource(data.widgetSource.dataSetId);
				data.widgetSourceStr = dataItem.widgetSource ;
				data.val20 = true ;
			}else if(data.widgetSourceType == "30"){
				data.widgetSourceStr = dataItem.widgetSource ;
				data.val30 = true ;
			}
		}
		kendo.bind($('#widgets'),data);
		$('#widgetSourceWin').data("kendoWindow").open();
	}
	//输入参数定义
	function showParamsWin(e) {
		var fieldColumnsGrid = $('#fieldColumnsGrid').data("kendoGrid");
		var $tr = $(e).parents('tr');
		dataIndex = $tr.index();
		var dataItem = fieldColumnsGrid.dataItem($tr);
		var data = $.extend(true,{},dataItem);
		if(data){
			$("#id_Param").val("");
			$("#id_Param_hidden").val(data.params);
			var dialog = $('#dialog2').kendoWindow({
				///mx/form/defined/param/paramDefined?nameElementId=id_17742&objelementId=id_17742_hidden
				content : "${pageContext.request.contextPath}/mx/form/defined/param/paramDefined?nameElementId=id_Param&objelementId=id_Param_hidden&retFn=paramsRetFn" ,
				iframe: true,
				modal : true,
				pinned : true,
				width : /*"800px"*/ ($(document).width()-10)+"px",
				height : /*"450px"*/($(document).height()-10)+"px",
				title : "输入参数定义",
				actions : [ "Refresh","Close" ],
				visible : false,
				position : {
					top : 1,
					left : 1
				}
			}).data("kendoWindow");
			//dialog.maximize();
			$('#dialog2').data("kendoWindow").open();
		}
	};
	/**
	 * 输入输出参数返回
	 * @return {[type]} [description]
	 */
	function paramsRetFn(){
		var column = selectColumns[dataIndex];
		column.params = $("#id_Param_hidden").val();
		rushSelectColumns();
		$('#dialog2').data("kendoWindow").close();
	}
	//选择区间
	function showExtentWin(e) {
		var fieldColumnsGrid = $('#fieldColumnsGrid').data("kendoGrid");
		var $tr = $(e).parents('tr');
		dataIndex = $tr.index();
		var dataItem = fieldColumnsGrid.dataItem($tr);
		var data = $.extend(true,{},dataItem);
		if(data){
			data.isNum = false ;
			data.isDate = false ;
			if(dataItem.dataValidation == 'range' && dataItem.editor =='numerictextbox' ){
				data.isNum = true ;
			}else if(dataItem.dataValidation == 'range' && ( dataItem.editor.indexOf('datepicker') != -1 || dataItem.editor.indexOf('timepicker') != -1)){
				data.isDate = true ;
			}
		}
		kendo.bind($('#extent'),data);
		$('#extentWin').data("kendoWindow").open();
	}
	//checkbox 绑定
	function bindFn(role) {
		$('#' + role).bind('click', function() {
			var checked = this.checked;
			$.each(selectColumns, function(i, column) {
				column[role] = checked ;
			})
			rushSelectColumns();
		});
	}

	$(document).ready(function() {
		//初始化 tabStrip
		var tabStrip = $("#tabstrip").kendoTabStrip({
			select : onSelect,
			animation : {
				open : {
					effects : "fadeIn"
				}
			}
		}).data("kendoTabStrip");;
		if('${param.flag}' == '1'){//如果是树 隐藏第3个选卡
			tabStrip.enable(tabStrip.tabGroup.children().eq(2), false);
		}
		
		//初始化布局  选择数据集
		$("#dataSourceSplitter").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsible : false,
				size : GetHeight() * 0.8
			}, {
				collapsible : false,
				size : "155px"
			} ]
		});
		//初始化布局  选择字段
		$("#columnsSplitter").kendoSplitter({
			orientation : "columnsSplitter",
			panes : [ {
				collapsible : false,
				size : "49%"
			}, {
				collapsible : false,
				size : "49%"
			} ]
		});
		//数据集grid
		$("#datasourceGrid").kendoGrid({
			dataSource : {
				schema : {
					model : {
						id : "rowId",
						fields : {
							name : {
								type : 'string'
							},
							title : {
								type : 'string'
							},
							databaseId : {
								type : 'string'
							},
							field : {
								type : 'string'
							},
						}
					},
					data : "rows",
					total : "total"
				},
				transport : {
					read : {
						contentType : "application/json",
						url : '${pageContext.request.contextPath}/rs/dataset/data',
						dataType : "json",
						type : 'POST'
					},
					parameterMap : function(options) {
						return JSON.stringify(options);
					},
				},
				pageSize : 10,
				serverPaging : true,
				serverFiltering :true
			},
			filterable: true,
			height : 405,
			sortable : true,
			pageable : {
				refresh : true,
				pageSizes : true,
				buttonCount : 5
			},
			columns : [ {
				field : 'name',
				title : '名称',
				width : 250
			}, {
				field : 'title',
				title : '标题',
				width : 250
			}, {
				field : 'databaseId',
				title : '标段',
				width : 120,
				filterable: false ,
				template : function(dataItem){
					var ret = "默认" ;
					databaseSource.fetch().then(function(){
						 var data = databaseSource.data();
						 for(var i=0;i<data.length ;i++){
							 if(data[i].id == dataItem.databaseId){
								 ret = data[i].text ;
							 }
						 }
					})
					return ret ;
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 80,
				filterable: false 
			}, {
				command : [ {
					name : "选择",
					click : function(e) {
						if('${param.flag}' != '2'){
							if (selectDatasource.length > 0) {
								layer.alert('只能选择一个数据集！', 3);
								return;
							}   
						}
						var tr = $(e.target).closest("tr"), data = this.dataItem(tr), flag = true;
						$.each(selectDatasource, function(i, v) {
							if (v.dataSetId == data.dataSetId) {
								layer.alert('不能重复添加数据集！', 3);
								flag = false;
							}
						});
						if (flag) {
							selectDatasource.push(data);
							rushSelectDatasourceGrid();
						}
					}
				},{
					name : "查看",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						if(data.dynamicDataSet == "Y"){
							window.open("${pageContext.request.contextPath}/mx/form/dataset/dynamicDatabase?id="+data.datasetId);
						}else{
							window.open("${pageContext.request.contextPath}/mx/dataset/sqlbuilder?id="+data.datasetId);
						}
					}
				} ]
			} ]
		});
		//已选择的数据集
		$("#selectDatasourceGrid").kendoGrid({
			dataSource : selectDatasource,
			height : 150,
			sortable : true,
			columns : [ {
				field : 'name',
				title : '名称',
				width : 250
			}, {
				field : 'title',
				title : '标题',
				width : 250
			}, {
				field : 'databaseId',
				title : '标段',
				width : 120,
				template : function(dataItem){
					var ret = "默认" ;
					databaseSource.fetch().then(function(){
						 var data = databaseSource.data();
						 for(var i=0;i<data.length ;i++){
							 if(data[i].id == dataItem.databaseId){
								 ret = data[i].text ;
							 }
						 }
					})
					return ret ;
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 80
			}, {
				command : [ {
					name : "删除",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						for(var i=0;i<selectDatasource.length;i++){
							var value = selectDatasource[i];
							if (value.datasetId == data.datasetId) {
								selectDatasource.splice(i, 1);
							}
						}
						rushSelectDatasourceGrid();
					}
				}/* ,{
					name : "查看",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						if(data.dynamicDataSet == "Y"){
							window.open("${pageContext.request.contextPath}/mx/form/dataset/dynamicDatabase?id="+data.datasetId);
						}else{
							window.open("${pageContext.request.contextPath}/mx/dataset/sqlbuilder?id="+data.datasetId);
						}
					}
				} */ ]
			} ]
		});

		//数据集下拉列表
		$("#datasourceDropdownlist").kendoDropDownList({
			dataTextField : "title",
			dataValueField : "datasetId",
			dataSource : selectDatasource,
			index : 0,
			change : function(e) {
				var dataItem = this.dataItem(e.item);
				//刷新字段grid
				var columnsGrid = $("#columnsGrid").data("kendoGrid");
				//columnsGrid.dataSource.transport.options.read.data.datasetId = dataItem.datasetId;
				columnsGrid.dataSource.read();
			}
		});
		
		//字段列表
		$("#columnsGrid").kendoGrid({
			/* dataSource : {
				schema : {
					model : {
						id : "id",
						fields : {
							title : {
								type : 'string'
							}
						}
					}
				},
				transport : {
					read : {
						//contentType : "application/json",
						url : '${pageContext.request.contextPath}/rs/dataset/getSelectJson',
						dataType : "json",
						type : 'POST',
						data : {
							datasetId : ''
						}
					},
					parameterMap : function(options) {
						return options;
					},
				}
			}, */
			dataSource : columnsGridDataSource,
			height : GetHeight() * 0.96,
			sortable : true,
			selectable : "multiple",
			columns : [ {
				field : 'title',
				title : '字段',
				template : function(dataItem) {
					var temp = "" ;
					try{
						temp = JSON.parse(dataItem.expression).tableNameCN + "." + dataItem.title ;
						//temp = dataItem.title;
					}catch(e){
						temp = JSON.parse(dataItem.exp).tableNameCN + "." + dataItem.title ;
					}
					return temp;
				},
				width : 250
			}, {
				command : [ {
					name : "选择",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						var flag = true;
						$.each(selectColumns, function(i, v) {
							if (data.columnLabel == v.columnLabel) {
								layer.alert('不能重复选择！', 3);
								flag = false;
							}
						});
						if (flag) {
							selectColumns.push(transformRow(data));
							rushSelectColumns();
						}
					}
				} ]
			} ]
		});
		//已选择字段列表
		$("#selectColumnsGrid").kendoGrid({
			dataSource : selectColumns,
			height : GetHeight() * 0.96,
			sortable : true,
			selectable : "multiple",
			columns : [ {
				field : 'title',
				title : '字段',
				template : function(dataItem) {
					return dataItem.tableNameCn + "." + dataItem.title;
				},
				width : 250
			}, {
				command : [ {
					name : "删除",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						for(var i=0;i<selectColumns.length;i++){
							var value = selectColumns[i];
							if (value.columnLabel == data.columnLabel) {
								selectColumns.splice(i, 1);
							}
						}
						//刷新
						rushSelectColumns();
					}
				} ]
			} ]
		});
		//选择字段批量添加
		$("#batch_add").kendoButton({
			icon : "k-icon k-i-tick",
			click : function(e) {
				var columnsGrid = $('#columnsGrid').data("kendoGrid");
				var selects = columnsGrid.select();
				var data, errorStr = "", datas = [], value;
				for (var i = 0; i < selects.length; i++) {
					data = columnsGrid.dataItem(selects[i]);
					for (var index = 0; index < selectColumns.length; index++) {
						value = selectColumns[index];
						if (value.columnLabel == data.columnLabel) {
							errorStr += " " + data.title + " ";
						}
					}
					datas.push(transformRow(data));
				}
				if (errorStr) {
					layer.alert('已选择字段[' + errorStr + ']不能重复添加！', 3);
					return;
				} else {
					$.merge(selectColumns, datas);
					rushSelectColumns();
				}
			}
		});
		//选择字段批量删除
		$("#batch_del").kendoButton({
			icon : "k-icon k-i-cancel",
			click : function(e) {
				var selectColumnsGrid = $('#selectColumnsGrid').data("kendoGrid");
				var selects = selectColumnsGrid.select();
				var data, value;
				for (var i = 0; i < selects.length; i++) {
					data = selectColumnsGrid.dataItem(selects[i]);
					for (var index = (selectColumns.length-1); index >=0; index--) {
						value = selectColumns[index];
						if (value.columnLabel == data.columnLabel) {
							selectColumns.splice(index, 1);
						}
					}
				}
				rushSelectColumns();
			}
		});

		//选择字段右对齐
		$("#rightAlign_button").kendoButton({
			icon : "k-icon k-i-align-left",
			click : function(e) {
				$.each(selectColumns,function(i,item){
					item.alignment = "right";
				});
				rushFieldColumns();
			}
		});

		//选择字段居中对齐
		$("#centerAlign_button").kendoButton({
			icon : "k-icon k-i-align-center",
			click : function(e) {
				$.each(selectColumns,function(i,item){
					item.alignment = "center";
				});
				rushFieldColumns();
			}
		});

		//选择字段左对齐
		$("#leftAlign_button").kendoButton({
			icon : "k-icon k-i-align-right",
			click : function(e) {
				$.each(selectColumns,function(i,item){
					item.alignment = "left";
				});
				rushFieldColumns();
			}
		});

		//编辑字段
		$("#fieldColumnsGrid").kendoGrid({
			dataSource : {
				autoSync : true ,
				data : selectColumns,
				schema : {
					model : {
						id : "id",
						fields : {
							title : {
								'editable' : false
							},
							isShowList : {
								type : 'boolean',
								'editable' : true
							},
							isFilterable : {
								type : 'boolean',
								'editable' : true
							},
							isSortable : {
								type : 'boolean',
								'editable' : true
							},
							isMenu : {
								type : 'boolean',
								'editable' : true
							},
							columnWidth : {
								type : 'string',
								'editable' : true
							},
							alignment : {
								type : 'string',
								'editable' : true
							},
							editor : {
								type : 'string',
								'editable' : true
							},
							formatValue : {
								type : 'string',
								'editable' : true
							},
							isRequired : {
								type : 'boolean',
								'editable' : true
							},
							defaultVlue : {
								type : 'string',
								'editable' : true
							},
							dataValidation : {
								type : 'string',
								'editable' : true
							},
							hidLinkImg : {
								type : 'string',
								'editable' : true
							},
							isValidate : {
								type : 'boolean',
								'editable' : true
							},
							isEditor : {
								type : 'boolean',
								'editable' : true
							},
							validateMsg : {
								type : 'string',
								'editable' : true
							},
							numberMinValue : {
								type : 'string',
								'editable' : true
							},
							numberMaxValue : {
								type : 'string',
								'editable' : true
							},
							widgetSourceType : {
								type : 'string',
								'editable' : true
							},
							widgetSource : {
								type : 'string',
								'editable' : true
							},
							dateMinValue : {
								type : 'string',
								'editable' : true
							},
							dateMaxValue : {
								type : 'string',
								'editable' : true
							},
							dynamic : {
								type : 'boolean',
								'editable' : true
							},
							latitude : {
								type : 'boolean',
								'editable' : true
							},
						}
					}
				}
			},
			height : GetHeight() * 0.96,
			scrollable : true,
			editable : true,
			resizable : true,
			selectable : "multiple,row",
			//toolbar : kendo.template($("#template").html(),selectColumns) ,
			columns : [ {
				field : 'title',
				title : '字段',
				//locked: true,
				template : function(dataItem) {
					return "<div class='k-k-div'>"+dataItem.tableNameCn + "." + dataItem.title + "</div>";
				},
				width : 200,
				locked : true,
			}, {
				title : '显示',
				field : 'isShowList',
				headerTemplate : "<input type='checkbox' id='isShowList' /><label for='isShowList'>显示</label>",
				template : function(dataItem){
					return dataItem.isShowList?"是":"<p style='color:red'>否</p>";
				},
				width : 60,
			}, {
				title : '过滤',
				field : 'isFilterable',
				headerTemplate : "<input type='checkbox' id='isFilterable' /><label for='isFilterable'>过滤</label>",
				template : function(dataItem){
					return dataItem.isFilterable?"是":"<p style='color:red'>否</p>";
				},
				width : 60
			}, {
				title : '排序',
				field : 'isSortable',
				headerTemplate : "<input type='checkbox' id='isSortable' /><label for='isSortable'>排序</label>",
				template : function(dataItem){
					return dataItem.isSortable?"是":"<p style='color:red'>否</p>";
				},
				width : 60
			}, {
				title : '可隐藏',
				field : 'isMenu',
				headerTemplate : "<input type='checkbox' id='isMenu' /><label for='isMenu'>可隐藏</label>",
				template : function(dataItem){
					return dataItem.isMenu?"是":"<p style='color:red'>否</p>";
				},
				width : 80
			}, {
				title : '列宽',
				field : 'columnWidth',
				width : 100
			}, {
				title : '对齐方式',
				field : 'alignment',
				values : alignmentSource,
				width : 100
			}, {
				title : '样式定义',
				template : function(dataItem) {
					return "<button class='k-button "+(dataItem.hidLinkImg?"hasMany":"")+"' data-role='button' onclick='showLikeImgWin(this)'>样式定义</button>";
				},
				width : 100
			}, {
				title : '控件类型',
				field : 'editor',
				template : function(dataItem){
					var ret = "" ;
					editorSource.fetch().then(function(){
						 var data = editorSource.data();
						 for(var i=0;i<data.length ;i++){
							 if(data[i].code == dataItem.editor){
								 ret = data[i].name ;
							 }
						 }
					})
					return ret ;
				},
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 var _index = $tr.index() ;
					 $("<input/>").attr('name',options.field).appendTo(container).
					 kendoDropDownList({dataValueField:'code',dataTextField:'name',dataSource:editorSource,change:function(){
						 selectColumns[_index].formatValue= "" ;
						 $("#fieldColumnsGrid").data("kendoGrid").dataSource.read();
					 }});
				},
				width : 180
			}, {
				title : '显示格式',
				field : 'formatValue',
				template : function(dataItem){
					var datarole = dataItem.editor || '' ;
					var source ;
					if(datarole.indexOf("numeric")!=-1){
			   			source = numericFormatSource;
			    	} else if(datarole.indexOf("datetime")!=-1){
			    		source = datetimeFormatSource;
			    	} else if(datarole.indexOf("calendar")!=-1 || datarole.indexOf("date")!=-1){
			    		source = dateFormatSource;
			    	} else if(datarole.indexOf("time")!=-1){
			    		source = timeFormatSource;
			    	} 
					var ret = "" ;
					if(source){
						source.fetch().then(function(){
							 var data = source.data();
							 for(var i=0;i<data.length ;i++){
								 if(data[i].code == dataItem.formatValue){
									 ret = data[i].name ;
								 }
							 }
						})
					}
					return ret ;
				},
				editor : function(container,options){
					var datarole = options.model.editor || '' ;
					var source ;
					if(datarole.indexOf("numeric")!=-1){
			   			source = numericFormatSource;
			    	} else if(datarole.indexOf("datetime")!=-1){
			    		source = datetimeFormatSource;
			    	} else if(datarole.indexOf("calendar")!=-1 || datarole.indexOf("date")!=-1){
			    		source = dateFormatSource;
			    	} else if(datarole.indexOf("time")!=-1){
			    		source = timeFormatSource;
			    	} else{
			    		source = [] ;
			    	}
					 $("<input/>").attr('name',options.field).appendTo(container).
					 kendoDropDownList({dataValueField:'code',dataTextField:'name',dataSource:source});
				},
				width : 180
			}, {
				title : '控件数据源',
				template : function(dataItem){
					return "<button class='k-button "+(dataItem.widgetSourceType?"hasMany":"")+"' data-role='button' onclick='showWidgetWin(this)'>控件数据源</button>";
				},
				width : 100
			}, {
				title : '输入形参',
				template : function(dataItem){
					return "<button class='k-button "+(dataItem.params?"hasMany":"")+"' data-role='button' onclick='showParamsWin(this)'>输入形参</button>";
				},
				width : 100
			}, {
				title : '可编辑',
				headerTemplate : "<input type='checkbox' id='isEditor' /><label for='isEditor'>可编辑</label>",
				field : 'isEditor',
				template : function(dataItem){
					return dataItem.isEditor?"是":"<p style='color:red'>否</p>";
				},
				width : 100
			}, {
				title : '必填',
				headerTemplate : "<input type='checkbox' id='isRequired' /><label for='isRequired'>必填</label>",
				field : 'isRequired',
				template : function(dataItem){
					return dataItem.isRequired?"是":"<p style='color:red'>否</p>";
				},
				width : 100
			}, {
				title : '默认值',
				field : 'defaultVlue',
				width : 100
			}, {
				title : '验证',
				headerTemplate : "<input type='checkbox' id='isValidate' /><label for='isValidate'>验证</label>",
				field : 'isValidate',
				template : function(dataItem){
					return dataItem.isValidate?"是":"<p style='color:red'>否</p>";
				},
				width : 100
			}, {
				title : '验证类型',
				field : 'dataValidation',
				template : function(dataItem){
					var ret = "" ;
					editorSource.fetch().then(function(){
						 var data = dataValidationSource.data();
						 for(var i=0;i<data.length ;i++){
							 if(data[i].code == dataItem.dataValidation){
								 ret = data[i].name ;
							 }
						 }
					})
					return ret ;
				},
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 var _index = $tr.index() ;
					 $("<input/>").attr('name',options.field).appendTo(container).
					 kendoDropDownList({dataValueField:'code',dataTextField:'name',dataSource:dataValidationSource,change:function(){
						 //selectColumns[_index].formatValue= "" ;
						 $("#fieldColumnsGrid").data("kendoGrid").dataSource.read();
					 }});
				},
				width : 100
			},{
				title : '区间选择',
				template : function(dataItem){
					var editor = dataItem.editor||"";
					var	disabled = "disabled='true'" ;
					if(dataItem.dataValidation == 'range' && (editor =='numerictextbox' || editor.indexOf('datepicker') != -1 
							|| editor.indexOf('timepicker') != -1) ){
						disabled = "" ;
					}
					return "<button class='k-button "+(dataItem.dataValidation?"hasMany":"")+"' data-role='button' "+disabled+" onclick='showExtentWin(this)'>区间选择</button>";
				},
				width : 100
			}, {
				title : '验证提示',
				field : 'validateMsg',
				width : 100
			}, {
				title : '动态列',
				field : 'dynamic',
				template : function(dataItem){
					return dataItem.dynamic?"是":"<p style='color:red'>否</p>";
				},
				width : 100
			}, {
				title : '纬度列',
				field : 'latitude',
				template : function(dataItem){
					return dataItem.latitude?"是":"<p style='color:red'>否</p>";
				},
				width : 100
			}
			]
		});
		kendo.bind($('#batch'),{});
		//初始化 图片链接窗口
		$('#linkImgWin').kendoWindow({
			modal : true,
			pinned : true,
			width : "630px",
			height : "320px",
			title : "样式定义",
			actions : [ "Close" ],
			visible : false,
			position : {
				top : 100,
				left : 100
			}
		});
		//初始化图片链接grid
		$("#linkImgGrid").kendoGrid({
			dataSource : linkImgDataSource,
			height : 300,
			sortable : true,
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'expression',
				title : '表达式定义',
				editor : expEditor
			}, {
				field : 'htmlExpression',
				title : 'HTML样式定义',
				editor : htmlExpEditor
			}, {
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				for (var i = 0; i < values.length; i++) {
					if (values[i].expression == undefined || values[i].expression == "" || values[i].htmlExpression == undefined || values[i].htmlExpression == "") {
						layer.alert("必须填写值", 3);
						return;
					}
				}
				var column = selectColumns[dataIndex];
				column.hidLinkImg = JSON.stringify(values);
				$('#linkImgWin').data("kendoWindow").close();
				rushSelectColumns();
				e.preventDefault();
			},
			edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});

		//初始化控件数据源
		$('#widgetSourceWin').kendoWindow({
			modal : true,
			pinned : true,
			width : "630px",
			height : "345px",
			title : "控件数据源",
			actions : [ "Close" ],
			visible : false,
			position : {
				top : 100,
				left : 100
			}
		});
		//绑定checkbox事件
		bindFn('isShowList');
		bindFn('isFilterable');
		bindFn('isSortable');
		bindFn('isMenu');
		bindFn('isEditor');
		bindFn('isRequired');
		bindFn('isValidate');
		
		//控件数据源类型
		$("#widgetSourceTypeDropdownlist").kendoDropDownList({
			dataTextField : "name",
			dataValueField : "code",
			dataSource : widgetSourceTypeSource,
			index : 0,
			change : function(e) {
				var value = this.value();
				var data = {} ;
				data.val10 = false ;
				data.val20 = false ;
				data.val30 = false ;
				data.widgetSourceType = value ;
				if(value == "10"){
					data.val10 = true ;
					data.widgetSource = "" ;
				}else if(value == "20"){
					data.val20 = true ;
					data.widgetSource = {} ;
				}else if(value == "30"){
					data.val30 = true ;
					data.widgetSource = "" ;
				}
				//清楚数据元 
				var textDrop = $("#textDropdownlist").data("kendoDropDownList");
				var valueDrop = $("#valueDropdownlist").data("kendoDropDownList");
				textDrop.setDataSource([]);
				valueDrop.setDataSource([]);
				kendo.bind($('#widgets'),data);
			}
		});
		//控件数据源 -- 字典数据
		$("#widgetSourceDropdownlist").kendoDropDownList({
			dataTextField : "name",
			dataValueField : "code",
			dataSource : widgetSourceSource,
			index : 0
		});
		//选择数据集 
		$("#selectDataset").on("click",function(){
			var dialog = $('#dialog').kendoWindow({
				content : "${pageContext.request.contextPath}/mx/form/defined/table/select_dataset_kendo?retFn=selectDatasetRetFn" ,
				iframe: true,
				modal : true,
				pinned : true,
				width : "800px",
				height : "450px",
				title : "数据集",
				actions : [ "Refresh","Close" ],
				visible : false,
				position : {
					top : 100,
					left : 100
				}
			});
			$('#dialog').data("kendoWindow").open();
		}); 
		//控件数据源 -- 字典数据
		$("#textDropdownlist").kendoDropDownList({
			dataTextField : "title",
			dataValueField : "columnLabel",
			dataSource : datasetSource,
			index : 0
		});
		//控件数据源 -- 字典数据
		$("#valueDropdownlist").kendoDropDownList({
			dataTextField : "title",
			dataValueField : "columnLabel",
			dataSource : datasetSource,
			index : 0
		});
		//用户自定义编辑数据
		$("#widgetButton").kendoButton({
			click : function(e) {
				var source = $('#widgetInput').val() ;
				customSource.length = 0 ;
				if(source != ""){
					$.merge(customSource,JSON.parse(source)) ;
				}
				$('#customSourceGrid').data("kendoGrid").dataSource.read();
				$('#customSourceWin').data("kendoWindow").open();
			}
		});
		//用户自定义编辑数据窗口
		$('#customSourceWin').kendoWindow({
			modal : true,
			pinned : true,
			width : "630px",
			height : "320px",
			title : "编辑链接",
			actions : [ "Close" ],
			visible : false,
			position : {
				top : 100,
				left : 100
			}
		});
		//自定义控件数据源
		$("#customSourceGrid").kendoGrid({
			dataSource : customSource,
			height : 300,
			sortable : true,
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'text',
				title : '显示值',
			}, {
				field : 'value',
				title : '实际值',
			}, {
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				for (var i = 0; i < values.length; i++) {
					if (values[i].text == undefined || values[i].text == "" || values[i].value == undefined || values[i].value == "") {
						layer.alert("必须填写值", 3);
						return;
					}
				}
				$("#widgetInput").val(JSON.stringify(values));
				$('#customSourceWin').data("kendoWindow").close();
				//rushSelectColumns();
				e.preventDefault();
			},
			edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});
		//控件数据源工具条 widgetToolbar
		$("#widgetToolbar").kendoToolBar({
			resizable : true,
			items:[
			   { type: "button", text: "保存", icon: "k-icon k-i-tick" ,click:function(e){
				   //保存控件数据源信息
				   var widgetSourceType = $('#widgetSourceTypeDropdownlist').val();
				   var widgetSource = "" ;
				   if(widgetSourceType == ""){
					   var column = selectColumns[dataIndex];
					   column.widgetSourceType = widgetSourceType ;
					   column.widgetSource = widgetSource ;
					   rushSelectColumns();
					   $('#widgetSourceWin').data("kendoWindow").close();
					   return ;
				   }else if(widgetSourceType=="10"){
					   widgetSource =  $('#widgetSourceDropdownlist').val();
				   }else if(widgetSourceType == "20"){
					   var widgetSourceTableText = $('#textDropdownlist').val() ;
					   var widgetSourceTableValue = $('#valueDropdownlist').val() ;
					   if(widgetSourceTableText=="" || widgetSourceTableValue == ""){
						   layer.alert("请检查数据填写完整", 3);
						   return ;
					   }
					   widgetSource =  $('#widgetInput').val();
					   if(widgetSource!=""){
						   widgetSource =  JSON.parse(widgetSource);
						   widgetSource.widgetSourceTableText = widgetSourceTableText ;
						   widgetSource.widgetSourceTableValue = widgetSourceTableValue ;
					   }
					   widgetSource = JSON.stringify(widgetSource);
				   }else if(widgetSourceType == "30"){
					   widgetSource = $("#widgetInput").val();
				   }
				   if(widgetSource == ""){
					   layer.alert("请检查数据填写完整", 3);
					   return ;
				   }
				   var column = selectColumns[dataIndex];
				   column.widgetSourceType = widgetSourceType ;
				   column.widgetSource = widgetSource ;
				   var paramsInputStr = $('#paramsInput').val(),
				   	   paramsAry = paramsInputStr?JSON.parse(paramsInputStr):[];
				   if(column.params){
				   	   //$.merge(paramsAry,JSON.parse(column.params));
				   	   paramsAry = mergeObjParams(JSON.parse(column.params),paramsAry);
				   }
				   column.params = paramsAry.length?JSON.stringify(paramsAry):"";
				   
				   rushSelectColumns();
				   //关闭控件数据源窗口
				   $('#widgetSourceWin').data("kendoWindow").close();
			   }},
			]
		});
		
		
		//初始化 选择区间
		$('#extentWin').kendoWindow({
			modal : true,
			pinned : true,
			width : "630px",
			height : "345px",
			title : "选择区间",
			actions : [ "Close" ],
			visible : false,
			position : {
				top : 100,
				left : 100
			}
		});
		//选择区间保存
		$("#extentToolbar").kendoToolBar({
			resizable : true,
			items:[
			   { type: "button", text: "保存", icon: "k-icon k-i-tick" ,click:function(e){
				   //保存区间信息 
				   var numberMaxValue = $("#numberMaxValue").val();
				   var numberMinValue = $("#numberMinValue").val();
				   var dateMinValue = $("#dateMinValue").val();
				   var dateMaxValue = $("#dateMaxValue").val();
				   
				   var column = selectColumns[dataIndex];
				   column.numberMaxValue = numberMaxValue ;
				   column.numberMinValue = numberMinValue ;
				   column.dateMinValue = dateMinValue ;
				   column.dateMaxValue = dateMaxValue ;
				   rushSelectColumns();
				   //关闭控件数据源窗口
				   $('#extentWin').data("kendoWindow").close();
			   }},
			]
		});
		//保存
		$("#commitToolbar").kendoToolBar({
			resizable : true,
			items:[/* {
				   type: "button", text: "新增数据集", icon: "k-icon k-i-add" ,click:function(e){
					   window.open("${pageContext.request.contextPath}/mx/dataset?pageId=${param.pageId}");
				   }
			   }, */{ 
				    type: "button", text: "保存", icon: "k-icon k-i-tick" ,click:function(e){
					var results = [] ;
					var tablenames = [];
					var result = {} ,
						tableDataSetId = "";
					$.each(selectDatasource ,function(i,table){
						if(table.name && table.name!="null" && table.name!=""){
							tablenames.push(table.name||table.title);
						}
						tableDataSetId = table.datasetId ;
						if(i==0){
							result.databaseId = table.databaseId ;
							result.datasetId = table.datasetId ;
							result.name = table.name ;
							result.title = table.title ;
							result.createTime = table.createTime ;
							result.columns = selectColumns;	//保存所有字段信息 
							result.datasource = selectDatasource ;
							result.tables = [] ; //保存表名
							results.push(result);
						}
							$.each(selectColumns,function(index,field){
								if(table.datasetId===field.datasetId){
									if(result.tables.indexOf(field.tableName) == -1){
										if(field.tableName && field.tableName!="null" && field.tableName!=""){
											result.tables.push(field.tableName);
										}
									}
								}
							});
					});
					//dynamicDatabase
					if('${param.results}' == 'dynaDatabase'){
						var $dynaDatabase = parent.$("body").data("dynaDatabase");
						$dynaDatabase.val(tablenames.join(',')).data("dynamicId",tableDataSetId);
						parent.$ioWindow.rushDyn.call(parent.$ioWindow);
						parent.dynaDatabase && parent.dynaDatabase.close();
					}else{
						if('${param.resultsElementId}' != ''){
						parent.document.getElementById('${param.resultsElementId}').value = JSON.stringify(results);
						}
						if('${param.tablenameElementId}' != ''){
							parent.document.getElementById('${param.tablenameElementId}').value = tablenames.join(',');
						}
						parent.layer.close(parent.layer.getFrameIndex());
					}
			   	  }
			   },{
				   type: "button", text: "取消", icon: "k-icon k-i-del" ,click:function(e){
					   parent.layer.close(parent.layer.getFrameIndex());
				   }
			   }
			]
		});
		
		rushSelectDatasourceGrid();
	});
	
	//选择数据集回调方法
	function selectDatasetRetFn(name,rowStr,params){
		$('#selectDataset').val(name);
		$('#widgetInput').val(rowStr);
		$('#paramsInput').val(params);
		rushDatasetSource(JSON.parse(rowStr).dataSetId);
		
		$('#dialog').data("kendoWindow").close();
	}
	function rushDatasetSource(datasetId){
		//重新加载数据源
		var textDrop = $("#textDropdownlist").data("kendoDropDownList");
		var valueDrop = $("#valueDropdownlist").data("kendoDropDownList");
		datasetSource = new kendo.data.DataSource({//数据集下拉框
			type : "json",
			transport : {
				read : {
					url : "${pageContext.request.contextPath}/rs/dataset/getSelectJson",
					type : "POST",
					dataType : "JSON",
					data : {
						datasetId :  datasetId
					}
				},
				parameterMap : function(options, operation) {
					if (operation == "read") {
						return options;
					}
				}
			}
		});
		textDrop.setDataSource(datasetSource);
		valueDrop.setDataSource(datasetSource);
	}
	//返回表达式执行函数
	function retExpression(data) {
		var grid = $("#linkImgGrid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		dataItem.expression = data.expActVal;
		dataItem.expdata = JSON.stringify(data);
		grid.editCell();
	}
	//获取参数
	function getExpression() {
		var expressionData = [];
		for (var i = 0; i < selectColumns.length; i++) {
			var express = {};
			var selectFieldRow = selectColumns[i];
			express.t = selectFieldRow.title;
			express.dType = getfieldTypeValue(selectFieldRow.FieldType);
			express.code = selectFieldRow.code;
			express.value = selectFieldRow.value;
			express.name = selectFieldRow.title;
			expressionData.push(express);
		}
		return expressionData;
	}
	//初始化参数
	function initExpressionFn() {
		var grid = $("#linkImgGrid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.expdata);
	}
	//表达式定义
	function expEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		var _href = "${pageContext.request.contextPath}/mx/expression/defined/view?category=front&retFn=retExpression" + "&getFn=getExpression&initExpFn=initExpressionFn";
		$(input).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}

	// html编辑器获取变量方法
	function getVarFn() {
		//获取字典类型FieldTypeControl
		var expressionData = [];
		for (var i = 0; i < selectColumns.length; i++) {
			var express = {};
			var selectFieldRow = selectColumns[i];
			express.t = selectFieldRow.title;
			express.dType = getfieldTypeValue(selectFieldRow.FieldType);
			express.code = selectFieldRow.code != null ? selectFieldRow.code.replace("~F{", "~V{") : "";
			express.value = selectFieldRow.value != null ? selectFieldRow.value.replace("~F{", "~V{") : "";
			express.name = selectFieldRow.title;
			expressionData.push(express);
		}
		return expressionData;
	}
	// html编辑器获取参数方法
	function getParamFn() {
		return [];
	}
	// html编辑器回调函数
	function retHtmlFn(data) {
		if (data) {
			var grid = $("#linkImgGrid").data("kendoGrid");
			var dataItem = grid.dataItem(grid.select());
			dataItem.htmlExpression = data.htmlVal;
			dataItem.htmldata = JSON.stringify(data);
			grid.editCell();
		}
	}
	// html编辑器回显内容 
	function initHTMLFn() {
		var grid = $("#linkImgGrid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.htmldata);
	}
	//HTML样式定义定义
	function htmlExpEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		var _href = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn&"
				+ "getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
		$(input).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}
	

	
</script>
</head>
<body>
	<input type="hidden" id="id_Param">
	<input type="hidden" id="id_Param_hidden">
	<div id="customSourceWin">
		<div id="customSourceGrid"></div>
	</div>
	<div id="linkImgWin">
		<div id="linkImgGrid"></div>
	</div>
	<div id="widgetSourceWin">
		<input id="paramsInput" type="hidden" />
		<div id="widgetToolbar" style="height: 23px"></div>
		<div id="widgets" >
			<div class="wdt">
				<table>
					<tr>
						<td class="wdtw">
							控件数据源类型  ：
						</td>
						<td>
							<div id="widgetSourceTypeDropdownlist" data-bind="value:widgetSourceType"></div>
						</td>
					</tr>
				</table>
			</div>
			<div data-bind="visible:val10" class="wdt">
				<table>
					<tr>
						<td class="wdtw">
							控件数据源 ：
						</td>
						<td>
							<div id="widgetSourceDropdownlist" data-bind="value:widgetSource" ></div>
						</td>
					</tr>
				</table>
			</div>
			<div data-bind="visible:val20">
				<div class="wdt">
					<table>
						<tr>
							<td class="wdtw">
								选择数据集  ：
							</td>
							<td>
								<input class="k-textbox" id="selectDataset" data-bind="value:widgetSource.name" />
							</td>
						</tr>
					</table>
				</div>
				<div class="wdt">
					<table>
						<tr>
							<td class="wdtw">
								显示值 ：
							</td>
							<td>
								<div id="textDropdownlist" data-bind="value:widgetSource.widgetSourceTableText"></div>
							</td>
						</tr>
					</table>
				</div>
				<div class="wdt">
					<table>
						<tr>
							<td class="wdtw">
								实际值 ：
							</td>
							<td>
								<div id="valueDropdownlist" data-bind="value:widgetSource.widgetSourceTableValue"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div data-bind="visible:val30" class="wdt">
				<table>
					<tr>
						<td class="wdtw">
							自定义数据 ：
						</td>
						<td>
							<button id="widgetButton" >编辑数据</button>
						</td>
					</tr>
				</table>
				<input id="widgetInput" type="hidden" data-bind="value:widgetSourceStr" />
			</div>
		</div>
	</div>
	<div id="extentWin">
		<div id="extentToolbar" style="height: 23px"></div>
		<div id="extent" >
			<div data-bind="visible:isNum" class="wdt">
				<div class="wdt">
					<table>
						<tr>
							<td class="wdtw">
								最大值 ：
							</td>
							<td>
								<input id="numberMaxValue" data-role="numerictextbox" data-bind="value:numberMaxValue" />
							</td>
						</tr>
					</table>
				</div>
				<div class="wdt">
					<table>
						<tr>
							<td class="wdtw">
								最小值 ：
							</td>
							<td>
								<input id="numberMinValue" data-role="numerictextbox" data-bind="value:numberMinValue" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div data-bind="visible:isDate">
				<div class="wdt">
					<table>
						<tr>
							<td class="wdtw">
								最大值 ：
							</td>
							<td>
								<input id="dateMaxValue" data-role="datetimepicker" data-bind="value:dateMaxValue" />
							</td>
						</tr>
					</table>
				</div>
				<div class="wdt">
					<table>
						<tr>
							<td class="wdtw">
								最小值 ：
							</td>
							<td>
								<input id="dateMinValue" data-role="datetimepicker" data-bind="value:dateMinValue" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="tabstrip">
		<ul>
			<li class="k-state-active">选择数据集</li>
			<li>选择字段</li>
			<li>编辑字段</li>
		</ul>
		<div>
			<div id="dataSourceSplitter" class="mt-kuangkuang" style="height: 770px;">
				<div>
					<div id="datasourceGrid" style="display:none"></div>
					<iframe src="${contextPath }/mx/form/defined/ex/dataSourceService?fn=ret_dataset" class="mt-iframe" style="width:100%; height:600px" frameborder="0"></iframe>
				</div>
				<div>
					<div id="selectDatasourceGrid"></div>
				</div>
			</div>
		</div>
		<div>
			<div id="columnsSplitter" class="mt-kuangkuang" style="height: 770px;">
				<div>
					<div id="datasourceDropdownlist"></div>
					<button id="batch_add">批量添加</button>
					<div id="columnsGrid"></div>
				</div>
				<div>
					<button id="batch_del">批量删除</button>
					<div id="selectColumnsGrid"></div>
				</div>
			</div>
		</div>
		<div>
			<div id="columnsSplitter" class="mt-kuangkuang" style="width: 100%; height: 770px;">
				<button id="leftAlign_button">批量左对齐</button>
				<button id="centerAlign_button">批量居中对齐</button>
				<button id="rightAlign_button">批量右对齐</button>
				<div id="fieldColumnsGrid"></div>
			</div>
		</div>
	</div>
	<div id="commitToolbar" style="height: 23px;text-align: right;"></div>
	<div id="dialog"></div>
	<div id="dialog2"></div>
</body>
<script type="text/javascript">

$(".mt-kuangkuang").css({
	height : GetHeight()
});

$(".mt-iframe").css({
	height : GetHeight() - 170
});


Array.prototype.empty = function(){
	if(this.length){
		this.splice(0, this.length);
	}
	return this;
};

	function ret_dataset(data){
		
		if(!data.content){
			//alert("服务内容错误!");	
			return false;
		}
		
		var content = data.content;
		
		delete data.content;
		
		selectDatasource.empty().push(data);
		
		columnsGridDataSource.empty();
		
		$.each(content.outPutParams, function(i, v){
			columnsGridDataSource.push({
				title : v.outPutParams_title,
				columnLabel : v.outPutParams_code,
				columnName : v.outPutParams_code,
				expression : JSON.stringify({
					FieldLength : 0,
					FieldType : 'string',
					tableNameCN : data.name,
					value: "~F{默认."+ data.name +"."+ v.outPutParams_title +"}",
					code: "~F{default.row."+ v.outPutParams_code +"}"
				})
			});
		});
		
		data.columnsGridDataSource = columnsGridDataSource;
		data.params = content.inputParams;
		
		rushSelectDatasourceGrid();
	}
	
	var CommonCallBack = (function(){
		return function(data, targetId, fn){
			if(!data){
				return false;
			}
			var target = targetId ? document.getElementById(//
					targetId) : null;
			if(fn && window[fn]){
				window[fn].call(target, data);
			} else if(target){
				target = $(target);
				var $name = $('#id_' + target.attr('itemid')),
				$this = $('#id_' + target.attr('itemid') + "_hidden");
				$name.val(data.name);
				$this.val(JSON.stringify([data]));
			}
			//closeLayer();
		};
	})();
</script>
</html>