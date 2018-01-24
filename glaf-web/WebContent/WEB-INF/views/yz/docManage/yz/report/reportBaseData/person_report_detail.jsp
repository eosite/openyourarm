<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.glaf.base.utils.WebUtil"%>
<%@page import="java.util.*"%>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员内页完成情况明细</title>
<%@include file="../../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{font-size: 12px}
	body table{background-color: #000;}
	body table tr td{background-color: #FFF;}
</style>
<script type="text/javascript">
function show(processId){
	var url = "<%=request.getContextPath()%>/mx/docManage/yz/report/reportBaseData?method=showTable&systemName=${systemName}&processId="+processId;
	var height = screen.availHeight-150;
	var width = screen.availWidth-10;
    var title = "查看表格";
	art.dialog.open(url, { height: height, width: width, title: title, scrollbars:"no" , lock: true });
}
</script>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div> 
	<table border="0" cellspacing="1" cellpadding="1" style="width:100%;">
		<tr class="list-title" align="center" style="height: 30px">
			<td width="50px">序号</td>
			<td>流程名称</td>
			<td width="100px">状态</td>
		</tr>
		
		<c:forEach items="${pager.results }" var="flowActiv" varStatus="indexStatus">
			<tr>
				<td class="td-no">${(pager.currentPageNo-1)*pager.pageSize+indexStatus.index+1 }</td>
				<td class="td-text"><a href="javascript:show('${flowActiv.processId_enc }');">${flowActiv.name }</a></td>
				<td class="td-no">${flowActiv.state==0?"未完成":"已完成" }</td>
			</tr>
		</c:forEach>
	</table>
	<table border="0" cellspacing="1" cellpadding="1" style="width:100%;background-color: #FFF;">
		<tr><td align="right">
		<jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
		    <jsp:param name="total" value="${pager.totalRecordCount}"/>
		    <jsp:param name="page_count" value="${pager.totalPageCount}"/>
		    <jsp:param name="page_size" value="${pager.pageSize}"/>
		    <jsp:param name="page_no" value="${pager.currentPageNo}"/>
		    <jsp:param name="url" value='<%=request.getContextPath()+"/mx/docManage/yz/report/reportBaseData" %>'/>
			<jsp:param name="params" value="<%=java.net.URLDecoder.decode(WebUtil.getQueryString(request)) %>"/>       
		</jsp:include> 
		</td></tr>
	</table>
</body>
</html>