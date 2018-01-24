<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.security.LoginContext"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	if (request.getAttribute("userTheme") != null) {
		UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
		request.setAttribute("theme", userTheme.getThemeStyle());
		request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
	} else {
		String theme = com.glaf.core.util.RequestUtils.getTheme(request);
		request.setAttribute("theme", theme);
		String homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
		request.setAttribute("homeTheme", homeTheme);
	}
	LoginContext loginContext = RequestUtils.getLoginContext(request);
	request.setAttribute("loginContext", loginContext);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/responsive/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/responsive/awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/responsive/ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/responsive/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/responsive/dist/css/skins/skin-blue.min.css">
<link rel="stylesheet" href="${contextPath}/scripts/jquery-ui.min.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<link rel="stylesheet"
	href="${contextPath}/scripts/designer/designer.css">
<style type="text/css">
.colCss {
	background-color: #45c8dc;
	border: 10px;
}
.selected_border{border: 2px solid orange;}
.conf_icon_uncheck{color: #eeeeee;}
.fonticon-hover{cursor: pointer;}
</style>
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini edit" style="cursor: auto;" ng-app="mainApp">
	<div class="wrapper" >

		<!-- Main Header -->
		<header class="main-header"> <!-- Logo --> <a
			href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
			<span class="logo-mini"> <b>PSD</b>
		</span> <!-- logo for regular state and mobile devices --> <span
			class="logo-lg">页面设计器</span>
		</a> <!-- Header Navbar --> 
		<nav class="navbar navbar-static-top"
			role="navigation"> <!-- Sidebar toggle button--> 
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button"> 
			<span class="sr-only">Toggle navigation</span>
		</a> 
		<div class="mymenu" style="margin-top: 8px;">
			<div class="btn-toolbar" role="toolbar">
				<div class="btn-group">
					<button type="button" id="previewBt" class="btn btn-sm btn-default">
						<i class="fa fa-eye"></i> 预览
					</button>
					<button type="button" id="editBt"
						class="active btn btn-sm btn-default">
						<i class="fa fa-edit "></i> 编辑
					</button>
					<button type="button" id="exportBt" class="btn btn-sm btn-default">
						<i class="fa fa-download"></i> 导出
					</button>
				</div>
				<div class="btn-group">
					<button type="button" id="saveBt" class="btn btn-sm btn-default">
						<i class="fa fa-save"></i> 保存
					</button>
					<button type="button" id="emptyBt" class="btn btn-sm btn-danger">
						<i class="fa fa-trash-o"></i> 清空
					</button>
				</div>
				<div class="btn-group">
					<button type="button" id="undoBt" class="btn btn-sm btn-warning">
						<i class="fa fa-reply"></i> 撤销
					</button>
					<button type="button" id="redoBt" class="btn btn-sm btn-warning">
						<i class="fa fa-mail-forward"></i> 重做
					</button>
				</div>
			</div>
			<ul class="float-right reponsive-block">
					<li><a class="desktop active" data-width="auto"></a></li>
					<li><a class="tablet-landscape" data-width="1024"></a></li>
					<li><a class="tablet-portrait" data-width="768"></a></li>
					<li><a class="iphone-landscape" data-width="960"></a></li>
					<li><a class="iphone-portrait" data-width="640"></a></li>
		    </ul>
		</div>
		</nav>
		<!-- Navbar Right Menu -->
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar"> <!-- Sidebar user panel (optional) -->
		<!-- search form (Optional) -->
		<form action="#" method="get" class="sidebar-form">
			<div class="input-group">
			    <select id="ui-select" class="form-control" placeholder="选择UI..."> 
			      <c:forEach items="${uiList}" var="ui">
			        <option value="${ui.code}">${ui.name}</option>
			      </c:forEach>
                </select>
				<span class="input-group-btn">
						<button type="submit" name="search" id="search-btn"
							class="btn btn-flat">
							<i class="fa fa-search"></i>
						</button>
				</span>
			</div>
		</form>
		<!-- /.search form --> <!-- Sidebar Menu -->
		<ul id="sidebar-menu" class="sidebar-menu">
			<li class="header">控件列表</li>
			<!-- Optionally, you can add icons to the links -->
			<!--<li class="active"><a href="#"><i class="fa fa-link"></i> <span>Link</span></a></li>
        <li><a href="#"><i class="fa fa-link"></i> <span>Another Link</span></a></li>
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>Multilevel</span> <i class="fa fa-angle-left pull-right"></i></a>
          <ul class="treeview-menu">
            <li><a href="#">Link in level 2</a></li>
            <li><a href="#">Link in level 2</a></li>
          </ul>
        </li>
          -->
		</ul>
		<!-- /.sidebar-menu --> </section> <!-- /.sidebar --> </aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<!-- 
			<section class="content-header">
			<h1>
				设计区<small></small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
				<li class="active">Here</li>
			</ol>
			</section>
            -->
			<!-- Main content -->
			<section class="content"> 
			<!-- 支持UI列表 -->
			<div class="ui-list">
			   
			</div>
			<!-- Your Page Content Here -->
			<div class="designer" id="dsDiv" style="width: 100%;">
			   ${desingerHtml}
			</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Control Sidebar -->

		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<form name="dForm" action="download" method="post">
	   <textarea id="pageLayout" name="pageLayout" style="display: none;"></textarea>
	   <textarea id="pageDesigner" name="pageDesigner" style="display: none;"></textarea>
	</form>
	<iframe name="operFrame" style="display: none;"></iframe>
	<!-- 警告框 -->
	 <div class="hidemy-modal">
	      <div id="warningModal" class="modal fade modal-warning" role="dialog" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title">系统提示</h4>
                  </div>
                  <div class="modal-body">
                    <p>One fine body…</p>
                  </div>
                  <div class="modal-footer">
                    <button class="btn btn-outline pull-left modalClose" type="button" data-dismiss="modal">取消</button>
                    <button class="btn btn-outline modalContinue" type="button">继续</button>
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
      </div>
	<%@ include file="templates.jsp"%>
	<!-- ./wrapper -->
    
	<!-- REQUIRED JS SCRIPTS -->
    <script type="text/javascript">
        var contextPath='${contextPath}';
        var id='${param.id}';
        var pId='${param.pId}';
        var designerJson='${desingerJson}';
    </script>
	<!-- jQuery 2.1.4 -->
	<script
		src="${contextPath}/scripts/home/responsive/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script src="${contextPath}/scripts/jquery-ui-1.11.4.min.js"></script>
	<!-- angular 1.4.3 -->
	<script src="${contextPath}/scripts/angular/angular.min.js"></script>
	<script src="${contextPath}/scripts/angular/app/app.js"></script>
	<script src="${contextPath}/scripts/angular/app/Element/ElementController.js"></script>
	<script src="${contextPath}/scripts/angular/app/Element/BgColorController.js"></script>
	<script src="${contextPath}/scripts/angular/app/component/list/ListService.js"></script>
	<script src="${contextPath}/scripts/angular/app/component/list/ListController.js"></script>
	<!-- Bootstrap 3.3.5 -->
	<script
		src="${contextPath}/scripts/home/responsive/bootstrap/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${contextPath}/scripts/home/responsive/dist/js/app.min.js"></script>
	<script src="${contextPath}/scripts/treeJson.js"></script>
	<script src="${contextPath}/scripts/designer/component.js"></script>
	<script src="${contextPath}/scripts/designer/designer.js"></script>
	<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->

	<!--Temp-->
    <link href="${contextPath}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="${contextPath}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <link href="${contextPath}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${contextPath}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
    <link href="${contextPath}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="${contextPath}/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
    <link href="${contextPath}/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
    <!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN THEME LAYOUT STYLES -->
    <link href="${contextPath}/assets/layouts/layout2/css/layout.min.css" rel="stylesheet" type="text/css" />
    <link href="${contextPath}/assets/layouts/layout2/css/custom.min.css" rel="stylesheet" type="text/css" />
</body>
</html>
