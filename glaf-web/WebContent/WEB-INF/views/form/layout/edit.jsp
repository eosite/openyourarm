<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.form.core.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	FormLayout formLayout = (FormLayout) request.getAttribute("formLayout");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑表单布局信息</title>
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
        "deploymentId": "${formLayout.deploymentId}",
        "name": "${formLayout.name}",
        "type": "${formLayout.type}",
        "json": "<%=formLayout.getJson()%>",
        "createDate": "<fmt:formatDate value='${formLayout.createDate}' pattern='MM/dd/yyyy'/>",
        "createBy": "${formLayout.createBy}",
        "status": "${formLayout.status}",
        "height": "${formLayout.height}",
        "width": "${formLayout.width}",
        "id": "${formLayout.id}"
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
		   form.method="post";
		   form.action = "<%=request.getContextPath()%>/mx/form/layout/save";
		   form.submit();
	   /*
	   var link = "<%=request.getContextPath()%>/mx/form/layout/saveFormLayout";
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
			 */
       }
   }
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑表单布局信息">&nbsp;
编辑表单布局信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" enctype="multipart/form-data" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${formLayout.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	     data-bind="value: name" style=" width:420px;"
	   value="${formLayout.name}" validationMessage="请输入名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
	</td>
    </tr>
    <!-- <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="type" class="required">类型&nbsp;</label>
        <input id="type" name="type" type="text" class="k-textbox"  
	   data-bind="value: type"
	   value="${formLayout.type}" validationMessage="请输入类型"/>
	<span class="k-invalid-msg" data-for="type"></span>
	</td>
    </tr> -->
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="json" class="required">json&nbsp;</label>
         <textarea id="json" name="json" data-bind="value: json" rows="8" cols="56" class="k-textbox" style="height:180px; width:420px;" readonly="true" disabled="true"></textarea>   
	</td>
    </tr>

	<tr>
	  <td width="2%" align="left">&nbsp;</td>
	  <td align="left">
	   <label for="file" class="required">布局图片&nbsp;</label>
	   <input type="file" id="file" name="file" size="50" class="input-file">
	   <br><br>
	   <span style="margin-left: 115px;font-size: 14px;">如果需要更改布局预览图片，请上传。</span>
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
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" 
		  onclick="javascript:save();">保存</button>
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