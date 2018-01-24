<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.base64.js"></script>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";

   var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/rs/datamgr/project/treeJson?category=${category}",
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
		var link = "<%=request.getContextPath()%>/mx/my/project/json?category=${category}&parentId="+treeNode.id;
		loadGridData(link);
 	}

    jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});


   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: '<%=request.getContextPath()%>/mx/my/project/json?category=${category}&parentId=${parentId}',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:60, sortable:false},
					    {title:'编号', field:'id', width:60, sortable:false},
					    {title:'名称', field:'name', width:220, sortable:false},
					    {title:'代码', field:'code', width:120, sortable:false},
					    {title:'主题', field:'title', width:220, sortable:false},
				        {title:'是否启用', field:'active', width:90, align:"center", sortable:false, formatter:formatterActive},
					    {title:'创建日期', field:'createTime_date', width:90, sortable:false},
					    {field:'functionKey',title:'功能键',width:120, formatter:formatterKeys }
				]],
				rownumbers: false,
				pagination: true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100],
				pagePosition: 'both'
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });

			$('#mydatagrid').datagrid({
				onDblClickRow: function(index, row){
					 editRow(row.id);
				}
			});

	});

		 
	function addNew(){
	    //var link="<%=request.getContextPath()%>/mx/my/project/edit?fromUrl=${fromUrl}";
	    //art.dialog.open(link, { height: 420, width: 680, title: "添加记录", lock: true, scrollbars:"no" }, false);
		//location.href=link;
		var nodeId = jQuery("#nodeId").val();
		link="<%=request.getContextPath()%>/mx/my/project/edit?category=${category}&parentId="+nodeId;
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
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function onRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/mx/my/project/edit?id='+row.id+'&category=${category}&fromUrl=${fromUrl}';
	    //art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}

	function formatterActive(val, row){
		if(val == "1"){
			return "<font color='green'>启用</font>";
		} else {
            return "<font color='red'>禁用</font>";
		}
	}

	function formatterLevel(val, row){
		if(val == 1){
			return "主库";
		} else {
            return "从库";
		}
	}

	function formatterKeys(val, row){
		var str = "<a href='javascript:editRow(\""+row.id+"\");'>修改</a>&nbsp;<a href='javascript:deleteRow(\""+row.id+"\");'>删除</a>";
	    return str;
	}

	function refRow(id){
       location.href="<%=request.getContextPath()%>/mx/my/project/choose?category=${category}&projectId="+id;
	}

	function editRow(id){
		link="<%=request.getContextPath()%>/mx/my/project/edit?id="+id;
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
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}


	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function deleteRow(id){
		if(confirm("删除数据不能恢复，确定删除吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/my/project/deleteById?id='+id,
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
					   if(data.statusCode == 200){
						   window.location.reload();
					   }
				   }
			 });
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
		  var link = '<%=request.getContextPath()%>/mx/my/project/edit?id='+selected.id+'&category=${category}&fromUrl=${fromUrl}';
		  //art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		  //location.href=link;
		  editRow(selected.id);
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
		    location.href='<%=request.getContextPath()%>/mx/my/project/edit?readonly=true&category=${category}&id='+selected.id+'&fromUrl=${fromUrl}';
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

	function loadGridData(url){
        //var params = jQuery("#iForm").formSerialize();
	    jQuery.ajax({
			type: "POST",
			url:  url,
			//data: params,
			dataType:  'json',
			error: function(data){
				alert('服务器处理错误！');
			},
			success: function(data){
				jQuery('#mydatagrid').datagrid('loadData', data);
			}
		});
	}

	function searchData(){
        var params = jQuery("#searchForm").formSerialize();
        jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/mx/my/project/json?category=${category}',
                    dataType:  'json',
                    data: params,
                    error: function(data){
                              alert('服务器处理错误！');
                    },
                    success: function(data){
                              jQuery('#mydatagrid').datagrid('loadData', data);
                    }
                  });

	    jQuery('#dlg').dialog('close');
	}
	

 function projectDB(){
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
		    location.href="<%=request.getContextPath()%>/mx/my/project/choose?category=${category}&projectId="+selected.id;
		} else {
            alert("请选择其中一条记录。");
			return;  
		}
	}

  function perm(){
		location.href="<%=request.getContextPath()%>/mx/my/project/permission?category=${category}";
  }
 
  function sortProject(){
	  var nodeId = jQuery("#nodeId").val();
	  var link = "<%=request.getContextPath()%>/mx/my/project/showSort?category=${category}&parentId="+nodeId;
	  var width=680;
	  var height=430;
	  var scroll="yes";
	  jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "节点排序",
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
<body>
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" >
<input type="hidden" id="category" name="category" value="${category}" > 
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:240px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
   <div data-options="region:'center'"> 
	<div class="easyui-layout" data-options="fit:true">  
	  <div data-options="region:'center',border:false">
	    <div class="toolbar-backgroud"  > 
			<img src="<%=request.getContextPath()%>/images/window.png">
			&nbsp;<span class="x_content_title">分类列表</span>
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
			   onclick="javascript:addNew();">新增</a>  
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
			   onclick="javascript:editSelected();">修改</a> 
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-sys'"
			   onclick="javascript:sortProject();">同级排序</a>
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-reload'"
			   onclick="javascript:reloadGrid();">全部</a> 
			<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-role'"
			   onclick="javascript:perm();">授权</a> -->
			<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-props'"
			   onclick="javascript:projectDB();">数据库关联</a>
			<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-imp'"
			   onclick="javascript:showImport();">导入数据</a> -->
		</div> 
		<table id="mydatagrid"></table>
	  </div>  
	</div>
  </div>
</div>
</body>
</html>
