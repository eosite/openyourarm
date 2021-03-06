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

	//http://127.0.0.1:8080/glaf/mx/isdp/table/data/treelist?ruleId=831c1ca3-2e2e-4877-a4eb-a4e40117a4ef&databaseId=223
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webfile/js/jquery.ztree.extends.js" ></script>
<style type="text/css"> 
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
.tdCls{
	align:left;
	height:16px;
	border-top:1px solid #ddd;
}
.selectCls{
	width : 90%;
}
.textCls{
	width : 65%;
}
</style>
<script type="text/javascript">

    //创建grid表格数据源
	function createGridDataSource(link){
		var dataSource = new kendo.data.DataSource({
			type: "json",
            transport: {
            	parameterMap: function(options) {
                    return JSON.stringify(options);
                },
                read: {
		            "contentType": "application/json",
                    "type": "POST",
                    "url": link
                }
            },
			serverFiltering: true,
            serverSorting: true,
            pageSize: 10,
            serverPaging: true,
            schema:{
            	total: "total",
            	data: "rows",
            	model:{
            		fields:{
                        "id": {
                            "type": "string"
                        },
                        "startIndex": {
                            "type": "number"
                        }
                    }
            	}
            }
		});
		return dataSource;
	}


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
					"url": "<%=request.getContextPath()%>/rs/isdp/table/data/json?databaseId=${databaseId}&tableName=${tablename}&index_id=${index_id}"
                }
             },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 100,
            "serverPaging": false
        },
        "height": "452",
		"width": "${width}",
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 10
                     },
		"selectable": "single",
		//"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
			{
            "field": "startIndex",
            "title": "序号",
            "width": "65px",
			"expandable": true,
			"lockable": false,
            "locked": false
            }
		    <c:forEach items="${columns}" var="column">
			,{
            "field": "${column.name}",
            "title": "${column.title}",
            "width": "${column.width}px"
            }		
			</c:forEach>
	     ],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });

</script>
</head>
<body >
	<div id="container">
		<div id="vertical" style=''>
			
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; " >
						<img src="<%=request.getContextPath()%>/images/setting_tools.png" style="vertical-align: middle;" /> 
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">数据列表</span>
							<span style="font-size: 16px;font-weight: bolder;"></span>
						</td>
						<td style="text-align:right;">
							 
						</td>
					</tr>
				</table>
			</div>
			
			<div id="center-pane" style="border:0px;">
				<div style="border:0px;">
					<div id="tabstrip-1" class='tabstrip' style="border:0px;">
 		              <div id="grid" style="width:${width}px"></div>
 					</div>
				</div>
			</div>

		</div>
	</div>
 
</body>
</html>
<script type="text/javascript">
 
var $tabstrip = $(".tabstrip"),$vertical = $("#vertical"), contextPath = "<%=request.getContextPath()%>";

$vertical.css({
	height : $(window).height()
}).kendoSplitter({
	orientation: "vertical",
	panes: [
		{ collapsible: false , resizable : false, scrollable: false ,size : '32px'},
		{ collapsible: true, resizable : true, scrollable: false} 
	],
	layoutChange : onResize,
});

$("#center-pane").kendoSplitter({
	orientation: "horizontal",
	panes: [
		{ collapsible: true, resizable : true, scrollable: true}
	]
}); 

function onResize(e){
	
	$vertical.css({
		height : $(window).height()
	});
	
	$tabstrip.css({
		height : $tabstrip.closest('div[role=group]').height()
	});
	
}

$tabstrip.each(function(i,item){
	var $this = $(this);
	mtxx[$this.attr('id')] = $this.kendoTabStrip({
		animation:  {
			open: {
				effects: "fadeIn"
			}
		},
		select : function(e){
			var dd = $(this.wrapper);
			$('#' + e.contentElement.id).height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
		}
	});
});

</script>
