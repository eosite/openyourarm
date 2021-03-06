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
    
    request.setAttribute("operateTable", !RequestUtils.getBoolean(request, "isBind") //
    		&& RequestUtils.getLoginContext(request).isSystemAdministrator());
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表信息管理</title>
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
ul li
{
	list-style-type:none;
}
</style>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
var mtxx = {
	fn : '${fn}',
	targetId : '${targetId}',
	tableid : '${tableid}',
	parent : window.opener || window.parent,
	contextPath : '${pageContext.request.contextPath}',
	url : '${pageContext.request.contextPath}/mx/form/defined'
//	admin : ${operateTable}
};

mtxx.getDictByCode = function(code,fn){
	$.ajax({
		url : mtxx.url + '/getDictByCode',
		data : {code : code},
		type : 'post',
		dataType : 'json',
		async : false,
		success:function(data){ 
			if(fn)
				fn(data);
		}
	});
};

/**
*字段类型
*/
mtxx.getDictByCode('dType',function(data){
	data = data || new Array(),mtxx.dTypeSourceObj = new Object(), //
	sorts = {
		 i4 : ['integer', 'number', 'long'],
         r8 : ['float', 'double'],
         datetime : ['timestamp', 'date'],
         image : ['blob'],
         string : ['clob'],
         char : ['boolean']
	};
	$.each(data,function(i,v){
		mtxx.dTypeSourceObj[v.code] = v;
		if((v.code in sorts) && (sorts[v.code]).length){
			$.each(sorts[v.code], function($i, $v){
				mtxx.dTypeSourceObj[$v.toLowerCase()] = v;
			});
		}
	});
	mtxx.dTypeSource = new kendo.data.DataSource({
		data : data
	});
});

/**
 * 当前表字段
 */
 
mtxx.dataTemplate = {
	dType : 'string',
	text : '',
	strlen : 200
};
mtxx.currentColumns = [];
mtxx.currentTable = '';
mtxx.isBind = "${isBind}" || false;

function Queue(w){
	
	this.window = w || window;
	this.funcs = [];
	
	this.add = function(fn){
		!!fn && (this.funcs.push(fn));
	};
	
	this.fire = function(){
		var i = 0, len = this.funcs.length, fn;
		if(len){
			for(; i < len; i ++){
				if($.isFunction(fn = this.funcs[i])){
					fn.call(this.window);
				}
			}
		}
	};
}

var ztreeFuncs = {
	ztreeBeforeClick: function (treeId, treeNode, clickFlag) {
		var data = {};
		if (!treeNode.isParent){
			mtxx.parentNode = treeNode.getParentNode();
			
			var tcs = getColumnsByTableId(treeNode.id, treeNode.tableName, treeNode.R);
			data = tcs || {};
			mtxx.currentTable = data.table;
		}
		set(data.table || treeNode);
		if("${isBind}"){
			initCurrentColumns(mtxx.currentColumns = data.columns || []);
		} else {
			var dataSource = getDataSource({
				data:data.columns || []
			});
			mtxx["grid-1"].setDataSource(dataSource);
		}
		mtxx["grid-1"].refresh();
	},
	selectNodeById : function(id){
		setTimeout(function(){
			var node = mtxx.zTree.getNodeByParam("id", id, null);
			if(node){
				mtxx.zTree.selectNode(node);
			}
		}, 1000);
	}
};

function iterator(nodes){
	if(nodes && nodes.length){
		$.each(nodes, function(i,v){
			if(v.tableName){
				v.icon = "${contextPath}/images/table_tab.png";
			}
			if(v.children && v.children.length){
				iterator(v.children);
			}
		});
	}
}

//重新加载zTree事件
var utableTreeUrl = mtxx.contextPath + "/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?1=1";
var cellUrl = mtxx.contextPath + "/rs/isdp/treeDot/getTreeDotByParentIdInit?1=1";
var setting = {
		async:{
			enable:true,
			url:utableTreeUrl,
			autoParam:["indexId","nlevel=level"],
			dataFilter : function(treeId, msg, ret){
				iterator(ret);
				return ret;
			}
		},
		data: {
			simpleData:{
				enable:true,
				idKey : 'indexId',
				pIdKey:"parentId"
			},
			key:{
				name:"indexName"
			}
		},
		callback: {
			beforeClick: ztreeFuncs.ztreeBeforeClick
		},
		view: {
			selectedMulti: false,
			fontCss: function(treeId, treeNode) {
				return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
			}
		}
	};
function initZTree(tableType, ds){
	var zTreeObj = $("#tree"), data = null;
	if(tableType==1){
		setting.async.url = utableTreeUrl+"&type=1&tableType=2&systemName="+ds;
    	//$.fn.zTree.init(zTreeObj, setting); 
	}else if(tableType==2)	{
		setting.async.url = cellUrl+'&systemName='+ds;
    	//$.fn.zTree.init(zTreeObj, setting); 
	}else if(tableType==3){
		setting.async.url = utableTreeUrl+"&type=1&tableType=12&systemName="+ds;
		//$.fn.zTree.init(zTreeObj, setting); 
	}else if(tableType==4){
		setting.async.url = utableTreeUrl+"&type=4&tableType=12&systemName="+ds;
		data = [{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|',isParent:true}];
		//$.fn.zTree.init(zTreeObj, setting, [{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|',isParent:true}]); 
	}else if(tableType==5){
		//$.fn.zTree.init(zTreeObj, setting, [{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}]); 
		data = [{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}];
	}
	mtxx.zTree = $.fn.zTree.init(zTreeObj, setting, data); 
}

var q = new Queue(this);

q.add(function(){
	
	var $select_biaoduan = $("#select-biaoduan").kendoDropDownList({
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
      	  changeFunc(this.value());
        }
 	}).data("kendoDropDownList");
	
	var url = "${contextPath}/mx/form/defined/getDictByCode/?code=tableType";
	$("#create_table_type").kendoDropDownList({
	//	 dataSource : [
	//		{name:"普通表",value:"1"}
	//	 ],
		 
		 dataSource : {
				transport : {
					read : {
						dataType : "json",
						url : url
					}
				}
		 },
	//	 enable: false,
		 dataTextField: "name",
		 dataValueField: "value",
		 index : 0,
		 change : function(e) {
			var biaoduan = $select_biaoduan.dataItem();
			var typeItem = e.sender.dataItem();
			initZTree(typeItem.code, biaoduan.code);
		}
	});
	
	$("#create_table_sort").kendoDropDownList({
		dataTextField: "indexName",
		dataValueField: "indexId",
		dataSource : getSortDataSource("${databaseCode}")
	});
	
	/**
	*	验证
	*/
	$("#create_table_test_btn").kendoButton({
		imageUrl : mtxx.contextPath + '/images/filter.png',
		click : function(){
			validateFunc(true);
		}
	});
	
	
	getKendo($("#select-biaoduan")).value("${databaseCode}" || "default");
	
});

function changeFunc(systemName){
	var k = getKendo($("#create_table_sort"));
	
  initZTree(window.getTableType(), systemName);
  !!k && (k.setDataSource(getSortDataSource(systemName)));
}

q.add(function(){
	initZTree(1, getSystemName());
});


function getTableType(){
	var $type = $("#create_table_type").data("kendoDropDownList");
	if($type){
		return $type.value();
	}
	return 1;
}

function getSystemName(){
	var k = null,
	SystemName = !!(k = getKendo($("#select-biaoduan"))) ? k.value() : "${databaseCode}";
	return SystemName || "default";
}

function getKendo(jq){
	var data = jq.data();
	if(data){
		for(var key in data){
			if(key.toLowerCase() == ("kendo" + jq.data("role"))){
				return jq.data(key);
			}
		}
	}
	return null;
}

function getSortDataSource(systemName){
	return new kendo.data.DataSource({
		type:"json",
		transport:{
			read:{
				contentType:"application/json",
				url:mtxx.contextPath + "/rs/isdp/cellUTableTree/getUtableTreeByTableType?systemName=" + systemName ,
				type: "POST",
                dataType:"JSON"
			},
			parameterMap : function(options, operation) {
                if (operation == "read") {
            		options.systemName = systemName;
                	return JSON.stringify(options);
                }
             }
		}
	});
}

	$(function(){
		q.fire();
		var $create_table_name = $("#create_table_name");
		var datas = '';
		var $p = mtxx.parent;
		if(mtxx.tableid){//修改表
			var tcs = getColumnsByTableId(mtxx.tableid);
			if(tcs){
				set(tcs.table);
				datas = tcs.columns;
			}
		} else if($p){
			var func = mtxx.fn ? $p[mtxx.fn] : null,parentMsg = null,
			target = mtxx.targetId ? $p.document.getElementById(mtxx.targetId) : null;
			if(func){
				parentMsg = func.call(target);
			} else if (target){
				parentMsg = target.value;
			}
			if(parentMsg){
				var linkageControl = parentMsg.linkageControl,
				tableMsg = parentMsg.tableMsg;
				if(linkageControl){
					linkageControl = JSON.parse(linkageControl);
				}
				if(tableMsg){
					tableMsg = JSON.parse(tableMsg);
					if(tableMsg && tableMsg[0]){
						var table = tableMsg[0].table;
						var columns = tableMsg[0].columns;
						mtxx.dataSetId = tableMsg[0].dataSetId;
						if(table){
							set(table);
							getKendo($("#select-biaoduan")).value(table.systemName);
							changeFunc(table.systemName);
							var tcs = getColumnsByTableId(mtxx.tableid, table.tableName, table.R);
							if(tcs){
								initCurrentColumns(mtxx.currentColumns = tcs.columns);
								mtxx.currentTable = tcs.table;
							}
						}
						if(linkageControl && linkageControl[0] && columns){
							var tmp = new Object();
							$.each(columns,function(i,v){
								var t = mtxx.currentColumnsObj.ids[v.fieldId];
								if(t){
									setValues(t,v);
								}
								tmp[v.id] = v;
							});
							$.each(linkageControl,function(i,v){
								var t = tmp[v.id];
								if(t){
									$.extend(v,t);
								}
							});
						}
						if(table){
							ztreeFuncs.selectNodeById(table.id);
						}
					}
					
				}
				datas = linkageControl;
			}
		}
		
		initGridOne(datas || [mtxx.dataTemplate]);
		
		//创建表确认事件
		var validate = $("#create_table_content").kendoValidator();
		
		$("#create_table_submit_btn").kendoButton({
			imageUrl : mtxx.contextPath + '/images/database_save.png'
		}).on("click",function(e){
			
			if("${isBind}" && mtxx.currentTable){
				bindFunc();
			} else {
				if(validate.data("kendoValidator").validate()){
					if(validateFunc()){
						return false;
					}
					var params = {
						name : $create_table_name.val(),
						systemName : getSystemName(),
						indexId : $("#create_table_sort").data("kendoDropDownList").value(),
						tableid : mtxx.tableid,
						columns : JSON.stringify(mtxx["grid-1"].dataSource.data())
					};
					
					if(params.indexId){//
						 
					} else {
						var node = getTreeSelected();
						if(!node.tableName){
						}
						params.indexId = node.indexId;
						params.R = true;
					}
					var myUrl = mtxx.contextPath + "/rs/isdp/table/saveTableInfo";
					if(params.R){
						myUrl = mtxx.contextPath + "/mx/isdp/rdataTable/saveTableInfo?tableType="+$("#cTableType").val();
					}
					$.ajax({
						type: "POST",
						url: myUrl,
						dataType: 'json',
						async : false,
						data: params,
						error: function(data){
							alert('服务器处理错误！');
						},
						success: function(data){
							if(data != null && data.message != null){
								alert(data.message);
							} else {
								mtxx.tableid = data.table.id;
								initCurrentColumns(mtxx.currentColumns = data.columns);
								mtxx["grid-1"].setDataSource(new kendo.data.DataSource({
									  data:data.columns
								}));
								if("${isBind}"){
									mtxx.currentTable = data.table;
									bindFunc();
								} else {
									mtxx.zTree.reAsyncChildNodes(null,'refresh');
									ztreeFuncs.selectNodeById(mtxx.tableid);
								}
								alert('操作成功!');
							}
						}
					});
				}
			}
		});
		mtxx.onload = true;
		
		
		var selections = ["name", "tableName"];
		
		$("button[t=search]").on("click.search", function() {
			if ($("#searchText").val()) {
				var fn = $(this).data("fn");
				!fn && ($(this).data("fn", fn = new ztreeFunc($("#tree"))));
				fn.highlight(selections, $("#searchText").val());
			}
		});
		
	});
	
	/**
	 * 验证
	 */
	function validateFunc(validate){
		var data = mtxx["grid-1"].dataSource.data(),row,undo = false;
		h1 : for(var i = 0; i < data.length; i ++){
			row = data[i];
			if(row.dType == 'string'){
				for(key in mtxx.dataTemplate){
					if(!row[key]){
						mtxx['grid-1'].select("#grid-1 tbody tr:eq(" + i + ")");
						undo = true;
					}
				}
			} else {
				//row.strlen = null;
			}
		}
		if(undo){
			alert('请完成高亮选中内容!');
		} else if(validate){
			alert('验证成功!');
		}
		
		return undo;
	}
	
	/**
	* 返回
	*/
	function bindFunc(wdataSet){
		
		var columns = new Array(),ds = mtxx['grid-1'].dataSource.data();
		var fields = ['id','text','fieldName','strlen','dType','fieldId'],obj;//只获取需要的字段
		for(var i = 0; i < ds.length; i ++){
			obj = new Object();
			$.each(fields,function(index,v){
				obj[v] = ds[i][v];
			});
			columns.push(obj);
		}
		
		mtxx.currentTable.systemName = getSystemName();
		mtxx.currentTable.databaseId = getKendo($("#select-biaoduan")).dataItem().id || 0;
		mtxx.currentTable.tableType = window.getTableType();
		var data = {
			table : mtxx.currentTable,
			columns : columns,
			wdataSet : wdataSet,
			dataSetId : mtxx.dataSetId
		};
		if(!wdataSet && "${param.type}" == "update"){
			window.createUpdateSource([data]);
		} else {
			var wds = {};
			a2b(["id", "dataSetName"], wdataSet, wds);
			data.wdataSet = wds;
			returnFunc(data);
		}
		/* var str = JSON.stringify(data);
		if(mtxx.parent){
			var func = mtxx.parent[mtxx.fn],target = mtxx.parent.document.getElementById(mtxx.targetId);
			if(func){
				func.call(target, data);
			} else if (target){
				target.value = str;
			}
		} */
	}
	
	/**
	 * 生成更新集、表单查询集
	 * @param jq
	 */
	function createUpdateSource(tableMsgArr){
		$.ajax({
		    url: mtxx.contextPath + '/mx/form/defined/createUpdateSource',
		    type: "post",
		    data: {
		    	pageId : mtxx.pageId,
		    	cell : 'CELL ',
		    	tableMsgArr : JSON.stringify(tableMsgArr)
		    }, 	    
		    dataType: "json",
		    success : function(data){
		    	if(data && data[0])
		    		mtxx.dataSetId = data[0].dataSetId;
		    	$("button[t='getUpdateSet']").click();
		    }
		});
	}
	
	function returnFunc(data){
		var str = JSON.stringify(data);
		if(mtxx.parent){
			var func = mtxx.parent[mtxx.fn],target = mtxx.parent.document.getElementById(mtxx.targetId);
			if(func){
				func.call(target, data);
			} else if (target){
				target.value = str;
			}
		}
	}
	
	function initCurrentColumns(currentColumns){
		mtxx.currentColumnsObj = {
			ids : new Object(),
			names : new Object()
		};
		if(currentColumns && currentColumns[0]){
			$.each(currentColumns,function(i,v){
				mtxx.currentColumnsObj.ids[v.fieldId] = v;
				mtxx.currentColumnsObj.names[v.text] = v;
			});
		} else {
			
		}
	}
	
	function initGridOne(datas){
		$.each(datas,function(i,v){
			for(var k in mtxx.dataTemplate)
				v[k] = v[k] || mtxx.dataTemplate[k];
		});
		initCurrentColumns(mtxx.currentColumns);
		var options = {
				height : '88%',
				autoSync : true ,
				scrollable : true,
				editable : true,
				selectable : "multiple,row",
				dataSource : datas,
				columns : initColumns(datas)
		};
		
		if(!mtxx.isBind){
			options.toolbar = kendo.template("<button class='k-button' id='grid-1-toolbar-create'>增加</button>");
		}
		
		mtxx["grid-1"] = $("#grid-1").kendoGrid(options).data("kendoGrid");
		
		$("#grid-1-toolbar-create").kendoButton({
			imageUrl : mtxx.contextPath + '/images/bullet_add.png',
			click : function(e){
				datas = mtxx["grid-1"].dataSource.data();
				datas.splice(0,0,{
					dType : 'string',
					text : '',
					strlen : 200
				});
				mtxx["grid-1"].setDataSource(new kendo.data.DataSource({
					  data:datas
				}));
			}
		});
	}
	
	function initColumns(datas){
		var columns = [
			{
				title : '名称',
				field : 'text',
				editor : function(container,options){
					var $tr = container.closest('tr'),index = $tr.index(),
					grid = $tr.closest('[data-role=grid]').data('kendoGrid');
					$("<input/>",{
						name : options.field,
						class : 'k-textbox'
					}).appendTo(container).change(function(){
						grid.dataSource.data()[index][options.field] = $(this).val();
					});
				}
			},
			{
				title : '字段类型',
				field : 'dType',
				template : function(dataItem){
					var v = mtxx.dTypeSourceObj[dataItem.dType];
					if(v){
						return v.name;
					} else {
						return '';
					}
				},
				editor : function(container,options){
					var $tr = container.closest('tr'),index = $tr.index(),
					grid = $tr.closest('[data-role=grid]').data('kendoGrid');
					var dataItem = grid.dataItem($tr);
					$("<input>",{
						name : options.field
					}).appendTo(container).kendoDropDownList({
						dataValueField:'code',
						dataTextField:'name',
						dataSource:mtxx.dTypeSource,
						change:function(e){
							grid.dataSource.data()[index][options.field] = e.sender.value();
				   }});
				}
			},
			{
				title : '字段长度',
				field : 'strlen',
				editor : function(container,options){
					var $tr = container.closest('tr'),index = $tr.index(),
					grid = $tr.closest('[data-role=grid]').data('kendoGrid');
					$("<input>",{
						name : options.field
					}).appendTo(container).change(function(e){
						grid.dataSource.data()[index][options.field] = $(this).val();
					}).kendoNumericTextBox();
				}
			},{ title: "操作", width: "120px",template : function(){
		    	return "<button class='k-button k-delete' onclick='deletehandler(this);' >删除</button>";
		    } }			
		];
		if("${isBind}"){
			columns.splice(1,0,{
				title : '绑定字段',
				field :'fieldId',
				template : function(dataItem){
					var v = mtxx.currentColumnsObj.ids[dataItem.fieldId],ret;
					if(v){
						ret =  v.text;
					} else {
						v = mtxx.currentColumnsObj.names[dataItem.text];
						if(v)
							ret = dataItem.text;
					}
					if(v && mtxx.onload){
						setValues(v, dataItem);
					}
					return ret || "";
				},
				editor : function(container,options){
					var $tr = container.closest('tr'),index = $tr.index(),
					grid = $tr.closest('[data-role=grid]').data('kendoGrid');
					var dataItem = grid.dataItem($tr);
					$("<input>",{
						name : options.field
					}).appendTo(container).kendoDropDownList({
						dataValueField:'fieldId',
						dataTextField:'text',
						dataSource:mtxx.currentColumns,
						change:function(e){
							var $this = e.sender;
							var data = grid.dataSource.data()[index];
							data[options.field] = $this.value();
							setValues($this.dataItem(), data);
							mtxx["grid-1"].refresh();
				   }});
				}
			});
		}
		
		return columns;
	}
	
	/**
	* 根据tableId 获取table及列信息
	* return {
		table : o
		columns : array
	}
	*/
	var fieldsMapping = {
			dType : "dtype",
			fieldId : 'FieldId',
			fieldName : 'dname',
			listId : 'listid',
			strlen : 'strlen',
			tableId : 'tableid',
			tableName : 'tablename',
			tableNameCN : 'tableNameCN',
			text : 'fname'
	};
	
	function getColumnsByTableId(tableId, tableName, R){
		var url = "/rs/isdp/table/getColumnsByTableId", o = null;
		R && (url = "/mx/isdp/rdataTable/getColumnsByTableId");
		
		$.ajax({
			url : mtxx.contextPath + url,
			type : 'post',
			dataType : 'json',
			data : {
				tableid : tableId,
				systemName : getSystemName()
			},
			async : false,
			success : function(data){
				o = data || {};
			}
		});
		
		var columns = window.getColumns({
			tableId : tableId,
			tableName : tableName
		}, true);
		
		if(columns && columns.length){
			var collection = [], column;
			$.each(columns, function(i, c){
				collection.push((column = {}));
				$.each(fieldsMapping, function(k, v){
					column[k] = c[v];
				});
			});
			o.columns = collection;
		}
	//	o.columns = columns;
		return o;
	}
	
	function deletehandler(o){
		var item = mtxx["grid-1"].dataItem($(o).closest('tr'));
		if("${isBind}"){
			mtxx["grid-1"].removeRow(o);
		}else if(item && item.tableName){
			if(confirm("你确定删除吗?")){
				var node = getTreeSelected();
				var myUrl = mtxx.contextPath + "/rs/isdp/table/deleteColumn";
				
				if(node.R){
					myUrl = mtxx.contextPath + "/mx/isdp/rdataTable/deleteColumn";
				}
				$.post(myUrl,{
					tablename : item.tableName,
					FieldId : item.fieldId,
					listId : item.listId,
					dname : item.fieldName,
					systemName : getSystemName()
				},function(data){
					if(data != null && data.message != null){
						var rst = mtxx["grid-1"].removeRow(o,false);
						alert(data.message);
					} else {
						alert('操作成功!');
					}
				},'json');
			}
		}
	}
	
	var DMAPPING = {
		2 : 1
	};
	
	function set(table){
		mtxx.tableid = table.tableName ? table.id : "";
		$("#create_table_sort").data("kendoDropDownList").value(table.indexId);
		
		$("#create_table_type").data("kendoDropDownList").value(DMAPPING[table.tableType]);
		
		$("#create_table_name").val(table.name || "");
		$("#create_table").text(table.tableName || "");
	}
	
	mtxx.fields = [
		//'listId',
		//'tableId',
		//'tableName',
		//'tableNameCN',
		'fieldId',
		'dType',
		'fieldName',
		'strlen'
	];
	function setValues(f,t){//指定列赋值
		$.each(mtxx.fields,function(i,v){
			t[v] = f[v];
		});
	}
	
	function getDataSource(options){
		return new kendo.data.DataSource(options);
	}
</script>
</head>
<body>
	
	 <div id="container">
		<div id="vertical" style='border:0px;'>
			
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; " ><img
							src="${contextPath}/images/setting_tools.png"
							style="vertical-align: middle;" /> 
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">表信息管理</span>
						</td>
						<td style="text-align:right;">
							<button id="create_table_test_btn" class="k-button">验证</button>
					 		<button id="create_table_submit_btn" class="k-button">保存</button>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="center-pane">
				<div style="height: 922px; position: absolute; top: 0px; width: 300px; left: 0px;" role="group" class="k-pane k-scrollable">
						<ul>
							<li style="padding-left: 10px;">
						        标段
						     <select id="select-biaoduan" style="width: 120px; display: none;" data-role="dropdownlist"></select>
						    </li>
						</ul>
						<ul style=""> 
							<li style="padding-left: 10px;">
								<input id="searchText" type="text" class="k-textbox" style="width: 85px;">
						        <button id="searchBt" t='search' type="button" data-role="button" class="k-button k-button-icontext" role="button" aria-disabled="false" tabindex="0"><img alt="icon" class="k-image" src="/glaf/images/search.png">搜索</button>
							</li>
						</ul>
						<hr>
						<button class='k-button' t='add-sort' title="新增分类">
							新增分类
						</button>
						<button class='k-button' t="update-sort" title="修改分类">
							修改分类
						</button>
						
						<c:if test="${operateTable eq true }">
							<button class='k-button' t="delete-table" title="删除表">
								删除表
							</button>
						</c:if>
						
						<ul id="tree" class="ztree"></ul>
				</div>
				
				<div id="create_table_content" style='margin-top: 20px;float: right;border:0px;'>
					<fieldset style="border:#DBDBDB dashed 1px;"  >
						<legend style="font-size:12px;font-weight:800; ">创建表信息</legend>
					 	<table cellspacing="5" border="0" style="width:90%">
					 		<tr>
					 			<td align="right">创建表类型</td>
					 			<td>
					 				<input id="create_table_type" />
					 			</td>
					 			<td align="right">业务表分类</td>
					 			<td>
					 				<input id="create_table_sort" />
					 			</td>
					 			<td align="right" width="60px">表名(中文)</td>
					 			<td>
					 				<input type="text" id="create_table_name" name="create_table_name" class="k-textbox" required validationMessage="必填项" />
					 				<span class="k-invalid-msg" data-for="create_table_name"></span>
									<select id="cTableType" name="cTableType" class="k-select" style="height:22px;">
									    <option value="">页面</option>
										<option value="imp">接口</option>
									</select>
					 			</td>
					 			<td align="right" width="60px">表名:</td>
					 			<td>
					 				<span id="create_table" style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;" ></span>
					 				<button class='k-button' t='preview-data'>预览</button>
					 			</td>
					 			<td style="display: none;">
					 				<button class='k-button' t='getUpdateSet'>获取更新设置</button>
					 			</td>
					 		</tr>
					 	</table>
					</fieldset>
				 	<div id='grid-1' style='margin-top: 10px;' ></div>
				</div>
			</div>
		</div>
	</div>
	<div style="display:none;width:300px; height:60px;" id="dialog-01">
		<div id="combobox-01"></div>
		<button class='k-button' t='sure'>确定</button>
	</div>
</body>
<script type="text/javascript">
var $vertical = $("#vertical");

$vertical.css({
	height : $(window).height()
}).kendoSplitter({
	orientation: "vertical",
	panes: [
		{ collapsible: false , resizable : false, scrollable: false ,size : '32px'},
		{ collapsible: true, resizable : true, scrollable: false},
		{ collapsible: true, resizable : true, scrollable: false,size : '4%'}
	],
	layoutChange : onResize,
});

$("#center-pane").kendoSplitter({
	orientation: "horizontal",
	panes: [
		{ collapsible: true, resizable : true, scrollable: true,size : '249px'},
		{ collapsible: true, resizable : true, scrollable: true},
		{ collapsible: true, resizable : true, scrollable: false,size : '340px'}
	]
});

function onResize(e){
	
	$vertical.css({
		height : $(window).height()
	});
	
}
var btnFuncs = {
	
		'preview-data' : function(e){ //数据预览
			var tableName = $("#create_table").text();
			if(tableName){
				
			} else {
				alert("请选择表!");
			}
		},
		getUpdateSet : function(e){ // 获取更新设置
		if(mtxx.currentTable){
			$.ajax({
				url : "${contextPath}/mx/dep/base/depBaseWdataSet/json",
				type : "post",
				dataType : "json",
				data : {
					dataTableName : mtxx.currentTable.tableName
				},
				success : function(ret){
					var rows;
					if(ret && (rows = ret.rows)){
						mtxx.currentTable.tableId = mtxx.currentTable.id;
						if(rows.length){
							if(rows.length > 1){
								var $win = $("#dialog-01").data("kendoWindow");
								$win.open();
								$win.center();
								$("#combobox-01").data("kendoComboBox").setDataSource(new kendo.data.DataSource({
									data : rows
								}));
							} else {
								mtxx.wdataSet = $.extend({}, rows[0]);
							//	bindFunc(mtxx.wdataSet);
							}
						} else { //自动生成更新
							//window.create(mtxx.currentTable);
							mtxx.wdataSet = mtxx.wdataSet || {};
						}
						window.create(mtxx.currentTable);
					}
				}
			});
		} else {
			alert("请选择关联表!");
			return false;
		}
		return true;
	},
	sure : function(e){
		var $kendo = $("#combobox-01").data("kendoComboBox");
		var dataItem = $kendo.dataItem();
		if(dataItem){
			mtxx.wdataSet = $.extend({}, dataItem);
			$("#dialog-01").data("kendoWindow").close();
			bindFunc(mtxx.wdataSet);
		} else {
			alert("请选择更新集!");
		}
	},
	"add-sort" : function(e){
		var k = openSortWindow(), data = {
			parentId : -1,
			nlevel : 0
		};
		var node = window.getTreeSelected();
		if(node){
			data = {
				parentId : node.indexId,
				nlevel : 0
			};
		}
		loadData(data);
	},
	"update-sort" : function(e){
		var node = window.getTreeSelected();
		if(node && !node.tableName){
			var k = openSortWindow();
			loadData({
				indexId : node.indexId,
				indexName : node.indexName
			});
		} else {
			alert('请选择分类!');
		}
	},
	"delete-table" : function(e){
		var node = window.getTreeSelected();
		if(node && node.tableId){
			if(!confirm("你确定删除该表结构吗?")){
				return false;
			}
			var p = node.getParentNode();
			
			var url = node.R ? "/mx/isdp/rdataTable/deleteTable" : "/rs/isdp/table/deleteTable";
			
			if('${operateTable}' == 'true'){
				$.ajax({
					url : mtxx.contextPath + url,
					data : {
						id : node.tableId,
						systemName : window.getSystemName()
					},
					dataType : 'JSON',
					type : 'post',
					success : function(ret){
						if(ret && ret.message){
							alert(ret.message);
						}
						$.fn.zTree.getZTreeObj('tree').removeNode(node);
						if(p){
							ztreeFuncs.selectNodeById(p.id);
							ztreeFuncs.ztreeBeforeClick ("", p, "");
						}
					}
				});
			}
		} else {
			alert("请选择表格!");
		}
	}
};

var MAPPING = {
	1 : 2,
	3 : 12,
	4 : 12
};

function loadData(data){
	
	var type = window.getTableType();
	$.extend(data, {
		tableType : MAPPING[type]
	});
	
	$("#dialog-sort input").each(function(){
		$(this).val('').val(data[this.id]);
	});
}

function getTreeSelected(){
	var tree = $.fn.zTree.getZTreeObj('tree');
	var nodes = tree.getSelectedNodes();
	return nodes.length ? nodes[0] : null;
}

function openSortWindow(){
	var k = $("#dialog-sort").data("kendoWindow");
	k.center();
	k.open();
	return k;
}



var fields = [ "FieldId", "dataItem", "defVal", 'dtype', "fname", "dname",
"param", 'paramName', "strlen", "tableNameCN", "tablename", 'tableid',
"input", "nullAble", "variable", "output", "input", "update" ];

function a2b(fs, a, b) {
	if (fs && a && b) {
		$.each(fs, function(i, f) {
			b[f] = a[f];
		});
	}
}
function create(node){
	
	var datas = window.getColumns(node);
	
	var wdataSet = {
			id : mtxx.wdataSet.id,
			dataSetDesc : node.name + " 系统自动生成",
			dataSetName : node.name + " 系统自动生成"
	};

	var wdataSetId = wdataSet.id;
	var table = {
		//dataSetCode : 'wds-code-' + wdataSetId,
		tableName : node.name,
		dataTableName : node.dataTableName || node.tableName
	};
	$.extend(wdataSet, table);
	var $bd = $("#select-biaoduan").data("kendoDropDownList")
			.dataItem()
			|| {};
	var $type = {
		code : 1,
		name : '普通表'
	};
	window.a2b([ "id", "tableId", "name" ], node, table);
	var ruleJson = {
		bd : {
			code : $bd.code,
			text : $bd.text
		},
		type : {
			code : $type.code,
			text : $type.name
		},
		columns : [],
		table : table,
		whereClaus : []
	};
	var time = new Date().getTime();
	$.each(datas, function(i, v) {
		var currentTime = time++, param = "col" + currentTime;
		var column = {
			wdataSetId : wdataSetId,
			columnName : v.fname,
			dataColumnName : v.dname,
			defaultVal : v.defVal,
		};
		window.a2b(fields, v, column);
		$.extend(column, {
			param : param ,
			paramName : table.tableName + " " + v.fname + " | 参数-" + currentTime,
			tableNameCN: table.tableName,
			tableid: node.id,
			tablename: table.dataTableName
		});
		ruleJson.columns.push(column);
		if (column.dname == "id") {
		//	column.defVal = "$UUID()";
		//	column.dataItem = "{\"expVal\":\"$UUID()\",\"expActVal\":\"$UUID()\",\"varVal\":[{\"key\":\"$USER_ID(~CONST)\",\"value\":{\"id\":999524752,\"pId\":999524751,\"t\":\"用户自增ID\",\"name\":\"USERID 用户自增ID\",\"value\":\"$USER_ID(~CONST)\",\"isParent\":false,\"code\":\"$USER_ID(~CONST)\",\"level\":1,\"tId\":\"expTree_35\",\"parentTId\":\"expTree_34\",\"open\":false,\"zAsync\":true,\"isFirstNode\":true,\"isLastNode\":false,\"isAjaxing\":false}},{\"key\":\"$OID()\",\"value\":{\"id\":999524755,\"pId\":999524751,\"t\":\"流水号ID，从1开始自增\",\"name\":\"OID 流水号ID\",\"value\":\"$OID()\",\"isParent\":false,\"code\":\"$OID()\",\"level\":1,\"tId\":\"expTree_37\",\"parentTId\":\"expTree_34\",\"open\":false,\"zAsync\":true,\"isFirstNode\":false,\"isLastNode\":true,\"isAjaxing\":false}},{\"key\":\"$UUID()\",\"value\":{\"id\":999524754,\"pId\":999524751,\"name\":\"UUID UUID\",\"value\":\"$UUID()\",\"isParent\":false,\"code\":\"$UUID()\",\"level\":1,\"tId\":\"expTree_36\",\"parentTId\":\"expTree_34\",\"open\":false,\"zAsync\":true,\"isFirstNode\":false,\"isLastNode\":false,\"isAjaxing\":false}}]}";
		
			var data = {
					"expVal" : "$USER_ID(~CONST)",
					"expActVal" : "$USER_ID(~CONST)",
					"varVal" : [ {
						"key" : "$USER_ID(~CONST)",
						"value" : {
							"code" : "$USER_ID(~CONST)",
							"isParent" : false,
							"t" : "用户自增ID",
							"name" : "USERID 用户自增ID",
							"pId" : 999524751,
							"id" : 999524752,
							"value" : "$USER_ID(~CONST)",
							"level" : 1,
							"tId" : "expTree_45",
							"parentTId" : "expTree_44",
							"open" : false,
							"zAsync" : true,
							"isFirstNode" : true,
							"isLastNode" : false,
							"isAjaxing" : false
						}
					} ]
				};
			column.defVal = data.expVal;
			column.dataItem = JSON.stringify(data);
			ruleJson.whereClaus.push($.extend({}, column));
		}
	});
	wdataSet.ruleJson = JSON.stringify(ruleJson);
	var saveUrl = '${contextPath}/mx/dep/base/depBaseWdataSet/saveDepBaseWdataSet';
	$.ajax({
		url : saveUrl,
		data : wdataSet,
		type : "POST",
		dataType : 'JSON',
		success : function(ret) {
			if (ret) {
				mtxx.wdataSet = $.extend({}, ret);
				bindFunc(mtxx.wdataSet);
			}
		}
	});

}

/**
 * 根据tableId 获取table及列信息 return []
 */
function getColumns(tableObj, cell) {
	var collection = [], json = {}, dname;
	var type = $("#create_table_type").data("kendoDropDownList").value();
	var types = [type];
	if(!cell){
		types.push(99);
	}	
	$.each(types,
			function(i, type) {
				tableObj.type = type;
				window.selectFieldByTableId(tableObj, function(data) {
					if (data && data.rows) {
						$.each(data.rows, function(index, row) {
							dname = row.dname;
						//	console.log(row);
							dname != "id" && (row.nullAble = true);
						//	row.output = true;
							row.update = true;
							row.input = true;
							dname == 'ctime' && (row.update = false);
							!json[dname]
									&& ((json[dname] = row) && (collection
											.push(row)));
						});
					}
				});
			});
	return collection;
}

function selectFieldByTableId(tableObj, fn) {
	$.ajax({
		url : "${contextPath}/rs/isdp/cellDataField/selectFieldByTableId",
		type : "POST",
		dataType : "JSON",
		async : false,
		data : {
			tableId : tableObj.tableId,
			type : tableObj.type,
			tableName : tableObj.tableName,
			systemName : window.getSystemName()
		},
		success : function(data) {
			if (fn)
				fn(data);
		}
	});
}

q.add(function(){
	$("button.k-button").on("click.button", function(e){
		var t = $(this).attr("t");
		(t = btnFuncs[t]) && t.call(this, e);
	});
	
	$("#combobox-01").kendoComboBox({
		dataTextField: "dataSetName",
		dataValueField: "id"
	});
	
	$("#dialog-01").kendoWindow({
		title : "请选择更新集"
	});
	
});



function ztreeFunc(jq) {
	this.$jq = jq;
	this.highlights = null;
	this.state = {
		ztree : $.fn.zTree.getZTreeObj(jq.attr("id"))
	};
	this.updateHighlight = function(nodes, highlight) {
		var ztree = this.state.ztree;
		$.each(nodes, function(i, node) {
			node.highlight = highlight || false;
			ztree.updateNode(node);
		});
		return this;
	}; this.highlight = function(keys, value, append) {
		if (!keys || !value)
			return this;
		var ztree = this.state.ztree;
		var nodes = this.highlights;
		if (nodes && nodes.length && !append) {
			this.updateHighlight(nodes, !!append);
		}
		if(!(keys instanceof Array)){
			keys = [keys];
		}
		nodes = [];
		$.each(keys, function(i, key){
			var ns = ztree.getNodesByParamFuzzy(key, value,
					null);
			if(ns != null && ns.length){
				$.each(ns, function(){
					nodes.push(this);
				});
			}
		});
		this.highlights = nodes;
		if (nodes && nodes[0]) {
			$.each(nodes, function(i, node) {
				!node.isParent && (node = node.getParentNode());
				ztree.expandNode(node, true, true, false);
			});
			this.updateHighlight(nodes, true);
		}
		return this;
	}
}

q.add(function(){
	$("#dialog-sort").kendoWindow({
		open : false,
		title : '分类操作'
	});
});

function saveSort(){
	if(!$("#indexName").val()){
		alert('名称不能为空!');
		return false;
	}
	
	var data = {};
	$("#dialog-sort input").each(function(){
		data[this.id] = this.value;
	});
	
	
	var url = '${contextPath}/rs/isdp/cellUTableTree/saveCellUTableTree'; 
	url = "${contextPath}/mx/isdp/rUtabletree/saveRUtabletree";
	console.log(data);
	
	$.ajax({
		url : url,
		type : 'post',
		data : data,
		dataType : 'json',
		success : function(ret){
			if(ret && ret.statusCode == 200){
				alert(ret.message);
				$("#dialog-sort").data("kendoWindow").close();
				var tree = $.fn.zTree.getZTreeObj("tree");
				$.fn.zTree.init($("#tree"), tree.setting);
			}
		}
	});
	
}
</script>
<div style="display:none;width:300px; height:60px;" id="dialog-sort">
	<input type='hidden' name="nlevel" id="nlevel" />
	<input type='hidden' name="indexId" id="indexId" />
	<input type='hidden' name="parentId" id="parentId" />
	<input type='hidden' name="tableType" id="tableType" />
	<input class='k-textbox' type='text' name="indexName" id="indexName" />
	<button class='k-button' onclick='return saveSort();'>确定</button>
</div>
</html>