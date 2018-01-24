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
<title>系统规划</title>
<link rel="stylesheet" href="${contextPath}/scripts/jOrgChart/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${contextPath}/scripts/jOrgChart/css/jquery.jOrgChart.css"/>
<link rel="stylesheet" href="${contextPath}/scripts/jOrgChart/css/custom.css"/>
    <script type="text/javascript" src="${contextPath}/scripts/jOrgChart/prettify.js"></script>
    
    <!-- jQuery includes -->
    <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
    <script src="${contextPath}/scripts/treeJson.js" type="text/javascript"></script>
    <script src="${contextPath}/scripts/jOrgChart/jquery.jOrgChart.js"></script>
    <script>
	var sysId='${param.sysId}';
	var treeId='${param.treeId}';
    jQuery(document).ready(function() {
		//准备数据
		initSystemStructure();
        $("#org").jOrgChart({
            chartElement : '#chart',
            dragAndDrop  : false
        });
    });
	function initSystemStructure(){
		$.ajax({
				url : contextPath+"/rs/modeling/subSystemDef/subSystems?sysId="+sysId+"&treeId="+treeId,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if (rdata) {
						var jsonData = eval(rdata);
						var jsonDataTree = transData(jsonData, 'subSysId',
								'parentId_', 'children');
						jsonDataTree = {
							"children" : jsonDataTree
						};
						if ("children" in jsonDataTree) {
							$("#org").empty();
							createSubSystem(jsonDataTree.children,
									$("#org"), 0);
						}
					}

				},
				error : function() {
					console.log("获取系统数据失败");
				}

			});
	  }
	
		//创建控件菜单项
		function createSubSystem(nodes, pnode, level) {
			var l = level;
			if (pnode[0].localName && pnode[0].localName == "ul") {
				var lidom, adom,divdom,imgdom,path,title;
			$
					.each(
							nodes,
							function(i, node) {
								l = level;
								lidom = $("<li></li>");
								lidom.addClass("nav-item");
								adom = $("<a></a>");
								adom.attr("href", "#");
								adom.append(node.name);
								divdom=$("<div class=\"sysicon\"></div>");
								imgdom=$("<img></img>");
								if(node.eleType=='subsystem'){
									title="子系统";
									path=contextPath+"/scripts/model/img/sitemap-application-blue.png";
								}else if(node.eleType=='page'){
									title="页面";
									path=contextPath+"/scripts/model/img/page_world.png";
								}else if(node.eleType=='report'){
									title="报表";
									path=contextPath+"/scripts/model/img/report-excel.png";
								}else if(node.eleType=='service'){
									title="服务";
									path=contextPath+"/scripts/model/img/database_server.png";
								}else if(node.eleType=='dataobj'){
									title="服务";
									if(node.dataObjType==1)
									 path=contextPath+"/scripts/model/img/database_export.png";
								    else
									 path=contextPath+"/scripts/model/img/database_import.png";	
								}
								adom.attr("title",title);
								imgdom.attr("src",path);
								divdom.append(imgdom);
								lidom.append(divdom);
								lidom.append(adom);
								if ("children" in node) {
									var uldom = $("<ul></ul>");
									createSubSystem(node.children, uldom, ++l);
								}
								lidom.append(uldom);
								pnode.append(lidom);
		  });
			}
		}
    </script>
</head>
<body>
    <ul id="org" style="display:none">
    
   </ul>            
    
    <div id="chart" class="orgChart"></div>
	<script type="text/javascript">
    var subSysId = '${param.subSysId}';
    var contextPath = '${contextPath}';
	</script>
</body>
</html>