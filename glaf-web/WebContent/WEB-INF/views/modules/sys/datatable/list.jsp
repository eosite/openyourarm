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
<title>数据表列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>
	  <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:location.href='<%=request.getContextPath()%>/mx/system/datatable/showImport';">上传模型</button>
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
                    "url": "<%=request.getContextPath()%>/rs/system/datatable/data"
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
            "field": "tablename",
            "title": "表名",
            "width": "250px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
            {
            "field": "title",
            "title": "标题",
            "width": "150px",
            "locked": false
            },
			{
            "field": "typeName",
            "title": "类型",
            "width": "150px",
            "locked": false
            },
			{
            "field": "createTime_datetime",
            "title": "创建日期",
            "width": "150px",
            "locked": false
            },
		    {
			"command": [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.id);
							var link = "<%=request.getContextPath()%>/mx/system/datatable/edit?id="+dataItem.id;
							editRow(link);
		            }
                },{
                "text": "列定义",
                "name": "columns",
                "click": function showDetails(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.id);
							var link = "<%=request.getContextPath()%>/mx/system/datafield?datatableId="+dataItem.datatableId;
							location.href=link;
		            }
                },{
                "text": "更新表",
                "name": "updateTableSchema",
                "click": function updateSchema(e) {
						    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
						    //alert(JSON.stringify(dataItem));
						    //alert(dataItem.id);
						    updateTableSchema(dataItem.id);
		            }
                },{
                "text": "更新接口表",
                "name": "updateTableMeta",
                "click": function updateMeta(e) {
						    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
						    //alert(JSON.stringify(dataItem));
						    //alert(dataItem.id);
						    updateTableMeta(dataItem.id);
		            }
                },{
                "text": "数据列表",
                "name": "datalist",
                "click": function showDetails(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.id);
							var link = "<%=request.getContextPath()%>/mx/data/table?datatableId="+dataItem.id;
							location.href=link;
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
         editRow('<%=request.getContextPath()%>/mx/system/datatable/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑数据表信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['780px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function updateTableMeta(id){
		if(confirm("将根据字段定义信息更新接口表信息，字段名称及类型不能再修改，确定更新吗？")){
		  var link = "<%=request.getContextPath()%>/rs/system/datatable/updateTableMeta?id="+id;
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

	function updateTableSchema(id){
		if(confirm("将根据字段定义信息更新数据库表结构，字段名称及类型不能再修改，确定更新吗？")){
		  var link = "<%=request.getContextPath()%>/rs/system/datatable/updateTableSchema?id="+id;
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

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="数据表列表">&nbsp;
    数据表列表
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