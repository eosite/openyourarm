<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.glaf.core.util.DateUtils"%>
<%@page import="com.glaf.core.util.RequestUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Calendar cal = Calendar.getInstance();
String startParam = RequestUtils.getAttribute(request, "startRunDay"),endParam = RequestUtils.getAttribute(request, "endRunDay");
Date startRunDay = null,endRunDay=null;
if(startParam != null && !startParam.equals("0")){
	startRunDay = DateUtils.toDate(startParam);
}
if(endParam != null && !endParam.equals("0")){
	endRunDay = DateUtils.toDate(endParam);
}
int betweenDays = Integer.parseInt(RequestUtils.getAttribute(request,"betweenDays"));
String startRunDayParam = "",endRunDayParam = "";

if(betweenDays>0){
	startRunDayParam = DateUtils.getDate(startRunDay);
	endRunDayParam = DateUtils.getDate(endRunDay);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日常统计查询</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.base64.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
		<button type="button" id="redirectButton" class="k-button" onclick="javascript:window.location='${pageContext.request.contextPath}/mx/datamgr/dailySearch?method=list';">按标段统计</button>
   		<label>建设项目：</label><input type="text" id="projectDropDownList" />
		<label>开始日期：</label><input id="startRunDayPicker" style="width:100px;" value="<%=startRunDayParam%>" />
		<label>结束日期：</label><input id="endRunDayPicker" style="width:100px;" value="<%=endRunDayParam%>" />
		<button type="button" id="buildColumnButton" class="k-button" onclick="javascript:buildColumn();">构建列</button>
		<button type="button" id="queryButton" class="k-button" onclick="javascript:query();">查询</button>
		<button type="button" id="saveButton" class="k-button" onclick="javascript:save();">导出结果</button>
   </div>
</script>
<script type="text/javascript">
	jQuery(function() {
		jQuery("#grid").kendoGrid({
	        "columnMenu": true,
	        "height": x_height,
	        "reorderable": false,
	        "filterable": false,
	        "sortable": false,
	        "selectable": "single",
	        "resizable": false,
	        "groupable": true,
	        "scrollable": {},
	        "toolbar": kendo.template(jQuery("#template").html()),
			"pageable": {
               "refresh": true,
               //"pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
               "buttonCount": 10
             },
	        "columns": [
				{"field": "title","title": "标段","width": "120px",<%=betweenDays>0?"locked:true,":""%>hidden:false},
				{"field": "name","title": "查询项目","width": "120px",<%=betweenDays>0?"locked:true,":""%>hidden:false}<%=betweenDays>0?",":""%>
				<%
				if(betweenDays>0){
					StringBuffer sb = new StringBuffer();
					Date tempDate = startRunDay;
					while(tempDate.getTime()-endRunDay.getTime()<=0){
						String runday = DateUtils.getDateTime("yyyyMMdd", tempDate);
						
						sb.append("{").append("'field':'day_").append(runday).append("',");
		            	sb.append("'title':'").append(DateUtils.getDate(DateUtils.toDate(String.valueOf(runday)))).append("',");
		            	sb.append("'width':'120px',").append("'hidden':").append(false).append("}");
		            	if(tempDate.getTime()-endRunDay.getTime()<0){
		            		sb.append(",");
		            	}
		            	
		            	cal.setTime(tempDate);
		            	cal.add(Calendar.DATE, 1); 
		            	tempDate = cal.getTime();
					}
					out.print(sb.toString());
				}
				
				%>
			]
	    });
		
		jQuery("#projectDropDownList").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "value",
            dataSource: JSON.parse('${results}'),
            value:"${projectId}"
        });
		
		jQuery("#startRunDayPicker").kendoDatePicker({
			culture:"zh-CN",
			format: "yyyy-MM-dd"
		});
		
		jQuery("#endRunDayPicker").kendoDatePicker({
			culture:"zh-CN",
			format: "yyyy-MM-dd"
		});
	});
	
	function buildColumn(){
		var project = jQuery("#projectDropDownList").val();
		
		var startRunDayPicker = jQuery("#startRunDayPicker").data("kendoDatePicker");
		var startRunDay = kendo.toString(startRunDayPicker.value(),"yyyyMMdd");
		var endRunDayPicker = jQuery("#endRunDayPicker").data("kendoDatePicker");
		var endRunDay = kendo.toString(endRunDayPicker.value(),"yyyyMMdd");
		
		if(startRunDay==null || endRunDay==null){
			alert("请选择开始和结束日期！");
			return;
		}
		
		var location = window.location.href;
		var url = location.substring(0,location.lastIndexOf('?'));
		window.location = url + "?method=list&type=d&projectId="+project+"&startRunDay="+startRunDay+"&endRunDay="+endRunDay
	}
	
	function query(){
		var projectId = jQuery("#projectDropDownList").val();
		
		var startRunDayPicker = jQuery("#startRunDayPicker").data("kendoDatePicker");
		var startRunDay = kendo.toString(startRunDayPicker.value(),"yyyyMMdd");
		var endRunDayPicker = jQuery("#endRunDayPicker").data("kendoDatePicker");
		var endRunDay = kendo.toString(endRunDayPicker.value(),"yyyyMMdd");
		
		jQuery("#projectId").val(projectId);
		jQuery("#startRunDay").val(startRunDay);
		jQuery("#endRunDay").val(endRunDay);
		
		var datasource = new kendo.data.DataSource({
			"schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "databaseId": {
                            "type": "number"
                        },
                        "title": {
                            "type": "string"
                        }<%=betweenDays>0?",":""%>
                        <%
                        if(betweenDays>0){
        					StringBuffer sb = new StringBuffer();
        					Date tempDate = startRunDay;
        					while(tempDate.getTime()-endRunDay.getTime()<=0){
        						String runday = DateUtils.getDateTime("yyyyMMdd", tempDate);
        						sb.append("'day_").append(runday).append("':{'type':'number'}");
        		            	if(tempDate.getTime()-endRunDay.getTime()<0){
        		            		sb.append(",");
        		            	}
        		            	cal.setTime(tempDate);
        		            	cal.add(Calendar.DATE, 1); 
        		            	tempDate = cal.getTime();
        					}
       		            	out.print(sb.toString());
        				}
                        %>
                    }
                },
                "data": "rows"
            },
            "transport": {
                "parameterMap": function(options) {
                    return JSON.stringify(options);
                },
                "read": {
		            "contentType": "application/json",
                    "type": "POST",
                    "url": "${pageContext.request.contextPath}/rs/datamgr/dailySearch/data4date?projectId="+projectId+"&startRunDay="+startRunDay+"&endRunDay="+endRunDay
                }
            },
	        "serverFiltering": true,
            "serverSorting": true,
            //"pageSize": 20,
            "serverPaging": true
		});
		
		var grid = jQuery("#grid").data("kendoGrid");
		grid.setDataSource(datasource);
	}
	
	function save(){
		var grid = jQuery("#grid").data("kendoGrid");
		
		if(grid.dataSource.total()==0){
			alert("请查询后再进行导出！");
			return ;
		}
		
		var params = "projectId="+jQuery("#projectId").val()+"&startRunDay="+jQuery("#startRunDay").val()+"&endRunDay="+jQuery("#endRunDay").val();
		window.open("${pageContext.request.contextPath}/rs/datamgr/dailySearch/exportExcel4date?1=1&"+params);
		
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
	<br>
	 <div class="x_content_title">
	  <img src="${pageContext.request.contextPath}/images/window.png">&nbsp;
	    日常统计查询
	 </div>
	<br>
	<input type="hidden" id="startRunDay" value="${startRunDay }" />
	<input type="hidden" id="endRunDay" value="${endRunDay }"/>
	<input type="hidden" id="projectId" value="${projectId }" />
	<div id="grid"></div>
	</div> 
</body>
</html>