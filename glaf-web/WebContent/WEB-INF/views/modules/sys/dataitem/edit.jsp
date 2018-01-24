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
<title>编辑数据项信息</title>
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
        "name": "${sysDataItem.name}",
        "title": "${sysDataItem.title}",
        "queryId": "${sysDataItem.queryId}",
        "querySQL": "${sysDataItem.querySQL}",
        "parameter": "${sysDataItem.parameter}",
        "textField": "${sysDataItem.textField}",
        "valueField": "${sysDataItem.valueField}",
        "treeIdField": "${sysDataItem.treeIdField}",
        "treeParentIdField": "${sysDataItem.treeParentIdField}",
        "treeTreeIdField": "${sysDataItem.treeTreeIdField}",
        "treeNameField": "${sysDataItem.treeNameField}",
        "treeListNoField": "${sysDataItem.treeListNoField}",
        "url": "${sysDataItem.url}",
        "createBy": "${sysDataItem.createBy}",
        "createTime": "<fmt:formatDate value='${sysDataItem.createTime}' pattern='yyyy-MM-dd'/>",
        "updateBy": "${sysDataItem.updateBy}",
        "updateTime": "<fmt:formatDate value='${sysDataItem.updateTime}' pattern='yyyy-MM-dd'/>",
		"locked": "${sysDataItem.locked}",
		"cacheFlag": "${sysDataItem.cacheFlag}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/system/dataitem/saveSysDataItem";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/system/dataitem/saveSysDataItem";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑数据项信息">&nbsp;
编辑数据项信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${sysDataItem.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style=" width:340px;"
	           value="${sysDataItem.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style=" width:340px;"
	           value="${sysDataItem.title}" validationMessage="请输入标题"/>
	<span class="k-invalid-msg" data-for="title"></span>
	</td>
    </tr>
    <!-- <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="queryId" class="required">查询语句编号&nbsp;</label>
        <input id="queryId" name="queryId" type="text" class="k-textbox"  
	           data-bind="value: queryId" style=" width:340px;"
	           value="${sysDataItem.queryId}" validationMessage="请输入查询语句编号"/>
	    <span class="k-invalid-msg" data-for="queryId"></span>
	</td>
    </tr> -->
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="querySQL" class="required">查询语句&nbsp;</label>
		<textarea id="querySQL" name="querySQL" data-bind="value: querySQL" rows="6" cols="46" class="k-textbox" style="height:90px; width:340px;" validationMessage="请输入查询语句">${sysDataItem.querySQL}</textarea>  
	    <span class="k-invalid-msg" data-for="querySQL"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="parameter" class="required">查询参数&nbsp;</label>
		<textarea id="parameter" name="parameter" data-bind="value: parameter" rows="6" cols="46" class="k-textbox" style="height:90px; width:340px;" validationMessage="请输入查询参数">${sysDataItem.parameter}</textarea>  
	    <span class="k-invalid-msg" data-for="parameter"></span>
	</td>
    </tr>
	<c:if test="${not empty sysDataItem and ( sysDataItem.querySQL != null or sysDataItem.queryId != null ) }">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="textField" class="required">文本字段&nbsp;</label>
		<select id="textField" name="textField">
		    <option value="">----请选择----</option>
			<c:forEach items="${cloumns}" var="c">
            <option value="${c.name}">${c.name}</option>
			</c:forEach>
        </select> 
	    <span class="k-invalid-msg" data-for="textField"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="valueField" class="required">值字段&nbsp;</label>
        <select id="valueField" name="valueField">
		    <option value="">----请选择----</option>
			<c:forEach items="${cloumns}" var="c">
            <option value="${c.name}">${c.name}</option>
			</c:forEach> 
        </select> 
	    <span class="k-invalid-msg" data-for="valueField"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="treeIdField" class="required">树节点编号&nbsp;</label>
        <select id="treeIdField" name="treeIdField">
		    <option value="">----请选择----</option>
			<c:forEach items="${cloumns}" var="c">
            <option value="${c.name}">${c.name}</option>
			</c:forEach>  
        </select> 
	    <span class="k-invalid-msg" data-for="treeIdField"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="treeParentIdField" class="required">树父节点编号&nbsp;</label>
        <select id="treeParentIdField" name="treeParentIdField">
		    <option value="">----请选择----</option>
			<c:forEach items="${cloumns}" var="c">
            <option value="${c.name}">${c.name}</option>
			</c:forEach>  
        </select> 
	    <span class="k-invalid-msg" data-for="treeParentIdField"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="treeTreeIdField" class="required">树结构编号&nbsp;</label>
        <select id="treeTreeIdField" name="treeTreeIdField">
		    <option value="">----请选择----</option>
			<c:forEach items="${cloumns}" var="c">
            <option value="${c.name}">${c.name}</option>
			</c:forEach>  
        </select> 
	    <span class="k-invalid-msg" data-for="treeTreeIdField"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="treeNameField" class="required">树节点名称&nbsp;</label>
        <select id="treeNameField" name="treeNameField">
		    <option value="">----请选择----</option>
			<c:forEach items="${cloumns}" var="c">
            <option value="${c.name}">${c.name}</option>
			</c:forEach>  
        </select> 
	    <span class="k-invalid-msg" data-for="treeNameField"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="treeListNoField" class="required">树节点排序&nbsp;</label>
        <select id="treeListNoField" name="treeListNoField">
		    <option value="">----请选择----</option>
			<c:forEach items="${cloumns}" var="c">
            <option value="${c.name}">${c.name}</option>
			</c:forEach>  
        </select> 
	    <span class="k-invalid-msg" data-for="treeListNoField"></span>
	</td>
    </tr>
	</c:if>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="url" class="required">链接&nbsp;</label>
        <input id="url" name="url" type="text" class="k-textbox"  
	           data-bind="value: url" style=" width:340px;"
	           value="${sysDataItem.url}" validationMessage="请输入Url"/>
	    <span class="k-invalid-msg" data-for="url"></span>
	</td>
    </tr>

	<tr>
        <td class="input-box2" valign="top"></td>
        <td>
		  <label for="cacheFlag" >缓存数据&nbsp;</label>
          <select id="cacheFlag" name="cacheFlag">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
         </select> 
	     <span class="k-invalid-msg" data-for="cacheFlag"></span>
		</td>
    </tr> 
	   
    <tr>
        <td class="input-box2" valign="top"></td>
        <td>
		  <label for="locked" >是否有效&nbsp;</label>
          <select id="locked" name="locked">
		    <option value="">----请选择----</option>
			<option value="0">是</option>
			<option value="1">否</option>
         </select> 
	     <span class="k-invalid-msg" data-for="locked"></span>
		</td>
    </tr> 
    <c:choose>
     <c:when test="${ not empty sysDataItem and sysDataItem.type == 'SYS'}">
	    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
         <div>
           系统内置功能，不提供修改功能！
	     </div>
	    </td>
     </tr>  
	 </c:when>
	 <c:otherwise>
     <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
         <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	     </div>
	    </td>
     </tr>
	</c:otherwise>
	</c:choose>
</table>   
</form>
</div> 
<br><br>
<script>
    jQuery(document).ready(function() {
		 document.getElementById("textField").value="${sysDataItem.textField}";
		 document.getElementById("valueField").value="${sysDataItem.valueField}";
		 document.getElementById("treeIdField").value="${sysDataItem.treeIdField}";
		 document.getElementById("treeParentIdField").value="${sysDataItem.treeParentIdField}";
		 document.getElementById("treeTreeIdField").value="${sysDataItem.treeTreeIdField}";
		 document.getElementById("treeNameField").value="${sysDataItem.treeNameField}";
		 document.getElementById("treeListNoField").value="${sysDataItem.treeListNoField}";
		 document.getElementById("cacheFlag").value="${sysDataItem.cacheFlag}";
		 document.getElementById("locked").value="${sysDataItem.locked}";
    });
</script>
</body>
</html>