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
<title>编辑组件规则信息</title>
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
        "ruleId": "${depBaseCompProp.ruleId}",
        "Id": "${depBaseCompProp.Id}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });           
    });

    jQuery(function () {
        var container = jQuery("#iForm");
        kendo.init(container);
        container.kendoValidator({
                rules: {
                      greaterdate: function (input) {
                            if (input.is("[data-greaterdate-msg]") && input.val() != "") {                                    
                               var date = kendo.parseDate(input.val()),
                               otherDate = kendo.parseDate(jQuery("[name='" + input.data("greaterdateField") + "']").val());
                               return otherDate == null || otherDate.getTime() < date.getTime();
                             }

                             return true;
                          }
                      }
        });
    });

   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	   //form.method="post";
	   //form.action = "<%=request.getContextPath()%>/mx/dep/depBaseCompProp/saveDepBaseCompProp";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/de
	p/depBaseCompProp/saveDepBaseCompProp";
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
pt>
</head>
<body style="margin-top: 0px;">
	<div id="main_content" class="k-content ">
		<br>
		<div class="x_content_title">
			<img src="<%=request.getContextPath()%>/images/window.png"
				alt="编辑组件规则信息">&nbsp; 编辑组件规则信息
		</div>
		<br>
		<form id="iForm" name="iForm" method="post" data-role="validator"
			novalidate="novalidate">
			<input type="hidden" id="Id" name="Id" value="${depBaseCompProp.Id}" />
			<table width="95%" align="center" border="0" cellspacing="0"
				cellpadding="5">
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="ruleId" class="required">规则标识&nbsp;</label>
						<input id="ruleId" name="ruleId" type="text" class="k-textbox"
						data-bind="value: ruleId" value="${depBaseCompProp.ruleId}"
						validationMessage="请输入规则标识" /> <span class="k-invalid-msg"
						data-for="ruleId"></span></td>
				</tr>

				<!-- <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
	  <input type="radio" name="status" id="engine1" class="k-radio" value="0" >
	  <label class="k-radio-label" for="engine1">有效</label>&nbsp;&nbsp;
	  <input type="radio" name="status" id="engine2" class="k-radio" value="1" >
	  <label class="k-radio-label" for="engine2">无效</label>
	</td>
    </tr> -->

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
		});
	</script>
</body>
</html>