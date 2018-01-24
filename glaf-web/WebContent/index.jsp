<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.core.config.SystemConfig"%>
<%
	String contextPath = request.getContextPath();
	com.glaf.base.utils.ContextUtil.setContextPath(contextPath);
	com.glaf.core.util.ContextUtils.setContextPath(contextPath);
	String actorId = com.glaf.core.util.RequestUtils.getActorId(request);
	//System.out.println("########################actorId:"+actorId);
	SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
    String ssoFlag = SystemConfig.getString("sso_flag");
	if (("true").equals(ssoFlag)) {
		String tgt=request.getParameter("TGT");
		if(tgt!=null){
			Cookie cookie = new Cookie("TGT", tgt);
			int port=request.getServerPort();
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		}
	}
	String sysPropertyMainHtml = SystemConfig.getString("main_html");
	if (user != null) {
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    //System.out.println("->user.getAccountType():"+user.getAccountType());
		if (sysPropertyMainHtml != null && !sysPropertyMainHtml.isEmpty()) {
%>
			<script type="text/javascript">
				 location.href="<%=request.getContextPath()%><%=sysPropertyMainHtml%>";
			</script>
<%	
		}else  if(user.getAccountType() == 1){
%>
			<script type="text/javascript">
				 location.href="<%=request.getContextPath()%>/mx/my/home";
			</script>
<%
		   // response.sendRedirect(request.getContextPath() + "/mx/my/main");
		} else {
%>
			<script type="text/javascript">
				 location.href="<%=request.getContextPath()%>/mx/my/home";
			</script>
<%
			//response.sendRedirect(request.getContextPath() + "/mx/my/home");
		}
	} else {
           //System.out.println("δ��¼!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
%>
			<script type="text/javascript">
				 location.href="<%=request.getContextPath()%>/mx/login";
			</script>
<%
		//response.sendRedirect(request.getContextPath() + "/mx/login");
	}
%>