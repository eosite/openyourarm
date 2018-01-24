<%@ page contentType="text/html;charset=UTF-8" %>
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
<title>企业通应用注册</title>
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
        "ip": "${eimBaseInfo.ip}",
		"name": "${eimBaseInfo.name}",
        "host": "${eimBaseInfo.host}",
        "secret": "${eimBaseInfo.secret}",
        "paasId": "${eimBaseInfo.paasId}",
        "createBy": "${eimBaseInfo.createBy}",
        "createTime": "<fmt:formatDate value='${eimBaseInfo.createTime}' pattern='MM/dd/yyyy'/>",
        "updateBy": "${eimBaseInfo.updateBy}",
        "updateTime": "<fmt:formatDate value='${eimBaseInfo.updateTime}' pattern='MM/dd/yyyy'/>",
        "deleteFlag": "${eimBaseInfo.deleteFlag}",
        "id": "${eimBaseInfo.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/tencentEim/eimBaseInfo/saveEimBaseInfo";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/teim/base/saveEimBaseInfo";
	   var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
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
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${eimBaseInfo.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${eimBaseInfo.name}" validationMessage="请输入name"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="ip" class="required">IP地址&nbsp;</label>
        <input id="ip" name="ip" type="text" class="k-textbox"  
	   data-bind="value: ip"
	   value="${eimBaseInfo.ip}" validationMessage="请输入ip"/>
	<span class="k-invalid-msg" data-for="ip"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="host" class="required">域名&nbsp;</label>
        <input id="host" name="host" type="text" class="k-textbox"  
	   data-bind="value: host"
	   value="${eimBaseInfo.host}" validationMessage="请输入host"/>
	<span class="k-invalid-msg" data-for="host"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="secret" class="required">密钥&nbsp;</label>
        <input id="secret" name="secret" type="text" class="k-textbox"  
	   data-bind="value: secret"
	   value="${eimBaseInfo.secret}" validationMessage="请输入secret"/>
	<span class="k-invalid-msg" data-for="secret"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="paasId" class="required">应用ID&nbsp;</label>
        <input id="paasId" name="paasId" type="text" class="k-textbox"  
	   data-bind="value: paasId"
	   value="${eimBaseInfo.paasId}" validationMessage="请输入paasId"/>
	<span class="k-invalid-msg" data-for="paasId"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="deleteFlag" class="required">有效性&nbsp;</label>
			 <input type="radio"  name="deleteFlag" value="0" ${eimBaseInfo.deleteFlag!=1?"checked":""}/> 有效
			 <span></span>
			 <input type="radio"  name="deleteFlag"  value="1" ${eimBaseInfo.deleteFlag==1?"checked":""}/> 无效
			 <span></span>
    </td>
  </tr>
    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	</div>
	</td>
      </tr>
</table>   
</form>
</div>     
<script>
    jQuery(document).ready(function() {
	     //jQuery("#createTime").kendoDateTimePicker();
	     //jQuery("#updateTime").kendoDateTimePicker();
    });
</script>
</body>
</html>