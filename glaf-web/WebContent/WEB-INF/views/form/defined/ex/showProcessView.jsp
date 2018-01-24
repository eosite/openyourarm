<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>工作流查看</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
	var msgId = "${param.msgId}";
	
	if(!msgId){
		alert("参数不对!");
		//return;
	} else {
		$.ajax({
			url : '${contextPath}/mx/form/formWorkflowPlan/getFormWorkflowPlanByDefId',
			data : {
				defId : msgId
			},
			async : true,
			type : 'POST',
			dataType : 'JSON',
			success : function(ret){
				if(ret && ret.plans && ret.plans[0]){
					var processDefId = ret.plans[0].processDefId;
					//alert(processDefId);
					window.location.href = "${contextPath}/mx/activiti/image/viewImage?processDefinitionId=" + processDefId;
				}	
			}
		});
	}
	
</script>
</head>
<body>
</body>
</html>