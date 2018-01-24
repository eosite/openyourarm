<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转换配置</title>

<script type="text/javascript">
var contextPath="${contextPath}";
</script>

<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link href="${contextPath}/scripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.rtl.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.default.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.dataviz.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.mobile.all.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/layer/skin/layer.css" rel="stylesheet" />
<link href="${contextPath}/themes/default/styles.css" rel="stylesheet" type="text/css" >
<link href="${contextPath}/css/icons.css" rel="stylesheet" type="text/css" >
<link href="${contextPath}/css/kendoui.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/json2.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/angular.min.js"></script> 
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/kendo.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/jszip.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="${contextPath}/inc/globaljs.jsp"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.base64.js"></script>

<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <span class="x_content_title">行转列配置列表</span>&nbsp;&nbsp;
      <button type="button" id="newButton" class="k-button" style="width: 90px" 
	          onclick="javascript:addRow();">新增</button>   
	  <input id="keywordsLike" name="keywordsLike" type="text" class="k-textbox"  
	         style="width:185px;" />
	  <button type="button" id="searchButton" class="k-button" style="width: 90px" 
	          onclick="javascript:searchData();">查找</button>
   </div>
</script>
<script type="text/javascript">

	$(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	// 	var dataSrcUrl = contextPath + "/mx/dataset/handle/listDataSources";
	// 	$.ajax({
	// 			url : dataSrcUrl,
	// 			type: "POST",
	// 	      	dataType: "json",
	// 	      	data: JSON.stringify({pageSize:10}),
	// 	      	contentType: "application/json",
	// 			success : function(data){
	// 				if(data){
	// 					if(data.statusCode == 500) {							
	// 						alert('服务器处理错误！');
	// 					}else{
	// 						$.each(data.rows,function(i,o){
	// 							var $li  = $('<li style="vertical-align: middle;height:18px;"><a href="javascript:;" style="font-size:15px;"><span class="button ico_docu" style=""></span>'+o.sourceName+'</a></li>');		
	// 							$li.data("sourceId",o.id);
	// 							$("#myTree").append($li);
	// 						});
	// 					}
	// 				}
	// 			},
	// 			error : function(data){
	// 				 alert('服务器处理错误！');
	// 			} 
	// 	});
	});

    var setting = {
		async: {
			enable: true,
			url:"${contextPath}/rs/tree/treeJson?nodeCode=datatransfer_project",
			dataFilter: filter
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="${contextPath}/images/basic.gif";
		}
		return childNodes;
	}
    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		$("#nodeId").val(treeNode.id);
		reloadGrid();
 	}

 	function reloadGrid(){
		var link="${contextPath}/mx/datatransfer/listTransferConfigs";
	    var grid = $("#grid").data("kendoGrid");
		grid.setDataSource(createGridDataSource(link));
 	}

 	jQuery(function() {

    	$("#grid").kendoGrid({

    		"height": x_height,
    		"reorderable": true,
	        "filterable": true,
	        "sortable": true,
	        "scrollable": {},
	        "resizable": true,
	        "groupable": false,
	        "selectable": "single",
            "toolbar": kendo.template(jQuery("#template").html()),
        	"pageable": {
                   "refresh": true,
                   "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                   "buttonCount": 10
                 },
    		dataSource:{},
            
    		"columns": [
				{
	            "field": "transName",
	            "title": "配置名称",
	            "width": "220px",
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
	            },
	            {"command": [{
	                "text": "修改",
	                "name": "edit",
	                "click": function showDetails(e) {
				            	var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
		                        //alert(JSON.stringify(dataItem));
				                //alert(dataItem.id);
				           		var link = "${contextPath}/mx/etl/model/transferedit?id="+dataItem.id;
				           	 	editRow(link);
				           	 	
			                }
			               
	                },
					{
	                "text": "删除",
	                "name": "delete",
	                "click": function deleteItem(e) {
				                var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
	                            if(confirm("数据删除后不能恢复，确定删除吗？")){
	                                var link= "${contextPath}/mx/datatransfer/deleteTransferConfig?sourceId="+dataItem.id;
									var params = jQuery("#iForm").formSerialize();
									$.ajax({
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
										   // window.location.reload();
										  reloadGrid();
									   }
									});
								}
			                }
	                },
					{
	                "text": "配置构建器",
	                "name": "designer",
	                "click": function showSqlbuilder(e) {
				                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
	                            //alert(JSON.stringify(dataItem));
			                    //alert(dataItem.id);
				                var link = "${contextPath}/mx/etl/model/transfer_config?id="+dataItem.id;
				                window.open(link);
			                }
	                }]
          		}]
    	});
		reloadGrid();
     });
 	//创建grid表格数据源
	function createGridDataSource(link){
		var dataSource = new kendo.data.DataSource({
            transport: {
			    read: {
			      type: "POST",
			      dataType: "json",
			      contentType: "application/json",
			      url: link,
			    },
			    parameterMap: function(options) {
                	return JSON.stringify(options);
            	},
			},
			"serverFiltering": true,
        	"serverSorting": true,
        	"pageSize": 10,
        	"serverPaging": true,
           	schema:{
					"total": "total",
                	"data": "rows"
    		}
		});
		return dataSource;
	}

	function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑数据源信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['820px', ($(window).height() - 40) +'px'], iframe: {src: link}
		});
	}


    function addRow(){
		var nodeId = jQuery("#nodeId").val();
		if(nodeId==null || nodeId=='' ){
			alert("请在左边选择分类类型！");
			return;
		}
        editRow('${contextPath}/mx/etl/model/transferedit');
    }

	function searchData(){
		var keywordsLike=document.getElementById("keywordsLike").value;
		keywordsLike = jQuery.base64.encode(keywordsLike);
		var link="${contextPath}/mx/datatransfer/listTransferConfigs?keywordsLike_base64="+keywordsLike;
	    var grid = jQuery("#grid").data("kendoGrid");
		grid.setDataSource(createGridDataSource(link));
	}

	function loadMxData(url){
		location.href=url;
	}

 </script>
</head>
<body>
	<input type="hidden" id="nodeId" name="nodeId" value="" > 
	<div class="easyui-layout" data-options="fit:true">  

	    <div data-options="region:'west',split:true" style="width:180px;">
		  <div class="easyui-layout" data-options="fit:true">  
	           
				 <div data-options="region:'center',border:false">
				    <ul id="myTree" class="ztree"></ul>  
				 </div> 
				 
	        </div>  
		</div> 

	   <div data-options="region:'center'"> 
		<div class="easyui-layout" data-options="fit:true">  
		  <div data-options="region:'center',border:false">
			 <div id="grid"></div>
		  </div>  
		</div>
	  </div>

	</div>
</body>
</html>