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
<%@ include file="/WEB-INF/views/isdp/table/select_js.jsp"%>
<script type="text/x-kendo-template" id="template">
<table>
	<tr>
		<td>
			<button id="confirm_field_btn" class="k-button" onclick="confirm_field_click()">确定</button>
			<button id="add_field_btn" class="k-button" onclick="add_field_click()">新增字段</button>
			<button id="modify_field_btn" class="k-button" onclick="modify_field_click()">修改字段</button>
			<button id="delete_field_btn" class="k-button" onclick="delete_field_click()">删除字段</button>
			<button id="close_field_btn" class="k-button" onclick="parent.layer.close(parent.layer.getFrameIndex())">关闭</button>
		</td>
	</tr>
</table>
</script>
<script type="text/javascript">
	var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;
	var layerIndex = -1;
	$(function(){
		$("#field_content").hide();
		
		var tableJson = [];
		if(parent.tableJson != ''){
			tableJson = JSON.parse(parent.tableJsonArray);
		}
		var fieldJson = [];//传入的字段
		if(parent.fieldJsonArray!=''){
			fieldJson = JSON.parse(parent.fieldJsonArray);
		}
		
		//新增、编辑字段时事件
		$("#field_submit_btn").on("click",function(e){
			var validate = $("#field_content").kendoValidator().data("kendoValidator");
			if(validate.validate()){
				var grid = $("#select_field_grid").data("kendoGrid");
				
				var dropdownlist = $("#tableDropDownList").data("kendoDropDownList");
				var tableid = dropdownlist.value();
				
				var link = "${pageContext.request.contextPath}/rs/isdp/table/addColumn";
				var params = {};
				params.tableid=tableid;
				params.systemName="${databaseCode}";
				params.name = $("#field_name").val();
				params.fieldLen = $("#field_len").data("kendoNumericTextBox").value();
				params.fieldType = $("#fieldTypeDropDownList").data("kendoDropDownList").value();
				
				if($("#modify_field_id").val() != ""){
					link = "${pageContext.request.contextPath}/rs/isdp/table/modifyColumn";
					params.fieldId = $("#modify_field_id").val();
				}
				if($("#modify_list_id").val() != ""){
					params.listId = $("#modify_list_id").val();
				}
				if($("#modify_table_name").val() != ""){
					params.tablename = $("#modify_table_name").val();
				}
				if($("#referenced_table_obj").val() != ""){
					params.referenced_table_obj = $("#referenced_table_obj").val();
					
					if($("#referenced_field_obj").val() == ""){
						layer.alert("请选择引用字段",3);
						return;
					}
				}
				if($("#referenced_field_obj").val() != ""){
					params.referenced_field_obj = $("#referenced_field_obj").val();
				}
				
				$.ajax({
					type: "POST",
					url: link,
					dataType: 'json',
					data: params,
					error: function(data){
						alert('服务器处理错误！');
					},
					success: function(_data){
						if(_data != null && _data.statusCode == 200){
							layer.alert('操作成功！',1);
							grid.dataSource.read({
								tableId:tableid,
			        			type:$("#table_type").val()
							});
							layer.close(layerIndex);
						}else{
							layer.alert('操作失败！',3);
						}
					}
				});
			}
		});
		//end
		
		//数据绑定
		var viewModel = kendo.observable({
			tableDropDownDataSource : tableJson,//选择表数据源绑定
			tableChange:function(e){//选择表事件
				var selectTable = this.get("tableSelect");
				$("#table_type").val(selectTable.tableType);
				
				var isEnable = false;
				if(selectTable.tableType == 1){
					isEnable = true;
				}
				setFieldBtnEnable(isEnable);
				
				var grid = $("#select_field_grid").data("kendoGrid");
        		var datasource = grid.dataSource;
        		datasource.read({
        			tableId:selectTable.TableId,
        			type:selectTable.tableType,
        			tableName:selectTable.TableName
        		});
			},
			fieldTypeChange:function(e){//选择数据类型事件
				var dropdownlist = this.get("fieldTypeSelect");
				fileTypeChangeSetValue(dropdownlist.value);
			},
			closeLayer:function(e){//关闭弹出窗
				layer.close(layerIndex);
			},
			fieldTypeDropDownDataSource:new kendo.data.DataSource({//新增字段类型
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
                    		options.nodeCode = "dType";
	                    	return JSON.stringify(options);
	                    }
	                 }
				}
			}),
			srcGridDataSource : new kendo.data.DataSource({//表中数据字段数据源
	   			type: "json",
	            transport: {
	               	read:{
	               		contentType: "application/json",
                        url: "${pageContext.request.contextPath}/rs/isdp/cellDataField/selectFieldByTableId",
                        type: "POST",
                        dataType:"JSON"
	               	},
	                parameterMap : function(options, operation) {
	               	  	if(operation=="read"){
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
	               			tableid:{type:"string"},
	               			tablename:{type:"string"},
	               			tableNameCN:{type:"string"},
	               			FieldId:{type:"string"},
	               			listid:{type:"string"},
	               			dname:{type:"string"},
	               			fname:{type:"string"},
	               			dtype:{type:"string"},
	               			strlen:{type:"string"},
	               			referencedTableName:{type:"string"},
	               			referencedFieldName:{type:"string"}
	               		}
	               	},
	            }
	   		}),
	   		destGridDataSource : new kendo.data.DataSource({//已选择数据字段数据源
            	schema:{
                   	model:{
                   		fields:{
                   			tableid:{type:"string"},
                   			tablename:{type:"string"},
                   			tableNameCN:{type:"string"},
                   			FieldId:{type:"string"},
                   			listid:{type:"string"},
                   			dname:{type:"string"},
                   			fname:{type:"string",editable:false},
                   			dtype:{type:"string"},
                   			strlen:{type:"string"},
                   			referencedTableName:{type:"string"},//引用表
                   			referencedFieldName:{type:"string"},//引用字段
                   			
                   			isEditor:{type:"boolean"},//是否可编辑
                   			isShowList:{type:"boolean"},//是否显示
                   			isShowTooltip:{type:"boolean"},//鼠标悬浮提示
                   			alignment:{type:"string"},
                   			columnWidth:{type:"numeric"},//列宽
                   			defaultValue:{type:"string"},//默认值 
                   			isRequired:{type:"boolean"},//是否必填项
                   			formatValue:{type:"string"},//显示格式
                   			editor:{type:"string"},//编辑控件类型
                   			widgetSourceType:{type:"string"},//控件数据类型
                   			widgetSource:{type:"string"}//控件数据源
                   		}
                   	},
                }
            })
		});
		kendo.bind($(document.body), viewModel);
		//end
		
		//工具栏初始化
		initToolBar();
		//end
		
		//初始化布局
		$("#splitter").height(height-80);
		$("#splitter").kendoSplitter({
			orientation: "horizontal",
	        panes: [
				{ 
					collapsible: false,
					size:'230px'
				},{
					collapsible: false,
					size:'90px'
				},{ 
					collapsible: false,
					size:'230px'
				}
	        ]
		});
		//end
		
		//设置Grid中的字段为第一个表中的字段
		var firstTableType = tableJson[0].tableType,firstTableId=tableJson[0].TableId,firstTableName=tableJson[0].TableName;
		
		var grid = $("#select_field_grid").data("kendoGrid");
		grid.dataSource.read({
			tableId:firstTableId,
			type:firstTableType,
			tableName:firstTableName
		});
		//设置按钮是否可用
		var isEnable = false;
		if(firstTableType == 1){
			isEnable = true;
		}
		setFieldBtnEnable(isEnable);
		//end
		
		//设置初始数据表id及类型，是Cell表还是普通表
		var dropdownlist = $("#tableDropDownList").data("kendoDropDownList");
		dropdownlist.value(firstTableId);
		$("#table_type").val(firstTableType);
		
		//设置已选择字段列表中的字段为request传入的字段
		initSelectedGridData(fieldJson);
		//end
	});
	//页面初始化结束
	
	//工具栏初始化
	function initToolBar(){
		$("#toolbar").kendoToolBar({
	        resizable: false,
	        items: [
	            { template:kendo.template($("#template").html()) }
	        ]
	    });
		
		$("#confirm_field_btn").kendoButton({
			icon:"k-icon k-i-tick"
		});
		$("#add_field_btn").kendoButton({
			icon:"k-icon k-i-plus"
		});
		$("#modify_field_btn").kendoButton({
			icon:"k-icon k-i-pencil"
		});
		$("#delete_field_btn").kendoButton({
			icon:"k-icon k-i-cancel"
		});
		$("#close_field_btn").kendoButton({
			icon:"k-icon k-i-close"
		});
	}

	//初始化已选择字段列表
	function initSelectedGridData(fieldJson){
		var selectedGrid = $("#selected_field_grid").data("kendoGrid");
		for(var i=0;i<fieldJson.length;i++){
			var temp = {};
			temp.tablename=fieldJson[i].FieldTable;
			temp.tableNameCN=fieldJson[i].tableNameCN;
			
			temp.strlen=fieldJson[i].FieldLength;
			temp.dtype=fieldJson[i].FieldType;
			temp.fname=fieldJson[i].name;
			temp.dname=fieldJson[i].ColumnName;
			temp.FieldId=fieldJson[i].FieldId;
			
			temp.isShowList=fieldJson[i].isShowList?fieldJson[i].isShowList:true;
			temp.isShowTooltip=fieldJson[i].isShowTooltip?fieldJson[i].isShowTooltip:false;
			temp.isEditor=fieldJson[i].isEditor?fieldJson[i].isEditor:false;
			temp.columnWidth=fieldJson[i].columnWidth?fieldJson[i].columnWidth:"200";
			temp.alignment=fieldJson[i].alignment?fieldJson[i].alignment:"left";
			temp.defaultValue=fieldJson[i].defaultValue;
			temp.isRequired=fieldJson[i].isRequired;
			temp.formatValue=fieldJson[i].formatValue;
			temp.editor=fieldJson[i].editor?fieldJson[i].editor:"textfield";
			temp.widgetSourceType = fieldJson[i].widgetSourceType;
			if(fieldJson[i].widgetSourceType==10){
				temp.isVisible=true;
				temp.isNotVisible=false;
			}else if(fieldJson[i].widgetSourceType==20){
				
			}else if(fieldJson[i].widgetSourceType==30){
				temp.isVisible=false;
				temp.isNotVisible=true;
			}else{
				temp.isVisible=true;
				temp.isNotVisible=false;
			}
			temp.widgetSource = fieldJson[i].widgetSource;
			selectedGrid.dataSource.add(temp);
		}
	}
	
	function setWidgetSourceSource(){
		var widgetDataSource = new kendo.data.DataSource({
			type:"json",
			transport:{
				read:{
					contentType:"application/json",
					url:"${pageContext.request.contextPath}/rs/isdp/global/treeDataJson",
					type:"POST",
					dataType:"JSON"
				},
				parameterMap : function(options, operation) {
                	if (operation == "read") {
                		options.nodeCode = "widgetSource";
                		options.systemName = "${databaseCode}";
                		return JSON.stringify(options);
                	}
             	}
			}
		});
		var widgetDropdownlist = $("#widgetSourceDropDownList").data("kendoDropDownList");
		widgetDropdownlist.setDataSource(widgetDataSource);
	}
	
	//设置新增、修改、删除字段按钮是否可用
	function setFieldBtnEnable(isEnable){
		$("#add_field_btn").data("kendoButton").enable(isEnable);
		$("#modify_field_btn").data("kendoButton").enable(isEnable);
		$("#delete_field_btn").data("kendoButton").enable(isEnable);
	}
	
	//字段类型设置
	function fileTypeChangeSetValue(type){
		var lenValue = 100;
		var isEnable = false;
		if(type=="i4"){
			lenValue = 4;
		}else if(type=="r8"){
			lenValue = 8;
		}else if(type=="datetime"){
			lenValue = 8;
		}else if(type=="image" || type=="char"){
			lenValue = 1;
		}else{
			lenValue = 100;
			isEnable = true;
		}
		var numerictextbox = $("#field_len").data("kendoNumericTextBox");
		numerictextbox.value(lenValue);
		numerictextbox.enable(isEnable);
	}
	
	//确定
	function confirm_field_click(){
		var selectedGrid = $("#selected_field_grid").data("kendoGrid");
		var dataItems = selectedGrid.dataSource.data();
		
		var fieldId = [],name = [],fieldJson = [];
		for(var i=0;i<dataItems.length;i++){
			fieldId.push(dataItems[i].FieldId);
			name.push(dataItems[i].fname);
			
			var temp = {};
			temp.FieldTable = dataItems[i].tablename;
			temp.tableNameCN = dataItems[i].tableNameCN;
			temp.FieldLength = dataItems[i].strlen;
			temp.FieldType = dataItems[i].dtype;
			temp.name = dataItems[i].fname;
			temp.ColumnName = dataItems[i].dname;
			temp.FieldId = dataItems[i].FieldId;
			
			temp.isShowList = dataItems[i].isShowList?dataItems[i].isShowList:true;
			temp.isShowTooltip=dataItems[i].isShowTooltip?dataItems[i].isShowTooltip:false;
			temp.isEditor = dataItems[i].isEditor?dataItems[i].isEditor:false;
			temp.columnWidth = dataItems[i].columnWidth?dataItems[i].columnWidth:"200";
			temp.alignment = dataItems[i].alignment?dataItems[i].alignment:"left";
			temp.defaultValue=dataItems[i].defaultValue;
			temp.isRequired=dataItems[i].isRequired;
			temp.formatValue=dataItems[i].formatValue;
			temp.editor=dataItems[i].editor?dataItems[i].editor:"textfield";
			temp.widgetSourceType=dataItems[i].widgetSourceType;
			
			try{
				var t = JSON.parse(dataItems[i].widgetSource);
				temp.widgetSource = [];
				for(var j=0;j<t.length;j++){
					temp.widgetSource.push(t[j]);
				}
			}catch(e){
				temp.widgetSource=dataItems[i].widgetSource;
			}
			
			fieldJson.push(temp);
		}
		
		if("${fieldIdElementId}" != ""){
			parent.document.getElementById("${fieldIdElementId}").value = fieldId.join(",");
		}
		if("${fieldNameElementId}" != ""){
			parent.document.getElementById("${fieldNameElementId}").value = name.join(",");
		}
		if("${fieldObjElementId}" != ""){
			parent.document.getElementById("${fieldObjElementId}").value = JSON.stringify(fieldJson);
		}
		
		parent.layer.close(parent.layer.getFrameIndex());
	}
	
	//新增、修改字段弹窗
	function openEditFieldLayer(domid,title,width,height){
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
	
	//新增字段
	function add_field_click(){
		//设置文本框初始值
		$("#field_name").val("");
		
		var dropdownlist = $("#fieldTypeDropDownList").data("kendoDropDownList");
		dropdownlist.value("string");
		
		var numeric = $("#field_len").data("kendoNumericTextBox");
		numeric.value(100);
		numeric.enable(true);
		
		$("#referenced_table_name").val("");
		$("#referenced_field_name").val("");
		
		var inputs = $("#field_content").children("input");
		for(var i=0;i<inputs.length;i++){
			inputs[i].value=""
		}
		
		openEditFieldLayer('field_content','新增字段','350px','300px')
	}
	
	//修改字段
	function modify_field_click(){
		var grid = $("#select_field_grid").data("kendoGrid");
		var row = grid.select();
		
		if(row.length==0){
			layer.alert("请先选择一个字段再进行修改",3);
			return;
		}
		var data = grid.dataItem(row);
		
		var dropdownlist = $("#fieldTypeDropDownList").data("kendoDropDownList");
		dropdownlist.value(data.dtype);
		fileTypeChangeSetValue(data.dtype);
		
		$("#field_name").val(data.fname);
		$("#field_len").data("kendoNumericTextBox").value(data.strlen);
		
		$("#modify_field_id").val(data.FieldId);
		$("#modify_list_id").val(data.listid);
		$("#modify_table_name").val(data.tablename);
		
		//清除引用表及引用字段信息
		$("#referenced_table_obj").val("");
		$("#referenced_field_obj").val("");
		$("#referenced_table_name").val("");
		$("#referenced_field_name").val("");
		
		//通过ajax查询引用表及引用字段
		if(data.referencedTableName!="" && data.referencedTableName!=undefined){
			var params = {};
			params.systemName="${databaseCode}";
			params.tablename=data.referencedTableName;
			params.fieldname=data.referencedFieldName;
		
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/rs/isdp/table/selectReferenced",
				dataType:'json',
				data: params,
				error: function(_data){
				 alert('服务器处理错误！');
				},
				success: function(_data){

					var fieldArray = _data.fieldArray;
					var tableArray = _data.tableArray;
					
					$("#referenced_table_obj").val(JSON.stringify(tableArray));
					$("#referenced_field_obj").val(JSON.stringify(fieldArray));
					
					$("#referenced_table_name").val(tableArray[0].name);
					$("#referenced_field_name").val(fieldArray[0].name);
					
					openEditFieldLayer('field_content','修改字段','350px','300px');
				}
			});
		}else{
			openEditFieldLayer('field_content','修改字段','350px','300px');
		}
	}
	
	//删除字段
	function delete_field_click(){
		var grid = $("#select_field_grid").data("kendoGrid");
		var row = grid.select();
		if(row.length==0){
			layer.alert("请先选择字段再进行删除",3);
			return;
		}
		
		var data = grid.dataItem(row);
		layer.confirm("删除字段后右侧已选字段也将删除，<br/>确认删除字段["+data.fname+"]吗？",function(){
			
			var params = {};
			params.tablename = data.tablename;
			params.FieldId = data.FieldId;
			params.listId = data.listid;
			params.dname = data.dname;
			params.systemName="${databaseCode}";
			
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/rs/isdp/table/deleteColumn",
				dataType:'json',
				data: params,
				error: function(data){
				 alert('服务器处理错误！');
				},
				success: function(_data){
					if(_data != null && _data.statusCode == 200){
						layer.alert('删除字段['+data.fname+']成功！',1);
						
						 //删除字段后，从右侧的选择字段中删除
						 var selectedGrid = $("#selected_field_grid").data("kendoGrid");
						 var datas = selectedGrid.dataSource.data();
						 for(var i=0;i<datas.length;i++){
							if(datas[i].listId==data.listId && datas[i].dname==data.dname){
								selectedGrid.dataSource.remove(datas[i]);
								break;
							}
						}
						 
						 var dropdownlist = $("#tableDropDownList").data("kendoDropDownList");
						 grid.dataSource.read({
							 tableId:dropdownlist.value(),
		        			 type:$("#table_type").val()
						 });
					}else{
						layer.alert('删除失败！',3);
					}
				}
			});
		});
	}
	
	function move_btn_click(movetype){
		var selectGrid = $("#select_field_grid").data("kendoGrid");
		var selectedGrid = $("#selected_field_grid").data("kendoGrid");
		
		switch(movetype){
			case 1://移进
				var rows = selectGrid.select();
				for(var i=0;i<rows.length;i++){
					var data = selectGrid.dataItem(rows[i]);
					
					// 判断是否重复添加
					var datas = selectedGrid.dataSource.data();
					for(var i=0;i<datas.length;i++){
						if(datas[i].listId==data.listId && datas[i].dname==data.dname){
							layer.alert("已添加字段["+data.fname+"]，请勿重复添加",3);
							return;
						}
					}
					
					if(data.dtype=="varchar"){
						data.dtype="string";
					}else if(data.dtype=="int"){
						data.dtype="i4";
					}else if(data.dtype=="float"){
						data.dtype="r8";
					}else if(data.dtype=="datetime2"){
						data.dtype="datatime";
					}else if(data.dtype=="varbinary"){
						data.dtype="image";
					}
					
					selectedGrid.dataSource.add(data);
				}
				break;
			case 2://移出
				var rows = selectedGrid.select();
				for(var i=0;i<rows.length;i++){
					selectedGrid.removeRow(rows[i]);
				}
				break;
			case 3://上移
				var rows = selectedGrid.select();
				var dataSource = selectedGrid.dataSource;
				for(var i=0;i<rows.length;i++){
					var data = selectedGrid.dataItem(rows[i]);
					var index = dataSource.indexOf(data);
					if(index==0){
						break;
					}else{
						selectedGrid.removeRow(rows[i]);
						dataSource.insert(index-1,data);
						var row = selectedGrid.tbody.find(">tr:not(.k-grouping-row)").eq(index-1);
						selectedGrid.select(row);
					}
				}
				
				break;
			case 4://下移
				var rows = selectedGrid.select();
				var dataSource = selectedGrid.dataSource;
				for(var i=rows.length-1;i>=0;i--){
					var data = selectedGrid.dataItem(rows[i]);
					var index = dataSource.indexOf(data);
					if(index==dataSource.total()-1){
						break;
					}else{
						selectedGrid.removeRow(rows[i]);
						dataSource.insert(index+1,data);
						var row = selectedGrid.tbody.find(">tr:not(.k-grouping-row)").eq(index+1);
						selectedGrid.select(row);
					}
				}
				break;
			default:
				
		}
	}

	//引用表
	function referenced_table_click(){
		var tableObj = $("#referenced_table_obj").val();
		selectTable("${databaseCode}","","referenced_table_name","referenced_table_obj",tableObj);
	}
	
	//引用字段
	function referenced_field_click(){
		var tableObj = $("#referenced_table_obj").val();
		var fieldObj = $("#referenced_field_obj").val();
		selectField("${databaseCode}","","referenced_field_name","referenced_field_obj",tableObj,fieldObj);
	}
	
</script>
</head>
<body>
	<div id="main_content">
		<div id="toolbar"></div>
		<input type="text" id="tableDropDownList" style="width:100%"
			data-role="dropdownlist" 
			data-text-field="name"
            data-value-field="TableId"
            data-bind="
            	value:tableSelect,
            	source:tableDropDownDataSource,
	 	 		events:{
	 	 			change:tableChange
	 	 		}" />
		<div id="splitter">
			<div id="select_field_grid" 
				data-role="grid" 
				data-editable="false" 
				data-selectable="rows,multiple" 
				data-groupable="false"
				data-sortable="true"
				data-pageable="false"
				data-reorderable="false"
				data-columns="[
					{ field:'fname', title:'字段名' }
				]"
				data-bind="
					source:srcGridDataSource
				"
			></div>
			<table>
				<tr>
					<td align="center">
						<a class="k-button" onclick="move_btn_click(1)">移进</a>
						<br/><br/>
						<a class="k-button" onclick="move_btn_click(2)">移出</a>
						<br/><br/>
						<a class="k-button" onclick="move_btn_click(3)">上移</a>
						<br/><br/>
						<a class="k-button" onclick="move_btn_click(4)">下移</a>
					</td>
				</tr>
			</table>
			<div id="selected_field_grid"
				data-role="grid" 
				data-editable="false" 
				data-selectable="rows" 
				data-groupable="false"
				data-sortable="true"
				data-pageable="false"
				data-reorderable="false"
				data-columns="[
					{ field:'fname', title:'字段名' }
				]"
				data-bind="
					source:destGridDataSource
				"
			></div>
		</div>
	</div>
	<div id="field_content">
		<input type="hidden" id="modify_field_id" />
		<input type="hidden" id="modify_list_id" />
		<input type="hidden" id="modify_table_name" />
		<input type="hidden" id="referenced_table_obj" />
		<input type="hidden" id="referenced_field_obj" />
		<span>
			<!-- span内的input在新建字段时不清空值 -->
			<input type="hidden" id="table_type" />
		</span>
		<table cellspacing="5" border="0" style="width:320px">
	 		<tr>
	 			<td align="right" width="60px">名称</td>
	 			<td>
	 				<input type="text" id="field_name" name="field_name" class="k-textbox" required validationMessage="必填项" />
		 			<span class="k-invalid-msg" data-for="field_name"></span>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="right">字段类型</td>
	 			<td><input id="fieldTypeDropDownList" name="fieldTypeDropDownList" required validationMessage="必填项"
						data-role="dropdownlist" 
						data-text-field="name"
			            data-value-field="value"
			            data-value="string"
			            data-bind="
			            	value:fieldTypeSelect,
			            	source:fieldTypeDropDownDataSource,
				 	 		events:{
				 	 			change:fieldTypeChange
				 	 		}" />
					<span class="k-invalid-msg" data-for="fieldTypeDropDownList"></span>
				</td>
	 		</tr>
	 		<tr>
	 			<td align="right">字段长度</td>
	 			<td>
	 				<input id="field_len" name="field_len" data-role="numerictextbox" data-format="n0" data-min="1" required validationMessage="必填项" />
		 			<span class="k-invalid-msg" data-for="field_len"></span>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="right">引用表</td>
	 			<td>
	 				<input id="referenced_table_name" name="referenced_table_name" class="k-textbox" onclick="referenced_table_click()"/>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td align="right">引用字段</td>
	 			<td>
	 				<input id="referenced_field_name" name="referenced_field_name" class="k-textbox" onclick="referenced_field_click()" />
	 			</td>
	 		</tr>
	 		<tr>
	 			<td colspan="2" align="center">
	 				<button id="field_submit_btn" data-role="button" data-sprite-css-class="k-icon k-i-tick" class="k-button">确定</button>
	 				<button data-role="button" data-sprite-css-class="k-icon k-i-cancel" data-bind="events:{click:closeLayer}">取消</button>
	 			</td>
	 		</tr>
	 	</table>
	</div>
</body>
</html>