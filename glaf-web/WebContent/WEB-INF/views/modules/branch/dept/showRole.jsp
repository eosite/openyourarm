<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
 		StringBuffer bufferx = new StringBuffer();
		StringBuffer buffery = new StringBuffer();
	    List roles = (List)request.getAttribute("roles");
	    List selecteds = (List)request.getAttribute("selectedIds");
        if(roles != null && roles.size() > 0){
			for(int j=0;j<roles.size();j++){
				SysRole r = (SysRole) roles.get(j);
				if(selecteds != null && selecteds.contains(r.getId())){
                    buffery.append("\n<option value=\"").append(r.getId()).append("\" selected>")
						   .append(r.getName()).append(" [")
						   .append(r.getCode() != null ? r.getCode() : r.getId()).append("]</option>");
				} else {
				    bufferx.append("\n<option value=\"").append(r.getId()).append("\">")
						   .append(r.getName()).append(" [")
						   .append(r.getCode() != null ? r.getCode() : r.getId()).append("]</option>");
				}
			}
		}

%>
<!DOCTYPE html>
<html>
<title>角色选择</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
 
<script language="javascript">

var contextPath="<%=request.getContextPath()%>";

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

 
  function saveData(closeWin) {
	//var parent_window = getOpener();
	//var x_roles = parent_window.document.getElementById("${elementId}");
    //var x_roles_name = parent_window.document.getElementById("${elementName}");

    var len= document.iForm.selected.length;
	var result = "";
	var names = "";
	for (var i=0; i<len; i++) {
      result = result + document.iForm.selected.options[i].value;
	  names = names + document.iForm.selected.options[i].text;
	  if(i < (len - 1)){
		  result = result + ",";
		  names = names + ",";
	   }
    }

	//x_roles.value = result;
	//x_roles_name.value = names;
     
	//window.close();
    var params = jQuery("#iForm").formSerialize();
    jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/mx/branch/department/saveRoles?deptId=${deptId}&items="+result,
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
						    //window.parent.location.reload();	
							if(closeWin){
								window.parent.location.reload();
								window.close();
							}
					   }
				   }
			 });
  }

  function switchMenuX(){
	 var menuFlag = document.getElementById("menuFlag").value;
     location.href="<%=request.getContextPath()%>/mx/branch/department/showRole?deptId=${deptId}&menuFlag="+menuFlag;
  }
</script>

<body >
<center>
<form id="iForm" name="iForm" class="x-form" method="post">
<input type="hidden" id="deptId" name="deptId" value="${deptId}">
<div class="content-block" style="width: 100%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="机构角色设置">&nbsp;
机构角色设置</div>
<fieldset class="x-fieldset" style="width: 98%;">
<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
	<tbody>
	    <tr>
		    <td class="beta" colspan="3" align="left">
			 <label> &nbsp;&nbsp;角色选项&nbsp;</label> 
			  <select id="menuFlag" name="menuFlag" onchange="javascript:switchMenuX();" class="form-control" style="width:200px;display: inline-block;">
				<option value="0">角色下放</option>
				<option value="9">角色菜单</option>
			  </select>
			  <script type="text/javascript">
			      document.getElementById("menuFlag").value="${menuFlag}";
			  </script>
			</td>
			<td class="beta" colspan="3" align="left">
			<label> &nbsp;${dept.name}下级机构是否使用同样设置&nbsp;</label>
			 <select id="isPropagationAllowed" name="isPropagationAllowed" class="form-control" style="width:130px;display: inline-block;">
				<option value="">----请选择----</option>
				<option value="Y">是</option>
				<option value="N">否</option>
			 </select>
			 <script type="text/javascript">
			      document.getElementById("isPropagationAllowed").value="${isPropagationAllowed}";
			 </script>
			</td>
		</tr>
		<tr>
			<td class="beta" colspan="2">
			<div align="left">&nbsp;&nbsp;<b>可选角色</b></div>
			</td>
			<td class="beta"></td>
			<td class="beta" colspan="3">
			<div align="left">&nbsp;&nbsp;<b>已选角色</b></div>
			</td>
		</tr>
		<tr>
			<td class="beta" width="18">&nbsp;</td>
			<td class="table-content" height="26" valign="top" width="390">
			<div align="center"><select class="form-control"
				style="width: 300px; height: 250px;" multiple="multiple" size="12"
				name="noselected" ondblclick="addElement()"><%=bufferx.toString()%>
			</select></div>
			</td>
			<td class="beta" width="114">
			<div align="center"><input name="add" value="添加 ->"
				onclick="addElement()" class="btn" type="button"> <br>
			<br>
			<input name="remove" value="<- 删除" onclick="removeElement()"
				class="btn" type="button"></div>
			</td>
			<td class="table-content" height="26" valign="top" width="359">
			<div align="center"><select class="form-control"
				style="width: 300px; height: 250px;" multiple="multiple" size="12"
				name="selected" ondblclick="removeElement()"><%=buffery.toString()%>
			</select></div>
			</td>
			<td class="beta" width="23">&nbsp;</td>
		</tr>
	</tbody>
</table>
</fieldset>

<div align="center">
<br>
<input value="保存" class="btn btn-success" name="submit252" type="button" onclick="javacsript:saveData(false);">
<input value="保存并关闭" class="btn btn-primary" name="submit253" type="button" onclick="javacsript:saveData(true);">
</div>
</div>
</form>
</center>