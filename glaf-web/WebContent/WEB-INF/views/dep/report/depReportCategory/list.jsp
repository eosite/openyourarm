<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模板列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
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
</style>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="${contextPath }/webfile/js/jquery.ztree.extends.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="btn-1" class="k-button" style="" onclick="javascript:saveOrUpdateSort();">新增分类</button>
      <button type="button" id="btn-2" class="k-button" style="" onclick="javascript:saveOrUpdateSort(true);">修改分类</button>
      <button type="button" id="btn-3" class="k-button" style="" onclick="javascript:saveOrUpdateTemplate();">新增模版</button>
 	  <button type="button" id="btn-7" class="k-button" style="" onclick="javascript:deleteTemplates();">删除模版</button>
      <button type="button" id="btn-4" class="k-button" style="" onclick="javascript:downLoadJsonTemplate();">导出模版(JSON)</button>
      <button type="button" id="btn-5" class="k-button" style="" onclick="javascript:uploadFiles();">导入模版(JSON)</button>
	  <input class='k-textbox' id="textbox-1">
	  <button type="button" id="btn-6" class="k-button" style="" onclick="javascript:search();">检索</button>
   </div>
</script>
<script type="text/javascript">

var defType = "A000";

	var dataSource = {
		"schema" : {
			"total" : "total",
			"data" : "rows"
		},
		"transport" : {
			"parameterMap" : function(options) {
				options && (options.rows = options.pageSize);
				!options.depId && $.extend(options, window.getTreeParams());
				return options;
			},
			"read" : {
				"type" : "POST",
				dataType : 'JSON',
				"url" : "${contextPath}/mx/dep/report/depReportTemplate/templateJson"
			}
		},
		"pageSize" : 10,
		"serverPaging" : true
	};

	var columns = [
			{
				"field" : "PNAME",
				"title" : "模板分类名称",
				"width" : "150px"
			},
			{
				"field" : "NAME_",
				"title" : "模版名称",
				"width" : "150px"
			},
			{
				"field" : "CREATOR_",
				"title" : "创建人",
				"width" : "150px",
				"lockable" : false,
				"locked" : false
			},
			{
				"field" : "CREATEDATETIME_",
				"title" : "创建时间",
				"width" : "160px",
				template : function(item) {
					return window.formatDate(item, "CREATEDATETIME_");
				}
			},
			{
				"field" : "MODIFIER_",
				"title" : "修改人",
				"width" : "150px",
				"lockable" : false,
				"locked" : false
			},
			{
				"field" : "MODIFYDATETIME_",
				"title" : "修改时间",
				"width" : "160px",
				template : function(item) {
					return window.formatDate(item, "MODIFYDATETIME_");
				}
			},
			{
				"command" : [
						{
							"text" : "修改",
							"name" : "edit",
							"click" : function(e) {
								var dataItem = this.dataItem($(e.currentTarget)
										.closest("tr"));
								var link = "${contextPath}/mx/dep/report/depReportTemplate/edit?id="
										+ dataItem.ID_;
								editRow({
									link : link
								});
							}
						},
						{
							text : '模版设计',
							name : 'defined',
							click : function(e) {
								var dataItem = this.dataItem($(e.currentTarget)
										.closest("tr"));
								
								
								var type = window.getParentType(dataItem.PID) || defType;
								
								var url = "${contextPath}/mx/form/defined/spreadjs/list?id="
										+ dataItem.ID_ + "&type=" + type;
								window.open(url);
							}
						},
						{
							text : '删除模版',
							name : 'removeTmp',
							click : function(e) {
								var dataItem = this.dataItem($(e.currentTarget)
										.closest("tr"));
								window.deleteTems([dataItem.ID_]);
							}
						} ],
				"width" : "230px",
			} ];

	function formatDate(item, key) {
		if (item && (item = item[key])) {
			return kendo.toString(new Date(item), 'yyyy-MM-dd HH:mm:ss');
		}
		return "";
	}
	
	/**
	 * 获取主分类code
	 */
	function getParentType(id){
		var $ztree = $("#tree").ztree("getZtree"), //
		node = $ztree.getNodeByParam("id",id, null);
		if(node){
			
			if(node.t && node.t == "main"){
				return node.code;
			}
			else if(node.getParentNode()){
				return window.getParentType(node.getParentNode().id);
			}
		}
	}

	$(function() {
		$("#grid").kendoGrid({
			"dataSource" : dataSource,
			"height" : x_height,
			"pageable" : {
				"refresh" : true,
				"pageSizes" : [ 5, 10, 15, 20, 25, 50, 100, 200, 500 ],
				"buttonCount" : 10
			},
			"selectable" : "multiple, row",
			"toolbar" : kendo.template(jQuery("#template").html()),
			"columns" : columns,
			scrollable : true
		});

		window.initZtree();
	});

	function addRow() {
		editRow();
	}

	function editRow(o) {

		var url = o.link
				|| ("${contextPath}/mx/dep/report/depReportCategory/edit?" + $
						.param(o));

		$.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			//offset : [ '20px', '' ],
			fadeIn : 100,
			area : [ '520px', (220) + 'px' ],
			iframe : {
				src : url
			}
		});
	}

	function closeLayer() {
		layer.close(layer.getFrameIndex());
	}
	
	function deleteTems(ids){
		if(confirm("确定删除吗?")){
			$.ajax({
				url : '${contextPath}/mx/dep/report/depReportTemplate/delete',
				data : {
					ids : ids.join(",")
				},
				type : 'post',
				dataType : 'json',
				success : function(ret){
					alert("操作成功!");
					window.gridReload(window.getTreeParams());
				}
			});
		}
	}

	function saveOrUpdateTemplate() {
		var row = window.getZtreeSelected(); //
		if (row) {
			var url = "${contextPath}/mx/dep/report/depReportTemplate/edit?depId="
					+ row.id;
			window.editRow({
				link : url
			});
		} else {
			alert("请选择分类节点!");
		}
	}

	function getZtreeSelected() {
		var $ztree = $("#tree");
		if($ztree.data("ztree")){
			var ztree = $ztree.ztree("getZtree");
			var nodes = ztree.getSelectedNodes();
			if (nodes && nodes.length) {
				var row = nodes[0];
				$ztree.data("selectedNode", $.extend({}, row));
				return row;
			}
		}
		return null;
	}

	function saveOrUpdateSort(update) {
		var row = window.getZtreeSelected();
		if (row) {
			var param = null;
			if (update) {
				param = {
					pId : row.pId,
					id : row.id
				};
			} else {
				param = {
					pId : row.id,
				};
			}
			window.editRow(param);
		} else {
			alert("请选择分类节点!");
		}

	}
	
	/**
	* 下载json模版
	*/
	function downLoadJsonTemplate(){
		var ids = window.getSelectedIds();
		if(ids && ids.length){
			window.location.href = "${contextPath}/mx/dep/report/depReportTemplate/downLoadJsonTemplate?ids=" + ids.join(",") + "&r=" + new Date().getTime();
		}
	}
	
	function getSelectedIds(){
		var kgrid = $("#grid").data("kendoGrid");
		var rows = kgrid.select();
		if(rows && rows.length){
			var ids = [];
			$.each(rows, function(i, v){
				var r = kgrid.dataItem(v);
				ids.push(r.ID_);
			});
			return ids;
		}
		return null;
	}
	
	function deleteTemplates(){
		var ids = window.getSelectedIds();
		if(ids && ids.length){
			deleteTems(ids);
		}
	}
	
	function uploadFiles(type){
		var row = window.getZtreeSelected();
		if(row){
			var url = "${contextPath}/mx/dep/report/depReportTemplate/uploadFiles?" + $.param({
				fn : "search",
				pId : row.id,
				type : (type || "json")
			});
			window.openWin(url);
		} else {
			alert("请选择分类节点!");
		}
	}
	
	function openWin(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "模版上传",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (250) +'px'],
                        iframe: {src: link}
		});
	}

	function refresh() {
		window.initZtree();
		window.closeLayer();
		window.zTreeBeforeClick(null, $("#tree").data("selectedNode"), null);
	}
</script>
</head>
<body>
	<div id="container">
		<div id="vertical" style=''>
			<div id="center-pane" style="border: 0px;">
				<div style="border: 0px;">
					<div class="ztree" id="tree" style='height: auto; border: 0px;'></div>
				</div>
				<div style="border: 0px;">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var $tabstrip = $(".tabstrip"), $vertical = $("#vertical"), contextPath = "${contextPath}";

	$vertical.css({
		height : $(window).height()
	}).kendoSplitter({
		orientation : "vertical",
		panes : [ {
			collapsible : false,
			resizable : false,
			scrollable : false,
			size : '32px'
		} ],
		layoutChange : onResize,
	});

	$("#center-pane").kendoSplitter({
		orientation : "horizontal",
		panes : [ {
			collapsible : true,
			resizable : true,
			scrollable : true,
			size : '252px'
		}, {
			collapsible : true,
			resizable : true,
			scrollable : true
		} ]
	});

	function onResize(e) {
		$vertical.css({
			height : $(window).height()
		});

		$tabstrip.css({
			height : $tabstrip.closest('div[role=group]').height()
		});

	}
	
	function initZtree() {
		var url = "${contextPath}/mx/dep/report/depReportCategory/json?rows=999999";
		$("#tree").ztree({
			async : {
				url : url,
				dataFilter : function(treeId, parentNode, ret) {
					
					var rows = [], tmpId = 0;
					if(ret.parentRows){
						$.each(ret.parentRows, function(i, v){
							if(v.code == defType){
								tmpId = v.id;
							}
							v.icon = '${contextPath}/images/home.png';
							rows.push(v);
						});
					}
					
					/* rows.unshift({
						id : 0,
						name : "模板分类",
						icon : '${contextPath}/images/home.png'
					}); */
					
					
					$.each(ret.rows, function(i, v){
						if(v.pId == 0){
							v.pId = tmpId;
						}
						rows.push(v);
					});
					
					return rows;
				}
			},
			callback : {
				onAsyncSuccess : ztreeOnAsyncSuccess,
				beforeClick : zTreeBeforeClick
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
				}
			},
			edit : {
				enable : true,
				showRenameBtn : false,
				showRemoveBtn : false,
				editNameSelectAll : true,
				drag : {
					isCopy : false,
					isMove : true
				}
			}
		});
	}

	function ztreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		var selectedNode = $("#" + treeId).data("selectedNode");
		if (selectedNode) {
			window.setTimeout(function() {
				var ztree = $("#" + treeId).ztree("getZtree"), //
				node = ztree.getNodeByParam("id",//
				selectedNode.id + "", null);
				if (node) {
					ztree.selectNode(node);
					ztree.expandNode(node, true, true, true);
				}
			}, 0);
		}
	}

	function search() {
		var text = $("#textbox-1").val(), params = {};
		if (text) {
			params.name = "%" + text + "%";
		}
		window.gridReload(params);
		$("#tree").ztree("highlight", "name", text);
	}
	
	function gridReload(params, treeNode) {
		var p = $.extend(true, params || {}, window.getTreeParams(treeNode));
		$("#grid").data("kendoGrid").dataSource.read(p);
	}
	
	function getTreeParams(treeNode){
		treeNode = treeNode || window.getZtreeSelected();
		var params = {};
		if(treeNode){
			params.depId = treeNode.id;
		}
		return params;
	}

	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
		if (treeNode && treeNode.id) {
			window.gridReload(null, treeNode);
		}
	}
</script>
</html>