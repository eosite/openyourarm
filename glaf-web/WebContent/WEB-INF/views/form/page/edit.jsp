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
<title>编辑页面信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
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
        "name": "${formPage.name}",
        "title": "${formPage.title}",
        "accessType": "${formPage.accessType}",
        "perms": "${formPage.perms}",
        "addressPerms": "${formPage.addressPerms}",
        "type": "${formPage.type}",
        "locked": "${formPage.locked}",
        "id": "${formPage.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/form/page/saveFormPage";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/form/page/saveFormPage";
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
					   //window.parent.location.reload();
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑页面信息">&nbsp;
编辑页面信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${formPage.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="12%" align="left"><label for="name" class="required">名称</label></td>
    <td width="38%" align="left">
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name"
	           value="${formPage.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
	<td width="12%" align="left"><label for="accessType" class="required">访问类型</label></td>
    <td width="38%" align="left">
      <select id="accessType" name="accessType">
	    <option value="PRV">私有</option>
		<option value="PUB">公开</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("accessType").value="${formPage.accessType}";
	  </script>   
    </td>
  </tr>
  <tr>
    <td width="12%" align="left"><label for="title" class="required">标题</label></td>
    <td width="38%" align="left">
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title"
	           value="${formPage.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
	<td width="12%" align="left"><label for="locked" class="required">是否有效&nbsp;</label></td>
    <td width="38%" align="left">
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${formPage.locked}";
	  </script>
	</td> 
  </tr>

   <tr>
    <td width="12%" align="left"><label for="title" class="required">样式</label></td>
    <td width="38%" align="left">
        <select id="userStyleFlag" name="userStyleFlag">
		    <option value="">----请选择----</option>
			<option value="Y">用户自定义</option>
			<option value="N">系统内置</option>
		</select>&nbsp;(是否使用用户自定义样式)
		<script type="text/javascript">
			 document.getElementById("userStyleFlag").value="${formPage.userStyleFlag}";
		</script>
    </td>
	<td width="12%" align="left">&nbsp;</td>
    <td width="38%" align="left">&nbsp;</td> 
  </tr>

   <tr>
		<td width="12%" align="left"><label for="perms" class="required">访问角色</label></td>
		<td align="left" colspan="3"> 
			<input type="hidden" id="perms" name="perms" value="${formPage.perms}">
            <textarea  id="x_roles_name" name="x_roles_name" rows="6" cols="46" class="k-textbox" readonly style="width:485px;" 
		    >${x_role_names}</textarea>
			<input type="button" name="button" value="添加" class="k-button" 
			       onclick="javascript:selectRole('iForm', 'perms','x_roles_name');"> 
			&nbsp;
			<input type="button" name="button" value="清空" class="k-button" 
			       onclick="javascript:clearSelected('perms','x_roles_name');">
			<br><span style="color:red;">（需要独立提供对外数据服务时才设定。）</span>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left"><label for="addressPerms" class="required">允许访问IP地址</label></td>
		<td align="left" colspan="3"> 
		    <textarea id="addressPerms" name="addressPerms" rows="6" cols="46" class="k-textbox" style="width:485px;" >${formPage.addressPerms}</textarea>
			<br><span style="color:red;">（需要独立提供对外数据服务时才设定。）</span>
			<br>允许使用*为通配符，多个地址之间用半角的逗号“,”隔开。
			<br>例如：192.168.*.*，那么192.168.1.100及192.168.142.100都可访问该服务。
            <br>192.168.142.*，那么192.168.1.100不能访问但192.168.142.100可访问该服务。
			<br>如果配置成192.168.1.*,192.168.142.*，那么192.168.1.100及192.168.142.100均可访问该服务。
		</td>
	</tr>
 
   <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	</div>
	</td>
   </tr>
</table>   
</form>
<br>
<br>
</div>     
</body>
</html>