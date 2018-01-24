<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字段列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/saveColumn',
				   data: params,
				   dataType:  'json',
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
					       window.parent.location.reload();
					   } 
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/saveColumn',
				   data: params,
				   dataType:  'json',
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
					       window.parent.location.reload();
					   }
				   }
			 });
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑字段</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${column.id}"/>
  <input type="hidden" id="tableId" name="tableId" value="${tableId}"/>
  <table class="easyui-form" style="width:650px;" align="center">
    <tbody>
	<tr>
		<td width="20%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text" 
				   value="${column.title}" size="60"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">名称</td>
		<td align="left">
            <input id="name" name="name" type="text" 
			       class="easyui-validatebox  x-text" 
				   value="${column.name}" size="60"/>
		   <br> 
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">取数位置</td>
		<td align="left">
            <input id="colIndex" name="colIndex" type="text" 
			       class="easyui-numberbox  x-text"  style="width:60px;"
				   value="${column.colIndex}" size="3"/>
		    &nbsp;（提示：从文本文件或Excel文件中提取数据的列数）	
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">数据类型</td>
		<td align="left">
            <select id="javaType" name="javaType">
			    <option value="Integer">整数型</option>
				<option value="Long">长整数型</option>
				<option value="Double">数值型</option>
				<option value="Date">日期型</option>
				<option value="String">文本型</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("javaType").value="${column.javaType}";
			</script>
		</td>
	</tr>

    <tr>
		<td width="20%" align="left">字段长度</td>
		<td align="left">
            <select id="length" name="length">
			    <option value="0">默认</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="150">150</option>
				<option value="200">200</option>
				<option value="250">250</option>
				<option value="500">500</option>
				<option value="1000">1000</option>
				<option value="2000">2000</option>
				<option value="4000">4000</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("length").value="${column.length}";
			</script>
			&nbsp;（提示：文本类型可以设置字段长度，表结构生成后不能修改）
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">小数精度</td>
		<td align="left">
            <select id="scale" name="scale">
			    <option value="">----请选择----</option>
				<option value="0">不保留小数点</option>
				<option value="1">一位小数点</option>
				<option value="2">两位小数点</option>
				<option value="3">三位小数点</option>
				<option value="4">四位小数点</option>
				<option value="5">五位小数点</option>
				<option value="6">六位小数点</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("scale").value="${column.scale}";
			</script>
			（提示：数值型字段才需要设置该值）
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">是否显示</td>
		<td align="left">
            <select id="displayType" name="displayType">
				<option value="0">不显示</option>
				<option value="1">表单显示</option>
				<option value="2">列表显示</option>
				<option value="4">表单及列表显示</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("displayType").value="${column.displayType}";
			</script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">是否可编辑</td>
		<td align="left">
            <select id="editableField" name="editableField">
			    <option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("editableField").value="${column.editableField}";
			</script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">是否可检索</td>
		<td align="left">
            <select id="searchableField" name="searchableField">
			    <option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("searchableField").value="${column.searchableField}";
			</script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">是否可导出</td>
		<td align="left">
            <select id="exportFlag" name="exportFlag">
			    <option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("exportFlag").value="${column.exportFlag}";
			</script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">是否必填</td>
		<td align="left">
            <select id="requiredField" name="requiredField">
				<option value="0">否</option>
				<option value="1">是</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("requiredField").value="${column.requiredField}";
			</script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">设置值</td>
		<td align="left">
            <select id="dataCode" name="dataCode">
				<option value="">无</option>
				<option value="CURR_USER">当前登录用户名称</option>
				<option value="CURR_ORGANIZATION">当前登录用户机构名称</option>
				<option value="CURR_DATE">当前日期</option>
				<option value="CHOOSE_USER">选择一个用户</option>
				<option value="CHOOSE_USERS">选择多个用户</option>
				<option value="CHOOSE_ORGANIZATION">选择一个机构名称</option>
				<option value="CHOOSE_ORGANIZATIONS">选择多个机构名称</option>
				<c:forEach var="item" items="${items}" >
				<option value="${item.value}">${item.name}</option>
				</c:forEach>
            </select>
			<script type="text/javascript">
			    document.getElementById("dataCode").value="${column.dataCode}";
			</script>
		</td>
	</tr>
    <tr><td><br><br><br><br>&nbsp;</td></tr>
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>