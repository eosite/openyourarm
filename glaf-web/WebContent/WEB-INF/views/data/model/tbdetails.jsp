<%@page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
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
<title>数据模型设计</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/font-awesome.min.css">
<link rel="stylesheet" media="screen" type="text/css" href="${contextPath}/scripts/colorpicker/css/colorpicker.css" />
<link rel="stylesheet" media="screen" type="text/css" href="${contextPath}/scripts/colorpicker/css/layout.css" />

</head>
<style>
.goes-table tbody {
	display: block;
	overflow-y: scroll;
}

.goes-table thead, .goes-table tbody tr, .goes-table thead tr {
	display: table;
	width: 100%;
	table-layout: fixed;
}

.goes-table thead {
	width: calc(100% - 1.2em)
}

.nav>li>a {
	padding: 6px 9px;
}
</style>
<body>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab_1_1" data-toggle="tab"
				aria-expanded="true"> General </a></li>
			<c:choose>
				<c:when test="${param.type == 'view'}">
					<li class=""><a href="#tab_1_2" data-toggle="tab"
						aria-expanded="false"> Query </a></li>
				</c:when>
				<c:otherwise>
					<li class=""><a href="#tab_1_2" data-toggle="tab"
						aria-expanded="false"> Columns </a></li>
				</c:otherwise>
			</c:choose>
			<li class=""><a href="#preview" data-toggle="tab"
						aria-expanded="false">Preview</a></li>
			<li class=""><a href="#other" data-toggle="tab"
						aria-expanded="false">other</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane fade active in" id="tab_1_1">
				<div class="form-body" id="table-describe">
					<div class="form-group has-success">
						<label class="control-label">中文描述</label>
						<div class="input-icon right">
							<input type="text" class="form-control" name="name" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label">Code</label>
						<div class="input-icon right">
							<input type="text" class="form-control" name="code" readonly='readonly' /> <input
								type="text" class="form-control" name="id"
								style="display: none;" readonly='readonly' />
						</div>
					</div>
					<div class="form-group has-warning">
						<label class="control-label">说明</label>
						<div class="input-icon right">
							<textarea class="form-control" rows="5" name="describe"></textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="tab_1_2">
				<c:choose>
					<c:when test="${param.type == 'view'}">
						<div class="form-group has-warning" id="query">
							<label class="control-label">Show Query</label>
							<div class="input-icon right">
								<textarea class="form-control" rows="10" name="query"></textarea>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="row ">
							<table id="column-details"
								class="goes-table table table-hover table-striped table-condensed">
								<thead>
									<tr class="">
										<th colspan="7">
											<button class="btn btn-default btn-xs"
												onclick="TableDetails.AddRow(null, true)" title="添加">
												<i class="fa fa-plus-square"></i>
											</button>
											<button class="btn btn-default btn-xs"
												onclick="TableDetails.AddDefaultRows()" title="添加默认字段">
												<i class="fa fa-plus-square-o" aria-hidden="true"></i>
											</button>
										</th>
									</tr>
									<tr class="">
										<th style="width: 6%">编号</th>
										<th>名称</th>
										<th>编码</th>
										<th>结构</th>
										<th>类型</th>
										<th>长度</th>
										<th style="width: 6%;">主键</th>
										<th style="width: 6%">外键</th>
										<th style="width: 8%">操作</th>
									</tr>
								</thead>
								<tbody style="height: 266px;"></tbody>
							</table>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="tab-pane fade" id="preview">
			<div class="form-group has-warning" id="SQL">
				<label class="control-label"> SQL </label>
				<div class="input-icon right" style="overflow: auto;/* font-family:Lucida Calligraphy; */font-size: 20px;font-weight: bolder;">
					<textarea class="form-control" rows="10" name="SQL" ></textarea>
				</div>
			</div>
			</div>
			<div class="tab-pane fade" id="other">
			<div class="form-body" id="other-wise">
					<div class="form-group has-success">
						<label class="control-label">颜色</label>
						<div class="input-icon right">
							<input type="text" class="form-control" id="colorpickerField" name="color" />
						</div>
						
					</div>
					
				</div>
			</div>
			
		</div>
	</div>
</body>
<script type="text/html" id="row-template">
<tr class="column-detail form-group-sm">
	<td style="width:6%">
		<span class="no"></span>
	</td> 
	<td><input class="form-control" name="name" /></td> 
	<td style="display:none;"><input class="form-control" name="id"/></td> 
	<td><input class="form-control" name="code" /></td> 
	<td><input class="form-control" name="describe" /></td> 
	<td>
		<select class="form-control" name="datatype">
			<option value="varchar">Varchar</option>
			<option value="integer">Integer</option>
			<option value="date">Date</option>
		</select>
	</td> 
	<td><input class="form-control" name="length" type="number" /></td> 
	<td style="width:4%">
		<input class="" type="checkbox" name="primaryKey" />
	</td>
	<td style="width:4%">
		<input class="" type="checkbox" name="foreignKey" />
	</td>
	<td style="width:10%">
		<button class="btn btn-default btn-xs" onclick="TableDetails.RemoveRow(this)">-</button>
		<button class="btn btn-default btn-xs" onclick="TableDetails.move(this, true)">↑</button>
		<button class="btn btn-default btn-xs" onclick="TableDetails.move(this)">↓</button>
	</td>
</tr>
</script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/base/js/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="${contextPath}/scripts/colorpicker/js/colorpicker.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/colorpicker/js/eye.js"></script>
    <script type="text/javascript" src="${contextPath}/scripts/kendo/utils.js"></script>

    
<script type="text/javascript">
	var $callbacks = jQuery.Callbacks("hmtd.fzmt.data.model");
	$(document).ready(function() {
		
		$callbacks.fire();
		$('#colorpickerField').ColorPicker({
			onSubmit: function(hsb, hex, rgb, el) {
				$(el).val(hex);
				$(el).ColorPickerHide();
			},
			onBeforeShow: function () {
				$(this).ColorPickerSetColor(this.value);
			}
		})
		.bind('keyup', function(){
			$(this).ColorPickerSetColor(this.value);
		});
	});
	
	function GetPreview(data, fn){
		$.ajax({
			url : '${contextPath}/mx/data/model/preview',
			data : {
				tableDefinition : JSON.stringify(data)
			},
			type : 'POST',
			dataType : 'JSON',
			success : fn
		});
	}

	$callbacks.add(function() {
		
		var javaTypes = {
				integer : 'Integer',
				varchar : 'String',
				date : 'Date'
		};
		
		$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
            // 获取已激活的标签页的名称
            var activeTab = $(e.target).text();
            // 获取前一个激活的标签页的名称
           // var previousTab = $(e.relatedTarget).text();
            if(activeTab == "Preview")
			{
				var data = TableDetails.GetData();
				
				var tableDefinition = $.extend(true, {}, data);
				tableDefinition.tableName = tableDefinition.code
					|| tableDefinition.id;
				$.each(tableDefinition.columns, function(i, v){
					v.javaType = javaTypes[v.datatype];
					v.datatype = null;
					v.columnName = v.code || v.id;
					
					if(v.primaryKey){
						tableDefinition.idField = $.extend(true, {}, v);
					}
				});
				
				window.GetPreview(tableDefinition, function(ret){
					if(ret && ret.SQL){
						$("#SQL").LoadData(ret);
					}
				});
			}
        });
	});

	var TableDetails = (function() {
		var func = new Function();
		var $columnDetails = $("#column-details"), //
		$tbody = $columnDetails.find("tbody");
		var rowTemplate = $("#row-template").html();
		
		function Row(name, id, datatype, length, primaryKey) {
			var data = {
				name : name,
				datatype : datatype || "varchar",
				code : id,
				length : length,
				id : id,
				primaryKey : !!primaryKey
			};
			return data;
		}
		
		var defaultRows = [ 
		        //Row("主键ID", "ID_", "", 50, true),
				//Row("TOPID", "TOPID_", "", 50, false),
				//Row("PARENTID", "PARENTID_", "", 50, false),
				//Row("层级代码", "TREEID_", "", 50, false),
				//Row("创建人", "CREATEBY_", "", 50, false),
				//Row("创建时间", "CREATEDATE_", "date", '', false),
				//Row("修改人", "UPDATEBY_", "", 50, false),
				//Row("修改时间", "UPDATEDATE_", "date", '', false),
				Row("主键ID", "ID", "", 100, true),
				Row("TOPID", "TOPID", "", 100, false),
				Row("PARENT_ID", "PARENT_ID", "", 50, false),
				Row("TREE_ID", "TREE_ID", "", 100, false),
				Row("INDEX_ID", "INDEX_ID", "integer", 50, false),
				Row("LISTNO", "LISTNO", "integer", '', false), 
				Row("CREATEDATE", "CREATEDATE", "date", '', false), 
				Row("UPDATEDATE", "UPDATEDATE", "date", '', false),
				Row("LESSESSID", "LESSESSID", "", '', false),
				Row("CREATER", "CREATER", "", '', false),
				Row("UPDATER", "UPDATER", "", '', false),
				],
		SYSTEM = {};
		$.each(defaultRows, function(i, v){
			SYSTEM[v.id] = v;
		});
		
		func.GetData = function() {
			var isOk = true, data = $("#table-describe").GetData() || {};
			!data.columns && (data.columns = []);
			var dangerCls = "warning", codes = {}, names = {};
			$tbody.find("tr.column-detail").each(function() {
				var $this = $(this), v = $this.GetData();
				if (!v.code) {
					isOk = false;
					$this.addClass(dangerCls);
				} else {
					$this.removeClass(dangerCls);
					if(codes[v.code]){
						isOk = codes = false;
					}
					codes[v.code] = v.code;
				}
				if(!v.name){
					names = true;
				} else {
					if(names[v.name]){
						names = false;
					}
					names[v.name] = v.name;
				}
				data.columns.push(v);
			});
			$.extend(true, data, $("#query").GetData());
			$.extend(true, data, $("#other-wise").GetData());

			if(names === true){
				alert("字段Name不能为空!");
				isOk = false;
			} else if(names === false){
				alert("字段Name不能重复!");
				isOk = false;
			} else if(codes === false){
				alert("字段Code不能重复!");
				isOk = false;
			} else if (!isOk && confirm("字段Code为空,是否自动生成?")) {
				isOk = true;
				$.each(data.columns, function(i, v) {
					!v.code && (v.code = v.id);
				});
			}
			return (!isOk || data);
		};
		function each(i, v) {
			func.AddRow(v);
		}
		func.InitData = function(data) {
			if (!data)
				return;
			$("#table-describe").LoadData(data);
			$("#query").LoadData(data);
			if (data.columns) {
				$.each(data.columns, each);
			}
		};
		func.LoadData = function($tr, data) {
			window.setTimeout(function() {
				$tr.LoadData(data);
				func.changeNo($tr);
				if(data.id in SYSTEM){
					$tr.addClass("success");
				}
			}, 0);
		};

		func.changeNo = function($tr) {
			$tr.find("span.no").text($tr.index() + 1);
		};

		var $id = new Date().getTime();

		func.AddRow = function(data, prepend) {
			if (data) {
				var id = data.id, isOk = true;
				$tbody.find("tr.column-detail").each(function() {
					var o = $(this).GetData();
					if (o.id && o.id == id) {
						isOk = false;
					}
				});
				if (!isOk)
					return false;
			}
			var $tr = $(rowTemplate);
			if (prepend) {
				//$tbody.prepend($tr);
			} else {
				//$tbody.append($tr);
			}
			
			$tbody.append($tr);
			
			func.LoadData($tr, data || {
				name : 'name_' + (++$id),
				datatype : 'varchar',
				id : 'col_' + $id,
				code : 'col_' + $id,
				length : 200,
				primaryKey : false
			});
		};

		func.AddDefaultRows = function() {
			$.each(defaultRows, function(i, v) {
				func.AddRow(v);
			});
		};

		func.RemoveRow = function(o) {
			var $this = $(o).closest("tr"), //
			$nextTr = $this.nextAll("tr");
			$this.remove();
			$nextTr.each(function() {
				func.changeNo($(this));
			});
		};

		func.move = function(o, up) {
			var $tr = $(o).closest("tr"), $tmp = null;
			if (up) {
				$tmp = $tr.prev("tr");
				$tr.insertBefore($tmp);
			} else {
				$tmp = $tr.next("tr");
				$tmp.insertBefore($tr);
			}
			func.changeNo($tr);
			func.changeNo($tmp);
		};

		return func;
	})();

	$.fn.LoadData = function(data, attr, each) {
		if (!data) {
			return this;
		}
		attr = attr || "name";
		return this.each(function(i, v) {
			var $attrs = $(this).find("[" + attr + "]");
			if (each && $.isFunction(each)) {
				$attrs.each(each);
			} else {
				$attrs.each(function(i, v) {
					var $this = $(this), name = $this.attr(attr);
					if (data[name] !== undefined) {
						if ($this.is(":checkbox")) {
							this.checked = data[name];
						} else {
							$this.val(data[name]);
						}
					}
				});
			}
		});
	};

	$.fn.GetData = function(attr) {
		attr = attr || "name";
		var data = {};
		$(this).find("[" + attr + "]").each(function() {
			var $this = $(this), name = //
			$this.attr(attr), val;
			if ($this.is(":checkbox")) {
				val = this.checked;
			} else {
				val = $this.val();
			}
			data[name] = val;
		});
		return data;
	};
</script>

</html>