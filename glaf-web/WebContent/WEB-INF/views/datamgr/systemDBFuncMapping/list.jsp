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
<title>数据库函数对照表列表</title>
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
											"funcId" : {
												"type" : "string"
											},
											"dtype" : {
												"type" : "string"
											},
											"funcName" : {
												"type" : "string"
											},
											"params" : {
												"type" : "string"
											},
											"createBy" : {
												"type" : "string"
											},
											"createTime" : {
												"type" : "date",
												"format" : "{0: yyyy-MM-dd}"
											},
											"updateBy" : {
												"type" : "string"
											},
											"updateTime" : {
												"type" : "date",
												"format" : "{0: yyyy-MM-dd}"
											},
											"deleteFlag" : {
												"type" : "number"
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
										<c:if test="${!empty param.funcId}">
										!options.filter && (options.filter = {filters:[]});
										options.filter.filters.push({field:'funcId', operator:'eq', value:"${param.funcId}"});</c:if>
										return JSON.stringify(options);
									},
									"read" : {
										"contentType" : "application/json",
										"type" : "POST",
										"url" : "${contextPath}/rs/datamgr/systemDBFuncMapping/data"
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
										"field" : "funcId",
										"title" : "函数ID",
										"width" : "150px",
										"lockable" : false,
										"locked" : false
									},
									{
										"field" : "dtype",
										"title" : "数据库类型",
										"width" : "150px",
										"lockable" : false,
										"locked" : false
									},
									{
										"field" : "funcName",
										"title" : "函数名",
										"width" : "150px",
										"lockable" : false,
										"locked" : false
									},
									/* {
									"field": "params",
									"title": "参数说明",
									                "width": "150px",
									"lockable": false,
									"locked": false
									}, */
									{
										"field" : "createBy",
										"title" : "创建人",
										"width" : "150px",
										"lockable" : false,
										"locked" : false
									},
									{
										"field" : "createTime",
										"title" : "创建日期",
										"width" : "120px",
										"format" : "{0: yyyy-MM-dd}",
										"filterable" : {
											"ui" : "datetimepicker"
										},
										"lockable" : false,
										"locked" : false
									},
									/* {
									"field": "updateBy",
									"title": "UPDATEBY_",
									                "width": "150px",
									"lockable": false,
									"locked": false
									},
									{
									"field": "updateTime",
									"title": "UPDATETIME_",
									"width": "120px",
									"format": "{0: yyyy-MM-dd}",
									"filterable": {
										"ui": "datetimepicker"  
									    },
									"lockable": false,
									"locked": false
									},
									{
									"field": "deleteFlag",
									"title": "DELETE_FLAG_",
									"width": "90px",
									"lockable": false,
									"locked": false
									}, */
									{
										"command" : [ {
											"text" : "修改",
											"name" : "edit",
											"click" : function showDetails(e) {
												var dataItem = this
														.dataItem(jQuery(
																e.currentTarget)
																.closest("tr"));
												editRow({
													id : dataItem.id
												});
											}
										} ]
									} ],
							"scrollable" : {},
							"resizable" : true,
							"groupable" : false
						});
	});

	function addRow() {
		editRow();
	}

	function editRow(params) {
		var link = '${contextPath}/mx/datamgr/systemDBFuncMapping/edit';
		!params && (params = {funcId : "${param.funcId}"});
		if(params){
			link += "?" + $.param(params);
		}
		jQuery.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑数据库函数对照表信息",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '20px', '' ],
			fadeIn : 100,
			area : [ '620px', (jQuery(window).height() - 150) + 'px' ],
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
			<img src="${contextPath}/images/window.png" alt="数据库函数对照表列表">&nbsp;
			数据库函数对照表列表
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