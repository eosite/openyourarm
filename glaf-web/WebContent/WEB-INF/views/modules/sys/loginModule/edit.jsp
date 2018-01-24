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
<title>编辑${typeName}登录模块信息</title>
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
        "title": "${loginModule.title}",
        "content": "${loginModule.content}",
        "publicKey": "${loginModule.publicKey}",
		"url": "${loginModule.url}",
		<c:if test="${type == 'client'}">
	    "appId": "${loginModule.appId}",
	    "appSecret": "${loginModule.appSecret}",
		"loginUserId": "${loginModule.loginUserId}",
		"loginUrl": "${loginModule.loginUrl}",
		</c:if>
		<c:if test="${type == 'client_rsa'}">
		"peerPublicKey": "${loginModule.peerPublicKey}",
		"loginUserId": "${loginModule.loginUserId}",
		</c:if>
		<c:if test="${type == 'server_rsa'}">
		"peerPublicKey": "${loginModule.peerPublicKey}",
		</c:if>
        "serverId": "${loginModule.serverId}",
		"systemCode": "${loginModule.systemCode}",
        "timeLive": "${loginModule.timeLive}",
        "locked": "${loginModule.locked}",
        "id": "${loginModule.id}"
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
	    var link = "<%=request.getContextPath()%>/mx/sys/loginModule/saveLoginModule";
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

   function resetLoginRSAKey(){
       if(confirm("RSA密锁更新后对方也需要同时更新，确定要更新RSA密锁吗？")){
	      var link = "<%=request.getContextPath()%>/rs/sys/loginModule/resetLoginRSAKey?id=${loginModule.id}";
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   location.href="<%=request.getContextPath()%>/mx/sys/loginModule/edit?id=${loginModule.id}&type=${type}";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑${typeName}登录模块信息">&nbsp;
编辑${typeName}登录模块信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${loginModule.id}"/>
<input type="hidden" id="type" name="type" value="${type}"/>
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox" style="width:585px;" 
	           data-bind="value: title"
	           value="${loginModule.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="systemCode" class="required">系统代码&nbsp;</label>
        <input id="systemCode" name="systemCode" type="text" class="k-textbox" style="width:585px;" 
	           data-bind="value: systemCode"
	           value="${loginModule.systemCode}" validationMessage="请输入系统代码"/>
	    <span class="k-invalid-msg" data-for="systemCode"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="content" class="required">描述&nbsp;</label>
        <input id="content" name="content" type="text" class="k-textbox" style="width:585px;" 
	           data-bind="value: content"
	           value="${loginModule.content}" validationMessage="请输入描述"/>
	    <span class="k-invalid-msg" data-for="content"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="appId" class="required">应用ID&nbsp;</label>
	    <input type="text" id="appId" name="appId" class="k-textbox" maxlength="200"
		       style="width:585px;" value="${loginModule.appId}" 
		       data-bind="value: appId" validationMessage="请输入应用ID"> 
	    <span class="k-invalid-msg" data-for="appId"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="appSecret" class="required">应用密锁&nbsp;</label>
	    <input type="text" id="appSecret" name="appSecret" class="k-textbox" maxlength="200" 
		       style="width:585px;" value="${loginModule.appSecret}"
		       data-bind="value: appSecret" validationMessage="请输入应用密锁"> 
	    <span class="k-invalid-msg" data-for="appSecret"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="url" class="required">链接地址&nbsp;</label>
	    <textarea  id="url" name="url" rows="6" cols="46" class="k-textbox" 
		           style="height:90px;width:585px;" 
		           data-bind="value: url" validationMessage="请输入链接地址">${loginModule.url}</textarea>
	    <span class="k-invalid-msg" data-for="url"></span>
		<div style="margin-top:5px;">
			<span style="color:red; margin-left:110px;">
			 （提示：填写需要链接网站地址的根路径即可，例如：<%=com.glaf.core.util.RequestUtils.getServiceUrl(request)%>）
			</span>
	   </div>
    </td>
  </tr>

  <c:if test="${type == 'client'}">

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="loginUrl" class="required">登录地址&nbsp;</label>
	    <textarea  id="loginUrl" name="loginUrl" rows="6" cols="46" class="k-textbox" 
		           style="height:90px;width:585px;" 
		           data-bind="value: loginUrl" validationMessage="请输入登录地址">${loginModule.loginUrl}</textarea>
	    <span class="k-invalid-msg" data-for="loginUrl"></span>
		<div style="margin-top:5px;">
			<span style="color:red; margin-left:110px;">
			 （提示：填写需要链接网站地址的登录地址，可以是第三方系统模拟登录地址）
			</span>
	   </div>
    </td>
  </tr>

  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="loginUrl" class="required">登录地址&nbsp;</label>
	    <textarea  id="loginUrl" name="loginUrl" rows="6" cols="46" class="k-textbox" 
		           style="height:90px;width:585px;" 
		           data-bind="value: loginUrl" validationMessage="请输入登录地址">${loginModule.loginUrl}</textarea>
	    <span class="k-invalid-msg" data-for="loginUrl"></span>
    </td>
  </tr> -->

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="appId" class="required">远程系统登录账户&nbsp;</label>
	    <input type="text" id="loginUserId" name="loginUserId" class="k-textbox" maxlength="50"
		       style="width:585px;" value="${loginModule.loginUserId}" 
		       data-bind="value: loginUserId" validationMessage="请输入远程系统登录账户"> 
	    <span class="k-invalid-msg" data-for="loginUserId"></span>
		<div style="margin-top:5px;">
			<span style="color:red; margin-left:110px;">
			 （提示：如果输入了远程系统的账户，就以该账户身份登录远程系统，否则以本系统的用户登录。）
			</span>
	   </div>
    </td>
  </tr>

</c:if>

<c:if test="${type == 'client_rsa'}">

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="appId" class="required">远程系统登录账户&nbsp;</label>
	    <input type="text" id="loginUserId" name="loginUserId" class="k-textbox" maxlength="50"
		       style="width:585px;" value="${loginModule.loginUserId}" 
		       data-bind="value: loginUserId" validationMessage="请输入远程系统登录账户"> 
	    <span class="k-invalid-msg" data-for="loginUserId"></span>
		<div style="margin-top:5px;">
			<span style="color:red; margin-left:110px;">
			 （提示：如果输入了远程系统的账户，就以该账户身份登录远程系统，否则以本系统的用户登录。）
			</span>
	   </div>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="peerPublicKey" class="required">对方公钥&nbsp;</label>
	    <textarea  id="peerPublicKey" name="peerPublicKey" rows="6" cols="46" class="k-textbox" 
		           style="height:90px;width:585px;" data-bind="value: peerPublicKey"
		            validationMessage="请输入服务器端的RSA公钥">${loginModule.peerPublicKey}</textarea>
	    <span class="k-invalid-msg" data-for="peerPublicKey"></span>
		<div style="margin-top:5px;">
			<span style="color:red; margin-left:110px;">
			 （提示：填写服务器端的RSA公钥）
			</span>
	   </div>
    </td>
  </tr>

</c:if>

<c:if test="${type == 'server_rsa'}">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="peerPublicKey" class="required">对方公钥&nbsp;</label>
	    <textarea  id="peerPublicKey" name="peerPublicKey" rows="6" cols="46" class="k-textbox" 
		           style="height:90px;width:585px;" data-bind="value: peerPublicKey"
		            validationMessage="请输入客户端的RSA公钥">${loginModule.peerPublicKey}</textarea>
	    <span class="k-invalid-msg" data-for="peerPublicKey"></span>
		<div style="margin-top:5px;">
			<span style="color:red; margin-left:110px;">
			 （提示：填写登录客户端的RSA公钥）
			</span>
	   </div>
    </td>
  </tr>
</c:if>

<c:if test="${type == 'client_rsa' || type == 'server_rsa'}">
 <c:if test="${!empty loginModule.publicKey}">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="publicKey" class="required">本机RSA公钥&nbsp;</label>
		<textarea  id="publicKey" name="publicKey" rows="6" cols="46" class="k-textbox" 
		           style="height:90px;width:585px;" data-bind="value: publicKey" readonly
		           >${loginModule.publicKey}</textarea>
	    <span class="k-invalid-msg" data-for="publicKey"></span>
    </td>
  </tr>
 </c:if>
</c:if>

<c:if test="${type == 'server'}">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="appId" class="required">登录编号&nbsp;</label>
	     ${loginModule.appId} 
	    <span class="k-invalid-msg" data-for="appId"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="appSecret" class="required">登录密锁&nbsp;</label>
	    ${loginModule.appSecret}
	    <span class="k-invalid-msg" data-for="appSecret"></span>
    </td>
  </tr>
</c:if>


<c:if test="${type == 'server' || type == 'client'}">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="serverId" class="required">登录服务器&nbsp;</label>
	    <select id="serverId" name="serverId">
			<option value="" selected>----请选择----</option>
			<c:forEach items="${servers}" var="server">
			<option value="${server.id}">${server.title}[${server.host}]</option>
			</c:forEach>
	    </select>
		<script type="text/javascript">
		    document.getElementById("serverId").value="${loginModule.serverId}";
		</script>
	    <span class="k-invalid-msg" data-for="serverId"></span>
    </td>
  </tr>
</c:if>


  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="timeLive" class="required">令牌有效时长&nbsp;</label>
	  <select id="timeLive" name="timeLive">
		<option value="0">----请选择----</option>
		<option value="1">1分钟</option>
		<option value="30">30分钟</option>
		<option value="60">60分钟</option>
		<option value="120">2小时</option>
		<option value="300">5小时</option>
		<option value="480">8小时</option>
		<option value="600">10小时</option>
		<option value="1440">24小时</option>
	   </select>
	   <script type="text/javascript">
            document.getElementById("timeLive").value="${loginModule.timeLive}";
       </script>
	  <span class="k-invalid-msg" data-for="timeLive"></span>
    </td>
   </tr>

    <tr>
	    <td width="2%" align="left">&nbsp;</td>
        <td align="left">
		  <label for="locked" class="required">是否有效&nbsp;</label>
		  <select id="locked" name="locked">
		      <option value="0">有效</option>
			  <option value="1">无效</option>
		  </select>
	      <script type="text/javascript">
            document.getElementById("locked").value="${loginModule.locked}";
          </script>
		</td>
      </tr>
 
    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:save();">保存</button>
          <c:if test="${type == 'client_rsa' || type == 'server_rsa'}">
           <c:if test="${!empty loginModule.publicKey}">
		    <button type="button" id="iconButton2"  class="k-button" style="width: 90px" 
		          onclick="javascript:resetLoginRSAKey();">重置RSA密锁</button>
		   </c:if>
	     </c:if>
	    </div>
	</td>
   </tr>
</table>   
</form>
</div>
<br>
<br>
</body>
</html>