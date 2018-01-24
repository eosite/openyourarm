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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>增删改定义工具</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<%@ include file="/WEB-INF/views/inc/globaljs.jsp"%>
<script type="text/javascript">
	var contextPath = "${contextPath}", wdataSet = {
		id : "${id}"
	};
</script>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link
	href="${contextPath}/scripts/plugins/bootstrap/base/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css">
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

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.tree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

.
td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

ul li {
	list-style-type: none;
}

.active {
	background-color: #93C3CF
}
</style>
<body>
	<div id="vertical" style="width: 98%; height: 98%; margin: 10px auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width: 500px; text-align: left; vertical-align: middle;"><img
						src="${contextPath}/images/database.png"
						style="vertical-align: middle;" /> <span
						style="font-family: Lucida Calligraphy; font-size: 20px; font-weight: bolder;">增删改</span>
						<span style="font-size: 16px; font-weight: bolder;">定义工具</span></td>
					<td style="text-align: right;">
						<div style="padding: 5px;">
							<button t="sure" class="k-button" type="button">确定</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="horizontal" style="height: 100%; width: 100%;">
				<div style="height: 451px; padding-top: 10px;">
					<%@include file="/WEB-INF/views/form/defined/ex/commonTree.jsp"%>
				</div>
				<div>
					<div id="tabstrip" style="border: 0px;">
						<ul>
							<li>保存</li>
							<!-- <li>修改条件</li>
							<li>删除</li>
							<li>删除条件</li> -->
						</ul>

						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;">
							<div id="grid-2" style="width: 100%; height: 650px;"></div>
						</div>
						<!-- <div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;"></div>

						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;"></div>

						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;"></div> -->
					</div>
					<div id="message"
						style="width: 100%; height: 200px; overflow: auto;"></div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/webfile/js/sqlcrud-c.js"></script>
</html>