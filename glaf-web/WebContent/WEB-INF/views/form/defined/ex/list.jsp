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
<title>Java定义平台</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.ztree.extends.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.cookie.js" ></script>
<%@ include file="/WEB-INF/views/form/defined/table/select_js.jsp"%>
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

.k-tabstrip-wrapper,#tabletree{
	height: 100%;
}

#tabletree .k-content{
	padding: 0px;
	height: 100%;
}
</style>
<script type="text/javascript">

/**
 * 全局参数
 */
var mtxx = {
	contextPath : '${contextPath}',
	url : 	"${contextPath}/mx/form/defined",
	selectedNode : null,
	selectPageId:'${param.selectPageId}',
	selectPageName:'${param.selectPageName}',
}, contextPath = '${contextPath}';

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
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">华闽通达自定义平台</span>
							<span style="font-size: 16px;font-weight: bolder;">(JAVA)</span>
						</td>
						<td style="text-align:right;">
							<button id="button-113" class='k-button' type="button">静态发布</button>
							<button id="button-112" class='k-button' type="button">引入规则</button>
							<button id="button-111" class='k-button' type="button">控  件  初  始  化</button>
							UI类型:<select id="uiTypeSelect" ></select>
							<button id="button-22" class='k-button' type="button">事  件  流  程</button>
							<button id="button-11" class='k-button' type="button">页  面  设  计</button>
							<button id="button-1" class='k-button' type="button">保  存  配  置</button>
							<button id="button-2" class='k-button' type="button">预  览</button>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="center-pane" style="border:0px;">
				<div style="border:0px;">
					<div id="tabletree">
					    <ul>
					        <li>页面</li>
					        <li>页面控件列表</li>
					        <li>数据集关联</li>
					    </ul>
					    <div>
							<div style='margin: 2px;'>
								模块 : <div id="pageCategory"></div>
							</div>
							<div style='margin: 2px;'>
								<button id="button-3" class='k-button' type="button" >分类</button>
								<input type='checkbox' title="显示禁用分类" id='showLockedBox'  />
								<input type='text' class='k-textbox' id='searchTextBox' style='width:60px;' />
								<button id="button-4" class='k-button' type="button">搜索</button>
							</div>
							<div class="ztree" id="tree" style='height:auto;border:0px;' ></div>
					    </div>
					    <div>
					    	<div class="ztree" id="widgetTree" style='height:auto;border:0px;' ></div>
					    </div>
					    <div>
					    	<div class="ztree" id="componentDataSetTree" style='height:auto;border:0px;' ></div>
					    </div>
					</div>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-1" class='tabstrip' style="border:0px;"></div>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-2" class='tabstrip' style="border:0px;"></div>
				</div>
				
			</div>
			
			<%--<div id="south-pane" >
				<table style="width: 100%;">
					<tr>
						<td style="text-align:center;">
							<span style="" >华闽通达 版权所有</span>
						</td>
					</tr>
				</table>
			</div>
		--%></div>
	</div>
	<div id="selectField" style="display:none;width:300px;height:200px;" >
		<div id='selectFieldComb' style='margin : 10px;' ></div>
	</div>
</body>
<script type="text/javascript" src="${contextPath }/webfile/js/defined.kendo.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/defined.kendo.paramConvert.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/defined.kendo.eventParam.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/defined.kendo.param.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/defined.kendo.openWin.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.kendo.extends.js" ></script>
</html>
