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
<title>编辑FORMVIDEO信息</title>
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
		var viewModel = kendo
				.observable({
					"name" : "${formVideo.name}",
					"ip" : "${formVideo.ip}",
					"port" : "${formVideo.port}",
					"status" : "${formVideo.status}",
					"valided" : "${formVideo.valided}",
					"userName" : "${formVideo.userName}",
					"pwd" : "${formVideo.pwd}",
					"updateBy" : "${formVideo.updateBy}",
					"createDate" : "<fmt:formatDate value='${formVideo.createDate}' pattern='MM/dd/yyyy'/>",
					"updateDate" : "<fmt:formatDate value='${formVideo.updateDate}' pattern='MM/dd/yyyy'/>",
					"createBy" : "${formVideo.createBy}",
					"id" : "${formVideo.id}"
				});
		var status = "${formVideo.status}" || 0;
		kendo.bind(jQuery("#iForm"), viewModel);
		$("input[name='status'][value='" + status + "']").attr('checked','true'); 
	});

	jQuery(document).ready(function() {
		jQuery("#iconButton").kendoButton({
			spriteCssClass : "k-icon"
		});
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
	
	$.fn.serializeObject = function(params){
		var o = params || {};
		$.each(this.serializeArray(),function(i,v){
			o[v.name] = v.value;
		});
		return o;
	};
	
	function save() {
		
		var validator = jQuery("#iForm").kendoValidator().data("kendoValidator");
		var params = jQuery("#iForm").serializeObject();
		if (validator.validate()) {
			var link = "${contextPath}/mx/form/video/saveFormVideo";
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
<body style="margin-top:0px;">
	<div id="main_content" class="k-content ">
		<br>
		<div class="x_content_title">
			<img src="${contextPath}/images/window.png" alt="编辑监控信息">&nbsp;
			编辑监控信息
		</div>
		<br>
		<form id="iForm" name="iForm" method="post" data-role="validator"
			novalidate="novalidate">
			<input type="hidden" id="id" name="id" value="${formVideo.id}" />
			<table width="95%" align="center" border="0" cellspacing="0"
				cellpadding="5">
				<tr>	
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="name" class="required">名称&nbsp;</label>
						<input id="name" name="name" type="text" class="k-textbox"
						data-bind="value: name" value="${formVideo.name}" required 
						validationMessage="请输入名称" /> <span class="k-invalid-msg"
						data-for="name"></span>
					</td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="ip" class="required">IP&nbsp;</label>
						<input id="ip" name="ip" type="text" class="k-textbox"
						data-bind="value: ip" value="${formVideo.ip}" required
						validationMessage="请输入IP" /> <span class="k-invalid-msg"
						data-for="ip"></span>
					</td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="port" class="required">端口&nbsp;</label>
						<input id="port" name="port" type="text" class="k-textbox"
						data-bind="value: port" value="${formVideo.port}" required
						validationMessage="请输入端口" /> <span class="k-invalid-msg"
						data-for="port"></span>
					</td>
				</tr>
				
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="userName" class="required">用户名&nbsp;</label>
						<input id="userName" name="userName" type="text" class="k-textbox"
						data-bind="value: userName" value="${formVideo.userName}" required
						validationMessage="请输入用户名" /> <span class="k-invalid-msg"
						data-for="userName"></span>
					</td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="pwd" class="required">密码&nbsp;</label>
						<input id="pwd" name="pwd" type="password" class="k-textbox"
						data-bind="value: pwd" value="${formVideo.pwd}" required
						validationMessage="请输入密码" /> <span class="k-invalid-msg"
						data-for="pwd"></span>
					</td>
				</tr>
				<%--<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="status" class="required">status&nbsp;</label>
						<input id="status" name="status" type="text" class="k-textbox"
						data-bind="value: status" value="${formVideo.status}"
						validationMessage="请输入status" /> <span class="k-invalid-msg"
						data-for="status"></span>
					</td>
				</tr>
				
				--%><tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="status" class="required">是否有效&nbsp;</label>
						<label>有效<input type="radio" name="status" value="0" ></label>&nbsp;&nbsp;
					  	<label>无效<input type="radio" name="status" value="1" ></label>
					</td>
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
			jQuery("#status").kendoNumericTextBox();
			jQuery("#valided").kendoNumericTextBox();
			//jQuery("#createDate").kendoDateTimePicker();
			//jQuery("#updateDate").kendoDateTimePicker();
		});
	</script>
</body>
</html>