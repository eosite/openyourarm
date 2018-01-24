/**
 * 网格
 */
var definedPanelFunc = {
	getSelectedData: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).definedPanelExt("getSelectRowData"),
			val = [];
		if (dataItems) {
			return dataItems[rule.columnName];
		}
		return "";
	},
};

pubsub.sub("definedpanel", definedPanelFunc);