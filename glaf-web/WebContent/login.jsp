<%@page import="com.glaf.core.util.RequestUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
    /*
	////System.out.println("->params:" + RequestUtils.getParameterMap(request));
	BaseDataManager manager = BaseDataManager.getInstance();
	BaseDataInfo baseDataInfo = manager.getBaseData("remote_loginParam", "remote_access_param");
	if (baseDataInfo != null && StringUtils.isNotEmpty(baseDataInfo.getValue())) {
		String param = request.getParameter(baseDataInfo.getValue());
		if (StringUtils.isNotEmpty(param)) {
			BaseDataInfo info = manager.getBaseData(param, "remote_access_param");
			if (info != null && StringUtils.isNotEmpty(info.getValue())) {
				response.sendRedirect(info.getValue());
				return;
			}
		}
	}
	**/
	//response.sendRedirect(request.getContextPath() + "/mx/login");
%>
			<script type="text/javascript">
				 location.href="<%=request.getContextPath()%>/mx/login";
			</script>