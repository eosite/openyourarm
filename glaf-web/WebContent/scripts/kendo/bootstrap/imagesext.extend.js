/**
 * 
 */
var imagesextFunc = {
	setAttachmentParent : function(){
		var $this = pubsub.getJQObj(rule) ;
		var value = null;
		$.each(params,function(i,item){
			value = item;
		})
		value && $this.imagesext("setParentId",value);		
	},
	getRandomparent : function(){
		var $this = pubsub.getJQObj(rule,true) ;
		return $this.imagesext("getRandomparent");
	}
};
pubsub.sub("imagesext", imagesextFunc);