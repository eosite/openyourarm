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
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{font-size: 12px}
	body table{background-color: #000;}
	body table tr td{background-color: #FFF;}
</style>
<script type="text/javascript">
	jQuery(function(){
		
	});
</script>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div>  
	<table border="0" cellspacing="1" cellpadding="1" class="list-box" style="width:100%;">
		<tr class="list-title" align="center">
			<td rowspan="2">项次</td>
			<td rowspan="2" width="100px">合同号</td>
			<td colspan="2">本月启动计划</td>
			<td colspan="3">本月已填写</td>
			<td colspan="3">本月已报验</td>
			<td colspan="3">已形成文件</td>
			<!-- <td colspan="2">累计启动计划</td>
			<td colspan="3">累计已填写</td>
			<td colspan="3">累计已报验</td>
			<td colspan="3">累计形成文件</td> -->
		</tr>
		<tr class="list-title" align="center">
			<td>份</td>
			<td>页</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
			<!-- <td>份</td>
			<td>页</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
			<td>份</td>
			<td>页</td>
			<td>%</td> -->
		</tr>
		
		<c:set var="index" value="0"/>
		<c:forEach items="${muiProjList }" var="muiProj" varStatus="muiProjStatus">
			<c:set var="index" value="${index+1 }" />
			<c:set var="treepinfoSum" value="${resultMap[muiProj.indexId] }" />
  			<tr>
  				<td class="td-no">${index }</td>
  				<td class="td-text" title="${muiProj.projName }">${muiProj.projName }</td>
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
			    <%-- <td class="td-no">${treepinfoSum.cell1Sum }</td>
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
			    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td> --%>
  			</tr>
		</c:forEach>
	</table>
</body>
</html>