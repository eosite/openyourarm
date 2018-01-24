<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
</head>
<body>
 <table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title " height="25"> 
   <c:forEach items="${fields}" var="field">
    <td align="center">${field.fname}</td>
   </c:forEach>
  </tr>
  <c:forEach items="${dataList}" var="row">
  <tr height="23"> 
   <c:forEach items="${row.columns}" var="col">
    <td align="center">${col.value}</td>
   </c:forEach>
   </tr>
  </c:forEach>
 </table>
</body>
</html>