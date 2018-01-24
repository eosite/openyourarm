<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<body>
<script type="text/javascript">
//在此拿到jsAPI权限验证配置所需要的信息
var _config =${config};
console.log(_config);
var contextPath="<%=request.getContextPath()%>";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dingtalk/logger.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dingtalk/login.js"></script>
</body>
</html>
