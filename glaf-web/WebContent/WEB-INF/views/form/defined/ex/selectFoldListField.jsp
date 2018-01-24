<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<style type="text/css">
.ztree li span.button.tree_folder_ico_open{margin-right:2px; background: url(${contextPath}/images/folder-open.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_close{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_docu{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.tree_leaf_ico_open{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_close{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_docu{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.k-tabstrip-wrapper{
	height: 502px;
}
</style>
<script type="text/javascript">
var selectedTabStrip ;
	var uploadtype = '${param.uploadtype}' ;
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	var paramElementId = '${param.paramElementId}';
	var source = [],
		source2 = [],
		source3 = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	var params = window.parent.$('#'+paramElementId).val() ; //控件输入参数

	var textNameValue;
	if(objsource){
		var objAry = JSON.parse(objsource),
			objValues = objAry[0].values;
		if(objValues){
			$.each(objValues,function(i,item){
				if(item.columnType == 'nameKey'){
					textNameValue = item.ColumnName;
				}
			})
		}
	}

	

	var columnValues = [] ;
	if(params){
		var data = JSON.parse(params);
		for (var i = 0; i < data.length; i++) {
			var columns = data[i].columns;
			for(var j=0;j<columns.length;j++){
				var val = {};
				var param = columns[j];
				val.fname = param.tableNameCn+"->"+param.title ;
				val.dname = param.columnName ;
				columnValues.push(val);
			}
		}
	}
	
	$(function(){
		if(uploadtype == 'foldList'){
			$(".titleName").text("显示文本");
		}else if(uploadtype == 'cangeCalendar'){
			$(".titleName").text("选择日期");
		}
		//数据绑定
		var viewModel = kendo.observable({
			srcDropdownDataSource : new kendo.data.DataSource({
				data: columnValues,
	   		}),
	   		textNameValue:textNameValue,
		});
		kendo.bind($(document.body), viewModel);
	})

	//确定
	function confirm_field_click(){
		var fieldJson = [];
		var name = [];
		var temp = {};
		
		//textName
		dataItem = $("#textName").data("kendoDropDownList").dataItem();
		if(dataItem){
			name.push(dataItem.fname);
			temp = {};
			temp.name = dataItem.fname;
			temp.ColumnName = dataItem.dname;
			if(uploadtype == 'foldList'){
				temp.columnType = 'nameKey' ;
			}else if(uploadtype == 'cangeCalendar'){
				temp.columnType = 'dateKeyName' ;
			}
			fieldJson.push(temp);
		}else{
			if(uploadtype == 'foldList'){
				alert('请选择显示文本');
			}else if(uploadtype == 'cangeCalendar'){
				alert('请选择选择日期');
			}
			
			return ;
		}

		if(!fieldJson.length ){
			window.parent.$('#' + nameElementId).val("");
			window.parent.$('#' + objelementId).val("");
		}else{
			window.parent.$('#' + nameElementId).val(name);
			window.parent.$('#' + objelementId).val(JSON.stringify([{name:"数据样式定义",values:fieldJson}]));
		}
		
		parent.layer.close(parent.layer.getFrameIndex());
	}
</script>
</head>
<body>
	<div style="width: 100%">
	<table style="width: 100%;text-align: center;">
		<tr>
			<td>
			<span class="titleName">显示文本</span><span style="color: red;">*</span>：
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
			<td colspan="2">
				<button id="confirm_field_btn" class="k-button" onclick="confirm_field_click()">确定</button>
				<button id="close_field_btn" class="k-button" onclick="parent.layer.close(parent.layer.getFrameIndex())">关闭</button>
			</td>
		</tr>
	</table>
</body>
</html>