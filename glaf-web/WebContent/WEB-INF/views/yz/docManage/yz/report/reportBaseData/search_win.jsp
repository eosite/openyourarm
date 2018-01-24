<%@page contentType="text/html; charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择WBS工程节点</title>
<%@include file="../../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	
	function search(){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=search&systemName=${systemName}&type=${type}&"+encodeURI(jQuery("#submitForm").serialize());
		var p = parent.jQuery(parent.document.body).layout('panel','center');
		p.panel("refresh",url);
		parent.document.getElementById("indexName").value = jQuery("#indexName").val();
		parent.document.getElementById("indexId").value = jQuery("#indexId").val();
		parent.document.getElementById("startDate1").value = jQuery("#startDate1").datebox("getValue");
		parent.document.getElementById("endDate1").value = jQuery("#endDate1").datebox("getValue");
		parent.document.getElementById("startDate2").value = jQuery("#startDate2").datebox("getValue");
		parent.document.getElementById("endDate2").value = jQuery("#endDate2").datebox("getValue");
		parent.document.getElementById("mixProNo").value = jQuery("#mixProNo").val();
		parent.document.getElementById("designStrRank").value = jQuery("#designStrRank").val();
		parent.document.getElementById("method").value = jQuery("#type").val();
		art.dialog.close('basedata_search_dialog');
	}
	
	function selectWBS(){
		var url="<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=openSelectWBSWindow&systemName=${systemName}";
		var returnValue = window.showModalDialog(url,"选择工程节点","dialogHeight=400,dialogWidth=300,center=true,resizable=no,status=no,scroll=no");
		jQuery("#indexId").val(returnValue[0]);
		jQuery("#indexName").val(returnValue[1]);
	}
	
	function resetForm(){
		document.getElementById("indexName").value = "";
		document.getElementById("indexId").value = "";
		jQuery("#startDate1").datebox("setValue","");
		jQuery("#endDate1").datebox("setValue","");
		jQuery("#startDate2").datebox("setValue","");
		jQuery("#endDate2").datebox("setValue","");
		document.getElementById("mixProNo").value = "";
		document.getElementById("designStrRank").value = "";
	}
	
	function closeWin(){
		parent.document.getElementById("method").value = jQuery("#type").val();
		art.dialog.close('basedata_search_dialog');
	}
</script>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div>
	<form id="submitForm" method="post">
	<table id="formTable" width="98%" border="0" align="center" cellpadding="5" cellspacing="0" class="box">
		<input type="hidden"  id="type" name="type" value="${type }" />
		<tr>
			<td class="input-box" style="width:80px">工程节点</td>
			<td>
				<input type="text" id="indexName" name="indexName" style="width:210px" onclick="selectWBS();" value="${indexName }" />
				<input type="hidden" id="indexId" name="indexId" value="${indexId }" />
			</td>
		</tr>
		<tr>
			<td class="input-box">成型日期</td>
			<td>
				<input id="startDate1" name="startDate1" class="easyui-datebox" style="width:100px" value="${startDate1 }"/>
				至
				<input id="endDate1" name="endDate1" class="easyui-datebox" style="width:100px" value="${endDate1 }"/>
			</td>
		</tr>
		<tr>
			<td class="input-box">试验日期</td>
			<td>
				<input id="startDate2" name="startDate2" class="easyui-datebox" style="width:100px" value="${startDate2 }"/>
				至
				<input id="endDate2" name="endDate2" class="easyui-datebox" style="width:100px" value="${endDate2 }"/>
			</td>
		</tr>
		<tr>
			<td class="input-box">配合比编号</td>
			<td><input type="text" id="mixProNo" name="mixProNo" style="width:210px" value="${mixProNo }"/></td>
		</tr>
		<tr>
			<td class="input-box">设计强度等级</td>
			<td><input type="text" id="designStrRank" name="designStrRank" style="width:210px" value="${designStrRank }"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" id="selectBtn" value="确定" onclick="javascript:search();" class="button"/>
				<input type="button" id="selectBtn" value="重置" onclick="javascript:resetForm();" class="button"/>
				<input type="button" id="cancelBtn" value="取消" onclick="javascript:closeWin();" class="button"/>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>