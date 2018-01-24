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
<title>实际进度报表</title>
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
			<td width="80px">单位</td>
			<td width="90px">分部</td>
			<td width="110px">分项</td>
			<td width="110px">报验结构</td>
			<td width="110px">报验单元名称</td>
			<td width="60px">状态</td>
			<td width="60px">完成时间</td>
		</tr>
		
		<c:forEach items="${pager.results }" var="actualReport" varStatus="indexStatus">
			<c:set var="byjg" value="${allBaseDataMap[actualReport.parentId] }" />
			<c:set var="fenxiang" value="${allBaseDataMap[actualReport.subItemId] }" />
			<c:set var="fenbu" value="${allBaseDataMap[actualReport.subSectionId] }" />
			<c:set var="unit" value="${allBaseDataMap[actualReport.unitId] }" />
			<tr>	
				<td class="td-no">${(pager.currentPageNo-1)*pager.pageSize+indexStatus.index+1 }</td>	
				<td class="td-text" title="${unit.treepinfoIndexName }">${unit.treepinfoIndexName }</td>
				<td class="" title="${fenbu.treepinfoIndexName }">${fenbu.treepinfoIndexName }</td>
				<td class="" title="${fenxiang.treepinfoIndexName }">${fenxiang.treepinfoIndexName }</td>
				<c:if test="${actualReport.parentId!=actualReport.subItemId }">
				<td class="" title="${byjg.treepinfoIndexName }">${byjg.treepinfoIndexName }</td>
				</c:if>
				<c:if test="${actualReport.parentId==actualReport.subItemId }">
				<td class="">&nbsp;</td>
				</c:if>
				<td class="" title="${actualReport.treepinfoIndexName }">${actualReport.treepinfoIndexName }</td>	
				<td class="td-no">${actualReport.finishStatus }</td>
				<td class="td-no"><fmt:formatDate value="${actualReport.finishTime }" pattern="yyyy-MM-dd" timeZone="GMT+8"/></td>
			</tr>
		</c:forEach>
	</table>
	
	<table border="0" cellspacing="1" cellpadding="1" style="width:100%;background-color: #FFF;">
		<tr><td align="right">
		<jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
		    <jsp:param name="total" value="${pager.totalRecordCount}"/>
		    <jsp:param name="page_count" value="${pager.totalPageCount}"/>
		    <jsp:param name="page_size" value="${pager.pageSize}"/>
		    <jsp:param name="page_no" value="${pager.currentPageNo}"/>
		    <jsp:param name="url" value='<%=request.getContextPath()+"/mx/docManage/yz/report/reportBaseData" %>'/>
			<jsp:param name="params" value="<%=WebUtil.getQueryString(request) %>"/>       
		</jsp:include>
		</td></tr>
	</table>	
</body>
</html>