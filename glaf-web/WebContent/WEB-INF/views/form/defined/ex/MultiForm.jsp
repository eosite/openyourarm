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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	
	//客户端类型
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	request.setAttribute("isMobile", RequestUtils.isMobile(request));
	request.setAttribute("position", RequestUtils.isMobile(request) ? "top" : "left");
	request.setAttribute("pageParams", RequestUtils.getParameterMap(request));//页面参数
	
%>
<!DOCTYPE html>
<html>
<head>

<!-- 移动端点击输入框的时候控制不让变大  -->
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${name }</title>
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/plugins/bootstrap/tabs/style/plugins.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/plugins/bootstrap/tabs/style/tabdrop.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<span></span>
		</div>
		<div class="row">
			<div id="componentAttr" data-role="tabs" style="width:100%"></div>
		</div>
		
		<div class="row" data-show=false style="text-align: right; margin:5px;display: none;" id="operate-tools">
			<button class="btn btn-info" id="btn-03">保存</button>
			<button class="btn btn-info" id="btn-01" data-approve="true">提交</button>
			<button class="btn btn-info" id="btn-02" data-approve="false">退回</button>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/tabs/js/jquery.tabs.extends.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/tabs/js/bootstrap-tabdrop.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"
	type="text/javascript"></script>
<script src="${contextPath }/scripts/kendo/bootstrap/flow.extend.js"
	type="text/javascript"></script>
<script src="${contextPath}/scripts/kendo/bootstrap/mobile.extend.js" type="text/javascript"></script>

<script type="text/javascript">

function addPage(tabs, pageObj){
	!index && (pageObj.selected = true);
	 tabs.push(pageObj);
	 pageParameters[pageObj.pageId] = pageObj;
	 index ++;
}

	var tabs = [], contextPath = "${contextPath }", //
	uri = "${contextPath }/mx/form/page/viewPage?";
	var $tabs = $('#componentAttr'), paramsObj = {
		defId : "${param.defId}",
		processId : "${param.processId}"
	}, pageParameters = {}, pageObj, terminal, isMobile = ${isMobile}, index = 0;
	<c:forEach items="${pages }" var="page" varStatus="status">
	pageObj = {
		variableKey : "${page.variableKey}",
		idValue : "${page.idValue}",
		pageId : "${page.pageId}",
		title : '${status.index + 1}、${page.pageName}',
		cache : true,
		width : '100%',
		selected : <c:if test="${status.index == 0}">true</c:if><c:if test="${status.index != 0}">false</c:if>,
		closeable : false,
		content : null,
		onload : function(){
			window.iframeOnload.apply(this, arguments);
		}
	 };
	
	<c:if test="${!empty page.rules}">
	pageObj.rules = ${page.rules};
	</c:if>
	<c:if test="${!empty page.flowDefined}">
	pageObj.flowDefined = ${page.flowDefined};
	</c:if>
	
	
	terminal = "";
	if(pageObj.flowDefined){
		terminal = pageObj.flowDefined.terminal
	}
	
	if(!terminal){ //默认都显示
		addPage(tabs, pageObj);
	} else if(terminal == "mobile" && isMobile){
		addPage(tabs, pageObj);
	} else if(!isMobile && terminal != "mobile"){
		addPage(tabs, pageObj);
	}
	</c:forEach>
	
	$(document).ready(readyFunc);
	
	/**
	 * 其他页面调用当前页面执行流程事件
	 */
	var $Flow = (function(){
		
		var func = $.extend(new Function(), paramsObj),//
			refresh = function(){ //刷新主界面
			
			var rpid = "${param.rpid}";
			
			if(!rpid){
				return;
			}
			
			var __windows = "__windows";
			
			wins = window.top[__windows] || window.parent[__windows];
			
			wins[rpid] && wins[rpid].location.reload();
		};
		
		func.save = function(){
			$("#btn-03").click();
		};
		
		func.submit = function(fn){
			$Flow.callback = function(data){
				refresh();
				fn(data);
			};
			if(!paramsObj.processId){//流程未启动，保存并提交
				func.save();
			} else {
				$("#btn-01").click();
			}
		};
		
		func.back = function(fn){
			$Flow.callback = function(data){
				refresh();
				fn(data);
			};
			$("#btn-02").click();
		};
		
		return func;
	})();
	
	function iframeOnload(o){
		//console.log(o);
		$(this).attr({
			id : o.pageId,
			height : window.getHeight()//$(document).height() - 55,
			//width : '100%'
		});
		var page = {};
		if((page = pageParameters[o.pageId])){
			var $win = this.contentWindow;
			if(page.flow){
			//	new $win.FlowFilter(page.flow).exec($win.$ret.jdoms);
			}
			$win && ($win.$ParentFlow = $Flow);
			
			window.setTimeout(function() {
				try {
					//this.contentWindow.close = closeWindow;
				} catch (e) {
					
				}
			}, 1000);
			
			if(page.flowDefined){
				if(typeof page.flowDefined == 'string')
					page.flowDefined = eval('(' + page.flowDefined + ')');
				if(!page.idValue && !page.flowDefined.kkkkkk){
					$win.$($win.document).ready(function(){
						new $win.FlowFilter(page.flowDefined).exec($win.$ret.jdoms);
					});
				}
			}
			
		}
		
		<c:if test="${param.readOnly != true}">
			$("#operate-tools").show();
		</c:if>
	}

	function readyFunc() {
		
		<c:if test="${param.readOnly != true}">
			$("#operate-tools").attr("data-show", true);
		</c:if>
		
		$tabs.tabs({
		    tabs : tabs,
			width : 'auto',
		//	height : window.getHeight(),
			reversed : false,
			border : true,
			fit : false,
			tabdrop : false,
			tabPosition : '${position}',
			tabStyle : 'tabs',
			onSelect : function(title, index) {
				var s = window.selectTab(index, function(){
					if(index == 0 && tabs.length > 1){
						for(var i = 1; i < tabs.length; i ++){
							window.timeoutSelect(i);
						}
					}
					window.iframeOnload.apply(this, arguments);
				});
				return s;
			},
			cols : [ 1, 11 ],
			onLoad : function(){
				if(tabs.length == 1){
					if("${position}" == "top"){
						$tabs.find("ul.nav-tabs").hide();
					} else {
						$tabs.find("div.tabnav").hide();
						$tabs.find("div.contentCol").addClass("col-md-12 col-sm-12 col-xs-12").css({
							"padding-right" : 0
						});
					}
				}
				
			}
		});
		
		$.each(pageParameters, function(id, page){
			if(page && page.rules){
				var flow = {
					elements : {}
				};
				try {
					if(typeof page.rules == 'string')
						page.rules = JSON.parse(page.rules);
					$.each(page.rules, function(i, rule){
						if(rule){
							flow.elements[rule.t] = rule;
						}
					});
				} catch(e){
					console.info(e);
				}
				page.flow = flow;
			}
		});
		
		onButtonClick();
		
		
		$tabs.find(".row").css("margin", 0);
		
		$(window).resize(function(){
			$("iframe").css("height", window.getHeight());
		});
		
		var $content = $('#componentAttr .tab-content');
		$content.get(0) && $content.css("border-bottom-style", "").find("iframe").animate({
			height : window.getHeight()
		});
	}
	
	function getHeight(){
		var $tools = $("#operate-tools"), n = 10;
		if($tools.data("show")){
			n = $tools.height() + 20;
		}
		return $(window).height() - n;
	}
	/**
	 * 按钮事件
	 */
	function onButtonClick(){
		
		var dataApprove = "data-approve", 
		disabled = "disabled";
		var $saveBtn = $("#btn-03"),//
		$submit = $(".btn[" + dataApprove + "]");
		
		if(!paramsObj.processId){//流程未启动【保存即提交】
			
			$saveBtn.attr(dataApprove, true).off("click").on("click", function(e){
				submitMsg.call(this, e, true);
			});
		
			$submit.attr(disabled, disabled);
		} else { //流程已启动
			
			$submit.removeAttr(disabled).off("click").on("click", submitMsg);
		
			$saveBtn.removeAttr(dataApprove).off("click").on("click", function(e) {
				saveMsg(null);
			});
		}
	}
	
	/**
	 * 提交方法
	 */
	function submitMsg(e, conf){
		var $this = $(this), disabled = "disabled";
		if($this.attr("data-approve") == "false"){
			if(!paramsObj.processId){
				alert("流程未启动!");
				return false;
			}
			FlowProcess.reject({processId:paramsObj.processId, taskId : "${param.taskId}"}, FlowProcess.callback = $Flow.callback);
			$Flow.callback = null;
			return false;
		}
		if (!conf && !window.confirm("确定" + $this.text() + "吗?")) {
			return false;
		}
			
		function ajaxRequest($data){
			var params = window.getParameters(), key = "审批结果", key0 = "审核结果";
			paramsObj.approve = $this.attr("data-approve");
			paramsObj[key] = paramsObj.approve;
			paramsObj[key0] = paramsObj.approve;
			$.extend(paramsObj, params);
			
			$this.attr(disabled, true);
			$.ajax({
				url : "${contextPath}/mx/form/workflow/defined/submit",
				type : "POST",
				data : paramsObj,
				dataType : 'JSON',
				success : function(ret) {
					if (ret) {
						execPageFunc($.extend(true, {}, paramsObj, ret));
						!$Flow.callback && alert("操作成功!");
						$this.removeAttr(disabled);
						if(!$Flow.callback && !conf && confirm("是否关闭界面?")){
							closeWindow();
						}
						paramsObj.defId = ret.defId;
						if(ret.processId){
							//reloadWindow(ret.processId);
							paramsObj.processId = ret.processId[0];
						}
						onButtonClick();
						$Flow.callback && $Flow.callback(paramsObj);
						
						$Flow.callback = null;
					}
				},
				error : function(ret){
					console.log(ret);
				}
			});
		}
		saveMsg(ajaxRequest);
	}
	
	/**
	 * 关闭界面
	 */
	function closeWindow(){
		if(window.parent && //
				window.parent.closePage){
			//关闭tab
			try {
				var doc = window.parent.document;
				$("div iframe", doc)//
					.each(function(i, frame){
					if(frame.contentWindow === window){
						var id = $(this, doc)//
							.closest("div").attr("id");
						
						var a = $("#link_" + id, doc)//
							.closest("li").find("div a");
						
						window.parent.closePage(a.get(0));
					}
				});
			} catch(e){
				console.log(e);
				window.close();
			} finally {
				
			}
		} else {
			window.close();
		}
	}
	
	function reloadWindow(processId){
		if(!paramsObj.processId){
			window.location.href = window.location.href + "&processId=" + processId;
		}
	}
	
	/**
	 * 保存方法
	 */
	function saveMsg(fn){
		/* var tab = $("#componentAttr").tabs("getSelected");
		var opts = $.data(tab, "tabs");
		if(opts.pageId){
			var $frame = $("#" + opts.pageId);
			var $window = $frame.get(0).contentWindow;
			if($window){
				var msg = $window.$.data($window.document.body, "saveMsg");
				if(msg){
					$.each(msg, function(i, btn){
						if(btn && btn.save){
							btn.save(fn);
						}
					});
				}
			}
		} */
		
		var msg = GetPageFunc("saveMsg");
		if(msg && msg.fn && !msg.idValue){
			$.each(msg.fn, function(i, btn){
				if(btn && btn.save){
					!msg.idValue && btn.save(fn, {});
				}
			});
		} else {
			fn && fn();
		}
	}
	
	function GetPageFunc(key){
		var tab = $("#componentAttr").tabs("getSelected");
		var opts = $.data(tab, "tabs");
		if(opts.pageId){
			var $frame = $("#" + opts.pageId);
			var $window = $frame.get(0).contentWindow;
			if($window){
				return {
					window : $window,
					pageId : opts.pageId,
					fn : $window.$.data($window.document.body, key),
					idValue : $window.callbackParam ? $window.callbackParam.idValue : null
				}
			}
		}
	}
	
	function execPageFunc(o){
		var msg = GetPageFunc("mtSubmit");
		if(msg && msg.fn){
			var spp = msg.window.SetPageParameter;
			if(o.STATUS !== undefined){
				o.STATUS = o.STATUS + "";
			}
			if(spp){
				spp("ID", o[msg.pageId]);
				spp("processId", o.processId[0]);
				spp("STATUS", o.STATUS);
			}
			msg.fn && msg.fn();
		}
	}
	
	function timeoutSelect(i){
		window.setTimeout(function(){
			window.selectTab(i);
		}, 10);
	}
	
	function getParameters(){
		var params = {};
		$tabs.find("div.tab-pane iframe").each(function(index, v) {
			var $this = $(this), $win = this.contentWindow;
			var opt = tabs[index];
			if (opt) {
				if (!opt.idValue) {
					var $document = $this.contents();
					var $idVal = $("." + opt.pageId, $document);
					opt.idValue = $idVal.attr("idvalue");
					if(!opt.idValue){
						if($win.callbackParam){
							opt.idValue = $win.callbackParam.idValue;
						}
					}
				}
				params[opt.pageId] = opt.idValue;
				var o;
				if($win && $win.processGetData){//TODO
					try{
						o = $win.processGetData();
					}catch(e){
						console.log(e);
					}
				}
				if($win && $win.getFlowDataSetParams){
					var dataSetParams = $win.getFlowDataSetParams();
					if(dataSetParams){
						params.nextSubmit = JSON.stringify({
							taskId : '',
							params : dataSetParams
						});
					}
				}
				if(o){
					for(var k in o){
						if(!(k == "id")){
							params[k] = o[k];
						}
					}
				}
			}
		});
		
		console.log(params);
		
		return params;
	}
	
	var getPageParams = (function(){
		var others = {};<c:forEach items="${pageParams }" var="map" varStatus="status">
		others["${map.key}"] = "${map.value}";</c:forEach>
		return function(params){
			return $.extend(true, {}, params, others);
		};
	})();
	
	function selectTab(index, fn){
		var tab = $tabs.tabs('getTab', index), key = "onload";
		if (tab && !tab.data(key)) {
			tab.data(key, true);
			var opt = tabs[index], params = {
				id : opt.pageId
			};
			opt.onload = fn;
			opt.variableKey && (params[opt.variableKey] = opt.idValue);
			$tabs.tabs('update', {
				tab : tab,
				options : $.extend({}, opt, {
					href : uri + $.param(window.getPageParams(params))
				})
			});
			return true;
		}
		return false;
	}
</script>
</html>