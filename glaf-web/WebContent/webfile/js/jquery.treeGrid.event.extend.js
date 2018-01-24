/**
 * jQuery treegrid extend
 */
var TreeGridExtend = function() {
	var obj = {};
	//行模板 treegrid-3 treegrid-parent-1 treegrid-collapsed
	//<button class='k-button addChild'>+ 下级</button>
	var lineTemp = [];
	obj.targetName = ".tree";
	obj.lineTemp = lineTemp;
	obj.nodeClassPrefix = "treegrid-";
	obj.parentNodeClassPrefix = obj.nodeClassPrefix + "parent-";
	obj._delStr = "<button class='k-button del_row'>删除</button>";
	obj.formatLine = function(arg0, arg1, isRemDel) {
		arg0 = this.nodeClassPrefix + arg0, arg1 = arg1 == null ? "" : this.parentNodeClassPrefix + arg1;
		return kendo.format(this.lineTemp.join(), arg0, arg1, isRemDel ? "" : this._delStr);
	};
	obj.isClick = true;
	obj.bindData = function($tr, nodeObj) {};
	obj.getData = function($tr) {};
	//点击前执行事件
	obj.beforeClick = function(){};
	obj.endClick = function(){};
	//点击事件
	obj.click = function(e, $tr) {};
	obj.selectStyle = function($tr){
		$tr.addClass('mt-select').css("background", "#207092");
		$tr.siblings().removeClass("mt-select").css("background", "");
	}
	//选择行事件方法
	obj.clickFun = function(e) {
		var $tr = $(this);
		obj.beforeClick(e, $tr);
		obj.selectStyle($tr);
		obj.click(e, $tr);
		obj.endClick(e, $tr);
	};
	obj.dbclick = function() {};
	//绑定行点击事件
	obj.bindRowEvent = function($tr) {
		if (obj.isClick) {
			$tr.on("click", obj.clickFun);
		}
	};
	//增加同级
	obj.addBrother = function(target, nodeDatas) {
		var $k;
		if (target instanceof jQuery) {
			$k = target;
			$(obj.targetName).append($k);
		} else {
			var $tr = $(this).parents("tr"),
				cls = $tr.prop("class"),
				maxNo = $(obj.targetName).data("maxNo"),
				//m = cls.match(new RegExp(obj.nodeClassPrefix + "(\\d+)")),
				n = cls.match(new RegExp(obj.parentNodeClassPrefix + "(\\d+)")), //匹配父节点class
				maxNo = maxNo + 1,
				arg0 = maxNo,
				arg1 = (n && n.length >= 2 ? parseInt(n[1]) : null);
			$k = $(obj.formatLine(arg0, arg1));
			$tr.before($k);
			$(obj.targetName).data('maxNo', maxNo);
		}
		//注册行事件
		obj.bindRowEvent($k);
		//渲染
		$(obj.targetName).treegrid();
		obj._initControl($k, nodeDatas);
	};
	//增加下级
	obj.addChild = function(target, nodeDatas) {
		var $tr;
		if (target instanceof jQuery) {
			$tr = target;
		} else {
			$tr = $(this).parents("tr");
		}
		var cls = $tr.prop("class"),
			maxNo = $(obj.targetName).data("maxNo"),
			maxNo = maxNo + 1,
			m = cls.match(new RegExp(obj.nodeClassPrefix + "(\\d+)")),
			arg0 = maxNo,
			arg1 = (m && m.length >= 2 ? parseInt(m[1]) : null),
			$k = $(obj.formatLine(arg0, arg1));
		if (target instanceof jQuery) {
			$(obj.targetName).append($k);
		} else {
			$tr.after($k);
		}
		//注册行事件
		obj.bindRowEvent($k);
		//渲染
		$(obj.targetName).data('maxNo', arg0).treegrid();
		obj._initControl($k, nodeDatas);
		return $k;
	};
	//删除行
	obj.delRow = function() {
		//迭代删除
		function delIterator($tr) {
			$tr.treegrid('getChildNodes').each(function(i, n) {
				var $this = $(n);
				if (!$this.treegrid("isLeaf")) { //不是叶子的话遍历删除
					delIterator($this);
				}
				$this.remove();
			});
		}
		var $this = $(this),
			$tr = $this.parents("tr"),
			$tree = $tr.closest(obj.targetName);
		delIterator($tr);
		$tr.remove();
		obj.delRowEnd($tree);
	};
	obj.delRowEnd = function($tree) {};
	obj.copyFun = function(e) {
		var $this = $(this),
			$tr = $this.parents("tr");
		obj.init(JSON.stringify([{
			values: [obj.getData($tr)]
		}]), true);
	};
	obj.outerAddRow = function() {
		var maxNo = $(obj.targetName).data("maxNo"),
			maxNo = maxNo + 1,
			$tr = $(obj.formatLine(maxNo, null));
		obj.addBrother($tr);
		$(obj.targetName).data("maxNo", maxNo);
	};
	obj.initControlEvent = function($tr) {

	};
	obj._initControl = function($tr, nodeDatas) {
		obj.initControlEvent($tr)
		obj.bindData($tr, nodeDatas);
	};
	//注册事件
	obj._initEvent = function() {
		var $targetObj = $(obj.targetName);
		if (!$targetObj.data("isEvent"))
			$targetObj.delegate(".addBrother", "click", this.addBrother)
			.delegate(".addChild", "click", this.addChild)
			.delegate(".del_row", "click", this.delRow)
			.delegate(".tg-up", "click", this.upFun)
			.delegate(".tg-down", "click", this.downFun)
			.delegate(".tg-power", "click", this.powerFun)
			.delegate(".tg-lower", "click", this.lowerFun)
			.delegate(".tg-copy", "click", this.copyFun);
	};
	obj.init = function(datas, noResetMaxNo) {
		var $targetObj = $(obj.targetName);
		!noResetMaxNo && $targetObj.data("maxNo", 1);
		if (datas && datas != "") {
			//恢复数据 
			function iteratorChildsBuild($ptr, nodes) {
				var length = nodes.length,
					node,
					$tr,
					i = 0;
				for (; i < length; i++) {
					node = nodes[i];
					$tr = obj.addChild($ptr, node);
					childs = node.childs;
					if (childs && childs.length) {
						iteratorChildsBuild($tr, childs);
					}
				}
			}
			var retdatasJson = JSON.parse(datas);
			if (retdatasJson && retdatasJson.length) {
				var nodes = retdatasJson[0]['nodes'],
					i = 0,
					length = nodes.length,
					node,
					childs,
					maxNo;
				for (; i < length; i++) {
					node = nodes[i];
					maxNo = $targetObj.data("maxNo");
					maxNo = maxNo + 1;
					var $tr = $(obj.formatLine(maxNo, null));
					obj.addBrother($tr, node);
					$targetObj.data("maxNo", maxNo);
					childs = node.childs;
					if (childs && childs.length) {
						iteratorChildsBuild($tr, childs);
					}
				}
			}
		} else {
			var $tr = $(obj.formatLine("1", null));
			$targetObj.append($tr).treegrid();
			obj._initControl($tr);
			obj.bindRowEvent($tr);
		}
		obj._initEvent();
		$targetObj.data("isEvent", true);
	};
	obj._getLastNode = function($tr) {
		var lastNode = $tr;
		$childNodes = $tr.treegrid("getChildNodes");
		if ($childNodes && $childNodes.length) {
			lastNode = obj._getLastNode($($childNodes[$childNodes.length - 1]));
		}
		return lastNode;
	}
	obj.upFun = function() {
		var $this = $(this),
			$nodes,
			$tree = $(obj.targetName),
			$tr = $this.parents("tr"),
			lastIndex = obj._getLastNode($tr).index(),
			depth = $tr.treegrid("getDepth"),
			index = $tr.index(),
			$nodes = depth ? $tr.treegrid("getParentNode").treegrid("getChildNodes") : $tree.treegrid("getRootNodes"),
			indexInArray = $nodes.index($tr);
		if (index && indexInArray) {
			var prevNode = $nodes[indexInArray - 1],
				$prevNode = $(prevNode),
				prevIdex = $prevNode.index(),
				$moveNodes = $tree.find("tr:gt(" + index + "):lt(" + (lastIndex - index + 1) + ")");
			$moveNodes.insertBefore($prevNode);
		}
	};
	obj.downFun = function() {
		var $this = $(this),
			$nodes,
			$tree = $(obj.targetName),
			allLength = $tree.find("tbody tr").length,
			$tr = $this.parents("tr"),
			lastIndex = obj._getLastNode($tr).index(),
			depth = $tr.treegrid("getDepth"),
			index = $tr.index(),
			$nodes = depth ? $tr.treegrid("getParentNode").treegrid("getChildNodes") : $tree.treegrid("getRootNodes"),
			indexInArray = $nodes.index($tr);
		if (index < allLength - 1 && indexInArray < $nodes.length - 1) {
			var nextNode = $nodes[indexInArray + 1],
				$nextNode = $(nextNode),
				nextIndex = $nextNode.index(),
				$moveNodes = $tree.find("tr:gt(" + index + "):lt(" + (nextIndex - index) + ")"),
				$nextLastNode = obj._getLastNode($nextNode);
			$moveNodes.insertAfter($nextLastNode);
		}
	};
	obj.powerFun = function() {
		var $this = $(this),
			$nodes,
			$tree = $(obj.targetName),
			$tr = $this.parents("tr"),
			parentNodeId = $tr.treegrid("getParentNodeId");
		if (parentNodeId != null) {
			$tr.removeClass('treegrid-parent-' + parentNodeId);
			var $parentTr = $tree.find(".treegrid-" + parentNodeId),
				ppNodeId = $parentTr.treegrid("getParentNodeId");
			if (ppNodeId != null) {
				$tr.addClass('treegrid-parent-' + ppNodeId);
			}

			if (!$parentTr.treegrid("getChildNodes").length) {
				$parentTr.find('span.treegrid-expander').removeClass('treegrid-expander-expanded');;
			}

			function removeIndent($tr) {
				$tr.find('.treegrid-indent:first').remove();
			}
			removeIndent($tr);
			$.each($tr.treegrid("getChildNodes"), function(i, el) {
				removeIndent($(el));
			});

			obj.upFun.call($tr.find('.tg-up'));
			$tr.treegrid('render');
			$parentTr.treegrid('render');
		}
		//treegrid-parent-2
	};
	obj.lowerFun = function() {
		var $this = $(this),
			$nodes,
			$tree = $(obj.targetName),
			$tr = $this.parents("tr"),
			parentNodeId = $tr.treegrid("getParentNodeId"),
			$prevTr,
			//同辈元素
			$peers;

		if (parentNodeId == null) {
			$peers = $tree.find("tr:not(.thead):not([class*=treegrid-parent-])");
		} else {
			$peers = $tree.find("tr.treegrid-parent-" + parentNodeId);
		}
		var rowIndex = $peers.index($tr);
		if (rowIndex) {
			$prevTr = $($peers[rowIndex - 1]);
			if (!$prevTr.find('button.addChild:visible').length) {
				return;
			}
			var newPNodeId = $prevTr.treegrid("getNodeId");
			if (newPNodeId == parentNodeId) {
				return;
			}
			if (parentNodeId != null) {
				$tr.removeClass('treegrid-parent-' + parentNodeId);
			}
			$tr.addClass('treegrid-parent-' + newPNodeId);

			function addIndent($tr) {
				$('<span class="treegrid-indent">').insertBefore($tr.find('span.treegrid-expander'));
			}
			addIndent($tr);

			$.each($tr.treegrid("getChildNodes"), function(i, el) {
				addIndent($(el));
			});

			$prevTr.find('span.treegrid-expander').addClass('treegrid-expander-expanded');
			$tr.treegrid('render');
			$prevTr.treegrid('render');
		}


	};
	obj.push = function(expObj) {
		for (var p in expObj)
			obj[p] = expObj[p];
	};
	return obj;
};