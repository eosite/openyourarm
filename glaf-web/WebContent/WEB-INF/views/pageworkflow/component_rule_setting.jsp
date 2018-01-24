<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程构件更新规则设置</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<%@ include file="/WEB-INF/views/form/defined/table/select_js.jsp"%>
<style type="text/css"> 
.tdCls{
	align:left;
	height:16px;
	border-top:1px solid #ddd;
}
.selectCls{
	width : 90%;
}
.textCls{
	width : 65%;
}
</style>
<script type="text/javascript">

/**
 * 全局参数
 */
var mtxx = {
	contextPath : '${contextPath}',
	url : 	"${contextPath}/mx/form/defined",
	selectedNode : null
};
var pageId='${param.pageId}';
</script>
</head>
<body >
	<div id="container" style="width:100%;height:100%; margin: 0 auto;">
		<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; " >
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;"></span>
							<span style="font-size: 16px;font-weight: bolder;"></span>
						</td>
						<td style="text-align:right;">
							<button id="button-1" class='k-button' type="button">保  存  配  置</button>
							<!-- <button id="button-2" class='k-button' type="button">预  览</button> -->
						</td>
					</tr>
				</table>
			</div>
			<div id="center-pane" style="border:0px;">
				<div>
					<div id="tabstrip-1" class='tabstrip' style="border:0px;"></div>
				</div>
				<div>
					<div id="tabstrip-2" class='tabstrip' style="border:0px;"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="selectField" style="display:none;width:300px;height:200px;" >
		<div id='selectFieldComb' style='margin : 10px;' ></div>
	</div>
</body>
<script type="text/javascript" src="${contextPath}/scripts/pageworkflow/defined.kendo.js" ></script>
<script type="text/javascript" src="${contextPath}/scripts/pageworkflow/jquery.kendo.extends.js" ></script>
</html>
