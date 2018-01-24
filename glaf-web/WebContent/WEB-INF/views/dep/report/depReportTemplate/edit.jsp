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
<title>编辑报表模板信息</title>
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

.accept,.status {
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
			"code" : "${depReportTemplate.code}",
			"name" : "${depReportTemplate.name}",
			"id" : "${depReportTemplate.id}"
		});

		kendo.bind(jQuery("#iForm"), viewModel);

	});

	jQuery(document).ready(function() {
		jQuery("#iconButton").kendoButton({
			spriteCssClass : "k-icon"
		});
		!$("#code").val() && ($("#code").val("code-" + new Date().getTime()));
	});

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
		var validator = jQuery("#iForm").data("kendoValidator");
		if (validator.validate()) {
			var link = "${contextPath}/mx/dep/report/depReportTemplate/saveDepReportTemplate";
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
					window.parent.refresh();
				}
			});
		}
	}
</script>
</head>
<body style="margin-top:0px;">
	<div id="main_content" class="k-content ">
		<br>
		<div class="x_content_title">
			<img src="${contextPath}/images/window.png" alt="编辑报表模板信息">&nbsp;
			编辑报表模板信息
		</div>
		<br>
		<form id="iForm" name="iForm" method="post" data-role="validator"
			novalidate="novalidate">
			<input type="hidden" id="id" name="id"
				value="${depReportTemplate.id}" /> <input type="hidden" id="depId"
				name="depId" value="${param.depId}" />
			<table width="95%" align="center" border="0" cellspacing="0"
				cellpadding="5">
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="code" class="required">模板代码&nbsp;</label>
						<input id="code" name="code" type="text" class="k-textbox"
						data-bind="value: code" value="${depReportTemplate.code}"
						validationMessage="请输入模板代码" readonly="readonly" /> <span class="k-invalid-msg"
						data-for="code"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="name" class="required">模板名称&nbsp;</label>
						<input id="name" name="name" type="text" class="k-textbox"
						data-bind="value: name" value="${depReportTemplate.name}"
						validationMessage="请输入模板名称" /> <span class="k-invalid-msg"
						data-for="name"></span></td>
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
	<script>
		jQuery(document).ready(function() {
			jQuery("#ver").kendoNumericTextBox();
			//jQuery("#createDateTime").kendoDateTimePicker();
			//jQuery("#modifyDateTime").kendoDateTimePicker();
		});
	</script>
</body>
</html>