<%@page import="com.glaf.core.config.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String useDatabase = request.getParameter("useDatabase")==null?"default":request.getParameter("useDatabase");
	
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    String ztreeMaxCount=com.glaf.core.config.SystemConfig.getString("treeNodeLoadQty", "500");
;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看表格</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">

	var setting = {
		async: {
			enable: true,
			url: "<%=request.getContextPath()%>/rs/treepInfoRest/getTreepinfoByParentId?systemName=<%=useDatabase%>",
			autoParam:["indexId"],
			otherParam:["pageName" , "table"],
			type:"post"
		},
		callback:{
			onExpand:zTreeOnExpand,
			onClick:zTreeOnClick,
			onAsyncSuccess: onAsyncSuccess
		},
		view: {
			showIcon: true
		}
	};
	
	function zTreeOnExpand(event, treeId, treeNode){
		if(treeNode.children.length==0){
			treeNode.isParent = false;
			var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
			treeObj.updateNode(treeNode);
		}
		
	}
	
		function onAsyncSuccess(event, treeId, treeNode) {
			if(treeNode.count<<%=ztreeMaxCount%> ){
			if(treeNode.count>1){
			
			expandNodes(treeNode.children);
		      }else{
		      treeNode.isParent=false;
		      
		      }
		    
		  
		}
			
			
			
		}
		
		function expandNodes(nodes) {
		if (!nodes) return;
		 var treeObj = $.fn.zTree.getZTreeObj("myTree");
			for (var i=0, l=nodes.length; i<l; i++) {
			    
				treeObj.expandNode(nodes[i], true, false, false);
				if (nodes[i].isParent && nodes[i].zAsync) {
					expandNodes(nodes[i].children);
				} else {
					goAsync = true;
				}
			}
		}


	
	function zTreeOnClick(event, treeId, treeNode){
		jQuery("#sheetTable").datagrid({url:'<%=request.getContextPath()%>/rs/viewTableRest/sheetTable?useDatabase=<%=useDatabase%>&indexId='+treeNode.indexId});
		jQuery("#fileTable").datagrid({url:'<%=request.getContextPath()%>/rs/viewTableRest/fileImage?useDatabase=<%=useDatabase%>&indexId='+treeNode.indexId});
	}
	
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});
	
	jQuery(function(){
		jQuery("#sheetTable").datagrid({
			onSelect:function(rowIndex,rowData){
				showCellData(rowIndex,rowData);
			},
			onLoadSuccess:function(data){
				if(data.rows!=0){
					jQuery("#sheetTable").datagrid("selectRow",0);
				}else{
					document.getElementById("showDataFrame").src = "";
				}
			}
		});
		
		jQuery("#fileTable").datagrid({
			onSelect:function(rowIndex,rowData){
				showCellData(rowIndex,rowData);
			}
		});
		
		document.getElementById("showDataFrame").src = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/cellTable/showTable?method=loadFrame&useDatabase=<%=useDatabase%>";
	});
	
	/**
	 * 点击文件列表后在右侧iframe中显示内容
	 */
	function showCellData(rowIndex,rowData){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/cellTable/showTable?method=dotUse&useDatabase=<%=useDatabase%>&fileID="+rowData.fileID_enc;
		document.getElementById("showDataFrame").src = url;
	}
	
	function search(value,name){
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var nodes = treeObj.getSelectedNodes();
		var treepInfoId = "";
		if(nodes && nodes.length>0){
			treepInfoId = nodes[0].id;
		}
		
		jQuery.post("<%=request.getContextPath()%>/rs/treepInfoRest/searchTreepInfo?systemName=<%=useDatabase%>",
			{keyWord:value,treepInfoId:treepInfoId},
			function(data){
				jQuery.fn.zTree.init(jQuery("#myTree"), setting, data);
			},"JSON"
		);
	}

	function reloadTree(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	}
</script>
</head>
<body class="easyui-layout">
	<div title="工程划分WBS" data-options="region:'west'" style="width:300px">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="width:295px;height:28px;border: 0px">
		 		<input id="searchId" class="easyui-searchbox" style="width:295px" data-options="searcher:search,prompt:'输入关键字查询',menu:'#mm'" />
		 	</div>
			<div data-options="region:'center'" style="width:297px;border: 0px">
				<img style="cursor: pointer;" title="点击重新加载" src="<%=request.getContextPath()%>/icons/icons/reload.png" onclick="javascript:reloadTree();">
				<ul id="myTree" class="ztree"></ul>
			</div>
			<div data-options="region:'south'" style="width:295px;height: 230px;border: 0px">
				<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools',border:false" style="width: 297px">
					<div title="表格" data-options="cache:false">
						<table id="sheetTable" class="easyui-datagrid" style="width:auto;height:195px" border="0" data-options="idField:'fileID',singleSelect:true,fitColumns:true">
							<thead>
								<tr>
									<th data-options="field:'ID',hidden:true">ID</th>
									<th data-options="field:'fileID',hidden:true">fileID</th>
									<th data-options="field:'name',width:295">名称</th>
								</tr>
							</thead>
						</table>
					</div>
					<div title="参考文件" data-options="cache:false" style="overflow: auto; padding: 0px;">
						<table id="fileTable" class="easyui-datagrid" style="width:auto;height:195px" border="0" data-options="idField:'fileID',singleSelect:true,fitColumns:true">
							<thead>
								<tr>
									<th data-options="field:'ID',width:20,hidden:true">ID</th>
									<th data-options="field:'fileID',width:20,hidden:true">fileID</th>
									<th data-options="field:'name',width:'295'">名称</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<iframe id="showDataFrame" style="border: 0;" width="100%"  height="100%" ></iframe>
	</div>
</body>
</html>