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
</head>
<body>
 <script id="container" name="content" type="text/plain">
                      
 </script>    
 <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script> 
<!--百度编辑器-->
<script type="text/javascript">
	var contextPath = "${contextPath}";
	window.PROJECT_CONTEXT = "${contextPath}/mx/";
	var ue;
	$(document).ready(function(){		
	ue = UE.getEditor('container', {
					initialFrameWidth : '99%',
					initialFrameHeight : 500
	});});
	function setCode(content){
			var  div=$("<div class='contentArea'></div>");
			div.append(content);
			/**百度编辑器集成*/
			ue.ready(function () {
					// editor准备好之后才可以使用
					ue.setContent(div.prop("outerHTML"));
			});
		}
	function getCode(){
		var content= ue.getContent();
		return $(content).html();
	}
</script>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.all.desinger.js"></script>	
<script type="text/javascript"  src="${contextPath}/scripts/jquery.base64.js"></script>

<script type="text/javascript"  src="${contextPath}/scripts/designer/editor/htmlEditorExtend/editor_designer.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/metroselect/select2.full.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/touchspin/ext/jquery.touchspin.js"></script>

<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/label/addLabelDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/textboxbt/addTextboxBtDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/datepickerbt/addDatepickerBtDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/datetimepickerbt/addDatetimepickerBtDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/touchspin/addTouchspinDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/textareabt/addTextareaBtDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/metroselect/addMetroselectDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/metroselect_m/addMetroselectMDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/icheckradio/addIcheckradioDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/icheckbox/addIcheckboxDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/mtbutton/addMtbuttonDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/fileupload/addFileUploadDialog.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/col/addColDialog.js"></script>
<!--  
<script type="text/javascript" src="${contextPath}/scripts/designer/editor/htmlEditorExtend/buttongroup/addButtongroupDialog.js"></script>-->


</body>