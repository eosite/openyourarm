<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@page import="com.glaf.base.utils.*"%>
<%
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
	int curY = Integer.parseInt(sdf.format(new java.util.Date()));
	
	String refresh = ParamUtil.getParameter(request,"refresh");
    if(refresh == null || refresh.equals("")){
  	  refresh = request.getAttribute("refresh")!=null?request.getAttribute("refresh").toString():"";
    }
    String year = request.getAttribute("year")==null?"":request.getAttribute("year").toString();
    String moneth = request.getAttribute("month")==null?"":request.getAttribute("month").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新统计数据</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
</head>
<body style="margin:1px;">  
<form method="post">
	<select name="year" style="width:100px">
		<c:forEach var="y" begin="2010" end="2030" step="1">
			<option value="${y}" ${y==year?'selected':'' }>${y}</option>
		</c:forEach>
	</select>
	<select name="month" style="width:50px">
		<c:forEach var="m" begin="1" end="12" step="1">
			<option value="${m}" ${m==month?'selected':'' }>${m}</option>
		</c:forEach>
	</select>
	<input name="btn_save" type="submit" value="更新数据">
</form>

<div id="messageDiv" style="display:none">
	${message }
</div>

<script type="text/javascript">
var messageDiv = document.getElementById("messageDiv");
var message = messageDiv.innerText;
var refreshStr = '<%= refresh %>';
if (message.length > 0) {
  alert(message);
}
</script>
</body>
</html>