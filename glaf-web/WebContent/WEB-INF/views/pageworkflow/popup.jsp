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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>页面流测试-联动控件</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
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
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}
</style>
<script type="text/javascript">
	var workflowContext = {
			variables : {},
			processInstanceId : 0,
			currTaskInstanceid : 0,
			endFlag : 0
	};
	var pageContext = {
		   param : {
			province : ""
		},
		  pageId:2,
		  variables:{},
		  datasource:{}
	};
		//启动流程
		var startWorkFlow = function() {
			$.ajax({
				url : "${contextPath}/rs/page/workflow/startPageWorkFlow",
				type : "POST",
				async : true,
				dataType : "json",
				data : {
					pageId : 2,
				   //此处为页面定义唯一标识
				    processDefinitionId:"popup_activiti",
				    conValue:$('#conValue').val()
				},
				success : function(data) {
					if (data) {
						var url=data.url;
						var type=data.type;
						var param=data.param;
						workflowContext.processInstanceId=data.processInstanceId;
						workflowContext.endFlag=data.endFlag;
						pageControl(type,url,param);
					}
				},
				error : function() {
                        
				}
			});
		};
		//运行流程
		var runWorkFlow = function() {
			$.ajax({
				url : "${contextPath}/rs/page/workflow/runPageWorkFlow",
				type : "POST",
				async : true,
				dataType : "json",
				data : {
					pageId : 2,//此处为页面定义唯一标识
					processInstanceId : workflowContext.processInstanceId
				//流程实例Id
				},
				success : function(data) {
					if (data) {
						//更新前段流程上下文 检验流程是否运行结束
						var url=data.url;
						var type=data.type;
						var param=data.param;
						var option=data.option;
						workflowContext.processInstanceId=data.processInstanceId;
						workflowContext.endFlag=data.endFlag;
						pageControl(type,url,param,option);
					}
				},
				error : function() {

				}
			});
		};
		//结束流程
		var endWorkFlow = function(param) {

		};
		$(document).ready(function() {
			$("#conValue").kendoDropDownList({
			 dataTextField: "Name",
             dataValueField: "Value"
		});
		  	$("#bt").kendoButton({
			imageUrl : "${contextPath}/images/okay.png"
		   });
		  var bt = $("#bt").data("kendoButton");
		bt.bind(
						"click",
						function(e) {
                              startWorkFlow();
	});
		 var dataSource = new kendo.data.DataSource({
		      data: [ {Name:"0",Value:"0"}, {Name:"50",Value:"50"},{Name:"100",Value:"100"} ]
		    });
		    var dropdownlist = $("#conValue").data("kendoDropDownList");
		    dropdownlist.setDataSource(dataSource);
		    dropdownlist.select(0);
		});
	 
	  var pageControl = function(type,url,param,option) {
			var func = eval(type+'Page');
			func(url, param,option);
		}; 
	 var openPage=function(url,param,option)
	 {
	     window.open(url);
	     if (workflowContext.endFlag != 1) {
				runWorkFlow();
			}
	 };
	 var redirectPage=function(url,param,option)
	 {
	     window.location.href=url;
	 };
</SCRIPT>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width:180px;text-align: left;vertical-align: middle;">
						<span
						style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;"></span>
						<span style="font-size: 16px;font-weight: bolder;">页面跳转</span>
					</td>
					<td style="text-align:right;"></td>
				</tr>
			</table>
		</div>
		<div>
		     <div id="tabstrip" style="border: 0px;height:100%;width:99%;">
					<ul>
						<li id="Tab1">演示实例</li>
						<li id="Tab2">流程图</li>
						<li id="Tab3">流程实例</li>
					</ul>
			        <div style="height:550px; width: 98%;overflow-y:auto;overflow-x: hidden;">
			      	<table>
				<tr>
					<td width="200" height="50"><input id="conValue"
						style="vertical-align: middle;"></td>
					<td width="200" height="50"><button id="bt">页面跳转</button></td>
				</tr>
			</table>
			</div>
			<div style="height:550px; width: 98%;overflow-y:auto;overflow-x: hidden;">
			    <iframe src="/glaf/mx/activiti/image?processDefinitionId=popup_activiti:5:66528"  style="height:100%; width: 100%;border: 0px;"></iframe>
			</div>
			<div style="height:550px; width: 98%;overflow-y:auto;overflow-x: hidden;">
			    <iframe id="proInst"  style="height:100%; width: 100%;border: 0px;"></iframe>
			</div>
			</div>
		
		</div>
	</div>
	<script type="text/javascript">
		$("#vertical").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsed : false,
				resizable : false,
				scrollable : false,
				size : "40px"
			}, {
				collapsed : false,
				scrollable : false
			} ]
		});
			//Tab初始化
		$("#tabstrip").kendoTabStrip({
			tabPosition : "bottom",
			scrollable : false,
			animation : {
				open : {
					effects : "fadeIn"
				}
			},
			select: onSelect
		});
		var tabToActivate = $("#Tab1");
		$("#tabstrip").kendoTabStrip().data("kendoTabStrip").activateTab(
				tabToActivate);
		//卡片选中后隐藏与现实处理
		function onSelect(e)
		{
		        if(e.item.id=='Tab3'&&workflowContext.processInstanceId>0)
		   {
		     $('#proInst').attr("src","/glaf/mx/activiti/task?processInstanceId="+workflowContext.processInstanceId);
		     }
		}
	</script>
</body>
</html>