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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<style type="text/css">
.ztree li span.button.tree_folder_ico_open{margin-right:2px; background: url(${contextPath}/images/folder-open.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_close{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_docu{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.tree_leaf_ico_open{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_close{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_docu{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.k-tabstrip-wrapper{
	height: 502px;
}
</style>
<script type="text/javascript">
var selectedTabStrip ;
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	var paramElementId = '${param.paramElementId}';
	var source = [],
		source2 = [],
		source3 = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	var params = window.parent.$('#'+paramElementId).val() ; //控件输入参数
	if(objsource){
		var objAry = JSON.parse(objsource),
			objValues = objAry[0].values;
		source = objValues.icon;
		source2 = objValues.check;
		source3 = objValues.text;
	}
	
	var columnValues = [] ;
	if(params){
		var data = JSON.parse(params);
		for (var i = 0; i < data.length; i++) {
			var columns = data[i].columns;
			for(var j=0;j<columns.length;j++){
				var val = {};
				var param = columns[j];
				val.text = param.tableNameCn+"->"+param.title ;
				val.value = param.columnName ;
				columnValues.push(val);
			}
		}
	}
	
	//返回表达式执行函数
	function retExpression(data) {
		var grid = $("#grid").data("kendoGrid");
		if(___index___ == 1){
			grid = $("#grid2").data("kendoGrid");
		}else if(___index___ == 2){
			grid = $("#grid3").data("kendoGrid");
		}
		var dataItem = grid.dataItem(grid.select());
		dataItem.expression = data.expActVal;
		dataItem.expdata = JSON.stringify(data);
		grid.editCell();
	}
	//获取参数
	function getExpression() {
		var expressionData = [];
		if(params){
			var paramsJsons = JSON.parse(params);
			for (var i = 0; i < paramsJsons.length; i++) {
				var selectColumnParam = paramsJsons[i].columns;
				for(var j=0;j<selectColumnParam.length;j++){
					var express = {};
					var param = selectColumnParam[j];
					express.t = param.title;
					express.dType = "string"/*FieldType*/;
					express.value = param.value;
					express.code = param.code;
					express.name = param.title;
					expressionData.push(express); 
				}
			}
		}
		/* for (var i = 0; i < selectColumns.length; i++) {
			var express = {};
			var selectFieldRow = selectColumns[i];
			express.t = selectFieldRow.title;
			express.dType = getfieldTypeValue(selectFieldRow.FieldType);
			express.code = selectFieldRow.code;
			express.value = selectFieldRow.value;
			express.name = selectFieldRow.title;
			expressionData.push(express);
		} */
		return expressionData;
	}
	//初始化参数
	function initExpressionFn() {
		var grid = $("#grid").data("kendoGrid");
		if(___index___ == 1){
			grid = $("#grid2").data("kendoGrid");
		}else if(___index___ == 2){
			grid = $("#grid3").data("kendoGrid");
		}
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.expdata);
	}
	//表达式定义
	function expEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		var _href = "${pageContext.request.contextPath}/mx/expression/defined/view?category=front&retFn=retExpression" + "&getFn=getExpression&initExpFn=initExpressionFn";
		$(input).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}

	// html编辑器获取变量方法
	function getVarFn() {
		//获取字典类型FieldTypeControl
		var expressionData = [];
		/* for (var i = 0; i < selectColumns.length; i++) {
			var express = {};
			var selectFieldRow = selectColumns[i];
			express.t = selectFieldRow.title;
			express.dType = getfieldTypeValue(selectFieldRow.FieldType);
			express.code = selectFieldRow.code != null ? selectFieldRow.code.replace("~F{", "~V{") : "";
			express.value = selectFieldRow.value != null ? selectFieldRow.value.replace("~F{", "~V{") : "";
			express.name = selectFieldRow.title;
			expressionData.push(express);
		} */
		return expressionData;
	}
	// html编辑器获取参数方法
	function getParamFn() {
		return [];
	}
	// html编辑器回调函数
	function retHtmlFn(data) {
		if (data) {
			var grid = $("#grid").data("kendoGrid");
			if(___index___ == 1){
				grid = $("#grid2").data("kendoGrid");
			}else if(___index___ == 2){
				grid = $("#grid3").data("kendoGrid");
			}
			var dataItem = grid.dataItem(grid.select());
			dataItem.htmlExpression = data.htmlVal;
			dataItem.htmldata = JSON.stringify(data);
			grid.editCell();
		}
	}
	// html编辑器回显内容 
	function initHTMLFn() {
		var grid = $("#grid").data("kendoGrid");
		if(___index___ == 1){
			grid = $("#grid2").data("kendoGrid");
		}else if(___index___ == 2){
			grid = $("#grid3").data("kendoGrid");
		}
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.htmldata);
	}
	//HTML样式定义定义
	function htmlExpEditor(container, options) {
		if(options.model.model == "custom"){
			var id = "expInput_" + parseInt(Math.random() * 1000);
			var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
			input.appendTo(container);
			var _href = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn&"
					+ "getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
			$(input).bind({
				"click" : function() {
					window.open(_href);
				}
			});
		}else{
			options.model[options.field] = "" ;
		}
	}
	
	//选取图片	
	function picUrlTextboxEditor(container, options) {
		var id = "picInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" data-bind="value:'+options.field+'" />');
		input.appendTo(container);

		var dialogWidth = 1024;
		var dialogHeight = 600;
		var url = "${pageContext.request.contextPath}/mx/isdp/global/image/choose?elementId=" + id;
		$(input).bind({
			"click" : function() {
				if (!!window.ActiveXObject || "ActiveXObject" in window) {
					//IE
					window.showModalDialog(url, self, "edge:raised;scroll:0;status:0;help:0;resizable:0;dialogWidth:" + dialogWidth + "px;dialogHeight:" + dialogHeight + "px;center:true", true);
				} else {
					var f = "height=" + dialogHeight + ",width=" + dialogWidth + ",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=500,left=500,resizable=no,modal=yes,dependent=yes,dialog=yes,minimizable=no";
					window.open(url, self, f, true);
				}
			},
			"blur" : function() {
				//获取当前选中行
				var grid = $("#grid").data("kendoGrid");
				var data = grid.dataItem(grid.select());
				if(input.val()){
					data.icon = input.val() ;
				}else{
					data.icon = "" ;
				}
			}
		});
	}
	//输入输出参数定义
	function inOutParamEditor(container, options) {
		var input = $('<input type="text" class="k-textbox" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		$('#hidParam').val(options.model.param);
		
		$(input).bind({
			"click" : function() {
				var link = '${pageContext.request.contextPath}/mx/form/defined/param/jsgis_outInParam?' + $.param({
					pageId : '${param.pageId}',
					eleType :'${param.eleType}',
					eleId : 'hidParam',
					fn : "initInOutParameter"
				});
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "输入&输出关系定义",
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['',''],
					fadeIn: 100,
					area: ['980px', '500px'],
					iframe: {src: link}
				});
			}
		});
		
		
	}
	
	function retParamFn(data) {
		if (data) {
			var grid = $("#grid").data("kendoGrid");
			if(___index___ == 1){
				grid = $("#grid2").data("kendoGrid");
			}else if(___index___ == 2){
				grid = $("#grid3").data("kendoGrid");
			}
			var dataItem = grid.dataItem(grid.select());
			//dataItem.param = JSON.stringify(data.datas);
			//dataItem.paramName = data.name;
			//grid.editCell();
			dataItem.set("param", JSON.stringify(data));
			dataItem.set("paramName", data[0].name);
			grid.dataSource.sync();
		}
	}
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
	<input id='hidParam' type='hidden' />
	<div id="tabstrip" style="height: 500px;">
		<ul>
        	<li class="k-state-active">图标</li>
        	<li>选择框</li>
        	<li>文本样式</li>
    	</ul>
    	<div class="k-state-active">
    		<div id="grid"></div>
			<div style="color: red;font-size: 18px;">
				注意 ：<br/>
				1、模式： ⒈级别->为树展开的级别（表达式中请输入节点的级别,从1开始）⒉自定义->为自定义条件显示不同的图标 <br/>
				2、样式类型: ⒈打开/关闭->则表示打开和关闭图标都一样 ⒉打开->则表示展开节点显示的图标 ⒊关闭->表示收缩节点显示的图标  <br/>
				   以上只针对父节点 ⒋子节点 表示子节点显示图标<br/>
				3、优先级 级别<自定义<节点（即元素自己显示的图标）
			</div>
		</div>
    	<div>
    		<div id="grid2"></div>
			<div style="color: red;font-size: 18px;">
				注意 ：<br/>
				1、此功能只有在开启选择框模式下有效
			</div>
    	</div>
    	<div>
    		<div id="grid3"></div>
			<div style="color: red;font-size: 18px;">
				注意 ：<br/>
				1、模式： ⒈级别->为树展开的级别（表达式中请输入节点的级别,从1开始）⒉自定义->为自定义条件显示节点的样式 <br/>
				2、优先级  级别<自定义
			</div>
    	</div>
	</div>
	<div id="toolbar" style="height: 32px;padding-top: 5px;padding-right: 10px;text-align: right;">
		
	</div>

	<script type="text/javascript">
	$("#toolbar").kendoToolBar({
        resizable: false,
        items: [
            { type: "button", text: "保存",icon:"save",click: function(){
				var values = $("#grid").data("kendoGrid").dataItems();
				for (var i = 0; i < values.length; i++) {
					if (!values[i].expression || !values[i].icon || !values[i].model || !values[i].type) {
						layer.alert("必须填写值", 3);
						return;
					}
				}
				var values2 = $("#grid2").data("kendoGrid").dataItems();
				for (var i = 0; i < values2.length; i++) {
					if (!values2[i].expression || !values2[i].type) {
						layer.alert("必须填写值", 3);
						return;
					}
				}
				var values3 = $("#grid3").data("kendoGrid").dataItems();
				for (var i = 0; i < values3.length; i++) {
					if (!values3[i].expression || !values3[i].model ) {
						layer.alert("必须填写值", 3);
						return;
					}
				}
				if(!values.length && !values2.length && !values3.length){
					window.parent.$('#' + nameElementId).val("");
					window.parent.$('#' + objelementId).val("");
				}else{
					window.parent.$('#' + nameElementId).val("数据样式定义");
					window.parent.$('#' + objelementId).val(JSON.stringify([{name:"数据样式定义",values:{
						icon:values,
						check:values2,
						text:values3
					}}]));
				}
				parent.layer.close(parent.layer.getFrameIndex());
            }},
            { type: "button", text: "关闭",icon:"close",click:function(){
            	parent.layer.close(parent.layer.getFrameIndex());
            }}
        ]
    });
    var ___index___ =  0 ;
	$("#tabstrip").kendoTabStrip({
		select: function(e){
			___index___ = $(e.item).index();
		}
	});
	
		//初始化图片链接grid
		$("#grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : source,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : true,
								type : 'string'
							},
							model : {
								editable : true,
								type : 'string'
							},
							type: {
								editable : true,
								type : 'string'
							},
							expression : {
								editable : true,
								type : 'string'
							},
							icon : {
								editable : true,
								type : 'string'
							},
							expdata: {
								editable : true,
								type : 'string'
							},
						}
					}
				}
			},
			height : 300,
			sortable : true,
			editable : true,
			selectable : "rows",
			resizable : true,
			scrollable :true,
			toolbar : [ 'create'/*, 'save' */],
			columns : [{
				field : 'name',
				title : '名称',
				headerTemplate : '<span class="tooltip" title="名称">名称</span>',
				width : '100px',
			},{
				field : 'model',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="模式">模式</span>',
				values : [{text:"级别",value:"level"},{text:"自定义",value:"custom"}],
				title : '样式类型',
			}, {
				field : 'expression',
				title : '表达式定义',
				headerTemplate : '<span class="tooltip" title="表达式定义">表达式定义</span>',
				editor : expEditor,
				width : '80px',
			}, {
				field : 'type',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="样式类型">样式类型</span>',
				values : [{text:"打开/关闭",value:"icon"},{text:"打开",value:"iconOpen"},{text:"关闭",value:"iconClose"},{text:"子节点",value:"icon_"}],
				title : '样式类型',
			},{
				field : 'icon',
				title : '显示图标',
				headerTemplate : '<span class="tooltip" title="显示图标">显示图标</span>',
				editor : picUrlTextboxEditor,
				width : '80px',
			},{
				command : [{ 
	        		name: "destroy" 
	        	},{
			         name : "up",
			         text : "↑" ,
			         click: upFun
		        },{
			         name : "down",
			         text : "↓" ,
			         click: downFun
		        }
		      ],
				title : '',
				width : '225px'
			} ],
	
			edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});

		$("#grid2").kendoGrid({
			dataSource : {
				autoSync : true,
				data : source2,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : true,
								type : 'string'
							},
							type: {
								editable : true,
								type : 'string'
							},
							expression : {
								editable : true,
								type : 'string'
							},
							expdata: {
								editable : true,
								type : 'string'
							},
						}
					}
				}
			},
			height : 300,
			sortable : true,
			editable : true,
			selectable : "rows",
			resizable : true,
			scrollable :true,
			toolbar : [ 'create'/*, 'save' */],
			columns : [{
				field : 'name',
				title : '名称',
				headerTemplate : '<span class="tooltip" title="名称">名称</span>',
				width : '100px',
			},/*{
				field : 'model',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="模式">模式</span>',
				values : [{text:"级别",value:"level"},{text:"自定义",value:"custom"}],
				title : '样式类型',
			}, */{
				field : 'expression',
				title : '表达式定义',
				headerTemplate : '<span class="tooltip" title="表达式定义">表达式定义</span>',
				editor : expEditor,
				width : '80px',
			},{
				field : 'type',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="类型">类型</span>',
				values : [{text:"禁用",value:"disabled"},{text:"选中",value:"check"}],
				title : '样式类型',
			},{
				command : [{ 
	        		name: "destroy" 
	        	},{
			         name : "up",
			         text : "↑" ,
			         click: upFun
		        },{
			         name : "down",
			         text : "↓" ,
			         click: downFun
		        }
		      ],
				title : '',
				width : '225px'
			} ],
			edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});

		$("#grid3").kendoGrid({
			dataSource : {
				autoSync : true,
				data : source3,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : true,
								type : 'string'
							},
							model : {
								editable : true,
								type : 'string'
							},
							expression : {
								editable : true,
								type : 'string'
							},
							expdata: {
								editable : true,
								type : 'string'
							},
							htmlExpression : {
								editable : true,
								type : 'string'
							},
							htmldata: {
								editable : true,
								type : 'string'
							},
							color: {
								editable : true,
								type : 'string'
							}
						}
					}
				}
			},
			height : 300,
			sortable : true,
			editable : true,
			selectable : "rows",
			resizable : true,
			scrollable :true,
			toolbar : [ 'create'/*, 'save' */],
			columns : [{
				field : 'name',
				title : '名称',
				headerTemplate : '<span class="tooltip" title="名称">名称</span>',
				width : '100px',
			},{
				field : 'model',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="模式">模式</span>',
				values : [{text:"级别",value:"level"},{text:"自定义",value:"custom"}],
				title : '样式类型',
			},{
				field : 'expression',
				title : '表达式定义',
				headerTemplate : '<span class="tooltip" title="表达式定义">表达式定义</span>',
				editor : expEditor,
				width : '80px',
			}, {
				field : 'htmlExpression',
				title : '样式定义',
				headerTemplate : '<span class="tooltip" title="样式定义">样式定义</span>',
				editor : htmlExpEditor,
				width : '80px',
			},{
				field : 'color',
				title : '样式定义',
				headerTemplate : '<span class="tooltip" title="样式定义">样式定义</span>',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.color+"'></div>";
				},
				editor : function(container,options){
					if(options.model.model == "level"){
					 	$("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
					}else{
						options.model[options.field] = "" ;
					}
				},
				width : '80px',
			},{
				command : [{ 
	        		name: "destroy" 
	        	},{
			         name : "up",
			         text : "↑" ,
			         click: upFun
		        },{
			         name : "down",
			         text : "↓" ,
			         click: downFun
		        }
		      ],
				title : '',
				width : '225px'
			} ],
			edit : function(e) {
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

	   
	</script>
</body>
</html>