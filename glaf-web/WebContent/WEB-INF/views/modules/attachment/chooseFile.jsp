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
	int index = 0;
 
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件列表</title>
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
<style>
.icon-upload{
	background:url('${contextPath}/images/upload.gif') no-repeat ;
}
</style>
<script type="text/javascript">

    var contextPath="${contextPath}";

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
		jQuery("#path").val(treeNode.path);
		jQuery("#nodeId").val(treeNode.id);
		loadMxData('${contextPath}/mx/file/attachment/json?type=${type}&nodeId='+treeNode.id);
	}

	function loadMxData(url){
		  jQuery.get(url+'&randnum='+Math.floor(Math.random()*1000000),{qq:'xx'},function(data){
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
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:false},
					{title:'名称',field:'name',width:220,sortable:false},
					{title:'文件名',field:'filename', align:'center', valign:'middle', width:380,sortable:false, formatter:formatterUrl},
					{title:'链接地址',field:'url', width:280, formatter:formatterLink}
				]],
				rownumbers:false,
				pagination:true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pagePosition: 'both',
				pageList: [10,15,20,25,30,40,50,100]
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

 
	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}

	function selectedFile(){
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
		    //alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
			var parent_window = getOpener();
			var x_elementId = parent_window.document.getElementById("${elementId}");
            var x_element_name = parent_window.document.getElementById("${elementName}");
			x_elementId.value=selected.path;
			x_element_name.value=selected.path;
			window.close();
	    }
	}

	function uploadFile(){
        var nodeId = jQuery("#nodeId").val();
		if(nodeId == null || nodeId ==""){
			alert("请选择左边分类！");
			return;
		}
		var link = "${contextPath}/mx/file/attachment/edit?type=${type}&nodeId="+nodeId;
		window.location.href=link;
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
 
</script>
</head>
<body style="margin:1px;">  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div style="margin:0;"></div>  
<div class="easyui-tabs" style="width:100%;height:580px;">
	<div title="我的素材库" data-options="closable:false" style="padding:1px">
		<div class="easyui-layout" data-options="fit:true"> 
		    <div data-options="region:'north',split:true,border:true" style="height:40px"> 
			<div class="toolbar-backgroud"  > 
			    <img src="${contextPath}/images/window.png">
				&nbsp;<span class="x_content_title">文件列表</span>
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-ok'"
				   onclick="javascript:selectedFile();">选择</a> 
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-reload'"
				   onclick="javascript:reloadGrid();">重载</a> 
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-upload'"
				   onclick="javascript:uploadFile();">上传</a> 
				<input id="name" name="name" type="text" 
	               class="x-searchtext" size="50" maxlength="200"/>
	            <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	               onclick="javascript:searchData();">查找</a>
			</div> 
		</div> 
		   
		<div data-options="region:'west',split:true" style="width:195px;">
			<ul id="myTree" class="ztree"></ul>  
		</div>  

		<div data-options="region:'center',border:true">
			<table id="mydatagrid"></table>
		</div>  
			 
		</div>
    </div>

</div>
<form id="iForm" name="iForm" method="post">
<input type="hidden" id="nameLike" name="nameLike">
</form> 
</body>
</html>
