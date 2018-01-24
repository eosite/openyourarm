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
<script type="text/javascript">
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	var paramElementId = '${param.paramElementId}';
	var fixDataSourceId = '${param.fixDataSourceId}';
	var source = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	var params = window.parent.$('#'+paramElementId).val() ; //控件输入参数
	var fixData = window.parent.$('#'+fixDataSourceId).val() ; //控件输入参数
	if(objsource){
		var objJson = JSON.parse(objsource);
		if(objJson[0].value){
			source = objJson[0].value ;
		}
	}
	
	//返回表达式执行函数
	function retExpression(data) {
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		dataItem.expression = data.expActVal;
		dataItem.expdata = JSON.stringify(data);
		grid.editCell();
	}
	//获取参数
	function getExpression() {
		var expressionData = [];
		if(params){
			var paramsJsons = JSON.parse(params);
			var fixJsons = JSON.parse(fixData);
			
			for(var i=0;i<paramsJsons.length;i++){
				var selectColumns = paramsJsons[0].columns;
				for (var i = 0; i < selectColumns.length; i++) {
					var express = {};
					var selectFieldRow = selectColumns[i];
					express.t = selectFieldRow.title;
					express.dType = selectFieldRow.FieldType;
					express.code = selectFieldRow.code;
					express.value = selectFieldRow.value;
					express.datasetId = selectFieldRow.datasetId;
					express.name = selectFieldRow.title;
					expressionData.push(express);
				}
			}	
		}
		var items = [ {
				text : 'id',
				type : 'getData',
				columnName : 'id'
			},{
				text : '编号',
				type : 'getData',
				columnName : 'index'
			}, {
				text : '序号值',
				type : 'getData',
				columnName : 'number'
			}, {
				text : '内容区',
				type : 'getData',
				columnName : 'content'
			} ];
		for(var i=0;i<items.length;i++){
			var express = {};
			express.t = items[i].text;
			express.name = items[i].text;
			express.dType = "string";
			express.code = "~F{default.fix."+items[i].columnName+"}";
			express.value = "~F{default.自定义数据集."+items[i].text+"}";
			expressionData.push(express);
		}
		/* 
		for (var i = 0; i < paramsJsons.length; i++) {
				var express = {};
				var param = paramsJsons[i];
				express.t = param.name;
				express.dType = "string";
				express.code = "~F{"+param.param+"."+param.param+"."+param.param+"}";
				express.value = "~F{"+param.name+"}";
				express.name = param.name;
				expressionData.push(express); 
		}
		 */
		return expressionData;
	}
	//初始化参数
	function initExpressionFn() {
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.expdata);
	}
	//表达式定义
	function expEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		var _href = "${pageContext.request.contextPath}/mx/expression/defined/view?category=front&retFn=retExpression" + "&getFn=getExpression&initExpFn=initExpressionFn";
		$(input).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}

	// html编辑器获取变量方法
	function getVarFn() {
		//获取字典类型FieldTypeControl
		var expressionData = [];
		/* for (var i = 0; i < selectColumns.length; i++) {
			var express = {};
			var selectFieldRow = selectColumns[i];
			express.t = selectFieldRow.title;
			express.dType = getfieldTypeValue(selectFieldRow.FieldType);
			express.code = selectFieldRow.code != null ? selectFieldRow.code.replace("~F{", "~V{") : "";
			express.value = selectFieldRow.value != null ? selectFieldRow.value.replace("~F{", "~V{") : "";
			express.name = selectFieldRow.title;
			expressionData.push(express);
		} */
		return expressionData;
	}
	// html编辑器获取参数方法
	function getParamFn() {
		return [];
	}
	// html编辑器回调函数
	function retHtmlFn(data) {
		if (data) {
			var grid = $("#grid").data("kendoGrid");
			var dataItem = grid.dataItem(grid.select());
			dataItem.htmlExpression = data.htmlVal;
			dataItem.htmldata = JSON.stringify(data);
			grid.editCell();
		}
	}
	// html编辑器回显内容 
	function initHTMLFn() {
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.htmldata);
	}
	//HTML样式定义定义
	function htmlExpEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		var _href = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn&"
				+ "getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
		$(input).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}
	// html编辑器回调函数
	function retHtmlFn0(data) {
		if (data) {
			var grid = $("#grid").data("kendoGrid");
			var dataItem = grid.dataItem(grid.select());
			dataItem.htmlStyle = data.htmlVal;
			dataItem.htmlstyledata = JSON.stringify(data);
			grid.editCell();
		}
	}
	// html编辑器回显内容 
	function initHTMLFn0() {
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.htmlstyledata);
	}
	function htmlStyleEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		var _href = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn0&"
				+ "getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn0";
		$(input).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}
</script>
</head>
<body>
	<div id="grid"></div>
	<script type="text/javascript">
		//初始化图片链接grid
		$("#grid").kendoGrid({
			dataSource : source,
			height : 300,
			sortable : true,
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'expression',
				title : '表达式定义',
				editor : expEditor
			},{
				field : 'htmlStyle',
				title : '序号区HTML样式定义',
				editor : htmlStyleEditor
			},{
				field : 'htmlExpression',
				title : '内容区HTML样式定义',
				editor : htmlExpEditor
			}, {
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = "" ;
				for (var i = 0; i < values.length; i++) {
					if (values[i].expression == undefined || values[i].expression == "" || values[i].htmlExpression == undefined || values[i].htmlExpression == "")
					{
						layer.alert("必须填写值", 3);
						return;
					}
				}
				if(values.length){
					names = "自定义" ;
				}
				var reAry = [] ;
				var reObj = {};
				reObj.name = names ;
				reObj.value =  values;
				reAry.push(reObj);
				window.parent.$('#'+nameElementId).val(names);
				window.parent.$('#'+objelementId).val(JSON.stringify(reAry));
				parent.layer.close(parent.layer.getFrameIndex());
			},
			edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});
	</script>
</body>
</html>