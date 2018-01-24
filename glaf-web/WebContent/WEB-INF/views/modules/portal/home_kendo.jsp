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
<script type="text/javascript" src="${contextPath}/scripts/kendoConfirm.js"></script>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
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

.menuCss {
	background: #000 9;
	filter: alpha(opacity : 50);
	-moz-opacity: 0;
	-khtml-opacity: 0;
	opacity: 1;
	border: none;
	margin-left: 2px;
	font-size: 15px;
	font: 15px/1 bold  "宋体", Helvetica, Tahoma, Arial, sans-serif;
}

.firstMenuCss {
	border: none;
	font-size: 15px;
	vertical-align: middle;
}

.itemMenuCss {
	border: none;
	font-size: 12px;
}

.footerCss {
	text-align: center;
	font: normal 12px/24px Helvetica, Tahoma, Arial, sans-serif;
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
.palbar{
	height:100%;margin: 0 auto;border:0px;
}
span.k-link.k-header{
	height:38px;
	font-size: 18px;
}
</style>
</head>
<body>
	<div id="banner" class="k-header k-widget" 
		style="position: relative; z-index: 9999; width: 100%; height: 62px; margin: 0 auto; border-bottom: 0px;">
		<table style="width: 100%; height: 100%; border: 0px;">
			<tr>
				<!-- <td style="width: 50px;"><img src="${contextPath}/images/logo.png" height="20"
					style="margin-left: 10px; vertical-align: middle;"></td> -->
				<td style="width: 520px; padding-left: 2px;"><span
					style="font: '微软雅黑'; font-size: 22px; font-weight: 700; letter-spacing: 1px; color: #009;"><%=SystemConfig.getString("res_system_name")%></span>&nbsp;&nbsp;<span
					style="font-size: 13px; font-weight: 700; color: red;"><%=SystemConfig.getString("res_version")%></span><br /> 
				</td>
				<td>
					<ul id="menu-images" class="menuCss"></ul>
				</td>
				<td style="width: 80px; padding-right: 10px;padding-top:0px; vertical-align: top; text-align: right;"><img
					src="${contextPath}/images/style_edit.png" height="16" style="vertical-align: middle" />&nbsp;<a id="chtheme"
					href="javascript:openthemeDialog();">切换主题</a><br><img src="${contextPath}/images/door_out.png"
					style="vertical-align: middle" height="16" />&nbsp;<a href="javascript:logout();">注　　销</a><br></td>
			</tr>
		</table>
	</div>
	<%@include file="theme_kendo.jsp"%>
	<div id="vertical" style="width: 100%; margin: 0 auto;">
		<div>
			<div id="horizontal" style="height: 100%; width: 100%;">
				<div style="height: 100%;"><div id="tree"></div></div>
				<div id="content"></div>
			</div>
		</div>
		<div id="footer" class="k-header k-footer footerCss">
			<table style="width: 100%; height: 100%; vertical-align: middle;">
				<tr>
					<td><%=SystemConfig.getString("res_copyright")%></td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		window.MSPointerEvent = null;
		window.PointerEvent = null;
		var mainHeight=$(window).height()-45; 
		$('#vertical').height(mainHeight);
		$(window).resize(function(){  
		         mainHeight=$(window).height()-45; 	 
		        $("#vertical").height(mainHeight);   
		    });
		var _mxm_ = {
				  "children":  ${scripts}
			    };
		var nMenuJson="[";
		function initMenuData(){
			nMenuJson+="{\"text\": \"首  页\", \"cssClass\":\"firstMenuCss\",\"imageUrl\":\"${contextPath}/images/home.png\",\"url\":\"javascript:openPage(\\\"/mx/user/portal\\\");\"},";
			var nodes =_mxm_.children.children ;	
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				createNode(node);
			}
			if(nMenuJson.lastIndexOf(",")== nMenuJson.length-1)
			{
				nMenuJson=nMenuJson.substring(0, nMenuJson.length-1);
			}
		}
		function createNode(node,fullId,thisId){
			
			nMenuJson+="{";
			if(node.text!="")
			{
				nMenuJson+="\"text\":\""+node.text+"\",";
			}
			
			nMenuJson+="\"cssClass\":\"firstMenuCss\",";
			if(node.icon!=null&&node.icon!=""&&node.icon!=undefined)
			{
				var imagePath="${contextPath}"+"/"+node.icon;
				nMenuJson+="\"imageUrl\":\""+imagePath+"\",";
			}
			if(!fullId){
				fullId = node.id ;
			}
			//if(node.url!=""&&node.url!=undefined)
			//{
				nMenuJson+="\"url\":\"javascript:openPage(\\\""+node.url+"\\\",\\\""+fullId+"\\\",\\\""+node.id+"\\\");\",";
			//}
			if("children" in node)
			{
			var nodeChilds = node.children;
			for(var i = 0; i < nodeChilds.length; i++)
			{
				if(i==0)
				{
				    nMenuJson+="\"items\":[";
				}
				createNode(nodeChilds[i],fullId,thisId);
				if(i==nodeChilds.length-1)
				{
					if(nMenuJson.lastIndexOf(",")== nMenuJson.length-1)
					{
						nMenuJson=nMenuJson.substring(0, nMenuJson.length-1);
					}
					nMenuJson+="]";	
				}
			}
			}
			if(nMenuJson.lastIndexOf(",")== nMenuJson.length-1)
			{
				nMenuJson=nMenuJson.substring(0, nMenuJson.length-1);
			}
			nMenuJson+="},";
			//var nodeJson="{text:\""+node.text+"\", cssClass:\"firstMenuCss\",imageUrl:\""+node.text+"\",url:\"javascript:openPage(\""+node.url"\");}";
			//alert(nodeJson);
		}
		
		initMenuData();
		nMenuJson+="]";
		//nMenuJson ="[{\"text\":\"基础定义\",\"cssClass\":\"firstMenuCss\",\"url\":\"javascript:openPage('www.sina.com.cn');\"}]";
		var jsonOBJ=JSON.parse(nMenuJson);
		//选中节点后触发事件
		function selectNode(e){
			//alert(1);
		}
		var setting = {
				data: {
					simpleData: {
						enable: true
					},
					key:{
						url:"#"
					}
				},
				callback: {
					onClick: onClick
				}

			};
		function onClick(event, treeId, treeNode, clickFlag) {
			if(treeNode.url != undefined && treeNode.url != ""){
				ajaxUrlOpen(treeNode.url);
			}
        }
		//初始化左边树菜单
		function initLeftTree(fullId,selectNodeId){
			var nodes =_mxm_.children.children ;
			var selectNode ;
			if(fullId==undefined || selectNodeId == undefined){
				selectNode =  nodes ;
			}else{
				selectNode = retSelectNode(nodes,fullId);
			}
			if(fullId == selectNodeId){
				selectNodeId == undefined;
			}
			initPanerBar(selectNode,fullId,selectNodeId);
		}
		function initPanerBar(selectNode,fullId,selectNodeId){
			var dataSource = [];
			var nodeId ;
			if(selectNode instanceof Array){
				for(var i=0 ; i<selectNode.length ;i++){
					var node = selectNode[i];
					var obj = {} ;
					obj.text = node.text ;
					if(i == 0){
						obj.expanded = true ;
					}
					obj.imageUrl = "${contextPath}/images/network_folder.png" ;
					nodeId = node.id;
					obj.content = "<div class='palbar'><ul id=\"ztree"+nodeId+"\" class=\"ztree\"></ul></div>" ;
					dataSource.push(obj);
				}
			}else{
				var obj = {} ;
				obj.text = selectNode.text ;
				obj.expanded = true ;
				obj.imageUrl = "${contextPath}/images/network_folder.png" ;
				nodeId = selectNode.id;
				obj.content = "<div class='palbar'><ul id=\"ztree"+nodeId+"\" class=\"ztree\"></ul></div>" ;
				dataSource.push(obj);
			}
			$("#tree").kendoPanelBar({
				 expandMode: "single",
		         dataSource: dataSource
		  	});
			if(selectNode instanceof Array){
				for(var i=0 ; i<selectNode.length ;i++){
					var node = selectNode[i];
					initZtree(node.id,node.children);
				}
			}else{
				initZtree(nodeId,selectNode.children);
			}
			//选中节点
			if(selectNodeId!=undefined && selectNodeId !="undefined" && selectNodeId != "" ){
				var treeObj = $.fn.zTree.getZTreeObj("ztree"+fullId);
				var node = treeObj.getNodesByFilter(function(node){
					return node.id == selectNodeId ; 					
				}, true); // 仅查找一个节点
				treeObj.selectNode(node);
			}
		}
		function initZtree(id,nodes){
			$.fn.zTree.init($("#ztree"+id), setting, nodes);
		}
		//查找根节点
		function retSelectNode(nodes,fullId){
			var selectNode ;
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				if(node.id ==  fullId){
					selectNode = node ;
					return selectNode ;
				}
				if(node.children){
					var hasNode = this.retSelectNode(node.children , fullId);
					if(hasNode){
						return node ;
					}
				}
			}
		}
    	$("#menu-images").kendoMenu({
	                hoverDelay: 100,
					popupCollision: false,
                    dataSource:jsonOBJ,
                    select : selectNode
                });
				$("#vertical").kendoSplitter({
				orientation: "vertical",
				  panes: [
				 {collapsed: false,scrollable: false},{collapsed: false ,resizable: false,scrollable: false,size:"30px"} ]
				});
				var splitter= $("#horizontal").kendoSplitter({
				 panes: [
				 {collapsed: false ,collapsible: true, collapsedSize: "0px",max:"300px",resizable: false,size:"250px",scrollable: true}, {scrollable: true} ]
				});
				splitter = splitter.data("kendoSplitter");
				var treeURL=$("#treeURL").val();
				if(treeURL==null||treeURL=="")
				{
					//splitter使用相对路径时不使用iframe，需使用全路径
					treeURL="${webPath}/mx/erms/deskTop/view";
				}
				var contentURL=$("#contentURL").val();
				if(contentURL==null||contentURL=="")
				{
					//splitter使用相对路径时不使用iframe，需使用全路径
					contentURL='${webPath}<%=com.glaf.base.utils.LoginContextUtil.getIndexUrl(request)%>';
				}
				//splitter.ajaxRequest("#tree",treeURL);
				splitter.ajaxRequest("#content",contentURL);
				function openPage(url,fullId,selectNodeId)
				{	
					$("#menu-images").data("kendoMenu").close();
					initLeftTree(fullId,selectNodeId);
					if(url==undefined || url == "undefined" || url == ""){
						return ;
					}
					ajaxUrlOpen(url);
				}
				function ajaxUrlOpen(url){
					//验证url是否使用相对路径，如果使用相对路径则补全
					if(url.indexOf("http")<0)
					{
						url='${webPath}'+url;
					}
					splitter.ajaxRequest("#content",url);
				}
				var contextPath = "<%=request.getContextPath()%>";
				function logout() {
					var okTemplate={text:'&nbsp&nbsp确定&nbsp&nbsp',callback:function(kendoConfirm){kendoConfirm.close();location.href = '${contextPath}/mx/login/logout';}};
					kendo.confirm('您确定要退出本次登录吗?',{title:'<font size="2">系统提醒</font>',width:'250px',height:'150px'},okTemplate);
				}
				initPanerBar(_mxm_.children.children);
	</script>
</body>
</html>
<!--views\modules\portal\home_kendo.jsp-->