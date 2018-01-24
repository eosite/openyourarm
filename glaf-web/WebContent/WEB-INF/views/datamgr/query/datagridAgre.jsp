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
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.default.min.css" rel="stylesheet" />

    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/kendo.all.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/kendoui_zh_CN.js"></script> 
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
                "read": {
                    "type": "POST",
                    "url": "<%=request.getContextPath()%>/website/public/query/jsonArray?sqlDefId=${sqlDefId}"
                }
            },
            "serverPaging": true
        },
        "height": $(window).height()-20,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"selectable": "single",
        "columns": [
			{
            "field": "startIndex",
            "title": "序号",
            "width": "80px"
            }
			<c:forEach items="${columns}" var="column">
			<c:if test="${column.displayType==2}">
			,{
            "field": "${column.columnLabel}",
            "title": "${column.columnLabel}",
            "width": "${column.width}px"
            }
			</c:if>
			</c:forEach>
	    ],
        //"scrollable": {},
        "resizable": true,
        //"groupable": false
        /**
        ,group: {
           field: "per", aggregates: [
              { field: "per", aggregate: "average" }
           ]
         }
         */
    });
  });

 </script>
</head>
<body>
<div id="grid"></div>
</body>
</html>