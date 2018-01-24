<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    List<ColumnDefinition> columns = (List<ColumnDefinition>)request.getAttribute("columns");
	String tableName =  request.getParameter("tableName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑字段信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.json.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/map.js"></script>
<script type="text/javascript">
               
   function switchTx(){
         document.getElementById("iForm").submit();
   }

   function save(createTable){
	   if (endEditing()){
			$('#dg').datagrid('acceptChanges');
		}
	    var rows = $('#dg').datagrid('getRows');
		document.getElementById("createTable").value=createTable;
		document.getElementById("jsonArray").value=JSON.stringify(rows);
		//alert(JSON.stringify(rows));
		var len = rows.length;
		for(i=0; i <len; i++){
			var row = rows[i];
			var str = JSON.stringify(row);
			//alert(str);
			var myData = JSON.parse(str);
			//alert(row.columnName + "  "+ row.title);
		}

        var form = document.getElementById("iForm");
	    var link = "<%=request.getContextPath()%>/mx/sys/tableInterface/saveAdd";
	    var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   if(data.statusCode == 200){
					       //window.parent.location.reload();
					   }
				   }
			 });
   }

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑字段信息">&nbsp;
    编辑字段信息</div>
<br>
<form id="iForm" name="iForm" method="post" >
<input type="hidden" id="jsonArray" name="jsonArray">
<input type="hidden" id="createTable" name="createTable">
<table  align="center" border="1" cellspacing="2" cellpadding="2"  style="width:885px;">
 <tr>
	  <td colspan="5" height="35">
	  数据库选择&nbsp;
	  <select id="databaseId" name="databaseId">
		<option value="0"></option>
		<c:forEach items="${databases}" var="item">
		<option value="${item.id}">${item.title}</option>
		</c:forEach>
	  </select>
	  <script type="text/javascript">
	    document.getElementById("databaseId").value="${databaseId}";
	  </script>
	  </td>
 </tr>
 <tr>
	  <td colspan="5" height="35">
	  中文名称&nbsp;&nbsp;
	  <input type="text" name="tableNameCn" value="${tableNameCn}" size="30" class="easyui-textbox">
	  &nbsp;（提示：选择一个库，输入表名，查询是否已经存在，如存在直接修改即可。）
	  </td>
 </tr>
 <tr>
	  <td colspan="5" height="35">
	  数据库表&nbsp;&nbsp;
	  <input type="text" name="tableName" value="${tableName}" size="30" class="easyui-textbox" >
	  &nbsp;<input type="button" value="查询" onclick="javascript:switchTx();"  class="btn btnGray">
	  </td>
 </tr>
 <tr>
 <td colspan="5">
 <table id="dg" class="easyui-datagrid" title="字段列表" style="width:880px;height:400px;margin:30px"  
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%=request.getContextPath()%>/mx/sys/tableInterface/columnsJson?tableName=${tableName}&databaseId=${databaseId}',
				method: 'get',
				onClickCell: onClickCell,
				onEndEdit: onEndEdit
			">
		<thead>
			<tr>
				<th data-options="field:'columnName', width:150, editor:{type:'textbox'}, required:true">字段名称</th>
				<th data-options="field:'title', width:220, align:'left', editor:{type:'textbox'}, required:true">标题</th>
				<th data-options="field:'type', width:150, required:true,
						formatter:function(value, row){
							return row.typeName;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'type',
								textField:'typeName',
								method:'get',
								url:'<%=request.getContextPath()%>/type.json',
								required:true
							}
						}">字段类型</th>
				<th data-options="field:'length', width:120, align:'right', editor:{type:'numberbox',options:{precision:0}}">
				字段长度</th>
				<th data-options="field:'ismustfill',width:60,align:'center',editor:{type:'checkbox',options:{on:'1',off:'0'}}">是否必填</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
		   onclick="append()">增加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
		   onclick="removeit()">删除</a>
		<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" 
		   onclick="accept()">确定</a> -->
	</div>
	
	<script type="text/javascript">
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#dg').datagrid('validateRow', editIndex)){
				$('#dg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickCell(index, field){
			if (editIndex != index){
				if (endEditing()){
					$('#dg').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					var ed = $('#dg').datagrid('getEditor', {index:index,field:field});
					if (ed){
						($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
					}
					editIndex = index;
				} else {
					setTimeout(function(){
						$('#dg').datagrid('selectRow', editIndex);
					},0);
				}
			}
		}
		function onEndEdit(index, row){
			var ed = $(this).datagrid('getEditor', {
				index: index,
				field: 'type'
			});
			row.typeName = $(ed.target).combobox('getText');
		}
		function append(){
			if (endEditing()){
				$('#dg').datagrid('appendRow',{type:'String', ismustfill:"0"});
				editIndex = $('#dg').datagrid('getRows').length-1;
				$('#dg').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		function removeit(){
			if (editIndex == undefined){return}
			$('#dg').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}
		function accept(){
			if (endEditing()){
				$('#dg').datagrid('acceptChanges');
			}
		}
		function reject(){
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
		}
		function getChanges(){
			var rows = $('#dg').datagrid('getChanges');
			alert(rows.length+' rows are changed!');
		}
	</script>
	  </td>
 </tr>
 
  <tr>
    <td colspan="5" align="center" valign="center" height="30">&nbsp;
       <input type="button" value="保存"  class="btn btnGray" style="width: 90px" onclick="javascript:save('false');">
       &nbsp;
	   <input type="button" value="保存并建表"  class="btn btnGray" style="width: 120px" onclick="javascript:save('true');"> 
	</td>
  </tr>
</table>   
</form>
</div>     
<br> 
<br> 
<br> 
<br> 
</body>
</html>