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
<title>存储设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		if(jQuery("#port").val()*1 < 1 || jQuery("#port").val()*1 >65536){
             alert('端口不合法，必须大约1并且小于65536！');
			 document.getElementById("port").focus();
			 return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/repository/saveRepository',
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
					   /**
					   if (window.opener) {
						window.opener.location.reload();
					   } else if (window.parent) {
						window.parent.location.reload();
					   }**/
					   //location.href='<%=com.glaf.core.util.RequestUtils.decodeURL(request.getParameter("fromUrl"))%>';
					   window.parent.location.reload();
					   window.close();
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/repository/saveRepository',
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
					   window.parent.location.reload();
					   window.close();
				   }
			 });
	}

	function changePort(){
        <c:if test="${empty repository}">
        var type = document.getElementById("type").value;
		//alert(type);
		if(type=="mysql"){
			document.getElementById("port").value="3306";
			document.getElementById("portLabel").innerHTML="3306";
		}
		if(type=="postgresql"){
			document.getElementById("port").value="5432";
			document.getElementById("portLabel").innerHTML="5432";
		}
		if(type=="sqlserver"){
			document.getElementById("port").value="1433";
			document.getElementById("portLabel").innerHTML="1433";
		}
		if(type=="mongodb"){
			document.getElementById("port").value="27017";
			document.getElementById("portLabel").innerHTML="27017";
		}
		if(type=="oracle"){
			document.getElementById("port").value="1521";
			document.getElementById("portLabel").innerHTML="1521";
		}
		</c:if>
	}

	function verify(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/repository/verify',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert(data.message);
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
		<span class="x_content_title">编辑记录</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
		   onclick="javascript:saveData();" >保存</a>
		&nbsp;
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
		   onclick="javascript:saveAsData();" >另存</a>
		<c:if test="${!empty repository }">
		&nbsp;
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'" 
		   onclick="javascript:verify();" >验证</a>
		</c:if>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${repository.id}"/>
  <input type="hidden" id="nodeId" name="nodeId" value="${nodeId}"/>
  <table class="easyui-form" style="width:600px;" align="left">
    <tbody>
	<tr>
		<td width="20%" align="left">主题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${repository.title}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">存储类型</td>
		<td align="left">
             <select id="type" name="type" onchange="javascript:changePort();">
			    <option value="">----请选择----</option>
			    <option value="mysql">MySQL</option>
				<option value="sqlserver">Microsoft SQL Server</option>
				<option value="mongodb">MongoDB</option>
				<option value="oracle">Oracle</option>
				<option value="postgresql">PostgreSQL</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("type").value="${repository.type}";
             </script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">存储级别</td>
		<td align="left">
             <select id="level" name="level">
			    <option value="1">主库</option>
				<option value="2">备用库</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("level").value="${repository.level}";
             </script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">读写操作</td>
		<td align="left">
             <select id="operation" name="operation">
			    <option value="2">读写</option>
				<option value="1">只写</option>
			    <option value="0">只读</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("operation").value="${repository.operation}";
             </script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">优先级</td>
		<td align="left">
             <select id="priority" name="priority">
			    <option value="">----请选择----</option>
				<c:forEach begin="0" end="99" step="1" var="index">
				<option value="${index}">${index}</option>   
				</c:forEach>
             </select>&nbsp;（提示：查询将使用优先级高的存储设备）
             <script type="text/javascript">
                 document.getElementById("priority").value="${repository.priority}";
             </script>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">主机</td>
		<td align="left">
            <input id="host" name="host" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${repository.host}"  size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">端口</td>
		<td align="left">
			<input id="port" name="port" type="text" 
			       class="easyui-validatebox easyui-numberbox x-text" 
				   increment="1"  
				   value="${repository.port}" size="5" maxlength="5"/>
		    <label id="portLabel"></label>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">用户名</td>
		<td align="left">
            <input id="user" name="user" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${repository.user}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">密码</td>
		<td align="left">
            <input id="password" name="password" type="password" 
			       class="easyui-validatebox  x-text"  
				   <c:if test="${!empty repository.password}"> value="88888888" </c:if>  size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">库名</td>
		<td align="left">
            <input id="dbname" name="dbname" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${repository.dbname}"  size="50"/>
		   <br>（提示：初始化后不能修改库名！）
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">分区名(表名)</td>
		<td align="left">
            <input id="bucket" name="bucket" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${repository.bucket}"  size="50"/>
		</td>
	</tr>
	
	<tr>
		<td width="20%" align="left">是否启用</td>
		<td align="left">
		<input type="radio" name="active" value="1" <c:if test="${repository.active == '1'}">checked</c:if>>启用&nbsp;&nbsp;
	    <input type="radio" name="active" value="0" <c:if test="${repository.active == '0'}">checked</c:if>>禁用
		</td>
	</tr>
 
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>