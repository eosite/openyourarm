var mediaListFunc = {		
    
    linkageControl : function(rule,args){
    	var $id = pubsub.getJQObj(rule);
        $id.data("mdatepicker").tdisabled();
    
    },
    loadData : function(rule,args){
    	var $id = pubsub.getJQObj(rule);
        $id.data("mdatepicker").tdisabled();
    },
    getSelectItem : function(rule,args){
    	var $id = pubsub.getJQObj(rule),value = [];
    	$.each(args,function(i,arg){
    		value.push(arg[rule.columnName]);
    	})
    	return value.join(",");
    },
};
pubsub.sub("medialist", mediaListFunc);
