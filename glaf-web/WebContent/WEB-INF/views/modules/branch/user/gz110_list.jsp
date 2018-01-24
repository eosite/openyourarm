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
<title>用户列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript">

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/mx/branch/user/json?parent=${parent}&deptId=${deptId}',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:true},
					{title:'用户名',field:'actorId', width:120},
					{title:'姓名',field:'showName', width:120},
					{title:'部门',field:'deptName', width:180},
					{title:'最近登录日期',field:'lastLoginTime', width:120},
					{title:'是否有效',field:'status', width:90, formatter:formatterStatus}
				]],
				rownumbers:false,
				pagination:true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100],
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});


	function formatterStatus(val, row){
       if(val == "0"){
			return '<span style="color:green; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">否</span>';
	   }  
	}

	function addNew(){
	    //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit";
	    var link="<%=request.getContextPath()%>/mx/branch/user/prepareAdd?parent=${parent}&deptId=${deptId}";
	    //art.dialog.open(link, { height: 430, width: 620, title: "添加用户", lock: true, scrollbars:"no" }, false);
		jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "添加用户",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	}

	function onRowClick(rowIndex, row){
            //window.open('<%=request.getContextPath()%>/branch/user.do?method=edit&id='+row.id);
	    var link = '<%=request.getContextPath()%>/mx/branch/user/prepareModify?parent=${parent}&deptId=${deptId}&id='+row.actorId_enc;
	    //art.dialog.open(link, { height: 430, width: 620, title: "修改用户", lock: true, scrollbars:"no" }, false);
		jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "修改用户",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','用户查询');
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
		  //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit&id="+selected.id;
		  var link = "<%=request.getContextPath()%>/mx/branch/user/prepareModify?parent=${parent}&deptId=${deptId}&id="+selected.actorId_enc;
		  //art.dialog.open(link, { height: 430, width: 620, title: "修改用户", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "修改用户",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	    }
	}


	function resetPwd(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit&id="+selected.id;
		  var link = "<%=request.getContextPath()%>/mx/branch/user/prepareResetPwd?parent=${parent}&deptId=${deptId}&id="+selected.actorId_enc;
		  //alert(link);
		  //art.dialog.open(link, { height: 300, width: 465, title: "重置用户密码", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "重置用户密码",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['580px', (jQuery(window).height() - 150) +'px'],
				iframe: {src: link}
			});
	    }
	}

	function userRoles(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit&id="+selected.id;
		  var link = "<%=request.getContextPath()%>/mx/branch/user/showRole?parent=${parent}&deptId=${deptId}&actorId="+selected.actorId_enc;
		  //art.dialog.open(link, { height: 420, width: 620, title: "用户角色设置", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "用户角色设置",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
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
		    var link ="<%=request.getContextPath()%>/mx/branch/user/prepareModify?parent=${parent}&deptId=${deptId}&id="+selected.actorId_enc;
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "修改用户",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].actorId);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var x_ids = ids.join(',');
			//alert(x_ids);
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/branch/user/delete?userIds='+x_ids,
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
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
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
	    var params = jQuery("#searchForm").formSerialize();
	    var queryParams = jQuery('#mydatagrid').datagrid('options').queryParams;
	    jQuery('#mydatagrid').datagrid('reload');	
	    jQuery('#dlg').dialog('close');
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">用户列表</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:addNew();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	   onclick="javascript:editSelected();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-pwd'"
	   onclick="javascript:resetPwd();">重置密码</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-actor'"
	   onclick="javascript:userRoles();">用户角色</a>
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a> -->
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
</body>
</html>