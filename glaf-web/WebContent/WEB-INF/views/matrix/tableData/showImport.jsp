<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script language="javascript">

	function submitRequest(form){
		if(document.getElementById("file").value==""){
            alert("请选择您要导入的Excel文件！");
			return;
		}
        document.iForm.submit(); 
	}

</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<br>
<br>
<br>
<center>
<form action="<%=request.getContextPath()%>/mx/tableData/importXls?tableId=${tableId}" method="post"
	enctype="multipart/form-data" name="iForm" class="x-form">
<div class="content-block" style="width: 90%;" >
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="Excel导入">&nbsp;Excel导入
</div>
 
<div align="center">
  <br>
  <label for="file">请选择您要导入的Excel文件</label>&nbsp;&nbsp;
  <input type="file" id="file" name="file" size="50" class="input-file"> <br>
</div>
<br>

<div align="center">
<br>
<input type="button" name="bt01" value="确定" class="btn btnGray"
	   onclick="javascript:submitRequest(this.form);" /> 
<br>
<br>
</div>

</div>
</form>
</center>
</body>
</html>