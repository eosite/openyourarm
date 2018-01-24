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
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/treegrid/css/jquery.treegrid.css">
<link href="${pageContext.request.contextPath}/scripts/layer/skin/layer.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/permissionDefined.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/layer/layer.min.js"></script>
<style>
html, body {
	height: 100%;
	padding: 0;
	margin: 0;
}

#main {
	width: 99%;
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
#treegrid {
	width: 100%;
}
.tree thead td{
	padding: 5px;
}
.tree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}
.tree .k-textbox {
	width: 130px;
}
.tree .tg-type {
	width : 120px;
}
</style>
<script type="text/javascript">
	var mtxx = {
		contextPath : '${contextPath}',
		url : "${contextPath}/mx/form/defined",
		parent : window.opener || window.parent ,
	}, pageId = "${param.pageId}",
	objelementId = "${param.objelementId}",
	nameElementId = "${param.nameElementId}",
	treegridExtend = new TreeGridExtend();
	$(function() {
		$("#main").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				scrollable : true,
				size : 40,
				resizable : false
			}, {} ]
		});
		$("#warp").kendoSplitter({
			orientation : "horizontal",
			panes : [ {
				scrollable : true,
				size : '70%',
				resizable : false
			}, {} ]
		});

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
			url : "${contextPath}/mx/form/defined/getPageControlPermissionDefined?pageId=" + pageId + "&showEvent=false",
			success : function(data) {
				if (data) {
					initTabsContent(data,true);
				}
			},
			error : function(e) {
				alert('异常错误，稍后再试');
			}
		});
		
		$("#treegrid").treegrid();
		var datas = parent.$("#" + objelementId).val();
		treegridExtend.init(datas);
		
		$("#help").kendoTooltip({
	        content: function(e) {
	          var msg = "<div style='color:red;'>说明：预览界面中 ，绿色边框为前置控件、红色边框为验证项</div>";
	          return msg; 
	        }
	    });
		
		$("#treegrid").kendoTooltip({
			width:200,
			filter : "input:not([role=listbox])",
			content : function(e) {
				var target = e.target;
				return target.val();
			}
		});
	});

</script>
</head>
<body>
	<div id="main">
		<div id="title" class="k-header" style="">
			<table style="width: 100%;height: 100%">
				<tr>
					<td valign="middle">
						<img src="/glaf/images/setting_tools.png" style="">
						<span style="display: inline;font-size: 28px;font-weight: bold;">权限定义</span>
					</td>
					<td align="right" valign="middle">				
						<span id="help">帮助</span>		
						<button class="k-button" onclick="treegridExtend.outerAddRow()" >添加</button>
						<button class="k-button" onclick="saveValidate()" >保存</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="warp">
			<div class="treewarp">
				<table id="treegrid" class="tree">
					<thead>
						<tr class="thead">
							<td>名称</td>
							<td>类型</td>
							<td>内容</td>
							<td>权限项</td>
							<td width="220px">操作</td>
						</tr>
					</thead>
				</table>
			</div>
			<div class="tabwarp">
				<div id="frameTab"></div>
			</div>
		</div>
	</div>
</body>
</html>