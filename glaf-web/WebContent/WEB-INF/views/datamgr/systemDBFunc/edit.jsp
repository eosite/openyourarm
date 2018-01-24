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
<title>编辑数据库公共函数表信息</title>
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
        "code": "${systemDBFunc.code}",
        "name": "${systemDBFunc.name}",
        "publicParams": "${systemDBFunc.publicParams}",
        "desc": "${systemDBFunc.desc}",
        "createBy": "${systemDBFunc.createBy}",
        "createTime": "<fmt:formatDate value='${systemDBFunc.createTime}' pattern='MM/dd/yyyy'/>",
        "updateBy": "${systemDBFunc.updateBy}",
        "updateTime": "<fmt:formatDate value='${systemDBFunc.updateTime}' pattern='MM/dd/yyyy'/>",
        "deleteFlag": "${systemDBFunc.deleteFlag}",
        "id": "${systemDBFunc.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/datamgr/systemDBFunc/saveSystemDBFunc";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/datamgr/systemDBFunc/saveSystemDBFunc";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑数据库公共函数表信息">&nbsp;
编辑数据库公共函数表信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${systemDBFunc.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="code" class="required">代码&nbsp;</label>
        <input id="code" name="code" type="text" class="k-textbox"  
	   data-bind="value: code"
	   value="${systemDBFunc.code}" validationMessage="请输入代码"/>
	<span class="k-invalid-msg" data-for="code"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${systemDBFunc.name}" validationMessage="请输入名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="publicParams" class="required">公共参数说明&nbsp;</label>
        <input id="publicParams" name="publicParams" type="text" class="k-textbox"  
	   data-bind="value: publicParams"
	   value="${systemDBFunc.publicParams}" validationMessage="请输入公共参数说明"/>
	<span class="k-invalid-msg" data-for="publicParams"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="desc" class="required">描述&nbsp;</label>
        <input id="desc" name="desc" type="text" class="k-textbox"  
	   data-bind="value: desc"
	   value="${systemDBFunc.desc}" validationMessage="请输入描述"/>
	<span class="k-invalid-msg" data-for="desc"></span>
    </td>
  </tr>
 <%--  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="createBy" class="required">CREATEBY_&nbsp;</label>
        <input id="createBy" name="createBy" type="text" class="k-textbox"  
	   data-bind="value: createBy"
	   value="${systemDBFunc.createBy}" validationMessage="请输入CREATEBY_"/>
	<span class="k-invalid-msg" data-for="createBy"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="createTime" class="required">CREATETIME_&nbsp;</label>
	<input id="createTime" name="createTime" type="text" class="k-textbox" 
	       data-role='datepicker' data-type="date" data-bind="value: createTime"
	   
	       value="<fmt:formatDate value="${systemDBFunc.createTime}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入CREATETIME_"/>
	<span class="k-invalid-msg" data-for="createTime"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="updateBy" class="required">UPDATEBY_&nbsp;</label>
        <input id="updateBy" name="updateBy" type="text" class="k-textbox"  
	   data-bind="value: updateBy"
	   value="${systemDBFunc.updateBy}" validationMessage="请输入UPDATEBY_"/>
	<span class="k-invalid-msg" data-for="updateBy"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="updateTime" class="required">UPDATETIME_&nbsp;</label>
	<input id="updateTime" name="updateTime" type="text" class="k-textbox" 
	       data-role='datepicker' data-type="date" data-bind="value: updateTime"
	   
	       value="<fmt:formatDate value="${systemDBFunc.updateTime}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入UPDATETIME_"/>
	<span class="k-invalid-msg" data-for="updateTime"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="deleteFlag" class="required">DELETE_FLAG_&nbsp;</label>
	<input id="deleteFlag" name="deleteFlag" type="text" class="k-textbox" 
	   data-bind="value: deleteFlag"
	       value="${systemDBFunc.deleteFlag}" validationMessage="请输入DELETE_FLAG_"/>
	<span class="k-invalid-msg" data-for="deleteFlag"></span>
    </td>
  </tr> --%>
	   
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
	     //jQuery("#createTime").kendoDateTimePicker();
	     //jQuery("#updateTime").kendoDateTimePicker();
	    jQuery("#deleteFlag").kendoNumericTextBox();			 
    });
</script>
</body>
</html>