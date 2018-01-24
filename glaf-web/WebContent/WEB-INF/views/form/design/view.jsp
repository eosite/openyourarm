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
<title>查看表单设计元素实体信息</title>
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
        "deploymentId": "${formDesignEntity.deploymentId}",
        "name": "${formDesignEntity.name}",
        "title": "${formDesignEntity.title}",
        "type": "${formDesignEntity.type}",
        "layoutHtml": "${formDesignEntity.layoutHtml}",
        "layoutJS": "${formDesignEntity.layoutJS}",
        "addElementHtml": "${formDesignEntity.addElementHtml}",
        "addElementJS": "${formDesignEntity.addElementJS}",
        "addElementDS": "${formDesignEntity.addElementDS}",
        "id": "${formDesignEntity.id}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });           
    });

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="查看表单设计元素实体信息">&nbsp;
查看表单设计元素实体信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${formDesignEntity.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="deploymentId" class="required">部署编号&nbsp;</label>
        <input id="deploymentId" name="deploymentId" type="text" class="k-textbox"  
	   data-bind="value: deploymentId"
	   value="${formDesignEntity.deploymentId}" validationMessage="请输入部署编号"/>
	<span class="k-invalid-msg" data-for="deploymentId"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${formDesignEntity.name}" validationMessage="请输入名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	   data-bind="value: title"
	   value="${formDesignEntity.title}" validationMessage="请输入标题"/>
	<span class="k-invalid-msg" data-for="title"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="type" class="required">类型&nbsp;</label>
        <input id="type" name="type" type="text" class="k-textbox"  
	   data-bind="value: type"
	   value="${formDesignEntity.type}" validationMessage="请输入类型"/>
	<span class="k-invalid-msg" data-for="type"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <textarea id="layoutHtml" name="layoutHtml" data-bind="value: layoutHtml" rows="6" cols="46" class="k-textbox" style="height:60px; width:240px;">${formDesignEntity.layoutHtml}</textarea> 
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="layoutJS" class="required">布局的JS&nbsp;</label>
        <textarea id="layoutJS" name="layoutJS" data-bind="value: layoutJS" rows="6" cols="46" class="k-textbox" style="height:60px; width:240px;">${formDesignEntity.layoutJS}</textarea> 
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<textarea id="addElementHtml" name="addElementHtml" data-bind="value: addElementHtml" rows="6" cols="46" class="k-textbox" style="height:60px; width:240px;">${formDesignEntity.layoutHtml}</textarea>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<textarea id="addElementJS" name="addElementJS" data-bind="value: addElementJS" rows="6" cols="46" class="k-textbox" style="height:60px; width:240px;">${formDesignEntity.addElementJS}</textarea>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<textarea id="addElementDS" name="addElementDS" data-bind="value: addElementDS" rows="6" cols="46" class="k-textbox" style="height:60px; width:240px;">${formDesignEntity.addElementDS}</textarea>
	</td>
    </tr>
    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;</td>
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