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
		$("#chart").width(width/2-10);
		$("#chart").height(height/2-50);
		
		$("#table").width(width/2-10);
		$("#table").height(height/2-50);
		////
		createGrid();//创建表格
		createChart();//创建图表
		$(document).bind("kendo:skinChange", createChart);
	});
	
	function createGrid(){
		var finishField = "";//完成情况字段名
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
			}else if(columns[i].dtype=="char"){
				//完成情况标记，即业务评价标记
				type = "string";
				columns[i].values=[{
					text:"未完成",value:0
				},{
					text:"完成",value:1
				}];
				finishField = columns[i].field;
			}
			fields[columns[i].field]={type:type};
		}
		//加载表格数据 
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
            height: height/2-50,
            groupable: false,
            sortable: true,
            columns: columns
        });
		
		//以下判断业务总体评价
		var ok_total=0,no_total=0;
		var grid = $("#grid").data("kendoGrid");
		var datasource = grid.dataSource;
		datasource.fetch(function(){
			var datas = datasource.data();
			for(var i=0;i<datasource.total();i++){
				if(datas[i][finishField]==0){
					no_total++;
				}else{
					ok_total++;
				}
			}
			
			if(no_total>0){
				$("#allInput").val("未闭合");
				$("#allInput").attr("style","color:red;");
			}else{
				$("#allInput").val("闭合");
			}
			$("#okInput").val(ok_total+"个");
			$("#noInput").val(no_total+"个");
		});
		////
	}
	
	function createChart() {
        $("#chart").kendoChart({
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
        
        queryCharts()
    }
	
	//查询表格数据 
	function queryCharts(){
		var series = JSON.parse('${results.series}');
		
		
		for(var i=0;i<series.length;i++){
			series[i].type="column";
			series[i].axis="quantity";
			series[i].aggregate="sum";
			series[i].categoryField="INSPECTION_NAME";
		}
		
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
			series:series,
			dataSource: {
                transport: {
                    read: {
                        url: "${pageContext.request.contextPath}/rs/isdp/collect/charts?dataType=sum&tableName=${param.tableName}",
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
		<hr>
		<div id="chart" style="float:left"></div>
		<div id="table" style="float:left;background-color:#FFF;">
			<table border="0" width="100%" height="100%">
				<tr>
					<td align="right" style="font-style: oblique;font-size: 16px">业务总体评价：</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">业务总体情况</td>
					<td><input id="allInput" type="text" class="k-textbox" readonly="readonly" /></td>
				</tr>
				<tr>
					<td align="right">已完成业务数</td>
					<td><input id="okInput" type="text" class="k-textbox" readonly="readonly" /></td>
				</tr>
				<tr>
					<td align="right">未完成业务数</td>
					<td><input id="noInput" type="text" class="k-textbox" readonly="readonly" /></td>
				</tr>
			</table>
		</div>
	</div> 
</body>
</html>