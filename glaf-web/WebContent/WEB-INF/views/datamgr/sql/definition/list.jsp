<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SQL语句列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton" class="k-button" style="width: 80px"   
	  onclick="javascript:addRow();">新增</button> 
	  <button type="button" id="queryButton" class="k-button" 
	  onclick="javascript:executeAllQuery();">执行全部查询</button>
	  <button type="button" id="queryButton" class="k-button"  
	  onclick="javascript:executeXYQuery();">执行指定查询</button>
	  <button type="button" id="queryResultsButton" class="k-button"   
	  onclick="javascript:queryResults();">查询执行历史</button> 
	  <button type="button" id="dropButton" class="k-button"   
	  onclick="javascript:dropTables();">重建结果表</button> 
      <button type="button" id="queryButton2" class="k-button" 
	  onclick="javascript:executeAllQueryToResults();">抓取数据</button>
	  <button type="button" id="queryButton2" class="k-button" 
	  onclick="javascript:executeAllQueryToResultsAsync();">多线程抓取</button>
	  <button type="button" id="permButton2" class="k-button" 
	  onclick="javascript:sqlPerms();">权限设置</button>
   </div>
</script>
<script type="text/javascript">
	
  jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "id": {
                            "type": "string"
                        },
                        "startIndex": {
                            "type": "number"
                        }
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
                    "url": "<%=request.getContextPath()%>/rs/datamgr/sql/definition/data"
                }
            },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 10
                     },
		"selectable": "single",
		"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
			{
            "field": "id",
            "title": "编号",
            "width": "65px",
			"expandable": false,
			"lockable": false,
            "locked": false
            },
			{
            "field": "code",
            "title": "编码",
            "width": "125px",
			"expandable": false,
			"lockable": false,
            "locked": false
            },
			{
            "field": "name",
            "title": "名称",
            "width": "220px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },		
			{
            "field": "title",
            "title": "标题",
            "width": "280px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },		
			{
            "field": "createBy",
            "title": "创建人",
            "width": "90px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
            {
            "field": "createTime",
            "title": "创建日期",
            "width": "100px",
            "lockable": false,
			"align": "center",
			"format": "{0: yyyy-MM-dd}",
			"filterable": {
              "ui": "datetimepicker"  
              }
            },
		    {
			"command": [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
			             var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                         //alert(JSON.stringify(dataItem));
		                 //alert(dataItem.id);
			             var link = "<%=request.getContextPath()%>/mx/datamgr/sql/definition/edit?id="+dataItem.id;
			             editRow(link);
		               }
                },{
                "text": "删除",
                "name": "delete",
                "click": function showDetails(e) {
			             var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                         //alert(JSON.stringify(dataItem));
		                 //alert(dataItem.id);
			             var link = "<%=request.getContextPath()%>/mx/datamgr/sql/definition/delete?id="+dataItem.id;
			             deleteRow(link);
		               }
                },{
                "text": "执行",
                "name": "execute",
                "click": function showExecution(e) {
			             var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                         //alert(JSON.stringify(dataItem));
		                 //alert(dataItem.id);
						 //alert(dataItem.operation);
						 if(dataItem.parentId !=null && dataItem.parentId*1.0 >0 ){
			                  alert("该查询为非独立查询，需要在父查询中执行。");
						 } else {
							  var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/show?sqlDefId="+dataItem.id;
			                  showExec(link);
						 }
		               }
                },{
                "text": "执行历史",
                "name": "executeHistory",
                "click": function showExecutionHistory(e) {
			             var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                         //alert(JSON.stringify(dataItem));
		                 //alert(dataItem.id);
						 //alert(dataItem.operation);
			             var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/showHistory?sqlDefId="+dataItem.id;
			             showExecHistory(link);
		               }
                },{
                "text": "查询入库",
                "name": "executeResults",
                "click": function showExecutionHistory(e) {
			             var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                         if(confirm("确定在各个数据库执行并将查询结果写入到结果表吗?")){
                              var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/saveExecutionResult?sqlDefId="+dataItem.id;
							  jQuery.ajax({
									   type: "POST",
									   url: link,
									   dataType:  'json',
									   error: function(data){
										   alert('服务器处理错误！');
									   },
									   success: function(data){
										   if(data != null && data.message != null){
											   alert(data.message);
										   } else {
											   alert('操作成功完成！');
										   }
									   }
								 });
						 }
		               }
                }]
          }
	],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });


    function addRow(){
         editRow('<%=request.getContextPath()%>/mx/datamgr/sql/definition/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑SQL语句",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function editColumns(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑字段列表信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['1080px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function showExec(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "执行SQL语句",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['950px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function showExecHistory(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "SQL语句执行历史",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function executeXYQuery(){
		location.href="<%=request.getContextPath()%>/mx/datamgr/sql/execution/choose";
	}

	function executeAllQuery(){
		if(confirm("确定在全部数据库上执行统计结果吗？")){
          var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/executeAllQuery";
	      var params = jQuery("#iForm").formSerialize();
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   var jobNo = "";
					   if(data != null && data.jobNo!= null){
						   jobNo = data.jobNo;
					   }
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   queryResults(jobNo);
				   }
			 });
		}
	}

	function executeAllQueryToResults(){
		if(confirm("确定在全部数据库上执行抓取数据到统计表吗？")){
          var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/executeAllQueryToResults";
	      var params = jQuery("#iForm").formSerialize();
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   var jobNo = "";
					   if(data != null && data.jobNo!= null){
						   jobNo = data.jobNo;
					   }
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   //queryResults(jobNo);
				   }
			 });
		}
	}


	function executeAllQueryToResultsAsync(){
		if(confirm("确定在全部数据库上执行抓取数据到统计表吗？")){
          var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/executeAllQueryToResultsAsync";
	      var params = jQuery("#iForm").formSerialize();
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   var jobNo = "";
					   if(data != null && data.jobNo!= null){
						   jobNo = data.jobNo;
					   }
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   //queryResults(jobNo);
				   }
			 });
		}
	}

	function dropTables(){
		if(confirm("确定删除抓取结果表并清空抓取数据吗？")){
          var link = "<%=request.getContextPath()%>/mx/datamgr/sql/definition/drop";
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
				   }
			 });
		}
	}

	function deleteRow(link){
		if(confirm("确定删除SQL语句吗？")){
	      var params = jQuery("#iForm").formSerialize();
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){				   
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					  window.location.reload();
				   }
			 });
		}
	}

	function queryResults(jobNo){
	   var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/showHistory?jobNo="+jobNo;
       jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "SQL语句执行历史",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function sqlPerms(){
       var link = "<%=request.getContextPath()%>/mx/sys/dataPermission/viewByUser?businessType=sql_mgr";
       jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "权限设置",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="SQL语句列表">&nbsp;
    SQL语句列表
 </div>
<br>
<div id="grid"></div>
</div>
<br/>
<br/>
<br/>
<br/>
</body>
</html>