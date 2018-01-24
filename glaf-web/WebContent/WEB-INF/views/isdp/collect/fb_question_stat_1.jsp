<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>质量计划统计</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
var width = document.documentElement.clientWidth,height=document.documentElement.clientHeight;
var whereCondition_2={QUALITY_QUESTION_TYPE:"${param.type}"};
$(function() {
	//设置图表高度及宽度
	$("#chart_2").width(width/2-20);
	$("#chart_2").height(height/2-50);
	
	$("#table_2").width(width/2-20);
	$("#table_2").height(height/2-50);
	
	$("#grid_2").width(width-20);
	
	createGrid_2();
	createChart_2();
	$(document).bind("kendo:skinChange", createChart_2);
});

function createGrid_2(){
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
				text:"未闭合",value:0
			},{
				text:"闭合",value:1
			}];
			finishField = columns[i].field;
		}
		fields[columns[i].field]={type:type};
	}
	
	$("#grid_2").kendoGrid({
        dataSource: {
            type: "json",
            transport: {
            	contentType: "application/json",
                type: "POST",
                read: "${pageContext.request.contextPath}/rs/isdp/collect/datalist?tableName=${param.tableName}&whereCondition="+encodeURI(JSON.stringify(whereCondition_2))
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
	var ok_total=0,no_total=0,all_total=0,state=1;
	var grid_2 = $("#grid_2").data("kendoGrid");
	var datasource = grid_2.dataSource;
	datasource.fetch(function(){
		var datas = datasource.data();
		for(var i=0;i<datasource.total();i++){
			all_total += datas[i].QUALITY_COUNT_ONE;
			ok_total += datas[i].QUALITY_COUNT_TWO;
			no_total += datas[i].QUALITY_COUNT_THREE;
			
			if(datas[i].QUALITY_STATE==0){
				state = 0;
			}
		}
		
		if(state==0){
			$("#noInput_2").val("未闭合");
			$("#noInput_2").attr("style","color:red;");
		}else{
			$("#noInput_2").val("闭合");
		}
		$("#allInput_2").val(all_total+"个");
		$("#okInput_2").val(ok_total+"个");
	});
	////
}

function createChart_2(){
	$("#chart_2").kendoChart({
        legend: {
            position: "bottom"
        },
        categoryAxis: {
            field: "RUNDAY",
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
            format: "NO"
        }
    });
    
    queryCharts_2();
}

function queryCharts_2(){
	var series = JSON.parse('${results.series}');
	
	
	for(var i=0;i<series.length;i++){
		series[i].type="bar";
		series[i].axis="quantity";
	}
	
	var title = "${chartName}";
	if("${param.type}"==1){
		title = "整改"+title;
	}else if("${param.type}"==2){
		title = "缺陷"+title;
	}
	document.title = title;
	
	var chart_2 = $("#chart_2").data("kendoChart");
	chart_2.setOptions({
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
                    url: "${pageContext.request.contextPath}/rs/isdp/collect/charts?dataType=list&tableName=${param.tableName}&whereCondition="+encodeURI(JSON.stringify(whereCondition_2)),
                    dataType: "json"
                }
            }
        }
	});
}
</script>
</head>
<body>
<div>
	<div id="main_content_2" class="k-content">
	<br/>
	<div class="x_content_title">
	  <img src="${pageContext.request.contextPath}/images/window.png">&nbsp;
	  	<c:if test="${param.type==1 }">
	  		整改${chartName }
	  	</c:if>
	    <c:if test="${param.type==2 }">
	  		缺陷${chartName }
	  	</c:if>
	 </div>
	 <br/>
		<div id="grid_2"></div>
		<div id="chart_2" style="float: left"></div>
		<div id="table_2" style="float:left;background-color:#FFF;">
			<table border="0" width="100%" height="100%">
				<tr>
					<td align="right" style="font-style: oblique;font-size: 16px">
						<c:if test="${param.type==1 }">
					  		整改总体评价：
					  	</c:if>
					    <c:if test="${param.type==2 }">
					  		缺陷总体评价：
					  	</c:if>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">
						<c:if test="${param.type==1 }">
					  		整改通知总数
					  	</c:if>
					    <c:if test="${param.type==2 }">
					  		缺陷通知总数
					  	</c:if>
					</td>
					<td><input id="allInput_2" type="text" class="k-textbox" readonly="readonly" /></td>
				</tr>
				<tr>
					<td align="right">
						<c:if test="${param.type==1 }">
					  		整改反馈总数
					  	</c:if>
					    <c:if test="${param.type==2 }">
					  		缺陷反馈总数
					  	</c:if>
					</td>
					<td><input id="okInput_2" type="text" class="k-textbox" readonly="readonly" /></td>
				</tr>
				<tr>
					<td align="right">工作评价</td>
					<td><input id="noInput_2" type="text" class="k-textbox" readonly="readonly" /></td>
				</tr>
			</table>
		</div>
	</div> 
</body>
</html>