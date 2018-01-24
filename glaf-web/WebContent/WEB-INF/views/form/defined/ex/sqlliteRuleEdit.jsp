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
	var id = "${param.id}" || "${id}";

	//已选择的数据集
	var selectDatasource = [];

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
		var keywordsLike=document.getElementById("keywordsLike").value;
	//	keywordsLike = jQuery.base64.encode(keywordsLike);
		var link="<%=request.getContextPath()%>/rs/dataset/data?keywordsLike_base64="+keywordsLike;
		
	  //var grid = jQuery("#grid").data("kendoGrid");
		//grid.setDataSource(createGridDataSource(link));
		
		//params = $.extend((params || {}), {
		//	keywordsLike : keywordsLike
	//	});
		
		$(".mt-grid").each(function(){
			$(this).data("kendoGrid").dataSource.read({
				keywordsLike : keywordsLike,
				nodeId : jQuery("#nodeId").val()
			});
		})
		
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

	//刷新 已选择数据集
	function rushSelectDatasourceGrid() {
		//刷新选择的数据集
		var grid = $("#selectDatasourceGrid").data("kendoGrid");
		grid.dataSource.read();
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
                	var tr = $(e.target).closest("tr"), data = this.dataItem(tr), flag = true;
					$.each(selectDatasource, function(i, v) {
						if (v.dataSetId == data.dataSetId) {
							layer.alert('不能重复添加数据集！', 3);
							flag = false;
						}
					});
					if(flag){
						selectDatasource.push(data);
						rushSelectDatasourceGrid();
					}
			                // var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							// callback(dataItem);
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

    function initData(){
    	var params = {
    		id : id
    	}
    	jQuery.ajax({
			   type: "POST",
			   url: '<%=request.getContextPath()%>/mx/form/wdatasetSqllite/getById',
			   data: params,
			   async: false,
			   dataType:  'json',
			   error: function(data){
				   alert('服务器处理错误！');
			   },
			   success: function(data){
			   		if(data.ruleJson){
					   	selectDatasource = JSON.parse(data.ruleJson);
						// rushSelectDatasourceGrid();
					}
			   }
		});
    }

    function saveSqlliteRule(){
    	var params = {
    		id : id,
    		ruleJson : JSON.stringify(selectDatasource)
    	}
    	jQuery.ajax({
			   type: "POST",
			   url: '<%=request.getContextPath()%>/mx/form/wdatasetSqllite/saveWdatasetSqllite',
			   data: params,
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
				   		// window.parent.location.reload();
				   window.close();
				   }
		 });
    }

    function sureBtnFunction(){
		// window.opener[retFn](selectDatasource);
		if(id){
			saveSqlliteRule();
			
		}else{

		}
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
                	 // return JSON.stringify(options);
                	 options.rows = options.pageSize;

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
        "height": 280,
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
			"command": seacrhCommand
          }
	],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });

	var databaseSource = new kendo.data.DataSource({//编辑器数据源
		type : "json",
		transport : {
			read : {
				contentType : "application/json",
				url : "${pageContext.request.contextPath}/rs/isdp/global/databaseJson",
				type : "POST",
				dataType : "JSON"
			},
			parameterMap : function(options, operation) {
				if (operation == "read") {
					return JSON.stringify(options);
				}
			}
		}
	});
	databaseSource.fetch().then(function() {
		// rushSelectColumns();
	});//加载数据源


	if(id){
		initData();
	}
	//已选择的数据集
	$("#selectDatasourceGrid").kendoGrid({
		dataSource : selectDatasource,
		height : 200,
		sortable : true,
		toolbar : '<div class="toolbar"><button type="button" id="newButton"  class="k-button" style="width: 90px" 			onclick="javascript:sureBtnFunction()"><span class="k-icon k-update"></span>确定</button></div>',
		columns : [ {
			field : 'name',
			title : '名称',
			width : 200
		}, {
			field : 'title',
			title : '标题',
			width : 250
		}, {
			field : 'databaseId',
			title : '标段',
			width : 70,
			template : function(dataItem){
				var ret = "默认" ;
				databaseSource.fetch().then(function(){
					 var data = databaseSource.data();
					 for(var i=0;i<data.length ;i++){
						 if(data[i].id == dataItem.databaseId){
							 ret = data[i].text ;
						 }
					 }
				})
				return ret ;
			}
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 100
		}, {
			command : [ {
				name : "删除",
				click : function(e) {
					var tr = $(e.target).closest("tr");
					var data = this.dataItem(tr);
					for(var i=0;i<selectDatasource.length;i++){
						var value = selectDatasource[i];
						if (value.datasetId == data.datasetId) {
							selectDatasource.splice(i, 1);
						}
					}
					rushSelectDatasourceGrid();
				}
			},{
				name : "查看",
				click : function(e) {
					var tr = $(e.target).closest("tr");
					var data = this.dataItem(tr);
					if(data.dynamicDataSet == "Y"){
						window.open("/glaf/mx/form/dataset/dynamicDatabase?id="+data.dataSetId);
					}else{
						window.open("/glaf/mx/dataset/sqlbuilder?id="+data.dataSetId);
					}
				}
			} ]
		} ]
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
 <style type="text/css">
 	.mt-grid{
 		overflow-y: scroll;
 		height: 280px !important;
 	}
 </style>
</head>
<body>
	<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}">
	<div class="x_content_title">
		
	</div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" style="width: 180px;">
			<div class="easyui-layout" data-options="fit:true">

				<div data-options="region:'center',border:false">
					<ul id="myTree" class="ztree"></ul>
				</div>

			</div>
		</div>
		<div data-options="region:'center'">
			<!-- <div class="easyui-layout" data-options="fit:true">  
	  <div data-options="region:'center',border:false">
		 <div id="grid"></div>
	  </div>  
	</div> -->
			<div id="tt" class="easyui-tabs" style="" data-options="region:'south'">
				<div title="视图1" style="padding: 10px;">
					<div id="grid" class="mt-grid"></div>
				</div>
				<div title="视图2" data-options="closable:false"
					style="overflow: auto; padding: 10px;">
					<%@ include file="/WEB-INF/views/datamgr/dataset/treelist.jsp"%>
				</div>
			</div>

			<div data-options="region:'south',split:true,fit:true" class="easyui-tabs">
				<div title="已选数据集" style="padding: 10px;">
					<div id="selectDatasourceGrid"></div>
				</div>
			</div>
		</div>
		
	</div>
</body>

</html>