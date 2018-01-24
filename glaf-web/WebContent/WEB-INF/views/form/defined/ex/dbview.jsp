<%@page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>实时查询</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style type="text/css">
.datagrid {
	height: 440px;
}
</style>
<script type="text/javascript">
	var mtxx = {
		columns : [],
		sql : null
	};

	$.fn.getSelected = function() {
		if (!window.getSelection) {
			return document.selection.createRange().text;
		} else {
			var e = this.get(0);
			if (e && e.value)
				return e.value.substr(e.selectionStart, e.selectionEnd
						- e.selectionStart);
		}
		return null;
	};

	function getSql() {
		return $("#textarea-1").getSelected() || $("#textarea-1").val();
	}

	$(function() {
		$("#btn-4").on("click", search);
	});

	function search(e) {
		e.preventDefault();
		var text = mtxx.sql = window.getSql();
		if (text) {
			window.reloadFrame();
		}
	}

	function data() {
		return {
			columns : mtxx.columns,
			sql : mtxx.sql,
			systemName : ''
		};
	}

	function reloadFrame() {
		$("#frame-1").attr({
			src : '${contextPath}/mx/form/defined/ex/gridViewer?fn=data'
		}).css({
			height : x_height * 0.9
		});
	}
	function getTemplate(dataItem, field) {
		var val = dataItem[field] || '', str = "";
		if (val) {
			if (typeof val == 'number' && ((str = new String(val)).length > 12)
					&& str.indexOf(".") < 0) {//可能是日期类型
				try {
					val = kendo.toString(new Date(val), 'yyyy-MM-dd HH:mm:ss');
				} catch (e) {
					console.log(e);
				}
			}
		}
		return val;
	}
</script>

</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="<%=request.getContextPath()%>/images/window.png"
				alt=" 数据集预览">&nbsp; 数据集预览
		</div>

		<button type="button" id="btn-4" class="k-button" style="width: 50px">GO</button>
		<br> <br>
		<textarea class='k-textbox' id="textarea-1" rows="10"
			style="width:100%;"></textarea>

		<br>
		<div id="content">
			<iframe id="frame-1" frameborder="0" style="width:100%"></iframe>
		</div>

	</div>
	<br />
	<br />
	<br />
	<br />
</body>
</html>