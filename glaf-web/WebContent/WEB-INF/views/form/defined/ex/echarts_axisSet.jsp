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
		gridSource = $.merge(gridSource,JSON.parse(objsource)[0].values);
	}
	
	$(document).ready(function() {
		$('#extentWin').kendoWindow({
			modal : true,
			pinned : true,
			width : "630px",
			height : "345px",
			title : "区域带",
			actions : [ "Close" ],
			visible : false,
			position : {
				top : 10,
				left : 10
			}
		});
		
		$("#grid").kendoGrid({
			dataSource : {
				autoSync : true ,
				data : gridSource,
				schema : {
					model : {
						id : 'id',
						fields : {
							title : {
								'editable' : true,
								type : 'string'
							},
							labelsRotation : {
								'editable' : true,
								type : 'string'
							},
							ceiling : {
								'editable' : true,
								type : 'string'
							},
							floor : {
								'editable' : true,
								type : 'string'
							},
							yMax : {
								'editable' : true,
								type : 'string'
							},
							yMin : {
								'editable' : true,
								type : 'string'
							},
							allowDecimals : {
								editable : true,
								type : 'boolean',
								'defaultValue' : true 
							},
							opposite : {
								editable : true,
								type : 'boolean',
								'defaultValue' : false 
							},
							alternateGridColor : {
								'editable' : true,
								type : 'string'
							},
							axisPlotBands : {
								'editable' : true,
								type : 'string'
							},
							axisWeight : {
								editable : true,
								type : 'boolean',
								'defaultValue' : true 
							},axisStyle : {
								editable : true,
								type : 'boolean',
								'defaultValue' : true 
							}
						}
					}
				}
			},
			editable : true,
			resizable: true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ 
			{
				field : 'spiltNumber',
				title : '刻度数量',
				headerTemplate : '<span class="tooltip" title="允许最高的自动计算的坐标轴刻度极端值">刻度数量</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			},
			{
				field : 'ceiling',
				title : '最大刻度',
				headerTemplate : '<span class="tooltip" title="允许最高的自动计算的坐标轴刻度极端值">最大刻度</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			}, {
				field : 'floor',
				title : '最小刻度',
				headerTemplate : '<span class="tooltip" title="允许最高的自动计算的坐标轴刻度极端值">最小刻度</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			},
			{
				field : 'radius',
				title : '宽度',
				headerTemplate : '<span class="tooltip" title="允许最高的自动计算的坐标轴刻度极端值">宽度</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			},
			{
				field : 'yMax',
				title : '轴的最大值',
				headerTemplate : '<span class="tooltip" title="如果是空则最大值被自动计算">轴的最大值</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			}, {
				field : 'yMin',
				title : '轴的最小值',
				headerTemplate : '<span class="tooltip" title="如果是空则最小值被自动计算">轴的最小值</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			}, 
			{
				field : 'xLie',
				title : '轴水平距离',
				headerTemplate : '<span class="tooltip" title="如果是空则最小值被自动计算">轴水平距离</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			},{
				field : 'yLie',
				title : '轴垂直距离',
				headerTemplate : '<span class="tooltip" title="如果是空则最小值被自动计算">轴垂直距离</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			},{
				field : 'fontSize',
				title : '字体大小',
				headerTemplate : '<span class="tooltip" title="如果是空则最大值被自动计算">字体大小</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			},
			 {
				field : 'textColor',
				title : '轴字体颜色',
				headerTemplate : '<span class="tooltip" title="轴字体颜色">轴字体颜色</span>',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.textColor+"'></div>";
				},
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 var _index = $tr.index() ;
					 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
				}
			}, {
				field : 'axisWeight',
				title : '是否加粗',
				headerTemplate : '<span class="tooltip" title="是否显示">是否加粗</span>',
				template : function(dataItem){
					return dataItem.axisWeight?"是":"否" ;
				}
			}, 
			{
				field : 'axisStyle',
				title : '字体倾斜',
				headerTemplate : '<span class="tooltip" title="是否显示">是否倾斜</span>',
				template : function(dataItem){
					return dataItem.axisStyle?"是":"否" ;
				}
			},{
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = "" ;	
				for (var i = 0; i < values.length; i++) {
				/* 	if (values[i].title == undefined 
						 || values[i].title.trim() == "" ) {
						layer.alert("必须填标题", 3);
						return;
					} else { */
						names += values[i].title+"," ;
					/* } */
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
		
		
		$("#grid2").kendoGrid({
			dataSource : grid2Source,
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'from',
				title : '开始值',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			}, {
				field : 'to',
				title : '结束值',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
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
				for (var i = 0; i < values.length; i++) {
					if (values[i].from == undefined || values[i].to == undefined  || values[i].color == undefined 
						 || values[i].color == "" ) {
						layer.alert("必须填写值", 3);
						return;
					} 
				}
				var gridddd = $('#grid').data("kendoGrid") ;
				var $tr = $('#grid tbody tr').eq(dataIndex);
				$tr.find('td').eq(8).html(JSON.stringify(values));
				gridddd.dataItem($tr).axisPlotBands = JSON.stringify(values) ;
				$('#extentWin').data("kendoWindow").close();
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
	<div id="extentWin">
		<div id="grid2"></div>
	</div>
</body>
</html>