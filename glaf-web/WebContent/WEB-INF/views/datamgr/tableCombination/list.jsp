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
<title>组合表列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
   	  <c:if  test="${param.retFn==null}">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>
	  &nbsp;&nbsp;
      <button type="button" id="execAllButton"  class="k-button" style="width: 90px" 
	          onclick="javascript:executeAll();">全部执行</button> 
	  &nbsp;&nbsp;
      <!-- <button type="button" id="execAllButton"  class="k-button" style="width: 90px" 
	          onclick="javascript:dropAllTable();">全部重建</button> -->              
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
			name:dataItem.name,
			value:dataItem.id
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
								    var link = "<%=request.getContextPath()%>/mx/sys/tableCombination/edit?id="+dataItem.id;
								    editRow(link);
								}
                },
				{
                "text": "规则",
                "name": "rule",
                "click": function showRules(e) {
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    //alert(JSON.stringify(dataItem));
								    //alert(dataItem.id);
								    var link = "<%=request.getContextPath()%>/mx/sys/tableCombination/rule?tableCombinationId="+dataItem.id;
								    editRule(link);
								}
                },
				{
                "text": "重建表",
                "name": "dropTable",
                "click": function dropTable(e) {
					       if(confirm("之前抽取的数据将清除，确定在目标库中执行重新建表功能吗？")){
								var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								var link = "<%=request.getContextPath()%>/mx/sys/tableCombination/dropTable?tableCombinationId="+dataItem.id;
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
											   }
										 });
							}
					  }
                },
				{
                "text": "执行",
                "name": "execute",
                "click": function executeSync(e) {
					       if(confirm("确定在选择库中执行数据同步吗？")){
								var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								var link = "<%=request.getContextPath()%>/mx/sys/tableCombination/execute?tableCombinationId="+dataItem.id;
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
											   }
										 });
							}
					  }
                },
				{
                "text": "数据",
                "name": "tableData",
                "click": function showDBTable(e) {
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    //alert(JSON.stringify(dataItem));
								    //alert(dataItem.id);
								    var link = "<%=request.getContextPath()%>/mx/sys/table/resultList?q=1&tableName_enc="+dataItem.targetTableName_enc;
								    showTableData(link);
								}
                },
				{
                "text": "处理器",
                "name": "handler",
                "click": function handler(e) {
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    var link = "<%=request.getContextPath()%>/mx/sys/syntheticFlow/edit?currentStep=table_combination_"+dataItem.id+"&code=table_combination";
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
								    //var link = "<%=request.getContextPath()%>/mx/logging?moduleId=table_combination_"+dataItem.id;
									var link = "<%=request.getContextPath()%>/mx/sys/executionLog?type=table_combination&businessKey="+dataItem.id;
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
                    "url": "<%=request.getContextPath()%>/rs/sys/tableCombination/data"
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
            "width": "280px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
			{
            "field": "templateTableName",
            "title": "模板表",
            "width": "120px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
			{
            "field": "targetTableName",
            "title": "目标表",
            "width": "120px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },	
            {
            "field": "syncTime",
            "title": "同步时间",
            "width": "100px",
            "lockable": false,
			"align": "center",
			"format": "{0: yyyy-MM-dd}",
			"filterable": {
              "ui": "datetimepicker"  
              }
            },	
			{
            "field": "syncStatusText",
            "title": "同步状态",
            "width": "90px",
			"expandable": true,
			"lockable": false,
            "locked": false
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
         editRow('<%=request.getContextPath()%>/mx/sys/tableCombination/edit');
    }

    function dropAllTable(){
		if(confirm("确定要清空全部数据并重新创建表结构吗？")){
			var link = "<%=request.getContextPath()%>/mx/sys/tableCombination/dropAllTable";
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
						   if(data.statusCode == 200){
							   window.location.reload();
						   }
					   }
				 });
		}
	}

	function executeAll(){
		if(confirm("确定要执行全部定义表的数据同步吗？")){
			var link = "<%=request.getContextPath()%>/mx/sys/tableCombination/executeAll";
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
						   if(data.statusCode == 200){
							   window.location.reload();
						   }
					   }
				 });
		}
	}

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑组合表信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

    function editRule(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑组合规则信息",
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
			title: "同步日志信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['1080px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function showTableData(link){
		//alert(link);
        jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "显示表数据",
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
  <img src="<%=request.getContextPath()%>/images/window.png" alt="组合表列表">&nbsp;
    组合表列表
 </div>
<br>
</c:if>
<div id="grid"></div>
</div>
</body>
</html>