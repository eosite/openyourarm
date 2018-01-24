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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
	if (request.getAttribute("userTheme") != null) {
		UserTheme userTheme = (UserTheme) request
				.getAttribute("userTheme");
		request.setAttribute("theme", userTheme.getThemeStyle());
		request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
	} else {
		String theme = com.glaf.core.util.RequestUtils
				.getTheme(request);
		request.setAttribute("theme", theme);
		String homeTheme = com.glaf.core.util.RequestUtils
				.getHomeTheme(request);
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
<link data-jsfiddle="common" rel="stylesheet" media="screen"
	href="${contextPath}/scripts/handsontable/handsontable.css">
<link data-jsfiddle="common" rel="stylesheet" media="screen"
	href="${contextPath}/scripts/handsontable/pikaday/pikaday.css">
<link rel="stylesheet" href="${contextPath}/scripts/jquery-ui.min.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  <style type="text/css">
      .colCss{
        background-color: #45c8dc;
        border:10px;
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
			<span class="logo-mini"><b>PSD</b></span> <!-- logo for regular state and mobile devices -->
			<span class="logo-lg">页面设计器</span>
		</a> <!-- Header Navbar --> <nav class="navbar navbar-static-top"
			role="navigation"> <!-- Sidebar toggle button--> <a href="#"
			class="sidebar-toggle" data-toggle="offcanvas" role="button"> <span
			class="sr-only">Toggle navigation</span>
		</a> <!-- Navbar Right Menu --> </nav> </header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar"> <!-- Sidebar user panel (optional) -->
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
			<section class="content-header">
			<h1>
				设计区<small></small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
				<li class="active">Here</li>
			</ol>
			</section>

			<!-- Main content -->
			<section class="content"> <!-- Your Page Content Here -->
			<div id="dsDiv"
				style="width: 100%;background: white;border: 3px solid red;">

			</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

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
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->

	<!-- jQuery 2.1.4 -->
	<script
		src="${contextPath}/scripts/home/responsive/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script src="${contextPath}/scripts/jquery-ui-1.11.4.min.js"></script>
	<!-- Bootstrap 3.3.5 -->
	<script
		src="${contextPath}/scripts/home/responsive/bootstrap/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${contextPath}/scripts/home/responsive/dist/js/app.min.js"></script>
	<script src="${contextPath}/scripts/treeJson.js"></script>
	<script src="${contextPath}/scripts/component.js"></script>
	<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
	<!-- handsontable -->
	<script data-jsfiddle="common"
		src="${contextPath}/scripts/handsontable/pikaday/pikaday.js"></script>
	<script data-jsfiddle="common"
		src="${contextPath}/scripts/handsontable/moment/moment.js"></script>
	<script data-jsfiddle="common"
		src="${contextPath}/scripts/handsontable/zeroclipboard/ZeroClipboard.js"></script>
	<script data-jsfiddle="common"
		src="${contextPath}/scripts/handsontable/handsontable.js"></script>
	<script type="text/javascript">
		initMenu();
		function initMenu() {
		    $.ajax({
				url : "${contextPath}/rs/form/component/read",
				type :"post",
				async: false,
				contentType: "application/json",
				dataType : "json",
				success : function(rdata) {
					if (rdata) {
						var jsonData = eval(rdata);
						var jsonDataTree = transData(jsonData, 'id', 'parentId', 'children'); 
						jsonDataTree = {
							    "children":  jsonDataTree
							  };  
						if ("children" in jsonDataTree) {
							createMenu(jsonDataTree.children, $(".sidebar-menu"), 0);
						}
					}

				},
				error : function() {
					console.log("获取控件列表数据失败");
				}

			});
			
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
									adom = $("<a data_role='"+node.dataRole+"' class='elem'></a>");
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
									adom.append("<span>" + node.name
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
		//重新计算iframe高度
		function settingFrameHeight() {
		  var contentTh = $(".content-wrapper").height();
		  var contentHh= $(".content-header").height();
		  //var conentFh=$(".main-footer").height();
		  $("#dsDiv").height(contentTh-contentHh-55);
	    }
	     $(window).resize(function() {
		  settingFrameHeight();
		});
	    $(function() {
	       settingFrameHeight();
	       //给控件列表控件注册拖动事件
	       $(".elem").draggable({
	    	  connectToSortable : ".column",
	    	  cursor: "move",
			  helper:function(event){
				  //获取当前元素类型
				  var elementType=$(this).attr("data_role");
				  var targetElem;
					if($.template[elementType]!=''){
						targetElem=$($.template[elementType]);
					}
					else if($('#'+elementType)&&$('#'+elementType).html()){
						targetElem=$($('#'+elementType).html());
					}
					else{
					    targetElem=$("<img></img>");
					    targetElem.attr("src","${contextPath}/images/component/"+elementType+".png");
					    targetElem.css("vertical-align","middle");
					} 
					var dtStr=new Date().getMilliseconds();
					targetElem.attr("id",elementType+"_"+dtStr);
					targetElem.attr("data-role",elementType);
					targetElem.attr("crtltype","kendo");
				  return targetElem;
			  }
			});
			//目标设计区网格注册
			$("#dsDiv td").droppable({
			    activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",
                drop:function(event,ui){
				var $this=this;
				var handsontable = $("#dsDiv").handsontable("getInstance");
				//获取选中行
				var rowIndex = $(this).parent().prevAll().length;
                var colIndex = $(this).prevAll().length-1;
                //targetElem.prop("outerHTML")
                var currContent=$(this).html();
                var moveObj=$(ui.helper[0]);
                moveObj.attr("style","");
                //获取当前列宽
                var currColWidth=$(this).width()+moveObj[0].scrollWidth;
                this.innerHTML=currContent+moveObj.prop('outerHTML');
                //window.setTimeout(function(){
                //获取之前列宽
               // var preColWidth=$("#dsDiv").handsontable("getInstance").getColWidth(colIndex);
                //if(preColWidth<currColWidth)
                //{
                  handsontable.setCellMeta(rowIndex, colIndex,'width',currColWidth);
                //}
				 handsontable.setDataAtCell(rowIndex,colIndex,$($this).html(),"htmlRenderer");
                //},500);
				}
			});
			});   
	</script>
	<script data-jsfiddle="dsDiv">
           var $container = $("#dsDiv");
                $container.handsontable({
                  startRows: 10,
                  startCols: 5,
                  minRows: 1,
                  minCols: 1,
                  rowHeaders: true,
                  colHeaders: true,
                  minSpareRows: 1,
                  contextMenu: true,
                  colWidths: [150, 150, 150, 150, 150],
                  rowHeights: [40, 40, 40,40,40,40, 40,40,40, 40],
                  manualColumnResize: true,
                  manualRowResize: true,
                  mergeCells:true,
                  currentColClassName:"colCss",
                  className: "htCenter htMiddle"/* ,
                  cells: function (row, col, prop) {
					  var cellProperties = {};
					  cellProperties.renderer=function(hotInstance, TD, row, col, prop, value, cellProperties) {
					      TD.innerHTML = value;
					      this.cellProperties=cellProperties;
					  };
					  return cellProperties;
                   }, */
                });
           var handsontable = $("#dsDiv").handsontable("getInstance");
           //添加钩子 渲染完成后执行的事件
           handsontable.addHook('afterRenderer', function(hotInstance, row, col, prop, value, cellProperties) {                
                  hotInstance.innerHTML = hotInstance.innerText;
                  //获取当前列宽
                  //var currColWidth=handsontable.getColWidth(col);
                  //修改cell属性
                  ////if($(hotInstance).find("img"))
                  //{
                   // var contentWidth=$(hotInstance).find("img").width();
                    //if(contentWidth)
                    //cellProperties.width=$(hotInstance).find("img").width();
                    //handsontable.setCellMeta(row, col,'width',500);
                  //}
                  //return cellProperties;
           });
           handsontable.addHook('modifyColWidth', function(width, col) { 
                
           });
    </script>
    <%@ include file="templates.jsp"%>
</body>
</html>
