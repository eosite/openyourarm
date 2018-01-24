<%@page import="com.glaf.base.utils.WebUtil"%>
<%@page import="com.glaf.chinaiss.model.GisInfo"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    GisInfo gi = (GisInfo)request.getAttribute("GisInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表生成</title>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	
</style>
<script type="text/javascript">
	
</script>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div> 
	<table border="1" cellspacing="0" cellpadding="1" style="width:100%;border-collapse: collapse" bordercolor="#111111" >
		<tr class="list-title" align="center" style="height: 30px">
			<td width="25px">名称</td>
			<td width="80px">累计已填写(份)</td>
		</tr>
		<tr style="height: 30px">
			<td class="td-no"><%=gi.getName() %></td>
			<td class="td-no"><%=gi.getNum() %></td>
		</tr>
	</table>	
</body>
</html>