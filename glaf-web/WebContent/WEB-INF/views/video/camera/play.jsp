<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频监控</title>
</head>
<body style="margin-top:0px;" <c:if test="${empty enableFunction}"> onload="fnOpen();" </c:if> onunload="fnClose();">
<object id="x" width="100%" height="90%" classid="clsid:8d7d8518-ca58-4863-b94d-3c616fda7b35"></object>
<c:if test="${!empty enableFunction}">
 <hr />
 <input type="button" value="开启视频" onclick="fnOpen()" />  
 <input type="button" value="向左" onmousedown="fnStart('LEFT')" onmouseup="fnStop('LEFT')" />
 <input type="button" value="向右" onmousedown="fnStart('RIGHT')" onmouseup="fnStop('RIGHT')" />
 <input type="button" value="向上" onmousedown="fnStart('UP')" onmouseup="fnStop('UP')" />
 <input type="button" value="向下" onmousedown="fnStart('DOWN')" onmouseup="fnStop('DOWN')" />
 <input type="button" value="左上" onmousedown="fnStart('UPLEFT')" onmouseup="fnStop('UPLEFT')" />
 <input type="button" value="左下" onmousedown="fnStart('DOWNLEFT')" onmouseup="fnStop('DOWNLEFT')" />
 <input type="button" value="右上" onmousedown="fnStart('UPRIGHT')" onmouseup="fnStop('UPRIGHT')" />
 <input type="button" value="右下" onmousedown="fnStart('DOWNRIGHT')" onmouseup="fnStop('DOWNRIGHT')" />
 <input type="button" value="放大" onmousedown="fnStart('ZOOMIN')" onmouseup="fnStop('ZOOMIN')" />
 <input type="button" value="缩小" onmousedown="fnStart('ZOOMOUT')" onmouseup="fnStop('ZOOMOUT')" />
 <input type="button" value="缩小焦距" onmousedown="fnStart('FOCUSNEAR')" onmouseup="fnStop('FOCUSNEAR')" />
 <input type="button" value="放大焦距" onmousedown="fnStart('FOCUSFAR')" onmouseup="fnStop('FOCUSFAR')" />
 <input type="button" value="光圈扩大" onmousedown="fnStart('IRISSTARTUP')" onmouseup="fnStop('IRISSTARTUP')" />
 <input type="button" value="光圈缩小" onmousedown="fnStart('IRISSTOPDOWN')" onmouseup="fnStop('IRISSTOPDOWN')" />
 <input type="button" value="调亮" onmousedown="fnStart('LIGHT')" onmouseup="fnStop('LIGHT')" />
 <input type="button" value="调暗" onmousedown="fnStart('WIPER')" onmouseup="fnStop('WIPER')" />
 <input type="button" value="自动" onmousedown="fnStart('AUTO')" onmouseup="fnStop('AUTO')" />
 <input type="button" value="拍照" onclick="fnCapturePicture();" />
</c:if>
 <script type="text/javascript">

 var x_height = Math.floor(window.screen.height * 0.78);
 var x_width = Math.floor(window.screen.width * 0.95);
 
 document.getElementById("x").height=x_height;
 //alert(document.getElementById("x").height);

 var fnOpen = function(){
     var x = document.getElementById("x");
     //x.Start("这是js中的参数");
	 var config = '<%=request.getAttribute("json")%>';
	 var ret = x.Start(config);
	 if(ret){
	    //alert("视频调用成功！");
	 }
 }

 var fnClose = function(){
     var x = document.getElementById("x");
     //x.Start("这是js中的参数");
	 //var config = '<%=request.getAttribute("json")%>';
	 x.Stop();
 }

  var fnLeftStart = function(){
     var x = document.getElementById("x");
     //x.Start("这是js中的参数");
	 /*
	 enCommand参数内容
	             UP, DOWN, LEFT, RIGHT,
            UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT,
            ZOOMIN, ZOOMOUT, FOCUSNEAR, FOCUSFAR,
            IRISSTARTUP, IRISSTOPDOWN, LIGHT, WIPER,
            AUTO
     enAction参数内容
	 START, STOP
	 */
	 var cmd = "{ \"enCommand\": \"LEFT\", \"enAction\": \"START\"}";
	 x.Yun(cmd);
 }

 var fnLeftStop = function(){
     var x = document.getElementById("x");
     //x.Start("这是js中的参数");
	 var cmd = "{ \"enCommand\": \"LEFT\", \"enAction\": \"STOP\"}";
	 x.Yun(cmd);
 }

  var fnStart = function(orientation){
     var x = document.getElementById("x");
	 var cmd = "{ \"enCommand\": \""+orientation+"\", \"enAction\": \"START\"}";
	 x.Yun(cmd);
  }

 var fnStop = function(orientation){
     var x = document.getElementById("x");
	 var cmd = "{ \"enCommand\": \""+orientation+"\", \"enAction\": \"STOP\"}";
	 x.Yun(cmd);
 }

 var fnCapturePicture = function(){ 
	var x = document.getElementById("x"); 
	//x.Start("这是js中的参数"); 
	var path = x.CapturePicture();//返回本地截图文件路径 
	alert(path); 
	//var fso = new ActiveXObject("Scripting.FileSystemObject"); 
    //window.open(path);
	//alert("fso:"+fso); 
	//if(fso.FileExists(path)){
	//	alert("文件已经保存到C:\\temp\\12.jpg");
	//	fso.CopyFile(path, "C:\\temp\\12.jpg"); 
	//}
}


 </script>
 </body>
 </html>