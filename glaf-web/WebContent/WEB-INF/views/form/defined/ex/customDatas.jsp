<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义数据</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/defineKendouiInit.js"></script>
<script type="text/javascript">
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
</script>
</head>
<body>
	<div id="grid" data-role="grid" data-toolbar="[{name:'createNew',text:'新增'}]" data-editable="true" data-selectable="rows" data-groupable="false"
		data-sortable="false" data-pageable="false" data-reorderable="false" 
		data-params="[{text:'获取',items:[{text:'所有行',type:'getAll',id:'grid'}]}]"  
		data-columns="[
				{ field:'param', title:'实际值' },
				{ field:'name', title:'显示值' },
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
		var objsource = window.parent.$('#'+objelementId).val() ,objJson;
		if(objelementId){
			if(objsource){
				dataSource = JSON.parse(objsource);	
			}
		}else{
			var $target = window.parent.$("body").data("target"),objid = $target.data("field");
			objsource = window.parent.$("input[data-field="+objid+"]").data("jsonSource")/*val()*/;
			if(objsource){
				objJson = JSON.parse(objsource);
				if(objJson && objJson.length && objJson[0].datas){
					dataSource = objJson[0].datas[$target.data("field")][0]['val'] ;
				}
			}
		}
		kendo.bind($("#grid"), {
			grid_dataSource : dataSource,
			onSaveChanges : function(e) {
				var values = this.get("grid_dataSource");
				var hasValues = [];
				var names = "" ;	
				var times = (new Date().getTime()) ;
				for (var i = 0; i < values.length; i++) {
					if (values[i].name == undefined /* || values[i].expression == undefined  */|| values[i].name.trim() == "" /* || values[i].expression.trim() == "" */) {
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
								values[i].param = 'col' + (times+i);
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
		
		$(".k-grid-createNew").click(function(e){
		    var grid = $('#grid').data('kendoGrid');
		    grid.dataSource.insert({name:'',param:''});
		    grid.refresh();
		});
		
	</script>
</body>
</html>