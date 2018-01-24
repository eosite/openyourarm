<%@page import="com.glaf.chinaiss.util.JSONConvertUtil"%>
<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page import="com.glaf.chinaiss.model.FileAtt"%>
<%@page import="java.util.List"%>
<%@page import="com.glaf.chinaiss.query.FileAttQuery"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.glaf.core.context.ContextFactory"%>
<%@page import="com.glaf.chinaiss.service.FileAttService"%>
<%@page import="com.glaf.core.config.Environment"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
	
	String useDatabase = request.getParameter("useDatabase")==null?"default":request.getParameter("useDatabase");
	String inspectionId = request.getParameter("inspectionId")==null?"":request.getParameter("inspectionId");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查看文件</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	jQuery(function(){
		jQuery("#sheetTable").datagrid({
			url:'<%=request.getContextPath()%>/rs/viewReportRest/getCellSheetName?useDatabase=<%=useDatabase%>&inspectionId=<%=inspectionId%>',
			fit:true,
			fitColumns:true,
			nowrap: false,
			striped: true,
			collapsible:true,
			singleSelect:true,
			remoteSort: false,
			idField:'id',
			columns:[[
				{field:"id",title:"id",width:"100",hidden:true},
				{field:"dotUseId_enc",title:"dotUseId_enc",width:"100",hidden:true},
				{field:"name",title:"表格名称"}
			]],
			onLoadSuccess:function(){
				jQuery("#sheetTable").datagrid("selectRow",0);
			},
			onSelect:function(rowIndex,rowData){
				showCellData(rowIndex,rowData);
			},
			rownumbers:true
		});
	});
	
	/**
	 * 点击文件列表后在右侧iframe中显示内容
	 */
	function showCellData(rowIndex,rowData){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/cellTable/showTable?method=dotUse&useDatabase=<%=useDatabase%>&fileID="+rowData.dotUseId_enc;
		document.getElementById("showDataFrame").src = url;
	}
	
</script>
</head>
<body  class="easyui-layout">
	    <div data-options="region:'west',title:'表格列表'" style="width:250px;">
	    	<table id="sheetTable" ></table>
	    </div>  
	    <div id="centerDIV" data-options="region:'center'" style="padding:0px;background:#eee;">
			<iframe id="showDataFrame" style="border: 0;" width="100%"  height="100%" ></iframe>
	    </div>  
</body>
</html>