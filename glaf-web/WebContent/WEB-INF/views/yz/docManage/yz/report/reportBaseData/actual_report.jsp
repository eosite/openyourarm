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
			<td width="60px">未启动</td>
			<td width="60px">未完成</td>
			<td width="60px">已完成</td>
		</tr>
		
		<c:forEach items="${pager.results }" var="actualReport" varStatus="indexStatus">
			<c:set var="fenxiang" value="${allBaseDataMap[actualReport.subItemId] }" />
			<c:set var="fenbu" value="${allBaseDataMap[actualReport.subSectionId] }" />
			<c:set var="unit" value="${allBaseDataMap[actualReport.unitId] }" />
			<tr>	
				<td class="td-no">${(pager.currentPageNo-1)*pager.pageSize+indexStatus.index+1 }</td>	
				<td class="td-text" title="${unit.treepinfoIndexName }">${unit.treepinfoIndexName }</td>
				<td class="" title="${fenbu.treepinfoIndexName }">${fenbu.treepinfoIndexName }</td>
				<td class="" title="${fenxiang.treepinfoIndexName }">${fenxiang.treepinfoIndexName }</td>
				<td class="td-no"><a href="javascript:showDetail(0,'${fenxiang.treepinfoIndexId }');">${statusCount[fenxiang.treepinfoIndexId].unStartCount }</a></td>
				<td class="td-no"><a href="javascript:showDetail(1,'${fenxiang.treepinfoIndexId }');">${statusCount[fenxiang.treepinfoIndexId].unFinishCount }</a></td>
				<td class="td-no"><a href="javascript:showDetail(2,'${fenxiang.treepinfoIndexId }');">${statusCount[fenxiang.treepinfoIndexId].finishCount }</a></td>
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
</body>
</html>