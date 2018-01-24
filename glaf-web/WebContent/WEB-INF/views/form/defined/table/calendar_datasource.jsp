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
	Array.prototype.indexOf = function(val) {     
		for (var i = 0; i < this.length; i++) {           
			if (this[i] == val) return i;           
		}            
		return -1;        
	};        
	Array.prototype.remove = function(val) {            
		var index = this.indexOf(val);            
		if (index > -1) {                
			this.splice(index, 1);            
		}        
	};

	var fieldTypeControl = [];
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
					$.merge(selectDatasource, datas[0].selectDatasource);
					$.merge(selectColumns, datas[0].selectColumns||[]);
					$.merge(relation, datas[0].relation);
				}
			}
		}
	}
	//已选择的数据集
	var selectDatasource = [];
	var selectColumns = [] ;
	//关系定义 
	var relation = [];
	var customSource = [] ;
	init();
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
		rushSelectDatasourceGrid();
	});//加载数据源
	//
	function getSource(datasetId){
		return new kendo.data.DataSource({//已选择数据集的列字段
					type : "json",
					transport : {
						read : {
							//contentType : "application/json",
							url : '${pageContext.request.contextPath}/rs/dataset/getSelectJson',
							dataType : "json",
							type : 'POST',
							data : {
								datasetId : datasetId
							}
						},
						parameterMap : function(options) {
							return options;
						},
					}
				});
	}
	
	//刷新 已选择数据集
	function rushSelectDatasourceGrid() {
		//刷新选择的数据集
		var grid = $("#selectDatasourceGrid").data("kendoGrid");
		grid.dataSource.read();
		//刷新编辑字段
		rushSelectColumns();
	}
	//刷新编辑字段
	function rushSelectColumns() {
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
	
	
	//删除选中数据集，并删除字段
	function removeSelectColumns(data){
		var length = selectColumns.length ;
		for(var i= (length-1);i>=0 ; i--){
			var c = selectColumns[i];
			if(c && (c.seq == data.id)){
				selectColumns.splice(i, 1);
			} 
		}
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
			row.formatValue = '';
			row.editor = 'maskedtextbox';
			row.columnLabel = rowData.columnLabel;
			row.title = rowData.title;
			//{"value":"~F{默认.项目文件收集分类.顺序号}","code":"~F{default.cell_useradd7028.cell_useradd7028_user3}"}
			var exp ={};
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
			//
			row.ctype = "" ; //系列or名称or值  类型
			
			rows.push(row);
		}
		return rows;
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
			data.defaultIndex = dataItem.defaultIndex;
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
	//图标类型数据源
	var chartsSource = buildSource('charts');
	chartsSource.fetch();
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
		if('${param.isTree}' == 'true'){//如果是树 隐藏第3个选卡
			tabStrip.enable(tabStrip.tabGroup.children().eq(2), false);
		}
		
		//初始化布局  选择数据集
		$("#dataSourceSplitter").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsible : false,
				size : "410px"
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
						id : "id",
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
							createTime : {
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
				serverFiltering : true
			},
			filterable : true,
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
				width : 150,
				filterable : false,
				template : function(dataItem) {
					var ret = "默认";
					databaseSource.fetch().then(function() {
						var data = databaseSource.data();
						for (var i = 0; i < data.length; i++) {
							if (data[i].id == dataItem.databaseId) {
								ret = data[i].text;
							}
						}
					})
					return ret;
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 80,
				filterable : false
			}, {
				command : [ {
					name : "选择",
					click : function(e) {
						var tr = $(e.target).closest("tr"), dataItem = this.dataItem(tr);
						var data = {} ;
						var selectSource = getSource(dataItem.id) ;
						selectSource.fetch().then(function(){
							var columns = selectSource.data();
							data.id = "NO" + (selectDatasource.length+1) ;
							data.datasetId = dataItem.id ;
							data.title = dataItem.title ;
							data.name = dataItem.name ;
							data.createTime = dataItem.createTime ;
							data.databaseId = dataItem.databaseId ;
							data.chartType = "" ;
							//data.xAxisName = "" ;
							//data.yAxisName = "" ;
							selectDatasource.push(data);
							//data.columns = transformRows(columns,data.id) ;
							//selectColumns.push();
							$.merge(selectColumns, transformRows(columns,data));
							rushSelectDatasourceGrid();
						})
					}
				},{
					name : "查看",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						window.open("${pageContext.request.contextPath}/mx/dataset/sqlbuilder?id="+data.id);
					}
				}  ]
			} ]
		});
		//已选择的数据集
		$("#selectDatasourceGrid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : selectDatasource,
				schema : {
					model : {
						id : 'id',
						fields : {
							id : {
								editable : false
							},
							name : {
								editable : false
							},
							databaseId : {
								editable : false
							},
							chartType : {
								editable : true,
								type : 'string'
							}
						}
					}
				}
			},
			height : 150,
			scrollable : true,
			editable : true,
			selectable : "multiple,row",
			columns : [{
				field : 'id',
				title : '序号',
				width : 50
			}, {
				field : 'name',
				title : '名称',
				width : 250 
			},  {
				field : 'databaseId',
				title : '标段',
				width : 150,
				template : function(dataItem) {
					var ret = "默认";
					//databaseSource.fetch().then(function() {
						var data = databaseSource.data();
						for (var i = 0; i < data.length; i++) {
							if (data[i].id == dataItem.databaseId) {
								ret = data[i].text;
							}
						}
					//})
					return ret;
				}
			}, 
			{
				command : [ {
					name : "删除",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						var index = -1;
						$.each(selectDatasource,function(i,c){
							if(c && c.id == data.id){
								selectDatasource.splice(i, 1);
							}
						})
						removeSelectColumns(data);
						rushSelectDatasourceGrid();
					}
				} ,{
					name : "查看",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						window.open("${pageContext.request.contextPath}/mx/dataset/sqlbuilder?id="+data.datasetId);
					}
				} ]
			} ]
		});
		function axisEditor(container, options) {
			var $tr = $(container).parents('tr');
			var _index = $tr.index();
			var columns = options.model.columns || [];
			$("<input/>").attr('name', options.field).appendTo(container).kendoDropDownList({
				dataValueField : 'columnLabel',
				dataTextField : 'title',
				dataSource : columns
			});
		}
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
		function bindFn(role) {
			$('#' + role).bind('click', function() {
				var checked = this.checked;
				$.each(selectColumns, function(i, column) {
					column[role] = checked ;
				})
				rushSelectColumns();
			});
		}
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
		var ctypes =  [
			  {
			  	value:'type',
			  	text:'日历类型'
			  },
		      {
		    	  value:'title',
		    	  text:'标题'
		      },
		      {
		    	  value:'start',
		    	  text:'开始时间'
		      },{
		    	  value:'end',
		    	  text:'结束时间'
		      },{
		    	  value : 'backgroundColor',
		    	  text : '背景色'
		      },{
		      	  value: 'allDay',
		      	  text : '是否全天'
		      },{
		      	value:'remark',
		      	text:'详细备注'
		      }] ;
		//编辑字段
		$("#fieldColumnsGrid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : selectColumns,
				schema : {
					model : {
						id : 'sid',
						fields : {
							seq : {
								editable : false
							},
							title : {
								editable : false
							},
							ctype : {
								editable : true,
								type : 'string'
							},
							
							isShowList : {
								type : 'boolean',
								'editable' : true
							},
							/* isFilterable : {
								type : 'boolean',
								'editable' : true
							},
							isSortable : {
								type : 'boolean',
								'editable' : true
							}, */
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
			height : 540,
			scrollable : true,
			editable : true,
			selectable : "multiple,row",
			//toolbar : kendo.template($("#template").html(),selectColumns) ,
			columns : [ {
				field : 'seq',
				title : '序号',
				width : 50
			}, {
				field : 'title',
				title : '数据集名称',
				template : function(dataItem){
					return dataItem.tableNameCn+"."+dataItem.title
				},
				width : 200
			}, {
				title : '字段映射',
				field : 'ctype',
				template : function(dataItem) {
					var ctype = dataItem.ctype;
					if (ctypes) {
						var size = ctypes.length;
						for (var i = 0; i < size; i++) {
							var c = ctypes[i] ;
							if (c.value == dataItem.ctype) {
								return c.text;
							}
						}
					}
					return "";
				},
				editor : function(container, options){
					var $tr = $(container).parents('tr');
					var _index = $tr.index();
					$("<input/>").attr('name', options.field).appendTo(container).kendoDropDownList({
						dataValueField : 'value',
						dataTextField : 'text',
						optionLabel: {
							text: "请选择",
						    value: ""
						},
						dataSource : ctypes
					});
				},
				width : 150
			},{
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
			},]
		});
		//保存
		$("#commitToolbar").kendoToolBar({
			resizable : true,
			items : [{
				   type: "button", text: "新增数据集", icon: "k-icon k-i-add" ,click:function(e){
					   window.open("${pageContext.request.contextPath}/mx/dataset?pageId=${param.pageId}");
				   }
			   }, {
				type : "button",
				text : "保存",
				icon : "k-icon k-i-tick",
				click : function(e) {
					var results = [];
					var datasetNames = [] ;
					var result = {};
					result.relation = relation ; 
					result.selectDatasource = selectDatasource ;
					result.selectColumns = selectColumns ;
					for(var i=0;i<selectDatasource.length;i++){
						datasetNames.push(selectDatasource[i].name);
					}
					result.name = datasetNames.join(',');
					results.push(result);

					if ('${param.resultsElementId}' != '') {
						parent.document.getElementById('${param.resultsElementId}').value = JSON.stringify(results);
					}
					if ('${param.tablenameElementId}' != '') {
						parent.document.getElementById('${param.tablenameElementId}').value = datasetNames.join(',');
					}
					parent.layer.close(parent.layer.getFrameIndex());
				}
			}, {
				type : "button",
				text : "取消",
				icon : "k-icon k-i-del",
				click : function(e) {
					parent.layer.close(parent.layer.getFrameIndex());
				}
			} ]
		});
		rushSelectDatasourceGrid();
	});
	
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
			<div class="wdt">
				<table>
					<tr>
						<td class="wdtw">
							默认选中项  ：
						</td>
						<td>
							<input class="k-textbox" type="number" id="defaultIndex" data-bind="value:defaultIndex"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="tabstrip">
		<ul>
			<li class="k-state-active">选择数据集</li>
			<li>编辑字段</li>
		</ul>
		<div>
			<div id="dataSourceSplitter" style="height: 570px;">
				<div>
					<div id="datasourceGrid"></div>
				</div>
				<div>
					<div id="selectDatasourceGrid"></div>
				</div>
			</div>
		</div>
		<div>
			<div id="columnsSplitter" style="width: 100%; height: 570px;">
				<div id="fieldColumnsGrid"></div>
			</div>
		</div>
	</div>
	<div id="commitToolbar" style="height: 23px;text-align: right;"></div>
</body>
</html>