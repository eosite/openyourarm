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
<title>编辑同步信息</title>
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
        "name": "${tableSync.name}",
        "title": "${tableSync.title}",
        "type": "${tableSync.type}",
        "sourceDatabaseId": "${tableSync.sourceDatabaseId}",
        "sourceTableName": "${tableSync.sourceTableName}",
        "primaryKey": "${tableSync.primaryKey}",
        "syncColumns": "${tableSync.syncColumns}",
        "targetTableName": "${tableSync.targetTableName}",
        "targetDatabaseIds": "${tableSync.targetDatabaseIds}",
        "scheduleFlag": "${tableSync.scheduleFlag}",
        "createTableFlag": "${tableSync.createTableFlag}",
        "deleteFetch": "${tableSync.deleteFetch}",
        "insertOnly": "${tableSync.insertOnly}",
        "locked": "${tableSync.locked}",
        "id": "${tableSync.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/sys/tableSync/saveTableSync";
	   //form.submit();
	    var link = "<%=request.getContextPath()%>/mx/sys/tableSync/saveTableSync";
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
		document.getElementById("id").value="";
	    var link = "<%=request.getContextPath()%>/mx/sys/tableSync/saveTableSync";
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

    function openQx(){
        var selected = jQuery("#syncColumns").val();
		var sourceDatabaseId = jQuery("#sourceDatabaseId").val();
		var sourceTableName = jQuery("#sourceTableName").val();
        var link = '<%=request.getContextPath()%>/mx/sys/tableSync/chooseColumns?elementId=syncColumns&elementName=syncColumns&selected='+
			selected+"&tableSyncId=${tableSync.id}&databaseId="+sourceDatabaseId+"&tableName="+sourceTableName;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

	function openDx(){
        var selected = jQuery("#targetDatabaseIds").val();
        var link = '<%=request.getContextPath()%>/mx/sys/tableSync/chooseDatabases?elementId=targetDatabaseIds&elementName=selectedDB&selected='+selected+"&tableSyncId=${tableSync.id}";
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

	function openSrcExec(){
        var selected = jQuery("#sourceTableExecutionIds").val();
        var link = '<%=request.getContextPath()%>/mx/sys/tableExecution/choose?elementId=sourceTableExecutionIds&elementName=selected1Name&selected='+selected;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
	}


	function openTargetExec(){
        var selected = jQuery("#targetTableExecutionIds").val();
        var link = '<%=request.getContextPath()%>/mx/sys/tableExecution/choose?elementId=targetTableExecutionIds&elementName=selected2Name&selected='+selected;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
	}

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑表同步信息">&nbsp;
    编辑表同步信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${tableSync.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style="width:380px"
	           value="${tableSync.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style="width:380px"
	           value="${tableSync.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
 
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
		<label for="sourceDatabaseId" class="required">来源数据库&nbsp;</label>
		<select id="sourceDatabaseId" name="sourceDatabaseId" >
			<option value="">----请选择----</option>    
			<c:forEach items="${databases}" var="item">
			<option value="${item.id}">${item.title}</option>     
			</c:forEach>
		</select>
	    <span class="k-invalid-msg" data-for="sourceDatabaseId"></span>
	    <script type="text/javascript">
	       document.getElementById("sourceDatabaseId").value="${tableSync.sourceDatabaseId}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
		<label for="sourceTableName" class="required">来源表名&nbsp;</label>
		<input id="sourceTableName" name="sourceTableName" type="text" class="k-textbox"  
		       data-bind="value: sourceTableName" style="width:380px"
		       value="${tableSync.sourceTableName}" validationMessage="请输入来源表名"/>
		<span class="k-invalid-msg" data-for="sourceTableName"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="selected1Name" class="required">源库后置处理&nbsp;</label>
		<input type="hidden" id="sourceTableExecutionIds" name="sourceTableExecutionIds" 
		       value="${tableSync.sourceTableExecutionIds}">
        <textarea id="selected1Name" name="selected1Name" rows="5" cols="68" class="k-textbox"
				  style="width:380px;height:60px;" onclick="javascript:openSrcExec();"  
				  readonly="true" >${selected1Name}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openSrcExec();">
			<img src="<%=request.getContextPath()%>/images/lightning.png" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="selected1Name"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="primaryKey" class="required">主键列&nbsp;</label>
        <input id="primaryKey" name="primaryKey" type="text" class="k-textbox"  
	           data-bind="value: primaryKey" style="width:380px"
	           value="${tableSync.primaryKey}" validationMessage="请输入主键列"/>
	    <span class="k-invalid-msg" data-for="primaryKey"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="syncColumns" class="required">同步列&nbsp;</label>
        <textarea id="syncColumns" name="syncColumns" rows="12" cols="68" class="k-textbox"
				  style="width:380px;height:150px;" onclick="javascript:openQx();"  
				  readonly="true" >${tableSync.syncColumns}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="syncColumns"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="sqlCriteria" class="required">转换SQL条件&nbsp;</label>
        <textarea id="sqlCriteria" name="sqlCriteria" rows="12" cols="68" class="k-textbox"
				  style="width:380px;height:80px;" >${tableSync.sqlCriteria}</textarea>&nbsp;
	    <span class="k-invalid-msg" data-for="sqlCriteria"></span>
		&nbsp;（提示：添加and条件即可, 例如 and ctime >= '2017-01-01' ）
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="targetTableName" class="required">目标表名&nbsp;</label>
        <input id="targetTableName" name="targetTableName" type="text" class="k-textbox"  
	           data-bind="value: targetTableName" style="width:380px"
	           value="${tableSync.targetTableName}" validationMessage="请输入目标表名"/>
	    <span class="k-invalid-msg" data-for="targetTableName"></span>
		<div style="margin-top:5px;">
			<span style="color:red; margin-left:110px;">
			（提示：为了保证系统安全，目标表只能以useradd_、etl_、sync_、tmp_开头。）
			</span>
	   </div>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="selectedDB" class="required">目标数据库&nbsp;</label>
		 <input type="hidden" id="targetDatabaseIds" name="targetDatabaseIds" value="${tableSync.targetDatabaseIds}">
         <textarea id="selectedDB" name="selectedDB" rows="12" cols="68" class="k-textbox"
				  style="width:380px;height:80px;" onclick="javascript:openDx();"  
				  readonly="true" >${selectedDB}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openDx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a>  
	    <span class="k-invalid-msg" data-for="selectedDB"></span>
    </td>
  </tr>
 
   <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="selected1Name" class="required">目标库后置处理&nbsp;</label>
		<input type="hidden" id="targetTableExecutionIds" name="targetTableExecutionIds" 
		       value="${tableSync.targetTableExecutionIds}">
        <textarea id="selected2Name" name="selected2Name" rows="5" cols="68" class="k-textbox"
				  style="width:380px;height:60px;" onclick="javascript:openTargetExec();"  
				  readonly="true" >${selected2Name}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openTargetExec();">
			<img src="<%=request.getContextPath()%>/images/lightning.png" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="selected2Name"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left" height="25">&nbsp;</td>
    <td align="left">
	    <label for="createTableFlag" class="required">是否需要建表&nbsp;</label>
        <select id="createTableFlag" name="createTableFlag">
			<option value="">----请选择----</option>
			<option value="N">否</option>
			<option value="Y">是</option>
	  </select>&nbsp;（提示：表不存在时自动创建）
	  <script type="text/javascript">
	       document.getElementById("createTableFlag").value="${tableSync.createTableFlag}";
	  </script>
	  <span class="k-invalid-msg" data-for="createTableFlag"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left" height="25">&nbsp;</td>
    <td align="left">
	    <label for="deleteFetch" class="required">同步前删除&nbsp;</label>
        <select id="deleteFetch" name="deleteFetch">
			<option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
	  </select>&nbsp;（提示：同步前清空目标表数据，只有临时表tmp_或sync_开头的表才生效）
	  <script type="text/javascript">
	       document.getElementById("deleteFetch").value="${tableSync.deleteFetch}";
	  </script>
	  <span class="k-invalid-msg" data-for="deleteFetch"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left" height="25">&nbsp;</td>
    <td align="left">
	    <label for="insertOnly" class="required">增量标识&nbsp;</label>
        <select id="insertOnly" name="insertOnly">
			<option value="">----请选择----</option>
			<option value="Y">只做新增，不做修改</option>
			<option value="N">可以新增及修改</option>
	  </select>&nbsp; 
	  <script type="text/javascript">
	       document.getElementById("insertOnly").value="${tableSync.insertOnly}";
	  </script>
	  &nbsp;(如果选择增量，可以保证不对原始数据造成破坏，只是新增数据)
	  <span class="k-invalid-msg" data-for="insertOnly"></span>
    </td>
  </tr>

  <tr>
		<td width="2%" height="25"></td>
		<td class="x-content" colspan="3" height="28">
		    <label for="sortNo" class="required">执行次序&nbsp;</label>
		    <select id="sortNo" name="sortNo" class="span2" style="height:20px">
			    <option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
		    </select>&nbsp;（提示：数值小的先执行）
			<script type="text/javascript">
			    document.getElementById("sortNo").value="${tableSync.sortNo}";
			</script>
		</td>
	</tr>

  <tr>
    <td width="2%" align="left" height="25">&nbsp;</td>
    <td align="left">
	  <label for="scheduleFlag" class="required">是否实时调度&nbsp;</label>
	  <select id="scheduleFlag" name="scheduleFlag">
	    <option value="N">否</option>
		<option value="Y">是</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("scheduleFlag").value="${tableSync.scheduleFlag}";
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
	       document.getElementById("locked").value="${tableSync.locked}";
	  </script>
    </td>
  </tr>
 
  <tr>
    <td colspan="2" align="center" valign="bottom" height="25">&nbsp;
    <div>
      <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	  <button type="button" id="iconButton"  class="k-button" style="width: 90px" onclick="javascript:saveAs();">另存</button>
	</div>
	</td>
  </tr>
</table>   
</form>
</div>     
 
</body>
</html>