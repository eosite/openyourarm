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
<title>HTML模板编辑器</title>
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
<script type="text/javascript" src="${contextPath}/scripts/glaf-core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/clipboardByExpress.js"></script>
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
	var contextPath = "${contextPath}";
	//表达式Json数据
	var expressions = '${expressions}';
	//Json字符串转Json对象
	var expJson = eval(expressions);
	<!--
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
			//insertAtCursor($("#expressionEditor")[0],
			//	treeNode.value);
			ue.execCommand('insertHtml', treeNode.value);
			map.put(treeNode.value, filterNode(treeNode));
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
	var varzNodes = [ {
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
	//参数树
	var paramzNodes = [ {
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
	$(document).ready(
			function() {
				var fieldExpDatafunc = "${param.getFieldFn}";
				var varExpDatafunc = "${param.getVarFn}";
				var paramDatafunc = "${param.getParamFn}";
				if (window.parent.location != undefined
						&& window.parent.location != window.location) {
					fieldzNodes = window.parent[fieldExpDatafunc]();
					varzNodes = window.parent[varExpDatafunc]();
					paramzNodes = window.parent[paramDatafunc]();
				} else if (window.opener && window.opener.location != null
						&& window.opener.location != undefined) {
					fieldzNodes = window.opener[fieldExpDatafunc]();
					varzNodes = window.opener[varExpDatafunc]();
					paramzNodes = window.opener[paramDatafunc]();
				}
				$.fn.zTree.init($("#varTree"), setting, varzNodes);
				$.fn.zTree.init($("#fieldTree"), setting, fieldzNodes);
				$.fn.zTree.init($("#paramTree"), setting, paramzNodes);
			});
//-->
</SCRIPT>
</head>
<body>
	<!-- 配置文件 -->
	<script>
		window.PROJECT_CONTEXT = "${contextPath}/mx/";
	</script>
	<script type="text/javascript"
		src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript"
		src="${contextPath}/scripts/ueditor/ueditor.all.js"></script>
	<!-- 进度条插件 -->
	<script type="text/javascript" charset="utf-8" src="${contextPath}/scripts/ueditor/exp/progressbarPlugs.js"></script>
	<!-- 查看附件插件 -->
	<script type="text/javascript" charset="utf-8" src="${contextPath}/scripts/ueditor/exp/fileViewPlugs.js"></script>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width:250px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/images/calculator_edit.png"
						style="vertical-align: middle;" /> <span
						style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">HTML</span>
						<span style="font-size: 16px;font-weight: bolder;">模板编辑器</span></td>
					<td style="text-align:right;">
						<button id="button3" type="button">清空</button>
						<button id="button" type="button">预览</button>
						<button id="button2" type="button">确定</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="horizontal" style="width: 100%;">
			<div id="tabstrip" style="border: 0px;height:100%;">
				<ul>
					<li id="Tab1">变量</li>
					<li id="Tab2">参数</li>
					<li id="Tab3">数据字段</li>
				</ul>
				<div
					style="height:94%; width: 86%;overflow-y:auto;overflow-x: hidden;">
					<ul id="varTree" class="ztree"></ul>
				</div>
				<div
					style="height:94%; width: 86%;overflow-y:auto;overflow-x: hidden;">
					<ul id="paramTree" class="ztree"></ul>
				</div>
				<div
					style="height:94%; width: 86%;overflow-y:auto;overflow-x: hidden;">
					<ul id="fieldTree" class="ztree"></ul>
				</div>
			</div>
			<div id="content" style="margin: 2px;border: 0px;">
				<!-- 加载编辑器的容器 -->
				<script id="container" name="content" type="text/plain">
                      
                    </script>
				<!-- 实例化编辑器 -->
				<script type="text/javascript">
					var ue = UE.getEditor('container', {
						initialFrameWidth : '99%',
						initialFrameHeight : 500
					});
					//获取初始化的HTMl
					var initHTMLFn = '${param.initHTMLFn}';
					//对编辑器的操作最好在编辑器ready之后再做
					ue.ready(function() {
						if (window.parent.location != undefined
								&& window.parent.location != window.location) {
							if (initHTMLFn) {
								var initData = parent[initHTMLFn]();
								if (initData) {
									//设置引入的变量、参数、字段
									if(initData.varVal){
										map.elements = initData.varVal;
									}
									//设置编辑器的内容
									if(initData.htmlVal){
										ue.setContent(recoverHtml(initData.htmlVal));
									}
								}
							}
						} else if (window.opener
								&& window.opener.location != null
								&& window.opener.location != undefined) {

							if (initHTMLFn) {
								var initData = opener[initHTMLFn]();
								if (initData) {
									//设置引入的变量、参数、字段
									if(initData.varVal){
										map.elements = initData.varVal;
									}
									//设置编辑器的内容
									if(initData.htmlVal){
										ue.setContent(recoverHtml(initData.htmlVal));
									}
								}
							}
						}
						//设置编辑器的内容
						//ue.setContent(initHTML);
						//初始化参数、变量、字段map
						//获取html内容，返回: <p>hello</p>
						//var html = ue.getContent();
						//获取纯文本内容，返回: hello
						//var txt = ue.getContentTxt();
					});
				</script>
				<textarea id="htmlActEditor" style="display: none;"></textarea>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		window.MSPointerEvent = null;
		window.PointerEvent = null;
		var mainHeight = $(window).height();
		$('#vertical').height(mainHeight);
		$(window).resize(function() {
			mainHeight = $(window).height();
			$("#vertical").height(mainHeight);
		});
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
				scrollable : false
			} ]
		});
		splitter = splitter.data("kendoSplitter");

		$(document).ready(function() {
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
			//var tabToActivate = $("#Tab1");
			//$("#tabstrip").data("kendoTabStrip").activateTab(
			//	tabToActivate);
			$("#tabstrip").data("kendoTabStrip").select(0);
		});

		//清空按钮
		$("#button3").kendoButton({
			imageUrl : "${contextPath}/images/delete.png"
		}).data("kendoButton").bind("click", function(e) {
			ue.setContent("");
			map.clear();
		});
		
		//初始化验证按钮
		$("#button").kendoButton({
			imageUrl : "${contextPath}/images/spell-check.png"
		});
		var html;
		var variableJSONString;
		var button = $("#button").data("kendoButton");
		button.bind("click", function(e) {
			html = ue.getAllHtml();
			html = changeHtml(html);
			//检查是否存在值
			if (html == "") {
				alert("模板为空");
				return false;
			} else {
				//预览
				//paramVal = html;
				variableJSONString = structVariableJSONString();
				openMaxWin('${contextPath}/mx/html/editor/preview');
			}
		});

		//初始化确认按钮
		$("#button2").kendoButton({
			imageUrl : "${contextPath}/images/okay.png"
		});
		var button2 = $("#button2").data("kendoButton");
		button2.bind("click", function(e) {
			html = ue.getContent();
			html = changeHtml(html);
			var returnVal=createReturnVal(html,map.elements);
			if (window.parent.location != undefined
					&& window.parent.location != window.location) {
				window.returnValue = returnVal;
				window.close();
			} else if (window.opener && window.opener.location != null
					&& window.opener.location != undefined) {
				var fieldExpDatafunc = "${param.retFn}";
				window.opener[fieldExpDatafunc](returnVal);
				window.close();
			}
		});
		//将显示文本框中的转换为隐藏域中的
		function changeHtml(html) {
			//变量map替换
			if(map.elements){
			$.each(map.elements, function(i, obj) {
				if (obj.value.value != obj.value.code) {
					//reg=new RegExp("\$F\{db1.cell_useradd12.column1\}","g"); 
					html = replaceAll(html, replaceAll(replaceAll(obj.value.value,"\"", "&quot;"),"\'", "&#39;"), obj.value.code);
				}
			});
			}
			return html;
		}
		//将隐藏域中的html转换成显示文本框中的
		function recoverHtml(html){
			//变量map替换
			if(map.elements){
			$.each(map.elements, function(i, obj) {
				if (obj.value.value != obj.value.code) {
					//reg=new RegExp("\$F\{db1.cell_useradd12.column1\}","g"); 
					html = replaceAll(html, obj.value.code , obj.value.value);
				}
			});
			}
			return html;
		}
		/**
		 *替换所有字符
		 *
		 */
		function replaceAll(srcStr, repStr, nStr) {
			while (srcStr.indexOf(repStr) > -1) {
				srcStr = srcStr.replace(repStr, nStr);
			}
			return srcStr;
		}
		createReturnVal=function (htmlVal,varVal){
							var obj = new Object();
							obj.htmlVal = htmlVal;
							obj.varVal = varVal;
							return obj;
						};		
	</script>
</body>
</html>