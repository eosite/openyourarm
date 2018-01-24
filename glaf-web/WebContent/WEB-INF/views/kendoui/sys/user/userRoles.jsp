<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %> 
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	List<SysRole> roleList = (List<SysRole>)request.getAttribute("roles");
	SysUser user = (SysUser)request.getAttribute("user");
	Collection<SysRole> roles = user.getRoles();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门用户权限列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript">

	  function setUserRole(actorId, roleId){
		 var operation="invoke";
         if(!document.getElementById(actorId+"_"+roleId).checked ){
			 operation="revoke";
		 }
		 //alert(operation);
		 jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/branch/user/saveUserRole?actorId='+actorId+'&roleId='+roleId+'&operation='+operation,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					    
				   }
			 });
	}

</script>
</head>
<body>
<div id="main_content">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="用户角色设置">&nbsp;
用户角色设置</div>
<br>
<form method="post" action="<%=request.getContextPath()%>/mx/system/user/showUserRoles?actorId=${actorId}">
	<table width="98%" height="30" border="0" cellspacing="1" cellpadding="0" class="mainTable">
		<tr>
		    <td align="right">
			    <input type="text" id="nameLike" name="nameLike" class=" x-searchtext">&nbsp;
			    <input type="submit" value="查找" class="btnGray">
		    </td>
		</tr>	 
	</table>
</form>
<% 
	if(roleList != null && !roleList.isEmpty() ){
%> 
		<table width="100%" height="80" border="0" cellspacing="1" cellpadding="0" class="mainTable">
		  <tr>
			<td colspan="10" class="list-title">
			  <table width="100%" cellspacing="0" cellpadding="0">
			   <tr>
				<td align="left">
				<font color="#0066ff"><b>用户：<%=user.getName()%> [<%=user.getActorId()%>]</b></font>
				</td>
			   </tr>
			  </table>
			</td>
		  </tr>
		  <%
		  int size = roleList.size() / 4 ;
		  for(int j=0; j<=size; j++) { 
		  %>
		  <tr>
			<%
			for(int i=j*4; i<(j+1)*4; i++) { 
			  if(i<roleList.size()){
                 SysRole role = roleList.get(i);
				 String checked = "";
				 if(roles.contains(role)){
					 checked = "checked";
				 }
			%>
			<td class="weekend" align="left" height="30" width="180">
			  
			  <input type="checkbox" id="<%=user.getActorId()%>_<%=role.getId()%>" name="id" <%=checked%>
			  onclick="javascript:setUserRole('<%=user.getActorId()%>','<%=role.getId()%>')"  >
				<%				 				      
					out.println(role.getName());	
			        if(role.getCode() != null){
						out.println("&nbsp;["+role.getCode() +"]");	
					}
				%>
				
			</td>
			<%
			   } else{
			%>
			<td class="weekend" align="left" height="25" width="180">&nbsp;</td>
			<%  }
			
		    }%>
		  </tr>
		  <%}%>
		</table>
		<br>
<%   
 }
%>
</div> 
<br>
<br> 
</body>
</html>