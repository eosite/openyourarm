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
</script>
</head>
<body>
	<div id="grid" data-role="grid" data-toolbar="[{name:'createNew',text:'新增'},'save']" data-editable="true" data-selectable="rows" data-groupable="false"
		data-sortable="false" data-pageable="false" data-reorderable="false"
		data-columns="[
				{ field:'jname', title:'图片名称' },
				{ field:'name', title:'图片链接',editor : textboxEditor },
				{ command: 'destroy', title:'',width:'80px'}
			]"
		data-bind="
				source: grid_dataSource,
				events: {
					saveChanges: onSaveChanges
				}
			"></div>
	<script type="text/javascript">
		function selectImageFunc(url){
			$(this).val(url);
			var grid = $("#grid").data("kendoGrid");
			var data = grid.dataItem(grid.select());
			data.set("name", url);
			grid.dataSource.sync();
			var index = layer.getFrameIndex(window.name); //获取窗口索引
            layer.close(layer.index);
		}
		//选取图片链接
		function textboxEditor(container, options) {
			var id = "input_" + (new Date().getTime());
			var $input = $('<input id='+id+' type="text" class="k-textbox"/>');
			$input.attr("readonly","true").appendTo(container);
			$input.bind("dblclick",function(e){
				$this = $(this);
				var link = "${pageContext.request.contextPath}/mx/form/defined/ex/selectImage?fn=selectImageFunc&targetId="+id;
					//选取关联页面
					$.layer({
						type: 2,
						maxmin: true,
						shadeClose: true,
						title: "请选择图片",
						closeBtn: [0, true],
						shade: [0, '#000'],
						border : [ 10, 0.3, '#000' ],
						offset : [ '', '' ],
						fadeIn: 100,
						area: ['1100px', '580px' ],
						iframe : {
							src : link
						}
					});
			});
		}
		
		var dataSource = [];
		var objsource = window.parent.$('#'+objelementId).val() ;
		if(objsource){
			dataSource = JSON.parse(objsource);
		}
		//保存按钮
		kendo.bind($("#grid"), {
			grid_dataSource : dataSource,
			onSaveChanges : function(e) {
				var values = this.get("grid_dataSource");
				var hasValues = [];
				var urls = "" ;	
				var times = (new Date().getTime()) ;
				for (var i = 0; i < values.length; i++) {
					if (values[i].name == undefined /* || values[i].expression == undefined  */|| values[i].name.trim() == "" /* || values[i].expression.trim() == "" */) {
						layer.alert("必须填写值", 3);
						return;
					} else {
						if (hasValues.indexOf(values[i].name) >= 0) {
							layer.alert("存在重复图片链接，请检查", 3);
							return;
						} else {
							hasValues.push(values[i].name);
							if(!values[i].jname){
								//自动生成唯一的参数
								values[i].jname = (times+i);
							}
							urls += values[i].name+"," ;
						}
					}
				}
				window.parent.$('#'+nameElementId).val(urls);			
				window.parent.$('#'+objelementId).val(JSON.stringify(values));
				parent.layer.close(parent.layer.getFrameIndex());
			}
		});
		//新增按钮事件
		$(".k-grid-createNew").click(function(e){
		    var grid = $('#grid').data('kendoGrid'),dataItems = grid.dataItems(),times = (new Date().getTime());
		    for(var i=0;i<dataItems.length;i++){
		    	var data = dataItems[i] ;
		    	if(data.jname == times){
		    		return  ;
		    	}
		    }
		    grid.dataSource.insert({jname:times,name:''});
		    grid.refresh();
		});
	</script>
</body>
</html>