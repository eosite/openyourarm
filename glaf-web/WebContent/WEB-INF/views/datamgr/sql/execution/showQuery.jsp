<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.jdbc.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ page import="org.apache.commons.lang3.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String sql = request.getParameter("sql");
	String actionType = request.getParameter("actionType");
	if(sql == null){
		sql="";
	}
	//SysKey sysKey = (SysKey)request.getAttribute("sysKey");
	//String sql2 = DESUtils.encode(sysKey.getData(), sql.getBytes("UTF-8"));
	//SQLFormatter f = new SQLFormatter();
	//sql = f.format(sql);
	String sql2 = RequestUtils.encodeString(sql);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实时查询</title>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/js/i18n/grid.locale-en.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqueryui/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqgrid/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script type="text/javascript">

    jQuery(document).ready(function () {
            jQuery("#jqGrid").jqGrid({
                url: '<%=request.getContextPath()%>/rs/sys/sql/execution/data?sql=<%=sql2%>&databaseId=${databaseId}&gridType=jqgrid&rows=${rows}&ikey=ikey',
                datatype: "json",
				type: "POST",
				method: 'POST',
                colModel: [
                    { label: 'Index', name: 'startIndex', width: 75 }
					<c:forEach items="${columns}" var="column">
                    ,{ 
					    label: '${column.columnLabel}', 
					    name: '${column.columnLabel}', 
					    <c:if test="${column.primaryKey}">
					    key: true, 
					    </c:if>
					    width:150
					}
				    </c:forEach>
                ],
                width: ${width},
                height: 400,
                rowNum: ${rows},
                pager: "#jqGridPager"
            });
        });

    function doQuery(actionType){
		var databaseId = document.getElementById("databaseId").value;
		var sql = document.getElementById("sql").value;
		document.iForm.target="_self";
        document.iForm.submit();
    }

	function doQueryWin(actionType){
		var databaseId = document.getElementById("databaseId").value;
		var sql = document.getElementById("sql").value;
		document.iForm.target="_blank";
        document.iForm.submit();
    }
	 
</script>
</head>
<body style="margin:20px;">
<form method="post" id="iForm" name="iForm" action="<%=request.getContextPath()%>/mx/datamgr/sql/execution/showQuery">
<table style="width:95%;height:250px" align="center">
<tbody>
<tr>
<td height="40">
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="实时查询"> &nbsp;实时查询
</div>
</td>
</tr>
<tr>
 <td>
    <textarea id="sql" name="sql" rows="12" cols="88"  class="x-textarea" style="width:1050px;height:320px;"><%=sql%></textarea>
</td>
</tr>
<tr>
 <td height="40">
	&nbsp;切换数据库&nbsp;
	<select id="databaseId" name="databaseId" onchange="javascript:doQuery('');">
	<option value="">----请选择----</option>    
	<c:forEach items="${databases}" var="item">
	<option value="${item.id}">${item.title}</option>     
	</c:forEach>
	</select>
	<script type="text/javascript">
		 document.getElementById("databaseId").value="${databaseId}";
	</script>
	&nbsp;记录条数&nbsp;
    <select id="rows" name="rows" >
	   <option value="10">10</option>
	   <option value="15">15</option>
	   <option value="20">20</option>
	   <option value="25">25</option>
	   <option value="50">50</option>
	   <option value="100">100</option>  
	   <option value="200">200</option> 
	   <option value="500">500</option> 
	   <option value="1000">1000</option> 
	</select>
	<script type="text/javascript">
		 document.getElementById("rows").value="${rows}";
	</script>
    &nbsp;&nbsp;
    <input type="button" value="确定" class="btnGray" onclick="javascript:doQuery('query');"> 
	<input type="button" value="新窗口打开" class="btnGray" onclick="javascript:doQueryWin('query');">
	&nbsp;&nbsp;(提示：本查询最多返回1000条记录，为减少网络流量，请尽量缩小查询范围。)
 <td>
</tr>
</tbody>
</table>
</form>
<br>
<div style="margin:40px;">
<table id="jqGrid" style="width:95%;"></table>
<div id="jqGridPager"></div>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
</body>
</html>