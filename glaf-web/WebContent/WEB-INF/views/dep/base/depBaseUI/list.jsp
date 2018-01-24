<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>UI列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 60px" 
	  onclick="javascript:addRow();"><span class="k-icon k-i-plus"></span>新增</button>               
   </div>
</script>
<script type="text/javascript">
	
  jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "autoSync":true,
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "id": {
                            "type": "string"
                        },
                        "code": {
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "type": {
                            "type": "string"
                        },
                        "creator": {
                            "type": "string"
                        },
                        "createDateTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "modifier": {
                            "type": "string"
                        },
                        "modifyDateTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "delFlag": {
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
                    "url": "${pageContext.request.contextPath}/rs/dep/base/depBaseUI/data?delFlag=0"
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
				"field": "code",
				"title": "UI代码",
				"width": "350px",
				"lockable": false,
				"locked": false
            },
            {
				"field": "name",
				"title": "UI名称",
				"width": "350px",
				"lockable": false,
				"locked": false
            },
            {
				"field": "type",
				"title": "UI类型",
				"width": "150px",
				"lockable": false,
				"locked": false
            },
            {
				"field": "creator",
				"title": "创建人",
				"width": "150px",
				"lockable": false,
				"locked": false
            },
            {
				"field": "createDateTime_datetime",
				"title": "创建时间",
				"width": "180px",
				"format": "{0: yyyy-MM-dd HH:mm:ss}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
            },
            {
				"field": "modifier",
				"title": "修改人",
				"width": "150px",
				"lockable": false,
				"locked": false
            },
            {
				"field": "modifyDateTime_datetime",
				"title": "修改时间",
				"width": "180px",
				"format": "{0: yyyy-MM-dd HH:mm:ss}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
            },
    	    {
    		"command": [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
        			var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
        			var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseUI/edit?id="+dataItem.id;
        			editRow(link);
    		    }
            },{
                "text":"删除",
                "name":"updateDelFlag",
                "click":function updateDelFlag(e){
                    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                    $.post('${pageContext.request.contextPath}/rs/dep/base/depBaseUI/updateDelFlag', {id: dataItem.id,delFlag:"1"}, function(data, textStatus, xhr) {
                        if(data.statusCode==200){
                            alert("删除成功！");
                            jQuery("#grid").data("kendoGrid").dataSource.read();
                        }
                    });
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
         editRow('${pageContext.request.contextPath}/mx/dep/base/depBaseUI/edit');
	}

	function editRow(link) {
		jQuery.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑UI信息",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			offset : [ '', '' ],
			fadeIn : 100,
			area : [ '620px', '400px' ],
			iframe : {
				src : link
			}
		});
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="${pageContext.request.contextPath}/images/window.png" alt="UI列表">&nbsp;
			UI列表
		</div>
		<br>
		<div id="grid"></div>

	</div>
	<br />
	<br />
	<br />
	<br />
</body>
</html>