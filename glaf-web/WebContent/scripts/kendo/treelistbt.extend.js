/**
 * 树型网格
 */
var treelistbtFunc = {
	/**
	 * 插入静态数据
	 * @param {[type]} rule [规则]
	 * @param {[type]} args [输入形参]
	 * @param {[type]} obj  [description]
	 */
	insertStaticRow: function(rule,args,obj){
		var $id = pubsub.getJQObj(rule);
		var inparamobjrule = $id.data("inparamobjrule");
		if(!$id && !$id[0]){
			//无对象时跳过
			return;
		}
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
			// 	"obj1495077686857": {
			// 		"col1495077685625": "r_glafdb_tb3_0_treeid",
			// 		"col1495077685623": "r_glafdb_tb3_0_parent_id",
			// 		"col1495077685622": "r_glafdb_tb3_0_index_id",
			// 		"col1495077685620": "r_glafdb_tb3_0_topid",
			// 		"col1495077685617": "r_glafdb_tb3_0_id"
			// 	}
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
			$id.treelist("insertStaticRow",datas);
		}
		
	},
	getRow : function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule,true).treelist("getSelectedRows"),val=[];
		if(dataItems && dataItems.length){
			$.each(dataItems,function(i,o){
				if(o[rule.columnName]){
					val.push(o[rule.columnName]||"");
					pubsub.outParamsObj.call(i,rule, obj, o);
				}
			});
			return val.join(",");
		}
		return "";
	},
	//获取当前点击行节点
	getCurRow: function(rule, args) {
		if (args && args[1]) {
			return args[1][rule.columnName] || "";
		}
		return "";
	},
	getValue : function(rule, args) {
		return pubsub.getJQObj(rule,true).closest("a").text();
	},
	linkage: function(rule, params) {
		treelistbtFunc.linkageControl(rule, params);
	},
	linkageControl : function(id, params) {
		pubsub.getJQObj(id).data("treelist").options.expandChilds = params.expandLevel||true;
		pubsub.getJQObj(id).treelist("query", params);
	},
	getGrade : function(rule,args){
		var dataItems = pubsub.getJQObj(rule,true).treelist("getSelectedRows"),val=[];
		return ""+(dataItems[0]._level_+1);
	},
	selectRow : function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.treelist("getData");

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		var key = args.key;
		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key].indexOf(v) > -1){
				$id.treelist("select",item.id);
			}
		})

		// if (rows && rows.length) {
		// 	$.each(rows,function(i,row){
		// 		var idVal = args[rule[0].param];
		// 		if(idVal){
		// 			var  pas = idVal.split(",");
		// 			for(var j=0;j<pas.length;j++){
		// 				if(row.id == pas[j]){
		// 					$id.treelist("select",row.id);
		// 				}
		// 			}
		// 		}
		// 	});
		// }
	},
	cancelSelect:function(rule, args){
		var $id = pubsub.getJQObj(rule).treelist("cancelSelect");
	},
	getAll : function(rule, args) {
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("treelist").getData(), dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName]||"");
				}
				return retAry.join(",");
			}
		}
		return "";
	},
	setRowBgColor: function(rule, args){
		pubsub.getJQObj(rule).treelist("setRowBgColor", args.rowIndex, args.color);
	},
	tSelectFirst: function(rule, args) { //默认选中第一行
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			setTimeout(function() {				
				$id.treelist("select","","tr:eq(0)");
				var ink = new MutationObserver(function(record) {
					$id.treelist("select","","tr:eq(0)");
					ink.disconnect();
				});
				ink.observe($id[0], {
					'childList': true,
					'arrtibutes': true,
					'subtree': true,
					'characterData': true
				});
			}, 100);
		}
	},
	grefresh: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.treelist("query", $.isArray(args)?{}:args);
	},
	gsaveChange:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.treelist("saveHandle");
	},
	//展开并刷新子节点
	refreshAndExpandNode : function(rule,args){
		var expandNodeId = args.expandNodeId;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				$id.treelist("refreshAndExpandNode",item);
			}
		})
	},
	
	//展开并刷新子节点的父节点
	parentRefresh : function(rule,args){
		var expandNodeId = args.expandNodeId;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var pItemNode = $id.treelist("getRow",$id.treelist("getNodeById",$id.treelist("getParentNodeId",$id.treelist("getSelections")),$id));
		pItemNode && $id.treelist("refreshAndExpandNode",pItemNode);
	},
	
	
	//修改当前节点内容
	nodeRefresh:function(rule,args){
		var expandNodeId = args.expandNodeId;
		var rename = args.rename;
		var text = args.text;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param == 'expandNodeId')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				$id.treelist("nodeRefresh",item,rename,text);
			}
		})
		
	},
	
	
	expandNode:function(rule,args){
		var expandNodeId = args.expandNodeId;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				rowIndex = item["row-index"];
				$id.find("tr[row-index='"+rowIndex+"'] .treelist-expander").click();
			}
		})
	},
	//取消编辑事件
	endEdit: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.treelist("endEdit");
	},

	getKeyName : function(rule,args){
		return rule.columnName;
	},

	getCheckedRow: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).treelist("getCheckedRowsData"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
					pubsub.outParamsObj(rule, obj, o);
				}
			});
			return val.join(",");
		}
		return "";
	}, 

	checkRow : function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.treelist("getData");

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		var key = args.key;
		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				$id.treelist("checkRow",null,null,item);
			}
		})
	}, 

	isCheckedInput: function(rule, args) {
		if (args && args[1]) {
			var $jq = pubsub.getJQObj(rule, true),
				g = $jq.data("grid");
			return g.tbody().find("tr:eq(" + args[1]["row-index"] + ") .inner_checkbox").prop("checked");
		}
		return false;
	},
	editRow: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.treelist("getData");

		var key = args.key;
		key = key || 'id';

		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'key'){
				return true;
			}
			value = args[rule[i].param] || "";
		})

		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				var idVal = value;
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.treelist("editRow", row.id);
							}else{
								$id.treelist("editRow", null,null,row);
							}
						}
					}
				}
			});
		}
		
		// var $id = pubsub.getJQObj(rule);
		// var rows = $id.treelist("getData");
		// if (rows && rows.length) {
		// 	$.each(rows, function(i, row) {
		// 		var idVal = args[rule[0].param];
		// 		if (idVal) {
		// 			var pas = idVal.split(",");
		// 			for (var j = 0; j < pas.length; j++) {
		// 				if (row.id == pas[j]) {
		// 					$id.treelist("editRow", row.id);
		// 				}
		// 			}
		// 		}
		// 	});
		// }
	},
};
pubsub.sub("treelistbt", treelistbtFunc);


