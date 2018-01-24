<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审核意见</title>
<style>

.subject { font-size: 13px;  text-decoration: none; font-weight:normal; font-family:"宋体"}
.table-border { background-color:#ccccff; height: 32px; font-family:"宋体"}
.table-content { background-color:#ffffff; height: 32px;font-size: 12px; font-family:"宋体"}

</style>
</head>
<body>
<div style="margin:0;"></div>  
    <c:forEach var="comment" items="${comments}" >
      <table class="table-border" cellspacing="1" cellpadding="4" width="98%" nowrap>
		<tr align="left">
			<td class="table-content" width="30%">
			<span class="subject">审核人</span> ${comment.username}
			</td>
			<td class="table-content" width="30%">
			<span class="subject">审核时间</span> ${comment.approvalDate}  
			</td>
			<td class="table-content" width="40%">
			<span class="subject">是否通过</span>
			<c:choose>
			<c:when test="${comment.approval == 1}">  
			<span style="color:green;font-weight:bold;">通过</span>
			</c:when>
			<c:otherwise>
			<span style="color:red;font-weight:bold;">不通过</span>
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<c:if test="${!empty comment.content}">
		<tr align="left">
			<td class="table-content" colspan="3">
			<span class="subject">审核意见</span>
			<br><br>&nbsp;&nbsp;&nbsp;&nbsp;${comment.content}     
			<br><br>&nbsp;
			</td>
		</tr>
		</c:if>
	  </table>
	  <br>
	</c:forEach> 
</body>
</html>