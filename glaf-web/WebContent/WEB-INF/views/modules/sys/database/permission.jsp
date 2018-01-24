<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %> 
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户权限列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript">

     function saveAccessor(actorId, databaseId){
		 var operation="invoke";
         if(!document.getElementById(actorId+"_"+databaseId).checked ){
			 operation="revoke";
		 }
		 //alert(operation);
		 jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/database/saveAccessor?actorId='+actorId+'&databaseId='+databaseId+'&operation='+operation,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					    
				   }
			 });
	}

	function switchView(){
		<c:choose>
		   <c:when test="${op_view eq 'database'}">
		       location.href="<%=request.getContextPath()%>/mx/sys/database/permission?op_view=user";
		   </c:when>
		   <c:otherwise>
			   location.href="<%=request.getContextPath()%>/mx/sys/database/permission?op_view=database";
		   </c:otherwise>
		</c:choose>
	}

function searchDatabases(){
      document.iForm.action="<%=request.getContextPath()%>/mx/sys/database/permission?op_view=database";
	  document.iForm.submit();
}

function searchUsers(){
      document.iForm.action="<%=request.getContextPath()%>/mx/sys/database/permission?op_view=user";
	  document.iForm.submit();
}

</script>
</head>
<body leftmargin="0" topmargin="0">  
<center>
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="用户权限设置">&nbsp;用户权限设置</div>
<form id="iForm" name="iForm" method="post" action="">
<table width="95%" height="30" border="0" cellspacing="1" cellpadding="0" class="mainTable">
  <tr>
    <td width="85%" align="right">
		<input type="text" id="nameLike" name="nameLike" class=" x-searchtext">&nbsp;
	    
		<c:choose>
         <c:when test="${op_view eq 'database'}">
	      <input type="button" value="查找" class="btnGray" onclick="javascript:searchDatabases();">
		 </c:when>
		 <c:otherwise>
		  <input type="button" value="查找" class="btnGray" onclick="javascript:searchUsers();">
		 </c:otherwise>
		</c:choose>
	</td>
	<td width="15%" align="right">
	   <c:choose>
         <c:when test="${op_view eq 'database'}">
	      <input type="button" class="btnGray" value="切换用户视图" onclick="javascript:switchView();">
		 </c:when>
		 <c:otherwise>
		  <input type="button" class="btnGray" value="切换数据库视图" onclick="javascript:switchView();">
		 </c:otherwise>
		</c:choose>
	</td>
  </tr>
</table>
</form>
<% 
    List<Database> databaseList = (List<Database>)request.getAttribute("databaseList");
	List<User> userList = (List<User>)request.getAttribute("users");
	 
%> 
<c:choose>
  <c:when test="${op_view eq 'database'}">
<% 
	if(databaseList != null && !databaseList.isEmpty() && userList != null && !userList.isEmpty()){
        for(Database database: databaseList){
%> 
		<table width="95%" height="80" border="0" cellspacing="1" cellpadding="0" class="mainTable">
		  <tr>
			<td colspan="10" class="list-title">
			  <table width="100%" cellspacing="0" cellpadding="0">
			   <tr>
				<td align="left">
				<font color="#0066ff"><b>数据库：<%=database.getTitle()%> [<%=database.getName()%>]</b></font>
				</td>
			   </tr>
			  </table>
			</td>
		  </tr>
		  <%
		  int size = userList.size() / 4 ;
		  for(int j=0; j<=size; j++) { 
		  %>
		  <tr>
			<%
			for(int i=j*4; i<(j+1)*4; i++) { 
			  if(i<userList.size()){
                 User user = userList.get(i);
				 String checked = "";
				 if(user.getRowKeys().contains(database.getId()+"")){
					 checked = "checked";
				 }
			%>
			<td class="weekend" align="left" height="25" width="180">
			  
			  <input type="checkbox" id="<%=user.getActorId()%>_<%=database.getId()%>" name="id" <%=checked%>
			  onclick="javascript:saveAccessor('<%=user.getActorId()%>','<%=database.getId()%>')"  >
				<%				 				      
					out.println(user.getName()+"&nbsp;["+user.getActorId()+"]");	    
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
<%   }
 }
%>
  </c:when>
  <c:otherwise>

<% 
	if(databaseList != null && !databaseList.isEmpty() && userList != null && !userList.isEmpty()){
        for(User user: userList){
%> 
		<table width="95%" height="80" border="0" cellspacing="1" cellpadding="0" class="mainTable">
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
		  int size = databaseList.size() / 4 ;
		  for(int j=0; j<=size; j++) { 
		  %>
		  <tr>
			<%
			for(int i=j*4; i<(j+1)*4; i++) { 
			  if(i<databaseList.size()){
                 Database database = databaseList.get(i);
				 String checked = "";
				 if(user.getRowKeys().contains(database.getId()+"")){
					 checked = "checked";
				 }
			%>
			<td class="weekend" align="left" height="25" width="180">
			  
			  <input type="checkbox" id="<%=user.getActorId()%>_<%=database.getId()%>" name="id" <%=checked%>
			  onclick="javascript:saveAccessor('<%=user.getActorId()%>','<%=database.getId()%>')"  >
				<%				 				      
					out.println(database.getTitle()+"&nbsp;["+database.getName()+"]");	    
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
<%   }
 }
%>

  </c:otherwise>
</c:choose>
<br>
<br>
<br>
<br>
</center>
</body>  
</html>