<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    request.setAttribute("theme", "default");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看表格</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/icons.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
var setting = {
		async: {
			enable: true,
			url: "${pageContext.request.contextPath}/rs/isdp/treepInfo/getTreepInfoJSON?databaseCode=${systemName}",
			autoParam:["indexId"],
			type:"post"
		},
		callback:{
			onExpand:zTreeOnExpand,
			onClick:zTreeOnClick
		},
		view: {
			showIcon: true,
			showLine: true
		},
		simpleData: {
			enable: true,
			idKey: "indexId",
			pIdKey: "parentId"
		}
	};

	function zTreeOnExpand(event, treeId, treeNode){
		
	}

	function zTreeOnClick(event, treeId, treeNode){
		var showSubTable = $("#showSubTable").is(":checked");
		$("#cellTableList").datagrid({
			url:'${pageContext.request.contextPath}/rs/isdp/wbs/cellTableList?systemName=${systemName}&indexId='+treeNode.indexId+'&showSubTable='+showSubTable
		});
	}

	$(document).ready(function(){
		$.fn.zTree.init($("#myTree"), setting);
	});

	$(function(){
		
		$("#cellTableList").datagrid({
			height:document.body.clientHeight-5,
			toolbar:'#tb',
			idField:'fileID',
			singleSelect:false,
			fitColumns:true,
			striped:true,
			rownumbers:true,
			autoRowHeight:true,
			columns:[[
				{field:'ID',title:'ID',hidden:true},
				{field:'fileID',title:'fileID',hidden:true},
				{field:'name',title:'名称',width:'83%'},
				{field:'opt',title:'操作',align:'center',width:'10%',formatter:function(value,row,index){
					var viewURL = '<a href="#" onclick="readCell(\''+row.readURL+'\')">查看</a>';
					var downloadURL = '<a href="#" onclick="downloadCell(\''+row.downloadURL+'\')">下载</a>';
					return viewURL + '&nbsp;&nbsp;' + downloadURL;
				}}
			]],
			pagination:true,
			pagePosition:'bottom',
			pageSize:20,
			pageList:[20,50,100]
		});
	});

	//读取华表数据
	function readCell(readURL){
		var height = screen.availHeight-100;
		var width = screen.availWidth-10;
	    var title = "查看表格";
		window.open(readURL);
	}

	function checkedSelect(obj){
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length==0){
			return;
		}
		$("#cellTableList").datagrid({
			url:'$${pageContext.request.contextPath}/rs/isdp/wbs/cellTableList?systemName=${systemName}&indexId='+nodes[0].indexId+'&showSubTable='+obj.checked
		});
	}

	function downloadCell(downloadURL){
		window.open(downloadURL);
	}
</script>
</head>
<body class="easyui-layout">
	<div title="工程划分WBS" data-options="region:'west'" style="width:250px">
		<input id="project" name="project" />  
		<ul id="myTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center'">
		<div data-options="cache:false">
			<table id="cellTableList"></table>
		</div>
	</div>
	<div id="tb" style="height: 25px">
		<span>表格列表</span>
		<input type="checkbox" id="showSubTable" name="showSubTable" onclick="checkedSelect(this)" />
		<label for="showSubTable">显示下级表格</label>
	</div>
</body>
</html>