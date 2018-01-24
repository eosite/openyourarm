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
<title>编辑更新数据集信息</title>
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
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/form/wdatasetSqllite/saveWdatasetSqllite',
				   data: params,
				   dataType:  'json',
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
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑更新数据集信息">&nbsp;
编辑更新数据集信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${wdatasetSqllite.id}"/>
<input type="hidden" id="nodeId" name="nodeId" value="${param.nodeId}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <%-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="dataSetCode" class="required">数据集代码&nbsp;</label>
        <input id="dataSetCode" name="dataSetCode" type="text" class="k-textbox"  
	   data-bind="value: dataSetCode"
	   value="${wdatasetSqllite.dataSetCode}" validationMessage="请输入数据集代码"/>
	<span class="k-invalid-msg" data-for="dataSetCode"></span>
    </td>
  </tr> --%>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="sqlliteRuleName" class="required">SQLlite规则名称&nbsp;</label>
        <input id="sqlliteRuleName" name="sqlliteRuleName" type="text" class="k-textbox"  
	   data-bind="value: sqlliteRuleName"
	   value="${wdatasetSqllite.sqlliteRuleName}" validationMessage="请输入SQLlite规则名称"/>
	<span class="k-invalid-msg" data-for="sqlliteRuleName"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="sqlliteRuleDesc" class="required">${wdatasetSqllite.sqlliteRuleDesc}描述&nbsp;</label>
        <input id="sqlliteRuleDesc" name="sqlliteRuleDesc" type="text" class="k-textbox"  
	   data-bind="value: sqlliteRuleDesc"
	   value="${wdatasetSqllite.sqlliteRuleDesc}" validationMessage="请输入描述"/>
	<span class="k-invalid-msg" data-for="sqlliteRuleDesc"></span>
    </td>
  </tr>
 
    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:saveData();">保存</button>
	</div>
	</td>
      </tr>
</table>   
</form>
</div>     
<script>
    jQuery(document).ready(function() {
	    jQuery("#ver").kendoNumericTextBox();			 
	     //jQuery("#createDatetime").kendoDateTimePicker();
	     //jQuery("#modifyDatetime").kendoDateTimePicker();
    });
</script>
</body>
</html>