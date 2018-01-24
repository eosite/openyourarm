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
<script type="text/javascript">
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}';
	var pageId = '${param.pageId}';
	var source = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	if(objsource){
		var objJson = JSON.parse(objsource);
		if(objJson[0].value){
			source = objJson[0].value ;
		}
	}
	// 获取字典数据
	function buildSource(type,urlType) {
		if(urlType){
			urlType = "getDictByCodeOld" ;
		}else{
			urlType = "getDictByCode";
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

	function rushGrid(){
		 var sgrid = $("#grid").data("kendoGrid");
		 sgrid.dataSource.read();
	}
	var jsgisParams = buildSource('${param.code}' || 'jsgisParam');
	jsgisParams.fetch().then(function(){
		 rushGrid();
	});//加载数据源
	var outputParams = [] ;
	function getOutputParams(){
		var doc = parent.mtxx.jQueryFrame.contents();
		var crtltypes = doc.find('[crtltype]');
		if(crtltypes && crtltypes.length){
			jQuery.each(crtltypes,function(index,item){
				var id = jQuery(this).attr('id');
				var text = jQuery(this).attr('title') || jQuery(this).attr('cname') || id;
				outputParams.push({code : id,name : text});
			});
		}
	}
	getOutputParams();
</script>
</head>
<body>
	<div id="grid"></div>
	<script type="text/javascript">
		//初始化图片链接grid
		$("#grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : source,
				schema : {
					model : {
						id : 'id',
						fields : {
							name : {
								editable : true
							},
							output : {
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
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'name',
				title : '字段',
				template : function(dataItem){
					var ret = "" ;
					jsgisParams.fetch().then(function(){
						 var data = jsgisParams.data();
						 for(var i=0;i<data.length ;i++){
							 if(data[i].code == dataItem.name){
								 ret = data[i].name ;
							 }
						 }
					})
					return ret ;
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).
					 kendoDropDownList({dataValueField:'code',dataTextField:'name',dataSource:jsgisParams});
				},
			}, {
				field : 'output',
				title : '输出控件',
				template : function(dataItem){
					var ret = "" ;
					 for(var i=0;i<outputParams.length ;i++){
						 if(outputParams[i].code == dataItem.output){
							 ret = outputParams[i].name ;
						 }
					 }
					return ret ;
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).
					 kendoDropDownList({dataValueField:'code',dataTextField:'name',dataSource:outputParams});
				},
			}, {
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = "" ;
				for (var i = 0; i < values.length; i++) {
					if (values[i].name == undefined || values[i].name == "" || values[i].output == undefined || values[i].output == "") {
						layer.alert("必须填写值", 3);
						return;
					}
					names += values[i].name + "," ;
				}
				var reAry = [] ;
				var reObj = {};
				reObj.name = names ;
				reObj.value =  values;
				reAry.push(reObj);
				window.parent.$('#'+nameElementId).val(names);
				window.parent.$('#'+objelementId).val(JSON.stringify(reAry));
				parent.layer.close(parent.layer.getFrameIndex());
			},
			edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});
		
	</script>
</body>
</html>