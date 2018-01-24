<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
<title>服务关联设置</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<%
	    StringBuffer bufferx = new StringBuffer();
	    StringBuffer buffery = new StringBuffer();
 
		List databases = (List)request.getAttribute("unselected");
		List selecteds = (List)request.getAttribute("selected");
        if(databases != null && databases.size() > 0){
		    for(int j=0; j<databases.size(); j++){
				Database d = (Database) databases.get(j);
				if(selecteds != null && selecteds.contains(d.getId())){
                    buffery.append("\n<option value=\"").append(d.getId()).append("\">")
						   .append(d.getTitle()).append(" [").append(d.getMapping()).append("]")
						   .append("</option>");
				} else {
				    bufferx.append("\n<option value=\"").append(d.getId()).append("\">")
						   .append(d.getTitle()).append(" [").append(d.getMapping()).append("]")
						   .append("</option>");
				}
			}
		}

%>
 

<script language="javascript">

 var contextPath = "${contextPath}";

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


  function submitRequestX() {
    var len=document.iForm.selected.length;
	var result="";
	for (var i=0;i<len;i++) {
      result=result+","+document.iForm.selected.options[i].value;
    }
    document.iForm.objectIds.value=result;
	return true;
  }


   function save() {
		var len= document.iForm.selected.length;
		var result = "";
		for (var i=0;i<len;i++) {
		  result = result + document.iForm.selected.options[i].value;
		  if(i < (len - 1)){
			  result = result + ",";
		   }
		}

        document.getElementById("databaseIds").value=result;
	    var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/database/saveServerRef',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert(data.message);
					   } 
				   }
			 });  
    }


    function addToList2(list, value, text) {
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

  function closeXY(){
     if(window.opener != null){
		 window.close();
	 } else {
        history.back();
	 }
  }

  function switchServer(){
	  var serverId = document.getElementById("serverId").value;
	  location.href="<%=request.getContextPath()%>/mx/sys/database/showServerRef?serverId="+serverId;
  }

</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<center> 
<br>
<form id="iForm" name="iForm" class="x-form" method="post">
<input type="hidden" name="objectIds"> 
<input type="hidden" id="databaseIds" name="databaseIds"> 
<div class="content-block" style="width: 785px;"> 
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="服务关联设置">&nbsp;  服务关联设置</div>

<fieldset class="x-fieldset" style="width: 95%;">
&nbsp;&nbsp;
<select id="serverId" name="serverId" onchange="javascript:switchServer();">
	<option value="">----请选择----</option>
	<c:forEach items="${servers}" var="server">
	<option value="${server.id}">${server.title}</option>
	</c:forEach>
</select>
<script type="text/javascript">
    document.getElementById("serverId").value="${serverId}";
</script>
<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="95%">
	<tbody>		 
		<tr>
			<td colspan="2" width="212">
			<div align="center">
			   <div align="center">可选数据库</div>
			</div>
			</td>
			<td></td>
			<td colspan="2" width="212">
			<div align="center">已选数据库</div>
			</td>
		</tr>
		<tr>
			<td width="2">&nbsp;</td>
			<td height="26" valign="top" width="182">
			<div align="center"><select class="list" id="noselected" name="noselected" 
				style="width: 320px; height: 280px;" multiple="multiple" size="12"
				ondblclick="addElement()"><%=bufferx.toString()%>
			</select></div>
			</td>
			<td width="114">
			<div align="center"><input name="add" value="添加->"
				onclick="addElement()" class="btn " type="button"> <br>
			<br>
			<input name="remove" value="<- 删除" onclick="removeElement()"
				class="btn " type="button"></div>
			</td>
			<td height="26" valign="top" width="212">
			<div align="center"><select id="selected" name="selected" class="list"
				style="width: 320px; height: 280px;" multiple="multiple" size="12"
				ondblclick="removeElement()"><%=buffery.toString()%>
			</select></div>
			</td>
			<td width="23">&nbsp;</td>
		</tr>
	</tbody>
</table>
</fieldset>


<div align="center"><br />
 
 <input value=" 确 定 " class=" btn btn-primary" name="button" type="button"
		onclick="javacsript:save();">
 <input value=" 关 闭 " class=" btn" name="close"
	   onclick="javacsript:window.close();" type="button">  
<br />
</div>

</div>
</form>
</center>

 