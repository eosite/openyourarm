<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
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
	String hideSysName=themeInfo!=null?themeInfo.getExt4():"0";
	//获取logo图片路径
	String logoPicPath=themeInfo!=null?themeInfo.getDesc():"";
	if(StringUtils.isEmpty(logoPicPath)){
		logoPicPath="scripts/home/"+homeTheme+"/images/chissLOGO.png";
	}else{
		logoPicPath=logoPicPath.trim();
	}
	request.setAttribute("logoPicPath", logoPicPath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页-顶部</title>
<link href="${contextPath}/scripts/home/${homeTheme}/css/style.css" rel="stylesheet"
	type="text/css" />
<script language="JavaScript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})


	function goHome() {

		self.parent.rightFrame.$("#home").trigger('click');
	}
</script>


</head>

<body
	style="background:url(${contextPath}/scripts/home/${homeTheme}/images/topbg.gif) repeat-x;">
	<div class="topleft">
		<a href="${contextPath}/mx/my/home" target="_top"><img
			src="${contextPath}/${logoPicPath}" title="系统首页" /></a>
	</div>
	<!--<ul class="nav">
    
    </ul>  -->
    <div class="user">
    			<span>${username}，欢迎您！</span> <!-- <i>消息</i> <b>5</b> -->
    	</div>
	<div class="systitle">
			  <%=!hideSysName.equals("1")?SystemConfig.getString("res_system_name"):""%>
	</div>
	<div class="topright">
		<ul>
		     <%
			  if(!("false").equals(SystemConfig.getString("theme_show"))){
			%>
			<li><span><img
					src="${contextPath}/scripts/home/${homeTheme}/images/palette.png" title="切换主题"
					/></span><a class="bounceIn dialog" href="#" onclick="javascript:openThemeWin();">切换主题</a></li>
			<%
			  }
			%>
			<%
			  if(SystemConfig.getString("helper_link")!=null&&!SystemConfig.getString("helper_link").equals("")){
			%>
			<li><span><img
					src="${contextPath}/scripts/home/${homeTheme}/images/help.png" title="帮助"
					class="helpimg" /></span><a href="#" id="helperButton">帮助</a></li>
			<%
			  }
			%>
			<%
			  if(SystemConfig.getString("about_link")!=null&&!SystemConfig.getString("about_link").equals("")){
			%>
			<li><a href="#" id="aboutButton">关于</a></li>
			<%
			  }
			%>
			<li><a href="${contextPath}/mx/login/logout" target="_parent">退出</a></li>
		</ul>
	</div>
	<script type="text/javascript">
	  function openThemeWin(){
		 parent.parent.openThemeDialog();
	}

	$(function(){
	 	$('#helperButton').click(function(){
            var helper_link = '<%=SystemConfig.getString("helper_link")%>';
            if(helper_link){
                window.open(helper_link);
                // window.location.href = message_link;
            }
        })

		$('#aboutButton').click(function(){
            var about_link = '<%=SystemConfig.getString("about_link")%>';
            if(about_link){
                window.open(about_link);
                // window.location.href = message_link;
            }
        })
	 })
</script>
</body>
</html>

