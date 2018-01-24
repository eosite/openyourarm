var ztreeFunc = {
	//获取选中的节点
	getRow: function(rule, args, obj) {
		//if (args && args.length > 1) {
		//	return args[1][rule.columnName];
		//} else {
		var $id = pubsub.getJQObj(rule, true);
		if (!$id) {
			$id = pubsub.getJQObj(rule.inid, true);
		}
		var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		var nodes = ztreeObj.getSelectedNodes();
		//nodes.length || (nodes = ztreeObj.getCheckedNodes());
		var retVal = "";
		if (nodes.length) {
			for (var i = 0, len = nodes.length; i < len; i++) {
				retVal += nodes[i][rule.columnName] || "";
				retVal += (i == len - 1) ? "" : ",";
				//放入集合中
				pubsub.outParamsObj.call(i,rule, obj, nodes[i]);
			}
			//return nodes[0][rule.columnName];
		}
		return retVal;
		//}
	},
	//设置展开层级数
	setExpandLevel:function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var value = "";
		$.each(rule,function(i,item){
			value = args[rule[i].param] || "";
		})
		$id.data("expandChilds",value||0);
	},
	//获取勾选的节点
	getChkRow: function(rule, args, obj) {
		var $id = pubsub.getJQObj(rule, true);
		if (!$id) {
			$id = pubsub.getJQObj(rule.inid, true);
		}
		var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		var nodes = ztreeObj.getCheckedNodes();
		var retVal = "";
		if (nodes.length) {
			for (var i = 0, len = nodes.length; i < len; i++) {
				retVal += nodes[i][rule.columnName] || "";
				retVal += (i == len - 1) ? "" : ",";
			}
			var columnName = rule.columnName;
			var param = {};
			param[columnName] = retVal;
			//放入集合中
			pubsub.outParamsObj.call(0,rule, obj, param);
		}
		return retVal;
	},
	//获取当前点击的节点
	getCurRow: function(rule, args) {
		if (args && args.length > 1) {
			return args[1][rule.columnName];
		}
		var $id = pubsub.getJQObj(rule, true);
		if (!$id) {
			$id = pubsub.getJQObj(rule.inid, true);
		}
		var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		var nodes = ztreeObj.getCheckedNodes();
		var retVal = "";
		if (nodes.length) {
			for (var i = 0, len = nodes.length; i < len; i++) {
				retVal += nodes[i][rule.columnName] || "";
				retVal += (i == len - 1) ? "" : ",";
			}
			return retVal;
		}
		return "";
	},
	linkage: function(rule, args) {
		ztreeFunc.linkageControl(rule, args);
	},
	linkageControl: function(id, params) {
		var $id = pubsub.getJQObj(id);
		var requireNum = $id.data("requireNum") || 0;	//请求数量
		if ($id && (!requireNum)) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var otherParam = ztreeObj.setting.async.otherParam;
			otherParam.params = JSON.stringify(params);
			ztreeObj.setting.async.otherParam = otherParam;

			if(params.expandLevel != null){
				$id.data("expandChilds",params.expandLevel||0);
			}else{
				var expandChilds = $id.data("expandChilds");
				$id.data("expandChilds",expandChilds||ztreeObj.setting.expandChilds||0);
			}
			ztreeObj.reAsyncChildNodes(null, "refresh");
		}
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
	showChilds : function(rule, args){
		var $id = pubsub.getJQObj(rule, true);
		if ($id) {
			var ztreeObj = $.fn.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getCheckedNodes(true);
			$.each(nodes,function(i,o){
				ztreeObj.expandNode(o, true, true, true);
			})
		/*	if (nodes.length>0) {
				ztreeObj.expandNode(nodes[0], true, true, true);
			}*/
		}
	},
	//当前节点和子节点刷新
	nodeRefresh:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $.fn.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getSelectedNodes();
			if (nodes.length>0) {
				ztreeObj.reAsyncChildNodes(nodes[0], "refresh");
			}
		}
		
	},
	//当前节点和父节点刷新
	parentRefresh:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $.fn.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getSelectedNodes();
			if (nodes.length>0) {
				var node = nodes[0].getParentNode();
				ztreeObj.reAsyncChildNodes(node, "refresh");
			}
		}
		
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
	cancelSelectNodes: function(rule, args) { //取消选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			ztreeObj.cancelSelectedNode();
			ztreeObj.checkAllNodes(false);
		}
	},
	_init_: function(rule, param, args) {
		var $id = pubsub.getJQObj(rule);
		eval("(" + __gobalInit__[$id.attr("id")] + ")")($.isArray(param) ? {} : param);
		pubsub.execChilds(rule);
	},
	//兼容旧版本
	_ztreeinit_: function(rule, param, args) {
		var $id = pubsub.getJQObj(rule);
		eval("(" + _gobalInit_[$id.attr("id")] + ")")($.isArray(param) ? {} : param);
		pubsub.execChilds(rule);
	},
	isChecked : function(rule, args){
		if(args[1]){
			return args[1].checked;
		}
		var $id = pubsub.getJQObj(rule,true),
			ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			selectNodes = ztreeObj.getSelectedNodes();
		if(selectNodes.length){
			return selectNodes[0].checked ;
		}
		return false;
	},
	
	
	checkedNode : function(rule,args){
		// var $id = pubsub.getJQObj(rule);
		// if ($id) {
		// 	var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		// 	//遍历args
		// 	$.each(args,function(key,value){
		// 		//根据,分割字符串
		// 		var strs = value.split(",");
		// 		$.each(strs,function(i,str){
		// 			var ztreeNodes = ztreeObj.getNodesByParam(key, str, null);
		// 			$.each(ztreeNodes,function(i,item){
		// 				ztreeObj.checkNode(item, true, false);	
		// 			})
		// 		})
		// 	})
		// }

		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var compare_key = args.key;

			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			//遍历args
			$.each(args,function(key,value){
				//根据,分割字符串
				if(key == 'key'){
					return true;
				}
				var strs = value.split(",");
				$.each(strs,function(i,str){
					var ztreeNodes = ztreeObj.getNodesByParam(compare_key || key, str, null);
					$.each(ztreeNodes,function(i,item){
						ztreeObj.checkNode(item, true, false);	
					})
				})
			})
		}
	},
	selectNode: function(rule, args) { //取消选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));

			var ztreeKey = args.key;	//获取key值
			//key = key || 'id';

			//遍历args
			$.each(args,function(key,value){
				//如果输入参数是用于设置key值时，跳过
				if(key == 'key'){
					return true;
				}
				//根据,分割字符串
				var strs = value.split(",");
				key = ztreeKey || key;
				$.each(strs,function(i,str){
					var ztreeNodes = ztreeObj.getNodesByParam(key, str, null);
					$.each(ztreeNodes,function(i,item){
						ztreeObj.selectNode(item);
						ztreeObj.setting.callback.onClick(null, $id.attr('id'), item);
					})
				})
			})
		}
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	},
	insertStaticRow : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		if(!$id && !$id[0]){
			//无对象时跳过
			return;
		}
		var inparamobjrule = $id.data("inparamobjrule");
		
		//集合信息
		var arySource = inparamobjrule["arySource"];
		if($.isArray(arySource)){
			//数据转换为如下：集合：对象
			//{ary1495094362175: "obj1495077696961"}
			//若是数组，转换为json数据
			var arySourceArray = arySource;
			arySource = {};
			$.each(arySourceArray,function(i,item){
				var child = item.child;
				var objName = "";	//对象名称
				if(child && child[0]){
					objName = child[0].param;
				}
				arySource[item.param] = objName;
			})
			inparamobjrule["arySource"] = arySource;
		}
		// console.log(arySource);
		//对象信息
		var objSource = inparamobjrule["objSource"];
		if($.isArray(objSource)){
			//转换为如下数据，参数：映射字段
			// {
			// 	"obj1495077696961": {
			// 		"col1495077685661": "r_glafdb_tb3_0_r_glafdb_tb3_col9",
			// 		"col1495077685657": "r_glafdb_tb3_0_r_glafdb_tb3_col8",
			// 		"col1495077685651": "r_glafdb_tb3_0_r_glafdb_tb3_col7",
			// 		"col1495077685646": "r_glafdb_tb3_0_r_glafdb_tb3_col6",
			// 		"col1495077685642": "r_glafdb_tb3_0_r_glafdb_tb3_col5"
			// 	},
			// }
			
			//若是数组，则将数组转换为json数据
			var objSourceArray = objSource;
			objSource = {};
			$.each(objSourceArray,function(i,item){
				var child = JSON.parse(item.child);
				var param = {};
				$.each(child,function(i,item){
					param[item.param] = item.columnFiled;
				})
				objSource[item.param] = param;
			})
			inparamobjrule["objSource"] = objSource;
		}
		if(!objSource){
			//无对象信息时，无法插入
			return;
		}

		//寻找对象赋值
		var datas = [];
		var data = null;
		$.each(args,function(key,value){
			if($.isPlainObject(value) && !$.isEmptyObject(value)){
				//为对象时
				data = {};
				var nowInparamObj = objSource[key];
				$.each(value,function(key2,value2){
					if(nowInparamObj[key2]){
						//注释掉为"sss,ssss"的格式.
						// var value2Array = value2.split(",");
						// $.each(value2Array,function(i,item){
						// 	if(datas.length <= i){
						// 		datas.push({});
						// 	}
						// 	datas[i][nowInparamObj[key2]] = item;
						// })
						data[nowInparamObj[key2]] = value2;
					}
				})
				datas.push(data);
			}else if($.isArray(value)){
				//为集合时
				if(!arySource[key]){
					//集合中无对象信息
					return true;
				}

				var nowInparamObj = objSource[arySource[key]];
				$.each(value,function(i,item){
					data = {};
					$.each(item,function(key2,value2){
						if(nowInparamObj[key2]){
							data[nowInparamObj[key2]] = value2;
						}
					})
					datas.push(data);
				})
			}

		})

		if(datas && datas.length > 0){
			//grid插入数据
			
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		}
	}
};
if(typeof pubsub != 'undefined'){
	pubsub.sub("ztree", ztreeFunc);
}

function expandNodesFunc(ztreeObj,expandNodes,expandChilds){
	var ztreeObj = ztreeObj;
	var expandChilds = expandChilds;
	if(expandChilds && expandChilds >= 2 && expandNodes && expandNodes.length>0){
		$.each(expandNodes,function(i,item){
			ztreeObj.expandNode(item, true, false, true);
			expandNodesFunc(ztreeObj,item.children,expandChilds - 1);
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
				if (!isDeep) {
					return retVal;
				}
			} else {
				childVal = getChildByParam(children, field, exp, outFiled, isDeep);
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
function isMobile(){
	var userAgentInfo = navigator.userAgent;  
	var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
	var flag = false;  
	for (var v = 0; v < Agents.length; v++) {  
		if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = true; break; }  
	}  
	return flag;  
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
				var treeIdValue = "";
				if (nodes.length == 1) {
					pidkey = nodes[0].pidkey;
					treeIdValue = nodes[0].idkey;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&pid=' + pidkey + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose + '&treeIdValue=' + treeIdValue;
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
		var SB = "<iframe width='100%' height='280' src='" + link + "' frameborder='no' scrolling='no'></iframe>";
		window.ztreeOpenShow = window.show({
			title: '增加/编辑节点',
			size: BootstrapDialog.SIZE_WIDE,
			message: SB,
			modal: true,
			draggable: true,
			css: {
				width: 400,
				height: 300
			}
		});
	},
	init: function(id, options,ztreeNodesData) {
		var ztreeObj = null;
		
		// $('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/demo.css">');
		
		
		
		

		var $this = $('#' + id),
			_bindEvent_ = $this.data("_bindEvent_");
		
		$this.data("expandChilds",options.expandChilds || 1);
		//选中该节点和子节点
		var chkboxType = options.check.chkboxType;
		if(options.check.chkboxType == '2'){
			chkboxType = { "Y" : "s", "N" : "s" };
		}
		
		//选中该节点的父节点
		if(options.check.chkboxType == '1'){
		    chkboxType = { "Y" : "p", "N" : "p" };
		}
		//之选中该节点
		if(options.check.chkboxType == '0'){
		    chkboxType = { "Y" : "", "N" : "" };
		}
		//之选中该节点
		if(options.check.chkboxType == '3'){
		    chkboxType = { "Y" : "ps", "N" : "ps" };
		}
	    options.check.chkboxType = chkboxType;
	    //当选择框样式选为单选时
	    options.check.chkStyle == "radio" ? (options.check.radioType = "all") : (undefined);
		/*var check = {
				enable: true,
        	checkboxType : chkboxType
        }
		options.check = check;*/
		
		if(isMobile() || $this.attr("zTreeStyle") == 'awesome'){
			options.callback.beforeClick = function (treeId, treeNode, clickFlag) {
			    //treeNode.checked = treeNode.checked?false:true;
			    //ztreeObj.refresh();
			    if(!treeNode.chkDisabled){
			    	ztreeObj.checkNode(treeNode, !treeNode.checked, true);	
			    }
			    return true;
			};

			glafZtree.trunToMobileStyle(options);
		}

		if(ztreeNodesData){
			
			ztreeObj = $.fn.zTree.init($this, options, ztreeNodesData);
		}else{
			ztreeObj = $.fn.zTree.init($this, options);
		}

		

		$this.data("kkk",ztreeObj);		
		if (_bindEvent_) {
			$.each(_bindEvent_, function(i, bindEvent) {
				pubsubobjects["ztree"].call($this, bindEvent);
			})
		}
		if (options) {
			$("#ztree_" + id + "_add").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){
					ztreeObj.addNodes(null,[{name:"newNode1"}] );
				}else{
					glafZtree.ztreeToolbar('add', id, options.opts.ruleId, options.opts.editPanleAutoClose);	
				}
				
			});
			$("#ztree_" + id + "_add_next").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){
					var nodes = ztreeObj.getSelectedNodes();
					if(nodes){
						ztreeObj.addNodes(nodes[0],[{name:"newNode1"}] );
					}

				}else{
					glafZtree.ztreeToolbar('next', id, options.opts.ruleId, options.opts.editPanleAutoClose);
				}
			});
			$("#ztree_" + id + "_edit").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){

				}else{
					glafZtree.ztreeToolbar('edit', id, options.opts.ruleId, options.opts.editPanleAutoClose);
				}
			});
			$("#ztree_" + id + "_del").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){

				}else{
					glafZtree.ztreeToolbar('del', id, options.opts.ruleId, options.opts.editPanleAutoClose);
				}
			});
		}
	},
	trunToMobileStyle : function(options){
		if(!$("[href='"+contextPath+"/scripts/ztree/css/awesomeStyle/awesome.css']")[0]){
			$('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/awesomeStyle/awesome.css">');
			$('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/zTreeStyle/font-awesome.min.css">');
			$('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/awesomeStyle/ztree.extend.css">');
			$("[href='"+contextPath+"/scripts/ztree/css/zTreeStyle/zTreeStyle.css']").remove();
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