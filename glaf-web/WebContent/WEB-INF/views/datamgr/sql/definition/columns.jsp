<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=appTitle%></title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
	var contextPath = "${contextPath}";

	function closeDialog() {
		window.close();
	}
	 

	//明细列表
	var editcount = 0;
	jQuery(function() {
		jQuery('#mydatagrid').datagrid(
						{
							width : 745,
							height : 220,
							fit : true,
							fitColumns : true,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '${contextPath}/mx/datamgr/sql/definition/columns?sqlDefId=${sqlDefId}',
							remoteSort : false,
							singleSelect : true,
							idField : 'id',
							columns : [ [
									{
										title : '序号',
										field : 'startIndex',
										width : 80,
										sortable : false
									},
									{
										title : '名称',
										field : 'name',
										width : 280,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'notNullAndLength[200]'
											}
										}
									},
									{
										title : '字段名',
										field : 'columnName',
										width : 280,
										editor : {
											type : 'validatebox',
											options : {
												required : true,
												validType : 'Maxlength[50]'
											}
										}
									},
									{
										title : '列表显示长度',
										field : 'width',
										width : 120,
										align : 'right',
										editor : {
											type : 'numberbox',
											options : {
												required : false,
												min : 1,
												max : 4000
											}
										}
									},
									{
										title : '标题',
										field : 'title',
										width : 250,
										editor : {
											type : 'validatebox',
											options : {
												required : false,
												validType : 'Maxlength[250]'
											}
										}
									},
									{
										title : '显示',
										field : 'displayType',
										width : 120,
										editor : {
											type : 'combobox',
											options : {
												required : true,
												editable : false,
												url : '${contextPath}/rs/datamgr/sql/definition/displayType',
												valueField : 'code',
												textField : 'text'
											}
										},
										formatter : function(value, row, index) {
											var displayType = "";
											jQuery.ajax({
														type : "POST",
														url : '${contextPath}/rs/datamgr/sql/definition/displayType',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null && data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		displayType = data[i].text;
																	}
																}
															}
														}
													});
											return displayType;
										}
									},
									{
										title : '类型',
										field : 'javaType',
										width : 110,
										editor : {
											type : 'combobox',
											options : {
												required : true,
												editable : false,
												url : '${contextPath}/rs/datamgr/sql/definition/javaType',
												valueField : 'code',
												textField : 'text'
											}
										},
										formatter : function(value, row, index) {
											var javaType = "";
											jQuery.ajax({
														type : "POST",
														url : '${contextPath}/rs/datamgr/sql/definition/javaType',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null && data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		javaType = data[i].text;
																	}
																}
															}
														}
													});
											return javaType;
										}
									},
									{
										title : '显示顺序',
										field : 'ordinal',
										width : 110,
										editor : {
											type : 'combobox',
											options : {
												required : true,
												editable : false,
												url : '${contextPath}/rs/datamgr/sql/definition/sortNo',
												valueField : 'code',
												textField : 'text'
											}
										},
										formatter : function(value, row, index) {
											var ordinal = "";
											jQuery.ajax({
														type : "POST",
														url : '${contextPath}/rs/datamgr/sql/definition/sortNo',
														dataType : 'json',
														async : false,
														success : function(data) {
															if (data != null && data.message != null) {
																alert(data.message);
															} else {
																for ( var i = 0; i < data.length; i++) {
																	if (data[i].code == value) {
																		ordinal = data[i].text;
																	}
																}
															}
														}
													});
											return ordinal;
										}
									},
									{
										title : '操作',
										field : 'functionKey',
										width : 120,
										formatter : function(value, row, index) {
											if (row.editting) {
												var s = "<a href='#' onclick='saveDetail("
														+ index + ")'>保存</a> ";
												var c = "<a href='#' onclick='cancelrow("
														+ index + ")'>取消</a> ";
												return s + c;
											} else {
												var e = "<a href='#' onclick='editrow("
														+ index + ")'>编辑</a> ";
												return e;
											}
										}
									} ] ],
							rownumbers : false,
							pagination : false,
							onBeforeEdit : function(index, row) {
								row.editting = true;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount++;
							},
							onAfterEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount--;
							},
							onCancelEdit : function(index, row) {
								row.editting = false;
								jQuery('#mydatagrid').datagrid('refreshRow',
										index);
								editcount--;
							}
						});

		var p = jQuery('#mydatagrid').datagrid('getPager');
		jQuery(p).pagination({
			onBeforeRefresh : function() {
				//alert('before refresh');
			}
		});
	});
	
	
	//明细增加
	function addNew() {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，不能增加记录。");
			return;
		}
		var index = jQuery("#mydatagrid").datagrid("getRows").length;
		jQuery("#mydatagrid").datagrid("appendRow", {});
		jQuery("#mydatagrid").datagrid("beginEdit", index);
	}
	
	
	//明细取消
	function cancelrow(indexs) {
		var bol = $("#mydatagrid").datagrid("validateRow", indexs);
		jQuery('#mydatagrid').datagrid("cancelEdit", indexs);
		jQuery('#mydatagrid').datagrid("endEdit", indexs);
		jQuery('#mydatagrid').datagrid('refreshRow', indexs);
		$("#mydatagrid").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid").datagrid("getSelections");
		if (bol == false) {
			 
		}
	}
	
	
	//明细编辑
	function editrow(indexs) {
		if (editcount > 0) {
			alert("当前还有" + editcount + "记录正在编辑，暂不能编辑。");
			return;
		}
		jQuery('#mydatagrid').datagrid("beginEdit", indexs);
	}
	
	
	//明细保存
	function saveDetail(indexs) {
		$("#mydatagrid").datagrid("endEdit", indexs);
		$("#mydatagrid").datagrid("selectRow", indexs);
		var rows = $("#mydatagrid").datagrid("getSelections");
		var bol = $("#mydatagrid").datagrid("validateRow", indexs);
		if (bol == false) {
			alert("请检查输入！");
			return;
		}
		jQuery.ajax({
			type : "POST",
			url : '${contextPath}/mx/datamgr/sql/definition/saveColumn?sqlDefId=${sqlDefId}',
			data : rows[0],
			dataType : 'json',
			async : false,
			error : function(data) {
				alert('服务器处理错误！');
			},
			success : function(data) {
				if (data != null && data.message != null) {
					alert(data.message);
				} else {
					alert('操作成功！');
				}
				var link = '${contextPath}/mx/datamgr/sql/definition/saveColumn?sqlDefId=${sqlDefId}';
				//jQuery('#mydatagrid').datagrid('reload');
				jQuery.get(url+'&randnum='+Math.floor(Math.random()*1000000),{qq:'xx'},function(data){
			         jQuery('#easyui_data_grid').datagrid('loadData', data);
		         },'json');
			}
		});
	}

	function reloadColumns(){
         location.href="${contextPath}/mx/datamgr/sql/definition/reloadColumns?sqlDefId=${sqlDefId}";
	}

</script>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<div style="margin: 0;"></div>
				<div data-options="region:'north',split:true,border:true"
					style="height: 40px">
					<div class="toolbar-backgroud">
						 &nbsp; &nbsp;<img src="${contextPath}/images/window.png">&nbsp; <span
							class="x_content_title">"${sqlDefinition.title}"字段列表</span> 
							<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-reload'"
	                           onclick="javascript:reloadColumns();">重载字段</a>  
					</div>
					<div data-options="region:'center',border:true"
						style="width: 980px; height: 345px">
						<table id="mydatagrid">
						</table>
					</div>
 
					<div style="margin: 0; height: 4px"></div>
					 
			</form>

		</div>

</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>