<%
    String my_theme = com.glaf.core.util.RequestUtils.getTheme(request);
	if(my_theme == null){
		my_theme="default";
	} else if("default".equals(my_theme)){
		my_theme="blueopal";
	} else if("gray".equals(my_theme)){
		my_theme="default";
	} else if("sunny".equals(my_theme)){
		my_theme="default";
	} else if("metro".equals(my_theme)){
		my_theme="metro";
	} else if("bootstrap".equals(my_theme)){
		my_theme="bootstrap";
	}
    request.setAttribute("my_theme", my_theme);
%>
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.${my_theme}.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.mobile.flat.min.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/kendo.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>