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
<title>选择岗位</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
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
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}

.titleTd {
	text-align: left;
	width: 100px;
}
</style>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width:100%;border:0px;">
				<tr>
					<td style="width:20px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/images/users.png"
						style="vertical-align: middle;width:16px;padding-left: 5px;" /></td>
					<td
						style="width:120px;text-align:left;vertical-align: middle;padding-left:5px;">
						流程定义》选择岗位</td>
					<td style="vertical-align: middle;text-align:left;">
						<div id="toolbar" style="border:0px;text-align:left;">
							<button id="confirmBt" type="button">确认选择</button>
							<button id="resetBt" type="button">重新选择</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="filterCondition"
				style="width:95%;height:60px;float: inherit;">
				<table style="width:95%;height:100%;margin:auto">
					<tr>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">编号：</span></td>
						<td><input type="text" name="id" class="k-textbox" /></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">名称：</span></td>
						<td><input type="text" name="name" class="k-textbox" /></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">编码：</span></td>
						<td><input type="text" name="code" class="k-textbox" /></td>
						<td style="vertical-align:middle;width:120px;"><button
								id="searchBt" type="button">查询</button></td>
					</tr>
				</table>
			</div>
			<div id="posttree" class="ztree"></div>
		</div>
	</div>
	<script type="text/javascript">
	var contextPath = '${contextPath}';
	var webPath = '${webPath}';
	//初始化布局
	window.MSPointerEvent = null;
	window.PointerEvent = null;
	var mainHeight = $(window).height();
	var mainWidth = $(window).width();
	$('#vertical').height(mainHeight);
	$("#vertical").kendoSplitter({
		orientation : "vertical",
		panes : [ {
			collapsed : false,
			resizable : false,
			scrollable : false,
			collapsible : false,
			size : "30px"
		}, {
			collapsed : false,
			scrollable : false
		} ]
	});
	$.fn.extend($.fn.zTree,{
		  initZtree:function (container,url,setting,selectedVal){
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if($.isEmptyObject(rdata)){
						rdata=new Array();
					}else{
						rdata=rdata.data;
					}
					var treeObj=$.fn.zTree.init(container,setting, rdata);
					var node = treeObj.getNodeByParam("id", 1, null);
					if(selectedVal!=undefined&&selectedVal!=""){
						//获取选中节点
						selectedIds=selectedVal.split(",");
						var selectedNode;
						for (var i=0, l=selectedIds.length; i < l; i++) {
							selectedNode=treeObj.getNodeByParam("postCode", selectedIds[i], null);
							treeObj.checkNode(selectedNode, true, false);
							treeObj.expandNode(selectedNode.getParentNode(), true, true, null);
						}
					}
				},
				error : function() {
					console.log("获取岗位数据失败");
				}

		   }
		  );
		}});
  //默认选中的岗位
  var selectedPosts='${param.selectedVal}';
  //选中节点
  var selectedIds=new Array();
  var selectedNames=new Array();
  $(document).ready(function(){
	    var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
				    idKey: "treeId",
					pIdKey: "pTreeId",
				}
			}
		};
		var url=contextPath+"/rs/sys/post/deptRolePostTreeJson";
	
		$.fn.zTree.initZtree($("#posttree"),url,setting,selectedPosts);
		$("#confirmBt").kendoButton({
			imageUrl : "${contextPath}/images/save_as.png"
		});
		var confirmBt = $("#confirmBt").data("kendoButton");
		//确认按钮点击事件绑定
		confirmBt.bind("click",function(e) {
			    //获取选中节点
				var treeObj = $.fn.zTree.getZTreeObj("posttree");
                var nodes = treeObj.getCheckedNodes(true);
				selectedIds=new Array();
				selectedNames=new Array();
			    $(nodes).each(function(i,node){
					selectedIds.push(node.postCode);
					selectedNames.push(node.name);
				});
		        if(selectedIds&&selectedIds.length>0){
		        	 setValue(selectedIds.join(","), selectedNames.join(","));
				}
				else{
				   if(confirm("未选择岗位，窗口将直接关闭，确认继续？")){
					   setValue('','');
				   }
				}
		});
		
		function setValue(values, names){
			var targetId = "${param.targetId}", fn = "${param.fn}" , $parent = window.opener || window.parent;
		     if(fn && $parent[fn]){
		    	 var $target = $($parent.document.getElementById(targetId));
		    	 $parent[fn].call($target, {data : {
		    		 post_name : names,
		    		 post_id : values
		    	 }});
		    	 $target.attr("select-type", "post");
		    	 window.close();
		     } else {
			     $parent.setPropertyVal(values, names);
			     $parent.closeAssignChooseDialog();
		     }
		}
		
		$("#resetBt").kendoButton({
			imageUrl : "${contextPath}/images/gem_cancel_1.png"
		});
		var resetBt = $("#resetBt").data("kendoButton");
		//重置按钮点击事件绑定
		resetBt.bind("click",function(e) {
			    treeObj=$.fn.zTree.getZTreeObj("posttree");
				if(selectedVal!=undefined&&selectedVal!=""){
						//获取选中节点
						var selectedvalArr=selectedVal.split(",");
						var selectedNode;
						for (var i=0, l=selectedvalArr.length; i < l; i++) {
							selectedNode=treeObj.getNodeByParam("postCode", selectedvalArr[i], null);
							treeObj.checkNode(selectedNode, true, false);
							treeObj.expandNode(selectedNode.getParentNode(), true, true, null);
						}
				}
				selectedIds=new Array();
				selectedNames=new Array();
		});
		//查询按钮
		$("#searchBt").kendoButton({
			imageUrl : "${contextPath}/images/search.png"
		});
		var searchBt = $("#searchBt").data("kendoButton");
		//查询按钮事件绑定
		searchBt.bind("click", function(e) {
			var paramVal = {};
			var filterObjs = $("#filterCondition input");
			var filters = new Array();
			$.each(filterObjs, function(i, filterObj) {
				if ($(filterObj).val() != '') {
					filter = new Object();
					filter.field = $(filterObj).attr("name");
					filter.operator = "contains";
					filter.value = $(filterObj).val();
					filters.push(filter);
				}
			});
			paramVal = JSON.stringify(filters);
			var filter = JSON.parse(paramVal);

		});
  });
	</script>
</body>
</html>