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
<title>流程元素属性定义</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/scripts/workflow/workflow-toolbox.js"></script>
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

.datagrid-header td,th {
	border-right: 1px dotted #ccc;
	font-size: 12px;
	font-weight: normal;
	background: #fafafa url('${contextPath}/images/datagrid_header_bg.gif')
		repeat-x left bottom;
	border-bottom: 1px dotted #ccc;
	border-top: 1px dotted #fff;
	height: 28px;
}

.datagrid-tbody td {
	border-right: 1px dotted #ccc;
	font-size: 12px;
	font-weight: normal;
	border-bottom: 1px dotted #ccc;
	border-top: 1px dotted #fff;
	height: 28px;
	padding-left: 5px;
}

.datagrid-tbody tr {
	
}

.datagrid-title td {
	font-size: 12px;
	font-weight: bolder;
	background: #E0ECFF repeat-x left bottom;
	height: 28px;
	vertical-align: middle;
}

.titleTd {
	background: #E0ECFF repeat-x left bottom;
}

td,th {
	display: table-cell;
	vertical-align: inherit;
}

#panelbar {
	margin: 0 auto;
}

li span {
	height: 30px;
	font-size: 12px;
	vertical-align: middle;
}
/*Tooltips*/ 
.tooltips{ 
position:relative; /*这个是关键*/ 
z-index:2; 
text-decoration:underline;
font-size: 13px;
margin-left: 5px;
} 
.tooltips:hover{ 
z-index:3; 
background:none; /*没有这个在IE中不可用*/ 
text-decoration:underline;
font-weight: bolder;
} 
.tooltips a{
display: none; 
} 
.tooltips:hover a{/*span 标签仅在 :hover 状态时显示*/ 
display:inline-block; 
} 
.removea{
font-weight: bolder;
color: red;
font-size: 15px;
cursor: pointer;
}
</style>
</head>
<body>
	<div
		style="width:100%;height:100%; margin: 0 auto;border:0px;overflow: auto;">
		<form name="propform" action="" method="post">
			<div id="content"
				style="width:100%;height:100%; margin: 0 auto;border:0px;overflow: auto;">
				<ul id="panelbar" style="border: 0px;">

				</ul>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		var contextPath = "${contextPath}";
		$(document)
				.ready(
						function() {
							$
									.Toolbox(
											$("#panelbar"),
											{
												"url" : "${contextPath}/service/workflow/${param.elemType}/elemsetview",
												"contextPath" : contextPath
											},
											{
												"processDefinitionId" : "${param.processDefinitionId}",
												"resourceId" : "${param.resourceId}"
											}, null);
        });
	</script>

</body>
</html>