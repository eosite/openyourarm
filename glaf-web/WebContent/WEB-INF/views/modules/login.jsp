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
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%
   String logoPath=SystemConfig.getString("res_login_logo");
   if(StringUtils.isEmpty(logoPath)||logoPath.trim().length()==0){
	   logoPath="images/chissLOGO1.png";
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title><%=SystemConfig.getString("res_system_name")%></title>
<link rel="shortcut icon" href="${contextPath}/scripts/login/img/jadp.ico">
<link rel="stylesheet" href="${contextPath}/scripts/login/css/style.css">
<script type="text/javascript">
	
</script>
</head>
<body class="p-login p-login-vip188" id="pagebody">
	<div class="g-hd">
		<div class="m-hd">
			<span class="u-logo" style="overflow:hidden;background: url('${contextPath}/<%=logoPath%>') no-repeat;"></span>
			<p class="nav">
				<a
					href="#"
					>产品中心</a> <a
					href="#"
					>科技研发</a> <a
					href="#"
					>人物风尚</a><a id="aboutus"
					href="#">关注我们</a> <a id="service"
					href="#" class="serviceIcon">7x24客户服务<i
					class="serviceIcon"></i></a>
			</p>
		</div>
	</div>
	<div class="g-scroll">
		<div class="g-loginbox">
			<div class="g-bd">
				<div class="m-loginbg">
					<img id="bg" draggable="false"
						src="${contextPath}/scripts/login/img/bg4.jpg"
						style="margin-left: 0px; margin-top: -50px;"  width="100%">
				</div>
				<div class="m-bgwrap"></div>
				<div class="m-loginboxbg"></div>
				<div class="m-loginbox">
					<div id="pervent" class="m-photoframe" title="更换防伪图片" hidefocus=""
						style="display: none;">
						<img class="perImg" src="about:blank" alt="">
					</div>
					<div class="lbinner" id="mailbox">
						<form  id="iForm" name="iForm" method="post" action="">
						    <input type="hidden" id="y" name="y">
						    <input type="hidden" id="x_y" name="x_y">
							<input type="hidden" id="x_z" name="x_z">
							<div class="line1 f-cb">
								<span class="domain"><img
									src="${contextPath}/scripts/login/img/user.png" width="16"
									style="vertical-align: middle;" />&nbsp;&nbsp;用户名</span> <input
									type="text" name="x" class="ipt ipt-user" id="x"
									autocomplete="off" value="" maxlength="40" style=""
									onclick="hiddenUserWarn()" onblur="validUser()"> <input
									type="text" class="ipt ipt-user ipt-replace" id="replaceun"
									autocomplete="off" value="用户名" maxlength="40"
									style="display: none;">
							</div>
							<div class="line1 f-cb">
								<span class="domain"><img
									src="${contextPath}/scripts/login/img/lock.png" width="16"
									style="vertical-align: middle;" />&nbsp;&nbsp;密&nbsp;&nbsp;码</span> <input
									type="password" name="yy" class="ipt ipt-user" id="yy"
									autocomplete="off" value="" maxlength="40" style=""
									onclick="hiddenPswWarn()" onblur="validPsw()"> <input
									type="password" class="ipt ipt-pwd" id="replacepw"
									autocomplete="off" value="" name="replacepw" maxlength="40"
									style="display:none;">
							</div>
							<div class="line3 f-cb">
								<p class="ssl" id="ssl" title="您正在使用银行级加密登录，全面保障邮箱安全">
									<span class="u-ico u-ico-tick u-ico-ticked" id="icossl"></span>&nbsp;加密登录
								</p>
								<a class="forgotpw" href="#" >忘记密码？</a>
							</div>
							<div class="line4">
								<a class="u-loginbtn" id="loginBt" href="#"
									onclick="return false;">登 录</a>
							</div>
						</form>
					</div>
					<div class="m-popup m-popup-warn m-popup-warn-block">
						<div class="inner">
							<div class="tt">
								<span class="u-ico u-ico-warn"></span>帐号或密码错误
							</div>
							<div class="ct">
								<p>提示：</p>
								<p>1. 请检查帐号拼写，是否输入有误</p>
								<p>
									2. 若帐号长期未登录，可能已被注销，请<a 
										href="#"
										id="reglink">重新注册</a>
								</p>
								<p>
									3. 若您忘记密码，请<a 
										href="#">找回密码</a>
								</p>
								<p>
									4. 若您需要锁定此帐号，请<a 
										href="#">点击这里</a>
								</p>
							</div>
						</div>
						<div class="arrow"></div>
					</div>
					<div class="m-popup m-popup-warn m-popup-warn-username-empty">
						<div class="inner">
							<div class="tt">
								<span class="u-ico u-ico-warn"></span>请输入帐号
							</div>
						</div>
						<div class="arrow"></div>
					</div>
					<div class="m-popup m-popup-warn m-popup-warn-username">
						<div class="inner">
							<div class="tt">
								<span class="u-ico u-ico-warn"></span>帐号输入错误，请重新输入
							</div>
						</div>
						<div class="arrow"></div>
					</div>
					<div class="m-popup m-popup-warn m-popup-warn-password">
						<div class="inner">
							<div class="tt">
								<span class="u-ico u-ico-warn"></span>请输入密码
							</div>
						</div>
						<div class="arrow"></div>
					</div>
				</div>
			</div>
			<a href=".g-more" class="u-jumpNext J_jump" data-jump="1" style="display:none">
				<p>了解JADP</p> <img src="${contextPath}/scripts/login/img/nav.gif">
			</a>
		</div>
			</div>
			
			<div class="g-ft1" style="position: absolute; bottom: 0px;z-index: 9999;width: 100%;">
				<div class="m-bd">
				   
					<div class="m-flink" style="margin-bottom: -30px;">
						<a  href="#"></a>&nbsp;&nbsp;
						<a  href="#"></a>&nbsp;&nbsp;
						<a  href="#"></a>&nbsp;&nbsp;
						<a 
							href="#"></a>&nbsp;&nbsp;
						<a  href="#"
							style="position: relative;display:inline-block"><i
							class="u-new"
							style="position: absolute;display;block;width:20px;height:13px;top:-12px;right:-13px;background:url(${contextPath}/scripts/login/img/new.png) no-repeat;"></i></a><br>
						<a  href="#"></a>&nbsp;&nbsp;
						<a  href="#">客户服务</a>&nbsp;&nbsp;
						<a  href="#">隐私政策</a>&nbsp;&nbsp;|&nbsp;&nbsp;<%=SystemConfig.getString("res_copyright")%>
						<!--© 1997-
						<script type="text/javascript"
							src="${contextPath}/scripts/login/year.js"></script>
						2015-->
					</div>
				</div>
			</div>
		</div>
	<!--</div>-->
      
	<a href=".g-scroll" class="J_jump J_top u-top" style="display: none;">
		<img src="${contextPath}/scripts/login/img/nav.png" class="J_lazy"> <i
		class="cover">返 回<br>登 录
	</i>
	</a>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/map.js"></script>
	<!-- 
	<script type="text/javascript"
		src="${contextPath}/scripts/login/spread.js"></script>
	 -->
	 <script type="text/javascript">
	   var map = new Map();
	 </script>
	<script type="text/javascript">
		//向上滚动效果 
		$(".u-top").click(function() {
			var href = $(this).attr("href");
			var pos = $(href).offset().top;
			$(href).animate({
				scrollTop : pos - 50
			}, 1000);
			return false;
		});
		//向下滚动效果
		$(".u-jumpNext").click(function() {
			var href = $(this).attr("href");
			var pos = $(href).offset().top;
			$(".g-scroll").animate({
				scrollTop : pos + 50
			}, 1000);
			return false;
		});
		function hiddenUserWarn() {
			$(".m-popup-warn-username-empty").removeClass("m-popup-warn-show");
			$(".m-popup-warn-username").removeClass("m-popup-warn-show");
			$(".m-popup-warn-block").removeClass(
											"m-popup-warn-show");
		}
		function validUser() {
			if ($("#x").val() == "")
				$(".m-popup-warn-username-empty").addClass("m-popup-warn-show");
		}
		function hiddenPswWarn() {
			$(".m-popup-warn-password").removeClass("m-popup-warn-show");
			$(".m-popup-warn-block").removeClass(
											"m-popup-warn-show");
		}
		function validPsw() {
			if ($("#yy").val() == "")
				$(".m-popup-warn-password").addClass("m-popup-warn-show");
		}
		
		$(document)
				.ready(
						function() {
						    if("${account}"!=""){
						      $(".m-popup-warn-block").addClass(
											"m-popup-warn-show");
						    }
						    
						    var $x = $("#x").focus();
						    
							function doAxy() {
								var x = $x.val();
								x=encodeURIComponent(x);
								var y = $("#yy").val();
								if (x == "") {
									$(".m-popup-warn-username-empty").addClass(
											"m-popup-warn-show");
									return;
								} else if (y == "") {
									$(".m-popup-warn-password").addClass(
											"m-popup-warn-show");
									return;
								} else {
								   document.getElementById("loginBt").disabled=true;
								   if(map.get("login_status")!= null){
                                       alert("正在登录,请稍等......");
									   return;
								   }
								   map.put("login_status", "login");
								   $("#loginBt:disabled");
								   delay();
                                   jQuery.ajax({
											   type: "POST",
											   url: "${contextPath}/mx/login/getLoginSecurityKey",
											   dataType:  'json',
											   error: function(data){
												   //alert('服务器处理错误！');
												   document.getElementById("loginBt").disabled=false;
												   map.remove("login_status");
											   },
											   success: function(dataxy){  
												 if(dataxy != null && dataxy.x_y != null && dataxy.x_z != null){
													var px = dataxy.x_y+ y + dataxy.x_z;
													document.getElementById("y").value=px;
													document.getElementById("yy").value=px;
													var link = "${contextPath}/mx/login/doLogin?x="+ x
															 + "&responseDataType=json"
															 + "&rember=1";
													var params = jQuery("#iForm").formSerialize();
													params += "&originPassword="+y;
													//alert(params);
													jQuery.ajax({
															   type: "POST",
															   url: link,
															   dataType:  'json',
															   data: params,
															   error: function(data){
																   //alert('服务器处理错误！');
																   map.remove("login_status");
																   $("#loginBt:enabled");
															   },
															   success: function(data){  
																   if(data != null ){
																	   if(data.statusCode == 200){//登录成功
																		   map.remove("login_status");
																		   //alert("登录成功,准备跳转...");
																		   window.location="${contextPath}/index.jsp";
																	   } else {
																		  if( data.message != null){
																			  map.remove("login_status");
																			  alert(data.message);
																			  document.getElementById("y").value="";
																			  document.getElementById("yy").value="";
																			  document.getElementById("x_y").value="";
																			  document.getElementById("x_z").value="";
																			  $("#loginBt:enabled");
																		  }
																	   }
																   } else {
																	   //alert('服务器处理错误！');
																	   map.remove("login_status");
																	   $("#loginBt:enabled");
																   }
															   }
														 });	
												   }
											   }
										    });
								     }							
							}

							$("#loginBt").click(function() {
								doAxy();
							});
							document.onkeydown = function(event) {
								var e = event || window.event
										|| arguments.callee.caller.arguments[0];
								if (e && e.keyCode == 13) {
									doAxy();
								}
							};
						});


					  function delay(){    
						  window.setTimeout(function(){        
							 //document.getElementById("loginBt").disabled=false;
							 $("#loginBt:enabled");
							},10000);
						return false;
					  }

	</script>
</body>
</html>