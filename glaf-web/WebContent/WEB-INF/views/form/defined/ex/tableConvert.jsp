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
<title>列转换列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
    <c:if  test="${param.retFn==null}">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>      
	</c:if>         
	<c:if  test="${param.retFn!=null}">
	   <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
	          onclick="javascript:clearAll();">清除</button> 

       <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
	          onclick="javascript:closeWin();">关闭</button>          
    </c:if>
   </div>
</script>
<script type="text/javascript">

//事件定义器中的回调方法
	var retFn = "${param.retFn}";
	function callback(dataItem){
		//debugger;
		var retObj = {
			name:dataItem.title,
			value:dataItem.id,
			tableConvertCall : dataItem
		};
		parent[retFn](retObj);
		closeWin();
	}
	function closeWin(){
		parent.layer.close(parent.layer.getFrameIndex());
	}
	function clearAll(){
		callback({});
		closeWin();
	}
	var command = [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
								  var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  //alert(JSON.stringify(dataItem));
							      //alert(dataItem.id);
								  var link = "<%=request.getContextPath()%>/mx/sys/matrixTableTransform/edit?transformId="+dataItem.id;
								  editRow(link);
								}
                },
				{
                "text": "删除",
                "name": "delete",
                "click": function deleteRow(e) {
								     var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								     //alert(JSON.stringify(dataItem));
							         //alert(dataItem.id);
									 if(confirm("数据删除后不能恢复，确定删除吗？")){
										 var link = "<%=request.getContextPath()%>/mx/sys/matrixTableTransform/delete?transformId="+dataItem.id;
										 var params = jQuery("#iForm").formSerialize();
										 jQuery.ajax({
												   type: "POST",
												   url: link,
												   dataType: 'json',
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
                "text": "转换列",
                "name": "columns",
                "click": function showColumns(e) {
								  var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  //alert(JSON.stringify(dataItem));
							      //alert(dataItem.id);
								  var link = "<%=request.getContextPath()%>/mx/sys/matrixColumnTransform?transformId="+dataItem.id;
								  editColumns(link);
								}
                },
				{
                "text": "执行",
                "name": "execute",
                "click": function executeRow(e) {
								     var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								     //alert(JSON.stringify(dataItem));
							         //alert(dataItem.id);
								     var link = "<%=request.getContextPath()%>/mx/sys/matrixTableTransform/execute?transformId="+dataItem.id;
									 var params = jQuery("#iForm").formSerialize();
									 jQuery.ajax({
											   type: "POST",
											   url: link,
											   dataType: 'json',
											   error: function(data){
												   alert('服务器处理错误！');
											   },
											   success: function(data){
												   if(data != null && data.message != null){
													   alert(data.message);
												   } else {
													   alert('操作成功完成！');
												   }
												   if(data.statusCode == 200){
												     window.location.reload();
												   }
											   }
									});
								}
                },
				{
                "text": "重置",
                "name": "emptyColumns",
                "click": function emptyColumnsRow(e) {
					             if(confirm("确定重置目标字段的值为空吗？")){
								     var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								     //alert(JSON.stringify(dataItem));
							         //alert(dataItem.id);
								     var link = "<%=request.getContextPath()%>/mx/sys/matrixTableTransform/emptyColumns?transformId="+dataItem.id;
									 var params = jQuery("#iForm").formSerialize();
									 jQuery.ajax({
											   type: "POST",
											   url: link,
											   dataType: 'json',
											   error: function(data){
												   alert('服务器处理错误！');
											   },
											   success: function(data){
												   if(data != null && data.message != null){
													   alert(data.message);
												   } else {
													   alert('操作成功完成！');
												   }
												   if(data.statusCode == 200){
												     window.location.reload();
												   }
											   }
										 });
								}
				           }
                },
				{
                "text": "处理器",
                "name": "handler",
                "click": function handler(e) {
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    var link = "<%=request.getContextPath()%>/mx/sys/syntheticFlow/edit?currentStep=matrix_table_transform_"+dataItem.id+"&code=table_transform";
								    showHandler(link);
								}
                },
				{
                "text": "日志",
                "name": "log",
                "click": function showLogging(e) {
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    //alert(JSON.stringify(dataItem));
								    //alert(dataItem.id);
								    //var link = "<%=request.getContextPath()%>/mx/logging?moduleId=table_transform_"+dataItem.id;
									var link = "<%=request.getContextPath()%>/mx/sys/executionLog?type=matrix_table_transform&businessKey="+dataItem.id;
								    showLog(link);
								}
                }
				],
		seacrhCommand = [{
                "text": "选择",
                "name": "select",
                "click": function showSqlbuilder(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							callback(dataItem);
                }
        }];
	
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
                    "url": "<%=request.getContextPath()%>/rs/sys/matrixTableTransform/data"
                }
            },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        },
        "height": retFn?"445px":x_height,
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
            "width": "220px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
			{
            "field": "tableName",
            "title": "表名",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
            {
            "field": "transformTime",
            "title": "转换时间",
            "width": "100px",
            "lockable": false,
			"align": "center",
			"format": "{0: yyyy-MM-dd}",
			"filterable": {
              "ui": "datetimepicker"  
              }
            },	
			{
            "field": "transformStatusText",
            "title": "状态",
            "width": "90px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
            {
            "field": "createBy",
            "title": "创建人",
            "width": "100px",
            "lockable": false,
			"align": "center"
            },	
		    {
			"command": retFn?seacrhCommand:command
          }
	    ],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });


    function addRow(){
         editRow('<%=request.getContextPath()%>/mx/sys/matrixTableTransform/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑表转换信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function editColumns(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "表转换列信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['1080px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function showHandler(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "处理器设置",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['1080px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function showLog(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "日志信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['1080px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}
 </script>
</head>
<body>
<div id="main_content" class="k-content">
<c:if  test="${param.retFn==null}">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="表转换列表">&nbsp;
    表转换列表
 </div>
<br>
</c:if>
<div id="grid"></div>
</div>
<br/>
<br/>
<br/>
<br/>
</body>
</html>