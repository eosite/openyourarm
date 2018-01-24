<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
   	JSONArray reportNameArr = new JSONArray();
   	JSONObject jobject = new JSONObject();
   	jobject.put("name", "混凝土强度分析表");
   	jobject.put("method", "shiyanReport");
   	reportNameArr.put(jobject);
   	
   	jobject = new JSONObject();
   	jobject.put("name", "水泥混凝土生产质量水平统计分析表");
   	jobject.put("method", "shiyanReport01");
   	jobject.put("selected",true);
   	reportNameArr.put(jobject);
   	
   	jobject = new JSONObject();
   	jobject.put("name", "水泥混凝土抗压强度检验频率表");
   	jobject.put("method", "shiyanReport02");
   	reportNameArr.put(jobject);
   	
   	jobject = new JSONObject();
   	jobject.put("name", "各墩混凝土分析图");
   	jobject.put("method", "shiyanReport03");
   	reportNameArr.put(jobject);
   	
   	jobject = new JSONObject();
   	jobject.put("name", "混凝土各月份分析图");
   	jobject.put("method", "shiyanReport04");
   	reportNameArr.put(jobject);
   	
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
		    	jQuery("#systemName").val(rec.indexId);
		    	clearParam();
		    },
		    onLoadSuccess:function(){
		    	jQuery("#projNameSelect").combobox("select","2");
		    }
		});
		jQuery("#reportNameSelect").combobox({
			valueField:'method',
		    textField:'name',
		    data:<%=reportNameArr%>,
		    onSelect:function(rec){
		    	jQuery("#method").val(rec.method);
		    	jQuery("#type").val(rec.method);
		    	clearParam();
		    }
		});
	});
	
	function searchData(){
		var systemName = jQuery("#systemName").val();
		var method = jQuery("#method").val();
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method="+method+"&systemName="+systemName;
		var p = jQuery(document.body).layout('panel','center');
		p.panel("refresh",url);
	}
	
	function gotoPage(pageno){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?page_no="+pageno+"&"+encodeURI(jQuery("#submitForm").serialize());
		var p = jQuery(document.body).layout('panel','center');
		p.panel("refresh",url);
	}
	
	function showSearch(){
		document.getElementById("method").value = "openSearchWindow";
		var url="<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?"+encodeURI(jQuery("#submitForm").serialize());
		art.dialog.open(url, {id:"basedata_search_dialog",height: 300, width: 400, title: "检索", scrollbars:"no" , lock: false });
	}
	
	function clearParam(){
		document.getElementById("indexName").value = "";
		document.getElementById("indexId").value = "";
		document.getElementById("startDate1").value = "";
		document.getElementById("endDate1").value = "";
		document.getElementById("startDate2").value = "";
		document.getElementById("endDate2").value = "";
		document.getElementById("mixProNo").value = "";
		document.getElementById("designStrRank").value = "";
	}
</script>
</head>
<body class="easyui-layout" style="margin:1px;">
<div style="margin:0;"></div>  
  <div data-options="region:'north',border:true" style="height:40px"> 
	  <div style="margin: 3px">
	  	 <form id="submitForm" method="post">
	  		 <input type="hidden"  id="systemName" name="systemName" value="default" />
	  		 <input type="hidden"  id="method" name="method" value="shiyanReport01" />
	  		 <input type="hidden"  id="type" name="type" value="shiyanReport01" />
	  		 <input type="hidden"  id="indexName" name="indexName" value="${indexName }" />
	  		 <input type="hidden"  id="indexId" name="indexId" value="${indexId }" />
	  		 <input type="hidden"  id="startDate1" name="startDate1" value="${startDate1 }" />
	  		 <input type="hidden"  id="endDate1" name="endDate1" value="${endDate1 }" />
	  		 <input type="hidden"  id="startDate2" name="startDate2" value="${startDate2 }" />
	  		 <input type="hidden"  id="endDate2" name="endDate2" value="${endDate2 }" />
	  		 <input type="hidden"  id="mixProNo" name="mixProNo" value="${mixProNo }" />
	  		 <input type="hidden"  id="designStrRank" name="designStrRank" value="${designStrRank }" />
  		 </form>
		 <label>项目名称</label>
		 <select id="projNameSelect" class="easyui-combobox" name="projName" data-options="required:true"  style="width:120px;"></select>
		 <label>报表名称</label>
		 <select id="reportNameSelect" class="easyui-combobox" name="reportName" data-options="required:true" style="width:220px"></select>
		 <input type="button" value="生成数据" class="button" onclick="javascript:searchData();" />
		 <input type="button" value="检索" class="button" onclick="javascript:showSearch();" />
	  </div>
  </div>
  <div data-options="region:'center',title:'统计结果',loadingMessage:'正在加载数据，由于数据量较大，请耐心等待……'"></div>
</body>
</html>