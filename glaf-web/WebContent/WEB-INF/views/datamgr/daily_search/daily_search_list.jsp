<%@page import="com.glaf.base.modules.sys.model.Dictory"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Dictory> sqlList = (List<Dictory>)request.getAttribute("sqlList");
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
		<button type="button" id="redirectButton" class="k-button" onclick="javascript:window.location='${pageContext.request.contextPath}/mx/datamgr/dailySearch?method=list&type=d';">按日期统计</button>
   		<label>建设项目：</label><input type="text" id="projectDropDownList" />
		<label>日期：</label><input id="runDayPicker" style="width:100px;" />
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
			"pageable": false,
	        "columns": [
				{"field": "projectName","title": "项目","width": "220px",locked:true,hidden:false},
				{"field": "title","title": "标段","width": "220px",locked:true,hidden:false}<%=sqlList.size()>0?",":""%>
	            <%
	            StringBuffer sb = new StringBuffer();
	            for(int i=0,len=sqlList.size();i<len;i++){
	            	Dictory sql = sqlList.get(i);
	            	sb.append("{").append("'field':'").append(sql.getCode()).append("',");
	            	sb.append("'title':'").append(sql.getName()).append("',");
	            	sb.append("'width':'120px',").append("'hidden':").append((sql.getCode()==null||sql.getCode().length()==0)?true:false).append("}");
	            	if(i<len-1){
	            		sb.append(",");
	            	}
	            }
            	out.print(sb.toString());
	            %>
			]
	    });
		
		jQuery("#projectDropDownList").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "value",
            dataSource: JSON.parse('${results}')
        });
		
		jQuery("#runDayPicker").kendoDatePicker({
			culture:"zh-CN",
			format: "yyyy-MM-dd"
		});
	});
	
	function query(){
		var project = jQuery("#projectDropDownList").val();
		
		var runDayPicker = jQuery("#runDayPicker").data("kendoDatePicker");
		var runDay = kendo.toString(runDayPicker.value(),"yyyyMMdd");
		
		jQuery("#projectId").val(project);
		jQuery("#runDay").val(runDay);
		
		var datasource = new kendo.data.DataSource({
			"schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "databaseId": {
                            "type": "number"
                        },
                        "projectName":{
                        	"type":"string"
                        },
                        "title": {
                            "type": "string"
                        }<%=sqlList.size()>0?",":""%>
                        <%
                        sb = new StringBuffer();
        	            for(int i=0,len=sqlList.size();i<len;i++){
        	            	Dictory sql = sqlList.get(i);
        	            	sb.append("'").append(sql.getCode()).append("':{").append("'type':'number'}");
        	            	if(i<len-1){
        	            		sb.append(",");
        	            	}
        	            }
       	            	out.print(sb.toString());
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
                    "url": "${pageContext.request.contextPath}/rs/datamgr/dailySearch/data4project?projectId="+project+"&runDay="+runDay
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
		
		var codes = [];
		var titles = [];
		for (var i = 0; i < grid.columns.length; i++) {
		  if(!grid.columns[i].hidden){
			  //codes.push(grid.columns[i].field);
			  //titles.push(grid.columns[i].title);
		  }
		}
		
		var params = "projectId="+jQuery("#projectId").val()+"&runDay="+jQuery("#runDay").val()+"&codes="+codes.join(",")+"&titles="+jQuery.base64.encode(titles.join(","));
		window.open("${pageContext.request.contextPath}/rs/datamgr/dailySearch/exportExcel4project?1=1&"+params);
		
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
	<input type="hidden" id="runDay" />
	<input type="hidden" id="projectId" />
	<div id="grid"></div>
	</div> 
</body>
</html>