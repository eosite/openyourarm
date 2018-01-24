<%
    String my_theme = com.glaf.core.util.RequestUtils.getTheme(request);
	String js_theme = "grid-light";
	if(my_theme == null){
		my_theme="default";
		js_theme="grid-light";
	} else if("default".equals(my_theme)){
		my_theme="blue";
		js_theme="grid-light";
	} else if("gray".equals(my_theme)){
		my_theme="grid-light";
	} else if("sunny".equals(my_theme)){
		my_theme="grid";
		js_theme="sand-signika";
	} else if("metro".equals(my_theme)){
		my_theme="metro";
		js_theme="grid-light";
	} else if("bootstrap".equals(my_theme)){
		my_theme="bootstrap";
		js_theme="grid-light";
	}
    request.setAttribute("my_theme", my_theme);
	request.setAttribute("js_theme", js_theme);
	if(request.getParameter("charts_theme") != null && request.getParameter("charts_theme") != ""){
		request.setAttribute("js_theme", request.getParameter("charts_theme"));
	}
%>
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/core.css" rel="stylesheet" type="text/css" >
<link href="<%=request.getContextPath()%>/themes/${my_theme}/styles.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/highcharts-more.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/modules/funnel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/themes/${js_theme}.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>