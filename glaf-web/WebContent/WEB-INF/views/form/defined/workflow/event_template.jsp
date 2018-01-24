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
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css"
	id="style_color" />
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">

<!-- jqGrid -->
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath }/scripts/jqueryui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jqgrid/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jqgrid/css/ui.jqgrid-bootstrap-ui.css">

<!-- ztree -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/ztree/css/zTreeStyle/zTreeStyle.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/eventTemplate/css/style.css">
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath }", pageId = '${param.pageId}', complexId = '${param.complexId}', isSelect = '${param.isSelect}', isSave = '${param.isSave}', mtxx = {
		tfMsg : {},
		saveData : {
			pageId : pageId
		}
	};
</script>
</head>
<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white page-sidebar-closed">

	<div class="page-header navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner ">
			<!-- LOGO -->
			<div class="page-logo">
				<a href="#"> <img src="${pageContext.request.contextPath }/scripts/home/metrolic/images/yellow_chissLOGOg.png" alt="logo" class="logo-default" />
				</a>
				<div class="menu-toggler sidebar-toggler"></div>
			</div>

			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<div class="btn-toolbar" role="toolbar" style="float: left; padding-left: 88px; padding-top: 8px;">
				<div class="btn-group">
					<button type="button" id="saveBt" class="btn btn-icon-only btn-default" title="保存" style="height: 30px;" disabled>
						<i class="fa fa-save"></i>
					</button>

					<button type="button" id="saveBt2" class="btn btn-icon-only btn-default" title="保存为复合构件" style="height: 30px; display: none;">
						<i class="fa fa-paste"></i>
					</button>
				</div>

				<!-- 
				<div class="btn-group">
					<button type="button" id="keyboardBt" class="btn btn-icon-only btn-default" title="快捷键" style="height: 30px;">
						<i class="fa fa-keyboard-o"></i>
					</button>
				</div>
				 -->
			</div>
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">

					<li class="dropdown dropdown-quick-sidebar-toggler"><a href="javascript:;" class="dropdown-toggle"> <i class="icon-logout"></i>
					</a></li>
					<!-- END QUICK SIDEBAR TOGGLER -->
				</ul>
			</div>
			<!-- END TOP NAVIGATION MENU -->
		</div>
		<!-- END HEADER INNER -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper">
			<div class="page-sidebar navbar-collapse collapse"></div>
			<!-- END SIDEBAR -->
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<!-- BEGIN CONTENT BODY -->
			<div class="page-content" style="">
				<!-- BEGIN PAGE HEADER-->
				<div class="row content-row">
					<div class="col-md-12">
						<ul id="myTab" class="nav nav-tabs">
							<li class="active"><a href="#home" data-toggle="tab">自定义</a></li>
							<li><a href="#tab2" data-toggle="tab">系统</a></li>
						</ul>
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane fade in active" id="home">
								<div class="row inline-row">
									<div class="col-md-4" style="width: 300px;">
										<div class="mt-ztree-tools">
											<button type="button" id="" class="btn btn-primary mtbtn" data-type="1" data-actType="addBrother" title="同级增加" style="height: 30px;">
												<i class="fa fa-plus"></i>同级
											</button>
											<button type="button" id="" class="btn btn-primary mtbtn" data-type="1" data-actType="addChild" title="下级增加" style="height: 30px;">
												<i class="fa fa-plus"></i>下级
											</button>
											<button type="button" id="" class="btn btn-primary mtbtn" data-type="1" data-actType="eidt" title="修改" style="height: 30px;">
												<i class="fa fa-edit"></i>修改
											</button>
											<button type="button" id="" class="btn btn-primary mtbtn" data-type="1" data-actType="delete" title="删除" style="height: 30px;">
												<i class="fa fa-remove"></i>删除
											</button>
										</div>
										<ul id="ztree" class="ztree"></ul>
									</div>
									<div class="col-md-8">
										<table id="grid"></table>
										<div id="pager"></div>
									</div>
								</div>
							</div>
							<div class="tab-pane fade" id="tab2"></div>
						</div>
					</div>
				</div>
			</div>
			<!-- END CONTENT BODY -->
		</div>
		<!-- END CONTENT -->
		<a href="javascript:;" class="page-quick-sidebar-toggler"> <i class="icon-login"></i>
		</a>
		<div class="page-quick-sidebar-wrapper" data-close-on-body-click="false">
			<div class="page-quick-sidebar"></div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="page-footer">
		<div class="page-footer-inner">华闽通达.</div>
		<div class="scroll-to-top">
			<i class="icon-arrow-up"></i>
		</div>
	</div>



	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">增加分类</h4>
				</div>
				<div class="modal-body">
					<div id="pname_row" class="row" style="display: none;">
						<div class="col-md-12">
							<div class="form-group">
								<label for="pname">父分类名称:</label> <input type="text" class="form-control" id="pname" disabled>
							</div>
						</div>
					</div>
					<div id="fname_row" class="row">
						<div class="col-md-12">
							<div class="form-group">
								<input type="hidden" id="nodeId" /> <input type="hidden" id="parentId" /> <input type="hidden" id="treeId" /> <input type="hidden" id="type" /> <input
									type="hidden" id="actType" /> <label for="fname"><font style="color: red;">*</font>分类名称:</label> <input type="text" class="form-control" id="fname"
									placeholder="分类名称">
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="mtSave" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">保存</h4>
				</div>
				<div class="modal-body">
					<div id="fname_row" class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label for="ffname"><font style="color: red;">*</font>模板名称:</label> <input type="text" class="form-control" id="ffname" placeholder="模板名称">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label for="fremark">备注:</label>
								<textarea type="text" class="form-control" id="fremark" placeholder="备注"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="tempSave" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">模板映射</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div id="mappingDiv" class="col-md-8">
							
						</div>
						<div id="pageTreeDiv" class="col-md-4">
							<ul id="pageTree" class="ztree"></ul>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="mtSelect" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
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


	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/filterObj.js" ></script>
	
	<!-- jqGrid -->
	<script src="${pageContext.request.contextPath }/scripts/jqgrid/js/minified/i18n/grid.locale-cn.js"></script>
	<script src="${pageContext.request.contextPath }/scripts/jqgrid/jquery.jqGrid.js"></script>

	<!-- ztree -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/ztree/js/jquery.ztree.all.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/eventTemplate/index.js"></script>



</body>
</html>