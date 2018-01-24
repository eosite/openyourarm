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
<title>编辑表字段转换信息</title>
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
        "title": "${tableTransform.title}",
        "tableName": "${tableTransform.tableName}",
        "primaryKey": "${tableTransform.primaryKey}",
        "sort": "${tableTransform.sort}",
        "locked": "${tableTransform.locked}"
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


	function openDB(){
        var selected = jQuery("#databaseIds").val();
        var link = '<%=request.getContextPath()%>/mx/sys/database/chooseDatabases?elementId=databaseIds&elementName=selectedDB&selectedDatabaseIds='+selected;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

    function openQx(){
        var selected = jQuery("#transformColumns").val();
		var databaseIds = jQuery("#databaseIds").val();
		var tableName = jQuery("#tableName").val();
        var link = '<%=request.getContextPath()%>/mx/sys/tableTransform/chooseColumns?elementId=transformColumns&elementName=transformColumns&selected='+
			selected+"&databaseIds="+databaseIds+"&tableName="+tableName;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	     var link = "<%=request.getContextPath()%>/mx/sys/tableTransform/saveTableTransform";
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

   function saveAs(){
       var form = document.getElementById("iForm");
	   document.getElementById("tableName").value="";
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	     var link = "<%=request.getContextPath()%>/mx/sys/tableTransform/saveTableTransform";
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
            	  // window.parent.location.reload();
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑表转换信息">&nbsp;
编辑表转换信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="tableName" class="required">表名&nbsp;</label>
		<c:choose>
		<c:when test="${!empty tableTransform.tableName}">
		${tableTransform.tableName}
		<input type="hidden" id="tableName" name="tableName" value="${tableTransform.tableName}">
		</c:when>
		<c:otherwise>
        <input id="tableName" name="tableName" type="text" class="k-textbox"  
               data-bind="value: tableName" maxlength="50" style="width:380px;"
               value="" validationMessage="请输入表名"/>
	    <span class="k-invalid-msg" data-for="tableName"></span>
	    </c:otherwise>
	    </c:choose>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
               data-bind="value: title" maxlength="200" style="width:380px;"
               value="${tableTransform.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="selectedDB" class="required">转换数据库&nbsp;</label>
		<input type="hidden" id="databaseIds" name="databaseIds" value="${tableTransform.databaseIds}">
        <textarea id="selectedDB" name="selectedDB" rows="12" cols="68" class="k-textbox"
				  style="width:380px;height:80px;" onclick="javascript:openDB();"  
				  readonly="true" >${selectedDB}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openDB();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a>  
	    <span class="k-invalid-msg" data-for="selectedDB"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="primaryKey" class="required">主键列&nbsp;</label>
        <input id="primaryKey" name="primaryKey" type="text" class="k-textbox"  
               data-bind="value: primaryKey" maxlength="50" style="width:380px;"
               value="${tableTransform.primaryKey}" validationMessage="请输入主键列"/>
	    <span class="k-invalid-msg" data-for="primaryKey"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="transformColumns" class="required">参与转换列&nbsp;</label>
        <textarea id="transformColumns" name="transformColumns" rows="12" cols="68" class="k-textbox"
				  style="width:380px;height:150px;" readonly="true">${tableTransform.transformColumns}</textarea>
		&nbsp;
	    <a href="#" onclick="javascript:openQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="transformColumns"></span>
		&nbsp;（提示：默认全部列参与转换）
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="sqlCriteria" class="required">转换SQL条件&nbsp;</label>
        <textarea id="sqlCriteria" name="sqlCriteria" rows="12" cols="68" class="k-textbox"
				  style="width:380px;height:80px;" >${tableTransform.sqlCriteria}</textarea>&nbsp;
	    <span class="k-invalid-msg" data-for="sqlCriteria"></span>
		 <div style="margin-top:5px;">
			<span style="  margin-left:110px;">
			 （提示：添加and条件即可，动态参数也是支持的 column1 = <%="#"%>{param1}）。
			</span>
	     </div>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
        <label for="sort" class="required">顺序&nbsp;</label>
        <select id="sort" name="sort" class="span2" style="height:20px">
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
            document.getElementById("sort").value="${tableTransform.sort}";
		</script>
        <span class="k-invalid-msg" data-for="sort"></span>
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
            document.getElementById("locked").value="${tableTransform.locked}";
	    </script>
    </td>
  </tr>

  <tr>
    <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
     <div>
       <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	   <button type="button" id="iconButton"  class="k-button" style="width: 90px" onclick="javascript:saveAs();">另存</button>
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