<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
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
<title>页面选择</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">

$(function() {
	
});

function submit(){
	parent.callbackFn($("#inp").val()||null);
	parent.layer.close(parent.layer.getFrameIndex());
}
</script>
</head>
<body>
	<div style="height:200px;">
		输入隐藏选卡：<input type="text" id="inp" class="k-textbox" value="${param.val}" />
		<br/>
		<span style="color:red;">说明：请输入需要隐藏的选卡（比如隐藏2和3选卡则输入2,3），如需要隐藏所有则不输入</span>
	</div>
	<div style="" align="center" >
		<button class='k-button' onclick="submit();" > 确  定 </button>
	</div>
</body>
</html>