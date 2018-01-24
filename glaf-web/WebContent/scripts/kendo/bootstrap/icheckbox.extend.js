/**
 * Metronic icheckbox
 */
var icheckboxFunc = {
	getSelection : function(rule, args) {
		return pubsub.getJQObj(rule).icheckbox("getSelection");
	},
	getText:function(rule, args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		// if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
		// 	retVal.push($(args[0].currentTarget).attr("text")||"");
		// }else{         //控件获取的值
			var selections = $id.icheckbox("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.text);
			});
		// }
		return retVal.join(",");
	},
	getValue:function(rule,args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		// if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
		// 	retVal.push($(args[0].currentTarget).attr("value")||"");
		// }else{         //控件获取的值
			var selections = $id.icheckbox("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.value);
			});
		// }
		return retVal.join(",");
	},
	getData:function (rule,args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
//			var datas = $(args[0].delegateTarget).data("icheckbox").options.__dataArray;
			var datas =  $id.data("icheckbox").options.__dataArray;
			var index = $(args[0].currentTarget).attr("row-index")||"";
			if(datas.length>0){
				retVal.push(datas[index][rule.columnName]);
			}
		}else{         //控件获取的值
			var selections = $id.icheckbox("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v["data"][rule.columnName]);
			});
		}
		return retVal.join(",");
	},
	setValue:function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.icheckbox("setValue",v);
		} else {
			$id.icheckbox("setValue",args);
		}
	},
	
	//联动事件
	linkage: function(rule, args) {
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.icheckbox("linkage",args);
		
	},
	
	getKeyName : function(rule,args){
		return rule.columnName;
	},
	
	
	tdisabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckbox("disabled",false);
	},
	tenabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckbox("disabled",true);
	},
	disable: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("disable", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type:'disable'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	enable: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("enable", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type:'enable'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	check: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("check", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type : 'check'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	uncheck: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("uncheck", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type : 'uncheck'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	checkAll: function(rule, args){
		pubsub.getJQObj(rule).icheckbox("checkAll");
	},
	uncheckAll: function(rule, args){
		pubsub.getJQObj(rule).icheckbox("uncheckAll");
	},
	invert: function(rule, args){
		pubsub.getJQObj(rule).icheckbox("invert");
	}
};
pubsub.sub("icheckbox", icheckboxFunc);