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
<title>使用规则分类树列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>               
   </div>
</script>
<script type="text/javascript">
	jQuery(function() {
		jQuery("#grid")
				.kendoGrid(
						{
							"columnMenu" : true,
							"dataSource" : {
								"schema" : {
									"total" : "total",
									"model" : {
										"fields" : {
											"id" : {
												"type" : "string"
											},
											"code" : {
												"type" : "string"
											},
											"name" : {
												"type" : "string"
											},
											"treeId" : {
												"type" : "string"
											},
											"expandFlag" : {
												"type" : "string"
											},
											"pid" : {
												"type" : "number"
											},
											"orderNo" : {
												"type" : "number"
											},
											"creator" : {
												"type" : "string"
											},
											"createDateTime" : {
												"type" : "date",
												"format" : "{0: yyyy-MM-dd}"
											},
											"modifier" : {
												"type" : "string"
											},
											"modifyDateTime" : {
												"type" : "date",
												"format" : "{0: yyyy-MM-dd}"
											},
											"delFlag" : {
												"type" : "string"
											},
											"startIndex" : {
												"type" : "number"
											}
										}
									},
									"data" : "rows"
								},
								"transport" : {
									"parameterMap" : function(options) {
										return JSON.stringify(options);
									},
									"read" : {
										"contentType" : "application/json",
										"type" : "POST",
										"url" : "${pageContext.request.contextPath}/rs/dep/base/depBaseCategory/data"
									}
								},
								"serverFiltering" : true,
								"serverSorting" : true,
								"pageSize" : 10,
								"serverPaging" : true
							},
							"height" : x_height,
							"reorderable" : true,
							"filterable" : true,
							"sortable" : true,
							"pageable" : {
								"refresh" : true,
								"pageSizes" : [ 5, 10, 15, 20, 25, 50, 100,
										200, 500 ],
								"buttonCount" : 10
							},
							"selectable" : "single",
							"toolbar" : kendo.template(jQuery("#template")
									.html()),
							"columns" : [
                                    {
                                        "field" : "name",
                                        "title" : "类型名称",
                                        "width" : "200px",
                                        "lockable" : false,
                                        "locked" : false
                                    },
									{
										"field" : "code",
										"title" : "类型代码",
										"width" : "200px",
										"lockable" : false,
										"locked" : false
									},
									{
										"field" : "treeId",
										"title" : "树形结构编码",
										"width" : "200px",
										"lockable" : false,
										"locked" : false
									},
									{
										"field" : "expandFlag",
										"title" : "默认展开",
										"width" : "150px",
										"lockable" : false,
										"locked" : false
									},
									{
										"field" : "pid",
										"title" : "父分类标识",
										"width" : "200px",
										"lockable" : false,
										"locked" : false
									},
									{
										"field" : "orderNo",
										"title" : "排序号",
										"width" : "90px",
										"lockable" : false,
										"locked" : false
									},
									{
										"command" : [ {
											"text" : "修改",
											"name" : "edit",
											"click" : function showDetails(e) {
												var dataItem = this
														.dataItem(jQuery(
																e.currentTarget)
																.closest("tr"));
												//alert(JSON.stringify(dataItem));
												//alert(dataItem.id);
												var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/edit?id="
														+ dataItem.id;
												editRow(link);
											}
										} ]
									} ],
							"scrollable" : {},
							"resizable" : true,
							"groupable" : false
						});
	});

	function addRow() {
		editRow('${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/edit');
	}

	function editRow(link) {
		jQuery.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑使用规则分类树信息",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '620px', '450px' ],
			iframe : {
				src : link
			}
		});
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="${pageContext.request.contextPath}/images/window.png"
				alt="使用规则分类树列表">&nbsp; 使用规则分类树列表
		</div>
		<br>
		<div id="grid"></div>
	</div>
	<br />
	<br />
	<br />
	<br />
</body>
</html>