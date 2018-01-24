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
	var flag = '${param.flag}';
	var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;
	var layerIndex = -1;
	$(function(){
		$("#field_content").hide();//页面加载时隐藏字段编辑DIV
		var fieldJson = [];//传入的字段
		
		var tableJson = JSON.parse(parent.tableJsonArray);
		//获取字段
		console.log(tableJson[0]);
		var columnDatas = [] ;
		if(tableJson!=null){
			var datas = tableJson[0].columns || tableJson[0].selectColumns;
			$.each(datas,function(i,d){
				var col = {} ;
				col.fname = d.title;
				col.dname = d.columnName ;
				columnDatas.push(col);
			})
		}
		
		if(parent.fieldJsonArray != undefined && parent.fieldJsonArray != ''){
			fieldJson = JSON.parse(parent.fieldJsonArray);
		}
		
		//字段数据源传入参数
		var firstTableType = tableJson[0].tableType,firstTableId=tableJson[0].TableId,firstTableName=tableJson[0].TableName;
		var prefixTableName =/*  tableJson[0].selectColumns[0].tableName || tableJson[0]['tables'][0]+"_0_" */ "" ;
		if(tableJson[0]){
			var d = tableJson[0].tables || tableJson[0].selectColumns ;
			if(d && d.length){
				prefixTableName = d[0].tableName || d[0]+"_0_" ;
			}
		}
		//计算初始化选择字段
		var idKeyValue=prefixTableName+'treeid',titleValue=prefixTableName+'parent_id',contentValue,iconValue,
			iconOpenValue,iconCloseValue,imgUrlValue=prefixTableName+'index_id';
		for (var i = 0; i < fieldJson.length; i++) {
			switch (fieldJson[i].columnType) {
				
				case "imgUrl":
					imgUrlValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "title":
					titleValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				case "content":
					contentValue = fieldJson[i].ColumnName==undefined ? '' : fieldJson[i].ColumnName ;
					break;
				
				default:
					break;
			}
		}
		//数据绑定
		var viewModel = kendo.observable({
			srcDropdownDataSource : columnDatas,
	   		
	   		imgUrlValue:imgUrlValue,
	   		titleValue:titleValue,
	   		contentValue:contentValue,
	   		
	   		isVisible: !flag,
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
		
		$("#clearIdkey").kendoButton({
			click:function(){
				$("#idKey").data("kendoDropDownList").value("");
			}
		});
	});
	//页面初始化结束
	
	//确定
	function confirm_field_click(){
		var fieldId = [],name = [],fieldJson = [];
		var temp = {};
		
		
		
		//pIdKey
		dataItem = $("#imgUrl").data("kendoDropDownList").dataItem();
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
			temp.columnType = 'imgUrl' ;
			fieldJson.push(temp);
		}else{
			alert('请选择图片源');
			return ;
		}
		
		if(!flag){
			//textName
			dataItem = $("#title").data("kendoDropDownList").dataItem();
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
				temp.columnType = 'title' ;
				fieldJson.push(temp);
			}else{
				alert('请选择标题');
				return ;
			}
		}
		
		//icon
		dataItem = $("#content").data("kendoDropDownList").dataItem();
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
			temp.columnType = 'content' ;
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
		图片源<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="imgUrl" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:imgUrlValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td>
		标题<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="title" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	value:titleValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td class='ztreehid'>
		内容<span style="color: red;">*</span>：
		</td>
		<td>
		<input class='ztreehid' type="text" id="content" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="fname"
            data-value-field="dname"
            data-bind="
            	
            	value:contentValue,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	
	<script>
	if(flag){
		$('.ztreehid').hide();
	}
	</script>
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