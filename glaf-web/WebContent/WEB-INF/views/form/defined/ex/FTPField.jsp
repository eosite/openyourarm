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
	var datasetId = '${param.dataSourceSetId}';
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	var idField = "" ;
	var ftpField = "" ;
	var fs = window.parent.$('#'+objelementId).val() ;
	if(fs){
		var fields = JSON.parse(fs);
		var datas = fields[0].datas ;
		idField = datas.idField ;
		ftpField = datas.ftpField ;
	}
	var datasource = window.parent.$('#'+datasetId).val() ;
	var columnDatas = [] ;
	
	if(datasource){
		var ds = JSON.parse(datasource);
		var datas = ds[0].columns || ds[0].selectColumns;
		$.each(datas,function(i,d){
			var col = {} ;
			col.name = d.title;
			col.dname = d.columnName ;
			columnDatas.push(col);
		})
	}
	
	$(function(){
	
		//数据绑定
		var viewModel = kendo.observable({
			srcDropdownDataSource : columnDatas,
	   		ftpField:ftpField,
	   		idField:idField,
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
		var retAry = [] ;
		//id
		var idDataItem = $("#idField").data("kendoDropDownList").dataItem();
		var idObj = idDataItem.toJSON();
		//ftp
		var dataItem = $("#ftpField").data("kendoDropDownList").dataItem();
		var ftpObj = dataItem.toJSON();
		
		//[{name:'xx',datas:{idField:'',ftpField:''}}]
		var name = idObj.name +"," +ftpObj.name ;
		var retObj = {
			name : name , 
			datas:{
				idField :  idObj.dname ,
				ftpField:  ftpObj.dname
			}
		}
		retAry.push(retObj);
		
		window.parent.$("#"+nameElementId).val(name);
		window.parent.$("#"+objelementId).val(JSON.stringify(retAry));
		
		parent.layer.close(parent.layer.getFrameIndex());
	}
</script>
</head>
<body>
<div style="width: 100%">
<table style="width: 100%;text-align: center;">
	<tr>
		<td>
		主键<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="idField" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="name"
            data-value-field="dname"
            data-bind="
            	value:idField,
            	source:srcDropdownDataSource" />
		</td>
	</tr>
	<tr>
		<td>
		路径保存字段<span style="color: red;">*</span>：
		</td>
		<td>
		<input type="text" id="ftpField" style="width:200px"
			data-role="dropdownlist" 
			data-text-field="name"
            data-value-field="dname"
            data-bind="
            	value:ftpField,
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