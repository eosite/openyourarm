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
<script type="text/javascript">
	
</script>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div> 
	<table border="0" cellspacing="1" cellpadding="1" style="width:100%;">
		<tr class="list-title" align="center" style="height: 30px">
			<td width="25px">序号</td>
			<td width="80px">单位工程名称</td>
			<td width="80px">应完成数量（份/页）</td>
			<td width="80px">已填写数量（页）</td>
			<td width="80px">已完成数量（份）</td>
			<td width="80px">完成率</td>
			<td width="80px">待办数量（份）</td>
			<td width="80px">待办率</td>
		</tr>
		
		<c:forEach items="${unitReportList }" var="unitReport" varStatus="indexStatus">
			<tr>
				<td class="td-no">${indexStatus.index+1 }</td>
				<td class="td-text">${unitMap[unitReport.unitId].indexName }</td>
				<c:if test="${unitReport.intpFile1!=null }">
				<td class="td-no">${unitReport.intpFile1 }/${unitReport.cell1 }</td>
				</c:if>
				<c:if test="${unitReport.intpFile1==null }">
				<td class="td-no">&nbsp;</td>
				</c:if>
				<td class="td-no">${unitReport.cell2 }</td>
				<td class="td-no">${unitReport.intpFile2 }</td>
				
				<c:if test="${unitReport.intpFile1!=null && unitReport.intpFile1!=0 && unitReport.intpFile2/unitReport.intpFile1>0 }">
					<td class="td-no"><fmt:formatNumber pattern="0.00%" value="${unitReport.intpFile2/unitReport.intpFile1 }" /></td>
				</c:if>
				
				<c:if test="${unitReport.intpFile1==null || unitReport.intpFile1==0 || unitReport.intpFile2/unitReport.intpFile1==0 }">
					<td class="td-no">&nbsp;</td>
				</c:if>
				
				<td class="td-no">${unitReport.intpFile3 }</td>
				
				<c:if test="${unitReport.intpFile1!=null && unitReport.intpFile1!=0 && unitReport.intpFile3/unitReport.intpFile1>0 }">
					<td class="td-no"><fmt:formatNumber pattern="0.00%" value="${unitReport.intpFile3/unitReport.intpFile1 }" /></td>
				</c:if>
				
				<c:if test="${unitReport.intpFile1==null || unitReport.intpFile1==0 || unitReport.intpFile3/unitReport.intpFile1==0 }">
					<td class="td-no">&nbsp;</td>
				</c:if>
			</tr>
		</c:forEach>
		
	</table>
</body>
</html>