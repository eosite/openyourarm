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
<title>${dataSet.name }</title>
<%-- <%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> --%>

<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link
	href="${contextPath}/scripts/plugins/bootstrap/base/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css">
<style type="text/css">
.grid {}
</style>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/jquery.min.js"></script>
<script type="text/javascript">
	var mtxx = {
		contextPath : "${contextPath}",
		columns : []
	};

	<c:forEach items="${dataSet.selectSegments}" var="v">
	<c:if test="${v.output == true}">
		mtxx.columns.push({
			"field" : "${v.columnName}",
			"title" : "${v.title}",
			template : function(dataItem) {
				return getTemplate(dataItem, "${v.columnName}");
			}
		});
	</c:if>
	</c:forEach>

	$(function() {
		var myurl = mtxx.contextPath + '/mx/datasrc/previewJSON?id=${dataSet.id}';
		/*
		$('#grid').kendoGrid({
			columns : mtxx.columns,
			dataSource : new kendo.data.DataSource({
				schema : {
					total : 'total',
					data : 'rows'
				},
				transport : {
					read : {
						dataType : "json",
						type : 'post',
						url : function(o) {
							return myurl;
						},
						data : function(o) {
							return {
								rows : o.pageSize
							};
						}
					}
				},
				serverFiltering : true,
				serverSorting : true,
				pageSize : 10,
				serverPaging : true
			}),
			height : x_height,
			pageable : {
				refresh : true,
				pageSizes : [ 5, 10, 15, 20, 25, 50, 100, 200, 500 ],
				buttonCount : 10
			},
			selectable : 'single',
			resizable : true

		}); 
		*/
		var options = {
			columns : mtxx.columns,
			ajax : {
				read : {
					url : myurl,
					contentType : 'application/x-www-form-urlencoded'
				},
				parameterMap : function(options) {
					options.page = options.page || 1;
					options.rows = options.pageSize || 10;
					return options;
				},
				schema : {
					total : 'total',
					data : 'rows',
					errors : 'error'
				}
			},
			pagination : {
				paging : true,
				page : 1,
				pageSize : 10
			}
		};
		$("#grid").grid(options);
			
	});




	function getTemplate(dataItem, field) {
		var val = dataItem[field] || '', str = "";
		if (val) {
			if (typeof val == 'number' && ((str = new String(val)).length > 12)
					&& str.indexOf(".") < 0) {//可能是日期类型
				try {
					val = new Date(val).format('yyyy-MM-dd hh:mm:ss');
					//val = kendo.toString(new Date(val), 'yyyy-MM-dd HH:mm:ss');
				} catch (e) {
					console.log(e);
				}
			}
		}
		return val;
	}

	Date.prototype.format = function(format) {
		var args = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
			"S" : this.getMilliseconds()
		};
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var i in args) {
			var n = args[i];
			if (new RegExp("(" + i + ")").test(format))
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n
						: ("00" + n).substr(("" + n).length));
		}
		return format;
	};
</script>

</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="<%=request.getContextPath()%>/images/window.png"
				alt=" 数据集预览">&nbsp; 数据集预览<span>[${dataSet.name }]</span>
		</div>
		<br>
		<div id="grid"></div>
	</div>
	<br />
	<br />
	<br />
	<br />
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
</html>