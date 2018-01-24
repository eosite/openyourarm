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
							var maxNo = 0,tsd,sds;
							if(selectDatasource && selectDatasource.length){
								for(var i=0;i<selectDatasource.length;i++){
									sds =  selectDatasource[i];
									tsd = sds.id.replace('NO','');
									if(tsd-maxNo>0){
										maxNo = tsd ;
									}
								}
							}
							data.id = "NO" + (parseInt(maxNo)+1) ;
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
			}, {
				field : 'chartType',
				title : '类型',
				width : 150,
				template : function(dataItem){
					var ret = "" ;
					chartsSource.fetch().then(function(){
						var data = chartsSource.data();
						for(var i=0;i<data.length ;i++){
							if(data[i].code == dataItem.chartType){
								ret = data[i].name ;
							}
						}
					})
					return ret ;
				}, 
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).
					 kendoDropDownList({dataValueField:'code',dataTextField:'name',dataSource:chartsSource});
				},
			},  {
				field : 'markerEnable',
				title : '显示点',
				width : 70,
				template : function(dataItem){
					var ret = "" ;
					if(dataItem.markerEnable != null){
						ret = dataItem.markerEnable ? "显示" : "不显示";
					}
					return ret ;
				}, 
				editor : function(container,options){
					var $checkbox = $('<input type="checkbox">').attr("name","markerEnable");
					$checkbox.appendTo(container);
				},
			}, {
				field : 'dataLabelsEnable',
				title : '显示值',
				width : 70,
				template : function(dataItem){
					var ret = "" ;
					if(dataItem.markerEnable != null){
						ret = dataItem.dataLabelsEnable ? "显示" : "不显示";
					}
					return ret ;
				}, 
				editor : function(container,options){
					var $checkbox = $('<input type="checkbox">').attr("name","dataLabelsEnable");
					$checkbox.appendTo(container);
				},
			}, {
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
		var ctypes =  [{
			value:'fileContent',
			text : '文件内容' 
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
							chartStack : {
								editable : true,
								type : 'boolean',
								
							}
							/*	xAxisName : { //系列 x
								type : 'string',
								'editable' : true
							},
							yAxisName : { //名称 y
								type : 'string',
								'editable' : true
							},
							axisName : { // 值
								type : 'string',
								'editable' : true
							},
					 seriesStack :{ //堆, 根据值分类
							type : 'string',
							'editable' : true
						},
						seriesZIndex :{ //  图zIndex
							type : 'number',
							'editable' : true
						},
						seriesLegendIndex : {//图例顺序
							type : 'string',
							'editable' : true
						},
						seriesColor : {//系列颜色
							type : 'string',
							'editable' : true
						}, */

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
				title : '类型',
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
				field : 'chartStack',
				title : '堆叠分组字段',
				width : 80,
				template : function(dataItem){
					return dataItem.chartStack ? "确定" : "" ;
				}
			}/*,{
				title : '颜色',
				field : 'cColor',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.cColor+"'></div>";
				},
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 var _index = $tr.index() ;
					 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
				},
				width : 50
			} , {
				title : '系列(x轴)',
				field : 'xAxisName',
				template : function(dataItem) {
					var columns = dataItem.columns;
					if (columns) {
						var size = columns.length;
						for (var i = 0; i < size; i++) {
							if (columns[i].columnLabel == dataItem.xAxisName) {
								return columns[i].title;
							}
						}
					}
					return "";
				},
				editor : axisEditor,
				width : 60
			}, {
				title : '名称(y轴)',
				field : 'yAxisName',
				template : function(dataItem) {
					var columns = dataItem.columns;
					if (columns) {
						var size = columns.length;
						for (var i = 0; i < size; i++) {
							if (columns[i].columnLabel == dataItem.yAxisName) {
								return columns[i].title;
							}
						}
					}
					return "";
				},
				editor : axisEditor,
				width : 60
			}, {
				title : '值',
				field : 'axisName',
				template : function(dataItem) {
					var columns = dataItem.columns;
					if (columns) {
						var size = columns.length;
						for (var i = 0; i < size; i++) {
							if (columns[i].columnLabel == dataItem.axisName) {
								return columns[i].title;
							}
						}
					}
					return "";
				},
				editor : axisEditor,
				width : 60
			} */ ]
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
					var colContains = [],column ;
					for(var j=0;j<selectColumns.length;j++){
						column = selectColumns[i];
						if(column.chartStack){
							if(colContains.indexOf(column.seq + column.chartStack)!=-1){
								alert('堆叠分组字段一个数据集只能设置一个');
								return ;
							}else{
								colContains.push(column.seq + column.chartStack);
							}
						}
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
		
		
		//多数据集关系定义
		/* $("#relationSplitter").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsible : false,
				size : "33%"
			}, {
				collapsible : false,
				size : "33%"
			}, {
				collapsible : false,
				size : "33%"
			}  ]
		}); */
		
		  
	});
	
</script>
</head>
<body>
	<div id="tabstrip">
		<ul>
			<li class="k-state-active">选择数据集</li>
				<!-- 关系定义   钻下  混合 
			<li>多数据集关系定义</li>
				 -->
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
		<!-- 
		<div>
			<div id="relationSplitter" style="height: 570px;" >
				<div >
					 选择数据集
				</div>
				<div>
					<div id="container"></div>
				</div>
				<div>
				
				</div>
			</div>
		</div>
		 -->
		<div>
			<div id="columnsSplitter" style="width: 100%; height: 570px;">
				<div id="fieldColumnsGrid"></div>
			</div>
		</div>
	</div>
	<div id="commitToolbar" style="height: 23px;text-align: right;"></div>
</body>
</html>