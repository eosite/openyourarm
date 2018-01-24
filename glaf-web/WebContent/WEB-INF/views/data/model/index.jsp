<%@page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
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
<title>数据模型设计</title>

<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/webfile/styles/zTreeStyle.css">

<style type="text/css">
.ui-jqgrid .ui-jqgrid-htable .ui-th-div {
	height: 23px;
}

td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

ul li {
	list-style-type: none;
}

.tree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.refresh {
	margin-left: 2px;
	margin-right: -1px;
	/*background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle 
	*/
	
	background: url(${contextPath}/images/refresh.png) no-repeat scroll 0 0 transparent;
}


.ztree li span.button.tables_ico_open{margin-right:2px; background: url(${contextPath}/images/blue-folder-horizontal-open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tables_ico_close{margin-right:2px; background: url(${contextPath}/images/blue-folder-horizontal.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.views_ico_open{margin-right:2px; background: url(${contextPath}/images/34758.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.views_ico_close{margin-right:2px; background: url(${contextPath}/images/16193.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
	
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/base/js/bootstrap.min.js"></script>
<script type="text/javascript">
	var contextPath = "${contextPath}/mx", oid = '${oid}';
</script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
	<script type="text/javascript"
		src="${contextPath}/scripts/jsPlumb/js/jsPlumb-2.2.7-min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/scripts/jsPlumb/js/jsPlumbToolkit-1.1.4.js"></script>
<script type="text/javascript"
	src="${contextPath}/webfile/js/data/model/index.js"></script>
	<script type="text/javascript"
		src="${contextPath}/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script> 
</head>
<body>
	<div class="container-fluid">
		<div class="row well" style="background-color: beige;">
			<div class="col-sm-2 columns split-left">
				<div class="well">
					<div style='text-align: left;'>
						<button class='btn btn-default btn-xs' t="model-add" title="添加分类">
							<i class="fa fa-plus" aria-hidden="true"></i>
						</button>
					</div>
					<div id="ztree-01" style="height: 820px"></div>
				</div>
			</div>
			<div class="col-sm-10 columns split-right">
				<div class="well mt-well" style="height: 100%">
					 <iframe frameborder="0" scrolling="no" class="data-dbmodel"
						onload="DBDesigner.set(this)"
						src="${contextPath}/mx/data/model/index?view=/data/model/designer"
						height="99%" width="100%"></iframe> 
				</div>
			</div>
			<!--row-->
			<div style='text-align: right;'>
				<!-- <button class='btn btn-default' t="validate">
					<i class="fa fa-filter" aria-hidden="true"></i>验证
				</button> -->
				<button class='btn btn-default' t="model-save">
					<i class="fa fa-hand-peace-o" aria-hidden="true"></i>确定
				</button>
				 <button class='btn btn-default' t="buildMetadatas">
					<i class="fa fa-refresh" aria-hidden="true"></i>刷新表结构
				</button> 
			</div>
		</div>
	</div>
	
	<div style="display:none;position: fixed;" id="split-btn">
		<button class="btn btn-default btn-xs">&lt&lt</button>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/webfile/js/jquery.ztree.extends.js"></script>
<!-- business js -->
<!-- business js -->
</html>