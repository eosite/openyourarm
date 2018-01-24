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
<title>数据列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
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
                    "url": "<%=request.getContextPath()%>/rs/datamgr/sql/execution/kendoui/data?sqlDefId=${sqlDefId}&databaseId=${databaseId}&gridType=jqgrid&x_complex_query=${x_complex_query}&rows=${rows}"
                }
            },
            "pageSize": ${rows},
            "serverPaging": true
        },
        "height": ${height},
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500, 1000],
                       "buttonCount": 10
                     },
		"selectable": "single",
        "columns": [
			{
            "field": "startIndex",
            "title": "序号",
            "width": "80px"
            }	
			<c:forEach items="${columns}" var="column">
			,{
            "field": "${column.name}",
            "title": "${column.title}",
            "width": "${column.width}px"
            }		
			</c:forEach>
	    ],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });

 </script>
</head>
<body>
<div id="grid"></div>
</body>
</html>