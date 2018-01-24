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
<title>编辑字段信息</title>
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
        "name": "${columnDefinition.name}",
        "title": "${columnDefinition.title}",
        "width": "${columnDefinition.width}"
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
	     var link = "<%=request.getContextPath()%>/mx/system/dataitem/updateColumn";
	     var params = jQuery("#iForm").formSerialize();
		 jQuery.ajax({
				   type: "POST",
				   url: link,
				   javaType:  'json',
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑字段信息">&nbsp;
编辑字段信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${columnDefinition.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	   <label for="tablename" class="required">列名&nbsp;</label>
	   <c:choose>
	     <c:when test="${!empty columnDefinition}">
	         ${columnDefinition.columnName}
	     <input type="hidden" name="columnName" value="${columnDefinition.columnName}">
	    </c:when>
	    <c:otherwise>
        </c:otherwise>
	   </c:choose>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	 <c:choose>
	   <c:when test="${!empty columnDefinition}">
	    <label for="javaType" class="required">类型&nbsp;</label>
        <select id="javaType" name="javaType" readonly="true">
		    <option value="">----请选择----</option>
			<option value="Integer">整数型</option>
			<option value="Long">长整数型</option>
			<option value="Double">数值型</option>
			<option value="Date">日期型</option>
			<option value="String">字符串型</option>
        </select>
	    <span class="k-invalid-msg" data-for="javaType"></span>
	   </c:when>
	   <c:otherwise>      
	    <label for="javaType" class="required">类型&nbsp;</label>
        <select id="javaType" name="javaType">
		    <option value="">----请选择----</option>
			<option value="Integer">整数型</option>
			<option value="Long">长整数型</option>
			<option value="Double">数值型</option>
			<option value="Date">日期型</option>
			<option value="String">字符串型</option>
        </select>
	    <span class="k-invalid-msg" data-for="javaType"></span>
	   </c:otherwise>
	   </c:choose>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name"
	           value="${columnDefinition.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title"
	           value="${columnDefinition.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="length" class="required">长度&nbsp;</label>
	  <input id="length" name="length" type="text" class="k-textbox" 
	       data-bind="value: length" data-role="numerictextbox"
           data-format="i" data-min="1" data-max="4000"
		   min="1" max="4000" step="1"
	       value="${columnDefinition.length}" validationMessage="请输入长度"/>
	   <span class="k-invalid-msg" data-for="length"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="width" class="required">列表宽度&nbsp;</label>
	  <input id="width" name="width" type="text" class="k-textbox" 
	       data-bind="value: width" data-role="numerictextbox"
           data-format="i" data-min="30" data-max="600"
		   min="30" max="600" step="30"
	       value="${columnDefinition.width}" validationMessage="请输入列表宽度"/>
	  <span class="k-invalid-msg" data-for="width"></span>
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
</div>     
<br/>
<br/>
<script type="text/javascript">
    jQuery(function() {
			document.getElementById("javaType").value="${columnDefinition.javaType}";
	});
</script>
</body>
</html>