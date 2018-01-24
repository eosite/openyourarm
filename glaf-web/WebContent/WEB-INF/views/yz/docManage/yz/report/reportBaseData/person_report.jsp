<%@page import="com.glaf.base.utils.WebUtil"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表生成</title>
<%@include file="../../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{font-size: 12px}
	body table{background-color: #000;}
	body table tr td{background-color: #FFF;}
</style>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div> 
	<table border="0" cellspacing="1" cellpadding="1" style="width:100%;">
		<tr class="list-title" align="center" style="height: 30px">
			<td width="25px">序号</td>
			<td width="80px">用户名</td>
			<td width="80px">姓名</td>
			<td width="80px">角色</td>
			<td width="80px">应完成数量（角色）</td>
			<td width="80px">应完成数量（用户）</td>
			<td width="80px">已完成数量（用户）</td>
			<td width="80px">完成率</td>
			<td width="80px">待办数量</td>
			<td width="80px">待办率</td>
		</tr>
		
		<c:forEach items="${personReportList }" var="personReport" varStatus="indexStatus">
			<tr>
				<td class="td-no">${indexStatus.index+1 }</td>
				<td class="td-text">${personReport.userId }</td>
				<td class="td-text">${personReport.userName }</td>
				<td class="td-text">${roleMap[personReport.netRoleId].name }</td>
				<td class="td-no"><a href="javascript:showDetail('0','${personReport.netRoleId }','${personReport.userId_enc }')">${personReport.shouldFinishNum }</a></td>
				<td class="td-no"><a href="javascript:showDetail('1','${personReport.netRoleId }','${personReport.userId_enc }')">${personReport.finishNum+personReport.notFinishNum}</a></td>
				<td class="td-no"><a href="javascript:showDetail('2','${personReport.netRoleId }','${personReport.userId_enc }')">${personReport.finishNum }</a></td>
				
				<c:if test="${personReport.shouldFinishNum!=null && personReport.finishNum/personReport.shouldFinishNum>0 }">
					<td class="td-no"><fmt:formatNumber pattern="0.00%" value="${personReport.finishNum/(personReport.finishNum+personReport.notFinishNum) }" /></td>
				</c:if>
				
				<c:if test="${personReport.shouldFinishNum==null || personReport.finishNum/personReport.shouldFinishNum==0 }">
					<td class="td-no">&nbsp;</td>
				</c:if>
				
				<td class="td-no"><a href="javascript:showDetail('3','${personReport.netRoleId }','${personReport.userId_enc }')">${personReport.notFinishNum }</a></td>
				
				<c:if test="${personReport.shouldFinishNum!=null && personReport.notFinishNum/personReport.shouldFinishNum>0 }">
					<td class="td-no"><fmt:formatNumber pattern="0.00%" value="${personReport.notFinishNum/personReport.shouldFinishNum }" /></td>
				</c:if>
				
				<c:if test="${personReport.shouldFinishNum==null || personReport.notFinishNum/personReport.shouldFinishNum==0 }">
					<td class="td-no">&nbsp;</td>
				</c:if>
			</tr>
		</c:forEach>
		
	</table>
</body>
</html>