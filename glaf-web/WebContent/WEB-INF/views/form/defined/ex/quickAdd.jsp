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
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/jquery.cookie.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/filterObj.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/portlet/jquery.portlet.extends.js"></script>
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
	pageId = "${param.pageId}",
	parent = window.opener || window.parent ,
	getFn = "${param.getFn}",
	retFn = "${param.retFn}";

	function isCheckNodes(){
		return mtxx.outTreeObj.getCheckedNodes().length
	}
	var src = "${contextPath}/mx/form/defined/param/events_outInParam?pageId="+pageId+"&eleType="+pageId+"&eleId=hidParam&fn=initInOutParameterByEvents&retFn=retParamFn&isQuick=true";
	function zTreeOnCheck(){
		if(isCheckNodes()){
			$("#frame").attr("src",src);
		}else{
			$("#frame").attr("src","");
		}
	}

	function getParamFn(){
		var retObj = {};
		if(mtxx.zTreeObj.getCheckedNodes().length){
			retObj["inWidget"] =  JSON.stringify(mtxx.zTreeObj.getCheckedNodes());
		}
		if(mtxx.outTreeObj.getCheckedNodes().length){
			retObj["outWidget"] =  JSON.stringify(mtxx.outTreeObj.getCheckedNodes());
		}
		return retObj;
	}

	function treeDataFilter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for (var i = 0, l = childNodes.length, childNode; i < l; i++) {
			childNode = childNodes[i];
			if (childNode.icon) {
				childNode.icon = mtxx.contextPath + childNode.icon;
			}
			if (!childNode.isEvn) {
				//childNode.chkDisabled = true;
			}
			if (i == 0) {
				childNode.open = true;
			}
		}
		return childNodes;
	}
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
			}, {
				scrollable : true,
				size : 250,
				resizable : false
			},{
				scrollable : true,
				size : 250,
				resizable : false
			},{} ]
		});

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
					"Y" : "",
					"N" : "ps"
				}
			},
			async : {
				enable : true,
				url : "/glaf/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent=false&isGroup=true",
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
				onCheck : zTreeOnCheck
			}
		};

		var insetting = $.extend(true,{},setting),
			triggerSetting = $.extend(true,{},setting,{
				async : {
					enable : true,
					url : "${contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent=true&isTrigger=true&isGroup=true",
					dataFilter : treeDataFilter
				},
				callback: {

				}
			}),
			outSetting = $.extend(true,{},setting,{
				async : {
					enable : true,
					url : "${contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent=true&isTrigger=&isGroup=true",
					dataFilter : treeDataFilter
				}
			});


		mtxx.zTreeObj = $.fn.zTree.init($("#tree"), setting);

		mtxx.triggerTreeObj = $.fn.zTree.init($("#triggerTree"), triggerSetting);

		mtxx.outTreeObj = $.fn.zTree.init($("#outTree"), outSetting);
	});

	function getNodes(nodes){
		var names = "",
			retNodes = [],
			retDatas = {};
		for (var i = 0; i < nodes.length; i++) {
			var node = nodes[i],
				pObj = node.pObj ?JSON.parse(node.pObj) : null;
			names += (pObj?pObj.name + "-" :"")+ node.name + ",";
			var d = $.extend(true, {}, node);
			d.children = "";
			filterNode(d);
			retNodes.push(d);
		}
		retDatas.names = names;
		retDatas.nodes = retNodes;
		return retDatas;
	}

	function saveSelectNodes() {
		var nodes = mtxx.zTreeObj.getCheckedNodes(),
			nodes2 = mtxx.triggerTreeObj.getCheckedNodes(),
			nodes3 = mtxx.outTreeObj.getCheckedNodes(),
			inNodes,
			outNodes,
			triggerNodes;
		if(!nodes2.length && !nodes3.length){
			alert("请选择触发控件和输出控件");
			return ;
		}
		if (nodes.length) {
			inNodes = getNodes(nodes);
		}
		if (nodes2.length) {
			triggerNodes = getNodes(nodes2);
		}
		if (nodes3.length) {
			outNodes = getNodes(nodes3);
		}
		var $frame = $("#frame"),
			frameSrc = $frame.attr("src"),
			paramName = "",
			paramObj = [];
		if(frameSrc){
			var rets = $frame[0].contentWindow.getRets();
			paramName = "输入输出关系" ;
			paramObj.push({name : paramName,datas : rets});
		}

		var datasObj = [{
			values:[{
				inWidgetName:inNodes && inNodes.names?inNodes.names:"",
				inWidget:inNodes && inNodes.nodes?inNodes.nodes:[],
				widgetName:triggerNodes && triggerNodes.names?triggerNodes.names:"",
				widget:triggerNodes && triggerNodes.nodes?triggerNodes.nodes:[],
				widgetEvent:[{
					outWidgetName:outNodes && outNodes.names?outNodes.names:"",
					outWidget:outNodes && outNodes.nodes?outNodes.nodes:[],
					paramName: paramName,
					param: paramObj
				}]
			}]
		}];

		parent.treeGridExtend.init(JSON.stringify(datasObj));
		parent.layer && parent.layer.close(parent.layer.getFrameIndex());
	}
	
</script>
</head>
<body>
	<div id="main">
		<div id="title" class="k-header">
			<table style="width: 100%;height: 100%">
				<tr>
					<td valign="middle">
						<img src="/glaf/images/setting_tools.png" style="">
						<span style="display: inline;font-size: 28px;font-weight: bold;">快速添加定义</span>
					</td>
					<td align="right" valign="middle">
						<button class="k-button" onclick="saveSelectNodes()" >保存</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="warp">
			<div class="treewarp">
				<div style="font-size: 16px;font-weight: bold;text-align: center;">输入控件</div>
				<ul id="tree" class="ztree"></ul>
			</div>
			<div class="treewarp">
				<div style="font-size: 16px;font-weight: bold;text-align: center;">触发控件</div>
				<ul id="triggerTree" class="ztree"></ul>
			</div>
			<div class="treewarp">
				<div style="font-size: 16px;font-weight: bold;text-align: center;">输出控件</div>
				<ul id="outTree" class="ztree"></ul>
			</div>
			<div class="">
				<iframe id="frame" src="" width="99%" height="99%" border="0"></iframe>
			</div>
		</div>
	</div>
</body>
</html>