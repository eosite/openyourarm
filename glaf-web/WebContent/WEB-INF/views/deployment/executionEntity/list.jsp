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
<title>执行过程列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>

<script id="template" type="text/x-kendo-template">
   <div class="toolbar"> 
	  
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
                        "host": {
                             "type": "string"
                        },
                        "title": {
                             "type": "string"
                        },
                        "retryCount": {
							"type": "number"
                        },
                        "sortNo": {
							"type": "number"
                        },
                        "status": {
							"type": "number"
                        },
                        "createBy": {
                             "type": "string"
                        },
                        "createTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "startTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
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
                    "url": "<%=request.getContextPath()%>/rs/deployment/executionEntity/data?category=${category}&serverId=${serverId}&status=${status}&nodeId=${nodeId}"
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
		"selectable": "multiple",
		//"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
			    {
				"field": "title",
				"title": "标题",
                "width": "320px",
				"lockable": false,
				"locked": false
                },
				{
				"field": "host",
				"title": "执行主机",
                "width": "120px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "sortNo",
				"title": "顺序",
				"width": "70px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "startTime",
				"title": "开始时间",
				"width": "120px",
				"format": "{0: yyyy-MM-dd HH:mm:ss}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                },
				{
				"field": "retryCount",
				"title": "重试次数",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "statusLabel",
				"title": "执行状态",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createBy",
				"title": "创建人",
                "width": "120px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createTime",
				"title": "创建时间",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                },
				{
				"command": [{
					"text": "执行",
					"name": "exec",
					"click": function showDetails(e) {
								 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								 //alert(JSON.stringify(dataItem));
								 //alert(dataItem.id);
								 if(confirm("确定执行该任务吗？")){
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    var link = "<%=request.getContextPath()%>/mx/deployment/executionEntity/execute?executionId="+dataItem.id;
									jQuery.ajax({
										   type: "POST",
										   url: link,
										   dataType: 'json',
										   error: function(data){
											   alert('服务器处理错误！');
										   },
										   success: function(data){
											   if(data != null && data.message != null){
												   alert(data.message);
											   } else {
												   alert('操作成功完成！');
											   }
											   if(data.statusCode == 200){
												   window.location.reload();
											   }
										   }
									 });
								 }
				           }
                }
				]
          }
	    ],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });

  });


  function executeSelected(){
	    var grid = jQuery("#grid").data("kendoGrid");
		var text = "";
        grid.select().each(function() {
            var dataItem = grid.dataItem($(this));
            text += dataItem.id+",";
        });
		//var dataItems = grid.dataItem(grid.select());
		//for(var i=0;i<dataItems.length;i++){
        //    text += dataItems[i].id+",";
		//}
        if(text.length > 0){
           if(confirm("确定重新执行选中的任务吗？")){
               var link = "<%=request.getContextPath()%>/mx/deployment/executionEntity/executeAll?executionIds="+text;
			   jQuery.ajax({
                      type: "POST",
                      url: link,
                      dataType: 'json',
                      error: function(data){
	                      alert('服务器处理错误！');
                      },
                      success: function(data){
	                      if(data != null && data.message != null){
		                      alert(data.message);
	                      } else {
		                      alert('操作成功完成！');
	                      }
	                      if(data.statusCode == 200){
		                      window.location.reload();
	                      }
                      }
			    });
		   }
		} else {
           alert("请选择至少一条记录。");
		}
  }

  function searchData(){
	  document.iForm.submit();
  }

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="执行过程列表">&nbsp;
    执行过程列表
 </div>
<br>
<form method="post" id="iForm" name="iForm" action="<%=request.getContextPath()%>/mx/deployment/executionEntity">
<table>
<tr>
	<td width="20%" align="left">
	类别&nbsp;
	  <select id="nodeId" name="nodeId">
        <option value="">----请选择----</option>
		<c:forEach items="${projects}" var="item">
		<option value="${item.id}">${item.name}</option>     
		</c:forEach>
      </select>
	  <script type="text/javascript">
	       document.getElementById("nodeId").value="${nodeId}";
	  </script> 
	</td>
	<td width="40%" align="left">
	部署服务器&nbsp;
      <select id="serverId" name="serverId">
        <option value="">----请选择----</option>
		<c:forEach items="${servers}" var="item">
		<option value="${item.id}">${item.title}[${item.host}]</option>     
		</c:forEach>
      </select>
	  <script type="text/javascript">
	       document.getElementById("serverId").value="${serverId}";
	  </script> 
	</td>
	<td width="20%" align="left">
	  执行状态&nbsp;
	  <select id="status" name="status">
		 <option value="0">未开始</option>
		 <option value="-1">执行失败</option>
		 <option value="9">执行成功</option>
      </select>
	  <script type="text/javascript">
	       document.getElementById("status").value="${status}";
	  </script> 
	</td>
	<td width="20%" align="left">
	    <input type="button" id="searchButton" value="查找"  class="k-button" style="width: 60px" 
	         onclick="javascript:searchData();"> &nbsp;
		<button type="button" id="chooseButton"  class="k-button" style="width: 90px" 
	          onclick="javascript:executeSelected();">执行多个任务</button>  
	</td>
</tr>
</table>
</form>
<div id="grid"></div>
</div>     
<br/>
<br/>
<br/>
<br/>
</body>
</html>