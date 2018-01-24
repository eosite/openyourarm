<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
	com.glaf.base.modules.sys.model.BaseDataInfo themeInfo=session.getAttribute("theme")!=null?(com.glaf.base.modules.sys.model.BaseDataInfo)session.getAttribute("theme"):null;
	String theme = themeInfo!=null?themeInfo.getExt3():com.glaf.core.util.RequestUtils.getTheme(request);
	String homeTheme = themeInfo!=null?themeInfo.getExt2():com.glaf.core.util.RequestUtils.getHomeTheme(request);
	request.setAttribute("homeTheme", homeTheme);
	String layoutTheme = themeInfo!=null?themeInfo.getExt1():com.glaf.core.util.RequestUtils.getLayoutTheme(request);
	request.setAttribute("layoutTheme", layoutTheme);
	String indexUrl = com.glaf.base.utils.LoginContextUtil.getIndexUrl(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容区</title>
<link href="${contextPath}/scripts/home/${homeTheme}/css/nav_style.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/scripts/home/${homeTheme}/css/select.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
#cdiv {
position:absolute;
width:30px;height:30px;
z-index:9999;
top:50%;
left:10px;
}
.removea{
font-weight: bolder;
color: red;
cursor: pointer;
}
.removediv{
position:absolute;
width:6px;height:6px;
z-index:200;
float: left;
}
</style>

<script type="text/javascript"
	src="${contextPath}/scripts/jquery.min.js"></script>
<script language="JavaScript" src="${contextPath}/scripts/home/home.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/home/${homeTheme}/js/jquery.idTabs.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/home/${homeTheme}/js/select-ui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(e) {
		settingTabHeight();
	});
	function settingTabHeight() {
		var winHeight = $(window).height();
		$("#mainContent").height(winHeight-40);
		$("#mainContent iframe").height(winHeight - 40);
	}
	$(document).ready(function(){
	$("body").append("<div id=\"cdiv\">"+
	   "<img id=\"expd_img\"  src=\"${contextPath}/scripts/home/${homeTheme}/images/expand.gif\" width=\"18\" style=\"text-align:center;vertical-align:middle;margin: auto;cursor: pointer;display: none;\" onclick=\"expand()\"/>"+"</div>");
	});
	function showExpd(){
	   $("#expd_img").css("display","");
	}
	function expand(){
          window.parent.document.getElementsByTagName("frameset")[1].cols="187,*";
          $("#expd_img").css("display","none");
        }
    
	function goHome() {
        var url="${webPath}<%=com.glaf.base.utils.LoginContextUtil.getIndexUrl(request)%>";
		$("#mainFrame").attr("src",url);
	}
</script>
</head>

<body>
<div>
	<div class="formbody">
	    <div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
	    <li><a href="#" onclick="goHome()">首页</a></li>
	    </ul>
   	    <div class="topmenu">
		<ul>
			
		</ul>
		</div>
		<script type="text/javascript">
     var _mxm_ = {
				  "children":  ${scripts}
		    };
		function initLeftMenu(nodeId){
		   var nodes =_mxm_.children.children;
		   self.parent.leftFrame.menudata=_mxm_.children;
		   self.parent.leftFrame.initLeftTree(nodes,nodeId);
		}
		var defaultOpenNode;
		function initMenuData(){
			var nodes =_mxm_.children.children ;	
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				if(i==0)
				{
				  defaultOpenNode=node.id;
				}
				createNode(node);
			}
		}
		function createNode(node){
			var lidom=$("<li></li>");
			var adom=$("<a></a>");
			adom.attr("href","#");
			adom.attr("onclick","javascript:initLeftMenu("+node.id+")");
			adom.attr("target","rightFrame");
			var imgdom=$("<img></img>");
			if(node.icon&&node.icon!=""){
			    imgdom.attr("src","${contextPath}"+node.icon);
			    imgdom.attr("title",node.text);
			    imgdom.css("vertical-align","middle");
			    imgdom.css("margin-right","3px");
			    adom.append(imgdom);
			}
			//else{
			 //   imgdom.attr("src","${contextPath}/images/star.png");
			//}
			adom.append(node.text);
			lidom.append(adom);
			$(".topmenu ul").append(lidom);
			console.log($(".topmenu ul"));
		}
		initMenuData();
		//打开默认模块
		if(defaultOpenNode!=null&&defaultOpenNode!="")
		initLeftMenu(defaultOpenNode);
</script>
	    </div>
		<div id="mainContent" style="width:100%;height:100%;">
				<iframe id="mainFrame"
					src="<%=indexUrl.startsWith("http")?indexUrl:(context+indexUrl)%>"
					style="width:100%;height:100%" />
		</div>
	</div>
</div>
</body>
</html>
