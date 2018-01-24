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
<title>编辑SendDocApply信息</title>
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
   
   var baseData = new Object(),contextPath = "<%=request.getContextPath()%>";
   /**
	*获取基础数据放在全局
	*/
	function getBaseDataByType(type){
		if(!baseData[type]){
			$.ajax({
				url : "<%=request.getContextPath()%>/rs/dictory/jsonArray/" + type,
				type:'post',
				dataType:'json',
				async : false,
				success:function(data){
					if(data){
						$.each(data,function(index,item){
							item.text = item.name;
						});
					}
					baseData[type] = data || [];
				}
			}); 
		}
		return baseData[type];
	}
  jQuery(function() {
    var viewModel = kendo.observable({
        "subject": "${sendDocApply.subject}",
        "securityLevel": "${sendDocApply.securityLevel}",
        "urgencyLevel": "${sendDocApply.urgencyLevel}",
        "docNo": "${sendDocApply.docNo}",
        "docType": "${sendDocApply.docType}",
        "draftName": "${sendDocApply.draftName}",
        "draftDate": "<fmt:formatDate value='${sendDocApply.draftDate}' pattern='MM/dd/yyyy'/>",
        "sendDocDate": "<fmt:formatDate value='${sendDocApply.sendDocDate}' pattern='MM/dd/yyyy'/>",
        "serialNumber": "${sendDocApply.serialNumber}",
        "fromCompany": "${sendDocApply.fromCompany}",
        "keywords": "${sendDocApply.keywords}",
        "docToCompany": "${sendDocApply.docToCompany}",
        "docCCCompany": "${sendDocApply.docCCCompany}",
        "checkOpinion": "${sendDocApply.checkOpinion}",
        "countersignOpinion": "${sendDocApply.countersignOpinion}",
        "signAndIssueOpinion": "${sendDocApply.signAndIssueOpinion}",
        "remark": "${sendDocApply.remark}",
        "createDate": "<fmt:formatDate value='${sendDocApply.createDate}' pattern='MM/dd/yyyy'/>",
        "updateDate": "<fmt:formatDate value='${sendDocApply.updateDate}' pattern='MM/dd/yyyy'/>",
        "createBy": "${sendDocApply.createBy}",
        "updateBy": "${sendDocApply.updateBy}",
        "status": "${sendDocApply.status}",
        "id": "${sendDocApply.id}"
    });

    //kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });
          
    });
    
     function openUploadFiles(){
		var link = contextPath + "/mx/flow/property/uploadFiels";
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "文件上传",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
	                  iframe: {src: link}
		});
	}

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
	   //form.action = "<%=request.getContextPath()%>/mx/oa/doc/sendDocApply/saveSendDocApply";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/oa/doc/sendDocApply/saveSendDocApply";
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
					   window.parent.getWorkList();
					   //$('#goBackButton').click();
				   }
			 });
       }
   }
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title" id="title"  ><span style="font-size: 30px;color: red;" >发文呈批表</span> </div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${sendDocApply.id}"/>
<table style="width:80%;border:0;cellspacing:0;cellpadding:5" align='center'>
	<tr>
		<td align="left">&nbsp;</td>
		<td align="left" colspan="5">
		<label for="subject" class="required" style="color:red;">文件标题&nbsp;</label>
        <input id="subject" name="subject" type="text" class="k-textbox"  
	   		data-bind="value: subject" style="color:red;width:660px;"
	   value="${sendDocApply.subject}" validationMessage="请输入subject"/>	 
	   </td>
	</tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="securityLevel" class="required">密级&nbsp;</label>
	<input id="securityLevel" name="securityLevel" class="k-textbox"  data-bind="value: securityLevel"
	       value="${sendDocApply.urgencyLevel}"/>
    </td>
    <td align="left">
	<label for="keywords" class="required">主题词&nbsp;</label>
        <input id="keywords" name="keywords" type="text" class="k-textbox"  
	   data-bind="value: keywords"
	   value="${sendDocApply.keywords}" validationMessage="请输入主题词"/>
	<span class="k-invalid-msg" data-for="keywords"></span>
    </td>
    
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="urgencyLevel" class="required">紧急程度&nbsp;</label>
	<input id="urgencyLevel" name="urgencyLevel" type="text" class="k-textbox" 
	   data-bind="value: urgencyLevel"
	       value="${sendDocApply.urgencyLevel}" validationMessage="请输入紧急程度"/>
	<span class="k-invalid-msg" data-for="urgencyLevel"></span>
    </td>
    <td align="left">
	<label for="docNo" class="required">发文编号&nbsp;</label>
        <input id="docNo" name="docNo" type="text" class="k-textbox"  
	   data-bind="value: docNo" readonly="readonly"
	   value="${sendDocApply.docNo}" validationMessage="请输入发文编号"/>
	<span class="k-invalid-msg" data-for="docNo"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="docType" class="required">公文类别&nbsp;</label>
        <input id="docType" name="docType" type="text" class="k-textbox"  
	   data-bind="value: docType"
	   value="${sendDocApply.docType}" validationMessage="请输入公文类别"/>
	<span class="k-invalid-msg" data-for="docType"></span>
	<script>
	  	$.each(['securityLevel','urgencyLevel','docType'],function(index,id){
	  		 var obj = $("#" + id),kendoDropDownList = obj.kendoDropDownList({
					autoBind: true,
				    dataTextField: "name",
				    dataValueField: "value",
				    dataSource: getBaseDataByType(id)
			  }).data('kendoDropDownList');
			  if(!obj.val()){
			  	 kendoDropDownList.select(0);
			  }
          });
	  </script>
    </td>
    <td align="left">
	<label for="draftName" class="required">拟稿人&nbsp;</label>
        <input id="draftName" name="draftName" type="text" class="k-textbox"  
	   data-bind="value: draftName" readonly="readonly"
	   value="${sendDocApply.draftName == null ? user.name : sendDocApply.draftName}" validationMessage="请输入拟稿人"/>
	<span class="k-invalid-msg" data-for="draftName"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="draftDate" class="required">拟稿日期&nbsp;</label>
	<input id="draftDate" name="draftDate" class="k-textbox" data-type="date" format="yyyy-MM-dd"
	       value="<fmt:formatDate value="${sendDocApply.draftDate}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入拟稿日期"/>
	<span class="k-invalid-msg" data-for="draftDate"></span>
    </td>
    <td align="left">
	<label for="sendDocDate" class="required">发文日期&nbsp;</label>
	<input id="sendDocDate" name="sendDocDate" type="text" class="k-textbox"  data-type="date" format="yyyy-MM-dd"
	       value="<fmt:formatDate value="${sendDocApply.sendDocDate}" pattern="yyyy-MM-dd"/>" 
	       validationMessage="请输入发文日期"/>
	<span class="k-invalid-msg" data-for="sendDocDate"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="serialNumber" class="required">流水号&nbsp;</label>
        <input id="serialNumber" name="serialNumber" type="text" class="k-textbox"  
	   data-bind="value: serialNumber" readonly="readonly"
	   value="${sendDocApply.serialNumber}" validationMessage="请输入流水号"/>
	<span class="k-invalid-msg" data-for="serialNumber"></span>
    </td>
    <td align="left">
	<label for="fromCompany" class="required">发文单位&nbsp;</label>
        <input id="fromCompany" name="fromCompany" type="text" class="k-textbox"  
	   data-bind="value: fromCompany"
	   value="${sendDocApply.fromCompany}" validationMessage="请输入发文单位"/>
	<span class="k-invalid-msg" data-for="fromCompany"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="docToCompany" class="required">发送&nbsp;</label>
        <input id="docToCompany" name="docToCompany" type="text" class="k-textbox"  
	   data-bind="value: docToCompany"
	   value="${sendDocApply.docToCompany}" validationMessage="请输入发送"/>
	<span class="k-invalid-msg" data-for="docToCompany"></span>
    </td>
    <td align="left">
	<label for="docCCCompany" class="required">抄送&nbsp;</label>
        <input id="docCCCompany" name="docCCCompany" type="text" class="k-textbox"  
	   data-bind="value: docCCCompany"
	   value="${sendDocApply.docCCCompany}" validationMessage="请输入抄送"/>
	<span class="k-invalid-msg" data-for="docCCCompany"></span>
    </td>
  </tr>
  
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left" colspan="4">
	<label for="checkOpinion" class="required">核稿意见&nbsp;</label>
	   <textarea id="checkOpinion" name="checkOpinion"
	   data-bind="value: checkOpinion"
	   value="${sendDocApply.checkOpinion}" validationMessage="请输入核稿意见" rows="6" cols="80">${sendDocApply.checkOpinion}</textarea>
	<span class="k-invalid-msg" data-for="checkOpinion"></span>
	</td>
  </tr>
  
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left" colspan="4">
	<label for="countersignOpinion" class="required">领导会签&nbsp;</label>
	   <textarea id="countersignOpinion" name="countersignOpinion"
	   data-bind="value: countersignOpinion"
	   value="${sendDocApply.countersignOpinion}" validationMessage="请输入领导会签" rows="6" cols="80">${sendDocApply.countersignOpinion}</textarea>
	<span class="k-invalid-msg" data-for="countersignOpinion"></span>
	</td>
  </tr>
  <tr>
  	<td width="2%" align="left">&nbsp;</td>
    <td align="left" colspan="4">
	<label for="signAndIssueOpinion" class="required">领导签发&nbsp;</label>
	   <textarea id="signAndIssueOpinion" name="signAndIssueOpinion"
	   data-bind="value: signAndIssueOpinion"
	   value="${sendDocApply.signAndIssueOpinion}" validationMessage="请输入领导签发" rows="6" cols="80">${sendDocApply.signAndIssueOpinion}</textarea>
		<span class="k-invalid-msg" data-for="signAndIssueOpinion"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left" colspan="4">
	<label for="remark" class="required">备注&nbsp;</label>
	    <textarea id="remark" name="remark" data-bind="value: remark"
	   value="${sendDocApply.remark}" validationMessage="请输入备注" rows="6" cols="80">${sendDocApply.remark}</textarea>
	<span class="k-invalid-msg" data-for="remark"></span>
    </td>
  </tr>
    <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
          
           <button type="button" id="goBackButton"  class="k-button k-primary" style="width: 90px" onclick="parent.close();">关闭</button> 
		</div>
	</td>
      </tr>
</table>   
</form>
</div>     
</body>
<script type="text/javascript">
	$.each(['sendDocDate','draftDate'],function(index,id){
       	$("#" + id).kendoDatePicker({
   		format : 'yyyy-MM-dd'
   		});
    });
    
</script>
</html>