<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列规则</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/tableExecutionColumn/saveTableExecutionColumn',
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
					   if (window.opener) {
						  window.opener.location.reload();
					   } else if (window.parent) {
						  window.parent.location.reload();
					   } 
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/tableExecutionColumn/saveTableExecutionColumn',
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
	<span class="x_content_title">编辑列规则</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${tableExecutionColumn.id}"/>
  <input type="hidden" id="executionId" name="executionId" value="${executionId}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
	<tr>
		<td width="15%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox x-text" style="width:400px;"  
				   value="${tableExecutionColumn.title}"/>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">字段名</td>
		<td align="left">
            <input id="columnName" name="columnName" type="text" 
			       class="easyui-validatebox x-text" style="width:400px;"  
				   value="${tableExecutionColumn.columnName}"/>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">字段类型</td>
		<td align="left">
			<select id="type" name="type">
				<option value="Integer">整数型</option>
				<option value="Long">长整数型</option>
				<option value="Double">数值型</option>
				<option value="Date">日期型</option>
				<option value="String">字符串型</option>
			</select>
			<script type="text/javascript">
				document.getElementById("type").value="${tableExecutionColumn.type}";
			</script>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">值表达式</td>
		<td align="left">
            <input id="valueExpression" name="valueExpression" type="text" 
			       class="easyui-validatebox x-text" style="width:400px;"   
				   value="${tableExecutionColumn.valueExpression}"/>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">是否有效</td>
		<td align="left">
		    <select id="locked" name="locked">
				<option value="0">是</option>
				<option value="1">否</option>
			</select>
			<script type="text/javascript">
				document.getElementById("locked").value="${tableExecutionColumn.locked}";
			</script>
		</td>
	</tr>
 
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>