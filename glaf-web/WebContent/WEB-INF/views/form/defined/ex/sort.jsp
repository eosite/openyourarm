<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>控件分组</title>
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

.selected-cls {
	background-color: yellow;
}

.ui-selectable-helper {
	border: 5px #FFB90F solid
}

</style>

<link rel="stylesheet"
	href="${contextPath}/webfile/styles/jsplumbtable.css">
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
	var mtxx = {
		fn : '${fn}',
		targetId : '${targetId}',
		parent : window.opener || window.parent,
		contextPath : '${pageContext.request.contextPath}',
		isPage : "${param.isPage}"
	};
</script>
</head>
<body>

	<div id="container">
		<div id="vertical" style=''>
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; "><img
							src="${contextPath}/images/setting_tools.png"
							style="vertical-align: middle;" /> <span
							style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">控件分组</span>
						</td>
						<td style="text-align:right;">
							<button id="sort_submit_btn" class="k-button">保存</button>
						</td>
					</tr>
				</table>
			</div>

			<div id="center-pane">
				<div>
					<div id="tabstrip-1" class='tabstrip' style="">
						<ul>
							<li class="k-state-active">控件列表分组</li>
							<li id='view-li-1'>可视化分组</li>
						</ul>
						<div id="view-2" style="height:95%;">
							<table style="height:100%;width:100%;">
								<tr>
									<td
										style="height:40px;text-align: center;font-family:宋体;font-size: 20px;font-weight: bolder;">可选分组控件</td>
									<td
										style="height:40px;text-align: center;font-family:宋体;font-size: 20px;font-weight: bolder;">分
										组</td>
								</tr>
								<tr>
									<td style="width: 40%">
										<div id="availableColTb"></div>
									</td>
									<td style="width: 40%">
										<table style="height:100%;width:100%;" id='sort-table'
											name='sortedColTb'></table>
									</td>
								</tr>
							</table>
						</div>
						<div id='view-1' style="height:95%;">
							<table style="height:100%;width:100%;">
								<tr>
									<td style="width: 80%"><iframe id='iframe-1'
											style="width:100%;height:99%" frameborder=0 scrolling='auto'
											src=''></iframe>
									</td>
									<td></td>
									<td style="width: 15%">
										<table style="height:100%;width:99%; margin-left: 10px;"
											id='view-table-1' name='viewedColTb'></table>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath }/webfile/js/defined.kendo.sort.js"></script>
</html>