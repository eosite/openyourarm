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
<title>编辑开发者信息</title>
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
        "userId": "${videoDeveloper.userId}",
        "name": "${videoDeveloper.name}",
        "desc": "${videoDeveloper.desc}",
        "phoneNumber": "${videoDeveloper.phoneNumber}",
        "appKey": "${videoDeveloper.appKey}",
        "secretKey": "${videoDeveloper.secretKey}",
        "apiUrl": "${videoDeveloper.apiUrl}",
        "isEncrypt": "${videoDeveloper.isEncrypt}",
        "locked": "${videoDeveloper.locked}",
        "developerId": "${videoDeveloper.developerId}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/video/developer/saveVideoDeveloper";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/video/developer/saveVideoDeveloper";
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
					   if(data.statusCode == 200){
					       window.parent.location.reload();
					   }
				   }
			 });
       }
   }
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑开发者信息">&nbsp;
编辑开发者信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="developerId" name="developerId" value="${videoDeveloper.developerId}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="userId" class="required">开发者用户名&nbsp;</label>
        <input id="userId" name="userId" type="text" class="k-textbox"  
	   required="true"   data-bind="value: userId"
	   value="${videoDeveloper.userId}" validationMessage="请输入开发者用户名"/>
	<span class="k-invalid-msg" data-for="userId"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${videoDeveloper.name}" validationMessage="请输入名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="desc" class="required">描述&nbsp;</label>
        <input id="desc" name="desc" type="text" class="k-textbox"  
	   data-bind="value: desc"
	   value="${videoDeveloper.desc}" validationMessage="请输入描述"/>
	<span class="k-invalid-msg" data-for="desc"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="phoneNumber" class="required">手机号码.&nbsp;</label>
        <input id="phoneNumber" name="phoneNumber" type="text" class="k-textbox"  
	   required="true"   data-bind="value: phoneNumber"
	   value="${videoDeveloper.phoneNumber}" validationMessage="请输入Tel No."/>
	<span class="k-invalid-msg" data-for="phoneNumber"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="appKey" class="required">应用密锁&nbsp;</label>
        <input id="appKey" name="appKey" type="text" class="k-textbox"  
	   required="true"   data-bind="value: appKey"
	   value="${videoDeveloper.appKey}" validationMessage="请输入appKey"/>
	<span class="k-invalid-msg" data-for="appKey"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="secretKey" class="required">安全密锁&nbsp;</label>
        <input id="secretKey" name="secretKey" type="text" class="k-textbox"  
	   required="true"   data-bind="value: secretKey"
	   value="${videoDeveloper.secretKey}" validationMessage="请输入secretKey"/>
	<span class="k-invalid-msg" data-for="secretKey"></span>
    </td>
  </tr>
  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="authAddr" class="required">认证地址&nbsp;</label>
        <input id="authAddr" name="authAddr" type="text" class="k-textbox"  
	   data-bind="value: authAddr"
	   value="${videoDeveloper.authAddr}" validationMessage="请输入authAddr"/>
	<span class="k-invalid-msg" data-for="authAddr"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="platformAddr" class="required">平台地址&nbsp;</label>
        <input id="platformAddr" name="platformAddr" type="text" class="k-textbox"  
	   data-bind="value: platformAddr"
	   value="${videoDeveloper.platformAddr}" validationMessage="请输入platformAddr"/>
	<span class="k-invalid-msg" data-for="platformAddr"></span>
    </td>
  </tr> -->
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="apiUrl" class="required">api地址&nbsp;</label>
        <input id="apiUrl" name="apiUrl" type="text" class="k-textbox"  
	           required="true"   data-bind="value: apiUrl" 
	           value="https://open.ys7.com/api/" validationMessage="请输入apiUrl"/>
	    <span class="k-invalid-msg" data-for="apiUrl"></span>
    </td>
  </tr>
 
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="locked" class="required">是否有效&nbsp;</label>
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${videoDeveloper.locked}";
	  </script>
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

</body>
</html>