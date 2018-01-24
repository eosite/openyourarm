<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.report.domain.*"%>
<%!private String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}%>
<%
	String htmlData = "";
	Report report = (Report) request.getAttribute("report");
	if (report != null && report.getTextContent() != null) {
		htmlData = htmlspecialchars(report.getTextContent());
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表定义</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/scripts/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/scripts/kindeditor/plugins/code/prettify.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script charset="UTF-8"
	src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-all-min.js"></script>
<script charset="UTF-8"
	src="<%=request.getContextPath()%>/scripts/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/scripts/kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">
           
		   /**
           var editor1 = null;

           KindEditor.ready(function(K) {
       			    editor1 = K.create('textarea[id="textContent"]', {
       				allowFileManager : true
       			});
       		});
			**/

		function initData(){
			// $('#iForm').form('load','<%=request.getContextPath()%>/rs/bi/report/view/${rowId}');
		}

		function saveData(){
			//document.getElementById("textContent").value=editor1.html();
             var params = jQuery("#iForm").formSerialize();
             jQuery.ajax({
             type: "POST",
             url: '<%=request.getContextPath()%>/rs/bi/report/saveReport',
             data: params,
             dataType:  'json',
             error: function(data){
	             alert('服务器处理错误！');
             },
             success: function(data){
	             if(data.message != null){
		             alert(data.message);
	             } else {
		             alert('操作成功完成！');
	             }
                 <c:if test="${empty report}">
	             parent.window.location.reload();
				</c:if>
             }
			 });
		}

		function saveAsData(){
             document.getElementById("id").value="";
             document.getElementById("reportId").value="";
             //document.getElementById("textContent").value=editor1.html();
             var params = jQuery("#iForm").formSerialize();
             jQuery.ajax({
             type: "POST",
             url: '<%=request.getContextPath()%>/rs/bi/report/saveReport',
             data: params,
             dataType:  'json',
             error: function(data){
	             alert('服务器处理错误！');
             },
             success: function(data){
	             if(data.message != null){
		             alert(data.message);
	             } else {
		             alert('操作成功完成！');
	             }
              }
		    });
		}

		function createReport(){
			 window.open('<%=request.getContextPath()%>/mx/bi/report/createReport?reportId=${report.id}');
		}

		function createHtmlReport(){
			 window.open('<%=request.getContextPath()%>/mx/bi/report/outputHtml?reportId=${report.id}');
		}

		function sendTestMail(){
            jQuery.ajax({
             type: "POST",
             url: '<%=request.getContextPath()%>/rs/bi/report/sendMail?reportId=${report.id}',
             dataType:  'json',
             error: function(data){
	             alert('服务器处理错误！');
             },
             success: function(data){
	             if(data.message != null){
		             alert(data.message);
	             } else {
		             alert('操作完成！');
	             }
             }
			});
		}


		function openQx(){
            var selected = jQuery("#queryIds").val();
            var link = '<%=request.getContextPath()%>/mx/dts/query/queryTree?elementId=queryIds&elementName=queryNames&nodeCode=report_category&selected='+selected;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			 }
			openWindow(link,self,x, y, 495, 480);
		}

		function openTable(){
            var selected = jQuery("#tansferTables").val();
            var link = '<%=request.getContextPath()%>/mx/bi/report/chooseTable?elementId=tansferTables&elementName=tableNames&nodeCode=report_category&selected='+selected;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			 }
			openWindow(link,self,x, y, 695, 480);
		}
		

        function openQx2(){
                var link = '<%=request.getContextPath()%>/mx/bi/report/chooseQuery?reportId=${report.id}&elementId=queryIds&elementName=queryNames';
				var x=100;
				var y=100;
				if(is_ie) {
					x=document.body.scrollLeft+event.clientX-event.offsetX-200;
					y=document.body.scrollTop+event.clientY-event.offsetY-200;
				 }
				openWindow(link,self,x, y, 695, 480);
		}


		function openChart(){
            var selected = jQuery("#chartIds").val();
            var link = '<%=request.getContextPath()%>/mx/bi/chart/chartTree?elementId=chartIds&elementName=chartNames&nodeCode=report_category&selected='+selected;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			 }
			openWindow(link,self,x, y, 495, 480);
		}

        function openChart2(){
                var link = '<%=request.getContextPath()%>/mx/bi/report/chooseChart?reportId=${report.id}&elementId=chartIds&elementName=chartNames';
				var x=100;
				var y=100;
				if(is_ie) {
					x=document.body.scrollLeft+event.clientX-event.offsetX-200;
					y=document.body.scrollTop+event.clientY-event.offsetY-200;
				 }
				openWindow(link,self,x, y, 695, 480);
		}

	    function openFile(){
			var link = "<%=request.getContextPath()%>/mx/bi/report/chooseFile?reportId=${report.id}&elementId=reportTemplate&elementName=reportTemplate";
			var x = 100;
			var y = 100;
			if (is_ie) {
				x = document.body.scrollLeft + event.clientX - event.offsetX - 200;
				y = document.body.scrollTop + event.clientY - event.offsetY - 200;
			}
			openWindow(link, self, x, y, 745, 480);
	    }
</script>
</head>
<body>
	<br>
	<div class="x_content_title">
		<img src="<%=request.getContextPath()%>/images/window.png" alt="报表定义">
		&nbsp;报表定义
	</div>
	<form id="iForm" name="iForm" method="post">
		<input type="hidden" id="id" name="id" value="${report.id}" /> <input
			type="hidden" id="nodeId" name="nodeId" value="${nodeId}" /> <input
			type="hidden" id="reportId" name="reportId" value="${report.id}" />
		<table class="easyui-form" style="width: 920px;" align="center">
			<tbody>
				<tr>
					<td width="180" height="28">名称</td>
					<td height="28"><input id="name" name="name"
						class="span7 x-text" type="text" value="${report.name}" size="80"></input>
					</td>
				</tr>

				<tr>
					<td height="28">标题</td>
					<td height="28"><input id="subject" name="subject"
						class="span7 x-text" type="text" value="${report.subject}"
						size="80"></input></td>
				</tr>

				<tr>
					<td height="28">报表名称</td>
					<td height="28"><input id="reportName" name="reportName"
						class="span7 x-text" type="text" value="${report.reportName}"
						size="80"></input></td>
				</tr>

				<tr>
					<td height="28">模板文件</td>
					<td height="28"><input id="reportTemplate"
						name="reportTemplate" class="span7 x-text" type="text"
						value="${report.reportTemplate}" size="80"
						ondblclick="javascript:openFile();"></input> &nbsp; <a href="#"
						onclick="javascript:openFile();"> <img
							src="<%=request.getContextPath()%>/images/report.gif" border="0">
					</a></td>
				</tr>

				<tr>
					<td height="28">报表标题日期</td>
					<td height="28"><input id="reportTitleDate"
						name="reportTitleDate" class="span7 x-text" type="text"
						value="${report.reportTitleDate}" size="80"></input></td>
				</tr>

				<tr>
					<td height="28">报表年月</td>
					<td height="28"><input id="reportMonth" name="reportMonth"
						class="span7 x-text" type="text" value="${report.reportMonth}"
						size="80"></input></td>
				</tr>

				<tr>
					<td height="28">报表年月日参数</td>
					<td height="28"><input id="reportDateYYYYMMDD"
						name="reportDateYYYYMMDD" class="span7 x-text" type="text"
						value="${report.reportDateYYYYMMDD}" size="80"></input></td>
				</tr>

				<tr>
					<td height="56">报表类型</td>
					<td height="56">
					<select id="type" name="type" class="span2"	style="height: 20px">
						<option value="jasper">JasperReport</option>
						<option value="jxls">JXLS</option>
						<option value="ftl">Freemarker</option>
					</select> 
					<script type="text/javascript">
						$('#type').val('${report.type}');
					</script> &nbsp;&nbsp;&nbsp;&nbsp;
					生成格式&nbsp; 
					<select id="reportFormat" name="reportFormat" class="span2" style="height: 20px">
						<option value="pdf">PDF</option>
						<option value="xls">Office Excel 97-2003</option>
						<option value="xlsx">Office Excel 2007</option>
						<option value="html">HTML</option>
					</select> 
					<script type="text/javascript">
						$('#reportFormat').val('${report.reportFormat}');
					</script>
					<br>
					（提示：如果模板格式为xls,输出格式应该是Office Excel 97-2003；模板格式为xlsx,输出格式应该是Office Excel 2007。）
				</tr>

				<tr>
					<td height="28">是否启用&nbsp;</td>
					<td height="28">
						<select id="enableFlag" name="enableFlag"
							class="span2" style="height: 20px">
								<option value="1">启用</option>
								<option value="0">不启用</option>
						</select> 
						<script type="text/javascript">
							$('#enableFlag').val('${report.enableFlag}');
						</script> 
						&nbsp;是否公开访问&nbsp;
                        <select id="publicFlag" name="publicFlag"
							class="span2" style="height: 20px">
								<option value="1">公开</option>
								<option value="0">私有</option>
						</select> 
						<script type="text/javascript">
							$('#publicFlag').val('${report.publicFlag}');
						</script> 
						&nbsp;（提示：公开访问则有无权限都可以访问，即不做权限控制。）
					</td>
				</tr>

				<!-- <tr>
					<td height="28">邮件标题</td>
					<td height="28"><input id="textTitle" name="textTitle"
						class="span7 x-text" type="text" value="${report.textTitle}"
						size="80"></input></td>
				</tr>

				<tr>
					<td height="28">邮件模板</td>
					<td height="28"><textarea id="textContent" name="textContent"
							class="x-textarea" rows="5" cols="58"
							style="width: 715px; height: 480px;"><%=htmlData%></textarea>
						<br> （支持Freemarker模板语言）</td>
				</tr>

				<tr>
					<td height="98">邮件接收人</td>
					<td height="98"><textarea id="mailRecipient"
							name="mailRecipient" class="x-textarea" rows="5" cols="58"
							style="width: 535px; height: 90px;">${report.mailRecipient}</textarea>
						<br>（多个邮件接收人以,分隔）</td>
				</tr> -->

				<tr>
					<td height="98">后置处理程序</td>
					<td height="98">
					<textarea id="postProcessor"
							  name="postProcessor" class="x-textarea" rows="5" cols="58"
							  style="width: 535px; height: 60px;">${report.postProcessor}</textarea>
					 <br>（提示：多个处理程序之间用半角的逗号","隔开。）
					</td>
				</tr>

				<tr>
					<td height="98">JSON格式参数</td>
					<td height="98"><textarea id="jsonParameter"
							name="jsonParameter" class="x-textarea" rows="5" cols="58"
							style="width: 535px; height: 90px;">${report.jsonParameter}</textarea>
					</td>
				</tr>

				<tr>
					<td height="98">抽取数据表</td>
					<td height="98"><input type="hidden" id="tansferTables"
						name="tansferTables" value="${report.tansferTables}"> <textarea
							type="textarea" id="tableNames" name="tableNames"
							value="${tableNames}" readonly="true" class="x-textarea"
							onclick="javascript:openTable();"
							style="width: 535px; height: 90px;">${report.tansferTables}</textarea>
						&nbsp; <a href="#" onclick="javascript:openTable();"> <img
							src="<%=request.getContextPath()%>/images/search_results.gif"
							border="0" title="如果报表需要多个查询数据集组成，请先建好查询数据再选择。">
					</a></td>
				</tr>

				<tr>
					<td height="98">数据集</td>
					<td height="98"><input type="hidden" id="queryIds"
						name="queryIds" value="${report.queryIds}"> <textarea
							type="textarea" id="queryNames" name="queryNames"
							value="${queryNames}" readonly="true" class="x-textarea"
							onclick="javascript:openQx();"
							style="width: 535px; height: 90px;">${queryNames}</textarea>
						&nbsp; <a href="#" onclick="javascript:openQx();"> <img
							src="<%=request.getContextPath()%>/images/search_results.gif"
							border="0" title="如果报表需要多个查询数据集组成，请先建好查询数据再选择。">
					</a></td>
				</tr>

				<tr>
					<td height="98">相关图表</td>
					<td height="98"><input type="hidden" id="chartIds"
						name="chartIds" value="${report.chartIds}"> <textarea
							type="textarea" id="chartNames" name="chartNames"
							value="${chartNames}" readonly="true" class="x-textarea"
							onclick="javascript:openChart();"
							style="width: 535px; height: 90px;">${chartNames}</textarea>
						&nbsp; <a href="#" onclick="javascript:openChart();"> <img
							src="<%=request.getContextPath()%>/images/process.gif" border="0">
					</a></td>
				</tr>

				<tr>
					<td>说明</td>
					<td>
					<br> 
					<%out.println("#{curr_yyyymmdd}");%>代表当天的日期，如
					<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMMDD()%>
              <br> 
					<%out.println("#{curr_yyyymm}");%>代表当天的月份，如<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMM()%>
					<br> 
					<%out.println("#{input_yyyymmdd}");%>
                    代表报表输入的日期，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMMDD()%>
					<br> 
					<%out.println("#{input_yyyymm}");%>
					代表报表输入的月份，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMM()%>
					</td>
				</tr>

				<tr>
					<td colspan="4" align="center">
					<br> <br> 
					<input type="button" name="save" value=" 保 存 " class=" btn btn-primary"
		                   onclick="javascript:saveData();" /> &nbsp;&nbsp; 
                    <c:if test="${!empty report}">
					<input type="button" name="saveAs" value=" 另 存 " class=" btn"
		                   onclick="javascript:saveAsData();" /> &nbsp;&nbsp; 
					<input type="button" name="createRpt" value=" 生成报表 " class=" btn"
		                   onclick="javascript:createReport();" /> &nbsp;&nbsp;
					<input type="button" name="outputHtml" value=" 输出HTML " class=" btn"
		                   onclick="javascript:createHtmlReport();" />
					<!-- <input type="button" name="sendMail" value=" 发送邮件 " class=" btn"
		                   onclick="javascript:sendTestMail();" /> &nbsp;&nbsp; -->
					</c:if>
					<!-- <input name="btn_back" type="button" value=" 返 回 " class=" btn"
		                   onclick="javascript:history.back(0);"> -->
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<br>
	<br>
	<br>
	<script type="text/javascript">
		initData();
	</script>
</body>
</html>