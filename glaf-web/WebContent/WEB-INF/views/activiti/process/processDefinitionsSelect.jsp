<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/webfile/js/jquery.kendo.extends.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
		流程名称:<input type='text' class='k-textbox' id="nameLike"  />
		<button type="button" id="" class="k-button" onclick="javascript:query();">查询</button>
		<button type="button" id="" class="k-button" onclick="javascript:sure();">确定</button>       
   </div>
</script>
<script type="text/javascript">
var fn = "${param.fn}",$grid;
function sure(){
	var selects = $grid.select();
	if(selects.length == 0){
		alert('请选择!');
	} else {
		var $parent = window.opener || parent;
		var $fn = $parent[fn],$target = $parent.document.getElementById("${param.targetId}");
		var data = $grid.dataItem(selects[0]);
		if($fn){
			$fn.call($target,data);
		} else if($target){
			$target.value = JSON.stringify(data);
		}
	}
}

	jQuery(function() {
		var gridDataSource = new kendo.data.DataSource(
				{
					schema : {
						total : "totalRecords",
						data : "records",
						parse: function(response) {
						      if(response && response.records){
						    	  var tmp = {},records = [];
						    	  $.each(response.records,function(i,v){
						    		  var o = tmp[v.key];
						    		  if(o){
						    			  if(o.version < v.version){
						    				  tmp[v.key] = v;
						    			  }
						    		  } else {
							    		  tmp[v.key] = v;
						    		  }
						    	  });
						    	  var total = 0;
						    	  $.each(tmp,function(k,v){
						    		  records.push(v);
						    		  total ++;
						    	  });
						    	  response.records = records;
						    	  response.totalRecords = total;
						    	  tmp = null;
						      }
						      return response;
						    }
					},
					transport : {
						parameterMap : function(options) {
							if (options.page < 1) {
								options.page = 1;
							}
							options.startIndex = (options.page - 1)
									* options.pageSize;
							options.results = 99999999;
							return options;
						},
						read : {
							url : "${contextPath}/mx/activiti/process/processDefinitionJson",
							dataType : "json",
							type : "POST",
							data : {
								sort : 'id',
								dir : 'asc',
								lastVersion : true
							}
						}
					},
					pageSize : 10,
					serverPaging : false
				});
		
		var options = {
			dataSource : gridDataSource,
			height : x_height * 0.7,
			pageable : {
				refresh : true,
				pageSizes : [ 5, 10, 15, 20, 25, 50, 100, 200, 500 ],
				buttonCount : 10
			},
			selectable : "single",
			columns : [ {
				field : 'id',
				title : '流程定义编号',
				width : '200px'
			}, {
				field : "key",
				title : "Key",
				width : "150px"
			}, {
				field : "name",
				title : "流程名称",
				width : "200px"
			}, {
				field : "version",
				title : "版本",
				width : "80px"
			}, {
				field : "diagramResourceName",
				title : "流程图",
				width : "150px"
			}, {
				title : '功能键',
				template : function(dataItem){
					var buttons = [
						"<button class='k-button' image='process.gif' t='image/viewImage'>流程图</button>",
						"<button class='k-button' image='lightbulb.png' t='process/processInstances'>流程实例</button>",
						"<button class='k-button' image='lightbulb_off.png' t='history/historyProcessInstances'>历史实例</button>"
					];
					return buttons.join(' ');
					
				}
			} ],
			scrollable : {},
			resizable : true,
			groupable : false,
			dataBound: function(e) {
				var that = this;
				$("#grid .k-button").each(function(i,v){
					if($(this).attr("image")){
						$(this).kendoButton({
							imageUrl : "${contextPath}/images/" + $(this).attr("image"),
							click : function(e){
								var $this = e.sender.wrapper;
								var dataItem = $this.closest("[data-role=grid]").data("kendoGrid").dataItem($this.closest("tr"));
								var url = "${contextPath}/mx/activiti/" + $this.attr("t") + "?processDefinitionId=" + dataItem.processDefinitionId;
								window.open(url);
							}
						});
					}
				});
			}
		};
		
		if(fn){
			options.toolbar = kendo.template(jQuery("#template").html());
		}

		$grid = $("#grid").kendoGrid(options).data("kendoGrid");
		
	});

	function query() {
		
		var nameLike = $("#nameLike").val();
		$grid.dataSource.filter({
			logic: "or",
			filters : [
	           	{field: "name", operator: "contains", value: nameLike}
			]
		});
	}

	function addRow() {
		editRow("${contextPath}/mx/form/video/edit");
	}

	function editRow(link) {
		jQuery.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑监控信息",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			fadeIn : 100,
			area : [ '620px', '345px' ],
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
			<img src="<%=request.getContextPath()%>/images/window.png"
				alt="流程定义列表">&nbsp; 流程定义列表
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