<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.jcraft.jsch.Session"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    List<Database> databaseList = (List<Database>)request.getAttribute("databases");
	List<ServerEntity> servers = (List<ServerEntity>)request.getAttribute("servers");
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
	if(databaseList != null && !databaseList.isEmpty() && servers != null && !servers.isEmpty()){
		boolean addOK = false;
		String message = null;
		Session sess = null;
        for(Database database: databaseList){
			if(org.apache.commons.lang3.StringUtils.isEmpty(database.getSysId())){
				continue;
			}
            addOK = false;
			message = null;
			String password = SecurityUtils.decode(database.getKey(), database.getPassword());
            password = DigestUtils.sha1Hex(password);
 
			for(ServerEntity server: servers){
				if(org.apache.commons.lang3.StringUtils.endsWith(server.getProgram(), "rabbitmqctl")){
					String host = server.getHost();
					int port = server.getPort() > 0 ? server.getPort() : 22;
					String user = server.getUser();
					String pwd = server.getPassword();
					pwd = SecurityUtils.decode(server.getKey(), server.getPassword());
					try{
					    sess = ShellUtils.openSession(host, port, user, pwd);
					    boolean result = ShellUtils.execCmd(sess, server.getProgram()+" add_user " + database.getSysId() 
							                               +" " +password);
                        if(!result){
                            ShellUtils.execCmd(sess, server.getProgram()+" change_password " + database.getSysId()
							                   +" "+ password);
					    }
                        ShellUtils.execCmd(sess, server.getProgram()+" set_permissions  -p  \"/\" " + database.getSysId() +
						" \".*\"  \".*\"  \".*\" ");
						addOK = true;
				     } catch(Exception ex){
						 addOK = false;
                         message = ex.getMessage();
					 } finally {
                        if(sess != null){
							sess.disconnect();
						}
					 }
			     }
			}
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
					 状态：<%
					        if(addOK){
	                            out.println("<font color='green'>添加MQ用户成功</font>");
                            } else {
								out.println("<font color='red'>添加MQ用户失败</font>");
								if(message != null){
								    out.println("<br><font color='red'>"+message+"</font>");
								}
							}
					       %>
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