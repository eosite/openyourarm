<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>导入应用模块</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script language="JavaScript">

	function submitRequest(form){
		 if(document.getElementById("file").value==""){
             alert("请选择您要导入的格式为json的文本文件或多个json文件的zip包！");
			 return;
		 }
         if(confirm("您准备导入应用模块，确认吗？")){
             document.iForm.submit();
		 }
	}

</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<br>
<br>
<br>
<center>
<form action="<%=request.getContextPath()%>/mx/sys/application/importJson?parentId=${parentId}" method="post"
	enctype="multipart/form-data" name="iForm" class="x-form">
<div class="content-block"  style=" width: 98%;" >
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="导入应用模块">&nbsp;导入应用模块
</div>
 
<div align="center">
<br>
<label for="file">请选择导入的应用模块，必须是json格式或多个json文件的zip包</label>&nbsp;&nbsp;<br>
<input type="file" id="file" name="file" size="50" class="input-file"> <br>
</div>
<br>

<div align="center"><br />
<input type="button" name="bt01" value="确定" class="btn btn-primary"
	onclick="javascript:submitRequest(this.form);" /> 
<br />
<br />
</div>

</div>
</form>
</center>
</body>
</html>