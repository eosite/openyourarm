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
	var _data = {};

	var dataSource = new kendo.data.DataSource({
		schema : {
			total : 'total',
			data : 'rows'
		},
		transport : {
			read : {
				dataType : "json",
				type : 'post',
				url : function() {
					return "${contextPath}/mx/form/defined/searchBySql";
				}
			},
			parameterMap : function(data, type) {
				data.rows = data.pageSize;
				data.sql = _data.sql;
				data.systemName = _data.systemName;
				return data;
			}
		},
		serverFiltering : true,
		serverSorting : true,
		pageSize : 10,
		serverPaging : true
	});

	$(function() {
		var fn = "${param.fn}", $p = window.opener || window.parent;
		if ($p && (fn = $p[fn])) {
			_data = fn();
			window.initGrid(_data.columns);
		}
	});

	function initGrid(columns) {
		$('#grid').kendoGrid({
			dataSource : dataSource,
	//		columns : columns,
			width : '800px',
			height : x_height * 0.8,
			pageable : {
				refresh : true,
				pageSizes : [ 5, 10, 15, 20, 25, 50, 100, 200, 500 ],
				buttonCount : 10
			},
			resizable: true
		});
	}
	
	
</script>

</head>
<body>
	<div id="grid"></div>
</body>
</html>