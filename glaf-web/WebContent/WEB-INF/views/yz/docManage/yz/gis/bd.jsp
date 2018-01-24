<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<% 
	String id = (String)request.getAttribute("id");
	//id = "0100010000130501122416015Z1";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#panorama {width:100%; height: 100%;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=HNQgLseKGp9mlIWDWnjOLwy8"></script>
	<title>全景图</title>
</head>
<body>
	<div id="panorama" style=""></div>
</body>
</html>
<script type="text/javascript"> 
	var map = new BMap.Map('panorama');
	map.centerAndZoom(new BMap.Point(120.305456, 31.570037), 12);
	map.addTileLayer(new BMap.PanoramaCoverageLayer());

	var panorama = new BMap.Panorama('panorama'); 
	panorama.setPov({heading: -40, pitch: 6});
	panorama.setId('<%=id%>');
	
</script>

