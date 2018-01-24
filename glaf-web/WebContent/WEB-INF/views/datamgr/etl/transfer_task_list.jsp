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
<title>转换任务列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>               
   </div>
</script>
<script type="text/javascript">

    function reloadGrid(){
        var link="<%=request.getContextPath()%>/mx/transferTask/listAllTask";
        var grid = $("#grid").data("kendoGrid");
        var dataSource = new kendo.data.DataSource({
            transport: {
                read: {
                  type: "POST",
                  dataType: "json",
                  contentType: "application/x-www-form-urlencoded",
                  url: link,
                },
                parameterMap: function(options) {
                    return JSON.stringify(options);
                },
            },
            schema:{
                    "total": "total",
                    "data": "rows"
            },
            "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        });
        grid.setDataSource(dataSource);
    }

  jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "transport": {
                "parameterMap": function(options) {
                    return JSON.stringify(options);
                },
                "read": {
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    url: "<%=request.getContextPath()%>/mx/transferTask/listAllTask"
                }
            },
            "schema": {
                "total": "total",
                "data": "rows"
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
				"field": "taskName_",
				"title": "任务名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "taskDesc_",
				"title": "任务描述",
                "width": "150px",
				"lockable": false,
				"locked": false
                },   
                {
                "field": "srcDatabaseName_",
                "title": "源数据库名称",
                "width": "150px",
                "lockable": false,
                "locked": false
                },
                {
                "field": "targetDatabaseName_",
                "title": "目标数据库名称",
                "width": "150px",
                "lockable": false,
                "locked": false
                },
                {
                "field": "createBy_",
                "title": "创建人",
                "width": "100px",
                "expandable": true,
                "lockable": false,
                "locked": false
                },
                {
                "field": "createTime_",
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
        		                //alert(dataItem.iD);
            			 var link = "<%=request.getContextPath()%>/mx/etl/model/transfer_task_edit?taskId="+dataItem.id_;
            			 editRow(link);
            		}
                },{
                    "text": "删除",
                    "name": "delete",
                    "click": function deleteItem(e) {
                        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                        if(confirm("数据删除后不能恢复，确定删除吗？")){
                            var link= "${contextPath}/mx/transferTask/deleteTask?taskId="+dataItem.id_;
                            $.ajax({
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
                                   // window.location.reload();
                                  reloadGrid();
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
         editRow('<%=request.getContextPath()%>/mx/etl/model/transfer_task_edit/');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑转换任务信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['800px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="转换任务列表">&nbsp;
    转换任务列表
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