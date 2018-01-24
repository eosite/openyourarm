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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>数据集定义工具</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<%@ include file="/WEB-INF/views/inc/globaljs.jsp"%>
<script type="text/javascript">
var contextPath="${contextPath}";
</script>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<link rel="stylesheet" href="${contextPath}/scripts/treegrid/css/jquery.treegrid.css" >
<script type="text/javascript" src="${contextPath}/scripts/treegrid/js/jquery.treegrid.js"></script>
<link rel="stylesheet"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
<link rel="stylesheet" href="${contextPath}/scripts/jsPlumb/css/jsplumb.css">
<script type="text/javascript" src="${contextPath}/scripts/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jsPlumb/js/jquery.jsPlumb.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/expression.js"></script>
<script type="text/javascript" src="${contextPath}/webfile/js/builder.js"></script>
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
div.mt-row{
	height: 40px;
	max-height: 40px;
	line-height:40px;
}
div.mt-cell{
	display:inline-table;
}
div.mt-left{
 	width: 140px;
 	padding-left: 20px;
}

	</style>
<script>
  	var x_height = Math.floor(window.innerHeight * 0.68);
	var x_width = Math.floor(window.innerWidth * 0.82);

	if(window.screen.height <= 768){
        x_height = Math.floor(window.screen.height * 0.60);
	}
	if(window.screen.width < 1200){
        x_width = Math.floor(window.screen.width * 0.82);
	} else if(window.screen.width > 1280){
        x_width = Math.floor(window.screen.width * 0.72);
	}  
	

(function($) {
   function isDOMAttrModifiedSupported() {
		var p = document.createElement('p');
		var flag = false;
		
		if (p.addEventListener) p.addEventListener('DOMAttrModified', function() {
			flag = true
		}, false);
		else if (p.attachEvent) p.attachEvent('onDOMAttrModified', function() {
			flag = true
		});
		else return false;
		
		p.setAttribute('id', 'target');
		
		return flag;
   }
   
   function checkAttributes(chkAttr, e) {
		if (chkAttr) {
			var attributes = this.data('attr-old-value');
			
			if (e.attributeName.indexOf('style') >= 0) {
				if (!attributes['style']) attributes['style'] = {};
				var keys = e.attributeName.split('.');
				e.attributeName = keys[0];
				e.oldValue = attributes['style'][keys[1]];
				e.newValue = keys[1] + ':' + this.prop("style")[$.camelCase(keys[1])];
				attributes['style'][keys[1]] = e.newValue;
			} else {
				e.oldValue = attributes[e.attributeName];
				e.newValue = this.attr(e.attributeName);
				attributes[e.attributeName] = e.newValue; 
			}
			
			this.data('attr-old-value', attributes); //update the old value object
		}	   
   }
   var MutationObserver = window.MutationObserver || window.WebKitMutationObserver;
   $.fn.attrchange = function(o) {
	   
		var cfg = {
			trackValues: false,
			callback: $.noop
		};

		if (typeof o === "function" ) { 
			cfg.callback = o; 
		} else { 
			$.extend(cfg, o); 
		}

	    if (cfg.trackValues) {
	    	$(this).each(function (i, el) {
	    		var attributes = {};
	    		for (var attr, i=0, attrs=el.attributes, l=attrs.length; i<l; i++){
	    		    attr = attrs.item(i);
	    		    attributes[attr.nodeName] = attr.value;
	    		}
	    		
	    		$(this).data('attr-old-value', attributes);
	    	});
	    }
	   
		if (MutationObserver) {
			var mOptions = {
				subtree: false,
				attributes: true,
				attributeOldValue: cfg.trackValues
			};
	
			var observer = new MutationObserver(function(mutations) {
				mutations.forEach(function(e) {
					var _this = e.target;
					if (cfg.trackValues) {
						e.newValue = $(_this).attr(e.attributeName);
					}
					
					cfg.callback.call(_this, e);
				});
			});
	
			return this.each(function() {
				observer.observe(this, mOptions);
			});
		} else if (isDOMAttrModifiedSupported()) {
			return this.on('DOMAttrModified', function(event) {
				if (event.originalEvent) event = event.originalEvent;
				event.attributeName = event.attrName;
				event.oldValue = event.prevValue;
				cfg.callback.call(this, event);
			});
		} else if ('onpropertychange' in document.body) {
			return this.on('propertychange', function(e) {
				e.attributeName = window.event.propertyName;				
				checkAttributes.call($(this), cfg.trackValues , e);
				cfg.callback.call(this, e);
			});
		}

		return this;
    }
})(jQuery);
	<!--
	var JP,dataSet = {//全局保存数据
		id : "${param.id}"
	};
	
	$(document).ready(function() {
		
		$(".tabstrips").css({
			height : x_height
		});
		
		JP = new JP('tabstrip');
		
		if(dataSet.id){
			$.ajax({
				url : contextPath + "/rs/dataset/getLastestJson",
				type : 'POST',
				dataType : 'json',
				data : {datasetId : dataSet.id},
				success : function(data){
					if(data){
						data.id = dataSet.id; //另存时json 里保存的还是上一次的主键id
					}
					dataSet = data;
					initPageState(data);
					if(data){
						//$("#message-div").html(data.sql);
					}
					initEvent($("#join-div"));
					
				}
			});
		}
		
		
	});

	
	//初始还原
	function initPageState(dataSet){
		
		/*if(dataSet.systemName && $select_biaoduan){
			$select_biaoduan.value(dataSet.systemName);
			
			var selectType = $("#select-type").data('kendoDropDownList').dataItem();
        	if(selectType){
        		initZTree(selectType.code,dataSet.systemName);	
        	}
		}*/
		
		if(dataSet.fromSegments){//还原 table
			var pos,left,top,dataSource,$table = $("#tableName");
			$.each(dataSet.fromSegments,function(i,table){
				pos = table.expression/*,left = 0,top = 0*/;
				var item = {};
				if(pos){
					item = JSON.parse(pos) || {};
				}
				$table.val(item.expVal || table.tableName).data("item", item);
				/*if(pos){
					left = pos.split(",")[0];
					top = pos.split(",")[1];
				}
				createDataTable({
					tableId : table.tableId,
					tableName : table.tableName,
					tableNameCN : table.tableCN,
					tableType : table.tableType
				},table.dataSource,null,left*1 + 20,top*1 + 40);*/
			});
			
			setTimeout(function(){
				if(dataSet.selectSegments){//还原 选择列
					var id,chk;
					$.each(dataSet.selectSegments,function(i,column){
						column.group_by = column.group_by || false;
						
						if(!column.expItem){
							id = column.tableName + "_chk_" + column.columnName;
							chk = $("#" + id);
							if(chk[0]){
								chk[0].checked = true;
							}
						}
						
					});
					
					dataSource = new kendo.data.DataSource({
						  data:dataSet.selectSegments
					});
					
					$("#colTb").data("kendoGrid").setDataSource(dataSource);
				}
				
				/*if(dataSet.joinSegments){//还原连线
					$.each(dataSet.joinSegments,function(i,join){
						var params = {
							source : join.sourceTable + "_tr_" + join.sourceColumn,
							target : join.targetTable + "_tr_" + join.targetColumn
						};
						 var connection = JP.connect(params);
						 
						 if(connection){
							 $.each(connection.getOverlays(),function(i,overlay){
								 var $this = $(overlay.canvas);
								 if($this.is("img")){
									 
									var chk = $("input[name=join-type][value='" + join.connector + "']");
									
									if(chk[0]){
										var img = chk.closest("tr").find("img");
										$this.attr({
											src : img.attr("src")
										});
									}
									
								    $this.data("msg",join);
								 }
							 });
						 }
					});
				}*/
				
				
				function initWhereHaving(collection,collectionTree,$tree){
					if(collectionTree){
						$tree.addNodes(collectionTree);
					}
					var $this;
					$.each(collection,function(i,item){
						//$this = treeFuncs.addChildCon($tr);
						$this = $tree.find(".treegrid-" + item.ordinal);
						
						var parentNode = $this.treegrid("getParentNode");
						
						if(parentNode[0] && !parentNode.data("init")){
						//$.log(item);
							parentNode.find("select[class=linkCon]").data("kendoDropDownList").value(item.connector);
							parentNode.data("init",true);
						}
						
						var fieldInput = $this.find("input[name=field]"),
						paramInput = $this.find("input[name=param]"),
						dynamicChk = $this.find("input[type=checkbox]"),
						operatorDrop =$this.find("select[class=jCon]").data("kendoDropDownList"); 
						
						if(fieldInput[0] && paramInput[0]){
							
							var fieldItem = {
							//	expressionl : item.expressionl,
								expression : item.expressionl,
								value : item.expressionl,
								data : item.fieldData,
								dataStr : JSON.stringify(item.fieldData)
							};
							if(!fieldItem.dataStr){
								fieldItem.item = item;
							}
							
							fieldInput.val(item.fieldVal).data("item",fieldItem);
							
							paramInput.val(item.paramVal).data("item",{
								expression : item.expression,
								value : item.expression,
								data : item.paramData,
								dataStr : JSON.stringify(item.paramData)
							});
							operatorDrop.value(item.operator);
							if(dynamicChk[0])
								dynamicChk[0].checked = item.dynamic == "Y";
							
						}
					});
				}
				
				if(dataSet.whereSegments){//還原過濾條件
					
					initWhereHaving(dataSet.whereSegments,dataSet.whereSegmentTree,$("#conditionTree"));
				}
				
				if(dataSet.havingSegments){//還原分组条件
					
					initWhereHaving(dataSet.havingSegments,dataSet.havingSegmentTree,$("#groupConTree"));
				  
				}
				
				if(dataSet.orderBySegments){//还原排序
					
					dataSource = new kendo.data.DataSource({
						  data:dataSet.orderBySegments
					});
					
					sortedColTbGrid.setDataSource(dataSource);
					
				}
				
				if(dataSet.params){
					if(window.initParams){
						window.initParams(dataSet.params);
					}
				}
				
			}, 500);
		}
	}
	
	function getCheckedRows(){
		
		var grid = $("#colTb").data("kendoGrid");
		var dataSource = grid.dataSource.data();
		var tmp = new Object(),key;
		var datas = [],$this;
		if(dataSource && dataSource[0]){
			$.each(dataSource,function(i,row){
				//key = row.columnLabel;
				if(row.tableName){
					key = row.tableName + "_" + row.columnName;
					tmp[key] = row;
				}else{ //表达式
					datas.push(row);
				}
			});
		}
		
		$(".tbl-cls").each(function(i,table){
			$this = $(this);
			var tableName = $this.attr("tableName");
			var nameCN = $this.attr("nameCN");
			var chks = $this.find(".field-chk:checked");
			var bd = $this.data("dataSource");
			if(chks.length > 0){
				chks.each(function(index,chk){
					var row = getRow(chk);
					if(row){
						key = tableName + "_" + row.dname;
						if(key in tmp){
							var o = tmp[key];
							o.lenstr = row.strlen || row.lenstr;
							o.dtype = row.dtype;
							row = o;
						}else{
							row.isAdd = false;
							row.aggregate = '';
							row.group_by = false;
							row.output = true;
							row.columnName = row.dname;
							row.title = row.fname;
							row.tableName = tableName;
							row.tableNameCN = nameCN;
							row.chartCoord = '';
						}
						row.dataSource = bd;
					    datas.push(row);
					}
				});
			}
		});
		return datas;
	}
	
	function getRow(chk){
		
		var json = $(chk).parents(".field").attr("fieldjson"),row = null;
		
		if(json)
			row = $.parseJSON(json);
		
		return row;
	}
	
	//子页面表达式获取数据
	function getRowTree(){
		var tables = $(".tbl-cls"),$this,d = null, datas = null,ds;
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
		
		datas.push({
			id : '123'	,
			name : '字段',
			pId : ''
		});
		if(tables.length > 0){
			tables.each(function(i, $tbl){
				$this = $(this);
				var tableName = $this.attr("tableName"), nameCN = $this.attr("nameCN");
				var tableId = $this.attr("tableId");
				var bd = $this.data("dataSource");
				if(!ds[bd.code]){
					datas.push({
						id : bd.code,
						pId : "123",
						name : bd.text,
						t : '',
						open : false,
						isParent : true
					});
					ds[bd.code] = bd;
				}
				
				datas.push({
					id : tableId,
					pId : bd.code,
					name : nameCN,
					t : '',
					open : false,
					isParent : true
				});
				
				$this.find("tr").each(function(index,$tr){
					var fieldjson = $(this).attr("fieldjson");
					if(fieldjson){
						var row = $.parseJSON(fieldjson);
						datas.push({
							id : $(this).attr("id"),
							pId : tableId,
							name : row.fname,
							dType : row.dtype,
							t : '',
							code : "~F{"+ bd.code +"."+ tableName +"."+ row.dname +"}",
							value : "~F{"+ bd.text +"."+ nameCN +"."+ row.fname +"}",
							isParent : false
						});
					}
				});
			});
		}
		return datas;
	}
	
	function getinitExp(){
		var $menu = $("#conditionTree").data("menu");
		var textbox = $menu.prev('.textbox-menu'),item = textbox.data("item");
		if(item){
			if(item.data){
				return item.data;
			} else if(item.dataStr){
				return JSON.parse(item.dataStr);
			}
		}
		return null;
	}
	
	//表达是页面回调函数
	function setRowField(data){
		
		if(data){
			var $menu = $("#conditionTree").data("menu");
			
			var textbox = $menu.prev('.textbox-menu');
			
			data = $.extend({},data);
			
			if(textbox && textbox[0]){
				textbox.show().val(data.expVal).data("item",{
					expression : true,
					value : data.expActVal,
					//data : data,
					dataStr : JSON.stringify(data)
				});
				$menu.hide();
			}
		}
		
	}
	
	
	//获取菜单数据
	function getMenuData(){
		
		var menu = {
				text : "选择列",
				items : [],
				level : 0,
				leaf : false
		},obj;
		
		var colTbGrid = $("#colTb").data("kendoGrid");
		var dataSource = colTbGrid.dataSource.data();
		var colTbtTmp = new Object(),key;
		if(dataSource && dataSource[0]){
			$.each(dataSource,function(i,row){
				key = row.tableName + "_" + row.columnName;
				colTbtTmp[key] = row;
			});
		}
		
		$(".tbl-cls").each(function(i,table){
			$this = $(this);
			var tableName = $this.attr("tableName");
			var nameCN = $this.attr("nameCN");
			var bd = $this.data("dataSource");
			obj = {
					text : nameCN,
					value : tableName,
					items : [],
					leaf : false
			};
			
			$this.find("tr").each(function(index,$tr){
				var fieldjson = $(this).attr("fieldjson");
				if(fieldjson){
					var row = $.parseJSON(fieldjson);
					row.tableName = tableName;
					row.tableNameCN = nameCN;
					row.columnName = row.dname;
					key = row.tableName + "_" + row.dname;
					if(key in colTbtTmp){
						row.title = colTbtTmp[key].title || row.fname;
					}
					row.bd = bd;
					obj.items.push({
						text : row.title || row.fname,
						value : row.dname,
						item : row,
						leaf : true
					});
				}
			});
			menu.items.push(obj);
		});
		return [{
			text : "表达式",
			level : 1,
			leaf : false
		},menu];
	}
	
	//根据数据生成 ul >> li
	function createMenu(items,$meun){
		var $ul,$li;
		$.each(items,function(i,item){
			$li = $("<li>").text(item.text);
			if(item.items){
				$ul = $("<ul>");
				$li.append($ul);
				createMenu(item.items,$ul);
			}
			$li.data("item",item);
			$meun.append($li);
		});
	}
	
//-->
</SCRIPT>
<body>
	<div id="vertical" style="width:98%;height:98%; margin: 10px auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width:500px;text-align: left;vertical-align: middle; " ><img
						src="${contextPath}/images/database.png"
						style="vertical-align: middle;" /> <span
						style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">Dataset</span>
						<span style="font-size: 16px;font-weight: bolder;">动态数据集定义工具</span></td>
						<!-- 
						<td>当前选中表名：<span id="tb-span" style="font-size: 16px;font-weight: bolder;"></span></td>
						-->
					<td style="text-align:right;">
						<button id="button1" type="button">验证</button>
						<button id="button2" type="button">确定</button>
						<button id="button-3" type="button">预览</button>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="horizontal" style="height:100%; width: 100%;">
				<div>
				<div id="tabstrip" class='' style="border:0px;">
				    <ul>
						<li id="Tab2">数据表列</li>
						<li id="Tab3">过滤条件</li>
						<li id="Tab4">分组条件</li>
						<li id="Tab5">排序</li>
						<li id="Tab6">输入形参定义</li>
					</ul>
					<div class='tabstrips'
						style=" width: 96%;overflow-y:auto;overflow-x: hidden;">
						 <div class="mt-row k-header">
						 	<div class="mt-cell mt-left">标段</div>
						 	<div class="mt-cell">
				        		<select id="select-biaoduan" style="width : 120px;" > </select>
				        	</div>
						    <div class="mt-cell mt-left">表名设置</div><div class="mt-cell"><input id="tableName" type="text" class="k-textbox" name="" onclick="openExpress(this);"></div>	
			        	</div>
						 <div id="colTb" style=" width: 100%;height:100%;"></div>
						 
						 <script type="text/x-kendo-template" id="toolbar-template">
                			<div class="toolbar">
                    			<button class='k-button' id='colTb-toolbar-create'>增加</button>
                			</div>
            			</script>
						 <script type="text/javascript">
					     	var $select_biaoduan =  $("#select-biaoduan").kendoDropDownList({
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
		                        	
		                        }
					     	}).data("kendoDropDownList");

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
						 
					 		var dropdownlistDatasource =  ['','SUM','MAX','MIN',"AVG","COUNT"] ,chartCoordList = [
					 		        {
					 		        	chartCoord : '',
					 		        	name : ''	
					 		        },                                                                           
					 		        {
					 		        	chartCoord : 'X',
					 		        	name : 'X坐标'
					 		        },{
					 		        	chartCoord : 'Y',
					 		        	name : 'Y坐标'
					 		        }],template = {
					 				tableNameCN : "<input style=''  data-role='maskedtextbox' name='tableNameCN' data-bind='value:tableNameCN'/>",
					 				columnName : "<input style='' ondblclick='javascript:openExpress(this);'  data-role='maskedtextbox' name='columnName' data-bind='value:columnName' readonly=true />",
					 				columnLabel : "<input data-role='maskedtextbox' name='columnLabel' data-bind='value:columnLabel' style='' />",
					 				title : "<input  data-role='maskedtextbox' name='title' data-bind='value:title'/>",
					 				aggregate : "<input data-role='dropdownlist' name='aggregate' data-bind='value:aggregate' data-source='{data:dropdownlistDatasource}' />",
					 				chartCoord : "<input data-role='dropdownlist' name='chartCoord' data-text-field='name' data-value-field='chartCoord' data-bind='value:chartCoord' data-source='{data:chartCoordList}' />",
					 				output : "<input class='k-input' type='checkbox' name='output' data-bind='value:output' /> ",
					 				group_by : "<input class='k-input' type='checkbox' name='group_by' data-bind='value:group_by'/> "
					 		}; 
							var colTbGrid = $("#colTb").kendoGrid({
								  toolbar : kendo.template($("#toolbar-template").html()),
								  editable : true,
								  columns: [
								    
								   /*         
								    { field: "tableNameCN",title:"表名" , width: "200px", template : template.tableNameCN},
								    { field: "columnName",title:"字段/表达式" , width: "200px", template : template.columnName},
								    { field : "title",title:"中文名称", width: "200px",template:template.title},
								    { field : "aggregate",title:"聚合",width: "200px",template:template.aggregate},
								    { field : "chartCoord",title:"图表坐标",width: "200px",template:template.chartCoord},
								    { field : "output",title:"输出",width: "60px",template:template.output }, 
								    { field : "group_by",title:"分组",width: "60px",template:template.group_by },
								    
								    
								    
								    {
								    	field : "tableNameCN",
								    	title:"表名" ,
								    	width: "200px",
								    	editor : function(container,options){
								    		editors.textbox(container,options);
								    	}
								    },*/
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
										field : "title",
										title:"中文名称", 
										width: "200px",
								    	editor : function(container,options){
								    		editors.textbox(container,options);
								    	}
									},
									{ 
										field : "aggregate",
										title:"聚合",
										width: "200px",
										editor : function(container,options){
											var s = editors.stuff(container);
											editors.dropdownlist(container,options,{
												dataSource : dropdownlistDatasource,
												change : function(e){
													s.grid.dataSource.data()[s.index][options.field] = e.sender.value();
												}
											});
										}
									},
								    /*{ 
										field : "chartCoord",
										title:"图表坐标",
										width: "200px",
										template:function(dataItem){
											for(var i = 0; i < chartCoordList.length ; i ++){
												if(dataItem.chartCoord === chartCoordList[i].chartCoord)
													return chartCoordList[i].name;
											}
											return '';
										},
										editor : function(container,options){
											var s = editors.stuff(container);
											editors.dropdownlist(container,options,{
												dataSource : chartCoordList,
												dataValueField:'chartCoord',
												dataTextField:'name',
												change : function(e){
													s.grid.dataSource.data()[s.index][options.field] = e.sender.value();
												}
											});
										}
									},*/
								    { 
										field : "output",
										title:"输出",
										width: "60px",
										template : function(dataItem){
											return  checkboxTemplate(dataItem,'output');
										}
									}, 
								    { 
										field : "group_by",
										title:"分组",
										width: "60px",
										template : function(dataItem){
											return  checkboxTemplate(dataItem,'group_by');
										}
									},
								    { title: "操作", width: "120px",template : function(){
								    	return "<button class='k-button' onclick='deletehandler(this)' >删除</button>";
								    } }
								  ],
								  dataSource: [], 
								  selectable:true,
								  dataBound: function(e){
									  /*
									  e.sender.tbody.find("tr").each(function(i,tr){
										  var dataItem = e.sender.dataItem(this);
										  
										  $.log(dataItem);
										  
										  kendo.bind($(this),dataItem);
										  
										  $(this).find("input[type=checkbox]").each(function(){
									    	  var $this = $(this).unbind();
											  $this.attr('checked',$this.val() == 'true').on('click',function(){
													$this.val($this.is(':checked'));
													dataItem[$this.attr('name')] = $this.val();
											  });
									      });
									 }); 
									  */
								}
							}).data('kendoGrid');
							
							function clickCheckbox(o){
								var $this = $(o);
								var s = editors.stuff($this);
								s.grid.dataSource.data()[s.index][$this.attr("name")] = o.checked;
							}
					 		
							function deletehandler(o){
								//if(confirm('你确定删除吗?')){
									colTbGrid.removeRow(o);
								//}
							}
							
							var wins = [];
							
					 		function openExpress(o){
					 			$.data(document.body, "expressionObj", o);
					 			console.log(o);
					 			var expURL = contextPath + "/mx/expression/defined/view?" + $.param({
									retFn : "setColTbField",
									getFn : "getRowTree",
									initExpFn : "getColTbExp",
									category : 'db'
								});
								window.open(expURL);
					 		}
					 		
							$("#colTb").find("#colTb-toolbar-create").kendoButton({
								click : function(e){
									var data = colTbGrid.dataSource.data();
									data.splice(0,0,{
										isAdd : true,
										tableNameCN : '',
										columnName :"",
										columnLabel : 'field_' + new Date().getTime(),
										title : "",
										aggregate : "",
										output : true,
										group_by : false,
										expression : "",
										expItem : ''
									});
									var dataSource = new kendo.data.DataSource({
										  data:data
									});
									colTbGrid.setDataSource(dataSource);
								}
							});
							
							function getColTbExp(){
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
							
							//表达是页面回调函数
							function setColTbField(data){
								if(data){
									var $input = $($.data(document.body, "expressionObj"));
									
									$input.val(data.expVal).data("item", data).focus();
									
									var stuff = editors.stuff($input);
									if(stuff.grid){
										stuff.tr.data("item", data);
									
										var dataItem = stuff.grid.dataItem($input.closest("tr"));
										
										dataItem[$input.attr("name")] = data.expVal;
										
										dataItem.expression = $input.val();
										dataItem.expItem = data;
										kendo.bind($input,dataItem);
									}
								}
							}
							
						 </script>
					</div>
					<div class='tabstrips' style=" width: 96%;overflow-y:auto;overflow-x: hidden;">
							<table id="conditionTree" class="tableCon tree" style="border-spacing:0px;">
							</table>
							<script type="text/javascript">
							
							var templateComposit = [
								"<tr class='treegrid-{0} parent-cls'>",
									"<td>{1}</td>",
									"<td colspan='2'>",
										"<button class='addCon addChildCon k-button' name='addChildCon' type='button'>新增</button> ",
									    "  <select class='linkCon'> </select>",
									"</td>",
									"<td></td>",
									"<td></td>",
								"</tr>"
							];
							
							var templateChildren = [
								"<tr class='treegrid-{0} treegrid-parent-{1}' >",
								"<td>{2}</td>",
								"<td>",
										"<button class='addCon addSiblingCon k-button' name='addSiblingCon' type='button'>新增</button>",
								"</td>",
								
								 "<td>",
										" <input type='text' class='k-textbox textbox-menu' name='field' />",
										" <ul class='conMenu' style='display:none;' ></ul>",
								"</td>",
								"<td>",
								        " <select class='jCon' style='width: 80px;'> </select> ",
								"</td>",
								"<td>",
										" <input type='text' class='k-textbox textbox-menu' name='param' />",
										
										" <ul class='conMenu' style='display:none;' ></ul>",
								"</td>",
								
								
								"<td>",
								  //   "<input type='checkbox'  value='1'/> 动态 ",
								     "<label style='' > 动态 <input  class='chk-cls' name='dynamic' type='checkbox'></input> </label>",
								     " <button  class='delCon k-button' name='delCon' type='button'>删除</button> ",
								     " <button  class='upCon k-button' name='upCon' type='button'>转换为复合条件</button> ",
								"</td>",
								"</tr>"
							];
							
							var templateCompositStr = kendo.format(templateComposit.join(''),1,1);
							
						 </script>
					</div>
					
					<div class='tabstrips'
						style=" width: 96%;overflow-y:auto;overflow-x: hidden;">
						<table id="groupConTree" class="tableCon groupConTree" style="border-spacing:0px;"></table>
							<script type="text/javascript">
							
							$(".tableCon").append(templateCompositStr).treegrid();
							
							$.fn.extend({
								addNodes : function(nodes){
									var $this = this;
									if(nodes && nodes.length > 0){
										return $this.each(function(){
											var $t = $(this);
											$.each(nodes,function(i,node){
												return $t.addNode(node);
											});
										});
									}
								},
								addNode : function(node){
									var $this = this;
									if(!node.pId){//根节点
										return $this.find('tr:first');
									} else {
										var pnode = $this.find('treegrid-' + node.pId);
										if(pnode[0]){
											return createnode($this.find('treegrid-parent-' + node.id + ':last'),node);
										} else {
											return createnode($this.find('tr:last'),node);
										}
									}
									
									function createnode($tr,n){
										var $node;
										if(n.isParent){
											$node = $(kendo.format(templateComposit.join(''),n.id + " treegrid-parent-" + n.pId,n.name));
										} else {
											$node = $(kendo.format(templateChildren.join(''),n.id,n.pId,n.name));
										}
										$tr.after($node);
										
										initEvent($node);
										
										$this.treegrid();
										
										return $node;
									}
								}
							});
							
							var treeFuncs = {
									
									addChildCon : function($tr){
										return treeFuncs.addSiblingCon.call(this,$tr,true);
									},
									addSiblingCon : function($tr,isChild){
										
										var tree = $tr.parent();
										
										var parentNode = $tr.treegrid("getParentNode");
										if(isChild)
											parentNode = $tr;
										
										if(!tree.data("childId")){
											var l = tree.find('tr:last'),$id = 0,len = tree.find('tr').length;
											if(l[0]){
												$id = l.treegrid("getNodeId");
											}
											tree.data("childId",$id > len ? $id : len);
										}
										
										var id = tree.data("childId")*1 + 1;
										
										tree.data("childId",id);
										
										var pid = parentNode.treegrid("getNodeId");
										
										var name = parentNode.find(">td:first").text() + "." + (parentNode.treegrid("getChildNodes").length + 1);
										
										var node = $(kendo.format(templateChildren.join(''),id,pid,name));
										
										var last = tree.find(".treegrid-parent-" + pid + ":last");
										
										if(last[0]){
											function findLast(last){
												var childNodes = last.treegrid('getChildNodes');
												if(childNodes && childNodes.length > 0){
													var tid = last.treegrid('getNodeId');
													last = findLast(tree.find(".treegrid-parent-" + tid + ":last"));
												}
												return last;
											}
											 last = findLast(last);
										}
										
										last = last[0] ? last : $tr;
										
										last.after(node);
										
										initEvent(node);
										
										tree.treegrid();
										
										return node;
									},
									delCon : function($tr){
										
										var tree = $tr.parent();
										
										var parentNode = $tr.treegrid("getParentNode");
										
										var parentName = parentNode.find(">td:first").text();
										
										var pid = parentNode.treegrid("getNodeId");
										
										$tr.remove();
										
										tree.find(".treegrid-parent-" + pid).each(function(i,t){
											$(this).find(">td:first").text(parentName + "." + (i + 1));
										});
										tree.treegrid();
										
									},
									upCon : function($tr){//转换为复合条件
										
										var tree = $tr.parent();
										
										var id = $tr.treegrid("getNodeId");
										
										var name = $tr.find(">td:first").text();
										
										$tr.find(">td:first").text(name + ".1" );
										
										var parentNode = $tr.treegrid("getParentNode");
										
										var pid = parentNode.treegrid("getNodeId");
										
										var node = $(kendo.format(templateComposit.join(''),id,name));
										
										node.removeAttr("class").attr('class',$tr.attr('class') + " parent-cls");
										
										var tId = tree.data("childId")*1 + 1;
										
										tree.data("childId",tId);
										
										$tr.removeClass("treegrid-" + id).addClass("treegrid-" + tId);
										
										$tr.removeClass("treegrid-parent-" + pid).addClass("treegrid-parent-" + id);
										
										$tr.before(node);
										
										initEvent(node);
										
										tree.treegrid();
									}
								};
								
								function initEvent(jq){
									
									var $this;
									
									jq.find(".addCon").kendoButton({
										imageUrl : "${contextPath}/images/bullet_add.png",
										click: function(e) {
									        var target = $(e.event.target),name = target.attr('name');
									        if(name && (name in treeFuncs)){
									        	treeFuncs[name].call(target,target.parents("tr"));
									        }
									    }
									});
								    
									jq.find(".delCon").kendoButton({
										imageUrl : "${contextPath}/images/bullet_delete.png",
										click: function(e) {
									        var target = $(e.event.target);
									        treeFuncs['delCon'].call(target,target.parents("tr"));
									    }
									});
									
									jq.find(".upCon").kendoButton({
										imageUrl : "${contextPath}/images/arrow_small_up.png",
										click: function(e) {
									        var target = $(e.event.target);
									        treeFuncs['upCon'].call(target,target.parents("tr"));
									    }
									});
									
									jq.find(".fieldComB").kendoComboBox({
										dataTextField: "title",
				                        dataValueField: "columnName",
									  	dataSource : getCheckedRows(),
										animation: false
									});
								    
									jq.find(".linkCon").kendoDropDownList({
										dataTextField: "text",
				                        dataValueField: "value",
									  	dataSource : [
									  	     { text : '全部符合',value : 'AND',connector : 'AND'},
									  	     { text : '任意符合',value : 'OR',connector : 'OR'}
									  	   //  { text : '无',value : 'None',connector : ''},
									  	    //// { text : '排除全部',value : 'Not All',connector : ''},
									  	]
									});
									
									jq.find(".expression-select").kendoDropDownList({
										dataTextField: "text",
				                        dataValueField: "value",
									  	dataSource : [
									  	     { text : '字段',value : 'field'},
									  	     { text : '表达式',value : 'expression'}
									  	],
									  	enable : false
									});
									
									jq.find(".jCon").kendoDropDownList({
								    	dataTextField: "text",
				                        dataValueField: "value",
									  	dataSource : [
									  	     { text : '等于',value : '='},
										    // { text : '不等于',value : '!='},
									  	     { text : '不等于',value : '<>'},
									  	     { text : '大于',value : '>'},
									  	     { text : '小于',value : '<'},
									  	     { text : '大于等于',value : '>='},
									  	     { text : '小于等于',value : '<='},
									  	     { text : '包含',value : 'like'},
									  	     { text : '不包含',value : 'not like'},
									  	     { text : 'IS',value : 'IS'},
									  	     { text : 'IS NOT',value : 'IS NOT'},
									  	     { text : 'IN',value : 'IN'},
									  	]
									});
									
									jq.find(".textbox-menu").on('dblclick',function(){
										$this = $(this);
										$this.next('.conMenu').show();
										$this.hide();
									}).on('keyup',function(){
										$(this).data('item',null);
									}).attr({
										//readonly : true
									});
									
									jq.find(".chk-cls").on('click',function(){
										$this = $(this);
										var checked = $this.is(":checked"),chkItem = $this.data("item"),paramInput = $this.closest("tr").find("input[name=param]");
										if(checked){
											
											if(!paramInput.val()){
												alert('请填写参数名');
												return false;
											}
											if(!chkItem){
												$this.data("item",{
													name : paramInput.val(),
													param : 'col' + new Date().getTime()
												});
											} else {
												chkItem.name = paramInput.val();
											}
										} else {
											
										}
										
									});
									
									
									initMenuEvent(jq);
									
								}
								
								function addListener(jq){
									$(".k-animation-container").each(function(){
										var $this = $(this), listenerKey = "lis";
										if(!$this.data(listenerKey)){
											$this.attrchange(function(e){
												 $(this).css({top : 30 });
											}).data(listenerKey, true);
										}
									});
								}
								
								function initMenuEvent(jq){//下拉菜单
									var menus = getMenuData();
									jq.find(".conMenu").each(function(){
										var $this = $(this);
										$this.empty();
										createMenu(menus,$this);
										$this.kendoMenu({
											open : function(e){
												addListener(e.sender.wrapper);
											},
											select : function(e){
												var $this = $(e.item).parents("ul");
									    	    var textbox = $this.prev('.textbox-menu');
									    	    var item = $(e.item).data("item");
									    	    if(item){
									    	    	if(item.level == 1){//表達式
									    	    		$("#conditionTree").data("menu",$this);
									    	    		var expURL = contextPath + "/mx/expression/defined/view?" + $.param({
															retFn : "setRowField",
															getFn : "getRowTree",
															initExpFn : "getinitExp",
															category : 'db'
														});
														window.open(expURL);
									    	    	}else if(!item.items){//列
									    	    		$this.hide();
									    	    		var row = item.item;
									    	    		textbox.val(row.tableNameCN + "." + row.fname).data("item",item);
											    	    textbox.show();
									    	    	}
									    	    }
											},
											close : function(e){
												var $li = $(e.item);
												var item = $li.data("item");
												if(item.level == 0){
													var $ul = $li.parents("ul").hide();
										    	    $ul.prev('.textbox-menu').show();
												}
											}
										});
									});
									
									
								}
							    
								initEvent($(".tableCon"));
						 	</script>
					</div>
					<div class='tabstrips'
						style=" width: 96%;overflow-y:auto;overflow-x: hidden;">
						 <table style="height:100%;width:100%;">
						         <tr>
					                <td style="height:40px;text-align: center;font-family:宋体;font-size: 20px;font-weight: bolder;">可选排序字段</td>
					                <td></td>
					                <td style="height:40px;text-align: center;font-family:宋体;font-size: 20px;font-weight: bolder;">已选取排序字段</td>
					                <td></td>
						         </tr>
						          <tr>
						               <td style="width: 40%">
						                     <div id="availableColTb"></div>
						               </td>
						               <td style="text-align: center;">
						                     <button id="rightBt" name="rightBt" type="button" style="width:70px"></button>
						                     <br><br>
						                     <button id="leftBt" name="leftBt" type="button" style="width:70px"></button>
						               </td>
						               <td style="width: 40%">
						                    <table id="sortedColTb"></table>
						               </td>
						               <td style="text-align: center;">
						                     <button id="upBt" name="upBt" type="button" style="width:70px"></button>
						                     <br> <br>
						                     <button id="downBt" name="downBt" type="button" style="width:70px"></button>
						               </td>
						          </tr>
						 </table>
						 <script type="text/javascript">
							 var availableColTbGrid = $("#availableColTb").kendoGrid({
								  columns: [
								    { field: "tableColumn",title:"字段/表达式" , width: "180px" ,attributes: {"class": "table-cell", style: "border-left:0px;border-bottom-width:1px;"} },
								    { field: "title",title:"别名", width: "150px",attributes: {"class": "table-cell",style: "border-left:0px;border-bottom-width:1px;"}}
								  ],
								  selectable:"multiple, row",
								  height:500
							}).data("kendoGrid");
							 
						    var sortedColTbGrid = $("#sortedColTb").kendoGrid({
						    	  editable : true,
								  columns: [
								    { 
								    	field: "title",
								    	title:"排序字段" , 
								    	width: "30%" 
								    }, { 
								    	field: "order",
								    	title:"降序排列",
								    	width: "30%",
								    	//template:"<input type='checkbox' name='order' value='#:order#' />",
								    	template:function(dataItem){
								    		return checkboxTemplate(dataItem, "order");
								    	}
								    }, {
								    	field : 'expression',
								    	title : '表达式',
								    	width : '40%',
								    	editor : function(container, options){
											editors.textbox(container, options).attr({
												t : options.model.uid,
												readonly : true
											}).on('dblclick',function(e){
												openExpress(this);
											});
								    	}
								    }
								  ],
								  selectable:"multiple, row",
								  height:500,
								  dataBound: function(e){
									  /**
									  e.sender.tbody.find("tr").each(function(i,tr){
										  var dataItem = e.sender.dataItem(this);
										  kendo.bind($(this),dataItem);
										  $(this).find("input[type=checkbox]").each(function(){
									    	  var $this = $(this);
											  $this.attr('checked',$this.val() == 'true').on('click',function(){
													$this.val($this.is(':checked'));
													dataItem[$this.attr('name')] = $this.val();
											  });
									      });
									 }); 
									  */
								  }
						    }).data("kendoGrid");
						    
						    function exchange(fg,tg){
						    	var rows = fg.select();
						    	if(rows.length == 0){
						    		alert("请选择!");
						    	}else{
						    		var item;
						    		var ds = tg.dataSource.data(),len = ds.length;
						    		$.each(rows,function(i,row){
						    			item = fg.dataItem(row);
						    			item.ordinal = len + i;
						    			ds.push(item);
						    		});
						    		for(var i = 0; i < rows.length; i ++)
						    			fg.removeRow(rows[i]);
						    		fg.refresh();
						    	}
						    }
						    
						    $("#rightBt").kendoButton({
								imageUrl : "${contextPath}/images/arrow_right_16.png",
								click : function(e){
									exchange(availableColTbGrid,sortedColTbGrid);
								}
							});
						    
							$("#leftBt").kendoButton({
								imageUrl : "${contextPath}/images/arrow_left_16.png",
								click : function(e){
									exchange(sortedColTbGrid,availableColTbGrid);
								}
							});
							
							//上下移动
							var upsidedownfuncs = {
								up : function(rows,ds){
									var item = sortedColTbGrid.dataItem(rows[0]),item_ = ds[(item.ordinal - 1)];//前一个
									var ret = null;
									if(item.ordinal > 0){
										ret = [];
										$.each(rows,function(i,row){
											item = sortedColTbGrid.dataItem(row);
											item.ordinal --;
							    			ret.push(item.ordinal);
							    			item_.ordinal ++;
							    		});
									}
									return ret;
								},
								down : function(rows,ds){
									var item = sortedColTbGrid.dataItem(rows[rows.length - 1]),ret = null;
									if(item.ordinal < ds.length){
										var item_ = ds[(item.ordinal + 1)];//下一个
										if(item_){
											ret = [];
											$.each(rows,function(i,row){
								    			item = sortedColTbGrid.dataItem(row);
								    			item.ordinal ++;
								    			ret.push(item.ordinal);
								    			item_.ordinal --;
								    		});
										}
									}
									return ret;
								}
							};
							function upsidedown(t){
								var rows = sortedColTbGrid.select();
								if(rows.length == 0){
						    		alert("请选择!");
						    	}else{
						    		
									var ds = sortedColTbGrid.dataSource.data(), rets = upsidedownfuncs[t](rows,ds);
									
									ds.sort(function(a,b){
										return a.ordinal - b.ordinal;
									});
									
									var dataSource = new kendo.data.DataSource({
										  data:ds
									});
									
									sortedColTbGrid.setDataSource(dataSource);
									
									if(rets){
										var selects = [];
										$.each(rets,function(i,v){
											selects.push("#sortedColTb tr:eq(" + v + ")");
										});
										sortedColTbGrid.select(selects.join(","));
									}
						    	}
							}
							
							
							 $("#upBt").kendoButton({
								imageUrl : "${contextPath}/images/arrow_up_16.png",
								click : function(e){
									upsidedown('up');
								}
							});
							 $("#downBt").kendoButton({
								imageUrl : "${contextPath}/images/arrow_down_16.png",
								click : function(e){
									upsidedown('down');
								}
							});
						 </script>
					</div>
					<div class="tabstrips">
						<%@include file="/WEB-INF/views/datamgr/dataset/params.jsp" %>
					</div>
				</div>
				<div id="message-div" style="overflow: auto; height:200px;"></div>
				</div>
			</div>
		</div>
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

		
		//初始化验证按钮
		$("#button1").kendoButton({
			imageUrl : "${contextPath}/images/spell-check.png",
			click : function(e){
				saveDataSet(true,contextPath + "/rs/dataset/toSql");
			}
		});

		//初始化确认按钮
		$("#button2").kendoButton({
			imageUrl : "${contextPath}/images/okay.png",
			click : function(e){
				saveDataSet(true,contextPath + "/rs/dataset/save");
			}
		});
		
		//初始化预览按钮
		$("#button-3").kendoButton({
			imageUrl : "${contextPath}/images/eye.png",
			click : function(e){
				
				if(dataSet.id){
					window.open("${contextPath}/mx/dataset/preview?id=" + dataSet.id);
				} else {
					alert('请保存');
				}
				
			}
		});
		
		
		//初始化查询按钮
		$("#searchBt").kendoButton({
			//imageUrl : "${contextPath}/images/search.png"
		});
		
		$("#nextBt").click(function(){
			$("#searchText").data("n-index", ($("#searchText").data("n-index") || 0)*1 + 1);
			window.selectNode();
		});
		//Tab初始化
		$("#tabstrip").kendoTabStrip({
			tabPosition : "bottom",
			scrollable : false,
			animation : {
				open : {
					effects : "fadeIn"
				}
			},
			select: onSelect
		});
		var tabToActivate = $("#Tab2");
		$("#tabstrip").data("kendoTabStrip").activateTab(
				tabToActivate);
		
		function saveDataSet(save,url){
			//获取选择的表信息
			/*var tables = $(".tbl-cls");
			if(tables.length == 0){
				if(save)
					alert("请选择表!");
				return false;
			}*/
			
			//获取选择的列信息
			var columns = getCheckedRows();
			if(columns.length == 0){
				if(save)
					alert("请选择列!");
				return false;
			}
			
			
			var json = {
					id : dataSet.id,
					selectSegments : [],
					fromSegments : [],
					joinSegments : [],
					joinSegmentTree : [],
					whereSegments : [],
					whereSegmentTree : [],
					havingSegments : [],
					havingSegmentTree : [],
					orderBySegments : [],
					groupBySegments : [],
					params : []
			};
			
			//获取连线关系
			$("img").each(function(i,v){
				$this = $(this);
				if($this.hasClass("_jsPlumb_overlay")){
					var msg = $this.data("msg");
					if(msg){
						msg.ordinal = i;
						json.joinSegments.push(msg);
					}
				}
			});
			
			if(json.joinSegments.length > 1){
				$.each(json.joinSegments, function(i, v){
					json.joinSegmentTree.push($.extend(true, {}, v));
				});
				json.joinSegmentTree = transData(json.joinSegmentTree, "targetTable", "sourceTable", "items");
			}
			
			/*$.each(tables,function(i,table){
				$this = $(this);
				var pos = $this.data("position") || $this.closest("div").offset();
				json.fromSegments.push({
					tableName : $this.attr("tableName"),
					tableCN : $this.attr("nameCN"),
					tableId : $this.attr("tableId"),
					tableType : $this.attr("tableType"),
					ordinal : i,
					dataSource : $this.data("dataSource"),
					position :  pos.left + "," + pos.top
				});
			});*/
			var $table = $("#tableName");
			if($table.val()==""){
				alert("请输入表名");
				return;
			}
			json.fromSegments.push({
					tableName : /*$table.val()*/"cell_useradd1",
					tableCN : "自定义表名",
					tableId : "",
					tableType : "",
					ordinal : 0,
					dataSource : "",
					expression :  JSON.stringify($table.data("item"))
				});
			
			var columnLabel,column,isOk = true,$tr = $("#colTb tbody tr");
			$tr.find("input").css({
				"background-color" : ''
			});
			for(var i = 0; i < columns.length; i ++){
				
				column = columns[i];
				if(!column.columnName){
					$tr.eq(i).find("input[name=columnName]").css({
						"background-color" : 'red'
					});
					isOk = false;
				}
				if(!column.tableNameCN){
					$tr.eq(i).find("input[name=tableNameCN]").css({
						"background-color" : 'yellow'
					});
				}
				if(!column.title){
					$tr.eq(i).find("input[name=title]").css({
						"background-color" : 'yellow'
					});
				}
				
				//columnLabel = column.columnName;
				var tableName = "cell_useradd1" ,tableNameCN = "动态表1";
				if(isOk){
					columnLabel = column.tableName + "_0_" + column.columnName;
					var row = {
							title : column.title,
							columnName : column.columnName,
							tableName : column.tableName ,
							tableNameCN : column.tableNameCN ,
							ordinal : i,
							aggregate : column.aggregate,
							group_by : column.group_by,
							output : column.output,
							columnLabel : column.columnLabel
							//expression : column.expression
						};
					if(column.chartCoord){
						if(typeof(column.chartCoord) == 'object')
							row.chartCoord = column.chartCoord.chartCoord;
						else {
							row.chartCoord = column.chartCoord;
						}
					}
					
					if(column.expItem){//表达式
						var expItem = column.expItem;
						row.expression = expItem.expActVal;
						row.expItem = expItem;
						row.expression = {
								columnCode : expItem.expActVal,
							code : "~F{db.row."+ column.columnLabel +"}",
							value :"~F{" + (column.dataSource || {text : '默认'}).text +"." + column.tableNameCN + "."+ column.title +"}",
							valueCode:expItem.expVal,
							FieldLength : column.lenstr || 200,
							FieldType : column.dtype || "string",
							tableNameCN : column.tableNameCN
						};
					} else {
						row.expression = {
								columnCode : "~F{"+ column.dataSource.code +"."+ column.tableName +"."+ column.columnName +"}",
							code : "~F{"+ column.dataSource.code +"."+ column.tableName +"."+ column.columnName +"}",
							value : "~F{"+ column.dataSource.text +"."+ column.tableNameCN +"."+ column.title +"}",
							valueCode : "~F{"+ column.dataSource.text +"."+ column.tableNameCN +"."+ column.title +"}",
							FieldLength : column.lenstr,
							FieldType : column.dtype,
							tableNameCN : column.tableNameCN
						};
						row.columnLabel = columnLabel;
					}
					
					json.selectSegments.push(row);
						//console.log(column);
					if(column.group_by == 'true' || column.group_by == true){
						if(column.tableName){
							json.groupBySegments.push({
								columnName : column.columnName,
								tableName : column.tableName
							});
						} else { //增加列(表达式)
							json.groupBySegments.push({
								columnName : row.expression.columnCode,
								tableName : column.tableName
							});
						}
					}
				}
			}
			
			if(!isOk && save){
				alert('请完成[数据列--字段/表达式] 高亮部分');
				return false;
			}
			
			//排序
			var orderBySegment = sortedColTbGrid.dataSource.data();
			if(orderBySegment && orderBySegment.length > 0){
				$.each(orderBySegment,function(i,v){
					json.orderBySegments.push({
						tableName : v.tableName,
						columnName : v.columnName,
						expItem : v.expItem,
						expression : v.expression,
						sort : (v.order == 'true' || v.order == true) ? "desc" : "asc",
						ordinal : v.ordinal,
						order : v.order,
						title : v.title
					});
				});
			}
			
			isOk = true;
			//过滤、分组条件
			$(".tableCon").each(function(){
				var nodes = $(this).treegrid("getAllNodes"),$this,collection = json.whereSegments,collectionTree = json.whereSegmentTree;
				
				$(this).find("tr input").css({
					"background-color" : ''
				});
				 if(nodes && nodes.length > 1){
					if($(this).attr("id") == 'groupConTree'){
						collection = json.havingSegments;
						collectionTree = json.havingSegmentTree;
					}
					
					function hasChildren($tr){
						
						var children = $tr.treegrid('getChildNodes'),node;
						if(children.length > 0){
							for(var i = 0; i < children.length; i ++){
								if(!$(children[i]).hasClass("parent-cls")){
									return true;
								}
							}
						}
						return false;
					}
					
					for(var i = 0; i < nodes.length; i ++){
						$this = $(nodes[i]), id = $this.treegrid("getNodeId"),pId = $this.treegrid("getParentNodeId");
						
						var treeObject = {
							name : $this.find("td:first").text(),
							id : id,
							pId : pId,
							isParent : $this.hasClass("parent-cls")
						};
						
						
						if(!$this.treegrid("isLeaf") || $this.hasClass("parent-cls")){
							
							var dataItem = $this.find("select").data("kendoDropDownList").dataItem();
							if(dataItem){
								treeObject.connector = dataItem.connector;
							}
							
							if(hasChildren($this)){
								collectionTree.push(treeObject);
							}
							continue;
						}
						
						collectionTree.push(treeObject);
						var job = {
							parentId : pId,
							ordinal : id,
							datasetId : dataSet.id
						},$connector = $this.treegrid("getParentNode");
						
						var fieldInput = $this.find("input[name=field]"),fieldItem = fieldInput.data("item"),
						paramInput = $this.find("input[name=param]"),paramItem = paramInput.data("item");
						
						if(!fieldInput.val()){
							fieldInput.css({
								"background-color" : 'red'
							});
							isOk = false;
						}
						
						if(!paramInput.val()){
							paramInput.css({
								"background-color" : 'red'
							});
							isOk = false;
						}
						
						if(!isOk){
							continue;
						}
						
						if(fieldItem){
							
							var linkConItem = $connector.find("select").data("kendoDropDownList").dataItem();
							if(linkConItem)
								job.connector = linkConItem.connector;//连接条件（AND或OR）
							if(fieldItem.data){
								job.fildData = fieldItem.data;
								job.fieldData = job.fildData;
							}
								
							if(fieldItem.dataStr){
								job.fildData = JSON.parse(fieldItem.dataStr);
								job.fieldData = job.fildData;
							}
								
							if(fieldItem.expression){
								job.expressionl = fieldItem.value || fieldInput.val();
							} else if(fieldItem.item){
								job.tableName = fieldItem.item.tableName;
								job.parameName = job.columnName = fieldItem.item.columnName;//查询参数/列
								job.parameType = job.dtype =  fieldItem.item.dtype;//
							}													
						} else {
							job.expressionl = fieldInput.val();
						}

						if(paramItem){ //表达式
							if(paramItem.expression){//表达式符号
								job.expression = paramItem.value || paramInput.val();
							} else if(paramItem.item){//字段匹配
								job.expression = paramItem.item.tableName + "." + paramItem.item.dname;
							}
							if(job.expression && job.expression.indexOf("cell_useradd") > -1){
								//job.parameType = "Object";
							}
							if(paramItem.dataStr){
								job.paramData = JSON.parse(paramItem.dataStr);
							}
							
						} else {							
							job.expression = paramInput.val();							
						}
						
						
						job.dynamic = $this.find("input[type=checkbox]").is(":checked") ? "Y" : "N";//是否动态条件
						job.operator = $this.find("select[class=jCon]").data("kendoDropDownList").dataItem().value;//
						job.fieldVal = fieldInput.val();
						job.paramVal = paramInput.val();
						collection.push(job);
					}
				}
			});
			

			if(!isOk && save){
				//$("#tabstrip").data("kendoTabStrip").select("li:eq(1)");;
				alert('请完成[过滤条件/分组条件] 高亮部分');
				return false;
			}
			
			/*var dataItem = $select_biaoduan.dataItem();*/
			/*json.databaseId = 0;
			json.systemName = 'default';*/
			/*if(dataItem){
				json.systemName = dataItem.code || 'default';
				json.databaseId = dataItem.id || 0;
			}*/
			var dataItem = $select_biaoduan.dataItem();
			json.databaseId = 0;
			json.systemName = 'default';
			if(dataItem){
				json.systemName = dataItem.code || 'default';
				json.databaseId = dataItem.id || 0;
			}
			
			if(window.initParams){
				var params = window.initParams();
				if(params){
					json.params = params;
				}
			}
			
			/*if(json.fromSegments.length > 1){
				var len = json.joinSegments.length + json.whereSegments.length;
				if(len == 0){
					alert("请关联连接/where条件");
					return false;
				}
			}*/
			
			var jsonStr = JSON.stringify(json);
		
			$.ajax({
				url : url,
				data : {jsonResult : jsonStr,datasetId : dataSet.id},
				type : 'post',
				dataType : 'json',
				success : function(data){
					if(data){
						if(data.statusCode == 200){
							
						}else if(data.statusCode == 500) {							
							outError(data.message);
						}
						
						if(data.time){
							outError("执行时间为:" + data.time + "s 超过5秒!");
							return false;
						}
						
						if(data.id){
							dataSet = data;
							if(save){
								alert(data.message || '操作成功!');
							}
						}
						$("#message-div").html(data.sql);
					}
				},
				error : function(data){
					if(data){
						outError(data.responseText, true);
					}
				} 
			});
		}
		
		function outError(message, error){
			alert(message);
			var $error = $("<span>").css({
				color : 'red'
			}).text(message);
			$("#message-div").html(error ? message : $error);
		}
		
		//卡片选中后隐藏与现实处理
		function onSelect(e)
		{
			$("#tabstrip-1").css({
				height : x_height,
				'overaflow-x' : 'hidden'
		    });
		   
		   var itemId = e.item.id;
		   
		   if(itemId != 'Tab1'){
			   JP.hideAllConnections();
		   }else{
			  JP.showAllConnections();
		   }
		  
		   //切换卡片时会把table 位置置为 0
		   $(".tbl-cls").each(function(){
			   var $this = $(this);
			   var pos = $this.data("expression");
			   if(itemId != 'Tab1' && !pos){
			   		$this.data("expression",$this.closest("div").offset());
			   }else if(itemId == 'Tab1'){
				   $this.data("expression",undefined);
			   }
		   });
		   
		   if(tabfuncs[itemId]){
			   tabfuncs[itemId](e);
		   }
		   
		   
		   saveDataSet(false,contextPath + "/rs/dataset/saveHistory");
		   
		   //if(dataSet.id)
		   
		}
		
		var tabfuncs = {
			Tab2 : function(e){
			
				var grid = $("#colTb").data("kendoGrid");

				/*dataSource = new kendo.data.DataSource({
					  data:getCheckedRows()
				});
				grid.setDataSource(dataSource);*/
				
			},
			Tab3 : function(e){
				
				initMenuEvent($("#conditionTree"));
				
			},
			Tab4 : function(e){
				
				initMenuEvent($("#groupConTree"));
				
			},
			Tab5 : function(e){
				
				var colTbGrid = $("#colTb").data("kendoGrid");
				var dataSource = colTbGrid.dataSource.data(),tmp = new Object(),$this;
				var colTbtTmp = new Object(),key;
				if(dataSource && dataSource[0]){
					$.each(dataSource,function(i,row){
						key = row.tableName + "_" + row.columnName;
						colTbtTmp[key] = row;
					});
				}
				
				dataSource = sortedColTbGrid.dataSource.data();
				if(dataSource && dataSource[0]){
					$.each(dataSource,function(i,row){
						key = row.tableName + "_" + row.columnName;
						if(key in colTbtTmp){
							row.title = colTbtTmp[key].title  || row.fname;
						}
						tmp[key] = row;
					});
				}
				var datas = [];
				$(".tbl-cls").each(function(i,table){
					$this = $(this);
					var tableName = $this.attr("tableName");
					var nameCN = $this.attr("nameCN");
					
					$this.find("tr").each(function(){
						var jsonStr = $(this).attr("fieldjson");
						if(jsonStr){
							var row = $.parseJSON(jsonStr);
							key = tableName + "_" + row.dname;
							if(row){
								if(!(key in tmp)){
									row.ordinal = '';
									row.order = false;
									row.columnName = row.dname;
									row.title = row.fname;
									row.tableName = tableName;
									row.tableNameCN = nameCN;
									if(key in colTbtTmp){
										row.title = colTbtTmp[key].title  || row.fname;
									}
									row.tableColumn = nameCN + "." + row.title;
							    	datas.push(row);
								}
							}
						}
					});
					
				});
				
				dataSource = new kendo.data.DataSource({
					  data:datas
				});
				
				availableColTbGrid.setDataSource(dataSource);
			}
		};
	</script>
</body>
</html>