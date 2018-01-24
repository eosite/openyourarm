<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>各标段分部进度表</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	var setting = {
			view: {
				selectedMulti: false
			},
			async: {
				enable: true,
				url: "<%=request.getContextPath()%>/rs/fileScheduleReportRest/getMasterDateTree",
				autoParam: ["id","systemName","pId=indexId"]
			},
			data:{
				simpleData:{
					enable:true
				}
			},
			callback: {
				onClick: zTreeOnClick
			}
	};
	
	var nodeData = ${treeData};
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting, nodeData);
	});

	function zTreeOnClick(event, treeId, treeNode,clickFlag) {
		if(!treeNode.isUnit){
			var dateParam = "&indexId="+treeNode.pId+"&year="+treeNode.id;
			if(treeNode.isMonth){
				dateParam = "&isMonth=true&indexId="+treeNode.indexId+"&year="+treeNode.pId+"&month="+treeNode.id;
			}
	    	var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileschedulereport/getSubsectionReport?systemName="+treeNode.systemName+dateParam;
			var p = jQuery(document.body).layout('panel','center');
			p.panel("refresh",url);
			
		}
	}
</script>
</head>
<body class="easyui-layout" style="margin:1px;">  
	<div data-options="region:'west',title:'工程结构',split:true,border:true" style="width: 135px;margin: 1px">
    	<ul id="myTree" class="ztree"></ul>
    </div>  
    <div data-options="region:'center',title:'统计结果',loadingMessage:'正在加载数据，由于数据量较大，请耐心等待……'" style="padding:1px;"></div>  
</body>
</html>