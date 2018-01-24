<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page import="com.glaf.core.config.SystemConfig"%>
<%@page import="com.glaf.chinaiss.model.Treepinfo"%>
<%@page import="com.glaf.base.modules.sys.model.CellTreedot"%>
<%@page import="com.glaf.chinaiss.model.Interface"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    CellTreedot treedot = (CellTreedot)request.getAttribute("treedot");
    String systemName = (String)request.getAttribute("systemName");
    Treepinfo root = (Treepinfo)request.getAttribute("root");
    
    String datasourceJson = (String)request.getAttribute("datasourceJson");
    String fileId = (String)request.getAttribute("fileId");
    String reportURL = SystemConfig.getString("report_service_url")+"&datasourceJson="+datasourceJson+"&fileId="+fileId+"&token="+SystemConfig.getToken();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="renderer" content="ie-comp">
<title><%=treedot.getIndexName() %></title>
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
	var setting = {
		async: {
			enable: true,
			url: "<%=request.getContextPath()%>/rs/treepInfoRest/getTreepinfoByParentId?systemName=<%=systemName%>",
			autoParam:["indexId"],
			type:"post"
		},
		callback:{
			onExpand:zTreeOnExpand,
			onClick:zTreeOnClick
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
	
	
	var pageNumber = 1;
	var pageSize = 50;
	var total = 1;
	var totalPage = 1;
	function zTreeOnClick(event, treeId, treeNode){
		pageNumber = 1;
		jQuery("#myTable").datagrid({
			url:"<%=request.getContextPath()%>/rs/isdp/template/templateRest/getCellDataList?systemName=<%=systemName%>&treedot_index_id=<%=treedot.getIndexId()%>&treepinfo_index_id="+treeNode.indexId+"&pageNumber="+pageNumber+"&pageSize="+pageSize,
			onLoadSuccess:function(data){
				total = data.total==0?1:data.total;
				totalPage = parseInt((total+pageSize-1)/pageSize);
				setToolbarPageText(pageNumber,totalPage);
			}
		});
	}
	
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});
	
	function selectRow(rowIndex, rowData){
		var treepinfo_index_id="";
		var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
    	var nodes = treeObj.getSelectedNodes();
    	if(nodes.length==0){
    		treepinfo_index_id = <%=root.getIndexId()%>;
    	}else{
    		treepinfo_index_id = nodes[0].indexId;
    	}
    	
		var tab = jQuery("#tabs").tabs("options");
		var obj = document.getElementById("iframepage");
		obj.height = tab.height;
		obj.src = "<%=reportURL%>&filedot_fileid="+rowData.fileDotFileId_enc;
	}
	
	/*
	 *选择页数
	 */
	function gotoPage(pageNumber,pageSize){
		var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
    	var nodes = treeObj.getSelectedNodes();
		var treepinfo_index_id = nodes[0]?nodes[0].indexId:<%=root.getIndexId()%>;
		jQuery("#myTable").datagrid({
			url:"<%=request.getContextPath()%>/rs/isdp/template/templateRest/getCellDataList?systemName=<%=systemName%>&treedot_index_id=<%=treedot.getIndexId()%>&treepinfo_index_id="+treepinfo_index_id+"&pageNumber="+pageNumber+"&pageSize="+pageSize,
			onLoadSuccess:function(data){
				total = data.total==0?1:data.total;
				totalPage = parseInt((total+pageSize-1)/pageSize);
				setToolbarPageText(pageNumber,totalPage);
			}
		});
	}
	
	function setToolbarPageText(pageNumber,totalPage){
		var tb = jQuery('#myTable').datagrid("options").toolbar;
		for(var i=0;i<tb.length;i++){
			if(tb[i].id && tb[i].id=="page_info"){
				tb[i].text = pageNumber+"/"+totalPage;
			}
		}
	}
</script>
</head>

<body class="easyui-layout">
	<div data-options="region:'west',split:true" style="width:250px;">
		<ul id="myTree" class="ztree"></ul>
	</div>
   	<div data-options="region:'center'" style="padding:1px;background:#eee;">
   		<div class="easyui-layout" data-options="fit:true">
   			<div data-options="region:'north',split:true" style="height:310px">
   				<table id="myTable" class="easyui-datagrid" border="0" 
   					data-options="height:300,striped:true,singleSelect:true,rownumbers:true,toolbar:toolbar,fitColumns:true,
   					url:'<%=request.getContextPath()%>/rs/isdp/template/templateRest/getCellDataList?systemName=<%=systemName%>&treedot_index_id=<%=treedot.getIndexId()%>&treepinfo_index_id=<%=root.getIndexId() %>',
   					onSelect:selectRow">
					<thead>
						<tr>
							<th data-options="field:'id_enc',hidden:true">id</th>
							<th data-options="field:'indexId',hidden:true">index_id</th>
							<th data-options="field:'fileDotFileId',hidden:true">fileDotFileId</th>
							<th data-options="field:'chkNum',align:'center',width:1">编号</th>
							<th data-options="field:'name',align:'left',width:10">名称</th>
						</tr>
					</thead>
				</table>
				<script type="text/javascript">
				var toolbar = [{text:'报表列表'},'-',{
		        	iconCls:'pagination-first',
		        	handler:function(){
		        		pageNumber = 1;
		        		gotoPage(pageNumber,pageSize);
		        		setToolbarPageText(pageNumber,totalPage);
		        	}
		        },{
		        	iconCls:'pagination-prev',
		        	handler:function(){
		        		pageNumber==1?pageNumber:pageNumber--;
		        		gotoPage(pageNumber,pageSize);
		        		setToolbarPageText(pageNumber,totalPage);
		        	}
		        },{
		        	id:'page_info',
		        	text:'1/1'
		        },{
		        	iconCls:'pagination-next',
		        	handler:function(){
		        		pageNumber==totalPage?totalPage:pageNumber++;
		        		gotoPage(pageNumber,pageSize);
		        		setToolbarPageText(pageNumber,totalPage);
		        	}
		        },{
		        	iconCls:'pagination-last',
		        	handler:function(){
		        		gotoPage(totalPage,pageSize);
		        		setToolbarPageText(totalPage,totalPage);
		        	}
		        }];
				</script>
   			</div>
		   	<div data-options="region:'center'" style="padding:1px;background:#eee;">
		   		<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
		   			<div title="报表内容" style="padding:0px;overflow:hidden">
		   				<iframe id="iframepage" name="iframepage" style="border:0px" height="400" width="100%" scrolling="auto"></iframe>
		   			</div>
		   		</div>
		   	</div>
   		</div>
   		
   	</div>
</body>
</html>
