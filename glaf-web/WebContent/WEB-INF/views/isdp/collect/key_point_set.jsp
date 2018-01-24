<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.base64.js"></script>
<script type="text/javascript">
	var width = document.documentElement.clientWidth,height=document.documentElement.clientHeight;
	jQuery(function() {
		
		//设置图表高度及宽度
		$("#chart").height(height/2-20);
		
		
		createGrid();
		createChart();
		$(document).bind("kendo:skinChange", createChart);
	});
	
	function createGrid(){
		var fields={},columns = JSON.parse('${results.columns}');
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
		columnsValue.field="KEY_POINT_PERCENT";
		columnsValue.title="关键点占比";
		columnsValue.width="150";
		columnsValue.format="{0:p1}"
		columns.push(columnsValue);
		
		$("#grid").kendoGrid({
            dataSource: {
                type: "json",
                transport: {
                	contentType: "application/json",
                    type: "POST",
                    read: "${pageContext.request.contextPath}/rs/isdp/collect/datalist?tableName=${param.tableName}"
                },
                schema:{
                	total:"total",
                	data:"rows",
                	model:{
                		fields:fields
                	}
                }
            },
            height: height/2-100,
            groupable: false,
            sortable: true,
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
                    minorUnit:0.2,
                    majorUnit:0.2,
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
		seriesValue.field="KEY_POINT_PERCENT";
		seriesValue.name="关键点占比";
		seriesValue.axis="percent";
		seriesValue.color="#007eff";
		seriesValue.labels={
				format: "{0:p1}"
		}
		seriesValue.tooltip={
				format: "{0:p1}"
		};
		series.push(seriesValue);
		
		var title = "${chartName}";
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
			series:series,
			dataSource: {
                transport: {
                    read: {
                        url: "${pageContext.request.contextPath}/rs/isdp/collect/charts?dataType=list&tableName=${param.tableName}",
                        dataType: "json"
                    }
                }
            }
		});
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
	<br/>
	<div class="x_content_title">
	  <img src="${pageContext.request.contextPath}/images/window.png">&nbsp;
	   ${chartName }
	 </div>
	 <br/>
		<div id="grid"></div>
		<div id="chart"></div>
	</div> 
</body>
</html>