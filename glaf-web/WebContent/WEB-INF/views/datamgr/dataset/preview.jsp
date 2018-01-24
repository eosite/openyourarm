<%@page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript">
	var mtxx = {
		contextPath : "${contextPath}",
		columns : []
	};
	
	function add(opts){
		$.extend(true, opts, {
			width : 200,
			template : function(dataItem) {
				return window.getTemplate(dataItem, opts.field);
			}
		});
		mtxx.columns.push(opts);
	}
	
	<c:forEach items="${dataSet.selectSegments}" var="v"><c:if test="${v.output == true}">add({"field" : "${v.columnLabel}","title" : "${v.title}"});</c:if>
	</c:forEach>

	$(function() {
		var myurl = mtxx.contextPath
				+ '/mx/dataset/previewJSON?id=${param.id}';
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
							return $.extend(true, getParams(), {
								rows : o.pageSize
							});
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
			scrollable : true,
			resizable : true
			
		}); 
		 
		 
		 $("button.k-search").on("click", function(){
			 $('#grid').data("kendoGrid").dataSource.read();
		 });
		 
	});
	
	function getParams(){
		var params = {};
		return $(".k-params").each(function(){
			params[$(this).attr("name")] = this.value;
		}), params;
	}

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
		
		<c:if test="${dataSet.params != null  && fn:length(dataSet.params) > 0}">
			<div>
				<c:forEach items="${dataSet.params}" var="v">
					<input class="k-textbox k-params" name="${v.param }" placeholder="${v.name }" value="${v.defVal }" />
				</c:forEach>
				<button class="k-button k-search" >查询</button>
			</div>
		<br>
		</c:if>
		
		<div id="grid"></div>
	</div>
	<br />
	<br />
	<br />
	<br />
</body>
</html>