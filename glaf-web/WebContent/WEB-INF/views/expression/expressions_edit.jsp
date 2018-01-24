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
<title>表达式编辑器</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/filterObj.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/clipboardByExpress.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/expression.js"></script>
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
<script>
	var notEempty = "${param.notEempty}";
    var notValidate = "${param.notValidate}"; 
	var contextPath = "${contextPath}";
	//表达式Json数据
	var expressions = '${expressions}';
	//Json字符串转Json对象
	var expJson = eval(expressions);
	
	var setting = {
		data : {
			key : {
				title : "t"
			},
			simpleData : {
				enable : true
			}
		},
		callback : {
			//声明单击回调事件
			beforeClick : beforeClick
		}
	};
	//用于记录选中的对象
	var map = new Map();
	//单击回调事件
	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		//如果是父节点单击则展开，如果是叶子节点单击则在输入框中插入相应的表达式
		if (treeNode.isParent) {
			zTree.expandNode(treeNode);
			return false;
		} else {
			insertAtCursor($("#expressionEditor")[0], treeNode.value);
			map.put(treeNode.value, filterNode(treeNode));
			$("span[name='desSpan']")
					.html(
							"<font  style='font-family:Lucida Calligraphy;font-size: 15px;font-weight: bolder;color:red;'>使用说明：</font><br><br>"
									+ treeNode.t);
			return true;
		}
	}
	function variable(variableName, dataType) {
		this.variableName = variableName;
		this.dataType = dataType;
	}
	/**
	构造变量JSON对象
	 */
	function structVariableJSONString() {
		var arr1 = new Array();
		if(map.elements){
		$.each(map.elements, function(i, obj) {
			if (obj.value.value != obj.value.code) {
				arr1.push(new variable(obj.value.code, obj.value.dType));
			}
		});
		}
		return JSON.stringify(arr1);
	}
	var zNodes = expJson;
	//开始初始化数据字段树
	var fieldJson = [ {
		id : 1,
		pId : 0,
		name : "数据源1",
		t : '',
		open : false,
		isParent : true
	}, {
		id : 11,
		pId : 1,
		name : "试验任务",
		t : '',
		open : false,
		isParent : true
	}, {
		id : 111,
		pId : 11,
		name : "名称",
		t : '',
		dType : 'string',
		code : "~F{db1.cell_useradd12.column1}",
		value : "~F{数据源1.试验任务.名称}",
		isParent : false
	}, {
		id : 112,
		pId : 11,
		name : "备注",
		t : '',
		dType : 'string',
		code : "~F{db1.cell_useradd12.column2}",
		value : "~F{数据源1.试验任务.备注}",
		isParent : false
	}, {
		id : 2,
		pId : 0,
		name : "数据源2",
		t : '',
		open : false,
		isParent : true
	}, {
		id : 21,
		pId : 2,
		name : "试验任务",
		t : '',
		open : false,
		isParent : true
	}, {
		id : 211,
		pId : 21,
		name : "名称",
		t : '',
		dType : 'string',
		code : "~F{db2.cell_useradd12.column1}",
		value : "~F{数据源2.试验任务.名称}",
		isParent : false
	}, {
		id : 212,
		pId : 21,
		name : "备注",
		t : '',
		dType : 'string',
		code : "~F{db2.cell_useradd12.column1}",
		value : "~F{数据源2.试验任务.备注}",
		isParent : false
	} ];
	var fieldzNodes = fieldJson;
	//开始初始化页面元素树
	var pageAttrzNodes = [ {
		id : 1,
		pId : 0,
		name : "文本框",
		t : '',
		dType : 'string',
		code : "~M{text1}",
		value : "~M{文本框_text1}",
		isParent : false
	}, {
		id : 2,
		pId : 0,
		name : "静态文本",
		t : '',
		dType : 'string',
		code : "~M{span1}",
		value : "~M{静态文本_span1}",
		isParent : false
	}, {
		id : 3,
		pId : 0,
		name : "下拉列表",
		t : '',
		dType : 'string',
		code : "~M{select1}",
		value : "~M{下拉列表_select1}",
		isParent : false
	}, ];
	//var initExpFn = '${param.initExpFn}';
	//var initActExpFn = '${param.initActExpFn}';
	//var initVarFn = '${param.initVarFn}';
	//初始化数据调用方法名
	var initExpFn = '${param.initExpFn}';
	$(document).ready(
			function() {
				$.fn.zTree.init($("#expTree"), setting, zNodes);

				var parent = window.opener || window.parent;

				var getFn = "${param.getFn}", data, initData;

				if (getFn) {
					data = parent[getFn]();
					$.fn.zTree.init($("#fieldTree"), setting, data);
				} else
					$.fn.zTree.init($("#fieldTree"), setting, fieldzNodes);

				var getPageAttrFn = "${param.getPageAttrFn}";//页面元素菜单初始化
				if(getPageAttrFn){
					pageAttrzNodes = parent[getPageAttrFn]();
					console.log(pageAttrzNodes);
				}
				$.fn.zTree.init($("#pageAttrTree"), setting, pageAttrzNodes);
				
				//获取流程变量
				var getVarFn="${param.getVarFn}";
				//模态窗口
				if (window.parent.location != undefined
						&& window.parent.location != window.location) {
					if (getFn) {
						data = parent[getFn]();
						$.fn.zTree.init($("#fieldTree"), setting, data);
					} else {
						$.fn.zTree.init($("#fieldTree"), setting, fieldzNodes);
					}
					//流程变量tree赋值
					if (getVarFn) {
						data = parent[getVarFn]();
						$.fn.zTree.init($("#variableTree"), setting, data);
					} 
					if (initExpFn) {
						initData = parent[initExpFn]();
						if (initData) {
						    if(initData.expVal)
							$("#expressionEditor").val(initData.expVal);
							if(initData.expActVal)
							$("#initActExpression").val(initData.expActVal);
							if(initData.varVal)
							map.elements = initData.varVal;
						}
					}
				}
				//非模态窗口
				else if (window.opener && window.opener.location != null
						&& window.opener.location != undefined) {
					if (getFn) {
						data = opener[getFn]();
						$.fn.zTree.init($("#fieldTree"), setting, data);
					} else {
						$.fn.zTree.init($("#fieldTree"), setting, fieldzNodes);
					}
					//流程变量tree赋值
					if (getVarFn) {
						data = opener[getVarFn]();
						$.fn.zTree.init($("#variableTree"), setting, data);
					} 
					if (initExpFn) {
						initData = opener[initExpFn]();
						if (initData) {
						    if(initData.expVal!=null)
							//初始化显示表达式值
							$("#expressionEditor").val(initData.expVal);
							if(initData.expActVa!=null)
							//初始化实际表达式值
							$("#initActExpression").val(initData.expActVal);
							if(initData.varVal!=null)
							//初始化引入参数、字段map
							map.elements = initData.varVal;
						}
					}
				}

			});

</SCRIPT>
</head>
<body>
	<div id="vertical" style="width:800px;height:600px; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width:180px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/images/calculator_edit.png"
						style="vertical-align: middle;" /> <span
						style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">fx=</span>
						<span style="font-size: 16px;font-weight: bolder;">表达式编辑器</span></td>
					<td style="text-align:right;">
						<button id="button3" type="button">清空</button>
						<button id="button" type="button">验证</button>
						<button id="button2" type="button">确定</button>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="horizontal" style="height:100%; width: 100%;">
				<div id="tabstrip" style="border: 0px;">
					<ul>
						<li id="Tab1">表达式</li>
						<c:if test="${param.category=='db' || param.category=='back' || param.category=='front'}"><li>数据字段</li></c:if>
						<c:if test="${param.category=='front' || param.category=='form'}"><li>页面元素</li></c:if>
						<c:if test="${param.category=='workflow'}"><li>流程变量</li></c:if>
					</ul>
					<div
						style="height:451px; width: 86%;overflow-y:auto;overflow-x: auto;">
						<ul id="expTree" class="ztree"></ul>
					</div>
					<c:if test="${param.category=='db' || param.category=='back' || param.category=='front'}">
					<div
						style="height:451px; width: 86%;overflow-y:auto;overflow-x: auto;">
						<ul id="fieldTree" class="ztree"></ul>
					</div>
					</c:if>
					<c:if test="${param.category=='front'  || param.category=='form'}">
					<div
						style="height:451px; width: 86%;overflow-y:auto;overflow-x: auto;">
						<ul id="pageAttrTree" class="ztree"></ul>
					</div>
					</c:if>
					<c:if test="${param.category=='workflow'}">
					<div
						style="height:451px; width: 86%;overflow-y:auto;overflow-x: auto;">
						<ul id="variableTree" class="ztree"></ul>
					</div>
					</c:if>
				</div>
				<div id="content" style="margin: 1px;">
					<textarea id="expressionEditor" onKeydown="savePos(this)"
						class="k-widget" onKeyup="savePos(this)"
						onmousedown="savePos(this)" onmouseup="savePos(this)"
						onfocus="savePos(this)"
						style="height:60%; width: 99%;font-size:20px;font-weight: bolder;border-left: 0px;border-right: 0px;border-top: 0px;"></textarea>
					<textarea id="expressionActEditor" style="display: none;"></textarea>
					<div id="desDiv" class="k-header k-footer footerCss"
						style="height:39%; width: 100%;font-size:15px;border:0px;">
						<span name="desSpan"></span>
					</div>
				</div>
			</div>
		</div>
		<div id="footer" class="k-header k-footer footerCss">
			<table style="width:100%;height:100%;vertical-align:middle;">
				<tr>
					<td style="text-align: left;" class="expspan"><font
						style="font-size: 16px;font-weight: bolder;margin-left: 5px;">常用运算符：</font>
						<c:forEach items="${expre_operator}" var="operator">
							<span name="span_${operator.id}"
								style="font-family:Lucida Calligraphy;font-size: 20px;"></span>&nbsp;
									<script type="text/javascript">
										$(document)
												.ready(
														function() {
															$(
																	"span[name='span_${operator.id}']")
																	.attr(
																			"title",
																			"${operator.desc}");
															$(
																	"span[name='span_${operator.id}']")
																	.css(
																			"cursor",
																			"pointer");
															$(
																	"span[name='span_${operator.id}']")
																	.html(
																			"${operator.code}");
															$(
																	"span[name='span_${operator.id}']")
																	.click(
																			function() {
																				insertAtCursor(
																						$("#expressionEditor")[0],
																						"${operator.value}");
																				return false;
																			})
														});
									</script>
						</c:forEach> <c:forEach items="${expre_split}" var="split">
							<span name="span_${split.id}"
								style="font-family:Lucida Calligraphy;font-size: 20px;"></span>&nbsp;
									<script type="text/javascript">
										$(document)
												.ready(
														function() {
															$(
																	"span[name='span_${split.id}']")
																	.attr(
																			"title",
																			"${split.desc}");
															$(
																	"span[name='span_${split.id}']")
																	.css(
																			"cursor",
																			"pointer");
															$(
																	"span[name='span_${split.id}']")
																	.html(
																			"${split.code}");
															$(
																	"span[name='span_${split.id}']")
																	.click(
																			function() {
																				insertAtCursor(
																						$("#expressionEditor")[0],
																						"${split.value}");
																				return false;
																			})
														});
									</script>
						</c:forEach></td>
					<td></td>
				</tr>
			</table>
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
			}, {
				collapsed : false,
				resizable : false,
				scrollable : false,
				size : "50px"
			} ]
		});
		var splitter = $("#horizontal").kendoSplitter({
			panes : [ {
				collapsed : false,
				collapsible : false,
				collapsedSize : "0px",
				max : "300px",
				resizable : false,
				size : "260px",
				scrollable : true
			}, {
				size : "500px",
				scrollable : true
			} ]
		});
		splitter = splitter.data("kendoSplitter");
		//Tab初始化
		$("#tabstrip").kendoTabStrip({
			tabPosition : "bottom",
			scrollable : false,
			animation : {
				open : {
					effects : "fadeIn"
				}
			}
		});
		var tabToActivate = $("#Tab1");
		$("#tabstrip").kendoTabStrip().data("kendoTabStrip").activateTab(
				tabToActivate);
		//初始化验证按钮
		$("#button").kendoButton({
			imageUrl : "${contextPath}/images/spell-check.png"
		});
		var button3 = $("#button3").kendoButton({}).data("kendoButton").bind("click",function(e){
			map.clear();
			$("#expressionEditor").val("");
		});
		var button = $("#button").data("kendoButton");
		var category='${param.category}';
		button
				.bind(
						"click",
						function(e) {
							//检查是否存在值
							if ($("#expressionEditor").val() == "" && !notEempty) {
								outMessage(contextPath, 2, "未录入表达式", "", -1,
										$("#expressionEditor"),
										$("span[name='desSpan']"));
								return false;
							} else if(category!='db'){
								//发送到后端服务器验证
								changeExpre($("#expressionEditor"),
										$("#expressionActEditor"));
								var paramVal = $("#expressionActEditor").val();
								var variableJSONString = structVariableJSONString();
								$
										.ajax({
											url : "${contextPath}/mx/expression/defined/validataExpression",
											type : "post",
											async : false,
											dataType : "json",
											data : {
												expression : paramVal,
												variableJSONString : variableJSONString,
												notValidate : notValidate
											},
											success : function(data) {
												if (data) {
													if (data.execFlag == 1) {
														//alert("表达式验证成功  执行结果："+data.message);
														var msg = "表达式验证成功，执行结果："
																+ data.message;
														//显示表达式转化为实际表达式
													} else {
														//alert("表达式验证失败  执行结果："+data.message);
														var msg = "表达式验证失败，"
																+ data.message;
													}
													outMessage(
															contextPath,
															data.execFlag,
															msg,
															data.errorToken,
															data.errorPosition,
															$("#expressionEditor"),
															$("span[name='desSpan']"));
												}
											},
											error : function() {
												outMessage(
														contextPath,
														0,
														"表达式验证异常",
														data.errorToken,
														data.errorPosition,
														$("#expressionEditor"),
														$("span[name='desSpan']"));
											}

										});
							}
							else if(category=='db'){
								var msg = "表达式验证成功，执行结果："
									+ $("#expressionEditor").val();
								outMessage(
										contextPath,
										1,
										msg,
										"",
										"",
										$("#expressionEditor"),
										$("span[name='desSpan']"));
							}
						});

		//初始化确认按钮
		$("#button2").kendoButton({
			imageUrl : "${contextPath}/images/okay.png"
		});
		var button2 = $("#button2").data("kendoButton");
		button2
				.bind(
						"click",
						function(e) {
							//检查是否存在值
							if ($("#expressionEditor").val() == "" && !notEempty) {
								outMessage(contextPath, 2, "未录入表达式", "", -1,
										$("#expressionEditor"),
										$("span[name='desSpan']"));
								return false;
							} else if(category!='db'){
								//发送到后端服务器验证
								changeExpre($("#expressionEditor"),
										$("#expressionActEditor"));
								var paramVal = $("#expressionActEditor").val();
								var variableJSONString = structVariableJSONString();
								$
										.ajax({
											url : "${contextPath}/mx/expression/defined/validataExpression",
											type : "post",
											async : false,
											dataType : "json",
											data : {
												expression : paramVal,
												variableJSONString : variableJSONString,
												notValidate : notValidate
											},
											success : function(data) {
												if (data) {
													if (data.execFlag == 1) {
													    var returnVal=createReturnVal($("#expressionEditor").val(),paramVal,map.elements);
														if (window.parent.location != undefined
																&& window.parent.location != window.location) {
															var fieldExpDatafunc = "${param.retFn}";
															//kendo弹出页面
															if (fieldExpDatafunc) {
																window.parent[fieldExpDatafunc]
																		(returnVal);
															}
															else{
																window.returnValue = returnVal;
																window.close();
															}
														} else if (window.opener
																&& window.opener.location != null
																&& window.opener.location != undefined) {
															var fieldExpDatafunc = "${param.retFn}";
															if (fieldExpDatafunc) {
															  
																window.opener[fieldExpDatafunc]
																		(returnVal);
																window.close();
															}
														}
													} else {
														//alert("表达式验证失败  执行结果："+data.message);
														var msg = "表达式验证失败，"
																+ data.message;
														outMessage(
																contextPath,
																data.execFlag,
																msg,
																data.errorToken,
																data.errorPosition,
																$("#expressionEditor"),
																$("span[name='desSpan']"));
													}

												}
											},
											error : function() {
												outMessage(
														contextPath,
														0,
														"表达式验证异常",
														data.errorToken,
														data.errorPosition,
														$("#expressionEditor"),
														$("span[name='desSpan']"));
											}
										});
							}else if(category=='db'){
								//发送到后端服务器验证
								changeExpre($("#expressionEditor"),
										$("#expressionActEditor"));
								var paramVal = $("#expressionActEditor").val();
								var variableJSONString = structVariableJSONString();
								var returnVal=createReturnVal($("#expressionEditor").val(),paramVal,map.elements);
								if (window.parent.location != undefined
										&& window.parent.location != window.location) {
									var fieldExpDatafunc = "${param.retFn}";
									//kendo弹出页面
									if (fieldExpDatafunc) {
										window.parent[fieldExpDatafunc]
												(returnVal);
									}
									else{
										window.returnValue = returnVal;
										window.close();
									}
								} else if (window.opener
										&& window.opener.location != null
										&& window.opener.location != undefined) {
									var fieldExpDatafunc = "${param.retFn}";
									if (fieldExpDatafunc) {
									  
										window.opener[fieldExpDatafunc]
												(returnVal);
										window.close();
									}
								}
							}
						});
						createReturnVal=function (expVal,expActVal,varVal){
							var obj = new Object();
							obj.expVal = expVal;
							obj.expActVal = expActVal;
							obj.varVal = varVal;
							return obj;
						};			
	</script>
</body>
</html>