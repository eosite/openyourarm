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
</head>
<body style=" margin: 0">
 <div id="main" style="min-width: 120px; max-width: ${chart.chartWidth*1.5}px; width: ${chart.chartWidth}px; height: ${chart.chartHeight}px; margin: 0 auto"></div>
 <%@ include file="/WEB-INF/views/inc/init_echarts.jsp"%>
 <script type="text/javascript">

    var myChart = echarts.init(document.getElementById('main')); 

    var option = {
		    title: {
			    text: '${chart.chartTitle}'
			    <c:if test="${not empty chart.chartSubTitle}">
			    ,subtext: '${chart.chartSubTitle}'
			    </c:if>
		    },
            tooltip: {
                show: true
            },
            legend: {
                data:${legendArrayJson}
            },
            xAxis : [
                {
                    type : 'category',
                    data : ${categories_scripts}
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : ${seriesDataJson}
 
        };
  
    // 为echarts对象加载数据 
    myChart.setOption(option); 

 </script>
</body>
</html>