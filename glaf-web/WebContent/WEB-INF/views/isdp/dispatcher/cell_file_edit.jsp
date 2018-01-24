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
<title>文件录入</title>
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
	   //form.submit();
	   var link = "${contextPath}/mx/isdp/cell/file/save";
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
	src="<%=request.getContextPath()%>/images/window.png" alt="原文录入">&nbsp;原文录入</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${cellUpicInfo.id}"/>
<input type="hidden" id="treeNodeId" name="treeNodeId" value="${treeNodeId}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td align="left">
		<label for="tname" class="required">文件题名&nbsp;</label>
		<input id="tname" name="tname" type="text" class="k-textbox" 
		   data-bind="value: tname" value="${cellUpicInfo.tname}" validationMessage="请输入文件题名"/>
		<span class="k-invalid-msg" data-for="tname"></span>
    </td>
    <td colspan="2" align="left">
    </td>
  </tr>
  <tr>
    <td align="left">
		<label for="tagnum">文件编号&nbsp;</label>
        <input id="tagnum" name="tagnum" type="text" class="k-textbox"  
	  	 data-bind="value: tagnum" value="${cellUpicInfo.tagnum}" />
	<span class="k-invalid-msg" data-for="tagnum"></span>
    </td>

    <td align="left">
	<label for="ctime" >文件时间&nbsp;</label>
        <input id="ctime" name="ctime" type="text"  data-role="datetimepicker"
	   data-bind="value: ctime"  data-format="yyyy/MM/dd HH:mm"/>
		<span class="k-invalid-msg" data-for="ctime"></span>
    </td>
  </tr>
  <tr>
    <td align="left">
	<label for="duty" >责任者&nbsp;</label>
	<input id="duty" name="duty" type="text" class="k-textbox"
	     data-bind="value: duty" value="${cellUpicInfo.duty}" />
	<span class="k-invalid-msg" data-for="duty"></span>
    </td>
    
    <td align="left">
	<label for="page" >页数&nbsp;</label>
	<input id="page" name="page" type="text" data-role="numerictextbox"
	     data-bind="value: page" value="${cellUpicInfo.page}" />
	<span class="k-invalid-msg" data-for="page"></span>
    </td>
  </tr>
   <tr>
    <td align="left">
	<label for="thematic">主题词&nbsp;</label>
	<input id="thematic" name="thematic" type="text" class="k-textbox"
	     data-bind="value: thematic" value="${cellUpicInfo.thematic}" />
	<span class="k-invalid-msg" data-for="thematic"></span>
    </td>
    
    <td align="left">
	<label for="annotations" >附注&nbsp;</label>
	<input id="annotations" name="annotations" type="text" class="k-textbox"
	     data-bind="value: annotations" value="${cellUpicInfo.annotations}" />
	<span class="k-invalid-msg" data-for="annotations"></span>
    </td>
  </tr>
  <tr>
   <td colspan="2" align="left">
		<input type="file" name="files" id="files" />	
    </td>
    <td></td>
  </tr>
    <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	</div>
	</td>
      </tr>
</table>   
</form>
</div>     
<script>
	function onSuccess(e) {
	    var files = e.files;
	    if (e.operation == "upload") {
			$('#tname').val(files[0].name);
			var resultJson = JSON.parse(e.XMLHttpRequest.responseText);
			$('#id').val(resultJson.id);
			$('#treeNodeId').val(resultJson.treeNodeId);
	    }
	}
	var files  = [] ;
	if('${isdpFile.fileName}' != "" ){
		files = [{name:'${isdpFile.fileName}'}] ;
	}
    jQuery(document).ready(function() {
    	var viewModel = kendo.observable({
     		'tname' : '${cellUpicInfo.tname}',
     		'ctime' : new Date('${cellUpicInfo.ctime}') ,
     		'tagnum' : '${cellUpicInfo.tagnum}',
     		'duty' : '${cellUpicInfo.duty}',
     		'page' : '${cellUpicInfo.page}',
     		'thematic' : '${cellUpicInfo.thematic}',
     		'annotations' : '${cellUpicInfo.annotations}'
   	 	});

    	kendo.bind(jQuery("#iForm"), viewModel);
    	    $("#files").kendoUpload({
    	    	async: {
    	            saveUrl: "${contextPath}/mx/isdp/cell/file/saveFiles?treeNodeId=${treeNodeId}&id=${cellUpicInfo.id}"
    	        },
    	    	multiple: false,
    	    	success: onSuccess,
    	    	files:files
            });
	
	  	 
    });
</script>
</body>
</html>