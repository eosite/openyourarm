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
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style type="text/css">
.datagrid {
	height: 440px;
}
</style>
<script type="text/javascript">

	var mtxx = {
		contextPath : "<%=request.getContextPath()%>",
		columns : new Array()
	};
	<c:forEach items="${dataSet.selectSegments}" var="v">
	<c:if test="${v.output == true}">
	mtxx.columns.push({
		"field" : "${v.columnLabel}",
		"title" : "${v.title}",
		template : function(dataItem) {
			return getTemplate(dataItem, "${v.columnLabel}");
		}
	});
	</c:if>
	</c:forEach>

	function pageFunc(){
		var kendoGrid = $("#grid").data("kendoGrid");
		var selected = kendoGrid.select();
		var targetId = "${param.targetId}", fn = "${param.fn}",$parent = window.opener || window.parent;
		if(selected && selected.length){
			var target = $parent.document.getElementById(targetId), items = [], item;
			$.each(selected , function(i, $tr){
				item = kendoGrid.dataItem($tr);
				items.push(item);
			})
			if(fn && $parent[fn]){
				$parent[fn].call(target, items);
			}
		} else {
			alert('请选择!');
		}
	}
	$(function() {

		$('#grid')
				.kendoGrid(
						{
							toolbar : "<div class='toolbar'><button class='k-button' onclick='pageFunc()'>确定</button></div>",
							columns : mtxx.columns,
							dataSource : new kendo.data.DataSource(
									{
										schema : {
											total : 'total',
											data : 'rows'
										},
										transport : {
											read : {
												dataType : "json",
												type : 'post',
												url : function(o) {
													return mtxx.contextPath
															+ '/mx/dataset/previewJSON?id=${dataSet.id}';
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
							height : (x_height * 0.6),
							pageable : {
								refresh : true,
								pageSizes : [ 5, 10, 15, 20, 25, 50, 100, 200,
										500 ],
								buttonCount : 10
							},
							selectable : 'row, multiple',
							resizable : true

						});

	});

	function getTemplate(dataItem, field) {
		var val = dataItem[field] || '', str = "";
		if (val) {
			if (typeof val == 'number' && ((str = new String(val)).length > 12)
					&& str.indexOf(".") < 0) {//可能是日期类型
				try {
					val = kendo.toString(new Date(val), 'yyyy-MM-dd HH:mm:ss')
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
		
		<div class="x_content_title">选择流程模版</div>
		<br>
		<div id="grid"></div>
	</div>
</body>
</html>