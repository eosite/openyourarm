<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>Cell 设计器</title>
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN THEME GLOBAL STYLES -->
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css"
	rel="stylesheet" id="style_components" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css"
	rel="stylesheet" type="text/css" />
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/layout.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/themes/darkblue.min.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/custom.min.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
body{
	background-color: #67B4C0;
}
.page-header.navbar{
	background-color: #2ab4c0;
}
.page-quick-sidebar-wrapper{
	background-color: #2ab4c0;
}
.mt-dialog .modal-dialog {
	width: 1150px;
}
a{
	color: black;
}
.contentCol{
	background-color: white;
}
div.list-toggle{
	padding:4px 0px 4px 0px !important;
	height: 35px;
}
li.mt-list-item{
	padding:4px !important;
}
.page-quick-sidebar-wrapper{
	color: black !important;
}
.mt-element-list .list-simple.group .list-toggle-container .list-toggle{
	color: black !important;
	background-color: #4B77BE !important;
}
.mt-element-list .list-simple.group .list-toggle-container .list-toggle.done1{
	background-color: #26C281 !important;
}
.mt-element-list .list-simple.group .list-toggle-container .list-toggle.done2{
	background-color: #f3c200 !important;
}
.mt-element-list .list-simple.ext-1.mt-list-container ul>.mt-list-item{
	border-color : #4B77BE #4B77BE #e5e5e5 !important;
}
.mt-element-list .list-simple.ext-1.mt-list-container ul>.mt-list-item:hover{
	background-color : #A5BADF !important;
}
.mt-element-list .list-simple.ext-1.mt-list-container ul>.mt-list-item.done1{
	border-color : #26C281 #26C281 #e5e5e5 !important;
}
.mt-element-list .list-simple.ext-1.mt-list-container ul>.mt-list-item.done1:hover{
	background-color : #96ebc8 !important;
}
.mt-element-list .list-simple.ext-1.mt-list-container ul>.mt-list-item.done2{
	border-color : #f3c200 #f3c200 #e5e5e5 !important;
}
.mt-element-list .list-simple.ext-1.mt-list-container ul>.mt-list-item.done2:hover{
	background-color : #FFEE99 !important;
}

.input-zpadding{
	padding: 0px;
	padding-left: 10px;
}

.page-quick-sidebar-wrapper div[categoryid]>div{
	padding: 0px;
}
</style>
<script type="text/javascript"
	src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath }/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript">
	function Queue(win) {
		this.window = (win || window);
		this.funcs = [];
		this.push = function(fn) {
			this.funcs.push(fn);
		};
		this.fire = function(timeout) {
			var i = 0, len = this.funcs.length, fn;
			for (; i < len; i++) {
				if ((fn = this.funcs[i]) && $.isFunction(fn)) {
					fn.call(this.window);
				}
			}
		};
	}

	$.extend(true, window, {
		contextPath : "${contextPath}",
		resizeFuncs : new Queue(),
		readyFuncs : new Queue(),
		templateId : "${id}",
		systemType : "${systemType}"
	});

	$(document).ready(function() {
		readyFuncs.fire();
	});

	readyFuncs.push(function() {
		$(window).resize(function() {
			resizeFuncs.fire();
		});
		
		setTimeout(function(){
			
			$(".menu-toggler").click();
		}, 100);
	});
</script>
<style type="text/css" media="screen">
.page-quick-sidebar-wrapper {
	width: 360px;
	right: -360px; //
	background-color: #FFF; //
	color: #000;
}

.form-horizontal .form-group {
	margin-right: 0px;
}

.checkbox-list .form-control {
	width: auto;
	height: 20px;
}
</style>
</head>
<body
	class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
	<!-- BEGIN HEADER -->
	<div class="page-header navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner ">
			<!-- BEGIN LOGO -->
			<div class="page-logo">
				<div class="menu-toggler sidebar-toggler"></div>
			</div>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler"
				data-toggle="collapse" data-target=".navbar-collapse"> </a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			<div class="btn-toolbar" role="toolbar" style="padding-top:6px;">
				<div class="btn-group">
					<button type="button" id="saveBt" class="btn btn-sm btn-default">
						<i class="fa fa-save"></i> 保存
					</button>
					<button type="button" id="emptyBt" class="btn btn-sm btn-danger">
						<i class="fa fa-trash-o"></i> 清空
					</button>
				</div>
				<div class="btn-group">
					<button type="button" id="previewBt" class="btn btn-sm btn-default">
						<i class="fa fa-eye"></i> 预览
					</button>
				</div>
			</div>
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<!-- BEGIN NOTIFICATION DROPDOWN -->

					<!-- END USER LOGIN DROPDOWN -->
					<!-- BEGIN QUICK SIDEBAR TOGGLER -->
					<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
					<li class="dropdown dropdown-quick-sidebar-toggler"><a
						href="javascript:;" class="dropdown-toggle"> <i
							class="icon-logout"></i>
					</a></li>
					<!-- END QUICK SIDEBAR TOGGLER -->
				</ul>
			</div>
		</div>

		<!-- END HEADER INNER -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">

		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper ">
			<!-- BEGIN CONTENT BODY -->
			<div class="page-content">
				<div class="row">
					<div class="col-lg-10">
						<iframe id="frame-01" style="width:100%; height:500px;"></iframe>
					</div>
				</div>

			</div>
			<!-- END CONTENT BODY -->
		</div>
		<!-- END CONTENT -->
		<a href="javascript:;" class="page-quick-sidebar-toggler"> <i
			class="icon-login"></i>
		</a>
		<div class="page-quick-sidebar-wrapper"
			data-close-on-body-click="false">
			<div id="componentAttr" data-role="tabs" style="width:100%"></div>
		</div>
	</div>
	<!-- END CONTAINER -->
</body>

<script type="text/javascript"
	src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${contextPath }/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.js"></script>
<script type="text/javascript"
	src="${contextPath }/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
<script
	src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
	type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script
	src="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js"
	type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->
<!-- BEGIN PAGE LEVEL SCRIPTS 
<script src="${contextPath }/scripts/metronic/4.5.2/theme/assets/pages/scripts/dashboard.min.js" type="text/javascript"></script>
-->
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME LAYOUT SCRIPTS -->
<script
	src="${contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/scripts/layout.min.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/global/scripts/quick-sidebar.min.js"
	type="text/javascript"></script>
<!-- END THEME LAYOUT SCRIPTS -->

<script type="text/javascript">
	var auto = "${auto}";

	function frameResize() {
		return $("#frame-01").css({
			width : '100%',
			border : 0,
			height : $(window).height() * 0.9
		});
	}
	frameResize().attr({
		src : "${contextPath }/scripts/spreadjs/html/index/index.html?_="+ (new Date().getTime()),
		onload : "frameOnload(this)"
	});

	resizeFuncs.push(frameResize);

	function getGcSpread() {
		return $window.GcSpread;
	}

	function frameOnload(frame) {
		window.$window = frame.contentWindow;
		window.$$ = $window.$;
		$$($window.document).ready(function() {
			/* var $s = $$("#ss"), isOk = $timeout(function() {
				return !$s.get(0) || !$s.data('spread');
			}, 10000);
			if (isOk) {
				$s.data('SpreadJs', new SpreadJs($window.GcSpread, $s));
			} */
			//callBackFunc.call($window, $$);
			
			window.intervalProcess(function(){
				return !!$$("#ss").get(0);
			}, function(){
				var $s = $$("#ss");
				$s.data('SpreadJs', new SpreadJs($window.GcSpread, $s));
			});
			
		});
	}
	
	/* var callBackFunc = (function($){
		var init = false;
		return function($$){
			if(init){
				return;
			}
			init = true;
			var $window = this;
			var $s = $$("#ss"), isOk = $timeout(function() {
				return !$s.get(0) || !$s.data('spread');
			}, 10000);
			if (isOk) {
				$s.data('SpreadJs', new SpreadJs($window.GcSpread, $s));
			}
		};
	})(jQuery); */
	
	/**
	 * 定时器
	 */
	var intervalProcess = (function(){
		var $interval = null, time = 0;
		return function(filter, fn){
			if(!$interval){
				$interval = window.setInterval(function(){
					if(filter && filter()){
						fn && fn();
						window.clearTimeout($interval);
						console.log(++ time);
					}
				}, 500);
			}
		};
	})();

	/* function $timeout(fn, time) {
		var ret = true;
		if (fn) {
			var start = new Date().getTime(), t = 0;
			while (fn()) {
				if (((t = new Date().getTime()) - start) >= time) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	} */

	function SpreadJs(GcSpread, jq) {
		this.GcSpread = GcSpread;
		this.jq = jq;
		this.spread = this.jq.data('spread');
		(function(that) {
			that.init();
		})(this);
	}

	function changeCellType(o) {
		changeType(o);
		showHide(o.value);
	}

	function showHide(type) {
		//$(".form-group:not(div[hidden='hidden'])").hide();
		$("li.mt-list-item:not(div[hidden='hidden'])").hide();
		//$(".common-group:not(div[hidden='hidden'])").show();
		$(".common-group").closest("li.mt-list-item:not(div[hidden='hidden'])").show();
		//$(".common-rule:not(div[hidden='hidden'])").show();
		$(".common-rule").closest("li.mt-list-item:not(div[hidden='hidden'])").show();
		//$("." + type + "-cls:not(div[hidden='hidden'])").show();
		$("." + type + "-cls").closest("li.mt-list-item:not(div[hidden='hidden'])").show();
	}

	function setCellValue(type, fn) {
		showHide(type);
		if (fn) {
			fn();
		}
	}

	function testUpdate() {

		var url = contextPath
				+ "/mx/form/defined/ex/tableMgr?fn=viewSourceSetFunc&targetId=id_1460530906392&isBind=true&type=update";

		window.$openWin = window.open(url);
	}
</script>
<script src="${contextPath }/webfile/js/defined.kendo.param.js"
	type="text/javascript"></script>
<script src="${contextPath }/webfile/js/spreadjs.extends.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/tabs/js/jquery.tabs.extends.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/tabs/js/bootstrap-tabdrop.js"
	type="text/javascript"></script>
</html>
