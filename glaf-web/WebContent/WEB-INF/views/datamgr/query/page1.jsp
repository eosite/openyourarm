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
<title>路面基层压实度检测结果汇总表</title>
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
                    "url": "<%=request.getContextPath()%>/website/public/query/json?sqlDefId=${sqlDefId}"
                }
            },
            "serverPaging": true
        },
        "height": $(window).height()-20,
        "reorderable": true,
        "filterable": false,
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
            "title": "${column.title}",
            "width": "${column.width}px"
            }
			</c:if>
			</c:forEach>
	    ],
        "scrollable": {},
        "resizable": true,
        "groupable": true
        ,group: {
           field: "per", aggregates: [
              { field: "per", aggregate: "average" }
           ]
         }
    });
  });

 </script>
</head>
<body>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="${sqlDef.title}">&nbsp;
    ${sqlDef.title}
 </div>
 <br>
 <div id="grid"></div>
</body>
</html>