<%@page import="java.io.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
	
	JSONArray data = (JSONArray)request.getAttribute("jArray");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="renderer" content="ie-comp">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看表格</title>
<%@include file="../../inc/script.jsp" %>
<script type="text/javascript">
jQuery(function(){
	jQuery("#listTable").datagrid({
		onLoadSuccess:function(data){
			jQuery("#listTable").datagrid("selectRow",0);
		},
		onSelect:function(rowIndex,rowData){
			if(rowData.hasFile && DetectActiveX()==true){
				CellWeb.Login('炎晟软件','','11100101387','1120-1235-0064-6005');
				CellWeb.LocalizeControl(2052);// (&H804);
				CellWeb.style.height = "100%";
				var flag = CellWeb.OpenFile(rowData.filePath,"");
				switch(flag){
					case -1:
						alert("文件不存在");
						break;
					case -2:
						alert("文件操作错误");
						break;
					case -3:
						alert("文件格式错误");
						break;
					case -4:
						alert("密码错误");
						break;
					case -5:
						alert("不能打开高版本文件");
						break;
					case -6:
						alert("不能打开特定版本文件");
						break;
					case -99:
						alert("不能下载文件");
						break;
					default:
						break;
				}
				CellWeb.ShowHScroll(1,0);
				CellWeb.ShowVScroll(1,0);
				CellWeb.WorkbookReadonly = true;
			}
		}
	});
	jQuery("#listTable").datagrid("loadData",<%=data%>);
});

function DetectActiveX(){
	try {
		new ActiveXObject('CELLWEB5.CellWebCtrl.1');
	} catch (e) {
		alert("未安装华表插件，请先下载安装！");
		return false;
	}
	return true;
}
</script>
<body class="easyui-layout">
	<div data-options="region:'west',border:true" style="width:250px;">
		<table id="listTable" class="easyui-datagrid" style="width:240px;height:auto" border="0" data-options="idField:'id',singleSelect:true,fitColumns:true,autoRowHeight:true,nowrap:false,rownumbers:true">
    		<thead>
				<tr>
					<th data-options="field:'id',hidden:true">id</th>
					<th data-options="field:'fillFormId',hidden:true">fillFormId</th>
					<th data-options="field:'fileDotFileId',hidden:true">fileDotFileId</th>
					<th data-options="field:'name',width:100">名称</th>
				</tr>
			</thead>
    	</table>
	</div>
	<div data-options="region:'center',border:true">
		<OBJECT id="CellWeb" style="left:0px;width:100%;top:0px;height:0px;background:#eee;"
			codeBase="<%=request.getContextPath()%>/cellPlugin/CellWeb5.CAB#version=5,3,9,16"
			classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
			<PARAM NAME="_Version" VALUE="65536">
			<PARAM NAME="_ExtentX" VALUE="10266">
			<PARAM NAME="_ExtentY" VALUE="7011">
			<PARAM NAME="_StockProps" VALUE="0">
		</OBJECT>
	</div>
</body>
</html>