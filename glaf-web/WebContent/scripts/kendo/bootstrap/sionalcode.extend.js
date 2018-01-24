/**
 *二维码事件定义器接口
 */
var sionalcodeFunc = {
		
		copyContent:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		args.id=$id.selector;
		$(args.id).empty();
		$id.sionalcodeExt("copyContent",args);
	},
	downloadSional:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		args.id=$id.selector;
		$id.sionalcodeExt("downloadSional",args);
	},
};
pubsub.sub("sionalcode", sionalcodeFunc);