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
<title>编辑全文检索数据来源信息</title>
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
        width: 150px;
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
	input.k-textbox,textarea{
		width:400px;
	}
</style>
<script type="text/javascript">
  var rule=${sysFullTextSearchSrc.rule_==null?"":sysFullTextSearchSrc.rule_};              
  jQuery(function() {
    var viewModel = kendo.observable({
        "serviceName_": "${sysFullTextSearchSrc.serviceName_}",
        "serviceAddress_": "${sysFullTextSearchSrc.serviceAddress_}",
        "rule_": (rule!=''?JSON.stringify(rule):""),
        "fullTextServer_": "${sysFullTextSearchSrc.fullTextServer_}",
        "indexName_": "${sysFullTextSearchSrc.indexName_}",
        "typeName_": "${sysFullTextSearchSrc.typeName_}",
        "createBy_": "${sysFullTextSearchSrc.createBy_}",
        "createTime_": "<fmt:formatDate value='${sysFullTextSearchSrc.createTime_}' pattern='MM/dd/yyyy'/>",
        "updateBy_": "${sysFullTextSearchSrc.updateBy_}",
        "updateTime_": "<fmt:formatDate value='${sysFullTextSearchSrc.updateTime_}' pattern='MM/dd/yyyy'/>",
        "deleteFlag_": "${sysFullTextSearchSrc.deleteFlag_}",
        "id": "${sysFullTextSearchSrc.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/apps/sysFullTextSearchSrc/saveSysFullTextSearchSrc";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/apps/sysFullTextSearchSrc/saveSysFullTextSearchSrc";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑全文检索数据来源信息">&nbsp;
编辑全文检索数据来源信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${sysFullTextSearchSrc.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="serviceName_" class="required">服务名称&nbsp;</label>
        <input id="serviceName_" name="serviceName_" type="text" class="k-textbox"  
	   data-bind="value: serviceName_"
	   value="${sysFullTextSearchSrc.serviceName_}" validationMessage="请输入serviceName"/>
	<span class="k-invalid-msg" data-for="serviceName_"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="serviceAddress_" class="required">服务地址&nbsp;</label>
        <input id="serviceAddress_" name="serviceAddress_" type="text" class="k-textbox"  
	   data-bind="value: serviceAddress_"
	   value="${sysFullTextSearchSrc.serviceAddress_}" validationMessage="请输入serviceAddress"/>
	<span class="k-invalid-msg" data-for="serviceAddress_"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="rule_" class="required">规则&nbsp;</label>
        <textarea id="rule_" name="rule_" type="text" rows="10" cols="30" style="height:200px"
	   data-bind="value: rule_"
	   value="${sysFullTextSearchSrc.rule_}" validationMessage="请输入rule"></textarea>
	<span class="k-invalid-msg" data-for="rule_"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="fullTextServer_" class="required">全文检索服务地址&nbsp;</label>
        <input id="fullTextServer_" name="fullTextServer_" type="text" class="k-textbox"  
	   data-bind="value: fullTextServer_"
	   value="${sysFullTextSearchSrc.fullTextServer_}" validationMessage="请输入fullTextServer"/>
	<span class="k-invalid-msg" data-for="fullTextServer_"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="indexName_" class="required">索引名称&nbsp;</label>
        <input id="indexName_" name="indexName_" type="text" class="k-textbox"  
	   data-bind="value: indexName_"
	   value="${sysFullTextSearchSrc.indexName_}" validationMessage="请输入indexName"/>
	<span class="k-invalid-msg" data-for="indexName_"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="typeName_" class="required">类型名称&nbsp;</label>
        <input id="typeName_" name="typeName_" type="text" class="k-textbox"  
	   data-bind="value: typeName_"
	   value="${sysFullTextSearchSrc.typeName_}" validationMessage="请输入typeName"/>
	<span class="k-invalid-msg" data-for="typeName_"></span>
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
	     //jQuery("#createTime_").kendoDateTimePicker();
	     //jQuery("#updateTime_").kendoDateTimePicker();
	    jQuery("#deleteFlag_").kendoNumericTextBox();			 
    });
</script>
</body>
</html>