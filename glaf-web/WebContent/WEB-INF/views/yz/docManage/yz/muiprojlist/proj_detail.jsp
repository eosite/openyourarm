<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标段管理</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	jQuery(function(){
		jQuery("#submitForm").form({
			url:"<%=request.getContextPath() %>/rs/muiProjListRest/saveProjMuiprojlist",
			onSubmit:function(){
				var isValid = $(this).form('validate');
				return isValid;
			},
			success:function(data){
				var _data = eval("("+data+")");
				if(_data.success){
					alert("保存成功！");
					parent.jQuery("#dataTable").datagrid("reload");
					art.dialog.close('proj_add_dialog');
				}else{
					alert("保存失败!Error="+_data.error);
				}
			}
		});
	});
</script>
</head>
<body style="margin: 1px;">  
	<form id="submitForm"  method="post">
		<input type="hidden" name="indexId" value="${model.indexId }" />
		<input type="hidden" name="id" name="id" value="${model.id }" />
		<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0" class="box">
		<tr>
			<td class="input-box">线路名称</td>
			<td><input id="lineName" class="easyui-combobox" name="lineName"  data-options="required:true,valueField:'lineName',textField:'lineName',url:'<%=request.getContextPath()%>/rs/muiProjListRest/getMuiProjLineNameList'" /></td>
		</tr>
		<tr>
			<td class="input-box">标段名称</td>
			<td><input id="projName" name="projName" value="${model.projName }" class="easyui-validatebox" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="input-box">数据库名称</td>
			<td><input id="dbName" name="dbName" value="${model.dbName }" class="easyui-validatebox" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="input-box">数据库服务器地址</td>
			<td><input id="serverName" name="serverName" value="${model.serverName }" class="easyui-validatebox" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="input-box">数据库登录名</td>
			<td><input id="user" name="user" value="${model.user }" class="easyui-validatebox" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="input-box">数据库登录密码</td>
			<td><input id="password" name="password" value="${model.password }" type="password" class="easyui-validatebox" data-options="required:true" /></td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" value="保存" onclick="javascript:jQuery('#submitForm').submit();" />
				<input type="button" value="取消" onclick="javascript:art.dialog.close('proj_add_dialog');" />
			</td>
		</tr>
		</table>
	</form>
</body>
</html>