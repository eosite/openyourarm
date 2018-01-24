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
        <link href="${contextPath}/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        
        
		<!--ztree-->
		<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
		<!--图片弹窗-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
		<!--图片附件上传-->
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
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
			.joinBtn{
			  font-size: 12px;
			}
			.editBtn{
			  font-size: 12px;
			}
			.exitBtn{
			  font-size: 12px;
			}
		</style>
</head>
<body>
 <div class="portlet box blue" style="margin:3px;3px;">
             <div class="portlet-title">
                 <div class="caption">
                     <i class="fa fa-gift"></i>模板管理
				 </div>
                 <ul class="nav nav-tabs">
                     <li class="active">
                         <a href="#portlet_tab1" data-toggle="tab"> 内容编辑区 </a>
                     </li>
					 <li>
                         <a href="#portlet_tab2" data-toggle="tab"> 自定义模板 </a>
                     </li>
                     <li>
                         <a href="#portlet_tab3" data-toggle="tab"> 系统模板 </a>
                     </li>
                 </ul>
             </div>
             <div class="portlet-body" style="background:#FAFAFA;">
                 <div class="row"> 
				 <div class="col-md-3">
				    <button type="button" class="btn yellow-gold joinBtn">添加到分类</button>
					<button type="button" class="btn yellow-gold editBtn">保存修改</button>
					<button type="button" class="btn yellow-gold exitBtn">导出</button>
					<div class="zTreeDemoBackground left">
						<ul id="templateCategory" class="ztree"></ul>
					</div>
				 </div>
				 <div class="col-md-9">
				 <div class="tab-content">
				     <div class="tab-pane active" id="portlet_tab1">
						<div class="row"> 
							<div class="col-md-3" style="padding-bottom:10px;">
							  <span style="float:left;margin-top:5px;">模板名称：</span>
							</div> 
							<div class="col-md-9 text-left" style="padding-bottom:10px;">
							  <input id="tmpName" name="tmpName" class="form-control" style="width:300px;"/>
							</div>
						</div>
						<div class="row"> 
							<div class="col-md-3" style="padding-bottom:10px;">
							  <span style="float:left;margin-top:5px;">缩略图：</span>
							</div> 
							<div class="col-md-9 text-left" style="padding-bottom:10px;">
							  <div class="fileinput fileinput-new" data-provides="fileinput">
									<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;"> </div>
									<div>
										<span class="btn red btn-outline btn-file">
											<span class="fileinput-new"> 选择图片 </span>
											<span class="fileinput-exists"> 重新选择 </span>
											<input type="file" name="thumbnail" id="thumbnail"></span>
										<a href="javascript:;" class="btn red fileinput-exists" data-dismiss="fileinput"> 删除 </a>
									</div>
                               </div>
							</div>
						</div>
						<div class="row"> 
							<div class="col-md-12"> 
							    <div name="summernote" id="summernote"> </div>
								<!-- 加载百度编辑器的容器 -->
								<!--
				                <script id="container" name="content" type="text/plain">
                      
                                </script>-->
							</div>
						</div>
                     </div>
                     <div class="tab-pane" id="portlet_tab2">
                         <div class="row"> 
							<div class="col-md-12 gridcontent">
<!--							    
								<div class="row">
								    <div class="col-sm-6 column col-md-3">
									  <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
									 
								    </div>
									<div class="col-sm-6 column col-md-3">
									 <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
								    </div>
									 <div class="col-sm-6 column col-md-3">
								 <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
								    </div>
									 <div class="col-sm-6  column col-md-3">
										 <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
								    </div>
								 </div>
								 
								 
								 <div class="row">
								    <div class="col-sm-6 column col-md-3">
									  <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
									 
								    </div>
									<div class="col-sm-6 column col-md-3">
									 <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
								    </div>
									 <div class="col-sm-6 column col-md-3">
								 <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
								    </div>
									 <div class="col-sm-6  column col-md-3">
										 <div class="thumbnail">
										 <img class="img-responsive" src="${contextPath}/images/a.jpg"  
										 alt="通用的占位符缩略图">
										 <div class="caption">
										    <p class="btarea" style="text-align:center;">
											   <a href="#" class="btn thumbnailbt" role="button">
												  查看
											   </a> 
											   <a href="#" class="btn thumbnailbt" role="button">
												  选择
											   </a>
											</p>
										 </div>
										 <h3>模板1</h3>
									  </div>
								    </div>
								 </div>-->
								<!--
								 <div class="row">
								    <div class="col-sm-12 col-md-12 text-right">
											  <div class="btn-toolbar margin-bottom-10">
													<div class="btn-group">
														<button type="button" class="btn btn-default"><i class="fa fa-angle-left"></i></button>
														<button type="button" class="btn btn-default">1</button>
														<button type="button" class="btn btn-default">2</button>
														<button type="button" class="btn btn-default">3</button>
														<button type="button" class="btn btn-default">4</button>
														<button type="button" class="btn btn-default"><i class="fa fa-angle-right"></i></button>
											  </div>
                                       </div>
									</div>
								 </div>-->
							  </div>
							 </div>
						</div>
                        <div class="tab-pane" id="portlet_tab3">
                        <div class="row"> 
							<div class="col-md-12"> 
							   
							</div>
						</div>
                       </div>
                 </div>
             </div>
			 </div>
  </div>
  </div>
  <template id="thumbnail_template">
  
        <div class="thumbnail">
		<!-- <div class="checkbox checkbox-primary">
			<input id="checkbox2" class="styled" type="checkbox" checked>
				
		</div> -->
		<span class="mask" ></span>
	     
		 <img class="img-responsive" src="${contextPath}/images/a.jpg" >
		 <div class="caption">
			<p class="btarea" style="text-align:center;">
			   <!--<a href="#" class="btn thumbnailbt viewTemplateBt" role="button">
				  查看
			   </a>-->
			   <a href="#" class="btn thumbnailbt editTemplateBt" role="button">
				  编辑
			   </a>
			   <a href="#" class="btn thumbnailbt applyTemplateBt" role="button">
				  套用
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
		<!--图片上传控件-->
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
		<!--百度编辑器>
		<script>
			window.PROJECT_CONTEXT = "${contextPath}/mx/";
		</script>
		<script type="text/javascript"
			src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
		<script type="text/javascript"
			src="${contextPath}/scripts/ueditor/ueditor.all.desinger.js"></script>
	    <![end]-->
		<script type="text/javascript" src="${contextPath}/scripts/designer/metro_designer_template.js"></script>
</body>
</html>