<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
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
<title>数据库设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var type = document.getElementById("type").value;
		if(type!="sqlite" && jQuery("#port").val()*1 < 1 || jQuery("#port").val()*1 >65536){
             alert('端口不合法，必须大约1并且小于65536！');
			 document.getElementById("port").focus();
			 return;
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/database/saveDB',
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

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/database/saveDB',
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
        <c:if test="${empty database}">
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
		if(type=="hbase"){
			document.getElementById("port").value="2181";
			document.getElementById("portLabel").innerHTML="2181";
		}
		if(type=="oracle"){
			document.getElementById("port").value="1521";
			document.getElementById("portLabel").innerHTML="1521";
		}
		if(type=="sqlite"){
			document.getElementById("port").value="0";
			document.getElementById("portLabel").innerHTML="0";
		}
		if(type=="DM DBMS"){
			document.getElementById("port").value="5236";
			document.getElementById("portLabel").innerHTML="5236";
		}
		</c:if>
	}

	function verify(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/database/verify',
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
		<c:if test="${!empty database }">
		&nbsp;
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'" 
		   onclick="javascript:verify();" >验证</a>
		</c:if>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${database.id}"/>
  <input type="hidden" id="databaseId_enc" name="databaseId_enc" value="${databaseId_enc}"/>
  <input type="hidden" id="nodeId" name="nodeId" value="${nodeId}"/>
  <table class="easyui-form" style="width:800px;" align="left">
    <tbody>
	<tr>
		<td width="18%" align="left">主题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.title}" size="60"/>
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">标段</td>
		<td align="left">
            <input id="section" name="section" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.section}" size="60" maxlength="50"/>
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">别名</td>
		<td align="left">
            <input id="mapping" name="mapping" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.mapping}" size="60" maxlength="50"/>
			&nbsp;（提示：别名master为Java主库时，会自动创建日志LOG库及文件FILE存储库）
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">类别</td>
		<td align="left">
             <select id="discriminator" name="discriminator" >
			    <option value="">----请选择----</option>
				<option value="S">存储</option>
			    <option value="C">业主</option>
				<option value="J">监理</option>
				<option value="A">施工</option>
				<option value="O">其他</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("discriminator").value="${database.discriminator}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">实例类别</td>
		<td align="left">
             <select id="runType" name="runType" >
			    <option value="">----请选择----</option>
			    <option value="TPL">模板库</option>
				<option value="INST">实例库</option>
				<option value="TPL&INST">模板库&实例库</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("runType").value="${database.runType}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">数据库类型</td>
		<td align="left">
             <select id="type" name="type" onchange="javascript:changePort();">
			    <option value="">----请选择----</option>
				<option value="hbase">HBase</option>
			    <option value="mysql">MySQL</option>
				<option value="sqlserver">Microsoft SQL Server</option>
				<option value="mongodb">MongoDB</option>
				<option value="oracle">Oracle</option>
				<option value="postgresql">PostgreSQL</option>
				<option value="sqlite">SQLite</option>
				<option value="DM DBMS">DM DBMS</option>
             </select>&nbsp;*
             <script type="text/javascript">
                 document.getElementById("type").value="${database.type}";
             </script>
		</td>
	</tr>

	<tr>
		<td width="18%" align="left">使用目的</td>
		<td align="left">
             <select id="useType" name="useType" >
			    <option value="">----请选择----</option>
				<option value="GENERAL">通用库</option>
				<option value="FILE">文件存储库</option>
				<option value="LOG">日志库</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("useType").value="${database.useType}";
             </script>
		</td>
	</tr>

	<tr>
		<td width="18%" align="left">数据库级别</td>
		<td align="left">
             <select id="level" name="level">
			    <option value="1">主库</option>
				<option value="2">从库</option>
				<option value="3">日志</option>
				<option value="7">其他</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("level").value="${database.level}";
             </script>
		</td>
	</tr>

	<tr>
		<td width="18%" align="left">主机</td>
		<td align="left">
            <input id="host" name="host" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.host}"  size="60"/>&nbsp;*
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">端口</td>
		<td align="left">
			<input id="port" name="port" type="text" 
			       class="easyui-validatebox easyui-numberbox x-text" 
				   increment="1"  
				   value="${database.port}" size="5" maxlength="5"/>&nbsp;*
		    <label id="portLabel"></label>
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">库名</td>
		<td align="left">
            <input id="dbname" name="dbname" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.dbname}"  size="60"/>&nbsp;*
		   <br>（提示：初始化后不能修改库名！）
		</td>
	</tr>

	<tr>
		<td width="18%" align="left">用户名</td>
		<td align="left">
            <input id="user" name="user" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.user}" size="60"/>&nbsp;*
		</td>
	</tr>
	<tr>
		<td width="18%" align="left">密码</td>
		<td align="left">
            <input id="password" name="password" type="password" 
			       class="easyui-validatebox  x-text"  size="60"
				   <c:if test="${!empty database.password}"> value="88888888" </c:if>  />
		</td>
	</tr>

	<tr>
		<td width="18%" align="left">短类别码</td>
		<td align="left">
			<input id="intToken" name="intToken" type="text" 
			       class="easyui-validatebox easyui-numberbox x-text" 
				   increment="1"  
				   value="${database.intToken}" size="4" maxlength="4"/>&nbsp;*
		    <label id="intTokenLabel"></label>
			<span style="color:blue; margin-left:2px;">
			 （提示：大于1000，小于9999的整数且不与其他库重复，用于缓存、合成树等前缀识别。）
			</span>
		</td>
	</tr>

	<tr height="30">
		<td width="18%" align="left">系统编号</td>
		<td align="left">
            <input id="sysId" name="sysId" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.sysId}"  size="60"/>&nbsp;
		   <br>（提示：ISDP平台交换表定义的唯一系统编号。）
		</td>
	</tr>

  <c:if test="${!empty database.token}">
	<tr>
		<td width="18%" align="left">令牌</td>
		<td align="left">
			<input id="token" name="token" type="text" 
			       class="easyui-validatebox x-text"
				   value="${database.token}" size="60" maxlength="80" readonly />&nbsp;
		    <label id="tokenLabel"></label>
			<br>（提示：修改短类别码时可以同时修改令牌，提供给直接对外服务的应用做验证。）
		</td>
	</tr>
  </c:if>

	<!-- <tr height="30">
		<td width="18%" align="left">队列名称</td>
		<td align="left">
            <input id="queueName" name="queueName" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.queueName}"  size="60"/>&nbsp;
		   <br>（提示：MQ队列名称。）
		</td>
	</tr> -->

	<tr>
		<td width="18%" align="left">ISDP服务器</td>
		<td align="left">
            <input id="infoServer" name="infoServer" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.infoServer}"  size="60"/>&nbsp;
		   <br>（提示：ISDP服务器IP或域名。）
		</td>
	</tr>

	<tr>
		<td width="18%" align="left">登录ISDP账号</td>
		<td align="left">
            <input id="loginAs" name="loginAs" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.loginAs}"  size="60"/>&nbsp;
		   <br>（提示：以某个固定账号登录ISDP平台。）
		</td>
	</tr>

	<tr>
    <td width="18%" align="left">云众联接入地址&nbsp;</td>
    <td width="80%" align="left">
		<textarea  id="remoteUrl" name="remoteUrl" rows="6" cols="66" class="x-textarea" style="height:90px;width:445px;" 
		    >${database.remoteUrl}</textarea>
	    <span class="k-invalid-msg" data-for="remoteUrl"></span>
    </td>
   </tr>

	<tr height="30">
		<td width="18%" align="left">云众联账号</td>
		<td align="left">
            <input id="userNameKey" name="userNameKey" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.userNameKey}"  size="60"/>&nbsp;
		   <br>（提示：云众联账号，配置云众联时指定。）
		</td>
	</tr>

	<tr height="30">
		<td width="18%" align="left">云众联票据</td>
		<td align="left">
            <input id="ticket" name="ticket" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.ticket}"  size="60"/>&nbsp;
		   <br>（提示：云众联票据，配置云众联时指定。）
		</td>
	</tr>

   	<tr height="30">
		<td width="18%" align="left">应用程序ID</td>
		<td align="left">
            <input id="programId" name="programId" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.programId}"  size="60"/>&nbsp;
		   <br>（提示：应用程序ID，配置云众联时指定的程序编号。）
		</td>
	</tr>

	<tr height="30">
		<td width="18%" align="left">应用程序名称</td>
		<td align="left">
            <input id="programName" name="programName" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${database.programName}"  size="60"/>&nbsp;
		   <br>（提示：应用程序名称，配置云众联时指定的程序名称。）
		</td>
	</tr>

	<tr height="30">
		<td width="18%" align="left">是否启用</td>
		<td align="left">
		<input type="radio" name="active" value="1" <c:if test="${database.active == '1'}">checked</c:if>>启用&nbsp;&nbsp;
	    <input type="radio" name="active" value="0" <c:if test="${database.active == '0'}">checked</c:if>>禁用
		</td>
	</tr>
  
    <tr>
		<td width="18%" align="left">&nbsp;</td>
		<td align="left">
		  <br><br> 
		</td>
	</tr>
    
    <tr>
		<td width="18%" align="left">&nbsp;</td>
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