<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SQL执行历史</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">

<script type="text/javascript">
	
  jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "id": {
                            "type": "string"
                        },
                        "startIndex": {
                            "type": "number"
                        }
                    }
                },
                "data": "rows"
            },
            "transport": {
                "parameterMap": function(options) {
                    return JSON.stringify(options);
                },
                "read": {
		            "contentType": "application/json",
                    "type": "POST",
                    "url": "<%=request.getContextPath()%>/rs/datamgr/sql/result/data?sqlDefId=${sqlDefinition.id}&databaseId=${databaseId}&jobNo=${jobNo}"
                }
            },
            "pageSize": 10,
            "serverPaging": true
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 10
                     },
		"selectable": "single",
        "columns": [
			{
            "field": "db_title",
            "title": "库名",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
			{
            "field": "sql_title",
            "title": "主题",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
            {
            "field": "operation",
            "title": "操作",
            "width": "80px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
		    {
            "field": "count",
            "title": "记录数",
            "width": "80px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },		
			{
            "field": "createBy",
            "title": "执行者",
            "width": "80px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
            {
            "field": "createTime_datetime",
            "title": "执行日期",
            "width": "130px",
            "lockable": false,
			"align": "center",
			"format": "{0: yyyy-MM-dd HH:mm:ss}",
			"filterable": {
              "ui": "datetimepicker"  
              }
            }],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });

  function changeDB(){
    var databaseId = document.getElementById("databaseId").value;
    location.href="<%=request.getContextPath()%>/mx/datamgr/sql/execution/showHistory?databaseId="+databaseId+"&sqlDefId=${sqlDefinition.id}&jobNo=${jobNo}";
    }

   function openChat(){
	  window.open("<%=request.getContextPath()%>/mx/bi/chart/chart?chartId=179551&sqlDefId=${sqlDefinition.id}&databaseId=${databaseId}");
   }

   function exportXls(){
	var databaseId = document.getElementById("databaseId").value;
	<c:choose>
		<c:when test="${!empty sqlDefinition}">
		   location.href="<%=request.getContextPath()%>/mx/datamgr/sql/execution/exportXls?sqlDefId=${sqlDefinition.id}&databaseId="+databaseId;
		</c:when>
	    <c:otherwise>
           location.href="<%=request.getContextPath()%>/mx/datamgr/sql/execution/exportXls?databaseId="+databaseId;
		</c:otherwise>
	</c:choose>
   }

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="SQL执行历史">&nbsp;
    SQL执行历史
 </div>
<br>
&nbsp;切换数据库&nbsp;<select id="databaseId" name="databaseId" onchange="javascript:changeDB();">
<option value="">----请选择----</option>    
<c:forEach items="${databases}" var="item">
<option value="${item.id}">${item.title}</option>     
</c:forEach>
<script type="text/javascript">
    document.getElementById("databaseId").value="${databaseId}";
</script>
</select>
<c:if test="${!empty databaseId}">
<c:if test="${!empty sqlDefinition}">
&nbsp; <input type="button" value="数据变化曲线图" onclick="javascript:openChat();" class="btnGray">
</c:if>
&nbsp; <input type="button" value="导出数据" onclick="javascript:exportXls();" class="btnGray">
</c:if>

<div id="grid"></div>
</div>     
</body>
</html>