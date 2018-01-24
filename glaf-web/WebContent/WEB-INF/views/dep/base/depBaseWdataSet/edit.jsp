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
    String code =  com.glaf.core.config.CustomProperties.getString("dataSet.code");
    code = RequestUtils.getString(request, "code", code == null ? "report_category" : code);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑更新数据集信息</title>
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
  var _typeId = '${typeId}';
      
  jQuery(function() {
    var viewModel = kendo.observable({
        "dataSetName": "${depBaseWdataSet.dataSetName}",
        "dataSetDesc": "${depBaseWdataSet.dataSetDesc}",
        "id": "${depBaseWdataSet.id}",
        'nodeId' : '${depBaseWdataSet.nodeId}'
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
        jQuery.ajax({
            type: "POST",
            url: '<%=request.getContextPath()%>/rs/tree/treeJson?nodeCode=<%=code%>',
            dataType:'json',
            error: function(datas){
                      alert('服务器处理错误！');
            },
            success: function(datas){	
           	         $.each(datas,function(i,data){
           	        	if(data.id == $("#dataSetNodeId").val() || data.id == _typeId) {
            						var option = '<option value='+data.id+' selected="selected">'+data.name+'</option>';          					
            					}
            					else{
            						var option = "<option value="+data.id+">"+data.name+"</option>";        					
            					} 
           	        	$("#nodeId").append(option); 
           	         });      	         
      				
             }
        });
    });

   function save(o){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	   var link = "<%=request.getContextPath()%>/mx/dep/base/depBaseWdataSet/saveDepBaseWdataSet";

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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑更新数据集信息">&nbsp;
编辑更新数据集信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${depBaseWdataSet.id}"/>
<input type="hidden" id="saveAs" name="saveAs"/>
<%-- <input type="hidden" id="nodeId" name="nodeId" value="${param.nodeId}"/> --%>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <%-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="dataSetCode" class="required">数据集代码&nbsp;</label>
        <input id="dataSetCode" name="dataSetCode" type="text" class="k-textbox"  
	   data-bind="value: dataSetCode"
	   value="${depBaseWdataSet.dataSetCode}" validationMessage="请输入数据集代码"/>
	<span class="k-invalid-msg" data-for="dataSetCode"></span>
    </td>
  </tr> --%>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="dataSetName" class="required">更新集名称&nbsp;</label>
        <input id="dataSetName" name="dataSetName" type="text" class="k-textbox"  
	   data-bind="value: dataSetName"
	   value="${depBaseWdataSet.dataSetName}" validationMessage="请输入数据集名称"/>
	<span class="k-invalid-msg" data-for="dataSetName"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="dataSetDesc" class="required">更新集描述&nbsp;</label>
        <input id="dataSetDesc" name="dataSetDesc" type="text" class="k-textbox"  
	   data-bind="value: dataSetDesc"
	   value="${depBaseWdataSet.dataSetDesc}" validationMessage="请输入数据集描述"/>
	<span class="k-invalid-msg" data-for="dataSetDesc"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="nodeId" class="required">分类&nbsp;</label>
	   <select id="nodeId" name="nodeId" type="text" class="k-textbox"  style="height:28px;" 
	           validationMessage="类型" data-bind="value: nodeId"   >
	           <option selected="selected">分类选择</option>
	    </select>
	       <input type="hidden" id="dataSetNodeId" value="${depBaseWdataSet.nodeId}" />  
	<span class="k-invalid-msg" data-for="dataSetDesc"></span>
    </td>
  </tr>
  <%-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="ruleJson" class="required">数据集规则JSON&nbsp;</label>
        <input id="ruleJson" name="ruleJson" type="text" class="k-textbox"  
	   data-bind="value: ruleJson"
	   value="${depBaseWdataSet.ruleJson}" validationMessage="请输入数据集规则JSON"/>
	<span class="k-invalid-msg" data-for="ruleJson"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="tableName" class="required">数据表名&nbsp;</label>
        <input id="tableName" name="tableName" type="text" class="k-textbox"  
	   data-bind="value: tableName"
	   value="${depBaseWdataSet.tableName}" validationMessage="请输入数据表名"/>
	<span class="k-invalid-msg" data-for="tableName"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="dataTableName" class="required">数据表物理表名&nbsp;</label>
        <input id="dataTableName" name="dataTableName" type="text" class="k-textbox"  
	   data-bind="value: dataTableName"
	   value="${depBaseWdataSet.dataTableName}" validationMessage="请输入数据表物理表名"/>
	<span class="k-invalid-msg" data-for="dataTableName"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="wtype" class="required">新增/更新&nbsp;</label>
        <input id="wtype" name="wtype" type="text" class="k-textbox"  
	   data-bind="value: wtype"
	   value="${depBaseWdataSet.wtype}" validationMessage="请输入新增/更新"/>
	<span class="k-invalid-msg" data-for="wtype"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="ver" class="required">版本号&nbsp;</label>
	<input id="ver" name="ver" type="text" class="k-textbox" 
	   data-bind="value: ver"
	       value="${depBaseWdataSet.ver}" validationMessage="请输入版本号"/>
	<span class="k-invalid-msg" data-for="ver"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="creator" class="required">创建人&nbsp;</label>
        <input id="creator" name="creator" type="text" class="k-textbox"  
	   data-bind="value: creator"
	   value="${depBaseWdataSet.creator}" validationMessage="请输入创建人"/>
	<span class="k-invalid-msg" data-for="creator"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="createDatetime" class="required">创建时间&nbsp;</label>
	<input id="createDatetime" name="createDatetime" type="text" class="k-textbox" 
	       data-role='datepicker' data-type="date" data-bind="value: createDatetime"
	   
	       value="<fmt:formatDate value="${depBaseWdataSet.createDatetime}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入创建时间"/>
	<span class="k-invalid-msg" data-for="createDatetime"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="modifier" class="required">修改人&nbsp;</label>
        <input id="modifier" name="modifier" type="text" class="k-textbox"  
	   data-bind="value: modifier"
	   value="${depBaseWdataSet.modifier}" validationMessage="请输入修改人"/>
	<span class="k-invalid-msg" data-for="modifier"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="modifyDatetime" class="required">修改时间&nbsp;</label>
	<input id="modifyDatetime" name="modifyDatetime" type="text" class="k-textbox" 
	       data-role='datepicker' data-type="date" data-bind="value: modifyDatetime"
	   
	       value="<fmt:formatDate value="${depBaseWdataSet.modifyDatetime}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入修改时间"/>
	<span class="k-invalid-msg" data-for="modifyDatetime"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="delFlag" class="required">删除标识&nbsp;</label>
        <input id="delFlag" name="delFlag" type="text" class="k-textbox"  
	   data-bind="value: delFlag"
	   value="${depBaseWdataSet.delFlag}" validationMessage="请输入删除标识"/>
	<span class="k-invalid-msg" data-for="delFlag"></span>
    </td>
  </tr> --%>
	   
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
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
          
          <c:if test="${id != null }">
	          <button type="button"  class="k-button k-primary" style="width: 90px" onclick="javascript:save(saveAs.value=-1);">另存为</button>
          </c:if>
          
	</div>
	</td>
      </tr>
</table>   
</form>
</div>     
<script>
    jQuery(document).ready(function() {
	    jQuery("#ver").kendoNumericTextBox();			 
	     //jQuery("#createDatetime").kendoDateTimePicker();
	     //jQuery("#modifyDatetime").kendoDateTimePicker();
    });
</script>
</body>
</html>