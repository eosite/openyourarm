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
        //"name": "${dataSetSynthetic.name}",
        "title": "${dataSetSynthetic.title}",
        "type": "${dataSetSynthetic.type}",
        "sourceDatabaseIds": "${dataSetSynthetic.sourceDatabaseIds}",
        "sourceDataSetId": "${dataSetSynthetic.sourceDataSetId}",
		"datasetName": "${datasetName}",
	    "aggregationKeys": "${dataSetSynthetic.aggregationKeys}",
        "syncColumns": "${dataSetSynthetic.syncColumns}",
        "targetTableName": "${dataSetSynthetic.targetTableName}",
        "targetDatabaseId": "${dataSetSynthetic.targetDatabaseId}",
        "scheduleFlag": "${dataSetSynthetic.scheduleFlag}",
        "createTableFlag": "${dataSetSynthetic.createTableFlag}",
        "deleteFetch": "${dataSetSynthetic.deleteFetch}",
        "insertOnly": "${dataSetSynthetic.insertOnly}",
        "locked": "${dataSetSynthetic.locked}",
        "id": "${dataSetSynthetic.id}"
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
	      var link = "<%=request.getContextPath()%>/mx/sys/dataSetSynthetic/saveDataSetSynthetic";
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
	      var link = "<%=request.getContextPath()%>/mx/sys/dataSetSynthetic/saveDataSetSynthetic";
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

    function openDataset(){
        var selected = jQuery("#sourceDataSetId").val();
        var link = '<%=request.getContextPath()%>/mx/dataset/showTree?elementId=sourceDataSetId&elementName=datasetName&nodeCode=report_category&selected='+selected;
		var x=100;
		var y=50;
		if(is_ie) {
			x=document.body.scrollLeft+event.clientX-event.offsetX-200;
			y=document.body.scrollTop+event.clientY-event.offsetY-200;
		}
		openWindow(link,self,x, y, 595, 480);
	}


	function openDx(){
        var selected = jQuery("#sourceDatabaseIds").val();
        var link = '<%=request.getContextPath()%>/mx/sys/dataSetSynthetic/chooseDatabases?elementId=sourceDatabaseIds&elementName=selectedDB&selected='+selected+"&dataSetSyntheticId=${dataSetSynthetic.id}";
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

    function openQx(){
        var selected = jQuery("#syncColumns").val();
		var sourceDatabaseIds = jQuery("#sourceDatabaseIds").val();
		var sourceDataSetId = jQuery("#sourceDataSetId").val();
        var link = '<%=request.getContextPath()%>/mx/sys/dataSetSynthetic/chooseColumns?elementId=syncColumns&elementName=syncColumns&selected='+
			selected+"&tableSyncId=${dataSetSynthetic.id}&sourceDatabaseIds="+sourceDatabaseIds+"&sourceDataSetId="+sourceDataSetId;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 945, 480);
    }

	function openQx2(){
        var selected = jQuery("#aggregationKeys").val();
		var sourceDatabaseIds = jQuery("#sourceDatabaseIds").val();
		var sourceDataSetId = jQuery("#sourceDataSetId").val();
        var link = '<%=request.getContextPath()%>/mx/sys/dataSetSynthetic/chooseColumns?elementId=aggregationKeys&elementName=aggregationKeys&selected='+
			selected+"&tableSyncId=${dataSetSynthetic.id}&iType=AGGR&sourceDatabaseIds="+sourceDatabaseIds+"&sourceDataSetId="+sourceDataSetId;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 945, 480);
    }

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑合成表信息">&nbsp;
    编辑合成表信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${dataSetSynthetic.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style="width:380px"
	           value="${dataSetSynthetic.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr> -->

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style="width:380px"
	           value="${dataSetSynthetic.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
		<label for="sourceDataSetId" class="required">来源数据集&nbsp;</label>
		<input type="hidden" id="sourceDataSetId" name="sourceDataSetId" value="${dataSetSynthetic.sourceDataSetId}">
		<input id="datasetName" name="datasetName" type="text" class="k-textbox"  
		       data-bind="value: datasetName" style="width:380px" onclick="javascript:openDataset();" 
		       value="${datasetName}" validationMessage="请输入来源数据集"/>
		&nbsp;
		<a href="#" onclick="javascript:openDataset();">
		<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
			 title="合成表数据由数据集组成，请先建好数据集数据再选择。">
		</a>
		<span class="k-invalid-msg" data-for="datasetName"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="selectedDB" class="required">来源数据库&nbsp;</label>
		 <input type="hidden" id="sourceDatabaseIds" name="sourceDatabaseIds" value="${dataSetSynthetic.sourceDatabaseIds}">
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
	    <label for="aggregationKeys" class="required">聚合列&nbsp;</label>
        <textarea id="aggregationKeys" name="aggregationKeys" rows="12" cols="68" class="k-textbox"
				  style="width:380px; height:60px;" onclick="javascript:openQx2();" readonly="true"  
				  >${dataSetSynthetic.aggregationKeys}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openQx2();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="aggregationKeys"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="syncColumns" class="required">同步列&nbsp;</label>
        <textarea id="syncColumns" name="syncColumns" rows="12" cols="68" class="k-textbox"
				  style="width:380px;height:150px;" onclick="javascript:openQx();"  
				  readonly="true" >${dataSetSynthetic.syncColumns}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="syncColumns"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="targetTableName" class="required">目标表&nbsp;</label>
        <input id="targetTableName" name="targetTableName" type="text" class="k-textbox"  
	           data-bind="value: targetTableName" style="width:380px"
	           value="${dataSetSynthetic.targetTableName}" validationMessage="请输入目标表"/>
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
		<label for="targetDatabaseId" class="required">目标数据库&nbsp;</label>
		<select id="targetDatabaseId" name="targetDatabaseId" >
			<option value="">----请选择----</option>    
			<c:forEach items="${databases}" var="item">
			<option value="${item.id}">${item.title}</option>     
			</c:forEach>
		</select>
	    <span class="k-invalid-msg" data-for="targetDatabaseId"></span>
	    <script type="text/javascript">
	       document.getElementById("targetDatabaseId").value="${dataSetSynthetic.targetDatabaseId}";
	    </script>
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
	       document.getElementById("createTableFlag").value="${dataSetSynthetic.createTableFlag}";
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
	  </select>&nbsp;（提示：同步前清空目标表数据，只有临时表tmp_或etl_开头的表才生效）
	  <script type="text/javascript">
	       document.getElementById("deleteFetch").value="${dataSetSynthetic.deleteFetch}";
	  </script>
	  <span class="k-invalid-msg" data-for="deleteFetch"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left" height="25">&nbsp;</td>
    <td align="left">
	    <label for="insertOnly" class="required">修改标识&nbsp;</label>
        <select id="insertOnly" name="insertOnly">
			<option value="">----请选择----</option>
			<option value="Y">不可以修改</option>
			<option value="N">可以修改</option>
	  </select>&nbsp; 
	  <script type="text/javascript">
	       document.getElementById("insertOnly").value="${dataSetSynthetic.insertOnly}";
	  </script>
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
			    document.getElementById("sortNo").value="${dataSetSynthetic.sortNo}";
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
	       document.getElementById("scheduleFlag").value="${dataSetSynthetic.scheduleFlag}";
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
	       document.getElementById("locked").value="${dataSetSynthetic.locked}";
	  </script>
    </td>
  </tr>
 
  <tr>
    <td colspan="2" align="center" valign="bottom" height="25">&nbsp;
    <div>
      <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	  &nbsp;
	  <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:saveAs();">另存</button>
	</div>
	</td>
  </tr>
</table>   
</form>
</div>
</body>
</html>