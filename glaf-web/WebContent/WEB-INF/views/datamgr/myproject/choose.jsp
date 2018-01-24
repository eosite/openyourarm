<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ page import="com.glaf.base.project.domain.*"%>
<%
 		StringBuffer bufferx = new StringBuffer();
		StringBuffer buffery = new StringBuffer();
	    List databases = (List)request.getAttribute("databases");
		List databaseIds = (List)request.getAttribute("databaseIds");
        if(databases != null && databases.size() > 0){
			for(int j=0;j<databases.size();j++){
				Database d = (Database) databases.get(j);
				bufferx.append("\n<option value=\"").append(d.getId()).append("\">")
					   .append(d.getTitle()).append(" [")
					   .append(d.getDbname()).append("]</option>");
				if(databaseIds != null && databaseIds.contains(d.getId())){
                   buffery.append("\n<option value=\"").append(d.getId()).append("\">")
					   .append(d.getTitle()).append(" [")
					   .append(d.getDbname()).append("]</option>");
				}
			}
		}
%>
<!DOCTYPE html>
<html>
<title>标段设置</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
 
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

</script>


<script language="javascript">

  function saveData() {

    var len= document.iForm.selected.length;
	var result = "";
	for (var i=0;i<len;i++) {
      result = result + document.iForm.selected.options[i].value;
	  if(i < (len - 1)){
		  result = result + ",";
	   }
    }


	if(result != ""){
		if(confirm("确定保存操作吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/mx/my/project/saveDB?projectId=${projectId}&databaseIds="+result,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
				   }
			 });
		}
	}

  }
</script>

<body >
<center>
<form name="iForm" class="x-form" method="post">
<div class="content-block" style="width: 100%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="选择数据库">&nbsp;选择数据库</div>
<fieldset class="x-fieldset" style="width: 98%;">

<table class="table-border" align="center" cellpadding="4"
	cellspacing="1" width="90%">
	<tbody>
		<tr>
			<td class="beta" colspan="2">
			<div align="center">可选数据库</div>
			</td>
			<td class="beta"></td>
			<td class="beta" colspan="3">
			<div align="center">已选数据库</div>
			</td>
		</tr>
		<tr>
			<td class="beta" width="18">&nbsp;</td>
			<td class="table-content" height="26" valign="top" width="690">
			<div align="center">
			  <select class="list"
				style="width: 380px; height: 250px;" multiple="multiple" size="12"
				name="noselected" ondblclick="addElement()">
				<%=bufferx.toString()%>
			  </select>
			</div>
			</td>
			<td class="beta" width="84">
			<div align="center"><input name="add" value="添加 ->"
				onclick="addElement()" class="btn" type="button"> <br>
			<br>
			<input name="remove" value="<- 删除" onclick="removeElement()"
				class="btn" type="button"></div>
			</td>
			<td class="table-content" height="26" valign="top" width="359">
			<div align="center">
			  <select class="list"
				style="width: 380px; height: 250px;" multiple="multiple" size="12"
				name="selected" ondblclick="removeElement()">
				<%=buffery.toString()%>
			  </select>
			</div>
			</td>
			<td class="beta" width="23">&nbsp;</td>
		</tr>
	</tbody>
</table>
</fieldset>

<div align="center">
  <br>
  <input name="submit252" type="button" value="确 定" class="btn btn-primary"
	     onclick="javacsript:saveData();">
  <input name="submit253" type="button" value="返 回" class="btn btn-primary"
	     onclick="javacsript:history.back(0);">
 <br />
</div>
</div>
</form>
</center>