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
<title>合成表管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">

   var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/rs/tree/treeJson?nodeCode=report_category",
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
			childNodes[i].icon="<%=request.getContextPath()%>/images/basic.gif";
		}
		return childNodes;
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		//alert(treeNode.id);
		loadMxData('<%=request.getContextPath()%>/mx/datamgr/table/json?nodeId='+treeNode.id);
	}

	function loadMxData(url){
		  jQuery.get(url+'&randnum='+Math.floor(Math.random()*1000000),{qx:'xx'},function(data){
		      var text = JSON.stringify(data); 
              //alert(text);
			  jQuery('#mydatagrid').datagrid('loadData', data);
			  //jQuery('#mydatagrid').datagrid('load',getMxObjArray(jQuery("#iForm").serializeArray()));
		  },'json');
	}


	function reLoadData(nodeId){
		loadData('<%=request.getContextPath()%>/mx/datamgr/table/json?nodeId='+nodeId);
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
				url:'<%=request.getContextPath()%>/mx/datamgr/table/json',
				remoteSort: false,
				singleSelect:true,
				idField:'tableName',
				columns:[[
	                {title:'序号', field:'startIndex', width:80, sortable:false},
					{title:'表名', field:'tableName', width:150, sortable:false},
					{title:'标题', field:'title', width:280, sortable:false},
					{title:'执行次序', field:'sortNo', width:100, sortable:false},
					{title:'临时表', field:'temporaryFlag', width:100, sortable:false, formatter: formatter4},
					{title:'抓取前删除', field:'deleteFetch', width:100, sortable:false, formatter: formatter5},
					{title:'创建日期', field:'createTime', width:100, sortable:false},
					{title:'功能键', field:'functionKeys', width:120, sortable:false, formatter:formatterKeys}
				]],
				rownumbers:false,
				pagination:true,
				pageSize:15,
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


	function formatter4(value, row, rowIndex) {
		if(value == "1"){
           return "<div style='width:100%;color:red;' title='临时表'><nobr>是</nobr></div>";
		}
		return "<div style='width:100%;color:green;' title='非临时表' ><nobr>否</nobr></div>";
	}

	function formatter5(value, row, rowIndex) {
		if(value == "1"){
           return "<div style='width:100%;color:red;' title='抓取前删除'><nobr>是</nobr></div>";
		}
		return "<div style='width:100%;color:green;' title='抓取前不删除' ><nobr>否</nobr></div>";
	}

	function formatterKeys(val, row){
		var str = "<a href='javascript:editRow(\""+row.tableName_enc+"\");'>修改</a>&nbsp;<a href='javascript:transformTable(\""+row.tableName_enc+"\");'>抓取</a>";        
	    return str;
	}

		 
	function addNew(){
		var nodeId = jQuery("#nodeId").val();
		if(nodeId==null || nodeId=='' ){
			alert("请在左边选择分类类型！");
			return;
		}
		var link="<%=request.getContextPath()%>/mx/datamgr/table/edit?nodeId="+nodeId;
		location.href=link;
	    //art.dialog.open(link, { height: 480, width: 900, title: "添加记录", lock: true, scrollbars:"no" }, false);
	}

	function onRowClick(rowIndex, row){
		var nodeId = jQuery("#nodeId").val();
	    var link = '<%=request.getContextPath()%>/mx/datamgr/table/edit?tableName_enc='+row.tableName_enc+"&nodeId="+nodeId;
		//art.dialog.open(link, { height: 480, width: 900, title: "修改记录", lock: true, scrollbars:"no" }, false);
		//location.href=link;
		editRow(row.tableName_enc);
	}

	function editRow(id){
		var nodeId = jQuery("#nodeId").val();
		var link = '<%=request.getContextPath()%>/mx/datamgr/table/edit?tableName_enc='+id+"&nodeId="+nodeId;
		jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "编辑表信息",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['20px',''],
					fadeIn: 100,
					area: ['980px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','信息查询');
	}

	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    var nodeId = jQuery("#nodeId").val();
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/datamgr/table/edit?tableName_enc="+selected.tableName_enc+"&nodeId="+nodeId;
		  //art.dialog.open(link, { height: 480, width: 900, title: "修改记录", lock: true, scrollbars:"no" }, false);
		  location.href=link;
	    }
	}


	function addRow(){
		var nodeId = jQuery("#nodeId").val();
		if(nodeId==null || nodeId=='' ){
			alert("请在左边选择分类类型！");
			return;
		}
		var link = "<%=request.getContextPath()%>/mx/datamgr/table/edit?nodeId="+nodeId;
		location.href=link;
	}

	 
	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/mx/datamgr/table/edit?tableName_enc="+selected.tableName_enc;
		}
	}


	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var str = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/table/delete?ids='+str,
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
	    jQuery('#mydatagrid').datagrid('reload');	
	    jQuery('#dlg').dialog('close');
	}

    function transformTable(tableName_enc){
		if(confirm("确定重新获取数据吗？")){
			var params = jQuery("#iForm").formSerialize();
			jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/datamgr/table/transform?tableName_enc='+tableName_enc,
					   data: params,
					   dataType:  'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
							if(data.message != null){
							   alert(data.message);
							} else {
							   alert('操作成功完成！');
							}
					   }
			});
		  }
	  }


	 function loadAndFetchData(){
          if(confirm("重新加载数据并取数，确定吗？")){
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/datamgr/table/transformAll',
					   data: params,
					   dataType:  'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						   if(data.message != null){
                             alert(data.message);
						   } else {
							 alert('操作成功完成！');
						   }
					   }
				 });
		  }
	}

	function fetchData(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    transformTable(selected.tableName_enc);
		}
	}
 

	function listData(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		    showData(selected.tableName_enc);
		}
	}

	 
	 function showData(tableName_enc){
		var link= '<%=request.getContextPath()%>/mx/datamgr/table/resultList?q=1';
		document.getElementById("tableName_enc").value=tableName_enc;
        document.iForm.action=link;
		document.iForm.submit();
	}

	function editTable(tableName_enc){
		document.getElementById("tableName_enc").value=tableName_enc;
		document.getElementById("iForm").action="<%=request.getContextPath()%>/mx/datamgr/table/edit";
		document.getElementById("iForm").submit();
	}

</script>
</head>
<body style="margin:1px;">  
<input type="hidden" id="rowId" name="rowId" value="" >
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:180px;">
	  <div class="easyui-layout" data-options="fit:true">  
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
        </div>  
	</div> 
   <div data-options="region:'center'">  
     <div class="easyui-layout" data-options="fit:true"> 
	   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
	   <form id="iForm" name="iForm" method="post">
	   <input type="hidden" id="nodeId" name="nodeId" value="" >
	   <input type="hidden" id="tableName" name="tableName" />
	   <input type="hidden" id="tableName_enc" name="tableName_enc" />
		<div class="toolbar-backgroud"  >&nbsp;&nbsp; 
		<img src="<%=request.getContextPath()%>/images/window.png">
		<span class="x_content_title"> 合成表管理</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'"
		   onclick="javascript:addRow();">新建</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
		   onclick="javascript:editSelected();">修改</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-list'"
		   onclick="javascript:listData();">查看数据</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-sys'"
		   onclick="javascript:fetchData();">抓取数据</a> 
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-sys'"
		   onclick="javascript:loadAndFetchData();">全部重新抓取</a> 
	   </div> 
	   </form>
	  </div> 
	  <div data-options="region:'center',border:true">
		 <table id="mydatagrid"></table>
	  </div>  
    </div>
  </div>
</div>
</body>
</html>
