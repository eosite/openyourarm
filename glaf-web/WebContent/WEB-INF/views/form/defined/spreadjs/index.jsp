<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.security.LoginContext"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	if (request.getAttribute("userTheme") != null) {
		UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
		request.setAttribute("theme", userTheme.getThemeStyle());
		request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
	} else {
		String theme = com.glaf.core.util.RequestUtils.getTheme(request);
		request.setAttribute("theme", theme);
		String homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
		request.setAttribute("homeTheme", homeTheme);
	}
	LoginContext loginContext = RequestUtils.getLoginContext(request);
	request.setAttribute("loginContext", loginContext);
%>
<!DOCTYPE html>
<html>
<head>
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="${contextPath}/scripts/spreadjs/html/lib/spread/gcspread.sheets.excel2013white.9.40.20153.0.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<!-- END THEME LAYOUT STYLES -->
</head>
<body>
	<div class="container">
		<div id="spreadContainer"
			style="width: 99%; height: auto; border: 1px solid gray"></div>
	</div>
</body>
<script
	src="${contextPath}/scripts/spreadjs/html/lib/jquery-2.0.2.min.js"></script>
<script
	src="${contextPath}/scripts/spreadjs/html/lib/spread/gcspread.sheets.all.9.40.20153.0.min.js"></script>
<script
	src="${contextPath}/scripts/spreadjs/html/lib/spread/gcspread.sheets.resources.zh.9.40.20153.0.min.js"></script>
<script
	src="${contextPath}/scripts/spreadjs/html/lib/spread/gcspread.sheets.print.9.40.20153.0.min.js"></script>
<script type="text/javascript">
function resizeDiv(){
	return $("#spreadContainer").css({
		width : '100%',
		height : $(window).height() * 0.9
	});
}

	$(document).ready(function() {
		//Create a spread instance on DIV element which id is 'spreadContainer'
		// Obtain spread instance
		var $spreadContainer = resizeDiv();
		
		var spread = new GcSpread.Sheets.Spread($spreadContainer.get(0), {
			sheetCount : 3
		});
		// Get active sheet in spread instance
		var activeSheet = spread.getActiveSheet();
		
		activeSheet.bind(GcSpread.Sheets.Events.CellClick, function(a, b){
			alert(a);
		});
		
		var persons = {name: "Wang feng", age: 25, address: {postcode: "710075"}};
		var source = new GcSpread.Sheets.CellBindingSource(persons);
		activeSheet.setBindingPath(0, 0, "name");
		activeSheet.setBindingPath(1, 1, "age");
		activeSheet.setBindingPath(3, 3, "address.postcode");
		activeSheet.setDataSource(source);
		// Here is a sample to set A1 cell value to "A1" and foreground color to "red"
		// activeSheet.getCell(0,0).value("A1").foreColor("red");
		//
		// TODO: more initialize code here
	});
</script>
</html>
