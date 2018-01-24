<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
   	jQuery(document).ready(function() {
   		var id = '${pId}',type = '${type}', fn = "${fn}";
   		var url = "${contextPath}/mx/dep/report/depReportTemplate/importJsonTemplate?" + $.param({
   			pId : id,
   			type : type
   		});
	    $("#files").kendoUpload({
	    	async: {
	            saveUrl: url
	        },
	    	multiple : true,
	    	showFileList: true,
	    	dataType : 'json',
	    	success: function(e){
	    		var $p = (window.opener || parent);
	    		$p[fn] && $p[fn]();
	    	},
	    	upload: function(e){
	    		var files = e.files;
			    $.each(files, function () {
			        if (this.extension.toLowerCase() != ".json") {
			            alert("请上传html文件");
			            e.preventDefault();
			        }
			    });
	    	},
	    	error: function(e){
	    		
	    	}
        });
	});  
 </script>
</head>
<body style="margin-top:0px;">
	<div class="toolbar-backgroud" style="height:48px;line-height: 48px;"> 
		<input type="file" name="files" id="files"  accept="html/plain"/>
	</div>
	
</body>
</html>