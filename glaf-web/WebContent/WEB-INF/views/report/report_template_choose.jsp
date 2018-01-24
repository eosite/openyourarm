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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>选择报表模板</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
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

.titleTd {
	text-align: left;
	width: 100px;
}
</style>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width:100%;border:0px;">
				<tr>
					<td style="width:20px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/images/reports.png"
						style="vertical-align: middle;width:16px;padding-left: 5px;" /></td>
					<td
						style="width:120px;text-align:left;vertical-align: middle;padding-left:5px;">
						选择报表模板</td>
					<td></td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="filterCondition"
				style="width:80%;height:60px;float: inherit;">
				<table style="width:80%;height:100%;margin:auto">
					<tr>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">名称：</span></td>
						<td><input type="text" name="name" class="k-textbox" /></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">代码：</span></td>
						<td><input type="text" name="code" class="k-textbox" /></td>
						<td style="vertical-align:middle;"><button
								id="searchBt" type="button">查询</button></td>
					</tr>
				</table>
			</div>
			<div id="grid"></div>
		</div>
	</div>
	<script type="text/javascript">
		var contextPath = '${contextPath}';
		var webPath = '${webPath}';
		//初始化布局
		window.MSPointerEvent = null;
		window.PointerEvent = null;
		var mainHeight = $(window).height();
		var mainWidth = $(window).width();
		$('#vertical').height(mainHeight);
		$("#vertical").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsed : false,
				resizable : false,
				scrollable : false,
				collapsible : false,
				size : "30px"
			}, {
				collapsed : false,
				scrollable : false
			} ]
		});
		$(document)
				.ready(
						function() {
							$("#grid")
									.kendoGrid(
											{
												"columnMenu" : true,
												"dataSource" : {
													"schema" : {
														"total" : "total",
														"model" : {
															"fields" : {
																"name" : {
																	"type" : "string"
																},
																"code" : {
																	"type" : "string"
																},
																"rev" : {
																	"type" : "string"
																},
																"creator" : {
																	"type" : "string"
																},
																"modifyDatatime" : {
																	"type" : "date",
																	"format" : "{0: yyyy-MM-dd}"
																}
															}
														},
														"data" : "rows"
													},
													"transport" : {
														"parameterMap" : function(
																options) {
															//console.log(JSON.stringify(options));
															return JSON
																	.stringify(options);
														},
														"read" : {
															//"dataType": "json",
															data : {},
															"contentType" : "application/json",
															"type" : "POST",
															"url" : ""
														}
													},
													"serverFiltering" : true,
													"serverSorting" : true,
													"pageSize" : 10,
													"serverPaging" : true,
													"serverGrouping" : false
												},
												"dataBound" : function(e) {
													 var rows = this.items();
													  var statusObj,publishObj,status,publish;
													  var editBt,createNewBt;
										             $(rows).each(function () {
										                statusObj = $(this).find(".row-status");
										                editBt= $(this).find(".k-grid-editBt");
										                createNewBt= $(this).find(".k-grid-createNewBt");
										                status=statusObj.text();
										                if(status=="-1"){
										                  status="禁用";
										                  editBt.attr("disabled","disabled");
										                }
										                else{
										                   status="有效";
										                }
										                publishObj = $(this).find(".row-publish");
										                publish=publishObj.text();
										                if(publish=="1"){
										                  publish="已发布";
										                  editBt.attr("disabled","disabled");
										                }
										                else{
										                  publish="未发布";
										                  createNewBt.attr("disabled","disabled");
										                }
										                $(statusObj).html(status);
										                $(publishObj).html(publish);
												    });
												},
												"height" : 505,
												"reorderable" : true,
												"filterable" : false,
												"sortable" : true,
												"selectable":"row",
												"pageable" : {
													"refresh" : true,
													"pageSizes" : [ 10, 15, 30,
															50, 100 ],
													"buttonCount" : 15
												},
												"change": function(e) {
													var row = this.select();
											        var data = this.dataItem(row);
												},
												toolbar : [ {
													template : "<div id=\"toolbar\" style=\"border:0px;text-align: left;\"></div>"
												} ],
												"columns" : [
														{
															"field" : "startIndex",
															"title" : "序号",
															"width" : "80px",
															"lockable" : false,
															"locked" : false,
															"filterable" : false,
															"sortable" : false,
														},
														{
															"field" : "name",
															"title" : "模板名称",
															"width" : "120px",
															"lockable" : false,
															"locked" : false
														},
														{
															"field" : "ext",
															"title" : "文件类型",
															"width" : "120px",
															"lockable" : false,
															"locked" : false
														},
														{
															"field" : "rev",
															"title" : "版本号",
															"width" : "80px",
															"locked" : false
														},
														{
															"field" : "status",
															"title" : "状态",
															"width" : "80px",
															"locked" : false,
															"template": "<span class='row-status'>#:status#</span>",
														},
														{
															"field" : "publish",
															"title" : "发布状态",
															"width" : "120px",
															"locked" : false,
															"template": "<span class='row-publish'>#:publish#</span>",
														},
														{
															"field" : "creator",
															"title" : "创建人",
															"width" : "100px",
															"locked" : false
														},
														{
															"field" : "modifyDatatime_datetime",
															"title" : "修改时间",
															"width" : "150px",
															"lockable" : false,
															"format" : "{0: yyyy-MM-dd HH:mm:ss}",
															"align" : "center",
															"filterable" : {
																"operator" : [
																		"gt",
																		"gte",
																		"lt",
																		"lte" ],
																"ui" : "datetimepicker"
															}
														},
														{
															"command" : [													
																	{
																		"text" : "<img src=\"${contextPath}/images/eye.png\" style=\"vertical-align:middle;margin-right:5px;\"/>预览",
																		"name" : "defList",
																		"click" : function showDetails(
																				e) {
																			var dataItem = this
																					.dataItem(jQuery(
																							e.currentTarget)
																							.closest(
																									"tr"));
																			viewTemplate(dataItem.id);
																		},
																		"width" : "100px",
																	} ]
														} ],
												"scrollable" : {},
												"resizable" : true,
												"groupable" : false
											});
											initData();

							$("#toolbar")
									.kendoToolBar(
											{
												items : [
														
														{
															type : "button",
															id : "export",
															text : "导  出",
															imageUrl : contextPath
																	+ "/images/download.png",
															click:exportTemplate
														},{
															type : "button",
															id : "sure-ok",
															text : "确 定",
															imageUrl : contextPath
																	+ "/images/download.png",
															click:ok
														}]
											});
						});
	    function initData(){
			var filters=new Array();
			filters.push({field:"publish",operator:"contains",value:1});
			var paramVal = JSON.stringify(filters);
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.transport.options.read.url="${contextPath}/rs/reportTemplate/data";
			var filter = JSON.parse(paramVal);
			grid.dataSource.filter(filter);
			grid.dataSource.read();
		}
		//查询按钮
		$("#searchBt").kendoButton({
			imageUrl : "${contextPath}/images/search.png"
		});
		var searchBt = $("#searchBt").data("kendoButton");
		//查询按钮事件绑定
		searchBt.bind("click", function(e) {
			var paramVal = {};
			var filterObjs = $("#filterCondition input");
			var filters = new Array();
			$.each(filterObjs, function(i, filterObj) {
				if ($(filterObj).val() != '') {
					filter = new Object();
					filter.field = $(filterObj).attr("name");
					filter.operator = "contains";
					filter.value = $(filterObj).val();
					filters.push(filter);
				}
			});
			filters.push({field:"publish",operator:"contains",value:1});
			paramVal = JSON.stringify(filters);
			var grid = $("#grid").data("kendoGrid");
			var filter = JSON.parse(paramVal);
			grid.dataSource.filter(filter);
			grid.dataSource.read();

		});
		$("#addDialog").kendoWindow({
		      visible : false,
			  close: function(e) {
			    refreshGrid();
			  }
		});
		
		function viewTemplate(id) {
			$("#addDialog").kendoWindow({
				title : "预览报表",
				visible : false,
				modal : true
			});
			$("#addDialog").data("kendoWindow").title("预览报表");
			$("#addDialog")
					.data("kendoWindow")
					.refresh(
							"${server_scheme}://${header['host']}/glaf-report/view/stml/report/view/js?reportId="
									+ id);
			$("#addDialog").data("kendoWindow").toggleMaximization();
			$("#addDialog").data("kendoWindow").open();
		}
		
		//导出模型方法
		function exportTemplate() {
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			if (data.id) {
				window.location.href = "${server_scheme}://${header['host']}/glaf-report/view/reportTemplate/export?reportId=" + data.id;

			}
		}
		//刷新grid
		function refreshGrid() {
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
		}
		
		function ok(){
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			var fn = "${param.fn}", $parent = window.opener || parent;
			if(fn && (fn = $parent[fn])){
				fn.call(this, data);
			}
		}
	</script>
</body>
</html>