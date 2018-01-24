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
							axisVisible : {
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
			columns : [ {
				field : 'title',
				headerTemplate : '<span class="tooltip" title="这是轴标题">轴标题</span>',
			}, {
				field : 'labelsRotation',
				title : '轴标签旋转角度',
				headerTemplate : '<span class="tooltip" title="轴标签的旋转角度，默认为水平">轴标签旋转角度</span>',
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({});
				}
			}, 
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
			},
			{
				field : 'allowDecimals',
				title : '轴刻度允许小数',
				headerTemplate : '<span class="tooltip" title="轴刻度是否允许小数">小数</span>',
				template : function(dataItem){
					return dataItem.allowDecimals?"允许":"不允许" ;
				}
			}, {
				field : 'opposite',
				title : '第二轴是否对面',
				headerTemplate : '<span class="tooltip" title="此功能用于多轴的情况，用于判断次轴是否在主轴的对面">次轴位置</span>',
				template : function(dataItem){
					return dataItem.opposite?"是":"否" ;
				}
			}, {
				field : 'alternateGridColor',
				title : '隔行颜色',
				headerTemplate : '<span class="tooltip" title="背景网格的隔行显示颜色">隔行颜色</span>',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.alternateGridColor+"'></div>";
				},
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 var _index = $tr.index() ;
					 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
				}
			},{
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
			},{
				field : 'axisPlotBands',
				title : '区域带',
				headerTemplate : '<span class="tooltip" title="区域带，用于标不同区域范围的颜色预警标识">区域带</span>',
				editor : function(container,options){
				 	 var $tr = $(container).parents('tr') ;
				 	 dataIndex = $tr.index() ;
					 $("<input/>").attr({
						 'name':options.field,
						 'class' : 'k-input k-textbox'
					 }).appendTo(container).end().on('click',function(e){
						var length = grid2Source.length ;
						for(var i= (length-1);i>=0 ; i--){
							grid2Source.splice(i, 1);
						}
						if(options.model.axisPlotBands!=""){
							$.merge(grid2Source,JSON.parse(options.model.axisPlotBands));
						} 
						$("#grid2").data("kendoGrid").dataSource.read();
						$('#extentWin').data("kendoWindow").open();
					 });
				}
			}, {
				field : 'axisVisible',
				title : '是否显示',
				headerTemplate : '<span class="tooltip" title="是否显示">是否显示</span>',
				template : function(dataItem){
					return dataItem.axisVisible?"是":"否" ;
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