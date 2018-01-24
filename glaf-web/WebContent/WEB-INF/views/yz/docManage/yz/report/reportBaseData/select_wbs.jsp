<%@page contentType="text/html; charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择WBS工程节点</title>
<%@include file="../../inc/script.jsp" %>
<script type="text/javascript">
	var setting = {
		async: {
			enable: true,
			url: "<%=request.getContextPath()%>/rs/treepInfoRest/getTreepinfoByParentId?systemName=${systemName}",
			autoParam:["indexId"],
			type:"post"
		},
		callback:{
			onExpand:zTreeOnExpand
		},
		view: {
			showIcon: false
		}
	};
	
	function zTreeOnExpand(event, treeId, treeNode){
		if(treeNode.children.length==0){
			treeNode.isParent = false;
			var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
			treeObj.updateNode(treeNode);
		}
	}
	
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});
	
	function selectWBS(){
		var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
		var nodes = treeObj.getSelectedNodes();
		
		if(nodes && nodes.length>0){
			window.returnValue = [nodes[0].indexId,nodes[0].name];
			window.close();
		}else{
			jQuery.messager.alert("错误","请选择工程节点！","error");
		}
	}
</script>
</head>
<body class="easyui-layout" style="margin:1px;">
<div style="margin:0;"></div>
	<div data-options="region:'center'"><ul id="myTree" class="ztree"></ul></div>
	<div data-options="region:'south',border:false" style="height:30px;text-align: right">
		<input type="button" id="selectBtn" value="确定" onclick="selectWBS();" />
		<input type="button" id="cancelBtn" value="取消" onclick="window.close();" />
	</div>
</body>
</html>