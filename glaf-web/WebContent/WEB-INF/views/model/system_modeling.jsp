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
<title>系统规划</title>
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
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<!--ztree-->
<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width:100%;border:0px;">
				<tr>
					<td style="width:250px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/scripts/model/img/brick_edit.png"
						style="vertical-align: middle;width: 32px;" /> <span
						style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">SYSTEM</span>
						<span style="font-size: 16px;font-weight: bolder;">系统规划</span></td>
					<td style="vertical-align: middle;">
						<div id="toolbar" style="border:0px;text-align:right;"></div>
					</td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="horizontal" style="height:100%; width: 100%;">
				<div id="systemDiv">
					<ul id="systemTree" class="ztree"></ul>
				</div>
				<div>
					<div id="tabstrip" style="height:100%; width: 100%;border: 0px;">
						<ul>
							<li id="tab-name1">规划流程</li>
							<li id="tab-name2">功能结构图</li>
						</ul>
						<div style="border: 0px;padding: 0px;margin:0px;"
							id="tab-pageview">
							<iframe id="process"
								style="height:98%; width: 100%;border: 0px;padding: 0px;margin:0px;"></iframe>
						</div>
						<div style="border: 0px;padding: 0px;margin:0px;">
							<iframe id="systemstructure"
								style="height:98%; width: 100%;overflow-y:hidden;overflow-x: hidden;border: 0px;padding: 0px;margin:0px;"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    var sysId = '${param.sysId}';
    var contextPath = '${contextPath}';
    var webPath='${webPath}';
	</script>
	<script type="text/javascript"
	src="${contextPath}/scripts/model/modeling.js"></script>
</body>
</html>