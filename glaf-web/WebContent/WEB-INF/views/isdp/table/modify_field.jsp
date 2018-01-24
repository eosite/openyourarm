<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<%@ include file="/WEB-INF/views/isdp/table/select_js.jsp"%>
<script type="text/x-kendo-template" id="template">
<table>
	<tr>
		<td>
			<button id="confirm_field_btn" class="k-button" onclick="confirm_field_click()">确定</button>
			<button id="close_field_btn" class="k-button" onclick="parent.layer.close(parent.layer.getFrameIndex())">关闭</button>
		</td>
	</tr>
</table>
</script>
<script type="text/javascript">
var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;

var columnPropertyViewModel,widget_ds_viewModel,status_grid_viewModel;
var fieldJson = [];//传入的字段

var setting = {
	view: {
		showIcon:true,
		showLine:true,
		showTitle:false,
		selectedMulti: false
	},
	async:{
		enable:true,
		url:"${pageContext.request.contextPath}/mx/form/defined/getFormPageTree"
	},
	data:{
		simpleData:{
			enable:true,
			idKey:"id",
			pIdKey:"parentId"
		}
	},
	callback: {
		onClick: zTreeOnClick
	}
};

function zTreeOnClick(event, treeId, treeNode){
	
}

$(function(){
	$("#linkpage_select_content").hide();
	$.fn.zTree.init($("#linkpageTree"), setting);
	
	if(parent.fieldJsonArray!=''){
		fieldJson = JSON.parse(parent.fieldJsonArray);
	}
	
	function createDictoryDataSource(nodeCode){
		return new kendo.data.DataSource({
			type:"json",
			transport:{
				read:{
					contentType:"application/json",
					url:"${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson",
					type: "POST",
                    dataType:"JSON"
				},
				parameterMap: function(options,operation){
					options.nodeCode = nodeCode;
					options.systemName = "${databaseCode}";
                	return JSON.stringify(options);
				}
			}
		})
	}
	
	//数据绑定
	var viewModel = kendo.observable({
		destGridDataSource : new kendo.data.DataSource({//已选择数据字段数据源
        	schema:{
               	model:{
               		fields:{
               			tableid:{type:"string"},
               			tablename:{type:"string"},
               			tableNameCN:{type:"string"},
               			FieldId:{type:"string"},
               			listid:{type:"string"},
               			dname:{type:"string"},
               			fname:{type:"string",editable:false},
               			dtype:{type:"string"},
               			strlen:{type:"string"},
               			referencedTableName:{type:"string"},//引用表
               			referencedFieldName:{type:"string"},//引用字段
               			
               			isShowList:{type:"boolean"},//是否显示
               			isShowTooltip:{type:"boolean"},//是否显示
               			alignment:{type:"string"},//是否显示
               			isEditor:{type:"boolean"},//是否可编辑
               			columnWidth:{type:"numeric"},//列宽
               			defaultValue:{type:"string"},//默认值 
               			isRequired:{type:"boolean"},//是否必填项
               			formatValue:{type:"string"},//显示格式
               			editor:{type:"string"},//编辑控件类型
               			widgetSourceType:{type:"string"},//控件数据类型
               			widgetSource:{type:"string"}//控件数据源
               		}
               	},
            }
        }),
        onSelectedChange:function(e){
			//已选择字段单击事件
			var selectedGrid = $("#selected_field_grid").data("kendoGrid");
			var row = selectedGrid.select();
			var data = selectedGrid.dataItem(row);
			
			//数据源选择，根据不同数据源，给出不同选择方式
			if(data.widgetSourceType==10){
				data.isDictVisible=true;
				data.isTableVisible=false;
				data.isUserVisible=false;
				setWidgetSourceSource();
			}else if(data.widgetSourceType==20){
				data.isDictVisible=false;
				data.isTableVisible=true;
				data.isUserVisible=false;
			}else if(data.widgetSourceType==30){
				data.isDictVisible=false;
				data.isTableVisible=false;
				data.isUserVisible=true;
			}else{
				data.isDictVisible=true;
				data.isTableVisible=false;
				data.isUserVisible=false;
			}
			//
			
			//设置数据源数据
			var dataWidgetSource;
			if(isjson(data.widgetSource)){
				dataWidgetSource = JSON.stringify(data.widgetSource);
			}else{
				dataWidgetSource = data.widgetSource;
			}
			
			//判断数据是否为区间选择数据，默认为false
			if(data.isRangeEditor==undefined){
				data.isRangeEditor = false;
			}
			//如果区间选择没有相关数据，默认为数值选择
			if(data.isNumeric==undefined){
				data.isNumeric = true;
			}
			
			var statusPic;
			if(isjson(data.statusPic)){
				statusPic = JSON.stringify(statusPic);
			}else{
				statusPic = statusPic;
			}
			
			//isFilterable
			if(data.isFilterable==undefined){
				data.isFilterable = true;
			}
			if(data.isSortable==undefined){
				data.isSortable = true;
			}
			if(data.isMenu==undefined){
				data.isMenu = true;
			}

			//单击已选择字段绑定数据到字段配置中，并绑定保存事件
			columnPropertyViewModel = kendo.observable({
				editorTypeSource:new kendo.data.DataSource({
					type:"json",
					transport:{
						read:{
							contentType: "application/json",
	                        url: "${pageContext.request.contextPath}/rs/form/component/read?parentIdNotEqual=0",
	                        type: "POST",
	                        dataType:"JSON"
						}
					}
				}),
				alignmentSource: createDictoryDataSource("alignment"),
				dataValidationSource: createDictoryDataSource("dataValidation"),
				formatValueSource : new kendo.data.DataSource({
					type:"json",
					transport:{
						read:{
							contentType:"application/json",
							url:"${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson",
							type: "POST",
	                        dataType:"JSON"
						},
						parameterMap : function(options, operation) {
							var dropdownlist = $("#editor").data("kendoDropDownList");
							var editor = dropdownlist.value();
		                    if (operation == "read") {
		                    	if(editor.toLowerCase().indexOf("numeric")!=-1){
		                   			options.nodeCode = "numericFormat ";
		                    	} else if(editor.toLowerCase().indexOf("datetime")!=-1){
		                    		options.nodeCode = "datetimeFormat ";
		                    	} else if(editor.toLowerCase().indexOf("calendar")!=-1 || editor.toLowerCase().indexOf("date")!=-1){
		                    		options.nodeCode = "dateFormat ";
		                    	} else if(editor.toLowerCase().indexOf("time")!=-1){
		                    		options.nodeCode = "timeFormat ";
		                    	} else{
		                    		options.nodeCode = "";
		                    	}
		                    	options.systemName = "${databaseCode}";
		                    	return JSON.stringify(options);
		                    }
		                 }
					}
				}),
				widgetSourceTypeSource : new kendo.data.DataSource({
					type:"json",
					transport:{
						read:{
							contentType:"application/json",
							url:"${pageContext.request.contextPath}/rs/isdp/global/dictoryDataJson",
							type: "POST",
	                        dataType:"JSON"
						},
						parameterMap : function(options, operation) {
		                    if (operation == "read") {
	                    		options.nodeCode = "widgetSourceType";
		                    	options.systemName = "${databaseCode}";
		                    	return JSON.stringify(options);
		                    }
		                 }
					}
				}),
				selectLinkPage:function(e){
					//弹出选择页面
					$.layer({
						type: 1,
						maxmin: true,
						shadeClose: true,
						title: "请选择页面",
						closeBtn: [0, true],
						shade: [0, '#000'],
						border: [2, 0.3, '#000'],
						fadeIn: 100,
						area: ["300px", "450px"],
						btns: 2,
						btn: ['确定', '取消'],
						page:{
							dom:"#linkpage_select_content"
						},
						yes: function(index){
							var treeObj = $.fn.zTree.getZTreeObj("linkpageTree");
							var nodes = treeObj.getSelectedNodes();
							if(nodes.length==0){
								layer.alert("请先选择链接页面！",3);
								return;
							}else{
								var node = nodes[0];
								columnPropertyViewModel.set("linkPageId",node.id);
								columnPropertyViewModel.set("linkPageName",node.name);
								columnPropertyViewModel.saveColumnProperty();
								layer.close(index);
							}
						}
					});
				},
				editorChange:function(e){
					var combobox = $("#formatValue").data("kendoComboBox");
					combobox.dataSource.read().then(function(){
						if(combobox.dataSource.total()==0){
							combobox.value("");
						}else{
							combobox.select(0);
							columnPropertyViewModel.set("formatValue",combobox.value());
						}
						columnPropertyViewModel.saveColumnProperty();
					});
					
					var editor = columnPropertyViewModel.get("editor");
					if(isjson(editor)){
						editor=editor.dataRole;
					}
					//日期及数字格式最大值最小值控件设置
					if(editor.toLowerCase().indexOf("numeric")!=-1){
						columnPropertyViewModel.set("isNumeric",true);
						columnPropertyViewModel.set("isDate",false);
						columnPropertyViewModel.set("isDateTime",false);
						columnPropertyViewModel.set("isTime",false);
                	} else if(editor.toLowerCase().indexOf("datetime")!=-1){
                		columnPropertyViewModel.set("isNumeric",false);
						columnPropertyViewModel.set("isDate",false);
						columnPropertyViewModel.set("isDateTime",true);
						columnPropertyViewModel.set("isTime",false);
                	} else if(editor.toLowerCase().indexOf("calendar")!=-1 || editor.toLowerCase().indexOf("date")!=-1){
                		columnPropertyViewModel.set("isNumeric",false);
						columnPropertyViewModel.set("isDate",true);
						columnPropertyViewModel.set("isDateTime",false);
						columnPropertyViewModel.set("isTime",false);
                	} else if(editor.toLowerCase().indexOf("time")!=-1){
                		columnPropertyViewModel.set("isNumeric",false);
						columnPropertyViewModel.set("isDate",false);
						columnPropertyViewModel.set("isDateTime",false);
						columnPropertyViewModel.set("isTime",true);
                	}
				},
				widgetSourceTypeChange:function(e){
					var widgetSourceType = columnPropertyViewModel.get("widgetSourceType");
					if(widgetSourceType.value && widgetSourceType.value!=undefined){
						widgetSourceType = widgetSourceType.value;
					}
					//根据类型绑定数据源
					var widgetViewModel;
					if(widgetSourceType==10){
						//字典数据
						columnPropertyViewModel.set("isDictVisible",true);
						columnPropertyViewModel.set("isTableVisible",false);
						columnPropertyViewModel.set("isUserVisible",false);
						setWidgetSourceSource();
					} else if(widgetSourceType==20){
						//数据表
						columnPropertyViewModel.set("isDictVisible",false);
						columnPropertyViewModel.set("isTableVisible",true);
						columnPropertyViewModel.set("isUserVisible",false);
						columnPropertyViewModel.set("widgetSourceTextbox","");
						open_widgetSource_window(20);
							
					}else if(widgetSourceType==30){
						//用户自定义
						columnPropertyViewModel.set("isDictVisible",false);
						columnPropertyViewModel.set("isTableVisible",false);
						columnPropertyViewModel.set("isUserVisible",true);
						columnPropertyViewModel.set("widgetSourceTextbox","");
						open_widgetSource_window(30);
					}
					
					this.saveColumnProperty();
				},
				saveColumnProperty: function(e){
					//保存事件响应，将字段配置数据赋值到Grid数据中
					data.columnWidth=columnPropertyViewModel.get("columnWidth");
					//isShowList
					data.isShowList=columnPropertyViewModel.get("isShowList");
					//isShowTooltip
					data.isShowTooltip=columnPropertyViewModel.get("isShowTooltip");
					//isLink、linkPageId
					data.isLink=columnPropertyViewModel.get("isLink");
					if(!data.isLink){
						columnPropertyViewModel.set("linkPageName","");
						columnPropertyViewModel.set("linkPageId","");
					}
					data.linkPageName=columnPropertyViewModel.get("linkPageName");
					data.linkPageId=columnPropertyViewModel.get("linkPageId");
					
					//alignment
					var alignment = columnPropertyViewModel.get("alignment");
					if(isjson(alignment)){
						data.alignment=alignment.value;
					}else{
						data.alignment=alignment;
					}
					
					var dataValidation = columnPropertyViewModel.get("dataValidation");
					if(isjson(dataValidation)){
						data.dataValidation = dataValidation.value;
					}else{
						data.dataValidation = dataValidation;
					}
					
					if(data.dataValidation=="range"){
						columnPropertyViewModel.set("isRangeEditor",true);
					}else{
						columnPropertyViewModel.set("isRangeEditor",false);
					}
					
					//minValue、maxValue
					data.isRangeEditor = columnPropertyViewModel.get("isRangeEditor");
					data.isNumeric = columnPropertyViewModel.get("isNumeric");
					data.isDate = columnPropertyViewModel.get("isDate");
					data.isDateTime = columnPropertyViewModel.get("isDateTime");
					data.isTime = columnPropertyViewModel.get("isTime");
					
					if(data.isNumeric){
						data.numberMinValue=columnPropertyViewModel.get("numberMinValue");
						data.numberMaxValue=columnPropertyViewModel.get("numberMaxValue");
						data.dateMinValue = "";
						data.dateMaxValue = "";
					}else if(data.isDate){
						data.numberMinValue = "";
						data.numberMaxValue = "";
						data.dateMinValue=kendo.toString(columnPropertyViewModel.get("dateMinValue"),"yyyy-MM-dd");
						data.dateMaxValue=kendo.toString(columnPropertyViewModel.get("dateMaxValue"),"yyyy-MM-dd");
					}else if(data.isDateTime){
						data.numberMinValue = "";
						data.numberMaxValue = "";
						data.dateMinValue=kendo.toString(columnPropertyViewModel.get("dateMinValue"),"yyyy-MM-dd HH:mm:ss");
						data.dateMaxValue=kendo.toString(columnPropertyViewModel.get("dateMaxValue"),"yyyy-MM-dd HH:mm:ss");
					}else if(data.isTime){
						data.numberMinValue = "";
						data.numberMaxValue = "";
						data.dateMinValue=kendo.toString(columnPropertyViewModel.get("dateMinValue"),"HH:mm:ss");
						data.dateMaxValue=kendo.toString(columnPropertyViewModel.get("dateMaxValue"),"HH:mm:ss");
					}
					
					//editor
					var editor = columnPropertyViewModel.get("editor");
					if(isjson(editor)){
						data.editor=editor.dataRole;
					}else{
						data.editor=editor;
					}
					
					//formatValue
					var formatValue = columnPropertyViewModel.get("formatValue");
					if(isjson(formatValue)){
						data.formatValue=formatValue.value;
					}else{
						data.formatValue=formatValue;
					}
					
					//statusPic
					var statusPic = columnPropertyViewModel.get("statusPic");
					try{
						var t = JSON.parse(statusPic);
						data.statusPic = [];
						for(var i=0;i<t.length;i++){
							data.statusPic.push(t[i]);
						}
					}catch(e){
						data.statusPic=statusPic;
					}
					
					//isFilterable
					data.isFilterable=columnPropertyViewModel.get("isFilterable");
					//isSortable
					data.isSortable=columnPropertyViewModel.get("isSortable");
					//isMenu
					data.isMenu=columnPropertyViewModel.get("isMenu");
					

					//widgetSourceType
					var widgetSourceType = columnPropertyViewModel.get("widgetSourceType");
					if(isjson(widgetSourceType)){
						data.widgetSourceType=widgetSourceType.value;
					}else{
						data.widgetSourceType=widgetSourceType;
					}
					
					//widgetSource
					if(data.widgetSourceType==10){
						var widgetSource = columnPropertyViewModel.get("widgetDictSource");
						if(isjson(widgetSource)){
							data.widgetSource=widgetSource.code;
						}else{
							data.widgetSource=widgetSource;
						}
					}else if(data.widgetSourceType==20){
						var source = columnPropertyViewModel.get("widgetTableSource");
						if(source!=undefined&&source!=""){
							try{
								source = JSON.parse(source);
								if(isjson(source)){
									data.widgetSource = {};
									for( key in source ){
										data.widgetSource[key] = source[key];
									}
								}else{
									data.widgetSource = source;
								}
							}catch(e){
								alert("程序异常:"+e);
							}
						}
					}else if(data.widgetSourceType==30){
						var source = columnPropertyViewModel.get("widgetUserSource");
						try{
							var t = JSON.parse(source);
							data.widgetSource = [];
							for(var i=0;i<t.length;i++){
								data.widgetSource.push(t[i]);
							}
						}catch(e){
							data.widgetSource=source;
						}
					}
					
					if(columnPropertyViewModel.get("isEditor")){
						data.isEditor=columnPropertyViewModel.get("isEditor");
						data.defaultValue=columnPropertyViewModel.get("defaultValue");
						data.isRequired=columnPropertyViewModel.get("isRequired");
						//validate
						data.isValidate = columnPropertyViewModel.get("isValidate");
						data.validateMsg = columnPropertyViewModel.get("validateMsg");
						
					}else{
						setColumnPropertyDefault(data);
					}
					console.log("save:"+JSON.stringify(data));
				},
				isNumeric: data.isNumeric,
				isDate: data.isDate,
				isDateTime:data.isDateTime,
				isTime:data.isTime,
				isRangeEditor: data.isRangeEditor,
				isDictVisible: data.isDictVisible,
				isTableVisible: data.isTableVisible,
				isUserVisible: data.isUserVisible,
				isShowList: data.isShowList,
				isShowTooltip: data.isShowTooltip,
				isLink: data.isLink,
				linkPageId: data.linkPageId,
				linkPageName: data.linkPageName,
				alignment: data.alignment,
				isEditor: data.isEditor,
				isFilterable: data.isFilterable,
				isMenu: data.isMenu,
				isSortable: data.isSortable,
				columnWidth: data.columnWidth,
				defaultValue: data.defaultValue,
				isRequired: data.isRequired,
				formatValue: data.formatValue,
				dataValidation: data.dataValidation,
				validateMsg: data.validateMsg,
				isValidate: data.isValidate,
				numberMinValue: data.numberMinValue,
				numberMaxValue: data.numberMaxValue,
				dateMinValue: data.dateMinValue,
				dateMaxValue: data.dateMaxValue,
				editor: data.editor,
				widgetSourceType: data.widgetSourceType,
				widgetDictSource: data.widgetSourceType==10?dataWidgetSource:"",
				widgetTableSource: data.widgetSourceType==20?dataWidgetSource:"",
				widgetUserSource: data.widgetSourceType==30?dataWidgetSource:"",
				statusPic: data.statusPic
			});
			kendo.bind($("#columnPropertyTable"), columnPropertyViewModel);
		}
	});
	kendo.bind($(document.body), viewModel);
	
	
	//初始化页面时，首先将列属性设置为不可编辑
	columnPropertyViewModel = kendo.observable({
		saveColumnProperty: function(e){
			//初始化时，判断没有选择数据字段时，给出提示
			var selectedGrid = $("#selected_field_grid").data("kendoGrid");
			var row = selectedGrid.select();
			if(row.length==0){
				layer.alert("请先选择字段！",3);
			}
		},
		isFilterable: true,
		isSortable: true,
		isMenu:true,
		isRangeEditor:false,
		isEditor:false,
		isDictVisible:true,
		isTableVisible:false,
		isUserVisible:false,
		isNumeric:true,
		isDate:false,
		isDateTime:false,
		isTime:false,
		isValidate:false
	});
	kendo.bind($("#columnPropertyTable"), columnPropertyViewModel);
	
	//用户自定义控件数据源绑定
	widget_ds_viewModel = kendo.observable({
		widget_ds_grid_dataSource:[],
		onSaveChanges:function(e){
			var values = this.get("widget_ds_grid_dataSource");
			
			for(var i=0;i<values.length;i++){
				if(values[i].text==undefined || values[i].value==undefined || values[i].text.trim()=="" || values[i].value.trim()==""){
					layer.alert("必须填写值",3);
					return;
				}
			}
			var value = JSON.stringify(this.get("widget_ds_grid_dataSource"));
			columnPropertyViewModel.set("widgetUserSource",value);
			columnPropertyViewModel.saveColumnProperty();
			layer.close(widgetSource_layout_index);
		}
	});
	kendo.bind($("#widget_ds_content"), widget_ds_viewModel);
	$("#widget_ds_content").hide();
	$("#widget_ds_table_content").hide();
	
	//字段图片展示数据绑定
	status_grid_viewModel = kendo.observable({
		status_grid_dataSource: [],
		onSaveChanges:function(e){
			var grid = $("#status_grid").data("kendoGrid");
			var values = grid.dataSource.data();
			
			//name.columnName.
			var returnValue = [];
			for(var i=0;i<values.length;i++){
				if(values[i].fieldData==undefined || values[i].picUrl==undefined || values[i].picUrl.trim()==""){
					layer.alert("必须填写值",3);
					return;
				}
				
				var v = {};
				var fieldData = {};
				fieldData.name = values[i].fieldData.name;
				fieldData.columnName = values[i].fieldData.ColumnName;
				v.fieldData = fieldData;
				v.picUrl = values[i].picUrl;
				
				returnValue.push(v);
			}
			var value = JSON.stringify(returnValue);
			columnPropertyViewModel.set("statusPic",value);
			columnPropertyViewModel.saveColumnProperty();
			layer.close(widgetSource_layout_index);
		}
	});
	kendo.bind($("#status_grid_content"),status_grid_viewModel);
	$("#status_grid_content").hide();
	
	//初始化布局
	$("#splitter").height(height-80);
	$("#splitter").kendoSplitter({
		orientation: "horizontal",
        panes: [
			{ 
				collapsible: false,
				size:'200px'
			},{ 
				collapsible: false,
				size:'600px'
			}
        ]
	});
	//end
	
	initToolBar();
	
	//设置已选择字段列表中的字段为request传入的字段
	initSelectedGridData(fieldJson);
	//end
	
	
	$("#wigetSourcetableName").on("click",function(e){
		var tableObj = $("#widgetSourceTableObj").val();
		selectTable('${databaseCode}',"","wigetSourcetableName","widgetSourceTableObj",tableObj,setTableDataSource);
	});


});

function setTableDataSource(){
	//选择表后设置字段数据源
	var dataSource = createDataSource();
	var wsTableIdDropdownlist = $("#wsTableIdDropDownList").data("kendoDropDownList");
	wsTableIdDropdownlist.setDataSource(dataSource);
	wsTableIdDropdownlist.value("");
	
	var wsTableParentIdDropDownList = $("#wsTableParentIdDropDownList").data("kendoDropDownList");
	wsTableParentIdDropDownList.setDataSource(dataSource);
	wsTableParentIdDropDownList.value("");
	
	var wsTableTextDropDownList = $("#wsTableTextDropDownList").data("kendoDropDownList");
	wsTableTextDropDownList.setDataSource(dataSource);
	wsTableTextDropDownList.value("");
	
	var wsTableValueDropDownList = $("#wsTableValueDropDownList").data("kendoDropDownList");
	wsTableValueDropDownList.setDataSource(dataSource);
	wsTableValueDropDownList.value("");
}

function createDataSource(){
	 return new kendo.data.DataSource({
		type: "json",
        transport: {
           	read:{
           		contentType: "application/json",
                url: "${pageContext.request.contextPath}/rs/isdp/cellDataField/selectFieldByTableId",
                type: "POST",
                dataType:"JSON"
           	},
            parameterMap : function(options, operation) {
           	  	if(operation=="read"){
           	  		options.systemName = "${databaseCode}";
           	  		
           	  		var tableObj = JSON.parse($("#widgetSourceTableObj").val());
           	  		options.tableId = tableObj[0].TableId;
           	  		options.tableName = tableObj[0].TableName;
           	  		options.type = 99;
           	  		return JSON.stringify(options);
           	  	}
            }
        },
        schema:{
           	total:"total",
           	data:"rows",
           	model:{
           		fields:{
           			tableid:{type:"string"},
           			tablename:{type:"string"},
           			FieldId:{type:"string"},
           			listid:{type:"string"},
           			dname:{type:"string"},
           			fname:{type:"string"},
           			dtype:{type:"string"},
           			strlen:{type:"string"},
           			referencedTableName:{type:"string"},
           			referencedFieldName:{type:"string"}
           		}
           	}
        }
	})
}

function isjson(obj){
	var isObj = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]";
	var isArray = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object array]";
	return isObj || isArray;
}

//工具栏初始化
function initToolBar(){
	$("#toolbar").kendoToolBar({
        resizable: false,
        items: [
            { template:kendo.template($("#template").html()) }
        ]
    });
	
	$("#confirm_field_btn").kendoButton({
		icon:"k-icon k-i-tick"
	});
	$("#close_field_btn").kendoButton({
		icon:"k-icon k-i-close"
	});
	
	$("#widget_ds_table_toolbar").kendoToolBar({
		resizable: false,
		items:[
			{
				text:"确定",
				type:"button",
				spriteCssClass:"k-icon k-i-tick",
				click:function(e){
					//保存数据源
					var table = JSON.parse($("#widgetSourceTableObj").val());
					table = table[0];
					
					var wsTableIdDropdownlist = $("#wsTableIdDropDownList").data("kendoDropDownList");
					var widgetSourceTableId = wsTableIdDropdownlist.value();
					
					var wsTableParentIdDropDownList = $("#wsTableParentIdDropDownList").data("kendoDropDownList");
					var widgetSourceTableParentId = wsTableParentIdDropDownList.value();
					
					var wsTableTextDropDownList = $("#wsTableTextDropDownList").data("kendoDropDownList");
					var widgetSourceTableText = wsTableTextDropDownList.value();
					
					var wsTableValueDropDownList = $("#wsTableValueDropDownList").data("kendoDropDownList");
					var widgetSourceTableValue = wsTableValueDropDownList.value();
					
					table.widgetSourceTableId = widgetSourceTableId;
					table.widgetSourceTableParentId = widgetSourceTableParentId;
					table.widgetSourceTableText = widgetSourceTableText;
					table.widgetSourceTableValue = widgetSourceTableValue;
					
					columnPropertyViewModel.set("widgetTableSource",JSON.stringify(table));
					columnPropertyViewModel.saveColumnProperty();
					
					layer.close(widgetSource_layout_index);
				}
			},
			{
				text:"关闭",
				type:"button",
				spriteCssClass:"k-icon k-i-close",
				click:function(e){
					layer.close(widgetSource_layout_index);
				}
			}
		]
	});
}

//初始化已选择字段列表
function initSelectedGridData(fieldJson){
	var selectedGrid = $("#selected_field_grid").data("kendoGrid");
	for(var i=0;i<fieldJson.length;i++){
		var temp = {};
		temp.tablename=fieldJson[i].FieldTable;
		temp.tableNameCN=fieldJson[i].tableNameCN;
		
		temp.strlen=fieldJson[i].FieldLength;
		temp.dtype=fieldJson[i].FieldType;
		temp.fname=fieldJson[i].name;
		temp.dname=fieldJson[i].ColumnName;
		temp.FieldId=fieldJson[i].FieldId;
		temp.isEditor=fieldJson[i].isEditor;
		temp.isShowList=fieldJson[i].isShowList;
		temp.isShowTooltip=fieldJson[i].isShowTooltip;
		temp.isLink=fieldJson[i].isLink;
		temp.isRangeEditor = fieldJson[i].isRangeEditor;
		temp.linkPageId=fieldJson[i].linkPageId;
		temp.linkPageName=fieldJson[i].linkPageName;
		temp.alignment=fieldJson[i].alignment;
		temp.columnWidth=fieldJson[i].columnWidth?fieldJson[i].columnWidth:"200";
		temp.defaultValue=fieldJson[i].defaultValue;
		temp.isRequired=fieldJson[i].isRequired;
		temp.formatValue=fieldJson[i].formatValue;
		temp.dataValidation=fieldJson[i].dataValidation;
		temp.validateMsg=fieldJson[i].validateMsg;
		temp.isValidate=fieldJson[i].isValidate;
		temp.numberMinValue=fieldJson[i].numberMinValue;
		temp.numberMaxValue=fieldJson[i].numberMaxValue;
		temp.dateMinValue=fieldJson[i].dateMinValue;
		temp.dateMaxValue=fieldJson[i].dateMaxValue;
		temp.isNumeric=fieldJson[i].isNumeric;
		temp.isDate = fieldJson[i].isDate;
		temp.isDateTime = fieldJson[i].isDateTime;
		temp.isTime = fieldJson[i].isTime;
		temp.editor=fieldJson[i].editor?fieldJson[i].editor:"textfield";
		temp.widgetSourceType = fieldJson[i].widgetSourceType;
		if(fieldJson[i].widgetSourceType==10){
			temp.isDictVisible=true;
			temp.isTableVisible=false;
			temp.isUserVisible=false;
		}else if(fieldJson[i].widgetSourceType==20){
			temp.isDictVisible=false;
			temp.isTableVisible=true;
			temp.isUserVisible=false;
		}else if(fieldJson[i].widgetSourceType==30){
			temp.isDictVisible=false;
			temp.isTableVisible=false;
			temp.isUserVisible=true;
		}else{
			temp.isDictVisible=true;
			temp.isTableVisible=false;
			temp.isUserVisible=false;
		}
		temp.widgetSource = fieldJson[i].widgetSource;
		temp.statusPic = fieldJson[i].statusPic;
		
		temp.isFilterable = fieldJson[i].isFilterable;
		temp.isSortable = fieldJson[i].isSortable;
		temp.isMenu = fieldJson[i].isMenu;
		selectedGrid.dataSource.add(temp);
	}
}


//确定
function confirm_field_click(){
	var selectedGrid = $("#selected_field_grid").data("kendoGrid");
	var dataItems = selectedGrid.dataSource.data();

	var fieldJson = [];
	for(var i=0;i<dataItems.length;i++){
		
		var temp = {};
		temp.FieldTable = dataItems[i].tablename;
		temp.tableNameCN = dataItems[i].tableNameCN;
		temp.FieldLength = dataItems[i].strlen;
		temp.FieldType = dataItems[i].dtype;
		temp.name = dataItems[i].fname;
		temp.ColumnName = dataItems[i].dname;
		temp.FieldId = dataItems[i].FieldId;
		temp.isEditor = dataItems[i].isEditor;
		temp.isShowList = dataItems[i].isShowList;
		temp.isShowTooltip = dataItems[i].isShowTooltip;
		temp.isRangeEditor = dataItems[i].isRangeEditor;
		temp.isLink=dataItems[i].isLink;
		temp.linkPageId=dataItems[i].linkPageId;
		temp.linkPageName=dataItems[i].linkPageName;
		temp.alignment = dataItems[i].alignment;
		temp.columnWidth = dataItems[i].columnWidth;
		temp.defaultValue=dataItems[i].defaultValue;
		temp.isRequired=dataItems[i].isRequired;
		temp.formatValue=dataItems[i].formatValue;
		temp.dataValidation=dataItems[i].dataValidation;
		temp.validateMsg=dataItems[i].validateMsg;
		temp.isValidate=dataItems[i].isValidate;
		temp.numberMinValue=dataItems[i].numberMinValue;
		temp.numberMaxValue=dataItems[i].numberMaxValue;
		temp.dateMinValue=dataItems[i].dateMinValue;
		temp.dateMaxValue=dataItems[i].dateMaxValue;
		temp.isNumeric=dataItems[i].isNumeric;
		temp.isDate = dataItems[i].isDate;
		temp.isDateTime = dataItems[i].isDateTime;
		temp.isTime = dataItems[i].isTime;
		temp.editor=dataItems[i].editor;
		temp.widgetSourceType=dataItems[i].widgetSourceType;
		
		var t = dataItems[i].widgetSource;
		if(isjson(t)){
			if(temp.widgetSourceType==20){
				temp.widgetSource = {};
				for( key in t ){
					temp.widgetSource[key] = t[key];
				}
			}else{
				temp.widgetSource = [];
				for(var j=0;j<t.length;j++){
					temp.widgetSource.push(t[j]);
				}
			}
		}else{
			temp.widgetSource=dataItems[i].widgetSource;
		}
		
		var statusPic = dataItems[i].statusPic;
		if(isjson(statusPic)){
			temp.statusPic = [];
			for(var j=0;j<statusPic.length;j++){
				temp.statusPic.push(statusPic[j]);
			}
		}else{
			temp.statusPic=dataItems[i].statusPic;
		}
		
		temp.isFilterable=dataItems[i].isFilterable;
		temp.isSortable=dataItems[i].isSortable;
		temp.isMenu=dataItems[i].isMenu;
		
		if("${databaseCode}"!==""){
			var ds = "${databaseCode}";
			temp.code = "~F{"+ds+"."+dataItems[i].tablename+"."+dataItems[i].dname+"}";
			temp.value = "~F{"+ds+"."+dataItems[i].tableNameCN+"."+dataItems[i].fname+"}";
		}else{
			temp.code = "~F{default."+dataItems[i].tablename+"."+dataItems[i].dname+"}";
			temp.value = "~F{默认库."+dataItems[i].tableNameCN+"."+dataItems[i].fname+"}";
		}
		
		fieldJson.push(temp);
	}
	
	if("${fieldObjElementId}" != ""){
		parent.document.getElementById("${fieldObjElementId}").value = JSON.stringify(fieldJson);
	}
	
	parent.layer.close(parent.layer.getFrameIndex());
}

//设置列属性为默认值
function setColumnPropertyDefault(data){
	data.isDictVisible=true;
	data.isTableVisible=false;
	data.isUserVisible=false;
	data.isEditor=false;
	data.columnWidth=data.columnWidth?data.columnWidth:"200";
	data.defaultValue="";
	data.isRequired=false;
	//data.formatValue="";
	data.dataValidation="";
	data.isRangeEditor=false;
	data.numberMinValue="";
	data.numberMaxValue="";
	data.dateMinValue="";
	data.dateMaxValue="";
	data.dataValidation="";
	data.validateMsg="";
	data.isValidate=false;
	if(data.fieldType=="i4"){
		formatValue="#";
	}
	data.editor=data.editor?data.editor:"textfield";
	//data.widgetSourceType="";
	//data.widgetSource="";
}

function setWidgetSourceSource(){
	var widgetDataSource = new kendo.data.DataSource({
		type:"json",
		transport:{
			read:{
				contentType:"application/json",
				url:"${pageContext.request.contextPath}/rs/isdp/global/treeDataJson",
				type:"POST",
				dataType:"JSON"
			},
			parameterMap : function(options, operation) {
            	if (operation == "read") {
            		options.nodeCode = "widgetSource";
            		options.systemName = "${databaseCode}";
            		return JSON.stringify(options);
            	}
         	}
		}
	});
	var widgetDropdownlist = $("#widgetSourceDropDownList").data("kendoDropDownList");
	widgetDropdownlist.setDataSource(widgetDataSource);
}

function open_widgetSource_window(widgetSourceType){
	
	if(widgetSourceType==30){
		//自定义用户数据
		var widgetSourceTextbox = columnPropertyViewModel.get("widgetUserSource");
		if(widgetSourceType!=0 && widgetSourceTextbox != ""){
			widget_ds_viewModel.set("widget_ds_grid_dataSource",eval("("+widgetSourceTextbox+")"));
		}else{
			widget_ds_viewModel.set("widget_ds_grid_dataSource",eval([]));
		}
		
		widgetSource_layout_index = $.layer({
			type: 1,
			maxmin: true,
			shadeClose: true,
			title: "用户自定义数据",
			closeBtn: [0, true],
			shade: [0, '#000'],
			border: [2, 0.3, '#000'],
			fadeIn: 100,
			area: ["450px", "300px"],
			page:{
				dom:"#widget_ds_content"
			}
		});
	}else if(widgetSourceType==20){
		//选择数据表：{tableName:'',id:'',parent_id:'',text:'',value:''}
		var widgetTableSource = columnPropertyViewModel.get("widgetTableSource");

		var wsTableIdDropdownlist = $("#wsTableIdDropDownList").data("kendoDropDownList");
		var wsTableParentIdDropDownList = $("#wsTableParentIdDropDownList").data("kendoDropDownList");
		var wsTableTextDropDownList = $("#wsTableTextDropDownList").data("kendoDropDownList");
		var wsTableValueDropDownList = $("#wsTableValueDropDownList").data("kendoDropDownList");
		
		var table = {};
		if(widgetSourceType!=0 && widgetTableSource != ""){
			table = JSON.parse(widgetTableSource);
			$("#wigetSourcetableName").val(table.name);
			$("#widgetSourceTableObj").val("["+JSON.stringify(table)+"]");
			setTableDataSource();
			wsTableIdDropdownlist.value(table.widgetSourceTableId);
			wsTableParentIdDropDownList.value(table.widgetSourceTableParentId);
			wsTableTextDropDownList.value(table.widgetSourceTableText);
			wsTableValueDropDownList.value(table.widgetSourceTableValue);
		}else{
			wsTableIdDropdownlist.value("");
			wsTableParentIdDropDownList.value("");
			wsTableTextDropDownList.value("");
			wsTableValueDropDownList.value("");
			$("#wigetSourcetableName").val("");
			$("#widgetSourceTableObj").val("");
		}
		
		
		widgetSource_layout_index = $.layer({
			type: 1,
			maxmin: true,
			shadeClose: true,
			title: "选择表数据源",
			closeBtn: [0, true],
			shade: [0, '#000'],
			border: [2, 0.3, '#000'],
			fadeIn: 100,
			area: ["350px", "300px"],
			page:{
				dom:"#widget_ds_table_content"
			}
		});
	}
}

function open_satatus_window(){
	//自定义用户数据
	var statusPic = columnPropertyViewModel.get("statusPic");
	if(statusPic != ""){
		status_grid_viewModel.set("status_grid_dataSource",statusPic);
	}else{
		status_grid_viewModel.set("status_grid_dataSource",eval([]));
	}
	
	widgetSource_layout_index = $.layer({
		type: 1,
		maxmin: true,
		shadeClose: true,
		title: "配置状态图片",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [2, 0.3, '#000'],
		fadeIn: 100,
		area: ["450px", "300px"],
		page:{
			dom:"#status_grid_content"
		}
	});
}

//grid字段选择
function fieldDropdownEditor(container, options){
	$('<input data-text-field="name" data-value-field="ColumnName" data-bind="value:' + options.field + '"/>')
  	.appendTo(container)
  	.kendoDropDownList({
      autoBind: true,
      dataSource: fieldJson
  });
}

function fieldDataColumnFormat(dataItem){
	if(dataItem.fieldData==undefined){
		return "";
	}else{
		return dataItem.fieldData.name;
	}
}

function picShowEditor(container, options){
	var id="picture_"+parseInt(Math.random()*1000); 
	var grid = $("#status_grid").data("kendoGrid");
	var data = grid.dataItem(grid.select());
	var path = "${pageContext.request.contextPath}" + data.picUrl;
	var img = $('<img id="'+id+'" data-bind="attr:{src:'+path+'}"/>');
	img.appendTo(container);
}

function picUrlTextboxEditor(container, options){
	var id="picInput_"+parseInt(Math.random()*1000);
	var input = $('<input id="'+id+'" type="text" class="k-textbox" data-bind="value:'+options.field+'" />');
	input.appendTo(container);
	
	var dialogWidth = 1024;
	var dialogHeight = 600;
	var url = "${pageContext.request.contextPath}/mx/isdp/global/image/choose?elementId="+id;
	$(input).bind({
		"click":function(){
			if(!!window.ActiveXObject || "ActiveXObject" in window){
	  			//IE
		  		window.showModalDialog(url,self,"edge:raised;scroll:0;status:0;help:0;resizable:0;dialogWidth:"+dialogWidth+"px;dialogHeight:"+dialogHeight+"px;center:true",true);
	  		}else{
	  			var f = "height="+dialogHeight+",width="+dialogWidth+",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=500,left=500,resizable=no,modal=yes,dependent=yes,dialog=yes,minimizable=no";
	  		    window.open(url, self, f, true);
	  		}
		},
		"blur":function(){
			//获取当前选中行
			var grid = $("#status_grid").data("kendoGrid");
			var data = grid.dataItem(grid.select());
	  		data.picUrl = input.val();
		}
	});
}
</script>
</head>
<body>
	<div id="main_content">
	<div id="toolbar"></div>
	<div id="splitter">
		<div id="selected_field_grid"
			data-role="grid" 
			data-editable="false" 
			data-selectable="rows" 
			data-groupable="false"
			data-sortable="true"
			data-pageable="false"
			data-reorderable="false"
			data-columns="[
				{ field:'fname', title:'字段名' }
			]"
			data-bind="
				source:destGridDataSource,
				events:{
					change:onSelectedChange
				}
			"
		></div>
		<div>
		<table id="columnPropertyTable" cellpadding="5" cellspacing="3" border="0" style="width:98%">
			<tr>
				<td colspan="4" style="font-size: 18px;font-style: italic;">列属性：</td>
			</tr>
			<tr>
				<td align="right" width="100px">是否显示</td>
				<td align="left">
					<input type="checkbox" id="isShowList" name="isShowList" data-bind="checked:isShowList,events:{click:saveColumnProperty}" />
				</td>
				<td align="right" width="100px">鼠标悬浮提示</td>
				<td align="left">
					<input type="checkbox" id="isShowTooltip" name="isShowTooltip" data-bind="checked:isShowTooltip,events:{click:saveColumnProperty}" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100px">是否编辑</td>
				<td align="left">
					<input type="checkbox" id="isEditor" name="isEditor" data-bind="checked:isEditor,events:{click:saveColumnProperty}" />
				</td>
				<td align="right" width="100px">必填项</td>
				<td align="left"><input type="checkbox" id="isRequired" name="isRequired" data-bind="checked:isRequired,enabled:isEditor,events:{click:saveColumnProperty}" /></td>
			</tr>
			<tr>
				<td align="right" width="100px">是否过滤</td>
				<td align="left">
					<input type="checkbox" id="isFilterable" name="isFilterable" data-bind="checked:isFilterable,events:{click:saveColumnProperty}" />
				</td>
				<td align="right" width="100px">是否排序</td>
				<td align="left"><input type="checkbox" id="isSortable" name="isSortable" data-bind="checked:isSortable,events:{click:saveColumnProperty}" /></td>
			</tr>
			<tr>
				<td align="right" width="100px">是否可隐藏</td>
				<td align="left">
					<input type="checkbox" id="isMenu" name="isMenu" data-bind="checked:isMenu,events:{click:saveColumnProperty}" />
				</td>
				<td align="right" width="100px">&nbsp;</td>
				<td align="left">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" width="100px">插入链接</td>
				<td align="left">
					<input type="checkbox" id="isLink" name="isLink" data-bind="checked:isLink,events:{click:saveColumnProperty}" />
				</td>
				<td align="right" width="100px">链接页面</td>
				<td align="left">
					<input type="text" id="linkPageName" name="linkPageName" class="k-textbox"  style="width:120px" readonly="readonly"
						data-bind="
							enabled:isLink,
							value:linkPageName,
							events:{
								click:selectLinkPage
							}
						" />
					<input type="hidden" id="linkPageId" name="linkPageId" class="k-textbox"  style="width:120px"
						data-bind="value:linkPageId" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100px">列宽</td>
				<td align="left">
					<input type="text" id="columnWidth" name="columnWidth" class="k-textbox" data-bind="value:columnWidth,events:{blur:saveColumnProperty}" style="width:120px" />
				</td>
				<td align="right" width="100px">默认值</td>
				<td align="left">
					<input type="text" id="defaultVlue" name="defaultVlue" class="k-textbox" data-bind="value:defaultValue,enabled:isEditor,events:{blur:saveColumnProperty}" style="width:120px" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100px">对齐方式</td>
				<td align="left">
					<input id="alignment" name="alignment" style="width:120px"
						data-role="dropdownlist"
						data-text-field="name"
						data-value-field="value"
						data-option-label="请选择"
						data-bind="
							value:alignment,
							source:alignmentSource,
							events:{
								change:saveColumnProperty
							}"
					 />
				</td>
				<td align="right" width="100px">控件类型</td>
				<td align="left">
					<input id="editor" name="editor" style="width:120px"
						data-role="dropdownlist"
						data-text-field="name"
						data-value-field="dataRole"
						data-value="textfield"
						data-option-label="请选择"
						data-bind="
							value:editor,
							source:editorTypeSource,
							events:{
								change:editorChange
							}
						" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100px">显示格式</td>
				<td align="left">
					<input id="formatValue" name="formatValue" style="width:120px"
						data-role="combobox"
						data-text-field="name"
               			data-value-field="value"
						data-bind="
							value:formatValue,
							source:formatValueSource,
							events:{
								change:saveColumnProperty
							}
						" />
				</td>
				<td align="right" width="100px">数据验证格式</td>
				<td>
					<input id="dataValidation" name="dataValidation" style="width:120px"
						data-role="dropdownlist"
						data-text-field="name"
               			data-value-field="value"
               			data-option-label="请选择"
						data-bind="
							enabled:isEditor,
							value:dataValidation,
							source:dataValidationSource,
							events:{
								change:saveColumnProperty
							}
						" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100px">最小值</td>
				<td align="left">
					<input id="minValueNumericTextBox" name="minValueNumericTextBox" style="width:120px"
						data-role="numerictextbox"
						data-format="n0"
						data-bind="
							visible:isNumeric,
							enabled:isRangeEditor,
							value:numberMinValue,
							events:{
								change:saveColumnProperty
							}
						" />
					<input id="minValueDatePicker" name="minValueDatePicker" style="width:120px"
						data-role="datepicker"
						data-culture="zh-CN"
						data-format="yyyy-MM-dd"
						data-bind="
							visible:isDate,
							enabled:isRangeEditor,
							value:dateMinValue,
							events:{
								change:saveColumnProperty
							}
						" />
					<input id="minValueDateTimePicker" name="minValueDateTimePicker" style="width:120px"
						data-role="datetimepicker"
						data-culture="zh-CN"
						data-format="yyyy-MM-dd HH:mm:ss"
						data-bind="
							visible:isDateTime,
							enabled:isRangeEditor,
							value:dateMinValue,
							events:{
								change:saveColumnProperty
							}
						" />
					<input id="minValueTimePicker" name="minValueTimePicker" style="width:120px"
						data-role="timepicker"
						data-culture="zh-CN"
						data-format="HH:mm:ss"
						data-bind="
							visible:isTime,
							enabled:isRangeEditor,
							value:dateMinValue,
							events:{
								change:saveColumnProperty
							}
						" />
				</td>
				<td align="right" width="100px">最大值</td>
				<td align="left">
					<input id="maxValueNumericTextBox" name="maxValueNumericTextBox" style="width:120px"
						data-role="numerictextbox"
						data-format="n0"
						data-bind="
							visible:isNumeric,
							enabled:isRangeEditor,
							value:numberMaxValue,
							events:{
								change:saveColumnProperty
							}
						" />
					<input id="maxValueDatePicker" name="maxValueDatePicker" style="width:120px"
						data-role="datepicker"
						data-culture="zh-CN"
						data-format="yyyy-MM-dd"
						data-bind="
							visible:isDate,
							enabled:isRangeEditor,
							value:dateMaxValue,
							events:{
								change:saveColumnProperty
							}
						" />
					<input id="maxValueDateTimePicker" name="maxValueDateTimePicker" style="width:120px"
						data-role="datetimepicker"
						data-culture="zh-CN"
						data-format="yyyy-MM-dd HH:mm:ss"
						data-bind="
							visible:isDateTime,
							enabled:isRangeEditor,
							value:dateMaxValue,
							events:{
								change:saveColumnProperty
							}
						" />
					<input id="maxValueTimePicker" name="maxValueTimePicker" style="width:120px"
						data-role="timepicker"
						data-culture="zh-CN"
						data-format="HH:mm:ss"
						data-bind="
							visible:isTime,
							enabled:isRangeEditor,
							value:dateMaxValue,
							events:{
								change:saveColumnProperty
							}
						" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100px">是否验证数据</td>
				<td align="left"><input type="checkbox" id="isValidate" name="isValidate" data-bind="enabled:isEditor,checked:isValidate,events:{click:saveColumnProperty}" /></td>
				<td align="right" width="100px">验证提示信息</td>
				<td align="left"><input type="text" id="validateMsg" name="validateMsg" class="k-textbox" data-bind="enabled:isValidate,value:validateMsg,events:{blur:saveColumnProperty}" style="width:120px" /></td>
			</tr>
			<tr>
				<td align="right" width="100px">控件数据源类型</td>
				<td align="left">
				<input id="widgetSourceTypeDropdownList" name="widgetSourceTypeDropdownList" style="width:120px"
					data-role="dropdownlist"
					data-text-field="name"
					data-value-field="value"
					data-option-label="请选择"
					data-bind="
						value:widgetSourceType,
						source:widgetSourceTypeSource,
						events:{
							change:widgetSourceTypeChange
						}
					" />
					
				</td>
				<td align="right" width="100px">控件数据源</td>
				<td align="left">
					<input id="widgetSourceDropDownList" name="widgetSourceDropDownList" style="width:120px"
						data-role="dropdownlist"
						data-text-field="name"
						data-value-field="code"
						data-option-label="请选择"
						data-bind="
							visible:isDictVisible,
							value:widgetDictSource,
							events:{
								change:saveColumnProperty
							}
						" />
					<input type="text" id="widgetTableSourceTextbox" name="widgetTableSourceTextbox" class="k-textbox" style="width:120px"
						onclick="javascript:open_widgetSource_window(20)"
						data-bind="
							visible:isTableVisible,
							value:widgetTableSource
						" />
					<input type="text" id="widgetUserSourceTextbox" name="widgetUserSourceTextbox" class="k-textbox" style="width:120px"
						onclick="javascript:open_widgetSource_window(30)"
						data-bind="
							visible:isUserVisible,
							value:widgetUserSource
						" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100px">状态图片</td>
				<td align="left">
					<input id="statusPic" name="statusPic" class="k-textbox" style="width:120px"
						onclick="javascript:open_satatus_window()"
						data-bind=" value:statusPic " />
				</td>
				<td align="right" width="100px"></td>
				<td align="left"></td>
			</tr>
		</table>
		</div>
	</div>
	</div>
	<div id="widget_ds_content">
		<div id="widget_ds_grid" style="height: 260px;width: 450px"
			data-role="grid"
			data-toolbar="['create','save']"
			data-editable="true"
			data-selectable="rows" 
			data-groupable="false"
			data-sortable="false"
			data-pageable="false"
			data-reorderable="false"
			data-columns="[
				{ field:'text', title:'显示值' },
				{ field:'value', title:'实际值'},
				{ command: 'destroy', title:'',width:'80px'}
			]"
			data-bind="
				source: widget_ds_grid_dataSource,
				events: {
					saveChanges: onSaveChanges
				}
			"
		></div>
	</div>
	
	<div id="widget_ds_table_content">
		<div id="widget_ds_table_toolbar" style="width:100%"></div>
		<table cellpadding="5" cellspacing="2" border="0" style="width:350px">
			<tr>
				<td align="right">选择数据表</td>
				<td>
					<input type="text" id="wigetSourcetableName" name="wigetSourcetableName" class="k-textbox" />
					<input type="hidden" id="widgetSourceTableObj" name="widgetSourceTableObj" />
				</td>
			</tr>
			<tr>
				<td align="right">ID</td>
				<td><input id="wsTableIdDropDownList" name="wsTableIdDropDownList" 
					data-role="dropdownlist" 
					data-text-field="fname"
		            data-value-field="dname" />
		         </td>
			</tr>
			<tr>
				<td align="right">parent_id</td>
				<td><input id="wsTableParentIdDropDownList" name="wsTableParentIdDropDownList" 
					data-role="dropdownlist" 
					data-text-field="fname"
		            data-value-field="dname" />
				</td>
			</tr>
			<tr>
				<td align="right">显示值</td>
				<td><input id="wsTableTextDropDownList" name="wsTableTextDropDownList" 
					data-role="dropdownlist" 
					data-text-field="fname"
		            data-value-field="dname"/>
				</td>
			</tr>
			<tr>
				<td align="right">实际值</td>
				<td><input id="wsTableValueDropDownList" name="wsTableValueDropDownList" 
					data-role="dropdownlist" 
					data-text-field="fname"
		            data-value-field="dname" />
				</td>
			</tr>
		</table>
	</div>
	
	<div id="linkpage_select_content" style="overflow:auto;width:300px;height: 370px">
		<ul id="linkpageTree" class="ztree"></ul>
	</div>
	
	<div id="status_grid_content">
		<div id="status_grid" style="height: 260px;width: 450px"
			data-role="grid"
			data-toolbar="['create','save']"
			data-editable="true"
			data-selectable="rows" 
			data-groupable="false"
			data-sortable="false"
			data-pageable="false"
			data-reorderable="false"
			data-columns="[
				{ field:'fieldData', title:'图片值字段',editor:fieldDropdownEditor,template:fieldDataColumnFormat },
				{field:'picUrl', title:'图片',editor:picShowEditor},
				{ field:'picUrl', title:'图片地址',editor:picUrlTextboxEditor},
				{ command: 'destroy', title:'',width:'80px'}
			]"
			data-bind="
				source: status_grid_dataSource,
				events: {
					saveChanges: onSaveChanges
				}
			"
		></div>
	</div>
</body>
</html>