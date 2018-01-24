<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    List<ColumnDefinition> columns = (List<ColumnDefinition>)request.getAttribute("columns");
	String tableName =  request.getParameter("tableName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑字段信息</title>
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

   function switchTx(){
         document.getElementById("iForm").submit();
   }

   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	      var link = "<%=request.getContextPath()%>/mx/sys/tableInterface/save";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑字段信息">&nbsp;
    编辑字段信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<table width="95%" align="center" border="1" cellspacing="2" cellpadding="2" >
 <tr>
	  <td colspan="5" height="35">
	  数据库选择&nbsp;
	  <select id="databaseId" name="databaseId">
		<option value="0"></option>
		<c:forEach items="${databases}" var="item">
		<option value="${item.id}">${item.title}</option>
		</c:forEach>
	  </select>
	  <script type="text/javascript">
	    document.getElementById("databaseId").value="${databaseId}";
	  </script>
	  </td>
 </tr>
 <tr>
	  <td colspan="5" height="35">
	  数据库表&nbsp;&nbsp;&nbsp;<input type="text" name="tableName" value="${tableName}" size="30" class="k-textbox" >
	  &nbsp;中文名称&nbsp;<input type="text" name="tableNameCn" value="${tableNameCn}" size="30" class="k-textbox">
	  <input type="button" value="查询" onclick="javascript:switchTx();"  class="k-button">
	  &nbsp;（提示：选择一个库，输入表名，查询是否已经存在，如存在直接修改即可。）
	  </td>
 </tr>
 <tr height="35">
	  <td>列名</td>
	  <td>类型</td>
	  <td>标题</td>
 </tr>
 <c:forEach items="${columns}" var="item">
	<tr height="35">
	  <td>${item.columnName}</td>
	  <td>
	    ${item.javaType}&nbsp;
	  <c:choose> 
	    <c:when test="${item.javaType=='Integer'}">整数型</c:when>
		<c:when test="${item.javaType=='Long'}">长整数型</c:when>
		<c:when test="${item.javaType=='Double'}">数值型</c:when>
		<c:when test="${item.javaType=='Boolean'}">逻辑型</c:when>
		<c:when test="${item.javaType=='Date'}">日期型</c:when>
		<c:when test="${item.javaType=='String'}">字符串型</c:when>
		<c:otherwise>&nbsp;</c:otherwise>
	  </c:choose> 
	  </td>
	  <td>
	    <input type="text" id="${item.columnName}_title" name="${item.columnName}_title" 
	           value="${item.title}" size="50" class="k-textbox">
	  </td>
	 </tr>
 </c:forEach>
  
  <tr>
    <td colspan="5" align="center" valign="bottom" height="25">&nbsp;
    <div>
      <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	</div>
	</td>
  </tr>
</table>   
</form>
</div>     
<br> 
<br> 
<br> 
<br> 
</body>
</html>