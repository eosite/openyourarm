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
<title>模型列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
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

.titleTd {
	text-align: left;
	width: 100px;
}
</style>
</head>
<body>
	<form id="addForm" style="text-align: center;margin: auto;width:100%;height:100%;vertical-align: middle;">
		<table style="width:500px;height:300px;text-align: left;margin: auto;vertical-align: middle;">
			<tr>
				<td style="width:130px;height:30px;">流程名称：</td>
				<td><input type="input" class='k-input k-textbox'
					name="modelName" id="modelName" style="width:200px" /></td>
			</tr>
			<tr>
				<td style="width:130px;">流程描述：</td>
				<td><textarea class="k-content k-raw-content" name="modelDesc"
						id="modelDesc" style="width:350px;height:200px;"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<button id="addBt" type="button">确定</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="cancelBt" type="button">取消</button>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	    var contextPath = '${contextPath}';
		var webPath = '${webPath}';
		var categoryId='${param.categoryId}';
		$("#addBt").kendoButton({
			icon : "oky"
		});
		$("#addForm").kendoValidator(
				{
					rules : {
						customRule1 : function(input) {
							if (input.is("[name=modelName]")) {
								if (input.val() == '' || input.val().length < 3
										|| input.val().length > 50) {
									return false;
								}
							}
							return true;
						},
						customRule3 : function(input) {
							if (input.is("[name=modelDesc]")) {
								if (input.val().length > 200) {
									return false;
								}
							}
							return true;
						}
					},
					messages : {
						customRule1 : "长度必须在3-50之间",
						customRule2 : "长度必须在3-50之间",
						customRule3 : "长度必须小于300"
					},
					validateOnBlur : true
				});
		//绑定方法
		$("#addBt").data("kendoButton").bind("click", add);
		//新增模型
		function add() {
			//启用验证
			var validatable = $("#addForm").data("kendoValidator");
			if (validatable.validate()) {
				$
						.ajax({
							url : "${contextPath}/rs/workflow/createWorkFlow",
							type : "post",
							async : false,
							dataType : "json",
							data : {
								pageId : '${param.pageId}',
								modelName : $("#modelName").val(),
								modelDesc : $("#modelDesc").val(),
								"categoryId":categoryId
							},
							success : function(data) {
								if (data) {
									if (data.result) {
										if (data.result == 1) {
											//跳转到编辑页面
											var url = "/mx/form/workflow/defined/view?modelId="
													+ data.modelId+"&modelName="+$("#modelName").val()+"&modelKey="+data.modelKey;
											$("#modelName").val("");
											$("#modelDesc").val("");
											//刷新grid
											parent.refreshGrid();
											//弹窗跳转到流程编辑页面
											if (parent.$("#addDialog").data(
													"kendoWindow")) {
												parent.$("#addDialog").data(
														"kendoWindow").title(
														"流程建模");
												parent.$("#addDialog").data(
														"kendoWindow").refresh(
														webPath + url);
											}
										} else {
											alert("新建流程失败！");
										}
									}
								}
							},
							error : function() {
								alert("新建流程失败！");
							}

						});
			}
		}
		//绑定方法
		$("#cancelBt").kendoButton({
			icon : "cancel"
		});
		//绑定取消方法
		$("#cancelBt").data("kendoButton").bind("click", cancelAdd);
		function cancelAdd() {
			if (confirm("确定取消吗？")) {
				$("#modelName").val("");
				$("#modelDesc").val("");
				$("#addDialog").data("kendoWindow").close();
			}
		}
	</script>
</body>
</html>