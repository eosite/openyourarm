<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>数据行转列配置工具</title>
	<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
	<%@ include file="/WEB-INF/views/inc/globaljs.jsp"%>
	<script type="text/javascript">var contextPath="${contextPath}";</script>

	<script type="text/javascript" src="${contextPath}/scripts/kendoConfirm.js"></script>
	<link rel="stylesheet" href="${contextPath}/scripts/treegrid/css/jquery.treegrid.css" >
	<script type="text/javascript" src="${contextPath}/scripts/treegrid/js/jquery.treegrid.js"></script>
	<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
	<link rel="stylesheet" href="${contextPath}/scripts/jsPlumb/css/jsplumb.css">
	<script type="text/javascript" src="${contextPath}/scripts/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/jsPlumb/js/jquery.jsPlumb.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/expression.js"></script>

	<script type="text/javascript" src="${contextPath}/webfile/js/builder.jsplumb.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery.base64.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/treeJson.js"></script>
	<link rel="stylesheet" href="${contextPath}/webfile/styles/jsplumbtable.css">

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

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}
.tree td{
border-top:1px solid #ddd;
padding:5px;
}
.groupConTree td{
border-top:1px solid #ddd;
padding:5px;
}
ul li
{
list-style-type:none;
}
.dom_line {margin:2px;border-bottom:1px gray dotted;height:1px}
.domBtnDiv {display:block;padding:2px;border:1px gray dotted;background-color:powderblue}
.categoryDiv {display:inline-block; width:335px}
.domBtn {display:inline-block;cursor:pointer;padding:2px;margin:2px 10px;border:1px gray solid;background-color:#FFE6B0}
.domBtn_Disabled {display:inline-block;cursor:default;padding:2px;margin:2px 10px;border:1px gray solid;background-color:#DFDFDF;color:#999999}
.dom_tmp {position:absolute;font-size:12px;}
.active {background-color: #93C3CF}
/* .td-cls{
	background-color:#FCFCFC;
	align:left;
	height:16px;
} */
.tr-cls{
	height : 30px
}
</style>
<script type="text/javascript">

var dataTransfer = {//全局保存数据
		id : "${param.id}"
	};

var setting = {
	async: {
		enable: true,
		type:"post",
		dataType:"json",
		contentType: "application/x-www-form-urlencoded",
		url:"${contextPath}/mx/datasrc/listDataSources",
		dataFilter: filter
	},
	callback: {
		onClick: zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pId",
		}
	},
	view: {
		selectedMulti: false,
		fontCss: function(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		}
	}
};


function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	var data = childNodes.rows||[];
	for (var i=0, l=data.length; i<l; i++) {
		data[i].name = data[i].sourceName;
		data[i].pId = 0;
	}
	data.unshift({
		id : 0,
		name : "数据源列表"
	});
	return data;
}

function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	var data  = JSON.parse(treeNode.columnInfos);
	$("#button2").data("sourceId",treeNode.id);
	//colTbGrid.data("source",data);
	setGridData(data);
}

$(function(){
	
	//初始化数据源
	// var dataSrcUrl = contextPath + "/mx/datasrc/listDataSources?1=1";
	// $.ajax({
	// 		url : dataSrcUrl,
	// 		data : JSON.stringify({Pagination:"10"}),
	// 		type : 'post',
	// 		dataType : 'json',
	// 		contentType: "application/json",
	// 		success : function(data){
	// 			if(data){
	// 				if(data.statusCode == 500) {							
	// 					outError(data.message);
	// 				}
	// 				$.each(data.rows,function(i,o){
	// 					var $li  = $('<li style="padding-left: 30px;vertical-align: middle;height:18px;"><a href="javascript:;" style="font-size:15px;"><span class="button ico_docu" style=""></span>'+o.sourceName+'</a></li>');		
	// 					$li.data("sourceId",o.id);
	// 					$li.data("source",o);
	// 					$("#menu").append($li);
	// 				});
	// 			}

	// 		},
	// 		error : function(data){
	// 			if(data){
	// 				outError(data.responseText, true);
	// 			}	
	// 		} 
	// });
	//初始化grid数据

	// //菜单单击操作
	// $("#menu").on("click","li",function(){
	// 	$this = $(this);
	// 	var data = $this.data("source").columnInfos;
	// 	data = JSON.parse(data);
	// 	$("#menu").find("a").css("color","");
	// 	$("#menu").find("li").removeAttr('checked');
	// 	$this.find("a").css("color","red");
	// 	$this.attr("checked","checked");
	// 	setGridData(data);
	// });
	$.fn.zTree.init($("#menu"), setting);

	window.fieldTree = $.fn.zTree.getZTreeObj("menu");

	$.ajax({
			url : "${contextPath}/mx/datatransfer/getTransferById?transferId="+dataTransfer.id,
			data : JSON.stringify({Pagination:"10"}),
			type: "POST",
		    dataType: "json",
		    contentType: "application/json",
			success : function(data){
				if(data){
					if(data.statusCode == 500) {							
						outError(data.message);
					}else{
						setGridData(data.columns);
						// $("#menu").find("li").data("source")
						//colTbGrid.data("source",data.columns);
					}
				}
			},
			error : function(data){
				if(data){
					outError(data.responseText, true);
				}	
			} 
	});


	

	var dataTransferSaveUrl = contextPath + "/mx/datatransfer/dataTransferSave?1=1";
	//初始化保存按钮
	$("#button2").kendoButton({
		click:function(e){
			//alert(111);
			// var selectLi = $("#menu").find('li[checked="checked"]');
			// var sourceId = selectLi.data("sourceId");
			var sourceId = $("#button2").data("sourceId");
			var dataSource = JSON.stringify(colTbGrid.dataSource.data());
			$.ajax({
				url : dataTransferSaveUrl,
				data : {jsonResult:dataSource,sourceId:sourceId,dataTransferId:dataTransfer.id},
				type : 'post',
				dataType : 'json',
				success : function(data){
					if(data){
						if(data.statusCode == 500) {							
							outError(data.message);
						}
						if(data.id){
							dataTransfer = data;
							alert(data.message || '操作成功!');
						}
					}
				},
				error : function(data){
					if(data){
						outError(data.message, true);
					}
				} 
			});
		}
	});
	

	//初始化运行按钮
	$("#runBtn").kendoButton({
		click:function(e){
			//alert(111);

			var output = {};
			output.database = $select_out.dataItem();
			output.outTableName = $("#create_table_name").val();
			var out = JSON.stringify(output);

			var transferId = dataTransfer.id;
			var dataSource = JSON.stringify(colTbGrid.dataSource.data());
		}
	});

});
	

function setGridData(data){
	var dataSource = new kendo.data.DataSource({data:data});
	colTbGrid.setDataSource(dataSource);
}

function outError(message, error){
	alert(message);
	var $error = $("<span>").css({color : 'red'}).text(message);
	$("#message-div").html(error ? message : $error);
}




function nextNode(){
	$("#searchText").data("n-index", ($("#searchText").data("n-index") || 0)*1 + 1);
	window.selectNode();
}

function searchProject(){

	var searchTextBox = $("#searchText"),val = searchTextBox.val();
	
	var f = fieldTree.expandAll(false), tmp = {};
	
	function updateHighlight(ns,flag){
		$.each(ns, function(i,node){
			node.highlight = flag || false;
			fieldTree.updateNode(node);
		});
	}
	
	function getNodesLike(value, collection){
		if(value && (value = $.trim(value))){
			$.each(["name", "sourceName"], function(){
				var ns = fieldTree.getNodesByParamFuzzy(this, value, null);
				if(ns && ns.length){
					$.each(ns, function(){
						if(!this.isParent && !tmp[this.id]){							
							if(this.indexName && this.indexName.indexOf(this.tableName) < 0){
								this.indexName += " [" + this.tableName + "]";
							}
							collection.push(this);
							tmp[this.id] = this;
						}
					});
				}
			});
		}
	}
	
	if(!f){

		setTimeout(function(){
			var ns = searchTextBox.data('nodes');
			if(ns && ns[0]){
				updateHighlight(ns, false);
			}
			if(val){
				nodes = [];
				if(val.indexOf(",") > 0){
					$.each(val.split(","), function(){
						getNodesLike(this, nodes);
					});
				} else {
					getNodesLike(val, nodes);
				}
				if(nodes && nodes[0]){
					$.each(nodes,function(i,node){
						if(!node.isParent){
							node = node.getParentNode();
						}
						fieldTree.expandNode(node, true, true, false);
					});
					updateHighlight(nodes, true);
					searchTextBox.data('nodes', nodes);
					searchTextBox.data("n-index", 0);
					window.setTimeout(function(){
						window.selectNode();
					}, 500);					
				}
			}
			
		}, 500);

	}
}

function selectNode(){

	var $o = $("#searchText");
	var nodes = $o.data("nodes"), index = ($o.data("n-index") || 0)*1;
	if(nodes && nodes.length){
		if(index >= nodes.length){
			index = nodes.length - 1;
			$o.data("n-index", -1);
		}
		var node = nodes[index*1];
		fieldTree.selectNode(node);	
		// $("#tb-span").text(node.tableName);
	}
}

</script>
<body>
	<div id="vertical" style="width:98%;height:98%; margin: 10px auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width:500px;text-align: left;vertical-align: middle; " >
						<img src="${contextPath}/images/database.png" style="vertical-align: middle;" />
						<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">数据行转列</span>
						<span style="font-size: 16px;font-weight: bolder;">定义工具</span>
					</td>
					<td style="text-align:right;">
						<button id="button2" type="button">保存</button>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="horizontal" style="height:100%; width: 100%;">
				
				<div style="height:451px;">
					<ul>
						<li style="padding-left: 30px;">
							<input id="searchText"  type="text" class="k-textbox" style="width: 152px;"/>
							<button id="searchBt" type="button" onclick='searchProject()'>搜索</button>
							<button id="nextBt" type="button" class='k-button' onclick='nextNode()'>Next</button>
						</li>
					</ul>
					<hr>
					<ul id="menu" class="ztree" style="height:91%;overflow:auto"></ul>
				</div>
				
				<div>
					<div id="tabstrip" class='' style="border:0px;">
						<div id="colTb" style=" width: 100%;height:100%;"></div>

						<script type="text/x-kendo-template" id="toolbar-template">
                			<div class="toolbar">
                    			<button class='k-button' id='colTb-toolbar-create'>增加</button>
                			</div>
            			</script>
						<script type="text/javascript">
							initGrid();
							$("#colTb").find("#colTb-toolbar-create").kendoButton({click : 
								function(e){
									var data = colTbGrid.dataSource.data();
									data.splice(0,0,{
										isAdd : true,
										tableName : '',
										columnName :"",
										columnLabel : 'field_' + new Date().getTime(),
										group_by : false,
										transfer : false,
										routRule : false
									});
									var dataSource = new kendo.data.DataSource({
										  data:data
									});
									colTbGrid.setDataSource(dataSource);
								}
							});	

							var editors = {
								stuff : function(container){
									var $tr = container.closest('tr');
									return {
										tr : $tr,
										index : $tr.index(),
										grid : $tr.closest('[data-role="grid"]').data('kendoGrid')
									};
								},
								textbox : function(container,options){
									var s = editors.stuff(container);
									var $input = $("<input/>",{
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

							var dropdownlistDatasource =  ['','分组字段','行转列字段','值字段'];


							function openExpress(o){
					 			$.data(document.body, "expressionObj", o);
					 			console.log(o);
					 			var expURL = contextPath + "/mx/expression/defined/view?" + $.param({
									retFn : "setField",
									getFn : "getRowTree",
									initExpFn : "getinitExp",
									category : 'db'
								});
								window.open(expURL);
					 		}

					 		//表达是页面回调函数
							function setField(data){
								if(data){
									var $input = $($.data(document.body, "expressionObj"));
									
									$input.val(data.expVal).data("item", data).focus();
									
									var stuff = editors.stuff($input);
									
									stuff.tr.data("item", data);
									
									var dataItem = stuff.grid.dataItem($input.closest("tr"));
									
									dataItem[$input.attr("name")] = data.expVal;
									
									dataItem.expression = $input.val();
									dataItem.expItem = data;
									kendo.bind($input,dataItem);
								}
							}

							//子页面表达式获取数据
							function getRowTree(){
							
								var data=colTbGrid.dataSource.data(),$this,d = null, datas = null,ds;
								
									ds = new Object(), datas = new Array(), d = new Array();
									var p = {
										id : 'param-01',
										name : '输入形参',
										pId : '',
										children : []
									};
									if(window.initParams){
										var params = window.initParams();
										if(params){
											$.each(params, function(i, v){
												$.extend(v, {
													code : "~F{"+ v.param +"."+ v.param +"."+ v.param +"}",
													value : "~F{"+ v.name +"}",
												});
												p.children.push(v);
											});
										}
									}
									datas.push(p);

								if(data.length > 0){
									datas.push({
										id : 'columns',
										name : '字段',
										pId : ''
									});
									
									$.each(data,function(i, row){
										if(!row.isAdd){
											datas.push({
												id :row.columnName,
												pId : "columns",
												name : row.columnName,
												t : '',
												code : "~F{"+ row.tableName +"."+row.columnName +"}",
												value : "~F{"+ row.tableName +"."+row.columnName +"}",
												open : false,
												isParent : false
											});
										}
									});
								}
								return datas;
							}

				 			function getinitExp(){				 				
								var $input = $($.data(document.body, "expressionObj"));
								if($input[0]){
									var item = $input.data("item") || $input.closest("tr").data("item");
									if(!item){
										var stuff = editors.stuff($input);
										var dataItem = stuff.grid.dataItem($input.closest("tr"));
										if(dataItem)
											item = dataItem.expItem;
									}
									if(item){
										$input.data("item",item);
										return item;
									}
								}
								return null;							
							}

							function initGrid(){
								window.colTbGrid = $("#colTb").kendoGrid({
								  toolbar : kendo.template($("#toolbar-template").html()),
								  editable : true,
								  columns: [
								    {
								    	field : "tableName",
								    	title:"表名" ,
								    	width: "200px",
								    	editor : function(container,options){
								    		editors.textbox(container,options);
								    	}
								    },
								    { 
								    	field: "columnName",
								    	title:"字段/表达式" , 
								    	width: "200px", 
								    	editor : function(container,options){							    		
											editors.textbox(container,options).attr({
												t : options.model.uid,
												readonly : true
											}).on('dblclick',function(e){
													openExpress(this);
											});
								    	}
								    },
								    { 
								    	field: "columnLabel",
								    	title:"字段别名" , 
								    	width: "200px", 
								    	
								    	editor : function(container,options){							    	
											editors.textbox(container,options);
								    	}
								    },
								    { 
										field : "config",
										title: "字段配置",
										width: "60px",
										editor : function(container,options){		
											var s = editors.stuff(container);
											editors.dropdownlist(container,options,{
												dataSource : dropdownlistDatasource,
												change : function(e){
													s.grid.dataSource.data()[s.index][options.field] = e.sender.value();
													if(e.sender.value()=="行转列字段"){
														var data = s.grid.dataSource.data();
														//data.splice(s.index,1);
														$.each(data,function(i,o){
															if(i!=s.index){
																if(o.config=="行转列字段"){
																	alert("警告：行转列字段只能有一个！");
																	e.sender.value("");
																	s.grid.dataSource.data()[s.index].config="";
																}
															}
														});
													}

												}
											});
								    	}
										
									},
									{ 
										field : "routRule",
										title: "路由规则",
										width: "60px",
										template : function(dataItem){
											return  checkboxTemplate(dataItem,'routRule');
										}
									},
								    { title: "操作", width: "120px",template : function(){
								    	return "<button class='k-button' onclick='deletehandler(this)' >删除</button>";} 
								    }
									],
									height : $(document).height(),
									selectable:true,
									dataSource: {
									} 
								    
								}).data('kendoGrid');
								
							}

							function deletehandler(o){
								//if(confirm('你确定删除吗?')){
									colTbGrid.removeRow(o);
								//}
							}

							function checkboxTemplate(dataItem,key){
									var opt = {
										type : 'checkbox',
										onclick : "clickCheckbox(this)",
										name : key
									};
									if(dataItem[key] == true || dataItem[key] == 'true')
										opt.checked = true;
									return $("<div>").append($("<input />",opt)).html();
							}

							function clickCheckbox(o){
								var $this = $(o);
								var s = editors.stuff($this);
									s.grid.dataSource.data()[s.index]["routRule"] = o.checked;//对象赋值
							}

							// function clickCheckbox(o){
							// 	var $this = $(o);
							// 	var s = editors.stuff($this);
							// 	if($this.attr("name")=="group_by"){
							// 		$this.parents("tr").find('input[name="transfer"]').removeAttr("checked");
							// 		s.grid.dataSource.data()[s.index]["group_by"] = o.checked;//对象赋值
							// 	}else if($this.attr("name")=="transfer"){
							// 		$this.parents("tr").find('input[name="group_by"]').removeAttr("checked");
							// 		s.grid.dataSource.data()[s.index]["transfer"] = o.checked;//对象赋值
							// 	}
							// }
						</script>
					</div>
				</div>
			</div>
		</div>
		<!--
		<div id="output" class="k-header k-footer footerCss">
			<table cellspacing="1" border="0" style="width:90%">
				<tbody>
					<tr>
						<td align="right">目标数据库</td>
						<td>
							<select id="select-out" style="width : 120px;"></select>
							<script type="text/javascript">
						     	var $select_out = $("#select-out").kendoDropDownList({
						     		dataTextField: "text",
			                        dataValueField: "code",
			                        dataSource: {
			                            transport: {
			                                read: {
			                                    dataType: "json",
			                                    url:'${contextPath}/rs/isdp/global/databaseJson'
			                                }
			                            }
			                        },
			                        index: 0,
			                        change: function(e){
			                        	console.log(e.sender.dataItem());
			                        }
						     	}).data("kendoDropDownList");
			   				</script>
						</td>
						<td align="right">目标表名称</td>
						<td>
							<input type="text" id="create_table_name" name="create_table_name" class="k-textbox" required="" validationmessage="必填项">
							<span class="k-invalid-msg" data-for="create_table_name"></span>
						</td>
						<td style="">
							<button id="runBtn" type="button">运行</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		-->
	</div>

	<script type="text/javascript">
		$("#vertical").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsed : false,
				resizable : false,
				scrollable : false,
				size : "40px"
			}, {
				collapsed : false,
				scrollable : false
			}, {
				collapsed : false,
				resizable : false,
				scrollable : false,
				size : "50px"
			} ]
		});
		var splitter = $("#horizontal").kendoSplitter({
			panes : [ {
				collapsed : false,
				collapsible : true,
				collapsedSize : "0px",
				max : "300px",
				resizable : true,
				size : "300px",
				scrollable : true
			}, {
				size : "500px",
				scrollable : false
			} ]
		});
		splitter = splitter.data("kendoSplitter");

		
		//初始化sousuo按钮
		$("#searchBt").kendoButton({
			//imageUrl : "${contextPath}/images/search.png"
		});
		
	</script>
</body>
</html>