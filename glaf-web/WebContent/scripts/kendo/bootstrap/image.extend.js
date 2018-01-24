/**
 * Metronic icheckradio
 */
var imageFunc = {
	setAttachmentId : function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var attachmentId = "";
		$.each(args,function(key,value){
			attachmentId = value;
		})
		if(attachmentId){
			var url = contextPath + '/mx/form/imageUpload?method=downloadById&_' + (new Date().getTime()) + '&id=' + attachmentId;
			$id.attr("src",url).show();
		}
		
	},
	setAttachmentParent : function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var attachmentParent = "";
		$.each(args,function(key,value){
			attachmentParent = value;
		})
		if(attachmentParent){
			var url = contextPath + '/mx/form/imageUpload?method=download&from=to_db&' + (new Date().getTime()) + '&randomparent=' + attachmentParent;
			$id.attr("src",url).show();
		}
		
	},
	seturl : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var url = "";
		$.each(args,function(key,value){
			url = value;
		})
		$id.attr("src",url).show();
	}
};
pubsub.sub("image", imageFunc);