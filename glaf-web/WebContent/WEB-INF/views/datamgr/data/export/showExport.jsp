<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/js/i18n/grid.locale-en.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqueryui/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqgrid/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script type="text/javascript">
     
	function exportSqlite(){
		var databaseId = jQuery("#databaseId").val();
		if(databaseId != ""){
            window.open("<%=request.getContextPath()%>/mx/datamgr/data/export/exportSqlite?databaseId="+databaseId+"&dataExportId=${dataExport.id}");
		} else {
			alert("请选择一个来源数据库库");
		}
	}

	function exportJson(){
		var databaseId = jQuery("#databaseId").val();
		if(databaseId != ""){
            window.open("<%=request.getContextPath()%>/mx/datamgr/data/export/exportJson?databaseId="+databaseId+"&dataExportId=${dataExport.id}");
		} else {
			alert("请选择一个来源数据库库");
		}
	}


	function exportDataCenter(){
        if (confirm("确定导出数据包到交换平台吗？")) {
			var link = "<%=request.getContextPath()%>/rs/repository/export/pkg?dataExportId=${dataExport.id}";
			var params = jQuery("#iForm").formSerialize();
			jQuery.ajax({
					   type: "POST",
					   url: link,
					   dataType:  'json',
					   data: params,
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
					            
					       }
					   }
				 });
		   }    
	}

	function resetExportFlag(){
        if (confirm("状态重置后全部数据将重新生成，确定重置吗？")) {
            var link = "<%=request.getContextPath()%>/mx/datamgr/data/export/resetExportFlag?dataExportId=${dataExport.id}";
			var params = jQuery("#iForm").formSerialize();
			jQuery.ajax({
					   type: "POST",
					   url: link,
					   dataType:  'json',
					   data: params,
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
					            
					       }
					   }
				 });
		}
	}

</script>
</head>
<body>
<form id="iForm" name="iForm" method="post" >
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="20%" align="left" valign="middle">数据库&nbsp;</td>
    <td align="left" valign="middle">
	  <label for="name" class="required2"></label>
      <span style="font-size:13px;">--------请选择要导出的数据库--------</span><br>
      <select id="databaseId" name="databaseId" class="select">    
		<c:forEach items="${databases}" var="item">
		<option value="${item.id}">${item.title}</option>     
		</c:forEach>
		</select>
		<script type="text/javascript">
			 document.getElementById("databaseId").value="${dataExport.databaseId}";
		</script>
    </td>
  </tr>
  <tr>
    <td width="20%" align="left">&nbsp;</td>
    <td align="left">
	    <input type="button" value="导出Json数据" onclick="javascript:exportJson();" class="btnGray">
		&nbsp;&nbsp;
	    <input type="button" value="导出SQLite数据" onclick="javascript:exportSqlite();" class="btnGray">
	    &nbsp;&nbsp;
		<br><br>
		<input type="button" value="重置状态" onclick="javascript:resetExportFlag();" class="btnGray">
	    &nbsp;&nbsp;
		<input type="button" value="导出数据到交换平台" onclick="javascript:exportDataCenter();" class="btnGray">
	    &nbsp;&nbsp;
		<br><br>	    
	</td>
  </tr>
</table>
</form>
</body>
</html>