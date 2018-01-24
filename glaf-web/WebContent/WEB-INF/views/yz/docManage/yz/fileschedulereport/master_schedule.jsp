<%@page contentType="text/html; charset=UTF-8" %>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>总体进度表</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	var setting = {
			view: {
				selectedMulti: false
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
	
	function zTreeOnClick(event, treeId, treeNode,clickFlag) {
		var dateParam = "year="+treeNode.id;
		if(treeNode.isMonth){
			dateParam = "isMonth=true&year="+treeNode.pId+"&month="+treeNode.id;
		};
    	var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileschedulereport/getMasterReport?"+dateParam;
    	var p = jQuery(document.body).layout('panel','center');
    	p.panel("refresh",url);
	}
	
	var nodeData = ${treeData};
	
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting, nodeData);
	});
</script>
</head>
<body class="easyui-layout" style="margin: 1px">  
	    <div data-options="region:'west',title:'工程结构',split:true" style="width:130px;">
	    	<ul id="myTree" class="ztree"></ul>
	    </div>  
	    <div data-options="region:'center',title:'统计',loadingMessage:'正在加载数据，由于数据量较大，请耐心等待……'" style="padding:1px;"></div>  
</body>
</html>