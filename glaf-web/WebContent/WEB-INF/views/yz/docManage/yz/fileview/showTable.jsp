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
<%@include file="../inc/script.jsp" %>
<body>
	<OBJECT id="CellWeb" style="left:0px;width:100%;top:0px;height:0px;background:#eee;"
		codeBase="<%=request.getContextPath()%>/cellPlugin/CellWeb5.CAB#version=5,3,9,16"
		classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
		<PARAM NAME="_Version" VALUE="65536">
		<PARAM NAME="_ExtentX" VALUE="10266">
		<PARAM NAME="_ExtentY" VALUE="7011">
		<PARAM NAME="_StockProps" VALUE="0">
	</OBJECT>
</body>
<script type="text/javascript">
function DetectActiveX(){
	try {
		new ActiveXObject('CELLWEB5.CellWebCtrl.1');
	} catch (e) {
		alert("未安装华表插件，请先下载安装！");
		return false;
	}
	return true;
}

	<%if((Boolean)request.getAttribute("hasContent")){%>
	if(DetectActiveX()==true){
		CellWeb.Login('炎晟软件','','11100101387','1120-1235-0064-6005');
		CellWeb.LocalizeControl(2052);// (&H804);
		CellWeb.style.height = "100%";
		var flag = CellWeb.OpenFile("<%=request.getAttribute("filePath")%>","");
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
	<%}%>
</script>
</html>