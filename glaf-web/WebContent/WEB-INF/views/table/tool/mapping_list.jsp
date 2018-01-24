<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script> 
<script type="text/javascript">
var editIndex = undefined;
var tableEditIndex = undefined;
$(document).ready(function(){
	$('#tableGrid').datagrid({
		url:'${pageContext.request.contextPath}/mx/table/mapping?method=tablesJson',
		method:'post',
		width:'100%',
		height:'97%',
		fitColumns:true,
		singleSelect:true,
		rownumbers:true,
		striped:true,
		columns:[[
			{field:'tableName',title:'数据表名',width:100},
			{field:'name',title:'数据表描述',width:100,editor:'text'}
		]],
		onClickRow:function(rowIndex,rowData){
			if(tableEditIndex!=rowIndex){
				$('#tableGrid').datagrid('acceptChanges');
				$('#tableGrid').datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
				tableEditIndex = rowIndex;
				var ed = $('#tableGrid').datagrid('getEditor', {index:tableEditIndex,field:'name'});
				$(ed.target).focus();
				$(ed.target).select();
			}
			$('#fieldGrid').datagrid('load',{'table':rowData.tableName});
		}
	});
	$('#fieldGrid').datagrid({
		url:'${pageContext.request.contextPath}/mx/table/mapping?method=fieldsJson',
		method:'post',
		width:'100%',
		height:'100%',
		fitColumns:true,
		striped:true,
		rownumbers:true,
		selectOnCheck:true,
		checkOnSelect:true,
		loadMsg:'正在加载数据，请稍后…',
		idField:'fieldName',
		columns:[[
			{field:'ck',checkbox:true},
			{field:'fieldName',title:'字段名',width:100},
			{field:'name',title:'字段描述',width:100,editor:'text'},
			{field:'fieldType',title:'字段类型',width:100},
			{field:'fieldLength',title:'字段长度',width:100},
			{field:'listNo',title:'排序号',width:30,align:'center',formatter:function(value){
				return value=='999'?'':value;
			}}
		]],
		onLoadSuccess:function(data){
			if(data){
				$('#fieldGrid').datagrid('clearChecked');
				//默认选中设置过的字段
				$.each(data.rows,function(index,row){
					if(row.name!=undefined && row.name!=''){
						$('#fieldGrid').datagrid('selectRecord',row.fieldName);
					}
				});
			}
		},
		onClickRow:function(index,rowData){
			if (editIndex != index){
				if (endEditing()){
					$('#fieldGrid').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					editIndex = index;
					var ed = $('#fieldGrid').datagrid('getEditor', {index:editIndex,field:'name'});
					$(ed.target).focus();
					$(ed.target).select();
				} else {
					$('#fieldGrid').datagrid('selectRow', editIndex);
				}
			}else{
				if(editIndex != undefined){
					var ed = $('#fieldGrid').datagrid('getEditor', {index:editIndex,field:'name'});
					var name = $(ed.target).val();
					rowData.name = name;
					$('#fieldGrid').datagrid('endEdit', editIndex);
					editIndex = undefined;
				}
			}
		},
		toolbar:[{
			text:'保存',
			iconCls: 'icon-save',
			handler: function(){
				$('#tableGrid').datagrid('acceptChanges');
				tableEditIndex = undefined;
				$('#fieldGrid').datagrid('acceptChanges');
				editIndex = undefined;
				
				var ckdatas = $('#fieldGrid').datagrid("getChecked");
				var table = $('#tableGrid').datagrid('getSelected');
				
				if(ckdatas.length<=0){
					alert('请先选择需要保存的字段');
					return;
				}
				
				var fieldNames = [];
				var names = [];
				for(var i=0;i<ckdatas.length;i++){
					var obj = ckdatas[i];
					fieldNames.push(obj.fieldName);
					names.push((obj.name&&obj.name!='')?obj.name:obj.fieldName);
				}
				
				var submiturl = "${pageContext.request.contextPath}/mx/table/mapping?method=genMapping";
				$.post(
						submiturl,
						{
							"table":table.tableName,
							"tableDescription":table.name?table.name:table.tableName,
							"fieldNames":fieldNames.join(";"),
							"names":names.join(";")
						},
						function(data){
							if(data.statusCode==200){
								alert("保存成功");
								$('#fieldGrid').datagrid("load")
							}else{
								alert("保存时出现异常，Error="+data.msg);
							}
						},
						'json'
					);
			}
		},{
			text:'清除',
			iconCls:'icon-clear',
			handler:function(){
				if(!confirm('确定要清除已保存的数据吗？')){
					return;
				}
				var table = $('#tableGrid').datagrid('getSelected');
				
				var submiturl = "${pageContext.request.contextPath}/mx/table/mapping?method=clearMapping";
				$.post(
					submiturl,
					{
						"table":table.tableName
					},
					function(data){
						if(data.statusCode==200){
							alert("清除已保存的数据字段成功");
							$('#fieldGrid').datagrid("load")
							$('#tableGrid').datagrid("load")
						}else{
							alert("清除时出现异常，Error="+data.msg);
						}
					},
					'json'
				);
			}
		}]
	});
});

function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#fieldGrid').datagrid('validateRow', editIndex)){
		var ed = $('#fieldGrid').datagrid('getEditor', {index:editIndex,field:'name'});
		var name = $(ed.target).val();
		$('#fieldGrid').datagrid('getRows')[editIndex]['name'] = name;
		$('#fieldGrid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

//执行搜索数据表
function doSearch(value,name){
	$('#tableGrid').datagrid('load',{'keywords':value,'searchType':name});
}
</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'数据表',split:true" style="width:350px;">
    	<input class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',menu:'#mm',searcher:doSearch" style="width:330px">
	    <table id="tableGrid"></table>
    </div>
    <div data-options="region:'center',title:'字段列表'" style="background:#eee;">
    	<table id="fieldGrid"></table>
    </div>
    <div id="mm">
		<div data-options="name:'tableName',selected:true">数据表名</div>
		<div data-options="name:'name'">数据表描述</div>
	</div>
</body>
</html>