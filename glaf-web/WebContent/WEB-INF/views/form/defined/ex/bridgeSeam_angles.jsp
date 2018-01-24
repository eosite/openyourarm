<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/scripts/defineKendouiInit.js"></script>
<script type="text/javascript">
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
</script>
</head>
<body>
	<div id="grid" data-role="grid"
		data-toolbar="[{name:'createNew',text:'新增'},'save']"
		data-editable="true" data-selectable="rows" data-groupable="false"
		data-sortable="false" data-pageable="false" data-reorderable="false"
		data-columns="[
				{ field:'name', title:'角度' },
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
		var objsource = window.parent.$('#' + objelementId).val(), objJson;
		if (objelementId) {
			if (objsource) {
				dataSource = JSON.parse(objsource);
			}
		} else {
			var $target = window.parent.$("body").data("target"), objid = $target
					.data("field");
			objsource = window.parent.$("input[data-field=" + objid + "]")
					.data("jsonSource")/*val()*/;
			if (objsource) {
				objJson = JSON.parse(objsource);
				if (objJson && objJson.length && objJson[0].datas) {
					dataSource = objJson[0].datas[$target.data("field")][0]['val'];
				}
			}
		}
		kendo
				.bind(
						$("#grid"),
						{
							grid_dataSource : dataSource,
							onSaveChanges : function(e) {
								var values = this.get("grid_dataSource"),
									names = "",
									rvalues = "",
									retNames = [];
								for (var i = 0; i < values.length; i++) {
									if (values[i].name == undefined ) {
										layer.alert("必须填写值", 3);
										return;
									} 
									retNames.push(values[i].name);
								}
								if(values.length != 0){
									names = retNames.join(",");
									rvalues = JSON.stringify(values);
								}
								window.parent.$('#' + nameElementId).val(names);
								window.parent.$('#' + objelementId).val(rvalues);
								parent.layer.close(parent.layer.getFrameIndex());
							}
						});

		$(".k-grid-createNew")
				.click(
						function(e) {
							var grid = $('#grid').data('kendoGrid'), 
								dataItems = grid.dataItems();
							grid.dataSource.insert({
								name : 0
							});
							grid.refresh();
						});
	</script>
</body>
</html>