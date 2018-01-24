<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>编辑使用规则分类树信息</title>
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

.accept,.status {
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
        "code": "${depBaseCategory.code}",
        "name": "${depBaseCategory.name}",
        "treeId": "${depBaseCategory.treeId}",
        "expandFlag": "${depBaseCategory.expandFlag}",
        "pid": "${depBaseCategory.pid}",
        "orderNo": "${depBaseCategory.orderNo}",
        "creator": "${depBaseCategory.creator}",
        "createDateTime": "<fmt:formatDate value='${depBaseCategory.createDateTime}' pattern='MM/dd/yyyy'/>",
        "modifier": "${depBaseCategory.modifier}",
        "modifyDateTime": "<fmt:formatDate value='${depBaseCategory.modifyDateTime}' pattern='MM/dd/yyyy'/>",
        "delFlag": "${depBaseCategory.delFlag}",
        "id": "${depBaseCategory.id}",
        "toolBarTemplate":"${depBaseCategory.toolBarTemplate}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });    
          $("#expandFlag").kendoDropDownList({
          	dataSource:[
          		{text:"是",value:"1"},
          		{text:"否",value:"0"}
          	],
          	dataTextField:"text",
          	dataValueField:"value",
          	value:"${depBaseCategory.expandFlag}"
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
		   var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/saveDepBaseCategory";
		   var params = jQuery("#iForm").formSerialize();
		   var dropdownlist = $("#expandFlag").data("kendoDropDownList");
		   params.expandFlag = dropdownlist.value();
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
				   window.parent.parent.location.reload();
			   }
		 	});
       }	
   }
 </script>
</head>
<body style="margin-top: 0px;">
	<div id="main_content" class="k-content ">
		<br>
		<div class="x_content_title">
			<img src="${pageContext.request.contextPath}/images/window.png"
				alt="编辑使用规则分类树信息">&nbsp; 编辑使用规则分类树信息
		</div>
		<br>
		<form id="iForm" name="iForm" method="post" data-role="validator"
			novalidate="novalidate">
			<input type="hidden" id="id" name="id" value="${depBaseCategory.id}" />
			<input type="hidden" id="pid" name="pid" value="${pid}" />
			<table width="95%" align="center" border="0" cellspacing="0"
				cellpadding="5">
				<c:if test="${pid != -1}">
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="pname" class="required">父分类&nbsp;</label>
						<input type="hidden" id="ptreeid" name="ptreeid" value="${pCategory.treeId}" />
						<input id="pname" name="pname" type="text" class="k-textbox" value="${pCategory.name}"
						validationMessage="请输入父分类标识" readonly/> <span class="k-invalid-msg"
						data-for="pname"></span></td>
				</tr>
				</c:if>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="name" class="required">类型名称&nbsp;</label>
						<input id="name" name="name" type="text" class="k-textbox" required 
						data-bind="value: name" value="${depBaseCategory.name}"
						validationMessage="请输入类型名称" /> <span class="k-invalid-msg"
						data-for="name"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="code" class="required">类型代码&nbsp;</label>
						<input id="code" name="code" type="text" class="k-textbox" required
						data-bind="value: code" value="${depBaseCategory.code}" readonly 
						validationMessage="请输入类型代码" /> <span class="k-invalid-msg"
						data-for="code"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="expandFlag" class="required">默认展开&nbsp;</label>
						<input id="expandFlag" name="expandFlag" />
						<span class="k-invalid-msg" data-for="expandFlag"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="toolBarTemplate" class="required">工具栏模板&nbsp;</label>
						<textarea id="toolBarTemplate" name="toolBarTemplate" class="k-textbox" style="height:100px" validationMessage="请输入工具栏模板">${depBaseCategory.toolBarTemplate}</textarea> 
						<span class="k-invalid-msg" data-for="toolBarTemplate"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="orderNo" class="required">排序号&nbsp;</label>
						<input id="orderNo" name="orderNo" type="text" class="k-textbox"
						data-bind="value: orderNo" value="${depBaseCategory.orderNo}"
						validationMessage="请输入排序号" /> <span class="k-invalid-msg"
						data-for="orderNo"></span></td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="bottom" height="30">&nbsp;
						<div>
							<button type="button" id="iconButton" class="k-button k-primary"
								style="width: 90px" onclick="javascript:save();">保存</button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>