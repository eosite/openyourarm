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
<title>部门角色选择工具</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
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
</style>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width:100%;border:0px;">
				<tr>
					<td style="width:20px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/images/users.png"
						style="vertical-align: middle;width:16px;padding-left: 5px;" /></td>
					<td
						style="width:180px;text-align:left;vertical-align: middle;padding-left:5px;">
						流程定义》选择部门角色</td>
					<td style="vertical-align: middle;text-align:left;">
						<div id="toolbar" style="border:0px;text-align:left;">
							<button id="confirmBt" type="button">确认选择</button>
							<button id="resetBt" type="button">重新选择</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="horizontal" style="height:100%; width: 100%;">
				<div id="departDiv">
					<ul id="departTree" class="ztree"></ul>
				</div>
				<div>
					<div id="filterCondition"
						style="width:100%;height:60px;float: inherit;">
						<table style="width:100%;height:100%;margin:auto">
							<tr>
								<td class="titleTd"><img
									src="${contextPath}/images/bullet_blue.png"
									style="vertical-align: middle;" /><span
									style="word-spacing:8px; letter-spacing: 1px;">编号：</span></td>
								<td><input type="text" name="id" class="k-textbox" /></td>
								<td class="titleTd"><img
									src="${contextPath}/images/bullet_blue.png"
									style="vertical-align: middle;" /><span
									style="word-spacing:8px; letter-spacing: 1px;">名称：</span></td>
								<td><input type="text" name="name" class="k-textbox" /></td>
								<td class="titleTd"><img
									src="${contextPath}/images/bullet_blue.png"
									style="vertical-align: middle;" /><span
									style="word-spacing:8px; letter-spacing: 1px;">编码：</span></td>
								<td><input type="text" name="code" class="k-textbox" /></td>
								<td style="vertical-align:middle;width:120px;"><button
										id="searchBt" type="button">查询</button></td>
							</tr>
						</table>
					</div>
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var pageId = '${param.pageId}';
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
				collapsible : true,
				size : "40px"
			}, {
				collapsed : false,
				scrollable : false
			} ]
		});
		var splitter = $("#horizontal").kendoSplitter({
			panes : [ {
				collapsed : false,
				collapsible : true,
				collapsedSize : "0px",
				max : "300px",
				resizable : false,
				size : "200px",
				scrollable : true
			}, {
				scrollable : false
			} ]
		});
		//树初始化设置
		var setting = {
			data : {
				key : {
					title : "text"
				},
				simpleData : {
					enable : true
				}
			},
			callback : {
				//声明单击回调事件
				beforeClick : beforeClick
			}
		};
		//树节点点击事件
		var selectedNode;
		function beforeClick(treeId, treeNode) {
			selectedNode = treeNode;
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.transport.options.read.url = "${contextPath}/rs/sys/role/data";
			grid.dataSource.page(1);
			grid.dataSource.read();
		}
		//树控件数据绑定
		function bindTreeData() {
			$.ajax({
				url : contextPath + "/rs/sys/department/treeJson",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					nodeCode : "012"
				},
				success : function(data) {
					if (data) {
						var nodes = eval(data);
						//增加动态部门节点（提单人部门、提单人上级部门、提单人上两级部门、动态部门）
						nodes.unshift({id:"{dynDept}",code:"{dynDept}",parentId:0,name:"动态部门",text:"动态部门",sort:4,icon:contextPath+"/images/group_blue.png"});
						nodes.unshift({id:"{subGrandDept}",code:"{subGrandDept}",parentId:0,name:"提单人上两级部门",text:"提单人上两级部门",sort:3,icon:contextPath+"/images/group.png"});
						nodes.unshift({id:"{subParentDept}",code:"{subParentDept}",parentId:0,name:"提单人上级部门",text:"提单人上级部门",sort:2,icon:contextPath+"/images/group.png"});
						nodes.unshift({id:"{subDept}",code:"{subDept}",parentId:0,name:"提单人部门",text:"提单人部门",sort:1,icon:contextPath+"/images/group.png"});
						$.fn.zTree.init($("#departTree"), setting, nodes);
						treeObj = $.fn.zTree.getZTreeObj("departTree");
					}
				},
				error : function() {

				}
			});
		}
		bindTreeData();
		$(document)
				.ready(
						function() {
							jQuery("#grid")
									.kendoGrid(
											{
												"columnMenu" : true,
												"dataSource" : {
													"schema" : {
														"total" : "total",
														"model" : {
															"fields" : {
																"roleId" : {
																	"type" : "string"
																},
																"name" : {
																	"type" : "string"
																},
																"code" : {
																	"type" : "string"
																},
																"content" : {
																	"type" : "string"
																},
																"createTime" : {
																	"type" : "date",
																	"format" : "{0: yyyy-MM-dd}"
																},
																"lastLoginTime" : {
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
															return JSON
																	.stringify(options);
														},
														"read" : {
															"contentType" : "application/json",
															"type" : "POST",
															"url" : ""
														}
													},
													"serverFiltering" : true,
													"serverSorting" : true,
													"pageSize" : 12,
													"serverPaging" : true,
													"serverGrouping" : false,
												},
												"dataBound" : function(e) {
													gridInit();
												},
												"height" : 490,
												"reorderable" : true,
												"filterable" : false,
												"sortable" : true,
												"pageable" : {
													"refresh" : true,
													"pageSizes" : [ 12, 20, 50,
															100 ],
													"buttonCount" : 15
												},
												"columns" : [
														{
															"field" : "roleId",
															"headerTemplate" : "<input type=\"checkbox\" name=\"checkall\" onclick=\"selectAll(this)\"/><label for=\"check-all\">全选</label>",
															"width" : "60px",
															"lockable" : false,
															"locked" : false,
															"filterable" : false,
															"sortable" : false,
															"template" : "<input type=\"checkbox\" name=\"rid\" value=\"#:roleId#\" onclick=\"selectRow(this)\"/>"
														},
														{
															"field" : "roleId",
															"title" : "角色编号",
															"width" : "100px",
															"lockable" : false,
															"locked" : false
														},
														{
															"field" : "name",
															"title" : "角色名称",
															"width" : "120px",
															"lockable" : false,
															"locked" : false
														},
														{
															"field" : "code",
															"title" : "编码",
															"width" : "120px",
															"locked" : false
														},
														{
															"field" : "content",
															"title" : "描述",
															"width" : "200px",
															"sortable" : false,
															"filterable" : {
																"cell" : {
																	"showOperators" : false,
																	"suggestionOperator" : "contains",
																	"operator" : "contains"
																}
															}
														},
														{
															"command" : [ {
																"text" : "用户设置",
																"name" : "roleUsers",
																"click" : function showDetails(
																		e) {
																	dataItem = this
																			.dataItem(jQuery(
																					e.currentTarget)
																					.closest(
																							"tr"));
																	var link = "${contextPath}/mx/sys/role/roleUsers?roleId="
																			+ dataItem.roleId;
																	roleUsers(link);
																}
															} ],
															"width" : "100px",
														} ],
												"scrollable" : {},
												"resizable" : true,
												"groupable" : false
											});

							//根据获取的参数设置页面选中
							function initSelect() {
								var selectedVal = "${selectedVal}";
								var selectedNameVal="${selectedNameVal}";
								var selectedArr,seleceted,selectedCode,selectedValue,selectedValMap;
								if (selectedVal) {
									selectedArr = selectedVal.split("|");
									$.each(selectedArr, function(i, item) {
									    seleceted= item.split(":");
									    selectedCode=seleceted[0];
									    selectedValue=seleceted[1];
									    if(selectedValue){
									    selectedValue=selectedValue.substring(1,selectedValue.length-1);
									    }
									    selectedValMap=new Map();
									    $.each(selectedValue.split(","),function(i, item){
									      selectedValMap.put(item,item);
									    });
										selectedIds.put(selectedCode, selectedValMap);
									});
								var selectedName;
								if (selectedNameVal) {
									selectedArr = selectedNameVal.split("|");
									$.each(selectedArr, function(i, item) {
									    seleceted= item.split(":");
									    selectedName=seleceted[0];
									    selectedValue=seleceted[1];
									    if(selectedValue){
									    selectedValue=selectedValue.substring(1,selectedValue.length-1);
									    }
									    selectedValMap=new Map();
									    $.each(selectedValue.split(","),function(i, item){
									      selectedValMap.put(item,item);
									    });
										selectedNames.put(selectedName, selectedValMap);
									});
									}
									gridInit();
								}
							}
							initSelect();
						});
		var selectedIds = new Map();
		var selectedNames=new Map();
		//checkbox选中、取消选中行操作
		function selectRow(obj) {
			var grid = $("#grid").data("kendoGrid");
			var checkstatus = obj.checked;
			var selRow = $(obj).closest("tr"), dataItem = grid.dataItem(selRow);
			//获取部门
			var departCode = selectedNode.code;
			var departName =selectedNode.name;
			//获取部门下已选择的角色
			var roles = selectedIds.get(departCode);
			var roleNames= selectedNames.get(departName);
			if (checkstatus) {
				//grid.select(selRow);
				selRow.addClass("k-state-selected");
				if (roles == null) {
					roles = new Map();
					roleNames= new Map();
				}
				roles.put($(obj).val(), $(obj).val());
				roleNames.put(dataItem.name, dataItem.name);
				selectedIds.put(departCode, roles);
				selectedNames.put(departName, roleNames);
			} else {
				//grid.clearSelection(selRow);
				selRow.removeClass("k-state-selected");
				$("input[name='checkall']").prop("checked", false);
				roles.remove($(obj).val());
				if (roles.size == 0) {
					selectedIds.remove(departCode);
					selectedNames.remove(departName);
				} else {
					selectedIds.put(departCode, roles);
					selectedNames.put(departName, roleNames);
				}

			}
		}
		//全选
		function selectAll(obj) {
			var checkstatus = obj.checked;
			var checkboxs = $("input[name='rid']");
			var selRows = checkboxs.closest("tr");
			//获取部门
			var departCode = selectedNode.code;
			var departName =selectedNode.name;
			//获取部门下已选择的角色
			var roles = selectedIds.get(departCode);
			var roleNames= selectedNames.get(departName);
			if (checkstatus) {
				checkboxs.prop("checked", true);
				selRows.addClass("k-state-selected");
				$.each(checkboxs, function(i, checkbox) {
				    var selRow = $(checkbox).closest("tr"), dataItem = grid.dataItem(selRow);
					roles.put($(checkbox).val(), $(checkbox).val());
					roleNames.put(dataItem.name, dataItem.name);
				    selectedIds.put(departCode, roles);
				    selectedNames.put(departName, roleNames);
				});
			} else {
				checkboxs.prop("checked", false);
				selRows.removeClass("k-state-selected");
				roles.clear();
				roleNames.clear();
				selectedIds.remove(departCode);
				selectedNames.remove(departName);
			}
		}
		//grid数据加载完成后执行事件 checkbox选中状态初始化
		function gridInit() {
			if (selectedNode) {
				//获取部门
				var departCode = selectedNode.code;
				if (departCode) {
					//获取部门下已选择的角色
					var roles = selectedIds.get(departCode);
					if (roles) {
						$.each(roles.elements, function(i, selectedId) {
							//默认选中打钩
							var checkbox = $("input[type='checkbox'][value='"
									+ selectedId.key + "']");
							if (checkbox) {
								checkbox.prop("checked", true);
								var selRow = checkbox.closest("tr");
								selRow.addClass("k-state-selected");
							}
						});
					}
				}
			}
		}
		$("#confirmBt").kendoButton({
			imageUrl : "${contextPath}/images/save_as.png"
		});
		var confirmBt = $("#confirmBt").data("kendoButton");
		//确认按钮点击事件绑定
		confirmBt.bind("click", function(e) {
			if (selectedIds && selectedIds.size() > 0) {
			    var departCode,roles;
			    var retval=new Array();
			    $.each(selectedIds.elements,function(i,deptSelectedRoles){		         
			         departCode=deptSelectedRoles.key;
			         roles=deptSelectedRoles.value;
			         retval.push(departCode+":["+roles.values().join(",")+"]");
			    });
			    var departName,roleNames;
			    var retNameval=new Array();
			      $.each(selectedNames.elements,function(i,deptSelectedRoleNames){		         
			         departName=deptSelectedRoleNames.key;
			         roleNames=deptSelectedRoleNames.value;
			         retNameval.push(departName+":["+roleNames.values().join(",")+"]");
			    });
				//console.log(selectedIds.values().join(","));
				parent.setPropertyVal(retval.join("|"),retNameval.join("|"));
				parent.closeAssignChooseDialog();
			} else {
				if (confirm("未选择用户，窗口将直接关闭，确认继续？")) {
					parent.setPropertyVal("","");
					parent.closeAssignChooseDialog();
				}
			}
		});
		$("#resetBt").kendoButton({
			imageUrl : "${contextPath}/images/gem_cancel_1.png"
		});
		var resetBt = $("#resetBt").data("kendoButton");
		//重置按钮点击事件绑定
		resetBt.bind("click", function(e) {
		    if(selectedNode){
			//获取部门
			var departCode = selectedNode.code;
			//获取部门下已选择的角色
			var roles = selectedIds.get(departCode);
			if (roles) {
				$.each(roles.elements, function(i, selectedId) {
					//默认选中打钩
					var checkbox = $("input[type='checkbox'][value='"
							+ selectedId.key + "']");
					if (checkbox) {
						checkbox.prop("checked", false);
						var selRow = checkbox.closest("tr");
						selRow.removeClass("k-state-selected");
					}
				});
			}
			 $("input[name='checkall']").prop("checked", false);
			}
			selectedIds = new Map();
			selectedNames = new Map();
		});
	</script>
</body>
</html>