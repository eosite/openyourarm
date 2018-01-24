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
<title>字段列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/save',
				   data: params,
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
					       window.parent.location.reload();
					   } 
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("tableId").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/save',
				   data: params,
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
					       window.parent.location.reload();
					   }
				   }
			 });
	}

	function switchAttachmentFlag(){
        var attachmentFlag = document.getElementById("attachmentFlag").value;
		if(attachmentFlag == "1" || attachmentFlag == "2"){
			jQuery("#attachmentExtsDiv").show();
			jQuery("#attachmentSizeDiv").show();
		} else {
			jQuery("#attachmentExtsDiv").hide();
			jQuery("#attachmentSizeDiv").hide();
		}
	}


</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑表</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="tableId" name="tableId" value="${tableId}"/>
  <table class="easyui-form" style="width:650px;" align="center">
    <tbody>
	<tr>
		<td width="20%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  style="width:420px;"
				   value="${tableDefinition.title}" size="60"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">描述</td>
		<td align="left">
            <textarea id="description" name="description" type="text" 
			          class="easyui-validatebox  x-text"
					  style="width:420px;height:90px;">${tableDefinition.description}</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">聚合列</td>
		<td align="left">
            <textarea id="aggregationKeys" name="aggregationKeys" type="text" 
			          class="easyui-validatebox  x-text"
					  style="width:420px;height:90px;">${tableDefinition.aggregationKeys}</textarea>
			<br>&nbsp;（提示：构成一条数据唯一的列，也可以多个列数据构成业务上的唯一数据）
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">开始行号</td>
		<td align="left">
            <input id="startRowIndex" name="startRowIndex" type="text" 
			       class="easyui-numberbox  x-text"  style="width:90px;"
				   value="${tableDefinition.startRowIndex}" size="5"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">结束跳过行</td>
		<td align="left">
            <input id="stopSkipRow" name="stopSkipRow" type="text" 
			       class="easyui-numberbox  x-text"  style="width:90px;"
				   value="${tableDefinition.stopSkipRow}" size="5"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">结束词</td>
		<td align="left">
            <input id="stopWord" name="stopWord" type="text" 
			       class="easyui-validatebox  x-text"  style="width:120px;"
				   value="${tableDefinition.stopWord}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">切分符</td>
		<td align="left">
            <input id="split" name="split" type="text" 
			       class="easyui-validatebox  x-text"  style="width:120px;"
				   value="${tableDefinition.split}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">是否树表</td>
		<td align="left">
            <select id="treeFlag" name="treeFlag">
				<option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("treeFlag").value="${tableDefinition.treeFlag}";
			</script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">上传附件</td>
		<td align="left">
            <select id="attachmentFlag" name="attachmentFlag" onchange="javascript:switchAttachmentFlag();">
				<option value="0">不允许</option>
				<option value="1">仅一个附件</option>
				<option value="2">允许多附件</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("attachmentFlag").value="${tableDefinition.attachmentFlag}";
			</script>
		</td>
	</tr>
	<tr id="attachmentExtsDiv" style="display:none">
		<td width="20%" align="left">附件扩展名</td>
		<td align="left">
             <input id="attachmentExts" name="attachmentExts" type="text" 
			       class="easyui-validatebox  x-text"  style="width:420px;"
				   value="${tableDefinition.attachmentExts}" size="60"/>
			<br>&nbsp;（提示：多种类型附件文件类型以半角的|隔开。如图像类型：jpg|jpeg|png|gif）
		</td>
	</tr>
	<tr id="attachmentSizeDiv" style="display:none">
		<td width="20%" align="left">附件大小限制</td>
		<td align="left">
            <select id="attachmentSize" name="attachmentSize">
				<option value="1">1MB</option>
				<option value="2">2MB</option>
				<option value="3">3MB</option>
				<option value="4">4MB</option>
				<option value="5">5MB</option>
				<option value="10">10MB</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("attachmentSize").value="${tableDefinition.attachmentSize}";
			</script>
			&nbsp;（提示：附件大小限制，单个附件的大小不能超过该限制）
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">授权访问</td>
		<td align="left">
            <select id="privilegeFlag" name="privilegeFlag">
				<option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("privilegeFlag").value="${tableDefinition.privilegeFlag}";
			</script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">审核标识</td>
		<td align="left">
            <select id="auditFlag" name="auditFlag">
				<option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("auditFlag").value="${tableDefinition.auditFlag}";
			</script>
			（提示：设置审核标记后，凡是通过审核的数据不允许修改。）
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">是否从表</td>
		<td align="left">
            <select id="isSubTable" name="isSubTable">
				<option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("isSubTable").value="${tableDefinition.isSubTable}";
			</script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">级联删除</td>
		<td align="left">
            <select id="deleteCascade" name="deleteCascade">
			    <option value="0">可物理删除，记录删除后不能恢复</option>
				<option value="1">不做物理删除，只打删除标记</option>
				<option value="2">不能删除</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("deleteCascade").value="${tableDefinition.deleteCascade}";
			</script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">每次新增</td>
		<td align="left">
            <select id="insertOnly" name="insertOnly">
				<option value="Y">是</option>
				<option value="N">否</option>
            </select>
			<script type="text/javascript">
			    document.getElementById("insertOnly").value="${tableDefinition.insertOnly}";
			</script>
		</td>
	</tr>
	<tr><td><br><br><br><br>&nbsp;</td></tr>
    </tbody>
  </table>
  </form>
</div>
</div>
<script type="text/javascript">
    switchAttachmentFlag();
</script>
</body>
</html>