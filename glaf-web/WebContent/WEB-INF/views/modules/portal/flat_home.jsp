<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
	String themeKey="";
	String homeTheme ="";
    if (request.getAttribute("userTheme") != null) {
		UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
		request.setAttribute("theme", userTheme.getThemeStyle());
		homeTheme=userTheme.getHomeThemeStyle();
		request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
		request.setAttribute("layoutTheme", userTheme.getLayoutModel());
		themeKey=userTheme.getLayoutModel()+"|"+userTheme.getHomeThemeStyle()+"|"+userTheme.getThemeStyle();
	} else {
		String theme = com.glaf.core.util.RequestUtils.getTheme(request);
		request.setAttribute("theme", theme);
		homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
		request.setAttribute("homeTheme", homeTheme);
		String layoutTheme = com.glaf.core.util.RequestUtils.getLayoutTheme(request);
		request.setAttribute("layoutTheme", layoutTheme);
		themeKey=layoutTheme+"|"+homeTheme+"|"+layoutTheme;
	}
	   BaseDataManager bm=BaseDataManager.getInstance();
	   com.glaf.base.modules.sys.model.BaseDataInfo info=bm.getBaseData(themeKey,"dict_theme");
	   session.setAttribute("theme",info);
	   String hideSysName=info!=null?info.getExt4():"0";
	   	//获取logo图片路径
	String logoPicPath=info!=null?info.getDesc():"";
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
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${contextPath}/scripts/home/${homeTheme}/css/flat_style.css"
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
	<div style="height: 100%; width: 100%;">
		<div id="top" class="top">
			<div id="logo" class="logo">
				<img class="logoimg" id="logoimg"
					src="${contextPath}/${logoPicPath}"
					title="系统首页" />
			</div>
			<div class="systitle">
			  <%=!hideSysName.equals("1")?SystemConfig.getString("res_system_name"):""%>
			</div>
			<div id="topmenu" class="topmenu">
			    <div class="scrollleft" id="scrollleft" style="display:inline;">◀</div>
				<div><ul></ul></div>
				<div class="scrollright" id="scrollright" style="display:inline;">▶</div>
			</div>
			<div class="topright">
				<ul>
				    <li><span class="toprightimg"> <img
							src="${contextPath}/scripts/home/${homeTheme}/images/back.png"
							title="返回主页" />
					</span> <a class="bounceIn dialog" href="#"
						onclick="restoreTopStyle()">主页</a></li>
					<li><span class="toprightimg"> <img
							src="${contextPath}/scripts/home/${homeTheme}/images/palette1.png"
							title="切换主题" />
					</span> <a class="bounceIn dialog" href="#"
						onclick="javascript:openThemeDialog();">主题</a></li>
					<li><span class="toprightimg"> <img
							src="${contextPath}/scripts/home/${homeTheme}/images/help1.png"
							title="帮助" class="helpimg" />
					</span> <a href="#" id="helperButton">帮助</a></li>
					<li><span class="toprightimg"> <img
							src="${contextPath}/scripts/home/${homeTheme}/images/exit.png"
							title="退出" class="exitimg" />
							</span><a href="${contextPath}/mx/login/logout" target="_parent">退出</a>
					</li>
				</ul>
				<!-- <div class="user">
				<span>${username}，欢迎您！</span> <i>消息</i> <b>5</b>
						        </div> -->
			</div>
		</div>
		<div id="middle" style="width: 100%; float: left;">
			<div id="left" class="left">
				<div class="lefttop">
					<span></span>系统菜单
				</div>
				<dl class="leftmenu">
				</dl>
				<script type="text/javascript">
                     //菜单初始化
     var _mxm_ = {
				  "children":  ${scripts}
		    };
		function initLeftMenu(node){
		   var nodes =_mxm_.children.children;
		   initLeftTree(nodes,node.id);
		   if(node.url!=undefined&&node.url!=''&&openUrl&&openUrl!=undefined){
                openUrl(node.id,node.text,node.url,node.showmenu);
		   }
		}
		var defaultOpenNode;
		function initMenuData(){
			var nodes =_mxm_.children.children ;	
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				if(i==0)
				{
				  defaultOpenNode=node;
				}
				createNode(node);
			}
			//设置宽度
			var winWidth=$(window).width();
		    var leftWidth=$("#left").width();
			var ulWh=winWidth-leftWidth-50;
			$(".topmenu ul").width(ulWh);
		}
		function createNode(node){
			var lidom=$("<li></li>");
			var adom=$("<a></a>");
			adom.attr("href","#");
			//adom.attr("onclick","javascript:initLeftMenu("+node.id+")");
			adom.unbind("click");
		    adom.bind("click",function(){initLeftMenu(node)});
			adom.append(node.text);
			lidom.append(adom);
			$(".topmenu ul").append(lidom);
			//console.log($(".topmenu ul"));
		}
		initMenuData();
		//打开默认模块
		if(defaultOpenNode!=null&&defaultOpenNode!="")
		initLeftMenu(defaultOpenNode);
        function initLeftTree(nodes,nodeId){
			var selectNode = retSelectNode(nodes,nodeId);
			if(selectNode!=null&&selectNode.children&&selectNode.children.length>0){
				$(".leftmenu").empty();
			    $(".lefttop").html("<span></span>"+selectNode.text);
				initMenuson(selectNode);
			}
			//导航切换
			$(".menuson .header").unbind("click");
			$(".menuson .header").click(function(){
				var $parent = $(this).parent();
				$(this).parent().parent().find("li").not($(this).parent()).removeClass("active open");
				$(this).parent().addClass("active");
				//获取临近的ul
				var ul=$(this).next("ul");
				if(ul&&ul.length>0){
				    if($(this).parent().hasClass("open")){
				       $(this).parent().removeClass("open");
				       ul.hide();
				    }
				    else{
				       $(this).parent().addClass("open");
				       ul.show();
				    }
				}
			});
	$(".sub-menus li").unbind("click");
	$(".has-child li").unbind("click");
	// 三级菜单点击
	$('.sub-menus li').click(function(e) {
        $(".sub-menus li.active").removeClass("active");
        $(".has-child li.active").removeClass("active");
		$(this).addClass("active");
    });
	$(".title").unbind("click");
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('.menuson').slideUp();
		if($ul.is(':visible')){
			$(this).next('.menuson').slideUp();
		}else{
			$(this).next('.menuson').slideDown();
		}
	});
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
		function initMenuson(selectNode){
			if("children" in selectNode){
				createMenu(selectNode.children,true,0)
			}
		}
		function createMenu(nodes,bol,count,menusonId){
			var t;
			for(var i=0;i<nodes.length;i++){
			    t = count ;
				var node = nodes[i];
				var obj = {} ;
				var lidom;
				obj.text = node.text ;
				var imagePath = "${contextPath}/scripts/home/${homeTheme}/images/leftico01_1.png" ;
				if(node.icon){
					imagePath ="${contextPath}"+node.icon;
				}
				obj.imageUrl = imagePath ;
				if(bol){
					if(i==0){
						obj.expanded = true ;
					}
				}
				
				//创建第一层
				if(count == 0){
				 	   //创建菜单项
						var dddom=$("<dd></dd>");
						var divtitledom=$("<div  onmouseover=\"menu_MM_over(this)\" onmouseout=\"menu_MM_out(this)\"></div>");
						divtitledom.addClass("title");
						divtitledom.addClass("menu_bg1");
						 var adom=$("<a></a>");
						 adom.attr("href","#");
						 if(node.url&&node.url!="")
						 {
						      adom.attr("onclick","openUrl("+node.id+",\""+node.text+"\",\""+node.url+"\",\""+node.showmenu+"\")");
				         }
				        //adom.attr("target","rightFrame");
						var spantitledom=$("<span></span>");
						var imgtitledom=$("<img></img>");
						imgtitledom.attr("src",imagePath);
						spantitledom.append(imgtitledom);
						adom.append(spantitledom);
						adom.append(node.text);
						divtitledom.append(adom);
						dddom.append(divtitledom);
						var menusondom=$("<ul id=\""+node.id+"\" class=\"menuson\"></ul>");
						dddom.append(menusondom);
						$(".leftmenu").append(dddom);
						
				}
				else if(count == 1){
				         lidom=$("<li id=\""+node.id+"_li\"></li>");
				         var divdom=$("<div></div>");
				         divdom.addClass("header");
				         var citedom=$("<cite></cite>");
				         divdom.append(citedom);
				         var adom=$("<a></a>");
				          adom.attr("href","#");
						 if(node.url&&node.url!="")
						 {
						      adom.attr("onclick","openUrl("+node.id+",\""+node.text+"\",\""+node.url+"\",\""+node.showmenu+"\")");
				         }
				         adom.append(node.text);
				         divdom.append(adom);
				         var splitlidom=$("<li></li>");
				         divdom.append(splitlidom);
				         lidom.append(divdom);
				         $("#"+menusonId).append(lidom);
				}
				else{
						 var lidom=$("<li></li>");
						 var adom=$("<a></a>");
						  adom.attr("href","#");
						 if(node.url&&node.url!="")
						 {
						      adom.attr("onclick","openUrl("+node.id+",\""+node.text+"\",\""+node.url+"\",\""+node.showmenu+"\")");
				         }
				         adom.append(node.text);
				         lidom.append(adom);
						 $("#"+menusonId).append(lidom);
				 }
				if(node.url){
					obj.url = "javascript:openUrl(\""+node.url+"\")" ;
					if(count == 1 && i==0){
						//openUrl(node.id,node.name,node.url);
					}
					//只有一级菜单
					if(count == 0 && !node.children && i==0){
						//openUrl(node.id,node.name,node.url);
				    }
				   }
				if("children" in node){
				    if(t>=1){
				       var uldom=$("<ul id=\""+node.id+"\" class=\"sub-menus\"></ul>");
				       $("#"+node.id+"_li").append(uldom);
				    }
					createMenu(node.children,false,++t,node.id);
				}
			}
		}
		        //切换头样式（主页面与模块主页面切换）
		        function changeTopStyle(){
				 //隐藏topmenu
				 //$("#topmenu").hide();
				 //隐藏用户状态栏
				 $(".user").hide();
				 //$("#content iframe").css("height",($(window).height()-$("#top").height())+"px");
				 //startInit("frame1",$(window).height()-$("#top").height());
		        }
		        function restoreTopStyle(){
					 //隐藏topmenu
					 //$("#topmenu").show();
					 //隐藏用户状态栏
					 $(".user").show();
		        }
		        
                </script>
					<script type="text/javascript">
	$(document).ready(function(e) {
		settingHeight();
		setTimeout(showTopMenuScroll,2000);
		var pwdUpdateFlag='${pwdUpdateFlag}';
		if(pwdUpdateFlag!='1'){
			var url="${contextPath}/mx/identity/user/prepareModifyPwd";
			openFancybox(window,url,"修改用户密码",500,400);
		}

		$('#helperButton').click(function(){
            var helper_link = '<%=SystemConfig.getString("helper_link")%>';
            if(helper_link){
                window.open(helper_link);
                // window.location.href = message_link;
            }
        })
	});
	$(window).resize(function(){
		settingHeight();
		showTopMenuScroll();
		});
	function settingHeight() {
		var winHeight = $(window).height();
		var winWidth=$(window).width();
		var topHeight=$("#top").height();
		var leftWidth=$("#left").width();
		$("#middle").height(winHeight-topHeight);
		$("#content").height(winHeight-topHeight);
		$("#content").width(winWidth-leftWidth);
		$(".topmenu ul").width(winWidth-leftWidth-50);
	}
		function openUrl(id,name,url,showmenu){
		   //addTab
		   if(!(url.indexOf("http://") ==0 || url.indexOf("https://") ==0)){
			   url="${contextPath}"+url;
		   }
			   
		  
		   if(showmenu&&showmenu==2){
			      window.open(url,"_blank");
			   
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
			   }else{
				   document.getElementById('frame1').contentWindow.addTabEx(id,name,url);
			   }
		   changeTopStyle();
		}
		function collapse(){
          window.parent.document.getElementsByTagName("frameset")[1].cols="0,*";
          self.parent.rightFrame.showExpd();
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
		$("#scrollleft").click(function(){
			//获取第一个非隐藏状态的li
			$(".topmenu ul").find("li:hidden:last").show();
			showTopMenuScroll();
		});
		$("#scrollright").click(function(){
			//获取第一个隐藏状态的li
			   $(".topmenu ul").find("li:visible:first").hide();
			   showTopMenuScroll();
			 
		});
		function showTopMenuScroll(){
			   var lastpos=$(".topmenu ul").find("li:last").offset().left;
			   var scrollrightpos=$("#scrollright").offset().left;
			   if(scrollrightpos>lastpos){
			       $("#scrollright").hide();
			   }else{
				   $("#scrollright").show();
			   }
			   var firstvis=$(".topmenu ul").find("li:first:visible").length;
			   if(firstvis==0){
				   $("#scrollleft").show();
			   }else{
				   $("#scrollleft").hide();
			   }
		}
        </script>
			</div>
			<div id="content" style="height: 100%; float: left;">
				<iframe id="frame1"
					src="${contextPath}/mx/my/home/content?style=flat"
					style="width: 100%; height: 100%;"></iframe>
			</div>
		</div>
	</div>
	<!--
	<div id="cdiv">
	    <img id="coll_img" src="${contextPath}/scripts/home/${homeTheme}/images/nav.gif" width="18" style="text-align:center;vertical-align:middle;margin: auto;cursor: pointer;" onclick="collapse()"/>
	</div>  -->
	<%@ include file="theme_default.jsp"%>
</body>
</html>
