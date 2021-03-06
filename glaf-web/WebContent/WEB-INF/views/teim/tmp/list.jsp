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
<title>EimServerTmp列表</title>
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
                        "categoryId": {
							"type": "number"
                        },
                        "path_": {
                                                        "type": "string"
                        },
                        "reqUrlParam": {
                                                        "type": "string"
                        },
                        "reqType": {
                                                        "type": "string"
                        },
                        "reqHeader": {
                                                        "type": "string"
                        },
                        "reqContentType": {
                                                        "type": "string"
                        },
                        "resContentType": {
                                                        "type": "string"
                        },
                        "reqBody": {
                                                        "type": "string"
                        },
                        "response_": {
                                                        "type": "string"
                        },
                        "createBy": {
                                                        "type": "string"
                        },
                        "createTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "updateBy": {
                                                        "type": "string"
                        },
                        "updateTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "deleteFlag": {
							"type": "number"
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
                    "url": "<%=request.getContextPath()%>/rs/teim/tmp/data"
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
				"field": "categoryId",
				"title": "categoryId",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "path_",
				"title": "path_",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "reqUrlParam",
				"title": "reqUrlParam",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "reqType",
				"title": "reqType",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "reqHeader",
				"title": "reqHeader",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "reqContentType",
				"title": "reqContentType",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "resContentType",
				"title": "resContentType",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "reqBody",
				"title": "reqBody",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "response_",
				"title": "response",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createBy",
				"title": "createBy",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createTime",
				"title": "createTime",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                },
                {
				"field": "updateBy",
				"title": "updateBy",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "updateTime",
				"title": "updateTime",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                },
                {
				"field": "deleteFlag",
				"title": "deleteFlag",
				"width": "90px",
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
		        //alert(dataItem.tmpId);
			 var link = "<%=request.getContextPath()%>/mx/teim/tmp/edit?tmpId="+dataItem.tmpId;
			 if(dataItem.reqType=='soap1.1'||dataItem.reqType=='soap1.2'){
				 link="<%=request.getContextPath()%>/mx/teim/tmp/ws/edit?tmpId="+dataItem.tmpId;
			 }
			 editRow(link);
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
         editRow('<%=request.getContextPath()%>/mx/teim/tmp/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑EimServerTmp信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
                        iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="EimServerTmp列表">&nbsp;
    EimServerTmp列表
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