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
<title>模板列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<!--ztree-->
<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
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
						报表定义》模板列表</td>
					<td></td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="horizontal" style="height:100%; width: 100%;">
			<div id="tree" style="border: 0px;">
					<div class="zTreeDemoBackground left">
						<ul id="templateCategory" class="ztree"></ul>
					</div>
			</div>
			<div id="list">
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
		</div>
	</div>
	<div id="addDialog"></div>
	<div id="importDialog">
		<table style="width:90%;height:90%">
			<tr>
				<td style="width:100px;height:30px;">选择文件：</td>
				<td><input type="file" name="uploadfile" id="uploadfile"
					style="width: 250px;" /></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
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
		var splitter = $("#horizontal").kendoSplitter({
		panes : [ {
			collapsed : false,
			collapsible : false,
			collapsedSize : "0px",
			max : "300px",
			resizable : false,
			size : "250px",
			scrollable : true
		}, {
			size : "500px",
			scrollable : true
		} ]
	   });
	   </script>
	   	<script type="text/javascript">
        <!--
        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
					idKey: "id",
					pIdKey: "parentId_",
					rootPId: 0
                }
            },
            edit: {
                enable: true,
				removeTitle:"删除模板分类",
				renameTitle:"修改分类名称"
            },
			callback:{
				onClick: zTreeOnClick,
				onRename:zTreeOnRename,
				beforeRemove:zTreeBeforeRemove,
				beforeDrop:zTreeBeforeDrop,
				onDrop: zTreeOnDrop
			}
        };

        $(document).ready(function(){		
           $.fn.zTree.initZtree($("#templateCategory"),url,setting);
        });
		var url=contextPath+"/rs/reportTemplate/categorys";
        $.fn.extend($.fn.zTree,{
		  initZtree:function (container,url,setting){
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if($.isEmptyObject(rdata)){
						rdata=new Array();
					}
					rdata.push({"id":"0","parentId_":"0","name":"模板类型列表","open":true});
					$.fn.zTree.init(container,setting, rdata);
				},
				error : function() {
					console.log("获取分类数据失败");
				}

		   }
		  );
		}});
        var newCount = 1;
		//新增节点事件
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增模板分类' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                
                //zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"模板分类" + (newCount++)});
				return zTreeOnAdd(treeId,treeNode);
            });
        };
		//新增节点后台处理
		function zTreeOnAdd(treeId,treeNode){
			 var pId=treeNode.id;
			 var pTreeId=treeNode.treeId==undefined?"":treeNode.treeId;
			 var level=treeNode.level+1;
             var url=contextPath+"/rs/reportTemplate/category/add";
			 var name="新建分类" + (newCount++);
			 var params={"pId":pId,"pTreeId":pTreeId,"level":level,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					if(rdata!=null&&rdata.result==1){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						zTree.addNodes(treeNode, {id:rdata.id, pId:pId,name:name});
						result= true;
					}else{
						result= false;
					}
					
				},
				error : function() {
					console.log("新增模板分类失败");
					result= false;
				}
		   });
		   return result;
		}
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
		//ztree树节点重命名事件
		function zTreeOnRename(event, treeId, treeNode, isCancel){
			 var categoryId=treeNode.id;
			 var name=treeNode.name;
			 var url=contextPath+"/rs/reportTemplate/category/rename";
			 var params={"categoryId":categoryId,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("重命名失败");
					result= false;
				}

		   }
		  );
		  return result;
		}
		function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
          if(targetNode==null||(targetNode.id==0&&moveType=='prev')){
				 return false;
			 }
        };
		function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
			 
			 var categoryId=treeNodes[0].id;
		     var treeId=(targetNode.treeId_!=undefined&&targetNode.treeId_!=null?targetNode.treeId_:"")+categoryId+"|";
			 var url=contextPath+"/rs/reportTemplate/category/move";
			 var params={"categoryId":categoryId,"pId":targetNode.id,"treeId":treeId,"moveType":moveType};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("移动失败");
					result= false;
				}

		   }
		  );
		  return result;
		}
		//ztree树节点移除事件
		function zTreeBeforeRemove(treeId, treeNode){
			 var categoryId=treeNode.id;
			 var url=contextPath+"/rs/reportTemplate/category/delete";
			 var params={"categoryId":categoryId};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("删除分类失败");
					result= false;
				}
		   }
		  );
		  return result;
		}
		var selectedNode;
		//ztree树节点点击事件
		function zTreeOnClick(event, treeId, treeNode){
			selectedNode=treeNode;
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.transport.options.read.url="${contextPath}/rs/reportTemplate/data?categoryId="+treeNode.id;
			grid.dataSource.read();
		}
		</script>
	   <script>
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
													controlToolBarStatus(data);
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
															"width" : "100px",
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
															"width" : "90px",
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
																		"text" : "<img src=\"${contextPath}/images/page_edit.png\" style=\"vertical-align:middle;margin-right:5px;\"/>编辑",
																		"name" : "editBt",
																		"click" : function showDetails(
																				e) {
																			var dataItem = this
																					.dataItem(jQuery(
																							e.currentTarget)
																							.closest(
																									"tr"));
																			if(dataItem.publish==0){
																			editTemplate(dataItem.id);
																			}else {
																				return;
																			}
																		},
																		"width" : "100px",
																	},
																	{
																		"text" : "<img src=\"${contextPath}/images/page_add.png\" style=\"vertical-align:middle;margin-right:5px;\"/>新版本",
																		"name" : "createNewBt",
																		"click" : function newTemplate(
																				e) {
																					  var dataItem = this
																					.dataItem(jQuery(
																							e.currentTarget)
																							.closest(
																									"tr"));
																			if(dataItem.publish==1){
																			if(confirm("确认创建新版本？")){
																			 
																			   createNewTemplate(dataItem.id);
																			}
																			else{
																			  return;
																			}
																			}
																		},
																		"width" : "100px",
																	},
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

							$("#toolbar")
									.kendoToolBar(
											{
												items : [
														{
															type : "button",
															id : "create",
															text : "新建",
															imageUrl : contextPath
																	+ "/images/page_add.png",
															click : createTemplate
														},
														{
															type : "separator"
														},
														{
															type : "button",
															id : "deploy",
															text : "发布",
															enable : false,
															imageUrl : contextPath
																	+ "/images/msn_messenger.png",
															click:openDeployTemplateDialog
														},
														{
															type : "separator"
														}, {
															type : "button",
															id : "import",
															text : "导  入",
															imageUrl : contextPath + "/images/document_small_upload.png",
															click : openImportDialog
														},
														{
															type : "button",
															id : "export",
															text : "导  出",
															enable : false,
															imageUrl : contextPath
																	+ "/images/download.png",
															click:exportTemplate
														},
														{
															type : "separator"
														},{
															type : "button",
															id : "enable",
															text : "启用",
															enable : false,
															imageUrl : contextPath
																	+ "/images/page_refresh.png",
															click:openEnableTemplateDialog
														},
														{
															type : "button",
															id : "delete",
															text : "禁用",
															enable : false,
															imageUrl : contextPath
																	+ "/images/page_delete.png",
															click:openDeleteTemplateDialog
														}  ]
											});
						});
	
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
		
		/**
		*创建模板
		*/
		function createTemplate() {
			$("#addDialog").kendoWindow({
				title : "新建模板",
				visible : false,
				modal : true
			});
			$("#addDialog").data("kendoWindow").title("新建模板");
			$("#addDialog")
					.data("kendoWindow")
					.refresh(
							"${server_scheme}://${header['host']}/glaf-report/view/stml/report/designer/js?categoryId="+selectedNode.id);
			$("#addDialog").data("kendoWindow").toggleMaximization();
			$("#addDialog").data("kendoWindow").open();
		}
		/**
		*创建新版本模板
		*/
		function createNewTemplate(id){
		 $.ajax({
      				url : "${server_scheme}://${header['host']}/glaf-report/view/reportTemplate/createNew",
      				type : "post",
      				async : false,
      				dataType : "json",
      				data : {
      					reportId : id,
      					actorId:"${actorId}",
						categoryId:selectedNode.id
      				},
      				success : function(retData) {
      					if (retData) {
      						if (retData.result) {
      							if (retData.result == 1&&retData.reportId&&retData.reportId!="") {
      								refreshGrid();
      								editTemplate(retData.reportId);
      							} else {
      								alert("创建新版本出错！");
      							}
      						}
      					}
      				},
      				error : function() {
      					alert("创建新版本异常！");
      				}

      			});
		}
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
		//修改模型方法
		function editTemplate(id) {
			openEditTemplateDialog(id);
		}
		function openEditTemplateDialog(id) {
			$("#addDialog").kendoWindow({
				title : "修改模板",
				visible : false,
				modal : true
			});
			$("#addDialog").data("kendoWindow").title("修改模板");
			$("#addDialog")
					.data("kendoWindow")
					.refresh(
							"${server_scheme}://${header['host']}/glaf-report/view/stml/report/designer/js?reportId="
									+ id+"&actorId=${actorId}");
			$("#addDialog").data("kendoWindow").toggleMaximization();
			$("#addDialog").data("kendoWindow").open();
		}
		//弹出禁用模型窗口
		function openDeleteTemplateDialog() {
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deleteTemplate();
					}
				};
				kendo.confirm("确认禁用模板\"" + data.name + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}
		}
		//禁用模型方法
		function deleteTemplate() {
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			if (data.id) {
				$.ajax({
					url : "${server_scheme}://${header['host']}/glaf-report/view/reportTemplate/updateStatus",
					type : "post",
					async : false,
					dataType : "json",
					data : {
						reportId : data.id,
						status:-1,
						actorId:"${actorId}"
					},
					success : function(data) {
						if (data) {
							if (data.result) {
								if (data.result == 1) {
									refreshGrid();
									alert("禁用成功！");
								} else {
									alert("禁用失败！");
								}
							}
						}
					},
					error : function() {
						alert("禁用异常！");
					}

				});
			}
		}
		//弹出禁用模型窗口
		function openEnableTemplateDialog() {
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deleteTemplate();
					}
				};
				kendo.confirm("确认启用模板\"" + data.name + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}
		}
		//启用模板方法
		function enableTemplate() {
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			if (data.id) {
				$.ajax({
					url : "${server_scheme}://${header['host']}/glaf-report/view/reportTemplate/updateStatus",
					type : "post",
					async : false,
					dataType : "json",
					data : {
						reportId : data.id,
						status:1,
						actorId:"${actorId}"
					},
					success : function(data) {
						if (data) {
							if (data.result) {
								if (data.result == 1) {
									refreshGrid();
									alert("启用成功！");
								} else {
									alert("启用失败！");
								}
							}
						}
					},
					error : function() {
						alert("启用异常！");
					}

				});
			}
		}
		
		function openDeployTemplateDialog() {
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deployTemplate();
					}
				};
				kendo.confirm("确认发布模板\"" + data.name + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}

		}
		//部署模型方法
		function deployTemplate() {
			var grid = $("#grid").data("kendoGrid");
			var row = grid.select();
			var data = grid.dataItem(row);
			if (data.id) {
				$.ajax({
					url : "${server_scheme}://${header['host']}/glaf-report/view/reportTemplate/deploy",
					type : "post",
					async : false,
					dataType : "json",
					data : {
						reportId : data.id,
						actorId:"${actorId}"
					},
					success : function(data) {
						if (data) {
							if (data.result) {
								if (data.result == 1) {
									refreshGrid();
									alert("发布成功！");
								} else {
									alert("发布失败！");
								}
							}
						}
					},
					error : function() {
						alert("发布异常！");
					}

				});
			}
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
		//打开导入对话框
		$("#importDialog")
				.kendoWindow(
						{
							width : 400,
							height : 200,
							title : "<img src='"+contextPath+"/images/document_small_upload.png' style='vertical-align: middle;'/><span style='margin-left:5px;'>导入模板</span>",
							visible : false,
							modal : true
						});
		function openImportDialog() {
			//弹出导入模板窗口
			$("#importDialog").data("kendoWindow").open();
			$("#importDialog").data("kendoWindow").center();
			var saveUrl = "${server_scheme}://${header['host']}/glaf-report/view/reportTemplate/import?categoryId="+selectedNode.id+"&actorId=${actorId}";
			var upload = $("#uploadfile").data("kendoUpload");
			if (upload) {
				upload.setOptions({
					saveUrl : saveUrl,
					files : null
				});
			} else {
				$("#uploadfile").kendoUpload({
					async : {
						saveUrl : saveUrl,
						removeUrl : "remove",
						autoUpload : false
					},
					localization : {
						statusFailed : "导入模板失败",
						statusUploaded : "导入模板成功"
					},
					multiple : false,
					success : refreshGrid

				});
			}
		}
		//刷新grid
		function refreshGrid() {
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
		}
		//控制菜单按钮状态
		function controlToolBarStatus(node) {
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#deploy", false);
				toolbar.enable("#export", false);
				toolbar.enable("#copy", false);
				toolbar.enable("#delete", false);
				toolbar.enable("#enable", false);
				if (!node.id || node.id == '') {
					toolbar.enable("#create");
					toolbar.enable("#edit", false);
					toolbar.enable("#deploy", false);
					toolbar.enable("#export", false);
					toolbar.enable("#copy", false);
					toolbar.enable("#delete", false);
					toolbar.enable("#enable", false);
				} else {
					toolbar.enable("#create");
					toolbar.enable("#edit");
					if (node.publish != 1) {
						toolbar.enable("#deploy");
					}
					toolbar.enable("#export");
					toolbar.enable("#copy");
					if (node.status != -1) {
						toolbar.enable("#delete");
					}
				    else{
				        toolbar.enable("#enable");
				    }
				}
			}
		}
	</script>

</body>
</html>