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
	function editor(container,options){
		 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox();
	}
</script>
</head>
<body>
	<div id="grid" data-role="grid" data-toolbar="['create','save']" data-editable="true" data-selectable="rows" data-groupable="false"
		data-sortable="false" data-pageable="false" data-reorderable="false"
		data-columns="[
				{ field:'name', title:'顺序号',editor : editor},
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
					if (values[i].name == undefined /* || values[i].expression == undefined  */|| values[i].name == "" /* || values[i].expression.trim() == "" */) {
						layer.alert("必须填写值", 3);
						return;
					} else {
						if (hasValues.indexOf(values[i].name) >= 0) {
							layer.alert("存在重复顺序号，请检查", 3);
							return;
						} 
					}
					names += values[i].name + "," ;
				}
				window.parent.$('#'+nameElementId).val(names);
				window.parent.$('#'+objelementId).val(JSON.stringify(values));
				parent.layer.close(parent.layer.getFrameIndex());
			}
		});
	</script>
</body>
</html>