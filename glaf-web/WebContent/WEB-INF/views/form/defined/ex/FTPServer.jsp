<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("theme", "default");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<style>
.wdt {
	height: 30px;
}
.wdtw{
	width : 100px;
}
div.k-window-content {
    padding: 0;
}
#toolbar {
	border-width: 0 0 1px;
}
#widgets {
    margin: 0;
    padding: 10px;
    border-width: 0 0 1px 0;
}
</style>
<script type="text/javascript">

	//已选择的数据集
	var selectDatasource = [];
	var parent = window.opener || window.parent ;
	
	if('${param.objelementId}' != ''){
		var results = parent.document.getElementById('${param.objelementId}').value;
		if(results){
			var datas = JSON.parse(results);
			if(datas.length){
				$.merge(selectDatasource, datas);
			}
		}
	}
	
	
	//刷新 已选择数据集
	function rushSelectDatasourceGrid() {
		//刷新选择的数据集
		var grid = $("#selectDatasourceGrid").data("kendoGrid");
		grid.dataSource.read();
	}
	
	

	$(document).ready(function() {
		//初始化布局  选择数据集
		$("#dataSourceSplitter").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				collapsible : false,
				size : "245px"
			}, {
				collapsible : false,
			} ]
		});
		//数据集grid
		$("#datasourceGrid").kendoGrid({
			dataSource : {
				schema : {
					model : {
						id : "id",
						fields : {
							title : {
								type : 'string'
							},
							host : {
								type : 'string'
							},
							port : {
								type : 'string'
							},
							verify : {
								type : 'string'
							},
						}
					},
					data : "rows",
					total : "total"
				},
				transport : {
					read : {
						contentType : "application/json",
						url : '<%=request.getContextPath()%>/mx/sys/server/json?type=ftp&verify=Y',
						dataType : "json",
						type : 'POST'
					},
					parameterMap : function(options) {
						return JSON.stringify(options);
					},
				},
				pageSize : 10,
				serverPaging : true,
				serverFiltering :true
			},
			filterable: true,
			height : 242,
			sortable : true,
			pageable : {
				refresh : true,
				pageSizes : true,
				buttonCount : 5
			},
			columns : [ {
				field : 'title',
				title : '名称',
				width : 250
			}, {
				field : 'host',
				title : '主机',
				width : 120
			}, {
				field : 'port',
				title : '主机',
				width : 80
			},  {
				field : 'verify',
				title : '是否验证',
				width : 100,
				template : function(dataItem){
					 if(dataItem.verify == 'Y'){
							return "<font color='green'><b>通过</b></font>";
						} else {
				            return "<font color='red'><b>未通过</b></font>";
						}
				}
			}, /*   {
				field : 'createTime',
				title : '创建时间',
				width : 80,
				filterable: false 
			}, */ {
				command : [ {
					name : "选择",
					click : function(e) {
						if (selectDatasource.length > 0) {
							layer.alert('只能选择一个数据集！', 3);
							return;
						}   
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						data.name = data.title ;
						selectDatasource.push(data);
						rushSelectDatasourceGrid();
					}
				}]
			} ]
		});
		//已选择的数据集
		$("#selectDatasourceGrid").kendoGrid({
			dataSource : selectDatasource,
			
			sortable : true,
			columns : [ {
				field : 'title',
				title : '名称',
				width : 250
			}, {
				field : 'host',
				title : '主机',
				width : 120
			}, {
				field : 'port',
				title : '主机',
				width : 80
			},{
				field : 'verify',
				title : '是否验证',
				width : 100,
				template : function(dataItem){
					 if(dataItem.verify == 'Y'){
							return "<font color='green'><b>通过</b></font>";
						} else {
				            return "<font color='red'><b>未通过</b></font>";
						}
				}
			}, {
				command : [ {
					name : "删除",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						for(var i=0;i<selectDatasource.length;i++){
							var value = selectDatasource[i];
							if (value.datasetId == data.datasetId) {
								selectDatasource.splice(i, 1);
							}
						}
						rushSelectDatasourceGrid();
					}
				}]
			} ]
		});
		
		
		//保存
		$("#commitToolbar").kendoToolBar({
			resizable : true,
			items:[{ 
				    type: "button", text: "保存", icon: "k-icon k-i-tick" ,click:function(e){
					var tablenames = [];
					$.each(selectDatasource ,function(i,table){
						if(table.name && table.name!="null" && table.name!=""){
							tablenames.push(table.name||table.title);
						}
					});
					
				   	if('${param.objelementId}' != ''){
						parent.document.getElementById('${param.objelementId}').value = JSON.stringify(selectDatasource);
					}
					if('${param.nameElementId}' != ''){
						parent.document.getElementById('${param.nameElementId}').value = tablenames.join(',');
					}
					if(window.opener){
						window.close();
					}else{
						parent.layer.close(parent.layer.getFrameIndex());
					}
			   	  }
			   },{
				   type: "button", text: "取消", icon: "k-icon k-i-del" ,click:function(e){
					    if(window.opener){
							window.close();
						}else{
							parent.layer.close(parent.layer.getFrameIndex());
						}
				   }
			   }
			]
		});
	});
		
</script>
</head>
<body>
		<div>
			<div id="dataSourceSplitter" style="height: 410px;">
				<div>
					<div id="datasourceGrid"></div>
				</div>
				<div>
					<div id="selectDatasourceGrid"></div>
				</div>
			</div>
		</div>
		<div id="commitToolbar" style="height: 23px;text-align: right;"></div>
</body>
</html>