var nestableFunc = {
	//获取拖拽格
	getNowNest : function(rule, args){
		var $id = pubsub.getJQObj(rule, true);
		if (!$id) {
			$id = pubsub.getJQObj(rule.inid, true);
		}
	   var build = $id.data("nestable");
	   var retVal = "";
	   var data = build.getNestData();
	   if (data.length) {
			for (var i = 0, len = data.length; i < len; i++) {
				retVal += data[i][rule.columnName] || "";
				retVal += (i == len - 1) ? "" : ",";
			}
			//return nodes[0][rule.columnName];
		}
	   return retVal;
	   
	},
	//刷新
	refresh : function(id,args){
	    var $id = pubsub.getJQObj(id);
		var build = $id.data("nestable");
		build.refresh();
	},
	//静态数据插入
	insertStaticNestData: function(rule,args,obj){
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
			$id.nestable("saveStaticData",datas);
		}
		
	}
};
pubsub.sub("nestable", nestableFunc);
