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
	    <c:if  test="${param.retFn==null}">
	        <button type="button" id="" class="k-button" style="width: 90px" 
		          onclick="javascript:addRow();">新增</button>   
	   	</c:if>
	    <c:if  test="${param.retFn!=null}">
		   <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
		          onclick="javascript:clearAll();">清除</button> 

	       <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
		          onclick="javascript:closeWin();">关闭</button>          
	    </c:if>
    
		<button type="button" id="" class="k-button" style="width: 90px" 
		          onclick="javascript:reloadGrid(this);">重载</button>
		<input id="keywordsLike" name="keywordsLike" type="text" class="k-textbox"  
		         style="width:185px;" />
		<button type="button" id="searchButton" class="k-button" style="width: 90px" 
		          onclick="javascript:searchData(this);">查找</button>
   </div>
</script>
<script type="text/javascript">
//事件定义器中的回调方法
	var retFn = "${param.retFn}";
	var getFn = "${param.getFn}";
	var _selectDatasource = [];
	function callback(dataItem){
		//debugger;
		var data = {
			id : dataItem.id,
			name: dataItem.name,
			title : dataItem.title,
			createBy : dataItem.createBy,
			createTime : dataItem.createTime,
			dynamicDataSet : dataItem.dynamicDataSet
		};
		var retObj = {
			name:dataItem.name,
			value:dataItem.id,
			data : data
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

	var idata = [];
	if(getFn){
  		idata =  parent[getFn]();
  		if(idata && idata.data){
  			_selectDatasource.push(idata.data);
  		}	
    }


    var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/rs/tree/treeJson?nodeCode=<%=code%>",
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
			childNodes[i].icon="<%=request.getContextPath()%>/images/basic.gif";
		}
		return childNodes;
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		var link="<%=request.getContextPath()%>/rs/dataset/data?nodeId="+treeNode.id;
	    var grid = jQuery("#grid").data("kendoGrid");
		//alert(grid);
	//	grid.setDataSource(createGridDataSource(link));
		
		window.searchData();
 	}

	function loadMxData(url){
		  location.href=url;
	}

    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

    function reloadGrid(){
       // var link="<%=request.getContextPath()%>/rs/dataset/data";
	    //var grid = jQuery("#grid").data("kendoGrid");
	//	grid.setDataSource(createGridDataSource(link));
		searchData();
	}

	function searchData(o){
	//	var keywordsLike=document.getElementById("keywordsLike").value;
	//	keywordsLike = jQuery.base64.encode(keywordsLike);
	//	var link="<%=request.getContextPath()%>/rs/dataset/data?keywordsLike_base64="+keywordsLike;
		
	  //var grid = jQuery("#grid").data("kendoGrid");
		//grid.setDataSource(createGridDataSource(link));
		
		//params = $.extend((params || {}), {
		//	keywordsLike : keywordsLike
	//	});
		
		$(".mt-grid").each(function(){
			$(this).data("kendoGrid").dataSource.read({
				//keywordsLike : keywordsLike,
				//nodeId : jQuery("#nodeId").val()
			});
		})
		
	}
	
	function getParameters(){
		var keywordsLike=document.getElementById("keywordsLike").value;
		return {
			keywordsLike : keywordsLike,
			nodeId : jQuery("#nodeId").val()
		};
		
	}

	//创建grid表格数据源
	function createGridDataSource(link){
		var dataSource = new kendo.data.DataSource({
			type: "json",
            transport: {
            	parameterMap: function(options) {
            		options.rows = options.pageSize;
                    return JSON.stringify(options);
                },
                read: {
		            "contentType": "application/json",
                    "type": "POST",
                    "url": link
                }
            },
			serverFiltering: true,
            serverSorting: true,
            pageSize: 10,
            serverPaging: true,
            schema:{
            	total: "total",
            	data: "rows",
            	model:{
            		fields:{
                        "id": {
                            "type": "string"
                        },
                        "startIndex": {
                            "type": "number"
                        }
                    }
            	}
            }
		});
		return dataSource;
	}

	var command = [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
			            var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                        //alert(JSON.stringify(dataItem));
		                //alert(dataItem.id);
			            var link = "<%=request.getContextPath()%>/mx/dataset/edit?id="+dataItem.id;
			            editRow(link);
		                }
                },
				{
                "text": "删除",
                "name": "delete",
                "click": function deleteItem(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                            if(confirm("数据删除后不能恢复，确定删除吗？")){
                                var link = "<%=request.getContextPath()%>/mx/dataset/delete?id="+dataItem.id;
								var params = jQuery("#iForm").formSerialize();
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
                "text": "SQL构建器",
                "name": "designer",
                "click": function showSqlbuilder(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                            //alert(JSON.stringify(dataItem));
		                    //alert(dataItem.id);

			                var link = "<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id="+dataItem.id;
			                if(dataItem.dynamicDataSet && dataItem.dynamicDataSet == 'Y'){
			                	link = '<%=request.getContextPath()%>/mx/form/dataset/dynamicDatabase?id='+dataItem.id;
			                }
			                window.open(link);
		                }
                }],
        seacrhCommand = [{
                "text": "选择",
                "name": "select",
                "click": function showSqlbuilder(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							callback(dataItem);
                }
        },
		{
        "text": "SQL构建器",
        "name": "designer",
        "click": function showSqlbuilder(e) {
	                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                    //alert(JSON.stringify(dataItem));
                    //alert(dataItem.id);

	                var link = "<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id="+dataItem.id;
	                if(dataItem.dynamicDataSet && dataItem.dynamicDataSet == 'Y'){
	                	link = '<%=request.getContextPath()%>/mx/form/dataset/dynamicDatabase?id='+dataItem.id;
	                }
	                window.open(link);
                }
        }];
        //<button class='k-button' t='sure' >选择</button>

    jQuery(function() {
    	if(getFn || retFn){
    		jQuery("#selectedGrid").kendoGrid({
		        "columnMenu": true,
		        "dataSource": _selectDatasource,
		        "height": 87,
		        // "reorderable": true,
		        // "filterable": true,
		        "sortable": true,
				// "pageable": {
		  //                      "refresh": true,
		  //                      "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
		  //                      "buttonCount": 10
		  //                    },
				"selectable": "single",
				"toolbar": '<div class="toolbar"><span class="x_content_title">已选数据集</span>(旧的没有需要重新选择)</div>',
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
		            },
				    {
					"command": [{
				        "text": "SQL构建器",
				        "name": "designer",
				        "click": function showSqlbuilder(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
		                    //alert(JSON.stringify(dataItem));
		                    //alert(dataItem.id);

			                var link = "<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id="+dataItem.id;
			                if(dataItem.dynamicDataSet && dataItem.dynamicDataSet == 'Y'){
			                	link = '<%=request.getContextPath()%>/mx/form/dataset/dynamicDatabase?id='+dataItem.id;
			                }
			                window.open(link);
		                }
			        }]
		          }
				],
		        // "scrollable": {},
		        // "resizable": true,
		        // "groupable": false
		    });
    	}else{
    		jQuery("#selectedGrid").remove();
    	}
      
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
                	 // return JSON.stringify(options);
                	 options.rows = options.pageSize;
                	 
                	 $.extend(true, options, window.getParameters());

                    return options;
                },
                "read": {
		          //  "contentType": "application/json",
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
        "height": retFn?"465px":x_height,
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
		var nodeId = jQuery("#nodeId").val();
		if(nodeId==null || nodeId=='' ){
			alert("请在左边选择分类类型！");
			return;
		}
        editRow('<%=request.getContextPath()%>/mx/dataset/edit?nodeId=' + nodeId);
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑数据集信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['820px', (jQuery(window).height() - 40) +'px'], iframe: {src: link}
		});
	}

 </script>
</head>
<body>
	<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" style="width: 180px;">
			<div class="easyui-layout" data-options="fit:true">

				<div data-options="region:'center',border:false">
					<ul id="myTree" class="ztree"></ul>
				</div>

			</div>
		</div>
		<div data-options="region:'center'" style="overflow-y: scroll;">
			<!-- <div class="easyui-layout" data-options="fit:true">  
	  <div data-options="region:'center',border:false">
		 <div id="grid"></div>
	  </div>  
	</div> -->
			<div id="selectedGrid" class="mt-grid"></div>
			<!-- data-options="fit:true" 为自适应，会自动充满100% -->
			<!-- <div id="tt" class="easyui-tabs" style="height:349px" data-options="fit:true"> -->
			<div id="tt" class="easyui-tabs" style="height:451px"
				<c:if  test="${param.retFn == null}">
				   data-options="fit:true"
			    </c:if>
			 >
				<div title="视图1" style="padding: 10px;">
					
					<div id="grid" class="mt-grid"></div>
				</div>
				<div title="视图2" data-options="closable:false"
					style="overflow: auto; padding: 10px;">
					<%@ include file="/WEB-INF/views/datamgr/dataset/treelist.jsp"%>
				</div>
			</div>

		</div>
	</div>
</body>

</html>