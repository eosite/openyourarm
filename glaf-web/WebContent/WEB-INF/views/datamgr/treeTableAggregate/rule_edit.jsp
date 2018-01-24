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
<title>编辑聚合表信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
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
        "aggregateId": "${aggregateId}",
        "aggregateType": "${treeTableAggregateRule.aggregateType}",
        "name": "${treeTableAggregateRule.name}",
        "title": "${treeTableAggregateRule.title}",
        "sourceColumnName": "${treeTableAggregateRule.sourceColumnName}",
        "sourceColumnTitle": "${treeTableAggregateRule.sourceColumnTitle}",
        "targetColumnName": "${treeTableAggregateRule.targetColumnName}",
        "targetColumnTitle": "${treeTableAggregateRule.targetColumnTitle}",
        "targetColumnType": "${treeTableAggregateRule.targetColumnType}",
        "locked": "${treeTableAggregateRule.locked}",
        "id": "${treeTableAggregateRule.id}"
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
	      var link = "<%=request.getContextPath()%>/mx/sys/treeTableAggregateRule/saveRule";
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
					   if(data.statusCode == 200){
					       window.parent.location.reload();
					   }
				   }
			 });
       }
   }

   function saveAs(){
	   document.getElementById("id").value="";
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	      var link = "<%=request.getContextPath()%>/mx/sys/treeTableAggregateRule/saveRule";
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
					   if(data.statusCode == 200){
					       //window.parent.location.reload();
					   }
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑聚合表规则">&nbsp;
    编辑聚合表规则</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="aggregateId" name="aggregateId" value="${aggregateId}"/>
<input type="hidden" id="id" name="id" value="${treeTableAggregateRule.id}"/>
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="5">

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style="width:380px"
	           value="${treeTableAggregateRule.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
		<label for="sourceColumnName" class="required">源字段&nbsp;</label>
		<select id="sourceColumnName" name="sourceColumnName" >
			<option value="">----请选择----</option>    
			<c:forEach items="${columns}" var="item">
			<option value="${item.columnName}">${item.columnName}</option>     
			</c:forEach>
		</select>
        <script type="text/javascript">
	       document.getElementById("sourceColumnName").value="${treeTableAggregateRule.sourceColumnName}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="targetColumnTitle" class="required">转换后列中文名称&nbsp;</label>
        <input id="targetColumnTitle" name="targetColumnTitle" type="text" class="k-textbox"  
	           data-bind="value: targetColumnTitle" style="width:380px"
	           value="${treeTableAggregateRule.targetColumnTitle}" validationMessage="请输入转换后列中文名称"/>
	    <span class="k-invalid-msg" data-for="targetColumnTitle"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="targetColumnType" class="required">字段类型&nbsp;</label>
		<select id="targetColumnType" name="targetColumnType" >
			<option value="">----请选择----</option>    
			<option value="String">字符串型</option>
			<option value="Integer">整数型</option>    
			<option value="Long">长整数型</option> 
			<option value="Double">数值型</option>    
		</select>
	    <span class="k-invalid-msg" data-for="targetColumnType"></span>
	    <script type="text/javascript">
	       document.getElementById("targetColumnType").value="${treeTableAggregateRule.targetColumnType}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="aggregateType" class="required">聚合类型&nbsp;</label>
		<select id="aggregateType" name="aggregateType" >
			<option value="">----请选择----</option>    
			<option value="SUM">逐级汇总</option>   
		</select>
	    <span class="k-invalid-msg" data-for="aggregateType"></span>
	    <script type="text/javascript">
	       document.getElementById("aggregateType").value="${treeTableAggregateRule.aggregateType}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left" height="25">&nbsp;</td>
    <td align="left">
	  <label for="locked" class="required">是否有效&nbsp;</label>
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${treeTableAggregateRule.locked}";
	  </script>
    </td>
  </tr>

  <tr>
    <td colspan="2" align="center" valign="bottom" height="25">&nbsp;
    <div>
      <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	  &nbsp;
	  <button type="button" id="iconButton"  class="k-button " style="width: 90px" onclick="javascript:saveAs();">另存</button>
	</div>
	</td>
  </tr>
</table>   
</form>
</div>     
<br/>
<br/>
</body>
</html>