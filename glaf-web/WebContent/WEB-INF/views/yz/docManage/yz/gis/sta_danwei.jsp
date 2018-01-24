<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	table{background-color:#CCC;}
	table tr{background-color:#FFF;}
</style>
<script type="text/javascript">

</script>
</head>
<body style="margin: 1px">  
    <table cellpadding="3" cellspacing="1" border="0" style="width: 100%;text-align: center;" >
    	<tr>
    		<td colspan="7">单位形象进度统计结果</td>
    	</tr>
    	<tr>
    		<td>划分</td>
    		<td>合同总量<br/>（个）</td>
    		<td>本月计划<br/>（个）</td>
    		<td>本月完成<br/>（个）</td>
    		<td>累计完成<br/>（个）</td>
    		<td>计划完成比率<br/>（%）</td>
    		<td>累计完成比率<br/>（%）</td>
    	</tr>
    	<tr>
    		<td><a href="<%=request.getContextPath()%>/mx/docManage/yz/gis/staFenbu?systemName=${systemName}&unitId=${unitId}">分部工程</a></td>
    		<td>${count1 }</td>
    		<td>${count3 }</td>
    		<td>${count7 }</td>
    		<td>${count5 }</td>
    		<td><fmt:formatNumber pattern="0.00" value="${count3==0?0:count7*100/count3 }" /></td>
    		<td><fmt:formatNumber pattern="0.00" value="${count1==0?0:count5*100/count1 }" /></td>
    	</tr>
    	<tr>
    		<td>分项工程</td>
    		<td>${count2 }</td>
    		<td>${count4 }</td>
    		<td>${count8 }</td>
    		<td>${count6 }</td>
    		<td><fmt:formatNumber pattern="0.00" value="${count4==0?0:count8*100/count4 }" /></td>
    		<td><fmt:formatNumber pattern="0.00" value="${count2==0?0:count6*100/count2 }" /></td>
    	</tr>
    	<tr>
    		<td colspan="7"></td>
    	</tr>
    </table>
</body>
</html>