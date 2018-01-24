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
<title>录像机列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">添加录像机</button>   
	  <button type="button" id="button2"  class="k-button" style="width: 120px" 
	  onclick="javascript:syncAllCameras();">同步全部摄像头</button>   
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
                        "deviceName": {
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
                    "url": "<%=request.getContextPath()%>/rs/video/device/data"
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
				"lockable": false,
				"locked": false
                },
                {
				"field": "deviceName",
				"title": "录像机名称",
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
				"field": "deviceId",
				"title": "编号",
                "width": "320px",
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
							//alert(dataItem.deviceId);
							var link = "<%=request.getContextPath()%>/mx/video/device/edit?deviceId="+dataItem.deviceId;
							editRow(link);
		                }
                },
				{
                "text": "删除",
                "name": "del",
                "click": function delRow(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							if(confirm("录像机关联的摄像头也会一同删除，确定删除吗？")){
                                var link = "<%=request.getContextPath()%>/mx/video/device/delete?deviceId="+dataItem.id;
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
											   window.location.reload();
										   }
									 });
							   }
							}
                },
				{
                "text": "同步设备",
                "name": "syncDevice",
                "click": function syncDevice(e) {
							   var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							   var link = "<%=request.getContextPath()%>/mx/video/device/syncDevice?deviceId="+dataItem.id;
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
											   window.location.reload();
										   }
									 });
							   }
                },
				{
                "text": "同步摄像头",
                "name": "syncCameras",
                "click": function syncCameras(e) {
							   var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							   var link = "<%=request.getContextPath()%>/mx/video/device/syncCameras?deviceId="+dataItem.id;
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
											   window.location.href="<%=request.getContextPath()%>/mx/video/camera?deviceId="+dataItem.id;
										   }
									 });
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
         editRow('<%=request.getContextPath()%>/mx/video/device/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑录像机",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function syncAllCameras(){
		if(confirm("确定从萤石云服务器同步摄像头到本地服务器吗？")){
            var link = "<%=request.getContextPath()%>/mx/video/device/syncAllCameras";
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
			                  window.location.href="<%=request.getContextPath()%>/mx/video/camera";
							 }
				  });
		}
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="录像机列表">&nbsp;
    录像机列表
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