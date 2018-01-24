<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("theme", "default");
%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/portlet/jquery.portlet.extends.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/jquery.cookie.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/dropselect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/filterObj.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/eventDefinedSelect.js"></script>
<style>
html, body {
	height: 100%;
	padding: 0;
	margin: 0;
}

#main{
	width : 99%;
	height: 99%;
}
#warp {
	padding: 0;
	margin: 0;
}

.treewarp, .tabwarp, .k-tabstrip-wrapper {
	height: 99%;
	padding: 0;
	margin: 0;
}

#frameTab, #frameTab .k-content {
	height: 99%;
	padding: 0;
	margin: 0;
}
.selected-cls {
	background-color: yellow;
}

.ui-selectable-helper {
	border: 5px #FFB90F solid
}
#title{

}
.li-span{
	width: 200px;
}
</style>
<script type="text/javascript">
	var mtxx = {
		contextPath : '${contextPath}',
		url : "${contextPath}/mx/form/defined",
	},
	pageId = "${param.pageId}" || "ff8eb93098c5429ea38cecb6008d6096",
	parent = window.opener || window.parent ,
	getFn = "${param.getFn}",
	retFn = "${param.retFn}",
	showEvent = "${param.showEvent}",
	isRadio = "${param.isRadio}",
	isGroup = false,
	isTrigger = "${param.isTrigger}";
	
	var sourceData = parent[getFn].call(this);
	$(function() {
		
		if(sourceData && ((sourceData.nodes  && sourceData.nodes.length) || sourceData.isGroup )){
			isGroup = sourceData["isGroup"]  ? true : false;
		}else{
			if("true" == $.cookie("showGroup")){
				isGroup = true;
			}
		}

		var setting = {
			view : {
				showIcon : true,
				showLine : true,
				showTitle : false,
				selectedMulti : false
			},
			check : {
				enable : true,
				chkStyle : isRadio?"radio":"checkbox",
				chkboxType : {
					"Y" : "",
					"N" : ""
				}
			},
			async : {
				enable : true,
				url : "${contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent="+showEvent+"&isTrigger="+isTrigger+"&isGroup="+isGroup,
				dataFilter : treeDataFilter
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			},
			callback: {
				onAsyncSuccess: function(event, treeId, treeNode, msg){
					if (sourceData) { //还原页面
						var ary = sourceData.nodes,
							treeObj = $.fn.zTree.getZTreeObj("tree"),
							ns = treeObj.transformToArray(treeObj.getNodes()),
							n, i, j;
						if (ary) {
							for (j = 0; j < ns.length; j++) {
								n = ns[j];
								for (i = 0; i < ary.length; i++) {
									if (n.id == ary[i].id) {
										treeObj.checkNode(n, true, false, false);
									}
								}
							}
						}
					}
				},
				beforeCheck: function(treeId, treeNode) {
					if(!(showEvent == "true") && !isRadio && treeNode.icon.indexOf("system-settings.png")>0){
						var checkFlag = true;
						$.each(treeNode.children,function(i,node){
							if(i==0){
								checkFlag = node.checked;
							}
							mtxx.zTreeObj.checkNode(node, !checkFlag);
						});
						return false;
					}
					if(showEvent == "true" && treeNode.isEvn && !treeNode.isParent){
						return true;
					}else if(showEvent == "false" && treeNode.isEle){
						return true;
					}
					return false;
				}
			}
		};

		mtxx.zTreeObj = $.fn.zTree.init($("#tree"), setting);

	});
	
</script>
</head>
<body>
	<div id="main">
		<div id="warp">
			<div style="position: fixed;right: 5px;top: 2px;">
				<button class="k-button" onclick="saveSelectNodes()" >保存</button>
			</div>
			<div class="treewarp">
				<ul id="tree" class="ztree"></ul>
			</div>
		</div>
	</div>
</body>
</html>