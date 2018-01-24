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
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab_1_1" data-toggle="tab"
				aria-expanded="true"> General </a></li>
			<li class=""><a href="#tab_1_2" data-toggle="tab"
				aria-expanded="false"> 映射属性 </a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane fade active in" id="tab_1_1">
				<div class="form-body" id="table-describe">
					<div class="form-group has-success">
						<label class="control-label">名称</label>
						<div class="input-icon right">
							<input type="text" class="form-control" name="name"
								value="${dataSet.name}" disabled />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label">标题</label>
						<div class="input-icon right">
							<input type="text" class="form-control" name="code"
								value="${dataSet.title}" disabled /> <input type="text"
								class="form-control" name="id" style="display: none;" />
						</div>
					</div>
					<div class="form-group has-warning">
						<label class="control-label">映射列表</label>
						<div class="input-icon right">
							<table id="mapping-list"
								class="goes-table table table-hover table-striped table-bordered table-condensed">
								<thead>
									<tr class="">
										<th colspan="5">
											<button class="btn btn-default btn-xs"
												onclick="TableDetails.AddRow(null, true)" title="添加映射数据集">
												<i class="fa fa-plus-square"></i>
											</button>
										</th>
									</tr>
									<tr class="">
										<th style="width: 6%">No.</th>
										<th>名称</th>
										<th>标题</th>
										<th>状态</th>
										<th style="width: 10%">OP</th>
									</tr>
								</thead>
								<tbody style="height: 266px;"></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="tab_1_2">
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
					<div class="col-sm-11" ></div>
					<div class="col-sm-1" >
						<button class="" onclick="Mapping.Save(this)">确定</button>
					</div>
				</div>
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

<script type="text/html" id="mapping-template">
<tr class="column-detail form-group-sm">
	<td style="display:none;">
		<input class="form-control" name="dsmId"/>
		<input class="form-control" name="id"/>
		<input class="form-control" name="parentId"/>
    </td> 
	<td style="width:6%">
		<span class="no"></span>
	</td> 
	<td><span class="" name="dsmName" ></span></td>
	<td><span class="" name="dsmName" ></span></td>
	<td>
		<input class="" type="checkbox" name="status" />
	</td>
	<td style="width:10%">
		<button class="btn btn-default btn-xs" onclick="GoesTable.RemoveRow(this)">-</button>
		<button class="btn btn-default btn-xs" onclick="Mapping.Set(this)">S</button>
	</td>
</tr>
</script>
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
	
	
	var GoesTable = (function(){
		var func = function(table, rowTemplate){
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
		
		func.prototype.clear = function(){
			this.$tbody.empty();
		};
		
		func.prototype.GetData = function(){
			var items = [];
			this.$tbody.find("tr").each(function(){
				items.push($(this).GetData());
			});
			return items;
		};
		
		return func;
	})();
	
	
var Mapping = (function(id){
		var func = new Function(),//
		D = new GoesTable("#mapping-list", //
				$("#mapping-template").html());
	
		var GetData = function(url, data, fn){
			$.ajax({
				data : data,
				url : url,
				type : 'POST',
				dataType : 'JSON',
				success : fn
			});
		}, GetMappings = function(id, fn){
			GetData('${contextPath}/mx/dataset/dataSetMapping/json', {
				parentId : id
			}, fn);
		}, GetLastJSON = function(id, fn){
			GetData("${contextPath}/rs/dataset/getLastestJson", {
				datasetId : id
			}, fn);
		}, lastJson = null;
		
		GetLastJSON(id, function(ret){
			lastJson = ret;
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
				$("#mapping-template-common").html()),
				Params = new GoesTable("#mapping-params", //
						$("#mapping-template-common").html());
		
		function AddTableRow(gt, collection){
			gt.clear();
			$.each(collection, function(i, v){
				gt.AddRow(v);
			});
		}
		
		func.Set = function(o){
			
			if(lastJson == null){
				return false;
			}
			
			var $tr = $(o).closest("tr");
			
			var data = $tr.GetData();
			
			/**
			 * 列映射
			 */
			AddTableRow(Columns, lastJson.selectSegments);
			
			/**
			 * 参数映射
			 */
			 
			 function populateParams(params){
				params = params || [];
				$.each(params, function(i, v){
					v.title = v.name;
					v.columnLabel = v.param;
				});
				return params;
			 }
			 
			lastJson.params = populateParams(lastJson.params);
			
			
			
			$.each(lastJson.params, function(i, v){
				v.title = v.name;
				v.columnLabel = v.param;
			});
			AddTableRow(Params, lastJson.params);
			
			function Set(gt, map, STR){
				gt.$tbody.find("tr").each(function(i, v){
					var $this = $(this); var data = $this.GetData();
					var value = map[data.columnLabel];
					$this.find("td select.form-control:eq(0)")//
						.append(STR).val(value ? value.mappingCode : "");
				});
			}
			
			var FIELDS = new StringBuffer("<option value=''></option>"),
			PARAMS = new StringBuffer("<option value=''></option>");
			
			/**
			 * 映射数据集的字段，参数
			 */
			GetLastJSON(data.dsmId, function(ret){
				if(ret && (ret.selectSegments)){
					$.each(ret.selectSegments, function(i, v){
						FIELDS.appendFormat("<option value='{1}'>{0}</option>", v.title, v.columnLabel);
					});
					Columns.$tbody.data("collection", ret.selectSegments);
				}
				
				if(ret && populateParams((ret.params))){
					$.each(ret.params, function(i, v){
						PARAMS.appendFormat("<option value='{1}'>{0}</option>", v.title, v.columnLabel);
					});
					Params.$tbody.data("collection", ret.params);
				}
				
				var SELECTSTR = FIELDS.toString(),
				PARAMSSELECTSTR = PARAMS.toString();
				
				GetData("${contextPath}/mx/dataset/dataSetMappingItem/json", {
					rows : 200,
					parentId : data.id
				}, function(ret){
					var map = {};
					if(ret && ret.rows){
						$.each(ret.rows, function(i, v){
							if(!v.mappingType || !v.code){
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
		func.SortByName = function(type){
			
			var gt = eval(type), //
			tmpSelectSegments = gt.$tbody.data("collection");
			
			if(!tmpSelectSegments || //
					!tmpSelectSegments.length){
				return false;
			}
			var NAMES = {};
			$.each(tmpSelectSegments, function(i, v){
				v.title && (NAMES[v.title] = v);
			});
			
			gt.$tbody.find("tr").each(function(){
				var $this = $(this);
				var data = $this.GetData();
				data.title && (data.title in NAMES) && //
					(data.mapping = NAMES[data.title].columnLabel);
				$this.LoadData(data);
			});
		};
		
		func.Save = function(){
			
			var items = [], item;
			
			function PopulateItems(gt, type){
				gt.$tbody.find("tr").each(function(){
					var $this = $(this);
					var data = $this.GetData();
					if(!data.mapping){
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
			
			var mappings = D.GetData();
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
					mappings : JSON.stringify(mappings),
					items : JSON.stringify(items),
					parentId : Columns.$tbody.data("parentId")
				},
				type : 'POST',
				url : '${contextPath}/mx/dataset/dataSetMappingItem/saveBatch',
				dataType : 'JSON',
				success : function(ret){
					ret && ret.message && (alert(ret.message));
				}
			});
			
		};

		func.instance = D;

		return func;
	})("${param.id}");

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

		var defaultRows = [ Row("主键ID", "ID_", "", 50, true),
				Row("TOPID", "TOPID_", "", 50, false),
				Row("PARENTID", "PARENTID_", "", 50, false),
				Row("层级代码", "TREEID_", "", 50, false),
				Row("创建人", "CREATEBY_", "", 50, false),
				Row("创建时间", "CREATEDATE_", "date", '', false),
				Row("修改人", "UPDATEBY_", "", 50, false),
				Row("修改时间", "UPDATEDATE_", "date", '', false),
				Row("排序号", "LISTNO_", "integer", '', false) ], SYSTEM = {};
		$.each(defaultRows, function(i, v) {
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
					if (codes[v.code]) {
						isOk = codes = false;
					}
					codes[v.code] = v.code;
				}
				if (!v.name) {
					names = true;
				} else {
					if (names[v.name]) {
						names = false;
					}
					names[v.name] = v.name;
				}
				data.columns.push(v);
			});
			$.extend(true, data, $("#query").GetData());

			if (names === true) {
				alert("字段Name不能为空!");
				isOk = false;
			} else if (names === false) {
				alert("字段Name不能重复!");
				isOk = false;
			} else if (codes === false) {
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
				if (data.id in SYSTEM) {
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