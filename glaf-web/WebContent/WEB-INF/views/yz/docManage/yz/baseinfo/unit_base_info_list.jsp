<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本情况列表</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	jQuery(function(){
		jQuery("#myTable").datagrid({
			url:'<%=request.getContextPath()%>/rs/baseinfoRest/getBaseInfoList?type=<%=request.getParameter("type")%>',
			width:"1000",
			height:"480",
			fit:true,
			fitColumns:true,
			nowrap: false,
			striped: true,
			collapsible:true,
			singleSelect:false,
			remoteSort: false,
			idField:"id",
			columns:[[
			    {field:"projName",title:"项目名称",width:"200"},
			    {field:"indexName",title:"单位工程名称",width:"300"},
			    {field:"num",title:"项目编号",width:"150",align:"center"},
			    {field:"content",title:"说明",width:"150",align:"center"},
			    {field:"fileName",title:"文件名称",width:"150"},
			    {field:"id",title:"查看",width:"50",align:"center",formatter:function(value){
			    	var link = "<a href='javascript:showDetail("+value+");'>查看</a>";
			    	return link;
			    }}
			]],
			onLoadSuccess:function(){
				
			},
			rownumbers:true
		});
	});
	
	function addNew(){
		  var url="<%=request.getContextPath()%>/mx/docManage/yz/baseinfo/baseInfoAdd?type=2";
		  var width=800;
		  var height=450;
		  art.dialog.open(url, { height: height, width: width, title: "添加基本情况", scrollbars:"no" , lock: false });
	}
	
	function showDetail(id){
			var width=700;
		    var height=450;
		    var url="<%=request.getContextPath()%>/mx/docManage/yz/baseinfo/view?id="+id;
		    art.dialog.open(url, { height: height, width: width, title: "查看基本情况", scrollbars:"no" , lock: false });
	}
	
	function deleteSelections(){
		var ids = [];
		var rows = jQuery("#myTable").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length == 0){
			alert("请选择至少一条记录。");
			return;
		}else if(confirm("确定要删除所选择的记录吗？")){
			var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/baseinfoRest/deleteBaseInfos?rowIds='+rowIds,
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
					   jQuery("#myTable").datagrid("reload");
				   }
			 });
		}
	}
</script>
</head>
<body style="margin: 1px;">  
	<div style="margin:0;"></div>  
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:true" style="height:40px">
		    <div class="toolbar-backgroud"  style="height: 27px"> 
				<img src="<%=request.getContextPath()%>/images/window.png">&nbsp;<span class="x_content_title">标段基本情况列表</span>
			    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'"  onclick="javascript:addNew();">新增</a>  
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'" onclick="javascript:deleteSelections();">删除</a> 
		    </div> 
		</div>  
		<div data-options="region:'center',border:true">
		     <table id="myTable"></table>
	    </div>
	</div>
</body>
</html>