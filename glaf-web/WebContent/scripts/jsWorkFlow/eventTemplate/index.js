var setting = {
	view : {
		showIcon : true,
		showLine : true,
		showTitle : false,
		selectedMulti : false
	},
	check : {
		enable : false,
		chkStyle : "checkbox",
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	},
	async : {
		enable : true,
		url : contextPath + "/mx/form/formEventTemplateTree/treeData",
		autoParam : [ "treeId=zId" ],
		otherParam : {
			"type" : 1
		},
		dataFilter : function(treeId, parentNode, childNodes) {
			if (!childNodes)
				return null;
			for (var i = 0, l = childNodes.length, childNode; i < l; i++) {
				childNode = childNodes[i];
				if (i == 0) {
					childNode.open = true;
				}
			}
			return childNodes;
		}
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "indexId",
			pIdKey : "parentId"
		}
	},
	edit : {
		drag : {
			isCopy : true
		},
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			mtxx.selectNode = treeNode;
			$("#grid").jqGrid('setGridParam', {
				postData : {
					parentId : treeNode.indexId
				}
			}).trigger("reloadGrid");
		}
	}
};

var pageTreeSetting = {
	view : {
		showIcon : true,
		showLine : true,
		showTitle : false,
		selectedMulti : false
	},
	check : {
		enable : false,
		chkStyle : "checkbox",
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	},
	async : {
		enable : true,
		url : contextPath + "/mx/form/defined/getPageHierarchicalAssembly?pageId=" + pageId + "&showEvent=false&isTrigger=false&isGroup=true",
		dataFilter : function(treeId, parentNode, childNodes) {
			if (!childNodes)
				return null;
			for (var i = 0, l = childNodes.length, childNode; i < l; i++) {
				childNode = childNodes[i];
				if (childNode.icon) {
					childNode.icon = contextPath + childNode.icon;
				}
				if (i == 0) {
					childNode.open = true;
				}
			}
			return childNodes;
		}
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pId"
		}
	},
	edit : {
		drag : {
			isCopy : true
		},
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	callback : {
		beforeDrag : function(treeId, treeNodes) {
			return !!treeNodes[0].isEle || !!treeNodes[0].isPage;
		},
		beforeDrop : function(treeId, treeNodes, targetNode, moveType) {
			return false;
		},
		onDragMove: function(event, treeId, treeNodes){
			var treeNode = treeNodes[0], drole = treeNode.drole, $target = $(event.target);
			$(".pageTreeTarget").removeClass("mt-sel");
			if($target.hasClass("pageTreeTarget") && drole == $target.attr("data-role")){
				$target.addClass("mt-sel");
			}
		},
		onDrop : function(event, treeId, treeNodes, targetNode, moveType) {
			var treeNode = treeNodes[0], drole = treeNode.drole, $target = $(event.target),nodeName;
			if ($target.hasClass("pageTreeTarget") && drole == $target.attr("data-role")) {
				filterNode(treeNode);
				nodeName = treeNode.name+"->"+treeNode.eleId;
				$target.data("targetData",{
					id:$target.attr("data-id"),
					name:nodeName,
					input:treeNode.name,
					input_data:treeNodes
				});
				$target.html(nodeName);
				$target.removeClass("mt-sel-err");
			}
			$(".pageTreeTarget").removeClass("mt-sel");
		}
	}
};

function formatButton(cellValue, options, rowObject) {
	var shtml = "<button class='btn btn-primary grid-btn' data-type='select' data-id='" + rowObject.id + "'>选择</button> &nbsp;";
	shtml += "<button class='btn btn-primary grid-btn' data-type='view' data-id='" + rowObject.id + "'>查看</button>";
	return shtml;
}
/**
 * 保存模板
 * 
 * @returns
 */
function saveTemp(node, $this) {
	if ($("#ffname").val() == "") {
		BootstrapDialog.alert("请填写模板名称");
		return;
	}
	$.ajax({
		url : contextPath + "/mx/form/formEventTemplate/saveFormEventTemplate",
		type : "POST",
		data : {
			pId : node.indexId,
			complexId : complexId,
			name : $("#ffname").val(),
			remark : $("#fremark").val(),
		},
		success : function(data) {
			$this && $this.removeAttr('disabled');
			if (data && (data = JSON.parse(data)) && data.statusCode == 200) {
				$("#myModal2").modal("hide");
				BootstrapDialog.alert("保存成功");
				$("#grid").jqGrid('setGridParam', {
					postData : {
						parentId : node.indexId
					}
				}).trigger("reloadGrid");
			} else {
				BootstrapDialog.alert("保存失败");
			}
		},
		error : function() {
			BootstrapDialog.alert("保存失败");
			$this && $this.removeAttr('disabled');
		}
	});
}
var gridBtnFun = {
	/**
	 * 选择按钮
	 */
	select : function(e, id) {
		var dataObj = $("#grid").jqGrid('getRowData',id),
			rules = dataObj.rule && JSON.parse(dataObj.rule),
			rule,
			inputRule,
			eleId,
			htmlStr = ["<table style='width:100%'>","<thead><tr><th>模板控件</th><th>映射控件</th></tr></thead>","<tbody>"];
		if(rules){
			window.lateRule = rules;
			window.selGridObj = dataObj;
			window.replaceStore = {};
			for(var key in rules){
				if(key.indexOf('ChinaissContainers')>=0){
					rule = rules[key];
					inputRule = rule["input_data"]["0"];
					eleId = inputRule["eleId"];
					if(!(eleId in replaceStore)){
						htmlStr.push("<tr><td>"+rule.name+"</td><td><div class='form-control pageTreeTarget' data-role='"+inputRule["drole"]+"' data-id='"+eleId+"' >请拖拽进来</div></td></tr>");
						replaceStore[eleId] = [];
					}
					if($.inArray(key,replaceStore[eleId]) < 0){
						replaceStore[eleId].push(key);
					}
				}else if(key.indexOf('ChinaissDataOperate')>=0){
					//直接更新pageid
					var ow = lateRule[key]["outWidget"][0],
						pObj = JSON.parse(ow.pObj);
					pObj.eleId = pObj.pageId = pageId;
					ow.pObj = JSON.parse(pObj);
				}else if(key.indexOf('ChinaissGetNode')>=0){
					rule = rules[key];
					$.each(rule["minput_data"],function(i,mrule){
						eleId = mrule["eleId"];
						if(!(eleId in replaceStore)){
							htmlStr.push("<tr><td>"+mrule.name+"->"+mrule.eleId+"</td><td><div class='form-control pageTreeTarget' data-role='"+mrule["drole"]+"' data-id='"+eleId+"' >请拖拽进来</div></td></tr>");
							replaceStore[eleId] = [];
						}
						if($.inArray(key,replaceStore[eleId]) < 0){
							replaceStore[eleId].push(key);
						}
					})
				}
			}
		}
		htmlStr.push("</tbody>");
		htmlStr.push("</table>");
		$("#mappingDiv").html(htmlStr.join(""));
		$("#myModal3").modal();
	},
	/**
	 * 查看按钮
	 */
	view : function(e, id) {
		var url = contextPath + "/mx/form/defined/workflow/workflow2?pageId=" + pageId + "&isTemplate=true&templateId=" + id;
		window.open(url);
	}
};
jQuery(document).ready(function() {
	$(".mtbtn").on("click", function(e) {
		var $this = $(this), type = $this.attr("data-type"), action = $this.attr("data-actType"), node = mtxx.selectNode, nodeId = "", parentId = "", fname = "", treeId = "";
		$("#type").val(type);
		$("#actType").val(action);
		$("#pname_row").hide();
		switch (action) {
		case "delete":
			$this.attr('disabled');
			$.ajax({
				url : contextPath + "/rs/form/formEventTemplateTree/delete",
				type : "POST",
				data : {
					id : node.id
				},
				success : function(data) {
					if (data && data.statusCode == 200) {
						BootstrapDialog.alert("删除成功");
						mtxx.zTreeObj.reAsyncChildNodes(mtxx.selectNode.getParentNode(), "refresh");
					} else {
						BootstrapDialog.alert("删除失败");
					}
					$this && $this.removeAttr('disabled');
				},
				error : function() {
					BootstrapDialog.alert("保存失败");
					$this && $this.removeAttr('disabled');
				}
			})
			return;
		case "addBrother":
			parentId = node ? node["parentId"] : "";
			treeId = node && node.getParentNode() ? node.getParentNode()["treeId"] : "";
			break;
		case "addChild":
			if (!node) {
				BootstrapDialog.alert("请先选择分类");
				return;
			}
			treeId = node["treeId"];
			parentId = node["indexId"];
			$("#pname").val(node.name);
			$("#pname_row").show();
			break;
		default:
			if (!node) {
				BootstrapDialog.alert("请先选择分类");
				return;
			}
			nodeId = node.id;
			fname = node.name;
			break;
		}
		$("#fname").val(fname);
		$("#nodeId").val(nodeId);
		$("#parentId").val(parentId);
		$("#treeId").val(treeId);
		$("#myModal").modal();
	})
	
	$("#mtSelect").on("click",function(e){
		var $this = $(this),
			bool = true,
			$target,targetData,
			lateRule = window.lateRule,
			selGridObj = window.selGridObj,
			getNodeIds = [];
		$(".pageTreeTarget").each(function(i,el){
			$target = $(el),
			targetData = $target.data("targetData"),
			targetDataId = $target.attr("data-id");
			if(!targetData){
				$target.addClass("mt-sel-err");
				bool = false;
			}else{
				$.each(replaceStore[targetDataId],function(i,key){
					if(key.indexOf('ChinaissContainers')>=0){
						targetData["id"] = key;
						lateRule[key] = targetData;
					}else if(key.indexOf('ChinaissGetNode')>=0){
						getNodeIds.push(key);
						var t_in_data = targetData["input_data"][0];
						lateRule[key]["id"] = key;
						$.each(lateRule[key]["minput_data"],function(i,v){
							if(v.eleId == targetDataId){
								lateRule[key]["minput_data"][i] = t_in_data;
							}
						})
					}
				})
				//lateRule[$target.attr("data-id")] = targetData;
			}
		});
		//更新多参数构件的名称
		$.each(getNodeIds,function(i,nodeId){
			// minput and name
			var name = "";
			$.each(lateRule[nodeId]["minput_data"],function(j,md){
				name += md.name + "," ;
			});
			lateRule[nodeId]["name"] = lateRule[nodeId]["minput"] = name;
		});
		if(bool){
			console.log(lateRule);
			var coreRuleStr = selGridObj.complexRule,
				coreRule = JSON.parse(coreRuleStr);
			$.each(coreRule,function(i,crule){
				coreRule[i] = lateRule[crule.id];
			})
			$.ajax({
				url: contextPath + "/mx/form/formEventComplex/saveFormEventComplex2",
				type: "POST",
				data: {
					id: complexId,
					sourceId: selGridObj.id,
					rule: JSON.stringify(lateRule),
					name: selGridObj.name,
					remark: selGridObj.remark,
					complexRule: JSON.stringify(coreRule),
					pageId: pageId
				},
				success: function(data) {
					if(data && (data = JSON.parse(data)) && !data.statusCode ){
						$("#myModal3").modal("hide");
						BootstrapDialog.alert("保存成功");
						if(parent.location.href != window.location.href){
							parent.modeling.updateProperties(parent.mtxx.selectElement,{viewId:data.id,remark:data.remark});
							parent.cli.setLabel(parent.mtxx.selectElement,data.name);
							parent.mtxx.zTree3Obj.reAsyncChildNodes(null, "refresh");
							parent.mt_dialog.close();
							parent.complexEdit(data.id,true);
						}
					}else{
						BootstrapDialog.alert("保存失败");
					}
					$this && $this.removeAttr('disabled');
				},
				error: function() {
					BootstrapDialog.alert("保存失败");
					$this && $this.removeAttr('disabled');
				}
			});
		}
		//BootstrapDialog.warning("请映射控件");
		
	});

	$("#grid").on("click", ".grid-btn", function(e) {
		var $this = $(this), type = $this.attr("data-type"), id = $this.attr("data-id");
		gridBtnFun[type].call($this, e, id);
	});

	if (isSave) {
		$(".mt-ztree-tools").show();
		$("#saveBt").removeAttr("disabled");
	}
	// $("#myTab").find("li.active").index();

	mtxx.zTreeObj = $.fn.zTree.init($("#ztree"), setting);
	mtxx.pageTree = $.fn.zTree.init($("#pageTree"), pageTreeSetting);

	$("#tempSave").on("click", function(e) {
		saveTemp(mtxx.selectNode, $(this));
	});
	/**
	 * 保存按钮
	 */
	$("#saveBt").on("click", function(e) {
		if (!mtxx.selectNode) {
			BootstrapDialog.alert("请选择分类");
			return;
		}
		$("#myModal2").modal();
	});
	/**
	 * 增加修改分类 保存按钮
	 */
	$("#mtSave").on("click", function(e) {
		var $this = $(this);
		$this.attr('disabled');
		$.ajax({
			url : contextPath + "/mx/form/formEventTemplateTree/saveFormEventTemplateTree",
			type : "POST",
			data : {
				id : $("#nodeId").val(),
				parentId : $("#parentId").val(),
				name : $("#fname").val(),
				type : $("#type").val(),
				treeId : $("#treeId").val(),
			},
			success : function(data) {
				if (data && (data = JSON.parse(data)) && data.statusCode == 200) {
					BootstrapDialog.alert("保存成功");
					$("#myModal").modal("hide");
					var actType = $("#actType").val(), node = mtxx.selectNode;
					if (!(actType == "addChild")) {
						node = mtxx.selectNode.getParentNode();
					}
					mtxx.zTreeObj.reAsyncChildNodes(node, "refresh");
				} else {
					BootstrapDialog.alert("保存失败");
				}
				$this && $this.removeAttr('disabled');
			},
			error : function() {
				BootstrapDialog.alert("保存失败");
				$this && $this.removeAttr('disabled');
			}
		})
	});

	$("#grid").jqGrid({
		width : 800,
		height : "100%",
		url : contextPath + '/rs/form/formEventTemplate/list',
		mtype : "POST",
		postData : {
			parentId : null
		},
		datatype : "json",
		colNames : isSave ? [ '名称', '备注', '规则', '主键', '图形','暴露规则' ] : [ '操作列', '名称', '备注', '规则', '主键', '图形','暴露规则' ],
		colModel : isSave ? [ {
			name : 'name',
			width : 100
		}, {
			name : 'remark'
		} ,{
			name : 'rule',
			hidden:true
		}, {
			name : 'id',
			hidden:true
		}, {
			name : 'diagram',
			hidden:true
		}, {
			name : 'complexRule',
			hidden:true
		}] : [ {
			name : 'handle',
			width : "42px",
			formatter : formatButton
		}, {
			name : 'name',
			width : 100
		}, {
			name : 'remark'
		}, {
			name : 'rule',
			hidden:true
		}, {
			name : 'id',
			hidden:true
		}, {
			name : 'diagram',
			hidden:true
		}, {
			name : 'complexRule',
			hidden:true
		}],
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : '#pager',
		sortname : 'name',
		viewrecords : true,
		emptyrecords : "无数据",
		sortorder : "desc"
	});

});
