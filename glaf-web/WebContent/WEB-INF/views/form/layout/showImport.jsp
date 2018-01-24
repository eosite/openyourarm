<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>发布表单布局模板</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script language="JavaScript">

	function submitRequest(form){
		 if(document.getElementById("file").value==""){
             alert("请选择您要发布的格式为xls的表单布局模板！");
			 return;
		 }
          if(confirm("您准备更新表单布局模板，确认吗？")){
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
<form action="<%=request.getContextPath()%>/mx/form/layout/doImport" method="post"
	enctype="multipart/form-data" name="iForm" class="x-form">
<div class="content-block"  style=" width: 90%;" >
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="发布表单布局模板">&nbsp;发布表单布局模板
</div>
 
<div align="center">
<br>
<label for="file">请选择要发布表单布局模板，必须是xls格式</label>&nbsp;&nbsp;
<br><br>
<input type="file" id="file" name="file" size="50" class="input-file"> <br>
</div>
<br>

<div align="center"><br />
<input type="button" name="bt01" value="确定" class="btn btn-primary  btnGray"
	onclick="javascript:submitRequest(this.form);" /> 
<br />
<br />
</div>

</div>
</form>
</center>
</body>
</html>