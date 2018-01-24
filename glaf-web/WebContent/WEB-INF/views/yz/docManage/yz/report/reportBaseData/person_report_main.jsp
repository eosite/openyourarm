<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    JSONArray jsonArray = (JSONArray)request.getAttribute("projMuiprojListArray");
    //System.out.println(jsonArray.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表生成</title>
<%@include file="../../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{font-size: 12px}
	body table{background-color: #000;}
	body table tr td{background-color: #FFF;}
</style>
<script type="text/javascript">
	jQuery(function(){
		jQuery("#projNameSelect").combobox({
		    valueField:'indexId',
		    textField:'projName',
		    data:<%=request.getAttribute("projMuiprojListArray")%>,
		    onSelect:function(rec){
		    	jQuery("#useDatabase").val(rec.indexId);
		    }
		});
	});
	
	function searchData(){
		var useDatabase = jQuery("#useDatabase").val();
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=personReport&systemName="+useDatabase;
		var p = jQuery(document.body).layout('panel','center');
		p.panel("refresh",url);
	}
	
	function gotoPage(pageno){
		var useDatabase = jQuery("#useDatabase").val();
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=personReport&systemName="+useDatabase+"&page_no="+pageno;
		var p = jQuery(document.body).layout('panel','center');
		p.panel("refresh",url);
	}
	
	function showDetail(type,netRoleId,userId){
		var useDatabase = jQuery("#useDatabase").val();
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=personReportDetail&systemName="+useDatabase+"&type="+type+"&netRoleId="+netRoleId+"&userId="+userId;
		var width = window.screen.width;
		var height = window.screen.height;
		window.showModalDialog(url,"","dialogHeight="+height+";dialogWidth="+width+";center=true;status=no;scroll=yes");
	}
</script>
</head>
<body class="easyui-layout" style="margin:1px;">
<div style="margin:0;"></div>  
  <div data-options="region:'north',border:true" style="height:40px"> 
	  <div style="margin: 3px">
  		 <input type="hidden"  id="useDatabase" name="useDatabase" value="default" />
		 <label>项目名称</label>
		 <select id="projNameSelect" class="easyui-combobox" name="projName" style="width:120px;"></select>
		 <a id="searchBtn" href="#" onclick="javascript:searchData();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	  </div>
  </div>
  <div data-options="region:'center',title:'统计结果',loadingMessage:'正在加载数据，由于数据量较大，请耐心等待……'"></div>
</body>
</html>