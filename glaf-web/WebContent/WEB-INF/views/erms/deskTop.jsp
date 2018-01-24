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
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=SystemConfig.getString("res_system_name")%></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<style type="text/css">
html {
	height: 100%;
	margin: 0;
}

body {
	height: 100%;
	margin: 0;
	font: 16px/1 Tahoma, Helvetica, Arial, "黑体", sans-serif;
}

a:link {
	font-size: 12px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

a:visited {
	font-size: 12px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

a:hover {
	font-size: 12px;
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

a:active {
	font-size: 12px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.leftClass {
	height: 95%;
}
</style>
</head>
<body>
	<div style="width:250px;height:100%;margin: 0 auto;border:0px;">
		<ul id="panelbar" style="border:0px">
		</ul>
		<script>
<!--
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}

		};
		var setting2 = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};
		function onClick(event, treeId, treeNode, clickFlag) {
			window.parent.openPage(treeNode.address);
        }
		var zNodes =[
			{ id:1, pId:0, name:"收藏夹", open:true, iconOpen:"${contextPath}/images/blue-document-bookmark.png", iconClose:"${contextPath}/images/blue-document-bookmark.png"},
			{ id:11, pId:1, name:"文件1", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:12, pId:1, name:"文件2", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:13, pId:1, name:"卷1", open:true,iconOpen:"${contextPath}/images/folder-horizontal-open.png",iconClose:"${contextPath}/images/folder-horizontal.png"},
			{ id:131, pId:13, name:"文件1", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:132, pId:13, name:"文件2", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:2, pId:0, name:"最近操作文件", open:true, iconOpen:"${contextPath}/images/date_edit.png",iconClose:"${contextPath}/images/date_edit.png"},
			{ id:21, pId:2, name:"最近修改", icon:"${contextPath}/images//folder_edit.png"},
			{ id:211, pId:21, name:"文件1", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:212, pId:21, name:"文件2", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:22, pId:2, name:"最近浏览", icon:"${contextPath}/images/folder_explore.png"},
			{ id:221, pId:22, name:"文件1", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:222, pId:22, name:"文件2", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:23, pId:2, name:"最近锁定", icon:"${contextPath}/images/folder_classic_locked.png"},
			{ id:231, pId:23, name:"文件1", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:232, pId:23, name:"文件2", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:24, pId:2, name:"最近处置", icon:"${contextPath}/images/folder_key.png"},
			{ id:241, pId:24, name:"文件1", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"},
			{ id:242, pId:24, name:"文件2", icon:"${contextPath}/images/document-list.png",address:"http://www.baidu.com"}
		];
        var zNodes2 =[
			{ id:1, pId:0, name:"公路工程施工工艺", open:true},
			{ id:11, pId:1, name:"1.1 路基工程施工工艺"},
			{ id:12, pId:1, name:"1.2 路面工程施工工艺"},
			{ id:13, pId:1, name:"1.3 桥梁工程施工工艺"},
			{ id:14, pId:1, name:"1.4 互通立交工程施工工艺"},
			{ id:15, pId:1, name:"1.5 隧道工程施工工艺"},
			{ id:16, pId:1, name:"1.6 环保工程施工工艺"},
			{ id:17, pId:1, name:"1.7 交通安全工程施工工艺"},
			{ id:18, pId:1, name:"1.8 特大桥梁工程施工工艺"},
			{ id:2, pId:0, name:"公路工程施工帮助", open:true},
			{ id:21, pId:2, name:"2.1 路基工程施工帮助"},
			{ id:22, pId:2, name:"2.2 路面工程施工帮助"},
			{ id:23, pId:2, name:"2.3 桥梁工程施工帮助"},
			{ id:24, pId:2, name:"2.4 互通立交工程施工帮助"},
			{ id:25, pId:2, name:"2.5 隧道工程施工帮助"},
			{ id:26, pId:2, name:"2.6 环保工程施工帮助"},
			{ id:27, pId:2, name:"2.7 交通安全工程施工帮助"},
			{ id:28, pId:2, name:"2.8 特大桥梁工程施工帮助"},
			{ id:3, pId:0, name:"公路工程填写标注", open:true}
		];

		$(document).ready(function(){
			$.fn.zTree.init($("#workspace"), setting, zNodes);
			$.fn.zTree.init($("#fileType"), setting2, zNodes2);
		});
$("#panelbar").kendoPanelBar({
		 expandMode: "single",
         dataSource: [
          {
              text: "工作台",
			  expanded: true,
              cssClass: "leftClass",                            // Add custom CSS class to the item, optional, added 2012 Q3 SP1.
              imageUrl: "${contextPath}/images/monitor.png",
              content : "<div id=\"workspace\" class=\"ztree\"></div>" 
          },
          {
              text: "分类列表",
              // item image URL, optional
              imageUrl: "${contextPath}/images/network_folder.png",
              content: "<div id=\"fileType\" class=\"ztree\"></div>"
          }
      ]
  });
  //主内容区页面跳转
  //window.parent.openPage("http://www.baidu.com");
</script>
	</div>
</body>
</html>