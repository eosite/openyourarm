<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>各标段分部进度表</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
</head>
<body  style="margin:1px;">  
	<table border="0" cellspacing="1" cellpadding="0" class="list-box" style="width:100%;">
	  <tr class="list-title"> 
	    <td width="2%" align="center" rowspan="2">序<br/>号</td>
	    <td width="12%" align="center" rowspan="2">分部工程名称</td>
	    <td width="14%" align="center" rowspan="2">分项工程名称</td>
	    <td width="6%" align="center" colspan="2">本月启动计划</td>
	    <td width="10%" align="center" colspan="3">本月已填写</td>
	    <td width="10%" align="center" colspan="3">本月已报验</td>
	    <td width="10%" align="center" colspan="3">已形成文件</td>
	    <td width="6%" align="center" colspan="2">累计启动计划</td>
	    <td width="10%" align="center" colspan="3">累计已填写</td>
	    <td width="10%" align="center" colspan="3">累计已报验</td>
	    <td width="10%" align="center" colspan="3">累计形成文件</td>
	  </tr>
	  <tr class="list-title">
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  </tr>
	  
	  <c:set var="index"  value="1" />
	  <c:forEach items="${subSectionList }" var="subsection" varStatus="subsectionStatus">
	  		<c:forEach items="${subItemMap[subsection.indexId] }" var="subitem" varStatus="subitemStatus">
	  			<c:set var="treepinfoSum" value="${treepInfoSumMap[subitem.indexId] }" />
	  			<c:set var="newIndex" value="${index+newIndex }" />
	  			<c:set var="monthCell1Count" value="${monthCell1Count+treepinfoSum.monthCell1Sum }" />
	  			<c:set var="monthCell1PageCount" value="${monthCell1PageCount+treepinfoSum.monthCell1PageSum }" />
	  			<c:set var="monthCell2Count" value="${monthCell2Count+treepinfoSum.monthCell2Sum }" />
	  			<c:set var="monthCell2PageCount" value="${monthCell2PageCount+treepinfoSum.monthCell2PageSum }" />
	  			<c:set var="monthCell3Count" value="${monthCell3Count+treepinfoSum.monthCell3Sum }" />
	  			<c:set var="monthCell3PageCount" value="${monthCell3PageCount+treepinfoSum.monthCell3PageSum }" />
	  			<c:set var="monthIntpFile1Count" value="${monthIntpFile1Count+treepinfoSum.monthIntpFile1Sum }" />
	  			<c:set var="monthIntpFile1PageCount" value="${monthIntpFile1PageCount+treepinfoSum.monthIntpFile1PageSum }" />
	  			<c:set var="cell1Count" value="${cell1Count+treepinfoSum.cell1Sum }" />
	  			<c:set var="cell1PageCount" value="${cell1PageCount+treepinfoSum.cell1PageSum }" />
	  			<c:set var="cell2Count" value="${cell2Count+treepinfoSum.cell2Sum }" />
	  			<c:set var="cell2PageCount" value="${cell2PageCount+treepinfoSum.cell2PageSum }" />
	  			<c:set var="cell3Count" value="${cell3Count+treepinfoSum.cell3Sum }" />
	  			<c:set var="cell3PageCount" value="${cell3PageCount+treepinfoSum.cell3PageSum }" />
	  			<c:set var="intpFile1Count" value="${intpFile1Count+treepinfoSum.intpFile1Sum }" />
	  			<c:set var="intpFile1PageCount" value="${intpFile1PageCount+treepinfoSum.intpFile1PageSum }" />
	  			<tr>
		  			<td class="td-no">${newIndex }</td>
		  			<c:if test="${subitemStatus.first }">
		  				<td class="td-text" title="${subsection.indexName }" rowspan="${fn:length(subItemMap[subsection.indexId]) }">${subsection.indexName }</td>
		  			</c:if>
		  			<td class="td-text" title="${subitem.indexName }">${subitem.indexName }&nbsp;</td>
				    <td class="td-no">${treepinfoSum.monthCell1Sum }</td>
				    <td class="td-no">${treepinfoSum.monthCell1PageSum }</td>
				    <td class="td-no">${treepinfoSum.monthCell2Sum }</td>
				    <td class="td-no">${treepinfoSum.monthCell2PageSum }</td>
				    <c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthCell2Sum*100/treepinfoSum.monthCell1Sum }" />
				    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
				    <td class="td-no">${treepinfoSum.monthCell3Sum }</td>
				    <td class="td-no">${treepinfoSum.monthCell3PageSum }</td>
				    <c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthCell3Sum*100/treepinfoSum.monthCell1Sum }" />
				    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
				    <td class="td-no">${treepinfoSum.monthIntpFile1Sum }</td>
				    <td class="td-no">${treepinfoSum.monthIntpFile1PageSum }</td>
				    <c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthIntpFile1Sum*100/treepinfoSum.monthCell1Sum }" />
				    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
				    <td class="td-no">${treepinfoSum.cell1Sum }</td>
				    <td class="td-no">${treepinfoSum.cell1PageSum }</td>
				    <td class="td-no">${treepinfoSum.cell2Sum }</td>
				    <td class="td-no">${treepinfoSum.cell2PageSum }</td>
				    <c:set var="percent" value="${treepinfoSum.cell1Sum==0?0:treepinfoSum.cell2Sum*100/treepinfoSum.cell1Sum }" />
				    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
				    <td class="td-no">${treepinfoSum.cell3Sum }</td>
				    <td class="td-no">${treepinfoSum.cell3PageSum }</td>
				    <c:set var="percent" value="${treepinfoSum.cell1Sum==0?0:treepinfoSum.cell3Sum*100/treepinfoSum.cell1Sum }" />
				    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
				    <td class="td-no">${treepinfoSum.intpFile1Sum }</td>
				    <td class="td-no">${treepinfoSum.intpFile1PageSum }</td>
				    <c:set var="percent" value="${treepinfoSum.cell1Sum==0?0:treepinfoSum.intpFile1Sum*100/treepinfoSum.cell1Sum }" />
				    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
				  </tr>
	  		</c:forEach>
	  </c:forEach>
	  <tr >
	  
	  
	  <tr class="list-title">
	  	<td class="td-no">&nbsp;</td>
	  	<td class="td-no">&nbsp;</td>
	  	<td align="center" >合&nbsp;&nbsp;计</td>
	  	<td class="td-no">${monthCell1Count }</td>
	  	<td class="td-no">${monthCell1PageCount }</td>
	  	<td class="td-no">${monthCell2Count }</td>
	  	<td class="td-no">${monthCell2PageCount }</td>
	  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${monthCell1Count==0?0:monthCell2Count*100/monthCell1Count }"/></td>
	  	<td class="td-no">${monthCell3Count }</td>
	  	<td class="td-no">${monthCell3PageCount }</td>
	  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${monthCell1Count==0?0:monthCell3Count*100/monthCell1Count }"/></td>
	  	<td class="td-no">${monthIntpFile1Count }</td>
	  	<td class="td-no">${monthIntpFile1PageCount }</td>
	  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${monthCell1Count==0?0:monthIntpFile1Count*100/monthCell1Count }"/></td>
	  	<td class="td-no">${cell1Count }</td>
	  	<td class="td-no">${cell1PageCount }</td>
	  	<td class="td-no">${cell2Count }</td>
	  	<td class="td-no">${cell2PageCount }</td>
	  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${cell1Count==0?0:cell2Count*100/cell1Count }"/></td>
	  	<td class="td-no">${cell3Count }</td>
	  	<td class="td-no">${cell3PageCount }</td>
	  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${cell1Count==0?0:cell3Count*100/cell1Count }"/></td>
	  	<td class="td-no">${intpFile1Count }</td>
	  	<td class="td-no">${intpFile1PageCount }</td>
	  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${cell1Count==0?0:intpFile1Count*100/cell1Count }"/></td>
	  </tr>
	</table>
</body>
</html>