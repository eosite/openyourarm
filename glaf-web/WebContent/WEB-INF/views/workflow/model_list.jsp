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
<title>模型列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<!--ztree-->
<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/dateutil.js"></script>
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
			<table style="border:0px;">
				<tr>
					<td style="width:20px;text-align:left;vertical-align: middle;"><img
						src="${contextPath}/images/workflow_small.png"
						style="vertical-align: middle;width:24px;padding-left: 5px;" /></td>
					<td
						style="width:120px;text-align:left;vertical-align: middle;padding-left:5px;">
						流程管理》工作流建模</td>
				</tr>
			</table>
		</div>
		<div id="content">
		  <div id="horizontal" style="height:100%; width: 100%;">
			<div id="tree" style="border: 0px;">
					<div class="zTreeDemoBackground left">
						<ul id="processCategory" class="ztree"></ul>
					</div>
			</div>
			<div id="list">
			<%-- <div id="filterCondition"
				style="width:80%;height:60px;float: inherit;">
				<table style="width:600px;height:100%;margin:auto">
					<tr>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">流程名称：</span></td>
						<td><input type="text" name="nameLike" class="k-textbox" /></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">关键字：</span></td>
						<td><input type="text" name="key" class="k-textbox" /></td>
						<td style="vertical-align:middle;"><button
								id="searchBt" type="button">查询</button></td>
					</tr>
				</table>
			</div> --%>
			  <div id="grid"></div>
			</div>
		   </div>
	    </div>
		</div>
	<div id="addDialog">
		
	</div>
	<div id="editorDialog">
	
	</div>
	<div id="moveToCategoryDialog">
		<div class="zTreeDemoBackground left" style="height: 320px;">
			<ul id="processCategoryChoose" class="ztree"></ul>
		</div>
		<button id="sureBt" type="button">确认</button>
	</div>
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
		$(document).ready(function() {
			$("#grid").kendoGrid({
				"columnMenu" : true,
				"dataSource" :gridDataSource,
				"reorderable" : true,
				"filterable" : false,
				"sortable" : true,
				"selectable":"multiple",
				"pageable" : {
					"refresh" : true,
					"pageSizes" : [ 10, 15, 30, 50, 100 ],
					"buttonCount" : 15
				},
				"change": function(e) {
					var row = this.select();
					//如果是多选"移动到分类"按钮可操作
					if(row.length>1){
						initToolBarStatus();
						var toolbar = $("#toolbar").data("kendoToolBar");
						toolbar.enable("#moveTo");
					}
					else{
			          var data = this.dataItem(row);
					  controlToolBarStatus(data);
					}
				},
				 toolbar: [
				    { template:"<div id=\"toolbar\" style=\"border:0px;text-align: left;\"></div>"}
				  ],
				"columns" : [ {
			        "field": "rowNumber",
			        "title": "序号",
			        "template": "<span class='row-number'></span>",
			        "width" : "80px"
			    },{
					"field" : "id",
					hidden: true
				},{
					"field" : "deploymentId",
					hidden: true
				},{
					"field" : "name",
					"title" : "模型名称",
					"width" : "200px"
				}, {
					"field" : "key",
					"title" : "关键字",
					"width" : "200px"
				}, {
					"field" : "revision",
					"title" : "版本号",
					"width" : "100px"
				} , {
					"field" : "lastUpdateTime",
					"title" : "更新时间",
					"width" : "150px"
				} ,
				{
					"command": [
						{
			               "text": "<img src=\"${contextPath}/images/eye.png\" style=\"vertical-align:middle;margin-right:5px;\"/>流程图",
			               "name": "diagram",
			               "click": function showDetails(e) {
							 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							 viewDiagram(dataItem.id);
							},
						    "width" : "100px",
							},{
					               "text": "<img src=\"${contextPath}/images/application_view_list.png\" style=\"vertical-align:middle;margin-right:5px;\"/>流程定义列表",
					               "name": "defList",
					               "click": function showDetails(e) {
									 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
									 viewProcessDefinitions(dataItem.key);
										},
							       "width" : "100px",
									} ]}
										],
				"dataBound": function() {
				    var rows = this.items();
		            var page=this.pager.page()-1;
		            var pagesize = this.pager.pageSize();
		            $(rows).each(function () {
		                var index = $(this).index() + 1+page*pagesize;
		                var rowLabel = $(this).find(".row-number");
		                $(rowLabel).html(index);
				    });
		            },
				"scrollable" : {},
				"resizable" : true,
				"groupable" : false
			});
			 $("#toolbar").kendoToolBar({
		        items: [
                { type: "button",id: "create",text: "新建",imageUrl:contextPath+"/images/page_add.png",click : createModel},
				{ type: "button",id: "edit",text: "编辑",enable : false,imageUrl:contextPath+"/images/page_edit.png",click : editModel},
				{ type: "separator" },
				{
					type : "button",
					id : "deploy",
					text : "发布",
					enable : false,
					imageUrl : contextPath + "/images/msn_messenger.png",
					click : openDeployModelDialog
				}, {
					type : "separator"
				}, {
					type : "button",
					id : "import",
					text : "导  入",
					imageUrl : contextPath + "/images/document_small_upload.png",
					click : openImportDialog
				}, {
					type : "button",
					id : "export",
					text : "导  出",
					enable : false,
					imageUrl : contextPath + "/images/download.png",
					click : exportModel
				}, {
					type : "separator"
				}, {
					type : "button",
					id : "delete",
					text : "删  除",
					enable : false,
					imageUrl : contextPath + "/images/page_delete.png",
					click : openDeleteModelDialog
				}, {
					type : "button",
					id : "moveTo",
					text : "移动到",
					enable : false,
					imageUrl : contextPath + "/images/move_to_folder.png",
					click : openMoveToCategoryDialog
				}
		        ]
		    });
			 
			 window.setTimeout(function(){
				$("#toolbar").append($("#condition-01").html());
				
				//查询按钮
				$("#searchBt").kendoButton({
					imageUrl : "${contextPath}/images/search.png"
				});
				var searchBt = $("#searchBt").data("kendoButton");
				  //查询按钮事件绑定
				  searchBt.bind("click",function(e) {
				     var grid=$("#grid").data("kendoGrid"); 
				     grid.dataSource.read();
				     
				});
			 }, 10);
			
		});
		
		var gridDataSource = new kendo.data.DataSource(
				{
					schema : {
						total : "total",
						data : "records",
						parse : function(response) {
							if (response && response.data) {
								var tmp, records = [];
								$.each(response.data, function(i, v) {
									//遍历行数据
									tmp = {};
									tmp.name=v.name;
									tmp.revision=v.version;
									tmp.key=v.key;
									tmp.id=v.id;
									tmp.createTime=v.createTime;
									tmp.deploymentId=v.deploymentId;
									tmp.lastUpdateTime=(new Date(v.lastUpdateTime)).Format("yyyy-MM-dd hh:mm:ss");
									tmp.url=v.url;
									records.push(tmp);
								});
								response.records = records;
							}
							return response;
						}
					},
					transport : {
						parameterMap : function(data) {
							if (data.page < 1) {
								data.page = 1;
							}
							data.start = (data.page - 1)
									* data.pageSize;
							data.size=data.pageSize;
							//设置read data参数值
							//获取查询条件
							var filterObjs=$("#filterCondition input");
							$.each(filterObjs,function(i,filterObj){
							var $filterObj=$(filterObj);
							if($filterObj.val()!=null&&$filterObj.val()!="")
							 data[$filterObj.attr("name")]=$filterObj.val();
							});
							return data;
						},
						read : {
							url : "${contextPath}/service/workflow/models",
							dataType : "json",
							type : "POST",
							data : {
								sort:"id",
								order:"asc",
								category:"workflow"
							}
						}
					},		
                    pageSize : 10,
					serverPaging : true
				});
		/* 		//查询按钮
		$("#searchBt").kendoButton({
			imageUrl : "${contextPath}/images/search.png"
		});
		var searchBt = $("#searchBt").data("kendoButton");
		  //查询按钮事件绑定
		  searchBt.bind("click",function(e) {
		     var grid=$("#grid").data("kendoGrid"); 
		     grid.dataSource.read();
		     
		}); */
		var contextPath='${contextPath}';
		//创建模型
		function createModel() {
			$("#addDialog")
			.kendoWindow(
					{
						title : "新建模型",
						visible : false,
						modal : true
					});
			        var url = "/mx/workflow/management/create?categoryId="+selectedNode.id;
			        $("#addDialog").data("kendoWindow").title("新建模型");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
		//修改模型方法
		function editModel() {
		  //获取选中行
		  var grid=$("#grid").data("kendoGrid"); 
		  var row = grid.select();
          var data = grid.dataItem(row);
          if(data.deploymentId!=null&&data.deploymentId!=""){
        	  if(confirm("此流程已发布，修改后会产生新版本，确认继续修改吗？")){
        		  $.ajax({
      				url : contextPath+"/rs/workflow/copyWorkFlow",
      				type : "post",
      				async : false,
      				dataType : "json",
      				data : {
      					modelId : data.id
      				},
      				success : function(retData) {
      					if (retData) {
      						if (retData.result) {
      							if (retData.result == 1) {
      								refreshGrid();
      								openEditModelDialog(data);
      							} else {
      								alert("对已发布的流程申请修改出错！");
      							}
      						}
      					}
      				},
      				error : function() {
      					alert("对已发布的流程进行修改，出现异常！");
      				}

      			});
        	  }
        	 
          }
          else{
    		  openEditModelDialog(data);
    	  }
		 			
		}
		function openEditModelDialog(data){
			 var url = "/mx/form/workflow/defined/view?modelId="+data.id+"&&modelName="+data.name+"&&modelKey="+data.key;
			  $("#addDialog")
				.kendoWindow(
						{
							title : "流程设计器",
							visible : false,
							modal : true
						});
			  if ($("#addDialog").data("kendoWindow")) {
					$("#addDialog").data("kendoWindow").title("流程设计器");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
				} 		
		}
		//弹出删除模型窗口
		function openDeleteModelDialog() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deleteModel();
					}
				};
				kendo.confirm("确认删除流程\"" + data.name + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}
		}
		//删除模型方法
		function deleteModel() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
	        if (data.id) {
			$.ajax({
				url : contextPath+"/rs/workflow/deleteWorkFlow",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					modelId : data.id
				},
				success : function(data) {
					if (data) {
						if (data.result) {
							if (data.result == 1) {
								refreshGrid();
								alert("删除成功！");
							} else {
								alert("删除失败！");
							}
						}
					}
				},
				error : function() {
					alert("删除异常！");
				}

			});
	        }
		}
		//弹出发布模型窗口
		function openDeployModelDialog() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deployModel();
					}
				};
				kendo.confirm("确认发布流程\"" + data.name + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}

		}
		//移动到分类
		function openMoveToCategoryDialog(){
			initProcessCategoryChooseTree();
			 $("#moveToCategoryDialog")
				.kendoWindow(
						{
							width : 600,
							height : 400,
							title : "移动到流程分类",
							visible : false,
							modal : true
						});
			$("#moveToCategoryDialog").data("kendoWindow").open();
			$("#moveToCategoryDialog").data("kendoWindow").center();
		}
		function initProcessCategoryChooseTree(){
			var setting = {
            view: {
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
					idKey: "id",
					pIdKey: "parentId",
					rootPId: 0
                }
            },
            edit: {
                enable: false
            }
            };
			//获取当前分类节点数据
			var treeObj = $.fn.zTree.getZTreeObj("processCategory");
            var nodes = treeObj.getNodeByParam("id", 0, null).children;
			$.fn.zTree.init($("#processCategoryChoose"), setting, nodes);
		}
		$(document).ready(function(){		
		    //添加到分类确认按钮
			 $("#sureBt").kendoButton({
				imageUrl : "${contextPath}/images/okay.png"
			 });
			  var sureBt = $("#sureBt").data("kendoButton");
				  //确认按钮事件绑定
				  sureBt.bind("click",function(e) {
				   //获取选中节点
				   var treeObj = $.fn.zTree.getZTreeObj("processCategoryChoose");
                   var nodes = treeObj.getSelectedNodes();
				   if(nodes.length==0){
					   alert("请选择分类");
					   return;
				   }
				   var categoryId=nodes[0].id;
				   //获取当前选中的行
				   var grid=$("#grid").data("kendoGrid"); 
				   var rows = grid.select();
				   if(rows.length==0){
					   alert("请选择流程");
					   return;
				   }
				   var data,modelId;
				   $.each(rows,function(i,row){
					   data = grid.dataItem(row);
					   //获取选中的流程ID
					   if(i==0)
					    modelId="'"+data.id+"'";
				       else
						modelId+=",'"+data.id+"'";   
				   });
				   $.ajax({
					url : contextPath+"/rs/workflow/actReCategory/moveToCategory",
					type : "post",
					async : false,
					dataType : "json",
					data : {
						modelId :modelId,
						categoryId:categoryId
					},
					success : function(data) {
						if (data) {
							if (data.result) {
								if (data.result == 1) {
									refreshGrid();
									alert("添加到分类成功！");
									$("#moveToCategoryDialog").data("kendoWindow").close();
								} else {
									alert("添加到分类失败！");
								}
							}
						}
					},
					error : function() {
						alert("添加到分类异常！");
					}

				});
				});
		 });
		//部署模型方法
		function deployModel() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
			$.ajax({
				url : contextPath+"/rs/workflow/deployWorkFlow",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					modelId : data.id,
				},
				success : function(data) {
					if (data) {
						if (data.result) {
							if (data.result == 1) {
								refreshGrid();
								alert("部署成功！");
							} else {
								alert("部署失败！");
							}
						}
					}
				},
				error : function() {
					alert("部署异常！");
				}

			});
			}
		}
		//导出模型方法
		function exportModel() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
				window.location = contextPath+"/rs/workflow/exportWorkFlow?modelId="
						+ data.id;

			}
		}
		$("#importDialog")
				.kendoWindow(
						{
							width : 400,
							height : 200,
							title : "<img src='"+contextPath+"/images/document_small_upload.png' style='vertical-align: middle;'/><span style='margin-left:5px;'>导入模型</span>",
							visible : false,
							modal : true
						});
		//打开流程导入对话框
		function openImportDialog() {
			//弹出新建流程窗口
			$("#importDialog").data("kendoWindow").open();
			$("#importDialog").data("kendoWindow").center();
			var saveUrl = contextPath+"/mx/workflow/management/importWorkFlow?categoryId="+selectedNode.id;
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
						statusFailed : "导入模型失败",
						statusUploaded : "导入模型成功"
					},
					multiple : false,
					success : refreshGrid

				});
			}
		}
		//控制菜单按钮状态
		function controlToolBarStatus(node) {
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#deploy", false);
				toolbar.enable("#import");
				toolbar.enable("#export", false);
				toolbar.enable("#copy", false);
				toolbar.enable("#delete", false);
				toolbar.enable("#moveTo", false);
					if (!node.id || node.id== '') {
						toolbar.enable("#create");
						toolbar.enable("#edit", false);
						toolbar.enable("#deploy", false);
						toolbar.enable("#import");
						toolbar.enable("#export", false);
						toolbar.enable("#copy", false);
						toolbar.enable("#delete", false);
						toolbar.enable("#moveTo", false);
					} else {
						toolbar.enable("#create");
						toolbar.enable("#edit");
						if (node.deploymentId==null || node.deploymentId == '') {
							toolbar.enable("#deploy");
						}
						toolbar.enable("#import");
						toolbar.enable("#export");
						toolbar.enable("#copy");
						toolbar.enable("#delete");
						toolbar.enable("#moveTo");
					}
			}
		}
		function initToolBarStatus(){
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#deploy", false);
				toolbar.enable("#import");
				toolbar.enable("#export", false);
				toolbar.enable("#copy", false);
				toolbar.enable("#delete", false);
				toolbar.enable("#moveTo", false);
			}
		}
		//刷新grid
		function refreshGrid(){
			var grid = $("#grid").data(
			"kendoGrid");
			grid.dataSource.read();
		}
		//查看流程图
		function viewDiagram(modelId){
			$("#addDialog")
			.kendoWindow(
					{
						title : "查看流程图",
						visible : false,
						modal : true
					});
			        var url = "/mx/workflow/management/viewDiagram?procModelId="+modelId;
			        $("#addDialog").data("kendoWindow").title("查看流程图");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
		function viewProcessDefinitions(modelKey){
			
			$("#addDialog")
			.kendoWindow(
					{
						title : "流程定义列表",
						visible : false,
						modal : true
					});
			        $("#addDialog").data("kendoWindow").title("流程定义列表");
			        var url = "/mx/workflow/management/view/process-definitions?modelKey="+modelKey;
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
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
					pIdKey: "parentId",
					rootPId: -1
                }
            },
            edit: {
                enable: true,
				removeTitle:"删除流程分类",
				renameTitle:"修改分类名称",
				showRemoveBtn:showRemoveBtn,
				showRenameBtn:showRenameBtn
            },
			callback:{
				onClick: zTreeOnClick,
				onRename:zTreeOnRename,
				beforeRemove:zTreeBeforeRemove,
				beforeDrop:zTreeBeforeDrop,
				onDrop: zTreeOnDrop
			}
        };
		function showRemoveBtn(treeId, treeNode) {
		    //顶层和未分类节点不显示删除按钮
			if(treeNode.id=="-1"||treeNode.id==""){
				return false;
			}
			return true;
		}
		function showRenameBtn(treeId, treeNode) {
			//顶层和未分类节点不显示重命名按钮
		    if(treeNode.id=="-1"||treeNode.id==""){
				return false;
			}
			return true;
		}
        $(document).ready(function(){		
           $.fn.zTree.initZtree($("#processCategory"),url,setting);
        });
		var url=contextPath+"/rs/workflow/actReCategory/categorys";
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
					rdata.push({"id":"-1","parentId":"-1","name":"--全部--","open":true});
					rdata.push({"id":"0","parentId":"-1","name":"流程分类列表","open":true});
					rdata.push({"id":"","parentId":"-1","name":"未分类流程","open":true});
					var treeObj =$.fn.zTree.init(container,setting, rdata);
					  //默认选中第一个节点
		            var node = treeObj.getNodesByParam("id", "-1", null);
		            treeObj.selectNode(node);
					$("#processCategory_1_a").click();
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
			//顶层和未分类节点禁用新增按钮
			if(treeNode.id=="-1"||treeNode.id==""){
				return;
			}
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增流程分类' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                
                //zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"流程分类" + (newCount++)});
				return zTreeOnAdd(treeId,treeNode);
            });
        };
		//新增节点后台处理
		function zTreeOnAdd(treeId,treeNode){
			 var pId=treeNode.id;
			 var pTreeId=treeNode.treeId;
			 var level=treeNode.level+1;
             var url=contextPath+"/rs/workflow/actReCategory/category/add";
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
					console.log("新增流程分类失败");
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
			 var url=contextPath+"/rs/workflow/actReCategory/category/rename";
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
			 var url=contextPath+"/rs/workflow/actReCategory/category/move";
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
			
			if(!confirm("确定删除分类[" + treeNode.name + "]吗?")){
				return false;
			}
			
			 var categoryId=treeNode.categoryId || treeNode.id;
			 var url=contextPath+"/rs/workflow/actReCategory/category/delete";
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
			grid.dataSource.transport.options.read.url="${contextPath}/service/workflow/models";
			if(treeNode.id!='0'&&treeNode.id!='-1'){
			    delete grid.dataSource.transport.options.read.data["tenantIdLike"];
				grid.dataSource.transport.options.read.data.tenantId=treeNode.id;
			}else if(treeNode.id=='0'){
				delete grid.dataSource.transport.options.read.data["tenantId"];
				grid.dataSource.transport.options.read.data.tenantIdLike="%_%";
			}else if(treeNode.id=='-1'){
				delete grid.dataSource.transport.options.read.data["tenantIdLike"];
				delete grid.dataSource.transport.options.read.data["tenantId"];
			}
			grid.dataSource.read();
			initToolBarStatus();
		}
		</script>
</body>
<script type="text/html" id='condition-01'>
		<div id="filterCondition"
				style="width:50%;float: inherit;">
				<table style="width:600px;height:100%;margin:auto">
					<tr>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">流程名称：</span></td>
						<td><input type="text" name="nameLike" class="k-textbox" /></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">关键字：</span></td>
						<td><input type="text" name="key" class="k-textbox" /></td>
						<td style="vertical-align:middle;"><button
								id="searchBt" type="button">查询</button></td>
					</tr>
				</table>
			</div>
	</script>
</html>