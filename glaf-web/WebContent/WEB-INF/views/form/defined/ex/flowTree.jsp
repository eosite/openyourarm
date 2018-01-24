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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>流程树</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
</head>
<body>
<div>
	<button class="k-button">保存</button>
</div>
	<div id="ztree-1"></div>
</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="${contextPath }/webfile/js/jquery.ztree.extends.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		
		if(!'${defId}'){
			alert("页面参数不对!");
			return false;
		}
		
		var ztreeObj = $("#ztree-1").ztree({
			check : {
				enable : true,
				chkStyle : "radio",
				radioType : "level"
			},
			/* data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : null
				}
			}, */
			async : {
				url : "${contextPath}/mx/form/formWorkflowTree/getWorkFlowPlanWithTreeByDefId"
					+ "?defId=${defId}&dsId=${dsId}",
				dataFilter : function(treeId, parentNode, responseData){
					return responseData.data;
				}
			}
		}).ztree("getZtree");
		
		$(".k-button").on("click", function(e){
			var nodes = ztreeObj.getCheckedNodes(true);
			if(nodes && nodes.length){
				$.each(nodes, function(){
					this.p_defId = '${defId}';
					this.processdefId = this.processDefId;
				});
				$.ajax({
					url : '${contextPath}/mx/form/formWorkflowTree/saveAll',
					type : 'post',
					dataType : 'JSON',
					data : {
						p_defId : '${defId}',
						treesStr : JSON.stringify(nodes)
					}
				}).done(function(ret){
					ret && (alert(ret.message));
				});
			}
		});
	});
</script>
</html>