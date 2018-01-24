<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OfficeControl</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/scripts/officecontrol/StyleSheet.css">
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%> 
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/officecontrol/OfficeContorlFunctions.js"></script>
 <script type="text/javascript">
 	var contextPath = "<%=request.getContextPath() %>";
 	 
	function pageOnLoad(){
 		var fileUrl = '<%=request.getContextPath() %>/website/public/report/exportXls?reportId=${reportId}';
 		OFFICE_CONTROL_OBJ = document.all("TANGER_OCX");
		try{
			NTKO_OCX_OpenDoc(fileUrl);
			OFFICE_CONTROL_OBJ.Titlebar=false;
			OFFICE_CONTROL_OBJ.Statusbar=false;
			OFFICE_CONTROL_OBJ.Toolbars=false;
		}catch(exe){
		}
	}

	function closeOCX(){
		OFFICE_CONTROL_OBJ = document.all("TANGER_OCX");
		try{
		    TANGER_OCX.Close();
		}catch(exe){
		}
	}
	
	
	function displayControl(rst){
		var control = $('#officecontrol');
		if(rst){
			control.show();
		}else {
			control.hide();
		}
	}
 </script>
</head>
<body class="easyui-layout" onload="pageOnLoad();" onbeforeunload ="closeOCX()">
	<div id='center' data-options="region:'center',split:true">
		<div align="center">
			<div id="officecontrol">
				<script type="text/javascript" 
				        src="<%=request.getContextPath() %>/inc/ntkoofficejs.jsp"></script>
				<div id=statusBar
					 style="height:20px;width:100%;background-color:#c0c0c0;font-size:12px;"></div> 
			</div>
		</div>
	</div>
</body>
</html>