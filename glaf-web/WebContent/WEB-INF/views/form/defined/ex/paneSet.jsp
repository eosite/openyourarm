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
	function rushGrid(){
		$("#grid").data("kendoGrid").dataSource.read();
	}
	var dataIndex ;
	//区域带
	function showExtentWin(e) {
		var grid = $('#grid').data("kendoGrid");
		var $tr = $(e).parents('tr');
		dataIndex = $tr.index();
		var dataItem = grid.dataItem($tr);
		$.merge(grid2Source ,dataItem.axisPlotBands||[]) ;
		$("#grid2").data("kendoGrid").dataSource.read();
		$('#extentWin').data("kendoWindow").open();
	}
	
	gridSource = [];
	grid2Source = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	if(objsource){
		gridSource = $.merge(gridSource,JSON.parse(objsource)[0].values?JSON.parse(objsource)[0].values:[]);
	}
	
	$(document).ready(function() {
		$("#grid").kendoGrid({
			dataSource : gridSource,
			editable : true,
			resizable: true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'startAngle',
				headerTemplate : '<span class="tooltip" title="极坐标或者仪表盘的始端点角度，以度来计算，且北为0度的方式给出。 默认值：0">刻度开始角度</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			}, {
				field : 'endAngle',
				headerTemplate : '<span class="tooltip" title="极坐标或者仪表盘的末端点角度，以度来计算，且北为0度的方式给出。默认值：StartAngle + 360">刻度结束角度</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			}, {
				field : 'backOpacity',
				headerTemplate : '<span class="tooltip" title="选中表示背景颜色透明,背景色失效">透明背景</span>',
				template : function(dataItem){
					return dataItem.backOpacity?"透明":"不透明" ;
				},
				editor : function(container,options){
					 $("<input type='checkbox'/>").attr({'name':options.field}).appendTo(container);
				}
			},{
				field : 'background',
				headerTemplate : '<span class="tooltip" title="仪表盘的背景颜色">背景色</span>',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.background+"'></div>";
				},
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 var _index = $tr.index() ;
					 $("<input/>").attr({'name':options.field,style:'width:50px'}).appendTo(container).kendoColorPicker({buttons: false/*,opacity: true*/});
				}
			}, {
				field : 'size',
				headerTemplate : '<span class="tooltip" title="面板的尺寸大小，要么是一个数字定义的像素，要么是一个百分比定义的图像面积百分比大小。">面板尺寸</span>',
			}, {
				field : 'centerX',
				headerTemplate : '<span class="tooltip" title="极坐标或者角度仪表盘的中心，既是给出的一组[x,y]的定位。位置可以通过整数或者百分比来设定，整数会被转换为像素；百分比是根据绘图区的尺寸来设定。">X坐标</span>',
			}, {
				field : 'centerY',
				headerTemplate : '<span class="tooltip" title="极坐标或者角度仪表盘的中心，既是给出的一组[x,y]的定位。位置可以通过整数或者百分比来设定，整数会被转换为像素；百分比是根据绘图区的尺寸来设定。">Y坐标</span>',
			},{
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = "" ;
				for (var i = 0; i < values.length; i++) {
					if ((values[i].startAngle == undefined || values[i].startAngle == "") && (values[i].endAngle == undefined || values[i].endAngle == "") 
						&& (values[i].background == undefined || values[i].background.trim() == "") && (values[i].size == undefined || values[i].size.trim() == "") 
						&& (values[i].centerX == undefined || values[i].centerX.trim() == "") && (values[i].centerY == undefined || values[i].centerY.trim() == "") ) {
						layer.alert("不能有空行", 3);
						return;
					} 
				}
				var retAry = [] ;
				var retObj = {} ;
				if(values.length){
					names = "仪表盘设置" ;
					retObj.values = values ;
					retObj.name = names ;
				}
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
		
		
		
		$(".tooltip").kendoTooltip({
	        animation: {
	          open: {
	            duration: "1000"
	          }
	        }
	    });
	});
</script>
</head>
<body>
	<div id="grid"></div>
</body>
</html>