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

	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	var dataSourceSetId = '${param.dataSourceSetId}';
	
	//返回表达式执行函数
	function retExpression(data) {
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		dataItem.expression = data.expActVal;
		dataItem.expData = JSON.stringify(data);
		grid.editCell();
	}
	//获取参数
	function getExpression() {
		var expressionData = [];
		var datasourceSet = window.parent.$('#'+dataSourceSetId).val();
		if(datasourceSet){
			var datasourceSetObj = JSON.parse(datasourceSet) ;
			var columns = datasourceSetObj[0].columns ;
			for (var i = 0; i < columns.length; i++) {
				var express = {};
				var selectFieldRow = columns[i];
				express.t = selectFieldRow.title;
				express.dType = getfieldTypeValue(selectFieldRow.FieldType);
				express.code = selectFieldRow.code;
				express.value = selectFieldRow.value;
				express.name = selectFieldRow.title;
				expressionData.push(express);
			}
			return expressionData;
		}
	}
	//初始化参数
	function initExpressionFn() {
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.expData);
	}

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
</script>
</head>
<body>
	<div id="grid" data-role="grid" data-toolbar="['create','save']" data-editable="true" data-selectable="rows" data-groupable="false"
		data-sortable="false" data-pageable="false" data-reorderable="false"
		data-columns="[
				{ field:'name', title:'参数名' },
				{ field:'expression', title:'表达式',editor:expEditor},
				{ command: 'destroy', title:'',width:'80px'}
			]"
		data-bind="
				source: grid_dataSource,
				events: {
					saveChanges: onSaveChanges
				}
			"></div>
	<script type="text/javascript">
		var dataSource = [];
		var objsource = window.parent.$('#'+objelementId).val() ;
		if(objsource){
			dataSource = JSON.parse(objsource);
		}
		kendo.bind($("#grid"), {
			grid_dataSource : dataSource,
			onSaveChanges : function(e) {
				var values = this.get("grid_dataSource");
				var hasValues = [];
				var names = "" ;	
				for (var i = 0; i < values.length; i++) {
					if (values[i].name == undefined || values[i].expression == undefined || values[i].name.trim() == "" || values[i].expression.trim() == "") {
						layer.alert("必须填写值", 3);
						return;
					} else {
						if (hasValues.indexOf(values[i].name) >= 0) {
							layer.alert("存在重复参数名，请检查", 3);
							return;
						} else {
							hasValues.push(values[i].name);
							if(!values[i].param){
								//自动生成唯一的参数
								values[i].param = '';
							}
							names += values[i].name+"," ;
						}
					}
				}
				window.parent.$('#'+nameElementId).val(names);
				window.parent.$('#'+objelementId).val(JSON.stringify(values));
				parent.layer.close(parent.layer.getFrameIndex());
			}
		});
	</script>
</body>
</html>