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
<title>表单工作流关联配置</title>
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
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width:100%;border:0px;">
				<tr>
					<td style="width:350px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/images/workflow_small.png"
						style="vertical-align: middle;width: 32px;" /> <span
						style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">WorkFlow</span>
						<span style="font-size: 16px;font-weight: bolder;">表单工作流定义工具</span></td>
					<td style="vertical-align: middle;">
						<div id="toolbar" style="border:0px;text-align:right;"></div>
					</td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="horizontal" style="height:100%; width: 100%;">
				<div id="workflowView"></div>
				<div id="formDefined"  style="border:0px;">
				    <div id="head" class="k-header">
						<table style="width:100%;border:0px;">
							<tr>
								<td style="text-align: left;vertical-align: middle;width:100px;"><img
									src="${contextPath}/images/application-icon.png"
									style="vertical-align: middle;" /> <span
									style="font-size: 13px;font-weight: bolder;">流程属性</span>
								    
								</td>
								<td style="vertical-align: middle;">
									<div id="toolbar" style="border:0px;text-align:left;"><button id="button" type="button">保存</button></div>
								</td>
							</tr>
						</table>
					</div>
					<div id="propcontent">

					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="dialog"></div>
	<script type="text/javascript">
		var $workflowData={};
		var pageId = '${param.pageId}';
		var contextPath = '${contextPath}';
		var webPath = '${webPath}';
		var modelId='${param.modelId}';
		//初始化布局
		window.MSPointerEvent = null;
		window.PointerEvent = null;
		var mainHeight = $(window).height();
		var mainWidth = $(window).width();
		$('#vertical').height(mainHeight);
		$("#vertical").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsed : true,
				resizable : false,
				scrollable : false,
				collapsible : true,
				size : "40px"
			}, {
				collapsed : false,
				scrollable : false
			} ]
		});
		var splitter1=$("#horizontal").kendoSplitter({
			panes : [ {
				collapsed : false,
				collapsible : true,
				collapsedSize : "0px",
				max : "80%",
				resizable : false,
				size : "70%",
				scrollable : true
			}, {
				collapsible : true,
				scrollable : false
			} ]
		});
		var splitter2 =$("#formDefined").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsed : false,
				resizable : false,
				scrollable : false,
				collapsible : false,
				size : "30px"
			}, {
				collapsed : false,
				scrollable : true
			} ]
		});
		
		function ajaxUrlOpen(splitter,id,url) {
			//验证url是否使用相对路径，如果使用相对路径则补全
			if (url.indexOf("http") < 0) {
				url = '${webPath}' + url;
			}
			splitter.ajaxRequest("#"+id, url);
		}
		 splitter1 = splitter1.data("kendoSplitter");
		function openWorkFlowDesigner(){
		   ajaxUrlOpen(splitter1,"workflowView","/workflow/modeler.html?modelId=${param.modelId}&definedType=workflow&modelName=${param.modelName}&modelKey=${param.modelKey}"); 
		}
		splitter2 = splitter2.data("kendoSplitter");
		
		function openPropertiesEdit(url){
			openPropertiesEdit00.lock(500, url);
		}
		
		function openPropertiesEdit00(url){
		   //ajaxUrlOpen(splitter2,"propcontent",url);
		   if($("iframe").eq(1).get(0)&&$("iframe").eq(1).attr("src")!=''){
		     $("iframe").eq(1).removeAttr("src");
			 $("iframe").eq(1).attr("src",'${webPath}' +url);
		   }else{
			  ajaxUrlOpen(splitter2,"propcontent",url); 
		   }
		}
		//ajaxUrlOpen("workflowView","/workflow/diagram-viewer/view.html?processDefinitionId=process:19:161505");
		openWorkFlowDesigner();
		//保存按钮初始化
		$("#button").kendoButton({
			imageUrl : "${contextPath}/images/save_as.png"
		});
		var button = $("#button").data("kendoButton");
		//按钮点击事件方法绑定
		button.bind("click",function(e) {
				$("#propcontent").find("iframe")[0].contentWindow.saveTaskProperties();
		});
		//设置属性值
		function setPropertyVal(val,nameVal){
		   $("#propcontent").find("iframe")[0].contentWindow.setPropertyVal(val,nameVal);
		}
		function setCronPropertyVal(val){
		  $("#propcontent").find("iframe")[0].contentWindow.setCronPropertyVal(val);
		}
		//设置流程条件表达式值
		function setSubProcessParamExpPropertyVal(val){
		   $("#propcontent").find("iframe")[0].contentWindow.setSubProcessParamExpPropertyVal(val);
		}
		//关闭弹出窗
		function closeAssignChooseDialog(){
		    $("#dialog").data("kendoWindow").close();
		}
		//获取流程变量值
		function getWorkflowVariableJSON(){
			return $("#propcontent").find("iframe")[0].contentWindow.getWorkflowVariableJSON();
		}
		//获取流程变量值（通过流程关键字）
		function getSubProcessVariableJSON(){
			return $("#propcontent").find("iframe")[0].contentWindow.getSubProcessVariableJSON();
		}
		//获取流程条件表达式返回值
		function setExpPropertyVal(val){
			$("#propcontent").find("iframe")[0].contentWindow.setExpPropertyVal(val);
		}
		//获取流程条件表达式初始值用于表达式定义工具
		function getExproertyVal(){
			return $("#propcontent").find("iframe")[0].contentWindow.getExproertyVal();
		}
		//更新流程定义属性值
		function updateActivitiSource() {
				$.ajax({
					url : contextPath+"/service/workflow/updateActivitiSource",
					type : "post",
					async : false,
					dataType : "json",
					data : {
						modelId : modelId
					},
					success : function(data) {
						if (data) {
							if (data.result) {
								if (data.result == 1) {
									console.log("更新流程定义属性值成功");
								} else if (data.result == 0) {
									console.log("更新流程定义属性值失败");
								} else if (data.result == -1) {
									console.log("更新流程定义属性值异常");
								}
							}
						}
					},
					error : function() {

					}

				});
		}
		//设置子流程值
		function setSubProcessVal(val,nameVal){
			$("#propcontent").find("iframe")[0].contentWindow.setSubProcessVal(val,nameVal);
		}
		//子流程参数定义设置流程变量值
		function setProcessVariable(data){
			 $("#propcontent").find("iframe")[0].contentWindow.setProcessVariable(data);
		 }
		//关闭窗口
		function closeDialog(){
		    $("#dialog").data("kendoWindow").close();
		}
		//初始加载
		function loadFunc(e){
			$(e).data("locked", false);
	   }
		
		
		
		
		/**
		 * 指定时间内不可重复执行
		 * 异步执行。。。
		 eg: function todo(type){}
			todo.lock(500, "java")
		 */
		Function.prototype.lock = function(time){					
			var self = this, args = Array.prototype.slice
						.call(arguments, 1),//
				exec = function(){
					self.apply(self, args);
					clear();
			}, clear = function(){
				self._1 && //
				window.clearTimeout(self._1);
			};
			if(time){
				var	set = function(){
					clear();
					self._1 = window.setTimeout(exec, time);
				};			
				set();
			} else {
				exec();
			}
		};
	</script>
</body>
</html>