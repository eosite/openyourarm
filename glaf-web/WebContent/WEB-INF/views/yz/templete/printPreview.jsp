<%@page import="com.glaf.core.config.SystemConfig"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
	String datasourceJson = (String)request.getAttribute("datasourceJson");
	String treedot_index_id = (String)request.getAttribute("treedot_index_id");
	String treepinfo_index_id = (String)request.getAttribute("treepinfo_index_id");
	String fileId = (String)request.getAttribute("fileId");
	
	String reportURL = SystemConfig.getString("report_service_url");
	reportURL += "&datasourceJson="+datasourceJson;
	reportURL += "&treedot_index_id="+treedot_index_id;
	reportURL += "&treepinfo_index_id="+treepinfo_index_id;
	reportURL += "&fileId="+fileId;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="renderer" content="ie-comp">
<title>打印预览</title>
<script type="text/javascript">
	function loadHeight(){
		var obj = document.getElementById("iframepage");
        obj.height = window.screen.height-150;
	}
</script>
<body style="margin:0px;overflow:hidden;scroll:no">
	<iframe src="<%=reportURL%>" id="iframepage" name="iframepage" style="border:0px" height="650" width="100%" scrolling="no" onLoad="loadHeight()"></iframe>
</body>
</head>
</html>

