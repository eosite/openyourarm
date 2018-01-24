<%@page import="com.glaf.core.util.UUID32"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	request.setAttribute("isSystemAdministrator",
			RequestUtils.getLoginContext(request).isSystemAdministrator());
	request.setAttribute("uuid", UUID32.getUUID());
%>
<div id="params-${uuid}" style="width: 100%; height: 99%;"></div>
<style>
#params-${uuid} tbody button.k-button {
	min-width: 28px;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/webfile/js/checkRelation.js"></script>
<script type="text/javascript">
	
	
	/**
	 *固定参数
	 */
	var ParamTools = window.ParamTools || [ {
		name : '来源节点父ID',
		id : 'fpid'
	} ];

	var MtDataSource = (function() {
		var dataSource = {
			paramTypes : [ {
				name : '',
				paramType : ''
			}, {
				name : '字符串',
				paramType : 'string'
			}, {
				name : '整型',
				paramType : 'integer'
			}, {
				name : '浮点型',
				paramType : 'double'
			}, {
				name : '日期',
				paramType : 'date'
			} ]
		};

		var func = function() {
		};
		func.add = function(key, obj) {
			!dataSource[key] && (dataSource[key] = []);
			dataSource[key].push(obj);
		};

		func.getArray = function(key) {
			return dataSource[key] || [];
		};

		func.getObjCached = function(key, rowKey) {
			var okey = key + "_obj";
			!dataSource[okey] && (dataSource[okey] = //
			func.getObj(key, rowKey));
			return dataSource[okey];
		};

		func.getObj = function(key, rowKey) {
			var obj = {};
			if (dataSource[key]) {
				$.each(dataSource[key], function(i, v) {
					v[rowKey] && (obj[v[rowKey]] = v);
				});
			}
			return obj;
		};

		return func;
	})();

	function delParam(o) {
		function callback(){
			MtParams.delParam(o);
		}
		var dataItem = MtParams.getDataItemByBtn(o);
		if(MtParams.checkIsOriginal(dataItem)){
			checkParamRelation({
				param:JSON.stringify({param:dataItem.param})
			},callback);
		}else{
			return callback();
		}
	}

	var MtParams = (function(uuid, $) {

		var SB = [];
		$.each(ParamTools, function(i, v) {
			SB.push("<button class='k-button params-${uuid}-createNew' t='");
			SB.push(v.id || "");
			SB.push("' >");
			SB.push(v.name);
			SB.push("</button>");
		});

		var options = {
			editable : true,
			selectable : true,
			toolbar : "<button class='k-button params-${uuid}-createNew'>增加</button>"
					+ SB.join(""),
			columns : [
					{
						field : 'name',
						title : '参数名',
						editor : function(container, options) {
							MtColumns.editors.textbox(container, options);
						}
					},
					{
						field : 'expression',
						title : '表达式组合',
						editor : function(container, options) {
							MtColumns.editors.textbox(container, options).attr(
									{
										t : options.model.uid,
										readonly : true
									}).on('dblclick', function(e) {
								//这个方法需要主界面提供
								openExpress(this);// TODO
							});
						}
					},
					{
						field : 'defVal',
						title : '默认值'
					},
					{
						field : 'param',
						title : '参数',
						editor : function(container, options) {
							MtColumns.editors.textbox(container, options).attr(
									"${isSystemAdministrator}" == "true"//
									? "read" : "readonly", true);
						}
					},
					{
						field : "paramType",
						title : '参数类型',
						template : function(dataItem) {
							var field = "paramType";
							var dataSource = MtDataSource//
							.getObjCached("paramTypes", field);
							return dataSource[dataItem[field]] ? //
							dataSource[dataItem[field]].name : "";
						},
						editor : function(container, options) {
							var s = MtColumns.editors.stuff(container), //
							opts = {
								dataSource : MtDataSource
										.getArray("paramTypes"),
								dataValueField : 'paramType',
								dataTextField : 'name',
								change : function(e) {
									var row = MtColumns.editors.row(s);
									row[options.field] = e.sender.value();
								}
							};
							MtColumns.editors.dropdownlist(container, options,
									opts);
						}
					},
					{
						field : "prepared",
						title : "预编译",
						width : "60px",
						template : function(dataItem) {
							return MtColumns.checkboxTemplate(dataItem,
									'prepared');
						}
					},
					{
						title : "操作",
						width : "220px",
						template : function() {
							return "<button class='k-button'  onclick='delParam(this)' >X</button>"
									+ "<button class='k-button' onclick='MtParams.up(this)' >↑</button>"
									+ "<button class='k-button' onclick='MtParams.down(this)' >↓</button>";
						}
					} ]
		}

		var $grid = $('#params-' + uuid).kendoGrid(options), //
		grid = $grid.data('kendoGrid');

		var func = function() {
		};

		function exchange(index, n) {
			var data = func.getParamsData();
			var params = [];
			$.each(data, function(i, v) {
				params.push($.extend(true, {}, v));
			});
			var item = params[index];
			params[index] = params[index + n];
			params[index + n] = item;
			func.initParams(params);
		}

		func.up = function(o) {
			var $tr = $(o).closest("tr"), index;
			if ((index = $tr.index()) > 0) {
				exchange(index, -1);
			}
		};

		func.down = function(o) {
			var $tr = $(o).closest("tr"), index, len;
			var data = func.getParamsData();
			len = data.length;
			if ((index = $tr.index()) < len - 1) {
				exchange(index, 1);
			}
		};

		func.delParam = function(o) {
			grid.removeRow(o);
		};
		// 原始参数  即每次打开页面的时候 本身有的参数
		var originalDatas = [];
		func.initParams = function(datas) {
			if (datas) {
				grid.setDataSource(new kendo.data.DataSource({
					data : datas
				}));
				originalDatas = datas;
			} else {
				var i = 0, dataItems = grid.dataItems(), item;
				if (dataItems) {
					var hasValues = [], ret = [];
					var times = (new Date().getTime());
					for (; i < dataItems.length; i++) {
						item = dataItems[i];
						if (!item.name) {
							alert('参数名不能为空');
							return false;
						} else {
							if (hasValues.indexOf(item.name) >= 0) {
								alert("存在重复参数名，请检查");
								return;
							} else {
								hasValues.push(item.name);
								if (!item.param) {
									item.param = 'col' + (times + i);
								}
								ret.push(item);
							}
						}
					}
					return ret;
				}
				return false;
			}
		};
		//保存一份原始的参数
		func.getOriginalParams = function(){
			return originalDatas;
		};
		func.getDataItemByBtn = function(o){
			var $tr = $(o).closest('tr');
			return grid.dataItems()[$tr.index()];
		};
		//检查是否为原始参数
		func.checkIsOriginal = function(row){
			var flag = false;
			$.each(originalDatas, function(index, odata) {
				if(odata.param == row.param){
					flag = true;
					return false;
				}
			});
			return flag;
		};
		func.getParamsData = function() {
			return grid.dataSource.data();
		};
		var times = null;
		func.getTime = function(){
			!times && (times = new Date().getTime());
			return times ++;
		};

		func.addParams = function(params) {
			var times = func.getTime();
			!params && (params = {
				name : '参数-' + times,
				param : "col" + times,
				prepared : true
			});

			if (!(params instanceof Array)) {
				params = [ params ];
			}
			var dataItems = grid.dataItems();
			var map = {};
			if (dataItems.length) {
				for (var i = 0; i < dataItems.length; i++) {
					var data = dataItems[i];
					map[data.param] = data;
				}
			}

			for (var i = 0; i < params.length; i++) {
				var data = params[i];
				if (!(map[data.param])) {
					grid.dataSource.insert(data);
				}
			}
			grid.refresh();
		};

		return func;

	})("${uuid}", jQuery);

	function initParams(datas) {
		return MtParams.initParams(datas);
	}

	$(".params-${uuid}-createNew").click(function(e) {
		var $this = $(this), t = $this.attr("t");
		MtParams.addParams(!!t ? {
			name : $this.text(),
			param : t,
			prepared : true
		} : null);
	});

	function getParamsData() {
		return MtParams.getParamsData();
	}

	function addParams(params) {
		MtParams.addParams(params);
	}

	var MtColumns = (function(uuid, $) {

		var func = function() {
		};

		var editors = {
			stuff : function(container) {
				var $tr = container.closest('tr');
				return {
					tr : $tr,
					index : $tr.index(),
					grid : $tr.closest('[data-role=grid]').data('kendoGrid')
				};
			},
			row : function(s) {
				return s.grid.dataSource.data()[s.index];
			},
			textbox : function(container, options) {
				var s = editors.stuff(container), $input = $("<input/>", {
					name : options.field,
					class : 'k-textbox'
				}).appendTo(container).change(function(e) {
					var row = editors.row(s);
					row[options.field] = $(this).val();
				});
				return $input;
			},
			dropdownlist : function(container, options, dropdownlist) {
				var s = editors.stuff(container), //
				$input = $("<input/>", {
					name : options.field
				}).appendTo(container);

				var opts = $.extend(true, {
					change : function(e) {
						var row = editors.row(s);
						row[options.field] = e.sender.value();
					}
				}, dropdownlist);
				$input.kendoDropDownList(opts);
				return $input;

			},
			checkbox : function(container, options) {
				var $input = $("<input/>", {
					name : options.field,
					type : 'checkbox'
				}).appendTo(container);
				return $input;
			}
		};

		func.editors = editors;

		func.checkboxTemplate = function(dataItem, key) {
			var opt = {
				type : 'checkbox',
				onclick : "MtColumns.clickCheckbox(this)",
				name : key
			};
			if (dataItem[key] == true || dataItem[key] == 'true')
				opt.checked = true;
			return $("<div>").append($("<input />", opt)).html();
		};

		func.clickCheckbox = function(o) {
			var $this = $(o), row = editors.row(editors.stuff($this));
			row[$this.attr("name")] = o.checked;
		};

		return func;

	})("${uuid}", jQuery);
</script>