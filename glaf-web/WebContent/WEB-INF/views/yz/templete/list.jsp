<%@page import="com.glaf.base.modules.sys.model.CellTreedot"%>
<%@page import="com.glaf.chinaiss.model.Interface"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    List<Interface> interfaceList = (List<Interface>)request.getAttribute("interfaceList");
    CellTreedot treedot = (CellTreedot)request.getAttribute("treedot");
    String systemName = (String)request.getAttribute("systemName");
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
			url: "<%=request.getContextPath()%>/rs/treepInfoRest/getTreepinfoByTreedotIndexId?systemName=<%=systemName%>&treedot_index_id=<%=treedot.getIndexId()%>",
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
		var p = jQuery("body").layout("panel","center");
		var panelHeight = p.panel('panel').outerHeight();
		jQuery("#myTable").datagrid({
			height:panelHeight-10,
			url:"<%=request.getContextPath()%>/rs/isdp/template/templateRest/getDataList?systemName=<%=systemName%>&treedot_index_id=<%=treedot.getIndexId()%>&treepinfo_index_id="+treeNode.indexId+"&pageNumber="+pageNumber+"&pageSize="+pageSize,
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
	
	/*
	 *选择页数
	 */
	function gotoPage(pageNumber,pageSize){
		var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
    	var nodes = treeObj.getSelectedNodes();
    	if(nodes.length==0){
    		alert("请先选择结构树");
    		return;
    	}
		var treepinfo_index_id = nodes[0].indexId;
		jQuery("#myTable").datagrid({
			url:"<%=request.getContextPath()%>/rs/isdp/template/templateRest/getDataList?systemName=<%=systemName%>&treedot_index_id=<%=treedot.getIndexId()%>&treepinfo_index_id="+treepinfo_index_id+"&pageNumber="+pageNumber+"&pageSize="+pageSize,
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
		<div id="pagination"></div>
   		<table id="myTable" class="easyui-datagrid" border="0" data-options="striped:true,singleSelect:true,rownumbers:true,toolbar:toolbar">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:false">id</th>
					<th data-options="field:'index_id',hidden:true">index_id</th>
					<th data-options="field:'top_id',hidden:true">top_id</th>
					<%
						for(int i=0;i<interfaceList.size();i++){
							Interface interfaceModel = interfaceList.get(i);
							String width = "100";
							String align = "left";
					%>
						<th data-options="field:'<%=interfaceModel.getDname() %>',width:<%=width%>,align:'<%=align%>'"><%=interfaceModel.getFname() %></th>
					<%} %>
				</tr>
			</thead>
		</table>
		
		<script type="text/javascript">
		var toolbar = [{text:'列表计划'},{
            text:'打印',
            iconCls:'icon-print',
            handler:function(){
            	var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
            	var nodes = treeObj.getSelectedNodes();
            	if(nodes.length==0){
            		alert("请先选择结构树");
            		return;
            	}
            	var sFeatures = "width="+window.screen.width+",height="+window.screen.height+",top=0,left=0,toolbar=no,menubar=no,status=no,location=no,help=no,center=yes,resizable=no,scroll=no,depended=yes,alwaysRaised=yes";
            	window.open("<%=request.getContextPath()%>/mx/isdp/templete?method=printPreview&systemName=<%=systemName%>&treedot_index_id=<%=treedot.getIndexId()%>&treepinfo_index_id="+nodes[0].indexId,"打印预览",sFeatures);
            }
        },'-',{
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
</body>
</html>
