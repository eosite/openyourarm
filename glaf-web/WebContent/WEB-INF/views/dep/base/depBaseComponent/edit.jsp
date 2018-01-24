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
<title>编辑组件信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style scoped>
.k-textbox {
	width: 300px;
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
        "code": "${depBaseComponent.code}",
        "name": "${depBaseComponent.name}",
        "type": "${depBaseComponent.type}",
        "creator": "${depBaseComponent.creator}",
        "createDateTime": "<fmt:formatDate value='${depBaseComponent.createDateTime}' pattern='MM/dd/yyyy'/>",
        "modifier": "${depBaseComponent.modifier}",
        "modifyDateTime": "<fmt:formatDate value='${depBaseComponent.modifyDateTime}' pattern='MM/dd/yyyy'/>",
        "delFlag": "${depBaseComponent.delFlag}",
        "id": "${depBaseComponent.id}",
        "htmlTemplate":"${depBaseComponent.htmlTemplate}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });

          jQuery("#compScopes").kendoMultiSelect({
          		dataTextField:"name",
          		dataValueField:"id",
          		value:[${uiid}],
          		dataSource:new kendo.data.DataSource({
          			transport:{
          				read:{
          					"contentType": "application/json",
          					"type": "POST",
                    		"url": "${pageContext.request.contextPath}/rs/dep/base/depBaseUI/json"
          				}
          			}
          		})
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
		    var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseComponent/saveDepBaseComponent";
	   		var compScopes = jQuery("#compScopes").data("kendoMultiSelect");
			$("#compScopes").val(compScopes.value());
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
			<img src="${pageContext.request.contextPath}/images/window.png"
				alt="编辑组件信息">&nbsp; 编辑组件信息
		</div>
		<br>
		<form id="iForm" name="iForm" method="post" data-role="validator"
			novalidate="novalidate">
			<input type="hidden" id="id" name="id" value="${depBaseComponent.id}" />
			<table width="98%" align="center" border="0" cellspacing="0"
				cellpadding="5">
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="name" class="required">组件名称&nbsp;</label>
						<input id="name" name="name" type="text" class="k-textbox" required
						data-bind="value: name" value="${depBaseComponent.name}"
						validationMessage="请输入组件名称" /> <span class="k-invalid-msg"
						data-for="name"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="code" class="required">组件代码&nbsp;</label>
						<input id="code" name="code" type="text" class="k-textbox"
						data-bind="value: code" value="${depBaseComponent.code}" required
						validationMessage="请输入组件代码" /> <span class="k-invalid-msg"
						data-for="code"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="type" class="required">组件类型&nbsp;</label>
						<input id="type" name="type" type="text" class="k-textbox"
						data-bind="value: type" value="${depBaseComponent.type}" required
						validationMessage="请输入组件类型" /> <span class="k-invalid-msg"
						data-for="type"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="compScopes" class="required">适用范围&nbsp;</label>
            			<input id="compScopes" name="compScopes"  class="k-textbox" />
						<span class="k-invalid-msg" data-for="compScopes"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="type" class="required">组件html模板&nbsp;</label>
						<textarea name="htmlTemplate" class="k-textbox" validationMessage="请输入html模板" style="height:100px" >${depBaseComponent.htmlTemplate}</textarea>
						<span class="k-invalid-msg" data-for="htmlTemplate"></span>
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
			
		});
	</script>
</body>
</html>