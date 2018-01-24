<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据表列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:450,
				fit:true,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: '${contextPath}/mx/sys/tableMgr/json?type=${type}',
				remoteSort: false,
				singleSelect: true,
				idField: 'tableId',
				columns:[[
				    {title:'序号', field:'startIndex', align:'left', width:60, sortable:false},
					{title:'标题', field:'title', width:180},
					{title:'表名', field:'tableName', align:'center', width:120, formatter:formatterTable},
					{title:'创建日期', field:'createTime', align:'center', width:90},
					{title:'功能键', field:'functionKey', align:'left', width:250, formatter:formatterKeys}
				]],
				rownumbers: false,
				pagination: true,
				pageSize: 10,
				pageList: [10,15,20,25,30,40,50,100,200,500],
				pagePosition: 'both',
				onDblClickRow: onMyRowClick 
			});
	});

   function formatterTable(val, row){
		 return val;
	}


   function formatterKeys(val, row){
		var str = "<a href='javascript:datalist(\""+row.tableId+"\");'><img src='${contextPath}/images/list.gif' border='0'>数据</a>&nbsp;<a href='javascript:editRow(\""+row.tableId+"\");'><img src='${contextPath}/images/edit.gif' border='0'>修改</a>&nbsp;<a href='javascript:deleteRow(\""+row.tableId+"\");'><img src='${contextPath}/images/remove.png' border='0'>删除</a>&nbsp;<a href='javascript:tableColumns(\""+row.tableId+"\");'><img src='${contextPath}/images/FIX_duplicate_row.png' border='0'>字段列表</a>&nbsp;<a href='javascript:sqlCriterias(\""+row.tableId+"\", \""+row.tableName+"\");'><img src='${contextPath}/images/FIX_join_right.png' border='0'>条件</a>&nbsp;<a href='javascript:updateSchema(\""+row.tableId+"\");'><img src='${contextPath}/images/cfg.png' border='0'>更新表</a>";
		if(row.isSubTable == "N"){
			str = str + "&nbsp;<a href='javascript:tableCorrelation(\""+row.tableId+"\");'><img src='${contextPath}/images/bricks.png' border='0'>关联</a>";
		}
		str = str + "&nbsp;<a href='javascript:privilege(\""+row.tableId+"\");'><img src='${contextPath}/images/actor.gif' border='0'>授权</a>";
	    return str;
	}

	function datalist(tableId){
		window.open("${contextPath}/mx/tableData/datalist?tableId="+tableId);
	}

	function addNew(){
	    var link="${contextPath}/mx/sys/tableMgr/editTable?type=${type}";
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "新增记录",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function sqlCriterias(tableId, tableName){
		link="${contextPath}/mx/sys/sqlCriteria?moduleId="+tableId+"&businessKey="+tableName+"&tableName="+tableName;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "查询条件",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['1080px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}


	function tableCorrelation(tableId){
		link="${contextPath}/mx/sys/tableCorrelation?tableId="+tableId;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "表关联关系",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function tableColumns(tableId){
		link="${contextPath}/mx/sys/tableMgr/columns?type=${type}&tableId="+tableId;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "列信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}
 

	function editRow(tableId){
		link="${contextPath}/mx/sys/tableMgr/editTable?type=${type}&tableId="+tableId;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑记录",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function onMyRowClick(rowIndex, row){
	    var link = '${contextPath}/mx/sys/tableMgr/editTable?type=${type}&tableId='+row.tableId;
	    jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑记录",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function deleteRow(tableId){
		if(confirm("删除数据不能恢复，确定删除吗？")){
			if(confirm("表相关的列定义数据都将删除且不能恢复，确实要删除吗？")){
			  jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/deleteTable?tableId='+tableId,
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
					   if(data.statusCode == 200){
						   //window.location.reload();
						   jQuery('#mydatagrid').datagrid('reload');
					   }
				   }
			 });
			}
		}
	}

	function updateSchema(tableId){
		if(confirm("将根据字段信息更新数据库物理表结构，确定吗？")){
			if(confirm("字段类型确定后将无法修改，确实要更新吗？")){
			  jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/updateSchema?tableId='+tableId,
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
					   if(data.statusCode == 200){
						   //window.location.reload();
					   }
				   }
			 });
			}
		}
	}

	function updateAllSchema(){
		if(confirm("将根据字段信息更新数据库物理表结构，确定吗？")){
			if(confirm("字段类型确定后将无法修改，确实要更新吗？")){
			  jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/updateAllSchema',
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
					   if(data.statusCode == 200){
						   //window.location.reload();
					   }
				   }
			 });
			}
		}
	}
 

	function editSelected(){
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		    alert("请选择其中一条记录。");
		    return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  var link = '${contextPath}/mx/sys/tableMgr/editTable?type=${type}&tableId='+selected.tableId;
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "编辑记录",
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
		    var link='${contextPath}/mx/sys/tableMgr/editTable?type=${type}&tableId='+selected.tableId;
		    jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "编辑记录",
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
			ids.push(rows[i].id);
		}
		if(ids.length == 1 ){
		  if(confirm("数据删除后不能恢复，确定删除吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/sys/tableMgr/deleteTable?type=${type}&tableId='+rows[0].tableId,
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
					   if(data.statusCode == 200){
					       jQuery('#mydatagrid').datagrid('reload');
					   }
				   }
			 });
		  }
		} else {
			alert("请选择一条记录。");
		}
	}

	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}

	 
	function loadGridData(url){
	    jQuery.ajax({
			type: "POST",
			url:  url,
			dataType:  'json',
			error: function(data){
				alert('服务器处理错误！');
			},
			success: function(data){
				jQuery('#mydatagrid').datagrid('loadData', data);
			}
		});
	}

	 
    function privilege2(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected){
		    var link='${contextPath}/mx/sys/tableSysPermission/privilege?granteeType=role&privilege=r&tableId='+selected.tableId;
		    jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "授权",
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

	function privilege(tableId){
		 var link='${contextPath}/mx/sys/tableSysPermission/privilege?granteeType=role&privilege=read&tableId='+tableId;
		 jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "授权",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['880px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
		   });
	}

</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'center',border:true">
   <div class="toolbar-backgroud"  > 
	<img src="${contextPath}/images/window.png">
	&nbsp;<span class="x_content_title">数据表列表</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:addNew();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	   onclick="javascript:editSelected();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-package'"
	   onclick="javascript:updateAllSchema();">更新全部表结构</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-actor'"
	   onclick="javascript:privilege2();">授权</a> 
   </div> 
   <table id="mydatagrid"></table>
  </div>  
</div>
</body>
</html>