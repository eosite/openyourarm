<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%
 		StringBuffer bufferx = new StringBuffer();
		StringBuffer buffery = new StringBuffer();
	    List databases = (List)request.getAttribute("databases");
		List sqlDefinitions = (List)request.getAttribute("sqlDefinitions");
        if(databases != null && databases.size() > 0){
			for(int j=0;j<databases.size();j++){
				Database r = (Database) databases.get(j);
				bufferx.append("\n<option value=\"").append(r.getId()).append("\">")
					   .append(r.getTitle()).append(" [")
					   .append(r.getDbname()).append("]</option>");		
			}
		}

		if(sqlDefinitions != null && sqlDefinitions.size() > 0){
			for(int j=0;j<sqlDefinitions.size();j++){
				SqlDefinition r = (SqlDefinition) sqlDefinitions.get(j);
				if(r.getParentId() != null && r.getParentId() > 0){
					continue;
				}
				buffery.append("\n<option value=\"").append(r.getId()).append("\">")
					   .append(r.getTitle()).append(" [")
					   .append(r.getCreateBy()).append("]</option>");		
			}
		}

%>
<!DOCTYPE html>
<html>
<title>查询选择</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
 
<script language="javascript">

var contextPath="<%=request.getContextPath()%>";

  function addElement() {
        var list = document.iForm.noselected1;
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
        var list = document.iForm.selected1;
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
        var list = document.iForm.selected1;
		var slist = document.iForm.noselected1;
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
</script>


<script language="javascript">

  function executeQuery() {

    var len= document.iForm.selected1.length;
	var result = "";
	for (var i=0;i<len;i++) {
      result = result + document.iForm.selected1.options[i].value;
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

	if(result != "" && result2 != ""){
		if(confirm("确定在这些选择库进行查询操作吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath()%>/mx/datamgr/sql/execution/executeQuery?databaseIds="+result2+"&sqlDefIds="+result,
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
                       
					   if(data != null && data.jobNo!= null){
					      var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/showHistory?jobNo="+data.jobNo;
						  location.href=link;
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
	src="<%=request.getContextPath()%>/images/window.png" alt="选择查询">&nbsp;
选择查询</div>
<fieldset class="x-fieldset" style="width: 98%;">
<table class="table-border" align="center" cellpadding="4"
	cellspacing="1" width="90%">
	<tbody>
		<tr>
			<td class="beta" colspan="2">
			<div align="center">可选查询</div>
			</td>
			<td class="beta"></td>
			<td class="beta" colspan="3">
			<div align="center">已选查询</div>
			</td>
		</tr>
		<tr>
			<td class="beta" width="18">&nbsp;</td>
			<td class="table-content" height="26" valign="top" width="690">
			<div align="center"><select class="list"
				style="width: 380px; height: 250px;" multiple="multiple" size="12"
				name="noselected1" ondblclick="addElement()"><%=buffery.toString()%>
			</select></div>
			</td>
			<td class="beta" width="84">
			<div align="center"><input name="add" value="添加 ->"
				onclick="addElement()" class="btn" type="button"> <br>
			<br>
			<input name="remove" value="<- 删除" onclick="removeElement()"
				class="btn" type="button"></div>
			</td>
			<td class="table-content" height="26" valign="top" width="359">
			<div align="center"><select class="list"
				style="width: 380px; height: 250px;" multiple="multiple" size="12"
				name="selected1" ondblclick="removeElement()">
			</select></div>
			</td>
			<td class="beta" width="23">&nbsp;</td>
		</tr>
	</tbody>
</table>
<br>
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
			<div align="center"><select class="list"
				style="width: 380px; height: 250px;" multiple="multiple" size="12"
				name="noselected2" ondblclick="addElement2()"><%=bufferx.toString()%>
			</select></div>
			</td>
			<td class="beta" width="84">
			<div align="center"><input name="add" value="添加 ->"
				onclick="addElement2()" class="btn" type="button"> <br>
			<br>
			<input name="remove" value="<- 删除" onclick="removeElement2()"
				class="btn" type="button"></div>
			</td>
			<td class="table-content" height="26" valign="top" width="359">
			<div align="center"><select class="list"
				style="width: 380px; height: 250px;" multiple="multiple" size="12"
				name="selected2" ondblclick="removeElement2()">
			</select></div>
			</td>
			<td class="beta" width="23">&nbsp;</td>
		</tr>
	</tbody>
</table>
</fieldset>

<div align="center">
  <input value="确 定" class="btn btn-primary"
	name="submit252" type="button" onclick="javacsript:executeQuery();">
 <br />
</div>
</div>
</form>
</center>