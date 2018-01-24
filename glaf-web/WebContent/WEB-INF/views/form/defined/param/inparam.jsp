<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/treegrid/css/jquery.treegrid.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/treegrid/js/jquery.treegrid.js"></script>
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

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.tree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

.groupConTree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

ul li {
	list-style-type: none;
}

.dom_line {
	margin: 2px;
	border-bottom: 1px gray dotted;
	height: 1px
}

.domBtnDiv {
	display: block;
	padding: 2px;
	border: 1px gray dotted;
	background-color: powderblue
}

.categoryDiv {
	display: inline-block;
	width: 335px
}

.domBtn {
	display: inline-block;
	cursor: pointer;
	padding: 2px;
	margin: 2px 10px;
	border: 1px gray solid;
	background-color: #FFE6B0
}

.domBtn_Disabled {
	display: inline-block;
	cursor: default;
	padding: 2px;
	margin: 2px 10px;
	border: 1px gray solid;
	background-color: #DFDFDF;
	color: #999999
}

.dom_tmp {
	position: absolute;
	font-size: 12px;
}

.active {
	background-color: #93C3CF
}

.td-cls {
	background-color: #FCFCFC;
	align: left;
	height: 16px;
}
</style>
<script type="text/javascript">
	var $groupConTree ;

	var fieldTypeControl = [];
	$.ajax({
		url : '${pageContext.request.contextPath }/mx/form/defined/getDictByCode',
		data : {
			code : 'FieldTypeControl'
		},
		type : 'post',
		dataType : 'json',
		async : false,
		success : function(data) {
			$.each(data, function(i, d) {
				var e = {};
				e.code = d.code;
				e.value = d.value
				fieldTypeControl.push(e);
			});
		}
	});

	//转换字段类型
	function getfieldTypeValue(fieldType) {
		var v = "";
		$.each(fieldTypeControl, function(i, d) {
			if (d.code == fieldType) {
				v = d.value;
			}
		})
		return v;
	}
	var contextPath = '${pageContext.request.contextPath}', mt = {
		each : function(obj, fn, collection) {
			this._each = function(o, f, c) {
				if (f) {
					var i, r;
					for (i in o) {
						r = f.call(c == undefined ? o[i] : c, i, o[i]);
						if (r === false) {
							break;
						} else if (r === true) {
							continue;
						}
					}
				}
				return c;
			};
			return this._each(obj, fn, collection);
		}

	}, eleId = '${param.eleId}', eleType = '${param.eleType}', paraTypeFunc = '${param.fn}', $parent = window.opener || window.parent;
	var dataSourceSetId = '${param.dataSourceSetId}',datasourceSet = window.parent.$('#' + dataSourceSetId).val();
	$.fn.extend({
		addNodes : function(nodes) {
			var $this = this;
			if (nodes && nodes.length > 0) {
				return $this.each(function() {
					var $t = $(this);
					$.each(nodes, function(i, node) {
						return $t.addNode(node);
					});
				});
			}
		},
		addNode : function(node) {
			var $this = this;
			if (!node.pId) {//根节点
				return $this.find('tr:first');
			} else {
				var pnode = $this.find('treegrid-' + node.pId);
				if (pnode[0]) {
					return createnode($this.find('treegrid-parent-' + node.id + ':last'), node);
				} else {
					return createnode($this.find('tr:last'), node);
				}
			}

			function createnode($tr, n) {
				var $node;
				if (n.isParent) {
					$node = $(kendo.format(templateComposit.join(''), n.id + " treegrid-parent-" + n.pId, n.name,"<button  class='delCon k-button' name='delCon2' type='button'>删除</button>"));
				} else {
					$node = $(kendo.format(templateChildren.join(''), n.id, n.pId, n.name));
				}
				$tr.after($node);

				initEvent($node);

				$this.treegrid();

				return $node;
			}
		}
	});

	var templateComposit = [ "<tr class='treegrid-{0} parent-cls'>", "<td>{1}</td>", "<td colspan='2'>", "<button class='addCon addChildCon k-button' name='addChildCon' type='button'>新增</button>", " <select class='linkCon'> </select>", "</td>", "<td>{2}</td>", "<td></td>", "</tr>" ];

	var templateChildren = [ "<tr class='treegrid-{0} treegrid-parent-{1} mytree' >", "<td>{2}</td>", "<td>", "<button class='addCon addSiblingCon k-button' name='addSiblingCon' type='button'>新增</button>", "</td>",

	"<td>", " <input type='text' class='k-textbox textbox-menu' name='field' />", " <ul class='conMenu' style='display:none;' ></ul>", "</td>",

	"<td>", " <select class='jCon' style='width: 80px;'> </select> ", "</td>", "<td>", " <input  type='text' class='k-textbox textbox-menu' name='param' ></input> ", " <ul class='conMenu' style='display:none;' ></ul>", "</td><td>", "<input type='checkbox'  value='1'/> 绑定 ",
			" <button  class='delCon k-button' name='delCon' type='button'>删除</button> ", " <button  class='upCon k-button' name='upCon' type='button'>转换为复合条件</button> ", "</td>", "</tr>" ];

	var templateCompositStr = kendo.format(templateComposit.join(''), 1, 1, "");

	var outParamDataSource = [ {
		title : '参数一',
		columnName : 'col1'
	}, {
		title : '参数二',
		columnName : 'col2'
	} ];
	var inParamDataSource = new Array();

	function increateInParam() {
		var len = inParamDataSource.length;
		inParamDataSource.push({
			name : '参数' + (len + 1),
			param : 'col' + (new Date().getTime())
		});
	}

	//根据数据生成 ul >> li
	function createMenu(items, $meun) {
		var $ul, $li;
		$.each(items, function(i, item) {
			$li = $("<li>").text(item.text);
			if (item.items) {
				$ul = $("<ul>");
				$li.append($ul);
				createMenu(item.items, $ul);
			}
			$li.data("item", item);
			$meun.append($li);
		});
	}

	//获取菜单数据
	function getMenuData() {

		var menu = {
			text : "选择列",
			items : [],
			level : 0,
			leaf : false
		}, obj;

		/* var colTbGrid = $("#colTb").data("kendoGrid");
		var dataSource = colTbGrid.dataSource.data();
		var colTbtTmp = new Object(), key;
		if (dataSource && dataSource[0]) {
			$.each(dataSource, function(i, row) {
				key = row.tableName + "_" + row.columnName;
				colTbtTmp[key] = row;
			});
		}

		$(".tbl-cls").each(function(i, table) {
			$this = $(this);
			var tableName = $this.attr("tableName");
			var nameCN = $this.attr("nameCN");
			var bd = $this.data("dataSource");
			obj = {
				text : nameCN,
				value : tableName,
				items : [],
				leaf : false
			};

			$this.find("tr").each(function(index, $tr) {
				var fieldjson = $(this).attr("fieldjson");
				if (fieldjson) {
					var row = $.parseJSON(fieldjson);

					row.tableName = tableName;
					row.tableNameCN = nameCN;
					row.columnName = row.dname;
					key = row.tableName + "_" + row.dname;
					if (key in colTbtTmp) {
						row.title = colTbtTmp[key].title || row.fname;
					}
					row.bd = bd;
					obj.items.push({
						text : row.title || row.fname,
						value : row.dname,
						item : row,
						leaf : true
					});
				}
			});
			menu.items.push(obj);
		}); */
		return [ {
			text : "表达式",
			level : 1,
			leaf : false
		} /* , menu */];
	}

	//子页面表达式获取数据
	function getRowTree() {
		var data = [ {
			id : 1,
			pId : 0,
			name : "输入形参",
			open : "false",
			isParent : true
		}, {
			id : 2,
			pId : 0,
			name : "字段",
			open : "false",
			isParent : true
		} ];
		//获取参数
		var inParamDefinedId = '${param.inParamDefinedId}';
		var formalParams = window.parent.$('#' + inParamDefinedId).val();
		if (formalParams) {
			var formalParamsArray = JSON.parse(formalParams);
			if(formalParamsArray && formalParamsArray.length){
				var formalParamsObj = formalParamsArray[0];
				if(formalParamsObj && formalParamsObj["type"]){
					formalParamsArray = formalParamsObj["source"];
				}
				for (var i = 0; i < formalParamsArray.length; i++) {
						var sr = formalParamsArray[i];
						var param = {};
						param.t = sr.name;
						param.dType = "string";
						param.code = "~F{" + sr.param + "." + sr.param + "." + sr.param + "}";
						param.value = "~F{" + sr.name + "}";
						param.param = sr.param;
						param.name = sr.name
						param.pId = 1;
						data.push(param);
					}
			}
		}
		//获取字段
		if (datasourceSet) {
			var datasourceSetObj = JSON.parse(datasourceSet);
			if("columns" in datasourceSetObj[0]){
				var columns = datasourceSetObj[0].columns;
				for (var i = 0; i < columns.length; i++) {
					var express = {};
					var selectFieldRow = columns[i];
					express.t = selectFieldRow.title;
					express.dType = getfieldTypeValue(selectFieldRow.FieldType);
					express.datasetId = selectFieldRow.datasetId;
					express.code = selectFieldRow.code;
					express.value = selectFieldRow.value;
					express.name = selectFieldRow.tableNameCn + "." + selectFieldRow.title;
					express.pId = 2;
					data.push(express);
				}
			}else if("selectDatasource" in datasourceSetObj[0]){
				var columns =datasourceSetObj[0].columns || datasourceSetObj[0].selectColumns ;
				for (var j = 0; j < columns.length; j++) {
					var express = {};
					var selectFieldRow = columns[j];
					express.t = selectFieldRow.title;
					express.dType = getfieldTypeValue(selectFieldRow.FieldType);
					express.datasetId = selectFieldRow.datasetId;
					express.code = selectFieldRow.code;
					express.value = selectFieldRow.value;
					express.name = selectFieldRow.tableNameCn + "." + selectFieldRow.title;
					express.pId = 2;
					data.push(express);
				}
			}
		}
		return data;
	}
	//初始化 表达式
	function getinitExp() {
//		var textbox = $("#groupConTree").data("textbox");
		var textbox = $groupConTree.data("textbox");
		var item = textbox.data("item");
		if (item) {
			return JSON.parse(item);
		}
		return null;
	}
	//表达是页面回调函数
	function setRowField(d) {
		if (d) {
			//var textbox = $("#groupConTree").data("textbox");
			var textbox = $groupConTree.data("textbox");

			//var textbox = $menu.prev('.textbox-menu');

			var data = $.extend({}, d);

			if (textbox && textbox[0]) {
				textbox.show().val(data.expVal).data("item", JSON.stringify(data));
				//$menu.hide();
			}
		}

	}
</script>
</head>
<body>
	<div id="head" class="k-header k-footer footerCss">
		<table style="width: 100%;">
			<tr>
				<td style="width: 500px; text-align: left; vertical-align: middle;"><img
					src="${pageContext.request.contextPath}/images/flow.png" style="vertical-align: middle;" /> <span
					style="font-size: 16px; font-weight: bolder;">输入表达式定义</span></td>
				<td style="text-align: right;">
					<button id="button2" type="button">确定</button>
				</td>
			</tr>
		</table>
	</div>
	<div style="height: 555px; width: 100%; overflow-y: auto; overflow-x: hidden;">
		<div id="tabstrip"> </div>
		
	</div>
	<script type="text/javascript">
		var nameElementId = '${param.nameElementId}';
		var objelementId = '${param.objelementId}';
		var tabStrip = $("#tabstrip").kendoTabStrip().data("kendoTabStrip");
		var tabstripId  ;
		if(datasourceSet){
			var datasourceSetObj = JSON.parse(datasourceSet);
			if(datasourceSetObj){
				var ds = datasourceSetObj[0].datasource || datasourceSetObj[0].selectDatasource || datasourceSetObj ;
				var sourceLength = ds.length ;
				if(sourceLength){
					for(var i=0;i<sourceLength;i++){
						var d = ds[i];
						tabstripId = (d.dataSetId||d.id||d.datasetId) ;
						tabStrip.append([{
				            text: d.name,
				            content: "<table id='"+tabstripId+"' class='tableCon groupConTree' style='border-spacing: 0px;'></table>"
				        }]);
						$("#"+tabstripId).append(templateCompositStr).treegrid();
						//$("#groupConTree").append(templateCompositStr).treegrid();
					}
					tabStrip.select(0);
				}
			}
		}
		//初始化确认按钮
		$("#button2").kendoButton({
			imageUrl : "${pageContext.request.contextPath}/images/okay.png",
			click : function(e) {
				var retArray = [],names = "" , isOk = true ;
				$(".groupConTree").each(function() {
					var nodes = $(this).treegrid("getAllNodes"),tabId = $(this).attr("id"), $this, collection = [], collectionTree = [], retName = "";
					//清除颜色
					$(this).find("tr input").css({
						"background-color" : ''
					});
					if (nodes && nodes.length > 1) {
						//检查有无下级节点
						function hasChildren($tr) {
							var children = $tr.treegrid('getChildNodes'), node;
							if (children.length > 0) {
								for (var i = 0; i < children.length; i++) {
									if (!$(children[i]).hasClass("parent-cls")) {
										return true;
									}
								}
							}
							return false;
						}
						//循环树节点
						for (var i = 0; i < nodes.length; i++) {
							$this = $(nodes[i]), //单个节点
							id = $this.treegrid("getNodeId"),//当前节点的nodeid
							pId = $this.treegrid("getParentNodeId");//当前节点的父id

							//节点对象
							var treeObject = {
								name : $this.find("td:first").text(),//第一个tr的值   例如：1.1  
								id : id,
								pId : pId,
								isParent : $this.hasClass("parent-cls")
							//是否为父节点
							};

							//如果不是叶子节点，且是父节点
							if (!$this.treegrid("isLeaf") || $this.hasClass("parent-cls")) {

								//找到当前节点的 （全部符合）
								var dataItem = $this.find("select").data("kendoDropDownList").dataItem();
								if (dataItem) {
									treeObject.connector = dataItem.value;
								}
								//判断有无下级节点  如果有返回true
								if (hasChildren($this)) {
									collectionTree.push(treeObject);
								}
								continue;
							}

							//如果是子节点
							collectionTree.push(treeObject);
							var job = {
								parentId : pId,
								ordinal : id
							/* ,
															datasetId : dataSet.id */
							}, $connector = $this.treegrid("getParentNode");

							//获取2个表达式的参数值
							var fieldInput = $this.find("input[name=field]"), fieldItem = fieldInput.data("item"), paramInput = $this.find("input[name=param]"), paramItem = paramInput.data("item");

							//空值 改变颜色
							if (!fieldInput.val()) {
								fieldInput.css({
									"background-color" : 'red'
								});
								isOk = false;
							}

							if (!paramInput.val()) {
								paramInput.css({
									"background-color" : 'red'
								});
								isOk = false;
							}

							if (!isOk) {
								continue;
							}

							//遍历
							if (fieldItem) {
								job.fieldVal = fieldInput.val(); //显示表达式值
								var linkConItem = $connector.find("select").data("kendoDropDownList").dataItem();
								if (linkConItem)//父节点连接条件
									job.connector = linkConItem.value;//连接条件（AND或OR）
								if (fieldItem) {//表达式隐藏回显
									job.fieldData = fieldItem;
								}
								retName += fieldInput.val(); //用于回显的名称
							} else {

							}
							var operator = $this.find("select[class=jCon]").data("kendoDropDownList").dataItem();//操作符号 大于 小于
							job.operator = operator.value;

							retName += " " + operator.text + " ";

							if (paramItem) {
								job.paramVal = paramInput.val();
								if (paramItem) {
									job.paramData = paramItem;
								}
								retName += paramInput.val() + ",";
							} else {

							}

							//绑定参数
							job.dynamic = $this.find("input[type=checkbox]").is(":checked") ? "Y" : "N";

							collection.push(job);
						}

						if (!isOk) {
							//$("#tabstrip").data("kendoTabStrip").select("li:eq(1)");;
							alert('请完成高亮部分');
							return false;
						}
					}
					//返回保存
					var ret = {
						tabId  : tabId ,
						name : retName,
						collection : collection,
						collectionTree : collectionTree
					}
					names += retName ; 
					retArray.push(ret);
				});
				if(!isOk){
					return ;
				}
				window.parent.$('#' + nameElementId).val(names);
				window.parent.$('#' + objelementId).val(JSON.stringify(retArray));
				parent.layer.close(parent.layer.getFrameIndex());
			}
		});

		//$("#groupConTree").append(templateCompositStr).treegrid();

		var treeFuncs = {
			addChildCon : function($tr) {
				treeFuncs.addSiblingCon.call(this, $tr, true);
			},
			addSiblingCon : function($tr, isChild) {

				var tree = $tr.parent();

				var parentNode = $tr.treegrid("getParentNode");
				if (isChild)
					parentNode = $tr;

				if (!tree.data("childId")) {
					tree.data("childId",tree.find('tr').length);
				}

				var id = tree.data("childId") * 1 + 1;

				tree.data("childId", id);

				var pid = parentNode.treegrid("getNodeId");

				var name = parentNode.find(">td:first").text() + "." + (parentNode.treegrid("getChildNodes").length + 1);

				var last = tree.find(".treegrid-parent-" + pid + ":last");

				var node = $(kendo.format(templateChildren.join(''), id, pid, name)).attr({
					level : name
				});
				
				if(last[0]){
					function findLast(last){
						var childNodes = last.treegrid('getChildNodes');
						if(childNodes && childNodes.length > 0){
							var tid = last.treegrid('getNodeId');
							last = findLast(tree.find(".treegrid-parent-" + tid + ":last"));
						}
						return last;
					}
					 last = findLast(last);
				}

				last = last[0] ? last : $tr;

				last.after(node);

				initEvent(node);

				tree.treegrid();

			},
			delCon : function($tr) {

				var tree = $tr.parent();

				var parentNode = $tr.treegrid("getParentNode");

				var pid = parentNode.treegrid("getNodeId");

				var parentName = parentNode.find(">td:first").text();

				tree.find("tr[level^='" + $tr.find(">td:first").text() + "']").each(function() {
					$(this).remove();
				});

				if ($tr[0])
					$tr.remove();

				tree.find(".treegrid-parent-" + pid).each(function(i, t) {
					$(this).find(">td:first").text(parentName + "." + (i + 1));
				});
				tree.treegrid();

			},
			upCon : function($tr) {

				var tree = $tr.parent();

				var id = $tr.treegrid("getNodeId");

				var name = $tr.find(">td:first").text();

				$tr.find(">td:first").text(name + ".1");

				var parentNode = $tr.treegrid("getParentNode");

				var pid = parentNode.treegrid("getNodeId");

				var node = $(kendo.format(templateComposit.join(''), id, name, "<button  class='delCon k-button' name='delCon2' type='button'>删除</button>"));

				node.removeAttr("class").attr({
					'class' : $tr.attr('class')+" parent-cls",
					level : name
				});

				var tId = tree.data("childId") * 1 + 1;

				tree.data("childId", tId);

				$tr.removeClass("treegrid-parent-" + pid).addClass("treegrid-parent-" + id);

				$tr.removeClass("treegrid-" + id).addClass("treegrid-" + tId);

				$tr.attr({
					level : $tr.find(">td:first").text()
				}).before(node);

				initEvent(node);

				tree.treegrid();
			}
		};

		var datas = new Array(), menu = {
			text : "表达式定义",
			level : 1,
			leaf : false
		/* ,
					items : [] */
		};

		$.ajax({
			url : contextPath + '/mx/form/defined/getParametersByPageId',
			data : {
				pageId : '${param.pageId}'
			},
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data) {
					mt.each(data, function(i, v) {

						if (v.NAME_ != eleType && v.PARAMTYPE_ == 'outParameters' && v.VALUE_) {
							if (v.VALUE_) {
								var items = mt.each(JSON.parse(v.VALUE_), function(i, p) {
									p.text = p.name;
									this.push(p);
								}, new Array());

								menu.items.push({
									text : v.TITLE_ || v.NAME_,
									items : items
								});
							}

						} else if (v.NAME_ == eleType && v.PARAMTYPE_ == 'inParameters') {
							//当前 控件 的输入参数
						}

					});
				}
			}
		});

		var temp = {};
		if ($parent[paraTypeFunc]) {//输入参数(已经配置)
			var str = $parent[paraTypeFunc]();
			if (str) {
				try {
					var arr = JSON.parse(str);
					if (arr) {
						mt.each(arr, function(i, v) {
							temp[v.param] = v;
							this.push(v);
						}, inParamDataSource);
					}
				} catch (e) {

				}
			}
		}

		var ele = $parent.document.getElementById(eleId);
		if (ele) {
			var str = $(ele).val();
			if (str) {
				try {
					var json = JSON.parse(str);
					if (json) {
						var tree = json.tree;
						$("#groupConTree").addNodes(tree);
					}
				} catch (e) {

				}
			}

		}

		function initEvent(jq) {
			jq.find(".addCon").kendoButton({
				imageUrl : "${pageContext.request.contextPath}/images/bullet_add.png",
				click : function(e) {
					var target = $(e.event.target), name = target.attr('name');
					if (name && (name in treeFuncs)) {
						treeFuncs[name].call(target, target.parents("tr"));
					}
				}
			});

			jq.find(".delCon").kendoButton({
				imageUrl : "${pageContext.request.contextPath}/images/bullet_delete.png",
				click : function(e) {
					var target = $(e.event.target);
					treeFuncs['delCon'].call(target, target.closest("tr"));
				}
			});

			jq.find(".upCon").kendoButton({
				imageUrl : "${pageContext.request.contextPath}/images/arrow_small_up.png",
				click : function(e) {
					var target = $(e.event.target);
					treeFuncs['upCon'].call(target, target.parents("tr"));
				}
			});

			jq.find(".fieldComB").kendoDropDownList({
				dataTextField : "name",
				dataValueField : "param",
				dataSource : inParamDataSource,
				animation : false
			});

			jq.find(".linkCon").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : [ {
					text : '全部符合',
					value : 'All'
				}, {
					text : '任意符合',
					value : 'Any'
				}, {
					text : '无',
					value : 'None'
				}, {
					text : '排除全部',
					value : 'Not All'
				}, ]
			});

			jq.find(".jCon").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : [ {
					text : '等于',
					value : '='
				}, {
					text : '不等于',
					value : '<>'
				}, {
					text : '大于',
					value : '>'
				}, {
					text : '小于',
					value : '<'
				}, { 
					text : '大于等于',
					value : '>='
				}, {
					text : '小于等于',
					value : '<='
				}, {
					text : '包含',
					value : 'like'
				}, {
					text : '左包含',
					value : 'leftLike'
				},{
					text : '右包含',
					value : 'rightLike'
				},{
					text : '不包含',
					value : 'not like'
				},]
			});

			initMenuEvent(jq);
		}

		function initMenuEvent(jq) {//下拉菜单

			jq.find(".textbox-menu").on('dblclick', function() { // 弹出HTML表达式定义
				var $this = $(this);
				$groupConTree = $this.parents(".tableCon.groupConTree");
				//直接双击弹出页面
				//$("#groupConTree").data("textbox", $this);
				$groupConTree.data("textbox", $this);
				var expURL = contextPath + "/mx/expression/defined/view?" + $.param({
					retFn : "setRowField",
					getFn : "getRowTree",
					initExpFn : "getinitExp",
					category : 'db'
				});
				window.open(expURL);
				/* $this.next('.conMenu').show();
				$this.hide(); */
			}).each(function() {
				$(this).attr({
					readonly : true
				});
			});

			var menus = [ menu ] /* getMenuData() */;

			jq.find(".conMenu").each(function() {
				var $this = $(this).empty();

				createMenu(menus, $this);

				$this.kendoMenu({
					select : function(e) {
						var $this = $(e.item).parents("ul");
						var textbox = $this.prev('.textbox-menu');
						var item = $(e.item).data("item");
						if (item) {
							if (item.level == 1) {//表達式
//								$("#groupConTree").data("menu", $this);
								$groupConTree.data("menu", $this);
								var expURL = contextPath + "/mx/expression/defined/view?" + $.param({
									retFn : "setRowField",
									getFn : "getRowTree",
									initExpFn : "getinitExp",
									category : 'db'
								});
								window.open(expURL);
							} else if (!item.items) {//列
								/* $this.hide();
								textbox.val(item.text).data("item",item);
								textbox.show();
								
								if($(".textbox-menu").length > inParamDataSource.length){
									increateInParam();
									var dataSource =  new kendo.data.DataSource({
										  data:inParamDataSource
									});
									$("#groupConTree").find(".fieldComB").each(function(){
										var k = $(this).data("kendoDropDownList");
										if(k)
											k.setDataSource(dataSource);
									});
								} */

							}
						}
					},
					close : function(e) {
						var $li = $(e.item);
						var item = $li.data("item");
						if (item.level == 0) {
							var $ul = $li.parents("ul").hide();
							$ul.prev('.textbox-menu').show();
						}
					}
				});
			});
		}

		initEvent($(".tableCon"));
		
		//初始化树
		function initWhereHaving(collection,collectionTree,$tree){
			if(collectionTree){
				$tree.addNodes(collectionTree);
			}
			var $this;
			$.each(collection,function(i,item){
				//$this = treeFuncs.addChildCon($tr);
				$this = $tree.find(".treegrid-" + item.ordinal);
				
				var parentNode = $this.treegrid("getParentNode");
				
				if(parentNode[0] && !parentNode.data("init")){
				//$.log(item);
					parentNode.find("select[class=linkCon]").data("kendoDropDownList").value(item.connector);
					parentNode.data("init",true);
				}
				
				var fieldInput = $this.find("input[name=field]"),
				paramInput = $this.find("input[name=param]"),
				dynamicChk = $this.find("input[type=checkbox]"),
				operatorDrop =$this.find("select[class=jCon]").data("kendoDropDownList"); 
				
				if(fieldInput[0] && paramInput[0]){
					fieldInput.val(item.fieldVal).data("item",item.fieldData);
					paramInput.val(item.paramVal).data("item",item.paramData);
					operatorDrop.value(item.operator);
					if(dynamicChk[0])
						dynamicChk[0].checked = item.dynamic == "Y";
					
				}
			});
		}
		
		// 恢复数据
		var vv = window.parent.$('#' + objelementId).val() ;
		if(vv){
			var initObj =  JSON.parse(vv) , initObjLength = initObj.length;
			if(initObj){
				for(var i=0;i<initObjLength;i++){
					var obj = initObj[i];
					var collection = obj.collection ,
					collectionTree = obj.collectionTree;
					if(initObjLength==1 && !obj.tabId){
						initWhereHaving(collection,collectionTree,$("#"+tabstripId));
					}else{
						initWhereHaving(collection,collectionTree,$("#"+obj.tabId));
					}
				}
			}
		}
		
	</script>
</body>
</html>