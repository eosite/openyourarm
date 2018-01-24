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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=SystemConfig.getString("res_system_name")%></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/kendoConfirm.js"></script>
<link href="<%=request.getContextPath()%>/css/index_icons.css" rel="stylesheet" type="text/css">
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
	margin-left: 1px;
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

span.k-link.k-header {
	height: 38px;
	font-size: 18px;
}

.k-panel>.k-item>.k-link {
	font-size: 12px;
}

.k-panel>li.k-item {
	margin-top:5px;
	background-color: #FFFEF5;
}

#tree {
	background-color: #FFFEF5;
}

.midicon {
	width: 100%;
	height: 63px;
	text-align: center
}

.middiv {
	width: 32px;
	height: 32px;
	margin: 0 auto
}

@media only screen and (max-width : 1280px) {
	#imgside2 {
		display: none;
	}
}

@media only screen and (max-width : 900px) {
	#imgside {
		display: none;
	}
}
</style>
</head>
<body>
	<div id="banner" class="k-header k-widget" style="position: relative; z-index: 9999; width: 100%; height: 62px; margin: 0 auto; border-bottom: 0px;">
		<table style="width: 100%; height: 100%; border: 0px;">
			<tr>
				<!-- <td id="imgside" style="width: 80px;"><img src="${contextPath}/images/logo.png" height="20" style="margin-left: 10px; vertical-align: middle;"></td> -->
				<td id="imgside2" style="width: 520px; padding-left: 2px;"><span style="font: '微软雅黑'; font-size: 22px; font-weight: 700; letter-spacing: 1px; color: #009;"><%=SystemConfig.getString("res_system_name")%></span>&nbsp;&nbsp;<span
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
		<div style="height: 100%">
			<div id="horizontal" style="height: 100%; width: 100%;">
				<!-- 
				<div id="tree" style="height: 100%;"></div>
			 -->
				<div id="content"></div>
			</div>
		</div>
		<div id="footer" class="k-header k-footer footerCss">
			<table style="width: 100%; height: 20px; vertical-align: middle;">
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
			nMenuJson+="{\"text\": \"首  页\", \"cssClass\":\"firstMenuCss\",\"imageUrl\":\"${contextPath}/images/home.png\",\"url\":\"javascript:initHome(\\\"${webPath}<%=com.glaf.base.utils.LoginContextUtil.getIndexUrl(request)%>\\\");\"},";
			var nodes =_mxm_.children.children ;	
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				//alert(node.text);
				createNode(node);
			}
			if(nMenuJson.lastIndexOf(",")== nMenuJson.length-1)
			{
				nMenuJson=nMenuJson.substring(0, nMenuJson.length-1);
			}
		}
		function createNode(node){
			
			nMenuJson+="{";
			if(node.text!="")
			{
				nMenuJson+="\"text\":\""+node.text+"\",";
			}

			if(node.url != null && node.url != ""){
                nMenuJson+=" \"url\":\"javascript:initHome(\\\"${webPath}"+node.url+"\\\");\"";
			} else {
				nMenuJson+="\"cssClass\":\"firstMenuCss\",";
				if(node.icon!=null&&node.icon!=""&&node.icon!=undefined)
				{
					var imagePath="${contextPath}"+"/"+node.icon;
					nMenuJson+="\"imageUrl\":\""+imagePath+"\",";
				}
				nMenuJson+="\"url\":\"javascript:initLeftTree(\\\""+node.id+"\\\");\",";
				if(nMenuJson.lastIndexOf(",")== nMenuJson.length-1)
				{
					nMenuJson=nMenuJson.substring(0, nMenuJson.length-1);
				}
			}
			nMenuJson+="},";
		}
	 
		
		initMenuData();
		nMenuJson+="]";
		var jsonOBJ=JSON.parse(nMenuJson);
		//点击首页
		function initHome(url){
			//移除左侧树
			$("#horizontal").data("kendoSplitter").remove($("#tree"));
			ajaxUrlOpen(url);
		}
		//初始化左边树菜单
		function initLeftTree(nodeId){
			//创建左侧
			if(!$("#tree").html()){
				$("#horizontal").data("kendoSplitter").insertBefore({collapsed: false ,collapsible: true, collapsedSize: "0px",max:"300px",resizable: false,size:"250px",scrollable: true},$("#content")).html("<div id='panelBar'>无下级菜单</div>").attr("id","tree");
			}
			
			var nodes =_mxm_.children.children ;
			var selectNode = retSelectNode(nodes,nodeId);
			if(selectNode!=null){
				initPanerBar(selectNode);
			}
		}
		function getRandom(){
			return Math.ceil(Math.random()*24) +1;
		}
		function creatPanelBarDatasource(nodes,dataSource,bol,count){
			var t = count ;
			for(var i=0;i<nodes.length;i++){
				var node = nodes[i];
				var obj = {} ;
				if(!bol){
					obj.cssClass = "midicon" ;
					
					obj.text = "<div class='icon-"+getRandom()+" middiv' ></div>"+node.text ;
				}else{
					obj.text = node.text ;
				}
				obj.encoded = false ;
				if(bol){
					//obj.imageUrl = "${contextPath}/images/network_folder.png" ;
					if(i==0){
						obj.expanded = true ;
					}
				}
				if(node.url){
					//alert(node.url);
					obj.url = "javascript:ajaxUrlOpen(\""+node.url+"\")" ;
					if(count == 1 && i==0){
						ajaxUrlOpen(node.url);
					}
					//只有一级菜单
					if(count == 0 && !node.children && i==0){
						ajaxUrlOpen(node.url);
					}
				}
				if("children" in node){
					var items = [] ;
					creatPanelBarDatasource(node.children,items,false,++t);
					obj.items = items ;
				}
				dataSource.push(obj);
			}
		}
		function initPanerBar(selectNode){
			var dataSource = [];
			if("children" in selectNode){
				creatPanelBarDatasource(selectNode.children,dataSource,true,0)
				var panelBar = $("#panelBar").kendoPanelBar({
					 expandMode: "single",
			         dataSource: dataSource
			  	});
				//panelBar.select(0);
				//ajaxUrlOpen(treeNode.url);
			}
		}
		//查找匹配节点
		function retSelectNode(nodes,nodeId){
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				if(node.id ==  nodeId){
					return node ;
				}
			}
			return null ;
		}
    	$("#menu-images").kendoMenu({
	                hoverDelay: 100,
					popupCollision: false,
                    dataSource:jsonOBJ
                });
				$("#vertical").kendoSplitter({
				orientation: "vertical",
				  panes: [
				 {collapsed: false,scrollable: false},{collapsed: false ,resizable: false,scrollable: false,size:"30px"} ]
				});
				var splitter= $("#horizontal").kendoSplitter({
				 panes: [
				 /* {collapsed: false ,collapsible: true, collapsedSize: "0px",max:"300px",resizable: false,size:"250px",scrollable: true},  */{scrollable: true} ]
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
				
				function ajaxUrlOpen(url){
					if(url.indexOf("/form/defined")!=-1){
                        window.open('${webPath}'+url);
					}else{
					//验证url是否使用相对路径，如果使用相对路径则补全
					if(url.indexOf("http")<0)
					{
						url='${webPath}'+url;
					}
					splitter.ajaxRequest("#content",url);
					}
				}
				var contextPath = "<%=request.getContextPath()%>";
				function logout() {
					var okTemplate={text:'&nbsp&nbsp确定&nbsp&nbsp',callback:function(kendoConfirm){kendoConfirm.close();location.href = '${contextPath}/mx/login/logout';}};
					kendo.confirm('您确定要退出本次登录吗?',{title:'<font size="2">系统提醒</font>',width:'250px',height:'150px'},okTemplate);
				}
				//initPanerBar(_mxm_.children.children);
	</script>
</body>
</html>
<!--views\modules\portal\kendo2.jsp-->