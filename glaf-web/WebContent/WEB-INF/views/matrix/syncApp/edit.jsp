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
<title>数据同步</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/syncApp/save',
				   data: params,
				   dataType: 'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   if(data.statusCode == 200) { 
					       parent.location.reload(); 
					   }
				   }
			 });
	}

	function saveAsData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/syncApp/saveAs',
				   data: params,
				   dataType: 'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   if(data.statusCode == 200) { 
					       parent.location.reload(); 
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
	<span class="x_content_title">编辑数据同步</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveData();" >保存</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveAsData();" >另存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${syncApp.id}"/>
  <input type="hidden" id="syncId" name="syncId" value="${syncApp.id}"/>
  <table class="easyui-form" style="width:680px;" align="center">
    <tbody>
	<tr>
		<td width="10%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox x-text" style="width:425px;" 
				   value="${syncApp.title}"/>
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">来源数据库编号</td>
		<td align="left">
			<select id="srcDatabaseId" name="srcDatabaseId">
			    <option value="">----请选择----</option>
				<c:forEach items="${databases}" var="database">
				<option value="${database.id}">${database.title}[${database.dbname}]</option>
				</c:forEach>
             </select>
             <script type="text/javascript">
                 document.getElementById("srcDatabaseId").value="${syncApp.srcDatabaseId}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">目标数据库</td>
		<td align="left">
		    <select class="easyui-combobox" name="targetDatabaseIds" 
			        multiple="true" multiline="true"  
			        labelPosition="top" style="width:425px;height:30px;">
				<option value=""></option>
                <c:forEach items="${databases}" var="database">
				<option value="${database.id}" ${database.selected}>${database.title}[${database.dbname}]</option>
				</c:forEach>
            </select>
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">同步标识</td>
		<td align="left">
		    <select id="syncFlag" name="syncFlag">
			    <option value="">----请选择----</option>
				<option value="INSERT_UPDATE">增量同步</option>
				<option value="INSERT_ONLY">数据表为空才能增加</option>
			    <option value="DELETE_INSERT">数据表不空时先删除再增加</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("syncFlag").value="${syncApp.syncFlag}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">类型</td>
		<td align="left">
             <select id="type" name="type">
			    <option value="">----请选择----</option>
			    <option value="SYS">系统表</option>
				<option value="FORM">表单自定义</option>
				<option value="BPM">工作流</option>
				<option value="USER">业务数据</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("type").value="${syncApp.type}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">是否自动同步</td>
		<td align="left">
		    <select id="autoSyncFlag" name="autoSyncFlag">
			    <option value="">----请选择----</option>
				<option value="Y">是</option>
			    <option value="N">否</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("autoSyncFlag").value="${syncApp.autoSyncFlag}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">时间间隔</td>
		<td align="left">
		    <select id="interval" name="interval">
			    <option value="">----请选择----</option>
				<option value="1">1分钟</option>
				<option value="2">2分钟</option>
				<option value="5">5分钟</option>
				<option value="10">10分钟</option>
				<option value="15">15分钟</option>
				<option value="20">20分钟</option>
				<option value="30">30分钟</option>
			    <option value="60">60分钟</option>
				<option value="120">2小时</option>
				<option value="240">4小时</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("interval").value="${syncApp.interval}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">执行顺序</td>
		<td align="left">
		    <select id="sortNo" name="sortNo">
			    <option value="0">----请选择----</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
			    <option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("sortNo").value="${syncApp.sortNo}";
             </script>
			 &nbsp;（提示：顺序小的先执行。）
		</td>
	</tr>
	<tr>
		<td width="10%" align="left">是否有效</td>
		<td align="left">
		    <select id="active" name="active">
				<option value="Y">是</option>
			    <option value="N">否</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("active").value="${syncApp.active}";
             </script>
		</td>
	</tr>
	<tr><td><br><br><br><br></td></tr>
    </tbody>
  </table>
 </form>
</div>
</div>
</body>
</html>