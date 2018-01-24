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
<title>编辑ReceiveDocApply信息</title>
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
        "subject": "${receiveDocApply.subject}",
        "securityLevel": "${receiveDocApply.securityLevel}",
        "urgencyLevel": "${receiveDocApply.urgencyLevel}",
        "receiveDocTime": "<fmt:formatDate value='${receiveDocApply.receiveDocTime}' pattern='MM/dd/yyyy'/>",
        "docType": "${receiveDocApply.docType}",
        "fromCompany": "${receiveDocApply.fromCompany}",
        "serialNumber": "${receiveDocApply.serialNumber}",
        "fromDocNo": "${receiveDocApply.fromDocNo}",
        "receiveDocNo": "${receiveDocApply.receiveDocNo}",
        "distributeCompany": "${receiveDocApply.distributeCompany}",
        "nibanOption": "${receiveDocApply.nibanOption}",
        "leadOption": "${receiveDocApply.leadOption}",
        "chengbanOption": "${receiveDocApply.chengbanOption}",
        "remark": "${receiveDocApply.remark}",
        "createDate": "<fmt:formatDate value='${receiveDocApply.createDate}' pattern='MM/dd/yyyy'/>",
        "updateDate": "<fmt:formatDate value='${receiveDocApply.updateDate}' pattern='MM/dd/yyyy'/>",
        "createBy": "${receiveDocApply.createBy}",
        "updateBy": "${receiveDocApply.updateBy}",
        "status": "${receiveDocApply.status}",
        "id": "${receiveDocApply.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/oa/doc/receiveDocApply/saveReceiveDocApply";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/oa/doc/receiveDocApply/saveReceiveDocApply";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑ReceiveDocApply信息">&nbsp;
编辑ReceiveDocApply信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${receiveDocApply.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="subject" class="required">subject&nbsp;</label>
        <input id="subject" name="subject" type="text" class="k-textbox"  
	   data-bind="value: subject"
	   value="${receiveDocApply.subject}" validationMessage="请输入subject"/>
	<span class="k-invalid-msg" data-for="subject"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="securityLevel" class="required">securityLevel&nbsp;</label>
	<input id="securityLevel" name="securityLevel" type="text" class="k-textbox" 
	   data-bind="value: securityLevel"
	       value="${receiveDocApply.securityLevel}" validationMessage="请输入securityLevel"/>
	<span class="k-invalid-msg" data-for="securityLevel"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="urgencyLevel" class="required">urgencyLevel&nbsp;</label>
	<input id="urgencyLevel" name="urgencyLevel" type="text" class="k-textbox" 
	   data-bind="value: urgencyLevel"
	       value="${receiveDocApply.urgencyLevel}" validationMessage="请输入urgencyLevel"/>
	<span class="k-invalid-msg" data-for="urgencyLevel"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="receiveDocTime" class="required">receiveDocTime&nbsp;</label>
	<input id="receiveDocTime" name="receiveDocTime" type="text" class="k-textbox" 
	       data-role='datepicker' data-type="date" data-bind="value: receiveDocTime"
	   
	       value="<fmt:formatDate value="${receiveDocApply.receiveDocTime}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入receiveDocTime"/>
	<span class="k-invalid-msg" data-for="receiveDocTime"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="docType" class="required">docType&nbsp;</label>
        <input id="docType" name="docType" type="text" class="k-textbox"  
	   data-bind="value: docType"
	   value="${receiveDocApply.docType}" validationMessage="请输入docType"/>
	<span class="k-invalid-msg" data-for="docType"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="fromCompany" class="required">fromCompany&nbsp;</label>
        <input id="fromCompany" name="fromCompany" type="text" class="k-textbox"  
	   data-bind="value: fromCompany"
	   value="${receiveDocApply.fromCompany}" validationMessage="请输入fromCompany"/>
	<span class="k-invalid-msg" data-for="fromCompany"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="serialNumber" class="required">serialNumber&nbsp;</label>
        <input id="serialNumber" name="serialNumber" type="text" class="k-textbox"  
	   data-bind="value: serialNumber"
	   value="${receiveDocApply.serialNumber}" validationMessage="请输入serialNumber"/>
	<span class="k-invalid-msg" data-for="serialNumber"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="fromDocNo" class="required">fromDocNo&nbsp;</label>
        <input id="fromDocNo" name="fromDocNo" type="text" class="k-textbox"  
	   data-bind="value: fromDocNo"
	   value="${receiveDocApply.fromDocNo}" validationMessage="请输入fromDocNo"/>
	<span class="k-invalid-msg" data-for="fromDocNo"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="receiveDocNo" class="required">receiveDocNo&nbsp;</label>
        <input id="receiveDocNo" name="receiveDocNo" type="text" class="k-textbox"  
	   data-bind="value: receiveDocNo"
	   value="${receiveDocApply.receiveDocNo}" validationMessage="请输入receiveDocNo"/>
	<span class="k-invalid-msg" data-for="receiveDocNo"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="distributeCompany" class="required">distributeCompany&nbsp;</label>
        <input id="distributeCompany" name="distributeCompany" type="text" class="k-textbox"  
	   data-bind="value: distributeCompany"
	   value="${receiveDocApply.distributeCompany}" validationMessage="请输入distributeCompany"/>
	<span class="k-invalid-msg" data-for="distributeCompany"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="nibanOption" class="required">nibanOption&nbsp;</label>
        <input id="nibanOption" name="nibanOption" type="text" class="k-textbox"  
	   data-bind="value: nibanOption"
	   value="${receiveDocApply.nibanOption}" validationMessage="请输入nibanOption"/>
	<span class="k-invalid-msg" data-for="nibanOption"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="leadOption" class="required">leadOption&nbsp;</label>
        <input id="leadOption" name="leadOption" type="text" class="k-textbox"  
	   data-bind="value: leadOption"
	   value="${receiveDocApply.leadOption}" validationMessage="请输入leadOption"/>
	<span class="k-invalid-msg" data-for="leadOption"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="chengbanOption" class="required">chengbanOption&nbsp;</label>
        <input id="chengbanOption" name="chengbanOption" type="text" class="k-textbox"  
	   data-bind="value: chengbanOption"
	   value="${receiveDocApply.chengbanOption}" validationMessage="请输入chengbanOption"/>
	<span class="k-invalid-msg" data-for="chengbanOption"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="remark" class="required">remark&nbsp;</label>
        <input id="remark" name="remark" type="text" class="k-textbox"  
	   data-bind="value: remark"
	   value="${receiveDocApply.remark}" validationMessage="请输入remark"/>
	<span class="k-invalid-msg" data-for="remark"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="createDate" class="required">createDate&nbsp;</label>
	<input id="createDate" name="createDate" type="text" class="k-textbox" 
	       data-role='datepicker' data-type="date" data-bind="value: createDate"
	   
	       value="<fmt:formatDate value="${receiveDocApply.createDate}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入createDate"/>
	<span class="k-invalid-msg" data-for="createDate"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="updateDate" class="required">updateDate&nbsp;</label>
	<input id="updateDate" name="updateDate" type="text" class="k-textbox" 
	       data-role='datepicker' data-type="date" data-bind="value: updateDate"
	   
	       value="<fmt:formatDate value="${receiveDocApply.updateDate}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入updateDate"/>
	<span class="k-invalid-msg" data-for="updateDate"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="createBy" class="required">createBy&nbsp;</label>
        <input id="createBy" name="createBy" type="text" class="k-textbox"  
	   data-bind="value: createBy"
	   value="${receiveDocApply.createBy}" validationMessage="请输入createBy"/>
	<span class="k-invalid-msg" data-for="createBy"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="updateBy" class="required">updateBy&nbsp;</label>
        <input id="updateBy" name="updateBy" type="text" class="k-textbox"  
	   data-bind="value: updateBy"
	   value="${receiveDocApply.updateBy}" validationMessage="请输入updateBy"/>
	<span class="k-invalid-msg" data-for="updateBy"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="status" class="required">status&nbsp;</label>
	<input id="status" name="status" type="text" class="k-textbox" 
	   data-bind="value: status"
	       value="${receiveDocApply.status}" validationMessage="请输入status"/>
	<span class="k-invalid-msg" data-for="status"></span>
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
	    jQuery("#securityLevel").kendoNumericTextBox();			 
	    jQuery("#urgencyLevel").kendoNumericTextBox();			 
	     //jQuery("#receiveDocTime").kendoDateTimePicker();
	     //jQuery("#createDate").kendoDateTimePicker();
	     //jQuery("#updateDate").kendoDateTimePicker();
	    jQuery("#status").kendoNumericTextBox();			 
    });
</script>
</body>
</html>