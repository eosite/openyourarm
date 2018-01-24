<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图表定义</title>
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
<style>
.x-searchtext {
	background-color:#fff;border:1px solid #d3d3d3;color:#666; padding:2px 2px; line-height:18px ; height:18px;font-size: 13px;
} 
</style>
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
		loadMxData('<%=request.getContextPath()%>/mx/bi/chart/json?nodeId='+treeNode.id);
	}

	function loadMxData(url){
		  jQuery.get(url+'&randnum='+Math.floor(Math.random()*1000000),{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  jQuery('#easyui_data_grid').datagrid('loadData', data);
			  //jQuery('#easyui_data_grid').datagrid('load',getMxObjArray(jQuery("#iForm").serializeArray()));
		  },'json');
	}

    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

		jQuery(function(){
			jQuery('#easyui_data_grid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				url:'<%=request.getContextPath()%>/mx/bi/chart/json',
				remoteSort: false,
				singleSelect:true,
				idField:'id',
				pageSize:20,
				columns:[[
	                {title:'编号',field:'id',width:80,sortable:true},
					{field:'subject',title:'标题',width:180},
					{field:'chartName',title:'图表名称',width:120},
					{field:'chartTitle',title:'图表主题',width:180},
					{field:'chartType',title:'图表类型',width:130,formatter:formatter1},
					{field:'functionKey', title:'功能键', align:'center', width:80, formatter:formatterKeys}
				]],
				pagination:true,
				rownumbers:false,
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#easyui_data_grid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					alert('before refresh');
				}
			});
		});

		 function formatterKeys(val, row){
		   var str = "<a href='javascript:editRow(\""+row.id+"\");'>修改</a>&nbsp;<a href='javascript:viewChart(\""+row.id+"\");'>图表</a>";        
	       return str;
	    }

		function editRow(id){
			var link = "<%=request.getContextPath()%>/mx/bi/chart/edit?chartId="+id;
				jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "编辑图表信息",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['20px',''],
					fadeIn: 100,
					area: ['1080px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
		}

		function viewChart(id){
			var link = "<%=request.getContextPath()%>/mx/bi/chart/showChart?chartId="+id;
				jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "查看图表",
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
           // window.open('<%=request.getContextPath()%>/mx/bi/chart/edit?chartId='+row.id);
		   editRow(row.id);
		}

		function formatter1(value,row,index){
			if(value=='pie'){
				return "饼图";
			} else if(value=='donut'){
				return "环形图";
			} else if(value=='line'){
				return "线形图";
			} else if(value=='line_sum'){
				return "曲线汇总图";
			} else if(value=='bar'){
				return "条形图";
			} else if(value=='bar_line'){
				return "条形曲线图";
			} else if(value=='bar_line_sum'){
				return "条形曲线汇总图";
			} else if(value=='radarLine'){
				return "雷达图";
			} else if(value=='area'){
				return "面积图";
			} else if(value=='column'){
				return "柱状图";
			} else if(value=='column_line'){
				return "柱状曲线图";
			} else if(value=='column_line_sum'){
				return "柱状曲线汇总图";
			} else if(value=='stackedbar'){
				return "堆叠条形图";
			} else if(value=='stacked_area'){
				return "堆叠面积图";
			} else if(value=='stackedbar_line'){
				return "堆叠条形曲线图";
			} else if(value=='stackedbar_line_sum'){
				return "堆叠条形曲线汇总图";
			} else if(value=='funnel'){
				return "漏斗图";
			} else if(value=='gauge'){
				return "仪表盘";
			}
			return "";
		}

		function searchWin(){
			jQuery('#dlg').dialog('open').dialog('setTitle','图表定义查询');
			//jQuery('#iForm').form('clear');
		}

		function resize(){
			jQuery('#easyui_data_grid').datagrid('resize', {
				width:800,
				height:400
			});
		}

		function create(){	
			var nodeId = jQuery("#nodeId").val();
			if(nodeId==null || nodeId=='' ){
			    alert("请在左边选择分类类型！");
			    return;
		    }
			location.href="<%=request.getContextPath()%>/mx/bi/chart/edit?nodeId="+nodeId;
		}

		function editSelected(){
		    var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			    //location.href="<%=request.getContextPath()%>/mx/bi/chart/edit?chartId="+selected.id;
				var link = "<%=request.getContextPath()%>/mx/bi/chart/edit?chartId="+selected.id;
				window.open(link);
			}
		}

		function viewSelected(){
		    var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
			if (selected ){
			  location.href="<%=request.getContextPath()%>/mx/bi/chart/edit?chartId="+selected.id;
			}
		}

		function deleteSelections(){
			var ids = [];
			var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
			  var chartIds = ids.join(',');
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/chart/deleteAll?chartIds='+chartIds,
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
					   jQuery('#easyui_data_grid').datagrid('reload');
				   }
			 });
			} else {
			    //alert("请选择至少一条记录。");
			}
		}

	function getSelected(){
		var selected = jQuery('#easyui_data_grid').datagrid('getSelected');
		if (selected){
			alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
		}
	}

	function getSelections(){
		var ids = [];
		var rows = jQuery('#easyui_data_grid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].code);
		}
		alert(ids.join(':'));
	}

	function clearSelections(){
		jQuery('#easyui_data_grid').datagrid('clearSelections');
	}


	function searchData(){
		var searchWord = document.getElementById("searchWord2").value.trim();
        document.getElementById("keywordsLike").value = searchWord;
		var params = jQuery("#iForm").formSerialize();
        jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/mx/bi/chart/json',
                    dataType: 'json',
				    data: params,
                    error: function(data){
                           alert('服务器处理错误！');
                    },
                    success: function(data){
                           jQuery('#easyui_data_grid').datagrid('loadData', data);
                    }
               });
	}

</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<input type="hidden" id="nodeId" name="nodeId" value="" >
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
		<div class="toolbar-backgroud"> 
		<span class="x_content_title">图表定义列表</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
		   onclick="javascript:create();">新增</a>  
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
		   onclick="javascript:editSelected();">修改</a>  
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
		   onclick="javascript:deleteSelections();">删除</a> 
		<input id="searchWord2" name="searchWord2" type="text" 
	       class="x-searchtext" size="50" maxlength="200"/>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
		   onclick="javascript:searchData();">查找</a>
	   </div> 
	  </div> 
	  <div data-options="region:'center',border:false">
		 <table id="easyui_data_grid"></table>
	  </div>  
	</div>
  </div>
</div>
<form id="iForm" name="iForm" method="post">
   <input type="hidden" id="keywordsLike" name="keywordsLike">
</form>
</body>
</html>