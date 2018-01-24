<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.config.SystemConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看TIF</title>
<style type="text/css">
	html{
		height: 100%;
	}
	body{
		height: 99%;
	}
	body{
		margin: auto;
		text-align: center;
	}
</style>
</head>
<body>
	<object width='100%' height='100%' classid="CLSID:106E49CF-797A-11D2-81A2-00E02C015623" name="tiffObj"> 
		加载失败，请先<a href="<%=request.getContextPath() %>/tif/alternatiff-ax-w32-2.0.8.zip">下载控件</a>,并以管理员运行安装 
		<param name="src" value="<%=request.getContextPath()%>/mx/form/imageUpload?method=downloadById&id=${param.id}">
		<param name="negative" value="no"> 
	</object> 
</body>
</html>