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
			<td width="80px">单位</td>
			<td width="90px">分部</td>
			<td width="120px">分项</td>
			<td width="120px">报验结构</td>
			<td width="120px">报验单元名称</td>
			<td width="25px">份数</td>
			<td width="25px">页数</td>
			<td width="100px">流程名称</td>
			<td width="60px">状态</td>
			<td width="60px">责任人</td>
			<td width="60px">上一流程<br/>责任人</td>
			<td width="60px">报验状态</td>
			<td width="60px">报验时间</td>
			<td width="60px">完成时间</td>
			<!-- <td>处理报验时间</td>
			<td>状态</td>
			<td>责任人</td>
			<td>监理已审批时间</td> -->
		</tr>
		
		<c:forEach items="${needCellList }" var="needCell" varStatus="needCellStatus">
			<c:set var="byjg" value="${allBaseDataMap[needCell.parentId] }" />
			<c:set var="fenxiang" value="${allBaseDataMap[needCell.subItemId] }" />
			<c:set var="fenbu" value="${allBaseDataMap[needCell.subSectionId] }" />
			<c:set var="unit" value="${allBaseDataMap[needCell.unitId] }" />
			<c:set var="flowinfoList" value="${flowInfoMap[needCell.id] }" />
			<c:if test="${not empty flowinfoList }">
			<c:forEach items="${flowinfoList }" var="flowinfo" varStatus="flowinfoStatus">
			<tr>
				<c:if test="${flowinfoStatus.first }">
					<td class="td-no" rowspan="${fn:length(flowinfoList) }">${(pager.currentPageNo-1)*pager.pageSize+needCellStatus.index+1 }</td>
					<td class="td-text" rowspan="${fn:length(flowinfoList) }" title="${unit.treepinfoIndexName }">${unit.treepinfoIndexName }</td>
					<td class="" rowspan="${fn:length(flowinfoList) }" title="${fenbu.treepinfoIndexName }">${fenbu.treepinfoIndexName }</td>
					<td class="" rowspan="${fn:length(flowinfoList) }" title="${fenxiang.treepinfoIndexName }">${fenxiang.treepinfoIndexName }</td>
					<c:if test="${needCell.parentId!=needCell.subItemId }">
					<td class="" rowspan="${fn:length(flowinfoList) }" title="${byjg.treepinfoIndexName }">${byjg.treepinfoIndexName }</td>
					</c:if>
					<c:if test="${needCell.parentId==needCell.subItemId }">
					<td class="" rowspan="${fn:length(flowinfoList) }">&nbsp;</td>
					</c:if>
					<td class="" rowspan="${fn:length(flowinfoList) }" title="${needCell.treepinfoIndexName }">${needCell.treepinfoIndexName }</td>
					<td class="td-no" rowspan="${fn:length(flowinfoList) }">${needCell.intpFile1 }</td>
					<td class="td-no" rowspan="${fn:length(flowinfoList) }">${needCell.cell1 }</td>
					
					<td class="td-text" title="${flowinfo.flowName }">${flowinfo.taskName}</td>
					<td class="td-text" title="${flowinfo.flowStatus }">${flowinfo.flowStatus }</td>
					<td class="td-text">&nbsp;</td>
					<td class="td-text" title="${userMap[flowinfo.flowPreUserId].name }">${userMap[flowinfo.flowPreUserId].name }</td>
					
					<td class="td-no" rowspan="${fn:length(flowinfoList) }">${needCell.inspecStatus }</td>
					<td class="td-no" rowspan="${fn:length(flowinfoList) }"><fmt:formatDate value="${needCell.inspecTime }" pattern="yyyy-MM-dd" timeZone="GMT+8"/></td>
					<td class="td-no" rowspan="${fn:length(flowinfoList) }"><fmt:formatDate value="${needCell.finishTime }" pattern="yyyy-MM-dd" timeZone="GMT+8"/></td>
				</c:if>
				
				<c:if test="${!flowinfoStatus.first }">
					<td class="td-text" title="${flowinfo.flowName }">${flowinfo.taskName}</td>
					<td class="td-text" title="${flowinfo.flowStatus }">${flowinfo.flowStatus }</td>
					<td class="td-text">&nbsp;</td>
					<td class="td-text" title="${userMap[flowinfo.flowPreUserId].name }">${userMap[flowinfo.flowPreUserId].name }</td>
				</c:if>
				
			</tr>
			</c:forEach>
			</c:if>
			
			<c:if test="${empty flowinfoList }">
				<tr>
				<td class="td-no">${(pager.currentPageNo-1)*pager.pageSize+needCellStatus.index+1 }</td>
				<td class="td-text" title="${unit.treepinfoIndexName }">${unit.treepinfoIndexName }</td>
				<td class="" title="${fenbu.treepinfoIndexName }">${fenbu.treepinfoIndexName }</td>
				<td class="" title="${fenxiang.treepinfoIndexName }">${fenxiang.treepinfoIndexName }</td>
				<td class="" title="${byjg.treepinfoIndexName }">${byjg.treepinfoIndexName }</td>
				<td class="" title="${needCell.treepinfoIndexName }">${needCell.treepinfoIndexName }</td>
				<td class="td-no">${needCell.intpFile1 }</td>
				<td class="td-no">${needCell.cell1 }</td>
				<td class="td-text" >&nbsp;</td>
				<td class="td-text" >&nbsp;</td>
				<td class="td-text">&nbsp;</td>
				<td class="td-text" >&nbsp;</td>
				<td class="td-no" rowspan="${fn:length(flowinfoList) }">${needCell.inspecStatus }</td>
				<td class="td-no" rowspan="${fn:length(flowinfoList) }"><fmt:formatDate value="${needCell.inspecTime }" pattern="yyyy-MM-dd" timeZone="GMT+8"/></td>
					<td class="td-no" rowspan="${fn:length(flowinfoList) }"><fmt:formatDate value="${needCell.finishTime }" pattern="yyyy-MM-dd" timeZone="GMT+8"/></td>
				</tr>
			</c:if>
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