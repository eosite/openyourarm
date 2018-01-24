<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>质量计划统计</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
var width = document.documentElement.clientWidth,height=document.documentElement.clientHeight;
$(function() {
	$("#main_content").height(height-20);
	$("#main_content").kendoSplitter({
        orientation: "horizontal",
        panes: [
			{ 
				collapsible: true,
				contentUrl:"${pageContext.request.contextPath}/mx/isdp/collect?method=showCharts&tableName=QUALITY_QUESTION_STAT&type=1"
			},{ 
				collapsible: false,
				contentUrl:"${pageContext.request.contextPath}/mx/isdp/collect?method=showCharts&tableName=QUALITY_QUESTION_STAT&type=2"
			}
        ]
    });
});
</script>
</head>
<body>
<div id="main_content">
	<div id="left-panel"></div>
	<div id="center-panel"></div>
</div>
</body>
</html>