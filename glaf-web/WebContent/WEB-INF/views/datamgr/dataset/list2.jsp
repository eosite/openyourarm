<%@page import="com.glaf.core.config.CustomProperties"%>
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
    String code =  CustomProperties.getString("dataSet.code");
    code = RequestUtils.getString(request, "code", code == null ? "report_category" : code);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据集列表</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.base64.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      	<span class="x_content_title">数据集列表</span>&nbsp;&nbsp;
		<input id="keywordsLike" name="keywordsLike" type="text" class="k-textbox"  
		         style="width:185px;" />
		<button type="button" id="searchButton" class="k-button" style="width: 90px" 
		          onclick="javascript:searchData(this);">查找</button>
		<button type="button" id="saveButton" class="k-button" style="width: 90px" 
		          onclick="javascript:saveData(this);">确定</button>
   </div>
</script>
<script type="text/javascript">
    function reloadGrid(){
       // var link="<%=request.getContextPath()%>/rs/dataset/data";
		searchData();
	}

	function searchData(o){
		var keywordsLike=document.getElementById("keywordsLike").value;
		var link="<%=request.getContextPath()%>/rs/dataset/data?keywordsLike_base64="+keywordsLike;
		
		$("#grid").data("kendoGrid").dataSource.read({
			keywordsLike : keywordsLike,
			nodeId : jQuery("#nodeId").val()
		});
	}
	
	function saveData(){
		var grid = jQuery("#grid").data("kendoGrid");
		var row = grid.select(), items = [];
		if(row && row.length){
			$.each(row, function(){
				var data = grid.dataItem(this);
				items.push(data);
			});
		} else {
			alert("请选择!");
			return;
		}
		var $parent = window.opener || window.parent;
		var fn = "${param.fn}";
		$parent && $parent[fn] && $parent[fn](items);
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
                	 options.rows = options.pageSize;
                    return options;
                },
                "read": {
		            "contentType": "application/x-www-form-urlencoded; charset=UTF-8",
                    "type": "POST",
                    dataType : 'JSON',
                    "url": "<%=request.getContextPath()%>/mx/dataset/json?nodeId=${nodeId}"
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
            "field": "name",
            "title": "名称",
            "width": "220px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },		
			{
            "field": "title",
            "title": "标题",
            "width": "280px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },		
			{
            "field": "createBy",
            "title": "创建人",
            "width": "100px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
            {
            "field": "createTime",
            "title": "创建日期",
            "width": "100px",
            "lockable": false,
			"align": "center",
			"format": "{0: yyyy-MM-dd}",
			"filterable": {
              "ui": "datetimepicker"  
              }
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
	<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}">
	<div id="grid"></div>
</body>

</html>