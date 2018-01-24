<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    JSONArray jsonArray = (JSONArray)request.getAttribute("projMuiprojListArray");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实际进度报表</title>
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
		    	jQuery("#indexId").val("");
		    	clear(1);
		    	
		    	jQuery("#useDatabase").val(rec.indexId);
		    	jQuery("#unitSelect").combobox("reload","<%=request.getContextPath()%>/rs/reportBaseData/getTreeJSON?systemName="+rec.indexId);
		    }
		});
		

		jQuery("#unitSelect").combobox({
			valueField:'id',
			textField:'text',
    		onSelect:function(rec){
    			clear(2);
    			jQuery("#indexId").val(rec.id);
    			var systemName = jQuery("#useDatabase").val();
    			jQuery("#subSectionSelect").combobox("reload","<%=request.getContextPath()%>/rs/reportBaseData/getTreeJSON?systemName="+systemName+"&pid="+rec.id);
    		}
    	});
		
		jQuery("#subSectionSelect").combobox({
			valueField:'id',
			textField:'text',
    		onSelect:function(rec){
    			clear(3);
    			jQuery("#indexId").val(rec.id);
    			var systemName = jQuery("#useDatabase").val();
    			jQuery("#subItemSelect").combobox("reload","<%=request.getContextPath()%>/rs/reportBaseData/getTreeJSON?systemName="+systemName+"&pid="+rec.id);
    		}
		});
		
		jQuery("#subItemSelect").combobox({
			valueField:'id',
			textField:'text',
    		onSelect:function(rec){
    			jQuery("#indexId").val(rec.id);
    		}
		});
	});
	
	function clear(clearFlag){
		if(clearFlag==1){
			jQuery("#unitSelect").combobox("clear");
			jQuery("#unitSelect").combobox("loadData",[]);
		}
		if(clearFlag==1 || clearFlag==2){
			jQuery("#subSectionSelect").combobox("clear");
			jQuery("#subSectionSelect").combobox("loadData",[]);
		}
		if(clearFlag==1 || clearFlag==2 || clearFlag==3){
			jQuery("#subItemSelect").combobox("clear");
			jQuery("#subItemSelect").combobox("loadData",[]);
		}
		
		
    	
	}
	
	function searchData(){
		var useDatabase = jQuery("#useDatabase").val();
		var indexId = jQuery("#indexId").val();
		
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=actualReport&systemName="+useDatabase+"&treepinfoIndexId="+indexId;
		var p = jQuery(document.body).layout('panel','center');
		p.panel("refresh",url);
	}
	
	function gotoPage(pageno){
		var useDatabase = jQuery("#useDatabase").val();
		var indexId = jQuery("#indexId").val();
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=actualReport&systemName="+useDatabase+"&treepinfoIndexId="+indexId+"&page_no="+pageno;
		var p = jQuery(document.body).layout('panel','center');
		p.panel("refresh",url);
	}
	
	function showDetail(type,indexId){
		var useDatabase = jQuery("#useDatabase").val();
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=actualReportDetail&systemName="+useDatabase+"&type="+type+"&treepinfoIndexId="+indexId;
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
  		 <input type="hidden" id="useDatabase" name="useDatabase" value="default" />
  		 <input type="hidden" id="indexId" name="indexId" value="" />
		 <label>项目名称</label>
		 <input id="projNameSelect" class="easyui-combobox" name="projName" style="width:80px;" />
		 <label>单位工程</label>
		 <input id="unitSelect" class="easyui-combobox" name="unit" style="width:130px;" />
		 <label>分部</label>
		 <input id="subSectionSelect" class="easyui-combobox" name="subSection" style="width:220px;" />
		 <label>分项</label>
		 <input id="subItemSelect" class="easyui-combobox" name="subItem" style="width:200px;" />
		 <a id="searchBtn" href="#" onclick="javascript:searchData();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	  </div>
  </div>
  <div data-options="region:'center',title:'统计结果',loadingMessage:'正在加载数据，由于数据量较大，请耐心等待……'"></div>
</body>
</html>