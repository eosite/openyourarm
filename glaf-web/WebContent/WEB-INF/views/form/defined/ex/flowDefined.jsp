<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>流程定义配置</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
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
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script>
	var fn = "${param.fn}", targetId = "${param.targetId}", treeGrid;

	$(function() {

		var dropFields = new Array();
		
		treeGrid = jQuery("#tree-grid").kendoTreeList({
			dataSource : new kendo.data.TreeListDataSource({
				transport : {
					read : {
						url : "${contextPath}/mx/form/defined/getPageElementsById?pageId=${param.pageId}",
						dataType : "json",
						type : "POST"
					},
					parameterMap : function(options, operation) {
						return options;
					}
				},
				batch : false,
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
					}
				}
			}),
			editable : true,
			//	height : x_height * 0.6,
			columns : [ {
				"field" : "name",
				"title" : "名称",
				"width" : "150px"
			}, {
				"field" : "read",
				"title" : "可读",
				"width" : "150px",
				headerTemplate: '<input type="checkbox" id="check-read" /><label for="check-read">可读</label>',
				template : function(dataItem) {
					return checkboxTemplate(dataItem, 'read');
				}
			}, {
				"field" : "write",
				"title" : "可写",
				"width" : "150px",
				headerTemplate: '<input type="checkbox" id="check-write" /><label for="check-write">可写</label>',
				template : function(dataItem) {
					return checkboxTemplate(dataItem, 'write');
				}
			}, {
				"field" : "show",
				"title" : "显示",
				"width" : "120px",
				headerTemplate: function(item){
					console.log(item);
					return '<input type="checkbox" id="check-show" /><label for="check-show">显示</label>';
				},
				template : function(dataItem) {
					return checkboxTemplate(dataItem, 'show');
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
							dropFields.push($.extend({},v));
						}
					});
			},
			selectable : true
		}).data("kendoTreeList");

		$(".k-button").each(function(i, v) {
			var t = $(this).attr("t");
			$(this).kendoButton({
				click : buttons[t]
			});

		});
		
		var vg = $("#variables").kendoGrid({
			height : 300,
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
				title : '表单',
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
		
		$.ajax({
			type : 'post',
			dataType : 'json',
			data : {
				pageId : "${param.pageId}"
			},
			async : false,
			url : "${contextPath}/mx/form/workFlowRule/getLastFormWorkFlowRules",
			success : function(data){
				if(data && data.rows){
					$.each(data.rows,function(i,v){
						var bytes = v.bytes;
						if(bytes){
							properties[v.actTaskId] = JSON.parse(bytes);
						}
					});
				}
			}
		});

		pageFunc();
		
		
	});
	
	/**
	 *	页面初始化以及页面信息反馈
	 */
	function pageFunc(data) {
		var $parent = window.opener || window.parent, $fn = $parent[fn], $target = $parent.document
				.getElementById(targetId);
		if (data) {
			if ($fn) {
				$fn.call($target, data);
			} else if ($target) {
				$target.value = JSON.stringify(data);
			}
		} else {
			//初始化页面信息
			var data = $target.value;
			if (data) {
				var datas = JSON.parse(data);
				if (datas) {
					data = datas[0];
					selectProcessFunc(data);
				}
			} else {
				$("button[t=selectProcess]").click();
			}
		}
	}

	$.fn.outter = function() {
		return this[0].outerHTML;
	};

	function checkboxTemplate(dataItem, key) {
		if (dataItem.parentId) {
			var opt = {
				type : 'checkbox',
				onclick : "clickCheckbox(this)",
				name : key,
				't-uid' : dataItem.uid
			};
			if (dataItem[key] == true || dataItem[key] == 'true'){
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
	
	function getGrid(jq){
		return jq.closest("[data-role=grid]").data("kendoGrid") || jq.closest("[data-role=treelist]").data("kendoTreeList");
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
	
	function getPageAttrFn(){
		var datas = new Array(),parents = {};
		$.each(treeGrid.dataSource.data(),function(i,v){
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
			}
		}
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

	var buttons = {
		select : false,
		selectProcess : function(e) {
			var url = "${contextPath}/mx/activiti/process/processDefinitionsSelect?"
					+ $.param({
						targetId : '',
						fn : 'selectProcessFunc'
					});
			buttons.select = true;
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
			
			showSelectProperties($("#processimage")[0],false);
			if (properties) {
				var frame = $("#processimage");
				var array = new Array();
				$.each(properties,function(key,v){
					var dom = $("#" + key,frame.contents());
					array.push({
						pageId : "${param.pageId}",
						actTaskId : key,
						actTaskName : dom.attr("taskName"),
						bytes : v
					});
				});
				if(array.length > 0){
					$.ajax({
						type : 'post',
						dataType : 'json',
						data : {
							pageId : "${param.pageId}",
							jsonStr : JSON.stringify(array)
						},
						url : "${contextPath}/mx/form/workFlowRule/batchSaveFormWorkFlowRule",
						success : function(data){
							var variables = $("#variables").data("kendoGrid");
							current.variables = variables.dataSource.data();
							pageFunc(current);
						}
					});
				}
			}
		}
	};

	var current = null;
	/**
	 * 选择流程
	 */
	function selectProcessFunc(data) {
		if (data) {
			current = data;
			
			var url = "${contextPath }/mx/form/workflow/defined/showImageAndDiv?";
			url = "${contextPath }/workflow/diagram-viewer/view.html?";
			
			url += "processDefinitionId="+data.processDefinitionId + "&openType=defined";
			
			$("#processimage")
					.attr(
							{
								src : url,
								onload:"iframeLoaded(this);"
							});
			layer.close(layer.getFrameIndex());
			currentId = null;
			if(buttons.select){
				 properties = new Object();
			}
		}
	}
	
	var variables = {};
	function openPropertiesView(canvas, element, contextObject){
		
		//var processDiagrams = $("#processimage")[0].contentWindow.ProcessDiagramGenerator.processDiagrams;
		
		showSelectProperties({
			id : element.id
		},false);

		if(!variables[element.id]){
			variables[element.id] = {
				params : new Array()
			};
			var ac = activities[element.id];
			if(ac && ac.children){
				$.each(ac.children,function(i,v){
					ajaxGetElementSet(v.id,function(ret){
						if(ret){
							var o = JSON.parse(ret);
							var properties = o.propertyPackages[0].properties;
							if(properties && properties[0]){
								var param = null;
								$.each(properties,function(index,obj){
									if(obj.id == 'expression'){
										if(obj.value){
											if(obj.value.varVal && obj.value.varVal[0]){
												variables[element.id].params.push(obj.value.varVal[0].key);
											}
										}
									}
								});
							}
						}
					});
				});
			}
		}
	}
	
	function ajaxGetElementSet(elementId,fn){
		$.ajax({
			url : "${contextPath}/service/workflow/flow/elemsetview",
			data : {
				"processDefinitionId" : current.processDefinitionId,
				"resourceId" : elementId
			},
			type : 'post',
			async : false,
			success:function(ret){
				if(ret && fn)
					fn(ret);
			}
		});
	}

	/**
	 ** 点击流程节点，显示属性配置
	 */
	var properties = new Object();
	var currentId = null;
	function showSelectProperties(o,tree) {
		
		if(!tree){//不是树节点点击
			var zTree = $ztree.ztree("getZtree");
			var node = zTree.getNodeByParam("id", o.id, null);
			zTree.selectNode(node);
		}
		o = o || tree;
		var id = o.id, $grid = treeGrid, data = $grid.dataSource.data(), props;
		if (currentId && (currentId != id)) {
			props = new Array();
			$.each(data, function(k, v) {
				v.uid = null;
				if (!v.hasChildren) {
					var obj = new Object();
					$.each([ "t", "rid", "write", "show", "read", "defaultVal","datas"], function(i, field) {
						obj[field] = v[field];
					});
					props.push(obj);
				}
			});
			properties[currentId] = props;
		}
		currentId = id;
		var props = properties[currentId],datas = new Array();
		if (props) {
			var tmp = new Object();
			$.each(props, function(i, v) {
				tmp[v.t] = v;
			});
			$.each(data, function(i, v) {
				var o;
				v.uid = null;
				v.datas = null;
				if (!v.hasChildren) {
					o = tmp[v.t];
					if (o) {
						datas.push($.extend({},v,o));
					}
				} else {
					datas.push($.extend({},v));
				}
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
						defaultVal : ''
					});
				}
				datas.push(v);
			});
			properties[currentId] = datas;
		}
		var dataSource = new kendo.data.TreeListDataSource({
			data : datas
		});
		$grid.setDataSource(dataSource);
	}
	
	
	var activities = {};//每个节点
	function getProcessed(processDiagramGenerator){
		
	//	var processDiagramGenerator = $("#processimage")[0].contentWindow.ProcessDiagramGenerator;
	
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
												$.extend(obj.value,variableProp[obj.value.varcode]);
											}
										}
										globalVariables.push(obj.value);
									});
									var dataSource = new kendo.data.DataSource({
										data : globalVariables
									});
									$("#variables").data("kendoGrid").setDataSource(dataSource);
								}
							});
						}
					}
				});
				
				var processDiagram = pdg.processDiagrams[current.processDefinitionId];
				var pd = processDiagram.processDefinitionDiagramLayout.processDefinition;
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
			initZtree(datas,iframe);
		}, 200);
		
		
		var v = "${contextPath}/mx/form/defined/ex/flowVariable?";
		v = "${contextPath}/mx/workflow/management/viewProcessDef?";
		
		docs.find(".processContainer>img").attr({
			title : '双击流程变量配置'
		}).on("dblclick.img",function(){
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : false,
				title : "流程变量配置",
				closeBtn : [ 0, true ],
				shade : [ 0, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '1400px', '850px' ],
				iframe : {
					src : v + $.param({
						fn : 'getVariable',
						processDefinitionId : current ? current.processDefinitionId : "",
						targetId : ''
					}) 
				}
			});
		});
	}
	
	function getVariable(data){
		
		if(data){
			
		} else {
			
			return [];
		}
		
	}
	
	function initZtree(nodes,iframe){
		
		function create(datas){
			$ztree = $("#ztree-1").ztree({
				callback : {
					beforeClick : function(treeId, treeNode, clickFlag){
						if(!treeNode.isParent){
							showSelectProperties($("#" + treeNode.id,$(iframe).contents())[0],treeNode);
						}
					}
				}
			},datas);
		}
		
		if(!$ztree){
			create(nodes);
		} else {
			$ztree.ztree("getZtree").destroy();
			$ztree.data("ztree",null);
			create(nodes);
		}
		
		var treeObj = $ztree.ztree("getZtree");
		
		treeObj.expandAll(true);
		
		var node = treeObj.getNodes()[0];
		if(node && node.children){
			node = node.children[0];
			treeObj.setting.callback.beforeClick(null,node);
			treeObj.selectNode(node);
		}
		
		
	}
	
	
	var editors = {
			stuff : function(container){
				var $tr = container.closest('tr');
				return {
					tr : $tr,
					index : $tr.index(),
					grid : $tr.closest('[data-role=grid]').data('kendoGrid')
				};
			},
			textbox : function(container,options){
				var s = editors.stuff(container),$input = $("<input/>",{
					name : options.field,
					class : 'k-textbox'
				}).appendTo(container).change(function(e){
					s.grid.dataSource.data()[s.index][options.field] = $(this).val();
				});									
				return $input;									
			},
			dropdownlist : function(container,options,dropdownlist){
				
				var $input = $("<input/>",{
					name : options.field
				}).appendTo(container).kendoDropDownList(dropdownlist);
				
				return $input;
				
			},
			checkbox : function(container,options){
				var $input = $("<input/>",{
					name : options.field,
					type : 'checkbox'
				}).appendTo(container);									
				return $input;
			}
		};
	
</script>
</head>
<body>
	<div id="container" style="overflow: auto;height:99%; width: 100%">

		<div id="header">
			<div id="north-pane" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width: 500px; text-align: left; vertical-align: middle;"><img
						src="${contextPath}/images/setting_tools.png"
						style="vertical-align: middle;" /> <span
						style="font-family: Lucida Calligraphy; font-size: 20px; font-weight: bolder;">流程定义配置</span>
					</td>

					<td style="text-align: right;">
						<button class='k-button' type="button" t='selectProcess'>选
							择 流 程</button>
					</td>
					<td style="text-align: right;">
						<button class='k-button' type="button" t='saveSet'>保 存 配
							置</button>
					</td>
				</tr>
			</table>
		</div>
		</div>

		<div id="menu" style="width:15%;float:left" >
			<ul id='ztree-1' class='ztree' > </ul>
		</div>

		<div id="content" style="width:83%;float: left;">
			<div id="tree-grid" ></div>
		</div>

		<div align="center" style="width:100%;">
			<div id="menu" style="width:60%;float:left" >
				<br>
				<iframe id="processimage" align="center" frameborder="0" style="width:100%;height:800px;"> </iframe>
			</div>
	
			<div id="content" style="width:38%;float: left;">
				<br>
				<br>
				<div id="variables" ></div>
			</div>
		</div>

	</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.ztree.extends.js" ></script>
</html>