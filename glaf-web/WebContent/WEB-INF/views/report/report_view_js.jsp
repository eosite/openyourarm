<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<title>报表阅读器</title>
<link href="${contextPath}/scripts/stireport/css/stimulsoft.viewer.office2010.css" rel="stylesheet">
<script src="${contextPath}/scripts/stireport/js/stimulsoft.reports.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/stireport/js/stimulsoft.viewer.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
var mappingId='${param.mappingId}';
var params='${param.params}';
var paramsJson=params!=''?$.parseJSON(params):{};
Stimulsoft.Base.Localization.StiLocalization.setLocalizationFile("${contextPath}/scripts/stireport/Localizations/zh-CHS.xml");
var report = new Stimulsoft.Report.StiReport();
$.ajax({
		url : "${contextPath}/rs/reportTemplate/"+mappingId+"/getdata",
		type : "post",
		async : false,
		dataType : "json",
		contentType:"application/x-www-form-urlencoded",
		data : {
			params:params
		},
		success : function(data) {
			if (data) {
				var dataSet,jsonData,dataTableCode;
				for(var datasetCode in data){
					//获取datasetName
					datasetName=datasetCode.split(".")[0];
					dataTableCode=datasetCode.split(".").length==2?datasetCode.split(".")[1]:datasetName;
					dataSet = new Stimulsoft.System.Data.DataSet(datasetName);
				    jsonData={};
					jsonData[dataTableCode]=data[datasetCode];
					dataSet.readJson(jsonData);
					report.regData(datasetName, datasetName, dataSet);
				}
			}
		},
		error : function() {
			alert("获取报表数据异常！");
		}

});
//var dataSet = new Stimulsoft.System.Data.DataSet("dataset1");
//var jsonData={"数据源1":[{"sex":"张三","name":"男"},{"sex":"张三","name":"男"}]};
//dataSet.readJson(jsonData);
//var report = new Stimulsoft.Report.StiReport();
//report.regData(dataSet.dataSetName, "数据源1", dataSet);
//report.loadFile("${server_scheme}://${header['host']}/glaf-report/view/reportTemplate/${templateId}/data");
//使用nginx代理后不能取得真实的端口，修改成相对路径
report.loadFile("../../../../../../glaf-report/view/reportTemplate/${templateId}/data");
var options = new Stimulsoft.Viewer.StiViewerOptions;
options.toolbar.showAboutButton = false;
options.exports.showExportToPdf = true;
options.exports.ShowExportToWord2007 = true;
options.localization="zh-CHS";
var viewer = new Stimulsoft.Viewer.StiViewer(options, "StiViewer",
		false);
viewer.report = report;
viewer.renderHtml("viewerContent");
</script>
<div id="viewerContent"></div>
</body>
</html>