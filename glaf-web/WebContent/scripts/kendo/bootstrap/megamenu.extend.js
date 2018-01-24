/**
 * menu事件
 */
var megaMenuFunc = {

	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.megaMenuExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.megaMenuExt("display",true);
	},
	getMenu:function(rule,args){	//获取数据集
		return args[0][rule.columnName];
	},
	removeAllSelected : function(rule,args){
		//取消所有选中
		var $id = pubsub.getJQObj(rule);
		var selectcolor = $id.attr("selectcolor");
		var selectColor = $id.megaMenuExt("getSelectColor", selectcolor);
		$id.data("select").removeClass(selectColor);
		$id.removeData("select");
	},
	linkage: function(rule, params) {
		pubsub.getJQObj(rule).megaMenuExt("query", params);
	},
	tSelectFirst : function(rule,params){
		pubsub.getJQObj(rule).megaMenuExt("tSelectFirst");	
	}
	
};
pubsub.sub("megamenu", megaMenuFunc);