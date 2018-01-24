<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目信息管理</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.base64.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
		<table><tr>
		<td>工程项目</td>
		<td><button type="button" id="button" class="k-button" onclick="javascript:save();">保存工程著录信息</button></td>
		</tr></table>
   </div>
</script>
<script type="text/javascript">
	jQuery(function() {
		$("#toolbar").kendoToolBar({
			items : [ {
				template : kendo.template(jQuery("#template").html())
			} ]
		});

		$("#tabstrip").kendoTabStrip({
			animation : {
				open : {
					effects : "fadeIn"
				}
			}
		});
		
		$("input[dtype='datetime']").kendoDatePicker({
			culture:"zh-CN",
			format:"yyyy-MM-dd"
		});
	});
	
	function save(){
		
		var params = $("#iForm").formSerialize();
		jQuery.ajax({
		   type: "POST",
		   url: "${pageContext.request.contextPath }/rs/isdp/wbs/pinfo/update?databaseCode=${param.databaseCode}",
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
			   window.parent.location.reload();
		   }
		 });
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		 <div class="x_content_title">
		  <img src="<%=request.getContextPath()%>/images/window.png">&nbsp;
		    工程项目著录
		 </div>
		<br>
		<div id="toolbar"></div>
		<form id="iForm">
		<input type="hidden" name="id" value="${pinfo['id']}" />
		<div id="tabstrip">
			<ul>
				<c:forEach items="${cellUTableTreeList }" var="cellUTable" varStatus="varStatus">
					<li ${varStatus.index==0?'class="k-state-active"':'' }>${cellUTable.indexName }</li>
				</c:forEach>
			</ul>
			<c:forEach items="${cellUTableTreeList }" var="cellUTable" varStatus="varStatus">
				<div>
					<table width="100%">
						<c:if test="${varStatus.index==0 }">
						<tr>
							<td width="10%" align="right"><label>工程名称：</label></td> 
							<td width="40%"><input type="text" name="treepNameIndexName" class="k-textbox" style="width: 300px;"value="${treepName.indexName }" /></td>
							<td width="10%" align="right"><label>工程编号：</label></td>
							<td width="40%"><input type="text" name="treepNameNum" class="k-textbox" style="width: 300px;"value="${treepName.num }" /></td>
						</tr>
						</c:if>
						<c:forEach items="${interfaceList }" var="interfaceModel">
							<c:if test="${interfaceModel.indexId==cellUTable.indexId }">
								<c:if test="${interfaceModel.listno%2==0 }">
									<tr>
								</c:if>
								
								<td  width="10%" align="right"><label>${interfaceModel.fname }</label></td>
								<td width="40%">
								<c:if test="${interfaceModel.dtype=='string' || interfaceModel.dtype=='char' }">
									<input type="text" class="k-textbox" name="${interfaceModel.dname }" style="width: 300px" value="${pinfo[interfaceModel.dname] }"  dtype="${interfaceModel.dtype }"/>
								</c:if>
								<c:if test="${interfaceModel.dtype=='r8' || interfaceModel.dtype=='i4' || interfaceModel.dtype=='int' }">
									<input type="text" class="k-textbox" name="${interfaceModel.dname }" style="width: 300px" value="<fmt:formatNumber value="${pinfo[interfaceModel.dname] }" pattern="#.#" />"  dtype="${interfaceModel.dtype }"/>
								</c:if>
								<c:if test="${interfaceModel.dtype=='datetime' }">
									<input type="text" class="k-textbox" name="${interfaceModel.dname }" style="width: 300px" value="<fmt:formatDate value="${pinfo[interfaceModel.dname] }" pattern="yyyy-MM-dd" />"  dtype="${interfaceModel.dtype }"/>
								</c:if>
								
								</td>
								
								<c:if test="${interfaceModel.listno%2==1 }">
									</tr>
								</c:if>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
		</div>
		</form>
	</div>
</body>
</html>