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
<title>编辑执行模板信息</title>
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
        //"category": "${executionTemplate.category}",
        "title": "${executionTemplate.title}",
        //"type": "${executionTemplate.type}",
        //"condition": '${executionTemplate.condition}',
		//"command": '${executionTemplate.command}',
		//"expression": '${executionTemplate.expression}',
		"separator": '${executionTemplate.separator}',
        //"verifyCondition": '${executionTemplate.verifyCondition}',
		//"verifyExpression": '${executionTemplate.verifyExpression}',
		"group": "${executionTemplate.group}",
        "retryCount": "${executionTemplate.retryCount}",
        "sortNo": "${executionTemplate.sortNo}",
        "active": "${executionTemplate.active}",
        "id": "${executionTemplate.id}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#saveButton").kendoButton({
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

   function saveData(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	    //form.method="post";
	    //form.action = "<%=request.getContextPath()%>/mx/deployment/executionTemplate/saveExecutionTemplate";
	    //form.submit();
	    var link = "<%=request.getContextPath()%>/mx/deployment/executionTemplate/saveExecutionTemplate";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑执行模板">&nbsp;
编辑执行模板</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${executionTemplate.id}"/>
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" > 
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="title" class="required">标题&nbsp;</label>
    <input id="title" name="title" type="text" class="k-textbox"  
	   data-bind="value: title" style="width:480px;"
	   value="${executionTemplate.title}" validationMessage="请输入标题"/>
	<span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="category" class="required">类别&nbsp;</label>
	  <select id="category" name="category">
        <option value="">----请选择----</option>
		<c:forEach items="${dicts}" var="item">
		<option value="${item.code}">${item.name}</option>     
		</c:forEach>
      </select>
	  <script type="text/javascript">
	       document.getElementById("category").value="${executionTemplate.category}";
	  </script> 
	<span class="k-invalid-msg" data-for="category"></span>
    </td>
  </tr> -->
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="type" class="required">类型&nbsp;</label>
      <select id="type" name="type">
	    <option value="exec">exec执行命令</option>
		<option value="sftp">sftp文件传输</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("type").value="${executionTemplate.type}";
	  </script>     
	  <span class="k-invalid-msg" data-for="type"></span>
    </td>
  </tr>
  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="group" class="required">分组编号&nbsp;</label>
	<select id="group" name="group">
		<%
		 for(int i=0; i<=50; i++){
         %>
		 <option value="group_<%=i%>">Group_<%=i%></option>
		 <%
		 }
		 %>
	 </select> 
	 <script type="text/javascript">
	       document.getElementById("group").value="${executionTemplate.group}";
	 </script>   
	<span class="k-invalid-msg" data-for="group"></span>
    </td>
  </tr> -->
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="separator" class="required">命令分隔符&nbsp;</label>
    <input id="separator" name="separator" type="text" class="k-textbox"  
	       data-bind="value: separator" style="width:120px;" maxlength="50"
	       value="${executionTemplate.separator}" validationMessage="请输入命令分隔符"/>
	<span class="k-invalid-msg" data-for="separator"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="condition" class="required">检查条件&nbsp;</label>
    <textarea id="condition" name="condition" rows="12" cols="68" class="k-textbox"
			style="width:480px;height:90px;" >${executionTemplate.condition}</textarea>
	<div style="margin-top:5px;">
		<span style="color:red; margin-left:110px;">
		  （提示：检查条件可以是某个操作系统命令，输出变量名为var 。）
		</span>
	</div>
	<span class="k-invalid-msg" data-for="condition"></span>
    </td>
  </tr>
    <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="expression" class="required">表达式&nbsp;</label>
    <textarea id="expression" name="expression" rows="12" cols="68" class="k-textbox"
			style="width:480px;height:60px;" >${executionTemplate.expression}</textarea>
	<div style="margin-top:5px;">
		<span style="color:red; margin-left:110px;">
		  （提示：如果输入表达式，当表达式为true时才执行命令。）
		</span>
	</div>
	<span class="k-invalid-msg" data-for="expression"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="command" class="required">命令&nbsp;</label>
    <textarea id="command" name="command" rows="12" cols="68" class="k-textbox"
			style="width:480px;height:150px;" >${executionTemplate.command}</textarea>
	<div style="margin-top:5px;">
		<span style="color:red; margin-left:110px;">
		  （提示：多条命令之间可以用指定的命令分隔符分隔。）
		</span>
	 </div>
	<span class="k-invalid-msg" data-for="command"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="retryCount" class="required">命令重试次数&nbsp;</label>
	<select id="retryCount" name="retryCount">
		 <option value="-1">不限制</option>
		 <%
		 for(int i=0; i<=5; i++){
         %>
		 <option value="<%=i%>"><%=i%></option>
		 <%
		 }
		 %>
	 </select> 
	 <script type="text/javascript">
	       document.getElementById("retryCount").value="${executionTemplate.retryCount}";
	 </script>   
	<span class="k-invalid-msg" data-for="retryCount"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="sortNo" class="required">执行顺序&nbsp;</label>
	 <select id="sortNo" name="sortNo">
		 <option value="0">----请选择----</option>
		 <%
		 for(int i=1; i<100; i++){
         %>
		 <option value="<%=i%>"><%=i%></option>
		 <%
		 }
		 %>
	 </select> 
	 <div style="margin-top:5px;">
		<span style="color:red; margin-left:110px;">
		  （提示：从小到大依次执行，数值较小的优先执行。）
		</span>
	 </div>
	 <script type="text/javascript">
	       document.getElementById("sortNo").value="${executionTemplate.sortNo}";
	 </script>   
	 <span class="k-invalid-msg" data-for="sortNo"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="verifyCondition" class="required">验证条件&nbsp;</label>
    <textarea id="verifyCondition" name="verifyCondition" rows="12" cols="68" class="k-textbox"
			style="width:480px;height:90px;" >${executionTemplate.verifyCondition}</textarea>
	<div style="margin-top:5px;">
		<span style="color:red; margin-left:110px;">
		  （提示：验证条件可以是某个操作系统命令，输出变量名为verifyVar 。）
		</span>
	</div>
	<span class="k-invalid-msg" data-for="verifyCondition"></span>
    </td>
  </tr>
    <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="verifyExpression" class="required">验证表达式&nbsp;</label>
    <textarea id="verifyExpression" name="verifyExpression" rows="12" cols="68" class="k-textbox"
			style="width:480px;height:60px;" >${executionTemplate.verifyExpression}</textarea>
	<span class="k-invalid-msg" data-for="verifyExpression"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="active" class="required">是否有效&nbsp;</label>
      <select id="active" name="active">
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("active").value="${executionTemplate.active}";
	  </script>    
	 <span class="k-invalid-msg" data-for="active"></span>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
    <div>
      <input type="button" id="saveButton" value="保存"  class="k-button k-primary" style="width: 90px" 
	         onclick="javascript:saveData();"> 
	</div>
	</td>
  </tr>   
</table>   
</form>
</div>
<br>
</body>
</html>