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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>流程定义配置</title>
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
</style>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script>
	var fn = "${param.fn}", targetId = "${param.targetId}", treeGrid;

	$(function() {

		$.ajax({
			type : 'post',
			dataType : 'json',
			data : {
				processDefinitionId : "${param.processDefinitionId}"
			},
			url : "${contextPath}/mx/form/workflow/defined/getActivities",
			success : function(data) {
				if(data){
					console.log(data.rows);
					createZtree(data.rows);
				}
			}
		});

		//pageFunc();
	});

	/**
	 *	页面初始化以及页面信息反馈
	 */
	function pageFunc(data) {
		var $parent = window.opener || window.parent, $fn = $parent[fn], $target = $parent.document
				.getElementById(targetId);
		if (data) {
			if ($fn) {
				$fn.call($target, data);
			} else if ($target) {
				$target.value = JSON.stringify(data);
			}
		} else {
			//初始化页面信息
			var data = $target.value;
			if (data) {
				var datas = JSON.parse(data);
				if (datas) {
				}
			} else {
			}
		}
	}
	
	function createZtree(datas){
		$ztree = $("#ztree-1").ztree({
			callback : {
				beforeClick : function(treeId, treeNode, clickFlag){
					if(!treeNode.isParent){
						getVariableByParams(treeNode);
					}
				}
			}
		},datas);
	}
	
	function getVariableByParams(node){
		
		console.log(node);
		
		$.ajax({
			type : 'post',
			dataType : 'json',
			data : {
				taskKey : node.id,
				processDefinitionId : "${param.processDefinitionId}"
			},
			url : "${contextPath}/mx/form/workflow/defined/getVariableJSON",
			success : function(data) {
				if(data){
					console.log(data);
				}
			}
		});
	}

	$.fn.outter = function() {
		return this[0].outerHTML;
	};
</script>
</head>
<body>
	<div id="container" style="overflow: auto; height: 99%; width: 100%">

		<div id="header">
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td
							style="width: 500px; text-align: left; vertical-align: middle;"><img
							src="${contextPath}/images/setting_tools.png"
							style="vertical-align: middle;" /> <span
							style="font-family: Lucida Calligraphy; font-size: 20px; font-weight: bolder;">流程变量配置</span>
						</td>
						<td style="text-align: right;">
							<button class='k-button' type="button" t='saveSet'>保 存 配
								置</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div id="menu" style="width:15%;float:left" >
			<ul id='ztree-1' class='ztree' > </ul>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="${contextPath }/webfile/js/jquery.ztree.extends.js"></script>
</html>