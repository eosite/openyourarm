<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    System.setProperty("jdbc_pool_type", "dbcp");
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    List<Database> databaseList = (List<Database>)request.getAttribute("databases");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据库列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
</head>
<body>
<% 
	if(databaseList != null && !databaseList.isEmpty()){
		boolean checkOK = false;
		long ts = 0;
		long start = System.currentTimeMillis();
		DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
        for(Database database: databaseList){
			checkOK = cfg.checkConnectionImmediately(database);
			ts = System.currentTimeMillis() -start;
			start = System.currentTimeMillis();
%> 
		<table width="95%" height="30" border="0" cellspacing="1" cellpadding="0" class="mainTable">
		  <tr>
			<td colspan="10" class="list-title">
			  <table width="100%" cellspacing="0" cellpadding="0">
			   <tr>
				<td align="left">
				    <b>数据库：
					<a href="<%=request.getContextPath()%>/mx/sys/database/edit?id=<%=database.getId()%>" 
					   target="_blank"><%=database.getTitle()%></a> [<%=database.getName()%>]</b>
					<%=DateUtils.getDateTime(new java.util.Date())%>&nbsp;状态：
					<%
					        if(checkOK){
	                            out.println("&nbsp;<font color='green'><b>连接成功</b></font>");
                            } else {
								out.println("&nbsp;<font color='red'><b>连接失败</b></font>");
							}
					%>
					&nbsp;用时（毫秒）：<%=ts%>
				</td>
			   </tr>
			  </table>
			</td>
		  </tr>
		 </table>
 <%    
        out.flush();
       }
  }
 %>
</body>
</html> 