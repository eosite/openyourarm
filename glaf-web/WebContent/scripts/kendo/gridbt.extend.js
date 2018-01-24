/**
 * 网格
 */
var gridbtFunc = {
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
			$id.grid("insertStaticRow",datas);
		}
		
	},
	getRow: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).grid("getSelectedRows"),
		    item = pubsub.getJQObj(rule, true).data("grid"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					var td = $(item.target).find("td[field="+rule.columnName+"]")[(o.row_number)-1];
					if($(td).find("input[type=radio]")[0] != undefined){
						$.each($(td).find("input[type=radio]"),function(i,j){
							 if(j.checked){
								 val.push(j.value || "");
							 }
						});
					}
					else{
						val.push(o[rule.columnName] || "");
						pubsub.outParamsObj.call(i,rule, obj, o);
					}
					
				}
			});
			return val.join(",");
		}
		return "";
	},
	getUnselectRow: function(rule, args) {
		var dataItems = pubsub.getJQObj(rule, true).grid("getUnselectedRows"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
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
	
	getText : function(rule,args){
		//当点击的单元格获取的非文本时
		var ret = '';
		var dataItems = pubsub.getJQObj(rule, true).data("grid");
		var td = $(dataItems.target).find("td[field="+args[1]+"]")[args[0]];
		if($(td).find("input[type=radio]")[0] != undefined){
			$.each($(td).find("input[type=radio]"),function(i,j){
				 if(j.checked){
					 ret = j.value
				 }
			});
		}
		else{
			ret = args[2]
		}
		return ret;
	},
	getCurColumn : function(rule, args){	
		var dataItems = pubsub.getJQObj(rule, true).data("grid");
		var obj = args[3];
		
		var item = dataItems.options.columns;
		var value = ""
		$.each(item,function(i,v){
			if(v.field == args[1]){
				value = v.title;
			}
		})
		return value;
	},
	isChecked: function(rule, args) {
		if (args && args[1]) {
			var $jq = pubsub.getJQObj(rule, true),
				g = $jq.data("grid");
			return g.tbody().find("tr:eq(" + args[1]["row-index"] + ")").hasClass(g.options.selectedCls);
		}
		return false;
	},
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).closest("a").text();
	},
	linkage: function(rule, params) {
		gridbtFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) {
		params["databaseId"] && pubsub.getJQObj(id).attr("dbid", params["databaseId"]);
		var $id = pubsub.getJQObj(id);
		var options = $id.data("grid.options").options;
		if (options && options.pagination.page) {
			options.pagination.page = 1;
		}
		pubsub.getJQObj(id).grid("query", params);
	},
	selectRow: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

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
								$id.grid("select", row.id);
							}else{
								$id.grid("select", null,null,row);
							}
						}
					}
				}
			});
		}
	},
	//根据传入参数勾选，grid中的对应的行的复选框的事件
	checkRecord:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var values=args[rule[0].param];
		
		var key = args.key;
		key = key || 'id';

		var rows = $id.grid("getData");
		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				var idVal = args[rule[0].param];
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.grid("checkRow", row.id);
							}else{
								$id.grid("checkRow", null,null,row);
							}
						}
					}
				}
			});
		}
	},
	cancelSelect: function(rule, args) {
		var $id = pubsub.getJQObj(rule).grid("cancelSelect");
	},
	getAll: function(rule, args, obj) {
		var $in = pubsub.getJQObj(rule, /*args[0] ||*/ true);
		if ($in) {
			var retAry = [],
				dataItems = $in.data("grid").getData(),
				dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName] || "");

					pubsub.outParamsObj.call(i,rule, obj, dataItem);
				}
				return retAry.join(",");
			}
		}
		return "";
	},
	setRowBgColor: function(rule, args) {
		pubsub.getJQObj(rule).grid("setRowBgColor", args.rowIndex, args.color);
	},
	tSelectFirst: function(rule, args) { //默认选中第一行
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			setTimeout(function() {
				var flag = $id.grid("select", "", "tr:eq(0)");
				if(!flag){
					var ink = null;
					ink = $id.data("mutationObserver");
					if(ink){
						ink.disconnect();
						$id.data("mutationObserver","");
					}
					ink = new MutationObserver(function(record) {
						$id.grid("select", "", "tr:eq(0)");
						ink.disconnect();
						$id.data("mutationObserver","");
					});
					$id.data("mutationObserver",ink);
					ink.observe($id[0], {
						'childList': true,
						'arrtibutes': true,
						'subtree': true,
						'characterData': true
					});
				}
			}, 100);
		}
	},
	grefresh: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.grid("query", $.isArray(args) ? {} : args);
	},
	gsaveChange: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.grid("saveHandle");
	},
	getTotal: function(rule, args) {
		return pubsub.getJQObj(rule, true).grid("getTotal") || 0;
	},
	//取消编辑事件
	endEdit: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.grid("endEdit");
		pubsub.execChilds(rule);
	},
	//隐藏工具栏
	hideToolbar: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.find(".grid-toolbar").hide();
	},
	//显示工具栏
	showToolbar: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.find(".grid-toolbar").show();
	},
	editRow: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

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
								$id.grid("editRow", row.id);
							}else{
								$id.grid("editRow", null,null,row);
							}
							// $id.grid("editRow", row.id);
						}
					}
				}
			});
		}
	},
	addRow: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.grid("_newRow");
		
	},
	getCheckedRow: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).grid("getCheckedRowsData"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
					pubsub.outParamsObj.call(i,rule, obj, o);
				}
			});
			return val.join(",");
		}
		return "";
	}, 

	isCheckedInput: function(rule, args) {
		if (args && args[1]) {
			var $jq = pubsub.getJQObj(rule, true),
				g = $jq.data("grid");
			return g.tbody().find("tr:eq(" + args[1]["row-index"] + ") .inner_checkbox").prop("checked");
		}
		return false;
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	},

	//设置行不可编辑状态
	setEditDisabled : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

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
								$id.grid("setEditDisabled", row[key]);
							}else{
								$id.grid("setEditDisabled", null,null,row);
							}
						}
					}
				}
			});
		}
	},

	//设置行可编辑状态
	setEditEnabled : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

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
								$id.grid("setEditEnabled", row[key]);
							}else{
								$id.grid("setEditEnabled", null,null,row);
							}
						}
					}
				}
			});
		}
	},
	//隐藏操作列
	hideCommandCell: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var $col = $id.find(".grid-header colgroup col");

		if(!$id.find(".commandCell").is(":hidden")){
			var ary = [];
			$.each($col,function(i,item){
				ary.push($(item).width());
			})
			$id.data("commandWidthAry",ary);
		}
		$id.data("hidecommandCell",true);
		$id.find(".commandCell").hide();
	},
	//显示操作列
	showCommandCell: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		
		var ary = $id.data("commandWidthAry");
		var $col = $id.find(".grid-header colgroup col");
		$.each(ary,function(i,item){
			$($col[i]).width(item);
		})
		$id.data("hidecommandCell",false);
		$id.find(".commandCell").show();
	}
};

pubsub.sub("gridbt", gridbtFunc);