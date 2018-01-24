<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%
    String actorId = com.glaf.core.util.RequestUtils.getActorId(request);
	SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
	//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//System.out.println("->user.getAccountType():"+user.getAccountType());
	//boolean isMobile = com.glaf.core.util.RequestUtils.isMobile(request);
	//if(isMobile){
%>

<%
	//} else {
		if(user.getAccountType() == 1){
	%>
		<script type="text/javascript">
			location.href="<%=request.getContextPath()%>/mx/my/home";
		</script>
	<%}else{%>
		<script type="text/javascript">
			location.href="<%=request.getContextPath()%>/mx/my/home";
		</script>
	<%
		}
	// }
	%>