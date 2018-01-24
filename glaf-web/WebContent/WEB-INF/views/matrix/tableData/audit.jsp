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
<title>审核记录</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">

	function saveData(approval){
		if(!approval){
            var comment = document.getElementById("comment").value;
            if(comment == ""){
				alert('请输入不通过的原因！');
                document.getElementById("comment").focus();
				return;
			}
		}
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/tableDataSave/audit?approval='+approval,
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
							   window.parent.reloadGrid();
						   }
					   }  
				   }
			 });
	}

</script>
</head>

<body> 
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud" > 
		<span class="x_content_title">审核记录</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'" 
		   onclick="javascript:saveData(true);" >审核通过</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-no'" 
		   onclick="javascript:saveData(false);" >审核不通过</a>
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
		</td>
		<td align="left">
			<c:choose>
			  <c:when test="${col.javaType == 'Integer' or col.javaType == 'Long' or col.javaType == 'Double' or col.javaType == 'Date'}">  
			   ${col.value}
			  </c:when>
			  <c:otherwise>
			   <c:choose>
			    <c:when test="${!empty col.items}" >
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
			   <c:otherwise>
                ${col.value}
			   </c:otherwise>
			  </c:choose>
			 </c:otherwise>
			</c:choose>
		   <br>
		</td>
	</tr>
	</c:if>
    </c:forEach>

	<tr>
	 <td colspan="2">
	   <b>审核意见：</b>
	   <br>&nbsp;&nbsp;<textarea id="comment" name="comment" rows="6" cols="50" style="width:450px;height:125px;"></textarea>
     </td>
	</tr>

    <c:if test="${table.attachmentFlag == '1' || table.attachmentFlag == '2'}">
	<tr>
	 <td colspan="2">
        <iframe id="newFrame" name="newFrame" width="100%" height="350" border="0" frameborder="0"
		        src="${contextPath}/mx/tableData/showUpload?tableId=${table.tableId}&businessKey=${uuid}&audit=true&serviceKey=${table.tableId}&status=${status}"></iframe>
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