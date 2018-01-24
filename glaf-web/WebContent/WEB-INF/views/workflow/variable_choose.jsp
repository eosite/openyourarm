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
<title>选择流程变量</title>
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
						流程管理》选择流程变量</td>
					<td style="vertical-align: middle;text-align:left;"><button id="confirmBt" type="button">确认选择</button>
							<button id="resetBt" type="button">重新选择</button></td>
				</tr>
			</table>
		</div>
		<div id="content">
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
		var url="${contextPath}/service/workflow/variable/key?processKey=${param.processKey}";
		var modelId="${param.modelId}";
		var resourceId="${param.modelId}";
		if(modelId!=""&&resourceId!=""){
			url="${contextPath}/service/workflow/variable?modelId="+modelId+"&resourceId="+resourceId;
		}
		$(document).ready(function() {
			$("#grid").kendoGrid({
				"columnMenu" : true,
				"dataSource" :gridDataSource,
				"reorderable" : false,
				"filterable" : false,
				"sortable" : false,
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
					"field" : "name",
					"title" : "变量名称",
					"width" : "200px"
				},{
					"field" : "code",
					"title" : "变量代码",
					"width" : "200px"
				},{
					"field" : "dType",
					"title" : "数据类型",
					"width" : "200px"
				}
				],
				"dataBound": function() {
					//settingRowStyle();
				    var rows = this.items();
		            $(rows).each(function () {
		                var index = $(this).index() + 1;
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
							if (response) {
								var tmp, records = [];
								$.each(response, function(i, v) {
									//遍历行数据
									tmp = {};
									tmp.name=v.name;
									tmp.value=v.value;
									tmp.code=v.code;
									tmp.dType=v.dType;									
									records.push(tmp);
								});
								response.records = records;
							}
							return response;
						}
					},
					transport : {
						read : {
							url : url,
							dataType : "json",
							type : "POST",
							data : {
									
								}
						}
					},		
					serverPaging : false
		 });
		//确认按钮
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
					     parent.setProcessVariable(data);
					     parent.closeDialog();
			 }
			else{
					   if(confirm("未选择流程变量，窗口将直接关闭，确认继续？")){
					      parent.setSubProcessVariable(null);
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