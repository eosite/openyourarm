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
	<div id="grid"></div>
	<script type="text/javascript">
		var dataSource = [];
		var objsource = window.parent.$('#'+objelementId).val() ;
		if(objsource){
			dataSource = JSON.parse(objsource)[0].values;
		}
		$("#grid").kendoGrid({
			dataSource : {
				autoSync : true ,
				data : dataSource,
				schema : {
					model : {
						fields : {
							from : {
								'editable' : true,
								type : 'number'
							},
							to : {
								'editable' : true,
								type : 'number'
							},
							color : {
								'editable' : true,
								type : 'string'
							}
						}
					}
				}
			},
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'from',
				title : '开始值',
			}, {
				field : 'to',
				title : '结束值',
			}, {
				field : 'color',
				title : '颜色',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.color+"'></div>";
				},
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 var _index = $tr.index() ;
					 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
				}
			}, {
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = "" ;	
				for (var i = 0; i < values.length; i++) {
					if (values[i].from == undefined || values[i].to == undefined  || values[i].color == undefined 
						 || values[i].color.trim() == "" ) {
						layer.alert("必须填写值", 3);
						return;
					} else {
						names += values[i].color+"," ;
					}
				}
				var retAry = [] ;
				var retObj = {} ;
				retObj.values = values ;
				retObj.name = names ;
				retAry.push(retObj);
				window.parent.$('#'+nameElementId).val(names);
				window.parent.$('#'+objelementId).val(JSON.stringify(retAry));
				parent.layer.close(parent.layer.getFrameIndex());
			},edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});
	</script>
</body>
</html>