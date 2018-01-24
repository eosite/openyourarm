<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setAttribute("theme", "default");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
	var utableTreeUrl = "${pageContext.request.contextPath}/rs/isdp/cellUTableTree/getUtableTreeByTableType?1=1", cellUrl = "${pageContext.request.contextPath}/rs/isdp/treeDot/getTreeDotByParentId?1=1";

	//zTree属性配置
	var setting = {
		view : {
			showIcon : false,
			showLine : true,
			showTitle : false,
			selectedMulti : false
		},
		async : {
			enable : true,
			url : utableTreeUrl,
			autoParam : [ "indexId", "nlevel=level" ]
		},
		data : {
			simpleData : {
				enable : true,
				pIdKey : "parentId"
			},
			key : {
				name : "indexName"
			}
		},
		callback : {
			onClick : zTreeOnClick
		}
	};

	//单击zTree节点时处理事件
	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		var tableType = $('#tableTypeCombobox').combobox('getValue');
		var ds = $('#databaseCodeCombobox').combobox('getValue');
		if (tableType == "") {
			alert("请先选择数据表类型");
			return;
		}
		var queryParams = {};
		queryParams.allChild = true;
		queryParams.indexId = treeNode.indexId;
		queryParams.type = tableType;
		queryParams.systemName = ds;

		$('#table_grid').datagrid({
			url : '${pageContext.request.contextPath}/rs/isdp/cellDataTable/getTableListByIndexId',
			queryParams : queryParams
		});
	}

	//重新加载zTree事件
	function initZTree(tableType, ds) {
		if (tableType == 1) {
			setting.async.url = utableTreeUrl + "&tableType=2&systemName=" + ds;
			$.fn.zTree.init($("#zTree"), setting);
		} else if (tableType == 2) {
			setting.async.url = cellUrl + '&systemName=' + ds;
			$.fn.zTree.init($("#zTree"), setting);
		} else if (tableType == 3) {
			setting.async.url = utableTreeUrl + "&tableType=12&systemName=" + ds;
			$.fn.zTree.init($("#zTree"), setting);
		} else if (tableType == 4) {
			$.fn.zTree.init($("#zTree"), setting, [ { 'indexName' : '系统内置表', 'parentId' : -1, 'indexId' : 1, id : '1|' } ]);
		} else if (tableType == 5) {
			$.fn.zTree.init($("#zTree"), setting, [ { 'indexName' : '自动生成表', 'parentId' : -1, 'indexId' : -1,id : '1|' } ]);
		}
	}

	//检查表
	function searchTable() {
		var queryParams = $('#table_grid').datagrid("options").queryParams;
		queryParams.keyword = $('#keyword').val();
		$('#table_grid').datagrid("load", queryParams);
	}

	$(function() {
		$('#tableTypeCombobox').combobox({
			url : '${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson?nodeCode=tableType',
			valueField : 'value',
			textField : 'name',
			editable : false,
			panelHeight : 'auto',
			onLoadSuccess : function() {
				//设置默认选中第一个
				var data = $(this).combobox('getData');
				$(this).combobox('select', data[0].value);
			},
			onSelect : function(record) {
				var ds = $('#databaseCodeCombobox').combobox(
						'getValue');
				initZTree(record.value, ds);
			}
		});

		$('#databaseCodeCombobox').combobox({
			url : '${pageContext.request.contextPath}/rs/isdp/global/databaseJson',
			valueField : 'code',
			textField : 'text',
			panelHeight : 'auto',
			value : 'default',
			onSelect : function(record) {
				$('#systemName').val(record.code);

				var tableType = $('#tableTypeCombobox')
						.combobox('getValue');
				initZTree(tableType, record.code)

				$('#table_grid').datagrid('reload', {
					systemName : record.code
				});
			}
		});

		$('#table_grid').datagrid({
			height : 360,
			toolbar : "#table_grid_tb",
			rownumbers : true,
			striped : true,
			pagination : true,
			singleSelect : true,
			pageSize : 20,
			columns : [ [ {
				field : 'id',
				title : 'id',
				hidden : true
			}, {
				field : 'tableName',
				title : '表名',
				width : 150
			}, {
				field : 'name',
				title : '名称',
				width : 300
			}, {
				field : 'ctime',
				title : '创建时间',
				width : 100
			} ] ],
			onDblClickRow : function(rowIndex, rowData) {
				var tableType = $('#tableTypeCombobox').combobox('getValue');
				var ds = $('#databaseCodeCombobox').combobox('getValue');
				rowData.tableType = tableType;
				rowData.TableId = rowData.id;
				rowData.TableName = rowData.tableName;
				rowData.databaseCode = ds ? ds : 'default';
				$('#select_table_grid').datagrid('insertRow', {
					index : 0,
					row : rowData
				});
			}
		});

		$('#select_table_grid').datagrid({
			height : 193,
			rownumbers : true,
			striped : true,
			columns : [ [ {
				field : 'TableId',
				title : 'TableId',
				hidden : true
			}, {
				field : 'databaseCode',
				title : '数据源',
				width : 100
			}, {
				field : 'TableName',
				title : '表名',
				width : 150
			}, {
				field : 'name',
				title : '名称',
				width : 300
			} ] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid('deleteRow', rowIndex);
			}
		});
		
		var tableobj = parent.document.getElementById('${param.elementId}').value;
		if(tableobj!=''){
			tableobj = JSON.parse(tableobj);
			var datas = [];
			datas.push(tableobj);
			$('#select_table_grid').datagrid('loadData',datas);
		}
		
	})
	
	function confirm(){
		var rows = $('#select_table_grid').datagrid('getData').rows;
		if(rows.length===0){
			alert('请选择一条记录');
			return;
		}else if(rows.length>1){
			alert('只能选择一条记录');
			return;
		}else{
			var row = {};
			row.TableId = rows[0].TableId;
			row.tableType = rows[0].tableType;
			row.TableName = rows[0].TableName;
			row.name = rows[0].name;
			row.databaseCode = rows[0].databaseCode;
			
			if('${param.nameId}' !== ''){
				parent.document.getElementById('${param.nameId}').value = row.name;
			}
			
			if('${param.elementId}' !== ''){
				parent.document.getElementById('${param.elementId}').value = JSON.stringify(row);
				parent.layer.close(parent.layer.getFrameIndex());
			}
		}
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="height: 500px">
		<div data-options="region:'west',split:true" style="width: 200px">
			<table cellpadding="1" cellspacing="0"
				style="width: 100%; height: 60px" border="0">
				<tr>
					<td align="right">标段：</td>
					<td><input id="databaseCodeCombobox" style="width: 130px" /></td>
				</tr>
				<tr>
					<td align="right">类型：</td>
					<td><input id="tableTypeCombobox" style="width: 130px" /></td>
				</tr>
			</table>
			<ul id="zTree" class="ztree"></ul>
		</div>
		<div data-options="region:'center'">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center'">
					<table id="table_grid"></table>
					<div id="table_grid_tb">
						<label>表名：</label> 
						<input type="text" id="keyword" /> 
						<a href="javascript:searchTable();" id="keyword" class="easyui-linkbutton" iconCls="icon-search">检索</a>
						<a href="javascript:confirm();" class="easyui-linkbutton" iconCls="icon-ok">确定选择</a>
					</div>
				</div>

				<div data-options="region:'south',split:true" style="height: 200px">
					<table id="select_table_grid"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>