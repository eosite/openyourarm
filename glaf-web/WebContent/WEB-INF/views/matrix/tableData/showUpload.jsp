<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
<link rel="stylesheet" href="${contextPath}/scripts/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/scripts/upload/css/style.css">
<link rel="stylesheet" href="${contextPath}/scripts/upload/css/jquery.fileupload.css">
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.base64.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/upload/js/jquery.fileupload-process.js"></script>
<!-- <script type="text/javascript" src="${contextPath}/scripts/upload/js/jquery.fileupload-image.js"></script> -->
<script type="text/javascript" src="${contextPath}/plugins/upload/js/jquery.fileupload-audio.js"></script>
<script type="text/javascript" src="${contextPath}/plugins/upload/js/jquery.fileupload-video.js"></script>
<script type="text/javascript" src="${contextPath}/plugins/upload/js/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">

    function deleteById(fileId){
		if(confirm("文件删除后不能恢复，确定删除吗？")){
          jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/dataFile/deleteById?fileId='+fileId,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   if(data.statusCode == 200){
                          jQuery("#div_"+fileId).hide();
					   }  
				   }
			 });
		}
	}

	function removeById(filename){
          jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/dataFile/deleteByName?serviceKey=${serviceKey}&businessKey=${businessKey}&status=${status}&filename='+filename,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   if(data.statusCode == 200){
                          jQuery("#div_"+jQuery.base64.encode(filename)).hide();
					   }  
				   }
			 });
	}

	function downById(fileId){
		window.open("${contextPath}/mx/dataFile/download?fileId="+fileId);
	}
</script>
</head>
<body>
<div class="container"> 
	
	<c:if test="${canUpload == true}">
	<span class="btn btn-success fileinput-button">
	  <i class="glyphicon glyphicon-plus"></i>
	  <span>选择文件...</span>
	  <!-- The file input field used as target for the file upload widget -->
	  <input id="fileupload" type="file" name="files[]" multiple>
	</span>
	<br>
	<!-- The global progress bar -->
	<div id="progress" class="progress">
	  <div class="progress-bar progress-bar-success"></div>
	</div>
	</c:if>
	
	<!-- The container for the uploaded files -->
	<div id="files" class="files">
	  <c:forEach var="file" items="${dataFiles}" >
	    <div id="div_${file.fileId}">${file.filename}&nbsp;
		<a href='javascript:downById("${file.fileId}");'>下载&nbsp;<img src="${contextPath}/images/download.png"></a>
		<c:if test="${canUpdate == true}">
		<a href='javascript:deleteById("${file.fileId}");'>删除&nbsp;<img src="${contextPath}/images/remove.png"></a>
		</c:if>
		</div>
	  </c:forEach>
	</div>
	<br>
</div>		 
<script>
/*jslint unparam: true */
/*global window, $ */
jQuery(function () {
    'use strict';
    // Change this to the location of your server-side upload handler:
    var url = '${contextPath}/mx/dataFile/doUpload?tableId=${tableId}&businessKey=${businessKey}';
	<c:choose>
	 <c:when test="${!empty businessKey}">
	 url = url + "&status=1";
	 </c:when>
	 <c:otherwise>
     url = url + "&status=0";
	 </c:otherwise>
    </c:choose>
    jQuery('#fileupload').fileupload({
        url: url,
        dataType: 'json',
		<c:if test="${table.attachmentExts != ''}">
		acceptFileTypes: /(\.|\/)(${table.attachmentExts})$/i,
		</c:if>
		<c:if test="${table.attachmentFlag == '1'}">
		maxNumberOfFiles: 1,
		</c:if>
		<c:if test="${table.attachmentSize >= 0}" >
		//maxFileSize: 1024000*${table.attachmentSize} ,
		maxFileSize: 999000,
		</c:if>
        done: function (e, data) {
            jQuery.each(data.files, function (index, file) {
				//alert(file.url + "->"+file.name+"  " + file.size);
                jQuery('<div id="div_'+jQuery.base64.encode(file.name)+'">').html(file.name+"&nbsp;<a href='javascript:removeById(\""+file.name+"\");'>&nbsp;删除&nbsp;<img src=\"${contextPath}/images/remove.png\"></a></div>").appendTo('#files');
            });
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            jQuery('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
});
</script>
</body>
</html>