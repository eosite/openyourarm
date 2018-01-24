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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>页面流设计工作区</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style type="text/css">
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head">
			<table style="width:100%;">
				<tr>
				    <td><div id="toolbar" style="border:0px;text-align: right;"></div></td>
				</tr>
			</table>
		</div>
		<div>

		</div>
	</div>
	<script type="text/javascript">
	var contextPath='${contextPath}';
		 $("#toolbar").kendoToolBar({
		        items: [
                { type: "button",id: "create",text: "新建",imageUrl:contextPath+"/images/page_add.png"},
				{ type: "button",id: "edit",text: "编辑",imageUrl:contextPath+"/images/page_edit.png"},
				{ type: "separator" },
				{ type: "button",id: "import",text: "导入",imageUrl:contextPath+"/images/document_small_upload.png"},
				{ type: "button",id: "export",text: "导出",imageUrl:contextPath+"/images/download.png"},
				{ type: "separator" },
				{ type: "button",id: "copy",text: "复制",imageUrl:contextPath+"/images/page_copy.png"},
				{ type: "button",id: "delete",text: "删除",imageUrl:contextPath+"/images/page_delete.png"},
		        ]
		    });
		 $("#vertical").kendoSplitter({
				orientation : "vertical",
				panes : [ {
					collapsed : false,
					resizable : false,
					scrollable : false,
					size : "40px"
				}, {
					collapsed : false,
					scrollable : false
				} ]
			});
	</script>
</body>
</html>