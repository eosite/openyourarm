<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/x-kendo-template" id="template">
<table>
	<tr>
		<td>
			<input type="text" class="k-textbox" id="keyword" name="keyword" />
			<button id="search_table_btn" class="k-button" onclick="search_table_click()">检索</button>
			<button id="modify_table_btn" class="k-button" onclick="modify_table_click()">修改表</button>
			<button id="delete_table_btn" class="k-button" onclick="delete_table_click()">删除表</button>
			<button id="select_table_btn" class="k-button" onclick="select_table_click()">选择</button>
			<button id="close_table_btn" class="k-button" onclick="parent.layer.close(parent.layer.getFrameIndex())">关闭</button>
		</td>
	</tr>
</table>
</script>
<script type="text/x-kendo-template" id="template2">
<table>
	<tr>
		<td>
			<button id="selected_table_remove_btn" class="k-button" onclick="selected_table_remove()">移除</button>
			<button id="selected_table_confirm_btn" class="k-button" onclick="selected_table_confirm()">确定</button>
		</td>
	</tr>
</table>
</script>
<script type="text/javascript">
	var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;
	var utableTreeUrl = "${pageContext.request.contextPath}/rs/isdp/cellUTableTree/getUtableTreeByTableType?systemName=${databaseCode}",
		cellUrl = "${pageContext.request.contextPath}/rs/isdp/treeDot/getTreeDotByParentId?&systemName=${databaseCode}";
	
	var setting = {
			view: {
				showIcon:false,
				showLine:true,
				showTitle:false,
				selectedMulti: false
			},
			async:{
				enable:true,
				url:utableTreeUrl,
				autoParam:["indexId","nlevel=level"]
			},
			data:{
				simpleData:{
					enable:true,
					pIdKey:"parentId"
				},
				key:{
					name:"indexName"
				}
			},
			callback: {
				onClick: zTreeOnClick
			}
	};

	var layerIndex = -1;
	$(function(){
		$("#modify_table_content").hide();
		$("#create_table_content").hide();
		//修改表确认事件
		$("#modify_table_submit_btn").on("click",function(e){
			var validate = $("#modify_table_content").kendoValidator().data("kendoValidator");
			if(validate.validate()){
				var grid = $("#select_table_grid").data("kendoGrid");
				$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath}/rs/isdp/cellDataTable/saveCellDataTable",
					dataType:  'json',
					data: {id:$("#modify_table_id").val(),name:$("#modify_table_name").val(),systemName:"${databaseCode}"},
					error: function(data){
						alert('服务器处理错误！');
					},
					success: function(data){
						if(data != null && data.message != null){
							layer.alert(data.message,1);
						} else {
							layer.alert('操作成功完成！',1);
						}
						grid.dataSource.read();
						layer.close(layerIndex);
					}
				});
			}
		});
		
		//创建表确认事件
		$("#create_table_submit_btn").on("click",function(e){
			var validate = $("#create_table_content").kendoValidator().data("kendoValidator");
			if(validate.validate()){
				var grid = $("#select_table_grid").data("kendoGrid");
				$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath}/rs/isdp/table/createTable",
					dataType: 'json',
					data: {name:$("#create_table_name").val(),systemName:"${databaseCode}"},
					error: function(data){
						alert('服务器处理错误！');
					},
					success: function(data){
						if(data != null && data.message != null){
							layer.alert(data.message,1);
						} else {
							layer.alert('操作成功完成！',1);
						}
						grid.dataSource.read();
						layer.close(layerIndex);
					}
				});
			}
		});
		
		var viewModel = kendo.observable({
			tableTypeSource:new kendo.data.DataSource({//表类型
				type:"json",
				transport:{
					read:{
						contentType:"application/json",
						url:"${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson",
						type: "POST",
                        dataType:"JSON"
					},
					parameterMap : function(options, operation) {
	                    if (operation == "read") {
                    		options.nodeCode = "tableType";
	                    	return JSON.stringify(options);
	                    }
	                 }
				}
			}),
			changeTableType:function(e){
				var tableType = $("input[name='tableType']").val();
	        	if(tableType==1){
	        		setting.async.url = utableTreeUrl+"&tableType=2";
		        	$.fn.zTree.init($("#zTree"), setting); 
	        	}else if(tableType==2)	{
	        		setting.async.url = cellUrl;
		        	$.fn.zTree.init($("#zTree"), setting); 
	        	}else if(tableType==3){
	        		setting.async.url = utableTreeUrl+"&tableType=12";
		        	$.fn.zTree.init($("#zTree"), setting); 
	        	}else if(tableType==4){
	        		$.fn.zTree.init($("#zTree"), setting,[{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|'}]); 
	        	}else if(tableType==5){
	        		$.fn.zTree.init($("#zTree"), setting,[{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}]); 
	        	}
	        	
	        	var link = "${pageContext.request.contextPath}/rs/isdp/cellDataTable/getTableListByIndexId";
	    		var dataSource = createGridDataSource(link,-99);
	    		var grid = $("#select_table_grid").data("kendoGrid");
	    		grid.setDataSource(dataSource);
			},
			createTableTypeSource:[
				{name:"普通表",value:"1"}
			],
			closeLayer:function(e){
				layer.close(layerIndex);
			}
		});
		kendo.bind($(document.body), viewModel);
		$("#tableTypeDropDownList").data("kendoDropDownList").value("1");
		
		//创建表右键菜单
		$("#createTableMenu").kendoContextMenu({
			target: "#left-panel",
		});
	
		$("#main_content").height(height-20);
		$("#main_content").kendoSplitter({
	        orientation: "horizontal",
	        panes: [
				{ 
					collapsible: true,
					size:"150px"
				},{ 
					collapsible: false
				}
	        ]
	    });
		$.fn.zTree.init($("#zTree"), setting);
		
		$("#select_table_grid").kendoGrid({
            height: (height-25)*3/5,
            toolbar:kendo.template($("#template").html()), 
			editable: false,
            groupable: false,
            sortable: true,
            resizable:true,
            reorderable:true,
            selectable:"row",
            columns: [
            	{
            		field:"id",
            		title:"id",
            		hidden:true
            	},
            	{
            		field:"tableName",
            		title:"表名",
            		width:"150px"
            	},
            	{
            		field:"name",
            		title:"名称"
            	},
            	{
            		field:"ctime",
            		title:"创建时间",
            		width:"120px"
            	}
            ],
            pageable:{
            	refresh:true,
            	pageSizes:[20,50,100],
            	buttonCount:5
            }
        });
		
		$("#search_table_btn").kendoButton({
			icon:"k-icon k-i-search"
		});
		$("#modify_table_btn").kendoButton({
			icon:"k-icon k-i-pencil"
		});
		$("#delete_table_btn").kendoButton({
			icon:"k-icon k-i-cancel"
		});
		$("#select_table_btn").kendoButton({
			icon:"k-icon k-i-tick"
		});
		$("#close_table_btn").kendoButton({
			icon:"k-icon k-i-close"
		});
		
		$("#selected_table_grid").kendoGrid({
            height: (height-25)*2/5,
            toolbar:kendo.template($("#template2").html()), 
			editable: false,
            groupable: false,
            sortable: true,
            resizable:true,
            reorderable:true,
            selectable:"row",
            columns: [
            	{
            		field:"id",
            		title:"id",
            		hidden:true
            	},
            	{
            		field:"tableName",
            		title:"表名",
            		width:"150px"
            	},
            	{
            		field:"name",
            		title:"名称"
            	}
            ]
        });
		
		$("#selected_table_remove_btn").kendoButton({
			icon:"k-icon k-i-cancel"
		});
		$("#selected_table_confirm_btn").kendoButton({
			icon:"k-icon k-i-tick"
		});
		
		var selectedGrid = $("#selected_table_grid").data("kendoGrid");
		if(parent.tableJsonArray != ''){
			//var tableJson = JSON.parse('${tableJson}');
			var tableJson = JSON.parse(parent.tableJsonArray);
			parent.tableJsonArray = [];
			for(var i=0;i<tableJson.length;i++){
				var temp = {};
				temp.id=tableJson[i].TableId;
				temp.tableName=tableJson[i].TableName;
				temp.name=tableJson[i].name;
				temp.tableType=tableJson[i].tableType;
				selectedGrid.dataSource.add(temp);
			}
		}
		
	});
	
	function zTreeOnClick(event, treeId, treeNode,clickFlag){
		
		var tableTypeDropDownList = $("#tableTypeDropDownList").data("kendoDropDownList");
		var tableType = tableTypeDropDownList.value();
		if(tableType==""){
			layer.alert("请先选择数据表类型","3");
			return;
		}
		
		var link = "${pageContext.request.contextPath}/rs/isdp/cellDataTable/getTableListByIndexId";
		
		var dataSource = createGridDataSource(link,treeNode.indexId);
		
		var grid = $("#select_table_grid").data("kendoGrid");
		grid.setDataSource(dataSource);
	}
	
	function createGridDataSource(link,indexId){
		var dataSource = new kendo.data.DataSource({
			type: "json",
            transport: {
            	read:{
            		contentType: "application/json",
                    url: link,
                    type: "POST",
                    dataType:"JSON"
            	},
                parameterMap : function(options, operation) {
                	  var tableType = $("input[name='tableType']").val();
                      if (operation == "read") {
                    	  options.keyword = $("#keyword").val();
                    	  options.allChild = true;
                    	  options.indexId = indexId;
                    	  options.type = tableType;
                    	  options.systemName = "${databaseCode}";
                          return JSON.stringify(options);
                      }
                 }
            },
            schema:{
            	total:"total",
            	data:"rows",
            	model:{
            		fields:{
            			id:{type:"string"},
            			tableId:{type:"string"},
            			tableType:{type:"string"},
            			tableName:{type:"string"},
            			name:{type:"string"},
            			ctime:{type:"datetime"}
            		}
            	},
            },
            pageSize: 20,
           	serverPaging: true,
           	serverSorting: true
		});
		
		return dataSource;
	}
	//选择
	function select_table_click(){
		var grid = $("#select_table_grid").data("kendoGrid");
		var row = grid.select();
		var data = grid.dataItem(row);
		
		var tableType = $("input[name='tableType']").val();
		data.tableType = tableType;
		
		var selectedGrid = $("#selected_table_grid").data("kendoGrid");
		var datas = selectedGrid.dataSource.data();
		//检查选中的表是否已经在已选择列表中，如果在已选择列表中，则不添加
		for(var i=0;i<datas.length;i++){
			if(data.id==datas[i].id && data.tableName==datas[i].tableName){
				layer.alert("已添加表["+data.name+"]，请勿重复添加",3);
				return;
			}
		}
		
		selectedGrid.dataSource.add(data);
	}
	
	function openEditTableLayer(domid,title,width,height){
		layerIndex = $.layer({
			type: 1,
			maxmin: true,
			shadeClose: true,
			title: title,
			closeBtn: [0, true],
			shade: [0, '#000'],
			border: [2, 0.3, '#000'],
			fadeIn: 100,
			area: [width, height],
			page:{
				dom:"#"+domid
			}
		});
	}
	
	//修改
	function modify_table_click(){
		var grid = $("#select_table_grid").data("kendoGrid");
		var row = grid.select();
		
		if(row.length==0){
			layer.alert("请先选择表再进行修改",3);
			return;
		}
		
		var data = grid.dataItem(row);
		$("#modify_table_name").val(data.name);
		$("#modify_table_id").val(data.id);
		
		openEditTableLayer("modify_table_content","修改表","300px","120px");
	}
	//删除 
	function delete_table_click(){
		var grid = $("#select_table_grid").data("kendoGrid");
		var row = grid.select();
		if(row.length==0){
			layer.alert("请先选择表再进行删除",3);
			return;
		}
		
		var dropdownlist = $("#tableTypeDropDownList").data("kendoDropDownList");
		if(dropdownlist.value()=="2"){
			layer.alert("不能对CELL表进行删除",3);
			return;
		}
		
		var data = grid.dataItem(row);
		layer.confirm("确认删除表["+data.name+"]吗？删除后将从选择表中移除！",function(){
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/rs/isdp/table/deleteTable",
				dataType:  'json',
				data: {id:data.id,systemName:"${databaseCode}"},
				error: function(data){
				 alert('服务器处理错误！');
				},
				success: function(_data){
				 if(_data != null && _data.statusCode == 200){
					 layer.alert('操作成功！',1);
					 grid.dataSource.read();
					 
					 var selectedGrid = $("#selected_table_grid").data("kendoGrid");
					 var datas = selectedGrid.dataSource.data();
					 for(var i=0;i<datas.length;i++){
						if(data.id==datas[i].id && data.tableName==datas[i].tableName){
							selectedGrid.dataSource.remove(datas[i]);
							return;
						}
					}
				 } else {
					 layer.alert('操作失败！',3);
				 }
				 
				}
			});
			
		});
	}
	//检索
	function search_table_click(){
		var grid = $("#select_table_grid").data("kendoGrid");
		var datasource = grid.dataSource;
		datasource.page(1);
	}
	
	//移除
	function selected_table_remove(){
		var selectedGrid = $("#selected_table_grid").data("kendoGrid");
		var row = selectedGrid.select();
		var data = selectedGrid.dataItem(row);
		selectedGrid.dataSource.remove(data);
	}
	//确定
	function selected_table_confirm(){
		var selectedGrid = $("#selected_table_grid").data("kendoGrid");
		var dataItems = selectedGrid.dataSource.data();
		var tableId = [],name = [],tableJson = [];
		for(var i=0;i<dataItems.length;i++){
			tableId.push(dataItems[i].id);
			name.push(dataItems[i].name);
			
			var temp = {};
			temp.TableId = dataItems[i].id;
			temp.tableType = dataItems[i].tableType;
			temp.TableName = dataItems[i].tableName;
			temp.name = dataItems[i].name;
			tableJson.push(temp);
		}
		
		if("${tableIdElementId}" != ""){
			parent.document.getElementById("${tableIdElementId}").value = tableId.join(",");
		}
		
		if("${tableNameElementId}" != ""){
			parent.document.getElementById("${tableNameElementId}").value = name.join(",");
		}
		if("${tableObjElementId}" != ""){
			parent.document.getElementById("${tableObjElementId}").value = JSON.stringify(tableJson);
		}
		parent.layer.close(parent.layer.getFrameIndex());
	}
	
</script>
</head>
<body>
	<div id="main_content" style="height:400px">
	 	 <div id="left-panel">
	 	 	<input id="tableTypeDropDownList" name="tableType" style="width:100%"
	 	 		data-role="dropdownlist" 
				data-text-field="name"
                data-value-field="value"
	 	 		data-bind="
	 	 			source:tableTypeSource,
	 	 			events:{
	 	 				change:changeTableType
	 	 			}
	 	 			" />
	 	 	<ul id="zTree" class="ztree"></ul>
	 	 </div>
		 <div id="center-panel">
	 		<div id="select_table_grid"></div>
	 		<div id="selected_table_grid"></div>
		 </div>
	 </div>
	 <div id="modify_table_content">
	 	<input type="hidden" id="modify_table_id" />
	 	<table cellspacing="5" border="0" style="width: 300px">
	 		<tr>
	 			<td align="right" width="40px">表名</td>
	 			<td>
	 				<input type="text" id="modify_table_name" name="modify_table_name" class="k-textbox" required validationMessage="必填项" />
	 				<span class="k-invalid-msg" data-for="modify_table_name"></span>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2" align="center">
	 				<button id="modify_table_submit_btn" data-role="button" data-sprite-css-class="k-icon k-i-tick" class="k-button">确定</button>
	 				<button data-role="button" data-sprite-css-class="k-icon k-i-cancel" data-bind="events:{click:closeLayer}">取消</button>
	 			</td>
	 		</tr>
	 	</table>
	 </div>
	 <div id="create_table_content">
	 	<table cellspacing="5" border="0" style="width:320px">
	 		<tr>
	 			<td align="right" width="60px">表名</td>
	 			<td>
	 				<input type="text" id="create_table_name" name="create_table_name" class="k-textbox" required validationMessage="必填项" />
	 				<span class="k-invalid-msg" data-for="create_table_name"></span>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="right">创建类型</td>
	 			<td><input id="create_table_type" name="create_table_type"
	 					data-role="dropdownlist"
	 					data-text-field="name"
	 					data-value-field="value"
	 					data-bind="enabled:false,source:createTableTypeSource" />
	 			
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2" align="center">
	 				<button id="create_table_submit_btn" data-role="button" data-sprite-css-class="k-icon k-i-tick" class="k-button">确定</button>
	 				<button data-role="button" data-sprite-css-class="k-icon k-i-cancel" data-bind="events:{click:closeLayer}">取消</button>
	 			</td>
	 		</tr>
	 	</table>
	 </div>
	 <ul id="createTableMenu">
	 	<li onclick="openEditTableLayer('create_table_content','创建表','320px','150px');">创建表</li>
	 </ul>
</body>
</html>