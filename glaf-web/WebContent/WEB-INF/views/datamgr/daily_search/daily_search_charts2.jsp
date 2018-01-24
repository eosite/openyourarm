<%@page import="com.glaf.datamgr.domain.SqlDefinition"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日常统计查询</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.base64.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
		<table><tr>
		<td><button type="button" id="redirectButton" class="k-button" onclick="javascript:window.location='${pageContext.request.contextPath}/mx/datamgr/dailySearch?method=charts&type=d'">按日期统计</button></td>
		<td><label>建设项目：</label></td><td><input type="text" id="projectDropDownList" /></td>
		<td><label>日期：</label></td><td><input id="runDayPicker" style="width:100px;" /></td>
		<td><label>序列：</label></td><td><select id="sqlDefinitionSelect" multiple="multiple" style="width:300px" /></td>
		<td><button type="button" id="queryButton" class="k-button" onclick="javascript:query();">查询</button></td>
		<td><button type="button" id="saveButton" class="k-button" onclick="javascript:exportCharts();">导出结果</button></td>
		</tr></table>
   </div>
</script>
<script type="text/javascript">
	jQuery(function() {
		
		$("#toolbar").kendoToolBar({
	        items: [
	            {
	                template: kendo.template(jQuery("#template").html())
	            }
	        ]
	    });
		
		jQuery("#sqlDefinitionSelect").kendoMultiSelect({
			placeholder:"点此选择序列",
            dataTextField: "text",
            dataValueField: "value",
            dataSource: JSON.parse('${results.series}')
        });
		
		jQuery("#projectDropDownList").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "value",
            dataSource: JSON.parse('${results.project}')
        });
		
		jQuery("#runDayPicker").kendoDatePicker({
			culture:"zh-CN",
			format: "yyyy-MM-dd"
		});
		
		createChart();
		$(document).bind("kendo:skinChange", createChart);
		
		$("#grid").kendoGrid({
			height: "230",
			editable: false,
            groupable: false,
            sortable: true,
            resizable:true,
            reorderable:true,
            selectable:"row",
            columns:[{
            	field:"name",
            	title:"标段"
            }]
		});
	});
	
	function createChart() {
        $("#chart").kendoChart({
            legend: {
                position: "top"
            },
            categoryAxis: {
                field: "title",
                //type:"date",
                labels: {
                    rotation: -60
                },
                crosshair: {
                    visible: true
                }
            },
            valueAxis: {
                labels: {
                    format: "N0"
                }
            },
            tooltip: {
                visible: true,
                shared: true,
                format: "N0"
            }
        });
    }
	
	function query(){
		var dropdownlist = $("#projectDropDownList").data("kendoDropDownList");
		var projectId = dropdownlist.value();
		
		var runDayPicker = jQuery("#runDayPicker").data("kendoDatePicker");
		var runDay = kendo.toString(runDayPicker.value(),"yyyyMMdd");
		
		var multiselect = $("#sqlDefinitionSelect").data("kendoMultiSelect");
		var dataItems = multiselect.dataItems();
		
		var series = [],codes = [];
		for(var i=0;i<dataItems.length;i++){
			var seriesValue = {};
			seriesValue.type="column";
			seriesValue.field=dataItems[i].value;
			seriesValue.name=dataItems[i].text;
			seriesValue.labels={visible:true};
			series.push(seriesValue);
			
			codes.push(dataItems[i].value);
		}
		codes.join(",");
		
		var chart = $("#chart").data("kendoChart");
		chart.setOptions({
			title: {
                text: dropdownlist.text()
            },
			series:series,
			dataSource: {
                transport: {
                    read: {
                        url: "${pageContext.request.contextPath}/rs/datamgr/dailySearch/columnCharts?1=1&codes="+codes+"&runDay="+runDay+"&projectId="+projectId,
                        dataType: "json"
                    }
                }
            }
		});
		
		//grid数据
		var grid = $("#grid").data("kendoGrid");
		var columns =[];
		var col = {};
		col.field="title";
		col.title="标段";
		col.width="200px";
		col.locked=true;
		columns.push(col);
		for(var i=0;i<dataItems.length;i++){
			col = {};
			col.field=dataItems[i].value;
			col.title=dataItems[i].text;
			col.width="120px";
			columns.push(col);
		}
		
		grid.setOptions({
            columns:columns,
            dataSource: new kendo.data.DataSource({
				type:"POST",
				transport: {
	            	read:{
	            		contentType: "application/json",
	                    url: "${pageContext.request.contextPath}/rs/datamgr/dailySearch/columnChartsGrid?1=1&codes="+codes+"&runDay="+runDay+"&projectId="+projectId,
	                    type: "POST",
	                    dataType:"JSON"
	            	}
	            },
	            schema:{
	            	total:"total",
	            	data:"rows"
	            }
			})
		});
	}
	
	function exportCharts(){
		var dropdownlist = $("#projectDropDownList").data("kendoDropDownList");
		var projectId = dropdownlist.value();
		
		var runDayPicker = jQuery("#runDayPicker").data("kendoDatePicker");
		var runDay = kendo.toString(runDayPicker.value(),"yyyyMMdd");
		
		var multiselect = $("#sqlDefinitionSelect").data("kendoMultiSelect");
		var dataItems = multiselect.dataItems();
		
		var codes = [];
		for(var i=0;i<dataItems.length;i++){
			codes.push(dataItems[i].value);
		}
		codes.join(",");
		
		var params = "projectId="+projectId+"&runDay="+runDay+"&codes="+codes.join(",");
		window.open("${pageContext.request.contextPath}/rs/datamgr/dailySearch/exportExcel4ColumnCharts?1=1&"+params);
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
	<br>
	 <div class="x_content_title">
	  <img src="${pageContext.request.contextPath}/images/window.png">&nbsp;
	    日常统计图表查询
	 </div>
	<br>
	<div id="toolbar"></div>
	<div id="chart"></div><br/><br/>
	<div id="grid"></div>
	</div> 
</body>
</html>