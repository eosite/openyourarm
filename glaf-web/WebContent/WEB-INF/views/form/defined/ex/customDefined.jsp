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
	var datasourceElementId = '${param.datasourceElementId}';
	var getParamFn_ = '${param.getParamFn}';
	var source = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	var params = window.parent.$('#'+paramElementId).val() ; //控件输入参数
	var dataSource = window.parent.$('#'+datasourceElementId).val() ; //控件数据源
	var sysMenuData = '${sysMenuData}';
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
		dataItem.expVal = data.expVal;
		dataItem.expdata = JSON.stringify(data);
		grid.editCell();
	}

	var fieldTypeControl = [];
	$.ajax({
		url : '${pageContext.request.contextPath }/mx/form/defined/getDictByCode',
		data : {
			code : 'FieldTypeControl'
		},
		type : 'post',
		dataType : 'json',
		async : false,
		success : function(data) {
			$.each(data, function(i, d) {
				var e = {};
				e.code = d.code;
				e.value = d.value
				fieldTypeControl.push(e);
			});
		}
	});

	//转换字段类型
	function getfieldTypeValue(fieldType) {
		var v = "";
		$.each(fieldTypeControl, function(i, d) {
			if (d.code == fieldType) {
				v = d.value;
			}
		})
		return v;
	}

	//获取参数
	function getExpression() {
		var expressionData = [];
		var num = 1;

		var window_parent = window.parent || window;
		if(getParamFn_ && $.isFunction(window_parent[getParamFn_])){
			expressionData = window_parent[getParamFn_]();
		}
		if(params){
			expressionData.push({
				id:101,
				name:'输入形参',
				isParent : true,
				pId: 0,
				open : "false",
			}); 

			var paramsJsons = JSON.parse(params);
			if(paramsJsons && paramsJsons[0]["type"]){
				paramsJsons = paramsJsons[0]["source"] ;
				/*
				$.each(value_[0]["source"], function(i, p) {
					p.text = p.name;
					p.id = v.NAME_;
					p.title = v.NAME_ + "-选中行-" + p.name;
					v.items.push(p);
				});
				if(value_[0]["objSource"]){
					$.each(value_[0]["objSource"], function(i, p) {
						var objv = {
							text : p.name ,
							items:[]
						};
						$.each(JSON.parse(p.child), function(ii, pp) {
							pp.text = pp.name;
							pp.id = v.NAME_;
							pp.dataType = "obj";
							pp.dataId = p.param;
							objv.items.push(pp);
						});
						v.items.push(objv);
					});
				}

				if(value_[0]["arySource"]){
					$.each(value_[0]["arySource"], function(i, p) {
						var objv = {
							text : p.name ,
							items:[]
						};
						$.each(JSON.parse(JSON.parse(p.child)[0]["child"]), function(ii, pp) {
							pp.text = pp.name;
							pp.id = v.NAME_;
							pp.dataType = "ary";
							pp.dataId = p.param;
							objv.items.push(pp);
						});
						v.items.push(objv);
					});
				}*/
			}else{
			}
			for (var i = 0; i < paramsJsons.length; i++) {
				var express = {};
				var param = paramsJsons[i];
				express.t = param.name;
				express.dType = "string";
				express.code = "~F{"+param.param+"."+param.param+"."+param.param+"}";
				express.value = "~F{"+param.name+"}";
				express.name = param.name;
				express.param = param.param;
				express.columnName = param.param;
				express.pId = 101;
				express.id = 101 + num;
				num++;

				expressionData.push(express); 
			}
		}

		

		if(dataSource){
			expressionData.push({
				id:201,
				name:'数据源字段',
				isParent : true,
				pId: 0,
				open : "false",
			}); 
			// var dataSourceJsons = JSON.parse(dataSource);
			// if(dataSourceJsons && dataSourceJsons[0]){
			// 	var columns =  dataSourceJsons[0].selectColumns || dataSourceJsons[0].columns;
			// 	for (var i = 0; i < columns.length; i++) {
			// 		var express = {};
			// 		var selectFieldRow = columns[i];
			// 		express.t = selectFieldRow.title;
			// 		express.dType = getfieldTypeValue(selectFieldRow.FieldType);
			// 		express.code = selectFieldRow.code;
			// 		express.value = selectFieldRow.value;
			// 		express.name = selectFieldRow.title;
			// 		express.pid = 2;
			// 		express.id = num;
			// 		num++;
			// 		expressionData.push(express);
			// 	}
			// }

			var datasourceSetObj = JSON.parse(dataSource);
			if("columns" in datasourceSetObj[0]){
				var columns = datasourceSetObj[0].columns;
				for (var i = 0; i < columns.length; i++) {
					var express = {};
					var selectFieldRow = columns[i];
					express.t = selectFieldRow.title;
					express.dType = getfieldTypeValue(selectFieldRow.FieldType);
					express.datasetId = selectFieldRow.datasetId;
					express.code = selectFieldRow.code;
					express.value = selectFieldRow.value;
					express.name = selectFieldRow.tableNameCn + "." + selectFieldRow.title;
					express.pId = 201;
					express.id = 201 + num;
					express.columnName = selectFieldRow.columnName;
					num++;
					expressionData.push(express);
				}
			}else if("selectDatasource" in datasourceSetObj[0]){
				var columns =datasourceSetObj[0].columns || datasourceSetObj[0].selectColumns ;
				for (var j = 0; j < columns.length; j++) {
					var express = {};
					var selectFieldRow = columns[j];
					express.t = selectFieldRow.title;
					express.dType = getfieldTypeValue(selectFieldRow.FieldType);
					express.datasetId = selectFieldRow.datasetId;
					express.code = selectFieldRow.code;
					express.value = selectFieldRow.value;
					express.name = selectFieldRow.tableNameCn + "." + selectFieldRow.title;
					express.pId = 201;
					express.columnName = selectFieldRow.columnName;
					express.id = 201 + num;
					num++;
					expressionData.push(express);
				}
			}

		}

		
		/* for (var i = 0; i < selectColumns.length; i++) {
			var express = {};
			var selectFieldRow = selectColumns[i];
			express.t = selectFieldRow.title;
			express.dType = getfieldTypeValue(selectFieldRow.FieldType);
			express.code = selectFieldRow.code;
			express.value = selectFieldRow.value;
			express.name = selectFieldRow.title;
			expressionData.push(express);
		} */
		if(sysMenuData){
			if(typeof sysMenuData == 'string'){
				sysMenuData = JSON.parse(sysMenuData);
			}
			expressionData.push({
				id:505,
				name:'系统菜单',
				isParent : true,
				pId: 0,
				open : "false",
			}); 

			for (var i = 0; i < sysMenuData.length; i++) {
				var express = {};
				var param = sysMenuData[i];
				express.t = param.text;
				express.dType = "string";
				express.code = "~F{"+param.columnName+"."+param.columnName+"."+param.columnName+"}";
				express.value = "~F{"+param.text+"}";
				express.name = param.text;
				express.param = param.columnName;
				express.columnName = param.columnName;
				express.pId = 505;
				express.id = 505 + num;
				num++;

				expressionData.push(express); 
			}
		}
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
		var _href = "${pageContext.request.contextPath}/mx/expression/defined/view?category=db&retFn=retExpression" + "&getFn=getExpression&initExpFn=initExpressionFn";
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
				field : 'desc',
				title : '描述',
				// editor : 
			},{
				field : 'expVal',
				title : '表达式定义',
				editor : expEditor,
				template: function(data){
					return data.expVal || data.expression || "";
				}
			}, {
				field : 'htmlExpression',
				title : 'HTML样式定义',
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
					if (values[i].expression == undefined || values[i].expression == "" || values[i].htmlExpression == undefined || values[i].htmlExpression == "") {
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