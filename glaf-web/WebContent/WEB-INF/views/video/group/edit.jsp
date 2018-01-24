<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.video.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
        String theme = com.glaf.core.util.RequestUtils.getTheme(request);
        request.setAttribute("theme", theme);

	    StringBuffer bufferx = new StringBuffer();
		StringBuffer buffery = new StringBuffer();
	    List cameras = (List)request.getAttribute("cameras");
		List cameraIds = (List)request.getAttribute("selectedCameraIds");
        if(cameras != null && cameras.size() > 0){
			for(int j=0;j<cameras.size();j++){
				VideoCamera c = (VideoCamera) cameras.get(j);
				if(cameraIds != null && cameraIds.contains(c.getCameraId())){
                   buffery.append("\n<option value=\"").append(c.getCameraId()).append("\">")
					   .append(c.getName() != null ? c.getName() : c.getDeviceName()).append(" [")
					   .append(c.getDeviceSerial()).append("]</option>");
				} else {
                   bufferx.append("\n<option value=\"").append(c.getCameraId()).append("\">")
					   .append(c.getName() != null ? c.getName() : c.getDeviceName()).append(" [")
					   .append(c.getDeviceSerial()).append("]</option>");
				}
			}
		}

		StringBuffer bufferx2 = new StringBuffer();
		StringBuffer buffery2 = new StringBuffer();
	    List users = (List)request.getAttribute("users");
		List userIds = (List)request.getAttribute("selectedUserIds");
        if(users != null && users.size() > 0){
			for(int j=0;j<users.size();j++){
				User d = (User) users.get(j);
				if(userIds != null && userIds.contains(d.getActorId())){
                   buffery2.append("\n<option value=\"").append(d.getActorId()).append("\">")
					   .append(d.getName() != null ? d.getName() : d.getActorId()).append(" [")
					   .append(d.getActorId()).append("]</option>");
				} else {
                   bufferx2.append("\n<option value=\"").append(d.getActorId()).append("\">")
					   .append(d.getName() != null ? d.getName() : d.getActorId()).append(" [")
					   .append(d.getActorId()).append("]</option>");
				}
			}
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑群组信息</title>
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

function addElement() {
        var list = document.iForm.noselected;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList(value, text);
				list.remove(i);
				i=i-1;
            }
        }
    }

function addElement2() {
        var list = document.iForm.noselected2;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList2(value, text);
				list.remove(i);
				i=i-1;
            }
        }
    }

  function addToList(value, text) {
        var list = document.iForm.selected;
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }

  function addToList2(value, text) {
        var list = document.iForm.selected2;
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }


  function removeElement() {
        var list = document.iForm.selected;
		var slist = document.iForm.noselected;
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }
		
				
  function removeElement2() {
        var list = document.iForm.selected2;
		var slist = document.iForm.noselected2;
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }

  jQuery(function() {
    var viewModel = kendo.observable({
        "developerId": "${videoGroup.developerId}",
        "name": "${videoGroup.name}",
        "code": "${videoGroup.code}",
        "desc": "${videoGroup.desc}",
        "type": "${videoGroup.type}",
        "sortNo": "${videoGroup.sortNo}",
        "groupId": "${videoGroup.groupId}"
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
	    var len= document.iForm.selected.length;
		var result = "";
		for (var i=0;i<len;i++) {
		  result = result + document.iForm.selected.options[i].value;
		  if(i < (len - 1)){
			  result = result + ",";
		   }
		}

		var len2= document.iForm.selected2.length;
		var result2 = "";
		for (var i=0;i<len2;i++) {
		  result2 = result2 + document.iForm.selected2.options[i].value;
		  if(i < (len2 - 1)){
			  result2 = result2 + ",";
		   }
		}

	    var link = "<%=request.getContextPath()%>/mx/video/group/saveVideoGroup?cameraIds="+result+"&actorIds="+result2;
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
					       window.location.reload();
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑群组信息">&nbsp;
编辑群组信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="groupId" name="groupId" value="${videoGroup.groupId}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${videoGroup.name}" validationMessage="请输入名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
   
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="code" class="required">编码&nbsp;</label>
        <input id="code" name="code" type="text" class="k-textbox"  
	   data-bind="value: code"
	   value="${videoGroup.code}" validationMessage="请输入编码"/>
	<span class="k-invalid-msg" data-for="code"></span>
    </td>
  </tr>
 
   <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="type" class="required">类型&nbsp;</label>
        <input id="type" name="type" type="text" class="k-textbox"  
	   data-bind="value: type"
	   value="${videoGroup.type}" validationMessage="请输入类型"/>
	<span class="k-invalid-msg" data-for="type"></span>
    </td>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="locked" class="required">是否有效&nbsp;</label>
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${videoGroup.locked}";
	  </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left" colspan="3">
	<label for="desc" class="required">描述&nbsp;</label>
	    <textarea id="desc" name="desc" data-bind="value: desc" rows="6" cols="46" class="k-textbox" style="height:60px; width:540px;"></textarea>
	    <span class="k-invalid-msg" data-for="picUrl"></span>
    </td>
  </tr>
 
    <tr>
	 <td colspan="4">
		<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
			<tbody>
				<tr>
					<td class="beta" colspan="2">
					<div align="center">可选摄像头</div>
					</td>
					<td class="beta"></td>
					<td class="beta" colspan="3">
					<div align="center">已选摄像头</div>
					</td>
				</tr>
				<tr>
					<td class="beta" width="18">&nbsp;</td>
					<td class="table-content" height="26" valign="top" width="690">
					<div align="center">
					  <select class="list"
						style="width: 280px; height: 250px;" multiple="multiple" size="12"
						name="noselected" ondblclick="addElement()">
						<%=bufferx.toString()%>
					  </select>
					</div>
					</td>
					<td class="beta" width="84">
					<div align="center"><input name="add" value="添加 ->"
						onclick="addElement()" class="k-button" type="button"> <br>
					<br>
					<input name="remove" value="<- 删除" onclick="removeElement()"
						class="k-button" type="button"></div>
					</td>
					<td class="table-content" height="26" valign="top" width="359">
					<div align="center">
					  <select class="list"
						style="width: 280px; height: 250px;" multiple="multiple" size="12"
						name="selected" ondblclick="removeElement()">
						<%=buffery.toString()%>
					  </select>
					</div>
					</td>
					<td class="beta" width="23">&nbsp;</td>
				</tr>
			</tbody>
		</table>
	 </td>
	</tr>

    <tr>
	 <td colspan="4">
		<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
			<tbody>
				<tr>
					<td class="beta" colspan="2">
					<div align="center">可选用户</div>
					</td>
					<td class="beta"></td>
					<td class="beta" colspan="3">
					<div align="center">已选用户</div>
					</td>
				</tr>
				<tr>
					<td class="beta" width="18">&nbsp;</td>
					<td class="table-content" height="26" valign="top" width="690">
					<div align="center">
					  <select class="list"
						style="width: 280px; height: 250px;" multiple="multiple" size="12"
						name="noselected2" ondblclick="addElement2()">
						<%=bufferx2.toString()%>
					  </select>
					</div>
					</td>
					<td class="beta" width="84">
					<div align="center"><input name="add" value="添加 ->"
						onclick="addElement2()" class="k-button" type="button"> <br>
					<br>
					<input name="remove" value="<- 删除" onclick="removeElement2()"
						class="k-button" type="button"></div>
					</td>
					<td class="table-content" height="26" valign="top" width="359">
					<div align="center">
					  <select class="list"
						style="width: 280px; height: 250px;" multiple="multiple" size="12"
						name="selected2" ondblclick="removeElement2()">
						<%=buffery2.toString()%>
					  </select>
					</div>
					</td>
					<td class="beta" width="23">&nbsp;</td>
				</tr>
			</tbody>
		</table>
	 </td>
	</tr>

    <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
         <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
		  <br>
		  <br>
	     </div>
	    </td>
    </tr>
</table>   
</form>
</div>     
<script>
    jQuery(document).ready(function() {
	     //jQuery("#createTime").kendoDateTimePicker();
	    jQuery("#sortNo").kendoNumericTextBox();			 
    });
</script>
</body>
</html>