<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<style type="text/css">
.ztree li span.button.tree_folder_ico_open{margin-right:2px; background: url(${contextPath}/images/folder-open.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_close{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_docu{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.tree_leaf_ico_open{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_close{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_docu{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
<script type="text/javascript">
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	function rushGrid(){
		$("#grid").data("kendoGrid").dataSource.read();
	}
	var dataIndex ;
	function buildSource(type,urlType) {
		if(urlType){
			urlType = "getDictByCodeOld" ;
		}else{
			urlType = "getDictByCode" ;
		}
		return new kendo.data.DataSource({//编辑器数据源
			type : "json",
			transport : {
				read : {
					//contentType : "application/json",
					url : "${pageContext.request.contextPath}/mx/form/defined/"+urlType+"?code=" + type,
					type : "POST",
					dataType : "JSON"
				},
				parameterMap : function(options, operation) {
					if (operation == "read") {
						return options;
					}
				}
			}
		});
	}
	var clickSource = buildSource('click');
	clickSource.fetch();
	
	//选取关联页面
	function widgetEditor(container, options) {
		 $("<input class='k-textbox'/>").attr('name',options.field).appendTo(container).on("click",function(){
			 var treeObj = $.fn.zTree.getZTreeObj("ztree");
			 treeObj.checkAllNodes(false);
			 var arystr = options.model[options.field.replace("Name","")] ;
			 if(arystr){
				 var ary = JSON.parse(arystr),
			     ns =  treeObj.transformToArray(treeObj.getNodes()),n;
				 for(var j=0;j<ns.length;j++){
					 n = ns[j];
					 for(var i=0;i<ary.length;i++){
						 if(n.id == ary[i].id){
							 treeObj.checkNode( n, true, false, false);
						 }
					 }
				 }
			 }
			 
			//选取关联页面
				$.layer({
					type : 1,
					maxmin : true,
					shadeClose : true,
					title : "请选择控件",
					closeBtn : [ 0, true ],
					shade : [ 0, '#000' ],
					border : [ 2, 0.3, '#000' ],
					fadeIn : 100,
					area : [ "300px", "450px" ],
					btns : 2,
					btn : [ '确定', '取消' ],
					page : {
						dom : "#selectPage"
					},
					yes : function(index) {
						var treeObj = $.fn.zTree.getZTreeObj("ztree");
						var nodes = treeObj.getCheckedNodes(); 
							//treeObj.getSelectedNodes();
						if (nodes.length == 0) {
							var grid = $("#grid").data("kendoGrid");
							var data = grid.dataItem(grid.select());
							data.set(options.field, "");
							data.set(options.field.replace("Name",""), "");
							grid.dataSource.sync();
							layer.close(index);
						} else {
							var names = "" ,retNodes = [];
							for(var i=0;i<nodes.length;i++){
								var node = nodes[i];
								names += node.name + "," ; 
								var d = $.extend(true, {}, node) ; 
								d.children="" ;
								retNodes.push(d);
							}
							var grid = $("#grid").data("kendoGrid");
							var data = grid.dataItem(grid.select());
							data.set(options.field, names);
							data.set(options.field.replace("Name",""), JSON.stringify(retNodes));
							grid.dataSource.sync();
							layer.close(index);
						}
					}
				});
		 });
	}
	//选取关联页面
	function widgetEditor2(container, options) {
		 var gid = container.closest("div[data-role=grid]")[0].id ;
		 $("<input class='k-textbox'/>").attr('name',options.field).appendTo(container).on("click",function(){
			 //清楚选中 
			 var treeObj = $.fn.zTree.getZTreeObj("ztree2");
			 treeObj.checkAllNodes(false);
			 var arystr = options.model[options.field.replace("Name","")] ;
			 if(arystr){
				 var ary = JSON.parse(arystr),
				 ns =  treeObj.transformToArray(treeObj.getNodes()),n;
				 for(var j=0;j<ns.length;j++){
					 n = ns[j];
					 for(var i=0;i<ary.length;i++){
						 if(n.id == ary[i].id){
							 treeObj.checkNode( n, true, false, false);
						 }
					 }
				 }
			 }
			//选取关联页面
				$.layer({
					type : 1,
					maxmin : true,
					shadeClose : true,
					title : "请选择控件",
					closeBtn : [ 0, true ],
					shade : [ 0, '#000' ],
					border : [ 2, 0.3, '#000' ],
					fadeIn : 100,
					area : [ "300px", "450px" ],
					btns : 2,
					btn : [ '确定', '取消' ],
					page : {
						dom : "#selectPage2"
					},
					yes : function(index) {
						var treeObj = $.fn.zTree.getZTreeObj("ztree2");
						var nodes = treeObj.getCheckedNodes(); 
							//treeObj.getSelectedNodes();
						if (nodes.length == 0) {
							var grid = $("#"+gid).data("kendoGrid");
							var data = grid.dataItem(grid.select());
							data.set(options.field, "");
							data.set(options.field.replace("Name",""), "");
							grid.dataSource.sync();
							layer.close(index);
						} else {
							var names = "" ;
							var retNodes = []
							for(var i=0;i<nodes.length;i++){
								var node = nodes[i];
								names += node.name + "," ;
								var d = $.extend(true, {}, node) ; 
								d.children="" ;
								retNodes.push(d);
							}
							var grid = $("#"+gid).data("kendoGrid");
							var data = grid.dataItem(grid.select());
							data.set(options.field, names);
							data.set(options.field.replace("Name",""), JSON.stringify(retNodes));
							grid.dataSource.sync();
							layer.close(index);
						}
					}
				});
		 });
	}
	
	//输入输出参数定义
	function inOutParamEditor(container, options) {
		$('#hidParam').val(options.model['param']);
		$("<input class='k-textbox'/>").attr('name',options.field).appendTo(container).on("click",function(){
			var link = '${pageContext.request.contextPath}/mx/form/defined/param/events_outInParam?' + $.param({
				pageId : '${param.pageId}',
				eleType :'${param.eleType}',
				eleId : 'hidParam',
				fn : "initInOutParameterByEvents"
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
		});
	}
	//获取输入参数
	function getParamFn(){
		var grid = $("#grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		var data = {} ;
		if(dataItem){
			data.inWidget = dataItem.inWidget ;
			var eventsGrid = $("#eventsGrid").data("kendoGrid");
			var dataItem2 = eventsGrid.dataItem(eventsGrid.select());
			if(dataItem2){
				data.outWidget = dataItem2.outWidget ;
			}
		}
		return data ;
	}
	
	//选择树返回参数
	function retParamFn(data) {
		if (data) {
			var grid = $("#eventsGrid").data("kendoGrid");
			var dataItem = grid.dataItem(grid.select());
			dataItem.set("param", JSON.stringify(data));
			dataItem.set("paramName", data[0].name);
			grid.dataSource.sync();
		}
	}
	
	//返回表达式执行函数
	function retExpression(data) {
		var grid = $("#eventsGrid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		dataItem.exp = data.expActVal;
		dataItem.expdata = JSON.stringify(data);
		grid.editCell();
	}
	
	//获取表达式参数
	function getExpParams(inParam,pid,expressionData){
		var items = inParam.items;
		for(var j=0;j<items.length;j++){
			var param = items[j];
			var subexpress = {};
			subexpress.id = param.id ? (items.pageId+param.id):(pid+"-"+j);
			if(subexpress.id == pid){
				subexpress.id = subexpress.id + j ;
			}
			subexpress.name = param.text;
			subexpress.pId = pid;
			subexpress.t = "";
			if(param.items){
				getExpParams(param,subexpress.id,expressionData)
			}else{
				subexpress.pageId = items.pageId ;
				subexpress.eleId = param.id ;
				subexpress.fnType = param.type;
				subexpress.lev = items.lev;
				if(param.code){
					subexpress.columnName = param.columnName ;
					subexpress.dType = param.FieldType;
					subexpress.code = param.code;
					subexpress.value = param.value;
					subexpress.isFn = false ;
				}else{
					subexpress.dType = "string";
					subexpress.code = "~F{"+items.pageId+"."+param.id+"."+param.type+"}";
					subexpress.value = "~F{"+inParam.text+"."+param.text+"}";
					subexpress.isFn = true;
				}
			}
			expressionData.push(subexpress); 
		}
	}
	function getOutParams(pageid,expressionData){
		var datas = parent.getParametersByPageId(pageid);
		var flag = true ;
		for(var i=0;i<datas.length;i++){
			var param = datas[i] ;
			if(flag){
				var pexpress = {} ;
				pexpress.id = param['PAGEID_'];
				pexpress.name = param['PAGENAME_'];
				flag = false ;
				expressionData.push(pexpress); 
			}
			var express = {} ;
			express.id = param['PAGEID_'] + param['NAME_'];
			express.name = param['TITLE_'];
			express.pId = param['PAGEID_'];
			expressionData.push(express); 
			
			var values = JSON.parse(param['VALUE_']) ;
			for(var j=0;j<values.length;j++){
				var val = values[j];
				var subexpress = {} ;
				subexpress.id = express.id+j;
				subexpress.dType = "string";
				subexpress.code = "~F{"+val.param+"."+val.param+"."+val.param+"}";
				subexpress.value = "~F{"+val.name+"}";
				subexpress.pId = express.id ;
				subexpress.name = val.name;
				expressionData.push(subexpress); 
			}
		}
	}
	//获取参数
	function getExpression2() {
		var expressionData = [];
		//${param.pageId}
		var egrid = $("#eventsGrid").data('kendoGrid');
		var dataItem = egrid.dataItem(egrid.select());
		var outs = JSON.parse(dataItem.outWidget);
		var pageids =[] ;
		for(var i=0;i<outs.length;i++){
			var out = outs[i];
			var pobj = JSON.parse(out.pObj) ;
			if(pobj.pageId && $.inArray(pobj.pageId,pageids)==-1){
				pageids.push(pobj.pageId);
			}
		}
		for(var i=0;i<pageids.length;i++){
			var pageid  =  pageids[i];
			getOutParams(pageid,expressionData);
		}
		return expressionData;
	}
	
	//获取参数
	function getExpression() {
		var expressionData = [];
		var inOutParams = parent.initInOutParameterByEvents(null,getParamFn().inWidget),inparams = inOutParams.menus['in'],inparam,i;
		if(inparams && inparams.length >0){
			for(i=0;i<inparams.length;i++){
				inparam = inparams[i];
				var express = {};
				express.id = inparam.items.pageId+inparam.id ;
				express.name = inparam.text;
				getExpParams(inparam,express.id,expressionData);
				expressionData.push(express); 
			}
		}
		return expressionData;
	}
	//初始化参数
	function initExpressionFn() {
		var grid = $("#eventsGrid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.expdata);
	}
	//表达式定义
	function expEditor(container, options) {
		var _href = "${pageContext.request.contextPath}/mx/expression/defined/view?category=front&retFn=retExpression" 
				+ "&getFn=getExpression&initExpFn=initExpressionFn";
		$("<input class='k-textbox'/>").attr('name',options.field).appendTo(container).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}
	
	//返回表达式执行函数
	function retCellExpression(data) {
		var grid = $("#eventsGrid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		dataItem.cellExp = data.expActVal;
		dataItem.cellExpdata = JSON.stringify(data);
		grid.editCell();
	}
	//初始化参数
	function initCellExpressionFn() {
		var grid = $("#eventsGrid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.cellExpdata);
	}
	//华表表达式定义
	function cellExpEditor(container, options) {
		var _href = "${pageContext.request.contextPath}/mx/expression/defined/view?category=front&retFn=retCellExpression" 
				+ "&getFn=getExpression&initExpFn=initCellExpressionFn";
		$("<input class='k-textbox'/>").attr('name',options.field).appendTo(container).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}
	
	var dataIndex ,eventsGridSource = [] ,
	gridSource = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	if(objsource){
		gridSource = $.merge(gridSource,JSON.parse(objsource)[0].values?JSON.parse(objsource)[0].values:[]);
	}
	function treeDataFilter(treeId, parentNode, childNodes){
		if (!childNodes)
			return null;
		for (var i = 0, l = childNodes.length,childNode; i < l; i++) {
			childNode = childNodes[i] ;
			if(childNode.icon){
				childNode.icon = "${pageContext.request.contextPath}"+childNode.icon;
			}
		}
		return childNodes;
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
	$(document).ready(function() {
		//初始化树
		var setting = {
			view : {
				showIcon : true,
				showLine : true,
				showTitle : false,
				selectedMulti : false
			},
			check : {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
			},
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId=${param.pageId}&showEvent=false",
				dataFilter : treeDataFilter
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			},
			callback : {
			//onClick : zTreeOnClick
			}
		};
		zTree = jQuery.fn.zTree.init(jQuery("#ztree"), setting);
		
		var setting2 = {
				view : {
					showIcon : true,
					showLine : true,
					showTitle : false,
					selectedMulti : false
				},
				check : {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "s", "N": "ps" }
				},
				async : {
					enable : true,
					url : "${pageContext.request.contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId=${param.pageId}&showEvent=true",
					dataFilter : treeDataFilter
				},
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pId"
					}
				},
				callback : {
				//onClick : zTreeOnClick
				}
			};
			zTree2 = jQuery.fn.zTree.init(jQuery("#ztree2"), setting2);
		
		$("#grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : gridSource,
				schema : {
					model : {
						id : 'id',
						fields : {
							cname : {
								editable:true
							},
							inWidgetName : {
								editable : true
							},
							inWidget : {
								editable : true
							},
							/* outWidgetName : {
								editable : true
							},
							outWidget : {
								editable : true
							}, */
							widgetName : {
								editable : true
							},
							widget : {
								editable : true
							},
							eventType : {
								editable : true,
								type : 'string'
							},
							widgetEvent : {
								editable : true,
								type : 'string'
							}
							/*, paramName : {
								editable : true ,
							},
							param : {
								editable : true ,
							}, */
						}
					}
				}
			},
			editable : true,
			resizable: true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'cname' ,
				headerTemplate : '<span class="tooltip" title="">名称</span>',
			},{
				field : 'inWidgetName',
				headerTemplate : '<span class="tooltip" title="参数输入的控件">输入控件</span>',
				editor : widgetEditor
			}, /* {
				field : 'outWidgetName',
				headerTemplate : '<span class="tooltip" title="">输出控件</span>',
				editor : widgetEditor2
			}, */ {
				field : 'widgetName',
				headerTemplate : '<span class="tooltip" title="">事件触发控件</span>',
				editor : widgetEditor2
			},{
				field : 'widgetEvent',
				headerTemplate : '<span class="tooltip" title="">输出控件定义</span>',
				template : '<button class="k-button" onclick="showEventsWin(this)">输出控件事件编辑器</button>'
			},/* {
				field : 'paramName',
				headerTemplate : '<span class="tooltip" title="">输入输出参数关系定义</span>',
				editor : inOutParamEditor
			}, */{
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
				var names = "",value,outWidget,param ;
				for (var i = 0; i < values.length; i++) {
					value = values[i] ;
					if ((value.widgetName == undefined || value.widgetName == "")) {
						layer.alert("不能有空行", 3);
						return;
					} 
					/* if(value.outWidget && value.param){
						outWidget = JSON.parse(value.outWidget) ;
						//param = JSON.parse(value.param) ;
						for(var k = 0;k<outWidget.length ;k++){
							var ow = outWidget[k];
							if(value.param.indexOf(ow.eName)==-1){
								layer.alert("输出控件[ "+ow.name+" ]事件更改，需要重新设置输入输出参数关系定义", 3);
								return ;
							} 
						}
					} */
				}
				var retAry = [] ;
				var retObj = {} ;
				if(values.length){
					names = "事件定义器" ;
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
		
		$("#eventsGrid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : eventsGridSource,
				schema : {
					model : {
						id : 'id',
						fields : {
							exp : {
								editable : true
							},
							expdata : {
								editable : true
							},
							cellExp : {
								editable : true
							},
							cellExpdata : {
								editable : true
							},
							outWidgetName : {
								editable : true
							},
							outWidget : {
								editable : true
							},
							paramName : {
								editable : true ,
							},
							param : {
								editable : true ,
							},
						}
					}
				}
			},
			editable : true,
			resizable: true,
			scrollable : false,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'outWidgetName',
				headerTemplate : '<span class="tooltip" title="">输出控件</span>',
				editor : widgetEditor2,
				width : '200px'
			},{
				field : 'exp',
				headerTemplate : '<span class="tooltip" title="">表达式定义</span>',
				editor : expEditor,
				width : '200px'
			},{
				field : 'paramName',
				headerTemplate : '<span class="tooltip" title="">输入输出参数关系定义</span>',
				editor : inOutParamEditor,
				width : '140px'
			},{
				field : 'cellExp',
				headerTemplate : '<span class="tooltip" title="华表的表达式">华表表达式</span>',
				editor : cellExpEditor,
				width : '200px'
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
				width : '200px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems(),valuesLength = values.length,value;
				//检测 输出控件事件更改  同步变更输入输出参数 中 out事件定义
				for(var i=0;i<valuesLength;i++){
					value = values[i];
					//eName  pObj-->eleId
					var outWidgets = value.outWidget?JSON.parse(value.outWidget):null ;
					var paramDatas = value.param?JSON.parse(value.param)[0].datas:null;
					if(outWidgets && paramDatas){
						for(var j=0;j<outWidgets.length ;j++){
							var outWidget = outWidgets[j];
							var eName = outWidget.eName ; 
							var eleId = JSON.parse(outWidget.pObj).eleId ;
							var datas = paramDatas[eleId] ;
							for(var k=0;k<datas.length;k++){
								datas[k].eventType = eName ;
							}
						}
						var rdatas = [{"name":"输入输出关系","datas":paramDatas}];
						value.param = JSON.stringify(rdatas) ;
					}
				}
				
				var pgrid = $("#grid").data("kendoGrid") ;
				var dataItem = pgrid.dataItems()[dataIndex] ;
				dataItem.widgetEvent = JSON.stringify(values);
				//column.hidLinkImg = 
				$('#eventsWin').data("kendoWindow").close();
				//rushGrid();
				e.preventDefault();
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
		//初始化 图片链接窗口
		$('#eventsWin').kendoWindow({
			modal : true,
			pinned : true,
			width : $(document).width()-200,
			height : $(document).height()-200,
			title : "输出控件事件定义器",
			actions : [ "Close" ],
			visible : false,
			position : {
				top : 100,
				left : 100
			}
		});
	});
	
	//显示事件输入定义器
	function showEventsWin(e) {
		var grid = $('#grid').data("kendoGrid");
		var $tr = $(e).parents('tr');
		dataIndex = $tr.index();
		grid.select($tr);
		var dataItem = grid.dataItem($tr);
		var data = $.extend(true,{},dataItem);
		eventsGridSource.length = 0 ;
		eventsGridSource = $.merge(eventsGridSource,dataItem.widgetEvent?JSON.parse(dataItem.widgetEvent):[]); 
		$("#eventsGrid").data("kendoGrid").dataSource.read();
		$('#eventsWin').data("kendoWindow").open();
	}
</script>
</head>
<body>
	<input type="hidden" id="hidParam" />
	<div id="grid"></div>
	<div id="selectPage" style="display: none; width: 300px; height: 400px;">
		<div id="ztree" class="ztree" style="width: 290px; height: 350px;overflow: auto;"></div>
	</div>
	<div id="selectPage2" style="display: none; width: 300px; height: 400px;">
		<div id="ztree2" class="ztree" style="width: 290px; height: 350px;overflow: auto;"></div>
	</div>
	<div id="eventsWin" style="padding: 0;">
		<div id="eventsGrid" >
		</div>
	</div>
</body>
</html>