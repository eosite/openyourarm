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
<%@include file="../ProjectScheduleQuery/script.jsp" %>
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
	
	function showData(){
		var reportType = jQuery("#reportName").combobox("getValue");
		if(reportType!="") reportType = parseInt(reportType);
		var tabName = "";
		var sheet = "sheet001.htm";
		switch(reportType){
			case 2:
				tabName = "合同资料进度汇总表";
				break;
			case 4:
				tabName = "单位工程资料进度汇总表";
				sheet = "sheet002.htm";
				break;
			case 6:
				tabName = "分项工程资料进度汇总表";
				sheet = "sheet001.htm";
				break;
			default:
				tabName = "";
		}
		
		if(jQuery("#reportName").combobox("getValue")!="2" && jQuery("#projNameSelect").combobox("getValue")==""){
			jQuery.messager.alert("提示","请选择项目名称","info");
			return ;
		}
		if(jQuery("#startDate").datebox("getValue")==""){
			jQuery.messager.alert("提示","请选择开始日期","info");
			return ;
		}
		if(jQuery("#endDate").datebox("getValue")==""){
			jQuery.messager.alert("提示","请选择结束日期","info");
			return ;
		}
		document.getElementById("dataFrame").src="${pageContext.request.contextPath}/data/"+jQuery("#projNameSelect").combobox("getText")+"/"+sheet;
	}
</script>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',border:true" style="height:40px"> 
	  <div style="margin: 3px">
	  	<form id="submitForm" method="post">
	  		<input type="hidden"  id="useDatabase" name="useDatabase" value="default" />
	  		<input type="hidden"  id="projectName" name="projectName" value="" />
	  		<label>报名</label>
	  		<select id="reportName" class="easyui-combobox" name="reportName" style="width:180px"></select>
			<label>项目名称</label>
			<select id="projNameSelect" class="easyui-combobox" name="projName" style="width:60px;"></select>
			<label>统计日期</label>
			<input id="startDate"  name="startDate" type="text" class="easyui-datebox" style="width:100px"></input>至 
			<input id="endDate" name="endDate"  type="text" class="easyui-datebox"  style="width:100px"></input> 
			<a id="searchBtn" href="#" onclick="javascript:showData();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		 </form>
		</div>
  </div>
  <div data-options="region:'center',border:true">
  	<iframe id="dataFrame" width="100%" height="100%"  style="border: 0"></iframe>
  </div>
</div>
</body>
</html>