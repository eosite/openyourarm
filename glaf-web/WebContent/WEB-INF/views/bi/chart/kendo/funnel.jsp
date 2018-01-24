<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>${chart.subject}</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script type="text/javascript">

    function createChart() {
            $("#chart").kendoChart({
                title: {
                    text: "${chart.chartTitle}",
                    margin: {
                        top: 10,
                        bottom: 10,
                        left: -5
                    }
                },
				dataSource: {
                    transport: {
                        read: {
                            url: "${contextPath}/mx/bi/chart/kendo/json?chartId=${chart.id}",
                            dataType: "json"
                        }
                    }
                },
                legend: {
                    visible: false
                },
                series: [{
                    type: "funnel",
                    dynamicSlope:true,
                    field: "value",
                    categoryField: "category",
                    dynamicHeight : false,
                    labels: {
                        color:"black",
                        visible: true,
                        background: "transparent",
                        template: "#= category #: #= value#",
                        align: "left"
                    }
                }],
                tooltip: {
                    visible: true,
                    template: "#= category # #= kendo.format('{0:p}',value/dataItem.parent()[0].value)#"
                }
            });
        }

    $(document).ready(createChart);
    $(document).bind("kendo:skinChange", createChart);
 </script>
</head>
<body>
 <div id="chart" style="min-width: 120px; max-width: ${chart.chartWidth*1.5}px; width: ${chart.chartWidth}px; height: ${chart.chartHeight}px; margin: 0 auto"></div>
</body>
</html>