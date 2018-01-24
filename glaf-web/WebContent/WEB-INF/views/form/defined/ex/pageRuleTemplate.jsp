<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>页面工作流流程设置</title>
<meta charset="utf-8">
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />

<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">

<!-- ztree -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<style type="text/css">
	html,body{
		height: 100%;
		margin: 0;
		padding: 0;
	}
	.div-container{
		margin: 0px;
		padding: 0px;
		width: 100%;
		height: 100%;
		display: inline-flex;
	}
	.div-tree{
		width: 300px;
		height: 100%
		border: 0px;
	}
	.div-page{
		margin: 0px;
		padding: 0px;
		width: 100%;
		height: 100%;
		border: 1px solid #9af3fb;
	}
	iframe{
		width: 100%;
		height: 99%;
		border: 0px;
	}
</style>
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath }";
</script>
</head>
<body >
	<div class="div-container">
		<div class="div-tree">
			<button id="select" class="btn btn-link">
				<i class="fa fa-floppy-o"></i>
				套用
			</button>
			<ul id="ztree" class="ztree"></ul>
		</div>
		<div class="div-page">
			<iframe id="iframe" ></iframe>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/scripts/layout.js" type="text/javascript"></script>

	<script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

	<script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/scripts/plugins/bootstrap/select/jquery.metroselect.extends.js"></script>
	<script src="${pageContext.request.contextPath }/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>

	<!-- jqGrid -->
	<script src="${pageContext.request.contextPath }/scripts/jqgrid/js/minified/i18n/grid.locale-cn.js"></script>
	<script src="${pageContext.request.contextPath }/scripts/jqgrid/jquery.jqGrid.js"></script>

	<!-- ztree -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/ztree/js/jquery.ztree.all.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/pageRuleTemplate.js"></script>



</body>
</html>