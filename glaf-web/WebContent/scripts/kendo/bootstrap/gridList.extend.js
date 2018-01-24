/**
 * 九宫格事件定义器接口
 */
var gridlistFunc = {
	setIndex : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$id.css("z-Index",v);
	},
	getNowClickCell : function(rule, args) {
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getClickValue");
		return data[rule.columnName];
	},
	getNowDblClickCell : function(rule, args) {
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getDblClickValue");
		return data[rule.columnName];
	},
	isChecked: function(rule, args) {
		if (args && args[0]) {
			var $jq = pubsub.getJQObj(rule, true);
			var gridlistBox = $jq.gridList("getGridlistBox");
			
			return gridlistBox.find('>.gridList_ul>li>.demo_content[' + "cell-index" + "=" + args[0]["cell-index"] + ']').hasClass("selected");
			// return g.tbody().find("tr:eq(" + args[1]["row-index"] + ")").hasClass(g.options.selectedCls);
		}
		return false;
	},
	getClickCell : function(rule, args){
		//pubsub.getJQObj(rule,true)，若是true则获取输入控件，否则获取输出控件。
		// var $id = pubsub.getJQObj(rule,true),r, v = "";
		// var data = $id.gridList("getClickValue");
		// return data[rule.columnName];


		var dataItems = pubsub.getJQObj(rule, true).gridList("getSelectedDatas"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
					// pubsub.outParamsObj(rule, obj, o);
				}
			});
			return val.join(",");
		}

	},
	getAll : function(rule, args) {
		var $in = pubsub.getJQObj(rule, /*args[0] ||*/ true);
		if ($in) {
			var retAry = [], dataItems = $in.data("gridList").getData(), dataItem;
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
	linkage: function(rule, params) {
		gridlistFunc.linkageControl(rule, params);
	},
	linkageControl : function(id, params) {
		pubsub.getJQObj(id).gridList("query", params);
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show();
	},

	getDrapRow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getDrapRow");
		return data[rule.columnName];
	},

	getDropRow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getDropRow");
		return data[rule.columnName];
	},

	getOverRow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getOverRow");
		return data[rule.columnName];
	},

	refresh: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		var data = $id.gridList("refresh");
	},
	//禁用单元格
	disableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				if(args[r.param] == null){
					v = "";
				}
			}
		} else {
			v = args;
		}
		$id.gridList("disableCell",v);
	},
	//禁用单元格
	blukCancelDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";

		var key = args.key;
		var v = args.value;
		$id.gridList("blukCancelDisableCell",key,v);
	},
	//全部选择
	selectCheckCell: function(rule,args){
		var $id = pubsub.getJQObj(rule.outid,true),
		    build = $id.data('gridList'),
		    dataRule = $(build.target).attr('data-rule'),
		    ele = $(build.target).find("li");
		$.each(ele,function(i,e){
			if($(e).find(".disabledCell")[0] != undefined && $(e).find(".disabledCell")[0] != null){
				$(e).find("input[type=checkbox]").iCheck("uncheck");
			}
			else{
				$(e).find(".demo_content").addClass("selected");
				$(e).find("input[type=checkbox]").iCheck("check");
			}
			
			
		});
		
	},
	//全部取消
	cancelCheckCell: function(rule,args){
		var $id = pubsub.getJQObj(rule.outid,true),
		    build = $id.data('gridList'),
		    dataRule = $(build.target).attr('data-rule'),
		    ele = $(build.target).find("li");
		
		$.each(ele,function(i,e){
			$(e).find(".demo_content").removeClass("selected");
			$(e).find("input[type=checkbox]").iCheck("uncheck");
			
		});
	},
	
	
	//取消禁用格
	cancelDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.gridList("cancelDisableCell",v);
		} else {
			$id.gridList("cancelDisableCell",args);
		}
	},
	//取消全部禁用色
	cancelAllDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.gridList("cancelAllDisableCell");
	},

	//禁用单元格
	blukDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";

		var key = args.key;
		var v = args.value;

		// if (typeof args == "object") {
		// 	for (var i = 0; i < rule.length; i++) {
		// 		r = rule[i];
		// 		v = args[r.param];
		// 		if(args[r.param] == null){
		// 			v = "";
		// 		}
		// 	}
		// } else {
		// 	v = args;
		// }
		
		$id.gridList("blukDisableCell",key,v);
	},

	getKeyName : function(rule,args){
		return rule.columnName;
	}
	
};
pubsub.sub("gridlist", gridlistFunc);
pubsub.sub("gridList", gridlistFunc);