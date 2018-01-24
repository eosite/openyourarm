<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%
 		StringBuffer bufferx = new StringBuffer();
		StringBuffer buffery = new StringBuffer();
		List tables = (List) request.getAttribute("tableModels");
		List selectedTableIds = (List) request.getAttribute("selectedTables");
		if(tables != null && tables.size() > 0){
			for(int j=0; j<tables.size(); j++){
				TableModel t = (TableModel) tables.get(j);
				if(selectedTableIds != null && selectedTableIds.contains(t.getTableName())){
					bufferx.append("\n<option value=\"").append(t.getTableName()).append("\">")
					   .append(t.getTitle()).append(" [")
					   .append(t.getTableName()).append("]</option>");	
				}
				buffery.append("\n<option value=\"").append(t.getTableName()).append("\">")
					   .append(t.getTitle()).append(" [")
					   .append(t.getTableName()).append("]</option>");		
			}
		}

%>
<!DOCTYPE html>
<html>
<title>数据表选择</title>
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
</script>


<script language="javascript">

  function submitRequest() {

    var parent_window = getOpener();
	var x_ids = parent_window.document.getElementById("${elementId}");
    var x_ids_name = parent_window.document.getElementById("${elementName}");

    var len= document.iForm.selected.length;
	var result = "";
	var names = "";
	for (var i=0;i<len;i++) {
      result = result + document.iForm.selected.options[i].value;
	  names = names + document.iForm.selected.options[i].text;
	  if(i < (len - 1)){
		  result = result + ",";
		  names = names + ",";
	   }
    }

	x_ids.value = result;
	x_ids_name.value = names;
     
	window.close();  
 
  }
</script>

<body >
<center>
<form name="iForm" class="x-form" method="post">
<div class="content-block" style="width: 100%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="选择数据表">&nbsp;
选择数据表</div>
<fieldset class="x-fieldset" style="width: 98%;">
<table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
	<tbody>
		<tr>
			<td class="beta" colspan="2">
			<div align="center">可选数据表</div>
			</td>
			<td class="beta"></td>
			<td class="beta" colspan="3">
			<div align="center">已选数据表</div>
			</td>
		</tr>
		<tr>
			<td class="beta" width="18">&nbsp;</td>
			<td class="table-content" height="26" valign="top" width="790">
			<div align="center"><select class="list"
				style="width: 350px; height: 300px;" multiple="multiple" size="12"
				name="noselected" ondblclick="addElement()"><%=buffery.toString()%>
			</select></div>
			</td>
			<td class="beta" width="145">
			<div align="center">&nbsp;&nbsp;<input name="add" value="添加->"
				onclick="addElement()" class="btn btnGray" type="button">&nbsp;&nbsp;<br>
			<br>&nbsp;&nbsp;
			<input name="remove" value="<-删除" onclick="removeElement()"
				class="btn btnGray" type="button">&nbsp;&nbsp;</div>
			</td>
			<td class="table-content" height="26" valign="top" width="359">
			<div align="center"><select class="list"
				style="width: 350px; height: 300px;" multiple="multiple" size="12"
				name="selected" ondblclick="removeElement()"><%=bufferx.toString()%>
			</select></div>
			</td>
			<td class="beta" width="23">&nbsp;</td>
		</tr>
	</tbody>
</table>
<br>
 
</fieldset>

<div align="center">
  <input value="确 定" class="btn btn-primary"
	name="submit252" type="button" onclick="javacsript:submitRequest();">
 <br />
</div>
</div>
</form>
</center>