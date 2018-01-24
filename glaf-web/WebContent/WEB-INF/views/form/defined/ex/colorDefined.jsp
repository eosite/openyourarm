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
	function colorEditor(container,options){
		 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
	};
	function colorTemplate(dataItem){
		return "<div style='width:50px;height:20px;background-color:"+dataItem.color+"'></div>";
	}
	function endColorTemplate(dataItem){
		return "<div style='width:50px;height:20px;background-color:"+dataItem.endcolor+"'></div>";
	}
</script>
</head>
<body>
	<!-- <div id="grid"  data-toolbar="['create','save']" data-editable="true" data-selectable="rows" data-groupable="false"
		data-sortable="false" data-pageable="false" data-reorderable="false"
		data-columns="[
				{ field:'color', title:'开始颜色',editor:colorEditor,width:'80px',template:colorTemplate },
				{ field:'endcolor', title:'结束颜色',editor:colorEditor,width:'80px',template:colorTemplate },
				{ command: 'destroy', title:'',width:'80px'}
			]"
		data-bind="
				source: grid_dataSource,
				events: {
					saveChanges: onSaveChanges
				}
			"></div> -->
	<div id="grid"   data-editable="true" data-selectable="rows" data-groupable="false"
	data-sortable="false" data-pageable="false" data-reorderable="false"
	data-bind="
			source: grid_dataSource,
			events: {
				saveChanges: onSaveChanges
			}
		"></div>
	<script type="text/javascript">
		var dataSource = [];
		var objsource = window.parent.$('#'+objelementId).val() ;
		var gradientAble = false;
		if(objsource){
			var vals = JSON.parse(objsource) ;
			if(vals){
				dataSource = vals[0].colors;
				gradientAble = vals[0].gradientAble;
			}
		}
		var columns = [
			{ field:'color', title:'开始颜色',editor:colorEditor,width:'80px',template:colorTemplate },
			{ command: 'destroy', title:'',width:'80px'}
		]
		var gradientColumns = [
			{ field:'color', title:'开始颜色',editor:colorEditor,width:'80px',template:colorTemplate },
			{ field:'endcolor', title:'结束颜色',editor:colorEditor,width:'80px',template:endColorTemplate },
			{ command: 'destroy', title:'',width:'80px'}
		]
		$("#grid").kendoGrid({
			columns : columns,
			toolbar : [
				{ name: "create" },
    			{ name: "save" },
			    {
    				template: '<label class="k-button k-button-icontext">渐变颜色<input type="checkbox" class="gradientCheckbox"></label>'
			    }
			]
		});
		var gridObj = $("#grid").data("kendoGrid");

		$("body").on("change",".gradientCheckbox",function(events){
			var $this = $(this);
			if($this.prop("checked")){
				// gridObj.columns = gradientColumns;
				gridObj.setOptions({"columns":gradientColumns});
				$('.gradientCheckbox').prop("checked",true);
			}else{
				gridObj.setOptions({"columns":columns});
			}
		})

		kendo.bind($("#grid"), {
			grid_dataSource : dataSource,
			onSaveChanges : function(e) {
				var values = this.get("grid_dataSource");
				var names = "" ;	
				var times = (new Date().getTime()) ;
				for (var i = 0; i < values.length; i++) {
					if (values[i].color == undefined || values[i].color.trim() == "" ) {
						layer.alert("必须填写值", 3);
						return;
					} 
				}
				var res =  [] ;
				var reval = {} ;
				if(values && values.length>0){
					names = "颜色" ;
				}
				reval.name = names ;
				reval.colors = values ;
				reval.gradientAble = $('.gradientCheckbox').prop("checked");
				res.push(reval);
				window.parent.$('#'+nameElementId).val(names);
				window.parent.$('#'+objelementId).val(JSON.stringify(res));
				parent.layer.close(parent.layer.getFrameIndex());
			}
		});

		if(gradientAble){
			$('.gradientCheckbox').prop("checked",true).trigger("change");
		}

	</script>
</body>
</html>