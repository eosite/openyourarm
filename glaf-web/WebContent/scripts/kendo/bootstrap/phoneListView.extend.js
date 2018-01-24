var phoneListViewFunc = {		
    getAll : function(){
    	var $in = pubsub.getJQObj(rule, /*args[0] ||*/ true);
		if ($in) {
			var retAry = [],
				dataItems = $in.data("phoneListView").getData(),
				dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName] || "");
				}
				return retAry.join(",");
			}
		}
		return "";
    }
};
pubsub.sub("phoneListView", phoneListViewFunc);
