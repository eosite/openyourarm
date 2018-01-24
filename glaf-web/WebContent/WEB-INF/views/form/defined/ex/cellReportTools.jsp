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
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.rounded.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link
	href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath}/scripts/spreadjs/html/lib/spread/gcspread.sheets.excel2013white.9.40.20153.0.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath}/scripts/spreadjs/spreadjs.view.css"
	rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinSimple.css" rel="stylesheet" type="text/css" />
<!-- END THEME LAYOUT STYLES -->
<style type="text/css">
	.selecticon:before{
		content: "☑";
		color: red;
		font-size:18px;
		font-weight:bold;
		position: absolute;
    	top: 1px;
    	left: 1px;
	}

	.btndiv{
		position: absolute;
		right: 10px;
		top:5px;
		z-index: 9999;
	}
</style>

</head>
<body>
	<div class="btndiv">
		<button id="btn" class="btn btn-info">选择</button>
		<button id="btn3" class="btn btn-info">清空</button>
		<button id="btn2" class="btn btn-info">关闭</button>
	</div>
    <div>
	<div class="content">
		<div class="row" style="margin: 0px;">
			<div class="col-md-12 col-xs-12 header">
				<ul class="nav nav-tabs" style="background-color: #d0ddee;border-bottom:1px solid #b7c3d2;">
                       <li  class="active"  style="margin-left:30px;margin-top:5px;margin-bottom:-3px;height:25px;">
                          <a href="#tab_1_2" data-toggle="tab" class="titleControl" style="border:1px solid #b7c3d2;border-bottom-color:transparent;font-size:12px;height:25px;padding-top:5px;padding-left:15px;padding-right:15px;background-color:#f5faff">字体 </a>
                       </li>
                       <li style="margin-top:5px;margin-bottom:-3px;height:25px;">
                         <a href="#tab_1_3" data-toggle="tab" class="titleControl" style="border:1px solid #b7c3d2;border-bottom-color:transparent;font-size:12px;height:25px;padding-top:5px;padding-left:15px;padding-right:15px;background-color:#f5faff"> 排版</a>
                       </li>
                        <!-- <li style="margin-top:5px;margin-bottom:-3px;height:25px;">
                        	 <a href="#tab_1_4" data-toggle="tab" class="titleControl" style="border:1px solid #b7c3d2;border-bottom-color:transparent;font-size:12px;height:25px;padding-top:5px;padding-left:15px;padding-right:15px;background-color:#f5faff"> 插入</a>
                       </li> -->
                        <li style="margin-top:5px;margin-bottom:-3px;height:25px;">
                           <a href="#tab_1_1" data-toggle="tab" class="titleControl" style="border:1px solid #b7c3d2;border-bottom-color:transparent;font-size:12px;height:25px;padding-top:5px;padding-left:15px;padding-right:15px;background-color:#f5faff">系统 </a>
                       </li>
                </ul>
                <div class="tab-content">
	                <div class="tab-pane fade active in  ziti" id="tab_1_2">
						<div class="form-inline well well-sm toolbar">
							<div class="btn-group">
								<div class="myicon" toolgroup="1" tooltype="font-icon" toolName="字体">
							        <select class="form-control select2 " data-placeholder="请选择..." data-allow-clear="true" tabindex="-1" aria-hidden="true">
							            <option value="宋体">宋体</option>
							         </select>
						         </div>
							</div>
							<div class="btn-group">
								<div class="myicon" toolgroup="1" tooltype="fontsize-icon" toolName="字体大小">
						        <select class="form-control select2 " data-placeholder="请选择..." data-allow-clear="true" tabindex="-1" aria-hidden="true">
						        	<option value="8">8</option>
						        	<option value="9">9</option>
						            <option value="10">10</option>
						            <option value="11" selected="">11</option>
						            <option value="12">12</option>
						            <option value="14">14</option>
						            <option value="16">16</option>
						            <option value="18">18</option>
						            <option value="20">20</option>
						            <option value="24">24</option>
						            <option value="26">26</option>
						            <option value="28">28</option>
						            <option value="36">36</option>
						            <option value="48">48</option>
						            <option value="72">72</option>
						         </select>
						         </div>
							</div>
							<div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="1" tooltype="textbold-icon" toolName="粗体">
			                        <div  class="ui-icon textbold-icon"></div>
			                        <div> 粗体</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="1" tooltype="textitalic-icon" toolName="斜体">
			                        <div  class="ui-icon textitalic-icon"></div>
			                        <div> 斜体</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="1" tooltype="textunderline-icon" toolName="下划线">
			                        <div  class="ui-icon textunderline-icon"></div>
			                        <div> 下划线</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="1" tooltype="fillcolor-icon" toolName="填充颜色">
			                        <div  class="ui-icon fillcolor-icon"></div>
			                        <div> 填充颜色</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="1" tooltype="textcolor-icon" toolName="字体颜色">
			                        <div  class="ui-icon textcolor-icon"></div>
			                        <div> 字体颜色</div>
			                    </button>
		                    </div>
		                   <!--  <div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="1" tooltype="superscript-icon">
			                        <div  class="ui-icon superscript-icon"></div>
			                        <div> 上标</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="1" tooltype="subscript-icon">
			                        <div  class="ui-icon subscript-icon"></div>
			                        <div> 下标</div>
			                    </button>
		                    </div> -->
						</div>
					</div>
					<div class="tab-pane fade  in paiban" id="tab_1_3">
						<div class="form-inline well well-sm toolbar">
							<div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="3" tooltype="justify-icon" toolName="折行显示">
			                        <div  class="ui-icon justify-icon"></div>
			                        <div> 折行显示</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
			                    <button class="icon-btn  myicon" toolgroup="3" tooltype="horizontalleft-icon" toolName="居左">
			                        <div  class="ui-icon horizontalleft-icon"></div>
			                        <div> 居左</div>
			                    </button>
			                </div>
		                    <div class="btn-group">
			                    <button class="icon-btn  myicon" toolgroup="3" tooltype="horizontalcenter-icon" toolName="水平居中">
			                        <div  class="ui-icon horizontalcenter-icon"></div>
			                        <div> 水平居中</div> 
			                    </button>
			                </div>
		                    <div class="btn-group">
			                    <button class="icon-btn  myicon" toolgroup="3" tooltype="horizontalright-icon" toolName="居右">
			                        <div  class="ui-icon horizontalright-icon"></div>
			                        <div> 居右</div>
			                        
			                    </button>
			                </div>
		                    <div class="btn-group">
			                    <button id="lastPage"  class="icon-btn  myicon" toolgroup="3" tooltype="verticaltop-icon" toolName="居上">
			                        <div  class="ui-icon verticaltop-icon"></div>
			                        <div> 居上</div>
			                        
			                    </button>
		                    </div>
		                    <div class="btn-group">
		                     <button  class="icon-btn  myicon" toolgroup="3" tooltype="verticalcenter-icon" toolName="垂直居中">
		                        <div  class="ui-icon verticalcenter-icon"></div>
		                        <div> 垂直居中</div>
		                    	</button>
		                    </div>
		                    <div class="btn-group">
		                     <button class="icon-btn  myicon" toolgroup="3" tooltype="verticalbuttom-icon" toolName="居下">
		                        <div class="ui-icon verticalbuttom-icon"></div>
		                        <div> 居下</div>
		                    	</button>
		                    </div>
						</div>
					</div>
					<!-- <div class="tab-pane fade  in" id="tab_1_4">
						<div class="form-inline well well-sm toolbar">
							<div class="btn-group">
							<button class="icon-btn  myicon " tooltype="save-icon">
		                        <div  class="ui-icon save-icon"></div>
		                        <div> 保存</div>
		                    </button>
		                    </div>
						</div>
					</div> -->
	                <div class="tab-pane fade  in xitong" id="tab_1_1">
						<div class="form-inline well well-sm toolbar">
							<div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="5" tooltype="editundo-icon" toolName="撤销">
			                        <div  class="ui-icon editundo-icon"></div>
			                        <div> 撤销</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="5" tooltype="editredo-icon" toolName="恢复">
			                        <div  class="ui-icon editredo-icon"></div>
			                        <div> 恢复</div>
			                    </button>
		                    </div>
							<div class="btn-group">
								<button class="icon-btn  myicon " toolgroup="5" tooltype="save-icon" toolName="保存">
			                        <div  class="ui-icon save-icon"></div>
			                        <div> 保存</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
			                    <button class="icon-btn  myicon" toolgroup="5" tooltype="first-icon" toolName="第一页">
			                        <div  class="ui-icon first-icon"></div>
			                        <div> 第一页</div>
			                    </button>
		                    </div>
		                    <div class="btn-group">
			                    <button class="icon-btn  myicon" toolgroup="5" tooltype="prev-icon" toolName="上一页">
			                        <div  class="ui-icon prev-icon"></div>
			                        <div> 上一页</div>
			                        
			                    </button>
		                    </div>
		                    <div class="btn-group">
			                    <button class="icon-btn  myicon" toolgroup="5" tooltype="next-icon" toolName="下一页">
			                        <div  class="ui-icon next-icon"></div>
			                        <div> 下一页</div>
			                        
			                    </button>
		                    </div>
		                    <div class="btn-group">
			                    <button id="lastPage"  class="icon-btn  myicon" toolgroup="5" tooltype="last-icon" toolName="最后一页">
			                        <div  class="ui-icon last-icon"></div>
			                        <div> 最后一页</div>
			                        
			                    </button>
		                    </div>
		                    <div class="btn-group">
		                     <button  id="print"  class="icon-btn  myicon" toolgroup="5" tooltype="print-icon" toolName="打印">
		                        <div  class="ui-icon print-icon"></div>
		                        <div> 打印</div>
		                        
		                    </button>
		                    </div>
						</div>
					</div>
				</div>
                <!-- 
				    <div class="title">工具栏</div>
				-->
			</div>
		</div>
		<!-- <div class="row" style="margin: 0px;">
			<div class="col-md-12 col-xs-12" style="padding: 0px;">
				<div id="loading" class="spread center">
					<div id="ss"  class="spreadDiv"></div>
				</div>
			</div>
		</div> -->
        <!-- <div class="row foot" style="display: none;">
			<div class="col-md-12 col-xs-12" style="padding: 0px;">
				<div id="pagemsg" class="pagemsg center"></div>
				<div class="btn-group dropup zoomBt">
                     <button type="button" class="btn btn-default zoomval">100%</button>
                     <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                         <i class="fa fa-angle-up"></i>
                     </button>
                     <ul class="dropdown-menu" role="menu">
                         <li>
                             <a href="javascript:;" val="400" class="zoomlink">400%</a>
                         </li>
                         <li>
                             <a href="javascript:;" val="300" class="zoomlink">300%</a>
                         </li>
                         <li>
                             <a href="javascript:;" val="200" class="zoomlink">200%</a>
                         </li>
                         <li>
                             <a href="javascript:;" val="150" class="zoomlink">150%</a>
                         </li>
                         <li>
                             <a href="javascript:;" val="100" class="zoomlink">100%</a>
                         </li>
                         <li>
                             <a href="javascript:;" val="75" class="zoomlink">75%</a>
                         </li>
                         <li>
                             <a href="javascript:;" val="50" class="zoomlink">50%</a>
                         </li>
                         <li class="divider"> </li>
                         <li class="actzoom">
                             <a href="javascript:;"  class="zoomlink"><i class="fa fa-file-o"></i> 实际大小</a>
                         </li>
                     </ul>
                 </div>
				<div class="zoom">
				   <input type="text" class="rangeSlider" value="100"/>
				</div>
			</div>
		</div>  -->
	</div>
	<div class="hidemy-modal">
	      <div id="printModal" class="modal fade modal-warning" role="dialog" aria-hidden="true">
              <div class="modal-dialog" style="width:900px;">
                <div class="modal-content">
                  <div class="modal-header">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title">打印设置</h4>
                  </div>
                  <div class="modal-body">
            <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>开始行:</label>
			</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" class="form-control"  id="rowStart">
            </div>
            <div class="col-md-2 col-xs-2">
                <label>结束行:</label>
			</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" class="form-control"  id="rowEnd">
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>开始列:</label>
			</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" class="form-control"  id="columnStart">
            </div>
            <div class="col-md-2 col-xs-2">
                <label>结束列:</label>
			</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" class="form-control"  id="columnEnd">
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>重复行开始:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" class="form-control"  id="repeatRowStart">
            </div>
            <div class="col-md-2 col-xs-2">
                <label>重复行结束:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text"  class="form-control" id="repeatRowEnd">
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>重复列开始:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text"  class="form-control" id="repeatColumnStart">
            </div>
            <div class="col-md-2 col-xs-2">
                <label>重复列结束:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" class="form-control"  id="repeatColumnEnd">
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>
                    <input type="checkbox"  id="showBorder">
                    显示边框
                </label>
            </div>
            <div class="col-md-2 col-xs-2">
                <label>
                    <input type="checkbox"  id="showGridLine">
                    显示网格线
                </label>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>显示行头:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="showRowHeader" class="form-control" >
                    <option value="0">继承</option>
                    <option value="1">隐藏</option>
                    <option value="2">显示</option>
                    <option value="3">显示一次</option>
                </select>
            </div>
            <div class="col-md-2 col-xs-2">
                <label>显示列头:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="showColumnHeader" class="form-control" >
                    <option value="0">继承</option>
                    <option value="1">隐藏</option>
                    <option value="2">显示</option>
                    <option value="3">显示一次</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>页眉左:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" id="headerLeft" class="form-control">
            </div>
            <div class="col-md-2 col-xs-2">
                <label>页眉左图片:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="headerLeftImage" class="form-control" >
				    <option value=""></option>
                    <option value="${contextPath}/images/chiss_logo_blue.png">LOGO</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>页眉中:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" id="headerCenter" class="form-control">
            </div>
            <div class="col-md-2 col-xs-2">
                <label>页眉中图片:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="headerCenterImage" class="form-control" >
				    <option value=""></option>
                    <option value="${contextPath}/images/chiss_logo_blue.png">LOGO</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>页眉右:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" id="headerRight" class="form-control" >
            </div>
            <div class="col-md-2 col-xs-2">
                <label>页眉右图片:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="headerRightImage" class="form-control" >
				    <option value=""></option>
                    <option value="${contextPath}/images/chiss_logo_blue.png">LOGO</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>页脚左:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" id="footerLeft" class="form-control" >
            </div>
            <div class="col-md-2 col-xs-2">
                <label>页脚左图片:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="footerLeftImage" class="form-control" >
				   <option value=""></option>
                   <option value="${contextPath}/images/chiss_logo_blue.png">LOGO</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>页脚中:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" id="footerCenter"  class="form-control" >
            </div>
            <div class="col-md-2 col-xs-2">
                <label>页脚中图片:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="footerCenterImage"  class="form-control" >
				    <option value=""></option>
                    <option value="${contextPath}/images/chiss_logo_blue.png">LOGO</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
                <label>页脚右:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <input type="text" id="footerRight" class="form-control" >
            </div>
            <div class="col-md-2 col-xs-2">
                <label>页脚右图片:</label>
				</div>
			<div class="col-md-4 col-xs-4">
                <select id="footerRightImage"  class="form-control" >
                    <option value=""></option>
                    <option value="${contextPath}/images/chiss_logo_blue.png">LOGO</option>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-xs-2">
				<button id="btnSetPrintInfo" class="btn btn-info" type="button"><i class="fa fa-save"></i> 保存打印设置</button>
            </div>
        </div>
                  </div>
                  <div class="modal-footer">
                    <button class="btn btn-info  pull-left modalClose" type="button" data-dismiss="modal"><i class="fa fa-close"></i> 取消</button>
                    <button class="btn btn-info  modalPrint" type="button"><i class="fa fa-print"></i> 打印</button>
                  </div>
                </div>
              </div>
          </div>
    </div>
	<script type="text/javascript">
        var contextPath='${contextPath}';
        var id='${param.id}';
        var pId='${param.pId}';
        var serverUrl='${serverUrl}';
        var templateId='${param.templateId}';
		var reportId='${param.reportId}',
			key = '${param.key}',
			content = '${param.content}',
            database = '${param.database}';
    </script>
	<script
		src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js"
		type="text/javascript"></script>
	<script
		src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="${contextPath}/scripts/spreadjs/html/lib/spread/gcspread.sheets.all.9.40.20153.0.min.js"
		type="text/javascript"></script>
	<script
		src="${contextPath}/scripts/spreadjs/html/lib/spread/gcspread.sheets.print.9.40.20153.0.min.js"
		type="text/javascript"></script>
	<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/js/ion.rangeSlider.min.js" type="text/javascript"></script>
	<script type="text/javascript">
			function closeWin(){
				parent.layer.close(parent.layer.getFrameIndex());
			}

			var objelementId = '${param.objelementId}',
				nameElementId = '${param.nameElementId}',
				$objEle = parent.$("#"+objelementId),
				$nameEle = parent.$("#"+nameElementId);
				
			var selval = $objEle.val();
			if(selval){
				var selRet = JSON.parse(selval);
				var selTools = selRet[0]["tools"];
				if(selTools.length){
					$.each(selTools,function(i,selTool){
						if(selTool["tools"].length){
							$.each(selTool["tools"],function(i,tool){
								$("[tooltype="+tool['icon']+"]").addClass('selecticon');
							});
						}
					})
				}
			}else{
				$(".myicon").addClass('selecticon');
			}

			$(".myicon").click(function(event) {
				$(this).toggleClass("selecticon");
			});

			$("#btn").click(function(){
				var selObj = getSelect(),
					retName = "工具栏样式定义";
				var ret = [{"name":retName,"tools":selObj}];
				$objEle.val(JSON.stringify(ret));
				$nameEle.val(retName);
				closeWin();
			})

			$("#btn3").click(function(){
				$objEle.val("");
				$nameEle.val("");
				closeWin();
			})

			$("#btn2").click(function(){
				closeWin();
			})
			
			function getSelect(){
				var zitis = $(".ziti .myicon.selecticon"),
					paibans = $(".paiban .myicon.selecticon"),
					xitongs = $(".xitong .myicon.selecticon"),
					obj = [];
				if(zitis.length){
					var o = {"toolBarName":"字体","tools":[]};
					obj.push(o);
					$.each(zitis,function(i,el){
						o["tools"].push({
							"icon" : $(el).attr("tooltype") ,
							"toolName" : $(el).attr("toolName")
						});
					});
				}
				if(paibans.length){
					var o = {"toolBarName":"排版","tools":[]};
					obj.push(o);
					$.each(paibans,function(i,el){
						o["tools"].push({
							"icon" : $(el).attr("tooltype") ,
							"toolName" : $(el).attr("toolName")
						});
					});
				}
				if(xitongs.length){
					var o = {"toolBarName":"系统","tools":[]};
					obj.push(o);
					$.each(xitongs,function(i,el){
						o["tools"].push({
							"icon" : $(el).attr("tooltype") ,
							"toolName" : $(el).attr("toolName")
						});
					});
				}
				return obj;
			}
			
	</script>
</body>
</html>
