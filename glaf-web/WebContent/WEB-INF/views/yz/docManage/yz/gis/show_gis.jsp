<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page import="com.glaf.base.security.BaseIdentityFactory"%>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);

JSONArray jsonArray = BaseIdentityFactory.getRoleMenu(RequestUtils.getServiceUrl(request), "GISMENU");
if(jsonArray.length()>0){
	jsonArray = jsonArray.getJSONObject(0).getJSONArray("children");
}

JSONArray menuArray = new JSONArray();
for(int i=0;i<jsonArray.length();i++){
	JSONObject jobject = jsonArray.getJSONObject(i);
	JSONObject menu = new JSONObject();
	menu.put("ActionID", jobject.getInt("id"));
	menu.put("ActionName",jobject.get("name"));
	menu.put("ActionUrl",jobject.get("url"));
	menu.put("ImgUrl","");
	
	menuArray.put(menu);
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GIS地图</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/Silverlight.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<style type="text/css">
html, body {
 height: 100%;
 overflow: auto;
}
body {
 padding: 0;
 margin: 0;
}
#silverlightControlHost {
 height: 100%;
 text-align:center;
}
</style>
<script type="text/javascript">
//点击菜单被动触发功能 ActionID:菜单编号,place:桩号
function MenuItemClick(ActionID,index_db, index_id,ActionUrl) {
	var width = window.screen.width;
	var height = window.screen.height;
	window.showModalDialog(ActionUrl+"&systemName="+index_db+"&index_id="+index_id,"查看","dialogHeight="+height+";dialogWidth="+width+";center=true;status=no;scroll=yes");
}

//显示统计信息, section以'|'为分隔符，标段|类型|单位名称, 如：A1|1|K36+100-K42+000路面工程
function ShowData(section) {
	var width = window.screen.width;
	var height = window.screen.height;
	var param = section.split("|");
	window.showModalDialog("<%=request.getContextPath()%>/mx/docManage/yz/gis/staDanwei?systemName="+param[0]+"&index_id="+param[2],"查看","dialogHeight="+height+";dialogWidth="+width+";center=true;status=no;scroll=yes");
}

//传入自定义菜单列表
var slCtl = null;
function SilverlightLoaded(sender) {
    slCtl = sender.getHost();
    slCtl.Content.mainForm.OpenPlaneMap("<%=request.getAttribute("gisURL")%>");
    slCtl.Content.mainForm.CustomerMenuData(JSON.stringify(<%=menuArray%>));
}
</script>
</head>
<body style="margin: 0px">
	<form id="form1" runat="server" style="height:100%">
    <div id="silverlightControlHost">
        <object id="xamlObject" data="data:application/x-silverlight-2," type="application/x-silverlight-2" width="100%" height="100%">
		  <param name="source" value="<%=request.getContextPath()%>/ClientBin/GISDemo.xap"/>
		  <param name="onError" value="onSilverlightError" />
          <param name="onLoad" value="SilverlightLoaded" />
		  <param name="background" value="white" />
		  <param name="minRuntimeVersion" value="4.0.50826.0" />
		  <param name="autoUpgrade" value="true" />
		  <a href="http://go.microsoft.com/fwlink/?LinkID=149156&v=4.0.50826.0" style="text-decoration:none">
 			  <img src="http://go.microsoft.com/fwlink/?LinkId=161376" alt="获取 Microsoft Silverlight" style="border-style:none"/>
		  </a>
	    </object><iframe id="_sl_historyFrame" style="visibility:hidden;height:0px;width:0px;border:0px"></iframe></div>
    </form>
</body>
</html>