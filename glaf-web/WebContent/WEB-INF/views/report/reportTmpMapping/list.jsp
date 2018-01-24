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
<title>模板数据映射列表</title>
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
											"systemId" : {
												"type" : "string"
											},
											"templateId" : {
												"type" : "string"
											},
											"templateCode" : {
												"type" : "string"
											},
											"templateName" : {
												"type" : "string"
											},
											"describe" : {
												"type" : "string"
											},
											"creator" : {
												"type" : "string"
											},
											"createDatetime" : {
												"type" : "date",
												"format" : "{0: yyyy-MM-dd}"
											},
											"modifier" : {
												"type" : "string"
											},
											"modifyDatetime" : {
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
										//	options && (options.deleteFlag = 0);
										return JSON.stringify(options);
									},
									"read" : {
										"contentType" : "application/json",
										"type" : "POST",
										"url" : "${contextPath}/rs/report/reportTmpMapping/data"
									}
								},
								//	"serverFiltering" : true,
								//		"serverSorting" : true,
								"pageSize" : 10,
								"serverPaging" : true
							},
							"height" : x_height,
							//	"reorderable" : true,
							//	"filterable" : true,
							//	"sortable" : true,
							"pageable" : {
								"refresh" : true,
								"pageSizes" : [ 5, 10, 15, 20, 25, 50, 100,
										200, 500 ],
								"buttonCount" : 10
							},
							"selectable" : "single",
							"toolbar" : kendo.template(jQuery("#template")
									.html()),
							"columns" : [ {
								"field" : "templateId",
								"title" : "模板唯一标识",
								"width" : "150px",
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "templateCode",
								"title" : "模板代码",
								"width" : "150px",
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "templateName",
								"title" : "模板名称",
								"width" : "150px",
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "desc",
								"title" : "映射描述",
								"width" : "120px",
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "creator",
								"title" : "创建人",
								"width" : "120px",
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "createDatetime",
								"title" : "创建时间",
								"width" : "120px",
								"format" : "{0: yyyy-MM-dd}",
								"filterable" : {
									"ui" : "datetimepicker"
								},
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "modifier",
								"title" : "修改人",
								"width" : "120px",
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "modifyDatetime",
								"title" : "修改时间",
								"width" : "120px",
								"format" : "{0: yyyy-MM-dd}",
								"filterable" : {
									"ui" : "datetimepicker"
								},
								"lockable" : false,
								"locked" : false
							},
							/* {
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
										var link = "${contextPath}/mx/report/reportTmpMapping/edit?id="
												+ dataItem.id;
										editRow(link);
									}
								} ]
							}, */
							{
								title : '操作',
								width : '300px',
								template : createButtons
							} ],
							"scrollable" : {},
							"resizable" : true,
							"groupable" : false
						});

		$("#grid").on("click.button.t", "button[t]", function(e) {
			var $this = $(this);
			var t = $this.attr("t");
			t && (t in btnFunc) && (btnFunc[t].call(this, e));

			return false;
		})
	});

	var btnFunc = {
		'delete' : function(e) {
			var $this = $(this);
			var grid = window.getKendoGrid($this);
			var dataItem = grid.dataItem($this.closest("tr"));
			window.deleteRow([ dataItem ]);
		},
		update : function(e) {
			var $this = $(this);
			var grid = window.getKendoGrid($this);
			var dataItem = grid.dataItem($this.closest("tr"));
			var link = "${contextPath}/mx/report/reportTmpMapping/edit?id="
					+ dataItem.id;
			editRow(link);
		},
		mapping : function(e) {
			var $this = $(this);
			var grid = window.getKendoGrid($this);
			var dataItem = grid.dataItem($this.closest("tr"));
			var params = {
				view : '/report/reportTmpMapping/mapping',
				templateId : dataItem.templateId,
				tmpMappingId : dataItem.id
			};

			var url = "${contextPath}/mx/report/reportTmpMapping?"
					+ $.param(params);

			
			window.open(url);return false;
			
			jQuery.layer({
				type : 2,
				maxmin : true,
				shadeClose : true,
				title : "数据集映射",
				closeBtn : [ 0, true ],
				shade : [ 0.8, '#000' ],
				border : [ 10, 0.3, '#000' ],
				//	offset: ['20px',''],
				fadeIn : 100,
				area : [ '520px', (330) + 'px' ],
				iframe : {
					src : url
				}
			});
		},
		preview : function(e){
			var $this = $(this);
			var grid = window.getKendoGrid($this);
			var dataItem = grid.dataItem($this.closest("tr"));
			var url = "${contextPath}/mx/stml/report/view/js?"
				+ $.param({
					mappingId : dataItem.id
				});
			
			window.open(url);
		}
	}

	function deleteRow(rows) {
		if (!rows || !rows.length) {
			alert("请选择!");
			return false;
		}
		if (confirm("你确定删除吗?")) {
			var ids = [];
			$.each(rows, function(i, v) {
				ids.push(v.id);
			});
			$.ajax({
				url : '${contextPath}/mx/report/reportTmpMapping/delete?ids='
						+ ids.join(","),
				data : '',
				type : 'post',
				dataType : 'json',
				success : function(ret) {
					alert("操作成功!");
					window.getKendoGrid($("#grid")).dataSource.read();
				}
			});
		}
	}

	function getKendoGrid(o) {
		return $(o).closest("[data-role=grid]").data("kendoGrid");
	}
	
	function createButtons() {
		var arr = [ "<button class='k-button' t='update' style='width:5%;'>修改</button>",
				"<button class='k-button' t='delete'>删除</button>",
				"<button class='k-button' t='mapping'>数据集映射</button>",
				"<button class='k-button' title='暂时预览' t='preview'>报表预览</button>" ];
		return arr.join(" ");
	}

	function addRow() {
		editRow('${contextPath}/mx/report/reportTmpMapping/edit');
	}

	function editRow(link) {
		jQuery.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑模板数据映射信息",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			//	offset: ['20px',''],
			fadeIn : 100,
			area : [ '520px', (330) + 'px' ],
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
			<img src="${contextPath}/images/window.png" alt="模板数据映射列表">&nbsp;
			模板数据映射列表
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