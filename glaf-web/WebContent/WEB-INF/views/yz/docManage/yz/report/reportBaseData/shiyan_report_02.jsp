<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="com.glaf.chinaiss.model.CellRepInfo2"%>
<%@page import="com.glaf.core.util.RequestUtils"%>
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
<title>水泥混凝土抗压强度检验频率表</title>
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
	<c:if test="${dataFlag }">
		<table border="0" cellspacing="1" cellpadding="1" style="width:100%;">
		<tr class="list-title" align="center" style="height: 30px">
			<td width="30px">序号</td>
			<c:forEach items="${columns }" var="cellRepInfo2">
				<c:if test="${cellRepInfo2.type!=22 }">
					<td>${cellRepInfo2.content }</td>
				</c:if>
			</c:forEach>
		</tr>
		
		<c:forEach items="${pager.results }" var="data" varStatus="dataStatus">
				<tr>
					<td class="td-no">${(pager.currentPageNo-1)*pager.pageSize+dataStatus.index+1 }</td>
					<c:forEach items="${columns }" var="cellRepInfo2">
						<c:if test="${cellRepInfo2.type!=22 }">
							<c:set var="colName" value="${cellRepInfo2.formula }" />
							
							<c:if test="${cellRepInfo2.dtype=='datetime' }">
								<td class="td-no"><fmt:formatDate value="${data[colName] }" pattern="yyyy-MM-dd"/></td>
							</c:if>
							
							<c:if test="${cellRepInfo2.dtype!='datetime' }">
								<td class="td-no">${data[colName] }</td>
							</c:if>
							
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		<table border="0" cellspacing="1" cellpadding="1" style="width:100%;background-color: #FFF;">
			<tr><td align="right">
			<jsp:include page="/WEB-INF/views/inc/show_page_js.jsp" flush="true"> 
			    <jsp:param name="total" value="${pager.totalRecordCount}"/>
			    <jsp:param name="page_count" value="${pager.totalPageCount}"/>
			    <jsp:param name="page_size" value="${pager.pageSize}"/>
			    <jsp:param name="page_no" value="${pager.currentPageNo}"/>
			    <jsp:param name="url" value=""/>
				<jsp:param name="params" value=""/>        
				<jsp:param name="func" value="gotoPage"/>        
			</jsp:include> 
			</td></tr>
		</table>
	</c:if>
	<c:if test="${!dataFlag }">
		<div style="margin:10px;">不存在名称为【水泥混凝土抗压强度统计汇总表】的模板，请先定义模板</div>
	</c:if>
</body>
</html>