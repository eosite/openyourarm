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
<title>部门用户</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">
	$(function(){
		$('#myTable').datagrid({
		    url:'<%=request.getContextPath()%>/rs/filedot/fileDotRest/datagrid',
		    rownumbers:true,
		    fitColumns:true,
		    singleSelect:true,
		    columns:[[
		        {field:'fileID',title:'模板ID',width:150},
		        {field:'dotName',title:'模板名称',width:400}
		    ]],
		    onLoadSuccess:function(data){
		    	$('#pagination').pagination({total:data.total});
		    }
		});
		
		$('#pagination').pagination({
			pageSize:50,
			showRefresh:false,
			showPageList:false,
			beforePageText:'第',
			afterPageText:'页，共{pages}页',
			displayMsg:'显示第 {from} 至 {to} 条记录，共 {total} 条记录',
			onSelectPage:function(pageNumber, pageSize){
				$(this).pagination('loading');
				gotoPage(pageNumber,pageSize);
				$(this).pagination('loaded');
			}
		});
	});
	
	/*
	 *选择页数
	 */
	function gotoPage(pageNumber,pageSize){
		$("#myTable").datagrid({
			queryParams:{"pageNumber":pageNumber,"pageSize":pageSize}
		});
	}

	function chooseMyFormData(){
		var row = $("#myTable").datagrid("getSelected");
		if(!row){
			alert("请选择模板");
			return;
		}
		
		
		var parent_window = getOpener();
	    var x_elementId = parent_window.document.getElementById("${elementId}");
        var x_element_name = parent_window.document.getElementById("${elementName}");
		x_elementId.value=row.fileID;
		x_element_name.value=row.dotName;
		window.close(); 
	}

	function search(){
		var dotName = document.getElementById("dotName").value;
		$("#myTable").datagrid({
			queryParams:{"dotName":dotName}
		});
	}
</script>
</head>

<body>

<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
	<span class="x_content_title">关联模板</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true" 
	   onclick="javascript:chooseMyFormData();" >确定</a> 
	   
	   <input  class="easyui-textbox" type="text" name="dotName" id="dotName" />
	   <a href="javascript:search();" class="easyui-linkbutton" data-options="plain:true">查询</a>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
	<table id="myTable"></table>
  </form>
</div>
	<div data-options="region:'south',border:true" style="height:40px">
	<div id="pagination" style="width: 550px;"></div>
	</div>
</div>
<script type="text/javascript">
  	
</script>

</body>
</html>