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
<title>编辑模板信息</title>
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
        "name": "${formTemplate.name}",
        "componentId": "${formTemplate.componentId}",
        "createBy": "${formTemplate.createBy}",
        "createDate": "<fmt:formatDate value='${formTemplate.createDate}' pattern='MM/dd/yyyy'/>",
        "type": "${formTemplate.type}",
        "deleteFlag": "${formTemplate.deleteFlag}",
        "id": "${formTemplate.id}"
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
  	   form.action = "<%=request.getContextPath()%>/mx/form/template/saveFormTemplate";
       form.target = 'hidden_frame';
  	   form.submit();
       return;
       var link = "<%=request.getContextPath()%>/mx/form/template/saveFormTemplate";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑模板信息">&nbsp;
编辑模板信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate" enctype="multipart/form-data">
<input type="hidden" id="id" name="id" value="${formTemplate.id}"/>
<input type="hidden" id="componentId" name="componentId" value="${componentId}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">模板名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${formTemplate.name}" validationMessage="请输入模板名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="type" class="required">模板类型&nbsp;</label>
	    <select id="type" name="type">
			<option value="attr">属性模板</option>
			<option value="data">数据模板</option>
			<option value="event">事件模板</option>
      <option value="css">样式模板</option>
      <option value="style">风格模板</option>
      <option value="render">渲染模板</option>
			<option value="mapping">映射模板</option>
        </select>  
		<script type="text/javascript">
			if("${formTemplate.type}" != ""){
			    document.getElementById("type").value="${formTemplate.type}";
			}
		</script>
	<span class="k-invalid-msg" data-for="type"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
  <label for="template" class="required">模板&nbsp;</label>
        <textarea id="template" name="template" class="k-textbox"  style="width: 370px" rows="10" validationMessage="请输入模板信息">${formTemplate.template}</textarea>
  <span class="k-invalid-msg" data-for="template"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
  <label for="image" class="required">图片&nbsp;</label>
        <input id="image" name="image" type="file" class="k-textbox"  
          validationMessage="请选择模板图片"/>
  <span class="k-invalid-msg" data-for="image"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
  <label for="image" class="required">&nbsp;</label>
        <img style="width:100px;height:100px;" src="${empty formTemplate.image_exists||formTemplate.image_exists==0? contextPath.concat('/images/notfound.jpg'):contextPath.concat('/service/designer/').concat(formTemplate.id).concat('/getTemplateImage')}" alt="..." />
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
	     //jQuery("#createDate").kendoDateTimePicker();
    });
</script>
  <script type="text/javascript">
  function callback(res){
    if(eval(res)){
      alert('保存成功！');
      window.parent.location.reload();
    }else{
      alert('保存失败！');
    }
  }
  </script>
</body>
<iframe name="hidden_frame" style="display:none;"></iframe>
</html>