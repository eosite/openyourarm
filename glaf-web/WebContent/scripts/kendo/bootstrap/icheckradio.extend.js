/**
 * Metronic icheckradio
 */
var icheckradioFunc = {
	getSelection : function(rule, args) {
		return pubsub.getJQObj(rule).icheckradio("getSelection");
	},
	getText:function(rule, args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0 && args[0] && args[0].currentTarget && args[0].currentTarget.id.indexOf($id[0].id) != -1) { //触发事件传过来的值
			retVal.push($(args[0].currentTarget).attr("text")||"");
		}else{         //控件获取的值
			var selections = $id.icheckradio("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.text);
			});
		}
		return retVal.join(",");
	},
	getValue:function(rule, args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0 && args[0] && args[0].currentTarget && args[0].currentTarget.id.indexOf($id[0].id) != -1) { //触发事件传过来的值
			retVal.push($(args[0].currentTarget).attr("value")||"");
		}else{         //控件获取的值
			var selections = $id.icheckradio("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.value);
			});
		}
		return retVal.join(",");
	},
	getData:function (rule,args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
//			var datas = $(args[0].delegateTarget).data("icheckradio").options.__dataArray;
			var datas =  $id.data("icheckradio").options.__dataArray;
			var index = $(args[0].currentTarget).attr("row-index")||"";
			if(datas.length>0){
				retVal.push(datas[index][rule.columnName]);
			}
		}else{         //控件获取的值
			var selections = $id.icheckradio("getSelection");
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
			$id.icheckradio("setValue",v);
		} else {
			$id.icheckradio("setValue",args);
		}
	},
	//联动事件
	linkage: function(rule, args) {
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.icheckradio("linkage",args);
		
	},
	
	tdisabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckradio("disabled",false);
	},
	tenabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckradio("disabled",true);
	},
	disable: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("disable", args.index);
		}
	},
	enable: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("enable", args.index);
		}
	},
	check: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("check", args.index);
		}
	},
	uncheck: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("uncheck", args.index);
		}
	},
	uncheckAll: function(rule, args){
		pubsub.getJQObj(rule).icheckradio("uncheckAll");
	},
};
pubsub.sub("icheckradio", icheckradioFunc);