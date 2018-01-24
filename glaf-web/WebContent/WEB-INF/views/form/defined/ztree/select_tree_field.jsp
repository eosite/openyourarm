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
<script type="text/javascript">
	var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;
	var layerIndex = -1;
	$(function(){
		$("#field_content").hide();//页面加载时隐藏字段编辑DIV
		//var tableJson = JSON.parse('${tableJson}');//传入的表
		var fieldJson = [];//传入的字段
		//if('${fieldJson}' != ''){
		//	fieldJson = JSON.parse('${fieldJson}');
		//}
		
		var tableJson = JSON.parse(parent.tableJsonArray);
		if(parent.fieldJsonArray != ''){
			fieldJson = JSON.parse(parent.fieldJsonArray);
		}
		
		//字段数据源传入参数
		var firstTableType = tableJson[0].tableType,firstTableId=tableJson[0].TableId,firstTableName=tableJson[0].TableName;
		var prefixTableName = tableJson[0]['tables'][0]+"_0_" ;
		//计算初始化选择字段
		var idKeyValue=prefixTableName+'treeid',pIdKeyValue=prefixTableName+'parent_id',textNameValue,iconValue,
			iconOpenValue,iconCloseValue,indexValue=prefixTableName+'index_id';
		for (var i = 0; i < fieldJson.length; i++) {
			switch (fieldJson[i].columnType) {
				case "idKey":
					idKeyValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "indexKey":
					indexValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "pIdKey":
					pIdKeyValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "nameKey":
					textNameValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "icon":
					iconValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "iconOpen":
					iconOpenValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "iconClose":
					iconCloseValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				default:
					break;
			}
		}
		//数据绑定
		var viewModel = kendo.observable({
			srcDropdownDataSource : new kendo.data.DataSource({//表中数据字段数据源
	   			type: "json",
	            transport: {
	               	read:{
                        url: "${pageContext.request.contextPath}/rs/isdp/cellDataField/selectFieldByTableId",
                        type: "POST",
                        dataType:"JSON",
                        data:{
                        	tableId:firstTableId,
                			type:99,
                			tableName:firstTableName
                        }
	               	},
	                parameterMap : function(options, operation) {
	               	  	if(operation=="read"){
	               	  		options.systemName = "${databaseCode}";
	               	  		return options;
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
	   		idKeyValue:idKeyValue,
	   		indexValue:indexValue,
	   		pIdKeyValue:pIdKeyValue,
	   		textNameValue:textNameValue,
	   		iconValue:iconValue,
	   		iconOpenValue:iconOpenValue,
	   		iconCloseValue:iconCloseValue,
		});
		kendo.bind($(document.body), viewModel);
		//end
		
		//按钮
		$("#confirm_field_btn").kendoButton({
			icon:"k-icon k-i-tick"
		});
		$("#add_field_btn").kendoButton({
			icon:"k-icon k-i-plus"
		});
		$("#close_field_btn").kendoButton({
			icon:"k-icon k-i-close"
		});
		//end
		
	});
	//页面初始化结束
	
	//确定
	function confirm_field_click(){
		var fieldId = [],name = [],fieldJson = [];
		var temp = {};
		
		//idkey
		var dataItem = $("#idKey").data("kendoDropDownList").dataItem();
		if(dataItem){
			fieldId.push(dataItem.FieldId);
			name.push(dataItem.fname);
			temp.FieldTable = dataItem.tablename;
			temp.FieldLength = dataItem.strlen;
			temp.FieldType = dataItem.dtype;
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			temp.FieldId = dataItem.FieldId;
			temp.columnType = 'idKey' ;
			fieldJson.push(temp);
		}else{
			alert('请选择分级编号');
			return ;
		}
		
		//index
		dataItem = $("#index").data("kendoDropDownList").dataItem();
		if(dataItem){
			fieldId.push(dataItem.FieldId);
			name.push(dataItem.fname);
			temp = {};
			temp.FieldTable = dataItem.tablename;
			temp.FieldLength = dataItem.strlen;
			temp.FieldType = dataItem.dtype;
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			temp.FieldId = dataItem.FieldId;
			temp.columnType = 'indexKey' ;
			fieldJson.push(temp);
		}else{
			alert('请选择节点编号');
			return ;
		}
		
		//pIdKey
		dataItem = $("#pIdKey").data("kendoDropDownList").dataItem();
		if(dataItem){
			fieldId.push(dataItem.FieldId);
			name.push(dataItem.fname);
			temp = {};
			temp.FieldTable = dataItem.tablename;
			temp.FieldLength = dataItem.strlen;
			temp.FieldType = dataItem.dtype;
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			temp.FieldId = dataItem.FieldId;
			temp.columnType = 'pIdKey' ;
			fieldJson.push(temp);
		}else{
			alert('请选择父类编号');
			return ;
		}
		
		//textName
		dataItem = $("#textName").data("kendoDropDownList").dataItem();
		if(dataItem){
			fieldId.push(dataItem.FieldId);
			name.push(dataItem.fname);
			temp = {};
			temp.FieldTable = dataItem.tablename;
			temp.FieldLength = dataItem.strlen;
			temp.FieldType = dataItem.dtype;
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			temp.FieldId = dataItem.FieldId;
			temp.columnType = 'nameKey' ;
			fieldJson.push(temp);
		}else{
			alert('请选择显示文本');
			return ;
		}
		
		//icon
		dataItem = $("#icon").data("kendoDropDownList").dataItem();
		if(dataItem){
			fieldId.push(dataItem.FieldId);
			name.push(dataItem.fname);
			temp = {};
			temp.FieldTable = dataItem.tablename;
			temp.FieldLength = dataItem.strlen;
			temp.FieldType = dataItem.dtype;
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			temp.FieldId = dataItem.FieldId;
			temp.columnType = 'icon' ;
			fieldJson.push(temp);
		}
		
		//iconOpen
		dataItem = $("#iconOpen").data("kendoDropDownList").dataItem();
		if(dataItem){
			fieldId.push(dataItem.FieldId);
			name.push(dataItem.fname);
			temp = {};
			temp.FieldTable = dataItem.tablename;
			temp.FieldLength = dataItem.strlen;
			temp.FieldType = dataItem.dtype;
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			temp.FieldId = dataItem.FieldId;
			temp.columnType = 'iconOpen' ;
			fieldJson.push(temp);
		}
		
		//iconClose
		dataItem = $("#iconClose").data("kendoDropDownList").dataItem();
		if(dataItem){
			fieldId.push(dataItem.FieldId);
			name.push(dataItem.fname);
			temp = {};
			temp.FieldTable = dataItem.tablename;
			temp.FieldLength = dataItem.strlen;
			temp.FieldType = dataItem.dtype;
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			temp.FieldId = dataItem.FieldId;
			temp.columnType = 'iconClose' ;
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
	
	function add_field_click(){
		selectField('','','','','${tableJson}','');
	}
</script>
</head>
<body>
<div style="width: 100%">
<table style="width: 100%;text-align: center;">
	<tr>
		<td>
		分级编号<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="idKey" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:idKeyValue,
            	source:srcDropdownDataSource" />
		</td>
		<td>
           	<button id="add_field_btn" class="k-button" onclick="add_field_click()">新增字段</button>
		</td>
	</tr>
	<tr>
		<td>
		节点编号<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="index" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:indexValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td>
		父类编号<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="pIdKey" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:pIdKeyValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td>
		显示文本<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="textName" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:textNameValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td>
		显示图标：
		</td>
		<td>
		<input type="text" id="icon" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:iconValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td>
		打开图标：
		</td>
		<td>
		<input type="text" id="iconOpen" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:iconOpenValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td>
		关闭图标：
		</td>
		<td>
		<input type="text" id="iconClose" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:iconCloseValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<button id="confirm_field_btn" class="k-button" onclick="confirm_field_click()">确定</button>
			<button id="close_field_btn" class="k-button" onclick="parent.layer.close(parent.layer.getFrameIndex())">关闭</button>
		</td>
	</tr>
</table>
</div>
</body>
</html>