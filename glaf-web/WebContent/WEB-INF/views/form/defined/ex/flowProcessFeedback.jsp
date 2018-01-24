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

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>审核意见</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
</head>
<%
	pageContext.setAttribute("contextPath", request.getContextPath());
	request.setAttribute("uuid", UUID.randomUUID().toString().replace("-", ""));
%>
<script type="text/javascript">
	window["$cb${uuid}"] = $.Callbacks("com.glaf");
</script>
<body>
	<div style="width: 100%; height: 100%;">
		<table border="1"
			style="border-color: rgb(163, 208, 228); font-family: 华文细黑; font-size: 11pt; width: 100%; height: 100%;">
			<tbody>
				<c:if test="${type eq 4}">
					<tr>
						<td
							style="width: 80px; border-width: 0px 1px 1px 0px; border-color: rgb(163, 208, 228); text-align: center; font-family: 华文细黑; font-size: 11pt;"
							bgcolor="#fafafa">撤回节点：</td>
						<td style="border: 0px rgb(163, 208, 228); border-image: none;"
							colspan="3"><input id="input-01-${uuid }"
							style="width: 300px;"></input></td>
						<script type="text/javascript">
							
						</script>
					</tr>
				</c:if>
				
				<tr>
					<td
						style="width: 80px; border-width: 0px 1px 1px 0px; border-color: rgb(163, 208, 228); text-align: center; font-family: 华文细黑; font-size: 11pt;"
						bgcolor="#fafafa">意见：</td>
					<td style="border: 0px rgb(163, 208, 228); border-image: none;"
						colspan="3"><textarea class="k-textbox"
							id="editor-0-${uuid }"
							style="font-family: 华文细黑; font-size: 11pt; width: 100%; height: 100%;"
							title="审核意见" data-role="editor"></textarea></td>
				</tr>
				
				<tr>
					<td
						style="width: 80px; border-width: 0px 1px 1px 0px; border-color: rgb(163, 208, 228); text-align: center; font-family: 华文细黑; font-size: 11pt;"
						bgcolor="#fafafa">附件上传：</td>
					<td style="border: 0px rgb(163, 208, 228); border-image: none;"
						colspan="3"></td>
				</tr>
				
				<tr>
					<td colspan=4
						style="text-align: center; border: 0px rgb(163, 208, 228); border-image: none; font-family: 华文细黑; font-size: 11pt;">
						<button class="k-button btn" id="btn-0-${uuid }">确定</button>
						<button class="k-button btn" id="btn-1-${uuid }">取消</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url = "${contextPath}/mx/form/workflow/defined/getPermissRejectTasks";
	var contextPath = "${contextPath}", fn = "${param.fn}";
	window.PROJECT_CONTEXT = "${contextPath}/mx/";
	$(function() {
		window["$cb${uuid}"].fire();
	});

	window["$cb${uuid}"].add(function() {

		function each(i, v) {
			!this.name && (this.name = this.id);
		}

		var dataSource = new kendo.data.DataSource({
			transport : {
				read : {
					url : url,
					dataType : "json",
					data : {
						taskId : '${taskId}',
						processId : '${processId}'
					}
				}
			},
			schema : {
				data : function(ret) {
					if (ret && ret.rows && ret.rows.length) {
						$.each(ret.rows, //
						each);
					}
					return ret.rows;
				}
			}
		});

		$("#input-01-${uuid }").kendoDropDownList({
			dataSource : dataSource,
			dataTextField : "name",
			dataValueField : "id"
		});
	});

	window["$cb${uuid}"].add(function() {
		var $editor = $('#editor-0-${uuid }').editor({
			initialFrameWidth : '100%',
			initialFrameHeight : '200'
		});
		var $parent = window.opener || window.parent;
		$("#btn-0-${uuid }").on(
				"click",
				function(e) {
					var content = $editor.editor("getContent");

					var destTaskKey = null;
					if ($("#input-01-${uuid }").get(0)) {
						destTaskKey = $("#input-01-${uuid }").data(
								"kendoDropDownList").value();
					}

					if (fn && (fn = $parent[fn])) {
						fn.call(this, {
							destTaskKey : destTaskKey,
							message : content
						});
					}
				});

		$("#btn-1-${uuid }").on("click", function(e) {
			if (fn && (fn = $parent[fn])) {
				fn && fn.close && (fn.close());
			}
		});
	});
</script>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.config.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/ueditor/ueditor.all.js"></script>
<script type="text/javascript"
	src="${contextPath }/webfile/js/jquery.editor.extends.js"></script>
</html>