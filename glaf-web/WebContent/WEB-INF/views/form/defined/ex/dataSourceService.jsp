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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	request.setAttribute("serviceUrl", CustomProperties.getString("service.url"));
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
	$(function() {
		var serviceUrl = "${serviceUrl}";
		if (!serviceUrl) {
			alert("服务地址不存在!");
			return false;
		}
		if (serviceUrl.indexOf("?") > -1) {
			serviceUrl = serviceUrl + "&";
		} else {
			serviceUrl = serviceUrl + "?";
		}
		$("iframe").css({
			height : $(window).height() - 20
		}).attr({
			src : serviceUrl + $.param({
				callback : '__callback'
			})
		});

		var targetId = "${param.targetId}", fn = "${param.fn}"
				|| "${param.retFn}";
		var $parent = window.opener || window.parent;
		window.addEventListener('message', function(e) {
			$parent.CommonCallBack && $parent.CommonCallBack(//
			e.data, targetId, fn);

			fn && (callback(e.data, fn));

		}, false);

	});

	function callback(data, retFn) {
		data.value = data.url;
		parent[retFn](data),closeWin();
	}

	function closeWin() {
		parent.layer.close(parent.layer.getFrameIndex());
	}
</script>
</head>
<body>
	<iframe frameborder="0" style="width: 100%;"></iframe>
</body>
</html>