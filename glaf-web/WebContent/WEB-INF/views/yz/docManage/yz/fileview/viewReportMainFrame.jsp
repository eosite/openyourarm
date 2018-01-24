<%@page import="com.glaf.chinaiss.query.InterfaceQuery"%>
<%@page import="com.glaf.chinaiss.service.InterfaceService"%>
<%@page import="com.glaf.chinaiss.model.Interface"%>
<%@page import="com.glaf.chinaiss.model.TreepName"%>
<%@page import="com.glaf.chinaiss.query.TreepNameQuery"%>
<%@page import="com.glaf.chinaiss.service.TreepNameService"%>
<%@page import="com.glaf.chinaiss.model.ProjMuiProjList"%>
<%@page import="java.util.List"%>
<%@page import="com.glaf.core.context.ContextFactory"%>
<%@page import="com.glaf.chinaiss.service.ProjMuiProjListService"%>
<%@page import="com.glaf.core.config.Environment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String useDatabase = request.getParameter("useDatabase")==null?"default":request.getParameter("useDatabase");
	
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    Environment.setCurrentSystemName(useDatabase);
    TreepNameService treepNameServcie = (TreepNameService)ContextFactory.getBean("com.glaf.chinaiss.service.treepNameService");
    TreepNameQuery treepNameQuery = new TreepNameQuery();
    List<TreepName> treepNameList = treepNameServcie.list(treepNameQuery);
    TreepName treepName = null;
    if(treepNameList.size()>0){
    	treepName = treepNameList.get(0);
    }
    
    InterfaceService interfaceService = (InterfaceService)ContextFactory.getBean("com.glaf.chinaiss.service.interfaceService");
    InterfaceQuery interfaceQuery = new InterfaceQuery();
    interfaceQuery.setFrmType("proj_Inspection");
    interfaceQuery.setIsSystem("1");
    interfaceQuery.setIsListShow("1");
    interfaceQuery.setOrderBy("listno");
    List<Interface> interfaceList = interfaceService.list(interfaceQuery);
    String ztreeMaxCount=com.glaf.core.config.SystemConfig.getString("treeNodeLoadQty", "500");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件报审</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	
	var setting = {
		async: {
			enable: true,
			url: "<%=request.getContextPath()%>/rs/treepInfoRest/getTreepinfoByParentId?systemName=<%=useDatabase%>",
			autoParam:["indexId"],
			otherParam:["pageName" , "report"],
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
		var showChildFile = "0";
		if(document.getElementById("showChildFile").checked){
			showChildFile = "1";
		}
		
		jQuery("#myTable").datagrid({
			queryParams:{"useDatabase":<%=useDatabase%>,"showChildFile":showChildFile,"treepInfoId":treeNode.id,"indexId":treeNode.indexId}
		});
	}
	
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});
	

	jQuery(function(){
		//加载表格
		jQuery("#myTable").datagrid({
			url:"<%=request.getContextPath()%>/rs/viewReportRest/fileTable?useDatabase=<%=useDatabase%>",
			idField:'id',
			singleSelect:true,
			//fitColumns:true,
			rownumbers:true,
			onSelect:function(rowIndex,rowData){
				if(rowData){
					jQuery("#tname").val(rowData.tname);
					jQuery("#tagnum").val(rowData.tagnum);
					jQuery("#ctime").val(rowData.ctime);
					jQuery("#duty").val(rowData.duty);
					jQuery("#page").val(rowData.page);
					jQuery("#thematic").val(rowData.thematic);
					jQuery("#annotations").val(rowData.annotations);
				}
			},
			onDblClickRow:function(rowIndex,rowData){
				var url="<%=request.getContextPath()%>/mx/docManage/yz/fileview/viewReport/reportTableList?useDatabase=<%=useDatabase%>&inspectionId="+rowData.id_enc;
		    	var height = screen.availHeight-100;
		    	var width = screen.availWidth-10;
			    var title = "查看表格";
		    	art.dialog.open(url, { height: height, width: width, title: title,left:0,top:0, scrollbars:"no" , lock: true });
			},
			onLoadSuccess:function(data){
				if(data.rows!=0){
					jQuery("#myTable").datagrid("selectRow",0);
					jQuery('#pagination').pagination({total:data.total});
					jQuery('#pagination').show();
				}else{
					jQuery('#pagination').hide();
					jQuery("#tname").val("");
					jQuery("#tagnum").val("");
					jQuery("#ctime").val("");
					jQuery("#duty").val("");
					jQuery("#page").val("");
					jQuery("#thematic").val("");
					jQuery("#annotations").val("");
				}
			}
		});
		
		jQuery('#pagination').pagination({
			pageSize:20,
			showRefresh:false,
			showPageList:false,
			beforePageText:'第',
			afterPageText:'页，共{pages}页',
			displayMsg:'显示第 {from} 至 {to} 条记录，共 {total} 条记录',
			onSelectPage:function(pageNumber, pageSize){
				jQuery(this).pagination('loading');
				gotoPage(pageNumber,pageSize);
				jQuery(this).pagination('loaded');
			}
		});
	});
	
	/*
	 *选择页数
	 */
	function gotoPage(pageNumber,pageSize){
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var nodes = treeObj.getSelectedNodes();
		var treepInfoId = "";
		var indexId = -1;
		if(nodes && nodes.length>0){
			treepInfoId = nodes[0].id;
			indexId = nodes[0].indexId;
		}
		
		var showChildFile = "0";
		if(document.getElementById("showChildFile").checked){
			showChildFile = "1";
		}
		
		jQuery("#myTable").datagrid({
			queryParams:{"useDatabase":<%=useDatabase%>,"showChildFile":showChildFile,"treepInfoId":treepInfoId,"indexId":indexId,"pageNumber":pageNumber,"pageSize":pageSize}
		});
	}
	
	
	/**
	 *显示下级文件
	 */
	function showChildFile(){
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var nodes = treeObj.getSelectedNodes();
		var treepInfoId = "";
		var indexId = -1;
		if(nodes && nodes.length>0){
			treepInfoId = nodes[0].id;
			indexId = nodes[0].indexId;
		}
		
		var showChildFile = "0";
		if(document.getElementById("showChildFile").checked){
			showChildFile = "1";
		}
		
		jQuery("#myTable").datagrid({
			queryParams:{"useDatabase":<%=useDatabase%>,"showChildFile":showChildFile,"treepInfoId":treepInfoId,"indexId":indexId}
		});
	}
	
	/**
	 *报审状态格式化
	 */
	 function intCheckFormatter(value){
		switch(value){
			case 0:
				return "未报审";
			case 1:
				return "报审中";
			case 2:
				return "已审批";
			default:
				return "";
		}
		
	}
	
	 function searchFrame(){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/viewReport/viewReportSearch?useDatabase=<%=useDatabase%>";
		art.dialog.open(url, { height: 550, width: 900, title: "检索", lock: false, scrollbars:"no" }, false);
	 }
</script>
</head>
<body  class="easyui-layout">
	<div title="工程划分WBS"  data-options="region:'west'" style="width:230px" style="padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="width:215px;height:28px;border: 0px" >
				<label>项目</label><input type="text" class="easyui-validatebox"  style="width: 185px;background-color: #EEE" readonly="readonly" value="<%=treepName==null?"":treepName.getIndexName()%>"/>
			</div>
			<div data-options="region:'center'" style="width:215px;border: 0px;" >
				<ul id="myTree" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="height:440px;border: 0px;" >
				<div class="toolbar-backgroud"  style="height: 30px">
				<table border="0" style="width: 100%;height: 30px" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<img src="<%=request.getContextPath() %>/images/window.png">&nbsp;<span class="x_content_title">文件列表</span>
							<a href="javascript:searchFrame();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">检索</a>
							<input id="showChildFile" name="showChildFile" type="checkbox"  value="0" style="margin-left: 10px" onclick="javascript:showChildFile();">
							<label for="showChildFile">显示下级文件</label>
						</td>
						<td align="right"><div id="pagination" style="width: 550px;display: none"></div></td>
					</tr>
				</table>
				</div>
				<table id="myTable" class="easyui-datagrid" style="width:1150;height:400px;" border="0">
							<thead>
								<tr>
									<th data-options="field:'id_enc',hidden:true">id</th>
									<th data-options="field:'indexId',hidden:true">indexId</th>
									<th data-options="field:'intCheck',width:70,formatter:intCheckFormatter">报审状态</th>
									<%
										for(Interface interfaceModel:interfaceList){
											String width = "100";
											String align = "center";
											if(interfaceModel.getDname().equals("tname")){//文件题名
												width = "530";
												align = "left";
											}else if(interfaceModel.getDname().equals("page")){//页数
												width = "50";
											}
									%>
										<th data-options="field:'<%=interfaceModel.getDname() %>',width:<%=width%>,align:'<%=align%>'"><%=interfaceModel.getFname() %></th>
									<%} %>
								</tr>
							</thead>
						</table>
			</div>
			<div title="文件信息" data-options="region:'center'" style="border: 0px">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'" style="border: 0px;" >
						<div id="allTabs" class="easyui-tabs" data-options="fit:true">
						    <div title="著录项" data-options="fit:true" style="overflow:auto;padding:3px;">  
						        <table cellpadding="0" cellspacing="10" border="0" width="100%">
						        	<%
						        	int i=0;
						        	for(Interface interfaceModel:interfaceList){
						        		if(i%2==0){
						        	%>
						        	<%=i==0?"":"</tr>" %><tr>
						        	<%}%>
						        		<td align="right" style="width: 60px"><label><%=interfaceModel.getFname() %></label></td>
						        		<td style="width: 400px"><input id="<%=interfaceModel.getDname() %>"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	<%
						        		i++;
						        	}
						        	out.print("</tr>");
						        	%>
						        </table>
						    </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>