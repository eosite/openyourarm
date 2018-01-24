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
<title>合成列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>
	  &nbsp;&nbsp;
      <!-- <button type="button" id="execAllButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:executeAll();">全部执行</button> -->              
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
                    "url": "<%=request.getContextPath()%>/rs/sys/treeTableSyntheticRule/data?syntheticId=${syntheticId}"
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
            "field": "title",
            "title": "标题",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },		
            {
            "field": "columnName",
            "title": "目标表字段名",
            "width": "150px",
            "lockable": false,
			"align": "center"
            },	
			{
            "field": "typeName",
            "title": "字段类型",
            "width": "90px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
			{
            "field": "datasetName",
            "title": "数据集",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
            {
            "field": "mappingToSourceIdColumn",
            "title": "关联主键字段",
            "width": "120px",
            "lockable": false,
			"align": "center"
            },	
            {
            "field": "mappingToTargetColumn",
            "title": "映射字段",
            "width": "150px",
            "lockable": false,
			"align": "center"
            },
			{
			"command": [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    //alert(JSON.stringify(dataItem));
								    //alert(dataItem.id);
								    var link = "<%=request.getContextPath()%>/mx/sys/treeTableSyntheticRule/edit?id="+dataItem.id;
								    editRow(link);
								}
                },
				{
                "text": "删除",
                "name": "del",
                "click": function delRule(e) {
					        if(confirm("数据删除后不能恢复，确定删除吗？")){
								  var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  var link = "<%=request.getContextPath()%>/mx/sys/treeTableSyntheticRule/delete?id="+dataItem.id;
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
											   if(data.statusCode == 200){
												   window.location.reload();
											   }
										   }
									 });
								}
				            }
                },
				{
                "text": "数据集",
                "name": "dataset",
                "click": function showDataset(e) {
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    //alert(JSON.stringify(dataItem));
								    //alert(dataItem.id);
								    var link = "<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id="+dataItem.datasetId;
								    window.open(link);
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


    function addRow(){
         editRow('<%=request.getContextPath()%>/mx/sys/treeTableSyntheticRule/edit?syntheticId=${syntheticId}');
    }

 
    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑合成表规则",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}
  

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="合成表规则列表">&nbsp;
    合成表规则列表
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