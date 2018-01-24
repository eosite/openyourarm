<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
	String context = request.getContextPath();
	SysUser bean=(SysUser)request.getAttribute("bean");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用密锁</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript">
function resetUserToken(){
    if(confirm("确定要重置“${bean.name}”的访问密锁吗？")){
	    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/identity/user/token/resetSecret',
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
					    window.location.reload();
				   }
			 });
	}
}
 
</script>
</head>
<body>
<div class="nav-title"><span class="Title">应用密锁</span></div>
<center>
<html:form id="iForm" name="iForm" method="post" > 
<input type="hidden" name="actorId" value="<%=bean.getActorId()%>"> 
<table width="85%" align="center" border="0" cellspacing="0" cellpadding="5">
	  <tr>
        <td width="25%" class="input-box">用户名</td>
        <td width="75%">${bean.account}</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">姓 名</td>
        <td>${bean.name}</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">服务器URL</td>
        <td><%=com.glaf.core.util.RequestUtils.getServiceUrl(request)%></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">AppId</td>
        <td>${bean.appId}</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">AppSecret</td>
        <td>${bean.appSecret}</td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
           <br><input name="btn_save2" type="button" value="重置应用密锁" class="btnGray" onclick="javascript:resetUserToken();">
		</td>
      </tr>
</table>
</html:form>
</center>
</body>
</html>
