<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.security.LoginContext"%>
<%@ page import="com.glaf.base.modules.BaseDataManager"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
	com.glaf.base.modules.sys.model.BaseDataInfo themeInfo=session.getAttribute("theme")!=null?(com.glaf.base.modules.sys.model.BaseDataInfo)session.getAttribute("theme"):null;
	String theme = themeInfo!=null?themeInfo.getExt3():com.glaf.core.util.RequestUtils.getTheme(request);
	String homeTheme = themeInfo!=null?themeInfo.getExt2():com.glaf.core.util.RequestUtils.getHomeTheme(request);
	request.setAttribute("homeTheme", homeTheme);
	String layoutTheme = themeInfo!=null?themeInfo.getExt1():com.glaf.core.util.RequestUtils.getLayoutTheme(request);
	request.setAttribute("layoutTheme", layoutTheme);
	if(themeInfo==null){
		String themeKey=layoutTheme+"|"+homeTheme+"|"+theme;
		BaseDataManager bm=BaseDataManager.getInstance();
		themeInfo=bm.getBaseData(themeKey,"dict_theme");
		session.setAttribute("theme",themeInfo);
	}
	String hideSysName=themeInfo!=null?themeInfo.getExt4():"0";
	//获取logo图片路径
	String logoPicPath=themeInfo!=null?themeInfo.getDesc():"";
	if(StringUtils.isEmpty(logoPicPath)){
		logoPicPath="scripts/home/metrolic/images/yellow_chissLOGOg.png";
	}else{
		logoPicPath=logoPicPath.trim();
	}
	request.setAttribute("logoPicPath", logoPicPath);
%>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.5
Version: 4.5.2
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title><%=SystemConfig.getString("res_system_name")%></title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/morris/morris.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/scripts/home/metrolic/css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 
		<style>
		   @media (max-width: 470px){
			.page-header.navbar .top-menu {
			  clear:none;
			}
		   }
		   .fancybox-overlay.fancybox-overlay-fixed{
				z-index:99999;
		   }
		</style>
		</head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-fixed page-footer-fixed page-content-white">
        <!-- BEGIN HEADER -->
        <div class="page-header navbar navbar-fixed-top">
            <!-- BEGIN HEADER INNER -->
            <div class="page-header-inner ">
                <!-- BEGIN LOGO -->
                <div class="page-logo">
                    <a href="#">
                        <img src="${contextPath}/${logoPicPath}" alt="logo" class="logo-default hidden-xs" style="margin: 8px 0 0;"/> </a>
                    <div class="menu-toggler sidebar-toggler"> </div>
                </div>
                <!-- END LOGO -->
                <!-- BEGIN RESPONSIVE MENU TOGGLER -->
                <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
                <!-- END RESPONSIVE MENU TOGGLER -->
                <!-- BEGIN TOP NAVIGATION MENU -->
                <div class="top-menu">
				    <div class="systitle hidden-xs">
							  <%=!hideSysName.equals("1")?SystemConfig.getString("res_system_name"):""%>
					</div>
                    <ul class="nav navbar-nav pull-right">
					    <!--开始主题-->
						 <li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">
                            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                <i class="fa fa-paint-brush"></i>
                                <span class="badge badge-default">${fn:length(themeList)}</span>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="external">
                                    <h3>
                                        <span class="bold"></span> 主题列表</h3>
                                    <a href="#">${fn:length(themeList)}种</a>
                                </li>
                                <li>
                                    <ul class="dropdown-menu-list scroller" style="height: 250px;" data-handle-color="#637283">
                                         <c:forEach items="${themeList}" var="themeItem" varStatus="i">
											<li>
											<!-- start message --> <a href="#" onclick="chooseLayout('${themeItem.ext1}', '${themeItem.ext2}','${themeItem.ext3}');setMyTheme();">
											   <span class="details">
                                                    <span class="label label-sm label-icon">
                                                        <img src="${contextPath}/images/palette.png"/>
                                                    </span>${themeItem.name}</span>
												<!-- The message -->
											</a>
											</li>
										</c:forEach>
                                    </ul>
                                </li>
                            </ul>
                        </li>
						<!--结束主题-->
                        <!-- BEGIN NOTIFICATION DROPDOWN -->
                        <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
						<!--
                        <li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">
                            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                <i class="icon-bell"></i>
                                <span class="badge badge-default"> 7 </span>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="external">
                                    <h3>
                                        <span class="bold">12 pending</span> notifications</h3>
                                    <a href="page_user_profile_1.html">view all</a>
                                </li>
                                <li>
                                    <ul class="dropdown-menu-list scroller" style="height: 250px;" data-handle-color="#637283">
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">just now</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-success">
                                                        <i class="fa fa-plus"></i>
                                                    </span> New user registered. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">3 mins</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-danger">
                                                        <i class="fa fa-bolt"></i>
                                                    </span> Server #12 overloaded. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">10 mins</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-warning">
                                                        <i class="fa fa-bell-o"></i>
                                                    </span> Server #2 not responding. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">14 hrs</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-info">
                                                        <i class="fa fa-bullhorn"></i>
                                                    </span> Application error. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">2 days</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-danger">
                                                        <i class="fa fa-bolt"></i>
                                                    </span> Database overloaded 68%. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">3 days</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-danger">
                                                        <i class="fa fa-bolt"></i>
                                                    </span> A user IP blocked. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">4 days</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-warning">
                                                        <i class="fa fa-bell-o"></i>
                                                    </span> Storage Server #4 not responding dfdfdfd. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">5 days</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-info">
                                                        <i class="fa fa-bullhorn"></i>
                                                    </span> System Error. </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="time">9 days</span>
                                                <span class="details">
                                                    <span class="label label-sm label-icon label-danger">
                                                        <i class="fa fa-bolt"></i>
                                                    </span> Storage server failed. </span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
						-->
                        <!-- END NOTIFICATION DROPDOWN -->
                        <!-- BEGIN INBOX DROPDOWN -->
                        <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                        <li class="dropdown dropdown-extended dropdown-inbox" id="header_inbox_bar">
                            <a id="messageBox" class="dropdown-toggle">
                                <i class="icon-envelope-open"></i>
                                <span class="badge badge-default"> 4 </span>
                            </a>
                            <!-- <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                <i class="icon-envelope-open"></i>
                                <span class="badge badge-default"> 4 </span>
                            </a> -->
							<!--
                            <ul class="dropdown-menu">
                                <li class="external">
                                    <h3>You have
                                        <span class="bold">7 New</span> Messages</h3>
                                    <a href="app_inbox.html">view all</a>
                                </li>
                                <li> 
									<ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283">
                                        <li>
                                            <a href="#">
                                                <span class="photo">
                                                    <img src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout3/img/avatar2.jpg" class="img-circle" alt=""> </span>
                                                <span class="subject">
                                                    <span class="from"> Lisa Wong </span>
                                                    <span class="time">Just Now </span>
                                                </span>
                                                <span class="message"> Vivamus sed auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="photo">
                                                    <img src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout3/img/avatar3.jpg" class="img-circle" alt=""> </span>
                                                <span class="subject">
                                                    <span class="from"> Richard Doe </span>
                                                    <span class="time">16 mins </span>
                                                </span>
                                                <span class="message"> Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="photo">
                                                    <img src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout3/img/avatar1.jpg" class="img-circle" alt=""> </span>
                                                <span class="subject">
                                                    <span class="from"> Bob Nilson </span>
                                                    <span class="time">2 hrs </span>
                                                </span>
                                                <span class="message"> Vivamus sed nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="photo">
                                                    <img src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout3/img/avatar2.jpg" class="img-circle" alt=""> </span>
                                                <span class="subject">
                                                    <span class="from"> Lisa Wong </span>
                                                    <span class="time">40 mins </span>
                                                </span>
                                                <span class="message"> Vivamus sed auctor 40% nibh congue nibh... </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <span class="photo">
                                                    <img src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout3/img/avatar3.jpg" class="img-circle" alt=""> </span>
                                                <span class="subject">
                                                    <span class="from"> Richard Doe </span>
                                                    <span class="time">46 mins </span>
                                                </span>
                                                <span class="message"> Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>-->
                        </li>
                        <!-- END INBOX DROPDOWN -->
                        <!-- BEGIN TODO DROPDOWN -->
                        <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                        <li class="dropdown dropdown-extended dropdown-tasks" id="header_task_bar">
                            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                <i class="icon-calendar"></i>
                                <span class="badge badge-default"> 3 </span>
                            </a>
							<!--
                            <ul class="dropdown-menu extended tasks">
                                <li class="external">
                                    <h3>You have
                                        <span class="bold">12 pending</span> tasks</h3>
                                    <a href="app_todo.html">view all</a>
                                </li>
                                <li>
                                    <ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283">
                                        <li>
                                            <a href="javascript:;">
                                                <span class="task">
                                                    <span class="desc">New release v1.2 </span>
                                                    <span class="percent">30%</span>
                                                </span>
                                                <span class="progress">
                                                    <span style="width: 40%;" class="progress-bar progress-bar-success" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">40% Complete</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="task">
                                                    <span class="desc">Application deployment</span>
                                                    <span class="percent">65%</span>
                                                </span>
                                                <span class="progress">
                                                    <span style="width: 65%;" class="progress-bar progress-bar-danger" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">65% Complete</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="task">
                                                    <span class="desc">Mobile app release</span>
                                                    <span class="percent">98%</span>
                                                </span>
                                                <span class="progress">
                                                    <span style="width: 98%;" class="progress-bar progress-bar-success" aria-valuenow="98" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">98% Complete</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="task">
                                                    <span class="desc">Database migration</span>
                                                    <span class="percent">10%</span>
                                                </span>
                                                <span class="progress">
                                                    <span style="width: 10%;" class="progress-bar progress-bar-warning" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">10% Complete</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="task">
                                                    <span class="desc">Web server upgrade</span>
                                                    <span class="percent">58%</span>
                                                </span>
                                                <span class="progress">
                                                    <span style="width: 58%;" class="progress-bar progress-bar-info" aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">58% Complete</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="task">
                                                    <span class="desc">Mobile development</span>
                                                    <span class="percent">85%</span>
                                                </span>
                                                <span class="progress">
                                                    <span style="width: 85%;" class="progress-bar progress-bar-success" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">85% Complete</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:;">
                                                <span class="task">
                                                    <span class="desc">New UI release</span>
                                                    <span class="percent">38%</span>
                                                </span>
                                                <span class="progress progress-striped">
                                                    <span style="width: 38%;" class="progress-bar progress-bar-important" aria-valuenow="18" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">38% Complete</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>-->
                        </li>
                        <!-- END TODO DROPDOWN -->
                        <!-- BEGIN USER LOGIN DROPDOWN -->
                        <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                        <li class="dropdown dropdown-user">
                            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                <img alt="" class="img-circle" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar3_small.jpg" />
                                <span class="username username-hide-on-mobile"> ${username} </span>
                                <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-default">
                                <li>
                                    <a href="#">
                                        <i class="icon-user"></i> 个人信息 </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="icon-calendar"></i> 工作日历 </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="icon-envelope-open"></i> 收件箱
                                        <span class="badge badge-danger"> 3 </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="icon-rocket"></i>任务列表
                                        <span class="badge badge-success"> 7 </span>
                                    </a>
                                </li>
                                <li class="divider"> </li>
                                <li>
                                    <a href="${contextPath}/mx/login/lock">
                                        <i class="icon-lock"></i> 锁屏</a>
                                </li>
                                <li>
                                    <a href="${contextPath}/mx/login/logout">
                                        <i class="icon-key"></i> 注销 </a>
                                </li>
                            </ul>
                        </li>
                        <!-- END USER LOGIN DROPDOWN -->
                        <!-- BEGIN QUICK SIDEBAR TOGGLER -->
                        <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                        <!--
						<li class="dropdown dropdown-quick-sidebar-toggler">
                            <a href="javascript:;" class="dropdown-toggle">
                                <i class="icon-logout"></i>
                            </a>
                        </li>
						-->
                        <!-- END QUICK SIDEBAR TOGGLER -->
                    </ul>
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
                    <ul id="sidebar-menu" class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="padding-top: 20px">
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
                            <form class="sidebar-search  " action="page_general_search_3.html" method="POST">
                                <a href="javascript:;" class="remove">
                                    <i class="icon-close"></i>
                                </a>
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search...">
                                    <span class="input-group-btn">
                                        <a href="javascript:;" class="btn submit">
                                            <i class="icon-magnifier"></i>
                                        </a>
                                    </span>
                                </div>
                            </form>
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
                    <div class="theme-panel hidden-xs hidden-sm" style="display:none;">
                        <div class="toggler"> </div>
                        <div class="toggler-close"> </div>
                        <div class="theme-options">
                            <div class="theme-option theme-colors clearfix">
                                <span> 主题颜色 </span>
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
                                <span> 主题风格 </span>
                                <select class="layout-style-option form-control input-sm">
                                    <option value="square" selected="selected">方角</option>
                                    <option value="rounded">圆角</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 布局 </span>
                                <select class="layout-option form-control input-sm">
                                    <option value="fluid" selected="selected">展开</option>
                                    <option value="boxed">盒子</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 头部 </span>
                                <select class="page-header-option form-control input-sm">
                                    <option value="fixed" selected="selected">固定</option>
                                    <option value="default">默认</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 顶部菜单</span>
                                <select class="page-header-top-dropdown-style-option form-control input-sm">
                                    <option value="light" selected="selected">明亮</option>
                                    <option value="dark">暗色</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 边栏样式</span>
                                <select class="sidebar-option form-control input-sm">
                                    <option value="fixed">固定</option>
                                    <option value="default" selected="selected">默认</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 边栏菜单 </span>
                                <select class="sidebar-menu-option form-control input-sm">
                                    <option value="accordion" selected="selected">手风琴</option>
                                    <option value="hover">悬浮</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 边栏样式 </span>
                                <select class="sidebar-style-option form-control input-sm">
                                    <option value="default" selected="selected">默认</option>
                                    <option value="light">明亮</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 边栏位置 </span>
                                <select class="sidebar-pos-option form-control input-sm">
                                    <option value="left" selected="selected">左侧</option>
                                    <option value="right">右侧</option>
                                </select>
                            </div>
                            <div class="theme-option">
                                <span> 页脚 </span>
                                <select class="page-footer-option form-control input-sm">
                                    <option value="fixed">固定</option>
                                    <option value="default" selected="selected">默认</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <div class="page-bar">
                        <ul class="page-breadcrumb">
                            <li>
                                <a href="#" onclick="setHomePage();">主页</a>
                                <i class="fa fa-circle"></i>
                            </li>
                        </ul>
                        <div class="page-toolbar">
                           
                        </div>
                    </div>
					<div class="row contentRow" style="padding-top:5px;">
					   <div class="col-md-12 content">
					   </div>
					</div>
                </div>
                <!-- END CONTENT BODY -->
            </div>
            <!-- END CONTENT -->
            <!-- BEGIN QUICK SIDEBAR -->
            <a href="javascript:;" class="page-quick-sidebar-toggler">
                <i class="icon-login"></i>
            </a>
            <div class="page-quick-sidebar-wrapper" data-close-on-body-click="false">
                <div class="page-quick-sidebar">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-target="#quick_sidebar_tab_1" data-toggle="tab"> Users
                                <span class="badge badge-danger">2</span>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" data-target="#quick_sidebar_tab_2" data-toggle="tab"> Alerts
                                <span class="badge badge-success">7</span>
                            </a>
                        </li>
                        <li class="dropdown">
                            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> More
                                <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu pull-right">
                                <li>
                                    <a href="javascript:;" data-target="#quick_sidebar_tab_3" data-toggle="tab">
                                        <i class="icon-bell"></i> Alerts </a>
                                </li>
                                <li>
                                    <a href="javascript:;" data-target="#quick_sidebar_tab_3" data-toggle="tab">
                                        <i class="icon-info"></i> Notifications </a>
                                </li>
                                <li>
                                    <a href="javascript:;" data-target="#quick_sidebar_tab_3" data-toggle="tab">
                                        <i class="icon-speech"></i> Activities </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="javascript:;" data-target="#quick_sidebar_tab_3" data-toggle="tab">
                                        <i class="icon-settings"></i> Settings </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active page-quick-sidebar-chat" id="quick_sidebar_tab_1">
                            <div class="page-quick-sidebar-chat-users" data-rail-color="#ddd" data-wrapper-class="page-quick-sidebar-list">
                                <h3 class="list-heading">Staff</h3>
                                <ul class="media-list list-items">
                                    <li class="media">
                                        <div class="media-status">
                                            <span class="badge badge-success">8</span>
                                        </div>
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar3.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Bob Nilson</h4>
                                            <div class="media-heading-sub"> Project Manager </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar1.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Nick Larson</h4>
                                            <div class="media-heading-sub"> Art Director </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <div class="media-status">
                                            <span class="badge badge-danger">3</span>
                                        </div>
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar4.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Deon Hubert</h4>
                                            <div class="media-heading-sub"> CTO </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar2.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Ella Wong</h4>
                                            <div class="media-heading-sub"> CEO </div>
                                        </div>
                                    </li>
                                </ul>
                                <h3 class="list-heading">Customers</h3>
                                <ul class="media-list list-items">
                                    <li class="media">
                                        <div class="media-status">
                                            <span class="badge badge-warning">2</span>
                                        </div>
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar6.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Lara Kunis</h4>
                                            <div class="media-heading-sub"> CEO, Loop Inc </div>
                                            <div class="media-heading-small"> Last seen 03:10 AM </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <div class="media-status">
                                            <span class="label label-sm label-success">new</span>
                                        </div>
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar7.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Ernie Kyllonen</h4>
                                            <div class="media-heading-sub"> Project Manager,
                                                <br> SmartBizz PTL </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar8.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Lisa Stone</h4>
                                            <div class="media-heading-sub"> CTO, Keort Inc </div>
                                            <div class="media-heading-small"> Last seen 13:10 PM </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <div class="media-status">
                                            <span class="badge badge-success">7</span>
                                        </div>
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar9.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Deon Portalatin</h4>
                                            <div class="media-heading-sub"> CFO, H&D LTD </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar10.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Irina Savikova</h4>
                                            <div class="media-heading-sub"> CEO, Tizda Motors Inc </div>
                                        </div>
                                    </li>
                                    <li class="media">
                                        <div class="media-status">
                                            <span class="badge badge-danger">4</span>
                                        </div>
                                        <img class="media-object" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar11.jpg" alt="...">
                                        <div class="media-body">
                                            <h4 class="media-heading">Maria Gomez</h4>
                                            <div class="media-heading-sub"> Manager, Infomatic Inc </div>
                                            <div class="media-heading-small"> Last seen 03:10 AM </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="page-quick-sidebar-item">
                                <div class="page-quick-sidebar-chat-user">
                                    <div class="page-quick-sidebar-nav">
                                        <a href="javascript:;" class="page-quick-sidebar-back-to-list">
                                            <i class="icon-arrow-left"></i>Back</a>
                                    </div>
                                    <div class="page-quick-sidebar-chat-user-messages">
                                        <div class="post out">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar3.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Bob Nilson</a>
                                                <span class="datetime">20:15</span>
                                                <span class="body"> When could you send me the report ? </span>
                                            </div>
                                        </div>
                                        <div class="post in">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar2.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Ella Wong</a>
                                                <span class="datetime">20:15</span>
                                                <span class="body"> Its almost done. I will be sending it shortly </span>
                                            </div>
                                        </div>
                                        <div class="post out">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar3.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Bob Nilson</a>
                                                <span class="datetime">20:15</span>
                                                <span class="body"> Alright. Thanks! :) </span>
                                            </div>
                                        </div>
                                        <div class="post in">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar2.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Ella Wong</a>
                                                <span class="datetime">20:16</span>
                                                <span class="body"> You are most welcome. Sorry for the delay. </span>
                                            </div>
                                        </div>
                                        <div class="post out">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar3.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Bob Nilson</a>
                                                <span class="datetime">20:17</span>
                                                <span class="body"> No probs. Just take your time :) </span>
                                            </div>
                                        </div>
                                        <div class="post in">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar2.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Ella Wong</a>
                                                <span class="datetime">20:40</span>
                                                <span class="body"> Alright. I just emailed it to you. </span>
                                            </div>
                                        </div>
                                        <div class="post out">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar3.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Bob Nilson</a>
                                                <span class="datetime">20:17</span>
                                                <span class="body"> Great! Thanks. Will check it right away. </span>
                                            </div>
                                        </div>
                                        <div class="post in">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar2.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Ella Wong</a>
                                                <span class="datetime">20:40</span>
                                                <span class="body"> Please let me know if you have any comment. </span>
                                            </div>
                                        </div>
                                        <div class="post out">
                                            <img class="avatar" alt="" src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/img/avatar3.jpg" />
                                            <div class="message">
                                                <span class="arrow"></span>
                                                <a href="javascript:;" class="name">Bob Nilson</a>
                                                <span class="datetime">20:17</span>
                                                <span class="body"> Sure. I will check and buzz you if anything needs to be corrected. </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="page-quick-sidebar-chat-user-form">
                                        <div class="input-group">
                                            <input type="text" class="form-control" placeholder="Type a message here...">
                                            <div class="input-group-btn">
                                                <button type="button" class="btn green">
                                                    <i class="icon-paper-clip"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane page-quick-sidebar-alerts" id="quick_sidebar_tab_2">
                            <div class="page-quick-sidebar-alerts-list">
                                <h3 class="list-heading">General</h3>
                                <ul class="feeds list-items">
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-info">
                                                        <i class="fa fa-check"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> You have 4 pending tasks.
                                                        <span class="label label-sm label-warning "> Take action
                                                            <i class="fa fa-share"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> Just now </div>
                                        </div>
                                    </li>
                                    <li>
                                        <a href="javascript:;">
                                            <div class="col1">
                                                <div class="cont">
                                                    <div class="cont-col1">
                                                        <div class="label label-sm label-success">
                                                            <i class="fa fa-bar-chart-o"></i>
                                                        </div>
                                                    </div>
                                                    <div class="cont-col2">
                                                        <div class="desc"> Finance Report for year 2013 has been released. </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col2">
                                                <div class="date"> 20 mins </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-danger">
                                                        <i class="fa fa-user"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> You have 5 pending membership that requires a quick review. </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 24 mins </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-info">
                                                        <i class="fa fa-shopping-cart"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> New order received with
                                                        <span class="label label-sm label-success"> Reference Number: DR23923 </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 30 mins </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-success">
                                                        <i class="fa fa-user"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> You have 5 pending membership that requires a quick review. </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 24 mins </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-info">
                                                        <i class="fa fa-bell-o"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> Web server hardware needs to be upgraded.
                                                        <span class="label label-sm label-warning"> Overdue </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 2 hours </div>
                                        </div>
                                    </li>
                                    <li>
                                        <a href="javascript:;">
                                            <div class="col1">
                                                <div class="cont">
                                                    <div class="cont-col1">
                                                        <div class="label label-sm label-default">
                                                            <i class="fa fa-briefcase"></i>
                                                        </div>
                                                    </div>
                                                    <div class="cont-col2">
                                                        <div class="desc"> IPO Report for year 2013 has been released. </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col2">
                                                <div class="date"> 20 mins </div>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                                <h3 class="list-heading">System</h3>
                                <ul class="feeds list-items">
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-info">
                                                        <i class="fa fa-check"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> You have 4 pending tasks.
                                                        <span class="label label-sm label-warning "> Take action
                                                            <i class="fa fa-share"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> Just now </div>
                                        </div>
                                    </li>
                                    <li>
                                        <a href="javascript:;">
                                            <div class="col1">
                                                <div class="cont">
                                                    <div class="cont-col1">
                                                        <div class="label label-sm label-danger">
                                                            <i class="fa fa-bar-chart-o"></i>
                                                        </div>
                                                    </div>
                                                    <div class="cont-col2">
                                                        <div class="desc"> Finance Report for year 2013 has been released. </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col2">
                                                <div class="date"> 20 mins </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-default">
                                                        <i class="fa fa-user"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> You have 5 pending membership that requires a quick review. </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 24 mins </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-info">
                                                        <i class="fa fa-shopping-cart"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> New order received with
                                                        <span class="label label-sm label-success"> Reference Number: DR23923 </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 30 mins </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-success">
                                                        <i class="fa fa-user"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> You have 5 pending membership that requires a quick review. </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 24 mins </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="col1">
                                            <div class="cont">
                                                <div class="cont-col1">
                                                    <div class="label label-sm label-warning">
                                                        <i class="fa fa-bell-o"></i>
                                                    </div>
                                                </div>
                                                <div class="cont-col2">
                                                    <div class="desc"> Web server hardware needs to be upgraded.
                                                        <span class="label label-sm label-default "> Overdue </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col2">
                                            <div class="date"> 2 hours </div>
                                        </div>
                                    </li>
                                    <li>
                                        <a href="javascript:;">
                                            <div class="col1">
                                                <div class="cont">
                                                    <div class="cont-col1">
                                                        <div class="label label-sm label-info">
                                                            <i class="fa fa-briefcase"></i>
                                                        </div>
                                                    </div>
                                                    <div class="cont-col2">
                                                        <div class="desc"> IPO Report for year 2013 has been released. </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col2">
                                                <div class="date"> 20 mins </div>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="tab-pane page-quick-sidebar-settings" id="quick_sidebar_tab_3">
                            <div class="page-quick-sidebar-settings-list">
                                <h3 class="list-heading">General Settings</h3>
                                <ul class="list-items borderless">
                                    <li> Enable Notifications
                                        <input type="checkbox" class="make-switch" checked data-size="small" data-on-color="success" data-on-text="ON" data-off-color="default" data-off-text="OFF"> </li>
                                    <li> Allow Tracking
                                        <input type="checkbox" class="make-switch" data-size="small" data-on-color="info" data-on-text="ON" data-off-color="default" data-off-text="OFF"> </li>
                                    <li> Log Errors
                                        <input type="checkbox" class="make-switch" checked data-size="small" data-on-color="danger" data-on-text="ON" data-off-color="default" data-off-text="OFF"> </li>
                                    <li> Auto Sumbit Issues
                                        <input type="checkbox" class="make-switch" data-size="small" data-on-color="warning" data-on-text="ON" data-off-color="default" data-off-text="OFF"> </li>
                                    <li> Enable SMS Alerts
                                        <input type="checkbox" class="make-switch" checked data-size="small" data-on-color="success" data-on-text="ON" data-off-color="default" data-off-text="OFF"> </li>
                                </ul>
                                <h3 class="list-heading">System Settings</h3>
                                <ul class="list-items borderless">
                                    <li> Security Level
                                        <select class="form-control input-inline input-sm input-small">
                                            <option value="1">Normal</option>
                                            <option value="2" selected>Medium</option>
                                            <option value="e">High</option>
                                        </select>
                                    </li>
                                    <li> Failed Email Attempts
                                        <input class="form-control input-inline input-sm input-small" value="5" /> </li>
                                    <li> Secondary SMTP Port
                                        <input class="form-control input-inline input-sm input-small" value="3560" /> </li>
                                    <li> Notify On System Error
                                        <input type="checkbox" class="make-switch" checked data-size="small" data-on-color="danger" data-on-text="ON" data-off-color="default" data-off-text="OFF"> </li>
                                    <li> Notify On SMTP Error
                                        <input type="checkbox" class="make-switch" checked data-size="small" data-on-color="warning" data-on-text="ON" data-off-color="default" data-off-text="OFF"> </li>
                                </ul>
                                <div class="inner-content">
                                    <button class="btn btn-success">
                                        <i class="icon-settings"></i> Save Changes</button>
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
        <div class="page-footer">
		    <div class="pull-right hidden-xs clockTime" style="color:white;"></div>
            <div class="pull-center page-footer-inner"> <%=SystemConfig.getString("res_copyright")%>
            </div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
		<input id="background" type="hidden" name="background"
		value="${userTheme.background}" /> <input id="backgroundType"
		name="backgroundType" type="hidden"
		value="${userTheme.backgroundType}" /> <input id="userThemeId"
		name="userThemeId" type="hidden" value="${userTheme.id}" />
        <!-- END FOOTER -->
        <!--[if lt IE 9]>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/respond.min.js"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
        <script type="text/javascript">
		 var contextPath='${contextPath}';
		</script>
        <!-- BEGIN CORE PLUGINS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
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
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amcharts/amcharts.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amcharts/serial.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amcharts/pie.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amcharts/radar.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amcharts/themes/light.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amcharts/themes/patterns.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amcharts/themes/chalk.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/ammap/ammap.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/ammap/maps/js/worldLow.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/amcharts/amstockcharts/amstock.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/flot/jquery.flot.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/flot/jquery.flot.resize.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/flot/jquery.flot.categories.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-easypiechart/jquery.easypiechart.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.sparkline.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/pages/scripts/dashboard.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/home/metrolic/home.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
        <!-- END THEME LAYOUT SCRIPTS -->
		<script type="text/javascript">
         console.log('${themeList}');
		  var _mxm_ = {
			"children" : ${scripts}
		};
		initMenu(_mxm_.children);
		function initMenu(selectNode) {
			if ("children" in selectNode) {
				$("#sidebar-menu .sidebar-search-wrapper").nextAll().remove();
			    $("#sidebar-menu").append("<li class=\"heading\"><h3 class=\"uppercase\">菜单列表</h3></li>");
				createMenu(selectNode.children, $("#sidebar-menu"), 0);
			}
		}

		function createMenu(nodes, pnode, level) {
			var l = level;
			if (pnode[0].localName && pnode[0].localName == "ul") {
				var lidom, adom,spandom;
				$
						.each(
								nodes,
								function(i, node) {
									l = level;
									lidom = $("<li></li>");
									if(l==0&&i==0){
									  lidom.addClass("active");
									}
									lidom.addClass("nav-item");
									adom = $("<a></a>");
									adom.addClass("nav-link");
									adom.attr("href", "#");
									if (node.url && node.url != "") {
										adom.attr("onclick", "openUrl(this,"
												+ node.id + ",\"" + node.name
												+ "\",\"" + node.url + "\",\""
												+ node.showmenu + "\")");
									}
									if (l == 0) {
										adom
												.append("<i class=\"icon-docs\"></i>");
			                               
									} else {
										adom
												.append("<i></i>");
									}
									adom.append("<span  class=\"title\">" + node.text
											+ "</span>");
									spandom=$(" <span class=\"arrow\"></span>");
                                    
									if("children" in node){
										adom.addClass("nav-toggle");
										if(lidom.hasClass("open")){
										  adom.append("<span class=\"selected\"></span>");
										  spandom.addClass("open");
									    }
										adom.append(spandom);
									}
									
									lidom.append(adom);
									if ("children" in node) {
										var uldom = $("<ul class=\"sub-menu\"></ul>");
										createMenu(node.children,uldom,++l);
									}
									lidom.append(uldom);
									pnode.append(lidom);
								});
			}
		}
		function openUrl(e,id, name, url, showmenu) {
			var menuArr=new Array();
			 //获取导航路径
			var menuName=$(e).find(".title").text();
			menuArr.push(menuName);
			var $e=$(e).closest("ul");
			//获取上级菜单路径
			while($e.length>0){
				var parentMenu=$e.prev("a").find(".title").text();
				if(parentMenu!='')
				{
					menuArr.push(parentMenu);
				}
				$e=$e.parents("ul:first");
			}
			$(".page-breadcrumb").children("li:first").nextAll().remove();
			if(menuArr&&menuArr.length>0){
				$(menuArr.reverse()).each(function(i,menuitem){
					if(i<menuArr.length-1){
						$(".page-breadcrumb").append("<li><span>"+menuitem+"</span><i class=\"fa fa-circle\"></i></li>");
					}
					else{
						$(".page-breadcrumb").append("<li><span>"+menuitem+"</span></li>");
					}
				});
			}
			//addTab
			url = "${contextPath}" + url;
			if (showmenu && showmenu == 2) {
				window.open(url, "_blank");

			}else if(showmenu && showmenu == 3){
                $.fancybox.open({
                    href:url,
                    width:500,
                    height:500,
                    type: 'iframe',
                    modal:false,
                    closeClick : true,
                    openEffect : 'none',
                    padding : 5,
                    autoSize   : false,
                    autoHeight : false,
                    autoWidth  : false,
                    helpers : {
                        title : null
                    }
                });
            }else {
				$(document.getElementById("mainFrame")).attr("src", url);
				//$(".content").load(url);
			}
			 //$('.page-header .responsive-toggler').click();
			 settingFrameHeight();
			 $(e).closest(".page-sidebar-menu").find(".active").removeClass("active");
			 //设置选中
			 $(e).closest("li").addClass("active");
			 //顶层选项
			 var $this=$(e);
			 var topFlag=false;
			 while(!topFlag){
				 $this=$this.parents("li:first");
				 if($this.parent().attr("id")=="sidebar-menu"){
					 topFlag=true;
				 }
			 }
			$this.addClass("active");
			var resBreakpointMd = App.getResponsiveBreakpoint('md');
			if (App.getViewPort().width < resBreakpointMd && $('.page-sidebar').hasClass("in")) { 
			    // close the menu on mobile view while laoding a page 
                $('.page-header .responsive-toggler').click();
            }
		}
		//显示当前时间
		function CurentTime() {
			var now = new Date();
			var year = now.getYear();
			var month = now.getMonth();
			var day = now.getDate();
			var hh = now.getHours();
			var mm = now.getMinutes();
			var ss = now.getTime() % 60000;
			ss = (ss - (ss % 1000)) / 1000;
			var clock = (1900 + year) + "年" + (month + 1) + "月" + day + "日   "
					+ hh + ':';
			if (mm < 10)
				clock += '0';
			clock += mm + ':';
			if (ss < 10)
				clock += '0';
			clock += ss;
			return (clock);
		}
		function refreshCalendarClock() //
		{
			$(".clockTime").html(CurentTime());
		}
		setInterval('refreshCalendarClock()', 1000);//1秒钟刷新1次当前时间
		
		//重新计算iframe高度
		function settingFrameHeight() {
		  $(".page-content").outerHeight($(window).height()-$(".page-header").outerHeight()-$(".page-footer").outerHeight())
		  var contentTh = $(".page-content").height();
		  var contentHh= $(".page-bar").height();
		  $(".content iframe").height(contentTh - contentHh+20);
	    }
	     $(".content").html(
			"<iframe id=\"mainFrame\" src=\"\""+
					"style=\"width:100%;height:100%;\" frameborder=\"0\" scrolling=\"auto\"/>"); 
	    $(function() {
		   settingFrameHeight();
	       setHomePage();
	    });
		function setHomePage(){
			
			 $(".content iframe").attr("src", "${webPath}<%=com.glaf.base.utils.LoginContextUtil.getIndexUrl(request)%>");
			 $(".page-breadcrumb").children("li:first").nextAll().remove();
		}
	    $(window).resize(function() {
		  settingFrameHeight();
		});
		var theme="${theme}";
		var layoutModel="default_home";
		var homeTheme="${homeTheme}";
		function chooseLayout(ly,color,uicolor){
		   this.layoutModel=ly;
		   this.theme=uicolor;
		   this.homeTheme=color;
		   $(".themeLayoutContent").css("background","");
		   $(window.event.srcElement).css("background","yellow");
		}
		function setMyTheme(){
		   var theme = this.theme;
		   var homeTheme=this.homeTheme;
		   var layoutModel =this.layoutModel;
		   var background = $('#background').val();
		   var userThemeId = $('#userThemeId').val();
		   var backgroundType = $('#backgroundType').val();
		   var param = {'themeStyle':theme,'homeThemeStyle':homeTheme,'layoutModel':layoutModel , 'background':background 
				   ,'userThemeId':userThemeId,'backgroundType':backgroundType} ;
		   jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/my/home/setTheme',
			dataType : 'json',
			data : param,
			error : function(data) {
				alert('服务器处理错误！');
			},
			success : function(data) {
				 window.location="<%=request.getContextPath()%>/mx/my/home";
				//location.reload();
				//document.getElementById("themeui").href=contextPath+"/scripts/easyui/themes/"+theme+"/easyui.css";
			}
		});
	}
    $(function(){
       
        $('#messageBox').click(function(){
            var message_link = '<%=SystemConfig.getString("message_link")%>';
            if(message_link){
                window.open(message_link);
                // window.location.href = message_link;
            }
        })
    })
	//弹窗事件触发源所在页面window
		var eventView;
		//打开窗口
		function openFancybox(eventWin,url,title,width,height){
			//eventWin.view.location=eventWin.view.location;
			eventView=eventWin.view;
			if(width==undefined){
				width="99%";
			}
			if(height==undefined){
				height="99%";
			}
			$.fancybox.open({
						href:url,
						width:width,
						height:height,
						type: 'iframe',
						modal:false,
						closeClick : true,
						openEffect : 'none',
						padding : 5,
						autoSize   : false,
				        autoHeight : false,
				        autoWidth  : false,
						helpers : {
							title : null
				       }
			});
		}
		$(function(){
			var pwdUpdateFlag='${pwdUpdateFlag}';
			if(pwdUpdateFlag!='1'){
				var url="${contextPath}/mx/identity/user/prepareModifyPwd";
				openFancybox(window,url,"修改用户密码",500,400);
			}
		});
		</script>
    </body>

</html>