var bridgeseamFunc = {
	init: function(rule, args) {
		var options = args[0];
		options.url = contextPath + "/mx/form/charts/datas";
		options.data = JSON.stringify({
			'rid': options.rid,
			'params': getParams(rule.inid) ? JSON.stringify(getParams(rule.inid)) : null
		});
		var $this = $('#' + rule.inid);
		$this.bridgeSeam(options);
	},
	linkage :function(id, params, args){
		bridgeseamFunc.linkageControl(id, params, args);
	},
	linkageControl: function(id, params, args) {
		var $this = pubsub.getJQObj(id);
		$this.bridgeSeam("query",params);
	}
};
pubsub.sub("bridgeseam", bridgeseamFunc);