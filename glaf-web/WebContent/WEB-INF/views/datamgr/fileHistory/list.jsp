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
<title>文件历史版本列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
   <table>
     <tr>
		<td>
            <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	                onclick="javascript:reload();">重新加载</button>
		</td>
		<td><label>文件名：</label></td>
		<td><input id="fileNameLike" type="text" class="k-textbox" style="width:180px;" value="${fileNameLike}" /></td>
        <td><label>开始日期：</label></td>
		<td><input id="startDayPicker" style="width:100px;" value="${startDay}" /></td>
		<td><label>结束日期：</label></td>
		<td><input id="endDayPicker" style="width:100px;" value="${endDay}"/></td>
		<td><button type="button" id="queryButton" class="k-button" onclick="javascript:query();">查询</button></td>
		<td><button type="button" id="downButton" class="k-button" onclick="javascript:downloadZip();">下载</button></td>
	  </tr>
	</table>
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
                        "fileName": {
                            "type": "string"
                        },
                        "fileSize": {
							"type": "number"
                        },
                        "lastModified": {
							"type": "number"
                        },
                        "modifyDate_datetime": {
							"type": "string"
                        },
                        "path": {
                            "type": "string"
                        },
                        "version": {
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
                    "url": "<%=request.getContextPath()%>/rs/datamgr/fileHistory/data?startDay=${startDay}&endDay=${endDay}&fileNameLike=${fileNameLike}"
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
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500, 1000,2000],
                       "buttonCount": 10
                     },
		"selectable": "single",
		"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
				{
				"field": "path",
				"title": "路径",
                "width": "550px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "fileName",
				"title": "文件名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "fileSize",
				"title": "文件大小",
				"width": "90px",
				"align": "right",
				"lockable": false,
				"locked": false
                },
                {
				"field": "modifyDate_datetime",
				"title": "修改日期",
				"width": "135px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "version",
				"title": "版本",
				"width": "75px",
				"lockable": false,
				"locked": false
                },
		       {
			    "command": [{
                "text": "下载",
                "name": "download",
                "click": function showDetails(e) {
						var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
						var link = "<%=request.getContextPath()%>/mx/datamgr/fileHistory/download?fileId="+dataItem.fileId;
						window.open(link);
				    }
                }]
             }
	    ],
        "scrollable": {},
        "resizable": true,
        "groupable": false
      });

        jQuery("#startDayPicker").kendoDatePicker({
			culture:"zh-CN",
			format: "yyyy-MM-dd"
		});
		
		jQuery("#endDayPicker").kendoDatePicker({
			culture:"zh-CN",
			format: "yyyy-MM-dd"
		});

  });


  function query(){
	    var startDayPicker = jQuery("#startDayPicker").data("kendoDatePicker"),
			startDay = kendo.toString(startDayPicker.value(),"yyyy-MM-dd");
		if(startDay == null){
			startDay="";
		}
		
		var endDayPicker = jQuery("#endDayPicker").data("kendoDatePicker"),
			endDay = kendo.toString(endDayPicker.value(),"yyyy-MM-dd");
		if(endDay == null){
			endDay="";
		}

		var fileNameLike = jQuery("#fileNameLike").val();
		//alert(fileNameLike); 

		location.href="<%=request.getContextPath()%>/mx/datamgr/fileHistory?startDay="+startDay+"&endDay="+endDay+"&fileNameLike="+fileNameLike;
  }

  function downloadZip(){
	    var startDayPicker = jQuery("#startDayPicker").data("kendoDatePicker"),
			startDay = kendo.toString(startDayPicker.value(),"yyyy-MM-dd");
		if(startDay == null){
			startDay="";
		}
		
		var endDayPicker = jQuery("#endDayPicker").data("kendoDatePicker"),
			endDay = kendo.toString(endDayPicker.value(),"yyyy-MM-dd");
		if(endDay == null){
			endDay="";
		}
		window.open("<%=request.getContextPath()%>/mx/datamgr/fileHistory/downloadZIP?startDay="+startDay+"&endDay="+endDay);
  }

  function reload(){
	  if(confirm("还未进入版本管理的文件将会进入版本库，确定重新加载吗？")){
		  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/fileHistory/reload',
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

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="文件历史版本">&nbsp;
    文件历史版本
 </div>
<br>
<div id="grid"></div>
</div>  
<iframe id="newFrame" name="newFrame" width="0" height="0"></iframe>
<br/>
<br/>
<br/>
<br/>
</body>
</html>