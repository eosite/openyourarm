<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑模板数据映射信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style scoped>
.k-textbox {
	width: 18.8em;
}

.main-section {
	width: 800px;
	padding: 0;
}

label {
	display: inline-block;
	width: 100px;
	text-align: right;
	padding-right: 10px;
}

.required {
	font-weight: bold;
}

.accept, .status {
	padding-left: 90px;
}

.confirm {
	text-align: right;
}

.valid {
	color: green;
}

.invalid {
	color: red;
}

span.k-tooltip {
	margin-left: 6px;
}
</style>
<script type="text/javascript">
	jQuery(function() {
		var viewModel = kendo.observable({
			"templateId" : "${reportTmpMapping.templateId}",
			"templateCode" : "${reportTmpMapping.templateCode}",
			"templateName" : "${reportTmpMapping.templateName}",
			"desc" : "${reportTmpMapping.desc}",
			"id" : "${reportTmpMapping.id}"
		});

		kendo.bind(jQuery("#iForm"), viewModel);

	});

	jQuery(document).ready(function() {
		jQuery("#iconButton").kendoButton({
			spriteCssClass : "k-icon"
		});
		///mx/reportTemplate/choose
		var url = "${contextPath}/mx/reportTemplate/choose?" + $.param({
			fn : 'retFunc'
		});

		$("#btn-0").on("click.btn-0", function(e) {
			window.$openWindow = window.open(url);
			return false;
		});
	});

	function retFunc(data) {
		if (data) {
			var mapping = {
				templateCode : 'code',
				templateName : 'name',
				templateId : 'id'
			};
			$.each(mapping, function(k, v){
				$("#" + k).val(data[v]);
			});
		}
		window.$openWindow && (window.$openWindow.close());
	}

	jQuery(function() {
		var container = jQuery("#iForm");
		kendo.init(container);
		container
				.kendoValidator({
					rules : {
						greaterdate : function(input) {
							if (input.is("[data-greaterdate-msg]")
									&& input.val() != "") {
								var date = kendo.parseDate(input.val()), otherDate = kendo
										.parseDate(jQuery(
												"[name='"
														+ input
																.data("greaterdateField")
														+ "']").val());
								return otherDate == null
										|| otherDate.getTime() < date.getTime();
							}

							return true;
						}
					}
				});
	});

	function save() {
		var form = document.getElementById("iForm");
		var validator = jQuery("#iForm").data("kendoValidator");
		if (validator.validate()) {
			var link = "${contextPath}/mx/report/reportTmpMapping/saveReportTmpMapping";
			var params = jQuery("#iForm").formSerialize();
			jQuery.ajax({
				type : "POST",
				url : link,
				dataType : 'json',
				data : params,
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null && data.message != null) {
						alert(data.message);
					} else {
						alert('操作成功完成！');
					}
					window.parent.location.reload();
				}
			});
		}
	}
</script>
</head>
<body style="margin-top: 0px;">
	<div id="main_content" class="k-content ">
		<br>
		<div class="x_content_title">
			<img src="${contextPath}/images/window.png" alt="编辑模板数据映射信息">&nbsp;
			编辑模板数据映射信息
		</div>
		<br>
		<form id="iForm" name="iForm" method="post" data-role="validator"
			novalidate="novalidate">
			<input type="hidden" id="id" name="id" value="${reportTmpMapping.id}" />
			<table width="95%" align="center" border="0" cellspacing="0"
				cellpadding="5">
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="desc" class="required">映射描述&nbsp;</label>
						<input id="desc" name="desc" type="text" class="k-textbox"
						data-bind="value: desc" value="${reportTmpMapping.desc}"
						required="required" validationMessage="请输入映射描述" /> <span
						class="k-invalid-msg" data-for="desc"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="templateName" class="required">模板名称&nbsp;</label>
						<input id="templateName" name="templateName" type="text"
						readonly="readonly" class="k-textbox"
						data-bind="value: templateName"
						value="${reportTmpMapping.templateName}"
						validationMessage="请输入模板名称" /> <span class="k-invalid-msg"
						data-for="templateName"></span>
						<button class='k-button' id="btn-0" title="选择报表模版">.</button></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="templateId" class="required">模板唯一标识&nbsp;</label>
						<input id="templateId" name="templateId" type="text"
						readonly="readonly" class="k-textbox"
						data-bind="value: templateId"
						value="${reportTmpMapping.templateId}"
						validationMessage="请输入模板唯一标识" /> <span class="k-invalid-msg"
						data-for="templateId"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="templateCode" class="required">模板代码&nbsp;</label>
						<input id="templateCode" name="templateCode" type="text"
						readonly="readonly" class="k-textbox"
						data-bind="value: templateCode"
						value="${reportTmpMapping.templateCode}"
						validationMessage="请输入模板代码" /> <span class="k-invalid-msg"
						data-for="templateCode"></span></td>
				</tr>

				<tr>
					<td colspan="2" align="center" valign="bottom" height="30">&nbsp;
						<div>
							<button type="button" id="iconButton" class="k-button k-primary"
								style="width: 90px" onclick="javascript:save();">保存</button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>