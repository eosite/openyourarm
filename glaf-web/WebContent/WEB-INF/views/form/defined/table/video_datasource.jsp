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
			row.ntype = "" ; //系列or名称or值  类型
			row.rtype = "" ;
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
									if(tsd>maxNo){
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
		var ctypes =  [
		      {
		    	  value:'ip',
		    	  text:'IP'
		      },
		      {
		    	  value:'port',
		    	  text:'端口'
		      },{
		    	  value:'userName',
		    	  text:'用户'
		      },{
		    	  value : 'pwd',
		    	  text : '密码'
		      }] ;
		var ptypes =  [
		 		      {
		 		    	  value:'ip',
		 		    	  text:'IP'
		 		      },
		 		      {
		 		    	  value:'port',
		 		    	  text:'端口'
		 		      },{
		 		    	  value:'userName',
		 		    	  text:'用户'
		 		      },{
		 		    	  value : 'pwd',
		 		    	  text : '密码'
		 		      },{
		 		    	  value : 'jkd',
		 		    	  text : '监控点'
		 		      }] ;
		var rtypes =  [
			 		      {
			 		    	  value:'ip',
			 		    	  text:'IP'
			 		      },
			 		      {
			 		    	  value:'port',
			 		    	  text:'端口'
			 		      },{
			 		    	  value:'username',
			 		    	  text:'用户'
			 		      },{
			 		    	  value : 'password',
			 		    	  text : '密码'
			 		      },{
			 		    	  value : 'cameraId',
			 		    	  text : '摄像头ID'
			 		      }] ;
		var ntypes = [{
	    	  value:'developerId',
	    	  text:'开发者ID(开发者)'
	      },/* {
	    	  value:'apiUrl',
	    	  text:'远程地址(开发者)'
	      },{
	    	  value:'phone',
	    	  text:'注册手机号(开发者)'
	      },{
	    	  value : 'userId',
	    	  text : '用户账号(开发者)'
	      },{
	    	  value : 'appKey',
	    	  text : '程序密钥(开发者)'
	      },{
	    	  value : 'secretKey',
	    	  text : '安全密钥(开发者)'
	      }, */{
	    	  value : 'cameraId',
	    	  text : '摄像头ID'
	      },{
	    	  value : 'safeKey',
	    	  text : '摄像头安全码'
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
							ntype : {
								editable : true,
								type : 'string'
							},
							ptype : {
								editable : true,
								type : 'string'
							},
							rtype : {
								editable : true,
								type : 'string'
							}
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
				title : '直连模式',
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
			}, {
				title : '萤石云模式',
				field : 'ntype',
				template : function(dataItem) {
					var ntype = dataItem.ntype;
					if (ntypes) {
						var size = ntypes.length;
						for (var i = 0; i < size; i++) {
							var c = ntypes[i] ;
							if (c.value == dataItem.ntype) {
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
						dataSource : ntypes
					});
				},
				width : 150
			},{
				title : '平台模式',
				field : 'ptype',
				template : function(dataItem) {
					var ptype = dataItem.ptype;
					if (ptypes) {
						var size = ptypes.length;
						for (var i = 0; i < size; i++) {
							var c = ptypes[i] ;
							if (c.value == dataItem.ptype) {
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
						dataSource : ptypes
					});
				},
				width : 150
			},{
				title : '智能交通平台模式',
				field : 'rtype',
				template : function(dataItem) {
					var rtype = dataItem.rtype;
					if (rtypes) {
						var size = rtypes.length;
						for (var i = 0; i < size; i++) {
							var c = rtypes[i] ;
							if (c.value == dataItem.rtype) {
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
						dataSource : rtypes
					});
				},
				width : 150
			} ]
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