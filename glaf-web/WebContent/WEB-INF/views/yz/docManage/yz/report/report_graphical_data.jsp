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
	<c:forEach items="${danweiList }" var="danwei" varStatus="status">
		<table cellpadding='4' cellspacing='0' border='0' width='98%'>
			<c:set var="treepinfoSum" value="${TreepInfoSumMap[danwei.indexId] }" />
		
			<tr>
				<td width='100%' align='center'>
					<img src="${imageUrl }&indexId=${danwei.indexId}" />
				</td>
			</tr>
			<tr>
				<td>
					<table class='data' cellpadding='0' cellspacing='1' border='0' width='100%'>
						<tr height='20'>
							<td rowspan='2' align='center' width='50'>项次</td>
							<td rowspan='2' align='center'>单位工程名称</td>
							<td colspan='2' align='center'>本月启动计划</td>
							<td colspan='3' align='center'>本月已填写</td>
							<td colspan='2' align='center'>累计启动计划</td>
							<td colspan='3' align='center'>累计已填写</td>
						</tr>
						<tr height='20'>
							<td align='center' width='50'>份</td>
							<td align='center' width='50'>页</td>
							<td align='center' width='50'>份</td>
							<td align='center' width='50'>页</td>
							<td align='center' width='50'>%</td>
							<td align='center' width='50'>份</td>
							<td align='center' width='50'>页</td>
							<td align='center' width='50'>份</td>
							<td align='center' width='50'>页</td>
							<td align='center' width='50'>%</td>
						</tr>
						<tr height='20'>
							<td align='center'>${status.index+1 }</td>
							<td align='center'>${danwei.indexName }</td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.monthCell1Sum }" /></td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.monthCell1PageSum }" /></td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.monthCell2Sum }" /></td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.monthCell2PageSum }" /></td>
							<c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthCell2Sum*100/treepinfoSum.monthCell1Sum }" />
							<td align='center'><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.cell1Sum }" /></td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.cell1PageSum }" /></td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.cell2Sum }" /></td>
							<td align='center'><fmt:formatNumber pattern="#.##" value="${treepinfoSum.cell2PageSum }" /></td>
							<c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthCell2Sum*100/treepinfoSum.monthCell1Sum }" />
							<td align='center'><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</c:forEach>
</body>
</html>