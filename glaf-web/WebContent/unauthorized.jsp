<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Apache Shiro Application</title>
</head>
<body>
<h3>没有权限</h3>
<p>您没有该资源的访问权限.  </p>
<p><a href="<%=request.getContextPath()%>/index.jsp">首页</a></p>
<p><a href="#" onclick="javascript:history.back();">返回</a></p>
</body>
</html>
