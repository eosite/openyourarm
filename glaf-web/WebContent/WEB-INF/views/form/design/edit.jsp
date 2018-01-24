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
<title>编辑表单设计元素实体信息</title>
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
        "createDate": "<fmt:formatDate value='${formDesignEntity.createDate}' pattern='MM/dd/yyyy'/>",
        "createBy": "${formDesignEntity.createBy}",
        "id": "${formDesignEntity.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/form/design/saveFormDesignEntity";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/form/design/saveFormDesignEntity";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑表单设计元素实体信息">&nbsp;
编辑表单设计元素实体信息</div>
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
	<label for="layoutHtml" class="required">布局的html&nbsp;</label>
        <input id="layoutHtml" name="layoutHtml" type="text" class="k-textbox"  
	   data-bind="value: layoutHtml"
	   value="${formDesignEntity.layoutHtml}" validationMessage="请输入布局的html"/>
	<span class="k-invalid-msg" data-for="layoutHtml"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="layoutJS" class="required">布局的JS&nbsp;</label>
        <input id="layoutJS" name="layoutJS" type="text" class="k-textbox"  
	   data-bind="value: layoutJS"
	   value="${formDesignEntity.layoutJS}" validationMessage="请输入布局的JS"/>
	<span class="k-invalid-msg" data-for="layoutJS"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="addElementHtml" class="required">添加元素的html&nbsp;</label>
        <input id="addElementHtml" name="addElementHtml" type="text" class="k-textbox"  
	   data-bind="value: addElementHtml"
	   value="${formDesignEntity.addElementHtml}" validationMessage="请输入添加元素的html"/>
	<span class="k-invalid-msg" data-for="addElementHtml"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="addElementJS" class="required">添加元素的JS&nbsp;</label>
        <input id="addElementJS" name="addElementJS" type="text" class="k-textbox"  
	   data-bind="value: addElementJS"
	   value="${formDesignEntity.addElementJS}" validationMessage="请输入添加元素的JS"/>
	<span class="k-invalid-msg" data-for="addElementJS"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="addElementDS" class="required">添加元素的DataSource&nbsp;</label>
        <input id="addElementDS" name="addElementDS" type="text" class="k-textbox"  
	   data-bind="value: addElementDS"
	   value="${formDesignEntity.addElementDS}" validationMessage="请输入添加元素的DataSource"/>
	<span class="k-invalid-msg" data-for="addElementDS"></span>
	</td>
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
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
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