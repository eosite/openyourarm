<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page import="com.glaf.base.security.BaseIdentityFactory"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
String webPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

JSONArray jsonArray = BaseIdentityFactory.getRoleMenu(RequestUtils.getServiceUrl(request), "GISMENU");
if(jsonArray.length()>0){
	jsonArray = jsonArray.getJSONObject(0).getJSONArray("children");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平面地图</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
var mapId = 0;
jQuery(function(){
	document.myActiveX.Html = this; 
	var myActiveX = document.getElementById("myActiveX");
	myActiveX.OpenMap();
});
</script>
</head>
<body style="margin: 1px"> 
	<div style="margin:0;"></div>
	<div id="objectDiv" style="z-index:-100;position: relative" >
	<object id="myActiveX" classid="clsid:2440035E-E5BD-4C45-ADEB-3947822BA5B8" height="93%" width="100%" border="0" >
		<param name="MapFilePath" value="C:\Program Files\Microsoft\shp2" />
		<param name="MHeight" value="490" />
		<param name="MWidth" value="1000" />
    </object>
    </div>
    <div align="center" style="margin-top:10px">
    	<a href="<%=request.getContextPath()%>/software/MapEngineOCXSetup.zip">地图插件下载</a>
    	<a href="<%=request.getContextPath()%>/software/shp2.zip">地图数据下载</a>
    </div>
	<script type="text/javascript" for="myActiveX" event="ClickEvent(type,data)">
	//显示弹出菜单
	if(type == 1){
		if(data!=-1){
			mapId = data;
			myActiveX.OpenMenu('<%=jsonArray.toString()%>');
		}
	}else{
		var _data = eval("("+data+")");
		//点击菜单后返回值，根据点击的菜单弹出窗体
		var url=_data.url+"&mapId="+mapId;
		
		var height = window.screen.height;
		var width = window.screen.width;
		if(_data.code=="baseinfo"){
			//基本情况
			height = "450px";
			width = "800px";
		}else if(_data.code=="file" || _data.code=="statistical" || _data.code=="constructioProgress"){
			//文件查询、统计查询、施工进度情况
			height = window.screen.height;
			width = window.screen.width;
		}
		window.showModalDialog(url,"文件查看","dialogHeight="+height+";dialogWidth="+width+";center=true;status=no;scroll=yes");
	}
	</script> 
</body>
</html>