
var tabbarFunc = {
		
		getValue : function(rule,args){
			var $id = pubsub.getJQObj(rule.inid);
			var target = $id.data("tabbar").target;
			var item = target.find(".weui-bar__item--on").attr("href");
			return item.substring(item.indexOf("#tab") + "#tab".length,item.length);
		},
		visible : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("tabbar").visible(args);
		},
		hidden : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			
			$id.data("tabbar").hidden(args);
		},
		selectNode : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			
			$id.data("tabbar").select(args);
		}
};
pubsub.sub("tabbar", tabbarFunc);