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
	function upFun(e){
        var $target = $(e.target),$tr = $target.parents("tr"), $grid = $(e.delegateTarget).data("kendoGrid"),
        	index = $tr.index(),items = $grid.dataItems()  ;
        if(index){
            $grid.dataSource.sync();
        	var temp = items[index -1] ;
            items[index-1] = items[index];
            items[index] = temp ;
            var dataSource = new kendo.data.DataSource({
            	  data: items 
            });
            $grid.setDataSource(dataSource);
            $grid.dataSource.sync();
        }	    
	}
	function downFun(e){
        var $target = $(e.target),$tr = $target.parents("tr"), $grid = $(e.delegateTarget).data("kendoGrid"),
        	index = $tr.index(),items = $grid.dataItems()  ;
        if(items.length -1 != index){
            $grid.dataSource.sync();
        	var temp = items[index + 1] ;
            items[index + 1] = items[index];
            items[index] = temp ;
            var dataSource = new kendo.data.DataSource({
            	  data: items 
            });
            $grid.setDataSource(dataSource);
            $grid.dataSource.sync();
        }	    
	}
</script>
</head>
<body>
	<div id="grid" data-role="grid" data-toolbar="[{name:'createNew',text:'新增'},'save']" data-editable="true" data-selectable="rows" data-groupable="false"
		data-sortable="false" data-pageable="false" data-reorderable="false"
		data-columns="[
				{ field:'name', title:'地图名称' },
				{ field:'mapUrl', title:'引擎地址' },				
				{ field:'thumbnailUrl', title:'缩略图地址' },				
				{ command : [{ 
		        		name: 'destroy'
		        	},{
				         name : 'up',
				         text : '↑' ,
				         click: upFun
			        },{
				         name : 'down',
				         text : '↓' ,
				         click: downFun
			        }
			      ], title:'',width:'250px'}
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
				var $grid = $("#grid").data("kendoGrid");
				var values = $grid.dataItems();
				//this.get("grid_dataSource");
				var hasValues = [];
				var names = "" ;	
				var times = (new Date().getTime()) ;
				for (var i = 0; i < values.length; i++) {
					if (values[i].name == undefined  || values[i].mapUrl == undefined  || values[i].name.trim() == ""  || values[i].mapUrl.trim() == "" ) {
						layer.alert("必须填写值", 3);
						return;
					} else {
						if (hasValues.indexOf(values[i].name) >= 0) {
							layer.alert("存在重复参数名，请检查", 3);
							return;
						} else {
							hasValues.push(values[i].name);
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
		    var grid = $('#grid').data('kendoGrid'),dataItems = grid.dataItems() ;
		    grid.dataSource.insert({name:'',mapUrl:'',thumbnailUrl:''});
		    grid.refresh();
		});
	</script>
</body>
</html>