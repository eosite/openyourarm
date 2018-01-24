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
<title>数据项列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
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
                    "url": "<%=request.getContextPath()%>/rs/system/dataitem/data"
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
						"width": "150px",
						"expandable": true,
						"lockable": false,
						"locked": false
						},
						{
						"field": "title",
						"title": "标题",
						"width": "250px",
						"locked": false
						},
						{
						"field": "typeName",
						"title": "是否系统内置",
						"width": "80px",
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
								  //if(dataItem.type != 'SYS'){
								      var link = "<%=request.getContextPath()%>/mx/system/dataitem/edit?id="+dataItem.id;
								      editRow(link);
								  //} else {
									  //alert("系统内置，不提供修改功能！");
								  //}
								}
							},{
							"text": "字段",
							"name": "fields",
							"click": function showDetails(e) {
								  var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  //alert(JSON.stringify(dataItem));
								  //alert(dataItem.id);
								  var link = "<%=request.getContextPath()%>/mx/system/dataitem/fields?dataItemId="+dataItem.id;
								  location.href=link;
								}
							},{
							"text": "数据",
							"name": "json",
							"click": function showDetails(e) {
								  var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  //alert(JSON.stringify(dataItem));
								  //alert(dataItem.id);
								  var link = "<%=request.getContextPath()%>/rs/dataitem/jsonArray?id="+dataItem.id;
								  window.open(link);
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
         editRow('<%=request.getContextPath()%>/mx/system/dataitem/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑数据项信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['720px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="数据项列表">&nbsp;
    数据项列表
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