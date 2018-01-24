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
<title>分类树</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css"  />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<style>
.btnGreen {
	padding: 5px 20px; border-radius: 3px; border: 1px solid rgb(61, 129, 12); text-align: center; color: rgb(255, 255, 255); overflow: visible; display: inline-block; cursor: pointer; background-color: rgb(90, 170, 75); -moz-border-radius: 3px; -webkit-border-radius: 3px;
}
.btnGreen:hover {
	color: rgb(255, 255, 255); box-shadow: 0px 1px 1px #aaa; background-color: rgb(83, 160, 70); -moz-box-shadow: 0 1px 1px #aaa; -webkit-box-shadow: 0 1px 1px #aaa;
}
.btnGreenS {
	margin: 0px; padding: 3px 5px; border-radius: 3px; border: 1px solid rgb(61, 129, 12); text-align: center; color: rgb(255, 255, 255); overflow: visible; display: inline-block; cursor: pointer; background-color: rgb(90, 170, 75); -moz-border-radius: 3px; -webkit-border-radius: 3px;
}
.btnGreenS:hover {
	color: rgb(255, 255, 255); box-shadow: 0px 1px 1px #aaa; background-color: rgb(83, 160, 70); -moz-box-shadow: 0 1px 1px #aaa; -webkit-box-shadow: 0 1px 1px #aaa;
}
.btnGreenS:hover {
	color: rgb(255, 255, 255); box-shadow: 0px 1px 1px #aaa; background-color: rgb(83, 160, 70); -moz-box-shadow: 0 1px 1px #aaa; -webkit-box-shadow: 0 1px 1px #aaa;
}
.btnGreenS#saveSetting {
	margin-left: 105px;
}
.btnGray {
	padding: 5px 20px; border-radius: 3px; border: 1px solid rgb(170, 170, 170); text-align: center; color: rgb(0, 0, 0); overflow: visible; display: inline-block; cursor: pointer; background-color: rgb(244, 244, 244); -moz-border-radius: 3px; -webkit-border-radius: 3px;
}
.btnGray:hover {
	box-shadow: 0px 1px 1px #ccc; background-color: rgb(248, 248, 248); -moz-box-shadow: 0 1px 1px #ccc; -webkit-box-shadow: 0 1px 1px #ccc;
}
.btnGrayS {
	margin: 0px; padding: 3px 5px; border-radius: 3px; border: 1px solid rgb(170, 170, 170); text-align: center; color: rgb(0, 0, 0); overflow: visible; display: inline-block; cursor: pointer; background-color: rgb(244, 244, 244); -moz-border-radius: 3px; -webkit-border-radius: 3px;
}
.btnGrayS:hover {
	box-shadow: 0px 1px 1px #ccc; background-color: rgb(248, 248, 248); -moz-box-shadow: 0 1px 1px #ccc; -webkit-box-shadow: 0 1px 1px #ccc;
}
.btnGrayS:hover {
	box-shadow: 0px 1px 1px #ccc; background-color: rgb(248, 248, 248); -moz-box-shadow: 0 1px 1px #ccc; -webkit-box-shadow: 0 1px 1px #ccc;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
<script type="text/javascript">

    var prevTreeNode;

    var setting = {
			async: {
				enable: true,
				//url: getAjaxTreeUrl,
				url: "<%=request.getContextPath()%>/rs/sys/tree/allTreeJson?nodeCode=0",
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
			if(childNodes[i].isParent){
			    //childNodes[i].icon="<%=request.getContextPath()%>/images/orm_root.gif";
			}
		}
		return childNodes;
	}

	function getAjaxTreeUrl(treeId, treeNode) {
		if(treeNode){
		    return "<%=request.getContextPath()%>/rs/sys/tree/treeJson?nodeCode=0&node="+treeNode.id;
		}
        return "<%=request.getContextPath()%>/rs/sys/tree/treeJson?nodeCode=0";
	}
 
    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		loadData('<%=request.getContextPath()%>/mx/sys/tree/json?parentId='+treeNode.id);
		if(treeNode.id == 4 || treeNode.parentId == 4){
			//alert(treeNode.parentId);
			document.getElementById("dictBtn").disabled=false;
			//alert(document.getElementById("dictBtn").disabled);
		} else {
			document.getElementById("dictBtn").disabled=true;
		}
	}

	function dict(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		    alert("请选择其中一条记录。");
		    return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
			var link="<%=request.getContextPath()%>/mx/sys/dictoryDefinition/edit?target=SYS_DICTORY&nodeId="+selected.id;
			//alert(link);
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "字典信息",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['620px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
		}
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
				url:'<%=request.getContextPath()%>/mx/sys/tree/json',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				columns:[[
	                {title:'序号',field:'startIndex',width:80,sortable:true},
					{title:'名称',field:'name', width:120},
					{title:'代码',field:'code', width:120},
					{title:'描述',field:'desc', width:180},
					{title:'是否有效',field:'locked', width:90, formatter:formatterStatus}
				]],
				rownumbers:false,
				pagination:true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100,200,500],
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
       if(val == 0){
			return '<span style="color:green; font: bold 13px 宋体;">是</span>';
	   } else  {
			return '<span style="color:red; font: bold 13px 宋体;">否</span>';
	   }  
	}


	function addNew(){
		var nodeId = jQuery("#nodeId").val();
		var link = "<%=request.getContextPath()%>/mx/sys/tree/prepareAdd?parent="+nodeId;
	    art.dialog.open(link, { height: 400, width: 480, title: "添加分类", lock: true, scrollbars:"yes" }, false);
		//openWindow(link, 400, 480, "yes");
	}


	function onRowClick(rowIndex, row){
		var nodeId = jQuery("#nodeId").val();
	    var link = '<%=request.getContextPath()%>/mx/sys/tree/prepareModify?id='+row.id;
	    art.dialog.open(link, { height: 400, width: 480, title: "修改分类", lock: true, scrollbars:"yes" }, false);
		//openWindow(link, 400, 480, "yes");
	}


	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','分类树查询');
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
		  //location.href="<%=request.getContextPath()%>/mx/sys/tree/edit&rowId="+selected.id;
		  var link = "<%=request.getContextPath()%>/mx/sys/tree/prepareModify?id="+selected.id;
		  art.dialog.open(link, { height: 400, width: 480, title: "修改分类", lock: true, scrollbars:"yes" }, false);
		  //openWindow(link, 400, 480, "yes");
	    }
	}


	function categoryPerms(){
		 var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		    alert("请选择其中一条记录。");
		    return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  var link = "<%=request.getContextPath()%>/mx/sys/tree/showPerms?nodeId="+selected.id;
		  //art.dialog.open(link, { height: 400, width: 480, title: "修改分类", lock: true, scrollbars:"yes" }, false);
		  openWindow(link, 680, 560, "yes");
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
		    location.href="<%=request.getContextPath()%>/mx/sys/tree/prepareModify?id="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/tree/delete?rowIds='+rowIds,
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
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:245px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
   <div data-options="region:'center'">   
		<div class="easyui-layout" data-options="fit:true">  
		   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
			<div class="toolbar-backgroud"  > 
			<img src="<%=request.getContextPath()%>/images/window.png">
			&nbsp;<span class="x_content_title">分类列表</span>
			<input type="button" class="btn btnGray"  
			   onclick="javascript:addNew();" value="新增">
			<input type="button" class="btn btnGray"  
			   onclick="javascript:editSelected();" value="修改">
			<input type="button" class="btn btnGray"  
			   onclick="javascript:deleteSelections();" value="删除">
			<input type="button" class="btn btnGray"  
			   onclick="javascript:dict();" id="dictBtn" name="btn_dict" disabled value="字典">
			<input type="button" class="btn btnGray"  
			   onclick="javascript:categoryPerms();" value="分类权限">
			<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
			   onclick="javascript:searchWin();">查找</a> -->
		   </div> 
		  </div> 
		  <div data-options="region:'center',border:true">
			 <table id="mydatagrid"></table>
		  </div>  
      </div>
	</div>
</div>
<div id="edit_dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
    <form id="editForm" name="editForm" method="post">
         
    </form>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
    <form id="searchForm" name="searchForm" method="post">
	<table class="easyui-form" >
            <tbody>
	    </tbody>
        </table>
    </form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
</div>
</body>
</html>
