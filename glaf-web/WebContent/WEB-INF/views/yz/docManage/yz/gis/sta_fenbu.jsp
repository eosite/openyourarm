<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    		<td colspan="8">分部工程实时进度统计结果</td>
    	</tr>
    	<tr>
    		<td>序号</td>
    		<td>分部</td>
    		<td>合同总量<br/>（个）</td>
    		<td>本月计划<br/>（个）</td>
    		<td>本月完成<br/>（个）</td>
    		<td>累计完成<br/>（个）</td>
    		<td>计划完成比率<br/>（%）</td>
    		<td>累计完成比率<br/>（%）</td>
    	</tr>
    	<c:forEach items="${subSectionList }" var="item" varStatus="sta">
	    	<tr>
	    		<td>${sta.index+1 }</td>
	    		<td>${item.indexName }</td>
	    		<td>${item.cell1 }</td>
	    		<td>${item.cell2 }</td>
	    		<td>${item.intpFile1 }</td>
	    		<td>${item.intpFile2 }</td>
	    		<td><fmt:formatNumber pattern="0.00" value="${item.cell2==0?0:item.intpFile1*100/item.cell2 }" /></td>
	    		<td><fmt:formatNumber pattern="0.00" value="${item.cell1==0?0:item.intpFile2*100/item.cell1 }" /></td>
	    	</tr>
    	</c:forEach>
    </table>
</body>
</html>