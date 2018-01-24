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
		<!--<td>工程项目</td>
		<td><button type="button" id="button" class="k-button" onclick="javascript:update();">工程项目信息登记</button></td>-->
		<td>选择标段数据</td>
		<td><input type="text" id="databaseDropDownList" /></td>
		</tr></table>
   </div>
</script>
<script type="text/javascript">
	$(function() {
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
		
		$("#databaseDropDownList").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "code",
            optionLabel: "请选择项目标段",
            dataSource: {
            	transport:{
            		read:{
            			contentType: "application/json",
                        type: "POST",
                        url:"${pageContext.request.contextPath}/rs/isdp/global/databaseJson"
            		}
            	}
            },
            value:"${param.databaseCode}",
            change:function(e){
            	document.location = "${pageContext.request.contextPath}/mx/isdp/wbs/pinfo?method=show&databaseCode="+this.value();
            }
        });
	});
	
	function update(){
		var link="${pageContext.request.contextPath}/mx/isdp/wbs/pinfo?method=update&databaseCode=${param.databaseCode}";
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "工程项目著录",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			//offset: ['20px',''],
			fadeIn: 100,
			area: [(jQuery(window).width() - 150)+'px', (jQuery(window).height() - 150) +'px'],
            iframe: {src: link}
		});
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div id="toolbar"></div>
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
							<td width="40%"><input type="text" class="k-textbox" style="width: 300px;" value="${treepName.indexName }" /></td>
							<td width="10%" align="right"><label>工程编号：</label></td>
							<td width="40%"><input type="text"class="k-textbox" style="width: 300px;"value="${treepName.num }" /></td>
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
									<input type="text" class="k-textbox" style="width: 300px" value="${pinfo[interfaceModel.dname] }" />
								</c:if>
								<c:if test="${interfaceModel.dtype=='r8' || interfaceModel.dtype=='i4' || interfaceModel.dtype=='int' }">
									<input type="text" class="k-textbox" style="width: 300px" value="<fmt:formatNumber value="${pinfo[interfaceModel.dname] }" pattern="#.#" />" />
								</c:if>
								<c:if test="${interfaceModel.dtype=='datetime' }">
									<input type="text" class="k-textbox" style="width: 300px" value="<fmt:formatDate value="${pinfo[interfaceModel.dname] }" pattern="yyyy-MM-dd" />" />
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
	</div>
</body>
</html>