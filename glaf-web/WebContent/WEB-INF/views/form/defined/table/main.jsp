<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setAttribute("theme", "default");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css" >
<script type="text/javascript">
var fieldTypeControl = [] ;
$.ajax({
	url : '${pageContext.request.contextPath }/mx/form/defined/getDictByCode',
	data : {code : 'FieldTypeControl'},
	type : 'post',
	dataType : 'json',
	async : false,
	success:function(data){ 
		$.each(data,function(i,d){
			var e = {} ;
			e.code = d.code ;
			e.value = d.value
			fieldTypeControl.push(e);
		});
	}
});
var databaseControl = [] ;
$.ajax({
	url : '${pageContext.request.contextPath }/rs/isdp/global/databaseJson',
	type : 'post',
	dataType : 'json',
	async : false,
	success:function(data){ 
		databaseControl =data;
	}
});

var utableTreeUrl = "${pageContext.request.contextPath}/rs/isdp/cellUTableTree/getUtableTreeByTableType?1=1",
cellUrl = "${pageContext.request.contextPath}/rs/isdp/treeDot/getTreeDotByParentId?1=1";

var selectFieldRows = [];

//恢复显示信息
function loadData(jsonobj){
	var tableRows = [];
	$.each(jsonobj,function(index,json){ //循环遍历数据
		var row = {};
		row.databaseId = json.databaseId;
		row.datasetId = json.datasetId;
		row.name = json.name;
		row.title = json.title;
		row.sql = json.sql;
		tableRows.push(row);
		$.each(json.columns,function(index,field){
			selectFieldRows.push(field);
		});
	});
	
	
	$('#select_table_grid').datagrid('loadData',tableRows); //已选择数据集
	$('#selectTableCombobox').combobox('loadData',tableRows);//下拉选择数据集

	initFieldPropertyGrid(); //编辑字段  字段列表
	
	initSelectFieldGrid(); //选择字段 已选择的列
	
	initFieldGrid();//选择字段  字段列表
	
}
var tabsIndex ;
$(function(){
	//下拉
	$('#selectTableCombobox').combobox({
		valueField:'datasetId',   
	    textField:'title',
	    panelHeight:'auto',
	    editable: false,
	    onSelect:function(record){
	    	var queryParams = {};
	    	//queryParams.systemName=record.databaseCode;
	    	//queryParams.tableId=record.TableId;
	    	//queryParams.type=record.tableType;
	    	//queryParams.tableName=record.TableName;
	    	queryParams.datasetId = record.datasetId ;
	    	$('#field_grid').datagrid('load',queryParams); //更新选择字段
	    	//$('#fieldOptDs').val(record.databaseCode);
	    	
	    	//$('#field_form_systemName').val(record.databaseCode);
	    	//$('#field_form_tableid').val(record.TableId);
	    }
	});
	
	//选卡项
	$('#main_tabs').tabs({
		onSelect:function(title,index){
			if(tabsIndex == 3){
				saveProperty();
			}
			tabsIndex = index ;
			if(index===1){ //刷新 选择字段
				
				initFieldPropertyGrid(); //选择字段  字段列表
				
				initSelectFieldGrid(); //选择字段  已选择字段列表
				
				initFieldGrid(); // 编辑字段   字段列表
				
				//如果不是第一个tabs，点击时重新加载
				var selectRows = $('#select_table_grid').datagrid('getData').rows;
				
				if(selectRows.length==0){
					alert("请先选择数据集！");
					$('#main_tabs').tabs('select',0);
					return;
				}
				
		    	$('#selectTableCombobox').combobox('loadData',selectRows); 
		    	$('#selectTableCombobox').combobox('select',selectRows[0].datasetId);
			}else if(index===2){ //刷新 编辑字段
				initFieldPropertyGrid();
			}else if(index===3){ //刷新 编辑链接
				initLinkFieldGrid();
			}
		}
	});
	
	if('${param.isTree}' == 'true'){//如果是树 隐藏第3个选卡
		$('#main_tabs').tabs('close', 2);
	}
	
	//表信息
	$('#table_grid').datagrid({
		url:'${pageContext.request.contextPath}/mx/dataset/json',
		queryParams:'',
		height:365,
		toolbar:"#table_grid_tb",
		rownumbers:true,
		striped:true,
		pagination:true,
		singleSelect:true,
		pageSize:20,
	    columns:[[
	        {
	        	field:'datasetId',
	        	title:'datasetId',
	        	hidden:true
	        },{
	        	field:'name',
	        	title:'名称',
	        	width:150
	        },{
	        	field:'title',
	        	title:'标题',
	        	width:150
	        },{
	        	field:'databaseId',
	        	title:'标段',
	        	width:150,
	        	formatter: function(value,row,index){
	        		var str = "默认" ;
	        		$.each(databaseControl,function(i,d){
	        			if(value == d.id){
	        				str = d.text ;
	        			}
	        		})
	        		return str ;
	        	}
	        },{
        		field:'createTime',
        		title:'创建时间',
        		width:100
	        }
	    ]],
	    onDblClickRow:function(rowIndex, rowData){
	    	var selectRows = $('#select_table_grid').datagrid('getData').rows;
	    	if(selectRows.length>0){
	    		alert("只能选择一个数据集");
	    		return;
	    	}
	    	var row = {};
	    	row.databaseId = rowData.databaseId;
	    	row.datasetId = rowData.dataSetId;
	    	row.name = rowData.name;
	    	row.title = rowData.title;
	    	row.sql = rowData.sql;
	    	row.createTime = rowData.createTime ;
	    	$('#select_table_grid').datagrid('insertRow',{index:0,row:row});
	    }
	});  
	//已选择的表
	$('#select_table_grid').datagrid({
		height:193,
		rownumbers:true,
		striped:true,
		columns:[[
	        {field:'datasetId',title:'datasetId',hidden:true},   
	        {field:'name',title:'名称',width:120},
	        {field:'title',title:'标题',width:150},
	        {field:'databaseId',title:'标段',width:150,formatter: function(value,row,index){
        		var str = "默认" ;
        		$.each(databaseControl,function(i,d){
        			if(value == d.id){
        				str = d.text ;
        			}
        		})
        		return str ;
        	}},
	        {field:'createTime',title:'创建时间',width:150}
	    ]],
	    onDblClickRow:function(rowIndex, rowData){
	    	$(this).datagrid('deleteRow',rowIndex);
	    	//清空已选择记录
	    	selectFieldRows = [];
	    }
	});
	
	//恢复数据
	if('${param.resultsElementId}' != ''){
		var obj = parent.document.getElementById('${param.resultsElementId}').value;
		if(obj != ''){
			var  obj1 = JSON.parse(obj);
			loadData(obj1);
		}
	}
});
//选择字段  字段列表
function initFieldGrid(){
	$('#field_grid').datagrid({
		height:567,
		url:'${pageContext.request.contextPath}/rs/dataset/getSelectJson',
		toolbar:'#field_grid_tb',
		singleSelect:false,
		fitColumns:true,
		rownumbers:true,
		columns:[[
          	{	
          		checkbox : true,
				field:'id'
			},
			{	
				hidden:true ,
				field:'columnName',
				title:'字段',
				width:'250'
			},
		    {	
				field:'title',
				title:'字段名',
				width:'250',
				formatter: function(value,row,index){
					var exp = JSON.parse(row.expression);
	        		var str = exp.tableNameCN+"."+value ;
	        		return str ;
		        }
			}
		]],
		onDblClickRow: function(rowIndex,rowData){
			var datas = $('#select_field_grid').datagrid('getData').rows;
			var addflag = true;
			$.each(datas,function(index,data){
				if(/*rowData.id==data.id &&*/ rowData.columnName==data.columnName){
					alert("已添加字段["+data.tableNameCn+"."+data.title+"]，请勿重复添加");
					addflag = false;
				}
			});
			if(addflag){
				var row = {};
				row.id=rowData.id;
				row.datasetId=rowData.datasetId;
				row.tableName=rowData.tableName;
				row.columnName = rowData.columnLabel ;
				//row.columnLabel = rowData.columnLabel ;
				row.title=rowData.title;
				//{"value":"~F{默认.项目文件收集分类.顺序号}","code":"~F{default.cell_useradd7028.cell_useradd7028_user3}"}
				var exp = JSON.parse(rowData.expression) ;
				row.FieldLength=exp.FieldLength;
				row.FieldType=exp.FieldType;
				row.tableNameCn=exp.tableNameCN;
				row.value = exp.value ;
				row.code = exp.code ;
    			$('#select_field_grid').datagrid('insertRow',{index:datas.length,row:row});
    			selectFieldRows = $('#select_field_grid').datagrid('getData').rows;
			}
		}
	});
}
//选择字段  已选择字段grid
function initSelectFieldGrid(){
	$('#select_field_grid').datagrid({
		height:567,
		toolbar:'#select_field_grid_tb',
		singleSelect:false,
		fitColumns:true,
		rownumbers:true,
		columns:[[
			{	
				checkbox : true,
				field:'id'
			},
			{	
				hidden:true ,
				field:'columnName',
				title:'字段',
				width:'250'
			},
		    {
				field:'title',
				title:'字段名',
				width:'250',
				formatter: function(value,row,index){
	        		var str = (row.tableNameCn?row.tableNameCn:'')+"."+value ;
	        		return str ;
		        }
			}
		]],
		onDblClickRow: function(rowIndex,rowData){
			$('#select_field_grid').datagrid('deleteRow',rowIndex);
			selectFieldRows = $('#select_field_grid').datagrid('getData').rows;
		}
	});
	$('#select_field_grid').datagrid('loadData',selectFieldRows);
}
//编辑字段  字段列表
function initFieldPropertyGrid(){
	$('#field_property_grid').datagrid({
		height:567,
		singleSelect:true,
		fitColumns:true,
		rownumbers:true,
		columns:[[
		    {
				field:'title',
				title:'字段名',
				width:'250',
				formatter: function(value,row,index){
	        		var str = (row.tableNameCn?row.tableNameCn:'')+"."+value ;
	        		return str ;
		        }
			}
		]],
		onClickRow: function(rowIndex,rowData){
			saveProperty();
			
			$('#hidRowIndex').val(rowIndex);
			$('#dictWidgetSource').hide();
			$("tr[name='tableWidgetSource']").hide();
			$('#userWidgetSource').hide();
			var row = $.extend({},rowData);
			setPropertyToComment(row);
		}
	});
	$('#field_property_grid').datagrid('loadData',selectFieldRows);
}


//设置属性值到控件中
function setPropertyToComment(row){
	
	//显示属性
	hiddenRangeComment(row.editor?row.editor:'');
	
	document.getElementById("isShowList").checked = row.isShowList?row.isShowList:false;
	document.getElementById("isFilterable").checked = row.isFilterable?row.isFilterable:false;
	document.getElementById("isSortable").checked = row.isSortable?row.isSortable:false;
	document.getElementById("isMenu").checked = row.isMenu?row.isMenu:false;
	$('#columnWidth').val(row.columnWidth?row.columnWidth:200);
	$('#alignment').combobox('setValue',row.alignment?row.alignment:'left');
	$('#editor').combobox('setValue',row.editor?row.editor:'');
	$('#formatValue').combobox('setValue',row.formatValue?row.formatValue:'');
	//编辑属性
	document.getElementById("isEditor").checked = row.isEditor?row.isEditor:false;
	changeIsEditor(row.isEditor);
	
	document.getElementById("isRequired").checked = row.isRequired?row.isRequired:false;
	//document.getElementById("isLink").checked = row.isLink?row.isLink:false;
	//changeIsLink(row.isLink);
	$('#hidLinkImg').val(row.hidLinkImg);
	
	//$('#linkPageName').val(row.linkPageName?row.linkPageName:'');
	//$('#linkPageId').val(row.linkPageId?row.linkPageId:'');
	$('#defaultVlue').val(row.defaultVlue?row.defaultVlue:'');
	$('#dataValidation').combobox('setValue',row.dataValidation?row.dataValidation:'');
	enableRangeComment(row.dataValidation);
	
	if(row.isNumeric){
		$('#numberMinValue').numberspinner('setValue',row.numberMinValue);
		$('#numberMaxValue').numberspinner('setValue',row.numberMaxValue);
	}
	
	if(row.isDate){
		$('#dateMinValue').datebox('setValue',row.dateMinValue);
		$('#dateMaxValue').datebox('setValue',row.dateMaxValue);
	}
	
	if(row.isDateTime){
		$('#datetimeMinValue').datetimebox('setValue',row.dateMinValue);
		$('#datetimeMaxValue').datetimebox('setValue',row.dateMaxValue);
	}
	
	if(row.isTime){
		$('#timeMinValue').numberspinner('setValue',row.dateMinValue);
		$('#timeMaxValue').numberspinner('setValue',row.dateMaxValue);
	}
	
		
	document.getElementById("isValidate").checked = (row.isValidate?row.isValidate:false);
	changeIsValidate(row.isValidate);
	
	$('#validateMsg').val(row.validateMsg?row.validateMsg:'');
	
	if(row.widgetSourceType==10){
		$('#dictWidgetSource').show();
		$("tr[name='tableWidgetSource']").hide();
		$('#userWidgetSource').hide();
	}else if(row.widgetSourceType==20){
		$('#dictWidgetSource').hide();
		$("tr[name='tableWidgetSource']").show();
		$('#userWidgetSource').hide();
	}else if(row.widgetSourceType==30){
		$('#dictWidgetSource').hide();
		$("tr[name='tableWidgetSource']").hide();
		$('#userWidgetSource').show();
	}
	
	$('#widgetSourceType').combobox('setValue',row.widgetSourceType?row.widgetSourceType:'');
	if(row.widgetSourceType==10){
		$('#wsUserData').val('');
		$('#widgetSourceTableObj').val('');
		$('#wigetSourcetableName').val('');
		
		$('#widgetDictSource').combobox('setValue',row.widgetSource?row.widgetSource:'');
	}else if(row.widgetSourceType==20){
		$('#wsUserData').val('');
		
		var obj = row.widgetSource;
		if(obj){
			$('#widgetSourceTableObj').val(JSON.stringify(obj));
			$('#wigetSourcetableName').val(obj.name);
			
			initTableDataSource();
		}
	}else if(row.widgetSourceType==30){
		$('#widgetDictSource').combobox('setValue','');
		$('#widgetSourceTableObj').val('');
		$('#wigetSourcetableName').val('');
		
		var obj = row.widgetSource;
		if(obj){
			$('#wsUserData').val(JSON.stringify(obj));
		}
	}
}

//保存属性至字段中
function saveProperty(){
	var rows = $('#field_property_grid').datagrid('getData').rows;
	var row ;
	
	if($('#hidRowIndex').val()){
		row = rows[$('#hidRowIndex').val()];
	}
	
	//var row = $('#field_property_grid').datagrid('getSelected');
	if(row){
		//显示属性
		row.isShowList = $('#isShowList').is(':checked');
		row.isFilterable = $('#isFilterable').is(':checked');
		row.isSortable = $('#isSortable').is(':checked');
		row.isMenu = $('#isMenu').is(':checked');
		row.columnWidth = $('#columnWidth').val();
		row.alignment = $('#alignment').combobox('getValue');
		row.editor = $('#editor').combobox('getValue');
		row.formatValue = $('#formatValue').combobox('getValue');
		//编辑属性
		row.isEditor = $('#isEditor').is(':checked');
		row.isRequired = $('#isRequired').is(':checked');
		//row.isLink = $('#isLink').is(':checked');
		//row.linkPageName = $('#linkPageName').val();
		//row.linkPageId = $('#linkPageId').val();
		row.defaultVlue = $('#defaultVlue').val();
		row.dataValidation = $('#dataValidation').combobox('getValue');
		
		row.hidLinkImg = $('#hidLinkImg').val();
		
		row.isValidate = $('#isValidate').is(':checked');
		row.validateMsg = $('#validateMsg').val();
		
		if(row.isNumeric){
			row.numberMinValue = $('#numberMinValue').numberspinner('getValue');
			row.numberMaxValue = $('#numberMaxValue').numberspinner('getValue');
			row.dateMinValue = '';
			row.dateMaxValue = '';
		}else{
			row.numberMinValue = '';
			row.numberMaxValue = '';
		}
		
		if(row.isDate){
			row.dateMinValue = $('#dateMinValue').datebox('getValue');
			row.dateMaxValue = $('#dateMaxValue').datebox('getValue');
		}
		
		if(row.isDateTime){
			row.dateMinValue = $('#datetimeMinValue').datetimebox('getValue');
			row.dateMaxValue = $('#datetimeMaxValue').datetimebox('getValue');
		}
		
		if(row.isTime){
			row.dateMinValue = $('#timeMinValue').numberspinner('getValue');
			row.dateMaxValue = $('#timeMaxValue').numberspinner('getValue');
		}
		
		row.widgetSourceType = $('#widgetSourceType').combobox('getValue');
		if(row.widgetSourceType==10){
			row.widgetSource = $('#widgetDictSource').combobox('getValue');
		}else if(row.widgetSourceType==20){
			if($('#widgetSourceTableObj').val()!=''){
				row.widgetSource =  JSON.parse($('#widgetSourceTableObj').val());
			}else{
				row.widgetSource = [];
			}
		}else if(row.widgetSourceType==30){
			if($('#wsUserData').val() != ''){
				row.widgetSource = JSON.parse($('#wsUserData').val());
			}else{
				row.widgetSource = [];
			}
		}
	}
}

//检查表
function searchTable(){
	var queryParams = {};
	queryParams.nameLike = $('#keyword').val();
//	$('#table_grid').datagrid("options").queryParams;
	//alert($('#keyword').val());
	$('#table_grid').datagrid("load",queryParams);
}


//编辑字段  空间数据源   加载表数据源
function initTableDataSource(){
	var tableobj = $('#widgetSourceTableObj').val();
	saveProperty();
	var params = {};
	if(tableobj != ''){
		tableobj = JSON.parse(tableobj);
		/* params.systemName = tableobj.databaseCode;
		params.tableId = tableobj.TableId;
		params.tableName = tableobj.TableName;
		params.type = 99; */
		params.datasetId = tableobj.dataSetId ;
	}
	$.ajax({
		type:'post',
//		url:'${pageContext.request.contextPath}/rs/isdp/cellDataField/selectFieldByTableId',
		url:'${pageContext.request.contextPath}/rs/dataset/getSelectJson',
		dataType: 'json',
		data: params,
		success:function(data){
			/* $("#wsTableIdCombobox").combobox({
				data:data.rows,
				onSelect: function(record){
					tableobj.widgetSourceTableId = record.dname;
					$('#widgetSourceTableObj').val(JSON.stringify(tableobj));
					saveProperty();
				},
				onLoadSuccess:function(){
					$(this).combobox('setValue',tableobj.widgetSourceTableId);
				}
			});
			$("#wsTableParentIdCombobox").combobox({
				data:data.rows,
				onSelect: function(record){
					tableobj.widgetSourceTableParentId = record.dname;
					$('#widgetSourceTableObj').val(JSON.stringify(tableobj));
					saveProperty();
				},
				onLoadSuccess:function(){
					$(this).combobox('setValue',tableobj.widgetSourceTableParentId);
				}
			}); */
			$("#wsTableTextCombobox").combobox({
				data:data,
				onSelect: function(record){
					tableobj.widgetSourceTableText = record.columnLabel;
					$('#widgetSourceTableObj').val(JSON.stringify(tableobj));
					saveProperty();
				},
				onLoadSuccess:function(){
					$(this).combobox('setValue',tableobj.widgetSourceTableText);
				}
			});
			$("#wsTableValueCombobox").combobox({
				data:data,
				onSelect: function(record){
					tableobj.widgetSourceTableValue = record.columnLabel;
					$('#widgetSourceTableObj').val(JSON.stringify(tableobj));
					saveProperty();
				},
				onLoadSuccess:function(){
					$(this).combobox('setValue',tableobj.widgetSourceTableValue);
				}
			});
		}
	});
}

//处理数据输入区间最大值最小值控件的显示与隐藏
function hiddenRangeComment(editor){
	//日期及数字格式最大值最小值控件设置
	var isNumeric = false,isDate = false,isDateTime = false,isTime = false;
	if(editor.toLowerCase().indexOf("numeric")!=-1){
		$("tr[name='rangeInputComment']").show();
		
		$("span[name='isNumberComment']").show();
		$("span[name='isDateComment']").hide();
		$("span[name='isDateTimeComment']").hide();
		$("span[name='isTimeComment']").hide();
		
		isNumeric = true;
	} else if(editor.toLowerCase().indexOf("datetime")!=-1){
		$("tr[name='rangeInputComment']").show();
		
		$("span[name='isNumberComment']").hide();
		$("span[name='isDateComment']").hide();
		$("span[name='isDateTimeComment']").show();
		$("span[name='isTimeComment']").hide();
		
		isDateTime = true;
	} else if(editor.toLowerCase().indexOf("calendar")!=-1 || editor.toLowerCase().indexOf("date")!=-1){
		$("tr[name='rangeInputComment']").show();
		
		$("span[name='isNumberComment']").hide();
		$("span[name='isDateComment']").show();
		$("span[name='isDateTimeComment']").hide();
		$("span[name='isTimeComment']").hide();
		
		isDate = true;
	} else if(editor.toLowerCase().indexOf("time")!=-1){
		$("tr[name='rangeInputComment']").show();
		
		$("span[name='isNumberComment']").hide();
		$("span[name='isDateComment']").hide();
		$("span[name='isDateTimeComment']").hide();
		$("span[name='isTimeComment']").show();
		
		isTime = true;
	}else{
		$("tr[name='rangeInputComment']").hide();
	}
	
	var row = $('#field_property_grid').datagrid('getSelected');
	row.isNumeric = isNumeric;
	row.isDate = isDate;
	row.isDateTime = isDateTime;
	row.isTime = isTime;
}

//最后的确定操作，返回数据
function confirmData(){
	var isTree = '${param.isTree}';
	//提交之前必须再保存次数据
	if(isTree != 'true'){//如果是树 不需要保存
		saveProperty();
	}
	//取得选择的表数据
	var results = [];
	var tablenames = [];
	var fieldnames = [];
	
	var tableRows = $('#select_table_grid').datagrid('getData').rows;  // 选择的数据集
	var fields ;
	if(isTree  == 'true' ){
		fields = $('#select_field_grid').datagrid('getData').rows;	
	}else{
		fields = $('#field_property_grid').datagrid('getData').rows;   // 编辑字段  字段列表
	}
	$.each(tableRows,function(index,table) {
		tablenames.push(table.title);
		table.columns = [];	//保存所有字段信息 
		table.tables = [] ; //保存表名
		$.each(fields,function(index,field){
			if(table.datasetId===field.datasetId){
				//generatorData(field);//构建表达式数据
				table.columns.push(field);
				if(table.tables.indexOf(field.tableName) == -1){
					table.tables.push(field.tableName);
				}
				fieldnames.push(field.title);
			}
		});
		results.push(table);
	});
	if('${param.resultsElementId}' != ''){
		parent.document.getElementById('${param.resultsElementId}').value = JSON.stringify(results);
	}
	if('${param.tablenameElementId}' != ''){
		parent.document.getElementById('${param.tablenameElementId}').value = tablenames.join(',');
	}
	if('${param.fieldnameElementId}' != ''){
		parent.document.getElementById('${param.fieldnameElementId}').value = fieldnames.join(',');
	}
	parent.layer.close(parent.layer.getFrameIndex());
}


function generatorData(row){
	if(row.databaseCode!==""){
		row.code = "~F{"+ds+"."+row.FieldTable+"."+row.ColumnName+"}";
		row.value = "~F{"+ds+"."+row.tableNameCN+"."+row.name+"}";
		var ds = row.databaseCode;
	}else{
		row.code = "~F{default."+row.FieldTable+"."+row.ColumnName+"}";
		row.value = "~F{默认库."+row.tableNameCN+"."+row.name+"}";
	}
	return row;
}

//批量添加字段
function batchAddField(){
	//获取左侧选择的数据
	var selectRows = $('#field_grid').datagrid('getSelections');
	//右侧已添加的数据
	var datas = $('#select_field_grid').datagrid('getData').rows;
	var addflag = true;
	var altStr = "" ;
	$.each(datas,function(index,data){
		$.each(selectRows,function(i,row){
			if(/* row.id==data.id && */ row.columnName==data.columnName){
				altStr +=" "+data.tableNameCn+"."+ data.title + " " ;
				addflag = false;
			}
		});
	});
	if(addflag){
		$.each(selectRows,function(i,rowData){
			var row = {};
			row.id=rowData.id;
			row.datasetId=rowData.datasetId;
			row.tableName=rowData.tableName;
			row.columnName = rowData.columnLabel ;
			//row.columnLabel = rowData.columnLabel ;
			row.title=rowData.title;
			var exp = JSON.parse(rowData.expression) ;
			row.FieldLength=exp.FieldLength;
			row.FieldType=exp.FieldType;
			row.tableNameCn=exp.tableNameCN;
			row.value = exp.value ;
			row.code = exp.code ;
			$('#select_field_grid').datagrid('appendRow',row);
		});
		selectFieldRows = $('#select_field_grid').datagrid('getData').rows;
	}else{
		alert("已添加字段["+altStr+"]，请勿重复添加");
	}
}

//批量删除字段
function batchDelField(){
	//获取右侧选择的数据
	var selectRows = $('#select_field_grid').datagrid('getSelections');
	$.each(selectRows,function(i,row){
		var index = $('#select_field_grid').datagrid('getRowIndex',row);
		$('#select_field_grid').datagrid('deleteRow',index);
	});
	
	selectFieldRows = $('#select_field_grid').datagrid('getData').rows;
}

</script>
</head>
<body>
	<div id="main_tabs" class="easyui-tabs" style="width:auto; height:600px">
		<div title="选择数据集">
			<div class="easyui-layout" data-options="fit:true">  
	            <div data-options="region:'center'">
	            	<div class="easyui-layout" data-options="fit:true">
	            		<div data-options="region:'center'">
	            			<table id="table_grid"></table>
			            	<div id="table_grid_tb">
			            		<label>数据集名称：</label>
			            		<input type="text" id="keyword" />
			            		<a href="javascript:searchTable();" id="keywordBtn" class="easyui-linkbutton" iconCls="icon-search">检索</a>
			            	</div>
	            		</div>
	            		
	            		<div data-options="region:'south',split:true" style="height:200px">
	            			<table id="select_table_grid"></table>
	            		</div>
	            	</div>
	            </div>  
	        </div> 
		</div>
		<div title="选择字段">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'west',split:true" style="width:300px">
					<table id="field_grid"></table>
					<div id="field_grid_tb">
						<input id="selectTableCombobox" style="width:200px"/>
						<a href="javascript:batchAddField();" class="easyui-linkbutton" iconCls="icon-add">批量添加</a>
						<input id="fieldOptDs" type="hidden" />
					</div>
				</div>
				<div data-options="region:'center'">
					<table id="select_field_grid"></table>
					<div id="select_field_grid_tb">
						<a href="javascript:batchDelField();" class="easyui-linkbutton" iconCls="icon-no">批量删除</a>
					</div>
				</div>
			</div>
		</div>
		<div title="编辑字段">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'west',split:true" style="width:300px">
					<table id="field_property_grid"></table>
				</div>
				<div data-options="region:'center'">
					<div id="accordion" class="easyui-accordion" data-options="fit:true" style="height:100%">  
					<input type="hidden" id="hidRowIndex" value="">
					    <div title="显示属性" data-options="selected:true" style="overflow:auto;padding:10px;">  
					        <table cellpadding="3" cellspacing="0" border="0" width="100%">
					        	<tr>
									<td>是否显示</td>
									<!-- onclick="saveProperty()" -->
									<td><input type="checkbox" id="isShowList" name="isShowList" /></td>
								</tr>
								<tr>
									<td>是否过滤</td>
									<!-- onclick="saveProperty()" -->
									<td><input type="checkbox" id="isFilterable" name="isFilterable" /></td>
								</tr>
								<tr>
									<td>是否排序</td>
									<td><input type="checkbox" id="isSortable" name="isSortable"/></td>
								</tr>
								<tr>
									<td>显示列是否可隐藏</td>
									<td><input type="checkbox" id="isMenu" name="isMenu"/></td>
								</tr>
								<tr>
									<td>列宽</td>
									<td><input type="text" id="columnWidth" name="columnWidth" value="200"/></td>
								</tr>
								<tr>
									<td>对齐方式</td>
									<td>
										<input type="text" id="alignment" name="alignment" />
										<script type="text/javascript">
										$('#alignment').combobox({
											url:'${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson?nodeCode=alignment',
											valueField:'value',   
										    textField:'name',
										    panelHeight:'auto',
										    editable: false,
										    onSelect:function(record){
										    	//saveProperty();
										    }
										});
										</script>
									</td>
								</tr>
								 <tr>
									<td>链接图片</td>
									<td>
										<input type="hidden" id="hidLinkImg" name="hidLinkImg" />
										<a href="javascript:" onclick="setLinkImg()" class="easyui-linkbutton">编辑链接图片</a>
										<div id="linkImgWin"><table id="linkImgGrid"></table></div>
										<script type="text/javascript">
										// html编辑器获取变量方法
										function getVarFn(){
											//获取字典类型FieldTypeControl
											var expressionData = [] ;
											for(var i=0;i<selectFieldRows.length;i++){
												var express = {} ;
												var selectFieldRow = selectFieldRows[i];
												express.t = selectFieldRow.title ;
												express.dType = getfieldTypeValue(selectFieldRow.FieldType) ;
												express.code = selectFieldRow.code!=null?selectFieldRow.code.replace("~F{","~V{"):"" ;
												express.value = selectFieldRow.value!=null?selectFieldRow.value.replace("~F{","~V{"):"" ;
												express.name = selectFieldRow.title ;
												expressionData.push(express);
											}
											return expressionData ;
										}
										// html编辑器获取参数方法
										function getParamFn(){
											return [] ;
										}
										// html编辑器回调函数
										function retHtmlFn(data){
											if(data){
												$('#linkImgGrid').datagrid('endEdit',gindex);
												$('#linkImgGrid').datagrid('updateRow',{index:gindex,row:{htmldata:JSON.stringify(data),
													htmlExpression:data.htmlVal}});
											}
										}
										// html编辑器回显内容 
										function initHTMLFn(){
											var rows = $('#linkImgGrid').datagrid('getData').rows;
											if(rows){
												var row = rows[gindex];
												if(row){
													//var obj = {};
													//obj.varVal = row.htmlMap;
													//obj.htmlVal = row.htmlExpression ;
													return JSON.parse(row.htmldata);
												}
											}
										}
										var gindex = 0 ;
										//绑定事件
										function bindGridEvent(index){
											var expUrl = "${pageContext.request.contextPath}/mx/expression/defined/view?category=front&retFn=retExpression"+
													"&getFn=getExpression&initExpFn=initExpressionFn";
											var htmlUrl = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn&"+
													"getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
											//绑定表达式事件
											var expression = $('#linkImgGrid').datagrid('getEditor', {index:index,field:'expression'});
											$(expression.target).bind("click",function(){
												gindex = index ;
												window.open(expUrl);
											});
											//绑定html编辑器事件
											var htmlExpression = $('#linkImgGrid').datagrid('getEditor', {index:index,field:'htmlExpression'});
											$(htmlExpression.target).bind("click",function(e){
												gindex = index ;
												window.open(htmlUrl);
											});
											
										};
										//表达式传递数据
										function getExpression(){
											//获取字典类型FieldTypeControl
											var expressionData = [] ;
											for(var i=0;i<selectFieldRows.length;i++){
												var express = {} ;
												var selectFieldRow = selectFieldRows[i];
												express.t = selectFieldRow.title ;
												express.dType = getfieldTypeValue(selectFieldRow.FieldType) ;
												express.code = selectFieldRow.code ;
												express.value = selectFieldRow.value ;
												express.name = selectFieldRow.title ;
												expressionData.push(express);
											}
											return expressionData ;
										}
										// 转换字段类型
										function getfieldTypeValue(fieldType){
											var v = "" ;
											$.each(fieldTypeControl,function(i,d){
												if(d.code == fieldType){
													v = d.value ;
												}
											})
											return v ;
										}
										//表达式定义回调函数
										function retExpression(data){
											var datastr = JSON.stringify(data);
											$('#linkImgGrid').datagrid('endEdit',gindex);
											$('#linkImgGrid').datagrid('updateRow',{index:gindex,row:{expdata:datastr,expression:data.expActVal,
												expVal:data.expVal}});
										}
										function initExpressionFn(){
											var rows = $('#linkImgGrid').datagrid('getData').rows;
											if(rows.length>0){
												var row = rows[gindex];
												var data = JSON.parse(row['expdata']);
												return data;
											}
										}
										function setLinkImg(){
											$('#linkImgGrid').datagrid({
												width:534,
												height:260,
												singleSelect:true,
												fitColumns:true,
												rownumbers:true,
												toolbar:[{
													text:'增加',
													iconCls:'icon-add',
													handler:function(){
														var grid = $('#linkImgGrid');
														grid.datagrid('endEdit',0);
														grid.datagrid('insertRow',{index:0,row:{expression:'',htmlExpression:''}})
														grid.datagrid('beginEdit',0);
														//绑定事件
														bindGridEvent(0) ;
													}
												},{
													text:'删除',
													iconCls:'icon-no',
													handler:function(){
														var grid = $('#linkImgGrid');
														var index = grid.datagrid('getRowIndex',grid.datagrid('getSelected'));
														grid.datagrid('deleteRow',index);
													}
												},{
													text:'保存',
													iconCls:'icon-save',
													handler:function(){
														$('#linkImgGrid').datagrid('acceptChanges');
													}
												},'-',{
													text:'确定',
													iconCls:'icon-ok',
													handler:function(){
														$('#linkImgGrid').datagrid('acceptChanges');
														
														var rows = $('#linkImgGrid').datagrid('getData').rows;
														$('#hidLinkImg').val(JSON.stringify(rows));
														$('#linkImgWin').window('close');
														saveProperty();
													}
												}],
												columns:[[
												    {
														field:'expression',
														title:'表达式定义',
														width:'150',
														editor:{
															type:'text'
														}
													},{
														field:'htmlExpression',
														title:'HTML样式定义',
														width:'150',
														editor:{
															type:'text'
														}
													}
												]],
												onClickRow: function(rowIndex,rowData){
													$('#linkImgGrid').datagrid('beginEdit',rowIndex);
													
													bindGridEvent(rowIndex);
												}
											});
											
											var dataRows = [];
											var hidLinkImg = $('#hidLinkImg').val();
											if(hidLinkImg !== ''){
												dataRows = JSON.parse(hidLinkImg);
											}
											$('#linkImgGrid').datagrid('loadData',dataRows);
											
											$('#linkImgWin').window({
												title:'编辑链接图片',
												width:550,   
											    height:300,   
											    modal:true,
											    closed:false,
											    collapsible:false,
											    minimizable:false,
											    maximizable:false,
											    resizable:false
											});
										}
										</script>
									</td>
								</tr>
								<tr>
									<td>控件类型</td>
									<td>
										<input type="text" id="editor" name="editor" />
										<script type="text/javascript">
										$('#editor').combobox({
											valueField:'dataRole',   
										    textField:'name',
										    panelHeight:'120',
										    editable: false,
										    onSelect:function(record){
										    	var nodeCode = '';
										    	if(record.dataRole.toLowerCase().indexOf("numeric")!=-1){
						                   			nodeCode = "numericFormat ";
						                    	} else if(record.dataRole.toLowerCase().indexOf("datetime")!=-1){
						                    		nodeCode = "datetimeFormat ";
						                    	} else if(record.dataRole.toLowerCase().indexOf("calendar")!=-1 || record.dataRole.toLowerCase().indexOf("date")!=-1){
						                    		nodeCode = "dateFormat ";
						                    	} else if(record.dataRole.toLowerCase().indexOf("time")!=-1){
						                    		nodeCode = "timeFormat ";
						                    	} else{
						                    		nodeCode = "";
						                    	}
										    	var url = '${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson?nodeCode='+nodeCode;
										    	$('#formatValue').combobox('reload',url);
										    	$('#formatValue').combobox('select','');
										    	
										    	hiddenRangeComment(record.dataRole);
										    	
										    	//saveProperty();
										    }
										});
										$.ajax({
											url:'${pageContext.request.contextPath}/rs/form/component/list',
											type:'post',
											processData:true,
											data:{"parentIdNotEqual":"0","rows":10000},
											dataType:'json',
											success:function(data){
												$('#editor').combobox('loadData',data.rows);
											}
										});
										</script>
									</td>
								</tr>
								<tr>
									<td>显示格式</td>
									<td>
										<input type="text" id="formatValue" name="formatValue" />
										<script type="text/javascript">
										$('#formatValue').combobox({
											url:'${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson',
											valueField:'value',   
										    textField:'name',
										    panelHeight:'auto',
										    editable: true,
										    onSelect:function(record){
										    	//saveProperty();
										    }
										});
										</script>
									</td>
								</tr>
								<tr>
									<td>控件数据源类型</td>
									<td>
										<input type="text" id="widgetSourceType" name="widgetSourceType" />
										<script type="text/javascript">
										$('#widgetSourceType').combobox({
											url:'${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson?nodeCode=widgetSourceType',
											valueField:'value',   
										    textField:'name',
										    panelHeight:'auto',
										    editable: true,
										    onSelect:function(record){
										    	if(record.value==10){
													//字典数据
										    		$("#dictWidgetSource").show();
										    		$("tr[name='tableWidgetSource']").hide();
										    		$('#userWidgetSource').hide();
										    		//数据表选择设置为空
										    		$("#wigetSourcetableName").val('');
										    		$("#widgetSourceTableObj").val('');
										    		$("#wsTableIdCombobox").combobox('loadData',[]);
													$("#wsTableParentIdCombobox").combobox('loadData',[]);
													$("#wsTableTextCombobox").combobox('loadData',[]);
													$("#wsTableValueCombobox").combobox('loadData',[]);
													//用户自定义数据设置为空
													$('#wsUserData').val('');
												} else if(record.value==20){
													//数据表
													$("#dictWidgetSource").hide();
													$("tr[name='tableWidgetSource']").show();
													$('#userWidgetSource').hide();
													
													//字典数据设置为空
													$('#widgetDictSource').combobox('setValue','');
													//用户自定义数据设置为空
													$('#wsUserData').val('');
													
													initTableDataSource();
												}else if(record.value==30){
													//用户自定义
													$("#dictWidgetSource").hide();
													$("tr[name='tableWidgetSource']").hide();
													$('#userWidgetSource').show();
													
													//字典数据设置为空
													$('#widgetDictSource').combobox('setValue','');
													//数据表选择设置为空
													$("#wigetSourcetableName").val('');
										    		$("#widgetSourceTableObj").val('');
										    		$("#wsTableIdCombobox").combobox('loadData',[]);
													$("#wsTableParentIdCombobox").combobox('loadData',[]);
													$("#wsTableTextCombobox").combobox('loadData',[]);
													$("#wsTableValueCombobox").combobox('loadData',[]);
												}
										    	
										    	saveProperty();
										    }
										});
										</script>
									</td>
								</tr>
								<!-- 基础数据数据源选择 -->
								<tr id="dictWidgetSource">
									<td>&nbsp;控件数据源</td>
									<td>
										<input type="text" id="widgetDictSource" name="widgetDictSource" />
										<script type="text/javascript">
										$('#widgetDictSource').combobox({
											valueField:'code',   
										    textField:'name',
										    panelHeight:'auto',
										    editable: false,
										    onSelect:function(record){
										    	saveProperty();
										    }
										});
										$.ajax({
											url:'${pageContext.request.contextPath}/rs/isdp/global/treeDataJson',
											type:'post',
											contentType:'application/json',
											processData:false,
											data:'{"nodeCode":"widgetSource"}',
											dataType:'json',
											success:function(data){
												$('#widgetDictSource').combobox('loadData',data);
											}
										});
										</script>
									</td>
								</tr>
								<tr name="tableWidgetSource" >
									<td>&nbsp;选择数据集</td>
									<td>
										<input type="text" id="wigetSourcetableName" name="wigetSourcetableName" class="easyui-validatebox" onclick="openSelectTableWin();" />
										<input type="hidden" id="widgetSourceTableObj" name="widgetSourceTableObj" />
										<script type="text/javascript">
										function openSelectTableWin(){
											$.layer({
												type: 2,
												maxmin: true,
												shadeClose: true,
												title: "选择数据集",
												closeBtn: [0, true],
												shade: [0.5, '#000'],
												border: [2, 0.3, '#000'],
												fadeIn: 100,
												area: ["800px", "450px"],
												iframe:{
//													src:'${pageContext.request.contextPath}/mx/form/defined/table/select_table?nameId=wigetSourcetableName&elementId=widgetSourceTableObj'
													src:'${pageContext.request.contextPath}/mx/form/defined/table/select_dataset?nameId=wigetSourcetableName&elementId=widgetSourceTableObj'
												},
												end:initTableDataSource
											})
										}
										</script>
									</td>
								</tr>
								<!-- 
								<tr name="tableWidgetSource">
									<td>ID</td>
									<td>
										<input id="wsTableIdCombobox" name="widgetSourceTableId" data-options="valueField:'dname',textField:'fname'" class="easyui-combobox" style="width: 153px" />
							         </td>
								</tr>
								<tr name="tableWidgetSource">
									<td>parent_id</td>
									<td><input id="wsTableParentIdCombobox" name="widgetSourceTableParentId" data-options="valueField:'dname',textField:'fname'" class="easyui-combobox" style="width: 153px" />
									</td>
								</tr>
								 -->
								<tr name="tableWidgetSource">
									<td>&nbsp;显示值</td>
									<td><input id="wsTableTextCombobox" name="widgetSourceTableText" data-options="valueField:'columnLabel',textField:'title'" class="easyui-combobox" style="width: 153px" />
									</td>
								</tr>
								<tr name="tableWidgetSource">
									<td>&nbsp;实际值</td>
									<td>
									<input id="wsTableValueCombobox" name="widgetSourceTableValue" data-options="valueField:'columnLabel',textField:'title'" class="easyui-combobox" style="width: 153px" />
									</td>
								</tr>
								<tr id="userWidgetSource">
									<td>&nbsp;自定义数据</td>
									<td>
										<input type="hidden" id="wsUserData" name="wsUserData" />
										<a href="javascript:" onclick="setUserData()" class="easyui-linkbutton">编辑数据</a>
										<div id="userDataWin"><table id="userDataGrid"></table></div>
										<script type="text/javascript">
										function setUserData(){
											$('#userDataGrid').datagrid({
												height:260,
												singleSelect:true,
												fitColumns:true,
												rownumbers:true,
												toolbar:[{
													text:'增加',
													iconCls:'icon-add',
													handler:function(){
														var grid = $('#userDataGrid');
														grid.datagrid('insertRow',{index:0,row:{text:'',value:''}})
														grid.datagrid('beginEdit',0);
													}
												},{
													text:'删除',
													iconCls:'icon-no',
													handler:function(){
														var grid = $('#userDataGrid');
														var index = grid.datagrid('getRowIndex',grid.datagrid('getSelected'));
														grid.datagrid('deleteRow',index);
													}
												},{
													text:'保存',
													iconCls:'icon-save',
													handler:function(){
														$('#userDataGrid').datagrid('acceptChanges');
													}
												},'-',{
													text:'确定',
													iconCls:'icon-ok',
													handler:function(){
														$('#userDataGrid').datagrid('acceptChanges');
														
														var rows = $('#userDataGrid').datagrid('getData').rows;
														$('#wsUserData').val(JSON.stringify(rows));
														$('#userDataWin').window('close');
														saveProperty();
													}
												}],
												columns:[[
												    {
														field:'text',
														title:'显示值',
														width:'150',
														editor:{
															type:'text'
														}
													},{
														field:'value',
														title:'实际值',
														width:'150',
														editor:{
															type:'text'
														}
													}
												]],
												onClickRow: function(rowIndex,rowData){
													$('#userDataGrid').datagrid('beginEdit',rowIndex);
												}
											});
											
											var dataRows = [];
											var wsUserData = $('#wsUserData').val();
											if(wsUserData !== ''){
												dataRows = JSON.parse(wsUserData);
											}
											$('#userDataGrid').datagrid('loadData',dataRows);
											
											$('#userDataWin').window({
												title:'编辑用户自定义数据',
												width:350,   
											    height:300,   
											    modal:true,
											    closed:false,
											    collapsible:false,
											    minimizable:false,
											    maximizable:false,
											    resizable:false
											});
										}
										</script>
									</td>
								</tr>
					        </table>
					        <script type="text/javascript">
							$('#dictWidgetSource').hide();
							$("tr[name='tableWidgetSource']").hide();
							$('#userWidgetSource').hide();
							</script>
					    </div>  
					    <div title="编辑属性" data-options="selected:true" style="padding:10px;">  
					        <table cellpadding="3" cellspacing="0" border="0" width="100%">
								<tr>
									<td>是否编辑</td>
									<td>
										<input type="checkbox" id="isEditor" name="isEditor" onchange="changeIsEditor(this.checked)"/>
										<script type="text/javascript">
										function changeIsEditor(checked){
											if(checked){
												$("#isRequired").removeAttr('disabled');
												$("#defaultVlue").removeAttr('disabled');
												//$('#dataValidation').combobox('enable');
												$('#isValidate').removeAttr('disabled');
												//$('#isLink').removeAttr('disabled');
											}else{
												$('#isRequired').attr('checked',false);
												$("#defaultVlue").val('');
												//$('#isLink').attr('checked',false);
												$('#isValidate').attr('checked',false);
												$('#dataValidation').combobox('setValue','');
												
												$("#isRequired").attr('disabled','disabled');
												$("#defaultVlue").attr('disabled','disabled');
												$('#dataValidation').combobox('disable');
												$('#isValidate').attr('disabled','disabled');
												//$('#isLink').attr('disabled','disabled');
												//$('#linkPageName').attr('disabled','disabled');
												//$('#linkPageId').attr('disabled','disabled');
												$('#validateMsg').val('');
												$('#validateMsg').attr('disabled','disabled');
												
												$('#numberMinValue').numberspinner('disable');
									    		$('#numberMaxValue').numberspinner('disable');
									    		
									    		$('#dateMinValue').datebox('disable');
									    		$('#dateMaxValue').datebox('disable');
									    		
									    		$('#datetimeMinValue').datetimebox('disable');
									    		$('#datetimeMaxValue').datetimebox('disable');
									    		
									    		$('#timeMinValue').timespinner('disable');
									    		$('#timeMaxValue').timespinner('disable');
											}
										}
										</script>
									</td>
								</tr>
								<tr>
									<td>必填项</td>
									<td><input type="checkbox" id="isRequired" name="isRequired" disabled="disabled" /></td>
								</tr>
								
								<tr>
									<td>默认值</td>
									<td><input type="text" id="defaultVlue" name="defaultVlue" disabled="disabled" /></td>
								</tr>
								<tr>
									<td>是否验证数据</td>
									<td>
										<input type="checkbox" id="isValidate" name="isValidate" disabled="disabled" onchange="changeIsValidate(this.checked)"/>
										<script type="text/javascript">
										function changeIsValidate(checked){
											if(checked){
												$('#validateMsg').removeAttr('disabled');
												
												$('#dataValidation').combobox('enable');
											}else{
												$('#validateMsg').val('');
												$('#validateMsg').attr('disabled','disabled');
												
												$('#dataValidation').combobox('setValue','');
												$('#dataValidation').combobox('disable');
												
												$('#numberMinValue').numberspinner('disable');
									    		$('#numberMaxValue').numberspinner('disable');
									    		
									    		$('#dateMinValue').datebox('disable');
									    		$('#dateMaxValue').datebox('disable');
									    		
									    		$('#datetimeMinValue').datetimebox('disable');
									    		$('#datetimeMaxValue').datetimebox('disable');
									    		
									    		$('#timeMinValue').timespinner('disable');
									    		$('#timeMaxValue').timespinner('disable');
											}
										}
										</script>
									</td>
								</tr>
								<tr>
									<td>数据验证格式</td>
									<td>
										<input type="text" id="dataValidation" name="dataValidation" disabled="disabled"/>
										<script type="text/javascript">
										$('#dataValidation').combobox({
											url:'${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson?nodeCode=dataValidation',
											valueField:'value',   
										    textField:'name',
										    panelHeight:'auto',
										    editable: false,
										    onSelect:function(record){
										    	enableRangeComment(record.value);
									    		//saveProperty();
										    }
										});
										
										function enableRangeComment(range){
											var isEnable = 'enable';
									    	if(range!=='range'){
									    		isEnable = 'disable';
									    	}
									    	
								    		$('#numberMinValue').numberspinner(isEnable);
								    		$('#numberMaxValue').numberspinner(isEnable);
								    		
								    		$('#dateMinValue').datebox(isEnable);
								    		$('#dateMaxValue').datebox(isEnable);
								    		
								    		$('#datetimeMinValue').datetimebox(isEnable);
								    		$('#datetimeMaxValue').datetimebox(isEnable);
								    		
								    		$('#timeMinValue').timespinner(isEnable);
								    		$('#timeMaxValue').timespinner(isEnable);
										}
										</script>
									</td>
								</tr>
								<tr name="rangeInputComment">
									<td>最小值</td>
									<td>
										<span name="isNumberComment">
										<!-- ,onChange:function(newValue,oldValue){saveProperty();} -->
											<input type="text" id="numberMinValue" name="numberMinValue" data-options="disabled:true" class="easyui-numberspinner" value="0" style="width:153px" />
										</span>
										<span name="isDateComment">
										<!-- ,onSelect:function(date){saveProperty();} -->
											<input type="text" id="dateMinValue" name="dateMinValue" data-options="disabled:true" class="easyui-datebox" style="width:153px" />
										</span>
										<span name="isDateTimeComment">
										<!-- ,onSelect:function(newValue,oldvalue){saveProperty();} -->
										<input type="text" id="datetimeMinValue" name="datetimeMinValue" data-options="disabled:true" class="easyui-datetimebox" style="width:153px" />
										</span>
										<span name="isTimeComment">
										<!-- ,onSpinUp:function(){saveProperty();},onSpinDown:function(){saveProperty();} -->
										<input type="text" id="timeMinValue" name="timeMinValue" data-options="disabled:true" class="easyui-timespinner" style="width:153px" />
										</span>
									</td>
								</tr>
								<tr name="rangeInputComment">
									<td>最大值</td>
									<td>
										<span name="isNumberComment">
											<!-- ,onChange:function(newValue,oldValue){saveProperty();} -->
											<input type="text" id="numberMaxValue" name="numberMaxValue" data-options="disabled:true" class="easyui-numberspinner" value="0" style="width:153px" />
										</span>
										<span name="isDateComment">
											<!-- ,onSelect:function(date){saveProperty();} -->
											<input type="text" id="dateMaxValue" name="dateMaxValue" data-options="disabled:true" class="easyui-datebox" style="width:153px" />
										</span>
										<span name="isDateTimeComment">
											<!-- ,onSelect:function(newValue,oldValue){saveProperty();} -->
										<input type="text" id="datetimeMaxValue" name="datetimeMaxValue" data-options="disabled:true" class="easyui-datetimebox" style="width:153px" />
											</span>
										<span name="isTimeComment">
											<!-- ,onSpinUp:function(){saveProperty();},onSpinDown:function(){saveProperty();} -->
											<input type="text" id="timeMaxValue" name="timeMaxValue" data-options="disabled:true" class="easyui-timespinner" style="width:153px" />
										</span>
										<script type="text/javascript">
										$("span[name='isDateComment']").hide();
										$("span[name='isDateTimeComment']").hide();
										$("span[name='isTimeComment']").hide();
										
										$("tr[name='rangeInputComment']").hide();
										</script>
									</td>
								</tr>
								
								<tr>
									<td>验证提示信息</td>
									<!-- onchange="saveProperty();" -->
									<td><input type="text" id="validateMsg" name="validateMsg" disabled="disabled" /></td>
								</tr>
							</table>
					    </div>  
					</div> 
				</div>
			</div>
		</div>
	</div>
	<div align="right" style="width:100%;margin-top:5px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="confirmData();">提交</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="parent.layer.close(parent.layer.getFrameIndex());">取消</a>
	</div>
</body>
</html>