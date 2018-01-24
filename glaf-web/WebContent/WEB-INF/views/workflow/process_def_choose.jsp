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
<title>选择流程定义</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
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
.suspendedStyle{
 background-color:red;
}
.hiddenStyle{
 display:none;
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
						style="width:200px;text-align:left;vertical-align: middle;padding-left:5px;">
						流程管理》选择流程定义</td>
					<td style="vertical-align: middle;text-align:left;"><button id="confirmBt" type="button">确认选择</button>
							<button id="resetBt" type="button">重新选择</button></td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="filterCondition"
				style="width:80%;height:60px;float: inherit;">
				<table style="width:730px;height:100%;margin:auto">
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
						<td><input type="text" name="key" class="k-textbox" value="${param.modelKey}"/></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">挂起：</span></td>
						<td><input type="checkbox" id="suspended" name="suspended" value="true"/></td>
						<td style="vertical-align:middle;"><button
								id="searchBt" type="button">查询</button></td>

					</tr>
				</table>
			</div>
			<div id="grid"></div>
		</div>
	</div>
	<div id="addDialog">
		
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
		$(document).ready(function() {
			$("#grid").kendoGrid({
				"columnMenu" : true,
				"dataSource" :gridDataSource,
				"reorderable" : true,
				"filterable" : false,
				"sortable" : true,
				"selectable":"row",
				"pageable" : {
					"refresh" : true,
					"pageSizes" : [ 10, 15, 30, 50, 100 ],
					"buttonCount" : 15
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
					"title" : "定义唯一标识",
					"width" : "200px"
				},{
					"field" : "deploymentId",
					hidden: true
				},{
					"field" : "name",
					"title" : "流程名称",
					"width" : "200px"
				}, {
					"field" : "key",
					"title" : "关键字",
					"width" : "200px"
				}, {
					"field" : "version",
					"title" : "版本号",
					"width" : "100px"
				}  ,
				{
					"field" : "suspended",
					hidden: true
				},
				{
					"command": [
						{
			               "text": "<img src=\"${contextPath}/images/eye.png\" style=\"vertical-align:middle;margin-right:5px;\"/>流程图",
			               "name": "diagram",
			               "click": function showDetails(e) {
							 dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							 viewDiagram(dataItem.deploymentId);
							},
						    "width" : "100px",
							},{
					               "text": "<img src=\"${contextPath}/images/setting_tools.png\" style=\"vertical-align:middle;margin-right:5px;\"/>流程定义",
					               "name": "diagram",
					               "click": function showDetails(e) {
									 dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
									 viewProcessDef(dataItem.id);
									},
								    "width" : "100px",
									},{
					               "text": "<img src=\"${contextPath}/images/download.png\" style=\"vertical-align:middle;margin-right:5px;\"/>导出定义",
					               "name": "defList",
					               "click": function showDetails(e) {
									 dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
									 exportModel(dataItem.id);
										},
							       "width" : "100px",
									},{
					               "text": "<img src=\"${contextPath}/images/control-stop-square.png\" style=\"vertical-align:middle;margin-right:5px;\"/>挂起",
					               "name": "suspended",
					               "click": function showDetails(e) {
									 dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
									 suspend(dataItem.id);
										},
							       "width" : "100px",
									},{
					               "text": "<img src=\"${contextPath}/images/control.png\" style=\"vertical-align:middle;margin-right:5px;\"/>激活",
					               "name": "activation",
					               "click": function showDetails(e) {
									 dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
									 activation(dataItem.id);
										},
							       "width" : "100px",
									}  ]}
				],
				"dataBound": function() {
					settingRowStyle();
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
									tmp.version=v.version;
									tmp.key=v.key;
									tmp.id=v.id;									
									tmp.url=v.url;
									tmp.deploymentId=v.deploymentId;
									tmp.suspended=v.suspended;
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
							if(filterObj.type=='checkbox'){
								if(filterObj.checked==true)
								data[$filterObj.attr("name")]=$filterObj.val();	
							}
							else{
							if($filterObj.val()!=null&&$filterObj.val()!="")
							 data[$filterObj.attr("name")]=$filterObj.val();
							}
							 });
							return data;
						},
						read : {
							url : "${contextPath}/service/workflow/process-definitions",
							dataType : "json",
							type : "POST",
							data : {
								sort:"version",
								order:"desc"
							}
						}
					},		
                    pageSize : 10,
					serverPaging : true
				});
		//查询按钮
		$("#searchBt").kendoButton({
			imageUrl : "${contextPath}/images/search.png"
		});
		var searchBt = $("#searchBt").data("kendoButton");
		  //查询按钮事件绑定
		  searchBt.bind("click",function(e) {
			  refreshGrid();
		     
		});
		$("#confirmBt").kendoButton({
				imageUrl : "${contextPath}/images/save_as.png"
			});
		var confirmBt = $("#confirmBt").data("kendoButton");
		//确认按钮点击事件绑定
		confirmBt.bind("click",function(e) {
			//获取选中行
			var grid = $("#grid").data("kendoGrid");
            var row = grid.select();
            var data = grid.dataItem(row);
			if(data){
					     parent.setSubProcessVal(data.key,data.name);
					     parent.closeDialog();
			 }
			else{
					   if(confirm("未选择流程定义，窗口将直接关闭，确认继续？")){
					      parent.setSubProcessVal("","");
					      parent.closeDialog();
					   }
					}
			});
		$("#resetBt").kendoButton({
				imageUrl : "${contextPath}/images/gem_cancel_1.png"
			});
		var resetBt = $("#resetBt").data("kendoButton");
		//重置按钮点击事件绑定
		resetBt.bind("click",function(e) {
			var grid = $("#grid").data("kendoGrid");
			grid.clearSelection();		
		});
		var contextPath='${contextPath}';	
		//刷新grid
		function refreshGrid(){
			var grid = $("#grid").data(
			"kendoGrid");
			grid.dataSource.read();
		}
		//查看流程图
		function viewDiagram(procDeployId){
			$("#addDialog")
			.kendoWindow(
					{
						width : "95%",
						height : "95%",
						title : "查看流程图",
						visible : false,
						modal : true
					});
			        var url = "/mx/workflow/management/viewDiagram?procDeployId="+procDeployId;
			        $("#addDialog").data("kendoWindow").title("查看流程图");
			        $("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").open();
					$("#addDialog").data("kendoWindow").center();	
		}
		//查看流程定义
		function viewProcessDef(processDefinitionId){
			$("#addDialog")
			.kendoWindow(
					{
						width : "95%",
						height : "95%",
						title : "查看流程定义",
						visible : false,
						modal : true
					});
			        var url = "/mx/workflow/management/viewProcessDef?processDefinitionId="+processDefinitionId;
			        $("#addDialog").data("kendoWindow").title("查看流程定义");
			        $("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").open();
					$("#addDialog").data("kendoWindow").center();	
		}
		/**
		*导出流程定义
		*/
		function exportModel(proDefId){
		     window.location = contextPath+"/rs/workflow/exportWorkFlowByProcDefId?processDefinitionId="
						+ proDefId;
		}
		function suspend(proDefId){
			$.ajax({
				url : contextPath+"/service/workflow/process-suspend-activa/",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"processDefinitionId":proDefId,
					"action" : "suspend",
					//是否改变流程实例的状态
					"includeProcessInstances" : "false"
					//暂停触发的时间
				    //"date":"2013-04-15T00:42:12Z"
				},
				statusCode:{
					200:function(){
						alert("请求暂停流程成功！");
						refreshGrid();
					},
					404:function(){
						alert("找不到请求的流程定义");
					},
					409:function(){
						alert("请求的流程已经暂停了");
					}
				}
			});
			
		}
		function activation(proDefId){
			$.ajax({
				url : contextPath+"/service/workflow/process-suspend-activa/",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"processDefinitionId":proDefId,
					"action" : "activate",
					//是否改变流程实例的状态
					"includeProcessInstances" : "true"
					//暂停触发的时间
				    //"date":"2013-04-15T00:42:12Z"
				},
				statusCode:{
					200:function(){
						alert("请求暂停流程成功！");
						refreshGrid();
					},
					404:function(){
						alert("找不到请求的流程定义");
					},
					409:function(){
						alert("请求的流程已经暂停了");
					}
				}
			});
			
		}
		//设置行样式
		function settingRowStyle(){
		    //获取当前行
		    var rows=$(".k-grid-content").find("tr[role='row']");
		    $.each(rows,function(i,row){
		       //获取行对应暂停状态值
		       var suspendedCol=$(row).find("td")[6];
		       var suspended=$(suspendedCol).text();
		       var suspBt=$(row).find(".k-grid-suspended");
		       var actBt=$(row).find(".k-grid-activation");
		          if(suspended=="true")
		         {
		          $(row).addClass("suspendedStyle");
		          $(suspBt).addClass("hiddenStyle");
		          $(actBt).removeClass("hiddenStyle");
		         }
		          else
		        { 
		          $(row).removeClass("suspendedStyle");
		          $(suspBt).removeClass("hiddenStyle");
		          $(actBt).addClass("hiddenStyle");
		        }
		       }
		    );
		}
	</script>
</body>
</html>