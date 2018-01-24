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
<title>流程定义查看</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
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

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}
</style>
<script type="text/javascript">

</script>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto; border-left:none;border-right:none;">
		<div id="head" class="k-header k-footer footerCss" style="border: 0px;">
			<table style="width: 100%;">
				<tr>
					<td style="width:70px;text-align: left;vertical-align: middle;">
						<img alt="" src="${contextPath}/images/process.png" style="margin-left: 5px;">
					</td>
					<td style="text-align: left;width:350px;">
						<span
						style="font-family:Lucida Calligraphy;font-size: 18px;font-weight: bolder;">${procName}</span>
						<br>
						<img alt="" src="${contextPath}/images/blue-documents-stack.png">
						<span style="font-size: 14px;">版本：${procVersion}</span>
					</td>
					<td style="text-align: center;">
					    <table style="width:100%;margin-left: 10px;">
						    <tr>
						        <td style="text-align:left;font-family:Lucida Calligraphy;font-size: 15px;font-weight: bolder;color: red;">控件名称：</td>
								<td style="text-align:left;">${compName}</td>
								<td style="text-align:left;font-family:Lucida Calligraphy;font-size: 15px;font-weight: bolder;color: red;">事件类型：</td>
								<td style="text-align:left;">${event}</td>
						    </tr>
						</table>
				   </td>
				</tr>
			</table>
			
		</div>
		<div>
		<c:choose>
		   <c:when test="${procModelId!=''&&(filePath==null||filePath=='')}">
		         此流程为外部导入或第一次编辑未进行保存操作，需在编辑页面保存后生成流程图
		   </c:when>
		   <c:when test="${filePath!=null&&filePath!=''}">
		    <img src="${contextPath}/${filePath}"/>
		   </c:when>
		   <c:otherwise></c:otherwise>
		</c:choose>
		</div>
	</div>
	<script type="text/javascript">
		$("#vertical").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsed : false,
				resizable : false,
				scrollable : false,
				size : "60px"
			}, {
				collapsed : false,
				scrollable : false
			} ]
		});
	</script>
</body>
</html>