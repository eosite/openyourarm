/**
 * where 条件独立js
 */
window.$queue = window.$queue || $.Callbacks("zygs.fzmt.whereClaus");
$.fn.extend({
	_addNodes : function(nodes) {
		var $this = this;
		if (nodes && nodes.length > 0) {
			return $this.each(function() {
				var $t = $(this);
				$.each(nodes, function(i, node) {
					return $t._addNode(node);
				});
			});
		}
	},
	_addNode : function(node) {
	//	var $this = this;
		var $this = this, $tbody = $this.find("tbody");
		var id_ = $tbody.data("childId") || 0;
		if (!node.pId) {// 根节点
			return $this.find('tr:first');
		} else {
			var pnode = $this.find('treegrid-' + node.pId);
			if (pnode[0]) {
				return createnode($this.find('treegrid-parent-' + node.id
						+ ':last'), node);
			} else {
				return createnode($this.find('tr:last'), node);
			}
		}

		function createnode($tr, n) {
			var $node;
			if (n.isParent) {
				$node = $(whereClaus.getCompositTemp().format(
						n.id + " treegrid-parent-" + n.pId, n.name));
			} else {
				$node = $(whereClaus.getChildrenTemp().format(n.id, n.pId,
						n.name));
			}
			$tr.after($node);

			whereClaus.initEvent($node);
			$this.treegrid();
			$tbody.data("childId", Math.max( id_, n.id * 1 ));
			return $node;
		}
	}
});
var whereClaus = (function() {
	var templateComposit = [
			"<tr class='treegrid-{0} parent-cls'>",
			"<td>{1}</td>",
			"<td colspan=''>",
		//	"<button class='addCon addChildCon k-button' name='addChildCon' type='button'><img alt='icon' class='k-image' src='"+contextPath+"/images/bullet_add.png'>新增</button> ",
			"<button class='addCon addChildCon k-button' name='addChildCon' type='button' title='新增'><img alt='icon' class='k-image' src='"+contextPath+"/images/bullet_add.png'></button> ",
			
			"</td>",
			"<td>",
			"  <select class='linkCon'> </select>", 
			"</td>",
			"<td></td>", "</tr>" ].join("");

	var templateChildren = [
			"<tr class='treegrid-{0} treegrid-parent-{1}' >",
			"<td>{2}</td>",
			"<td>",
		//	"<button class='addCon addSiblingCon k-button' name='addSiblingCon' type='button'><img alt='icon' class='k-image' src='"+contextPath+"/images/bullet_add.png'>新增</button>",
			"<button class='addCon addSiblingCon k-button' name='addSiblingCon' type='button' title='新增'><img alt='icon' class='k-image' src='"+contextPath+"/images/bullet_add.png'></button>",
			"</td>",

			"<td>",
			" <input type='text' class='k-textbox textbox-menu' name='field' readonly=true />",
			" <ul class='conMenu' style='display:none;' ></ul>",
			"</td>",
			"<td>",
			" <select class='jCon' style='width: 80px;'> </select> ",
			"</td>",
			"<td>",
			" <input type='text' class='k-textbox textbox-menu' name='param' readonly=true />",

			" <ul class='conMenu' style='display:none;' ></ul>",
			"</td>",

			"<td>",
		//	"<label style='' > 动态 <input  class='chk-cls' name='dynamic' type='checkbox'></input> </label>",
		//	" <button  class='delCon k-button' name='delCon' type='button'><img alt='icon' class='k-image' src='"+contextPath+"/images/bullet_delete.png'>删除</button> ",
		//	" <button  class='upCon k-button' name='upCon' type='button'><img alt='icon' class='k-image' src='"+contextPath+"/images/arrow_small_up.png'>转换为复合条件</button> ",
			" <button  class='delCon k-button' name='delCon' type='button' title='删除'><img alt='icon' class='k-image' src='"+contextPath+"/images/bullet_delete.png'></button> ",
			" <button  class='upCon k-button' name='upCon' type='button' title='转换为复合条件' ><img alt='icon' class='k-image' src='"+contextPath+"/images/arrow_small_up.png'></button> ",
			"</td>", "</tr>" ].join("");

	var treeFuncs = {

		addChildCon : function($tr) {
			return treeFuncs.addSiblingCon.call(this, $tr, true);
		},
		
		getChildId : function(tree){
			if(!tree.data("childId")){
				var l = tree.find('tr:last'),$id = 0,len = tree.find('tr').length;
				if(l[0]){
					$id = l.treegrid("getNodeId");
				}
				tree.data("childId",$id > len ? $id : len);
			}
			var id = tree.data("childId")*1 + 1;
			tree.data("childId",id);
			return id;
		},
		
		addSiblingCon : function($tr, isChild) {

			var tree = $tr.parent();

			var parentNode = $tr.treegrid("getParentNode");
			if (isChild)
				parentNode = $tr;

			var id = treeFuncs.getChildId(tree);

			var pid = parentNode.treegrid("getNodeId");

			var name = parentNode.find(">td:first").text() + "."
					+ (parentNode.treegrid("getChildNodes").length + 1);

			var node = $(whereClaus.getChildrenTemp().format(id, pid, name));

			var last = tree.find(".treegrid-parent-" + pid + ":last");

			if (last[0]) {
				function findLast(last) {
					var childNodes = last.treegrid('getChildNodes');
					if (childNodes && childNodes.length > 0) {
						var tid = last.treegrid('getNodeId');
						last = findLast(tree.find(".treegrid-parent-" + tid
								+ ":last"));
					}
					return last;
				}
				last = findLast(last);
			}

			last = last[0] ? last : $tr;

			last.after(node);

			window.setTimeout(function() {
				func.initEvent(node);
				tree.treegrid();
			}, 0);

			return node;
		},
		delCon : function($tr) {

			var tree = $tr.parent();

			var parentNode = $tr.treegrid("getParentNode");

			var parentName = parentNode.find(">td:first").text();

			var pid = parentNode.treegrid("getNodeId");

			$tr.remove();

			tree.find(".treegrid-parent-" + pid).each(function(i, t) {
				$(this).find(">td:first").text(parentName + "." + (i + 1));
			});
			tree.treegrid();

		},
		upCon : function($tr) {// 转换为复合条件

			var tree = $tr.parent();

			var id = $tr.treegrid("getNodeId");

			var name = $tr.find(">td:first").text();

			$tr.find(">td:first").text(name + ".1");

			var parentNode = $tr.treegrid("getParentNode");

			var pid = parentNode.treegrid("getNodeId");

			var node = $(whereClaus.getCompositTemp().format(id, name));

			node.removeAttr("class").attr('class',
					$tr.attr('class') + " parent-cls");

			var tId = treeFuncs.getChildId(tree);

			tree.data("childId", tId);

			$tr.removeClass("treegrid-" + id).addClass("treegrid-" + tId);

			$tr.removeClass("treegrid-parent-" + pid).addClass(
					"treegrid-parent-" + id);

			$tr.before(node);

			window.setTimeout(function() {
				tree.treegrid();
				func.initEvent(node);
			}, 0);
		}
	};

	var func = function() {
	};

	/**
	 * 渲染条件树
	 */
	func.fromJson = function(jq, json) {

		if (!json || !jq)
			return false;

		var $tree = jq;
		var collection = json.collection, //
		collectionTree = json.collectionTree;

		if (collectionTree) {
			$tree._addNodes(collectionTree);
		}
		var $this;
		$
				.each(
						collection,
						function(i, item) {
							// $this = treeFuncs.addChildCon($tr);
							$this = $tree.find(".treegrid-" + item.ordinal);

							var parentNode = $this.treegrid("getParentNode");

							if (parentNode[0] && !parentNode.data("init")) {
								parentNode.find("select[class=linkCon]")//
								.data("kendoDropDownList")
										.value(item.connector);
								parentNode.data("init", true);
							}

							var fieldInput = $this.find("input[name=field]"), paramInput = $this
									.find("input[name=param]"), dynamicChk = $this
									.find("input[type=checkbox]"), operatorDrop = $this
									.find("select[class=jCon]").data(
											"kendoDropDownList");

							if (fieldInput[0] && paramInput[0]) {

								var fieldItem = {
									// expressionl : item.expressionl,
									expression : item.expressionl,
									value : item.expressionl,
									data : item.fieldData,
									dataStr : JSON.stringify(item.fieldData)
								};
								if (!fieldItem.dataStr) {
									fieldItem.item = item;
								}

								fieldInput.val(item.fieldVal).data("item",
										fieldItem);

								paramInput.val(item.paramVal).data("item", {
									expression : item.expression,
									value : item.expression,
									data : item.paramData,
									dataStr : JSON.stringify(item.paramData)
								});
								operatorDrop.value(item.operator);
								if (dynamicChk[0])
									dynamicChk[0].checked = item.dynamic == "Y";

							}
						});

	};

	/**
	 * 获取条件树
	 */
	func.toJson = function(jq) {

		var json = {
			collection : [],
			collectionTree : []
		};

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

		var dataSet = window.dataSet || {}, isOk = true;

		$(jq)
				.each(
						function() {
							var nodes = $(this).treegrid("getAllNodes"), $this, collection = //
							json.collection, collectionTree = json.collectionTree;

							$(this).find("tr input").css({
								"background-color" : ''
							});
							if (nodes && nodes.length > 1) {

								for (var i = 0; i < nodes.length; i++) {
									$this = $(nodes[i]), id = $this
											.treegrid("getNodeId"), pId = $this
											.treegrid("getParentNodeId");
									var treeObject = {
										name : $this.find("td:first").text(),
										id : id,
										pId : pId,
										isParent : $this.hasClass("parent-cls")
									};
									if (!$this.treegrid("isLeaf")
											|| $this.hasClass("parent-cls")) {
										var dataItem = $this.find("select")
												.data("kendoDropDownList")
												.dataItem();
										if (dataItem) {
											treeObject.connector = dataItem.connector;
										}
										if (hasChildren($this)) {
											collectionTree.push(treeObject);
										}
										continue;
									}
									collectionTree.push(treeObject);

									var job = {
										parentId : pId,
										ordinal : id,
										datasetId : dataSet.id
									}, $connector = $this
											.treegrid("getParentNode");

									var fieldInput = $this
											.find("input[name=field]"), fieldItem = fieldInput
											.data("item"), paramInput = $this
											.find("input[name=param]"), paramItem = paramInput
											.data("item");

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

									if (fieldItem) {
										var linkConItem = $connector.find(
												"select").data(
												"kendoDropDownList").dataItem();
										if (linkConItem)
											job.connector = linkConItem.connector;// 连接条件（AND或OR）
										if (fieldItem.data) {
											job.fildData = fieldItem.data;
											job.fieldData = job.fildData;
										}

										if (fieldItem.dataStr) {
											job.fildData = JSON
													.parse(fieldItem.dataStr);
											job.fieldData = job.fildData;
										}

										if (fieldItem.expression) {
											job.expressionl = fieldItem.value
													|| fieldInput.val();
										} else if (fieldItem.item) {
											job.tableName = fieldItem.item.tableName;
											job.parameName = job.columnName = fieldItem.item.columnName;// 查询参数/列
											job.parameType = job.dtype = fieldItem.item.dtype;//
										}
									} else {
										job.expressionl = fieldInput.val();
									}

									if (paramItem) { // 表达式
										if (paramItem.expression) {// 表达式符号
											job.expression = paramItem.value
													|| paramInput.val();
										} else if (paramItem.item) {// 字段匹配
											job.expression = paramItem.item.tableName
													+ "."
													+ paramItem.item.dname;
										}
										if (paramItem.dataStr) {
											job.paramData = JSON
													.parse(paramItem.dataStr);
										}
									} else {
										job.expression = paramInput.val();
									}

									job.dynamic = $this.find(
											"input[type=checkbox]").is(
											":checked") ? "Y" : "N";// 是否动态条件
									job.operator = $this.find(
											"select[class=jCon]").data(
											"kendoDropDownList").dataItem().value;//
									job.fieldVal = fieldInput.val();
									job.paramVal = paramInput.val();
									collection.push(job);
								}
							}
						});

		json.outerTree = $.transformToTreeFormat(//
		$.extend(true, [], json.collectionTree), "id", "pId", "children");

		return json;
	};

	func.getChildrenTemp = function() {
		return templateChildren;
	};

	func.getCompositTemp = function() {
		return templateComposit;
	};

	func.init = function($jq) {
		$jq.empty().append(func.getCompositTemp().format(1, 1)).treegrid();
		func.initEvent($jq);
	};

	func.initEvent = function(jq) {
		var $this;

		jq.find(".linkCon").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			dataSource : func.Connector
		});

		jq.find(".jCon").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			dataSource : func.Syn
		});

	};
	
	func.Connector = [ {
		text : '全部符合',
		value : 'AND',
		connector : 'AND'
	}, {
		text : '任意符合',
		value : 'OR',
		connector : 'OR'
	} ];
	
	func.Syn = [ {
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
		value : 'lLike'
	}, {
		text : '右包含',
		value : 'rLike'
	}, {
		text : '不包含',
		value : 'not like'
	}, {
		text : 'IS',
		value : 'IS'
	}, {
		text : 'IS NOT',
		value : 'IS NOT'
	}, {
		text : 'IN',
		value : 'IN'
	}, {
		text : 'NOT IN',
		value : 'NOT IN'
	} ];
	
	/**
	 * 事件注册(一次)
	 */
	func.initEvents = function(){
	
		var $doc = $(document);
		
		$doc.on("click." + func.cls + ".chk-cls", "." + func.cls + " input.chk-cls", function(){
			var $this = $(this);
			var checked = $this.is(":checked"), chkItem = $this.data("item"), paramInput = $this
			.closest("tr").find("input[name=param]");
			if (checked) {
				if (!paramInput.val()) {
					alert('请填写参数名');
					return false;
				}
				if (!chkItem) {
					$this.data("item", {
						name : paramInput.val(),
						param : 'col' + new Date().getTime()
					});
				} else {
					chkItem.name = paramInput.val();
				}
			} else {
				
			}
		}).on("dblclick." + func.cls + ".textbox-menu", "." + func.cls + " input.textbox-menu", function(){
			window.openExpress && window.openExpress(this);
		}).on("keyup." + func.cls + ".textbox-menu", "." + func.cls + " input.textbox-menu", function(){
			$(this).data('item', null);
		});
		
		$.each(["addCon", "delCon", "upCon"], function(i, v){
			$doc.on("click." + func.cls + "." + v, "." + func.cls + " button." + v, function(e){
				var target = $(e.target).closest("button." + v), name = target.attr('name');
				if (name && (name in treeFuncs)) {
					treeFuncs[name].call(target, target.closest("tr"));
				}
			});
		});
		return func;
	}
	
	func.cls = "table-tree";
	
	return func.initEvents();
})();

window.getCheckedRows = window.getCheckedRows || function() {
	return [];
}

$queue.add(function() {
	whereClaus.init($("." + whereClaus.cls));
});