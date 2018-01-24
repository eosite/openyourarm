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
<title>编辑字段信息</title>
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

    .requiredField {
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
        "name": "${column.name}",
        "title": "${column.title}",
        "javaType": "${column.javaType}",
        "length": "${column.length}",
        "width": "${column.width}",
        "primaryKeyField": "${column.primaryKeyField}",
        "inputType": "${column.inputType}",
        "displayType": "${column.displayType}",
        "importType": "${column.importType}",
        "formatter": "${column.formatter}",
        "searchableField": "${column.searchableField}",
        "editable": "${column.editable}",
        "updatableField": "${column.updatableField}",
        "formula": "${column.formula}",
        "mask": "${column.mask}",
        "validType": "${column.validType}",
        "requiredField": "${column.requiredField}",
        "initValue": "${column.initValue}",
        "defaultValue": "${column.defaultValue}",
		"maxValue": "${column.maxValue}",
		"minValue": "${column.minValue}",
		"stepValue": "${column.stepValue}",
		"placeholder": "${column.placeholder}",
        "valueExpression": "${column.valueExpression}",
        "sortableField": "${column.sortableField}",
        "ordinal": "${column.ordinal}" 
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
		    var link = "<%=request.getContextPath()%>/rs/dts/table/saveColumn";
		    var params = jQuery("#iForm").formSerialize();
			jQuery.ajax({
					   type: "POST",
					   url: link,
					   javaType:  'json',
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑字段信息">&nbsp;
编辑字段信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${column.id}"/>
<c:if test="${!empty column.tableName}">
<input type="hidden" id="tableName" name="tableName" value="${column.tableName}"/>
</c:if>
<input type="hidden" id="tableName_enc" name="tableName_enc" value="${tableName_enc}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	   <label for="columnName" class="requiredField">列名&nbsp;</label>
	   <c:choose>
	     <c:when test="${!empty column}">
	         ${column.columnName}
	     <input type="hidden" name="columnName" value="${column.columnName}">
	    </c:when>
	    <c:otherwise>
	     <input id="columnName" name="columnName" type="text" class="k-textbox"  
	           data-bind="value: columnName" 
	           value="${column.columnName}" validationMessage="请输入列名"/>
	     <span class="k-invalid-msg" data-for="columnName"></span>
        </c:otherwise>
	   </c:choose>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	 <c:choose>
	   <c:when test="${!empty column}">
	    <label for="javaType" class="requiredField">类型&nbsp;</label>
        <select id="javaType" name="javaType" readonly="true">
		    <option value="">----请选择----</option>
			<option value="Integer">整数型</option>
			<option value="Long">长整数型</option>
			<option value="Double">数值型</option>
			<option value="Date">日期型</option>
			<option value="String">字符串型</option>
        </select>
	    <span class="k-invalid-msg" data-for="javaType"></span>
	   </c:when>
	   <c:otherwise>      
	    <label for="javaType" class="requiredField">类型&nbsp;</label>
        <select id="javaType" name="javaType">
		    <option value="">----请选择----</option>
			<option value="Integer">整数型</option>
			<option value="Long">长整数型</option>
			<option value="Double">数值型</option>
			<option value="Date">日期型</option>
			<option value="String">字符串型</option>
        </select>
	    <span class="k-invalid-msg" data-for="javaType"></span>
	   </c:otherwise>
	   </c:choose>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="name" class="requiredField">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name"
	           value="${column.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="title" class="requiredField">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title"
	           value="${column.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="length" class="requiredField">长度&nbsp;</label>
	  <input id="length" name="length" type="text" class="k-textbox" 
	       data-bind="value: length" data-role="numerictextbox"
           data-format="i" data-min="1" data-max="4000"
		   min="1" max="4000" step="1"
	       value="${column.length}" validationMessage="请输入长度"/>
	   <span class="k-invalid-msg" data-for="length"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="width" class="requiredField">列表宽度&nbsp;</label>
	  <input id="width" name="width" type="text" class="k-textbox" 
	       data-bind="value: width" data-role="numerictextbox"
           data-format="i" data-min="30" data-max="600"
		   min="30" max="600" step="30"
	       value="${column.width}" validationMessage="请输入列表宽度"/>
	  <span class="k-invalid-msg" data-for="width"></span>
	</td>
    </tr>

    <tr>
	  <td width="2%" align="left">&nbsp;</td>
	  <td align="left">
	    <label for="primaryKeyField" >主键&nbsp;</label>
        <select id="primaryKeyField" name="primaryKeyField">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="primaryKeyField"></span>
	  </td>
	  <td width="2%" align="left">&nbsp;</td>
	  <td align="left">
	    <label for="displayType" >显示类型&nbsp;</label>
	    <select id="displayType" name="displayType">
		    <option value="">----请选择----</option>
			<option value="0">不显示</option>
			<option value="1">表单</option>
			<option value="2">列表</option>
			<option value="4">表单及列表</option>
        </select>
	    <span class="k-invalid-msg" data-for="displayType"></span>
	  </td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="inputType" >输入类型&nbsp;</label>
		<select id="inputType" name="inputType">
		    <option value="">----请选择----</option>
		    <option value="NumericTextBox">数值输入框</option>
			<option value="MaskedTextBox">掩码输入框</option>
			<option value="TextField">单行文本框</option>
			<option value="TextArea">多行文本框</option>
			<option value="Editor">富文本编辑器</option>
			<option value="Calendar">日历组件</option>
			<option value="DatePicker">日期选择</option>
			<option value="DateTimePicker">日期时间选择</option>
			<option value="TimePicker">时间选择</option>
			<option value="ComboBox">组合框</option>
			<option value="DropDownList">单项下拉选择</option>
			<option value="MultiSelect">多项下拉选择</option>
			<option value="Radio">单选框</option>
			<option value="CheckBox">复选框</option>
			<option value="Grid">数据网格</option>
			<option value="ListView">列表视图</option>
			<option value="TreeView">树型视图</option>
			<option value="TreeList">树型网格视图</option>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("inputType").value="${column.inputType}";
		   </script>
		   <span class="k-invalid-msg" data-for="inputType"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="dataCode" >数据项&nbsp;</label>
	    <select id="dataCode" name="dataCode">
		    <option value="">----请选择----</option>
			<c:forEach items="${dataItems}" var="item">
			<option value="${item.code}">${item.title}</option>
			</c:forEach>
        </select>
	    <span class="k-invalid-msg" data-for="dataCode"></span>
	</td>
    </tr>
     
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="searchableField" >是否可查找&nbsp;</label>
        <select id="searchableField" name="searchableField">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="searchableField"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="sortableField" >是否可排序&nbsp;</label>
        <select id="sortableField" name="sortableField">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="sortableField"></span>
	</td>
	</tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="updatableField" >是否可更新&nbsp;</label>
        <select id="updatableField" name="updatableField">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="updatableField"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td>
	    <label for="editable" >是否可编辑&nbsp;</label>
        <select id="editable" name="editable">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="editable"></span>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="requiredField" >必填&nbsp;</label>
        <select id="requiredField" name="requiredField">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="requiredField"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="defaultValue" >排序号&nbsp;</label>
        <select id="ordinal" name="ordinal">
		    <option value="">----请选择----</option>
			<c:forEach begin="0" end="99" step="1" var="index">
			<option value="${index}">${index}</option>   
			</c:forEach>
        </select>
	    &nbsp;（提示：由大到小排列，序号大的排在前面）
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="initValue" >初始化值&nbsp;</label>
        <input id="initValue" name="initValue" type="text" class="k-textbox"  
	           data-bind="value: initValue"
	           value="${column.initValue}" validationMessage="请输入初始化值"/>
	    <span class="k-invalid-msg" data-for="initValue"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="defaultValue" >默认值&nbsp;</label>
        <input id="defaultValue" name="defaultValue" type="text" class="k-textbox"  
	           data-bind="value: defaultValue"
	           value="${column.defaultValue}" validationMessage="请输入默认值"/>
	    <span class="k-invalid-msg" data-for="defaultValue"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="placeholder" >输入提示&nbsp;</label>
        <input id="placeholder" name="placeholder" type="text" class="k-textbox"  
	           data-bind="value: placeholder"
	           value="${column.placeholder}" validationMessage="请输入提示语"/>
	    <span class="k-invalid-msg" data-for="placeholder"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="formatter" >格式&nbsp;</label>
      <input id="formatter" name="formatter" type="text" class="k-textbox"  
	   data-bind="value: formatter"
	   value="${column.formatter}" validationMessage="请输入格式"/>
	   <span class="k-invalid-msg" data-for="formatter"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="formula" >公式&nbsp;</label>
        <input id="formula" name="formula" type="text" class="k-textbox"  
	           data-bind="value: formula"
	           value="${column.formula}" validationMessage="请输入公式"/>
	    <span class="k-invalid-msg" data-for="formula"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="mask" >掩码&nbsp;</label>
        <input id="mask" name="mask" type="text" class="k-textbox"  
	           data-bind="value: mask"
	           value="${column.mask}" validationMessage="请输入掩码"/>
	    <span class="k-invalid-msg" data-for="mask"></span>
	</td>
    </tr>
   
     
  <!-- <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
	  <input type="radio" name="status" id="engine1" class="k-radio" value="0" >
	  <label class="k-radio-label" for="engine1">有效</label>&nbsp;&nbsp;
	  <input type="radio" name="status" id="engine2" class="k-radio" value="1" >
	  <label class="k-radio-label" for="engine2">无效</label>
	</td>
    </tr> -->
 
    <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton" class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	</div>
	</td>
   </tr>
</table>   
</form>
</div>     
<br/>
<br/>
<script type="text/javascript">
    jQuery(function() {
		    document.getElementById("primaryKey").value="${column.primaryKey}";
			document.getElementById("javaType").value="${column.javaType}";
			document.getElementById("displayType").value="${column.displayType}";
			document.getElementById("searchableField").value="${column.searchableField}";
			document.getElementById("editable").value="${column.editable}";
			document.getElementById("updatableField").value="${column.updatableField}";
			document.getElementById("requiredField").value="${column.requiredField}";
			document.getElementById("sortableField").value="${column.sortableField}";
			document.getElementById("inputType").value="${column.inputType}";
			document.getElementById("ordinal").value="${column.ordinal}";
			document.getElementById("dataCode").value="${column.dataCode}";
		});
</script>
</body>
</html>