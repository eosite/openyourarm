<%@page contentType="text/html; charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标段管理</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	var width=400;
	var height=300;
	
	jQuery(function(){
		jQuery("#dataTable").datagrid({
			url:'<%=request.getContextPath()%>/rs/muiProjListRest/getMuiProjList',
			sortName:"listNo",
			sortOrder:"asc",
			fitColumns:true,
			singleSelect:true,
			striped:true,
			columns:[[
				{field:"id",title:"id",width:"100",align:"center"},
				{field:"indexId",title:"indexId",width:"100",align:"center"},
				{field:"lineName",title:"线路名称",width:"300"},
				{field:"projName",title:"标段名称",width:"300"},
				{field:"dbName",title:"数据库名称",width:"250"},
				{field:"serverName",title:"数据库服务器地址",width:"250"},
				{field:"listNo",title:"排序",width:"100",align:"center",formatter:function(value,row,index){
					var a = "<a href=\"javascript:sort("+row.indexId+",0)\"><img src=\"<%=request.getContextPath()%>/images/up.gif\" border=\"0\" height=\"13\" width=\"13\" title=\"上移\"></a>";
					var b = "<a href=\"javascript:sort("+row.indexId+",1)\"><img src=\"<%=request.getContextPath()%>/images/down.gif\" border=\"0\" height=\"13\" width=\"13\" title=\"下移\"></a>";
					return a + b;
				}}
			]],
			toolbar:[{
				iconCls:"icon-add",
				handler:function(){
					var url="<%=request.getContextPath()%>/mx/docManage/yz/muiprojlist?method=addMain";
					art.dialog.open(url, {id:"proj_add_dialog",height: height, width: width, title: "添加标段", scrollbars:"no" , lock: false });
				}
			},{
				iconCls:"icon-edit",
				handler:function(){
					var row = jQuery("#dataTable").datagrid("getSelected");
					if(row){
						var url="<%=request.getContextPath()%>/mx/docManage/yz/muiprojlist?method=addMain&indexId="+row.indexId;
						art.dialog.open(url, {id:"proj_add_dialog",height: height, width: width, title: "编辑标段", scrollbars:"no" , lock: false });
					}else{
						jQuery.messager.alert("错误","请选择一条记录!","error");
					}
				}
			},{
				iconCls:"icon-cancel",
				handler:function(){
					var row = jQuery("#dataTable").datagrid("getSelected");
					if(row){
						if(confirm("确认删除所选记录？")){
							var url = "<%=request.getContextPath()%>/rs/muiProjListRest/deleteProjMuiprojlist";
							jQuery.post(url,{indexId:row.indexId},function(data){
								//var _data = eval("("+data+")");
								if(data.success){
									alert("删除成功！");
								}else{
									alert("删除失败!Error="+_data.error);
								}
								jQuery("#dataTable").datagrid("reload");
							},"json");
						}
					}else{
						jQuery.messager.alert("错误","请选择一条记录!","error");
					}
				}
			},{
				iconCls:"icon-reload",
				handler:function(){
					jQuery("#dataTable").datagrid("reload");
				}
			}]
		});
		
	});
	
	function sort(indexId,operate){
		var url = "<%=request.getContextPath()%>/rs/muiProjListRest/sort";
		jQuery.post(url,{indexId:indexId,operate:operate},function(data){
			if(data.success){
				jQuery("#dataTable").datagrid("reload");
			}else{
				alert(data.error);
			}
		},"json");
	}
	
</script>
</head>
<body style="margin: 1px">  
    <div style="margin:0;"></div>
    <table id="dataTable"></table>
</body>
</html>