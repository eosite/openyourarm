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
<title>编辑数据导出信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
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
        "name": "${dataExport.name}",
        "title": "${dataExport.title}",
        "type": "${dataExport.type}",
		"serviceKey": "${dataExport.serviceKey}",
        "userId": "${dataExport.userId}",
        "operation": "${dataExport.operation}",
        "exportFlag": "${dataExport.exportFlag}",
        "exportName": "${dataExport.exportName}",
        "tables": "${dataExport.tables}",
        "scheduleFlag": "${dataExport.scheduleFlag}",
        "deleteFetch": "${dataExport.deleteFetch}",
        "publicFlag": "${dataExport.publicFlag}",
        "locked": "${dataExport.locked}"
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
			//form.action = "<%=request.getContextPath()%>/mx/datamgr/data/export/saveDataExport";
			//form.submit();
			var link = "<%=request.getContextPath()%>/mx/datamgr/data/export/saveDataExport";
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
        var form = document.getElementById("iForm");
        var validator = jQuery("#iForm").data("kendoValidator");
        if (validator.validate()) {
			//form.method="post";
			//form.action = "<%=request.getContextPath()%>/mx/datamgr/data/export/saveDataExport";
			//form.submit();
			jQuery("#id").val("");
			var link = "<%=request.getContextPath()%>/mx/datamgr/data/export/saveDataExport";
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

    function chooseTables(){
		var link="<%=request.getContextPath()%>/mx/datamgr/data/export/choose?id=${dataExport.id}&elementId=tables&elementName=tableNames";
		var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 895, 480);
	}
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑数据导出信息">&nbsp;
编辑数据导出信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${dataExport.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="name" class="required">名称&nbsp;</label>
      <input id="name" name="name" type="text" class="k-textbox"  
	         data-bind="value: name" style=" width:380px;"
	         value="${dataExport.name}" validationMessage="请输入名称"/>
	  <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style=" width:380px;"
	           value="${dataExport.title}" validationMessage="请输入标题"/>
	<span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
  
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="tables" class="required">数据表&nbsp;</label>
	  <input type="hidden" id="tables" name="tables" value="${dataExport.tables}">
	  <textarea id="tableNames" name="tableNames" rows="6" cols="46" 
	            style="height:120px; width:380px;" class="k-textbox"  
	            onclick="javascript:chooseTables();">${tableNames}</textarea>
		        <a href="#" onclick="javascript:chooseTables();">
                  <img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
                       title="请选择数据数据表。">
                </a>
	  <span class="k-invalid-msg" data-for="tableNames"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="exportFlag" class="required">是否导出&nbsp;</label>
      <select id="exportFlag" name="exportFlag">
	    <option value="">----请选择----</option>
	    <option value="N">否</option>
		<option value="Y">是</option>
	  </select>&nbsp;
	 <span class="k-invalid-msg" data-for="exportFlag"></span>
	 <script type="text/javascript">
	       document.getElementById("exportFlag").value="${dataExport.exportFlag}";
	  </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="serviceKey" class="required">服务标识&nbsp;</label>
        <input id="serviceKey" name="serviceKey" type="text" class="k-textbox"  
	           data-bind="value: serviceKey"
	           value="${dataExport.serviceKey}" validationMessage="请输入服务标识"/>
	    <span class="k-invalid-msg" data-for="serviceKey"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="userId" class="required">用户名&nbsp;</label>
        <input id="userId" name="userId" type="text" class="k-textbox"  
	           data-bind="value: userId"
	           value="${dataExport.userId}" validationMessage="请输入交换用户"/>
		&nbsp;(发送到交换中心的用户名)
	    <span class="k-invalid-msg" data-for="userId"></span>
    </td>
  </tr>

   <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="type" class="required">导出类型&nbsp;</label>
	  <select id="type" name="type">
	    <option value="">----请选择----</option>
	    <option value="json">JSON</option>
		<option value="xml">XML</option>
		<option value="access">MS Access</option>
		<option value="sqlite">SQLite</option>
	  </select>&nbsp;
      <script type="text/javascript">
	       document.getElementById("type").value="${dataExport.type}";
	  </script>    
	  <span class="k-invalid-msg" data-for="type"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="exportName" class="required">导出库名&nbsp;</label>
      <input id="exportName" name="exportName" type="text" class="k-textbox"  
	         data-bind="value: exportName"
	         value="${dataExport.exportName}" validationMessage="请输入导出库名"/>
	  <span class="k-invalid-msg" data-for="exportName"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="createTableFlag" class="required">是否导出建表语句&nbsp;</label>
      <select id="createTableFlag" name="createTableFlag">
	    <option value="">----请选择----</option>
	    <option value="N">否</option>
		<option value="Y">是</option>
	  </select>&nbsp;
	  <script type="text/javascript">
	       document.getElementById("createTableFlag").value="${dataExport.createTableFlag}";
	  </script>
	 <span class="k-invalid-msg" data-for="createTableFlag"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="databaseId" class="required">默认库&nbsp;</label>
        <select id="databaseId" name="databaseId" >
			<option value="">----请选择----</option>    
			<c:forEach items="${databases}" var="item">
			<option value="${item.id}">${item.title}</option>     
			</c:forEach>
		</select>
	    <span class="k-invalid-msg" data-for="databaseId"></span>
	    <script type="text/javascript">
	       document.getElementById("databaseId").value="${dataExport.databaseId}";
	    </script>
    </td>
  </tr>
   
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	   <label for="scheduleFlag" class="required">是否调度&nbsp;</label>
       <select id="scheduleFlag" name="scheduleFlag">
	    <option value="">----请选择----</option>
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>&nbsp;（是否由后台自动定时执行）
	  <script type="text/javascript">
	       document.getElementById("scheduleFlag").value="${dataExport.scheduleFlag}";
	  </script>
	  <span class="k-invalid-msg" data-for="scheduleFlag"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="deleteFetch" class="required">每次抓取前删除&nbsp;</label>
       <select id="deleteFetch" name="deleteFetch">
	    <option value="">----请选择----</option>
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>&nbsp;
	  <script type="text/javascript">
	       document.getElementById("deleteFetch").value="${dataExport.deleteFetch}";
	  </script>
	<span class="k-invalid-msg" data-for="deleteFetch"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="publicFlag" class="required">是否公开&nbsp;</label>
      <select id="publicFlag" name="publicFlag">
	    <option value="">----请选择----</option>
	    <option value="N">否</option>
		<option value="Y">是</option>
	  </select>&nbsp;（未登录用户能否访问结果集）
	  <script type="text/javascript">
	       document.getElementById("publicFlag").value="${dataExport.publicFlag}";
	  </script>
	<span class="k-invalid-msg" data-for="publicFlag"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="locked" class="required">是否有效&nbsp;</label>
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${dataExport.locked}";
	  </script>
    </td>
  </tr>

    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
          <div>
            <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:save();">保存</button>
				  &nbsp;
		    <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:saveAs();">另存</button>
	      </div>
	</td>
   </tr>
</table>   
</form>
</div>
</body>
</html>