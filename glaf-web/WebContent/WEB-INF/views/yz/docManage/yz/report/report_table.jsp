<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    JSONArray reportSelData = new JSONArray();
    JSONObject jobject = new JSONObject();
	jobject.put("reportName", "分项工程资料进度汇总表");    
	jobject.put("reportCode", "6");
	jobject.put("selected", "true"); 
	reportSelData.put(jobject);
	jobject = new JSONObject();
	jobject.put("reportName", "单位工程资料进度汇总表");    
	jobject.put("reportCode", "4");
	reportSelData.put(jobject);
	jobject = new JSONObject();
	jobject.put("reportName", "合同资料进度汇总表");    
	jobject.put("reportCode", "2");
	reportSelData.put(jobject);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表生成</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{font-size: 12px}
</style>
<script type="text/javascript">
	jQuery(function(){
		jQuery("#projNameSelect").combobox({
		    valueField:'indexId',
		    textField:'projName',
		    data:<%=request.getAttribute("data")%>,
		    onSelect:function(rec){
		    	jQuery("#useDatabase").val(rec.indexId);
				jQuery("#projectName").val(rec.projName);
		    }
		});
		
		jQuery("#reportName").combobox({
			valueField:'reportCode',
			textField:'reportName',
			data:<%=reportSelData%>,
			onSelect:function(rec){
				if(rec.reportCode=="2")	{
					jQuery("#projNameSelect").combobox("setValue","");
					jQuery("#projNameSelect").combobox("disable");
				}else{
					jQuery("#projNameSelect").combobox("enable");
				}
			}
		});
	});
	
	function searchReport(){
		if(jQuery("#reportName").combobox("getValue")!="2" && jQuery("#projNameSelect").combobox("getValue")==""){
			jQuery.messager.alert("提示","请选择项目名称","info");
			return;
		}
		if(jQuery("#startDate").datebox("getValue")==""){
			jQuery.messager.alert("提示","请选择开始日期","info");
			return;
		}
		if(jQuery("#endDate").datebox("getValue")==""){
			jQuery.messager.alert("提示","请选择结束日期","info");
			return;
		}
		
		var reportType = jQuery("#reportName").combobox("getValue");
		var projName = jQuery("#projNameSelect").combobox("getText");
		if(reportType!="") reportType = parseInt(reportType);
		var tabName = "";
		switch(reportType){
			case 2:
				tabName = projName+"合同资料进度汇总表";
				break;
			case 4:
				tabName = projName+"单位工程资料进度汇总表";
				break;
			case 6:
				tabName = projName+"分项工程资料进度汇总表";
				break;
			default:
				tabName = "";
		}
		
		
		var params = "&panelHeight="+jQuery("#dataTab").tabs("options").height+"&reportName="+reportType+"&useDatabase="+jQuery("#useDatabase").val()+"&startDate="+jQuery("#startDate").datebox("getValue")+"&endDate="+jQuery("#endDate").datebox("getValue");
		var tabExists = jQuery("#dataTab").tabs("exists",tabName);
		if(tabExists){
			jQuery("#dataTab").tabs("select",tabName);
			var tab = jQuery("#dataTab").tabs("getSelected");
			jQuery("#dataTab").tabs("update",{
				tab:tab,
				options:{
					href:"<%=request.getContextPath()%>/mx/docManage/yz/report?method=searchReport"+params
				}
			});
		}else{
			jQuery("#dataTab").tabs("add",{
				title:tabName,
				href:"<%=request.getContextPath()%>/mx/docManage/yz/report?method=searchReport"+params,
				selected:true,
				closable:true
			});
		}
	}
</script>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',border:false" style="height: 34px"> 
    <div style="margin: 3px">
  		<input type="hidden"  id="useDatabase" name="useDatabase" value="default" />
  		<input type="hidden"  id="projectName" name="projectName" value="" />
  		<label>报表名称</label>
  		<select id="reportName" class="easyui-combobox" name="reportName" style="width:180px"></select>
		<label>项目名称</label>
		<select id="projNameSelect" class="easyui-combobox" name="projName" style="width:120px;"></select>
		<label>统计日期</label>
		<input id="startDate"  name="startDate" type="text" class="easyui-datebox" style="width:100px"></input>至 
		<input id="endDate" name="endDate"  type="text" class="easyui-datebox"  style="width:100px"></input> 
		<a id="searchBtn" href="#" onclick="javascript:searchReport();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
  </div>
  <div data-options="region:'center',border:true">
	<div id="dataTab"  class="easyui-tabs" data-options="tabPosition:'bottom',border:false,fit:true,loadingMessage:'正在加载数据，由于数据量较大，请耐心等待……'"></div>
  </div>
</div>
</body>
</html>