<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>HTML模板预览</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css"/>
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>

<style type="text/css">
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}

</style>
<script type="text/javascript">
$(document).ready(function(){
     var htmltemplate=window.opener.html;
     var variableJSONString=window.opener.variableJSONString;
     $.ajax({
			url : "${contextPath}/mx/html/editor/getPreviewHTML",
			type : "post",
			async : false,
			dataType : "text",
			data : {
				htmltemplate : htmltemplate,
				variableJSONString : variableJSONString
			},
			success : function(data) {
				if (data) {
					var cw = window.frames['content'];  
					cw.document.open();  
					cw.document.write(data);  
					cw.document.close(); 		
			       //$(window.frames["content"].document).html(data);
				}
				
			},
			error : function() {
				
			}

		});
});
</script>
</head>
<body>		
<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
<div id="head" class="k-header k-footer footerCss">
<table style="width:100%;">
	<tr>
		<td style="width:250px;text-align: left;vertical-align: middle;"><img
			src="${contextPath}/images/book_open.png"
			style="vertical-align: middle;" /> <span
			style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">HTML</span>
			<span style="font-size: 16px;font-weight: bolder;">模板预览</span></td>
	</tr>
</table>
</div>
<div id="conDiv" style="width:100%;">
<iframe id="content" name="content" width="100%" height="100%" frameborder="0" src="javascript:false"></iframe>	
</div>
</div>
<script type="text/javascript">
$("#vertical").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsed : false,
				resizable : false,
				scrollable : false,
				size : "40px"
			}, {
				collapsed : false,
				scrollable : false
			}]
		});
		function openDialog(e)
		{
		    var dialogHeight=$(e.target).attr("dialogHeight");
		    var dialogWidth=$(e.target).attr("dialogWidth");
		    var url=$(e.target).attr("ahref");
		    var title=$(e.target).text;
		    var iTop = (window.screen.availHeight-30-dialogHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-dialogWidth)/2; //获得窗口的水平位置;
		    var option="height="+dialogHeight+",width="+dialogWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
		    window.open(url,title,option);
		}
</script>
</body>
</html>