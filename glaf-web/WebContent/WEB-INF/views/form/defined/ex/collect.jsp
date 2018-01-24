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
<title>聚合列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
	          onclick="javascript:clearAll();">清除</button> 

     <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
	          onclick="javascript:closeWin();">关闭</button> 
	
   </div>
</script>
<script type="text/javascript">
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}' ;
	function closeWin(){
		parent.layer.close(parent.layer.getFrameIndex());
	}

	function clearAll(){
		if(callbackFn){
			collectCallback({});
		}else{
			window.parent.$('#' + nameElementId).val("");
			window.parent.$('#' + objelementId).val("");
		}
		parent.layer.close(parent.layer.getFrameIndex());
	}

	var callbackFn = '${param.retFn}';

	function collectCallback(dataItem){
		var retObj = {
			name:dataItem.title,
			value:dataItem.id
		};
		parent[callbackFn](retObj);
		parent.layer.close(parent.layer.getFrameIndex());
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
                    "url": "<%=request.getContextPath()%>/rs/sys/treeTableAggregate/data"
                }
            },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        },
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
            "width": "280px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
			{
            "field": "targetTableName",
            "title": "目标表",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false,
            },	
            {
            "field": "createBy",
            "title": "创建人",
            "width": "100px",
            "lockable": false,
			"align": "center"
            },	
			{
			"command": [{
                "text": "选择",
                "name": "select",
                "click": function showDetails(e) {
				    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
				    if(callbackFn){
				    	collectCallback(dataItem);
				    }else{
				    	var retAry = [] ,
						retData = {id:dataItem.id,name:dataItem.title,targetTableName:dataItem.targetTableName} ;
						retAry.push(retData);
						window.parent.$('#' + nameElementId).val(dataItem.title);
						window.parent.$('#' + objelementId).val(JSON.stringify(retAry));
						parent.layer.close(parent.layer.getFrameIndex());
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


 </script>
</head>
<body>
<div id="grid"></div>

</body>
</html>