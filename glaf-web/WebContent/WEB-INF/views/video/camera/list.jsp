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
<title>摄像头列表</title>
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
                        "name": {
                                                        "type": "string"
                        },
                        "code": {
                                                        "type": "string"
                        },
                        "desc": {
                                                        "type": "string"
                        },
                        "cameraNo": {
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
                    "url": "<%=request.getContextPath()%>/rs/video/camera/data?deviceId=${deviceId}"
                }
            },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 100,
            "serverPaging": true
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 100
                     },
		"selectable": "single",
		//"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
                {
				"field": "name",
				"title": "名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
				{
				"field": "deviceName",
				"title": "设备名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "deviceSerial",
				"title": "序列号",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "cameraNo",
				"title": "通道",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
			    {
				"field": "cameraName",
				"title": "摄像头名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
			    {
				"field": "createTime",
				"title": "创建日期",
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
							//alert(dataItem.cameraId);
							var link = "<%=request.getContextPath()%>/mx/video/camera/edit?cameraId="+dataItem.cameraId;
							editRow(link);
		                }
                },
				{
                "text": "播放",
                "name": "play",
                "click": function playVideo(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.cameraId);
							var link = "<%=request.getContextPath()%>/mx/video/camera/play?enableFunction=true&cameraId="+dataItem.cameraId;
							play(link);
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
         editRow('<%=request.getContextPath()%>/mx/video/camera/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑摄像头",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function play(link){
		window.open(link);
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="摄像头列表">&nbsp;
    摄像头列表
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