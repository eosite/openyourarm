<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
    com.glaf.base.modules.sys.model.BaseDataInfo themeInfo=session.getAttribute("theme")!=null?(com.glaf.base.modules.sys.model.BaseDataInfo)session.getAttribute("theme"):null;
	String theme = themeInfo!=null?themeInfo.getExt3():com.glaf.core.util.RequestUtils.getTheme(request);
	String homeTheme = themeInfo!=null?themeInfo.getExt2():com.glaf.core.util.RequestUtils.getHomeTheme(request);
	request.setAttribute("homeTheme", homeTheme);
	String layoutTheme = themeInfo!=null?themeInfo.getExt1():com.glaf.core.util.RequestUtils.getLayoutTheme(request);
	request.setAttribute("layoutTheme", layoutTheme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=SystemConfig.getString("res_system_name")%></title>
</head>
<frameset rows="55,*,31" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${contextPath}/mx/my/home/top?style=nav" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="187,*" frameborder="no" border="0" framespacing="0">
    <frame src="${contextPath}/mx/my/home/left?style=nav" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="${contextPath}/mx/my/home/content?style=nav&menu=true" name="rightFrame" id="rightFrame" title="rightFrame" />
  </frameset>
  <frame src="${contextPath}/mx/my/home/footer?style=nav" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes><body>
</body></noframes>
</html>
