<%@page import="org.json.JSONArray"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    JSONArray data = (JSONArray)request.getAttribute("data");
    String code = request.getParameter("code");
    String url = request.getContextPath()+"/mx/docManage/yz/fileview/";
    String title = "";
    if(code.equals("wbs")){
    	title = "完成情况检查";
    	url += "viewWBS/show?useDatabase=";
    }else if(code.equals("table")){
    	title = "表格查看";
    	url += "viewTable/show?useDatabase=";
    }else if(code.equals("report")){
    	title = "报审文件查看";
    	url += "viewReport/show?useDatabase=";
    }else if(code.equals("file")){
    	title = "形成文件查看";
    	url += "viewFile/show?useDatabase=";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目进度统计列表</title>
<%@include file="/WEB-INF/views/yz/docManage/yz/inc/script.jsp" %>
<script type="text/javascript">
	jQuery(function(){
		jQuery("#myTable").datagrid({
			//title:"项目列表",
			width:"auto",
			height:"auto",
			fit:true,
			fitColumns:true,
			nowrap: false,
			striped: true,
			collapsible:true,
			singleSelect:true,
			remoteSort: false,
			checkbox: true, 
			idField:"indexId",
			columns:[[
				{field:"ServerName",title:"ServerName",width:"100",hidden:true},
				{field:"DbName",title:"DbName",width:"100",hidden:true},
				{field:"User",title:"User",width:"100",hidden:true},
			    {field:"projName",title:"项目名称",width:"300"},
				{field:"num",title:"项目编号",width:"200",align:"center"},
				{field:"content",title:"说明",width:"400"},
				{field:"indexId",title:"查看",width:"100",align:"center",formatter:function(value,rowData){
					var url = "<%=url%>"+rowData.indexId;
					return "<a href='javascript:view(\""+url+"\")'>查看</a>";
				}}
			]],
			onLoadSuccess:function(){
				jQuery("#myTable").datagrid("selectRow",0);
			},
			rownumbers:true
		});
		
		jQuery("#myTable").datagrid("loadData",<%=data%>);
	});
	
	function view(url){
		<%-- 
		var sFeatures = "width="+window.screen.width+",height="+window.screen.height+",top=0,left=0,toolbar=no,menubar=no,status=no,location=no,help=no,center=yes,resizable=no,scroll=no,depended=yes,alwaysRaised=yes";
		window.open(url,'<%=title%>',sFeatures);
		 --%>
		var width = window.screen.width;
		var height = window.screen.height;
		window.showModalDialog(url,"<%=title%>","dialogHeight="+height+";dialogWidth="+width+";center=true;status=no;scroll=yes");
	}
	
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
 <table id="myTable"></table>
</body>

</html>