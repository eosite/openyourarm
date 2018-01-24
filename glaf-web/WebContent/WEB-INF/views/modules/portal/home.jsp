<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.glaf.base.utils.*" %>
<%@ page import="com.glaf.base.business.*" %>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*" %>
<%@ page import="com.glaf.core.util.*" %>
<%@ page import="com.glaf.ui.model.*" %>
<%@ page import="com.glaf.base.modules.sys.model.*" %>
<%@ page import="com.glaf.base.modules.sys.service.*" %>
<%
	 
    String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);

	//String userId = RequestUtils.getActorId(request);
 	//SysApplicationService sysApplicationService = ContextFactory.getBean("sysApplicationService");
	//long appId = RequestUtils.getLong(request, "appId", 3);
	//String scripts = "";
	//try{
	  //JSONArray array = sysApplicationService.getUserMenu(appId, userId);
	  //scripts = array.toString('\n');
	//}catch(Exception ex){
	//	ex.printStackTrace();
	//}
	String scripts = (String) request.getAttribute("scripts");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=SystemConfig.getString("res_system_name")%></title>
<link href="<%=request.getContextPath()%>/scripts/layout/css/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/${theme}/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/icons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/layout/js/outlook3.js'> </script>
<style type="text/css">
   .icon-gears {
        background:url('<%=request.getContextPath()%>/images/config.gif') no-repeat ;
   }
   .icon-base {
        background:url('<%=request.getContextPath()%>/images/folder-open.gif') no-repeat ;
   }
</style>
<script type="text/javascript">

   var contextPath = "<%=request.getContextPath()%>";

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

   function changeTheme(){
	    $('#w').window({
                title: '设置主题',
                modal: true,
                shadow: true,
                closed: true,
                resizable:false
            });
   }

    $(function() {
        changeTheme();
        $('#editTheme').click(function() {
            $('#w').window('open');
        });         
		
	    $('#btnCancel').click(function(){closePwd();});

		$('#loginOut').click(function() {
             $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
                  if (r) {
                        location.href = '${contextPath}/mx/login/logout';
                    }
                });
         })

    });

	var _mxm_ = {
	  "children":  <%=scripts%>
    };
	function colseNorth(ele){
		var pic = $(ele);
		if(!$('#cc').layout('panel','north').panel('options').minimized){
			$('#cc').layout('panel','north').panel('minimize');
		//	$('#cc').layout('resize','west');
			$('#cc').layout('resize','center');
			pic.css('background',"url('<%=request.getContextPath()%>/scripts/easyui/themes/bootstrap/images/accordion_arrows.png') no-repeat scroll -16px 0 rgba(0, 0, 0, 0)");
		}else{
			$('#cc').layout('panel','north').panel('open'); 
			$('#cc').layout('resize','center');
		//	$('#cc').layout('resize','west'); 
			pic.css('background',"url('<%=request.getContextPath()%>/scripts/easyui/themes/bootstrap/images/accordion_arrows.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0)");
		}
	}
</script>
<style type="text/css">
	.panel-header{
		height:16px;
	}
</style>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no" id="cc">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="<%=request.getContextPath()%>/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div>
</noscript>

<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="${contextPath}/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>

<div region="north" split="true" border="false" style="overflow: hidden; height: 63px;
        background: url(${contextPath}/themes/${theme}${userTheme.background}) #7f99be repeat-x center 50%;
        line-height: 63px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">
		欢迎 <%=RequestUtils.getLoginContext(request).getUser().getName()%> 
		<a href="#" id="editTheme" onclick="javascript:changeTheme();">切换主题</a>
		<a href="<%=request.getContextPath()%>/cellPlugin/CellWeb5_plugin_setup.zip" id="downCellPlugin">华表插件下载</a>
		<a href="#" id="loginOut">退出</a>
        </span>
        <span style="padding-left:10px; font-size: 24px; ">
		<img src="<%=request.getContextPath()%><%=SystemConfig.getString("res_logo","/images/logo.png")%>" border="0" id="mb" align="absmiddle" /> 
		<span class="sys_name" style="padding-left:10px; font-size: 24px; ">
		<%=SystemConfig.getString("res_system_name")%></span>&nbsp;
	   <span class="sys_version"><%=SystemConfig.getString("res_version")%></span>
      </span>
</div>

<div id="mmc" class="menu-content" style="width:255px;height: 600px">  
			<div style="width:250px;height: 594px;">
			   <div id="nav"  >
			     <!--  导航内容 -->
					
				</div>
			</div>
		</div>  
<script type="text/javascript">
$('#mb').menubutton({   
    menu: '#mmc'  
});  
</script>
<div region="south" split="true" class="south-backgroud" style="height: 30px;   ">
        <div class="footer"><%=SystemConfig.getString("res_copyright")%></div>
</div>

<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="我的桌面" style="padding:0px;overflow:hidden; color:red; " >
				<iframe
					src='${contextPath}<%=com.glaf.base.utils.LoginContextUtil.getIndexUrl(request)%>'
					width='100%' height='100%' frameborder='0' scrolling='no' noResize></iframe>
			</div>
		</div>
		<div style="position: absolute;right: 10px;top: 5px;width:16px;height:16px;
			background: url('<%=request.getContextPath()%>/scripts/easyui/themes/bootstrap/images/accordion_arrows.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0)" 
			onclick="colseNorth(this)">
		</div>
</div>
    
    
   <%@ include file="theme.jsp"%>

<%
	UserTheme userTheme = (UserTheme)request.getAttribute("userTheme");
	if(userTheme.getCourse()==null || userTheme.getCourse() == 0  ){
%>
	<div id ="tishi" style="width:100%;height:100%;float: left;z-index: 3;
		position: absolute;opacity:0.7; filter:alpha(opacity=0.7);background-color: gray;">

		<div style="background: url('${contextPath}/images/iconpng2.png');width:80px;height:80px;
			left:13px;top:25px;position:absolute;"></div>
		<div style="position: absolute;left: 88px;top: 83px;color: red;color: white;font-size: 30px;">鼠标移入展开菜单</div>
		<div style="background: url('${contextPath}/images/iconpngc2.png');width:40px;height:40px;
			position: absolute;right: 30px;top: 42px;" onclick="hiddenTishi();"></div>
	</div>
	<script type="text/javascript">
		function hiddenTishi(){
			document.getElementById('tishi').style.display = 'none';
			var userThemeId = $('#userThemeId').val();
			var param = {'userThemeId':userThemeId};
			 jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/my/home/colseCourse',
				   dataType:  'json',
				   data:param,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					  //  location.reload();
				   }
			 });
		}
	</script>
<%
	}
%>
	<div id="mm" class="easyui-menu" style="width:150px;">
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

</body>
</html>
<!--views\modules\portal\home.jsp-->