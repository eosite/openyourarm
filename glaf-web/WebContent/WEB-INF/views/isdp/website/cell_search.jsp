<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    request.setAttribute("theme", "default");
	//String server = "http://"+request.getLocalAddr()+":"+request.getLocalPort();
	//server = server + request.getContextPath();
	
	//request.setAttribute("server", server);
	
	request.setAttribute("server", com.glaf.core.util.RequestUtils.getServiceUrl(request));
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看表格</title>
<link rel="stylesheet" type="text/css" href="${server}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${server}/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="${server}/css/icons.css">
<script type="text/javascript" src="${server}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${server}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${server}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${server}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${server }/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="${server }/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
var setting = {
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
	showTreeData(treeNode,treeNode.indexId);
}

function zTreeOnClick(event, treeId, treeNode){
	var showSubTable = $("#showSubTable").is(":checked");
	var options = $("#cellTableList").datagrid("options");
	showCellData(treeNode.indexId,showSubTable,options.pageNumber,options.pageSize);
}

function showCellData(indexId,showSubTable,pageNumber,pageSize){
	$.ajax({
	   type: "POST",
	   url: "${server }/website/isdp/websiteWBS/cellTableList?systemName=${systemName}&databaseId=${databaseId}",
	   data: {indexId:indexId,showSubTable:showSubTable,page:pageNumber,rows:pageSize},
	   dataType: "jsonp",
	   success: function(data){
		   $("#cellTableList").datagrid({
			   data:data,
			   pageNumber:pageNumber,
			   pageSize:pageSize
		   });
	   }
	});
}

var openLevel = '${param.level}';
openLevel = openLevel==''?0:(openLevel>3?3:openLevel);

function showTreeData(parentNode,indexId){
	$.ajax({
	   type: "POST",
	   url: "${server }/website/isdp/websiteWBS/getTreepInfoJSON?systemName=${systemName}&databaseId=${databaseId}",
	   data: {indexId:indexId},
	   dataType: "jsonp",
	   success: function(data){
		   var treeObj = $.fn.zTree.getZTreeObj("myTree");
		   treeObj.addNodes(parentNode, data);
		   for(var i=0;i<data.length;i++){
			   var node = treeObj.getNodeByParam('indexId',data[i].indexId,parentNode);
			   if(node.level<openLevel){
				   showTreeData(node,node.indexId);
			   }
		   }
	   }
	});
}

$(document).ready(function(){
	$.fn.zTree.init($("#myTree"), setting);

/**
	$('#cc').combobox({    
	    url:"<%=request.getContextPath()%>/website/isdp/websiteWBS/getDatabases",    
	    valueField:'id',    
	    textField:'title',
	     onSelect: function(rec){    
            var treeObj = $.fn.zTree.getZTreeObj("myTree");
			treeObj.destroy();
			 
			 $.fn.zTree.init($("#myTree"), setting);
			 $.ajax({
			   type: "POST",
			   url: "${server }/website/isdp/websiteWBS/getTreepInfoJSON?systemName=${systemName}&databaseId=" + rec.id,
			   data: {indexId:-1},
			   dataType: "jsonp",
			   success: function(data){
				   var treeObj = $.fn.zTree.getZTreeObj("myTree");
				   treeObj.addNodes(null, data);
			   }
			});
        } 
        ,onLoadSuccess:function(){
        	var id = $('#cc').combobox('getValue');
	    	$.ajax({
			   type: "POST",
			   url: "${server }/website/isdp/websiteWBS/getTreepInfoJSON?systemName=${systemName}&databaseId=" + id,
			   data: {indexId:-1},
			   dataType: "jsonp",
			   success: function(data){
				   var treeObj = $.fn.zTree.getZTreeObj("myTree");
				   treeObj.addNodes(null, data);
			   }
			});
		}  
	}); 
	*/

	//初始化取得根节点数据
	showTreeData(null,-1);
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
			{field:'fileID',title:'fileID',checkbox:true},
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
		pageList:[20,50,100],
		onLoadSuccess:function(data){
			var pager = $("#cellTableList").datagrid("getPager");
			$(pager).pagination({
				onSelectPage: function(pageNumber, pageSize){
					var treeObj = $.fn.zTree.getZTreeObj("myTree");
					var nodes = treeObj.getSelectedNodes();
					if(nodes.length==0){
						return;
					}
					
					var showSubTable = $("#showSubTable").is(":checked");
					showCellData(nodes[0].indexId,showSubTable,pageNumber,pageSize);
				},
				onChangePageSize: function(pageSize){
					var treeObj = $.fn.zTree.getZTreeObj("myTree");
					var nodes = treeObj.getSelectedNodes();
					if(nodes.length==0){
						return;
					}
					
					var showSubTable = $("#showSubTable").is(":checked");
					showCellData(nodes[0].indexId,showSubTable,1,pageSize);
				}
			});
		}
	});
});

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
	
	var options = $("#cellTableList").datagrid("options");
	showCellData(nodes[0].indexId,obj.checked,options.pageNumber,options.pageSize);
}

function downloadCell(downloadURL){
	window.open(downloadURL);
}

function doSubmit(){
	var datas = $("#cellTableList").datagrid("getSelections");
	var rows = [];
	for(var i=0;i<datas.length;i++){
		var row = {};
		row.fileName=datas[i].name;
		row.url=datas[i].readURL;
		row.systemName="${systemName}";
		rows.push(row);
	}
	
	 var returnValue = {  //回填参数
         rows:rows
     }

		// parent.doSubmit(returnValue);
/*
     try {
         var result = {
             UIDialogTag: getUrlParam('UIDialogTag'),
             returnValue: returnValue
         };
         //关闭前调用父窗口的postMessage方法
         if (/(.*?)(chrome\/)(\d+)(.*?)/i.test(window.navigator.userAgent) && parseInt(RegExp.$3) >= 37) {
			 alert("1:top.opener:"+JSON.stringify(returnValue));
             top.opener.postMessage(result, '*');
             top.close();
         } else {
			 alert("2:top.returnValue:"+JSON.stringify(returnValue));
             top.returnValue = returnValue;
			 //window.opener.postMessage(result, '*');
             top.close(true);
         }

     } catch (e) {}
	*/
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}
</script>
</head>
<body class="easyui-layout">
	<div title="工程划分WBS" data-options="region:'west'" style="width:250px">
	<p>
		<!--  
		<div style="height: 30px">数据源选择：<input id="cc" name="dept" value=""></div>
	-->
		<div>
			<ul id="myTree" class="ztree"></ul>
		</div>
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
		<a href="<%=request.getContextPath()%>/cellPlugin/CellWeb5_plugin_setup.zip" id="downCellPlugin" class="easyui-linkbutton">插件下载</a>
		<a href="#" class="easyui-linkbutton" onclick="javascript:doSubmit()">确定选择</a>
	</div>
</body>
</html>