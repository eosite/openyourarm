/**
 * 自定义卡片事件定义器接口
 */
var definedcardFunc = {
	getSelectRow : function(rule, args){
		//pubsub.getJQObj(rule,true)，若是true则获取输入控件，否则获取输出控件。
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.definedCardExt("getSelectRow");
		return data[rule.columnName];
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
	refresh : function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.definedCardExt("refresh");
	},
	linkage : function(rule,params){
		params["databaseId"] && pubsub.getJQObj(id).attr("dbid", params["databaseId"]);
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.definedCardExt("linkage",params);
	},
};
pubsub.sub("definedcard", definedcardFunc);