<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件管理</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/layer/skin/layer.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/themes/${theme}/styles.css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/icons.css">
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script> 
<script type="text/javascript" src="${contextPath}/scripts/glaf-core.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/glaf-base.js"></script>
<script type="text/javascript">


    var prevTreeNode;

    var setting = {
			async: {
				enable: true,
				url: getUrl,
				dataFilter: filter
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="${contextPath}/images/folder.png";
		}
		return childNodes;
	}

 
    function getUrl(treeId, treeNode) {
		if(treeNode != null){
		    var param = "parentId="+treeNode.id;
		    return "${contextPath}/rs/file/attachment/treeJson?"+param;
		}
		return "${contextPath}/rs/file/attachment/treeJson?type=${type}&parentId=${parentId}";
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		loadData('${contextPath}/mx/file/attachment/json?type=${type}&nodeId='+treeNode.id);
	}

	function loadData(url){
		  jQuery.get(url,{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  jQuery('#mydatagrid').datagrid('loadData', data);
		  },'json');
	  }

    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'${contextPath}/mx/file/attachment/json?nodeId=${nodeId}&type=${type}',
				remoteSort: false,
				singleSelect:true,
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:false},
					{title:'名称',field:'name', width:120},
					{title:'文件名',field:'filename', width:360,formatter:formatterUrl},
					{title:'链接地址',field:'url', width:280, formatter:formatterLink},
					{title:'是否有效',field:'locked', width:90, align:'center', formatter:formatterStatus},
					{title:'功能键', field:'functionKey', width:120, formatter:formatterKeys}
				]],
				rownumbers:false,
				pagination:true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100],
				pagePosition: 'both',
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

 
	function formatterUrl(val, row){
	  if(row.filename.endsWith(".jpg") || row.filename.endsWith(".jpeg") 
		   || row.filename.endsWith(".gif") || row.filename.endsWith(".png")){
          return "<img src='${contextPath}/mx/lob/lob/download?fileId="+row.id+"' border='0'>";
	  } else {
		  return row.originalFilename;
	  }
	}

	function formatterLink(val, row){
        return "/mx/lob/lob/download?fileId="+row.id;
	}
		 
	function formatterStatus(val, row){
       if(val == 0){
			return '<span style="color:green; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">否</span>';
	   }  
	}


	function addNew(){
		var nodeId = jQuery("#nodeId").val();
		if(nodeId == null || nodeId ==""){
			alert("请选择左边分类！");
			return;
		}
		var link = "${contextPath}/mx/file/attachment/edit?type=${type}&fromUrl=${fromUrl}&nodeId="+nodeId;
	    //art.dialog.open(link, { height: 420, width: 600, title: "添加记录", lock: true, scrollbars:"yes" }, false);
		//location.href=link;
		showEdit("");
	}

	function onRowClick(rowIndex, row){
	    //var link = '${contextPath}/mx/file/attachment/edit?type=${type}&fromUrl=${fromUrl}&id='+row.id;
	    //art.dialog.open(link, { height: 480, width: 600, title: "修改记录", lock: true, scrollbars:"yes" }, false);
		showEdit(row.id);
	}

    function formatterKeys(val, row){
		return "<a href='javascript:editRow2(\""+row.id+"\");'>修改</a>&nbsp;<a href='javascript:deleteRow2(\""+row.id+"\");'>删除</a>&nbsp;<a href='javascript:downloadRow2(\""+row.id+"\");'>下载</a>";
	}


	function editRow2(rowId){
	    var link = '${contextPath}/mx/file/attachment/edit?type=${type}&fromUrl=${fromUrl}&id='+rowId;
	    //art.dialog.open(link, { height: 420, width: 600, title: "修改记录", lock: true, scrollbars:"no" }, false);
		//location.href=link;
		showEdit(rowId);
	}

	function downloadRow2(rowId){
	    var link = '${contextPath}/mx/lob/lob/download?fileId='+rowId;
		window.open(link);
	}

	function deleteRow2(rowId){
		if(confirm("记录删除后不能恢复，确定删除吗？")){
		    jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/file/attachment/delete?id='+rowId,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   //jQuery('#mydatagrid').datagrid('reload');
					   var nodeId = jQuery("#nodeId").val();
		               loadData('${contextPath}/mx/file/attachment/json?type=${type}&nodeId='+nodeId);
				   }
			 });
		}
	}


	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','文件查询');
	    //jQuery('#searchForm').form('clear');
	}

	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  //location.href="${contextPath}/mx/file/attachment?method=edit&rowId="+selected.id;
		  var link = "${contextPath}/mx/file/attachment/edit?type=${type}&fromUrl=${fromUrl}&id="+selected.id;
		  //art.dialog.open(link, { height: 480, width: 600, title: "修改记录", lock: true, scrollbars:"yes" }, false);
		  //location.href=link;
		  showEdit(selected.id);
	    }
	}

	function functionSettings(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  
	    }
	}

	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="${contextPath}/mx/file/attachment/edit?type=${type}&fromUrl=${fromUrl}&id="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 ){
		  if(confirm("数据删除后不能恢复，确定删除吗？")){
		    var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/file/attachment/delete?ids='+rowIds,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   //jQuery('#mydatagrid').datagrid('reload');
					   var nodeId = jQuery("#nodeId").val();
		               loadData('${contextPath}/mx/file/attachment/json?type=${type}&nodeId='+nodeId);
				   }
			 });
		  }
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}

	function getSelected(){
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
		  alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
	    }
	}

	function getSelections(){
	    var ids = [];
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    for(var i=0;i<rows.length;i++){
		ids.push(rows[i].code);
	    }
	    alert(ids.join(':'));
	}

	function clearSelections(){
	    jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function searchData(){
		    var name = document.getElementById("name").value.trim();
			document.getElementById("nameLike").value = name;
			var params = jQuery("#iForm").formSerialize();
            jQuery.ajax({
                        type: "POST",
                        url: '${contextPath}/mx/file/attachment/json?type=${type}&nodeId=${nodeId}',
                        dataType:  'json',
						data: params,
                        error: function(data){
                                  alert('服务器处理错误！');
                        },
                        success: function(data){
                                  jQuery('#mydatagrid').datagrid('loadData', data);
                        }
                        });
	}

	function showEdit(id){
	  var nodeId = jQuery("#nodeId").val();
	  if(nodeId == null || nodeId ==""){
			alert("请选择左边分类！");
			return;
	  }
      var link='${contextPath}/mx/file/attachment/edit?type=${type}&nodeId='+nodeId+'&id='+id;
      jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑文件信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['650px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
    }

		 
</script>
</head>
<body style="margin:1px;">  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div class="easyui-layout" data-options="fit:true">   
   	<div data-options="region:'north',split:true,border:true" style="height:40px"> 
		<div class="toolbar-backgroud"  > 
			<img src="${contextPath}/images/window.png">
			&nbsp;<span class="x_content_title">文件列表</span>
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
			   onclick="javascript:addNew();">新增</a>  
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
			   onclick="javascript:editSelected();">修改</a>  
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
			   onclick="javascript:deleteSelections();">删除</a>  
			<input id="name" name="name" type="text" 
	               class="x-searchtext" size="50" maxlength="200"/>
	        <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	           onclick="javascript:searchData();">查找</a>
		</div> 
	</div> 
       
	<div data-options="region:'west',split:true" style="width:225px;">
		<ul id="myTree" class="ztree"></ul>  
    </div>  

	<div data-options="region:'center',border:true">
		<table id="mydatagrid"></table>
	</div>  

</div>
<form id="iForm" name="iForm" method="post">
<input type="hidden" id="nameLike" name="nameLike">
</form> 
</body>
</html>
