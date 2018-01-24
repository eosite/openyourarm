<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    List<ColumnDefinition> columns = (List<ColumnDefinition>)request.getAttribute("columns");
	List<String> tableNames = (List<String>)request.getAttribute("tableNames");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑组合规则信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<style scoped>

.table-border { background-color:#3399cc;  font-family:"宋体"}
.table-content { background-color:#ffffff; font-size: 12px; font-family:"宋体"}

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

   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	      var link = "<%=request.getContextPath()%>/mx/sys/tableCombination/saveTableCombinationRules";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑组合规则信息">&nbsp;
    编辑组合规则信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="tableCombinationId" name="tableCombinationId" value="${tableCombination.id}"/>
<table width="95%" align="center" border="0" cellspacing="1" cellpadding="2" class="table-border" >
  <tr>
    <td colspan="<%=columns.size()+1%>" class="table-content">模板表${tableCombination.templateTableName}</td>
  </tr>

  <tr>
	<c:forEach items="${columns}" var="item">
	<td class="table-content">${item.columnName}</td>
	</c:forEach>
  </tr>

  <tr>
	<c:forEach items="${columns}" var="item">
	<td class="table-content">
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
	</c:forEach>
  </tr>

  <tr>
	<c:forEach items="${columns}" var="item">
	<td height="30" class="table-content">
	<input type="text" id="title_${fn:toLowerCase(item.columnName)}" name="title_${fn:toLowerCase(item.columnName)}" 
	       size="30" class="txt" value="${item.title}">
	</td>
	</c:forEach>
  </tr>

  <c:forEach items="${tableNames}" var="tableName">
  <%
   String tableName = (String) pageContext.getAttribute("tableName");
   List<ColumnDefinition> columns2 = (List<ColumnDefinition>)request.getAttribute(tableName + "_columns");
   pageContext.setAttribute("columns2", columns2);
   String sqlCondition = "";
   TableCombinationRule rule = (TableCombinationRule)request.getAttribute(tableName+"_rule");
   if(rule != null){
	   sqlCondition = rule.getSqlCondition();
   }
   if(sqlCondition == null){
	   sqlCondition="";
   }
  %>
  <tr>
    <td colspan="<%=columns.size()+1%>" height="50" class="table-content">
	待合成表：${tableName}  &nbsp;&nbsp; 
	转换条件：<input id="${tableName}_sqlCondition" name="${tableName}_sqlCondition" type="text" class="k-textbox"  
                     maxlength="2000" style="height:25px;width:580px;text-align:top;" value="<%=sqlCondition%>" />
	</td>
  </tr>

  <tr>
	<c:forEach items="${syncColumns}" var="item">
	<td height="30" class="table-content">
	    <select id="${tableName}_${item}" name="${tableName}_${item}" >
			<option value="">----请选择----</option>    
			<c:forEach items="${columns2}" var="item2">
			<%
			  ColumnDefinition column = (ColumnDefinition) pageContext.getAttribute("item2");
			  String columnName = column.getColumnName();
			  columnName = columnName.toLowerCase();
			%>
			<option value="<%=columnName%>"><%=columnName%></option>     
			</c:forEach>
		</select>
		<script type="text/javascript">
		   <%
		     String col = (String)pageContext.getAttribute("item");
			 String name = tableName + "_"+col+"_col";
		   %>
		   document.getElementById("${tableName}_${item}").value="<%=request.getAttribute(name)%>";
		</script>
	</td>
	</c:forEach>
  </tr>

  </c:forEach>
  
  <c:forEach items="${dataSetList}" var="dataSet">
  <%
     DataSet dataSet = (DataSet)pageContext.getAttribute("dataSet");
	 List<ColumnDefinition> columns2 = (List<ColumnDefinition>)request.getAttribute(dataSet.getId() + "_columns");
     pageContext.setAttribute("columns2", columns2);
  %>
  <tr>
    <td colspan="<%=columns.size()+1%>" height="50" class="table-content">
	    待合成数据集：&nbsp;
		<a href="<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id=${dataSet.id}" target="_blank">${dataSet.name}</a>
		&nbsp;
		[<a href="<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id=${dataSet.id}" target="_blank">${dataSet.title}</a>]   
	</td>
  </tr>
  <tr>
	<c:forEach items="${syncColumns}" var="item">
	<td height="30" class="table-content">
	    <select id="${dataSet.id}_${item}" name="${dataSet.id}_${item}" >
			<option value="">----请选择----</option>    
			<c:forEach items="${columns2}" var="item2">
			<%
			  ColumnDefinition column = (ColumnDefinition) pageContext.getAttribute("item2");
			  String columnLabel = column.getColumnLabel();
			  //columnLabel = columnLabel.toLowerCase();
			%>
			<option value="<%=columnLabel%>"><%=column.getColumnLabel()%>&nbsp;${item2.title}</option>     
			</c:forEach>
		</select>
		<script type="text/javascript">
		   <%
		     String col = (String)pageContext.getAttribute("item");
			 String name = dataSet.getId() + "_"+col+"_col";
		   %>
		   document.getElementById("${dataSet.id}_${item}").value="<%=request.getAttribute(name)%>";
		</script>
	</td>
	</c:forEach>
  </tr>
  </c:forEach>

  <tr>
    <td colspan="<%=columns.size()+1%>" align="center" valign="bottom" height="25" class="table-content">&nbsp;
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