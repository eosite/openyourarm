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
<title>表结构刷新</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<%@ include file="/WEB-INF/views/inc/globaljs.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">

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

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.tree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

.
td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

ul li {
	list-style-type: none;
}

.active {
	background-color: #93C3CF
}
</style>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	function ztreeBeforeClick() {

	}
</script>
<body>
	<div id="vertical" style="width: 98%; height: 98%; margin: 10px auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width: 550px; text-align: left; vertical-align: middle;"><img
						src="${contextPath}/images/database.png"
						style="vertical-align: middle;" /> <span
						style="font-family: Lucida Calligraphy; font-size: 20px; font-weight: bolder;">表结构刷新</span>
						<span style="font-size: 16px; font-weight: bolder;">工具</span></td>
					<td style="text-align: right;">
						<div style="padding: 5px;">
							<button t="build" class="k-button" type="button">生成表元数据</button>
							<button t="download" class="k-button" type="button">下载表元数据</button>
							<button t="upload" class="k-button" type="button">导入表元数据</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="horizontal" style="height: 100%; width: 100%;">
				<div style="height: 451px; padding-top: 10px;">
					<%@include file="/WEB-INF/views/form/defined/ex/commonTree.jsp"%>
				</div>
				<div></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript">
	$(function() {
		initZTree(window.getTableType(), window.getSystemName());
	});

	function getKendo(jq) {
		var data = jq.data();
		if (data) {
			for ( var key in data) {
				if (key.toLowerCase() == ("kendo" + jq.data("role"))) {
					return jq.data(key);
				}
			}
		}
		return null;
	}

	/* 布局 start */
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
		}, {
			collapsed : false,
			resizable : false,
			scrollable : false,
			size : "50px"
		} ]
	});

	$("#horizontal").kendoSplitter({
		panes : [ {
			collapsed : false,
			collapsible : true,
			collapsedSize : "0px",
			max : "300px",
			resizable : true,
			size : "300px",
			scrollable : true
		}, {
			size : "300px",
			scrollable : false
		} ]
	}).data("kendoSplitter");

	function buildMetaData(e, tables) {
		var $this = this;
		$.ajax({
			url : contextPath + '/mx/dataset/buildMetaData',
			type : 'POST',
			data : {
				tns : tables.join(","),
				systemName : window.getSystemName()
			},
			dataType : 'JSON',
			success : function(ret) {
				if(ret && ret.message){
				 	alert(ret.message);
				 	state.download.call($this, e);
				}
			},
			error : function(ret) {
				ret && ret.message && alert(ret.message);
			}
		});
	}

	var state = {
		build : function(e) {
			var tables = [ ];
			var nodes = window.$zTree.getCheckedNodes();
			if(nodes && nodes.length){
				$.each(nodes, function(i, v){
					v.tableName && (tables.push(v.tableName));
				});
			}
			if (tables.length) {
				window.buildMetaData.call(this, e, tables);
			} else {
				if (confirm("当前没有选择任何表格,生成表元数据会相当慢!")) {
					window.buildMetaData.call(this, e, tables);
				}
			}
		},
		download : function(e) {
			window.location.href = contextPath + '/mx/dataset/downloadMetaData?tid=' + new Date().getTime();
		},
		upload : function(e) {
			var link = contextPath + '/mx/dataset/uploadFiles?systemName=' + window.getSystemName();
			jQuery.layer({
				type : 2,
				maxmin : true,
				shadeClose : true,
				title : "导入",
				closeBtn : [ 0, true ],
				shade : [ 0.8, '#000' ],
				border : [ 10, 0.3, '#000' ],
				fadeIn : 100,
				close : function() {

				},
				area : [ '420px', (jQuery(window).height() - 650) + 'px' ],
				iframe : {
					src : link
				}
			});
		}
	};

	$("button.k-button").on("click.button", function(e) {
		var t = $(this).attr("t");
		state[t] && state[t].call(this, e);
	});

	$.extend(true, window.setting, {
		 check: {  
             enable: true
         }
	});
</script>
</html>