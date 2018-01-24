<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表生成</title>
<%@include file="../inc/script.jsp" %>
<style type="text/css">
.data{ background:#000;}
.data tr td{ background:#fff;}
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
		
		jQuery('#dataTab').tabs({   
		    border:false,
		    fit:true,
		    onClose:function(title){
		    	var tabs = jQuery("#dataTab").tabs("tabs");
		    	if(tabs.length==0){
		    		//jQuery('#dataTab').hide();
		    	}
		    }
		}); 
	});
	
	function search(){
		var projName = jQuery("#projNameSelect").combobox("getText");
		var tabName = projName+"单位工程资料进度汇总图表";
		if(jQuery("#projNameSelect").combobox("getValue")==""){
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
		
		var params = "&useDatabase="+jQuery("#useDatabase").val()+"&startDate="+jQuery("#startDate").datebox("getValue")+"&endDate="+jQuery("#endDate").datebox("getValue");
		var tabExists = jQuery("#dataTab").tabs("exists",tabName);
		if(tabExists){
			jQuery("#dataTab").tabs("select",tabName);
			var tab = jQuery("#dataTab").tabs("getSelected");
			jQuery("#dataTab").tabs("update",{
				tab:tab,
				options:{
					href:"<%=request.getContextPath()%>/mx/docManage/yz/report?method=searchGraphical"+params
				}
			});
		}else{
			jQuery("#dataTab").tabs("add",{
				title:tabName,
				href:"<%=request.getContextPath()%>/mx/docManage/yz/report?method=searchGraphical"+params,
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
  <div data-options="region:'north',border:true" style="height:40px"> 
	  <div style="margin: 3px">
	  		<input type="hidden"  id="useDatabase" name="useDatabase" value="default" />
	  		<input type="hidden"  id="projectName" name="projectName" value="" />
			<label>项目名称</label>
			<select id="projNameSelect" class="easyui-combobox" name="projName" style="width:60px;"></select>
			<label>统计日期</label>
			<input id="startDate"  name="startDate" type="text" class="easyui-datebox" style="width:100px" value=""></input>至 
			<input id="endDate" name="endDate"  type="text" class="easyui-datebox"  style="width:100px" value=""></input> 
			<a id="searchBtn" href="javascript:search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
  </div>
  <div data-options="region:'center',border:true">
	<div id="dataTab"  class="easyui-tabs" ></div>
  </div>
</div>
</body>
</html>