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
	pageContext.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>审核意见</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
	var contextPath = "${contextPath}", fn = "${param.fn}";
	window.PROJECT_CONTEXT = "${contextPath}/mx/";
	$(function() {
		$('[data-role="editor"]').editor({
			initialFrameWidth : '100%',
			initialFrameHeight : '200'
		});

		var condition = {
			'true' : '同意',
			'false' : '退回'
		};
		$(".k-button").click(function() {
			var t = $(this).attr("t");
			if (t in condition) {
				if (confirm("你确定【" + condition[t] + "】吗?")) {
					var params = {
						approve : t,
						pageId : '${param.pageId}'
					};
					var $parent = window.parent
							|| window.opener;
					if (fn) {
						if ($parent[fn]) {
							$.extend(params, $parent[fn]());
						}
					}
					$.post(contextPath+ '/mx/form/workflow/defined/processSubmit',
					params, function(ret) {
						alert("操作成功!");
						if($parent.processCloseWin){
							$parent.processCloseWin(params.pageRuleId);
						}
					}, "json");
				}
			}
		});

	});
</script>
</head>
<body>
	<div style="width: 100%; height: 100%;">
		<table border="1"
			style="border-color: rgb(163, 208, 228); font-family: 华文细黑; font-size: 11pt; width: 100%; height: 100%;">
			<tbody>
				<tr>
					<td
						style="width: 80px; border-width: 0px 1px 1px 0px; border-color: rgb(163, 208, 228); text-align: center; font-family: 华文细黑; font-size: 11pt;"
						bgcolor="#fafafa">审核意见：</td>
					<td style="border: 0px rgb(163, 208, 228); border-image: none;"
						colspan="3"><textarea class="k-textbox" id="editor0"
							style="font-family: 华文细黑; font-size: 11pt; width: 100%; height: 100%;"
							title="审核意见" data-role="editor"></textarea></td>
				</tr>
				<tr>
					<td colspan=4
						style="text-align: center; border: 0px rgb(163, 208, 228); border-image: none; font-family: 华文细黑; font-size: 11pt;">
						<button class="k-button" id="button0" t='true'>同意</button>
						<button class="k-button" id="button1" t='false'>退回</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.all.js"></script>
<script type="text/javascript"
	src="${contextPath }/webfile/js/jquery.editor.extends.js"></script>
</html>