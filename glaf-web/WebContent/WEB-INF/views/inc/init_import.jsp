<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
   pageContext.setAttribute("server_scheme", request.getScheme());
%>
<!-- 解决kendo splitter控件 span页面路径使用相对路径指定时 页面未通过iframe标签指定-->
<c:set  var="webPath" value="${server_scheme}://${header['host']}${pageContext.request.contextPath}/"/>