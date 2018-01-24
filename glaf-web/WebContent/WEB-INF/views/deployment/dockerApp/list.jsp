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
<title>应用列表</title>
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
                        "title": {
                                                        "type": "string"
                        },
                        "port": {
							"type": "number"
                        },
                        "status": {
							"type": "number"
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
                    "url": "<%=request.getContextPath()%>/rs/deployment/dockerApp/data"
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
                "width": "320px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "code",
				"title": "代码",
                "width": "120px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "port",
				"title": "对外端口",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
				{
				"field": "statusLabel",
				"title": "状态",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createBy",
				"title": "创建人",
                "width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createTime",
				"title": "创建时间",
				"width": "90px",
				"format": "{0: yyyy-MM-dd}",
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
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.id);
							var link = "<%=request.getContextPath()%>/mx/deployment/dockerApp/edit?id="+dataItem.id;
							editRow(link);
						}
                },{
                "text": "编排",
                "name": "compose",
                "click": function showCompose(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.id);
							var link = "<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment?deploymentId="+dataItem.deploymentId;
							compose(link);
						}
                },{
                "text": "编排文件",
                "name": "composeFile",
                "click": function showCompose(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							//alert(JSON.stringify(dataItem));
							//alert(dataItem.id);
							var link = "<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment/viewCompose?deploymentId="+dataItem.deploymentId;
							composeFile(link);
						}
                },{
                "text": "重新发布",
                "name": "deploy",
                "click": function deploy(e) {
					       if(confirm("将根据最新的配置重新生成相关配置信息，并在下次重启时生效，确定重新配置吗？")){
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							var link = "<%=request.getContextPath()%>/mx/deployment/dockerApp/deploy?deploymentId="+dataItem.deploymentId;
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
                },{
                "text": "启动",
                "name": "start",
                "click": function start(e) {
							var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							var link = "<%=request.getContextPath()%>/mx/deployment/dockerApp/start?deploymentId="+dataItem.deploymentId;
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
                },{
                "text": "停止",
                "name": "stop",
                "click": function stop(e) {
					        if(confirm("应用会随容器关闭而停止，确定停止应用吗？")){
							    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							    var link = "<%=request.getContextPath()%>/mx/deployment/dockerApp/stop?deploymentId="+dataItem.deploymentId;
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
                }]
          }
	],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });


    function addRow(){
         editRow('<%=request.getContextPath()%>/mx/deployment/dockerApp/edit');
    }

	function compose(link){
         location.href=link;
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑应用信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['820px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

    function composeFile(link){
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
			area: ['820px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="应用列表">&nbsp;
    应用列表
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