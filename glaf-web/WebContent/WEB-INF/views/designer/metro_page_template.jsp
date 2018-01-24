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
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/apps/css/inbox.min.css" rel="stylesheet" type="text/css"/>
		<!--ztree-->
		<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
		<!--图片弹窗-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
		<style>
		  .myalert{
				 width:500px;
				 position: absolute;
				 top: 40px;
				 bottom: auto;
				 right: 5px;
				 left: auto;
				 display: block;
				 z-index:9999;
			}
		   .thumbnailbt {
			      padding: 3px 10px;
				  color: #fff;
				  border: 1px #fff solid;
				  margin: -13px 5px 0;
				  background: transparent;
				  position: relative;
				  top: 50%;
				  font-size: 12px;
		   }
		   .thumbnail div{
			    position:relative;
				margin-top:-80px;
				height:80px !important;
		   }
		   .thumbnail h3{
			   text-align:center;font-size:14px;font-weight:400;color:#3e4d5c;
			   margin:3px;
		   }
		   .thumbnail:hover p{
			   display:block;
		   }
		   .thumbnail p{
			   display:none;
		   }
		   .thumbnail img{
			   min-width:150px;
			   min-height:200px;
		   }
		   .thumbnail p>a{
			  color:#fff;
		   }
		   .thumbnail p>a:hover{
			  background:#e84d1c;
			  color:#fff;
		   }
		   .templatecolumn{
			   padding-left:3px;
			   padding-right:3px;
		   }
		   .btn-toolbar button i {
			   line-height:20px !important;
		   }
		   .btn-toolbar .btn-group{
			   float:right;
		   }
		   .myFancyboxImage{
			   max-width:800px;
			   max-height:600px;
		   }
		   .mask{
			   display:none;
		   }
		   /**遮罩层样式*/
		   .thumbnail:hover .mask{
			  display:block;
			  background-color:rgba(0,0,0,0.8);
			  filter:alpha(opacity=40);
			  -moz-opacity:0.4;
			  opacity:0.4;
			  color:yellow;
			  font-weight:bold;
			  text-align:center;
			}
		</style>
</head>
<body>
 <div class="portlet box blue" style="margin:3px;3px;">
             <div class="portlet-title">
                 <div class="caption">
                     <i class="fa fa-gift"></i>页面模板
				 </div>
             </div>
             <div class="portlet-body" style="background:#FAFAFA;">
                 <div class="row"> 
				 <div class="col-md-3">
					<div class="zTreeDemoBackground left">
						<ul id="templateCategory" class="ztree"></ul>
					</div>
				 </div>
				 <div class="col-md-9">
			          <div class="row content"> 
					      <div class="col-md-12 gridcontent" style="height: 100%;overflow-y:auto;">

						  </div>
					  </div>
                 </div>
             </div>
			 </div>
  </div>
  </div>
  <template id="thumbnail_template">
     <div class="thumbnail">
	     <span class="mask"></span>
		 <img class="img-responsive" src="${contextPath}/images/a.jpg" >
		 <div class="caption">
			<p class="btarea" style="text-align:center;">
			   <!--<a href="#" class="btn thumbnailbt viewTemplateBt" role="button">
				  查看
			   </a>-->
			   <a href="#" class="btn thumbnailbt editTemplateBt" role="button">
				  浏览
			   </a>
			   <a href="#" class="btn thumbnailbt applyTemplateBt" role="button">
				  套用
			   </a>
			   <a href="#" class="btn thumbnailbt applyStyleTemplateBt" role="button">
				  套用样式
			   </a>
			</p>
		 </div>
		 <h3>模板1</h3>
	  </div>
  </template>
  <script type="text/javascript">
        var contextPath='${contextPath}';
        var id='${param.id}';
        var pId='${param.pId}';
        var designerJson='${desingerJson}';
		var action='${param.action}'==''?'join':'${param.action}';
        </script>
        <!-- END FOOTER -->
        <!--[if lt IE 9]>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/respond.min.js"></script>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js" type="text/javascript"></script>
        <script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
		<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.js"></script>
		<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.svg.js"></script>
		<script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
		<!--百度编辑器>
		<script>
			window.PROJECT_CONTEXT = "${contextPath}/mx/";
		</script>
		<script type="text/javascript"
			src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
		<script type="text/javascript"
			src="${contextPath}/scripts/ueditor/ueditor.all.desinger.js"></script>
	    <![end]-->
		<script type="text/javascript" src="${contextPath}/scripts/designer/metro_page_template.js"></script>
</body>
</html>