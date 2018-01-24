<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>表关系配置</title>
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/plugins/bootstrap/tabs/style/plugins.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css"
	rel="stylesheet" id="style_components" type="text/css" />
<link
	href="${contextPath }/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css">
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
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
</style>
<script type="text/javascript"
	src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js"></script>
<script>
	var contextPath = "${contextPath}", $q = $
			.Callbacks("zygs.fzmt.table.relation");
</script>
</head>
<body>
	<div id="container" style="">
		<div id="tabs-01" data-role="tabs"></div>
	</div>
</body>

<script
	src="${contextPath }/scripts/plugins/bootstrap/tabs/js/jquery.tabs.extends.js"
	type="text/javascript"></script>
<script
	src="${contextPath }/scripts/plugins/bootstrap/tabs/js/bootstrap-tabdrop.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${contextPath }/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js"></script>

<script type="text/template" id="mytemplate-0">
		<div class='group'>
			<div style='padding:10px;'>
				<span style='margin-right:10px;display:inline-block;'>主表: </span>
				<select style='width:80%;display:inline-block;' class='form-control relation' tablename='{0}' ><select>
			</div>
			<br>
			<div class='grid' id='grid-{0}' style="width: 100%; height: 550px;" tablename='{0}' tableid='{1}' systemName=''></div>
		</div>
</script>
<script type="text/javascript">
	var contentTemplate = $("#mytemplate-0").html();
	var tables = [ {
		name : '123',
		tableName : "cell_useradd8105"
	}, {
		name : '345',
		tableName : "cell_useradd8060"
	}, {
		name : '菜单测试表',
		tableName : "cell_useradd8193"
	} ];
	var fn = fnName = "${param.fn}", targetId = "${param.targetId}",
		$parent = window.opener || window.parent;
	$q.add(function() {
		var tabs = [];
		if (fn && (fn = $parent[fn])) {
			tables = fn.call($parent.document.getElementById(targetId));
		}
		$.each(tables, function(i, table) {
			tabs.push({
				content : contentTemplate.format(table.tableName, table.id,
						table.systemName),
				title : table.name+table.tableName,
				closeable : false,
				selected : false,
				isDropdownMenu : false
			});
		});
		$("#tabs-01").tabs({
			tabs : tabs,
			width : "auto",
			height : 'auto',
			reversed : false,
			border : true,
			fit : false,
			tabdrop : false,
			tabPosition : 'left',
			tabStyle : 'tabs',
			cols : [ 2, 10 ],
			onSelect : function() {
				var that = $(this);
				setTimeout(function() {
					that.find("div.active div.grid").grid("resize");
				}, 200);
			}
		});
	});

	$q.add(function() {
		$("div.grid").each(function(i, v) {
			var $this = $(this);
			$this.grid({
				datas : window.getColumns({
					tableId : $this.attr("tableid"),
					systemName : $this.attr("systemName"),
					tableName : $this.attr("tablename")
				}),
				clickUpdate : true,
				editable : true,
				columns : [ {
					title : '字段',
					field : 'dname'
				}, {
					title : '字段名称',
					field : 'fname'
				}, {
					title : '关联字段',
					field : 'rname',
					editor : function(container, options, row) {
						window.dropdownFunc(container, options, row);
					}
				} ]
			});
		});
	});

	$q.add(function() {
		$("select.form-control").each(
				function() {
					var tableName = $(this).attr("tablename");
					var opts = new StringBuffer(
							"<option value=''>请选择主表</option>");
					var selectedIds = [] ;
					$.each(tables, function(I, T) {
						if (tableName != this.tableName) {
							console.log(T);
							opts.appendFormat(
									"<option value='{0}' tableid={1}>{2}</option>",
									T.tableName, T.id ,T.name+"-"+T.tableName);
						}else{
							if(T.topId){
								selectedIds.push(T.topId);
							}
						}
					});
					$(this).append(opts.toString());
					//恢复选中
					$(this).find("option").each(function(){
						var $option = $(this);
						if($.inArray($option.attr("tableid"),selectedIds) != -1){
							$option.attr("selected","selected");
						}
					})
				});
	});

	$(function() {
		$q.fire();
	});

	function getSaveData(){
		var relations = {} ;
		$("select.form-control.relation").each(function(){
			var $this = $(this),
				$selected = $this.find("option:selected");
				tablename = $this.attr("tablename"),
				$grid = $("#grid-"+tablename),
				rows = $grid.grid("getData") ;
				if($selected.val()){
					relations[tablename] =  {
							tableId : $selected.attr("tableid")
						};
				}
				$.each(rows,function(i,row){
					if(row.rname){
						$.extend(relations[tablename],{
							tableColumn : row.dname,
							tableKey : row.rname
						});
					}
				});
		})
		return relations ;
	}
</script>

<script type="text/javascript">
	function getRelationShip() {
		
	}
	
	function dropdownFunc(container, options, row) {
		var field = options.field, $drop = $("<select>", {
			class : 'form-control',
			style : 'width:40%',
			onchange : "onSelect(this)",
			name : field
		}).append(window.getSelectOptions(container)).val(row[field]);
		container.append($drop);
	}

	function onSelect(e) {
		var $g = $(e).closest("div.grid");
		var row = $g.grid("getRow", e);
		if (row) {
			row[$(e).attr("name")] = e.value;
		}
	}
	function getSelectOptions(o) {
		var columnSelectKey = "columnSelect";
		var $grid = $(o).closest("div.grid");
		var tableName = $grid.attr("tablename");
		var $select = $grid.closest("div.group").find("select");
		if (!$grid.data(columnSelectKey)) {
			if ($select.val()) {
				var opts = new StringBuffer(
						"<option value=''>请选择主表关联列</option>");
				var data = $("#grid-" + tableName).grid("getData");
				$.each(data, function(i, c) {
					opts.appendFormat("<option value='{0}'>{1}</option>",
							c.dname, c.fname);
				});
				$grid.data(columnSelectKey, opts.toString(""));
			}
		}
		return $grid.data(columnSelectKey);
	}

	function getColumns(tableObj) {
		var collection = [], json = {}, dname;
		$.each([ 1, 99 ], function(i, type) {
			tableObj.type = type;
			window.selectFieldByTableId(tableObj, function(data) {
				if (data && data.rows) {
					$.each(data.rows, function(index, row) {
						dname = row.dname;
						!json[dname]
								&& ((json[dname] = row) && (collection
										.push(row)));
					});
				}
			});
		});
		return collection;
	}

	function selectFieldByTableId(tableObj, fn) {
		$.ajax({
			url : "${contextPath}/rs/isdp/cellDataField/selectFieldByTableId",
			type : "POST",
			dataType : "JSON",
			async : false,
			data : {
				tableId : tableObj.tableId,
				type : tableObj.type,
				tableName : tableObj.tableName,
				systemName : tableObj.systemName
			},
			success : function(data) {
				if (fn)
					fn(data);
			}
		});
	}
</script>
</html>