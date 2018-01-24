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
<html ng-app="angularApp">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ng-test</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath }/scripts/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath }/scripts/bootstrap/css/bootstrap-theme.min.css">
<script type="text/javascript"
	src="${contextPath }/workflow/editor-app/libs/angular_1.2.13/angular.min.js"></script>
<script type="text/javascript">
	var contextPath = "${contextPath}";
</script>
</head>
<body>
	<div class="container" ng-controller="customersCtrl">

		<div class="row"></div>
		<div class="clearfix"></div>
		<br>
		<div class="row">
			<div class="col-sm-3">
				<div class="input-group input-group-sm">
					<span class="input-group-addon">名称</span> <input type="text"
						class="form-control" placeholder="输入名称..." ng-model="name">
				</div>
			</div>
			<div class="col-sm-2">
				<button class="btn btn-info" ng-click="paging.first()">Search</button>
			</div>
		</div>

		<div class="clearfix"></div>
		<br>
		<div class="row">
			<div class="ng-table">
				<table
					class="table table-bordered table-hover table-condensed table-striped">
					<thead>
						<tr>
							<th>序号</th>
							<th>ID</th>
							<th>名称</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="row in rows" ng-dblclick="dblclickAction(row)">
							<td>{{ row.startIndex }}</td>
							<td>{{ row.cell_useradd8111_0_id }}</td>
							<td>{{ row.cell_useradd8111_0_cell_useradd8111_user1 }}</td>
						</tr>
					</tbody>
				</table>
				<ul class="pagination  pagination-sm">
					<li><a href="javascript:void(0)" ng-click="paging.first()">首页</a></li>
					<li><a href="javascript:void(0)" ng-click="paging.prev()">上一页</a></li>
					<li><a href="javascript:void(0)" ng-click="paging.next()">下一页</a></li>
					<li><a href="javascript:void(0)" ng-click="paging.last()">末页</a></li>
				</ul>
			</div>

		</div>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/webfile/js/defined.angular.controller.js"></script>
</html>
