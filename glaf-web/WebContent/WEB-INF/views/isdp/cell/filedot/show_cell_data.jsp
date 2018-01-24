<%@page contentType="text/html; charset=UTF-8" %>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="renderer" content="ie-comp">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看表格</title>
<%
if(request.getAttribute("contextPath")==null){
	String contextPath = request.getContextPath();
	if(session.getAttribute("yz_path")!=null){
		contextPath = (String)session.getAttribute("yz_path");
	}
	request.setAttribute("contextPath", contextPath);
}
%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="renderer" content="ie-comp">
<link href="${contextPath }/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${contextPath }/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath }/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="${contextPath }/icons/styles.css">
<link rel="stylesheet" type="text/css" href="${contextPath }/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="${contextPath }/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath }/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath }/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath }/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath }/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${contextPath }/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="${contextPath }/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<body class="easyui-layout">
	<div data-options="region:'west',title:'表格列表',split:true" style="width:300px;">
		<table id="cellGrid"></table>
	</div>
	<div data-options="region:'center'" style="padding:5px;">
	<OBJECT id="CellWeb" style="left:0px;width:100%;top:0px;height:0px;background:#eee;"
		codeBase="${contextPath }/cellPlugin/CellWeb5.CAB#version=5,3,9,16"
		classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
		<PARAM NAME="_Version" VALUE="65536">
		<PARAM NAME="_ExtentX" VALUE="10266">
		<PARAM NAME="_ExtentY" VALUE="7011">
		<PARAM NAME="_StockProps" VALUE="0">
	</OBJECT>
	</div>
</body>
<script type="text/javascript">
$(function(){
	$("#cellGrid").datagrid({
		width:'100%',
		height:'auto',
		method:'post',
		url:'${contextPath}/rs/isdp/cellFillForm/tableList',
		fitColumns:false,
		striped:true,
		rownumbers:true,
		singleSelect:true,
		nowrap:false,
		queryParams:{
			indexId:'${param.indexId}',
			data_id:'${param.data_id}',
			fileDotFileId:'${param.fileID}',
			systemName:'${param.systemName}'
		},
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'fileDotFileId',title:'fileDotFileId',hidden:true},
			{field:'name',title:'名称',align:'left'}
		]],
		onLoadSuccess:function(data){
			if(data.rows.length>0){
				$("#cellGrid").datagrid('selectRow',0);
			}
		},
		onSelect:function(rowIndex,rowData){
			openCell(rowData.id);
		}
	});
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

function openCell(fileID){
	var url = "${contextPath}/rs/isdp/dotUse/showCellContent?systemName=${param.systemName}&fileID="+encodeURI(fileID);
	if(DetectActiveX()==true){
		CellWeb.Login('炎晟软件','','11100101387','1120-1235-0064-6005');
		CellWeb.LocalizeControl(2052);// (&H804);
		CellWeb.style.height = "100%";
		var flag = CellWeb.OpenFile(url,"");
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
</script>
</html>