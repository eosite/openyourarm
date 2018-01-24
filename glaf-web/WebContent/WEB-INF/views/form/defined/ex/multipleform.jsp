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
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程表单绑定</title>
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
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
<script type="text/javascript">

/**
 * 全局参数
 */
var mtxx = {
	contextPath : '${contextPath}',
	url : 	"${contextPath}/mx/form/defined",
	targetId : '${param.targetId}',
	fn : '${param.fn}'
};
var $queue = $.Callbacks("mtxx.form.defined.multipleform");
window.parentDef = {};
$(function(){
	$queue.fire();
});
</script>
</head>
<body >
	<div id="container">
		<div id="vertical" style=''>
			
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; " ><img
							src="${contextPath}/images/setting_tools.png"
							style="vertical-align: middle;" /> 
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">流程表单绑定</span>
							<button id="button-3" class='k-button' type="button" t='selectProcess'>选 择 流 程</button>
							<button id="button-4" class='k-button' type="button" t='selectPage'>选 择 页 面</button>
							
						</td>
						<td style="text-align:right;">
							<button id="button-1" class='k-button' type="button" t='saveSet'>保  存  配  置</button>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="center-pane" style="border:0px;">
				<div style="border:0px;">
					<iframe id="processimage" align="center" frameborder="0" style="width:100%;height:100%;"> </iframe>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-1" class='tabstrip' style="border:0px;"></div>
				</div>
				<div style="border:0px;">
					<div id="variables" ></div>
				</div>
			</div>
			
			<div id="south-pane" style="border:0px;">
				<div style="border:0px;">
					
				</div>				
			</div>
			
			</div>
	</div>
</body>
<script type="text/javascript" src="${contextPath }/webfile/js/defined.kendo.param.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/mt.global.js"></script>
<script type="text/javascript" src="${contextPath }/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/multipleform.js"></script>
<script type="text/html" id="toolbar-01">
<div class="k-toolbar">
	<span class="k-span-msg" >节点信息:</span>
	<span class="k-span" style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;"></span>
	<button class='k-button' t='paraType'>输入&输出关系定义</button>
	
	<span class="k-span-terminal" >运行平台:</span>
	<select name="terminal">
		<option value="">PC/移动</option>
		<option value="pc">PC</option>
		<option value="mobile">移动</option>
	</select>

	<span class="k-span-href" >从属于:</span>
	<select name="k-href">
		<option value="">--请选择--</option>
	</select>
</div>
</script>
</html>