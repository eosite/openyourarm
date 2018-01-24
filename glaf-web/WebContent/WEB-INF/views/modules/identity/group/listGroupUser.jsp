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
<title>群组列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">
var contextPath="<%=request.getContextPath()%>";
   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				toolbar:'#tb',
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/mx/identity/group/listGroupUserJson?groupId=${groupId}',
				remoteSort: false,
				//singleSelect:true,
				idField:'userId',
				nowrap:true,
				columns:[[
					{title:'', field:'xstartIndex',checkbox:true, width:40,halign:'center'},
	                {title:'序号',field:'startIndex',width:40,sortable:true,halign:'center',align:'center'},
					{title:'群组名称',field:'name', width:100,sortable:true,halign:'center'},
					{title:'帐号',field:'userId', width:100,sortable:true,align:'center'},
					{title:'用户名称',field:'uname', width:120,sortable:true,halign:'center'},
					{title:'部门',field:'dname', width:120,sortable:true,halign:'center',align:'left'}
				]],
				rownumbers:false,
				pagination:false,
				pageSize:15,
				pageList: [10,15,20,25,50,100]
			});
	});

	function addNew(){
		var url="<%=request.getContextPath()%>/mx/identity/group/prepareAdd?type=${type}";
	    var width=450;
	    var height=280;
	    var scroll="no";
	    //openWindow(url, width, height, scroll);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加群组用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}


	function editData(){
		jQuery("#id").val('');
		jQuery("#name").val('');
		//jQuery("#code").val('');
		jQuery("#desc").val('');
		jQuery("#groupId").val('');
        jQuery('#edit_dlg').dialog('open').dialog('setTitle','群组编辑');
	}


	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	
	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].userid);
		}
		if(ids.length<=0){
			jQuery.messager.alert('Info', '请选择至少一条记录。', 'info');
			return;
		}
		if(confirm("数据删除后不能恢复，确定删除吗？")){
		    var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/identity/group/batchDeleteGroupUser?groupId=${groupId}&userIds='+rowIds,
				   dataType:  'json',
				   error: function(data){
					   if(data != null && data.message != null){
						   //alert(data.message);
						   jQuery.messager.alert('Info', data.message, 'info');
					   } else{
					       //alert('服务器处理错误！');
						   jQuery.messager.alert('Info', '服务器处理错误！', 'info');
					   }
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   //alert(data.message);
						   jQuery.messager.alert('Info', data.message, 'info');
					   } else {
						   //alert('操作成功完成！');
						   jQuery.messager.alert('Info', '操作成功完成！', 'info');
					   }
					   clearSelections();
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
		} else {
			//alert("请选择至少一条记录。");
			//jQuery.messager.alert('Info', '请选择至少一条记录。', 'info');
		}
	}

	function getSelected(){
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected){
			//alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
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
		var params = jQuery("#searchForm").formSerialize();
		var queryParams = jQuery('#mydatagrid').datagrid('options').queryParams;
		jQuery('#mydatagrid').datagrid('reload');	
		jQuery('#dlg').dialog('close');
	}

	function searchFormData(){
	    clearSelections();
	    jQuery('#mydatagrid').datagrid('load', getObjArray(jQuery("#searchForm").serializeArray()));
	}
	
	function addUser(){
		  var link = "<%=request.getContextPath()%>/mx/base/identityChoose/chooseUsersInsert?actionUrl=/mx/identity/group/saveGroupUsers?type=groupUserAddUser&rowId=${groupId}";
		  var width=700;
		  var height=400;
		  var scroll="yes";
		  //openWindow(link, width, height, scroll);
		  //alert(link);
		  art.dialog.open(link, { height: height, width: width, title: "群组用户", lock: true, scrollbars:"no" }, false);
	}

	function reLoadPage(){
		jQuery('#mydatagrid').datagrid('reload');
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',border:true" style="height:45px"> 
    <div class="search-backgroud" style="margin-top:8px;" > 
	<form id="searchForm" name="searchForm" method="post">
		<!-- <img src="<%=request.getContextPath()%>/images/window.png">
			&nbsp;<span class="x_content_title">群组列表</span> -->
		&nbsp;&nbsp;帐号：<input type="text" class="x-searchtext" name="userIdLike" id="userIdLike" size="10" >
		&nbsp;&nbsp;用户名：<input type="text" class="x-searchtext" name="unameLike" id="unameLike" size="10" >
		&nbsp;&nbsp;部门：<input type="text" class="x-searchtext" name="dnameLike" id="dnameLike" size="15" >
		&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchFormData();">查询</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onClick="javascript:jQuery('#searchForm').form('clear');">清空</a>
	</form>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
  <div id="tb" style="padding:5px;height:auto"  > 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onClick="javascript:addUser();">新增用户</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onClick="javascript:deleteSelections();">删除用户</a> 
  </div>
</div>
</body>
</html>
