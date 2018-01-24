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
<title>执行SQL语句</title>
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
                

  function executeSQL(){
	   if(document.getElementById("databaseId").value==""){
		   alert("请选择要执行SQL的数据库");
		   document.getElementById("databaseId").focus();
		   return;
	   }
       if (confirm("更新操作执行后无法恢复，确定要在该数据库上执行SQL操作吗？")) {
	      var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/executeUpdate";
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
				   }
			 });
       }
  }

  function executeAllSQL(){
       if (confirm("更新操作执行后无法恢复，确定要在全部数据库上执行SQL操作吗？")) {
	      var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/executeAllUpdate";
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
				   }
			 });
       }
  }
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title">
<img src="<%=request.getContextPath()%>/images/window.png" alt="执行SQL语句">&nbsp;执行SQL语句
</div>
<br>
<form id="iForm" name="iForm" method="post" >
<input type="hidden" id="sqlDefId" name="sqlDefId" value="${sqlDefinition.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           style="width:360px;" readonly
	           value="${sqlDefinition.name}"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           style="width:360px;" readonly
	           value="${sqlDefinition.title}" />
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="sql" class="required">SQL语句&nbsp;</label>
	    <textarea id="sql" name="sql" rows="6" cols="46"   class="k-textbox" style="height:240px; width:360px;" readonly>${sqlDefinition.sql}</textarea> 
	    <span class="k-invalid-msg" data-for="sql"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="databaseId" class="required">数据库&nbsp;</label>
        <select id="databaseId" name="databaseId">
            <option value="">----请选择----</option>    
		    <c:forEach items="${databases}" var="item">
			<option value="${item.id}">${item.title}</option>     
		    </c:forEach>
        </select>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left" valign="bottom" height="30">&nbsp;
     <div style="margin-left:150px;">
      <button type="button" class="k-button k-primary" style="width: 90px" 
	          onclick="javascript:executeSQL();">执行</button>
	  &nbsp;&nbsp;
	  <!-- <button type="button" class="k-button k-primary" style="width: 90px" 
	          onclick="javascript:executeAllSQL();">执行全部</button> -->
	 </div>
	</td>
  </tr>
</table>   
</form>
<br/>
<br/>
</div>     
</body>
</html>