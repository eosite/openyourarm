<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang3.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
	request.setAttribute("nowDate", new java.util.Date());
	LoginContext loginContext = com.glaf.core.util.RequestUtils.getLoginContext(request);
	String scripts = (String) request.getAttribute("scripts");
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=SystemConfig.getString("res_system_name")%></title>
<link href="<%=request.getContextPath()%>/scripts/layout/css/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/${theme}/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/icons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui.simple.extend.js"></script> 
<style type="text/css">
   .icon-gears {
        background:url('<%=request.getContextPath()%>/images/config.gif') no-repeat ;
   }
   .icon-base {
        background:url('<%=request.getContextPath()%>/images/folder-open.gif') no-repeat ;
   }
</style>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

    var openTabSize = 0;  

	$(function() {
        //changeTheme();
        $('#editTheme').click(function() {
            $('#w').window('open');
        });  
	});	

    
	function mopenTabs(title, menuId){
		if ($('#tabs').tabs('exists', title)){
			$('#tabs').tabs('select', title);
		} else {
			//alert(menuId);
			var url = "${contextPath}/mx/my/menu/jump?id="+menuId;
			addTab(title, url, "icon-gears");
		}
	}

	function openTabs(subtitle,menuId){
	  if(!$('#tabs').tabs('exists',subtitle)){
		//alert(menuId);
		openTabSize = openTabSize + 1;
		openTabSize = $("#tabs").tabs("tabs").length;
		if(openTabSize > 10){
			$('#tabs').tabs('close', 1);//关闭第二个,第一个为我的桌面，不能关闭
		}
		var url = "${contextPath}/mx/my/menu/jump?id="+menuId;
		//jQuery('#cc').attr('src', url);
		
		jQuery('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:"icon-gears"
		});
		
	  }else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	  }
	tabClose();
  }

	function openThemeWin(){
		 jQuery('#w').window('open');
	}
	
	function setMyTheme(){
		   var theme = $('#theme').val();
		   var layoutModel = $('#layoutModel').val();
		   var background = $('#background').val();
		   var userThemeId = $('#userThemeId').val();
		   var backgroundType = $('#backgroundType').val();
		   var param = {'themeStyle':theme,'layoutModel':layoutModel , 'background':background 
				   ,'userThemeId':userThemeId,'backgroundType':backgroundType} ;
		   jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/my/home/setTheme',
					   dataType:  'json',
					   data:param,
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						    location.reload();
	                        //document.getElementById("themeui").href=contextPath+"/scripts/easyui/themes/"+theme+"/easyui.css";
					   }
				 });
	   }

 
    function showDesktop(){
		  var url = "${contextPath}/mx/user/portal?easyuiPortal=true";
          $('#cc').attr('src', url);
	 }

  	 function relogin(){
        if(confirm("您确定要重新登录吗？")){
			var link = '${contextPath}/mx/login/logout';
			self.location.href = link;
		}
	}
 
    jQuery(document).ready(function(){
        jQuery('#w').window('close'); 
	});
	
	function colseNorth(ele){
		var pic = $(ele);
		if(!$('#cc').layout('panel','north').panel('options').minimized){
			$('#cc').layout('panel','north').panel('minimize');
			$('#cc').layout('resize','west');
			$('#cc').layout('resize','center');
			pic.css('background',"url('<%=request.getContextPath()%>/scripts/easyui/themes/bootstrap/images/accordion_arrows.png') no-repeat scroll -16px 0 rgba(0, 0, 0, 0)");
		}else{
			$('#cc').layout('panel','north').panel('open'); 
			$('#cc').layout('resize','center');
			$('#cc').layout('resize','west'); 
			pic.css('background',"url('<%=request.getContextPath()%>/scripts/easyui/themes/bootstrap/images/accordion_arrows.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0)");
		}
	}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" fit="true" scroll="no" id="cc">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="${contextPath}/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>

	<div id="loading-mask"
		style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; background: #D2E0F2; z-index: 20000">
		<div id="pageloading"
			style="position: absolute; top: 50%; left: 50%; margin: -120px 0px 0px -120px; text-align: center; border: 2px solid #8DB2E3; width: 200px; height: 40px; font-size: 14px; padding: 10px; font-weight: bold; background: #fff; color: #15428B;">
			<img src="${contextPath}/images/loading.gif"
				align="absmiddle" /> 正在加载中,请稍候...
		</div>
	</div>

	<div region="north" split="false" border="false"
		style="overflow: hidden; height: 63px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr
				style="overflow: hidden; height: 63px; background: url(${contextPath}/themes/${theme}/images/top_bar_bg.jpg)">
				<td width="5%" align="center" valign="middle"><img
					src="<%=request.getContextPath()%>/images/logo.png"
					border="0"></td>
				<td width="35%" align="left" valign="middle"><span
					class="sys_name" style="padding-left:10px; font-size: 24px; "><%=SystemConfig.getString("res_system_name")%></span>
					&nbsp;<span class="sys_version"><%=SystemConfig.getString("res_version")%></span></td>
				<td>&nbsp;</td>
				<td width="5%" valign="middle" align="right"><a href="#"
					id="changeTheme" title="更换主题" onclick="javascript:openThemeWin();"><img
						src="${contextPath}/images/palette48.png"
						border="0"></a></td>
				<td width="10%" align="left" valign="middle">
					<table border="0" cellspacing="0" cellpadding="0"
						style="float: right; margin-right: 8px; margin-top: 5px">
						<tr>
							<td height="25"><img
								src="${contextPath}/images/linkman.gif"
								width="15" height="14" align="absmiddle"> <span
								class="user_name"> <%
							 	if (loginContext.getUser().getName() != null) {
							 		out.println(" " + loginContext.getUser().getName());
							 	}
							 %></span></td>
							<td valign="top"><span onclick="javascript:relogin();"
								style="text-decoration: none; cursor: pointer"><img
									src="${contextPath}/images/logout.gif"
									width="20" height="20" style="position: absolute"> </span></td>
						</tr>
						<tr>
							<td height="22"><img
								src="${contextPath}/images/calendar.png"
								width="15" height="13" align="absmiddle" border="0"><span
								class="curr_date"> <fmt:formatDate value="${nowDate}"
										pattern="yyyy-MM-dd" />&nbsp;
							</span></td>
							<td style="font-weight: bold"><span
								onclick="javascript:relogin();"
								style="text-decoration: none; cursor: pointer; font-size: 14px; color: #ffffff;">
									退出</span></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<div region="south" split="true" style="height: 30px;" class="south-backgroud">
		<div class="footer"><%=SystemConfig.getString("res_copyright")%></div>
	</div>
	<div region="west" split="true" title="导航菜单" style="width: 215px;"
		id="west">
		<ul class="easyui-tree">
			<%
				if (scripts != null) {
					out.println(scripts);
				}
			%>
		</ul>
	</div>
	<div id="mainPanel" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="我的桌面" style="padding: 1px; overflow: hidden; color: red;">
				<iframe id="cc"
					src="${contextPath}/mx/user/portal?easyuiPortal=true"
					width='100%' height='100%' frameborder='0' scrolling='auto' noResize></iframe>
			</div>
		</div>
		<div style="position: absolute;right: 10px;top: 5px;width:16px;height:16px;
			background: url('<%=request.getContextPath()%>/scripts/easyui/themes/bootstrap/images/accordion_arrows.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0)" 
			onclick="colseNorth(this)">
		</div>
	</div>

<%@ include file="theme.jsp"%>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
	</div>

	<iframe id="newFrame" name="newFrame" width="0" height="0" src="<%=request.getContextPath()%>/mx/public/online/remain"></iframe>

<script type="text/javascript">
	var waitTime=1000; //1 秒 
	timer=setInterval("OnTimer()",1000); 
	function OnTimer(){ 
		waitTime=waitTime-1000; 
		if(waitTime==0){ 
			jQuery('#themeWin').window('close'); 
		}
	}
</script>
</body>
</html>
 <!--views\modules\portal\main.jsp-->