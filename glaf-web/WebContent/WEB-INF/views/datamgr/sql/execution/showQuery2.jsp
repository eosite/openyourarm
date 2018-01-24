<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.jdbc.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ page import="org.apache.commons.lang3.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String sql = request.getParameter("sql");
	String actionType = request.getParameter("actionType");
	if(sql == null){
		sql="";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>执行SQL语句</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.base64.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap-theme.min.css" />
<script type="text/javascript">

    function doQuery(actionType){
		var databaseId = document.getElementById("databaseId").value;
		var sql = document.getElementById("sql").value;
        //location.href="<%=request.getContextPath()%>/mx/datamgr/sql/execution/showQuery?databaseId="+databaseId+"&sql="+sql+"&//actionType="+actionType;
        document.iForm.submit();
    }
	 
</script>
</head>
<body>
<form method="post" name="iForm" action="<%=request.getContextPath()%>/mx/datamgr/sql/execution/showQuery">
<table style="width:95%;height:250px" align="center">
<tbody>
<tr>
<td height="40">
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="实时查询"> &nbsp;实时查询
</div>
</td>
</tr>
<tr>
 <td>
    <textarea id="sql" name="sql" rows="12" cols="88"  class="x-textarea" style="width:1050px;height:320px;"><%=sql%></textarea>
<td>
</tr>
<tr>
 <td height="40">
	&nbsp;切换数据库&nbsp;
	<select id="databaseId" name="databaseId" onchange="javascript:doQuery('');">
	<option value="">----请选择----</option>    
	<c:forEach items="${databases}" var="item">
	<option value="${item.id}">${item.title}</option>     
	</c:forEach>
	</select>
	<script type="text/javascript">
		 document.getElementById("databaseId").value="${databaseId}";
	</script>
    &nbsp;&nbsp;
    <input type="button" value="确定" class="btnGray" onclick="javascript:doQuery('query');"> 
	&nbsp;&nbsp;(提示：本查询最多返回5000条记录，为减少网络流量，请尽量缩小查询范围。)
 <td>
</tr>
</tbody>
</table>
</form>
<br>
<%
    Database currentDB = (Database)request.getAttribute("currentDB");
	List<ColumnDefinition> columns = (List<ColumnDefinition>)request.getAttribute("columns");
	if(columns != null && !columns.isEmpty()){
%>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" 
       class="table table-striped table-bordered table-condensed">
	<tr>
	   <td>序号</td>
	   <% for(ColumnDefinition column: columns){%>
		<td>
		     <%=column.getColumnLabel()%>
		</td>
		<%}%>
	</tr>

	 <%if(currentDB != null){
		try {
			com.glaf.core.config.Environment.setCurrentSystemName(currentDB.getName());
			ITablePageService tablePageService = ContextFactory.getBean("tablePageService");
 
            Map<String, Object> parameters = new HashMap<String, Object>();
		 
		    List rows = null;
			if(StringUtils.containsAny(sql.toLowerCase(), " count(*) ")){
				rows = tablePageService.getListData(sql, parameters);
			} else {
				rows = tablePageService.getListData(sql, parameters, 1, 5000);
			}
			
			if(rows != null && !rows.isEmpty()){
				int index = 1;
 				Iterator iterator = rows.iterator();
				while(iterator.hasNext() && index <=5000){
					 Map rowMap = (Map)iterator.next();
					 rowMap.remove("password");
					 rowMap.remove("PASSWORD");
					 rowMap.remove("password_");
					 rowMap.remove("PASSWORD_");
					 rowMap.remove("key");
					 rowMap.remove("KEY_");
					 rowMap.remove("sPassword");
					 out.println("<tr class='x-content'>");
					 out.println("\n<td>"+index+++"</td>");
					 for(ColumnDefinition column: columns){
						String value = "";
						String javaType = column.getJavaType();
						Object object =  ParamUtils.getString(rowMap, column.getColumnLabel());
                        if(object != null){
							  if(object instanceof Date){
								  Date date = (Date)object;
								  value = DateUtils.getDate(date);
							  } else{
								  value = object.toString();
							  }
						}
%>      
        <td><%=value%></td>
<%   
				  }
				  out.println("</tr>");
			   }
			 }
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally{
			com.glaf.core.config.Environment.removeCurrentSystemName();
		}
}
%>
</table>
<%}
%> 

</body>
</html>