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
        "name": "${sysDataField.name}",
        "title": "${sysDataField.title}",
        "dataType": "${sysDataField.dataType}",
        "length": "${sysDataField.length}",
        "listWeigth": "${sysDataField.listWeigth}",
        "primaryKey": "${sysDataField.primaryKey}",
        "inputType": "${sysDataField.inputType}",
        "displayType": "${sysDataField.displayType}",
        "importType": "${sysDataField.importType}",
        "formatter": "${sysDataField.formatter}",
        "searchable": "${sysDataField.searchable}",
        "editable": "${sysDataField.editable}",
        "updatable": "${sysDataField.updatable}",
        "formula": "${sysDataField.formula}",
        "mask": "${sysDataField.mask}",
        "validType": "${sysDataField.validType}",
        "required": "${sysDataField.required}",
        "initValue": "${sysDataField.initValue}",
        "defaultValue": "${sysDataField.defaultValue}",
		"maxValue": "${sysDataField.maxValue}",
		"minValue": "${sysDataField.minValue}",
		"stepValue": "${sysDataField.stepValue}",
		"placeholder": "${sysDataField.placeholder}",
        "valueExpression": "${sysDataField.valueExpression}",
        "sortable": "${sysDataField.sortable}",
        "ordinal": "${sysDataField.ordinal}",
        "dataItemId": "${sysDataField.dataItemId}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/system/datafield/saveSysDataField";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/system/datafield/saveSysDataField";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑字段信息">&nbsp;
编辑字段信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${sysDataField.id}"/>
<c:if test="${!empty sysDataTable}">
<input type="hidden" id="tablename" name="tablename" value="${sysDataTable.tablename}"/>
</c:if>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	   <label for="tablename" class="required">列名&nbsp;</label>
	   <c:choose>
	     <c:when test="${!empty sysDataField}">
	         ${sysDataField.columnName}
	     <input type="hidden" name="columnName" value="${sysDataField.columnName}">
	    </c:when>
	    <c:otherwise>
	     <input id="columnName" name="columnName" type="text" class="k-textbox"  
	           data-bind="value: columnName" 
	           value="${sysDataField.columnName}" validationMessage="请输入表名"/>
	     <span class="k-invalid-msg" data-for="columnName"></span>
        </c:otherwise>
	   </c:choose>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	 <c:choose>
	   <c:when test="${!empty sysDataField}">
	    <label for="dataType" class="required">类型&nbsp;</label>
        <select id="dataType" name="dataType" readonly="true">
		    <option value="">----请选择----</option>
			<option value="Integer">整数型</option>
			<option value="Long">长整数型</option>
			<option value="Double">数值型</option>
			<option value="Date">日期型</option>
			<option value="String">字符串型</option>
			<option value="Text">长文本型</option>
        </select>
	    <span class="k-invalid-msg" data-for="dataType"></span>
	   </c:when>
	   <c:otherwise>      
	    <label for="dataType" class="required">类型&nbsp;</label>
        <select id="dataType" name="dataType">
		    <option value="">----请选择----</option>
			<option value="Integer">整数型</option>
			<option value="Long">长整数型</option>
			<option value="Double">数值型</option>
			<option value="Date">日期型</option>
			<option value="String">字符串型</option>
			<option value="Text">长文本型</option>
        </select>
	    <span class="k-invalid-msg" data-for="dataType"></span>
	   </c:otherwise>
	   </c:choose>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name"
	           value="${sysDataField.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title"
	           value="${sysDataField.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="length" class="required">长度&nbsp;</label>
	  <input id="length" name="length" type="text" class="k-textbox" 
	       data-bind="value: length" data-role="numerictextbox"
           data-format="i" data-min="1" data-max="4000" size="4" maxlength="4"
		   min="1" max="4000" step="1"
	       value="${sysDataField.length}" validationMessage="请输入长度"/>
	   <span class="k-invalid-msg" data-for="length"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="listWeigth" class="required">列表宽度&nbsp;</label>
	  <input id="listWeigth" name="listWeigth" type="text" class="k-textbox" 
	       data-bind="value: listWeigth" data-role="numerictextbox"
           data-format="i" data-min="30" data-max="600"
		   min="30" max="600" step="30" size="4" maxlength="4"
	       value="${sysDataField.listWeigth}" validationMessage="请输入列表宽度"/>
	  <span class="k-invalid-msg" data-for="listWeigth"></span>
	</td>
    </tr>

    <tr>
	  <td width="2%" align="left">&nbsp;</td>
	  <td align="left">
	    <label for="primaryKey" >主键&nbsp;</label>
        <select id="primaryKey" name="primaryKey">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="primaryKey"></span>
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
		        document.getElementById("inputType").value="${sysDataField.inputType}";
		   </script>
		   <span class="k-invalid-msg" data-for="inputType"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="dataItemId" >数据项&nbsp;</label>
	    <select id="dataItemId" name="dataItemId">
		    <option value="">----请选择----</option>
			<c:forEach items="${dataItems}" var="item">
			<option value="${item.id}">${item.title}</option>
			</c:forEach>
        </select>
	    <span class="k-invalid-msg" data-for="dataItemId"></span>
	</td>
    </tr>
     
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="searchable" >是否可查找&nbsp;</label>
        <select id="searchable" name="searchable">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="searchable"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="sortable" >是否可排序&nbsp;</label>
        <select id="sortable" name="sortable">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="sortable"></span>
	</td>
	</tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="updatable" >是否可更新&nbsp;</label>
        <select id="updatable" name="updatable">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="updatable"></span>
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
	    <label for="required" >必填&nbsp;</label>
        <select id="required" name="required">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select>
	    <span class="k-invalid-msg" data-for="required"></span>
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
	           value="${sysDataField.initValue}" validationMessage="请输入初始化值"/>
	    <span class="k-invalid-msg" data-for="initValue"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="defaultValue" >默认值&nbsp;</label>
        <input id="defaultValue" name="defaultValue" type="text" class="k-textbox"  
	           data-bind="value: defaultValue"
	           value="${sysDataField.defaultValue}" validationMessage="请输入默认值"/>
	    <span class="k-invalid-msg" data-for="defaultValue"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="maxValue" >最大值&nbsp;</label>
        <input id="maxValue" name="maxValue" type="text" class="k-textbox"  
	           data-bind="value: maxValue"
	           value="${sysDataField.maxValue}" validationMessage="请输入最大值"/>
	    <span class="k-invalid-msg" data-for="maxValue"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="minValue" >最小值&nbsp;</label>
        <input id="minValue" name="minValue" type="text" class="k-textbox"  
	           data-bind="value: minValue"
	           value="${sysDataField.minValue}" validationMessage="请输入最小值"/>
	    <span class="k-invalid-msg" data-for="minValue"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="stepValue" >步长值&nbsp;</label>
        <input id="stepValue" name="stepValue" type="text" class="k-textbox"  
	           data-bind="value: stepValue"
	           value="${sysDataField.stepValue}" validationMessage="请输入步长值"/>
	    <span class="k-invalid-msg" data-for="stepValue"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="valueExpression" >值表达式&nbsp;</label>
        <input id="valueExpression" name="valueExpression" type="text" class="k-textbox"  
	           data-bind="value: valueExpression"
	           value="${sysDataField.valueExpression}" validationMessage="请输入值表达式"/>
	    <span class="k-invalid-msg" data-for="valueExpression"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="placeholder" >输入提示&nbsp;</label>
        <input id="placeholder" name="placeholder" type="text" class="k-textbox"  
	           data-bind="value: placeholder"
	           value="${sysDataField.placeholder}" validationMessage="请输入提示语"/>
	    <span class="k-invalid-msg" data-for="placeholder"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	  <label for="formatter" >格式&nbsp;</label>
      <input id="formatter" name="formatter" type="text" class="k-textbox"  
	   data-bind="value: formatter"
	   value="${sysDataField.formatter}" validationMessage="请输入格式"/>
	   <span class="k-invalid-msg" data-for="formatter"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="formula" >公式&nbsp;</label>
        <input id="formula" name="formula" type="text" class="k-textbox"  
	           data-bind="value: formula"
	           value="${sysDataField.formula}" validationMessage="请输入公式"/>
	    <span class="k-invalid-msg" data-for="formula"></span>
	</td>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="mask" >掩码&nbsp;</label>
        <input id="mask" name="mask" type="text" class="k-textbox"  
	           data-bind="value: mask"
	           value="${sysDataField.mask}" validationMessage="请输入掩码"/>
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
		    document.getElementById("primaryKey").value="${sysDataField.primaryKey}";
			document.getElementById("dataType").value="${sysDataField.dataType}";
			document.getElementById("displayType").value="${sysDataField.displayType}";
			document.getElementById("searchable").value="${sysDataField.searchable}";
			document.getElementById("editable").value="${sysDataField.editable}";
			document.getElementById("updatable").value="${sysDataField.updatable}";
			document.getElementById("required").value="${sysDataField.required}";
			document.getElementById("sortable").value="${sysDataField.sortable}";
			document.getElementById("inputType").value="${sysDataField.inputType}";
			document.getElementById("ordinal").value="${sysDataField.ordinal}";
			document.getElementById("dataItemId").value="${sysDataField.dataItemId}";
		});
</script>
</body>
</html>