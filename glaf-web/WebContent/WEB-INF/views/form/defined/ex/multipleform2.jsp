<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程表单绑定</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.ztree.extends.js" ></script>
<style type="text/css"> 
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
<script type="text/javascript">

/**
 * 全局参数
 */
var mtxx = {
	contextPath : '${contextPath}',
	url : 	"${contextPath}/mx/form/defined",
	targetId : '${param.targetId}',
	fn : '${param.fn}'
};

var buttons = {
		select : false,
		selectProcess : function(e) {
			var url = "${contextPath}/mx/activiti/process/processDefinitionsSelect?"
					+ $.param({
						targetId : '',
						fn : 'selectProcessFunc'
					});
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : false,
				title : "选择流程",
				closeBtn : [ 0, true ],
				shade : [ 0, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '1100px', '630px' ],
				iframe : {
					src : url
				}
			});
		},
		saveSet : function(e) { //保存流程配置
			
			if(!window.pd){
				alert("请选择流程!");
				return false;
			}
			
			showSelectProperties({id : '0000'},{});
			
			function initTarget(vs, taskId, collection){
				if(vs && vs.length){
					$.each(vs, function(i, v){					
						if(v.target){
							collection.push({
								eleId : v.t,
								taskId : taskId
							});
						}
					});
				}
			}
			
			if (pageProperties) {
				var array = new Array(), task, targets = {};				
				$.each(pageProperties,function(pageId,v){
					if(v){
						!targets[pageId] && (targets[pageId] = []);
						$.each(v, function(taskId, vs){
							task = getTaskByTaskId(taskId);
							if(task){
								initTarget(vs, taskId, targets[pageId]);
								array.push({
									pageId : pageId,
									actTaskId : taskId,
									actTaskName : task.properties ? task.properties.name : '',
									bytes : vs
								});
							}							
						});
					}					
				});
				current = current || {};
				
				var $grid0Ds = $('#grid-0').data("kendoGrid").dataSource, plans = new Array();
				var c = {}, t = $.extend(true, {}, current);
				$.each($grid0Ds.data(), function(i, v){
				//	c.processDefId = current.processDefinitionId;
				//	c.pageId = v.id;
					t.variables = v.variables;
					t.name = current.name;
					t.pageName = v.name;
					t.targets = targets[v.id];
				//	c.bytes = JSON.stringify([t]);
				//	plans.push(c);
					plans.push({
						processDefId : current.processDefinitionId,
						pageId : v.id,
						pageName : v.name,
						bytes : JSON.stringify([t])
					});
				});
				if(array.length > 0){
					$.ajax({
						type : 'POST',
						dataType : 'JSON',
						async : false,
						data : {
							defId : window.parentDef.MSGID,
							jsonStr : JSON.stringify({
								rules : array,
								plans : plans
							})
						},
						url : "${contextPath}/mx/form/formWorkflowPlan/batchSaveFormWorkflowPlan",
						success : function(ret){
							/* var variables = $("#variables").data("kendoGrid");
							current.variables = variables.dataSource.data();
							pageFunc(current); */
							window.parentDef.MSGID = ret.defId;
							parentDef.processName = current.name;
							parentDef.datas = array;
							pageFunc(parentDef);
							
							if(confirm("关闭窗口?")){
								window.close();
							}
						}
					});
				}
				
			}
		},
		selectPage : function(){
			var url = '${contextPath}/mx/form/defined/ex/selectPage?' + $.param({
				targetId : null,
				fn : 'addPageGrid',
			});
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : false,
				title : "选择页面",
				closeBtn : [ 0, true ],
				shade : [ 0, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '1100px', '630px' ],
				iframe : {
					src : url
				}
			});
		}
	};

$(function(){
	
	
	var grid0Ds = [];
	
	if(mtxx.targetId){
		var json = pageFunc();
		if(json && json.MSGID){
			$.ajax({
				url : '${contextPath}/mx/form/formWorkflowPlan/getFormWorkflowPlanByDefId',
				data : {
					defId : json.MSGID
				},
				async : false,
				type : 'POST',
				dataType : 'JSON',
				success : function(ret){
					if(ret && ret.plans && ret.plans[0]){
						var plans = ret.plans, bytes = null, name, pageEle;
						$.each(plans, function(i, plan){
							try{
								bytes = JSON.parse(plan.bytes || JSON.stringify([{}]))[0];
							}catch(e){
								console.log(e);
							}
							if(i == 0 && bytes && bytes.id){
								selectProcessFunc(bytes);
							}
							pageEle = {
								id : plan.pageId,
								name : (bytes ? bytes.pageName : null),
								variables : bytes.variables
							};
							//$.log(bytes);
							grid0Ds.push(pageEle);
						});
						if(ret.rules && ret.rules.length){
							var p, t = null, pageId;
							$.each(ret.rules, function(i, rule){
								pageId = rule.pageId;
								!(p = pageProperties[pageId]) && (p = pageProperties[pageId] = {});
								try{
									t = p[rule.actTaskId] = bytes = JSON.parse(rule.bytes || JSON.stringify([{}]));
								}catch(e){
									console.log(e);
								}
							});
							$.log(t);
						}
					} else {
						 buttons.selectProcess(null);
					}
				}
			});
		} else { //弹出流程选择页面
			 buttons.selectProcess(null);
		}
	}
	
	$('#grid-0').kendoGrid({
		dataSource : grid0Ds,
		columns : [{
			title : '页面',
			field : 'name'
		},{
			title : '操作',
			template : function(){
				return "<button class='k-button' width='20' onclick='deleteRow(this)'>删除</button>";
			}
		}],
		selectable: "row",
		change: function(e) {
			var selectedRows = this.select();
			if(selectedRows.get(0)){
				var item_old = $('#grid-0').data("selected");
				if(item_old){
					item_old.variables = $("#variables").data("kendoGrid").dataSource.data();
				}
				var item = this.dataItem(selectedRows.get(0));
				if(item){
					setVariablesData($.extend([],item.variables || globalVariables));
				}
				addPageTab(item);
				$('#grid-0').data("selected", item);
				if(currentContextObject){
					populateProperties(item, currentContextObject);
				}
			}
		},
	    dataBound: function(e) {
		    setTimeout(function(){
		    	e.sender.select("tbody tr:eq(0)");
		    }, 0);
		}
	});
	
	var vg = $("#variables").kendoGrid({ // 流程变量
		editable : true,
		selectable: "multiple, row",
		columns : [{
			title : '流程定义参数',
			field : 'varname',
			template : function(dataItem){
				return $("<input/>", {
					class : 'k-textbox',
					name : 'fieldId',
					value : dataItem.varname,
					readonly : true
				}).css({
					width : "100%"
				}).outter();
			}
		},
		{
			title : '表单元素',
			field : 'fieldId',
			template : function(dataItem){
				return $("<input/>", {
					class : 'k-textbox',
					name : 'fieldId',
					value : dataItem.fieldId,
					ondblclick : "openExpress(this)",
					onchange : 'textboxOnchange(this)',
					't-uid' : dataItem.uid
				}).css({
					width : "100%"
				}).outter();
			}
		}],
		dataSource : []
	}).data("kendoGrid");
	
	var dropFields = new Array();
		
	jQuery("#tree-grid").kendoTreeList({
		height : 550,
		editable : true,
		columns : [ {
			"field" : "name",
			"title" : "名称",
			"width" : "150px"
		}, {
			"field" : "read",
			"title" : "可读",
			"width" : "150px",
			headerTemplate: '<input type="checkbox" id="check-read" class="check-all" /><label for="check-read">可读</label>',
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'read');
			}
		}, {
			"field" : "write",
			"title" : "可写",
			"width" : "150px",
			headerTemplate: '<input type="checkbox" id="check-write" class="check-all" /><label for="check-write">可写</label>',
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'write');
			}
		}, {
			"field" : "show",
			"title" : "显示",
			"width" : "120px",
			headerTemplate: function(item){
				return '<input type="checkbox" id="check-show" class="check-all" /><label for="check-show">显示</label>';
			},
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'show');
			}
		}, {
			"field" : "target",
			"title" : "接收对象",
			"width" : "120px",
			headerTemplate: function(item){
				return '<input type="checkbox" id="check-target" class="check-all" /><label for="check-target">接收对象</label>';
			},
			template : function(dataItem) {
				return checkboxTemplate(dataItem, 'target');
			}
		}, {
			"field" : "defaultVal",
			"title" : "默认值",
			"width" : "150px",
			template : function(dataItem) {
				if (dataItem.parentId) {
					return $("<input/>", {
						class : 'k-textbox',
						name : 'defaultVal',
						value : dataItem.defaultVal,
						ondblclick : "openExpress(this)",
						onchange : 'textboxOnchange(this)',
						't-uid' : dataItem.uid
					}).css({
						width : "100%"
					}).outter();
				}
			}
		} ],
		dataBinding : function(e){
			if(!dropFields[0])
				$.each(e.sender.dataSource.data(),function(i,v){
					if(v.parentId){
					//	dropFields.push($.extend({},v));
					}
				});
		},
		selectable : true
	}).data("kendoTreeList");
	
	$(".k-button").each(function(i, v) {
		var t = $(this).attr("t");
		if(t){
			$(this).kendoButton({
				click : buttons[t]
			});
		}
	});
	
	$(".check-all").on('change', function(e){
		var id = this.id, type = id.substring(6), checked = this.checked;
		$("input[name="+ type +"]").each(function(){
			this.checked = checked;
			clickCheckbox(this);
		});
	});
	
});

function deleteRow(o){
	if(confirm("你确定删除吗?")){
		var $this = $(o), $tr = $this.closest("tr"), $grid = $this.closest("[data-role='grid']");
		var $kg = $grid.data("kendoGrid"), dataSource = $kg.dataSource;
		var item = $kg.dataItem($tr[0]);
		dataSource.remove(item);
	}
}

function setTreeListDataByPageId(pageId){
	
	var treeGrid = $('#tree-grid').data('kendoTreeList');
	
	var dataSource = new kendo.data.TreeListDataSource({	
		transport : {
			read : {
				type : "POST",
				dataType : "json",
				data : {pageId : pageId},
				url : "${contextPath}/mx/form/defined/getPageElementsById"
			},
			parameterMap : function(options, operation) {
				return options;
			}
		},
		schema : {
			data : 'rows',
			model : {
				fields : {
					id : {
						type : "number",
						nullable : false
					},
					parentId : {
						field : "pid",
						type : "number",
						nullable : true
					}
				},
				expanded : true
			},
			parse : function(ret){
				return ret;
			}
		}
	});
	
	treeGrid.setDataSource(dataSource);
	
}

function getVariablesByPageId(pageId, collection){
	$.ajax({
		type : 'post',
		dataType : 'json',
		data : {
			pageId : pageId
		},
		async : false,
		url : "${contextPath}/mx/form/workFlowRule/getLastFormWorkFlowRules",
		success : function(data){
			if(collection && data && data.rows){
				$.each(data.rows,function(i, v){
					var bytes = v.bytes;
					if(bytes){
						collection[v.actTaskId] = JSON.parse(bytes);
					}
				});
			}
		}
	});
}

</script>
</head>
<body >
	<div id="container">
		<div id="vertical" style=''>
			
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; " ><img
							src="${contextPath}/images/setting_tools.png"
							style="vertical-align: middle;" /> 
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">流程表单绑定</span>
							
							<button id="button-3" class='k-button' type="button" t='selectProcess'>选 择 流 程</button>
							<button id="button-4" class='k-button' type="button" t='selectPage'>选 择 页 面</button>
							
						</td>
						<td style="text-align:right;">
							<button id="button-1" class='k-button' type="button" t='saveSet'>保  存  配  置</button>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="center-pane" style="border:0px;">
				<div style="border:0px;">
					<div class="ztree" id="tree" style='height:auto;border:0px;display:none;' ></div>
					<div id='grid-0'></div>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-1" class='tabstrip' style="border:0px;">
						<ul>
							<li class="k-state-active">表单元素</li>
						</ul>
						<div style="height:auto;">
							<div id='tree-grid'></div>
						</div>
					</div>
				</div>
				<div style="border:0px;">
					<div id="variables" ></div>
				</div>
			</div>
			
			<div id="south-pane" style="border:0px;">
				<div style="border:0px;">
					<iframe id="processimage" align="center" frameborder="0" style="width:100%;height:350px;"> </iframe>
				</div>				
			</div>
			
			</div>
	</div>
</body>
<script type="text/javascript" >
var $tabstrip = $(".tabstrip"),$vertical = $("#vertical"), contextPath = mtxx.contextPath;;

$vertical.css({
	height : $(window).height()
}).kendoSplitter({
	orientation: "vertical",
	panes: [
		{ collapsible: false , resizable : false, scrollable: false ,size : '32px'},
		{ collapsible: true, resizable : true, scrollable: false},
		{ collapsible: true, resizable : true, scrollable: false, size : '35%'}
	],
	layoutChange : onResize,
});

$("#center-pane").kendoSplitter({
	orientation: "horizontal",
	panes: [
		{ collapsible: true, resizable : true, scrollable: true,size : '252px'},
		{ collapsible: true, resizable : true, scrollable: true},
		{ collapsible: true, resizable : true, scrollable: false,size : '240px'}
	]
});

function onResize(e){
	
	$vertical.css({
		height : $(window).height()
	});
	
	$tabstrip.css({
		height : $tabstrip.closest('div[role=group]').height()
	});
	
}

$tabstrip.each(function(i,item){
	var $this = $(this), a_target = 'span.dbl-close',eventType = 'click.' + a_target, $tab;
	mtxx[$this.attr('id')] = $this.kendoTabStrip({
		animation:  {
			open: {
				effects: "fadeIn"
			}
		},
		select : function(e){
			var dd = $(this.wrapper);
			$('#' + e.contentElement.id).height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
		}
	}).off(eventType, a_target).on(eventType, a_target, function(event) {
		$tab = $this.data('kendoTabStrip');
		$tab.remove($(this).closest("li"));
		$tab.select("li:last");
	});
});
window.parentDef = {};

function pageFunc(data){
	window.parent = window.opener || window.parent;
	var target = parent.document.getElementById(mtxx.targetId);
	if(data){
		 if(parent[mtxx.fn]){
			var datas = {
				data : data,
				rule : $(target).attr("dbrule")
			};
			return parent[mtxx.fn].call(target, datas);
		 }
	} else {
		if(parent[mtxx.fn]){
			var data = parent[mtxx.fn].call(target, datas);
			
			return window.parentDef = data;
		}
	}
}

function selectProcessFunc(data){
	current = data;
	if (data) {
		var url = "${contextPath }/workflow/diagram-viewer/view.html?processDefinitionId="+data.processDefinitionId + "&openType=defined";			
		$("#processimage").attr({
			src : url,
			onload:"iframeLoaded(this);"
		});
		closeLayer();
	}
}

function contains(data, item){	
	for(var i = 0; i < data.length; i ++){
		if(data[i].id === item.id)
			return true;
	}
	return false;
}

function addPageGrid(data){
	var ds = $("#grid-0").data('kendoGrid').dataSource;		
	if(!contains(ds.data(), data)){
		ds.add(data);
		addPageTab(data);
		$("#grid-0").data('kendoGrid').select("tbody tr:eq(" + ds.data().length + ")");		
	} else {
		alert('该页面已经选择!');
	}
	return false;
}

function addPageTab(data){		
	
	var id = data.id, text = data.name;	
	
	$('li.k-state-active span.k-link').text(text);
	
	setTreeListDataByPageId(id);
	
	
	
	return false;
	
	var $tabstrip1 = mtxx["tabstrip-1"].data('kendoTabStrip'),
	selector = "li:last",$frame = jQuery('<iframe>', {
		frameborder : 0,
		scrolling : 'auto',
		id : id,
		style : 'width:100%;height:99%'
	}).attr({
		src: mtxx.url + '/getFormPageHtmlById?id=' + id,
		title : text
	});
	
	$tabstrip1.remove(selector).append({
		id : 'tab_' + id,
		encoded : false,
		text : "<a class=''>" + text + "</a>",
		content : $frame.outter()
	}).select(selector);
	
//	closeLayer();
	
}

function setVariablesData(variables){
	var dataSource = new kendo.data.DataSource({
		data : variables
	});
	$("#variables").data("kendoGrid").setDataSource(dataSource);
}

var $ztree, globalVariables = null;//流程定义全局变量
function iframeLoaded(iframe){
	var docs = $(iframe).contents(),p = {
		id : 'lqbz',
		name : $(".processed-name",docs).val() || '流程'
	};
	globalVariables = new Array();
	setTimeout(function(){
		var pdg = iframe.contentWindow.ProcessDiagramGenerator;
		var ds = getProcessed(pdg);
		if(ds){			
			ajaxGetElementSet(current.processDefinitionId.split(":")[0],function(str){//获取流程定义里面的全局变量
				if(str){
					var p = JSON.parse(str);
					if(p.propertyPackages && p.propertyPackages){
						$.each(p.propertyPackages,function(i,v){
							if(v.id == "varprop"){
								var variableProp = new Object();
								if(current.variables && current.variables[0]){
									$.each(current.variables,function(i,vv){
										variableProp[vv.varcode] = vv;
									});
								}
								$.each(v.properties,function(index, obj){
									if(obj.value){
										if(obj.value.varcode in variableProp){
											$.extend(obj.value, variableProp[obj.value.varcode] ,{datas : null});
										}
									}
									globalVariables.push(obj.value);
								});
								setVariablesData($.extend([], globalVariables));
							}
						});
					}
				}
			});			
			window.processDiagram = pdg.processDiagrams[current.processDefinitionId];
			window.pd = processDiagram.processDefinitionDiagramLayout.processDefinition;
			p = {
				id : pd.id,
				name : pd.name
			};
			var datas = new Array(p);
			$.each(ds,function(i,o){
				datas.push({
					id : o.activityId,
					name : o.properties.name || '',
					parentId : p.id
				});
			});
		}
	}, 1000); //延迟一秒，确保iframe 加载完
}

function getTaskByTaskId(taskId){
	function getTask(activities){
		return activities[taskId];
	}	
	if(!window.activities && window.processDiagram){
		if(processDiagram.processDefinitionDiagramLayout){
			window.activities = processDiagram.processDefinitionDiagramLayout.activities;
		}
	}
	return getTask(window.activities || {});
}

var currentId = null;
var current = null;
function ajaxGetElementSet(elementId,fn){
	$.ajax({
		url : "${contextPath}/service/workflow/flow/elemsetview",
		data : {
			"processDefinitionId" : current.processDefinitionId,
			"resourceId" : elementId
		},
		type : 'post',
		async : true,
		success:function(ret){
			if(ret && fn)
				fn(ret);
		}
	});
}

var activities = {};//每个节点
function getProcessed(processDiagramGenerator){
	var processDiagrams = processDiagramGenerator.processDiagrams;
	if(processDiagrams && processDiagrams[current.processDefinitionId]){
		var processDiagram = processDiagrams[current.processDefinitionId];
		if(processDiagram){
			var nodes = processDiagram.processDefinitionDiagramLayout.activities;
			if(nodes){
				$.each(nodes,function(i,o){
					o.children = new Array();
					activities[o.activityId] = o;
				});
			}			
			var sequenceFlows = processDiagram.processDefinitionDiagramLayout.sequenceFlows;
			if(sequenceFlows && sequenceFlows[0]){
				$.each(sequenceFlows,function(i,v){
					var activiti = activities[v.sourceActivityId];
					if(activiti){
						activiti.children.push(v);
					}
				});
			}
			return nodes;
		}
	}
	return null;
}

/**
 ** 点击流程节点，显示属性配置
 */
var currentId = null, pageProperties = {};
var currentContextObject;
function showSelectProperties(o, properties) {
	o = o || {};
	var id = o.id, $grid = $("#tree-grid").data('kendoTreeList'),//
	data = $grid.dataSource.data(), props;
	if (currentId && (currentId !== id)) {
		props = [];
		$.each(data, function(k, v) {
			if (!v.hasChildren) {
				var obj = {};
				$.each([ "t", "rid", "write", "show", "read", "defaultVal",//
				         "datas", "target", "container"],
					function(i, field) {
					obj[field] = v[field];
				});
				props.push(obj);
			}
		});
		properties[currentId] = props;
	}
	currentId = id;
	currentContextObject = o;
	var p = properties[currentId], datas = [];
	if (p) {
		var tmp = {}, e, ele;
		$.each(p, function(i, v) {
			tmp[v.t] = v;
		});
		$.each(data, function(i, v) {
			v.uid = null;
			v.datas = null;
			ele = {};
			datas.push($.extend(ele, v));
			!v.hasChildren && !!(e = tmp[v.t]) && ($.extend(ele, e));
		});
	} else {
		$.each(data, function(k, v) {
			v.uid = null;
			v.datas = null;
			if (!v.hasChildren) {
				$.extend(v, {
					write : true,
					show : true,
					read : true,
					defaultVal : '',
					target : '' //接收人
				});				
			}
			datas.push(v);
		});
		properties[currentId] = datas;
	}
	$grid.dataSource.data(datas);
}

function getSelected(){
	var grid = $('#grid-0').data("kendoGrid");
	var selects = grid.select();
	if(selects && selects[0]){
		return grid.dataItem(selects[0]);
	} else 
		return null;
}

function openPropertiesView(canvas, element, contextObject){
	populateProperties(getSelected(), contextObject);
}

function populateProperties(item, contextObject){
	if(item){
		var pageId = item.id;
		if(!item.properties){
			!pageProperties[pageId] && (pageProperties[pageId] = {});
			item.properties = pageProperties[pageId];
		}
		showSelectProperties(contextObject, item.properties);
	}
}

$.log = function(msg){
	return console.log(msg || '');
};
function closeLayer(){
	layer.close(layer.getFrameIndex());
}

function checkboxTemplate(dataItem, key) {
	if (dataItem.parentId) {
		var opt = {
			type : 'checkbox',
			onclick : "clickCheckbox(this)",
			name : key,
			't-uid' : dataItem.uid
		}, t;
		if ((t = dataItem[key]) == true || t == 'true'){
			opt.checked = true;
		}
		return $("<input>", opt).outter();
	}
	return null;
}

function clickCheckbox(o) {
	set(o, o.checked);
}

function textboxOnchange(o) {
	return set(o, o.value);
}

function getGrid(jq){
	return jq.closest("[data-role=grid]").data("kendoGrid") || jq.closest("[data-role=treelist]").data("kendoTreeList");
}

function set(o,value){
	var $this = $(o), name = $this.attr("name");
	var grid = getGrid($this) || $("#variables").data("kendoGrid");
	if(grid){
		var dataItem = grid.dataSource.getByUid($this.attr("t-uid"));
		if(dataItem){
			dataItem.set(name,value);
		}
	}
	return dataItem;
}

var expTarget = null;
function openExpress(o) {
	expTarget = o;
	var expURL = "${contextPath}/mx/expression/defined/view?" + $.param({
		retFn : "setRowField",
		getPageAttrFn : "getPageAttrFn",
		initExpFn : "getinitExp",
		category : 'form'
	});
	window.open(expURL);
}

function getinitExp(){
	if(expTarget){
	//	var $this = $(expTarget);
		var uid = $(expTarget).attr("t-uid");
		var grid = getGrid($(expTarget));
		var dataItem = grid.dataSource.getByUid(uid);
		if(dataItem && dataItem.datas){
			return dataItem.datas;
		}
	}
	return null;
}

function getPageAttrFn(){
	var datas = [],parents = {};
	$.each($("#tree-grid").data('kendoTreeList').dataSource.data(),function(i,v){
		var obj = {
				id : v.id,
				pId : v.parentId,
				name : v.name	
		};
		if(!v.parentId){
			parents[v.id] = v;
		} else {
			var p = parents[v.parentId];
			if(p){
				obj.dType = 'string';
				obj.code = "~M{" + v.t + "}";
				obj.value = "~M{" + p.name + "_" + v.name + "}";
			}
		}
		datas.push(obj);
	});
	return datas;
}

function setRowField(data){
	if(data){
		expTarget.value = data.expVal;
		var dataItem = textboxOnchange(expTarget);
		if(dataItem){
			dataItem.set("datas",data);
			var item = getSelected();
			if(item){
				//!item.variables && (item.variables = []);
				//item.variables.push(dataItem);
			}
		}
	}
}

$.fn.outter = function(){
	return this[0] ? this[0].outerHTML : '';
};


</script>
</html>
