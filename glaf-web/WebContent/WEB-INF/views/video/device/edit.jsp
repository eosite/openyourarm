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
<title>编辑录像机</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
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
        "developerId": "${videoDevice.developerId}",
		"name": "${videoDevice.name}",
        "desc": "${videoDevice.desc}",
        "deviceId": "${videoDevice.deviceId}",
        "deviceName": "${videoDevice.deviceName}",
        "deviceSerial": "${videoDevice.deviceSerial}",
		"safeKey": "${videoDevice.safeKey}",
        "picUrl": "${videoDevice.picUrl}",
		"databaseId": "${videoDevice.databaseId}",
		"indexId": "${videoDevice.indexId}",
		"indexName": "${videoDevice.indexName}",
        "type": "${videoDevice.type}",
        "isEncrypt": "${videoDevice.isEncrypt}",
        "isShared": "${videoDevice.isShared}",
        "status": "${videoDevice.status}",
        "sortNo": "${videoDevice.sortNo}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/video/device/saveVideoDevice";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/video/device/saveVideoDevice";
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


  function chooseWBS(formName, elementId, elementName){
    var x_selected =  document.getElementById("databaseId");
    var url="<%=request.getContextPath()%>/mx/video/camera/wbstree?formName="+formName+"&elementId="+elementId+"&elementName="+elementName+"&databaseId=${videoDevice.databaseId}";
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&strfuntion="+x_selected.value;
    }
    var x=150;
    var y=50;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 565, 600);
  }

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑录像机">&nbsp;
编辑录像机</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="deviceId" name="deviceId" value="${videoDevice.deviceId}"/>
<input type="hidden" id="databaseId" name="databaseId" value="${videoDevice.databaseId}">
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
 
 <c:if test="${empty developerId or empty videoDevice}">
   <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="developerId" class="required">开发者&nbsp;</label>
        <select id="developerId" name="developerId">
		   <c:forEach items="${developers}" var="developer">
			<option value="${developer.developerId}">${developer.userId}[${developer.name}]</option>
		   </c:forEach>
         </select> 
	    <span class="k-invalid-msg" data-for="developerId"></span>
        <script type="text/javascript">
             document.getElementById("developerId").value="${videoDevice.developerId}"; 
        </script> 
    </td>
  </tr>
 </c:if>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style=" width:240px;"
	           value="${videoDevice.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="deviceSerial" class="required">设备序列号&nbsp;</label>
        <input id="deviceSerial" name="deviceSerial" type="text" class="k-textbox"  
	           data-bind="value: deviceSerial" style=" width:240px;"
	           value="${videoDevice.deviceSerial}" validationMessage="请输入设备序列号"/>
	    <span class="k-invalid-msg" data-for="deviceSerial"></span>
    </td>
  </tr>
 
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="deviceName" class="required">设备名称&nbsp;</label>
        <input id="deviceName" name="deviceName" type="text" class="k-textbox"  
	           data-bind="value: deviceName" style=" width:240px;"
	           value="${videoDevice.deviceName}" validationMessage="请输入设备名称"/>
	    <span class="k-invalid-msg" data-for="deviceName"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="safeKey" class="required">认证码&nbsp;</label>
        <input id="safeKey" name="safeKey" type="text" class="k-textbox"  
	           data-bind="value: safeKey" style=" width:240px;"
	           value="${videoDevice.safeKey}" validationMessage="请输入设备安全码"/>
	    <span class="k-invalid-msg" data-for="safeKey"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="picUrl" class="required">图片链接&nbsp;</label>
	    <textarea id="picUrl" name="picUrl" data-bind="value: picUrl" rows="6" cols="46" class="k-textbox" style="height:60px; width:240px;"></textarea>
	    <span class="k-invalid-msg" data-for="picUrl"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="desc" class="required">描述&nbsp;</label>
	    <textarea id="desc" name="desc" data-bind="value: desc" rows="6" cols="46" class="k-textbox" style="height:60px; width:240px;"></textarea>
	    <span class="k-invalid-msg" data-for="picUrl"></span>
    </td>
  </tr>
 
 	  <!-- <tr>
         <td width="2%" align="left">&nbsp;</td>
         <td align="left">
	       <label for="locked" class="required">工程部位</label>
		   <input type="hidden" id="indexId" name="indexId" 
		          value="${videoDevice.indexId}">
           <input type="text" id="indexName" name="indexName" size="35" readonly="true"
		          value="${videoDevice.indexName}" class="k-textbox " style=" width:240px;"
				  onclick="javascript:chooseWBS('iForm', 'indexId', 'indexName');">
		</td>
      </tr> -->

    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
		</div>
		</td>
      </tr>
</table>   
</form>
</div>     
 
</body>
</html>