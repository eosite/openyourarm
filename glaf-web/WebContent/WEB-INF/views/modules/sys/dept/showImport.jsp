<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>导入数据文件</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script language="javascript">

	function submitRequest(form){
		 if(document.getElementById("file").value==""){
             alert("请选择要导入的数据文件，必须是Excel97-2003格式！");
			 return;
		 }
          if(confirm("您准备数据文件，确认吗？")){
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
<form
	action="<%=request.getContextPath()%>/mx/sys/department/doImport"
	method="post" enctype="multipart/form-data" name="iForm" class="x-form">
<div class="content-block" style="width: 98%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="导入数据文件">&nbsp;导入数据文件
</div>


<div align="center"><br>
<label for="file">请选择要导入的数据文件，必须是Excel97-2003格式</label>&nbsp;&nbsp; 
<br><input type="file" id="file" name="file" size="50" class="input-file"> <br>
</div>
<br>

<div align="center"><br>
<label for="filex">模板下载</label>&nbsp;&nbsp; 
<br><a href="<%=request.getContextPath()%>/templates/Department.xls" target="_blank">机构模板</a><br>
</div>
<br>
 

<div align="center"><br />
<input type="button" name="bt01" value="确定" class="btn btn-primary"
	onclick="javascript:submitRequest(this.form);" /> <br />
<br />
</div>

</div>
</form>
</center>
</body>
</html>