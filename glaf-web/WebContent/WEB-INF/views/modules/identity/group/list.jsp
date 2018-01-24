<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
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
<script type="text/javascript">
var contextPath="<%=request.getContextPath()%>";
   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				toolbar:'#tb',
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/mx/identity/group/listJson?type=${type}',
				remoteSort: true,
				singleSelect:true,
				idField:'groupId',
				nowrap:true,
				columns:[[
	                {title:'序号',field:'startIndex',width:50,align:'center'},
					{title:'群组名称',field:'name', width:180,halign:'center',sortable:true},
					{title:'描述',field:'desc', width:180,halign:'center'},
					{title:'创建人',field:'createBy',width:80,align:'center',sortable:true},
					{title:'创建时间',field:'createDate',width:80,align:'center',sortable:true},
					{title:'更新人',field:'updateBy',width:80,align:'center',sortable:true},
					{title:'更新时间',field:'updateDate',width:80,align:'center',sortable:true}
				]],
				rownumbers:false,
				pagination:true,
				pageSize:10,
				pageList: [10,15,20,25,50,100],
				onDblClickRow: onMyRowClick
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});
		 

	function onMyRowClick(rowIndex, row){
		jQuery.layer({
				type: 2,
				shade: [0.5, '#000'],
				fix: false,
				title: '群组编辑',
				//maxmin: true,
				iframe: {src : '<%=request.getContextPath()%>/mx/identity/group/edit?type=${type}&groupId='+row.groupId},
				area: ['850px' , '350px']
			}); 
	}


	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function groupUser(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			//alert("请选择其中一条记录。");
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/identity/group/groupUsers?groupId="+selected.groupId;
		  var width=450;
		  var height=480;
		  var scroll="yes";
		  //openWindow(link, width, height, scroll);
		  //alert(link);
		  //art.dialog.open(link, { height: height, width: width, title: "群组用户", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "群组用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		   });
		}
	}

	function groupUserList(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			//alert("请选择其中一条记录。");
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/identity/group/listGroupUser?groupId="+selected.groupId;
		  var width=720;
		  var height=400;
		  var scroll="yes";
		  //openWindow(link, width, height, scroll);
		  //alert(link);
		  //art.dialog.open(link, { height: height, width: width, title: "群组用户", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "群组用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		 });
		}
	}

	function groupLeader(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			//alert("请选择其中一条记录。");
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/identity/group/groupLeaders?groupId="+selected.groupId;
		  var width=450;
		  var height=480;
		  var scroll="yes";
		  //openWindow(link, width, height, scroll);
		  //alert(link);
		  //art.dialog.open(link, { height: height, width: width, title: "群组领导", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "群组领导",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		 });
		}
	}

	function groupLeaderList(){
        var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			//alert("请选择其中一条记录。");
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/identity/group/listGroupLeader?groupId="+selected.groupId;
		  var width=720;
		  var height=400;
		  var scroll="yes";
		  //openWindow(link, width, height, scroll);
		  //alert(link);
		  //art.dialog.open(link, { height: height, width: width, title: "群组领导", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "群组领导",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		 });
		}
	}


	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].groupId);
		}
		if(ids.length<=0){
			jQuery.messager.alert('Info', '请选择至少一条记录。', 'info');
			return;
		}
		if(confirm("数据删除后不能恢复，确定删除吗？")){
		    var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/identity/group/batchDelete?groupIds='+rowIds,
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

	function sortGroup(id, operate){  
    	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/identity/group/sort?groupId='+id+'&operate='+operate,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   //location.reload();
					   jQuery('#mydatagrid').datagrid('reload');
				 }
		});
	}

	function addNew(){
		var link = "<%=request.getContextPath()%>/mx/identity/group/edit";
		//art.dialog.open(link, { height: 300, width: 550, title: "添加群组", lock: true, scrollbars:"yes" }, false);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加群组",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function editGroup(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			jQuery.messager.alert('Info', '请选择至少一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
			var link = '<%=request.getContextPath()%>/mx/identity/group/edit?type=${type}&groupId='+selected.groupId;
			//art.dialog.open(link, { height: 300, width: 550, title: "修改群组", lock: true, scrollbars:"yes" }, false);
			jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改群组",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		  });
		}
	}

	function addGroup(){
		jQuery.layer({
			type: 2,
			shade: [0.5, '#000'],
			fix: false,
			title: '群组编辑',
			//maxmin: true,
			//border: [0],
			//title: false,
			//shadeClose: true,
			//closeBtn: false,
			iframe: {src : '<%=request.getContextPath()%>/mx/identity/group/edit'},
			area: ['850px' , '350px']
		}); 
	}

	function editSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			jQuery.messager.alert('Info', '请选择其中一条记录。', 'info');
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
			jQuery.layer({
				type: 2,
				shade: [0.5, '#000'],
				fix: false,
				title: '群组编辑',
				iframe: {src : '<%=request.getContextPath()%>/mx/identity/group/edit?type=${type}&groupId='+selected.groupId},
				area: ['850px' , '350px']
			}); 
		}
	}

	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <!-- <div data-options="region:'north',border:true,title:'查询',iconCls:'icon-list'" style="height:65px"> -->
   <div data-options="region:'north',border:true" style="height:45px"> 
    <div class="search-backgroud" style="margin-top:8px;" > 
	<form id="searchForm" name="searchForm" method="post">
		<!-- <img src="<%=request.getContextPath()%>/images/window.png">
			&nbsp;<span class="x_content_title">群组列表</span> -->
		&nbsp;&nbsp;群组名称：<input type="text" class="x-searchtext" name="nameLike" id="nameLike" size="15" >
		&nbsp;&nbsp;描述：<input type="text" class="x-searchtext" name="descLike" id="descLike" size="15" >
		&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchFormData();">查询</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onClick="javascript:jQuery('#searchForm').form('clear');">清空</a>
	</form>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
  <div id="tb" style="padding:5px;height:auto"  > 
	<glaf:permission key="SystemAdministrator">
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:addNew();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	   onclick="javascript:editGroup();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a> 
	</glaf:permission>
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-user'"
	   onclick="javascript:groupUser();">群组用户</a> --> <!-- 树选择用户方式 -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-user'"
	   onclick="javascript:groupUserList();">群组用户</a> 
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-actor'"
	   onclick="javascript:groupLeader();">群组领导</a> --> <!-- 树选择用户方式 -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-actor'"
	   onclick="javascript:groupLeaderList();">群组领导</a>
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a> -->
  </div>
</div>
</body>
</html>
