<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.security.LoginContext"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	if (request.getAttribute("userTheme") != null) {
		UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
		request.setAttribute("theme", userTheme.getThemeStyle());
		request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
	} else {
		String theme = com.glaf.core.util.RequestUtils.getTheme(request);
		request.setAttribute("theme", theme);
		String homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
		request.setAttribute("homeTheme", homeTheme);
	}
	LoginContext loginContext = RequestUtils.getLoginContext(request);
	request.setAttribute("loginContext", loginContext);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/apps/css/inbox.min.css" rel="stylesheet" type="text/css"/>
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
		<!-- dialog --> 
		<link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">
		<!--ztree-->
		<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
		<!--图片弹窗-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />

		<!--grid-->
		<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>
		<link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/treelist/css/treelist.css">
		
		<style type="text/css">
			.myTableStyle{
				border: 1px solid #989898;
			}
			.myTableStyle caption{
				border-bottom:1px solid #989898;
				padding-left:8px
			}
			.myTableStyle thead{
				font-size:20px;

			}
			.myTableStyle thead tr th{
				border-bottom:1px solid #989898;
				border-right:1px solid #989898;
				text-align: center;
			}
			.myTableStyle thead tr th:last-child{
				border-right-style:none;	
			}
		</style>
</head>
<body>
	<template id="createSelectData_template"> 
		<div class="tabbable-line">
			<ul class="nav nav-tabs">
	            <li class="active">
	                <a href="#tab_1" data-toggle="tab" aria-expanded="false"> 数据录入 </a>
	            </li>
	            <li class="pull-right" style="padding-top:6px；padding-right:6px">
	            	<button type="button" id="sureButton" class="btn blue pull-right btn-sm" style=""><i class="glyphicon glyphicon-ok"></i>确认</button>
	            </li>
	        </ul>
	        <div class="tab-content" style="padding:10px 0px;">
				<div class="tab-pane active" id="tab_0">
					<div class="col-md-12" id="content">
						<button type="button" id="addFieldBtn" class="btn red btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>新增</button>
						<button type="button" id="sureFieldBtn" class="btn btn-success btn-sm" style=""><i class="glyphicon glyphicon-ok"></i>一键确认</button>
						<div style="margin-top:10px">
							<div id="fieldGrid"></div>
						</div>
					</div>
				</div>
	        </div>
		</div>
	</template>
	<template id="createGridData_template"> 
		<div class="tabbable-line">
			<ul class="nav nav-tabs">
	            <li class="active">
	                <a href="#tab_0" data-toggle="tab" aria-expanded="false"> 表头信息 </a>
	            </li>
	            <li class="" id="gridDataLi">
	                <a href="#tab_1" data-toggle="tab" aria-expanded="false"> 数据录入 </a>
	            </li>
	            <li class="pull-right" style="padding-top:6px;padding-right:6px">
	            	<button type="button" id="sureButton" class="btn blue pull-right btn-sm" style=""><i class="glyphicon glyphicon-ok"></i>确认</button>
	            </li>
	        </ul>
	        <div class="tab-content" style="padding:10px 0px;">
	        	<!-- <div class="tab-pane active" id="tab_0">
	        	            		<div class="col-md-12" style="padding:5px 5px;">
	        	            					        <table class="table myTableStyle" id="grid_field_table">
	        	            					            <caption > 
	        	            					                <button type="button" id="addFieldBtn" class="btn btn-info btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>新增</button>
	        	            					            </caption>
	        	            					            <thead>
	        	            					                <tr>
	        	            					                    <th>字段中文名称</th>
	        	            					                    <th>字段别名</th>
	        	            					                    <th></th>
	        	            					                    <th>操作</th>
	        	            					                </tr>
	        	            					            </thead>
	        	            					                <tbody>
	        	            					                <tr>
	        	            					                    <td width="30%"> <select name="startField" class="startField" style="width:100%"></select> </td>
	        	            					                    <td width="20%"> <select name="combinedType" class="combinedType" style="width:100%"></select> </td>
	        	            					                    <td width="30%"><div> <select name="endField" class="endField" style="width:100%"></select> </div></td>
	        	            					                    <td width="20%" style="text-align:center;"> 
	        	            					                        <button type="button" id="sureButton" class="btn btn-danger btn-sm" style=""><i class="glyphicon glyphicon-remove"></i>删除</button> 
	        	            					                    </td>
	        	            					                </tr>
	        	            					            </tbody>
	        	            					        </table>
	        	            					    </div>
	        	            	</div>     -->            	
				<div class="tab-pane active" id="tab_0">
					<div class="col-md-12" id="content">
						<button type="button" id="addFieldBtn" class="btn red btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>新增</button>
						<button type="button" id="sureFieldBtn" class="btn btn-success btn-sm" style=""><i class="glyphicon glyphicon-ok"></i>一键确认</button>
						<div style="margin-top:10px">
							<div id="fieldGrid"></div>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="tab_1">
					<div class="col-md-12" id="content1">
						<button type="button" id="addDataBtn" class="btn red btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>新增</button>
						<button type="button" id="sureDataBtn" class="btn btn-success btn-sm" style=""><i class="glyphicon glyphicon-ok"></i>一键确认</button>
						<div style="margin-top:10px">
							<div id="dataGrid"></div>
						</div>
					</div>
				</div>
	        </div>
		</div>
	</template>
	<template id="addField_template">
		<tr>
			<td width="30%" style="padding:5px;"> <input class="form-control" type="text"><span class="surebtn"></span> </td>
			<td width="20%" style="padding:5px;"> <input class="form-control" type="text"><span class="surebtn"></span> </td>
            <!-- <td width="30%"><div> <select name="endField" class="endField" style="width:100%"></select> </div></td> -->
            <td width="20%" style="padding-left:10px"> 
            	<!-- <button type="button" class="addFieldBtn btn btn-info btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>新增</button> -->
            	<button type="button" class="editeButton btn btn-success btn-sm" style=""><i class="glyphicon glyphicon-edit"></i>确认</button>
            	<button type="button" class="editeButton btn btn-success btn-sm" style=""><i class="glyphicon glyphicon-edit"></i>取消</button>
                <button type="button" class="deleteButton btn btn-danger btn-sm" style=""><i class="glyphicon glyphicon-remove"></i>删除</button> 
            </td>
        </tr>
	</template>
	<!--$("#thumbnail_template").html()-->
  <template id="thumbnail_template">
     <div class="thumbnail">
	     <span class="mask"></span>
		 <img class="img-responsive" src="${contextPath}/images/a.jpg" >
		 <div class="caption">
			<p class="btarea" style="text-align:center;">
			   <a href="#" class="btn thumbnailbt applyTemplateBt" role="button">
				  选用
			   </a>
			</p>
		 </div>
		 <h3>模板1</h3>
	  </div>
  </template>


<script type="text/javascript">
var contextPath='${contextPath}';
var id='${param.nameElementId}';
var hiddenId='${param.objelementId}';
var designerJson='${desingerJson}';
var action='${param.action}'==''?'join':'${param.action}';
var type = '${param.type}';
var pageId_ = '${param.pageId}';
var componentId_ = '${param.componentId}';
</script>
<!-- END FOOTER -->
<!--[if lt IE 9]>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/respond.min.js"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.svg.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>

<!-- dialog --> 
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
<!--grid-->
<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src="/glaf/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.js" type="text/javascript"></script>

	
<script type ="text/javascript">
	var dataGrid_data_ = null;
	var fieldGrid_data_ = null;
	var setting = {
		datas:[],
		// tableCls: 'table myTableStyle',
	    resizable: false,
	    scrollable: false,
	    clickUpdate: false,
	    occupy: false,
	    sortable: {},
	    selectable: '',
	    showInLine: true,
	    sortDesc: false,
	    combineAble: false,
	    // toolbar: "[{'name':'create','text':'新增'}]",
	    columns:[

	        {title:'字段名称', field:'columnName', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:center;'
	        }
	        },
	        {title:'类型', field:'type', width:80, sortable:false,'isEditor':true,'editor':{'component':'dropdownlist',
				url : '/mx/form/defined/getDictByCode?code=StaticChartsType',
	        	params : {
	        	text : 'name',
	        	value : 'code'
	        }}, 'attributes': {
	            'style': 'text-align:center;'
	        }       
	        },
	        {'width':'120px','command':[
	        	{'name':'edit','text':'编辑'},
	        	{'name':'destroy','text':'删除'},
	        	{'name':'upward','text':'↑'},
	        	{'name':'downward','text':'↓'},
	        	// {'name':'up','text':'上移','icon':'fa-arrow-up','color':'blue'},
	        	// {'name':'down','text':'下移','icon':'fa-arrow-down','color':'blue'},
	        ]}
	    ],
    }

    function initGridData(){
    	if(pageId_ && componentId_){
    		$.ajax({
				url: contextPath + '/mx/form/staticFile/download',
				type: "post",
				data: {pageId:pageId_,componentId:componentId_},
				async: false,
				dataType: 'text',
				success: function(ret) {
					if(ret){
						var staticData = JSON.parse(ret);
						var datas = staticData.staticDataRule;
						dataGrid_data_ =  datas.dataGrid.datas;
						fieldGrid_data_ = datas.fieldGrid.datas;
					}
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
    	}
    	if(!dataGrid_data_ && !fieldGrid_data_){
    		var targetId = window.getParameter("targetId");
	    	var $parent = window.opener || window.parent;
	    	if($parent && $parent.document.getElementById(targetId)){
	    		var dataStr = $parent.document.getElementById(targetId).value;
	    		if(dataStr){
		    		var datas = JSON.parse(dataStr);
		    		dataGrid_data_ =  datas.dataGrid.datas;
		    		fieldGrid_data_ = datas.fieldGrid.datas;
		    	}
	    	}
    	}
    }

    function initByGridOrTreeType(type){
    	var $body = $("body");
    	$body.append($("#createGridData_template").html());
    	initGridData();
		
    	var initfunc = 'grid';
    	if(type == 'tree' || type == 'ztree' || type == 'charts'){
    		initfunc = 'treelist';
    	}
    	if(type == 'nestable'){
    		initfunc = 'nestable';
    	}
    	
		//字段grid
		var $fieldGrid = $body.find("#fieldGrid");
		if(fieldGrid_data_){
			setting.datas = fieldGrid_data_;
		}
		$fieldGrid.grid(setting);

		$fieldGrid.on("click", "a.btn[atype]", function(e) {
			var $btn = $(this);
			if ($btn.attr('atype') === 'up' || $btn.attr('atype') === 'down') {

			}
		})

		$("#addFieldBtn").click(function(){
			$fieldGrid.grid("_newRow");
		})
		$("#sureFieldBtn").click(function(){
			$fieldGrid.grid("endEdit");
		})

		//数据录入grid
		var $dataGrid = $body.find("#dataGrid");

		if(type == 'tree' || type == 'ztree' || type == 'nestable'){
			setting.toolbar = "[{'name':'addBrother','text':'同级新增'},{'name':'addChildren','text':'子级新增'}]";
			$("#addDataBtn").hide();
			$("#sureDataBtn").hide();
		}
		else{
			$("#addDataBtn").click(function(){
				$dataGrid[initfunc]("_newRow");
			})
			$("#sureDataBtn").click(function(){
				$dataGrid[initfunc]("endEdit");
			})
		}
		
		reInitDataGrid($dataGrid,$fieldGrid,initfunc,type);
		// .tabs({
		$body.on('shown.bs.tab','#gridDataLi a[data-toggle="tab"]',function (e) {
			reInitDataGrid($dataGrid,$fieldGrid,initfunc,type);
		})

		//确认返回
		$("#sureButton").click(function(event){
			$fieldGrid.grid("endEdit");
			$dataGrid[initfunc]("endEdit");

			var data = $dataGrid[initfunc]("getData");
			$.each(data,function(i,item){
				delete item["_items_"];
				delete item["__Old"];
				delete item["__dirties"];
				delete item["children"];
			})
			var fieldGridDatas = $fieldGrid.grid("getData");
			$.each(fieldGridDatas,function(i,item){
				delete item["_items_"];
				delete item["__Old"];
				delete item["__dirties"];
				delete item["children"];
			})
			var retData = {
				dataGrid:{
					columns : $dataGrid.data()[initfunc].options.columns,
					datas : data
				},
				fieldGrid : {
					datas : fieldGridDatas
				}
			}
			callBack2(retData);
		})
    }

    function reInitDataGrid($dataGrid,$fieldGrid,initfunc,type){
    	var fieldColumn = $fieldGrid.grid("getData");
		var dataGrid_data = null;
		try{
			dataGrid_data = $dataGrid[initfunc]("getData");
		}catch(ex){
			dataGrid_data = null;
		}
		
		if(!fieldColumn || fieldColumn.length == 0){
			$dataGrid.empty();
			$dataGrid.removeData();
			return;
		}
		if(dataGrid_data && dataGrid_data.length > 0){
			dataGrid_data_ = dataGrid_data;
		}
		$dataGrid.empty();
		$dataGrid.removeData();
		var columns = [];
		if(type && type == 'ztree'){
			columns.push({title:'显示名称', field:'name', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:left;'
	        },})
		}
		if(type && type == 'treelist'){
			columns.push({title:'显示名称', field:'name', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:left;'
	        },})
		}
		if(type && type == 'nestable'){
			columns.push({title:'显示名称', field:'name', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:left;'
	        },})
		}
		if(type && type == 'carousel'){
			columns.push({title:'图片', field:'image', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:left;'
	        },})
		}
		if(type && type == 'metrolist'){
			columns.push({title:'内容', field:'content', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:left;'
	        },})
		}
		if(type && type == 'gridlist'){
			columns.push({title:'内容', field:'content', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:left;'
	        },})
		}
		if(type && type == 'definedcard'){
			columns.push({title:'内容', field:'content', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:left;'
	        },})
		}
		
		
		for(var i = 0 ; i < fieldColumn.length; i++){
			var item = fieldColumn[i];
			var column = {
				title:item.columnName,
				field:item.columnName,
				isEditor:true,
				editor:{'component':'maskedtextbox'}, 
				attributes: {
		            'style': 'text-align:left;'
		        },
			}
			columns.push(column);
		}
		columns.push({'width':'120px','command':[{'name':'edit','text':'编辑'},{'name':'destroy','text':'删除'}]});
		setting.columns = columns;
		// setting.toolbar = "[{'name':'create','text':'新增'}]";
		if(dataGrid_data_ && dataGrid_data_.length >0){
			setting.datas = dataGrid_data_;
		}else{
			setting.datas = [];
		}
		$dataGrid[initfunc](setting);
    }

    function callBack2(data){
    	var data = JSON.stringify(data);
		var fn = window.getParameter("fn"), targetId = window.getParameter("targetId");
		var $parent = window.opener || window.parent;
		if(fn && $parent[fn]){
			$parent[fn].call($parent.document.getElementById(targetId), data);
		}
	}

	function getParameter(name){
		var params = {};
	 	var search = window.location.search;
		if(search && search.length > 1){
		 	search = search.substring(1);
			search = search.split("&");
			var s;
			$.each(search, function(i, v){
				s = v.split("=");
				params[s[0]] = s[1];
			});
		}
		return params[name];
	 }

	$(function(){
		var $body = $("body");
		if(type == 'grid' || type == 'tree' || type == 'ztree' 
				|| type == 'charts' || type == 'definedcard' || 
				type == 'nestable' || type == 'metrolist' || type == 'gridlist' || type == 'carousel'){
			initByGridOrTreeType(type);
		}else if(type == 'select'){
			initBySelectType(type);
		}
	})

	function initSelectData(){
    	if(!fieldGrid_data_){
    		var targetId = window.getParameter("targetId");
	    	var $parent = window.opener || window.parent;
	    	if($parent && $parent.document.getElementById(targetId)){
	    		var dataStr = $parent.document.getElementById(targetId).value;
	    		if(dataStr){
		    		var datas = JSON.parse(dataStr);
		    		fieldGrid_data_ =  datas.dataGrid.datas;
		    	}
	    	}
    	}
    }

	function initBySelectType(type){
    	setting.columns = [
	        // {title:'字段中文名称', field:'title', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	        //     'style': 'text-align:center;'
	        // },},
	        {title:'内容', field:'value', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
	            'style': 'text-align:center;'
	        },},
	        {'width':'120px','command':[
	        	{'name':'edit','text':'编辑'},
	        	{'name':'destroy','text':'删除'},
	        	{'name':'upward','text':'↑'},
	        	{'name':'downward','text':'↓'},
	        	// {'name':'up','text':'上移','icon':'fa-arrow-up','color':'blue'},
	        	// {'name':'down','text':'下移','icon':'fa-arrow-down','color':'blue'},
	        ]}
	    ]

    	var $body = $("body");
    	$body.append($("#createSelectData_template").html());
    	initSelectData();
		

		//字段grid
		var $fieldGrid = $body.find("#fieldGrid");
		if(fieldGrid_data_){
			setting.datas = fieldGrid_data_;
		}
		
		$fieldGrid.grid(setting);

		$("#addFieldBtn").click(function(){
			$fieldGrid.grid("_newRow");
		})
		$("#sureFieldBtn").click(function(){
			$fieldGrid.grid("endEdit");
		})

		//确认返回
		$("#sureButton").click(function(event){
			$fieldGrid.grid("endEdit");

			var fieldGridDatas = $fieldGrid.grid("getData");
			$.each(fieldGridDatas,function(i,item){
				delete item["_items_"];
				delete item["__Old"];
				delete item["__dirties"];
				delete item["children"];
			})
			var retData = {
				dataGrid : {
					datas : fieldGridDatas
				}
			}
			callBack2(retData);
		})
    }
</script>
</body>
</html>