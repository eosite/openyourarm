<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>HTML模板编辑器</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style type="text/css">
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
<script>
	var contextPath = "${contextPath}";
	window.PROJECT_CONTEXT = "${contextPath}/mx/";
	$(function(){
		
		$("[data-role=editor]").editor({
			initialFrameWidth : '80%',
			initialFrameHeight : '560',
			toolbars : window.UEDITOR_CONFIG.toolbars
		});
		
		$('button').on('click',getStuff);
	});
	
	function getStuff(e){
		
		var key = $(this).attr("key");
		
		var val = $("#" + key).mtkendo("getValue");
		
		alert(val);
		
	}
</SCRIPT>
</head>
<body>
	<div id="container1" name="content" class='col' data-role='editor'></div>
	<button class='k-button' key='container1' >确定</button><%--
	
	<div id="container0" name="content" class='col' data-role='editor0'></div>
	<button class='k-button' key='container0' >确定</button>
--%></body>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.editor.extends.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.kendo.extends.js" ></script>
</html>