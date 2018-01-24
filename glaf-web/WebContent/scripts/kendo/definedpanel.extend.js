/**
 * 网格
 */
var definedPanelFunc = {
	getSelectedData: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).grid("getSelectRowData"),
			val = [];
		if (dataItems && dataItems.length) {
			return o[rule.columnName];
		}
		return "";
	},
};

pubsub.sub("definedpanel", definedPanelFunc);