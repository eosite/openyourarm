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
<title>编辑记录</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">

	function saveData(refresh){
    <c:forEach var="col" items="${columns}" >
	<c:if test="${col.editableField == 'Y' && col.requiredField == '1'}">
	  if(document.getElementById("${col.id}").value==""){
		  alert("${col.title}是必须的，不能为空。");
          document.getElementById("${col.id}").focus();
		  return;
	  }
	</c:if>
	</c:forEach>
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/tableDataSave/saveData',
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
						   if(refresh){
						       window.parent.location.reload();
					           window.close();
						   } else { 
							   //alert(window.parent);
							   //alert(window.parent.document.getElementById("nodeId").value);
							   window.parent.reloadGrid();
                               <c:if test="${!empty uuid}">
                                 window.close();
							   </c:if>
						   }
					   }  
				   }
			 });
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" > 
    <div class="toolbar-backgroud" style="height:42px"> 
		&nbsp;<span class="x_content_title">编辑记录</span>
		<c:if test="${canEdit == true}">
		 <c:if test="${canUpdate == true}">
		 <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
		   onclick="javascript:saveData(false);" >保存</a>
		 <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
		   onclick="javascript:saveData(true);" >保存并关闭</a>
		 </c:if>
		</c:if>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
  <input type="hidden" id="tableId" name="tableId" value="${tableId}" >
  <input type="hidden" id="topId" name="topId" value="${topId}" >
  <table class="easyui-form" style="width:600px;margin-left:5px;" align="left">
    <tbody>
	
	<c:if test="${!empty treeNodes}">
    <tr>
		<td width="20%" align="left">上级节点</td>
		<td align="left">
             <select id="parentId" name="parentId">
			   <option value="">----请选择----</option>
			   <c:forEach var="tnode" items="${treeNodes}" >
				<option value="${tnode.id}">${tnode.blank}${tnode.name} [${tnode.code}]</option>
			   </c:forEach>
             </select>
		     <script type="text/javascript">
	
		     </script>
		</td>
	</tr>		
	</c:if>

   <c:forEach var="col" items="${columns}" >
	<c:if test="${col.editableField == 'Y'}">
    <tr>
		<td width="20%" align="left">
		<b>${col.title}</b> 
		<c:if test="${col.columnName == 'name_' || col.columnName == 'code_' || col.requiredField == '1'}">
			<label for="${col.id}" class="required">&nbsp;*</label>
		</c:if>
		</td>
		<td align="left">
		  <c:choose>
		    <c:when test="${col.javaType == 'Integer'}">
			<input id="${col.id}" name="${col.id}" type="text" 
			       class="easyui-numberbox easyui-validatebox x-text" style="width:90px;" precision="0"
				   <c:if test="${col.requiredField == '1'}"> required="true" data-options="required:true" </c:if>
				   value="${col.value}" size="10"/>
			</c:when>
			<c:when test="${col.javaType == 'Long'}">
			<input id="${col.id}" name="${col.id}" type="text" 
			       class="easyui-numberbox easyui-validatebox x-text" style="width:90px;" precision="0" 
				   <c:if test="${col.requiredField == '1'}"> required="true" data-options="required:true" </c:if>
				   value="${col.value}" size="10"/>
			</c:when>
			<c:when test="${col.javaType == 'Double'}">
			<input id="${col.id}" name="${col.id}" type="text" 
			       class="easyui-numberbox easyui-validatebox x-text" style="width:90px;" precision="${col.scale}"
				   <c:if test="${col.requiredField == '1'}"> required="true" data-options="required:true" </c:if>
				   value="${col.value}" size="10"/>
			</c:when>
			<c:when test="${col.javaType == 'Date'}">
			<input id="${col.id}" name="${col.id}" type="text" 
			       class="easyui-datebox easyui-validatebox x-text" style="width:120px;"  
				   <c:if test="${col.requiredField == '1'}"> required="true" data-options="required:true" </c:if>
				   value="${col.value}" size="30"/>
			</c:when>
			<c:otherwise>
			<c:choose>
			  <c:when test="${!empty col.items}">
                 <select id="${col.id}" name="${col.id}">
					<option value="">----请选择----</option>
					<c:forEach var="item" items="${col.items}" >
					<option value="${item.value}">${item.name}</option>
					</c:forEach>
                 </select>
				 <script type="text/javascript">
				     document.getElementById("${col.id}").value="${col.value}";
				 </script>
			  </c:when>
			  <c:when test="${col.length >= 2000 }" >
              <textarea id="${col.id}" name="${col.id}" type="text" 
			       class="easyui-validatebox  x-text" style="width:480px;height:240px"
				   <c:if test="${col.requiredField == '1'}"> required="true" data-options="required:true" </c:if>
				   >${col.value}</textarea>
			  </c:when>
			  <c:when test="${ col.length >= 500 }" >
              <textarea id="${col.id}" name="${col.id}" type="text" 
			       class="easyui-validatebox  x-text" style="width:480px;height:120px"
				   <c:if test="${col.requiredField == '1'}"> required="true" data-options="required:true" </c:if>
				   >${col.value}</textarea>
			  </c:when>
			  <c:otherwise>
			   <input id="${col.id}" name="${col.id}" type="text" 
			       class="easyui-validatebox  x-text" style="width:480px;"
				   <c:if test="${col.requiredField == '1'}"> required="true" data-options="required:true" </c:if>
				   value="${col.value}" size="50"/>
			  </c:otherwise>
			  </c:choose>
			 </c:otherwise>
		   </c:choose>
		   <br>
		</td>
	</tr>
	</c:if>
   </c:forEach>

    <c:if test="${table.attachmentFlag == '1' || table.attachmentFlag == '2'}">
	<tr>
	 <td colspan="2">
          <iframe id="newFrame" name="newFrame" width="100%" height="350" border="0" frameborder="0"
		          src="${contextPath}/mx/tableData/showUpload?tableId=${table.tableId}&businessKey=${uuid}&serviceKey=${table.tableId}&status=${status}"></iframe>
  
	 </td>
	</tr>
	</c:if>

	<tr>
	 <td colspan="2"><br><br><br><br></td>
	</tr>
	
    </tbody>
  </table>
 </form>
</div>
</div>

</body>
</html>