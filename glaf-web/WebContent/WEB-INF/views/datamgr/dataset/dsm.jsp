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
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据集映射</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/font-awesome.min.css">
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
		<div class="row">
			<div class="form-group has-warning col-sm-6">
				<label class="control-label">字段映射</label>
				<div class="input-icon right">
					<table id="mapping-columns"
						class="goes-table table table-hover table-striped table-bordered table-condensed">
						<thead>
							<tr class="">
								<th colspan="3">
									<button class="btn btn-default btn-xs"
										onclick="Mapping.SortByName('Columns')" title="按名称自动匹配">
										<i class="fa fa-asterisk" aria-hidden="true"></i>
									</button>
								</th>
							</tr>
							<tr class="">
								<th style="width: 8%">No.</th>
								<th>名称</th>
								<th>映射名称</th>
							</tr>
						</thead>
						<tbody style="height: 466px;"></tbody>
					</table>
				</div>
			</div>

			<div class="form-group has-warning  col-sm-6">
				<label class="control-label">参数映射</label>
				<div class="input-icon right">
					<table id="mapping-params"
						class="goes-table table table-hover table-striped table-bordered table-condensed">
						<thead>
							<tr class="">
								<th colspan="3">
									<button class="btn btn-default btn-xs"
										onclick="Mapping.SortByName('Params')" title="按名称自动匹配">
										<i class="fa fa-asterisk" aria-hidden="true"></i>
									</button>
								</th>
							</tr>
							<tr class="">
								<th style="width: 8%">No.</th>
								<th>名称</th>
								<th>映射名称</th>
							</tr>
						</thead>
						<tbody style="height: 466px;"></tbody>
					</table>
				</div>
			</div>

		</div>
		<div class="row">
			<div class="col-sm-11"></div>
			<div class="col-sm-1">
				<button class="" onclick="Mapping.Save(this)">确定</button>
			</div>
		</div>
	</div>
</body>
<script type="text/html" id="mapping-template-common">
<tr class="column-detail form-group-sm">
	<td style="display:none;">
		<input class="form-control" name="mappingId"/>
		<input class="form-control" name="columnLabel"/>
    </td> 
	<td style="width:8%">
		<span class="no"></span>
	</td> 
	<td style="">
		<span class="" name="title"></span>
	</td> 
	<td>
		<select class="form-control" name="mapping"></select>
	</td>
</tr>
</script>

<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/base/js/bootstrap.min.js"></script>
<script type="text/javascript">
	var $callbacks = jQuery.Callbacks("hmtd.fzmt.data.model");
	$(document).ready(function() {
		$callbacks.fire();
	});

	$callbacks.add(function() {
		Mapping.init();
	});

	var GoesTable = (function() {
		var func = function(table, rowTemplate) {
			this.$table = $(table);
			this.$tbody = this.$table.find("tbody");
			this.rowTemplate = rowTemplate;
		};

		func.prototype.AddRow = function(data, prepend) {
			var $tr = $(this.rowTemplate), //
			$tbody = this.$tbody;
			if (prepend) {
				$tbody.prepend($tr);
			} else {
				$tbody.append($tr);
			}
			$tbody.append($tr);
			func.LoadData($tr, data);
			return $tr;
		};

		func.LoadData = function($tr, data) {
			window.setTimeout(function() {
				$tr.LoadData(data);
				func.changeNo($tr);
			}, 0);
		};

		func.changeNo = function($tr) {
			$tr.find("span.no").text($tr.index() + 1);
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

		func.prototype.clear = function() {
			this.$tbody.empty();
		};

		func.prototype.GetData = function() {
			var items = [];
			this.$tbody.find("tr").each(function() {
				items.push($(this).GetData());
			});
			return items;
		};

		return func;
	})();

	var Mapping = (function(id, dsmId) {
		var func = new Function(), //
		D = new GoesTable("#mapping-list", //
		$("#mapping-template").html());

		var GetData = function(url, data, fn) {
			$.ajax({
				data : data,
				url : url,
				type : 'POST',
				dataType : 'JSON',
				success : fn
			});
		}, GetMappings = function(id, fn) {
			GetData('${contextPath}/mx/dataset/dataSetMapping/json', {
				parentId : id
			}, fn);
		}, GetLastJSON = function(id, fn) {
			GetData("${contextPath}/rs/dataset/getLastestJson", {
				datasetId : id
			}, fn);
		}, lastJson = null;

		GetLastJSON(id, function(ret) {
			lastJson = ret;
			func.Set({
				id : "${mapping.id}",
				dsmId : dsmId
			});
		});

		func.init = function() {
			GetMappings(id, function(ret) {
				if (!ret.rows) {
					return;
				}
				$.each(ret.rows, function(i, v) {
					D.AddRow(v);
				});
			});
		};

		var Columns = new GoesTable("#mapping-columns", //
		$("#mapping-template-common").html()), Params = new GoesTable(
				"#mapping-params", //
				$("#mapping-template-common").html());

		function AddTableRow(gt, collection) {
			gt.clear();
			$.each(collection, function(i, v) {
				gt.AddRow(v);
			});
		}

		func.Set = function(d) {

			if (lastJson == null) {
				return false;
			}

			//var $tr = $(o).closest("tr");

			var data = d;

			/**
			 * 列映射
			 */
			AddTableRow(Columns, lastJson.selectSegments);

			/**
			 * 参数映射
			 */

			function populateParams(params) {
				params = params || [];
				$.each(params, function(i, v) {
					v.title = v.name;
					v.columnLabel = v.param;
				});
				return params;
			}

			lastJson.params = populateParams(lastJson.params);

			$.each(lastJson.params, function(i, v) {
				v.title = v.name;
				v.columnLabel = v.param;
			});
			AddTableRow(Params, lastJson.params);

			function Set(gt, map, STR) {
				gt.$tbody.find("tr").each(function(i, v) {
					var $this = $(this);
					var data = $this.GetData();
					var value = map[data.columnLabel];
					$this.find("td select.form-control:eq(0)")//
					.append(STR).val(value ? value.mappingCode : "");
				});
			}

			var FIELDS = new StringBuffer("<option value=''></option>"), PARAMS = new StringBuffer(
					"<option value=''></option>");

			/**
			 * 映射数据集的字段，参数
			 */
			GetLastJSON(data.dsmId, function(ret) {
				if (ret && (ret.selectSegments)) {
					$.each(ret.selectSegments, function(i, v) {
						FIELDS.appendFormat("<option value='{1}'>{0}</option>",
								v.title, v.columnLabel);
					});
					Columns.$tbody.data("collection", ret.selectSegments);
				}

				if (ret && populateParams((ret.params))) {
					$.each(ret.params, function(i, v) {
						PARAMS.appendFormat("<option value='{1}'>{0}</option>",
								v.title, v.columnLabel);
					});
					Params.$tbody.data("collection", ret.params);
				}

				var SELECTSTR = FIELDS.toString(), PARAMSSELECTSTR = PARAMS
						.toString();

				GetData("${contextPath}/mx/dataset/dataSetMappingItem/json", {
					rows : 200,
					parentId : data.id
				}, function(ret) {
					var map = {};
					if (ret && ret.rows) {
						$.each(ret.rows, function(i, v) {
							if (!v.mappingType || !v.code) {
								return true;
							}
							!map[v.mappingType] && (map[v.mappingType] = {});
							map[v.mappingType][v.code] = v;
						});
					}
					Set(Columns, map["column"] || {}, SELECTSTR);
					Set(Params, map["param"] || {}, PARAMSSELECTSTR);
				});
				Columns.$tbody.data("parentId", data.id);
			});

			$('ul.nav-tabs a:last').tab('show');
		};

		/**
		 * 按名称自动匹配
		 */
		func.SortByName = function(type) {

			var gt = eval(type), //
			tmpSelectSegments = gt.$tbody.data("collection");

			if (!tmpSelectSegments || //
			!tmpSelectSegments.length) {
				return false;
			}
			var NAMES = {};
			$.each(tmpSelectSegments, function(i, v) {
				v.title && (NAMES[v.title] = v);
			});

			gt.$tbody.find("tr").each(function() {
				var $this = $(this);
				var data = $this.GetData();
				data.title && (data.title in NAMES) && //
				(data.mapping = NAMES[data.title].columnLabel);
				$this.LoadData(data);
			});
		};

		func.Save = function() {

			var items = [], item;
			
			function PopulateItems(gt, type) {
				gt.$tbody.find("tr").each(function() {
					var $this = $(this);
					var data = $this.GetData();
					if (!data.mapping) {
						return true;
					}
					items.push(item = {
						code : data.columnLabel,
						name : data.title,
						mappingCode : data.mapping,
						mappingName : '',
						mappingType : type
					});
				});
			}

			//var mappings = D.GetData();
			/**
			 * 列映射
			 */
			PopulateItems(Columns, "column");

			/**
			 * 参数映射
			 */
			PopulateItems(Params, "param");

			$.ajax({
				data : {
					items : JSON.stringify(items),
					parentId : Columns.$tbody.data("parentId")
				},
				type : 'POST',
				url : '${contextPath}/mx/dataset/dataSetMappingItem/saveBatch',
				dataType : 'JSON',
				success : function(ret) {
					ret && ret.message && (alert(ret.message));
					var $parent = window.opener || window.parent;
					var fn = "${param.fn}";
					if($parent && $parent[fn]){
						$parent && $parent[fn] && $parent[fn](ret);
					}
				}
			});

		};

		func.instance = D;

		return func;
	})("${param.parentId}", "${param.dsmId}");

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
						} else if ($this.is("span") || $this.is("div")) {
							$this.text(data[name]);
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
			} else if ($this.is("span") || $this.is("div")) {
				val = $this.text();
			} else {
				val = $this.val();
			}
			data[name] = val;
		});
		return data;
	};
</script>

</html>