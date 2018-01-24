<%@ page contentType="text/html;charset=UTF-8" language="java"%>
	<ul>
		<li>标段 <select id="select-biaoduan" style="width: 120px;">
		</select>
		</li>
	</ul>
	<ul>
		<li>类型 <select id="select-type" style="width: 120px;"></select>
		</li>
	</ul>
	<ul>
		<li><input id="searchText" type="text" class="k-textbox"
			style="width: 85px;" />
			<button type="button" class="k-button" t='search'>搜索</button></li>
	</ul>
	<hr>
	<div style="width:100%;height:81%;overflow: hidden;">
		<ul id="tree" class="ztree" style="width:100;height:98%;overflow: auto;"></ul>
	</div>
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
	$("button[t=search]").on("click.search", function() {
		if ($("#searchText").val()) {
			var fn = $(this).data("fn");
			!fn && ($(this).data("fn", fn = new ztreeFunc($("#tree"))));
			fn.highlight(["name", 'tableName'], $("#searchText").val());
		}
	});

	$(function() {
		initDropDownList();
		
		initZtreeSetting();
	});

	function initDropDownList() {
		var url = contextPath + "/rs/isdp/global/databaseJson", //
		$select_biaoduan = $("#select-biaoduan").kendoDropDownList(
				{
					dataTextField : "text",
					dataValueField : "code",
					dataSource : {
						transport : {
							read : {
								dataType : "json",
								url : contextPath
										+ "/rs/isdp/global/databaseJson"
							}
						}
					},
					index : 0,
					change : function(e) {
						var selectType = $("#select-type").data(
								'kendoDropDownList').dataItem();
						initZTree(selectType.code, e.sender.dataItem().code);
					}
				}).data("kendoDropDownList");

		url = contextPath + "/mx/form/defined/getDictByCode/?code=tableType", //
		$select_type = $("#select-type").kendoDropDownList({
			dataTextField : "name",
			dataValueField : "value",
			dataSource : {
				transport : {
					read : {
						dataType : "json",
						url : url
					}
				}
			},
			index : 0,
			change : function(e) {
				var biaoduan = $select_biaoduan.dataItem();
				var typeItem = e.sender.dataItem();
				initZTree(typeItem.code, biaoduan.code);
			}
		});
	}

	function initZtreeSetting() {
		$.extend(true, window.setting.callback, {
			beforeClick : ztreeBeforeClick
		});
	}
	
	var ztreeBeforeClickFunc = (function(){
		return function(){
			return window.ztreeBeforeClick.//
				apply(this, arguments);
		};
	})();
	
	//加载zTree事件
	window.utableTreeUrl = contextPath
			+ "/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?1=1";
	window.cellUrl = contextPath
			+ "/rs/isdp/treeDot/getTreeDotByParentIdInit?1=1";
	window.setting = {
		async : {
			enable : true,
			url : utableTreeUrl,
			autoParam : [ "indexId", "nlevel=level" ]
		},
		data : {
			simpleData : {
				enable:true,
				idKey : 'indexId',
				pIdKey:"parentId"
			},
			key : {
				name : "indexName",
				title : 'tableName'
			}
		},
		callback : {
			selectNodeById : function(id) {
				
				var onAsyncSuccess = window.setting.callback.onAsyncSuccess;
				
				window.setting.callback.onAsyncSuccess  = function(){
					onAsyncSuccess.apply(this, arguments);
					var node = window.$zTree.getNodeByParam("id",
							id, null);
					if (node) {
						window.$zTree.selectNode(node);
					}
				};
			},
			onAsyncSuccess : function(){
				window.__TreeNodeId = window.__TreeNodeId || //
					(window._treeNode ? window._treeNode.id : "");
				if(window.__TreeNodeId){
					var node = window.$zTree.getNodeByParam("id",
							window.__TreeNodeId, null);
					if (node) {
						window.$zTree.selectNode(node);
						$("#" +node.tId + "_span").click();
					}
					window.__TreeNodeId = null;
				}
				
			},
			refreshAndSelectById : function(id, init){
				window.__TreeNodeId = id;
				window.__TreeNodeIdInit = init;
				if(!init)
					window.$zTree.reAsyncChildNodes(null, 'refresh');
			},
			beforeAsync : function(treeId, treeNode){
				//alert(treeId);
			}
		},
		view : {
			selectedMulti : true,
			fontCss : function(treeId, treeNode) {
				return (!!treeNode.highlight) ? {
					color : "#A60000",
					"font-weight" : "bold"
				} : {
					color : "#333",
					"font-weight" : "normal"
				};
			}
		}
	};

	function getTableType() {
		var k = null, tableType = !!(k = window
				.getKendo($("#select-type"))) ? k.value() : null;
		return tableType || "1";
	}
	function getSystemName() {
		var k = null, SystemName = !!(k = window
				.getKendo($("#select-biaoduan"))) ? k.value() : null;
		return SystemName || "default";
	}

	function initZTree(tableType, ds) {
		var zTreeObj = $("#tree"), data = null;
		var url = null;
		if (tableType == 1) {
			url = utableTreeUrl
					+ "&type=1&tableType=2&systemName=" + ds;
		} else if (tableType == 2) {
			url = cellUrl + '&systemName=' + ds;
		} else if (tableType == 3) {
			url = utableTreeUrl
					+ "&type=1&tableType=12&systemName=" + ds;
		} else if (tableType == 4) {
			url = utableTreeUrl
					+ "&type=4&tableType=12&systemName=" + ds;
			data = [ {
				'indexName' : '系统内置表',
				'parentId' : -1,
				'indexId' : 1,
				id : '1|',
				isParent : true
			} ];
		} else if (tableType == 5) {
			data = [ {
				'indexName' : '自动生成表',
				'parentId' : -1,
				'indexId' : -1,
				id : '1|'
			} ];
		}
		var isOk = false;
		if(window.$zTree && url){
			isOk = window.$zTree.setting.async.url != url;
		}

		isOk = isOk || !window.$zTree;
		if(isOk){
			url && (setting.async.url = url);
			window.$zTree = $.fn.zTree.init(zTreeObj, setting, data);
		}
		
	}

	function ztreeFunc(jq) {
		this.$jq = jq;
		this.highlights = null;
		this.state = {
			ztree : $.fn.zTree.getZTreeObj(jq.attr("id"))
		};
		this.updateHighlight = function(nodes, highlight) {
			var ztree = this.state.ztree;
			$.each(nodes, function(i, node) {
				node.highlight = highlight || false;
				ztree.updateNode(node);
			});
			return this;
		}, this.highlight = function(keys, value, append) {
			if (!keys || !value)
				return this;
			var ztree = this.state.ztree;
			var nodes = this.highlights;
			if (nodes && nodes.length && !append) {
				this.updateHighlight(nodes, !!append);
			}
			if(!(keys instanceof Array)){
				keys = [keys];
			}
			nodes = [];
			$.each(keys, function(i, key){
				var ns = ztree.getNodesByParamFuzzy(key, value,
						null);
				if(ns != null && ns.length){
					$.each(ns, function(){
						nodes.push(this);
					});
				}
			});
			this.highlights = nodes;
			if (nodes && nodes[0]) {
				$.each(nodes, function(i, node) {
					!node.isParent && (node = node.getParentNode());
					ztree.expandNode(node, true, true, false);
				});
				this.updateHighlight(nodes, true);
			}
			return this;
		}
	}
</script>