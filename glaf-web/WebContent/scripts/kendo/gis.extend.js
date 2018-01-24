var gisFunc = {
	getValue : function(rule, args) {
		return args[1] || '0';
	},
	linkageControl : function(id, params) { // 联动控件
		for ( var p in params) {
			slCtl.Content.mainForm.SelectUnit(params[p]/* "28738" */, '#003333CC');
		}
	}
};
pubsub.sub("gis", gisFunc);