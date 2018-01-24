<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("theme", "default");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
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

	/**
	 * 根据数据集id 获取参数列表
	 * @param  {[type]}   datasetIds [description]
	 * @param  {Function} fn         [description]
	 * @return {[type]}              [description]
	 */
	function getDataSetParams(datasetIds, fn) {
		$.ajax({
			url: "${pageContext.request.contextPath}/rs/dataset/getDataSetParams",
			type: 'POST',
			dataType: 'JSON',
			async: false,
			data: {
				datasetIds: datasetIds
			},
			success: function(ret) {
				if (fn) {
					fn(ret);
				}
			}
		});
	}
	//检查表
	function searchTable(){
		var queryParams = {};
		queryParams.nameLike = $('#keyword').val();
		$('#table_grid').datagrid("load",queryParams);
	}

	function confirm() {
		var rows = $('#table_grid').datagrid('getData').rows;
		if (rows.length === 0) {
			alert('请选择一条记录');
			return;
		} else {
			var row =  {} ;
			row.dataSetId =	rows[0].dataSetId;
			row.name = rows[0].name;
			row.title =	rows[0].title;
			row.id = rows[0].id;
			row.databaseId = rows[0].databaseId;

/* 			if ('${param.nameId}' !== '') {
				parent.document.getElementById('${param.nameId}').value = row.name;
			}

			if ('${param.elementId}' !== '') {
				parent.document.getElementById('${param.elementId}').value = JSON.stringify(row);
			} */
				//执行父类提供方法
			var retFn = '${param.retFn}';
			if(retFn != '' ){
				parent[retFn](row.name,JSON.stringify(row));
			}
			
		}
	}
	$(function() {
		$('#table_grid').datagrid({
			url : '${pageContext.request.contextPath}/mx/dataset/json',
			queryParams : '',
			height : 365,
			toolbar : "#table_grid_tb",
			rownumbers : true,
			striped : true,
			pagination : true,
			singleSelect : true,
			pageSize : 20,
			columns : [ [ {
				field : 'datasetId',
				title : 'datasetId',
				hidden : true
			}, {
				field : 'name',
				title : '名称',
				width : 150
			}, {
				field : 'title',
				title : '标题',
				width : 150
			}, {
				field : 'databaseId',
				title : '标段',
				width : 150,
				formatter : function(value, row, index) {
					var str = "默认";
					$.each(databaseControl, function(i, d) {
						if (value == d.id) {
							str = d.text;
						}
					})
					return str;
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 100
			} ] ],
			onDblClickRow : function(rowIndex, rowData) {
				var row =  {} ;
				row.dataSetId =	rowData.dataSetId;
				row.name = rowData.name;
				row.title =	rowData.title;
				row.id = rowData.id;
				row.databaseId = rowData.databaseId;
				
			/* 	if ('${param.nameId}' !== '') {
					parent.document.getElementById('${param.nameId}').value = row.name;
				}

				if ('${param.elementId}' !== '') {
					parent.document.getElementById('${param.elementId}').value = JSON.stringify(row);
					parent.layer.close(parent.layer.getFrameIndex());
				} */
				
				var retFn = '${param.retFn}';
				var params = null;
				getDataSetParams(row.dataSetId,function(ret){
					if(ret && ret.length){
						params = [];
						$.each(ret,function(i,v){
							params.push({
								name:v.name,
								param:v.param
							});
						})
					}
				});
				if(retFn != '' ){
					parent[retFn](row.name,JSON.stringify(row),params?JSON.stringify(params):params);
				}
			}
		});
	})
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="height: 500px">
		<div data-options="region:'center'">
			<table id="table_grid"></table>
			<div id="table_grid_tb">
				<label>数据集名称：</label> <input type="text" id="keyword" /> 
				<a href="javascript:searchTable();" id="keywordBtn" class="easyui-linkbutton" iconCls="icon-search">检索</a>
				<!-- 
				<a href="javascript:confirm();" id="keywordBtn" class="easyui-linkbutton" iconCls="icon-ok">选择</a>
				 -->
			</div>
		</div>
	</div>
</body>
</html>