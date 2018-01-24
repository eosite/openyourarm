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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!--开始数值增量输入框-->
		 <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.css" rel="stylesheet" type="text/css" />
		<!--结束数值增量输入框-->
		<!--开始颜色选择器-->
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-minicolors/jquery.minicolors.css" rel="stylesheet" type="text/css" />
        <!--结束颜色选择器-->
		<!--<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/nouislider/nouislider.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/nouislider/nouislider.pips.css" rel="stylesheet" type="text/css" />-->
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
		<!--开始下拉选择框-->
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
	    <!--结束下拉选择框-->
		<!-- BEGIN PAGE LEVEL PLUGINS -->
		<!-- 
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/morris/morris.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet" type="text/css" />
         -->
         <!--该文件找不到-->
        <!-- <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css" /> -->
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />
		<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />
		<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="${contextPath}/scripts/designer/designer.layout.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/themes/blue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
         <!-- select2 -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
         <!-- icheck -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />
        <!-- jquery fileupload -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/scripts/designer/metro_designer_new.css">
        <link rel="stylesheet" href="${contextPath}/scripts/designer/bootstrap_extend.css">
        <link rel="stylesheet" href="${contextPath}/scripts/mmenu/src/css/jquery.mmenu.all.css">
        <link rel="stylesheet" href="${contextPath}/scripts/mmenu/demo/demo.css">
        
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" />
        <!-- jplayer -->
        <link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/scripts/plugins/bootstrap/jPlayer/css/jplayer.blue.monday.min.css">
       
        <!-- touchspin -->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/touchspin/css/bootstrap.touchspin.min.css"> 
        <!-- tagsinput -->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/tagsinput/bootstrap-tagsinput.css">
        <!-- datepicker -->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/datepicker/css/bootstrap-datepicker3.min.css">
        <!-- datetimepicker -->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css">       
        <!-- calendar --> 
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/calendar/css/fullcalendar.min.css">
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/calendar/ext/calendar.extends.css">
        <!-- megamenu --> 
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/menu/ext/megamenu.extends.css">
        <!-- treelist -->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/treelist/css/treelist_demo.css">
        
        <!-- customSelect -->
     	
      
        <!-- messenger -->
        <link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/scripts/plugins/bootstrap/messenger/build/css/messenger.css">
        <link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/scripts/plugins/bootstrap/messenger/build/css/messenger-theme-block.css">
        <link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/scripts/plugins/bootstrap/messenger/build/css/messenger-theme-future.css">
        <link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/scripts/plugins/bootstrap/messenger/build/css/messenger-theme-air.css">
        <link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/scripts/plugins/bootstrap/messenger/build/css/messenger-theme-ice.css">
        <link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/scripts/plugins/bootstrap/messenger/build/css/messenger-theme-flat.css">
		<!-- dialog --> 
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">
        <!-- tabs -->
        <link href="${contextPath}/scripts/plugins/bootstrap/tabs/style/plugins.css" rel="stylesheet" type="text/css" />
		<!--文件上传-->
		<link href="${contextPath}/scripts/designer/plugins/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" type="text/css" />
		<!-- login页面 --> 
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/login/css/login.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/login/css/login-2.css">		 
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/login/css/login-3.css">		 
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/login/css/login-4.css">		 
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/login/css/login-5.css">
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/nestable/css/ace.min.css">
		<link rel="stylesheet/less" type="text/css" href="${contextPath}/scripts/designer/less/designer.less" />
		<link rel="stylesheet/less" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/diagrambt/css/orgcharts.css" />
		<link rel="stylesheet/less" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/editor/css/editors.css" />
		<link rel="stylesheet/less" type="text/css" href="${contextPath}/scripts/jquery-ui.min.css" />
		<link rel="stylesheet/less" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/spreadjs/spreadjs.css" />
		<link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/webchat/ext/webchat.css">
		<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.css' />
		
		<script>
		  /* less = {
			env: "development",
			logLevel: 2,
			async: false,
			fileAsync: false,
			poll: 1000,
			functions: {},
			dumpLineNumbers: "comments",
			relativeUrls: false,
			rootpath: ""
		  };*/
		</script>
		 
		<script src="${contextPath}/scripts/jquery.min.js" type="text/javascript"></script>
		
		<script src="${contextPath}/scripts/designer/less/less.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/ueditor/ueditor.all.min.js"> </script>
		
        
        <!-- 找不到该文件 -->
        <!-- <script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/editor/js/editor.js"> </script> -->
		
</head>
    <!-- END HEAD -->
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
 <body class="blue page-sidebar-closed-hide-logo page-content-white edit page-sidebar-closed page-header-fixed page-sidebar-fixed dev openGobalStyle" ng-app="mainApp">
        <!-- BEGIN HEADER -->
        <div class="page-header navbar navbar-fixed-top">
            <!-- BEGIN HEADER INNER -->
            <div class="page-header-inner  main-header">
                <!-- BEGIN LOGO -->
                <div class="page-logo">
                    <a href="#">
						<img src="${contextPath}/scripts/designer/images/logo.png" style="margin-top:5px;" alt="logo" class="logo-default" />
					</a>
                    <div class="menu-toggler sidebar-toggler"> </div>
                </div>
                <!-- END LOGO -->
                <!-- BEGIN RESPONSIVE MENU TOGGLER -->
                <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
                <!-- END RESPONSIVE MENU TOGGLER -->
                <!-- BEGIN TOP NAVIGATION MENU -->
                <div class="mymenu">
                <div class="btn-toolbar" role="toolbar">
				<div class="btn-group">
					<button type="button" id="saveBt" class="btn btn-icon-only btn-default" title="保存" >
						<img src="${contextPath}/scripts/designer/images/toolbar/save.png"/>
						<span>保存</span>
					</button>
					<button type="button" id="previewBt" class="btn btn-icon-only btn-default" title="预览" >
						<img src="${contextPath}/scripts/designer/images/toolbar/preview.png"/>
						<span>预览</span>
					</button>
					<button type="button" id="editBt"
						class="active btn btn-icon-only btn-default" title="编辑" >
						<img src="${contextPath}/scripts/designer/images/toolbar/edit.png"/>
						<span>编辑</span>
					</button>
					<img class="split" src="${contextPath}/scripts/designer/images/toolbar/line.png" style="float: left;"/>
					<button type="button" id="undoBt" class="btn btn-icon-only btn-default" title="撤销" >
						<img src="${contextPath}/scripts/designer/images/toolbar/cancel.png"/>
						<span>撤销</span>
					</button>
					<button type="button" id="redoBt" class="btn btn-icon-only btn-default" title="重做" >
						<img src="${contextPath}/scripts/designer/images/toolbar/redo.png"/>
						<span>重做</span>
					</button>
					<button type="button" id="emptyBt" class="btn btn-icon-only btn-default" title="清空" >
						<img src="${contextPath}/scripts/designer/images/toolbar/empty.png"/>
						<span>清空</span>
					</button>
					<img class="split" src="${contextPath}/scripts/designer/images/toolbar/line.png" style="float: left;"/>
					<button type="button" id="autoHeightBt" class="btn  btn-icon-only red-mint" title="高度自适应" >
						<img src="${contextPath}/scripts/designer/images/toolbar/autoHeight.png"/>
						<span>自适应</span>
					</button>
					<div class="btn-group">
					    <button type="button" id="layoutDefined" class="btn  btn-icon-only btn-default" title="自定义布局" >
							<img src="${contextPath}/scripts/designer/images/toolbar/layoutDefined.png"/>
							<span>定义布局</span>
					    </button>
					</div>
					<button type="button" id="formatBrushBt" class="btn btn-icon-only btn-default" title="格式刷" >
						<img src="${contextPath}/scripts/designer/images/toolbar/format.png"/>
						<span>格式刷</span>
					</button>
					<button type="button" id="showGobalStyleBt" class="btn btn-icon-only btn-default" title="全局样式" >
						<img src="${contextPath}/scripts/designer/images/toolbar/x_style.png"/>
						<span>全局样式</span>
					</button>
					<button type="button" id="showThemeStyleBt" class="btn btn-icon-only btn-default" title="主题" >
						<img src="${contextPath}/scripts/designer/images/toolbar/style-edit.png"/>
						<span>主题</span>
					</button>
					<img class="split" src="${contextPath}/scripts/designer/images/toolbar/line.png" style="float: left;"/>
					<button type="button" id="themeTemplate" class="btn  btn-icon-only btn-default" title="页面模板" >
						<img src="${contextPath}/scripts/designer/images/toolbar/template.png"/>
						<span>模板库</span>
				    </button>
				    <button type="button" id="exportBt" class="btn btn-icon-only btn-default" style="width:54px;" title="导出页面" >
						<img src="${contextPath}/scripts/designer/images/toolbar/expPage.png"/>
						<span>导出页面</span>
					</button>
					<button type="button" id="designerImportBt" class="btn btn-icon-only btn-default" style="width:54px;" title="设计导入" >
						<img src="${contextPath}/scripts/designer/images/toolbar/impLayout.png"/>
						<span>导入设计</span>
					</button>
					<button type="button" id="designerExportBt" class="btn btn-icon-only btn-default" style="width:54px;" title="设计导出" >
						<img src="${contextPath}/scripts/designer/images/toolbar/expLayout.png"/>
						<span>导出设计</span>
					</button>
					<img class="split" src="${contextPath}/scripts/designer/images/toolbar/line.png" style="float: left;"/>
					<button type="button" id="setStaticData" class="btn btn-icon-only btn-default" style="width:54px;" title="模拟数据" disabled="disabled">
						<img src="${contextPath}/scripts/designer/images/toolbar/table2.png"/>
						<span>模拟数据</span>
					</button>
					<button type="button" id="complistBt" class="btn btn-icon-only btn-default selectedBt" style="width:54px;" title="显示/隐藏控件列表">
						<img src="${contextPath}/scripts/designer/images/toolbar/table2.png"/>
						<span>控件列表</span>
					</button>
				</div>
				
				
				<div style="display:none;" id="t1" ng-controller="BgColorController">
					&nbsp;{{focusObj}}
					<span ng-repeat="color in data">
						<i class="fa fa-square-o {{color.name}} fonticon-hover" ng-click="selectBgColor(color.name)" ng-class="{selected_border: color.name==focusObj.bgColor}"></i>
					</span>
				</div>
			  </div>  
                            <!-- <div class="tabbable tabbable-tabdrop">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab1" data-toggle="tab">Section 1</a>
                                            </li>
                                            <li>
                                                <a href="#tab2" data-toggle="tab">Section 2</a>
                                            </li>
                                            <li>
                                                <a href="#tab3" data-toggle="tab">Section 3</a>
                                            </li>
                                            <li>
                                                <a href="#tab4" data-toggle="tab">Section 4</a>
                                            </li>
                                            <li>
                                                <a href="#tab5" data-toggle="tab">Section 5</a>
                                            </li>
                                            <li>
                                                <a href="#tab6" data-toggle="tab">Section 6</a>
                                            </li>
                                            <li>
                                                <a href="#tab7" data-toggle="tab">Section 7</a>
                                            </li>
                                            <li>
                                                <a href="#tab8" data-toggle="tab">Section 8</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="tab1">
                                                <a href="#"><img src="/glaf/images/ui/ui-combo-box-calendar.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-combo-box-calendar.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                            <div class="tab-pane" id="tab2">
                                                <a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                            <div class="tab-pane" id="tab3">
                                                <a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                            <div class="tab-pane" id="tab4">
                                                <a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                            <div class="tab-pane" id="tab5">
                                                <a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                            <div class="tab-pane" id="tab6">
                                                <a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                            <div class="tab-pane" id="tab7">
                                                <a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                            <div class="tab-pane" id="tab8">
                                                <a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
												<a href="#"><img src="/glaf/images/ui/ui-text-field.png"/></a>
                                            </div>
                                        </div>
                                    </div>-->		  
              <div class="top-menu">
                    <ul class="float-right reponsive-block">
						<li><a class="desktop active" data-width="auto"></a></li>
						<li><a class="tablet-landscape" data-width="1024"></a></li>
						<li><a class="tablet-portrait" data-width="768"></a></li>
						<li><a class="iphone-landscape" data-width="960"></a></li>
						<li><a class="iphone-portrait" data-width="640"></a></li>
			        </ul>
               </div>

                </div>
                <div class="commonStylePanel">
							<div class="portlet-body" style="padding:0;">
							</div>
						</div>
                <!-- END TOP NAVIGATION MENU -->
            </div>
            <!-- END HEADER INNER -->
        </div>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN SIDEBAR -->
            <div class="page-sidebar-wrapper">
                <!-- BEGIN SIDEBAR -->
                <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
                <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
                <div class="page-sidebar navbar-collapse collapse">
                    <!-- BEGIN SIDEBAR MENU -->
                    <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
                    <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
                    <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
                    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
                    <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
                    <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
                     <div class="tabbable-designer">
					 <ul class="nav nav-tabs" style="border-bottom:0">
						<li class="active">
							<a href="#tab_1_1_1" data-toggle="tab" style="background:transparent;border:transparent;">构件</a>
						</li>
						<li>
							<a href="#tab_1_1_2" data-toggle="tab" style="background:transparent;border:transparent;">模板 </a>
						</li>
						<li>
							<a href="#tab_1_1_3" data-toggle="tab" style="background:transparent;border:transparent;padding-left: 1px;padding-right: 1px;">规则模板</a>
						</li>
                    </ul>
					<div class="tab-content">
                    <div class="tab-pane active" id="tab_1_1_1">
					<ul  id="sidebar-menu" class="page-sidebar-menu  page-header-fixed page-sidebar-menu-closed" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
                        <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
                        <li class="sidebar-toggler-wrapper hide">
                            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                            <div class="sidebar-toggler"> </div>
                            <!-- END SIDEBAR TOGGLER BUTTON -->
                        </li>
                        <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
                        <li class="sidebar-search-wrapper">
                            <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
                            <!-- DOC: Apply "sidebar-search-bordered" class the below search form to have bordered search box -->
                            <!-- DOC: Apply "sidebar-search-bordered sidebar-search-solid" class the below search form to have bordered & solid search box -->
                            
                            <form action="ddddd" onsubmit="return searchControl(event);" class="sidebar-search">
                            	<div class="input-group">
		                            <input id="searchInp" class="form-control" style="background: 0 0;border: 0;border-bottom: 1px solid #c2cad8;">
									<span class="input-group-btn">
		                                <a href="javascript:;" id="search-btn" class="btn submit">
		                                    <i class="icon-magnifier"></i>
		                                </a>
		                            </span>
		                        </div>
	                        </form>

                           <!--  <form action="#" method="get" class="sidebar-search">
								<a href="javascript:;" class="remove">
                                    <i class="icon-close"></i>
                                </a>
								<div class="input-group">
								    <select id="ui-select" class="form-control" placeholder="选择UI..." style="padding-left:5px;"> 
								      <c:forEach items="${uiList}" var="ui">
								        <option value="${ui.code}">${ui.name}</option>
								      </c:forEach>
					                </select>
									<select id="type-select" class="form-control" placeholder="选择分类" style="padding-left:5px;"> 
								     
					                </select>
									<span class="input-group-btn">
                                        <a href="#" id="search-btn" class="btn submit">
                                            <i class="icon-magnifier"></i>
                                        </a>
                                    </span>
								</div>
							</form> -->
                            <!-- END RESPONSIVE QUICK SEARCH FORM -->
                        </li>
						<!--
                        <li class="nav-item start active open">
                            <a href="javascript:;" class="nav-link nav-toggle">
                                <i class="icon-home"></i>
                                <span class="title">Dashboard</span>
                                <span class="selected"></span>
                                <span class="arrow open"></span>
                            </a>
                            <ul class="sub-menu">
                                <li class="nav-item start active open">
                                    <a href="index.html" class="nav-link ">
                                        <i class="icon-bar-chart"></i>
                                        <span class="title">Dashboard 1</span>
                                        <span class="selected"></span>
                                    </a>
                                </li>
                                <li class="nav-item start ">
                                    <a href="dashboard_2.html" class="nav-link ">
                                        <i class="icon-bulb"></i>
                                        <span class="title">Dashboard 2</span>
                                        <span class="badge badge-success">1</span>
                                    </a>
                                </li>
                                <li class="nav-item start ">
                                    <a href="dashboard_3.html" class="nav-link ">
                                        <i class="icon-graph"></i>
                                        <span class="title">Dashboard 3</span>
                                        <span class="badge badge-danger">5</span>
                                    </a>
                                </li>
                            </ul>
                        </li>-->  
                    </ul>
					</div>
					<div class="tab-pane" id="tab_1_1_2">
					  <ul id="template-sidebar-menu" class="page-sidebar-menu template-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
                         <li class="sidebar-toggler-wrapper hide">
                            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                            <div class="sidebar-toggler"> </div>
                            <!-- END SIDEBAR TOGGLER BUTTON -->
                        </li>
						<!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
                        <li class="sidebar-search-wrapper">
                            <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
                            <!-- DOC: Apply "sidebar-search-bordered" class the below search form to have bordered search box -->
                            <!-- DOC: Apply "sidebar-search-bordered sidebar-search-solid" class the below search form to have bordered & solid search box -->
                            <form action="#" onSubmit="event.preventDefault()" method="get" class="sidebar-search">
								<a href="javascript:;" class="remove">
                                    <i class="icon-close"></i>
                                </a>
								<div class="input-group">
								    <select id="template-select" class="form-control" placeholder="选择模板分类..."> 
								    
					                </select>
									<span class="input-group-btn">
                                        <a href="javascript:;" id="template-search-btn" class="btn submit">
                                            <i class="icon-magnifier"></i>
                                        </a>
                                    </span>
								</div>
							</form>
                            <!-- END RESPONSIVE QUICK SEARCH FORM -->
                        </li>
						
                        <li class="nav-item start active open">
                            <a href="javascript:;" class="nav-link nav-toggle">
                                <i class="icon-puzzle"></i>
                                <span class="title">模板列表</span>
                                <span class="selected"></span>
                                <span class="arrow open"></span>
                            </a>
                            <ul class="sub-menu templateli">
                               
                            </ul>
                        </li>
                    </ul>
					</div>
					<div class="tab-pane" id="tab_1_1_3">
						<ul id="ztree" class="ztree"></ul>
					</div>
					</div>
					</div>
                    <!-- END SIDEBAR MENU -->
                    <!-- END SIDEBAR MENU -->
                </div>
                <!-- END SIDEBAR -->
            </div>
            <!-- END SIDEBAR -->
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <div class="theme-panel hidden-xs hidden-sm">
                        <div class="toggler" style="display:none;"> </div>
                        <div class="toggler-close"> </div>
                        <div class="theme-options">
                            <div class="theme-option theme-colors clearfix">
                                <span> THEME COLOR </span>
                                <ul>
                                    <li class="color-default current tooltips" data-style="default" data-container="body" data-original-title="Default"> </li>
                                    <li class="color-darkblue tooltips" data-style="darkblue" data-container="body" data-original-title="Dark Blue"> </li>
                                    <li class="color-blue tooltips" data-style="blue" data-container="body" data-original-title="Blue"> </li>
                                    <li class="color-grey tooltips" data-style="grey" data-container="body" data-original-title="Grey"> </li>
                                    <li class="color-light tooltips" data-style="light" data-container="body" data-original-title="Light"> </li>
                                    <li class="color-light2 tooltips" data-style="light2" data-container="body" data-html="true" data-original-title="Light 2"> </li>
                                </ul>
                            </div>
                            <div class="theme-option">
                                <span> Theme Style </span>
                                <select class="layout-style-option form-control input-sm">
                                    <option value="square" selected="selected">Square corners</option>
                                    <option value="rounded">Rounded corners</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Layout </span>
                                <select class="layout-option form-control input-sm">
                                    <option value="fluid" selected="selected">Fluid</option>
                                    <option value="boxed">Boxed</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Header </span>
                                <select class="page-header-option form-control input-sm">
                                    <option value="fixed" selected="selected">Fixed</option>
                                    <option value="default">Default</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Top Menu Dropdown</span>
                                <select class="page-header-top-dropdown-style-option form-control input-sm">
                                    <option value="light" selected="selected">Light</option>
                                    <option value="dark">Dark</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Sidebar Mode</span>
                                <select class="sidebar-option form-control input-sm">
                                    <option value="fixed">Fixed</option>
                                    <option value="default" selected="selected">Default</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Sidebar Menu </span>
                                <select class="sidebar-menu-option form-control input-sm">
                                    <option value="accordion" selected="selected">Accordion</option>
                                    <option value="hover">Hover</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Sidebar Style </span>
                                <select class="sidebar-style-option form-control input-sm">
                                    <option value="default" selected="selected">Default</option>
                                    <option value="light">Light</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Sidebar Position </span>
                                <select class="sidebar-pos-option form-control input-sm">
                                    <option value="left" selected="selected">Left</option>
                                    <option value="right">Right</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> Footer </span>
                                <select class="page-footer-option form-control input-sm">
                                    <option value="fixed">Fixed</option>
                                    <option value="default" selected="selected">Default</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <!--
					<div class="page-bar">
                        <ul class="page-breadcrumb">
                            <li>
                                <a href="index.html">页面布局设计器</a>
                                <i class="fa fa-circle"></i>
                            </li>
                            <li>
                                <span>工作区</span>
                            </li>
                        </ul>
                        <div class="page-toolbar">
                           
                        </div>
                    </div>
					-->
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- BEGIN DASHBOARD STATS 1-->
                    <div class="content-wrapper">
						<!-- Main content -->
						<section class="content"> 
						<!-- 支持UI列表 -->
						<div class="ui-list">
						   
						</div>
						<!-- <div class="commonStylePanel portlet box dark"> -->
							<!-- <div class="portlet-title">
								<div class="caption" style="font-size:14px;">
								</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="#" class="reload"></a>
									<a href="#" class="remove"></a>
								</div>
							</div> -->
				       	<!-- <div class="commonStylePanel">
							<div class="portlet-body" style="padding:0;">
							</div>
						</div> -->
						<!-- Your Page Content Here -->
						<div class="designer" id="dsDiv" data-role="global" style="overflow-y:auto;">
							
						   ${desingerHtml}

						   
						</div>
						
						</section>
						<!-- /.content -->
		            </div>
                    <!-- END DASHBOARD STATS 1-->
                    
                  </div>
            <!-- END CONTENT BODY -->
			</div>
            <!-- END CONTENT -->
            <!-- BEGIN QUICK SIDEBAR -->
            <a href="javascript:;" class="page-quick-sidebar-toggler">
                <i class="icon-login"></i>
            </a>
           <div class="page-quick-sidebar-wrapper" data-close-on-body-click="true" style="height:100%;">
                <div class="page-quick-sidebar">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-target="#quick_sidebar_tab_2" data-toggle="tab"><i class="fa fa-magic"></i>样式
                                
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" data-target="#quick_sidebar_tab_1" data-toggle="tab"><i class="glyphicon glyphicon-cog"></i> 形态
                                
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" data-target="#quick_sidebar_tab_4" data-toggle="tab"><i class="fa fa-paint-brush"></i> 主题
                                
                            </a>
                        </li>
                        <!-- <li class="">
                            <a href="javascript:;" data-target="#quick_sidebar_tab_3" data-toggle="tab"><i class="fa fa-magic"></i>静态
                                
                            </a>
                        </li> -->
						<!--
                        <li>
                            <a href="javascript:;" data-target="#quick_sidebar_tab_3" data-toggle="tab"><i class="glyphicon glyphicon-edit"></i>自定义
                                
                            </a>
                        </li>
						-->
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane page-quick-sidebar-chat" id="quick_sidebar_tab_1">
                            <div class="rightTabPanel" data-always-visible="1" data-handle-color="#b2d6f6" data-rail-color="#0085fb"  style="position: relative; overflow: hidden; width: auto; height: 504px;">
								<ul class="media-list list-items">
	                            </ul>
	                        </div>
                        </div>
	                        <!-- <div class="slimScrollDiv"  style="position: relative; overflow: hidden; width: auto; height: 504px;">
	                                                    <div class=" rightTabPanel" data-rail-color="#0085fb"  data-height="504" data-initialized="1" style="overflow: hidden; width: auto; height: 504px;">
	                                                        模板分类
	                                                        <ul class="media-list list-items">
	                                                           
	                                                        </ul>
	                                                    </div>
	                                                    <div class="slimScrollBar" style="width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 904px; background: rgb(187, 187, 187);"></div>
	                                                    <div class="slimScrollRail" style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(221, 221, 221);"></div>
	                                                    </div>
                        	</div> -->
                        <div class="tab-pane active page-quick-sidebar-alerts" id="quick_sidebar_tab_2">
                            <div class="rightTabPanel" data-opacity="1" railVisible="1" data-always-visible="1" data-handle-color="#b2d6f6" data-rail-color="#0085fb" style="position: relative; overflow: hidden; width: auto; height: 504px;">
								<div class="mt-element-list">
		                            <div class="mt-list-container list-simple ext-1 group">
		                            </div>
		                        </div>
		                    </div>
		                </div>
	                    <!-- <div class="slimScrollDiv"  style="position: relative; overflow: hidden; width: auto; height: 504px;">
                            <div class="rightTabPanel" data-height="504" data-initialized="1" style="overflow: hidden; width: auto; height: 504px;">
                                <div class="mt-element-list">
                                        <div class="mt-list-container list-simple ext-1 group">
                                         
                                        </div>
                                         
                                            </div>
                                        </div>
                              <div class="slimScrollBar" style="width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px; background: rgb(187, 187, 187);"></div><div class="slimScrollRail" style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(234, 234, 234);"></div>
                            </div>
                        </div> -->
                        <div class="tab-pane page-quick-sidebar-alerts" id="quick_sidebar_tab_4">
                            <div class="rightTabPanel" data-opacity="1" railVisible="1" data-always-visible="1" data-handle-color="#b2d6f6" data-rail-color="#0085fb" style="position: relative; overflow: hidden; width: auto; height: 504px;">
								<div class="compThemePanel">
									
								</div>
		                    </div>
                        </div>
                        <!-- <div class="tab-pane page-quick-sidebar-alerts" id="quick_sidebar_tab_3">
                            <div class="rightTabPanel" style="position: relative; overflow: hidden; width: auto; height: 904px;">
								<div class="mt-element-list">
                                    <div class="mt-list-container list-simple ext-1 group">
                                	</div>
                                </div>
                            </div>
                        </div> -->
                            <!-- <div class="slimScrollDiv"  style="position: relative; overflow: hidden; width: auto; height: 504px;">
                            <div class="page-quick-sidebar-alerts-list" data-height="904" data-initialized="1" style="overflow: hidden; width: auto; height: 904px;">
                                <div class="mt-element-list">
                                        <div class="mt-list-container list-simple ext-1 group">
                                         
                                        </div>
                                         
                                            </div>
                                        </div>
                              <div class="slimScrollBar" style="width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px; background: rgb(187, 187, 187);"></div><div class="slimScrollRail" style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(234, 234, 234);"></div>
                            </div>
                        	</div> -->
                        
                        <!--<div class="tab-pane page-quick-sidebar-settings" id="quick_sidebar_tab_3">
                            <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 904px;"><div class="page-quick-sidebar-settings-list" data-height="904" data-initialized="1" style="overflow: hidden; width: auto; height: 904px;">
                               
                            </div><div class="slimScrollBar" style="width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px; background: rgb(187, 187, 187);"></div><div class="slimScrollRail" style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(234, 234, 234);"></div></div>
                        </div>-->
                    </div>
                </div>
                <div class="componentlistPanel">
					<div class="componentlist portlet box dark">
						<div class="portlet-title">
							<div class="caption" style="font-size:14px;">
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
								<a href="#" class="reload"></a>
								<a href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body" style="padding:0;">
							<div  id="componentlist_list_panel">
								<div class="scroller">
									<!-- <table class="bg-dark bg-font-dark"> -->
									<table class="bg-font-dark">
										
									</table>
								</div>
							</div>
							<div  id="componentlist_ztree_panel" style="display:none;">
								<div class="scroller ztree" id="componentlist_ztree">
									
								</div>
							</div>
						</div>
					</div>
                </div>
            </div>
            <!-- END QUICK SIDEBAR -->
        </div>
        <!-- END CONTAINER -->
        <!-- BEGIN FOOTER -->
	    <!--
        <div class="page-footer">
            <div class="page-footer-inner"> 2015 &copy; 福建华闽通达信息技术有限公司 .
                <a href="#" title="联系我们" target="_blank">联系我们</a>
            </div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
		-->
		<form name="dForm" action="download" method="post">
	   <textarea id="pageLayout" name="pageLayout" style="display: none;"></textarea>
	   <textarea id="pageDesigner" name="pageDesigner" style="display: none;"></textarea>
	   </form>
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
                    <button class="btn btn-outline blue pull-left modalClose" type="button" data-dismiss="modal">取消</button>
                    <button class="btn btn-outline red modalContinue" type="button">继续</button>
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
       </div>
       <!--分栏框-->
       <div class="hidemy-modal">
	      <div id="divideModal" class="modal fade modal-warning" role="dialog" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title">分栏操作</h4>
                  </div>
                  <div class="modal-body" style="height:170px;">                                                    
                       <form id="divideForm" class="form-horizontal" action="#">
                       <div class="form-body">
                       <div class="form-group">
                           <label class="control-label col-md-3">栅格系统：</label>
                           <div class="col-md-9">
                             <input type="radio" name="gridsys" class="icheck" value="12" checked style="margin-top: 10px"/>12栅格
                             <input type="radio" name="gridsys" class="icheck" value="24" style="margin-top: 10px"/>24栅格
                             <input type="radio" name="gridsys" class="icheck" value="48" style="margin-top: 10px;margin-left: 5px;"/>48栅格
                           </div> 
                       </div>   
                       <div class="form-group">
                           <label class="control-label col-md-3">栅格分组：</label>
                           <div class="col-md-5">
                              <input id="groupNumber" name="groupNumber" class="form-control spaceTouchSpin spininput" min="1" max="12"/>
                           </div> 
                           <div class="col-md-4">
                              <button class="btn btn-default autoBt" type="button">自动分格</button>
                           </div>
                       </div>                                             
                       <div class="form-group has-error">
                           <label class="control-label col-md-3">组栅格数：</label>
                           <div class="col-md-9">
                              <input id="divideNumber" name="divideNumber" class="form-control" required/>（多组逗号隔开 如12栅格系统：分2组，每组占6个栅格 6,6）
                           </div> 
                       </div>
                       </div>      
                       </form>                                             
                  </div>
                  <div class="modal-footer">
                    <button class="btn btn-outline blue pull-left cancelBt" type="button" data-dismiss="modal">取消</button>
                    <button class="btn btn-outline red okBt" type="button">确定</button>
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
       </div>
	   <!--模板编辑窗口-->
	   <div class="hidemy-modal">
	      <div id="codeEditModal" class="modal fade" role="dialog" aria-hidden="true" style="min-height:680px;">
              <div class="modal-dialog" style="min-width:1100px;">
                <div class="modal-content">
                  <div class="modal-header">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                  </div>
                  <div class="modal-body" style="padding:3px;">                                                    
                       <iframe src="${contextPath}/mx/page/designer/metro/template?action=join&fn=getCurrentComponent" style="border:0px;" width="100%" height="650" style="width:100%;" allowtransparency="true"></iframe>                     
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
       </div>
	   <!--页面模板选择窗口-->
	   <div class="hidemy-modal">
	      <div id="pageTemplateModal" class="modal fade" role="dialog" aria-hidden="true" style="min-height:680px;">
              <div class="modal-dialog" style="min-width:1100px;">
                <div class="modal-content">
                  <div class="modal-header">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                  </div>
                  <div class="modal-body" style="padding:3px;">                                                    
                       <iframe src="${contextPath}/mx/page/designer/metro/pageTemplate" style="border:0px;" width="100%" height="650" style="width:100%;" allowtransparency="true"></iframe>                     
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
       </div>
	   <!--自由布局窗口-->
	   <div class="hidemy-modal">
	      <div id="freelayoutModal" class="modal fade" role="dialog" aria-hidden="true" style="min-height:250px;">
              <div class="modal-dialog" style="min-width:200px;">
                <div class="modal-content">
                  <div class="modal-header">
					<button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                  </div>
                  <div class="modal-body" style="padding:3px;">                                                    
                      <span>布局设置</span>
					  <table class="bannerL">
					     <tbody>
						   <tr>
						      <td style="width:150px;">
							   <div class="text-center laycell" layno="1" style="height:25px;width:150px;">1</div>
							  </td>
						   </tr>
						 </tbody>
					  </table>
					  <table class="bodyL">
					     <tbody>
						   <tr>
						      <td class="left" style="width:30px;">
							       <div class="text-center laycell" layno="2_1" style="height: 120px;width:30px;">2</div>
							  </td>
							  <td class="main" style="width:90px;">
							      <div class="text-center laycell" layno="2_2_1" style="height: 25px;width: 86px;display:block;margin-left:2px;">5</div>
								  <div class="text-center laycell" layno="2_2_2_1" style="height: 66px;width: 40px;display: inline-block;margin-left: 2px;margin-top: 2px;margin-bottom: 2px;">6</div>
								  <div class="text-center laycell" layno="2_2_2_2" style="height: 66px;width: 40px;display:inline-block;margin-left: 2px;margin-top: 2px;margin-bottom: 2px;">7</div>
							      <div class="text-center laycell" layno="2_2_3" style="height: 25px;width: 86px;display:block;margin-left:2px;">8</div>
							  </td>
							  <td class="right" style="width:30px;">
							    <div class="text-center laycell" layno="2_3" style="height: 120px;width:30px;">3</div>
							  </td>
						   </tr>
						 </tbody>
					  </table>   	
                      <table class="footerL">
					     <tbody>
						   <tr>
						      <td style="width:150px;">
							   <div class="text-center laycell" layno="3" style="height:25px;width:150px;">4</div>
							  </td>
						   </tr>
						 </tbody>
					  </table>   					  
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
       </div>
	    <!--文本编辑器窗口-->
	   <div class="hidemy-modal">
	      <div id="editModal" class="modal fade" role="dialog" aria-hidden="true" style="min-height:680px;">
              <div class="modal-dialog" style="min-width:1100px;">
                <div class="modal-content">
                  <div class="modal-header">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                  </div>
                  <div class="modal-body" style="padding:3px;">                                                    
                        <iframe src="${contextPath}/mx/page/designer/metro/editor" style="border:0px;" width="100%" height="650" 
						    style="width:100%;" allowtransparency="true"></iframe>                        
                  </div>
				  <div class="modal-footer">
                    <button class="btn btn-outline blue pull-left cancelEditBt" type="button" data-dismiss="modal">取消</button>
                    <button class="btn btn-outline red saveEditBt" type="button">确定</button>
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
       </div>
	   <!--导入设计-->
       <div class="hidemy-modal">
	      <div id="importDesignerModal" class="modal fade modal-warning" role="dialog" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header bg-blue bg-font-blue">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title">导入设计文件</h4>
                  </div>
                  <div class="modal-body" style="height:170px;">                                                    
                       <form id="importDesignerForm"  enctype="multipart/form-data">
                       <div class="form-body">
                         <div class="form-group text-center">
                           <div class="col-md-10">
						   <input id="fileinput" name="fileinput" class="file" type="file" data-preview-file-type="text">
						   </div>
                         </div>   
                       </div>      
                       </form>                                             
                  </div>
                  <div class="modal-footer">
                    <button class="btn blue pull-right cancelBt" type="button" data-dismiss="modal">关闭</button>
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
          </div>
       </div>
       <!-- <div class="commonStylePanel portlet box dark"> -->
       <!-- <div class="commonStylePanel"> -->
			<!-- <div class="portlet-title">
				<div class="caption" style="font-size:14px;">
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
					<a href="#" class="reload"></a>
					<a href="#" class="remove"></a>
				</div>
			</div> -->
			<!-- <div class="portlet-body" style="padding:0;">
			</div>
					</div> -->
       	<!-- <div class="componentlist portlet box dark componentlistfloat">
			<div class="portlet-title">
				<div class="caption" style="font-size:14px;">
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
					<a href="#" class="reload"></a>
					<a href="javascript:;" class="remove"></a>
				</div>
			</div>
			<div class="portlet-body" style="padding:0;">
				<div  id="componentlist_list_panel">
					<div class="scroller">
						<table class="bg-dark bg-font-dark">
							
						</table>
					</div>
				</div>
				<div  id="componentlist_ztree_panel" style="display:none;">
					<div class="scroller ztree" id="componentlist_ztree">
						
					</div>
				</div>
			</div>
		</div> -->
		<div class="x-Snapline"></div>
	   <div class="y-Snapline"></div>
	   <div id="elem_toolbar" class="elem_toolbar bg-font-dark border-white">
		   <div class="btn-group" style="border-right:1px #fff;">
				<button type="button" id="displayBt" class="btn btn-icon-only dark c-btn-border-1x c-btn-white" title="显示">
					<i class="fa fa-eye font-white"></i>
				</button>
				<button type="button" id="hideBt" class="btn btn-icon-only dark c-btn-border-1x c-btn-white" title="隐藏">
					<i class="fa fa-eye-slash font-white"></i>
				</button>
				
				<button type="button" id="upLayBt" class="btn btn-icon-only dark c-btn-border-1x c-btn-white" title="上移一层">
					<i class="fa fa-arrow-up font-white"></i>
				</button>
				<button type="button" id="downLayBt" class="btn btn-icon-only dark c-btn-border-1x c-btn-white" title="下移一层">
					<i class="fa fa-arrow-down font-white"></i>
				</button>
				
			</div>	
			<div class="btn-group" style="border-right:1px #fff;float:right;">
				<button type="button" id="copyBt" class="btn btn-icon-only dark" title="复制">
					<i class="fa fa-copy font-white"></i>
				</button>
				<button type="button" id="deleteBt" class="btn btn-icon-only dark" title="删除">
					<i class="fa fa-trash-o font-white"></i>
				</button>
		   </div>
        </div>

        <ul class="dropdown-menu leftControlMenu" style="font-size:12px;">
	        <li class="dropdown-submenu setCommomComp">
	            设置为常用控件
	        </li>
	    </ul>

	    <iframe name="operFrame" style="display:none;"></iframe> 
        <%@ include file="metro_template.jsp"%>
		<OBJECT ID=dlgHelper CLASSID="clsid:3050f819-98b5-11cf-bb82-00aa00bdce0b" WIDTH="0px" HEIGHT="0px"></OBJECT>
        <!-- END FOOTER -->
        <!--[if lt IE 9]>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/respond.min.js"></script>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${contextPath}/scripts/jquery.base64.js"></script>
		<script type="text/javascript">
        var contextPath='${contextPath}';
        var id='${param.id}';
        var pId='${param.pId}';
        var designerJson='${desingerJson}';
        var _ui = '${param.uiType}';
        //当前页面的主题ID
        var _themeTmpId = '${themeTmpId}';

        

		$(".designer").attr("data-rule",designerJson);
		
		if(designerJson!=''){
			var designerJsonObj=JSON.parse(designerJson);
			if(designerJsonObj['@backgroundImage']!=undefined&&designerJsonObj['@backgroundImage'].indexOf(".")>-1){
				designerJsonObj["backgroundImageFullPath"]=designerJsonObj['@backgroundImage'].replace("\/","");
				designerJsonObj["backgroundImageFullPath"]=designerJsonObj["backgroundImageFullPath"].replace(/\//g,"1sb");
			}
			less.refresh(false, designerJsonObj, true);
		}
		var selectedUiType='${param.uiType}';
		var objectCategoryJson='${objectCategoryJson}';
		if(selectedUiType==''){
			selectedUiType="bootstrap";
		}
		$("#ui-select>option[value='"+selectedUiType+"']").attr("selected",true);
        </script>
        <link href="${contextPath}/scripts/plugins/bootstrap/fullpage/css/jquery.fullPage.css" rel="stylesheet" type="text/css">
   		<script src="${contextPath}/scripts/plugins/bootstrap/fullpage/js/jquery.fullPage.js" type="text/javascript"></script>	
		<script src="${contextPath}/scripts/plugins/bootstrap/fullpage/js/juqery.fullpage.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/moment.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/morris/morris.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/morris/raphael-min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/counterup/jquery.waypoints.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/counterup/jquery.counterup.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
		<!--按钮-->
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <!-- 开始数值增量输入框 -->
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/fuelux/js/spinner.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>
		<!-- 结束数值增量输入框 -->
		<!-- 开始颜色选择器 -->
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-minicolors/jquery.minicolors.min.js" type="text/javascript"></script>
		<!-- 结束颜色选择器 -->
		<!--
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/nouislider/wNumb.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/nouislider/nouislider.min.js" type="text/javascript"></script>-->
		 <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/js/ion.rangeSlider.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-markdown/lib/markdown.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-markdown/js/bootstrap-markdown.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
		<!--开始下拉选择框>-->
		 <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
		<!--结束下拉选择框>-->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <!--<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/pages/scripts/dashboard.min.js" type="text/javascript"></script>-->
        <!-- END PAGE LEVEL SCRIPTS -->
        <script src="${contextPath}/scripts/designer/metro_designer_toolbox_new.js"></script>
        <!--控件列表插件-->
		<script src="${contextPath}/scripts/designer/metro_componentlist.js"></script>
		<!--控件主题设置-->
		<script src="${contextPath}/scripts/designer/metro_componentlist.js"></script>
		<script src="${contextPath}/scripts/designer/metro_compTheme.js"></script>

        <script src="${contextPath}/scripts/designer/commonStyleList.js"></script>
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/designer/layout/quick-sidebar.min.js" type="text/javascript"></script>
        <!-- END THEME LAYOUT SCRIPTS -->
        <!-- angular 1.4.3 -->
        <!-- 表单验证 -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
        <!-- select2 -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<%--         <script src="${contextPath}/scripts/plugins/bootstrap/select/jquery.metroselect.extends.js"></script> --%>
<%--         <script src="${contextPath}/scripts/plugins/bootstrap/select/jquery.metroselect_m.extends.js"></script> --%>
        <!-- icheck -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/plugins/bootstrap/icheck/jquery.checkbox.extends.js"></script>
        <script src="${contextPath}/scripts/plugins/bootstrap/icheck/jquery.radio.extends.js"></script>
        <script src="${contextPath}/scripts/designer/component.js"></script>
		<script src="${contextPath}/scripts/treeJson.js" type="text/javascript"></script>
		<!--模板静态化脚本-->
		<script src='${contextPath}/scripts/designer/comp_render_template.js'></script>
        <script src="${contextPath}/scripts/designer/metro_designer_new.js"></script>
		<script src="${contextPath}/scripts/designer/layout_define.js"></script>
	    <!-- 找不到该文件 -->
	    <!-- <script src="${contextPath}/scripts/designer/ui_init.js"></script> -->
        <script src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
        <!--找不到该文件-->
        <!-- <script src="${contextPath}/scripts/plugins/bootstrap/list/jquery.list.ext.js"></script> -->
        <!-- touchspin -->
        <script src="${contextPath}/scripts/plugins/bootstrap/touchspin/ext/jquery.touchspin.js"></script>
        <!-- tagsinput -->
        <script src="${contextPath}/scripts/plugins/bootstrap/tagsinput/bootstrap-tagsinput.js"></script>
        <!-- datepicker -->
        <script src="${contextPath}/scripts/plugins/bootstrap/datepicker/js/bootstrap-datepicker.min.js"></script>
 		<!-- datetimepicker -->      
        <script src="${contextPath}/scripts/plugins/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
        <!-- calendar -->
        <script src="${contextPath}/scripts/plugins/bootstrap/calendar/js/moment.min.js"></script>
        <script src="${contextPath}/scripts/plugins/bootstrap/calendar/js/fullcalendar.min.js"></script>
        <script src="${contextPath}/scripts/plugins/bootstrap/calendar/ext/lang-zh-cn.js"></script>
        <!-- treelist -->
        <script src="${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js"></script>
        <script src="${contextPath}/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js"></script>
        <!-- messenger -->
        <script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/messenger/build/js/messenger.min.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/messenger/build/js/messenger-theme-flat.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/messenger/build/js/messenger-theme-future.js"></script>
        <!-- megamenu -->        
        <!--<script src="${contextPath}/scripts/plugins/bootstrap/menu/js/bootstrap-menu.js"></script> 
        <script src="${contextPath}/scripts/designer/init/megamenu_init.js"></script>--> 
        <!-- dialog --> 
        <script src="${contextPath}/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
        <script src="${contextPath}/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
        <!-- tabs -->
        <script src="${contextPath}/scripts/plugins/bootstrap/tabs/js/jquery.tabs.extends.js" type="text/javascript"></script>
		<!--文件上传-->
		<script src="${contextPath}/scripts/designer/plugins/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
		<script src="${contextPath}/scripts/designer/plugins/bootstrap-fileinput/js/locales/zh.js" type="text/javascript"></script>
        <!-- login页面 
       	<script src="${contextPath}/scripts/plugins/bootstrap/login/js/jquery.validate.min.js"></script>
       	<script src="${contextPath}/scripts/plugins/bootstrap/login/js/additional-methods.min.js"></script>
       	<script src="${contextPath}/scripts/plugins/bootstrap/login/js/login.min.js"></script>
       	<script src="${contextPath}/scripts/plugins/bootstrap/login/js/backstretch.min.js"></script>
        <script src="${contextPath}/scripts/designer/init/login_init.js"></script>-->
        <!-- artTemplate -->
        <script src="${contextPath}/scripts/artTemplate/dist/template.js"></script>
        <script src="${contextPath}/scripts/artTemplate/dataUtils.js"></script>
        <script src="${contextPath}/scripts/plugins/bootstrap/portlet/jquery.portlet.extends.js"></script>
		<!-- gannt -->
        <script src="${contextPath}/scripts/plugins/bootstrap/gantt/js/iframeGantt.js"></script>
		 <!-- gannt -->
        <script src="${contextPath}/scripts/plugins/bootstrap/svgEditor/js/svgEditor.js"></script>
		<!-- destroy -->
        <script src="${contextPath}/scripts/designer/metro_destroy.js"></script>
        <!-- restore -->
        <script src="${contextPath}/scripts/designer/metro_restore.js"></script>
        <!-- 分页 -->
        <script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
        <!-- gridList -->
        <link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/gridList/css/gridList.extends.css'/>
        <script src='${contextPath}/scripts/plugins/bootstrap/gridList/ext/jquery.gridList.extends.js'></script>
        <!-- dhxgantt -->
        <link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/dhxgantt/codebase/dhtmlxgantt.css'/>
        <link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/dhxgantt/codebase/skins/dhtmlxgantt_contrast_white.css'/>
        <link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/dhxgantt/ext/dhxgantt.extend.css'/>
        <!-- mui -->
        <link rel='stylesheet' href='${contextPath}/scripts/mui/css/mui.update.css'/>
        <script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>
        <!-- levellist -->
        <link rel='stylesheet' href='/glaf/scripts/plugins/bootstrap/levellist/ext/levellist.extend.css'/>
        <link rel='stylesheet' href='/glaf/scripts/plugins/bootstrap/foldlist/ext/foldlist.extend.css'/>
        <!-- mswitch -->
        <link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/mswitch/mswitch.extend.css'/>
        <!-- muitab -->
        <script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/muitab/jquery.muitab.extend.js'></script>
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/muitab/muitab.extend.css">
        <!--loginmessagevalid-->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/loginmessagevalid/css/loginmessagevalid.css">
        <!-- rangecalendar -->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/rangecalendar/css/rangecalendar.css">
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/rangecalendar/css/rangecalendar.extend.css">
        <!--definedCardModel -->
		<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/definedCard/css/definedCard.css'/> 		
   	 <link href="${contextPath}/scripts/slideout/test/test.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/slideout/index.css" rel="stylesheet" type="text/css" />
   		<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css'>
   		<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/layer/skin/layer.css' />
   		<script type='text/javascript' src='${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js'></script>
		<script type="text/javascript" src="${contextPath}/scripts/kendo/bootstrap/ztree.extend.js"></script>
        <!--aspectlayout-->
        <link rel="stylesheet" href="${contextPath}/scripts/plugins/bootstrap/aspectlayout/aspectlayout.extend.css">
        <script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/aspectlayout/jquery.aspectlayout.extend.js'></script>
		
		<script src="${contextPath}/scripts/plugins/bootstrap/select/jquery.metroselect.extends.js"></script>
		<script src="${contextPath}/scripts/plugins/bootstrap/select/jquery.metroselect_m.extends.js"></script>

		<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/pageRuleTemplate.js"></script>
<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.js'></script>
        <script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>
       <script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/fastclick.js'></script> 	
		
    </body>
</html>

<template id="controlItem_template">
	<div class="compTheme_item">
	    <img class="theme_thumbnail" src="${contextPath}/mx/theme/sysThemeTmpControl/downloadThumbnail?controlId={{controlId}}&_{{nowdate}}" onerror="javascript:this.src='${contextPath}/scripts/designer/images/none.jpg'">
	    <div class="item_name_div">
	        <span class="item_name">{{controlName}}
	        </span>
	    </div>
	</div>
</template>