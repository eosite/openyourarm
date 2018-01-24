<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.repository.parser.*"%>
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
<title>编辑信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
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
        "name": "${parserDefinition.name}",
        "title": "${parserDefinition.title}",
        "databaseId": "${parserDefinition.databaseId}",
        "userId": "${parserDefinition.userId}",
        "type": "${parserDefinition.type}",
        "providerClass": "${parserDefinition.providerClass}",
        "locked": "${parserDefinition.locked}",
        "id": "${parserDefinition.id}"
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

     function openTables(){
            var selected = jQuery("#tables").val();
            var link = '<%=request.getContextPath()%>/mx/repository/parser/chooseTables?elementId=tables&elementName=tables_name&id=${parserDefinition.id}&selected='+selected;
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
	     //form.method="post";
	     //form.action = "<%=request.getContextPath()%>/mx/repository/parser/saveParserDefinition";
	     //form.submit();
		 // jQuery("#tables").val(jQuery("#tables2").val());
	     var link = "<%=request.getContextPath()%>/mx/repository/parser/saveParserDefinition";
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
	     //form.action = "<%=request.getContextPath()%>/mx/repository/parser/saveParserDefinition";
	     //form.submit();
		 // jQuery("#tables").val(jQuery("#tables2").val());
		 jQuery("#id").val("");
	     var link = "<%=request.getContextPath()%>/mx/repository/parser/saveParserDefinition";
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

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑数据处理信息">&nbsp;
编辑数据处理信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${parserDefinition.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style=" width:320px;"
	           value="${parserDefinition.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style=" width:320px;"
	           value="${parserDefinition.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>

  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="type" class="required">类别&nbsp;</label>
        <input id="type" name="type" type="text" class="k-textbox"  
	           data-bind="value: type" style=" width:320px;"
	           value="${parserDefinition.type}" validationMessage="请输入类别"/>
	    <span class="k-invalid-msg" data-for="type"></span>
    </td>
  </tr> -->

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="type" class="required">类型&nbsp;</label>
	  <select id="type" name="type">
	    <option value="">----请选择----</option>
	    <option value="json">JSON</option>
		<option value="xml">XML</option>
		<option value="access">MS Access</option>
		<option value="sqlite">SQLite</option>
	  </select>&nbsp;
      <script type="text/javascript">
	       document.getElementById("type").value="${parserDefinition.type}";
	  </script>    
	  <span class="k-invalid-msg" data-for="type"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="databaseId" class="required">数据库&nbsp;</label>
        <select id="databaseId" name="databaseId" >
			<option value="">----请选择----</option>    
			<c:forEach items="${databases}" var="item">
			<option value="${item.id}">${item.title}</option>     
			</c:forEach>
		</select>
	    <span class="k-invalid-msg" data-for="databaseId"></span>
	    <script type="text/javascript">
	       document.getElementById("databaseId").value="${parserDefinition.databaseId}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="userId" class="required">用户&nbsp;</label>
        <select id="userId" name="userId" >
			<option value="">----请选择----</option>    
			<c:forEach items="${users}" var="item">
			<option value="${item.actorId}">${item.name}</option>     
			</c:forEach>
		</select>
		<script type="text/javascript">
	       document.getElementById("userId").value="${parserDefinition.userId}";
	    </script>
	    <span class="k-invalid-msg" data-for="userId"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left" valign="middle">
	    <label for="tables2" class="required">表名&nbsp;</label>
		<input type="hidden" id="tables" name="tables" value="${parserDefinition.tables}" >
        <textarea id="tables_name" name="tables_name" rows="6" cols="38" style="height:220px; width:320px;"  class="k-textbox">${selecteds_name}</textarea> 
		&nbsp; <a href="#" onclick="javascript:openTables();"> <img
			      src="<%=request.getContextPath()%>/images/search_results.gif" ></a>
		<br><span style="margin-left:100px;">&nbsp;&nbsp;（只有选中的表才能导入数据。）</span>
	    <span class="k-invalid-msg" data-for="tables2"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="providerClass" class="required">实现提供者&nbsp;</label>
        <select id="providerClass" name="providerClass" nullable="no" chname="任务类名">
			<%
			  ParserProperties.reload();
			  Properties props =  ParserProperties.getProperties();
			  Enumeration<?> e = props.keys();
			  while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = props.getProperty(key);
			%>
				<option value="<%=key%>"><%=value%></option>
			<%}%>
			</select>
			<script type="text/javascript">
			    document.getElementById("providerClass").value="${parserDefinition.providerClass}";
			</script>
	        <span class="k-invalid-msg" data-for="providerClass"></span>
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
	       document.getElementById("locked").value="${parserDefinition.locked}";
	  </script>
    </td>
  </tr>
 
  <tr>
    <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
     <div>
      <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>&nbsp;
	  <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:saveAs();">另存</button>
	 </div>
   </td>
  </tr>
</table>   
</form>
</div>     
</body>
</html>