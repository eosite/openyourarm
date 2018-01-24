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
	isGroup = false,
	isTrigger = "${param.isTrigger}";
	
	var sourceData = parent[getFn].call(this);
	$(function() {
		$("#main").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				scrollable : true,
				size : 50,
				resizable : false
			}, {} ]
		});
		$("#warp").kendoSplitter({
			orientation : "horizontal",
			panes : [ {
				scrollable : true,
				size : 250,
				resizable : false
			}, {} ]
		});
		
		if(sourceData && sourceData.nodes  && sourceData.nodes.length){
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
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "s",
					"N" : "ps"
				}
			},
			async : {
				enable : true,
				url : "${contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent=true&isTrigger="+isTrigger+"&isGroup="+isGroup,
				dataFilter : treeDataFilter
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			},
			callback : {
				onClick : zTreeOnClick,
				onCheck : zTreeOnCheck,
				beforeCheck: function(treeId, treeNode) {
					if(treeNode.isEvn && !treeNode.isParent){
						return true;
					}
					return false;
				}
			}
		};

		mtxx.zTreeObj = $.fn.zTree.init($("#tree"), setting);

		mtxx.tabStrip = $("#frameTab").kendoTabStrip({
			//  collapsible : true,
			animation : {
				open : {
					duration : 0,
					effects : "expand:vertical"
				}
			},
			select : initTabHeight
		}).data("kendoTabStrip");

		$.ajax({
			url : "${contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent=false&isGroup="+isGroup,
			success : function(data){
				if(data){
					initTabsContent(data);
				}
			},
			error : function(e){
				alert('异常错误，稍后再试');
			}
		});
	});
	
</script>
</head>
<body>
	<div id="main">
		<div id="title" class="k-header">
			<table style="width: 100%;height: 100%">
				<tr>
					<td valign="middle">
						<img src="/glaf/images/setting_tools.png" style="">
						<span style="display: inline;font-size: 28px;font-weight: bold;">控件选择</span>
					</td>
					<td align="right" valign="middle">
						<button class="k-button" onclick="saveSelectNodes()" >保存</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="warp">
			<div class="treewarp">
				<ul id="tree" class="ztree"></ul>
			</div>
			<div class="tabwarp">
				<div id="frameTab"></div>
			</div>
		</div>
	</div>
</body>
</html>