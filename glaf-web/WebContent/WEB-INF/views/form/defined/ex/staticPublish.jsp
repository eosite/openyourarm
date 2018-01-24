<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>静态发布</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.ztree.extends.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.cookie.js" ></script>
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
.tdCls{
	align:left;
	height:16px;
	border-top:1px solid #ddd;
}
.selectCls{
	width : 90%;
}
.textCls{
	width : 65%;
}

.k-tabstrip-wrapper,#tabletree{
	height: 100%;
}

#tabletree .k-content{
	padding: 0px;
	height: 100%;
}
</style>
<script type="text/javascript">

/**
 * 全局参数
 */
var mtxx={},
	contextPath = '${contextPath}',
	mtZtree;

$(document).ready(function() {
	function ajaxDataFilter(treeId, parentNode, responseData) {
		if (responseData) {
			for (var i = 0; i < responseData.length; i++) {
				var data = responseData[i];
				if (data.locked == 1) {
					data.icon = contextPath + "/images/lock.png";
				} else {
					if (data.type == 1) {
						if (data.formType == 1) {
							data.icon = contextPath + "/images/folder_page_blue.png";
						} else {
							data.icon = contextPath + "/images/page_gear.png";
						}
					} else {
						if (data.formType == 1) {
							data.icon = contextPath + "/images/folder_page_white.png";
						} else {
							data.icon = contextPath + "/images/page_white.png";
						}
					}
				}
			}
		}
		return responseData;
	};
	mtZtree = $("#tree").ztree({
		async: {
			url: contextPath + '/mx/form/defined/getFormPageTree',
			autoParam: ["parentId=pId", "id"],
			otherParam: {},
			dataFilter: ajaxDataFilter
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
			}
		},
		edit: {
			enable: true
		},
		check: {
			enable: true,
			chkStyle: "checkbox",

		}
	}).ztree('getZtree');	

	mtxx.pageCategory = $.cookie('pageCategory');
	$("#pageCategory").kendoDropDownList({
		dataSource: new kendo.data.DataSource({
			transport: {
				read: {
					url: contextPath + '/rs/form/formPageCategory/all',
					data: {},
					type: 'post',
					dataType: 'json',
				}
			}
		}),
		dataTextField: "name",
		dataValueField: "id",
		animation: false,
		change: function(e) {
			var value = this.value();
			$.cookie('pageCategory', value, {
				expires: 30
			});
			mtZtree.setting.async.otherParam = {
				locked: $("#showLockedBox").prop("checked"),
				pageCategory: value
			};
			mtZtree.reAsyncChildNodes(null, 'refresh');
		}
	});

	if (mtxx.pageCategory) {
		$("#pageCategory").data("kendoDropDownList").value(mtxx.pageCategory);
	}

	$('#surePublish').kendoButton({
		imageUrl: contextPath + "/images/ok.png",
		click: function() {
			var nodes = mtZtree.getCheckedNodes(),
				effectiveNodes = [];
			if(nodes.length){
				$.each(nodes,function(i,node){
					if(node.formType=="0" && node.deleteFlag=="0"){
						effectiveNodes.push(node.id);
					}
				});
			}
			if(effectiveNodes.length){
				if(confirm("确认发布吗？")){
					$.ajax({
						url:contextPath+"/form/static/publish",
						type:"POST",
						dataType:"json",
						data:JSON.stringify(effectiveNodes),
						contentType:"application/json",
						success:function(){

						},
						error:function(){

						}
					})
				}
			}else{
				alert("请选择页面发布!");				
			}
		}
	});
});
</script>
</head>
<body >
	<div style="border:0px;">
	    <div>
			<div style='margin: 2px;'>
				<table style="width: 99%">
					<tr>
						<td>
							模块 : <div id="pageCategory"></div>	
						</td>
						<td align="right">
							<button id="surePublish" class="k-button k-button-icontext" type="button">静态发布</button>	
							<%-- <button class="k-button k-button-icontext">b</button> --%>
						</td>
					</tr>

				</table>
			</div>
			<div class="ztree" id="tree" style='height:auto;border:0px;' ></div>
	    </div>
	</div>
</body>
</html>
