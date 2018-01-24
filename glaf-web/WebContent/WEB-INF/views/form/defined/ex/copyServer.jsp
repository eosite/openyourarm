<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setAttribute("theme", "default");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	var contextPath = '${contextPath}';
</script>

<link rel="stylesheet" href="/glaf/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css">
<link rel="stylesheet" href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/glaf/scripts/designer/bootstrap_extend.css">
<link rel="stylesheet"
	href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css">
<script type="text/javascript" src="/glaf/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<!--treelist-->
<link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/treelist/css/treelist.css">
<script src="/glaf/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js" type="text/javascript"></script>
<script type="text/javascript" src="/glaf/scripts/jquery.mtResizable.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js" type="text/javascript"></script>
<!-- ztree -->
<link rel="stylesheet" type="text/css" href="/glaf/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="/glaf/scripts/ztree/js/jquery.ztree.all.min.js"></script>

<!-- slimscroll -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>

<!-- 表头设置的 -->
<link rel="stylesheet" type="text/css" href="/glaf/scripts/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/glaf/themes/default/styles.css">
<link rel="stylesheet" type="text/css" href="/glaf/css/icons.css">

<link rel="stylesheet" type="text/css" href="/glaf/scripts/layer/skin/layer.css">

<!-- select2 -->
<link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/glaf/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>

<!-- select2 -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<!-- metroselect -->
<script src="/glaf/scripts/plugins/bootstrap/select/jquery.metroselect.extends.js"></script>

<!-- 一些整合好的方法，如查询表格字段信息 -->
<script type="text/javascript" src="/glaf/webfile/js/builder.js"></script>
<script type="text/javascript" src="/glaf/webfile/js/builder.jsplumb.js"></script>



<title></title>
<style type="text/css">
	body{
		font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	}
	.panle_content{
		padding:10px 10px;
	}
	.panle_content .tab-content{
		height: 531.8px;
	}
	.outerPanel{
		border: 1px solid rgb(187, 220, 235);
		height:400px;
		padding: 10px 10px;
		background-color: #fff;
	}
	.datasourceTreeOuterPanel{
		padding:0 0;
	}
	#tableInformation{
		width:100%;
	}
	.portlet>.portlet-title{
		min-height: 20px;
	}
	.portlet>.portlet-title>.caption{
		padding: 8px 0 9px;
		font-size: 14px;
		font-weight: bold;
	}
	.portlet>.portlet-title>.tools {
	    padding: 9px 0 8px;
	}
	.portlet{
		margin-bottom: 0px;
	}
	.portlet.box>.portlet-body{
		padding: 0 0;
	}
	.tab-pane{
		width: 876px;
		height: 516px;
	}
	#select_biaoduan_div , .selectPanel{
		display: inline-block;
	}
	#select_biaoduan_div span.select2-container{
		width:150px !important;
	}
	.selectPanel span.select2-container{
		width:150px !important;
	};
</style>

</head>
<body>
<header>
</header>
<div class="panle_content">
	<div class="tabbable-custom ">
		<ul class="nav nav-tabs ">
			<li class="active">
				<a href="#tab_5_1" data-toggle="tab"> 物理表选择 </a>
			</li>
			<li >
				<a href="#tab_5_2" id="tab_5_2_li" data-toggle="tab"> 表头设置 </a>
			</li>
			<li style="display:none;">
				<a href="#tab_5_3" id="tab_5_3_li" data-toggle="tab"> 数据字段 </a>
			</li>
		</ul>
		<div style="position: absolute;right: 15px;top: 10px;">
			<a href="javascript:;" id="sureButton" class="btn btn-circle purple"> 选择
                <i class="fa fa-file-o"></i>
            </a>
		</div>
		<div class="tab-content">
			<div class="tab-pane active  row" id="tab_5_1">
				<div style="overflow:hidden;">
					<div class="outerPanel col-xs-4">
						<div id="datasourceTreePanel" style="width:269.984px;height:378px;">
							<div style="text-align:center;padding:0px 10px 10px 10px;">
								<label>标段:</label>
								<div id="select_biaoduan_div" data-value-primitive="true" enabled="true" data-value-field="code" data-text-field="text" selectedindex="1">
									<select id="select_biaoduan" data-placeholder="---请选择---">
									</select>
								</div>
							</div>
							<div style="text-align:center;padding:0px 10px 10px 10px;border-bottom:1px solid black">
								<input id="searchText" type="text" class="form-control input-sm" style="width: 120px;     display: inline-block;">
								<a href="javascript:;" id="searchBt" class="btn btn-sm blue" style="margin-top:-3px;"> 搜索
	                                <i class="fa fa-search"></i>
	                            </a>
							</div>
							<div id="datasourceTree" class="ztree"></div>
						</div>
					</div>
					<div class="outerPanel col-xs-8">
						<div id="tableInformation" class="form-inline">
							<div class="form-group">
								<label for="name" class="control-label">表名(中文)
								</label>
								<input id="tableCnName" type="text" class="form-control input-sm" readonly="true">
							</div>
							<div class="form-group">
								<label for="name" class="control-label">表名
								</label>
								<input id="tableEnName" type="text" class="form-control input-sm" readonly="true">
							</div>
							<div class="form-group">
								<a href="javascript:;" id="showTable" class="btn btn-sm blue"> 预览
	                                <i class="fa fa-search"></i>
	                            </a>
	                            <a href="javascript:;" id="selectButton" class="btn btn-sm green"> 选择
	                                <i class="fa fa-check"></i>
	                            </a>
							</div>
						</div>
						<div id="tableInformationGrid" style="height: 339px;margin-top:10px;">
								
						</div>
					</div>
				</div>
				<div class="portlet box blue">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="fa fa-gift"></i>已选物理表 </div>
                        <div class="tools">
                            <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div id="selectDatasourceGrid" style="height: 77px;">
						</div>
                    </div>
                </div>
			</div>
			<div class="tab-pane " id="tab_5_2">
				<div data-options="region:'north'" style="height:510px">
					<table id="tg"></table>
				</div>
				<div data-options="region:'center'">
					<div id="preview"></div>
				</div>
				<div id="win">
					<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'center',border:false">
							<table id="pg"></table>
						</div>
						<div data-options="region:'south',border:false" style="height:30px" align="right">
							<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="javascript:saveColumnBind();">保存</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#win').window('close');">取消</a>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane " id="tab_5_3">
				<div style="text-align:center;padding:0px 10px 10px 10px;">
					<label>分级编号:</label>
					<div class="selectPanel" id="idKey_div" data-value-primitive="true" enabled="true" data-value-field="code" data-text-field="text" selectedindex="1">
						<select id="idKey" data-placeholder="---请选择---">
						</select>
					</div>
				</div>
				<div style="text-align:center;padding:0px 10px 10px 10px;">
					<label>节点编号:</label>
					<div class="selectPanel" id="indexKey_div" data-value-primitive="true" enabled="true" data-value-field="code" data-text-field="text" selectedindex="1">
						<select id="indexKey" data-placeholder="---请选择---">
						</select>
					</div>
				</div>
				<div style="text-align:center;padding:0px 10px 10px 10px;">
					<label>父类编号:</label>
					<div class="selectPanel" id="pIdKey_div" data-value-primitive="true" enabled="true" data-value-field="code" data-text-field="text" selectedindex="1">
						<select id="pIdKey" data-placeholder="---请选择---">
						</select>
					</div>
				</div>
				<div class="form-group" style="text-align:center;padding:0px 10px 10px 10px;">
					<a href="javascript:;" id="columnDataSure" class="btn btn-sm blue"> 确认
                        <i class="fa fa-search"></i>
                    </a>
				</div>
			</div>
		</div>
	</div>
</div>
<footer>
</footer>
</body>
</html>
<script type="text/javascript">
	//该属性的名称
    var controlName = 'tableSource';
    //数据返回位置
    var nameElementId = '${param.nameElementId}';	//显示域ID
    var objelementId = '${param.objelementId}';		//隐藏域ID
    var dataRole = '${param.dataRole}';	//dataRole
    //获取隐藏域信息，即该属性的规则
    var tableSourceData_ = JSON.parse(window.parent.$('#' + objelementId).val() || '[]')[0];
    //具体如下
    // tableSourceData_ = {
    // 	table : {},	//表格信息
    // 	headerColumn : {},	//表头信息
    // 	headerText : {},	//表头显示信息
    // 	name : controlName,	//名称
    // 	columnData : [{}],	//数据字段信息
    // }

	//当前页面的全局变量
	var thispage_param = {
		currentTable : null,	//当前表
		selectedtable : null,	//选中的物理表
		headerColumn : null,	//表头模板数据信息
		headerText : null,	//表头模板显示信息
		zTree : null,	//ztree
		columnData : null,	//数据字段信息
	};	
	//重新加载zTree事件
	var utableTreeUrl = contextPath+ "/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?1=1";
	var cellUrl = contextPath + "/rs/isdp/treeDot/getTreeDotByParentIdInit?1=1";
	var datasourceTree_setting = {
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
			beforeClick: ztreeBeforeClick,
		},
		view: {
			selectedMulti: false,
			fontCss: function(treeId, treeNode) {
				return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
			}
		}
	};
	var tableInformationGrid_setting = {
		columns: [{
			'field': 'fname',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '中文名称',
		},{
			'field': 'dname',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '英文名称',
			'template': function(data){
				return data.columnName || data.dname;
			}
		},{
			'field': 'dtype',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '字段类型',
		},{
			'field': 'strlen',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '字段长度',
		},
		{
			'field': 'control',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '操作',
			'template': function(data){
				return "<select class='control-ex' style='width:80px' selected='selected'><option value=''>请选择</option>"
				+ "<option value='index_id'>index_id</option>"
				+ "<option value='treeid'>treeid</option>"
				+ "<option value='parent_id'>parent_id</option>"
				+ "</select>";
			}
		}
		],
		childrenColumn: 'columns',
		editable: true,
		selectable: "multiple",
		sortable: {
			mode: "single"
		},
		autoHeader: true, // 自动合并表头
		events: {
		},
		tableCls: 'table table-striped table-bordered table-hover table-plus tableMsg',
		selectedCls: 'table-selected',
		pagination: {
			paging: false,
		}
	};
	var selectDatasourceGrid_setting = {
		columns: [{
			'field': 'name',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '中文名称',
		},{
			'field': 'tableName',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '英文名称',
		},{
			'field': '',
			'attributes': {
				'style': 'text-align:left;'
			},
			'title': '操作',
			'template' : function(data){
				return '<a href="javascript:;" onclick="deleteSelectedTable()" class="btn btn-sm purple"><i class="fa fa-times"></i>删除</a>';
			}
		}],
		childrenColumn: 'columns',
		editable: true,
		selectable: "single",
		sortable: {
			mode: "single"
		},
		autoHeader: true, // 自动合并表头
		events: {
		},
		tableCls: 'table table-striped table-bordered table-hover table-plus',
		selectedCls: 'table-selected',
		pagination: {
			paging: false,
		}
	};
	var biaoduan_select_setting = {
		ajax: {
			read: {
				url: contextPath + "/rs/isdp/global/databaseJson",
				type: 'POST',
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
				data: {},
				async: true
			},
			parameterMap: function(params) {
				return params;
			}
		},
		keys: {}
	};
	$(function(){
		if(dataRole == 'treelistbt'){
			$("#tab_5_3_li").parent().show();
		}
		//标段下拉框赋值
		$("#select_biaoduan_div").metroselect(biaoduan_select_setting);
		//标段下拉框切换
		$("#select_biaoduan").change(function(e){
			//window.getTableType()为表格类型，1为普通表，暂时支持普通表，systemName为标段
			// initZTree(window.getTableType(), systemName);
			initZTree(1, this.value);
		})

		//赋值选中表格信息
		if(tableSourceData_){
			thispage_param.selectedtable = tableSourceData_.table;
			thispage_param.headerColumn = tableSourceData_.headerColumn;
			thispage_param.headerText = tableSourceData_.headerText;
			thispage_param.columnData = tableSourceData_.columnData;
		}
		var datas = [];
		if(thispage_param.selectedtable){
			datas.push(thispage_param.selectedtable.table)
		}
		loadSelectDataSourceGrid(datas);
		var zTreeObj = $("#datasourceTree"), data = null;
		var zTree = $.fn.zTree.init(zTreeObj, datasourceTree_setting, data); 
		thispage_param.zTree = zTree;
		$("#datasourceTreePanel").slimScroll({
			height: $("#datasourceTreePanel").height(),
			width: $("#datasourceTreePanel").width(),
			opacity: 0.4,
			alwaysVisible: false,
			color: '#69d3dc',
		});

		$("#searchBt").click(function(event){
			var $searchText = $("#searchText")
			var value = $searchText.val();
			//flase时折叠所有节点，返回false表示折叠，true表示展开，null表示没有父节点
			var f = zTree.expandAll(false);
			if (!f) {
				var ns = $searchText.data('nodes');
				if (ns && ns[0]) {
					updateHighlight(ns, false, zTree);
				}
				if (value) {
					//获取指定名字的节点
					var nodes = zTree.getNodesByParamFuzzy("indexName", value, null);
					if (nodes && nodes[0]) {
						$.each(nodes, function(i, node) {
							if (!node.isParent) {
								node = node.getParentNode();
							}
							zTree.expandNode(node, true, true, false); //展开
						});
						updateHighlight(nodes, true, zTree); //高亮
						$searchText.data('nodes', nodes);
					}
				}
			}
			// var nodes = zTree.getNodesByParam("indexName", value, null);
			// zTree.showNodes(nodes);
		});

		//选择表格
		$("#selectButton").click(function(event){
			if(thispage_param.currentTable){
				thispage_param.selectedtable = thispage_param.currentTable;
				var datas = [];
				datas.push(thispage_param.currentTable.table);
        		loadSelectDataSourceGrid(datas);
			}
		});

		$("#tab_5_2_li").on('shown.bs.tab',function(event){
			$("#tab_5_2").addClass("active");
			initEditTableHeader();
		});

		$("#tab_5_3_li").on('shown.bs.tab',function(event){
			initDataColumnPanel();
			$("#tab_5_3").addClass("active");
		});

		//确认并保存数据
		$("#sureButton").click(function(event){
			/* var retText = "";
			if(thispage_param.selectedtable){
				retText = thispage_param.selectedtable.table.name;
				thispage_param.selectedtable.systemName = getSystemName();	//标段

				var retData = {
					table : thispage_param.selectedtable || "",
					headerColumn : thispage_param.headerColumn || "",
					headerText : thispage_param.headerText || "",
					name : retText,	
					columnData : thispage_param.columnData,
				}
				var retDatas = [];
				retDatas.push(retData);
				
	    		window.parent.$("#" + objelementId).val(JSON.stringify(retDatas));
	    		window.parent.$("#" + nameElementId).val(JSON.stringify(retText));
			}else{
				window.parent.$("#" + objelementId).val("");
				window.parent.$("#" + nameElementId).val("");
			} */
			$.ajax({
				url : contextPath + '/mx/form/data/gridDataServer',
				async : true,
	            data : {
	            	"databaseId" : $("#select_biaoduan option:selected").val(),
	            	"tableName" : $("#tableEnName").val()
	            },
	            type : 'post',
	            success : function(ret){
	            	//var $(".tableMsg>tbody>tr>td:field=control")
	            	var trMsg = $(".tableMsg>tbody>tr"),
	            	    dataset = [],
	            	    param = {};
	            	    itemcs = [];
	            	$.each(trMsg,function(ms,tr){
	            		if($(tr).find("td[field=control] option:selected").val() != ""){
	            			
	            			param[$(tr).find("td[field=control] option:selected").val()] = $(tr).find("td[field=fname]")[0].innerText;
	            			
	            		}
	            	});
	            	$.each(eval("("+ret+")"),function(i,data){
	            		var param = {
	            				id: data.index_id,
	        					name: data.name,
	        					text: data.name	
	            		}
	            		dataset.push(param);
	            	});
	            	var config = {
	        				text : "系统服务参数",
	        				code : "serverParamter",
	        				tableName : $("#tableEnName").val(),
	        				databaseId : $("#select_biaoduan option:selected").val(),
	        				param : dataset,
	        				item : param
	            	}
	            	callback(config);
	            }
			});
    		//parent.layer.close(parent.layer.getFrameIndex());
		});
		
		// initEditTableHeader();
	});
	
	var retFn = "OutExtItem";
    function callback(dataItem){
    	parent[retFn](dataItem);
        closeWin();
    }
    function closeWin(){
    	
    	 parent.layer.close(parent.layer.getFrameIndex());
    }
    
	//初始化数据字段面板，用于选择匹配字段
	function initDataColumnPanel(){
		//恢复数据
		var columnData = [];
		if(thispage_param.selectedtable !== null ){
			tableNameCN = thispage_param.selectedtable.table.tableNameCN;
			$.each(thispage_param.selectedtable.columns,function(index,field){
				var obj = {};
				obj.id = (field.columnName || field.dname).toLowerCase();
				obj.text = field.fname;
				// obj.columnNameTitle=tableNameCN+"."+field.fname;
				columnData.push(obj);
			})
		}

		var options = {
			data : columnData,
		}
		options.width = "auto" ;
		$("#idKey_div").find('select').select2(options).prop("disabled", false);
		$("#indexKey_div").find('select').select2(options).prop("disabled", false);
		$("#pIdKey_div").find('select').select2(options).prop("disabled", false);

		$("#columnDataSure").click(function(){
			var idKeyData = $('#idKey').select2('data')[0];
			var indexKeyData = $('#indexKey').select2('data')[0];
			var pIdKeyData = $('#pIdKey').select2('data')[0];

			var columnData = [{
			    "name": idKeyData.text,
			    "columnName": idKeyData.id,
			    "columnType": "idKey"
			}, {
			    "name": indexKeyData.text,
			    "columnName": indexKeyData.id,
			    "columnType": "indexKey"
			}, {
			    "name": pIdKeyData.text,
			    "columnName": pIdKeyData.id,
			    "columnType": "pIdKey"
			}]
			thispage_param.columnData = columnData;
		})

		
		//初始化
		if(thispage_param.columnData !== null){
			$.each(thispage_param.columnData,function(i,item){
				$("#" + item.columnType).val(item.columnName).trigger('change');
			})
		}
	}

	function loadSelectDataSourceGrid(datas){
		var $selectDatasourceGrid = $("#selectDatasourceGrid");
		$selectDatasourceGrid.removeData();
		$selectDatasourceGrid.empty();
		selectDatasourceGrid_setting.datas = datas;
		$selectDatasourceGrid.grid(selectDatasourceGrid_setting);
	}

	/**
	 * 初始化ztree
	 * @param  {[type]} tableType 表格类型，如普通表，cell表等
	 * @param  {[type]} ds        标段
	 * @return {[type]}           [description]
	 */
	function initZTree(tableType, ds){
		var zTreeObj = $("#datasourceTree"), data = null;
		if(tableType==1){
			datasourceTree_setting.async.url = utableTreeUrl+"&type=1&tableType=2&systemName="+ds;
	    	//$.fn.zTree.init(zTreeObj, setting); 
		}else if(tableType==2)	{
			datasourceTree_setting.async.url = cellUrl+'&systemName='+ds;
	    	//$.fn.zTree.init(zTreeObj, setting); 
		}else if(tableType==3){
			datasourceTree_setting.async.url = utableTreeUrl+"&type=1&tableType=12&systemName="+ds;
			//$.fn.zTree.init(zTreeObj, setting); 
		}else if(tableType==4){
			datasourceTree_setting.async.url = utableTreeUrl+"&type=4&tableType=12&systemName="+ds;
			data = [{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|',isParent:true}];
			//$.fn.zTree.init(zTreeObj, setting, [{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|',isParent:true}]); 
		}else if(tableType==5){
			//$.fn.zTree.init(zTreeObj, setting, [{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}]); 
			data = [{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}];
		}
		thispage_param.zTree = $.fn.zTree.init(zTreeObj, datasourceTree_setting, data); 
	}


	//Ztree点击事件
	function ztreeBeforeClick(treeId, treeNode, clickFlag) {
		var data = {};
		if (!treeNode.isParent){
			var tableObj = {
				tableId : treeNode.tableId,
				tableName : treeNode.tableName,
				R : treeNode.R
			}
			//getFieldByTableId是builder里面的方法，用于获取字段信息
			tableObj.systemName = getSystemName();
			var tcs = getFieldByTableId(tableObj).result;
			data = tcs || {};
		}
		tableInformationGrid_setting.datas = data.rows;

		$("#tableCnName").val(treeNode.tableNameCN);
		$("#tableEnName").val(treeNode.tableName);
		var $tableInformationGrid = $("#tableInformationGrid");
        $tableInformationGrid.removeData();
        $tableInformationGrid.empty();
		$tableInformationGrid.grid(tableInformationGrid_setting);

		var currentTable = {
			table : treeNode,
			columns : data.rows
		}

		thispage_param.currentTable = currentTable;
	}

	//删除已选内容
	function deleteSelectedTable(){
		$("#selectDatasourceGrid").grid("removeRow",new Number(0));
		thispage_param.selectedtable = null;

	}

	/**
	 * 获取表格类型
	 * @return {[type]} [description]
	 */
	function getTableType(){
		var $type = $("#create_table_type").data("kendoDropDownList");
		if($type){
			return $type.value();
		}
		return 1;
	}

	/**
	 * 获取标段
	 * @return {[type]} [description]
	 */
	function getSystemName(){
		var SystemName = !!(k = $("#select_biaoduan").val()) ? $("#select_biaoduan").val() : "";
		return SystemName || "default";
	}

	//ztree节点高亮
	function updateHighlight(nodes, flag, zTree) {
		$.each(nodes, function(i, node) {
			node.highlight = flag || false;
			zTree.updateNode(node);
		});
	}

	function iterator(nodes){
		if(nodes && nodes.length){
			$.each(nodes, function(i,v){
				if(v.tableName){
					v.icon = "/glaf/images/table_tab.png";
				}
				if(v.children && v.children.length){
					iterator(v.children);
				}
			});
		}
	}
</script>
<script type="text/javascript">
//参数1：返回的结果文本ID
//参数2：中文表名文本框ID
//参数3：中文字段名文本框ID
function selectTableAndFields(resultElementId,tablenameElementId,elementId,pageId){
	var flag = 0 ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = 1;  //ztree
	}else if(elementId.length > 30){
		flag = 2; //页面
	}
	var link = '/glaf/mx/form/defined/table/main2?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择表/字段",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByCharts(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '/glaf/mx/form/defined/table/charts_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByJPlayer(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '${pageContext.request.contextPath}/mx/form/defined/table/jplayer_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByJSGIS(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '/glaf/mx/form/defined/table/jsgis_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}


//参数1：返回的表头中文文本框ID
//参数2：返回的表头JSON字符串对象文本框ID
//参数3：选表后返回的结果文本框ID
function modifyTableHander(elementId,objElementId,datasId){
	var link = "/glaf/mx/form/defined/table/table_hander?1=1";
	link += "&elementId="+elementId+"&objElementId="+objElementId+"&datasId="+datasId;
	//link += "&tableHanderJsonArray="+tableHanderJsonArray;
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "编辑表头",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['800px', '600px'],
        iframe: {src: link}
	});
}

//选择树
function selectTreeField(databaseCode,fieldIdElementId,fieldNameElementId,fieldObjElementId,tableJson,fieldJson,endFunction,elementId,view){
	var link = "/glaf/mx/isdp/table?method=selectTreeField";
	if(view){
		link += '&view='+view;
	}
	/* 
	if(databaseCode == ""){
		layer.alert("请先选择数据源",3);
		return;
	}
	 */
	var flag = false ;
	if(elementId.indexOf("treelist")!= (-1)){
		flag = true;
		link += "&flag="+flag;
	}
	
	
	link += "&databaseCode="+databaseCode;
	if(tableJson == ""){
		layer.alert("请选择数据集",3);
		return;
	}
	//link += "&tableJson="+encodeURIComponent($.base64.encode(tableJson));
	link += "&fieldIdElementId="+fieldIdElementId+"&fieldNameElementId="+fieldNameElementId+"&fieldObjElementId="+fieldObjElementId;
	
	tableJsonArray = tableJson;
	if(fieldJson != ""){
		//link += "&fieldJson="+encodeURIComponent($.base64.encode(fieldJson));
		fieldJsonArray = fieldJson;
	}
	
	//layer彻底关闭后的回调函数
	 if(endFunction==undefined){
		 endFunction = function(index){
			 fieldJsonArray = [];
       }
	 }
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择字段",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['600px', '600px'],
        iframe: {src: link},
        end:endFunction
	});
}
</script>
<link rel="stylesheet" type="text/css" href="/glaf/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="/glaf/scripts/layer/skin/layer.ext.css" >
<script type="text/javascript" src="/glaf/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="/glaf/scripts/easyui/treegrid-dnd.js"></script>
<script type="text/javascript">
	var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;
	
	//数据列统计数据源
	//mx/form/defined/getDictByCode?code=?
	var dataColumnStat = [] ;
	$.ajax({
		url : '/glaf/mx/form/defined/getDictByCode',
		data : {code : 'function'},
		type : 'post',
		dataType : 'json',
		async : false,
		success:function(data){ 
			$.each(data,function(i,d){
				var e = {} ;
				e.id = d.code ;
				e.text = d.name
				dataColumnStat.push(e);
			});
		}
	});
	

	var treeCount = 0,columnData = [],editingId;

	function initEditTableHeader(){
		
		$('#win').window({   //关联字段弹窗
			title:"关联字段",
			collapsible:false,
			minimizable:false,
			maximizable:false,
			closed:true,
		    width:300,   
		    height:450,   
		    modal:true  
		}); 
		
		//恢复数据
		if(thispage_param.selectedtable !== null ){
			columnData = [];
			tableNameCN = thispage_param.selectedtable.table.tableNameCN;
			$.each(thispage_param.selectedtable.columns,function(index,field){
				var obj = {};
				obj.columnName= (field.columnName || field.dname).toLowerCase();
				obj.columnNameCN=field.fname;
				// obj.columnId=field.listId;
				obj.columnNameTitle=tableNameCN+"."+field.fname;
				columnData.push(obj);
			})
		}
		
		//表头编辑grid
		$("#tg").treegrid({
			height:398,
			idField:'id',   
		    treeField:'name',
		    lines:true,
		    fitColumns: false,
		    singleSelect : false,
		    toolbar:[
		    	{
		    		text:"确定",
		    		handler:function(){
		    			var nodes = $("#tg").treegrid("getData");
	    				deleteEditorProperty(nodes);//删除editor属性
	    				var headers = getAllHeaderNames(nodes);
	    				thispage_param.headerColumn = nodes;
						thispage_param.headerText = headers;
		    		}
		    	},"-",{
					text:"同级增加",
					handler:function(){
						var node = $("#tg").treegrid("getSelected");
						var parent;
						if(node){
							parent = $("#tg").treegrid("getParent",node.id);
						}
						$("#tg").treegrid("append",{
							parent: parent?parent.id:null,
							data:[{id:++treeCount,name:"新表头_"+treeCount,isHidden:""}]
						});
					}
				},
				{
					text:"下级增加",
					handler:function(){
						var node = $("#tg").treegrid("getSelected");
						if(!node){
							alert("请先选择一个表头后再增加");
							return;
						}
						
						$("#tg").treegrid("append",{
							parent: node.id,
							data:[{id:++treeCount,name:"新表头_"+treeCount,isHidden:""}]
						});
					}
				},{
					text:"删除",
					handler: function(){
						var nodes = $("#tg").treegrid("getSelections");
						if(!nodes.length){
							alert("请先选择一个表头后再删除");
							return;
						}
						for(var i=(nodes.length-1);i>=0;i--){
							var node = nodes[i];							
							if(node){
								$('#tg').treegrid('remove', node.id);
							}
						}
					}
				},{
					text:"自动生成",
					handler: function(){
						//data:[{id:++treeCount,name:"新表头_"+treeCount,isHidden:""}]
						if(!columnData || columnData.length===0){
							alert("请先定义字段后再通过此功能自动生成表头");
							return;
						}
						var data = [];
						for(var i=0,len=columnData.length;i<len;i++){
							var obj = {};
							obj.id = ++treeCount;
							obj.name = columnData[i].columnNameCN,
							obj.isHidden = "";
							obj.columnName = columnData[i].columnName;
							obj.columnNameCN = columnData[i].columnNameCN,
							obj.columnNameTitle = columnData[i].columnNameTitle,
							obj.columnId = columnData[i].columnId,//数据集列主键
							obj.value = columnData[i].columnName;
							data.push(obj);
						}
						$("#tg").treegrid("append",{
							parent: null,
							data:data
						});
					}
				},"-",{
					text:"编辑",
					handler: function(){
						editRow();
					}
				},{
					text:"保存",
					handler: function(){
						saveRow();
					}
				},{
					text:"取消编辑",
					handler: function(){
						$('#tg').treegrid('cancelEdit', editingId);
						editingId = undefined;
					}
				},"-",{
					text:"关联字段",
					handler:function(){
						var nodes = $("#tg").treegrid("getData");
						var allChildren = getAllChildren(nodes);
						$('#win').window('open');
						
						$("#pg").propertygrid({
							height:380,
							data:allChildren,
							columns:[[
					        	{
					        		field:"id",
					        		title:"id",
					        		hidden:true
					        	},
								{
									field:"name",
									title:"表头名称",
									width:150
								},{
									field:"value",
									title:"字段名称",
									width:250,
									formatter:function(value,rowData){
										for(var i=0,len=columnData.length;i<len;i++){
											if(value===columnData[i].columnName){
												rowData.columnNameCN = columnData[i].columnNameCN;
												rowData.columnId = columnData[i].columnId;
												return columnData[i].columnNameTitle;
											}
										}
									}
								}
							]],
							onUncheck: function(rowIndex,rowData){
								var editor = $("#pg").propertygrid("getEditor",{index:rowIndex,field:"value"});
								var text = $(editor.target).combobox("getText");
								alert(text);
							}
						});
					}
				},"-",{
					text:"预览效果",
					handler:function(){
						var nodes = $("#tg").treegrid("getData");
						createTable(nodes);
					}
				}
			],
		    columns:[[
				{	
					checkbox : true,
					field:'id_'
				},
                {
                	field:'name',
                	title:'表头名称',
                	width:150,
                	editor:{
                		type:'text'
                	}
                },{
                	field:'link',
                	title:'表头样式',
                	width:150,
                	editor:{
                		type:'text'
                	}
                },{
                	field:'columnName',
                	title:'关联字段',
                	width:200,
                	formatter: function(value,rowData){
                		return rowData.columnNameTitle;
                	}
                },{
                	field:'dataColunmStat',
                	title:'数据列统计',
                	width:100,
                	align:'center',
                	editor:{
                		type:'combotree',
                		options:{
                			multiple:true,
                			data:dataColumnStat
                		}
                	},formatter:function(value,rowData){
                		var v = value ;
                		$.each(dataColumnStat,function(i,d){
                			if(v!=null && v!= ""){
	                			v = v.replace(d.id,d.text);
                			}
                		});
                		return v ;
					}
                },{
                	field:'isHidden',
                	title:'是否隐藏',
                	width:80,
                	align:'center',
                	editor:{
                		type:'checkbox',
                		options:{
							on:"√",
							off:""
                		}
                	}
                },{
                	field:'isLock',
                	title:'是否冻结',
                	width:80,
                	align:'center',
                	editor:{
                		type:'checkbox',
                		options:{
							on:"√",
							off:""
                		}
                	}
                }
            ]],
            onLoadSuccess: function(row){
            	$(this).treegrid('enableDnd',row?row.id:null);
            },
            onBeforeEdit: function(row){
            	$(this).treegrid('disableDnd');
            },
            onAfterEdit: function(row){
            	$(this).treegrid('enableDnd');
            },
            onCancelEdit: function(row){
            	$(this).treegrid('enableDnd');
            },
            onDblClickRow: function(row){
            	editRow(row.id);
            }
		});
		
		var data = [];
		$("#tg").treegrid("loadData",data);

		

		var data = thispage_param.headerColumn;
		if(data && data!=""){
			var _data = data;
			treeCount = getMaxTreeId(_data);
			$("#tg").treegrid("loadData",data);
		}
	};
	
	function getAllHeaderNames(nodes){
		var names = [];
		for(var i=0,len=nodes.length;i<len;i++){
			if(nodes[i].children && nodes[i].children.length>0){
				var tnames = getAllHeaderNames(nodes[i].children);
				for(var j=0;j<tnames.length;j++){
					names.push(tnames[j]);
				}
			}else{
				names.push(nodes[i].name);
			}
		}
		return names;
	}
	
	//保存事件
	function saveRow(){
		if (editingId != undefined){
			var t = $('#tg');
			var node = t.treegrid("getSelected");
			t.treegrid('endEdit', editingId);
			editingId = undefined;
		}
	}
	
	//html表达式
	// html编辑器获取变量方法
	function getVarFn(){
		//获取字典类型FieldTypeControl
		//columnData
		/* var expressionData = [] ;
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
		return expressionData ; */
		return null;
	}
	// html编辑器获取参数方法
	function getParamFn(){
		//这里传递页面之间 或者联动的参数
	}
	// html编辑器回调函数
	function retHtmlFn(data){
		var row = $('#tg').treegrid('getSelected');
		
		row.link = data.htmlVal;
		row.htmldata = JSON.stringify(data),
		$('#tg').treegrid('refresh',editingId);
		$("#tg").treegrid('endEdit', editingId);
		/* if(data){
			$('#linkImgGrid').datagrid('endEdit',gindex);
			$('#linkImgGrid').datagrid('updateRow',{index:gindex,row:{htmldata:JSON.stringify(data),
				htmlExpression:data.htmlVal}});
		} */
	}
	// html编辑器回显内容 
	function initHTMLFn(){
		//alert(1);
		var rows = $('#tg').treegrid('getData');
		if(rows){
			var row = rows[editingId-1];
			if(row){
				//var obj = {};
				//obj.varVal = row.htmlMap;
				//obj.htmlVal = row.htmlExpression ;
				return JSON.parse(row.htmldata);
			}
		}
	}
	//html 编辑器需要的字段
	function getHtmlFn(){
		//return columnData ;
	}
	
	//编辑事件
	function editRow(rowId){
		if (editingId != undefined){
			saveRow();
		}
		var row = $('#tg').treegrid('getSelected');
		if (row){
			editingId = row.id
			$('#tg').treegrid('beginEdit', editingId);
			var editor = $("#tg").treegrid("getEditor",{index:row.id,field:"link"});
			//链接文本框事件
			editor.target.bind("dblclick",function(){
				var htmlUrl = "/glaf/mx/html/editor/view?retFn=retHtmlFn&"+
					"getFieldFn=getHtmlFn&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
				window.open(htmlUrl);
			});
		}
	}
	
	//删除editor属性,editor属性为配置时需要，在配置时自动添加，不需要返回结果
	function deleteEditorProperty(nodes){
		for(var i=0,len=nodes.length;i<len;i++){
			if(nodes[i].children && nodes[i].children.length>0){
				deleteEditorProperty(nodes[i].children);
			}else{
				delete nodes[i].editor;
			}
		}
	}
	
	//保存字段与表头的绑定
	function saveColumnBind(){
		var data = $("#pg").propertygrid("getData").rows;
		for(var i=0,len=data.length;i<len;i++){
			var row = $("#tg").treegrid("find",data[i].id);
			row.columnName = data[i].value;
			row.columnNameCN = data[i].columnNameCN;
			row.columnId = data[i].columnId;
			$("#tg").treegrid("refresh",row.id);
		}
		$("#win").window("close");
	}
	
	//获取叶子节点，返回JSON对象数组
	function getAllChildren(nodes){
		var allChildren = [];
		var editor = {
				type:"combobox",
				options:{
					valueField:"columnName",
					textField:"columnNameTitle",
					panelHeight:"auto",
					data:columnData
				}
		}
		
		for(var i=0,len=nodes.length;i<len;i++){
			if(nodes[i].children && nodes[i].children.length>0){
				var temp = getAllChildren(nodes[i].children);
				for(var j=0,len2=temp.length;j<len2;j++){
					//temp[j].value="";
					temp[j].editor=editor;
					allChildren.push(temp[j]);
				}
			}else{
				//nodes[i].value="";
				nodes[i].editor=editor;
				allChildren.push(nodes[i]);
			}
		}
		return allChildren;
	}
	
	//获取所有节点中最大的id值
	function getMaxTreeId(data){
		var maxid = 0;
		for(var i=0,len=data.length;i<len;i++){
			if(data[i].children && data[i].children.length>0){
				maxid = getMaxTreeId(data[i].children);
			}else{
				maxid = maxid>data[i].id?maxid:data[i].id;
			}
		}
		return maxid;
	}
	
	//预览创建表格
	var maxLevelNum = 0;
	function createTable(childrenNodes){
		var preview = document.getElementById("preview");
		preview.innerHTML="";
		
		var table = document.createElement("table");
		table.border="0";
		table.cellspacing="1";
		table.cellpadding="0";
		table.width="100%";
		table.style.backgroundColor="#ccc;";
		var row = document.createElement("tr");
		row.style.backgroundColor="#FFF;";
		table.appendChild(row);
		
		maxLevelNum = getLastLevelNum(childrenNodes)+1;//获取最深一层的层级 
		
		createTD(table,row,childrenNodes);
		
		preview.appendChild(table);
	}
	
	//预览创建单元格
	function createTD(table,row,childrenNodes){
		if(childrenNodes && childrenNodes.length>0){
			for(var i=0;i<childrenNodes.length;i++){
				var children = childrenNodes[i].children;
				if(children && children.length>0){
					if(!Boolean(childrenNodes[i].isHidden)){
						var childrenNum = getLastChildrenNum(children);
						var col = document.createElement("td");
						col.align="center";
						col.colSpan = childrenNum;
						
						//如果有链接，添加链接
						if(childrenNodes[i].link && childrenNodes[i].link !==""){
							var a = document.createElement("a");
							a.href="#";
							a.innerText = childrenNodes[i].name;
							col.appendChild(a);
						}else{
							col.innerText = childrenNodes[i].name;
						}
						row.appendChild(col);
						
						var trow = table.rows[$("#tg").treegrid("getLevel",children[0].id)-1];
						if(!trow){
							trow = document.createElement("tr");
							trow.style.backgroundColor="#FFF;";
							table.appendChild(trow);
						}
						createTD(table,trow,children);
					}
				}else{
					if(!Boolean(childrenNodes[i].isHidden)){
						var col = document.createElement("td");
						col.align="center";
						
						var level = $("#tg").treegrid("getLevel",childrenNodes[i].id);
						col.rowSpan = (maxLevelNum-level);
						
						//如果有链接，添加链接
						if(childrenNodes[i].link && childrenNodes[i].link !==""){
							var a = document.createElement("a");
							a.href="#";
							a.innerText = childrenNodes[i].name;
							col.appendChild(a);
						}else{
							col.innerText = childrenNodes[i].name;
						}
						
						row.appendChild(col);
					}
				}
			}
		}
	}
	
	//获取最深一层叶子节点总数
	function getLastChildrenNum(childrenNodes){
		var childrenNum = 0;
		if(childrenNodes && childrenNodes.length>0){
			for(var i=0;i<childrenNodes.length;i++){
				var children = childrenNodes[i].children;
				if(children && children.length>0){
					childrenNum += getLastChildrenNum(childrenNodes[i].children);
				}else{
					if(!Boolean(childrenNodes[i].isHidden)){
						childrenNum++;
					}
				}
			}
		}
		return childrenNum;
	}
	
	//获取最深一层叶子节点层级
	function getLastLevelNum(childrenNodes){
		var maxLevel = 0;
		if(childrenNodes && childrenNodes.length>0){
			for(var i=0;i<childrenNodes.length;i++){
				var tlevel = 0;
				var children = childrenNodes[i].children;
				if(children && children.length>0){
					tlevel = getLastLevelNum(childrenNodes[i].children);
				}else{
					if(!Boolean(childrenNodes[i].isHidden)){
						tlevel = $("#tg").treegrid("getLevel",childrenNodes[i].id);
					}
				}
				
				if(tlevel>maxLevel) maxLevel = tlevel;
			}
		}
		return maxLevel;
	}
</script>