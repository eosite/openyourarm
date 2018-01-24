<%@ page contentType="text/html;charset=UTF-8"%>
<%
	request.setAttribute("netVideoActiveX23", request.getContextPath() + "/scripts/NetVideoActiveX23"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>云台控制</title>
<style type="text/css">
html {
	height: 100%;
	width: 100%;
	margin: 0;
}
body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
td {
	color:#222248;
	font-size:12px;
	font-family:Arial, Helvetica, sans-serif;
}
#BodyRight {
	width: 99%;
	height:60%;
	float: right;
	padding:10px;
}
.videoTitle{
	width:99%;
	height:22px;
	padding-top:3px;
	padding-left:5px;
	font-weight:bold;
	background-color:#EBEBEB;
}

#OCXBody {
	width:99%;
	height:650px;
	margin-bottom:5px;
}

#OperatLogBody {
	width:99%;
	height:150px;
	overflow:auto;
	padding:2px;
	border:1px solid #EBEBEB;
}
.smallocxdiv{float:left;display:; width:30%; height:30%;}
</style>
<link rel="stylesheet" type="text/css" href="${netVideoActiveX23}/doc/css/bfq.css"/>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
var id = "${param.id}",contextPath = "${contextPath}";
</script>
<script type="text/javascript" for="HIKOBJECT1" event="SelectWindow()">
	ChangeStatus(1);
</script>
<script type="text/javascript" for="HIKOBJECT2" event="SelectWindow()">
	ChangeStatus(2);
</script>
<script type="text/javascript" for="HIKOBJECT3" event="SelectWindow()">
	ChangeStatus(3);
</script>
<script type="text/javascript" for="HIKOBJECT4" event="SelectWindow()">
	ChangeStatus(4);
</script>
</head>
<body>
<input type="hidden" id="DeviceIP" value="59.61.185.36"
		class="normalinput">
	<input type="hidden" id="DevicePort" value="8002" class="normalinput">
	<input type="hidden" id="DeviceUsername" value="admin"
		class="normalinput">
	<input type="hidden" id="DevicePasswd" value="qazwsx12345"
		class="normalinput">
 <div id="container" >
	<div id="vertical" style=''>
			<div style="border:0px;">
				<div align="left" style="display:none;">
					<ul>
				        <li>设备名称：
				          <input type="text" name="DeviceName" id="DeviceName" class="longinput" readonly>
				          <button class="normalbtn" onClick="ButtonPress('getDevName')">获取</button>
				        </li>
				        <li>通道列表：
				          <select name="ChannelList" id="ChannelList" class="longinput">
				           
				          </select>
				          <button class="normalbtn" onClick="ButtonPress('getDevChan')" >获取</button>
				        </li>
				        <li>
				          <button class="normalbtn" onClick="ButtonPress('Preview:start')" style=" margin-left:60px;">&Delta;开始预览</button>
				          <button class="normalbtn" onClick="ButtonPress('Preview:stop')" style=" margin-left:13px;">&nabla;停止预览</button>
				        </li>
		       		 </ul>
				</div>
				<div id="BodyRight" >
			      <div id="OCXBody">
			        <div class="smallocxdiv" id="NetPlayOCX1">
			          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" codebase="${netVideoActiveX23}/codebase/NetVideoActiveX23.cab#version=2,3,19,1" standby="Waiting..." id="HIKOBJECT1" width="100%" height="100%" name="HIKOBJECT1" ></object>
			        </div>
			        <div class="smallocxdiv" id="NetPlayOCX2">
			          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT2" width="100%" height="100%" name="HIKOBJECT2" ></object>
			        </div>
			        <div class="smallocxdiv" id="NetPlayOCX3">
			          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT3" width="100%" height="100%" name="HIKOBJECT3" ></object>
			        </div>
			        <div class="smallocxdiv" id="NetPlayOCX4">
			          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT4" width="100%" height="100%" name="HIKOBJECT4" ></object>
			        </div>
			        
			        <div style="float:right; display:none;"></div>
			      </div>
			      <div class='videoTitle' align="left">日志（运行结果）
			      	<span style="margin-left:170px; cursor:pointer" onClick="ArrangeWindow(1)">一画面</span>
			      	<span style="margin-left:20px; cursor:pointer" onClick="ArrangeWindow(4)">四画面</span>
			      </div>
			      <div id="OperatLogBody" align="left"></div>
			    </div>
			</div>
			<div style="border:0px;">
				<div class="videoTitle" align="center">设备列表</div>
			    <div align="center" style='height:30%;'>
			    	<table border='0' width='100%' height='auto' id="ChannelList-0" ></table>
			    </div>
			    
				<div class="videoTitle" align="center">云台控制</div>
			    <div align='center' style="width:99%;height:100%;position:absolute;">
					<div id='pic-0' style="left:50px;width:100%; height:160px;position:absolute;display: inline-block;">	
						<div style="z-index:2;height:160px;position:absolute;left:10px;top:10px;">
							<img src="${netVideoActiveX23}/doc/bfq/bfj.png" />
						</div>
						<a class="stop01 k-btn" t='PTZ:stop'></a>
						<a class="sm k-btn" t='PTZ:up'></a>
						<a class="zuos k-btn" t='PTZ:leftup'></a>
						<a class="yous k-btn" t='PTZ:rightup'></a>
						<a class="zuob k-btn" t='PTZ:left'></a>
						<a class="youb k-btn" t='PTZ:right'></a>
						<a class="zuox k-btn" t='PTZ:leftdown'></a>
						<a class="youx k-btn" t='PTZ:rightdown'></a>
						<a class="xm k-btn" t='PTZ:down'></a>
					</div>
					<br>
					<div style='width:100%;top:200px;position:absolute;'>
					    <button class='k-button k-btn' t='zoom:in' >放大</button>
					    <button class='k-button k-btn' t='zoom:out' >缩小</button>
					    <br><br>
					    <button class='k-button' onclick="ButtonPress('CatPic:bmp')" >拍照</button>
					    <button class='k-button' onclick="ButtonPress('Record:start')" >录像</button>
					    <button class='k-button' onclick="ButtonPress('Record:stop')" >停止录像</button>
					    <br><br>
						<button class='k-button k-music'>
							<img class="k-image" alt="music" src="${contextPath }/images/music-false.png" />
						</button>
					</div>
			    </div>
			</div>
	</div>
</div>
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/NetVideoActiveX23/codebase/video.js"></script>
</html>