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
<title>编辑应用信息</title>
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
        "nodeId": "${dockerApp.nodeId}",
        "deploymentId": "${dockerApp.deploymentId}",
        "databaseId": "${dockerApp.databaseId}",
        "serverId": "${dockerApp.serverId}",
        "code": "${dockerApp.code}",
        "title": "${dockerApp.title}",
        "type": "${dockerApp.type}",
        "domainName": "${dockerApp.domainName}",
		"contextPath": "${dockerApp.contextPath}",
        "port": "${dockerApp.port}",
        "configFile": "${dockerApp.configFile}",
        "active": "${dockerApp.active}",
        "id": "${dockerApp.id}"
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
	    //form.action = "<%=request.getContextPath()%>/mx/deployment/dockerApp/saveDockerApp";
	    //form.submit();
	    var link = "<%=request.getContextPath()%>/mx/deployment/dockerApp/saveDockerApp";
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
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑应用信息">&nbsp;
编辑应用信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${dockerApp.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">名称&nbsp;</label>
    <input id="name" name="name" type="text" class="k-textbox"  
	       data-bind="value: name"
	       value="${dockerApp.name}" validationMessage="请输入名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr> -->
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="code" class="required">项目代码&nbsp;</label>
    <input id="code" name="code" type="text" class="k-textbox"  
	       data-bind="value: code"
	       value="${dockerApp.code}" validationMessage="请输入代码"/>
	<span class="k-invalid-msg" data-for="code"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="title" class="required">标题&nbsp;</label>
    <input id="title" name="title" type="text" class="k-textbox"  
	       data-bind="value: title"
	       value="${dockerApp.title}" validationMessage="请输入标题"/>
	<span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="type" class="required">类型&nbsp;</label>
    <input id="type" name="type" type="text" class="k-textbox"  
	       data-bind="value: type"
	       value="${dockerApp.type}" validationMessage="请输入类型"/>
	<span class="k-invalid-msg" data-for="type"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="databaseId" class="required">应用主数据库&nbsp;</label>
	  <select id="databaseId" name="databaseId">
		<option value="">----请选择----</option>
		<c:forEach items="${databases}" var="item">
		<option value="${item.id}">${item.title}</option>     
		</c:forEach>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("databaseId").value="${dockerApp.databaseId}";
	  </script>
	  <span class="k-invalid-msg" data-for="databaseId"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="serverId" class="required">部署服务器&nbsp;</label>
	  <select id="serverId" name="serverId">
		<option value="">----请选择----</option>
		<c:forEach items="${servers}" var="item">
		<option value="${item.id}">${item.title}</option>     
		</c:forEach>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("serverId").value="${dockerApp.serverId}";
	  </script>    
	  <span class="k-invalid-msg" data-for="serverId"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="domainName" class="required">域名&nbsp;</label>
    <input id="domainName" name="domainName" type="text" class="k-textbox"  
	       data-bind="value: domainName"
	       value="${dockerApp.domainName}" validationMessage="请输入域名"/>
	<span class="k-invalid-msg" data-for="domainName"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="port" class="required">对外端口&nbsp;</label>
	<input id="port" name="port" type="text" class="k-textbox" 
	       data-bind="value: port" style="width:60px;" maxlength="5"
	       value="${dockerApp.port}" validationMessage="请输入对外端口"/>
	<span class="k-invalid-msg" data-for="port"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="contextPath" class="required">虚拟目录&nbsp;</label>
	<input id="contextPath" name="contextPath" type="text" class="k-textbox" 
	       data-bind="value: contextPath"
	       value="${dockerApp.contextPath}" validationMessage="请输入虚拟目录"/>
	 &nbsp;(例如：/glaf)
	<span class="k-invalid-msg" data-for="contextPath"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	 <label for="active" class="required">是否有效&nbsp;</label>
      <select id="active" name="active">
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("active").value="${dockerApp.active}";
	  </script>    
	 <span class="k-invalid-msg" data-for="active"></span>
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