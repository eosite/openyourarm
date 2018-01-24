<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.jdbc.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ page import="org.apache.commons.lang3.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String tableId = request.getParameter("tableId");
	if(tableId == null){
		tableId="";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/js/i18n/grid.locale-en.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqueryui/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqgrid/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script type="text/javascript">
    ///mx/common/data/datagrid?tableId=20150331/admin-0000042&databaseId=13
    jQuery(document).ready(function () {
            jQuery("#jqGrid").jqGrid({
                url: '<%=request.getContextPath()%>/rs/isdp/cell/data/json?tableId_enc=<%=RequestUtils.encodeString(tableId)%>&databaseId=${databaseId}&gridType=jqgrid',
                datatype: "json",
				type: "POST",
				method: 'POST',
                colModel: [
                    { label: '序号', name: 'startIndex', width: 75 }
					<c:forEach items="${columns}" var="column">
                    ,{ 
					    label: '${column.title}', 
					    name: '${column.name}', 
					    <c:if test="${column.primaryKey}">
					    key: true, 
					    </c:if>
					    width:${column.length}
					}
				    </c:forEach>
                ],
				loadonce: true,
                width: ${width},
                height: ${height},
                rowNum: 100,
				caption: '${subject}'
			    <c:if test="${pager eq 'true'}">
                ,pager: "#jqGridPager"
				</c:if>
            });
        });
	 
</script>
</head>
<body>
<center>
<c:choose>
<c:when test="${chart eq 'jfreechart'}">
<div id="chart"></div>
<script type="text/javascript">
	jQuery("#chart").load("<%=request.getContextPath()%>/mx/bi/chart/showChart?name_enc=<%=RequestUtils.encodeString(tableId)%>&mapping=${mapping}&chooseThemes=${chooseThemes}");
</script>
</c:when>
<c:when test="${chart eq 'highcharts'}">
<div id="chart"></div>
<script type="text/javascript">
    jQuery("#chart").load("<%=request.getContextPath()%>/mx/bi/chart/highcharts/showChart?name_enc=<%=RequestUtils.encodeString(tableId)%>&mapping=${mapping}&chooseThemes=${chooseThemes}");
</script>
</c:when>
<c:when test="${chart eq 'kendo'}">
<div id="chart"></div>
<script type="text/javascript">
    jQuery("#chart").load("<%=request.getContextPath()%>/mx/bi/chart/kendo/showChart?name_enc=<%=RequestUtils.encodeString(tableId)%>&mapping=${mapping}&chooseThemes=${chooseThemes}");
</script>
</c:when>
</c:choose>
<table id="jqGrid"></table>
<c:if test="${pager eq 'true'}">
<div id="jqGridPager"></div>
</c:if>
</center>
</body>
</html>