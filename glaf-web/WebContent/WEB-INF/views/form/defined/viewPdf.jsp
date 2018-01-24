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
<title>查看文件</title>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<style type="text/css">
	html body{
		height: 100%;
		padding: 0px;
		margin: 0px;
	}
</style>
</head>
<body>
	<iframe id="iframe" src="" width="99%" height="99%" border="0">
		
	</iframe>
	<script type="text/javascript">
		$(function(){
			var contextPath = '${contextPath}' , model = '${param.model}', rp = '${param.rp}' , id = '${param.id}' , key = '${param.key}' , url = '${param.url}' ,fid = '${param.fid}',
			baseUrl = contextPath+"/scripts/pdf.js/web/viewer.html" ;
			var path = contextPath /*+ url*/ ;
			//contextPath+"/mx/form/imageUpload?
			if(model == "ATTA" || model == "FTP"){
				path = "method=fileView&mode="+model+"&rp="+rp+"&id="+id+"&key="+key+"&url="+url+"&fid="+fid ;
			}else{
				if(key && url){
					path = "method=fileView&mode="+model+"&rp="+rp+"&id="+id+"&key="+key+"&url="+url+"&fid="+fid ;
				}else{
					path = "method=downloadById&id="+id ;
				}
			}
			$("#iframe").attr("path",contextPath+"/mx/form/imageUpload?").attr("src",baseUrl+"?file="+path.replace(/\&/ig, "@").replace(/\=/ig, "!"));
			//$("#embed").attr("src",path)
			//var embed = '<embed id="embed" type="application/pdf" src="'+path+'" width="100%" height="99%"></embed>';
			//$(embed).appendTo("body");
			//intializePage(path);
			//intializePage("/glaf/word.doc");
			//intializePage("/glaf/pdf.pdf");
		});
	</script>
</body>
</html>