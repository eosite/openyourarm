<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.core.base.DataFile,com.glaf.chinaiss.model.GisInfo"%>
<% 
/**
	DataFile df = (DataFile)request.getAttribute("df");
	String fileId = "";
	if(df != null)
		fileId = df.getFileId();
*/
	GisInfo gis = (GisInfo)request.getAttribute("df");
	String url = "";
	if(gis != null)
		url = gis.getImgUrl();
	String index_id = (String)request.getAttribute("index_id");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		
	</style>
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.min.js"></script>
	<!-- swf -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/swfobject.js"></script>
	<title>全景图</title>
	<script type="text/javascript">
		$(document).ready(function(){
			//alert("<%=request.getContextPath()%>/mx/lob/lob/download?fileId=");
			//$.post("<%=request.getContextPath()%>/mx/lob/lob/download",{fileId:''},function(result){});
			
			swfobject.embedSWF("<%=url%>", "aa", $(window).width()-30, $(window).height()-40, "18.0.0", "");
			$('#upload').click(function(){
			 	$("#userForm2").submit();
			 	setTimeout("window.location.reload()",1000);
			 });
		});
	</script>
</head>
<body>
<div>
<!-- <a href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId=">下载</a> -->
	<form id="userForm2" action="<%=request.getContextPath()%>/mx/docManage/yz/gisInfo/uploadSwf" enctype="multipart/form-data" method="post" target="imgPost">  
   	 <input type="hidden" name="index_id" id="index_id" value="<%=index_id%>">
   	 <input type="hidden" name="type" id="type" value="swf">    
        <div id="newUpload2">  
            <input type="file" name="file" id="file"><input type="button" value="上传" id="upload" style="height: 20px"/>
        </div>  
    </form>  
   
</div>
<div id="aa" >
</div> 
</body>
</html>


                                                                                                         