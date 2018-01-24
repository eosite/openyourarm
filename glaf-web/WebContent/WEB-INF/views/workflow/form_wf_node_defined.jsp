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
<title>流程元素属性定义</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/scripts/workflow/workflow-toolbox.js"></script>
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

.datagrid-header td,th {
	border-right: 1px dotted #ccc;
	font-size: 12px;
	font-weight: normal;
	background: #fafafa url('${contextPath}/images/datagrid_header_bg.gif')
		repeat-x left bottom;
	border-bottom: 1px dotted #ccc;
	border-top: 1px dotted #fff;
	height: 28px;
}

.datagrid-tbody td {
	border-right: 1px dotted #ccc;
	font-size: 12px;
	font-weight: normal;
	border-bottom: 1px dotted #ccc;
	border-top: 1px dotted #fff;
	height: 28px;
	padding-left: 5px;
}

.datagrid-tbody tr {
	
}

.datagrid-title td {
	font-size: 12px;
	font-weight: bolder;
	background: #E0ECFF repeat-x left bottom;
	height: 28px;
	vertical-align: middle;
}

.titleTd {
	background: #E0ECFF repeat-x left bottom;
}

td,th {
	display: table-cell;
	vertical-align: inherit;
}

#panelbar {
	margin: 0 auto;
}

li span {
	height: 30px;
	font-size: 12px;
	vertical-align: middle;
}
/*Tooltips*/ 
.tooltips{ 
position:relative; /*这个是关键*/ 
z-index:2; 
text-decoration:underline;
font-size: 13px;
margin-left: 5px;
} 
.tooltips:hover{ 
z-index:3; 
background:none; /*没有这个在IE中不可用*/ 
text-decoration:underline;
font-weight: bolder;
} 
.tooltips a{
display: none; 
} 
.tooltips:hover a{/*span 标签仅在 :hover 状态时显示*/ 
display:inline-block; 
} 
.removea{
font-weight: bolder;
color: red;
font-size: 15px;
cursor: pointer;
}
</style>
</head>
<body>
	<div
		style="width:100%;height:100%; margin: 0 auto;border:0px;overflow: auto;">
		<form name="propform" action="" method="post">
			<div id="content"
				style="width:100%;height:100%; margin: 0 auto;border:0px;overflow: auto;">
				<ul id="panelbar" style="border: 0px;">

				</ul>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		var contextPath = '${contextPath}';
		var taskId = '${taskId}';
		var taskName = '${taskName}';
		var description = '${description}';
		var webPath = '${webPath}';
		//页面加锁
		/*var locked=true;
		window.onload = function() {
		  locked=false;
		}; */
	$(document)
			.ready(
					function() {
			$
					.Toolbox(
							$("#panelbar"),
							{
								"url" : "${contextPath}/service/workflow/${param.elemType}/elemset",
								"contextPath" : contextPath
							},
							{
								"modelId" : "${param.modelId}",
								"resourceId" : "${param.resourceId}"
							}, "changePropertiesVal,bindChangeNameEvent,bindChangeSignModeEvent");
	      });
		function updateProperties(obj) {
			var valinput = $($(
					parent.$("#workflowView").find("iframe")[0].contentDocument)
					.find(".title")[0].next());
			if (valinput) {
				//valinput.bind("click",function(){
				//  alert(5555);
				// });
				// valinput.click(); 
				//valinput=valinput.find("input");
				// valinput.val(obj.value);
				//valinput.focus();
				//valinput.keyup();
				//模拟键盘输入
				//valinput.keypress();
				//valinput.change();
				//valinput.blur();
			}
		}
		function bindChangeNameEvent(){
			$("input[name='name']").bind("input propertychange",function(e){
				changeWorkflowDesignerProp.call(this, 1);
			});
			$("input[name='description']").bind("input propertychange",function(){
				changeWorkflowDesignerProp.call(this, 2);
			});
		}
		var  changeWorkflowDesignerProp = function(index){
			
			index = index || 1;
			
			if(index == 1 && //
					$(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} tspan").length>0)
						{
								$(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} tspan").text($(this).val());
						}
					//操作angular 对象值
					//parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope()
					var scope=parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope();
				
					scope.selectedItem.properties[index].value=$(this).val();
					
					scope.updatePropertyInModel(scope.selectedItem.properties[index]);
					scope.$apply();
		};
		function changePropertiesVal() {
			if ($("input[name='id']")&&taskId!="")
				$("input[name='id']").val(taskId);
			if ($("input[name='name']")&&taskName!="")
				$("input[name='name']").val(taskName);
			if ($("input[name='description']")&&description!="")
				$("input[name='description']").val(description);
		}
		function saveTaskProperties() {
			
			//var data= $($("#propcontent").find("iframe")[0].contentDocument.propform).serialize();
			
			if(propform.submitterTask && propform.submitterTask.checked){
				propform.submitterTask.value = 1;
			}
			
			var data = $(propform).serialize();
			if(data==undefined||data==null||data==''){
				return;
			}
			$
					.ajax({
						url : "${contextPath}/mx/form/workflow/nodeDefined/save?elemType=${param.elemType}&modelId=${param.modelId}&resourceId=${param.resourceId}",
						type : "POST",
						async : false,
						dataType : "json",
						data : data,
						success : function(data) {
							if (data.result == 1) {
								alert("保存成功");
							} else {
								alert("保存失败");
							}
						},
						error : function() {
							alert("保存异常");
						}

					});
		}
		function autoSaveTaskProperties() {
			//var data= $($("#propcontent").find("iframe")[0].contentDocument.propform).serialize();
			if(propform.submitterTask && propform.submitterTask.checked){
				propform.submitterTask.value = 1;
			}
			var data = $(propform).serialize();
			if(data==undefined||data==null||data==''){
				return;
			}
			if(data.id==''){
				alert("元素标识为空！");
				return;
			}
			$
					.ajax({
						url : "${contextPath}/mx/form/workflow/nodeDefined/save?elemType=${param.elemType}&modelId=${param.modelId}&resourceId=${param.resourceId}",
						type : "POST",
						async : false,
						dataType : "json",
						data : data,
						success : function(data) {
							if (data.result == 1) {
								console.log("保存成功");
							} else {
								console.log("保存失败");
							}
						},
						error : function() {
							console.log("保存异常");
						}

					});
		}
		var eventSrc;
		/**
		 *增加人员
		 */
		function addAssign() {
			//复制当前
			var trDom = window.event.srcElement.parentNode.parentNode.outerHTML;
			var trObj=$(trDom);
			trObj.find("input").val("");
			trObj.find("div").empty();
			$(window.event.srcElement.parentNode.parentNode).after(trObj);
		}
		/**
		 *删除人员
		 */
		function removeAssign() {
			//获取当前人员行数
			var tbDom = window.event.srcElement.parentNode.parentNode.parentNode;
			var trLen = tbDom.childNodes.length;
			if (trLen > 1) {
				$(window.event.srcElement.parentNode.parentNode).remove();
			} else {
				alert("需指定任务的执行人员");
			}
		}
		/**
		 *打开选择窗口
		 */
		function openAssignChooseDialog(obj) {
            eventSrc=window.event.srcElement;
            var nameObj=$(obj).prev().prev();
            var valObj=$(obj).prev();
			var assignType, title,val,nameVal;
			nameVal=encodeURIComponent(encodeURIComponent(nameObj.val()));
			val=encodeURIComponent(encodeURIComponent(valObj.val()));
			//获取assignType
			if ($(obj).closest("td").prev("td")
					&& $(obj).closest("td").prev("td").find("select")) {
				assignType = $($(obj).closest("td").prev("td").find("select"))
						.val();
				if (assignType == 'user') {
					title = "<img src=\"${contextPath}/images/title_window.png\" />&nbsp;&nbsp;选择用户";
				} else if (assignType == 'role') {
					title = "<img src=\"${contextPath}/images/title_window.png\" />&nbsp;&nbsp;选择角色";
				} else if(assignType == 'departRole') {
					title = "<img src=\"${contextPath}/images/title_window.png\" />&nbsp;&nbsp;部门角色";
				}else{
					title = "<img src=\"${contextPath}/images/title_window.png\" />&nbsp;&nbsp;岗位";
				}
			}
			parent
						.$("#dialog")
						.kendoWindow(
								{
									width : "1000px",
									height : "600px",
									actions : [ "Refresh", "Minimize",
											"Maximize", "Close" ],
									title : title,
									visible : false,
									modal : true,
									content : webPath
											+ "mx/form/workflow/assign/view?assignType="
											+ assignType + "&selectedVal=" +val+"&selectedNameVal="+nameVal,
									iframe : true
								});
			parent.$("#dialog").data("kendoWindow").setOptions({
				width : "1000px",
				height : "600px"
							});
			parent.$("#dialog").data("kendoWindow").open();
			parent.$("#dialog").data("kendoWindow").center();
		}
		function openCronDefinedDialog(obj){
		eventSrc=window.event.srcElement;
		var cronVal=$(obj).val();
		var title = "<img src=\"${contextPath}/images/time_go.png\" />&nbsp;&nbsp;<span "+
						" style=\"font-family:Lucida Calligraphy;font-size: 15px;font-weight: bolder;\">Cron</span>"+
						" <span style=\"font-size: 15px;font-weight: bolder;\">表达式生成工具</span>";
				parent
						.$("#dialog")
						.kendoWindow(
								{
									width : "1000px",
									height : "600px",
									actions : [ "Refresh", "Minimize",
											"Maximize", "Close" ],
									title : title,
									visible : false,
									modal : true,
									content : webPath + "mx/form/workflow/cron/view?cronVal="+cronVal,
									iframe : true
								});
			parent.$("#dialog").data("kendoWindow").setOptions({
				width : "1000px",
				height : "600px"
							});
			parent.$("#dialog").data("kendoWindow").open();
			parent.$("#dialog").data("kendoWindow").center();
		}
		/**
		*打开表达式定义窗口
		*/
		function openExpressionDialog(obj){
			eventSrc=window.event.srcElement;
			var title = " <span style=\"font-size: 15px;font-weight: bolder;\">条件表达式定义工具</span>";
					parent
							.$("#dialog")
							.kendoWindow(
									{
										width : "800px",
										height : "590px",
										actions : [ "Refresh", "Minimize",
												"Maximize", "Close" ],
										title : title,
										visible : false,
										modal : true,
										content : webPath + "/mx/expression/defined/view?category=workflow&getVarFn=getWorkflowVariableJSON&retFn=setExpPropertyVal&initExpFn=getExproertyVal",
										iframe : true
									});
				parent.$("#dialog").data("kendoWindow").setOptions({
				   width : "800px",
					height : "590px"
						});
				parent.$("#dialog").data("kendoWindow").open();
				parent.$("#dialog").data("kendoWindow").center();
			
		}
		/**
		*打开表达式定义窗口（根据流程关键字获取流程变量）
		*/
		function openSubProcessExpressionDialog(obj){
			eventSrc=window.event.srcElement;
			var title = " <span style=\"font-size: 15px;font-weight: bolder;\">条件表达式定义工具</span>";
					parent
							.$("#dialog")
							.kendoWindow(
									{
										width : "800px",
										height : "590px",
										actions : [ "Refresh", "Minimize",
												"Maximize", "Close" ],
										title : title,
										visible : false,
										modal : true,
										content : webPath + "/mx/expression/defined/view?category=workflow&getVarFn=getWorkflowVariableJSON&retFn=setSubProcessParamExpPropertyVal&initExpFn=getExproertyVal",
										iframe : true
									});
				parent.$("#dialog").data("kendoWindow").setOptions({
				   width : "800px",
					height : "590px"
						});
				parent.$("#dialog").data("kendoWindow").open();
				parent.$("#dialog").data("kendoWindow").center();
			
		}
		/*
		 *设置属性值
		 */
		function setPropertyVal(val,nameVal) {
		    var valObj=$(eventSrc).prev();
		    var nameObj=$(eventSrc).prev().prev();
			nameObj.val(nameVal);
			valObj.val(val);
			var assignType=$(eventSrc).closest("tr").find("select[name='assignType']").val();
			var joinc=",";
			if(assignType=='departRole'){
			  joinc="|";
			}
			//重新绘制
			var detailObj=$($(eventSrc).parent()).find("div");
			detailObj.empty();
			$.each(val.split(joinc),function(i,item){
			  var spanObj=$("<span></span>");
			  spanObj.addClass("tooltips");
			  spanObj.append(nameVal.split(joinc)[i]);
			  var aObj=$("<a></a>");
			  aObj.addClass("removea");
			  aObj.attr("onclick","removeAssignDetail('"+item+"','"+nameVal.split(joinc)[i]+"')");
			  aObj.text("×");
			  spanObj.append(aObj);
			  detailObj.append(spanObj);
			});
		}
		/*
		 *设置时间表达式属性值
		 */
		function setCronPropertyVal(val){
		   $(eventSrc).val(val);
		}
		//删除选中的角色、人员
		function removeAssignDetail(val,nameVal){
		    var assignType=$(eventSrc).closest("tr").find("select[name='assignType']").val();
			var joinc=",";
			if(assignType=='departRole'){
			  joinc="|";
			}
		   var eventSrc=window.event.srcElement;
		   //获取单元格对象
		   var tdObj=$(eventSrc).closest("td");
		   //获取valObj,nameObj
		   var nameObj=tdObj.find("input[name='assignval']");
		   var nameValArr=nameObj.val().split(joinc);
		   nameVal=removeVal(nameValArr,nameVal,joinc);
		   nameObj.val(nameVal);
		   var valObj=tdObj.find("input[name='assignvalhidden']");
		   var valArr=valObj.val().split(joinc);
		   val=removeVal(valArr,val,joinc);
		   valObj.val(val);
		   $(eventSrc).closest("span").remove();
		}
		//移除数组中的指定值，并返回以joinc字符连接的字符串
		function removeVal(arr,val,joinc){
		   $.each(arr,function (i,item){
		     if(item==val){
		        arr.splice(i, 1);
		     }
		   });
		   return arr.join(joinc);
		}
		
		
		/**
		 *增加流程变量
		 */
		function addVariable() {
			//复制当前
			var trDom = window.event.srcElement.parentNode.parentNode.outerHTML;
			var trObj=$(trDom);
			trObj.find("input").val("");
			trObj.find("div").empty();
			$(window.event.srcElement.parentNode.parentNode).after(trObj);
			var tbody=$(window.event.srcElement.parentNode.parentNode.parentNode);
			//获取变量代码隐藏域
		//	var variableCodeObj=tbody.find("input[name='varcode']");
			
			//获取倒数第二行变量代码value值
			//var variableMaxCode=$(variableCodeObj[variableCodeObj.length-2]).val();
			//自动生成当前行变量代码value值
			//var currVarCode="param"+(parseInt(variableMaxCode.substring(5,variableMaxCode.length))+1);
			//$(variableCodeObj[variableCodeObj.length-1]).val(currVarCode);
			var maxCode = 0;
			/**
			 * 获取所有代码数字的最大值
			 */
			tbody.find("input[name='varcode']").each(function(i, v){
				v.value && (maxCode = Math.max(maxCode, (this.value.substring(5, this.value.length))*1));
			});
			
			trObj.find("input[name='varcode']").val("param" + (maxCode+1));
			
		}
		/**
		 *删除流程变量
		 */
		function removeVariable() {
			//获取当前人员行数
			var tbDom = window.event.srcElement.parentNode.parentNode.parentNode;
			var trLen = tbDom.childNodes.length;
			if (trLen > 1) {
				$(window.event.srcElement.parentNode.parentNode).remove();
			} else {
				var trDom = window.event.srcElement.parentNode.parentNode.outerHTML;
				var trObj=$(trDom);
				trObj.find("input").val("");
			}
		}
		function getWorkflowVariableJSON(){
			var retData;
			$
			.ajax({
				url : "${contextPath}/service/workflow/variable?modelId=${param.modelId}&resourceId=${param.modelId}",
				type : "POST",
				async : false,
				dataType : "json",
				success : function(data) {
					if (data) {
						retData= data;
					} else {
						alert("获取变量定义失败");
					}
				},
				error : function() {
					console.log("获取变量定义异常");
				}

			});
			return retData;
		}
		//获取子流程变量
		function getSubProcessVariableJSON(){
			var retData;
			var processKeyVal=$("input[name='calledProcessKey']").val();
			$
			.ajax({
				url : "${contextPath}/service/workflow/variable/key?processKey="+processKeyVal,
				type : "POST",
				async : false,
				dataType : "json",
				success : function(data) {
					if (data) {
						retData= data;
					} else {
						alert("获取变量定义失败");
					}
				},
				error : function() {
					console.log("获取变量定义异常");
				}

			});
			return retData;
		}
		/**
		*设置流程条件表达式值
		*/
		function setExpPropertyVal(val){			
			    var eventObj=$(eventSrc);
			    var expVal,expActVal,varVal;
			    if(val){
					expVal = val.expVal;
					expActVal = val.expActVal;
					varVal = val.varVal;
					eventObj.val(expVal);
					eventObj.next().val(expActVal);
					//遍历表达式变量
					if(varVal){
						var key,value,obj,valObj;
						var arr=new Array();
						$.each(varVal,function(i,item){
							obj=new Object();
							key=item.key;
							value=item.value;
							obj.key=key;
							valObj=new Object();
							valObj.name=value.name;
							valObj.value=value.value;
							valObj.code=value.code;
							valObj.dType=value.dType;
							obj.value=valObj;
							arr.push(obj);
						});						
						eventObj.next().next().val(JSON.stringify(arr));
					}
					
			    }
                  //获取当前行
				 var trObj=eventObj.closest("td");
				 var tbody=eventObj.closest("tbody");
				 tbody.find("input[name='name']").val(expVal);
				 tbody.find("input[name='description']").val(expVal);
				 if($(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} tspan").length>0)
					{
						$(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} tspan").text(expVal);
					}
				 if($(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} span").length>0)
					{
						$(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} span").text(expVal);
					}
				 //操作angular 对象值
				 //parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope()
				 var scope=parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope();
				 scope.selectedItem.properties[1].value=expVal;
				 scope.selectedItem.properties[2].value=expVal;
				 scope.selectedItem.properties[3].value=expActVal;
				 scope.updatePropertyInModel(scope.selectedItem.properties[1]);
				 scope.updatePropertyInModel(scope.selectedItem.properties[2]);
				 scope.updatePropertyInModel(scope.selectedItem.properties[3]);
				 scope.$apply();
			    parent.closeAssignChooseDialog();
		}
	  /**
		*设置流程条件表达式值
		*/
		function setSubProcessParamExpPropertyVal(val){	
			    var eventObj=$(eventSrc);
			    var expVal,expActVal,varVal;
			    if(val){
					expVal = val.expVal;
					expActVal = val.expActVal;
					varVal = val.varVal;
					eventObj.val(expVal);
					eventObj.next().val(expActVal);
					//遍历表达式变量
					if(varVal){
						var key,value,obj,valObj;
						var arr=new Array();
						$.each(varVal,function(i,item){
							obj=new Object();
							key=item.key;
							value=item.value;
							obj.key=key;
							valObj=new Object();
							valObj.name=value.name;
							valObj.value=value.value;
							valObj.code=value.code;
							valObj.dType=value.dType;
							obj.value=valObj;
							arr.push(obj);
						});						
						eventObj.next().next().val(JSON.stringify(arr));
					}
					
			    }
			    parent.closeAssignChooseDialog();
		}
		/**
		*获取流程条件表达式值 提供给表达式定义工具初始化
		*/
	  function getExproertyVal(){
		  
		  var eventObj=$(eventSrc);
		  var initData=new Object();
		  if(eventObj.val()!=""){
			  initData.expVal=eventObj.val();
			  initData.expActVal=eventObj.next().val();
			  var json=JSON.parse(eventObj.next().next().val());
			  var arr=new Array();
			  var obj;
			  $.each(json,function(i,item){
				  obj=new Object();
				  obj.key=item.key;
				  obj.value=item.value;
				  arr.push(obj);
			  });
			  initData.varVal=arr;
		  }
		  return initData;
	  }
	 /**
	  * 快速设置条件表达式的值 通过 不通过
	  */
	 function setExpression(approve){
		 var eventSrc=window.event.srcElement;
		 //获取当前行
		 var trObj=$(eventSrc).closest("td");
		 var tbody=$(eventSrc).closest("tbody");
		 var expStr,expActStr;
		 if(approve=='1'){
			 expStr="审核结果==true";	
			 expActStr="approve==true";
		 }else if(approve=='0'){
			 expStr="审核结果==false";
			 expActStr="approve==false";
		 }
		 else if(approve==''){
			 expStr="";
			 expActStr="";
		 }
		 var expressionObj=trObj.find("input[name='expression']");
		 expressionObj.val(expStr);
		 tbody.find("input[name='name']").val(expStr);
		 tbody.find("input[name='description']").val(expStr);
		 var expressionActObj=trObj.find("input[name='expressionAct']");
		 expressionActObj.val(expActStr);
		 if($(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} tspan").length>0)
			{
				$(parent.$("#workflowView").find("iframe")[0].contentDocument).find("#svg-${param.resourceId} tspan").text(expStr);
			}
		 //操作angular 对象值
		 //parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope()
		 var scope=parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope();
		 scope.selectedItem.properties[1].value=expStr;
		 scope.selectedItem.properties[2].value=expStr;
		 scope.selectedItem.properties[3].value=expActStr;
		 scope.updatePropertyInModel(scope.selectedItem.properties[1]);
		 scope.updatePropertyInModel(scope.selectedItem.properties[2]);
		 scope.updatePropertyInModel(scope.selectedItem.properties[3]);
		 scope.$apply();
	  }
	 /**
	 *弹出选择子流程页面
	 */
	 function openProcessSelectDialog(obj){
		 eventSrc=window.event.srcElement;
		 var title="选择子流程";
		 var selectedProcess=$(obj).val();
		 var url=webPath + "mx/workflow/management/view/process-definition-choose";
		 if(selectedProcess!=""){
		  url+="?modelKey="+selectedProcess;
		 }
			parent.$("#dialog")
			.kendoWindow(
					{
						width : "1200px",
						height : "550px",
						actions : [ "Refresh", "Minimize",
								"Maximize", "Close" ],
						visible : false,
						modal : true,
						content : url,
						iframe : true
			});
			parent.$("#dialog").data("kendoWindow").setOptions({
				width : "1200px",
				height : "550px",
						});
			parent.$("#dialog").data("kendoWindow").title(title);
			parent.$("#dialog").data("kendoWindow").open();
			parent.$("#dialog").data("kendoWindow").center();
	 }
	 //设置子流程值
	 function setSubProcessVal(processKey,processName){
		 $(eventSrc).val(processKey);
		 var scope=parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope();
		 var idInput=$("input[name='id']");
		 scope.selectedItem.properties[0].value=idInput.val();
		 scope.selectedItem.properties[1].value=processName;
		 scope.selectedItem.properties[2].value=processName;
		 scope.selectedItem.properties[6].value=processKey;
		 scope.updatePropertyInModel(scope.selectedItem.properties[0]);
		 scope.updatePropertyInModel(scope.selectedItem.properties[1]);
		 scope.updatePropertyInModel(scope.selectedItem.properties[2]);
		 scope.$apply();
		 var nameInput=$("input[name='name']");
		 if(nameInput&&nameInput.val()==''){
			 nameInput.val(processName);
		 }
	 }
     /**
	 *弹出子流程 参数设置 变量选择页面
	 */
	 function openVariableDialog(processKey,obj){
		 eventSrc=window.event.srcElement;
		 var title="选择流程变量";
		 var selectedVariable=$(obj).val();
		 var processKeyVal=$("input[name='"+processKey+"']").val();
		 var url=webPath + "mx/workflow/management/view/variable-choose?processKey="+processKeyVal;
		 if(selectedVariable!=""){
		  url+="&selectedVariable="+selectedVariable;
		}
	    parent.$("#dialog")
			.kendoWindow(
					{
						width : "1200px",
						height : "550px",
						actions : [ "Refresh", "Minimize",
								"Maximize", "Close" ],
						visible : false,
						modal : true,
						content : url,
						iframe : true
			});
			parent.$("#dialog").data("kendoWindow").setOptions({
				width : "1200px",
				height : "550px",
						});
			parent.$("#dialog").data("kendoWindow").title(title);
			parent.$("#dialog").data("kendoWindow").open();
			parent.$("#dialog").data("kendoWindow").center();
	 }
	 /**
	 *弹出子流程 输出参数目标设置 变量选择页面
	 */
	 function openWorkflowVariableDialog(obj){
		 eventSrc=window.event.srcElement;
		 var title="选择流程变量";
		 var selectedVariable=$(obj).val();
		 var modelId="${param.modelId}";
		 var resourceId="${param.resourceId}";
		 var url=webPath + "mx/workflow/management/view/variable-choose?modelId="+modelId+"&resourceId="+resourceId;
		 if(selectedVariable!=""){
		  url+="&selectedVariable="+selectedVariable;
		}
	    parent.$("#dialog")
			.kendoWindow(
					{
						width : "1200px",
						height : "550px",
						actions : [ "Refresh", "Minimize",
								"Maximize", "Close" ],
						visible : false,
						modal : true,
						content : url,
						iframe : true
			});
			parent.$("#dialog").data("kendoWindow").setOptions({
				width : "1200px",
				height : "550px",
						});
			parent.$("#dialog").data("kendoWindow").title(title);
			parent.$("#dialog").data("kendoWindow").open();
			parent.$("#dialog").data("kendoWindow").center();
	 }
	 //子流程参数设置 变量值
	 function setProcessVariable(data){
		 $(eventSrc).val(data.name);
		 $(eventSrc).next().val(data.code);
	 }
	 
	 function bindChangeSignModeEvent(){
		 $("input[name='actmode']").bind("click",changeSignModel);
		 $("select[name='countersigntype']").bind("change",changeCountersigntype);
	 }
	 function changeSignModel(){
		 var signModel=$(this).val();
		 var countersigntype=$("select[name='countersigntype']").length>0?$("select[name='countersigntype']").val():"";
		 setSignMode(signModel,countersigntype);
	 }
	 function changeCountersigntype(){
		 var signModel=$("input[name='actmode']:checked").val();
		 var countersigntype=$(this).val();
		 setSignMode(signModel,countersigntype);
	 }
	 //设置单签会签时触发事件
	 function setSignMode(val,countersigntype){
		 var scope=parent.$("#workflowView").find("iframe")[0].contentWindow.angular.element('#propertiesHelpWrapper').scope();
		 if(val=='singlesign'){
			 scope.selectedItem.properties[6].value='none';
			 scope.updatePropertyInModel(scope.selectedItem.properties[6]);
			 scope.selectedItem.properties[8].value="";
			 scope.selectedItem.properties[9].value="";
			 scope.selectedItem.properties[12].value="";
			 scope.updatePropertyInModel(scope.selectedItem.properties[8]);
			 scope.updatePropertyInModel(scope.selectedItem.properties[9]);
			 scope.updatePropertyInModel(scope.selectedItem.properties[12]);
		 }else{
			 if(countersigntype=='sequential')
			 {
				 scope.selectedItem.properties[6].value='Sequential';
			 }
		     else
			 {
				 scope.selectedItem.properties[6].value='Parallel'; 	
			 }				 
			 scope.selectedItem.properties[8].value="$"+"{workFLowDefinedService.getUserTaskAssigns(execution)}";
			 scope.updatePropertyInModel(scope.selectedItem.properties[6]);
			 scope.updatePropertyInModel(scope.selectedItem.properties[8]);
		 } 
		 scope.$apply();
	 }
	</script>

</body>
</html>