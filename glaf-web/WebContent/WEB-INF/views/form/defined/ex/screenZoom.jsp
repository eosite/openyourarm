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
	function rushGrid(){
		$("#grid").data("kendoGrid").dataSource.read();
	}
	var dataIndex ;
	
	gridSource = [];
	var objsource = window.parent.$('#'+objelementId).val() ;
	if(objsource){
		gridSource = $.merge(gridSource,JSON.parse(objsource)[0].values?JSON.parse(objsource)[0].values:[]);
	}
	
	$(document).ready(function() {
		
		$("#grid").kendoGrid({
			dataSource : gridSource,
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'maxWidth',
				title : '屏幕宽度小于(像素)',
				template : function(d){
					return d.maxWidth?d.maxWidth + "px" : "" ;
				},
				editor : function(container,options){
					 $("<input/>").attr({'name':options.field}).appendTo(container).kendoNumericTextBox({min:1});
				}
			},{
				field : 'minWidth',
				title : '屏幕宽度大于(像素)',
				template : function(d){
					return d.minWidth?d.minWidth + "px" : "" ;
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({min:1});
				}
			},{
				field : 'maxHeight',
				title : '屏幕高度小于(像素)',
				template : function(d){
					return d.maxHeight?d.maxHeight + "px": "" ;
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({min:1});
				}
			},{
				field : 'minHeight',
				title : '屏幕高度大于(像素)',
				template : function(d){
					return d.minHeight?d.minHeight + "px":"" ;
				},
				editor : function(container,options){
					 $("<input/>").attr('name',options.field).appendTo(container).kendoNumericTextBox({min:1});
				}
			},{
				field : 'widtha',
				title : '宽度',
				/* editor : function(container,options){
					 var widtha = options.model.widtha || "" ;
					 var wid="" ,tha="" ;
					 if(widtha.indexOf("%")!=-1){
						 wid = widtha.replace("%","");
						 tha = "%" ;
					 }else{
						 wid = widtha.replace("px","");
						 tha = "px" ;
					 }
					 $("<input />").attr({'id':'wid',style :'width:80px'}).appendTo(container).kendoNumericTextBox({value : wid,change: function() {
					        var v = this.value();
					        var v2 = $('#tha').val();
					 }});
					 $("<input style='width:80px;' />").attr('id','tha').appendTo(container).
					 kendoDropDownList({value : tha,dataValueField:'value',dataTextField:'text',dataSource:[{value:'%',text:'百分比'},{value:'px',text:'像素'}],
						 change: function(e) {
						      var value = this.value();
						 }});
				} */
			},{
				field : 'heighta',
				title : '高度',
			}, {
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = "" ;	
				for (var i = 0; i < values.length; i++) {
					if (values[i].widtha == undefined || values[i].widtha.trim() == "" || values[i].heighta == undefined || values[i].heighta.trim() == "") {
						layer.alert("必须填写高度和宽度", 3);
						return;
					}else if((values[i].maxWidth == undefined || values[i].maxWidth == "") && (values[i].minWidth == undefined || values[i].minWidth == "")
							 && (values[i].maxHeight == undefined || values[i].maxHeight == "") && (values[i].minHeight == undefined || values[i].minHeight == "")){
						layer.alert("屏幕像素必须填写一项", 3);
						return;
					} 
				}
				var retAry = [] ;
				var retObj = {} ;
				if(values.length){
					names = "屏幕缩放模式" ;
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
		
	});
</script>
</head>
<body>
	<div id="grid"></div>
</body>
</html>