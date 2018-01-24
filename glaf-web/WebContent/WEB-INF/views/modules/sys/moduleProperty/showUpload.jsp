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
<title>编辑模块属性信息</title>
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
        "category": "${moduleProperty.category}",
        "name": "${moduleProperty.name}",
        "type": "${moduleProperty.type}",
        "title": "${moduleProperty.title}",
        "locale": "${moduleProperty.locale}",
        "value": "${moduleProperty.value}",
        "locked": "${moduleProperty.locked}",
        "id": "${moduleProperty.id}"
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
        var category = jQuery("#category").val();
	    var locale = jQuery("#locale").val();
	    if(category == ""){
             alert('请选择分类！');
			 return;
	    }
		document.iForm.action="<%=request.getContextPath()%>/mx/sys/moduleProperty/upload";
	    document.iForm.submit(); 
   }
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="导入模块属性信息">&nbsp;
导入模块属性信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate" enctype="multipart/form-data">
<input type="hidden" id="id" name="id" value="${moduleProperty.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="category" class="required">分类&nbsp;</label>
        <select id="category" name="category">
			<option value="" selected>----请选择----</option>
			<c:forEach items="${categories}" var="cat">
			<option value="${cat.name}">${cat.title}</option>
			</c:forEach>
		</select>
		<script type="text/javascript">
		     jQuery("#category").val("${moduleProperty.category}");
		</script>
	    <span class="k-invalid-msg" data-for="category"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	     <label for="locale" class="required">地域&nbsp;</label>
         <select id="locale" name="locale" >
			<option value="" selected>----请选择----</option>
			<option value="zh_CN">中文</option>
			<option value="en_US">英文</option>
		  </select>
		  <script type="text/javascript">
		     jQuery("#locale").val("${moduleProperty.locale}");
		  </script>
	     <span class="k-invalid-msg" data-for="locale"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="file" class="required">导入文件&nbsp;</label>
        <input id="file" name="file" type="file" class="k-textbox"  
	           data-bind="value: file" style="width:320px;">
	    <span class="k-invalid-msg" data-for="file"></span>
		<br>&nbsp;<div style="margin-left:120px;margin-top:2px;">选择扩展名为.properties的属性文件</div>
    </td>
  </tr>
  
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
 
</body>
</html>