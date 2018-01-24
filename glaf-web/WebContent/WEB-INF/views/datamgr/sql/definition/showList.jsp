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
            "width": "120px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
            {
            "field": "createTime",
            "title": "创建日期",
            "width": "120px",
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
                "text": "列信息",
                "name": "columns",
                "click": function showColumns(e) {
			             var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                         //alert(JSON.stringify(dataItem));
		                 //alert(dataItem.id);
						 //alert(dataItem.operation);
						 if(dataItem.operation == 'select'){
			               var link = "<%=request.getContextPath()%>/mx/datamgr/sql/definition/editColumns?sqlDefId="+dataItem.id;
			               editColumns(link);
						 } else {
							 alert("非查询语句无列信息。");
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
			area: ['980px', (jQuery(window).height() - 50) +'px'],
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
			area: ['980px', (jQuery(window).height() - 50) +'px'],
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