<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
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
<!DOCTYPE html>
<html>
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
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/apps/css/inbox.min.css" rel="stylesheet" type="text/css">
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
		</style>
</head>
<body>
<div style="padding:25px 20px 10px;">
<div class="inbox">
	<div class="row">
		<div class="col-md-2">
			<div class="inbox-sidebar">
				<a class="btn blue compose-btn btn-block receive" href="javascript:;"
					data-title="receive"> <i class="fa fa-download"></i> 收取邮件
				</a>
				<a class="btn red compose-btn btn-block linkapply" href="javascript:;"
					data-title="linkapply"> <i class="fa fa-edit"></i>申请互联
				</a>
				<ul class="inbox-nav">
					<li class="active"><a href="javascript:;" data-title="收件箱"
						data-type="inbox"> 收件箱 <span class="badge badge-success">${inbox}</span>
					</a></li>
					<li><a href="javascript:;" data-title="重要项"
						data-type="important"> 重要项 <span class="badge badge-danger">${important}</span></a></li>
					<li><a href="javascript:;" data-title="待发送邮件"
						data-type="draft"> 待发送邮件 <span class="badge badge-danger">${trash}</span>
					</a></li>
					<li><a href="javascript:;" data-title="已发送邮件" data-type="sent">
							已发送邮件 <span class="badge badge-success">${sent}</span></a></li>
					<li class="divider"></li>
					<li><a class="sbold uppercase" href="javascript:;" data-type="trash"
						data-title="垃圾邮件"> 垃圾邮件 <span class="badge badge-info">${trash}</span>
					</a></li>
					<li><a href="javascript:;" data-title="最近一周" data-type="last">
							最近一周 <span class="badge badge-success">${last}</span></a></li>
				</ul>
				<ul class="inbox-contacts">
					
				</ul>
			</div>
		</div>
		<div class="col-md-10">
			<div class="inbox-body">
				<div class="inbox-header">
					<h1 class="pull-left title" style="display: inline-block;">收件箱</h1><h2 class="pull-left maildatemsg" style="margin: 10px 10px;"></h2>
					<form class="form-inline pull-right">
						<div class="input-group input-medium">
							<input class="form-control" id="searchCondition" type="text" placeholder="发件人/收件人/主题">
							<span class="input-group-btn">
								<button class="btn green search" type="button">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</form>
				</div>
				<div class="inbox-content">
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th colspan="3">
									<input type="checkbox" class="mail-checkbox mail-group-checkbox">
									<div class="btn-group input-actions">
										<a class="btn btn-sm blue btn-outline dropdown-toggle sbold"
											href="javascript:;" data-toggle="dropdown"> 操作<i
											class="fa fa-angle-down"></i>
										</a>
										<ul class="dropdown-menu">
											<li><a href="javascript:;"> <i class="fa fa-pencil"></i>
													数据处理
											</a></li>
											<li><a href="javascript:;"> <i class="fa fa-ban"></i>
													标记为垃圾邮件
											</a></li>
											<li class="divider"></li>
											<li><a href="javascript:;"> <i class="fa fa-trash-o"></i>
													 删除
											</a></li>
										</ul>
									</div>
								</th>
								<th class="pagination-control" colspan="3">
								<div class="date date-picker" style="width:50px;display: inline-block;vertical-align:bottom;" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">
                                    <input type="text" class="form-control maildate" readonly style="display:none;">
                                    <span class="input-group-btn">
                                        <button class="btn default btn-icon-only" type="button">
                                            <i class="fa fa-calendar"></i>
                                        </button>
                                   </span>
                                </div>
								<span
									class="pagination-info">  </span> <a
									class="btn btn-sm blue btn-outline prePage"> <i
										class="fa fa-angle-left"></i>
								</a> <a class="btn btn-sm blue btn-outline nextPage"> <i
										class="fa fa-angle-right"></i>
								</a></th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<div class="hidemy-modal">
     <div id="sendModal" class="modal fade modal-warning" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                  <h4 class="modal-title">互联申请</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" action="#">
                       <div class="form-body">
                       <div class="form-group">
                           <label class="control-label col-md-3">  邮箱地址：</label>
                           <div class="col-md-9">
                             <input id="address" name="address" class="form-control"/>
                           </div> 
                       </div>   
                       </div>      
                   </form>
                </div>
                <div class="modal-footer">
                  <button class="btn btn-outline blue pull-left modalClose" type="button" data-dismiss="modal">取消</button>
                  <button class="btn btn-outline red linkApplybt" type="button">确定</button>
                </div>
              </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
</div>
<script type="text/javascript">
        var contextPath='${contextPath}';
        var id='${param.id}';
        var pId='${param.pId}';
        var designerJson='${desingerJson}';
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
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/maildata/main.js" type="text/javascript"></script>
    </body>
</html>