<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>各标段分部进度表</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
</head>
<body  style="margin:1px;">  
	<table border="0" cellspacing="1" cellpadding="0" class="list-box" style="width:100%">
	  <tr class="list-title"> 
	    <td width="2%" align="center" rowspan="2">序<br/>号</td>
	    <td width="10%" align="center" rowspan="2">单位工程名称</td>
	    <c:if test="${isMonth }">
	    <td width="8%" align="center" colspan="2">本月启动计划</td>
	    <td width="12%" align="center" colspan="3">本月已填写</td>
	    <td width="12%" align="center" colspan="3">本月已报验</td>
	    <td width="12%" align="center" colspan="3">本月形成文件</td>
	    </c:if>
	     <c:if test="${!isMonth }">
	    <td width="8%" align="center" colspan="2">本年启动计划</td>
	    <td width="12%" align="center" colspan="3">本年已填写</td>
	    <td width="12%" align="center" colspan="3">本年已报验</td>
	    <td width="12%" align="center" colspan="3">本年形成文件</td>
	    </c:if>
	    <td width="8%" align="center" colspan="2">累计启动计划</td>
	    <td width="12%" align="center" colspan="3">累计已填写</td>
	    <td width="12%" align="center" colspan="3">累计已报验</td>
	    <td width="12%" align="center" colspan="3">累计形成文件</td>
	  </tr>
	  <tr class="list-title">
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  	<td align="center" >份</td>
	  	<td align="center" >页</td>
	  	<td align="center" >%</td>
	  </tr>
	  <c:forEach items="${modelList }" var="model" varStatus="indexStatus">
		  <tr > 
		    <td class="td-no">${indexStatus.index+1 }</td>
		    <td class="td-text" title="${indexNameMap[model.unitId] }">${indexNameMap[model.unitId] }</td>
		    <td class="td-no">${model.monthCell1Sum }</td>
		    <td class="td-no">${model.monthCell1PageSum }</td>
		    <td class="td-no">${model.monthCell2Sum }</td>
		    <td class="td-no">${model.monthCell2PageSum }</td>
		    <c:set var="percent" value="${model.monthCell1Sum==0?0:model.monthCell2Sum*100/model.monthCell1Sum }" />
		    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }" /></td>
		    <td class="td-no">${model.monthCell3Sum }</td>
		    <td class="td-no">${model.monthCell3PageSum }</td>
		    <c:set var="percent" value="${model.monthCell1Sum==0?0:model.monthCell3Sum*100/model.monthCell1Sum }" />
		    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }" /></td>
		    <td class="td-no">${model.monthIntpFile1Sum }</td>
		    <td class="td-no">${model.monthIntpFile1PageSum }</td>
		    <c:set var="percent" value="${model.monthCell1Sum==0?0:model.monthIntpFile1Sum*100/model.monthCell1Sum }" />
		    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }" /></td>
		    <td class="td-no">${model.cell1Sum }</td>
		    <td class="td-no">${model.cell1PageSum }</td>
		    <td class="td-no">${model.cell2Sum }</td>
		    <td class="td-no">${model.cell2PageSum }</td>
		    <c:set var="percent" value="${model.cell1Sum==0?0:model.cell2Sum*100/model.cell1Sum }" />
		    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }" /></td>
		    <td class="td-no">${model.cell3Sum }</td>
		    <td class="td-no">${model.cell3PageSum }</td>
		    <c:set var="percent" value="${model.cell1Sum==0?0:model.cell3Sum*100/model.cell1Sum }" />
		    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }" /></td>
		    <td class="td-no">${model.intpFile1Sum }</td>
		    <td class="td-no">${model.intpFile1PageSum }</td>
		    <c:set var="percent" value="${model.cell1Sum==0?0:model.intpFile1Sum*100/model.cell1Sum }" />
		    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }" /></td>
		  </tr>
	  </c:forEach>
	</table>
</body>
</html>