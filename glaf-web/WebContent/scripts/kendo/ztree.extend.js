var ztreeFunc = {
	getRow: function(rule, args) {
		//if (args && args.length > 1) {
		//	return args[1][rule.columnName];
		//} else {
			var $id = pubsub.getJQObj(rule, true);
			if (!$id) {
				$id = pubsub.getJQObj(rule.inid, true);
			}
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getSelectedNodes();
			nodes.length || (nodes = ztreeObj.getCheckedNodes());
			var retVal = "" ;
			if (nodes.length) {
				for(var i=0,len = nodes.length;i<len;i++){
					retVal += nodes[i][rule.columnName]||"";
					retVal += (i==len-1)?"":",";
				}
				//return nodes[0][rule.columnName];
			}
			return retVal ;
		//}
	},
	linkage:function(rule,args){
		ztreeFunc.linkageControl(rule, args);
	},
	linkageControl: function(id, params) {
		var $id = pubsub.getJQObj(id);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var otherParam = ztreeObj.setting.async.otherParam;
			otherParam.params = JSON.stringify(params);
			/*
			 * for(var p in params){ otherParam[p] = params[p]; }
			 */
			ztreeObj.setting.async.otherParam = otherParam;
			setTimeout(function(){
				if($id.data("isloaded")){
						ztreeObj.reAsyncChildNodes(null, "refresh");
				}else{
						$id.data("params",params);
				}	
			}, 100);
		}
		/*
		 * view: { fontCss: getFontCss } function getFontCss(treeId, treeNode) {
		 * return (!!treeNode.highlight) ? {color:"#A60000",
		 * "font-weight":"bold"} : {color:"#333", "font-weight":"normal"}; }
		 */
	},
	_buildRetAry: function(nodes, retAry, rule) {
		var node, nodesLength = nodes.length;
		for (var i = 0; i < nodesLength; i++) {
			node = nodes[i];
			if (node[rule.columnName]) {
				retAry.push(node[rule.columnName]);
			}
			if (node.children) {
				ztreeFunc._buildRetAry(node.children, retAry, rule);
			}
		}
	},
	getAll: function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getNodes();
			var retAry = [];
			if (nodes) {
				ztreeFunc._buildRetAry(nodes, retAry, rule);
			}
			return retAry.join(",");
		}
		// rule.columnName
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).closest("div").hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).closest("div").show();
	},
	tSelectFirst: function(rule, args) { //选中第一个节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id')),
				nodes = ztreeObj.getNodes();
			if (nodes && nodes.length > 0) {
				var o = nodes[0];
				ztreeObj.selectNode(o);
				ztreeObj.setting.callback.onClick(null, $id.attr('id'), o);
			}
			var mo = new MutationObserver(function(record) {
				nodes = ztreeObj.getNodes();
				if (nodes && nodes.length > 0) {
					var o = nodes[0];
					ztreeObj.selectNode(o);
					ztreeObj.setting.callback.onClick(null, $id.attr('id'), o);
				}
				mo.disconnect();
			});
			mo.observe($id[0], {
				'childList': true,
				'arrtibutes': true,
				'subtree': true,
				'characterData': true
			});
		}
	},
	cancelSelectNodes: function(rule, args){//取消选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			ztreeObj.cancelSelectedNode();
			ztreeObj.checkAllNodes(false);
		}
	}
};
pubsub.sub("ztree", ztreeFunc);

function expandNodes(ztreeObj,expandNodes,expandChilds){
	var ztreeObj = ztreeObj;
	var expandChilds = expandChilds;
	if(expandChilds && expandChilds > 2 && expandNodes && expandNodes.length>0){
		$.each(expandNodes,function(i,item){
			ztreeObj.expandNode(item, true, false, true);
			expandNodes(ztreeObj,item.children,expandChilds - 1);
		})
	}
}

//根据参数获取父节点
function getParentByParam(node, field, exp, outFiled, isDeep) {
	var pNode = node.getParentNode(),
		depVal, retVal = "";
	if (pNode) {
		if (eval("'" + pNode[field] + "'" + exp)) {
			retVal = pNode[outFiled];
			if (isDeep) {
				depVal = getParentByParam(pNode, field, exp, outFiled, isDeep);
				if (depVal)
					retVal = depVal;
			}
		} else {
			retVal = getParentByParam(pNode, field, exp, outFiled, isDeep);
		}
	}
	return retVal;
};
//根据参数获取子节点
function getChildByParam(node, field, exp, outFiled, isDeep) {
	var childrens = node.children;
	if (childrens && childrens.length) {
		var children, retVal = "",
			childVal;
		for (var i = 0; i < childrens.length; i++) {
			children = childrens[i];
			if (eval("'" + children[field] + "'" + exp)) {
				retVal = children[outFiled];
				if(!isDeep){
					return retVal ;
				}
			} else {
				childVal = getChildByParam(children, field, exp, outFiled,isDeep);
				if (childVal) {
					retVal = childVal;
				}
			}
		}
		return retVal;
	}
	return "";
};
//往上查找节点
function getTreeUpNode(args, field, exp, outFiled, isDeep) {
	try {
		return getParentByParam(args[1], field, exp, outFiled, isDeep);
	} catch (e) {
		console.log(e);
	}
	return "";
};
//往下查找节点
function getTreeDownNode(args, field, exp, outFiled, isDeep) {
	try {
		return getChildByParam(args[1], field, exp, outFiled, isDeep);
	} catch (e) {
		console.log(e);
	}
	return "";
};
//获取树节点
function getTreeNode(trigger, args, level, outFiled) {
	var node = args[1];
	if (level == 0) {
		//k.getPreNode() k.getNextNode()
		var node = node.getParentNode();
	}
	/*var rule = {
		inid: trigger.eleId,
		inpage: trigger.pageId,
		inlev: trigger.level
	};
	var $id = pubsub.getJQObj(rule, true);
	try {
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			//ztreeObj.
			var node = ztreeObj.getNodesByFilter(function(node) {
				return eval("'" + node[field] + "'" + exp);
			}, true, args[1]);
			if (node) {
				return node[outFiled];
			}
		}
	} catch (e) {
		console.log(e);
	}
	return "";*/
};
// ********************************** ztree相关
// *****************************************************************
// event:标准事件;treeId：树ID;treeNode：点击的节点;
// clickFlag：点击模式;linkType:单击类型;ids:联动的对象ID集合
var glafZtree = {
	// treeId：树ID;treeNode：拖拽的节点
	// ;targetNode：拖拽的目标节点;moveType："inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
	// isCopy:移动类型（复制/移动）;ruleId:规则id;
	beforeDrop: function(treeId, treeNodes, targetNode, moveType, isCopy, ruleId) {
		if (moveType == "null") {
			return false;
		} else {
			var treeNodeIds = [];
			for (var i = 0; i < treeNodes.length; i++) {
				var treeNode = treeNodes[i];
				treeNodeIds.push(treeNode.id);
			}
			var data = "treeNodeIds=" + treeNodeIds.join(",") + "&targetNodeId=" + (targetNode ? targetNode.id : "") + "&moveType=" + moveType + "&rid=" + ruleId + "&isCopy=" + isCopy;
			var flag = true;
			$.ajax({
				type: "POST",
				url: contextPath + '/mx/form/treeData/drop',
				data: data,
				async: false,
				success: function(msg) {},
				error: function() {
					alert("操作失败");
					flag = false
				}
			});
			return flag;
		}
	},
	// treeId：树ID;treeNode：节点对象 ;ruleId:规则id;
	beforeRemove: function(treeId, treeNode, ruleId) {
		if (treeNode.children && treeNode.children.length > 0) {
			alert('有子节点不能删除');
			return false;
		}
		if (confirm("确认删除 节点<" + treeNode.name + ">吗？")) {
			var data = "treeNodeIds=" + treeNode.id + "&rid=" + ruleId;
			var flag = true;
			$.ajax({
				type: "POST",
				url: contextPath + '/mx/form/treeData/remove',
				data: data,
				async: false,
				success: function(msg) {},
				error: function() {
					alert("操作失败");
					flag = false;
				}
			});
			return flag;
		} else {
			return false;
		}
	},
	// treeId：树ID;treeNode：节点对象 ;newName：新名称：isCancel:是否取消编辑;ruleId:规则id;
	beforeRename: function(treeId, treeNode, newName, isCancel, ruleId) {
		if (isCancel) {
			return true;
		}
		var data = "treeNodeId=" + treeNode.id + "&newName=" + newName + "&rid=" + ruleId;
		var flag = true;
		$.ajax({
			type: "POST",
			url: contextPath + '/mx/form/treeData/rename',
			data: data,
			async: false,
			success: function(msg) {},
			error: function() {
				alert("操作失败");
				flag = false;
			}
		});
		return flag;
	},
	// type：工具条类型;ztreeId：树ID;ruleId:规则id;
	ztreeToolbar: function(type, ztreeId, ruleId, editPanleAutoClose) {
		var treeObj = $.fn.zTree.getZTreeObj(ztreeId);
		var nodes;
		if (treeObj.setting.check.enable) {
			nodes = treeObj.getCheckedNodes(true);
		} else {
			nodes = treeObj.getSelectedNodes();
		}
		var link;
		switch (type) {
			case "add": // 增加同级
				if (nodes.length > 1) {
					alert('请选择一个节点');
					return;
				}
				var pidkey = "";
				if (nodes.length == 1) {
					pidkey = nodes[0].pidkey;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&pid=' + pidkey + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose;
				break;
			case "next": // 增加子级
				if (!nodes || nodes.length <= 0) {
					alert('请选择节点');
					return;
				}
				if (nodes.length != 1) {
					alert('请选择一个节点');
					return;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&pid=' + nodes[0].indexkey + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose;
				break;
			case "edit": // 编辑
				if (!nodes || nodes.length <= 0) {
					alert('请选择节点');
					return;
				}
				if (nodes.length != 1) {
					alert('请选择一个节点');
					return;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&id=' + nodes[0].id + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose;
				break;
			case "del": // 删除
				if (!nodes || nodes.length <= 0) {
					alert('请选择节点');
					return;
				}
				if (!confirm("确认删除 节点 吗？")) {
					return;
				}
				var treeNodeIds = [];
				for (var i = 0; i < nodes.length; i++) {
					var treeNode = nodes[i];
					treeNodeIds.push(treeNode.id);
				}
				var data = "treeNodeIds=" + treeNodeIds.join(",") + "&rid=" + ruleId;
				$.ajax({
					type: "POST",
					url: contextPath + '/mx/form/treeData/remove',
					data: data,
					async: false,
					success: function(msg) {
						treeObj.reAsyncChildNodes(null, "refresh");
					},
					error: function() {
						alert("操作失败");
					}
				});
				return;
				break;
			default:
				alert('操作失败');
				break;
		}
		if (!link) {
			return;
		}
		$.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "增加/编辑节点",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['', ''],
			fadeIn: 100,
			area: ['400px', '300px'],
			iframe: {
				src: link
			}
		});
	},
	init:function(id,options){
		$.fn.zTree.init($('#'+id),options);
		if(options){
			$("#ztree_"+id+"_add").kendoButton({click:function(){glafZtree.ztreeToolbar('add',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
			$("#ztree_"+id+"_add_next").kendoButton({click:function(){glafZtree.ztreeToolbar('next',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
			$("#ztree_"+id+"_edit").kendoButton({click:function(){glafZtree.ztreeToolbar('edit',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
			$("#ztree_"+id+"_del").kendoButton({click:function(){glafZtree.ztreeToolbar('del',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
		}
	},
	convert: function(dataItem,rule){
		if(rule.icon && rule.icon.length){
			var icons = rule.icon ;
			for(var i=0;i<icons.length;i++){
				var icon = icons[i];
				if(icon.expression && eval("("+icon.expression.replace(/\\\"/g,"\"")+")")){
					dataItem[icon.type] = contextPath + icon.icon;
				}
			}
		}
		if(rule.check && rule.check.length){
			var checks = rule.check ;
			for(var i=0;i<checks.length;i++){
				var check = checks[i];
				if(check.expression && eval("("+check.expression.replace(/\\\"/g,"\"")+")")){
					if(check.type && check.type == "disabled"){
						dataItem["chkDisabled"] = true;	
					}else{
						dataItem["checked"] = true;
					}
				}
			}
		}
		if(rule.text && rule.text.length){
			var texts = rule.text ;
			for(var i=0;i<texts.length;i++){
				var text = texts[i];
				if(text.expression && eval("("+text.expression.replace(/\\\"/g,"\"")+")")){
					dataItem["name"] = new Function( "var dataItem = arguments[0];return \""+text.htmlVal.replace(/##/g,"\"")+"\";")(dataItem);	
				}
			}
		}
	}
};