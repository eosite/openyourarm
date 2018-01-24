<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
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
	href="${contextPath}/scripts/home/${homeTheme}/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/${homeTheme}/awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/${homeTheme}/ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/${homeTheme}/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
<link rel="stylesheet"
	href="${contextPath}/scripts/home/${homeTheme}/dist/css/skins/skin-blue.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<style type="text/css">
.fancybox-overlay.fancybox-overlay-fixed{
	z-index:99999;
}
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
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Main Header -->
		<header class="main-header"> <!-- Logo --> <a
			href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
			<span class="logo-mini"><b>JDP</b></span> <!-- logo for regular state and mobile devices -->
			<span class="logo-lg"><img
				src="${contextPath}/scripts/home/${homeTheme}/dist/img/chissLOGO.png"
				style="margin-top: 10px;" /></span>
		</a> <!-- Header Navbar --> <nav class="navbar navbar-static-top"
			role="navigation"> <!-- Sidebar toggle button--> <a href="#"
			class="sidebar-toggle" data-toggle="offcanvas" role="button"> <span
			class="sr-only">Toggle navigation</span>
		</a> <!-- Navbar Right Menu -->
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- Messages: style can be found in dropdown.less-->
				<li class="dropdown messages-menu">
					<!-- Menu toggle button --> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-paint-brush"></i> <span
						class="label label-success"></span>
				</a>
				   <ul class="dropdown-menu">
						<li class="header">主题列表</li>
						<li>
							<!-- inner menu: contains the messages -->
							<ul class="menu">
							    <c:forEach items="${themeList}" var="themeItem" varStatus="i">
									<li>
									<!-- start message --> <a href="#" onclick="chooseLayout('${themeItem.ext1}', '${themeItem.ext2}','${themeItem.ext3}');setMyTheme();">
									   <div class="pull-left">
											<!-- User Image -->
											<img
												src="${contextPath}/scripts/home/${homeTheme}/dist/img/user2-160x160.jpg"
												class="img-circle" alt="User Image">
										</div> 
										${themeItem.name}<!-- The message -->
								    </a>
									</li>
								</c:forEach>
								<!-- end message -->
							</ul> <!-- /.menu -->
						</li>
						<!--<li class="footer"><a href="#">See All Messages</a></li>-->
					</ul>
				</li>
				<li class="dropdown messages-menu">
					<!-- Menu toggle button --> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-envelope-o"></i> <span
						class="label label-success">4</span>
				</a>
					<ul class="dropdown-menu">
						<li class="header">You have 4 messages</li>
						<li>
							<!-- inner menu: contains the messages -->
							<ul class="menu">
								<li>
									<!-- start message --> <a href="#">
										<div class="pull-left">
											<!-- User Image -->
											<img
												src="${contextPath}/scripts/home/${homeTheme}/dist/img/user2-160x160.jpg"
												class="img-circle" alt="User Image">
										</div> <!-- Message title and timestamp -->
										<h4>
											Support Team <small><i class="fa fa-clock-o"></i> 5
												mins</small>
										</h4> <!-- The message -->
										<p>Why not buy a new awesome theme?</p>
								</a>
								</li>
								<!-- end message -->
							</ul> <!-- /.menu -->
						</li>
						<li class="footer"><a href="#"></a></li>
					</ul>
				</li>
				<!-- /.messages-menu -->

				<!-- Notifications Menu -->
				<li class="dropdown notifications-menu">
					<!-- Menu toggle button --> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-bell-o"></i> <span
						class="label label-warning">10</span>
				</a>
					<ul class="dropdown-menu">
						<li class="header">You have 10 notifications</li>
						<li>
							<!-- Inner Menu: contains the notifications -->
							<ul class="menu">
								<li>
									<!-- start notification --> <a href="#"> <i
										class="fa fa-users text-aqua"></i> 5 new members joined today
								</a>
								</li>
								<!-- end notification -->
							</ul>
						</li>
						<li class="footer"><a href="#">View all</a></li>
					</ul>
				</li>
				<!-- Tasks Menu -->
				<li class="dropdown tasks-menu">
					<!-- Menu Toggle Button --> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-flag-o"></i> <span
						class="label label-danger">9</span>
				</a>
					<ul class="dropdown-menu">
						<li class="header">You have 9 tasks</li>
						<li>
							<!-- Inner menu: contains the tasks -->
							<ul class="menu">
								<li>
									<!-- Task item --> <a href="#"> <!-- Task title and progress text -->
										<h3>
											Design some buttons <small class="pull-right">20%</small>
										</h3> <!-- The progress bar -->
										<div class="progress xs">
											<!-- Change the css width attribute to simulate progress -->
											<div class="progress-bar progress-bar-aqua"
												style="width: 20%" role="progressbar" aria-valuenow="20"
												aria-valuemin="0" aria-valuemax="100">
												<span class="sr-only">20% Complete</span>
											</div>
										</div>
								</a>
								</li>
								<!-- end task item -->
							</ul>
						</li>
						<li class="footer"><a href="#">View all tasks</a></li>
					</ul>
				</li>
				<!-- User Account Menu -->
				<li class="dropdown user user-menu">
					<!-- Menu Toggle Button --> <a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <!-- The user image in the navbar--> <img
						src="${contextPath}/scripts/home/${homeTheme}/dist/img/demo1.jpg"
						class="user-image" alt="User Image"> <!-- hidden-xs hides the username on small devices so only the image appears. -->
							<span class="hidden-xs">${username}</span></a>
					<ul class="dropdown-menu">
						<!-- The user image in the menu -->
						<li class="user-header"><img
							src="${contextPath}/scripts/home/${homeTheme}/dist/img/demo1.jpg"
							class="img-circle" alt="User Image">

								<p>
									${username} - Web Developer <small>创建时间：${loginContext.user.mail}</small>
								</p></li>
						<!-- Menu Body -->
						<li class="user-body">
							<div class="row">
								<div class="col-xs-4 text-center">
									<a href="#">Followers</a>
								</div>
								<div class="col-xs-4 text-center">
									<a href="#">Sales</a>
								</div>
								<div class="col-xs-4 text-center">
									<a href="#">Friends</a>
								</div>
							</div> <!-- /.row -->
						</li>
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-left">
								<a href="#" class="btn btn-default btn-flat">个人信息</a>
							</div>
							<div class="pull-right">
								<a href="${contextPath}/mx/login/logout"
									class="btn btn-default btn-flat" target="_parent">注销</a>
							</div>
						</li>
					</ul>
				</li>
				<!-- Control Sidebar Toggle Button -->
				<!--<li><a href="#" data-toggle="control-sidebar"><i
						class="fa fa-gears"></i></a></li>-->
			</ul>
		</div>
		</nav> </header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar"> <!-- Sidebar user panel (optional) -->
		<div class="user-panel">
			<div class="pull-left image">
				<img
					src="${contextPath}/scripts/home/${homeTheme}/dist/img/demo1.jpg"
					class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p>${username}</p>
				<!-- Status -->
				<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
			</div>
		</div>

		<!-- search form (Optional) -->
		<form action="#" method="get" class="sidebar-form">
			<div class="input-group">
				<input type="text" name="q" class="form-control"
					placeholder="Search..."> <span class="input-group-btn">
						<button type="submit" name="search" id="search-btn"
							class="btn btn-flat">
							<i class="fa fa-search"></i>
						</button>
				</span>
			</div>
		</form>
		<!-- /.search form --> <!-- Sidebar Menu -->
		<ul class="sidebar-menu">
			<li class="header">主菜单栏</li>
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
			<section class="content-header">
			<h1>
				工作台<small>任务列表</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
				<li class="active">Here</li>
			</ol>
			</section>

			<!-- Main content -->
			<section class="content"> <!-- Your Page Content Here --> 
			
		    </section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<footer class="main-footer"> <!-- To the right -->
		<div class="pull-right hidden-xs clockTime"></div>
		<!-- Default to the left --> <strong>Copyright &copy; 2015 <a
			href="#">福建华闽通达信息技术有限公司.</a></strong> 版权所有. </footer>

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark"> <!-- Create the tabs -->
		<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
			<li class="active"><a href="#control-sidebar-home-tab"
				data-toggle="tab"><i class="fa fa-home"></i></a></li>
			<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i
					class="fa fa-gears"></i></a></li>
		</ul>
		<!-- Tab panes -->
		<div class="tab-content">
			<!-- Home tab content -->
			<div class="tab-pane active" id="control-sidebar-home-tab">
				<h3 class="control-sidebar-heading">Recent Activity</h3>
				<ul class="control-sidebar-menu">
					<li><a href="javascript::;"> <i
							class="menu-icon fa fa-birthday-cake bg-red"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

								<p>Will be 23 on April 24th</p>
							</div>
					</a></li>
				</ul>
				<!-- /.control-sidebar-menu -->

				<h3 class="control-sidebar-heading">Tasks Progress</h3>
				<ul class="control-sidebar-menu">
					<li><a href="javascript::;">
							<h4 class="control-sidebar-subheading">
								Custom Template Design <span
									class="label label-danger pull-right">70%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-danger" style="width: 70%"></div>
							</div>
					</a></li>
				</ul>
				<!-- /.control-sidebar-menu -->

			</div>
			<!-- /.tab-pane -->
			<!-- Stats tab content -->
			<div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab
				Content</div>
			<!-- /.tab-pane -->
			<!-- Settings tab content -->
			<div class="tab-pane" id="control-sidebar-settings-tab">
				<form method="post">
					<h3 class="control-sidebar-heading">General Settings</h3>

					<div class="form-group">
						<label class="control-sidebar-subheading"> Report panel
							usage <input type="checkbox" class="pull-right" checked>
						</label>

						<p>Some information about this general settings option</p>
					</div>
					<!-- /.form-group -->
				</form>
			</div>
			<!-- /.tab-pane -->
		</div>
		</aside>
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
		<input id="background" type="hidden" name="background"
		value="${userTheme.background}" /> <input id="backgroundType"
		name="backgroundType" type="hidden"
		value="${userTheme.backgroundType}" /> <input id="userThemeId"
		name="userThemeId" type="hidden" value="${userTheme.id}" />
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->

	<!-- jQuery 2.1.4 -->
	<script
		src="${contextPath}/scripts/home/${homeTheme}/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<!-- Bootstrap 3.3.5 -->
	<script
		src="${contextPath}/scripts/home/${homeTheme}/bootstrap/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script
		src="${contextPath}/scripts/home/${homeTheme}/dist/js/app.min.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
	<script type="text/javascript">
		var _mxm_ = {
			"children" : ${scripts}
		};
		initMenu(_mxm_.children);
		function initMenu(selectNode) {
			if ("children" in selectNode) {
				createMenu(selectNode.children, $(".sidebar-menu"), 0);
			}
		}

		function createMenu(nodes, pnode, level) {
			var l = level;
			if (pnode[0].localName && pnode[0].localName == "ul") {
				var lidom, adom;
				$
						.each(
								nodes,
								function(i, node) {
									l = level;
									lidom = $("<li></li>");
									if(l==0&&i==0){
									  lidom.addClass("active");
									}
									lidom.addClass("treeview");
									adom = $("<a></a>");
									adom.attr("href", "#");
									if (node.url && node.url != "") {
										adom.attr("onclick", "openUrl("
												+ node.id + ",\"" + node.name
												+ "\",\"" + node.url + "\",\""
												+ node.showmenu + "\")");
									}
									if (l == 0) {
										adom
												.append("<i class=\"fa fa-edit\"></i>");
									} else {
										adom
												.append("<i class=\"fa fa-hand-o-right\"></i>");
									}
									adom.append("<span>" + node.text
											+ "</span>");
									if ("children" in node) {
										adom
												.append("<i class=\"fa fa-angle-left pull-right\"></i>");
									}
									lidom.append(adom);
									if ("children" in node) {
										var uldom = $("<ul class=\"treeview-menu\"></ul>");
										createMenu(node.children, uldom, ++l);
									}
									lidom.append(uldom);
									pnode.append(lidom);
								});
			}
		}
		function openUrl(id, name, url, showmenu) {
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
			} else {
				$(document.getElementById("mainFrame")).attr("src", url);
				//$(".content").load(url);
			}
			 settingFrameHeight();
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
		  var contentTh = $(".content-wrapper").height();
		  var contentHh= $(".content-header").height();
		  var conentFh=$(".main-footer").height();
		  $(".content iframe").height(contentTh - contentHh-conentFh-30);
	    }
	     $(".content").html(
			"<iframe id=\"mainFrame\" src=\"\""+
					"style=\"width:100%;height:100%;\" frameborder=\"0\" scrolling=\"no\"/>"); 
	    $(function() {
	       settingFrameHeight();
	       $(".content iframe").attr("src", "${webPath}<%=com.glaf.base.utils.LoginContextUtil.getIndexUrl(request)%>");
	    });
	    $(window).resize(function() {
		  $(".content-wrapper").height($(window).height()-$(".main-header").height());
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
