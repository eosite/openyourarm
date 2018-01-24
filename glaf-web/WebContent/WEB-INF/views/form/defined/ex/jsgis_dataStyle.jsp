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
</style>
<script type="text/javascript" src="${contextPath}/webfile/js/jquery.cookie.js" ></script>
<script type="text/javascript">
	var mtxx = {
		pageCategory : $.cookie('pageCategory')
	};
var selectedTabStrip ;
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	var paramElementId = '${param.paramElementId}';
	var source = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	var params = window.parent.$('#'+paramElementId).val() ; //控件输入参数
	if(objsource){
		source = JSON.parse(objsource);
	}
	
	var columnValues = [] ;
	if(params){
		var data = JSON.parse(params);
		for (var i = 0; i < data.length; i++) {
			var columns = data[i].selectColumns;
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
				var selectColumnParam = paramsJsons[i].selectColumns;
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
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.expdata);
	}
	//表达式定义
	function expEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
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
			var dataItem = grid.dataItem(grid.select());
			dataItem.htmlExpression = data.htmlVal;
			dataItem.htmldata = JSON.stringify(data);
			grid.editCell();
		}
	}
	// html编辑器回显内容 
	function initHTMLFn() {
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.htmldata);
	}
	//HTML样式定义定义
	function htmlExpEditor(container, options) {
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
	}
	//选取关联页面
	function contextUrlTextboxEditor(container, options) {
		var id = "contextUrlInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" data-bind="value:'+options.field+'" />');
		input.appendTo(container);

		$(input).bind({
			"click" : function() {
				//选取关联页面
				$.layer({
					type : 1,
					maxmin : true,
					shadeClose : true,
					title : "请选择页面",
					closeBtn : [ 0, true ],
					shade : [ 0, '#000' ],
					border : [ 2, 0.3, '#000' ],
					fadeIn : 100,
					area : [ "525px", "450px" ],
					btns : 2,
					btn : [ '确定', '取消' ],
					page : {
						dom : "#selectPage"
					},
					yes : function(index) {
						if(selectedTabStrip=="页面"){
							var treeObj = $.fn.zTree.getZTreeObj("selectPageTree");
							var nodes = treeObj.getSelectedNodes();
							if (nodes.length == 0) {
								layer.alert("请先选择链接页面！", 3);
								return;
							} else {
								var node = nodes[0];
								var grid = $("#grid").data("kendoGrid");
								var data = grid.dataItem(grid.select());
								data.set("urlId", node.id);
								data.set("urlText", node.name);
								grid.dataSource.sync();
								layer.close(index);
							}
						}else if(selectedTabStrip=="图表"){
							var treeObj = $.fn.zTree.getZTreeObj("selectPageTree2");
							var nodes = treeObj.getSelectedNodes();
							if (nodes.length == 0) {
								layer.alert("请先选择图表！", 3);
								return;
							} else {
								var node = nodes[0];
								var grid = $("#grid").data("kendoGrid");
								var data = grid.dataItem(grid.select());
								data.set("urlId", node.id);
								data.set("urlText", node.name);
								grid.dataSource.sync();
								layer.close(index);
							}
						}
					}
				});
			},
			"blur" : function() {
				//获取当前选中行
				var grid = $("#grid").data("kendoGrid");
				var data = grid.dataItem(grid.select());
				//data.contextUrl = input.val();
			}
		});

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
	<div id="grid"></div>
	<div style="color: red;font-size: 18px;">
		注意 ：<br/>
		1、显示图标，图标宽度(默认20)，图标高度(默认20)  适用于 “点” 类型<br/>
		2、线条粗细(默认2)，线条颜色(默认红色) 适用于 “线” 类型 		<br/>
		3、弹出页面，窗口名称，页面宽度，页面高度 适用于 弹出窗事件，如果不填写弹出页面，则当前不会弹出窗口<br/>
		4、显示序号只适用于 “显示并隐藏其他事件” 中的  “点” 类型 <br/>
		5、显示序号字体偏移量格式为 （5,-4 ） 表示 往右偏移5个，往 下偏移4个像素<br/>
		6、显示序号和显示字段同时选中时优先使用显示序号
	</div>
	<div id="selectPage" style="display: none; width: 520px; height: 400px;">
		<div id="tabstrip" style="width: 500;height: 370px">
			<ul>
				<li>页面</li>
				<li>图表</li>
			</ul>
			<div style="width: 480px;height: 330px;">
				<div>模块：<div id="pageCategory"></div></div>
				<div class="ztree" id="selectPageTree" style="width: 100%; overflow: auto;"></div>
			</div>
			<div style="width: 480px;height: 330px;">
				<div class="ztree" id="selectPageTree2" style="width: 100%; overflow: auto;"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//初始化树
	var setting = {
		view : {
			showIcon : true,
			showLine : true,
			showTitle : false,
			selectedMulti : false
		},
		async : {
			enable : true,
			url : "${pageContext.request.contextPath}/mx/form/defined/getFormPageTree"
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId"
			}
		},
		callback : {
		//onClick : zTreeOnClick
		}
	};
	zTree = jQuery.fn.zTree.init(jQuery("#selectPageTree"), setting);
	
	function getUrl(treeId, treeNode) { 
		if(treeNode != null){ 
		    var param = "nodeId="+treeNode.id; 
		    return "${pageContext.request.contextPath}/rs/bi/chart/treeJson?"+param; 
		} 
		return "${pageContext.request.contextPath}/rs/bi/chart/treeJson?nodeCode=report_category"; 
	}

	//初始化树
	var setting2 = {
		async : {
			enable : true,
			url : getUrl
		}
	};
	zTree2 = jQuery.fn.zTree.init(jQuery("#selectPageTree2"), setting2);
	
	
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
							toffset: {
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
							iconWidth : {
								editable : true,
								type : 'number'
							},
							iconHeight : {
								editable : true,
								type : 'number'
							},
							lineWidth : {
								editable : true,
								type : 'number'
							},
							lineColor : {
								editable : true,
								type : 'string'
							},
							highlightWidth: {
								editable : true,
								type : 'number'
							},
							highlightColor: {
								editable : true,
								type : 'string'
							},
							flicker: {
								editable : true,
								type : 'string'
							},
							urlId : {
								editable : true,
								type : 'string'
							},
							urlText : {
								editable : true,
								type : 'string'
							},
							windowName : {
								editable : true,
								type : 'string'
							},
							windowWidth : {
								editable : true,
								type : 'string'
							},
							windowHeight : {
								editable : true,
								type : 'string'
							},
							param : {
								editable : true,
								type : 'string'
							},
							paramName : {
								editable : true,
								type : 'string'
							},
							column : {
								editable : true,
								type : 'string'
							},
							isShow : {
								editable : true,
								type : 'boolean'
							},
							isSort : {
								editable : true,
								type : 'boolean'
							},
							showColumn : {
								editable : true,
								type : 'string'
							},
							sortColor : {
								editable : true,
								type : 'string'
							},
							sortSize : {
								editable : true,
								type : 'number'
							},
							sotrOffset : {
								editable : true,
								type : 'string'
							}
						}
					}
				}
			},
			height : 400,
			sortable : true,
			editable : true,
			selectable : "rows",
			resizable : true,
			scrollable :true,
			toolbar : [ 'create', 'save' ],
			columns : [{
				field : 'name',
				title : '分组名称',
				headerTemplate : '<span class="tooltip" title="分组名称">分组名称</span>',
				width : '100px',
			}, {
				field : 'expression',
				title : '表达式定义',
				headerTemplate : '<span class="tooltip" title="表达式定义">表达式定义</span>',
				editor : expEditor,
				width : '80px',
			},{
				field : 'toffset',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="图形偏移量">图形偏移量</span>',
				title : '图形偏移量',
			},{
				field : 'icon',
				title : '显示图标',
				headerTemplate : '<span class="tooltip" title="显示图标">显示图标</span>',
				editor : picUrlTextboxEditor,
				width : '80px',
			}, {
				field : 'iconWidth',
				headerTemplate : '<span class="tooltip" title="图标宽度">图标宽度</span>',
				title : '图标宽度',
				width : '80px',
			}, {
				field : 'iconHeight',
				headerTemplate : '<span class="tooltip" title="图标高度">图标高度</span>',
				title : '图标高度',
				width : '80px',
			}, {
				field : 'lineWidth',
				headerTemplate : '<span class="tooltip" title="线条粗细">线条粗细</span>',
				title : '线条粗细',
				width : '80px',
			}, {
				field : 'lineColor',
				title : '线条颜色',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="线条颜色">线条颜色</span>',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.lineColor+"'></div>";
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
				},
			}, {
				field : 'highlightWidth',
				headerTemplate : '<span class="tooltip" title="高亮显示宽度">高亮显示宽度</span>',
				title : '高亮显示宽度',
				width : '80px',
			}, {
				field : 'highlightColor',
				headerTemplate : '<span class="tooltip" title="高亮显示颜色">高亮显示颜色</span>',
				title : '高亮显示颜色',
				width : '80px',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.highlightColor+"'></div>";
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
				},
			}, /* {
				field : 'flicker',
				title : '闪烁间隔',
			}, */ {
				field : 'urlText',
				headerTemplate : '<span class="tooltip" title="弹出页面">弹出页面</span>',
				title : '弹出页面',
				width : '120px',
				editor : contextUrlTextboxEditor
			},{
				field : 'windowName',
				headerTemplate : '<span class="tooltip" title="窗口名称">窗口名称</span>',
				title : '窗口名称',
				width : '80px',
			},{
				field : 'windowWidth',
				headerTemplate : '<span class="tooltip" title="页面宽度">页面宽度</span>',
				title : '页面宽度',
				width : '80px',
			},{
				field : 'windowHeight',
				headerTemplate : '<span class="tooltip" title="页面高度">页面高度</span>',
				title : '页面高度',
				width : '80px',
			},{
				field : 'paramName',
				title : '输入输出参数',
				headerTemplate : '<span class="tooltip" title="输入输出参数">输入输出参数</span>',
				editor : inOutParamEditor,
				width : '100px',
			},{
				field : 'column',
				title : '坐标信息列',
				headerTemplate : '<span class="tooltip" title="坐标信息列">坐标信息列</span>',
				values : columnValues,
				width : '180px',
			},{
				field : 'isShow',
				title : '默认显示',
				headerTemplate : '<span class="tooltip" title="默认显示">默认显示</span>',
				width : '80px',
				template : function(dataItem){
					return dataItem.isShow? "是" : "<p style='color:red'>否</p>";
				}
			},{
				field : 'isSort',
				title : '显示序号',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="显示序号">显示序号</span>',
				template : function(dataItem){
					return dataItem.isSort? "是" : "<p style='color:red'>否</p>";
				}
			},{
				field : 'showColumn',
				title : '显示字段',
				width : '180px',
				headerTemplate : '<span class="tooltip" title="显示字段">显示字段</span>',
				values : columnValues,
			},{
				field : 'sortColor',
				title : '序号字体颜色',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="序号字体颜色">序号字体颜色</span>',
				template : function(dataItem){
					return "<div style='width:50px;height:20px;background-color:"+dataItem.sortColor+"'></div>";
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoColorPicker({buttons: false});
				},
			},{
				field : 'sortSize',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="序号字体大小">序号字体大小</span>',
				title : '序号字体大小',
			},{
				field : 'sortOffset',
				width : '80px',
				headerTemplate : '<span class="tooltip" title="字体偏移量">字体偏移量</span>',
				title : '字体偏移量',
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
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = "";
				for (var i = 0; i < values.length; i++) {
					if (values[i].expression == undefined || values[i].expression == "" || values[i].name == undefined || values[i].name == "" || values[i].icon == undefined || values[i].icon == "") {
						layer.alert("必须填写值", 3);
						return;
					}
					names += values[i].name + ",";
					if (values[i].urlId) {
						values[i].id = values[i].urlId;
					}
				}
				window.parent.$('#' + nameElementId).val(names);
				window.parent.$('#' + objelementId).val(JSON.stringify(values));
				parent.layer.close(parent.layer.getFrameIndex());
			},
			edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});

		function onselect(e) {
			selectedTabStrip = $(e.item).find("> .k-link").text();
		}
		$("#tabstrip").kendoTabStrip({
			select : onselect
		}).data("kendoTabStrip").select(0);

		$(".tooltip").kendoTooltip({
	        animation: {
	          open: {
	            duration: "1000"
	          }
	        }
	    });

	    $("#pageCategory").kendoDropDownList({
			dataSource: new kendo.data.DataSource({
				transport: {
					read: {
						url: '${contextPath}/rs/form/formPageCategory/all',
						data: {
						},
						type: 'post',
						dataType: 'json',
					}
				}
			}),
			dataTextField: "name",
			dataValueField: "id",
			animation: false,
			change : function(e){
				var value = this.value() ;
				//$.cookie('pageCategory', value , { expires: 30 });
				mtxx.zTree = $.fn.zTree.getZTreeObj("selectPageTree");
				mtxx.zTree.setting.async.otherParam = {
					pageCategory : value 
				};
				mtxx.zTree.reAsyncChildNodes(null, 'refresh');
			}
		});

		if(mtxx.pageCategory ){
			$("#pageCategory").data("kendoDropDownList").value(mtxx.pageCategory);
		}
	</script>
</body>
</html>