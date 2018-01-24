<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.config.SystemConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看TXT文件</title>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script src="${contextPath}/scripts/officecontrol/OfficeContorlFunctions.js" type="text/javascript"></script>
<style type="text/css">
	html body{
		height: 100%
	}
</style>
</head>
<body>
	<!-- <embed id="textContent" type="application/json" src="<%=request.getContextPath()%>/mx/form/imageUpload?method=downloadById&id=${param.id}" ></embed> -->
	<!-- <p id="textContent"></p> -->
	<pre id="textContent" style="word-wrap: break-word; white-space: pre-wrap; white-space: -moz-pre-wrap" >
</body>
<script type="text/javascript">
	var s = '${param.srcl}' ;
	s = s.replace(/\@/ig, "&"),
	id = '${param.id}',
	width = window.innerWidth,
	height = window.innerHeight;

	var contextPath = '<%=request.getContextPath()%>' , 
		model = '${param.model}', 
		rp = '${param.rp}' , 
		id = '${param.id}' , 
		key = '${param.key}' , 
		url = '${param.url}' ,
		fid = '${param.fid}' ;
	if(id){
		s = "<%=request.getContextPath()%>/mx/form/imageUpload?method=downloadTextById&id="+id ;
	}

	if(fid && key && url){
		s = contextPath+"/mx/form/imageUpload?method=fileView&mode="+model+"&rp="+rp+"&id="+id+"&key="+key+"&url="+url+"&fid="+fid ;
	}
	//img.setAttribute('src', s);
	$.ajax({
		url: s,
		type: 'POST',
		dataType: 'text',
		"data": {},
	}).done(function(result) {
		$("#textContent").text(result);

	}).fail(function() {
		console.log("error");
	})

</script>
</html>