
var searchbarFunc = {
		setValue : function(rule,args){
			var $id = pubsub.getJQObj(rule);
	    	$.each(args,function(i,arg){
	    		$id.data("searchbar").setValue(arg);
	    	});
		},
		appendSetValue : function(rule,args){
			var $id = pubsub.getJQObj(rule);
	    	$.each(args,function(i,arg){
	    		$id.data("searchbar").appendSetValue(arg);
	    	});
		},
		tdisabled : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("searchbar").tdisabled();
		},
		tenabled : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("searchbar").tenabled();
		},
		thidden : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("searchbar").thidden();
		},
		tshow : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("searchbar").tshow();
		}
};
pubsub.sub("searchbar", searchbarFunc);