/**
 * 侧边栏事件定义器接口
 */
var sidebarFunc = {
	display:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.sidebarExt("display");
	},
	show:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.sidebarExt("showSidebar");
	},
	hide:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.sidebarExt("hideSidebar");
	},
};
pubsub.sub("sidebar", sidebarFunc);