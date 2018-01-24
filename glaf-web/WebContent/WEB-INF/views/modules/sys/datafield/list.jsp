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
<title>字段列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>
	  <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:location.href='<%=request.getContextPath()%>/mx/system/datatable';">数据表列表</button>
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
                    "url": "<%=request.getContextPath()%>/rs/system/datafield/data?datatableId=${datatableId}"
                }
            }
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"selectable": "single",
		"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
			{
            "field": "chkbox",
            "title": "选择",
            "width": "70px"
            },
			{
            "field": "columnName",
            "title": "列名",
            "width": "120px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
            {
            "field": "name",
            "title": "名称",
            "width": "120px",
            "locked": false
            },
            {
            "field": "title",
            "title": "标题",
            "width": "180px",
            "locked": false
            },
            {
            "field": "dataTypeName",
            "title": "数据类型",
            "width": "120px",
            "locked": false
            },
            {
            "field": "length",
            "title": "字段长度",
            "width": "120px",
            "locked": false
            },
            {
            "field": "listWeigth",
            "title": "列表宽度",
            "width": "120px",
            "locked": false
            },
            {
            "field": "display",
            "title": "显示类型",
            "width": "120px",
            "locked": false
            },
		    {
			"command": [{
                "text": "修改",
                "name": "edit",
                "click": function showModify(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.id);
							var link = "<%=request.getContextPath()%>/mx/system/datafield/edit?id="+dataItem.id;
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
         editRow('<%=request.getContextPath()%>/mx/system/datafield/edit?datatableId=${datatableId}');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑字段信息",
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
  <img src="<%=request.getContextPath()%>/images/window.png" alt="${sysDataTable.title}字段列表">&nbsp;
    ${sysDataTable.title}字段列表
 </div>
<br>
<div id="grid"></div>
</div>     
</body>
</html>