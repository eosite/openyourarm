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
<title>应用</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/deployment/deployApp/saveDeployApp',
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
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/deployment/deployApp/saveDeployApp',
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
	<span class="x_content_title">编辑应用</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveAsData();" >另存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${deployApp.id}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
	<tr>
		<td width="20%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text" style="width:385px;"  
				   value="${deployApp.title}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">名称</td>
		<td align="left">
            <input id="name" name="name" type="text" 
			       class="easyui-validatebox  x-text" style="width:385px;"  
				   value="${deployApp.name}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">代码</td>
		<td align="left">
            <input id="code" name="code" type="text" 
			       class="easyui-validatebox  x-text"  style="width:385px;" 
				   value="${deployApp.code}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">部署服务器</td>
		<td align="left">
			<select id="serverId" name="serverId">
			    <option value="">----请选择----</option>
			    <c:forEach items="${servers}" var="item">
			    <option value="${item.id}">${item.title}</option>     
			    </c:forEach>
		    </select>
		    <script type="text/javascript">
			   document.getElementById("serverId").value="${deployApp.serverId}";
		    </script>    
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">类型</td>
		<td align="left">
            <select id="type" name="type">
			    <option value="java">Java</option>
			    <option value="DB">DB</option>
		    </select>
		    <script type="text/javascript">
			    document.getElementById("type").value="${deployApp.type}";
		    </script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">域名</td>
		<td align="left">
            <input id="domainName" name="domainName" type="text" 
			       class="easyui-validatebox  x-text" style="width:385px;"  
				   value="${deployApp.domainName}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">环境上下文</td>
		<td align="left">
            <input id="contextPath" name="contextPath" type="text" 
			       class="easyui-validatebox  x-text" style="width:385px;" 
				   value="${deployApp.contextPath}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">对外端口</td>
		<td align="left">
			<input id="port" name="port" type="text" 
			       class="easyui-numberbox x-text" 
				   increment="1"  
				   value="${deployApp.port}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">内容</td>
		<td align="left">
		    <textarea  id="content" name="content" rows="6" cols="46" class="x-text" style="height:90px;width:385px;" >${deployApp.content}</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">启动命令</td>
		<td align="left">
		    <textarea  id="startCmd" name="startCmd" rows="6" cols="46" class="x-text" style="height:90px;width:385px;" >${deployApp.startCmd}</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">停止命令</td>
		<td align="left">
		    <textarea  id="stopCmd" name="stopCmd" rows="6" cols="46" class="x-text" style="height:90px;width:385px;" >${deployApp.stopCmd}</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">检查间隔时间</td>
		<td align="left">
			<input id="checkInterval" name="checkInterval" type="text" 
			       class="easyui-numberbox x-text"  size="3"
				   increment="1"  style="width:50px;" maxlength="3"
				   value="${deployApp.checkInterval}"/>（单位：秒）
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">是否有效</td>
		<td align="left">
		    <select id="active" name="active">
			    <option value="Y">是</option>
			    <option value="N">否</option>
		    </select>
		    <script type="text/javascript">
			    document.getElementById("active").value="${deployApp.active}";
		    </script>    
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">&nbsp;</td>
		<td align="left">
		     <br><br>
		</td>
	</tr>
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>