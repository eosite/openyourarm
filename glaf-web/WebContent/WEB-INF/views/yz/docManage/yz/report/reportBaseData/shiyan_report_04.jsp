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
</head>
<body style="margin:1px;">
<div style="margin:0;"></div> 
	<iframe id="reportFrame" width="100%" height="100%" frameborder="0"
	src="${reportURL}&reportlet=/yz/shiyan/shiyanReport_04.cpt
	&database=${dbconfig.database}&host=${dbconfig.host}&port=${dbconfig.port}&user=${dbconfig.user}&password=${dbconfig.password}
	&sjqddjCode=${designStrRank}"></iframe>
</body>
</html>