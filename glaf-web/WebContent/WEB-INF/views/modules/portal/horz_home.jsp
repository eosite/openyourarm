<%@page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.BaseDataManager"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	String themeKey="";
	if (request.getAttribute("userTheme") != null) {
		UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
		request.setAttribute("theme", userTheme.getThemeStyle());
		request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
		request.setAttribute("layoutTheme", userTheme.getLayoutModel());
		themeKey=userTheme.getLayoutModel()+"|"+userTheme.getHomeThemeStyle()+"|"+userTheme.getThemeStyle();
	} else {
		String theme = com.glaf.core.util.RequestUtils.getTheme(request);
		request.setAttribute("theme", theme);
		String homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
		request.setAttribute("homeTheme", homeTheme);
		String layoutTheme = com.glaf.core.util.RequestUtils.getLayoutTheme(request);
		request.setAttribute("layoutTheme", layoutTheme);
		themeKey=layoutTheme+"|"+homeTheme+"|"+layoutTheme;
	}
	   BaseDataManager bm=BaseDataManager.getInstance();
	   com.glaf.base.modules.sys.model.BaseDataInfo info=bm.getBaseData(themeKey,"dict_theme");
	   session.setAttribute("theme",info);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${contextPath}/scripts/home/${homeTheme}/css/horz_style.css"
	rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/home/${homeTheme}/css/menu.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
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
.fancybox-overlay.fancybox-overlay-fixed{
	z-index:99999;
}
</style>
<script language="JavaScript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
<script language="JavaScript" src="${contextPath}/scripts/home/home.js"></script>
</head>
<body>
	<%@ include file="theme_default.jsp"%>
	<div id="menuDiv">
		<ul id="menu">
		</ul>
	</div>
	<script type="text/javascript">
		$(".themeLayoutContent").hover(function() {
			$(this).addClass("bgcolor");
		}, function() {
			$(this).removeClass("bgcolor");
		});
		$(".tiptop a").bind("click", function() {
			$("#themeDiv").hide();
		});
	</script>
	<script type="text/javascript">
		function initMenu(selectNode) {
			if ("children" in selectNode) {
				createMenu(selectNode.children, $("#menu"),0);
			}
		}

		function createMenu(nodes, pnode,level) {
			var l=level;
			if (pnode[0].localName&&pnode[0].localName == "ul") {
				var lidom, adom;
				$.each(nodes, function(i, node) {
					l=level;
					lidom = $("<li></li>");
					adom = $("<a></a>");
					adom.attr("href", "#");
					if (node.url && node.url != "") {
						adom.attr("onclick", "openUrl(" + node.id + ",\""
								+ node.text + "\",\"" + node.url + "\",\""
								+ node.showmenu + "\")");
					}
					adom.append(node.text);	
					if("children" in node){
						var imgdom=$("<img style=\"float:right;\"></img>");
						if(l==0){
					        imgdom.attr("src","${contextPath}/scripts/home/${homeTheme}/images/list031.gif");
						}
						else{
							imgdom.attr("src","${contextPath}/scripts/home/${homeTheme}/images/list021.gif");	
						}
					    imgdom.attr("title",node.text);
					    imgdom.css("vertical-align","middle");
					    imgdom.css("margin-right","3px");
					    adom.append(imgdom);
					}
					lidom.append(adom);
					if ("children" in node) {
						var uldom = $("<ul></ul>");
						createMenu(node.children, uldom,++l);
					}
					lidom.append(uldom);
					pnode.append(lidom);
				});
			}
		}
		function openUrl(id, name, url, showmenu) {
			//addTab
			if(!(url.indexOf("http://") ==0 || url.indexOf("https://") ==0)){
				   url="${contextPath}"+url;
			   }
			
			if (showmenu && showmenu == 2) {
				window.open(url, "_blank");

			}else if(showmenu && showmenu == 3){
				$.fancybox.open({
							href:url,
							width:500,
							height:500,
							type: 'iframe',
							modal:false,
							closeClick : true,
							openEffect : 'none',
							padding : 5,
							autoSize   : false,
					        autoHeight : false,
					        autoWidth  : false,
							helpers : {
								title : null
					}
				});
			} else {
				$(document.getElementById('frame1').contentWindow.document.body)
						.find("frame")[1].contentWindow.addTabEx(id, name, url);
			}
		}
		function goHome() {

			$(document.getElementById('frame1').contentWindow.document.body)
					.find("frame")[1].contentWindow.document.getElementById(
					"home").onclick();
		}
		//弹窗事件触发源所在页面window
		var eventView;
		//打开窗口
		function openFancybox(eventWin,url,title,width,height){
			//eventWin.view.location=eventWin.view.location;
			eventView=eventWin.view;
			if(width==undefined){
				width="99%";
			}
			if(height==undefined){
				height="99%";
			}
			$.fancybox.open({
						href:url,
						width:width,
						height:height,
						type: 'iframe',
						modal:false,
						closeClick : true,
						openEffect : 'none',
						padding : 5,
						autoSize   : false,
				        autoHeight : false,
				        autoWidth  : false,
						helpers : {
							title : null
				}
			});
		}
		$(function(){
			var pwdUpdateFlag='${pwdUpdateFlag}';
			if(pwdUpdateFlag!='1'){
				var url="${contextPath}/mx/identity/user/prepareModifyPwd";
				openFancybox(window,url,"修改用户密码",500,400);
			}
		});
	</script>
	<iframe id="frame1"
		src="${contextPath}/mx/my/home/main?style=horz&menu=true"
		style="width: 100%; height: 100%; margin: 0px;">
</body>
</html>
