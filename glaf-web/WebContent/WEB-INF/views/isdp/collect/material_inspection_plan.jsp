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
		$("#chart_material").height(height/2-20);
		
		
		createGrid_Meterial();
		createChart_Meterial();
		$(document).bind("kendo:skinChange", createChart_Meterial);
	});
	
	function createGrid_Meterial(){
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
		
		$("#grid_material").kendoGrid({
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
	
	function createChart_Meterial() {
        $("#chart_material").kendoChart({
            legend: {
                position: "bottom"
            },
            categoryAxis: {
                field: "MATERIAL_NAME",
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
                }
            ],
            tooltip: {
                visible: true,
                shared: true,
                format: "N0"
            }
        });
        
        queryCharts_Material()
    }
	
	function queryCharts_Material(){
		var series = JSON.parse('${results.series}');
		
		
		for(var i=0;i<series.length;i++){
			series[i].type="area";
			series[i].axis="quantity";
		}
		
		var title = "${chartName }";
		document.title = title;
		
		var chart = $("#chart_material").data("kendoChart");
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
	<div id="main_content_material" class="k-content">
	<br/>
	<div class="x_content_title">
	  <img src="${pageContext.request.contextPath}/images/window.png">&nbsp;
	    ${chartName }
	 </div>
	 <br/>
		<div id="grid_material"></div>
		<div id="chart_material"></div>
	</div> 
</body>
</html>