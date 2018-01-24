<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.base64.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
   		<label>分组字段：</label><input type="text" id="groupByField" />
   </div>
</script>
<script type="text/javascript">
	var width = document.documentElement.clientWidth,height=document.documentElement.clientHeight;
	var fields={},
		whereCondition={COMPLETION_TYPE:"${param.completionType}"},
		tree = "${param.tree}",
		columns = JSON.parse('${results.columns}');
	var gridurl = "${pageContext.request.contextPath}/rs/isdp/collect/dataAggregatelist?tableName=${param.tableName}",
		charturl = "${pageContext.request.contextPath}/rs/isdp/collect/charts?dataType=aggregatesum&tableName=${param.tableName}";
		
	$(function() {
		if(tree==1){
			$("#main_content").height(height-20);
			$("#main_content").kendoSplitter({
	            orientation: "horizontal",
	            panes: [
					{ collapsible: true, size: "200px" },
					{ collapsible: false }
	            ]
	        });
		}
		
        //设置图表高度及宽度
		$("#chart").height(height/2-20);
		
		createGrid();
		createChart();
		$(document).bind("kendo:skinChange", createChart);
		
		$("#groupByField").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "value",
            dataSource: JSON.parse('${results.groupByField}'),
            change:function(e){
            	whereCondition.groupBy= this.value();
            	
            	var grid = jQuery("#grid").data("kendoGrid");
        		grid.setDataSource(createGridDataSource(gridurl+"&whereCondition="+encodeURI(JSON.stringify(whereCondition))));
        		
        		var chart = $("#chart").data("kendoChart");
        		chart.setDataSource(createChartDataSource(charturl+"&whereCondition="+encodeURI(JSON.stringify(whereCondition))));
            }
        });
		
		var dropdownlist = $("#groupByField").data("kendoDropDownList");
		var dataItem = dropdownlist.dataItem(0);
		whereCondition.groupBy= dataItem.value;
	});
	
	function createGrid(){
		for(var i=0;i<columns.length;i++){
			columns[i].width="150";
			
			var type;
			if(columns[i].dtype=="string"){
				type = "string";
			}else if(columns[i].dtype=="i4"){
				type = "number";
			}else if(columns[i].dtype=="datetime"){
				type = "date";
			}
			fields[columns[i].field]={type:type};
		}
		
		var columnsValue = {};
		columnsValue.field="NODE_FINISH_PERCENT";
		columnsValue.title="完成率";
		columnsValue.width="150";
		columnsValue.format="{0:p1}"
		columns.push(columnsValue);
		
		columnsValue = {};
		columnsValue.field="NODE_FIRST_INSPECTION_PASS_PERCENT";
		columnsValue.title="首次通过率";
		columnsValue.width="150";
		columnsValue.format="{0:p1}"
		columns.push(columnsValue);
		
		$("#grid").kendoGrid({
            height: height/2-100,
            groupable: false,
            sortable: true,
            toolbar: kendo.template(jQuery("#template").html()),
            columns: columns
        });
	}
	
	function createChart() {
        $("#chart").kendoChart({
            legend: {
                position: "bottom"
            },
            categoryAxis: {
                field: "INDEX_NAME",
                labels: {
                    rotation: 0
                },
                crosshair: {
                    visible: true
                }
            },
            valueAxis: [
                {
                	name:"quantity"
                },{
                	name:"percent",
                	labels: {
                        format: "{0:p0}"
                    },
                    color: "#007eff",
                    minorUnit:0.1,
                    majorUnit:0.1,
                    min:0,
                    max:1
                }
            ],
            tooltip: {
                visible: true,
                shared: true,
                format: "NO"
            }
        });
        
        queryCharts();
    }
	
	function queryCharts(){
		var series = JSON.parse('${results.series}');
		
		
		for(var i=0;i<series.length;i++){
			series[i].type="column";
			series[i].axis="quantity";
		}
		
		var seriesValue = {};
		seriesValue.type="line";
		seriesValue.field="NODE_FINISH_PERCENT";
		seriesValue.name="完成率";
		seriesValue.axis="percent";
		seriesValue.color="#007eff";
		seriesValue.labels={
				format: "{0:p1}"
		}
		seriesValue.tooltip={
				format: "{0:p1}"
		};
		series.push(seriesValue);
		
		seriesValue = {};
		seriesValue.type="line";
		seriesValue.field="NODE_FIRST_INSPECTION_PASS_PERCENT";
		seriesValue.name="首次通过率";
		seriesValue.axis="percent";
		seriesValue.color="#007eff";
		seriesValue.labels={
				format: "{0:p1}"
		}
		seriesValue.tooltip={
				format: "{0:p1}"
		};
		series.push(seriesValue);
		
		var title = " ${chartName }";
		document.title = title;
		
		var chart = $("#chart").data("kendoChart");
		chart.setOptions({
			title: {
                text: title
            },
            seriesDefaults: {
               labels: {
                 visible: true
               }
            },
			series:series
		});
	}
	
	function zTreeOnClick(event, treeId, treeNode,clickFlag){
		var grid = jQuery("#grid").data("kendoGrid");
		var chart = $("#chart").data("kendoChart");
		
		//获取新数据源
		if(treeNode.zTreeType=="p"){
			//点击的是项目标段
			whereCondition.DATABASE_ID=treeNode.treeData;
			whereCondition.RUNYEAR=null;
			whereCondition.RUNMONTH=null;
		}else if(treeNode.zTreeType=="y"){
			//点击的是年
			var parentNode = treeNode.getParentNode();
			
			whereCondition.DATABASE_ID=parentNode.treeData;
			whereCondition.RUNYEAR=treeNode.treeData;
			whereCondition.RUNMONTH=null;
		}else if(treeNode.zTreeType=="m"){
			//点击的是月
			var parentNode = treeNode.getParentNode();
			var projectNode = parentNode.getParentNode();
			
			whereCondition.DATABASE_ID=projectNode.treeData;
			whereCondition.RUNYEAR=parentNode.treeData;
			whereCondition.RUNMONTH=treeNode.treeData;
		}
		//更新列表及图表
		
		grid.setDataSource(createGridDataSource(gridurl+"&whereCondition="+encodeURI(JSON.stringify(whereCondition))));
		chart.setDataSource(createChartDataSource(charturl+"&whereCondition="+encodeURI(JSON.stringify(whereCondition))));
	}
	
	//创建grid表格数据源
	function createGridDataSource(gridurl){
		var dataSource = new kendo.data.DataSource({
			type: "json",
            transport: {
            	contentType: "application/json",
                type: "POST",
                read: gridurl
            },
            schema:{
            	total:"total",
            	data:"rows",
            	model:{
            		fields:fields
            	}
            }
		});
		return dataSource;
	}
	
	//创建图表数据源
	function createChartDataSource(charturl){
		var dataSource = new kendo.data.DataSource({
			transport: {
                read: {
                    url: charturl,
                    dataType: "json"
                }
            }
		});
		return dataSource;
	}
</script>
</head>
<body>
	<div id="main_content">
		<div id="left-panel">
		    <c:if test="${param.tree==1 }">
			    <%@include file="/WEB-INF/views/isdp/global/proj_tree_yyyymm.jsp" %>
		    </c:if>
		</div>
		<div id="center-panel">
		    <br/>
		 	<div class="x_content_title">
			<img src="${pageContext.request.contextPath}/images/window.png">&nbsp;
			${chartName }
			</div>
			<br/>
			<div id="grid"></div>
			<div id="chart"></div>
		</div>
	</div>
</body>
</html>