<%@page import="com.glaf.base.modules.sys.model.CellTreedot"%>
<%@page import="com.glaf.chinaiss.model.CellUtableTree"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
	CellTreedot treedot = (CellTreedot)request.getAttribute("treedot");
	List<CellUtableTree> tableTreeList = (List<CellUtableTree>)request.getAttribute("tableTreeList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="renderer" content="ie-comp">
<title>项目信息管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<c:forEach items="${tableTreeList }" var="tableTree" varStatus="stat">
		<div title="${tableTree.indexName }" style="padding:3px">
			<table align="center" width="80%" cellpadding="5" cellspacing="5">
				<c:if test="${stat.index==0 }">
				<tr>
					<td width="10%" align="right">工程名称</td>
					<td width="40%"><input style="width: 300px" value="${treepName.indexName }" /></td>
					<td width="10%" align="right">工程编号</td>
					<td width="40%"><input style="width: 300px" value="${treepName.num }" /></td>
				</tr>
		        </c:if>
				<c:forEach items="${interfaceList }" var="interfaceModel" varStatus="sta">
					<c:if test="${interfaceModel.indexId==tableTree.indexId }">
					<c:if test="${interfaceModel.listNo%2==0 }">
						<tr>
					</c:if>
						<td width="10%" align="right">${interfaceModel.fname }</td>
						<td width="40%"><input style="width: 300px" value="${pinfo[interfaceModel.dname] }" /></td>
					<c:if test="${interfaceModel.listNo%2==1 }">
						</tr>
					</c:if>
					</c:if>
				</c:forEach>
				
			</table>
	    </div>
	</c:forEach>
	</div>
</body>
</html>
