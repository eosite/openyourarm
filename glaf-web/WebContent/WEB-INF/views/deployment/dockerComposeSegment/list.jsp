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
<title>Docker容器编排列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	          onclick="javascript:addRow();">新增</button> 
	  <button type="button" id="composeButton"  class="k-button" style="width: 120px" 
	          onclick="javascript:compose();">总体编排文件</button> 
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
                        "title": {
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "image": {
                            "type": "string"
                        },
                        "containerName": {
                            "type": "string"
                        },
                        "active": {
                            "type": "string"
                        },
                        "createBy": {
                            "type": "string"
                        },
                        "createTime": {
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
                    "url": "<%=request.getContextPath()%>/rs/deployment/dockerComposeSegment/data?deploymentId=${deploymentId}"
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
                "width": "250px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "name",
				"title": "名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "image",
				"title": "镜像名称",
                "width": "120px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "containerName",
				"title": "容器名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "active",
				"title": "是否有效",
                "width": "100px",
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
			    "command": [{
					"text": "修改",
					"name": "edit",
					"click": function showDetails(e) {
								var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								//alert(JSON.stringify(dataItem));
								//alert(dataItem.id);
								var link = "<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment/edit?id="+dataItem.id;
								editRow(link);
							}
					},{
					"text": "查看编排文件",
					"name": "view",
					"click": function showDetails(e) {
								var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								//alert(JSON.stringify(dataItem));
								//alert(dataItem.id);
								viewCompose(dataItem.id);
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
         editRow('<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment/edit?deploymentId=${deploymentId}');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑Docker容器编排信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function compose(){
	   var link="<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment/viewCompose?deploymentId=${deploymentId}";
       jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "查看编排信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function viewCompose(segmentId){
	   var link="<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment/viewCompose?segmentId="+segmentId;
       jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "查看编排信息",
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
  <img src="<%=request.getContextPath()%>/images/window.png" alt="Docker容器编排列表">&nbsp;
    Docker容器编排列表
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