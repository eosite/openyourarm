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
<%@ include file="/WEB-INF/views/inc/init_highcharts.jsp"%>
<script type="text/javascript">
 $(function () {

    <c:if test="${chart.gradientFlag eq '1' }">
	Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
		return {
			radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
			stops: [
				[0, color],
				[1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
			]
		};
	});
	</c:if>

    $('#container').highcharts({
        chart: {
            type: 'pie'
			<c:if test="${chart.enable3DFlag eq '1' }">
			,options3d: {
                enabled: true,
                alpha: 35,
				 beta: 0
            }
		    </c:if>
        },
        title: {
            text: '${chart.chartTitle}'
        },
		<c:if test="${not empty chart.chartSubTitle}">
        subtitle: {
            text: '${chart.chartSubTitle}'
        },
		</c:if>
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 45,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            name: '${chart.chartTitle}',
            data: ${pie_data}
        }]
 
    });
});

<c:if test="${chooseThemes eq 'true'}">
 
$(document).ready(function(){
	$("button.btn").click(function(){
		var theme = $(this).attr("theme");
		if(theme != null) {
		    window.location.href="<%=request.getContextPath()%>/mx/bi/chart/highcharts/showChart?chartId=${chart.id}&charts_theme="+theme+"&chooseThemes=${chooseThemes}";
		}
    });
});
</c:if>
</script>
</head>
<body>
<c:if test="${chooseThemes eq 'true'}">
 <p>
	图表主题：
	<button class="btn btnGray" theme="default">默认</button>
	<button class="btn btnGray" theme="grid">网格 (grid)</button>
	<button class="btn btnGray" theme="grid-light">grid-light</button>
	<button class="btn btnGray" theme="skies">天空 (skies)</button>
	<button class="btn btnGray" theme="gray">灰色 (gray)</button>
	<button class="btn btnGray" theme="dark-blue">深蓝 (dark-blue)</button>
	<button class="btn btnGray" theme="dark-green">深绿 (dark-green)</button>
	<button class="btn btnGray" theme="dark-unica">dark-unica</button>
	<button class="btn btnGray" theme="sand-signika">sand-signika</button>
  </p>
 </c:if>
 <div id="container" style="min-width: 80px; max-width: ${chart.chartWidth}px; height: ${chart.chartHeight}px; margin: 0 auto"></div>
</body>
</html>