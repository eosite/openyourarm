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
<title>编辑数据表信息</title>
<%@ include file="/WEB-INF/views/inc/init_mini_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/plugin/MultiSelectBox.js"></script>
 
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
  var contextPath="<%=request.getContextPath()%>";
  
  jQuery(function() {
    var viewModel = kendo.observable({
        <c:forEach items="${sysDataTable.fields}" var="field">
		    <c:if test="${not empty field.name and not empty field.value }">
			  <c:choose>
               <c:when test="${ field.dataType == 'Date'}">
			   "${field.name}": "<fmt:formatDate value="${field.value}" pattern="yyyy-MM-dd"/>",
			   </c:when>
			   <c:otherwise>
			   "${field.name}": "${field.value}",
		       </c:otherwise>
		     </c:choose>
			</c:if>
        </c:forEach>
		"__tmp__": ""
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

   function saveAjax(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	   //form.method="post";
	   //form.action = "<%=request.getContextPath()%>/rs/table/service/saveData";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/rs/table/service/saveData/${sysDataTable.id}";
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
					   window.close();
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑${sysDataTable.title}信息">&nbsp;
编辑${sysDataTable.title}信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="datatableId" name="datatableId" value="${sysDataTable.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    
<c:forEach items="${sysDataTable.fields}" var="field">
  <c:choose>
   <c:when  test="${ !empty field.columnName and field.primaryKey == 'Y'}">
    <input type="hidden" id="${field.name}" name="${field.name}" value="${field.value}">
   </c:when>
   <c:otherwise>
	<c:if test="${!empty field.name and !empty field.columnName and field.editable == 'Y'}">
	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left" width="15%">
	    <label for="${field.name}" <c:if test="${field.required =='Y'}">class="required"</c:if>>${field.title}&nbsp;</label>
		<c:choose>
		  <c:when test="${field.dataType =='Integer' || field.dataType =='Long'}">
		    <c:choose>
            <c:when test="${ field.inputType == 'DropDownList'}">
			<input id="${field.name}" name="${field.name}" type="text"   
	           data-bind="value: ${field.name}" 
			   style="width:340px;" class="k-textbox"
			   <c:if test="${field.required =='Y'}">required="true"</c:if>
	           value="${field.value}" validationMessage="请选择${field.title}"/>
			  <script type="text/javascript">
			    $(document).ready(function() {
                    $("#${field.name}").kendoDropDownList({
                        dataTextField: "${field.dataItem.textField}",
                        dataValueField: "${field.dataItem.valueField}",
                        dataSource: {
                            transport: {
                                read: {
                                    dataType: "json",
									contentType: "application/json",
                                    url: "<%=request.getContextPath()%>/rs/dataitem/jsonArray?id=${field.dataItemId}",
                                }
                            }
                        }
                    });
					var dropdownlist = jQuery("#${field.name}").data("kendoDropDownList");
                    dropdownlist.value("${field.value}");
                });
			  </script>
			</c:when>
			<c:otherwise>
             <input id="${field.name}" name="${field.name}" type="text"   
	           data-bind="value: ${field.name}" data-role="numerictextbox" data-format="i"
			   style="width:150px;" class="k-textbox"
			   <c:if test="${field.required =='Y'}">required="true"</c:if>
	           value="${field.value}" validationMessage="请输入${field.title}"/>
			</c:otherwise>
			</c:choose>
		  </c:when>
		  <c:when test="${field.dataType =='Double'}">
              <input id="${field.name}" name="${field.name}" type="text"   
	           data-bind="value: ${field.name}" data-role="numerictextbox" data-format="d"
			   style="width:150px;" class="k-textbox"
			   <c:if test="${field.required =='Y'}">required="true"</c:if>
	           value="${field.value}" validationMessage="请输入${field.title}"/>
		  </c:when>
		  <c:when test="${field.dataType =='Date'}">
             <!-- <input id="${field.name}" name="${field.name}" type="text"   
	           data-bind="value: ${field.name}" data-role='datepicker' data-type="date"
			   style="width:340px;" class="k-textbox"
			   <c:if test="${field.required =='Y'}">required="true"</c:if>
	           value="<fmt:formatDate value="${field.value}" pattern="yyyy-MM-dd"/>"
			   validationMessage="请输入${field.title}"/> -->
			   <kendo:datePicker name="${field.name}" value="${field.value}" 
			          validationMessage="请输入${field.title}"  
			          format="yyyy-MM-dd" culture="zh-CN">
               </kendo:datePicker>

		  </c:when>
		  <c:otherwise>
		  <c:choose>
            <c:when test="${ field.inputType == 'TextArea'}">
			<textarea id="${field.name}" name="${field.name}" 
			data-bind="value: ${field.name}" rows="6" cols="46" 
			validationMessage="请输入${field.title}"
			<c:if test="${field.required =='Y'}">required="true"</c:if>
			class="k-textbox" style="height:120px; width:340px;">${field.value}</textarea>  
			</c:when>
			<c:when test="${ field.inputType == 'DropDownList'}">
			  <input id="${field.name}" name="${field.name}" type="text"   
	           data-bind="value: ${field.name}" 
			   style="width:340px;" class="k-textbox"
			   <c:if test="${field.required =='Y'}">required="true"</c:if>
	           value="${field.value}" validationMessage="请选择${field.title}"/>
			  <script type="text/javascript">
			    $(document).ready(function() {
                    $("#${field.name}").kendoDropDownList({
                        dataTextField: "${field.dataItem.textField}",
                        dataValueField: "${field.dataItem.valueField}",
                        dataSource: {
                            transport: {
                                read: {
                                    dataType: "json",
									contentType: "application/json",
                                    url: "<%=request.getContextPath()%>/rs/dataitem/jsonArray?id=${field.dataItemId}",
                                }
                            }
                        }
                    });
					var dropdownlist = jQuery("#${field.name}").data("kendoDropDownList");
                    dropdownlist.value("${field.value}");
                });
			  </script>
			</c:when>
			<c:when test="${ field.inputType == 'Radio'}">
			    
            </c:when>
			<c:when test="${ field.inputType == 'MultiSelect'}">
			  <span id="${field.name}View">
			    <input id="${field.name}" name="${field.name}"  
				       type="text" style="width:340px;" class="k-textbox"
					   validationMessage="请输入${field.title}">
			  </span>
			  <script type="text/javascript">

				  jQuery(function() {

					var data =  ${field.dataItem.json};

					function onChange${field.name}(){
						var dropdownlist = jQuery("#${field.name}").data("kendoMultiSelectBox")
                        jQuery("#${field.name}").val(dropdownlist.value());
					}

					jQuery("#${field.name}").kendoMultiSelectBox({
								dataTextField: "${field.dataItem.textField}",
								dataValueField: "${field.dataItem.valueField}",
								dataSource: data,
								change: onChange${field.name}
					});

					var dropdownlist = jQuery("#${field.name}").data("kendoMultiSelectBox");
                    dropdownlist.value("${field.value}");
				}); 
			     	
			  </script>
			</c:when>
		    <c:otherwise>
            <input id="${field.name}" name="${field.name}" type="text"   
	           data-bind="value: ${field.name}" 
			   style="width:340px;" class="k-textbox"
			   <c:if test="${field.required =='Y'}">required="true"</c:if>
	           value="${field.value}" validationMessage="请输入${field.title}"/>
		    </c:otherwise>
		   </c:choose>
		  </c:otherwise>
		</c:choose>
	    <span class="k-invalid-msg" data-for="${field.name}"></span>
	</td>
    </tr>
  </c:if>
 </c:otherwise>
</c:choose>
</c:forEach>

	<tr>
      <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
		<div >
		<button type="button" id="iconButton"  class="k-primary" style="width: 90px" onclick="javascript:saveAjax();">保存</button>
		</div>
	  </td>
    </tr>
</table>   
</form>
</div>  
<br/>
<br/>
<script type="text/javascript">
    
</script> 
</body>
</html>